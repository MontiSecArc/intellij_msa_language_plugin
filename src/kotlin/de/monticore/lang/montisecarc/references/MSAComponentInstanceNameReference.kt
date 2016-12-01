package de.monticore.lang.montisecarc.references

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSAComponentInstanceDeclaration
import de.monticore.lang.montisecarc.psi.MSAComponentInstanceName
import de.monticore.lang.montisecarc.psi.MSAComponentName
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

class MSAComponentInstanceNameReference(element: MSAComponentInstanceName, textRange: TextRange, val reference: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> {

        val instanceDeclarationParent = PsiTreeUtil.getParentOfType(element, MSAComponentInstanceDeclaration::class.java)
        val parentComponent = PsiTreeUtil.getParentOfType(element, MSAComponentDeclaration::class.java)
        val prevComponentInstanceName = PsiTreeUtil.getPrevSiblingOfType(element, MSAComponentInstanceName::class.java)
        val superComponents = parentComponent?.superComponents.orEmpty()
        var wrappingComponentQualifiedName: String? = parentComponent?.qualifiedName

        if (prevComponentInstanceName != null) {

            if (prevComponentInstanceName.references.isNotEmpty()) {

                val componentInstanceDeclaration = prevComponentInstanceName.references[0]
                if (componentInstanceDeclaration is MSAComponentInstanceNameReference) {

                    val multiResolve = componentInstanceDeclaration.multiResolve(false)
                    if (multiResolve.isNotEmpty()) {

                        val element = multiResolve[0].element
                        if (element is MSAComponentDeclaration) {

                            wrappingComponentQualifiedName = element.qualifiedName
                        } else if (element is MSAComponentInstanceDeclaration) {

                            wrappingComponentQualifiedName = element.qualifiedName
                        }
                    }
                }
            }
        }

        var instanceComponentQualifiedName: String? = ""

        if (instanceDeclarationParent != null) {

            val references = instanceDeclarationParent.componentNameWithTypeProjectionList.last().componentName.references
            if (references.isNotEmpty()) {

                for (resolveResult in (references[0] as MSAComponentNameReference).multiResolve(false)
                        .filter { it.element is MSAComponentName }) {

                    val msaComponentDeclaration = PsiTreeUtil.getParentOfType(resolveResult.element, MSAComponentDeclaration::class.java)
                    if (!msaComponentDeclaration?.qualifiedName.isNullOrEmpty()) {

                        instanceComponentQualifiedName = msaComponentDeclaration!!.qualifiedName
                        break
                    }
                }
            }
        }

        val found = arrayListOf<MSAComponentInstanceName>()

        StubIndex.getInstance().processElements(MSAComponentInstanceIndex.KEY, reference, element.project, GlobalSearchScope.allScope(element.project), MSAComponentInstanceDeclaration::class.java, {

            val instanceNames = it.componentInstanceNameList.filter { !it.text.isNullOrEmpty() }
            found.addAll(instanceNames)
            true
        })

        StubIndex.getInstance().processElements(MSAComponentInstanceDeclarationIndex.KEY, reference, element.project, GlobalSearchScope.allScope(element.project), MSAComponentDeclaration::class.java, {
            val componentInstanceName = it.componentSignature?.componentInstanceName
            if (componentInstanceName != null) {
                found.add(componentInstanceName)
            }
            true
        })
        return found.filter {

            var isValid = false
            var msaComponentDeclaration = PsiTreeUtil.getParentOfType(it, MSAComponentDeclaration::class.java)
            fun foundInstanceName(msaComponentDeclaration: MSAComponentDeclaration): Boolean {
                if (!wrappingComponentQualifiedName.isNullOrEmpty() && msaComponentDeclaration.qualifiedName.contains(wrappingComponentQualifiedName.orEmpty())) {
                    return true
                }
                if (!instanceComponentQualifiedName.isNullOrEmpty() && msaComponentDeclaration.qualifiedName.contains(instanceComponentQualifiedName.orEmpty())) {
                    return true
                }

                return superComponents.any { !it.qualifiedName.isNullOrEmpty() && msaComponentDeclaration.qualifiedName.contains(it.qualifiedName) }
            }

            if (msaComponentDeclaration != null) {
                isValid = foundInstanceName(msaComponentDeclaration)
                msaComponentDeclaration = PsiTreeUtil.getParentOfType(it, MSAComponentDeclaration::class.java)

                if (msaComponentDeclaration != null) {
                    isValid = foundInstanceName(msaComponentDeclaration)
                }
            }
            isValid
        }.map(::PsiElementResolveResult).toTypedArray()
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