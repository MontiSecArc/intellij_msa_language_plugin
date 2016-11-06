package de.monticore.lang.montisecarc.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.psi.PsiFile
import de.monticore.lang.montisecarc.generator.GraphGeneratorBuilder
import de.monticore.lang.montisecarc.visualization.GraphDatabase

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
class MSAGenerateGraph: AnAction() {
    override fun actionPerformed(e: AnActionEvent?) {

        val psiFile = e?.dataContext?.getData(DataKeys.PSI_FILE.name) as PsiFile

        val createGraph = GraphGeneratorBuilder
                .addFile(psiFile)
                .build()
                .createGraph()
        GraphDatabase.instance.createDatabase(createGraph)
    }
}