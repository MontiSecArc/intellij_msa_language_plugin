package de.monticore.lang.montisecarc.generator

import com.intellij.psi.PsiElement
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.psi.*

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
class PortElementGenerator : MSAGenerator() {

    companion object {

        fun createPortIdentifiers(portElement: MSAPortElement): List<String> {

            return getPortIdentifiers(portElement).map {
                val (referenceType, portInstanceName) = it
                val enclosingComponent = portElement.enclosingComponent
                if (enclosingComponent != null) {

                    val componentIdentifier = enclosingComponent.qualifiedName.replace(".", "_")
                    arrayOf(componentIdentifier, referenceType, portInstanceName).joinToString("_")
                } else {

                    arrayOf(referenceType, portInstanceName).joinToString("_")
                }
            }
        }

        fun createPortIdentifiers(portElement: MSAPortElement, referenceType: String, portInstanceName: String): String {

            val enclosingComponent = portElement.enclosingComponent
            if (enclosingComponent != null) {

                val componentIdentifier = enclosingComponent.qualifiedName.replace(".", "_")
                return arrayOf(componentIdentifier, referenceType, portInstanceName).joinToString("_")
            }

            return arrayOf(referenceType, portInstanceName).joinToString("_")
        }

        fun getPortIdentifiers(portElement: MSAPortElement): List<Pair<String, String>> {

            /**
             * If port element has generic type parameter we need to find all instance declarations (with the
             * type projections) to generate the right amount of port elements
             */
            val referenceType = portElement.javaClassReference?.text
            val typeParametersFromTreeParents = getTypeParametersFromTreeParents(portElement)

            val typeParameter = typeParametersFromTreeParents.find {
                it.second.any {
                    it.text == referenceType
                }
            }

            val portElementInstanceName = portElement.portInstanceName?.text
            if (typeParameter != null) {

                // Type is generic
                // Find references to the enclosing component
                val enclosingComponentName = typeParameter.first.componentSignature?.componentNameWithType?.componentName

                if (enclosingComponentName != null) {

                    return ReferencesSearch.search(enclosingComponentName).map {

                        var newPair: Pair<String, String>? = null
                        val componentName = it.element
                        if (componentName is MSAComponentName) {

                            /**
                             * Can be used in a component instance declaration or extends
                             */
                            val msaComponentNameWithTypeProjection = PsiTreeUtil.getParentOfType(componentName, MSAComponentNameWithTypeProjection::class.java)
                            if (msaComponentNameWithTypeProjection != null) {

                                typeParameter.second.listIterator().withIndex().forEach {

                                    if (it.value.text == referenceType) {

                                        val typeProjectionList = msaComponentNameWithTypeProjection.typeProjections?.typeProjectionList
                                        if (typeProjectionList != null && typeProjectionList.size >= it.index) {

                                            val newReferenceType = typeProjectionList[it.index]

                                            val referenceTypeText = newReferenceType.text
                                            val portInstanceName = portElementInstanceName ?: referenceTypeText.decapitalize()
                                            newPair = Pair(referenceTypeText, portInstanceName)
                                        }
                                    }

                                }
                            }
                        }
                        newPair
                    }.requireNoNulls()
                }
            } else {

                val portInstanceName = portElementInstanceName ?: referenceType?.decapitalize()

                if (!referenceType.isNullOrEmpty() && !portInstanceName.isNullOrEmpty()) {
                    return listOf(Pair(referenceType!!, portInstanceName!!))
                }
            }
            return emptyList()
        }

        private fun getTypeParametersFromTreeParents(element: PsiElement): MutableList<Pair<MSAComponentDeclaration, List<MSATypeVariableDeclaration>>> {
            var msaComponentDeclaration = PsiTreeUtil.getParentOfType(element, MSAComponentDeclaration::class.java)

            val collectedTypeVariable = mutableListOf<Pair<MSAComponentDeclaration, List<MSATypeVariableDeclaration>>>()
            while (msaComponentDeclaration != null) {

                val typeVariable = Pair(msaComponentDeclaration, msaComponentDeclaration.componentSignature?.componentNameWithType?.typeParameters?.typeVariableDeclarationList?.filter { !it.id.text.isNullOrEmpty() }.orEmpty())
                collectedTypeVariable.add(typeVariable)
                msaComponentDeclaration = PsiTreeUtil.getParentOfType(msaComponentDeclaration, MSAComponentDeclaration::class.java)
            }
            return collectedTypeVariable
        }

        fun createPortElementNode(psiElement: MSAPortElement): String {

            val ports = mutableListOf<String>()
            for ((referenceType, portInstanceName) in getPortIdentifiers(psiElement)) {
                val model = mutableMapOf<String, Any>()

                //<@node instance_name="${instance_name}" type_name="${type_name}" is_critical="${is_critical}" access_roles="${access_roles}" extra="${extra_arguments}" />

                //${instance_name}_${type_name}
                val identifier = createPortIdentifiers(psiElement, referenceType, portInstanceName)
                model.put("id", identifier)
                model.put("instance_name", portInstanceName.orEmpty())

                val extras = mutableMapOf<String, String>()
                extras.put("type_name", referenceType.orEmpty())
                extras.put("is_critical", psiElement.isCritical.toString())
                extras.put("element_offset", psiElement.textOffset.toString())
                extras.put("file_path", psiElement.containingFile.virtualFile.canonicalPath.orEmpty())

                val portAccessRoles = psiElement.enclosingComponent?.componentBody?.accessStatementList?.map { it.portAccessRoleList.filter { it.qualifiedIdentifier.portInstanceName.text == portInstanceName }.map { "'${it.qualifiedIdentifier.portInstanceName.text}'" }.joinToString() }.orEmpty().joinToString()

                extras.put("access_roles", portAccessRoles)

                model.put("extra_arguments", extras)

                ports.add(FreeMarker.instance.generateModelOutput("ToGraph/PortMacro.ftl", model))
            }

            return ports.joinToString(",")
        }
    }

    override fun generate(psiElement: PsiElement): String? {

        if (psiElement is MSAPortElement) {
            return createPortElementNode(psiElement)
        }
        return null
    }
}