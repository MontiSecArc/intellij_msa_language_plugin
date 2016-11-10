package de.monticore.lang.montisecarc.generator

import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import com.intellij.util.containers.isNullOrEmpty
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSACompositeElementTypes

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
    private val nodes = mutableListOf<String>()
    private val connectors = mutableListOf<String>()
    private var psiRecursiveElementWalkingVisitor: MSAPsiRecursiveElementWalkingVisitor? = null
    private var referencedComponentInstances = mutableListOf<Pair<PsiFile, MSAComponentDeclaration>>()

    fun createGraph(): String {

        return files.map { generate(it) }.filter { !it.isNullOrEmpty() }.joinToString("")
    }

    private fun isSubComponent(componentQualifiedNames: List<String>, qualifiedName: String): Boolean {

        return componentQualifiedNames.any { it.contains(qualifiedName) && it != qualifiedName }
    }

    private fun generate(parseFile: PsiFile): String? {

        if (psiRecursiveElementWalkingVisitor != null) {
            ApplicationManager.getApplication().runReadAction({

                parseFile.accept(psiRecursiveElementWalkingVisitor!!)

                val referencedComponentNames = referencedComponentInstances.map { it.second.qualifiedName }
                referencedComponentInstances.forEach {

                    if (it.first != parseFile && !isSubComponent(referencedComponentNames, it.second.qualifiedName)) {

                        it.second.accept(psiRecursiveElementWalkingVisitor!!)
                    }
                }
            })

            trustLevels.forEach {
                nodes.add(TrustLevelGenerator.generateTrustLevelNode(it))
            }

            nodes.addAll(connectors)

            if (nodes.isNullOrEmpty()) {
                return ""
            }

            //CREATE ${nodes};
            val model = mutableMapOf<String, String>()
            model.put("nodes", nodes.filter { !it.isNullOrEmpty() }.joinToString())
            val graph = FreeMarker.instance.generateModelOutput("ToGraph/Create.ftl", model)
            return graph
        }
        return null
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

        fun extractStringsFromListAndReferences(addTo: MutableList<String>, references: MutableList<Pair<PsiFile, MSAComponentDeclaration>>): (Any) -> Unit {
            return {
                connector ->
                if (connector is Triple<*, *, *>) {

                    try {
                        val containingFile = connector.first as PsiFile
                        val referencedComponentInstance = connector.second as MSAComponentDeclaration
                        val nodes = connector.third as List<*>


                        nodes.forEach {
                            if (it is String) {
                                addTo.add(it)
                            }
                        }

                        references.add(Pair(containingFile, referencedComponentInstance))
                    } catch(e: Exception) {
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
                        componentIdentifier.forEach {
                            if (it is String) {
                                connectors.add(it)
                            }
                        }
                        val trustLevel = componentTrustLevel.first as Int
                        trustLevels.add(trustLevel)
                    }
                }
            }
        }

        registerGenerator(MSACompositeElementTypes.COMPONENT_DECLARATION, ComponentDeclarationGenerator(), extractString(nodes))

        registerGenerator(MSACompositeElementTypes.PORT_ELEMENT, PortElementGenerator(), extractString(nodes))

        registerGenerator(MSACompositeElementTypes.PORT_ELEMENT, PortElementConnectorGenerator(), extractStringsFromList(connectors))

        registerGenerator(MSACompositeElementTypes.COMPONENT_INSTANCE_DECLARATION, ComponentInstanceDeclarationGenerator(), extractStringsFromListAndReferences(nodes, referencedComponentInstances))

        registerGenerator(MSACompositeElementTypes.IDENTITY_STATEMENT, IdentityStatementGenerator(), extractStringsFromList(connectors))

        registerGenerator(MSACompositeElementTypes.CONNECTOR, ConnectorGenerator(), extractStringsFromList(connectors))

        registerGenerator(MSACompositeElementTypes.COMPONENT_DECLARATION, ComponentTrustLevelGenerator(), extractTrustLevel())

        registerGenerator(MSACompositeElementTypes.COMPONENT_INSTANCE_DECLARATION, ComponentInstanceTrustLevelGenerator(), extractTrustLevel())

        registerGenerator(MSACompositeElementTypes.COMPONENT_INSTANCE_DECLARATION, ComponentInstancePortElementGenerator(), {

            pair ->
            if (pair is Pair<*, *>) {

                val portElementNodes = pair.first as List<*>
                val portConnectors = pair.second as List<*>

                portElementNodes.map {
                    if (it is String) {

                        nodes.add(it)
                    }
                }

                portConnectors.map {
                    if (it is String) {

                        connectors.add(it)
                    }
                }
            }
        })
    }
}



