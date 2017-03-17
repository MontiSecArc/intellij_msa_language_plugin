package de.monticore.lang.montisecarc.completion

import com.intellij.codeInsight.completion.*
import com.intellij.codeInsight.lookup.AutoCompletionPolicy
import com.intellij.codeInsight.lookup.LookupElement
import com.intellij.codeInsight.lookup.LookupElementBuilder
import com.intellij.codeInsight.template.TemplateManager
import com.intellij.codeInsight.template.impl.TemplateSettings
import com.intellij.openapi.editor.EditorModificationUtil
import com.intellij.util.ObjectUtils
import com.intellij.util.ProcessingContext
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

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
class MSAKeywordCompletionProvider: CompletionProvider<CompletionParameters> {

    var myPriority: Int = 0
    var myInsertHandler: InsertHandler<LookupElement>? = null
    var myCompletionPolicy: AutoCompletionPolicy? = null
    var myKeywords: Array<String>

    constructor(myPriority: Int, myInsertHandler: InsertHandler<LookupElement>?, myCompletionPolicy: AutoCompletionPolicy?, myKeywords: Array<String>) : super() {
        this.myPriority = myPriority
        this.myInsertHandler = myInsertHandler
        this.myCompletionPolicy = myCompletionPolicy
        this.myKeywords = myKeywords
    }

    constructor(myPriority: Int, myKeywords: Array<String>) : super() {
        this.myPriority = myPriority
        this.myKeywords = myKeywords
    }

    constructor(myPriority: Int, myCompletionPolicy: AutoCompletionPolicy?, myKeywords: Array<String>) : super() {
        this.myPriority = myPriority
        this.myCompletionPolicy = myCompletionPolicy
        this.myKeywords = myKeywords
    }

    constructor(myPriority: Int, myInsertHandler: InsertHandler<LookupElement>?, myKeywords: Array<String>) : super() {
        this.myPriority = myPriority
        this.myInsertHandler = myInsertHandler
        this.myKeywords = myKeywords
    }

    override fun addCompletions(@NotNull parameters: CompletionParameters, context: ProcessingContext, @NotNull result: CompletionResultSet) {
        for (keyword in myKeywords) {
            result.addElement(createKeywordLookupElement(keyword))
        }
    }

    @NotNull
    private fun createKeywordLookupElement(@NotNull keyword: String): LookupElement {
        val insertHandler = ObjectUtils.chooseNotNull(myInsertHandler,
                createTemplateBasedInsertHandler("msa_lang_" + keyword))
        val result = createKeywordLookupElement(keyword, myPriority, insertHandler)
        return if (myCompletionPolicy != null) myCompletionPolicy!!.applyPolicy(result) else result
    }

    fun createKeywordLookupElement(@NotNull keyword: String,
                                   priority: Int,
                                   @Nullable insertHandler: InsertHandler<LookupElement>): LookupElement {
        val builder = LookupElementBuilder.create(keyword).withBoldness(true).withInsertHandler(insertHandler)
        return PrioritizedLookupElement.withPriority(builder, priority.toDouble())
    }

    @Nullable
    fun createTemplateBasedInsertHandler(@NotNull templateId: String): InsertHandler<LookupElement> {
        return InsertHandler { context, _ ->
            val template = TemplateSettings.getInstance().getTemplateById(templateId)
            val editor = context.editor
            if (template != null) {
                editor.document.deleteString(context.startOffset, context.tailOffset)
                TemplateManager.getInstance(context.project).startTemplate(editor, template)
            } else {
                val currentOffset = editor.caretModel.offset
                val documentText = editor.document.immutableCharSequence
                if (documentText.length <= currentOffset || documentText[currentOffset] != ' ') {
                    EditorModificationUtil.insertStringAtCaret(editor, " ")
                } else {
                    EditorModificationUtil.moveCaretRelatively(editor, 1)
                }
            }
        }
    }
}