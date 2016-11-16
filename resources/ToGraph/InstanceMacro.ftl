<#-- @ftlvariable name="id" type="java.lang.String" -->
<#-- @ftlvariable name="access_roles" type="java.lang.String" -->
<#-- @ftlvariable name="extra_arguments" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="type_name" type="java.lang.String" -->
<#-- @ftlvariable name="instance_name" type="java.lang.String" -->
<#macro node id instance_name>(${id}:Instance {name: "${instance_name}" <#nested>})</#macro>
<@node id="${id}" instance_name="${instance_name}"><#list extra_arguments!{} as attrName, attrVal>, ${attrName}:"${attrVal}"</#list></@node>