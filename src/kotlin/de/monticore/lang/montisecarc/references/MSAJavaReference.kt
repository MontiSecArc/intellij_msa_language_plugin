package de.monticore.lang.montisecarc.references

import com.intellij.codeInsight.completion.JavaLookupElementBuilder
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSAJavaReference
import de.monticore.lang.montisecarc.psi.MSATypeVariableDeclaration

/**
 * Copyright 2016 thomasbuning
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
class MSAJavaReference(element: MSAJavaReference, textRange: TextRange, val reference: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> {

        /*
        * Traverse Tree Up and collect all Generic Types, because these can be Java References
        * */
        return PsiShortNamesCache
                .getInstance(element.project)
                .getClassesByName(reference, GlobalSearchScope.allScope(element.project))
                .map(::PsiElementResolveResult)
                .union(
                        getTypeParametersFromTreeParents()
                                .filter { it.id.text == reference }
                                .map(::PsiElementResolveResult)
                ).toTypedArray()
    }

    private fun getTypeParametersFromTreeParents(): MutableList<MSATypeVariableDeclaration> {
        var msaComponentDeclaration = PsiTreeUtil.getParentOfType(element, MSAComponentDeclaration::class.java)

        val collectedTypeVariable = mutableListOf<MSATypeVariableDeclaration>()
        while (msaComponentDeclaration != null) {

            val typeVariables = msaComponentDeclaration.componentSignature?.componentNameWithType?.typeParameters?.typeVariableDeclarationList?.filter { !it.id.text.isNullOrEmpty() }.orEmpty()
            collectedTypeVariable.addAll(typeVariables)
            msaComponentDeclaration = PsiTreeUtil.getParentOfType(msaComponentDeclaration, MSAComponentDeclaration::class.java)
        }
        return collectedTypeVariable
    }

    override fun resolve(): PsiElement? {

        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<out Any> {

        val map = PsiShortNamesCache.getInstance(element.project).allClassNames.flatMap {
            className ->
            PsiShortNamesCache.getInstance(element.project).getClassesByName(className, GlobalSearchScope.allScope(element.project)).map { aClass -> JavaLookupElementBuilder.forClass(aClass) }
        }

        return map.union(getTypeParametersFromTreeParents().filter { it.id.text == reference }.map {
            val lookupElementBuilder = LookupElementBuilder.create(it)
            lookupElementBuilder.withLookupString(it.id.text)
            if(it.javaClassReference != null) {
                lookupElementBuilder.withTailText("(" + it.javaClassReference!!.text + ")")
            }
        }).toTypedArray()
    }
}