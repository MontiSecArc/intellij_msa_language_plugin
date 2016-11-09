package de.monticore.lang.montisecarc.psi.util

import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.PsiUtilCore

/**
 * Created by thomasbuning on 27.09.16.
 */
inline fun <reified T : PsiElement> PsiElement.parentOfType(strict: Boolean = true, minStartOffset: Int = -1): T? =
        PsiTreeUtil.getParentOfType(this, T::class.java, strict, minStartOffset)

inline fun <reified T : PsiElement> PsiElement.childOfType(strict: Boolean = true): T? =
        PsiTreeUtil.findChildOfType(this, T::class.java, strict)

inline fun <reified T : PsiElement> PsiElement.descendentsOfType(): Collection<T> =
        PsiTreeUtil.findChildrenOfType(this, T::class.java)


/**
 * Finds first sibling that is neither comment, nor whitespace before given element.
 */
fun PsiElement?.getPrevNonCommentSibling(): PsiElement? =
        PsiTreeUtil.skipSiblingsBackward(this, PsiWhiteSpace::class.java, PsiComment::class.java)

/**
 * Finds first sibling that is neither comment, nor whitespace after given element.
 */
fun PsiElement?.getNextNonCommentSibling(): PsiElement? =
        PsiTreeUtil.skipSiblingsForward(this, PsiWhiteSpace::class.java, PsiComment::class.java)




/**
 * Finds two edge leaf PSI elements within given range.
 */
private fun PsiFile.getElementRange(startOffset: Int, endOffset: Int): Pair<PsiElement, PsiElement>? {
    val element1 = findElementAtIgnoreWhitespaceBefore(startOffset) ?: return null
    val element2 = findElementAtIgnoreWhitespaceAfter(endOffset - 1) ?: return null

    // Elements have crossed (for instance when selection was inside single whitespace block)
    if (element1.textRange.startOffset >= element2.textRange.endOffset) return null

    return element1 to element2
}

/**
 * Finds a leaf PSI element at the specified offset from the start of the text range of this node.
 * If found element is whitespace, returns its next non-whitespace sibling.
 */
private fun PsiFile.findElementAtIgnoreWhitespaceBefore(offset: Int): PsiElement? {
    val element = findElementAt(offset)
    if (element is PsiWhiteSpace) {
        return findElementAt(element.getTextRange().endOffset)
    }
    return element
}

/**
 * Finds a leaf PSI element at the specified offset from the start of the text range of this node.
 * If found element is whitespace, returns its previous non-whitespace sibling.
 */
private fun PsiFile.findElementAtIgnoreWhitespaceAfter(offset: Int): PsiElement? {
    val element = findElementAt(offset)
    if (element is PsiWhiteSpace) {
        return findElementAt(element.getTextRange().startOffset - 1)
    }
    return element
}

/**
 * Finds child of [parent] of which given element is descendant.
 */
private fun PsiElement.getTopmostParentInside(parent: PsiElement): PsiElement {
    if (parent == this) return this

    var element = this
    while (parent != element.parent) {
        element = element.parent
    }
    return element
}

private fun collectElements(start: PsiElement, stop: PsiElement?, pred: (PsiElement) -> Boolean): Array<out PsiElement> {
    val list = mutableListOf<PsiElement>()
    var current = start
    while (current != stop) {
        if (pred(current)) {
            list.add(current)
        }
        current = current.nextSibling
    }
    return PsiUtilCore.toPsiElementArray(list)
}