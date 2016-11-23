<#macro node>
version: '2'
services
    <#nested>
</#macro>
<@node>
    <#list extra_arguments!{} as attrName, attrVal>, ${attrName}:"${attrVal}"</#list>
</@node>