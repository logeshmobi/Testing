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
import com.mobiversa.payment.util.PayoutData;
import com.mobiversa.payment.util.PropertyLoader;

public class PayoutUpdateApi {
	private static final Logger logger = Logger.getLogger(PayoutUpdateApi.class);
	public static String payoutStatusUpdate() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("payoutStatusUpdate"));
			path = prop.getProperty("updatyeQuery");
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
	
//	public static String getupdateEzysettleAmoutCard() {
//		// To get path
//		Properties prop = new Properties();
//		InputStream input = null;
//		String path = null;
//		try {
//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			input = loader.getResourceAsStream("/config.properties");
//			prop.load(input);
//			System.out.println("Path :" + prop.getProperty("updateEzysettleAmoutCard"));
//			path = prop.getProperty("updateEzysettleAmoutCard");
//			return path;
//		} catch (IOException io) {
//			io.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return path;
//	}

	public static String getupdateEzysettleAmoutCard() {

		Properties prop = new Properties();
		String path = null;
		try (InputStream input = PayoutUpdateApi.class.getResourceAsStream("/config.properties")) {
			if (input == null) {
				logger.info("Unable to find config.properties file");
				return null; // or throw an exception if required
			}

			prop.load(input);
			path = prop.getProperty("updateEzysettleAmoutCard");

			// Logging the path
			logger.info("updateEzysettleAmoutCard Path: " + path);
		} catch (IOException io) {
			io.printStackTrace();
		}
		return path;

		// To get path
//		Properties prop = new Properties();
//		InputStream input = null;
//		String path = null;
//		try {
//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			input = loader.getResourceAsStream("/config.properties");
//			prop.load(input);
//			logger.info("Path :" + prop.getProperty("updateEzysettleAmoutCard"));
//			path = prop.getProperty("updateEzysettleAmoutCard");
//			return path;
//		} catch (IOException io) {
//			io.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}

	}

	public static String getupdateEzysettleStausAndReasonCard() {

//		
//		 Properties properties = new Properties();
//	        String propertyValue = null;
//
//	        try (FileInputStream fis = new FileInputStream("/config.properties")) {
//	            properties.load(fis);
//	            propertyValue = properties.getProperty("updateEzysettleStausAndReasonCard");
//	            return propertyValue;
//	        } catch (IOException e) {
//	        	logger.info("updateEzysettleStausAndReasonCard Exception :"+e.getMessage());
//	            
//	        }
//		
//	        logger.info("updateEzysettleStausAndReasonCard :"+propertyValue);
//	        return propertyValue;
//		
//		

		Properties prop = new Properties();
		String path = null;
		try (InputStream input = PayoutUpdateApi.class.getResourceAsStream("/config.properties")) {
			if (input == null) {
				logger.info("Unable to find config.properties file");
				return null; // or throw an exception if required
			}

			prop.load(input);
			path = prop.getProperty("updateEzysettleStausAndReasonCard");

			// Logging the path
			logger.info("updateEzysettleStausAndReasonCard Path: " + path);
		} catch (IOException io) {
			io.printStackTrace();
		}
		return path;

//		// To get path
//		Properties prop = new Properties();
//		InputStream input = null;
//		String path = null;
//		try {
//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			input = loader.getResourceAsStream("/config.properties");
//			prop.load(input);
//			logger.info("Path :" + prop.getProperty("updateEzysettleStausAndReasonCard"));
//			path = prop.getProperty("updateEzysettleStausAndReasonCard");
//			return path;
//		} catch (IOException io) {
//			io.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return path;
	}

	public static String getupdateEzysettleAmoutBoost() {
		Properties prop = new Properties();
		String path = null;
		try (InputStream input = PayoutUpdateApi.class.getResourceAsStream("/config.properties")) {
			if (input == null) {
				logger.info("Unable to find config.properties file");
				return null; // or throw an exception if required
			}

			prop.load(input);
			path = prop.getProperty("updateEzysettleAmoutBoost");

			// Logging the path
			logger.info("updateEzysettleAmoutBoost Path: " + path);
		} catch (IOException io) {
			io.printStackTrace();
		}
		return path;

//		// To get path
//		Properties prop = new Properties();
//		InputStream input = null;
//		String path = null;
//		try {
//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			input = loader.getResourceAsStream("/config.properties");
//			prop.load(input);
//			logger.info("Path :" + prop.getProperty("updateEzysettleAmoutBoost"));
//			path = prop.getProperty("updateEzysettleAmoutBoost");
//			return path;
//		} catch (IOException io) {
//			io.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return path;
	}

	public static String getupdateEzysettleStausAndReasonBoost() {

		Properties prop = new Properties();
		String path = null;
		try (InputStream input = PayoutUpdateApi.class.getResourceAsStream("/config.properties")) {
			if (input == null) {
				logger.info("Unable to find config.properties file");
				return null; // or throw an exception if required
			}

			prop.load(input);
			path = prop.getProperty("updateEzysettleStausAndReasonBoost");

			// Logging the path
			logger.info("updateEzysettleStausAndReasonBoost Path: " + path);
		} catch (IOException io) {
			io.printStackTrace();
		}
		return path;

//		// To get path
//		Properties prop = new Properties();
//		InputStream input = null;
//		String path = null;
//		try {
//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			input = loader.getResourceAsStream("/config.properties");
//			prop.load(input);
//			logger.info("Path :" + prop.getProperty("updateEzysettleStausAndReasonBoost"));
//			path = prop.getProperty("updateEzysettleStausAndReasonBoost");
//			return path;
//		} catch (IOException io) {
//			io.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return path;
	}

	public static String getupdateEzysettleAmoutGrabpay() {

		Properties prop = new Properties();
		String path = null;
		try (InputStream input = PayoutUpdateApi.class.getResourceAsStream("/config.properties")) {
			if (input == null) {
				logger.info("Unable to find config.properties file");
				return null; // or throw an exception if required
			}

			prop.load(input);
			path = prop.getProperty("updateEzysettleAmoutGrabpay");

			// Logging the path
			logger.info("updateEzysettleAmoutGrabpay Path: " + path);
		} catch (IOException io) {
			io.printStackTrace();
		}
		return path;

//		// To get path
//		Properties prop = new Properties();
//		InputStream input = null;
//		String path = null;
//		try {
//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			input = loader.getResourceAsStream("/config.properties");
//			prop.load(input);
//			logger.info("Path :" + prop.getProperty("updateEzysettleAmoutGrabpay"));
//			path = prop.getProperty("updateEzysettleAmoutGrabpay");
//			return path;
//		} catch (IOException io) {
//			io.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return path;
	}

	public static String getupdateEzysettleStausAndReasonGrabpay() {

		Properties prop = new Properties();
		String path = null;
		try (InputStream input = PayoutUpdateApi.class.getResourceAsStream("/config.properties")) {
			if (input == null) {
				logger.info("Unable to find config.properties file");
				return null; // or throw an exception if required
			}

			prop.load(input);
			path = prop.getProperty("updateEzysettleStausAndReasonGrabpay");

			// Logging the path
			logger.info("updateEzysettleStausAndReasonGrabpay Path: " + path);
		} catch (IOException io) {
			io.printStackTrace();
		}
		return path;

//		
//		// To get path
//		Properties prop = new Properties();
//		InputStream input = null;
//		String path = null;
//		try {
//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			input = loader.getResourceAsStream("/config.properties");
//			prop.load(input);
//			logger.info("Path :" + prop.getProperty("updateEzysettleStausAndReasonGrabpay"));
//			path = prop.getProperty("updateEzysettleStausAndReasonGrabpay");
//			return path;
//		} catch (IOException io) {
//			io.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return path;
	}

	public static String getupdateEzysettleAmoutFpx() {

		Properties prop = new Properties();
		String path = null;
		try (InputStream input = PayoutUpdateApi.class.getResourceAsStream("/config.properties")) {
			if (input == null) {
				logger.info("Unable to find config.properties file");
				return null; // or throw an exception if required
			}

			prop.load(input);
			path = prop.getProperty("updateEzysettleAmoutFpx");

			// Logging the path
			logger.info("updateEzysettleAmoutFpx Path: " + path);
		} catch (IOException io) {
			io.printStackTrace();
		}
		return path;

//		
//		// To get path
//		Properties prop = new Properties();
//		InputStream input = null;
//		String path = null;
//		try {
//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			input = loader.getResourceAsStream("/config.properties");
//			prop.load(input);
//			logger.info("Path :" + prop.getProperty("updateEzysettleAmoutFpx"));
//			path = prop.getProperty("updateEzysettleAmoutFpx");
//			return path;
//		} catch (IOException io) {
//			io.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return path;
	}

	public static String getupdateEzysettleStausAndReasonFpx() {

		Properties prop = new Properties();
		String path = null;
		try (InputStream input = PayoutUpdateApi.class.getResourceAsStream("/config.properties")) {
			if (input == null) {
				logger.info("Unable to find config.properties file");
				return null; // or throw an exception if required
			}

			prop.load(input);
			path = prop.getProperty("updateEzysettleStausAndReasonFpx");

			// Logging the path
			logger.info("updateEzysettleStausAndReasonFpx Path: " + path);
		} catch (IOException io) {
			io.printStackTrace();
		}
		return path;

//		// To get path
//		Properties prop = new Properties();
//		InputStream input = null;
//		String path = null;
//		try {
//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			input = loader.getResourceAsStream("/config.properties");
//			prop.load(input);
//			logger.info("Path :" + prop.getProperty("updateEzysettleStausAndReasonFpx"));
//			path = prop.getProperty("updateEzysettleStausAndReasonFpx");
//			return path;
//		} catch (IOException io) {
//			io.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return path;
	}

	public static String getupdateEzysettleAmoutm1pay() {

		Properties prop = new Properties();
		String path = null;
		try (InputStream input = PayoutUpdateApi.class.getResourceAsStream("/config.properties")) {
			if (input == null) {
				logger.info("Unable to find config.properties file");
				return null; // or throw an exception if required
			}

			prop.load(input);
			path = prop.getProperty("updateEzysettleAmoutm1pay");

			// Logging the path
			logger.info("updateEzysettleAmoutm1pay Path: " + path);
		} catch (IOException io) {
			io.printStackTrace();
		}
		return path;

//		// To get path
//		Properties prop = new Properties();
//		InputStream input = null;
//		String path = null;
//		try {
//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			input = loader.getResourceAsStream("/config.properties");
//			prop.load(input);
//			logger.info("Path :" + prop.getProperty("updateEzysettleAmoutm1pay"));
//			path = prop.getProperty("updateEzysettleAmoutm1pay");
//			return path;
//		} catch (IOException io) {
//			io.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return path;
	}

	public static String getupdateEzysettleStausAndReasonm1pay() {

		Properties prop = new Properties();
		String path = null;
		try (InputStream input = PayoutUpdateApi.class.getResourceAsStream("/config.properties")) {
			if (input == null) {
				logger.info("Unable to find config.properties file");
				return null;
			}
			prop.load(input);
			path = prop.getProperty("updateEzysettleStausAndReasonm1pay");
			logger.info("updateEzysettleStausAndReasonm1pay Path: " + path);
		} catch (Exception e) {
			logger.info("Exception updateEzysettleStausAndReasonm1pay Path: " + e.getMessage());
		}
		
		return path;
		
//		// To get path
//		Properties prop = new Properties();
//		InputStream input = null;
//		String path = null;
//		try {
//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			input = loader.getResourceAsStream("/config.properties");
//			prop.load(input);
//			logger.info("Path :" + prop.getProperty("updateEzysettleStausAndReasonm1pay"));
//			path = prop.getProperty("updateEzysettleStausAndReasonm1pay");
//			return path;
//		} catch (IOException io) {
//			io.printStackTrace();
//		} finally {
//			if (input != null) {
//				try {
//					input.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return path;
	}
	@SuppressWarnings("nls")
	public PayoutData updatyeQuery(PayoutBean message) {
		PayoutData var=null;
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		
		try {
			

			// 201-demo
//			url = new URL("http://localhost:8080/externalapi/paidpayout/send");
			url = new URL(payoutStatusUpdate());
		

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("invoiceIdProof", message.getInvoiceIdProof());
			params.put("paidDate", message.getPaidDate());
			params.put("paidTime", message.getPaidTime());
			params.put("payoutStatus", message.getPayoutStatus());
			params.put("declineReason", message.getDeclineReason());
			
			
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			System.out.println("The params That passed" + paramss);
			System.out.println("DeclineReason()"+message.getDeclineReason());
			os.flush();
			
			StringBuffer response = new StringBuffer();
			
			

			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine = br.readLine()) != null) {
				
				response.append(inputLine);
				
			}
			
			output = response.toString();
			//new
			System.out.println("output     : "+output);
			Gson gson = new Gson();
			 var = gson.fromJson(output, PayoutData.class);
			var.setResponseCode(var.getResponseCode());
			System.out.println("final o/p :"+var.getResponseCode());


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return var;

	}
	public JSONObject updateEzysettleAmoutCard(PayoutSettleBean message) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		
		try {
			

			// 201-demo
//			url = new URL("http://localhost:8080/externalapi/paidpayout/send");
//			url = new URL(getupdateEzysettleAmoutCard());
			url = new URL(PropertyLoader.getFileData().getProperty("updateEzysettleAmoutCard"));
			logger.info("updateEzysettleAmoutCard : "+PropertyLoader.getFileData().getProperty("updateEzysettleAmoutCard"));

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("umMid", message.getUmMid());
			params.put("umEzywayMid", message.getUmEzywayMid());
			params.put("umMotoMid", message.getUmMotoMid());
			params.put("umEzyrecMid", message.getUmEzyrecMid());
			params.put("umEzypassMid", message.getUmEzypassMid());
			params.put("splitMid", "5678876565678");
			params.put("netAmt", message.getNetAmt());
			params.put("ezyAmt", message.getEzyAmt());
			params.put("formatsettledate", message.getFormatsettledate());

			
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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}
	public JSONObject updateEzysettleStausAndReasonCard(PayoutSettleBean message) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		
		try {
			

			// 201-demo
//			url = new URL("http://localhost:8080/externalapi/paidpayout/send");
//			url = new URL(getupdateEzysettleStausAndReasonCard());
			url = new URL(PropertyLoader.getFileData().getProperty("updateEzysettleStausAndReasonCard"));
			logger.info("updateEzysettleStausAndReasonCard : "+PropertyLoader.getFileData().getProperty("updateEzysettleStausAndReasonCard"));

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("umMid", message.getUmMid());
			params.put("umEzywayMid", message.getUmEzywayMid());
			params.put("umMotoMid", message.getUmMotoMid());
			params.put("umEzyrecMid", message.getUmEzyrecMid());
			params.put("umEzypassMid", message.getUmEzypassMid());
			params.put("splitMid", message.getSplitMid());
			params.put("formatsettledate", message.getFormatsettledate());
			
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
			
			output = response.toString();

		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return paramss;

	}
	public JSONObject updateEzysettleAmoutBoost(PayoutSettleBean message) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		
		try {
			

			// 201-demo
//			url = new URL("http://localhost:8080/externalapi/paidpayout/send");
//			url = new URL(getupdateEzysettleAmoutBoost());
			url = new URL(PropertyLoader.getFileData().getProperty("updateEzysettleAmoutBoost"));
			logger.info("updateEzysettleAmoutBoost : "+PropertyLoader.getFileData().getProperty("updateEzysettleAmoutBoost"));


			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("umMid", message.getUmMid());
			params.put("umEzywayMid", message.getUmEzywayMid());
			params.put("umMotoMid", message.getUmMotoMid());
			params.put("umEzyrecMid", message.getUmEzyrecMid());
			params.put("umEzypassMid", message.getUmEzypassMid());
			params.put("splitMid", message.getSplitMid());
			params.put("boostmid", message.getBoostmid());
			params.put("netAmt", message.getNetAmt());
			params.put("ezyAmt", message.getEzyAmt());
			params.put("formatsettledate", message.getFormatsettledate());
			
			/*
			 * query.setString("umMid", umMid); 
			 * query.setString("umEzywayMid", umEzywayMid);
			 * query.setString("umMotoMid", umMotoMid);
			 *  query.setString("umEzyrecMid", umEzyrecMid); 
			 *  query.setString("umEzypassMid", umEzypassMid);
			 * query.setString("splitMid", splitMid);
			 */
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			System.out.println("The params That passed" + paramss);
			System.out.println("updateEzysettleAmoutBoost");
			os.flush();
			
			StringBuffer response = new StringBuffer();
			
			
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			
			while ((inputLine = br.readLine()) != null) {
				
				response.append(inputLine);
				
			}
			
			output = response.toString();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}
	public JSONObject updateEzysettleStausAndReasonBoost(PayoutSettleBean message) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		
		try {
			

			// 201-demo
//			url = new URL("http://localhost:8080/externalapi/paidpayout/send");
//			url = new URL(getupdateEzysettleStausAndReasonBoost());
			url = new URL(PropertyLoader.getFileData().getProperty("updateEzysettleStausAndReasonBoost"));
			logger.info("updateEzysettleStausAndReasonBoost : "+PropertyLoader.getFileData().getProperty("updateEzysettleStausAndReasonBoost"));


			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("umMid", message.getUmMid());
			params.put("umEzywayMid", message.getUmEzywayMid());
			params.put("umMotoMid", message.getUmMotoMid());
			params.put("umEzyrecMid", message.getUmEzyrecMid());
			params.put("umEzypassMid", message.getUmEzypassMid());
			params.put("splitMid", message.getSplitMid());
			params.put("boostmid", message.getBoostmid());
			params.put("formatsettledate", message.getFormatsettledate());
			/*
			 * query.setString("umMid", umMid); 
			 * query.setString("umEzywayMid", umEzywayMid);
			 * query.setString("umMotoMid", umMotoMid);
			 *  query.setString("umEzyrecMid", umEzyrecMid); 
			 *  query.setString("umEzypassMid", umEzypassMid);
			 * query.setString("splitMid", splitMid);
			 */
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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}
	public JSONObject updateEzysettleAmoutGrabpay(PayoutSettleBean message) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		System.out.println("Grab  Amount On API calling");
		try {
			

			// 201-demo
//			url = new URL("http://localhost:8080/externalapi/paidpayout/send");
//			url = new URL(getupdateEzysettleAmoutGrabpay());
			url = new URL(PropertyLoader.getFileData().getProperty("updateEzysettleAmoutGrabpay"));
			logger.info("updateEzysettleAmoutGrabpay : "+PropertyLoader.getFileData().getProperty("updateEzysettleAmoutGrabpay"));


			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("currentMerchantid", message.getCurrentMerchantid());
			params.put("netAmt", message.getNetAmt());
			params.put("ezyAmt", message.getEzyAmt());
			params.put("formatsettledate", message.getFormatsettledate());
			
			
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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}
	public JSONObject updateEzysettleStausAndReasonGrabpay(PayoutSettleBean message) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		
		try {
			

			// 201-demo
//			url = new URL("http://localhost:8080/externalapi/paidpayout/send");
//			url = new URL(getupdateEzysettleStausAndReasonGrabpay());
			url = new URL(PropertyLoader.getFileData().getProperty("updateEzysettleStausAndReasonGrabpay"));
			logger.info("updateEzysettleStausAndReasonGrabpay : "+PropertyLoader.getFileData().getProperty("updateEzysettleStausAndReasonGrabpay"));


			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();
			System.out.println("Grab Reason:::");
			params.put("formatsettledate", message.getFormatsettledate());
			params.put("currentMerchantid", message.getCurrentMerchantid());
			params.put("TxnFormatDate", message.getTxnFormatDate());
			params.put("TxnDate", message.getTxnDate());
		
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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}
	
	public JSONObject updateEzysettleAmoutFpx(PayoutSettleBean message) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		System.out.println("Update FPX On API calling");
		try {
			

			// 201-demo
//			url = new URL("http://localhost:8080/externalapi/paidpayout/send");
//			url = new URL(getupdateEzysettleAmoutFpx());
			url = new URL(PropertyLoader.getFileData().getProperty("updateEzysettleAmoutFpx"));
			logger.info("updateEzysettleAmoutFpx : "+PropertyLoader.getFileData().getProperty("updateEzysettleAmoutFpx"));


			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("umMid", message.getUmMid());
			params.put("umEzywayMid", message.getUmEzywayMid());
			params.put("umMotoMid", message.getUmMotoMid());
			params.put("umEzyrecMid", message.getUmEzyrecMid());
			params.put("umEzypassMid", message.getUmEzypassMid());
			params.put("splitMid", message.getSplitMid());
			params.put("fpxmid", message.getFpxmid());
			params.put("settledate", message.getSettledate());
			params.put("netAmt", message.getNetAmt());
			params.put("ezyAmt", message.getEzyAmt());
			System.out.println("fpxmid  : "+ message.getFpxmid());

			
//			query.setString("umMid", umMid);
//			query.setString("umEzywayMid", umEzywayMid);
//			query.setString("umMotoMid", umMotoMid);
//			query.setString("umEzyrecMid", umEzyrecMid);
//			query.setString("umEzypassMid", umEzypassMid);
//			query.setString("splitMid", splitMid);
//			query.setString("fpxmid", fpxmid);
//			query.setString("settledate", settledate);
//			query.setString("netAmt", netAmt);
//			query.setString("ezyAmt", ezyAmt);
			
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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}
	public JSONObject updateEzysettleStausAndReasonFpx(PayoutSettleBean message) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		
		try {
			

			// 201-demo
//			url = new URL("http://localhost:8080/externalapi/paidpayout/send");
//			url = new URL(getupdateEzysettleStausAndReasonFpx());
			url = new URL(PropertyLoader.getFileData().getProperty("updateEzysettleStausAndReasonFpx"));
			logger.info("updateEzysettleStausAndReasonFpx : "+PropertyLoader.getFileData().getProperty("updateEzysettleStausAndReasonFpx"));


			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("umMid", message.getUmMid());
			params.put("umEzywayMid", message.getUmEzywayMid());
			params.put("umMotoMid", message.getUmMotoMid());
			params.put("umEzyrecMid", message.getUmEzyrecMid());
			params.put("umEzypassMid", message.getUmEzypassMid());
			params.put("splitMid", message.getSplitMid());
			
			params.put("fpxmid", message.getFpxmid());
			params.put("settledate", message.getSettledate());
			params.put("TxnFormatDate", message.getTxnFormatDate());
			params.put("TxnDate", message.getTxnDate());


			
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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}
	
	public JSONObject updateEzysettleAmoutm1pay(PayoutSettleBean message) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		
		try {
			

			// 201-demo
//			url = new URL("http://localhost:8080/externalapi/paidpayout/send");
			url = new URL(PropertyLoader.getFileData().getProperty("updateEzysettleAmoutm1pay"));
			logger.info("updateEzysettleAmoutm1pay : "+PropertyLoader.getFileData().getProperty("updateEzysettleAmoutm1pay"));


			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("tngMid", message.getTngMid());
			params.put("shoppyMid", message.getShoppyMid());
			params.put("formatsettledate", message.getFormatsettledate());
			params.put("netAmt", message.getNetAmt());
			params.put("ezyAmt", message.getEzyAmt());
			params.put("umEzywayMid", message.getUmEzywayMid());
			params.put("umMotoMid", message.getUmMotoMid());
			

		
			
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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}
	public JSONObject updateEzysettleStausAndReasonm1pay(PayoutSettleBean message) {
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		
		try {
			

			// 201-demo
//			url = new URL("http://localhost:8080/externalapi/paidpayout/send");
//			url = new URL(getupdateEzysettleStausAndReasonm1pay());
			url = new URL(PropertyLoader.getFileData().getProperty("updateEzysettleStausAndReasonm1pay"));
			logger.info("updateEzysettleStausAndReasonm1pay : "+PropertyLoader.getFileData().getProperty("updateEzysettleStausAndReasonm1pay"));

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			JSONObject params = new JSONObject();

			params.put("tngMid", message.getTngMid());
			params.put("shoppyMid", message.getShoppyMid());
			params.put("formatsettledate", message.getFormatsettledate());
			params.put("umEzywayMid", message.getUmEzywayMid());
			params.put("umMotoMid", message.getUmMotoMid());
	
			
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

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return paramss;

	}
//	query.setLong("currentMerchantid", currentMerchantid);
//	query.setString("netAmt", netAmt);
//	query.setString("ezyAmt", ezyAmt);
}
