package de.monticore.lang.montisecarc.highlighting

import com.intellij.ide.highlighter.JavaHighlightingColors
import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors
import com.intellij.openapi.editor.HighlighterColors
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import de.monticore.lang.montisecarc.MSALexerAdapter
import de.monticore.lang.montisecarc.psi.MSACompositeElementTypes
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

class MSASyntaxHighlighter: SyntaxHighlighterBase() {

    companion object {
        val KEY = createTextAttributesKey("MSA_KEY", DefaultLanguageHighlighterColors.KEYWORD)
        val STRING = createTextAttributesKey("MSA_STRING", DefaultLanguageHighlighterColors.STRING)
        val BAD_CHARACTER = createTextAttributesKey("MSA_BAD_CHARACTER", HighlighterColors.BAD_CHARACTER)
        val IDENTIFIER = createTextAttributesKey("MSA_IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER)
        val DOT = createTextAttributesKey("MSA_DOT", DefaultLanguageHighlighterColors.DOT)
        val SEMICOLON = createTextAttributesKey("MSA_SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON)
        val COLON = createTextAttributesKey("MSA_COLON", HighlighterColors.TEXT)
        val COMMA = createTextAttributesKey("MSA_COMMA", DefaultLanguageHighlighterColors.COMMA)
        val SINGLE_LINE_COMMENT = createTextAttributesKey("MSA_LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT)
        val MULTI_LINE_COMMENT = createTextAttributesKey("MSA_BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT)
        val BRACKETS = createTextAttributesKey("MSA_BRACKET", DefaultLanguageHighlighterColors.BRACKETS)
        val BRACES = createTextAttributesKey("MSA_BRACES", DefaultLanguageHighlighterColors.BRACES)
        val PARENTHESES = createTextAttributesKey("MSA_PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES)
        val LEVEL = createTextAttributesKey("MSA_NUMBER", DefaultLanguageHighlighterColors.NUMBER)
        val TYPES = createTextAttributesKey("MSA_TYPE", DefaultLanguageHighlighterColors.INSTANCE_FIELD)
        val COMPONENT_NAME = createTextAttributesKey("MSA_COMPONENT_NAME", DefaultLanguageHighlighterColors.CLASS_NAME)
        val COMPONENT_INSTANCE_NAME = createTextAttributesKey("MSA_COMPONENT_INSTANCE_NAME", DefaultLanguageHighlighterColors.CLASS_REFERENCE)
        val SUPPRESSION_ANNOTATION_KEYWORD = createTextAttributesKey("MSA_SUPPRESSION_ANNOTATION_KEYWORD", JavaHighlightingColors.ANNOTATION_NAME_ATTRIBUTES)
    }
    private val KEY_KEYS = arrayOf(KEY)
    private val STRING_KEYS = arrayOf(STRING)
    private val BAD_CHAR_KEYS = arrayOf(BAD_CHARACTER)
    private val EMPTY_KEYS = emptyArray<TextAttributesKey>()

    private val TYPES_KEYS = arrayOf(TYPES)

    override fun getTokenHighlights(tokenType: IElementType?): Array<TextAttributesKey> {

        if(MSATokenElementTypes.KEYWORDS.contains(tokenType)) {
            return KEY_KEYS
        }
        if(MSATokenElementTypes.TYPES.contains(tokenType)) {

            return TYPES_KEYS
        }
        when(tokenType) {
            TokenType.BAD_CHARACTER -> return BAD_CHAR_KEYS
            MSATokenElementTypes.PLUS, MSATokenElementTypes.MINUS, MSATokenElementTypes.NUMBER -> return arrayOf(LEVEL)
            MSACompositeElementTypes.COMPONENT_NAME -> return arrayOf(COMPONENT_NAME)
            MSACompositeElementTypes.COMPONENT_INSTANCE_NAME -> return arrayOf(COMPONENT_INSTANCE_NAME)
            MSATokenElementTypes.STRING -> return STRING_KEYS
            MSATokenElementTypes.COMMA -> return arrayOf(COMMA)
            MSATokenElementTypes.COLON -> return arrayOf(COLON)
            MSATokenElementTypes.SEMICOLON -> return arrayOf(SEMICOLON)
            MSATokenElementTypes.DOT -> return arrayOf(DOT)
            MSATokenElementTypes.ID -> return arrayOf(IDENTIFIER)
            MSATokenElementTypes.SINGLE_LINE_COMMENT, MSATokenElementTypes.MULTI_LINE_COMMENT-> return arrayOf(MULTI_LINE_COMMENT, SINGLE_LINE_COMMENT)
            MSATokenElementTypes.LBRACK, MSATokenElementTypes.RBRACK -> return arrayOf(BRACKETS)
            MSATokenElementTypes.LBRACE, MSATokenElementTypes.RBRACE -> return arrayOf(BRACES)
            MSATokenElementTypes.LPAREN, MSATokenElementTypes.RPAREN -> return arrayOf(PARENTHESES)
            MSATokenElementTypes.SUPPRESS_POLICY_TOKEN, MSATokenElementTypes.AT -> return arrayOf(SUPPRESSION_ANNOTATION_KEYWORD)
        }

        return EMPTY_KEYS
    }

    override fun getHighlightingLexer(): Lexer {
        return MSALexerAdapter()
    }
}