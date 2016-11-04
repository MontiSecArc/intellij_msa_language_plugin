<#-- @ftlvariable name="id" type="java.lang.String" -->
<#-- @ftlvariable name="access_roles" type="java.lang.String" -->
<#-- @ftlvariable name="is_critical" type="java.lang.Boolean" -->
<#-- @ftlvariable name="extra_arguments" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="type_name" type="java.lang.String" -->
<#-- @ftlvariable name="instance_name" type="java.lang.String" -->
<#macro node id instance_name type_name is_critical access_roles="" extra...>(${id}:Port {name:"${instance_name}", type:"${type_name}", is_critical:${is_critical}, access:[${access_roles}] <#list extra!{} as attrName, attrVal>, ${attrName}:"${attrVal}"</#list>})</#macro>
<@node id="${id}" instance_name="${instance_name}" type_name="${type_name}" is_critical="${is_critical?string('true', 'false')}" access_roles="${access_roles}" />