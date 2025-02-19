package com.mobiversa.payment.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONObject;

public class BoostApiToken {

	// http://localhost:8080/RESTfulExample/json/product/post
	//public static void main(String args[])  {
		
	public static String apiToken()  {
	String inputLine=null;
	String output=null;
	String apiTokenValue=null;
	  try {
		  
		   
		//URL url = new URL("https://qa-serviceswallet.boostorium.com/online/authentication/");
		  URL url = new URL(PropertyLoad.getFile().getProperty("B_AUTH_URL"));
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");

		
		
		/*System.out.println(" Data : "+ PropertyLoad.getFile().getProperty("B_APIKEY"));
		System.out.println(" Data : "+ PropertyLoad.getFile().getProperty("B_APISECRET"));
		*/
		String input = "{\"apiKey\":\""+PropertyLoad.getFile().getProperty("B_APIKEY")+"\",\"apiSecret\":\""+PropertyLoad.getFile().getProperty("B_APISECRET")+"\"}";

		//System.out.println(" Data input : "+ input);
		//String input = "{\""+boostApi.getApiKeyname()+"\":\""+boostApi.getApiKeyvalue()+"\",\""+boostApi.getApiSecretName()+"\":\""+boostApi.getApiSecretValue()+"\"}";

		OutputStream os = conn.getOutputStream();
		os.write(input.getBytes());
		os.flush();
		
		//System.out.println(" Data input : "+ conn.getResponseCode());
		
		if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED)
		{
			//System.out.println(" Data input HTTP_CREATED ");
			/*throw new RuntimeException("Failed : HTTP error code : "
				+ conn.getResponseCode());*/
			
			//throw new MobileApiException(status)
			 StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			 while ((inputLine = br.readLine()) != null) {
	            	response.append(inputLine);
	            }
			 //System.out.println(" Data input HTTP_CREATED "+response);
			 output= response.toString();
			// System.out.println(" Data input HTTP_CREATED "+output);
			 JSONObject dataObj1 = new JSONObject(output);
			 apiTokenValue=dataObj1.getString("apiToken");
			// System.out.println(apiTokenValue);
			 
			 //System.out.println("here     "+output);
			//System.out.println("Output from Server .... \n");
			if (output != null) {
				//System.out.println("IN output boostAPiTokke : "+output);
			}
			
		}else if(conn.getResponseCode() == HttpURLConnection.HTTP_BAD_REQUEST){
			
			apiTokenValue = "E400";
		}else if(conn.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
			apiTokenValue = "E4011";
		}else if(conn.getResponseCode() == HttpURLConnection.HTTP_SERVER_ERROR){
			apiTokenValue = "E500";
		}else{
			
			//System.out.println(" Data input HTTP_CREATED ");
			
			 StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			 while ((inputLine = br.readLine()) != null) {
	            	response.append(inputLine);
	            }
			// System.out.println(" Data input HTTP_CREATED "+response);
			 output= response.toString();
			// System.out.println(" Data input HTTP_CREATED "+output);
			 JSONObject dataObj1 = new JSONObject(output);
			 apiTokenValue=dataObj1.getString("apiToken");
			// System.out.println(apiTokenValue);
			
		}
		//System.out.println();
		/*BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

		 output= br.readLine();
		 //System.out.println("here     "+output);
		//System.out.println("Output from Server .... \n");
		if (output != null) {
			//System.out.println("IN output boostAPiTokke : "+output);
		}
*/
		conn.disconnect();

	  }
	  catch (MalformedURLException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();

	 }
	  
	  
	  //System.out.println("API TOKEN: "+boostApiToken.api());
		/*JSONObject dataObj1 = new JSONObject(output);
		String apiTokenValue=dataObj1.getString("apiToken");
		System.out.println(apiTokenValue);*/
	  
	//return output;
		return apiTokenValue;
	

	}

}