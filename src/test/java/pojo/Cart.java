package pojo;

import java.util.Date;
import java.util.List;

public class Cart 
{
  private int userid ;
  private String  date ; //get the date in string format
  private List<CartProduct> products;
  
 


public List<CartProduct> getProducts() {
	return products;
}

public void setProducts(List<CartProduct> products) {
	this.products = products;
}


  public Cart(int userid, String date2, List<CartProduct> products) {
		super();
		this.userid = userid;
		this.date = date2;
		this.products = products;
	}
  
  public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
  
  
} 
