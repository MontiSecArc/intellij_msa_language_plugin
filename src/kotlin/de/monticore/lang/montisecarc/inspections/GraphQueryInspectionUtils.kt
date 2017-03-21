package de.monticore.lang.montisecarc.inspections

import org.neo4j.graphdb.Node

val elementsWithHighlight = listOf("Port", "Instance", "Component")

fun Node.graphElementCanBeHighlighted(): Boolean = elementsWithHighlight.fold(false) { initial, element -> initial || this.nodeHasLabelWithName(element) }

fun Node.nodeHasLabelWithName(name: String): Boolean = this.labels.any {
    it.name() == name
}