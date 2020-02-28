package com.testcases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.models.User;
import com.utilities.BaseClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

public class UsersTest extends BaseClass {

	@BeforeClass
	void test() {
//		LOGGER = Logger.getLogger(UsersTest.class);
		//RestAssured.baseURI = url;
	}
	
	@Test
	void getSingleUser() {
		
		Response response = 
		given()
		.when()
			.get("/users/5")
		.then()
			.assertThat()
			.statusCode(200)
			.log().all()
			.extract()
			.response();
		
		User user = response.as(User.class);
		
		System.out.println(user.getAddress().getGeo().getLat());
		System.out.println(user.getAddress().getGeo().getLng());
//		System.out.println(user.address.geo);
//		System.out.println(user.company);
		
		
		
//		for(Map.Entry single : Arrays.hMap ) {
//			
//		}
		
		
		
	}
	
}
