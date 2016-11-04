package de.monticore.lang.montisecarc.psi.impl

import com.intellij.extapi.psi.ASTWrapperPsiElement
import com.intellij.lang.ASTNode
import de.monticore.lang.montisecarc.psi.MSACompositeElement
import de.monticore.lang.montisecarc.references.MSAReference

/**
 * Created by thomasbuning on 26.09.16.
 */
abstract class MSACompositeElementImpl(node: ASTNode) : ASTWrapperPsiElement(node), MSACompositeElement {
    override fun getReference(): MSAReference? = null
}