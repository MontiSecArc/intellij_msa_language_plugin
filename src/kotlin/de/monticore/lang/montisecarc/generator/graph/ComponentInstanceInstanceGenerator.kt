package de.monticore.lang.montisecarc.generator.graph

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.isNullOrEmpty
import de.monticore.lang.montisecarc.generator.FreeMarker
import de.monticore.lang.montisecarc.generator.MSAGenerator
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSAComponentInstanceDeclaration
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
class ComponentInstanceInstanceGenerator : MSAGenerator() {

    companion object {

        fun createComponentInstanceIdentifier(msaComponentDeclaration: MSAComponentDeclaration, instanceName: String): String {

            return ComponentDeclarationGenerator.createComponentIdentifier(msaComponentDeclaration) + "_" + instanceName
        }
    }

    override fun generate(psiElement: PsiElement): Any? {

        if(psiElement is MSAComponentInstanceDeclaration) {

            val msaComponentName = psiElement.componentNameWithTypeProjectionList.last().componentName.references[0].resolve()

            /**
             * Gets component name
             */
            val msaComponentDeclaration = PsiTreeUtil.getParentOfType(msaComponentName, MSAComponentDeclaration::class.java)
            if (msaComponentDeclaration != null && msaComponentDeclaration is MSAComponentDeclaration) {

                val generatedNodes = psiElement.componentInstanceNameList.filter { it.name.isNotEmpty() }.map {

                    generateComponentInstanceElement(msaComponentDeclaration, psiElement, it.name)
                }

                return Triple(msaComponentDeclaration.containingFile, msaComponentDeclaration, generatedNodes)
            }
        }
        return null
    }

    private fun generateComponentInstanceElement(msaComponentDeclaration: MSAComponentDeclaration, msaComponentInstanceName: MSAComponentInstanceDeclaration, instanceName: String): String {

        val model = mutableMapOf<String, Any>()

        //<@node id="${id}" instance_name="${instance_name}"><#list extra_arguments!{} as attrName, attrVal>, ${attrName}:"${attrVal}"</#list></@node>
        val componentIdentifier = createComponentInstanceIdentifier(msaComponentDeclaration, instanceName)
        model.put("id", componentIdentifier)
        model.put("instance_name", instanceName)

        val extras = mutableMapOf<String, String>()
        extras.put("type_name", msaComponentDeclaration.qualifiedName)
        extras.put("element_offset", msaComponentInstanceName.textOffset.toString())
        extras.put("file_path", msaComponentInstanceName.containingFile.virtualFile.canonicalPath.orEmpty())
        val accessRoles = msaComponentDeclaration.componentBody?.accessStatementList?.map { it.roleNameList.map { "'${it.text}'" }.joinToString() }?.joinToString()
        extras.put("access_roles", accessRoles.orEmpty())

        val configurationStatementList = msaComponentDeclaration.componentBody?.configurationStatementList
        if (!configurationStatementList.isNullOrEmpty()) {

            val config = configurationStatementList!!.first().id?.text
            if (!config.isNullOrEmpty()) {
                extras.put("configuration", config!!)
            }
        }
        val cpeStatementList = msaComponentDeclaration.componentBody?.cpeStatementList
        if (!cpeStatementList.isNullOrEmpty()) {

            val cpe = cpeStatementList!!.first().string?.text
            if (!cpe.isNullOrEmpty()) {
                extras.put("cpe", cpe!!.replace("\"", ""))
            }
        }

        val accessControlStatementList = msaComponentDeclaration.componentBody?.accessControlStatementList
        if (!accessControlStatementList.isNullOrEmpty()) {

            val findChildByType = accessControlStatementList!!.first().node.findChildByType(MSATokenElementTypes.ON)
            if (findChildByType != null) {

                extras.put("access_control", "on")
            } else {

                extras.put("access_control", "off")
            }
        }

        model.put("extra_arguments", extras)

        return FreeMarker.instance.generateModelOutput("ToGraph/InstanceMacro.ftl", model)
    }
}