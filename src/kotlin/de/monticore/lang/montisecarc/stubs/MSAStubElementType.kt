package de.monticore.lang.montisecarc.stubs

import com.intellij.psi.PsiElement
import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.StubElement
import com.intellij.psi.stubs.StubInputStream
import de.monticore.lang.montisecarc.MSALanguage

/**
 * Created by thomasbuning on 26.09.16.
 */
abstract class MSAStubElementType<StubT, PsiT>(
        debugName: String
) : IStubElementType<StubT, PsiT>(debugName, MSALanguage.instance)

where StubT : StubElement<PsiT>,
PsiT  : PsiElement {

    final override fun getExternalId(): String = "MSA.${super.toString()}"

    companion object {


        fun StubInputStream.readNameAsString(): String? = readName()?.string
    }
}