package com.mobiversa.payment.util;

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

public class ElasticEmailClient {

	private static final Logger logger = Logger.getLogger(ElasticEmailClient.class);

	public static String sendMessage1(ElasticEmail message) {

		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;

		try {
//			url = new URL("http://localhost:8089/ElasticWebProject/EmailApi/attachment");
//			url = new URL("http://192.168.19.115:6060/ElasticWebProject/EmailApi/attachment");
			url = new URL(elasticmailApi());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject params = new JSONObject();

			params.put("fromEmail", message.getFromEmail());
			params.put("subject", message.getSubject());
			params.put("fromName", message.getFromName());
			params.put("msgto", message.getMsgto());
			params.put("cc", message.getCc());
			params.put("bcc", message.getBcc());
			params.put("attachment", message.getAttachment());
			params.put("textbody", message.getTextbody());
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);

			}

			// output = response.toString();
			output = "200";

			System.out.println("Response :" + output);
		} catch (Exception e) {
			output = "400";
			e.printStackTrace();
		}
		// return paramss;
		return output;

	}

	public static String sendEMailWithAttachment(ElasticEmail message) {

		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;

		try {
//			url = new URL("http://localhost:8089/ElasticWebProject/EmailApi/attachment");
//			url = new URL(PropertyLoad.getFile().getProperty("ELASTIC_EMAIL_API_WITH_ATTACHMENT"));
			url = new URL(elasticmailApiWithAttachment());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");

			StringEntity jsonEntity = null;

			JSONObject params = new JSONObject();

			params.put("fromEmail", message.getFromEmail());
			params.put("subject", message.getSubject());
			params.put("fromName", message.getFromName());
			params.put("msgto", message.getMsgto());
			params.put("cc", message.getCc());
			params.put("bcc", message.getBcc());
			params.put("attachment", message.getAttachment());
			params.put("textbody", message.getTextbody());
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);

			}

			// output = response.toString();
			output = "200";

			System.out.println("Response :" + output);
		} catch (Exception e) {
			output = "400";
			e.printStackTrace();
		}
		// return paramss;
		return output;

	}

	public static JSONObject sendMessage(ElasticEmail message) {
		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		try {

			url = new URL(elasticmail());
			// url = new URL("http://localhost:8080/ElasticWebProject/EmailApi/attachment");
			// url = new
			// URL("http://192.168.10.202:3030/ElasticWebProject/EmailApi/attachment");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			StringEntity jsonEntity = null;
			JSONObject params = new JSONObject();
			params.put("fromEmail", message.getFromEmail());
			params.put("subject", message.getSubject());
			params.put("fromName", message.getFromName());
			params.put("msgto", message.getMsgto());
			params.put("cc", message.getCc());
			params.put("bcc", message.getBcc());
			params.put("attachment", message.getAttachment());
			params.put("textbody", message.getTextbody());
			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			output = response.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return paramss;
	}

	//Submerchant -saravana changes
	public static String elasticmail() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("config.properties");
			prop.load(input);
			logger.info("Path :" + prop.getProperty("ELASTIC_EMAIL"));
			path = prop.getProperty("ELASTIC_EMAIL");
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

	public static String elasticmailApi() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			logger.info("Path :" + prop.getProperty("ELASTIC_EMAIL_API"));
			path = prop.getProperty("ELASTIC_EMAIL_API");
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

	public static String elasticmailApiWithAttachment() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			logger.info("Path :" + prop.getProperty("ELASTIC_EMAIL_API_WITH_ATTACHMENT"));
			path = prop.getProperty("ELASTIC_EMAIL_API_WITH_ATTACHMENT");
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
	
	@SuppressWarnings("nls")
	public static int sendemailSlip(String fromEmail, String subject, String fromName, String msgto, String cc, String bcc, String bodyHtml) {

	    HttpURLConnection conn = null;
	    BufferedReader br = null;
	    OutputStream os = null;
	    String inputLine = null;
	    String output = null;
	    URL url;
	    int responseCode = 0;

	    try {
	        // Get the Elastic email URL from properties
	        String elasticemailurl = PropertyLoader.getFileData().getProperty("ELASTIC_EMAIL_SLIP");

	        // Initialize connection
	        url = new URL(elasticemailurl);
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setDoOutput(true);
	        conn.setRequestMethod("POST");
	        conn.setRequestProperty("Content-Type", "application/json");

	        // Prepare JSON payload
	        JSONObject jsonParams = new JSONObject();
	        jsonParams.put("fromEmail", fromEmail);
	        jsonParams.put("subject", subject);
	        jsonParams.put("fromName", fromName);
	        jsonParams.put("msgto", msgto);
	        jsonParams.put("cc", cc);
	        jsonParams.put("bcc", bcc);
	        jsonParams.put("bodyHtml", bodyHtml);

			logger.info("Elastic email URL: " + elasticemailurl + ", fromEmail=" + fromEmail + ", subject=" + subject
					+ ", fromName=" + fromName + ", msgto=" + msgto + ", cc=" + cc + ", bcc=" + bcc);

	        // Send the request
	        os = conn.getOutputStream();
	        os.write(jsonParams.toString().getBytes("UTF-8"));
	        os.flush();

	        // Get the response code
	        responseCode = conn.getResponseCode();
	        logger.info("Response code: " + responseCode);

	        // Process the response
	        if (responseCode == HttpURLConnection.HTTP_OK) {
	            StringBuilder response = new StringBuilder();
	            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	            while ((inputLine = br.readLine()) != null) {
	                response.append(inputLine);
	            }
	            output = response.toString();
	            logger.info("Output from server: " + output);
	        } else {
	            logger.error("Failed to send email. Response code: " + responseCode);
	        }

	        return responseCode;

	    } catch (Exception e) {
	        logger.error("Error occurred while sending email: " + e.getMessage(), e);
	        return responseCode;
	    } finally {
	        // Close the resources properly
	        if (os != null) {
	            try {
	                os.close();
	            } catch (IOException ex) {
	                logger.error("Failed to close OutputStream", ex);
	            }
	        }
	        if (br != null) {
	            try {
	                br.close();
	            } catch (IOException ex) {
	                logger.error("Failed to close BufferedReader", ex);
	            }
	        }
	        if (conn != null) {
	            conn.disconnect();
	        }
	    }
	}


//	public static int sendemailSlip(String fromEmail, String subject, String fromName, String msgto, String cc, String bcc, String bodyHtml) {
//
//		Gson gson = new Gson();
//		String inputLine = null;
//		String output = null;
//		URL url;
//		int responseCode = 0;
//		try {
//			String elasticemailurl = PropertyLoader.getFileData().getProperty("ELASTIC_EMAIL_SLIP");
//			logger.info("Elastic url " + elasticemailurl);
//			url = new URL(elasticemailurl);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setDoOutput(true);
//			conn.setRequestMethod("POST");
//			conn.setRequestProperty("Content-Type", "application/json");
//
//			JSONObject jsonParams = new JSONObject();
//			jsonParams.put("fromEmail", fromEmail);
//			jsonParams.put("subject", subject);
//			jsonParams.put("fromName", fromName);
//			jsonParams.put("msgto", msgto);
//			jsonParams.put("cc", cc);
//			jsonParams.put("bcc", bcc);
//			jsonParams.put("bodyHtml", bodyHtml);
//
//			logger.info("fromEmai " + fromEmail + " subject " + subject + " fromname " + fromName + " msgto " + msgto
//					+ " cc " + cc + " bcc " + bcc);
//
//			OutputStream os = conn.getOutputStream();
//			os.write(jsonParams.toString().getBytes());
//			os.flush();
//
//			StringBuffer response = new StringBuffer();
//			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
//			responseCode = conn.getResponseCode();
//			while ((inputLine = br.readLine()) != null) {
//				response.append(inputLine);
//			}
//			output = response.toString();
//			logger.info(" Output from Server : " + output);
//			return responseCode;
//		} catch (Exception e) {
//			logger.info(" Error message : " + e.getMessage());
//			e.printStackTrace();
//			return responseCode;
//		}
//	}

	public static JSONObject sendEzysettleMessage(ElasticEmail message) {

		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		try {

			url = new URL(PropertyLoader.getFileData().getProperty("ELASTIC_EMAIL"));

			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			StringEntity jsonEntity = null;
			JSONObject params = new JSONObject();
			params.put("fromEmail", message.getFromEmail());
			params.put("subject", message.getSubject());
			params.put("fromName", message.getFromName());
			params.put("msgto", message.getMsgto());
			params.put("cc", message.getCc());
			params.put("bcc", message.getBcc());
			params.put("attachment", message.getAttachment());
			params.put("textbody", message.getTextbody());
			paramss = params;

			logger.info("Request Params For Mail : " + params);

			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			output = response.toString();
			logger.info("Output Response Elastic mail : " + output);

		} catch (Exception e) {
			logger.info("Exception SendEzysettleMessage : " + e.getMessage());
			e.printStackTrace();
		}
		return paramss;

	}

	public static int sendemailSlipForEzysettle(ElasticEmail message) {

		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		int responseCode = 0;
		try {

			String elasticemailurl = PropertyLoader.getFileData().getProperty("ELASTIC_EMAIL_SLIP_EZYSETTLE");
			logger.info("Elastic url " + elasticemailurl);
			url = new URL(elasticemailurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			JSONObject jsonParams = new JSONObject();
			jsonParams.put("fromEmail", message.getFromEmail());
			jsonParams.put("subject", message.getSubject());
			jsonParams.put("fromName", message.getFromName());
			jsonParams.put("msgto", message.getMsgto());
			jsonParams.put("cc", message.getCc());
			jsonParams.put("bcc", message.getBcc());
			jsonParams.put("bodyHtml", message.getTextbody());

			logger.info("fromEmai " + message.getFromEmail() + " subject " + message.getSubject() + " fromname "
					+ message.getFromName() + " msgto " + message.getMsgto() + " cc " + message.getCc() + " bcc "
					+ message.getTextbody());

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			responseCode = conn.getResponseCode();
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			output = response.toString();
			logger.info(" Output from Server : " + output);
			return responseCode;
		} catch (Exception e) {
			logger.info(" Error message : " + e.getMessage());
			e.printStackTrace();
			return responseCode;
		}
	}

}
