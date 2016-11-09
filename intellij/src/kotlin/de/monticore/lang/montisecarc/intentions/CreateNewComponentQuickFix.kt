package de.monticore.lang.montisecarc.intentions

import com.intellij.codeInsight.intention.impl.BaseIntentionAction
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.fileChooser.FileChooserFactory
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import de.monticore.lang.montisecarc.psi.MSAFile

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
class CreateNewComponentQuickFix(val componentName: String): BaseIntentionAction() {

    override fun getText(): String {
        return "Create Component"
    }

    override fun getFamilyName(): String {
        return "Component Creation"
    }

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean {
        return true
    }

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {
        ApplicationManager.getApplication().invokeLater({

            val descriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor()
            descriptor.setRoots(project.baseDir)
            FileChooserFactory.getInstance().createPathChooser(descriptor, project, null).choose(null, {
                files ->
                    createComponent(project, files.get(0))
            })
        })
    }

    private fun createComponent(project: Project, choosenFile: VirtualFile) {

        WriteCommandAction.runWriteCommandAction(project, {

            val simpleFile = PsiManager.getInstance(project).findFile(choosenFile) as MSAFile


        })
    }


}