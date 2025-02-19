package com.mobiversa.payment.connect;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomStringGeneration {
	public static void main(String a[]) {
		
	Date date = new Date();
	String tid = "765858095093";
	Long stan=(long) 765858095;
	
	
	String df = new SimpleDateFormat("yyyyMMdd").format(date);
	
	String dt = new SimpleDateFormat("HHmmss").format(date);
	String strStan = String.format("%06d", stan);
	
	
	
	String partnerId= df+strStan+tid+dt;
	System.out.println(partnerId);
	
}

}
