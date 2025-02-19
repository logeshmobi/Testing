package com.mobiversa.payment.notification;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;

public class AndroidPushNotification {
	
	public final static String AUTH_KEY_FCM = "AIzaSyA6cIHe0oFnVojWNfU3HFEoC5NoGbcTBss";
	public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";
	
	@SuppressWarnings("unchecked")
	public void sendNotification(String userDeviceIdKey,String code,String name) throws Exception{
		
		   String authKey = AUTH_KEY_FCM; // You FCM AUTH key
		   String FMCurl = API_URL_FCM; 

		   URL url = new URL(FMCurl);
		   HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		   conn.setUseCaches(false);
		   conn.setDoInput(true);
		   conn.setDoOutput(true);

		   conn.setRequestMethod("POST");
		   conn.setRequestProperty("Authorization","key="+authKey);
		 //  conn.setRequestProperty("Authorization", "key=<authKey>");
		   conn.setRequestProperty("Content-Type","application/json");

		   JSONObject json = new JSONObject();
		   json.put("to",userDeviceIdKey.trim());
		   JSONObject info = new JSONObject();
		   info.put("title", "Promotion Approved"); // Notification title
		   info.put("sound", "notification");
		   info.put("body", "Your Ezywire Promotion has been approved" +":" + code +" : "+ name); // Notification body
		   json.put("notification", info);
		   OutputStreamWriter wr = null;
		   try{
			    wr = new OutputStreamWriter(conn.getOutputStream());
			   wr.write(json.toString());
			   wr.flush();
			   conn.getInputStream();
		   }catch(Exception e){
			   
		   }finally{
			   if(wr != null){
				   wr.close();
			   }
			  /* if(conn !=null){
				   conn.disconnect();
			   }*/
		   }
		
	}

}
