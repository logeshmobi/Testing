package com.mobiversa.payment.util;

import java.security.Key;
import java.util.Base64;

//import sun.misc.*;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Decoder;

public class AESencrp {

	private static final String ALGO = "AES";
	/*
	 * private static final byte[] keyValue = new byte[] { 'T', 'h', 'e', 'B',
	 * 'e', 's', 't','S', 'e', 'c', 'r','e', 't', 'K', 'e', 'y' };
	 */
	//private static byte[] keyValue = "WP02841126150021".getBytes();
	//private static byte[] keyValue = "WP02823441500098".getBytes();

	public static String encrypt(String Data,String keyValue) throws Exception {
		Key key = generateKey(keyValue);
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes());
		//String encryptedValue = Base64.getEncoder().encodeToString(encVal);// new BASE64Encoder().encode(encVal);
		//@SuppressWarnings("restriction")
		//String encryptedValue =  new BASE64Encoder().encode(encVal);
		String encryptedValue =  Base64.getEncoder().encodeToString(encVal);
		//String encryptedValue =  Base64.getEncoder().encodeToString(encVal);
		return encryptedValue;
	}

	public static String decrypt(String encryptedData,String keyValue) throws Exception {
		Key key = generateKey(keyValue);
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		//byte[] decordedValue = Base64.getDecoder().decode(encryptedData);// new
																			// BASE64Decoder().decodeBuffer(encryptedData);
		//@SuppressWarnings("restriction")
		//byte[] decordedValue =  new BASE64Decoder().decodeBuffer(encryptedData);
		byte[] decordedValue =  Base64.getDecoder().decode(encryptedData);
		//byte[] decordedValue =  Base64.getMimeDecoder().decode(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}
	
	
	public static String decrypt1(String encryptedData,String keyValue) throws Exception {
		Key key = generateKey(keyValue);
		Cipher c = Cipher.getInstance(ALGO);
		c.init(Cipher.DECRYPT_MODE, key);
		//byte[] decordedValue = Base64.getDecoder().decode(encryptedData);// new
																			// BASE64Decoder().decodeBuffer(encryptedData);
		//@SuppressWarnings("restriction")
		byte[] decordedValue =  new BASE64Decoder().decodeBuffer(encryptedData);
		//byte[] decordedValue =  Base64.getDecoder().decode(encryptedData);
		//byte[] decordedValue =  Base64.getMimeDecoder().decode(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}

	private static Key generateKey(String keyValue) throws Exception {
		
		System.out.println("inside generateKey" +keyValue);
		Key key = new SecretKeySpec(keyValue.getBytes(), ALGO);
		return key;
	}
	
	
	public static String hexaToAscii(String s, boolean toString) {

		String retString = "";
		String tempString = "";
		int offset = 0;
		if (toString) {
			for (int i = 0; i < s.length() / 2; i++) {

				tempString = s.substring(offset, offset + 2);
				retString += tempString.equalsIgnoreCase("1c") ? "[1C]"
						: decodeHexString(tempString);
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


	public static void main(String ar[]) {
		String data = "5453010000095323~123~2103";
		String enData = null;
		String deData = null;
		String heData = null;
		String stData = null;
		try {
			enData = encrypt(data,"4400000244000002");
			System.out.println(" Encrypted Data : " + enData);
			heData = hexaToAscii(enData,false);
			System.out.println(" Hexa Encrypted Data : " + heData);
			stData = hexaToAscii(heData,true);
			System.out.println(" Hexa Decrypted Data : " + stData);
			//heData="5643354D4D30634A38774E4B6E5271723961746649773D3D0A";
			//System.out.println(" Hexa Encrypted Data : " + heData);
			//heData = heData.substring(0, 48);
			/*System.out.println(" Hexa Encrypted Data : " + heData);
			stData = hexaToAscii(heData,true);*/
//			System.out.println(" Hexa Decrypted Data : " + stData);
			deData = decrypt1(stData,"3214001932140019");
			System.out.println(" Decrypted1 Data : " + deData);
			/*deData = decrypt(stData,"3214001932140019");
			System.out.println(" Decrypted Data : " + deData);*/
			/*deData = decrypt("Jy1d2DUVvtAU7F7F5btkbw==","WPC2046330000920");
			System.out.println(" Decrypted Data : " + deData);*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
