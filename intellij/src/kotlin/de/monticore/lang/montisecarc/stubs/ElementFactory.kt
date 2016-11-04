package de.monticore.lang.montisecarc.stubs

import de.monticore.lang.montisecarc.stubs.elements.MSAComponentDeclarationStubElementType
import de.monticore.lang.montisecarc.stubs.elements.MSAPortStubElementType

/**
 * Created by thomasbuning on 26.09.16.
 */
fun factory(name: String): MSAStubElementType<*, *> = when (name) {
    "PORT_ELEMENT"            -> MSAPortStubElementType
    "COMPONENT_DECLARATION"   -> MSAComponentDeclarationStubElementType
    else               -> {
        throw IllegalArgumentException("Unknown element $name")
    }
}