package de.monticore.lang.montisecarc.references

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiElementResolveResult
import com.intellij.psi.PsiPolyVariantReference
import com.intellij.psi.PsiReferenceBase
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.psi.*
import de.monticore.lang.montisecarc.stubs.index.MSAPortIndex

/**
 * Copyright 2016 Thomas Buning
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
class MSAPortInstanceNameReference(val element: MSAPortInstanceName, textRange: TextRange, val instanceName: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out PsiElementResolveResult> {

        val prevComponentInstanceName = PsiTreeUtil.getPrevSiblingOfType(element, MSAComponentInstanceName::class.java)

        if (prevComponentInstanceName != null) {

            if (prevComponentInstanceName.references.isNotEmpty()) {

                var instanceName = prevComponentInstanceName.references[0].resolve()

                /**
                 * Check if the parent is an instance to go directly to declaration
                 */
                val (psiElement, inInstance) = resolveIfInstance(instanceName)

                if (inInstance) {

                    instanceName = psiElement
                }

                if (instanceName != null) {

                    return createResolveResult(instanceName)
                }
            }
        } else {

            var checkElement: PsiElement? = element
            /**
             * Check instance
             */
            val (psiElement, inInstance) = resolveIfInstance(element)

            if (inInstance) {

                checkElement = psiElement
            }

            if (checkElement != null) {

                return createResolveResult(checkElement)
            }
        }

        return emptyArray()
    }

    private fun resolveIfInstance(elementInInstance: PsiElement?) : Pair<PsiElement?, Boolean> {

        val instanceDeclaration = PsiTreeUtil.getParentOfType(elementInInstance, MSAComponentInstanceDeclaration::class.java)

        if (instanceDeclaration != null) {

            if (instanceDeclaration.componentNameWithTypeProjectionList.isNotEmpty()) {

                val references = instanceDeclaration.componentNameWithTypeProjectionList.last().componentName.references

                if (references.isNotEmpty()) {

                    val checkElement = references[0].resolve()

                    return Pair(checkElement, checkElement != null)
                }
            }
        }

        return Pair(null, false)
    }

    private fun createResolveResult(psiElement: PsiElement): Array<PsiElementResolveResult> {
        val parentComponent = PsiTreeUtil.getParentOfType(psiElement, MSAComponentDeclaration::class.java)

        if (parentComponent != null) {

            fun getResolveResultFromBody(componentBody: MSAComponentBody?): Set<PsiElementResolveResult> {

                val portDeclarations = componentBody?.portDeclarationList?.flatMap {

                    it.portElementList.filter {

                        val portInstanceName = it.portInstanceName?.text ?: it.javaClassReference?.text?.decapitalize()
                        portInstanceName == element.text
                    }
                }?.requireNoNulls().orEmpty()

                return portDeclarations.map(::PsiElementResolveResult).toSet()
            }

            val componentResolutions = getResolveResultFromBody(parentComponent.componentBody)

            val superComponentsResolutions = parentComponent.superComponents.flatMap {

                getResolveResultFromBody(it.componentBody)
            }

            return componentResolutions.union(superComponentsResolutions).toTypedArray()
        }
        return emptyArray()
    }

    override fun resolve(): PsiElement? {

        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<out Any> {

        val found = arrayListOf<MSAPortInstanceName>()
        StubIndex.getInstance().getAllKeys(MSAPortIndex.KEY, element.project).forEach { portInstanceName ->
            StubIndex.getInstance().processElements(MSAPortIndex.KEY, portInstanceName, element.project, GlobalSearchScope.allScope(element.project), MSAPortElement::class.java, {

                var portInstanceNameLocal = it.portInstanceName
                if (portInstanceNameLocal == null) {

                    portInstanceNameLocal = MSAElementFactory.createPortInstanceName(element.project, it.portName)
                }

                if (!portInstanceNameLocal.text.isNullOrEmpty()) {
                    found.add(portInstanceNameLocal)
                }
                true
            })
        }
        val foundPortInstanceNames = found.filter { it.text.orEmpty().isNotEmpty() }
        val arrayOfLookupElementBuilders = foundPortInstanceNames.map {
            LookupElementBuilder.create(it).withLookupString(it.text)

        }.toTypedArray()

        return arrayOfLookupElementBuilders
    }
}
