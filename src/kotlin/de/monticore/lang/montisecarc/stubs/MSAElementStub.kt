package de.monticore.lang.montisecarc.stubs

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubBase
import com.intellij.psi.stubs.StubElement

/**
 * Created by thomasbuning on 26.09.16.
 */
abstract class MSAElementStub<PsiT : PsiElement>(
        parent: StubElement<*>?,
        elementType: IStubElementType<*, *>
) : StubBase<PsiT>(parent, elementType)