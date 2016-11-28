package de.monticore.lang.montisecarc.stubs.elements

import com.intellij.psi.stubs.*
import de.monticore.lang.montisecarc.psi.MSAComponentInstanceDeclaration
import de.monticore.lang.montisecarc.psi.impl.MSAComponentInstanceDeclarationImpl
import de.monticore.lang.montisecarc.stubs.MSAElementStub
import de.monticore.lang.montisecarc.stubs.MSANamedStubElementType
import de.monticore.lang.montisecarc.stubs.index.MSAComponentInstanceIndex

/**
* Copyright 2016 thomasbuning
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

object MSAComponentInstanceStubElementType : MSANamedStubElementType<MSAComponentInstanceStub, MSAComponentInstanceDeclaration>("COMPONENT_INSTANCE_DECLARATION") {


    override fun serialize(stub: MSAComponentInstanceStub, dataStream: StubOutputStream) = with(dataStream) {

        writeName(stub.name)
    }

    override fun createStub(psi: MSAComponentInstanceDeclaration, parentStub: StubElement<*>?): MSAComponentInstanceStub {

        val name = psi.componentInstanceNameList.map { it.text }.joinToString(",")
        return MSAComponentInstanceStub(parentStub, this, name)
    }

    override fun createPsi(stub: MSAComponentInstanceStub): MSAComponentInstanceDeclaration =
            MSAComponentInstanceDeclarationImpl(stub, this)

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): MSAComponentInstanceStub =
            MSAComponentInstanceStub(parentStub, this, dataStream.readNameAsString())

    override fun indexStub(stub: MSAComponentInstanceStub, sink: IndexSink) {

        stub.name?.split(",")?.forEach { if(!it.isNullOrEmpty()) { sink.occurrence(MSAComponentInstanceIndex.KEY, it)} }
    }
}


class MSAComponentInstanceStub(
        parent: StubElement<*>?,
        elementType: IStubElementType<*, *>,
        val name: String?
) : MSAElementStub<MSAComponentInstanceDeclaration>(parent, elementType)