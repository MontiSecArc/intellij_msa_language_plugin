package de.monticore.lang.montisecarc.stubs

import de.monticore.lang.montisecarc.stubs.elements.MSAComponentDeclarationStubElementType
import de.monticore.lang.montisecarc.stubs.elements.MSAComponentInstanceStubElementType
import de.monticore.lang.montisecarc.stubs.elements.MSAPortStubElementType

/**
* Copyright 2016 thomasbuning
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
fun factory(name: String): MSAStubElementType<*, *> = when (name) {
    "PORT_ELEMENT"            -> MSAPortStubElementType
    "COMPONENT_DECLARATION"   -> MSAComponentDeclarationStubElementType
    "COMPONENT_INSTANCE"      -> MSAComponentInstanceStubElementType
    else               -> {
        throw IllegalArgumentException("Unknown element $name")
    }
}