package com.mobiversa.payment.util;

import java.util.ArrayList;
import java.util.List;

public class test2 {
	
	
	public static void main(String[] args) {
		int recordCount=500;
		int recordavg=0;
		
		if(recordCount >= 100) {
			recordavg=recordCount/100;
		}else {
			recordavg=1;
		}
	
		System.out.println(recordavg);
	
		List<String> addList=new ArrayList<String>();
		for(int i=1;i<=recordavg;i++) {
			addList.add(String.valueOf(i));
		}
		
		System.out.println(addList.toString());
		
	}
	
	
	
	
	
	
	
	
}
