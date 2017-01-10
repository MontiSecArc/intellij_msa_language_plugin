package de.monticore.lang.montisecarc.annotation

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.JavaDirectoryService
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import de.monticore.lang.montisecarc.psi.MSAElementFactory
import de.monticore.lang.montisecarc.psi.MSAFile
import de.monticore.lang.montisecarc.psi.MSAPackageClause
import de.monticore.lang.montisecarc.psi.MSAVisitor

/**
 * Copyright 2016 thomasbuning
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
class MSAInvalidPackageAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) = element.accept(object : MSAVisitor() {

        override fun visitPackageClause(o: MSAPackageClause) {

            super.visitPackageClause(o)
            val directory = element.containingFile.containingDirectory

            val dirPackageName = JavaDirectoryService.getInstance().getPackage(directory)?.qualifiedName

            if(dirPackageName != null && dirPackageName != o.text.replace("package ", "").replace(";", "")) {

                val annotation = holder.createWarningAnnotation(o, "Package directive does not match files location")
                annotation.registerFix(MSAInvalidPackageRenamePackageDirectiveFix(dirPackageName))
            } else if (dirPackageName.isNullOrEmpty()) {

                val annotation = holder.createErrorAnnotation(o, "File is not contained in a java package directory")
                annotation.registerFix(MSAInvalidPackageRemovePackageDirectiveFix())
            }
        }
    })
}

class MSAInvalidPackageRenamePackageDirectiveFix(val actualPackage: String): IntentionAction {
    override fun getFamilyName() = "MSA"

    override fun startInWriteAction(): Boolean = true

    override fun getText(): String = "Rename package to $actualPackage"

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean = file is MSAFile

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {

        if(file is MSAFile) {

            val oldPackage = file.getPackage()?.node
            val newPackage = MSAElementFactory.createPackageStatement(project, actualPackage).node
            if(oldPackage != null) {
                file.node.replaceChild(oldPackage, newPackage)
            }
        }
    }
}

class MSAInvalidPackageRemovePackageDirectiveFix(): IntentionAction {
    override fun getFamilyName() = "MSA"

    override fun startInWriteAction(): Boolean = true

    override fun getText(): String = "Remove package directive"

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean = file is MSAFile

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {

        if(file is MSAFile) {

            val oldPackage = file.getPackage()?.node
            if(oldPackage != null) {
                file.node.removeChild(oldPackage)
            }
        }
    }
}
