package payloads;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import net.datafaker.Faker;
import pojo.Address;
import pojo.Cart;
import pojo.CartProduct;
import pojo.Geolocation;
import pojo.Login;
import pojo.Name;
import pojo.Products_Pojo;
import pojo.User;

public class Product_payload
{
	private static final Faker faker = new Faker();
	private static final String categories[]= {"electronics","furniture","clothing","books","beauty"};
	
	static Random random = new Random();
  public static Products_Pojo ProductPayload()
   {
	 String name= faker.commerce().productName(); 
	double price=Double.parseDouble(faker.commerce().price());
	String description = faker.lorem().sentence();
	String imageUrl = "https://i.pravatar.cc/100";
	String category = categories[random.nextInt(categories.length)];
	
	new  Products_Pojo(name,price,description,imageUrl,category);
	
	return new  Products_Pojo(name,price,description,imageUrl,category);
	   
   }
  
  public static User userPayload()
  {
	  /*
	   * email,username,password,name,address,phone
	   */
	  
	  String firstName=faker.name().firstName();
	  String lastName= faker.name().lastName();
	  Name name = new Name(firstName,lastName);
	  
	  //location
	  
	  String latitude = faker.address().latitude();
	  String longitude = faker.address().longitude();
	  
	  Geolocation location = new Geolocation(latitude,longitude);
	  
	  
	  String city = faker.address().city();
	  String street =  faker.address().streetName();
   	  int number=  random.nextInt(100);
	  String zipcode= faker.address().zipCode();
	  Address address = new Address(city,street,number,zipcode,location);
	  
	  //user
		 String email= faker.internet().emailAddress();
		 String username = faker.name().username();
		 String password= faker.internet().password();
	     String phonenumber= faker.phoneNumber().cellPhone();
	     
	     User user = new User(email,username,password,name,address,phonenumber);
	     
	  return user;
	  
	 
	  
  }
  
  //cart
  /*
   * 1.parameter should be user id bcz particular user will create cart 
   * 2.
   * 
   * 
   */
  public static Cart cartPayload(int userId)
  {
	  //Adding one random product to cart
	  
	  List<CartProduct> products = new ArrayList<>();
	  
	  //here in cart product pojo class we have 2 variables so state that with random obj
	 int productId= random.nextInt(100);
	 int quantity =  random.nextInt(10)+1;
	 
	 products.add(new CartProduct(productId,quantity));
	 
	 		
	  
	
	 SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	 String date = outputFormat.format(new Date());
	 return new Cart(userId, date, products);
	  
  }
  
  //login payload
  public static Login loginPayload()
  {
	 String username= faker.name().username();
	String password= faker.internet().password();
	
	Login login = new Login(username,password);
	
	return login;
	
	  
  }
}
