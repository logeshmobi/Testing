package com.mobiversa.payment.controller.bean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobiversa.payment.dto.RegMobileUser;
import com.mobiversa.payment.util.AEEmailUtils;

public class UpdateApi {
	private static final Logger logger = Logger.getLogger(UpdateApi.class.getName());

	public Apibean fpxStatusUpdate(String sellerOrderNo) {

		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		Apibean api = new Apibean();
		try {

			// 201-demo // url = new
			// URL("http://localhost:8080/externalapi/paidpayout/send");
			// url = new URL("http://localhost:8074/externalapi/dtl");
//			  url = new URL("http://192.168.11.202:8080/externalapi/fpxstatus");
			url = new URL(fpxStatusCheck());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("sellorOrderNum", sellerOrderNo);
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			logger.info("The params That passed" + paramss);

			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

			while ((inputLine = br.readLine()) != null) {

				response.append(inputLine);

			}
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			output = response.toString();

			api = gson.fromJson(output, Apibean.class);
			logger.info("1).ResponseCode : " + api.getResponseCode());

			if (api.getResponseData() != null) {
				logger.info("2).ResponseMessage Status : " + api.getResponseData().getStatus());
				logger.info("2.1).ResponseMessage Amount : " + api.getResponseData().getAmount());
				logger.info("2.2).ResponseMessage Mid : " + api.getResponseData().getMid());
				logger.info("2.3).ResponseMessage Txn Date : " + api.getResponseData().getTransactionDate());
				logger.info("2.4).ResponseMessage netamount : " + api.getResponseData().getNetAmount());
				logger.info("2.5).ResponseMessage MdrAmt : " + api.getResponseData().getMdramt());
				logger.info("2.6).ResponseMessage SellerOrderNo : " + api.getResponseData().getSellerOrderNo());
				logger.info("2.7).ResponseMessage FpxTxnId : " + api.getResponseData().getFpxTxnId());
				logger.info("2.8).ResponseMessage SettledDate : " + api.getResponseData().getSettleDate());
				logger.info("2.9).ResponseMessage validateFPXUpdate : " + api.getResponseData().getValidateFPXUpdate());
				
				String validateFPXUpdate =  api.getResponseData().getValidateFPXUpdate();
				   // Validate and send email
	            if ("Yes".equalsIgnoreCase(validateFPXUpdate)) {
	                logger.info("Validation passed. Sending email...");
	                AEEmailUtils.sendFpxTransactionSuccessEmail(
	                        api.getResponseData().getMid(),
	                        api.getResponseData().getAmount(),
	                        api.getResponseData().getTransactionDate(),
	                        api.getResponseData().getSettleDate(),
	                        api.getResponseData().getSellerOrderNo(),
	                        api.getResponseData().getMdramt(),
	                        api.getResponseData().getNetAmount(),
	                        api.getResponseData().getFpxTxnId()
	                );
	                logger.info("Email sent successfully.");
	            } else {
	                logger.info("Validation failed. Email not sent.");
	            }
			}
			logger.info("3).ResponseDescription : " + api.getResponseDescription());

		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }

			e.printStackTrace();
		}

