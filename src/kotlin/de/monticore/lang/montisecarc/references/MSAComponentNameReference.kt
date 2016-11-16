package de.monticore.lang.montisecarc.references

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSAComponentName
import de.monticore.lang.montisecarc.psi.MSAImportDeclaration
import de.monticore.lang.montisecarc.stubs.index.MSAComponentDeclarationIndex

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
class MSAComponentNameReference(element: MSAComponentName, textRange: TextRange, val componentName: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> {

        val found = arrayListOf<MSAComponentDeclaration>()

        val imports = PsiTreeUtil.getChildrenOfType(element.containingFile, MSAImportDeclaration::class.java)?.map { it.text.replace("import ", "").replace(";", "") }.orEmpty()

        StubIndex.getInstance().processElements(MSAComponentDeclarationIndex.KEY, componentName, element.project, GlobalSearchScope.allScope(element.project), MSAComponentDeclaration::class.java, {

            val referencePackage = it.qualifiedName
            for (import in imports) {

                if (import.indexOf("*") > 0) {

                    if (referencePackage.substringBeforeLast(".") == import.substringBeforeLast(".")) {

                        found.add(it)
                        break
                    }
                } else {
                    if (import.contains(referencePackage)) {

                        found.add(it)
                        break
                    }
                }
            }
            true
        })
        return found.map(::PsiElementResolveResult).toTypedArray()
    }

    override fun resolve(): PsiElement? {

        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<out Any> {

        val found = arrayListOf<MSAComponentDeclaration>()
        StubIndex.getInstance().getAllKeys(MSAComponentDeclarationIndex.KEY, element.project).forEach { componentDeclaration ->
            StubIndex.getInstance().processElements(MSAComponentDeclarationIndex.KEY, componentDeclaration, element.project, GlobalSearchScope.allScope(element.project), MSAComponentDeclaration::class.java, {
                found.add(it)
                true
            })
        }
        val foundComponentInstanceNames = found.filter { !it.instanceName.isNullOrEmpty() }
        val arrayOfLookupElementBuilders = foundComponentInstanceNames.map {
            val lookupElementBuilder = LookupElementBuilder.create(it)
            lookupElementBuilder.withLookupString(it.instanceName)
            lookupElementBuilder.withTailText("(" + it.qualifiedName + ")")

        }.toTypedArray()

        return arrayOfLookupElementBuilders
    }
}
