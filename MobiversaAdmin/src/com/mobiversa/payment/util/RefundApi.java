package com.mobiversa.payment.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobiversa.common.bo.RefundRequest;
import com.mobiversa.payment.controller.bean.RefundBean;

public class RefundApi {
	private static final Logger logger = Logger.getLogger(RefundApi.class);

	@SuppressWarnings("nls")
	public static RefundBean ConnectToBoost(String merchantId, String onlineRefNum, String boostPaymentRefNum,
			String remark, String refundType, String amount, String checksum, String transactionType) {

		RefundBean refundBean = null;
		String apiUrl = getBoostApiUrl(transactionType);
		logApiUrl(apiUrl);

		try {
			URL url = new URL(apiUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			setupBoostConnectionProperties(con);

			JSONObject params = buildBoostRequestParameters(merchantId, onlineRefNum, boostPaymentRefNum, remark,
					refundType, amount, checksum);

			sendRequest(con, params);

			int responseCode = con.getResponseCode();
			logResponseCode(responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) {
				String output = readResponse(con);
				logBoostResponse(output);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				refundBean = gson.fromJson(output, RefundBean.class);
			} else {
				logUnexpectedResponseCode(responseCode);
			}

		} catch (MalformedURLException e) {
			logError("Malformed URL during Boost API call. Details: " + e.getMessage());
		} catch (IOException e) {
			logError("IO exception occurred during Boost API call. Details: " + e.getMessage());
		}

		return refundBean;
	}

	@SuppressWarnings("nls")
	public static RefundBean ConnectToGrabPay(String service, String sessionId, String partnerTxID,
			String description) {

		RefundBean refundBean = null;
		String apiUrl = getGrabPayApiUrl();
		logApiUrl(apiUrl);

		try {
			URL url = new URL(apiUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			setupGrabPayConnectionProperties(con);

			JSONObject params = buildGrabPayRequestParameters(service, sessionId, partnerTxID, description);

			sendRequest(con, params);

			int responseCode = con.getResponseCode();
			logResponseCode(responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) {
				String output = readResponse(con);
				logGrabPayResponse(output);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				refundBean = gson.fromJson(output, RefundBean.class);
			} else {
				logUnexpectedResponseCode(responseCode);
			}

		} catch (MalformedURLException e) {
			logError("Malformed URL during GrabPay API call. Details: " + e.getMessage());
		} catch (IOException e) {
			logError("IO exception occurred during GrabPay API call. Details: " + e.getMessage());
		}

		return refundBean;
	}

	@SuppressWarnings("nls")
	public static RefundBean ConnectToTouchnGoAndShopeePay(String transactionId, String amount, String currencyType,
			String reason, String refundType, String merchantId, String service, String xSignature) {

		RefundBean refundBean = null;
		String apiUrl = getTouchnGoAndShopeePayApiUrl();
		logApiUrl(apiUrl);

		try {
			URL url = new URL(apiUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			setupTouchnGoAndShopeePayConnectionProperties(con, service, xSignature);

			JSONObject params = buildTouchnGoAndShopeePayRequestParameters(transactionId, amount, currencyType, reason,
					refundType, merchantId);

			sendRequest(con, params);

			int responseCode = con.getResponseCode();
			logResponseCode(responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) {
				String output = readResponse(con);
				logTouchnGoAndShopeePayResponse(output);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				refundBean = gson.fromJson(output, RefundBean.class);
			} else {
				logUnexpectedResponseCode(responseCode);
			}

		} catch (MalformedURLException e) {
			logError("Malformed URL during Touch'n Go And ShopeePay API call. Details: " + e.getMessage());
		} catch (IOException e) {
			logError("IO exception occurred during Touch'n Go And ShopeePay API call. Details: " + e.getMessage());
		}

		return refundBean;
	}

	@SuppressWarnings("nls")
	public static String ConnectToTouchnGoAndShopeePayOAuth(String service) {

		RefundBean refundBean = null;
		String apiUrl = getTouchnGoAndShopeePayApiOAuthUrl();
		logApiUrl(apiUrl);

		try {
			URL url = new URL(apiUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			setupTouchnGoAndShopeePayOAuthConnectionProperties(con);

			JSONObject params = buildTouchnGoAndShopeePayOAuthRequestParameters(service);

			sendRequest(con, params);

			int responseCode = con.getResponseCode();
			logResponseCode(responseCode);

			if (responseCode == HttpURLConnection.HTTP_OK) {
				String output = readResponse(con);
				logTouchnGoAndShopeePayOAuthResponse(output);

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				refundBean = gson.fromJson(output, RefundBean.class);
			} else {
				logUnexpectedResponseCode(responseCode);
			}

		} catch (MalformedURLException e) {
			logError("Malformed URL during Touch'n Go And ShopeePay OAuth API call. Details: " + e.getMessage());
		} catch (IOException e) {
			logError(
					"IO exception occurred during Touch'n Go And ShopeePay OAuth API call. Details: " + e.getMessage());
		}

		return refundBean.getResponseData().getToken();
	}

	@SuppressWarnings("nls")
	private static String getBoostApiUrl(String transactionType) {
		return transactionType.equalsIgnoreCase("void") ? PropertyLoad.getFile().getProperty("BOOST_VOID_URL")
				: PropertyLoad.getFile().getProperty("BOOST_REFUND_URL");
	}

	@SuppressWarnings("nls")
	private static String getGrabPayApiUrl() {
		return PropertyLoad.getFile().getProperty("GRABPAY_REFUND_URL");

	}

	@SuppressWarnings("nls")
	private static String getTouchnGoAndShopeePayApiUrl() {
		return PropertyLoad.getFile().getProperty("TNG_SPP_REFUND_URL");

	}

	@SuppressWarnings("nls")
	private static String getTouchnGoAndShopeePayApiOAuthUrl() {
		return PropertyLoad.getFile().getProperty("TNG_SPP_OAUTH_URL");

	}

	@SuppressWarnings("nls")
	private static void setupBoostConnectionProperties(HttpURLConnection con) throws ProtocolException {
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "Bearer " + PropertyLoad.getFile().getProperty("BOOST_API_TOKEN"));
	}

	@SuppressWarnings("nls")
	private static void setupGrabPayConnectionProperties(HttpURLConnection con) throws ProtocolException {
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
	}

	@SuppressWarnings("nls")
	private static void setupTouchnGoAndShopeePayConnectionProperties(HttpURLConnection con, String service,
			String xSignature) throws ProtocolException {
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("Authorization", "Bearer " + ConnectToTouchnGoAndShopeePayOAuth(service));
		con.setRequestProperty("X-Signature", xSignature);
	}

	@SuppressWarnings("nls")
	private static void setupTouchnGoAndShopeePayOAuthConnectionProperties(HttpURLConnection con)
			throws ProtocolException {
		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
	}

	@SuppressWarnings("nls")
	private static JSONObject buildBoostRequestParameters(String merchantId, String onlineRefNum,
			String boostPaymentRefNum, String remark, String refundType, String amount, String checksum) {
		JSONObject params = new JSONObject();
		params.put("merchantId", merchantId);
		params.put("onlineRefNum", onlineRefNum);
		params.put("boostPaymentRefNum", boostPaymentRefNum);
		params.put("remark", remark);
		params.put("partial", refundType.equalsIgnoreCase("Partial") ? 1 : 0);
		params.put("amount", amount);
		params.put("checksum", checksum);
		return params;
	}

	@SuppressWarnings("nls")
	private static JSONObject buildGrabPayRequestParameters(String service, String sessionId, String partnerTxID,
			String description) {
		JSONObject params = new JSONObject();
		params.put("service", service);
		params.put("sessionId", sessionId);
		params.put("partnerTxID", partnerTxID);
		params.put("description", description);
		return params;
	}

	@SuppressWarnings("nls")
	private static JSONObject buildTouchnGoAndShopeePayRequestParameters(String transactionId, String amount,
			String currencyType, String reason, String refundType, String merchantId) {
		JSONObject params = new JSONObject();
		params.put("transactionId", transactionId);
		params.put("amount", amount);
		params.put("currencyType", currencyType);
		params.put("reason", reason);
		params.put("refundType", refundType);
		params.put("merchantId", merchantId);
		return params;
	}

	@SuppressWarnings("nls")
	private static JSONObject buildTouchnGoAndShopeePayOAuthRequestParameters(String service) {
		JSONObject params = new JSONObject();
		params.put("service", service);
		return params;
	}

	@SuppressWarnings("nls")
	private static void sendRequest(HttpURLConnection con, JSONObject params) throws IOException {
		try (OutputStream os = con.getOutputStream()) {
			os.write(params.toString().getBytes());
			logger.info("Request Payload: " + params);
		}
	}

	private static String readResponse(HttpURLConnection con) throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
			return br.lines().collect(Collectors.joining(System.lineSeparator()));
		}
	}

	@SuppressWarnings("nls")
	private static void logResponseCode(int responseCode) {
		logger.info("HTTP Response Code: " + responseCode);
	}

	@SuppressWarnings("nls")
	private static void logBoostResponse(String output) {
		logger.info("Response from Boost API: " + output);
	}

	@SuppressWarnings("nls")
	private static void logGrabPayResponse(String output) {
		logger.info("Response from GrabPay API: " + output);
	}

	@SuppressWarnings("nls")
	private static void logTouchnGoAndShopeePayResponse(String output) {
		logger.info("Response from Touch'n Go And ShopeePay API: " + output);
	}

	@SuppressWarnings("nls")
	private static void logTouchnGoAndShopeePayOAuthResponse(String output) {
		logger.info("Response from Touch'n Go And ShopeePay OAuth API: " + output);
	}

	@SuppressWarnings("nls")
	private static void logUnexpectedResponseCode(int responseCode) {
		logger.info("Unexpected HTTP Response Code: " + responseCode);
	}

	private static void logApiUrl(String apiUrl) {
		logger.info(apiUrl);
	}

	private static void logError(String message) {
		logger.error(message);
	}

	@SuppressWarnings("nls")
	public static void sendRefundNotificationEmail(RefundRequest refundRequest, String transactiondate,
			String merchantEmail) {
		String senderEmail = PropertyLoad.getFile().getProperty("senderEmail");
		String senderPassword = PropertyLoad.getFile().getProperty("senderPassword");
		String recipientEmail = merchantEmail + "," + PropertyLoad.getFile().getProperty("recipientEmail");

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.user", PropertyLoad.getFile().getProperty("fromName"));
		properties.put("mail.smtp.host", PropertyLoad.getFile().getProperty("mail.smtp.host"));
		properties.put("mail.smtp.port", PropertyLoad.getFile().getProperty("mail.smtp.port"));
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});

