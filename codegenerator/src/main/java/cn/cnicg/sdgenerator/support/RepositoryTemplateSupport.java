package cn.cnicg.sdgenerator.support;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;

import cn.cnicg.sdgenerator.provider.AbstractTemplateProvider;
import cn.cnicg.sdgenerator.support.maker.RepositoryStructure;
import cn.cnicg.sdgenerator.util.CustomResourceLoader;
import cn.cnicg.sdgenerator.util.Tuple;

import java.util.Set;

/**
 * Created by carlos on 08/04/17.
 */
public class RepositoryTemplateSupport extends AbstractTemplateProvider {

    private CustomResourceLoader loader;
    private Set<String> additionalExtends;

    public RepositoryTemplateSupport(AnnotationAttributes attributes, Set<String> additionalExtends) {
        super(attributes);
        this.additionalExtends = additionalExtends;
    }

    public RepositoryTemplateSupport(CustomResourceLoader loader, Set<String> additionalExtends) {
        super(loader);
        this.loader = loader;
        this.additionalExtends = additionalExtends;
    }

    @Override
    protected Tuple<String, Integer> getContentFromTemplate(String repositoryPackage, String simpleClassName, String postfix, BeanDefinition beanDefinition, String additionalPackage) {
        return new RepositoryStructure(repositoryPackage, simpleClassName, beanDefinition.getBeanClassName(), postfix, loader, additionalExtends).build();
    }

    @Override
    protected String getExcludeClasses() {
        return "excludeRepositoriesClasses";
    }

    @Override
    protected String getPostfix() {
        return "repositoryPostfix";
    }

}
