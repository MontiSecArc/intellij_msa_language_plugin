package de.monticore.lang.montisecarc.importing.data

import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase
import de.monticore.lang.montisecarc.importing.data.Component
import de.monticore.lang.montisecarc.importing.data.Importer
import de.monticore.lang.montisecarc.importing.data.NewMSAFileFromImporter
import junit.framework.TestCase
import org.junit.Assert.*
import java.io.InputStream

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
class NewMSAFileFromImporterTest : LightCodeInsightFixtureTestCase() {

    fun testImport() {

        val c4 = Component("4", "T4", 1)
        val c5 = Component("5", "T5", 3)
        val c2 = Component("2", "T2", 1, listOf(c4, c5), listOf(Connection(c4, c5)))
        c4.parent = c2
        c5.parent = c2
        val c3 = Component("3", "T3", 3)
        val c1 = Component("1", "T1", 2, listOf(c2, c3), listOf(Connection(c2, c3)))
        c2.parent = c1
        c3.parent = c1

        val modelFor = NewMSAFileFromImporter(MockImporter()).getModelFor(c1, listOf("2"), listOf<String>("3"), "de.import")

        val result = "package de.import;\r\n" +
                "import java.lang.String;\r\n" +
                "component T1 {\r\n" +
                "    trustlevel 2;\r\n" +
                "    \r\n" +
                "    \r\n" +
                "    component T2 t2 {\r\n" +
                "    trustlevel 1;\r\n" +
                "    port in String inDummy;\r\n" +
                "    \r\n" +
                "    component T4 t4 {\r\n" +
                "    trustlevel 1;\r\n" +
                "    \r\n" +
                "    \r\n" +
                "}\r\n" +
                "    component T5 t5 {\r\n" +
                "    trustlevel 3;\r\n" +
                "    \r\n" +
                "    \r\n" +
                "}\r\n" +
                "        connect t4.outDummy -> t5.inDummy;\r\n" +
                "}\r\n" +
                "    component T3 t3 {\r\n" +
                "    trustlevel 3;\r\n" +
                "    \r\n" +
                "    port out String outDummy;\r\n" +
                "}\r\n" +
                "    connect t2.outDummy -> t3.inDummy;\r\n" +
                "}"
        TestCase.assertEquals(modelFor, result)
    }

    fun testDeepImport() {

        val c3 = Component("3", "T3", 3)
        val c2 = Component("2", "T2", 1, listOf(c3))
        c3.parent = c2
        val c1 = Component("1", "T1", 2, listOf(c2))
        c2.parent = c1

        val modelFor = NewMSAFileFromImporter(MockImporter()).getModelFor(c1, listOf("1", "2"), listOf<String>("1", "3"), "de.import")

        val result = "package de.import;\r\n" +
                "import java.lang.String;\r\n" +
                "component T1 {\r\n" +
                "    trustlevel 2;\r\n" +
                "    port in String inDummy;\r\n" +
                "    port out String outDummy;\r\n" +
                "    component T2 t2 {\r\n" +
                "    trustlevel 1;\r\n" +
                "    port in String inDummy;\r\n" +
                "    \r\n" +
                "    component T3 t3 {\r\n" +
                "    trustlevel 3;\r\n" +
                "    \r\n" +
                "    port out String outDummy;\r\n" +
                "}\r\n" +
                "}\r\n" +
                "}"
        TestCase.assertEquals(modelFor, result)
    }
}

class MockImporter : Importer() {

    override fun toComponents(inputStream: InputStream, output: String, packageIdentifier: String): Triple<List<Component>, List<String>, List<String>> {

        return Triple(emptyList(), emptyList(), emptyList())
    }

}
