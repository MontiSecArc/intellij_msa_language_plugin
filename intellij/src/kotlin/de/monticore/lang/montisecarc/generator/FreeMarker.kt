package de.monticore.lang.montisecarc.generator

import com.intellij.util.PathUtil
import freemarker.template.Configuration
import java.io.File
import java.io.StringWriter

/**
 * Created by thomasbuning on 30.10.16.
 */
class FreeMarker {

    private var cfg = Configuration(Configuration.VERSION_2_3_23)
    init {

        with(cfg) {
            setDirectoryForTemplateLoading(File(PathUtil.getJarPathForClass(FreeMarker::class.java)))
            defaultEncoding = "UTF-8"
            templateExceptionHandler = freemarker.template.TemplateExceptionHandler.RETHROW_HANDLER
        }
    }

    private object Holder {
        val INSTANCE = FreeMarker()
    }

    companion object {
        val instance: FreeMarker by lazy { Holder.INSTANCE }
    }


    fun generateModelOutput(template: String, model: Map<String, Any>): String {
        val temp = instance.cfg.getTemplate(template)

        /* Merge data-model with template */
        val out = StringWriter()
        temp.process(model, out)
        out.close()
        return out.toString()
    }
}