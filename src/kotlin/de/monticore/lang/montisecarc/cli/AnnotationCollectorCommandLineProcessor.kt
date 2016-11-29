package de.monticore.lang.montisecarc.cli

import de.monticore.lang.montisecarc.cli.plugins.CliOption
import de.monticore.lang.montisecarc.cli.plugins.CliOptionProcessingException
import de.monticore.lang.montisecarc.cli.plugins.CommandLineProcessor
import de.monticore.lang.montisecarc.psi.cli.CompilerConfiguration

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
class AnnotationCollectorCommandLineProcessor : CommandLineProcessor {
    companion object {
        val ANNOTATION_COLLECTOR_COMPILER_PLUGIN_ID: String = "org.jetbrains.kotlin.kapt"

        val ANNOTATION_FILTER_LIST_OPTION: CliOption =
                CliOption("annotations", "<path>", "Annotation filter regular expressions, separated by commas", required = false)

        val OUTPUT_FILENAME_OPTION: CliOption =
                CliOption("output", "<path>", "File in which annotated declarations will be placed", required = false)

        val STUBS_PATH_OPTION: CliOption =
                CliOption("stubs", "<path>", "Output path for stubs", required = false)

        val INHERITED_ANNOTATIONS_OPTION: CliOption =
                CliOption("inherited", "<true/false>",
                        "True if collecting Kotlin class names for inherited annotations is needed", required = false)
    }

    override val pluginId: String = ANNOTATION_COLLECTOR_COMPILER_PLUGIN_ID

    override val pluginOptions: Collection<CliOption> =
            listOf(ANNOTATION_FILTER_LIST_OPTION, OUTPUT_FILENAME_OPTION, STUBS_PATH_OPTION, INHERITED_ANNOTATIONS_OPTION)

    override fun processOption(option: CliOption, value: String, configuration: CompilerConfiguration) {
        when (option) {
            ANNOTATION_FILTER_LIST_OPTION -> {
                val annotations = value.split(',').filter { !it.isEmpty() }
                configuration.put(AnnotationCollectorConfigurationKeys.ANNOTATION_FILTER_LIST, annotations)
            }
            OUTPUT_FILENAME_OPTION -> configuration.put(AnnotationCollectorConfigurationKeys.OUTPUT_FILENAME, value)
            STUBS_PATH_OPTION -> configuration.put(AnnotationCollectorConfigurationKeys.STUBS_PATH, value)
            INHERITED_ANNOTATIONS_OPTION -> configuration.put(AnnotationCollectorConfigurationKeys.INHERITED, value)
            else -> throw CliOptionProcessingException("Unknown option: ${option.name}")
        }
    }
}