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
import de.monticore.lang.montisecarc.psi.MSANamedElement
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

            val references = instanceDeclarationParent.componentNameWithTypeList.last().componentName.references
            if (references.isNotEmpty()) {

                (references[0] as MSAComponentNameReference).multiResolve(false)
                        .filter { it.element is MSAComponentDeclaration }
                        .forEach { instanceComponentQualifiedName = (it.element as MSAComponentDeclaration).qualifiedName }
            }
        }

        val found = arrayListOf<MSANamedElement>()

        fun elementProcessor(): (MSANamedElement) -> Boolean {
            return {

                val itComponentParent = PsiTreeUtil.getParentOfType(it, MSAComponentDeclaration::class.java)
                if (itComponentParent?.qualifiedName.equals(wrappingComponentQualifiedName)) {
                    found.add(it)
                }
                if (itComponentParent?.qualifiedName.equals(instanceComponentQualifiedName)) {
                    found.add(it)
                }
                for (superComponent in superComponents) {

                    if (superComponent.qualifiedName == itComponentParent?.qualifiedName) {

                        found.add(it)
                        break
                    }
                }
                true
            }
        }

        StubIndex.getInstance().processElements(MSAComponentInstanceIndex.KEY, reference, element.project, GlobalSearchScope.allScope(element.project), MSAComponentInstanceDeclaration::class.java, elementProcessor())

        StubIndex.getInstance().processElements(MSAComponentInstanceDeclarationIndex.KEY, reference, element.project, GlobalSearchScope.allScope(element.project), MSAComponentDeclaration::class.java, elementProcessor())
        return found.map(::PsiElementResolveResult).toTypedArray()
    }

    override fun resolve(): PsiElement? {

        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<out Any> {

        val found = arrayListOf<MSANamedElement>()
        StubIndex.getInstance().getAllKeys(MSAComponentInstanceIndex.KEY, element.project).forEach { portInstanceName ->
            StubIndex.getInstance().processElements(MSAComponentInstanceIndex.KEY, portInstanceName, element.project, GlobalSearchScope.allScope(element.project), MSAComponentInstanceDeclaration::class.java, {
                found.add(it)
                true
            })

            StubIndex.getInstance().processElements(MSAComponentInstanceDeclarationIndex.KEY, portInstanceName, element.project, GlobalSearchScope.allScope(element.project), MSAComponentDeclaration::class.java, {
                found.add(it)
                true
            })
        }
        val foundComponentInstanceNames = found.filter { !it.name.isNullOrEmpty() }
        val arrayOfLookupElementBuilders = foundComponentInstanceNames.map {
            val lookupElementBuilder = LookupElementBuilder.create(it)
            if (it is MSAComponentInstanceDeclaration) {

                lookupElementBuilder.withLookupString(it.name)
                lookupElementBuilder.withTailText("(" + it.qualifiedName + ")")
            } else if (it is MSAComponentDeclaration) {

                lookupElementBuilder.withLookupString(it.instanceName)
                lookupElementBuilder.withTailText("(" + it.qualifiedName + ")")
            } else {

                lookupElementBuilder.withLookupString(it.name.orEmpty())
            }

        }.toTypedArray()

        return arrayOfLookupElementBuilders
    }
}