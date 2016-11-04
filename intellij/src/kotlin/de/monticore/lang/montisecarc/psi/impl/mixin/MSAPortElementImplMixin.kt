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
* Copyright 2016 thomasbuning
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