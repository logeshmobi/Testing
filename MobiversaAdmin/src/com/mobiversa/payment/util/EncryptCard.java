package com.mobiversa.payment.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.mobiversa.payment.dto.MotoTxnDet;

public class EncryptCard {
	
	public static String EncryptCardDet(MotoTxnDet fs) {

		String cardDetails = fs.getCardno() + "#" + fs.getExpDate() + "#"
				+ fs.getCvvno();
		
		   String keys = "b07ad9f31df158edb188a41f725899bc";
	        String encKey = keys.substring(0, 16);
	        String initVector = keys.substring(16);
	        

		String encryptCard = null;
		String decryptCard = null;
		try {
			String encrypted = encrypt(encKey, initVector,cardDetails);
			encryptCard = hexaToAscii(encrypted,false);
		        System.out.println(" Encrypted Card Details : " + encryptCard);
			fs.setCardDetails(encryptCard);
			
			

		} catch (Exception e) {

			e.printStackTrace();
		}

		return fs.getCardDetails();

	}
	
	 public static String encrypt(String key, String initVector, String value) {
	        try {
	            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");



	           Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);



	           byte[] encrypted = cipher.doFinal(value.getBytes());
	            System.out.println("encrypted string: " + Base64.encodeBase64String(encrypted));



	           return Base64.encodeBase64String(encrypted);
	        } catch (Exception ex) {
	            ex.printStackTrace();
	        }



	       return null;
	    }
	
	
public static String EncryptTC(String mid) {
		
		String keyValue ="7777777700000000";

		String encryptTc = null;

		try {
			encryptTc = encryptCard(mid, keyValue);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return encryptTc;

	}
	public static String DecryptTC(String encryptedTC) {
		
		String keyValue ="7777777700000000";

		
		String decryptTc = null;
		try {
			
			decryptTc = decryptCard(encryptedTC, keyValue);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return decryptTc;

	}
	
	public static String encryptCard(String cardDetails, String key)
			throws Exception {

		return AESencrp.hexaToAscii(AESencrp.encrypt(cardDetails, key), false);

	}

	public static String decryptCard(String encryptedCard, String keyValue)
			throws Exception {

		return AESencrp.decrypt(AESencrp.hexaToAscii(encryptedCard, true),keyValue);

	}
	
	public static void main(String[] args)
	{
		
		String FinalCard = null;
		
		String cardDetails = "5453010000095323" + "#" + "123" + "#" + "2311";
		String keyValue = "10003278" + "00000000";

		String encryptCard = "6E4D36544D5A44765A6C3571746B376A2F444D6D6851752B4E5168544B524442796E6D694F796C556B36733D";
		String decryptCard = null;
		try {
			encryptCard = encryptCard(cardDetails, keyValue);
			
			FinalCard = encryptCard;
			decryptCard = decryptCard(encryptCard, keyValue);

		} catch (Exception e) {

			e.printStackTrace();
		}

		System.out.println(FinalCard);
		System.out.println(decryptCard);
		
		String cardData1[] = decryptCard.split("#");
		
		System.out.println(cardData1[0]);
		System.out.println(cardData1[1]);
	}
	  public static String hexaToAscii(String s, boolean toString) {



	       String retString = "";
	        String tempString = "";
	        int offset = 0;
	        if (toString) {
	            for (int i = 0; i < s.length() / 2; i++) {



	               tempString = s.substring(offset, offset + 2);
	                retString += tempString.equalsIgnoreCase("1c") ? "[1C]" : decodeHexString(tempString);
	                offset += 2;
	            } // end for
	        } else {



	           for (int i = 0; i < s.length(); i++) {



	               tempString = s.substring(offset, offset + 1);
	                retString += encodeHexString(tempString);
	                offset += 1;
	            } // end for
	        }
	        return retString;
	    } // end hexaToAscii
	  
	  public static String decodeHexString(String hexText) {



	       String decodedText = null;
	        String chunk = null;



	       if (hexText != null && hexText.length() > 0) {
	            int numBytes = hexText.length() / 2;



	           byte[] rawToByte = new byte[numBytes];
	            int offset = 0;
	            for (int i = 0; i < numBytes; i++) {
	                chunk = hexText.substring(offset, offset + 2);
	                offset += 2;
	                rawToByte[i] = (byte) (Integer.parseInt(chunk, 16) & 0x000000FF);
	            }
	            // System.out.println(rawToByte.toString());
	            decodedText = new String(rawToByte);
	        }
	        return decodedText;
	    }
	  
	  public static String encodeHexString(String sourceText) {
	        byte[] rawData = sourceText.getBytes();
	        StringBuffer hexText = new StringBuffer();
	        String initialHex = null;
	        int initHexLength = 0;



	       for (int i = 0; i < rawData.length; i++) {
	            // System.out.println("raw "+rawData[i]);
	            int positiveValue = rawData[i] & 0x000000FF;
	            initialHex = Integer.toHexString(positiveValue);
	            initHexLength = initialHex.length();
	            while (initHexLength++ < 2) {
	                hexText.append("0");
	            }
	            hexText.append(initialHex);
	        }
	        return hexText.toString().toUpperCase();
	    }
	
	

}
