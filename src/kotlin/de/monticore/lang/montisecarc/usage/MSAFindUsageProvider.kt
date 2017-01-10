package de.monticore.lang.montisecarc.usage

import com.intellij.lang.cacheBuilder.DefaultWordsScanner
import com.intellij.lang.cacheBuilder.WordsScanner
import com.intellij.lang.findUsages.FindUsagesProvider
import com.intellij.psi.PsiElement
import com.intellij.psi.tree.TokenSet
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.MSALexerAdapter
import de.monticore.lang.montisecarc.psi.*
import de.monticore.lang.montisecarc.psi.util.elementType
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

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
class MSAFindUsageProvider : FindUsagesProvider {

    @Nullable
    override fun getWordsScanner(): WordsScanner? {
        return DefaultWordsScanner(MSALexerAdapter(),
                TokenSet.create(MSATokenElementTypes.ID),
                TokenSet.create(MSATokenElementTypes.SINGLE_LINE_COMMENT, MSATokenElementTypes.MULTI_LINE_COMMENT),
                TokenSet.create(MSATokenElementTypes.STRING))
    }

    override fun canFindUsagesFor(@NotNull psiElement: PsiElement): Boolean {
        /*
        * ToDo: Check if Find Usage is necessary:
        *  - For instance names only in instance declarations or component declarations
        *  - Port instance names only in port declarations
        *  - Component Names only in declarations
        * */
        return PsiTreeUtil.getParentOfType(psiElement, MSAPortElement::class.java, MSAComponentInstanceDeclaration::class.java, MSAComponentSignature::class.java) != null
        //return psiElement is PsiNamedElement
    }

    @Nullable
    override fun getHelpId(@NotNull psiElement: PsiElement): String? {
        return null
    }

    @NotNull
    override fun getType(@NotNull element: PsiElement): String {
        when (element.elementType) {

            MSACompositeElementTypes.COMPONENT_INSTANCE_NAME -> return "Component Instance"
            MSACompositeElementTypes.COMPONENT_NAME -> return "Component"
            MSACompositeElementTypes.PORT_INSTANCE_NAME -> return "Port Instance"
        }
        return ""
    }

    @NotNull
    override fun getDescriptiveName(@NotNull element: PsiElement): String {
        when (element.elementType) {

            MSACompositeElementTypes.COMPONENT_INSTANCE_NAME -> return (element as MSAComponentInstanceName).name
            MSACompositeElementTypes.COMPONENT_NAME -> return (element as MSAComponentName).name
            MSACompositeElementTypes.PORT_INSTANCE_NAME -> return (element as MSAPortInstanceName).name
        }
        return ""
    }

    @NotNull
    override fun getNodeText(@NotNull element: PsiElement, useFullName: Boolean): String {

        when (element.elementType) {

            MSACompositeElementTypes.COMPONENT_INSTANCE_NAME -> return (element as MSAComponentInstanceName).name
            MSACompositeElementTypes.COMPONENT_NAME -> return (element as MSAComponentName).name
            MSACompositeElementTypes.PORT_INSTANCE_NAME -> return (element as MSAPortInstanceName).name
        }
        return ""
    }
}