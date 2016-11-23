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
class MSAPortInstanceNameReference(element: MSAPortInstanceName, textRange: TextRange, val instanceName: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out com.intellij.psi.ResolveResult> {

        val parentComponent = PsiTreeUtil.getParentOfType(element, MSAComponentDeclaration::class.java)
        val superComponents = parentComponent?.superComponents.orEmpty()
        val instanceDeclarationParent = PsiTreeUtil.getParentOfType(element, MSAComponentInstanceDeclaration::class.java)
        val prevComponentInstanceName = PsiTreeUtil.getPrevSiblingOfType(element, MSAComponentInstanceName::class.java)
        var wrappingComponentQualifiedName: String? = parentComponent?.qualifiedName
        var instanceComponentQualifiedName: String? = ""

        if (instanceDeclarationParent != null) {

            val references = instanceDeclarationParent.componentNameWithTypeList.last().componentName.references
            if (references.isNotEmpty()) {

                (references[0] as MSAComponentNameReference).multiResolve(false)
                        .filter { it.element is MSAComponentDeclaration }
                        .forEach { instanceComponentQualifiedName = (it.element as MSAComponentDeclaration).qualifiedName }
            }
        }

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

                            val references = element.componentNameWithTypeList.last().componentName.references
                            if (references.isNotEmpty()) {

                                if (references[0] is MSAComponentNameReference) {

                                    val resolve = (references[0] as MSAComponentNameReference).multiResolve(false)
                                    if (resolve.isNotEmpty()) {

                                        val element1 = resolve[0].element
                                        if (element1 is MSAComponentDeclaration) {

                                            wrappingComponentQualifiedName = element1.qualifiedName
                                        }
                                    }
                                }
                            } else {
                                wrappingComponentQualifiedName = element.qualifiedName
                            }
                        }
                    }
                }
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

                    if(superComponent.qualifiedName == itComponentParent?.qualifiedName) {

                        found.add(it)
                        break
                    }
                }
                true
            }
        }

        StubIndex.getInstance().processElements(MSAPortIndex.KEY, instanceName, element.project, GlobalSearchScope.allScope(element.project), MSAPortElement::class.java, elementProcessor())
        return found.map(::PsiElementResolveResult).toTypedArray()
    }

    override fun resolve(): PsiElement? {

        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<out Any> {

        val found = arrayListOf<MSAPortElement>()
        StubIndex.getInstance().getAllKeys(MSAPortIndex.KEY, element.project).forEach { portInstanceName ->
            StubIndex.getInstance().processElements(MSAPortIndex.KEY, portInstanceName, element.project, GlobalSearchScope.allScope(element.project), MSAPortElement::class.java, {
                found.add(it)
                true
            })
        }
        val foundPortInstanceNames = found.filter { it.portInstanceName != null && it.portInstanceName?.text.orEmpty().isNotEmpty() }
        val arrayOfLookupElementBuilders = foundPortInstanceNames.map {
            val lookupElementBuilder = LookupElementBuilder.create(it)
            val portInstanceName = it.portInstanceName
            if (portInstanceName != null) {
                portInstanceName.text
                lookupElementBuilder.withLookupString(it.portInstanceName!!.text)

                /*if (it.referenceType?.text != null) {

                    lookupElementBuilder.withTailText("(" + it.referenceType!!.text + ")")
                }*/
            }
            lookupElementBuilder

        }.toTypedArray()

        return arrayOfLookupElementBuilders
    }
}
