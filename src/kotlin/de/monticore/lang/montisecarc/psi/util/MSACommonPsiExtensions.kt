package de.monticore.lang.montisecarc.psi.util

import com.intellij.lang.ASTNode
import com.intellij.openapi.module.Module
import com.intellij.openapi.module.ModuleUtilCore
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.IElementType

/**
 * Created by thomasbuning on 27.09.16.
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
 * Accounts for text-range relative to the parent of the element
 */
val PsiElement.parentRelativeRange: TextRange
    get() = rangeRelativeTo(parent)

fun ASTNode.containsEOL(): Boolean = textContains('\r') || textContains('\n')
fun PsiElement.containsEOL(): Boolean = textContains('\r') || textContains('\n')


/**
 * Returns module for this PsiElement.
 *
 * If the element is in a library, returns the module which depends on
 * the library.
 */
val PsiElement.module: Module?
    get() {
        // It's important to look the module for `containingFile` file
        // and not the element itself. Otherwise this will break for
        // elements in libraries.
        return ModuleUtilCore.findModuleForPsiElement(containingFile)
    }

/**
 * Checks whether this node contains [descendant] one
 */
fun PsiElement.contains(descendant: PsiElement?): Boolean {
    if (descendant == null) return false
    return descendant.ancestors.any { it === this }
}


/**
 * Composes tree-path from `this` node to the [other] (inclusive)
 */
fun PsiElement.pathTo(other: PsiElement): Iterable<PsiElement> {
    check(contains(other) || other.contains(this))

    // Check whether we're walking the tree up or down
    return if (other.contains(this))
        walkUp(this, other).toList()
    else
        walkUp(other, this).toList().reversed()
}

private val PsiElement.ancestors: Sequence<PsiElement> get() = generateSequence(this) { it.parent }

private fun walkUp(descendant: PsiElement, ancestor: PsiElement): Sequence<PsiElement> =
        descendant.ancestors.takeWhile { it !== ancestor } + ancestor

/**
 * Extracts node's element type
 */
val PsiElement.elementType: IElementType
    get() = node.elementType