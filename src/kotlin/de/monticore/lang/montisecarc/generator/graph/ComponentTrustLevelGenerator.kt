package de.monticore.lang.montisecarc.generator.graph

import com.intellij.psi.PsiElement
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration

/**
 * Copyright 2016 thomasbuning
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
class ComponentTrustLevelGenerator : TrustLevelGenerator() {

    override fun generate(psiElement: PsiElement): Any? {

        if(psiElement is MSAComponentDeclaration) {
            val componentIdentifier = ComponentDeclarationGenerator.createComponentIdentifier(psiElement)
            return Pair(psiElement.absoluteTrustLevel, createConnector(psiElement.absoluteTrustLevel, componentIdentifier))
        }
        return null
    }
}