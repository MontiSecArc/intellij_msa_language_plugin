package de.monticore.lang.montisecarc.policy

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
abstract class BasePolicyTestCase: LightCodeInsightFixtureTestCase() {


    private var dataPath = "testData/policy"

    fun withPolicies(action: (PolicyLoader) -> (Unit)) {

        val policySchema = myFixture.configureByFile("${dataPath}/policy_schemes/Policies_v1.xsd")
        val policy = myFixture.configureByFile("${dataPath}/PolicyConfiguration.xml")

        val policyLoader = project.getComponent(PolicyLoader::class.java)
        policyLoader.clearLoadedPolicies()
        policyLoader.parsePolicy(policySchema.virtualFile.inputStream, policy.virtualFile)

        arrayOf("fixes/EncryptComponentInstanceIdentity.cyp",
                "fixes/MultipleAccessPoints.cyp",
                "fixes/UntrustedConnector.cyp",
                "inspections/AuthenticationOverUntrustedConnector.cyp",
                "inspections/ClientTampering.cyp",
                "inspections/DirectAccess.cyp",
                "inspections/EncryptedPaths.cyp",
                "inspections/EncryptStrongIdentity.cyp",
                "inspections/InformationFlowComponentPort.cyp",
                "inspections/InformationFlowPortComponent.cyp",
                "inspections/InformationFlowPortPort.cyp",
                "inspections/MultipleAccessPoints.cyp",
                "inspections/UntrustedConnector.cyp"
        ).forEach {

            val fixOrInspection = myFixture.configureByFile("${dataPath}/" + it)
            policyLoader.parsePolicy(policySchema.virtualFile.inputStream, fixOrInspection.virtualFile)
        }

        action(policyLoader)
    }
}
