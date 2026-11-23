package testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.response.Response;
import routes.Product_Routes;

public class CartTests extends BaseClass
{
   //1.Get All Carts 
	
	@Test
	public void testGetAllCarts()
	{
		 given()
		 
		 .when()
		    .get(Product_Routes.GET_ALL_CARTS)
		 .then()
		  .statusCode(200)
		  .body("size()",greaterThan(0));
		 
	}
	
	//2.Test cart by id
	@Test
	public void testGetCartById()
	{
		int cartId = configReader.getIntProperty("cartId");
		given()
		   .pathParam("id",cartId)
		.when()
		   .get(Product_Routes.GET_CART_BY_ID)
		.then()
		  .statusCode(200)
		  /*
		   * whatver cart id we are passing as req same we need to get as a response
		   */
		  .body("id",equalTo(cartId));
		
		
	}
	
	//3.Get CartBy DateRange
	@Test(enabled=false)
	public void testGetCartsByDateRange() {

	    String startDate = configReader.getProperty("startdate");
	    String endDate = configReader.getProperty("enddate");

	    Response response = given()
	            .pathParam("startdate", startDate)
	            .pathParam("enddate", endDate)
	        .when()
	            .get(Product_Routes.GET_CART_By_DATERANGE)
	        .then()
	            .statusCode(200)
	            .body("size()", greaterThan(0))
	            .extract().response();

	    // Extract the list of cart dates
	    List<String> cartDates = response.jsonPath().getList("date");

	    // Validate cart dates
	    validateCartDatesWithinRange(cartDates, startDate, endDate);

	    assertThat(
	            validateCartDatesWithinRange(cartDates, startDate, endDate),
	            is(true)
	    );
	}
	
	//test Get User Cart by userid
	
	@Test
	public void testGetUserCart()
	{
		int userId = configReader.getIntProperty("userId");
		given()
		  .pathParam("userId",userId)
		 
		.when()
		   .get(Product_Routes.GET_USER_CART)
		
		.then()
		  .statusCode(200)
		  
		  //validate that response contains the correct user id or not which we are passing
		  .body("userId",everyItem(equalTo(userId)));	
		  
	}
	
	
	//@Test
	public void testCartWithLimit() {

	    int limit = configReader.getIntProperty("limit");

	    given()
	        .queryParam("limit", limit)
	        .log().uri()
	    .when()
	        .get(Product_Routes.GET_CARTS_WITH_LIMIT)
	    .then()
	        .statusCode(200)
	        .log().body()
	        .body("size()", lessThanOrEqualTo(limit));
	}
	
	//@Test
	public void testGetCartsSorted() {

	    Response response = given()
	            .pathParam("order", "desc")
	        .when()
	            .get(Product_Routes.GET_CARTS_SORTED)
	        .then()
	            .statusCode(200)
	            .body("size()", greaterThan(0)) // Validate that the response is not empty
	            .extract().response();

	    // Parse response to get product IDs
	    List<Integer> cartIds = response.jsonPath().getList("id", Integer.class);

	    // Validate IDs are sorted in ascending order (calling helper method)
	    assertThat(isSortedDescending(cartIds), is(true));
	}
	
	//@Test
	public void testGetCartsSortedAsc() {

	    Response response = given()
	            .pathParam("order", "asc")
	        .when()
	            .get(Product_Routes.GET_CARTS_SORTED)
	        .then()
	            .statusCode(200)
	            .body("size()", greaterThan(0)) // Validate that the response is not empty
	            .extract().response();

	    // Parse response to get product IDs
	    List<Integer> cartIds = response.jsonPath().getList("id", Integer.class);

	    // Validate IDs are sorted in ascending order (calling helper method)
	    assertThat(isSortedAscending(cartIds), is(true));
	}
}
