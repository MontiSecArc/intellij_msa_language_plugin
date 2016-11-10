package de.monticore.lang.montisecarc.stubs

import com.intellij.psi.stubs.IStubElementType
import com.intellij.psi.stubs.NamedStubBase
import com.intellij.psi.stubs.StubElement
import de.monticore.lang.montisecarc.psi.MSANamedElement

/**
 * Created by thomasbuning on 26.09.16.
 */
abstract class MSANamedElementStub<PsiT : MSANamedElement>(
        parent: StubElement<*>?,
        elementType: IStubElementType<*, *>,
        name: String
) : NamedStubBase<PsiT>(parent, elementType, name)