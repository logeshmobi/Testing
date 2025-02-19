package com.mobiversa.payment.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;

public class BoostWalletApi {
	protected final static Logger logger = Logger.getLogger(BoostWalletApi.class.getName());
	public static ResponseBoostWalletApi getBoostWalletBalance()
	{
		Gson gson = new Gson();
		String result =null;
		ResponseBoostWalletApi responseBoostWalletBalance=new ResponseBoostWalletApi();
		String apiTokenValue = BoostApiToken.apiToken();
		//logger.info("apiTokenValue: "+apiTokenValue);

		/*HttpClient httpClient = new DefaultHttpClient();
		HttpGet getMethod = new HttpGet(PropertyLoad.getFile().getProperty(
				"B_WALLETBALANCE_URL"));

		getMethod.addHeader("Content-Type", "application/json");
		getMethod.addHeader("Authorization", "Bearer " + apiTokenValue);*/
        URL url;
		try {
			url = new URL(PropertyLoad.getFile().getProperty(
					"B_WALLETBALANCE_URL"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setDoOutput(true);
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty ("Authorization",  "Bearer " + apiTokenValue);
	        try {
	        StringBuffer response = new StringBuffer();
	        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

	        String inputLine=null;
	        while ((inputLine = br.readLine()) != null) {
	              response.append(inputLine);
	        }

	        result = response.toString();
	     //   logger.info("boost wallet response: " + result);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        //conn.setRequestProperty("Content-Type", "application/json");
        
        


		/*HttpResponse response1;
		
			response1 = httpClient.execute(getMethod);
			result = EntityUtils.toString(response1.getEntity());

			logger.info("boost wallet response: "+result);*/
			//System.out.println("wallet response: "+result);
			
			JSONObject dataObj = new JSONObject(result);
			responseBoostWalletBalance = gson.fromJson(result, ResponseBoostWalletApi.class);
			/*int currentWalletBalance=dataObj.getInt("currentWalletBalance");
			int startingWalletBalance=dataObj.getInt("startingWalletBalance");
			int totalNoPaymentTransaction=dataObj.getInt("totalNoPaymentTransaction");
			int totalNoVoidTransaction=dataObj.getInt("totalNoVoidTransaction");
			int totalPaymentAmount=dataObj.getInt("totalPaymentAmount");
			int totalVoidAmount=dataObj.getInt("totalVoidAmount");
			
			responseBoostWalletBalance=new ResponseBoostWalletApi(currentWalletBalance,startingWalletBalance,totalNoPaymentTransaction,
					totalNoVoidTransaction,totalPaymentAmount,totalVoidAmount);*/
			// logger.info("responseBoostWalletBalance: "+responseBoostWalletBalance);
			
		}
		catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return responseBoostWalletBalance;

		
	}
}
