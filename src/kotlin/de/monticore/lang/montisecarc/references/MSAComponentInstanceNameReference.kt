package de.monticore.lang.montisecarc.references

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.psi.MSAComponentBody
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSAComponentInstanceDeclaration
import de.monticore.lang.montisecarc.psi.MSAComponentInstanceName
import de.monticore.lang.montisecarc.stubs.index.MSAComponentInstanceDeclarationIndex
import de.monticore.lang.montisecarc.stubs.index.MSAComponentInstanceIndex

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

class MSAComponentInstanceNameReference(val element: MSAComponentInstanceName, textRange: TextRange, val reference: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> {

        val prevComponentInstanceName = PsiTreeUtil.getPrevSiblingOfType(element, MSAComponentInstanceName::class.java)

        if (prevComponentInstanceName != null) {

            if (prevComponentInstanceName.references.isNotEmpty()) {

                val resolve = prevComponentInstanceName.references[0].resolve()

                if (resolve != null && resolve is MSAComponentInstanceName) {

                    return getResolveResult(resolve)
                }
            }
        } else {

            return getResolveResult(element)
        }

        return emptyArray()
    }

    private fun getResolveResult(psiElement: MSAComponentInstanceName): Array<PsiElementResolveResult> {
        val parentComponent = PsiTreeUtil.getParentOfType(psiElement, MSAComponentDeclaration::class.java)

        if (parentComponent != null) {

            fun getResolveResultFromBody(componentBody: MSAComponentBody?): Set<MSAComponentInstanceName> {
                val componentDeclarations = componentBody?.componentDeclarationList?.filter {
                    it != null && it.componentSignature?.componentInstanceName?.text == element.text
                }?.map { it.componentSignature?.componentInstanceName }.orEmpty().requireNoNulls()

                val componentInstanceDeclarations = componentBody?.componentInstanceDeclarationList?.filter {
                    it != null && it.componentInstanceNameList.map { it.id.text }.contains(element.text)
                }?.flatMap { it.componentInstanceNameList.filter { it.id.text == element.text } }.orEmpty()

                return componentDeclarations.union(componentInstanceDeclarations)
            }

            val componentResolutions = getResolveResultFromBody(parentComponent.componentBody)

            val superComponentsResolutions = parentComponent.superComponents.flatMap {

                getResolveResultFromBody(it.componentBody)
            }

            return componentResolutions.union(superComponentsResolutions).map(::PsiElementResolveResult).toTypedArray()
        }

        return emptyArray()
    }

    override fun resolve(): PsiElement? {

        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<out Any> {

        val found = arrayListOf<MSAComponentInstanceName>()

        StubIndex.getInstance().getAllKeys(MSAComponentInstanceIndex.KEY, element.project).forEach { portInstanceName ->
            StubIndex.getInstance().processElements(MSAComponentInstanceIndex.KEY, portInstanceName, element.project, GlobalSearchScope.allScope(element.project), MSAComponentInstanceDeclaration::class.java, {
                found.addAll(it.componentInstanceNameList.filter { !it.text.isNullOrEmpty() })
                true
            })

            StubIndex.getInstance().processElements(MSAComponentInstanceDeclarationIndex.KEY, portInstanceName, element.project, GlobalSearchScope.allScope(element.project), MSAComponentDeclaration::class.java, {
                val componentInstanceName = it.componentSignature?.componentInstanceName
                if (componentInstanceName != null) {
                    found.add(componentInstanceName)
                }
                true
            })
        }

        val arrayOfLookupElementBuilders = found.map {
            val lookupElementBuilder = LookupElementBuilder.create(it)
            if (it is MSAComponentInstanceDeclaration) {

                lookupElementBuilder.withLookupString(it.componentInstanceNameList.joinToString())
                lookupElementBuilder.withTailText("(" + it.qualifiedName + ")")
            } else if (it is MSAComponentDeclaration) {

                lookupElementBuilder.withLookupString(it.instanceName)
                lookupElementBuilder.withTailText("(" + it.qualifiedName + ")")
            } else {

                lookupElementBuilder.withLookupString(it.text.orEmpty())
            }

        }.toTypedArray()

        return arrayOfLookupElementBuilders
    }
}