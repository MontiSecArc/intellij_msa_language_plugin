package de.monticore.lang.montisecarc.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
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
class ThirdPartyComponentAnnotator : Annotator {
    override fun annotate(element: PsiElement, holder: AnnotationHolder) = element.accept(object : MSAVisitor() {

        override fun visitComponentBody(o: MSAComponentBody) {

            val cpes = o.cpeStatementList.size
            val configurations = o.configurationStatementList.size

            if(cpes > 0 && configurations == 0) {

                holder.createErrorAnnotation(o.cpeStatementList.first(), "A third party component consists of a cpe and a configuration")

            } else if (cpes == 0 && configurations > 0) {

                holder.createErrorAnnotation(o.configurationStatementList.first(), "A third party component consists of a cpe and a configuration")
            }
            super.visitComponentBody(o)
        }
    })
}