package de.monticore.lang.montisecarc.psi.impl.mixin

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.psi.MSAComponentBody
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSAComponentSignature
import de.monticore.lang.montisecarc.psi.MSAPortElement
import de.monticore.lang.montisecarc.psi.impl.MSAStubbedNamedElementImpl
import de.monticore.lang.montisecarc.psi.util.elementType
import de.monticore.lang.montisecarc.stubs.elements.MSAComponentDeclarationStub
import de.monticore.lang.montisecarc.stubs.elements.MSAPortElementStub

/**
 * Created by thomasbuning on 28.09.16.
 */
abstract class MSAComponentDeclarationImplMixin: MSAStubbedNamedElementImpl<MSAComponentDeclarationStub>, MSAComponentDeclaration {

    constructor(node: ASTNode) : super(node)

    constructor(stub: MSAComponentDeclarationStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)
}