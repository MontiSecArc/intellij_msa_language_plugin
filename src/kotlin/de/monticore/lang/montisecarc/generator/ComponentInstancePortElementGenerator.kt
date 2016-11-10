package de.monticore.lang.montisecarc.generator

import com.intellij.psi.PsiElement
import de.monticore.lang.montisecarc.psi.MSAComponentDeclaration
import de.monticore.lang.montisecarc.psi.MSAComponentInstanceDeclaration

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
class ComponentInstancePortElementGenerator : MSAGenerator() {
    override fun generate(psiElement: PsiElement): Any? {

        if(psiElement is MSAComponentInstanceDeclaration) {
            val componentName = psiElement.componentNameWithTypeList.last().componentName
            if(componentName.references.isNotEmpty()) {

                val componentDeclaration = componentName.references[0].resolve()
                if(componentDeclaration != null && componentDeclaration is MSAComponentDeclaration) {

                    val portElementNodes = mutableListOf<String>()
                    val connectors = mutableListOf<String>()
                    for (msaPortDeclaration in componentDeclaration.componentBody?.portDeclarationList.orEmpty()) {

                        for (msaPortElement in msaPortDeclaration.portElementList) {
                            val portElementNode = PortElementGenerator.createPortElementNode(msaPortElement)
                            portElementNodes.add(portElementNode)
                            val portIdentifier = PortElementGenerator.createPortIdentifier(msaPortElement)

                            for (msaComponentInstanceName in psiElement.componentInstanceNameList) {

                                val componentIdentifier = ComponentInstanceDeclarationGenerator.createComponentInstanceIdentifier(psiElement, msaComponentInstanceName.name)

                                val connector = PortElementConnectorGenerator.getModel(msaPortElement.direction, componentIdentifier, portIdentifier)
                                connectors.add(connector)
                            }
                        }
                    }
                    return Pair(portElementNodes, connectors)
                }
            }
        }
        return null
    }

}