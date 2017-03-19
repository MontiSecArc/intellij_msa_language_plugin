package de.monticore.lang.montisecarc.annotation

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase

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
class CVEAnnotatorTest: LightCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String {
        return "testData/annotator/CPE"
    }

    fun testCPEErrorAnnotator() {

        myFixture.configureByFiles("CPETestData.secarc")
        myFixture.checkHighlighting(false, false, true, true)
    }

    fun testCPEWarningAnnotator() {

        myFixture.configureByFiles("CPEWarningTestData.secarc")
        myFixture.checkHighlighting(true, true, true, true)
    }

    fun testCPEInformationAnnotator() {

        myFixture.configureByFiles("CPEInformationTestData.secarc")
        myFixture.checkHighlighting(true, true, true, true)
    }

    fun testCPE22Annotator() {

        myFixture.configureByFiles("CPE22TestData.secarc")
        myFixture.checkHighlighting(true, true, true, true)
    }
}