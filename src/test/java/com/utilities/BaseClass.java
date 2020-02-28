package com.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.testcases.PostsTest;

import io.restassured.RestAssured;

public class BaseClass {

	String env = "DEV";
	public String url;
	String info;
	public Logger LOGGER;
	public Properties prop = new Properties();
	
	public BaseClass(){
		
		try {
			
		
		env = System.getProperty("env");
//		System.out.println("env --- "+env);
//		env = "INT";
		String rootPath = System.getProperty("user.dir");
//		System.out.println("Root Path : "+rootPath);
		PropertyConfigurator.configure(rootPath + "/log4j.properties");
		LOGGER = Logger.getLogger(BaseClass.class);	
		
		if(env.equals("DEV")) {
			prop.load(new FileInputStream(new File(rootPath + "/DEVENV.properties")));
		}else if(env.equals("INT")) {
			prop.load(new FileInputStream(new File(rootPath + "/INTENV.properties")));
		}else if(env.equals("STG")) {
			prop.load(new FileInputStream(new File(rootPath + "/STGENV.properties")));
		}
//		System.out.println("url --- "+prop.getProperty("url"));
		url = prop.getProperty("url");
		info = prop.getProperty("info");
		
		System.out.println("===================================");
		System.out.println("Info --- "+info);
//		LOGGER.info("BaseClass info : "+info);
		System.out.println("===================================");
		
		RestAssured.baseURI = url;
//		System.out.println("BaseClass setup() : "+RestAssured.baseURI);
		
		}catch(FileNotFoundException f) {
			
		}catch(IOException io) {
			
		}
	}
	
}
