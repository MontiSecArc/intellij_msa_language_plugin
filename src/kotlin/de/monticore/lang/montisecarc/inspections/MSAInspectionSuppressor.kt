package de.monticore.lang.montisecarc.inspections

import com.intellij.codeInsight.FileModificationService
import com.intellij.codeInsight.daemon.impl.actions.AbstractBatchSuppressByNoInspectionCommentFix
import com.intellij.codeInspection.InspectionSuppressor
import com.intellij.codeInspection.InspectionsBundle
import com.intellij.codeInspection.SuppressQuickFix
import com.intellij.codeInspection.SuppressionUtil
import com.intellij.openapi.command.undo.UndoUtil
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiWhiteSpace
import com.intellij.psi.util.PsiTreeUtil.skipSiblingsBackward
import de.monticore.lang.montisecarc.psi.MSAElementFactory
import de.monticore.lang.montisecarc.psi.MSAHighlightable
import de.monticore.lang.montisecarc.psi.util.parentOfType
import org.jetbrains.annotations.NotNull


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
class MSAInspectionSuppressor : InspectionSuppressor {

    override fun getSuppressActions(element: PsiElement?, toolId: String): Array<out SuppressQuickFix> {

        val container = getContainer(element)
        if(container is MSAHighlightable) {

            return (container.policyViolations.map(::SuppressInspectionFix) + arrayOf(SuppressInspectionFix(SuppressionUtil.ALL))).toTypedArray()
        }

        return arrayOf(
                SuppressInspectionFix(toolId),
                SuppressInspectionFix(SuppressionUtil.ALL)

        )
    }

    private fun getContainer(context: PsiElement?): PsiElement? {
        if (context == null) return null
        return context.parentOfType<MSAHighlightable>(strict = false)
    }

    override fun isSuppressedFor(element: PsiElement, toolId: String): Boolean {
        val item = element.parentOfType<MSAHighlightable>(strict = false) ?: return false
        return isSuppressedByComment(item, toolId)
    }

    private fun isSuppressedByComment(element: PsiElement, toolId: String): Boolean {
        val comment = skipSiblingsBackward(element, PsiWhiteSpace::class.java) as? PsiComment
                ?: return false
        val matcher = SuppressionUtil.SUPPRESS_IN_LINE_COMMENT_PATTERN.matcher(comment.text)
        return matcher.matches() && SuppressionUtil.isInspectionToolIdMentioned(matcher.group(1), toolId)
    }

    private class SuppressInspectionFix(
            val id: String
    ) : AbstractBatchSuppressByNoInspectionCommentFix(id, /* replaceOthers = */ id == SuppressionUtil.ALL) {

        init {
            text = if (id == SuppressionUtil.ALL) "Suppress all inspections for item" else id
        }

        override fun getContainer(context: PsiElement?): PsiElement? {
            if (context == null) return null
            return context.parentOfType<MSAHighlightable>(strict = false)
        }

        override fun isAvailable(project: Project, context: PsiElement): Boolean  = getContainer(context) != null

        @NotNull
        override fun getFamilyName(): String {
            return InspectionsBundle.message("suppress.inspection.member")
        }

        override fun invoke(project: Project, element: PsiElement) {
            if (doSuppress(project, getContainer(element))) return
            // todo suppress
            //DaemonCodeAnalyzer.getInstance(project).restart();
            UndoUtil.markPsiFileForUndo(element.containingFile)
        }

        private fun doSuppress(@NotNull project: Project, container: PsiElement?): Boolean {
            assert(container != null)
            if (!FileModificationService.getInstance().preparePsiElementForWrite(container)) return true
            var commentText = "@SuppressPolicy($id)"
            if(container is MSAHighlightable) {

                commentText = "@SuppressPolicy(${container.policyViolations.joinToString()})"
            }


            val docComment = MSAElementFactory.createSuppressPolicyAnnotation(project, commentText)
            val firstChild = container!!.firstChild
            container.addBefore(docComment, firstChild)
            return false
        }

    }
}
