package com.mobiversa.payment.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mobiversa.common.bo.BoostDailyRecon;
import com.mobiversa.common.bo.EwalletTxnDetails;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.GrabPayFile;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.payment.controller.bean.DateTime;
import com.mobiversa.payment.dao.EzysettleDao;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dao.TransactionDao;
import com.mobiversa.payment.dto.ReuseDto;
import com.mobiversa.payment.exception.MobileApiException;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.TransactionService;
import com.postmark.java.Attachment;

@Component
public class EzysettleUtils {

	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	private static final String PAYER_HEADER = "PAYMENT DATE,CURRENCY,WITHDRAW FEE,TOTAL NET AMOUNT,PAYER";
	private static final String PAYEE_HEADER = "MERCHANT NAME,EMAIL,MID,ACCOUNTNO,BANKNAME,SETTLEMENT DATE,MERCHANT MDR,NET AMOUNT";

//	private MerchantDao merchantDao;
//	
//	public EzysettleUtils(MerchantDao merchantDao) {
//		this.merchantDao = merchantDao;
//	}

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private MerchantService merchantService;

	private static final Logger logger = Logger.getLogger(EzysettleUtils.class);

	@SuppressWarnings("static-method")
	public static String EzysettleCSV(Payer pAmount, Merchant currentMerchant, MerchantService merchantService,
			String finalPayoutDate, String today1, String csvFile, String status) {
		List<Payer> payerList = new ArrayList<Payer>();
//		List<PayeeDetails> payeeListBank = null;

		PayeeDetails payee = null;

		logger.info("payerAmt:" + pAmount);
		pAmount.setPaymentDate(today1);
		pAmount.setCurrency("MYR");
		pAmount.setPayerName("MOBIVERSA SDN BHD");
		payerList.add(pAmount);
		logger.info("Start payee");
		payee = merchantService.getPayeeDetails(finalPayoutDate, currentMerchant, status);
		FileWriter fileWriter = null;
		try {

			fileWriter = new FileWriter(PropertyLoader.getFileData().getProperty("JSCSV_CLEARENCEFILEPATH") + csvFile);
			/* Write the CSV file header */
			logger.info("Ezysettle CSV path " + fileWriter);
			fileWriter.append(PAYER_HEADER.toString());
			/* Add a new line separator after the header */
			fileWriter.append(NEW_LINE_SEPARATOR);
			/* Write a new payer object list to the CSV file */

			for (Payer payer : payerList) {
				logger.info(" payer List :" + payer.toString());

				fileWriter.append(String.valueOf(payer.getPaymentDate()));
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(payer.getCurrency());
				fileWriter.append(COMMA_DELIMITER);
				logger.info("Negilate Bank Fee :" + Double.parseDouble(payer.getTotalMerAmount()));
				fileWriter.append(payer.getTotalMerAmount());
				fileWriter.append(COMMA_DELIMITER);

				fileWriter.append(payer.getBankRequestFee());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(payer.getPayerName());
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
			fileWriter.append(NEW_LINE_SEPARATOR);
			fileWriter.append(NEW_LINE_SEPARATOR);
			// Write the CSV file header
			fileWriter.append(PAYEE_HEADER.toString());
			// Add a new line separator after the header
			fileWriter.append(NEW_LINE_SEPARATOR);
			// Write a new payer object list to the CSV file

			logger.info(" payee Data :" + payee.toString());

			fileWriter.append(String.valueOf(payee.getMerchantName()));
			fileWriter.append(COMMA_DELIMITER);
			List<String> emailList = Arrays.asList(payee.getEmail());
			String combinedEmail = String.join(",", emailList);
			// Enclose the combined string in double quotes
			String quotedEmail = "\"" + combinedEmail + "\"";

			fileWriter.append(quotedEmail);
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(payee.getMid());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append("'" + payee.getAccountNo() + "'");
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(payee.getBankName());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(payee.getSettlementDate());
			fileWriter.append(COMMA_DELIMITER);
//			double negilateBankFee = Double.parseDouble(payee.getWithdrawFee())
//					- Double.parseDouble("0.0");
			logger.info("Withdraw fee :" + payee.getWithdrawFee());

			fileWriter.append(payee.getWithdrawFee());
			fileWriter.append(COMMA_DELIMITER);
			fileWriter.append(payee.getBankRequestAmount());
			fileWriter.append(COMMA_DELIMITER);
//			fileWriter.append(payee.getAgentName());
			fileWriter.append(NEW_LINE_SEPARATOR);
//			}
			logger.info("CSV file was created successfully !!!");
		} catch (Exception e) {
			logger.error("Error in CsvFileWriter !!!" + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				logger.error("Error while flushing/closing fileWriter !!! " + e.getMessage());
				e.printStackTrace();
			}
		}
		return null;
	}

	public void ezysettleCsvAndFinalStatusUpdate(String finalPayoutDate, Merchant currentMerchant,
			MerchantService merchantService, String today1, String csvFile, Justsettle just,
			String ezysettleReferenceNo, MerchantDao merchantDao) {

		logger.info("Ezysettle Final status CSV Update ");

		if (just.getResponseCode().equals("0002")) {
			Payer pAmount = null;
			pAmount = merchantService.getPayerAmount(finalPayoutDate, currentMerchant, "ezysettleFaild");
			logger.info(" Curlec Response Pending ");
			logger.info(" Payer Net Amount : " + pAmount.getTotalNetAmount());
			logger.info(" Payer DTO : " + pAmount.toString());

			/* If curlec response pending to trigger merchant mail */
			sendEmailToMerchant(currentMerchant, pAmount);
			try {
				if (just.getResponseData() != null) {
					logger.info("Final status update ");
					merchantDao.updateEzysettlePendingStatus(finalPayoutDate, currentMerchant, merchantService, just,
							ezysettleReferenceNo, pAmount);
				} else {
					logger.info("Curlec API response ResponseData is Null");
				}
			} catch (MobileApiException e) {
				e.printStackTrace();
				logger.error("Ezysettle Final Status Update Exception (Ezysettle Utils) :" + e.getMessage());
			}

		} else {

			Payer pAmount = null;
			pAmount = merchantService.getPayerAmount(finalPayoutDate, currentMerchant, "ezysettleFaild");

			logger.info(" Curlec Response Failure ");
			if (!pAmount.getTotalNetAmount().equals("0.00")) {
				EzysettleUtils.EzysettleCSV(pAmount, currentMerchant, merchantService, finalPayoutDate, today1, csvFile,
						"ezysettleFaild");
			} else {
				logger.info("No Record Found");
			}
			if (!pAmount.getTotalNetAmount().equals("0.00")) {
				String clearencefile = PropertyLoader.getFileData().getProperty("JSCSV_CLEARENCEFILEPATH") + csvFile;

				logger.info("UM_CS Clearence File : " + clearencefile);
				File file = new File(clearencefile);
				if (!file.exists()) {
					logger.info("Clearence File Not Generated");
				} else {
					logger.info("Clearence File is Generated");
					this.sendEmailToEzysettleFaild(clearencefile);
				}
			}
			try {
				logger.info("Final status update ");
				merchantDao.updateEzysettleFinalStatus(finalPayoutDate, currentMerchant, merchantService, just,
						ezysettleReferenceNo, pAmount);
			} catch (MobileApiException e) {
				e.printStackTrace();
				logger.error("Ezysettle Final Status Update Exception (Ezysettle Utils) :" + e.getMessage());
			}

		}

	}

	public static String updateEzysettleFinalStatus(String finalPayoutDate, Merchant currentMerchant,
			MerchantService merchantService, Justsettle just) {

		StringBuffer response = new StringBuffer();
		// justsettle table update after CSV send
		Payer pAmountBank;
		List<Payer> payerListBank = new ArrayList<Payer>();
		pAmountBank = merchantService.getPayerAmountJust(finalPayoutDate, currentMerchant);

		logger.info("Update : date " + pAmountBank.getDate());
		logger.info("Update : mid " + pAmountBank.getMid());
		logger.info("Update : Batch Id " + just.getBatchId());

		String inputLine1 = null;
		JSONObject paramss1 = null;
		URL url;
		try {
			// getupdateEzysettleFinalStatus
			logger.info("Update final ezysettle status api calling ");
			// live
//			url = new URL(getupdateEzysettleFinalStatusV2());
			url = new URL(PropertyLoader.getFileData().getProperty("updateEzysettleAmbankFinalStatus"));

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			JSONObject params = new JSONObject();

			params.put("date", pAmountBank.getDate());
			params.put("mid", pAmountBank.getMid());
			params.put("batchId", just.getBatchId());

			paramss1 = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss1.toString().getBytes());
			logger.info("The params That passed :" + paramss1);
			os.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine1 = br.readLine()) != null) {
				response.append(inputLine1);
			}
		} catch (Exception e) {
			logger.info("Exception : " + e.getMessage());
			e.printStackTrace();
		}

		return response.toString();
	}

