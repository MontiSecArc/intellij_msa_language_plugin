package de.monticore.lang.montisecarc.stubs.elements

import com.intellij.psi.stubs.*
import de.monticore.lang.montisecarc.psi.MSAPortElement
import de.monticore.lang.montisecarc.psi.impl.MSAPortElementImpl
import de.monticore.lang.montisecarc.stubs.MSANamedElementStub
import de.monticore.lang.montisecarc.stubs.MSANamedStubElementType
import de.monticore.lang.montisecarc.stubs.index.MSAPortIndex

/**
 * Created by thomasbuning on 26.09.16.
 */

object MSAPortStubElementType : MSANamedStubElementType<MSAPortElementStub, MSAPortElement>("PORT_ELEMENT") {


    override fun serialize(stub: MSAPortElementStub, dataStream: StubOutputStream) = with(dataStream) {

        writeName(stub.name)
        writeName(stub.type)
        writeBoolean(stub.directionIsIn)
    }

    override fun createStub(psi: MSAPortElement, parentStub: StubElement<*>?): MSAPortElementStub {

        var directionIsIn = false
        if (psi.direction == "IN") {
            directionIsIn = true
        }
        return MSAPortElementStub(parentStub, this, psi.portInstanceName?.text, psi.javaClassReference?.text, directionIsIn)
    }

    override fun createPsi(stub: MSAPortElementStub): MSAPortElement =
            MSAPortElementImpl(stub, this)

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): MSAPortElementStub =
            MSAPortElementStub(parentStub, this,
                    dataStream.readNameAsString(), dataStream.readNameAsString(), dataStream.readBoolean())

    override fun indexStub(stub: MSAPortElementStub, sink: IndexSink) {
        super.indexStub(stub, sink)

        val stubName = stub.name ?: stub.type?.decapitalize()
        stubName.let { if(!it.isNullOrEmpty()) { sink.occurrence(MSAPortIndex.KEY, it!!)} }
    }
}


class MSAPortElementStub(
        parent: StubElement<*>?,
        elementType: IStubElementType<*, *>,
        name: String?,
        val type: String?,
        val directionIsIn: Boolean
) : MSANamedElementStub<MSAPortElement>(parent, elementType, name ?: type?.decapitalize() ?: "")