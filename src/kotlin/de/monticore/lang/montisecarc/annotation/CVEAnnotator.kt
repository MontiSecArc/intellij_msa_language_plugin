package de.monticore.lang.montisecarc.annotation

import com.beust.klaxon.JsonArray
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result
import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import de.monticore.lang.montisecarc.psi.MSACPEStatement
import de.monticore.lang.montisecarc.psi.MSAVisitor

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
class CVEAnnotator : Annotator {


    override fun annotate(element: PsiElement, holder: AnnotationHolder) = element.accept(object : MSAVisitor() {

        private val basePath = "https://cve.circl.lu/api"

        override fun visitCPEStatement(o: MSACPEStatement) {

            super.visitCPEStatement(o)

            val cpeText = o.string?.text?.replace("\"", "")

            if (cpeText != null) {

                //"https://cve.circl.lu/api/cpe2.3/cpe:2.3:a:nginx:nginx:1.9.9"
                fun getCVE(version: String) {
                    val (request, response, result) = "$basePath/$version/$cpeText".httpGet().responseString()
                    //do something with response
                    when (result) {
                        is Result.Success -> {
                            if (result.value != "None") {

                                val (_request, _response, _result) = "$basePath/cvefor/$cpeText".httpGet().responseString()
                                //do something with response
                                when (_result) {
                                    is Result.Success -> {
                                        val parser: Parser = Parser()
                                        val stringBuilder = StringBuilder(_result.value)
                                        val json = parser.parse(stringBuilder)

                                        if (json is JsonArray<*>) {

                                            json.forEach {

                                                if (it is JsonObject) {
                                                    val summary = it["summary"]
                                                    val cwe = it["cwe"]
                                                    val cvss = it["cvss"]
                                                    var cvssFloatValue: Float? = null

                                                    if (cvss != null && cvss is String) {
                                                        cvssFloatValue = cvss.toFloat()
                                                    }

                                                    if (cvssFloatValue != null && cvssFloatValue >= 7.0) {
                                                        holder.createErrorAnnotation(o, "$summary ($cwe, CVSS: $cvss)")
                                                    } (cvssFloatValue != null && (cvssFloatValue < 7.0 || cvssFloatValue >= 4.0 )) {

                                                    } else {
                                                        holder.createInfoAnnotation(o, "$summary ($cwe, CVSS: $cvss)")
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


                if (cpeText.contains("cpe:2.3")) {

                    getCVE("cpe2.3")
                } else if (cpeText.contains("cpe:2.2")) {

                    getCVE("cpe2.2")
                }
            }
        }
    })
}
