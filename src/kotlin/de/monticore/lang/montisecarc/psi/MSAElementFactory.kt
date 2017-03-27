package de.monticore.lang.montisecarc.psi

import com.intellij.openapi.project.Project
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFileFactory
import de.monticore.lang.montisecarc.MSAFileType

/**
 *  Copyright 2016 thomasbuning

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

object MSAElementFactory {

    fun createPortInstanceName(project: Project, name: String): MSAPortInstanceName {

        return createFile(project, "component A { port in A $name; }").firstChild.children[1].children[0].children[0].children[1] as MSAPortInstanceName
    }

    fun createConnector(project: Project, from: String, to: String, isEncrypted: Boolean) : MSAConnector {

        val encrypted = if (isEncrypted) "encrypted" else ""
        return createFile(project, "component A { connect $encrypted $from -> $to; }").firstChild.children[1].children[0] as MSAConnector
    }

    fun createFile(project: Project, text: String): MSAFile {
        val name = "dummy.secarc"
        return PsiFileFactory.getInstance(project).createFileFromText(name, MSAFileType.instance, text) as MSAFile
    }

    fun createPackageStatement(project: Project, actualPackage: String): MSAPackageClause {
        val file = createFile(project, "package $actualPackage;")
        return file.firstChild as MSAPackageClause
    }

    fun createImportStatement(project: Project, actualImport: String): MSAImportDeclaration {
        val file = createFile(project, "import $actualImport;")
        return file.firstChild as MSAImportDeclaration
    }

    fun createSuppressPolicyAnnotation(project: Project, commentText: String): PsiElement {

        val msaFile = createFile(project, commentText + " component A {}")

        return msaFile.firstChild.firstChild
    }
}