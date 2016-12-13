package de.monticore.lang.montisecarc.generator.graph

import com.intellij.psi.PsiElement
import de.monticore.lang.montisecarc.generator.FreeMarker
import de.monticore.lang.montisecarc.generator.MSAGenerator
import de.monticore.lang.montisecarc.generator.graph.ComponentDeclarationGenerator
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
class PortElementConnectorGenerator : MSAGenerator() {

    companion object {
        fun getModel(direction: String, componentIdentifier: String, portIdentifier: String): String {

            val connector_model = mutableMapOf<String, Any>()
            if (direction == "OUT") {

                connector_model.put("start_port", componentIdentifier)
                connector_model.put("target_port", portIdentifier)
                connector_model.put("relationship_type", ":DECLARES_OUT")
            } else {

                connector_model.put("start_port", portIdentifier)
                connector_model.put("target_port", componentIdentifier)
                connector_model.put("relationship_type", ":DECLARES_IN")
            }

            return FreeMarker.instance.generateModelOutput("ToGraph/ConnectorMacro.ftl", connector_model)
        }
    }

    override fun generate(psiElement: PsiElement): Any? {

        if (psiElement is MSAPortElement) {

            val portIdentifier = PortElementGenerator.createPortIdentifiers(psiElement)

            if (psiElement.enclosingComponent != null) {
                val componentIdentifier = ComponentDeclarationGenerator.createComponentIdentifier(psiElement.enclosingComponent!!)

                return portIdentifier.map { getModel(psiElement.direction, componentIdentifier, it) }
            }
        }
        return null
    }
}