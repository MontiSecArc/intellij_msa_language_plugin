package de.monticore.lang.montisecarc.psi.impl

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiReference
import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.util.containers.isNullOrEmpty
import de.monticore.lang.montisecarc.MSAFileElementType
import de.monticore.lang.montisecarc.psi.*
import de.monticore.lang.montisecarc.psi.util.elementType

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

class MSAPsiImplUtil {

    companion object {

        @JvmStatic fun getPortName(element: MSAPortInstanceName): String {

            return element.text
        }

        @JvmStatic fun setName(element: MSAPortInstanceName, newName: String): PsiElement {

            val property = MSAElementFactory.createPortInstanceName(element.project, newName)
            val keyNode = element.node
            val newKeyNode = property.firstChild.node
            element.node.replaceChild(keyNode, newKeyNode)
            return element
        }

        @JvmStatic fun setName(element: MSAComponentInstanceName, newName: String): PsiElement {

            val property = MSAElementFactory.createPortInstanceName(element.project, newName)
            val keyNode = element.node
            val newKeyNode = property.firstChild.node
            element.node.replaceChild(keyNode, newKeyNode)
            return element
        }

        @JvmStatic fun setName(element: MSAComponentName, newName: String): PsiElement {

            val property = MSAElementFactory.createPortInstanceName(element.project, newName)
            val keyNode = element.node
            val newKeyNode = property.firstChild.node
            element.node.replaceChild(keyNode, newKeyNode)
            return element
        }

        @JvmStatic fun getName(element: MSAComponentInstanceName): String {

            return element.text
        }

        @JvmStatic fun getName(element: MSAComponentName): String {

            return element.text
        }

        @JvmStatic fun getName(element: MSAComponentDeclaration): String {

            return element.qualifiedName
        }

        @JvmStatic fun getName(element: MSAComponentInstanceDeclaration): String {

            return element.componentInstanceNameList.map { it.text }.joinToString()
        }

        @JvmStatic fun getReferences(element: MSAPortInstanceName): Array<out PsiReference> {

            return ReferenceProvidersRegistry.getReferencesFromProviders(element)
        }

        @JvmStatic fun getReferences(element: MSAComponentInstanceName): Array<out PsiReference> {

            return ReferenceProvidersRegistry.getReferencesFromProviders(element)
        }

        @JvmStatic fun getReferences(element: MSAComponentName): Array<out PsiReference> {

            return ReferenceProvidersRegistry.getReferencesFromProviders(element)
        }

        @JvmStatic fun getReferences(element: MSAJavaReference): Array<out PsiReference> {

            return ReferenceProvidersRegistry.getReferencesFromProviders(element)
        }

        @JvmStatic fun getReferences(element: MSAJavaClassReference): Array<out PsiReference> {

            return ReferenceProvidersRegistry.getReferencesFromProviders(element)
        }

        @JvmStatic fun getReferencedPortElement(element: MSAPortInstanceName): MSAPortElement? {

            val referencesFromProviders = ReferenceProvidersRegistry.getReferencesFromProviders(element)

            if(referencesFromProviders.isNotEmpty()) {

                val resolve = referencesFromProviders[0].resolve()
                if(resolve != null) {
                    return resolve as MSAPortElement
                } else {
                    return null
                }
            }
            return null
        }

        @JvmStatic fun getComponentsPath(element: MSAPortInstanceName): String? {

            val children = element.parent.children
            val dropLast = children.dropLast(1)
            if(dropLast.isEmpty()) {
                return null
            } else {
                return dropLast.joinToString { element -> element.text }
            }
        }

        @JvmStatic fun getDirection(element: MSAPortElement): String {

            val findChildByType = element.node.findChildByType(MSATokenElementTypes.IN)
            if(findChildByType != null) {

                return "IN"
            }

            return "OUT"
        }

        @JvmStatic fun isCritical(element: MSAPortElement): Boolean {

            val findChildByType = element.node.findChildByType(MSATokenElementTypes.CRITICAL)
            if(findChildByType != null) {

                return true
            }
            return false
        }

        @JvmStatic fun getQualifiedName(element: MSAComponentDeclaration): String {

            val name = getComponentName(element)

            val wrappingComponent = PsiTreeUtil.getParentOfType(element, MSAComponentDeclaration::class.java)

            if(wrappingComponent == null) {

                val msaFile = PsiTreeUtil.getParentOfType(element, MSAFile::class.java) as MSAFile

                val packageIdentifier = msaFile.getPackage()?.text.orEmpty().replace("package ", "").replace(";", "")
                if(!packageIdentifier.isNullOrEmpty()) {

                    return arrayOf(packageIdentifier, name).joinToString(".")
                } else {

                    return name
                }
            } else {

                return arrayOf(wrappingComponent.qualifiedName, name).joinToString(".")
            }
        }

        @JvmStatic fun getQualifiedName(element: MSAComponentInstanceDeclaration): String {

            val name = element.componentNameWithTypeList.map { it.componentName.name }.joinToString()

            val wrappingComponent = PsiTreeUtil.getParentOfType(element, MSAComponentDeclaration::class.java)

            if(wrappingComponent == null) {

                val msaFile = PsiTreeUtil.getParentOfType(element, MSAFile::class.java) as MSAFile

                val packageIdentifier = msaFile.getPackage()?.text.orEmpty().replace("package ", "").replace(";", "")
                if(!packageIdentifier.isNullOrEmpty()) {

                    return arrayOf(packageIdentifier, name).joinToString(".")
                } else {

                    return name
                }
            } else {

                return arrayOf(wrappingComponent.qualifiedName, name).joinToString(".")
            }
        }

        @JvmStatic fun getComponentName(element: MSAComponentDeclaration): String {

            return element.componentSignature?.componentNameWithTypeList?.map { it.componentName.name }.orEmpty().joinToString()
        }

        @JvmStatic fun getInstanceName(element: MSAComponentDeclaration): String {

            val instanceName = element.componentSignature?.componentInstanceName?.text
            return instanceName.orEmpty()
        }

        @JvmStatic fun getTrustLevel(element: MSAComponentDeclaration): Int {

            val trustLevelStatementList = element.componentBody?.trustLevelStatementList
            if(trustLevelStatementList.isNullOrEmpty()) {

                if(element.parent.elementType == MSAFileElementType) {

                    return -1
                } else {

                    return 0
                }
            } else {

                val level = trustLevelStatementList!!.first()!!.level

                if(level != null) {

                    var lvl = 0
                    try {
                        lvl = level.number.text.toInt()
                    } catch (ex: NumberFormatException) {

                    }

                    if(level.node.findChildByType(MSATokenElementTypes.MINUS) != null) {
                        lvl *= (-1)
                    }

                    return lvl
                }

                return 0
            }
        }

        @JvmStatic fun getAbsoluteTrustLevel(element: MSAComponentDeclaration): Int {

            var trustlevel = element.trustLevel

            var parent = element.parent
            while(parent.elementType != MSAFileElementType) {

                if(parent.elementType == MSACompositeElementTypes.COMPONENT_DECLARATION) {

                    trustlevel += (parent as MSAComponentDeclaration).trustLevel
                }

                parent = parent.parent
            }
            return trustlevel
        }

        @JvmStatic fun getTrustLevel(element: MSAComponentInstanceDeclaration): Int {

            if(element.componentNameWithTypeList.last().references.isEmpty()) {
                return 0
            }

            val psiReference = element.componentNameWithTypeList.last().references[0]
            if(psiReference != null) {

                val component = psiReference.resolve()
                if(component != null && component is MSAComponentDeclaration) {

                    return component.trustLevel
                }
            }
            return 0
        }

        @JvmStatic fun getAbsoluteTrustLevel(element: MSAComponentInstanceDeclaration): Int {

            var trustlevel = element.trustLevel

            var parent = element.parent
            while(parent.elementType != MSAFileElementType) {

                if(parent.elementType == MSACompositeElementTypes.COMPONENT_DECLARATION) {

                    trustlevel += (parent as MSAComponentDeclaration).trustLevel
                }

                parent = parent.parent
            }
            return trustlevel
        }
    }
}