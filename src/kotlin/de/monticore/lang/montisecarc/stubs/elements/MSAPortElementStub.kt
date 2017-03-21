package de.monticore.lang.montisecarc.stubs.elements

import com.intellij.psi.stubs.*
import de.monticore.lang.montisecarc.psi.MSAPortElement
import de.monticore.lang.montisecarc.psi.impl.MSAPortElementImpl
import de.monticore.lang.montisecarc.stubs.MSAElementStub
import de.monticore.lang.montisecarc.stubs.MSANamedStubElementType
import de.monticore.lang.montisecarc.stubs.index.MSAPortIndex

/**
* Copyright 2017 thomasbuning
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
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

        val stubName = stub.name ?: stub.type?.decapitalize()
        stubName.let { if(!it.isNullOrEmpty()) { sink.occurrence(MSAPortIndex.KEY, it!!)} }
    }
}


class MSAPortElementStub(
        parent: StubElement<*>?,
        elementType: IStubElementType<*, *>,
        val name: String?,
        val type: String?,
        val directionIsIn: Boolean
) : MSAElementStub<MSAPortElement>(parent, elementType)