package de.monticore.lang.montisecarc.formatting

import com.intellij.formatting.Block
import com.intellij.formatting.Spacing
import com.intellij.lang.ASTNode
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.formatter.common.AbstractBlock
import de.monticore.lang.montisecarc.psi.MSACompositeElementTypes
import de.monticore.lang.montisecarc.psi.MSATokenElementTypes

/**
 *  Copyright 2016 thomasbuning

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
class MSASpacingProcessor(val child1: Block?, val child2: Block, val myNode: ASTNode, val cmSettings: CommonCodeStyleSettings) {


    fun getSpacing(): Spacing? {

        if (child1 !is AbstractBlock || child2 !is AbstractBlock) {
            return null
        }

        val node2 = child2.node
        val type2 = node2.elementType

        val node1 = child1.node
        val type1 = node1.elementType

        if (MSATokenElementTypes.KEYWORDS.contains(type1)) {

            return strictOneSpace()
        }

        if(type1 == MSACompositeElementTypes.STEREOTYPE) {

            return oneSpace()
        }

        if(type1 == MSACompositeElementTypes.COMPONENT_SIGNATURE && type2 == MSACompositeElementTypes.COMPONENT_BODY) {

            return oneSpace()
        }

        if(type1 == MSATokenElementTypes.COMMA && type2 == MSACompositeElementTypes.PORT_ELEMENT) {

            return addLineBreak(1)
        }

        if(type1 == MSACompositeElementTypes.PACKAGE_CLAUSE || type1 == MSACompositeElementTypes.IMPORT_DECLARATION) {

            return addLineBreak(2)
        }

        if(type2 == MSATokenElementTypes.SEMICOLON || type2 == MSATokenElementTypes.COMMA) {

            return noSpace()
        }

        if(type1 == MSACompositeElementTypes.PORT_DECLARATION || type1 == MSACompositeElementTypes.COMPONENT_INSTANCE_DECLARATION) {

            return addLineBreak(1)
        }

        if(type1 == MSACompositeElementTypes.SUPPRESS_ANNOTATION) {

            if(type2 == MSATokenElementTypes.COMPONENT || type2 == MSATokenElementTypes.PORT ) {

                return addLineBreak(1)
            } else {

                return oneSpace()
            }
        }

        return null
    }

    fun noSpace(): Spacing {
        return Spacing.createSpacing(0, 0, 0, cmSettings.KEEP_LINE_BREAKS, 0)
    }

    fun oneSpace(): Spacing {
        return Spacing.createSpacing(1, 1, 0, cmSettings.KEEP_LINE_BREAKS, cmSettings.KEEP_BLANK_LINES_IN_CODE)
    }

    fun strictOneSpace(): Spacing {
        return Spacing.createSpacing(1, 1, 0, false, 0)
    }

    fun addLineBreak(number: Int): Spacing {
        return Spacing.createSpacing(0, 0, number, false, cmSettings.KEEP_BLANK_LINES_IN_CODE)
    }
}