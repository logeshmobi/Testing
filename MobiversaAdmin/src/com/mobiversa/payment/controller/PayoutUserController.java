package com.mobiversa.payment.controller;

import static com.mobiversa.payment.util.AuthenticationProcessUtil.getCurrentUserAuthentication;
import static com.mobiversa.payment.util.AuthenticationProcessUtil.isUserAuthenticatedForPayoutIpnTrigger;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.PayoutDetail;
import com.mobiversa.common.bo.PayoutGrandDetail;
import com.mobiversa.payment.controller.bean.Apibean;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.controller.bean.PayoutBean;
import com.mobiversa.payment.controller.bean.PayoutUpdateApi;
import com.mobiversa.payment.controller.bean.Settlementbalance;
import com.mobiversa.payment.controller.bean.UpdateApi;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dao.TransactionDao;
import com.mobiversa.payment.exception.MobiException;
import com.mobiversa.payment.service.AccountEnquiryService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.ElasticEmailClient;
import com.mobiversa.payment.util.EmailUtils;
import com.mobiversa.payment.util.PayoutData;
import com.mobiversa.payment.util.PayoutModel;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.SettlementJobAPI;

@Controller
@RequestMapping(value = PayoutUserController.URL_BASE)
public class PayoutUserController extends BaseController {

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private MerchantDao merchantDao;

	@Autowired
	private TransactionDao transactionDao;

	@Autowired
	private AccountEnquiryService accountEnquiryService;

	public static final String URL_BASE = "/payoutDataUser";
	private static final Logger logger = Logger.getLogger(PayoutUserController.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("Default Page Merchant");
		return "redirect:" + URL_BASE + "/list/1";
	}

	// Payout Summary - Start

	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String settlement(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {

		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/PayoutList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Payout Summary:" + principal.getName());
		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.listPayoutTransaction(paginationBean, null, null, null);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found");

		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_PAYOUTUSER;

	}

	@RequestMapping(value = { "/searchPayout" }, method = RequestMethod.GET)
	public String displayEzyLinkTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/PayoutList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);

		if ((date == null || date.equalsIgnoreCase("null")) && (date1 == null || date1.equalsIgnoreCase("null"))) {
			transactionService.listPayoutTransaction(paginationBean, null, null, null);
		} else {
			transactionService.listPayoutTransaction(paginationBean, date, date1, null);
		}
//      transactionService.listPayoutTransaction(paginationBean, date, date1);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_PAYOUTUSER;

	}

	@RequestMapping(value = "/exportPayout", method = RequestMethod.GET)
	public ModelAndView getExcelEzyLink(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.listPayoutTransaction(paginationBean, date, date1, export);

		List<PayoutModel> list = paginationBean.getItemList();
		logger.info("list  : " + list.size());
		if (list == null || list.isEmpty()) {
			model.addAttribute("responseData", "No Records found");
			PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/PayoutList", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("responseData", "No Records found");
			logger.info("paginationBean : " + paginationBean);
			model.addAttribute("paginationBean", paginationBean);
			logger.info("list : " + list.size());
			return new ModelAndView("template/tpl_payoutUser");
		} else {
			if (!(export.equals("PDF"))) {
				logger.info("pagination is not empty: " + list);
				return new ModelAndView("PayoutSummaryExcel", "txnList", list);
			} else {
				logger.info("pagination is not empty: " + list);
				return new ModelAndView("PayoutSummaryPdf", "txnList", list);
			}
		}
	}
	
	@RequestMapping(value = "/exportPayoutInPayoutLogin", method = RequestMethod.GET)
	public ModelAndView exportPayoutInPayoutLogin(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.listPayoutTransaction(paginationBean, date, date1, export);

		List<PayoutModel> list = paginationBean.getItemList();
		logger.info("list  : " + list.size());
		if (list == null || list.isEmpty()) {
			model.addAttribute("responseData", "No Records found");
			PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/PayoutList", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("responseData", "No Records found");
			logger.info("paginationBean : " + paginationBean);
			model.addAttribute("paginationBean", paginationBean);
			logger.info("list : " + list.size());
			return new ModelAndView("template/tpl_payoutUser");
		} else {
			if (!(export.equals("PDF"))) {
				logger.info("pagination is not empty: " + list);
				return new ModelAndView("PayoutLoginSummaryExcel", "txnList", list);
			} else {
				logger.info("pagination is not empty: " + list);
				return new ModelAndView("PayoutSummaryPdf", "txnList", list);
			}
		}
	}

	@RequestMapping(value = { "/updatePayoutStatus" }, method = RequestMethod.GET)
	public String updatePayoutStatus(final Model model, @RequestParam("txnId") final String txnId,
			@RequestParam("merchantID") final Long merchantID, final java.security.Principal principal,
			final HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		PayoutDetail payoutData = new PayoutDetail();

		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/PayoutList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		logger.info("Merchant ID : " + merchantID);
		logger.info("Transaction ID : " + txnId);

		// Accordingly, update the paid status and date - Start

		Date currentDate = new Date();
		String paidDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
		String paidTime = new SimpleDateFormat("HH:mm:ss").format(currentDate);
		logger.info("Paid Date : " + paidDate + " Paid Time : " + paidTime);

		payoutData.setInvoiceIdProof(txnId);
		payoutData.setPaidDate(paidDate);
		payoutData.setPaidTime(paidTime);
		payoutData.setPayoutStatus("pp");

		// Accordingly, update the paid status and date - End

		merchantService.updatePayoutStatus(payoutData);

		PayoutGrandDetail payoutDetail = merchantService.PayoutGrandDetailbymerchantid(merchantID);

		if (payoutDetail.getIpnUrl() == null) {
			logger.info("IPN Url is empty");
		} else if (payoutDetail.getIpnUrl() != null) {
			logger.info("IPN Url :" + payoutDetail.getIpnUrl());
			transactionService.PayoutCallBack(txnId, payoutDetail.getIpnUrl());

		}

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.listPayoutTransaction(paginationBean, null, null, null);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_PAYOUTUSER;

	}

	@RequestMapping(value = { "/declinePayoutStatus" }, method = RequestMethod.GET)
	public String declinePayoutStatus(final Model model, @RequestParam("txnId1") final String txnId1,
			@RequestParam("merchantID1") final Long merchantID1,
			@RequestParam("cancelReason") final String cancelReason, final java.security.Principal principal,
			final HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		PayoutDetail Status = new PayoutDetail();
		Date currentDate = new Date();
		String paidDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
		String paidTime = new SimpleDateFormat("HH:mm:ss").format(currentDate);
		logger.info("Decline Date : " + paidDate + " Decline Time : " + paidTime);
		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/PayoutList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		logger.info("Merchant ID : " + merchantID1);
		logger.info("Transaction ID : " + txnId1);
		logger.info("cancelReason : " + cancelReason);
		Status.setInvoiceIdProof(txnId1);
		Status.setPayoutStatus("pd");
		Status.setPaidTime(paidTime);
		Status.setPaidDate(paidDate);
//        Status.setPaymentReason(cancelReason);
		Status.setPaymentReason(cancelReason);
		merchantService.declinePayoutStatus(Status);

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.listPayoutTransaction(paginationBean, null, null, null);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_PAYOUTUSER;
	}

	// Payout Summary - End

	// Merchant Topup Added by Dhinesh - Start
