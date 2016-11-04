package de.monticore.lang.montisecarc.formatting

import com.intellij.formatting.Wrap
import com.intellij.formatting.WrapType
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.Key
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.psi.formatter.WrappingUtil
import com.intellij.psi.tree.TokenSet
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
class MSAWrappingProcessor {

    companion object {
        private val  MSA_ARGUMENT_LIST_WRAP_KEY = Key.create<Wrap>("ARGUMENT_LIST_WRAP_KEY")

        fun createChildWrap(child: ASTNode, defaultWrap: Wrap, myNode: ASTNode, cmSettings: CommonCodeStyleSettings): Wrap {
            val childType = child.elementType
            val elementType = myNode.elementType

            if (childType === MSATokenElementTypes.COMMA || childType === MSATokenElementTypes.SEMICOLON) return defaultWrap

            if (elementType === MSACompositeElementTypes.PORT_DECLARATION) {
                if (cmSettings.CALL_PARAMETERS_WRAP != CommonCodeStyleSettings.DO_NOT_WRAP) {
                    val wrap: Wrap?
                    // First, do persistent object management.
                    if (child === myNode.firstChildNode) {
                        val children = myNode.getChildren(TokenSet.create(MSACompositeElementTypes.PORT_ELEMENT))
                        if (children.size >= 7) { // Approximation; SQUIRREL_style uses dynamic programming with cost-based analysis to choose.
                            wrap = Wrap.createWrap(WrapType.ALWAYS, true)
                        } else {
                            wrap = Wrap.createWrap(WrapType.NORMAL, true) // NORMAL,CHOP_DOWN_IF_LONG
                        }
                        if (myNode.lastChildNode !== child) {
                            myNode.putUserData(MSA_ARGUMENT_LIST_WRAP_KEY, wrap)
                        }
                    } else {
                        wrap = myNode.getUserData<Wrap>(MSA_ARGUMENT_LIST_WRAP_KEY)

                        if (myNode.lastChildNode === child) {
                            myNode.putUserData<Wrap>(MSA_ARGUMENT_LIST_WRAP_KEY, null)
                        }
                    }
                    // Second, decide what object to return.
                    return wrap ?: Wrap.createWrap(WrappingUtil.getWrapType(cmSettings.CALL_PARAMETERS_WRAP), false)
                }
            }

            return defaultWrap
        }
    }
}