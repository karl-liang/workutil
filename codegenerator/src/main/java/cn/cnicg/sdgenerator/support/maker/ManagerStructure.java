package cn.cnicg.sdgenerator.support.maker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.cnicg.sdgenerator.support.maker.builder.ObjectBuilder;
import cn.cnicg.sdgenerator.support.maker.builder.ObjectStructure;
import cn.cnicg.sdgenerator.support.maker.values.ExpressionValues;
import cn.cnicg.sdgenerator.support.maker.values.ObjectTypeValues;
import cn.cnicg.sdgenerator.support.maker.values.ObjectValues;
import cn.cnicg.sdgenerator.support.maker.values.ScopeValues;
import cn.cnicg.sdgenerator.util.GeneratorUtils;
import cn.cnicg.sdgenerator.util.Tuple;

/**
 * Created by carlos on 08/04/17.
 */
public class ManagerStructure {

    private ObjectBuilder objectBuilder;

    public ManagerStructure(String managerPackage, String entityName, String entityClass, String postfix, String repositoryPackage, String repositoryPostfix, String additionalPackage) {

        String managerName = entityName + postfix;
        String repositoryName = entityName + repositoryPostfix;
        String repositoryNameAttribute = GeneratorUtils.decapitalize(repositoryName);

        this.objectBuilder = new ObjectBuilder(new ObjectStructure(managerPackage, ScopeValues.PUBLIC, ObjectTypeValues.CLASS, managerName)
                .addImport(repositoryPackage + "." + (additionalPackage.isEmpty() ? "" : (additionalPackage + ".")) + repositoryName)
                .addImport(entityClass)
                .addImport(Autowired.class)
                .addImport(Component.class)
                .addAnnotation(Component.class)
                .addAttribute(repositoryName, repositoryNameAttribute)
                .addConstructor(new ObjectStructure.ObjectConstructor(ScopeValues.PUBLIC, managerName)
                        .addAnnotation(Autowired.class)
                        .addArgument(repositoryName, repositoryNameAttribute)
                        .addBodyLine(ObjectValues.THIS.getValue() + repositoryNameAttribute + ExpressionValues.EQUAL.getValue() + repositoryNameAttribute)
                )
        ).setAttributeBottom(false);

    }

    public Tuple<String, Integer> build(){
        return new Tuple<>(objectBuilder == null ? null : objectBuilder.build(), 0);
    }
}
