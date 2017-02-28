package de.monticore.lang.montisecarc.completion

import com.intellij.codeInsight.completion.CompletionContributor
import com.intellij.codeInsight.completion.CompletionParameters
import com.intellij.codeInsight.completion.CompletionResultSet
import com.intellij.codeInsight.completion.CompletionType
import com.intellij.codeInsight.lookup.AutoCompletionPolicy
import com.intellij.openapi.project.DumbAware
import com.intellij.patterns.PlatformPatterns
import com.intellij.patterns.PsiElementPattern
import com.intellij.patterns.StandardPatterns
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiErrorElement
import com.intellij.psi.PsiWhiteSpace
import de.monticore.lang.montisecarc.psi.*
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
class MSAKeywordCompletionContributor : CompletionContributor(), DumbAware {

    private fun importPattern(): PsiElementPattern.Capture<PsiElement> {

        return PlatformPatterns.psiElement(MSATokenElementTypes.ID).withParent(PlatformPatterns.psiElement(PsiErrorElement::class.java).withParent(MSAFile::class.java))
    }

    private fun packagePattern(): PsiElementPattern.Capture<PsiElement> {

        return PlatformPatterns.psiElement(MSATokenElementTypes.ID)
                .withParent(PlatformPatterns.psiElement(PsiErrorElement::class.java).withParent(PlatformPatterns.psiElement(MSAFile::class.java))
                        .isFirstAcceptedChild(PlatformPatterns.psiElement()))
    }

    private fun inComponentPattern(): PsiElementPattern.Capture<PsiElement> {

        return PlatformPatterns.psiElement(MSATokenElementTypes.ID).withParent(PlatformPatterns.psiElement(PsiErrorElement::class.java).withParent(MSAComponentBody::class.java))
    }

    private fun componentPattern(): PsiElementPattern.Capture<PsiElement> {

        return PlatformPatterns.psiElement(MSATokenElementTypes.ID).withParent(PlatformPatterns.psiElement(PsiErrorElement::class.java).withParent(MSAFile::class.java))
    }

    private fun portStartPattern(): PsiElementPattern.Capture<PsiElement> {

        return PlatformPatterns.psiElement(MSATokenElementTypes.ID).withSuperParent(3, PlatformPatterns.psiElement(MSAPortElement::class.java))
    }

    private fun accessControlPattern(): PsiElementPattern.Capture<PsiElement> {

        return PlatformPatterns.psiElement(MSATokenElementTypes.ID).afterSiblingSkipping(PlatformPatterns.psiElement(PsiWhiteSpace::class.java), PlatformPatterns.psiElement(MSAAccessControlStatement::class.java))
    }

    private fun portInOutPattern(): PsiElementPattern.Capture<PsiElement> {

        return PlatformPatterns.psiElement(MSATokenElementTypes.ID).withSuperParent(3, PlatformPatterns.psiElement(MSAPortElement::class.java).afterSiblingSkipping(StandardPatterns.and(PlatformPatterns.psiElement(PsiWhiteSpace::class.java), PlatformPatterns.psiElement(PsiErrorElement::class.java)),  PlatformPatterns.psiElement(MSATokenElementTypes.CRITICAL)))
    }

    override fun fillCompletionVariants(@NotNull parameters: CompletionParameters, @NotNull result: CompletionResultSet) {
        super.fillCompletionVariants(parameters, result)
    }

    init {
        this.extend(CompletionType.BASIC, packagePattern(), MSAKeywordCompletionProvider(MSACompletionUtil.KEYWORD_PRIORITY, AutoCompletionPolicy.ALWAYS_AUTOCOMPLETE, arrayOf("package")))
        this.extend(CompletionType.BASIC, importPattern(), MSAKeywordCompletionProvider(MSACompletionUtil.KEYWORD_PRIORITY, arrayOf("import")))
        this.extend(CompletionType.BASIC, componentPattern(), MSAKeywordCompletionProvider(MSACompletionUtil.KEYWORD_PRIORITY, arrayOf("component")))
        this.extend(CompletionType.BASIC, inComponentPattern(), MSAKeywordCompletionProvider(MSACompletionUtil.KEYWORD_PRIORITY, arrayOf("component","access","port","connect","autoconnect","accesscontrol","trustlevel","trustlevelrelation","identity","cpe","configuration")))
        this.extend(CompletionType.BASIC, portInOutPattern(), MSAKeywordCompletionProvider(MSACompletionUtil.KEYWORD_PRIORITY, arrayOf("in", "out")))
        this.extend(CompletionType.BASIC, portStartPattern(), MSAKeywordCompletionProvider(MSACompletionUtil.KEYWORD_PRIORITY, arrayOf("critical", "in", "out")))
        this.extend(CompletionType.BASIC, accessControlPattern(), MSAKeywordCompletionProvider(MSACompletionUtil.KEYWORD_PRIORITY, arrayOf("on", "off")))
    }
}