//	public static String getupdateEzysettleFinalStatus() {
//		// To get path
//		Properties prop = new Properties();
//		InputStream input = null;
//		String path = null;
//		try {
//			ClassLoader loader = Thread.currentThread().getContextClassLoader();
//			input = loader.getResourceAsStream("/config.properties");
//			prop.load(input);
//			logger.info("Path :" + prop.getProperty("updateEzysettleAmbankFinalStatus"));
//			path = prop.getProperty("updateEzysettleAmbankFinalStatus");
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

	public static String getupdateEzysettleFinalStatusV2() {

		Properties prop = new Properties();
		String path = null;
		try (InputStream input = EzysettleUtils.class.getResourceAsStream("/config.properties")) {
			if (input == null) {
				logger.info("Unable to find config.properties file");
			}
			prop.load(input);
			path = prop.getProperty("updateEzysettleAmbankFinalStatus");
			logger.info("updateEzysettleAmbankFinalStatus Path :" + path);
		} catch (Exception e) {
			logger.info("Exception in GetupdateEzysettleFinalStatusV2 :" + e.getMessage());
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
//			logger.info("Path :" + prop.getProperty("updateEzysettleAmbankFinalStatus"));
//			path = prop.getProperty("updateEzysettleAmbankFinalStatus");
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

	public void sendEmailToEzysettleFaild(String filename) {

		logger.info("Sending UM Clearance csv Mail " + filename);

		String fromAddress = PropertyLoader.getFileData().getProperty("FROMMAIL");
		String apiKey = PropertyLoader.getFileData().getProperty("APIKEY");
		String toAddress = PropertyLoader.getFileData().getProperty("JS_CSV_MAIL_TO");
		String ccAddress = PropertyLoader.getFileData().getProperty("JS_CSV_MAIL_CC");
		String subject = PropertyLoader.getFileData().getProperty("JS_CSV_SUBJECT_EZYSETTLE_FAILD")
				+ new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		logger.info("Sending Email to :" + toAddress + " : " + ccAddress);
		Attachment fileAttach = null;
		List<Attachment> attachments = new ArrayList<Attachment>();
		attachments.add(fileAttach);
		String fromName = PropertyLoader.getFileData().getProperty("FROMNAME");
		String textbody = PropertyLoader.getFileData().getProperty("TEXT_BODY");
		String faildTextbody = PropertyLoader.getFileData().getProperty("FAILD_TEXT_BODY");

		String attachment = filename;

		logger.info("Attachment Path :" + attachment);
		String textBody = "Hi Finance Team," + "\n\n" + faildTextbody + "\n\n" + textbody + " "
				+ new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
		ElasticEmail message = new ElasticEmail(fromAddress, subject, fromName, toAddress, ccAddress, ccAddress,
				attachment, textBody);
		ElasticEmailClient client = new ElasticEmailClient();

		try {
			client.sendEzysettleMessage(message);
			logger.info("Email Sent Successfully to " + toAddress);
		} catch (Exception pe) {
			logger.info("Exception in mail sending " + pe.getMessage());
		}
	}

	public void sendEmailToMerchant(Merchant merchant, Payer pAmount) {

//		List<NameValuePair> headers = new ArrayList<NameValuePair>();
//		headers.add(new NameValuePair("HEADER", "test"));
		String fromAddress = PropertyLoader.getFileData().getProperty("FROMMAIL");

		String toAddress = PropertyLoader.getFileData().getProperty("JS_MERCHANT_MAIL_TO") + "," + merchant.getEmail();
		String ccAddress = PropertyLoader.getFileData().getProperty("JS_MERCHANT_MAIL_CC");
		String subject = PropertyLoader.getFileData().getProperty("JS_MERCHANT_SUBJECT");
		logger.info("Sending Email to :" + toAddress + " : " + ccAddress);
		Attachment fileAttach = null;

		List<Attachment> attachments = new ArrayList<Attachment>();
		attachments.add(fileAttach);
		String fromName = PropertyLoader.getFileData().getProperty("FROMNAME");
		String textbody = PropertyLoader.getFileData().getProperty("EZYSETTLE_TEXT_BODY");

		String textBody = "Dear " + merchant.getBusinessName() + "," + " <br><br>"
				+ "Your request is currently being processed. <b>We expect the funds to be settled in your account by the end of the day.</b>"
				+ " <br><br>" + "<b>Settlement Details: </b>" + " <br><br><b> <ul> <li>Amount Requested: </b> "
				+ pAmount.getTotalNetAmount() + " RM </li></ul>"
				+ "We apologize for any inconveniences caused by this temporary delay. Our team is working diligently to ensure the funds reach you as soon as possible."
				+ "<br><br><b>In the meantime, you can:</b> <br><br>"
				+ "  <ul><li> If you have any questions or require further assistance, please don't hesitate to contact our support team at "
				+ PropertyLoader.getFileData().getProperty("EZYSETTLE_CS_MAIL") + ". </li> </ul>"
				+ "Thank you for your patience and understanding." + " <br><br>Sincerely," + " <br><br>Mobi";
		ElasticEmail message = new ElasticEmail(fromAddress, subject, fromName, toAddress, ccAddress, ccAddress, null,
				textBody);
		ElasticEmailClient client = new ElasticEmailClient();

		try {

			client.sendemailSlipForEzysettle(message);
//			client.sendEzysettleMessage(message);
			logger.info("Email Sent Successfully to " + toAddress);
		} catch (Exception pe) {
			logger.info("Exception in mail sending " + pe.getMessage());
		}
	}

	public static String encodeFileToBase64Binary(String fileName) throws IOException {
		File file = new File(fileName);
		byte[] bytes = loadFile(file);
		byte[] encoded = Base64.encodeBase64(bytes);
		String encodedString = new String(encoded);
		return encodedString;
	}

	private static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		byte[] bytes = new byte[(int) file.length()];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}
		is.close();
		return bytes;
	}

	public String ezysettleEnable(double finalTotalofNetAmount1) {

		logger.info("Ezysettle Eligible " + finalTotalofNetAmount1);
		if (finalTotalofNetAmount1 < 0 || finalTotalofNetAmount1 == 0.0) {
			logger.info("Ezysettle Eligible No");
			return "No";
		} else {
			logger.info("Ezysettle Eligible Yes");
			return "Yes";
		}

	}

	public double findSlab(double FinalSumofPayableAmount) {
		double slab = 0.0;
		if (FinalSumofPayableAmount < 5000) {
			String slab1 = PropertyLoader.getFileData().getProperty("SLAB_1");
			logger.info("slab1 :" + slab1);
			slab = Double.parseDouble(slab1);
		} else {
			String slab2 = PropertyLoader.getFileData().getProperty("SLAB_2");
			logger.info("slab2 :" + slab2);
			slab = Double.parseDouble(slab2);
		}
		logger.info("slab :" + slab);
		return slab;
	}

	// To check 4999.4 to 5000.0
	public ReuseDto curlecFeeError(double totalNetAmount, double slab) {
		ReuseDto reuseDto = new ReuseDto();

		double afterDeductCurlecFee = 0.0;
		afterDeductCurlecFee = totalNetAmount - slab;
		logger.info("AfterDeductCurlecFee First Manipulation :" + afterDeductCurlecFee);
		if (afterDeductCurlecFee >= 4999.4 && afterDeductCurlecFee <= 4999.9) {
			afterDeductCurlecFee = totalNetAmount - slab;
			logger.info("AfterDeductCurlecFee second (if) Manipulation :" + afterDeductCurlecFee);
			// Round up to the nearest integer
			double roundedValue = Math.ceil(afterDeductCurlecFee);
			// Format to display with two decimal places
			String curlecRequestAmount = String.format("%.2f", roundedValue);

			logger.info("curlecRequestAmount :" + curlecRequestAmount);

			reuseDto.setCurlecRequestAmount(curlecRequestAmount);
			reuseDto.setAfterDeductCurlecFee(afterDeductCurlecFee);

		} else {
			afterDeductCurlecFee = totalNetAmount - slab;
			logger.info("AfterDeductCurlecFee second (Else) Manipulation :" + afterDeductCurlecFee);

			reuseDto.setCurlecRequestAmount(String.format("%.2f", afterDeductCurlecFee));
			reuseDto.setAfterDeductCurlecFee(afterDeductCurlecFee);
		}

		logger.info("afterDeductCurlecFee :" + afterDeductCurlecFee);
		reuseDto.setSlab(slab);
		return reuseDto;
	}

	public double calculateWithdrawalFee(String ipDate, String ipNetAmount, double percentage) {
		if (ipDate != null && !ipDate.isEmpty()) {
			String calcIpNetAmount = ipNetAmount.replace(",", "");
			double netAmount = Double.parseDouble(calcIpNetAmount);
			double withdrawalFee = netAmount * percentage / 100;
			logger.info("Net amount: " + netAmount);
			logger.info("Withdrawal fee: " + withdrawalFee);
			return withdrawalFee;
		}
		return 0.0; // Or handle null case accordingly
	}

	public String ezysettleReferenceNoGenerate() {

		LocalDateTime now = LocalDateTime.now();

		// Generate a random 4-digit number
		Random random = new Random();
		int random4Digit = random.nextInt(10000); // Generate random number between 0 and 9999

		// Format the date and time along with the random number
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
		String formattedDateTime = now.format(formatter);

		// Append the random 4-digit number
		String result = formattedDateTime + String.format("%04d", random4Digit);

		return result;
	}

	public String CSVFilePatn() {

		Date currentDate = new Date();

		// Create a SimpleDateFormat object with the desired format
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

		// Format the current date
		String formattedDate = dateFormat.format(currentDate);

		// Create a SimpleDateFormat object with the desired time format
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH_mm_ss");

		// Format the current time
		String formattedTime = timeFormat.format(currentDate);

		// new file pattern
		String filePrtn = PropertyLoader.getFile().getProperty("JS_CLEARENCE_PRTN") + formattedDate + "_"
				+ formattedTime + PropertyLoader.getFile().getProperty("FILE_PATTERN");

		return filePrtn;
	}

	public boolean validateMerchantAmountBeforePayoutInitiate(String curlecRequestAmount,
			List<String> requestSettlementDateList, Merchant merchant, String requestNetAmount,
			EzysettleDao ezysettleDao) {

		Utils utils = new Utils();
		DateTime mytDateAndTime = utils.currentMYT();
//		Merchant merchant = merchantService.loadMerchant(userName);
//		boolean executed = false;
		ConcurrentMap<String, Double> settledAmountsMap = new ConcurrentHashMap<>();
		ConcurrentMap<String, String> settledAmountsProductVise = new ConcurrentHashMap<>();

		requestSettlementDateList.stream().filter(a -> a != null && !a.isEmpty())
				.forEach(a -> logger.info("Request Settlement Date List in : " + a));
		requestSettlementDateList.stream().filter(a -> a != null && !a.isEmpty()).forEach(settlementdate -> {

			logger.info("settlementdate inside loop in : " + settlementdate);
			try {
				logger.info("ezysettleDao : " + ezysettleDao);
				/* To recheck the settlement amount for card transactions */
				SettlementMDR getsettlementonedatacard = ezysettleDao
						.loadNetAmountandsettlementdatebyCardEzysettle(settlementdate, merchant);

				String cardSettlementAmount = String.valueOf(getsettlementonedatacard == null ? 0.0
						: Double.parseDouble(getsettlementonedatacard.getNetAmount()));
				logger.info("Settlement Amount Card : " + cardSettlementAmount + " Settlement Date :" + settlementdate);
				settledAmountsProductVise.merge(settlementdate, "Card " + cardSettlementAmount,
						(oldValue, newValue) -> oldValue + ", " + newValue);

				/* To recheck the settlement amount for Boost transactions */
				BoostDailyRecon getsettlementonedataboost = ezysettleDao
						.loadNetAmountandsettlementdatebyBoostEzysettle(settlementdate, merchant);

				String boostSettlementAmount = String.valueOf(getsettlementonedataboost == null ? 0.0
						: Double.parseDouble(getsettlementonedataboost.getNetAmount()));
				logger.info(
						"Settlement Amount Boost : " + boostSettlementAmount + " Settlement Date :" + settlementdate);
				settledAmountsProductVise.merge(settlementdate, "Boost " + boostSettlementAmount,
						(oldValue, newValue) -> oldValue + ", " + newValue);

				/* To recheck the settlement amount for Grabpay transactions */
				GrabPayFile getsettlementonedatagrabpay = ezysettleDao
						.loadNetAmountandsettlementdatebyGrabpayEzysettle(settlementdate, merchant);

				String grabPaySettlementAmount = String.valueOf(getsettlementonedatagrabpay == null ? 0.0
						: Double.parseDouble(getsettlementonedatagrabpay.getNetAmt()));
				logger.info("Settlement Amount GrabPay : " + grabPaySettlementAmount + " Settlement Date :"
						+ settlementdate);
				settledAmountsProductVise.merge(settlementdate, "Grab " + grabPaySettlementAmount,
						(oldValue, newValue) -> oldValue + ", " + newValue);

				/* To recheck the settlement amount for FPX transactions */
				FpxTransaction getsettlementonedatafpx = ezysettleDao
						.loadNetAmountandsettlementdatebyFpxEzysettle(settlementdate, merchant);

				String fpxSettlementAmount = String.valueOf(getsettlementonedatafpx == null ? 0.0
						: Double.parseDouble(getsettlementonedatafpx.getPayableAmt()));
				logger.info("Settlement Amount FPX : " + fpxSettlementAmount + " Settlement Date :" + settlementdate);
				settledAmountsProductVise.merge(settlementdate, "FPX " + fpxSettlementAmount,
						(oldValue, newValue) -> oldValue + ", " + newValue);

				/* To recheck the settlement amount for M1pay transactions */
				EwalletTxnDetails getsettlementonedatam1Pay = ezysettleDao
						.loadNetAmountandsettlementdatebym1PayEzysettle(settlementdate, merchant);

				String m1paySettlementAmount = String.valueOf(getsettlementonedatam1Pay == null ? 0.0
						: Double.parseDouble(getsettlementonedatam1Pay.getPayableAmt()));
				logger.info(
						"Settlement Amount M1Pay : " + m1paySettlementAmount + " Settlement Date :" + settlementdate);
				settledAmountsProductVise.merge(settlementdate, "M1Pay " + m1paySettlementAmount,
						(oldValue, newValue) -> oldValue + ", " + newValue);

				// Accumulate total amount
				double totalAmount = Double.parseDouble(cardSettlementAmount)
						+ Double.parseDouble(boostSettlementAmount) + Double.parseDouble(grabPaySettlementAmount)
						+ Double.parseDouble(fpxSettlementAmount) + Double.parseDouble(m1paySettlementAmount);

				logger.info("Total Amount for Settlement Date : " + settlementdate + " is : " + totalAmount);

				settledAmountsMap.put(settlementdate, totalAmount);

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception occurred while processing settlement for date : " + settlementdate, e);
			}
		});

//		double totalAmount = totalAmounts.stream().mapToDouble(Double::doubleValue).sum();
		double totalAmount = settledAmountsMap.values().stream().mapToDouble(Double::doubleValue).sum();
		logger.info("Recheck Total Amount for this merchant :" + totalAmount);
		logger.info("Merchant Request Amount from JSP : " + requestNetAmount);
		double merchantRequestAmountFromJSP = Double.parseDouble(requestNetAmount);

		return Double.compare(merchantRequestAmountFromJSP, totalAmount) == 0;

	}

	public String generateCSVForSuccessfulEzySettle(String finalPayoutDate, Merchant currentMerchant,
			MerchantService merchantService, String today1, String csvFile) {

		try {

			Payer pAmount = null;
			pAmount = merchantService.getPayerAmount(finalPayoutDate, currentMerchant, "ezysettleSuccess");

			logger.info(" Curlec Response Success ");
			if (!pAmount.getTotalNetAmount().equals("0.00")) {
				logger.info("After successful transaction, sending email notification.");
				EzysettleCSV(pAmount, currentMerchant, merchantService, finalPayoutDate, today1, csvFile,
						"ezysettleSuccess");
			} else {
				logger.info("No Record Found");
			}
			if (!pAmount.getTotalNetAmount().equals("0.00")) {
				String clearencefile = PropertyLoader.getFileData().getProperty("JSCSV_CLEARENCEFILEPATH") + csvFile;

				logger.info("UM_CS Clearence File : " + clearencefile);
				File file = new File(clearencefile);
				if (!file.exists()) {
					logger.info("Clearence File Not Generated");
				} else {
					logger.info("Clearence File is Generated");
					this.sendEmailtoSuccessEzysettle(clearencefile);
				}
			}
		} catch (Exception e) {
			logger.info("Exception while sending mail : " + e);
		}

		return null;
	}

	public void sendEmailtoSuccessEzysettle(String filename) {

		logger.info("Sending UM Clearance csv Mail " + filename);

		String fromAddress = PropertyLoader.getFileData().getProperty("FROMMAIL");
		String apiKey = PropertyLoader.getFileData().getProperty("APIKEY");
		String toAddress = PropertyLoader.getFileData().getProperty("JS_CSV_MAIL_TO");
		String ccAddress = PropertyLoader.getFileData().getProperty("JS_CSV_MAIL_CC");
		String subject = PropertyLoader.getFileData().getProperty("JS_CSV_SUBJECT_EZYSETTLE_SUCCESS")
				+ new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		logger.info("Sending Email to :" + toAddress + " : " + ccAddress);
		String fromName = PropertyLoader.getFileData().getProperty("FROMNAME");
		String textbody = PropertyLoader.getFileData().getProperty("TEXT_BODY");
		String successTextBody = PropertyLoader.getFileData().getProperty("SUCCESS_TEXT_BODY");
		String attachment = filename;

		logger.info("Attachment Path :" + attachment);
		String textBody = "Hi Finance Team," + "\n\n" + successTextBody + "\n\n" + textbody + " "
				+ new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
		ElasticEmail message = new ElasticEmail(fromAddress, subject, fromName, toAddress, ccAddress, ccAddress,
				attachment, textBody);
		ElasticEmailClient client = new ElasticEmailClient();

		try {
			client.sendEzysettleMessage(message);
			logger.info("Email Sent Successfully to " + toAddress);
		} catch (Exception pe) {
			logger.info("Exception in mail sending " + pe.getMessage());
		}
	}

	public List<Double> filterCurrentDayTransactions(String finalSettlementDate, double amount, MobiMDR mobiMdr,
			TransactionDao transactionDAO, List<Double> totalAmounts, LocalDate currentDate) {
//		

//		LocalDate currentDate = LocalDate.now();
//
//		String settlePeriod = Optional.ofNullable(mobiMdr).map(m -> m.getSettlePeriod()).orElse(null);
//
//		if (settlePeriod == null) {
//			throw new RuntimeException("The settle period is either empty or null.");
//		}
//		logger.info("settlePeriod : " + settlePeriod);
//		int daysToAdd = Integer.parseInt(settlePeriod);
//
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		String formattedDate = null;
//		LocalDate newDate = currentDate.plusDays(daysToAdd);
//
//		/* Format the new date */
//		formattedDate = newDate.format(formatter);
//	
//		/* Check the condition before the loop */
//
//		int count = 0;
//		while (true) {
//			logger.info("Loop running Count : " + count++);
//
//			/* Check if the formatted date is a holiday or weekend */
//			boolean isHoliday = transactionDAO.loadHolidayHistory(formattedDate);
//			DayOfWeek dayOfWeek = newDate.getDayOfWeek();
//
//			if (!isHoliday && dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
//				/* Valid date found, exit the loop */
//				break;
//			}
//
//			/* Increment days and reformat the date */
//			daysToAdd++;
//			newDate = currentDate.plusDays(daysToAdd);
//			formattedDate = newDate.format(formatter);
//		}
//		logger.info("finalSettlementDate : " + finalSettlementDate + "formattedDate : " + formattedDate);
//		logger.info("Size : "+totalAmounts.size());
//		totalAmounts.add(finalSettlementDate.equals(formattedDate) ? 0.00 : amount);
//
//		totalAmounts.stream().forEach(System.out::println);
//
//		return totalAmounts;
//		
//		
		try {

			String settlePeriod = Optional.ofNullable(mobiMdr).map(m -> m.getSettlePeriod()).orElse(null);

			if (settlePeriod == null) {
				throw new RuntimeException("The settle period is either empty or null.");
			}
			logger.info("settlePeriod : " + settlePeriod);
			int daysToAdd = Integer.parseInt(settlePeriod);

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			logger.info("formatter : " + formatter);
			LocalDate providerSettlementDate = LocalDate.parse(finalSettlementDate, formatter);
			logger.info("providerSettlementDate : " + providerSettlementDate);

			try {
				totalAmounts = getValidatedSettlementDate(currentDate, daysToAdd, providerSettlementDate, totalAmounts,
						amount, transactionDAO);
				logger.info("############# SUCCESS #############");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Exception while filterCurrentDayTransactions: " + e);
			}
			totalAmounts.stream().forEach(System.out::println);

			logger.info(totalAmounts.size());

		} catch (IllegalArgumentException e) {
			logger.error("Exception while filterCurrentDayTransactions:" + e.getMessage());
			totalAmounts.add(0.0);
		}catch (Exception e) {
			logger.error("Exception : " + e.getMessage());
			totalAmounts.add(0.0);
		}
		return totalAmounts;

//		LocalDate currentDate = LocalDate.now();
//		
//		String settlePeriod = Optional.ofNullable(mobiMdr)
//                .map(m -> m.getSettlePeriod())
//                .orElse(null);
//
//		if(settlePeriod == null) {
//			throw new RuntimeException("The settle period is either empty or null.");
//		}
//		logger.info("settlePeriod : "+settlePeriod);
//		int daysToAdd = Integer.parseInt(settlePeriod);
////		LocalDate newDate = currentDate.plusDays(daysToAdd);
//		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//		String formattedDate = null;
//		int count = 0;
//		while (true) {
//			logger.info("Loop running Count : "+count++);
//			LocalDate newDate = currentDate.plusDays(daysToAdd);
//			/* Format the new date */
//			formattedDate = newDate.format(formatter);
//
//			/* Check if the formatted date is a holiday or weekend */
//			boolean isHoliday = transactionDAO.loadHolidayHistory(formattedDate);
//			DayOfWeek dayOfWeek = newDate.getDayOfWeek();
//
////			if (isHoliday || dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
////				/* Increment daysToAdd and update newDate */
////				daysToAdd++;
////				newDate = currentDate.plusDays(daysToAdd);
////			} else {
////
////				break;
////			}
//			
//			if (!isHoliday && dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
//				 /* Valid date found, exit the loop */
//		        break; 
//		    }
//			daysToAdd++;
//		}
//
//		 logger.info(totalAmounts.size());
//		totalAmounts.add(finalSettlementDate.equals(formattedDate) ? 0.00 : amount);
//		
//		 totalAmounts.stream()
//         .forEach(System.out::println);
//		
//		 
//		 logger.info(totalAmounts.size());
//		 
//		return totalAmounts;
	}

	public static List<Double> getValidatedSettlementDate(LocalDate currentDate, int settlementPeriod,
			LocalDate providedSettlementDate, List<Double> totalAmounts, double amount, TransactionDao transactionDao)
			throws Exception {
		logger.info("currentDate : " + currentDate + " providedSettlementDate : " + providedSettlementDate
				+ " settlement period : " + settlementPeriod);
		LocalDate calculatedSettlementDate = calculateSettlementDate(currentDate, settlementPeriod, transactionDao);
		logger.info("Calculated Settlement Date : " + calculatedSettlementDate + " Amount : " + amount);

		logger.info("Condition  : " + calculatedSettlementDate.equals(providedSettlementDate));
		totalAmounts.add(calculatedSettlementDate.equals(providedSettlementDate) ? 0.00 : amount);

//		 // Check if the providedSettlementDate is a weekend
//	    boolean isProvidedDateWeekend = providedSettlementDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
//	                                    providedSettlementDate.getDayOfWeek() == DayOfWeek.SUNDAY;
//
//	    if (currentDate.equals(providedSettlementDate)) {
//	    	logger.info("case 1");
//	        // Case 1: If the current date matches the provided date, it should be 0.00.
//	        totalAmounts.add(0.00);
//	    } else if (providedSettlementDate.equals(calculatedSettlementDate)) {
//	    	logger.info("case 2");
//	        // Case 2: If the provided date is the calculated settlement date, it should also be 0.00.
//	        totalAmounts.add(0.00);
//	    } else if (isProvidedDateWeekend && providedSettlementDate.isAfter(currentDate) && providedSettlementDate.isBefore(calculatedSettlementDate)) {
//	    	logger.info("case 3");
//	    	// Case 3: If the provided date is a weekend and falls between the current date and calculated settlement date, show the specified amount.
//	        totalAmounts.add(amount);
//	    } else {
//	    	logger.info("case 4");
//	        // Default case: Show the specified amount.
//	        totalAmounts.add(amount);
//	    }

		return totalAmounts;

	}

