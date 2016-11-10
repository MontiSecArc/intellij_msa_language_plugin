package de.monticore.lang.montisecarc.stubs

import com.intellij.psi.stubs.IndexSink
import de.monticore.lang.montisecarc.psi.MSANamedElement
import de.monticore.lang.montisecarc.stubs.index.MSANamedElementIndex

/**
 * Created by thomasbuning on 26.09.16.
 */
abstract class MSANamedStubElementType<StubT, PsiT>(debugName: String) : MSAStubElementType<StubT, PsiT>(debugName)
where StubT : MSANamedElementStub<PsiT>,
PsiT  : MSANamedElement {

    override fun indexStub(stub: StubT, sink: IndexSink) {
        stub.name?.let { sink.occurrence(MSANamedElementIndex.KEY, it) }
    }

}