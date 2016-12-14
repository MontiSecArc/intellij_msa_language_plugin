package de.monticore.lang.montisecarc.generator

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.psi.PsiFile
import java.io.File
import java.io.InputStream

/**
 * Copyright 2016 Thomas Buning
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
abstract class FileGenerator : Generator() {

    private fun InputStream.toFile(path: String) {
        use { input ->
            File(path).outputStream().use { input.copyTo(it) }
        }
    }

    protected fun getFileName(psiFile: PsiFile): String = "${psiFile.name.substringBeforeLast(".")}${if (!getSuffix().isNullOrEmpty()) "${getSuffix()}" else ""}${getExtension()}"

    override fun save(parseFile: PsiFile) {

        if (generatedInputStream != null) {
            val basePath = parseFile.project.basePath?.orEmpty()
            val presentableUrl = "$basePath/gen/${getFileName(parseFile)}"
            generatedInputStream!!.toFile(presentableUrl)
            Notifications.Bus.notify(Notification("MSA", "Success", "${getDisplayName()} generated: $presentableUrl", NotificationType.INFORMATION))
        }
    }

}