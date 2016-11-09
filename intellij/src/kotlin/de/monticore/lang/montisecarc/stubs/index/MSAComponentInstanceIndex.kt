package de.monticore.lang.montisecarc.stubs.index

import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import de.monticore.lang.montisecarc.MSAFileElementType
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSAComponentInstanceDeclaration
import de.monticore.lang.montisecarc.psi.MSAPortElement

/**
 * Created by thomasbuning on 28.09.16.
 */
class MSAComponentInstanceIndex: StringStubIndexExtension<MSAComponentInstanceDeclaration>() {

    override fun getVersion(): Int = MSAFileElementType.stubVersion

    override fun getKey(): StubIndexKey<String, MSAComponentInstanceDeclaration> = KEY

    companion object {
        val KEY: StubIndexKey<String, MSAComponentInstanceDeclaration> = StubIndexKey.createIndexKey("de.monticore.lang.montisecarc.stubs.index.MSAComponentInstanceIndex")
    }
}