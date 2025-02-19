package com.mobiversa.payment.service;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.mobiversa.payment.util.PropertyLoad;

@Service
public class AgniService {
	
	  private static final Logger logger=Logger.getLogger(AgniService.class.getName());


	@SuppressWarnings("nls")
	public static void refreshCache(){
		logger.info("MDR cache refresh service initialized");
		String agniUrl = PropertyLoad.getFile().getProperty("MDR_CACHE_REFRESH_API_AGNI");
		HttpURLConnection connection = null;
		try {
			URL url = new URL(agniUrl);
			connection = (HttpURLConnection)url.openConnection();
			
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setConnectTimeout(10000);
			connection.connect();
			
			logger.info(connection.getResponseCode()==200? "MDR Cache refreshed successfully by calling Agni API" : "Failed to refresh the MDR cache");
			
		} catch (Exception e) {
			logger.error("Exception while connecting with the AGNI service \nCause::"+e.getMessage());
		} finally {
			connection.disconnect();
		}
	}
}
