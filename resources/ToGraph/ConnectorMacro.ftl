<#-- @ftlvariable name="start_port" type="java.lang.String" -->
<#-- @ftlvariable name="target_port" type="java.lang.String" -->
<#-- @ftlvariable name="relationship_type" type="java.lang.String" -->
<#macro connector p1 p2 relationship_type>(${p1})-[${relationship_type}]->(${p2})</#macro>
<@connector p1="${start_port}" p2="${target_port}" relationship_type="${relationship_type}" />