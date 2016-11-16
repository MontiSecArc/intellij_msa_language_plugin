package de.monticore.lang.montisecarc.references

import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import de.monticore.lang.montisecarc.psi.*
import de.monticore.lang.montisecarc.stubs.index.MSAComponentDeclarationIndex
import de.monticore.lang.montisecarc.stubs.index.MSAComponentInstanceDeclarationIndex
import de.monticore.lang.montisecarc.stubs.index.MSAComponentInstanceIndex
import de.monticore.lang.montisecarc.stubs.index.MSAPortIndex
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
class MSAReferenceContributor : PsiReferenceContributor() {
    override fun registerReferenceProviders(registrar: PsiReferenceRegistrar) {


        registrar.registerReferenceProvider(PlatformPatterns.psiElement(MSACompositeElementTypes.PORT_INSTANCE_NAME), object : PsiReferenceProvider() {

            @NotNull
            override fun getReferencesByElement(@NotNull element: PsiElement,
                                                @NotNull context: ProcessingContext): Array<PsiReference> {

                val msaPortInstanceName = element as MSAPortInstanceName

                if (msaPortInstanceName.parent is MSAPortDeclaration) {
                    return PsiReference.EMPTY_ARRAY
                }

                val instanceName = msaPortInstanceName.text

                if (!instanceName.isEmpty() && instanceName.first().isLowerCase()) {
                    return arrayOf(MSAPortInstanceNameReference(msaPortInstanceName, TextRange(0, instanceName.length), instanceName))
                } else {
                    return PsiReference.EMPTY_ARRAY
                }
            }
        })

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(MSACompositeElementTypes.COMPONENT_INSTANCE_NAME), object : PsiReferenceProvider() {

            @NotNull
            override fun getReferencesByElement(@NotNull element: PsiElement,
                                                @NotNull context: ProcessingContext): Array<PsiReference> {

                val msaComponentInstanceName = element as MSAComponentInstanceName

                if (msaComponentInstanceName.parent is MSAComponentSignature || msaComponentInstanceName.parent is MSAComponentInstanceDeclaration) {
                    return PsiReference.EMPTY_ARRAY
                }

                val instanceName = msaComponentInstanceName.text

                if (!instanceName.isEmpty() && instanceName.first().isLowerCase()) {
                    return arrayOf(MSAComponentInstanceNameReference(msaComponentInstanceName, TextRange(0, instanceName.length), instanceName))
                } else {
                    return PsiReference.EMPTY_ARRAY
                }
            }
        })

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(MSACompositeElementTypes.COMPONENT_NAME), object : PsiReferenceProvider() {

            @NotNull
            override fun getReferencesByElement(@NotNull element: PsiElement,
                                                @NotNull context: ProcessingContext): Array<PsiReference> {

                val msaComponentName = element as MSAComponentName

                val instanceDeclaration = PsiTreeUtil.getParentOfType(msaComponentName, MSAComponentInstanceDeclaration::class.java)
                if (instanceDeclaration != null) {

                    val componentName = msaComponentName.text
                    return arrayOf(MSAComponentNameReference(msaComponentName, TextRange(0, componentName.length), componentName))
                } else {

                    return PsiReference.EMPTY_ARRAY
                }
            }
        })
    }
}

class MSAPortInstanceNameReference(element: MSAPortInstanceName, textRange: TextRange, val instanceName: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> {

        val parentComponent = PsiTreeUtil.getParentOfType(element, MSAComponentDeclaration::class.java)
        val instanceDeclarationParent = PsiTreeUtil.getParentOfType(element, MSAComponentInstanceDeclaration::class.java)
        val prevComponentInstanceName = PsiTreeUtil.getPrevSiblingOfType(element, MSAComponentInstanceName::class.java)
        var wrappingComponentQualifiedName: String? = parentComponent?.qualifiedName
        var instanceComponentQualifiedName: String? = ""

        if (instanceDeclarationParent != null) {

            val references = instanceDeclarationParent.componentNameWithTypeList.last().componentName.references
            if (references.isNotEmpty()) {

                for (resolveResult in (references[0] as MSAComponentNameReference).multiResolve(false)) {

                    if (resolveResult.element is MSAComponentDeclaration) {

                        instanceComponentQualifiedName = (resolveResult.element as MSAComponentDeclaration).qualifiedName
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

/*class MSAReferenceTypeReference(element: MSAReferenceType, textRange: TextRange, val reference: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> {

        return PsiShortNamesCache.getInstance(element.project).getClassesByName(reference, GlobalSearchScope.allScope(element.project)).map(::PsiElementResolveResult).toTypedArray()
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
        return map.toTypedArray()
    }
}*/

class MSAComponentInstanceNameReference(element: MSAComponentInstanceName, textRange: TextRange, val reference: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> {

        val instanceDeclarationParent = PsiTreeUtil.getParentOfType(element, MSAComponentInstanceDeclaration::class.java)
        val parentComponent = PsiTreeUtil.getParentOfType(element, MSAComponentDeclaration::class.java)
        val prevComponentInstanceName = PsiTreeUtil.getPrevSiblingOfType(element, MSAComponentInstanceName::class.java)
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

                for (resolveResult in (references[0] as MSAComponentNameReference).multiResolve(false)) {

                    if (resolveResult.element is MSAComponentDeclaration) {

                        instanceComponentQualifiedName = (resolveResult.element as MSAComponentDeclaration).qualifiedName
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

class MSAComponentNameReference(element: MSAComponentName, textRange: TextRange, val componentName: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> {

        val found = arrayListOf<MSAComponentDeclaration>()

        val imports = PsiTreeUtil.getChildrenOfType(element.containingFile, MSAImportDeclaration::class.java)?.map { it.text.replace("import ", "").replace(";", "") }.orEmpty()

        val pathForComponentName = element.containingFile.virtualFile.canonicalPath

        StubIndex.getInstance().processElements(MSAComponentDeclarationIndex.KEY, componentName, element.project, GlobalSearchScope.allScope(element.project), MSAComponentDeclaration::class.java, {

            val referencePackage = it.qualifiedName

            if(it.containingFile.virtualFile.canonicalPath == pathForComponentName) {

                found.add(it)
            } else {

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