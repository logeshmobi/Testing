package com.mobiversa.payment.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.log4j.Logger;

import com.mobiversa.common.bo.Merchant;

public class EmailUtils {

	private static Logger logger = Logger.getLogger(EmailUtils.class);

	@SuppressWarnings("nls")
	public static void sendEmailWithAttachment(byte[] emailAttachment, String fromDate, String toDate, String emailID) {

		String senderEmail = PropertyLoad.getFile().getProperty("PAYOUT_USER_CSV_EXPORT_SENDER_EMAIL");
		String senderPassword = PropertyLoad.getFile().getProperty("PAYOUT_USER_CSV_EXPORT_SENDER_PASSWORD");
		String recipientEmail = PropertyLoad.getFile().getProperty("PAYOUT_USER_CSV_EXPORT_TO") + "," + emailID;

//		String senderEmail = "mobitest@gomobi.io";
//		String senderPassword = "BMO123ver$@!@#$8765kdm2!";
//		String recipientEmail = "kavin@gomobi.io";

//		String[] filePaths = filePathsBuilder.toString().split(",");

//		Properties properties = new Properties();
//		properties.put("mail.smtp.auth", "true");
//		properties.put("mail.smtp.starttls.enable", "true");
//		properties.put("mail.smtp.user", "mobi");
//		properties.put("mail.smtp.host", "smtp.office365.com");
//		properties.put("mail.smtp.port", "587");
//		properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

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
			InternetAddress[] multiRecipientEmail = InternetAddress.parse(recipientEmail);
			message.addRecipients(Message.RecipientType.TO, multiRecipientEmail);

			// Set email subject
			message.setSubject("Payout transaction Report");

			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			// Add text part
			String emailContent = "Dear Team,\n"
					+ "Please find attached the Payout transaction report covering the period from "
					+ formatDateAsMMMMdyyyy(fromDate) + ", to " + formatDateAsMMMMdyyyy(toDate)
					+ ".\n\n\n\n\n\n\n\n\n\n\n"
					+ "Disclaimer: The content of this email, including any attached CSV files, is intended solely for internal use within Mobi. "
					+ "If you have received this email in error, please notify the sender immediately and delete it from your system. Any unauthorized "
					+ "copying, distribution, or disclosure of the content is strictly prohibited. Mobi accepts no liability for any damage caused by any "
					+ "virus transmitted by this email.";
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText(emailContent);
			multipart.addBodyPart(textPart);

			// Add attachments
			if (emailAttachment != null && emailAttachment.length > 0) {
				MimeBodyPart attachmentPart = new MimeBodyPart();
				DataSource attachmentSource = new ByteArrayDataSource(new ByteArrayInputStream(emailAttachment),
						"application/octet-stream");
				attachmentPart.setDataHandler(new DataHandler(attachmentSource));
				attachmentPart.setFileName("PayoutTransactionReport.csv");
				multipart.addBodyPart(attachmentPart);
			}

			// Set the content of the message to the multipart
			message.setContent(multipart);

			// Send email
			Transport.send(message);

			logger.info("Email sent successfully.");
		} catch (MessagingException | IOException e) {
			e.printStackTrace();
			logger.error("Error sending email. Please try again later." + e);
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

	private static String buildEmailContent(String merchantName, String settlementDate) {
		return String.format("Dear %s,\n\n" + "Please find attached your Mobi merchant settlement report for %s.\n\n"
				+ "Disclaimer: This report is prepared based on the information available on the Mobi system as of the date of its generation.",
				merchantName, settlementDate);
	}

	public static String formatDateAsMMMMdyyyy(String inputDate) {
		LocalDate date = LocalDate.parse(inputDate);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.ENGLISH);
		return date.format(outputFormatter);
	}

