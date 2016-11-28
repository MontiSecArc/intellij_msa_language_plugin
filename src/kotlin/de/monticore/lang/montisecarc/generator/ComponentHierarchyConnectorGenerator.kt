package de.monticore.lang.montisecarc.generator

import com.intellij.psi.PsiElement
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration

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

class ComponentHierarchyConnectorGenerator : MSAGenerator() {

    private fun getModel(relationship: String, startIdentifier: String, targetIdentifier: String): String {

        val connector_model = mutableMapOf<String, Any>()
        connector_model.put("start_port", startIdentifier)
        connector_model.put("target_port", targetIdentifier)
        connector_model.put("relationship_type", ":$relationship")

        return FreeMarker.instance.generateModelOutput("ToGraph/ConnectorMacro.ftl", connector_model)
    }

    override fun generate(psiElement: PsiElement): Any? {

        if (psiElement is MSAComponentDeclaration) {

            val componentIdentifier = ComponentDeclarationGenerator.createComponentIdentifier(psiElement)

            val hierarchy = psiElement.componentBody?.componentDeclarationList?.flatMap {

                val childComponentIdentifier = ComponentDeclarationGenerator.createComponentIdentifier(it)

                listOf(getModel("PARENT_OF", componentIdentifier, childComponentIdentifier), getModel("CHILD_OF", childComponentIdentifier, componentIdentifier))
            }
            val hierarchy2 = psiElement.componentBody?.componentInstanceDeclarationList?.flatMap {

                val msaComponentDeclaration = it.componentNameWithTypeProjectionList.last().componentName.references[0].resolve()

                if (msaComponentDeclaration != null && msaComponentDeclaration is MSAComponentDeclaration) {

                    val childComponentIdentifier = ComponentInstanceGenerator.createComponentIdentifier(msaComponentDeclaration)

                    listOf(getModel("PARENT_OF", componentIdentifier, childComponentIdentifier), getModel("CHILD_OF", childComponentIdentifier, componentIdentifier))
                } else {

                    emptyList<String>()
                }
            }

            if (hierarchy != null && hierarchy2 == null) {

                return hierarchy
            } else if (hierarchy == null && hierarchy2 != null) {

                return hierarchy2
            } else if (hierarchy != null && hierarchy2 != null) {

                return hierarchy.union(hierarchy2).toList()
            }
        }
        return null
    }
}