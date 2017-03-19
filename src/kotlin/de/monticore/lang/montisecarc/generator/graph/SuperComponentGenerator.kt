package de.monticore.lang.montisecarc.generator.graph

import com.intellij.psi.PsiElement
import de.monticore.lang.montisecarc.generator.MSAGenerator
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration

class SuperComponentGenerator : MSAGenerator() {

    override fun generate(psiElement: PsiElement): Any? {

        if (psiElement is MSAComponentDeclaration) {

            return psiElement.superComponents.map {
                Triple(it.containingFile, it, psiElement)
            }
        }

        return null
    }
}