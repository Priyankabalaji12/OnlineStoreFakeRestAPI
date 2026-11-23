package testcases;



import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloads.Product_payload;
import pojo.Products_Pojo;
import routes.Product_Routes;
public class Product_Tests extends BaseClass
{
  //1.Test to retrive all produucts
	@Test()
	public void testGetAllProducts()
	{
		given()
		
		.when()
		  .get(Product_Routes.GET_ALL_PRODUCTS)
		  
		.then()
		  .statusCode(200)
		  .body("size()",greaterThan(0));
	}
	
	//2.gET PRODUCT BYID
	/*
	 * generally we will get the id from the above testmethod response that too it 
	 * should be post response then only id will be generated and here we are
	 * going order by order so we used product id data from config.properties
	 * 
	 */
	@Test()
	public void testGetSingleProductById()
	{
		int productId= configReader.getIntProperty("productId");
		
		given()
		   .pathParam("id",productId)
		.when()
		  .get(Product_Routes.GET_PRODUCT_BY_ID)
		  
		.then()
		  .statusCode(200)
		  .log().body();
		  
		
	}
	
	//3.get limited products
	
	@Test
	public void testGetLimitedProducts()
	{
		given()
		  .pathParam("limit",3)
		.when()
		   .get(Product_Routes.GET_PRODUCTS_WITH_LIMIT)
		   
		.then()
		  .statusCode(200)
		  .log().body()
		  .body("size()",equalTo(3));
		  
		  
	}


//4.Fetch data in desc order then asc order
  
	@Test
	
public void testGetSortedProductsDesc()
{
	Response response = given()
	    .pathParam("order","desc")
	.when()
	   .get(Product_Routes.GET_PRODUCTS_SORTED)
	   
	.then()
	   .statusCode(200)
	   .extract().response();
	
	  List<Integer> productId= response.jsonPath().getList("id",Integer.class);
	  assertThat(isSortedDescending(productId),is(true));
	
	
}
	
	
//5.Fetch in ascending
	@Test
	public void testGetSortedProductsAsc()
	{
		Response response = given()
		    .pathParam("order","asc")
		.when()
		   .get(Product_Routes.GET_PRODUCTS_SORTED)
		   
		.then()
		   .statusCode(200)
		   .extract().response();
		
		  List<Integer> productId= response.jsonPath().getList("id",Integer.class);
		  assertThat(isSortedAscending(productId),is(true));
		
		
	}
	
	//6.Get all products from all categories
	
	@Test
	public void testGetAllCategories()
	{
		given()
		
		.when()
		  .get(Product_Routes.GET_ALL_CATEGORIES)
		  
		.then()
		   .statusCode(200)
		   .body("size()",greaterThan(0))
		   .log().body();
	}
	
	//7.Test to get  products based on specific category
	@Test
	public void testGetProductsFromSpecificCategory()
	{
		given()
		   .pathParam("category","electronics")
		.when()
		   .get(Product_Routes.GET_PRODUCTS_BY_CATEGORY)
		   
		.then()
		   .statusCode(200)
		   .body("size()",greaterThan(0))
		   .body("category",everyItem(notNullValue()))
		   .body("category",everyItem(equalTo("electronics")))
		   .log().body();
		   
	}
	
	//8.add a new product 
	
	//@Test
	public void testAddNewProduct()
	{
		Products_Pojo newProducts_Pojo = Product_payload.ProductPayload();
		int productId = given()
		   .contentType(ContentType.JSON)
		   .body(newProducts_Pojo)
		   
		.when()
		   .post(Product_Routes.CREATE_PRODUCT)
		   
		.then()
		  .log().body()
		  .statusCode(200)
		  .body("id",notNullValue())
		  .body("title",equalTo(newProducts_Pojo.getTitle()))  
		  .extract().jsonPath().getInt("id");
		
		System.out.println(productId);
		   
	}
	
	//9.Update the existing product
	
	@Test
	public void testUpdateProduct()
	{
       int productId= configReader.getIntProperty("productId");
      Products_Pojo updatedPayload= Product_payload.ProductPayload();
		
		given()
		    .contentType(ContentType.JSON)
		    .body(updatedPayload)
		   .pathParam("id",productId)
		   
		.when()
		  .put(Product_Routes.UPDATE_PRODUCT)
		
		.then()
		  .statusCode(200)
		  .log().body()
		  .body("title",equalTo(updatedPayload.getTitle()));
	}
	
	//10.delete the existing product
	
	@Test
	public void  testDeleteProduct()
	{
		 int productId= configReader.getIntProperty("productId");
		given()
		  .pathParam("id",productId) 
		.when()
		   .delete(Product_Routes.DELETE_PRODUCT)
		.then()
		   .statusCode(200);
		    
	}
}

