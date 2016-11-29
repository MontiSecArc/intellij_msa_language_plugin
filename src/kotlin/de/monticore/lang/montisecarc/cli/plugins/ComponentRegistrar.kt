package de.monticore.lang.montisecarc.cli.plugins

import com.intellij.mock.MockProject
import de.monticore.lang.montisecarc.psi.cli.CompilerConfiguration
import de.monticore.lang.montisecarc.psi.cli.CompilerConfigurationKey

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
interface ComponentRegistrar {
    companion object {
        val PLUGIN_COMPONENT_REGISTRARS: CompilerConfigurationKey<MutableList<ComponentRegistrar>> = CompilerConfigurationKey.create("plugin component registrars")
    }

    fun registerProjectComponents(project: MockProject, configuration: CompilerConfiguration)
}
