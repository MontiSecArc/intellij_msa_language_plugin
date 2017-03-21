package de.monticore.lang.montisecarc.stubs.index

import com.intellij.psi.stubs.StringStubIndexExtension
import com.intellij.psi.stubs.StubIndexKey
import de.monticore.lang.montisecarc.MSAFileElementType
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration

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
class MSAComponentDeclarationIndex: StringStubIndexExtension<MSAComponentDeclaration>() {

    override fun getVersion(): Int = MSAFileElementType.stubVersion

    override fun getKey(): StubIndexKey<String, MSAComponentDeclaration> = KEY

    companion object {
        val KEY: StubIndexKey<String, MSAComponentDeclaration> = StubIndexKey.createIndexKey("de.monticore.lang.montisecarc.stubs.index.MSAComponentDeclarationIndex")
    }
}