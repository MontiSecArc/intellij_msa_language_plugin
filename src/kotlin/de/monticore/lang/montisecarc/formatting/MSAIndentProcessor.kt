package de.monticore.lang.montisecarc.formatting

import com.intellij.formatting.Indent
import com.intellij.lang.ASTNode
import com.intellij.psi.*
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

class MSAIndentProcessor {

    companion object {

        fun getChildIndent(node: ASTNode): Indent {

            val elementType = node.elementType
            val prevSibling = UsefulPsiTreeUtil.getPrevSiblingSkipWhiteSpacesAndComments(node)
            val prevSiblingType = prevSibling?.elementType
            val parent = node.treeParent
            val parentType = parent?.elementType

            if (parent == null || parent.treeParent == null) {
                return Indent.getNoneIndent()
            }

            if (parentType === MSACompositeElementTypes.COMPONENT_BODY) {
                val psi = node.psi
                if (psi.parent is PsiFile || elementType == MSATokenElementTypes.RBRACE) {
                    return Indent.getNoneIndent()
                }
                return Indent.getContinuationIndent()
            }

            if (elementType === MSATokenElementTypes.LPAREN && parentType === MSACompositeElementTypes.PORT_DECLARATION) {
                return Indent.getContinuationWithoutFirstIndent()
            }
            if (elementType === MSATokenElementTypes.RPAREN && parentType === MSACompositeElementTypes.PORT_DECLARATION) {
                if (prevSiblingType === MSACompositeElementTypes.PORT_ELEMENT) {
                    val children = prevSibling!!.getChildren(null)
                    val n = children.size
                    if (n > 2 && children[n - 1] is PsiErrorElement && children[n - 2].elementType === MSATokenElementTypes.COMMA) {
                        return Indent.getContinuationWithoutFirstIndent()
                    }
                }
            }
            if (parentType === MSACompositeElementTypes.PORT_DECLARATION && elementType != MSACompositeElementTypes.SUPPRESS_ANNOTATION && prevSiblingType != MSACompositeElementTypes.SUPPRESS_ANNOTATION) {
                /*val prev = UsefulPsiTreeUtil.getPrevSiblingSkipWhiteSpacesCommentsAndComma(node)
                if(prev == null || prev.elementType != MSATokenElementTypesPORT_ELEMENT) {
                    return Indent.getNoneIndent()
                }*/
                return Indent.getContinuationWithoutFirstIndent()
            }

            return Indent.getNoneIndent()
        }
    }
}

class UsefulPsiTreeUtil {

    companion object {

        fun isWhitespaceOrComment(element: PsiElement): Boolean {
            return element is PsiWhiteSpace || element is PsiComment
        }

        fun getPrevSiblingSkipWhiteSpacesAndComments(sibling: ASTNode?): ASTNode? {
            if (sibling == null) return null
            var result: ASTNode? = sibling.treePrev

            while (result != null && isWhitespaceOrComment(result.psi)) {
                result = result.treePrev
            }
            return result
        }
    }
}
