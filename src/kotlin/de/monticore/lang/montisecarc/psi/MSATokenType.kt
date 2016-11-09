package de.monticore.lang.montisecarc.psi

import com.intellij.lang.ASTNode
import com.intellij.lang.DefaultASTFactory
import com.intellij.openapi.components.service
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.ILeafElementType
import de.monticore.lang.montisecarc.MSALanguage
import org.jetbrains.annotations.NotNull

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

open class MSATokenType(@NotNull debugName: String): IElementType(debugName, MSALanguage.instance), ILeafElementType {

    override fun createLeafNode(leafText: CharSequence): ASTNode = LeafPsiElement(this, leafText)

    override fun toString(): String{
        return "MSATokenType." + super.toString()
    }
}

private val defaultASTFactory by lazy { service<DefaultASTFactory>() }

class MSACommentTokenType(debugName: String) : MSATokenType(debugName) {
    override fun createLeafNode(leafText: CharSequence): ASTNode = defaultASTFactory.createComment(this, leafText)
}

class MSAKeywordTokenType(debugName: String) : MSATokenType(debugName)

class MSAOperatorTokenType(debugName: String) : MSATokenType(debugName)

class MSAAttributeTokenType(debugName: String) : MSATokenType(debugName)