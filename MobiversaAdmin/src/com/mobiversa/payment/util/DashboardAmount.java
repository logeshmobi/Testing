package com.mobiversa.payment.util;

import java.util.regex.Pattern;

public class DashboardAmount {
	public static int roundNum(int max) {
		// max=9566646;
		int len=String.valueOf(max).length();
		System.out.println("length of max value: "+len+" "+max);
		
		int rounded = 0;
		
		if(len==10) {
			rounded = ((Math.round(max)+999999999) / 1000000000 ) * 1000000000;
		}else if(len==9) {
			rounded = ((Math.round(max)+99999999) / 100000000 ) * 100000000;
		}else if(len==8) {
			rounded = ((Math.round(max)+9999999) / 10000000 ) * 10000000;
		}else if(len==7) {
			rounded = ((Math.round(max)+999999) / 1000000 ) * 1000000;
		}else if(len==6) {
			rounded = ((Math.round(max)+99999) / 100000 ) * 100000;
		}else if(len == 5) {
			rounded = ((Math.round(max)+9999) / 10000 ) * 10000;
		}else if(len == 4) {
			rounded = ((Math.round(max)+999) / 1000 ) * 1000;
		}else if(len == 3) {
			rounded = ((Math.round(max)+99) / 100 ) * 100;
		}
		len=len+1;
		System.out.println("rounded value:" +rounded+" "+String.valueOf(rounded).length()+" len: "+len);
		String data=String.valueOf(rounded);
		String d=null;
		if(len==String.valueOf(rounded).length()) {
			System.out.println("insideif");
			d=data;
		}else {
			 d=data.replaceFirst(Pattern.quote(data.substring(0,1)), "10");
		}
		System.out.println(d+" "+d.length());
		
		return Integer.parseInt(d);
		
	}
	
	public static void main(String[] args) {
		System.out.println("math rounded value: "+Math.round(9566646.77f));
		System.out.println(DashboardAmount.roundNum(Math.round(95666.77f)));
	}
}
