package de.monticore.lang.montisecarc.cli

import com.intellij.openapi.Disposable
import de.monticore.lang.montisecarc.psi.cli.CompilerConfiguration
import de.monticore.lang.montisecarc.psi.cli.CompilerConfigurationKey
import org.jetbrains.jps.model.java.impl.JavaSdkUtil
import org.junit.Test
import java.io.File

/**
 * Copyright 2016 Thomas Buning
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
class MSACoreEnvironmentTest {

    @Test
    fun createCoreEnvironment() {
        val moduleDirs = File("/Development/RWTH/Masterarbeit/MSA_Demo/").listFiles { it -> it.isDirectory }!!
        val compilerConfiguration = CompilerConfiguration()

        compilerConfiguration.addJavaSourceRoots(moduleDirs.toList())
        val jdkClassesRoots = JavaSdkUtil.getJdkClassesRoots(File(System.getProperty("java.home")), true)
        compilerConfiguration.addJvmClasspathRoots(jdkClassesRoots)
        compilerConfiguration.addMSASourceRoots(moduleDirs.map { it.absolutePath })

        compilerConfiguration.put(CompilerConfigurationKey.create("module name"), "test-module")
        val msaCoreEnvironment = MSACoreEnvironment.createForTests(Disposable { }, compilerConfiguration, emptyList<String>())
    }
}

fun CompilerConfiguration.addJvmClasspathRoot(file: File) {
    add(JVMConfigurationKeys.CONTENT_ROOTS, JvmClasspathRoot(file))
}

fun CompilerConfiguration.addJvmClasspathRoots(files: List<File>): Unit = files.forEach { addJvmClasspathRoot(it) }

@JvmOverloads fun CompilerConfiguration.addJavaSourceRoot(file: File, packagePrefix: String? = null) {
    add(JVMConfigurationKeys.CONTENT_ROOTS, JavaSourceRoot(file, packagePrefix))
}

@JvmOverloads fun CompilerConfiguration.addJavaSourceRoots(files: List<File>, packagePrefix: String? = null): Unit =
        files.forEach { addJavaSourceRoot(it, packagePrefix) }