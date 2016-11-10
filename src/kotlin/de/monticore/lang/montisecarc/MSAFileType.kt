package de.monticore.lang.montisecarc

import com.intellij.openapi.fileTypes.LanguageFileType
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.CharsetToolkit
import com.intellij.openapi.vfs.VirtualFile
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable
import java.nio.charset.Charset
import javax.swing.Icon

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

class MSAFileType: LanguageFileType(MSALanguage.instance) {

    private object Holder { val INSTANCE = MSAFileType() }

    companion object Factory {

        val instance: MSAFileType by lazy { Holder.INSTANCE }
        val EXTENSION = "secarc"
    }

    override fun getIcon(): Icon {
        return MSAIcons.FILE
    }

    override fun getName(): String {
        return "MontiSecArc"
    }

    override fun getDefaultExtension(): String {
        return EXTENSION
    }

    override fun getDescription(): String {
        return "MontiSec Arc Language File"
    }

    override fun getCharset(@NotNull file: VirtualFile, @NotNull content: ByteArray): String {
        return CharsetToolkit.UTF8
    }

    override fun extractCharsetFromFileContent(project: Project?, file: VirtualFile?, content: CharSequence): Charset {
        return CharsetToolkit.UTF8_CHARSET
    }
}