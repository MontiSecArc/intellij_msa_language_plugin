package de.monticore.lang.montisecarc.psi.impl

import com.intellij.ide.projectView.PresentationData
import com.intellij.lang.ASTNode
import com.intellij.navigation.ItemPresentation
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType
import de.monticore.lang.montisecarc.psi.MSAFile
import de.monticore.lang.montisecarc.psi.MSATokenElementTypes
import de.monticore.lang.montisecarc.stubs.MSAElementStub

/**
 * Created by thomasbuning on 28.09.16.
 */
abstract class MSAStubbedNamedElementImpl<StubT> : MSAStubbedElementImpl<StubT>,
        PsiElement
where StubT : MSAElementStub<*> {

    constructor(node: ASTNode) : super(node)

    constructor(stub: StubT, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

    protected open val nameElement: PsiElement?
        get() = findChildByType(MSATokenElementTypes.ID)

    override fun getNavigationElement(): PsiElement = nameElement ?: this

    override fun getTextOffset(): Int = nameElement?.textOffset ?: super.getTextOffset()

    override fun getPresentation(): ItemPresentation {
        val mod = containingFile as MSAFile
        val loc = mod.getPackage().toString()
        return PresentationData(
                name, "(in $loc)", getIcon(0), null)
    }
}