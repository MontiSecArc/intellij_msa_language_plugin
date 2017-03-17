package de.monticore.lang.montisecarc.generator.graph

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.isNullOrEmpty
import de.monticore.lang.montisecarc.generator.FreeMarker
import de.monticore.lang.montisecarc.generator.MSAGenerator
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
class IdentityStatementGenerator : MSAGenerator() {
    override fun generate(psiElement: PsiElement): Any? {

        if (psiElement is MSAIdentityStatement) {
            val connectors = mutableListOf<String>()
            val msaIdentityStatement = psiElement

            val strongAuth = msaIdentityStatement.node.findChildByType(MSATokenElementTypes.STRONG)
            var link = ":WEAK"
            if (strongAuth != null) {

                link = ":STRONG"
            }
            var startIdentifier: List<String> = emptyList()
            val msaIdentityIdentifier = msaIdentityStatement.identityIdentifierList.first()
            if (msaIdentityIdentifier != null) {
                if (msaIdentityIdentifier.componentInstanceNameList.last() != null) {

                    val componentInstanceName = msaIdentityIdentifier.componentInstanceNameList.last()
                    val msaComponentName = componentInstanceName.references[0].resolve()
                    val msaComponentDeclaration = PsiTreeUtil.getParentOfType(msaComponentName, MSAComponentDeclaration::class.java)
                    val msaComponentInstanceDeclaration = PsiTreeUtil.getParentOfType(msaComponentName, MSAComponentInstanceDeclaration::class.java)

                    /**
                     * Don't change order
                     */
                    if (msaComponentInstanceDeclaration != null && msaComponentInstanceDeclaration is MSAComponentInstanceDeclaration) {

                        startIdentifier = listOf(ComponentInstanceInstanceGenerator.createComponentInstanceIdentifier(msaComponentInstanceDeclaration, componentInstanceName.name))
                    } else if (msaComponentDeclaration != null && msaComponentDeclaration is MSAComponentDeclaration) {

                        startIdentifier = listOf(ComponentInstanceGenerator.createComponentIdentifier(msaComponentDeclaration))
                    }
                }
            }
            msaIdentityStatement.identityIdentifierList.subList(1, msaIdentityStatement.identityIdentifierList.size).forEach {

                var stopIdentifier: List<String> = emptyList()
                if (it.componentInstanceNameList.last() != null) {

                    val componentInstanceName = it.componentInstanceNameList.last()
                    val msaComponentName = componentInstanceName.references[0].resolve()
                    val msaComponentDeclaration = PsiTreeUtil.getParentOfType(msaComponentName, MSAComponentDeclaration::class.java)
                    val msaComponentInstanceDeclaration = PsiTreeUtil.getParentOfType(msaComponentName, MSAComponentInstanceDeclaration::class.java)

                    /**
                     * Order is important because if it is an instance declaration we can also find a parent component declaration (in most cases)
                     */
                    if (msaComponentInstanceDeclaration != null && msaComponentInstanceDeclaration is MSAComponentInstanceDeclaration) {
                        stopIdentifier = listOf(ComponentInstanceInstanceGenerator.createComponentInstanceIdentifier(msaComponentInstanceDeclaration, componentInstanceName.name))
                    } else if (msaComponentDeclaration != null && msaComponentDeclaration is MSAComponentDeclaration) {

                        stopIdentifier = listOf(ComponentInstanceGenerator.createComponentIdentifier(msaComponentDeclaration))
                    }
                }

                if (!startIdentifier.isNullOrEmpty() && !stopIdentifier.isNullOrEmpty()) {

                    startIdentifier.forEach {
                        val start = it
                        stopIdentifier.forEach {
                            val stop = it
                            val connector_model = mutableMapOf<String, Any>()
                            connector_model.put("relationship_type", link)
                            connector_model.put("start_port", start)
                            connector_model.put("target_port", stop)
                            connector_model.put("element_offset", msaIdentityStatement.textOffset)
                            val connector = FreeMarker(this.javaClass).generateModelOutput("ToGraph/ConnectorMacro.ftl", connector_model)

                            connectors.add(connector)
                        }
                    }

                }
            }
            return connectors
        }
        return null
    }
}