package de.monticore.lang.montisecarc.psi.impl.mixin

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import de.monticore.lang.montisecarc.psi.MSAComponentInstanceDeclaration
import de.monticore.lang.montisecarc.psi.impl.MSAStubbedNamedElementImpl
import de.monticore.lang.montisecarc.stubs.elements.MSAComponentInstanceStub

/**
 * Created by thomasbuning on 28.09.16.
 */
abstract class MSAComponentInstanceImplMixin: MSAStubbedNamedElementImpl<MSAComponentInstanceStub>, MSAComponentInstanceDeclaration {

    constructor(node: ASTNode) : super(node)

    constructor(stub: MSAComponentInstanceStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

    private val foundPolicyViolations = mutableListOf<String>()

    override fun addPolicyViolation(violation: String) {

        foundPolicyViolations.add(violation)
    }

    override fun getPolicyViolations(): List<String> {

        return foundPolicyViolations
    }
}