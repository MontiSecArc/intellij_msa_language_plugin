package de.monticore.lang.montisecarc.psi.impl.mixin

import com.intellij.lang.ASTNode
import com.intellij.psi.stubs.IStubElementType
import de.monticore.lang.montisecarc.psi.MSAComponentInstanceDeclaration
import de.monticore.lang.montisecarc.psi.impl.MSAStubbedNamedElementImpl
import de.monticore.lang.montisecarc.stubs.elements.MSAComponentInstanceStub

/**
* Copyright 2017 thomasbuning
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
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