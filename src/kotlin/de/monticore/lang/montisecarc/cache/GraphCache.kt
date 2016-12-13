package de.monticore.lang.montisecarc.cache

import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import com.intellij.psi.PsiFile
import de.monticore.lang.montisecarc.generator.graph.GraphGenerator
import de.monticore.lang.montisecarc.visualization.GraphDatabase
import org.neo4j.graphdb.GraphDatabaseService
import java.util.concurrent.TimeUnit

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
object GraphCache {

    val graphLoader: LoadingCache<PsiFile, GraphDatabaseService?> = Caffeine
            .newBuilder()
            .maximumSize(50)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .refreshAfterWrite(1, TimeUnit.MINUTES)
            .build({
                file: PsiFile -> createGraphDatabase(file)
            })

    private fun  createGraphDatabase(file: PsiFile): GraphDatabaseService? {

        val createDatabaseQuery = GraphGenerator().registerGenerators().generate(file)?.bufferedReader()?.use { it.readText() }

        if(createDatabaseQuery.isNullOrEmpty()) {
            return null
        }

        val graphDatabase = file.project.getComponent(GraphDatabase::class.java)
        return graphDatabase.createDatabase(createDatabaseQuery!!, "analyzer")
    }
}