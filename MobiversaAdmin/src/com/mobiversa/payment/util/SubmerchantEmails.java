package com.mobiversa.payment.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.mobiversa.common.bo.Merchant;

public class SubmerchantEmails {
	private static final Logger logger = Logger.getLogger(SubmerchantEmails.class.getName());

	public void sendEmailtoRiskAndCompliance(Merchant subMerchant, Merchant mainMerchant) {

		String fromAddress = PropertyLoader.getFile().getProperty("FROMMAIL");
		String toAddress = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_MAIL_TO");
		String ccAddress = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_MAIL_CC");
		String subject = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_SUBJECT")
				+ new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		logger.info("Sending Email to :" + toAddress + " : " + ccAddress);
		String fromName = PropertyLoader.getFile().getProperty("FROMNAME");

		StringBuilder result = new StringBuilder();

		// New changes mail formate
		result.append("Dear Ethan, ").append("<br><br>").append(
				"We have received a new sub-merchant for review and approval. Below are the details of the sub-merchant: ")
				.append("<br><br><ul style=\"list-style-type:disc\">").append("<li>Merchant Name:  ")
				.append(subMerchant.getMmId()).append("</li>").append("<li>Sub-merchant Name:  ")
				.append(subMerchant.getBusinessName()).append("</li>").append("</ul>")
				.append("<br>Your prompt attention to this matter is greatly appreciated.")
				.append("<a href=\"https://portal.gomobi.io/MobiversaAdmin/\">https://portal.gomobi.io/MobiversaAdmin/ ").append("</a>")
				.append("<br><br> Keep up the good work.<br>").append("<br> Mobi ");	

		String textBody = result.toString();

		try {
			int message = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, ccAddress, null,
					textBody);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void sendMailToOperationChild(Merchant merchant) {
		String fromAddress = PropertyLoader.getFile().getProperty("FROMMAIL");
		String toAddress = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-CHILD_MAIL_TO");
		String ccAddress = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-CHILD_MAIL_CC");
		String subject = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-CHILD_SUBJECT")
				+ new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		logger.info("Sending Email to :" + toAddress + " : " + ccAddress);
		String fromName = PropertyLoader.getFile().getProperty("FROMNAME");

		StringBuilder result = new StringBuilder();

		// New changes mail formate

		String textBody = "Dear CS team,\n\n" + "The " +merchant.getBusinessName() + " of " +  merchant.getMmId() 
				+ " has been approved.\n\n"
				+ "Please proceed with the next steps in the onboarding process. https://portal.gomobi.io/MobiversaAdmin/ \n\n";

		ElasticEmail message = new ElasticEmail(fromAddress, subject, fromName, toAddress, ccAddress, ccAddress, null,
				textBody);
		ElasticEmailClient client = new ElasticEmailClient();

		try {
			client.sendMessage(message);
			logger.info("Email Sent Successfully to " + toAddress);
		} catch (Exception pe) {
			logger.info("Exception while sending mail to ethan@gomobi.io");
		}
	}

	public void sendMailToOperationParent(Merchant merchant, String yamlString) {
		String fromAddress = PropertyLoader.getFile().getProperty("FROMMAIL");
		String toAddress = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-PARENT_MAIL_TO");
		String ccAddress = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-PARENT_MAIL_CC");
		String subject = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-PARENT_SUBJECT")
				+ new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		logger.info("Sending Email to :" + toAddress + " : " + ccAddress);
		String fromName = PropertyLoader.getFile().getProperty("FROMNAME");

		StringBuilder result = new StringBuilder();

		result.append("Dear Muthu and Rachel, ").append("<br><br>")
				.append("Please review and confirm the sub-merchant MDR rate: ").append("<a href=\"https://portal.gomobi.io/MobiversaAdmin/\">https://portal.gomobi.io/MobiversaAdmin/</a>").append("<br><br>").append("<ul>")
				.append("<li>").append("Merchant Name: ").append(merchant.getMmId()).append("</li>").append("<li>")
				.append("Sub-merchant Name: ").append(merchant.getBusinessName()).append("</li>").append("<li>")
				.append("Mid: ").append(merchant.getMid().getSubMerchantMID()).append("</li>").append("<li>").append("Mdr Rates: ")
				.append("</li>").append("</ul>").append("<div style=\"margin-left:50px\">").append("<pre>")
				.append(yamlString).append("</pre>").append("</div>");

		String textBody = result.toString();
		int message = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, ccAddress, null,
				textBody);

		logger.info("Mail response : " + message);

	}
	
