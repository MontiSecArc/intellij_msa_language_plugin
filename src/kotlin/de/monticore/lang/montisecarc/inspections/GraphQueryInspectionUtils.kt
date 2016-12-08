package de.monticore.lang.montisecarc.analyzer.inspections

import org.neo4j.graphdb.Node
/**
 * Copyright 2016 Thomas Buning
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
object GraphQueryInspectionUtils {


}

val elementsWithHighlight = listOf("Port", "Instance", "Component")

fun Node.graphElementCanBeHighlighted(): Boolean = elementsWithHighlight.fold(false) { initial, element -> initial || this.nodeHasLabelWithName(element) }

fun Node.nodeHasLabelWithName(name: String): Boolean = this.labels.any {
    it.name() == name
}