//	@RequestMapping(value = { "/merchantTopupList" }, method = RequestMethod.GET)
//	public String merchantTopupList(final Model model, final java.security.Principal principal) {
//
//		PageBean pageBean = new PageBean("merchant Topup List", "PayoutUser/merchantTopupList", Module.TRANSACTION,
//				"transaction/sideMenuTransaction");
//		model.addAttribute("pageBean", pageBean);
//		// oldtopup
//		// List<Merchant> merchant = merchantService.loadMerchant();
//		List<Merchant> merchant = merchantService.loadMerchant1();
//		for (Merchant m : merchant) {
//			if (m != null) {
//
//				if (m.getBusinessName() != null && !m.getBusinessName().isEmpty()) {
//					m.setBusinessName(m.getBusinessName().toUpperCase());
//
//				} else {
//					m.setBusinessName("NIL");
//				}
//			}
//		}
//
//		model.addAttribute("pageBean", pageBean);
//		model.addAttribute("merchant1", merchant);
//
//		return TEMPLATE_PAYOUTUSER;
//	}

	@RequestMapping(value = { "/merchantTopupList" }, method = RequestMethod.GET)
	public String merchantTopupList(final Model model, final java.security.Principal principal) {

		PageBean pageBean = new PageBean("merchant Topup List", "PayoutUser/merchantTopupList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		// List<Merchant> merchant = merchantService.loadMerchant();
		List<Merchant> merchant = merchantDao.loadActiveMerchants();
		for (Merchant m : merchant) {
			if (m != null) {

				if (m.getBusinessName() != null && !m.getBusinessName().isEmpty()) {
					m.setBusinessName(m.getBusinessName().toUpperCase());

				} else {
					m.setBusinessName("NIL");
				}
			}
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant);

		return TEMPLATE_PAYOUTUSER;
	}

//	@RequestMapping(value = { "/merchantDetails" }, method = RequestMethod.GET)
//	public String displayMerchantDetails(final Model model, @RequestParam("id") Long id,
//			final java.security.Principal principal, HttpServletRequest request) {
//
//		logger.info("Merchant ID: " + id);
//		String currentAmount = null;
//
//		Merchant merchant = merchantService.loadMerchantbyid(id);
//
//		PageBean pageBean = new PageBean("merchant Topup List", "PayoutUser/merchantTopupList", Module.TRANSACTION,
//				"transaction/sideMenuTransaction");
//		model.addAttribute("pageBean", pageBean);
//		// oldtopup
//		// List<Merchant> merchant1 = merchantService.loadMerchant();
//		List<Merchant> merchant1 = merchantService.loadMerchant1();
//		PayoutGrandDetail payoutDetail = merchantService.PayoutGrandDetailbymerchantid(id);
//
//		String id2 = Long.toString(id);
//		SettlementBalance settleBalance = merchantService.settleMerchant(id2);
//		logger.info(" ******old NetAmt  : " + settleBalance.getNetAmt());
//		logger.info(" ******old DepositAmt  : " + settleBalance.getDepositAmount());
//		logger.info(" ******old TotalAmt  : " + settleBalance.getTotalAmount());
//
//		if (payoutDetail.getSaturationAmount() != null && !payoutDetail.getSaturationAmount().isEmpty()) {
//			double amount = Double.parseDouble(payoutDetail.getSaturationAmount());
//			amount = amount / 100;
//			DecimalFormat myFormatter = new DecimalFormat("###0.00");
//			currentAmount = myFormatter.format(amount);
//
//			logger.info("Available Saturation Amount: " + currentAmount);
//		} else {
//			currentAmount = "0.00";
//			logger.info("Available Saturation Amount: " + currentAmount);
//		}
//
//		Set<String> merchantNameList = new HashSet<String>();
//
//		model.addAttribute("pageBean", pageBean);
//		model.addAttribute("merchant1", merchant1);
//		model.addAttribute("merchantName", merchant.getUsername());
//		model.addAttribute("merchant", merchant);
//		model.addAttribute("merchantNameList", merchantNameList);
//		model.addAttribute("currentAmount", settleBalance.getDepositAmount());
//		model.addAttribute("netAmt", settleBalance.getNetAmt());
//
//		return TEMPLATE_PAYOUTUSER;
//	}

	@RequestMapping(value = { "/merchantDetails" }, method = RequestMethod.GET)
	public String displayMerchantDetails(final Model model, @RequestParam("id") Long id,
			final java.security.Principal principal, HttpServletRequest request) {

		Merchant merchant = this.merchantService.loadMerchantbyid(id);

		PageBean pageBean = new PageBean("merchant Topup List", "PayoutUser/merchantTopupList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		List<Merchant> merchantList = merchantDao.loadActiveMerchants();
		String availableBalance = this.transactionDao.loadPayoutbalanceView(Long.valueOf(id));
		if (availableBalance != null) {

			double availableBalanceAsDouble = Double.parseDouble(availableBalance);

			DecimalFormat df = new DecimalFormat();
			Set<String> merchantNameList = new HashSet<String>();

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("merchant1", merchantList);
			model.addAttribute("merchantName", merchant.getUsername());
			model.addAttribute("merchant", merchant);
			model.addAttribute("merchantNameList", merchantNameList);
			model.addAttribute("currentAmount", df.format(availableBalanceAsDouble));
		} else {

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("merchant1", merchantList);
			model.addAttribute("merchantName", merchant.getUsername());
			model.addAttribute("merchant", merchant);
			model.addAttribute("merchantNameList", new HashSet<String>());
			model.addAttribute("currentAmount", "N/A");
		}

		return this.TEMPLATE_PAYOUTUSER;
	}

	// newer updateTopUp

	@RequestMapping(value = { "/updateTopup" }, method = RequestMethod.GET)
	public String updatePayoutStatus(@RequestParam final Long id, final Model model,
			@RequestParam final String topupAmount, @RequestParam final String oldDepositAmount,
			final java.security.Principal principal, final HttpServletRequest request, @RequestParam String comments) {
		logger.info("description is : " + comments);
		logger.info("comments size :" + comments.length());
		if (comments.trim().isEmpty()) {
			logger.info("inside of if with " + comments.length());
			comments = comments.trim();
		}

		PageBean pageBean = new PageBean("merchant Topup List", "PayoutUser/merchantTopupList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");

		logger.info("Deposit Amount Update via Payout Portal, Merchant ID from JSP: " + id + ", Topup Amount from JSP: "
				+ topupAmount + ", Previous available balance from JSP: " + oldDepositAmount);

		try {
			if (topupAmount.equals("N/A") || Double.parseDouble(topupAmount) == 0.00) {
				logger.warn("Deposit amount is: 0.00");

				List<Merchant> merchantList = this.merchantDao.loadActiveMerchants();
				model.addAttribute("merchant1", merchantList);
				model.addAttribute("pageBean", pageBean);

				return this.TEMPLATE_PAYOUTUSER;
			}

			double topupAmountValue = Double.parseDouble(topupAmount.replace(",", ""));
			double previousBalance = Double.parseDouble(oldDepositAmount.replace(",", ""));

			Merchant merchant = this.merchantService.loadMerchantbyid(id);

			// Generating ReferenceNo for every Deposit
			UUID uuid = UUID.randomUUID();
			String referenceNo = uuid.toString();
			logger.info("commnets size before sending to external api +" + comments + " " + comments.length());
			String emailResponse = sendEmailtoMerchant(previousBalance, topupAmountValue, merchant,
					(topupAmountValue + previousBalance), referenceNo,
					LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), comments);

			List<Merchant> merchantList = merchantDao.loadActiveMerchants();

			model.addAttribute("merchant1", merchantList);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("isEmailSuccess", emailResponse.equals("200") ? "true" : "false");

		} catch (Exception e) {
			logger.error("Exception In PayoutuserController : " + e.getMessage(), e);

			List<Merchant> merchantList = merchantDao.loadActiveMerchants();
			model.addAttribute("merchant1", merchantList);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("isEmailSuccess", "false");
		}

		return this.TEMPLATE_PAYOUTUSER;

	}

	// newer

	public static String sendEmailtoMerchant(double previousAvaliableBalancevalue, double topupAmountValue,
			Merchant merchant, double availableBalanceValueAfterDeposit, String referenceNo, String timeStampString,
			String comments) {

		DecimalFormat myFormatter = new DecimalFormat("#,##0.00");
		String previousAvaliableBalance = myFormatter.format(previousAvaliableBalancevalue);
		String topupAmount = myFormatter.format(topupAmountValue);
		String availableBalanceAfterDeposit = myFormatter.format(availableBalanceValueAfterDeposit);

		logger.info("PreviousAvaliableBalance :" + previousAvaliableBalance);
		logger.info("TopupAmount :" + topupAmount);
		logger.info("AvaliableBalanceAfterDeposit :" + availableBalanceAfterDeposit);

		String response = null;
		// EZYWIRE AS USERNAME & password mobiversa
		String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");

//		logger.info("Merchant Mail :" + merchant.getEmail() );
//		String toAddress = merchant.getEmail() +","+ PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_TO");
		String toAddress = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_TO");
		String ccAddress = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_CC");
		String subject = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_TEXT_BODY");
		String fromName = PropertyLoad.getFile().getProperty("FROMNAME");

		String merchantNotification = PropertyLoad.getFile().getProperty("MERCHANT_NOTIFICATION_MAIL");

		logger.info("merchant Notification :" + merchantNotification);
		logger.info("Email from :" + fromAddress);
		logger.info("Sending Email to :" + toAddress);
		logger.info("Email subject :" + subject);

		logger.info("Email From Name :" + fromName);
		logger.info("Sending Email to :" + toAddress);
		logger.info("Sending Email to :" + toAddress);

		// Concatenate values
		String concatenatedUrlString = referenceNo + "/" + String.valueOf(merchant.getId()) + "/"
				+ previousAvaliableBalance + "/" + topupAmount + "/" + comments;
		// Encode with Base64
		byte[] encodedBytes = Base64.getEncoder().encode(concatenatedUrlString.getBytes());
		String encryptedParam = new String(encodedBytes);

		StringBuilder result = new StringBuilder();

		// New changes mail formate
		result.append("Hey CS Team, ").append("<br><br>")
				.append("A payout deposit has been recorded. Please find the details below and review them: ")
				.append("<br><br><ul style=\"list-style-type:disc\">").append("<li>Merchant/Partner: ")
				.append(merchant.getBusinessName()).append("</li>").append("<li>Previous Balance: MYR ")
				.append(previousAvaliableBalance).append("</li>").append("<li>Deposit Amount: MYR ").append(topupAmount)
				.append("</li>").append("<li>Total Balance: MYR ").append(availableBalanceAfterDeposit).append("</li>")
				.append("</ul>").append("<ul style=\"list-style-type:disc\">").append("<li>Date and Time: ")
				.append(timeStampString).append("</li>").append("<li>Reference Number: ").append(referenceNo)
				.append("</li>").append("</ul>")
				.append("<br>If the details are accurate, please click the link below to approve an email notification to be sent to ")

				.append(merchant.getBusinessName()).append("<br><br><a href=\"").append(merchantNotification)
				.append(encryptedParam).append("\">")

//				.append(merchant.getBusinessName()).append("<br><br><a href=\"").append(merchantNotification).append("")
//				.append(referenceNo).append("/").append(String.valueOf(merchant.getId())).append("\">")

				.append("Click here to approve</a>").append("<br><br> Keep up the good work.<br>").append("<br> Mobi ");

		String textBody = result.toString();

		try {

			int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, ccAddress,
					null, textBody);

			logger.info("Email Sent Successfully to " + toAddress);
			logger.info("output Response : " + mailResponse);

			return response = String.valueOf(mailResponse);
		} catch (Exception pe) {
			response = "400";
			logger.info("Exception while sending mail : " + pe);
		}
		return response;
	}

	/*
	 * @RequestMapping(value = { "/updateTopup" }, method = RequestMethod.GET)
	 * public String updatePayoutStatus(@RequestParam final Long id, final Model
	 * model,
	 * 
	 * @RequestParam final String topupAmount, @RequestParam final String
	 * oldDepositAmount, final java.security.Principal principal, final
	 * HttpServletRequest request) {
	 * 
	 * PageBean pageBean = new PageBean("merchant Topup List",
	 * "PayoutUser/merchantTopupList", Module.TRANSACTION,
	 * "transaction/sideMenuTransaction");
	 * 
	 * logger.info("Deposit Amount Update via Payout Portal, Merchant ID from JSP: "
	 * + id + ", Topup Amount from JSP: " + topupAmount +
	 * ", Previous available balance from JSP: " + oldDepositAmount);
	 * 
	 * try { if (topupAmount.equals("N/A") || Double.parseDouble(topupAmount) ==
	 * 0.00) { logger.warn("Deposit amount is: 0.00");
	 * 
	 * List<Merchant> merchantList = this.merchantDao.loadActiveMerchants();
	 * model.addAttribute("merchant1", merchantList); model.addAttribute("pageBean",
	 * pageBean);
	 * 
	 * return this.TEMPLATE_PAYOUTUSER; }
	 * 
	 * double topupAmountValue = Double.parseDouble(topupAmount.replace(",", ""));
	 * double previousBalance = Double.parseDouble(oldDepositAmount.replace(",",
	 * ""));
	 * 
	 * Merchant merchant = this.merchantService.loadMerchantbyid(id);
	 * 
	 * // Generating ReferenceNo for every Deposit UUID uuid = UUID.randomUUID();
	 * String referenceNo = uuid.toString();
	 * 
	 * sendEmailtoMerchant(previousBalance, topupAmountValue, merchant,
	 * (topupAmountValue + previousBalance), referenceNo,
	 * LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
	 * ));
	 * 
	 * List<Merchant> merchantList = merchantDao.loadActiveMerchants();
	 * 
	 * model.addAttribute("merchant1", merchantList); model.addAttribute("pageBean",
	 * pageBean);
	 * 
	 * } catch (Exception e) { logger.error("Exception In PayoutuserController : " +
	 * e.getMessage(), e);
	 * 
	 * List<Merchant> merchantList = merchantDao.loadActiveMerchants();
	 * model.addAttribute("merchant1", merchantList); model.addAttribute("pageBean",
	 * pageBean); }
	 * 
	 * return this.TEMPLATE_PAYOUTUSER;
	 * 
	 * }
	 */

