package de.monticore.lang.montisecarc.stubs.elements

import com.intellij.psi.stubs.*
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSAPortElement
import de.monticore.lang.montisecarc.psi.impl.MSAComponentDeclarationImpl
import de.monticore.lang.montisecarc.psi.impl.MSAPortElementImpl
import de.monticore.lang.montisecarc.stubs.MSANamedElementStub
import de.monticore.lang.montisecarc.stubs.MSANamedStubElementType
import de.monticore.lang.montisecarc.stubs.index.MSAComponentDeclarationIndex
import de.monticore.lang.montisecarc.stubs.index.MSAComponentInstanceDeclarationIndex
import de.monticore.lang.montisecarc.stubs.index.MSAComponentInstanceIndex
import de.monticore.lang.montisecarc.stubs.index.MSAPortIndex

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
object MSAComponentDeclarationStubElementType : MSANamedStubElementType<MSAComponentDeclarationStub, MSAComponentDeclaration>("COMPONENT_DECLARATION") {


    override fun serialize(stub: MSAComponentDeclarationStub, dataStream: StubOutputStream) = with(dataStream) {

        writeName(stub.name)
        writeName(stub.instanceName)
    }

    override fun createStub(psi: MSAComponentDeclaration, parentStub: StubElement<*>?): MSAComponentDeclarationStub {

        val name = psi.componentName
        val instanceName = psi.instanceName
        return MSAComponentDeclarationStub(parentStub, this, name, instanceName)
    }

    override fun createPsi(stub: MSAComponentDeclarationStub): MSAComponentDeclaration =
            MSAComponentDeclarationImpl(stub, this)

    override fun deserialize(dataStream: StubInputStream, parentStub: StubElement<*>?): MSAComponentDeclarationStub =
            MSAComponentDeclarationStub(parentStub, this, dataStream.readNameAsString(), dataStream.readNameAsString())

    override fun indexStub(stub: MSAComponentDeclarationStub, sink: IndexSink) {
        super.indexStub(stub, sink)

        stub.name.let { if(!it.isNullOrEmpty()) { sink.occurrence(MSAComponentDeclarationIndex.KEY, it!!)} }
        stub.instanceName.let { if(!it.isNullOrEmpty()) { sink.occurrence(MSAComponentInstanceDeclarationIndex.KEY, stub.instanceName!!) } }
    }
}


class MSAComponentDeclarationStub(
        parent: StubElement<*>?,
        elementType: IStubElementType<*, *>,
        name: String?,
        val instanceName: String?
) : MSANamedElementStub<MSAComponentDeclaration>(parent, elementType, name ?: "")