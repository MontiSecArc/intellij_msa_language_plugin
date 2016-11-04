package de.monticore.lang.montisecarc.references

import com.intellij.psi.PsiReference
import de.monticore.lang.montisecarc.psi.MSACompositeElement

/**
 * Created by thomasbuning on 26.09.16.
 */
interface MSAReference: PsiReference {

    override fun getElement(): MSACompositeElement
}