//	@RequestMapping(value = { "/updateTopup" }, method = RequestMethod.GET)
//	public String updatePayoutStatus(@RequestParam final Long id, final Model model,
//			@RequestParam final String topupAmount, @RequestParam final String oldDepositAmount,
//			final java.security.Principal principal, final HttpServletRequest request) {
//
//		PageBean pageBean = new PageBean("merchant Topup List", "PayoutUser/merchantTopupList", Module.TRANSACTION,
//				"transaction/sideMenuTransaction");
//		logger.info("Deposit Amount Update via Payout Portal ");
//		logger.info("Merchant ID in JSP : " + id);
//		logger.info("New Deposit Amount in JSP : " + topupAmount);
//		logger.info("oldDepositAmount Amount in JSP : " + oldDepositAmount);
//
//		try {
//
//			Merchant merchant = merchantService.loadMerchantbyid(id);
//
//			LoginResponse responseValue = new LoginResponse();
//			
//			//Obtain mobiApiKey by merchantId
//			String motoApiKey = merchantDao.loadMobileUserById(Long.valueOf(id)).getMotoApiKey();
//			
//			responseValue = merchantService.addDepositAmount(oldDepositAmount, topupAmount, id,motoApiKey);
//
//			PayoutDepositDetails merchantData = merchantService.checkdepositDetails(responseValue.getReferenceNo());
//			logger.info("merchantData : " + merchantData);
//			if (!merchantData.equals(null)) {
//
//				double oldDepositAmt = Double.parseDouble(merchantData.getPreviousBalance());
//				double newDepositAmt = Double.parseDouble(merchantData.getDepositAmount());
//				double availableAmt = Double.parseDouble(merchantData.getAvailableBalance());
//
//				logger.info("TimeStamp : " + merchantData.getTimeStamp());
//				String datetime = timestamp(merchantData.getTimeStamp());
//				logger.info("Date Time : " + datetime);
//
//				logger.info("Previous Balance : " + oldDepositAmt);
//				logger.info("Deposit Amount : " + newDepositAmt);
//				logger.info("Available Balance : " + availableAmt);
//
//				logger.info("responseData from external API : " + responseValue);
//				if (responseValue.getResponse().equalsIgnoreCase("TRUE")) {
//					// mail function
//					String responsedata = sendEmailtoMerchant(oldDepositAmt, newDepositAmt, merchant, availableAmt,
//							responseValue.getReferenceNo(), datetime);
//				} else {
//					logger.info("Deposit Not update ");
//				}
//			} else {
//				logger.info("Payout Deposit Details Deposit TXN invalid :" + merchantData);
//			}
//			List<Merchant> merchantList = merchantService.loadMerchant1();
//
//			for (Merchant m : merchantList) {
//				if (m != null) {
//
//					if (m.getBusinessName() != null && !m.getBusinessName().isEmpty()) {
//						m.setBusinessName(m.getBusinessName().toUpperCase());
//
//					} else {
//						m.setBusinessName("NIL");
//
//					}
//
//				}
//			}
//
//			model.addAttribute("merchant1", merchantList);
//			model.addAttribute("pageBean", pageBean);
//
//		} catch (Exception e) {
//			logger.info("Exception In PayoutuserController : " + e);
//		}
//
//		return TEMPLATE_PAYOUTUSER;
//
//	}

	// Old topup

//	@RequestMapping(value = { "/updateTopup" }, method = RequestMethod.GET)
//	public String updatePayoutStatus(@RequestParam final Long id, final Model model,
//			@RequestParam final String topupAmount, @RequestParam final String oldDepositAmount,
//			final java.security.Principal principal, final HttpServletRequest request) {
//
//		PageBean pageBean = new PageBean("merchant Topup List", "PayoutUser/merchantTopupList", Module.TRANSACTION,
//				"transaction/sideMenuTransaction");
//		logger.info("Deposit Amount Update via Payout Portal ");
//		logger.info("Merchant ID : " + id);
//		logger.info("New Deposit Amount: " + topupAmount);
//		// This is not used
//		logger.info("oldDepositAmount Amount: " + oldDepositAmount);
//
////		double oldDepositAmt = Double.parseDouble(oldDepositAmount);
//		double newDepositAmt = Double.parseDouble(topupAmount);
//
//		Merchant merchant = merchantService.loadMerchantbyid(id);
//
//		LoginResponse responseValue = new LoginResponse();
//
//		responseValue = merchantService.addDepositAmount(oldDepositAmount, topupAmount, id);
//
//		logger.info("responseData from external API : " + responseValue);
//		if (responseValue.getResponse().equalsIgnoreCase("TRUE")) {
//			logger.info("Response True ");
//			double oldDepositAmt = Double.parseDouble(responseValue.getOlddepositAmount());
//			double totalDepositAmt = Double.parseDouble(responseValue.getDepositAmount());
//			logger.info("After Update total Deposit value : " + totalDepositAmt);
//			logger.info("After Update Old Deposit value : " + oldDepositAmt);
//			// mail function
//			String responsedata = sendEmailtoMerchant(oldDepositAmt, newDepositAmt, merchant, totalDepositAmt);
//		} else {
//			logger.info("Deposit Not update ");
//		}
//
//		List<Merchant> merchantList = merchantService.loadMerchant1();
//
//		for (Merchant m : merchantList) {
//			if (m != null) {
//
//				if (m.getBusinessName() != null && !m.getBusinessName().isEmpty()) {
//					m.setBusinessName(m.getBusinessName().toUpperCase());
//
//				} else {
//					m.setBusinessName("NIL");
//
//				}
//
//			}
//		}
//
//		model.addAttribute("merchant1", merchantList);
//		model.addAttribute("pageBean", pageBean);
//
//		return TEMPLATE_PAYOUTUSER;
//
//	}

