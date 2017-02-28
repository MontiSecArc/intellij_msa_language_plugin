package de.monticore.lang.montisecarc.actions

import com.intellij.ide.actions.CreateFileFromTemplateAction
import com.intellij.ide.actions.CreateFileFromTemplateDialog
import com.intellij.openapi.fileEditor.FileDocumentManager
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiDirectory
import com.intellij.psi.PsiFile
import de.monticore.lang.montisecarc.MSAIcons
import de.monticore.lang.montisecarc.psi.MSAFile
import org.jetbrains.annotations.NotNull
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
class NewMSAFileAction : CreateFileFromTemplateAction("Create MSA file", "Create a new MSA file", MSAIcons.FILE), DumbAware {
    val FILE_TEMPLATE = "MSA File"
    val APPLICATION_TEMPLATE = "MSA Component"

    private val NEW_MSA_FILE = "Create MSA File"
    private val DEFAULT_MSA_TEMPLATE_PROPERTY = "DefaultMSATemplateProperty"

    override fun buildDialog(project: Project, directory: PsiDirectory, @NotNull builder: CreateFileFromTemplateDialog.Builder) {
        builder.setTitle(NEW_MSA_FILE).addKind("Simple Component", MSAIcons.FILE, APPLICATION_TEMPLATE)
    }

    @Nullable
    override fun getDefaultTemplateProperty(): String {
        return DEFAULT_MSA_TEMPLATE_PROPERTY
    }

    @NotNull
    override fun getActionName(directory: PsiDirectory, newName: String, templateName: String): String {
        return NEW_MSA_FILE
    }

    override fun postProcess(createdElement: PsiFile, templateName: String, customProperties: Map<String, String>?) {

        if (createdElement is MSAFile) {
            val packageClause = createdElement.getPackage()
            val project = createdElement.getProject()
            val editor = FileEditorManager.getInstance(project).selectedTextEditor ?: return
            val virtualFile = createdElement.containingFile.virtualFile ?: return
            if (FileDocumentManager.getInstance().getDocument(virtualFile) === editor.document) {
                var endOffset = packageClause?.textRange?.endOffset
                if(endOffset == null) {
                    endOffset = 0
                }
                editor.caretModel.moveToOffset(endOffset)
            }
        }
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return other is NewMSAFileAction
    }
}