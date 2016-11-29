package de.monticore.lang.montisecarc.cli

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
object AnnotationCollectorConfigurationKeys {
    val ANNOTATION_FILTER_LIST: CompilerConfigurationKey<List<String>> =
            CompilerConfigurationKey.create<List<String>>("annotation filter regular expressions")
    val OUTPUT_FILENAME: CompilerConfigurationKey<String> =
            CompilerConfigurationKey.create<String>("annotation file name")
    val STUBS_PATH: CompilerConfigurationKey<String> =
            CompilerConfigurationKey.create<String>("stubs output directory")
    val INHERITED: CompilerConfigurationKey<String> =
            CompilerConfigurationKey.create<String>("support inherited annotations")
}