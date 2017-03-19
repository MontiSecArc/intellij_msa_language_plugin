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

            super.visitPortElement(o)
            val portInstanceName = o.portInstanceName?.text
            if(o.portInstanceName != null) {
                checkInstanceName(holder, o.portInstanceName!!, portInstanceName)
            }
        }

        override fun visitComponentSignature(o: MSAComponentSignature) {

            super.visitComponentSignature(o)
            if(o.componentInstanceName != null) {
                checkInstanceName(holder, o.componentInstanceName!!, o.componentInstanceName!!.text)
            }
            checkTypeName(holder, o.componentName, o.componentName.text)
        }

        override fun visitConfigurationStatement(o: MSAConfigurationStatement) {

            super.visitConfigurationStatement(o)
            val config = o.id?.text
            if(!config.isNullOrEmpty() && config!!.first().isUpperCase()) {

                holder.createWeakWarningAnnotation(o, "Configuration names are supposed to be lower case")
            }
        }

        override fun visitRoleName(o: MSARoleName) {

            super.visitRoleName(o)
            if(o.id.text.first().isUpperCase()) {

                holder.createWeakWarningAnnotation(o, "Role names are supposed to be lower case")
            }
        }
    })

    private fun checkTypeName(holder: AnnotationHolder, o: MSACompositeElement, typeName: String?) {
        if (typeName != null && typeName.first().isLowerCase()) {

            holder.createWeakWarningAnnotation(o, "Type names are supposed to be upper case")
        }
    }

    private fun checkInstanceName(holder: AnnotationHolder, o: MSACompositeElement, instanceName: String?) {
        if (instanceName != null && instanceName.first().isUpperCase()) {

            holder.createWeakWarningAnnotation(o, "Instance names are supposed to be lower case")
        }
    }
}