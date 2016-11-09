package de.monticore.lang.montisecarc.psi.impl

import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubBase
import de.monticore.lang.montisecarc.psi.MSACompositeElement
import de.monticore.lang.montisecarc.references.MSAReference

/**
 * Created by thomasbuning on 28.09.16.
 */
abstract class MSAStubbedElementImpl<StubT : StubBase<*>> : StubBasedPsiElementBase<StubT>,
        MSACompositeElement {

    constructor(node: ASTNode) : super(node)

    constructor(stub: StubT, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

    override fun getParent(): PsiElement? = parentByTree

    override fun getReference(): MSAReference? = null

    override fun toString(): String = "${javaClass.simpleName}($elementType)"
}