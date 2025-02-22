package com.mobiversa.payment.dao;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;


public class SMSServiceImpl{

	private static Logger logger = Logger.getLogger(SMSServiceImpl.class);
	//private static Logger logger = Logger.getLogger(MobileEndpointController.class);

	private final static String smsServiceLink = "http://ic1.silverstreet.com/send.php";

	/**
	 * Generic sendSMS service for sending general purpose SMS
	 * 
	 * @param MSISDN
	 *            Mobile phone number, starts with 01
	 * @param content
	 *            Max size of an SMS content is 153 characters.
	 * @throws IOException
	 * @throws ClientProtocolException
	 * @throws Exception
	 *             so that GAE Task Queue could pick it up as HTTP 5xx response
	 *             and retry the Task
	 */
	public static void sendSMS(final String MSISDN, final String content) throws Exception {
		logger.info("about to send an sms"+MSISDN+"content:"+content);

		final CloseableHttpClient httpclient = HttpClients.createDefault();

		final HttpPost httppost = new HttpPost(smsServiceLink);

		final ArrayList<NameValuePair> nvpList = new ArrayList<NameValuePair>();
		nvpList.add(new BasicNameValuePair("body", content));
		nvpList.add(new BasicNameValuePair("destination", parsePhoneNumber(MSISDN)));
		nvpList.add(new BasicNameValuePair("password", "mobivers"));
		nvpList.add(new BasicNameValuePair("username", "mobiversa"));
		nvpList.add(new BasicNameValuePair("sender", "Mobiversa"));
		//nvpList.add(new BasicNameValuePair("dlr", "1"));// delivery report
		//nvpList.add(new BasicNameValuePair("DLR", "1"));// delivery report
		nvpList.add(new BasicNameValuePair("validity", "5"));
		nvpList.add(new BasicNameValuePair("reference", "" + new Date().getTime()));

		// for unicode (ie chinese characters)
		// nvpList.add(new BasicNameValuePair("bodytype", "4"));

		final UrlEncodedFormEntity entityRequest = new UrlEncodedFormEntity(nvpList);
		httppost.setEntity(entityRequest);
		/*if (logger.isLoggable(Level.FINE)) {
			logger.fine("Sending SMS with param: " + nvpList);
		}*/
		
		//if (logger.isLoggable(Level.FINE)) {
			logger.debug("Sending SMS with param: " + nvpList);
		//}

		final CloseableHttpResponse response = httpclient.execute(httppost);
		final HttpEntity entityResp = response.getEntity();

		final StringWriter writer = new StringWriter();
		IOUtils.copy(entityResp.getContent(), writer, "UTF-8");
		final String smsHostResponse = writer.toString();
		EntityUtils.consume(entityResp);
		response.close();
		httppost.releaseConnection();
		httpclient.close();
		if ("01".equals(smsHostResponse)) {
			// success
			logger.info("SMS is successfully sent");
		} else {
			//throw new RuntimeException("SMS service provider return error code " + smsHostResponse);
		}

		// alternative
		// HttpResponse test = Request.Post("http://targethost/login")
		// .bodyForm(Form.form().add("username", "vip").add("password",
		// "secret").build())
		// .execute().returnResponse();
	}

	
	
	private static String parsePhoneNumber(String recipientMobile) {
		recipientMobile = recipientMobile.replaceAll("[+ -]", "");
		if (recipientMobile.startsWith("0")) {
			// prefix with malaysia country code
			recipientMobile = "6" + recipientMobile;
		}
		return recipientMobile;
	}

}
