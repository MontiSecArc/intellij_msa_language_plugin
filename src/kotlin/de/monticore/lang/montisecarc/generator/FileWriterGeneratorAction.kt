package de.monticore.lang.montisecarc.generator.docker.actions

import com.intellij.ide.plugins.PluginManager
import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.openapi.application.ApplicationManager
import de.monticore.lang.montisecarc.generator.Generator
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
abstract class FileWriterGeneratorAction(val generator: Generator) : AnAction() {

    private fun InputStream.toFile(path: String) {
        use { input ->
            File(path).outputStream().use { input.copyTo(it) }
        }
    }

    override fun actionPerformed(e: AnActionEvent?) {

        if (e == null) {

            PluginManager.getLogger().error("Event Null")
        } else {

            val file = e.getData(DataKeys.PSI_FILE)

            if (file != null) {

                try {
                    ApplicationManager.getApplication().executeOnPooledThread {

                        val result = generator.registerGenerators().generate(file)

                        if(result != null) {

                            result.toFile(file.name + generator.getExtension())
                            Notifications.Bus.notify(Notification("MSA", "Success", "${generator.getDisplayName()} for file ${file.name}", NotificationType.INFORMATION))
                        }
                    }

                } catch (e: Exception) {
                    PluginManager.getLogger().error(e)
                }
            } else {

                PluginManager.getLogger().error("PsiFile Null")
            }

        }
    }
}