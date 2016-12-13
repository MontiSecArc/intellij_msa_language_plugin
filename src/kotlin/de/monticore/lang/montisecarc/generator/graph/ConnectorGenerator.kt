package de.monticore.lang.montisecarc.generator.graph

import com.intellij.psi.PsiElement
import de.monticore.lang.montisecarc.generator.FreeMarker
import de.monticore.lang.montisecarc.generator.MSAGenerator
import de.monticore.lang.montisecarc.psi.MSAConnector
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
class ConnectorGenerator : MSAGenerator() {

    private fun generateConnectorElement(msaConnector: MSAConnector): List<String> {

        var encrypted = ":UNENCRYPTED"
        if (msaConnector.node.findChildByType(MSATokenElementTypes.ENCRYPTED) != null) {

            encrypted = ":ENCRYPTED"
        }

        val connectors = mutableListOf<String>()

        for (msaConnectTarget in msaConnector.connectTargetList) {
            val sourcePort = msaConnector.connectSource.qualifiedIdentifier.portInstanceName.referencedPortElement ?: return emptyList()
            val sourcePortIdentifiers = PortElementGenerator.createPortIdentifiers(sourcePort)

            val targetPort = msaConnectTarget.qualifiedIdentifier.portInstanceName.referencedPortElement ?: continue

            sourcePortIdentifiers.filter { sourcePort.direction != targetPort.direction }.forEach {

                val targetPortIdentifiers = PortElementGenerator.createPortIdentifiers(targetPort)
                //<@connector p1="${start_port}" p2="${target_port}" relationship_type="${relationship_type}" />

                for (targetPortIdentifier in targetPortIdentifiers) {
                    val connector_model = mutableMapOf<String, Any>()
                    connector_model.put("relationship_type", encrypted)
                    connector_model.put("element_offset", msaConnector.textOffset)

                    if (targetPort.direction == "IN") {
                        connector_model.put("start_port", it)
                        connector_model.put("target_port", targetPortIdentifier)
                    } else {
                        connector_model.put("start_port", targetPortIdentifier)
                        connector_model.put("target_port", it)
                    }

                    val extras = mutableMapOf<String, String>()
                    extras.put("element_offset", msaConnector.textOffset.toString())
                    extras.put("file_path", msaConnector.containingFile.virtualFile.canonicalPath.orEmpty())
                    connector_model.put("extra_arguments", extras)

                    val connector = FreeMarker.instance.generateModelOutput("ToGraph/ConnectorMacro.ftl", connector_model)
                    connectors.add(connector)
                }
            }
        }

        return connectors
    }

    override fun generate(psiElement: PsiElement): Any? {

        if (psiElement is MSAConnector) {

            return generateConnectorElement(psiElement)
        }
        return null
    }
}