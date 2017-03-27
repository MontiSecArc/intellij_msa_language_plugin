package de.monticore.lang.montisecarc.generator.graph

import com.intellij.psi.PsiFile
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import junit.framework.TestCase
import java.io.File

/**
 * Copyright 2017 thomasbuning
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class GraphGeneratorTest: LightCodeInsightFixtureTestCase() {

    private val testData = "testData/Generator"

    fun testGenerator() {

        val configureByFile = myFixture.configureByFiles("$testData/CashDeskSystem.secarc", "$testData/CashDesk.secarc", "$testData/Store.secarc")

        val (inputText, sb) = runGraphGeneration(configureByFile.first())

        TestCase.assertEquals(inputText, sb)
    }

    private fun runGraphGeneration(psiFile: PsiFile): Pair<String?, String> {
        val createGraph = GraphGenerator()
        createGraph.generate(psiFile)
        val inputText = createGraph.generatedInputStream?.bufferedReader()?.use { it.readText() }

        val sb = File("$testData/${getTestName(false)}.txt").readText()
        return Pair(inputText, sb)
    }

    fun testClearanceForGenerator() {

        val configureByFiles = myFixture.configureByFiles("$testData/ClearanceFor.secarc", "$testData/MSASecurityClass.java", "$testData/SecretData.java", "$testData/SecurityClassification.java")

        val (inputText, sb) = runGraphGeneration(configureByFiles.first())

        TestCase.assertEquals(inputText, sb)
    }
}