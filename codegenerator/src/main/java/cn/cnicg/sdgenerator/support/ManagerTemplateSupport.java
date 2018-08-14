package cn.cnicg.sdgenerator.support;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.core.annotation.AnnotationAttributes;

import cn.cnicg.sdgenerator.provider.AbstractTemplateProvider;
import cn.cnicg.sdgenerator.support.maker.ManagerStructure;
import cn.cnicg.sdgenerator.util.CustomResourceLoader;
import cn.cnicg.sdgenerator.util.GeneratorUtils;
import cn.cnicg.sdgenerator.util.Tuple;

import java.io.File;
import java.util.Arrays;

/**
 * Created by carlos on 08/04/17.
 */
public class ManagerTemplateSupport extends AbstractTemplateProvider {

    private String repositoryPackage;
    private String repositoryPostfix;

    public ManagerTemplateSupport(AnnotationAttributes attributes, String repositoryPackage, String repositoryPostfix) {
        super(attributes);
        this.repositoryPackage = repositoryPackage;
        this.repositoryPostfix = repositoryPostfix;
        this.findFilterRepositories();
    }

    public ManagerTemplateSupport(CustomResourceLoader customResourceLoader) {
        super(customResourceLoader);
        this.repositoryPackage = customResourceLoader.getRepositoryPackage();
        this.repositoryPostfix = customResourceLoader.getRepositoryPostfix();
        this.findFilterRepositories();
    }

    private void findFilterRepositories() {
        String repositoryPath = GeneratorUtils.getAbsolutePath() + repositoryPackage.replace(".", "/");
        File[] repositoryFiles = GeneratorUtils.getFileList(repositoryPath, repositoryPostfix);
        this.setIncludeFilter(Arrays.asList(repositoryFiles));
        this.setIncludeFilterPostfix(repositoryPostfix);
    }

    @Override
    protected Tuple<String, Integer> getContentFromTemplate(String mPackage, String simpleClassName, String postfix, BeanDefinition beanDefinition, String additionalPackage) {
        return new ManagerStructure(mPackage, simpleClassName, beanDefinition.getBeanClassName(), postfix, repositoryPackage, repositoryPostfix, additionalPackage).build();
    }

    @Override
    protected String getExcludeClasses() {
        return "excludeManagerClasses";
    }

    @Override
    protected String getPostfix() {
        return "managerPostfix";
    }

}