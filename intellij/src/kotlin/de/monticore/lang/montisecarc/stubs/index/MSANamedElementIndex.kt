package de.monticore.lang.montisecarc.stubs.index

import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import de.monticore.lang.montisecarc.MSAFileElementType
import de.monticore.lang.montisecarc.psi.MSANamedElement

/**
 * Created by thomasbuning on 26.09.16.
 */
class MSANamedElementIndex : StringStubIndexExtension<MSANamedElement>() {
    companion object {
        val KEY: StubIndexKey<String, MSANamedElement> = StubIndexKey.createIndexKey("de.monticore.lang.montisecarc.stubs.index.MSANamedElementIndex")
    }

    override fun getVersion(): Int = MSAFileElementType.stubVersion

    override fun getKey(): StubIndexKey<String, MSANamedElement> = KEY
}