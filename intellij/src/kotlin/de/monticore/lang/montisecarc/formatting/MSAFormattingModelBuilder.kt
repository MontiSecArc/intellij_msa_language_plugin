package de.monticore.lang.montisecarc.formatting

import com.intellij.formatting.FormattingModel
import com.intellij.formatting.FormattingModelBuilder
import com.intellij.lang.ASTNode
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.codeStyle.CodeStyleSettings
import com.intellij.psi.formatter.DocumentBasedFormattingModel
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
class MSAFormattingModelBuilder: FormattingModelBuilder {

    @NotNull
    override fun createModel(element: PsiElement, settings: CodeStyleSettings): FormattingModel {
        val psiFile = element.containingFile
        val rootNode = if (psiFile is MSAFile) psiFile.node else element.node
        val rootBlock = MSABlock(rootNode, null, null, settings)
        return DocumentBasedFormattingModel(rootBlock, element.project, settings, psiFile.fileType, psiFile)
    }

    @Nullable
    override fun getRangeAffectingIndent(file: PsiFile, offset: Int, elementAtOffset: ASTNode): TextRange? {
        return null
    }
}