package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader 
{
   Properties properties;
   private static final String CONFIG_FILE_PATH=".\\src\\test\\resources\\config.properties";
   
   public ConfigReader() {

	    properties = new Properties();

	    try (FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH)) {

	        properties.load(fileInputStream);

	    } catch (IOException e) {

	        e.printStackTrace();
	        throw new RuntimeException("Failed to load config.properties file");

	    }
	}
   //to read string data from property file
   public String getProperty(String Key)
   {
	   return properties.getProperty(Key);
   }
   
   //to read numeric data from property file
   public int getIntProperty(String Key)
   
   {
	   return Integer.parseInt(properties.getProperty(Key));
   }
   
}