	public void operationParentForRecheckMail(Merchant merchant,String comments) {
		String fromAddress = PropertyLoader.getFile().getProperty("FROMMAIL");
		String toAddress = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-PARENT_MAIL_TO");
		String ccAddress = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-PARENT_MAIL_CC");
		String subject = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-PARENT_SUBJECT")
				+ new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		logger.info("Sending Email to :" + toAddress + " : " + ccAddress);
		String fromName = PropertyLoader.getFile().getProperty("FROMNAME");

		StringBuilder result = new StringBuilder();

		result.append("Dear Rachel, ").append("<br><br>")
				.append("The ").append(merchant.getBusinessName()+" of "+merchant.getMmId()+" has been rechecked.")
				.append("<br>").append("Comments:").append("<br>").append(comments);

		String textBody = result.toString();
		int message = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, ccAddress, null,
				textBody);

		logger.info("Mail response : " + message);

	}

	public void sendMailToOperationParentForRecheck(Merchant merchant, String description) {
		String fromAddress = PropertyLoader.getFile().getProperty("FROMMAIL");
		String toAddress = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-PARENT_RECHECK_MAIL_TO");
		String ccAddress = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-PARENT_RECHECK_MAIL_CC");
		String subject = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-PARENT_RECHECK_SUBJECT")
				+ new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		logger.info("Sending Email to :" + toAddress + " : " + ccAddress);
		String fromName = PropertyLoader.getFile().getProperty("FROMNAME");

		String textBody = "Hi Lidiya,\n\n" + "This is regarding Registration process of Submerchant " + merchant.getBusinessName()
				+ ". Please review for the following reason: " + description + ".\n\n"
				+ "Best regards,\n" + "mobi";

		ElasticEmail message = new ElasticEmail(fromAddress, subject, fromName, toAddress, ccAddress, ccAddress, null,
				textBody);
		ElasticEmailClient client = new ElasticEmailClient();

		try {
			client.sendMessage(message);
			logger.info("Email Sent Successfully to " + toAddress);
		} catch (Exception pe) {
			logger.info("Exception while sending mail to ethan@gomobi.io");
		}
	}

	public void sendMailToOperationParentForApprove(Merchant merchant, String mail, Merchant mainMerchantData) {
		String fromAddress = PropertyLoader.getFile().getProperty("FROMMAIL");
		String toAddress = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-PARENT_APPROVE_MAIL_TO")
				+ "," + mainMerchantData.getEmail() + "," + mail;
		String ccAddress = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-PARENT_APPROVE_MAIL_CC");
		String subject = PropertyLoader.getFile().getProperty("SUB_MERCHANT_REGISTRATION_OP-PARENT_APPROVE_SUBJECT")
				+ new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		logger.info("Sending Email to :" + toAddress + " : " + ccAddress);
		String fromName = PropertyLoader.getFile().getProperty("FROMNAME");

//		StringBuilder result = new StringBuilder();
//		result.append("Great news! Your , ").append(merchant.getBusinessName()).append("has been approved. ")
//				.append("Their sub-merchant MID is ")
//				.append(merchant.getMid().getSubMerchantMID() + ".")
//				.append("/n/n")
//				.append("You can proceed to add the sub-merchant to your sub-merchant list."
//						+ " If you have any questions or need further assistance, please feel free to contact us. ")
//				.append("/n")
//				.append("https://portal.gomobi.io/MobiversaAdmin/");

		String textBody =  "Great news! Your ," + merchant.getBusinessName() +" has been approved. "
		+"\n\n"
		+ "Their sub-merchant MID is " + merchant.getMid().getSubMerchantMID() + ".\n\n"
		+ "You can proceed to add the sub-merchant to your sub-merchant list.  If you have any questions or need further assistance, please feel free to contact us. " 
		+ " https://portal.gomobi.io/MobiversaAdmin/ ";

		ElasticEmail message = new ElasticEmail(fromAddress, subject, fromName, toAddress, ccAddress, ccAddress, null,
				textBody);
		ElasticEmailClient client = new ElasticEmailClient();

		try {
			client.sendMessage(message);
			logger.info("Email Sent Successfully to " + toAddress);
		} catch (Exception pe) {
			logger.info("Exception while sending mail to ethan@gomobi.io");
		}
	}

}
