package com.mobiversa.payment.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Properties;

import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mobiversa.common.bo.PreAuthorization;
import com.mobiversa.common.bo.UMEcomTxnRequest;
import com.mobiversa.common.bo.UMEcomTxnResponse;
import com.mobiversa.payment.dto.MotoTxnDet;
import com.mobiversa.payment.dto.PreAuthTxnDet;
import com.mobiversa.payment.dto.Request;
import com.mobiversa.payment.util.FileGenerate;
import com.mobiversa.payment.util.PreauthModel;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.ResponseDetails;

public class MotoPaymentCommunication {
	private static Logger logger = Logger.getLogger(MotoPaymentCommunication.class);

	public static String getPaymentPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("PaymentURL"));
			path = prop.getProperty("PaymentURL");
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
//rk added
	public static String getpaydeepaymentPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("PaydeePaymentURL"));
			path = prop.getProperty("PaydeePaymentURL");
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
	
	public static String getpaydeeauthviamotopaymentPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("PaydeeauthviamotoPaymentURL"));
			path = prop.getProperty("PaydeeauthviamotoPaymentURL");
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
	
	
	public static String getumobileauthviamotopaymentPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
	//		input = loader.getResourceAsStream("/config_demo.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("UmobileAuthViaMotoPaymentURL"));
			path = prop.getProperty("UmobileAuthViaMotoPaymentURL");
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
	
	
	
	
	
	public static String getumobilepaymentPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			//input = loader.getResourceAsStream("/config_demo.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("UmobilePaymentURL"));
			path = prop.getProperty("UmobilePaymentURL");
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
	
	
	//rk added end

	public static String getFilePath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
		//	input = loader.getResourceAsStream("/config_demo.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("VoidPaymentURL"));
			path = prop.getProperty("VoidPaymentURL");
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

	
	public static String getauthDDPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("AuthDDURL"));
			path = prop.getProperty("AuthDDURL");
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

	public static String getUMAuthDDPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("UM_AuthDDURL"));
			path = prop.getProperty("UM_AuthDDURL");
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

	public static String getUMAuthPath(String service) {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			if (service.equalsIgnoreCase("Sale")) {
				path = prop.getProperty("UM_Auth_Sale_URL");
			} else {
				path = prop.getProperty("UM_Auth_Void_URL");
			}

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

	public static String getUMAuthRevPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			path = prop.getProperty("UM_Auth_Sale_URL");

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

	public static String getUMVoidPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
		//	input = loader.getResourceAsStream("/config_demo.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("UM_Void_URL"));
			path = prop.getProperty("UM_Void_URL");
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
	
	//BNPL VOID
	
	public static String getBnplVoidPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("BNPL_Void_URL"));
			path = prop.getProperty("BNPL_Void_URL");
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

	public static String getUMEzyrecVoidPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("UMEZYREC_Void_URL"));
			path = prop.getProperty("UMEZYREC_Void_URL");
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

	public static String getFileRegenrate() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("File_Regenerate"));
			path = prop.getProperty("File_Regenerate");
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

	public static String getMotoVCPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("MOTO_VC_URL"));
			path = prop.getProperty("MOTO_VC_URL");
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

	public static String getUMRefundPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("UM_Refund_URL"));
			path = prop.getProperty("UM_Refund_URL");
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

	public static ResponseDetails motosubmitTrans(MotoTxnDet motoTrans) {
		ResponseDetails rd = new ResponseDetails();
		logger.info("filepath " + getFilePath());
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		try {

			logger.info("Watsapp In: " + motoTrans.getWhatsapp());

			logger.info(getFilePath());
			url = new URL(getFilePath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("mid", motoTrans.getMotoMid());
			jsonParams.put("tid", motoTrans.getTid());
			jsonParams.put("amount", motoTrans.getAmount());
			jsonParams.put("invoiceId", motoTrans.getReferrence());
			jsonParams.put("service", "MOTO_TXN_REQ");
			jsonParams.put("contactName", motoTrans.getContactName());
			jsonParams.put("longitude", motoTrans.getLongitude());
			jsonParams.put("latitude", motoTrans.getLatitude());
			jsonParams.put("mobileNo", motoTrans.getPhno());
			jsonParams.put("email", motoTrans.getEmail());
			jsonParams.put("expectedDate", motoTrans.getExpectedDate());
			jsonParams.put("reqMode", "WEB");
			jsonParams.put("hostType", motoTrans.getHostType());
			jsonParams.put("whatsApp", motoTrans.getWhatsapp());
			jsonParams.put("sessionId","aSegXQk1XhjQjkgqdlzGUTxa7HzY3yhNbE5FqBu22");
			if (motoTrans.getMultiOption() != null) {
				jsonParams.put("multiOption ", motoTrans.getMultiOption());
			}

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			JSONObject dataObj = new JSONObject(output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			rd.setResponseCode("0001");
			rd.setResponseMessage(e.getMessage());
			logger.info("exception: " + e.getMessage());
			// e.printStackTrace();
		} finally {

		}

		return rd;

	}
	
	// rk added
	
	public static ResponseDetails paydeemotopayment(MotoTxnDet motoTrans) {
		ResponseDetails rd = new ResponseDetails();
		logger.info("filepath " + getpaydeepaymentPath());
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		try {

			logger.info("Watsapp In: " + motoTrans.getWhatsapp());

			logger.info(getpaydeepaymentPath());
			url = new URL(getpaydeepaymentPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();
			
			
			jsonParams.put("service", "PORTAL_MOTO_SALE_REQ");
			jsonParams.put("mid", motoTrans.getMotoMid());
			jsonParams.put("tid", motoTrans.getTid());
			jsonParams.put("amount", motoTrans.getAmount());
			jsonParams.put("invoiceId", motoTrans.getReferrence());
			jsonParams.put("carddetails", motoTrans.getCardDetails());
			jsonParams.put("nameOnCard", motoTrans.getNameoncard());

			

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			JSONObject dataObj = new JSONObject(output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			rd.setResponseCode("0001");
			rd.setResponseMessage(e.getMessage());
			logger.info("exception: " + e.getMessage());
			// e.printStackTrace();
		} finally {

		}

		return rd;

	}
	
	
	public static ResponseDetails paydeeauthviamotopayment(MotoTxnDet motoTrans) {
		ResponseDetails rd = new ResponseDetails();
		logger.info("filepath " + getpaydeeauthviamotopaymentPath());
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		try {

			logger.info("Watsapp In: " + motoTrans.getWhatsapp());

			logger.info(getpaydeeauthviamotopaymentPath());
			url = new URL(getpaydeeauthviamotopaymentPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();
			
			
			jsonParams.put("service", "PORTAL_PREAUTH_TXN_REQ");
			jsonParams.put("mid", motoTrans.getMotoMid());
			jsonParams.put("tid", motoTrans.getTid());
			jsonParams.put("amount", motoTrans.getAmount());
			jsonParams.put("invoiceId", motoTrans.getReferrence());
			jsonParams.put("carddetails", motoTrans.getCardDetails());
			jsonParams.put("nameOnCard", motoTrans.getNameoncard());

			

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			JSONObject dataObj = new JSONObject(output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			rd.setResponseCode("0001");
			rd.setResponseMessage(e.getMessage());
			logger.info("exception: " + e.getMessage());
			// e.printStackTrace();
		} finally {

		}

		return rd;

	}
	
	
	
	public static ResponseDetails umobileauthviamotopayment(MotoTxnDet motoTrans) {
		ResponseDetails rd = new ResponseDetails();
		logger.info("filepath " + getumobileauthviamotopaymentPath());
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		try {

			logger.info("Watsapp In: " + motoTrans.getWhatsapp());

			logger.info(getumobileauthviamotopaymentPath());
			url = new URL(getumobileauthviamotopaymentPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("mid", motoTrans.getMotoMid());
			jsonParams.put("tid", motoTrans.getTid());
			jsonParams.put("amount", motoTrans.getAmount());
			jsonParams.put("invoiceId", motoTrans.getReferrence());
			jsonParams.put("nameOnCard", motoTrans.getNameoncard());
			jsonParams.put("carddetails", motoTrans.getCardDetails());

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			JSONObject dataObj = new JSONObject(output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			rd.setResponseCode("0001");
			rd.setResponseMessage(e.getMessage());
			logger.info("exception: " + e.getMessage());
			// e.printStackTrace();
		} finally {

		}

		return rd;

	}
	
	
	
	public static ResponseDetails umobilemotopayment(MotoTxnDet motoTrans) {
		ResponseDetails rd = new ResponseDetails();
		logger.info("filepath " + getumobilepaymentPath());
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		try {

			logger.info("Watsapp In: " + motoTrans.getWhatsapp());

			logger.info(getumobilepaymentPath());
			url = new URL(getumobilepaymentPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("mid", motoTrans.getMotoMid());
			jsonParams.put("tid", motoTrans.getTid());
			jsonParams.put("amount", motoTrans.getAmount());
			jsonParams.put("reference", motoTrans.getReferrence());
			jsonParams.put("cardHolderName", motoTrans.getNameoncard());
			jsonParams.put("carddetails", motoTrans.getCardDetails());

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			JSONObject dataObj = new JSONObject(output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			rd.setResponseCode("0001");
			rd.setResponseMessage(e.getMessage());
			logger.info("exception: " + e.getMessage());
			// e.printStackTrace();
		} finally {

		}

		return rd;

	}
	
	// rk ended
	
	
	
	
	
	
	
	
	
	
	
	

	public static ResponseDetails preAuthsubmitTrans(PreAuthTxnDet preAuthTxn) {
		ResponseDetails rd = null;
		logger.info("filepath " + getFilePath());
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(getFilePath());
			url = new URL(getFilePath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("mid", preAuthTxn.getMotoMid());
			jsonParams.put("tid", preAuthTxn.getTid());
			jsonParams.put("amount", preAuthTxn.getAmount());
			jsonParams.put("invoiceId", preAuthTxn.getReferrence());
			jsonParams.put("service", "MOTO_PREAUTH_TXN_REQ");
			jsonParams.put("contactName", preAuthTxn.getContactName());
			jsonParams.put("longitude", preAuthTxn.getLongitude());
			jsonParams.put("latitude", preAuthTxn.getLatitude());
			jsonParams.put("mobileNo", preAuthTxn.getPhno());
			jsonParams.put("email", preAuthTxn.getEmail());
			jsonParams.put("expectedDate", preAuthTxn.getExpectedDate());
			jsonParams.put("hostType", preAuthTxn.getHostType());
			jsonParams.put("reqMode", "WEB");

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			JSONObject dataObj = new JSONObject(output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return rd;

	}

	public static ResponseDetails CancelPayment(MotoTxnDet motoTxnDet) {
		ResponseDetails rd = null;
		logger.info("filepath " + getFilePath());
		Gson gson = new Gson();
		String error = "{\"responseCode\":\"0001\",\"responseMessage\":\"Failure\",\"responseDescription\":\"Void Transaction Failed\"}";
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(getFilePath());
			url = new URL(getFilePath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("tid", motoTxnDet.getTid());
			jsonParams.put("sessionId", PropertyLoad.getFile().getProperty("EZYWAYVOID_SESSIONID"));
			jsonParams.put("trxId", motoTxnDet.getTrxId());
			jsonParams.put("service", "VOID");
			jsonParams.put("merchantId", motoTxnDet.getMerchantId());
			jsonParams.put("hostType", motoTxnDet.getHostType());

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			// JSONObject dataObj = new JSONObject(output);
			try {
				rd = gson.fromJson(output, ResponseDetails.class);
			} catch (JsonSyntaxException e) {
				logger.info(" JsonSyntaxException .... " + output);
				rd = gson.fromJson(error, ResponseDetails.class);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * finally{
		 * 
		 * }
		 */

		return rd;

	}
	
	//VOID BNPL 13-12-22
	
	public static ResponseDetails CancelPaymentBnpl(MotoTxnDet motoTxnDet) {
		ResponseDetails rd = null;
		logger.info("filepath " + getBnplVoidPath());
		Gson gson = new Gson();
		String error = "{\"responseCode\":\"0001\",\"responseMessage\":\"Failure\",\"responseDescription\":\"Void Transaction Failed\"}";
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(getBnplVoidPath());
			url = new URL(getBnplVoidPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();
			
			jsonParams.put("mid",motoTxnDet.getMid());
			jsonParams.put("tid",motoTxnDet.getTid());
			jsonParams.put("invoiceId",motoTxnDet.getInvoiceId());
			jsonParams.put("sessionId","02A3B9C8F19606452F57477487FC3007");
			jsonParams.put("service","BNPL_REFUND_AND_VOID");
			jsonParams.put("mobiTxnId",motoTxnDet.getTrxId());

			/*
			 * jsonParams.put("tid", motoTxnDet.getTid()); jsonParams.put("sessionId",
			 * PropertyLoad.getFile().getProperty("EZYWAYVOID_SESSIONID"));
			 * jsonParams.put("trxId", motoTxnDet.getTrxId()); jsonParams.put("service",
			 * "VOID"); jsonParams.put("merchantId", motoTxnDet.getMerchantId());
			 * jsonParams.put("hostType", motoTxnDet.getHostType());
			 */
			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			// JSONObject dataObj = new JSONObject(output);
			try {
				rd = gson.fromJson(output, ResponseDetails.class);
			} catch (JsonSyntaxException e) {
				logger.info(" JsonSyntaxException .... " + output);
				rd = gson.fromJson(error, ResponseDetails.class);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * finally{
		 * 
		 * }
		 */

		return rd;

	}

	public static ResponseDetails UmCancelPayment(UMEcomTxnRequest TxnDet) {
		ResponseDetails rd = null;
		logger.info("UM Void path " + getUMVoidPath());
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(getUMVoidPath());
			url = new URL(getUMVoidPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			StringEntity jsonEntity = null;

			/*
			 * JSONObject jsonParams = new JSONObject(); jsonParams.put("f263_MRN",
			 * TxnDet.getF263_MRN()); logger.info("json: "+jsonParams.toString());
			 */

			OutputStream os = conn.getOutputStream();
			/* os.write(jsonParams.toString().getBytes()); */

			String mrn = "f263_MRN=" + TxnDet.getF263_MRN();

			logger.info("mrn to void" + mrn);
//				String mrn ="f263_MRN=";
//				String mrn ="f263_MRN="+"2018102200000000000928910003278050201";

			os.write(mrn.getBytes());

			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			JSONObject dataObj = new JSONObject(output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return rd;

	}
	
	
	public static ResponseDetails UmEzyrecCancelPayment(UMEcomTxnRequest TxnDet) {
		ResponseDetails rd = null;
		logger.info("getUMEzyrecVoidPath: " + getUMEzyrecVoidPath());
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(getUMEzyrecVoidPath());
			url = new URL(getUMEzyrecVoidPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			StringEntity jsonEntity = null;

			/*
			 * JSONObject jsonParams = new JSONObject(); jsonParams.put("f263_MRN",
			 * TxnDet.getF263_MRN()); logger.info("json: "+jsonParams.toString());
			 */

			OutputStream os = conn.getOutputStream();
			/* os.write(jsonParams.toString().getBytes()); */

			String mrn = "f263_MRN=" + TxnDet.getF263_MRN();

			logger.info("mrn to void" + mrn);
//				String mrn ="f263_MRN=";
//				String mrn ="f263_MRN="+"2018102200000000000928910003278050201";

			os.write(mrn.getBytes());

			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			JSONObject dataObj = new JSONObject(output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return rd;

	}

	public static ResponseDetails FileRegenerate(FileGenerate TxnDet) {
		ResponseDetails rd = null;
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		String error = "{\"responseCode\":\"0001\",\"responseMessage\":\"Failure\",\"responseDescription\":\"File Regeneration Failed\"}";
		URL url;
		try {
			logger.info("File Regeneration URL " + getFileRegenrate());
			url = new URL(getFileRegenrate());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("service", "REGEN_FILE");
			jsonParams.put("mid", TxnDet.getMid());
			jsonParams.put("date", TxnDet.getDate());
			jsonParams.put("merchantFile", TxnDet.getMerchantFile());
			jsonParams.put("mdrFile", TxnDet.getMdrFile());
			jsonParams.put("deductionFile", TxnDet.getDeductionFile());
			jsonParams.put("settleDay", TxnDet.getSettleType());
			jsonParams.put("csvFile", TxnDet.getCsvFile());
//				jsonParams.put("mailTo", TxnDet.getMailTo());
//				jsonParams.put("mailCC", TxnDet.getMailCC());

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			// JSONObject dataObj = new JSONObject(output);
			try {
				rd = gson.fromJson(output, ResponseDetails.class);
			} catch (JsonSyntaxException e) {
				logger.info(" JsonSyntaxException .... " + output);

				rd = gson.fromJson(error, ResponseDetails.class);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * finally{
		 * 
		 * }
		 */

		return rd;

	}

	public static ResponseDetails UmAuthReverse(UMEcomTxnRequest TxnDet) {
		ResponseDetails rd = null;
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		String Path = null;
		logger.info("Auth Service :  " + TxnDet.getStatus());

		try {

			logger.info("Auth Reverse Path :  " + getUMAuthRevPath());
			url = new URL(getUMAuthRevPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			StringEntity jsonEntity = null;

			/*
			 * JSONObject jsonParams = new JSONObject(); jsonParams.put("f263_MRN",
			 * TxnDet.getF263_MRN()); logger.info("json: "+jsonParams.toString());
			 */

			OutputStream os = conn.getOutputStream();
			/* os.write(jsonParams.toString().getBytes()); */

			// String mrn ="f263_MRN="+TxnDet.getF263_MRN();
			// logger.info("MRN to UM Payment :"+mrn);
			String Params = null;
			StringBuffer urlString = new StringBuffer();
			urlString.append("f263_MRN=" + TxnDet.getF263_MRN());
			urlString.append("&service=" + "reverse");
			Params = urlString.toString();

			logger.info("URL String to UM Reverse " + Params);
//				String mrn ="f263_MRN=";
//				String mrn ="f263_MRN="+"2018102200000000000928910003278050201";

			// os.write(mrn.getBytes());
			os.write(Params.getBytes());

			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			JSONObject dataObj = new JSONObject(output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return rd;

	}

	public static ResponseDetails UmAuthPayment(UMEcomTxnRequest TxnDet) {
		ResponseDetails rd = null;
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		String Path = null;
		logger.info("Auth Service :  " + TxnDet.getStatus());

		try {

			logger.info("Auth Path :  " + getUMAuthPath(TxnDet.getStatus()));
			url = new URL(getUMAuthPath(TxnDet.getStatus()));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			StringEntity jsonEntity = null;

			/*
			 * JSONObject jsonParams = new JSONObject(); jsonParams.put("f263_MRN",
			 * TxnDet.getF263_MRN()); logger.info("json: "+jsonParams.toString());
			 */

			OutputStream os = conn.getOutputStream();
			/* os.write(jsonParams.toString().getBytes()); */

			/*
			 * String mrn ="f263_MRN="+TxnDet.getF263_MRN();
			 * logger.info("MRN  to UM Payment :"+mrn);
			 */
			String Params = null;
			StringBuffer urlString = new StringBuffer();
			urlString.append("f263_MRN=" + TxnDet.getF263_MRN());
			urlString.append("&service=" + TxnDet.getStatus());
			urlString.append("&amount=" + TxnDet.getF007_TxnAmt());
			Params = urlString.toString();

			logger.info("URL String to UM Payment " + Params);
			;
//				String mrn ="f263_MRN=";
//				String mrn ="f263_MRN="+"2018102200000000000928910003278050201";

			// os.write(mrn.getBytes());
			os.write(Params.getBytes());

			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			JSONObject dataObj = new JSONObject(output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return rd;

	}

	public static ResponseDetails motoVC(String mid, String tid, String status) {

		ResponseDetails rd = null;
		logger.info("MotoVC Path " + getMotoVCPath());
		Gson gson = new Gson();
		String error = "{\"responseCode\":\"0001\",\"responseMessage\":\"Failure\",\"responseDescription\":\"Moto VC Transaction Failed\"}";
		String success = "{\"responseCode\":\"0000\",\"responseMessage\":\"Success\",\"responseDescription\":\"Moto VC Transaction Done\"}";
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(getMotoVCPath());
			url = new URL(getMotoVCPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("tid", tid);
			jsonParams.put("mid", mid);
			jsonParams.put("service", "MOTO_VC_TXN");
			jsonParams.put("txnType", status);

			logger.info("Before json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			// JSONObject dataObj = new JSONObject(output);
			try {
				rd = gson.fromJson(output, ResponseDetails.class);
			} catch (JsonSyntaxException e) {
				logger.info(" JsonSyntaxException .... " + output);
				rd = gson.fromJson(error, ResponseDetails.class);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * finally{
		 * 
		 * }
		 */

		return rd;

	}

	public static ResponseDetails authDDRequest(MotoTxnDet motoTrans) {
		ResponseDetails rd = new ResponseDetails();
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		try {

			if (motoTrans.getHostType().contains("U")) {
				logger.info("follow UMobile path:" + getUMAuthDDPath());
				url = new URL(getUMAuthDDPath());
			} else {
				logger.info("follow Paydee path:" + getauthDDPath());
				url = new URL(getauthDDPath());
			}

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("mid", motoTrans.getMotoMid());
			jsonParams.put("tid", motoTrans.getTid());
			jsonParams.put("amount", motoTrans.getAmount());
			jsonParams.put("invoiceId", motoTrans.getReferrence());
			jsonParams.put("service", "PORTAL_PREAUTH_TXN_REQ");
			jsonParams.put("contactName", motoTrans.getContactName());
			jsonParams.put("longitude", motoTrans.getLongitude());
			jsonParams.put("latitude", motoTrans.getLatitude());
			jsonParams.put("mobileNo", motoTrans.getPhno());
			jsonParams.put("email", motoTrans.getEmail());
			jsonParams.put("expectedDate", motoTrans.getExpectedDate());
			jsonParams.put("reqMode", "WEB");
			jsonParams.put("hostType", motoTrans.getHostType());
			jsonParams.put("watsApp", motoTrans.getReceiptVia());
			jsonParams.put("nameOnCard", motoTrans.getcName());
			jsonParams.put("appVersionNum", "5.4");
			jsonParams.put("carddetails", motoTrans.getCardDetails());

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			JSONObject dataObj = new JSONObject(output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			rd.setResponseCode("0001");
			rd.setResponseMessage(e.getMessage());
			logger.info("exception: " + e.getMessage());
			// e.printStackTrace();
		} finally {

		}

		return rd;

	}

	public static ResponseDetails UMAuthDDRequest(MotoTxnDet motoTrans) {
		ResponseDetails rd = new ResponseDetails();
		logger.info("filepath " + getauthDDPath());
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(getauthDDPath());
			url = new URL(getauthDDPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("mid", motoTrans.getMotoMid());
			jsonParams.put("tid", motoTrans.getTid());
			jsonParams.put("amount", motoTrans.getAmount());
			jsonParams.put("invoiceId", motoTrans.getReferrence());
			jsonParams.put("service", "PORTAL_PREAUTH_TXN_REQ");
			jsonParams.put("contactName", motoTrans.getContactName());
			jsonParams.put("longitude", motoTrans.getLongitude());
			jsonParams.put("latitude", motoTrans.getLatitude());
			jsonParams.put("mobileNo", motoTrans.getPhno());
			jsonParams.put("email", motoTrans.getEmail());
			jsonParams.put("expectedDate", motoTrans.getExpectedDate());
			jsonParams.put("reqMode", "WEB");
			jsonParams.put("hostType", motoTrans.getHostType());
			jsonParams.put("watsApp", motoTrans.getReceiptVia());
			jsonParams.put("nameOnCard", motoTrans.getcName());
			jsonParams.put("appVersionNum", "5.4");
			jsonParams.put("carddetails", motoTrans.getCardDetails());

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			JSONObject dataObj = new JSONObject(output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			rd.setResponseCode("0001");
			rd.setResponseMessage(e.getMessage());
			logger.info("exception: " + e.getMessage());
			// e.printStackTrace();
		} finally {

		}

		return rd;

	}

	public static ResponseDetails ForgetPassword(Request motoTxnDet) {
		ResponseDetails rd = null;
		logger.info("filepath " + getPaymentPath());
		Gson gson = new Gson();
		String error = "{\"responseCode\":\"0001\",\"responseMessage\":\"Failure\",\"responseDescription\":\"OTP Request Failed\"}";
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(getPaymentPath());
			url = new URL(getPaymentPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("service", motoTxnDet.getService());
			jsonParams.put("username", motoTxnDet.getUsername());
			jsonParams.put("mobileNo", motoTxnDet.getMobileNo());
			jsonParams.put("email", motoTxnDet.getEmail());
			jsonParams.put("salutation", motoTxnDet.getSalutation());
			jsonParams.put("firstName", motoTxnDet.getFirstName());

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			// JSONObject dataObj = new JSONObject(output);
			try {
				rd = gson.fromJson(output, ResponseDetails.class);
			} catch (JsonSyntaxException e) {
				logger.info(" JsonSyntaxException .... " + output);
				rd = gson.fromJson(error, ResponseDetails.class);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * finally{
		 * 
		 * }
		 */

		return rd;

	}

	public static ResponseDetails UmRefundPayment(UMEcomTxnRequest TxnDet) {
		ResponseDetails rd = null;
		logger.info("UM Refund path " + getUMRefundPath());
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(getUMRefundPath());
			url = new URL(getUMRefundPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();
			jsonParams.put("f263_mrn", TxnDet.getF263_MRN());
			jsonParams.put("amount", TxnDet.getF247_OrgTxnAmt());
			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			JSONObject dataObj = new JSONObject(output);

			rd = gson.fromJson(output, ResponseDetails.class);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}

		return rd;

	}

	public static ResponseDetails RefundOTP(Request motoTxnDet) {
		ResponseDetails rd = null;
		logger.info("filepath " + getPaymentPath());
		Gson gson = new Gson();
		String error = "{\"responseCode\":\"0001\",\"responseMessage\":\"Failure\",\"responseDescription\":\"OTP Request Failed\"}";
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(getPaymentPath());
			url = new URL(getPaymentPath());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("service", motoTxnDet.getService());
			jsonParams.put("username", motoTxnDet.getUsername());
			jsonParams.put("mobileNo", motoTxnDet.getMobileNo());
			jsonParams.put("email", motoTxnDet.getEmail());
			jsonParams.put("salutation", motoTxnDet.getSalutation());
			jsonParams.put("firstName", motoTxnDet.getFirstName());
			jsonParams.put("msgType", "REFUND");

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			// JSONObject dataObj = new JSONObject(output);
			try {
				rd = gson.fromJson(output, ResponseDetails.class);
			} catch (JsonSyntaxException e) {
				logger.info(" JsonSyntaxException .... " + output);
				rd = gson.fromJson(error, ResponseDetails.class);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * finally{
		 * 
		 * }
		 */

		return rd;

	}

	// PreAuth Void BY Merchant Started on 4/05/22

	public static ResponseDetails PreAuthVoid(PreAuthorization preAuth) {

		ResponseDetails rd = null;
		Gson gson = new Gson();
		String error = "{\"responseCode\":\"0001\",\"responseMessage\":\"Failure\",\"responseDescription\":\"Void Transaction Failed\"}";
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(PropertyLoad.getFile().getProperty("VOID_SALE"));
			url = new URL(PropertyLoad.getFile().getProperty("VOID_SALE"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("service", "PRE_AUTH_VOID");
			jsonParams.put("hostType", preAuth.getHostType());
			jsonParams.put("sessionId", PropertyLoad.getFile().getProperty("PREAUTH_SESSIONID"));
			jsonParams.put("tid", preAuth.getTid());
			jsonParams.put("trxId", preAuth.getTrxId());

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			try {
				rd = gson.fromJson(output, ResponseDetails.class);
			} catch (JsonSyntaxException e) {
				logger.info(" JsonSyntaxException .... " + output);
				rd = gson.fromJson(error, ResponseDetails.class);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return rd;

	}

	public static ResponseDetails PreAuthVoid1(UMEcomTxnResponse uxtxn) {

		ResponseDetails rd = null;
		Gson gson = new Gson();
		String error = "{\"responseCode\":\"0001\",\"responseMessage\":\"Failure\",\"responseDescription\":\"Void Transaction Failed\"}";
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(PropertyLoad.getFile().getProperty("VOID_SALE"));
			url = new URL(PropertyLoad.getFile().getProperty("VOID_SALE"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("service", "PRE_AUTH_VOID");
			jsonParams.put("hostType", uxtxn.getF261_HostID());
			jsonParams.put("sessionId", PropertyLoad.getFile().getProperty("PREAUTH_SESSIONID"));
			jsonParams.put("tid", uxtxn.getF354_TID());
			jsonParams.put("trxId", uxtxn.getF263_MRN());

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			try {
				rd = gson.fromJson(output, ResponseDetails.class);
			} catch (JsonSyntaxException e) {
				logger.info(" JsonSyntaxException .... " + output);
				rd = gson.fromJson(error, ResponseDetails.class);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return rd;

	}
	
	
	//rk added(7/7/2022)
	
	public static ResponseDetails wirePreAuthVoid1(PreAuthorization prtxn) {

		ResponseDetails rd = null;
		Gson gson = new Gson();
		String error = "{\"responseCode\":\"0001\",\"responseMessage\":\"Failure\",\"responseDescription\":\"Void Transaction Failed\"}";
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(PropertyLoad.getFile().getProperty("VOID_SALE"));
			url = new URL(PropertyLoad.getFile().getProperty("VOID_SALE"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("service", "PRE_AUTH_VOID");
			jsonParams.put("hostType", prtxn.getHostType());
			jsonParams.put("sessionId", PropertyLoad.getFile().getProperty("PREAUTH_SESSIONID"));
			jsonParams.put("tid", prtxn.getTid());
			jsonParams.put("trxId", prtxn.getTrxId());

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			try {
				rd = gson.fromJson(output, ResponseDetails.class);
			} catch (JsonSyntaxException e) {
				logger.info(" JsonSyntaxException .... " + output);
				rd = gson.fromJson(error, ResponseDetails.class);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return rd;

	}
	
	
	

	// PreAuth Void BY Merchant Ended on 4/05/22

	// PreAuth Convert To Sale Started on 5/05/22

	public static ResponseDetails ConvertToSale(PreauthModel preAuth) {

		ResponseDetails rd = null;
		Gson gson = new Gson();
		String error = "{\"responseCode\":\"0001\",\"responseMessage\":\"Failure\",\"responseDescription\":\"Void Transaction Failed\"}";
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(PropertyLoad.getFile().getProperty("VOID_SALE"));
			url = new URL(PropertyLoad.getFile().getProperty("VOID_SALE"));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("amount", preAuth.getAmount());
			jsonParams.put("merchantId", preAuth.getMerchantId());
			jsonParams.put("service", "PRE_AUTH_SALE");
			jsonParams.put("hostType", preAuth.getMerchantType());
			jsonParams.put("sessionId", PropertyLoad.getFile().getProperty("PREAUTH_SESSIONID"));
			jsonParams.put("trxId", preAuth.getTxnid());

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}

			output = response.toString();
			logger.info(" Output from Server .... " + output);

			try {
				rd = gson.fromJson(output, ResponseDetails.class);
			} catch (JsonSyntaxException e) {
				logger.info(" JsonSyntaxException .... " + output);
				rd = gson.fromJson(error, ResponseDetails.class);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return rd;

	}

	// PreAuth Convert To Sale End on 5/05/22
	
	
	// fiuu void path
	
	public static String getFiuuVoidPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
		//	input = loader.getResourceAsStream("/config_demo.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("FIUU_Void_URL"));
			path = prop.getProperty("FIUU_Void_URL");
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
	

	//void for fiuu Txns 
	
	public static ResponseDetails FiuuCancelPayment(UMEcomTxnRequest TxnDet) {
	    ResponseDetails rd = null;
	    logger.info("Fiuu Void path " +PropertyLoad.getFile().getProperty("FIUU_Void_URL"));
	    
	    Gson gson = new Gson();
	    String inputLine = null;
	    String output = null;
	    URL url;
	    try {
	        // Construct the URL with query parameters
	        String urlString = PropertyLoad.getFile().getProperty("FIUU_Void_URL") + "?f263_MRN=" + TxnDet.getF263_MRN();
	        logger.info("Constructed URL: " + urlString);

	        url = new URL(urlString);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET"); 
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

	        StringBuffer response = new StringBuffer();
	        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

	        while ((inputLine = br.readLine()) != null) {
	            response.append(inputLine);
	        }

	        output = response.toString();
	        logger.info("Output from Server: " + output);

	        rd = gson.fromJson(output, ResponseDetails.class);

	    } catch (Exception e) {
	        e.printStackTrace();
	        rd.setResponseDescription("An Unexpected Error Occured..");
	    }

	    return rd;
	}
	
	
	//fiuu changes
	
	//ezymoto payment request url
	
	public static String getFiuuPaymentPath() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			//input = loader.getResourceAsStream("/config_demo.properties");
			prop.load(input);
			System.out.println("Path :" + prop.getProperty("FIUU_PAYMENT_URL"));
			path = prop.getProperty("FIUU_PAYMENT_URL");
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
	
	
	
	//ezymoto request call

	
	public static ResponseDetails fiuuMotoPayment(MotoTxnDet motoTrans) {
	    ResponseDetails rd = new ResponseDetails();  
	    Gson gson = new Gson();
	    String inputLine = null;
	    String output = null;
	    URL url;

	    try {
	        logger.info("Whatsapp In: " + motoTrans.getWhatsapp());
	        
	        logger.info("filepath " + PropertyLoad.getFile().getProperty("FIUU_PAYMENT_URL"));

	        url = new URL(PropertyLoad.getFile().getProperty("FIUU_PAYMENT_URL"));
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setDoOutput(true);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/json");

	        JSONObject jsonParams = new JSONObject();
	        jsonParams.put("mid", motoTrans.getMotoMid());
	        jsonParams.put("tid", motoTrans.getTid());
	        jsonParams.put("amount", motoTrans.getAmount());
	        jsonParams.put("reference", motoTrans.getReferrence());
	        jsonParams.put("cardHolderName", motoTrans.getNameoncard());
	        jsonParams.put("carddetails", motoTrans.getCardDetails());

	        logger.info("json: " + jsonParams.toString());

	        OutputStream os = conn.getOutputStream();
	        os.write(jsonParams.toString().getBytes());
	        os.flush();

	        int responseCode = conn.getResponseCode();
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            StringBuffer response = new StringBuffer();
	            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }

	            output = response.toString();  
	            logger.info("Output from Server: " + output);

	            rd = gson.fromJson(output, ResponseDetails.class);

	        } else {
	            // If response is not OK (e.g., error code 500), read the error stream
	            logger.error("Error response code: " + responseCode);

	            BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
	            StringBuilder errorResponse = new StringBuilder();
	            String errorLine;
	            while ((errorLine = errorReader.readLine()) != null) {
	                errorResponse.append(errorLine);
	            }

	            logger.error("Error response from server: " + errorResponse.toString());

	            output = errorResponse.toString();  
	            if (output != null && !output.isEmpty()) {
	                rd = gson.fromJson(output, ResponseDetails.class);
	            } else {
	                rd.setResponseCode("500");
	                rd.setResponseMessage("Server returned an error with no response body.");
	                rd.setResponseDescription("An unexpected error occurred during the payment process.");
	            }
	        }

	    } catch (IOException e) {
	        // Handle IOExceptions separately
	        logger.error("IOException occurred: " + e.getMessage());
	        rd.setResponseCode("500");
	        rd.setResponseMessage("IOException: " + e.getMessage());
	        rd.setResponseDescription("An IOException occurred during the payment process.");

	    } catch (Exception e) {
	        // General exception handler
	        logger.error("Exception occurred: " + e.getMessage());
	        rd.setResponseCode("500");
	        rd.setResponseMessage("Exception: " + e.getMessage());
	        rd.setResponseDescription("An exception occurred during the payment process.");
	    }

	    // Ensure rd is never null by the time we return it
	    if (rd == null) {
	        rd = new ResponseDetails();
	        rd.setResponseCode("500");
	        rd.setResponseMessage("Unknown error");
	        rd.setResponseDescription("ResponseDetails object was null.");
	    }

	    // Return the rd object with the response data
	    return rd;
	}


	//fiuu auth 2d request
	
	public static ResponseDetails fiuuAuthViaMotoPayment(MotoTxnDet motoTrans) {
		ResponseDetails rd = new ResponseDetails();
		logger.info("filepath " + PropertyLoad.getFile().getProperty("FIUU_EZYAUTH_PAYMENT_URL"));
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		try {

			logger.info("Watsapp In: " + motoTrans.getWhatsapp());
			String basePath = PropertyLoad.getFile().getProperty("FIUU_EZYAUTH_PAYMENT_URL");
			logger.info("url for auth request : "+basePath);
			url = new URL(basePath);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("mid", motoTrans.getMotoMid());
			jsonParams.put("tid", motoTrans.getTid());
			jsonParams.put("amount", motoTrans.getAmount());
			jsonParams.put("invoiceId", motoTrans.getReferrence());
			jsonParams.put("nameOnCard", motoTrans.getNameoncard());
			jsonParams.put("carddetails", motoTrans.getCardDetails());

			logger.info("json: " + jsonParams.toString());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();
			
			 int responseCode = conn.getResponseCode();
		        if (responseCode == HttpURLConnection.HTTP_OK) {
		            
		            StringBuffer response = new StringBuffer();
		            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		            while ((inputLine = br.readLine()) != null) {
		                response.append(inputLine);
		            }

		            output = response.toString(); 
		            logger.info("Output from Server: " + output);

		            rd = gson.fromJson(output, ResponseDetails.class);

		        } else {
		     
		            logger.error("Error response code: " + responseCode);

		            BufferedReader errorReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		            StringBuilder errorResponse = new StringBuilder();
		            String errorLine;
		            while ((errorLine = errorReader.readLine()) != null) {
		                errorResponse.append(errorLine);
		            }

		            logger.error("Error response from server: " + errorResponse.toString());

		            output = errorResponse.toString();  
		            if (output != null && !output.isEmpty()) {
		                rd = gson.fromJson(output, ResponseDetails.class);
		            } else {
		                rd.setResponseCode("500");
		                rd.setResponseMessage("Server returned an error with no response body.");
		                rd.setResponseDescription("An unexpected error occurred during the payment process.");
		            }
		        }

		    } catch (IOException e) {
		       
		        logger.error("IOException occurred: " + e.getMessage());
		        rd.setResponseCode("500");
		        rd.setResponseMessage("IOException: " + e.getMessage());
		        rd.setResponseDescription("An unexpected error occurred during the payment process.");

		    } catch (Exception e) {

		        logger.error("Exception occurred: " + e.getMessage());
		        rd.setResponseCode("500");
		        rd.setResponseMessage("Exception: " + e.getMessage());
		        rd.setResponseDescription("An exception occurred during the payment process.");
		    }

		    if (rd == null) {
		        rd = new ResponseDetails();
		        rd.setResponseCode("500");
		        rd.setResponseMessage("Unknown error");
		        rd.setResponseDescription("An unexpected error occurred.");
		    }
	

		return rd;

	}
	
	//fiuu preauth void
	
	public static ResponseDetails FiuuCancelPaymentForAuth(UMEcomTxnResponse TxnDet) {
	    ResponseDetails rd = null;
	    logger.info("Fiuu Void path " +PropertyLoad.getFile().getProperty("FIUU_Void_URL"));
	    Gson gson = new Gson();
	    String inputLine = null;
	    String output = null;
	    URL url;
	    try {
	        // Construct the URL with query parameters
	        String urlString = PropertyLoad.getFile().getProperty("FIUU_Void_URL") + "?f263_MRN=" + TxnDet.getF263_MRN();
	        logger.info("Constructed URL: " + urlString);

	        url = new URL(urlString);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET"); 
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

	        StringBuffer response = new StringBuffer();
	        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

	        while ((inputLine = br.readLine()) != null) {
	            response.append(inputLine);
	        }

	        output = response.toString();
	        logger.info("Output from Server: " + output);

	        rd = gson.fromJson(output, ResponseDetails.class);

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return rd;
	}
	
	

	// convert to sale for Fiuu
	
	public static ResponseDetails convertToSaleForFiuu(PreauthModel preAuth) {

		ResponseDetails rd = null;
		Gson gson = new Gson();
		String error = "{\"responseCode\":\"0001\",\"responseMessage\":\"Failure\",\"responseDescription\":\"Void Transaction Failed\"}";
		String inputLine = null;
		String output = null;
		URL url;
		try {
			logger.info(PropertyLoad.getFile().getProperty("FIUU_CONVERT_TO_SALE"));
			String basePath = PropertyLoad.getFile().getProperty("FIUU_CONVERT_TO_SALE");
			
			String urlString = basePath + "?f263_MRN=" +  preAuth.getTxnid()+"&amount=" +preAuth.getAmount();
			logger.info("url for convert to sale : "+basePath);
			url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			 StringBuffer response = new StringBuffer();
		        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

		        while ((inputLine = br.readLine()) != null) {
		            response.append(inputLine);
		        }
		    
			output = response.toString();
			logger.info(" Output from Server .... " + output);

			try {
				rd = gson.fromJson(output, ResponseDetails.class);
			} catch (JsonSyntaxException e) {
				logger.info(" JsonSyntaxException .... " + output);
				rd = gson.fromJson(error, ResponseDetails.class);
			}

		} catch (Exception e) {

			e.printStackTrace();
		}

		return rd;

	}


}
