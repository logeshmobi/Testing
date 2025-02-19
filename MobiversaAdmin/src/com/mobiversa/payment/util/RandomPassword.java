package com.mobiversa.payment.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

//import java.util.Random;


public class RandomPassword {
	private static final String CHAR_LIST =
	        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    private static final int RANDOM_STRING_LENGTH = 8;
	     
	    /**
	     * This method generates random string
	     * @return
	     */
	    public String generateRandomString(){
	         
	        StringBuffer randStr = new StringBuffer();
	        for(int i=0; i<RANDOM_STRING_LENGTH; i++){
	            int number = getRandomNumber();
	            char ch = CHAR_LIST.charAt(number);
	            randStr.append(ch);
	        }
	        return randStr.toString();
	    }
	     
	    /**
	     * This method generates random numbers
	     * @return int
	     */
	    private static int getRandomNumber() {
	        int randomInt = 0;
	        SecureRandom randomGenerator = new SecureRandom();
	        randomInt = randomGenerator.nextInt(CHAR_LIST.length());
	        if (randomInt - 1 == -1) {
	            return randomInt;
	        } else {
	            return randomInt - 1;
	        }
	    }
	    private static String  bytesToHex(byte[] hash) {
	        return DatatypeConverter.printHexBinary(hash).toLowerCase();
	        }
	    
	    public static String getMD5Hash(String data) {
	           String result = null;
	           try {
	               MessageDigest digest = MessageDigest.getInstance("MD5");
	               byte[] hash = digest.digest(data.getBytes("UTF-8"));
	              
	               return bytesToHex(hash); // make it printable
	            }catch(Exception ex) {
	               ex.printStackTrace();
	            }
	          return result;
	    }
	     
	    
	    public static String generateEzywayTid()
		{
		int aNumber = 0; 
		aNumber = (int)((Math.random() * 9000000)+1000000); 
		System.out.print((aNumber));
		
		return String.valueOf(aNumber);
		}
	    
	    public static void main(String a[]){
	    	String tid = "66610001";
	    	RandomPassword msr = new RandomPassword();
	    	for (int i=0;i<15;i++) {
	       System.out.println(msr.generateRandomString());
	    	
	    	
	    	Date date = new Date();
			
			String df = new SimpleDateFormat("yyyyMMdd").format(date);
			
			String dt = new SimpleDateFormat("HHmmss").format(date);
			
			//System.out.print(" ParnterID: "+ df+tid+dt);
			
			String partnerId= df+tid+dt;
			System.out.print(" ParnterID: "+ partnerId);
			System.out.println("Data :"+msr.getMD5Hash(partnerId));
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    	}
	       
	    }


	    
}
