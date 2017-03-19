package de.monticore.lang.montisecarc.annotation

import com.intellij.codeInsight.daemon.impl.quickfix.DeleteElementFix
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import de.monticore.lang.montisecarc.psi.MSAAccessStatement
import de.monticore.lang.montisecarc.psi.MSAComponentBody
import de.monticore.lang.montisecarc.psi.MSAVisitor

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
class SingleStatementsAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) = element.accept(object : MSAVisitor() {

        override fun visitComponentBody(o: MSAComponentBody) {

            if (o.configurationStatementList.size > 1) {

                annotateLeftStatements(holder, "configuration statement", o.configurationStatementList)
            }
            if (o.trustLevelRelationStatementList.size > 1) {

                annotateLeftStatements(holder, "trustlevel relation statement", o.trustLevelRelationStatementList)
            }
            if (o.trustLevelStatementList.size > 1) {

                annotateLeftStatements(holder, "trustlevel statement", o.trustLevelStatementList)
            }
            if (o.accessControlStatementList.size > 1) {

                annotateLeftStatements(holder, "access control statement", o.accessControlStatementList)
            }
            if (o.cpeStatementList.size > 1) {

                annotateLeftStatements(holder, "cpe statement", o.cpeStatementList)
            }
            if (o.accessStatementList.size > 1) {

                annotateLeftStatements(holder, "access roles statement", o.accessStatementList)
            }
            if (o.clearanceForStatementList.size > 1) {

                annotateLeftStatements(holder, "clearance for statement", o.clearanceForStatementList)
            }
            super.visitComponentBody(o)
        }

        override fun visitAccessStatement(o: MSAAccessStatement) {
            super.visitAccessStatement(o)

            val distinctRoleName = o.roleNameList.distinctBy { it.id.text }

            o.roleNameList.minus(distinctRoleName).forEach {

                holder.createWarningAnnotation(it, "Access role should only be defined once").registerFix(DeleteElementFix(it))
            }

            o.portAccessRoleList.forEach {

                val _distinctRoleName = it.roleNameList.distinctBy { it.id.text }

                it.roleNameList.minus(_distinctRoleName).forEach {

                    holder.createWarningAnnotation(it, "Access role should only be defined once").registerFix(DeleteElementFix(it))
                }
            }
        }
    })

    private fun annotateLeftStatements(holder: AnnotationHolder, text: String, statementList: List<PsiElement>) {

        statementList.subList(1, statementList.size).map {

            holder.createErrorAnnotation(it, "Component can only define one $text").registerFix(DeleteElementFix(it))
        }
    }
}