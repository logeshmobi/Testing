package com.mobiversa.payment.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
public class Commons {        
	protected static Logger logger = Logger.getLogger(Commons.class);   
      public static Properties getFile(){ 
    	  
    	  //To get path   
    	  Properties prop = new Properties();     
    	  InputStream input = null;
     
    try {
        //ClassLoader loader = Thread.currentThread().getContextClassLoader();            
    	//InputStream stream = loader.getResourceAsStream("config.properties");      
    	input = new FileInputStream("C:\\Mobi_config\\AutoRun\\BoostSettlement\\config.properties");    
    	prop.load(input);         
    	return prop;      
    	} catch (IOException io) {   
    		io.printStackTrace();       
    		}
    finally {           
    	if (input != null) {  
    		try {                     input.close();   
    		} catch (IOException e) {                     e.printStackTrace();      
    		}       
    		}       
    	}
    return prop;   
    }
      
      public static void main(String[] args) {
    	 System.out.println(Commons.getFile().getProperty(
					"IP"));
	}
      
      
      
    }