	@SuppressWarnings("nls")
	public static String sendPayoutAsyncHandlerEmail(Merchant merchantDetail, String isAsyncPayoutEnabled,
			String payoutAsyncIpnUrl, String enablingAsyncPayoutReason) {


		if (isAsyncPayoutEnabled.toUpperCase().equals("YES")) {
			try {
				String split = "'SPLIT'";

				// Concatenate values
				String concatenatedUrlString = isAsyncPayoutEnabled + split + payoutAsyncIpnUrl + split
						+ enablingAsyncPayoutReason + split + String.valueOf(merchantDetail.getId()) + split
						+ merchantDetail.getBusinessName();

				// Encode with Base64
				String encryptedParam = Base64.getEncoder().encodeToString(concatenatedUrlString.getBytes());

				// Construct the final encrypted URL
				encryptedParam = PropertyLoad.getFile().getProperty("ASYNC_PAYOUT_APPROVAL_LINK") + encryptedParam;

				logger.info(
						"Concatenated Url String: " + PropertyLoad.getFile().getProperty("ASYNC_PAYOUT_APPROVAL_LINK")
								+ concatenatedUrlString + ", EncryptedParam: " + encryptedParam);

				String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");
				String toAddress = PropertyLoad.getFile().getProperty("PAYOUT_ASYNC_HANDLER_MAIL_TO");
				String ccAddress = PropertyLoad.getFile().getProperty("PAYOUT_ASYNC_HANDLER_MAIL_TO");
				String subject = "Request for Approval to Enable Asynchronous Payout for "
						+ merchantDetail.getBusinessName();
				String fromName = PropertyLoad.getFile().getProperty("FROMNAME");

				logger.info("Triggering email to request approval to enable asynchronous payout for: "
						+ merchantDetail.getBusinessName() + ", from: " + fromAddress + ", to: " + toAddress);

				String approvalLink = "<a href=\"" + encryptedParam
						+ "\" style=\"color: #005baa; font-weight: bold;\">Approve Asynchronous Payout</a>";

				StringBuilder emailContent = new StringBuilder();
				emailContent.append("<!DOCTYPE html>").append("<html lang=\"en\">").append("<head>")
				    .append("    <meta charset=\"UTF-8\">")
				    .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
				    .append("    <title>Request for Approval to Enable Asynchronous Payout</title>")
				    .append("    <style>")
				    .append("        body {")
				    .append("            font-family: 'Poppins', sans-serif;")
				    .append("            background-color: #ffffff;")
				    .append("            margin: 0;")
				    .append("            padding: 0;")
				    .append("        }")
				    .append("        .container {")
				    .append("            background-color: #f5f5f5;")
				    .append("            width: 100%;")
				    .append("            max-width: 600px;")
				    .append("            margin: 20px auto;")
				    .append("            padding: 20px;")
				    .append("            border-radius: 10px;")
				    .append("            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);")
				    .append("            box-sizing: border-box;")
				    .append("        }")
				    .append("        h1 {")
				    .append("            color: #005baa;")
				    .append("            font-size: 1.75rem;")
				    .append("        }")
				    .append("        .title {")
				    .append("            color: #43c63f;")
				    .append("            font-size: 1.5rem;")
				    .append("            font-weight: 600;")
				    .append("            margin-bottom: 20px;")
				    .append("        }")
				    .append("        .details {")
				    .append("            width: 100%;")
				    .append("            border-collapse: collapse;")
				    .append("            margin: 10px 0;")
				    .append("        }")
				    .append("        .details td {")
				    .append("            padding: 10px;")
				    .append("            font-weight: 500;")
				    .append("        }")
				    .append("        .details .left_text {")
				    .append("            padding-left: 0px;")
				    .append("            white-space: nowrap;")
				    .append("            vertical-align: baseline;")
				    .append("        }")
				    .append("        .details .right_text {")
				    .append("            font-weight: normal;")
				    .append("        }")
				    .append("        .details .colon {")
				    .append("            width: 10px;")
				    .append("            vertical-align: baseline;")
				    .append("        }")
				    .append("        .note, .contact {")
				    .append("            font-size: 14px;")
				    .append("            margin: 10px 0;")
				    .append("        }")
				    .append("        .fw-bold {")
				    .append("            font-weight: 600 !important;")
				    .append("        }")
				    .append("        p {")
				    .append("            margin: 10px 0;")
				    .append("        }")
				    .append("        @media (max-width: 600px) {")
				    .append("            h1 {")
				    .append("                font-size: 1.4rem;")
				    .append("            }")
				    .append("            .title {")
				    .append("                font-size: 1.2rem;")
				    .append("            }")
				    .append("            .details td {")
				    .append("                font-size: 12px;")
				    .append("                padding: 6px;")
				    .append("            }")
				    .append("            .note, .contact {")
				    .append("                font-size: 12px;")
				    .append("            }")
				    .append("        }")
				    .append("    </style>").append("</head>").append("<body>")
				    .append("    <div class=\"container\">")
				    .append("        <h1>Request to Enable Asynchronous Payout</h1>")
//				    .append("        <div style=\"color:#005baa !important\" class=\"title\">Merchant Details:</div>")
				    .append("        <table class=\"details\">")
				    .append("            <tr>")
				    .append("                <td class=\"left_text\">Merchant Name</td>")
				    .append("                <td class=\"colon\">:</td>")
				    .append("                <td class=\"right_text\">").append(merchantDetail.getBusinessName()).append("</td>")
				    .append("            </tr>")
				    .append("            <tr>")
				    .append("                <td class=\"left_text\">Payout Notification URL</td>")
				    .append("                <td class=\"colon\">:</td>")
				    .append("                <td class=\"right_text\">").append(payoutAsyncIpnUrl).append("</td>")
				    .append("            </tr>")
//				    .append("            <tr>")
//				    .append("                <td class=\"left_text\">Reason for Enabling Async Payout</td>")
//				    .append("                <td class=\"colon\">:</td>")
//				    .append("                <td class=\"right_text\">").append(enablingAsyncPayoutReason).append("</td>")
//				    .append("            </tr>")
				    .append("        </table>")
				    .append("        <div class=\"contact\">")
				    .append("            <p><strong>Network Team:</strong> Please review the request and whitelist the provided IPN. Once the IPN has been successfully whitelisted, proceed to click the approval link: ").append(approvalLink).append("</p>")
				    .append("            <p><strong>CS Team:</strong> Please be informed that we have initiated the approval process for enabling asynchronous payout for ").append(merchantDetail.getBusinessName()).append(".</p>")
				    .append("        </div>")
				    .append("        <div class=\"contact\"><br>")
				    .append("            <p class=\"fw-bold\">Best regards,</p>")
				    .append("            <p>Mobi team.</p>")
				    .append("        </div>")
				    .append("    </div>")
				    .append("</body>")
				    .append("</html>");

				int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress,
						ccAddress, null, emailContent.toString());

				logger.info("Email Notification: Successfully sent to '{" + toAddress + "}'. Response: {" + mailResponse
						+ "}");

				return String.valueOf(mailResponse);
			} catch (Exception pe) {
				logger.info("Failed to send Email Notification: " + pe.getMessage() + pe);
				return "500";
			}
		}
		return "400";
	
	}

	  public static String sendIPNConfigurationEmail(Merchant merchantDetail, String ipAddress, Map<String, String> ipnDetails, Map<String, String> midDetails) {
	        try {
	            String split = "'SPLIT'";

	            // Construct the concatenated string
	            StringBuilder concatenatedStringBuilder = new StringBuilder();
	            concatenatedStringBuilder.append(ipAddress).append(split)
	                    .append(String.valueOf(merchantDetail.getId())).append(split)
	                    .append(merchantDetail.getBusinessName());
	            
	            // Append each MID if available
	            midDetails.forEach((paymentMethod, mid) -> {
	                if (mid != null && !mid.isEmpty()) {
	                    concatenatedStringBuilder.append(split).append("MID(").append(paymentMethod).append("):").append(mid);
	                }
	            });

	            
	            ipnDetails.forEach((paymentMethod, ipnUrl) -> {
	                concatenatedStringBuilder.append(split).append(paymentMethod).append(":").append(ipnUrl);
	            });

	            // Encode with Base64
	            String encryptedParam = Base64.getEncoder().encodeToString(concatenatedStringBuilder.toString().getBytes());
	            encryptedParam = PropertyLoad.getFile().getProperty("IPN_CONFIG_APPROVAL_LINK") + encryptedParam;

	            logger.info("Concatenated Url String: " + PropertyLoad.getFile().getProperty("IPN_CONFIG_APPROVAL_LINK")
	                    + concatenatedStringBuilder + ", EncryptedParam: " + encryptedParam);

	            String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL1");
	            String toAddress = PropertyLoad.getFile().getProperty("IPN_CONFIG_MAIL_TO");
	            String ccAddress = PropertyLoad.getFile().getProperty("IPN_CONFIG_MAIL_TO");
	            String subject = "IPN Configuration Update for " + merchantDetail.getBusinessName();
	            String fromName = PropertyLoad.getFile().getProperty("FROMNAME");

	            logger.info("Triggering email for IPN configuration update for: "
	                    + merchantDetail.getBusinessName() + ", from: " + fromAddress + ", to: " + toAddress);

	            String approvalLink = "<a href=\"" + encryptedParam
	                    + "\" style=\"color: #005baa; font-weight: bold;\">Approve IPN Configuration</a>";

	            StringBuilder emailContent = new StringBuilder();
	            emailContent.append("<!DOCTYPE html>").append("<html lang=\"en\">").append("<head>")
	                    .append("    <meta charset=\"UTF-8\">")
	                    .append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
	                    .append("    <title>IPN Configuration Update</title>")
	                    .append("    <style>")
	                    .append("        body {")
	                    .append("            font-family: 'Poppins', sans-serif;")
	                    .append("            background-color: #ffffff;")
	                    .append("            margin: 0;")
	                    .append("            padding: 0;")
	                    .append("        }")
	                    .append("        .container {")
	                    .append("            background-color: #f5f5f5;")
	                    .append("            width: 100%;")
	                    .append("            max-width: 600px;")
	                    .append("            margin: 20px auto;")
	                    .append("            padding: 20px;")
	                    .append("            border-radius: 10px;")
	                    .append("            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);")
	                    .append("            box-sizing: border-box;")
	                    .append("        }")
	                    .append("        h1 {")
	                    .append("            color: #005baa;")
	                    .append("            font-size: 1.75rem;")
	                    .append("        }")
	                    .append("        .title {")
	                    .append("            color: #43c63f;")
	                    .append("            font-size: 1.5rem;")
	                    .append("            font-weight: 600;")
	                    .append("            margin-bottom: 20px;")
	                    .append("        }")
	                    .append("        .details {")
	                    .append("            width: 100%;")
	                    .append("            border-collapse: collapse;")
	                    .append("            margin: 10px 0;")
	                    .append("        }")
	                    .append("        .details td {")
	                    .append("            padding: 10px;")
	                    .append("            font-weight: 500;")
	                    .append("        }")
	                    .append("        .details .left_text {")
	                    .append("            padding-left: 0px;")
	                    .append("            white-space: nowrap;")
	                    .append("            vertical-align: baseline;")
	                    .append("        }")
	                    .append("        .details .right_text {")
	                    .append("            font-weight: normal;")
	                    .append("        }")
	                    .append("        .details .colon {")
	                    .append("            width: 10px;")
	                    .append("            vertical-align: baseline;")
	                    .append("        }")
	                    .append("        .note, .contact {")
	                    .append("            font-size: 14px;")
	                    .append("            margin: 10px 0;")
	                    .append("        }")
	                    .append("        .fw-bold {")
	                    .append("            font-weight: 600 !important;")
	                    .append("        }")
	                    .append("        p {")
	                    .append("            margin: 10px 0;")
	                    .append("        }")
	                    .append("        @media (max-width: 600px) {")
	                    .append("            h1 {")
	                    .append("                font-size: 1.4rem;")
	                    .append("            }")
	                    .append("            .title {")
	                    .append("                font-size: 1.2rem;")
	                    .append("            }")
	                    .append("            .details td {")
	                    .append("                font-size: 12px;")
	                    .append("                padding: 6px;")
	                    .append("            }")
	                    .append("            .note, .contact {")
	                    .append("                font-size: 12px;")
	                    .append("            }")
	                    .append("        }")
	                    .append("    </style>");

	            emailContent.append("</head>").append("<body>")
	                    .append("    <div class=\"container\">")
	                    .append("        <h1>IPN Configuration Update</h1>")
	                    .append("        <div class=\"title\">Merchant: ").append(merchantDetail.getBusinessName()).append("</div>")
	                    .append("        <table class=\"details\">")
	                    .append("            <tr>")
	                    .append("                <td class=\"left_text\">IP Address</td>")
	                    .append("                <td class=\"colon\">:</td>")
	                    .append("                <td class=\"right_text\">").append(ipAddress).append("</td>")
	                    .append("            </tr>")
	                    .append("            <tr>")
	                    .append("                <td class=\"left_text\">Merchant ID</td>")
	                    .append("                <td class=\"colon\">:</td>")
	                    .append("                <td class=\"right_text\">").append(merchantDetail.getId()).append("</td>")
	                    .append("            </tr>")
	                    .append("            <tr>")
	                    .append("                <td class=\"left_text\">Business Name</td>")
	                    .append("                <td class=\"colon\">:</td>")
	                    .append("                <td class=\"right_text\">").append(merchantDetail.getBusinessName()).append("</td>")
	                    .append("            </tr>");

	            ipnDetails.forEach((paymentMethod, ipnUrl) -> {
	                emailContent.append("            <tr>")
	                        .append("                <td class=\"left_text\">Payment Method</td>")
	                        .append("                <td class=\"colon\">:</td>")
	                        .append("                <td class=\"right_text\">").append(paymentMethod).append("</td>")
	                        .append("            </tr>")
	                        .append("            <tr>")
	                        .append("                <td class=\"left_text\">IPN URL</td>")
	                        .append("                <td class=\"colon\">:</td>")
	                        .append("                <td class=\"right_text\">").append(ipnUrl).append("</td>")
	                        .append("            </tr>");
	            });

	            emailContent.append("        </table>")
	                    .append("        <p class=\"note\">Please review the IPN configuration update for the above merchant.</p>")
	                    .append("        <p class=\"contact\">Contact support for any assistance.</p>")
	                    .append("        <p>").append(approvalLink).append("</p>")
	                    .append("    </div>")
	                    .append("</body>")
	                    .append("</html>");

				int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress,
						ccAddress, null, emailContent.toString());

				logger.info("Email Notification: Successfully sent to '{" + toAddress + "}'. Response: {" + mailResponse
						+ "}");

				return String.valueOf(mailResponse);


	        } catch (Exception e) {
	            logger.warn("Error sending email: " + e.getMessage());
	            return "Error sending email";
	        }
	    }
	  
	public String sendPayoutMaxTxnLimitHandlerEmailToOperation(Merchant merchantDetail, BigDecimal payoutMaxTxnLimit, String userName) {

		try {
			String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");
			String toAddress = PropertyLoad.getFile().getProperty("PAYOUT_MAX_TXN_LIMIT_APPROVAL");
			String ccAddress = PropertyLoad.getFile().getProperty("PAYOUT_MAX_TXN_LIMIT_APPROVAL");
			String subject = "Enabled Max Payout Transaction Limit for " + merchantDetail.getBusinessName();
			String fromName = PropertyLoad.getFile().getProperty("FROMNAME");

			logger.info("Triggering email enabled max transaction payout limit: " + merchantDetail.getBusinessName()
					+ ", from: " + fromAddress + ", to: " + toAddress);

			StringBuilder emailContent = new StringBuilder();
			emailContent.append("<!DOCTYPE html>");
			emailContent.append("<html lang=\"en\">");
			emailContent.append("<head>");
			emailContent.append("<meta charset=\"UTF-8\">");
			emailContent.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
			emailContent.append("<title>Maximum Payout Limit Exceeded</title>");
			emailContent.append("<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">");
			emailContent.append("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>");
			emailContent.append("<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap\" rel=\"stylesheet\">");
			emailContent.append("<style>@media (max-width: 600px) {");
			emailContent.append(".container { padding: 15px; margin: 20px auto !important; }");
			emailContent.append(".header td { padding-left: 2px !important; }");
			emailContent.append(".header img { width: 100px !important; height: 50px !important; }");
			emailContent.append(".title { font-size: 1.2rem !important; margin-bottom: 10px !important; }");
			emailContent.append(".details td { font-size: 10px !important; padding: 6px !important; padding-left: 2px !important; }");
			emailContent.append(".note, .contact { font-size: 10px !important; }}");
			emailContent.append("</style>");
			emailContent.append("</head>");
			emailContent.append("<body style=\"font-family: Poppins, sans-serif; background-color: #ffffff; margin: 0; padding: 0; text-align: center;\">");
			emailContent.append("<table class=\"container\" style=\"background-color: #f6f6f6; text-align: left; padding: 20px; border-radius: 10px; max-width: 550px; margin: 20px auto;\">");
			emailContent.append("<tr>");
			emailContent.append("<td>");
			emailContent.append("<table class=\"header\" style=\"width: 100%; text-align: left;\">");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding-left: 2px;\">");
			emailContent.append("<img src=\"https://portal.gomobi.io/MobiversaAdmin/resourcesNew/img/ElasticEmail-mobi.png\" width=\"110\" height=\"70\" style=\"width: 100px; height: 50px; clip-path: inset(0 10px 0 10px);\">");
			emailContent.append("</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"title\" style=\"width: 100%; text-align: left; color: #FF5722; font-size: 1.4rem !important; font-weight: bold; margin-bottom: 10px;\">");
			emailContent.append("<tr><td>Maximum Payout Transaction Limit Provided</td></tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"details\" style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">");
			emailContent.append("<tr><td style=\"padding: 5px; font-weight: bold; color: #333739;\">Merchant Name</td><td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">[Merchant Name]</td></tr>");
			emailContent.append("<tr><td style=\"padding: 5px; font-weight: bold; color: #333739;\">Merchant ID</td><td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">[Merchant ID]</td></tr>");
			emailContent.append("<tr><td style=\"padding: 5px; font-weight: bold; color: #333739;\">Payout Amount (MYR)</td><td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">[Payout Amount]</td></tr>");
			emailContent.append("<tr><td style=\"padding: 5px; font-weight: bold; color: #333739;\">Max Allowed Limit (MYR)</td><td>:</td>");
			emailContent.append("<td style=\"padding: 5px; color: #333739;\">[Max Allowed Limit]</td></tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"note\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">");
			emailContent.append("<tr><td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Action Completed</td></tr>");
			emailContent.append("<tr><td style=\"color: #333739;\">The payout request exceeding the maximum limit for this merchant has been approved via the portal.</td></tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"contact\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">");
			emailContent.append("<tr><td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Need Assistance? Contact Information</td></tr>");
			emailContent.append("<tr><td><a href=\"mailto:csmobi@gomobi.io\" style=\"text-decoration: none; color: #333739;\">csmobi@gomobi.io</a>, ");
			emailContent.append("<a style=\"color: #333739 !important; text-decoration: none;\"> +60 10-970-7880</a></td></tr>");
			emailContent.append("</table>");
			emailContent.append("</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("</body>");
			emailContent.append("</html>");

			int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, ccAddress,
					null, emailContent.toString());

			logger.info(
					"Email Notification: Successfully sent to '{" + toAddress + "}'. Response: {" + mailResponse + "}");

			return String.valueOf(mailResponse);
		} catch (Exception pe) {
			logger.info("Failed to send Email Notification: " + pe.getMessage() + pe);
			return "500";
		}
	}
	
	public String sendPayoutMaxTxnLimitHandlerEmailToMerchant(Merchant merchantDetail, BigDecimal payoutMaxTxnLimit, String userName) {

		try {
			String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");
			String toAddress = getSettlementEmailOrDefault(merchantDetail);
			String ccAddress = getSettlementEmailOrDefault(merchantDetail);
			String subject = "Enabled Max Payout Transaction Limit for " + merchantDetail.getBusinessName();
			String fromName = PropertyLoad.getFile().getProperty("FROMNAME");

			logger.info("Triggering email enabled max transaction payout limit: " + merchantDetail.getBusinessName()
					+ ", from: " + fromAddress + ", to: " + toAddress);

			StringBuilder emailContent = new StringBuilder();
			emailContent.append("<!DOCTYPE html>");
			emailContent.append("<html lang=\"en\">");
			emailContent.append("<head>");
			emailContent.append("<meta charset=\"UTF-8\">");
			emailContent.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
			emailContent.append("<title>Maximum Payout Limit Update</title>");
			emailContent.append("<link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">");
			emailContent.append("<link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>");
			emailContent.append("<link href=\"https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600&display=swap\" rel=\"stylesheet\">");
			emailContent.append("<style>");
			emailContent.append("@media (max-width: 600px) {");
			emailContent.append(".container { padding: 15px; margin: 20px auto !important; }");
			emailContent.append(".header td { padding-left: 2px !important; }");
			emailContent.append(".header img { width: 100px !important; height: 50px !important; }");
			emailContent.append(".title { font-size: 1.2rem !important; margin-bottom: 10px !important; }");
			emailContent.append(".details td { font-size: 10px !important; padding: 6px !important; padding-left: 2px !important; }");
			emailContent.append(".note, .contact { font-size: 10px !important; }");
			emailContent.append("}");
			emailContent.append(".highlight { font-weight: bold; color: #000000; font-size: 13px; }");
			emailContent.append("</style>");
			emailContent.append("</head>");
			emailContent.append("<body style=\"font-family: Poppins, sans-serif; background-color: #ffffff; margin: 0; padding: 0; text-align: center;\">");
			emailContent.append("<table class=\"container\" style=\"background-color: #f6f6f6; text-align: left; padding: 20px; border-radius: 10px; max-width: 550px; margin: 20px auto;\">");
			emailContent.append("<tr>");
			emailContent.append("<td>");
			emailContent.append("<table class=\"header\" style=\"width: 100%; text-align: left;\">");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"padding-left: 2px;\">");
			emailContent.append("<img src=\"https://portal.gomobi.io/MobiversaAdmin/resourcesNew/img/ElasticEmail-mobi.png\" width=\"110\" height=\"70\" style=\"width: 100px; height: 50px; clip-path: inset(0 10px 0 10px);\">");
			emailContent.append("</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"title\" style=\"width: 100%; text-align: left; color: #FF5722; font-size: 1.4rem !important; font-weight: bold; margin-bottom: 10px;\">");
			emailContent.append("<tr>");
			emailContent.append("<td>Maximum Payout Transaction Limit Updated</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"note\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"font-weight: bold; color: #333739;\">Dear [Merchant Name],</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"color: #333739;\">We would like to inform you that the maximum payout transaction limit for your account has been updated to <strong class=\"highlight\">[Maximum Limit Value]</strong>. Payouts exceeding this limit will now require approval before processing. </td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"instructions\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"font-weight: bold; color: #333739;\">Next Steps</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td style=\"color: #333739;\">Please ensure that your future payout requests comply with the new limit. If a request exceeds the limit, it will need to be approved manually.</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("<table class=\"contact\" style=\"width: 100%; font-size: 14px; margin-bottom: 10px; text-align: left; line-height: 1.6;\">");
			emailContent.append("<tr>");
			emailContent.append("<td class=\"fw-bold\" style=\"font-weight: bold; color: #333739;\">Need Assistance? Contact Information</td>");
			emailContent.append("</tr>");
			emailContent.append("<tr>");
			emailContent.append("<td>");
			emailContent.append("<a href=\"mailto:csmobi@gomobi.io\" style=\"text-decoration: none; color: #333739;\">csmobi@gomobi.io</a>, <a style=\"color: #333739 !important; text-decoration: none;\"> +60 10-970-7880</a>");
			emailContent.append("</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("</td>");
			emailContent.append("</tr>");
			emailContent.append("</table>");
			emailContent.append("</body>");
			emailContent.append("</html>");

			int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, ccAddress, null, emailContent.toString());

			logger.info("Email Notification: Successfully sent to '{" + toAddress + "}'. Response: {" + mailResponse + "}");

			return String.valueOf(mailResponse);
		} catch (Exception pe) {
			logger.info("Failed to send Email Notification: " + pe.getMessage() + pe);
			return "500";
		}
	}
	
	private static String getSettlementEmailOrDefault(Merchant merchantDetail) {
	    String email = merchantDetail.getSettlementEmail();
	    return (email != null) ? email : "kavin@gomobi.io";
	}

}
