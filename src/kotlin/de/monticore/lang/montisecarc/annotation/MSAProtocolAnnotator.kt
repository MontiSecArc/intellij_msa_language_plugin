package de.monticore.lang.montisecarc.annotation

import com.intellij.codeInsight.intention.IntentionAction
import com.intellij.lang.ASTNode
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import de.monticore.lang.montisecarc.psi.MSAConnector
import de.monticore.lang.montisecarc.psi.MSAFile
import de.monticore.lang.montisecarc.psi.MSATokenElementTypes
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
class MSAProtocolAnnotator : Annotator {

    override fun annotate(element: PsiElement, holder: AnnotationHolder) = element.accept(object : MSAVisitor() {

        override fun visitConnector(o: MSAConnector) {
            super.visitConnector(o)

            val encrypted = o.node.findChildByType(MSATokenElementTypes.ENCRYPTED)
            val unencrypted = o.node.findChildByType(MSATokenElementTypes.UNENCRYPTED)
            if(o.javaClassReference != null && (encrypted != null || unencrypted != null)) {

                val createErrorAnnotation = holder.createErrorAnnotation(o, "Connection cannot have a protocol and encrypted or unencrypted keyword")

                if(encrypted != null) {

                    createErrorAnnotation.registerFix(MSARemoveEncryptionFix("encrypted", encrypted))
                } else {

                    createErrorAnnotation.registerFix(MSARemoveEncryptionFix("unencrypted", unencrypted))
                }
            }
        }
    })
}

class MSARemoveEncryptionFix(val type: String, val unencrypted: ASTNode?): IntentionAction {
    override fun getFamilyName() = "MSA"

    override fun startInWriteAction(): Boolean = true

    override fun getText(): String = "Remove $type keyword"

    override fun isAvailable(project: Project, editor: Editor?, file: PsiFile?): Boolean = file is MSAFile

    override fun invoke(project: Project, editor: Editor?, file: PsiFile?) {

        if(file is MSAFile) {

            val treeParent = unencrypted?.treeParent
            if(treeParent != null && unencrypted != null) {

                treeParent.removeChild(unencrypted)
            }
        }
    }
}