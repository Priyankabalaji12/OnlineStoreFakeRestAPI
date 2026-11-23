package testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import pojo.Products_Pojo;
import routes.Product_Routes;

public class ProductDataDrivenTest extends BaseClass
{
	@Test(dataProvider="jsonDataProvider",dataProviderClass=utils.DataProviders.class)
   public void testAddNewProduct(Map<String,String> data)
   {
		String title = data.get("title");
	   double price= Double.parseDouble(data.get("price"));
	    String Category= data.get("category");
	   String description= data.get("decription");
	   String image = data.get("image");
	    
		
		//from this Map data we have to get all the values and we need to pass those into pojo class
		
	   Products_Pojo newProduct = new Products_Pojo(title,price,Category,description,image);
	   
		int productId = given()
		   .contentType(ContentType.JSON)
		   .body(newProduct)
		   
		.when()
		   .post(Product_Routes.CREATE_PRODUCT)
		   
		.then()
		  .log().body()
		  .statusCode(200)
		  .body("id",notNullValue())
		  .body("title",equalTo(newProduct.getTitle()))  
		  .extract().jsonPath().getInt("id");
		
		System.out.println("Generated Product id ="+ productId);
		
		//delete product
		
		given()
		   .pathParam("id",productId)
		.when()
		   .delete(Product_Routes.DELETE_PRODUCT)
		   
		.then()
		  .statusCode(200);
		 
   }
}
