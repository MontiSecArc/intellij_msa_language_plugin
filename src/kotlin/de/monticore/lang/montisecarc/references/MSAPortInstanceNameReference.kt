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
class MSAPortInstanceNameReference(element: MSAPortInstanceName, textRange: TextRange, val instanceName: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out com.intellij.psi.ResolveResult> {

        val parentComponent = PsiTreeUtil.getParentOfType(element, MSAComponentDeclaration::class.java)
        val superComponents = parentComponent?.superComponents.orEmpty()
        val instanceDeclarationParent = PsiTreeUtil.getParentOfType(element, MSAComponentInstanceDeclaration::class.java)
        val prevComponentInstanceName = PsiTreeUtil.getPrevSiblingOfType(element, MSAComponentInstanceName::class.java)
        var wrappingComponentQualifiedName: String? = parentComponent?.qualifiedName
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

        if (prevComponentInstanceName != null) {

            if (prevComponentInstanceName.references.isNotEmpty()) {

                val componentInstanceDeclaration = prevComponentInstanceName.references[0]
                if (componentInstanceDeclaration is MSAComponentInstanceNameReference) {

                    val multiResolve = componentInstanceDeclaration.multiResolve(false)
                    if (multiResolve.isNotEmpty()) {

                        val element = multiResolve[0].element
                        val msaComponentDeclaration = PsiTreeUtil.getParentOfType(element, MSAComponentSignature::class.java)
                        val msaComponentInstanceDeclaration = PsiTreeUtil.getParentOfType(element, MSAComponentInstanceDeclaration::class.java)
                        if (msaComponentDeclaration != null) {

                            wrappingComponentQualifiedName = PsiTreeUtil.getParentOfType(msaComponentDeclaration, MSAComponentDeclaration::class.java)?.qualifiedName
                        } else if (msaComponentInstanceDeclaration != null) {

                            val references = msaComponentInstanceDeclaration.componentNameWithTypeProjectionList.last().componentName.references
                            if (references.isNotEmpty()) {

                                if (references[0] is MSAComponentNameReference) {

                                    val resolve = (references[0] as MSAComponentNameReference).multiResolve(false)
                                    if (resolve.isNotEmpty()) {

                                        val element1 = PsiTreeUtil.getParentOfType(resolve[0].element, MSAComponentDeclaration::class.java)

                                        if (element1 is MSAComponentDeclaration) {

                                            wrappingComponentQualifiedName = element1.qualifiedName
                                        }
                                    }
                                }
                            } else {
                                wrappingComponentQualifiedName = msaComponentInstanceDeclaration.qualifiedName
                            }
                        }
                    }
                }
            }
        }
        val found = arrayListOf<PsiElement>()

        StubIndex.getInstance().processElements(MSAPortIndex.KEY, instanceName, element.project, GlobalSearchScope.allScope(element.project), MSAPortElement::class.java, {

            /**
             * Port Instance could reference a Port Type without instance name by a decapitalized port type identifier
             */
            val portInstanceName = it.portInstanceName

            val itComponentParent = PsiTreeUtil.getParentOfType(it, MSAComponentDeclaration::class.java)
            fun addPort(portInstanceName: MSAPortInstanceName?) {
                if (portInstanceName != null) {
                    found.add(portInstanceName)
                } else {
                    found.add(it)
                }
            }

            if (itComponentParent?.qualifiedName.equals(wrappingComponentQualifiedName)) {

                addPort(portInstanceName)
            } else if (itComponentParent?.qualifiedName.equals(instanceComponentQualifiedName)) {
                addPort(portInstanceName)
            }
            for (superComponent in superComponents) {

                if (superComponent.qualifiedName == itComponentParent?.qualifiedName) {

                    addPort(portInstanceName)
                    break
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
