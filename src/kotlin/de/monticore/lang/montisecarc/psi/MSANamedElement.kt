package de.monticore.lang.montisecarc.psi

import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiNamedElement

/**
 * Created by thomasbuning on 26.09.16.
 */
interface MSANamedElement: MSACompositeElement, PsiNamedElement, NavigatablePsiElement {

    fun getPolicyViolations(): List<String>

    fun addPolicyViolation(violation: String)
}