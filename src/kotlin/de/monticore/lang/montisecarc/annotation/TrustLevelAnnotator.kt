package de.monticore.lang.montisecarc.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.util.containers.isNullOrEmpty
import de.monticore.lang.montisecarc.psi.MSATokenElementTypes
import de.monticore.lang.montisecarc.psi.MSATrustLevelRelationStatement
import de.monticore.lang.montisecarc.psi.MSAVisitor
import de.monticore.lang.montisecarc.psi.impl.resolveToComponentDeclaration

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
class TrustLevelAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) = element.accept(object : MSAVisitor() {

        override fun visitTrustLevelRelationStatement(o: MSATrustLevelRelationStatement) {
            super.visitTrustLevelRelationStatement(o)

            val startComponentInstanceNameList = o.startComponent?.componentInstanceNameList
            val targetComponentInstanceNameList = o.targetComponent?.componentInstanceNameList

            if (!startComponentInstanceNameList.isNullOrEmpty() && !targetComponentInstanceNameList.isNullOrEmpty()) {

                val startTrustLevel = startComponentInstanceNameList!!.last().resolveToComponentDeclaration()?.absoluteTrustLevel ?: 0
                val targetTrustLevel = targetComponentInstanceNameList!!.last().resolveToComponentDeclaration()?.absoluteTrustLevel ?: 0

                val greater = o.node.findChildByType(MSATokenElementTypes.GREATER)
                val equal = o.node.findChildByType(MSATokenElementTypes.EQUAL)
                val less = o.node.findChildByType(MSATokenElementTypes.LESS)

                if(greater != null && startTrustLevel < targetTrustLevel) {

                    createErrorAnnotation(holder, o, "$startTrustLevel > $targetTrustLevel")
                } else if (equal != null && startTrustLevel != targetTrustLevel) {

                    createErrorAnnotation(holder, o, "$startTrustLevel = $targetTrustLevel")
                } else if (less != null && startTrustLevel > targetTrustLevel) {

                    createErrorAnnotation(holder, o, "$startTrustLevel < $targetTrustLevel")
                }
            }
        }
    })

    private fun createErrorAnnotation(holder: AnnotationHolder, element: PsiElement, text: String) {

        holder.createErrorAnnotation(element, "Relation not satisfied: $text")
    }
}