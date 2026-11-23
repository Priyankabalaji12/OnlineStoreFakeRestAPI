package testcases;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Product_payload;
import pojo.User;
import routes.Product_Routes;

public class UserTests extends BaseClass
{
   //1)Fetch all users 
	
	//@Test
	public void testGetAllUsers()
	{
		given()
		 
		.when()
		   .get(Product_Routes.GET_ALL_USERS)
		   
		.then()
		   .statusCode(200)
		   .contentType(ContentType.JSON)
		   .body("size()",greaterThan(0))
		   .log().body();
		
	}
	
	//2)Getuser by id
	
	//@Test
	public void testGetSpecificUserById()
	{
		int userId = configReader.getIntProperty("userId");
		given()
		   .pathParam("id",userId)
		.when()
		  .get(Product_Routes.GET_USER_BY_ID)
		  
		.then()
		   .statusCode(200)
		   .log().body();
		   
		
	}
	
	//3.get users by limit
	
	@Test
	public void testGetUserByLimit()
	{
		 given()
		   .pathParam("limit",3)
		 .when()
		   .get(Product_Routes.GET_USERS_WITH_LIMIT)
		 .then()
		   .statusCode(200)
		   .body("size()",equalTo(3))
		   .log().body();
		 
	}
	
	//4.get users in desc order
	
	@Test
	public void testGetUsersSorted()
	{
		Response response = given()
		   .pathParam("order","desc")
		.when()
		   .get(Product_Routes.GET_USERS_SORTED)
		.then()
		  .statusCode(200)
		  .extract().response();
		
		List<Integer>userIds = response.jsonPath().getList("id",Integer.class);
		
		assertThat(isSortedDescending(userIds),is(true));
		
	}
	
	//5.get users in asc order
	
		@Test
		public void testGetUsersSortedAsc()
		{
			Response response = given()
			   .pathParam("order","asc")
			.when()
			   .get(Product_Routes.GET_USERS_SORTED)
			.then()
			  .statusCode(200)
			  .extract().response();
			
			List<Integer>userIds = response.jsonPath().getList("id",Integer.class);
			
			assertThat(isSortedAscending(userIds),is(true));
			
		}
		
		//6.create users
		
		@Test
		public void testCreateUsers()
		{
			User newUser = Product_payload.userPayload();
			int id=given()
			   .contentType(ContentType.JSON)
			   .body(newUser)
			   
			.when()
			   .post(Product_Routes.CREATE_USER)
			   
			.then()
			   .log().body()
			  .statusCode(201)
			   .body("id",notNullValue())
			   .extract().jsonPath().getInt("id");
			 
			System.out.println("Generated UserID=======:"+id);
		}
		
		//7.update users
		
				@Test
				public void testUpdateUsers()
				{
					int userId = configReader.getIntProperty("userId");
					User updateUser= Product_payload.userPayload();
					
					given()
					   .contentType(ContentType.JSON)
					   .pathParam("id",userId)
					   .body(updateUser)
					   
					.when()
					   .put(Product_Routes.UPDATE_USER)
					   
					.then()
					   .log().body()
					  .statusCode(200)
					   .body("username",equalTo(updateUser.getUsername()));
					  
				}
				
				//8.delete user

				@Test
				public void testDeleteUsers()
				{
					int userId = configReader.getIntProperty("userId");
					
					
					given()
				
					   .pathParam("id",userId)
					   
					   
					.when()
					   .delete(Product_Routes.DELETE_USER)
					   
					.then()
					  
					  .statusCode(200);
					   
					  
				}
}
