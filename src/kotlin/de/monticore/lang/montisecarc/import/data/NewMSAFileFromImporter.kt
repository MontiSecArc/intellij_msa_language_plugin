package de.monticore.lang.montisecarc.import.data

import com.intellij.ide.util.PackageChooserDialog
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptor
import com.intellij.psi.search.GlobalSearchScope
import de.monticore.lang.montisecarc.generator.FreeMarker
import java.io.File

/**
 * Copyright 2017 thomasbuning
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
open class NewMSAFileFromImporter(val importer: Importer) : AnAction() {

    override fun actionPerformed(e: AnActionEvent?) {

        val chooseFile = FileChooser.chooseFile(FileChooserDescriptor(true, true, true, true, true, true), null, null)

        if (chooseFile != null) {

            val packageChooserDialog = PackageChooserDialog("Choose output package", e?.project)
            packageChooserDialog.show()
            val fileName = chooseFile.name.substringBeforeLast(".")
            val packageName = packageChooserDialog.selectedPackage.qualifiedName + "." + fileName

            val directories = packageChooserDialog.selectedPackage.getDirectories(GlobalSearchScope.projectScope(e?.project!!))

            if (directories.isNotEmpty()) {

                try {
                    directories[0].checkCreateSubdirectory(fileName)
                    directories[0].createSubdirectory(fileName)
                } catch (e: Exception) {
                }
                val path = directories[0].subdirectories.filter { it.name == fileName }.first().virtualFile.path

                val (components, inPortNeededIds, outPortNeededIds) = importer.toComponents(chooseFile.inputStream, path, packageName)

                components.forEach {

                    val modelFor = getModelFor(it, inPortNeededIds, outPortNeededIds, packageName)
                    val file = File("$path/${it.typeName}.secarc")
                    file.writeText(modelFor)
                }
            }
        }
    }

    private fun getChildModelsRecursive(children: List<Component>, inPortNeededIds: List<String>, outPortNeededIds: List<String>, packageIdentifier: String): List<Pair<String, List<String>>> {
        return children.map { child ->
            val map = mutableMapOf<String, Any>()
            map.put("componentName", child.typeName)
            map.put("componentInstanceName", child.instanceName)
            if (child.trustLevel != null) {
                map.put("trustlevel", child.trustLevel.toString())
            }
            val list = getChildModelsRecursive(child.children, inPortNeededIds, outPortNeededIds, packageIdentifier)
            map.put("subcomponents", list.map { it.first })
            if (inPortNeededIds.contains(child.id)) {
                map.put("inNeeded", "")
            }
            if (outPortNeededIds.contains(child.id)) {
                map.put("outNeeded", "")
            }
            var imports = emptyList<String>()
            if (child.connections.isNotEmpty()) {
                val connections = child.connections.map {
                    var from = child.getPath(it.from)
                    var to = child.getPath(it.to)
                    // Create Instance
                    val mutableMap = mutableMapOf<String, String>()
                    if (to != null) {
                        if (!from.isNullOrEmpty()) {
                            from += "."
                        }
                        if (!to.isNullOrEmpty()) {
                            to += "."
                        }
                        mutableMap.put("from", from ?: "")
                        mutableMap.put("to", to)
                    } else {
                        mutableMap.put("from", "")
                        mutableMap.put("to", "${it.to.instanceName}.")
                    }
                    mutableMap
                }
                val instances = child.connections.filter { child.getPath(it.to) == null }.map {
                    val mutableMap = mutableMapOf<String, String>()
                    mutableMap.put("type", it.to.typeName)
                    mutableMap.put("name", it.to.instanceName)
                    mutableMap.put("connector", "[outDummy -> inDummy]")
                    Pair(mutableMap, "$packageIdentifier.${it.to.getPackageIdentifier()}")
                }
                map.put("instances", instances.map { it.first })
                map.put("connections", connections)
                imports = imports.plus(instances.map { it.second })
            }
            Pair(FreeMarker(this.javaClass).generateModelOutput("ToMSA/MSAComponent.ftl", map).removeUnnecessaryFreeMarkerCharacters(), imports)
        }
    }

    private fun getModelFor(env: Component, inPortNeededIds: List<String>, outPortNeededIds: List<String>, packageIdentifier: String): String {
        val map = mutableMapOf<String, Any>()
        map.put("componentName", env.typeName)
        if (env.trustLevel != null) {
            map.put("trustlevel", env.trustLevel as Int)
        }
        val childModels: List<Pair<String, List<String>>> = getChildModelsRecursive(env.children, inPortNeededIds, outPortNeededIds, packageIdentifier)
        map.put("subcomponents", childModels.map { it.first })
        if (inPortNeededIds.contains(env.id)) {
            map.put("inNeeded", "")
        }
        if (outPortNeededIds.contains(env.id)) {
            map.put("outNeeded", "")
        }
        map.put("packageName", packageIdentifier)
        if (env.connections.isNotEmpty()) {
            val connections = env.connections.map {
                var from = env.getPath(it.from)
                var to = env.getPath(it.to)
                // Create Instance
                val mutableMap = mutableMapOf<String, String>()
                if (to != null) {
                    if (!from.isNullOrEmpty()) {
                        from += "."
                    }
                    if (!to.isNullOrEmpty()) {
                        to += "."
                    }
                    mutableMap.put("from", from ?: "")
                    mutableMap.put("to", to)
                } else {
                    mutableMap.put("from", "")
                    mutableMap.put("to", "${it.to.instanceName}.")
                }
                mutableMap
            }
            val instances = env.connections.filter { env.getPath(it.to) == null }.map {
                val mutableMap = mutableMapOf<String, String>()
                mutableMap.put("type", it.to.typeName)
                mutableMap.put("name", it.to.instanceName)
                mutableMap
            }
            map.put("connections", connections)
            map.put("instances", instances)
        }
        val imports = env.connections.filter { env.getPath(it.to) == null }.map { "$packageIdentifier.${it.to.getPackageIdentifier()}" }
        val childImports = childModels.flatMap { it.second }
        map.put("imports", imports.union(childImports))
        return FreeMarker(this.javaClass).generateModelOutput("ToMSA/MSAEnvComponent.ftl", map).removeUnnecessaryFreeMarkerCharacters()
    }

    private fun String.removeUnnecessaryFreeMarkerCharacters() = Regex("[\r\n]+").replace(this.trim(), "\r\n")

}