//	public static LocalDate calculateSettlementDate(LocalDate startDate, int settlementPeriodDays,
//			TransactionDao transactionDao) throws Exception {
//
//
//	    LocalDate settlementDate = startDate;
//	    int daysAdded = 0;
//	    try {
//	        logger.info("Start Date: " + settlementDate);
//	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//	        int validDaysCount = 0;
//
//	        while (validDaysCount < settlementPeriodDays) {
//	            settlementDate = settlementDate.plusDays(1); // Increment the day
//	            String formattedDate = settlementDate.format(formatter);
//
//	            boolean isHoliday = transactionDao.loadHolidayHistory(formattedDate);
//	            boolean isWeekend = settlementDate.getDayOfWeek() == DayOfWeek.SATURDAY || 
//	                                settlementDate.getDayOfWeek() == DayOfWeek.SUNDAY;
//
//	            logger.info("Checking Date: " + settlementDate + ", Is Holiday: " + isHoliday + ", Is Weekend: " + isWeekend);
//
//	            // If it's not a weekend or holiday, count it as a valid day
//	            if (!isWeekend && !isHoliday) {
//	                validDaysCount++;
//	                logger.info("Valid Day Count: " + validDaysCount);
//	            }
//	        }
//	    } catch (Exception e) {
//	        logger.error("Error calculating settlement date", e);
//	        throw e;
//	    }
//		return settlementDate;
//	}