//		PageBean pageBean = new PageBean("merchant Topup List", "PayoutUser/merchantTopupList", Module.TRANSACTION,
//				"transaction/sideMenuTransaction");
//		logger.info("Deposit Amount Update via Payout Portal ");
//		logger.info("Merchant ID : " + id);
//		logger.info("New Deposit Amount: " + topupAmount);
//		logger.info("oldDepositAmount Amount: " + oldDepositAmount);
//		DecimalFormat myFormatter = new DecimalFormat("###0.00");
//
//		double oldDepositAmt = Double.parseDouble(oldDepositAmount);
//		double newDepositAmt = Double.parseDouble(topupAmount);
//
//		double depositAmt = oldDepositAmt + newDepositAmt;
//
//		String depositAmount = myFormatter.format(depositAmt);
//
//		Merchant merchant = merchantService.loadMerchantbyid(id);
//		
////		
////		//mail function 
////		
////		String data = sendEmailtoMerchant(oldDepositAmt , newDepositAmt ,merchant,depositAmt);
//		
////		logger.info("data : " + data);
//		
//		PayoutGrandDetail oldpayoutDetail = merchantService.PayoutGrandDetailbymerchantid(id);
//		String upsatAmt = null;
//		String uptpAmt = null;
//		String topupAmt = null;
//		String limitAmt = null;
//		String settlement = null;
//
//		// Update the Fields of Payout Grand Detail Table - Start
//		if (oldpayoutDetail != null) {
//
//			// User Topup Amount and Topup Date - Start
//
//			if (depositAmount.contains(".")) {
//				topupAmt = String.format("%012d", (long) Double.parseDouble(depositAmount.replace(".", "")));
//				logger.info("Pharsed Topup Amount (.) :" + topupAmt);
//			} else {
//				topupAmt = String.format("%012d", (long) Double.parseDouble(depositAmount) * 100);
//				logger.info("Pharsed Topup Amount :" + topupAmt);
//			}
//
////			if(settlementAmount!=null || !settlementAmount.isEmpty() ||!settlementAmount.equals("0.00")) {
////				String id1=Long.toString(id);
////				logger.info("Merchant Id:" + id1);
////				merchantService.saveSettlement(settlementAmount,topupAmount,id1);
////			}
//
//			Date currentDate = new Date();
//			String topupDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
//			logger.info("Topup Date : " + topupDate);
//			logger.info("topupAmt  : " + topupAmt);
//			oldpayoutDetail.setTopupAmount(topupAmt);
//			String idString = String.valueOf(id);
//			logger.info("ID String  : " + idString);
//			
//			boolean responseData = false;
//			responseData = merchantService.addDepositAmount(depositAmount, idString);
//			logger.info("responseData from external API : " + responseData);
//			if(responseData) {
//				//mail function 
//				String responsedata = sendEmailtoMerchant(oldDepositAmt , newDepositAmt ,merchant,depositAmt);
//				
//			}else {
//				logger.info("Deposit Not update ");
//			}
//			// User Topup Amount - End
//
////			if (oldpayoutDetail.getFirstTopup().equalsIgnoreCase("Yes")) {
////				if (oldpayoutDetail.getLimitAmount() != null && !oldpayoutDetail.getLimitAmount().isEmpty()) {
////
////					logger.info("The New Merchant Topup Entry");
////
////					double limitamount = Double.parseDouble(oldpayoutDetail.getLimitAmount());
////					double topupamount = Double.parseDouble(topupAmount);
////					limitamount = limitamount / 100;
////					logger.info("Available Limit Amount : " + myFormatter.format(limitamount));
////					limitamount = topupamount - limitamount;
////					limitAmt = myFormatter.format(limitamount);
////					logger.info("New Topup Amount After Deduction With Limit Amount : " + limitAmt);
////					limitAmt = String.format("%012d", (long) Double.parseDouble(limitAmt.replace(".", "")));
////					logger.info("Final Saturation and Topup Amount Deduction with Limit Amount  : " + limitAmt);
////					oldpayoutDetail.setSaturationAmount(limitAmt);
////					oldpayoutDetail.setTopupAmount(limitAmt);
////					oldpayoutDetail.setFirstTopup("No");
////
////				}
////
////			} else {
////
////				logger.info("Existing Merchant Topup Entry");
////
////				double satamount = Double.parseDouble(oldpayoutDetail.getSaturationAmount());
////				double topupamount = Double.parseDouble(topupAmount);
////				satamount = satamount / 100;
////				logger.info("Available Saturation Amount : " + myFormatter.format(satamount));
////				satamount = satamount + topupamount;
////				upsatAmt = myFormatter.format(satamount);
////				logger.info("New Saturation Amount : " + upsatAmt);
////				upsatAmt = String.format("%012d", (long) Double.parseDouble(upsatAmt.replace(".", "")));
////				oldpayoutDetail.setSaturationAmount(upsatAmt);
////				oldpayoutDetail.setTopupAmount(topupAmt);
////
////			}
//
//			// Accordingly, update the topup amount and date - Start
//
////			if (oldpayoutDetail.getTopupDate() == null || oldpayoutDetail.getTopupDate().isEmpty()) {
////
////				oldpayoutDetail.setTopupDate(topupDate);
////			} else {
////				oldpayoutDetail.setTopupDate(oldpayoutDetail.getTopupDate() + "," + topupDate);
////			}
////
////			if (oldpayoutDetail.getTopupHistory() == null || oldpayoutDetail.getTopupHistory().isEmpty()) {
////
////				oldpayoutDetail.setTopupHistory(limitAmt);
////
////			} else {
////				oldpayoutDetail.setTopupHistory(oldpayoutDetail.getTopupHistory() + "," + topupAmt);
////			}
//
//			// Accordingly, update the topup amount and date - End
//
//			merchantService.savePayoutGrandDetail(oldpayoutDetail);
//
//			// Update the Fields of Payout Grand Detail Table - End
//		}
//
//		// Redirect to the Merchant Topup List Page
//		// List<Merchant> merchantList = merchantService.loadMerchant();
//		List<Merchant> merchantList = merchantService.loadMerchant1();
//
//		for (Merchant m : merchantList) {
//			if (m != null) {
//
//				if (m.getBusinessName() != null && !m.getBusinessName().isEmpty()) {
//					m.setBusinessName(m.getBusinessName().toUpperCase());
//
//				} else {
//					m.setBusinessName("NIL");
//
//				}
//
//			}
//		}
//
//		model.addAttribute("merchant1", merchantList);
//		model.addAttribute("pageBean", pageBean);
//
//		return TEMPLATE_PAYOUTUSER;

	// Merchant Topup Added by Dhinesh - End
	@RequestMapping(value = { "/updatePayoutPaidStatus" }, method = RequestMethod.GET)
	public Object updatePayoutPaidStatus(final Model model, @RequestParam("txnId") final String txnId,
			@RequestParam("merchantID") final Long merchantID, @RequestParam("payoutStatus") final String payoutStatus,
			final java.security.Principal principal, final HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		try {
			PayoutDetail payoutData = new PayoutDetail();

			PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/PayoutList", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			logger.info("Merchant ID : " + merchantID);
			logger.info("Transaction ID : " + txnId);

			Date currentDate = new Date();
			String paidDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
			String paidTime = new SimpleDateFormat("HH:mm:ss").format(currentDate);
			logger.info("Paid Date : " + paidDate + " Paid Time : " + paidTime);

			PayoutBean pb = new PayoutBean(txnId, paidDate, paidTime, payoutStatus);
			PayoutUpdateApi ppi = new PayoutUpdateApi();
			ppi.updatyeQuery(pb);

			PayoutGrandDetail payoutDetail = merchantService.PayoutGrandDetailbymerchantid(merchantID);

			if (payoutDetail.getIpnUrl() == null) {
				logger.info("IPN Url is empty");
			} else if (payoutDetail.getIpnUrl() != null) {
				logger.info("IPN Url :" + payoutDetail.getIpnUrl());
				transactionService.PayoutCallBack(txnId, payoutDetail.getIpnUrl());

			}

			PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
			paginationBean.setCurrPage(currPage);

			transactionService.listPayoutTransaction(paginationBean, null, null, null);

			model.addAttribute("paginationBean", paginationBean);

			String responseStatus = "0000";

			model.addAttribute("Status", responseStatus);

			return TEMPLATE_PAYOUTUSER;

		} catch (Exception e) {
			String responseStatus = "0001";

			model.addAttribute("Status", responseStatus);

			return null;

		}

	}

	@RequestMapping(value = { "/declinePayoutStatus1" }, method = RequestMethod.GET)
	public String declinePayoutStatus1(final Model model, @RequestParam("txnId1") final String txnId1,
			@RequestParam("merchantID1") final Long merchantID1,
			@RequestParam("cancelReason") final String cancelReason,
			@RequestParam("payoutStatus1") final String payoutStatus, final java.security.Principal principal,
			final HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		PayoutDetail Status = new PayoutDetail();

		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/PayoutList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		logger.info("Merchant ID : " + merchantID1);
		logger.info("Transaction ID : " + txnId1);
		logger.info("cancelReason : " + cancelReason);
		Status.setInvoiceIdProof(txnId1);
		Status.setPayoutStatus("pd");
		Status.setPaymentReason(cancelReason);
		Date currentDate = new Date();
		String paidDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
		String paidTime = new SimpleDateFormat("HH:mm:ss").format(currentDate);
		logger.info("Paid Date : " + paidDate + " Paid Time : " + paidTime);
		// api
		PayoutBean pb = new PayoutBean(txnId1, paidDate, paidTime, payoutStatus, cancelReason);
		PayoutUpdateApi ppi = new PayoutUpdateApi();
		ppi.updatyeQuery(pb);
//		merchantService.declinePayoutStatus(Status);

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.listPayoutTransaction(paginationBean, null, null, null);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_PAYOUTUSER;
	}

	@RequestMapping(value = { "/updatePayoutPaidStatus1" }, method = RequestMethod.GET)
	public @ResponseBody PayoutData Addpayoutdata1(@RequestParam("merchantId") String merchantID,

			@RequestParam("txnid") String txnId, @RequestParam("status") String payoutStatus,
			HttpServletRequest request, HttpServletResponse response, Model model) {

		logger.info("Merchant ID : ***********" + merchantID);
		logger.info("Transaction ID : ***********" + txnId);
		logger.info("payoutStatus : ***********" + payoutStatus);

		Date currentDate = new Date();
		String paidDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
		String paidTime = new SimpleDateFormat("HH:mm:ss").format(currentDate);
		logger.info("Paid Date : " + paidDate + " Paid Time : " + paidTime);

		PayoutBean pb = new PayoutBean(txnId, paidDate, paidTime, payoutStatus);
		PayoutUpdateApi ppi = new PayoutUpdateApi();

		PayoutData pay = ppi.updatyeQuery(pb);
		pay.getResponseCode();
		logger.info("response :" + pay.getResponseCode());

		return pay;

	}

	// 27-03-2023 PAYOUT BALANCE INTEGRATION

	// old
