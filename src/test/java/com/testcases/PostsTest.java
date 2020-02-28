package com.testcases;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static org.hamcrest.Matchers.*;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.models.Post;
import com.utilities.BaseClass;

import static io.restassured.RestAssured.*;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;



public class PostsTest extends BaseClass {
	int id;
	int userId;
	
	@BeforeClass
	void set() throws FileNotFoundException, IOException {
		
//		LOGGER = Logger.getLogger(PostsTest.class);	
		id = 1;
		userId = 1;
		System.out.println("PostsTest set() : "+RestAssured.baseURI);
		//RestAssured.baseURI = url;
//		RestAssured.useRelaxedHTTPSValidation();
		LOGGER.info("New message logger");
	}
	
	@Test
	void getAllPosts() {
		
		
		
		given()
		.when()
			.get("/posts")
		.then()
			.assertThat()
			.statusCode(200)
			.log().all(true);
	}
	
	@Test
	void getSinglePost() {
		
		given()
		.when()
			.get("/posts/"+id)
		.then()
			.assertThat()
			.body("id",  equalTo(id))
			.log().all(true);
		
	}
	
	@Test
	void getPostsThroughUserId() {
		//using query param ?userId=1
		
		Response response = 
		given()
			.param("userId", 1)
		.when()
			.get("/posts")
		.then()
			.assertThat()
			.statusCode(200)
			.log().all(true)
			.extract()
			.response();
		
		Post[] posts = response.as(Post[].class);
		
		Assert.assertEquals(posts.length, 10);
		
	}
	
	@Test
	void createNewPost() {
		
		System.out.println("Inside create post"+ RestAssured.baseURI);
		
		
		Post newPost = new Post();
		newPost.setId(500);
		newPost.setUserId(1);
		newPost.setTitle("new title");
		newPost.setBody("new body");
		
		given()
			.contentType(ContentType.JSON)
			.body(newPost)
		.when()
			.post("/posts")
		.then()
			.assertThat()
			.statusCode(201)
			.log().all();
	}
	
	
}
