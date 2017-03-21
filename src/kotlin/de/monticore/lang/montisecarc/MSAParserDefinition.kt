package de.monticore.lang.montisecarc

import com.intellij.lang.ASTNode
import com.intellij.lang.ParserDefinition
import com.intellij.lang.PsiParser
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.FileViewProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IFileElementType
import com.intellij.psi.tree.TokenSet
import de.monticore.lang.montisecarc.psi.MSACompositeElementTypes
import de.monticore.lang.montisecarc.psi.MSAFile
import de.monticore.lang.montisecarc.psi.MSATokenElementTypes
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

class MSAParserDefinition: ParserDefinition {

    val WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE)

    val FILE = MSAFileElementType

    @NotNull
    override fun createLexer(project: Project): Lexer {
        return MSALexerAdapter()
    }

    @NotNull
    override fun getWhitespaceTokens(): TokenSet {
        return WHITE_SPACES
    }

    @NotNull
    override fun getCommentTokens(): TokenSet {
        return MSATokenElementTypes.COMMENTS
    }

    @NotNull
    override fun getStringLiteralElements(): TokenSet {
        return TokenSet.EMPTY
    }

    @NotNull
    override fun createParser(project: Project): PsiParser {
        return MSAParser()
    }

    override fun getFileNodeType(): IFileElementType {
        return FILE
    }

    override fun createFile(viewProvider: FileViewProvider): PsiFile {
        return MSAFile(viewProvider)
    }

    override fun spaceExistanceTypeBetweenTokens(left: ASTNode, right: ASTNode): ParserDefinition.SpaceRequirements {
        return ParserDefinition.SpaceRequirements.MAY
    }

    @NotNull
    override fun createElement(node: ASTNode): PsiElement {
        return MSACompositeElementTypes.Factory.createElement(node)
    }
}