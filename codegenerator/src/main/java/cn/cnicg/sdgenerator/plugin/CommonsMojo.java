package cn.cnicg.sdgenerator.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import cn.cnicg.sdgenerator.util.*;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by carlos on 01/05/17.
 */
public abstract class CommonsMojo extends AbstractMojo {

    @Parameter(name = Constants.ENTITY_PACKAGE)
    protected String[] entityPackage;

    @Parameter(name = Constants.REPOSITORY_PACKAGE)
    protected String repositoryPackage;
    
    @Parameter(name = Constants.DTO_PACKAGE, defaultValue = "cn.cnicg.dto")
    protected String dtoPackage;
    
    @Parameter(name = Constants.SERVICEINTF_PACKAGE, defaultValue = "cn.cnicg.service")
    protected String serviceIntfPackage;
    
    @Parameter(name = Constants.SERVICEIMPL_PACKAGE, defaultValue = "cn.cnicg.service.impl")
    protected String serviceImplPackage;
    
    @Parameter(name = Constants.REPOSITORY_POSTFIX, defaultValue = "Repository")
    protected String repositoryPostfix;

    @Parameter(name = Constants.MAGANER_PACKAGE)
    protected String managerPackage;

    @Parameter(name = Constants.MANAGER_POSTFIX, defaultValue = "Manager")
    protected String managerPostfix;

    @Parameter(name = Constants.ONLY_ANNOTATIONS, defaultValue = "false")
    protected Boolean onlyAnnotations;

    @Parameter(name = Constants.OVERWRITE, defaultValue = "false")
    protected Boolean overwrite;

    @Parameter(name = Constants.EXTENDS)
    protected String[] additionalExtends;

    @Parameter(name = Constants.JDBC_URL)
    protected String jdbcUrl;
    
    @Parameter(name = Constants.JDBC_USER)
    protected String jdbcUser;
    
    @Parameter(name = Constants.JDBC_PASSWD)
    protected String jdbcPasswd;
   
    @Parameter(name = Constants.JDBC_DRIVER, defaultValue = "com.mysql.jdbc.Driver" )
    protected String jdbcDriver;
    
    @Component
    protected MavenProject project;

    protected Set<String> additionalExtendsList = new LinkedHashSet<>();

    public void validateField(String parameter) throws SDMojoException {

        boolean errorFound = Boolean.FALSE;

        switch (parameter) {
            case Constants.ENTITY_PACKAGE:
                if (entityPackage == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            case Constants.REPOSITORY_PACKAGE:
                if (repositoryPackage == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            case Constants.REPOSITORY_POSTFIX:
                if (repositoryPostfix == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            case Constants.MAGANER_PACKAGE:
                if (managerPackage == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            case Constants.MANAGER_POSTFIX:
                if (managerPostfix == null) {
                    errorFound = Boolean.TRUE;
                }
                break;
            case Constants.EXTENDS:
                if (additionalExtends != null) {
                    this.validateExtends();
                }
                break;
            default:
                SDLogger.addError( String.format("%s configuration parameter not found!", parameter));
                throw new SDMojoException();
        }

        if (errorFound) {
            SDLogger.addError( String.format("%s configuration not found!", parameter));
            throw new SDMojoException();
        }
    }

    private void validateExtends() throws SDMojoException {
        String extendTemporal;
        boolean errorValidate = Boolean.FALSE;
        for (int i = 0; i < additionalExtends.length; i++) {
            extendTemporal = additionalExtends[i];
            SDLogger.addAdditionalExtend(extendTemporal);
            if (extendTemporal.contains(".")){
                additionalExtendsList.add(extendTemporal);
            } else {
                errorValidate = Boolean.TRUE;
                SDLogger.addError( String.format("'%s' is not a valid object!", extendTemporal));
            }
        }

        if (errorValidate) {
            throw new SDMojoException();
        }
    }

}