//	public static LocalDate calculateSettlementDate(LocalDate startDate, int settlementPeriodDays,
//			TransactionDao transactionDao) throws Exception {
//
//		LocalDate settlementDate = startDate;
//		int validDaysCount = 0; // To count valid business days
//
//		try {
//			logger.info("Start Date: " + settlementDate);
//			logger.info("settlementPeriodDays : " + settlementPeriodDays);
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//// Loop until we have counted the required number of valid business days
//			while (validDaysCount == settlementPeriodDays) {
//				String formattedDate = settlementDate.format(formatter);
//				// Check is public holiday or Weekends
//				settlementDate = settlementDate.plusDays(1);
//				boolean isHoliday = transactionDao.loadHolidayHistory(formattedDate);
//				boolean isWeekend = settlementDate.getDayOfWeek() == DayOfWeek.SATURDAY
//						|| settlementDate.getDayOfWeek() == DayOfWeek.SUNDAY;
//				if(isHoliday || isWeekend) {
//					continue;
//				}
//				validDaysCount++;
//				
//			}
//			logger.info("Checking Date: " + settlementDate + ", Is Holiday: , Is Weekend: ");
////
////				settlementDate = settlementDate.plusDays(1); // Move to the next day
////				String formattedDate = settlementDate.format(formatter);
////
////				boolean isHoliday = transactionDao.loadHolidayHistory(formattedDate);
////				boolean isWeekend = settlementDate.getDayOfWeek() == DayOfWeek.SATURDAY
////						|| settlementDate.getDayOfWeek() == DayOfWeek.SUNDAY;
////
////				logger.info("Checking Date: " + settlementDate + ", Is Holiday: " + isHoliday + ", Is Weekend: "
////						+ isWeekend);
////
////// If it's not a weekend or holiday, count it as a valid day
////				if(isHoliday) {
////					validDaysCount++; 
////				}else if(isWeekend) {
////					validDaysCount++; 
////				}
//				
//				logger.info("Valid Day Count: " + validDaysCount);
//				
////				if (!isWeekend && !isHoliday) {
////					validDaysCount++; // Increment valid business day count
////					logger.info("Valid Day Count: " + validDaysCount);
////				}
//			
//		} catch (Exception e) {
//			logger.error("Error calculating settlement date", e);
//			throw e;
//		}
//		return settlementDate;
//	}

	public static LocalDate calculateSettlementDate(LocalDate startDate, int settlementPeriodDays,
			TransactionDao transactionDao) throws Exception {

		LocalDate settlementDate = startDate;

//		if(settlementDate.getDayOfWeek() == DayOfWeek.SATURDAY
//				|| settlementDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
//			logger.info("Today is a weekend, so the transaction amount for the settlement date " + settlementDate + " is shown.");
//			return settlementDate;
//		}

		int validDaysCount = 0; // To count valid business days

		logger.info("Start Date after settle period : " + settlementDate);

		// saravana
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		for (int i = 1; i <= settlementPeriodDays; i++) {
			logger.info("i : " + i);
			settlementDate = settlementDate.plusDays(1);

			String formattedDate = settlementDate.format(formatter);

			logger.info("First settlementDate : " + settlementDate);
			boolean isHoliday = transactionDao.loadHolidayHistory(formattedDate);
			boolean isWeekend = settlementDate.getDayOfWeek() == DayOfWeek.SATURDAY
					|| settlementDate.getDayOfWeek() == DayOfWeek.SUNDAY;

			if (isHoliday || isWeekend) {

				while (true) {
					settlementDate = settlementDate.plusDays(1);

					logger.info("Second settlementDate : " + settlementDate);

					String formattedDate1 = settlementDate.format(formatter);
					boolean isHoliday1 = transactionDao.loadHolidayHistory(formattedDate1);
					boolean isWeekend1 = settlementDate.getDayOfWeek() == DayOfWeek.SATURDAY
							|| settlementDate.getDayOfWeek() == DayOfWeek.SUNDAY;

					if (isHoliday1 == false && isWeekend1 == false) {
						break;
					}
				}

			}

		}

		logger.info("************ Final Formated Date *********** : " + settlementDate);

		return settlementDate;

//		try {
//			logger.info("Start Date: " + settlementDate);
//			logger.info("Settlement Period Days: " + settlementPeriodDays);
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//			
//			
//			while (validDaysCount < settlementPeriodDays) {
//				String formattedDate = settlementDate.format(formatter);
//				// Check if it's a public holiday or weekend
//				boolean isHoliday = transactionDao.loadHolidayHistory(formattedDate);
//				boolean isWeekend = settlementDate.getDayOfWeek() == DayOfWeek.SATURDAY
//						|| settlementDate.getDayOfWeek() == DayOfWeek.SUNDAY;
//
//				if (!isHoliday && !isWeekend) {
//					validDaysCount++;
//				}
//
//				// Move to the next day after checking
//				settlementDate = settlementDate.plusDays(1);
//			}
//			logger.info("Settlement Date: " + settlementDate);
//		} catch (Exception e) {
//			logger.error("Error calculating settlement date", e);
//			throw e;
//		}

	}

//	public static LocalDate calculateSettlementDate(LocalDate startDate, int settlementPeriodDays,
//			TransactionDao transactionDao) throws Exception {
//
//		LocalDate settlementDate = startDate;
//		int validDaysCount = 0; // To count valid business days
//
//		try {
//			logger.info("Start Date: " + settlementDate);
//			logger.info("Settlement Period Days: " + settlementPeriodDays);
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//
//			while (validDaysCount < settlementPeriodDays) {
//				String formattedDate = settlementDate.format(formatter);
//				// Check if it's a public holiday or weekend
//				boolean isHoliday = transactionDao.loadHolidayHistory(formattedDate);
//				boolean isWeekend = settlementDate.getDayOfWeek() == DayOfWeek.SATURDAY
//						|| settlementDate.getDayOfWeek() == DayOfWeek.SUNDAY;
//
//				if (!isHoliday && !isWeekend) {
//					validDaysCount++;
//				}
//
//				// Move to the next day after checking
//				settlementDate = settlementDate.plusDays(1);
//			}
//			logger.info("Settlement Date: " + settlementDate);
//		} catch (Exception e) {
//			logger.error("Error calculating settlement date", e);
//			throw e;
//		}
//
//		return settlementDate;
//	}

}
