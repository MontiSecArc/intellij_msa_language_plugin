package de.monticore.lang.montisecarc.inspections

import com.intellij.codeInspection.*
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiRecursiveElementWalkingVisitor
import de.monticore.lang.montisecarc.cache.GraphCache
import de.monticore.lang.montisecarc.policy.Policy
import de.monticore.lang.montisecarc.policy.PolicyLoader
import de.monticore.lang.montisecarc.psi.MSAFile
import de.monticore.lang.montisecarc.psi.MSAHighlightable
import de.monticore.lang.montisecarc.psi.MSASuppressAnnotation
import de.monticore.lang.montisecarc.psi.util.getPrevNonCommentSibling
import org.neo4j.graphdb.GraphDatabaseService
import org.neo4j.graphdb.Node
import org.neo4j.graphdb.PropertyContainer
import org.neo4j.graphdb.Relationship

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
class GraphQueryInspection : LocalInspectionTool() {

    val instance = Logger.getInstance(GraphQueryInspection::class.java)

    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<out ProblemDescriptor>? {

        if (file !is MSAFile) {

            return null
        }

        return doCheckFile(file, manager).orEmpty().toTypedArray()
    }

    @Synchronized
    private fun doCheckFile(file: PsiFile, manager: InspectionManager): List<ProblemDescriptor>? {

        if (file !is MSAFile) {
            return null
        }
        // Reset
        resetViolations(file)

        try {

            val graphDatabaseService = GraphCache.createGraphDatabase(file)
            if (graphDatabaseService != null) {

                val policyLoader = file.project.getComponent(PolicyLoader::class.java)

                instance.info("Found ${policyLoader.loadedPolicies.size} policies")
                return policyLoader.loadedPolicies.flatMap{ loadedPolicy ->

                    runInspection(file, graphDatabaseService, loadedPolicy).map {
                        manager.createProblemDescriptor(it.psiElement, loadedPolicy.inspection!!.description, true, it.quickFixes, it.problemHighlight)
                    }

                }
            }
        } catch (e: NoClassDefFoundError) {
            //Ignore Database Plugin not installed
        } catch (e: Exception) {

        }
        return null
    }

    private fun runInspection(file: MSAFile, graphDatabaseService: GraphDatabaseService, loadedPolicy: Policy): List<HighlightElement> {
        val graphQuery = loadedPolicy.inspection!!.inspection.orEmpty()

        var highlightableElements = emptyList<HighlightElement>()

        instance.info("Execute $graphQuery")
        if (graphQuery.isNullOrEmpty()) {

            return highlightableElements
        }


        val tx = graphDatabaseService.beginTx()

        try {

            val result = graphDatabaseService.execute(graphQuery) ?: return highlightableElements

            highlightableElements = result.iterator().asSequence()
                    .flatMap {
                        it.values.asSequence()
                    }
                    .filter {
                        (it is Node && it.graphElementCanBeHighlighted()) || it is Relationship
                    }
                    .map {

                        it as PropertyContainer
                        val element_offset = (it.getProperty("element_offset", "0") as String).toInt()

                        val filePath = it.getProperty("file_path", "") as String

                        if (element_offset > 0 && file.virtualFile.canonicalPath == filePath) {

                            file.findElementAt(element_offset)?.highlightElement(loadedPolicy)
                        } else {
                            null
                        }
                    }.filter {
                        it != null
                    }.requireNoNulls().toList()

            tx.success()
        } catch (e: Exception) {

            tx.failure()
        } finally {

            tx.close()
        }

        return highlightableElements
    }

    private fun resetViolations(file: PsiFile) {
        file.accept(object : PsiRecursiveElementWalkingVisitor() {

            override fun visitElement(element: PsiElement?) {

                super.visitElement(element)

                if (element is MSAHighlightable) {

                    element.resetPolicyViolations()
                }
            }
        })
    }

    private fun PsiElement.policyIsSuppressed(loadedPolicy: Policy): Boolean {

        /**
         * Search for Suppression Annotations in Parents and PrevSiblings
         */
        var element = this

        fun findSuppressedPolicy(prevNonCommentSibling: MSASuppressAnnotation): Boolean {
            val foundSuppressedPolicy = prevNonCommentSibling.policyList.any { it.text == loadedPolicy.id }
            if (foundSuppressedPolicy) {

                return true
            }
            return false
        }

        while (element != null && element !is MSAFile) {

            val prevNonCommentSibling = element.getPrevNonCommentSibling()

            if (prevNonCommentSibling is MSASuppressAnnotation) {

                if (findSuppressedPolicy(prevNonCommentSibling)) return true
            }

            if (element.firstChild is MSASuppressAnnotation) {

                if (findSuppressedPolicy(element.firstChild as MSASuppressAnnotation)) return true
            }

            element = element.parent
        }

        return false
    }

    private fun PsiElement.highlightElement(loadedPolicy: Policy): HighlightElement? {

        var element = this
        while (element !is MSAHighlightable && element != null) {

            element = element.parent
        }

        if (element.policyIsSuppressed(loadedPolicy)) {

            return null
        }

        if (element != null) {

            var arrayOfLocalQuickFixes = emptyArray<LocalQuickFix>()
            val fix = loadedPolicy.fix?.fix

            if (!fix.isNullOrEmpty()) {


                arrayOfLocalQuickFixes = arrayOf(GraphQueryQuickFix(fix!!, loadedPolicy.name, element))
            }

            (element as MSAHighlightable).addPolicyViolation(loadedPolicy.id)
            return HighlightElement(element, arrayOfLocalQuickFixes, ProblemHighlightType.valueOf(loadedPolicy.severity))
        }

        return null
    }
}

data class HighlightElement(val psiElement: PsiElement, val quickFixes: Array<LocalQuickFix>, val problemHighlight: ProblemHighlightType)