<#-- @ftlvariable name="id" type="java.lang.String" -->
<#-- @ftlvariable name="level" type="java.lang.Number" -->
<#-- @ftlvariable name="extra_arguments" type="java.util.Map<String, String>" -->
<#-- @ftlvariable name="instance_name" type="java.lang.String" -->
<#macro node id instance_name level extra...>(${id}:Trustlevel {name:"${instance_name}", level:${level} })</#macro>
<@node id="${id}" instance_name="${instance_name}" level="${level}" />