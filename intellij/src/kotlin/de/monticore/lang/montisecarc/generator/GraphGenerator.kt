package de.monticore.lang.montisecarc.generator

import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.util.containers.isNullOrEmpty
import de.monticore.lang.montisecarc.psi.MSACompositeElementTypes
import java.net.URL

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
class GraphGenerator {

    private val files = mutableListOf<PsiFile>()
    private val registeredGenerators = mutableMapOf<IElementType, List<Pair<MSAGenerator, (Any) -> Unit>>>()
    private val trustLevels = mutableSetOf<Int>()
    private val referencedPortElements = mutableSetOf<String>()
    private val nodes = mutableListOf<String>()
    private val connectors = mutableListOf<String>()
    private var psiRecursiveElementWalkingVisitor: MSAPsiRecursiveElementWalkingVisitor? = null

    private fun getUrl(path: String): URL? {

        return this.javaClass.classLoader.getResource(path)
    }

    fun createGraph(): String {

        return files.map { generate(it) }.joinToString("")
    }

    private fun generate(parseFile: PsiFile): String {

        if (psiRecursiveElementWalkingVisitor != null) {
            parseFile.accept(psiRecursiveElementWalkingVisitor!!)

            trustLevels.forEach {
                nodes.add(TrustLevelGenerator.generateTrustLevelNode(it))
            }

            referencedPortElements.filter {
                var portElementIsIncluded = false

                for (node in nodes) {

                    if(node.contains(it)) {

                        portElementIsIncluded = true
                        break
                    }
                }

                portElementIsIncluded
            }.forEach {

            }

            nodes.addAll(connectors)

            if(nodes.isNullOrEmpty()) {
                return ""
            }

            //CREATE ${nodes};
            val model = mutableMapOf<String, String>()
            model.put("nodes", nodes.filter { !it.isNullOrEmpty() }.joinToString())
            val graph = FreeMarker.instance.generateModelOutput(getUrl("ToGraph/Create.ftl")?.path.orEmpty(), model)
            return graph
        }
        return ""
    }

    fun addFile(psiFile: PsiFile) = files.add(psiFile)

    fun registerGenerator(elementType: IElementType, generator: MSAGenerator, callback: (Any) -> Unit) {
        val pair = listOf(Pair(generator, callback))
        if (registeredGenerators.contains(elementType)) {

            registeredGenerators.put(elementType, registeredGenerators[elementType]!! + pair)

        } else {

            registeredGenerators.put(elementType, pair)
        }

        psiRecursiveElementWalkingVisitor = MSAPsiRecursiveElementWalkingVisitor(registeredGenerators)
    }

    fun registerDefaultGenerators() {

        fun extractStringsFromList(addTo: MutableList<String>): (Any) -> Unit {
            return {
                connector ->
                if (connector is List<*>) {
                    connector.forEach {
                        if (it is String) {
                            addTo.add(it)
                        }
                    }
                }
            }
        }

        fun extractStringsFromListAndReferences(addTo: MutableList<String>, references: MutableSet<String>): (Any) -> Unit {
            return {
                connector ->
                if (connector is Pair<*,*>) {

                    val nodeConnector = connector.first as List<*>
                    val referencedPorts = connector.second as List<*>
                    nodeConnector.forEach {
                        if (it is String) {
                            addTo.add(it)
                        }
                    }
                    referencedPorts.forEach {
                        if(it is String) {

                            references.add(it)
                        }
                    }
                }
            }
        }

        fun extractString(addTo: MutableList<String>): (Any) -> Unit {
            return {
                component ->
                if (component is String) {
                    addTo.add(component)
                }
            }
        }

        fun extractTrustLevel(): (Any) -> Unit {
            return {

                componentTrustLevel ->
                if (componentTrustLevel is Pair<*, *>) {

                    if (componentTrustLevel.first is Int && componentTrustLevel.second is List<*>) {

                        val componentIdentifier = componentTrustLevel.second as List<*>
                        componentIdentifier.forEach { if(it is String) { connectors.add(it) } }
                        val trustLevel = componentTrustLevel.first as Int
                        trustLevels.add(trustLevel)
                    }
                }
            }
        }

        registerGenerator(MSACompositeElementTypes.COMPONENT_DECLARATION, ComponentDeclarationGenerator(), extractString(nodes))

        registerGenerator(MSACompositeElementTypes.PORT_ELEMENT, PortElementGenerator(), extractString(nodes))

        registerGenerator(MSACompositeElementTypes.PORT_ELEMENT, PortElementConnectorGenerator(), extractStringsFromList(connectors))

        registerGenerator(MSACompositeElementTypes.COMPONENT_INSTANCE_DECLARATION, ComponentInstanceDeclarationGenerator(), extractStringsFromList(nodes))

        registerGenerator(MSACompositeElementTypes.IDENTITY_STATEMENT, IdentityStatementGenerator(), extractStringsFromList(connectors))

        registerGenerator(MSACompositeElementTypes.CONNECTOR, ConnectorGenerator(), extractStringsFromListAndReferences(connectors, referencedPortElements))

        registerGenerator(MSACompositeElementTypes.COMPONENT_DECLARATION, ComponentTrustLevelGenerator(), extractTrustLevel())

        registerGenerator(MSACompositeElementTypes.COMPONENT_INSTANCE_DECLARATION, ComponentInstanceTrustLevelGenerator(), extractTrustLevel())
    }
}

