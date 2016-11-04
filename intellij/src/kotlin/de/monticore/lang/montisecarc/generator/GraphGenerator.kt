package de.monticore.lang.montisecarc.generator

import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.PsiRecursiveElementWalkingVisitor
import com.intellij.util.containers.isNullOrEmpty
import de.monticore.lang.montisecarc.psi.*
import de.monticore.lang.montisecarc.psi.util.elementType
import java.net.URL

/**
 * Created by thomasbuning on 30.10.16.
 */
class GraphGenerator {

    private fun getUrl(path: String): URL? {

        return this.javaClass.classLoader.getResource(path)
    }

    fun createGraph(psiFile: PsiFile): String {

        return generate(psiFile)
    }

    private fun generatePortElement(portElement: MSAPortElement): Pair<String, String> {
        val model = mutableMapOf<String, Any>()

        //<@node instance_name="${instance_name}" type_name="${type_name}" is_critical="${is_critical}" access_roles="${access_roles}" extra="${extra_arguments}" />

        //${instance_name}_${type_name}
        val (referenceType, portInstanceName) = getPortIdentifiers(portElement)
        val componentIdentifier = createComponentIdentifier(portElement.enclosingComponent)
        val identifier = createPortIdentifier(portElement)
        model.put("id", identifier)
        model.put("instance_name", portInstanceName.orEmpty())
        model.put("type_name", referenceType.orEmpty())
        model.put("is_critical", portElement.isCritical)

        val portAccessRoles = portElement.enclosingComponent?.componentBody?.accessStatementList?.map { it.portAccessRoleList.filter { it.portInstanceName.text == portInstanceName }.map { "'${it.portInstanceName.text}'" }.joinToString() }.orEmpty().joinToString()

        model.put("access_roles", portAccessRoles)

        val port = FreeMarker.instance.generateModelOutput(getUrl("ToGraph/PortMacro.ftl")?.path.orEmpty(), model)

        //${instance_name}_${component_name}_${type_name}
        var connector = ""
        if (!identifier.isNullOrBlank() && !componentIdentifier.isNullOrBlank()) {
            //<@connector p1="${start_port}" p2="${target_port}" relationship_type="${relationship_type}" />
            val connector_model = mutableMapOf<String, Any>()
            connector_model.put("relationship_type", ":CONNECT")

            if (portElement.direction == "IN") {
                connector_model.put("start_port", identifier)
                connector_model.put("target_port", componentIdentifier)
            } else {
                connector_model.put("start_port", componentIdentifier)
                connector_model.put("target_port", identifier)
            }
            connector = FreeMarker.instance.generateModelOutput(getUrl("ToGraph/ConnectorMacro.ftl")?.path.orEmpty(), connector_model)
        }

        return Pair(port, connector)
    }

    private fun createPortIdentifier(portElement: MSAPortElement): String {
        val (referenceType, portInstanceName) = getPortIdentifiers(portElement)
        val enclosingComponent = portElement.enclosingComponent
        val componentIdentifier = createComponentIdentifier(enclosingComponent)
        return arrayOf(componentIdentifier, referenceType, portInstanceName).joinToString("_")
    }

    private fun createComponentIdentifier(enclosingComponent: MSAComponentDeclaration?): String {
        var componentIdentifier = ""
        if (enclosingComponent != null) {

            componentIdentifier += enclosingComponent.qualifiedName.replace(".", "_") + "_" + enclosingComponent.instanceName
        }
        return componentIdentifier
    }

    private fun getPortIdentifiers(portElement: MSAPortElement): Pair<String?, String?> {
        val referenceType = portElement.referenceType?.text
        val portInstanceName = portElement.portInstanceName?.text ?: referenceType?.decapitalize()
        return Pair(referenceType, portInstanceName)
    }


    private fun generateComponentElement(componentDeclaration: MSAComponentDeclaration): Triple<String, String, Int> {
        val model = mutableMapOf<String, Any>()

        //<@node instance_name="${instance_name}" type_name="${type_name}" access_roles="${access_roles}" extra="${extra_arguments}" />
        val instanceName = componentDeclaration.instanceName
        val componentIdentifier = createComponentIdentifier(componentDeclaration)
        model.put("id", componentIdentifier)
        model.put("instance_name", instanceName.orEmpty())
        model.put("type_name", componentDeclaration.qualifiedName)
        val accessRoles = componentDeclaration.componentBody?.accessStatementList?.map { it.roleNameList.map { "'${it.text}'" }.joinToString() }?.joinToString()
        model.put("access_roles", accessRoles.orEmpty())

        val extras = mutableMapOf<String, String>()
        val configurationStatementList = componentDeclaration.componentBody?.configurationStatementList
        if(!configurationStatementList.isNullOrEmpty()) {

            val config = configurationStatementList!!.first().id?.text
            if(!config.isNullOrEmpty()) {
                extras.put("configuration", config!!)
            }
        }
        val cpeStatementList = componentDeclaration.componentBody?.cpeStatementList
        if(!cpeStatementList.isNullOrEmpty()) {

            val cpe = cpeStatementList!!.first().string?.text
            if(!cpe.isNullOrEmpty()) {
                extras.put("cpe", cpe!!.replace("\"", ""))
            }
        }

        val accessControlStatementList = componentDeclaration.componentBody?.accessControlStatementList
        if(!accessControlStatementList.isNullOrEmpty()) {

            val findChildByType = accessControlStatementList!!.first().node.findChildByType(MSATokenElementTypes.ON)
            if(findChildByType != null) {

                extras.put("access_control", "on")
            } else {

                extras.put("access_control", "off")
            }
        }

        model.put("extra_arguments", extras)

        val component = FreeMarker.instance.generateModelOutput(getUrl("ToGraph/ComponentMacro.ftl")?.path.orEmpty(), model)

        return Triple(component, componentIdentifier, componentDeclaration.absoluteTrustLevel)
    }