		return api;
	}

	public Apibean BoostStatusUpdate(String invoiceId) {

		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		Apibean api = new Apibean();
		try {
			// 201-demo // url = new
			// URL("http://localhost:8080/externalapi/paidpayout/send");
			// url = new URL("http://localhost:8074/externalapi/dtl");
//			  url = new URL("http://192.168.11.202:8080/externalapi/boostStatus");
			url = new URL(boostStatusCheck());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("invoiceId", invoiceId);
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			logger.info("The params That passed" + paramss);

			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

			while ((inputLine = br.readLine()) != null) {

				response.append(inputLine);

			}
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			output = response.toString();

			api = gson.fromJson(output, Apibean.class);

			logger.info("1).ResponseCode : " + api.getResponseCode());
			if (api.getResponseData() != null) {
				logger.info("2).ResponseMessage Status : " + api.getResponseData().getStatus());
				logger.info("2.1).ResponseMessage Amount : " + api.getResponseData().getAmount());
			}
			logger.info("3).ResponseDescription : " + api.getResponseMessage());

		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }

			e.printStackTrace();
		}

		return api;
	}

	public Apibean TngAndSppTokenGenerate() {

		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		Apibean api = new Apibean();
		try {
			// 201-demo // url = new
			// URL("http://localhost:8080/externalapi/paidpayout/send");
			// url = new URL("http://localhost:8074/externalapi/dtl");
//			  url = new URL("https://portal.gomobi.io/externalapi/api/v1/wall/Tng-Shopeepay");
			url = new URL(tngAndSppTokenGen());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("service", "REQUEST_TOKEN");
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			logger.info("The params That passed" + paramss);

			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

			while ((inputLine = br.readLine()) != null) {

				response.append(inputLine);

			}
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			output = response.toString();

			api = gson.fromJson(output, Apibean.class);
			logger.info("1).ResponseCode : " + api.getResponseCode());
			logger.info("2).ResponseMessage Token : " + api.getResponseData().getToken());
			logger.info("3).ResponseDescription : " + api.getResponseDescription());
			logger.info("4).responseMessage : " + api.getResponseMessage());

		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
			e.printStackTrace();
		}

		return api;
	}

	public Apibean TngAndSppGetTransactionId(String invoiceid) {

		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		Apibean api = new Apibean();
		try {
			// 201-demo // url = new
			// URL("http://localhost:8080/externalapi/paidpayout/send");
			// url = new URL("http://localhost:8074/externalapi/dtl");
//			  url = new URL("https://portal.gomobi.io/externalapi/api/v1/wall/Tng-Shopeepay");
			url = new URL(tngAndSppFindTxnId());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("service", "REQUEST_TXNID");
			params.put("operationId", invoiceid);
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			logger.info("The params That passed" + paramss);

			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

			while ((inputLine = br.readLine()) != null) {

				response.append(inputLine);

			}
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			output = response.toString();

			api = gson.fromJson(output, Apibean.class);
			logger.info("1).ResponseCode : " + api.getResponseCode());
			logger.info("2).ResponseMessage Txn ID : " + api.getResponseData().getTrxId());
			logger.info("2.1).ResponseMessage Txn Type : " + api.getResponseData().getTxnType());
			logger.info("3).ResponseDescription : " + api.getResponseDescription());
			logger.info("4).responseMessage : " + api.getResponseMessage());

		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
			e.printStackTrace();
		}

		return api;
	}

	public Apibean TngAndSppCheckStatus(String transactionId, Apibean apibean) {

		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		Apibean api = new Apibean();
		try {
			// 201-demo // url = new
			// URL("http://localhost:8080/externalapi/paidpayout/send");
			// url = new URL("http://localhost:8074/externalapi/dtl");
//			  url = new URL("https://portal.gomobi.io/externalapi/api/v1/wall/Tng-Shopeepay");
			url = new URL(tngAndSppCheckStatus());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			logger.info("API Token For TNG & SPP :" + apibean.getResponseData().getToken());
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("token", apibean.getResponseData().getToken());

			JSONObject params = new JSONObject();

			params.put("service", "REQUEST_STATUS");
			params.put("trxId", transactionId);
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			logger.info("The params That passed : " + paramss);

			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

			while ((inputLine = br.readLine()) != null) {

				response.append(inputLine);

			}
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			output = response.toString();

			api = gson.fromJson(output, Apibean.class);
			logger.info("1).ResponseCode : " + api.getResponseCode());
			logger.info("2).ResponseDescription : " + api.getResponseDescription());
			if (api.getResponseData() != null) {
				logger.info("3).ResponseMessage Txn Amount : " + api.getResponseData().getAmount());
				logger.info("3.1).ResponseMessage Txn Type : " + api.getResponseData().getTxnType());
				logger.info("3.2).ResponseMessage Txn Status : " + api.getResponseData().getTransactionStatus());
			}
			logger.info("4).responseMessage : " + api.getResponseMessage());

		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }
			e.printStackTrace();
		}

		return api;
	}

	public void updateDtlDetails2(RegMobileUser regMobileUser) {

		// RegMobileUser regMobileUser = new RegMobileUser();
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;

		try {

			// 201-demo // url = new
			// URL("http://localhost:8080/externalapi/paidpayout/send");
			// url = new URL("http://localhost:8074/externalapi/dtl");
			url = new URL(getDtl());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("Dtl", regMobileUser.getDTL());
			params.put("mid", regMobileUser.getMid());
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			logger.info("The params That passed" + paramss);

			os.flush();

			// ***** RESPONSE ********

//	  con.getDoOutput();
//	  con.getRequestMethod();
//	  con.getRequestProperty("application/json");

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

			while ((inputLine = br.readLine()) != null) {

				response.append(inputLine);

			}
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			output = response.toString();

			Apibean api = new Apibean();
			api = gson.fromJson(output, Apibean.class);
			logger.info("1).ResponseCode : " + api.getResponseCode());
			logger.info("2).ResponseMessage : " + api.getResponseMessage());
			logger.info("3).ResponseDescription : " + api.getResponseDescription());

		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }

			e.printStackTrace();
		}
	}

	public void updateForeigncard(String foreigncard, String Bname) {

		logger.info("$$$$ Call Api $$$$");
		// RegMobileUser regMobileUser = new RegMobileUser();
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;

		try {

			// 201-demo // url = new
			// URL("http://localhost:8080/externalapi/paidpayout/send");
			// url = new URL("http://localhost:8074/externalapi/foreign");
			url = new URL(getForeignCard());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("fcard", foreigncard);
			params.put("userName", Bname);
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			System.out.println("The params That passed" + paramss);

			os.flush();

			StringBuffer response = new StringBuffer();

			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

			while ((inputLine = br.readLine()) != null) {

				response.append(inputLine);

			}

			output = response.toString();

		} catch (Exception e) { // TODO: handle exception e.printStackTrace(); }

			e.printStackTrace();
		}
	}

	// ************* Dtl config prop
	public static String getDtl() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			logger.info("Path :" + prop.getProperty("DTL_UPDATE_URL"));
			path = prop.getProperty("DTL_UPDATE_URL");
			return path;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return path;

	}

	public static String getForeignCard() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			//input = loader.getResourceAsStream("/config.properties");
			input = loader.getResourceAsStream("/config_demo.properties");
			prop.load(input);
			logger.info("Path :" + prop.getProperty("FOREIGN_CARD_UPDATE_URL"));
			path = prop.getProperty("FOREIGN_CARD_UPDATE_URL");
			return path;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return path;

	}

	public static String tngAndSppCheckStatus() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			logger.info("Path :" + prop.getProperty("TNG_SPP_STATUS_CHECK"));
			path = prop.getProperty("TNG_SPP_STATUS_CHECK");
			return path;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return path;

	}

	public static String tngAndSppFindTxnId() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			logger.info("Path :" + prop.getProperty("TNG_SPP_FIND_TXN_ID"));
			path = prop.getProperty("TNG_SPP_FIND_TXN_ID");
			return path;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return path;

	}

	public static String tngAndSppTokenGen() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			logger.info("Path :" + prop.getProperty("TNG_SPP_TOKEN_GENERATE"));
			path = prop.getProperty("TNG_SPP_TOKEN_GENERATE");
			return path;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return path;
	}

	public static String boostStatusCheck() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			logger.info("Path :" + prop.getProperty("BOOST_STATUS_CHECK"));
			path = prop.getProperty("BOOST_STATUS_CHECK");
			return path;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return path;
	}

	public static String fpxStatusCheck() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			logger.info("Path :" + prop.getProperty("FPX_STATUS_CHECK"));
			path = prop.getProperty("FPX_STATUS_CHECK");
			return path;
		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return path;
	}

}
