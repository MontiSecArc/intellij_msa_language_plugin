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
import de.monticore.lang.montisecarc.stubs.elements.MSAPortElementStub

/**
 * Created by thomasbuning on 28.09.16.
 */
abstract class MSAPortElementImplMixin: MSAStubbedNamedElementImpl<MSAPortElementStub>, MSAPortElement {

    constructor(node: ASTNode) : super(node)

    constructor(stub: MSAPortElementStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

    override fun getEnclosingComponent(): MSAComponentDeclaration? {

        val msaComponentBody = PsiTreeUtil.getParentOfType(this, MSAComponentBody::class.java)
        return PsiTreeUtil.getParentOfType(msaComponentBody, MSAComponentDeclaration::class.java)
    }

    override fun getName(): String? {

        val portName = this.portInstanceName?.text ?: this.referenceType?.text?.decapitalize()
        return portName
    }
}