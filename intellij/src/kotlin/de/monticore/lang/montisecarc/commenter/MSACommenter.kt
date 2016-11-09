package de.monticore.lang.montisecarc.commenter

import com.intellij.lang.CodeDocumentationAwareCommenter
import com.intellij.psi.PsiComment
import com.intellij.psi.tree.IElementType
import de.monticore.lang.montisecarc.psi.MSATokenElementTypes

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
class MSACommenter: CodeDocumentationAwareCommenter {

    override fun getLineCommentPrefix(): String = "//"

    override fun getBlockCommentPrefix(): String = "/*"

    override fun getBlockCommentSuffix(): String = "*/"

    override fun getCommentedBlockCommentPrefix(): String? {
        return null
    }

    override fun getCommentedBlockCommentSuffix(): String? {
        return null
    }

    override fun getLineCommentTokenType(): IElementType = MSATokenElementTypes.SINGLE_LINE_COMMENT

    override fun getBlockCommentTokenType(): IElementType = MSATokenElementTypes.MULTI_LINE_COMMENT

    override fun getDocumentationCommentPrefix(): String = "/**"

    override fun getDocumentationCommentLinePrefix(): String = "*"

    override fun getDocumentationCommentSuffix(): String = "*/"

    override fun isDocumentationComment(element: PsiComment): Boolean = element.tokenType === MSATokenElementTypes.MULTI_LINE_COMMENT

    override fun getDocumentationCommentTokenType(): IElementType = MSATokenElementTypes.MULTI_LINE_COMMENT
}