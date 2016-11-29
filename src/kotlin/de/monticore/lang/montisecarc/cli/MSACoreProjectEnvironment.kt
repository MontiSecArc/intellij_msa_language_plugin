package de.monticore.lang.montisecarc.cli

import com.intellij.core.JavaCoreApplicationEnvironment
import com.intellij.core.JavaCoreProjectEnvironment
import com.intellij.openapi.Disposable
import com.intellij.psi.PsiManager

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
open class MSACoreProjectEnvironment(
        disposable: Disposable,
        applicationEnvironment: JavaCoreApplicationEnvironment
) : JavaCoreProjectEnvironment(disposable, applicationEnvironment) {
    override fun createCoreFileManager() = MSACliJavaFileManagerImpl(PsiManager.getInstance(project))
}