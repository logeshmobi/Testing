package com.mobiversa.payment.util;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

public class FinanceReportUtils {
	
	private static Logger logger = Logger.getLogger(EmailUtils.class);

	@SuppressWarnings("nls")
	public static void sendEmailWithAttachments(Map<String, byte[]> attachments, String fromDate, String toDate,
			String emailID) {
		 logger.info("Inside sendEmailWithAttachments");

		String senderEmail = PropertyLoad.getFile().getProperty("PAYIN_REPORT_XLS_EXPORT_SENDER_EMAIL");
		String senderPassword = PropertyLoad.getFile().getProperty("PAYIN_REPORT_XLS_EXPORT_SENDER_PASSWORD");
		String recipientEmail = PropertyLoad.getFile().getProperty("PAYIN_REPORT_XLS_EXPORT_TO") + "," + emailID;
		 logger.info("senderEmail:::" +senderEmail);
		 logger.info("senderPassword:::"+senderPassword);
		 logger.info("recipientEmail:::"+recipientEmail);

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
			message.setSubject("Transactions Report");

			// Create a multipart message
			Multipart multipart = new MimeMultipart();

			// Add text part
			/*
			 * String emailContent; emailContent = "Dear Team,\n" +
			 * "Please find attached the Payout and Payin transaction report covering the period from "
			 * + formatDateAsMMMMdyyyy(fromDate) + ", to " + formatDateAsMMMMdyyyy(toDate) +
			 * ".\n\n\n\n\n\n\n\n\n\n\n" +
			 * "Disclaimer: The content of this email, including any attached CSV files, is intended solely for internal use within Mobi. "
			 * +
			 * "If you have received this email in error, please notify the sender immediately and delete it from your system. Any unauthorized "
			 * +
			 * "copying, distribution, or disclosure of the content is strictly prohibited. Mobi accepts no liability for any damage caused by any "
			 * + "virus transmitted by this email.";
			 */
			
			   String emailContent;
			   if (attachments.size() == 1 && attachments.containsKey("PayoutTransactionReport.xls")) {
				    emailContent = "Dear Team,\n"
				            + "Please find attached the Payout transaction report for all Merchants covering the period from "
				            + formatDateAsMMMMdyyyy(fromDate) + " to " + formatDateAsMMMMdyyyy(toDate)
				            + ".\n\n\n\n\n\n\n\n\n\n\n"
				            + "Disclaimer: The content of this email, including any attached files, is intended solely for internal use within Mobi. "
				            + "If you have received this email in error, please notify the sender immediately and delete it from your system. Any unauthorized "
				            + "copying, distribution, or disclosure of the content is strictly prohibited. Mobi accepts no liability for any damage caused by any "
				            + "virus transmitted by this email.";
				} else if (attachments.size() == 1 && attachments.containsKey("PayinTransactionReport.xls")) {
				    emailContent = "Dear Team,\n"
				            + "Please find attached the Payin transaction report covering the period from "
				            + formatDateAsMMMMdyyyy(fromDate) + " to " + formatDateAsMMMMdyyyy(toDate)
				            + ".\n\n\n\n\n\n\n\n\n\n\n"
				            + "Disclaimer: The content of this email, including any attached files, is intended solely for internal use within Mobi. "
				            + "If you have received this email in error, please notify the sender immediately and delete it from your system. Any unauthorized "
				            + "copying, distribution, or disclosure of the content is strictly prohibited. Mobi accepts no liability for any damage caused by any "
				            + "virus transmitted by this email.";
				} else {
				    emailContent = "Dear Team,\n"
				            + "Please find attached the Payout and Payin transaction report covering the period from "
				            + formatDateAsMMMMdyyyy(fromDate) + " to " + formatDateAsMMMMdyyyy(toDate)
				            + ".\n\n\n\n\n\n\n\n\n\n\n"
				            + "Disclaimer: The content of this email, including any attached CSV files, is intended solely for internal use within Mobi. "
				            + "If you have received this email in error, please notify the sender immediately and delete it from your system. Any unauthorized "
				            + "copying, distribution, or disclosure of the content is strictly prohibited. Mobi accepts no liability for any damage caused by any "
				            + "virus transmitted by this email.";
				}
		        
			MimeBodyPart textPart = new MimeBodyPart();
			textPart.setText(emailContent);
			multipart.addBodyPart(textPart);

			// Add attachments
			for (Map.Entry<String, byte[]> entry : attachments.entrySet()) {
				String fileName = entry.getKey();
				byte[] fileContent = entry.getValue();

				if (fileContent != null && fileContent.length > 0) {
					MimeBodyPart attachmentPart = new MimeBodyPart();
					DataSource attachmentSource = new ByteArrayDataSource(new ByteArrayInputStream(fileContent),
							"application/octet-stream");
					attachmentPart.setDataHandler(new DataHandler(attachmentSource));
					attachmentPart.setFileName(fileName);
					multipart.addBodyPart(attachmentPart);
				}
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

}
