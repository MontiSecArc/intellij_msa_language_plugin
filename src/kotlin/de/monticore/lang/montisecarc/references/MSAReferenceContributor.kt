package de.monticore.lang.montisecarc.references

import com.intellij.openapi.util.TextRange
import com.intellij.patterns.PlatformPatterns
import com.intellij.psi.*
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.ProcessingContext
import de.monticore.lang.montisecarc.psi.*
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

                val parentOfType = PsiTreeUtil.getParentOfType(msaPortInstanceName, MSAPortDeclaration::class.java)
                if (parentOfType != null) {
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

                val instanceName = msaComponentInstanceName.text
                if (msaComponentInstanceName.parent is MSAComponentSignature || msaComponentInstanceName.parent is MSAComponentInstanceDeclaration) {
                    return PsiReference.EMPTY_ARRAY
                }

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

                // Is in Extends
                val extends = PsiTreeUtil.getParentOfType(msaComponentName, MSAComponentExtension::class.java)
                val instanceDeclaration = PsiTreeUtil.getParentOfType(msaComponentName, MSAComponentInstanceDeclaration::class.java)
                if (instanceDeclaration != null || extends != null) {

                    val componentName = msaComponentName.text
                    return arrayOf(MSAComponentNameReference(msaComponentName, TextRange(0, componentName.length), componentName))
                } else {

                    return PsiReference.EMPTY_ARRAY
                }
            }
        })

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(MSACompositeElementTypes.JAVA_REFERENCE), object : PsiReferenceProvider() {

            @NotNull
            override fun getReferencesByElement(@NotNull element: PsiElement,
                                                @NotNull context: ProcessingContext): Array<PsiReference> {

                val javaReference = element as de.monticore.lang.montisecarc.psi.MSAJavaReference

                val import = javaReference.text
                return arrayOf(MSAJavaReference(javaReference, TextRange(0, import.length), import))
            }
        })
    }
}