		try {
			// Create MimeMessage
			MimeMessage message = new MimeMessage(session);

			// Set sender and recipient
			message.setFrom(new InternetAddress(senderEmail));
			InternetAddress[] multirecipientEmail = InternetAddress.parse(recipientEmail);
			message.addRecipients(Message.RecipientType.TO, multirecipientEmail);

			// Set email subject
			message.setSubject(PropertyLoad.getFile().getProperty("refund_subject"));

			// Build email content with dynamic values
			String merchantName = refundRequest.getMerchantName();
			String transactionId = refundRequest.getTxnId();
			String transactionDate = transactiondate;
			String refundAmount = refundRequest.getRequestRefundAmount();
			String paymentMethod = refundRequest.getProductType();
			String emailContent = buildRefundEmailContent(merchantName, transactionId, transactionDate, refundAmount,
					paymentMethod);

			// Set email content
			message.setText(emailContent);

			// Send email
			Transport.send(message);

			logger.info("Email sent successfully.");

		} catch (MessagingException e) {
			e.printStackTrace(); // Consider logging the error instead
			logger.error("Error sending email. Please try again later.");
		} finally {
			try {
				if (session != null) {
					Transport transport = session.getTransport("smtp");
					transport.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("nls")
	private static String buildRefundEmailContent(String merchantName, String transactionId, String transactionDate,
			String refundAmount, String paymentMethod) {
		return String.format("Dear %s,\n\n"
				+ "This is to inform you that a refund transaction has been successfully processed with the following details:\n\n"
				+ "Detailed Transaction:\n\n" + "Transaction ID: %s\n" + "Date of Transaction: %s\n"
				+ "Refund Amount: %s\n" + "Refund Status: Success\n" + "Payment Method: %s\n\n"
				+ "For any inquiries or support, reach out to us on csmobi@gomobi.io\n\n"
				+ "This is an auto-notification email. Please do not reply.\n\n" + "Regards,\n"
				+ "Mobi Asia Sdn. Bhd.\n" + "Website: https://gomobi.io", merchantName, transactionId, transactionDate,
				refundAmount, paymentMethod);
	}

	@SuppressWarnings("nls")
	public static void sendVoidNotificationEmail(RefundRequest refundRequest, String transactiondate,
			String merchantEmail) {
		String senderEmail = PropertyLoad.getFile().getProperty("senderEmail");
		String senderPassword = PropertyLoad.getFile().getProperty("senderPassword");
		String recipientEmail = merchantEmail + "," + PropertyLoad.getFile().getProperty("recipientEmail");

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.user", PropertyLoad.getFile().getProperty("fromName"));
		properties.put("mail.smtp.host", PropertyLoad.getFile().getProperty("mail.smtp.host"));
		properties.put("mail.smtp.port", PropertyLoad.getFile().getProperty("mail.smtp.port"));
		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, senderPassword);
			}
		});

