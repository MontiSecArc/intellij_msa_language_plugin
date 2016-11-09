package de.monticore.lang.montisecarc.generator

import com.intellij.psi.PsiFile
import com.intellij.psi.tree.IElementType

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
class GraphGeneratorBuilder {

    private val psiFiles = mutableListOf<PsiFile>()

    private val graphGenerators = mutableListOf<Triple<IElementType, MSAGenerator, (Any) -> Unit>>()


    fun addFile(psiFile: PsiFile): GraphGeneratorBuilder {

        psiFiles.add(psiFile)

        return this
    }

    fun addGenerator(elementType: IElementType, generator: MSAGenerator, callback: (Any) -> Unit): GraphGeneratorBuilder {

        graphGenerators.add(Triple(elementType, generator, callback))
        return this
    }

    fun build(registerDefaultGenerators: Boolean = true): GraphGenerator {

        val graphGenerator = GraphGenerator()

        if(registerDefaultGenerators) {

            graphGenerator.registerDefaultGenerators()
        }

        psiFiles.forEach {
            graphGenerator.addFile(it)
        }

        graphGenerators.forEach {

            graphGenerator.registerGenerator(it.first, it.second, it.third)
        }

        //clean Up
        psiFiles.clear()
        graphGenerators.clear()

        return graphGenerator
    }
}