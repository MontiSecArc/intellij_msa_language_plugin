package de.monticore.lang.montisecarc.annotation

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import junit.framework.TestCase

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
class MSAInvalidPackageAnnotatorTest: LightCodeInsightFixtureTestCase() {

    override fun getTestDataPath(): String {
        return "testData/annotator"
    }

    fun testInvalidPackage() {

        myFixture.configureByFiles("InvalidPackage/InvalidPackageTestData.secarc")
        myFixture.checkHighlighting(false, false, true, true)

        val allQuickFixes = myFixture.getAllQuickFixes()

        TestCase.assertEquals(allQuickFixes.size, 1)

        myFixture.launchAction(allQuickFixes[0])
        myFixture.checkResultByFile("InvalidPackage/InvalidPackageTestData.secarc", "InvalidPackage/InvalidPackageTestDataFixed.secarc", false);
    }
}