//	@RequestMapping(value = { "/payoutbalance/{currPage}" }, method = RequestMethod.GET)
//	public String payoutbalance(final Model model, @PathVariable final int currPage,
//			final java.security.Principal principal) {
//		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/payoutbalance", Module.TRANSACTION,
//				"transaction/sideMenuTransaction");
//		model.addAttribute("pageBean", pageBean);
//		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
//		paginationBean.setCurrPage(currPage);
//		merchantService.loadPayoutbalance(paginationBean);
//		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
//				|| paginationBean.getItemList().size() == 0) {
//			model.addAttribute("responseData", "No Records found");
//		} else {
//			model.addAttribute("responseData", null);
//		}
//		model.addAttribute("paginationBean", paginationBean);
//		return TEMPLATE_PAYOUTUSER;
//	}

	@RequestMapping(value = { "/payoutbalance/{currPage}" }, method = RequestMethod.GET)
	public String payoutbalance(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {

		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/payoutbalance", Module.TRANSACTION,
				"transaction/sideMenuTransaction");

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);

		this.merchantDao.loadRefactoredPayoutbalance(paginationBean);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found");
		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("paginationBean", paginationBean);
		return this.TEMPLATE_PAYOUTUSER;
	}

//	@RequestMapping(value = { "/payoutbankbalance" }, method = RequestMethod.GET)
//	public String payoutbankbalance(final Model model,
//			@RequestParam(required = false, defaultValue = "1") final int currPage,
//			final java.security.Principal principal, HttpServletRequest request) {
//
//		logger.info("Payout bank balance  :********* " + principal.getName());
//
//		HttpSession session = request.getSession();
//		String myName = (String) session.getAttribute("userName");
//		String adminusername = principal.getName();
//		logger.info("Adminusername :" + adminusername);
//		logger.info("currently logged in as request: " + request.getSession());
//
//		logger.info("currently logged in as :" + myName);
//
////		Merchant currentMerchant = merchantService.loadMerchant(myName);
////
////		logger.info("Payout current merchant Id  :" + currentMerchant.getId());
//
//		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutBankBalance",
//				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//		model.addAttribute("pageBean", pageBean);
//
//		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
//		paginationBean.setCurrPage(currPage);
//
//		List<Settlementbalance> settlementbankbalance = transactionService.loadbankbalance();
//		logger.info("settlementbankbalance : " + settlementbankbalance);
//
//		model.addAttribute("loginname", principal.getName());
//		model.addAttribute("settlementbankbalance", settlementbankbalance);
////		model.addAttribute("merchantid", currentMerchant.getId());
//		model.addAttribute("adminusername", adminusername);
//
//		return TEMPLATE_PAYOUTUSER;
//	}

	@RequestMapping(value = { "/payoutbankbalance" }, method = RequestMethod.GET)
	public String payoutbankbalance(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		String adminusername = principal.getName();

		logger.info("Payout bank balance  :********* " + principal.getName() + ", Adminusername:" + adminusername
				+ ", Currently logged in as request: " + request.getSession() + "," + myName);

		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutBankBalance",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		Settlementbalance payoutBankBalance = new Settlementbalance();

		try {
			payoutBankBalance = this.transactionService.getPayoutBankBalance(payoutBankBalance);

			logger.info("Payout bank balance : " + principal.getName() + ", Session : " + request.getSession()
					+ ", PayoutBankBalance : " + payoutBankBalance.toString());
		} catch (MobiException e) {
			e.printStackTrace();
			logger.error("Exception in Payout BankBalance : " + e.getMessage(), e);
		}

		model.addAttribute("pageBean", pageBean);

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);

		model.addAttribute("loginname", principal.getName());
		model.addAttribute("settlementbankbalance", payoutBankBalance);
		model.addAttribute("adminusername", adminusername);

		return TEMPLATE_PAYOUTUSER;
	}

	@RequestMapping(value = { "/statusCheckFPX" }, method = RequestMethod.GET)
	public String fpxStatusCheck(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		String adminusername = principal.getName();
		logger.info("Adminusername :" + adminusername);
		logger.info("myName :" + myName);

		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/StatusCheckFPX",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("adminusername", adminusername);

		return TEMPLATE_PAYOUTUSER;
	}

	@RequestMapping(value = { "/getStatusFPX" }, method = RequestMethod.POST)
	public String fpxStatusCheckSearch(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal, HttpServletRequest request,
			@RequestParam("sellorOrderNum") String sellerOrderNumber) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("myName :" + myName);
		logger.info("sellerOrderNumber :" + sellerOrderNumber);

		// Api call to external Api
		Apibean api = new Apibean();
		UpdateApi dtlapi = new UpdateApi();
		api = dtlapi.fpxStatusUpdate(sellerOrderNumber);
		List<Apibean> responseList = new ArrayList<Apibean>();
		responseList.add(api);
		PaginationBean<Apibean> paginationBean = new PaginationBean<Apibean>();
		paginationBean.setItemList(responseList);
		logger.info(" Response Message :" + api.getResponseMessage());
		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/StatusCheckFPX",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("response", paginationBean);

		return TEMPLATE_PAYOUTUSER;
	}

	@RequestMapping(value = { "/statusCheckBoost" }, method = RequestMethod.GET)
	public String BoostStatusCheck(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		String adminusername = principal.getName();
		logger.info("Adminusername :" + adminusername);
		logger.info("myName :" + myName);

		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/StatusCheckBoost",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("adminusername", adminusername);

		return TEMPLATE_PAYOUTUSER;
	}

	@RequestMapping(value = { "/getBoostStatus" }, method = RequestMethod.POST)
	public String getBoostStatusCheck(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal, HttpServletRequest request,
			@RequestParam("invoiceId") String invoiceId) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		String adminusername = principal.getName();
		logger.info("Adminusername :" + adminusername);
		logger.info("myName :" + myName);
		logger.info("invoiceid :" + invoiceId);

		// Api call to external Api
		Apibean api = new Apibean();
		UpdateApi dtlapi = new UpdateApi();
		api = dtlapi.BoostStatusUpdate(invoiceId);

		List<Apibean> responseList = new ArrayList<Apibean>();
		responseList.add(api);
		PaginationBean<Apibean> paginationBean = new PaginationBean<Apibean>();
		paginationBean.setItemList(responseList);

		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/StatusCheckBoost",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("response", paginationBean);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());

		return TEMPLATE_PAYOUTUSER;
	}

	@RequestMapping(value = { "/statusCheckTngAndSpp" }, method = RequestMethod.GET)
	public String TngAndSppStatusCheck(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		String adminusername = principal.getName();
		logger.info("Adminusername :" + adminusername);
		logger.info("myName :" + myName);

		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/StatusCheckTNG",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("adminusername", adminusername);

		return TEMPLATE_PAYOUTUSER;
	}

	@RequestMapping(value = { "/getTngAndSppStatus" }, method = RequestMethod.POST)
	public String GetTngAndSppStatus(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal, HttpServletRequest request,
			@RequestParam("invoiceid") String invoiceid) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		String adminusername = principal.getName();
		logger.info("Adminusername :" + adminusername);
		logger.info("myName :" + myName);
		logger.info("invoiceid :" + invoiceid);

		// Token Api
		Apibean api = new Apibean();
		UpdateApi dtlapi = new UpdateApi();
		api = dtlapi.TngAndSppTokenGenerate();

		// get transaction Id API
		Apibean apiBean = new Apibean();
		apiBean = dtlapi.TngAndSppGetTransactionId(invoiceid);

		logger.info("token ****  :" + api.getResponseData().getToken());
		logger.info("TrxId  ****  :" + apiBean.getResponseData().getTrxId());

		List<Apibean> responseList = new ArrayList<Apibean>();
		if (apiBean != null) {

			logger.info("response Description ****  :" + apiBean.getResponseDescription());
			responseList.add(apiBean);

			logger.info("response code of 1st api :" + apiBean.getResponseCode());
			// get StatusCheck Api
			if (apiBean.getResponseCode().equals("0000")) {
				logger.info("response code i get 1st api :" + apiBean.getResponseCode());
				api = dtlapi.TngAndSppCheckStatus(apiBean.getResponseData().getTrxId(), api);
				responseList.add(api);
			} else {
				logger.info("Transaction Id api response failure ");
			}
		}

		PaginationBean<Apibean> paginationBean = new PaginationBean<Apibean>();
		paginationBean.setItemList(responseList);

		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/StatusCheckTNG",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("response", paginationBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("adminusername", adminusername);

		return TEMPLATE_PAYOUTUSER;
	}

