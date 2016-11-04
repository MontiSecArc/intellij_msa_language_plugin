package de.monticore.lang.montisecarc.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.DataKeys
import com.intellij.psi.PsiFile
import de.monticore.lang.montisecarc.generator.GraphGenerator
import de.monticore.lang.montisecarc.visualization.GraphDatabase

/**
 * Created by thomasbuning on 30.10.16.
 */
class MSAGenerateGraph: AnAction() {
    override fun actionPerformed(e: AnActionEvent?) {

        val psiFile = e?.dataContext?.getData(DataKeys.PSI_FILE.name) as PsiFile

        val createGraph = GraphGenerator().createGraph(psiFile)
        GraphDatabase.instance.createDatabase(createGraph)
    }
}