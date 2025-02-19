package com.mobiversa.payment.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
public class AEEmailUtils {

	private static Logger logger = Logger.getLogger(AEEmailUtils.class);
	
	@SuppressWarnings("nls")
	public static boolean sendFpxTransactionSuccessEmail(String mid, String amount, String txndate, String settledDate,
	        String sellerOrderNo, String mdrAmt, String netAmt, String fpxTxnId) {
	    boolean response = false;

	    try {
	        String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL1");
	        String toAddress = PropertyLoad.getFile().getProperty("FPX_SUCCESS_MAIL_TO");
	        String ccAddress = PropertyLoad.getFile().getProperty("FPX_SUCCESS_MAIL_TO");
	        String subject = "FPX Transaction Success Notification Order No: " + sellerOrderNo;
	        String fromName = PropertyLoad.getFile().getProperty("FROMNAME");

	        logger.info(String.format(
	                "FPX Success Email Sending: From [%s], To [%s], CC [%s], Subject [%s], FromName [%s]; MID [%s]; Seller Order No [%s]; Amount [%s]; Transaction Date [%s]; Settled Date [%s]; MDR Amount [%s]; Net Amount [%s]; FPX Transaction ID [%s]",
	                fromAddress, toAddress, ccAddress, subject, fromName, mid, sellerOrderNo, amount, txndate, settledDate, 
	                mdrAmt, netAmt, fpxTxnId));

	        StringBuilder emailContent = new StringBuilder();
	        emailContent.append("<!DOCTYPE html>").append("<html lang=\"en\">")
	        .append("<head>")
	        .append("<meta charset=\"UTF-8\">")
	        .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
	        .append("<title>Transaction Details</title>")
	        .append("<style>")
	        .append("body { font-family: 'Poppins', sans-serif; background-color: #ffffff; margin: 0; padding: 0; }")
	        .append(".container { background-color: #f5f5f5; width: 100%; max-width: 600px; margin: 20px auto; padding: 20px; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); box-sizing: border-box; }")
	        .append("h1 { color: #005baa; font-size: 1.2rem; margin-bottom: 5px;}")  // Added margin-bottom for spacing
	        .append(".details { width: 100%; border-collapse: collapse; margin: 10px 0; }")
	        .append(".details td { padding: 10px; font-weight: 500; }")
	        .append(".details .left_text { padding-left: 0px; white-space: nowrap; vertical-align: baseline; }")
	        .append(".details .right_text { font-weight: normal; }")
	        .append(".details .colon { width: 10px; white-space: nowrap; vertical-align: baseline; }")
	        .append(".space { margin-top: 15px; }") // Added space class for extra spacing
	        .append(".spaced-text { word-spacing: 0.3em; }")  // Add custom class for word spacing
	        .append(".font-size-custom { font-size: 1.05rem; }")  
	        .append(".line-spaced { line-height: 1.5em; }") 
	        .append("</style>")
	        .append("</head>")
	        .append("<body>")
	        .append("<div class=\"container\">")
	        
	        // Add space between "Dear Team" and the next line
	        .append("<p class=\"font-size-custom\">Dear Team,</p>")
	        .append("<p class=\"line-spaced font-size-custom\">The FPX transaction associated with the following details has been successfully processed:</p>")
	        .append("<div class=\"space\"></div>")  // Space after this line
	        
	        // Add space before and after the "Transaction Details" heading
	        .append("<h1>Transaction Details</h1>") 
	        .append("<table class=\"details\">")
	        .append("<tr><td class=\"left_text\">MID</td><td class=\"colon\">:</td><td class=\"right_text\">").append(mid).append("</td></tr>")
	        .append("<tr><td class=\"left_text\">Transaction Amount</td><td class=\"colon\">:</td><td class=\"right_text\">RM ").append(amount).append("</td></tr>")
	        .append("<tr><td class=\"left_text\">Net Amount</td><td class=\"colon\">:</td><td class=\"right_text\">RM ").append(netAmt).append("</td></tr>")
	        .append("<tr><td class=\"left_text\">MDR Amount</td><td class=\"colon\">:</td><td class=\"right_text\">RM ").append(mdrAmt).append("</td></tr>")
	        .append("<tr><td class=\"left_text\">Transaction Date</td><td class=\"colon\">:</td><td class=\"right_text\">").append(txndate).append("</td></tr>")
	        .append("<tr><td class=\"left_text\">Settlement Date</td><td class=\"colon\">:</td><td class=\"right_text\">").append(settledDate).append("</td></tr>")
	        .append("<tr><td class=\"left_text\">FPX Transaction ID</td><td class=\"colon\">:</td><td class=\"right_text\">").append(fpxTxnId).append("</td></tr>")
	        .append("<tr><td class=\"left_text\">Seller Order No</td><td class=\"colon\">:</td><td class=\"right_text\">").append(sellerOrderNo).append("</td></tr>")
	        .append("</table>")
	        
	        // Space between the table and the next paragraph
	        .append("<div class=\"space\"></div>")
	        
	        // Adding word-spacing and line spacing in the final section
	        .append("<p class=\"line-spaced font-size-custom\">This successful transaction has been settled and will reflect accordingly.<br>")
	        .append("Should anyone have further questions or need clarification, please don't hesitate to reach out.</p>")
	        .append("<p class=\"font-size-custom\">Best regards,</p>")
	        .append("<p class=\"font-size-custom\">Mobi Engineering Team</p>")
	        .append("<div class=\"space\"></div>")  // Add space after the signature

	        .append("</div>")
	        .append("</body>")
	        .append("</html>");



	        int mailResponse = sendemailSlipforFpx(fromAddress, subject, fromName, toAddress, ccAddress, null, emailContent.toString());

	        if (mailResponse == 200) {
	            logger.info("Email sent successfully to " + toAddress);
	            response = true;
	        } else {
	            logger.error("Failed to send email to " + toAddress);
	        }
	    } catch (Exception e) {
	        logger.error("Error in sending FPX Transaction Success Email: " + e.getMessage(), e);
	    }

	    return response;
	}
	
