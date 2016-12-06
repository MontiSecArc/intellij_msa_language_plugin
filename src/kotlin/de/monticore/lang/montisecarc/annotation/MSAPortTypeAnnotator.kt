package de.monticore.lang.montisecarc.annotation

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.lang.annotation.Annotation
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiJavaFile
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache
import de.monticore.lang.montisecarc.psi.MSAElementFactory
import de.monticore.lang.montisecarc.psi.MSAFile
import de.monticore.lang.montisecarc.psi.MSAJavaReference
import de.monticore.lang.montisecarc.psi.MSAVisitor

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
class MSAPortTypeAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) = element.accept(object : MSAVisitor() {

        override fun visitJavaReference(o: MSAJavaReference) {

            super.visitJavaReference(o)
            if(o.references.isEmpty()) {

                createElementTypCannotBeResolvedError(o, holder)
            } else {

                val psiElement = o.references.first().resolve()
                if(psiElement == null) {

                    createElementTypCannotBeResolvedError(o, holder)
                }
            }
        }
    })

    private fun createElementTypCannotBeResolvedError(o: MSAJavaReference, holder: AnnotationHolder) {

        val createErrorAnnotation = holder.createErrorAnnotation(o, "Port Element Typ cannot be resolved")

        if(o.references.isNotEmpty()) {

            val variants = PsiShortNamesCache.getInstance(o.project).allClassNames.filter { it.contains(o.id.text) }

            if (variants.size < 3) {

                registerQuickFixForImports(variants, createErrorAnnotation, o.project, o.textRange)
            } else {

                registerQuickFixForImports(variants.subList(0, 4), createErrorAnnotation, o.project, o.textRange)
            }
        }
    }

    private fun registerQuickFixForImports(variants: List<String>, holder: Annotation, project: Project, textRange: TextRange) {

        variants.map {
            PsiShortNamesCache.getInstance(project).getClassesByName(it, GlobalSearchScope.allScope(project)).map {

                val packageName = (it.containingFile as PsiJavaFile).packageStatement?.packageName
                if (packageName != null && it.name != null) {
                    holder.registerFix(ImportClassFix(packageName, it.name!!), textRange)
                }
            }
        }

    }
}

class ImportClassFix(val packageName: String, val className: String) : IntentionAction {
    override fun getFamilyName(): String = "MSA"

    override fun startInWriteAction(): Boolean = true

    override fun getText(): String = "Import class $className from $packageName"

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean = true

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {

        if (file != null && file is MSAFile) {

            val importStatement = MSAElementFactory.createImportStatement(project, packageName + "." + className)

            file.addAfter(importStatement, file.getPackage())
        }
    }
}
