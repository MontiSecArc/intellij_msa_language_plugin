package de.monticore.lang.montisecarc.usage

import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiNamedElement
import de.monticore.lang.montisecarc.psi.MSAComponentSignature
import de.monticore.lang.montisecarc.psi.MSAPortInstanceName
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

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
class MSAFindUsageProvider : FindUsagesProvider {

    @Nullable
    override fun getWordsScanner(): WordsScanner? = null

    /*{
        return DefaultWordsScanner(MSALexerAdapter(),
                TokenSet.create(MSACompositeElementTypes.COMPONENT_NAME, MSACompositeElementTypes.COMPONENT_INSTANCE_NAME, MSACompositeElementTypes.PORT_INSTANCE_NAME),
                TokenSet.create(MSATokenElementTypes.SINGLE_LINE_COMMENT, MSATokenElementTypes.MULTI_LINE_COMMENT),
                TokenSet.create(MSATokenElementTypes.STRING))
    }*/

    override fun canFindUsagesFor(@NotNull psiElement: PsiElement): Boolean {
        return psiElement is PsiNamedElement
    }

    @Nullable
    override fun getHelpId(@NotNull psiElement: PsiElement): String? {
        return null
    }

    @NotNull
    override fun getType(@NotNull element: PsiElement): String {
        if (element is MSAComponentSignature) {
            return "component"
        } else if (element is MSAPortInstanceName) {
            return "port"
        } else {
            return ""
        }
    }

    @NotNull
    override fun getDescriptiveName(@NotNull element: PsiElement): String {
        if (element is MSAComponentSignature) {
            return element.text
        } else {
            return ""
        }
    }

    @NotNull
    override fun getNodeText(@NotNull element: PsiElement, useFullName: Boolean): String {
        if (element is MSAComponentSignature) {
            return element.text
        } else {
            return ""
        }
    }
}