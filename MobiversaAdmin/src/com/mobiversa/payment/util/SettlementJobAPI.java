package com.mobiversa.payment.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;

import com.mobiversa.payment.controller.PayoutUserController;

public class SettlementJobAPI {

	private static final Logger logger = Logger.getLogger(PayoutUserController.class);

	public String settlementJobTriggerApi() throws IOException {

		logger.info("Settlement Job URL :" + PropertyLoad.getFile().getProperty("SETTLEMENT_JOB_URL"));
		logger.info("Settlement Job APIKEY :" + PropertyLoad.getFile().getProperty("SETTLEMENT_JOB_APIKEY"));

		URL url = new URL(PropertyLoad.getFile().getProperty("SETTLEMENT_JOB_URL"));
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// Set the request method to POST
		connection.setRequestMethod("POST");

		// Set the API key in the header
		connection.setRequestProperty("ApiKey", PropertyLoad.getFile().getProperty("SETTLEMENT_JOB_APIKEY"));

		// Enable input/output for the connection
		connection.setDoOutput(true);
		connection.setDoInput(true);

		// Get the response code
		int responseCode = connection.getResponseCode();

		// Read the response from the API
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuilder response = new StringBuilder();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// Print the response
		logger.info("Response Code: " + responseCode);
		logger.info("Response Body: " + response.toString());

		return response.toString();
	}

}
