package de.monticore.lang.montisecarc.psi.impl

import com.intellij.extapi.psi.StubBasedPsiElementBase
import com.intellij.lang.ASTNode
import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubBase
import de.monticore.lang.montisecarc.psi.MSACompositeElement
import de.monticore.lang.montisecarc.references.MSAReference

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
abstract class MSAStubbedElementImpl<StubT : StubBase<*>> : StubBasedPsiElementBase<StubT>,
        MSACompositeElement {

    constructor(node: ASTNode) : super(node)

    constructor(stub: StubT, nodeType: IStubElementType<*, *>) : super(stub, nodeType)

    override fun getParent(): PsiElement? = parentByTree

    override fun getReference(): MSAReference? = null

    override fun toString(): String = "${javaClass.simpleName}($elementType)"
}