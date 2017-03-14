package de.monticore.lang.montisecarc.codeinsight

import com.intellij.codeInsight.completion.CompletionType
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase

/**
 * Copyright 2017 thomasbuning
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
class CodeInsightTests: LightCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String = "testData"

    fun testCompletion() {

        myFixture.configureByFile("DefaultCashDeskSystem.secarc")
        myFixture.complete(CompletionType.BASIC, 1);
        val strings = myFixture.lookupElementStrings;

        if (strings == null) {

        } else {
            assertTrue(strings.containsAll(listOf("component", "connect", "accesscontrol", "autoconnect", "barcode")));
            assertEquals(5, strings.size);
        }
    }

    fun testAnnotator() {
        myFixture.configureByFiles("AnnotatorTestData.kt", "CashDeskSystem.secarc")
        myFixture.checkHighlighting(false, false, true, true)
    }

}