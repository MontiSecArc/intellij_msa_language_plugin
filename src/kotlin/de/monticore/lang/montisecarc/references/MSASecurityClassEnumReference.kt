package de.monticore.lang.montisecarc.references

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache
import de.monticore.lang.montisecarc.psi.MSAJavaReference



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
class MSASecurityClassEnumReference(element: MSAJavaReference, textRange: TextRange) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> {

        return PsiShortNamesCache.getInstance(element.project).getClassesByName("MSASecurityClass", GlobalSearchScope.allScope(element.project))
                .filter { it.isEnum }
                .map(::PsiElementResolveResult).toTypedArray()
    }

    override fun resolve(): PsiElement? {

        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<out Any> {

        return PsiShortNamesCache.getInstance(element.project).getClassesByName("MSASecurityClass", GlobalSearchScope.allScope(element.project))
                .filter { it.isEnum }
                .flatMap {
                    it.fields.filter { !it.name.isNullOrEmpty() }.map {
                        val lookupElementBuilder = LookupElementBuilder.create(it)
                        lookupElementBuilder.withLookupString(it.name!!)
                    }
                }.toTypedArray()
    }
}