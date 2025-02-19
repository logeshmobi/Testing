package com.mobiversa.payment.util;

import java.security.SecureRandom;

public class MobiversaRandomNumber {
	
	private static final String CHAR_LIST = "1234567890";
	
	
	public String generateRandomString(int length){
        
        StringBuffer randStr = new StringBuffer();
        for(int i=0; i<length; i++){
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }
	
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
	     
	    public static void main(String a[]){
	    	MobiversaRandomNumber msr = new MobiversaRandomNumber();
	    	String mid = "000009";
	    	for (int i=0;i<5;i++)
	    		
	        System.out.println(mid+msr.generateRandomString(9));
	       
	    }

}
