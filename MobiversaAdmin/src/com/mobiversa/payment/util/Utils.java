package com.mobiversa.payment.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.PayoutBankBalance;
import com.mobiversa.common.bo.PayoutMdr;
import com.mobiversa.payment.controller.bean.DateTime;
import com.mobiversa.payment.controller.bean.MDRDetailsBean;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Boost;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Cards;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Ewallet;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Fpx;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Grab;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Master;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Payout;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Spp;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Tng;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Union;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Visa;

public class Utils {

	private static Logger logger = Logger.getLogger(Utils.class);
	private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public static String currentTimestampToWithdraw() {
		// Get current timestamp
		LocalDateTime now = LocalDateTime.now();

		// Define the desired date-time format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");

		// Format the timestamp
		String formattedDateTime = now.format(formatter);

		// Get day of month and append suffix
		int dayOfMonth = now.getDayOfMonth();
		String dayWithSuffix = dayOfMonth + getDayOfMonthSuffix(dayOfMonth);

		// Print the formatted timestamp with day suffix
		logger.info("Current timestamp: " + dayWithSuffix + " " + formattedDateTime);
		return formattedDateTime;
	}

	public static String getDayOfMonthSuffix(int day) {
		if (day >= 11 && day <= 13) {
			return "th";
		}

		switch (day % 10) {
		case 1:
			return "st";
		case 2:
			return "nd";
		case 3:
			return "rd";
		default:
			return "th";
		}
	}

	public String amountFormatWithcomma(Double d) {
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		return myFormatter.format(d);
	}

	public String amountFormatWithoutcomma(Double d) {
		String pattern = "###0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		return myFormatter.format(d);
	}

