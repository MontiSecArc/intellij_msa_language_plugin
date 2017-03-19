package de.monticore.lang.montisecarc.inspections

import com.graphaware.tx.event.improved.api.LazyTransactionData
import com.intellij.codeInspection.LocalQuickFix
import com.intellij.codeInspection.ProblemDescriptor
import com.intellij.openapi.project.Project
import de.monticore.lang.montisecarc.cache.GraphCache
import de.monticore.lang.montisecarc.psi.MSAElementFactory
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.event.TransactionData
import org.neo4j.graphdb.event.TransactionEventHandler

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
class GraphQueryQuickFix(val graphQuery: String, val flawName: String) : LocalQuickFix {

    fun transactional(graphDb: GraphDatabaseService, body: GraphDatabaseService.() -> Unit) {
        val tx = graphDb.beginTx()!!
        try {
            graphDb.body()
            tx.success()
        } catch(e: Exception) {
            tx.failure()
        } finally {
            tx.close()
        }
    }

    override fun getFamilyName(): String = "MSA"

    override fun getName(): String = "Fix $flawName"

    override fun startInWriteAction(): Boolean = true

    override fun applyFix(project: Project, descriptor: ProblemDescriptor) {

        try {
            val graphDatabaseService = GraphCache.createGraphDatabase(descriptor.psiElement.containingFile)
            if (graphDatabaseService != null) {


                if (!graphQuery.isNullOrEmpty()) {

                    val handler = object : TransactionEventHandler<Any?> {

                        override fun afterRollback(data: TransactionData?, state: Any?) {

                        }

                        override fun beforeCommit(data: TransactionData?): Any? {

                            val lazyTransactionData = LazyTransactionData(data)
                            lazyTransactionData.allDeletedRelationships.forEach {

                                val element_offset = it.getProperty("element_offset", "") as String

                                if (!element_offset.isNullOrEmpty()) {

                                    descriptor.psiElement.parent.parent.node.removeChild(descriptor.psiElement.parent.node)
                                }
                            }

                            lazyTransactionData.allCreatedRelationships.forEach {

                                val msaConnector = MSAElementFactory.createConnector(descriptor.psiElement.project, it.startNode.getProperty("instanceName") as String, it.endNode.getProperty("instanceName") as String, it.isType(org.neo4j.graphdb.RelationshipType.withName("ENCRYPTED")))

                                descriptor.psiElement.parent.parent.add(msaConnector)
                            }

                            return null
                        }

                        override fun afterCommit(data: TransactionData?, state: Any?) {


                        }
                    }

                    graphDatabaseService.registerTransactionEventHandler(handler)

                    transactional(graphDatabaseService) {
                        graphDatabaseService.execute(graphQuery)
                    }
                }
            }
        } catch (e: NoClassDefFoundError) {
            //Ignore Database Plugin not installed
        }
    }
}
