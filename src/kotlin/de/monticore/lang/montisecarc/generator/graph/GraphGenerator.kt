package de.monticore.lang.montisecarc.generator.graph

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.psi.PsiClass
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.MSAFileElementType
import de.monticore.lang.montisecarc.generator.FreeMarker
import de.monticore.lang.montisecarc.generator.Generator
import de.monticore.lang.montisecarc.generator.MSAGenerator
import de.monticore.lang.montisecarc.psi.MSAClearanceForStatement
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSACompositeElementTypes
import de.monticore.lang.montisecarc.psi.MSAPortElement
import java.io.InputStream

/**
 * Copyright 2016 Thomas Buning
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
class GraphGenerator : Generator() {

    override fun getSuffix(): String = ""

    override fun save(parseFile: PsiFile) {
    }

    override fun getExtension(): String = ".cyp"

    override fun getDisplayName(): String = "Graph Generator"


    private val trustLevels = mutableSetOf<Int>()
    private val nodes = mutableSetOf<String>()
    private val connectors = mutableSetOf<String>()
    private val securityClasses = mutableSetOf<String>()
    private val securityClassesConnectors = mutableSetOf<String>()
    private var referencedComponentInstances = mutableListOf<Pair<PsiFile, MSAComponentDeclaration>>()
    /**
     * Key is the super component that needs to be replaced by the value
     */
    private var componentExtendHierarchy = mutableMapOf<String, String>()

    private fun isSubComponent(componentQualifiedNames: List<String>, qualifiedName: String): Boolean
            = componentQualifiedNames.filter { it != qualifiedName }.any { qualifiedName.startsWith(it) && qualifiedName.contains(it) }

    private fun filterReferencedComponentInstances(parsedFile: PsiFile): List<MSAComponentDeclaration> {
        val filteredInstances = referencedComponentInstances.filter { it.first != parsedFile }
        val filterSuperComponents = superComponents.filter { it.first != parsedFile }

        var qualifiedNames = filteredInstances.map { it.second.qualifiedName }.union(filterSuperComponents.map { it.second.qualifiedName }).toList()
        qualifiedNames = qualifiedNames.filter { !isSubComponent(qualifiedNames, it) }

        return referencedComponentInstances.union(superComponents)
                .filter { qualifiedNames.contains(it.second.qualifiedName) }.map { it.second }
    }

    override fun aggregateResultFor(parsedFile: PsiFile): InputStream? {

        val list = filterReferencedComponentInstances(parsedFile)

        referencedComponentInstances.clear()

        list.forEach {

            walkElement(it)
        }

        /*
        * - We got the original file (parsedFile) and all files referenced by Instances
        * - Now we need to collect all ports and components etc from superClasses and combine them in the extending class
        * - Ports could be already imported from references, or components so it is needed to check if these are already included
        * - SuperClasses don't need to be included because port etc. shall be combined in the extending class
        * */
        for ((superComponent, extendingComponent) in componentExtendHierarchy) {

            // Remove Super Components
            nodes.removeAll { it -> it.startsWith("($superComponent:Component") }

            // Fix Links
            connectors.mapInPlace { it.replace("($superComponent)", "($extendingComponent)") }
        }

        trustLevels.forEach {
            nodes.add(TrustLevelGenerator.generateTrustLevelNode(it))
        }

        nodes.addAll(securityClasses.distinct())
        connectors.addAll(securityClassesConnectors.distinct())

        nodes.addAll(connectors)

        if (nodes.isEmpty()) {
            return null
        }

        //CREATE ${nodes};
        val model = mutableMapOf<String, String>()
        model.put("nodes", nodes.filter { !it.isNullOrEmpty() }.joinToString())
        val graph = FreeMarker(this.javaClass).generateModelOutput("ToGraph/Create.ftl", model)

        return graph.byteInputStream()
    }

    inline fun <T> MutableSet<T>.mapInPlace(mutator: (T) -> T) {
        val iterate = this.iterator()
        val nodesToUpdate = mutableListOf<Pair<T, T>>()
        while (iterate.hasNext()) {
            val oldValue = iterate.next()
            val newValue = mutator(oldValue)
            if (newValue !== oldValue) {

                nodesToUpdate.add(Pair(oldValue, newValue))
            }
        }

        for ((oldValue, newValue) in nodesToUpdate) {
            this.remove(oldValue)
            this.add(newValue)
        }
    }

    private val superComponents = mutableListOf<Pair<PsiFile, MSAComponentDeclaration>>()

    override fun registerGenerators(): GraphGenerator {

        fun extractStringsFromList(addTo: MutableSet<String>): (Any) -> Unit {
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

        fun extractStringsFromListAndReferences(addTo: MutableSet<String>, references: MutableList<Pair<PsiFile, MSAComponentDeclaration>>): (Any) -> Unit {
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

        fun extractString(addTo: MutableSet<String>): (Any) -> Unit {
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

        registerGenerator(MSACompositeElementTypes.COMPONENT_DECLARATION, ComponentInstanceGenerator(), extractString(nodes))

        registerGenerator(MSACompositeElementTypes.COMPONENT_DECLARATION, ComponentDeclarationConnectorGenerator(), extractStringsFromList(connectors))

        registerGenerator(MSACompositeElementTypes.COMPONENT_DECLARATION, PortComponentInstanceDeclarationConnectorGenerator(), extractStringsFromList(connectors))

        registerGenerator(MSACompositeElementTypes.COMPONENT_DECLARATION, ComponentHierarchyConnectorGenerator(), extractStringsFromList(connectors))

        registerGenerator(MSACompositeElementTypes.COMPONENT_DECLARATION, SuperComponentGenerator(), {

            foundComponents ->
            if (foundComponents is List<*>) {


                foundComponents.forEach {

                    if (it is Triple<*, *, *>) {

                        if (it.first is PsiFile && it.second is MSAComponentDeclaration) {
                            superComponents.add(Pair(it.first as PsiFile, it.second as MSAComponentDeclaration))

                            if (it.third is MSAComponentDeclaration) {

                                componentExtendHierarchy.put((it.second as MSAComponentDeclaration).qualifiedName.replace(".", "_"), (it.third as MSAComponentDeclaration).qualifiedName.replace(".", "_"))
                            }
                        }
                    }
                }
            }
        })

        registerGenerator(MSACompositeElementTypes.PORT_ELEMENT, PortElementGenerator(), extractString(nodes))

        registerGenerator(MSACompositeElementTypes.PORT_ELEMENT, PortElementConnectorGenerator(), extractStringsFromList(connectors))

        registerGenerator(MSACompositeElementTypes.COMPONENT_INSTANCE_DECLARATION, ComponentInstanceInstanceGenerator(), extractStringsFromListAndReferences(nodes, referencedComponentInstances))

        registerGenerator(MSACompositeElementTypes.COMPONENT_INSTANCE_DECLARATION, PortComponentInstanceConnectorGenerator(), extractStringsFromList(connectors))

        registerGenerator(MSACompositeElementTypes.COMPONENT_INSTANCE_DECLARATION, ComponentInstanceDeclarationConnectorGenerator(), extractStringsFromList(connectors))

        registerGenerator(MSACompositeElementTypes.COMPONENT_INSTANCE_DECLARATION, ComponentInstancePortElementGenerator(), {

            if (it is Pair<*, *>) {

                if (it.first is List<*>) {

                    (it.first as List<*>).forEach {
                        if (it is String) {

                            nodes.add(it)
                        }
                    }
                }
                if (it.second is List<*>) {

                    (it.second as List<*>).forEach {
                        if (it is String) {

                            connectors.add(it)
                        }
                    }
                }
            }
        })

        registerGenerator(MSACompositeElementTypes.IDENTITY_STATEMENT, IdentityStatementGenerator(), extractStringsFromList(connectors))

        registerGenerator(MSACompositeElementTypes.CONNECTOR, ConnectorGenerator(), extractStringsFromList(connectors))

        registerGenerator(MSACompositeElementTypes.COMPONENT_DECLARATION, ComponentTrustLevelGenerator(), extractTrustLevel())

        registerGenerator(MSACompositeElementTypes.CLEARANCE_FOR_STATEMENT, ClearanceForGenerator(), extractString(securityClassesConnectors))

        registerGenerator(MSAFileElementType, SecurityClassGenerator(), extractStringsFromList(securityClasses))

        registerGenerator(MSAFileElementType, SecurityClassLinkGenerator(), extractStringsFromList(securityClassesConnectors))

        registerGenerator(MSACompositeElementTypes.PORT_ELEMENT, SecurityClassPortConnectorGenerator(), extractStringsFromList(connectors))

        return this
    }
}

class SecurityClassPortConnectorGenerator : MSAGenerator() {

    override fun generate(psiElement: PsiElement): Any? {

        val msaPortElement = psiElement as MSAPortElement

        val portIdentifiers = PortElementGenerator.createPortIdentifiers(msaPortElement)

        val msaJavaReference = msaPortElement.javaClassReference?.javaReferenceList?.last()

        if (msaJavaReference != null) {

            val references = msaJavaReference.references

            if (references.isNotEmpty()) {

                val resolve = references.get(0).resolve()

                if (resolve != null && resolve is PsiClass) {

                    return resolve.modifierList?.annotations?.filter {

                        it.qualifiedName?.contains("SecurityClassification") ?: false
                    }?.flatMap {
                        it.parameterList.attributes.filter {
                            it.name == "securityClass"
                        }.map {
                            it.value?.text?.substringAfterLast(".")
                        }
                    }?.filter { it != null }?.requireNoNulls()?.flatMap { securityClass ->

                        portIdentifiers.map {

                            val connector_model = mutableMapOf<String, Any>()
                            connector_model.put("relationship_type", ":SECURITY_CLASS")
                            connector_model.put("start_port", it)
                            connector_model.put("target_port", securityClass)
                            connector_model.put("element_offset", psiElement.textOffset)
                            FreeMarker(this.javaClass).generateModelOutput("ToGraph/ConnectorMacro.ftl", connector_model)
                        }
                    }
                }
            }
        }
        return null
    }
}

class SecurityClassLinkGenerator : MSAGenerator() {

    override fun generate(psiElement: PsiElement): Any? {

        val securityLists = PsiShortNamesCache.getInstance(psiElement.project)
                .getClassesByName("MSASecurityClass", GlobalSearchScope.allScope(psiElement.project))
                .filter { it.isEnum }
                .map {
                    it.fields.filter { !it.name.isNullOrEmpty() }.map {
                        it.name!!
                    }
                }

        val list = securityLists.flatMap {

            it.mapIndexed { index, s ->
                if (index + 1 < it.size) {

                    val connector_model = mutableMapOf<String, Any>()
                    connector_model.put("relationship_type", ":WEAKER")
                    connector_model.put("start_port", s)
                    connector_model.put("target_port", it[index + 1])
                    connector_model.put("element_offset", psiElement.textOffset)
                    FreeMarker(this.javaClass).generateModelOutput("ToGraph/ConnectorMacro.ftl", connector_model)
                } else {
                    null
                }
            }
        }.filter { !it.isNullOrEmpty() }.requireNoNulls()

        return list

    }

}

class SecurityClassGenerator : MSAGenerator() {

    override fun generate(psiElement: PsiElement): Any? {

        return PsiShortNamesCache.getInstance(psiElement.project)
                .getClassesByName("MSASecurityClass", GlobalSearchScope.allScope(psiElement.project))
                .filter { it.isEnum }
                .flatMap {
                    it.fields.filter { !it.name.isNullOrEmpty() }.map {
                        it.name!!
                        val model = mutableMapOf<String, Any>()

                        model.put("id", it.name!!)
                        model.put("instance_name", it.name!!)

                        val extras = mutableMapOf<String, String>()
                        extras.put("element_offset", psiElement.textOffset.toString())
                        extras.put("file_path", psiElement.containingFile.virtualFile.canonicalPath.orEmpty())
                        model.put("extra_arguments", extras)

                        FreeMarker(this.javaClass).generateModelOutput("ToGraph/SecurityClassMacro.ftl", model)
                    }
                }
    }
}

class ClearanceForGenerator : MSAGenerator() {

    override fun generate(psiElement: PsiElement): Any? {

        val msaClearanceForStatement = psiElement as MSAClearanceForStatement
        val msaComponentDeclaration = PsiTreeUtil.getParentOfType(psiElement, MSAComponentDeclaration::class.java)
        if (msaComponentDeclaration != null) {
            val identifier = ComponentDeclarationGenerator.createComponentIdentifier(msaComponentDeclaration)
            val securityClass = msaClearanceForStatement.javaClassReference?.javaReferenceList?.last()?.text

            if (!securityClass.isNullOrEmpty()) {
                val connector_model = mutableMapOf<String, Any>()
                connector_model.put("relationship_type", ":CLEARANCE_FOR")
                connector_model.put("start_port", identifier)
                connector_model.put("target_port", securityClass!!)
                connector_model.put("element_offset", psiElement.textOffset)
                return FreeMarker(this.javaClass).generateModelOutput("ToGraph/ConnectorMacro.ftl", connector_model)
            }
        }
        return null
    }
}
