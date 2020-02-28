package com.testcases;

import static org.hamcrest.Matchers.*;

import org.apache.log4j.Logger;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.utilities.BaseClass;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.math.MathContext;
import java.util.HashMap;

public class AuthTest extends BaseClass {
	String token = "";
	String userId = "";
	String todoId = "";
	@BeforeClass
	void set() {
//		LOGGER = Logger.getLogger(AuthTest.class);
		RestAssured.baseURI = "https://fast-falls-92986.herokuapp.com/";
	}
	
//	@Test(priority=1)
	void createUserAndGetAuthToken() {
		
		HashMap user = new HashMap();
		user.put("email", "myemail1@gmail.com");
		user.put("password", "12345678");
		
		Response response = 
		given()
			.contentType(ContentType.JSON)
			.body(user)
		.when()
			.post("/users")
		.then()
			.assertThat()
			.statusCode(200)
			.log().all()
			.extract()
			.response();
		
		token = response.getHeader("x-auth");
		
		userId = response.jsonPath().get("_id");
		System.out.println(userId);
		
	}
	
//	@Test(priority=2)
	void loginUser() {
		
		HashMap user = new HashMap();
		user.put("email", "myemail1@gmail.com");
		user.put("password", "12345678");
		
		Response response = 
		given()
			.contentType(ContentType.JSON)
			.body(user)
		.when()
			.post("/users/login")
		.then()
			.assertThat()
			.statusCode(200)
			.body("email", equalTo(user.get("email")))
			.log().all()
			.extract()
			.response();
		
		token = response.getHeader("x-auth");
		
	}
	
//	@Test(priority=3)
	void createTodo() {
		
		HashMap todo = new HashMap();
		todo.put("text", "My Title");
		todo.put("_creator", userId);
		
		Response response = 
		given()
			.contentType(ContentType.JSON)
			.header("x-auth", token)
			.body(todo)
		.when()
			.post("/todos")
		.then()
			.statusCode(200)
			.log().all()
			.extract()
			.response();
		
		todoId = response.jsonPath().get("_id");
	}
	
//	@Test(priority=3)
	void getSingleTodo() {
		
		given()
			.pathParam("id", todoId)
			.header("x-auth",token)
		.when()
			.get("/todos/{id}")
		.then()
			.assertThat()
			.statusCode(200)
			.log().all();
	
	}
	
//	@Test(priority=5)
	void deleteTodo() {
		token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZTU3N2ExMTcwMjZhOTAwMTVlMmQ2NDEiLCJhY2Nlc3MiOiJhdXRoIiwiaWF0IjoxNTgyNzkxMTg1fQ.xAUL5De2dbxTECeieFCbfb7wQ5lO4RM_vVXDS6AnCZ4";
		
		given()
			.pathParam("id", todoId)
		.when()
			.delete("/todos/{id}")
		.then()
			.assertThat()
			.statusCode(200)
			.log().all();
	}
	
//	@Test
	void deleteUser() {
		
		
		
	}
	
}
