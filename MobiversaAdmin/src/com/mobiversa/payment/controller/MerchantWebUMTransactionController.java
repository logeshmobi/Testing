package com.mobiversa.payment.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.BalanceAudit;
import com.mobiversa.common.bo.BnplTxnDetails;
import com.mobiversa.common.bo.CountryCurPhone;
import com.mobiversa.common.bo.EwalletTxnDetails;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.MobileOTP;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.PayoutBankBalance;
import com.mobiversa.common.bo.Receipt;
import com.mobiversa.common.bo.SettlementBalance;
import com.mobiversa.common.bo.SettlementDetails;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.bo.TransactionRequest;
import com.mobiversa.common.bo.TransactionResponse;
import com.mobiversa.common.bo.UMEcomTxnRequest;
import com.mobiversa.common.bo.UMEcomTxnResponse;
import com.mobiversa.common.bo.WithdrawalTransactionsDetails;
import com.mobiversa.payment.connect.MotoPaymentCommunication;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.controller.bean.SettlementMailList;
import com.mobiversa.payment.controller.bean.Settlementbalance;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dao.SMSServiceImpl;
import com.mobiversa.payment.dao.TransactionDao;
import com.mobiversa.payment.dto.Country;
import com.mobiversa.payment.dto.DataTransferObject;
import com.mobiversa.payment.dto.FinanceReport;
import com.mobiversa.payment.dto.MotoTxnDet;
import com.mobiversa.payment.dto.Request;
import com.mobiversa.payment.dto.TempletFields;
import com.mobiversa.payment.exception.MobiException;
import com.mobiversa.payment.exception.Status;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.CurrencyExchangeService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.SettlementWebService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.BankCode;
import com.mobiversa.payment.util.CardType;
import com.mobiversa.payment.util.CurrencyTypeUtils;
import com.mobiversa.payment.util.ElasticEmail;
import com.mobiversa.payment.util.ElasticEmailClient;
import com.mobiversa.payment.util.FinanceReportUtils;
import com.mobiversa.payment.util.MobiliteTrackDetails;
import com.mobiversa.payment.util.PayoutModel;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.ResponseDetails;
import com.mobiversa.payment.util.SettlementModel;
import com.mobiversa.payment.util.UMEzyway;
import com.mobiversa.payment.util.Utils;
import com.postmark.java.Attachment;
import com.postmark.java.EmailTemplet;
import com.postmark.java.NameValuePair;
import com.postmark.java.PostmarkClient;
import com.postmark.java.PostmarkException;
import com.postmark.java.PostmarkMessage;

@Controller
@RequestMapping(value = MerchantWebUMTransactionController.URL_BASE)
public class MerchantWebUMTransactionController extends BaseController {

	@Autowired
	private MobileUserService mobileUserService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private SettlementWebService settlementWebService;
	@Autowired
	CurrencyExchangeService currencyExchangeService;
	@Autowired
	private MerchantDao merchantDao;

	@Autowired
	private TransactionDao transactionDao;

	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	private static final String PAYER_HEADER = "MERCHANT NAME,MID,ACCOUNT NUMBER,BANK NAME,WITHDRAWL AMOUNT";

	private static final String PAYER_HEADER1 = "MERCHANT NAME,MID,ACCOUNT NUMBER,BANK NAME,EXSITING DEPOSIT AMOUNT , DEPOSIT AMOUNT,EXSITING OVERDRAFT AMOUNT,OVERDRAFT AMOUNT,EXSITING SETTLEMNET AMOUNT,SETTLEMNT AMOUNT,EXSITING TOTAL BALANCE,TOTAL BALANCE";

	private static final String PAYER_HEADER2 = "MERCHANT NAME,MID,ACCOUNT NUMBER,BANK NAME,EXSITING DEPOSIT AMOUNT , DEPOSIT AMOUNT,EXSITING SETTLEMNET AMOUNT,SETTLEMNT AMOUNT,EXSITING TOTAL BALANCE,TOTAL BALANCE";

	public static final String URL_BASE = "/transactionUmweb";
	private static final Logger logger = Logger.getLogger(MerchantWebUMTransactionController.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		// logger.info("Test1 defaultpage");
		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/alllist" }, method = RequestMethod.GET)
	public String displayTransactionSummary(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("All UMOBILE transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUmList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();

		paginationBean.setCurrPage(currPage);
		transactionService.getUMForSettlement(paginationBean, currentMerchant);
		List<TerminalDetails> terminalDetails = transactionService.getTerminalDetails(currentMerchant);
		if (paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Record found");
		}
		model.addAttribute("terminalDetailsList", terminalDetails);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());

		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/umlist" }, method = RequestMethod.GET)
	public String displayCardTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/cardUMTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		transactionService.getUMMidTransForSettlement(paginationBean, currentMerchant.getMid().getUmMid(), null);
		// transactionService.getCardTransactionForSettlement(paginationBean,
		// currentMerchant,"EZYWIRE",null,null,null);
		List<TerminalDetails> terminalDetailsList = transactionService
				.getTerminalDetails(currentMerchant.getMid().getUmMid());
		if (paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Record found");
		}
		model.addAttribute("terminalDetailsList", terminalDetailsList);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/listcash" }, method = RequestMethod.GET)
	public String displayCashTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();

		Merchant currentMerchant = merchantService.loadMerchant(myName);
		logger.info(" for test merchant id :" + currentMerchant.getMid().getMid());
		logger.info("cash list transaction: " + currentMerchant);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/cashTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);
		System.out.println("list of items from db1:");
		/*
		 * transactionService.getCashTransForSettlement(paginationBean,
		 * currentMerchant);
		 */
		transactionService.getTransactionForSettlement(paginationBean, currentMerchant, "CASH");
		// System.out.println("list of items from
		// db2:"+paginationBean.getItemList().size());
		logger.info("No of Records: " + paginationBean.getItemList().size());

		/*
		 * model.addAttribute("devIdList", dIdSet); model.addAttribute("tidList",
		 * tidSet);
		 */
		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {
				if (forSettlement.getTid() != null) {
					TerminalDetails terminalDetails = transactionService
							.getTerminalDetailsByTid(forSettlement.getTid().toString());
					if (terminalDetails != null) {
						// logger.info("terminal details contact Name:" +
						// terminalDetails.getContactName());
						forSettlement.setMerchantName(terminalDetails.getContactName());
					}
				}
				if (forSettlement.getAmount() != null) {
					double amount = 0;
					amount = Double.parseDouble(forSettlement.getAmount()) / 100;
					// forSettlement.setAmount(amount+"0");
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(amount);
					// System.out.println(" Amount :"+output);
					forSettlement.setAmount(output);
				}

				// System.out.println(forSettlement.getStatus());
				if (forSettlement.getStatus().equals("CT")) {
					forSettlement.setStatus("CASH SALE");
				}
				if (forSettlement.getStatus().equals("CV")) {
					forSettlement.setStatus("CASH CANCELLED");
				}

				if (forSettlement.getDate() != null && forSettlement.getTime() != null) {
					try {
						// String sd=forSettlement.getDate()+new
						// SimpleDateFormat("y").format(new java.util.Date());
						String sd = forSettlement.getTimeStamp();
						String rd = new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("yyyy-MM-dd").parse(sd));
						String rt = new SimpleDateFormat("HH:mm:ss")
								.format(new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
						forSettlement.setDate(rd);
						forSettlement.setTime(rt);
					} catch (ParseException e) {
					}

				}
			}

			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = { "/boostlist" }, method = RequestMethod.GET)
	public String displayboostTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		// logger.info("currently logged in as " + myName);
		logger.info("transaction type checking /boostlist");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/boostTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);
		/*
		 * transactionService.getBoostTransForSettlement(paginationBean,
		 * currentMerchant);
		 */
		transactionService.getTransactionForSettlement(paginationBean, currentMerchant, "BOOST");

		// transactionService.getCardTransactionForSettlement(paginationBean,
		// currentMerchant, "BOOST",null,null,null);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		List<TerminalDetails> terminalDetailsList = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMid());
		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetailsList) {
			String mtid = t.getTid();
			tidSet.add(mtid.toString());
			// tidSet.add(t.getDeviceId());
		}
		ArrayList<String> dIdSet = new ArrayList<String>();
		for (TerminalDetails t : terminalDetailsList) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());

		}
		/*
		 * model.addAttribute("devIdList", dIdSet); model.addAttribute("tidList",
		 * tidSet);
		 */
		model.addAttribute("terminalDetailsList", terminalDetailsList);
		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {

				TerminalDetails terminalDetails = transactionService
						.getTerminalDetailsByTid(forSettlement.getTid().toString());
				if (terminalDetails != null) {

					forSettlement.setMerchantName(terminalDetails.getContactName());
				}
				if (forSettlement.getAmount() != null) {
					double amount = 0;
					amount = Double.parseDouble(forSettlement.getAmount()) / 100;
					// forSettlement.setAmount(amount+"0");
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(amount);
					// System.out.println(" Amount :"+output);
					forSettlement.setAmount(output);
				}

				if (forSettlement.getStatus().equals("BPS")) {
					forSettlement.setStatus("BOOST SETTLED");
				}
				if (forSettlement.getStatus().equals("BP")) {
					forSettlement.setStatus("BOOST PENDING");
				}
				if (forSettlement.getStatus().equals("BPA")) {
					forSettlement.setStatus("BOOST PAYMENT");
				}
				if (forSettlement.getStatus().equals("BPC")) {
					forSettlement.setStatus("BOOST CANCELLED");
				}

				if (forSettlement.getTimeStamp() != null) {

					try {

						String rd = new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("yyyy-MM-dd").parse(forSettlement.getTimeStamp()));

						forSettlement.setDate(rd);

						String rt1 = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.parse(forSettlement.getTimeStamp().toString()));
						forSettlement.setTime(rt1);

					} catch (ParseException e) {
					}

				}

			}

			model.addAttribute("paginationBean", paginationBean);
			// TODO to get tid and device id
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {
			model.addAttribute("paginationBean", paginationBean);
			/* model.addAttribute("devIdList", dIdSet); */
			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = { "/motolist" }, method = RequestMethod.GET)
	public String displayMotoTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		// logger.info("currently logged in as " + myName);
		logger.info("transaction type checking /motolist");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("moto list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/MotoTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		logger.info("MotoMid: " + currentMerchant.getMid().getMotoMid());
		paginationBean.setCurrPage(currPage);
		/*
		 * transactionService.getMotoTransForSettlement(paginationBean,
		 * currentMerchant);
		 */

		/*
		 * transactionService.getTransactionForSettlement(paginationBean,
		 * currentMerchant,"MOTO");
		 */

//     Showing error
//		transactionService.getCardTransForSettlement(paginationBean, currentMerchant, "MOTO");

		/*
		 * transactionService.getCardTransactionForSettlement(paginationBean,
		 * currentMerchant,"MOTO",null,null,null);
		 */

		List<TerminalDetails> terminalDetailsList = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMotoMid());
		/*
		 * Set<String> tidSet = new HashSet<String>(); for (TerminalDetails t :
		 * terminalDetailsList) { String mtid = t.getTid(); tidSet.add(mtid.toString());
		 * // tidSet.add(t.getDeviceId()); } ArrayList<String> dIdSet = new
		 * ArrayList<String>(); for (TerminalDetails t : terminalDetailsList) { String
		 * did = t.getDeviceId(); dIdSet.add(did.toString());
		 * 
		 * } model.addAttribute("devIdList", dIdSet); model.addAttribute("tidList",
		 * tidSet);
		 */
		model.addAttribute("terminalDetailsList", terminalDetailsList);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().size() > 0) {

			/*
			 * for (ForSettlement forSettlement : paginationBean.getItemList()) { //
			 * logger.info("boost status    " + forSettlement.getStatus());
			 * 
			 * // logger.info("tid details:" + // forSettlement.getTid().toString());
			 * 
			 * TerminalDetails terminalDetails = transactionService
			 * .getTerminalDetailsByTid(forSettlement.getTid() .toString()); if
			 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
			 * // terminalDetails.getContactName());
			 * forSettlement.setMerchantName(terminalDetails .getContactName()); }
			 * 
			 * TransactionRequest
			 * tr=transactionService.loadTransactionRequest(forSettlement.getTrxId().
			 * toString()); if(tr!=null) { forSettlement.setPan(tr.getMaskedPan()); String
			 * pan = forSettlement.getPan().substring(forSettlement.getPan().length() - 8);
			 * if (pan.contains("f")) { pan = pan.replaceAll("f", "X");
			 * forSettlement.setPan(pan); } else { forSettlement.setPan(pan); } } if
			 * (forSettlement.getAmount() != null) { double amount = 0; amount =
			 * Double.parseDouble(forSettlement.getAmount()) / 100; //
			 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
			 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
			 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
			 * forSettlement.setAmount(output); }
			 * 
			 * if (forSettlement.getStatus().equals("S")) {
			 * forSettlement.setStatus("SETTLED"); } if
			 * (forSettlement.getStatus().equals("A")) {
			 * forSettlement.setStatus("NOT SETTLED"); }
			 * 
			 * if (forSettlement.getStatus().equals("C")) {
			 * forSettlement.setStatus("CANCELLED"); }
			 * 
			 * 
			 * if (forSettlement.getTime() != null) {
			 * 
			 * try {
			 * 
			 * String sd = forSettlement.getTimeStamp(); String rd = new
			 * SimpleDateFormat("dd-MMM-yyyy") .format(new SimpleDateFormat("yyyy-MM-dd")
			 * .parse(sd));
			 * 
			 * 
			 * String rt = new SimpleDateFormat("HH:mm:ss") .format(new
			 * SimpleDateFormat("HHmmss") .parse(forSettlement.getTime()));
			 * 
			 * 
			 * String rt = new SimpleDateFormat("HH:mm:ss") .format(new SimpleDateFormat(
			 * "yyyy-MM-dd HH:mm:ss") .parse(forSettlement.getTime()));
			 * 
			 * //logger.info("time moto: " + rt); forSettlement.setDate(rd);
			 * forSettlement.setTime(rt); } catch (ParseException e) { }
			 * 
			 * }
			 * 
			 * }
			 */

			model.addAttribute("paginationBean", paginationBean);
			// TODO to get tid and device id
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {
			model.addAttribute("paginationBean", paginationBean);
			/* model.addAttribute("devIdList", dIdSet); */
			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = { "/ezywaylist" }, method = RequestMethod.GET)
	public String displayEzyWayTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		// logger.info("currently logged in as " + myName);
		logger.info("transaction type checking /Ezywaylist");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("ezyway list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzyWayTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		logger.info("Ezyway Mid: " + currentMerchant.getMid().getEzywayMid());
		paginationBean.setCurrPage(currPage);
		/*
		 * transactionService.getTransactionForSettlement(paginationBean,
		 * currentMerchant,"EZYWAY");
		 */

//Showing Error
//		transactionService.getCardTransForSettlement(paginationBean, currentMerchant, "EZYWAY");

		/*
		 * transactionService.getCardTransactionForSettlement(paginationBean,
		 * currentMerchant,"EZYWAY",null,null,null);
		 */
		List<TerminalDetails> terminalDetailsList = transactionService
				.getTerminalDetails(currentMerchant.getMid().getEzywayMid());
		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetailsList) {
			String mtid = t.getTid();
			tidSet.add(mtid.toString());
			// tidSet.add(t.getDeviceId());
		}
		ArrayList<String> dIdSet = new ArrayList<String>();
		for (TerminalDetails t : terminalDetailsList) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());

		}
		model.addAttribute("terminalDetailsList", terminalDetailsList);
		model.addAttribute("devIdList", dIdSet);
		model.addAttribute("tidList", tidSet);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().size() > 0) {
			/*
			 * for (ForSettlement forSettlement : paginationBean.getItemList()) { //
			 * logger.info("boost status    " + forSettlement.getStatus());
			 * 
			 * // logger.info("tid details:" + // forSettlement.getTid().toString());
			 * 
			 * TerminalDetails terminalDetails = transactionService
			 * .getTerminalDetailsByTid(forSettlement.getTid() .toString()); if
			 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
			 * // terminalDetails.getContactName());
			 * forSettlement.setMerchantName(terminalDetails .getContactName()); }
			 * TransactionRequest
			 * tr=transactionService.loadTransactionRequest(forSettlement.getTrxId().
			 * toString()); if(tr!=null) { forSettlement.setPan(tr.getMaskedPan()); String
			 * pan = forSettlement.getPan().substring(forSettlement.getPan().length() - 8);
			 * if (pan.contains("f")) { pan = pan.replaceAll("f", "X");
			 * forSettlement.setPan(pan); } else { forSettlement.setPan(pan); } } if
			 * (forSettlement.getAmount() != null) { double amount = 0; amount =
			 * Double.parseDouble(forSettlement.getAmount()) / 100; //
			 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
			 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
			 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
			 * forSettlement.setAmount(output); }
			 * 
			 * if (forSettlement.getStatus().equals("S")) {
			 * forSettlement.setStatus("SETTLED"); } if
			 * (forSettlement.getStatus().equals("A")) {
			 * forSettlement.setStatus("NOT SETTLED"); }
			 * 
			 * if (forSettlement.getStatus().equals("C")) {
			 * forSettlement.setStatus("CANCELLED"); }
			 * 
			 * // logger.info("date and time boost: " + // forSettlement.getTime());
			 * 
			 * if (forSettlement.getDate() != null && forSettlement.getTime() != null) {
			 * 
			 * //logger.info("stan moto: "+forSettlement.getStan());
			 * //logger.info("location moto: "+forSettlement.getLocation()); if
			 * (forSettlement.getTime() != null) { //logger.info("time moto: " +
			 * forSettlement.getTime()); try { // String sd=forSettlement.getDate()+new //
			 * SimpleDateFormat("y").format(new java.util.Date()); String sd =
			 * forSettlement.getTimeStamp(); String rd = new SimpleDateFormat("dd-MMM-yyyy")
			 * .format(new SimpleDateFormat("yyyy-MM-dd") .parse(sd));
			 * 
			 * // logger.info(" Date : " + rd + " : " + sd); String rt = new
			 * SimpleDateFormat("HH:mm:ss") .format(new SimpleDateFormat("HHmmss")
			 * .parse(forSettlement.getTime()));
			 * 
			 * 
			 * String rt = new SimpleDateFormat("HH:mm:ss") .format(new SimpleDateFormat(
			 * "yyyy-MM-dd HH:mm:ss") .parse(forSettlement.getTime()));
			 * 
			 * //logger.info("time moto: " + rt); forSettlement.setDate(rd);
			 * forSettlement.setTime(rt); } catch (ParseException e) { }
			 * 
			 * }
			 * 
			 * }
			 */

			model.addAttribute("paginationBean", paginationBean);
			// TODO to get tid and device id
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {
			model.addAttribute("paginationBean", paginationBean);
			/* model.addAttribute("devIdList", dIdSet); */
			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = { "/cancelPayment/{id}" }, method = RequestMethod.GET)
	public String cancelPaymentTransactionDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("Transactions Details",
				"merchantweb/transaction/voidpayment/CancelPaymentConfirm", null);

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");

		Merchant merchant = merchantService.loadMerchant(myName);

		logger.info("Mid" + ":" + merchant.getMid().getMid() + "MerchantName" + ":" + merchant.getBusinessName() + ":"
				+ "Merchant void logged by" + ":" + principal.getName() + ":");
		ForSettlement fs = transactionService.getForSettlement(id);
		TransactionRequest tr = transactionService.loadTransactionRequest(id);

		MotoTxnDet txnDet = new MotoTxnDet();
		txnDet.setTid(fs.getTid());
		txnDet.setMid(fs.getMid());
		txnDet.setTrxId(id);
		txnDet.setContactName(tr.getCardHolderName());
		txnDet.setApprCode(fs.getAidResponse());
		String rd = null;
		if (fs.getDate() != null) {

			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fs.getTimeStamp()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String rt = null;
		if (fs.getTime() != null) {
			try {
				rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(fs.getTime()));

			} catch (ParseException e) {
			}
		}
		txnDet.setExpectedDate(rd + " " + rt);
		double amount = 0;
		amount = Double.parseDouble(fs.getAmount()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		txnDet.setAmount(output);

		if (tr.getMaskedPan() != null) {
			String pan = tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);
			if (pan.contains("f")) {
				pan = pan.replaceAll("f", "X");
				txnDet.setPan(pan);
			} else {
				txnDet.setPan(pan);
			}

		} else {
			txnDet.setPan("NA");
		}

		txnDet.setMerchantId(merchant.getId());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", txnDet);
		model.addAttribute("loginname", principal.getName());

		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/cancelPaymentByMerchant" }, method = RequestMethod.POST)
	public String motoSubmitTransaction(final Model model, @ModelAttribute("txnDet") MotoTxnDet motoTxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");

		ResponseDetails data = MotoPaymentCommunication.CancelPayment(motoTxnDet);
		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/CancelPaymentConfirm", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", motoTxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/voidPaymentDone", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("Transactions Details",
					"merchantweb/transaction/voidpayment/CancelPaymentConfirm", null);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", motoTxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}

		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/ezyreclist" }, method = RequestMethod.GET)
	public String displayEzyRecTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		// logger.info("currently logged in as " + myName);
		logger.info("transaction type checking /Ezyreclist");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("ezyrec list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzyRecTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		logger.info("EZYREC Mid: " + currentMerchant.getMid().getEzyrecMid());

		paginationBean.setCurrPage(currPage);
		/*
		 * transactionService.getTransactionForSettlement(paginationBean,
		 * currentMerchant,"EZYREC");
		 */
//Showing Error
//		transactionService.getCardTransForSettlement(paginationBean, currentMerchant, "EZYREC");

		/*
		 * transactionService.getCardTransactionForSettlement(paginationBean,
		 * currentMerchant,"EZYREC",null,null,null);
		 */
		List<TerminalDetails> terminalDetailsList = transactionService
				.getTerminalDetails(currentMerchant.getMid().getEzyrecMid());
		/*
		 * Set<String> tidSet = new HashSet<String>(); for (TerminalDetails t :
		 * terminalDetailsList) { String mtid = t.getTid(); tidSet.add(mtid.toString());
		 * // tidSet.add(t.getDeviceId()); } ArrayList<String> dIdSet = new
		 * ArrayList<String>(); for (TerminalDetails t : terminalDetailsList) { String
		 * did = t.getDeviceId(); dIdSet.add(did.toString());
		 * 
		 * } model.addAttribute("devIdList", dIdSet); model.addAttribute("tidList",
		 * tidSet);
		 */
		model.addAttribute("terminalDetailsList", terminalDetailsList);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().size() > 0) {

			/*
			 * for (ForSettlement forSettlement : paginationBean.getItemList()) { //
			 * logger.info("boost status    " + forSettlement.getStatus());
			 * 
			 * // logger.info("tid details:" + // forSettlement.getTid().toString());
			 * 
			 * TerminalDetails terminalDetails = transactionService
			 * .getTerminalDetailsByTid(forSettlement.getTid() .toString()); if
			 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
			 * // terminalDetails.getContactName());
			 * forSettlement.setMerchantName(terminalDetails .getContactName()); } if
			 * (forSettlement.getAmount() != null) { double amount = 0; amount =
			 * Double.parseDouble(forSettlement.getAmount()) / 100; //
			 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
			 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
			 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
			 * forSettlement.setAmount(output); }
			 * 
			 * if (forSettlement.getStatus().equals("S")) {
			 * forSettlement.setStatus("SETTLED"); } if
			 * (forSettlement.getStatus().equals("A")) {
			 * forSettlement.setStatus("NOT SETTLED"); }
			 * 
			 * if (forSettlement.getStatus().equals("C")) {
			 * forSettlement.setStatus("CANCELLED"); }
			 * 
			 * // logger.info("date and time boost: " + // forSettlement.getTime());
			 * 
			 * if (forSettlement.getDate() != null && forSettlement.getTime() != null) {
			 * 
			 * //logger.info("stan moto: "+forSettlement.getStan());
			 * //logger.info("location moto: "+forSettlement.getLocation()); if
			 * (forSettlement.getTime() != null) { //logger.info("time moto: " +
			 * forSettlement.getTime()); try { // String sd=forSettlement.getDate()+new //
			 * SimpleDateFormat("y").format(new java.util.Date()); String sd =
			 * forSettlement.getTimeStamp(); String rd = new SimpleDateFormat("dd-MMM-yyyy")
			 * .format(new SimpleDateFormat("yyyy-MM-dd") .parse(sd));
			 * 
			 * // logger.info(" Date : " + rd + " : " + sd); String rt = new
			 * SimpleDateFormat("HH:mm:ss") .format(new SimpleDateFormat("HHmmss")
			 * .parse(forSettlement.getTime()));
			 * 
			 * 
			 * String rt = new SimpleDateFormat("HH:mm:ss") .format(new SimpleDateFormat(
			 * "yyyy-MM-dd HH:mm:ss") .parse(forSettlement.getTime()));
			 * 
			 * //logger.info("time moto: " + rt); forSettlement.setDate(rd);
			 * forSettlement.setTime(rt); } catch (ParseException e) { }
			 * 
			 * }
			 * 
			 * }
			 */

			model.addAttribute("paginationBean", paginationBean);
			// TODO to get tid and device id
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {
			model.addAttribute("paginationBean", paginationBean);
			/* model.addAttribute("devIdList", dIdSet); */
			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = { "/ezypasslist" }, method = RequestMethod.GET)
	public String displayEzypassTransactionSummary(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");

		logger.info("transaction type checking /ezypasslist");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("EZYPASS list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzypassTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		logger.info("ezypass mid: " + currentMerchant.getMid().getEzypassMid());
		paginationBean.setCurrPage(currPage);
		/*
		 * transactionService.getEzypassTransForSettlement(paginationBean,
		 * currentMerchant);
		 */
		/*
		 * transactionService.getTransactionForSettlement(paginationBean,
		 * currentMerchant,"EZYPASS");
		 */

//Showing Error
//		transactionService.getCardTransForSettlement(paginationBean, currentMerchant, "EZYPASS");

		/*
		 * transactionService.getCardTransactionForSettlement(paginationBean,
		 * currentMerchant,"EZYPASS",null,null,null);
		 */
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().size() > 0) {

			/*
			 * for (ForSettlement forSettlement : paginationBean.getItemList()) { //
			 * logger.info("boost status    " + forSettlement.getStatus());
			 * 
			 * // logger.info("tid details:" + // forSettlement.getTid().toString());
			 * 
			 * TerminalDetails terminalDetails = transactionService
			 * .getTerminalDetailsByTid(forSettlement.getTid() .toString()); if
			 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
			 * // terminalDetails.getContactName());
			 * forSettlement.setMerchantName(terminalDetails .getContactName()); } if
			 * (forSettlement.getAmount() != null) { double amount = 0; amount =
			 * Double.parseDouble(forSettlement.getAmount()) / 100; //
			 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
			 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
			 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
			 * forSettlement.setAmount(output); }
			 * 
			 * if (forSettlement.getStatus().equals("S")) {
			 * forSettlement.setStatus("SETTLED"); } if
			 * (forSettlement.getStatus().equals("A")) {
			 * forSettlement.setStatus("NOT SETTLED"); }
			 * 
			 * if (forSettlement.getStatus().equals("C")) {
			 * forSettlement.setStatus("CANCELLED"); }
			 * 
			 * // logger.info("date and time boost: " + // forSettlement.getTime());
			 * 
			 * if (forSettlement.getDate() != null && forSettlement.getTime() != null) {
			 * 
			 * //logger.info("stan moto: "+forSettlement.getStan());
			 * //logger.info("location moto: "+forSettlement.getLocation()); if
			 * (forSettlement.getTime() != null) { //logger.info("time moto: " +
			 * forSettlement.getTime()); try { // String sd=forSettlement.getDate()+new //
			 * SimpleDateFormat("y").format(new java.util.Date()); String sd =
			 * forSettlement.getTimeStamp(); String rd = new SimpleDateFormat("dd-MMM-yyyy")
			 * .format(new SimpleDateFormat("yyyy-MM-dd") .parse(sd));
			 * 
			 * // logger.info(" Date : " + rd + " : " + sd); String rt = new
			 * SimpleDateFormat("HH:mm:ss") .format(new SimpleDateFormat("HHmmss")
			 * .parse(forSettlement.getTime()));
			 * 
			 * 
			 * String rt = new SimpleDateFormat("HH:mm:ss") .format(new SimpleDateFormat(
			 * "yyyy-MM-dd HH:mm:ss") .parse(forSettlement.getTime()));
			 * 
			 * //logger.info("time moto: " + rt); forSettlement.setDate(rd);
			 * forSettlement.setTime(rt); } catch (ParseException e) { }
			 * 
			 * }
			 * 
			 * }
			 */

			model.addAttribute("paginationBean", paginationBean);
			// TODO to get tid and device id
			List<TerminalDetails> terminalDetails = transactionService
					.getTerminalDetails(currentMerchant.getMid().getMid());
			Set<String> tidSet = new HashSet<String>();
			for (TerminalDetails t : terminalDetails) {
				String mtid = t.getTid();
				tidSet.add(mtid.toString());
				// tidSet.add(t.getDeviceId());
			}
			ArrayList<String> dIdSet = new ArrayList<String>();
			for (TerminalDetails t : terminalDetails) {
				String did = t.getDeviceId();
				dIdSet.add(did.toString());

			}
			model.addAttribute("devIdList", dIdSet);
			model.addAttribute("tidList", tidSet);
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {
			model.addAttribute("paginationBean", paginationBean);
			/* model.addAttribute("devIdList", dIdSet); */
			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String displayTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status,
			@RequestParam("txnType") String txnType, HttpServletRequest request,

			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");
		logger.info("Search All UMobile Transaction By Merchant " + myName);
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		Date frdate = new Date(fromDate);
		Integer fromday = frdate.getDate();
		Integer frommon = frdate.getMonth() + 1;
		Integer fromyear = frdate.getYear();
		Integer currentFrYear = fromyear + 1900;
		String frmon = String.format("%02d", frommon);
		String frday = String.format("%02d", fromday);
		// String fromdateString = frday + '/' + frmon + '/' +
		// String.valueOf(currentFrYear);
		String fromDate1 = String.valueOf(currentFrYear) + '-' + frmon + '-' + frday;

		Date todate = new Date(toDate);
		Integer today = todate.getDate();
		Integer tomon = todate.getMonth() + 1;
		Integer toyear = todate.getYear();
		Integer currentToYear = toyear + 1900;
		String tmon = String.format("%02d", tomon);
		String tday = String.format("%02d", today);
		// String todateString = tday + '/' + tmon + '/' +
		// String.valueOf(currentToYear);
		String toDate1 = String.valueOf(currentToYear) + '-' + tmon + '-' + tday;

		logger.info("from date:" + fromDate1 + " to date:" + toDate1);

		/*
		 * String fromDate1 = null; String toDate1 = null; if ((fromDate != null ||
		 * !fromDate.isEmpty()) && (toDate != null || !toDate.isEmpty())) { try {
		 * fromDate1 = new SimpleDateFormat("yyyy-MM-dd") .format(new
		 * SimpleDateFormat("dd/MM/yyyy") .parse(fromDate)); toDate1 = new
		 * SimpleDateFormat("yyyy-MM-dd") .format(new SimpleDateFormat("dd/MM/yyyy")
		 * .parse(toDate)); } catch (ParseException e) {
		 * 
		 * e.printStackTrace(); } }
		 */
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUmList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.searchUMForSettlement(fromDate1, toDate1, tid, status, paginationBean, currentMerchant,
				txnType);

		model.addAttribute("fromDate", fromDate1);

		model.addAttribute("toDate", toDate1);
		model.addAttribute("tid", tid);

		model.addAttribute("status", status);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getUmMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			tidSet.add(mtid.toString());
		}

		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}
		model.addAttribute("devIdList", dIdSet);

		model.addAttribute("tidList", tidSet);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/searchcashtrans", method = RequestMethod.POST)
	public String displayCashTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		String dat = null;
		String dat1 = null;

		if (!(fromDate == null || fromDate.equals("")) && !(toDate == null || toDate.equals(""))) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
		}
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/cashTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		transactionService.searchForSettlementcash(dat, dat1, paginationBean, currentMerchant);
		/*
		 * transactionService.searchTransactionForSettlement(dat, dat1, paginationBean,
		 * currentMerchant,"CASH");
		 */

		model.addAttribute("fromDate", fromDate);

		model.addAttribute("toDate", toDate);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		for (ForSettlement forSettlement : paginationBean.getItemList()) {
			TerminalDetails terminalDetails = transactionService
					.getTerminalDetailsByTid(forSettlement.getTid().toString());
			if (terminalDetails != null) {

				forSettlement.setMerchantName(terminalDetails.getContactName());
			}
			if (forSettlement.getAmount() != null) {
				double amount = 0;
				amount = Double.parseDouble(forSettlement.getAmount()) / 100;

				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);

				forSettlement.setAmount(output);
			}
			if (forSettlement.getStatus().equals("CT")) {
				forSettlement.setStatus("CASH SALE");
			}
			if (forSettlement.getStatus().equals("CV")) {
				forSettlement.setStatus("CASH CANCELLED");
			}

			if (forSettlement.getDate() != null && forSettlement.getTime() != null) {
				try {

					String sd = forSettlement.getTimeStamp();
					String rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd").parse(sd));
					String rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
					forSettlement.setDate(rd);
					forSettlement.setTime(rt);
				} catch (ParseException e) {
				}

			}
		}
		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			tidSet.add(mtid.toString());
		}
		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/searchcardtrans", method = RequestMethod.POST)
	public String displaycardTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		/*
		 * Merchant currentMerchant = merchantService.loadMerchant(principal.getName());
		 */

		String dat = fromDate;
		String dat1 = toDate;

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/cardUMTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");
		transactionService.searchCardUMForSettlement(dat, dat1, tid, status, paginationBean, currentMerchant);

		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		model.addAttribute("tid", tid);
		model.addAttribute("status", status);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		logger.info("No of Records " + paginationBean.getItemList().size());
		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) {
		 * TerminalDetails terminalDetails = transactionService
		 * .getTerminalDetailsByTid(forSettlement.getTid().toString()); if
		 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
		 * // terminalDetails.getContactName());
		 * forSettlement.setMerchantName(terminalDetails.getContactName()); } if
		 * (forSettlement.getAmount() != null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()) / 100; //
		 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
		 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
		 * forSettlement.setAmount(output); } if (forSettlement.getStatus().equals("S"))
		 * { forSettlement.setStatus("SETTLED"); } if
		 * (forSettlement.getStatus().equals("P")) { forSettlement.setStatus("PENDING");
		 * } if (forSettlement.getStatus().equals("A")) {
		 * forSettlement.setStatus("NOT SETTLED"); } if
		 * (forSettlement.getStatus().equals("C")) {
		 * forSettlement.setStatus("CANCELLED"); } if
		 * (forSettlement.getStatus().equals("R")) {
		 * forSettlement.setStatus("REVERSAL"); } if (forSettlement.getDate() != null &&
		 * forSettlement.getTime() != null && forSettlement.getTimeStamp()!= null ) {
		 * try { // String sd=forSettlement.getDate()+new //
		 * SimpleDateFormat("y").format(new java.util.Date()); String sd =
		 * forSettlement.getTimeStamp(); String rd = new SimpleDateFormat("dd-MMM-yyyy")
		 * .format(new SimpleDateFormat("yyyy-MM-dd") .parse(sd)); String rt = new
		 * SimpleDateFormat("HH:mm:ss") .format(new SimpleDateFormat("HHmmss")
		 * .parse(forSettlement.getTime())); forSettlement.setDate(rd);
		 * forSettlement.setTime(rt); } catch (ParseException e) { }
		 * 
		 * } }
		 */
		/*
		 * String myName = principal.getName(); logger.info("currently logged in as " +
		 * myName); Merchant currentMerchant = merchantService.loadMerchant(myName);
		 */
		List<TerminalDetails> terminalDetailsList = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMid());

		/*
		 * Set<String> tidSet = new HashSet<String>(); for (TerminalDetails t :
		 * terminalDetails) { String mtid = t.getTid(); tidSet.add(mtid.toString()); }
		 * 
		 * Set<String> dIdSet = new HashSet<String>(); for (TerminalDetails t :
		 * terminalDetails) { String did = t.getDeviceId(); dIdSet.add(did.toString());
		 * }
		 */
		model.addAttribute("terminalDetailsList", terminalDetailsList);

		// model.addAttribute("tidList", tidSet);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/searchboost", method = RequestMethod.POST)
	public String displayboostTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("current username: " + myName);
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		/*
		 * Merchant currentMerchant = merchantService.loadMerchant(principal
		 * .getName());
		 */

		logger.info("current merchant MID: " + currentMerchant.getMid().getMid());

		String dat = null;
		String dat1 = null;

		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": &
		// :"+tid+": & :"+status);
		if (!(fromDate == null || fromDate.equals("")) && !(toDate == null || toDate.equals(""))) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/boostTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");
		transactionService.searchForSettlementBoost(dat, dat1, tid, status, paginationBean, currentMerchant);

		// transactionService.getCardTransactionForSettlement(paginationBean,
		// currentMerchant, "BOOST", fromDate, toDate,status);

		model.addAttribute("fromDate", fromDate);

		model.addAttribute("toDate", toDate);
		model.addAttribute("tid", tid);

		model.addAttribute("status", status);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) {
		 * TerminalDetails terminalDetails = transactionService
		 * .getTerminalDetailsByTid(forSettlement.getTid().toString()); if
		 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
		 * // terminalDetails.getContactName());
		 * forSettlement.setMerchantName(terminalDetails.getContactName()); }
		 */
		/*
		 * if (forSettlement.getAmount() != null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()); //
		 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
		 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
		 * forSettlement.setAmount(output); } if
		 * (forSettlement.getStatus().equals("BPS")) {
		 * forSettlement.setStatus("BOOST SETTLED"); } if
		 * (forSettlement.getStatus().equals("BP")) {
		 * forSettlement.setStatus("BOOST PAYMENT"); } if
		 * (forSettlement.getStatus().equals("BPA")) {
		 * forSettlement.setStatus("BOOST PAYMENT"); } if
		 * (forSettlement.getStatus().equals("BPC")) {
		 * forSettlement.setStatus("BOOST CANCELLED"); }
		 * 
		 * if ( forSettlement.getTime() != null) { try {
		 * 
		 * String sd = forSettlement.getTimeStamp(); String rd = new
		 * SimpleDateFormat("dd-MMM-yyyy") .format(new SimpleDateFormat("yyyy-MM-dd")
		 * .parse(sd)); String rt = new SimpleDateFormat("HH:mm:ss") .format(new
		 * SimpleDateFormat("HHmmss") .parse(forSettlement.getTime()));
		 * forSettlement.setDate(rd); forSettlement.setTime(rt); } catch (ParseException
		 * e) { }
		 * 
		 * } }
		 */
		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			tidSet.add(mtid.toString());
		}

		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}
		model.addAttribute("devIdList", dIdSet);

		model.addAttribute("tidList", tidSet);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/searchmoto", method = RequestMethod.POST)
	public String displayMotoTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String dat = null;
		String dat1 = null;

		if (!(fromDate == null || fromDate.equals("")) && !(toDate == null || toDate.equals(""))) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/MotoTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		List<TerminalDetails> terminalDetailsList = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMotoMid());
		transactionService.searchUMForSettlementMoto(dat, dat1, tid, status, paginationBean, currentMerchant);

		// transactionService.getCardTransactionForSettlement(paginationBean,
		// currentMerchant, "MOTO", fromDate, toDate,status);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("fromDate", fromDate);
		model.addAttribute("toDate", toDate);
		model.addAttribute("tid", tid);

		model.addAttribute("status", status);
		model.addAttribute("terminalDetailsList", terminalDetailsList);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/searchezyway", method = RequestMethod.POST)
	public String searchEzyWayTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		/*
		 * Merchant currentMerchant = merchantService.loadMerchant(principal
		 * .getName());
		 */

		String dat = null;
		String dat1 = null;

		if (!(fromDate == null || fromDate.equals("")) && !(toDate == null || toDate.equals(""))) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzyWayTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		logger.info("test transaction " + fromDate);

//Showing Error		
//		transactionService.searchForSettlementEzyWay(dat, dat1, tid, status, paginationBean, currentMerchant);

		// transactionService.getCardTransactionForSettlement(paginationBean,
		// currentMerchant, "EZYWAY", fromDate, toDate,status);

		model.addAttribute("fromDate", fromDate);

		model.addAttribute("toDate", toDate);
		model.addAttribute("tid", tid);

		model.addAttribute("status", status);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) {
		 * TerminalDetails terminalDetails = transactionService
		 * .getTerminalDetailsByTid(forSettlement.getTid().toString()); if
		 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
		 * // terminalDetails.getContactName());
		 * forSettlement.setMerchantName(terminalDetails.getContactName()); }
		 */
		/*
		 * if (forSettlement.getAmount() != null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()); //
		 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
		 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
		 * forSettlement.setAmount(output); } if
		 * (forSettlement.getStatus().equals("BPS")) {
		 * forSettlement.setStatus("BOOST SETTLED"); } if
		 * (forSettlement.getStatus().equals("BP")) {
		 * forSettlement.setStatus("BOOST PAYMENT"); } if
		 * (forSettlement.getStatus().equals("BPA")) {
		 * forSettlement.setStatus("BOOST PAYMENT"); } if
		 * (forSettlement.getStatus().equals("BPC")) {
		 * forSettlement.setStatus("BOOST CANCELLED"); }
		 * 
		 * if ( forSettlement.getTime() != null) { try {
		 * 
		 * String sd = forSettlement.getTimeStamp(); String rd = new
		 * SimpleDateFormat("dd-MMM-yyyy") .format(new SimpleDateFormat("yyyy-MM-dd")
		 * .parse(sd)); String rt = new SimpleDateFormat("HH:mm:ss") .format(new
		 * SimpleDateFormat("HHmmss") .parse(forSettlement.getTime()));
		 * forSettlement.setDate(rd); forSettlement.setTime(rt); } catch (ParseException
		 * e) { }
		 * 
		 * } }
		 */
		List<TerminalDetails> terminalDetailsList = transactionService
				.getTerminalDetails(currentMerchant.getMid().getEzywayMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetailsList) {
			String mtid = t.getTid();
			tidSet.add(mtid.toString());
		}

		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetailsList) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}
		model.addAttribute("devIdList", dIdSet);
		model.addAttribute("terminalDetailsList", terminalDetailsList);

		model.addAttribute("tidList", tidSet);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/searchezyrec", method = RequestMethod.POST)
	public String searchEzyrecTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		/*
		 * Merchant currentMerchant = merchantService.loadMerchant(principal
		 * .getName());
		 */

		String dat = null;
		String dat1 = null;

		// String txn_Type = "MOTO";
		// logger.info("status: " + status + " txn_type: " + txn_Type);

		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": &
		// :"+tid+": & :"+status);
		if (!(fromDate == null || fromDate.equals("")) && !(toDate == null || toDate.equals(""))) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzyRecTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		logger.info("test transaction " + fromDate);

//Showing Error
//		transactionService.searchForSettlementEzyRec(dat, dat1, tid, status, paginationBean, currentMerchant);

		// transactionService.getCardTransactionForSettlement(paginationBean,
		// currentMerchant, "EZYREC", fromDate, toDate,status);

		model.addAttribute("fromDate", fromDate);

		model.addAttribute("toDate", toDate);
		model.addAttribute("tid", tid);

		model.addAttribute("status", status);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) {
		 * TerminalDetails terminalDetails = transactionService
		 * .getTerminalDetailsByTid(forSettlement.getTid().toString()); if
		 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
		 * // terminalDetails.getContactName());
		 * forSettlement.setMerchantName(terminalDetails.getContactName()); }
		 */
		/*
		 * if (forSettlement.getAmount() != null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()); //
		 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
		 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
		 * forSettlement.setAmount(output); } if
		 * (forSettlement.getStatus().equals("BPS")) {
		 * forSettlement.setStatus("BOOST SETTLED"); } if
		 * (forSettlement.getStatus().equals("BP")) {
		 * forSettlement.setStatus("BOOST PAYMENT"); } if
		 * (forSettlement.getStatus().equals("BPA")) {
		 * forSettlement.setStatus("BOOST PAYMENT"); } if
		 * (forSettlement.getStatus().equals("BPC")) {
		 * forSettlement.setStatus("BOOST CANCELLED"); }
		 * 
		 * if ( forSettlement.getTime() != null) { try {
		 * 
		 * String sd = forSettlement.getTimeStamp(); String rd = new
		 * SimpleDateFormat("dd-MMM-yyyy") .format(new SimpleDateFormat("yyyy-MM-dd")
		 * .parse(sd)); String rt = new SimpleDateFormat("HH:mm:ss") .format(new
		 * SimpleDateFormat("HHmmss") .parse(forSettlement.getTime()));
		 * forSettlement.setDate(rd); forSettlement.setTime(rt); } catch (ParseException
		 * e) { }
		 * 
		 * } }
		 */

		/*
		 * Set<String> tidSet = new HashSet<String>(); for (TerminalDetails t :
		 * terminalDetails) { String mtid = t.getTid(); tidSet.add(mtid.toString()); }
		 * 
		 * Set<String> dIdSet = new HashSet<String>(); for (TerminalDetails t :
		 * terminalDetails) { String did = t.getDeviceId(); dIdSet.add(did.toString());
		 * } model.addAttribute("devIdList", dIdSet);
		 * 
		 * model.addAttribute("tidList", tidSet);
		 */
		List<TerminalDetails> terminalDetailsList = transactionService
				.getTerminalDetails(currentMerchant.getMid().getEzyrecMid());
		model.addAttribute("terminalDetailsList", terminalDetailsList);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/searchezypass", method = RequestMethod.POST)
	public String searchEzyPassTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		/*
		 * Merchant currentMerchant = merchantService.loadMerchant(principal
		 * .getName());
		 */

		String dat = null;
		String dat1 = null;

		// String txn_Type = "MOTO";
		// logger.info("status: " + status + " txn_type: " + txn_Type);

		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": &
		// :"+tid+": & :"+status);
		if (!(fromDate == null || fromDate.equals("")) && !(toDate == null || toDate.equals(""))) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzypassTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		logger.info("test transaction " + fromDate);
		transactionService.searchForSettlementEzyPass(dat, dat1, tid, status, paginationBean, currentMerchant);

		// transactionService.getCardTransactionForSettlement(paginationBean,
		// currentMerchant, "EZYPASS", fromDate, toDate,status);

		model.addAttribute("fromDate", fromDate);

		model.addAttribute("toDate", toDate);
		model.addAttribute("tid", tid);

		model.addAttribute("status", status);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) {
		 * TerminalDetails terminalDetails = transactionService
		 * .getTerminalDetailsByTid(forSettlement.getTid().toString()); if
		 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
		 * // terminalDetails.getContactName());
		 * forSettlement.setMerchantName(terminalDetails.getContactName()); }
		 */
		/*
		 * if (forSettlement.getAmount() != null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()); //
		 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
		 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
		 * forSettlement.setAmount(output); } if
		 * (forSettlement.getStatus().equals("BPS")) {
		 * forSettlement.setStatus("BOOST SETTLED"); } if
		 * (forSettlement.getStatus().equals("BP")) {
		 * forSettlement.setStatus("BOOST PAYMENT"); } if
		 * (forSettlement.getStatus().equals("BPA")) {
		 * forSettlement.setStatus("BOOST PAYMENT"); } if
		 * (forSettlement.getStatus().equals("BPC")) {
		 * forSettlement.setStatus("BOOST CANCELLED"); }
		 * 
		 * if ( forSettlement.getTime() != null) { try {
		 * 
		 * String sd = forSettlement.getTimeStamp(); String rd = new
		 * SimpleDateFormat("dd-MMM-yyyy") .format(new SimpleDateFormat("yyyy-MM-dd")
		 * .parse(sd)); String rt = new SimpleDateFormat("HH:mm:ss") .format(new
		 * SimpleDateFormat("HHmmss") .parse(forSettlement.getTime()));
		 * forSettlement.setDate(rd); forSettlement.setTime(rt); } catch (ParseException
		 * e) { }
		 * 
		 * } }
		 */
		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getEzypassMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			tidSet.add(mtid.toString());
		}

		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}
		model.addAttribute("devIdList", dIdSet);

		model.addAttribute("tidList", tidSet);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// rk
	@RequestMapping(value = { "/UMdetails/{id}" }, method = RequestMethod.GET)
	public String displayUMReceiptDetails(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		HttpSession session = request.getSession();

		logger.info("mrn" + id);

		PageBean pageBean = new PageBean("Transactions Details", "merchantweb/transaction/receipt_UM", null);

		/*
		 * PageBean pageBean = new PageBean("transactions details",
		 * "merchantweb/transaction/receipt_v0.2", Module.TRANSACTION_WEB,
		 * "merchantweb/transaction/sideMenuTransaction");
		 */

		int amount = 0;
		double amt = 0.0;

		String myName = (String) session.getAttribute("userName");
		logger.info("myName" + myName);
		Merchant merchant = merchantService.loadMerchant(myName);
		// logger.info("merchant"+merchant.getBusinessName());

		UMEcomTxnRequest req = transactionService.loadUMEzywayTransactionRequest(id);
		UMEcomTxnResponse tr = transactionService.loadUMEzywayTransactionResponse(id);

		logger.info("UMEcomTxnResponse" + tr.getF263_MRN());

		DataTransferObject dt = new DataTransferObject();
		try {

			dt.setMerchantName(merchant.getBusinessName());
			dt.setMerchantAddr1(merchant.getBusinessAddress1());
			dt.setMerchantAddr2(merchant.getBusinessAddress2());
			dt.setMerchantCity(merchant.getCity());
			dt.setMerchantPostCode(merchant.getPostcode());
			dt.setMerchantContNo(merchant.getBusinessContactNumber());
			dt.setMerchantState(merchant.getState());

			logger.info("merchant State::" + merchant.getState() + "::;" + dt.getMerchantState());

			if (tr.getF270_ORN() != null) {
				dt.setRefNo(tr.getF270_ORN());
				logger.info("invoice id:" + dt.getRefNo());
			} else {
				dt.setRefNo("");
			}
			if (tr.getF354_TID() != null) {

				logger.info("tid" + tr.getF354_TID());
				dt.setTid(tr.getF354_TID());
			}
			dt.setMid(tr.getF001_MID());
			/*
			 * if(tr.getF007_TxnAmt()!=null) { amount=Integer.parseInt(tr.getF007_TxnAmt());
			 * amt=amount/100; logger.info("amount after change :"+amount);
			 *
			 * }
			 */

			if (tr.getF007_TxnAmt() != null) {

				amt = Double.parseDouble(tr.getF007_TxnAmt()) / 100;

				logger.info("amount after change :" + amount);

			}

			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String strTotal = myFormatter.format(amt);
			dt.setAmount(strTotal);
			dt.setTotal(strTotal);

			logger.info("strTotal" + strTotal);

			String sd = tr.getTimeStamp();
			try {

				String rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tr.getTimeStamp()));
				String rt = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tr.getTimeStamp()));
				dt.setDate(rd);
				dt.setTime(rt);
				logger.info("rd and rt" + rd + "::" + rt);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			/*
			 * if(tr.getStatus().equals("S")||tr.getStatus().equals("A")) {
			 * logger.info("status"+tr.getStatus()); dt.setTxnType("EZYWAY SALE");
			 *
			 * }else{ logger.info("status"+tr.getStatus()); dt.setTxnType("EZYWAY VOID"); }
			 */

			/*
			 * if(tr.getStatus().equals("S")||tr.getStatus().equals("A")) {
			 * logger.info("status"+tr.getStatus());
			 *
			 * dt.setTxnType(req.getTxnType()+" SALE");
			 *
			 *
			 * }else{ if(req.getTxnType().equals("EZYWAY")) { dt.setTxnType("EZYWAY VOID");
			 * }else { dt.setTxnType("EZYMOTO VOID"); }
			 *
			 *
			 * }
			 */

			/*
			 * logger.info("status"+tr.getStatus());
			 *
			 * if(tr.getStatus().equals("S")||tr.getStatus().equals("A")) {
			 * logger.info("status"+tr.getStatus()); if(req.getTxnType() == null) {
			 * dt.setTxnType("EZYWAY SALE"); }else {
			 * dt.setTxnType(req.getTxnType()+" SALE"); }
			 *
			 *
			 * } else {
			 *
			 * logger.info("else status" + req.getTxnType()); if (req.getTxnType() == null)
			 * { dt.setTxnType("EZYWAY VOID"); } else { if
			 * (req.getTxnType().equals("EZYWAY")) {
			 *
			 * dt.setTxnType("EZYWAY VOID"); } else if (req.getTxnType().equals("EZYMOTO"))
			 * { dt.setTxnType("EZYMOTO VOID"); } else { dt.setTxnType("EZYAUTH VOID"); } }
			 *
			 * }
			 */
			logger.info(" here rk merchant split values " + tr.getH002_VNO() + " " + tr.getF256_FiCode() + " "
					+ tr.getTxnType());
			if (tr.getStatus().equalsIgnoreCase("S") || tr.getStatus().equalsIgnoreCase("A")
					|| tr.getStatus().equalsIgnoreCase("E")) {
				logger.info("status S or A:" + tr.getStatus());
				logger.info("version 3 or 5:" + tr.getH002_VNO());
				logger.info("Txn Type:" + tr.getTxnType());
				/*
				 * if(req.getTxnType() == null) { dt.setTxnType("EZYWAY SALE"); }else {
				 * dt.setTxnType(req.getTxnType()+" SALE"); }
				 */

				if (tr.getH002_VNO().equals("03")) {
					logger.info("inside 03 :::::::::::::");
					logger.info("Txn Type:" + tr.getTxnType());

					if (tr.getF256_FiCode() != null && tr.getF256_FiCode() != "") {
						logger.info("ficode is not null");

						if ((tr.getF256_FiCode().equalsIgnoreCase("SPLATER"))
								&& (tr.getTxnType().equalsIgnoreCase("EZYAUTH")
										|| tr.getTxnType().equalsIgnoreCase("EZYMOTO")
										|| tr.getTxnType().equalsIgnoreCase("AUTHSALE")
										|| tr.getTxnType().equalsIgnoreCase("EZYLINK"))) {
							logger.info("inside ezysplitsale");
							dt.setTxnType("EZYSPLIT SALE");

						} else if (tr.getTxnType().equalsIgnoreCase("EZYMOTO")) {
							logger.info("inside ezylinksale :::::::::::::");

							dt.setTxnType("EZYLINK SALE");
						}

						else if (tr.getTxnType().equalsIgnoreCase("EZYWAY")) {

							dt.setTxnType("EZYWAY SALE");
						}

					}

					else if (tr.getTxnType().equalsIgnoreCase("EZYMOTO")) {
						logger.info("inside ezylinksale :::::::::::::");

						dt.setTxnType("EZYLINK SALE");
					} else if (tr.getTxnType().equalsIgnoreCase("EZYWAY")) {

						dt.setTxnType("EZYWAY SALE");
					}

				}

				else if (tr.getH002_VNO().equals("05")) {

					if (tr.getF256_FiCode() != null && tr.getF256_FiCode() != "") {
						logger.info("ficode is not null");

						if ((tr.getF256_FiCode().equalsIgnoreCase("SPLATER"))
								&& (tr.getTxnType().equalsIgnoreCase("EZYAUTH")
										|| tr.getTxnType().equalsIgnoreCase("EZYMOTO")
										|| tr.getTxnType().equalsIgnoreCase("AUTHSALE")
										|| tr.getTxnType().equalsIgnoreCase("EZYLINK"))) {
							logger.info("inside ezysplitsale");
							dt.setTxnType("EZYSPLIT SALE");

						} else if (tr.getTxnType().equals("EZYMOTO")) {

							dt.setTxnType("EZYMOTO SALE");
						}

						else if (tr.getTxnType().equals("EZYAUTH")) {

							dt.setTxnType("EZYAUTH SALE");
						}

						else if (tr.getTxnType().equals("EZYREC")) {

							dt.setTxnType("EZYREC SALE");
						}

					} else if (tr.getTxnType().equals("EZYMOTO")) {

						dt.setTxnType("EZYMOTO SALE");
					}

					else if (tr.getTxnType().equals("EZYAUTH")) {

						dt.setTxnType("EZYAUTH SALE");
					}

					else if (tr.getTxnType().equals("EZYREC")) {

						dt.setTxnType("EZYREC SALE");
					}

				}

				else if (tr.getH002_VNO().equals("05V")) {

					if (tr.getH002_VNO().equals("05V") && tr.getTxnType().equalsIgnoreCase("EZYMOTO")) {

						dt.setTxnType("EZYMOTO VCC SALE");
					}

				}

			} else if (tr.getStatus().equalsIgnoreCase("C")) {

				logger.info("status C:" + tr.getStatus());
				logger.info("version 3 or 5:" + tr.getH002_VNO());
				logger.info("Txn Type:" + tr.getTxnType());

				if (tr.getH002_VNO().equals("03")) {
					if (tr.getF256_FiCode() != null && tr.getF256_FiCode() != "") {
						logger.info("ficode is not null");
						if ((tr.getF256_FiCode().equalsIgnoreCase("SPLATER"))
								&& (tr.getTxnType().equalsIgnoreCase("EZYAUTH")
										|| tr.getTxnType().equalsIgnoreCase("EZYMOTO")
										|| tr.getTxnType().equalsIgnoreCase("AUTHSALE")
										|| tr.getTxnType().equalsIgnoreCase("EZYLINK"))) {
							logger.info("inside ezysplitsale");
							dt.setTxnType("EZYSPLIT VOID");

						}

						else if (tr.getTxnType().equalsIgnoreCase("EZYMOTO")) {

							dt.setTxnType("EZYLINK VOID");
						}

						else if (tr.getTxnType().equalsIgnoreCase("EZYWAY")) {

							dt.setTxnType("EZYWAY VOID");
						}
					} else if (tr.getTxnType().equalsIgnoreCase("EZYMOTO")) {

						dt.setTxnType("EZYLINK VOID");
					} else if (tr.getTxnType().equalsIgnoreCase("EZYWAY")) {

						dt.setTxnType("EZYWAY VOID");
					}
				}

				else if (tr.getH002_VNO().equals("05")) {
					if (tr.getF256_FiCode() != null && tr.getF256_FiCode() != "") {
						logger.info("ficode is not null");
						if ((tr.getF256_FiCode().equalsIgnoreCase("SPLATER"))
								&& (tr.getTxnType().equalsIgnoreCase("EZYAUTH")
										|| tr.getTxnType().equalsIgnoreCase("EZYMOTO")
										|| tr.getTxnType().equalsIgnoreCase("AUTHSALE")
										|| tr.getTxnType().equalsIgnoreCase("EZYLINK"))) {
							logger.info("inside ezysplitsale");
							dt.setTxnType("EZYSPLIT VOID");

						}

						else if (tr.getTxnType().equalsIgnoreCase("EZYMOTO")) {

							dt.setTxnType("EZYMOTO VOID");
						}

						else if (tr.getTxnType().equalsIgnoreCase("EZYAUTH")) {

							dt.setTxnType("EZYAUTH VOID");
						}

						else if (tr.getTxnType().equalsIgnoreCase("EZYREC")) {

							dt.setTxnType("EZYREC VOID");
						}
					}

					else if (tr.getTxnType().equalsIgnoreCase("EZYMOTO")) {

						dt.setTxnType("EZYMOTO VOID");
					}

					else if (tr.getTxnType().equalsIgnoreCase("EZYAUTH")) {

						dt.setTxnType("EZYAUTH VOID");
					}

					else if (tr.getTxnType().equalsIgnoreCase("EZYREC")) {

						dt.setTxnType("EZYREC VOID");
					}

				}

				else if (tr.getH002_VNO().equals("05V")) {

					if (tr.getTxnType().equalsIgnoreCase("EZYMOTO")) {

						dt.setTxnType("EZYMOTO VCC VOID");
					}

				}

			}

			else {

				logger.info("No status :" + tr.getStatus());
				logger.info("version 3 or 5:" + tr.getH002_VNO());
				logger.info("Txn Type:" + tr.getTxnType());

				logger.info("Set default as EZYWAY");

				dt.setTxnType("EZYWAY");

			}

			dt.setMaskedPan(tr.getMaskedPan());
			dt.setCardHolderName(req.getF268_ChName());

			String cardNum = null;
			if (tr.getMaskedPan() == null || tr.getMaskedPan().length() < 4) {
				cardNum = tr.getMaskedPan();
			} else {
				cardNum = tr.getMaskedPan().substring(tr.getMaskedPan().length() - 4);
			}
			String finalNum = String.format("XXXX %s", cardNum);
			dt.setCardNo(finalNum);
			logger.info("finalNum" + finalNum);
			dt.setRrn(tr.getF023_RRN());
			dt.setAid(tr.getF011_AuthIDResp());
			dt.setApprCode(tr.getF011_AuthIDResp());
			dt.setCardType(tr.getF350_CrdTyp());

			logger.info("ApprCod" + dt.getApprCode());

		} catch (Exception e) {
			System.out.println("Record not Found..!");
			request.setAttribute("errMsg", "Data not Available for this Transaction ID...!");
		}
		logger.info(dt.getTxnType());
		request.setAttribute("dto", dt);
		model.addAttribute("pageBean", pageBean);
		// return "merchantweb/transaction/CardReceiptNew";
		// return "merchantweb/transaction/receipt";
		return "merchantweb/transaction/receipt_UM";
	}

	// Boost Sales Slip - Start

	@RequestMapping(value = { "/BoostSlip/{id}" }, method = RequestMethod.GET)
	public String BoostSalesSlip(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		HttpSession session = request.getSession();

		logger.info("BOOST RRN = " + id);

		PageBean pageBean = new PageBean("Boost Transaction Sales Slip", "merchantweb/transaction/Boost_Receipt", null);

		String myName = (String) session.getAttribute("userName");
		logger.info("myName" + myName);
		Merchant merchant = merchantService.loadMerchant(myName);

		ForSettlement fsboost = transactionService.loadBoostForSettlement(id);

		DataTransferObject dt = new DataTransferObject();
		try {

			logger.info("Initiate ---------- Boost Transaction Slip ");

			dt.setMerchantName(merchant.getBusinessName());
			dt.setMerchantAddr1(merchant.getBusinessAddress1());
			dt.setMerchantCity(merchant.getCity());
			dt.setMerchantPostCode(merchant.getPostcode());
			dt.setMerchantContNo(merchant.getBusinessContactNumber());
			dt.setMerchantState(merchant.getState());

			// Date
			String date = null;
			try {
				date = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fsboost.getTimeStamp()));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			fsboost.setDate(date);

			logger.info("Date Format = " + fsboost.getDate());

			// Time
			String time = null;
			try {
				time = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fsboost.getTimeStamp()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			fsboost.setTime(time);
			logger.info("Time Format = " + fsboost.getTime());
			// MID
			logger.info(" MID = " + fsboost.getMid());
			// TID
			logger.info(" TID = " + fsboost.getTid());

			String amt = fsboost.getAmount().toString();
			double amt1 = Double.parseDouble(amt);
			amt1 = amt1 / 100;

			String pattern3 = "#,##0.00";
			DecimalFormat myFormatter3 = new DecimalFormat(pattern3);
			String BoostAmount = myFormatter3.format(amt1);
			fsboost.setAmount(BoostAmount);
			logger.info(" Amount = " + fsboost.getAmount());
			logger.info(" TxnType = " + fsboost.getTxnType());
			logger.info(" RRN = " + fsboost.getRrn());
			logger.info(" Stan = " + fsboost.getStan());
			logger.info(" AidResponse = " + fsboost.getAidResponse());
			logger.info(" Reference / Invoice ID = " + fsboost.getInvoiceId());

			dt.setDate(fsboost.getDate());
			dt.setTime(fsboost.getTime());
			dt.setTotal(fsboost.getAmount());
			dt.setRefNo(fsboost.getRrn());
			dt.setApprCode(fsboost.getAidResponse());
			dt.setInvoiceNo(fsboost.getInvoiceId());
			dt.setMid(fsboost.getMid());
			dt.setTid(fsboost.getTid());

			// To Check Status

			if (fsboost.getStatus().equals("BPS") || fsboost.getStatus().equals("BPA")) {
				dt.setTxnType("BOOST SALE");
			} else if (fsboost.getStatus().equals("BPC")) {
				dt.setTxnType("BOOST VOID");
			}

			logger.info("Ready To View ---------- Boost Transaction Slip ");

		} catch (Exception e) {

			logger.error("Error To Read Boost Transaction by RRN", e);
		}

		request.setAttribute("dto", dt);
		model.addAttribute("pageBean", pageBean);

		return "merchantweb/transaction/Boost_Receipt";

	}

	// Boost Sales Slip - End

	// Grabpay Sales Slip - Start

	@RequestMapping(value = { "/GrabpaySlip/{id}" }, method = RequestMethod.GET)
	public String GrabpaySalesSlip(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		HttpSession session = request.getSession();

		logger.info("GRABPAY RRN = " + id);

		PageBean pageBean = new PageBean("Grabpay Transaction Sales Slip", "merchantweb/transaction/Grabpay_Receipt",
				null);

		String myName = (String) session.getAttribute("userName");
		logger.info("myName" + myName);
		Merchant merchant = merchantService.loadMerchant(myName);

		ForSettlement fsgrabpay = transactionService.loadGrabpayForSettlement(id);

		String tid = fsgrabpay.getTid();

		DataTransferObject dt = new DataTransferObject();
		try {

			logger.info("Initiate ---------- Grabpay Transaction Slip ");

			dt.setMerchantName(merchant.getBusinessName());
			dt.setMerchantAddr1(merchant.getBusinessAddress1());
			dt.setMerchantCity(merchant.getCity());
			dt.setMerchantPostCode(merchant.getPostcode());
			dt.setMerchantContNo(merchant.getBusinessContactNumber());
			dt.setMerchantState(merchant.getState());

			// Date
			String date = null;
			try {
				date = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fsgrabpay.getTimeStamp()));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			fsgrabpay.setDate(date);

			logger.info("Date Format = " + fsgrabpay.getDate());

			// Time
			String time = null;
			try {
				time = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fsgrabpay.getTimeStamp()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			fsgrabpay.setTime(time);
			logger.info("Time Format = " + fsgrabpay.getTime());

			TempletFields tm = new TempletFields();
			TempletFields tm1 = new TempletFields();

			tm = transactionService.getMerchantDetByGrabpayTid(tid);
			if (tm != null) {
				// MID
				logger.info(" MID = " + tm.getMID());
				if (tm.getMID() != null) {
					dt.setMid(tm.getMID());
				}
				// TID
				logger.info(" TID = " + tm.getTID());
				if (tm.getTID() != null) {
					dt.setTid(tm.getTID());
				}

			}
			tm1 = transactionService.getMerchantDetByGrabpayecom(tid);
			if (tm1 != null) {
				// MID
				logger.info(" MID = " + tm1.getMID());
				if (tm1.getMID() != null) {
					dt.setMid(tm1.getMID());
				}
				// TID
				logger.info(" TID = " + tm1.getMID());
				if (tm1.getTID() != null) {
					dt.setTid(tm1.getTID());
				}

			}

			String amt = fsgrabpay.getAmount().toString();
			double amt1 = Double.parseDouble(amt);
			amt1 = amt1 / 100;

			String pattern3 = "#,##0.00";
			DecimalFormat myFormatter3 = new DecimalFormat(pattern3);
			String GrabpayAmount = myFormatter3.format(amt1);
			fsgrabpay.setAmount(GrabpayAmount);
			logger.info(" Amount = " + fsgrabpay.getAmount());
			logger.info(" TxnType = " + fsgrabpay.getTxnType());
			logger.info(" RRN = " + fsgrabpay.getRrn());
			logger.info(" Stan = " + fsgrabpay.getStan());
			logger.info(" AidResponse = " + fsgrabpay.getAidResponse());
			logger.info(" Reference / Invoice ID = " + fsgrabpay.getInvoiceId());

			dt.setDate(fsgrabpay.getDate());
			dt.setTime(fsgrabpay.getTime());
			dt.setTotal(fsgrabpay.getAmount());
			dt.setRefNo(fsgrabpay.getRrn());
			dt.setApprCode(fsgrabpay.getAidResponse());
			dt.setInvoiceNo(fsgrabpay.getInvoiceId());

			// To Check Status

			if (fsgrabpay.getStatus().equals("GPS") || fsgrabpay.getStatus().equals("GPT")) {
				dt.setTxnType("GRABPAY SALE");
			} else if (fsgrabpay.getStatus().equals("GRF") || fsgrabpay.getStatus().equals("GBC")) {
				dt.setTxnType("GRABPAY VOID");
			}

			logger.info("Ready To View ---------- Grabpay Transaction Slip ");

		} catch (Exception e) {

			logger.error("Error To Read Grabpay Transaction by RRN", e);
		}

		request.setAttribute("dto", dt);
		model.addAttribute("pageBean", pageBean);

		return "merchantweb/transaction/Grabpay_Receipt";

	}

	// Grabpay Sales Slip - End

	// FPX Sales Slip - Start

	@RequestMapping(value = { "/FpxSlip/{id}" }, method = RequestMethod.GET)
	public String FpxSalesSlip(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		HttpSession session = request.getSession();

		logger.info("FPX TXNID = " + id);

		PageBean pageBean = new PageBean("Fpx Transaction Sales Slip", "merchantweb/transaction/Fpx_Receipt", null);

		String myName = (String) session.getAttribute("userName");
		logger.info("myName" + myName);
		Merchant merchant = merchantService.loadMerchant(myName);

		FpxTransaction fpx = transactionService.loadFpxTransaction(id);

		DataTransferObject dt = new DataTransferObject();
		try {

			logger.info("Initiate ---------- FPX Transaction Slip ");

			dt.setMerchantName(merchant.getBusinessName());
			dt.setMerchantAddr1(merchant.getBusinessAddress1());
			dt.setMerchantCity(merchant.getCity());
			dt.setMerchantPostCode(merchant.getPostcode());
			dt.setMerchantContNo(merchant.getBusinessContactNumber());
			dt.setMerchantState(merchant.getState());

			// Date
			String date = null;
			try {
				date = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fpx.getTimestamp()));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			fpx.setTxDate(date);

			logger.info("Date Format = " + fpx.getTxDate());

			// Time
			String time = null;
			try {
				time = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fpx.getTimestamp()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			fpx.setTxTime(time);
			logger.info("Time Format = " + fpx.getTxTime());

			logger.info(" MID = " + fpx.getMid());
			logger.info(" TID = " + fpx.getTid());

			// Amount
			Double d = new Double(fpx.getTxnAmount());
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			logger.info(" Amount = " + output);

			// Bank Name
			logger.info(" Bank Name = " + fpx.getBankName());
			logger.info(" Fpx Transaction ID = " + fpx.getBankName());
			logger.info(" Reference/SellerOrderNo = " + fpx.getBankName());

			dt.setDate(fpx.getTxDate());
			dt.setTime(fpx.getTxTime());
			dt.setTotal(output);
			dt.setMid(fpx.getMid());
			dt.setTid(fpx.getTid());
			dt.setBankName(fpx.getBankName());
			dt.setFpxTxnId(fpx.getFpxTxnId());
			dt.setRefNo(fpx.getSellerOrderNo());

			// To Check Status

			if (fpx.getStatus() == null) {

				dt.setTxnType("FPX SALE");
			} else if (fpx.getStatus().equals("R") || fpx.getStatus().equals("PR")) {
				dt.setTxnType("FPX REFUNDED");
			} else {
				dt.setTxnType("FPX SALE");
			}

			logger.info("Ready To View ---------- FPX Transaction Slip ");
		} catch (Exception e) {

			logger.error("Error To Read FPX Transaction by Transaction ID", e);
		}

		request.setAttribute("dto", dt);
		model.addAttribute("pageBean", pageBean);

		return "merchantweb/transaction/Fpx_Receipt";

	}

	// FPX Sales Slip - End

	@RequestMapping(value = { "/UMdetailsAgent/{id}/{merchantid}" }, method = RequestMethod.GET)
	public String displayUMReceiptDetailsByAgent(final Model model, @PathVariable final String id,
			@PathVariable final String merchantid, HttpServletRequest request, HttpServletResponse response,
			Principal principal) {
		HttpSession session = request.getSession();

		logger.info("mrn" + id);

		PageBean pageBean = new PageBean("Transactions Details", "merchantweb/transaction/receipt_UM", null);

		/*
		 * PageBean pageBean = new PageBean("transactions details",
		 * "merchantweb/transaction/receipt_v0.2", Module.TRANSACTION_WEB,
		 * "merchantweb/transaction/sideMenuTransaction");
		 */

		int amount = 0;
		double amt = 0.0;

		String myName = (String) session.getAttribute("userName");
		logger.info("myName" + myName);
		Agent agent = agentService.loadAgent(principal.getName());

		logger.info("display agent web : " + agent.getFirstName());

		long merchantId = Long.parseLong(merchantid);
		Merchant merchant = merchantService.loadMerchantByPk(merchantId);

		/*
		 * String myName = (String) session.getAttribute("userName");
		 * logger.info("myName"+myName); Merchant merchant =
		 * merchantService.loadMerchant(myName);
		 */
		// logger.info("merchant"+merchant.getBusinessName());

		UMEcomTxnRequest req = transactionService.loadUMEzywayTransactionRequest(id);
		UMEcomTxnResponse tr = transactionService.loadUMEzywayTransactionResponse(id);

		logger.info("UMEcomTxnResponse" + tr.getF263_MRN());

		DataTransferObject dt = new DataTransferObject();
		try {

			dt.setMerchantName(merchant.getBusinessName());
			dt.setMerchantAddr1(merchant.getBusinessAddress1());
			dt.setMerchantAddr2(merchant.getBusinessAddress2());
			dt.setMerchantCity(merchant.getCity());
			dt.setMerchantPostCode(merchant.getPostcode());
			dt.setMerchantContNo(merchant.getBusinessContactNumber());
			dt.setMerchantState(merchant.getState());

			logger.info("merchant State::" + merchant.getState() + "::;" + dt.getMerchantState());

			if (tr.getF270_ORN() != null) {
				dt.setRefNo(tr.getF270_ORN());
				logger.info("invoice id:" + dt.getRefNo());
			} else {
				dt.setRefNo("");
			}
			if (tr.getF354_TID() != null) {

				logger.info("tid" + tr.getF354_TID());
				dt.setTid(tr.getF354_TID());
			}
			dt.setMid(tr.getF001_MID());
			/*
			 * if(tr.getF007_TxnAmt()!=null) { amount=Integer.parseInt(tr.getF007_TxnAmt());
			 * amt=amount/100; logger.info("amount after change :"+amount);
			 * 
			 * }
			 */

			if (tr.getF007_TxnAmt() != null) {

				amt = Double.parseDouble(tr.getF007_TxnAmt()) / 100;

				logger.info("amount after change :" + amount);

			}

			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String strTotal = myFormatter.format(amt);
			dt.setAmount(strTotal);
			dt.setTotal(strTotal);

			logger.info("strTotal" + strTotal);

			String sd = tr.getTimeStamp();
			try {

				String rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tr.getTimeStamp()));
				String rt = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tr.getTimeStamp()));
				dt.setDate(rd);
				dt.setTime(rt);
				logger.info("rd and rt" + rd + "::" + rt);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			/*
			 * if(tr.getStatus().equals("S")||tr.getStatus().equals("A")) {
			 * logger.info("status"+tr.getStatus()); dt.setTxnType("EZYWAY SALE");
			 * 
			 * }else{ logger.info("status"+tr.getStatus()); dt.setTxnType("EZYWAY VOID"); }
			 */

			/*
			 * if(tr.getStatus().equals("S")||tr.getStatus().equals("A")) {
			 * logger.info("status"+tr.getStatus());
			 * 
			 * dt.setTxnType(req.getTxnType()+" SALE");
			 * 
			 * 
			 * }else{ if(req.getTxnType().equals("EZYWAY")) { dt.setTxnType("EZYWAY VOID");
			 * }else { dt.setTxnType("EZYMOTO VOID"); }
			 * 
			 * 
			 * }
			 */

			/*
			 * logger.info("status"+tr.getStatus());
			 * 
			 * if(tr.getStatus().equals("S")||tr.getStatus().equals("A")) {
			 * logger.info("status"+tr.getStatus()); if(req.getTxnType() == null) {
			 * dt.setTxnType("EZYWAY SALE"); }else {
			 * dt.setTxnType(req.getTxnType()+" SALE"); }
			 * 
			 * 
			 * } else {
			 * 
			 * logger.info("else status" + req.getTxnType()); if (req.getTxnType() == null)
			 * { dt.setTxnType("EZYWAY VOID"); } else { if
			 * (req.getTxnType().equals("EZYWAY")) {
			 * 
			 * dt.setTxnType("EZYWAY VOID"); } else if (req.getTxnType().equals("EZYMOTO"))
			 * { dt.setTxnType("EZYMOTO VOID"); } else { dt.setTxnType("EZYAUTH VOID"); } }
			 * 
			 * }
			 */

			if (tr.getStatus().equals("S") || tr.getStatus().equals("A")) {
				logger.info("status S or A:" + tr.getStatus());
				logger.info("version 3 or 5:" + tr.getH002_VNO());
				logger.info("Txn Type:" + tr.getTxnType());
				/*
				 * if(req.getTxnType() == null) { dt.setTxnType("EZYWAY SALE"); }else {
				 * dt.setTxnType(req.getTxnType()+" SALE"); }
				 */

				if (tr.getH002_VNO().equals("03")) {

					if (tr.getTxnType().equals("EZYMOTO")) {

						dt.setTxnType("EZYLINK SALE");
					}

					if (tr.getTxnType().equals("EZYWAY")) {

						dt.setTxnType("EZYWAY SALE");
					}

				}

				if (tr.getH002_VNO().equals("05")) {

					if (tr.getTxnType().equals("EZYMOTO")) {

						dt.setTxnType("EZYMOTO SALE");
					}

					if (tr.getTxnType().equals("EZYAUTH")) {

						dt.setTxnType("EZYAUTH SALE");
					}

				}

				if (tr.getH002_VNO().equals("05V")) {

					if (tr.getTxnType().equals("EZYMOTO")) {

						dt.setTxnType("EZYMOTO VCC SALE");
					}

				}

			} else if (tr.getStatus().equals("C")) {

				logger.info("status C:" + tr.getStatus());
				logger.info("version 3 or 5:" + tr.getH002_VNO());
				logger.info("Txn Type:" + tr.getTxnType());

				if (tr.getH002_VNO().equals("03")) {

					if (tr.getTxnType().equals("EZYMOTO")) {

						dt.setTxnType("EZYLINK VOID");
					}

					if (tr.getTxnType().equals("EZYWAY")) {

						dt.setTxnType("EZYWAY VOID");
					}

				}

				if (tr.getH002_VNO().equals("05")) {

					if (tr.getTxnType().equals("EZYMOTO")) {

						dt.setTxnType("EZYMOTO VOID");
					}

					if (tr.getTxnType().equals("EZYAUTH")) {

						dt.setTxnType("EZYAUTH VOID");
					}

				}

				if (tr.getH002_VNO().equals("05V")) {

					if (tr.getTxnType().equals("EZYMOTO")) {

						dt.setTxnType("EZYMOTO VCC VOID");
					}

				}

			} else {

				logger.info("No status :" + tr.getStatus());
				logger.info("version 3 or 5:" + tr.getH002_VNO());
				logger.info("Txn Type:" + tr.getTxnType());

				logger.info("Set default as EZYWAY");

				dt.setTxnType("EZYWAY");

			}

			dt.setMaskedPan(tr.getMaskedPan());
			dt.setCardHolderName(req.getF268_ChName());

			String cardNum = null;
			if (tr.getMaskedPan() == null || tr.getMaskedPan().length() < 4) {
				cardNum = tr.getMaskedPan();
			} else {
				cardNum = tr.getMaskedPan().substring(tr.getMaskedPan().length() - 4);
			}
			String finalNum = String.format("XXXX %s", cardNum);
			dt.setCardNo(finalNum);
			logger.info("finalNum" + finalNum);
			dt.setRrn(tr.getF023_RRN());
			dt.setAid(tr.getF011_AuthIDResp());
			dt.setApprCode(tr.getF011_AuthIDResp());
			dt.setCardType(tr.getF350_CrdTyp());

			logger.info("ApprCod" + dt.getApprCode());

		} catch (Exception e) {
			System.out.println("Record not Found..!");
			request.setAttribute("errMsg", "Data not Available for this Transaction ID...!");
		}
		request.setAttribute("dto", dt);
		model.addAttribute("pageBean", pageBean);
		// return "merchantweb/transaction/CardReceiptNew";
		// return "merchantweb/transaction/receipt";
		return "merchantweb/transaction/receipt_UM";
	}

	@RequestMapping(value = { "/UMenquiryTransaction/{currPage}" }, method = RequestMethod.GET)
	public String UMTransactionEnquiry(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("Transaction Enquiry list");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/umTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		logger.info(" All Transaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMEzywayTransactionEnq(paginationBean, null, null,
				currentMerchant.getMid().getUmEzywayMid(), "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		logger.info(" enquiry");
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchUMEnquiry" }, method = RequestMethod.GET)
	public String SearchUMTransactionEnquiry(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("Transaction Enquiry list");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/umTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMEzywayTransactionEnq(paginationBean, date, date1,
				currentMerchant.getMid().getUmEzywayMid(), "ALL");

		model.addAttribute("paginationBean", paginationBean);
//		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = "/umEzywayEnqExport", method = RequestMethod.GET)
	public ModelAndView getExportUMEzywayEnq(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMEzywayTransactionEnq(paginationBean, dat, dat1,
				currentMerchant.getMid().getUmEzywayMid(), "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnUMEnqExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnUMEnqPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umMotoExport", method = RequestMethod.GET)
	public ModelAndView getExportUMMoto(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("txnType") String txnType,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("UM_EZYMOTO Export by Merchant");
		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getUmMotoMid());

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromdate;
		String dat1 = todate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		// String txnStatus="A";
//			transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1,txnStatus,currentMerchant.getMid().getUmEzywayMid(),"ALL");
		transactionService.exportUMMotoTransaction(paginationBean, dat, dat1, currentMerchant.getMid().getUmMotoMid(),currentMerchant.getMid().getFiuuMid(),
				txnType, currentMerchant);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListUMMerchantAllExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListMotoUMNewPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umEzyrecExport", method = RequestMethod.GET)
	public ModelAndView getumEzyrecExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("txnType") String txnType,

//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("umEzyrecExport  by Merchant");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getUmEzyrecMid());

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMEzyrecTransaction(paginationBean, dat, dat1,
				currentMerchant.getMid().getUmEzyrecMid(), txnType, currentMerchant);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListUMMerchantAllExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListMotoUMPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umLinkExport", method = RequestMethod.GET)
	public ModelAndView umLinkExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("UM_EZYLINK Export by Merchant");
		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getUmMotoMid());
		// logger.info("TXN Type :" + txnType);
		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromdate;
		String dat1 = todate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		// String txnStatus="A";
//			transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1,txnStatus,currentMerchant.getMid().getUmEzywayMid(),"ALL");
		transactionService.exportUMLinkTransaction(paginationBean, dat, dat1, currentMerchant.getMid().getUmMotoMid(),
				"CARD", currentMerchant);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListUMMerchantAllExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListMotoUMNewPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umVccExport", method = RequestMethod.GET)
	public ModelAndView umVccExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("UM_EZYMOTO VCC Export by Merchant");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getUmMotoMid());

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		// String txnStatus="A";
//			transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1,txnStatus,currentMerchant.getMid().getUmEzywayMid(),"ALL");
		transactionService.exportUMVccTransaction(paginationBean, dat, dat1, currentMerchant.getMid().getUmMotoMid(),
				"ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListUMExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListUMPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umEzyauthExport", method = RequestMethod.GET)
	public ModelAndView getExportUMEzyauth(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getUmMotoMid());

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMEzyauthTransaction(paginationBean, dat, dat1, currentMerchant, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnUMAuthExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListMotoUMNewPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umEzywayExport", method = RequestMethod.GET)
	public ModelAndView getExportUMEzyway(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("UM ezyway export :");

		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		// logger.info("TXN type" + txnType);
		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromdate;
		String dat1 = todate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		// String txnStatus="A";
//			transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1,txnStatus,currentMerchant.getMid().getUmEzywayMid(),"ALL");
		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
			String ezywaymid = "000000000021591";
			transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1, ezywaymid, "CARD", currentMerchant);
		} else {
			transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1,
					currentMerchant.getMid().getUmEzywayMid(), "CARD", currentMerchant);
		}
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			// return new ModelAndView("txnListUMExcel", "umTxnList", list1);
			return new ModelAndView("txnListUMEzywayExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListUMPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = { "/details/{id}" }, method = RequestMethod.GET)
	public String displayTransactionDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// Merchant merchant = merchantService.loadMerchant(principal.getName());
		Merchant merchant = merchantService.loadMerchant(myName);

		logger.info("Mid" + ":" + merchant.getMid().getMid() + "MerchantName" + ":" + merchant.getBusinessName() + ":"
				+ "Merchant Receipt logged by" + ":" + principal.getName() + ":");
		ForSettlement settle = transactionService.getForSettlement(id);
		if (settle.getTxnType() == null) {
			settle.setTxnType("CARD");
		}
		logger.info(" Txn Type : " + settle.getTxnType());
		if (settle.getTxnType().equals("CASH")) {

			/*
			 * PageBean pageBean = new PageBean("Transactions Details",
			 * "merchantweb/transaction/cashreceipt", null);
			 */

			PageBean pageBean = new PageBean("Transactions Details", "merchantweb/transaction/CashReceiptNew", null);

			logger.info("Transaction Id :" + id);
			DataTransferObject dt = new DataTransferObject();

			String txn = settle.getStatus();
			if (txn.equals("CT")) {
				dt.setTxnType("CASH SALE");
			} else {
				dt.setTxnType("CASH CANCELLED");
			}
			try {
				dt.setMerchantName(merchant.getBusinessName());
				dt.setMerchantAddr1(merchant.getBusinessAddress1());
				dt.setMerchantAddr2(merchant.getBusinessAddress2());
				dt.setMerchantCity(merchant.getCity());
				dt.setMerchantPostCode(merchant.getPostcode());
				dt.setMerchantContNo(merchant.getBusinessContactNumber());
				dt.setMerchantState(merchant.getState());
				if ((settle.getLatitude() != null)) {
					dt.setLatitude(settle.getLatitude());
					logger.info("Latitude id:" + dt.getLatitude());
				}
				if ((settle.getLongitude() != null)) {
					dt.setLongitude(settle.getLongitude());
					logger.info("Longitude id:" + dt.getLatitude());
				}

				// dt.setMapUrl(UrlSigner.GenerateMapImage(dt.getLatitude(),
				// dt.getLongitude()));
				// logger.info("Generated Map Image URL: "+dt.getMapUrl());
				// new changes for receipt
				if (settle.getInvoiceId() != null) {
					dt.setRefNo(settle.getInvoiceId());
					logger.info("invoice id:" + dt.getRefNo());
				} else {
					dt.setRefNo("");
				}
				if (settle.getTid() != null) {
					dt.setTid(settle.getTid());
				}
				dt.setMid(settle.getMid());
				dt.setAmount(settle.getAmount());
				dt.setAdditionAmount(settle.getAdditionAmount());
				String st = settle.getTime();
				String sd = settle.getTimeStamp();
				String pinEntry = settle.getPinEntry();
				dt.setPinEntry(pinEntry);
				try {

					/*
					 * String rd = new SimpleDateFormat("dd MMMM yyy") .format(new
					 * SimpleDateFormat("yyyy-MM-dd") .parse(sd)); String rt = new
					 * SimpleDateFormat("HH:mm") .format(new SimpleDateFormat("HHmmss").parse(st));
					 */

					String rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(settle.getTimeStamp()));
					String rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(settle.getTimeStamp()));
					dt.setDate(rd);
					dt.setTime(rt);
					// dt.setDate(rd.toUpperCase());

				} catch (ParseException e) {
					e.printStackTrace();
				}
				double amount = 0;
				logger.info("Amount For receipt :" + settle.getAmount());
				if (settle.getAmount() != null) {
					amount = Double.parseDouble(settle.getAmount()) / 100;
				}
				double tips = 0;
				double total = amount;
				if (settle.getAdditionAmount() != null) {
					tips = Double.parseDouble(settle.getAdditionAmount()) / 100;
					amount = amount - tips;
					total = amount + tips;

				}
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				String output1 = myFormatter.format(tips);
				String output2 = myFormatter.format(total);
				// System.out.println(" Amount :"+output);
				// forSettlement.setAmount(output);
				dt.setAdditionAmount(output1);
				dt.setAmount(output);
				dt.setTotal(output2);
				dt.setTraceNo(settle.getStan());
				if (settle.getInvoiceId() != null) {
					dt.setInvoiceNo(settle.getInvoiceId());
				} else {
					dt.setInvoiceNo("");
				}

			} catch (Exception e) {
				System.out.println("Record not Found..!");
				request.setAttribute("errMsg", "Data not Available for this Transaction ID...!");
			}

			request.setAttribute("dto", dt);
			model.addAttribute("pageBean", pageBean);
			// return "merchantweb/transaction/CashReceiptNew";

			// return "merchantweb/transaction/cashreceipt";
			return "merchantweb/transaction/CashReceipt_v0.2";

		} else {

			PageBean pageBean = new PageBean("Transactions Details", "merchantweb/transaction/receipt", null);
			// logger.info("Transaction Id3333333333 :" + id);
			TransactionRequest trRequest = transactionService.loadTransactionRequest(id);
			TransactionResponse trResponse = transactionService.loadTransactionResponse(id);

			Receipt a = transactionService.getReceiptSignature(id);

			DataTransferObject dt = new DataTransferObject();

			if (a != null) {
				if (a.getSignature() != null) {
					String signdata = new String(a.getSignature());

					dt.setSign(signdata);
				}
			}

			String txn = settle.getStatus();

			if (txn.equals("S") || txn.equals("A")) {
				logger.info("txntype: " + settle.getTxnType());
				if (settle.getTxnType().equals("MOTO")) {
					dt.setTxnType("EZYMOTO SALE");
				} else if (settle.getTxnType().equals("EZYWAY")) {
					dt.setTxnType("EZYWAY SALE");
				} else if (settle.getTxnType().equals("EZYREC") || settle.getTxnType().equals("RECURRING")) {
					dt.setTxnType("EZYREC SALE");
				} else if (settle.getTxnType().equals("EZYPASS")) {
					dt.setTxnType("EZYPASS SALE");
				} else {
					// dt.setTxnType("UMOBILE SALE");
					dt.setTxnType("SALE");
				}

			} else {

				if (settle.getTxnType().equals("MOTO")) {
					dt.setTxnType("EZYMOTO VOID");
				} else if (settle.getTxnType().equals("EZYWAY")) {
					dt.setTxnType("EZYWAY VOID");
				} else if (settle.getTxnType().equals("EZYREC") || settle.getTxnType().equals("RECURRING")) {
					dt.setTxnType("EZYREC VOID");
				} else if (settle.getTxnType().equals("EZYPASS")) {
					dt.setTxnType("EZYPASS VOID");
				} else {
					// dt.setTxnType("UMOBILE VOID");
					dt.setTxnType("VOID");
				}

			}
			try {
				dt.setMerchantName(merchant.getBusinessName());
				dt.setMerchantAddr1(merchant.getBusinessAddress1());
				dt.setMerchantAddr2(merchant.getBusinessAddress2());
				dt.setMerchantCity(merchant.getCity());
				dt.setMerchantPostCode(merchant.getPostcode());
				dt.setMerchantContNo(merchant.getBusinessContactNumber());
				dt.setMerchantState(merchant.getState());

				if ((trRequest.getLatitude() != null)) {
					dt.setLatitude(trRequest.getLatitude());
					logger.info("Latitude id:" + dt.getLatitude());

				}
				if ((trRequest.getLongitude() != null)) {
					dt.setLongitude(trRequest.getLongitude());
					logger.info("Longitude id:" + dt.getLatitude());

				}

				// dt.setMapUrl(UrlSigner.GenerateMapImage(dt.getLatitude(),
				// dt.getLongitude()));
				// logger.info("Generated Map Image URL: "+dt.getMapUrl());
				// new changes for receipt
				dt.setBatchNo(settle.getBatchNo());
				logger.info("get batchno:" + dt.getBatchNo());

				if (settle.getInvoiceId() != null) {

					dt.setRefNo(settle.getInvoiceId());
					logger.info("invoice id:" + dt.getRefNo());
				} else {
					dt.setRefNo("");
				}
				if (trRequest.getTid() != null) {
					dt.setTid(trRequest.getTid());
				}
				dt.setMid(trRequest.getMid());
				dt.setAmount(trRequest.getAmount());
				dt.setAdditionAmount(trRequest.getAdditionalAmount());
				// String rrn=HexatoAscii.hexaToAscii(trResponse.getRrn(),
				// true);
				// dt.setRrn(rrn);
				dt.setAid(trRequest.getAid());
				dt.setStan(trRequest.getStan());
				dt.setMaskedPan(trRequest.getMaskedPan());
				if (trRequest.getAid() != null && trRequest.getCardHolderName() != null) {
					// dt.setCardType(CardType.getCardType(trRequest.getAid()));
					dt.setCardHolderName(trRequest.getCardHolderName());
				}
				/*
				 * dt.setDate(trResponse.getLocalDate()); dt.setTime(trResponse.getLocalTime());
				 */
				// String
				// resposecode=HexatoAscii.hexaToAscii(trResponse.getAidResponse(),
				// true);
				// dt.setResponseCode(resposecode);
				String st = trResponse.getLocalTime();
				// String sd=trResponse.getLocalDate()+new
				// SimpleDateFormat("y").format(new java.util.Date());
				String sd = trResponse.getTimeStamp();
				try {

					/*
					 * String rd = new SimpleDateFormat("dd MMMM yyyy") .format(new
					 * SimpleDateFormat("yyyy-MM-dd") .parse(sd)); String rt = new
					 * SimpleDateFormat("HH:mm") .format(new SimpleDateFormat("HHmmss").parse(st));
					 */
					String rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(settle.getTimeStamp()));
					String rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(settle.getTimeStamp()));
					// dt.setDate(rd.toUpperCase());
					dt.setDate(rd);
					dt.setTime(rt);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String cardNum = null;
				if (trRequest.getMaskedPan() == null || trRequest.getMaskedPan().length() < 4) {
					cardNum = trRequest.getMaskedPan();
				} else {
					cardNum = trRequest.getMaskedPan().substring(trRequest.getMaskedPan().length() - 4);
				}
				String finalNum = String.format("XXXX %s", cardNum);
				dt.setCardNo(finalNum);
				double amount = 0;
				logger.info("Amount For receipt :" + trRequest.getAmount());
				if (trRequest.getAmount() != null) {
					amount = Double.parseDouble(trRequest.getAmount()) / 100;
				}
				double tips = 0;
				double total = amount;
				if (trRequest.getAdditionalAmount() != null) {
					tips = Double.parseDouble(trRequest.getAdditionalAmount()) / 100;
					amount = amount - tips;
					total = amount + tips;

				}
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				String output1 = myFormatter.format(tips);
				String output2 = myFormatter.format(total);
				// System.out.println(" Amount :"+output);
				// forSettlement.setAmount(output);
				dt.setAdditionAmount(output1);
				dt.setAmount(output);
				dt.setTotal(output2);
				dt.setCardHolderName(trRequest.getCardHolderName());

				dt.setAid(trRequest.getAid());
				/*
				 * if (trRequest.getAid() != null) {
				 * dt.setCardType(CardType.getCardType(trRequest.getAid())); } else {
				 * dt.setCardType(""); }
				 */

				if (trRequest.getCardScheme() != null && trRequest.getCardType() != null) {
					dt.setCardType(trRequest.getCardScheme() + " " + trRequest.getCardType());
					logger.info("Card Type from Txn Request Card Scheme and Card Type : " + dt.getCardType());
				} else if (trRequest.getApplicationLabel() != null) {

					dt.setCardType(trRequest.getApplicationLabel());
					logger.info("Card Type from Txn Request : " + dt.getCardType());
				} else if (trRequest.getApplicationLabel() == null && trRequest.getAid() != null) {
					dt.setCardType(CardType.getCardType(trRequest.getAid()));
					logger.info("Card Type from AID : " + dt.getCardType());
				} else {
					dt.setCardType(" ");
				}

				dt.setBatchNo(trRequest.getBatchNo());
				dt.setTraceNo(trRequest.getStan());
				dt.setRrn(hexaToAscii(trResponse.getRrn(), true));
				dt.setTC(trRequest.getStan() + trRequest.getTid());
				dt.setAid(trResponse.getAidResponse());
				if (settle.getBatchNo() != null) {
					dt.setBatchNo(settle.getBatchNo());
				} else {
					dt.setBatchNo("");
				}
				if (settle.getInvoiceId() != null) {
					dt.setInvoiceNo(settle.getInvoiceId());
				} else {
					dt.setInvoiceNo("");
				}

				String resposecode = hexaToAscii(trResponse.getAidResponse(), true);

				dt.setApprCode(resposecode);
			} catch (Exception e) {
				System.out.println("Record not Found..!");
				request.setAttribute("errMsg", "Data not Available for this Transaction ID...!");
			}
			/*
			 * Merchant merchant = merchantService.loadMerchantByPk(id); Transaction
			 * transaction = transactionService.loadTransactionByPk(id); MobileUser
			 * mobileUser = mobileUserService.loadMobileUserByPk(id);
			 * model.addAttribute("transaction", transaction);
			 * model.addAttribute("mobileUser", mobileUser); model.addAttribute("merchant",
			 * merchant);
			 */
			// model.addAttribute("dto", dt);
			request.setAttribute("dto", dt);
			model.addAttribute("pageBean", pageBean);
			// return "merchantweb/transaction/CardReceiptNew";
			// return "merchantweb/transaction/receipt";
			return "merchantweb/transaction/receiptUM_0.1";
		}
		// return "redirect:/transactionweb/list/1";
	}

	public static String hexaToAscii(String s, boolean toString) {

		String retString = "";
		String tempString = "";
		int offset = 0;
		if (toString) {
			for (int i = 0; i < s.length() / 2; i++) {

				tempString = s.substring(offset, offset + 2);
				retString += tempString.equalsIgnoreCase("1c") ? "[1C]" : decodeHexString(tempString);
				offset += 2;
			} // end for
		} else {

			for (int i = 0; i < s.length(); i++) {

				tempString = s.substring(offset, offset + 1);
				retString += encodeHexString(tempString);
				offset += 1;
			} // end for
		}
		return retString;
	} // end hexaToAscii

	public static String decodeHexString(String hexText) {

		String decodedText = null;
		String chunk = null;

		if (hexText != null && hexText.length() > 0) {
			int numBytes = hexText.length() / 2;

			byte[] rawToByte = new byte[numBytes];
			int offset = 0;
			for (int i = 0; i < numBytes; i++) {
				chunk = hexText.substring(offset, offset + 2);
				offset += 2;
				rawToByte[i] = (byte) (Integer.parseInt(chunk, 16) & 0x000000FF);
			}
			// System.out.println(rawToByte.toString());
			decodedText = new String(rawToByte);
		}
		return decodedText;
	}

	public static String encodeHexString(String sourceText) {
		byte[] rawData = sourceText.getBytes();
		StringBuffer hexText = new StringBuffer();
		String initialHex = null;
		int initHexLength = 0;

		for (int i = 0; i < rawData.length; i++) {
			// System.out.println("raw "+rawData[i]);
			int positiveValue = rawData[i] & 0x000000FF;
			initialHex = Integer.toHexString(positiveValue);
			initHexLength = initialHex.length();
			while (initHexLength++ < 2) {
				hexText.append("0");
			}
			hexText.append(initialHex);
		}
		return hexText.toString().toUpperCase();
	}

	@RequestMapping(value = "/boostExport", method = RequestMethod.POST)
	public ModelAndView getExportBoost(final Model model, final java.security.Principal principal,

			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status,
			@RequestParam("devId") String devId, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("the merchant obj based on currently logged in user is: " + currentMerchant);

		String dat = null;
		String dat1 = null;

		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": &
		// :"+tid+": & :"+status);
		if (!(fromDate == null || fromDate.equals("")) && !(toDate == null || toDate.equals(""))) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

			logger.info("check from date:" + fromDate);
			logger.info("check from date:" + dat);

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

			System.out.println("dat1:" + dat1);
		}
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");
		/*
		 * transactionService.searchForSettlementBoost(dat, dat1, tid, status,
		 * paginationBean, currentMerchant);
		 */
		/*
		 * transactionService.searchBoostForSettlement(dat, dat1, tid, status,
		 * paginationBean, currentMerchant);
		 */
		transactionService.searchForSettlementBoost(dat, dat1, tid, status, paginationBean, currentMerchant);
		/*
		 * transactionService.searchForSettlement(dat, dat1, paginationBean,
		 * currentMerchant);
		 */

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) {
		 * 
		 * logger.info("tid details:" + forSettlement.getTid().toString());
		 * TerminalDetails terminalDetails =
		 * transactionService.getTerminalDetailsByTid(forSettlement.getTid().toString())
		 * ; if(terminalDetails != null) { //
		 * logger.info("terminal details contact Name:" + //
		 * terminalDetails.getContactName()); if(terminalDetails.getContactName() !=
		 * null) { forSettlement.setMerchantName(terminalDetails
		 * .getContactName().toUpperCase()); } else { forSettlement.setMerchantName("");
		 * } }
		 * 
		 * logger.info("test data "+ paginationBean.getItemList());
		 */

		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) {
		 * 
		 * if (forSettlement.getAmount() != null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()) / 100; //
		 * forSettlement.setAmount(amount + "0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern);
		 * 
		 * String output = myFormatter.format(amount); // System.out.println(" Amount :"
		 * + output); forSettlement.setAmount(output); } if
		 * (forSettlement.getStatus().equals("S")) { forSettlement.setStatus("SETTLED");
		 * 
		 * } if (forSettlement.getStatus().equals("P")) {
		 * forSettlement.setStatus("PENDING"); } if
		 * (forSettlement.getStatus().equals("A")) {
		 * forSettlement.setStatus("NOT SETTLED"); } if
		 * (forSettlement.getStatus().equals("C")) {
		 * forSettlement.setStatus("CANCELLED"); } if
		 * (forSettlement.getStatus().equals("R")) {
		 * forSettlement.setStatus("REVERSAL"); } if (forSettlement.getDate() != null &&
		 * forSettlement.getTime() != null) { try {
		 * 
		 * String sd = forSettlement.getTimeStamp(); String rd = new
		 * SimpleDateFormat("dd-MM-yyyy") .format(new SimpleDateFormat("yyyy-MM-dd")
		 * .parse(sd)); String rt = new SimpleDateFormat("HH:mm:ss") .format(new
		 * SimpleDateFormat("HHmmss") .parse(forSettlement.getTime()));
		 * forSettlement.setDate(rd); forSettlement.setTime(rt); } catch (ParseException
		 * e) { }
		 * 
		 * } }
		 */
		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			tidSet.add(mtid.toString());
		}
		/*
		 * if (tid.equalsIgnoreCase(mtid)) { midTransationList.add(t); }
		 */
		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}

		List<ForSettlement> list1 = paginationBean.getItemList();

		// System.out.println("display list:" + list1);
		// System.out.println("Export Type:" + export);

		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListExcel1", "txnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = "/motoExport", method = RequestMethod.POST)
	public ModelAndView getExportMoto(final Model model, final java.security.Principal principal,

			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status,
			@RequestParam("devId") String devId, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getMid());

		String dat = null;
		String dat1 = null;

		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": &
		// :"+tid+": & :"+status);
		if (!(fromDate == null || fromDate.equals("")) && !(toDate == null || toDate.equals(""))) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			/*
			 * dat = fromDate; String sub[] = dat.split("/"); dat = sub[0] + sub[1];
			 */
			logger.info("check from date:" + fromDate);
			logger.info("check from date:" + dat);

			// logger.info("DD sub : " + dat);
			// System.out.println("DD sub : " + dat);
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			/*
			 * dat1 = toDate; String sub1[] = dat1.split("/"); dat1 = sub1[0] + sub1[1];
			 */
			System.out.println("dat1:" + dat1);
		}
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");
		transactionService.searchUMForSettlementMoto(dat, dat1, tid, status, paginationBean, currentMerchant);
		/*
		 * transactionService.searchForSettlement(dat, dat1, paginationBean,
		 * currentMerchant);
		 */
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) { //
		 * logger.info("tid details:" + forSettlement.getTid().toString());
		 * TerminalDetails terminalDetails = transactionService
		 * .getTerminalDetailsByTid(forSettlement.getTid().toString()); if
		 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
		 * // terminalDetails.getContactName()); if (terminalDetails.getContactName() !=
		 * null) { forSettlement.setMerchantName(terminalDetails
		 * .getContactName().toUpperCase()); } else { forSettlement.setMerchantName("");
		 * } }
		 * 
		 * logger.info("test data "+ paginationBean.getItemList()); for(ForSettlement
		 * forSettlement:paginationBean.getItemList()){
		 * 
		 * if (forSettlement.getAmount() != null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()) / 100; //
		 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
		 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
		 * forSettlement.setAmount(output); } if (forSettlement.getStatus().equals("S"))
		 * { forSettlement.setStatus("SETTLED");
		 * 
		 * } if (forSettlement.getStatus().equals("P")) {
		 * forSettlement.setStatus("PENDING"); } if
		 * (forSettlement.getStatus().equals("A")) {
		 * forSettlement.setStatus("NOT SETTLED"); } if
		 * (forSettlement.getStatus().equals("C")) {
		 * forSettlement.setStatus("CANCELLED"); } if
		 * (forSettlement.getStatus().equals("R")) {
		 * forSettlement.setStatus("REVERSAL"); } if (forSettlement.getDate() != null &&
		 * forSettlement.getTime() != null) { try { // String
		 * sd=forSettlement.getDate()+new // SimpleDateFormat("y").format(new
		 * java.util.Date()); String sd = forSettlement.getTimeStamp(); String rd = new
		 * SimpleDateFormat("dd-MM-yyyy") .format(new SimpleDateFormat("yyyy-MM-dd")
		 * .parse(sd)); String rt = new SimpleDateFormat("HH:mm:ss") .format(new
		 * SimpleDateFormat("HHmmss") .parse(forSettlement.getTime()));
		 * forSettlement.setDate(rd); forSettlement.setTime(rt); } catch (ParseException
		 * e) { }
		 * 
		 * } }
		 */

		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			// tidSet.add(mtid.toString());
		}
		/*
		 * if (tid.equalsIgnoreCase(mtid)) { midTransationList.add(t); }
		 */
		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}

		List<ForSettlement> list1 = paginationBean.getItemList();

		// System.out.println("display list:" + list1);
		// System.out.println("Export Type:" + export);

		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListExcel1", "txnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = "/ezywayExport", method = RequestMethod.POST)
	public ModelAndView getExportEzyway(final Model model, final java.security.Principal principal,

			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status,
			@RequestParam("devId") String devId, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());

		String dat = null;
		String dat1 = null;

		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": &
		// :"+tid+": & :"+status);
		if (!(fromDate == null || fromDate.equals("")) && !(toDate == null || toDate.equals(""))) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			/*
			 * dat = fromDate; String sub[] = dat.split("/"); dat = sub[0] + sub[1];
			 */
			logger.info("check from date:" + fromDate);
			logger.info("check from date:" + dat);

			// logger.info("DD sub : " + dat);
			// System.out.println("DD sub : " + dat);
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			/*
			 * dat1 = toDate; String sub1[] = dat1.split("/"); dat1 = sub1[0] + sub1[1];
			 */
			System.out.println("dat1:" + dat1);
		}
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");

//Showing Error
//		transactionService.searchForSettlementEzyWay(dat, dat1, tid, status, paginationBean, currentMerchant);
		/*
		 * transactionService.searchForSettlement(dat, dat1, paginationBean,
		 * currentMerchant);
		 */
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) { //
		 * logger.info("tid details:" + forSettlement.getTid().toString());
		 * TerminalDetails terminalDetails = transactionService
		 * .getTerminalDetailsByTid(forSettlement.getTid().toString()); if
		 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
		 * // terminalDetails.getContactName()); if (terminalDetails.getContactName() !=
		 * null) { forSettlement.setMerchantName(terminalDetails
		 * .getContactName().toUpperCase()); } else { forSettlement.setMerchantName("");
		 * } }
		 * 
		 * logger.info("test data "+ paginationBean.getItemList()); for(ForSettlement
		 * forSettlement:paginationBean.getItemList()){
		 * 
		 * if (forSettlement.getAmount() != null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()) / 100; //
		 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
		 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
		 * forSettlement.setAmount(output); } if (forSettlement.getStatus().equals("S"))
		 * { forSettlement.setStatus("SETTLED");
		 * 
		 * } if (forSettlement.getStatus().equals("P")) {
		 * forSettlement.setStatus("PENDING"); } if
		 * (forSettlement.getStatus().equals("A")) {
		 * forSettlement.setStatus("NOT SETTLED"); } if
		 * (forSettlement.getStatus().equals("C")) {
		 * forSettlement.setStatus("CANCELLED"); } if
		 * (forSettlement.getStatus().equals("R")) {
		 * forSettlement.setStatus("REVERSAL"); } if (forSettlement.getDate() != null &&
		 * forSettlement.getTime() != null) { try { // String
		 * sd=forSettlement.getDate()+new // SimpleDateFormat("y").format(new
		 * java.util.Date()); String sd = forSettlement.getTimeStamp(); String rd = new
		 * SimpleDateFormat("dd-MM-yyyy") .format(new SimpleDateFormat("yyyy-MM-dd")
		 * .parse(sd)); String rt = new SimpleDateFormat("HH:mm:ss") .format(new
		 * SimpleDateFormat("HHmmss") .parse(forSettlement.getTime()));
		 * forSettlement.setDate(rd); forSettlement.setTime(rt); } catch (ParseException
		 * e) { }
		 * 
		 * } }
		 */

		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getEzywayMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			// tidSet.add(mtid.toString());
		}
		/*
		 * if (tid.equalsIgnoreCase(mtid)) { midTransationList.add(t); }
		 */
		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}

		List<ForSettlement> list1 = paginationBean.getItemList();

		// System.out.println("display list:" + list1);
		// System.out.println("Export Type:" + export);

		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListExcel1", "txnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = "/ezypassExport", method = RequestMethod.POST)
	public ModelAndView getExportEzypass(final Model model, final java.security.Principal principal,

			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status,
			@RequestParam("devId") String devId, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzypassMid());

		String dat = null;
		String dat1 = null;

		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": &
		// :"+tid+": & :"+status);
		if (!(fromDate == null || fromDate.equals("")) && !(toDate == null || toDate.equals(""))) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			/*
			 * dat = fromDate; String sub[] = dat.split("/"); dat = sub[0] + sub[1];
			 */
			logger.info("check from date:" + fromDate);
			logger.info("check from date:" + dat);

			// logger.info("DD sub : " + dat);
			// System.out.println("DD sub : " + dat);
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			/*
			 * dat1 = toDate; String sub1[] = dat1.split("/"); dat1 = sub1[0] + sub1[1];
			 */
			System.out.println("dat1:" + dat1);
		}
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");
		transactionService.searchForSettlementEzyPass(dat, dat1, tid, status, paginationBean, currentMerchant);
		/*
		 * transactionService.searchForSettlement(dat, dat1, paginationBean,
		 * currentMerchant);
		 */
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) { //
		 * logger.info("tid details:" + forSettlement.getTid().toString());
		 * TerminalDetails terminalDetails = transactionService
		 * .getTerminalDetailsByTid(forSettlement.getTid().toString()); if
		 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
		 * // terminalDetails.getContactName()); if (terminalDetails.getContactName() !=
		 * null) { forSettlement.setMerchantName(terminalDetails
		 * .getContactName().toUpperCase()); } else { forSettlement.setMerchantName("");
		 * } }
		 * 
		 * logger.info("test data "+ paginationBean.getItemList()); for(ForSettlement
		 * forSettlement:paginationBean.getItemList()){
		 * 
		 * if (forSettlement.getAmount() != null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()) / 100; //
		 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
		 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
		 * forSettlement.setAmount(output); } if (forSettlement.getStatus().equals("S"))
		 * { forSettlement.setStatus("SETTLED");
		 * 
		 * } if (forSettlement.getStatus().equals("P")) {
		 * forSettlement.setStatus("PENDING"); } if
		 * (forSettlement.getStatus().equals("A")) {
		 * forSettlement.setStatus("NOT SETTLED"); } if
		 * (forSettlement.getStatus().equals("C")) {
		 * forSettlement.setStatus("CANCELLED"); } if
		 * (forSettlement.getStatus().equals("R")) {
		 * forSettlement.setStatus("REVERSAL"); } if (forSettlement.getDate() != null &&
		 * forSettlement.getTime() != null) { try { // String
		 * sd=forSettlement.getDate()+new // SimpleDateFormat("y").format(new
		 * java.util.Date()); String sd = forSettlement.getTimeStamp(); String rd = new
		 * SimpleDateFormat("dd-MM-yyyy") .format(new SimpleDateFormat("yyyy-MM-dd")
		 * .parse(sd)); String rt = new SimpleDateFormat("HH:mm:ss") .format(new
		 * SimpleDateFormat("HHmmss") .parse(forSettlement.getTime()));
		 * forSettlement.setDate(rd); forSettlement.setTime(rt); } catch (ParseException
		 * e) { }
		 * 
		 * } }
		 */

		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getEzypassMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			// tidSet.add(mtid.toString());
		}
		/*
		 * if (tid.equalsIgnoreCase(mtid)) { midTransationList.add(t); }
		 */
		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}

		List<ForSettlement> list1 = paginationBean.getItemList();

		// System.out.println("display list:" + list1);
		// System.out.println("Export Type:" + export);

		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListExcel1", "txnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = "/ezyrecExport", method = RequestMethod.POST)
	public ModelAndView getExportEzyrec(final Model model, final java.security.Principal principal,

			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status,
			@RequestParam("devId") String devId, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getMid());

		String dat = null;
		String dat1 = null;

		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": &
		// :"+tid+": & :"+status);
		if (!(fromDate == null || fromDate.equals("")) && !(toDate == null || toDate.equals(""))) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			/*
			 * dat = fromDate; String sub[] = dat.split("/"); dat = sub[0] + sub[1];
			 */
			logger.info("check from date:" + fromDate);
			logger.info("check from date:" + dat);

			// logger.info("DD sub : " + dat);
			// System.out.println("DD sub : " + dat);
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			/*
			 * dat1 = toDate; String sub1[] = dat1.split("/"); dat1 = sub1[0] + sub1[1];
			 */
			System.out.println("dat1:" + dat1);
		}
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");

//Showing Error
//		transactionService.searchForSettlementEzyRec(dat, dat1, tid, status, paginationBean, currentMerchant);
		/*
		 * transactionService.searchForSettlement(dat, dat1, paginationBean,
		 * currentMerchant);
		 */
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) { //
		 * logger.info("tid details:" + forSettlement.getTid().toString());
		 * TerminalDetails terminalDetails = transactionService
		 * .getTerminalDetailsByTid(forSettlement.getTid().toString()); if
		 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
		 * // terminalDetails.getContactName()); if (terminalDetails.getContactName() !=
		 * null) { forSettlement.setMerchantName(terminalDetails
		 * .getContactName().toUpperCase()); } else { forSettlement.setMerchantName("");
		 * } }
		 * 
		 * logger.info("test data "+ paginationBean.getItemList()); for(ForSettlement
		 * forSettlement:paginationBean.getItemList()){
		 * 
		 * if (forSettlement.getAmount() != null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()) / 100; //
		 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
		 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
		 * forSettlement.setAmount(output); } if (forSettlement.getStatus().equals("S"))
		 * { forSettlement.setStatus("SETTLED");
		 * 
		 * } if (forSettlement.getStatus().equals("P")) {
		 * forSettlement.setStatus("PENDING"); } if
		 * (forSettlement.getStatus().equals("A")) {
		 * forSettlement.setStatus("NOT SETTLED"); } if
		 * (forSettlement.getStatus().equals("C")) {
		 * forSettlement.setStatus("CANCELLED"); } if
		 * (forSettlement.getStatus().equals("R")) {
		 * forSettlement.setStatus("REVERSAL"); } if (forSettlement.getDate() != null &&
		 * forSettlement.getTime() != null) { try { // String
		 * sd=forSettlement.getDate()+new // SimpleDateFormat("y").format(new
		 * java.util.Date()); String sd = forSettlement.getTimeStamp(); String rd = new
		 * SimpleDateFormat("dd-MM-yyyy") .format(new SimpleDateFormat("yyyy-MM-dd")
		 * .parse(sd)); String rt = new SimpleDateFormat("HH:mm:ss") .format(new
		 * SimpleDateFormat("HHmmss") .parse(forSettlement.getTime()));
		 * forSettlement.setDate(rd); forSettlement.setTime(rt); } catch (ParseException
		 * e) { }
		 * 
		 * } }
		 */

		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getEzyrecMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			// tidSet.add(mtid.toString());
		}
		/*
		 * if (tid.equalsIgnoreCase(mtid)) { midTransationList.add(t); }
		 */
		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}

		List<ForSettlement> list1 = paginationBean.getItemList();

		// System.out.println("display list:" + list1);
		// System.out.println("Export Type:" + export);

		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListExcel1", "txnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = "/exportCardTrans", method = RequestMethod.POST)
	public ModelAndView getExportCardTrans(final Model model, final java.security.Principal principal,

			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status,
			@RequestParam("devId") String devId, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("the merchant obj based on currently logged in user is: " + currentMerchant);

		Date frdate = new Date(fromDate);
		int fromday = frdate.getDate();
		int frommon = frdate.getMonth() + 1;
		int fromyear = frdate.getYear();
		int currentFrYear = fromyear + 1900;
		String frmon = String.format("%02d", frommon);
		String frday = String.format("%02d", fromday);
		// String fromdateString = frday + '/' + frmon + '/' +
		// String.valueOf(currentFrYear);
		String dat = String.valueOf(currentFrYear) + '-' + frmon + '-' + frday;

		Date todate = new Date(toDate);
		int today = todate.getDate();
		int tomon = todate.getMonth() + 1;
		int toyear = todate.getYear();
		int currentToYear = toyear + 1900;
		String tmon = String.format("%02d", tomon);
		String tday = String.format("%02d", today);
		// String todateString = tday + '/' + tmon + '/' +
		// String.valueOf(currentToYear);
		String dat1 = String.valueOf(currentToYear) + '-' + tmon + '-' + tday;

		logger.info("from date:" + dat + " to date:" + dat1);

		/*
		 * String dat = fromDate; String dat1 = toDate;
		 * 
		 * // logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": & :"
		 * +tid+": & :"+status); if (!(fromDate == null || fromDate.equals("")) &&
		 * !(toDate == null || toDate.equals(""))) {
		 * 
		 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); try { dat =
		 * dateFormat.format(new SimpleDateFormat("dd/MM/yyyy") .parse(fromDate)); }
		 * catch (ParseException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * dat = fromDate; String sub[] = dat.split("/"); dat = sub[0] + sub[1];
		 * 
		 * logger.info("check from date:" + fromDate); logger.info("check from date:" +
		 * dat);
		 * 
		 * // logger.info("DD sub : " + dat); // System.out.println("DD sub : " + dat);
		 * SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd"); try { dat1
		 * = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy") .parse(toDate)); }
		 * catch (ParseException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * dat1 = toDate; String sub1[] = dat1.split("/"); dat1 = sub1[0] + sub1[1];
		 * 
		 * System.out.println("dat1:" + dat1); }
		 */
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");
		transactionService.searchCardUMForSettlement(dat, dat1, tid, status, paginationBean, currentMerchant);
		/*
		 * transactionService.searchForSettlement(dat, dat1, paginationBean,
		 * currentMerchant);
		 */

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) { //
		 * logger.info("tid details:" + forSettlement.getTid().toString());
		 * TerminalDetails terminalDetails = transactionService
		 * .getTerminalDetailsByTid(forSettlement.getTid().toString()); if
		 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
		 * // terminalDetails.getContactName()); if (terminalDetails.getContactName() !=
		 * null) { forSettlement.setMerchantName(terminalDetails
		 * .getContactName().toUpperCase()); } else { forSettlement.setMerchantName("");
		 * } }
		 * 
		 * logger.info("test data "+ paginationBean.getItemList()); for(ForSettlement
		 * forSettlement:paginationBean.getItemList()){
		 * 
		 * if (forSettlement.getAmount() != null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()) / 100; //
		 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
		 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
		 * forSettlement.setAmount(output); } if (forSettlement.getStatus().equals("S"))
		 * { forSettlement.setStatus("SETTLED");
		 * 
		 * } if (forSettlement.getStatus().equals("P")) {
		 * forSettlement.setStatus("PENDING"); } if
		 * (forSettlement.getStatus().equals("A")) {
		 * forSettlement.setStatus("NOT SETTLED"); } if
		 * (forSettlement.getStatus().equals("C")) {
		 * forSettlement.setStatus("CANCELLED"); } if
		 * (forSettlement.getStatus().equals("R")) {
		 * forSettlement.setStatus("REVERSAL"); } if (forSettlement.getDate() != null &&
		 * forSettlement.getTime() != null) { try { // String
		 * sd=forSettlement.getDate()+new // SimpleDateFormat("y").format(new
		 * java.util.Date()); String sd = forSettlement.getTimeStamp(); String rd = new
		 * SimpleDateFormat("dd-MM-yyyy") .format(new SimpleDateFormat("yyyy-MM-dd")
		 * .parse(sd)); String rt = new SimpleDateFormat("HH:mm:ss") .format(new
		 * SimpleDateFormat("HHmmss") .parse(forSettlement.getTime()));
		 * forSettlement.setDate(rd); forSettlement.setTime(rt); } catch (ParseException
		 * e) { }
		 * 
		 * } }
		 */

		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			// tidSet.add(mtid.toString());
		}
		/*
		 * if (tid.equalsIgnoreCase(mtid)) { midTransationList.add(t); }
		 */
		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}

		List<ForSettlement> list1 = paginationBean.getItemList();

		// System.out.println("display list:" + list1);
		// System.out.println("Export Type:" + export);

		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListExcel1", "txnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = "/exportCashTrans", method = RequestMethod.POST)
	public ModelAndView getExportCashTrans(final Model model, final java.security.Principal principal,

			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,

			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("the merchant obj based on currently logged in user is: " + currentMerchant);
		String dat = null;
		String dat1 = null;

		if (!(fromDate == null || fromDate.equals("")) && !(toDate == null || toDate.equals(""))) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

			logger.info("check from date:" + fromDate);
			logger.info("check from date:" + dat);

			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			System.out.println("dat1:" + dat1);
		}
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		transactionService.searchForSettlementcash(dat, dat1, paginationBean, currentMerchant);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		for (ForSettlement forSettlement : paginationBean.getItemList()) {
			// logger.info("tid details:" + forSettlement.getTid().toString());
			TerminalDetails terminalDetails = transactionService
					.getTerminalDetailsByTid(forSettlement.getTid().toString());
			if (terminalDetails != null) {
				// logger.info("terminal details contact Name:" +
				// terminalDetails.getContactName());
				if (terminalDetails.getContactName() != null) {
					forSettlement.setMerchantName(terminalDetails.getContactName().toUpperCase());
				} else {
					forSettlement.setMerchantName("");
				}
			}
			/*
			 * logger.info("test data "+ paginationBean.getItemList()); for(ForSettlement
			 * forSettlement:paginationBean.getItemList()){
			 */
			if (forSettlement.getAmount() != null) {
				double amount = 0;
				amount = Double.parseDouble(forSettlement.getAmount()) / 100;
				// forSettlement.setAmount(amount+"0");
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				// System.out.println(" Amount :"+output);
				forSettlement.setAmount(output);
			}
			if (forSettlement.getStatus().equals("CT")) {
				forSettlement.setStatus("CASH SALE");

			}
			if (forSettlement.getStatus().equals("CV")) {
				forSettlement.setStatus("CASH CANCELLED");
			}
			if (forSettlement.getDate() != null && forSettlement.getTime() != null) {
				try {
					// String sd=forSettlement.getDate()+new
					// SimpleDateFormat("y").format(new java.util.Date());
					String sd = forSettlement.getTimeStamp();
					String rd = new SimpleDateFormat("dd-MM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(sd));
					String rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
					forSettlement.setDate(rd);
					forSettlement.setTime(rt);
				} catch (ParseException e) {
				}

			}
		}

		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			// tidSet.add(mtid.toString());
		}

		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}

		List<ForSettlement> list1 = paginationBean.getItemList();

		/*
		 * if (!(export.equals("PDF"))) { return new ModelAndView("txnListExcelcash",
		 * "txnList", list1); } else { return new ModelAndView("txnListPdfcash",
		 * "txnList", list1); }
		 */

		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListExcel1", "txnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public ModelAndView getExport(final Model model, final java.security.Principal principal,
			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status,
			@RequestParam("txnType") String txnType, @RequestParam("devId") String devId,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": &
		// :"+tid+":"+status);
		// PageBean pageBean=new PageBean("Transactions Details",
		// "transaction/receipt", null);
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant merchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("the merchant obj based on currently logged in user is: " + merchant);
		/*
		 * PageBean pageBean = new PageBean("transactions list",
		 * "merchantweb/transaction/transactionList", Module.TRANSACTION_WEB,
		 * "merchantweb/transaction/sideMenuTransaction");
		 * model.addAttribute("pageBean", pageBean);
		 */
		/*
		 * if(!(fromDate.equals(null)||toDate.equals(null)||fromDate.equals("")||
		 * toDate.equals(""))){
		 */

		// new changes//
		// logger.info("test Data:");

		Date frdate = new Date(fromDate);
		int fromday = frdate.getDate();
		int frommon = frdate.getMonth() + 1;
		int fromyear = frdate.getYear();
		int currentFrYear = fromyear + 1900;
		String frmon = String.format("%02d", frommon);
		String frday = String.format("%02d", fromday);
		// String fromdateString = frday + '/' + frmon + '/' +
		// String.valueOf(currentFrYear);
		String dat = String.valueOf(currentFrYear) + '-' + frmon + '-' + frday;

		Date todate = new Date(toDate);
		int today = todate.getDate();
		int tomon = todate.getMonth() + 1;
		int toyear = todate.getYear();
		int currentToYear = toyear + 1900;
		String tmon = String.format("%02d", tomon);
		String tday = String.format("%02d", today);
		// String todateString = tday + '/' + tmon + '/' +
		// String.valueOf(currentToYear);
		String dat1 = String.valueOf(currentToYear) + '-' + tmon + '-' + tday;

		logger.info("from date:" + dat + " to date:" + dat1);
		/*
		 * String dat = null; String dat1 = null;
		 * 
		 * // logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": & :"
		 * +tid+": & :"+status); if (!(fromDate == null || fromDate.equals("")) &&
		 * !(toDate == null || toDate.equals(""))) {
		 * 
		 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); try { dat =
		 * dateFormat.format(new SimpleDateFormat("dd/MM/yyyy") .parse(fromDate)); }
		 * catch (ParseException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * dat = fromDate; String sub[] = dat.split("/"); dat = sub[0] + sub[1];
		 * 
		 * logger.info("check from date:" + fromDate); logger.info("check from date:" +
		 * dat);
		 * 
		 * // logger.info("DD sub : " + dat); // System.out.println("DD sub : " + dat);
		 * SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd"); try { dat1
		 * = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy") .parse(toDate)); }
		 * catch (ParseException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * dat1 = toDate; String sub1[] = dat1.split("/"); dat1 = sub1[0] + sub1[1];
		 * 
		 * System.out.println("dat1:" + dat1); }
		 */
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");
		// transactionService.searchForSettlement(dat, dat1, tid, status,
		// paginationBean, currentMerchant);

		transactionService.searchUMForSettlement(dat, dat1, tid, status, paginationBean, merchant, txnType);

		/*
		 * model.addAttribute("fromDate", fromDate);
		 * 
		 * model.addAttribute("toDate", toDate);
		 * 
		 * model.addAttribute("tid", tid);
		 * 
		 * model.addAttribute("status", status);
		 */
		/* logger.info("test transaction :::" + devId); */
		// model.addAttribute("devId", devId);
		// logger.info("test transaction :::"+ paginationBean.getItemList() ==
		// null);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) { //
		 * logger.info("tid details:" + forSettlement.getTid().toString());
		 * TerminalDetails terminalDetails = transactionService
		 * .getTerminalDetailsByTid(forSettlement.getTid().toString()); if
		 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
		 * // terminalDetails.getContactName()); if (terminalDetails.getContactName() !=
		 * null) { forSettlement.setMerchantName(terminalDetails
		 * .getContactName().toUpperCase()); } else { forSettlement.setMerchantName("");
		 * } }
		 * 
		 * logger.info("test data "+ paginationBean.getItemList()); for(ForSettlement
		 * forSettlement:paginationBean.getItemList()){
		 * 
		 * if (forSettlement.getAmount() != null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()) / 100; //
		 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
		 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
		 * forSettlement.setAmount(output); } if
		 * (forSettlement.getStatus().equals("CT")) {
		 * forSettlement.setStatus("CASH SALE");
		 * 
		 * } if (forSettlement.getStatus().equals("CV")) {
		 * forSettlement.setStatus("CASH CANCELLED"); } if
		 * (forSettlement.getStatus().equals("S")) { forSettlement.setStatus("SETTLED");
		 * 
		 * } if (forSettlement.getStatus().equals("P")) {
		 * forSettlement.setStatus("PENDING"); } if
		 * (forSettlement.getStatus().equals("A")) {
		 * forSettlement.setStatus("NOT SETTLED"); } if
		 * (forSettlement.getStatus().equals("C")) {
		 * forSettlement.setStatus("CANCELLED"); } if
		 * (forSettlement.getStatus().equals("R")) {
		 * forSettlement.setStatus("REVERSAL"); } if (forSettlement.getDate() != null &&
		 * forSettlement.getTime() != null) { try { // String
		 * sd=forSettlement.getDate()+new // SimpleDateFormat("y").format(new
		 * java.util.Date()); String sd = forSettlement.getTimeStamp(); String rd = new
		 * SimpleDateFormat("dd-MM-yyyy") .format(new SimpleDateFormat("yyyy-MM-dd")
		 * .parse(sd)); String rt = new SimpleDateFormat("HH:mm:ss") .format(new
		 * SimpleDateFormat("HHmmss") .parse(forSettlement.getTime()));
		 * forSettlement.setDate(rd); forSettlement.setTime(rt); } catch (ParseException
		 * e) { }
		 * 
		 * } }
		 */
		/*
		 * String myName = principal.getName(); logger.info("currently logged in as " +
		 * myName); Merchant currentMerchant = merchantService.loadMerchant(myName);
		 */
		List<TerminalDetails> terminalDetails = transactionService.getTerminalDetails(merchant.getMid().getMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			// tidSet.add(mtid.toString());
		}
		/*
		 * if (tid.equalsIgnoreCase(mtid)) { midTransationList.add(t); }
		 */
		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}
		/*
		 * model.addAttribute("devIdList", dIdSet);
		 * 
		 * model.addAttribute("tidList", tidSet); model.addAttribute("paginationBean",
		 * paginationBean);
		 * 
		 * return TEMPLATE_MERCHANT;
		 */

		List<UMEzyway> list1 = paginationBean.getItemList();

		// System.out.println("display list:" + list1);
		// System.out.println("Export Type:" + export);

		if (!(export.equals("PDF"))) {
			return new ModelAndView("UMMerchantWireExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = { "/umMotoList" }, method = RequestMethod.GET)
	public String umMotoList(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list umMotoList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = currentMerchant.getBusinessName();
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMMotoList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-MOTO Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMMotoTransaction(paginationBean, null, null, currentMerchant.getMid().getUmMotoMid(),currentMerchant.getMid().getFiuuMid(),
				"ALL", currentMerchant);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantName);
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/umEzyrecList" }, method = RequestMethod.GET)
	public String umEzyrecList(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list umEzyrecList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("currentMerchant " + currentMerchant.getBusinessName());
		logger.info("currentMerchant mid " + currentMerchant.getMid().getUmEzyrecMid());
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzyrecList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-MOTO Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMEzyrecTransaction(paginationBean, null, null,
				currentMerchant.getMid().getUmEzyrecMid(), "ALL", currentMerchant);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/umLinkList" }, method = RequestMethod.GET)
	public String umLinkList(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list umLinkList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = currentMerchant.getBusinessName();
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMLinkList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-Link Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMLinkTransaction(paginationBean, null, null, currentMerchant.getMid().getUmMotoMid(),
				"ALL", currentMerchant);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantName);
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/umVccList" }, method = RequestMethod.GET)
	public String umVccList(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list umVccList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMVccList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-VCC Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMVccTransaction(paginationBean, null, null, currentMerchant.getMid().getUmMotoMid(),
				"ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// UM-EZYAUTH Merchant Transaction Summary
	@RequestMapping(value = { "/umEzyauthList" }, method = RequestMethod.GET)
	public String umEzyauthList(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal, HttpServletRequest request) {
		logger.info(" UM-EZYAUTH Transaction Summary Merchant");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = currentMerchant.getBusinessName();

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzyauthList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-EZYAUTH Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMEzyauthMerchantTransaction(paginationBean, null, null, currentMerchant, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantName);
		return TEMPLATE_MERCHANT;
	}

	// umezyauthfailed transactions
	// rk added
	@RequestMapping(value = { "/umEzyauthFailList" }, method = RequestMethod.GET)
	public String umEzyauthFailList(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request)

	{
		logger.info(" UM-EZYAUTH  Failed Transaction Summary Merchant");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMFailAuthList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-EZYAUTH failed Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMEzyauthFailedTransaction(paginationBean, null, null, currentMerchant, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchumEzyauthFail" }, method = RequestMethod.GET)
	public String searchumEzyauthFail(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("search  UM-EZYAUTH failed Transaction ");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMFailAuthList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listUMEzyauthFailedTransaction(paginationBean, date, date1, currentMerchant, "ALL");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/umEzyauthFailExport", method = RequestMethod.GET)
	public ModelAndView getExportUMEzyauthFail(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getUmMotoMid());

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		// transactionService.listUMEzyauthFailedTransaction(paginationBean, dat, dat1,
		// currentMerchant, "ALL");
		transactionService.listUMEzyauthFailedTransaction1(paginationBean, dat, dat1, currentMerchant, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListUMMerchantFailExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListMotoUMPdf", "umTxnList", list1);
		}

	}

	// um ezyauth failed transactions end

	@RequestMapping(value = { "/umEzywayList" }, method = RequestMethod.GET)
	public String umEzywayList(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list umEzywayList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = currentMerchant.getBusinessName();
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzywayList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-EZWAY Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
			String ezywaymid = "000000000021591";
			transactionService.listUMEzywayTransaction(paginationBean, null, null, ezywaymid, "ALL", currentMerchant);
		} else {
			transactionService.listUMEzywayTransaction(paginationBean, null, null,
					currentMerchant.getMid().getUmEzywayMid(), "ALL", currentMerchant);
		}
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
			model.addAttribute("score", "yes");
		} else {
			model.addAttribute("score", "no");
		}

		String UmEzywayMid = null;
		Merchant merchant = merchantService.loadMerchant(myName);

		if (merchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = merchant.getMid().getUmEzywayMid();
		}

		model.addAttribute("UmEzywayMid", UmEzywayMid);

		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else if (userName.equalsIgnoreCase("FINANCE@DLOCAL.COM")
				|| userName.equalsIgnoreCase("daria.ar@monetix.pro")) {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantName);
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchUMMoto" }, method = RequestMethod.GET)
	public String umMotoList1(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam final String txnType,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("search  UM-Moto Transaction ");

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = currentMerchant.getBusinessName();
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMMotoList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listUMMotoTransaction(paginationBean, fromDate, toDate,
				currentMerchant.getMid().getUmMotoMid(),currentMerchant.getMid().getFiuuMid(), txnType, currentMerchant);
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantName);
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/searchUMEzyrec" }, method = RequestMethod.GET)
	public String searchUMEzyrec(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam final String txnType,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("searchUMEzyrec Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzyrecList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMEzyrecTransaction(paginationBean, date, date1,
				currentMerchant.getMid().getUmEzyrecMid(), txnType, currentMerchant);
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/searchUMLink" }, method = RequestMethod.GET)
	public String searchUMLink(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam final String txnType,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("search  UM-link Transaction ");
		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = currentMerchant.getBusinessName();
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMLinkList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listUMLinkTransaction(paginationBean, fromDate, toDate,
				currentMerchant.getMid().getUmMotoMid(), txnType, currentMerchant);

		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantName);
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/searchUMVcc" }, method = RequestMethod.GET)
	public String searchUMVcc(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("search  UM-Moto VCC Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMVccList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listUMVccTransaction(paginationBean, date, date1, currentMerchant.getMid().getUmMotoMid(),
				"ALL");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// UM-Ezyauth Merchant search
	@RequestMapping(value = { "/searchUMEzyauth" }, method = RequestMethod.GET)
	public String umEzyauthList1(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("search  UM-EZYAUTH Transaction ");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = currentMerchant.getBusinessName();

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzyauthList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listUMEzyauthMerchantTransaction(paginationBean, date, date1, currentMerchant, "ALL");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantName);
		return TEMPLATE_MERCHANT;

	}

	// UM-Ezyway search
	@RequestMapping(value = { "/searchUMEzyway" }, method = RequestMethod.GET)
	public String umEzywayList1(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam final String txnType,
			final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  UM-EZWAY Transaction ");
		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = currentMerchant.getBusinessName();
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzywayList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
			String ezywaymid = "000000000021591";
			transactionService.SearchlistUMEzywayTransaction(paginationBean, fromDate, toDate, ezywaymid, txnType,
					currentMerchant);
		} else {
			transactionService.SearchlistUMEzywayTransaction(paginationBean, fromDate, toDate,
					currentMerchant.getMid().getUmEzywayMid(), txnType, currentMerchant);
		}
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);
		logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());
		if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
			model.addAttribute("score", "yes");
		} else {
			model.addAttribute("score", "no");
		}

		String UmEzywayMid = null;
		Merchant merchant = merchantService.loadMerchant(myName);

		if (merchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = merchant.getMid().getUmEzywayMid();
		}

		model.addAttribute("UmEzywayMid", UmEzywayMid);

		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantName);
		return TEMPLATE_MERCHANT;

	}

	// UM-EYWAY Void
	@RequestMapping(value = { "/cancelPayment1/{id}" }, method = RequestMethod.GET)
	public String cancelPaymentUMEzywayDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/voidpayment/umCancelPayment",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		/*
		 * UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);
		 * 
		 * UMEzyway txnDet = new UMEzyway(); txnDet.setF354_TID(tr.getF354_TID());
		 * txnDet.setF001_MID(tr.getF001_MID());
		 * txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp()); String rd = null; if
		 * (tr.getTimeStamp() != null) {
		 * 
		 * try { rd = new SimpleDateFormat("dd-MMM-yyyy") .format(new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tr.getTimeStamp())); } catch
		 * (ParseException e) { // TODO Auto-generated catch block e.printStackTrace();
		 * }
		 * 
		 * } String rt = null; if (tr.getTimeStamp() != null) { try { rt = new
		 * SimpleDateFormat("HH:mm:ss").format(new
		 * SimpleDateFormat("HHmmss").parse(tr.getTimeStamp()));
		 * 
		 * } catch (ParseException e) { } } txnDet.setTime_Stamp(rd + " " + rt); double
		 * amount = 0; amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100; String
		 * pattern = "#,##0.00"; DecimalFormat myFormatter = new DecimalFormat(pattern);
		 * String output = myFormatter.format(amount); txnDet.setF007_TXNAMT(output);
		 * 
		 * txnDet.setF263_MRN(tr.getF263_MRN());
		 * txnDet.setF268_CHNAME(tr.getF268_ChName());
		 * 
		 * if (tr.getMaskedPan() != null) { String pan =
		 * tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);
		 * 
		 * if (pan.contains("f")) { pan = pan.replaceAll("f", "X"); txnDet.setPAN(pan);
		 * } else { txnDet.setPAN(pan); }
		 * 
		 * } else { txnDet.setPAN("NA"); }
		 * 
		 * // txnDet.setMerchantId(merchant.getId()); model.addAttribute("pageBean",
		 * pageBean); model.addAttribute("txnDet", txnDet);
		 */

		UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);
		
//			UMEzyway txnDet = new UMEzyway();
//			txnDet.setF354_TID(tr.getF354_TID());
//			txnDet.setF001_MID(tr.getF001_MID());
//			txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp());
		String rd = null;
		if (tr.getH003_TDT() != null) {

			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyyMMdd").parse(tr.getH003_TDT()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String rt = null;
		if (tr.getH004_TTM() != null) {
			try {
				rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(tr.getH004_TTM()));

			} catch (ParseException e) {
			}
		}
		tr.setH003_TDT(rd);
		tr.setH004_TTM(rt);
		double amount = 0;
		amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		tr.setF007_TxnAmt(output);

//			txnDet.setF263_MRN(tr.getF263_MRN());
//			txnDet.setF268_CHNAME(tr.getF268_ChName());

		if (tr.getMaskedPan() != null) {
			String pan = tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);

			if (pan.contains("f")) {
				pan = pan.replaceAll("f", "X");
				tr.setMaskedPan(pan);
			} else {
				tr.setMaskedPan(pan);
			}

		} else {
			tr.setMaskedPan("NA");
		}
	
//			txnDet.setMerchantId(merchant.getId());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", tr);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// UMMOto void
	@RequestMapping(value = { "/cancelMotoPayment1/{id}" }, method = RequestMethod.GET)
	public String cancelPaymentUMMotoDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/voidpayment/umMotoCancelPayment",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		/*
		 * UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);
		 * 
		 * UMEzyway txnDet = new UMEzyway(); txnDet.setF354_TID(tr.getF354_TID());
		 * txnDet.setF001_MID(tr.getF001_MID());
		 * txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp()); String rd = null; if
		 * (tr.getTimeStamp() != null) {
		 * 
		 * try { rd = new SimpleDateFormat("dd-MMM-yyyy") .format(new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tr.getTimeStamp())); } catch
		 * (ParseException e) { // TODO Auto-generated catch block e.printStackTrace();
		 * }
		 * 
		 * } String rt = null; if (tr.getTimeStamp() != null) { try { rt = new
		 * SimpleDateFormat("HH:mm:ss").format(new
		 * SimpleDateFormat("HHmmss").parse(tr.getTimeStamp()));
		 * 
		 * } catch (ParseException e) { } } txnDet.setTime_Stamp(rd + " " + rt); double
		 * amount = 0; amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100; String
		 * pattern = "#,##0.00"; DecimalFormat myFormatter = new DecimalFormat(pattern);
		 * String output = myFormatter.format(amount); txnDet.setF007_TXNAMT(output);
		 * 
		 * txnDet.setF263_MRN(tr.getF263_MRN());
		 * txnDet.setF268_CHNAME(tr.getF268_ChName());
		 * 
		 * if (tr.getMaskedPan() != null) { String pan =
		 * tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);
		 * 
		 * if (pan.contains("f")) { pan = pan.replaceAll("f", "X"); txnDet.setPAN(pan);
		 * } else { txnDet.setPAN(pan); }
		 * 
		 * } else { txnDet.setPAN("NA"); }
		 * 
		 * // txnDet.setMerchantId(merchant.getId()); model.addAttribute("pageBean",
		 * pageBean); model.addAttribute("txnDet", txnDet);
		 */

		UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

//			UMEzyway txnDet = new UMEzyway();
//			txnDet.setF354_TID(tr.getF354_TID());
//			txnDet.setF001_MID(tr.getF001_MID());
//			txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp());
		String rd = null;
		if (tr.getH003_TDT() != null) {

			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyyMMdd").parse(tr.getH003_TDT()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String rt = null;
		if (tr.getH004_TTM() != null) {
			try {
				rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(tr.getH004_TTM()));

			} catch (ParseException e) {
			}
		}
		tr.setH003_TDT(rd);
		tr.setH004_TTM(rt);
		double amount = 0;
		amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		tr.setF007_TxnAmt(output);

//			txnDet.setF263_MRN(tr.getF263_MRN());
//			txnDet.setF268_CHNAME(tr.getF268_ChName());

		if (tr.getMaskedPan() != null) {
			String pan = tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);

			if (pan.contains("f")) {
				pan = pan.replaceAll("f", "X");
				tr.setMaskedPan(pan);
			} else {
				tr.setMaskedPan(pan);
			}

		} else {
			tr.setMaskedPan("NA");
		}

//			txnDet.setMerchantId(merchant.getId());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", tr);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// UMMOto void
	@RequestMapping(value = { "/cancelUmEzyrecPayment1/{id}" }, method = RequestMethod.GET)
	public String cancelPaymentUMEzyrecDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/voidpayment/umMotoCancelPayment",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

//					UMEzyway txnDet = new UMEzyway();
//					txnDet.setF354_TID(tr.getF354_TID());
//					txnDet.setF001_MID(tr.getF001_MID());
//					txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp());
		String rd = null;
		if (tr.getH003_TDT() != null) {

			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyyMMdd").parse(tr.getH003_TDT()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String rt = null;
		if (tr.getH004_TTM() != null) {
			try {
				rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(tr.getH004_TTM()));

			} catch (ParseException e) {
			}
		}
		tr.setH003_TDT(rd);
		tr.setH004_TTM(rt);
		double amount = 0;
		amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		tr.setF007_TxnAmt(output);

//					txnDet.setF263_MRN(tr.getF263_MRN());
//					txnDet.setF268_CHNAME(tr.getF268_ChName());

		if (tr.getMaskedPan() != null) {
			String pan = tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);

			if (pan.contains("f")) {
				pan = pan.replaceAll("f", "X");
				tr.setMaskedPan(pan);
			} else {
				tr.setMaskedPan(pan);
			}

		} else {
			tr.setMaskedPan("NA");
		}

//					txnDet.setMerchantId(merchant.getId());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", tr);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/cancelPaymentByAdmin1" }, method = RequestMethod.POST)
	public String motoSubmitTransaction(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("cancelPaymentByAdmin");
		ResponseDetails data= null;
		logger.info("merchant type for void : "+TxnDet.getF260_ServID());	
		
		if(TxnDet.getF260_ServID().equalsIgnoreCase("UM")) {
			 data = MotoPaymentCommunication.UmCancelPayment(TxnDet);
		}else if(TxnDet.getF260_ServID().equalsIgnoreCase("FIUU")) {
			logger.info("inside fiuu end point call");
			 data = MotoPaymentCommunication.FiuuCancelPayment(TxnDet);
		}

		double amount = 0;
		amount = Double.parseDouble(data.getVoidResp().getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		String rd = "";
		String rt = "";
		try {
			rd = new SimpleDateFormat("dd-MMM-yyyy")
					.format(new SimpleDateFormat("yyyyMMdd").parse(data.getVoidResp().getH003_TDT()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			rt = new SimpleDateFormat("HH:mm:ss")
					.format(new SimpleDateFormat("HHmmss").parse(data.getVoidResp().getH004_TTM()));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("transactions list",
						"merchantweb/transaction/voidpayment/umCancelPayment", Module.TRANSACTION_WEB,
						"merchantweb/transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", TxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("transactions list",
						"merchantweb/transaction/voidpayment/umVoidPaymentDone", Module.TRANSACTION_WEB,
						"merchantweb/transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("amount", output);
				model.addAttribute("TDT", " " + rd + " " + rt);
				model.addAttribute("CHNumber", data.getVoidResp().getF004_PAN());
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/voidpayment/umCancelPayment",
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/cancelEzylinkPayment1/{id}" }, method = RequestMethod.GET)
	public String cancelPaymentEzylinkDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list",
				"merchantweb/transaction/voidpayment/umEzylinkCancelPayment", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		/*
		 * UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);
		 * 
		 * UMEzyway txnDet = new UMEzyway(); txnDet.setF354_TID(tr.getF354_TID());
		 * txnDet.setF001_MID(tr.getF001_MID());
		 * txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp()); String rd = null; if
		 * (tr.getTimeStamp() != null) {
		 * 
		 * try { rd = new SimpleDateFormat("dd-MMM-yyyy") .format(new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tr.getTimeStamp())); } catch
		 * (ParseException e) { // TODO Auto-generated catch block e.printStackTrace();
		 * }
		 * 
		 * } String rt = null; if (tr.getTimeStamp() != null) { try { rt = new
		 * SimpleDateFormat("HH:mm:ss").format(new
		 * SimpleDateFormat("HHmmss").parse(tr.getTimeStamp()));
		 * 
		 * } catch (ParseException e) { } } txnDet.setTime_Stamp(rd + " " + rt); double
		 * amount = 0; amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100; String
		 * pattern = "#,##0.00"; DecimalFormat myFormatter = new DecimalFormat(pattern);
		 * String output = myFormatter.format(amount); txnDet.setF007_TXNAMT(output);
		 * 
		 * txnDet.setF263_MRN(tr.getF263_MRN());
		 * txnDet.setF268_CHNAME(tr.getF268_ChName());
		 * 
		 * if (tr.getMaskedPan() != null) { String pan =
		 * tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);
		 * 
		 * if (pan.contains("f")) { pan = pan.replaceAll("f", "X"); txnDet.setPAN(pan);
		 * } else { txnDet.setPAN(pan); }
		 * 
		 * } else { txnDet.setPAN("NA"); }
		 * 
		 * // txnDet.setMerchantId(merchant.getId()); model.addAttribute("pageBean",
		 * pageBean); model.addAttribute("txnDet", txnDet);
		 */

		UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

//			UMEzyway txnDet = new UMEzyway();
//			txnDet.setF354_TID(tr.getF354_TID());
//			txnDet.setF001_MID(tr.getF001_MID());
//			txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp());
		String rd = null;
		if (tr.getH003_TDT() != null) {

			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyyMMdd").parse(tr.getH003_TDT()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String rt = null;
		if (tr.getH004_TTM() != null) {
			try {
				rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(tr.getH004_TTM()));

			} catch (ParseException e) {
			}
		}
		tr.setH003_TDT(rd);
		tr.setH004_TTM(rt);
		double amount = 0;
		amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		tr.setF007_TxnAmt(output);

		if (tr.getMaskedPan() != null) {
			String pan = tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);

			if (pan.contains("f")) {
				pan = pan.replaceAll("f", "X");
				tr.setMaskedPan(pan);
			} else {
				tr.setMaskedPan(pan);
			}

		} else {
			tr.setMaskedPan("NA");
		}

//			txnDet.setMerchantId(merchant.getId());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", tr);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/cancelMotoPaymentByAdmin1" }, method = RequestMethod.POST)
	public String motoVoidSubmitTransaction(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("cancelPaymentByAdmin");

		ResponseDetails data = MotoPaymentCommunication.UmCancelPayment(TxnDet);

		double amount = 0;
		amount = Double.parseDouble(data.getVoidResp().getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		String rd = "";
		String rt = "";
		try {
			rd = new SimpleDateFormat("dd-MMM-yyyy")
					.format(new SimpleDateFormat("yyyyMMdd").parse(data.getVoidResp().getH003_TDT()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			rt = new SimpleDateFormat("HH:mm:ss")
					.format(new SimpleDateFormat("HHmmss").parse(data.getVoidResp().getH004_TTM()));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("transactions list",
						"merchantweb/transaction/voidpayment/umMotoCancelPayment", Module.TRANSACTION_WEB,
						"merchantweb/transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", TxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("transactions list",
						"merchantweb/transaction/voidpayment/umMotoVoidPaymentDone", Module.TRANSACTION_WEB,
						"merchantweb/transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("amount", output);
				model.addAttribute("TDT", " " + rd + " " + rt);
				model.addAttribute("CHNumber", data.getVoidResp().getF004_PAN());
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("transactions list",
					"merchantweb/transaction/voidpayment/umMotoCancelPayment", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/cancelEzyrecPaymentByAdmin1" }, method = RequestMethod.POST)
	public String ezyrecVoidSubmitTransaction(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("cancelPaymentByAdmin");

		ResponseDetails data = MotoPaymentCommunication.UmEzyrecCancelPayment(TxnDet);

		double amount = 0;
		amount = Double.parseDouble(data.getVoidResp().getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		String rd = "";
		String rt = "";
		try {
			rd = new SimpleDateFormat("dd-MMM-yyyy")
					.format(new SimpleDateFormat("yyyyMMdd").parse(data.getVoidResp().getH003_TDT()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			rt = new SimpleDateFormat("HH:mm:ss")
					.format(new SimpleDateFormat("HHmmss").parse(data.getVoidResp().getH004_TTM()));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("transactions list",
						"merchantweb/transaction/voidpayment/umEzyrecCancelPayment", Module.TRANSACTION_WEB,
						"merchantweb/transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", TxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("transactions list",
						"merchantweb/transaction/voidpayment/umEzyrecVoidPaymentDone", Module.TRANSACTION_WEB,
						"merchantweb/transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("amount", output);
				model.addAttribute("TDT", " " + rd + " " + rt);
				model.addAttribute("CHNumber", data.getVoidResp().getF004_PAN());
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("transactions list",
					"merchantweb/transaction/voidpayment/umEzyrecCancelPayment", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/cancelEzylinkPaymentByAdmin1" }, method = RequestMethod.POST)
	public String EzylinkVoidSubmitTransaction(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("cancelPaymentByAdmin");

		ResponseDetails data = null;

		if(TxnDet.getF260_ServID().equalsIgnoreCase("UM")) {
			 data = MotoPaymentCommunication.UmCancelPayment(TxnDet);
		}else if(TxnDet.getF260_ServID().equalsIgnoreCase("FIUU")) {
			logger.info("inside fiuu end point call");
			 data = MotoPaymentCommunication.FiuuCancelPayment(TxnDet);
		}
		
		//ResponseDetails data = MotoPaymentCommunication.UmCancelPayment(TxnDet);

		double amount = 0;
		amount = Double.parseDouble(data.getVoidResp().getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		String rd = "";
		String rt = "";
		try {
			rd = new SimpleDateFormat("dd-MMM-yyyy")
					.format(new SimpleDateFormat("yyyyMMdd").parse(data.getVoidResp().getH003_TDT()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			rt = new SimpleDateFormat("HH:mm:ss")
					.format(new SimpleDateFormat("HHmmss").parse(data.getVoidResp().getH004_TTM()));

		} catch (ParseException e) {
			e.printStackTrace();
		}

		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("transactions list",
						"merchantweb/transaction/voidpayment/umEzylinkCancelPayment", Module.TRANSACTION_WEB,
						"merchantweb/transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", TxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("transactions list",
						"merchantweb/transaction/voidpayment/umEzylinkVoidPaymentDone", Module.TRANSACTION_WEB,
						"merchantweb/transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("amount", output);
				model.addAttribute("TDT", " " + rd + " " + rt);
				model.addAttribute("CHNumber", data.getVoidResp().getF004_PAN());
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("transactions list",
					"merchantweb/transaction/voidpayment/umEzylinkCancelPayment", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/UMMotoenquiryTransaction/{currPage}" }, method = RequestMethod.GET)
	public String UMMotoTransactionEnquiry(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("UM_MOTO Transaction Enquiry list by Merchant");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/umMotoTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		logger.info(" All Transaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		// transactionService.listUMMotoTxnEnqByAdmin(paginationBean, null, null,"ALL");

		transactionService.listUMMotoTransactionEnq(paginationBean, null, null, currentMerchant.getMid().getUmMotoMid(),
				"ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		logger.info(" enquiry");
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/UMLinkenquiryTransaction/{currPage}" }, method = RequestMethod.GET)
	public String UMLinkenquiryTransaction(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("UM_LINK Transaction Enquiry list by Merchant");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/umLinkTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		logger.info(" All Transaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		// transactionService.listUMMotoTxnEnqByAdmin(paginationBean, null, null,"ALL");

		transactionService.listUMMotoTransactionEnq(paginationBean, null, null, currentMerchant.getMid().getUmMotoMid(),
				"ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		logger.info(" enquiry");
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/UMVccenquiryTransaction/{currPage}" }, method = RequestMethod.GET)
	public String UMVccenquiryTransaction(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("UM_MOTO VCC Transaction Enquiry list by Merchant");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/umVccTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		logger.info(" All Transaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		// transactionService.listUMMotoTxnEnqByAdmin(paginationBean, null, null,"ALL");

		transactionService.listUMVccTransactionEnq(paginationBean, null, null, currentMerchant.getMid().getUmMotoMid(),
				"ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		logger.info(" enquiry");
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchUMMotoEnquiry" }, method = RequestMethod.GET)
	public String SearchUMMotoTransactionEnquiry(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("Transaction Enquiry list");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/umMotoTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMMotoTransactionEnq(paginationBean, date, date1,
				currentMerchant.getMid().getUmMotoMid(), "ALL");
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchUMLinkEnquiry" }, method = RequestMethod.GET)
	public String searchUMLinkEnquiry1(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("Transaction Enquiry list");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/umLinkTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMMotoTransactionEnq(paginationBean, date, date1,
				currentMerchant.getMid().getUmMotoMid(), "ALL");
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchUMVccEnquiry" }, method = RequestMethod.GET)
	public String searchUMLinkEnquiry(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("Transaction Enquiry list");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/umVccTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMVccTransactionEnq(paginationBean, date, date1, currentMerchant.getMid().getUmMotoMid(),
				"ALL");
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = "/umMotoEnqExport", method = RequestMethod.GET)
	public ModelAndView getExportUMMotoEnq(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
			// @RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("UM_MOTO Enquiry merchant by admin");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMMotoTransactionEnq(paginationBean, dat, dat1,
				currentMerchant.getMid().getUmMotoMid(), "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnUMEnqExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnUMEnqPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umLinkEnqExport", method = RequestMethod.GET)
	public ModelAndView umLinkEnqExport1(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
			// @RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("UM_Link Enquiry merchant by admin");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMMotoTransactionEnq(paginationBean, dat, dat1,
				currentMerchant.getMid().getUmMotoMid(), "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnUMEnqExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnUMEnqPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umVccEnqExport", method = RequestMethod.GET)
	public ModelAndView umLinkEnqExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
			// @RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("UM_MOTO VCC  Enquiry merchant by admin");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMVccTransactionEnq(paginationBean, dat, dat1, currentMerchant.getMid().getUmMotoMid(),
				"ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnUMEnqExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnUMEnqPdf", "umTxnList", list1);
		}

	}

	// UM-EZYAUTH Action
	@RequestMapping(value = { "/ezyauthDetails/{id}" }, method = RequestMethod.GET)
	public String UMEzyauthDetails(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/voidpayment/umEzyAuth",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

//		UMEzyway txnDet = new UMEzyway();
//		txnDet.setF354_TID(tr.getF354_TID());
//		txnDet.setF001_MID(tr.getF001_MID());
//		txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp());
		logger.info("umEzyAuth mrn :" + tr.getF263_MRN());
		logger.info("H001_MTI :" + tr.getH001_MTI());
		logger.info("Status  :" + tr.getStatus());
		String rd = null;
		String data = null;
		if (tr.getH003_TDT() != null) {

			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyyMMdd").parse(tr.getH003_TDT()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String rt = null;
		if (tr.getH004_TTM() != null) {
			try {
				rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(tr.getH004_TTM()));

			} catch (ParseException e) {
			}
		}
		tr.setH003_TDT(rd);
		tr.setH004_TTM(rt);
		double amount = 0;
		amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		tr.setF007_TxnAmt(output);

//		txnDet.setF263_MRN(tr.getF263_MRN());
//		txnDet.setF268_CHNAME(tr.getF268_ChName());

		if (tr.getMaskedPan() != null) {
			String pan = tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);

			if (pan.contains("f")) {
				pan = pan.replaceAll("f", "X");
				tr.setMaskedPan(pan);
			} else {
				tr.setMaskedPan(pan);
			}

		} else {
			tr.setMaskedPan("NA");
		}

		data = (String) request.getSession(true).getAttribute("responseDescriptioSession");

		logger.info("Response Description ::::::::::::  :" + data);

		if (data != null) {
			model.addAttribute("responseData", data + "...  Try Again..");
		}

//		txnDet.setMerchantId(merchant.getId());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", tr);

		request.getSession(true).removeAttribute("responseDescriptioSession");
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/umAuthByMerchant" }, method = RequestMethod.POST)
	public String authSubmitTransaction(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,

			@RequestParam("f354_TID") String f354_TID, @RequestParam("f001_MID") String f001_MID,
			@RequestParam("h001_MTI") String h001_MTI, @RequestParam("h003_TDT") String h003_TDT,
			@RequestParam("h004_TTM") String h004_TTM, @RequestParam("f011_AuthIDResp") String f011_AuthIDResp,

			@RequestParam("f007_TxnAmt") String f007_TxnAmt, @RequestParam("f268_ChName") String f268_ChName,
			@RequestParam("maskedPan") String maskedPan, @RequestParam("f263_MRN") String f263_MRN,
			@RequestParam("status") String status,

			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("umAuthByMerchant" + f263_MRN);
		logger.info("umAuthByMerchant Status:::::::::::" + status);
		logger.info("um H001_MTI :::::::::::" + h001_MTI);
		logger.info("Amount :::::::::::" + f007_TxnAmt);

		UMEcomTxnRequest TxnDet1 = new UMEcomTxnRequest();
		TxnDet1.setF354_TID(f354_TID);
		TxnDet1.setF001_MID(f001_MID);
		TxnDet1.setH001_MTI(h001_MTI);
		TxnDet1.setH003_TDT(h003_TDT);
		TxnDet1.setH004_TTM(h004_TTM);
		TxnDet1.setF011_AuthIDResp(f011_AuthIDResp);
		TxnDet1.setF007_TxnAmt(f007_TxnAmt);
		TxnDet1.setF268_ChName(f268_ChName);
		TxnDet1.setMaskedPan(maskedPan);
		TxnDet1.setF263_MRN(f263_MRN);
		TxnDet1.setStatus(status);

		ResponseDetails data = MotoPaymentCommunication.UmAuthPayment(TxnDet1);

		if (data != null) {
			if (data.getResponseCode().equals("0001")) {

				logger.info("fail 01 :::::::::::");

				/*
				 * PageBean pageBean = new PageBean("transactions list",
				 * "transaction/voidpayment/umEzyAuth", Module.TRANSACTION,
				 * "transaction/sideMenuTransaction"); model.addAttribute("pageBean", pageBean);
				 * model.addAttribute("txnDet", TxnDet); model.addAttribute("responseData",
				 * data.getResponseDescription()+"...  Try Again..");
				 */

				request.getSession(true).setAttribute("responseDescriptioSession", data.getResponseDescription());

				return "redirect:" + URL_BASE + "/ezyauthDetails/" + f263_MRN;
			} else {

				PageBean pageBean = new PageBean("transactions list",
						"merchantweb/transaction/voidpayment/umEzyAuthDone", Module.TRANSACTION_WEB,
						"merchantweb/transaction/sideMenuTransaction");

				double amount = 0;
				amount = Double.parseDouble(data.getSaleResp().getF007_TxnAmt()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				String rd = "";
				String rt = "";
				try {
					rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyyMMdd").parse(data.getSaleResp().getH003_TDT()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(data.getSaleResp().getH004_TTM()));

				} catch (ParseException e) {
					e.printStackTrace();
				}

				model.addAttribute("pageBean", pageBean);
				model.addAttribute("amount", output);
				model.addAttribute("TDT", " " + rd + " " + rt);
				model.addAttribute("CHNumber", data.getSaleResp().getF004_Pan());
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {

			logger.info("fail 02 :::::::::::");

			/*
			 * PageBean pageBean = new PageBean("transactions list",
			 * "transaction/voidpayment/umEzyAuth", Module.TRANSACTION,
			 * "transaction/sideMenuTransaction"); model.addAttribute("pageBean", pageBean);
			 * model.addAttribute("txnDet", TxnDet); model.addAttribute("responseData",
			 * "Failed, Try Again..");
			 */
			request.getSession(true).setAttribute("responseDescriptioSession", "Failed, Try Again..");

			return "redirect:" + URL_BASE + "/ezyauthDetails/ " + f263_MRN;
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// UM-EZYAUTH Reverse after sale
	@RequestMapping(value = { "/ezyauthReverseDetails/{id}" }, method = RequestMethod.GET)
	public String UMEzyauthReverseDetails(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		logger.info("UMEzyauthReverseDetails Reverse after sale ");

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/voidpayment/umEzyAuthReverse",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		// UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

		UMEcomTxnResponse tr = transactionService.loadUMEzywayTransactionResponse(id);

//		UMEzyway txnDet = new UMEzyway();
//		txnDet.setF354_TID(tr.getF354_TID());
//		txnDet.setF001_MID(tr.getF001_MID());
//		txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp());
		logger.info("umEzyAuth mrn :" + tr.getF263_MRN());
		logger.info("H001_MTI :" + tr.getH001_MTI());
		String rd = null;
		String data = null;
		if (tr.getH003_TDT() != null) {

			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyyMMdd").parse(tr.getH003_TDT()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String rt = null;
		if (tr.getH004_TTM() != null) {
			try {
				rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(tr.getH004_TTM()));

			} catch (ParseException e) {
			}
		}
		tr.setH003_TDT(rd);
		tr.setH004_TTM(rt);
		double amount = 0;
		amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		tr.setF007_TxnAmt(output);

//		txnDet.setF263_MRN(tr.getF263_MRN());
//		txnDet.setF268_CHNAME(tr.getF268_ChName());

		if (tr.getMaskedPan() != null) {
			String pan = tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);

			if (pan.contains("f")) {
				pan = pan.replaceAll("f", "X");
				tr.setMaskedPan(pan);
			} else {
				tr.setMaskedPan(pan);
			}

		} else {
			tr.setMaskedPan("NA");
		}

		data = (String) request.getSession(true).getAttribute("reverseDescriptioSession");

		logger.info("Reverse Description ::::::::::::  :" + data);

		if (data != null) {
			model.addAttribute("responseData", data + "...  Try Again..");
		}

//		txnDet.setMerchantId(merchant.getId());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", tr);

		request.getSession(true).removeAttribute("reverseDescriptioSession");
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/umAuthReverseByMerchant" }, method = RequestMethod.POST)
	public String authReverseTransaction(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("umAuthReverseByMerchant" + TxnDet.getF263_MRN());

		logger.info("umAuthReverseByMerchant Status:::::::::::" + TxnDet.getStatus());

		ResponseDetails data = MotoPaymentCommunication.UmAuthReverse(TxnDet);

		if (data != null) {
			if (data.getResponseCode().equals("0001")) {

				logger.info("fail 01 :::::::::::");

				/*
				 * PageBean pageBean = new PageBean("transactions list",
				 * "transaction/voidpayment/umEzyAuthReverse", Module.TRANSACTION,
				 * "transaction/sideMenuTransaction"); model.addAttribute("pageBean", pageBean);
				 * model.addAttribute("txnDet", TxnDet); model.addAttribute("responseData",
				 * data.getResponseDescription()+"...  Try Again..");
				 */

				request.getSession(true).setAttribute("reverseDescriptioSession", data.getResponseDescription());

				return "redirect:" + URL_BASE + "/ezyauthReverseDetails/" + TxnDet.getF263_MRN();

			} else {

				PageBean pageBean = new PageBean("transactions list",
						"merchantweb/transaction/voidpayment/umEzyAuthDone", Module.TRANSACTION_WEB,
						"merchantweb/transaction/sideMenuTransaction");

				double amount = 0;
				amount = Double.parseDouble(data.getVoidResp().getF007_TxnAmt()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				String rd = "";
				String rt = "";
				try {
					rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyyMMdd").parse(data.getVoidResp().getH003_TDT()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(data.getVoidResp().getH004_TTM()));

				} catch (ParseException e) {
					e.printStackTrace();
				}

				model.addAttribute("pageBean", pageBean);
				model.addAttribute("amount", output);
				model.addAttribute("TDT", " " + rd + " " + rt);
				model.addAttribute("CHNumber", data.getVoidResp().getF004_PAN());
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			logger.info("fail 02 :::::::::::");

			/*
			 * PageBean pageBean = new PageBean("transactions list",
			 * "transaction/voidpayment/umEzyAuthReverse", Module.TRANSACTION,
			 * "transaction/sideMenuTransaction"); model.addAttribute("pageBean", pageBean);
			 * model.addAttribute("txnDet", TxnDet); model.addAttribute("responseData",
			 * data.getResponseDescription()+"...  Try Again..");
			 */

			request.getSession(true).setAttribute("reverseDescriptioSession", "Failed , Try again...");

			return "redirect:" + URL_BASE + "/ezyauthReverseDetails/" + TxnDet.getF263_MRN();
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/allMerchantUMlist/{currPage}" }, method = RequestMethod.GET)
	public String displayAllUMTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false) final String date, @RequestParam(required = false) final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("about to list all  transaction");

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/UmAlltransactionList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		logger.info(" All Transaction Summary:" + principal.getName());

		logger.info("list umMotoList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String fromDate1 = null;
		String toDate1 = null;

		if ((date != null && date1 != null) && (!date.isEmpty() && !date1.isEmpty())) {
			try {
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date1));
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		model.addAttribute("pageBean", pageBean);

		transactionService.listAllUmTransactionDetails(paginationBean, currentMerchant, fromDate1, toDate1);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/allUmSearch1" }, method = RequestMethod.GET)
	public String displayAllUmTransactionList(final Model model, @RequestParam(required = false) final String date,
			@RequestParam(required = false) final String date1, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("all  Transaction in search controller");
		logger.info("inside search controler " + date + " " + date1);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		String dat = null;
		String dat1 = null;
		String status1 = null;

		if ((date != null && date1 != null) && (!date.isEmpty() && !date1.isEmpty())) {
			try {
				dat = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				dat1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date1));
				logger.info("inside search controler " + dat + " " + dat1);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/UmAlltransactionList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		transactionService.listAllUmTransactionDetails(paginationBean, currentMerchant, dat, dat1);

		model.addAttribute("date", dat);
		model.addAttribute("date1", dat1);
		model.addAttribute("status", status1);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/allUmExport" }, method = RequestMethod.GET)
	public ModelAndView getAllUmExcel(@RequestParam(required = false) final String date,
			@RequestParam(required = false) final String date1, HttpServletRequest request,
			@RequestParam(required = false) String export, final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("about to list all  Transaction export");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String fromDate1 = null;
		String toDate1 = null;

		if ((date != null && date1 != null) && (!date.isEmpty() && !date1.isEmpty())) {

			try {
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date1));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllUmTransactionDetails(paginationBean, currentMerchant, fromDate1, toDate1);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("txnUmListExcel", "txnList", list);

		} else {
			return new ModelAndView("txnUmListPdf", "txnList", list);
		}

	}

	@RequestMapping(value = { "/ezyLinkSSList" }, method = RequestMethod.GET)
	public String umEzyLinkList(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list umLinkList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);

		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMLinkSSList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-Link Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMEzyLinkSSTransaction(paginationBean, null, null,
				currentMerchant.getMid().getUmSsMotoMid(), "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchLinkSS" }, method = RequestMethod.GET)
	public String searchUMLinkss(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("search Ezylink SS Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMLinkSSList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMEzyLinkSSTransaction(paginationBean, date, date1,
				currentMerchant.getMid().getUmSsMotoMid(), "ALL");

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/ezyLinkSSExport", method = RequestMethod.GET)
	public ModelAndView umLinkExportss(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//					@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("UM_EZYLINK ss Export by Merchant");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMLinkSSTransaction(paginationBean, dat, dat1,
				currentMerchant.getMid().getUmSsMotoMid(), "ALL");

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListMobiliteUMExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListMobiliteUMPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = { "/getTrackData/{id}" }, method = RequestMethod.GET)
	public String displaytrackList(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("Merchant", "merchantweb/getTrackData", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		UMEcomTxnRequest trreq = transactionService.loadUMEzywayTransactionRequest(id);

		UMEcomTxnResponse tr = transactionService.loadUMEzywayTransactionResponse(id);

		Merchant currentMerchant = merchantService.loadMerchant(myName);
		MobiliteTrackDetails trackDet = new MobiliteTrackDetails();

		if (tr.getF007_TxnAmt() != null) {
			double amount = 0;
			amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100;
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(amount);
			trackDet.setAmount(output);

			logger.info("output: " + output);
		}

		trackDet.setBusinessName(currentMerchant.getBusinessName());
		trackDet.setTid(trreq.getF354_TID());
		trackDet.setCustomerName(trreq.getF268_ChName());
		trackDet.setCustomerPhoneNo(trreq.getF279_HP());
		trackDet.setMrn(tr.getF263_MRN());

		model.addAttribute("trackDet", trackDet);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/sendTrackData" }, method = RequestMethod.POST)
	public String confirmAddAgent(@ModelAttribute("trackDet") final MobiliteTrackDetails trackDet, final Model model,
			final java.security.Principal principal, final HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to sendTrackData Confirms");
		String responseMsg = null;
		PageBean pageBean = new PageBean("Merchant", "merchantweb/smsSuccess", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);

		logger.info("sendTrackData");
		PaginationBean<MobiliteTrackDetails> paginationBean = new PaginationBean<MobiliteTrackDetails>();
		paginationBean.setCurrPage(currPage);

		model.addAttribute("paginationBean", paginationBean);

		logger.info("trackdet::" + trackDet.getTrackNumber() + "::::" + trackDet.getAmount() + "::::"
				+ trackDet.getCourierName() + ":::::" + trackDet.getSentDate() + "::::" + trackDet.getCustomerPhoneNo()
				+ "::::" + trackDet.getMrn());

		/*
		 * trackDet.setAmount("10"); trackDet.setBusinessName("Mobi");
		 * trackDet.setTrackNumber("45678");
		 * trackDet.setCourierName("ProfessionalCourier");
		 * trackDet.setSentDate("20201120"); trackDet.setCustomerPhoneNo("0105302913");
		 */

		logger.info("trackdet" + trackDet.getTrackNumber());

		int a = merchantService.updateEzylinkssTrackDetails(trackDet);

		String smsBody = null;

		smsBody = "Click the link to confirm the service of goods : "
				+ PropertyLoad.getFile().getProperty("RECEIPT_SMSLINK") + trackDet.getBusinessName() + "&trackNo="
				+ trackDet.getTrackNumber() + "&courierName=" + trackDet.getCourierName() + "&sentDate="
				+ trackDet.getSentDate() + "&amount=" + trackDet.getAmount();

		logger.info("smsBody::" + smsBody);

		if (!(trackDet.getCustomerPhoneNo() == null || trackDet.getCustomerPhoneNo().equals(""))) {
			if (trackDet.getCustomerPhoneNo().length() > 7) {
				try {
					boolean recept = false;
					if (!recept) {
						SMSServiceImpl.sendSMS(trackDet.getCustomerPhoneNo(), smsBody);
						recept = true;
						responseMsg = " Message Sent Succesfully to the mobile number :"
								+ trackDet.getCustomerPhoneNo();
						logger.info("Message Sent Succesfully to the " + "mobile number :"
								+ trackDet.getCustomerPhoneNo() + " and the " + "Message Body is:" + smsBody);
					}
				} catch (Exception e) {

					logger.info("Message Not Sent to mobile number :" + trackDet.getCustomerPhoneNo());
					responseMsg = " Message Not Sent to mobile number :" + trackDet.getCustomerPhoneNo()
							+ "with exception: " + e;
					// throw new MobileApiException(Status.SMS_SENDING_FAILED);
				}
			} else {

				logger.info("Message Not Sent to mobile number :" + trackDet.getCustomerPhoneNo());
				responseMsg = " Message Not Sent to mobile number :" + trackDet.getCustomerPhoneNo();

			}
		}

		model.addAttribute("trackDet", trackDet);
		model.addAttribute("responsemsg", responseMsg);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/refundEzylinkPayment/{id}" }, method = RequestMethod.GET)
	public String refundEzylinkPayment(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list",
				"merchantweb/transaction/voidpayment/umEzylinkRefundPayment", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		logger.info("refundEzylinkPayment");

		UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

		String rd = null;
		if (tr.getH003_TDT() != null) {

			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyyMMdd").parse(tr.getH003_TDT()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String rt = null;
		if (tr.getH004_TTM() != null) {
			try {
				rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(tr.getH004_TTM()));

			} catch (ParseException e) {
			}
		}
		tr.setH003_TDT(rd);
		tr.setH004_TTM(rt);
		tr.setF247_OrgTxnAmt(tr.getF007_TxnAmt());
		double amount = 0;
		amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		tr.setF007_TxnAmt(output);

//		txnDet.setF263_MRN(tr.getF263_MRN());
//		txnDet.setF268_CHNAME(tr.getF268_ChName());

		if (tr.getMaskedPan() != null) {
			String pan = tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);

			if (pan.contains("f")) {
				pan = pan.replaceAll("f", "X");
				tr.setMaskedPan(pan);
			} else {
				tr.setMaskedPan(pan);
			}

		} else {
			tr.setMaskedPan("NA");
		}

//		txnDet.setMerchantId(merchant.getId());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", tr);

		logger.info("MID::" + tr.getF001_MID());
		MobiMDR tr1 = transactionService.loadMobiMdr(tr.getF001_MID());

		String from = null;
		String to = null;

		Date dt = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		from = dateFormat.format(dt);
		String from1 = from.substring(0, from.length() - 2);

		Date dt1 = new Date();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		to = dateFormat1.format(dt1);
		String to1 = to.substring(0, to.length() - 2);

		if (tr1.getSettlePeriod().equalsIgnoreCase("3")) {
			logger.info("T+" + tr1.getSettlePeriod());
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("From date :" + from);

			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("To date:" + to);

		} else if (tr1.getSettlePeriod().equalsIgnoreCase("5")) {

			logger.info("T+" + tr1.getSettlePeriod());
			from = from1 + String.format("%02d", -3 + Integer.valueOf(from.substring(8, 10)));
			logger.info("From date :" + from);

			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("To date:" + to);

		} else {

			logger.info("T+" + tr1.getSettlePeriod());
			from = from1 + String.format("%02d", 0 + Integer.valueOf(from.substring(8, 10)));
			logger.info("From date :" + from);

			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("To date:" + to);

		}

		String totalAmount = transactionService.totalSettleAmount(from, to, tr.getF001_MID());

		double totAmount = 0;
		totAmount = Double.parseDouble(totalAmount) / 100;

		logger.info("Total Amount" + totAmount);
		logger.info("Refund Amount" + amount);

		if (totAmount <= amount) {

			logger.info("Unable to initiate Refund, due to lesser settlement amount");

			model.addAttribute("responseData", "Unable to initiate Refund, due to lesser settlement amount!!");
			model.addAttribute("buttonDisable", "Yes");
		} else {
			logger.info("Optained TID :" + tr.getF354_TID());
			MobileUser mu = mobileUserService.loadMobileUserByTid(tr.getF354_TID());
			logger.info("Mobi Username :" + mu.getUsername());
			logger.info("Merchant Username :" + mu.getMerchant().getUsername());
			Merchant merchant = merchantService.validateMerchantUserName(mu.getMerchant().getUsername());

			if (merchant != null) {

				Request inData = new Request();

				if (merchant.getBusinessContactNumber() != null) {
					inData.setMobileNo(merchant.getBusinessContactNumber());
					logger.info("Mobile No" + inData.getMobileNo());
				} else {
					inData.setMobileNo("");
				}

				if (merchant.getEmail() != null) {
					inData.setEmail(merchant.getEmail());
					logger.info("Email " + inData.getEmail());
				} else {
					inData.setEmail("");
				}

				if (merchant.getSalutation() != null) {
					inData.setSalutation(merchant.getSalutation());
					logger.info("Salutation  " + inData.getEmail());
				} else {
					inData.setSalutation("Mr/Ms");
				}

				if (merchant.getContactPersonName() != null) {
					inData.setFirstName(merchant.getContactPersonName());
				} else {
					inData.setFirstName("");
				}

				inData.setService("WEB_REQ_OTP");
				inData.setUsername(merchant.getUsername());

				ResponseDetails data = MotoPaymentCommunication.RefundOTP(inData);
				String username = null;
				String mobileNo = null;
				String email = null;
				if (data.getResponseCode().equals("0000")) {
					logger.info("Success");
					logger.info("UserName :" + data.getResponseData().getUsername());
					logger.info("Email    :" + data.getResponseData().getEmail());
					logger.info("Mobile no:" + data.getResponseData().getMobileNo());

					if (data.getResponseData().getUsername() != null) {
						username = data.getResponseData().getUsername();
					} else {
						username = "";
					}
					if (data.getResponseData().getMobileNo() != null) {
						mobileNo = data.getResponseData().getMobileNo();
					} else {
						mobileNo = "";
					}
					if (data.getResponseData().getEmail() != null) {
						email = data.getResponseData().getEmail();
					} else {
						email = "";
					}

					model.addAttribute("username", username);
					model.addAttribute("mobileNo", mobileNo);
					model.addAttribute("email", email);

				}
			}

		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/refundEzywayPayment/{id}" }, method = RequestMethod.GET)
	public String refundEzywayPayment(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list",
				"merchantweb/transaction/voidpayment/umEzywayRefundPayment", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		logger.info("refundEzywayPayment");

		UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

		String rd = null;
		if (tr.getH003_TDT() != null) {

			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyyMMdd").parse(tr.getH003_TDT()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String rt = null;
		if (tr.getH004_TTM() != null) {
			try {
				rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(tr.getH004_TTM()));

			} catch (ParseException e) {
			}
		}
		tr.setH003_TDT(rd);
		tr.setH004_TTM(rt);
		tr.setF247_OrgTxnAmt(tr.getF007_TxnAmt());
		double amount = 0;
		amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		tr.setF007_TxnAmt(output);

//		txnDet.setF263_MRN(tr.getF263_MRN());
//		txnDet.setF268_CHNAME(tr.getF268_ChName());

		if (tr.getMaskedPan() != null) {
			String pan = tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);

			if (pan.contains("f")) {
				pan = pan.replaceAll("f", "X");
				tr.setMaskedPan(pan);
			} else {
				tr.setMaskedPan(pan);
			}

		} else {
			tr.setMaskedPan("NA");
		}

//		txnDet.setMerchantId(merchant.getId());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", tr);

		logger.info("MID::" + tr.getF001_MID());
		MobiMDR tr1 = transactionService.loadMobiMdr(tr.getF001_MID());

		String from = null;
		String to = null;

		Date dt = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		from = dateFormat.format(dt);
		String from1 = from.substring(0, from.length() - 2);

		Date dt1 = new Date();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		to = dateFormat1.format(dt1);
		String to1 = to.substring(0, to.length() - 2);

		if (tr1.getSettlePeriod().equalsIgnoreCase("3")) {
			logger.info("T+" + tr1.getSettlePeriod());
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("From date :" + from);

			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("To date:" + to);

		} else if (tr1.getSettlePeriod().equalsIgnoreCase("5")) {

			logger.info("T+" + tr1.getSettlePeriod());
			from = from1 + String.format("%02d", -3 + Integer.valueOf(from.substring(8, 10)));
			logger.info("From date :" + from);

			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("To date:" + to);

		} else {

			logger.info("T+" + tr1.getSettlePeriod());
			from = from1 + String.format("%02d", 0 + Integer.valueOf(from.substring(8, 10)));
			logger.info("From date :" + from);

			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("To date:" + to);

		}

		String totalAmount = transactionService.totalSettleAmount(from, to, tr.getF001_MID());

		double totAmount = 0;
		totAmount = Double.parseDouble(totalAmount) / 100;

		logger.info("Total Amount" + totAmount);
		logger.info("Refund Amount" + amount);

		if (totAmount <= amount) {

			logger.info("Unable to initiate Refund, due to lesser settlement amount");

			model.addAttribute("responseData", "Unable to initiate Refund, due to lesser settlement amount!!");
			model.addAttribute("buttonDisable", "Yes");
		} else {
			logger.info("Optained TID :" + tr.getF354_TID());
			MobileUser mu = mobileUserService.loadMobileUserByTid(tr.getF354_TID());
			logger.info("Mobi Username :" + mu.getUsername());
			logger.info("Merchant Username :" + mu.getMerchant().getUsername());
			Merchant merchant = merchantService.validateMerchantUserName(mu.getMerchant().getUsername());

			if (merchant != null) {

				Request inData = new Request();

				if (merchant.getBusinessContactNumber() != null) {
					inData.setMobileNo(merchant.getBusinessContactNumber());
					logger.info("Mobile No" + inData.getMobileNo());
				} else {
					inData.setMobileNo("");
				}

				if (merchant.getEmail() != null) {
					inData.setEmail(merchant.getEmail());
					logger.info("Email " + inData.getEmail());
				} else {
					inData.setEmail("");
				}

				if (merchant.getSalutation() != null) {
					inData.setSalutation(merchant.getSalutation());
					logger.info("Salutation  " + inData.getEmail());
				} else {
					inData.setSalutation("Mr/Ms");
				}

				if (merchant.getContactPersonName() != null) {
					inData.setFirstName(merchant.getContactPersonName());
				} else {
					inData.setFirstName("");
				}

				inData.setService("WEB_REQ_OTP");
				inData.setUsername(merchant.getUsername());

				ResponseDetails data = MotoPaymentCommunication.RefundOTP(inData);
				String username = null;
				String mobileNo = null;
				String email = null;
				if (data.getResponseCode().equals("0000")) {
					logger.info("Success");
					logger.info("UserName :" + data.getResponseData().getUsername());
					logger.info("Email    :" + data.getResponseData().getEmail());
					logger.info("Mobile no:" + data.getResponseData().getMobileNo());

					if (data.getResponseData().getUsername() != null) {
						username = data.getResponseData().getUsername();
					} else {
						username = "";
					}
					if (data.getResponseData().getMobileNo() != null) {
						mobileNo = data.getResponseData().getMobileNo();
					} else {
						mobileNo = "";
					}
					if (data.getResponseData().getEmail() != null) {
						email = data.getResponseData().getEmail();
					} else {
						email = "";
					}

					model.addAttribute("username", username);
					model.addAttribute("mobileNo", mobileNo);
					model.addAttribute("email", email);

				}
			}

		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/refundEzyrecPayment/{id}" }, method = RequestMethod.GET)
	public String refundEzyrecPayment(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list",
				"merchantweb/transaction/voidpayment/umEzyrecRefundPayment", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		logger.info("refundEzyrecPayment");

		UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

		String rd = null;
		if (tr.getH003_TDT() != null) {

			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyyMMdd").parse(tr.getH003_TDT()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String rt = null;
		if (tr.getH004_TTM() != null) {
			try {
				rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(tr.getH004_TTM()));

			} catch (ParseException e) {
			}
		}
		tr.setH003_TDT(rd);
		tr.setH004_TTM(rt);
		tr.setF247_OrgTxnAmt(tr.getF007_TxnAmt());
		double amount = 0;
		amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		tr.setF007_TxnAmt(output);

//		txnDet.setF263_MRN(tr.getF263_MRN());
//		txnDet.setF268_CHNAME(tr.getF268_ChName());

		if (tr.getMaskedPan() != null) {
			String pan = tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);

			if (pan.contains("f")) {
				pan = pan.replaceAll("f", "X");
				tr.setMaskedPan(pan);
			} else {
				tr.setMaskedPan(pan);
			}

		} else {
			tr.setMaskedPan("NA");
		}

//		txnDet.setMerchantId(merchant.getId());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", tr);

		logger.info("MID::" + tr.getF001_MID());
		MobiMDR tr1 = transactionService.loadMobiMdr(tr.getF001_MID());

		String from = null;
		String to = null;

		Date dt = new Date();

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		from = dateFormat.format(dt);
		String from1 = from.substring(0, from.length() - 2);

		Date dt1 = new Date();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		to = dateFormat1.format(dt1);
		String to1 = to.substring(0, to.length() - 2);

		if (tr1.getSettlePeriod().equalsIgnoreCase("3")) {
			logger.info("T+" + tr1.getSettlePeriod());
			from = from1 + String.format("%02d", -1 + Integer.valueOf(from.substring(8, 10)));
			logger.info("From date :" + from);

			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("To date:" + to);

		} else if (tr1.getSettlePeriod().equalsIgnoreCase("5")) {

			logger.info("T+" + tr1.getSettlePeriod());
			from = from1 + String.format("%02d", -3 + Integer.valueOf(from.substring(8, 10)));
			logger.info("From date :" + from);

			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("To date:" + to);

		} else {

			logger.info("T+" + tr1.getSettlePeriod());
			from = from1 + String.format("%02d", 0 + Integer.valueOf(from.substring(8, 10)));
			logger.info("From date :" + from);

			to = to1 + String.format("%02d", 1 + Integer.valueOf(to.substring(8, 10)));
			logger.info("To date:" + to);

		}

		String totalAmount = transactionService.totalSettleAmount(from, to, tr.getF001_MID());

		double totAmount = 0;
		totAmount = Double.parseDouble(totalAmount) / 100;

		logger.info("Total Amount" + totAmount);
		logger.info("Refund Amount" + amount);

		if (totAmount <= amount) {

			logger.info("Unable to initiate Refund, due to lesser settlement amount");

			model.addAttribute("responseData", "Unable to initiate Refund, due to lesser settlement amount!!");
			model.addAttribute("buttonDisable", "Yes");
		} else {
			logger.info("Optained TID :" + tr.getF354_TID());
			MobileUser mu = mobileUserService.loadMobileUserByTid(tr.getF354_TID());
			logger.info("Mobi Username :" + mu.getUsername());
			logger.info("Merchant Username :" + mu.getMerchant().getUsername());
			Merchant merchant = merchantService.validateMerchantUserName(mu.getMerchant().getUsername());

			if (merchant != null) {

				Request inData = new Request();

				if (merchant.getBusinessContactNumber() != null) {
					inData.setMobileNo(merchant.getBusinessContactNumber());
					logger.info("Mobile No" + inData.getMobileNo());
				} else {
					inData.setMobileNo("");
				}

				if (merchant.getEmail() != null) {
					inData.setEmail(merchant.getEmail());
					logger.info("Email " + inData.getEmail());
				} else {
					inData.setEmail("");
				}

				if (merchant.getSalutation() != null) {
					inData.setSalutation(merchant.getSalutation());
					logger.info("Salutation  " + inData.getEmail());
				} else {
					inData.setSalutation("Mr/Ms");
				}

				if (merchant.getContactPersonName() != null) {
					inData.setFirstName(merchant.getContactPersonName());
				} else {
					inData.setFirstName("");
				}

				inData.setService("WEB_REQ_OTP");
				inData.setUsername(merchant.getUsername());

				ResponseDetails data = MotoPaymentCommunication.RefundOTP(inData);
				String username = null;
				String mobileNo = null;
				String email = null;
				if (data.getResponseCode().equals("0000")) {
					logger.info("Success");
					logger.info("UserName :" + data.getResponseData().getUsername());
					logger.info("Email    :" + data.getResponseData().getEmail());
					logger.info("Mobile no:" + data.getResponseData().getMobileNo());

					if (data.getResponseData().getUsername() != null) {
						username = data.getResponseData().getUsername();
					} else {
						username = "";
					}
					if (data.getResponseData().getMobileNo() != null) {
						mobileNo = data.getResponseData().getMobileNo();
					} else {
						mobileNo = "";
					}
					if (data.getResponseData().getEmail() != null) {
						email = data.getResponseData().getEmail();
					} else {
						email = "";
					}

					model.addAttribute("username", username);
					model.addAttribute("mobileNo", mobileNo);
					model.addAttribute("email", email);

				}
			}

		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/refundEzylinkPaymentByAdmin1" }, method = RequestMethod.POST)
	public String refundEzylinkPaymentByAdmin1(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("refundEzylinkPaymentByAdmin1");

		if (TxnDet.getF279_HP() == null || TxnDet.getF279_HP().trim().isEmpty() || TxnDet.getF278_EmailAddr() == null
				|| TxnDet.getF278_EmailAddr().isEmpty()) {
			PageBean pageBean = new PageBean("transactions list",
					"merchantweb/transaction/voidpayment/umEzylinkRefundPayment", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", "InValid mobile number or email ...  Try Again..");

		}

		if (TxnDet.getF277_DDRN() == null || TxnDet.getF277_DDRN().trim().isEmpty()) {
			PageBean pageBean = new PageBean("transactions list",
					"merchantweb/transaction/voidpayment/umEzylinkRefundPayment", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", "InValid OTP ...  Try Again..");
		}

		logger.info("Mobile ::::::::" + TxnDet.getF279_HP());
		logger.info("Email  ::::::::" + TxnDet.getF278_EmailAddr());
		logger.info("OTP    ::::::::" + TxnDet.getF277_DDRN());

		String mno = TxnDet.getF279_HP();
		String email = TxnDet.getF278_EmailAddr();
		String otp = TxnDet.getF277_DDRN();

		MobileOTP otp1 = merchantService.checkOTP(mno, email);

		if (otp1 != null) {

			logger.info("Optained OTP : " + otp1.getOptData());

			if (otp1.getOptData().equalsIgnoreCase(otp)) {
				logger.info("Valid OTP");

				ResponseDetails data = MotoPaymentCommunication.UmRefundPayment(TxnDet);

				double amount = 0;
				amount = Double.parseDouble(data.getVoidResp().getF007_TxnAmt()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				String rd = "";
				String rt = "";
				try {
					rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyyMMdd").parse(data.getVoidResp().getH003_TDT()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(data.getVoidResp().getH004_TTM()));

				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (data != null) {
					if (data.getResponseCode().equals("0001")) {
						PageBean pageBean = new PageBean("transactions list",
								"merchantweb/transaction/voidpayment/umEzylinkRefundPayment", Module.TRANSACTION_WEB,
								"merchantweb/transaction/sideMenuTransaction");
						model.addAttribute("pageBean", pageBean);
						model.addAttribute("txnDet", TxnDet);
						model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
					} else {

						try {

							List<NameValuePair> headers = new ArrayList<NameValuePair>();
							headers.add(new NameValuePair("HEADER", "test"));
							String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");
							String apiKey = PropertyLoad.getFile().getProperty("APIKEY");
							String toAddress = PropertyLoad.getFile().getProperty("REFUND_MAIL_TO");
							String bccAddress = PropertyLoad.getFile().getProperty("REFUND_MAIL_BCC");
							logger.info("To Email : " + toAddress);
							logger.info("Bcc Email : " + bccAddress);
							String subject = "REFUND SUCCESSS - " + data.getVoidResp().getF001_MID();// set

							String rd1 = null;
							if (data.getVoidResp().getH003_TDT() != null) {

								try {
									rd1 = new SimpleDateFormat("dd-MMM-yyyy").format(
											new SimpleDateFormat("yyyyMMdd").parse(data.getVoidResp().getH003_TDT()));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							double amount1 = 0;
							amount1 = Double.parseDouble(data.getVoidResp().getF007_TxnAmt()) / 100;
							String pattern1 = "#,##0.00";
							DecimalFormat myFormatter1 = new DecimalFormat(pattern1);
							String refundAmount = myFormatter.format(amount1);

							TempletFields tempField = new TempletFields();

							tempField.setMid(data.getVoidResp().getF001_MID());
//								tempField.setRefNo(data.getVoidResp().getF011_AuthIDResp());
							tempField.setMaskedPan(data.getVoidResp().getF004_PAN());
							tempField.setDate(rd1);
							tempField.setAmount(refundAmount);

							logger.info("MID : " + tempField.getMID());
//								logger.info("Ref No : " + tempField.getRefNo());
							logger.info("Masked Pan : " + tempField.getMaskedPan());
							logger.info("Date : " + tempField.getDate());
							logger.info("Amount : " + tempField.getAmount());

							String emailBody = EmailTemplet.sentRefundTempletContent(tempField);

							Attachment logo = new Attachment("mobiversa_logo.png", "image/png",
									PropertyLoad.getFile().getProperty("REFUND_IMG"), "cid:otp_logo");

							List<Attachment> attachments = new ArrayList<Attachment>();
							attachments.add(logo);

							/*
							 * PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress,
							 * fromAddress, ccMail, bccMail, subject, emailBody, true, "test-email", null,
							 * attachments);
							 */
//								PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress,
//										fromAddress, null,subject, emailBody, true, "test-email",
//										null, attachments); 
							PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress, fromAddress,
									bccAddress, bccAddress, subject, emailBody, true, "test-email", null, attachments);
							PostmarkClient client = new PostmarkClient(apiKey);

							try {
								client.sendMessage(message);
								System.out.println("Email Sent Successfully to" + toAddress);
							} catch (PostmarkException pe) {
								System.out.println("Invalid Signature Base64 String");

							}

						} catch (Exception e) {
							// TODO: handle exception
						}

						PageBean pageBean = new PageBean("transactions list",
								"merchantweb/transaction/voidpayment/umEzylinkRefundPaymentDone",
								Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
						model.addAttribute("pageBean", pageBean);
						model.addAttribute("amount", output);
						model.addAttribute("TDT", " " + rd + " " + rt);
						model.addAttribute("CHNumber", data.getVoidResp().getF004_PAN());
						model.addAttribute("responseData", data.getResponseDescription());
					}
				} else {
					PageBean pageBean = new PageBean("transactions list",
							"merchantweb/transaction/voidpayment/umEzylinkRefundPayment", Module.TRANSACTION_WEB,
							"merchantweb/transaction/sideMenuTransaction");
					model.addAttribute("pageBean", pageBean);
					model.addAttribute("txnDet", TxnDet);
					model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
				}

			} else {
				PageBean pageBean = new PageBean("transactions list",
						"merchantweb/transaction/voidpayment/umEzylinkRefundPayment", Module.TRANSACTION_WEB,
						"merchantweb/transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", TxnDet);
				model.addAttribute("responseData", "InValid OTP...  Try Again..");
			}

		} else {
			logger.info("OTP Not exist for the Mobile Number ");
			PageBean pageBean = new PageBean("transactions list",
					"merchantweb/transaction/voidpayment/umEzylinkRefundPayment", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", "OTP Not exist for the Mobile Number / Email ...  Try Again..");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/refundEzywayPaymentByAdmin1" }, method = RequestMethod.POST)
	public String refundEzywayPaymentByAdmin1(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("refundEzywayPaymentByAdmin1");

		if (TxnDet.getF279_HP() == null || TxnDet.getF279_HP().trim().isEmpty() || TxnDet.getF278_EmailAddr() == null
				|| TxnDet.getF278_EmailAddr().isEmpty()) {
			PageBean pageBean = new PageBean("transactions list",
					"merchantweb/transaction/voidpayment/umEzywayRefundPayment", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", "InValid mobile number or email ...  Try Again..");

		}

		if (TxnDet.getF277_DDRN() == null || TxnDet.getF277_DDRN().trim().isEmpty()) {
			PageBean pageBean = new PageBean("transactions list",
					"merchantweb/transaction/voidpayment/umEzywayRefundPayment", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", "InValid OTP ...  Try Again..");
		}

		logger.info("Mobile ::::::::" + TxnDet.getF279_HP());
		logger.info("Email  ::::::::" + TxnDet.getF278_EmailAddr());
		logger.info("OTP    ::::::::" + TxnDet.getF277_DDRN());

		String mno = TxnDet.getF279_HP();
		String email = TxnDet.getF278_EmailAddr();
		String otp = TxnDet.getF277_DDRN();

		MobileOTP otp1 = merchantService.checkOTP(mno, email);

		if (otp1 != null) {

			logger.info("Optained OTP : " + otp1.getOptData());

			if (otp1.getOptData().equalsIgnoreCase(otp)) {
				logger.info("Valid OTP");

				ResponseDetails data = MotoPaymentCommunication.UmRefundPayment(TxnDet);

				double amount = 0;
				amount = Double.parseDouble(data.getVoidResp().getF007_TxnAmt()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				String rd = "";
				String rt = "";
				try {
					rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyyMMdd").parse(data.getVoidResp().getH003_TDT()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(data.getVoidResp().getH004_TTM()));

				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (data != null) {
					if (data.getResponseCode().equals("0001")) {
						PageBean pageBean = new PageBean("transactions list",
								"merchantweb/transaction/voidpayment/umEzylinkRefundPayment", Module.TRANSACTION_WEB,
								"merchantweb/transaction/sideMenuTransaction");
						model.addAttribute("pageBean", pageBean);
						model.addAttribute("txnDet", TxnDet);
						model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
					} else {

						try {

							List<NameValuePair> headers = new ArrayList<NameValuePair>();
							headers.add(new NameValuePair("HEADER", "test"));
							String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");
							String apiKey = PropertyLoad.getFile().getProperty("APIKEY");
							String toAddress = PropertyLoad.getFile().getProperty("REFUND_MAIL_TO");
							String bccAddress = PropertyLoad.getFile().getProperty("REFUND_MAIL_BCC");
							logger.info("To Email : " + toAddress);
							logger.info("Bcc Email : " + bccAddress);
							String subject = "REFUND SUCCESSS - " + data.getVoidResp().getF001_MID();// set

							String rd1 = null;
							if (data.getVoidResp().getH003_TDT() != null) {

								try {
									rd1 = new SimpleDateFormat("dd-MMM-yyyy").format(
											new SimpleDateFormat("yyyyMMdd").parse(data.getVoidResp().getH003_TDT()));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							double amount1 = 0;
							amount1 = Double.parseDouble(data.getVoidResp().getF007_TxnAmt()) / 100;
							String pattern1 = "#,##0.00";
							DecimalFormat myFormatter1 = new DecimalFormat(pattern1);
							String refundAmount = myFormatter.format(amount1);

							TempletFields tempField = new TempletFields();

							tempField.setMid(data.getVoidResp().getF001_MID());
//								tempField.setRefNo(data.getVoidResp().getF011_AuthIDResp());
							tempField.setMaskedPan(data.getVoidResp().getF004_PAN());
							tempField.setDate(rd1);
							tempField.setAmount(refundAmount);

							logger.info("MID : " + tempField.getMID());
//								logger.info("Ref No : " + tempField.getRefNo());
							logger.info("Masked Pan : " + tempField.getMaskedPan());
							logger.info("Date : " + tempField.getDate());
							logger.info("Amount : " + tempField.getAmount());

							String emailBody = EmailTemplet.sentRefundTempletContent(tempField);

							Attachment logo = new Attachment("mobiversa_logo.png", "image/png",
									PropertyLoad.getFile().getProperty("REFUND_IMG"), "cid:otp_logo");

							List<Attachment> attachments = new ArrayList<Attachment>();
							attachments.add(logo);

							/*
							 * PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress,
							 * fromAddress, ccMail, bccMail, subject, emailBody, true, "test-email", null,
							 * attachments);
							 */
//								PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress,
//										fromAddress, null,subject, emailBody, true, "test-email",
//										null, attachments); 
							PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress, fromAddress,
									bccAddress, bccAddress, subject, emailBody, true, "test-email", null, attachments);
							PostmarkClient client = new PostmarkClient(apiKey);

							try {
								client.sendMessage(message);
								System.out.println("Email Sent Successfully to" + toAddress);
							} catch (PostmarkException pe) {
								System.out.println("Invalid Signature Base64 String");

							}

						} catch (Exception e) {
							// TODO: handle exception
						}

						PageBean pageBean = new PageBean("transactions list",
								"merchantweb/transaction/voidpayment/umEzywayRefundPaymentDone", Module.TRANSACTION_WEB,
								"merchantweb/transaction/sideMenuTransaction");
						model.addAttribute("pageBean", pageBean);
						model.addAttribute("amount", output);
						model.addAttribute("TDT", " " + rd + " " + rt);
						model.addAttribute("CHNumber", data.getVoidResp().getF004_PAN());
						model.addAttribute("responseData", data.getResponseDescription());
					}
				} else {
					PageBean pageBean = new PageBean("transactions list",
							"merchantweb/transaction/voidpayment/umEzywayRefundPayment", Module.TRANSACTION_WEB,
							"merchantweb/transaction/sideMenuTransaction");
					model.addAttribute("pageBean", pageBean);
					model.addAttribute("txnDet", TxnDet);
					model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
				}

			} else {
				PageBean pageBean = new PageBean("transactions list",
						"merchantweb/transaction/voidpayment/umEzywayRefundPayment", Module.TRANSACTION_WEB,
						"merchantweb/transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", TxnDet);
				model.addAttribute("responseData", "InValid OTP...  Try Again..");
			}

		} else {
			logger.info("OTP Not exist for the Mobile Number ");
			PageBean pageBean = new PageBean("transactions list",
					"merchantweb/transaction/voidpayment/umEzywayRefundPayment", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", "OTP Not exist for the Mobile Number / Email ...  Try Again..");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/refundEzyrecPaymentByAdmin1" }, method = RequestMethod.POST)
	public String refundEzyrecPaymentByAdmin1(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("refundEzywayPaymentByAdmin1");

		if (TxnDet.getF279_HP() == null || TxnDet.getF279_HP().trim().isEmpty() || TxnDet.getF278_EmailAddr() == null
				|| TxnDet.getF278_EmailAddr().isEmpty()) {
			PageBean pageBean = new PageBean("transactions list",
					"merchantweb/transaction/voidpayment/umEzyrecRefundPayment", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", "InValid mobile number or email ...  Try Again..");

		}

		if (TxnDet.getF277_DDRN() == null || TxnDet.getF277_DDRN().trim().isEmpty()) {
			PageBean pageBean = new PageBean("transactions list",
					"merchantweb/transaction/voidpayment/umEzyrecRefundPayment", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", "InValid OTP ...  Try Again..");
		}

		logger.info("Mobile ::::::::" + TxnDet.getF279_HP());
		logger.info("Email  ::::::::" + TxnDet.getF278_EmailAddr());
		logger.info("OTP    ::::::::" + TxnDet.getF277_DDRN());

		String mno = TxnDet.getF279_HP();
		String email = TxnDet.getF278_EmailAddr();
		String otp = TxnDet.getF277_DDRN();

		MobileOTP otp1 = merchantService.checkOTP(mno, email);

		if (otp1 != null) {

			logger.info("Optained OTP : " + otp1.getOptData());

			if (otp1.getOptData().equalsIgnoreCase(otp)) {
				logger.info("Valid OTP");

				ResponseDetails data = MotoPaymentCommunication.UmRefundPayment(TxnDet);

				double amount = 0;
				amount = Double.parseDouble(data.getVoidResp().getF007_TxnAmt()) / 100;
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				String rd = "";
				String rt = "";
				try {
					rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyyMMdd").parse(data.getVoidResp().getH003_TDT()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(data.getVoidResp().getH004_TTM()));

				} catch (ParseException e) {
					e.printStackTrace();
				}

				if (data != null) {
					if (data.getResponseCode().equals("0001")) {
						PageBean pageBean = new PageBean("transactions list",
								"merchantweb/transaction/voidpayment/umEzyrecRefundPayment", Module.TRANSACTION_WEB,
								"merchantweb/transaction/sideMenuTransaction");
						model.addAttribute("pageBean", pageBean);
						model.addAttribute("txnDet", TxnDet);
						model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
					} else {

						try {

							List<NameValuePair> headers = new ArrayList<NameValuePair>();
							headers.add(new NameValuePair("HEADER", "test"));
							String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");
							String apiKey = PropertyLoad.getFile().getProperty("APIKEY");
							String toAddress = PropertyLoad.getFile().getProperty("REFUND_MAIL_TO");
							String bccAddress = PropertyLoad.getFile().getProperty("REFUND_MAIL_BCC");
							logger.info("To Email : " + toAddress);
							logger.info("Bcc Email : " + bccAddress);
							String subject = "REFUND SUCCESSS - " + data.getVoidResp().getF001_MID();// set

							String rd1 = null;
							if (data.getVoidResp().getH003_TDT() != null) {

								try {
									rd1 = new SimpleDateFormat("dd-MMM-yyyy").format(
											new SimpleDateFormat("yyyyMMdd").parse(data.getVoidResp().getH003_TDT()));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

							}
							double amount1 = 0;
							amount1 = Double.parseDouble(data.getVoidResp().getF007_TxnAmt()) / 100;
							String pattern1 = "#,##0.00";
							DecimalFormat myFormatter1 = new DecimalFormat(pattern1);
							String refundAmount = myFormatter.format(amount1);

							TempletFields tempField = new TempletFields();

							tempField.setMid(data.getVoidResp().getF001_MID());
//								tempField.setRefNo(data.getVoidResp().getF011_AuthIDResp());
							tempField.setMaskedPan(data.getVoidResp().getF004_PAN());
							tempField.setDate(rd1);
							tempField.setAmount(refundAmount);

							logger.info("MID : " + tempField.getMID());
//								logger.info("Ref No : " + tempField.getRefNo());
							logger.info("Masked Pan : " + tempField.getMaskedPan());
							logger.info("Date : " + tempField.getDate());
							logger.info("Amount : " + tempField.getAmount());

							String emailBody = EmailTemplet.sentRefundTempletContent(tempField);

							Attachment logo = new Attachment("mobiversa_logo.png", "image/png",
									PropertyLoad.getFile().getProperty("REFUND_IMG"), "cid:otp_logo");

							List<Attachment> attachments = new ArrayList<Attachment>();
							attachments.add(logo);

							/*
							 * PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress,
							 * fromAddress, ccMail, bccMail, subject, emailBody, true, "test-email", null,
							 * attachments);
							 */
//								PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress,
//										fromAddress, null,subject, emailBody, true, "test-email",
//										null, attachments); 
							PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress, fromAddress,
									bccAddress, bccAddress, subject, emailBody, true, "test-email", null, attachments);
							PostmarkClient client = new PostmarkClient(apiKey);

							try {
								client.sendMessage(message);
								System.out.println("Email Sent Successfully to" + toAddress);
							} catch (PostmarkException pe) {
								System.out.println("Invalid Signature Base64 String");

							}

						} catch (Exception e) {
							// TODO: handle exception
						}

						PageBean pageBean = new PageBean("transactions list",
								"merchantweb/transaction/voidpayment/umEzyrecRefundPaymentDone", Module.TRANSACTION_WEB,
								"merchantweb/transaction/sideMenuTransaction");
						model.addAttribute("pageBean", pageBean);
						model.addAttribute("amount", output);
						model.addAttribute("TDT", " " + rd + " " + rt);
						model.addAttribute("CHNumber", data.getVoidResp().getF004_PAN());
						model.addAttribute("responseData", data.getResponseDescription());
					}
				} else {
					PageBean pageBean = new PageBean("transactions list",
							"merchantweb/transaction/voidpayment/umEzyrecRefundPayment", Module.TRANSACTION_WEB,
							"merchantweb/transaction/sideMenuTransaction");
					model.addAttribute("pageBean", pageBean);
					model.addAttribute("txnDet", TxnDet);
					model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
				}

			} else {
				PageBean pageBean = new PageBean("transactions list",
						"merchantweb/transaction/voidpayment/umEzyrecRefundPayment", Module.TRANSACTION_WEB,
						"merchantweb/transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", TxnDet);
				model.addAttribute("responseData", "InValid OTP...  Try Again..");
			}

		} else {
			logger.info("OTP Not exist for the Mobile Number ");
			PageBean pageBean = new PageBean("transactions list",
					"merchantweb/transaction/voidpayment/umEzyrecRefundPayment", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", "OTP Not exist for the Mobile Number / Email ...  Try Again..");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// rk

	@RequestMapping(value = "/umSplitExport", method = RequestMethod.GET)
	public ModelAndView umSplitExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//                @RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("UM_EZYSPLIT Export by Merchant");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getSplitMid());

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		// String txnStatus="A";
//            transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1,txnStatus,currentMerchant.getMid().getUmEzywayMid(),"ALL");
		transactionService.exportUMSplitTransaction(paginationBean, dat, dat1, currentMerchant.getMid().getSplitMid(),
				"ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListUMMerchantAllExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListSplitUMPdf", "umTxnList", list1);
		}

	}

	// rk

	@RequestMapping(value = { "/umSplitList" }, method = RequestMethod.GET)
	public String umSplitList(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list umSplitList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMSplitList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-Split Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMSplitTransaction(paginationBean, null, null, currentMerchant.getMid().getSplitMid(),
				"ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// rk

	@RequestMapping(value = { "/searchUMSplit" }, method = RequestMethod.GET)
	public String searchUMSplit(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("search  UM-split Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMSplitList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listUMSplitTransaction(paginationBean, date, date1, currentMerchant.getMid().getSplitMid(),
				"ALL");

		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// dhinesh

	@RequestMapping(value = { "/UMwayenquiryTransaction/{currPage}" }, method = RequestMethod.GET)
	public String UMWayenquiryTransaction(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("UM_WAY Transaction Enquiry list by Merchant");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/umWayTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		logger.info(" All Transaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		// transactionService.listUMMotoTxnEnqByAdmin(paginationBean, null, null,"ALL");

		// transactionService.listUMMotoTransactionEnq(paginationBean, null, null,
		// currentMerchant.getMid().getUmMotoMid(),
		// "ALL");

		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
			String ezywaymid = "000000000021591";

			transactionService.listUMEzywayTransactionEnq(paginationBean, null, null, ezywaymid, "ALL");
		} else {

			transactionService.listUMEzywayTransactionEnq(paginationBean, null, null,
					currentMerchant.getMid().getUmEzywayMid(), "ALL");
		}

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		String UmEzywayMid = null;
		Merchant merchant = merchantService.loadMerchant(myName);

		if (merchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = merchant.getMid().getUmEzywayMid();
		}

		model.addAttribute("UmEzywayMid", UmEzywayMid);

		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}

		logger.info(" enquiry");
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchUMWayEnquiry" }, method = RequestMethod.GET)
	public String searchUMWayEnquiry1(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("Transaction Enquiry list");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/umWayTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
			String ezywaymid = "000000000021591";

			transactionService.listUMEzywayTransactionEnq(paginationBean, date, date1, ezywaymid, "ALL");
		} else {
			transactionService.listUMEzywayTransactionEnq(paginationBean, date, date1,
					currentMerchant.getMid().getUmEzywayMid(), "ALL");
		}

		String UmEzywayMid = null;
		Merchant merchant = merchantService.loadMerchant(myName);

		if (merchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = merchant.getMid().getUmEzywayMid();
		}

		model.addAttribute("UmEzywayMid", UmEzywayMid);

		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = "/umWayEnqExport", method = RequestMethod.GET)
	public ModelAndView umWayEnqExport1(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
			// @RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("UM_Way Enquiry merchant by admin");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
			String ezywaymid = "000000000021591";
			transactionService.exportUMEzywayTransactionEnq(paginationBean, dat, dat1, ezywaymid, "ALL");
		} else {
			transactionService.exportUMEzywayTransactionEnq(paginationBean, dat, dat1,
					currentMerchant.getMid().getUmEzywayMid(), "ALL");
		}
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnUMEnqExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnUMEnqPdf", "umTxnList", list1);
		}

	}

	// rksettlementsum
	@RequestMapping(value = { "/settlesum/{currPage}" }, method = RequestMethod.GET)
	public String displaysettlementSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("about to list settelement  summary");

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/settlementsummary",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		logger.info(" settlement Summary:" + principal.getName());

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
		paginationBean.setCurrPage(currPage);

		model.addAttribute("pageBean", pageBean);

		transactionService.listsettleDetails(paginationBean, currentMerchant, null, null);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// rksettlementsum
	@RequestMapping(value = { "/settlesearch" }, method = RequestMethod.GET)
	public String displaysettlesearch(final Model model, final java.security.Principal principal,
			@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("about to list settelement  summary");

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/settlementsummary",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		logger.info(" settlement Summary:" + principal.getName());

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
		paginationBean.setCurrPage(currPage);

		model.addAttribute("pageBean", pageBean);
		String dat = null;
		String dat1 = null;

		if ((date != null && date1 != null) && (!date.isEmpty() && !date1.isEmpty())) {
			try {
				dat = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				dat1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date1));
				logger.info("inside search controler " + dat + " " + dat1);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		transactionService.listsettleDetails(paginationBean, currentMerchant, dat, dat1);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// rksettlementsum
	@RequestMapping(value = { "/settleexport" }, method = RequestMethod.GET)
	public ModelAndView displaysettleexport(@RequestParam(required = false) final String date,
			@RequestParam(required = false) final String date1, HttpServletRequest request,
			@RequestParam(required = false) String export, final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("about to list settelement  summary");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
		String dat = null;
		String dat1 = null;
		String status1 = null;

		if ((date != null && date1 != null) && (!date.isEmpty() && !date1.isEmpty())) {
			try {
				dat = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				dat1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date1));
				logger.info("inside search controler " + dat + " " + dat1);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		transactionService.listsettleDetails(paginationBean, currentMerchant, dat, dat1);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<SettlementMDR> list1 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {
			logger.info("here excel");

			return new ModelAndView("SettlementsumExcel", "txnList", list1);

		} else {
			logger.info("here pdf");
			return new ModelAndView("txnListMotoUMPdf", "txnList", list1);

		}

	}

// Failed Transaction of Umobile - EZYWAY , EZYLINK , EZYMOTO , EZYREC , EZYWIRE 

// Ezyway Failed Transaction - Start

	@RequestMapping(value = { "/umEzywayFailList" }, method = RequestMethod.GET)
	public String umEzywayListFailure(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list umEzywayList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzywayFailList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-EZWAY Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		logger.info("currentMerchant.getMid().getUmEzywayMid" + currentMerchant.getMid().getUmEzywayMid());
		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
			logger.info("Inside IF Condition - " + currentMerchant.getMid().getUmEzywayMid());
			logger.info("1");
			String ezywaymid = "000000000021591";
			transactionService.listUMEzywayTransactionFailure(paginationBean, null, null, ezywaymid, "ALL",
					currentMerchant);
		} else {
			logger.info("Outside IF Condition - " + currentMerchant.getMid().getUmEzywayMid());
			logger.info("2");
			transactionService.listUMEzywayTransactionFailure(paginationBean, null, null,
					currentMerchant.getMid().getUmEzywayMid(), "ALL", currentMerchant);
		}
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchUMFailEzyway" }, method = RequestMethod.GET)
	public String umEzywayList1Fail(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("search  UM-EZWAY Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzywayFailList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
			logger.info("Inside IF Condition - " + currentMerchant.getMid().getUmEzywayMid());
			logger.info("1");
			String ezywaymid = "000000000021591";
			transactionService.listUMEzywayTransactionFailure(paginationBean, date, date1, ezywaymid, "ALL",
					currentMerchant);
		} else {

			transactionService.listUMEzywayTransactionFailure(paginationBean, date, date1,
					currentMerchant.getMid().getUmEzywayMid(), "ALL", currentMerchant);
		}

		logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/umEzywayFailExport", method = RequestMethod.GET)
	public ModelAndView getExportUMEzywayFailure(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
			logger.info("Inside IF Condition - " + currentMerchant.getMid().getUmEzywayMid());
			logger.info("1");
			String ezywaymid = "000000000021591";
			transactionService.exportUMEzywayTransactionFailure(paginationBean, dat, dat1, ezywaymid, "ALL",
					currentMerchant);
		} else {

			transactionService.exportUMEzywayTransactionFailure(paginationBean, dat, dat1,
					currentMerchant.getMid().getUmEzywayMid(), "ALL", currentMerchant);
		}
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {

			return new ModelAndView("txnListUMMerchantFailExcel", "umTxnList", list1);

		} else {

			return new ModelAndView("txnListUMEzywayFailPdf", "umTxnList", list1);
		}

	}

// Ezyway Failed Transaction - End  

// EzyLink Failed Transaction - Start

	@RequestMapping(value = { "/umLinkFailList" }, method = RequestMethod.GET)
	public String umLinkListFailure(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list umLinkList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMFailLinkList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-Link Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMLinkTransactionFailure(paginationBean, null, null,
				currentMerchant.getMid().getUmMotoMid(), "ALL", currentMerchant);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchUMFailLink" }, method = RequestMethod.GET)
	public String searchUMLinkFailure(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("search  UM-link Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMFailLinkList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMLinkTransactionFailure(paginationBean, date, date1,
				currentMerchant.getMid().getUmMotoMid(), "ALL", currentMerchant);

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/umLinkFailExport", method = RequestMethod.GET)
	public ModelAndView umLinkExportFailure(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("UM_EZYLINK Export by Merchant");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		logger.info("current Merchant: " + currentMerchant.getMid().getUmMotoMid());

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMLinkTransactionFailure(paginationBean, dat, dat1,
				currentMerchant.getMid().getUmMotoMid(), "ALL", currentMerchant);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListUMMerchantFailExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListMotoUMPdf", "umTxnList", list1);
		}

	}

// EzyLink Failed Transaction - End  

// EzyMoto Failed Transaction - Start

	@RequestMapping(value = { "/umMotoFailList" }, method = RequestMethod.GET)
	public String umMotoListFailure(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list umMotoList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMMotoFailList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-MOTO Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMMotoTransactionFailure(paginationBean, null, null,
				currentMerchant.getMid().getUmMotoMid(), "ALL", currentMerchant);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchUMMotoFail" }, method = RequestMethod.GET)
	public String umMotoList1Fail(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("search  UM-Moto Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMMotoFailList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMMotoTransactionFailure(paginationBean, date, date1,
				currentMerchant.getMid().getUmMotoMid(), "ALL", currentMerchant);

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/umMotoExportFail", method = RequestMethod.GET)
	public ModelAndView getExportUMMotoFail(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("UM_EZYMOTO Export by Merchant");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("current Merchant: " + currentMerchant.getMid().getUmMotoMid());

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMMotoTransactionFailure(paginationBean, dat, dat1,
				currentMerchant.getMid().getUmMotoMid(), "ALL", currentMerchant);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListUMMerchantFailExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListMotoUMPdf", "umTxnList", list1);
		}

	}

// EzyMoto Failed Transaction - End	

// EzyRec Failed Transaction - Start

	@RequestMapping(value = { "/umEzyrecFailList" }, method = RequestMethod.GET)
	public String umEzyrecListFailure(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list umEzyrecList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("currentMerchant " + currentMerchant.getBusinessName());
		logger.info("currentMerchant mid " + currentMerchant.getMid().getUmEzyrecMid());
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzyrecFailList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-MOTO Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMEzyrecTransactionFailure(paginationBean, null, null,
				currentMerchant.getMid().getUmEzyrecMid(), "ALL", currentMerchant);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchUMEzyrecFail" }, method = RequestMethod.GET)
	public String searchUMEzyrecFailure(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("searchUMEzyrec Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzyrecFailList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMEzyrecTransactionFailure(paginationBean, date, date1,
				currentMerchant.getMid().getUmEzyrecMid(), "ALL", currentMerchant);

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/umEzyrecExportFail", method = RequestMethod.GET)
	public ModelAndView getumEzyrecExportFailure(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("umEzyrecExport  by Merchant");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);
		logger.info("current Merchant: " + currentMerchant.getMid().getUmEzyrecMid());

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMEzyrecTransactionFailure(paginationBean, dat, dat1,
				currentMerchant.getMid().getUmEzyrecMid(), "ALL", currentMerchant);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListUMMerchantFailExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListMotoUMPdf", "umTxnList", list1);
		}

	}

// EzyRec Failed Transaction - End

// EzyWire Failed Transaction - Start

	@RequestMapping(value = { "/alllistFail" }, method = RequestMethod.GET)
	public String displayTransactionSummaryFailure(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("All UMOBILE transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzywireFailList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();

		paginationBean.setCurrPage(currPage);
		transactionService.getUMForSettlementFailure(paginationBean, currentMerchant);
		if (paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Record found");
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = "/exportFail", method = RequestMethod.GET)
	public ModelAndView getExportFailure(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant merchant = merchantService.loadMerchant(myName);

		logger.info("the merchant obj based on currently logged in user is: " + merchant);

		String dat = fromDate;
		String dat1 = toDate;

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchUMForSettlementFailure(dat, dat1, paginationBean, merchant);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListUMWireFailExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = "/searchFail", method = RequestMethod.GET)
	public String displayTransactionSearchByTidFailure(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");
		logger.info("Search All UMobile Transaction By Merchant " + myName);

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzywireFailList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.searchUMForSettlementFailure(date, date1, paginationBean, currentMerchant);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

// EzyWire Failed Transaction - End

	// RK PORTAL (28/06/2022)
//	@RequestMapping(value = { "/EzySettleList" }, method = RequestMethod.GET)
//	public String EzySettleList(final Model model,
//			@RequestParam(required = false, defaultValue = "1") final int currPage,
//			final java.security.Principal principal, HttpServletRequest request) {
//
//		logger.info("EZYSETTLE SUMMARY BY MERCHANT");
//
//		HttpSession session = request.getSession();
//		String myName = (String) session.getAttribute("userName");
//		// String myName = principal.getName();
//		logger.info("currently logged in as " + myName);
//		Merchant currentMerchant = merchantService.loadMerchant(myName);
//
//		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzySettleSummaryList",
//				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//
//		model.addAttribute("pageBean", pageBean);
//
//		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
//		paginationBean.setCurrPage(currPage);
//
//		transactionService.ListofEzySettleSummarymerchant(paginationBean, null, null, null, currentMerchant);
//
//		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
//				|| paginationBean.getItemList().size() == 0) {
//			model.addAttribute("responseData", "No Records found"); // table
//																	// response
//		} else {
//			model.addAttribute("responseData", null);
//		}
//		model.addAttribute("paginationBean", paginationBean);
//		model.addAttribute("loginname", principal.getName());
//		return TEMPLATE_MERCHANT;
//
//	}

//	@RequestMapping(value = { "/searchEzySettleList" }, method = RequestMethod.GET)
//	public String searchEzySettleList(HttpServletRequest request, final Model model, @RequestParam final String date,
//			@RequestParam final String date1, @RequestParam final String txntype,
//			@RequestParam(required = false, defaultValue = "1") final int currPage,
//			final java.security.Principal principal) {
//
//		logger.info("SEARCH ------ EZYSETTLE SUMMARY BY MERCHANT");
//		HttpSession session = request.getSession();
//		String myName = (String) session.getAttribute("userName");
//		// String myName = principal.getName();
//		logger.info("currently logged in as " + myName);
//		Merchant currentMerchant = merchantService.loadMerchant(myName);
//
//		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzySettleSummaryList",
//				Module.TRANSACTION, "transaction/sideMenuTransaction");
//		model.addAttribute("pageBean", pageBean);
//
//		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
//		paginationBean.setCurrPage(currPage);
//
//		transactionService.ListofEzySettleSummarymerchant(paginationBean, date, date1, txntype, currentMerchant);
//
//		model.addAttribute("paginationBean", paginationBean);
//		model.addAttribute("loginname", principal.getName());
//		return TEMPLATE_MERCHANT;
//
//	}

//	@RequestMapping(value = "/exportEzySettleList", method = RequestMethod.GET)
//	public ModelAndView exportsearchEzySettleList(@RequestParam final String date, @RequestParam final String date1,
//			@RequestParam final String txntype, @RequestParam(required = false, defaultValue = "1") final int currPage,
//			final Model model, HttpServletRequest request, @RequestParam(required = false) String export,
//			final java.security.Principal principal) {
//
//		logger.info("SEARCH ------ EZYSETTLE SUMMARY BY MERCHANT");
//		HttpSession session = request.getSession();
//		String myName = (String) session.getAttribute("userName");
//		// String myName = principal.getName();
//		logger.info("currently logged in as " + myName);
//		Merchant currentMerchant = merchantService.loadMerchant(myName);
//
//		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
//		paginationBean.setCurrPage(currPage);
//
//		transactionService.ListofEzySettleSummarymerchant(paginationBean, date, date1, txntype, currentMerchant);
//
//		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
//			model.addAttribute("responseData", "No Records found"); // table
//																	// response
//
//		} else {
//			model.addAttribute("responseData", null);
//		}
//
//		List<SettlementModel> list = paginationBean.getItemList();
//
//		if (!(export.equals("PDF"))) {
//
//			return new ModelAndView("EzySettleListExcel", "txnList", list);
//
//		} else {
//			return new ModelAndView("EzySettleListPdf", "txnList", list);
//		}
//	}

	@RequestMapping(value = { "/ezywireCancelPayment/{id}" }, method = RequestMethod.GET)
	public String ezywireCancelPayment(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		PageBean pageBean = new PageBean("Transactions Details",
				"merchantweb/transaction/voidpayment/EzywireCancelPaymentConfirm", null);
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant merchant = merchantService.loadMerchant(myName);
		logger.info("Mid" + ":" + merchant.getMid().getMid() + "MerchantName" + ":" + merchant.getBusinessName() + ":"
				+ "Merchant void logged by" + ":" + principal.getName() + ":");
		ForSettlement fs = transactionService.getForSettlement(id);
		TransactionRequest tr = transactionService.loadTransactionRequest(id);
		MotoTxnDet txnDet = new MotoTxnDet();
		txnDet.setTid(fs.getTid());
		txnDet.setMid(fs.getMid());
		txnDet.setTrxId(id);
		txnDet.setContactName(tr.getCardHolderName());
		txnDet.setApprCode(fs.getAidResponse());
		String rd = null;
		if (fs.getDate() != null) {
			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(fs.getTimeStamp()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String rt = null;
		if (fs.getTime() != null) {
			try {
				rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(fs.getTime()));
			} catch (ParseException e) {
			}
		}
		txnDet.setExpectedDate(rd + " " + rt);
		double amount = 0;
		amount = Double.parseDouble(fs.getAmount()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		txnDet.setAmount(output);
		if (tr.getMaskedPan() != null) {
			String pan = tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);
			if (pan.contains("f")) {
				pan = pan.replaceAll("f", "X");
				txnDet.setPan(pan);
			} else {
				txnDet.setPan(pan);
			}
		} else {
			txnDet.setPan("NA");
		}
		txnDet.setMerchantId(merchant.getId());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", txnDet);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/EzywireCancelPaymentByMerchant" }, method = RequestMethod.POST)
	public String EzywireCancelPaymentByMerchant(final Model model, @ModelAttribute("txnDet") MotoTxnDet motoTxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		ResponseDetails data = MotoPaymentCommunication.CancelPayment(motoTxnDet);
		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/EzywireCancelPaymentConfirm", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", motoTxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "... Try Again..");
			} else {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/EzywireVoidPaymentDone", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("Transactions Details",
					"merchantweb/transaction/voidpayment/EzywireCancelPaymentConfirm", null);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", motoTxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "... Try Again..");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// Start of Transaction Request Page Service - Developed by Dhinesh (TL)

	@RequestMapping(value = { "/Transaction" }, method = RequestMethod.GET)
	public String Transaction(HttpServletRequest request, final Model model, final java.security.Principal principal) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/Request/Transaction", Module.TRANSACTION,
				"transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		// Check if the Merchant is Moto or Link

		String Auth3ds = "No";

		if (currentMerchant.getMid().getMotoMid() != null && currentMerchant.getAuth3DS().equals("Yes")) {
			Auth3ds = "Yes";
		} else if (currentMerchant.getMid().getUmMotoMid() != null && currentMerchant.getAuth3DS().equals("Yes")) {
			Auth3ds = "Yes";
		} else if (currentMerchant.getMid().getMotoMid() != null && currentMerchant.getAuth3DS().equals("No")) {
			Auth3ds = "No";
		} else if (currentMerchant.getMid().getUmMotoMid() != null && currentMerchant.getAuth3DS().equals("No")) {
			Auth3ds = "No";
		}else if (currentMerchant.getMid().getFiuuMid() != null && currentMerchant.getAuth3DS().equals("Yes")) {
			Auth3ds = "Yes";
		} else if (currentMerchant.getMid().getFiuuMid() != null && currentMerchant.getAuth3DS().equals("No")) {
			Auth3ds = "No";
		}

		if (Auth3ds.equalsIgnoreCase("Yes")) {
			session.setAttribute("Auth3ds", "Yes");
			logger.info("EzyLink Transaction");
		} else {
			session.setAttribute("Auth3ds", "No");
			logger.info("EzyMoto Transaction");
		}

		logger.info("Auth3DS: " + Auth3ds);

		// Check if the Merchant is Enable PreAuth or Not

		String ezyAuthEnable = "No";
		String mototid = null;
		try {

			logger.error("Start Checking PreAuth Status in MobileUser");

			MobileUser mobileUser = mobileUserService.loadMobileUserbyMerchantFK(currentMerchant.getId().toString());

			mototid = mobileUser.getMotoTid();
			logger.info("moto tid is " + mototid);

			if (mobileUser == null) {
				ezyAuthEnable = "No";
			} else if (currentMerchant.getMid().getMotoMid() != null
					&& mobileUser.getPreAuth().equalsIgnoreCase("Yes")) {
				ezyAuthEnable = "Yes";
			} else if (currentMerchant.getMid().getUmMotoMid() != null
					&& mobileUser.getPreAuth().equalsIgnoreCase("Yes")) {
				ezyAuthEnable = "Yes";
			}

		} catch (Exception e) {

			logger.error("Error in PreAuth Checking Status");
		}

		if (ezyAuthEnable.equalsIgnoreCase("Yes")) {
			session.setAttribute("ezyAuthEnable", "Yes");

		} else {
			session.setAttribute("ezyAuthEnable", "No");

		}
		logger.info("ezyAuthEnable: " + ezyAuthEnable);

		model.addAttribute("mototid", mototid);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/EzyLinkRequestDetails" }, method = RequestMethod.GET)
	public String EzyLinkRequestDetails(HttpServletRequest request, final Model model,
			@RequestParam("LinkreqAmount") final String LinkreqAmount, final java.security.Principal principal) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Request/EzyLinkTransactionDetails",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		logger.info("Merchant Requested Amount: " + LinkreqAmount);

		List<CountryCurPhone> listofCountryNameandCode = transactionService.loadCountryCurrency();

		List<Country> countryList = new ArrayList<Country>();

		for (CountryCurPhone ccp : listofCountryNameandCode) {
			Country country = new Country();
			country.setCountryName(ccp.getCountryName());
			country.setPhoneCode("+" + ccp.getCountryPhone());
			countryList.add(country);
		}

		model.addAttribute("listCountry", countryList);

		session.setAttribute("Amount", LinkreqAmount);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/EzyMotoRequestDetails" }, method = RequestMethod.GET)
	public String EzyMotoRequestDetails(HttpServletRequest request, final Model model,
			@RequestParam("MotoreqAmount") final String MotoreqAmount,
			@RequestParam("getInvoice") final String getInvoice, final java.security.Principal principal) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Request/EzyMotoTransactionDetails",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		logger.info("Merchant Requested Amount: " + MotoreqAmount + " And invoice is " + getInvoice);

		List<CountryCurPhone> listofCountryNameandCode = transactionService.loadCountryCurrency();

		List<Country> countryList = new ArrayList<Country>();

		for (CountryCurPhone ccp : listofCountryNameandCode) {
			Country country = new Country();
			country.setCountryName(ccp.getCountryName());
			country.setPhoneCode("+" + ccp.getCountryPhone());
			countryList.add(country);
		}

		model.addAttribute("listCountry", countryList);

		session.setAttribute("Amount", MotoreqAmount);
		session.setAttribute("Invoice", getInvoice);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/EzyAuthRequestDetails" }, method = RequestMethod.GET)
	public String EzyAuthRequestDetails(HttpServletRequest request, final Model model,
			@RequestParam("AuthreqAmount") final String AuthreqAmount, final java.security.Principal principal) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Request/EzyAuthTransactionDetails",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		logger.info("Merchant Requested Amount: " + AuthreqAmount);

		List<CountryCurPhone> listofCountryNameandCode = transactionService.loadCountryCurrency();

		List<Country> countryList = new ArrayList<Country>();

		for (CountryCurPhone ccp : listofCountryNameandCode) {
			Country country = new Country();
			country.setCountryName(ccp.getCountryName());
			country.setPhoneCode("+" + ccp.getCountryPhone());
			countryList.add(country);
		}

		model.addAttribute("listCountry", countryList);

		session.setAttribute("Amount", AuthreqAmount);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// End of Transaction Request Page Service - Developed by Dhinesh

	// rk(preauthviamoto)
	@RequestMapping(value = { "/EzyauthviaMOTORequestDetails" }, method = RequestMethod.GET)
	public String EzyauthviaMOTORequestDetails(HttpServletRequest request, final Model model,
			@RequestParam("AuthviamotoreqAmount") final String AuthviamotoreqAmount,
			@RequestParam("getInvoice") final String getInvoice, final java.security.Principal principal) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Request/EzyAuthviaMotoTransactionDetails",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		logger.info("Merchant Requested Amount: " + AuthviamotoreqAmount);

		List<CountryCurPhone> listofCountryNameandCode = transactionService.loadCountryCurrency();

		List<Country> countryList = new ArrayList<Country>();

		for (CountryCurPhone ccp : listofCountryNameandCode) {
			Country country = new Country();
			country.setCountryName(ccp.getCountryName());
			country.setPhoneCode("+" + ccp.getCountryPhone());
			countryList.add(country);
		}

		model.addAttribute("listCountry", countryList);

		session.setAttribute("Amount", AuthviamotoreqAmount);
		session.setAttribute("Invoice", getInvoice);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/TngpaySlip/{id}" }, method = RequestMethod.GET)
	public String TngSalesSlip(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		HttpSession session = request.getSession();

		logger.info("Tng MRN = " + id);

		PageBean pageBean = new PageBean("Tng Transaction Sales Slip", "merchantweb/transaction/Tng_Receipt", null);

		String myName = (String) session.getAttribute("userName");
		logger.info("myName" + myName);
		Merchant merchant = merchantService.loadMerchant(myName);

		EwalletTxnDetails tng = transactionService.loadEwalletTxnDetails(id);

		DataTransferObject dt = new DataTransferObject();
		try {

			logger.info("Initiate ---------- Tng Transaction Slip ");

			dt.setMerchantName(merchant.getBusinessName());
			dt.setMerchantAddr1(merchant.getBusinessAddress1());
			dt.setMerchantCity(merchant.getCity());
			dt.setMerchantPostCode(merchant.getPostcode());
			dt.setMerchantContNo(merchant.getBusinessContactNumber());
			dt.setMerchantState(merchant.getState());

			// Date
			String date = null;
			try {
				date = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tng.getTimeStamp()));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			tng.setDate(date);

			logger.info("Date Format = " + tng.getDate());

			// Time
			String time = null;
			try {
				time = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(tng.getTimeStamp()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			tng.setTime(time);
			logger.info("Time Format = " + tng.getTime());
			// MID
			logger.info(" MID = " + tng.getMid());
			// TID
			logger.info(" TID = " + tng.getTid());

			String amt = tng.getAmount().toString();
			double amt1 = Double.parseDouble(amt);
			amt1 = amt1 / 100;

			String pattern3 = "#,##0.00";
			DecimalFormat myFormatter3 = new DecimalFormat(pattern3);
			String TngAmount = myFormatter3.format(amt1);
			tng.setAmount(TngAmount);
			logger.info(" Amount = " + tng.getAmount());
			logger.info(" TxnType = " + tng.getTxnType());
			// logger.info(" RRN = " + );
			// logger.info(" Stan = " + );
			logger.info(" AidResponse = " + tng.getTngTxnId());
			logger.info(" Reference / Invoice ID = " + tng.getInvoiceId());
			logger.info("RRN = " + tng.getMobiTxnId());

			dt.setDate(tng.getDate());
			dt.setTime(tng.getTime());
			dt.setTotal(tng.getAmount());
			// dt.setRefNo(fsboost.getRrn());
			dt.setApprCode(tng.getTngTxnId());
			dt.setInvoiceNo(tng.getInvoiceId());
			dt.setMid(tng.getMid());
			dt.setTid(tng.getTid());
			dt.setRrn(tng.getMobiTxnId());

			// To Check Status

			if (tng.getStatus().equals("TPA") || tng.getStatus().equals("TPS")) {
				dt.setTxnType("TNG SALE");
			}

			logger.info("Ready To View ---------- Tng Transaction Slip ");

		} catch (Exception e) {

			logger.error("Error To Read Tng Transaction by MRN", e);
		}

		request.setAttribute("dto", dt);
		model.addAttribute("pageBean", pageBean);

		return "merchantweb/transaction/Tng_Receipt";

	}

	// BnplPaySlip Starts

	@RequestMapping(value = { "/BnplPaySlip/{id}" }, method = RequestMethod.GET)
	public String BnplSalesSlip(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		HttpSession session = request.getSession();

		logger.info("BNPL MRN = " + id);

		PageBean pageBean = new PageBean("Bnpl Transaction Sales Slip", "merchantweb/transaction/BnplSalesSlip", null);

		String myName = (String) session.getAttribute("userName");
		logger.info("myName" + myName);
		Merchant merchant = merchantService.loadMerchant(myName);

		// EwalletTxnDetails tng = transactionService.loadEwalletTxnDetails(id);

		BnplTxnDetails bnp = transactionService.loadBnplTxnDetails(id);

		DataTransferObject dt = new DataTransferObject();
		try {

			logger.info("Initiate ---------- BNPL Transaction Slip ");

			dt.setMerchantName(merchant.getBusinessName());
			dt.setMerchantAddr1(merchant.getBusinessAddress1());
			dt.setMerchantCity(merchant.getCity());
			dt.setMerchantPostCode(merchant.getPostcode());
			dt.setMerchantContNo(merchant.getBusinessContactNumber());
			dt.setMerchantState(merchant.getState());

			// Date
			String date = null;
			try {
				date = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bnp.getTimeStamp()));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			bnp.setDate(date);

			logger.info("Date Format = " + bnp.getDate());

			// Time
			String time = null;
			try {
				time = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bnp.getTimeStamp()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			bnp.setTime(time);
			logger.info("Time Format = " + bnp.getTime());
			// MID
			logger.info(" MID = " + bnp.getMid());
			// TID
			logger.info(" TID = " + bnp.getTid());

			String amt = bnp.getAmount().toString();
			double amt1 = Double.parseDouble(amt);
			amt1 = amt1 / 100;

			String pattern3 = "#,##0.00";
			DecimalFormat myFormatter3 = new DecimalFormat(pattern3);
			String TngAmount = myFormatter3.format(amt1);
			bnp.setAmount(TngAmount);
			logger.info(" Amount = " + bnp.getAmount());
			logger.info(" TxnType = " + bnp.getTxnType());
			// logger.info(" RRN = " + );
			// logger.info(" Stan = " + );
			logger.info(" AidResponse = " + bnp.getBnplTxnId());
			logger.info(" Reference / Invoice ID = " + bnp.getInvoiceId());
			logger.info("RRN = " + bnp.getMobiTxnId());

			dt.setDate(bnp.getDate());
			dt.setTime(bnp.getTime());
			dt.setTotal(bnp.getAmount());
			// dt.setRefNo(fsboost.getRrn());
			dt.setApprCode(bnp.getBnplTxnId());
			dt.setInvoiceNo(bnp.getInvoiceId());
			dt.setMid(bnp.getMid());
			dt.setTid(bnp.getTid());
			dt.setRrn(bnp.getMobiTxnId());

			// To Check Status

			if (bnp.getStatus().equals("BNA") || bnp.getStatus().equals("BNS")) {
				dt.setTxnType("BNPL SALE");
			}

			logger.info("Ready To View ---------- BNPL Transaction Slip ");

		} catch (Exception e) {

			logger.error("Error To Read BNPL Transaction by MRN", e);
		}

		request.setAttribute("dto", dt);
		model.addAttribute("pageBean", pageBean);

		return "merchantweb/transaction/BnplSalesSlip";

	}

	// BNPL SALES SLIP ENDS

	@RequestMapping(value = { "/Shopeepayslip/{id}" }, method = RequestMethod.GET)
	public String ShopeepaySalesSlip(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		HttpSession session = request.getSession();

		logger.info("Shopeepay MRN = " + id);

		PageBean pageBean = new PageBean("Shopeepay Transaction Sales Slip", "merchantweb/transaction/Tng_Receipt",
				null);

		String myName = (String) session.getAttribute("userName");
		logger.info("myName" + myName);
		Merchant merchant = merchantService.loadMerchant(myName);

		EwalletTxnDetails shopeepay = transactionService.loadEwalletTxnDetails(id);

		DataTransferObject dt = new DataTransferObject();
		try {

			logger.info("Initiate ---------- Shopeepay Transaction Slip ");

			dt.setMerchantName(merchant.getBusinessName());
			dt.setMerchantAddr1(merchant.getBusinessAddress1());
			dt.setMerchantCity(merchant.getCity());
			dt.setMerchantPostCode(merchant.getPostcode());
			dt.setMerchantContNo(merchant.getBusinessContactNumber());
			dt.setMerchantState(merchant.getState());

			// Date
			String date = null;
			try {
				date = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(shopeepay.getTimeStamp()));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			shopeepay.setDate(date);

			logger.info("Date Format = " + shopeepay.getDate());

			// Time
			String time = null;
			try {
				time = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(shopeepay.getTimeStamp()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			shopeepay.setTime(time);
			logger.info("Time Format = " + shopeepay.getTime());
			// MID
			logger.info(" MID = " + shopeepay.getMid());
			// TID
			logger.info(" TID = " + shopeepay.getTid());

			String amt = shopeepay.getAmount().toString();
			double amt1 = Double.parseDouble(amt);
			amt1 = amt1 / 100;

			String pattern3 = "#,##0.00";
			DecimalFormat myFormatter3 = new DecimalFormat(pattern3);
			String TngAmount = myFormatter3.format(amt1);
			shopeepay.setAmount(TngAmount);
			logger.info(" Amount = " + shopeepay.getAmount());
			logger.info(" TxnType = " + shopeepay.getTxnType());
			// logger.info(" RRN = " + );
			// logger.info(" Stan = " + );
			logger.info(" AidResponse = " + shopeepay.getTngTxnId());
			logger.info(" Reference / Invoice ID = " + shopeepay.getInvoiceId());
			logger.info(" RRN = " + shopeepay.getMobiTxnId());

			dt.setDate(shopeepay.getDate());
			dt.setTime(shopeepay.getTime());
			dt.setTotal(shopeepay.getAmount());
			// dt.setRefNo(fsboost.getRrn());
			dt.setApprCode(shopeepay.getTngTxnId());
			dt.setInvoiceNo(shopeepay.getInvoiceId());
			dt.setMid(shopeepay.getMid());
			dt.setTid(shopeepay.getTid());
			dt.setRrn(shopeepay.getMobiTxnId());

			// To Check Status

			if (shopeepay.getStatus().equals("SPA") || shopeepay.getStatus().equals("SPS")) {
				dt.setTxnType("SHOPEEPAY SALE");
			}

			logger.info("Ready To View ---------- Shopeepay Transaction Slip ");

		} catch (Exception e) {

			logger.error("Error To Read Shopeepay Transaction by MRN", e);
		}

		request.setAttribute("dto", dt);
		model.addAttribute("pageBean", pageBean);

		return "merchantweb/transaction/Shopeepay_Receipt";

	}

	// payout replica from payoutusercontroller

//	@RequestMapping(value = { "/payoutlist/{currPage}" }, method = RequestMethod.GET)
//	public String settlement(final Model model, @PathVariable final int currPage, HttpServletRequest request,
//			HttpServletResponse response, final java.security.Principal principal) {
//
//		HttpSession session = request.getSession();
//
//		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutList",
//				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//
//		String myName = (String) session.getAttribute("userName");
//		String userName = principal.getName();
//		model.addAttribute("pageBean", pageBean);
//		logger.info("Payout Summary:" + myName);
//		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
//		paginationBean.setCurrPage(currPage);
//		Merchant merchant = merchantService.loadMerchant(myName);
//		String merchantName = merchant.getBusinessShortName();
//
//		transactionService.listPayoutTransactionByMerchant(paginationBean, null, null, merchantName);
//
//		if (userName.equalsIgnoreCase("daria.ar@monetix.pro")) {
//
//			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
//			model.addAttribute("localAdmin", "No");
//
//		}
//
//		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
//				|| paginationBean.getItemList().size() == 0) {
//			model.addAttribute("responseData", "No Records found");
//
//		} else {
//			model.addAttribute("responseData", null);
//		}
//		model.addAttribute("paginationBean", paginationBean);
//		model.addAttribute("loginname", principal.getName());
//		return TEMPLATE_MERCHANT;
//
//	}

	@RequestMapping(value = { "/payoutlist/{currPage}" }, method = RequestMethod.GET)
	public String settlement(final Model model, @PathVariable final int currPage, HttpServletRequest request,
			HttpServletResponse response, final java.security.Principal principal) {
		logger.info("pg no: " + currPage);
		HttpSession session = request.getSession();

		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		String myName = (String) session.getAttribute("userName");
		String userName = principal.getName();
		model.addAttribute("pageBean", pageBean);
		logger.info("Payout Summary:" + myName);
		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);
		Merchant merchant = merchantService.loadMerchant(myName);
//		String merchantName = merchant.getBusinessShortName();
		String merchantID = String.valueOf(merchant.getId());
		model.addAttribute("xpayId", merchant.getId());
		transactionService.listPayoutTransactionByMerchant(paginationBean, null, null, merchantID, currPage);

		if (userName.equalsIgnoreCase("daria.ar@monetix.pro")) {
			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");
		}
		// empty pop up modal
		logger.info("size of paginationbean before" + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			paginationBean.setItemList(new ArrayList<>());
			logger.info("size of paginationbean after" + paginationBean.getItemList().size());
			model.addAttribute("responseData", "No Records found"); // table
			// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());

		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/searchPayout" }, method = RequestMethod.GET)
	public String displayEzyLinkTransactionList(HttpServletRequest request, HttpServletResponse response,
			final Model model, @RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("Pg no: " + currPage);
		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);
		HttpSession session = request.getSession();
		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);
		String myName = (String) session.getAttribute("userName");
		String userName = principal.getName();
		Merchant merchant = merchantService.loadMerchant(myName);
//		String merchantName = merchant.getBusinessShortName();
		String merchantID = String.valueOf(merchant.getId());

		if ((date == null || date.equalsIgnoreCase("null")) && (date1 == null || date1.equalsIgnoreCase("null"))) {
			transactionService.listPayoutTransactionByMerchant(paginationBean, null, null, merchantID, currPage);
		} else {
			transactionService.listPayoutTransactionByMerchantForCurrDate(paginationBean, date, date1, merchantID,
					currPage);
		}
		if (userName.equalsIgnoreCase("daria.ar@monetix.pro")) {
			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}
		// empty pop up modal
		// empty pop up modal
		logger.info("size of paginationbean before" + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			paginationBean.setItemList(new ArrayList<>());
			logger.info("size of paginationbean" + paginationBean.getItemList().size());
			model.addAttribute("responseData", "No Records found"); // table
			// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());

		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/exportPayout", method = RequestMethod.GET)
	public ModelAndView getExcelEzyLink(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export, final java.security.Principal principal,
			HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);
		String myName = (String) session.getAttribute("userName");
		Merchant merchant = merchantService.loadMerchant(myName);
//		String merchantName = merchant.getBusinessShortName();
		String merchantID = String.valueOf(merchant.getId());

		// transactionService.listPayoutTransactionByMerchant(paginationBean, date,
		// date1, merchantName,currPage);
		transactionService.listPayoutTransactionByMerchantExport(paginationBean, date, date1, merchantID, currPage,
				export);
		String userName = principal.getName();
		if (userName.equalsIgnoreCase("daria.ar@monetix.pro")) {
			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");
		}
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found");
		} else {
			model.addAttribute("responseData", null);
		}
		List<PayoutModel> list = paginationBean.getItemList();
		if (list.size() == 0) {

			PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutList",
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("exportData", "noRecords");

			ModelAndView modelAndView = new ModelAndView(TEMPLATE_MERCHANT);

			return modelAndView;

		} else {

			if (!(export.equals("PDF"))) {
				return new ModelAndView("PayoutSummaryExcel", "txnList", list);

			} else {
				return new ModelAndView("PayoutSummaryPdf", "txnList", list);
			}
		}
	}

//	@RequestMapping(value = { "/searchPayout" }, method = RequestMethod.GET)
//	public String displayEzyLinkTransactionList(HttpServletRequest request, HttpServletResponse response,
//			final Model model, @RequestParam final String date, @RequestParam final String date1,
//			@RequestParam(required = false, defaultValue = "1") final int currPage,
//			final java.security.Principal principal) {
//
//		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutList",
//				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//
//		model.addAttribute("pageBean", pageBean);
//		HttpSession session = request.getSession();
//
//		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
//		paginationBean.setCurrPage(currPage);
//		String myName = (String) session.getAttribute("userName");
//		String userName = principal.getName();
//		Merchant merchant = merchantService.loadMerchant(myName);
//		String merchantName = merchant.getBusinessShortName();
//
//		transactionService.listPayoutTransactionByMerchant(paginationBean, date, date1, merchantName);
//
//		if (userName.equalsIgnoreCase("daria.ar@monetix.pro")) {
//
//			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
//			model.addAttribute("localAdmin", "No");
//
//		}
//
//		model.addAttribute("paginationBean", paginationBean);
//		model.addAttribute("loginname", principal.getName());
//		return TEMPLATE_MERCHANT;
//
//	}

//	@RequestMapping(value = "/exportPayout", method = RequestMethod.GET)
//	public ModelAndView getExcelEzyLink(@RequestParam final String date, @RequestParam final String date1,
//			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
//			@RequestParam(required = false) String export, final java.security.Principal principal,
//			HttpServletRequest request, HttpServletResponse response) {
//		HttpSession session = request.getSession();
//		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
//		paginationBean.setCurrPage(currPage);
//
//		String myName = (String) session.getAttribute("userName");
//
//		Merchant merchant = merchantService.loadMerchant(myName);
//		String merchantName = merchant.getBusinessShortName();
//
//		transactionService.listPayoutTransactionByMerchant(paginationBean, date, date1, merchantName);
//
//		String userName = principal.getName();
//
//		if (userName.equalsIgnoreCase("daria.ar@monetix.pro")) {
//
//			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
//			model.addAttribute("localAdmin", "No");
//
//		}
//
//		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
//			model.addAttribute("responseData", "No Records found");
//
//		} else {
//			model.addAttribute("responseData", null);
//		}
//
//		List<PayoutModel> list = paginationBean.getItemList();
//
//		if (!(export.equals("PDF"))) {
//
//			return new ModelAndView("PayoutSummaryExcel", "txnList", list);
//
//		} else {
//			return new ModelAndView("PayoutSummaryPdf", "txnList", list);
//		}
//	}

	// PAYOUT BALANCE

	@RequestMapping(value = { "/payoutbalance" }, method = RequestMethod.GET)
	public String payoutbalance(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal, HttpServletRequest request) {

		logger.info("Payout Summary balance  :********* " + principal.getName());

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		String adminusername = principal.getName();
		logger.info("Adminusername :" + adminusername);
		logger.info("currently logged in as request: " + request.getSession());

		logger.info("currently logged in as " + myName);

		logger.info("Payout Summary balance  :********* " + myName);

		Merchant currentMerchant = merchantService.loadMerchant(myName);
		currentMerchant.getId();

		logger.info("Payout Summary balance  :********* " + currentMerchant.getId());

		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutBalance",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.loadPayoutbalance(paginationBean, currentMerchant.getId());

		// New changes
		PayoutBankBalance payoutbankAmount = merchantDao.loadBankbalance();
		logger.info("Payout bank balance  : " + payoutbankAmount.getAmount());

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found");
		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("payoutbankAmount", payoutbankAmount.getAmount());
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("merchantid", currentMerchant.getId());
		model.addAttribute("adminusername", adminusername);
		model.addAttribute("loginname", principal.getName());

		return TEMPLATE_MERCHANT;
	}

	// With Draw JSP

	@RequestMapping(value = { "/withDrawPage" }, method = RequestMethod.GET)
	public String WithDrawJsp(final Model model, @RequestParam(required = false, defaultValue = "1") final int currPage,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {

		HttpSession session = request.getSession();

		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/WithDrawPage",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// Payout withdraw changes

	// Old payout

//	@RequestMapping(value = { "/withDraw" }, method = RequestMethod.GET)
//	public String WithDrawMethod(Model model, HttpServletRequest request, @RequestParam final String amount,
//			@RequestParam final String merchantId, final java.security.Principal principal) throws ParseException {
//
//		logger.info("WithDraw Amount from JSP : " + amount);
//		logger.info("WithDraw merchantId from JSP : " + merchantId);
//
//		String inputLine = null;
//		String output = null;
//		String csvFile = null;
//		String clearencefile = null;
//		String responseData = null;
//		int responseCode = 0;
//		String finalBalanceNetAmt = null;
//		PayoutModel var = new PayoutModel();
//		HttpSession session = request.getSession();
//
//		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutBalance",
//				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//
//		String userName = principal.getName();
//		model.addAttribute("pageBean", pageBean);
//		logger.info("Payout With Draw :" + (String) session.getAttribute("userName"));
//		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
//		Merchant merchant = merchantService.loadMerchant((String) session.getAttribute("userName"));
//
//		DecimalFormat decimalFormat = new DecimalFormat("##0.00");
//		double parsedAmount = decimalFormat.parse(amount).doubleValue();
//		String formattedNetAmount = decimalFormat.format(parsedAmount);
//		logger.info("Formatted Net amount : " + formattedNetAmount);
//
//		// Triggering External API to validate SettlementBalance Table and update in the
//		// database (Using Synchronized Block)
//		try {
//			logger.info("Deposit Api Url :" + PropertyLoad.getFile().getProperty("SYNCHRONIZED_BLOCK_API"));
//			URL url = new URL(PropertyLoad.getFile().getProperty("SYNCHRONIZED_BLOCK_API"));
//
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//			connection.setRequestMethod("POST");
//
//			connection.setDoOutput(true);
//
//			connection.setRequestProperty("Content-Type", "application/json");
//
//			JsonObject jsonBody = new JsonObject();
//			jsonBody.addProperty("service", "PAYOUT_WITHDRAW");
//			jsonBody.addProperty("parsedAmount", String.valueOf(parsedAmount));
//			jsonBody.addProperty("merchantId", merchantId);
//
//			String data = new Gson().toJson(jsonBody);
//
//			logger.info("Request : " + data);
//
//			try (OutputStream os = connection.getOutputStream()) {
//				byte[] input = data.getBytes(StandardCharsets.UTF_8);
//				os.write(input, 0, input.length);
//			}
//
//			responseCode = connection.getResponseCode();
//			if (responseCode != HttpURLConnection.HTTP_OK) {
//				logger.error("External API Request failed with response code: " + responseCode);
//			} else {
//				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//					StringBuilder response = new StringBuilder();
//					String line;
//					while ((line = reader.readLine()) != null) {
//						response.append(line);
//					}
//					responseData = response.toString();
//					logger.info("API Response: " + responseData);
//
//					JSONObject jsonObject = new JSONObject(responseData);
//					JSONObject responseDataInExternalApi = jsonObject.getJSONObject("updateResponseData");
//
//					finalBalanceNetAmt = responseDataInExternalApi.getString("finalBalanceNetAmt");
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//
//			connection.disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("Error while Connecting With External API");
//		}
//
//		if (responseCode == HttpURLConnection.HTTP_OK) {
//
//			List<SettlementMailList> financeMailList = new ArrayList<SettlementMailList>();
//
//			// file send by cs team new add
//			Date daysAgo = new Date();
//			logger.info("Data : " + daysAgo);
//			String resDate = new SimpleDateFormat("dd-MMM-yyyy").format(daysAgo);
//			String filePrtn = PropertyLoad.getFile().getProperty("SETTLEMENT_CLEARENCE_PRTN");
//			csvFile = resDate + filePrtn;
//
//			logger.info("csvFile pattern : " + csvFile);
//
//			SettlementMailList settleList = new SettlementMailList();
//
//			settleList.setAccNo(merchant.getBankAcc());
//			settleList.setBalanceNetAmt(finalBalanceNetAmt);
//			settleList.setBankName(merchant.getBankName());
//			settleList.setMerchantName(merchant.getUsername());
//			settleList.setMid(merchant.getMid().getMid());
//
//			financeMailList.add(settleList);
//			FileWriter fileWriter = null;
//
//			try {
//
//				fileWriter = new FileWriter(PropertyLoad.getFile().getProperty("CLEARENCEFILEPATH") + csvFile);
//				fileWriter.append(PAYER_HEADER.toString().toUpperCase());
//				fileWriter.append(NEW_LINE_SEPARATOR);
//
//				for (SettlementMailList settlementList : financeMailList) {
//
//					// Amount formate
//					String NetAmt1 = settlementList.getBalanceNetAmt();
//					String amountWithoutCommas = NetAmt1.replace(",", "");
//					logger.info("amountWithoutCommas: " + amountWithoutCommas);
//					double amount1 = 0;
//					try {
//						NumberFormat numberFormat = NumberFormat.getInstance();
//						amount1 = numberFormat.parse(amountWithoutCommas).doubleValue();
//					} catch (ParseException e) {
//						logger.info("Invalid Net amount format: " + NetAmt1);
//
//					}
//
//					fileWriter.append(settlementList.getMerchantName());
//					fileWriter.append(COMMA_DELIMITER);
//					fileWriter.append(settlementList.getMid());
//					fileWriter.append(COMMA_DELIMITER);
//					fileWriter.append(settlementList.getAccNo());
//					fileWriter.append(COMMA_DELIMITER);
//					fileWriter.append(settlementList.getBankName());
//					fileWriter.append(COMMA_DELIMITER);
//					fileWriter.append(amountWithoutCommas);
//					fileWriter.append(NEW_LINE_SEPARATOR);
//				}
//
//			} catch (Exception e) {
//				logger.error("Error in CsvFileWriter !!!");
//				e.printStackTrace();
//			} finally {
//				try {
//					fileWriter.flush();
//					fileWriter.close();
//				} catch (IOException e) {
//					logger.error("Error while flushing/closing fileWriter !!!");
//					e.printStackTrace();
//				}
//			}
//
//			clearencefile = PropertyLoad.getFile().getProperty("CLEARENCEFILEPATH") + csvFile;
//
//			File file = new File(clearencefile);
//			if (!file.exists()) {
//				logger.info("Clearence File Not Generated");
//			} else {
//
//				logger.info("Clearence File is Generated");
//				responseData = sendEmail(csvFile);
//			}
//		}
//
//		transactionService.loadPayoutbalance(paginationBean, merchant.getId());
//		String merchantName = merchant.getBusinessShortName();
//		logger.info("Short Name :  " + merchantName);
//		model.addAttribute("merchantid", merchant.getId());
//		model.addAttribute("paginationBean", paginationBean);
//		model.addAttribute("adminusername", userName);
//
//		return TEMPLATE_MERCHANT;
//	}

	@RequestMapping(value = { "/withDraw" }, method = RequestMethod.GET)
	public String WithDrawMethod(Model model, HttpServletRequest request,
			@RequestParam("finalwithdrawamount") String amount, @RequestParam final String merchantId,
			@RequestParam final String withdrawType, @RequestParam final String overDraftFee,
			final java.security.Principal principal) throws ParseException {

		logger.info("WithDraw Amount from JSP : " + amount + ", corresponding merchantId : " + merchantId);

		String fee = overDraftFee.substring(3).replace(",", "");

		HttpSession session = request.getSession();
		String userName = principal.getName();

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		Merchant merchant = merchantService.loadMerchant((String) session.getAttribute("userName"));

		// Generating Payout-ID referance for every withdraw
		UUID uuid = UUID.randomUUID();
		String payoutID = uuid.toString();
		String withdrawAmount = amount.replace(",", "");
		DecimalFormat decimalFormat = new DecimalFormat("##0.00");
		double parsedAmount = decimalFormat.parse(withdrawAmount).doubleValue();
		String formattedWithdrawAmount = decimalFormat.format(parsedAmount);

		// Obtain mobiApiKey by merchantId
		String motoApiKey = merchantDao.loadMobileUserById(Long.valueOf(merchantId)).getMotoApiKey();

		// Sum the fee with withdraw amount for 'OverDraftAdjustment' alone
		String formattedOverdraftFee = null;
		if (withdrawType.equalsIgnoreCase("OverDraftAdjustment")) {

			double parsedOverdraftFee = decimalFormat.parse(fee).doubleValue();
			parsedAmount = Double.sum(parsedAmount, parsedOverdraftFee);

			formattedWithdrawAmount = decimalFormat.format(parsedAmount);
			formattedOverdraftFee = decimalFormat.format(parsedOverdraftFee);
		}

		// fetch bank balance
		PayoutBankBalance payoutbankAmount = merchantDao.loadBankbalance();

		logger.info("WithdrawType : " + withdrawType + ", Formatted Withdraw amount : " + formattedWithdrawAmount
				+ ", overDraftFee : " + formattedOverdraftFee);

		int externalApiHttpStatusCode = 0;
		String preWithdrawBalanceNetAmount = null;
		String preWithdrawNetAmount = null;
		String postWithdrawBalanceNetAmount = null;
		String postWithdrawNetAmount = null;
		String externalApiResponseStatus = null;
		String externalApiResponseMessage = null;
		String preWithdrawDepositAmount = null;
		String postWithdrawDepositAmount = null;

		/*
		 * (to achieve a synchronized lock for the Settlement Balance table)Trigger
		 * external API /payoutservice - Which contain WithdrawLogic
		 */
		try {
//          if (Double.parseDouble(withdrawAmount) <= 10 && Double.parseDouble(withdrawAmount) == 50000.0) {
//              logger.warn("Withdraw amount provided is less >10 RM or equal 50,000 RM ");
//              throw new MobiException(Status.WITHDRAW_FAILURE);
//          }

			logger.info("Deposit Api Url :" + PropertyLoad.getFile().getProperty("SYNCHRONIZED_BLOCK_API")
					+ ", MotoApiKey : " + motoApiKey);
			URL url = new URL(PropertyLoad.getFile().getProperty("SYNCHRONIZED_BLOCK_API"));

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");

			JsonObject jsonBody = new JsonObject();
			jsonBody.addProperty("service", "PAYOUT_WITHDRAW");
			jsonBody.addProperty("mobiApiKey", motoApiKey);
			jsonBody.addProperty("parsedAmount", String.valueOf(parsedAmount));
			jsonBody.addProperty("merchantId", merchantId);
			jsonBody.addProperty("revertedWithdrawalAmount", "NO");

			String data = new Gson().toJson(jsonBody);

			logger.info("Request : " + data);

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = data.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			externalApiHttpStatusCode = connection.getResponseCode();
			if (externalApiHttpStatusCode != HttpURLConnection.HTTP_OK) {
				logger.error("External API Request failed with response code: " + externalApiHttpStatusCode);
			} else {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuilder response1 = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response1.append(line);
					}
					String responseData = response1.toString();
					logger.info("API Response: " + responseData);

					JSONObject jsonObject = new JSONObject(responseData);
					externalApiResponseStatus = jsonObject.getString("responseCode");
					externalApiResponseMessage = jsonObject.getString("responseMessage");

					if (externalApiResponseMessage.equals("FAILURE")) {
						throw new MobiException(Status.WITHDRAW_FAILURE);
					}

					JSONObject apiResponseData = jsonObject.getJSONObject("updateResponseData");

//                    withdrawAmount = apiResponseData.getString("withdrawAmount");

					preWithdrawBalanceNetAmount = apiResponseData.getString("preWithdrawBalanceNetAmount");
					preWithdrawNetAmount = apiResponseData.getString("preWithdrawNetAmount");
					preWithdrawDepositAmount = apiResponseData.getString("preWithdrawDepositAmount");

					postWithdrawBalanceNetAmount = apiResponseData.getString("postWithdrawBalanceNetAmount");
					postWithdrawNetAmount = apiResponseData.getString("postWithdrawNetAmount");
					postWithdrawDepositAmount = apiResponseData.getString("postWithdrawDepositAmount");

				} catch (IOException e) {
					e.printStackTrace();
					logger.error("Exception connecting to external Api : " + e.getMessage());
				}
			}

			connection.disconnect();
		} catch (Exception e) {

			e.printStackTrace();
			logger.error("Error while Connecting With External API Or Withdraw Failure " + e.getMessage());
		}
		if (externalApiHttpStatusCode == HttpURLConnection.HTTP_OK) {

			if (externalApiResponseMessage.equals("SUCCESS")) {

				logger.info("Generated PayoutID : " + payoutID);

				// Updating WithdrawalTransactionsDetails in DB as SUCCESS,If Success
				WithdrawalTransactionsDetails withdrawDetails = new WithdrawalTransactionsDetails();
				withdrawDetails.setCreatedDate(getCurrentTimestamp());
				withdrawDetails.setMerchantId(merchantId);
				withdrawDetails.setPayoutFundDisbursmentStatus("NotApproved");
				withdrawDetails.setWithdrawAmount(withdrawAmount);
				withdrawDetails.setPayoutId(payoutID);
//				withdrawDetails.setPreWithdrawSettlementBalance(preWithdrawNetAmount);
//				withdrawDetails.setPreWithdrawTotalBalance(preWithdrawBalanceNetAmount);
//				withdrawDetails.setPreWithdrawDepositAmount(preWithdrawDepositAmount);
//				withdrawDetails.setPostWithdrawSettlementBalance(postWithdrawNetAmount);
//				withdrawDetails.setPostWithdrawTotalBalance(postWithdrawBalanceNetAmount);
//				withdrawDetails.setPostWithdrawDepositAmount(postWithdrawDepositAmount);

				if (withdrawType.equalsIgnoreCase("SettlementAdjustment")) {
					withdrawDetails.setWithdrawType("Settlement Adjustment");
				}
				if (withdrawType.equalsIgnoreCase("DepositAdjustment")) {
					withdrawDetails.setWithdrawType("Deposit Adjustment");
				}
				if (withdrawType.equalsIgnoreCase("OverDraftAdjustment")) {
//					withdrawDetails.setOverdraftFee(fee);
					withdrawDetails.setWithdrawType("OverDraft Adjustment");
				}
				if (withdrawType.equalsIgnoreCase("Withdraw")) {
					withdrawDetails.setWithdrawType("Withdraw");
				}

				double sumofPostWithdrawBalance = Double.parseDouble(postWithdrawNetAmount)
						+ Double.parseDouble(postWithdrawBalanceNetAmount)
						+ Double.parseDouble(postWithdrawDepositAmount);
				double sumofPreWithdrawBalance = Double.parseDouble(preWithdrawNetAmount.replace(",", ""))
						+ Double.parseDouble(preWithdrawBalanceNetAmount.replace(",", ""))
						+ Double.parseDouble(preWithdrawDepositAmount.replace(",", ""));
				logger.info("SumofPostWithdraw TotalBalance : " + sumofPostWithdrawBalance);
				logger.info("SumofPreWithdraw TotalBalance : " + sumofPreWithdrawBalance);

				// Trigger E-Mail if only success from ExternalAPI
				String mailResponse = sendEmailtoFinance(payoutID, withdrawAmount, merchantId,
						payoutbankAmount.getAmount(), merchant, sumofPostWithdrawBalance, sumofPreWithdrawBalance,
						withdrawDetails.getWithdrawType(), formattedOverdraftFee);

				// Formatting the Withdraw type string to store in db
				withdrawDetails.setWithdrawType(withdrawDetails.getWithdrawType().replace(" ", ""));

				// Make a entry in WithdrawalTransactionsDetails table
				merchantDao.updateWithdrawdetailsInWithdrawalTransactionDetailsTable(withdrawDetails);
			} else {
				logger.info("Generated PayoutID : " + payoutID);

				// Updating WithdrawalTransactionsDetails in DB as Failed
				WithdrawalTransactionsDetails withdrawDetails = new WithdrawalTransactionsDetails();
				withdrawDetails.setCreatedDate(getCurrentTimestamp());
				withdrawDetails.setMerchantId(merchantId);
				withdrawDetails.setPayoutFundDisbursmentStatus("Withdraw Failed");
				withdrawDetails.setWithdrawAmount(withdrawAmount);
				withdrawDetails.setPayoutId(payoutID);
//				withdrawDetails.setPreWithdrawSettlementBalance(preWithdrawNetAmount);
//				withdrawDetails.setPreWithdrawTotalBalance(preWithdrawBalanceNetAmount);
//				withdrawDetails.setPreWithdrawDepositAmount(preWithdrawDepositAmount);
//				withdrawDetails.setPostWithdrawSettlementBalance(postWithdrawNetAmount);
//				withdrawDetails.setPostWithdrawTotalBalance(postWithdrawBalanceNetAmount);
//				withdrawDetails.setPostWithdrawDepositAmount(postWithdrawDepositAmount);

				// Make a entry in WithdrawalTransactionsDetails table
				merchantDao.updateWithdrawdetailsInWithdrawalTransactionDetailsTable(withdrawDetails);
			}
		} else {
			logger.info("Generated PayoutID : " + payoutID);

			// Updating WithdrawalTransactionsDetails in DB as Failed
			WithdrawalTransactionsDetails withdrawDetails = new WithdrawalTransactionsDetails();
			withdrawDetails.setCreatedDate(getCurrentTimestamp());
			withdrawDetails.setMerchantId(merchantId);
			withdrawDetails.setPayoutFundDisbursmentStatus("Cannot Connect ExternalAPI");
			withdrawDetails.setWithdrawAmount(withdrawAmount);
			withdrawDetails.setPayoutId(payoutID);
//			withdrawDetails.setPreWithdrawSettlementBalance(preWithdrawNetAmount);
//			withdrawDetails.setPreWithdrawTotalBalance(preWithdrawBalanceNetAmount);
//			withdrawDetails.setPreWithdrawDepositAmount(preWithdrawDepositAmount);
//			withdrawDetails.setPostWithdrawSettlementBalance(postWithdrawNetAmount);
//			withdrawDetails.setPostWithdrawTotalBalance(postWithdrawBalanceNetAmount);
//			withdrawDetails.setPostWithdrawDepositAmount(postWithdrawDepositAmount);

			// Make a entry in WithdrawalTransactionsDetails table
			merchantDao.updateWithdrawdetailsInWithdrawalTransactionDetailsTable(withdrawDetails);
		}
		transactionService.loadPayoutbalance(paginationBean, merchant.getId());
		String merchantName = merchant.getBusinessShortName();

		PayoutBankBalance payoutBankBalance = merchantDao.loadBankbalance();
		model.addAttribute("payoutbankAmount", payoutBankBalance.getAmount());

		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutBalance",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantid", merchant.getId());
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("adminusername", userName);
		model.addAttribute("loginname", principal.getName());

		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/withDraw1" }, method = RequestMethod.POST)
	public String WithDrawMethod1(Model model, HttpServletRequest request, @ModelAttribute("amount") final String brn,
			@RequestParam final String amount, final java.security.Principal principal) {
		logger.info("WithDraw Amount from JSP : " + amount);
		logger.info("brn @@@: " + brn);

		PayoutModel var = new PayoutModel();
		HttpSession session = request.getSession();

		logger.info("WithDraw Amount from JSP : " + amount);
		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutBalance",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		String myName = (String) session.getAttribute("userName");
		String userName = principal.getName();
		model.addAttribute("pageBean", pageBean);
		logger.info("Payout With Draw :" + myName);
		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		// paginationBean.setCurrPage(currPage);
		Merchant merchant = merchantService.loadMerchant(myName);
		String merchantName = merchant.getBusinessShortName();
		logger.info("Short Name :  " + merchantName);

		// transactionService.withDrawAmount(paginationBean, merchantName);

		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		try {
			url = new URL(getWithDrawUrl());

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			JSONObject params = new JSONObject();
			BankCode bank = new BankCode();

			String bicCode = bank.getBankBicCode(merchant.getBankName());

			logger.info("merchant name :" + merchant.getBusinessName());
			logger.info("Bank name :" + merchant.getBankName());
			logger.info("creditorAccNo :" + merchant.getBankAcc());
			logger.info("Bic code :" + bicCode);
			logger.info("amount :" + amount);
			logger.info("Service :" + "PAYOUT_TXN_WITHDRAW");
			logger.info("id :" + merchant.getId());

			String id = Long.toString(merchant.getId());

			params.put("creditorAccName", String.valueOf(merchant.getBusinessName()));

			params.put("service", "PAYOUT_TXN_WITHDRAW");
			params.put("mobiApiKey", "");
			params.put("brn", "");
			params.put("biccode", bicCode);
			params.put("bankName", merchant.getBankName());
			params.put("creditorAccNo", merchant.getBankAcc());
			params.put("amt", amount);
			params.put("id", id);

			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			System.out.println("The params That passed" + paramss);
			os.flush();
			StringBuffer response = new StringBuffer();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine = br.readLine()) != null) {
				response.append(inputLine);
			}
			output = response.toString();
			System.out.println("The params output ::" + output);
			Gson gson = new Gson();
			var = gson.fromJson(output, PayoutModel.class);

			logger.info("respose code :" + var.getResponseCode());
			// var.setResponseCode(var.getResponseCode());
			paginationBean.setResponseCode(var.getResponseCode());
			// var.getResponseCode()
		} catch (Exception e) {
			// TODO: handle exception
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// getUrl for WITHDRAW

	public static String getWithDrawUrl() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			logger.info("Path :" + prop.getProperty("WithDraw"));
			path = prop.getProperty("WithDraw");
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

	@RequestMapping(value = { "/MerchantGrab" }, method = RequestMethod.GET)
	public String listadmin(final Model model, final java.security.Principal principal, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  UM-EZWAY Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "transaction/merchantGrab(E-wallet)",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

//	        if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//	        	 String ezywaymid = "000000000021591";
//		            String umezywaymid = null;
//		            String ummid =null;
//		            String motomid = null;
//		            String ezyrecmid =null;
//		            String mid=null;
//		            String bnplMid=null;
//		            String boostmid=null;
//		            String tngMid=null;
//		            String shoppyMid=null;
//		            String grabmid =null;
//		            transactionService.merchantEwallet(paginationBean, null, null, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "GRABPAY",
//		                    currentMerchant);
//	        } else {
		transactionService.merchantEwallet(paginationBean, null, null, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "GRABPAY",
				currentMerchant);
		// }
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);
		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());
			if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
				model.addAttribute("score", "yes");
			} else {
				model.addAttribute("score", "no");
			}

			String UmEzywayMid = null;
			String ezywaymid = null;
			String ummid = null;
			String motomid = null;
			String ezyrecmid = null;
			String mid = null;
			Merchant merchant = merchantService.loadMerchant(myName);

			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getEzywayMid() != null) {
				ezywaymid = merchant.getMid().getEzywayMid();
			}
			if (merchant.getMid().getUmMid() != null) {
				ummid = merchant.getMid().getUmMid();
			}
			if (merchant.getMid().getUmMotoMid() != null) {
				motomid = merchant.getMid().getUmMotoMid();
			}
			if (merchant.getMid().getUmEzyrecMid() != null) {
				ezyrecmid = merchant.getMid().getUmEzyrecMid();
			}
			if (merchant.getMid().getMid() != null) {
				ezyrecmid = merchant.getMid().getMid();
			}
			model.addAttribute("UmEzywayMid", UmEzywayMid);
			model.addAttribute("ezywaymid", ezywaymid);
			model.addAttribute("ummid", ummid);
			model.addAttribute("motomid", motomid);
			model.addAttribute("ezyrecmid", ezyrecmid);
			model.addAttribute("mid", mid);

		}
		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/searchGrabTransactionlist" }, method = RequestMethod.GET)
	public String searchUMezywaygrab(final Model model, final java.security.Principal principal,
			@RequestParam final String date, @RequestParam final String date1, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  GRAB Transaction ");

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "transaction/merchantGrab(E-wallet)",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
//	        if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//	   
//	        	 String ezywaymid = "000000000021591";
//		            String umezywaymid = null;
//		            String ummid =null;
//		            String motomid = null;
//		            String ezyrecmid =null;
//		            String mid=null;
//		            String bnplMid=null;
//		            String boostmid=null;
//		            String tngMid=null;
//		            String shoppyMid=null;
//		            String grabmid =null;
//		            transactionService.merchantEwallet(paginationBean, date, date1, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "GRABPAY",
//		                    currentMerchant);
//	            
//	        } else {
		transactionService.merchantEwallet(paginationBean, fromDate, toDate, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "GRABPAY",
				currentMerchant);

		// }
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);
		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());
			if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
				model.addAttribute("score", "yes");
			} else {
				model.addAttribute("score", "no");
			}

			String UmEzywayMid = null;
			String ezywaymid = null;
			String ummid = null;
			String motomid = null;
			String ezyrecmid = null;
			String mid = null;
			Merchant merchant = merchantService.loadMerchant(myName);

			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getEzywayMid() != null) {
				ezywaymid = merchant.getMid().getEzywayMid();
			}
			if (merchant.getMid().getUmMid() != null) {
				ummid = merchant.getMid().getUmMid();
			}
			if (merchant.getMid().getUmMotoMid() != null) {
				motomid = merchant.getMid().getUmMotoMid();
			}
			if (merchant.getMid().getUmEzyrecMid() != null) {
				ezyrecmid = merchant.getMid().getUmEzyrecMid();
			}
			if (merchant.getMid().getMid() != null) {
				ezyrecmid = merchant.getMid().getMid();
			}
			model.addAttribute("UmEzywayMid", UmEzywayMid);
			model.addAttribute("ezywaymid", ezywaymid);
			model.addAttribute("ummid", ummid);
			model.addAttribute("motomid", motomid);
			model.addAttribute("ezyrecmid", ezyrecmid);
			model.addAttribute("mid", mid);

		}

		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/exportGrabTransactionlist", method = RequestMethod.GET)
	public ModelAndView ExportumEzywayGrab1(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			final java.security.Principal principal) {

		logger.info("export Grab : ");
		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromdate;
		String dat1 = todate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		// paginationBean.setCurrPage(currPage);

		// String txnStatus="A";
//			transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1,txnStatus,currentMerchant.getMid().getUmEzywayMid(),"ALL");
//		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//			 String ezywaymid = "000000000021591";
//	            String umezywaymid = null;
//	            String ummid =null;
//	            String motomid = null;
//	            String ezyrecmid =null;
//	            String mid=null;
//	            String bnplMid=null;
//	            String boostmid=null;
//	            String tngMid=null;
//	            String shoppyMid=null;
//	            String grabmid=null;
//	            transactionService.merchantEwallet1(paginationBean, fromDate, toDate, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "GRABPAY",
//	                    currentMerchant);
//		} else {
		transactionService.merchantEwallet1(paginationBean, fromdate, todate, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "GRABPAY",
				currentMerchant);

		// }
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (list1.size() == 0) {

			PageBean pageBean = new PageBean("transactions list", "transaction/merchantGrab(E-wallet)",

					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

			model.addAttribute("pageBean", pageBean);

			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("exportData", "noRecords");

			ModelAndView modelAndView = new ModelAndView(TEMPLATE_MERCHANT);

			return modelAndView;

		} else {

			if (!(export.equals("PDF"))) {
				// return new ModelAndView("txnListUMExcel", "umTxnList", list1);
				return new ModelAndView("txnListMerchantGrabExcel", "umTxnList", list1);
			} else {
				return new ModelAndView("EwalletMerchantTxnPdf", "umTxnList", list1);
			}
		}

	}

	@RequestMapping(value = { "/merchantBoostlist" }, method = RequestMethod.GET)
	public String MerchantBoostlist1(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("Boost Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "transaction/merchantBoost(E-wallet)",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

//	        if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//	            String ezywaymid = "000000000021591";
//	            String umezywaymid = null;
//	            String ummid =null;
//	            String motomid = null;
//	            String ezyrecmid =null;
//	            String mid=null;
//	            String bnplMid=null;
//	            String boostmid=null;
//	            String tngMid=null;
//	            String shoppyMid=null;
//	            String grabmid=null;
//	            transactionService.merchantEwallet(paginationBean, null, null, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "BOOST",
//	                    currentMerchant);
//	        } else {
		transactionService.merchantEwallet(paginationBean, null, null, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "BOOST",
				currentMerchant);

//	        }

		model.addAttribute("paginationBean", paginationBean);
		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());
			if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
				model.addAttribute("score", "yes");
			} else {
				model.addAttribute("score", "no");
			}
			String UmEzywayMid = null;
			Merchant merchant = merchantService.loadMerchant(myName);
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			model.addAttribute("UmEzywayMid", UmEzywayMid);
		}
		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";
			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");
			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/searchmerchantBoostlist" }, method = RequestMethod.GET)
	public String searchBoostTransaction(final Model model, final java.security.Principal principal,
			@RequestParam final String date, @RequestParam final String date1, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("Boost Transaction ");

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "transaction/merchantBoost(E-wallet)",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
//UMEzymoto m1=new UMEzymoto;
//	        if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//	        	 String ezywaymid = "000000000021591";
//		            String umezywaymid = null;
//		            String ummid =null;
//		            String motomid = null;
//		            String ezyrecmid =null;
//		            String mid=null;
//		            String bnplMid=null;
//		            String boostmid=null;
//		            String tngMid=null;
//		            String shoppyMid=null;
//		            String grabmid=null;
//		            transactionService.merchantEwallet(paginationBean, date, date1, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "BOOST",
//		                    currentMerchant);
//	        } else {
		transactionService.merchantEwallet(paginationBean, fromDate, toDate, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "BOOST",
				currentMerchant);

		// }

		model.addAttribute("paginationBean", paginationBean);
		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());
			if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
				model.addAttribute("score", "yes");
			} else {
				model.addAttribute("score", "no");
			}
			String UmEzywayMid = null;
			Merchant merchant = merchantService.loadMerchant(myName);
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			model.addAttribute("UmEzywayMid", UmEzywayMid);
		}
		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";
			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");
			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/exportBoosttransactionlist", method = RequestMethod.GET)
	public ModelAndView exportumEzywayBoost(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			final java.security.Principal principal) {

		logger.info("export boost : ");
		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());

		String dat = fromdate;
		String dat1 = todate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		// paginationBean.setCurrPage(currPage);

		// String txnStatus="A";
//			transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1,txnStatus,currentMerchant.getMid().getUmEzywayMid(),"ALL");
//		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//			 String ezywaymid = "000000000021591";
//	            String umezywaymid = null;
//	            String ummid =null;
//	            String motomid = null;
//	            String ezyrecmid =null;
//	            String mid=null;
//	            String bnplMid=null;
//	            String boostmid=null;
//	            String tngMid=null;
//	            String shoppyMid=null;
//	            String grabmid = null;
//	            transactionService.merchantEwallet1(paginationBean, fromDate, toDate, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "BOOST",
//	                    currentMerchant);
//		} else {
		transactionService.merchantEwallet1(paginationBean, fromdate, todate, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "BOOST",
				currentMerchant);

		// }
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (list1.size() == 0) {

			PageBean pageBean = new PageBean("transactions list", "transaction/merchantBoost(E-wallet)",
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

			model.addAttribute("pageBean", pageBean);

			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("exportData", "noRecords");

			ModelAndView modelAndView = new ModelAndView(TEMPLATE_MERCHANT);

			return modelAndView;

		} else {

			if (!(export.equals("PDF"))) {
				// return new ModelAndView("txnListUMExcel", "umTxnList", list1);
				return new ModelAndView("txnListMerchantBoostWithoutFPXExcel", "umTxnList", list1);
			} else {
				return new ModelAndView("EwalletMerchantTxnPdf", "umTxnList", list1);
			}
		}

	}

	@RequestMapping(value = { "/merchantTNGTransaction" }, method = RequestMethod.GET)
	public String MerchantBoostlist(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("TNG Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantname = currentMerchant.getBusinessName();
		PageBean pageBean = new PageBean("transactions list", "transaction/merchantTNGpay(E-wallet)",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
//	        if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//	        	 String ezywaymid = "000000000021591";
//		            String umezywaymid = null;
//		            String ummid =null;
//		            String motomid = null;
//		            String ezyrecmid =null;
//		            String mid=null;
//		            String bnplMid=null;
//		            String boostmid=null;
//		            String tngMid=null;
//		            String shoppyMid=null;
//		            String grabmid = null;
//		            transactionService.merchantEwallet(paginationBean, null, null, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "TNG",
//		                    currentMerchant);
//	        } else {
		transactionService.merchantEwallet(paginationBean, null, null, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "TNG", currentMerchant);

//	        }

		model.addAttribute("paginationBean", paginationBean);
		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());
			if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
				model.addAttribute("score", "yes");
			} else {
				model.addAttribute("score", "no");
			}
			String UmEzywayMid = null;
			String mid = null;
			String UmMotoMid = null;
			Merchant merchant = merchantService.loadMerchant(myName);
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getUmMotoMid() != null) {
				UmMotoMid = merchant.getMid().getUmMotoMid();
			}
			if (merchant.getMid().getMid() != null) {
				mid = merchant.getMid().getMid();
			}
			model.addAttribute("UmEzywayMid", UmEzywayMid);
			model.addAttribute("mid", mid);
			model.addAttribute("UmMotoMid", UmMotoMid);
		}
		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";
			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");
			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");
		}
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantname);
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/searchTNGpaymerchantlist" }, method = RequestMethod.GET)
	public String SearchM1payTransaction(final Model model, final java.security.Principal principal,
			@RequestParam final String date, @RequestParam final String date1, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("M1pay Transaction ");
		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantname = currentMerchant.getBusinessName();
		PageBean pageBean = new PageBean("transactions list", "transaction/merchantTNGpay(E-wallet)",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
//	        if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//	        	 String ezywaymid = "000000000021591";
//		            String umezywaymid = null;
//		            String ummid =null;
//		            String motomid = null;
//		            String ezyrecmid =null;
//		            String mid=null;
//		            String bnplMid=null;
//		            String boostmid=null;
//		            String tngMid=null;
//		            String shoppyMid=null;
//		            String grabmid = null;
//		            transactionService.merchantEwallet(paginationBean, date, date1, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "TNG",
//		                    currentMerchant);
//	        } else {
		transactionService.merchantEwallet(paginationBean, fromDate, toDate, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "TNG", currentMerchant);

//	        }

		model.addAttribute("paginationBean", paginationBean);
		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());
			if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
				model.addAttribute("score", "yes");
			} else {
				model.addAttribute("score", "no");
			}
			String UmEzywayMid = null;
			String mid = null;
			String UmMotoMid = null;
			Merchant merchant = merchantService.loadMerchant(myName);
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getUmMotoMid() != null) {
				UmMotoMid = merchant.getMid().getUmMotoMid();
			}
			if (merchant.getMid().getMid() != null) {
				mid = merchant.getMid().getMid();
			}
			model.addAttribute("UmEzywayMid", UmEzywayMid);
			model.addAttribute("mid", mid);
			model.addAttribute("UmMotoMid", UmMotoMid);
			model.addAttribute("UmEzywayMid", UmEzywayMid);
		}
		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";
			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");
			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");
		}
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantname);
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/exportTNGpaymerchantlist", method = RequestMethod.GET)
	public ModelAndView exportumEzywayM1pay(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			final java.security.Principal principal) {

		logger.info("Export TNG :");
		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromdate;
		String dat1 = todate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();

		// String txnStatus="A";
//			transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1,txnStatus,currentMerchant.getMid().getUmEzywayMid(),"ALL");
//		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//			 String ezywaymid = "000000000021591";
//	            String umezywaymid = null;
//	            String ummid =null;
//	            String motomid = null;
//	            String ezyrecmid =null;
//	            String mid=null;
//	            String bnplMid=null;
//	            String boostmid=null;
//	            String tngMid=null;
//	            String shoppyMid=null;
//	            String grabmid = null;
//	            transactionService.merchantEwallet1(paginationBean, fromDate, toDate, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "TNG",
//	                    currentMerchant);
//		} else {
		transactionService.merchantEwallet1(paginationBean, fromdate, todate, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "TNG", currentMerchant);

//		}
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (list1.size() == 0) {

			PageBean pageBean = new PageBean("transactions list", "transaction/merchantTNGpay(E-wallet)",
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

			model.addAttribute("pageBean", pageBean);

			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("exportData", "noRecords");

			ModelAndView modelAndView = new ModelAndView(TEMPLATE_MERCHANT);
			logger.info("size of pagination :" + paginationBean.getItemList().size());

			return modelAndView;

		} else {

			if (!(export.equals("PDF"))) {
				// return new ModelAndView("txnListUMExcel", "umTxnList", list1);
				return new ModelAndView("txnListMerchantM1payExcel", "umTxnList", list1);
			} else {
				return new ModelAndView("EwalletMerchantTxnPdf", "umTxnList", list1);
			}
		}

	}

	@RequestMapping(value = { "/merchantSHOPPYTransaction" }, method = RequestMethod.GET)
	public String MerchantShoppylist(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("M1pay Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = currentMerchant.getBusinessName();
		PageBean pageBean = new PageBean("transactions list", "transaction/merchantSHOPPYpay(E-wallet)",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
//	        if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//	        	 String ezywaymid = "000000000021591";
//		            String umezywaymid = null;
//		            String ummid =null;
//		            String motomid = null;
//		            String ezyrecmid =null;
//		            String mid=null;
//		            String bnplMid=null;
//		            String boostmid=null;
//		            String tngMid=null;
//		            String shoppyMid=null;
//		            String grabmid = null;
//		            transactionService.merchantEwallet(paginationBean, null, null, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "SHOPPY",
//		                    currentMerchant);
//	        } else {
		transactionService.merchantEwallet(paginationBean, null, null, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "SHOPPY",
				currentMerchant);

//	        }

		model.addAttribute("paginationBean", paginationBean);
		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());
			if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
				model.addAttribute("score", "yes");
			} else {
				model.addAttribute("score", "no");
			}
			String UmEzywayMid = null;
			String mid = null;
			String UmMotoMid = null;
			Merchant merchant = merchantService.loadMerchant(myName);
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getUmMotoMid() != null) {
				UmMotoMid = merchant.getMid().getUmMotoMid();
			}
			if (merchant.getMid().getMid() != null) {
				mid = merchant.getMid().getMid();
			}
			model.addAttribute("UmEzywayMid", UmEzywayMid);
			model.addAttribute("mid", mid);
			model.addAttribute("UmMotoMid", UmMotoMid);
		}
		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";
			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");
			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");
		}
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantName);
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/searchShoppypaymerchantlist" }, method = RequestMethod.GET)
	public String SearchShoppyTransaction(final Model model, final java.security.Principal principal,
			@RequestParam final String date, @RequestParam final String date1, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("Shoppeepay Transaction ");

		String fromdate = HtmlUtils.htmlEscape(date);
		String todate = HtmlUtils.htmlEscape(date1);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = currentMerchant.getBusinessName();
		PageBean pageBean = new PageBean("transactions list", "transaction/merchantSHOPPYpay(E-wallet)",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
//	        if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//	        	 String ezywaymid = "000000000021591";
//		            String umezywaymid = null;
//		            String ummid =null;
//		            String motomid = null;
//		            String ezyrecmid =null;
//		            String mid=null;
//		            String bnplMid=null;
//		            String boostmid=null;
//		            String tngMid=null;
//		            String shoppyMid=null;
//		            String grabmid = null;
//		            transactionService.merchantEwallet(paginationBean, date, date1, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "SHOPPY",
//		                    currentMerchant);
//	        } else {
		transactionService.merchantEwallet(paginationBean, fromdate, todate, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "SHOPPY",
				currentMerchant);

		// }

		model.addAttribute("paginationBean", paginationBean);
		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());
			if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
				model.addAttribute("score", "yes");
			} else {
				model.addAttribute("score", "no");
			}
			String UmEzywayMid = null;
			String mid = null;
			String UmMotoMid = null;
			Merchant merchant = merchantService.loadMerchant(myName);
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getUmMotoMid() != null) {
				UmMotoMid = merchant.getMid().getUmMotoMid();
			}
			if (merchant.getMid().getMid() != null) {
				mid = merchant.getMid().getMid();
			}
			model.addAttribute("UmEzywayMid", UmEzywayMid);
			model.addAttribute("mid", mid);
			model.addAttribute("UmMotoMid", UmMotoMid);
			model.addAttribute("UmEzywayMid", UmEzywayMid);
		}
		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";
			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");
			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");
		}
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantName);
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/exportShoppypaymerchantlist", method = RequestMethod.GET)
	public ModelAndView exportShoppypaylist(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			final java.security.Principal principal) {

		logger.info("shoppy export: ");
		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromdate;
		String dat1 = todate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();

		// String txnStatus="A";
//			transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1,txnStatus,currentMerchant.getMid().getUmEzywayMid(),"ALL");
//		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//			 String ezywaymid = "000000000021591";
//	            String umezywaymid = null;
//	            String ummid =null;
//	            String motomid = null;
//	            String ezyrecmid =null;
//	            String mid=null;
//	            String bnplMid=null;
//	            String boostmid=null;
//	            String tngMid=null;
//	            String shoppyMid=null;
//	            String grabmid = null;
//	            transactionService.merchantEwallet1(paginationBean, fromDate, toDate, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "SHOPPY",
//	                    currentMerchant);
//		} else {
		transactionService.merchantEwallet1(paginationBean, fromdate, todate, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "SHOPPY",
				currentMerchant);

//		}
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (list1.size() == 0) {

			PageBean pageBean = new PageBean("transactions list", "transaction/merchantSHOPPYpay(E-wallet)",
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("exportData", "noRecords");

			ModelAndView modelAndView = new ModelAndView(TEMPLATE_MERCHANT);

			return modelAndView;

		} else {

			if (!(export.equals("PDF"))) {
				// return new ModelAndView("txnListUMExcel", "umTxnList", list1);
				return new ModelAndView("txnListMerchantM1payExcel", "umTxnList", list1);
			} else {
				return new ModelAndView("EwalletMerchantTxnPdf", "umTxnList", list1);
			}
		}

	}

	@RequestMapping(value = { "/merchantbnplSummary" }, method = RequestMethod.GET)
	public String MerchantbnplSummaryAdmin(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("BNPL Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "transaction/merchantBNPL(E-wallet)",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
//	        if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//	        	 String ezywaymid = "000000000021591";
//		            String umezywaymid = null;
//		            String ummid =null;
//		            String motomid = null;
//		            String ezyrecmid =null;
//		            String mid=null;
//		            String bnplMid=null;
//		            String boostmid=null;
//		            String tngMid=null;
//		            String shoppyMid=null;
//		            String grabmid = null;
//		            transactionService.merchantEwallet(paginationBean, null, null, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "BNPL",
//		                    currentMerchant);
//	            transactionService.merchantEwallet(paginationBean, null, null, ezywaymid, "BNPL",
//	                    currentMerchant);
//	        } else {
		transactionService.merchantEwallet(paginationBean, null, null, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "BNPL",
				currentMerchant);

//	        }

		model.addAttribute("paginationBean", paginationBean);
		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());
			if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
				model.addAttribute("score", "yes");
			} else {
				model.addAttribute("score", "no");
			}

			String UmEzywayMid = null;
			String mid = null;
			String UmMotoMid = null;
			Merchant merchant = merchantService.loadMerchant(myName);
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getUmMotoMid() != null) {
				UmMotoMid = merchant.getMid().getUmMotoMid();
			}
			if (merchant.getMid().getMid() != null) {
				mid = merchant.getMid().getMid();
			}
			model.addAttribute("UmEzywayMid", UmEzywayMid);
			model.addAttribute("mid", mid);
			model.addAttribute("UmMotoMid", UmMotoMid);
		}
		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";
			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");
			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/searchBnplmerchantsummery" }, method = RequestMethod.GET)
	public String SearchBnplTransaction(final Model model, final java.security.Principal principal,
			@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("M1pay Transaction ");
		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "transaction/merchantBNPL(E-wallet)",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
//	        if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//	        	 String ezywaymid = "000000000021591";
//		            String umezywaymid = null;
//		            String ummid =null;
//		            String motomid = null;
//		            String ezyrecmid =null;
//		            String mid=null;
//		            String bnplMid=null;
//		            String boostmid=null;
//		            String tngMid=null;
//		            String shoppyMid=null;
//		            String grabmid = null;
//		            transactionService.merchantEwallet(paginationBean, date, date1, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "BNPL",
//		                    currentMerchant);
//	        } else {
		transactionService.merchantEwallet(paginationBean, fromDate, toDate, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "BNPL",
				currentMerchant);

//	        }

		model.addAttribute("paginationBean", paginationBean);
		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());
			if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
				model.addAttribute("score", "yes");
			} else {
				model.addAttribute("score", "no");
			}
			String UmEzywayMid = null;
			String mid = null;
			String UmMotoMid = null;
			Merchant merchant = merchantService.loadMerchant(myName);
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getUmMotoMid() != null) {
				UmMotoMid = merchant.getMid().getUmMotoMid();
			}
			if (merchant.getMid().getMid() != null) {
				mid = merchant.getMid().getMid();
			}
			model.addAttribute("UmEzywayMid", UmEzywayMid);
			model.addAttribute("mid", mid);
			model.addAttribute("UmMotoMid", UmMotoMid);
		}
		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";
			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");
			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/exportBnplmerchantsummery", method = RequestMethod.GET)
	public ModelAndView exportumEzywayBnpl(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("export") String export, HttpServletRequest request,
			final java.security.Principal principal) {

		logger.info("export BNPL: ");
		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		String dat = fromdate;
		String dat1 = todate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		// paginationBean.setCurrPage(currPage);

//		if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//			 String ezywaymid = "000000000021591";
//	            String umezywaymid = null;
//	            String ummid =null;
//	            String motomid = null;
//	            String ezyrecmid =null;
//	            String mid=null;
//	            String bnplMid=null;
//	            String boostmid=null;
//	            String tngMid=null;
//	            String shoppyMid=null;
//	            String grabmid = null;
//	            transactionService.merchantEwallet1(paginationBean, fromDate, toDate, umezywaymid,ezywaymid,ummid,motomid,ezyrecmid,mid,bnplMid,boostmid,tngMid,shoppyMid,grabmid, "BNPL",
//	                    currentMerchant);
//		} else {
		transactionService.merchantEwallet1(paginationBean, fromdate, todate, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getUmEzyrecMid(),
				currentMerchant.getMid().getMid(), currentMerchant.getMid().getBnplMid(),
				currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
				currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), "BNPL",
				currentMerchant);

//		}
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found");

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			// return new ModelAndView("txnListUMExcel", "umTxnList", list1);
			return new ModelAndView("txnListMerchantBNPLExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListUMPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = { "/fpxTxnSummary" }, method = RequestMethod.GET)
	public String MerchantFPXlist(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("FPX Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = currentMerchant.getBusinessName();
		PageBean pageBean = new PageBean("transactions list", "transaction/merchantportalFPXTransaction",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

//	        if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//	            String ezywaymid = "000000000021591";
//	            String fpxmid=null;
//	            String umezywaymid=null;
//	            String ummotomid=null;
//	            String mid=null;
//	        
//	            transactionService.merchantFpxtranscation(paginationBean,null,null,umezywaymid,fpxmid,ummotomid,mid,"FPX",currentMerchant);
//	        } else {
//	        	 transactionService.merchantFpxtranscation(paginationBean,null,null,currentMerchant.getMid().getUmEzywayMid(),currentMerchant.getMid().getFpxMid(),currentMerchant.getMid().getUmMotoMid(),currentMerchant.getMid().getMid(),"FPX",currentMerchant);
//	      
//	        }

		transactionService.merchantFpxtranscation(paginationBean, null, null, currentMerchant.getMid().getUmEzywayMid(),
				currentMerchant.getMid().getFpxMid(), currentMerchant.getMid().getUmMotoMid(),
				currentMerchant.getMid().getMid(), "FPX", currentMerchant);

		model.addAttribute("paginationBean", paginationBean);
		logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
				model.addAttribute("score", "yes");
			} else {
				model.addAttribute("score", "no");
			}
			String UmEzywayMid = null;
			Merchant merchant = merchantService.loadMerchant(myName);
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			model.addAttribute("UmEzywayMid", UmEzywayMid);
		}
		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";
			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");
			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");
		}
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantName);
		return TEMPLATE_MERCHANT;

	}

//	@RequestMapping(value = {"/sendFpxIpn"}, method = RequestMethod.GET )
//	public @ResponseBody AjaxResponseBody sendFpxIpn(@RequestParam String sellerOrderNumber,@RequestParam String mid,@RequestParam String tid)
//	{
//		try {
//			return currencyExchangeService.sendFpxIpn(sellerOrderNumber,mid,tid);
//		}catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		return new AjaxResponseBody();
//	}



	@RequestMapping(value = { "/searchFpxTxnSummary" }, method = RequestMethod.GET)
	public String searchFpxTxnSummary(final Model model, final java.security.Principal principal,
			@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		logger.info("FPX Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = currentMerchant.getBusinessName();
		PageBean pageBean = new PageBean("transactions list", "transaction/merchantportalFPXTransaction",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
//	        if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//	            String ezywaymid = "000000000021591";
//	            String fpxmid=null;
//	            String umezywaymid=null;
//	            String ummotomid=null;
//	            String mid=null;
//	            transactionService.merchantFpxtranscation(paginationBean,date,date1,umezywaymid,fpxmid,ummotomid,mid,"FPX",currentMerchant);
//	        } else {
//	        	 transactionService.merchantFpxtranscation(paginationBean,date,date1,currentMerchant.getMid().getUmEzywayMid(),currentMerchant.getMid().getFpxMid(),currentMerchant.getMid().getUmMotoMid(),currentMerchant.getMid().getMid(),"FPX",currentMerchant);
//	       
//	        }

		transactionService.merchantFpxtranscation(paginationBean, fromDate, toDate,
				currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getFpxMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getMid(), "FPX", currentMerchant);

		model.addAttribute("paginationBean", paginationBean);
		logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());

		if (currentMerchant.getMid().getUmEzywayMid() != null) {

			if (currentMerchant.getMid().getUmEzywayMid().equalsIgnoreCase("000000000014796")) {
				model.addAttribute("score", "yes");
			} else {
				model.addAttribute("score", "no");
			}
			String UmEzywayMid = null;
			String Mid = null;
			String fpxmid = null;
			String UmMotoMid = null;
			Merchant merchant = merchantService.loadMerchant(myName);
			if (merchant.getMid().getUmEzywayMid() != null) {
				UmEzywayMid = merchant.getMid().getUmEzywayMid();
			}
			if (merchant.getMid().getMid() != null) {
				Mid = merchant.getMid().getMid();
			}
			if (merchant.getMid().getUmEzywayMid() != null) {
				fpxmid = merchant.getMid().getFpxMid();
			}
			if (merchant.getMid().getUmMotoMid() != null) {
				UmMotoMid = merchant.getMid().getUmMotoMid();
			}
			model.addAttribute("UmEzywayMid", UmEzywayMid);
			model.addAttribute("Mid", Mid);
			model.addAttribute("fpxmid", fpxmid);
			model.addAttribute("UmMotoMid", UmMotoMid);
		}
		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";
			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");
			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");
		}
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantName);
		return TEMPLATE_MERCHANT;

	}

//	@RequestMapping(value = { "/exportFpxTxnSummary" }, method = RequestMethod.GET)
//	public ModelAndView getExportfpxSettlemen(final Model model, @RequestParam final String fromDate,
//			@RequestParam final String toDate, @RequestParam final String id,
//			@RequestParam(required = false, defaultValue = "1") final int currPage,
//			@RequestParam(required = false) String export, final java.security.Principal principal) {
//
//		logger.info("Transaction Summary:" + principal.getName());
//		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
//		paginationBean.setCurrPage(currPage);
//
//		// List<Merchant> merchant1 = merchantService.loadFpxMerchant();
//
//		if (id != "") {
//			Merchant midDetails = mobileUserService.loadIserMidDetails(Long.parseLong(id));
//			transactionService.merchantlistFPXTransactionByMid(paginationBean, midDetails, fromDate, toDate);
//		} else {
//			transactionService.MerchantFPXTransaction(paginationBean, fromDate, toDate);
//		}
//
//		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
//				|| paginationBean.getItemList().size() == 0) {
//			model.addAttribute("responseData", "No Records found"); // table
//																	// response
//		} else {
//			model.addAttribute("responseData", null);
//		}
//
//		List<FpxTransaction> list = paginationBean.getItemList();
//
//		if (!(export.equals("PDF"))) {
//
//			return new ModelAndView("fpxTxnExcel1", "txnList", list);
//
//		} else {
//			return new ModelAndView("fpxTxnPdf", "txnList", list);
//		}
//
//	}

	@RequestMapping(value = "/exportFpxTxnSummary", method = RequestMethod.GET)
	public ModelAndView exportFpxTxnSummary(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			final java.security.Principal principal) {

		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());

		String dat = fromdate;
		String dat1 = todate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		// paginationBean.setCurrPage(currPage);

		// String txnStatus="A";
//			transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1,txnStatus,currentMerchant.getMid().getUmEzywayMid(),"ALL");

		// if (currentMerchant.getMid().getUmEzywayMid().equals("000000111021591")) {
//	            String ezywaymid = "000000000021591";
//	            String fpxmid=null;
//	            String umezywaymid=null;
//	            String ummotomid=null;
//	            String mid=null;
//	            transactionService.merchantFpxtranscation1(paginationBean,fromDate,toDate,umezywaymid,fpxmid,ummotomid,mid,"FPX",currentMerchant);
//	        } else {
//	        	 transactionService.merchantFpxtranscation1(paginationBean,fromDate,toDate,currentMerchant.getMid().getUmEzywayMid(),currentMerchant.getMid().getFpxMid(),currentMerchant.getMid().getUmMotoMid(),currentMerchant.getMid().getMid(),"FPX",currentMerchant);
//	       
//	        }

		transactionService.merchantFpxtranscation1(paginationBean, fromdate, todate,
				currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getFpxMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getMid(), "FPX", currentMerchant);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (list1.size() == 0) {

			PageBean pageBean = new PageBean("transactions list", "transaction/merchantportalFPXTransaction",
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			model.addAttribute("pageBean", pageBean);

			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("exportData", "noRecords");

			ModelAndView modelAndView = new ModelAndView(TEMPLATE_MERCHANT);

			return modelAndView;

		} else {

			if (!(export.equals("PDF"))) {
				// return new ModelAndView("txnListUMExcel", "umTxnList", list1);
				return new ModelAndView("txnListMerchantBoostExcel", "umTxnList", list1);
			} else {
		//		return new ModelAndView("txnListUMPdf", "umTxnList", list1);
				return new ModelAndView("FpxListMerchant", "umTxnList", list1);
			}
		}

	}

	// UM - EZYWIRE PAGINATION CHANGES

	@RequestMapping(value = { "/umEzywireList" }, method = RequestMethod.GET)
	public String umEzywireList(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list umEzywayList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzywireList1",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-EZWAY Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		if (currentMerchant.getMid().getUmMid().equals("000000111021591")) {
			String ezywiremid = "000000000021591";
			// transactionService.listUMEzywayTransaction(paginationBean, null, null,
			// ezywaymid, "ALL", currentMerchant);
			transactionService.listUMEzywireTransaction(paginationBean, null, null, ezywiremid, "ALL", currentMerchant);
		} else {
			String ezywiremid = currentMerchant.getMid().getUmMid();
			transactionService.listUMEzywireTransaction(paginationBean, null, null, ezywiremid, "ALL", currentMerchant);
		}
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		if (currentMerchant.getMid().getUmMid().equalsIgnoreCase("000000000014796")) {
			model.addAttribute("score", "yes");
		} else {
			model.addAttribute("score", "no");
		}

		String UmEzywireMid = null;
		Merchant merchant = merchantService.loadMerchant(myName);

		if (merchant.getMid().getUmMid() != null) {
			UmEzywireMid = merchant.getMid().getUmMid();
		}

		model.addAttribute("UmEzywayMid", UmEzywireMid);

		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else if (userName.equalsIgnoreCase("FINANCE@DLOCAL.COM")
				|| userName.equalsIgnoreCase("daria.ar@monetix.pro")) {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

// SEARCH UM-EZYWIRE 

	@RequestMapping(value = { "/searchUMEzywire" }, method = RequestMethod.GET)
	public String SearchumEzywireList1(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam final String txnType,
			final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  UM-EZWire Transaction ");
		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzywireList1",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		if (currentMerchant.getMid().getUmMid().equals("000000111021591")) {
			String ezywiremid = "000000000021591";
//			transactionService.SearchlistUMEzywayTransaction(paginationBean, date, date1, ezywiremid, txnType,
//					currentMerchant);

			transactionService.listUMEzywireTransaction(paginationBean, fromDate, toDate, ezywiremid, txnType,
					currentMerchant);
		} else {
//			transactionService.SearchlistUMEzywayTransaction(paginationBean, date, date1,
//					currentMerchant.getMid().getUmMid(), txnType, currentMerchant);

			transactionService.listUMEzywireTransaction(paginationBean, fromDate, toDate,
					currentMerchant.getMid().getUmMid(), txnType, currentMerchant);
		}
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);
		logger.info("Search Mid:" + currentMerchant.getMid().getUmMid());
		if (currentMerchant.getMid().getUmMid().equalsIgnoreCase("000000000014796")) {
			model.addAttribute("score", "yes");
		} else {
			model.addAttribute("score", "no");
		}

		String UmEzywireMid = null;
		Merchant merchant = merchantService.loadMerchant(myName);

		if (merchant.getMid().getUmMid() != null) {
			UmEzywireMid = merchant.getMid().getUmEzywayMid();
		}

		model.addAttribute("UmEzywayMid", UmEzywireMid);

		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

//EXPORT UM-EZYWIRE

	@RequestMapping(value = "/umEzywireExport", method = RequestMethod.GET)
	public ModelAndView getExportUMEzywire(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("UM ezywire export: ");
		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		// logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		// logger.info("TXN type" + txnType);
		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromdate;
		String dat1 = todate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		// String txnStatus="A";
//			transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1,txnStatus,currentMerchant.getMid().getUmEzywayMid(),"ALL");
		if (currentMerchant.getMid().getUmMid().equals("000000111021591")) {
			String ezywiremid = "000000000021591";

			transactionService.exportUMEzywireTransaction(paginationBean, fromdate, todate, ezywiremid, "CARD",
					currentMerchant);
		} else {

			transactionService.exportUMEzywireTransaction(paginationBean, fromdate, todate,
					currentMerchant.getMid().getUmMid(), "CARD", currentMerchant);

		}
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			// return new ModelAndView("txnListUMExcel", "umTxnList", list1);
			return new ModelAndView("UMMerchantWireExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	public String sendEmail(String filename) {
		logger.info("Sending Boost Clearance csv Mail " + filename);
		String response = null;
		// EZYWIRE AS USERNAME & password mobiversa
		String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");

		String toAddress = PropertyLoad.getFile().getProperty("MAIL_TO");
		String ccAddress = PropertyLoad.getFile().getProperty("MAIL_CC");
		String subject = PropertyLoad.getFile().getProperty("FINANCE_SETTLEMENT_SUBJECT")
				+ new SimpleDateFormat("dd-MM-yyyy").format(new Date());// set

		logger.info("Sending Email to :" + toAddress + " : " + ccAddress);

		String fromName = PropertyLoad.getFile().getProperty("FROMNAME");
		String textbody = PropertyLoad.getFile().getProperty("TEXT_BODY");
		String attachment = PropertyLoad.getFile().getProperty("CLEARENCEFILEPATH") + filename;

		String textBody = "Hi Finance Team," + "\n\n" + textbody + " "
				+ new SimpleDateFormat("dd-MMM-yyyy").format(new Date());

		ElasticEmail message = new ElasticEmail(fromAddress, subject, fromName, toAddress, ccAddress, ccAddress,
				attachment, textBody);

		ElasticEmailClient client = new ElasticEmailClient();

		try {
			response = client.sendEMailWithAttachment(message);
			logger.info("Email Sent Successfully to " + toAddress);
			logger.info("output Response : " + response);

		} catch (Exception pe) {
			response = "400";
			logger.info("Invalid Signature Base64 String");

		}
		return response;

	}

	// New changes

	// Newly added for OCBC bank balance
	@RequestMapping(value = { "/payoutbankbalance" }, method = RequestMethod.GET)
	public String payoutbankbalance(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal, HttpServletRequest request) {

		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutBankBalance",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		Settlementbalance payoutBankBalance = new Settlementbalance();

		try {
			HttpSession session = request.getSession();
			payoutBankBalance = transactionService.getPayoutBankBalance(payoutBankBalance);

			logger.info("Payout bank balance : " + principal.getName() + ", Session : " + request.getSession()
					+ ", PayoutBankBalance : " + payoutBankBalance.toString());
		} catch (MobiException e) {
			e.printStackTrace();
			logger.error("Exception in Payout BankBalance : " + e.getMessage());
		}

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("settlementbankbalance", payoutBankBalance);
		return TEMPLATE_DEFAULT;
	}

	// Old changes

//	@RequestMapping(value = { "/payoutbankbalance" }, method = RequestMethod.GET)
//	public String payoutbankbalance(final Model model,
//			@RequestParam(required = false, defaultValue = "1") final int currPage,
//			final java.security.Principal principal, HttpServletRequest request) {
//
//		logger.info("Payout bank balance  :********* " + principal.getName());
//		HttpSession session = request.getSession();
//		logger.info("currently logged in as request: " + request.getSession());
//
//		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutBankBalance",
//				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//		model.addAttribute("pageBean", pageBean);
//
//		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
//		paginationBean.setCurrPage(currPage);
//		List<Settlementbalance> settlementbankbalance = transactionService.loadbankbalance();
//		logger.info("settlementbankbalance : " + settlementbankbalance);
//		model.addAttribute("loginname", principal.getName());
//		model.addAttribute("settlementbankbalance", settlementbankbalance);
//		return TEMPLATE_DEFAULT;
//	}

	@RequestMapping(value = { "/addbankamount" }, method = RequestMethod.GET)
	public String addbankbalance(Model model, HttpServletRequest request, @RequestParam final String amount,
			@RequestParam final String merchantId, final java.security.Principal principal) throws ParseException {
		logger.info("Update bank balance from JSP : " + amount);
		String csvFile = null;
		String clearencefile = null;
		String responseData = null;

		HttpSession session = request.getSession();

		logger.info("WithDraw Amount from JSP : " + amount);
		logger.info("WithDraw merchantId from JSP : " + merchantId);
		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutBankBalance",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		String myName = (String) session.getAttribute("userName");
		String userName = principal.getName();
		model.addAttribute("pageBean", pageBean);
		logger.info("Payout With Draw :" + myName);
		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		Merchant merchant = merchantService.loadMerchant(myName);

		DecimalFormat decimalFormat = new DecimalFormat("##0.00");
		double newAmount = decimalFormat.parse(amount).doubleValue();
		String formattedNetAmount = decimalFormat.format(newAmount);
		logger.info("Formatted Net amount : " + formattedNetAmount);

		// here add new table
//         SettlementBalance settlementTable = merchantService.SearchMerchantSettlement(merchantId);

//         logger.info("Old Bank amount : " + settlementTable.getBalanceNetAmount());
//         
//         double OldBankbalance=Double.parseDouble(settlementTable.getBalanceNetAmount());
//         
//         double newBankBalance=newAmount+OldBankbalance;
//         
//         logger.info("New Bank amount : " + newBankBalance);
//         
//         String BalanceNetAmount=settlementTable.getBalanceNetAmount();

		// paginationBean.setCurrPage(currPage);

//		
//		transactionService.loadPayoutbalance(paginationBean, merchant.getId());
//		String merchantName = merchant.getBusinessShortName();

		PayoutBankBalance bank = merchantService.oldbankbalance();

		String oldamount = bank.getAmount();

		double oldbankamount = Double.parseDouble(oldamount);

		double newAmount1 = newAmount + oldbankamount;
		logger.info("request Amount : " + newAmount);
		logger.info("Old Amount     : " + oldbankamount);
		logger.info("New Amount     : " + newAmount1);
		Date date = new Date();
		String outputFormat = "dd-MM-yyyy HH:mm:ss";

		SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputFormat);
		String outputDateTime = outputDateFormat.format(date);

		logger.info("Current date and time: " + outputDateTime);

		merchantService.updateBankAmount(String.valueOf(newAmount1), outputDateTime);

		logger.info("bank  @@@@ Amount : " + bank.getAmount());
		logger.info("bank  @@@@ acc no : " + bank.getBankAccNo());

		List<Settlementbalance> settlementbankbalance = transactionService.loadbankbalance();
		logger.info("settlementbankbalance : " + settlementbankbalance);

		model.addAttribute("merchantid", merchant.getId());
		model.addAttribute("settlementbankbalance", settlementbankbalance);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// Payout - OverDraftRefill
	@RequestMapping(value = { "/refill" }, method = RequestMethod.GET)
	public String refillAmount(Model model, HttpServletRequest request,
			@RequestParam("overdraftAmount1") String overdraftAmount1,
			@RequestParam("depositAmount") String depositAmount, @RequestParam("merchantId") String merchantId,
			final java.security.Principal principal) throws ParseException {
		logger.info("refill overdraft Amount from JSP : #########3### " + overdraftAmount1);
		logger.info("refill deposit Amount from JSP : " + depositAmount);

		String csvFile = null;
		String clearencefile = null;
		String responseData = null;
		int responseCode = 0;
		PayoutModel var = new PayoutModel();
		HttpSession session = request.getSession();
		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutBalance",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		String myName = (String) session.getAttribute("userName");
		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		Merchant merchant = merchantService.loadMerchant(myName);

		String merchantName = merchant.getBusinessShortName();
		logger.info("Short Name :  " + merchantName);

		String existingTotalBalance = null;
		String postRefillTotalBalance = null;

		String existingSettlementBalance = null;
		String postRefillSettlementBalance = null;

		String existingOverDraftAmount = null;
		String postRefillOverDraftAmount = null;

		String existingDepositAmount = null;
		String postRefillDepositAmount = null;

		// Triggering External API to validate SettlementBalance Table and update in the
		// database (Using Synchronized Block)
		try {
			logger.info("Deposit Api Url :" + PropertyLoad.getFile().getProperty("SYNCHRONIZED_BLOCK_API"));
			URL url = new URL(PropertyLoad.getFile().getProperty("SYNCHRONIZED_BLOCK_API"));

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");

			connection.setDoOutput(true);

			connection.setRequestProperty("Content-Type", "application/json");

			JsonObject jsonBody = new JsonObject();
			jsonBody.addProperty("service", "PAYOUT_OVERDRAFT_REFILL");
			jsonBody.addProperty("merchantId", merchantId);

			String data = new Gson().toJson(jsonBody);

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = data.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			responseCode = connection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				logger.error("External API Request failed with response code: " + responseCode);
			} else {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					responseData = response.toString(); // Store the response data
					logger.info("API Response: " + responseData);

					JSONObject jsonObject = new JSONObject(responseData);

					JSONObject responseDataInExternalApi = jsonObject.getJSONObject("updateResponseData");

					existingDepositAmount = responseDataInExternalApi.getString("existingDepositAmount");
					postRefillDepositAmount = responseDataInExternalApi.getString("postRefillDepositAmount");

					existingOverDraftAmount = responseDataInExternalApi.getString("existingOverDraftAmount");
					postRefillOverDraftAmount = responseDataInExternalApi.getString("postRefillOverDraftAmount");

					existingSettlementBalance = responseDataInExternalApi.getString("existingSettlementBalance");
					postRefillSettlementBalance = responseDataInExternalApi.getString("postRefillSettlementBalance");

					existingTotalBalance = responseDataInExternalApi.getString("existingTotalBalance");
					postRefillTotalBalance = responseDataInExternalApi.getString("postRefillTotalBalance");

				} catch (IOException e) {
					logger.error("Error while Reterving Response From External API");
					e.printStackTrace();
				}
			}

			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while Connecting With External API");
		}

		if (responseCode == HttpURLConnection.HTTP_OK) {
			List<SettlementMailList> financeMailList = new ArrayList<SettlementMailList>();

			// file send by cs team new add
			Date daysAgo = new Date();
			logger.info("Date : " + daysAgo);
			String resDate = new SimpleDateFormat("dd-MMM-yyyy").format(daysAgo);
			String filePrtn = PropertyLoad.getFile().getProperty("SETTLEMENT_CLEARENCE_PRTN");
			csvFile = resDate + filePrtn;

			logger.info("CSV File pattern : " + csvFile);

			SettlementMailList settleList = new SettlementMailList();

			settleList.setAccNo(merchant.getBankAcc());
			settleList.setBankName(merchant.getBankName());
			settleList.setMerchantName(merchant.getUsername());
			settleList.setMid(merchant.getMid().getMid());

			settleList.setExsitingdepositAmount(existingDepositAmount);
			settleList.setDepositAmount(postRefillDepositAmount);

			settleList.setExsitingoverdraftAmount(existingOverDraftAmount);
			settleList.setOverdraftAmount(postRefillOverDraftAmount);

			settleList.setExsitingsettlementBalance(existingSettlementBalance);
			settleList.setSettlementBalance(postRefillSettlementBalance);

			settleList.setExsitingtotalBalance(existingTotalBalance);
			settleList.setTotalBalance(postRefillTotalBalance);

			financeMailList.add(settleList);
			FileWriter fileWriter = null;

			try {

				fileWriter = new FileWriter(PropertyLoad.getFile().getProperty("CLEARENCEFILEPATH") + csvFile);
				fileWriter.append(PAYER_HEADER1.toString().toUpperCase());
				fileWriter.append(NEW_LINE_SEPARATOR);

				for (SettlementMailList settlementList : financeMailList) {

					fileWriter.append(settlementList.getMerchantName());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getMid());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getAccNo());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getBankName());

					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getExsitingdepositAmount());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getDepositAmount());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getExsitingoverdraftAmount());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getOverdraftAmount());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getExsitingsettlementBalance());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getSettlementBalance());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getExsitingtotalBalance());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getTotalBalance());
					fileWriter.append(NEW_LINE_SEPARATOR);
				}

			} catch (Exception e) {
				logger.error("Error in CsvFileWriter !!!");
				e.printStackTrace();
			} finally {
				try {
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e) {
					logger.error("Error while flushing/closing fileWriter !!!");
					e.printStackTrace();
				}
			}

			clearencefile = PropertyLoad.getFile().getProperty("CLEARENCEFILEPATH") + csvFile;

			File file = new File(clearencefile);
			if (!file.exists()) {
				logger.info("Clearence File Not Generated");
			} else {

				logger.info("Clearence File is Generated");
				responseData = sendEmail(csvFile);

				if (responseData.equals("200")) {
					logger.info("Email Sent Successfully");

				} else {
					logger.info("Email Sent Exception");
				}
			}
		}

		transactionService.loadPayoutbalance(paginationBean, merchant.getId());

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found");

		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		SettlementBalance settlementTable = merchantService.SearchMerchantSettlement(merchantId);
		logger.info("Exsiting overdraft Amount : " + existingOverDraftAmount);
		String adminusername = principal.getName();
		model.addAttribute("merchantid", merchant.getId());
		model.addAttribute("settlementTable", settlementTable);
		model.addAttribute("adminusername", adminusername);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());

		return TEMPLATE_MERCHANT;
	}

	// Payout - Deposit Refill
	@RequestMapping(value = { "/refill1" }, method = RequestMethod.GET)
	public String refillAmount(Model model, HttpServletRequest request, @RequestParam("merchantId") String merchantId,
			final java.security.Principal principal) throws ParseException {

		String csvFile = null;
		String clearencefile = null;
		String responseData = null;
		int responseCode = 0;
		PayoutModel var = new PayoutModel();
		HttpSession session = request.getSession();
		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutBalance",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		String myName = (String) session.getAttribute("userName");
		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		Merchant merchant = merchantService.loadMerchant(myName);

		String merchantName = merchant.getBusinessShortName();
		logger.info("Short Name :  " + merchantName);
		logger.info("merchantId : " + merchantId);

		String existingTotalBalance = null;
		String postRefillTotalBalance = null;

		String existingSettlementBalance = null;
		String postRefillSettlementBalance = null;

		String existingDepositAmount = null;
		String postRefillDepositAmount = null;

		// Triggering External API to validate SettlementBalance Table and update in the
		// database (Using Synchronized Block)
		try {
			logger.info("Deposit Api Url :" + PropertyLoad.getFile().getProperty("SYNCHRONIZED_BLOCK_API"));
			URL url = new URL(PropertyLoad.getFile().getProperty("SYNCHRONIZED_BLOCK_API"));

			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod("POST");

			connection.setDoOutput(true);

			connection.setRequestProperty("Content-Type", "application/json");

			JsonObject jsonBody = new JsonObject();
			jsonBody.addProperty("service", "PAYOUT_DEPOSIT_REFILL");
			jsonBody.addProperty("merchantId", merchantId);

			String data = new Gson().toJson(jsonBody);

			logger.info("RequestBody : " + data);

			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = data.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			responseCode = connection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				logger.error("External API Request failed with response code: " + responseCode);
			} else {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					responseData = response.toString(); // Store the response data
					logger.info("API Response: " + responseData);

					JSONObject jsonObject = new JSONObject(responseData);

					JSONObject responseDataInExternalApi = jsonObject.getJSONObject("updateResponseData");

					existingDepositAmount = responseDataInExternalApi.getString("existingDepositAmount");
					postRefillDepositAmount = responseDataInExternalApi.getString("postRefillDepositAmount");

					existingSettlementBalance = responseDataInExternalApi.getString("existingSettlementBalance");
					postRefillSettlementBalance = responseDataInExternalApi.getString("postRefillSettlementBalance");

					existingTotalBalance = responseDataInExternalApi.getString("existingTotalBalance");
					postRefillTotalBalance = responseDataInExternalApi.getString("postRefillTotalBalance");

				} catch (IOException e) {
					logger.error("Error while Reterving Response From External API");
					e.printStackTrace();
				}
			}

			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error while Connecting With External API");
		}

		if (responseCode == HttpURLConnection.HTTP_OK) {
			List<SettlementMailList> financeMailList = new ArrayList<SettlementMailList>();

			// file send by cs team new add
			Date daysAgo = new Date();
			logger.info("Date : " + daysAgo);
			String resDate = new SimpleDateFormat("dd-MMM-yyyy").format(daysAgo);
			String filePrtn = PropertyLoad.getFile().getProperty("SETTLEMENT_CLEARENCE_PRTN");
			csvFile = resDate + filePrtn;

			logger.info("csvFile pattern : " + csvFile);

			SettlementMailList settleList = new SettlementMailList();

			settleList.setAccNo(merchant.getBankAcc());
			settleList.setBankName(merchant.getBankName());
			settleList.setMerchantName(merchant.getUsername());
			settleList.setMid(merchant.getMid().getMid());

			settleList.setExsitingdepositAmount(existingDepositAmount);
			settleList.setDepositAmount(postRefillDepositAmount);

			settleList.setExsitingsettlementBalance(existingSettlementBalance);
			settleList.setSettlementBalance(postRefillSettlementBalance);

			settleList.setExsitingtotalBalance(existingTotalBalance);
			settleList.setTotalBalance(postRefillTotalBalance);

			financeMailList.add(settleList);
			FileWriter fileWriter = null;

			try {

				fileWriter = new FileWriter(PropertyLoad.getFile().getProperty("CLEARENCEFILEPATH") + csvFile);
				fileWriter.append(PAYER_HEADER2.toString().toUpperCase());
				fileWriter.append(NEW_LINE_SEPARATOR);

				for (SettlementMailList settlementList : financeMailList) {

					// Amount formate
					fileWriter.append(settlementList.getMerchantName());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getMid());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getAccNo());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getBankName());

					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getExsitingdepositAmount());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getDepositAmount());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getExsitingsettlementBalance());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getSettlementBalance());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getExsitingtotalBalance());
					fileWriter.append(COMMA_DELIMITER);
					fileWriter.append(settlementList.getTotalBalance());
					fileWriter.append(NEW_LINE_SEPARATOR);
				}

			} catch (Exception e) {
				logger.error("Error in CsvFileWriter !!!");
				e.printStackTrace();
			} finally {
				try {
					fileWriter.flush();
					fileWriter.close();
				} catch (IOException e) {
					logger.error("Error while flushing/closing fileWriter !!!");
					e.printStackTrace();
				}
			}

			clearencefile = PropertyLoad.getFile().getProperty("CLEARENCEFILEPATH") + csvFile;

			File file = new File(clearencefile);
			if (!file.exists()) {
				logger.info("Clearence File Not Generated");
			} else {

				logger.info("Clearence File is Generated");
				responseData = sendEmail(csvFile);
			}

			if (responseData.equals("200")) {
				logger.info("200 Ok");

			} else {
				logger.info("200 Not Ok ");
			}
		}

		transactionService.loadPayoutbalance(paginationBean, merchant.getId());

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found");

		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		SettlementBalance settlementTable = merchantService.SearchMerchantSettlement(merchantId);

		logger.info("Exsiting Deposit Amount : " + existingDepositAmount);
		model.addAttribute("loginname", principal.getName());
		String adminusername = principal.getName();
		model.addAttribute("merchantid", merchant.getId());
		model.addAttribute("settlementTable", settlementTable);
		model.addAttribute("adminusername", adminusername);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// payout Sales Slip - Start

	@RequestMapping(value = { "/Payoutslip/{id}" }, method = RequestMethod.GET)
	public String PayoutSalesSlip(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		HttpSession session = request.getSession();

		logger.info("Invoice Id  = " + id);

		PageBean pageBean = new PageBean("Payout Transaction Sales Slip", "merchantweb/transaction/PayoutReceipt",
				null);

		String myName = (String) session.getAttribute("userName");
		logger.info("myName" + myName);
		// Merchant merchant = merchantService.loadMerchant(myName);

		List<Object[]> loadpayoutTxns = transactionService.loadpayoutTxn(id);

		List<String> createdDate = new ArrayList<String>();
		List<String> payoutamount = new ArrayList<String>();
		List<String> payoutTypeList = new ArrayList<String>();
		List<String> invoiceIdProofList = new ArrayList<String>();
		List<String> srcrefno = new ArrayList<String>();
		List<String> payoutStatusList = new ArrayList<String>();
		List<String> failurereason = new ArrayList<String>();
		List<String> businessname = new ArrayList<String>();
		List<String> paymentdescription = new ArrayList<String>();

		for (Object[] data : loadpayoutTxns) {

			logger.info((Timestamp) data[0]);

			Timestamp timestamp = (Timestamp) data[0];
			String createdDateStr = timestamp.toString();
			createdDate.add(createdDateStr);

			payoutamount.add((String) data[1]);
			payoutTypeList.add((String) data[2]);
			invoiceIdProofList.add((String) data[3]);
			srcrefno.add((String) data[4]);
			payoutStatusList.add((String) data[5]);
			failurereason.add((String) data[6]);
			businessname.add((String) data[7]);
			paymentdescription.add((String) data[8]);

		}

		logger.info("Created Date :" + createdDate);
		logger.info("Payout Amount :" + payoutamount);
		logger.info("payoutTypeList :" + payoutTypeList);
		logger.info("invoiceIdProofList:" + invoiceIdProofList);
		logger.info("subMerchantMidList:" + srcrefno);
		logger.info("payoutStatusList :" + payoutStatusList);
		logger.info("failurereason :" + failurereason);
		logger.info("businessname :" + businessname);
		logger.info("paymentdescription :" + paymentdescription);

		String formattedDate = null;
		String formattedTime = null;
		try {
			formattedDate = new SimpleDateFormat("dd-MMM-yyyy")
					.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdDate.get(0)));
			formattedTime = new SimpleDateFormat("HH:mm:ss")
					.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(createdDate.get(0)));
			logger.info("formattedTime  :" + formattedTime);
			logger.info("formattedDate  :" + formattedDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		DataTransferObject dt = new DataTransferObject();
		try {

			logger.info("Initiate ---------- Payout Transaction Slip ");

			// dt.setCreatedDate(createdDate.get(0));
			dt.setCreatedDate(formattedDate);
			dt.setCreatedTime(formattedTime);
			dt.setTotal(payoutamount.get(0));
			dt.setPayoutType(payoutTypeList.get(0));
			dt.setInvoiceNo(invoiceIdProofList.get(0));
			dt.setSrcrefno(srcrefno.get(0));
			dt.setPayoutstatus(payoutStatusList.get(0));
			if (payoutStatusList.get(0) != null) {

				if (payoutStatusList.get(0).toString().equals("A")) {
					dt.setPayoutstatus("To Process");

				} else if (payoutStatusList.get(0).toString().equals("F")) {
					dt.setPayoutstatus("Failed");
				} else if (payoutStatusList.get(0).toString().equals("S")) {
					dt.setPayoutstatus("Processing");
				} else if (payoutStatusList.get(0).toString().equals("pp")) {
					dt.setPayoutstatus("Paid");
				} else if (payoutStatusList.get(0).toString().equals("pd")) {
					dt.setPayoutstatus("Declined");
				}

			}

			dt.setFailurereason(failurereason.get(0));
			dt.setMerchantName(businessname.get(0));
			dt.setPaymentdescription(paymentdescription.get(0));

			logger.info(dt);

			logger.info("Ready To View ---------- Payout Transaction Slip ");

		} catch (Exception e) {

			logger.error("Error To Read Payout Transaction by Invoice ID", e);
		}

		request.setAttribute("dto", dt);
		logger.info("Moving to JSP");
		model.addAttribute("pageBean", pageBean);

		return "merchantweb/transaction/PayoutReceipt";

	}

	// Payout Sales Slip - End
	public static String sendEmailtoFinance(String UUID, String withdrawAmount, String merchantId, String bankbalance,
			Merchant merchant, double sumOfCurrentbalance, double sumofPreWithdraw, String adjustmentType,
			String overDraftFee) {

		String response = null;
		String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");

		String toAddress = PropertyLoad.getFile().getProperty("MERCHANT_WITHDRAW_MAIL_TO");
		String ccAddress = PropertyLoad.getFile().getProperty("MERCHANT_WITHDRAW_MAIL_CC");
//      String subject = PropertyLoad.getFile().getProperty("MERCHANT_WITHDRAW_TEXT_BODY");
		String subject = adjustmentType + " has been initiated";
		String fromName = PropertyLoad.getFile().getProperty("FROMNAME");

		String merchantNotificationForApproval = PropertyLoad.getFile()
				.getProperty("MERCHANT_NOTIFICATION_APPROVE_MAIL");
		String merchantNotificationForReview = PropertyLoad.getFile().getProperty("MERCHANT_NOTIFICATION_REVIEW_MAIL");

		logger.info("merchantNotificationForApproval :" + merchantNotificationForApproval);
		logger.info("merchantNotificationForReview :" + merchantNotificationForReview);

		logger.info("Email from :" + fromAddress);
		logger.info("Sending Email to :" + toAddress);
		logger.info("Email subject :" + subject);

		logger.info("Email From Name :" + fromName);
		logger.info("Sending Email to :" + toAddress);

		Utils util = new Utils();
		StringBuilder result = new StringBuilder();
		String timeStamp = util.currentTimestampToWithdraw();

		logger.info("Time Stamp to Withdraw : " + timeStamp);
		logger.info("Merchant Id  : " + merchantId);

		// Concatenate values
		String concatenatedString = UUID + "/" + merchantId + "/" + withdrawAmount;
		// Encode with Base64
		byte[] encodedBytes = Base64.getEncoder().encode(concatenatedString.getBytes());
		String encryptedParam = new String(encodedBytes);

		result.append("Hey Finance Team, ").append("<br><br>").append("A ").append(adjustmentType.toLowerCase())
				.append(" has been initiated. Please find the details below and review them: ")
				.append("<br><br><ul style=\"list-style-type:disc\">").append("<li>Bank Balance: MYR ")
				.append(amountFormate(bankbalance)).append("</li>").append("</ul>")
				.append("<ul style=\"list-style-type:disc\">").append("<li>Merchant/Partner: ")
				.append(merchant.getBusinessName()).append("</li>").append("<li>Balance Before Adjustment: MYR ")
				.append(amountFormate(String.valueOf(sumofPreWithdraw))).append("</li>")
				.append("<li>Adjustment Amount: MYR ").append(amountFormate(withdrawAmount)).append("</li>");
		if (overDraftFee != null) {
			// For overdraft adjustment alone, add the overdraft fee.
			result.append("<li>Overdraft Fee: MYR ").append(amountFormate(overDraftFee)).append("</li>");
		}

		result.append("<li>Total Balance: MYR ").append(amountFormate(String.valueOf(sumOfCurrentbalance)))
				.append("</li>").append("</ul>").append("<ul style=\"list-style-type:disc\">")
				.append("<li>Date and Time: ").append(timeStamp).append("</li>").append("<li>Reference Id: ")
				.append(UUID).append("</li>").append("</ul>")

//                  .append("<br>Please review the details. If they are correct, click Approve. Otherwise, click Review.")
//                  .append("<br><br><a href=\"").append(merchantNotificationForApproval).append(encryptedParam)
//                  .append("\">").append("Click to Approve</a>").append(" &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ")
//                  .append("<a href=\"").append(merchantNotificationForReview).append(encryptedParam).append("\">")
//                  .append("Click to Review</a>") 

				.append("<br><br> Keep up the good work.<br>").append("<br> Mobi ");

		String textBody = result.toString();

		try {
			int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, ccAddress,
					null, textBody);
			logger.info("Email Sent Successfully to " + toAddress);
			logger.info("Mail Response : " + mailResponse);

			return response = String.valueOf(mailResponse);
		} catch (Exception pe) {
			response = "400";
			logger.info("Exception while sending mail : " + pe);
		}
		return response;
	}

	public static String amountFormate(String amountString) {

		DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
		double amount = Double.parseDouble(amountString);
		String formattedAmount = decimalFormat.format(amount);

		logger.info("Formatted amount: " + formattedAmount);
		return formattedAmount;
	}

	public static Timestamp getCurrentTimestamp() {

		LocalDateTime currentDateTime = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		String formattedDateTime = currentDateTime.format(formatter);
		LocalDateTime parsedDateTime = LocalDateTime.parse(formattedDateTime, formatter);
		Timestamp timestamp = Timestamp.valueOf(parsedDateTime);

		return timestamp;
	}

	/* FPX Failed Summary Start */

	// fpx failed summary

	@RequestMapping(value = { "/fpxfailList" }, method = RequestMethod.GET)
	public String fpxfailList(final Model model, final java.security.Principal principal, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("FPX Failed Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionFPXFailList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		transactionService.merchantfailedFpxtranscation(paginationBean, null, null,
				currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getFpxMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getMid(), "FPX", currentMerchant);

		model.addAttribute("paginationBean", paginationBean);
		logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());

		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// SearchFpxFailedSummary

	@RequestMapping(value = { "/SearchfpxfailList" }, method = RequestMethod.GET)
	public String SearchfpxfailList(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam("date") String fromdate, @RequestParam("date1") String todate) {
		logger.info("Search FPX Failed Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionFPXFailList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		transactionService.merchantfailedFpxtranscation(paginationBean, fromdate, todate,
				currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getFpxMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getMid(), "FPX", currentMerchant);

		model.addAttribute("paginationBean", paginationBean);
		logger.info("Search Mid:" + currentMerchant.getMid().getUmEzywayMid());

		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// ExportFPXFailedSummary

	@RequestMapping(value = "/exportFpxFailedTxnSummary", method = RequestMethod.GET)
	public ModelAndView exportFpxFailedTxnSummary(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			final java.security.Principal principal) {

		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());

		String dat = fromdate;
		String dat1 = todate;
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();

		transactionService.merchantfailedFpxtranscationexport(paginationBean, fromdate, todate,
				currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getFpxMid(),
				currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getMid(), "FPX", currentMerchant);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		logger.info("Inside the export method");
		List<FpxTransaction> list1 = paginationBean.getItemList();
		if (list1.size() == 0) {

			PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionFPXFailList",
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("exportData", "noRecords");

			ModelAndView modelAndView = new ModelAndView(TEMPLATE_MERCHANT);

			return modelAndView;

		} else {

			if (!(export.equals("PDF"))) {
				// return new ModelAndView("txnListUMExcel", "umTxnList", list1);
				return new ModelAndView("fpxfailedtxnsummaryExcel", "txnList2", list1);
			} else {
				return new ModelAndView("fpxfailedtxnsummaryExcel", "txnList2", list1);
			}
		}
	}

	@RequestMapping(value = { "/refactoredWithDraw" }, method = RequestMethod.GET)
	public String refactoredWithDraw(Model model, HttpServletRequest request,
			@RequestParam("finalwithdrawamount") String amount, @RequestParam final String merchantId,
			@RequestParam final String withdrawType, @RequestParam final String withdrawalComment,
			final java.security.Principal principal) throws ParseException, JsonProcessingException {

		// Generating Payout-ID referance for every withdraw
		UUID uuid = UUID.randomUUID();
		String payoutID = uuid.toString();
		String withdrawAmount = amount.replace("RM", "").replace(",", "").trim();
		boolean isWithdrawInitiated = true;
		boolean isOverdraftAvailable = false;
		
		final DecimalFormat df = new DecimalFormat("#,##0.00");

		try {
			boolean isUpdated = false;
			logger.info("WithDraw Amount from JSP : " + amount + ", corresponding merchantId : " + merchantId
					+ ", WithdrawType : " + withdrawType);

			HttpSession session = request.getSession();
			String userName = principal.getName();

			PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
			Merchant merchant = merchantService.loadMerchant((String) session.getAttribute("userName"));

			String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

			// fetch bank balance
			List<PayoutBankBalance> bankBalance = merchantDao.loadTotalPayoutBankbalance();

			// fetch available balance from view
			String availableBalance = transactionDao.loadPayoutbalanceView(Long.valueOf(merchantId));

			// To check if the merchant has an overdraft amount.
			SettlementDetails settlementDetail = merchantDao
					.loadSettlementDetailsByMerchantId(String.valueOf(merchant.getId()));

			isOverdraftAvailable = settlementDetail != null && settlementDetail.getOverDraftLimit() != null
					&& Double.parseDouble(settlementDetail.getOverDraftLimit().replace(",", "")) >= 1.00
					&& Double.parseDouble(availableBalance.replace(",", "")) >= 1.00;

			// Get AvaliableBalance View data by mercahntId
			Double avaliableBalanceBeforeWithdraw = merchantDao.getAvaliableBalanceByMerchantId(merchantId);
			
			// Make a entry in WithdrawalTransactionsDetails table
			WithdrawalTransactionsDetails withdrawDetails = new WithdrawalTransactionsDetails();
			withdrawDetails.setCreatedDate(getCurrentTimestamp());
			withdrawDetails.setMerchantId(merchantId);
			withdrawDetails.setPayoutFundDisbursmentStatus("Success");
			withdrawDetails.setWithdrawAmount(df.format(Double.valueOf(withdrawAmount)).replace(",", ""));
			withdrawDetails.setPayoutId(payoutID);
			withdrawDetails.setComment(withdrawalComment);
			withdrawDetails.setPreWithdrawAvailableBalance(df.format(avaliableBalanceBeforeWithdraw).replace(",", ""));
//	          witvhdrawDetails.setPostWithdrawAvailableBalance(postWithdrawAvailableBalance);

			if (withdrawType.equalsIgnoreCase("SettlementAdjustment")) {
				withdrawDetails.setWithdrawType("Settlement Adjustment");
			} else if (withdrawType.equalsIgnoreCase("OverDraftAdjustment")) {
				withdrawDetails.setWithdrawType("OverDraft Adjustment");
			} else {
				withdrawDetails.setWithdrawType("Finance Withdraw");
			}
			// Formatting the Withdraw type string to store in db
			withdrawDetails.setWithdrawType(withdrawDetails.getWithdrawType().replace(" ", ""));
			merchantDao.saveWithdrawalTransactionDetailsTable(withdrawDetails);

			// Get WithdrawalTransactionsDetails by PayoutId
			WithdrawalTransactionsDetails withdrawDetail = merchantDao
					.getWithdrawalTransactionsDetailsByPayoutId(payoutID);

			// Make a entry in BalanceAudit table
			BalanceAudit balanceAudit = new BalanceAudit();
			balanceAudit.setMerchantId(merchantId);
			if (withdrawType.equalsIgnoreCase("SettlementAdjustment")) {
				balanceAudit.setTransactionType("Withdrawal");
			} else if (withdrawType.equalsIgnoreCase("OverDraftAdjustment")) {
				balanceAudit.setTransactionType("OverdraftFeeAdjustment");
			} else if (withdrawType.equalsIgnoreCase("Withdraw")) {
				balanceAudit.setTransactionType("Withdrawal");
			}
			
			balanceAudit.setSettlementDate(currentDate);
			balanceAudit.setAmount(df.format(Double.valueOf(withdrawAmount)).replace(",", ""));
			balanceAudit.setWithdrawalMapping(String.valueOf(withdrawDetail.getId()));
			merchantDao.saveBalanceAuditTable(balanceAudit);

			// Double check the WithdrawalTransactionsDetails_Table & BalanceAudit_Table has
			// been Updated
			if (Objects.isNull(withdrawDetail)) {
				logger.error("Withdraw hasn't been updated in PayoutDepositDetails table for merchantId: " + merchantId
						+ ", " + merchant.getBusinessName() + ", PayoutID" + payoutID);
			} else {
				BalanceAudit balanceAuditIsUpdated = merchantDao
						.checkBalanceAuditEntryUpdatedByWithdrawalMapping(String.valueOf(withdrawDetail.getId()));

				if (Objects.isNull(balanceAuditIsUpdated)) {
					logger.error("Withdraw hasn't been updated in BalanceAudit table for merchantId: " + merchantId
							+ "," + merchant.getBusinessName() + ", PayoutID:" + payoutID + ", BalanceAudit: "
							+ balanceAuditIsUpdated);
				} else {
					isUpdated = true;
				}
			}

			if (isUpdated) {
				// If WithdrawalTransactionsDetails_Table is updated update the
				// postAvaliableBalance
				WithdrawalTransactionsDetails withdrawDetailToUpdatePostWithdraw = new WithdrawalTransactionsDetails();
				withdrawDetailToUpdatePostWithdraw.setId(withdrawDetail.getId());
				withdrawDetailToUpdatePostWithdraw.setPostWithdrawAvailableBalance(df.format(merchantDao.getAvaliableBalanceByMerchantId(merchantId)));
				merchantDao.updateWithdrawalTransactionDetailsTable(withdrawDetailToUpdatePostWithdraw);

				// Trigger an acknowledgment email.
				Double avaliableBalanceAfterWithdraw = avaliableBalanceBeforeWithdraw - Double.valueOf(withdrawAmount);
				String mailResponse = sendEmailtoFinance_PayoutRefactored(payoutID, withdrawAmount, merchantId,
						bankBalance, merchant, avaliableBalanceBeforeWithdraw, avaliableBalanceAfterWithdraw,
						withdrawDetails.getWithdrawType(), withdrawDetails.getComment(),userName);
			} else {
				logger.error("Withdraw update has failed. No acknowledgment email sent.");
			}

			// Returning to Jsp
			SettlementDetails settlementDetails = transactionDao.getDataFromSettlementDetails(merchantId);
			String availableBalance1 = transactionDao.loadPayoutbalanceView(Long.valueOf(merchantId));

			double eligiblePayoutAmount = 0.0;
			if (settlementDetails != null && availableBalance1 != null) {
				double balance = Double.parseDouble(availableBalance1);
				double overdraftLimit = Double.parseDouble(settlementDetails.getOverDraftLimit());
				eligiblePayoutAmount = balance - overdraftLimit;

				eligiblePayoutAmount = eligiblePayoutAmount <= 1 ? 0.00 : eligiblePayoutAmount;

			}

			if (availableBalance1 == null || availableBalance1.toString().trim().isEmpty()) {
				availableBalance1 = "0.00";
			}

			PayoutBankBalance payoutBankBalance = merchantDao.loadBankbalance();

			PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/refactoredPayoutbalance",
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
			
			model.addAttribute("payoutbankAmount", payoutBankBalance.getAmount())
					.addAttribute("eligiblePayoutAmount", df.format(eligiblePayoutAmount))
					.addAttribute("pageBean", pageBean)
					.addAttribute("availableBalance", df.format(Double.parseDouble(availableBalance1)))
					.addAttribute("merchantid", merchant.getId()).addAttribute("paginationBean", paginationBean)
					.addAttribute("adminusername", userName).addAttribute("loginname", principal.getName())
					.addAttribute("overdraftLimit", settlementDetails.getOverDraftLimit())
					.addAttribute("settlementDetails", settlementDetails)
					.addAttribute("isOverdraftAvailable", isOverdraftAvailable);
		} catch (Exception e) {

			e.printStackTrace();
			// Make a withdraw failed entry in WithdrawalTransactionsDetails table
			WithdrawalTransactionsDetails withdrawDetails = new WithdrawalTransactionsDetails();
			withdrawDetails.setCreatedDate(getCurrentTimestamp());
			withdrawDetails.setMerchantId(merchantId);
			withdrawDetails.setPayoutFundDisbursmentStatus("WithdrawFailed");
			withdrawDetails.setWithdrawAmount(withdrawAmount);
			withdrawDetails.setPayoutId(payoutID);
			if (withdrawType.equalsIgnoreCase("SettlementAdjustment")) {
				withdrawDetails.setWithdrawType("Settlement Adjustment");
			} else if (withdrawType.equalsIgnoreCase("OverDraftAdjustment")) {
				withdrawDetails.setWithdrawType("OverDraft Adjustment");
			} else {
				withdrawDetails.setWithdrawType("Withdraw");
			}
			// Formatting the Withdraw type string to store in db
			withdrawDetails.setWithdrawType(withdrawDetails.getWithdrawType().replace(" ", ""));
			merchantDao.saveWithdrawalTransactionDetailsTable(withdrawDetails);

			logger.error("WithDraw failed : " + e.getMessage() + ", " + e);
			
			isWithdrawInitiated = false;

//			HttpSession session = request.getSession();
//			String userName = principal.getName();
//
//			PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
//			Merchant merchant = merchantService.loadMerchant((String) session.getAttribute("userName"));
//
//			// Returning to Jsp
//			String availableBalance = transactionDao.loadPayoutbalanceView(Long.valueOf(merchantId));
//			SettlementDetails settlementDetails = transactionDao.getDataFromSettlementDetails(merchantId);
//
//			double eligiblePayoutAmount = 0.0;
//			if (settlementDetails != null && availableBalance != null) {
//				double balance = Double.parseDouble(availableBalance);
//				double overdraftLimit = Double.parseDouble(settlementDetails.getOverDraftLimit());
//				eligiblePayoutAmount = balance - overdraftLimit;
//
//				eligiblePayoutAmount = eligiblePayoutAmount <= 0 ? 0.00 : eligiblePayoutAmount;
//
//			}
//
//			if (availableBalance == null || availableBalance.toString().trim().isEmpty()) {
//				availableBalance = "0.00";
//			}
//
//			PayoutBankBalance payoutBankBalance = merchantDao.loadBankbalance();
//
//			PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/updatePayoutBalance",
//					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//
//			DecimalFormat df = new DecimalFormat("#,##0.00");
//
//			model.addAttribute("payoutbankAmount", payoutBankBalance.getAmount())
//					.addAttribute("eligiblePayoutAmount", df.format(eligiblePayoutAmount))
//					.addAttribute("pageBean", pageBean)
//					.addAttribute("availableBalance", df.format(Double.parseDouble(availableBalance)))
//					.addAttribute("merchantid", merchant.getId()).addAttribute("paginationBean", paginationBean)
//					.addAttribute("adminusername", userName).addAttribute("loginname", principal.getName())
//					.addAttribute("overdraftLimit", settlementDetails.getOverDraftLimit())
//					.addAttribute("settlementDetails", settlementDetails)
//					.addAttribute("isOverdraftAvailable", isOverdraftAvailable);
		} finally {

			/* Returning Model JSP */
			String adminUsername = principal.getName();
			HttpSession session = request.getSession();
			String merchantUserName = (String) session.getAttribute("userName");			
			Merchant currentMerchant = merchantService.loadMerchant((String) session.getAttribute("userName"));

			final DateTimeFormatter dateNdTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

			PageBean pageBean = new PageBean("payout transactions list",
					"merchantweb/transaction/refactoredPayoutbalance", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");

			String availableBalance = transactionDao.loadPayoutbalanceView(currentMerchant.getId());
			SettlementDetails settlementDetails = transactionDao
					.getDataFromSettlementDetails(currentMerchant.getId().toString());

			logger.info("Returning Payout Summary, Available balance Page::::::::::::> " + currentMerchant.getId()
					+ ", Merchant UserName: " + merchantUserName + ", Admin UserName: " + adminUsername
					+ ", Available Balance: " + availableBalance + ", SettlementDetails Bal: "
					+ settlementDetails != null && settlementDetails.getOverDraftLimit() != null
							? settlementDetails.getOverDraftLimit()
							: settlementDetails);

			// Check OverDraft available.
			isOverdraftAvailable = settlementDetails != null && settlementDetails.getOverDraftLimit() != null
					&& Double.parseDouble(settlementDetails.getOverDraftLimit().replace(",", "")) >= 1.00
					&& Double.parseDouble(availableBalance.replace(",", "")) >= 1.00;
					
			String jsonResponse = null; 
			Map<String, Object> response = new HashMap<String, Object>();

			// Check SettlementDetails data's r present.
			if (settlementDetails != null && settlementDetails.getOverDraftLimit() != null && availableBalance != null) {

				double balance = Double.parseDouble(availableBalance);
				double overdraftLimit = Double.parseDouble(settlementDetails.getOverDraftLimit());
				double eligibleSettlementAmount = Math.max(0.00, balance - overdraftLimit);

				// �Validating admin user name and merchant user name are the same; if so,consider as merchant.
				boolean isAdminUser = merchantUserName.trim().equalsIgnoreCase(principal.getName().trim());

				// Enable finance withdraw, If its admin_user & the Eligible Settlement_Amount > 0
				boolean isFinanceWithdrawEnabled = !isAdminUser && eligibleSettlementAmount > 5.0;
						
				response = currencyExchangeService.createResponseMap(currentMerchant.getId(),
						merchantUserName, adminUsername, isOverdraftAvailable, false,
						df.format(Double.parseDouble(availableBalance)), df.format(eligibleSettlementAmount),
						df.format(overdraftLimit), LocalDateTime.now().format(dateNdTime), 0.0,
						new HashMap<>(), new HashMap<>(),
						isFinanceWithdrawEnabled, 0.0);

				jsonResponse = buildJsonResponse(response);
			} else {
				response = currencyExchangeService.createResponseMap(currentMerchant.getId(), merchantUserName,
						adminUsername, false, false, "0.00", "0.00", "0.00", "N/A", 0.0, new HashMap<>(),
						new HashMap<>(), false, 0.0);

				jsonResponse = buildJsonResponse(response);
			}
			
			logger.info("JOSN response: " + jsonResponse);

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("withdrawInitiated", isWithdrawInitiated);
			model.addAttribute("rawPayoutBalancePageResponse", response);
			model.addAttribute("payoutBalancePageResponse", jsonResponse);
			model.addAttribute("withdrawRequestResult", "financeWithdrawRequestSuccess");
			model.addAttribute("withdrawRequestResultAmount", "RM " + amount);
			
			return TEMPLATE_MERCHANT;		
		}
	}

	public static String sendEmailtoFinance_PayoutRefactored(String UUID, String withdrawAmount, String merchantId,
			List<PayoutBankBalance> bankbalance, Merchant merchant, double beforeWithdraw, double afterWithdraw, String adjustmentType,
			String comment,String userName) {

		String response = null;
		String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");

		String toAddress = PropertyLoad.getFile().getProperty("MERCHANT_WITHDRAW_MAIL_TO");
		String ccAddress = PropertyLoad.getFile().getProperty("MERCHANT_WITHDRAW_MAIL_CC");
		String subject = adjustmentType + " has been initiated";
		String fromName = PropertyLoad.getFile().getProperty("FROMNAME");

		Utils util = new Utils();
		StringBuilder result = new StringBuilder();
		String timeStamp = util.currentTimestampToWithdraw();
		String htmlContent = util.withdrawMailContent(bankbalance,merchant,amountFormate(String.valueOf(beforeWithdraw)),amountFormate(withdrawAmount),amountFormate(String.valueOf(afterWithdraw)),timeStamp,UUID,comment,userName);

//		result.append("Hey Finance Team, ").append("<br><br>").append("A ").append(adjustmentType.toLowerCase())
//				.append(" has been initiated. Please find the details below and review them: ")
//				.append("<br><br><ul style=\"list-style-type:disc\">").append("<li>Bank Balance: MYR ")
//				.append(amountFormate(bankbalance)).append("</li>").append("</ul>")
//				.append("<ul style=\"list-style-type:disc\">").append("<li>Merchant/Partner: ")
//				.append(merchant.getBusinessName()).append("</li>").append("<li>Balance Before Adjustment: MYR ")
//				.append(amountFormate(String.valueOf(beforeWithdraw))).append("</li>")
//				.append("<li>Adjustment Amount: MYR ").append(amountFormate(withdrawAmount)).append("</li>")
//				.append("<li>Total Balance: MYR ").append(amountFormate(String.valueOf(afterWithdraw))).append("</li>")
//				.append("</ul>").append("<ul style=\"list-style-type:disc\">").append("<li>Date and Time: ")
//				.append(timeStamp).append("</li>").append("<li>Reference Id: ").append(UUID).append("</li>")
//				.append("<li>Comments: ").append(comment).append("</ul>")
//
//				.append("<br><br> Keep up the good work.<br>").append("<br> Mobi ");

//		String textBody = result.toString();

		try {
			int mailResponse = ElasticEmailClient.sendemailSlip(fromAddress, subject, fromName, toAddress, ccAddress,
					null, htmlContent);

			logger.info("Triggering Withdraw Email for MerchantId: " + merchantId + ", fromAddress: " + fromAddress
					+ ", toAddress: " + toAddress + " Sent Successfully: " + mailResponse + ", to: " + toAddress);
			return response = String.valueOf(mailResponse);
		} catch (Exception pe) {
			response = "400";
			logger.error("Exception while sending mail : " + pe.getMessage(), pe);
		}
		return response;
	}

//	Old UpdatePayoutbalance

//	@RequestMapping(value = { "/updatePayoutbalance" }, method = RequestMethod.GET)
//	public String updatePayoutbalance(final Model model,
//			final java.security.Principal principal, HttpServletRequest request) {
//
//		logger.info("Payout Summary balance  :********* " + principal.getName());
//
//		HttpSession session = request.getSession();
//		String myName = (String) session.getAttribute("userName");
//		String adminusername = principal.getName();
//		logger.info("Adminusername :" + adminusername);
//		logger.info("currently logged in as request: " + request.getSession());
//
//		logger.info("currently logged in as " + myName);
//
//		logger.info("Payout Summary balance  :********* " + myName);
//
//		Merchant currentMerchant = merchantService.loadMerchant(myName);
//		currentMerchant.getId();
//
//		logger.info("Payout Summary balance  :********* " + currentMerchant.getId());
//
//		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/updatePayoutBalance",
//				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//		model.addAttribute("pageBean", pageBean);
//
//		
//		Object availableBalance = transactionService.updateloadPayoutbalance(currentMerchant.getId());
//
//		model.addAttribute("availableBalance", availableBalance);
//		model.addAttribute("merchantid", currentMerchant.getId());
//		model.addAttribute("adminusername", adminusername);
//		model.addAttribute("loginname", principal.getName());
//
//		return TEMPLATE_MERCHANT;
//	}

	@RequestMapping(value = { "/updatePayoutbalance" }, method = RequestMethod.GET)
	public String updatePayoutbalance(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		String adminusername = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/updatePayoutBalance",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		try {
			String availableBalance = transactionDao.loadPayoutbalanceView(currentMerchant.getId());
			SettlementDetails settlementDetails = transactionDao
					.getDataFromSettlementDetails(currentMerchant.getId().toString());

			logger.info("Payout Summary balance ::::::::::::> " + currentMerchant.getId() + ", Username: " + myName
					+ ", Available Balance: " + availableBalance + ", SettlementDetails Bal: "
					+ settlementDetails != null && settlementDetails.getOverDraftLimit() != null
							? settlementDetails.getOverDraftLimit()
							: settlementDetails);

			// Check OverDraft available.
			boolean isOverdraftAvailable = settlementDetails != null && settlementDetails.getOverDraftLimit() != null
					&& Double.parseDouble(settlementDetails.getOverDraftLimit().replace(",", "")) >= 1.00
					&& Double.parseDouble(availableBalance.replace(",", "")) >= 1.00;
					
			if (settlementDetails != null && settlementDetails.getOverDraftLimit() != null
					&& availableBalance != null) {
				
				DecimalFormat df = new DecimalFormat("#,##0.00");

				double balance = Double.parseDouble(availableBalance);
				double overdraftLimit = Double.parseDouble(settlementDetails.getOverDraftLimit());
				double eligiblePayoutAmount = 0.0;

				eligiblePayoutAmount = balance - overdraftLimit;
				eligiblePayoutAmount = eligiblePayoutAmount <= 1 ? 0.00 : eligiblePayoutAmount;				
				
				// Variables for currency exchange
				boolean enableCurrencyExchange = false;
				Map<String, Double> exchangeRateMapForAllCountries = new HashMap<>();
				Map<String, Double> exchangeRateMapWithConversionFee = new HashMap<>();
				
				// Check if currency exchange is enabled & verify that the Currency API provides at least one country exchange value in the CurrencyType enum.
				// Fetch directly from DB if currency exchange is enabled
				if (transactionDao.isCurrencyExchangeEnabled(currentMerchant.getId().toString())) {

				    // Get exchange rates from DB for the current day and base currency
				    exchangeRateMapForAllCountries = transactionDao.getExchangeRatesForCurrentDay(CurrencyTypeUtils.getCurrentDate(), "MYR", currentMerchant.getId().toString());

				    // Check if at least one country rate is present (ignoring MYR)
				    boolean isAnyCountryRatePresent = currencyExchangeService.isAnyCountryRatePresent(exchangeRateMapForAllCountries);
				    
				    // Check if the TimeStamp is Between 12.05 MYT and Working day.
				    boolean isWithinWorkingHours  = currencyExchangeService.isWithinWorkingHours();

				    if (isAnyCountryRatePresent && isWithinWorkingHours) {
				        // Format country names (Appending Country name) excluding MYR
				        exchangeRateMapWithConversionFee = currencyExchangeService.getFormattedExchangeRateMap(exchangeRateMapForAllCountries, currentMerchant.getId().toString());
				        enableCurrencyExchange = true;

						logger.info("Currency exchange enabled: " + enableCurrencyExchange + ", for merchantId: " + currentMerchant.getId() + ", Exchange rates: " + exchangeRateMapForAllCountries + ", Formatted-ExchangeRate:" + exchangeRateMapWithConversionFee);
					} else {
						String reason = !isAnyCountryRatePresent ? "no country rates are available except MYR"
								: !isWithinWorkingHours ? "it is outside working hours" : "N/A";
						logger.warn("Currency exchange is not enabled for merchantId: " + currentMerchant.getId()
								+ " because " + reason + ". Timestamp: " + LocalDateTime.now());
					}
				} else {
					logger.info("Currency exchange is disabled for merchantId: " + currentMerchant.getId());
				}
				
////			// Fetch from API.
////			ExchangeRateResponse exchangeRatesForAllCountries = currencyExchangeService.getExchangeRates();
////			exchangeRateMapForAllCountries = currencyExchangeService.getExchangeRateAsMap(exchangeRatesForAllCountries);

				model.addAttribute("availableBalance", df.format(Double.parseDouble(availableBalance)))
						.addAttribute("overdraftLimit", df.format(Double.parseDouble(settlementDetails.getOverDraftLimit())))
						.addAttribute("pageBean", pageBean)
						.addAttribute("merchantid", currentMerchant.getId())
						.addAttribute("adminusername", adminusername)
						.addAttribute("loginname", principal.getName())
						.addAttribute("eligiblePayoutAmount", df.format(eligiblePayoutAmount))
						.addAttribute("settlementDetails", settlementDetails)
						.addAttribute("isOverdraftAvailable", isOverdraftAvailable)
						.addAttribute("enableCurrencyExchange", enableCurrencyExchange)
						.addAttribute("formattedExchangeRateMap", exchangeRateMapWithConversionFee);
			} else {

				logger.error("Available Balance: " + availableBalance + ", SettlementDetails Bal: "
						+ settlementDetails != null && settlementDetails.getOverDraftLimit() != null
								? settlementDetails.getOverDraftLimit()
								: settlementDetails);

				SettlementDetails settlementDetail = new SettlementDetails();
				settlementDetail.setSettlementDate("N/A");

				model.addAttribute("availableBalance", "0.00")
						.addAttribute("overdraftLimit", "0.00")
						.addAttribute("pageBean", pageBean)
						.addAttribute("merchantid", currentMerchant.getId())
						.addAttribute("adminusername", adminusername)
						.addAttribute("loginname", principal.getName())
						.addAttribute("eligiblePayoutAmount", "0.00")
						.addAttribute("settlementDetails", settlementDetail)
						.addAttribute("isOverdraftAvailable", false)
						.addAttribute("enableCurrencyExchange", false);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in Payout balance:" + e.getMessage(), e);

			SettlementDetails settlementDetail = new SettlementDetails();
			settlementDetail.setSettlementDate("N/A");

			model.addAttribute("availableBalance", "0.00").addAttribute("overdraftLimit", "0.00")
					.addAttribute("pageBean", pageBean).addAttribute("merchantid", currentMerchant.getId())
					.addAttribute("adminusername", adminusername).addAttribute("loginname", principal.getName())
					.addAttribute("eligiblePayoutAmount", "0.00").addAttribute("settlementDetails", settlementDetail)
					.addAttribute("isOverdraftAvailable", false)
					.addAttribute("enableCurrencyExchange", false);
		}

		return TEMPLATE_MERCHANT;
	}
	
	@RequestMapping(value = { "/refactored-payoutbalance" }, method = RequestMethod.GET)
	public String refactoredPayoutBalance(final Model model, final java.security.Principal principal,
			HttpServletRequest request) throws JsonProcessingException {
		
		final DecimalFormat df = new DecimalFormat("#,##0.00");
        DateTimeFormatter dateNdTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        		
		String merchantUserName = (String) request.getSession().getAttribute("userName");
		String adminUserName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(merchantUserName.trim());

		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/refactoredPayoutbalance",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		
		String jsonResponse = null;

		try {
			String availableBalance = transactionDao.loadPayoutbalanceView(currentMerchant.getId());
			SettlementDetails settlementDetails = transactionDao.getDataFromSettlementDetails(currentMerchant.getId().toString());

			logger.info("Payout Summary, Available balance Page::::::::::::> " + currentMerchant.getId() + ", Merchant UserName: " + merchantUserName + ", Admin UserName: " + adminUserName 
					+ ", Available Balance: " + availableBalance 
					+ ", SettlementDetails Bal: " + settlementDetails != null && settlementDetails.getOverDraftLimit() != null
																					? settlementDetails.getOverDraftLimit()
																					: settlementDetails);
			
			// Check OverDraft available.
			boolean isOverdraftAvailable = settlementDetails != null && settlementDetails.getOverDraftLimit() != null
					&& Double.parseDouble(settlementDetails.getOverDraftLimit().replace(",", "")) >= 1.00
					&& Double.parseDouble(availableBalance.replace(",", "")) >= 1.00;
					
			// Check SettlementDetails data's r present.
			if (settlementDetails != null && settlementDetails.getOverDraftLimit() != null && availableBalance != null) {
				
				double balance = Double.parseDouble(availableBalance);
				double overdraftLimit = Double.parseDouble(settlementDetails.getOverDraftLimit());
	            double eligibleSettlementAmount = Math.max(0.00, balance - overdraftLimit);
				
				// Variables for currency exchange
				Double exchangeRateFee = 0.0;
				Double usdtExchangeRateFee = 0.0;
	            boolean enableCurrencyExchange = false;
				Map<String, Double> exchangeRateMapWithConversionFee = new HashMap<>();
				Map<String, String> currencyCodesMapWithAbbreviation = new HashMap<>();
				
				//Validating admin user name and merchant user name are the same; if so, consider as merchant.
				boolean isAdminUser = merchantUserName.trim().equalsIgnoreCase(principal.getName().trim());
				
				// Enable finance withdraw, If its admin_user & the Eligible Settlement_Amount > 0
				boolean isFinanceWithdrawEnabled = !isAdminUser && eligibleSettlementAmount > 5.0;
				
				/*
				 * Check if currency exchange is enabled (from the SETTLEMENT_DETAILS table -
				 * EXCHANGE_RATE_FEE column) and verify that the Currency API provides at least
				 * one country exchange value in the CurrencyType enum. Fetch directly from the
				 * DB if currency exchange is enabled.
				 */
				if (transactionDao.isCurrencyExchangeEnabled(currentMerchant.getId().toString()) && isAdminUser) {

				    // Get exchange rates from DB for the current day and base currency
					Map<String, Double> exchangeRateMapForAllCountries = new HashMap<>();
				    exchangeRateMapForAllCountries = transactionDao.getExchangeRatesForCurrentDay(CurrencyTypeUtils.getCurrentDate(), "MYR", currentMerchant.getId().toString());

				    // Check if at least one country rate is present (ignoring MYR)
				    boolean isAnyCountryRatePresent = currencyExchangeService.isAnyCountryRatePresent(exchangeRateMapForAllCountries);
				    
				    // Check if the TimeStamp is Between 08:00 AM MYT - 11:00 AM MYT and Working day.
				    boolean isWithinWorkingHours  = currencyExchangeService.isWithinWorkingHours();

				    if (isAnyCountryRatePresent && isWithinWorkingHours) {
				        // Format country names (Appending Country name) excluding MYR
				        exchangeRateMapWithConversionFee = currencyExchangeService.getFormattedExchangeRateMap(exchangeRateMapForAllCountries, currentMerchant.getId().toString());
					    exchangeRateFee = currencyExchangeService.getExchangeRateFee(exchangeRateMapForAllCountries, currentMerchant.getId().toString());
					    usdtExchangeRateFee = currencyExchangeService.getUsdtExchangeRateFee(exchangeRateMapForAllCountries, currentMerchant.getId().toString());
					    currencyCodesMapWithAbbreviation = currencyExchangeService.getCurrencyCodeMapWithAbbreviation(exchangeRateMapForAllCountries, currentMerchant.getId().toString());
				        enableCurrencyExchange = true;

						logger.info("Currency exchange enabled: " + enableCurrencyExchange + ", for merchantId: " + currentMerchant.getId() + ", Exchange rates: " + exchangeRateMapForAllCountries + ", Formatted-ExchangeRate:" + exchangeRateMapWithConversionFee);
					} else {
						String reason = !isAnyCountryRatePresent ? "no country rates are available except MYR"
								: !isWithinWorkingHours ? "it is outside working hours" : "N/A";
						logger.warn("Currency exchange is not enabled for merchantId: " + currentMerchant.getId()
								+ " because " + reason + ". Timestamp: " + LocalDateTime.now());
					}
				} else {
					logger.info("Currency exchange is disabled for merchantId: " + currentMerchant.getId());
				}

		        Map<String, Object> response = currencyExchangeService.
								        		createResponseMap(currentMerchant.getId(), merchantUserName, adminUserName, 
						                        isOverdraftAvailable, enableCurrencyExchange, 
						                        df.format(Double.parseDouble(availableBalance)), 
						                        df.format(eligibleSettlementAmount), 
						                        df.format(overdraftLimit), 
						                        LocalDateTime.now().format(dateNdTime), 
						                        exchangeRateFee, currencyCodesMapWithAbbreviation, 
						                        exchangeRateMapWithConversionFee, isFinanceWithdrawEnabled, usdtExchangeRateFee);
								        
		        jsonResponse = buildJsonResponse(response);
		        
		        currencyExchangeService.setWithdrawModelAttributes(model, settlementDetails, isOverdraftAvailable, enableCurrencyExchange, balance, 
		                           eligibleSettlementAmount, exchangeRateMapWithConversionFee, currencyCodesMapWithAbbreviation, 
		                           jsonResponse, adminUserName, currentMerchant, principal, isFinanceWithdrawEnabled, pageBean, response);
		        
				model.addAttribute("withdrawRequestResult", "");
				model.addAttribute("withdrawRequestResultAmount", "");
			} else {

				logger.error("Available Balance: " + availableBalance + ", SettlementDetails Bal: "
						+ settlementDetails != null && settlementDetails.getOverDraftLimit() != null
								? settlementDetails.getOverDraftLimit()
								: settlementDetails);

				Map<String, Object> response = currencyExchangeService.createResponseMap(currentMerchant.getId(),
						merchantUserName, adminUserName, false, false, "0.00", "0.00", "0.00", "N/A", 0.0,
						new HashMap<>(), new HashMap<>(), false, 0.0);
		        
		        jsonResponse = buildJsonResponse(response);
		        
		        currencyExchangeService.setWithdrawModelAttributes(model, new SettlementDetails(), false, false, 0.0, 0.0, 
		                           new HashMap<>(), new HashMap<>(), jsonResponse, adminUserName, 
		                           currentMerchant, principal, false, pageBean, response);
				
		        model.addAttribute("withdrawRequestResult", "");
				model.addAttribute("withdrawRequestResultAmount", "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception in Payout balance: " + e.getMessage(), e);

			Map<String, Object> response = currencyExchangeService.createResponseMap(currentMerchant.getId(),
					merchantUserName, adminUserName, false, false, "0.00", "0.00", "0.00", "N/A", 0.0, new HashMap<>(),
					new HashMap<>(), false, 0.0);

	        jsonResponse = buildJsonResponse(response);
	        
	        currencyExchangeService.setWithdrawModelAttributes(model, new SettlementDetails(), false, false, 0.0, 0.0, 
	                           new HashMap<>(), new HashMap<>(), jsonResponse, adminUserName, 
	                           currentMerchant, principal, false, pageBean, response);
	        
	        model.addAttribute("withdrawRequestResult", "");
			model.addAttribute("withdrawRequestResultAmount", "");
		}

		logger.info("Payout Balance, JsonResponse: " + jsonResponse);
		return TEMPLATE_MERCHANT;
	}
	
	private static String buildJsonResponse(Map<String, Object> response) throws JsonProcessingException {
	    ObjectMapper mapper = new ObjectMapper();
	    return mapper.writeValueAsString(response);
	}
	
	@RequestMapping(value = { "/currency-exchange/confirm-notify" }, method = RequestMethod.POST)
	public String confirmAndNotifyCurrencyExchange(
			@RequestParam(value = "totalAmount") String withdrawAmountIncludesExchangeRateFee,
			@RequestParam(value = "selectedCurrency") String currencyType,
			@RequestParam(value = "convertedAmount") String currencyConvertedAmount,
			@RequestParam(value = "selectedCurrencyRate") String selectedCurrencyRate,
			@RequestParam(value = "actualAmount") String actualWithdrawAmount, final Model model,
			final java.security.Principal principal, HttpServletRequest request) throws JsonProcessingException {

		boolean isWithdrawInitiated = true;
		String adminUsername = principal.getName();
		HttpSession session = request.getSession();
		String merchantUserName = (String) session.getAttribute("userName");
		final Merchant currentMerchant = merchantService.loadMerchant(merchantUserName);
		try {
			final double amountWithdrawInMYR = Double
					.parseDouble(withdrawAmountIncludesExchangeRateFee.replace("RM ", "").replace(",", "").trim()); // Remove the "RM" prefix																												

			final double convertedAmount = Double.parseDouble(
					currencyConvertedAmount.substring(0, currencyConvertedAmount.length() - 4).replace(",", "").trim()); // Remove the last 4 characters (space + 3-character suffix)																														

			final double exchangeRate = Double.parseDouble(
					selectedCurrencyRate.substring(0, selectedCurrencyRate.length() - 4).replace(",", "").trim()); // Remove the last 4 characters (space + 3-character suffix)

			double exchangeRateFee = currencyExchangeService.getexchangeRateMDR(currentMerchant.getId().toString());

			final String currentDateAndTime = CurrencyTypeUtils.formattedTimeStamp();
			
			final double actualWithdrawAmountInMYR = Double.valueOf(actualWithdrawAmount.replace(",", "").replace("RM", "").trim());
			
			String uuidString = UUID.randomUUID().toString();

			CompletableFuture.runAsync(() -> {

				logger.info(String.format(
						"Withdrawal Confirmation Details: Admin User: %s, Merchant User: %s, Merchant ID: %s, Actual Amount includes fee(MYR): %.2f, "
								+ "Converted Amount (" + currencyType + ")"
								+ ": %.2f, Actual withdraw amount(Amount to be deducted in Available Balance): %s , Exchange Rate: %.4f, Currency: %s, Exchange Rate Fee: %.4f, "
								+ "Current Date and Time: %s, Transaction UUID: %s",
						adminUsername, merchantUserName, currentMerchant.getId(), amountWithdrawInMYR, convertedAmount, actualWithdrawAmountInMYR, 
						exchangeRate, currencyType, exchangeRateFee, currentDateAndTime, uuidString));

				// Need to double check the, Conversion amount if needed .!!!!

				// Update the Withdraw entry, in db.
				currencyExchangeService.updateWithdrawDetails(currentMerchant, amountWithdrawInMYR, exchangeRate,
						exchangeRateFee, currencyType, currentDateAndTime, uuidString,
						"Merchant requested a currency exchange withdrawal", convertedAmount, actualWithdrawAmountInMYR);

				// Trigger Confirmation Email, to finance for (approval 0r reject).
				currencyExchangeService.sendConfirmationEmailToFiannce(currentMerchant, amountWithdrawInMYR,
						exchangeRate, exchangeRateFee, currencyType, currentDateAndTime, uuidString, convertedAmount, actualWithdrawAmountInMYR);
			}).exceptionally(ex -> {
				logger.error("Exception in confirm & notify currency exchange as E-mail: " + ex.getMessage(), ex);
				return null;
			});
		} catch (Exception e) {
			e.printStackTrace();
			isWithdrawInitiated = false;
			logger.error("Exception in confirm & notify currency exchange as E-mail: " + e);
		} finally {
			/* Returning Model JSP */
			PageBean pageBean = new PageBean("payout transactions list",
					"merchantweb/transaction/refactoredPayoutbalance", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");

			final DecimalFormat df = new DecimalFormat("#,##0.00");

			String jsonResponse = null;

			DateTimeFormatter dateNdTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

			String availableBalance = transactionDao.loadPayoutbalanceView(currentMerchant.getId());
			SettlementDetails settlementDetails = transactionDao
					.getDataFromSettlementDetails(currentMerchant.getId().toString());

			logger.info("Returning Payout Summary, Available balance Page::::::::::::> " + currentMerchant.getId()
					+ ", Merchant UserName: " + merchantUserName + ", Admin UserName: " + adminUsername
					+ ", Available Balance: " + availableBalance + ", SettlementDetails Bal: "
					+ settlementDetails != null && settlementDetails.getOverDraftLimit() != null
							? settlementDetails.getOverDraftLimit()
							: settlementDetails);

			// Check OverDraft available.
			boolean isOverdraftAvailable = settlementDetails != null && settlementDetails.getOverDraftLimit() != null
					&& Double.parseDouble(settlementDetails.getOverDraftLimit().replace(",", "")) >= 1.00
					&& Double.parseDouble(availableBalance.replace(",", "")) >= 1.00;
					
			Map<String, Object> response = new HashMap<String, Object>();
			
			// Check SettlementDetails data's r present.
			if (settlementDetails != null && settlementDetails.getOverDraftLimit() != null && availableBalance != null) {

				double balance = Double.parseDouble(availableBalance);
				double overdraftLimit = Double.parseDouble(settlementDetails.getOverDraftLimit());
				double eligibleSettlementAmount = Math.max(0.00, balance - overdraftLimit);

				// Variables for currency exchange
				Double exchangeRateFee = 0.0;
				Double usdtExchangeRateFee = 0.0;
				boolean enableCurrencyExchange = false;
				Map<String, Double> exchangeRateMapWithConversionFee = new HashMap<>();
				Map<String, String> currencyCodesMapWithAbbreviation = new HashMap<>();

				// �Validating admin user name and merchant user name are the same; if so, consider as merchant.
				boolean isAdminUser = merchantUserName.trim().equalsIgnoreCase(principal.getName().trim());

				// Enable finance withdraw, If its admin_user & the Eligible Settlement_Amount > 0
				boolean isFinanceWithdrawEnabled = !isAdminUser && eligibleSettlementAmount > 5.0;

				if (transactionDao.isCurrencyExchangeEnabled(currentMerchant.getId().toString()) && isAdminUser) {

					// Get exchange rates from DB for the current day and base currency
					Map<String, Double> exchangeRateMapForAllCountries = new HashMap<>();
					exchangeRateMapForAllCountries = transactionDao.getExchangeRatesForCurrentDay(CurrencyTypeUtils.getCurrentDate(), "MYR", currentMerchant.getId().toString());

					// Check if at least one country rate is present (ignoring MYR)
					boolean isAnyCountryRatePresent = currencyExchangeService.isAnyCountryRatePresent(exchangeRateMapForAllCountries);

					// Check if the TimeStamp is Between 08:00 AM MYT - 11:00 AM MYT and Working day.
					boolean isWithinWorkingHours = currencyExchangeService.isWithinWorkingHours();

					if (isAnyCountryRatePresent && isWithinWorkingHours) {
						// Format country names (Appending Country name) excluding MYR
						exchangeRateMapWithConversionFee = currencyExchangeService.getFormattedExchangeRateMap(exchangeRateMapForAllCountries, currentMerchant.getId().toString());
						exchangeRateFee = currencyExchangeService.getExchangeRateFee(exchangeRateMapForAllCountries, currentMerchant.getId().toString());
						currencyCodesMapWithAbbreviation = currencyExchangeService.getCurrencyCodeMapWithAbbreviation(exchangeRateMapForAllCountries, currentMerchant.getId().toString());
					    usdtExchangeRateFee = currencyExchangeService.getUsdtExchangeRateFee(exchangeRateMapForAllCountries, currentMerchant.getId().toString());
						enableCurrencyExchange = true;

						logger.info("Currency exchange enabled: " + enableCurrencyExchange + ", for merchantId: "
								+ currentMerchant.getId() + ", Exchange rates: " + exchangeRateMapForAllCountries
								+ ", Formatted-ExchangeRate:" + exchangeRateMapWithConversionFee);
					}

					response = currencyExchangeService.createResponseMap(currentMerchant.getId(),
							merchantUserName, adminUsername, isOverdraftAvailable, enableCurrencyExchange,
							df.format(Double.parseDouble(availableBalance)), df.format(eligibleSettlementAmount),
							df.format(overdraftLimit), LocalDateTime.now().format(dateNdTime), exchangeRateFee,
							currencyCodesMapWithAbbreviation, exchangeRateMapWithConversionFee,
							isFinanceWithdrawEnabled, usdtExchangeRateFee);

					jsonResponse = buildJsonResponse(response);

				} else {
					response = currencyExchangeService.createResponseMap(currentMerchant.getId(),
							merchantUserName, adminUsername, false, false, "0.00", "0.00", "0.00", "N/A", 0.0,
							new HashMap<>(), new HashMap<>(), false, 0.0);

					jsonResponse = buildJsonResponse(response);
				}

				logger.info("JOSN response: " + jsonResponse);

				model.addAttribute("pageBean", pageBean);
				model.addAttribute("withdrawInitiated", isWithdrawInitiated);
				model.addAttribute("rawPayoutBalancePageResponse", response);
				model.addAttribute("payoutBalancePageResponse", jsonResponse);
				model.addAttribute("withdrawRequestResult", "merchantWithdrawSuccess");
				model.addAttribute("withdrawRequestResultAmount", currencyConvertedAmount);
			}
			return TEMPLATE_MERCHANT;
		}
	}
	
//	@RequestMapping(value = { "/refactored-payoutbalance" }, method = RequestMethod.GET)
//	public String refactoredPayoutBalance(final Model model, final java.security.Principal principal,
//			HttpServletRequest request) throws JsonProcessingException {
//		
//		final DecimalFormat df = new DecimalFormat("#,##0.00");
//        DateTimeFormatter dateNdTime = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
//        		
//		String merchantUserName = (String) request.getSession().getAttribute("userName");
//		String adminUserName = principal.getName();
//		Merchant currentMerchant = merchantService.loadMerchant(merchantUserName.trim());
//
//		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/refactoredPayoutbalance",
//				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//		
//		String jsonResponse;
//
//		try {
//			String availableBalance = transactionDao.loadPayoutbalanceView(currentMerchant.getId());
//			SettlementDetails settlementDetails = transactionDao.getDataFromSettlementDetails(currentMerchant.getId().toString());
//
//			logger.info("Payout Summary, Available balance Page::::::::::::> " + currentMerchant.getId() + ", Merchant UserName: " + merchantUserName + ", Admin UserName: " + adminUserName 
//					+ ", Available Balance: " + availableBalance 
//					+ ", SettlementDetails Bal: " + settlementDetails != null && settlementDetails.getOverDraftLimit() != null
//																					? settlementDetails.getOverDraftLimit()
//																					: settlementDetails);
//			
//			// Check OverDraft available.
//			boolean isOverdraftAvailable = settlementDetails != null && settlementDetails.getOverDraftLimit() != null
//					&& Double.parseDouble(settlementDetails.getOverDraftLimit().replace(",", "")) >= 1.00
//					&& Double.parseDouble(availableBalance.replace(",", "")) >= 1.00;
//					
//			// Check SettlementDetails data's r present.
//			if (settlementDetails != null && settlementDetails.getOverDraftLimit() != null && availableBalance != null) {
//				
//				double balance = Double.parseDouble(availableBalance);
//				double overdraftLimit = Double.parseDouble(settlementDetails.getOverDraftLimit());
//	            double eligibleSettlementAmount = Math.max(0.00, balance - overdraftLimit);
//				
//				// Variables for currency exchange
//				boolean enableCurrencyExchange = false;
//				Map<String, Double> exchangeRateMapWithConversionFee = new HashMap<>();
//				Map<String, String> currencyCodesMapWithAbbreviation = new HashMap<>();
//				Double exchangeRateFee = 0.0;
//				
//				//�Validating admin user name and merchant user name are the same; if so, consider as merchant.
//				boolean isAdminUser = merchantUserName.trim().equalsIgnoreCase(principal.getName().trim());
//				
//				// Enable finance withdraw, If its admin_user & the Eligible Settlement_Amount > 0
//				boolean isFinanceWithdrawEnabled = !isAdminUser && eligibleSettlementAmount > 5.0;
//				
//				/*
//				 * Check if currency exchange is enabled (from the SETTLEMENT_DETAILS table -
//				 * EXCHANGE_RATE_FEE column) and verify that the Currency API provides at least
//				 * one country exchange value in the CurrencyType enum. Fetch directly from the
//				 * DB if currency exchange is enabled.
//				 */
//				if (transactionDao.isCurrencyExchangeEnabled(currentMerchant.getId().toString()) && isAdminUser) {
//
//				    // Get exchange rates from DB for the current day and base currency
//					Map<String, Double> exchangeRateMapForAllCountries = new HashMap<>();
//				    exchangeRateMapForAllCountries = transactionDao.getExchangeRatesForCurrentDay(CurrencyTypeUtils.getCurrentDate(), "MYR", currentMerchant.getId().toString());
//
//				    // Check if at least one country rate is present (ignoring MYR)
//				    boolean isAnyCountryRatePresent = currencyExchangeService.isAnyCountryRatePresent(exchangeRateMapForAllCountries);
//				    
//				    // Check if the TimeStamp is Between 08:00 AM MYT - 11:00 AM MYT and Working day.
//				    boolean isWithinWorkingHours  = currencyExchangeService.isWithinWorkingHours();
//
//				    if (isAnyCountryRatePresent && isWithinWorkingHours) {
//				        // Format country names (Appending Country name) excluding MYR
//				        exchangeRateMapWithConversionFee = currencyExchangeService.getFormattedExchangeRateMap(exchangeRateMapForAllCountries, currentMerchant.getId().toString());
//					    exchangeRateFee = currencyExchangeService.getExchangeRateFee(exchangeRateMapForAllCountries, currentMerchant.getId().toString());
//					    currencyCodesMapWithAbbreviation = currencyExchangeService.getCurrencyCodeMapWithAbbreviation(exchangeRateMapForAllCountries, currentMerchant.getId().toString());
//				        enableCurrencyExchange = true;
//
//						logger.info("Currency exchange enabled: " + enableCurrencyExchange + ", for merchantId: " + currentMerchant.getId() + ", Exchange rates: " + exchangeRateMapForAllCountries + ", Formatted-ExchangeRate:" + exchangeRateMapWithConversionFee);
//					} else {
//						String reason = !isAnyCountryRatePresent ? "no country rates are available except MYR"
//								: !isWithinWorkingHours ? "it is outside working hours" : "N/A";
//						logger.warn("Currency exchange is not enabled for merchantId: " + currentMerchant.getId()
//								+ " because " + reason + ". Timestamp: " + LocalDateTime.now());
//					}
//				} else {
//					logger.info("Currency exchange is disabled for merchantId: " + currentMerchant.getId());
//				}
//
//				Map<String, Object> response = new HashMap<>();
//		        response.put("merchant_id", currentMerchant.getId());
//		        response.put("login_username", merchantUserName);
//		        response.put("admin_username", adminUserName);
//		        response.put("is_overdraft_available", isOverdraftAvailable);
//		        response.put("enable_currency_exchange", enableCurrencyExchange);
//		        response.put("available_balance", df.format(Double.parseDouble(availableBalance)));
//		        response.put("eligible_settlement_amount", df.format(eligibleSettlementAmount));
//		        response.put("overdraft_amount", isOverdraftAvailable? df.format(Double.parseDouble(settlementDetails.getOverDraftLimit())) :"0.00");
//		        response.put("available_balance_last_fetched", LocalDateTime.now().format(dateNdTime));
//		        response.put("base_currency", "MYR");
//		        response.put("base_currency_value", 1);
//		        response.put("exchange_rate_last_fetched", LocalDateTime.now().format(dateNdTime));
//		        response.put("exchange_rate_fee", exchangeRateFee);
//		        response.put("currency_codes_map_with_abbreviation", currencyCodesMapWithAbbreviation);
//		        response.put("conversion_rates", exchangeRateMapWithConversionFee);
//		        response.put("enable_finance_withdraw", isFinanceWithdrawEnabled);
//		        
//	            ObjectMapper mapper = new ObjectMapper();
//	            jsonResponse = mapper.writeValueAsString(response);
//				
//	            model.addAttribute("availableBalance", df.format(Double.parseDouble(availableBalance)))
//						.addAttribute("overdraftLimit", df.format(Double.parseDouble(settlementDetails.getOverDraftLimit())))
//						.addAttribute("pageBean", pageBean)
//						.addAttribute("merchantid", currentMerchant.getId())
//						.addAttribute("adminusername", adminUserName)
//						.addAttribute("loginname", principal.getName())
//						.addAttribute("eligiblePayoutAmount", df.format(eligibleSettlementAmount))
//						.addAttribute("settlementDetails", settlementDetails)
//						.addAttribute("isOverdraftAvailable", isOverdraftAvailable)
//						.addAttribute("enableCurrencyExchange", enableCurrencyExchange)
//						.addAttribute("formattedExchangeRateMap", exchangeRateMapWithConversionFee)
//						.addAttribute("payoutBalancePageResponse", jsonResponse)
//						.addAttribute("rawPayoutBalancePageResponse",response)
//						.addAttribute("currencyCodesMapWithAbbreviation", currencyCodesMapWithAbbreviation)
//						.addAttribute("enableFinanceWithdraw",isFinanceWithdrawEnabled);
//			} else {
//
//				logger.error("Available Balance: " + availableBalance + ", SettlementDetails Bal: "
//						+ settlementDetails != null && settlementDetails.getOverDraftLimit() != null
//								? settlementDetails.getOverDraftLimit()
//								: settlementDetails);
//
//				SettlementDetails settlementDetail = new SettlementDetails();
//				settlementDetail.setSettlementDate("N/A");
//				
//				Map<String, Object> response = new HashMap<>();
//		        response.put("merchant_id", currentMerchant.getId());
//		        response.put("login_username", merchantUserName);
//		        response.put("admin_username", adminUserName);
//		        response.put("is_overdraft_available", false);
//		        response.put("enable_currency_exchange", false);
//		        response.put("available_balance", "0.00");
//		        response.put("eligible_settlement_amount", "0.00");
//		        response.put("overdraft_amount", "0.00");
//		        response.put("available_balance_last_fetched", "N/A");
//		        response.put("base_currency", "MYR");
//		        response.put("base_currency_value", 1);
//		        response.put("exchange_rate_last_fetched", "N/A");
//		        response.put("exchange_rate_fee", "0.00");
//		        response.put("conversion_rates", "0.00");
//		        response.put("currency_codes_map_with_abbreviation", new HashMap<>());
//		        response.put("enable_finance_withdraw", false);
//		        
//	            ObjectMapper mapper = new ObjectMapper();
//	            jsonResponse = mapper.writeValueAsString(response);
//
//				model.addAttribute("availableBalance", "0.00")
//						.addAttribute("overdraftLimit", "0.00")
//						.addAttribute("pageBean", pageBean)
//						.addAttribute("merchantid", currentMerchant.getId())
//						.addAttribute("adminusername", adminUserName)
//						.addAttribute("loginname", principal.getName())
//						.addAttribute("eligiblePayoutAmount", "0.00")
//						.addAttribute("settlementDetails", settlementDetail)
//						.addAttribute("isOverdraftAvailable", false)
//						.addAttribute("enableCurrencyExchange", false)
//						.addAttribute("payoutBalancePageResponse", jsonResponse)
//						.addAttribute("rawPayoutBalancePageResponse",response)
//						.addAttribute("currencyCodesMapWithAbbreviation", new HashMap<>())
//						.addAttribute("enableFinanceWithdraw",false);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error("Exception in Payout balance: " + e.getMessage(), e);
//
//			SettlementDetails settlementDetail = new SettlementDetails();
//			settlementDetail.setSettlementDate("N/A");
//			
//			Map<String, Object> response = new HashMap<>();
//	        response.put("merchant_id", currentMerchant.getId());
//	        response.put("login_username", merchantUserName);
//	        response.put("admin_username", adminUserName);
//	        response.put("is_overdraft_available", false);
//	        response.put("enable_currency_exchange", false);
//	        response.put("available_balance", "0.00");
//	        response.put("eligible_settlement_amount", "0.00");
//	        response.put("overdraft_amount", "0.00");
//	        response.put("available_balance_last_fetched", "N/A");
//	        response.put("base_currency", "MYR");
//	        response.put("base_currency_value", 1);
//	        response.put("exchange_rate_last_fetched", "N/A");
//	        response.put("exchange_rate_fee", "0.00");
//			response.put("conversion_rates", "0.00");
//	        response.put("currency_codes_map_with_abbreviation", new HashMap<>());
//	        response.put("enable_finance_withdraw", false);
//
//			ObjectMapper mapper = new ObjectMapper();
//
//			jsonResponse = mapper.writeValueAsString(response);
//
//			model.addAttribute("availableBalance", "0.00")		
//					.addAttribute("overdraftLimit", "0.00")
//					.addAttribute("pageBean", pageBean)
//					.addAttribute("merchantid", currentMerchant.getId())
//					.addAttribute("adminusername", adminUserName)
//					.addAttribute("loginname", principal.getName())
//					.addAttribute("eligiblePayoutAmount", "0.00")
//					.addAttribute("settlementDetails", settlementDetail)
//					.addAttribute("isOverdraftAvailable", false)
//					.addAttribute("enableCurrencyExchange", false)
//					.addAttribute("payoutBalancePageResponse", jsonResponse)
//					.addAttribute("rawPayoutBalancePageResponse",response)
//					.addAttribute("currencyCodesMapWithAbbreviation", new HashMap<>())
//					.addAttribute("enableFinanceWithdraw", false);
//		}
//
//		logger.info("Payout Balance, JsonResponse: " + jsonResponse);
//		return TEMPLATE_MERCHANT;
//	}
		
	@RequestMapping(value = { "/EzySettleList" }, method = RequestMethod.GET)
	public String EzySettleList(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal, HttpServletRequest request) {
		try {
			logger.info("EZYSETTLE SUMMARY BY MERCHANT");

			HttpSession session = request.getSession();
			String myName = (String) session.getAttribute("userName");
			// String myName = principal.getName();
			logger.info("currently logged in as " + myName);
			Merchant currentMerchant = merchantService.loadMerchant(myName);

			PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzySettleSummaryList",
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

			model.addAttribute("pageBean", pageBean);

			PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
			paginationBean.setCurrPage(currPage);

			transactionService.ListofEzySettleSummarymerchant(paginationBean, null, null, currentMerchant, false);

			if (paginationBean.getItemList() == null || paginationBean.getItemList().isEmpty()) {
				model.addAttribute("responseData", "No Records found"); // table
				// response
			} else {
				model.addAttribute("responseData", null);
			}
			model.addAttribute("paginationBean", paginationBean);
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = { "/searchEzySettleList" }, method = RequestMethod.GET)
	public String searchEzySettleList(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1,
//	           @RequestParam final String txntype,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("SEARCH ------ EZYSETTLE SUMMARY BY MERCHANT");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzySettleSummaryList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.ListofEzySettleSummarymerchant(paginationBean, date, date1, currentMerchant, false);
		if (paginationBean.getItemList() == null || paginationBean.getItemList().isEmpty()) {
			model.addAttribute("responseData", "No Records found");
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/exportEzySettleList", method = RequestMethod.GET)
	public ModelAndView exportsearchEzySettleList(@RequestParam final String date, @RequestParam final String date1,
			// @RequestParam final String txntype,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			HttpServletRequest request, @RequestParam(required = false) String export,
			final java.security.Principal principal) {
		logger.info("EXPORT IS " + export);
		logger.info("SEARCH ------ EZYSETTLE SUMMARY BY MERCHANT--");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
		paginationBean.setCurrPage(currPage);
		try {
			transactionService.ListofEzySettleSummarymerchant(paginationBean, date, date1, currentMerchant, true);
			logger.info(paginationBean.getItemList().toString());
			if (paginationBean.getItemList() == null || paginationBean.getItemList().isEmpty()) {

				PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzySettleSummaryList",
						Module.TRANSACTION, "transaction/sideMenuTransaction");

				model.addAttribute("pageBean", pageBean);
				model.addAttribute("paginationBean", paginationBean);
				paginationBean.setItemList(new ArrayList<>());
				model.addAttribute("exportData", "noRecords");
				ModelAndView modelAndView = new ModelAndView(TEMPLATE_MERCHANT);
				return modelAndView;
			} else {

				List<SettlementModel> list = paginationBean.getItemList();

				if (!(export.equals("PDF"))) {
					return new ModelAndView("EzySettleListMerchant", "txnList", list);
				} else {
					return new ModelAndView("EzySettleListPdf", "txnList", list);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return new ModelAndView(TEMPLATE_MERCHANT);
	}

	/* FPX Failed Summary End */
	
	//merchant Consolidate Report 
	
		@RequestMapping(value = { "/MerchantFinanceReport/{currPage}" }, method = RequestMethod.GET)
		public String FinanceReport(final Model model, @PathVariable final int currPage,HttpServletRequest request,
				final java.security.Principal principal) {
			logger.info("Merchant ConsolidateReport for Payin and Payout Transactions:::");
			
			HttpSession session = request.getSession();
			String myName = (String) session.getAttribute("userName");
			String adminusername = principal.getName();
			Merchant currentMerchant = merchantService.loadMerchant(myName);
			String username = currentMerchant.getUsername();
			
			PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/MerchantFinanceReport", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			logger.info("Merchant Transaction Summary:" + principal.getName());
			PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
			paginationBean.setCurrPage(currPage);

			List<Object[]> businessNamesAndUsernames = transactionService.getBusinessNamesAndUsernames();
			logger.info("After this businessNamesAndUsernames Service in Merchant Login:::");

			// transactionService.listpreauthfee(paginationBean, null, null);

			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("businessNamesAndUsernames", businessNamesAndUsernames);
			model.addAttribute("username", username);

			return TEMPLATE_MERCHANT;
		}

		
		@RequestMapping(value = "/MerchantexportPayinTxnAsEmail", method = RequestMethod.GET)
		public String exportPayoutAsEmail(@RequestParam("date") String date, @RequestParam("date1") String date1,
				@RequestParam(value = "currPage", defaultValue = "1") final int currPage, final Model model, HttpServletRequest request,
				 @RequestParam(value = "export", required = false) String export, @RequestParam(value = "username", required = false) String username) {

			logger.info(
					"Merchant ConsolidateReport Export payin and Payout transaction as XLS......................................................................");

			 HttpSession session = request.getSession();
			    if (username != null && session.getAttribute("username") == null) {
			        session.setAttribute("username", username);
			    } else {
			        username = (String) session.getAttribute("username");
			    }
			try {
				logger.info("from date : " + date);
				logger.info("to date : " + date1);
				final String fromDate = convertDateFormatTo_YYY_MM_dd(date);
				final String toDate = convertDateFormatTo_YYY_MM_dd(date1);
				logger.info("After format from date : " + date);
				logger.info("After format to date : " + date1);
				

				logger.info("UserName :" + username);
				Merchant currentMerchant = merchantService.loadMerchant(username);
				String merchantName = currentMerchant.getBusinessShortName();
				String businessName = currentMerchant.getBusinessName();
				Long merchantId = currentMerchant.getId();
				String emailId = currentMerchant.getEmail();
				logger.info("emailId :" + emailId);
				if ("EXCEL".equalsIgnoreCase(export)) {
					ExecutorService executor = Executors.newSingleThreadExecutor();

					CompletableFuture.runAsync(() -> {
						List<FinanceReport> payinReportData = transactionService.getPayinTxnDetailsBetweenDates(fromDate,
								toDate, currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getFpxMid(),
								currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getMid(),
								currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
								currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getBnplMid(),
								currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
								currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(),currentMerchant.getMid().getFiuuMid(),
								currentMerchant);

						byte[] payinExcelContent = transactionService
								.generateMerchantPayinDetailExcelContentForEmail(payinReportData, fromDate, toDate, businessName);

						logger.info("Merchant generatePayinDetailExcelContentForEmail");
						
						  Map<String, byte[]> attachments = new HashMap<>();
					        attachments.put("PayinTransactionReport.xls", payinExcelContent);
					        
						String enblPayout = currentMerchant.getEnblPayout();
						logger.info("Payout is Enabled");

						 if ("Yes".equalsIgnoreCase(enblPayout)) {
					            List<FinanceReport> payoutReportData = transactionService.getPayoutTxnDetailsBetweenDates(fromDate, 
					                toDate, merchantId, currentMerchant);
					            byte[] payoutExcelContent = transactionService.generateMerchantPayoutReportXLSContentForEmail(
					                payoutReportData, fromDate, toDate, merchantName);
					            attachments.put("PayoutTransactionReport.xls", payoutExcelContent);
					        }
						new FinanceReportUtils().sendEmailWithAttachments(attachments, fromDate, toDate, emailId);
					}, executor).exceptionally(ex -> {
						logger.error("Exception in sending E-mail: " + ex.getMessage(), ex);
						return null;
					}).thenRun(executor::shutdown);
				} else {
					logger.warn("Export type must be EXCEL");
				}
			} catch (Exception e) {
				logger.error("Exception in export XLS for Merchant ConsolidateReport: ", e);
			}

			PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/MerchantFinanceReport", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			PaginationBean<PayoutModel> paginationBean = new PaginationBean<>();
			paginationBean.setCurrPage(currPage);
			List<Object[]> businessNamesAndUsernames = transactionService.getBusinessNamesAndUsernames();

			model.addAttribute("paginationBean", paginationBean);
			model.addAttribute("businessNamesAndUsernames", businessNamesAndUsernames);
			model.addAttribute("isEmailSent", "true");

			return TEMPLATE_MERCHANT;
		}

		public static String convertDateFormatTo_YYY_MM_dd(String inputDate) {
			try {
				DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				LocalDate date = LocalDate.parse(inputDate, inputFormatter);
				String outputDate = date.format(outputFormatter);
				return outputDate;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

}
