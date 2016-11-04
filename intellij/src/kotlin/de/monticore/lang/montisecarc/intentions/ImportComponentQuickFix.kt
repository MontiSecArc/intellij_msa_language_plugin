package de.monticore.lang.montisecarc.intentions

import com.intellij.codeInsight.intention.impl.BaseIntentionAction
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.fileChooser.FileChooser
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.pom.Navigatable
import com.intellij.psi.PsiElementFactory
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiManager
import com.intellij.psi.search.FileTypeIndex
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.indexing.FileBasedIndex
import de.monticore.lang.montisecarc.MSAFileType
import de.monticore.lang.montisecarc.psi.MSAElementFactory
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
class ImportComponentQuickFix(val fullReferencePath: String): BaseIntentionAction() {


    private object Holder {

        fun Package():List<String> {

            if(ImportComponentQuickFix.project == null) {

                return emptyList()
            }

            val project = ImportComponentQuickFix.project!!
            val virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, MSAFileType.instance,
                    GlobalSearchScope.allScope(project))

            val result: MutableList<String> = mutableListOf()
            for (file in virtualFiles) {
                val simpleFile = PsiManager.getInstance(project).findFile(file) as MSAFile

            }

            return result
        }
    }

    companion object {

        var project: Project? = null
        val importPackage: List<String> by lazy { Holder.Package() }
    }

    override fun getText(): String {
        return "Import Component"
    }

    override fun getFamilyName(): String {
        return "Component Import"
    }

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean {

        ImportComponentQuickFix.project = project

        return ImportComponentQuickFix.importPackage.contains(fullReferencePath)
    }

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {
        ApplicationManager.getApplication().invokeLater({
            val virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, MSAFileType.instance,
                    GlobalSearchScope.allScope(project))
            if (virtualFiles.size === 1) {
                createImport(project, virtualFiles.iterator().next())
            } else {
                val descriptor = FileChooserDescriptorFactory.createSingleFileDescriptor(MSAFileType.instance)
                descriptor.setRoots(project.baseDir)
                val choosenFile = FileChooser.chooseFile(descriptor, project, null)
                if (choosenFile != null) {
                    createImport(project, choosenFile)
                }
            }
        })
    }

    private fun createImport(project: Project, file: VirtualFile) {

        ImportComponentQuickFix.project = project
        WriteCommandAction.runWriteCommandAction(project, {

            val simpleFile = PsiManager.getInstance(project).findFile(file) as MSAFile

            /*val importList = PsiTreeUtil.findChildOfType(simpleFile, MSAImportList::class.java)
            if(importList != null) {
                val path = ImportComponentQuickFix.importPackage.find { element -> element.equals(fullReferencePath) }.orEmpty()
                if(path.isNotEmpty()) {
                    val import = MSAElementFactory.createImport(project, path)
                    importList.node.addChild(import.node)
                }
            }*/
        })
    }
}