package de.monticore.lang.montisecarc.generator

import com.intellij.psi.PsiElement
import com.intellij.util.containers.isNullOrEmpty
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSATokenElementTypes

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
class ComponentDeclarationGenerator : MSAGenerator() {

    companion object {

        fun createComponentIdentifier(componentDeclaration: MSAComponentDeclaration): String {

            return componentDeclaration.qualifiedName.replace(".", "_")
        }
    }

    override fun generate(psiElement: PsiElement): String? {

        if (psiElement is MSAComponentDeclaration) {

            val model = mutableMapOf<String, Any>()

            //<@node id="${id}" instance_name="${instance_name}"><#list extra_arguments!{} as attrName, attrVal>, ${attrName}:"${attrVal}"</#list></@node>
            val componentIdentifier = createComponentIdentifier(psiElement)
            model.put("id", componentIdentifier)

            val componentNameWithTypeList = psiElement.componentSignature?.componentName
            if (componentNameWithTypeList != null) {

                model.put("instance_name", componentNameWithTypeList.componentName.name)

            } else {
                return null
            }

            val extras = mutableMapOf<String, String>()
            extras.put("type_name", psiElement.qualifiedName)
            extras.put("trustlevel", psiElement.trustLevel.toString())
            extras.put("element_offset", psiElement.textOffset.toString())
            extras.put("file_path", psiElement.containingFile.virtualFile.canonicalPath.orEmpty())
            var accessRoles = psiElement.componentBody?.accessStatementList?.map { it.roleNameList.map { "'${it.text}'" }.joinToString() }?.joinToString()
            if(accessRoles.isNullOrEmpty()) {

                for (superComponent in psiElement.superComponents) {

                    val superComponentAccessRoles = superComponent.componentBody?.accessStatementList?.map { it.roleNameList.map { "'${it.text}'" }.joinToString() }?.joinToString()

                    if(!superComponentAccessRoles.isNullOrEmpty()) {

                        accessRoles = superComponentAccessRoles
                        break
                    }
                }
            }
            extras.put("access_roles", accessRoles.orEmpty())


            val configurationStatementList = psiElement.componentBody?.configurationStatementList
            if (!configurationStatementList.isNullOrEmpty()) {

                val config = configurationStatementList!!.first().id?.text
                if (!config.isNullOrEmpty()) {
                    extras.put("configuration", config!!)
                }
            }
            val cpeStatementList = psiElement.componentBody?.cpeStatementList
            if (!cpeStatementList.isNullOrEmpty()) {

                val cpe = cpeStatementList!!.first().string?.text
                if (!cpe.isNullOrEmpty()) {
                    extras.put("cpe", cpe!!.replace("\"", ""))
                }
            }

            val accessControlStatementList = psiElement.componentBody?.accessControlStatementList
            if (!accessControlStatementList.isNullOrEmpty()) {

                val findChildByType = accessControlStatementList!!.first().node.findChildByType(MSATokenElementTypes.ON)
                if (findChildByType != null) {

                    extras.put("access_control", "on")
                } else {

                    extras.put("access_control", "off")
                }
            } else {

                for (superComponent in psiElement.superComponents) {

                    val superComponentAccessControlStatementList = superComponent.componentBody?.accessControlStatementList

                    if(!superComponentAccessControlStatementList.isNullOrEmpty()) {
                        val findChildByType = superComponentAccessControlStatementList!!.first().node.findChildByType(MSATokenElementTypes.ON)
                        if (findChildByType != null) {

                            extras.put("access_control", "on")
                        } else {

                            extras.put("access_control", "off")
                        }
                        break
                    }
                }
            }

            model.put("extra_arguments", extras)

            return FreeMarker.instance.generateModelOutput("ToGraph/ComponentMacro.ftl", model)
        }
        return null
    }
}