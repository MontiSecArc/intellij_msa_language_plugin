package de.monticore.lang.montisecarc.psi.impl

import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import de.monticore.lang.montisecarc.psi.MSAElementFactory
import de.monticore.lang.montisecarc.psi.MSANamedElement
import de.monticore.lang.montisecarc.psi.MSATokenElementTypes

/**
 * Created by thomasbuning on 26.09.16.
 */
abstract class MSANamedElementImpl(node: ASTNode): MSACompositeElementImpl(node), MSANamedElement {

    protected open val nameElement: PsiElement?
        get() = findChildByType(MSATokenElementTypes.ID)

    override fun getName(): String? = nameElement?.text

    override fun setName(name: String): PsiElement? {
        nameElement?.replace(MSAElementFactory.createIdentifier(project, name))
        return this
    }

    override fun getNavigationElement(): PsiElement = nameElement ?: this

    override fun getTextOffset(): Int = nameElement?.textOffset ?: super.getTextOffset()
}