    private fun createConnector(start: String, target: String): String {

        val connector_model = mutableMapOf<String, Any>()
        connector_model.put("relationship_type", ":TRUST")
        connector_model.put("start_port", start)
        connector_model.put("target_port", target)
        return FreeMarker.instance.generateModelOutput(getUrl("ToGraph/ConnectorMacro.ftl")?.path.orEmpty(), connector_model)
    }

    private fun generate(parseFile: PsiFile): String {
        val components = mutableListOf<String>()
        val ports = mutableListOf<String>()
        val trustlevelNodes = mutableListOf<String>()
        val trustlevels = mutableMapOf<Int, List<String>>()
        val connectors = mutableListOf<String>()

        parseFile.accept(object : PsiRecursiveElementWalkingVisitor() {

            override fun visitElement(element: PsiElement?) {
                super.visitElement(element)

                if (element == null) {

                    return
                }

                when (element.elementType) {

                    MSACompositeElementTypes.PORT_ELEMENT -> {
                        val (port, connector) = generatePortElement(element as MSAPortElement)
                        ports.add(port)
                        connectors.add(connector)

                    }
                    MSACompositeElementTypes.COMPONENT_DECLARATION -> {
                        val (componentElement, componentIdentifier, trustlevel) = generateComponentElement(element as MSAComponentDeclaration)
                        components.add(componentElement)

                        val componentIdentifierList = trustlevels[trustlevel].orEmpty()
                        val newComponentIdentifierList = listOf(componentIdentifier) + componentIdentifierList
                        trustlevels.put(trustlevel, newComponentIdentifierList)
                    }
                    MSACompositeElementTypes.CONNECTOR -> {
                        val connector = generateConnectorElement(element as MSAConnector)
                        connectors.addAll(connector)
                    }
                }
            }
        })


        for ((trustLevel, componentIdentifiers) in trustlevels) {

            var trustLevelId:String
            var trustLevelString:String
            if(trustLevel >= 0) {

                trustLevelId = "tP$trustLevel"
                trustLevelString = "+$trustLevel"
            } else {

                trustLevelId = "tM$trustLevel"
                trustLevelString = "-$trustLevel"
            }
            val model = mutableMapOf<String, Any>()
            model.put("id", trustLevelId)
            model.put("instance_name", trustLevelString)
            model.put("level", trustLevel)
            val trustLevelNode = FreeMarker.instance.generateModelOutput(getUrl("ToGraph/TrustlevelMacro.ftl")?.path.orEmpty(), model)
            trustlevelNodes.add(trustLevelNode)

            for (componentIdentifier in componentIdentifiers) {

                connectors.add(createConnector(componentIdentifier, trustLevelId))
                connectors.add(createConnector(trustLevelId, componentIdentifier))
            }
        }

        val model = mutableMapOf<String, String>()

        //CREATE ${components}, ${ports}, ${trustlevel} ${connectors};
        model.put("components", components.joinToString())
        model.put("ports", ports.joinToString())
        model.put("trustlevel", trustlevelNodes.joinToString())
        model.put("connectors", connectors.filter { !it.isNullOrEmpty() }.joinToString())
        val graph = FreeMarker.instance.generateModelOutput(getUrl("ToGraph/Create.ftl")?.path.orEmpty(), model)
        return graph
    }

    private fun generateConnectorElement(msaConnector: MSAConnector): List<String> {

        var encrypted = ":UNENCRYPTED"
        if (msaConnector.node.findChildByType(MSATokenElementTypes.ENCRYPTED) != null) {

            encrypted = ":ENCRYPTED"
        }

        val connectors = mutableListOf<String>()

        val sourcePort = msaConnector.connectSource.portInstanceName.referencedPortElement ?: return emptyList()

        for (msaConnectTarget in msaConnector.connectTargetList) {

            val targetPort = msaConnectTarget.portInstanceName.referencedPortElement ?: continue

            val sourcePortIdentifier = createPortIdentifier(sourcePort)
            val targetPortIdentifier = createPortIdentifier(targetPort)
            //<@connector p1="${start_port}" p2="${target_port}" relationship_type="${relationship_type}" />

            val connector_model = mutableMapOf<String, Any>()
            connector_model.put("relationship_type", encrypted)

            if(sourcePort.direction == targetPort.direction) {
                continue
            }

            if (targetPort.direction == "IN") {
                connector_model.put("start_port", sourcePortIdentifier)
                connector_model.put("target_port", targetPortIdentifier)
            } else {
                connector_model.put("start_port", targetPortIdentifier)
                connector_model.put("target_port", sourcePortIdentifier)
            }
            val connector = FreeMarker.instance.generateModelOutput(getUrl("ToGraph/ConnectorMacro.ftl")?.path.orEmpty(), connector_model)
            connectors.add(connector)
        }

        return connectors
    }
}