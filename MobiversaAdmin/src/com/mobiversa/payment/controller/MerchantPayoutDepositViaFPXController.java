package com.mobiversa.payment.controller;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.payment.connect.ResponseData;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.util.PropertyLoad;

@Controller
@RequestMapping(value = MerchantPayoutDepositViaFPXController.URL_BASE)
public class MerchantPayoutDepositViaFPXController extends BaseController {

	@Autowired
	private MerchantService merchantService;

	public static final String URL_BASE = "/merchant/mobi"; //$NON-NLS-1$

	private static final int MAX_LENGTH = 6;
	private static final Set<String> generatedIds = new HashSet<>();

	private static final Logger logger = Logger.getLogger(MerchantPayoutDepositViaFPXController.class);

	@SuppressWarnings("nls")
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public static String defaultPage() {
		return "redirect:" + URL_BASE + "/doFpx";
	}

	@SuppressWarnings({ "nls", "unqualified-field-access" })
	@RequestMapping(value = { "/doFpx" }, method = RequestMethod.POST)
	public String processDirectToFPX(@RequestParam String transactionAmount, @RequestParam String bankCode,
			@RequestParam String bankName, HttpServletRequest request, final Model model) {

		logger.info("Transaction Amount: {}" + transactionAmount);
		logger.info("Bank Name: {}" + bankName);
		logger.info("Bank Code: {}" + bankCode);
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");
		logger.info("Currently logged in as: {}" + userName);

		logger.info("Initiating Process Direct To FPX: {}" + userName);

		Merchant currentMerchant = merchantService.loadMerchant(userName);

		MobileUser mobileUser = merchantService.loadMobileUserById(currentMerchant.getId());

		String sellerOrderNo = generateTransactionId();

		logger.info("Generated Transaction ID: " + sellerOrderNo);

		String callBackURL = PropertyLoad.getFile().getProperty("FpxCallBackURL");
		String redirectURL = PropertyLoad.getFile().getProperty("FpxRequestURL");

		String mid = null;
		String tid = null;

		if (currentMerchant.getMid().getFpxMid() != null && !currentMerchant.getMid().getFpxMid().isEmpty()) {
			mid = currentMerchant.getMid().getFpxMid();
		} else if (currentMerchant.getMid().getUmEzywayMid() != null
				&& !currentMerchant.getMid().getUmEzywayMid().isEmpty()) {
			mid = currentMerchant.getMid().getUmEzywayMid();
		}

		if (mobileUser.getFpxTid() != null && !mobileUser.getFpxTid().isEmpty()) {
			tid = mobileUser.getFpxTid();
		} else if (mobileUser.getEzywayTid() != null && !mobileUser.getEzywayTid().isEmpty()) {
			tid = mobileUser.getEzywayTid();
		}

		// FPX Post Parameters

		ResponseData data = new ResponseData();

		data.setRequestUrl(redirectURL);
		data.setAmount(transactionAmount);
		data.setRedirectUrl(callBackURL);
		data.setSellerOrderNo(sellerOrderNo);
		data.setBankType("01");
		data.setMid(mid);
		data.setBuyerName(currentMerchant.getUsername());
		data.setTid(tid);
		data.setMerchantName(currentMerchant.getBusinessName());
		data.setProductDesc(null);
		data.setBank(bankCode);
		data.setService("FULL_LIST");
		data.setMobiLink(null);
		data.setEmail(null);

		request.setAttribute("data", data);

		return "merchantweb/transaction/FpxRequestRedirect";

	}

	public static String generateTransactionId() {
		Random random = new Random();
		String transactionId = null;

		int attempts = 0;
		while (attempts < 10) {
			transactionId = generateRandomId(random, MAX_LENGTH);
			if (generatedIds.add(transactionId)) {
				// Unique ID found, break the loop
				break;
			}
			attempts++;
		}

		return transactionId;
	}

	private static String generateRandomId(Random random, int length) {
		StringBuilder idBuilder = new StringBuilder();

		for (int i = 0; i < length; i++) {
			idBuilder.append(random.nextInt(10));
		}

		return idBuilder.toString();
	}

}
