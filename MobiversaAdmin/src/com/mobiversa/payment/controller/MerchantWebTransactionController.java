package com.mobiversa.payment.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.mobiversa.common.bo.BizAppSettlement;
import com.mobiversa.common.bo.BnplTxnDetails;
import com.mobiversa.common.bo.BoostDailyRecon;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.GrabPayFile;
import com.mobiversa.common.bo.JustSettle;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.Receipt;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.bo.TransactionRequest;
import com.mobiversa.common.bo.TransactionResponse;
import com.mobiversa.payment.connect.MotoPaymentCommunication;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.controller.bean.UpdateInfoEzysettle;
import com.mobiversa.payment.dao.EzysettleDao;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dto.BaseDataImpl;
import com.mobiversa.payment.dto.DataTransferObject;
import com.mobiversa.payment.dto.ESwithdraw;
import com.mobiversa.payment.dto.EarlySettlementModel;
import com.mobiversa.payment.dto.MotoTxnDet;
import com.mobiversa.payment.dto.Pdf;
import com.mobiversa.payment.dto.RegAddMerchant;
import com.mobiversa.payment.dto.ReuseDto;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.SettlementWebService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.CardType;
import com.mobiversa.payment.util.ElasticEmail;
import com.mobiversa.payment.util.ElasticEmailClient;
import com.mobiversa.payment.util.EzysettleUtils;
import com.mobiversa.payment.util.Justsettle;
import com.mobiversa.payment.util.PayeeDetails;
import com.mobiversa.payment.util.Payer;
import com.mobiversa.payment.util.PropertyLoader;
import com.mobiversa.payment.util.ResponseDetails;
import com.mobiversa.payment.util.Utils;
import com.mobiversa.payment.util.forsettlement;
import com.postmark.java.Attachment;
import com.postmark.java.NameValuePair;

@Controller
@RequestMapping(value = MerchantWebTransactionController.URL_BASE)
public class MerchantWebTransactionController extends BaseController {

	@Autowired
	private MobileUserService mobileUserService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private SettlementWebService settlementWebService;

	@Autowired
	private MerchantDao merchantDao;

	@Autowired
	private EzysettleDao ezysettleDao;

	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	private static final String PAYER_HEADER = "PAYMENT DATE,CURRENCY,TOTAL MERCHANT AMOUNT,TOTAL NET AMOUNT,PAYER";
	private static final String PAYEE_HEADER = "MERCHANT NAME,EMAIL,MID,ACCOUNTNO,BANKNAME,SETTLEMENT DATE,MERCHANT MDR,NET AMOUNT, AGENT NAME";

	public static final String URL_BASE = "/transactionweb";
	private static final Logger logger = Logger.getLogger(MerchantWebTransactionController.class);

