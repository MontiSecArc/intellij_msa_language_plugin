package de.monticore.lang.montisecarc.folding

import com.intellij.codeInsight.folding.CodeFoldingSettings
import com.intellij.lang.ASTNode
import com.intellij.lang.folding.CustomFoldingBuilder
import com.intellij.lang.folding.FoldingDescriptor
import com.intellij.openapi.editor.Document
import com.intellij.openapi.project.DumbAware
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.psi.MSAComponentBody
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

class MSAFoldingBuilder: CustomFoldingBuilder(), DumbAware {

    override fun buildLanguageFoldRegions(descriptors: MutableList<FoldingDescriptor>,
                                          root: PsiElement,
                                          document: Document,
                                          quick: Boolean) {
        val msaFile = root as MSAFile
        foldComponentBodies(descriptors, msaFile)
    }

    override fun getLanguagePlaceholderText(node: ASTNode, range: TextRange): String? {
        val psiElement = node.psi

        if (psiElement is MSAFile) return "/.../"
        if (psiElement is MSAComponentBody) return "{...}"

        return "..."
    }

    override fun isRegionCollapsedByDefault(node: ASTNode): Boolean {

        val psiElement = node.psi
        val settings = CodeFoldingSettings.getInstance()

        if (psiElement is MSAFile)
            return settings.COLLAPSE_FILE_HEADER

        if (psiElement is MSAComponentBody) {
            return settings.COLLAPSE_METHODS
        }

        return false
    }


    private fun foldComponentBodies(descriptors: MutableList<FoldingDescriptor>, squirrelFile: MSAFile) {
        for (componentBody in PsiTreeUtil.findChildrenOfType(squirrelFile, MSAComponentBody::class.java)) {
            val body = componentBody
            if (body != null && body.textLength > 2) {
                descriptors.add(FoldingDescriptor(body, body.textRange))
            }
        }
    }
}