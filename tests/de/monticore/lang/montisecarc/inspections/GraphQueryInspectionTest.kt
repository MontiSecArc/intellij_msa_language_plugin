package de.monticore.lang.montisecarc.inspections

import de.monticore.lang.montisecarc.policy.BasePolicyTestCase
import junit.framework.TestCase
import org.junit.Ignore

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
//Can only be executed as a single test case else it fails
@Ignore
class GraphQueryInspectionTest: BasePolicyTestCase() {

    private var testData = "testData/inspections"

    fun testGraphQueryInspection() = withPolicies {

        myFixture.configureByFiles( "$testData/EncryptStrongComponentInstanceIdentity.secarc",
                                    "$testData/EncryptWeakComponentInstanceIdentity.secarc",
                                    "$testData/Data.java")
        myFixture.enableInspections(GraphQueryInspection())
        myFixture.checkHighlighting(false, false, true, false)

        val allQuickFixes = myFixture.getAllQuickFixes()

        TestCase.assertEquals(allQuickFixes.size, 5)

        allQuickFixes.find { it.familyName == "Fix Encrypt Strong and Weak Identity" }?.invoke(myFixture.project, myFixture.editor, myFixture.file)
    }
}