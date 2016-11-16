<#-- @ftlvariable name="start_port" type="java.lang.String" -->
<#-- @ftlvariable name="target_port" type="java.lang.String" -->
<#-- @ftlvariable name="relationship_type" type="java.lang.String" -->
<#-- @ftlvariable name="extra_arguments" type="java.util.Map<String, String>" -->
<#macro connector p1 p2 relationship_type>(${p1})-[${relationship_type} { dummy: "" <#nested>}]->(${p2})</#macro>
<@connector p1="${start_port}" p2="${target_port}" relationship_type="${relationship_type}"><#list extra_arguments!{} as attrName, attrVal>, ${attrName}:"${attrVal}"</#list></@connector>