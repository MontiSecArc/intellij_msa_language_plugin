package de.monticore.lang.montisecarc.actions

import com.intellij.ide.fileTemplates.DefaultTemplatePropertiesProvider
import com.intellij.ide.fileTemplates.FileTemplate
import com.intellij.openapi.util.text.StringUtil
import com.intellij.psi.PsiDirectory
import com.intellij.util.PathUtil
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.util.*

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
class MSATemplatePropertiesProvider: DefaultTemplatePropertiesProvider {
    val MSA_PACKAGE_NAME = "MSA_" + FileTemplate.ATTRIBUTE_PACKAGE_NAME
    override fun fillProperties(directory: PsiDirectory?, properties: Properties?) {


        val packageForDirectory = suggestPackageForDirectory(directory)
        properties?.setProperty(MSA_PACKAGE_NAME, packageForDirectory)
    }

    @NotNull
    fun suggestPackageForDirectory(@Nullable directory: PsiDirectory?): String {
        return getLocalPackageName(directory?.name ?: "")
    }

    fun getLocalPackageName(@NotNull importPath: String): String {
        val fileName = if (!StringUtil.endsWithChar(importPath, '/') && !StringUtil.endsWithChar(importPath, '\\'))
            PathUtil.getFileName(importPath)
        else
            ""
        var name: StringBuilder? = null
        for (i in 0..fileName.length - 1) {
            val c = fileName[i]
            if (!(Character.isLetter(c) || c == '_' || i != 0 && Character.isDigit(c))) {
                if (name == null) {
                    name = StringBuilder(fileName.length)
                    name.append(fileName, 0, i)
                }
                name.append('_')
            } else if (name != null) {
                name.append(c)
            }
        }
        return if (name == null) fileName else name.toString()
    }
}