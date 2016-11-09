package de.monticore.lang.montisecarc.generator

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiRecursiveElementWalkingVisitor
import com.intellij.psi.tree.IElementType
import de.monticore.lang.montisecarc.psi.util.elementType

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
class MSAPsiRecursiveElementWalkingVisitor(val registeredGenerators: MutableMap<IElementType, List<Pair<MSAGenerator, (Any) -> Unit>>>) : PsiRecursiveElementWalkingVisitor() {

    override fun visitElement(element: PsiElement?) {
        super.visitElement(element)

        if (element == null) {

            return
        }

        registeredGenerators[element.elementType]?.forEach {
            val statement = it.first.generate(element)
            if(statement != null) {

                it.second(statement)
            }
        }
    }
}