package de.monticore.lang.montisecarc.generator

import com.intellij.util.PathUtil
import freemarker.template.Configuration
import java.io.File
import java.io.StringWriter

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
class FreeMarker(val clazz : Class<Any>) {

    private var cfg = Configuration(Configuration.VERSION_2_3_23)
    init {

        with(cfg) {
            setDirectoryForTemplateLoading(File(PathUtil.getJarPathForClass(clazz)))
            defaultEncoding = "UTF-8"
            templateExceptionHandler = freemarker.template.TemplateExceptionHandler.RETHROW_HANDLER
        }
    }

    fun generateModelOutput(template: String, model: Map<String, Any>): String {
        val temp = cfg.getTemplate(template)

        /* Merge data-model with template */
        val out = StringWriter()
        temp.process(model, out)
        out.close()
        return out.toString()
    }
}