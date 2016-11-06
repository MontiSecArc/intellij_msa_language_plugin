package de.monticore.lang.montisecarc.generator

import com.intellij.psi.PsiElement
import com.intellij.util.containers.isNullOrEmpty
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
class ComponentInstanceDeclarationGenerator : MSAGenerator() {

    companion object {
        fun createComponentInstanceIdentifier(msaComponentInstanceDeclaration: MSAComponentInstanceDeclaration): String {

            val msaComponentDeclaration = msaComponentInstanceDeclaration.componentNameWithTypeList.last().componentName.references[0].resolve()

            return (msaComponentDeclaration as MSAComponentDeclaration).qualifiedName.replace(".", "_")
        }

        fun createComponentInstanceIdentifier(msaComponentInstanceDeclaration: MSAComponentInstanceDeclaration, instanceName: String): String {

            return createComponentInstanceIdentifier(msaComponentInstanceDeclaration) + "_" + instanceName
        }
    }

    override fun generate(psiElement: PsiElement): Any? {

        if(psiElement is MSAComponentInstanceDeclaration) {

            val msaComponentDeclaration = psiElement.componentNameWithTypeList.last().componentName.references[0].resolve()

            if (msaComponentDeclaration != null && msaComponentDeclaration is MSAComponentDeclaration) {

                return psiElement.componentInstanceNameList.map {

                    generateComponentInstanceElement(msaComponentDeclaration, psiElement, it.name)
                }
            }
        }
        return null
    }

    private fun generateComponentInstanceElement(msaComponentDeclaration: MSAComponentDeclaration, msaComponentInstanceName: MSAComponentInstanceDeclaration, instanceName: String): String {

        val model = mutableMapOf<String, Any>()

        //<@node instance_name="${instance_name}" type_name="${type_name}" access_roles="${access_roles}" extra="${extra_arguments}" />
        val componentIdentifier = createComponentInstanceIdentifier(msaComponentInstanceName, instanceName)
        model.put("id", componentIdentifier.orEmpty())
        model.put("instance_name", instanceName)
        model.put("type_name", msaComponentDeclaration.qualifiedName)
        val accessRoles = msaComponentDeclaration.componentBody?.accessStatementList?.map { it.roleNameList.map { "'${it.text}'" }.joinToString() }?.joinToString()
        model.put("access_roles", accessRoles.orEmpty())

        val extras = mutableMapOf<String, String>()
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

        return FreeMarker.instance.generateModelOutput("ToGraph/ComponentMacro.ftl", model)
    }
}