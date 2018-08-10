package com.lh.util.projectstart;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.lh.base.exception.TextHintException;
import com.lh.base.intf.ICmdRunable;

public class ProjectStart implements ICmdRunable {
	
	public static String CMD_PROJECTSTART = "CMD_PROJECTSTART";
	
	public static final int ARGS_INDEX_TARGET_DIR = 0;
	public static final int ARGS_INDEX_PROJECT_NAME = 1;
	public static final int ARGS_INDEX_PACKAGE_BASE = 2;
	public static final int ARGS_INDEX_GROUP_ID = 3;
//	public static final int ARGS_INDEX_PROJECT_NAME = 0;
//	public static final int ARGS_INDEX_PROJECT_NAME = 0;
//	public static final int ARGS_INDEX_PROJECT_NAME = 0;

	private static final String[] MODULE_LIST = new String[] {"grpc-protoc-interface","common","module","persist","service","web"}; 
	
	
	public String getCmdName() {
		return null;
	}

	public void run(String[] args) throws IOException {
		
		String targetDir = args[0];
		String projectName = args[1];
		String pacakageBase = args[2];
		
		String projectPath = targetDir+projectName;
		mkdirProjectDir(targetDir, projectName,pacakageBase);
		
		copyProjectPom(projectName,pacakageBase,projectPath);
		
		String commonPath = projectPath + "/" + projectName + "-" + MODULE_LIST[1];
		copyCommonPom(projectName,pacakageBase,commonPath);
		
		String grpcPath = projectPath + "/" + projectName + "-" + MODULE_LIST[0];
		copyGrpcPom(projectName,pacakageBase,grpcPath);
		
		String modulePath = projectPath + "/" + projectName + "-" + MODULE_LIST[2];
		copyModulePom(projectName,pacakageBase,modulePath);
		
		String persistPath = projectPath + "/" + projectName + "-" + MODULE_LIST[3];
		copyPersistPom(projectName,pacakageBase,persistPath);
		
		String servicePath = projectPath + "/" + projectName + "-" + MODULE_LIST[4];
		copyServicePom(projectName,pacakageBase,servicePath);
		
		String webPath = projectPath + "/" + projectName + "-" + MODULE_LIST[5];
		copyWebPom(projectName,pacakageBase,webPath);
		
	}
	
	private void copyWebPom(String projectName, String basePackage, String dirPath) throws IOException {
		copyPomImpl(projectName, basePackage, dirPath,"web_pom.xml");
	}
	
	private void copyServicePom(String projectName, String basePackage, String dirPath) throws IOException {
		copyPomImpl(projectName, basePackage, dirPath,"service_pom.xml");
	}
	
	private void copyPersistPom(String projectName, String basePackage, String dirPath) throws IOException {
		copyPomImpl(projectName, basePackage, dirPath,"persist_pom.xml");
	}
	private void copyModulePom(String projectName, String basePackage, String dirPath) throws IOException {
		copyPomImpl(projectName, basePackage, dirPath,"module_pom.xml");
	}
	private void copyGrpcPom(String projectName, String basePackage, String dirPath) throws IOException {
		copyPomImpl(projectName, basePackage, dirPath,"grpc_pom.xml");
	}
	private void copyProjectPom(String projectName, String basePackage, String projectDirPath) throws IOException {
		copyPomImpl(projectName, basePackage, projectDirPath,"project_pom.xml");
	}
	private void copyCommonPom(String projectName, String basePackage, String dirPath) throws IOException {
		copyPomImpl(projectName, basePackage, dirPath,"common_pom.xml");
	}
	
	private void copyPomImpl(String projectName, String basePackage, String targetDir, String sourcePom) throws IOException {
		File targetPomFile = new File(targetDir + "/pom.xml");
		if(targetPomFile.exists()) {
			targetPomFile.delete();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(targetPomFile));
		
		URL url1 = ProjectStart.class.getResource("/" + sourcePom);
		InputStream is = url1.openStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		String content = br.readLine();
		while(content != null) {
			content = content.replace("{basepackage}", basePackage);
			content = content.replace("{projectName}", projectName);
			bw.write(content+"\r\n");
			content = br.readLine();
		}
		
		br.close();
		bw.close();
	}

	


	
	private void mkdirProjectDir(String targetDir, String projectName, String pacakageBase) {
		if(!targetDir.endsWith("/")) {
			targetDir =  targetDir + "/";
		}
		String projectPath = targetDir+projectName;
		if(mkdir(projectPath)) {
			for(String tmp:MODULE_LIST) {
				 String path = projectPath + "/" + projectName + "-" +  tmp;
				 if(!mkdir(path)) {
					 throw new TextHintException(String.format("目录 %s 创建失败", path));
				 }
				 
				 if(!tmp.equals(MODULE_LIST[0])) {
					 String pacakagesBasePath = path + "/src/main/java/" + pacakageBase.replace(".", "/") + "/"+tmp;
					 if(!mkdir(pacakagesBasePath)) {
						 throw new TextHintException(String.format("目录 %s 创建失败", pacakagesBasePath));
					 } 
				 }
				 if(tmp.equals(MODULE_LIST[3])) {
					 String pacakagesBasePath = path + "/src/main/java/" + pacakageBase.replace(".", "/") + "/"+tmp + "/mapping";
					 if(!mkdir(pacakagesBasePath)) {
						 throw new TextHintException(String.format("目录 %s 创建失败", pacakagesBasePath));
					 } 
					 
					 pacakagesBasePath = path + "/src/main/java/" + pacakageBase.replace(".", "/") + "/"+tmp + "/mapper";
					 if(!mkdir(pacakagesBasePath)) {
						 throw new TextHintException(String.format("目录 %s 创建失败", pacakagesBasePath));
					 } 
				 }
				 
				 if(tmp.equals(MODULE_LIST[5])) {
					 String[] envs = new String[] {"dev","local","prd","test"};
					 for(String env:envs) {
						 String pacakagesBasePath = path + "/src/main/resources/conf/" + env;
						 if(!mkdir(pacakagesBasePath)) {
							 throw new TextHintException(String.format("目录 %s 创建失败", pacakagesBasePath));
						 } 
					 }
				 }
			}
		}
	}
	
	private boolean mkdir(String path) {
		File dir = new File(path);
		if(dir.exists() && dir.isDirectory()) {
			throw new TextHintException(String.format("指定目录 %s 已存在", dir));
		}
		return dir.mkdirs();
	}

	public static void main(String[] args) throws IOException {
		args = new String[] {"./", "cnicg-bpm","com.sciov.cnicg.bpm"};
		new ProjectStart().run(args);
	}
}
