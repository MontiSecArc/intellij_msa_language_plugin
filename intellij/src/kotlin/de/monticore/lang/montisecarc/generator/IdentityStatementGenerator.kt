package de.monticore.lang.montisecarc.generator

import com.intellij.psi.PsiElement
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
            var startIdentifier: String? = ""
            val msaIdentityIdentifier = msaIdentityStatement.identityIdentifierList.first()
            if (msaIdentityIdentifier != null) {
                if (msaIdentityIdentifier.portInstanceName != null) {

                    val msaPortElement = msaIdentityIdentifier.portInstanceName!!.references[0].resolve()
                    if (msaPortElement != null && msaPortElement is MSAPortElement) {

                        startIdentifier = PortElementGenerator.createPortIdentifier(msaPortElement)
                    }
                } else if (msaIdentityIdentifier.componentInstanceNameList.last() != null) {

                    val componentInstanceName = msaIdentityIdentifier.componentInstanceNameList.last()
                    val msaComponentDeclaration = componentInstanceName.references[0].resolve()
                    if (msaComponentDeclaration != null && msaComponentDeclaration is MSAComponentDeclaration) {

                        startIdentifier = ComponentDeclarationGenerator.createComponentIdentifier(msaComponentDeclaration)
                    } else if (msaComponentDeclaration != null && msaComponentDeclaration is MSAComponentInstanceDeclaration) {

                        startIdentifier = ComponentInstanceDeclarationGenerator.createComponentInstanceIdentifier(msaComponentDeclaration, componentInstanceName.name)
                    }
                }
            }
            msaIdentityStatement.identityIdentifierList.subList(1, msaIdentityStatement.identityIdentifierList.size).forEach {

                var stopIdentifier: String? = ""
                if (it.portInstanceName != null) {

                    val msaPortElement = it.portInstanceName!!.references[0].resolve()
                    if (msaPortElement != null && msaPortElement is MSAPortElement) {

                        stopIdentifier = PortElementGenerator.createPortIdentifier(msaPortElement)
                    }
                } else if (it.componentInstanceNameList.last() != null) {

                    val componentInstanceName = it.componentInstanceNameList.last()
                    val msaComponentDeclaration = componentInstanceName.references[0].resolve()
                    if (msaComponentDeclaration != null && msaComponentDeclaration is MSAComponentDeclaration) {

                        stopIdentifier = ComponentDeclarationGenerator.createComponentIdentifier(msaComponentDeclaration)
                    } else if (msaComponentDeclaration != null && msaComponentDeclaration is MSAComponentInstanceDeclaration) {

                        stopIdentifier = ComponentInstanceDeclarationGenerator.createComponentInstanceIdentifier(msaComponentDeclaration, componentInstanceName.name)
                    }
                }

                if (!startIdentifier.isNullOrEmpty() && !stopIdentifier.isNullOrEmpty()) {

                    val connector_model = mutableMapOf<String, Any>()
                    connector_model.put("relationship_type", link)
                    connector_model.put("start_port", startIdentifier!!)
                    connector_model.put("target_port", stopIdentifier!!)
                    val connector = FreeMarker.instance.generateModelOutput(getUrl("ToGraph/ConnectorMacro.ftl")?.path.orEmpty(), connector_model)

                    connectors.add(connector)
                }
            }
            return connectors
        }
        return null
    }
}