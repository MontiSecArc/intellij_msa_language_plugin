package de.monticore.lang.montisecarc.templates

import com.intellij.codeInsight.template.EverywhereContextType
import com.intellij.codeInsight.template.TemplateContextType
import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.psi.util.PsiUtilCore
import de.monticore.lang.montisecarc.MSALanguage
import de.monticore.lang.montisecarc.psi.MSAComponentBody
import kotlin.reflect.KClass

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
sealed class MSAContextType(
        id: String,
        presentableName: String,
        baseContextType: KClass<out TemplateContextType>
) : TemplateContextType(id, presentableName, baseContextType.java) {

    final override fun isInContext(file: PsiFile, offset: Int): Boolean {
        if (!PsiUtilCore.getLanguageAtOffset(file, offset).isKindOf(MSALanguage.instance)) {
            return false
        }

        val element = file.findElementAt(offset)
        if (element == null || element is PsiComment) {
            return false
        }

        return isInContext(element)
    }

    abstract protected fun isInContext(element: PsiElement): Boolean


    class Generic : MSAContextType("MSA_FILE", "MSA", EverywhereContextType::class) {
        override fun isInContext(element: PsiElement): Boolean = true
    }

    class Component : MSAContextType("MSA_COMPONENT_DECLARATION", "component", Generic::class) {
        override fun isInContext(element: PsiElement): Boolean {

            return PsiTreeUtil.findFirstParent(element, { element -> element is MSAComponentBody }) is MSAComponentBody
        }
    }

    class Port: MSAContextType("MSA_PORT_DECLARATION", "port", Generic::class) {
        override fun isInContext(element: PsiElement): Boolean {

            return PsiTreeUtil.findFirstParent(element, { element -> element is MSAComponentBody }) is MSAComponentBody
        }
    }

    //MSA_COMPONENT_BODY
    class ComponentBody: MSAContextType("MSA_COMPONENT_BODY", "component_body", Generic::class) {
        override fun isInContext(element: PsiElement): Boolean {

            return PsiTreeUtil.findFirstParent(element, { element -> element is MSAComponentBody }) is MSAComponentBody
        }
    }
}