<#-- @ftlvariable name="id" type="java.lang.String" -->
<#-- @ftlvariable name="access_roles" type="java.lang.String" -->
<#-- @ftlvariable name="extra_arguments" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="type_name" type="java.lang.String" -->
<#-- @ftlvariable name="instance_name" type="java.lang.String" -->
<#macro node id instance_name type_name access_roles extra...>(${id}:Component {name:"${instance_name}", type:"${type_name}", access:[${access_roles}] <#nested>})</#macro>
<@node id="${id}" instance_name="${instance_name}" type_name="${type_name}" access_roles="${access_roles}"><#list extra_arguments!{} as attrName, attrVal>, ${attrName}:"${attrVal}"</#list></@node>