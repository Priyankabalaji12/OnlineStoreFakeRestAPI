package testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.module.jsv.JsonSchemaValidator;
import routes.Product_Routes;

public class SchemaTests extends BaseClass
{
	@Test()
	public void testProductSchema()
	{
		int productId= configReader.getIntProperty("productId");
		
		given()
		   .pathParam("id",productId)
		.when()
		  .get(Product_Routes.GET_PRODUCT_BY_ID)
		  
		.then()
		  .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("productSchema.json"));	  
		
	}
	
	//2.Test cart by id
		@Test
		public void testGetCartSchema()
		{
			int cartId = configReader.getIntProperty("cartId");
			given()
			   .pathParam("id",cartId)
			.when()
			   .get(Product_Routes.GET_CART_BY_ID)
			.then()
			  .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("cartSchema.json"));
			
			
		}
		
		@Test
		public void testUserSchema()
		{
			int userId = configReader.getIntProperty("userId");
			given()
			   .pathParam("id",userId)
			.when()
			  .get(Product_Routes.GET_USER_BY_ID)
			  
			.then()
			   .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("userSchema.json"));
			   
			
		}
}
