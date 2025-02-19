package com.mobiversa.payment.controller;

import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.connect.MotoPaymentCommunication;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.util.PreauthModel;
import com.mobiversa.payment.util.ResponseDetails;

@Controller
@RequestMapping(value = FiuuTransactionsController.URL_BASE)
public class FiuuTransactionsController extends BaseController{

	public static final String URL_BASE = "/fiuuApi";
	
	@Autowired
	MerchantService merchantService;
	
	private static final Logger logger = Logger.getLogger(FiuuTransactionsController.class);
	
	@RequestMapping(value = "/fiuuConvertToSale")
	public @ResponseBody PreauthModel ConvertToSale(@RequestParam String amount,

			@RequestParam String userAmount, @RequestParam String txnid, HttpServletRequest request,
			HttpServletResponse response, Model model) {
			
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		double txnAmount = 0;
		double preAmount = 0;
		double sAmount = 0;
		double eAmount = 0;
		double fAmount = 0;
		double usersAmount = 0;
		String formattedAmount = null;
		String status = null;
		String preAmt = null;
		PreauthModel preAuth = new PreauthModel();

		logger.info("Amount: " + amount);
		logger.info("userAmount: " + userAmount);
		logger.info("txnid: " + txnid);
		
		String finalAmount = amount.replace(",", "");
		String finaluserAmount = userAmount.replace(",", "");
		logger.info("finalAmt: " + finalAmount);
		logger.info("finaluserAmount: " + finaluserAmount);

		if (finaluserAmount != null && !finaluserAmount.isEmpty()) {
			preAmount = Double.parseDouble(finalAmount);
			logger.info("preAmount: " + preAmount);
			usersAmount = Double.parseDouble(finaluserAmount);
			logger.info("usersAmount: " + usersAmount);
			
			preAmt = finaluserAmount;
			
			
			if (usersAmount > 0 && usersAmount <= preAmount) {
				logger.info("Convert To Sale is Processing: ");

				if (preAmt.contains(".")) {

					  formattedAmount = String.format("%012d", (long) Double.parseDouble(preAmt.replace(".", "")));
					    
					    // Convert formattedAmount to a double before formatting it using DecimalFormat
					    double amountToFormat = Double.parseDouble(formattedAmount) / 100; // Divide by 100 to account for the previously removed decimal point
					    
					    String pattern = "#,##0.00";
					    DecimalFormat myFormatter = new DecimalFormat(pattern);
					    String output = myFormatter.format(amountToFormat);
					    
					    preAuth.setAmount(output);
					    
					    logger.info("Parsed Amount: " + preAuth.getAmount());
				} else {
					//preAuth.setAmount(String.format("%012d", (long) Double.parseDouble(preAmt) * 100));
					//preAuth.setAmount(String.valueOf(Double.parseDouble(preAmt)));
					
					  formattedAmount = String.format("%012d", (long) Double.parseDouble(preAmt.replace(".", "")));
					    
					    // Convert formattedAmount to a double before formatting it using DecimalFormat
					    double amountToFormat = Double.parseDouble(formattedAmount) / 100; // Divide by 100 to account for the previously removed decimal point
					    
					    String pattern = "#,##0.00";
					    DecimalFormat myFormatter = new DecimalFormat(pattern);
					    String output = myFormatter.format(amountToFormat);
					    
					    preAuth.setAmount(output);
					    
					    logger.info("Parsed Amount: " + preAuth.getAmount());
				}
			

				preAuth.setMerchantId(currentMerchant.getId());
				logger.info("Merchant ID " + currentMerchant.getId());

				// Host Type

				String merchantType = currentMerchant.getMerchantType();

				if (merchantType == null) {

					preAuth.setMerchantType("P");

				} else if (merchantType.equalsIgnoreCase("P")) {
					preAuth.setMerchantType("P");

				} else if (merchantType.equalsIgnoreCase("U")) {

					preAuth.setMerchantType("U");

				}else if (merchantType.equalsIgnoreCase("FIUU")) {

					preAuth.setMerchantType("FIUU");

				}

				logger.info("Merchant Type " + preAuth.getMerchantType());

				// Transaction ID

				preAuth.setTxnid(txnid);
				logger.info("Transaction ID " + txnid);

				ResponseDetails data = MotoPaymentCommunication.convertToSaleForFiuu(preAuth);
				if (data != null) {
					if (data.getResponseCode().equals("0000")) {
						logger.info("Convert To Sale Done");
						preAuth.setResponseCode("0000");
						preAuth.setResponseDescription("Convert To Sale Done ");
					} else {
						logger.info("Failed To Convert To Sale");
						preAuth.setResponseCode("0000");
						preAuth.setResponseDescription("Failed To Convert To Sale");
					}
				}

			} else {
				preAuth.setResponseCode("0001");
				preAuth.setResponseDescription("Failed To Convert To Sale.");
			}

		} else {

			preAmt = finalAmount;

			logger.info("Convert To Sale is Processing: ");

			// Amount
			if (preAmt.contains(".")) {

				  formattedAmount = String.format("%012d", (long) Double.parseDouble(preAmt.replace(".", "")));
				    
				    // Convert formattedAmount to a double before formatting it using DecimalFormat
				    double amountToFormat = Double.parseDouble(formattedAmount) / 100; // Divide by 100 to account for the previously removed decimal point
				    
				    String pattern = "#,##0.00";
				    DecimalFormat myFormatter = new DecimalFormat(pattern);
				    String output = myFormatter.format(amountToFormat);
				    
				    preAuth.setAmount(output);
				    
				    logger.info("Parsed Amount: " + preAuth.getAmount());
			} else {
				  formattedAmount = String.format("%012d", (long) Double.parseDouble(preAmt.replace(".", "")));
				    
				    // Convert formattedAmount to a double before formatting it using DecimalFormat
				    double amountToFormat = Double.parseDouble(formattedAmount) / 100; // Divide by 100 to account for the previously removed decimal point
				    
				    String pattern = "#,##0.00";
				    DecimalFormat myFormatter = new DecimalFormat(pattern);
				    String output = myFormatter.format(amountToFormat);
				    
				    preAuth.setAmount(output);
				    
				    logger.info("Parsed Amount: " + preAuth.getAmount());
			}
			// Merchant ID

			preAuth.setMerchantId(currentMerchant.getId());
			logger.info("Merchant ID " + currentMerchant.getId());

			// Host Type

			String merchantType = currentMerchant.getMerchantType();

			if (merchantType == null) {

				preAuth.setMerchantType("P");

			} else if (merchantType.equalsIgnoreCase("P")) {
				preAuth.setMerchantType("P");

			} else if (merchantType.equalsIgnoreCase("U")) {

				preAuth.setMerchantType("U");

			}else if (merchantType.equalsIgnoreCase("FIUU")) {

				preAuth.setMerchantType("FIUU");

			}

			logger.info("Merchant Type " + preAuth.getMerchantType());

			// Transaction ID

			preAuth.setTxnid(txnid);
			logger.info("Transaction ID " + txnid);

			ResponseDetails data = MotoPaymentCommunication.convertToSaleForFiuu(preAuth);

			if (data != null) {
				if (data.getResponseCode().equals("0000")) {
					logger.info("Convert To Sale Done");
					preAuth.setResponseCode("0000");
					preAuth.setResponseDescription("Convert To Sale Done ");
				} else {
					logger.info("Failed To Convert To Sale");
					preAuth.setResponseCode("0000");
					preAuth.setResponseDescription("Failed To Convert To Sale");
				}
			}

		}
		return preAuth;
	}
}
