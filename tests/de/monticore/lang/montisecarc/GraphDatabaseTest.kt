package de.monticore.lang.montisecarc

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import de.monticore.lang.montisecarc.visualization.GraphDatabase
import junit.framework.TestCase

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
class GraphDatabaseTest: LightCodeInsightFixtureTestCase() {

    fun testCreateGraphDatabase() {

        val component = myFixture.project.getComponent(GraphDatabase::class.java)

        TestCase.assertNotNull(component)

        TestCase.assertTrue(component.isAvailable())

        val database = component.createDatabase("create (n:Test {name:'Test'})", "analyzer")

        TestCase.assertNotNull(database)

        val transaction = database!!.beginTx()
        TestCase.assertEquals(database!!.allNodes.count(), 1)
        transaction.success()

        val graphDatabase = component.createDatabase("create (n:Test {name:'Test'})", "graph_editor")

        TestCase.assertNotNull(graphDatabase)
    }
}