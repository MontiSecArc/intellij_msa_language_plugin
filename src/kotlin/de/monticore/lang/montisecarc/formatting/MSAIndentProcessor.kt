package de.monticore.lang.montisecarc.formatting

import com.intellij.formatting.Indent
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.Condition
import com.intellij.psi.*
import com.intellij.psi.codeStyle.CommonCodeStyleSettings
import com.intellij.util.Function
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

        fun getChildIndent(node: ASTNode, cmSettings: CommonCodeStyleSettings): Indent {

            val elementType = node.elementType
            val prevSibling = UsefulPsiTreeUtil.getPrevSiblingSkipWhiteSpacesAndComments(node)
            val nextSibling = UsefulPsiTreeUtil.getNextSiblingSkipWhiteSpacesAndComments(node)
            val prevSiblingType = prevSibling?.elementType
            val parent = node.treeParent
            val parentType = parent?.elementType
            val superParent = parent?.treeParent
            val superParentType = superParent?.elementType

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
                    val childs = prevSibling!!.getChildren(null)
                    val n = childs.size
                    if (n > 2 && childs[n - 1] is PsiErrorElement && childs[n - 2].elementType === MSATokenElementTypes.COMMA) {
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


        private fun findChildIndex(children: Array<ASTNode>, offset: Int): Int {
            var i = 0
            val length = children.size
            while (i < length) {
                val child = children[i]
                if (child.textRange.contains(offset)) {
                    return i
                }
                i++
            }

            return -1
        }

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

        fun getPrevSiblingSkipWhiteSpacesCommentsAndComma(sibling: ASTNode?): ASTNode? {

            if (sibling == null) return null
            var result: ASTNode? = sibling.treePrev

            while (result != null && (isWhitespaceOrComment(result.psi) || result.elementType == MSATokenElementTypes.COMMA)) {
                result = result.treePrev
            }
            return result
        }

        fun getNextSiblingSkipWhiteSpacesAndComments(sibling: ASTNode?): ASTNode? {

            if (sibling == null) return null
            var result: ASTNode? = sibling.treeNext

            while (result != null && isWhitespaceOrComment(result.psi)) {
                result = result.treeNext
            }
            return result
        }


        private fun getPrevSiblingSkippingCondition(sibling: PsiElement?,
                                                    condition: Condition<PsiElement>,
                                                    strictly: Boolean): PsiElement? {
            return getSiblingSkippingCondition(sibling, Function<com.intellij.psi.PsiElement, com.intellij.psi.PsiElement> { element -> element.prevSibling }, condition, strictly)
        }

        private fun getSiblingSkippingCondition(sibling: PsiElement?,
                                                nextSibling: Function<PsiElement, PsiElement>,
                                                condition: Condition<PsiElement>,
                                                strictly: Boolean): PsiElement? {
            if (sibling == null) return null
            if (sibling is PsiFile) return sibling
            var result: PsiElement? = if (strictly) nextSibling.`fun`(sibling) else sibling
            while (result != null && result !is PsiFile && condition.value(result)) {
                result = nextSibling.`fun`(result)
            }
            return result
        }
    }
}
