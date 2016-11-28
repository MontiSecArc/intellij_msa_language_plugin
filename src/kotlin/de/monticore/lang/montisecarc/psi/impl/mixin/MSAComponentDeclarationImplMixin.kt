package de.monticore.lang.montisecarc.psi.impl.mixin

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.impl.MSAStubbedNamedElementImpl
import de.monticore.lang.montisecarc.stubs.elements.MSAComponentDeclarationStub

/**
 * Created by thomasbuning on 28.09.16.
 */
abstract class MSAComponentDeclarationImplMixin: MSAStubbedNamedElementImpl<MSAComponentDeclarationStub>, MSAComponentDeclaration {

    constructor(node: ASTNode) : super(node)

    constructor(stub: MSAComponentDeclarationStub, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

    private val foundPolicyViolations = mutableListOf<String>()

    override fun addPolicyViolation(violation: String) {

        foundPolicyViolations.add(violation)
    }

    override fun getPolicyViolations(): List<String> {

        return foundPolicyViolations
    }

    override fun getSuperComponents() : Array<MSAComponentDeclaration> {

        val superComponents = mutableListOf<MSAComponentDeclaration>()
        var referenceToSuperComponent = componentSignature?.componentExtension?.componentNameWithTypeProjection?.componentName?.references
        while(referenceToSuperComponent != null && referenceToSuperComponent.isNotEmpty()) {

            val superComponent = referenceToSuperComponent[0].resolve()
            if(superComponent != null && superComponent is MSAComponentDeclaration) {

                superComponents.add(superComponent)
                referenceToSuperComponent = superComponent.componentSignature?.componentExtension?.componentNameWithTypeProjection?.componentName?.references
            } else {

                return superComponents.toTypedArray()
            }
        }
        return superComponents.toTypedArray()
    }
}