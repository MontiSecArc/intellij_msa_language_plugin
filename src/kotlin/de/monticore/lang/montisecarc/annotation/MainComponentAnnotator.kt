package de.monticore.lang.montisecarc.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import com.intellij.psi.search.searches.ReferencesSearch
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.psi.*

/**
 * Copyright 2016 Thomas Buning
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
class MainComponentAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) = element.accept(object : MSAVisitor() {

        override fun visitComponentDeclaration(o: MSAComponentDeclaration) {
            super.visitComponentDeclaration(o)

            val componentName = o.componentSignature?.componentNameWithType?.componentName

            if (componentName != null) {
                val instanceName = o.componentSignature?.componentInstanceName

                if (instanceName == null) {
                    val collection = ReferencesSearch.search(componentName).findAll()

                    if (collection.isEmpty()) {

                        // Not referenced therefore, main component
                        val peps = o.componentBody?.accessControlStatementList?.size ?: 0
                        val roles = o.componentBody?.accessStatementList?.size ?: 0

                        if (peps == 0 && roles == 0) {

                            holder.createErrorAnnotation(o.componentSignature!!, "Main component has either access control or access roles")
                        }
                    }
                }
            }
        }

        override fun visitAccessControlStatement(o: MSAAccessControlStatement) {
            super.visitAccessControlStatement(o)

            if (o.node.findChildByType(MSATokenElementTypes.ON) != null) {

                val msaComponentDeclaration = PsiTreeUtil.getParentOfType(o, MSAComponentDeclaration::class.java)

                val accessRoles = msaComponentDeclaration?.componentBody?.accessStatementList?.size ?: 0

                if (accessRoles == 0) {

                    msaComponentDeclaration?.componentBody?.componentDeclarationList?.forEach {

                        val accessRoleCount = it.componentBody?.accessStatementList?.size ?: 0
                        if (accessRoleCount == 0) {

                            holder.createErrorAnnotation(it.componentSignature!!, "Access control is activated therefore, access roles need to be defined in this component or main component")
                        }
                    }

                    msaComponentDeclaration?.componentBody?.componentInstanceDeclarationList?.forEach {

                        val compName = it.componentNameWithTypeProjectionList.last().componentName
                        val references = compName.references
                        if (references.isNotEmpty()) {

                            val resolve = references[0].resolve() as? MSAComponentName

                            val declaration = PsiTreeUtil.getParentOfType(resolve, MSAComponentDeclaration::class.java)
                            if (declaration != null) {

                                val foundAccessRoles = declaration.componentBody?.accessStatementList?.size ?: 0

                                if (foundAccessRoles == 0) {

                                    holder.createErrorAnnotation(compName, "Access control is activated therefore, access roles need to be defined in this component or main component")
                                }
                            }
                        }
                    }
                }
            }
        }
    })
}