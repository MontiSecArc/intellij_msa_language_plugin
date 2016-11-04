package de.monticore.lang.montisecarc

import com.intellij.psi.tree.IStubFileElementType
import de.monticore.lang.montisecarc.stubs.MSAFileStub

/**
 * Created by thomasbuning on 26.09.16.
 */
object MSAFileElementType : IStubFileElementType<MSAFileStub>(MSALanguage.instance) {
    override fun getStubVersion(): Int = 13
}