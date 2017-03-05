package de.monticore.lang.montisecarc.policy

import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ContentIterator
import com.intellij.openapi.roots.OrderRootType
import com.intellij.openapi.roots.libraries.LibraryUtil
import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFileFilter
import com.intellij.util.PathUtil
import com.intellij.util.io.URLUtil
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import java.io.File
import java.io.InputStream
import javax.xml.XMLConstants
import javax.xml.parsers.SAXParserFactory
import javax.xml.transform.stream.StreamSource
import javax.xml.validation.SchemaFactory


/**
 * Copyright 2016 thomasbuning
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class PolicyLoader(val project: Project?) : ProjectComponent {

    val loadedPolicies = mutableListOf<Policy>()


    override fun getComponentName(): String = javaClass.name

    override fun disposeComponent() {

    }

    override fun projectClosed() {

    }

    override fun initComponent() {


    }

    private val graphQueries = mutableMapOf<String, String>()

    override fun projectOpened() {

        val logger = Logger.getInstance(PolicyLoader::class.java)

        logger.info("project opened")
        if (project != null) {

            logger.info("found project containing msa files")

            logger.info("after post startup")
            val jarEntryURL = URLUtil.getJarEntryURL(File(PathUtil.getJarPathForClass(PolicyLoader::class.java)), "/policy_schemes/Policies_v1.xsd")

            val findFileByURL = VfsUtil.findFileByURL(jarEntryURL) ?: return

            logger.info("found xsd file")
            for (libraryRoot in LibraryUtil.getLibraryRoots(project, false, false)) {

                val libraryEntry = LibraryUtil.findLibraryEntry(libraryRoot, project)
                if (libraryEntry != null) {

                    for (file in libraryEntry.getFiles(OrderRootType.CLASSES)) {
                        VfsUtilCore.iterateChildrenRecursively(file, VirtualFileFilter {

                            it.name.endsWith(".jar") || it.isDirectory || it.name == "PolicyConfiguration.xml" || it.name.endsWith(".cyp")
                        }, ContentIterator {

                            logger.info("found ${it.name}")
                            if (it.name == "PolicyConfiguration.xml" && validateAgainstXSD(it.inputStream, findFileByURL.inputStream)) {

                                logger.info("is policy configuration")
                                val factory = SAXParserFactory.newInstance()
                                val parser = factory.newSAXParser()
                                val saxHandler = PoliciesV1SAXHandler(it.parent.path)
                                parser.parse(it.inputStream, saxHandler)
                                loadedPolicies.addAll(saxHandler.policies)

                                for ((path, inputString) in graphQueries) {
                                    addGraphQueryToPolicy(path, inputString)
                                }
                            } else if (it.name.endsWith(".cyp")) {

                                val inputString = it.inputStream.bufferedReader().use { it.readText() }

                                logger.info("graph query: $inputString")
                                if (!addGraphQueryToPolicy(it.path, inputString)) {

                                    graphQueries.put(it.path, inputString)
                                }
                            }
                            true
                        })
                    }
                }
            }
        }
    }

    fun addGraphQueryToPolicy(queryPath: String, inputString: String): Boolean {

        for ((_, _, _, inspection, fix) in loadedPolicies) {

            if (inspection?.path == queryPath) {

                inspection.inspection = inputString
                return true
            } else if (fix?.path == queryPath) {

                fix.fix = inputString
                return true
            }
        }
        return false
    }

    fun validateAgainstXSD(xml: InputStream, xsd: InputStream): Boolean {
        try {
            val factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
            val schema = factory.newSchema(StreamSource(xsd))
            val validator = schema.newValidator()
            validator.validate(StreamSource(xml))
            return true
        } catch (ex: Exception) {
            return false
        }

    }
}

class PoliciesV1SAXHandler(val basePath: String) : DefaultHandler() {

    val policies = mutableListOf<Policy>()

    private var currentPolicy: Policy? = null
    private var currentPath: String? = null
    private var currentFixDescription: String? = null
    private var currentProblemDescription: String? = null
    private var isPath: Boolean = false
    private var isFixDescription: Boolean = false
    private var isProblemDescription: Boolean = false

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {

        if (qName == "Policy") {

            if (attributes != null) {
                val nameIndex = attributes.getIndex("name")
                val name = attributes.getValue(nameIndex)


                val idIndex = attributes.getIndex("id")
                val id = attributes.getValue(idIndex)

                val severityIndex = attributes.getIndex("severity")
                val severity = attributes.getValue(severityIndex)

                currentPolicy = Policy(id, name, severity, null, null)
            }
        } else if (qName == "Path") {
            isPath = true
        } else if (qName == "FixDescription") {

            isFixDescription = true
        } else if (qName == "ProblemDescription") {

            isProblemDescription = true
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        if (qName == "Policy") {

            policies.add(currentPolicy!!)
            currentPolicy = null
        } else if (qName == "GraphQueryInspection") {

            currentPolicy?.inspection = PolicyGraphQueryInspection(currentPath!!, null, currentProblemDescription!!)
            currentPath = null
            currentProblemDescription = null

        } else if (qName == "GraphQueryFix") {

            currentPolicy?.fix = PolicyGraphQueryFix(currentPath!!, null, currentFixDescription!!)
            currentPath = null
            currentFixDescription = null

        } else if (qName == "Path") {
            isPath = false
        } else if (qName == "FixDescription") {

            isFixDescription = false
        } else if (qName == "ProblemDescription") {

            isProblemDescription = false
        }
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {

        if (ch == null) {
            return
        }

        val text = kotlin.text.String(ch, start, length)
        if (isPath) {

            if (basePath.endsWith("/")) {
                currentPath = PathUtil.getLocalPath("$basePath$text")
            } else {
                currentPath = PathUtil.getLocalPath("$basePath/$text")
            }
        } else if (isFixDescription) {
            currentFixDescription = text

        } else if (isProblemDescription) {
            currentProblemDescription = text
        }
    }
}

data class Policy(val id: String, val name: String, val severity: String, var inspection: PolicyGraphQueryInspection?, var fix: PolicyGraphQueryFix?)

data class PolicyGraphQueryInspection(val path: String, var inspection: String?, val description: String)

data class PolicyGraphQueryFix(val path: String, var fix: String?, val description: String)