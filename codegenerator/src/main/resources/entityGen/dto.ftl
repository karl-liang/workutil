package ${dtoPackageName};
<#list importRules as rule>
import ${rule.importValue};
</#list>
import io.swagger.annotations.ApiModelProperty;

<#if classComment?has_content>
${classComment}
</#if>

public class ${className}DTO<#if interfaceNames.size() \gt 0> implements ${interfaceNames?join(", ")}</#if> {
<#if primaryKeyFields.size() \gt 1>
  @Data
  public static class PrimaryKeys implements Serializable {
  <#list primaryKeyFields as field>
    private ${field.type} ${field.name}<#if field.defaultValue??> = ${field.defaultValue}</#if>;
  </#list>
  }
</#if>

<#list topAdditionalCodeList as code>
${code}

</#list>
<#list fields as field>
<#if field.comment?has_content>
${field.comment}
</#if>
<#list field.annotations as annotation>
  ${annotation.toString()}
</#list>
  @ApiModelProperty("${field.dbComment}")
  private ${field.type} ${field.name}<#if field.defaultValue??> = ${field.defaultValue}</#if>;
  
</#list>
<#list bottomAdditionalCodeList as code>

${code}
</#list>
}