	private static final Object lock = new Object();

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		// logger.info("Test1 defaultpage");
		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayTransactionSummary(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @PathVariable final int currPage) {
		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		transactionService.getForSettlement(paginationBean, currentMerchant);

		ArrayList<String> dIdSet = new ArrayList<String>();
		List<TerminalDetails> terminalDetails = transactionService.getTerminalDetails(currentMerchant);
		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			tidSet.add(mtid.toString());
			// tidSet.add(t.getDeviceId());
		}

		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());

		}

		model.addAttribute("devIdList", dIdSet);
		model.addAttribute("tidList", tidSet);

		if (paginationBean.getItemList().size() > 0) {

			model.addAttribute("paginationBean", paginationBean);
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {
			model.addAttribute("paginationBean", paginationBean);
			model.addAttribute("devIdList", dIdSet);
			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

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

	@RequestMapping(value = { "/listcard" }, method = RequestMethod.GET)
	public String displayCardTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/cardTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();

		paginationBean.setCurrPage(currPage);
		transactionService.getCardTransForSettlement(paginationBean, currentMerchant, null);

		if (paginationBean.getItemList().size() > 0) {
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

		logger.info("terminalDetailsList: " + terminalDetailsList);
		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetailsList) {
			String mtid = t.getTid();
			tidSet.add(mtid.toString());
			logger.info("tidSet: " + tidSet);
			// tidSet.add(t.getDeviceId());
		}
		ArrayList<String> dIdSet = new ArrayList<String>();
		for (TerminalDetails t : terminalDetailsList) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());

			logger.info("dIdSet: " + dIdSet);
		}
		/*
		 * model.addAttribute("devIdList", dIdSet); model.addAttribute("tidList",
		 * tidSet);
		 */
		model.addAttribute("terminalDetailsList", terminalDetailsList);

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

		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {

				TerminalDetails terminalDetails = transactionService
						.getTerminalDetailsByTid(forSettlement.getTid().toString());

				logger.info("terminalDetails: " + terminalDetails);
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

					logger.info("forSettlementgetAmount(): " + forSettlement.getAmount());
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

	@RequestMapping(value = { "/listgrabpay" }, method = RequestMethod.GET)
	public String displayGrabPayTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("/grabpay");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/grabPayTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		List<String> terminalDetailsListStr = transactionService.getGpayTidbymerchantFK(currentMerchant);

		transactionService.getGrabTransactionForSettlement(paginationBean, terminalDetailsListStr, null, null);

		/*
		 * List<TerminalDetails> terminalDetailsList = transactionService
		 * .getGpayTerminalDetails(currentMerchant);
		 */

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {
				/*
				 * TerminalDetails terminalDetails = transactionService
				 * .getTerminalDetailsByTid(forSettlement.getTid() .toString()); if
				 * (terminalDetails != null) {
				 * 
				 * forSettlement.setMerchantName(terminalDetails .getContactName()); }
				 */

				MobileUser terminalDetails = transactionService
						.getMobileUserByGpayTid(forSettlement.getTid().toString());

				if (terminalDetails != null) {

					forSettlement.setMerchantName(terminalDetails.getMerchant().getBusinessName());
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

				if (forSettlement.getStatus().equals("GPS")) {
					forSettlement.setStatus("GRABPAY COMPLETED");
				} else if (forSettlement.getStatus().equals("GPP")) {
					forSettlement.setStatus("GRABPAY PENDING");
				} else if (forSettlement.getStatus().equals("GRF")) {
					forSettlement.setStatus("GRABPAY REFUND");
				} else if (forSettlement.getStatus().equals("GPC")) {
					forSettlement.setStatus("GRABPAY CANCELLED");
				} else if (forSettlement.getStatus().equals("GPT")) {
					forSettlement.setStatus("GRABPAY SETTLED");
				}

				/*
				 * else if (forSettlement.getStatus().equals("GBC")) {
				 * forSettlement.setStatus("GRABPAY CANCELLED"); }
				 */

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
			// model.addAttribute("terminalDetailsList", terminalDetailsList);
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = { "/motolist" }, method = RequestMethod.GET)
	public String displayMotoTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

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

		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();

		logger.info("MotoMid: " + currentMerchant.getMid().getMotoMid());
		paginationBean.setCurrPage(currPage);

		transactionService.getCardTransForSettlement(paginationBean, currentMerchant, "EZYMOTO");

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().size() > 0) {

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

	@RequestMapping(value = { "/motoLinklist" }, method = RequestMethod.GET)
	public String displayMotoLinkTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		// logger.info("currently logged in as " + myName);
		logger.info("transaction type checking /motoLinklist");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("moto list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/MotoLinkTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();

		logger.info("MotoMid: " + currentMerchant.getMid().getMotoMid());
		paginationBean.setCurrPage(currPage);

		transactionService.getCardTransForSettlement(paginationBean, currentMerchant, "EZYLINK");

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().size() > 0) {

			model.addAttribute("paginationBean", paginationBean);
			// TODO to get tid and device id
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {
			model.addAttribute("paginationBean", paginationBean);
			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = { "/ezywaylist" }, method = RequestMethod.GET)
	public String displayEzyWayTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");

		logger.info("transaction type checking /Ezywaylist");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("ezyway list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzyWayTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();

		logger.info("Ezyway Mid: " + currentMerchant.getMid().getEzywayMid());
		paginationBean.setCurrPage(currPage);

		transactionService.getCardTransForSettlement(paginationBean, currentMerchant, "EZYWAY");

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().size() > 0) {

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

	@RequestMapping(value = { "/cancelPaymentRecplus/{id}" }, method = RequestMethod.GET)
	public String cancelTransactionDetails(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("Transactions Details",
				"merchantweb/transaction/voidpayment/RecplusCancelPaymentConfirm", null);

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
		if (merchant.getMerchantType() != null) {
			txnDet.setHostType(merchant.getMerchantType());
		} else {
			txnDet.setHostType("P");
		}
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

	@RequestMapping(value = { "/cancelRecplusPaymentByMerchant" }, method = RequestMethod.POST)
	public String motoSubmitTransactionRecplus(final Model model, @ModelAttribute("txnDet") MotoTxnDet motoTxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");

		ResponseDetails data = MotoPaymentCommunication.CancelPayment(motoTxnDet);
		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/RecplusCancelPaymentConfirm", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", motoTxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/RecplusVoidDone", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("Transactions Details",
					"merchantweb/transaction/voidpayment/RecplusCancelPaymentConfirm", null);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", motoTxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
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
		if (merchant.getMerchantType() != null) {
			txnDet.setHostType(merchant.getMerchantType());
		} else {
			txnDet.setHostType("P");
		}
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

	@RequestMapping(value = { "/bnplCancelPayment/{id}" }, method = RequestMethod.GET)
	public String cancelPaymentBnplWayTransactionDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("Transactions Details",
				"merchantweb/transaction/voidpayment/CancelPaymentBnpl", null);

		logger.info("Inside bnplCancelPayment method");
		BnplTxnDetails bnp = transactionService.loadBnplDetails(id);

		MotoTxnDet txnDet = new MotoTxnDet();
		txnDet.setTid(bnp.getTid());
		txnDet.setMid(bnp.getMid());
		txnDet.setInvoiceId(bnp.getInvoiceId());
		txnDet.setTrxId(bnp.getMobiTxnId());
		txnDet.setApprCode(bnp.getBnplTxnId());

		String rd = null;
		if (bnp.getDate() != null) {

			try {
				rd = new SimpleDateFormat("dd/MM/yyyy").format(new SimpleDateFormat("yyyyMMdd").parse(bnp.getDate()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		String rt = null;
		if (bnp.getTime() != null) {
			try {
				rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(bnp.getTime()));

			} catch (ParseException e) {
			}
		}
		txnDet.setExpectedDate(rd + " " + rt);
		double amount = 0;
		amount = Double.parseDouble(bnp.getAmount()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		bnp.setAmount(output);
		txnDet.setAmount(bnp.getAmount());

		logger.info("bnpl MID " + bnp.getMid());
		logger.info("bnpl TID " + bnp.getTid());
		logger.info("bnpl Amount " + bnp.getAmount());
		logger.info("bnpl Invoice Id " + bnp.getInvoiceId());
		logger.info("bnpl Approval Code " + bnp.getBnplTxnId());
		logger.info("bnpl Trxn Id " + bnp.getMobiTxnId());

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", txnDet);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/motoCancelPayment/{id}" }, method = RequestMethod.GET)
	public String motoCancelPayment(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("Transactions Details",
				"merchantweb/transaction/voidpayment/MotoCancelPaymentConfirm", null);

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

	@RequestMapping(value = { "/ezylinkCancelPayment/{id}" }, method = RequestMethod.GET)
	public String ezylinkCancelPayment(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("Transactions Details",
				"merchantweb/transaction/voidpayment/MotoCancelPaymentConfirm", null);

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

	// BNPL VOID 13-12-22

	@RequestMapping(value = { "/cancelPaymentByMerchantBnpl" }, method = RequestMethod.POST)
	public String bnplVoidSubmitTransaction(final Model model, @ModelAttribute("txnDet") MotoTxnDet motoTxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");
		logger.info("User clicked the void button and cancelPaymentByMerchantBnpl controller is activated");

		ResponseDetails data = MotoPaymentCommunication.CancelPaymentBnpl(motoTxnDet);
		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/CancelPaymentBnpl", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", motoTxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
				logger.info("responseData is " + data.getResponseDescription());
			}

			else if (data.getResponseCode().equals("0000")) {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/BnplVoidDone", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("responseData", data.getResponseDescription());
				logger.info("responseData is " + data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("Transactions Details",
					"merchantweb/transaction/voidpayment/CancelPaymentBnpl", null);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", motoTxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			logger.info("responseData is " + data.getResponseDescription());

		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/MotoCancelPaymentByMerchant" }, method = RequestMethod.POST)
	public String MotoCancelPaymentByMerchant(final Model model, @ModelAttribute("txnDet") MotoTxnDet motoTxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");

		ResponseDetails data = MotoPaymentCommunication.CancelPayment(motoTxnDet);
		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/MotoCancelPaymentConfirm", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", motoTxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/MotoVoidPaymentDone", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("Transactions Details",
					"merchantweb/transaction/voidpayment/MotoCancelPaymentConfirm", null);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", motoTxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/EzylinkCancelPaymentByMerchant" }, method = RequestMethod.POST)
	public String EzylinkCancelPaymentByMerchant(final Model model, @ModelAttribute("txnDet") MotoTxnDet motoTxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");

		ResponseDetails data = MotoPaymentCommunication.CancelPayment(motoTxnDet);
		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/EzylinkCancelPaymentConfirm", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", motoTxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/EzylinkVoidPaymentDone", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("Transactions Details",
					"merchantweb/transaction/voidpayment/EzylinkCancelPaymentConfirm", null);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", motoTxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/cancelPaymentEzyPOD/{id}" }, method = RequestMethod.GET)
	public String cancelPaymentEzyPODTransactionDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("Transactions Details",
				"merchantweb/transaction/voidpayment/CancelEzypodPaymentConfirm", null);

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
		if (merchant.getMerchantType() != null) {
			txnDet.setHostType(merchant.getMerchantType());
		} else {
			txnDet.setHostType("P");
		}
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

	@RequestMapping(value = { "/cancelEzypodPaymentByMerchant" }, method = RequestMethod.POST)
	public String ezypodSubmitTransaction(final Model model, @ModelAttribute("txnDet") MotoTxnDet motoTxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");

		ResponseDetails data = MotoPaymentCommunication.CancelPayment(motoTxnDet);
		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/CancelEzypodPaymentConfirm", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", motoTxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("Transactions Details",
						"merchantweb/transaction/voidpayment/EzypodVoidDone", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("Transactions Details",
					"merchantweb/transaction/voidpayment/CancelEzypodPaymentConfirm", null);
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

		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();

		logger.info("EZYREC Mid: " + currentMerchant.getMid().getEzyrecMid());

		paginationBean.setCurrPage(currPage);

		transactionService.getCardTransForSettlement(paginationBean, currentMerchant, "EZYREC");

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().size() > 0) {

			model.addAttribute("paginationBean", paginationBean);
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

	@RequestMapping(value = { "/ezypodlist" }, method = RequestMethod.GET)
	public String displayEzyPODTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String mid_ezyPOD = null;
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		// logger.info("currently logged in as " + myName);
		logger.info("transaction type checking /EzyPODlist");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("ezypod list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzyPODTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();

		logger.info("EZYREC Mid for ezypod: " + currentMerchant.getMid().getEzyrecMid());

		paginationBean.setCurrPage(currPage);

		TerminalDetails termDetails = merchantService.loadTerminalDetailsByMid(currentMerchant.getMid().getEzyrecMid());

		if ((termDetails.getDeviceType() == "EZYPOD") || (termDetails.getDeviceType().equals("EZYPOD"))) {

			mid_ezyPOD = currentMerchant.getMid().getEzyrecMid();

		} else {
			mid_ezyPOD = currentMerchant.getMid().getEzyrecMid();
		}

		transactionService.getCardTransForSettlement(paginationBean, currentMerchant, "EZYREC");

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().size() > 0) {
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

	@RequestMapping(value = { "/ezyrecpluslist" }, method = RequestMethod.GET)
	public String displayEzyRecPlusTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		// logger.info("currently logged in as " + myName);
		logger.info("transaction type checking /Ezyrecpluslist");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("ezyrec list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzyRecPlusTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();

		logger.info("EZYREC Mid: " + currentMerchant.getMid().getEzyrecMid());

		paginationBean.setCurrPage(currPage);
		/*
		 * transactionService.getTransactionForSettlement(paginationBean,
		 * currentMerchant,"RECPLUS");
		 */

		transactionService.getCardTransForSettlement(paginationBean, currentMerchant, "RECPLUS");

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().size() > 0) {

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

		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();

		logger.info("ezypass mid: " + currentMerchant.getMid().getEzypassMid());
		paginationBean.setCurrPage(currPage);
		List<TerminalDetails> terminalDetailsList = transactionService
				.getTerminalDetails(currentMerchant.getMid().getEzypassMid());

		for (TerminalDetails t : terminalDetailsList) {
			logger.info(t.getTid() + " " + t.getMerchantId());
		}
		model.addAttribute("terminalDetailsList", terminalDetailsList);
		/*
		 * transactionService.getEzypassTransForSettlement(paginationBean,
		 * currentMerchant);
		 */
		/*
		 * transactionService.getTransactionForSettlement(paginationBean,
		 * currentMerchant,"EZYPASS");
		 */
		transactionService.getCardTransForSettlement(paginationBean, currentMerchant, "EZYPASS");

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
	// transactionService.searchMobileUserTransactions(i, paginationBean);

	// model.addAttribute("tidSet", tidSet);
	// model.addAttribute("paginationBean", paginationBean);

	/*
	 * return TEMPLATE_MERCHANT; }
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String displayTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("tid") final String tid, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status, HttpServletRequest request,

			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");
		logger.info("Search All Transaction By Merchant " + myName);
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		Date frdate = new Date(fromDate);
		int fromday = frdate.getDate();
		int frommon = frdate.getMonth() + 1;
		int fromyear = frdate.getYear();
		int currentFrYear = fromyear + 1900;
		String frmon = String.format("%02d", frommon);
		String frday = String.format("%02d", fromday);
		// String fromdateString = frday + '/' + frmon + '/' +
		// String.valueOf(currentFrYear);
		String fromDate1 = String.valueOf(currentFrYear) + '-' + frmon + '-' + frday;

		Date todate = new Date(toDate);
		int today = todate.getDate();
		int tomon = todate.getMonth() + 1;
		int toyear = todate.getYear();
		int currentToYear = toyear + 1900;
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

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");

		transactionService.searchForSettlement(fromDate1, toDate1, tid, status, paginationBean, currentMerchant);

		// logger.info("from date:" + dat);
		// logger.info("to date:" + dat1);
		model.addAttribute("fromDate", fromDate1);

		model.addAttribute("toDate", toDate1);
		model.addAttribute("tid", tid);

		model.addAttribute("status", status);
		/*
		 * logger.info("test transaction :::" + devId); model.addAttribute("devId",
		 * devId);
		 */
		// logger.info("test transaction :::"+ paginationBean.getItemList()
		// ==null);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		// logger.info("test data "+ paginationBean.getItemList());
		/*
		 * for (ForSettlement forSettlement : paginationBean.getItemList()) {
		 * TerminalDetails terminalDetails = transactionService
		 * .getTerminalDetailsByTid(forSettlement.getTid().toString()); if
		 * (terminalDetails != null) { // logger.info("terminal details contact Name:" +
		 * // terminalDetails.getContactName());
		 * forSettlement.setMerchantName(terminalDetails.getContactName()); }
		 * 
		 * if (forSettlement.getStatus().equals("CT")) {
		 * forSettlement.setStatus("CASH SALE"); } if
		 * (forSettlement.getStatus().equals("CV")) {
		 * forSettlement.setStatus("CASH CANCELLED"); } if
		 * (forSettlement.getStatus().equals("S")) { forSettlement.setStatus("SETTLED");
		 * } if (forSettlement.getStatus().equals("P")) {
		 * forSettlement.setStatus("PENDING"); } if
		 * (forSettlement.getStatus().equals("A")) {
		 * forSettlement.setStatus("NOT SETTLED"); } if
		 * (forSettlement.getStatus().equals("C")) {
		 * forSettlement.setStatus("CANCELLED"); } if
		 * (forSettlement.getStatus().equals("R")) {
		 * forSettlement.setStatus("REVERSAL"); } if
		 * (forSettlement.getStatus().equals("BP")) {
		 * forSettlement.setStatus("BOOST PAYMENT"); } if
		 * (forSettlement.getStatus().equals("BPC")) {
		 * forSettlement.setStatus("BOOST CANCELLED"); } if
		 * (forSettlement.getStatus().equals("BPS")) {
		 * forSettlement.setStatus("BOOST SETTLED"); } if
		 * (forSettlement.getStatus().equals("BPA")) {
		 * forSettlement.setStatus("BOOST PAYMENT"); }
		 * 
		 * if(forSettlement.getStatus()=="BOOST PAYMENT" ||
		 * forSettlement.getStatus()=="BOOST SETTLED" ||
		 * forSettlement.getStatus()=="BOOST CANCELLED" ||
		 * forSettlement.getStatus().equals("BOOST PAYMENT") ||
		 * forSettlement.getStatus().equals("BOOST CANCELLED") ||
		 * forSettlement.getStatus().equals("BOOST SETTLED")) {
		 * logger.info("inside check boost status"); if (forSettlement.getAmount() !=
		 * null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()); //
		 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
		 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
		 * forSettlement.setAmount(output); }
		 * 
		 * if (forSettlement.getDate() != null && forSettlement.getTime() != null) { try
		 * { // String sd=forSettlement.getDate()+new //
		 * SimpleDateFormat("y").format(new java.util.Date()); String sd =
		 * forSettlement.getTimeStamp(); String rd = new SimpleDateFormat("dd-MMM-yyyy")
		 * .format(new SimpleDateFormat("yyyy-MM-dd") .parse(sd)); String rt = new
		 * SimpleDateFormat("HH:mm:ss") .format(new
		 * SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss") .parse(forSettlement.getTime()));
		 * forSettlement.setDate(rd); forSettlement.setTime(rt); } catch (ParseException
		 * e) { }
		 * 
		 * }
		 * 
		 * } else { if (forSettlement.getAmount() != null) { double amount = 0; amount =
		 * Double.parseDouble(forSettlement.getAmount()) / 100; //
		 * forSettlement.setAmount(amount+"0"); String pattern = "#,##0.00";
		 * DecimalFormat myFormatter = new DecimalFormat(pattern); String output =
		 * myFormatter.format(amount); // System.out.println(" Amount :"+output);
		 * forSettlement.setAmount(output);
		 * 
		 * } if (forSettlement.getDate() != null && forSettlement.getTime() != null) {
		 * try { // String sd=forSettlement.getDate()+new //
		 * SimpleDateFormat("y").format(new java.util.Date()); String sd =
		 * forSettlement.getTimeStamp(); String rd = new SimpleDateFormat("dd-MMM-yyyy")
		 * .format(new SimpleDateFormat("yyyy-MM-dd") .parse(sd)); String rt = new
		 * SimpleDateFormat("HH:mm:ss") .format(new SimpleDateFormat("HHmmss")
		 * .parse(forSettlement.getTime())); forSettlement.setDate(rd);
		 * forSettlement.setTime(rt); } catch (ParseException e) { }
		 * 
		 * } }
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * }
		 */

		/*
		 * String myName = principal.getName(); logger.info("currently logged in as " +
		 * myName); Merchant currentMerchant = merchantService.loadMerchant(myName);
		 */
		List<TerminalDetails> terminalDetails = transactionService.getTerminalDetails(currentMerchant);

		List<TerminalDetails> terminalDetailsList = transactionService.getAllTid(currentMerchant);

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
		model.addAttribute("devIdList", dIdSet);

		model.addAttribute("tidList", tidSet);
		model.addAttribute("terminalDetailsList", terminalDetailsList);
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

		logger.info("from date:" + dat + dat1);

		/*
		 * String dat = null; String dat1 = null;
		 * 
		 * if (!(fromDate == null || fromDate.equals("")) && !(toDate == null ||
		 * toDate.equals(""))) {
		 * 
		 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); try { dat =
		 * dateFormat.format(new SimpleDateFormat("dd/MM/yyyy") .parse(fromDate)); }
		 * catch (ParseException e) {
		 * 
		 * e.printStackTrace(); } SimpleDateFormat dateFormat1 = new
		 * SimpleDateFormat("yyyy-MM-dd"); try { dat1 = dateFormat1.format(new
		 * SimpleDateFormat("dd/MM/yyyy") .parse(toDate)); } catch (ParseException e) {
		 * 
		 * e.printStackTrace(); } }
		 */
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

	@RequestMapping(value = "/searchcardtrans", method = RequestMethod.GET)
	public String displaycardTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("paydee search ezywire ");

		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/cardTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");
		transactionService.searchCardForSettlement(fromdate, todate, paginationBean, currentMerchant);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		logger.info("No of Records " + paginationBean.getItemList().size());

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

		logger.info("from date:" + dat + dat1);

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
		 * SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd"); try { dat1
		 * = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy") .parse(toDate)); }
		 * catch (ParseException e) { e.printStackTrace(); }
		 * 
		 * }
		 */
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

	@RequestMapping(value = "/searchmoto", method = RequestMethod.GET)
	public String displayMotoTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("search paydee moto ");
		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/MotoTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchForSettlementMoto(fromdate, todate, paginationBean, currentMerchant);

		// transactionService.getCardTransactionForSettlement(paginationBean,
		// currentMerchant, "MOTO", fromDate, toDate,status);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = "/searchLinkmoto", method = RequestMethod.GET)
	public String displayMotoLinkTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search paydee ezylink");

		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/MotoLinkTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();
		paginationBean.setCurrPage(currPage);
		transactionService.searchForSettlementMoto(fromdate, todate, paginationBean, currentMerchant);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

//	//search grab pay 
	@RequestMapping(value = "/searchgrabpay", method = RequestMethod.POST)
	public String displaygrabTransactionSearch(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("/displaygrabTransactionSearch");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		Date frdate = new Date(fromDate);
		int fromday = frdate.getDate();
		int frommon = frdate.getMonth() + 1;
		int fromyear = frdate.getYear();
		int currentFrYear = fromyear + 1900;
		String frmon = String.format("%02d", frommon);
		String frday = String.format("%02d", fromday);
		String dat = frday + '/' + frmon + '/' + String.valueOf(currentFrYear);
		// String dat = String.valueOf(currentFrYear)+'-'+frmon+'-'+frday;

		Date todate = new Date(toDate);
		int today = todate.getDate();
		int tomon = todate.getMonth() + 1;
		int toyear = todate.getYear();
		int currentToYear = toyear + 1900;
		String tmon = String.format("%02d", tomon);
		String tday = String.format("%02d", today);
		String dat1 = tday + '/' + tmon + '/' + String.valueOf(currentToYear);
		// String dat1 = String.valueOf(currentToYear)+'-'+tmon+'-'+tday;

		logger.info("from date:" + dat + " to date:" + dat1);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/grabPayTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		List<String> terminalDetailsList = transactionService.getGpayTidbymerchantFK(currentMerchant);

		transactionService.getGrabTransactionForSettlement(paginationBean, terminalDetailsList, dat, dat1);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {
				/*
				 * TerminalDetails terminalDetails = transactionService
				 * .getTerminalDetailsByTid(forSettlement.getTid() .toString()); if
				 * (terminalDetails != null) {
				 * 
				 * forSettlement.setMerchantName(terminalDetails .getContactName()); }
				 */

				MobileUser terminalDetails = transactionService
						.getMobileUserByGpayTid(forSettlement.getTid().toString());

				if (terminalDetails != null) {

					forSettlement.setMerchantName(terminalDetails.getMerchant().getBusinessName());
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

				if (forSettlement.getStatus().equals("GPS")) {
					forSettlement.setStatus("GRABPAY COMPLETED");
				} else if (forSettlement.getStatus().equals("GPP")) {
					forSettlement.setStatus("GRABPAY PENDING");
				} else if (forSettlement.getStatus().equals("GRF")) {
					forSettlement.setStatus("GRABPAY REFUND");
				} else if (forSettlement.getStatus().equals("GPC")) {
					forSettlement.setStatus("GRABPAY CANCELLED");
				} else if (forSettlement.getStatus().equals("GPT")) {
					forSettlement.setStatus("GRABPAY SETTLED");
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
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = "/searchezyway", method = RequestMethod.GET)
	public String searchEzyWayTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("search paydee ezyway");
		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzyWayTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();
		paginationBean.setCurrPage(currPage);
		logger.info("test transaction " + fromdate);
		transactionService.searchForSettlementEzyWay(fromdate, todate, paginationBean, currentMerchant);

		logger.info("No of Records: " + paginationBean.getItemList().size());
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

	@RequestMapping(value = "/searchezyrec", method = RequestMethod.GET)
	public String searchEzyrecTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzyRecTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();
		paginationBean.setCurrPage(currPage);
		logger.info("test transaction " + fromDate);
		transactionService.searchForSettlementEzyRec(fromDate, toDate, paginationBean, currentMerchant);

		logger.info("No of Records: " + paginationBean.getItemList().size());
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

	@RequestMapping(value = "/searchezypod", method = RequestMethod.GET)
	public String searchEzypodTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzyPODTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();
		paginationBean.setCurrPage(currPage);
		logger.info("test transaction " + fromDate);
		transactionService.searchForSettlementEzyRec(fromDate, toDate, paginationBean, currentMerchant);

		logger.info("No of Records: " + paginationBean.getItemList().size());
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

	@RequestMapping(value = "/searchezyrecplus", method = RequestMethod.GET)
	public String searchEzyrecplusTransactionSearchByTid(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("searchezyrecplus" + fromDate + toDate);
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/EzyRecPlusTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("searchForSettlementEzyRecplus");
		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();
		paginationBean.setCurrPage(currPage);
		logger.info("test transaction " + fromDate);
		transactionService.searchForSettlementEzyRecplus(fromDate, toDate, paginationBean, currentMerchant);

		logger.info("No of Records: " + paginationBean.getItemList().size());
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
		 * //String txn_Type = "MOTO"; //logger.info("status: " + status + " txn_type: "
		 * + txn_Type);
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
		 * SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd"); try { dat1
		 * = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy") .parse(toDate)); }
		 * catch (ParseException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * }
		 */

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

	@RequestMapping(value = { "/details/{id}" }, method = RequestMethod.GET)
	public String displayTransactionDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// Merchant merchant = merchantService.loadMerchant(principal.getName());
		Merchant merchant = merchantService.loadMerchant(myName);

		logger.info("Merchant Receipt logged by" + ":" + principal.getName());
		logger.info("trxID: " + id);
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
				dt.setMerchantState(merchant.getState());
				dt.setMerchantContNo(merchant.getBusinessContactNumber());
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
			TerminalDetails termDetails = merchantService.loadTerminalDetailsByMid(merchant.getMid().getEzyrecMid());
			Receipt a = transactionService.getReceiptSignature(id);

			DataTransferObject dt = new DataTransferObject();

			dt.setHostType(settle.getHostType());
			if (a != null) {
				if (a.getSignature() != null) {
					String signdata = new String(a.getSignature());

					dt.setSign(signdata);
				}
			}

			String txn = settle.getStatus();
			String pinEntry = settle.getPinEntry();
			dt.setPinEntry(pinEntry);

			if (settle.getHostType() != null) {
				if (txn.equals("S") || txn.equals("A")) {
					logger.info("txntype: " + settle.getTxnType());
					if (settle.getTxnType().equals("MOTO")) {
						// dt.setTxnType("UMOBILE EZYMOTO SALE");
						dt.setTxnType("EZYMOTO SALE");
					} else if (settle.getTxnType().equals("EZYWAY")) {
						// dt.setTxnType("UMOBILE EZYWAY SALE");
						dt.setTxnType("EZYWAY SALE");
					} else if (settle.getTxnType().equals("EZYREC") || settle.getTxnType().equals("RECURRING")) {
						// dt.setTxnType("UMOBILE EZYREC SALE");
						dt.setTxnType("EZYREC SALE");
					} else if (settle.getTxnType().equals("EZYPASS")) {
						// dt.setTxnType("UMOBILE EZYPASS SALE");
						dt.setTxnType("EZYPASS SALE");
					} else {
						// dt.setTxnType("UMOBILE SALE");
						dt.setTxnType("SALE");
					}

				} else {

					if (settle.getTxnType().equals("MOTO")) {
						// dt.setTxnType("UMOBILE EZYMOTO VOID");
						dt.setTxnType("EZYMOTO VOID");
					} else if (settle.getTxnType().equals("EZYWAY")) {
						// dt.setTxnType("UMOBILE EZYWAY VOID");
						dt.setTxnType("EZYWAY VOID");
					} else if (settle.getTxnType().equals("EZYREC") || settle.getTxnType().equals("RECURRING")) {
						// dt.setTxnType("UMOBILE EZYREC VOID");
						dt.setTxnType("EZYREC VOID");
					} else if (settle.getTxnType().equals("EZYPASS")) {
						// dt.setTxnType("UMOBILE EZYPASS VOID");
						dt.setTxnType("EZYPASS VOID");
					} else {
						// dt.setTxnType("UMOBILE VOID");
						dt.setTxnType("VOID");
					}

				}
			} else {
				if (txn.equals("S") || txn.equals("A")) {
					logger.info("txntype: " + settle.getTxnType());
					if (settle.getTxnType().equals("MOTO")) {
						dt.setTxnType("EZYMOTO SALE");
					} else if (settle.getTxnType().equals("EZYLINK")) {
						dt.setTxnType("EZYLINK SALE");
					} else if (settle.getTxnType().equals("EZYWAY")) {
						dt.setTxnType("EZYWAY SALE");
					} else if (settle.getTxnType().equals("EZYREC") && ((termDetails.getDeviceType() == "EZYPOD")
							|| (termDetails.getDeviceType().equals("EZYPOD")))) {
						dt.setTxnType("EZYPOD SALE");
					} else if (settle.getTxnType().equals("EZYREC") || settle.getTxnType().equals("RECURRING")) {
						dt.setTxnType("EZYREC SALE");
					} else if (settle.getTxnType().equals("EZYPASS")) {
						dt.setTxnType("EZYPASS SALE");
					} else if (settle.getTxnType().equals("RECPLUS")) {
						dt.setTxnType("EZYREC+ SALE");
					} else if (settle.getTxnType().equals("GRABPAY")) {
						dt.setTxnType("GRABPAY SALE");
					} else {
						dt.setTxnType("SALE");
					}

				} else {

					if (settle.getTxnType().equals("MOTO")) {
						dt.setTxnType("EZYMOTO VOID");
					} else if (settle.getTxnType().equals("EZYLINK")) {
						dt.setTxnType("EZYLINK VOID");
					} else if (settle.getTxnType().equals("EZYWAY")) {
						dt.setTxnType("EZYWAY VOID");
					} else if (settle.getTxnType().equals("EZYREC") && ((termDetails.getDeviceType() == "EZYPOD")
							|| (termDetails.getDeviceType().equals("EZYPOD")))) {
						dt.setTxnType("EZYPOD VOID");
					} else if (settle.getTxnType().equals("EZYREC") || settle.getTxnType().equals("RECURRING")) {
						dt.setTxnType("EZYREC VOID");
					} else if (settle.getTxnType().equals("EZYPASS")) {
						dt.setTxnType("EZYPASS VOID");
					} else if (settle.getTxnType().equals("RECPLUS")) {
						dt.setTxnType("EZYREC+ VOID");
					} else if (settle.getTxnType().equals("GRABPAY")) {
						dt.setTxnType("GRABPAY VOID");
					} else {
						dt.setTxnType("VOID");
					}

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
				// dt.setAmount(trRequest.getAmount());
				dt.setAmount(settle.getAmount());
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
				logger.info("Amount For receipt :" + settle.getAmount());
				if (settle.getAmount() != null) {
					amount = Double.parseDouble(settle.getAmount()) / 100;
				}
				double tips = 0;
				double total = amount;
				/*
				 * if (trRequest.getAdditionalAmount() != null) { tips =
				 * Double.parseDouble(trRequest.getAdditionalAmount()) / 100; amount = amount -
				 * tips; total = amount + tips;
				 * 
				 * }
				 */
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
			return "merchantweb/transaction/receipt_v0.2";
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
		logger.info("from date:" + dat + dat1);

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
		 * logger.info("check from date:" + fromDate); logger.info("check from date:" +
		 * dat);
		 * 
		 * 
		 * SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd"); try { dat1
		 * = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy") .parse(toDate)); }
		 * catch (ParseException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * System.out.println("dat1:" + dat1); }
		 */
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

	@RequestMapping(value = "/motoExport", method = RequestMethod.GET)
	public ModelAndView getExportMoto(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("export paydee moto");
		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("current Merchant: " + currentMerchant.getMid().getMid());

		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchForSettlementMoto(fromdate, todate, paginationBean, currentMerchant);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<forsettlement> list1 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {
			return new ModelAndView("PaydeeTxnSuccess", "TxnList", list1);
		} else {
			return new ModelAndView("txnMerchantMotoPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = "/ezywayExport", method = RequestMethod.GET)
	public ModelAndView getExportEzyway(final Model model, final java.security.Principal principal,

			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());

		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchForSettlementEzyWay(fromDate, toDate, paginationBean, currentMerchant);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<forsettlement> list1 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {
			return new ModelAndView("PaydeeTxnSuccess", "TxnList", list1);
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

	@RequestMapping(value = "/ezyrecExport", method = RequestMethod.GET)
	public ModelAndView getExportEzyrec(final Model model, final java.security.Principal principal,

			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getMid());

		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();
		paginationBean.setCurrPage(currPage);
		transactionService.searchForSettlementEzyRec(fromDate, toDate, paginationBean, currentMerchant);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<forsettlement> list1 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {
			return new ModelAndView("PaydeeTxnSuccess", "TxnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = "/ezypodExport", method = RequestMethod.GET)
	public ModelAndView getExportEzypod(final Model model, final java.security.Principal principal,

			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getMid());

		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchForSettlementEzyRec(fromDate, toDate, paginationBean, currentMerchant);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<forsettlement> list1 = paginationBean.getItemList();

		// System.out.println("display list:" + list1);
		// System.out.println("Export Type:" + export);

		if (!(export.equals("PDF"))) {
			return new ModelAndView("PaydeeTxnSuccess", "TxnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = "/ezyrecplusExport", method = RequestMethod.GET)
	public ModelAndView getExportEzyrecplus(final Model model, final java.security.Principal principal,

			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getMid());

		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");
		transactionService.searchForSettlementEzyRecplus(fromDate, toDate, paginationBean, currentMerchant);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<forsettlement> list1 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {
			return new ModelAndView("PaydeeTxnSuccess", "TxnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	// grabpay export
	@RequestMapping(value = "/grabpayExport", method = RequestMethod.POST)
	public ModelAndView getExportGrabpay(final Model model, final java.security.Principal principal,

			// @RequestParam("tid") final String tid,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			// @RequestParam("status") String status,
			// @RequestParam("devId") String devId,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getMid());

		Date frdate = new Date(fromDate);
		int fromday = frdate.getDate();
		int frommon = frdate.getMonth() + 1;
		int fromyear = frdate.getYear();
		int currentFrYear = fromyear + 1900;
		String frmon = String.format("%02d", frommon);
		String frday = String.format("%02d", fromday);
		String dat = frday + '/' + frmon + '/' + String.valueOf(currentFrYear);
		// String dat = String.valueOf(currentFrYear)+'-'+frmon+'-'+frday;

		Date todate = new Date(toDate);
		int today = todate.getDate();
		int tomon = todate.getMonth() + 1;
		int toyear = todate.getYear();
		int currentToYear = toyear + 1900;
		String tmon = String.format("%02d", tomon);
		String tday = String.format("%02d", today);
		String dat1 = tday + '/' + tmon + '/' + String.valueOf(currentToYear);
		// String dat1 = String.valueOf(currentToYear)+'-'+tmon+'-'+tday;

		logger.info("from date:" + dat + " to date:" + dat1);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		List<String> terminalDetailsList = transactionService.getGpayTidbymerchantFK(currentMerchant);

		transactionService.getGrabTransactionForSettlement(paginationBean, terminalDetailsList, dat, dat1);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {
				/*
				 * TerminalDetails terminalDetails = transactionService
				 * .getTerminalDetailsByTid(forSettlement.getTid() .toString()); if
				 * (terminalDetails != null) {
				 * 
				 * forSettlement.setMerchantName(terminalDetails .getContactName()); }
				 */

				MobileUser terminalDetails = transactionService
						.getMobileUserByGpayTid(forSettlement.getTid().toString());

				if (terminalDetails != null) {

					forSettlement.setMerchantName(terminalDetails.getMerchant().getBusinessName());
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

				if (forSettlement.getStatus().equals("GPS")) {
					forSettlement.setStatus("GRABPAY COMPLETED");
				} else if (forSettlement.getStatus().equals("GPP")) {
					forSettlement.setStatus("GRABPAY PENDING");
				} else if (forSettlement.getStatus().equals("GRF")) {
					forSettlement.setStatus("GRABPAY REFUND");
				} else if (forSettlement.getStatus().equals("GPC")) {
					forSettlement.setStatus("GRABPAY CANCELLED");
				} else if (forSettlement.getStatus().equals("GPT")) {
					forSettlement.setStatus("GRABPAY SETTLED");
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
		}
		List<ForSettlement> list1 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListExcel1", "txnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = "/exportCardTrans", method = RequestMethod.GET)
	public ModelAndView getExportCardTrans(final Model model, final java.security.Principal principal,

			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("ezywire export Paydee ");

		String fromdate = HtmlUtils.htmlEscape(fromDate);
		String todate = HtmlUtils.htmlEscape(toDate);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("the merchant obj based on currently logged in user is: " + currentMerchant);

		PaginationBean<forsettlement> paginationBean = new PaginationBean<forsettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchCardForSettlement(fromdate, todate, paginationBean, currentMerchant);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<forsettlement> list1 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {
			return new ModelAndView("PaydeeTxnSuccess", "TxnList", list1);
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

		logger.info("from date:" + dat + dat1);

		/*
		 * String dat = null; String dat1 = null;
		 * 
		 * if (!(fromDate == null || fromDate.equals("")) && !(toDate == null ||
		 * toDate.equals(""))) {
		 * 
		 * SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); try { dat =
		 * dateFormat.format(new SimpleDateFormat("dd/MM/yyyy") .parse(fromDate)); }
		 * catch (ParseException e) {
		 * 
		 * e.printStackTrace(); }
		 * 
		 * logger.info("check from date:" + fromDate); logger.info("check from date:" +
		 * dat);
		 * 
		 * SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd"); try { dat1
		 * = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy") .parse(toDate)); }
		 * catch (ParseException e) {
		 * 
		 * e.printStackTrace(); } System.out.println("dat1:" + dat1); }
		 */
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
			@RequestParam("devId") String devId, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": &
		// :"+tid+":"+status);
		// PageBean pageBean=new PageBean("Transactions Details",
		// "transaction/receipt", null);
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());

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
		 * e.printStackTrace(); } logger.info("check from date:" +
		 * fromDate+" check from date:" + dat);
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
		transactionService.searchForSettlement(dat, dat1, tid, status, paginationBean, currentMerchant);

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
		/*
		 * model.addAttribute("devIdList", dIdSet);
		 * 
		 * model.addAttribute("tidList", tidSet); model.addAttribute("paginationBean",
		 * paginationBean);
		 * 
		 * return TEMPLATE_MERCHANT;
		 */

		List<ForSettlement> list1 = paginationBean.getItemList();

		// System.out.println("display list:" + list1);
		// System.out.println("Export Type:" + export);

		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListExcel1", "txnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = { "/listFpxTxn" }, method = RequestMethod.GET)
	public String displayFpxTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/FpxTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);
		transactionService.listMerchantFPXTransactionByMid(paginationBean, currentMerchant, null, null);

		logger.info("No of Records: " + paginationBean.getItemList().size());

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

		if (paginationBean.getItemList().size() > 0) {

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

	@RequestMapping(value = { "/searchFpxTxnSummary" }, method = RequestMethod.GET)
	public String searchFpxTxnSummary(final Model model, @RequestParam final String fromDate,
			@RequestParam final String toDate, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("searchFpxTxnSummary admin" + fromDate + "::" + toDate);

		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/FpxTransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		transactionService.listMerchantFPXTransactionByMidsearch(paginationBean, currentMerchant, fromDate, toDate);

		logger.info("No of Records: " + paginationBean.getItemList().size());

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

		if (paginationBean.getItemList().size() > 0) {

			model.addAttribute("paginationBean", paginationBean);
			// TODO to get tid and device id
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {

			logger.info("No Data");
			model.addAttribute("paginationBean", paginationBean);
			/* model.addAttribute("devIdList", dIdSet); */
			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = { "/exportFpxTxnSummary" }, method = RequestMethod.GET)
	public ModelAndView exportFpxTxnSummary(final Model model, @RequestParam final String fromDate,
			@RequestParam final String toDate, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam(required = false) String export, HttpServletRequest request,
			final java.security.Principal principal) {
		logger.info("searchFpxTxnSummary admin" + fromDate + "::" + toDate);
		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		transactionService.listMerchantFPXTransactionByMidsearch(paginationBean, currentMerchant, fromDate, toDate);

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

		List<FpxTransaction> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("fpxMerchantTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("fpxMerchantTxnPdf", "txnList", list);
		}

	}

	@RequestMapping(value = { "/bizappSettlementSummary" }, method = RequestMethod.GET)
	public String bizappSettlement(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionBizappSettSummary",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<BizAppSettlement> paginationBean = new PaginationBean<BizAppSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listbizappSettlementByMerchant(paginationBean, currentMerchant, null);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

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

	@RequestMapping(value = { "/searchBizappSettlementSummary" }, method = RequestMethod.GET)
	public String searchBizappTxnSummary(final Model model, @RequestParam final String settlementDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("searchBizappTxnSummary admin" + settlementDate);

		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionBizappSettSummary",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<BizAppSettlement> paginationBean = new PaginationBean<BizAppSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listbizappSettlementByMerchant(paginationBean, currentMerchant, settlementDate);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			model.addAttribute("paginationBean", paginationBean);
			// TODO to get tid and device id
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {

			logger.info("No Data");
			model.addAttribute("paginationBean", paginationBean);
			/* model.addAttribute("devIdList", dIdSet); */
			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = { "/exportBizappTxnSummary" }, method = RequestMethod.GET)
	public ModelAndView exportBizappTxnSummary(final Model model, @RequestParam final String settledDate,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam(required = false) String export, HttpServletRequest request,
			final java.security.Principal principal) {
		logger.info("exportBizappTxnSummary admin" + settledDate);
		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<BizAppSettlement> paginationBean = new PaginationBean<BizAppSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listbizappSettlementByMerchant(paginationBean, currentMerchant, settledDate);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		List<BizAppSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("bizappMerchantTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("bizappMerchantTxnPdf", "txnList", list);
		}

	}

	@RequestMapping(value = { "/listFpxSettlementList" }, method = RequestMethod.GET)
	public String displayFpxSettlementTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/FpxSettlementList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		transactionService.listMerchantFPXSettlementByMid(paginationBean, currentMerchant, null, null);

		logger.info("No of Records: " + paginationBean.getItemList().size());

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

		if (paginationBean.getItemList().size() > 0) {

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

	@RequestMapping(value = { "/searchFpxSettlementList" }, method = RequestMethod.GET)
	public String searchFpxSettlemList(final Model model, @RequestParam final String fromDate,
			@RequestParam final String toDate, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("searchFpxTxnSummary admin" + fromDate + "::" + toDate);

		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/FpxSettlementList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		transactionService.listMerchantFPXSettlementByMid(paginationBean, currentMerchant, fromDate, toDate);

		logger.info("No of Records: " + paginationBean.getItemList().size());

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

		if (paginationBean.getItemList().size() > 0) {

			model.addAttribute("paginationBean", paginationBean);
			// TODO to get tid and device id
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {

			logger.info("No Data");
			model.addAttribute("paginationBean", paginationBean);
			/* model.addAttribute("devIdList", dIdSet); */
			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = { "/exportFpxSettlementList" }, method = RequestMethod.GET)
	public ModelAndView exportFpxSettlementList(final Model model, @RequestParam final String fromDate,
			@RequestParam final String toDate, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam(required = false) String export, HttpServletRequest request,
			final java.security.Principal principal) {
		logger.info("searchFpxTxnSummary admin" + fromDate + "::" + toDate);
		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		transactionService.listMerchantFPXSettlementByMid(paginationBean, currentMerchant, fromDate, toDate);

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

		List<FpxTransaction> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("fpxSettlementMerchantTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("fpxSettlementMerchantTxnPdf", "txnList", list);
		}

	}

	// rk

	@RequestMapping(value = { "/FpxTxnEnq" }, method = RequestMethod.GET)
	public String displayFpxTransactionEnquiry(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/FpxTransactionEnq",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);
		transactionService.FPXTransactionEnqByMid(paginationBean, currentMerchant, null, null);

		logger.info("No of Records: " + paginationBean.getItemList().size());

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

		if (paginationBean.getItemList().size() > 0) {

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

	@RequestMapping(value = { "/searchFpxTxnEnq" }, method = RequestMethod.GET)
	public String searchFpxTxnEnquiry(final Model model, @RequestParam final String fromDate,
			@RequestParam final String toDate, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("searchFpxTxnEnquiry admin" + fromDate + "::" + toDate);

		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/FpxTransactionEnq",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		transactionService.FPXTransactionEnqByMid(paginationBean, currentMerchant, fromDate, toDate);

		logger.info("No of Records: " + paginationBean.getItemList().size());

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

		if (paginationBean.getItemList().size() > 0) {

			model.addAttribute("paginationBean", paginationBean);
			// TODO to get tid and device id
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		} else {

			logger.info("No Data");
			model.addAttribute("paginationBean", paginationBean);
			/* model.addAttribute("devIdList", dIdSet); */
			model.addAttribute("responseData", "No Record found");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

	}

	@RequestMapping(value = { "/exportFpxTxnEnq" }, method = RequestMethod.GET)
	public ModelAndView exportFpxTxnEnquiry(final Model model, @RequestParam final String fromDate,
			@RequestParam final String toDate, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam(required = false) String export, HttpServletRequest request,
			final java.security.Principal principal) {
		logger.info("searchFpxTxnEnquiry admin" + fromDate + "::" + toDate);
		// logger.info("about to list all transaction");
		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("card list transaction: " + currentMerchant);
		logger.info("Transaction Enquiry:" + principal.getName());
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		transactionService.FPXTransactionEnqByMid(paginationBean, currentMerchant, fromDate, toDate);

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

		List<FpxTransaction> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("FpxenqMerchantTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("FpxenqMerchantTxnPdf", "txnList", list);
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
		logger.info(" search settlement Summary:" + principal.getName());

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
			return new ModelAndView("SettlementsumPdf", "txnList", list1);

		}

	}

	// boostsettlementsum
	@RequestMapping(value = { "/boostsettlementsum/{currPage}" }, method = RequestMethod.GET)
	public String displayboostsettlementSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("about to list settelement  summary");

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/boostsettlementsummary",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		logger.info(" Boost settlement Summary:" + principal.getName());

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<BoostDailyRecon> paginationBean = new PaginationBean<BoostDailyRecon>();
		paginationBean.setCurrPage(currPage);

		model.addAttribute("pageBean", pageBean);

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

		transactionService.listboostsettleDetails(paginationBean, currentMerchant, null, null);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// boostsettlementsum
	@RequestMapping(value = { "/boostsettlesearch" }, method = RequestMethod.GET)
	public String displayboostsettlesearch(final Model model, final java.security.Principal principal,
			@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("about to list settelement  summary");

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/boostsettlementsummary",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		logger.info(" search boost settlement Summary:" + principal.getName());

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<BoostDailyRecon> paginationBean = new PaginationBean<BoostDailyRecon>();
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

		transactionService.listboostsettleDetails(paginationBean, currentMerchant, dat, dat1);

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

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// boostsettlementsum
	@RequestMapping(value = { "/boostsettleexport" }, method = RequestMethod.GET)
	public ModelAndView displayboostsettleexport(@RequestParam(required = false) final String date,
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

		PaginationBean<BoostDailyRecon> paginationBean = new PaginationBean<BoostDailyRecon>();
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

		transactionService.listboostsettleDetails(paginationBean, currentMerchant, dat, dat1);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<BoostDailyRecon> list1 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {
			logger.info("here excel");

			return new ModelAndView("BoostSettlementsumMerchantExcel", "txnList", list1);

		} else {
			logger.info("here pdf");
			return new ModelAndView("BoostSettlementsumMerchantPdf", "txnList", list1);

		}

	}

//grabpaysettlementsum

	@RequestMapping(value = { "/grabpaysettlementsum/{currPage}" }, method = RequestMethod.GET)
	public String displaygrabpaysettlementSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("about to list settlement  summary");

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/grabpaysettlementsummary",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		logger.info(" Grabpay settlement Summary:" + principal.getName());

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<GrabPayFile> paginationBean = new PaginationBean<GrabPayFile>();
		paginationBean.setCurrPage(currPage);

		model.addAttribute("pageBean", pageBean);

		transactionService.listgrabpaysettleDetails(paginationBean, currentMerchant, null, null);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// grabpaysettlementsum
	@RequestMapping(value = { "/grabpaysettlesearch" }, method = RequestMethod.GET)
	public String displaygrabpaysettlesearch(final Model model, final java.security.Principal principal,
			@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("about to list settelement  summary");

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/grabpaysettlementsummary",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		logger.info(" search grabpay settlement Summary:" + principal.getName());

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<GrabPayFile> paginationBean = new PaginationBean<GrabPayFile>();
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

		transactionService.listgrabpaysettleDetails(paginationBean, currentMerchant, dat, dat1);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// grabpaysettlementsum
	@RequestMapping(value = { "/grabpaysettleexport" }, method = RequestMethod.GET)
	public ModelAndView displaygrabpaysettleexport(@RequestParam(required = false) final String date,
			@RequestParam(required = false) final String date1, HttpServletRequest request,
			@RequestParam(required = false) String export, final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list settlement  summary");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<GrabPayFile> paginationBean = new PaginationBean<GrabPayFile>();
		String dat = null;
		String dat1 = null;
		String status1 = null;

		if ((date != null && date1 != null) && (!date.isEmpty() && !date1.isEmpty())) {
			try {
				dat = new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				dat1 = new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse(date1));
				logger.info("inside search controler " + dat + " " + dat1);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		transactionService.listgrabpaysettleDetails(paginationBean, currentMerchant, dat, dat1);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<GrabPayFile> list1 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {
			logger.info("here excel");

			return new ModelAndView("GrabpaySettlementsumMerchantExcel", "txnList", list1);

		} else {
			logger.info("here pdf");
			return new ModelAndView("GrabpaySettlementsumMerchantPdf", "txnList", list1);

		}

	}

	// rksubmerchant
	@RequestMapping(value = { "/submerchantSum" }, method = RequestMethod.GET)
	public String displayAddSubMerchantsUM(@ModelAttribute("merchant") final RegAddMerchant regAddMerchant,
			final HttpServletRequest request, Model model, final java.security.Principal principal) {

		logger.info("About to Add Sub merchant details");
		logger.info(" Admin login person Name:" + principal.getName());

		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/submerchantList", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		// paginationBean.setCurrPage(currPage);
		merchantService.listsubMerchantdefault(paginationBean, null, null);

		String UmEzywayMid = null;
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
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

		model.addAttribute("UmEzywayMid", UmEzywayMid);
		model.addAttribute("paginationBean", paginationBean);

		/*
		 * if(principal.getName().equals(bhuvi@mobiversa.com)) { return
		 * TEMPLATE_SUPER_AGENT; }
		 */
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// rksubmerchantsearch
	@RequestMapping(value = { "/submersearch" }, method = RequestMethod.GET)
	public String displaySearchMerchant(final Model model, final HttpServletRequest request,
			final java.security.Principal principal,

			@RequestParam(required = false, defaultValue = "1") final int currPage, @RequestParam final String date,
			@RequestParam final String date1) {
		logger.info("about to search Merchant based on search String:: " + date);

		logger.info("about to search Merchant based on search String:: " + date1);
		// String type=null;
		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/submerchantList", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();

		paginationBean.setCurrPage(currPage);

		// merchantService.listsubMerchant(paginationBean, date, date1);

		logger.info("userrolle: " + paginationBean.getItemList());
		model.addAttribute("pageBean", pageBean);

		model.addAttribute("paginationBean", paginationBean);

		String UmEzywayMid = null;
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
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
		return TEMPLATE_MERCHANT;

	}

	// rksubmerchantexport
	@RequestMapping(value = "/submerexport", method = RequestMethod.GET)
	public ModelAndView getExcel(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false) String export, final Model model, final HttpServletRequest request,
			final java.security.Principal principal) {
		/*
		 * public ModelAndView getExcel(final Model model, @RequestParam final String
		 * date,
		 * 
		 * @RequestParam(required = false, defaultValue = "1") final int currPage) {
		 */
		// List animalList = animalService.getAnimalList();
		logger.info("about to getExcel report data");
		// String type = "BANK_MERCHANT";
		/*
		 * PageBean pageBean = new PageBean("transactions list",
		 * "transaction/transactionList", Module.TRANSACTION,
		 * "transaction/sideMenuTransaction"); model.addAttribute("pageBean", pageBean);
		 * 
		 * PaginationBean<ForSettlement> paginationBean = new
		 * PaginationBean<ForSettlement>(); paginationBean.setCurrPage(currPage);
		 */
		// System.out.println(" Controller Date :"+ date);

		// PaginationBean<MobileUser> paginationBean = new
		// PaginationBean<MobileUser>();
		// paginationBean.setCurrPage(currPage);
		// mobileUserService.listMobileUsers(paginationBean,date,date1);

		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();

		// paginationBean.setCurrPage(currPage);

		// merchantService.listsubMerchant(paginationBean, date, date1);
		// List<MobileUser>
		// mobileuser=mobileUser.loadMobileUserByFk(merchant.getId());

		// List<Merchant> list
		// =merchantService.merchantSummaryExport(date,date1);

		List<Merchant> list = paginationBean.getItemList();
//                                  logger.info("check mobile user from date:" + date);
//                                   logger.info("Item count: " + paginationBean.getItemList().size());
//                                  for (Merchant m1 : list) {
		//
//                                         if (m1.getAgID() != null) {
//                                                Agent agent = agentService.loadAgentByIdPk(m1.getAgID().longValue());
//                                                if (agent != null) {
//                                                       logger.info("agent name" + agent.getUsername());
//                                                       m1.setRemarks(agent.getUsername());
//                                                } else {
//                                                       m1.setRemarks("");
//                                                }
//                                         } else {
//                                                m1.setRemarks("");
//                                         }
//                                         MID m = m1.getMid();
//                                         if (m != null) {
//                                                // if(m.getMid()==null && m.getMid().isEmpty()){
//                                                if (m.getMid() == null) {
//                                                       // logger.info("no mid");
//                                                       m.setMid("-");
		//
//                                                } else {
		//
//                                                       m.setMid(m.getMid());
//                                                }
//                                                if (m.getMotoMid() != null) {
//                                                       m.setMotoMid(m.getMotoMid());
//                                                       // logger.info("moto mid "+m.getMotoMid());
//                                                } else {
//                                                       m.setMotoMid("-");
//                                                }
//                                                if (m.getEzywayMid() != null) {
//                                                       m.setEzywayMid(m.getEzywayMid());
		//
//                                                } else {
//                                                       m.setEzywayMid("-");
//                                                }
//                                                if (m.getEzyrecMid() != null) {
//                                                       m.setEzyrecMid(m.getEzyrecMid());
		//
//                                                } else {
//                                                       m.setEzyrecMid("-");
//                                                }
		//
//                                                if (m.getUmMid() != null) {
//                                                       m.setUmMid(m.getUmMid());
		//
//                                                } else {
//                                                       m.setUmMid("-");
//                                                }
		//
//                                                if (m.getUmMotoMid() != null) {
//                                                       m.setUmMotoMid(m.getUmMotoMid());
		//
//                                                } else {
//                                                       m.setUmMotoMid("-");
//                                                }
		//
//                                                if (m.getUmEzywayMid() != null) {
//                                                       m.setUmEzywayMid(m.getUmEzywayMid());
		//
//                                                } else {
//                                                       m.setUmEzywayMid("-");
//                                                }
//                                                
//                                                
//                                                if (m.getUmEzyrecMid() != null) {
//                                                       m.setUmEzyrecMid(m.getUmEzyrecMid());
		//
//                                                } else {
//                                                       m.setUmEzyrecMid("-");
//                                                }
		//
//                                                m1.setMid(m);
//                                                // logger.info(" mid: "+m1.getMid().getMid()+" motomid:
//                                                // "+m1.getMid().getMotoMid());//+" ezypassmid: "+m1.getMid().getEzypassMid());
//                                         }
		//
//                                         // logger.info(" mid: "+m1.getMid().getMid()+" motomid:
//                                         // "+m1.getMid().getMotoMid());//+" ezypassmid: "+m1.getMid().getEzypassMid());
		//
//                                         if (m1.getRole().toString().equals("BANK_MERCHANT")) {
//                                                if (m1.getPreAuth() != null && m1.getPreAuth().equals("Yes")) {
//                                                       m1.setPreAuth("Yes");
//                                                } else {
//                                                       m1.setPreAuth("No");
//                                                }
//                                                // logger.info("MobileUser : " + m1.getId());
//                                                m1.setAutoSettled(mobileUser.loadMobileUserByFkBoost(m1.getId()));
//                                                m1.setFaxNo(mobileUser.loadMobileUserByFkMoto(m1.getId()));
//                                         } else {
//                                                m1.setAutoSettled("No");
//                                                m1.setFaxNo("No");
//                                                m1.setPreAuth("No");
//                                         }
		//
//                                  }

		// logger.info("check mobile user to date:" + date1);
		// System.out.println("export test:" + export);

		String UmEzywayMid = null;
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
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
		if (!(export.equals("PDF"))) {
			logger.info("here excel");

			return new ModelAndView("txnsubMerchantExcel", "txnList", list);

		} else {
			logger.info("here pdf");
			return new ModelAndView("txnMerchantPdf", "txnList", list);

		}
	}

	// edit sub merchant profile start added on 18-10-2021

	@RequestMapping(value = { "/edit" }, method = RequestMethod.POST)
	public String displayEditMerchantUser(final HttpServletRequest request, final Model model,
			@RequestParam("id") long id, final java.security.Principal principal) {

		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/edit/submerchantEditDetails",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		Merchant merchant = merchantService.loadMerchantbyid(id);

		logger.info("Details Merchantuser :" + merchant.getId() + ":" + principal.getName());

		String Submid = merchant.getMid().getSubMerchantMID();

		logger.info("current Merchant Sub Merchant Mid = " + Submid);

		Date activatedate = merchant.getActivateDate();

		logger.info(" activatedate = " + merchant.getActivateDate());

		Long salutation = merchant.getId();

		logger.info(" Current Merchant ID = " + salutation);

		String ADate = new SimpleDateFormat("dd-MMM-yyy").format(activatedate);

		logger.info(" ADate = " + ADate);

		String err = (String) request.getSession(true).getAttribute("editErSession1");

		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {

				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("editErSession1");
			}
		}

		String UmEzywayMid = null;

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
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

		model.addAttribute("UmEzywayMid", UmEzywayMid);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("Submid", Submid);
		model.addAttribute("ADate", ADate);
		model.addAttribute("salutation", salutation);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/editsubmerchantuser" }, method = RequestMethod.POST)
	public String displayEditMobileUserSes(final Model model, final HttpServletRequest request,
			@RequestParam("id") long id, @RequestParam("activationDate") final String activationDate,

			@RequestParam("businessName") final String businessName, @RequestParam("email") final String email,
			@RequestParam("city") final String city, @RequestParam("state") final String state,
			@RequestParam("mid") final String mid, final java.security.Principal principal) {

		logger.info("/editSubMerchant user " + id);
		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/edit/submerchantEditDetails",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		String Submid = mid;
		logger.info("Sub Merchant MID = " + mid);

		String Adate = activationDate;
		logger.info(" activatedate = " + activationDate);

		Long salutation = id;
		logger.info(" Current Merchant ID = " + salutation);

		Merchant merchant = new Merchant();

		merchant.setBusinessName(businessName);
		merchant.setEmail(email);
		merchant.setCity(city);
		merchant.setState(state);

		logger.info("businessName = " + merchant.getBusinessName());
		logger.info("email = " + merchant.getEmail());
		logger.info("city = " + merchant.getCity());
		logger.info("state = " + merchant.getState());

		BaseDataImpl baseData = new BaseDataImpl();
		Merchant m = baseData.vaildated(merchant);

		if (m != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession1", "yes");

			return "redirect:/admin/editsubmerchantuser/1";
		}

		String UmEzywayMid = null;

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
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

		model.addAttribute("UmEzywayMid", UmEzywayMid);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("salutation", salutation);
		model.addAttribute("Submid", Submid);
		model.addAttribute("Adate", Adate);

		request.getSession(true).setAttribute("editsubmerchantSession", merchant);

		request.getSession(true).setAttribute("Session", Submid);

		request.getSession(true).setAttribute("Session1", Adate);

		request.getSession(true).setAttribute("Session2", salutation);

		return "redirect:/admin/editSubMerchantUserReviewandConfirm";

	}

	@RequestMapping(value = { "/editSubMerchantUserReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditMerchantUserConfirm(final Model model, final HttpServletRequest request,
			final java.security.Principal principal) {

		logger.info("editSubMerchantUserReviewandConfirm ");

		Merchant merchant = (Merchant) request.getSession(true).getAttribute("editsubmerchantSession");

		String Submid = (String) request.getSession(true).getAttribute("Session");
		String Adate = (String) request.getSession(true).getAttribute("Session1");
		Long salutation = (Long) request.getSession(true).getAttribute("Session2");

		if (merchant == null) {
			return "redirect:" + URL_BASE + "/editsubmerchantuser/1";
		}

		PageBean pageBean = new PageBean("Sub Merchant edit Details",
				"merchantweb/edit/SubMerchantUserReviewandConfirm", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		String UmEzywayMid = null;

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
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

		model.addAttribute("UmEzywayMid", UmEzywayMid);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("Submid", Submid);
		model.addAttribute("Adate", Adate);
		model.addAttribute("salutation", salutation);

		request.getSession(true).removeAttribute("Session");
		request.getSession(true).removeAttribute("Session1");
		request.getSession(true).removeAttribute("Session2");
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/editSubMerchantUserReviewandConfirmPost" }, method = RequestMethod.POST)
	public String confirmEditMerchantUser(final Model model, final java.security.Principal principal,
			final HttpServletRequest request, @RequestParam("id") long id,
			@RequestParam("businessName") final String businessName, @RequestParam("email") String email,
			@RequestParam("city") String city, @RequestParam("state") String state) {

		logger.info("editSubMerchantUserReviewandConfirmPost");

		Merchant merchant = merchantService.loadMerchantbyid(id);

		logger.info("id" + id);

		merchant.setBusinessName(businessName);
		merchant.setState(state);
		merchant.setEmail(email);
		merchant.setCity(city);

		logger.info("Businessname =" + merchant.getBusinessName());
		logger.info("state =" + merchant.getState());
		logger.info("email =" + merchant.getEmail());
		logger.info("city =" + merchant.getCity());

		// merchantService.updateMerchant(merchant);

		merchantService.updateMerchantByNativeQuery(merchant);

		PageBean pageBean = new PageBean(" Merchant  Details", "merchantweb/edit/submerchantUserAlldoneSuccessful",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		Long salutation = id;

		BaseDataImpl baseData = new BaseDataImpl();

		Merchant m = baseData.vaildated(merchant);

		if (m != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession1", "yes");

			return "redirect:/admin/editsubmerchantuser/1";
		}

		request.getSession(true).removeAttribute("editsubmerchantSession");

		String UmEzywayMid = null;

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
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

		model.addAttribute("UmEzywayMid", UmEzywayMid);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("salutation", salutation);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// edit sub merchant profile end added on 18-10-2021

	// Rk - Settlement Pdf Start
//
//	@RequestMapping(value = { "/pdf" }, method = RequestMethod.GET)
//    public String pdf(final Model model, final java.security.Principal principal,
//                  @RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
//           logger.info("first one");
//           PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/Pdf", Module.TRANSACTION_WEB,
//                        "merchantweb/transaction/sideMenuTransaction");
//           HttpSession session = request.getSession();
//           String myName = (String) session.getAttribute("userName");
//           Merchant currentMerchant = merchantService.loadMerchant(myName);
//           ArrayList<String> midlist = new ArrayList<String>();
//           String mid = null;
//           if (currentMerchant.getMid().getUmMid() != null && !(currentMerchant.getMid().getUmMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmMid());
//                  mid = currentMerchant.getMid().getUmMid();
//           } else if (currentMerchant.getMid().getUmEzywayMid() != null
//                        && !(currentMerchant.getMid().getUmEzywayMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmEzywayMid());
//                  mid = currentMerchant.getMid().getUmEzywayMid();
//           } else if (currentMerchant.getMid().getUmEzyrecMid() != null
//                        && !(currentMerchant.getMid().getUmEzyrecMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmEzyrecMid());
//                  mid = currentMerchant.getMid().getUmEzyrecMid();
//           } else if (currentMerchant.getMid().getUmEzypassMid() != null
//                        && !(currentMerchant.getMid().getUmEzypassMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmEzypassMid());
//                  mid = currentMerchant.getMid().getUmEzypassMid();
//           } else if (currentMerchant.getMid().getUmMotoMid() != null
//                        && !(currentMerchant.getMid().getUmMotoMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmMotoMid());
//                  mid = currentMerchant.getMid().getUmMotoMid();
//           } else if (currentMerchant.getMid().getUmSsMotoMid() != null
//                        && !(currentMerchant.getMid().getUmSsMotoMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmSsMotoMid());
//                  mid = currentMerchant.getMid().getUmSsMotoMid();
//           } else if (currentMerchant.getMid().getSplitMid() != null
//                        && !(currentMerchant.getMid().getSplitMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getSplitMid());
//                  mid = currentMerchant.getMid().getSplitMid();
//           }
//           String nmid = null;
//           if (currentMerchant.getMid().getUmMid() != null && !(currentMerchant.getMid().getUmMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmMid());
//                  nmid = currentMerchant.getMid().getUmMid() + "~" + "MID";
//                  midlist.add(nmid);
//           }
//           if (currentMerchant.getMid().getUmEzywayMid() != null
//                        && !(currentMerchant.getMid().getUmEzywayMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmEzywayMid());
//                  nmid = currentMerchant.getMid().getUmEzywayMid() + "~" + "EZYWAYMID";
//                  midlist.add(nmid);
//           }
//           if (currentMerchant.getMid().getUmEzyrecMid() != null
//                        && !(currentMerchant.getMid().getUmEzyrecMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmEzyrecMid());
//                  nmid = currentMerchant.getMid().getUmEzyrecMid() + "~" + "EZYRECMID";
//                  midlist.add(nmid);
//           }
//           if (currentMerchant.getMid().getUmEzypassMid() != null
//                        && !(currentMerchant.getMid().getUmEzypassMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmEzypassMid());
//                  nmid = currentMerchant.getMid().getUmEzypassMid() + "~" + "EZYPASSMID";
//                  midlist.add(nmid);
//           }
//           if (currentMerchant.getMid().getUmMotoMid() != null && !(currentMerchant.getMid().getUmMotoMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmMotoMid());
//                  nmid = currentMerchant.getMid().getUmMotoMid() + "~" + "EZYMOTOMID";
//                  midlist.add(nmid);
//           }
//           if (currentMerchant.getMid().getUmSsMotoMid() != null
//                        && !(currentMerchant.getMid().getUmSsMotoMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmSsMotoMid());
//                  nmid = currentMerchant.getMid().getUmSsMotoMid() + "~" + "EZYMOTOSSMID";
//                  midlist.add(nmid);
//           }
//           if (currentMerchant.getMid().getSplitMid() != null && !(currentMerchant.getMid().getSplitMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getSplitMid());
//                  nmid = currentMerchant.getMid().getSplitMid() + "~" + "EZYSPLITMID";
//                  midlist.add(nmid);
//           }
//           logger.info("here mid is" + mid);
//           logger.info("midlist length::::::::" + midlist.size());
//           String to = null;
//           Date dt1 = new Date();
//           SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
//           to = dateFormat1.format(dt1);
//           to = to.replace("-", "");
//           logger.info("date" + to);
//           String year = to.substring(0, 4);
//           String month = to.substring(4, 6);
//           int monthno;
//           monthno = Integer.valueOf(month);
//           
//    monthno = monthno-0;
//     
//     logger.info("date format:" + monthno);
//           
//           ArrayList<String> fsList = new ArrayList<String>();
//           ArrayList<String> filenameList = new ArrayList<String>();
//           for (int i = monthno - 2; i <= monthno; i++) {
//                  if (i < 10) {
//                        logger.info("inside if");
//                        String m = "0" + ((i)-1);
//                        String file = "C:\\Mobi_config\\AutoRun\\MonthlyReport\\" + mid + "\\";
//                        try {
//                               String fileupdated = file + year + m + "01";
//                               File f = new File(fileupdated);
//                               logger.info(fileupdated);
//                               if (f.exists()) {
//                                      String[] pathnames = f.list();
//                                      for (String pathname : pathnames) {
//                                             String pathname1 = mid + "-" + year + m + "01" + "-" + pathname;
//                                             String filedate = year + m + "01";
//                                             
//                                             logger.info("filename date " + filedate);
//                                             filedate = filedate.substring(0, 4) + "-" + filedate.substring(4, 6) + "-"
//                                                           + filedate.substring(6, 8);
//                                             logger.info("filename date " + filedate);
//                                             SimpleDateFormat fileFormat = new SimpleDateFormat("MMM-yyyy-dd");
//                                             try {
//                                                    String filedate1 = year + m + "01";
//                                                    
//                                                    filedate = fileFormat.format(new SimpleDateFormat("yyyy-MM-dd").parse(filedate));
//                                                    logger.info("filename date " + filedate);
//                                                    filedate = filedate.substring(0, 8);
//                                                    logger.info("filename date " + filedate);
//                                             } catch (ParseException e) {
//                                                    e.printStackTrace();
//                                             }
//                                             filenameList.add(filedate);
//                                             fsList.add(pathname1);
//                                      }
//                               } else {
//                                      model.addAttribute("responseData", "No Record found for Last month");
//                               }
//                        } catch (Exception e) {
//                               e.printStackTrace();
//                        }
//                  } else {
//                        logger.info("inside else");
//                        String file = "C:\\Mobi_config\\AutoRun\\MonthlyReport\\" + mid + "\\";
//                        try {
//                               String fileupdated = file + year + i + "01";
//                               File f = new File(fileupdated);
//                               logger.info(fileupdated);
//                               if (f.exists()) {
//                                      String[] pathnames = f.list();
//                                      for (String pathname : pathnames) {
//                                             String pathname1 = mid + "-" + year + i + "01" + "-" + pathname;
//                                             String filedate = year + ((i)-1) + "01";
//                                             
//                                             logger.info("filename date " + filedate);
//                                             filedate = filedate.substring(0, 4) + "-" + filedate.substring(4, 6) + "-"
//                                                           + filedate.substring(6, 8);
//                                             logger.info("filename date " + filedate);
//                                             SimpleDateFormat fileFormat = new SimpleDateFormat("MMM-yyyy-dd");
//                                             try {
//                                                    filedate = fileFormat.format(new SimpleDateFormat("yyyy-MM-dd").parse(filedate));
//                                                    logger.info("filename date " + filedate);
//                                                    filedate = filedate.substring(0, 8);
//                                                    logger.info("filename date " + filedate);
//                                             } catch (ParseException e) {
//                                                    e.printStackTrace();
//                                             }
//                                      
//                                             filenameList.add(filedate);
//                                             fsList.add(pathname1);
//                                      }
//                               } else {
//                                      model.addAttribute("responseData", "No Record found for Last month");
//                               }
//                        } catch (Exception e) {
//                               e.printStackTrace();
//                        }
//                  }
//           }
//           for (String ss : fsList) {
//                  logger.info(ss);
//           }
//           PaginationBean<Pdf> paginationBean = new PaginationBean<Pdf>();
//           paginationBean.setItemListt(fsList);
//           paginationBean.setFilenamelist(filenameList);
//           logger.info("No of Records: " + paginationBean.getItemListt().size());
//           logger.info("No of file Records: " + paginationBean.getFilenamelist().size());
//           model.addAttribute("paginationBean", paginationBean);
//           model.addAttribute("pageBean", pageBean);
//           model.addAttribute("midlist", midlist);
//           return TEMPLATE_MERCHANT;
//    }
//
//	@RequestMapping(value = { "/pdfsearch" }, method = RequestMethod.POST)
//    public String pdfsearch(final Model model, final java.security.Principal principal,
//                  @RequestParam final String fromDate, @RequestParam final String toDate,
//                  @RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
//                  @RequestParam final String mid) {
//           PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/Pdf", Module.TRANSACTION_WEB,
//                        "merchantweb/transaction/sideMenuTransaction");
//           logger.info("here mid is" + mid);
//           String fromdat = null;
//           String todat = null;
//           String to = null;
//           String from = null;
//           Date frdate = new Date(fromDate);
//           int fromday = frdate.getDate();
//           int frommon = frdate.getMonth() + 1;
//           int fromyear1 = frdate.getYear();
//           int currentFrYear = fromyear1 + 1900;
//           String frmon = String.format("%02d", frommon);
//           String frday = String.format("%02d", fromday);
//           // String fromdateString = frday + '/' + frmon + '/' +
//           // String.valueOf(currentFrYear);
//           fromdat = String.valueOf(currentFrYear) + '-' + frmon + '-' + frday;
//           Date todate = new Date(toDate);
//           int today = todate.getDate();
//           int tomon = todate.getMonth() + 1;
//           int toyear1 = todate.getYear();
//           int currentToYear = toyear1 + 1900;
//           String tmon = String.format("%02d", tomon);
//           String tday = String.format("%02d", today);
//           // String todateString = tday + '/' + tmon + '/' +
//           // String.valueOf(currentToYear);
//           todat = String.valueOf(currentToYear) + '-' + tmon + '-' + tday;
//           String namemid = mid.substring(0, 15);
//           logger.info(" Mid is after removing " + namemid);
//           logger.info(" dates from " + fromdat + " todate " + todat);
//           ArrayList<String> midlist = new ArrayList<String>();
//           HttpSession session = request.getSession();
//           String myName = (String) session.getAttribute("userName");
//           Merchant currentMerchant = merchantService.loadMerchant(myName);
//           String nmid = null;
//           if (currentMerchant.getMid().getUmMid() != null && !(currentMerchant.getMid().getUmMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmMid());
//                  nmid = currentMerchant.getMid().getUmMid() + "~" + "MID";
//                  midlist.add(nmid);
//           }
//           if (currentMerchant.getMid().getUmEzywayMid() != null
//                        && !(currentMerchant.getMid().getUmEzywayMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmEzywayMid());
//                  nmid = currentMerchant.getMid().getUmEzywayMid() + "~" + "EZYWAYMID";
//                  midlist.add(nmid);
//           }
//           if (currentMerchant.getMid().getUmEzyrecMid() != null
//                        && !(currentMerchant.getMid().getUmEzyrecMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmEzyrecMid());
//                  nmid = currentMerchant.getMid().getUmEzyrecMid() + "~" + "EZYRECMID";
//                  midlist.add(nmid);
//           }
//           if (currentMerchant.getMid().getUmEzypassMid() != null
//                        && !(currentMerchant.getMid().getUmEzypassMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmEzypassMid());
//                  nmid = currentMerchant.getMid().getUmEzypassMid() + "~" + "EZYPASSMID";
//                  midlist.add(nmid);
//           }
//           if (currentMerchant.getMid().getUmMotoMid() != null && !(currentMerchant.getMid().getUmMotoMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmMotoMid());
//                  nmid = currentMerchant.getMid().getUmMotoMid() + "~" + "EZYMOTOMID";
//                  midlist.add(nmid);
//           }
//           if (currentMerchant.getMid().getUmSsMotoMid() != null
//                        && !(currentMerchant.getMid().getUmSsMotoMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getUmSsMotoMid());
//                  nmid = currentMerchant.getMid().getUmSsMotoMid() + "~" + "EZYMOTOSSMID";
//                  midlist.add(nmid);
//           }
//           if (currentMerchant.getMid().getSplitMid() != null && !(currentMerchant.getMid().getSplitMid().isEmpty())) {
//                  logger.info(currentMerchant.getMid().getSplitMid());
//                  nmid = currentMerchant.getMid().getSplitMid() + "~" + "EZYSPLITMID";
//                  midlist.add(nmid);
//           }
//           from = fromdat.replace("-", "");
//           to = todat.replace("-", "");
//           logger.info("date format: " + from + " " + to);
//           String fromyear = from.substring(0, 4);
//           String toyear = to.substring(0, 4);
//           String frommonth = from.substring(4, 6);
//           String tomonth = to.substring(4, 6);
//           logger.info("date format: " + fromyear + " " + toyear + " " + frommonth + " " + tomonth);
//           int frommonthno = Integer.valueOf(frommonth);
//           int tomonthno = Integer.valueOf(tomonth);
//           int fromyearno = Integer.valueOf(fromyear);
//           int toyearno = Integer.valueOf(toyear);
//           logger.info("month format:" + frommonthno + " " + tomonthno + " " + fromyearno + " " + toyearno);
//           ArrayList<String> fsList = new ArrayList<String>();
//           ArrayList<String> filenameList = new ArrayList<String>();
//           for (int i = fromyearno; i <= toyearno; i++) {
//                  for (int j = frommonthno; j <= 12; j++) {
//                        if (i == toyearno && j == tomonthno) {
//                               String file = "C:\\Mobi_config\\AutoRun\\MonthlyReport\\" + namemid + "\\";
//                               if (j < 10) {
//                                      logger.info(" inside month lessthan 10");
//                                      //Integer k = j;
//                                      Integer k = j-1;
//                                      String l = k.toString();
//                                      String n = "0" + l;
//                                      String fileupdated = file + i + n + "01";
//                                      File f = new File(fileupdated);
//                                      logger.info(fileupdated);
//                                      try {
//                                             if (f.exists()) {
//                                                    String[] pathnames = f.list();
//                                                    for (String pathname : pathnames) {
//                                                         Integer I = i;
//                                                          // Integer I = i-1;
//                                                           
//                                                           String si = I.toString();
//                                                           String pathname1 = namemid + "-" + si + n + "01" + "-" + pathname;
//                                                           String filedate = si + n + "01";
//                                                           logger.info("filename date " + filedate);
//                                                           filedate = filedate.substring(0, 4) + "-" + filedate.substring(4, 6) + "-"
//                                                                        + filedate.substring(6, 8);
//                                                           logger.info("filename date " + filedate);
//                                                           SimpleDateFormat fileFormat = new SimpleDateFormat("MMM-yyyy-dd");
//                                                           try {
//                                                                  filedate = fileFormat
//                                                                               .format(new SimpleDateFormat("yyyy-MM-dd").parse(filedate));
//                                                                  logger.info("filename date " + filedate);
//                                                                  filedate = filedate.substring(0, 8);
//                                                                  logger.info("filename date " + filedate);
//                                                           } catch (ParseException e) {
//                                                                  e.printStackTrace();
//                                                           }
//                                                           filenameList.add(filedate);
//                                                           fsList.add(pathname1);
//                                                    }
//                                             }
//                                      } catch (Exception e) {
//                                             e.printStackTrace();
//                                      }
//                               } else {
//                                      logger.info(" inside month equal or greater than 10");
//                                      String fileupdated = file + i + j + "01";
//                                      File f = new File(fileupdated);
//                                      logger.info(fileupdated);
//                                      try {
//                                             if (f.exists()) {
//                                                    String[] pathnames = f.list();
//                                                    for (String pathname : pathnames) {
//                                                           Integer I = i;
//                                                           //Integer I = i-1;
//                                                           String si = I.toString();
//                                                           Integer J = j;
//                                                           //Integer J = j-1;
//                                                           String sj = J.toString();
//                                                           String pathname1 = namemid + "-" + si + sj + "01" + "-" + pathname;
//                                                           String filedate = si + sj + "01";
//                                                           logger.info("filename date " + filedate);
//                                                           filedate = filedate.substring(0, 4) + "-" + filedate.substring(4, 6) + "-"
//                                                                        + filedate.substring(6, 8);
//                                                           logger.info("filename date " + filedate);
//                                                           SimpleDateFormat fileFormat = new SimpleDateFormat("MMM-yyyy-dd");
//                                                           try {
//                                                                  filedate = fileFormat
//                                                                               .format(new SimpleDateFormat("yyyy-MM-dd").parse(filedate));
//                                                                  logger.info("filename date " + filedate);
//                                                                  filedate = filedate.substring(0, 8);
//                                                                  logger.info("filename date " + filedate);
//                                                           } catch (ParseException e) {
//                                                                  e.printStackTrace();
//                                                           }
//                                                           filenameList.add(filedate);
//                                                           fsList.add(pathname1);
//                                                    }
//                                             }
//                                      } catch (Exception e) {
//                                             e.printStackTrace();
//                                      }
//                               }
//                               break;
//                        }
//                        String file = "C:\\Mobi_config\\AutoRun\\MonthlyReport\\" + namemid + "\\";
//                        if (j < 10) {
//                               logger.info(" inside main else month lessthan 10");
//                               //Integer k = j;
//                               Integer k = j-1;
//                               String l = k.toString();
//                               String n = "0" + l;
//                               String fileupdated = file + i + n + "01";
//                               File f = new File(fileupdated);
//                               logger.info(fileupdated);
//                               try {
//                                      if (f.exists()) {
//                                             String[] pathnames = f.list();
//                                             for (String pathname : pathnames) {
//                                                    Integer I = i;
//                                                   // Integer I = i-1;
//                                                    String si = I.toString();
//                                                    String pathname1 = namemid + "-" + si + n + "01" + "-" + pathname;
//                                                    String filedate = si + n + "01";
//                                                    logger.info("filename date " + filedate);
//                                                    filedate = filedate.substring(0, 4) + "-" + filedate.substring(4, 6) + "-"
//                                                                  + filedate.substring(6, 8);
//                                                    logger.info("filename date " + filedate);
//                                                    SimpleDateFormat fileFormat = new SimpleDateFormat("MMM-yyyy-dd");
//                                                    try {
//                                                           filedate = fileFormat.format(new SimpleDateFormat("yyyy-MM-dd").parse(filedate));
//                                                           logger.info("filename date " + filedate);
//                                                           filedate = filedate.substring(0, 8);
//                                                           logger.info("filename date " + filedate);
//                                                    } catch (ParseException e) {
//                                                           e.printStackTrace();
//                                                    }
//                                                    filenameList.add(filedate);
//                                                    fsList.add(pathname1);
//                                             }
//                                      }
//                               } catch (Exception e) {
//                                      e.printStackTrace();
//                               }
//                        } else {
//                               logger.info(" inside main else month equals or greater than 10");
//                               String fileupdated = file + i + (j-1) + "01";
//                               File f = new File(fileupdated);
//                               logger.info(fileupdated);
//                               try {
//                                      if (f.exists()) {
//                                             String[] pathnames = f.list();
//                                             for (String pathname : pathnames) {
//                                                    Integer I = i;
//                                                    //Integer I = i-1;
//                                                    String si = I.toString();
//                                     Integer J = j;
//                                                 //   Integer J = j-1;
//                                                    String sj = J.toString();
//                                                    String pathname1 = namemid + "-" + si + sj + "01" + "-" + pathname;
//                                                    String filedate = si + sj + "01";
//                                                    logger.info("filename date " + filedate);
//                                                    filedate = filedate.substring(0, 4) + "-" + filedate.substring(4, 6) + "-"
//                                                                  + filedate.substring(6, 8);
//                                                    logger.info("filename date " + filedate);
//                                                    SimpleDateFormat fileFormat = new SimpleDateFormat("MMM-yyyy-dd");
//                                                    try {
//                                                           filedate = fileFormat.format(new SimpleDateFormat("yyyy-MM-dd").parse(filedate));
//                                                           logger.info("filename date " + filedate);
//                                                           filedate = filedate.substring(0, 8);
//                                                           logger.info("filename date " + filedate);
//                                                    } catch (ParseException e) {
//                                                           e.printStackTrace();
//                                                    }
//                                                    filenameList.add(filedate);
//                                                    fsList.add(pathname1);
//                                             }
//                                      }
//                               } catch (Exception e) {
//                                      e.printStackTrace();
//                               }
//                        }
//                  }
//                  frommonthno = 1;
//           }
//           for (String ss : fsList) {
//                  System.out.println(ss);
//           }
//           PaginationBean<Pdf> paginationBean = new PaginationBean<Pdf>();
//           paginationBean.setItemListt(fsList);
//           paginationBean.setFilenamelist(filenameList);
//           logger.info("No of Records: " + paginationBean.getItemListt().size());
//           logger.info("No of Records: " + paginationBean.getFilenamelist().size());
//           model.addAttribute("paginationBean", paginationBean);
//           model.addAttribute("pageBean", pageBean);
//           model.addAttribute("midlist", midlist);
//           return TEMPLATE_MERCHANT;
//    }
//

	@RequestMapping(value = { "/pdf" }, method = RequestMethod.GET)
	public String pdf(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("first one");
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/Pdf", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		ArrayList<String> midlist = new ArrayList<String>();
		String mid = null;
		if (currentMerchant.getMid().getUmMid() != null && !(currentMerchant.getMid().getUmMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmMid());
			mid = currentMerchant.getMid().getUmMid();
		} else if (currentMerchant.getMid().getUmEzywayMid() != null
				&& !(currentMerchant.getMid().getUmEzywayMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmEzywayMid());
			mid = currentMerchant.getMid().getUmEzywayMid();
		} else if (currentMerchant.getMid().getUmEzyrecMid() != null
				&& !(currentMerchant.getMid().getUmEzyrecMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmEzyrecMid());
			mid = currentMerchant.getMid().getUmEzyrecMid();
		} else if (currentMerchant.getMid().getUmEzypassMid() != null
				&& !(currentMerchant.getMid().getUmEzypassMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmEzypassMid());
			mid = currentMerchant.getMid().getUmEzypassMid();
		} else if (currentMerchant.getMid().getUmMotoMid() != null
				&& !(currentMerchant.getMid().getUmMotoMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmMotoMid());
			mid = currentMerchant.getMid().getUmMotoMid();
		} else if (currentMerchant.getMid().getUmSsMotoMid() != null
				&& !(currentMerchant.getMid().getUmSsMotoMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmSsMotoMid());
			mid = currentMerchant.getMid().getUmSsMotoMid();
		} else if (currentMerchant.getMid().getSplitMid() != null
				&& !(currentMerchant.getMid().getSplitMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getSplitMid());
			mid = currentMerchant.getMid().getSplitMid();
		}else if (currentMerchant.getMid().getFiuuMid() != null
				&& !(currentMerchant.getMid().getFiuuMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getFiuuMid());
			mid = currentMerchant.getMid().getFiuuMid();
		}
		
		
		String nmid = null;
		if (currentMerchant.getMid().getUmMid() != null && !(currentMerchant.getMid().getUmMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmMid());
			nmid = currentMerchant.getMid().getUmMid() + "~" + "MID";
			midlist.add(nmid);
		}
		if (currentMerchant.getMid().getUmEzywayMid() != null
				&& !(currentMerchant.getMid().getUmEzywayMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmEzywayMid());
			nmid = currentMerchant.getMid().getUmEzywayMid() + "~" + "EZYWAYMID";
			midlist.add(nmid);
		}
		if (currentMerchant.getMid().getUmEzyrecMid() != null
				&& !(currentMerchant.getMid().getUmEzyrecMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmEzyrecMid());
			nmid = currentMerchant.getMid().getUmEzyrecMid() + "~" + "EZYRECMID";
			midlist.add(nmid);
		}
		if (currentMerchant.getMid().getUmEzypassMid() != null
				&& !(currentMerchant.getMid().getUmEzypassMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmEzypassMid());
			nmid = currentMerchant.getMid().getUmEzypassMid() + "~" + "EZYPASSMID";
			midlist.add(nmid);
		}
		if (currentMerchant.getMid().getUmMotoMid() != null && !(currentMerchant.getMid().getUmMotoMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmMotoMid());
			nmid = currentMerchant.getMid().getUmMotoMid() + "~" + "EZYMOTOMID";
			midlist.add(nmid);
		}
		if (currentMerchant.getMid().getUmSsMotoMid() != null
				&& !(currentMerchant.getMid().getUmSsMotoMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmSsMotoMid());
			nmid = currentMerchant.getMid().getUmSsMotoMid() + "~" + "EZYMOTOSSMID";
			midlist.add(nmid);
		}
		if (currentMerchant.getMid().getSplitMid() != null && !(currentMerchant.getMid().getSplitMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getSplitMid());
			nmid = currentMerchant.getMid().getSplitMid() + "~" + "EZYSPLITMID";
			midlist.add(nmid);
		}
		
		if (currentMerchant.getMid().getFiuuMid() != null && !(currentMerchant.getMid().getFiuuMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getFiuuMid());
			nmid = currentMerchant.getMid().getFiuuMid() + "~" + "FIUUMID";
			midlist.add(nmid);
		}
		
		logger.info("here mid is" + mid);
		logger.info("midlist length::::::::" + midlist.size());
		String to = null;
		Date dt1 = new Date();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		to = dateFormat1.format(dt1);
		to = to.replace("-", "");
		logger.info("date" + to);
		String year = to.substring(0, 4);
		String month = to.substring(4, 6);
		int monthno;
		monthno = Integer.valueOf(month);
		monthno = monthno - 1;
		logger.info("date format:" + monthno);
		ArrayList<String> fsList = new ArrayList<String>();
		ArrayList<String> filenameList = new ArrayList<String>();
		for (int i = monthno - 2; i <= monthno; i++) {
			if (i < 10) {
				logger.info("inside if");
				String m = "0" + i;
				String file = "C:\\Mobi_config\\AutoRun\\MonthlyReport\\" + mid + "\\";
				try {
					String fileupdated = file + year + m + "01";
					File f = new File(fileupdated);
					logger.info(fileupdated);
					if (f.exists()) {
						String[] pathnames = f.list();
						for (String pathname : pathnames) {
							String pathname1 = mid + "-" + year + m + "01" + "-" + pathname;
							String filedate = year + m + "01";
							logger.info("filename date " + filedate);
							filedate = filedate.substring(0, 4) + "-" + filedate.substring(4, 6) + "-"
									+ filedate.substring(6, 8);
							logger.info("filename date " + filedate);
							SimpleDateFormat fileFormat = new SimpleDateFormat("MMM-yyyy-dd");
							try {
								filedate = fileFormat.format(new SimpleDateFormat("yyyy-MM-dd").parse(filedate));
								logger.info("filename date " + filedate);
								filedate = filedate.substring(0, 8);
								logger.info("filename date " + filedate);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							filenameList.add(filedate);
							fsList.add(pathname1);
						}
					} else {
						model.addAttribute("responseData", "No Record found for Last month");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				logger.info("inside else");
				String file = "C:\\Mobi_config\\AutoRun\\MonthlyReport\\" + mid + "\\";
				try {
					String fileupdated = file + year + i + "01";
					File f = new File(fileupdated);
					logger.info(fileupdated);
					if (f.exists()) {
						String[] pathnames = f.list();
						for (String pathname : pathnames) {
							String pathname1 = mid + "-" + year + i + "01" + "-" + pathname;
							String filedate = year + i + "01";
							logger.info("filename date " + filedate);
							filedate = filedate.substring(0, 4) + "-" + filedate.substring(4, 6) + "-"
									+ filedate.substring(6, 8);
							logger.info("filename date " + filedate);
							SimpleDateFormat fileFormat = new SimpleDateFormat("MMM-yyyy-dd");
							try {
								filedate = fileFormat.format(new SimpleDateFormat("yyyy-MM-dd").parse(filedate));
								logger.info("filename date " + filedate);
								filedate = filedate.substring(0, 8);
								logger.info("filename date " + filedate);
							} catch (ParseException e) {
								e.printStackTrace();
							}
							filenameList.add(filedate);
							fsList.add(pathname1);
						}
					} else {
						model.addAttribute("responseData", "No Record found for Last month");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		for (String ss : fsList) {
			logger.info(ss);
		}
		PaginationBean<Pdf> paginationBean = new PaginationBean<Pdf>();
		paginationBean.setItemListt(fsList);
		paginationBean.setFilenamelist(filenameList);
		logger.info("No of Records: " + paginationBean.getItemListt().size());
		logger.info("No of file Records: " + paginationBean.getFilenamelist().size());
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("midlist", midlist);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/pdfsearch" }, method = RequestMethod.POST)
	public String pdfsearch(final Model model, final java.security.Principal principal,
			@RequestParam final String fromDate, @RequestParam final String toDate,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
			@RequestParam final String mid) {
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/Pdf", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		logger.info("here mid is" + mid);
		String fromdat = null;
		String todat = null;
		String to = null;
		String from = null;
		Date frdate = new Date(fromDate);
		int fromday = frdate.getDate();
		int frommon = frdate.getMonth() + 1;
		int fromyear1 = frdate.getYear();
		int currentFrYear = fromyear1 + 1900;
		String frmon = String.format("%02d", frommon);
		String frday = String.format("%02d", fromday);
		// String fromdateString = frday + '/' + frmon + '/' +
		// String.valueOf(currentFrYear);
		fromdat = String.valueOf(currentFrYear) + '-' + frmon + '-' + frday;
		Date todate = new Date(toDate);
		int today = todate.getDate();
		int tomon = todate.getMonth() + 1;
		int toyear1 = todate.getYear();
		int currentToYear = toyear1 + 1900;
		String tmon = String.format("%02d", tomon);
		String tday = String.format("%02d", today);
		// String todateString = tday + '/' + tmon + '/' +
		// String.valueOf(currentToYear);
		todat = String.valueOf(currentToYear) + '-' + tmon + '-' + tday;
		
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		
		String fiuuMid = mid.split("~")[0];
		logger.info("fiuuMid : "+fiuuMid+ "current merchant fiuu mid : "+currentMerchant.getMid().getFiuuMid());
		String namemid = null;
		if(currentMerchant.getMid().getFiuuMid() != null &&  fiuuMid.equals(currentMerchant.getMid().getFiuuMid())) {
			namemid = fiuuMid;
		}else {
			namemid = mid.substring(0, 15);
		}
		logger.info(" Mid is after removing " + namemid);
		logger.info(" dates from " + fromdat + " todate " + todat);
		ArrayList<String> midlist = new ArrayList<String>();
	
		String nmid = null;
		if (currentMerchant.getMid().getUmMid() != null && !(currentMerchant.getMid().getUmMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmMid());
			nmid = currentMerchant.getMid().getUmMid() + "~" + "MID";
			midlist.add(nmid);
		}
		if (currentMerchant.getMid().getUmEzywayMid() != null
				&& !(currentMerchant.getMid().getUmEzywayMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmEzywayMid());
			nmid = currentMerchant.getMid().getUmEzywayMid() + "~" + "EZYWAYMID";
			midlist.add(nmid);
		}
		if (currentMerchant.getMid().getUmEzyrecMid() != null
				&& !(currentMerchant.getMid().getUmEzyrecMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmEzyrecMid());
			nmid = currentMerchant.getMid().getUmEzyrecMid() + "~" + "EZYRECMID";
			midlist.add(nmid);
		}
		if (currentMerchant.getMid().getUmEzypassMid() != null
				&& !(currentMerchant.getMid().getUmEzypassMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmEzypassMid());
			nmid = currentMerchant.getMid().getUmEzypassMid() + "~" + "EZYPASSMID";
			midlist.add(nmid);
		}
		if (currentMerchant.getMid().getUmMotoMid() != null && !(currentMerchant.getMid().getUmMotoMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmMotoMid());
			nmid = currentMerchant.getMid().getUmMotoMid() + "~" + "EZYMOTOMID";
			midlist.add(nmid);
		}
		if (currentMerchant.getMid().getUmSsMotoMid() != null
				&& !(currentMerchant.getMid().getUmSsMotoMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getUmSsMotoMid());
			nmid = currentMerchant.getMid().getUmSsMotoMid() + "~" + "EZYMOTOSSMID";
			midlist.add(nmid);
		}
		if (currentMerchant.getMid().getSplitMid() != null && !(currentMerchant.getMid().getSplitMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getSplitMid());
			nmid = currentMerchant.getMid().getSplitMid() + "~" + "EZYSPLITMID";
			midlist.add(nmid);
		}
		//fiuuMid
		if (currentMerchant.getMid().getFiuuMid() != null && !(currentMerchant.getMid().getFiuuMid().isEmpty())) {
			logger.info(currentMerchant.getMid().getFiuuMid());
			nmid = currentMerchant.getMid().getFiuuMid() + "~" + "FIUUMID";
			midlist.add(nmid);
		}
		
		from = fromdat.replace("-", "");
		to = todat.replace("-", "");
		logger.info("date format: " + from + " " + to);
		String fromyear = from.substring(0, 4);
		String toyear = to.substring(0, 4);
		String frommonth = from.substring(4, 6);
		String tomonth = to.substring(4, 6);
		logger.info("date format: " + fromyear + " " + toyear + " " + frommonth + " " + tomonth);
		int frommonthno = Integer.valueOf(frommonth);
		int tomonthno = Integer.valueOf(tomonth);
		int fromyearno = Integer.valueOf(fromyear);
		int toyearno = Integer.valueOf(toyear);
		logger.info("month format:" + frommonthno + " " + tomonthno + " " + fromyearno + " " + toyearno);
		ArrayList<String> fsList = new ArrayList<String>();
		ArrayList<String> filenameList = new ArrayList<String>();
		for (int i = fromyearno; i <= toyearno; i++) {
			for (int j = frommonthno; j <= 12; j++) {
				if (i == toyearno && j == tomonthno) {
					String file = "C:\\Mobi_config\\AutoRun\\MonthlyReport\\" + namemid + "\\";
					if (j < 10) {
						logger.info(" inside month lessthan 10");
						Integer k = j;
						String l = k.toString();
						String n = "0" + l;
						String fileupdated = file + i + n + "01";
						File f = new File(fileupdated);
						logger.info(fileupdated);
						try {
							if (f.exists()) {
								String[] pathnames = f.list();
								for (String pathname : pathnames) {
									Integer I = i;
									String si = I.toString();
									String pathname1 = namemid + "-" + si + n + "01" + "-" + pathname;
									String filedate = si + n + "01";
									logger.info("filename date " + filedate);
									filedate = filedate.substring(0, 4) + "-" + filedate.substring(4, 6) + "-"
											+ filedate.substring(6, 8);
									logger.info("filename date " + filedate);
									SimpleDateFormat fileFormat = new SimpleDateFormat("MMM-yyyy-dd");
									try {
										filedate = fileFormat
												.format(new SimpleDateFormat("yyyy-MM-dd").parse(filedate));
										logger.info("filename date " + filedate);
										filedate = filedate.substring(0, 8);
										logger.info("filename date " + filedate);
									} catch (ParseException e) {
										e.printStackTrace();
									}
									filenameList.add(filedate);
									fsList.add(pathname1);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						logger.info(" inside month equal or greater than 10");
						String fileupdated = file + i + j + "01";
						File f = new File(fileupdated);
						logger.info(fileupdated);
						try {
							if (f.exists()) {
								String[] pathnames = f.list();
								for (String pathname : pathnames) {
									Integer I = i;
									String si = I.toString();
									Integer J = j;
									String sj = J.toString();
									String pathname1 = namemid + "-" + si + sj + "01" + "-" + pathname;
									String filedate = si + sj + "01";
									logger.info("filename date " + filedate);
									filedate = filedate.substring(0, 4) + "-" + filedate.substring(4, 6) + "-"
											+ filedate.substring(6, 8);
									logger.info("filename date " + filedate);
									SimpleDateFormat fileFormat = new SimpleDateFormat("MMM-yyyy-dd");
									try {
										filedate = fileFormat
												.format(new SimpleDateFormat("yyyy-MM-dd").parse(filedate));
										logger.info("filename date " + filedate);
										filedate = filedate.substring(0, 8);
										logger.info("filename date " + filedate);
									} catch (ParseException e) {
										e.printStackTrace();
									}
									filenameList.add(filedate);
									fsList.add(pathname1);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					break;
				}
				String file = "C:\\Mobi_config\\AutoRun\\MonthlyReport\\" + namemid + "\\";
				if (j < 10) {
					logger.info(" inside main else month lessthan 10");
					Integer k = j;
					String l = k.toString();
					String n = "0" + l;
					String fileupdated = file + i + n + "01";
					File f = new File(fileupdated);
					logger.info(fileupdated);
					try {
						if (f.exists()) {
							String[] pathnames = f.list();
							for (String pathname : pathnames) {
								Integer I = i;
								String si = I.toString();
								String pathname1 = namemid + "-" + si + n + "01" + "-" + pathname;
								String filedate = si + n + "01";
								logger.info("filename date " + filedate);
								filedate = filedate.substring(0, 4) + "-" + filedate.substring(4, 6) + "-"
										+ filedate.substring(6, 8);
								logger.info("filename date " + filedate);
								SimpleDateFormat fileFormat = new SimpleDateFormat("MMM-yyyy-dd");
								try {
									filedate = fileFormat.format(new SimpleDateFormat("yyyy-MM-dd").parse(filedate));
									logger.info("filename date " + filedate);
									filedate = filedate.substring(0, 8);
									logger.info("filename date " + filedate);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								filenameList.add(filedate);
								fsList.add(pathname1);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					logger.info(" inside main else month equals or greater than 10");
					String fileupdated = file + i + j + "01";
					File f = new File(fileupdated);
					logger.info(fileupdated);
					try {
						if (f.exists()) {
							String[] pathnames = f.list();
							for (String pathname : pathnames) {
								Integer I = i;
								String si = I.toString();
								Integer J = j;
								String sj = J.toString();
								String pathname1 = namemid + "-" + si + sj + "01" + "-" + pathname;
								String filedate = si + sj + "01";
								logger.info("filename date " + filedate);
								filedate = filedate.substring(0, 4) + "-" + filedate.substring(4, 6) + "-"
										+ filedate.substring(6, 8);
								logger.info("filename date " + filedate);
								SimpleDateFormat fileFormat = new SimpleDateFormat("MMM-yyyy-dd");
								try {
									filedate = fileFormat.format(new SimpleDateFormat("yyyy-MM-dd").parse(filedate));
									logger.info("filename date " + filedate);
									filedate = filedate.substring(0, 8);
									logger.info("filename date " + filedate);
								} catch (ParseException e) {
									e.printStackTrace();
								}
								filenameList.add(filedate);
								fsList.add(pathname1);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			frommonthno = 1;
		}
		for (String ss : fsList) {
			System.out.println(ss);
		}
		PaginationBean<Pdf> paginationBean = new PaginationBean<Pdf>();
		paginationBean.setItemListt(fsList);
		paginationBean.setFilenamelist(filenameList);
		logger.info("No of Records: " + paginationBean.getItemListt().size());
		logger.info("No of Records: " + paginationBean.getFilenamelist().size());
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("midlist", midlist);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/getpdf/{name}" }, method = RequestMethod.GET)
	public String getpdf(final Model model, final java.security.Principal principal, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage, @PathVariable final String name,
			HttpServletResponse response) {
		logger.info("Request to display text name: " + name);
		logger.info("second one");
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/Pdf", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String mid = null;
		logger.info("here mid is" + mid);
		try {
			String s = "C:\\Mobi_config\\AutoRun\\MonthlyReport\\";
			String l = name.replace("-", "\\");
			logger.info("date is here " + l);
			String r = s + l + ".pdf";
			logger.info("file is here " + r);
			Path pdfPath = Paths.get(r);
			byte[] documentInBytes = Files.readAllBytes(pdfPath);
			response.setHeader("Content-Disposition", "inline; filename=\"report.pdf\"");
			response.setDateHeader("Expires", -1);
			response.setContentType("application/pdf");
			response.setContentLength(documentInBytes.length);
			response.getOutputStream().write(documentInBytes);
		} catch (Exception ex) {
			logger.info(ex);
		} finally {
		}
		return null;
	}

	@RequestMapping(value = { "/dailypdf" }, method = RequestMethod.GET)
	public String dailypdf(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("first one");
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/DailyPdf",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		// MID CHANGES

		String ezyMid = currentMerchant.getMid().getMid();
		String ezyUmMid = currentMerchant.getMid().getUmMid();
		String ezyWayMid = currentMerchant.getMid().getEzywayMid();
		String ezywayUmMid = currentMerchant.getMid().getUmEzywayMid();
		String ezymotoUmMid = currentMerchant.getMid().getUmMotoMid();
		String motoMid = currentMerchant.getMid().getMotoMid();
		String fpxMid = currentMerchant.getMid().getFpxMid();
		String mid = null;
		List<String> midList = new ArrayList<String>();

		if (fpxMid != null) {
			midList.add(fpxMid);
		}
		if (ezyMid != null) {
			midList.add(ezyMid);
		}
		if (ezyUmMid != null) {
			midList.add(ezyUmMid);
		}
		if (ezyWayMid != null) {
			midList.add(ezyWayMid);
		}
		if (ezywayUmMid != null) {
			midList.add(ezywayUmMid);
		}
		if (ezymotoUmMid != null) {
			midList.add(ezymotoUmMid);
		}
		if (motoMid != null) {
			midList.add(motoMid);
		}

		for (String s : midList) {
			System.out.println(s);
			String mid1 = s;

			logger.info("Mid value : " + mid1);

			String merchantname = currentMerchant.getBusinessName().toUpperCase().replace("\\", "").replace(" ", "")
					.replace("/", "");

			String to = null;
			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
			to = dateFormat1.format(dt1);
			to = to.replace("-", "");
			logger.info("date" + to);

			String day = to.substring(0, 2);
			String month = to.substring(2, 4);
			String year = to.substring(4, 8);
			int dayno;
			dayno = Integer.valueOf(day);
			dayno = dayno - 1;
			int monthno = Integer.valueOf(month);
			int yearno = Integer.valueOf(year);
			logger.info("date format:" + dayno);
			ArrayList<String> fsList = new ArrayList<String>();
			ArrayList<String> filenameList = new ArrayList<String>();
			for (int i = dayno - 2; i <= dayno; i++) {
				if (i < 10) {
					logger.info("inside Month Less Than 10");
					String d = "0" + i;
					String m = null;
					if (monthno < 10) {
						m = "0" + monthno;
					} else {
						m = month;
					}

					try {
						// C:\\Mobi_config\\AutoRun\\UMobile\\SettlementClearence\\FpxPDF\\

						String file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\FpxPDF\\";
						String fileupdated = file + mid1 + "MOBI_FPX_" + merchantname + "_" + d + m + year;
						File f = new File(fileupdated + ".pdf");
						logger.info(f);
						fileupdated = fileupdated.replace("\\", "-");
						fileupdated = fileupdated.replace(".", "--");
						logger.info("file name after replacing // with - " + fileupdated);
						if (f.exists()) {

							String filedate = year + m + d;
							filedate = new SimpleDateFormat("dd-MMM-yyyy")
									.format(new SimpleDateFormat("yyyyMMdd").parse(filedate));
							logger.info("filename date " + filedate);
							filenameList.add(filedate);
							fsList.add(fileupdated);

						} else {
							model.addAttribute("responseData", "No Record found for Last month");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					logger.info("inside else");

					String file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\FpxPDF\\";
					try {
						String m = null;
						if (monthno < 10) {
							m = "0" + monthno;
						} else {
							m = month;
						}

						String fileupdated = file + mid1 + "MOBI_FPX_" + merchantname + "_" + i + m + year;
						File f = new File(fileupdated + ".pdf");
						logger.info(f);
						fileupdated = fileupdated.replace("\\", "-");
						fileupdated = fileupdated.replace(".", "--");
						logger.info("file name after replacing // with - " + fileupdated);

						if (f.exists()) {

							String filedate = year + month + i;
							filedate = new SimpleDateFormat("dd-MMM-yyyy")
									.format(new SimpleDateFormat("yyyyMMdd").parse(filedate));
							logger.info("filename date " + filedate);

							filenameList.add(filedate);
							fsList.add(fileupdated);

						} else {
							model.addAttribute("responseData", "No Record found for Last month");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			for (String ss : fsList) {
				logger.info(ss);
			}
			PaginationBean<Pdf> paginationBean = new PaginationBean<Pdf>();
			paginationBean.setItemListt(fsList);
			paginationBean.setFilenamelist(filenameList);
			logger.info("No of Records: " + paginationBean.getItemListt().size());
			logger.info("No of file Records: " + paginationBean.getFilenamelist().size());
			model.addAttribute("paginationBean", paginationBean);
			model.addAttribute("pageBean", pageBean);

		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/pdfdailysearch" }, method = RequestMethod.POST)
	public String pdfdailysearch(final Model model, final java.security.Principal principal,
			@RequestParam final String fromDate, @RequestParam final String toDate,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
			@RequestParam final String txnType) {
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/DailyPdf",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		String fromdat = null;
		String todat = null;
		String to = null;
		String from = null;
		Date frdate = new Date(fromDate);
		int fromday = frdate.getDate();
		int frommon = frdate.getMonth() + 1;
		int fromyear1 = frdate.getYear();
		int currentFrYear = fromyear1 + 1900;
		String frmon = String.format("%02d", frommon);
		String frday = String.format("%02d", fromday);

		fromdat = String.valueOf(currentFrYear) + '-' + frmon + '-' + frday;
		Date todate = new Date(toDate);
		int today = todate.getDate();
		int tomon = todate.getMonth() + 1;
		int toyear1 = todate.getYear();
		int currentToYear = toyear1 + 1900;
		String tmon = String.format("%02d", tomon);
		String tday = String.format("%02d", today);

		todat = String.valueOf(currentToYear) + '-' + tmon + '-' + tday;

		logger.info(" dates from " + fromdat + " todate " + todat);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		// MID CHANGES
		String fpxMid = currentMerchant.getMid().getFpxMid();
		String ezyWayMid = currentMerchant.getMid().getEzywayMid();
		String motoMid = currentMerchant.getMid().getMotoMid();
		String boostMid = currentMerchant.getMid().getBoostMid();
		String grabMid = currentMerchant.getMid().getGrabMid();
		String tngMid = currentMerchant.getMid().getTngMid();
		String ShopeeMid = currentMerchant.getMid().getShoppyMid();
		String BnplMid = currentMerchant.getMid().getBnplMid();
		String ezyMid = currentMerchant.getMid().getMid();
		String ezyUmMid = currentMerchant.getMid().getUmMid();
		String ezywayUmMid = currentMerchant.getMid().getUmEzywayMid();
		String ezymotoUmMid = currentMerchant.getMid().getUmMotoMid();

		String mid = null;
		List<String> midList = new ArrayList<String>();

		if (ezyMid != null) {
			midList.add(ezyMid);
		}
		if (ezyUmMid != null) {
			midList.add(ezyUmMid);
		}
		if (ezyWayMid != null) {
			midList.add(ezyWayMid);
		}
		if (ezywayUmMid != null) {
			midList.add(ezywayUmMid);
		}
		if (ezymotoUmMid != null) {
			midList.add(ezymotoUmMid);
		}
		if (motoMid != null) {
			midList.add(motoMid);
		}
		if (boostMid != null) {
			midList.add(boostMid);
		}
		if (grabMid != null) {
			midList.add(grabMid);
		}
		if (fpxMid != null) {
			midList.add(fpxMid);
		}
		if (tngMid != null) {
			midList.add(tngMid);
		}
		if (ShopeeMid != null) {
			midList.add(ShopeeMid);
		}
		if (BnplMid != null) {
			midList.add(BnplMid);
		}

		String merchantname = currentMerchant.getBusinessName().toUpperCase().replace("\\", "").replace(" ", "")
				.replace("/", "");

		ArrayList<String> fsList = new ArrayList<String>();
		ArrayList<String> filenameList = new ArrayList<String>();

		logger.info("txnType is :- " + txnType);

		try {

			LocalDate start = LocalDate.parse(fromdat).minusDays(1);
			LocalDate end = LocalDate.parse(todat).minusDays(1);
			logger.info("Start date is :- " + start + " End date is:- " + end);

			while (!start.isAfter(end)) {
				logger.info("Inside While Loop ");
				start = start.plusDays(1);
				logger.info("date is:- " + start);
				DateTimeFormatter DatePattern = DateTimeFormatter.ofPattern("ddMMYYYY");
				String FormatDate = start.format(DatePattern);
				logger.info("Date After Formatting " + FormatDate);
				String file = null;
				String fileupdated = null;

				if (txnType.equalsIgnoreCase("FPX")) {
					logger.info("Inside Fpx");
					for (String s : midList) {
						System.out.println(s);
						String mid1 = s;

						logger.info("Mid value : " + mid1);
						file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\FpxPDF\\";
						fileupdated = file + mid1 + "MOBI_FPX_" + merchantname + "_" + FormatDate;

						File f = new File(fileupdated + ".pdf");
						logger.info(f);
						fileupdated = fileupdated.replace("\\", "-");
						fileupdated = fileupdated.replace(".", "--");
						logger.info("file name after replacing \\ with - " + fileupdated);
						if (f.exists()) {

							String filedate = new SimpleDateFormat("dd-MMM-yyyy")
									.format(new SimpleDateFormat("ddMMyyyy").parse(FormatDate));
							logger.info("filename date " + filedate);

							filenameList.add(filedate);
							fsList.add(fileupdated);

						} else {
							logger.info("no record present on " + fileupdated);
							model.addAttribute("responseData", "No Record found for Last month");
						}

					}
				}
				if (txnType.equalsIgnoreCase("BOOST")) {
					logger.info("Inside Boost");

					for (String s : midList) {
						System.out.println(s);
						String mid1 = s;

						logger.info("Mid value : " + mid1);
						file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\BoostPDF\\";
						fileupdated = file + mid1 + "MOBI_BOOST_" + merchantname + "_" + FormatDate;

						File f = new File(fileupdated + ".pdf");
						logger.info(f);
						fileupdated = fileupdated.replace("\\", "-");
						fileupdated = fileupdated.replace(".", "--");
						logger.info("file name after replacing \\ with - " + fileupdated);
						if (f.exists()) {

							String filedate = new SimpleDateFormat("dd-MMM-yyyy")
									.format(new SimpleDateFormat("ddMMyyyy").parse(FormatDate));
							logger.info("filename date " + filedate);

							filenameList.add(filedate);
							fsList.add(fileupdated);

						} else {
							logger.info("no record present on " + fileupdated);
							model.addAttribute("responseData", "No Record found for Last month");
						}

					}
				}
				if (txnType.equalsIgnoreCase("GRABPAY")) {
					logger.info("Inside Grabpay");
					for (String s : midList) {
						System.out.println(s);
						String mid1 = s;

						logger.info("Mid value : " + mid1);
						file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\GrabPDF\\";
						fileupdated = file + mid1 + "MOBI_GRAB_" + merchantname + "_" + FormatDate;

						File f = new File(fileupdated + ".pdf");
						logger.info(f);
//						fileupdated = fileupdated.replace("\\", "-");

						fileupdated = fileupdated.replace("\\", "*");
						fileupdated = fileupdated.replace(".", "--");
						logger.info("file name after replacing \\ with - " + fileupdated);
						if (f.exists()) {

							String filedate = new SimpleDateFormat("dd-MMM-yyyy")
									.format(new SimpleDateFormat("ddMMyyyy").parse(FormatDate));
							logger.info("filename date " + filedate);

							filenameList.add(filedate);
							fsList.add(fileupdated);

						} else {
							logger.info("no record present on " + fileupdated);
							model.addAttribute("responseData", "No Record found for Last month");
						}
					}
				}

				if (txnType.equalsIgnoreCase("TNG") || txnType.equalsIgnoreCase("SHOPEEPAY")) {
					logger.info("Inside M1_Pay");
					for (String s : midList) {
						System.out.println(s);
						String mid1 = s;

						logger.info("Mid value : " + mid1);
						file = "C:\\Mobi_config\\AutoRun\\m1PaySettlement\\SettlementClearence\\M1PayPDF\\";
						fileupdated = file + mid1 + "MOBI_"+txnType+"_" + merchantname + "_" + FormatDate;

						logger.info("Final path of the file : ---->"+fileupdated);


						File f = new File(fileupdated + ".pdf");
						logger.info(f);
						fileupdated = fileupdated.replace("\\", "-");
						fileupdated = fileupdated.replace(".", "--");
						logger.info("file name after replacing \\ with - " + fileupdated);
						if (f.exists()) {

							String filedate = new SimpleDateFormat("dd-MMM-yyyy")
									.format(new SimpleDateFormat("ddMMyyyy").parse(FormatDate));
							logger.info("filename date " + filedate);

							filenameList.add(filedate);
							fsList.add(fileupdated);

						} else {
							logger.info("no record present on " + fileupdated);
							model.addAttribute("responseData", "No Record found for Last month");
						}

					}
				}

				if (txnType.equalsIgnoreCase("BNPL")) {
					logger.info("Inside BNPL");
					for (String s : midList) {
						System.out.println(s);
						String mid1 = s;

						logger.info("Mid value : " + mid1);
						file = "C:\\Mobi_config\\AutoRun\\BnplSettlement\\SettlementClearence\\BnplPDF\\";
						fileupdated = file + mid1 + "MOBI_BNPL_" + merchantname + "_" + FormatDate;

						File f = new File(fileupdated + ".pdf");
						logger.info(f);
						fileupdated = fileupdated.replace("\\", "-");
						fileupdated = fileupdated.replace(".", "--");
						logger.info("file name after replacing \\ with - " + fileupdated);
						if (f.exists()) {

							String filedate = new SimpleDateFormat("dd-MMM-yyyy")
									.format(new SimpleDateFormat("ddMMyyyy").parse(FormatDate));
							logger.info("filename date " + filedate);

							filenameList.add(filedate);
							fsList.add(fileupdated);

						} else {
							logger.info("no record present on " + fileupdated);
							model.addAttribute("responseData", "No Record found for Last month");
						}

					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
		}

		for (String ss : fsList) {
			logger.info("File List is " + ss);
		}

		PaginationBean<Pdf> paginationBean = new PaginationBean<Pdf>();
		paginationBean.setItemListt(fsList);
		paginationBean.setFilenamelist(filenameList);
		logger.info("No of Records: " + paginationBean.getItemListt().size());
		logger.info("No of Records: " + paginationBean.getFilenamelist().size());
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// Ewallets and Fpx Daily Report PDF By Rama Krishna - Start (11-03-2021)

//	@RequestMapping(value = { "/dailypdf" }, method = RequestMethod.GET)
//    public String dailypdf(final Model model, final java.security.Principal principal,
//                  @RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
//           logger.info("first one");
//           PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/DailyPdf",
//                        Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//           HttpSession session = request.getSession();
//           String myName = (String) session.getAttribute("userName");
//           Merchant currentMerchant = merchantService.loadMerchant(myName);
//           String merchantname = currentMerchant.getBusinessName().toUpperCase().replace("\\", "").replace(" ", "")
//                        .replace("/", "");
//
//           String to = null;
//           Date dt1 = new Date();
//           SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
//           to = dateFormat1.format(dt1);
//           to = to.replace("-", "");
//           logger.info("date" + to);
//
//           String day = to.substring(0, 2);
//           String month = to.substring(2, 4);
//           String year = to.substring(4, 8);
//           int dayno;
//           dayno = Integer.valueOf(day);
//           dayno = dayno - 1;
//           int monthno = Integer.valueOf(month);
//           int yearno = Integer.valueOf(year);
//           logger.info("date format:" + dayno);
//           ArrayList<String> fsList = new ArrayList<String>();
//           ArrayList<String> filenameList = new ArrayList<String>();
//           for (int i = dayno - 2; i <= dayno; i++) {
//                  if (i < 10) {
//                        logger.info("inside Month Less Than 10");
//                        String d = "0" + i;
//                        String m = null;
//                        if (monthno < 10) {
//                               m = "0" + monthno;
//                        } else {
//                               m = month;
//                        }
//
//                        try {
//                               String file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\FpxPDF\\";
//                               String fileupdated = file + "MOBI_FPX_" + merchantname + "_" + d + m + year;
//                               File f = new File(fileupdated + ".pdf");
//                               logger.info(f);
//                               fileupdated = fileupdated.replace("\\", "-");
//                               fileupdated = fileupdated.replace(".", "--");
//                               logger.info("file name after replacing // with - " + fileupdated);
//                               if (f.exists()) {
//
//                                      String filedate = year + m + d;
//                                      filedate = new SimpleDateFormat("dd-MMM-yyyy")
//                                                    .format(new SimpleDateFormat("yyyyMMdd").parse(filedate));
//                                      logger.info("filename date " + filedate);
//                                      filenameList.add(filedate);
//                                      fsList.add(fileupdated);
//
//                               } else {
//                                      model.addAttribute("responseData", "No Record found for Last month");
//                               }
//                        } catch (Exception e) {
//                               e.printStackTrace();
//                        }
//                  } else {
//                        logger.info("inside else");
//                        String file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\FpxPDF\\";
//                        try {
//                               String m = null;
//                               if (monthno < 10) {
//                                      m = "0" + monthno;
//                               } else {
//                                      m = month;
//                               }
//
//                               String fileupdated = file + "MOBI_FPX_" + merchantname + "_" + i + m + year;
//                               File f = new File(fileupdated + ".pdf");
//                               logger.info(f);
//                               fileupdated = fileupdated.replace("\\", "-");
//                               fileupdated = fileupdated.replace(".", "--");
//                               logger.info("file name after replacing // with - " + fileupdated);
//
//                               if (f.exists()) {
//
//                                      String filedate = year + month + i;
//                                      filedate = new SimpleDateFormat("dd-MMM-yyyy")
//                                                    .format(new SimpleDateFormat("yyyyMMdd").parse(filedate));
//                                      logger.info("filename date " + filedate);
//
//                                      filenameList.add(filedate);
//                                      fsList.add(fileupdated);
//
//                               } else {
//                                      model.addAttribute("responseData", "No Record found for Last month");
//                               }
//                        } catch (Exception e) {
//                               e.printStackTrace();
//                        }
//                  }
//           }
//           for (String ss : fsList) {
//                  logger.info(ss);
//           }
//           PaginationBean<Pdf> paginationBean = new PaginationBean<Pdf>();
//           paginationBean.setItemListt(fsList);
//           paginationBean.setFilenamelist(filenameList);
//           logger.info("No of Records: " + paginationBean.getItemListt().size());
//           logger.info("No of file Records: " + paginationBean.getFilenamelist().size());
//           model.addAttribute("paginationBean", paginationBean);
//           model.addAttribute("pageBean", pageBean);
//
//           return TEMPLATE_MERCHANT;
//    }
//
//	@RequestMapping(value = { "/pdfdailysearch" }, method = RequestMethod.POST)
//	public String pdfdailysearch(final Model model, final java.security.Principal principal,
//			@RequestParam final String fromDate, @RequestParam final String toDate,
//			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
//			@RequestParam final String txnType) {
//		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/DailyPdf",
//				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//
//		String fromdat = null;
//		String todat = null;
//		String to = null;
//		String from = null;
//		Date frdate = new Date(fromDate);
//		int fromday = frdate.getDate();
//		int frommon = frdate.getMonth() + 1;
//		int fromyear1 = frdate.getYear();
//		int currentFrYear = fromyear1 + 1900;
//		String frmon = String.format("%02d", frommon);
//		String frday = String.format("%02d", fromday);
//
//		fromdat = String.valueOf(currentFrYear) + '-' + frmon + '-' + frday;
//		Date todate = new Date(toDate);
//		int today = todate.getDate();
//		int tomon = todate.getMonth() + 1;
//		int toyear1 = todate.getYear();
//		int currentToYear = toyear1 + 1900;
//		String tmon = String.format("%02d", tomon);
//		String tday = String.format("%02d", today);
//
//		todat = String.valueOf(currentToYear) + '-' + tmon + '-' + tday;
//
//		logger.info(" dates from " + fromdat + " todate " + todat);
//
//		HttpSession session = request.getSession();
//		String myName = (String) session.getAttribute("userName");
//		Merchant currentMerchant = merchantService.loadMerchant(myName);
//
//		String merchantname = currentMerchant.getBusinessName().toUpperCase().replace("\\", "").replace(" ", "")
//				.replace("/", "");
//
//		ArrayList<String> fsList = new ArrayList<String>();
//		ArrayList<String> filenameList = new ArrayList<String>();
//
//		logger.info("txnType is :- " + txnType);
//
//		try {
//
//			LocalDate start = LocalDate.parse(fromdat).minusDays(1);
//			LocalDate end = LocalDate.parse(todat).minusDays(1);
//			logger.info("Start date is :- " + start + " End date is:- " + end);
//
//			while (!start.isAfter(end)) {
//				logger.info("Inside While Loop ");
//				start = start.plusDays(1);
//				logger.info("date is:- " + start);
//				DateTimeFormatter DatePattern = DateTimeFormatter.ofPattern("ddMMYYYY");
//				String FormatDate = start.format(DatePattern);
//				logger.info("Date After Formatting " + FormatDate);
//				String file = null;
//				String fileupdated = null;
//
//				if (txnType.equalsIgnoreCase("FPX")) {
//					logger.info("Inside Fpx");
//					file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\FpxPDF\\";
//					fileupdated = file + "MOBI_FPX_" + merchantname + "_" + FormatDate;
//				}
//				if (txnType.equalsIgnoreCase("BOOST")) {
//					logger.info("Inside Boost");
//					file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\BoostPDF\\";
//					fileupdated = file + "MOBI_BOOST_" + merchantname + "_" + FormatDate;
//				}
//				if (txnType.equalsIgnoreCase("GRABPAY")) {
//					logger.info("Inside Grabpay");
//					file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\GrabPDF\\";
//					fileupdated = file + "MOBI_GRAB_" + merchantname + "_" + FormatDate;
//				}
//				
//			    if (txnType.equalsIgnoreCase("TNG") || txnType.equalsIgnoreCase("SHOPPY")) {
//                    logger.info("Inside M1_Pay");
//                    file = "C:\\Mobi_config\\AutoRun\\m1PaySettlement\\SettlementClearence\\M1PayPDF\\";
//                    fileupdated = file + "MOBI_TNG_SHOPEEPAY_" + merchantname + "_" + FormatDate;
//                }
//			    
//			    if (txnType.equalsIgnoreCase("BNPL")) {
//					logger.info("Inside BNPL");
//					file = "C:\\Mobi_config\\AutoRun\\BnplSettlement\\SettlementClearence\\BnplPDF\\";
//					fileupdated = file + "MOBI_BNPL_" + merchantname + "_" + FormatDate;
//				}
//				File f = new File(fileupdated + ".pdf");
//				logger.info(f);
//				fileupdated = fileupdated.replace("\\", "-");
//				fileupdated = fileupdated.replace(".", "--");
//				logger.info("file name after replacing \\ with - " + fileupdated);
//				if (f.exists()) {
//
//					String filedate = new SimpleDateFormat("dd-MMM-yyyy")
//							.format(new SimpleDateFormat("ddMMyyyy").parse(FormatDate));
//					logger.info("filename date " + filedate);
//
//					filenameList.add(filedate);
//					fsList.add(fileupdated);
//
//				} else {
//					logger.info("no record present on " + fileupdated);
//					model.addAttribute("responseData", "No Record found for Last month");
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.info(e);
//		}
//
//		for (String ss : fsList) {
//			logger.info("File List is " + ss);
//		}
//
//		PaginationBean<Pdf> paginationBean = new PaginationBean<Pdf>();
//		paginationBean.setItemListt(fsList);
//		paginationBean.setFilenamelist(filenameList);
//		logger.info("No of Records: " + paginationBean.getItemListt().size());
//		logger.info("No of Records: " + paginationBean.getFilenamelist().size());
//		model.addAttribute("paginationBean", paginationBean);
//		model.addAttribute("pageBean", pageBean);
//
//		return TEMPLATE_MERCHANT;
//	}

	@RequestMapping(value = { "/getdailypdf/{name}" }, method = RequestMethod.GET)
	public String getdailypdf(final Model model, final java.security.Principal principal, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage, @PathVariable final String name,
			HttpServletResponse response) {
		logger.info("Request to display text name: " + name);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String name2 = null;
		try {
			logger.info("filepathname is :- " + name);
			String name1 = name.replace("--", ".");

			if (name1.contains("*")) {
				name2 = name1.replace("*", "\\");
				logger.info("filepathname GRAB is :- " + name1);
			}

			else {
				name2 = name1.replace("-", "\\");
				logger.info("filepathname is :- " + name1);
			}

			logger.info("File Name After Replacing:- " + name2);
			String filepath = name2 + ".pdf";

			logger.info("filepathname after adding extension is :- " + filepath);
			Path pdfPath = Paths.get(filepath);
			byte[] documentInBytes = Files.readAllBytes(pdfPath);
			response.setHeader("Content-Disposition", "inline; filename=\"report.pdf\"");
			response.setDateHeader("Expires", -1);
			response.setContentType("application/pdf");
			response.setContentLength(documentInBytes.length);
			response.getOutputStream().write(documentInBytes);
		} catch (Exception ex) {
			logger.info(ex);
		} finally {
		}
		return null;
	}

// Ewallets and Fpx Daily Report PDF By Rama Krishna - End (11-03-2021)

//Rk - Settlement Pdf End                 

	// EARLY SETTLEMENT PROCESS - START ON 31 - 01 - 2021

	@RequestMapping("/Addwithdraw")
	public @ResponseBody EarlySettlementModel viewPayoutbyid(@RequestParam String ipdateone,
			@RequestParam String ipnetamtone, @RequestParam String ipdatetwo, @RequestParam String ipnetamttwo,
			@RequestParam String ipdatethree, @RequestParam String ipnetamtthree, @RequestParam String ipdatefour,
			@RequestParam String ipnetamtfour, @RequestParam String ipdatefive, @RequestParam String ipnetamtfive,
			HttpServletRequest request, HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("ipdateone: " + ipdateone);
		logger.info("ipnetamtone: " + ipnetamtone);
		logger.info("ipdatetwo: " + ipdatetwo);
		logger.info("ipnetamttwo: " + ipnetamttwo);
		logger.info("ipdatethree: " + ipdatethree);
		logger.info("ipnetamtthree: " + ipnetamtthree);
		logger.info("ipdatefour: " + ipdatefour);
		logger.info("ipnetamtfour: " + ipnetamtfour);
		logger.info("ipdatefive: " + ipdatefive);
		logger.info("ipnetamtfive: " + ipnetamtfive);

		EzysettleUtils ezysettleUtils = new EzysettleUtils();
		Utils utils = new Utils();
		// MDR Calc For Settlement of 5 dates

		double settleonenetamount = 0;
		double settletwonetamount = 0;
		double settlethreenetamount = 0;
		double settlefournetamount = 0;
		double settlefivenetamount = 0;

		double withdrawfeeone = 0;
		double withdrawfeetwo = 0;
		double withdrawfeethree = 0;
		double withdrawfeefour = 0;
		double withdrawfeefive = 0;

//		withdrawfeeone = ezysettleUtils.calculateWithdrawalFee(ipdateone, ipnetamtone, 0.25);
//		withdrawfeetwo = ezysettleUtils.calculateWithdrawalFee(ipdatetwo, ipnetamttwo, 0.50);
//		withdrawfeethree = ezysettleUtils.calculateWithdrawalFee(ipdatethree, ipnetamtthree, 0.75);
//		withdrawfeefour = ezysettleUtils.calculateWithdrawalFee(ipdatefour, ipnetamtfour, 1.00);
//		withdrawfeefive = ezysettleUtils.calculateWithdrawalFee(ipdatefive, ipnetamtfive, 1.25);

		if (ipdateone != null && !ipdateone.isEmpty()) {
			String calcipnetamtone = null;
			calcipnetamtone = ipnetamtone.replace(",", "");
			settleonenetamount = Double.parseDouble(calcipnetamtone);
			withdrawfeeone = settleonenetamount * 0.25 / 100;
		}
		if (ipdatetwo != null && !ipdatetwo.isEmpty()) {
			String calcipnetamttwo = null;
			calcipnetamttwo = ipnetamttwo.replace(",", "");
			settletwonetamount = Double.parseDouble(calcipnetamttwo);
			withdrawfeetwo = settletwonetamount * 0.50 / 100;
		}
		if (ipdatethree != null && !ipdatethree.isEmpty()) {

			String calcipnetamtthree = null;
			calcipnetamtthree = ipnetamtthree.replace(",", "");
			settlethreenetamount = Double.parseDouble(calcipnetamtthree);
			withdrawfeethree = settlethreenetamount * 0.75 / 100;
		}
		if (ipdatefour != null && !ipdatefour.isEmpty()) {

			String calcipnetamtfour = null;
			calcipnetamtfour = ipnetamtfour.replace(",", "");
			settlefournetamount = Double.parseDouble(calcipnetamtfour);
			withdrawfeefour = settlefournetamount * 1.00 / 100;

		}
		if (ipdatefive != null && !ipdatefive.isEmpty()) {

			String calcipnetamtfive = null;
			calcipnetamtfive = ipnetamtfive.replace(",", "");
			settlefivenetamount = Double.parseDouble(calcipnetamtfive);
			withdrawfeefive = settlefivenetamount * 1.25 / 100;
		}

		logger.info("after if settleonenetamount: " + settleonenetamount);
		logger.info("after if settletwonetamount: " + settletwonetamount);
		logger.info("after if settlethreenetamount: " + settlethreenetamount);
		logger.info("after if settlefournetamount: " + settlefournetamount);
		logger.info("after if settlefivenetamount: " + settlefivenetamount);

		logger.info("after if withdrawfeeone: " + withdrawfeeone);
		logger.info("after if withdrawfeetwo: " + withdrawfeetwo);
		logger.info("after if withdrawfeethree: " + withdrawfeethree);
		logger.info("after if withdrawfeefour: " + withdrawfeefour);
		logger.info("after if withdrawfeefive: " + withdrawfeefive);

		double FinalSumofPayableAmount = settleonenetamount + settletwonetamount + settlethreenetamount
				+ settlefournetamount + settlefivenetamount;
		logger.info("FinalSumofPayableAmount " + FinalSumofPayableAmount);

		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String SumofPayableAmount = myFormatter.format(FinalSumofPayableAmount);

		double FinalSumofWithdrawfeeAmount = withdrawfeeone + withdrawfeetwo + withdrawfeethree + withdrawfeefour
				+ withdrawfeefive;

		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);

		String SumofWithdrawfeeAmount = df.format(FinalSumofWithdrawfeeAmount);

		logger.info("FinalSumofWithdrawfeeAmount " + FinalSumofWithdrawfeeAmount);

		double FinalTotalofNetAmount = FinalSumofPayableAmount - FinalSumofWithdrawfeeAmount;
		logger.info("FinalTotalofNetAmount " + FinalTotalofNetAmount);

		String TotalofNetAmount = myFormatter.format(FinalTotalofNetAmount);

		EarlySettlementModel payouttrxdata = new EarlySettlementModel();

		payouttrxdata.setPayableAmount(SumofPayableAmount);

		double finalTotalofNetAmount1 = 0.0;
		double totalPayableAmount = 0.0;

		logger.info("FinalSumofPayableAmount : " + FinalSumofPayableAmount);
		logger.info("FinalSumofPayableAmount : " + FinalSumofPayableAmount);
		logger.info("FinalTotalofNetAmount : " + finalTotalofNetAmount1);
		logger.info("FinalSumofWithdrawfeeAmount : " + FinalSumofWithdrawfeeAmount);

		if (FinalSumofWithdrawfeeAmount <= 25.00) {
			logger.info("Ezysettle Withdraw fee is lesser than 25.00 RM");

			double withdrawFee = 25.00;
			finalTotalofNetAmount1 = FinalSumofPayableAmount - 25.00;

			String payableAmount = utils.amountFormatWithoutcomma(finalTotalofNetAmount1);

			finalTotalofNetAmount1 = Double.parseDouble(payableAmount);

			logger.info("payableAmount: " + payableAmount);
			logger.info("FinalTotalofNetAmount: " + finalTotalofNetAmount1);

			double slab = ezysettleUtils.findSlab(finalTotalofNetAmount1);
			logger.info("Slab: " + slab);

			double afterDeductCurlecFee = 0.0;
			ReuseDto reuseDto = new ReuseDto();

			if (finalTotalofNetAmount1 > 5000.0) {
//				reuseDto = ezysettleUtils.curlecFeeError(finalTotalofNetAmount1, slab);
				reuseDto = ezysettleUtils.curlecFeeError(finalTotalofNetAmount1, 0.0);
				afterDeductCurlecFee = reuseDto.getAfterDeductCurlecFee();
			} else {
//				afterDeductCurlecFee = finalTotalofNetAmount1 - slab;
				afterDeductCurlecFee = finalTotalofNetAmount1 - 0.0;
			}
			logger.info("afterDeductCurlecFee After all manipulation compelete:" + afterDeductCurlecFee);

			String totalofNetAmount = myFormatter.format(afterDeductCurlecFee);
			logger.info("totalofNetAmount2: " + totalofNetAmount);

			String enableEzysettle = ezysettleUtils
					.ezysettleEnable(Double.parseDouble(totalofNetAmount.replace(",", "")));
			logger.info("EnableEzysettle : " + enableEzysettle);
			payouttrxdata.setEzysettleEligible(enableEzysettle);
			payouttrxdata.setNetAmount(myFormatter.format(Double.parseDouble(amountRoundofFormat(totalofNetAmount))));
			payouttrxdata.setWithdrawfeeAmount(
					myFormatter.format(Double.parseDouble(amountRoundofFormat(String.valueOf(withdrawFee)))));
			payouttrxdata.setCurlecRequestAmount(myFormatter
					.format(Double.parseDouble(amountRoundofFormat(String.valueOf(finalTotalofNetAmount1)))));
			payouttrxdata.setCurlecFee(df.format(slab));

		} else {
			logger.info("Ezysettle Withdraw fee is Greater than 25.00 RM");

			finalTotalofNetAmount1 = FinalSumofPayableAmount - FinalSumofWithdrawfeeAmount;
			logger.info("FinalTotalofNetAmount: " + finalTotalofNetAmount1);
			DecimalFormat formater = new DecimalFormat("##0.00");
			String payableAmount = formater.format(finalTotalofNetAmount1);

			finalTotalofNetAmount1 = Double.parseDouble(payableAmount);
			logger.info("FinalTotalofNetAmount  : " + finalTotalofNetAmount1);

			double slab = ezysettleUtils.findSlab(finalTotalofNetAmount1);

//			totalPayableAmount = finalTotalofNetAmount1 - slab;
			totalPayableAmount = finalTotalofNetAmount1 - 0.0;
			logger.info("TotalPayableAmount: " + totalPayableAmount);

			ReuseDto reuseDto = new ReuseDto();
			double afterDeductCurlecFee = 0.0;
			if (finalTotalofNetAmount1 > 5000.0) {
//				reuseDto = ezysettleUtils.curlecFeeError(finalTotalofNetAmount1, slab);
				reuseDto = ezysettleUtils.curlecFeeError(finalTotalofNetAmount1, 0.0);
				afterDeductCurlecFee = reuseDto.getAfterDeductCurlecFee();
			} else {
//				afterDeductCurlecFee = finalTotalofNetAmount1 - slab;
				afterDeductCurlecFee = finalTotalofNetAmount1 - 0.0;
			}

			String totalofNetAmount = myFormatter.format(afterDeductCurlecFee);
			logger.info("FinalTotalofNetAmount after deduct: " + totalofNetAmount);

			String enableEzysettle = ezysettleUtils
					.ezysettleEnable(Double.parseDouble(totalofNetAmount.replace(",", "")));
			logger.info("EnableEzysettle : " + enableEzysettle);
			payouttrxdata.setEzysettleEligible(enableEzysettle);

			payouttrxdata.setNetAmount(myFormatter.format(Double.parseDouble(amountRoundofFormat(totalofNetAmount))));
			payouttrxdata.setWithdrawfeeAmount(myFormatter
					.format(Double.parseDouble(amountRoundofFormat(String.valueOf(FinalSumofWithdrawfeeAmount)))));
			payouttrxdata.setCurlecRequestAmount(myFormatter
					.format(Double.parseDouble(amountRoundofFormat(String.valueOf(finalTotalofNetAmount1)))));
			payouttrxdata.setCurlecFee(df.format(slab));
		}

		Calendar now = Calendar.getInstance();
		Locale malaysianLocale = new Locale("ms", "MY");
		String pattern1 = "dd-MMM-yyyy HH:mm:ss";
		String pattern2 = "dd-MMM-yyyy";
		String pattern3 = "HH:mm:ss";

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern1);
		SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
		SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat(pattern3);

		TimeZone malaysianTimeZone = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
		simpleDateFormat.setTimeZone(malaysianTimeZone);
		simpleDateFormat2.setTimeZone(malaysianTimeZone);
		simpleDateFormat3.setTimeZone(malaysianTimeZone);

		logger.info("Current Malaysia Date and Time = " + simpleDateFormat.format(now.getTime()));

		String payoutDate = simpleDateFormat.format(now.getTime());

		logger.info("payoutDate = " + payoutDate);

		String currentdate = null;

		try {
			currentdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(payoutDate));

		} catch (ParseException e) {
			logger.info("Exception : " + e.getMessage());

			e.printStackTrace();
		}
		// Taking PayoutTime and CurrentTime
		String payoutTime = getPayoutTime();
		String currentTime = simpleDateFormat3.format(now.getTime());

		// Convert String to LocalDate
//		LocalDate CurrentDate = LocalDate.parse(currentdate);
		LocalTime LocalpayoutTime = LocalTime.parse(payoutTime);
		LocalTime LocalcurrentTime = LocalTime.parse(currentTime);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime CurrentDate = LocalDateTime.parse(currentdate, formatter);

		// new added
		LocalDateTime FinalPayoutDate = CurrentDate;
		String finalPayoutDate = FinalPayoutDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"));
		payouttrxdata.setPayoutDate(finalPayoutDate);
		logger.info(" payout Date : " + payouttrxdata.getPayoutDate());
		String paydate = finalPayoutDate.replace("-", " ");
		logger.info(" payoutTime = " + paydate);
		payouttrxdata.setPaydate(paydate);

		logger.info("Local payoutTime = " + LocalpayoutTime);
		logger.info("Local currentTime = " + LocalcurrentTime);

		return payouttrxdata;

	}

	@RequestMapping(value = "/Addpayoutdata")
	public @ResponseBody ESwithdraw Addpayoutdata(@RequestParam String FinalPayableAmt,
			@RequestParam String FinalWithdrawfee, @RequestParam String FinalNetamt,
			@RequestParam String FinalPayoutdate, @RequestParam String Finalipdateone,
			@RequestParam String Finalipdatetwo, @RequestParam String Finalipdatethree,
			@RequestParam String Finalipdatefour, @RequestParam String Finalipdatefive,
			@RequestParam String curlecRequestAmount, @RequestParam String finalCurlecFee, HttpServletRequest request,
			HttpServletResponse response, Model model) throws ParseException {
		HttpSession session = request.getSession();
		List<PayeeDetails> payeeList = null;
		List<Payer> payerList = new ArrayList<Payer>();

		EzysettleUtils ezysettleUtils = new EzysettleUtils();
		StringBuffer response1 = new StringBuffer();
		String myName = (String) session.getAttribute("userName");
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		logger.info("FinalPayableAmt: " + FinalPayableAmt);
		logger.info("FinalWithdrawfee: " + FinalWithdrawfee);
		logger.info("FinalNetamt: " + FinalNetamt);
		logger.info("FinalPayoutdate: " + FinalPayoutdate);

		logger.info("Finalipdateone: " + Finalipdateone);
		logger.info("Finalipdatetwo: " + Finalipdatetwo);
		logger.info("Finalipdatethree: " + Finalipdatethree);
		logger.info("Finalipdatefour: " + Finalipdatefour);
		logger.info("Finalipdatefive: " + Finalipdatefive);

		logger.info("finalCurlecFee: " + finalCurlecFee);

		synchronized (lock) {
			JustSettle checkMid = merchantService.findByMidandDate(currentMerchant);
			logger.info("Finalipdatefive: " + Finalipdatefive);
			boolean restrictDoubleEntry = true;
			if (checkMid != null) {
				restrictDoubleEntry = deniedDobleEntry(checkMid.getTimeStamp());
			}

			if (restrictDoubleEntry) {
				// For Withdraw Date and Settlement Date - Start
				Calendar now = Calendar.getInstance();
				Locale malaysianLocale = new Locale("ms", "MY");
				String pattern1 = "yyyy-MM-dd HH:mm:ss";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern1);
				TimeZone malaysianTimeZone = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
				simpleDateFormat.setTimeZone(malaysianTimeZone);
				logger.info("Current Malaysia Date and Time = " + simpleDateFormat.format(now.getTime()));
				String WithdrawDate = simpleDateFormat.format(now.getTime());

				logger.info("WithdrawDate TimeStamp Format => " + WithdrawDate);
				String finalPayoutDate = null;
				try {
					finalPayoutDate = new SimpleDateFormat(pattern1)
							.format(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(FinalPayoutdate));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				logger.info("finalPayoutDate TimeStamp Format = " + finalPayoutDate);

				String date_s = finalPayoutDate;
				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = dt.parse(date_s);
				SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				logger.info("output dt 1:" + dt1.format(date));
				String outputDateString = dt1.format(date);

				logger.info("outputDateString :" + outputDateString);

				Date currentDate = new Date();
				String filePrtn = ezysettleUtils.CSVFilePatn();
				// For Withdraw Date and Settlement Date - End
				logger.info("file Name : :" + filePrtn);
				String csvFile = filePrtn;
				// FinalNetAmt , FinalPayableAmt , FinalWithdrawfee - Remove (comma separator)
				String today1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(currentDate);

				String RepFinalNetamt = FinalNetamt.replace(",", "");
				String RepFinalWithdrawfee = FinalWithdrawfee.replace(",", "");
				String RepFinalPayableAmt = FinalPayableAmt.replace(",", "");

				String ezysettleReferenceNo = ezysettleUtils.ezysettleReferenceNoGenerate();

				EarlySettlementModel updateinjustsettle = new EarlySettlementModel();
				synchronized (updateinjustsettle) {

					updateinjustsettle.setUpdatepayableAmount(RepFinalNetamt);
					updateinjustsettle.setUpdatewithdrawfeeAmount(RepFinalWithdrawfee);
					updateinjustsettle.setUpdatenetAmount(RepFinalPayableAmt);
					updateinjustsettle.setWithdrawDate(WithdrawDate);
					updateinjustsettle.setStatus("S");
					updateinjustsettle.setSettlementDate(date_s);
					// need to add curlec request amount
					logger.info("curlecRequestAmount :" + curlecRequestAmount);
					updateinjustsettle.setCurlecRequestAmount(curlecRequestAmount);
					updateinjustsettle.setCurlecFee(finalCurlecFee);

					updateinjustsettle.setEzysettleReferenceNo(ezysettleReferenceNo);

					logger.info("Final Withdrawfee :" + RepFinalWithdrawfee);
					logger.info("Final FinalNetamt :" + RepFinalNetamt);
					logger.info("Final FinalWithdrawfee :" + RepFinalWithdrawfee);
					logger.info("Final FinalWthdrawFee :" + RepFinalWithdrawfee);
					logger.info("Final FinalWithdrawfee :" + RepFinalWithdrawfee);
					// Saving Just Settle Table Data
					transactionService.updateJustsettletable(updateinjustsettle, currentMerchant);

				}

				List<String> requestSettlementDateList = Arrays.asList(Finalipdateone, Finalipdatetwo, Finalipdatethree,
						Finalipdatefour, Finalipdatefive);

				requestSettlementDateList.forEach(a -> logger.info("Request Settlement Date List : " + a));

				/* validateMerchantAmount */
				boolean valid = ezysettleUtils.validateMerchantAmountBeforePayoutInitiate(curlecRequestAmount,
						requestSettlementDateList, currentMerchant, RepFinalPayableAmt, ezysettleDao);
				logger.info("Valid merchant Amount and Request Amount : " + valid);
				if (valid) {

					List<UpdateInfoEzysettle> updateInfos = Arrays.asList(new UpdateInfoEzysettle(Finalipdateone, 0.25),
							new UpdateInfoEzysettle(Finalipdatetwo, 0.50),
							new UpdateInfoEzysettle(Finalipdatethree, 0.75),
							new UpdateInfoEzysettle(Finalipdatefour, 1.00),
							new UpdateInfoEzysettle(Finalipdatefive, 1.25));

					List<Exception> exceptions = Collections.synchronizedList(new ArrayList<>());

					updateInfos.stream().filter(
							updateInfo -> updateInfo.getFinalipdate() != null && !updateInfo.getFinalipdate().isEmpty())
							.parallel().forEach(updateInfo -> {
								/* need to check trows exception */

								try {
									logger.info(updateInfo.getFinalipdate() + " day");
									logger.info(updateInfo.getEzysettleFee() + " Ezysettle");
									boolean updateTransactionDetails = transactionService.updatesettledate(
											updateInfo.getFinalipdate(), updateInfo.getEzysettleFee(), currentMerchant);

//									boolean boostStatus = transactionService.updatesettledatebyBoost(
//											updateInfo.getFinalipdate(), updateInfo.getEzysettleFee(), currentMerchant);
//									boolean grabStatus = transactionService.updatesettledatebyGrabpay(
//											updateInfo.getFinalipdate(), updateInfo.getEzysettleFee(), currentMerchant);
//									boolean fpxStatus = transactionService.updatesettledatebyFpx(
//											updateInfo.getFinalipdate(), updateInfo.getEzysettleFee(), currentMerchant);
//									boolean m1PayStatus = transactionService.updatesettledatebym1Pay(
//											updateInfo.getFinalipdate(), updateInfo.getEzysettleFee(), currentMerchant);
//
//									if (cardStatus && boostStatus && grabStatus && fpxStatus && m1PayStatus) {
//										logger.info("All transaction validations are correct. Proceeding to payout.");
//									} else {
//										logger.error("Transaction validation failed:");
//										if (!cardStatus)
//											logger.error("Card transaction validation failed.");
//										if (!boostStatus)
//											logger.error("Boost transaction validation failed.");
//										if (!grabStatus)
//											logger.error("Grabpay transaction validation failed.");
//										if (!fpxStatus)
//											logger.error("FPX transaction validation failed.");
//										if (!m1PayStatus)
//											logger.error("M1 Pay transaction validation failed.");
//										throw new RuntimeException();
//									}
								} catch (Exception e) {
									logger.error("Exception occurred while processing updateInfo: " + updateInfo, e);
									exceptions.add(e);
								}

							});

					if (!exceptions.isEmpty()) {
						logger.error("Exception occurred while processing updateInfo ");
						ESwithdraw eSwithdraw1 = new ESwithdraw();
						eSwithdraw1.setEzysettleVaild("Exception");
						return eSwithdraw1;
					}
				} else {
					logger.error(
							"The Merchant Request Amount and the Merchant Settlement Amount in the database do not match.");
					ESwithdraw eSwithdraw1 = new ESwithdraw();
					eSwithdraw1.setEzysettleVaild("No");
					return eSwithdraw1;
				}

				CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {

					String payoutDate = null;
					try {
						/* it takes Current date */
						payoutDate = new SimpleDateFormat(pattern1)
								.format(new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(FinalPayoutdate));
					} catch (ParseException e1) {
						e1.printStackTrace();
						logger.error("Date format : "+e1.getMessage());
					}
					
					// String payerTransactionLoadDateFormat = null;
					// try {
					// 	/* it takes Current date */
					// 	payerTransactionLoadDateFormat = new SimpleDateFormat(pattern1)
					// 			.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(FinalPayoutdate));
					// } catch (ParseException e1) {
					// 	e1.printStackTrace();
					// 	logger.error("Date format : "+e1.getMessage());
					// }

					Payer pAmount = null;
					pAmount = merchantService.getPayerAmount(payoutDate, currentMerchant, "ezysettleIntiate");

					// tread1
					Justsettle just = null;
					EzysettleUtils utils = new EzysettleUtils();
					Utils util = new Utils();
					if (!pAmount.getTotalNetAmount().equals("0.00")) {
						logger.info("payerAmt:" + pAmount);

						try {
							logger.info("To Take Payout Date:" + payoutDate);
							PayeeDetails payee = null;

							/* Convert list data to Data Transfer Object (DTO) */
							payee = merchantService.getPayeeDetails(payoutDate, currentMerchant, "ezysettleIntiate");

							logger.info("Finalipdateone: " + Finalipdateone);
							logger.info("Finalipdatetwo: " + Finalipdatetwo);
							logger.info("Finalipdatethree: " + Finalipdatethree);
							logger.info("Finalipdatefour: " + Finalipdatefour);
							logger.info("Finalipdatefive: " + Finalipdatefive);

							String ambank = PropertyLoader.getFileData().getProperty("ENABLE_AMBANK");
							logger.info("Enable Ambank : " + ambank);
							logger.info("Ezysettle Call to Payout Bank API");
							if (ambank.equalsIgnoreCase("yes")) {
								logger.info("Ambank is Enable ");
								just = util.amBankBankApi(payee, currentMerchant, payoutDate, curlecRequestAmount);
							} else {
								logger.info("CurlecBank is Enable ");
								just = util.curlecBankApi(payee, currentMerchant, payoutDate, curlecRequestAmount,
										ezysettleReferenceNo);
							}

							int count = 0;
							// Check response code and trigger CSV generation if necessary
							if (just.getResponseCode().equals("0001")) {

								logger.info("Transaction Info - Count: " + count
										+ ", Failure Reason: Bank Failure / Ezysettle Failure"
										+ ", External API Response Code: " + just.getResponseCode());

								// Backend Exception During that time trigger CSV to finance team
								utils.ezysettleCsvAndFinalStatusUpdate(payoutDate, currentMerchant, merchantService,
										today1, csvFile, just, ezysettleReferenceNo, merchantDao);
							} else if (just.getResponseCode().equals("0002")) {

								logger.info("Transaction Status - Count: " + count
										+ ", Status: Pending from Bank, Type: EzySettle" + just.getResponseCode());

								// Backend Exception During that time trigger CSV to finance team
								utils.ezysettleCsvAndFinalStatusUpdate(payoutDate, currentMerchant, merchantService,
										today1, csvFile, just, ezysettleReferenceNo, merchantDao);
							} else {
								/* After a successful EzySettle, send an email to finance. */
								logger.info("EzySettle Success - Response Code: "+ just.getResponseCode());
								utils.generateCSVForSuccessfulEzySettle(payoutDate, currentMerchant, merchantService,
										today1, csvFile);
								logger.info("External Api Response Code 0000");
							}

						} catch (Exception e) {

							logger.error("Exception Message final status update : " + e.getStackTrace());

							// Get the stack trace
							StackTraceElement[] stackTrace = e.getStackTrace();

							// Iterate over each element and print the details
							for (StackTraceElement element : stackTrace) {
								logger.error("Class: " + element.getClassName());
								logger.error("Method: " + element.getMethodName());
								logger.error("File: " + element.getFileName());
								logger.error("Line: " + element.getLineNumber());
								logger.info("");
							}

							/* Backend Exception During that time trigger CSV to finance team */
							utils.ezysettleCsvAndFinalStatusUpdate(payoutDate, currentMerchant, merchantService, today1,
									csvFile, just, ezysettleReferenceNo, merchantDao);

							logger.error("Exception Message final status update : " + e.getMessage());
							e.getStackTrace();
						}

					} else {
						logger.info("No Record Found");
					}

				});

				ESwithdraw eSwithdraw = new ESwithdraw();
				String sucessdate = FinalPayoutdate.replace("-", " ");
				logger.info("sucessdate :" + sucessdate);
				eSwithdraw.setSucessdate(sucessdate);
				eSwithdraw.setFinalNetamt(FinalNetamt);
				eSwithdraw.setEzysettleVaild("Yes");
				return eSwithdraw;
			} else {

				logger.info("Failure : Due to a double entry issue in Ezysettle.");
				ESwithdraw eSwithdraw1 = new ESwithdraw();
				eSwithdraw1.setEzysettleVaild("No");
				return eSwithdraw1;
			}

		}
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

	// WithDraw

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

	public static String getupdateEzysettleFinalStatus() {
		// To get path
		Properties prop = new Properties();
		InputStream input = null;
		String path = null;
		try {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			logger.info("Path :" + prop.getProperty("updateEzysettleAmbankFinalStatus"));
			path = prop.getProperty("updateEzysettleAmbankFinalStatus");
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

	public void sendEmail(String filename) {
		logger.info("Sending UM Clearance csv Mail " + filename);
		List<NameValuePair> headers = new ArrayList<NameValuePair>();
		headers.add(new NameValuePair("HEADER", "test"));
		String fromAddress = PropertyLoader.getFile().getProperty("FROMMAIL");
		String apiKey = PropertyLoader.getFile().getProperty("APIKEY");
		String toAddress = PropertyLoader.getFile().getProperty("JS_CSV_MAIL_TO");
		String ccAddress = PropertyLoader.getFile().getProperty("JS_CSV_MAIL_CC");
		String subject = PropertyLoader.getFile().getProperty("JS_CSV_SUBJECT")
				+ new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		logger.info("Sending Email to :" + toAddress + " : " + ccAddress);
		Attachment fileAttach = null;
		try {
			String filePath = PropertyLoader.getFile().getProperty("JSCSV_CLEARENCEFILEPATH");
			String fileName = filename;
			fileAttach = new Attachment(fileName, "application/csv", encodeFileToBase64Binary(filePath + fileName),
					"cid:" + fileName);
		} catch (Exception e) {
			logger.info("Exception FilePath in : " + e.getMessage());
			logger.info("Exception FilePath in : " + e.getStackTrace());
		}
		List<Attachment> attachments = new ArrayList<Attachment>();
		attachments.add(fileAttach);
		String fromName = PropertyLoader.getFile().getProperty("FROMNAME");
		String textbody = PropertyLoader.getFile().getProperty("TEXT_BODY");
		String attachment = PropertyLoader.getFile().getProperty("JSCSV_CLEARENCEFILEPATH") + filename;
		String textBody = "Hi Finance Team," + "\n\n" + textbody + " "
				+ new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
		ElasticEmail message = new ElasticEmail(fromAddress, subject, fromName, toAddress, ccAddress, ccAddress,
				attachment, textBody);
		ElasticEmailClient client = new ElasticEmailClient();

		try {
			client.sendMessage(message);
			logger.info("Email Sent Successfully to " + toAddress);
		} catch (Exception pe) {
			logger.info("Invalid Signature Base64 String");
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

	public static String getPayoutTime() {

		Properties prop = new Properties();
		InputStream input = null;
		String path = null;

		try {

			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			input = loader.getResourceAsStream("/config.properties");
			prop.load(input);
			logger.info("PAYOUT_TIME :" + prop.getProperty("PAYOUT_TIME"));
			path = prop.getProperty("PAYOUT_TIME");
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

	@RequestMapping("/Addcountbypaydee")
	public @ResponseBody String addpaydeecount(@RequestParam long count, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		logger.info("About to Addcountbypaydee : " + count);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		String currentcount = currentMerchant.getJustsettle_swtcount();
		String currentid = String.valueOf(currentMerchant.getId());

		logger.info("currentcount : " + currentcount);
		logger.info("currentid : " + currentid);

		long lcount = Long.parseLong(currentcount);

		logger.info("lcount : " + lcount);

		long TotalCount = count + lcount;

		logger.info("TotalCount : " + TotalCount);

		String sTotalCount = String.valueOf(TotalCount);
		transactionService.updateMerchantCount(sTotalCount, currentid);

//                        		if (TotalCount > 5) {
		//
//                        			String subject = currentMerchant.getBusinessName();
//                        			String Description = "Triggered by Merchant Above 5 Count";
		//
//                        			List<NameValuePair> headers = new ArrayList<NameValuePair>();
//                        			headers.add(new NameValuePair("HEADER", "test"));
//                        			String fromAddress = PropertyLoader.getFile().getProperty("FROMMAIL");
//                        			String apiKey = PropertyLoader.getFile().getProperty("APIKEY");
//                        			String toAddress = "santhosh@gomobi.io,dhinesh.m@gomobi.io";
//                        			String ccAddress = "sangeetha@gomobi.io";
//                        			String subject1 = subject;
		//
//                        			StringBuffer tm = new StringBuffer();
//                        			tm.append("Message Detail : ");
//                        			tm.append("\n\n");
//                        			tm.append("" + Description);
//                        			String emailBody = tm.toString();
//                        			logger.info(emailBody);
//                        			logger.info("Sending Email to :" + toAddress + " : " + ccAddress);
		//
//                        			PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress, fromAddress, ccAddress, subject1,
//                        					emailBody, false, "test-email", null);
		//
//                        			PostmarkClient client = new PostmarkClient(apiKey);
		//
//                        			try {
//                        				client.sendMessage(message);
//                        				logger.info("Email Sent Successfully to " + toAddress);
//                        			} catch (PostmarkException pe) {
//                        				logger.info("Invalid Signature Base64 String");
		//
//                        			}
		//
//                        		}

		String result = null;

		return result;
	}

	// Check_Box Count Function

	@RequestMapping("/Addcountbycheckbox")
	public @ResponseBody String addcheckboxcount(@RequestParam long count, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		logger.info("About to Addcountbycheckbox : " + count);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		String currentcount = currentMerchant.getJsCheckCount();
		String currentid = String.valueOf(currentMerchant.getId());

		logger.info("currentcount : " + currentcount);
		logger.info("currentid : " + currentid);

		long lcount = Long.parseLong(currentcount);

		logger.info("lcount : " + lcount);

		long TotalCount = count + lcount;

		logger.info("TotalCount : " + TotalCount);

		String sTotalCount = String.valueOf(TotalCount);
		transactionService.updateCheckboxCount(sTotalCount, currentid);

		String result = null;

		return result;
	}

	// Withdraw_Count Function

	@RequestMapping("/Addcountbywithdraw")
	public @ResponseBody String addwithdrawcount(@RequestParam long count, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		logger.info("About to Addcountbywithdraw : " + count);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		String currentcount = currentMerchant.getJsWithdrawCount();
		String currentid = String.valueOf(currentMerchant.getId());

		logger.info("currentcount : " + currentcount);
		logger.info("currentid : " + currentid);

		long lcount = Long.parseLong(currentcount);

		logger.info("lcount : " + lcount);

		long TotalCount = count + lcount;

		logger.info("TotalCount : " + TotalCount);

		String sTotalCount = String.valueOf(TotalCount);
		transactionService.updateWithdrawCount(sTotalCount, currentid);

		String result = null;

		return result;
	}

	// Ewallets and Fpx Daily Report PDF By Rama Krishna - Start (11-03-2021)

	@RequestMapping(value = { "/dailypdfOld" }, method = RequestMethod.GET)
	public String dailypdfOld(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("first one");
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/DailyPdfOld",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantname = currentMerchant.getBusinessName().toUpperCase().replace("\\", "").replace(" ", "")
				.replace("/", "");

		String to = null;
		Date dt1 = new Date();
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
		to = dateFormat1.format(dt1);
		to = to.replace("-", "");
		logger.info("date" + to);

		String day = to.substring(0, 2);
		String month = to.substring(2, 4);
		String year = to.substring(4, 8);
		int dayno;
		dayno = Integer.valueOf(day);
		dayno = dayno - 1;
		int monthno = Integer.valueOf(month);
		int yearno = Integer.valueOf(year);
		logger.info("date format:" + dayno);
		ArrayList<String> fsList = new ArrayList<String>();
		ArrayList<String> filenameList = new ArrayList<String>();
		for (int i = dayno - 2; i <= dayno; i++) {
			if (i < 10) {
				logger.info("inside Month Less Than 10");
				String d = "0" + i;
				String m = null;
				if (monthno < 10) {
					m = "0" + monthno;
				} else {
					m = month;
				}

				try {
					String file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\FpxPDF\\";
					String fileupdated = file + "MOBI_FPX_" + merchantname + "_" + d + m + year;
					File f = new File(fileupdated + ".pdf");
					logger.info(f);
					fileupdated = fileupdated.replace("\\", "-");
					fileupdated = fileupdated.replace(".", "--");
					logger.info("file name after replacing // with - " + fileupdated);
					if (f.exists()) {

						String filedate = year + m + d;
						filedate = new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("yyyyMMdd").parse(filedate));
						logger.info("filename date " + filedate);
						filenameList.add(filedate);
						fsList.add(fileupdated);

					} else {
						model.addAttribute("responseData", "No Record found for Last month");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				logger.info("inside else");
				String file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\FpxPDF\\";
				try {
					String m = null;
					if (monthno < 10) {
						m = "0" + monthno;
					} else {
						m = month;
					}

					String fileupdated = file + "MOBI_FPX_" + merchantname + "_" + i + m + year;
					File f = new File(fileupdated + ".pdf");
					logger.info(f);
					fileupdated = fileupdated.replace("\\", "-");
					fileupdated = fileupdated.replace(".", "--");
					logger.info("file name after replacing // with - " + fileupdated);

					if (f.exists()) {

						String filedate = year + month + i;
						filedate = new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("yyyyMMdd").parse(filedate));
						logger.info("filename date " + filedate);

						filenameList.add(filedate);
						fsList.add(fileupdated);

					} else {
						model.addAttribute("responseData", "No Record found for Last month");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		for (String ss : fsList) {
			logger.info(ss);
		}
		PaginationBean<Pdf> paginationBean = new PaginationBean<Pdf>();
		paginationBean.setItemListt(fsList);
		paginationBean.setFilenamelist(filenameList);
		logger.info("No of Records: " + paginationBean.getItemListt().size());
		logger.info("No of file Records: " + paginationBean.getFilenamelist().size());
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/pdfdailysearchOld" }, method = RequestMethod.POST)
	public String pdfdailysearchOld(final Model model, final java.security.Principal principal,
			@RequestParam final String fromDate, @RequestParam final String toDate,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
			@RequestParam final String txnType) {
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/DailyPdfOld",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		String fromdat = null;
		String todat = null;
		String to = null;
		String from = null;
		Date frdate = new Date(fromDate);
		int fromday = frdate.getDate();
		int frommon = frdate.getMonth() + 1;
		int fromyear1 = frdate.getYear();
		int currentFrYear = fromyear1 + 1900;
		String frmon = String.format("%02d", frommon);
		String frday = String.format("%02d", fromday);

		fromdat = String.valueOf(currentFrYear) + '-' + frmon + '-' + frday;
		Date todate = new Date(toDate);
		int today = todate.getDate();
		int tomon = todate.getMonth() + 1;
		int toyear1 = todate.getYear();
		int currentToYear = toyear1 + 1900;
		String tmon = String.format("%02d", tomon);
		String tday = String.format("%02d", today);

		todat = String.valueOf(currentToYear) + '-' + tmon + '-' + tday;

		logger.info(" dates from " + fromdat + " todate " + todat);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		String merchantname = currentMerchant.getBusinessName().toUpperCase().replace("\\", "").replace(" ", "")
				.replace("/", "");

		ArrayList<String> fsList = new ArrayList<String>();
		ArrayList<String> filenameList = new ArrayList<String>();

		logger.info("txnType is :- " + txnType);

		try {

			LocalDate start = LocalDate.parse(fromdat).minusDays(1);
			LocalDate end = LocalDate.parse(todat).minusDays(1);
			logger.info("Start date is :- " + start + " End date is:- " + end);

			while (!start.isAfter(end)) {
				logger.info("Inside While Loop ");
				start = start.plusDays(1);
				logger.info("date is:- " + start);
				DateTimeFormatter DatePattern = DateTimeFormatter.ofPattern("ddMMYYYY");
				String FormatDate = start.format(DatePattern);
				logger.info("Date After Formatting " + FormatDate);
				String file = null;
				String fileupdated = null;

				if (txnType.equalsIgnoreCase("FPX")) {
					logger.info("Inside Fpx");
					file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\FpxPDF\\";
					fileupdated = file + "MOBI_FPX_" + merchantname + "_" + FormatDate;
				}
				if (txnType.equalsIgnoreCase("BOOST")) {
					logger.info("Inside Boost");
					file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\BoostPDF\\";
					fileupdated = file + "MOBI_BOOST_" + merchantname + "_" + FormatDate;
				}
				if (txnType.equalsIgnoreCase("GRABPAY")) {
					logger.info("Inside Grabpay");
					file = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\GrabPDF\\";
					fileupdated = file + "MOBI_GRAB_" + merchantname + "_" + FormatDate;
				}

				File f = new File(fileupdated + ".pdf");
				logger.info(f);
				fileupdated = fileupdated.replace("\\", "-");
				fileupdated = fileupdated.replace(".", "--");
				logger.info("file name after replacing \\ with - " + fileupdated);
				if (f.exists()) {

					String filedate = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("ddMMyyyy").parse(FormatDate));
					logger.info("filename date " + filedate);

					filenameList.add(filedate);
					fsList.add(fileupdated);

				} else {
					logger.info("no record present on " + fileupdated);
					model.addAttribute("responseData", "No Record found for Last month");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e);
		}

		for (String ss : fsList) {
			logger.info("File List is " + ss);
		}

		PaginationBean<Pdf> paginationBean = new PaginationBean<Pdf>();
		paginationBean.setItemListt(fsList);
		paginationBean.setFilenamelist(filenameList);
		logger.info("No of Records: " + paginationBean.getItemListt().size());
		logger.info("No of Records: " + paginationBean.getFilenamelist().size());
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	public boolean deniedDobleEntry(String dateTime) {
		logger.info("DeniedDobleEntry DateTime :" + dateTime);
		// Step 1: Parse the database string format into LocalDateTime
		String dbDateTimeString = dateTime; // Replace this with your database string
		logger.info("DateFormat :" + PropertyLoader.getFileData().getProperty("EZYSETTLE_TIMESTAMP"));
		DateTimeFormatter formatter = DateTimeFormatter
				.ofPattern(PropertyLoader.getFileData().getProperty("EZYSETTLE_TIMESTAMP"));
		LocalDateTime dbDateTime = LocalDateTime.parse(dbDateTimeString, formatter);

		LocalDateTime currentDate = LocalDateTime.now();

		// Step 2: Add 5 minutes
		LocalDateTime thresholdTime = dbDateTime.plusMinutes(5);
		logger.info("dbDateTime :" + dbDateTime);
		logger.info("currentDate :" + currentDate);
		logger.info("Database time :" + dateTime);
		logger.info("thresholdTime :" + thresholdTime);
		// Step 3: Compare if current time is after the time with 5 minutes added
		if (thresholdTime.isBefore(currentDate)) {
			// Allow the condition

			return true;
		} else {
			logger.info("Condition not satisfied. Action denied.");
			// Print any statement if condition is not satisfied
			return false;
		}
	}

	private static String amountRoundofFormat(String amount) {
		logger.info("Amount Need to formate :" + amount);

		String amountString = null;
		amountString = amount.replace(",", "");

		logger.info("Amount After formate :" + String.format("%.2f", Double.parseDouble(amountString)));

		return String.format("%.2f", Double.parseDouble(amountString));
	}
}
