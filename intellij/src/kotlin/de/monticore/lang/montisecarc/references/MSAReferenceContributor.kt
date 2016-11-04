package de.monticore.lang.montisecarc.references

import com.intellij.codeInsight.completion.JavaLookupElementBuilder
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.search.GlobalSearchScope
import com.intellij.psi.search.PsiShortNamesCache
import com.intellij.psi.stubs.StubIndex
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import de.monticore.lang.montisecarc.psi.*
import de.monticore.lang.montisecarc.stubs.index.MSAComponentDeclarationIndex
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

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(MSACompositeElementTypes.REFERENCE_TYPE), object : PsiReferenceProvider() {
            @NotNull
            override fun getReferencesByElement(@NotNull element: PsiElement,
                                                @NotNull context: ProcessingContext): Array<PsiReference> {

                val msaReferenceType = element as MSAReferenceType

                val reference = msaReferenceType.text

                if (!reference.isEmpty() && reference.first().isUpperCase()) {
                    return arrayOf(MSAReferenceTypeReference(msaReferenceType, TextRange(0, reference.length), reference))
                } else {
                    return PsiReference.EMPTY_ARRAY
                }
            }
        })

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

                if (msaComponentName.parent is MSAComponentSignature) {

                    val instanceName = (msaComponentName.parent as MSAComponentSignature).componentInstanceName?.text.orEmpty()
                    if(!instanceName.isNullOrEmpty()) {

                        val componentName = msaComponentName.text
                        return arrayOf(MSAComponentNameReference(msaComponentName, TextRange(0, componentName.length), componentName))
                    } else {

                        return PsiReference.EMPTY_ARRAY
                    }

                } else {

                    return PsiReference.EMPTY_ARRAY
                }
            }
        })
    }
}

class MSAPortInstanceNameReference(element: MSAPortInstanceName, textRange: TextRange, val instanceName: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    fun getLastComponentInstanceName(componentInstanceNames: List<MSAComponentInstanceName>): MSAComponentInstanceName? {

        if (componentInstanceNames.isEmpty()) {
            return null
        }

        return componentInstanceNames.last()
    }

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> {

        var lastComponentInstanceName: String? = null
        val enclosingComponentInstanceName = PsiTreeUtil.getParentOfType(element, MSAComponentDeclaration::class.java)?.componentSignature?.componentInstanceName?.text
        // Used PortInstanceName in a Connector
        var searchScope = GlobalSearchScope.allScope(element.project)
        if (element.parent is MSAConnectTarget) {

            lastComponentInstanceName = getLastComponentInstanceName((element.parent as MSAConnectTarget).componentInstanceNameList)?.text.orEmpty()
            searchScope = GlobalSearchScope.fileScope(element.containingFile)
        } else if (element.parent is MSAConnectSource) {

            lastComponentInstanceName = getLastComponentInstanceName((element.parent as MSAConnectSource).componentInstanceNameList)?.text.orEmpty()
            searchScope = GlobalSearchScope.fileScope(element.containingFile)
        }


        val found = arrayListOf<MSAPortElement>()
        StubIndex.getInstance().processElements(MSAPortIndex.KEY, instanceName, element.project, searchScope, MSAPortElement::class.java, {

            var foundEnclosingComponentInstanceName: String? = null
            if (it.enclosingComponent != null) {
                foundEnclosingComponentInstanceName = it.enclosingComponent!!.componentSignature?.componentInstanceName?.text
            }

            if (!lastComponentInstanceName.isNullOrEmpty() && lastComponentInstanceName.equals(foundEnclosingComponentInstanceName)) {
                found.add(it)
            } else if (lastComponentInstanceName.isNullOrEmpty() && enclosingComponentInstanceName.equals(foundEnclosingComponentInstanceName)) {
                found.add(it)
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

                if (it.referenceType?.text != null) {

                    lookupElementBuilder.withTailText("(" + it.referenceType!!.text + ")")
                }
            }
            lookupElementBuilder

        }.toTypedArray()

        return arrayOfLookupElementBuilders
    }
}

class MSAReferenceTypeReference(element: MSAReferenceType, textRange: TextRange, val reference: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

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
}

class MSAComponentInstanceNameReference(element: MSAComponentInstanceName, textRange: TextRange, val reference: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> {

        val connector = PsiTreeUtil.getParentOfType(element, MSAConnector::class.java)
        val relationStatement = PsiTreeUtil.getParentOfType(element, MSATrustLevelRelationStatement::class.java)
        val identityStatement = PsiTreeUtil.getParentOfType(element, MSAIdentityStatement::class.java)

        val found = arrayListOf<MSAComponentDeclaration>()
        StubIndex.getInstance().processElements(MSAComponentDeclarationIndex.KEY, reference, element.project, GlobalSearchScope.allScope(element.project), MSAComponentDeclaration::class.java, {

            if(relationStatement != null) {

                val msaComponentDeclaration = PsiTreeUtil.getParentOfType(relationStatement, MSAComponentDeclaration::class.java)

                if(msaComponentDeclaration?.qualifiedName.equals(PsiTreeUtil.getParentOfType(it, MSAComponentDeclaration::class.java)?.qualifiedName)) {
                    found.add(it)
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

class MSAComponentNameReference(element: MSAComponentName, textRange: TextRange, val componentName: String) : PsiReferenceBase<PsiElement>(element, textRange), PsiPolyVariantReference {

    override fun multiResolve(incompleteCode: Boolean): Array<out ResolveResult> {

        val found = arrayListOf<MSAComponentDeclaration>()

        return found.map(::PsiElementResolveResult).toTypedArray()
    }

    override fun resolve(): PsiElement? {

        val resolveResults = multiResolve(false)
        return if (resolveResults.size == 1) resolveResults[0].element else null
    }

    override fun getVariants(): Array<out Any> {

        val found = arrayListOf<MSAComponentDeclaration>()

        val foundComponentInstanceNames = found.filter { !it.instanceName.isNullOrEmpty() }
        val arrayOfLookupElementBuilders = foundComponentInstanceNames.map {
            val lookupElementBuilder = LookupElementBuilder.create(it)
            lookupElementBuilder.withLookupString(it.instanceName)
            lookupElementBuilder.withTailText("(" + it.qualifiedName + ")")

        }.toTypedArray()

        return arrayOfLookupElementBuilders
    }
}
