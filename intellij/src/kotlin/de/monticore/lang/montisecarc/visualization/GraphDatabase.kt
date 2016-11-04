package de.monticore.lang.montisecarc.visualization

import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.factory.GraphDatabaseFactory
import org.neo4j.graphdb.factory.GraphDatabaseSettings
import java.io.File

/**
 * Created by thomasbuning on 30.10.16.
 */
class GraphDatabase {

    var graphDb: GraphDatabaseService? = null

    init {
        val bolt = GraphDatabaseSettings.boltConnector("0")
        graphDb = GraphDatabaseFactory().newEmbeddedDatabaseBuilder(File("resources/gen/graph_db/"))
                .setConfig(bolt.enabled, "true")
                .setConfig(bolt.address, "localhost:7687")
                .newGraphDatabase()

        registerShutdownHook(graphDb)
    }

    private object Holder { val INSTANCE = GraphDatabase() }

    companion object {
        val instance: GraphDatabase by lazy { Holder.INSTANCE }
    }

    fun createDatabase(graphQuery: String) {

        if(graphDb == null) {

            return
        }

        graphDb!!.execute("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE n, r")
        graphDb!!.execute(graphQuery)
    }

    private fun registerShutdownHook(graphDb: GraphDatabaseService?) {

        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook(object : Thread() {
            override fun run() {

                graphDb?.shutdown()
            }
        })
    }
}