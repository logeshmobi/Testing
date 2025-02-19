package com.mobiversa.payment.dto;

import java.lang.reflect.Field;

import org.apache.log4j.Logger;

import com.mobiversa.payment.util.MobiHtmlUtils;

public class BaseDataImpl implements BaseData {
	
	protected final Logger logger = Logger.getLogger(BaseDataImpl.class);

	@Override
	public <E> E vaildated(E abc) {
		
		boolean a = false ;//= false;
		boolean b = false ;
		logger.info("Bool :"+b);
		for (Field field : abc.getClass().getDeclaredFields()) {
 	        field.setAccessible(true);
 	        String name = field.getName();
 	        Object value = null;
 			try {
 				if(!name.equals("password")) {
	 				value = field.get(abc);
	 				if(value != null) {
	 					
	 					a = MobiHtmlUtils.isHtmlIs(value);
	 					if(a == true) {
	 						logger.info("testtt:"+a);
	 						b= true;
	 					}
	 				}
 				}
 				if(!name.equals("deviceToken")) {
	 				value = field.get(abc);
	 				if(value != null) {
	 					
	 					a = MobiHtmlUtils.isHtmlIs(value);
	 					if(a == true) {
	 						logger.info("testtt1:"+a);
	 						b= true;
	 					}
	 				}
 				}
 			} catch (IllegalArgumentException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			} catch (IllegalAccessException e) {
 				// TODO Auto-generated catch block
 				e.printStackTrace();
 			}
 	   //  System.out.printf("%s: %s%n", name, value);
		}
		logger.info("Bool 1:"+b);
		if(b == true) {
			logger.info("Bool 12:"+b);
			return abc;
		}else {
			logger.info("Bool 13:"+b);
			return null;
		}
	}

}