//	public static String sendEmailtoMerchant(double oldTopupAmount, double newTopupAmount, Merchant merchant,
//			double depositAmount) {
//
//		DecimalFormat myFormatter = new DecimalFormat("#,##0.00");
//		String oldDepositAmt = myFormatter.format(oldTopupAmount);
//		String newDepositAmt = myFormatter.format(newTopupAmount);
//		String totalDepositAmt = myFormatter.format(depositAmount);
//
//		logger.info("oldDepositAmt :" + oldDepositAmt);
//		logger.info("newDepositAmt :" + newDepositAmt);
//		logger.info("totalDepositAmt :" + totalDepositAmt);
//
//		String response = null;
//		// EZYWIRE AS USERNAME & password mobiversa
//		String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");
//
//		logger.info("Merchant Mail :" + merchant.getEmail());
//		String toAddress = merchant.getEmail() + "," + PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_TO");
//		String ccAddress = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_CC");
//		String subject = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_TEXT_BODY");
//		String fromName = PropertyLoad.getFile().getProperty("FROMNAME");
//
//		logger.info("Email from :" + fromAddress);
//		logger.info("Sending Email to :" + toAddress);
//		logger.info("Email subject :" + subject);
//
//		logger.info("Email From Name :" + fromName);
//		logger.info("Sending Email to :" + toAddress);
//		logger.info("Sending Email to :" + toAddress);
//
//		StringBuilder result = new StringBuilder();
//		result.append("Dear ").append(merchant.getBusinessName()).append(",\n\n").append(
//				"We're pleased to notify you that a Payout Deposit Amount has been approved and updated in your Merchant Portal.")
//				.append("\n\nHere are the details: \n\nPrevious Balance (a): RM ").append(oldDepositAmt)
//				.append("\nDeposit Amount (b): RM ").append(newDepositAmt).append("\nTotal Balance (a+b): RM ")
//				.append(totalDepositAmt)
//				.append("\n\nFor any inquiries or support, reach out to us on our WhatsApp Group or csmobi@gomobi.io")
//				.append("\n\nThis is an automated notification. Kindly refrain from replying.\n\nRegards,\n")
//				.append("Mobi Asia Sdn. Bhd.\nWebsite: https://gomobi.io");
//
//		String textBody = result.toString();
//
////		String textBody = "Dear "+ merchant.getBusinessName() +","+"\n\n"+ "We're pleased to notify you that a Payout Deposit Amount has been approved and updated in your Merchant Webview Portal."
////				+"\n"+" Here are the details: "+"\n\n"+"Previous Balance (a):RM "+oldDepositAmt+"\n"+"New Amount (b): RM "+newDepositAmt+"\n"+"Total Amount (a+b): RM "+totalDepositAmt+"\n\n"+"For any inquiries or support, reach out to us on our WhatsApp Group or csmobi@gomobi.io"+"\n\n"+"This is an automated notification. Kindly refrain from replying."+"\n\n"+"Regards,"
////				+"\n"+ "Mobi Asia Sdn. Bhd."+"\n"+"Website: https://gomobi.io";
//
//		ElasticEmail message = new ElasticEmail(fromAddress, subject, fromName, toAddress, ccAddress, ccAddress, null,
//				textBody);
//		ElasticEmailClient client = new ElasticEmailClient();
//
//		try {
//			response = client.sendMessage1(message);
//			logger.info("Email Sent Successfully to " + toAddress);
//			logger.info("output Response : " + response);
//
//		} catch (Exception pe) {
//			response = "400";
//			logger.info("Invalid Signature Base64 String");
//		}
//		return response;
//	}

