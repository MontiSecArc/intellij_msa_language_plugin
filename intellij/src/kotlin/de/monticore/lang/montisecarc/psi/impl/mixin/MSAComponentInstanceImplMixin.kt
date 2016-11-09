package de.monticore.lang.montisecarc.psi.impl.mixin

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.psi.*
import de.monticore.lang.montisecarc.psi.impl.MSAStubbedNamedElementImpl
import de.monticore.lang.montisecarc.psi.util.elementType
import de.monticore.lang.montisecarc.stubs.elements.MSAComponentDeclarationStub
import de.monticore.lang.montisecarc.stubs.elements.MSAComponentInstanceStub
import de.monticore.lang.montisecarc.stubs.elements.MSAPortElementStub

/**
 * Created by thomasbuning on 28.09.16.
 */
abstract class MSAComponentInstanceImplMixin: MSAStubbedNamedElementImpl<MSAComponentInstanceStub>, MSAComponentInstanceDeclaration {

    constructor(node: ASTNode) : super(node)

    constructor(stub: MSAComponentInstanceStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)
}