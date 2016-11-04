package de.monticore.lang.montisecarc.stubs.elements

import com.intellij.psi.stubs.*
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSAPortElement
import de.monticore.lang.montisecarc.psi.impl.MSAComponentDeclarationImpl
import de.monticore.lang.montisecarc.psi.impl.MSAPortElementImpl
import de.monticore.lang.montisecarc.stubs.MSANamedElementStub
import de.monticore.lang.montisecarc.stubs.MSANamedStubElementType
import de.monticore.lang.montisecarc.stubs.index.MSAComponentDeclarationIndex
import de.monticore.lang.montisecarc.stubs.index.MSAPortIndex

/**
 * Created by thomasbuning on 26.09.16.
 */

object MSAComponentDeclarationStubElementType : MSANamedStubElementType<MSAComponentDeclarationStub, MSAComponentDeclaration>("COMPONENT_DECLARATION") {


    override fun serialize(stub: MSAComponentDeclarationStub, dataStream: StubOutputStream) = with(dataStream) {

        writeName(stub.name)
    }

    override fun createStub(psi: MSAComponentDeclaration, parentStub: StubElement<*>?): MSAComponentDeclarationStub {

        val name = psi.instanceName
        return MSAComponentDeclarationStub(parentStub, this, name)
    }

    override fun createPsi(stub: MSAComponentDeclarationStub): MSAComponentDeclaration =
            MSAComponentDeclarationImpl(stub, this)

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): MSAComponentDeclarationStub =
            MSAComponentDeclarationStub(parentStub, this, dataStream.readNameAsString())

    override fun indexStub(stub: MSAComponentDeclarationStub, sink: IndexSink) {
        super.indexStub(stub, sink)

        stub.name.let { if(!it.isNullOrEmpty()) { sink.occurrence(MSAComponentDeclarationIndex.KEY, it!!)} }
    }
}


class MSAComponentDeclarationStub(
        parent: StubElement<*>?,
        elementType: IStubElementType<*, *>,
        name: String?
) : MSANamedElementStub<MSAComponentDeclaration>(parent, elementType, name ?: "")