		try {
			// Create MimeMessage
			MimeMessage message = new MimeMessage(session);

			// Set sender and recipient
			message.setFrom(new InternetAddress(senderEmail));
			InternetAddress[] multirecipientEmail = InternetAddress.parse(recipientEmail);
			message.addRecipients(Message.RecipientType.TO, multirecipientEmail);

			// Set email subject
			message.setSubject(PropertyLoad.getFile().getProperty("void_subject"));

			// Build email content with dynamic values
			String merchantName = refundRequest.getMerchantName();
			String transactionId = refundRequest.getTxnId();
			String transactionDate = transactiondate;
			String voidAmount = refundRequest.getRequestRefundAmount();
			String paymentMethod = refundRequest.getProductType();
			String emailContent = buildVoidEmailContent(merchantName, transactionId, transactionDate, voidAmount,
					paymentMethod);

			// Set email content
			message.setText(emailContent);

			// Send email
			Transport.send(message);

			logger.info("Email sent successfully.");

		} catch (MessagingException e) {
			e.printStackTrace(); // Consider logging the error instead
			logger.error("Error sending email. Please try again later.");
		} finally {
			try {
				if (session != null) {
					Transport transport = session.getTransport("smtp");
					transport.close();
				}
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	}

	@SuppressWarnings("nls")
	private static String buildVoidEmailContent(String merchantName, String transactionId, String transactionDate,
			String voidAmount, String paymentMethod) {
		return String.format("Dear %s,\n\n"
				+ "This is to inform you that a void transaction has been successfully processed with the following details:\n\n"
				+ "Detailed Transaction:\n\n" + "Transaction ID: %s\n" + "Date of Transaction: %s\n"
				+ "Void Amount: %s\n" + "Void Status: Success\n" + "Payment Method: %s\n\n"
				+ "For any inquiries or support, reach out to us on csmobi@gomobi.io\n\n"
				+ "This is an auto-notification email. Please do not reply.\n\n" + "Regards,\n"
				+ "Mobi Asia Sdn. Bhd.\n" + "Website: https://gomobi.io", merchantName, transactionId, transactionDate,
				voidAmount, paymentMethod);
	}

	public static void main(String[] args) {

		// Test the API call here if needed.

	}

}
