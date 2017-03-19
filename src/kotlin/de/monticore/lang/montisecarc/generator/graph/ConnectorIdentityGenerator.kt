package de.monticore.lang.montisecarc.generator.graph

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import de.monticore.lang.montisecarc.generator.FreeMarker
import de.monticore.lang.montisecarc.generator.MSAGenerator
import de.monticore.lang.montisecarc.psi.*

class ConnectorIdentityGenerator : MSAGenerator() {

    fun getIdentifier(psiElement: PsiElement, componentInstanceNames: List<MSAComponentInstanceName>): String? {
        var identifier: String? = null
        if (componentInstanceNames.isEmpty()) {

            // Source is Enclosing Component
            val startComponent = PsiTreeUtil.getParentOfType(psiElement, MSAComponentDeclaration::class.java)

            if (startComponent != null) {
                identifier = ComponentDeclarationGenerator.createComponentIdentifier(startComponent)
            }
        } else {

            val startComponent = componentInstanceNames.last().references[0].resolve()

            if (startComponent != null) {

                val msaComponentDeclaration = PsiTreeUtil.getParentOfType(startComponent, MSAComponentDeclaration::class.java)
                val msaComponentInstanceDeclaration = PsiTreeUtil.getParentOfType(startComponent, MSAComponentInstanceDeclaration::class.java)

                if (msaComponentDeclaration != null) {

                    identifier = ComponentDeclarationGenerator.createComponentIdentifier(msaComponentDeclaration)
                } else if (msaComponentInstanceDeclaration != null) {

                    val msaComponentName = msaComponentInstanceDeclaration.componentNameWithTypeProjectionList.last().componentName.references[0].resolve()

                    val componentDeclaration = PsiTreeUtil.getParentOfType(msaComponentName, MSAComponentDeclaration::class.java)

                    if (componentDeclaration != null && componentDeclaration is MSAComponentDeclaration) {

                        identifier = ComponentDeclarationGenerator.createComponentIdentifier(componentDeclaration)
                    }
                }
            }
        }
        return identifier
    }

    override fun generate(psiElement: PsiElement): Any? {

        if (psiElement is MSAConnector) {

            val weak = psiElement.node.findChildByType(MSATokenElementTypes.WEAK)
            val strong = psiElement.node.findChildByType(MSATokenElementTypes.STRONG)

            if (weak != null || strong != null) {

                val startComponentInstanceNames = psiElement.connectSource.qualifiedIdentifier.componentInstanceNameList

                val startIdentifier = getIdentifier(psiElement, startComponentInstanceNames)
                if (!startIdentifier.isNullOrEmpty()) {


                    val connectors = mutableListOf<String>()

                    val link = if (weak != null) ":WEAK" else ":STRONG"
                    psiElement.connectTargetList.forEach {

                        val stopComponentInstanceNames = it.qualifiedIdentifier.componentInstanceNameList

                        val stopIdentifier = getIdentifier(psiElement, stopComponentInstanceNames)

                        if (!stopIdentifier.isNullOrEmpty()) {
                            val connector_model = mutableMapOf<String, Any>()
                            connector_model.put("relationship_type", link)
                            connector_model.put("start_port", startIdentifier!!)
                            connector_model.put("target_port", stopIdentifier!!)
                            connector_model.put("element_offset", psiElement.textOffset)
                            val connector = FreeMarker(this.javaClass).generateModelOutput("ToGraph/ConnectorMacro.ftl", connector_model)

                            connectors.add(connector)
                        }
                    }

                    return connectors
                }
            }
        }
        return null
    }

}