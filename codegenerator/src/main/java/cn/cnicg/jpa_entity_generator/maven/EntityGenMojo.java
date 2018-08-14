package cn.cnicg.jpa_entity_generator.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import cn.cnicg.jpa_entity_generator.CodeGenerator;
import cn.cnicg.jpa_entity_generator.config.CodeGeneratorConfig;
import cn.cnicg.jpa_entity_generator.config.JDBCSettings;
import cn.cnicg.sdgenerator.plugin.CommonsMojo;

/**
 * @goal generateAll
 * @phase process-sources
 */
@Mojo(name = "entity")
@Execute(phase = LifecyclePhase.COMPILE)
@SuppressWarnings("unused")
public class EntityGenMojo extends CommonsMojo{

    /**
     * @parameter configPath
     */

    @Override
    public void execute() throws MojoExecutionException {
        try {
            CodeGeneratorConfig config = new CodeGeneratorConfig();
            JDBCSettings setting = new JDBCSettings();
            setting.setDriverClassName(this.jdbcDriver);
            setting.setPassword(this.jdbcPasswd);
            setting.setUrl(this.jdbcUrl);
            setting.setUsername(this.jdbcUser);
            config.setJdbcSettings(setting);
            
            config.setUpPresetRules();
            
            config.setPackageName(this.entityPackage[0]);
            config.setDtoPackageName(this.dtoPackage);
            config.setServiceImplPackageName(this.serviceImplPackage);
            config.setServiceIntfPackageName(this.serviceIntfPackage);
            config.setRepositoryPackageName(this.repositoryPackage);
            
            CodeGenerator.generateAll(config);
        } catch (Exception e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }
}