//	
//	
//	public static String sendEmailtoMerchant(double oldTopupAmount, double newTopupAmount, Merchant merchant,
//			double depositAmount, String referenceNo, String timeStampString) {
//
//		DecimalFormat myFormatter = new DecimalFormat("#,##0.00");
//		String oldDepositAmt = myFormatter.format(oldTopupAmount);
//		String newDepositAmt = myFormatter.format(newTopupAmount);
//		String totalDepositAmt = myFormatter.format(depositAmount);
//
//		logger.info("oldDepositAmt :" + oldDepositAmt);
//		logger.info("newDepositAmt :" + newDepositAmt);
//		logger.info("totalDepositAmt :" + totalDepositAmt);
//
//		String response = null;
//		// EZYWIRE AS USERNAME & password mobiversa
//		String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");
//
////		logger.info("Merchant Mail :" + merchant.getEmail() );
////		String toAddress = merchant.getEmail() +","+ PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_TO");
//		String toAddress = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_TO");
//		String ccAddress = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_CC");
//		String subject = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_TEXT_BODY");
//		String fromName = PropertyLoad.getFile().getProperty("FROMNAME");
//
//		String merchantNotification = PropertyLoad.getFile().getProperty("MERCHANT_NOTIFICATION_MAIL");
//
//		logger.info("merchant Notification :" + merchantNotification);
//		logger.info("Email from :" + fromAddress);
//		logger.info("Sending Email to :" + toAddress);
//		logger.info("Email subject :" + subject);
//
//		logger.info("Email From Name :" + fromName);
//		logger.info("Sending Email to :" + toAddress);
//		logger.info("Sending Email to :" + toAddress);
//
//		StringBuilder result = new StringBuilder();
//		// New changes mail formate
//		result.append("Hey CS Team, ").append("<br><br>")
//        .append("A payout deposit has been recorded. Please find the details below and review them: ")
//        .append("<br><br><ul style=\"list-style-type:disc\">")
//        .append("<li>Merchant/Partner: ").append(merchant.getBusinessName()).append("</li>")
//        .append("<li>Previous Balance: MYR ").append(oldDepositAmt).append("</li>")
//        .append("<li>Deposit Amount: MYR ").append(newDepositAmt).append("</li>")
//        .append("<li>Total Balance: MYR ").append(totalDepositAmt).append("</li>").append("</ul>")
//        .append("<ul style=\"list-style-type:disc\">")
//        .append("<li>Date and Time: ").append(timeStampString).append("</li>")
//        .append("<li>Reference Number: ").append(referenceNo).append("</li>")
//        .append("</ul>")
//        .append("<br>If the details are accurate, please click the link below to approve an email notification to be sent to ").append(merchant.getBusinessName())
//        .append("<br><br><a href=\"").append(merchantNotification).append("").append(referenceNo).append("/")
//        .append(String.valueOf(merchant.getId())).append("\">")
//        .append("Click here to approve</a>")
//        .append("<br><br> Keep up the good work.<br><br> Regards,<br>")
//        .append("<br> Mobi ");
//
//
//		String textBody = result.toString();
//
//		try {
//
//			int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, ccAddress, null, textBody);
//
//			logger.info("Email Sent Successfully to " + toAddress);
//			logger.info("output Response : " + response);
//
//			return response = String.valueOf(mailResponse);
//		} catch (Exception pe) {
//			response = "400";
//			logger.info("Exception while sending mail : " + pe);
//		}
//		return response;
//	}
	public static String timestamp(Timestamp dateTime) {
		Timestamp timestamp = dateTime;
		LocalDateTime localDateTime = timestamp.toLocalDateTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");
		String timeStampString = localDateTime.format(formatter);

		// Convert the day to include the ordinal suffix
		int dayOfMonth = localDateTime.getDayOfMonth();
		String dayWithSuffix = dayOfMonth + getDayOfMonthSuffix(dayOfMonth);
		timeStampString = timeStampString.replaceFirst(String.valueOf(dayOfMonth), dayWithSuffix);

		logger.info("TimeStamp : " + timeStampString);
		return timeStampString;
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

//	public static String sendEmailtoMerchant(double oldTopupAmount, double newTopupAmount, Merchant merchant,
//			double depositAmount, String referenceNo, String timeStampString) {
//
//		DecimalFormat myFormatter = new DecimalFormat("#,##0.00");
//		String oldDepositAmt = myFormatter.format(oldTopupAmount);
//		String newDepositAmt = myFormatter.format(newTopupAmount);
//		String totalDepositAmt = myFormatter.format(depositAmount);
//
//		logger.info("oldDepositAmt :" + oldDepositAmt);
//		logger.info("newDepositAmt :" + newDepositAmt);
//		logger.info("totalDepositAmt :" + totalDepositAmt);
//
//		String response = null;
//		// EZYWIRE AS USERNAME & password mobiversa
//		String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");
//
////		logger.info("Merchant Mail :" + merchant.getEmail() );
////		String toAddress = merchant.getEmail() +","+ PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_TO");
//		String toAddress = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_TO");
//		String ccAddress = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_CC");
//		String subject = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_TEXT_BODY");
//		String fromName = PropertyLoad.getFile().getProperty("FROMNAME");
//
//		String merchantNotification = PropertyLoad.getFile().getProperty("MERCHANT_NOTIFICATION_MAIL");
//
//		logger.info("merchant Notification :" + merchantNotification);
//		logger.info("Email from :" + fromAddress);
//		logger.info("Sending Email to :" + toAddress);
//		logger.info("Email subject :" + subject);
//
//		logger.info("Email From Name :" + fromName);
//		logger.info("Sending Email to :" + toAddress);
//		logger.info("Sending Email to :" + toAddress);
//
//		StringBuilder result = new StringBuilder();
//
//		// New changes mail formate
//		result.append("Hey CS Team, ").append("<br><br>")
//        .append("A payout deposit has been recorded. Please find the details below and review them: ")
//        .append("<br><br><ul style=\"list-style-type:disc\">")
//        .append("<li>Merchant/Partner: ").append(merchant.getBusinessName()).append("</li>")
//        .append("<li>Previous Balance: MYR ").append(oldDepositAmt).append("</li>")
//        .append("<li>Deposit Amount: MYR ").append(newDepositAmt).append("</li>")
//        .append("<li>Total Balance: MYR ").append(totalDepositAmt).append("</li>").append("</ul>")
//        .append("<ul style=\"list-style-type:disc\">")
//        .append("<li>Date and Time: ").append(timeStampString).append("</li>")
//        .append("<li>Reference Number: ").append(referenceNo).append("</li>")
//        .append("</ul>")
//        .append("<br>If the details are accurate, please click the link below to approve an email notification to be sent to ").append(merchant.getBusinessName())
//        .append("<br><br><a href=\"").append(merchantNotification).append("").append(referenceNo).append("/")
//        .append(String.valueOf(merchant.getId())).append("\">")
//        .append("Click here to approve</a>")
//        .append("<br><br> Keep up the good work.<br>")
//        .append("<br> Mobi ");
//
//		String textBody = result.toString();
//
//
//		try {
//
//			int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, ccAddress, null, textBody);
//
//			logger.info("Email Sent Successfully to " + toAddress);
//			logger.info("output Response : " + response);
//
//			return response = String.valueOf(mailResponse);
//		} catch (Exception pe) {
//			response = "400";
//			logger.info("Exception while sending mail : " + pe);
//		}
//		return response;
//	}

	/*
	 * public static String sendEmailtoMerchant(double
	 * previousAvaliableBalancevalue, double topupAmountValue, Merchant merchant,
	 * double availableBalanceValueAfterDeposit, String referenceNo, String
	 * timeStampString) {
	 * 
	 * DecimalFormat myFormatter = new DecimalFormat("#,##0.00"); String
	 * previousAvaliableBalance = myFormatter.format(previousAvaliableBalancevalue);
	 * String topupAmount = myFormatter.format(topupAmountValue); String
	 * availableBalanceAfterDeposit =
	 * myFormatter.format(availableBalanceValueAfterDeposit);
	 * 
	 * logger.info("PreviousAvaliableBalance :" + previousAvaliableBalance);
	 * logger.info("TopupAmount :" + topupAmount);
	 * logger.info("AvaliableBalanceAfterDeposit :" + availableBalanceAfterDeposit);
	 * 
	 * String response = null; // EZYWIRE AS USERNAME & password mobiversa String
	 * fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");
	 * 
	 * // logger.info("Merchant Mail :" + merchant.getEmail() ); // String toAddress
	 * = merchant.getEmail() +","+
	 * PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_TO"); String
	 * toAddress = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_TO");
	 * String ccAddress =
	 * PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_CC"); String subject
	 * = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_TEXT_BODY"); String
	 * fromName = PropertyLoad.getFile().getProperty("FROMNAME");
	 * 
	 * String merchantNotification =
	 * PropertyLoad.getFile().getProperty("MERCHANT_NOTIFICATION_MAIL");
	 * 
	 * logger.info("merchant Notification :" + merchantNotification);
	 * logger.info("Email from :" + fromAddress); logger.info("Sending Email to :" +
	 * toAddress); logger.info("Email subject :" + subject);
	 * 
	 * logger.info("Email From Name :" + fromName); logger.info("Sending Email to :"
	 * + toAddress); logger.info("Sending Email to :" + toAddress);
	 * 
	 * // Concatenate values String concatenatedUrlString = referenceNo + "/" +
	 * String.valueOf(merchant.getId()) + "/" + previousAvaliableBalance + "/" +
	 * topupAmount; // Encode with Base64 byte[] encodedBytes =
	 * Base64.getEncoder().encode(concatenatedUrlString.getBytes()); String
	 * encryptedParam = new String(encodedBytes);
	 * 
	 * StringBuilder result = new StringBuilder();
	 * 
	 * // New changes mail formate result.append("Hey CS Team, ").append("<br><br>")
	 * .append("A payout deposit has been recorded. Please find the details below and review them: "
	 * ) .append("<br><br><ul style=\"list-style-type:disc\">").
	 * append("<li>Merchant/Partner: ")
	 * .append(merchant.getBusinessName()).append("</li>").
	 * append("<li>Previous Balance: MYR ")
	 * .append(previousAvaliableBalance).append("</li>").
	 * append("<li>Deposit Amount: MYR ").append(topupAmount)
	 * .append("</li>").append("<li>Total Balance: MYR ").append(
	 * availableBalanceAfterDeposit).append("</li>")
	 * .append("</ul>").append("<ul style=\"list-style-type:disc\">").
	 * append("<li>Date and Time: ")
	 * .append(timeStampString).append("</li>").append("<li>Reference Number: ").
	 * append(referenceNo) .append("</li>").append("</ul>")
	 * .append("<br>If the details are accurate, please click the link below to approve an email notification to be sent to "
	 * )
	 * 
	 * .append(merchant.getBusinessName()).append("<br><br><a href=\"").append(
	 * merchantNotification) .append(encryptedParam).append("\">")
	 * 
	 * // .append(merchant.getBusinessName()).append("<br><br><a href=\"").append(
	 * merchantNotification).append("") //
	 * .append(referenceNo).append("/").append(String.valueOf(merchant.getId())).
	 * append("\">")
	 * 
	 * .append("Click here to approve</a>").
	 * append("<br><br> Keep up the good work.<br>").append("<br> Mobi ");
	 * 
	 * String textBody = result.toString();
	 * 
	 * try {
	 * 
	 * int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject,
	 * fromName, toAddress, ccAddress, null, textBody);
	 * 
	 * logger.info("Email Sent Successfully to " + toAddress);
	 * logger.info("output Response : " + response);
	 * 
	 * return response = String.valueOf(mailResponse); } catch (Exception pe) {
	 * response = "400"; logger.info("Exception while sending mail : " + pe); }
	 * return response; }
	 */

	@RequestMapping(value = "/exportPayoutAsEmail", method = RequestMethod.GET)
	public String exportPayoutAsEmail(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = true) String emailId, @RequestParam(required = false) String export) {

		logger.info(
				"Export payout transactiion as CSV......................................................................");

		try {
			final String fromDate = convertDateFormatTo_YYY_MM_dd(date);
			final String toDate = convertDateFormatTo_YYY_MM_dd(date1);

			if (export.equals("EXCEL")) {

				ExecutorService executor = Executors.newSingleThreadExecutor();

				CompletableFuture.runAsync(() -> {

					List<PayoutModel> csvContent = transactionDao.getPayoutTxnDetailsBetweenDates(fromDate, toDate);
					byte[] csvContentAsByteArray = transactionService
							.generatePayoutDetailCSVContentForEmail(csvContent);
					new EmailUtils().sendEmailWithAttachment(csvContentAsByteArray, fromDate, toDate, emailId);
				}, executor).exceptionally(ex -> {
					logger.error("Exception in sending E-mail: " + ex.getMessage(), ex);
					return null;
				}).thenRun(executor::shutdown);

//				executor.shutdown();
			} else {
				logger.warn("Export type must be EXCEL");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in export CSV for payout user: " + e);
		} finally {
			PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/PayoutList", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
			paginationBean.setCurrPage(currPage);

			transactionService.listPayoutTransaction(paginationBean, convertDateFormatTo_YYY_MM_dd(date),
					convertDateFormatTo_YYY_MM_dd(date1), null);

			model.addAttribute("paginationBean", paginationBean);

			return TEMPLATE_PAYOUTUSER;

		}

//		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/PayoutList", Module.TRANSACTION,
//				"transaction/sideMenuTransaction");
//		model.addAttribute("pageBean", pageBean);
//
//		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
//		paginationBean.setCurrPage(currPage);
//
//		transactionService.listPayoutTransaction(paginationBean, date, date1);
//
//		model.addAttribute("paginationBean", paginationBean);

//		return TEMPLATE_PAYOUTUSER;

	}

	public static String convertDateFormatTo_YYY_MM_dd(String inputDate) {
		// Define input and output date formats
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// Parse input date and format it to desired output format
		LocalDate date = LocalDate.parse(inputDate, inputFormatter);
		String outputDate = date.format(outputFormatter);

		return outputDate;
	}

	@RequestMapping(value = { "/manualSettlementJobTriggerIntial" }, method = RequestMethod.GET)
	public String manualSettlementJobTriggerIntial(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {

		PageBean pageBean = new PageBean("payout job trigger", "PayoutUser/PayoutJobTrigger", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		return TEMPLATE_PAYOUTUSER;
	}

	@RequestMapping(value = { "/manualSettlementJobTrigger" }, method = RequestMethod.GET)
	public String manualSettlementJobTrigger(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {

		logger.info("manualSettlementJobTrigger");
		PageBean pageBean = new PageBean("payout job trigger", "PayoutUser/PayoutJobTrigger", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		boolean availableBalance = transactionDao.loadAllMerchantPayoutbalanceView();

		logger.info(availableBalance);
		String responseCode = "Unknown";
		String responseMessage = "Unknown";
		if (availableBalance) {
			SettlementJobAPI settlementjobapi = new SettlementJobAPI();

			try {
				String apiResponnse = settlementjobapi.settlementJobTriggerApi();

				Gson gson = new Gson();
				JsonObject jsonObject = gson.fromJson(apiResponnse, JsonObject.class);

				JsonElement responseCodeElement = jsonObject.get("responseCode");
				JsonElement responseMessageElement = jsonObject.get("responseMessage");

				responseCode = responseCodeElement != null ? responseCodeElement.getAsString() : "Unknown";
				responseMessage = responseMessageElement != null ? responseMessageElement.getAsString() : "Unknown";
				logger.info(availableBalance);
			} catch (IOException e) {
				e.printStackTrace();
			}
			model.addAttribute("availableBalance", availableBalance);
		}

		boolean response = responseCode.equalsIgnoreCase("Unknown") || responseCode.equalsIgnoreCase("0000") ? true
				: false;

		model.addAttribute("pageBean", pageBean).addAttribute("apiResponse", response);
		return TEMPLATE_PAYOUTUSER;
	}
	@RequestMapping(value = "/accountEnquiry" , method = RequestMethod.GET)
	public String accountEnquiry(HttpServletRequest request , final Model model,
								 @RequestParam(required = false, defaultValue = "1") final int currentPage,
								 final java.security.Principal principal
	) throws MobiException {
		logger.info("Account Enquiry Response-->"+request.getRequestURI());
		logger.info(request.getUserPrincipal().getName());
		logger.info(request.getUserPrincipal().getName());
		logger.info("MERCHANT NAME : "+principal.getName());
		logger.info(principal.toString());
//

		accountEnquiryService.listApprovalTransactionsForPayoutUser(model,currentPage,principal.getName(),null);

		return TEMPLATE_PAYOUTUSER;
	}
	@RequestMapping(value = "/searchOnHoldPayout" , method = RequestMethod.GET)
	public String searchOnHoldPayout(HttpServletRequest request , final Model model,
									 @RequestParam final String idType,
									 @RequestParam final String id,
									 final java.security.Principal principal
	) throws MobiException {

//
		accountEnquiryService.findByIDForPayoutUser(idType,id,principal.getName(),model);

		return TEMPLATE_PAYOUTUSER;
	}


	@RequestMapping(value = "/triggerIPNForCS" , method = RequestMethod.POST ,  consumes = "application/json")
	public @ResponseBody ResponseEntity<String> triggerIPNForCS(@RequestBody Map<String, String> requestBody) throws IOException {
		
		logger.error("IPN trigger init time : "+ LocalTime.now());
		String invoiceIdProof = requestBody.get("invoiceIdProof");
		
		//static method call
		String currentUserAuthentication = getCurrentUserAuthentication();
		
		if(invoiceIdProof.trim().isEmpty() || Objects.isNull(invoiceIdProof))
			return new ResponseEntity("Invalid query data" , HttpStatus.BAD_REQUEST);
		
		if(isUserAuthenticatedForPayoutIpnTrigger()) {
			logger.info("IPN trigger intialized for invoice id : " + invoiceIdProof);
			logger.info("IPN retriggered by authorized user : " + currentUserAuthentication);

			return new ResponseEntity(transactionService.triggerIpnUsingProcessorAPI(invoiceIdProof),HttpStatus.OK);
		}else {
			logger.info("IPN retrigger initialzed by " + currentUserAuthentication);
			return new ResponseEntity("Unauthenticated user" , HttpStatus.UNAUTHORIZED);
		}
	}

	@RequestMapping(value = "/getIpnTriggerCountMsg" , method = RequestMethod.POST , consumes = "application/json")
	public @ResponseBody ResponseEntity<String> getIpnTriggerCountMessage(@RequestBody Map<String, String> requestBody) {
		
		String invoiceIdProof = requestBody.get("invoiceIdProof");
		//static method call
		String currentUserAuthentication = getCurrentUserAuthentication();
		
		if(invoiceIdProof == null)
			return new ResponseEntity("Invoice Id is Null" ,HttpStatus.BAD_REQUEST);
		
		if(isUserAuthenticatedForPayoutIpnTrigger()) {
			logger.info("IPN trigger count validation for invoice id : " + invoiceIdProof);
			logger.info("IPN retriggered by authorized user : " + currentUserAuthentication);
			return  new ResponseEntity(transactionService.getIpnTriggerMessage(invoiceIdProof), HttpStatus.OK);
		}else {
			logger.info("IPN retrigger count checking API initialized by " + currentUserAuthentication);
			
			return new ResponseEntity("Unauthenticated user" , HttpStatus.UNAUTHORIZED);
		}
				
	}

	


	}
