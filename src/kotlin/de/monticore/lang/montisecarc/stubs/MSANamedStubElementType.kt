package de.monticore.lang.montisecarc.stubs

import com.intellij.psi.PsiElement

/**
 * Created by thomasbuning on 26.09.16.
 */
abstract class MSANamedStubElementType<StubT, PsiT>(debugName: String) : MSAStubElementType<StubT, PsiT>(debugName)
where StubT : MSAElementStub<PsiT>,
PsiT  : PsiElement