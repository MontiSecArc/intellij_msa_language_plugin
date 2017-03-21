package de.monticore.lang.montisecarc.psi.util

import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType

/**
* Copyright 2017 thomasbuning
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

/**
 * Accounts for text-range relative to some ancestor (or the node itself) of the
 * given node
 */
fun PsiElement.rangeRelativeTo(ancestor: PsiElement): TextRange {
    check(ancestor.textRange.contains(textRange))
    return textRange.shiftRight(-ancestor.textRange.startOffset)
}


/**
 * Checks whether this node contains [descendant] one
 */
fun PsiElement.contains(descendant: PsiElement?): Boolean {
    if (descendant == null) return false
    return descendant.ancestors.any { it === this }
}


private val PsiElement.ancestors: Sequence<PsiElement> get() = generateSequence(this) { it.parent }

private fun walkUp(descendant: PsiElement, ancestor: PsiElement): Sequence<PsiElement> =
        descendant.ancestors.takeWhile { it !== ancestor } + ancestor

/**
 * Extracts node's element type
 */
val PsiElement.elementType: IElementType
    get() = node.elementType