package de.monticore.lang.montisecarc.generator

import com.intellij.psi.PsiElement
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

        val sourcePort = msaConnector.connectSource.qualifiedIdentifier.portInstanceName.referencedPortElement ?: return emptyList()
        val sourcePortIdentifier = PortElementGenerator.createPortIdentifier(sourcePort)
        for (msaConnectTarget in msaConnector.connectTargetList) {

            val targetPort = msaConnectTarget.qualifiedIdentifier.portInstanceName.referencedPortElement ?: continue

            val targetPortIdentifier = PortElementGenerator.createPortIdentifier(targetPort)
            //<@connector p1="${start_port}" p2="${target_port}" relationship_type="${relationship_type}" />

            val connector_model = mutableMapOf<String, Any>()
            connector_model.put("relationship_type", encrypted)
            connector_model.put("element_offset", msaConnector.textOffset)

            if (sourcePort.direction == targetPort.direction) {
                continue
            }

            if (targetPort.direction == "IN") {
                connector_model.put("start_port", sourcePortIdentifier)
                connector_model.put("target_port", targetPortIdentifier)
            } else {
                connector_model.put("start_port", targetPortIdentifier)
                connector_model.put("target_port", sourcePortIdentifier)
            }
            val connector = FreeMarker.instance.generateModelOutput("ToGraph/ConnectorMacro.ftl", connector_model)
            connectors.add(connector)
        }

        return connectors
    }

    override fun generate(psiElement: PsiElement): Any? {

        if(psiElement is MSAConnector) {

            return generateConnectorElement(psiElement)
        }
        return null
    }
}