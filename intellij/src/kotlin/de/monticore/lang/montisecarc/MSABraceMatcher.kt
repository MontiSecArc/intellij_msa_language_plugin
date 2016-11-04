package de.monticore.lang.montisecarc

import com.intellij.lang.BracePair
import com.intellij.lang.PairedBraceMatcher
import com.intellij.psi.PsiFile
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

class MSABraceMatcher: PairedBraceMatcher {

    private val BRACE_PAIRS = arrayOf(BracePair(MSATokenElementTypes.LBRACE, MSATokenElementTypes.RBRACE, true), BracePair(MSATokenElementTypes.LBRACK, MSATokenElementTypes.RBRACK, false), BracePair(MSATokenElementTypes.LPAREN, MSATokenElementTypes.RPAREN, false))
    override fun getPairs(): Array<BracePair> {
        return BRACE_PAIRS
    }

    override fun isPairedBracesAllowedBeforeType(lbraceType: IElementType, contextType: IElementType?): Boolean {
        return true
    }

    override fun getCodeConstructStart(file: PsiFile, openingBraceOffset: Int): Int {
        return openingBraceOffset
    }
}