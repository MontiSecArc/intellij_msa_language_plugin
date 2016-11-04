package de.monticore.lang.montisecarc.stubs.index

import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import de.monticore.lang.montisecarc.MSAFileElementType
import de.monticore.lang.montisecarc.psi.MSAPortElement

/**
 * Created by thomasbuning on 28.09.16.
 */
class MSAPortIndex: StringStubIndexExtension<MSAPortElement>() {

    override fun getVersion(): Int = MSAFileElementType.stubVersion

    override fun getKey(): StubIndexKey<String, MSAPortElement> = KEY

    companion object {
        val KEY: StubIndexKey<String, MSAPortElement> = StubIndexKey.createIndexKey("de.monticore.lang.montisecarc.stubs.index.MSAPortIndex")
    }
}