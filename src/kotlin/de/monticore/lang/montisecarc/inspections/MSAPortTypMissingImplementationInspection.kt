package de.monticore.lang.montisecarc.inspections

import com.intellij.codeInspection.*
import com.intellij.psi.PsiElementVisitor
import com.intellij.util.containers.isNullOrEmpty
import de.monticore.lang.montisecarc.psi.MSAPortElement
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
class MSAPortTypMissingImplementationInspection : LocalInspectionTool() {

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean, session: LocalInspectionToolSession): PsiElementVisitor {
        return super.buildVisitor(holder, isOnTheFly, session)
    }

    override fun buildVisitor(holder: ProblemsHolder, isOnTheFly: Boolean): PsiElementVisitor = object : MSAVisitor() {

        override fun visitPortElement(o: MSAPortElement) {

            val javaClassReference = o.javaClassReference
            if (javaClassReference != null) {

                if (!javaClassReference.javaReferenceList.isNullOrEmpty()) {

                    val reference = javaClassReference.javaReferenceList.last()
                    if (reference.reference == null) {

                        holder.manager.createProblemDescriptor(reference, "Unresolved Reference", isOnTheFly, emptyArray<LocalQuickFix>(), ProblemHighlightType.LIKE_UNKNOWN_SYMBOL)
                    }
                }
            }
        }
    }
}