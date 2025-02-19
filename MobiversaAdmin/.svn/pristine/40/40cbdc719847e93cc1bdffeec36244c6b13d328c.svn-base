package com.mobiversa.payment.util;
//import java.util.Random;

import java.security.SecureRandom;

/*import com.mobiversa.common.bo.MobiPromo;*/

public class RandomPcode {
	

	


	
		private static final String CHAR_LIST =
		        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		    private static final int RANDOM_STRING_LENGTH = 5;
		    
		    
		  
		    
		     
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
		     
		    public static void main(String a[]){
		    	/*RandomPassword msr = new RandomPassword();
		    	for (int i=0;i<5;i++)
		        System.out.println(msr.generateRandomString());*/
		    	
		    	/*String a1[]={"test","test1","test2","test3"};
		    	String a3="\"";
		    	for (String a2 : a1){
		    		a3=a3+a2;
		    		a3=a3+"\",\"";
		    	}
		    	System.out.println("Data : "+a3);
		    	a3=a3+"\"";
		    	System.out.println("Data : "+a3);
		    	a3=a3.replace(",\"\"", "");
		    	System.out.println("Data : "+a3);*/
		    }


	}


