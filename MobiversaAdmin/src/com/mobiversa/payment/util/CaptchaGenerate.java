package com.mobiversa.payment.util;

public class CaptchaGenerate {

	public static String getRandomCaptcha() {
		
		char[] alphanum = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', '0' };

		String a = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";
		String b = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";
		String c = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";
		String d = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";
		String e = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";
		String f = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";
		String g = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";
		String captchaCode = a +  b +  c + d  + e  + f + g;
		String captcha = captchaCode.trim().replaceAll("\\s+", "").toString();
		
		return captcha;
	}
}
