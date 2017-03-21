package de.monticore.lang.montisecarc.generator.graph

import de.monticore.lang.montisecarc.generator.FreeMarker
import de.monticore.lang.montisecarc.generator.MSAGenerator

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
abstract class TrustLevelGenerator: MSAGenerator() {

    companion object {

        fun getTrustLevelIdentifier(trustLevel: Int): String {

            val trustLevelId: String
            if (trustLevel >= 0) {

                trustLevelId = "tP$trustLevel"
            } else {

                trustLevelId = "tM${Math.abs(trustLevel)}"
            }

            return trustLevelId
        }

        fun getTrustLevelString(trustLevel: Int): String {

            val trustLevelString: String
            if (trustLevel >= 0) {

                trustLevelString = "+$trustLevel"
            } else {

                trustLevelString = "-${Math.abs(trustLevel)}"
            }

            return trustLevelString
        }

        fun generateTrustLevelNode(trustLevel: Int): String {
            val model = mutableMapOf<String, Any>()
            model.put("id", Companion.getTrustLevelIdentifier(trustLevel))
            model.put("instance_name", Companion.getTrustLevelString(trustLevel))
            model.put("level", trustLevel)
            return FreeMarker(this.javaClass).generateModelOutput("ToGraph/TrustlevelMacro.ftl", model)
        }

    }

    fun createConnectorString(source: String, target: String): String {

        val connector_model = mutableMapOf<String, Any>()
        connector_model.put("relationship_type", ":TRUST")
        connector_model.put("start_port", source)
        connector_model.put("target_port", target)
        return FreeMarker(this.javaClass).generateModelOutput("ToGraph/ConnectorMacro.ftl", connector_model)
    }

    fun createConnector(trustLevel: Int, componentIdentifier: String): List<String> {

        val trustLevelIdentifier = getTrustLevelIdentifier(trustLevel)
        return listOf(createConnectorString(trustLevelIdentifier, componentIdentifier), createConnectorString(componentIdentifier, trustLevelIdentifier))
    }
}