	public DateTime currentMYT() {
		Calendar now = Calendar.getInstance();
		Locale malaysianLocale = new Locale("ms", "MY");
		String pattern1 = "yyyy-MM-dd";
		String pattern2 = "HH:mm:ss";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern1);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);

		TimeZone malaysianTimeZone = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		simpleDateFormat.setTimeZone(malaysianTimeZone);
		simpleDateFormat2.setTimeZone(malaysianTimeZone);

		logger.info("Current Malaysia Date  => " + simpleDateFormat.format(now.getTime()));
		logger.info("Current Malaysia Time => " + simpleDateFormat2.format(now.getTime()));

		String currentDate = simpleDateFormat.format(now.getTime());
		String currentTime = simpleDateFormat2.format(now.getTime());

		return new DateTime(currentDate, currentTime);
	}

	public Justsettle curlecBankApi(PayeeDetails payee, Merchant currentMerchant, String finalPayoutDate,
			String repFinalNetamt, String ezysettleReferenceNo) throws IOException {

		Justsettle just = null;

		logger.info("Merchant Mid :" + payee.getMid());
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		StringBuffer response1 = new StringBuffer();
		URL url;
		logger.info("External Api URL for Ezysettle ==> "+PropertyLoader.getFileData().getProperty("EZYSETTLE_CURLEC_API"));
		url = new URL(PropertyLoader.getFileData().getProperty("EZYSETTLE_CURLEC_API"));

		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		JSONObject params = new JSONObject();
		BankCode bank = new BankCode();

		Utils util = new Utils();
		String ezysettleRequestAmt = util.amountFormatWithoutcomma(Double.parseDouble(repFinalNetamt.replace(",", "")));

		String bicCode = bank.getBankBicCode(payee.getBankName());
		logger.info("merchant name :" + payee.getMerchantName());
		logger.info("Bank name :" + payee.getBankName());
		logger.info("creditorAccNo :" + payee.getAccountNo());
		logger.info("Bic code :" + bicCode);
		logger.info("amt :" + ezysettleRequestAmt);
		logger.info("Service :" + "PAYOUT_TXN_EZYSETTLE");
		logger.info("mid :" + payee.getMid());
		logger.info("finalPayoutDate :" + finalPayoutDate);
		logger.info("BusinessName :" + currentMerchant.getBusinessName());
		logger.info("Ezysettle Reference No :" + ezysettleReferenceNo);

		params.put("creditorAccName", String.valueOf(payee.getMerchantName()));
		params.put("service", "PAYOUT_TXN_EZYSETTLE");
		params.put("mobiApiKey", "");
		params.put("brn", "");
		params.put("biccode", bicCode);
		params.put("bankName", payee.getBankName());
		params.put("creditorAccNo", payee.getAccountNo());
		params.put("amt", ezysettleRequestAmt);
		params.put("mid", payee.getMid());
		params.put("businessRegNo", currentMerchant.getBusinessRegistrationNumber());
		params.put("finalPayoutDate", finalPayoutDate);
		params.put("businessName", currentMerchant.getBusinessName());
		params.put("ezySettleReferenceNo", ezysettleReferenceNo);

		paramss = params;
		OutputStream os = con.getOutputStream();
		os.write(paramss.toString().getBytes());
		logger.info("The params That passed :" + paramss);
		os.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
		while ((inputLine = br.readLine()) != null) {
			response1.append(inputLine);
		}
		logger.info("Curlec API Response :" + response1.toString());
		output = response1.toString();
		logger.info("output  :" + output);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		just = gson.fromJson(output, Justsettle.class);
		just.setResponseCode(just.getResponseCode());

		just.setResponseData(just.getResponseData());
		logger.info("Bank Ezysettle Response Data :" + just.getResponseData());
		logger.info("Bank Response Code :" + just.getResponseCode());
		return just;
	}

	public Justsettle amBankBankApi(PayeeDetails payee, Merchant currentMerchant, String finalPayoutDate,
			String repFinalNetamt) throws IOException {

		logger.info("Merchant Mid :" + payee.getMid());

		Justsettle just = null;
		StringBuffer response1 = new StringBuffer();
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
//		try {
//		url = new URL(getupdateEzysettleAmbank());

		url = new URL(PropertyLoader.getFileData().getProperty("updateEzysettleAmbank"));

		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setDoOutput(true);
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json");
		JSONObject params = new JSONObject();
		BankCode bank = new BankCode();

		String bicCode = bank.getBankBicCode(payee.getBankName());

		logger.info("Merchant name :" + payee.getMerchantName());
		logger.info("Bank name :" + payee.getBankName());
		logger.info("CreditorAccNo :" + payee.getAccountNo());
		logger.info("Bic code :" + bicCode);
		logger.info("Amount :" + repFinalNetamt);
		logger.info("Service :" + "PAYOUT_TXN_EZYSETTLE");
		logger.info("Mid :" + payee.getMid());
		logger.info("FinalPayoutDate :" + finalPayoutDate);
		logger.info("BusinessName :" + currentMerchant.getBusinessName());

		params.put("Creditor AccName", String.valueOf(payee.getMerchantName()));
		params.put("service", "PAYOUT_TXN_EZYSETTLE");
		params.put("mobiApiKey", "");
		params.put("brn", "");
		params.put("biccode", bicCode);
		params.put("bankName", payee.getBankName());
		params.put("creditorAccNo", payee.getAccountNo());
		params.put("amt", repFinalNetamt);
		params.put("mid", payee.getMid());

		params.put("finalPayoutDate", finalPayoutDate);
		params.put("businessName", currentMerchant.getBusinessName());

		paramss = params;
		OutputStream os = con.getOutputStream();
		os.write(paramss.toString().getBytes());
		logger.info("The params That passed :" + paramss);
		os.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
		while ((inputLine = br.readLine()) != null) {
			response1.append(inputLine);
		}
		output = response1.toString();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		just = gson.fromJson(output, Justsettle.class);
		just.setResponseCode(just.getResponseCode());
		logger.info("Bank Response Code :" + just.getResponseCode());
		return just;
//		} catch (Exception e) {
//			logger.info("Exception 1");
//			e.printStackTrace();
//			just.setResponseCode("0001");
//			return just;
//		}

	}

	public static String getupdateEzysettleAmbank() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			logger.info("Path :" + prop.getProperty("updateEzysettleAmbank"));
			path = prop.getProperty("updateEzysettleAmbank");
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

	public static String ezysettleCurlecApiUrl() {

		Properties prop = new Properties();
		String path = null;
		try (InputStream input = PropertyLoader.class.getResourceAsStream("/config_demo.properties")) {
			if (input == null) {
				logger.info("Unable to find config.properties file");
			}
			prop.load(input);
			path = prop.getProperty("EZYSETTLE_CURLEC_API");
		} catch (Exception e) {
			logger.info("Exception in EzysettleCurlecApiUrl : " + e.getMessage());
		}

		return path;
	}

	public static String dateformat(String dateStr) {

		logger.info("Date : " + dateStr);
		// Define the input and output formatters
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		// Parse the date string to LocalDate
		LocalDate date = LocalDate.parse(dateStr, inputFormatter);

		// Convert LocalDate to LocalDateTime at start of day
		LocalDateTime dateTime = date.atStartOfDay();

		// Format the LocalDateTime to the desired output format
		String formattedDateStr = dateTime.format(outputFormatter);

		// Print the formatted date string
		logger.info(formattedDateStr);

		return formattedDateStr;
	}

	public static String generatePassword() {
		SecureRandom random = new SecureRandom();
		StringBuilder password = new StringBuilder(8);

		for (int i = 0; i < 8; i++) {
			int index = random.nextInt(CHARACTERS.length());
			password.append(CHARACTERS.charAt(index));
		}

		return password.toString();
	}

	public String createdDate() {

		LocalDateTime dateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		logger.info("dateTime : " + dateTime);
		String createdDate = formatter.format(dateTime);
		logger.info("createdDate : " + createdDate);

		return createdDate;
	}

	public MDRDetailsBean payoutmdrListViewinOperationParent(MDRDetailsBean mdrDetailsBean, PayoutMdr payoutmdr) {

		Payout payout = new Payout();
		payout.setMerchantmdr(payoutmdr.getMdr());
		payout.setMinimummdr(payoutmdr.getMinPayoutMdrAmount());
		payout.setHostmdr(payoutmdr.getHostMdr());
		payout.setMobimdr(payoutmdr.getMobiMdr());
		mdrDetailsBean.setPayout(payout);

		logger.info("Merchant mdr : " + payout.getMerchantmdr());

		return mdrDetailsBean;
	}

	public MDRDetailsBean mdrListViewinOperationParent(MobiMDR mobimdr, MDRDetailsBean mdrDetailsBean, Cards cards,
			Ewallet wallet) {
		Utils util = new Utils();

		switch (mobimdr.getCardBrand()) {

		case "VISA":
			Visa visa = new Visa();
			visa.setForiegncreditmdr(util.floatTostring(mobimdr.getCreditForeignMerchantMDR()));
			visa.setForiegndebitmdr(util.floatTostring(mobimdr.getDebitForeignMerchantMDR()));
			visa.setLocalcreditmdr(util.floatTostring(mobimdr.getCreditLocalMerchantMDR()));
			visa.setLocaldebitmdr(util.floatTostring(mobimdr.getDebitLocalMerchantMDR()));
			cards.setVisa(visa);
			mdrDetailsBean.setCards(cards);

			break;
		case "MASTERCARD":
			Master master = new Master();
			master.setForiegncreditmdr(util.floatTostring(mobimdr.getCreditForeignMerchantMDR()));
			master.setForiegndebitmdr(util.floatTostring(mobimdr.getDebitForeignMerchantMDR()));
			master.setLocalcreditmdr(util.floatTostring(mobimdr.getCreditLocalMerchantMDR()));
			master.setLocaldebitmdr(util.floatTostring(mobimdr.getDebitLocalMerchantMDR()));
			cards.setMaster(master);
			mdrDetailsBean.setCards(cards);
			break;
		case "UNIONPAY":

			Union union = new Union();
			union.setForiegncreditmdr(util.floatTostring(mobimdr.getCreditForeignMerchantMDR()));
			union.setForiegndebitmdr(util.floatTostring(mobimdr.getDebitForeignMerchantMDR()));
			union.setLocalcreditmdr(util.floatTostring(mobimdr.getCreditLocalMerchantMDR()));
			union.setLocaldebitmdr(util.floatTostring(mobimdr.getDebitLocalMerchantMDR()));
			cards.setUnion(union);
			mdrDetailsBean.setCards(cards);
			break;
		case "FPX":
			Fpx fpxmdrList = new Fpx();
			fpxmdrList.setHostmdr(util.floatTostring(mobimdr.getFpxHostAmt()));
			fpxmdrList.setMerchantmdr(util.floatTostring(mobimdr.getFpxMercAmt()));
			fpxmdrList.setMobimdr(util.floatTostring(mobimdr.getFpxMobiAmt()));
			fpxmdrList.setMinimummdr(mobimdr.getMinValue());
			mdrDetailsBean.setFpx(fpxmdrList);
			break;
		case "BOOST":
			Boost boost = new Boost();
			boost.setHostmdr(util.floatTostring(mobimdr.getBoostEcomHostMDR()));
			boost.setMerchantmdr(util.floatTostring(mobimdr.getBoostEcomMerchantMDR()));
			boost.setMobimdr(util.floatTostring(mobimdr.getBoostEcomMobiMDR()));
			boost.setMinimummdr(mobimdr.getMinValue());
			wallet.setBoost(boost);
			mdrDetailsBean.setEwallet(wallet);
			break;
		case "GRAB":
			Grab grab = new Grab();
			grab.setHostmdr(util.floatTostring(mobimdr.getGrabEcomHostMDR()));
			grab.setMerchantmdr(util.floatTostring(mobimdr.getGrabEcomMerchantMDR()));
			grab.setMobimdr(util.floatTostring(mobimdr.getGrabEcomMobiMDR()));
			grab.setMinimummdr(mobimdr.getMinValue());
			wallet.setGrab(grab);
			mdrDetailsBean.setEwallet(wallet);
			break;
		case "TNG":

			Tng tng = new Tng();
			tng.setHostmdr(util.floatTostring(mobimdr.getTngEcomHostMDR()));
			tng.setMerchantmdr(util.floatTostring(mobimdr.getTngEcomMerchantMDR()));
			tng.setMobimdr(util.floatTostring(mobimdr.getTngEcomMobiMDR()));
			tng.setMinimummdr(mobimdr.getMinValue());
			wallet.setTng(tng);
			mdrDetailsBean.setEwallet(wallet);
			break;
		case "SHOPPY":

			Spp spp = new Spp();
			spp.setHostmdr(util.floatTostring(mobimdr.getTngEcomHostMDR()));
			spp.setMerchantmdr(util.floatTostring(mobimdr.getTngEcomMerchantMDR()));
			spp.setMobimdr(util.floatTostring(mobimdr.getTngEcomMobiMDR()));
			spp.setMinimummdr(mobimdr.getMinValue());
			wallet.setSpp(spp);
			mdrDetailsBean.setEwallet(wallet);
			break;
		default:
			// Handle default case if necessary
		}

		return mdrDetailsBean;
	}

	public String floatTostring(float mdr) {
		try {
			logger.info("mdr data :" + mdr);
			String mdrvalue = String.valueOf(mdr);
			logger.info("mdrvalue data :" + mdrvalue);
			return mdrvalue;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "0.0";
		}
	}

	public float stringTofloat(String mdr) {
		try {
			logger.info("mdr data :" + mdr);
			String mdrData = (mdr.isEmpty()) ? "0.0" : mdr;
			Float mdrvalue = Float.parseFloat(mdrData);

			logger.info("mdrvalue data :" + mdrvalue);
			return mdrvalue;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return 0.0f;
		}
	}

	public static String withdrawMailContent(List<PayoutBankBalance> bankbalance, Merchant merchant,
			String beforeWithdraw, String withdrawAmount, String afterWithdraw, String timeStamp, String UUID,
			String comment, String userName) {

		StringBuilder htmlBuilder = new StringBuilder();

		htmlBuilder.append("<!DOCTYPE html>\n").append("<html lang=\"en\">\n").append("<head>\n")
				.append("    <meta charset=\"UTF-8\">\n")
				.append("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
				.append("    <title>Deposit Report</title>\n")
				.append("    <link rel=\"preconnect\" href=\"https://fonts.googleapis.com\">\n")
				.append("    <link rel=\"preconnect\" href=\"https://fonts.gstatic.com\" crossorigin>\n")
				.append("    <link href=\"https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900&display=swap\" rel=\"stylesheet\">\n")
				.append("    <style>\n").append("    @media (max-width: 600px) {\n").append("        .container {\n")
				.append("            padding: 15px;\n").append("            margin: 20px auto !important;\n")
				.append("        }\n").append("        .header td{\n")
				.append("            padding-left: 2px !important;\n").append("        }\n")
				.append("        .header img {\n").append("            width: 100px !important;\n")
				.append("            height: 50px !important;\n").append("        }\n").append("        .title {\n")
				.append("            font-size: 1.2rem !important;\n")
				.append("            margin-bottom: 10px !important;\n").append("        }\n")
				.append("        .details td {\n").append("            font-size: 10px !important;\n")
				.append("            padding: 6px !important;\n").append("            padding-left: 2px !important;\n")
				.append("        }\n").append("        .transaction-table th,\n")
				.append("        .transaction-table td {\n").append("            font-size: 10px !important;\n")
				.append("            padding: 6px;\n").append("        }\n").append("        .note,\n")
				.append("        .contact {\n").append("            font-size: 10px !important;\n")
				.append("        }\n").append("    }\n").append("    </style>\n").append("</head>\n")
				.append("<body style=\"font-family: Poppins, sans-serif; background-color: #ffffff; margin: 0; padding: 0; text-align: center;\">\n")
				.append("    <table class=\"container\" style=\"background-color: #f6f6f6; text-align: left; padding: 20px; border-radius: 10px; max-width: 550px; margin: 20px auto;\">\n")
				.append("        <tr>\n").append("            <td>\n")
				.append("                <table class=\"header\" style=\"width: 100%; text-align: left;\">\n")
				.append("                    <tr>\n")
				.append("                        <td style=\"padding-left: 2px;padding: 5px 5px;\">\n")
				.append("                            <img src=\"https://portal.gomobi.io/MobiversaAdmin/resourcesNew/img/mobi-logo-supported.svg\" width=\"110\" height=\"70\" style=\"width: 110px; height: 50px;\">\n")
				.append("                        </td>\n").append("                    </tr>\n")
				.append("                </table>\n")
				.append("                <table class=\"title\" style=\"width: 100%; text-align: left; color: #51CB49; font-size: 1.6rem; font-weight: bold; margin-bottom: 10px;margin-left: -2px !important;\">\n")
				.append("                    <tr>\n")
				.append("                        <td style=\"padding: 16px 5px\">Withdrawal details</td>\n")
				.append("                    </tr>\n").append("                </table>\n")

				.append("                <table class=\"details\" style=\"width: 100%; border-collapse: collapse; margin-bottom: 20px;\">\n")
				.append("                    <tr>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;font-weight: 600; white-space: nowrap; color: #333739;\">Date & Time</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;\">:</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;word-break: break-all; color: #333739;\">")
				.append(timeStamp).append("</td>\n").append("                    </tr>\n")
				.append("                    <tr>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;font-weight: 550; white-space: nowrap; color: #333739;\">Merchant</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;\">:</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px; word-break: break-all; color: #333739;\">")
				.append(merchant.getBusinessName()).append("</td>\n").append("                    </tr>\n")
				.append("                    <tr>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;font-weight: 600; white-space: nowrap; color: #333739;\">Withdraw Amount</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;\">:</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;word-break: break-all; color: #333739;\">")
				.append("RM ").append(formatAmount(withdrawAmount)).append("</td>\n")
				.append("                    </tr>\n").append("                    <tr>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;font-weight: 600; white-space: nowrap; color: #333739;\">Reference Id</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;\">:</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;word-break: break-all; color: #333739;\">")
				.append(UUID).append("</td>\n").append("                    </tr>\n")
				.append("                    <tr>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;font-weight: 600; white-space: nowrap; color: #333739;\">Requestor's Name</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;\">:</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;word-break: break-all; color: #333739;\">")
				.append(userName).append("</td>\n").append("                    </tr>\n")
				.append("                    <tr>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;font-weight: 600; white-space: nowrap; color: #333739;\">Before Withdrawal Balance</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;\">:</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;word-break: break-all; color: #333739;\">")
				.append("RM ").append(formatAmount(String.valueOf(beforeWithdraw))).append("</td>\n")
				.append("                    </tr>\n").append("                    <tr>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;font-weight: 600; white-space: nowrap; color: #333739;\">Available Balance</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;\">:</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;word-break: break-all; color: #333739;\">")
				.append("RM ").append(formatAmount(afterWithdraw)).append("</td>\n")
				.append("                    </tr>\n").append("                    <tr>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;font-weight: 600; white-space: nowrap; color: #333739;\">Comments</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;\">:</td>\n")
				.append("                        <td style=\"vertical-align: baseline;padding: 8px 5px;word-break: break-all; color: #333739;\">")
				.append(comment).append("</td>\n").append("                    </tr>\n")
				.append("                </table>\n")
				.append("                <table class=\"transaction-table\" style=\"width: 100%; border-collapse: collapse; background-color: #ddd; margin-bottom: 20px; border: 1px solid #ddd; border-radius: 5px; overflow: hidden;\">\n")
				.append("                    <thead>\n").append("                        <tr>\n")
				.append("                            <th style=\"border: 1px solid #C5C5C5; border-bottom: none; background-color: #F2F2F2; padding: 8px 5px; font-size: 15px; text-align: center; white-space: nowrap; color: #333739;\">Bank Name</th>\n")
				.append("                            <th style=\"border: 1px solid #C5C5C5; border-bottom: none; background-color: #F2F2F2; padding: 8px 5px; font-size: 15px; text-align: center; white-space: nowrap; color: #333739;\">Bank Balance</th>\n")
				.append("                        </tr>\n").append("                    </thead>\n")
				.append("                    <tbody>\n");

		boolean isFirstTime = true;
		if (bankbalance == null || bankbalance.isEmpty()) {
			logger.info("No bank balances found. Displaying NA.");
			htmlBuilder.append("                        <tr>\n").append(
					"                            <td style=\"border: 1px solid #C5C5C5; border-top: none; background-color: #ffffff; padding:  8px 5px; font-size: 15px; text-align: center; white-space: nowrap; color: #333739;\">")
					.append("NA").append("</td>\n")
					.append("                            <td style=\"border: 1px solid #C5C5C5; border-top: none; background-color: #ffffff; padding:  8px 5px; font-size: 15px; text-align: center; white-space: nowrap; color: #333739;\">")
					.append("RM 0.00").append("</td>\n").append("                        </tr>\n");
		} else {
			for (PayoutBankBalance bank : bankbalance) {
				String bankName = bank.getBankName();
				if (isFirstTime) {
					bankName = "AMBANK";
					isFirstTime = false;
				}
				logger.info("Database bankName : " + bankName);
				htmlBuilder.append("                        <tr>\n").append(
						"                            <td style=\"border: 1px solid #C5C5C5; border-top: none; background-color: #ffffff; padding:  8px 5px; font-size: 15px; text-align: center; white-space: nowrap; color: #333739;\">")
						.append(bankName).append("</td>\n")
						.append("                            <td style=\"border: 1px solid #C5C5C5; border-top: none; background-color: #ffffff; padding:  8px 5px; font-size: 15px; text-align: center; white-space: nowrap; color: #333739;\">")
						.append("RM ").append(formatAmount(bank.getAmount())).append("</td>\n")
						.append("                        </tr>\n");
			}
		}
		htmlBuilder.append("                    </tbody>\n").append("                </table>\n").append(
				"                <table class=\"contact\" style=\"width: 100%; font-size: 17px; margin-bottom: 10px; text-align: left; line-height: 1.6; margin-left: 2px !important;\">\n")
				.append("                    <tr>\n")
				.append("                        <td class=\"fw-bold\" style=\"font-weight: 550; color: #333739;\">Note</td>\n")
				.append("                    </tr>\n").append("                    <tr>\n")
				.append("                        <td>If you have any questions regarding this report, please contact our Engineering team.</td>\n")
				.append("                    </tr>\n").append("                </table>\n")

				.append("                <table class=\"contact\" style=\"width: 100%; font-size: 17px; margin-bottom: 10px; text-align: left; line-height: 1.6;margin-left: 2px !important;\">\n")
				.append("                    <tr>\n")
				.append("                        <td class=\"fw-bold\" style=\"font-weight: 550; color: #333739;\">Contact Information</td>\n")
				.append("                    </tr>\n").append("                    <tr>\n")
				.append("                        <td>\n")
				.append("                            <a href=\"mailto:tech@gomobi.io\" style=\"text-decoration: none; color: #333739; cursor: text; pointer-events: none;\">tech@gomobi.io</a>,\n")
				.append("                            <a style=\"color: #333739 !important; text-decoration: none; cursor: text; pointer-events: none;\">+91 88389-48443</a>\n")
				.append("                        </td>\n").append("                    </tr>\n")
				.append("                </table>\n").append("            </td>\n").append("        </tr>\n")
				.append("    </table>\n").append("</body>\n").append("</html>");

		return htmlBuilder.toString();
	}

	public static String formatAmount(String amount) {

		if (amount.contains(",")) {
			/* Return the amount as-is if it's already formatted */
			return amount;
		}

		try {
			double parsedAmount = Double.parseDouble(amount);
			DecimalFormat formatter = new DecimalFormat("#,##0.00");
			return formatter.format(parsedAmount);
		} catch (NumberFormatException e) {
			logger.info("Invalid amount format: " + amount);
			return amount;
		}
	}

	public List<String> loadMid(Merchant merchant) {

		List<String> mids = new ArrayList<>();

	    // Adding non-null and non-empty MID values
	    Optional.ofNullable(merchant.getMid().getUmMid()).filter(mid -> !mid.isEmpty()).ifPresent(mids::add);
	    Optional.ofNullable(merchant.getMid().getUmEzywayMid()).filter(mid -> !mid.isEmpty()).ifPresent(mids::add);
	    Optional.ofNullable(merchant.getMid().getUmMotoMid()).filter(mid -> !mid.isEmpty()).ifPresent(mids::add);
	    Optional.ofNullable(merchant.getMid().getBoostMid()).filter(mid -> !mid.isEmpty()).ifPresent(mids::add);
	    Optional.ofNullable(merchant.getMid().getGrabMid()).filter(mid -> !mid.isEmpty()).ifPresent(mids::add);
	    Optional.ofNullable(merchant.getMid().getTngMid()).filter(mid -> !mid.isEmpty()).ifPresent(mids::add);
	    Optional.ofNullable(merchant.getMid().getShoppyMid()).filter(mid -> !mid.isEmpty()).ifPresent(mids::add);
	    Optional.ofNullable(merchant.getMid().getFpxMid()).filter(mid -> !mid.isEmpty()).ifPresent(mids::add);

		return mids;

	}

//	public static String getEzysettleCurlecApiUrl() {
//		// To get path
//		Properties prop = new Properties();
//		InputStream input = null;
//		String path = null;
//		try {
//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			input = loader.getResourceAsStream("/config.properties");
//			prop.load(input);
//			logger.info("Path :" + prop.getProperty("EZYSETTLE_CURLEC_API"));
//			path = prop.getProperty("EZYSETTLE_CURLEC_API");
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
	public static String getFormattedDateTime(String pattern) {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		return now.format(formatter);
	}

	


	public static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().isEmpty();
	}
}
