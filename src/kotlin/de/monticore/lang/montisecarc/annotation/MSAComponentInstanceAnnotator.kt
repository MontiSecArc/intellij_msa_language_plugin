package de.monticore.lang.montisecarc.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSAComponentInstanceDeclaration
import de.monticore.lang.montisecarc.psi.MSAComponentInstanceName
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
class MSAComponentInstanceAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) = element.accept(object : MSAVisitor() {

        override fun visitComponentInstanceName(o: MSAComponentInstanceName) {

            super.visitComponentInstanceName(o)
            val msaComponentDeclaration = PsiTreeUtil.getParentOfType(o, MSAComponentDeclaration::class.java)
            val msaComponentInstanceDeclaration = PsiTreeUtil.getParentOfType(o, MSAComponentInstanceDeclaration::class.java)

            if(msaComponentDeclaration == null && msaComponentInstanceDeclaration == null) {

                if(o.references.isNotEmpty()) {

                    if(o.references.first().resolve() == null) {

                        holder.createErrorAnnotation(o, "Component Instance cannot be resolved")
                    }
                } else {

                    holder.createErrorAnnotation(o, "Component Instance cannot be resolved")
                }
            }
        }
    })
}