package de.monticore.lang.montisecarc.psi

import com.intellij.psi.NavigatablePsiElement
import com.intellij.psi.PsiNameIdentifierOwner

/**
 * Created by thomasbuning on 26.09.16.
 */
interface MSANamedElement: MSACompositeElement, PsiNameIdentifierOwner, NavigatablePsiElement