	public static int sendemailSlipforFpx(String fromEmail, String subject, String fromName, String msgto, String cc,
			String bcc, String bodyHtml) {

		Gson gson = new Gson();
		String inputLine = null;
		String output = null;
		URL url;
		int responseCode = 0;
		try {
			String elasticemailurl = PropertyLoad.getFile().getProperty("ELASTIC_EMAIL_SLIP_FPX");
			logger.info("Elastic url FPX " + elasticemailurl);
			url = new URL(elasticemailurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			JSONObject jsonParams = new JSONObject();

			jsonParams.put("fromEmail", fromEmail);
			jsonParams.put("subject", subject);
			jsonParams.put("fromName", fromName);
			jsonParams.put("msgto", msgto);
			jsonParams.put("cc", cc);
			jsonParams.put("bcc", bcc);
			jsonParams.put("bodyHtml", bodyHtml);

			logger.info("fromEmai " + fromEmail + " subject " + subject + " fromname " + fromName + " msgto " + msgto
					+ " cc " + cc + " bcc " + bcc);

			OutputStream os = conn.getOutputStream();
			os.write(jsonParams.toString().getBytes());
			os.flush();

			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

			responseCode = conn.getResponseCode();
			logger.info("responseCode .... " + responseCode);
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			output = response.toString();
			logger.info(" Output from Server .... " + output);

			return responseCode;

		} catch (MalformedURLException e) {
	        logger.error("MalformedURLException occurred: " + e.getMessage(), e);
	    } catch (IOException e) {
	        logger.error("IOException occurred while connecting to Elastic Email API: " + e.getMessage(), e);
	    } catch (JSONException e) {
	        logger.error("JSONException occurred while building JSON payload: " + e.getMessage(), e);
	    } catch (Exception e) {
	        logger.error("Unexpected error occurred: " + e.getMessage(), e);
	    }
		 return responseCode;
    }
	
}
