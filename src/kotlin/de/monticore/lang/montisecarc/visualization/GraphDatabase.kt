package de.monticore.lang.montisecarc.visualization

import com.intellij.openapi.Disposable
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.ProjectComponent
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import de.tbuning.neo4j.graphdatabase.Neo4JGraphDatabase
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
class GraphDatabase(private val project: Project) : ProjectComponent, Disposable {


    val instance = Logger.getInstance(GraphDatabase::class.java)

    override fun disposeComponent() {

        dispose()
    }

    override fun projectClosed() {

        dispose()
    }

    override fun initComponent() {
    }

    private var arrayOfNeo4JGraphDatabases: Array<out Neo4JGraphDatabase>? = null

    override fun projectOpened() {

        try {
            arrayOfNeo4JGraphDatabases = ApplicationManager.getApplication().getExtensions(Neo4JGraphDatabase.EP_NAME)

            arrayOfNeo4JGraphDatabases?.forEach {

                it.startDatabase(project.basePath.orEmpty())
            }
        } catch (ex: Exception) {

            Logger.getInstance(GraphDatabase::class.java).info("Graph Database Plugin not installed")
        }
    }

    override fun dispose() {

        arrayOfNeo4JGraphDatabases?.forEach(Neo4JGraphDatabase::dispose)
    }

    fun isAvailable():Boolean {

        if(arrayOfNeo4JGraphDatabases == null) {

            return false
        } else {

            return arrayOfNeo4JGraphDatabases!!.isNotEmpty()
        }
    }

    override fun getComponentName(): String = javaClass.name


    fun createDatabase(graphQuery: String, forDir: String = "graph_editor"): GraphDatabaseService? {

        instance.info("Create Database $graphQuery in $forDir")
        val database = arrayOfNeo4JGraphDatabases?.find { it.databaseFolder == forDir }

        if (database != null) {

            return database.createDatabase(graphQuery)
        }
        return null
    }
}