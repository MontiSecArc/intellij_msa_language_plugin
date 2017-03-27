package de.monticore.lang.montisecarc.psi.impl

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import de.monticore.lang.montisecarc.psi.*
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
class MSAPsiImplUtilTest: LightCodeInsightFixtureTestCase() {

    fun testPortInstanceNameRename() {

        myFixture.configureByFiles("CashDeskSystem.secarc")

        val msaPortElement = PsiTreeUtil.findChildOfType(myFixture.file, MSAPortInstanceName::class.java)

        val oldPortName = msaPortElement?.portName

        TestCase.assertEquals(msaPortElement?.name, oldPortName)

        val expected = "NewTest"
        msaPortElement?.name = expected

        TestCase.assertEquals(expected, msaPortElement?.portName)
        TestCase.assertNotSame(expected, oldPortName)
    }

    fun testSetComponentInstanceName() {

        myFixture.configureByFiles("CashDeskSystem.secarc")

        val msaComponentInstanceName = PsiTreeUtil.findChildOfType(myFixture.file, MSAComponentInstanceName::class.java)

        val oldComponentInstanceName = msaComponentInstanceName?.name

        val expected = "NewTest"
        msaComponentInstanceName?.name = expected

        TestCase.assertEquals(expected, msaComponentInstanceName?.name)
        TestCase.assertNotSame(expected, oldComponentInstanceName)
    }

    fun testSetComponentName() {

        myFixture.configureByFiles("CashDeskSystem.secarc")

        val msaComponentName = PsiTreeUtil.findChildOfType(myFixture.file, MSAComponentName::class.java)

        val oldComponentName = msaComponentName?.name

        val expected = "NewTest"
        msaComponentName?.name = expected

        TestCase.assertEquals(expected, msaComponentName?.name)
        TestCase.assertNotSame(expected, oldComponentName)
    }

    fun testTrustLevelResolution() {

        myFixture.configureByFiles("CashDeskSystem.secarc")

        val msaComponentDeclaration = PsiTreeUtil.findChildOfType(myFixture.file, MSAComponentDeclaration::class.java)

        val absoluteTrustLevel = msaComponentDeclaration?.absoluteTrustLevel
        TestCase.assertEquals(absoluteTrustLevel, 0)
        val instanceName = msaComponentDeclaration?.instanceName
        TestCase.assertEquals(instanceName, "")
        val qualifiedName = msaComponentDeclaration?.qualifiedName
        TestCase.assertEquals(qualifiedName, "Generator.CashDeskSystem")
        val superComponents = msaComponentDeclaration?.superComponents
        TestCase.assertEquals(superComponents?.size, 0)
        val suppressAnnotation = msaComponentDeclaration?.suppressAnnotation?.policyList
        TestCase.assertEquals(suppressAnnotation?.size, 1)
    }

    fun testTrustLevelInstanceResolution() {

        myFixture.configureByFiles("CashDeskSystem.secarc")

        val msaComponentDeclaration = PsiTreeUtil.findChildOfType(myFixture.file, MSAComponentInstanceDeclaration::class.java)

        val absoluteTrustLevel = msaComponentDeclaration?.absoluteTrustLevel
        TestCase.assertEquals(absoluteTrustLevel, 0)
        val qualifiedName = msaComponentDeclaration?.qualifiedName
        TestCase.assertEquals(qualifiedName, "Generator.CashDeskSystem.CashDesk")
    }

    override fun getTestDataPath(): String {
        return "testData/Generator"
    }
}