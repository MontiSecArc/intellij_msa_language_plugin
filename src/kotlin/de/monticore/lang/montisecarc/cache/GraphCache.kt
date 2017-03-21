package de.monticore.lang.montisecarc.cache

import com.intellij.psi.PsiFile
import de.monticore.lang.montisecarc.generator.graph.GraphGenerator
import de.monticore.lang.montisecarc.visualization.GraphDatabase
import org.neo4j.graphdb.GraphDatabaseService

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
class GraphCache {

    companion object {
        fun createGraphDatabase(file: PsiFile): GraphDatabaseService? {

            val createDatabaseQuery = GraphGenerator()
            createDatabaseQuery.generate(file)
            val input = createDatabaseQuery.generatedInputStream?.bufferedReader()?.use { it.readText() }

            if (input.isNullOrEmpty()) {
                return null
            }

            try {
                val graphDatabase = file.project.getComponent(GraphDatabase::class.java)
                return graphDatabase.createDatabase(input!!, "analyzer")
            } catch (e: Exception) {

                //Graph Database Plugin not installed, fail gracefully
            }
            return null
        }
    }
}