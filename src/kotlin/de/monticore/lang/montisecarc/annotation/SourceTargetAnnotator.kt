package de.monticore.lang.montisecarc.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.psi.*

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
class SourceTargetAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) = element.accept(object : MSAVisitor() {

        override fun visitTrustLevelRelationStatement(o: MSATrustLevelRelationStatement) {

            super.visitTrustLevelRelationStatement(o)
            val startComponentInstanceName = o.startComponent?.componentInstanceNameList?.last()
            val targetComponentInstanceName = o.targetComponent?.componentInstanceNameList?.last()

            if(startComponentInstanceName != null && targetComponentInstanceName != null) {

                if(startComponentInstanceName.references.isNotEmpty()) {

                    val startInstanceName = startComponentInstanceName.references.first().resolve() as MSAComponentInstanceName

                    if(targetComponentInstanceName.references.isNotEmpty()) {

                        val targetInstanceName = targetComponentInstanceName.references.first().resolve() as MSAComponentInstanceName

                        val startQualifiedName = (PsiTreeUtil.getParentOfType(startInstanceName, MSAComponentSignature::class.java)?.parent as? MSAComponentDeclaration)?.qualifiedName
                        val startQualifiedInstanceName = PsiTreeUtil.getParentOfType(startInstanceName, MSAComponentInstanceDeclaration::class.java)?.qualifiedName
                        val targetQualifiedName = (PsiTreeUtil.getParentOfType(targetInstanceName, MSAComponentSignature::class.java)?.parent as? MSAComponentDeclaration)?.qualifiedName
                        val targetQualifiedInstanceName = PsiTreeUtil.getParentOfType(targetInstanceName, MSAComponentInstanceDeclaration::class.java)?.qualifiedName

                        val start = startQualifiedName ?: startQualifiedInstanceName ?: ""
                        val target = targetQualifiedName ?: targetQualifiedInstanceName ?: ""

                        if(start == target) {

                            holder.createErrorAnnotation(targetComponentInstanceName, "Target in trustlevel relation is equal to source")
                        }
                    }
                }
            }
        }
    })
}