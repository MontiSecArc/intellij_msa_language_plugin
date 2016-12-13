package de.monticore.lang.montisecarc.inspections

import com.intellij.codeInspection.*
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import de.monticore.lang.montisecarc.analyzer.inspections.graphElementCanBeHighlighted
import de.monticore.lang.montisecarc.cache.GraphCache
import de.monticore.lang.montisecarc.policy.Policy
import de.monticore.lang.montisecarc.policy.PolicyLoader
import de.monticore.lang.montisecarc.psi.MSAFile
import de.monticore.lang.montisecarc.psi.MSAHighlightable
import de.monticore.lang.montisecarc.psi.MSASuppressAnnotation
import de.monticore.lang.montisecarc.psi.util.getPrevNonCommentSibling
import org.neo4j.graphdb.Node
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
class GraphQueryInspection() : LocalInspectionTool() {

    val instance = Logger.getInstance(GraphQueryInspection::class.java)

    override fun checkFile(file: PsiFile, manager: InspectionManager, isOnTheFly: Boolean): Array<out ProblemDescriptor> {

        if (file !is MSAFile) {

            return emptyArray()
        }

        instance.info("Check File ${file.name}")
        val problems = mutableListOf<String>()
        val problemDescriptors = mutableListOf<ProblemDescriptor>()
        doCheckFile(file, {
            element, loadedPolicy, quickFixes, problemType ->
            val problemDescriptor = manager.createProblemDescriptor(element, loadedPolicy.inspection!!.description, isOnTheFly, quickFixes, problemType)

            problems.add(problemDescriptor.toString())
            problemDescriptors.add(problemDescriptor)
        })

        instance.info("Found ${problemDescriptors.size} problems")

        return problemDescriptors.toTypedArray()
    }

    private fun doCheckFile(file: PsiFile, callback: (psiElement: PsiElement, loadedPolicy: Policy, emptyArray: Array<LocalQuickFix>, problemHighlight: ProblemHighlightType) -> Unit) {

        if (file is MSAFile) {

            val graphDatabaseService = GraphCache.graphLoader.get(file)
            if (graphDatabaseService != null) {

                val policyLoader = file.project.getComponent(PolicyLoader::class.java)

                instance.info("Found ${policyLoader.loadedPolicies.size} policies")
                for (loadedPolicy in policyLoader.loadedPolicies) {


                    val graphQuery = loadedPolicy.inspection!!.inspection.orEmpty()

                    instance.info("Execute $graphQuery")
                    if (!graphQuery.isNullOrEmpty()) {

                        val result = graphDatabaseService.execute(graphQuery)

                        if (result != null) {

                            while (result.hasNext()) {

                                for ((key, value) in result.next()) {

                                    if (value is Node && value.graphElementCanBeHighlighted()) {

                                        val element_offset = value.getProperty("element_offset", "") as String

                                        val filePath = value.getProperty("file_path", "") as String

                                        val sameFile = file.virtualFile.canonicalPath == filePath

                                        if (!element_offset.isNullOrEmpty()) {

                                            val offset = element_offset.toInt()
                                            if (offset > 0 && sameFile) {

                                                file.findElementAt(offset)?.highlightElement(loadedPolicy, callback)
                                            }
                                        }

                                    } else if (value is Relationship) {

                                        val element_offset = value.getProperty("element_offset", "") as String

                                        val filePath = value.getProperty("file_path", "") as String

                                        val sameFile = file.virtualFile.canonicalPath == filePath

                                        if (!element_offset.isNullOrEmpty()) {

                                            val offset = element_offset.toInt()
                                            if (offset > 0 && sameFile) {

                                                file.findElementAt(offset)?.highlightElement(loadedPolicy, callback)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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

        while(element != null && element !is MSAFile) {

            val prevNonCommentSibling = element.getPrevNonCommentSibling()

            if (prevNonCommentSibling is MSASuppressAnnotation) {

                if (findSuppressedPolicy(prevNonCommentSibling)) return true
            }

            if(element.firstChild is MSASuppressAnnotation) {

                if (findSuppressedPolicy(element.firstChild as MSASuppressAnnotation)) return true
            }

            element = element.parent
        }

        return false
    }

    private fun PsiElement.highlightElement(loadedPolicy: Policy, callback: (PsiElement, Policy, Array<LocalQuickFix>, ProblemHighlightType) -> Unit) {

        var element = this
        while(element !is MSAHighlightable && element != null) {

            element = element.parent
        }

        if(element.policyIsSuppressed(loadedPolicy)) {

            return
        }

        if(element != null) {

            (element as MSAHighlightable).addPolicyViolation(loadedPolicy.id)
            callback(element, loadedPolicy, emptyArray<LocalQuickFix>(), ProblemHighlightType.valueOf(loadedPolicy.severity))
        }
    }
}