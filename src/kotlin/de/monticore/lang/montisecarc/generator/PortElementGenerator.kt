package de.monticore.lang.montisecarc.generator

import com.intellij.psi.PsiElement
import de.monticore.lang.montisecarc.psi.MSAPortElement

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

        fun createPortIdentifier(portElement: MSAPortElement): String {
            val (referenceType, portInstanceName) = getPortIdentifiers(portElement)

            val enclosingComponent = portElement.enclosingComponent
            if (enclosingComponent != null) {

                val componentIdentifier = enclosingComponent.qualifiedName.replace(".", "_")
                return arrayOf(componentIdentifier, referenceType, portInstanceName).joinToString("_")
            } else {

                return arrayOf(referenceType, portInstanceName).joinToString("_")
            }
        }

        fun getPortIdentifiers(portElement: MSAPortElement): Pair<String?, String?> {
            val referenceType = portElement.javaClassReference?.text
            val portInstanceName = portElement.portInstanceName?.text ?: referenceType?.decapitalize()
            return Pair(referenceType, portInstanceName)
        }

        fun createPortElementNode(psiElement: MSAPortElement): String {
            val model = mutableMapOf<String, Any>()

            //<@node instance_name="${instance_name}" type_name="${type_name}" is_critical="${is_critical}" access_roles="${access_roles}" extra="${extra_arguments}" />

            //${instance_name}_${type_name}
            val (referenceType, portInstanceName) = getPortIdentifiers(psiElement)
            val identifier = createPortIdentifier(psiElement)
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

            return FreeMarker.instance.generateModelOutput("ToGraph/PortMacro.ftl", model)
        }
    }

    override fun generate(psiElement: PsiElement): String? {

        if (psiElement is MSAPortElement) {
            return createPortElementNode(psiElement)
        }
        return null
    }
}