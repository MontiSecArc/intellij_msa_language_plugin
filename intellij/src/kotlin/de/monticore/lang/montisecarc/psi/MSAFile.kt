package de.monticore.lang.montisecarc.psi

import com.intellij.extapi.psi.PsiFileBase
import com.intellij.lang.Language
import com.intellij.openapi.fileTypes.FileType
import com.intellij.psi.FileViewProvider
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import de.monticore.lang.montisecarc.MSAFileType
import de.monticore.lang.montisecarc.MSALanguage
import org.jetbrains.annotations.Nullable

/**
 *  Copyright 2016 thomasbuning

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

class MSAFile(viewProvider: FileViewProvider) : PsiFileBase(viewProvider, MSALanguage.instance) {
    override fun getFileType(): FileType {
        return MSAFileType.instance
    }

    @Nullable
    fun getPackage(): MSAPackageClause? = findChildByClass(MSAPackageClause::class.java)
}