package testcases;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import payloads.Product_payload;
import pojo.Login;
import routes.Product_Routes;


/*
 * 2 scenarios:
 * Valid Username and password
 * invalid username and password
 */
public class LoginTests extends BaseClass
{
	@Test
   public void testInvalidUserLogin()
   {
		Login newLogin =Product_payload.loginPayload();
		
	   given()
	      .contentType(ContentType.JSON)
	      .body(newLogin)
	      
	   .when()
	      .post(Product_Routes.AUTH_LOGIN)
	      
	   .then()
	      .log().body()
	      .statusCode(401)
	      .body(equalTo("username or password is incorrect"));
	   
   }
	
	//@Test
	   public void testValidUserLogin()
	   {
			String username = configReader.getProperty("username");
			String password = configReader.getProperty("password");
			
			Login login=new Login(username,password);
			
		   given()
		      .contentType(ContentType.JSON)
		      .body(login)		      
		   .when()
		      .post(Product_Routes.AUTH_LOGIN)
		      
		   .then()
		      .log().body()
		      .statusCode(200)
		      .body("token",notNullValue());
		   
	   }
}
