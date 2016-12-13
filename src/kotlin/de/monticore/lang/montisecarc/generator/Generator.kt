package de.monticore.lang.montisecarc.generator

import com.intellij.openapi.application.ApplicationManager
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType
import java.io.InputStream

/**
 * Copyright 2016 Thomas Buning
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
abstract class Generator() {

    abstract fun getDisplayName() : String

    abstract fun getExtension() : String

    private val registeredGenerators = mutableMapOf<IElementType, List<Pair<MSAGenerator, (Any) -> Unit>>>()
    private var psiRecursiveElementWalkingVisitor: MSAPsiRecursiveElementWalkingVisitor? = null

    fun generate(parseFile: PsiFile): InputStream? {

        psiRecursiveElementWalkingVisitor = MSAPsiRecursiveElementWalkingVisitor(registeredGenerators)
        if (psiRecursiveElementWalkingVisitor != null) {
            var aggregatedResult: InputStream? = null
            ApplicationManager.getApplication().runReadAction({

                parseFile.accept(psiRecursiveElementWalkingVisitor!!)

                aggregatedResult = aggregateResultFor(parseFile)
            })

            return aggregatedResult
        }
        return null
    }

    fun generate(psiElement: PsiElement) = psiRecursiveElementWalkingVisitor ?: psiElement.accept(psiRecursiveElementWalkingVisitor!!)

    fun registerGenerator(elementType: IElementType, generator: MSAGenerator, callback: (Any) -> Unit) : Generator {
        val pair = listOf(Pair(generator, callback))
        if (registeredGenerators.contains(elementType)) {

            registeredGenerators.put(elementType, registeredGenerators[elementType]!! + pair)

        } else {

            registeredGenerators.put(elementType, pair)
        }

        return this
    }

    abstract fun registerGenerators() : Generator

    abstract fun aggregateResultFor(parsedFile: PsiFile): InputStream?
}