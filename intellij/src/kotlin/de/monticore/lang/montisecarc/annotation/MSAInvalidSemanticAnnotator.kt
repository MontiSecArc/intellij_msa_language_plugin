package de.monticore.lang.montisecarc.annotation

import com.intellij.lang.annotation.AnnotationHolder
import com.intellij.lang.annotation.Annotator
import com.intellij.psi.PsiElement
import de.monticore.lang.montisecarc.psi.*

/**
 * Created by thomasbuning on 28.09.16.
 */
class MSAInvalidSemanticAnnotator : Annotator {


    override fun annotate(element: PsiElement, holder: AnnotationHolder) = element.accept(object : MSAVisitor() {

        override fun visitPortElement(o: MSAPortElement) {

            val portInstanceName = o.portInstanceName?.text
            if(o.portInstanceName != null) {
                checkInstanceName(holder, o.portInstanceName!!, portInstanceName)
            }

            if(o.referenceType != null) {
                val lastReference = o.referenceType!!.referenceTypeList.orEmpty()
                if (lastReference.isNotEmpty()) {

                    checkTypeName(holder, lastReference.last(), lastReference.last().text)
                }
            }
        }

        override fun visitComponentSignature(o: MSAComponentSignature) {

            if(o.componentInstanceName != null) {
                checkInstanceName(holder, o.componentInstanceName!!, o.componentInstanceName!!.text)
            }
            checkTypeName(holder, o.componentName, o.componentName.text)
        }
    })

    private fun checkTypeName(holder: AnnotationHolder, o: MSACompositeElement, typeName: String?) {
        if (typeName != null && typeName.first().isLowerCase()) {

            holder.createErrorAnnotation(o, "Type names are supposed to be lower case")
        }
    }

    private fun checkInstanceName(holder: AnnotationHolder, o: MSACompositeElement, instanceName: String?) {
        if (instanceName != null && instanceName.first().isUpperCase()) {

            holder.createErrorAnnotation(o, "Instance names are supposed to be lower case")
        }
    }
}