package com.mobiversa.payment.controller;

import java.io.IOException;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.mobiversa.common.bo.BoostDailyRecon;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.PayoutGrandDetail;
import com.mobiversa.common.bo.PayoutHoldTxn;
import com.mobiversa.common.bo.Receipt;
import com.mobiversa.common.bo.SettlementBalance;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.common.bo.TransactionRequest;
import com.mobiversa.common.bo.TransactionResponse;
import com.mobiversa.common.bo.UMEcomTxnRequest;
import com.mobiversa.common.bo.UMEcomTxnResponse;
import com.mobiversa.common.dto.AgentResponseDTO;
import com.mobiversa.common.dto.MerchantSettlementDTO;
import com.mobiversa.common.dto.TerminalDTO;
import com.mobiversa.payment.connect.MotoPaymentCommunication;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.AccountEnquiryDao;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dto.AjaxResponseBody;
import com.mobiversa.payment.dto.DataTransferObject;
import com.mobiversa.payment.dto.FinanceReport;
import com.mobiversa.payment.dto.LoginResponse;
import com.mobiversa.payment.dto.MotoTxnDet;
import com.mobiversa.payment.dto.ReportForSettlement;
import com.mobiversa.payment.dto.SettlementDetailsList;
import com.mobiversa.payment.dto.WithdrawDeposit;
import com.mobiversa.payment.service.CurrencyExchangeService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.service.UnuserMerchantService;
import com.mobiversa.payment.util.BoostWalletApi;
import com.mobiversa.payment.util.ElasticEmail;
import com.mobiversa.payment.util.ElasticEmailClient;
import com.mobiversa.payment.util.EmailUtils;
import com.mobiversa.payment.util.FinanceReportUtils;
import com.mobiversa.payment.util.PayoutModel;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.ResponseBoostWalletApi;
import com.mobiversa.payment.util.ResponseDetails;
import com.mobiversa.payment.util.SettlementModel;
import com.mobiversa.payment.util.UMEzyway;
import com.mobiversa.payment.util.UrlSigner;

@Controller
@RequestMapping(value = TransactionController.URL_BASE)
public class TransactionController extends BaseController {

	@Autowired
	private MobileUserService mobileUserService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private UnuserMerchantService unuserMerchantService;
	@Autowired
	private MerchantDao merchantDao;
	@Autowired
	private AccountEnquiryDao accountEnquiryDao;
	@Autowired
	private CurrencyExchangeService currencyExchangeService;
	
	public static final String URL_BASE = "/transaction";
	private static final Logger logger = Logger.getLogger(TransactionController.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String displayTransactionSummary(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info(" transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		transactionService.listAllTransaction(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/umList/{currPage}" }, method = RequestMethod.GET)
	public String displayUmTransactionSummary(final Model model,

			@PathVariable final int currPage, final java.security.Principal principal) {
		logger.info("um transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUmList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		transactionService.listAllUMTransaction(paginationBean, null, null, "ALL");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/UMunsedreport/{currPage}" }, method = RequestMethod.GET)
	public String displayTransactionSummaryUM(final Model model, final java.security.Principal principal,
			@PathVariable final int currPage) {
		logger.info("about to list allUMMMMMMMMMMMMMMMM  transaction");
		PageBean pageBean = new PageBean("merchant list", "merchantreport/merchantUnusedListUM", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ReportForSettlement> paginationBean = new PaginationBean<ReportForSettlement>();
		logger.info("PreAuth Summary :" + principal.getName());
		paginationBean.setCurrPage(currPage);
		// unuserMerchantService.listPreAuthTransaction(paginationBean, null, null);
		logger.info("11111" + principal.getName());
		unuserMerchantService.unusedMerchantListUM(paginationBean, "180");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			logger.info("if" + principal.getName());
			model.addAttribute("responseData", "No Records found"); // table response
		} else {
			logger.info("else" + principal.getName());
			model.addAttribute("responseData", null);
		}
		logger.info("elsee" + principal.getName());
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchUM" }, method = RequestMethod.GET)
	public String displayTransactionListUM(final Model model, @RequestParam final String days,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list all  Transactiona" + days);
		PageBean pageBean = new PageBean("merchant list", "merchantreport/merchantUnusedList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ReportForSettlement> paginationBean = new PaginationBean<ReportForSettlement>();
		paginationBean.setCurrPage(currPage);

		// transactionService.listPreAuthTransaction(paginationBean, date, date1);
		unuserMerchantService.unusedMerchantListUM(paginationBean, days);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/umEzywireList" }, method = RequestMethod.GET)
	public String displayUmEzywireTransactionSummary(final Model model,

			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("um transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUmEzywireList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllUmEzywireTransaction(paginationBean, null, null, "ALL");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/ezywireList" }, method = RequestMethod.GET)
	public String displayEzywireTransactionSummary(final Model model,

			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("um transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/EzywireSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllForsettlementTransaction(paginationBean, null, null, "EZYWIRE");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchEzywire" }, method = RequestMethod.GET)
	public String displayEzywireTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("Search Ezywire");
		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		PageBean pageBean = new PageBean("transactions list", "transaction/EzywireSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchAllForsettlementTransaction(paginationBean, fromDate, toDate, "EZYWIRE");

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/exportEzywire", method = RequestMethod.GET)
	public ModelAndView getExcelEzywire(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "EZYWIRE");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("paydeeTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("paydeeTxnPdf", "txnList", list);
		}
	}

	// ezyway paydee

	@RequestMapping(value = { "/ezywayList" }, method = RequestMethod.GET)
	public String displayEzywayTransactionSummary(final Model model,

			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("um transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/EzywaySummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllForsettlementTransaction(paginationBean, null, null, "EZYWAY");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchEzyway" }, method = RequestMethod.GET)
	public String displayEzywayTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		String txnType = null;
		logger.info("search  Transaction" + txnType);

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/EzywaySummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.searchAllForsettlementTransaction(paginationBean, fromDate, toDate, "EZYWAY");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("txnType", txnType);
		request.setAttribute("txnType", txnType);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/exportEzyway", method = RequestMethod.GET)
	public ModelAndView getExcelEzyway(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "EZYWAY");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("paydeeTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("paydeeTxnPdf", "txnList", list);
		}
	}

	// ezymoto paydee

	@RequestMapping(value = { "/ezymotoList" }, method = RequestMethod.GET)
	public String displayEzymotoTransactionSummary(final Model model,

			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("um transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/EzyMotoSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllForsettlementTransaction(paginationBean, null, null, "EZYMOTO");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchEzymoto" }, method = RequestMethod.GET)
	public String displayEzymotoTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		String txnType = null;
		logger.info("search  Transaction" + txnType);

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/EzyMotoSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.searchAllForsettlementTransaction(paginationBean, fromDate, toDate, "EZYMOTO");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("txnType", txnType);
		request.setAttribute("txnType", txnType);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/exportEzymoto", method = RequestMethod.GET)
	public ModelAndView getExcelEzymoto(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "EZYMOTO");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("paydeeTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("paydeeTxnPdf", "txnList", list);
		}
	}

	// ezyrec paydee

	@RequestMapping(value = { "/ezyrecList/{currPage}" }, method = RequestMethod.GET)
	public String displayEzyrecTransactionSummary(final Model model,

			@PathVariable final int currPage, final java.security.Principal principal) {
		logger.info("um transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/EzyRecSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllForsettlementTransaction(paginationBean, null, null, "EZYREC");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchEzyrec" }, method = RequestMethod.GET)
	public String displayEzyrecTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		String txnType = null;
		logger.info("search  Transaction" + txnType);

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/EzyRecSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.searchAllForsettlementTransaction(paginationBean, fromDate, toDate, "EZYREC");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("txnType", txnType);
		request.setAttribute("txnType", txnType);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/exportEzyrec", method = RequestMethod.GET)
	public ModelAndView getExcelEzyrec(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "EZYREC");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("paydeeTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("paydeeTxnPdf", "txnList", list);
		}
	}

	// recplus paydee

	@RequestMapping(value = { "/ezyrecplusList" }, method = RequestMethod.GET)
	public String displayEzyrecplusTransactionSummary(final Model model,

			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("um transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/RecPlusSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllForsettlementTransaction(paginationBean, null, null, "RECPLUS");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchRecplus" }, method = RequestMethod.GET)
	public String displayEzyrecplusTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		String txnType = null;
		logger.info("search  Transaction" + txnType);
		PageBean pageBean = new PageBean("transactions list", "transaction/RecPlusSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "RECPLUS");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("txnType", txnType);
		request.setAttribute("txnType", txnType);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/exportRecplus", method = RequestMethod.GET)
	public ModelAndView getExcelEzyrecplus(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "RECPLUS");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("paydeeTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("paydeeTxnPdf", "txnList", list);
		}
	}

//ezypass paydee

	@RequestMapping(value = { "/ezypassList/{currPage}" }, method = RequestMethod.GET)
	public String displayEzypassTransactionSummary(final Model model,

			@PathVariable final int currPage, final java.security.Principal principal) {
		logger.info("um transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/EzypassSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllForsettlementTransaction(paginationBean, null, null, "EZYPASS");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchEzypass" }, method = RequestMethod.GET)
	public String displayEzypassTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		String txnType = null;
		logger.info("search  Transaction" + txnType);
		PageBean pageBean = new PageBean("transactions list", "transaction/EzypassSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "EZYPASS");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("txnType", txnType);
		request.setAttribute("txnType", txnType);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/exportEzypass", method = RequestMethod.GET)
	public ModelAndView getExcelEzypass(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "EZYPASS");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("paydeeTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("paydeeTxnPdf", "txnList", list);
		}
	}

//ezypod paydee

	@RequestMapping(value = { "/ezypodList/{currPage}" }, method = RequestMethod.GET)
	public String displayEzypodTransactionSummary(final Model model,

			@PathVariable final int currPage, final java.security.Principal principal) {
		logger.info("um transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/EzypodSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllForsettlementTransaction(paginationBean, null, null, "EZYPOD");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchEzypod" }, method = RequestMethod.GET)
	public String displayEzypodTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		String txnType = null;
		logger.info("search  Transaction" + txnType);
		PageBean pageBean = new PageBean("transactions list", "transaction/EzypodSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "EZYPOD");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("txnType", txnType);
		request.setAttribute("txnType", txnType);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/exportEzypod", method = RequestMethod.GET)
	public ModelAndView getExcelEzypod(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "EZYPOD");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("paydeeTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("paydeeTxnPdf", "txnList", list);
		}
	}

	@RequestMapping(value = { "/UMenquiryTransaction" }, method = RequestMethod.GET)
	public String UMTransactionEnquiry(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("Transaction Enquiry list");
		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * logger.info("currently logged in as " + myName); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName);
		 */
		PageBean pageBean = new PageBean("transactions list", "transaction/umTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		logger.info(" All Transaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMEzywayTxnEnqByAdmin(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		logger.info(" enquiry");
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchUMEnquiry" }, method = RequestMethod.GET)
	public String SearchUMTransactionEnquiry(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("Transaction Enquiry list");
		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * logger.info("currently logged in as " + myName); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName);
		 */

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		PageBean pageBean = new PageBean("transactions list", "transaction/umTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMEzywayTxnEnqByAdmin(paginationBean, fromDate, toDate, "ALL");
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = "/umEzywayEnqExport", method = RequestMethod.GET)
	public ModelAndView getExportUMEzywayEnq(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		/*
		 * HttpSession session=request.getSession(); String myName
		 * =(String)session.getAttribute("userName"); //String myName =
		 * principal.getName(); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName); //
		 * logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		 * logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		 */
		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMEzywayTxnEnqByAdmin(paginationBean, dat, dat1, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("umAdminEnqExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("umAdminEnqPdf", "umTxnList", list1);
		}

	}

	/*
	 * @RequestMapping(value = { "/listcard/{currPage}" }, method =
	 * RequestMethod.GET) public String displayCardTransactionSummary(final Model
	 * model,
	 * 
	 * @PathVariable final int currPage,final java.security.Principal principal) {
	 * logger.info("about to list all  transaction"); PageBean pageBean = new
	 * PageBean("transactions list", "transaction/transactionList",
	 * Module.TRANSACTION, "transaction/sideMenuTransaction");
	 * model.addAttribute("pageBean", pageBean); logger.info("Transaction Summary:"
	 * + principal.getName()); PaginationBean<ForSettlement> paginationBean = new
	 * PaginationBean<ForSettlement>(); paginationBean.setCurrPage(currPage);
	 * transactionService.listAllTransaction(paginationBean, null, null,"CARD");
	 * if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() ==
	 * null || paginationBean.getItemList().size() == 0 ) {
	 * model.addAttribute("responseData", "No Records found"); //table response
	 * }else { model.addAttribute("responseData", null); }
	 * model.addAttribute("paginationBean", paginationBean);
	 * 
	 * return TEMPLATE_DEFAULT; }
	 * 
	 * @RequestMapping(value = { "/listcash/{currPage}" }, method =
	 * RequestMethod.GET) public String displayCashTransactionSummary(final Model
	 * model,
	 * 
	 * @PathVariable final int currPage,final java.security.Principal principal) {
	 * logger.info("about to list all  transaction"); PageBean pageBean = new
	 * PageBean("transactions list", "transaction/transactionList",
	 * Module.TRANSACTION, "transaction/sideMenuTransaction");
	 * model.addAttribute("pageBean", pageBean); logger.info("Transaction Summary:"
	 * + principal.getName()); PaginationBean<ForSettlement> paginationBean = new
	 * PaginationBean<ForSettlement>(); paginationBean.setCurrPage(currPage);
	 * transactionService.listAllTransaction(paginationBean, null, null,"CASH");
	 * if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() ==
	 * null || paginationBean.getItemList().size() == 0 ) {
	 * model.addAttribute("responseData", "No Records found"); //table response
	 * }else { model.addAttribute("responseData", null); }
	 * model.addAttribute("paginationBean", paginationBean);
	 * 
	 * return TEMPLATE_DEFAULT; }
	 */

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String displayTransactionList(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam final String txnType,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  Transaction" + txnType);

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listAllTransaction(paginationBean, fromDate, toDate, txnType);
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("txnType", txnType);
		request.setAttribute("txnType", txnType);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/searchumEzywire" }, method = RequestMethod.GET)
	public String displayUmTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		String txnType = null;
		logger.info("search  Transaction" + txnType);
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUmEzywireList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.searchAllUmEzywireTransaction(paginationBean, fromDate, toDate, txnType);
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("txnType", txnType);
		request.setAttribute("txnType", txnType);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	// @RequestMapping(value = { "/merchantdetails/{merchantName}" }, method =
	// RequestMethod.GET)
	@RequestMapping(value = { "/merchantdetails" }, method = RequestMethod.GET)
	public String displayTransactionDetails(final Model model, java.security.Principal principal,
			@RequestParam final String mid, @RequestParam final String txnType, @RequestParam final String date) {

		String txnType1 = null;
		logger.info("merchantdetails list by admin: " + txnType);
		PageBean pageBean = new PageBean("Transactions Details", "transaction/transactionsView", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		if (txnType != null) {
			txnType1 = txnType;

		} else {
			txnType1 = "ALL";

		}
		logger.info("type of transactionelse : " + txnType1);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		// paginationBean.setCurrPage(currPage);

		transactionService.loadMerchantName(paginationBean, mid, date, txnType1);

		String merchantName = null, city = null;
		if (paginationBean.getItemList().size() != 0) {
			for (ForSettlement f : paginationBean.getItemList()) {
				merchantName = f.getMerchantName();
				city = f.getLocation();

			}
		}

		model.addAttribute("paginationBean", paginationBean);

		model.addAttribute("namedd", merchantName);
		model.addAttribute("nameyy", city);

		// return "redirect:" + URL_BASE + "/merchantdetails";
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/umMerchantdetails" }, method = RequestMethod.GET)
	public String displayumTransactionDetails(final Model model, java.security.Principal principal,
			@RequestParam final String mid, @RequestParam final String txnType, @RequestParam final String date) {

		String txnType1 = null;
		logger.info("merchantdetails list by admin: " + txnType);
		PageBean pageBean = new PageBean("Transactions Details", "transaction/transactionsUMView", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		if (txnType != null) {
			txnType1 = txnType;
		} else {
			txnType1 = "ALL";
		}
		logger.info("type of transactionelse : " + txnType1);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		// paginationBean.setCurrPage(currPage);

		transactionService.loadUmMerchantByName(paginationBean, mid, date, txnType1);
		logger.info(paginationBean.getItemList().size());
		String merchantName = null, state = null;
		if (paginationBean.getItemList().size() != 0) {
			for (ForSettlement f : paginationBean.getItemList()) {
				if (f.getMerchantName() != null) {
					merchantName = f.getMerchantName();
				}
				if (f.getLocation() != null && !f.getLocation().isEmpty()) {
					state = f.getLocation();
				}
			}

		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("merchantName", merchantName);
		model.addAttribute("state", state);

		return TEMPLATE_DEFAULT;
	}

	// code change for agent fetching
	@RequestMapping(value = { "/agentdetails/{agentName}" }, method = RequestMethod.GET)
	public String displayAgentDetails(final Model model, @PathVariable final String agentName,

			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list all  Transactions :" + agentName);

		PageBean pageBean = new PageBean("Transactions Details", "transaction/agentMerchantList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		AgentResponseDTO list = new AgentResponseDTO();
		ArrayList<MerchantSettlementDTO> merlist = new ArrayList<MerchantSettlementDTO>();
		PaginationBean<MerchantSettlementDTO> paginationBean = new PaginationBean<MerchantSettlementDTO>();

		paginationBean.setCurrPage(currPage);
		/* String name[] = agentName.split("~"); */
		list = transactionService.loadAgentName(agentName);
		/* transactionService.loadMerchantName(pBean,agentName); */
		/* transactionService.loadAgentName(paginationBean1,agentName); */
		String agName = null;
		String agCode = null;
		String agCity = null;
		String agPhoneNo = null;
		/* for (AgentResponseDTO ag : list){ */
		agName = list.getAgName();
		agCode = list.getAgCode();
		agCity = list.getAgCity();
		agPhoneNo = list.getAgPhoneNo();
		merlist = (ArrayList<MerchantSettlementDTO>) list.getMerSettle();
		for (MerchantSettlementDTO msd : merlist) {
			/*
			 * System.out.println("DATA : "+msd.getMerchantAddr1());
			 * System.out.println("DATA : "+msd.getMerchantAddr2());
			 * System.out.println("DATA : "+msd.getMerchantCity());
			 * System.out.println("DATA : "+msd.getMerchantName());
			 * System.out.println("DATA : "+msd.getMerchantPostcode());
			 */
		}
		paginationBean.setItemList(merlist);

		// System.out.println("Ste the pagination");
		// }
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("agentName", agName);
		model.addAttribute("agentCode", agCode);
		model.addAttribute("agentCity", agCity);
		model.addAttribute("agentPhone", agPhoneNo);

		// System.out.println("Ste the pagination12345 ");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/terminalList/{merchantData}" }, method = RequestMethod.GET)
	public String displayTerminalDetails(final Model model, @PathVariable final String merchantData,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list all  Transactions");
		PageBean pageBean = new PageBean("Transactions Details", "transaction/agentMerchantDetails", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<TerminalDTO> paginationBean = new PaginationBean<TerminalDTO>();
		paginationBean.setCurrPage(currPage);
		String name[] = merchantData.split("~");
		// System.out.println("Ste the pagination");
		transactionService.loadTerminalName(paginationBean, name[0]);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("businessName", name[0]);
		model.addAttribute("businessAddr1", name[1]);
		model.addAttribute("businessCity", name[2]);
		model.addAttribute("businessPostcode", name[3]);
		// System.out.println("Ste the pagination 5467567657");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView getExcel(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam final String txnType, @RequestParam(required = false) String export) {

		logger.info("Export Transaction Summary.." + txnType);

		List<ForSettlement> list = transactionService.exportAllTransaction(date, date1, txnType);

		if (!(export.equals("PDF"))) {

			return new ModelAndView("txnAllListExcel", "txnList", list);

		} else {
			return new ModelAndView("txnListPdf1", "txnList", list);
		}
	}

	@RequestMapping(value = "/exportUm", method = RequestMethod.GET)
	public ModelAndView getUmExcel(@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Um Export Transaction Summary.." + txnType);

		List<ForSettlement> list = transactionService.exportAllUmTransaction(date, date1, txnType);

		if (!(export.equals("PDF"))) {

			return new ModelAndView("txnListExcel", "txnList", list);

		} else {
			return new ModelAndView("txnListPdf1", "txnList", list);
		}
	}

	@RequestMapping(value = "/exportUmEzywire", method = RequestMethod.GET)
	public ModelAndView getUmExcelEzywire(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Um Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchAllUmEzywireTransaction(paginationBean, date, date1, txnType);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("paydeeTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("paydeeTxnPdf", "txnList", list);
		}
	}

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

		// Merchant merchant = merchantService.loadMerchant(myName);
		UMEcomTxnRequest req = transactionService.loadUMEzywayTransactionRequest(id);
		UMEcomTxnResponse tr = transactionService.loadUMEzywayTransactionResponse(id);

		Merchant merchant = transactionService.loadMerchantDetails(tr.getF001_MID());
		// logger.info("merchant"+merchant.getBusinessName());

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
			logger.info(
					" here rk split values " + tr.getH002_VNO() + " " + tr.getF256_FiCode() + " " + tr.getTxnType());

			if (tr.getStatus().equalsIgnoreCase("S") || tr.getStatus().equalsIgnoreCase("A")) {
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

					} else if (tr.getTxnType().equalsIgnoreCase("EZYMOTO")) {
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

			} else {

				logger.info("No status :" + tr.getStatus());
				logger.info("version 3 or 5:" + tr.getH002_VNO());
				logger.info("Txn Type:" + tr.getTxnType());

				logger.info("Set default as EZYWAY");

				dt.setTxnType("EZYWAY");

			}

			/*
			 * else{ if(req.getTxnType() == null) { dt.setTxnType("EZYWAY VOID"); }else {
			 * if(req.getTxnType().equals("EZYWAY")) {
			 * 
			 * dt.setTxnType("EZYWAY VOID"); }else if(req.getTxnType().equals("EZYMOTO")) {
			 * dt.setTxnType("EZYMOTO VOID"); } else { dt.setTxnType("EZYAUTH VOID"); } }
			 * 
			 * 
			 * }
			 */

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
			logger.info("exception is" + e);
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

	@RequestMapping(value = "/umEzywayExport", method = RequestMethod.GET)
	public ModelAndView getExportUMEzyway(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, @RequestParam("mid") String mid, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();

		/* Merchant currentMerchant = merchantService.loadMerchant(myName); */
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		/*
		 * logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		 */

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		if (mid.isEmpty()) {
			logger.info("exporting txn with Dates ");
			transactionService.exportUMEzywayTransactionAdmin(paginationBean, dat, dat1, "ALL");
		} else {
			logger.info("exporting txn with Dates and MID ");
			transactionService.exportUMEzywayTransactionAdmin(paginationBean, dat, dat1, mid);
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
			return new ModelAndView("umAdminTxnExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("umAdminTxnPdf", "umTxnList", list1);
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

		/* Merchant currentMerchant = merchantService.loadMerchant(myName); */
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		/*
		 * logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		 */

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMEzyauthTransactionAdmin(paginationBean, dat, dat1, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("umAdminAuthExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("umAdminAuthPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umMotoExport", method = RequestMethod.GET)
	public ModelAndView getExportUMMoto(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("UM_EZYMOTO Export ");

		/* Merchant currentMerchant = merchantService.loadMerchant(myName); */
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		/*
		 * logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		 */

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMMotoTransactionAdmin(paginationBean, dat, dat1, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("umAdminTxnExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("umAdminTxnPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umEzyrecExport", method = RequestMethod.GET)
	public ModelAndView getumEzyrecExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("umEzyrecExport ");

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMEzyrecTransactionAdmin(paginationBean, dat, dat1, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("umAdminTxnExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("umAdminTxnPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umLinkExport", method = RequestMethod.GET)
	public ModelAndView umLinkExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("UM_EZYLINK Export ");

		/* Merchant currentMerchant = merchantService.loadMerchant(myName); */
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		/*
		 * logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		 */

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMLinkTransactionAdmin(paginationBean, dat, dat1, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("umAdminTxnExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("umAdminTxnPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umVccExport", method = RequestMethod.GET)
	public ModelAndView umVccExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("UM_EZYMOTO VCC Export ");

		/* Merchant currentMerchant = merchantService.loadMerchant(myName); */
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		/*
		 * logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		 */

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMVccTransactionAdmin(paginationBean, dat, dat1, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("umAdminTxnExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("umAdminTxnPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = { "/details/{id}" }, method = RequestMethod.GET)
	public String displayTransactionDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {
		// logger.info("in Transaction details");
		// PageBean pageBean = new PageBean("Transactions Details",
		// "merchantweb/transaction/receipt",
		// Module.TRANSACTION_WEB, null);

		ForSettlement settle = transactionService.getForSettlement(id);

		logger.info("transacion id: " + settle.getTrxId());
		Merchant merchant = transactionService.loadMerchantDetails(settle.getMid());

		logger.info(" Transaction Reciept:" + principal.getName());

		if (settle.getTxnType() == null) {
			settle.setTxnType("CARD");
		}

		if (settle.getTxnType().equals("CARD") || settle.getTxnType().equals("MOTO")
				|| settle.getTxnType().equals("EZYMOTO") || settle.getTxnType().equals("EZYLINK")
				|| settle.getTxnType().equals("EZYPASS") || settle.getTxnType().equals("EZYWAY")
				|| settle.getTxnType().equals("EZYREC") || settle.getTxnType().equals("RECURRING")
				|| settle.getTxnType().equals("RECPLUS") || settle.getTxnType().equals("GRABPAY")) {

			/*
			 * PageBean pageBean = new PageBean("Transactions Details",
			 * "transaction/receipt", null);
			 */
			/*
			 * PageBean pageBean = new PageBean("Transactions Details",
			 * "transaction/CardReceiptNew", null);
			 */
			PageBean pageBean = new PageBean("Transactions Details", "transaction/receipt_v0.3", null);

			// logger.info("Transaction Id :" + id);
			TransactionRequest trRequest = transactionService.loadTransactionRequest(id);
			TransactionResponse trResponse = transactionService.loadTransactionResponse(id);

			Receipt a = new Receipt();
			a = transactionService.getReceiptSignature(id);

			DataTransferObject dt = new DataTransferObject();
			// System.out.println(" dataaaaaaa :"+a.getSignature());
			if (a != null) {
				if (a.getSignature() != null) {
					String signdata = new String(a.getSignature());
					// System.out.println(" String sign :"+signdata);
					dt.setSign(signdata);
					// logger.info(dt.getSign());
				}
			}
			// System.out.println("Data :"+settle.getStatus());

			String txn = settle.getStatus();
			String pinEntry = settle.getPinEntry();

			if (settle.getHostType() == null) {
				if (txn.equals("S") || txn.equals("A")) {

					if (settle.getTxnType().equals("MOTO") && merchant.getAuth3DS() == null) {
						dt.setTxnType("EZYMOTO SALE");
					} else if (settle.getTxnType().equals("MOTO") && !merchant.getAuth3DS().equals("Yes")) {
						dt.setTxnType("EZYMOTO SALE");
					} else if (settle.getTxnType().equals("EZYMOTO") && merchant.getAuth3DS() == null) {
						dt.setTxnType("EZYMOTO SALE");
					} else if (settle.getTxnType().equals("EZYMOTO") && !merchant.getAuth3DS().equals("Yes")) {
						dt.setTxnType("EZYMOTO SALE");
					} else if (settle.getTxnType().equals("MOTO") && merchant.getAuth3DS().equals("Yes")) {
						dt.setTxnType("EZYLINK SALE");
					} else if (settle.getTxnType().equals("EZYMOTO") && merchant.getAuth3DS().equals("Yes")) {
						dt.setTxnType("EZYLINK SALE");
					} else if (settle.getTxnType().equals("EZYLINK")) {
						dt.setTxnType("EZYLINK SALE");
					} else if (settle.getTxnType().equals("EZYPASS")) {
						dt.setTxnType("EZYPASS SALE");
					} else if (settle.getTxnType().equals("EZYWAY")) {
						dt.setTxnType("EZYWAY SALE");
					} else if (settle.getTxnType().equals("EZYREC") || settle.getTxnType().equals("RECURRING")) {
						dt.setTxnType("EZYREC SALE");
					} else if (settle.getTxnType().equals("RECPLUS")) {
						dt.setTxnType("EZYREC+ SALE");
					} else if (settle.getTxnType().equals("GRABPAY")) {
						dt.setTxnType("GRABPAY SALE");
					} else {
						dt.setTxnType("SALE");
					}
				} else {
					if (settle.getTxnType().equals("MOTO") && merchant.getAuth3DS() == null) {
						dt.setTxnType("EZYMOTO VOID");
					} else if (settle.getTxnType().equals("MOTO") && !merchant.getAuth3DS().equals("Yes")) {
						dt.setTxnType("EZYMOTO VOID");
					} else if (settle.getTxnType().equals("EZYMOTO") && merchant.getAuth3DS() == null) {
						dt.setTxnType("EZYMOTO VOID");
					} else if (settle.getTxnType().equals("EZYMOTO") && !merchant.getAuth3DS().equals("Yes")) {
						dt.setTxnType("EZYMOTO VOID");
					} else if (settle.getTxnType().equals("MOTO") && merchant.getAuth3DS().equals("Yes")) {
						dt.setTxnType("EZYLINK VOID");
					} else if (settle.getTxnType().equals("EZYMOTO") && merchant.getAuth3DS().equals("Yes")) {
						dt.setTxnType("EZYLINK VOID");
					} else if (settle.getTxnType().equals("EZYLINK")) {
						dt.setTxnType("EZYLINK VOID");
					} else if (settle.getTxnType().equals("EZYPASS")) {
						dt.setTxnType("EZYPASS VOID");
					} else if (settle.getTxnType().equals("EZYWAY")) {
						dt.setTxnType("EZYWAY VOID");
					} else if (settle.getTxnType().equals("EZYREC") || settle.getTxnType().equals("RECURRING")) {
						dt.setTxnType("EZYREC VOID");
					} else if (settle.getTxnType().equals("RECPLUS")) {
						dt.setTxnType("EZYREC+ VOID");
					} else if (settle.getTxnType().equals("GRABPAY")) {
						dt.setTxnType("GRABPAY VOID");
					} else {
						dt.setTxnType("VOID");
					}
				}
			} else {
				if (txn.equals("S") || txn.equals("A")) {
					// logger.info("txntype: "+settle.getTxnType());
					if (settle.getTxnType().equals("MOTO")) {
						// dt.setTxnType("UMOBILE MOTO SALE");
						dt.setTxnType("MOTO SALE");
					} else if (settle.getTxnType().equals("EZYPASS")) {
						// dt.setTxnType("UMOBILE EZYPASS SALE");
						dt.setTxnType("EZYPASS SALE");
					} else if (settle.getTxnType().equals("EZYWAY")) {
						// dt.setTxnType("UMOBILE EZYWAY SALE");
						dt.setTxnType("EZYWAY SALE");
					} else if (settle.getTxnType().equals("EZYREC") || settle.getTxnType().equals("RECURRING")) {
						// dt.setTxnType("UMOBILE EZYREC SALE");
						dt.setTxnType("EZYREC SALE");
					} else {
						/* dt.setTxnType("UMOBILE SALE"); */
						dt.setTxnType("SALE");
					}
				} else {
					if (settle.getTxnType().equals("MOTO")) {
						dt.setTxnType("EZYMOTO VOID");
					} else if (settle.getTxnType().equals("EZYPASS")) {
						dt.setTxnType("EZYPASS VOID");
					} else if (settle.getTxnType().equals("EZYWAY")) {
						dt.setTxnType("EZYWAY VOID");
					} else if (settle.getTxnType().equals("EZYREC") || settle.getTxnType().equals("RECURRING")) {
						dt.setTxnType("EZYREC VOID");
					} else {
						/* dt.setTxnType("UMOBILE VOID"); */
						dt.setTxnType("VOID");
					}
				}
			}

			dt.setHostType(settle.getHostType());
			dt.setMerchantName(merchant.getBusinessName());
			dt.setMerchantAddr1(merchant.getBusinessAddress1());
			dt.setMerchantAddr2(merchant.getBusinessAddress2());
			dt.setMerchantCity(merchant.getCity());
			dt.setMerchantPostCode(merchant.getPostcode());
			dt.setMerchantContNo(merchant.getBusinessContactNumber());
			dt.setMerchantState(merchant.getState());
			// dt.setTxnType(dt.);
			dt.setLatitude(trRequest.getLatitude());
			dt.setLongitude(trRequest.getLongitude());
			dt.setPinEntry(pinEntry);
			try {
				dt.setMapUrl(UrlSigner.GenerateMapImage(dt.getLatitude(), dt.getLongitude()));
				// logger.info("Generated Map Image URL: "+dt.getMapUrl());
			} catch (InvalidKeyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			dt.setRrn(settle.getRrn());
			dt.setBatchNo(settle.getBatchNo());
			// logger.info("get batchno:" + dt.getBatchNo());

			if (settle.getInvoiceId() != null) {

				dt.setRefNo(settle.getInvoiceId());
				// logger.info("invoice id:" + dt.getRefNo());
			} else {
				dt.setRefNo("");
			}

			// dt.setTxnType(trRequest.getTxnType());
			dt.setTid(trRequest.getTid());
			dt.setMid(trRequest.getMid());
			dt.setAmount(trRequest.getAmount());
			dt.setAdditionAmount(trRequest.getAdditionalAmount());
			// String rrn=HexatoAscii.hexaToAscii(trResponse.getRrn(), true);
			// dt.setRrn(rrn);
			dt.setAid(trRequest.getAid());
			dt.setStan(trRequest.getStan());
			dt.setMaskedPan(trRequest.getMaskedPan());
			/*
			 * if(trRequest.getAid()!=null&&trRequest.getCardHolderName()!=null){
			 * //dt.setCardType(CardType.getCardType(trRequest.getAid()));
			 * dt.setCardHolderName(trRequest.getCardHolderName()); }
			 */

			// new changes for receipt 23082017

			if (trRequest.getCardHolderName() != null) {
				dt.setCardHolderName(trRequest.getCardHolderName());

			} else {
				dt.setCardHolderName("");
			}

			String cardType = "";
			/*
			 * if(trRequest.getCardScheme() != null) { cardType = trRequest.getCardScheme();
			 * }
			 */

			if (trRequest.getCardType() != null && trRequest.getCardScheme() != null) {
				cardType = (trRequest.getCardScheme() + " " + trRequest.getCardType()).toUpperCase();
			} else if (trRequest.getCardType() != null) {
				cardType = (trRequest.getCardType()).toUpperCase();
			} else {
				cardType = " ";
			}

			dt.setCardType(cardType);

			/*
			 * if(trRequest.getCardScheme() != null && trRequest.getCardType() != null){
			 * dt.setCardType(trRequest.getCardScheme()+" "+trRequest.getCardType());
			 * logger.info("Card Type from Txn Request Card Scheme and Card Type : "+dt.
			 * getCardType()); }else if(trRequest.getCardType() != null){
			 * dt.setCardType(trRequest.getCardType()); }else { dt.setCardType(" "); }
			 */
			/*
			 * if (trRequest.getCardScheme() != null && trRequest.getCardType() != null) {
			 * 
			 * dt.setCardType(trRequest.getCardScheme() + " " + trRequest.getCardType());
			 * 
			 * 
			 * 
			 * } else if (trRequest.getApplicationLabel() != null) {
			 * dt.setCardType(trRequest.getApplicationLabel()); } else if
			 * (trRequest.getApplicationLabel() == null && trRequest.getAid() != null) {
			 * dt.setCardType(CardType.getCardType(trRequest.getAid())); //
			 * mJson.setCardHolderName(trRequest.getCardHolderName()); } else {
			 * dt.setCardType(" "); }
			 */

			// end
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
				 * String rd = new SimpleDateFormat("dd-MMM-yy") .format(new
				 * SimpleDateFormat("yyyy-MM-dd").parse(sd)); String rt = new
				 * SimpleDateFormat("HH:mm") .format(new SimpleDateFormat("HHmmss").parse(st));
				 */
				String rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(settle.getTimeStamp()));
				String rt = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(settle.getTimeStamp()));
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
			// logger.info("Amount For receipt :" + trRequest.getAmount());

			if (settle.getAmount() != null) {
				amount = Double.parseDouble(settle.getAmount()) / 100;
			}
			/*
			 * if (trRequest.getAmount() != null) { amount =
			 * Double.parseDouble(trRequest.getAmount()) / 100; }
			 */
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
			 * if(trRequest.getAid() != null){
			 * dt.setCardType(CardType.getCardType(trRequest.getAid())); }else{
			 * dt.setCardType(""); }
			 */
			// dt.setBatchNo(trRequest.getBatchNo());
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
			/*
			 * if(settle!=null){ dt.setBatchNo(settle.getBatchNo()); }
			 */
			String resposecode = hexaToAscii(trResponse.getAidResponse(), true);

			dt.setApprCode(resposecode);
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
			// return "merchantweb/transaction/receipt";
			// return "transaction/CardReceiptNew";
			return "transaction/receipt_v0.3";
		} else {

			/*
			 * PageBean pageBean = new PageBean("Transactions Details",
			 * "transaction/cashreceipt", null);
			 */
			/*
			 * PageBean pageBean = new PageBean("Transactions Details",
			 * "transaction/CashReceiptNew", null);
			 */
			PageBean pageBean = new PageBean("Transactions Details", "transaction/CashReceipt_v0.2", null);
			// logger.info("Transaction Id :" + id);
			DataTransferObject dt = new DataTransferObject();
			String txn = settle.getStatus();
			String pinEntry = settle.getPinEntry();
			if (txn.equals("CT")) {
				dt.setTxnType("CASH SALE");
			} else {
				dt.setTxnType("CASH CANCELLED");
			}
			dt.setMerchantName(merchant.getBusinessName());
			dt.setMerchantAddr1(merchant.getBusinessAddress1());
			dt.setMerchantAddr2(merchant.getBusinessAddress2());
			dt.setMerchantCity(merchant.getCity());
			dt.setMerchantPostCode(merchant.getPostcode());
			dt.setMerchantContNo(merchant.getBusinessContactNumber());
			dt.setMerchantState(merchant.getState());
			dt.setLatitude(settle.getLatitude());
			dt.setLongitude(settle.getLongitude());

			try {
				dt.setMapUrl(UrlSigner.GenerateMapImage(dt.getLatitude(), dt.getLongitude()));
				// logger.info("Generated Map Image URL: "+dt.getMapUrl());
			} catch (InvalidKeyException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (settle.getInvoiceId() != null) {
				dt.setRefNo(settle.getInvoiceId());
				logger.info("invoice id:" + dt.getRefNo());
			} else {
				dt.setRefNo("");
			}
			// dt.setTxnType(settle.getTxnType());
			dt.setTid(settle.getTid());
			dt.setMid(settle.getMid());
			dt.setAmount(settle.getAmount());
			dt.setAdditionAmount(settle.getAdditionAmount());
			dt.setStan(settle.getStan());
			dt.setPinEntry(pinEntry);
			String st = settle.getTime();
			String sd = settle.getTimeStamp();
			try {
				/*
				 * String rd = new SimpleDateFormat("dd-MMM-yy") .format(new
				 * SimpleDateFormat("yyyy-MM-dd").parse(sd)); String rt = new
				 * SimpleDateFormat("HH:mm") .format(new SimpleDateFormat("HHmmss").parse(st));
				 */
				String rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(settle.getTimeStamp()));
				String rt = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(settle.getTimeStamp()));
				logger.info(rd);
				dt.setDate(rd);
				dt.setTime(rt);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			double amount = 0;
			// logger.info("Amount For receipt :" + settle.getAmount());
			if (settle.getAmount() != null) {
				amount = Double.parseDouble(settle.getAmount()) / 100;
			}
			double tips = 0;
			double total = amount;
			/*
			 * if (settle.getAdditionAmount() != null) { tips =
			 * Double.parseDouble(settle.getAdditionAmount()) / 100; amount = amount - tips;
			 * total = amount + tips; }
			 */
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(amount);
			String output1 = myFormatter.format(tips);
			String output2 = myFormatter.format(total);
			dt.setAdditionAmount(output1);
			dt.setAmount(output);
			dt.setTotal(output2);
			dt.setTraceNo(settle.getStan());
			if (settle.getInvoiceId() != null) {
				dt.setInvoiceNo(settle.getInvoiceId());
			} else {
				dt.setInvoiceNo("");
			}
			request.setAttribute("dto", dt);
			model.addAttribute("pageBean", pageBean);
			// return "transaction/cashreceipt";
			// return "transaction/CashReceiptNew";
			return "transaction/CashReceipt_v0.2";

		}
		// return "redirect:/transactionweb/list/1";
	}

	@RequestMapping(value = { "/walletBalance" }, method = RequestMethod.GET)
	public String displayBoostWalletBalance(final Model model, final java.security.Principal principal) {
		logger.info("Boost wallet balance");
		PageBean pageBean = new PageBean("transactions list", "transaction/boostWalletBalance", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		ResponseBoostWalletApi res = BoostWalletApi.getBoostWalletBalance();
		Merchant merchant = merchantService.loadMerchant(principal.getName());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("res", res);
		return TEMPLATE_DEFAULT;
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

	// UM-Ezyway Transaction Summary
	@RequestMapping(value = { "/umEzywayList" }, method = RequestMethod.GET)
	public String umEzywayList(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info(" UM-EZWAY Transaction Summary admin");
		List<Merchant> merchant1 = merchantService.loadUMMerchant();

		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMEzywayList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-EZWAY Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMEzywayTransaction(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	// UM-EZYAUTH Transaction Summary
	@RequestMapping(value = { "/umEzyauthList" }, method = RequestMethod.GET)
	public String umEzyauthList(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info(" UM-EZYAUTH Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMEzyauthList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-EZYAUTH Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMEzyauthTransaction(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	// rk added

	@RequestMapping(value = { "/UMEzyauthenquiryTransaction" }, method = RequestMethod.GET)
	public String UMEzyauthenquiryTransaction(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("Transaction Enquiry list");
		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * logger.info("currently logged in as " + myName); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName);
		 */
		PageBean pageBean = new PageBean("transactions list", "transaction/umAuthTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		logger.info(" All UM_EZYAUTH Transaction Enquiry:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMAuthTxnEnqByAdmin(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		logger.info(" enquiry");
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchUMAuthEnquiry" }, method = RequestMethod.GET)
	public String searchUMAuthEnquiry(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("Search UM_AUTH Transaction Enquiry list");
		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * logger.info("currently logged in as " + myName); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName);
		 */

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		PageBean pageBean = new PageBean("transactions list", "transaction/umAuthTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMAuthTxnEnqByAdmin(paginationBean, fromDate, toDate, "ALL");
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = "/umAuthEnqExport", method = RequestMethod.GET)
	public ModelAndView umAuthEnqExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//					@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		/*
		 * HttpSession session=request.getSession(); String myName
		 * =(String)session.getAttribute("userName"); //String myName =
		 * principal.getName(); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName); //
		 * logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		 * logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		 */
		/*
		 * String dat = null; String dat1 = null;
		 */

		logger.info("UM_AUTH  Enquiry Export ");

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMAuthTxnEnqByAdmin(paginationBean, dat, dat1, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("umAdminEnqExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("umAdminEnqPdf", "umTxnList", list1);
		}

	}

	// rk added

	@RequestMapping(value = { "/umMotoList" }, method = RequestMethod.GET)
	public String umMotoList(final Model model, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info(" UM-EZYMOTO Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMMotoList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-Moto Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMMotoTransaction(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/umEzyrecList" }, method = RequestMethod.GET)
	public String umEzyrecList(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info(" UM-EZYREC Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMEzyrecList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-EZYREC Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMEzyrecTransaction(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/umLinkList" }, method = RequestMethod.GET)
	public String umLinkList(final Model model, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info(" UM-EZYLINK Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMLinkList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-EZYLINK Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMLinkTransaction(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	// prauthfee rk added

	@RequestMapping(value = { "/Preauthfee/{currPage}" }, method = RequestMethod.GET)
	public String preauthfee(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info(" pre-auth fee Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/PreauthFeeTransaction", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" preauthfee Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listpreauthfee(paginationBean, null, null);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchpreauthfee" }, method = RequestMethod.GET)
	public String searchpreauthfee(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  UM-EZYLINK Transaction ");
		PageBean pageBean = new PageBean("transactions list", "transaction/PreauthFeeTransaction", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listpreauthfee(paginationBean, date, date1);
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/preauthfeeExport", method = RequestMethod.GET)
	public ModelAndView preauthfeeExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("preauthfeeExport ");

		/* Merchant currentMerchant = merchantService.loadMerchant(myName); */
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		/*
		 * logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		 */

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listpreauthfee(paginationBean, dat, dat1);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("PreauthfeeExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("PreauthfeePdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = { "/umVccList" }, method = RequestMethod.GET)
	public String umVccList(final Model model, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info(" UM-EZYMOTO VCC Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMVccList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-EZYMOTO VCC Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMVccTransaction(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	// UM-Ezyway search
	@RequestMapping(value = { "/searchUMEzyway" }, method = RequestMethod.GET)
	public String umEzywayList1(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam final String mid,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  UM-EZWAY Transaction ");
		logger.info("Transaction MID : " + mid);
		List<Merchant> merchant1 = merchantService.loadUMMerchant();

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMEzywayList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		if (mid.isEmpty()) {
			logger.info("searching txn with Dates ");
			transactionService.listUMEzywayTransaction(paginationBean, fromDate, toDate, "ALL");
		} else {
			logger.info("searching txn with Dates and MID ");
			transactionService.listUMEzywayTransaction(paginationBean, fromDate, toDate, mid);
		}

		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	// UM-Ezyauth search
	@RequestMapping(value = { "/searchUMEzyauth" }, method = RequestMethod.GET)
	public String umEzyauthList1(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  UM-EZYAUTH Transaction ");

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMEzyauthList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listUMEzyauthTransaction(paginationBean, fromDate, toDate, "ALL");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/searchUMMoto" }, method = RequestMethod.GET)
	public String umMotoList1(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  UM-EZYMOTO Transaction ");

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMMotoList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listUMMotoTransaction(paginationBean, fromDate, toDate, "ALL");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/searchUMEzyrec" }, method = RequestMethod.GET)
	public String searchUMEzyrec(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("searchUMEzyrec Transaction ");

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMEzyrecList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMEzyrecTransaction(paginationBean, fromDate, toDate, "ALL");

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/searchUMLink" }, method = RequestMethod.GET)
	public String searchUMLink(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  UM-EZYLINK Transaction ");

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMLinkList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listUMLinkTransaction(paginationBean, fromDate, toDate, "ALL");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/searchUMVcc" }, method = RequestMethod.GET)
	public String searchUMVcc(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  UM-EZYMOTO VCC Transaction ");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMVccList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listUMVccTransaction(paginationBean, date, date1, "ALL");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	// UM-EYWAY Void
	@RequestMapping(value = { "/cancelPayment/{id}" }, method = RequestMethod.GET)
	public String cancelPaymentUMEzywayDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umCancelPayment",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

//		UMEzyway txnDet = new UMEzyway();
//		txnDet.setF354_TID(tr.getF354_TID());
//		txnDet.setF001_MID(tr.getF001_MID());
//		txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp());
		logger.info("cancelPayment" + tr.getF263_MRN());
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
		return TEMPLATE_DEFAULT;
	}

	// UMmoto void
	@RequestMapping(value = { "/cancelMotoPayment/{id}" }, method = RequestMethod.GET)
	public String cancelPaymentUMMotoDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umMotoCancelPayment",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

//		UMEzyway txnDet = new UMEzyway();
//		txnDet.setF354_TID(tr.getF354_TID());
//		txnDet.setF001_MID(tr.getF001_MID());
//		txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp());
		logger.info("cancelPayment" + tr.getF263_MRN());
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
		return TEMPLATE_DEFAULT;
	}

	// UMmoto void
	@RequestMapping(value = { "/cancelEzyrecPayment/{id}" }, method = RequestMethod.GET)
	public String cancelEzyrecPayment(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umEzyrecCancelPayment",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

//			UMEzyway txnDet = new UMEzyway();
//			txnDet.setF354_TID(tr.getF354_TID());
//			txnDet.setF001_MID(tr.getF001_MID());
//			txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp());
		logger.info("cancelPayment" + tr.getF263_MRN());
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
		return TEMPLATE_DEFAULT;
	}

	// UMmoto void
	@RequestMapping(value = { "/cancelEzylinkPayment/{id}" }, method = RequestMethod.GET)
	public String cancelPaymentUMLinkDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umEzylinkCancelPayment",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

//		UMEzyway txnDet = new UMEzyway();
//		txnDet.setF354_TID(tr.getF354_TID());
//		txnDet.setF001_MID(tr.getF001_MID());
//		txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp());
		logger.info("cancelPayment" + tr.getF263_MRN());
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
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/cancelPaymentByAdmin" }, method = RequestMethod.POST)
	public String motoSubmitTransaction(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("cancelPaymentByAdmin" + TxnDet.getF263_MRN());

		//ResponseDetails data = MotoPaymentCommunication.UmCancelPayment(TxnDet);
		
		ResponseDetails data= null;
		logger.info("merchant type for void : "+TxnDet.getF260_ServID());	
		
		if(TxnDet.getF260_ServID().equalsIgnoreCase("UM")) {
			 data = MotoPaymentCommunication.UmCancelPayment(TxnDet);
		}else if(TxnDet.getF260_ServID().equalsIgnoreCase("FIUU")) {
			logger.info("inside fiuu end point call");
			 data = MotoPaymentCommunication.FiuuCancelPayment(TxnDet);
		}

		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umCancelPayment",
						Module.TRANSACTION, "transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", TxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umVoidPaymentDone",
						Module.TRANSACTION, "transaction/sideMenuTransaction");

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
			PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umCancelPayment",
					Module.TRANSACTION, "transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/cancelMotoPaymentByAdmin" }, method = RequestMethod.POST)
	public String motoVoidSubmitTransaction(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("cancelPaymentByAdmin" + TxnDet.getF263_MRN());

		//ResponseDetails data = MotoPaymentCommunication.UmCancelPayment(TxnDet);
		
		ResponseDetails data= null;
		logger.info("merchant type for void : "+TxnDet.getF260_ServID());	
		
		if(TxnDet.getF260_ServID().equalsIgnoreCase("UM")) {
			 data = MotoPaymentCommunication.UmCancelPayment(TxnDet);
		}else if(TxnDet.getF260_ServID().equalsIgnoreCase("FIUU")) {
			logger.info("inside fiuu end point call");
			 data = MotoPaymentCommunication.FiuuCancelPayment(TxnDet);
		}

		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umMotoCancelPayment",
						Module.TRANSACTION, "transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", TxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umMotoVoidPaymentDone",
						Module.TRANSACTION, "transaction/sideMenuTransaction");

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
			PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umMotoCancelPayment",
					Module.TRANSACTION, "transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/cancelEzyrecPaymentByAdmin" }, method = RequestMethod.POST)
	public String cancelEzyrecPaymentByAdmin(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("cancelPaymentByAdmin" + TxnDet.getF263_MRN());

		ResponseDetails data = MotoPaymentCommunication.UmEzyrecCancelPayment(TxnDet);

		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umEzyrecCancelPayment",
						Module.TRANSACTION, "transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", TxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umEzyrecVoidPaymentDone",
						Module.TRANSACTION, "transaction/sideMenuTransaction");

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
			PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umEzyrecCancelPayment",
					Module.TRANSACTION, "transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/cancelEzylinkPaymentByAdmin" }, method = RequestMethod.POST)
	public String ezyLinkVoidSubmitTransaction(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("cancelPaymentByAdmin" + TxnDet.getF263_MRN());

		//ResponseDetails data = MotoPaymentCommunication.UmCancelPayment(TxnDet);
		
		ResponseDetails data= null;
		logger.info("merchant type for void : "+TxnDet.getF260_ServID());	
		
		if(TxnDet.getF260_ServID().equalsIgnoreCase("UM")) {
			 data = MotoPaymentCommunication.UmCancelPayment(TxnDet);
		}else if(TxnDet.getF260_ServID().equalsIgnoreCase("FIUU")) {
			logger.info("inside fiuu end point call");
			 data = MotoPaymentCommunication.FiuuCancelPayment(TxnDet);
		}

		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umEzylinkCancelPayment",
						Module.TRANSACTION, "transaction/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", TxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("transactions list",
						"transaction/voidpayment/umEzylinkVoidPaymentDone", Module.TRANSACTION,
						"transaction/sideMenuTransaction");

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
			PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umEzylinkCancelPayment",
					Module.TRANSACTION, "transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", TxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/UMMotoenquiryTransaction" }, method = RequestMethod.GET)
	public String UMMotoTransactionEnquiry(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("Transaction Enquiry list");
		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * logger.info("currently logged in as " + myName); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName);
		 */
		PageBean pageBean = new PageBean("transactions list", "transaction/umMotoTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		logger.info(" All UM_EZYMOTO Transaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMMotoTxnEnqByAdmin(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		logger.info(" enquiry");
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/UMEzyrecenquiryTransaction" }, method = RequestMethod.GET)
	public String UMEzyrecenquiryTransaction(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("Transaction Enquiry list");

		PageBean pageBean = new PageBean("transactions list", "transaction/umEzyrecTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		logger.info(" All UMEzyrecenquiryTransaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMEzyrecTxnEnqByAdmin(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		logger.info(" enquiry");
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/UMLinkenquiryTransaction" }, method = RequestMethod.GET)
	public String UMLinkenquiryTransaction(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("Transaction Enquiry list");
		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * logger.info("currently logged in as " + myName); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName);
		 */
		PageBean pageBean = new PageBean("transactions list", "transaction/umLinkTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		logger.info(" All UM_EZYLINK Transaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMLinkTxnEnqByAdmin(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		logger.info(" enquiry");
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/UMVccenquiryTransaction" }, method = RequestMethod.GET)
	public String UMVccenquiryTransaction(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("Transaction Enquiry list");
		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * logger.info("currently logged in as " + myName); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName);
		 */
		PageBean pageBean = new PageBean("transactions list", "transaction/umVccTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		logger.info(" All UM_EZYMOTO VCC Transaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMVccTxnEnqByAdmin(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		logger.info(" enquiry");
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchUMMotoEnquiry" }, method = RequestMethod.GET)
	public String SearchUMMotoTransactionEnquiry(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("Search UM_MOTO Transaction Enquiry list");
		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * logger.info("currently logged in as " + myName); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName);
		 */

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/umMotoTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMMotoTxnEnqByAdmin(paginationBean, fromDate, toDate, "ALL");
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchUMEzyrecEnquiry" }, method = RequestMethod.GET)
	public String searchUMEzyrecEnquiry(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("searchUMEzyrecEnquiry list");
		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * logger.info("currently logged in as " + myName); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName);
		 */

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/umEzyrecTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMEzyrecTxnEnqByAdmin(paginationBean, fromDate, toDate, "ALL");
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchUMLinkEnquiry" }, method = RequestMethod.GET)
	public String searchUMLinkEnquiry(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("Search UM_LINK Transaction Enquiry list");
		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * logger.info("currently logged in as " + myName); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName);
		 */

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		PageBean pageBean = new PageBean("transactions list", "transaction/umLinkTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMLinkTxnEnqByAdmin(paginationBean, fromDate, toDate, "ALL");
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchUMVccEnquiry" }, method = RequestMethod.GET)
	public String searchUMVccEnquiry(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("Search UM_MOTO VCC Transaction Enquiry list");
		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * logger.info("currently logged in as " + myName); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName);
		 */

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		PageBean pageBean = new PageBean("transactions list", "transaction/umVccTransactionEnquiry",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMVccTxnEnqByAdmin(paginationBean, fromDate, toDate, "ALL");
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = "/umMotoEnqExport", method = RequestMethod.GET)
	public ModelAndView getExportUMMotoEnq(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		/*
		 * HttpSession session=request.getSession(); String myName
		 * =(String)session.getAttribute("userName"); //String myName =
		 * principal.getName(); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName); //
		 * logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		 * logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		 */
		/*
		 * String dat = null; String dat1 = null;
		 */

		logger.info("UM_MOTO Enquiry Export ");

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMMotoTxnEnqByAdmin(paginationBean, dat, dat1, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("umAdminEnqExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("umAdminEnqPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umEzyrecEnqExport", method = RequestMethod.GET)
	public ModelAndView getumEzyrecEnqExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("umEzyrecEnqExport ");

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMEzyrecTxnEnqByAdmin(paginationBean, dat, dat1, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("umAdminEnqExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("umAdminEnqPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umLinkEnqExport", method = RequestMethod.GET)
	public ModelAndView umLinkEnqExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		/*
		 * HttpSession session=request.getSession(); String myName
		 * =(String)session.getAttribute("userName"); //String myName =
		 * principal.getName(); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName); //
		 * logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		 * logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		 */
		/*
		 * String dat = null; String dat1 = null;
		 */

		logger.info("UM_LINK  Enquiry Export ");

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMLinkTxnEnqByAdmin(paginationBean, dat, dat1, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("umAdminEnqExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("umAdminEnqPdf", "umTxnList", list1);
		}

	}

	@RequestMapping(value = "/umVccEnqExport", method = RequestMethod.GET)
	public ModelAndView umVccEnqExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		/*
		 * HttpSession session=request.getSession(); String myName
		 * =(String)session.getAttribute("userName"); //String myName =
		 * principal.getName(); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName); //
		 * logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		 * logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		 */
		/*
		 * String dat = null; String dat1 = null;
		 */

		logger.info("UM_MOTO VCC Enquiry Export ");

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMVccTxnEnqByAdmin(paginationBean, dat, dat1, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("umAdminEnqExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("umAdminEnqPdf", "umTxnList", list1);
		}

	}

	// EZYMOTO void

	@RequestMapping(value = { "/motoCancelPayment/{id}" }, method = RequestMethod.GET)
	public String motoCancelPayment(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/MotoCancelPaymentConfirm",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		/*
		 * HttpSession session = request.getSession();
		 * 
		 * String myName = (String) session.getAttribute("userName");
		 * 
		 * Merchant merchant = merchantService.loadMerchant(myName);
		 * 
		 * logger.info("Mid" + ":" + merchant.getMid().getMid() + "MerchantName" + ":" +
		 * merchant.getBusinessName() + ":" + "Merchant void logged by" + ":" +
		 * principal.getName() + ":");
		 */

		logger.info("txid:" + id);

		ForSettlement fs = transactionService.getForSettlement(id);
		logger.info("Moto mid:" + fs.getMid());
		TransactionRequest tr = transactionService.loadTransactionRequest(id);
		Merchant merchant = merchantService.loadMerchantbyMotoMid(fs.getMid());

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

		return TEMPLATE_DEFAULT;
	}

	// EZYLINK void

	@RequestMapping(value = { "/motoLinkCancelPayment/{id}" }, method = RequestMethod.GET)
	public String motoLinkCancelPayment(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/MotoLinkCancelPaymentConfirm",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		/*
		 * HttpSession session = request.getSession();
		 * 
		 * String myName = (String) session.getAttribute("userName");
		 * 
		 * Merchant merchant = merchantService.loadMerchant(myName);
		 * 
		 * logger.info("Mid" + ":" + merchant.getMid().getMid() + "MerchantName" + ":" +
		 * merchant.getBusinessName() + ":" + "Merchant void logged by" + ":" +
		 * principal.getName() + ":");
		 */

		logger.info("txid:" + id);

		ForSettlement fs = transactionService.getForSettlement(id);
		logger.info("Moto mid:" + fs.getMid());
		TransactionRequest tr = transactionService.loadTransactionRequest(id);
		Merchant merchant = merchantService.loadMerchantbyMotoMid(fs.getMid());

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

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/MotoCancelPaymentByAdmin" }, method = RequestMethod.POST)
	public String MotoCancelPaymentByAdmin(final Model model, @ModelAttribute("txnDet") MotoTxnDet motoTxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {

		ResponseDetails data = MotoPaymentCommunication.CancelPayment(motoTxnDet);
		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("Transactions Details",
						"transaction/voidpayment/MotoCancelPaymentConfirm", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", motoTxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("Transactions Details", "transaction/voidpayment/MotoVoidPaymentDone",
						null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("Transactions Details", "transaction/voidpayment/MotoCancelPaymentConfirm",
					null);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", motoTxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/MotoLinkCancelPaymentByAdmin" }, method = RequestMethod.POST)
	public String MotoLinkCancelPaymentByAdmin(final Model model, @ModelAttribute("txnDet") MotoTxnDet motoTxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {

		ResponseDetails data = MotoPaymentCommunication.CancelPayment(motoTxnDet);
		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("Transactions Details",
						"transaction/voidpayment/MotoLinkCancelPaymentConfirm", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", motoTxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("Transactions Details",
						"transaction/voidpayment/MotoLinkVoidPaymentDone", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("Transactions Details",
					"transaction/voidpayment/MotoLinkCancelPaymentConfirm", null);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", motoTxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/umAuthByAdmin" }, method = RequestMethod.POST)
	public String authSubmitTransaction(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,

			@RequestParam("f354_TID") String f354_TID, @RequestParam("f001_MID") String f001_MID,
			@RequestParam("h001_MTI") String h001_MTI, @RequestParam("h003_TDT") String h003_TDT,
			@RequestParam("h004_TTM") String h004_TTM, @RequestParam("f011_AuthIDResp") String f011_AuthIDResp,

			@RequestParam("f007_TxnAmt") String f007_TxnAmt, @RequestParam("f268_ChName") String f268_ChName,
			@RequestParam("maskedPan") String maskedPan, @RequestParam("f263_MRN") String f263_MRN,
			@RequestParam("status") String status,

			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("umAuthByAdmin" + f263_MRN);
		logger.info("umAuthByAdmin Status:::::::::::" + status);
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
				PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umEzyAuthDone",
						Module.TRANSACTION, "transaction/sideMenuTransaction");

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

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/umAuthReverseByAdmin" }, method = RequestMethod.POST)
	public String authReverseTransaction(final Model model, @ModelAttribute("txnDet") UMEcomTxnRequest TxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("umAuthReverseByAdmin" + TxnDet.getF263_MRN());

		logger.info("umAuthReverseByAdmin Status:::::::::::" + TxnDet.getStatus());

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
				PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umEzyAuthDone",
						Module.TRANSACTION, "transaction/sideMenuTransaction");

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

		return TEMPLATE_DEFAULT;
	}

	// UM-EZYAUTH Action
	@RequestMapping(value = { "/ezyauthDetails/{id}" }, method = RequestMethod.GET)
	public String UMEzyauthDetails(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umEzyAuth", Module.TRANSACTION,
				"transaction/sideMenuTransaction");

		UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

//					UMEzyway txnDet = new UMEzyway();
//					txnDet.setF354_TID(tr.getF354_TID());
//					txnDet.setF001_MID(tr.getF001_MID());
//					txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp());
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

		data = (String) request.getSession(true).getAttribute("responseDescriptioSession");

		logger.info("Response Description ::::::::::::  :" + data);

		if (data != null) {
			model.addAttribute("responseData", data + "...  Try Again..");
		}

//					txnDet.setMerchantId(merchant.getId());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", tr);

		request.getSession(true).removeAttribute("responseDescriptioSession");

		return TEMPLATE_DEFAULT;
	}

	// UM-EZYAUTH Reverse after sale
	@RequestMapping(value = { "/ezyauthReverseDetails/{id}" }, method = RequestMethod.GET)
	public String UMEzyauthReverseDetails(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		logger.info("UMEzyauthReverseDetails Reverse after sale ");

		PageBean pageBean = new PageBean("transactions list", "transaction/voidpayment/umEzyAuthReverse",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		// UMEcomTxnRequest tr = transactionService.loadUMEzywayTransactionRequest(id);

		UMEcomTxnResponse tr = transactionService.loadUMEzywayTransactionResponse(id);

//							UMEzyway txnDet = new UMEzyway();
//							txnDet.setF354_TID(tr.getF354_TID());
//							txnDet.setF001_MID(tr.getF001_MID());
//							txnDet.setF011_AUTHIDRESP(tr.getF011_AuthIDResp());
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

//							txnDet.setF263_MRN(tr.getF263_MRN());
//							txnDet.setF268_CHNAME(tr.getF268_ChName());

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

//							txnDet.setMerchantId(merchant.getId());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", tr);

		request.getSession(true).removeAttribute("reverseDescriptioSession");
		return TEMPLATE_DEFAULT;
	}

	// Boost Transaction Summary
	@RequestMapping(value = { "/boost/{currPage}" }, method = RequestMethod.GET)
	public String boostList(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info(" Boost Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionBoostList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" Boost Transaction Summary:" + principal.getName());
		PaginationBean<BoostDailyRecon> paginationBean = new PaginationBean<BoostDailyRecon>();
		paginationBean.setCurrPage(currPage);
		transactionService.listBoostTransaction(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	// Boost search
	@RequestMapping(value = { "/searchBoost" }, method = RequestMethod.GET)
	public String boostList1(HttpServletRequest request, final Model model, @RequestParam final String date,
			/* @RequestParam final String date1, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  Boost Transaction " + date);
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionBoostList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<BoostDailyRecon> paginationBean = new PaginationBean<BoostDailyRecon>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listBoostTransaction(paginationBean, date, " ", "Search");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	// Boost Settlement Summary
	@RequestMapping(value = { "/boostss/{currPage}" }, method = RequestMethod.GET)
	public String boostss(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info(" Boost Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionBoostSettSummary",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" Boost Transaction Summary:" + principal.getName());

		double hMDR = Double.parseDouble("0.00");
		double mMDR = Double.parseDouble("0.00");
		double TTA = Double.parseDouble("0.00");
		double TNA = Double.parseDouble("0.00");

		BoostDailyRecon trResponse = transactionService.loadBoostSettlement(null);

		logger.info(" Boost Host MDR:" + trResponse.getMdrAmount());
		logger.info(" Boost Mobi MDR:" + trResponse.getMdrRebateAmount());
		logger.info(" Boost Total Txn Amt:" + trResponse.getTxnAmount());
		logger.info(" Boost Total Net Amt:" + trResponse.getNetAmount());

		if (!trResponse.getMdrAmount().isEmpty()) {
			hMDR = Double.parseDouble(trResponse.getMdrAmount());
		}

		if (!trResponse.getMdrRebateAmount().isEmpty()) {
			mMDR = Double.parseDouble(trResponse.getMdrRebateAmount());
		}

		if (!trResponse.getTxnAmount().isEmpty()) {
			TTA = Double.parseDouble(trResponse.getTxnAmount());
		}

		if (!trResponse.getNetAmount().isEmpty()) {
			TNA = Double.parseDouble(trResponse.getNetAmount());
		}

		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);

		String hMDR1 = df.format(hMDR);
		String mMDR1 = df.format(mMDR);
		String TTA1 = df.format(TTA);
		String TNA1 = df.format(TNA);

		model.addAttribute("hostMdr", hMDR1);
		model.addAttribute("mobiMdr", mMDR1);
		model.addAttribute("totTxn", TTA1);
		model.addAttribute("totNet", TNA1);
		model.addAttribute("setDate", trResponse.getDate());

		PaginationBean<BoostDailyRecon> paginationBean = new PaginationBean<BoostDailyRecon>();
		paginationBean.setCurrPage(currPage);
		transactionService.listBoostSettlement(paginationBean, null);
		/*
		 * if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() ==
		 * null || paginationBean.getItemList().size() == 0) {
		 * model.addAttribute("responseData", "No Records found"); // table // response
		 * } else { model.addAttribute("responseData", null); }
		 * model.addAttribute("paginationBean", paginationBean);
		 * 
		 * return TEMPLATE_DEFAULT;
		 */

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (BoostDailyRecon boostSett : paginationBean.getItemList()) {

				double hMDR2 = Double.parseDouble("0.00");
				double mMDR2 = Double.parseDouble("0.00");
				double TTA2 = Double.parseDouble("0.00");
				double TNA2 = Double.parseDouble("0.00");

				/*
				 * logger.info(" Loop Boost Host MDR:" + boostSett.getMdrAmount());
				 * logger.info(" Loop Boost Mobi MDR:" + boostSett.getMdrRebateAmount());
				 * logger.info(" Loop Boost Total Txn Amt:" + boostSett.getTxnAmount());
				 * logger.info(" Loop Boost Total Net Amt:" + boostSett.getNetAmount());
				 */

				if (!boostSett.getMdrAmount().isEmpty()) {
					hMDR2 = Double.parseDouble(boostSett.getMdrAmount());
				}

				if (!boostSett.getMdrRebateAmount().isEmpty()) {
					mMDR2 = Double.parseDouble(boostSett.getMdrRebateAmount());
				}

				if (!boostSett.getTxnAmount().isEmpty()) {
					TTA2 = Double.parseDouble(boostSett.getTxnAmount());
				}

				if (!boostSett.getNetAmount().isEmpty()) {
					TNA2 = Double.parseDouble(boostSett.getNetAmount());
				}

				String hMDR3 = df.format(hMDR2);
				String mMDR3 = df.format(mMDR2);
				String TTA3 = df.format(TTA2);
				String TNA3 = df.format(TNA2);

				boostSett.setMdrRebateAmount(mMDR3);
				boostSett.setMdrAmount(hMDR3);
				boostSett.setTxnAmount(TTA3);
				boostSett.setNetAmount(TNA3);

			}

			model.addAttribute("paginationBean", paginationBean);

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	// Boost Settlement search
	@RequestMapping(value = { "/searchBoostss" }, method = RequestMethod.GET)
	public String boostss1(HttpServletRequest request, final Model model, @RequestParam final String date,
			/* @RequestParam final String date1, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  Boost Settlement " + date);

		PageBean pageBean = new PageBean("transactions list", "transaction/transactionBoostSettSummary",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		double hMDR = Double.parseDouble("0.00");
		double mMDR = Double.parseDouble("0.00");
		double TTA = Double.parseDouble("0.00");
		double TNA = Double.parseDouble("0.00");

		BoostDailyRecon trResponse = transactionService.loadBoostSettlement(date);

		logger.info(" Boost Host MDR:" + trResponse.getMdrAmount());
		logger.info(" Boost Mobi MDR:" + trResponse.getMdrRebateAmount());
		logger.info(" Boost Total Txn Amt:" + trResponse.getTxnAmount());
		logger.info(" Boost Total Net Amt:" + trResponse.getNetAmount());

		if (!trResponse.getMdrAmount().isEmpty()) {
			hMDR = Double.parseDouble(trResponse.getMdrAmount());
		}

		if (!trResponse.getMdrRebateAmount().isEmpty()) {
			mMDR = Double.parseDouble(trResponse.getMdrRebateAmount());
		}

		if (!trResponse.getTxnAmount().isEmpty()) {
			TTA = Double.parseDouble(trResponse.getTxnAmount());
		}

		if (!trResponse.getNetAmount().isEmpty()) {
			TNA = Double.parseDouble(trResponse.getNetAmount());
		}

		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);

		String hMDR1 = df.format(hMDR);
		String mMDR1 = df.format(mMDR);
		String TTA1 = df.format(TTA);
		String TNA1 = df.format(TNA);

		model.addAttribute("hostMdr", hMDR1);
		model.addAttribute("mobiMdr", mMDR1);
		model.addAttribute("totTxn", TTA1);
		model.addAttribute("totNet", TNA1);
		model.addAttribute("setDate", trResponse.getDate());

		PaginationBean<BoostDailyRecon> paginationBean = new PaginationBean<BoostDailyRecon>();
		paginationBean.setCurrPage(currPage);
		transactionService.listBoostSettlement(paginationBean, date);
		/*
		 * if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() ==
		 * null || paginationBean.getItemList().size() == 0) {
		 * model.addAttribute("responseData", "No Records found"); // table // response
		 * } else { model.addAttribute("responseData", null); }
		 * model.addAttribute("paginationBean", paginationBean);
		 * 
		 * return TEMPLATE_DEFAULT;
		 */

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (BoostDailyRecon boostSett : paginationBean.getItemList()) {

				double hMDR2 = Double.parseDouble("0.00");
				double mMDR2 = Double.parseDouble("0.00");
				double TTA2 = Double.parseDouble("0.00");
				double TNA2 = Double.parseDouble("0.00");

				/*
				 * logger.info(" Loop Boost Host MDR:" + boostSett.getMdrAmount());
				 * logger.info(" Loop Boost Mobi MDR:" + boostSett.getMdrRebateAmount());
				 * logger.info(" Loop Boost Total Txn Amt:" + boostSett.getTxnAmount());
				 * logger.info(" Loop Boost Total Net Amt:" + boostSett.getNetAmount());
				 */

				if (!boostSett.getMdrAmount().isEmpty()) {
					hMDR2 = Double.parseDouble(boostSett.getMdrAmount());
				}

				if (!boostSett.getMdrRebateAmount().isEmpty()) {
					mMDR2 = Double.parseDouble(boostSett.getMdrRebateAmount());
				}

				if (!boostSett.getTxnAmount().isEmpty()) {
					TTA2 = Double.parseDouble(boostSett.getTxnAmount());
				}

				if (!boostSett.getNetAmount().isEmpty()) {
					TNA2 = Double.parseDouble(boostSett.getNetAmount());
				}

				String hMDR3 = df.format(hMDR2);
				String mMDR3 = df.format(mMDR2);
				String TTA3 = df.format(TTA2);
				String TNA3 = df.format(TNA2);

				boostSett.setMdrRebateAmount(mMDR3);
				boostSett.setMdrAmount(hMDR3);
				boostSett.setTxnAmount(TTA3);
				boostSett.setNetAmount(TNA3);

			}

			model.addAttribute("paginationBean", paginationBean);

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	// Grab Pay Transaction Summary
	@RequestMapping(value = { "/listgrabpay" }, method = RequestMethod.GET)
	public String displayGrabPayTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("/grabpay");

		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * Merchant currentMerchant = merchantService.loadMerchant(myName);
		 */

		logger.info(" GRAB_PAY Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionGrabPayList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		List<String> terminalDetailsList = transactionService.getAllGpayTidbymerchantFK();

		transactionService.getGrabTransactionForSettlement(paginationBean, terminalDetailsList, null, null);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {
				/*
				 * TerminalDetails terminalDetails = transactionService
				 * .getTerminalDetailsByTid(forSettlement.getTid() .toString());
				 */

				/*
				 * if (terminalDetails != null) {
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

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	// Grabpay QR Summary

	@RequestMapping(value = { "/listgrabpayqr" }, method = RequestMethod.GET)
	public String displayGrabPayQRTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("/grabpay qr");

		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * Merchant currentMerchant = merchantService.loadMerchant(myName);
		 */

		logger.info(" GRAB_PAY QR Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionGrabPayqrList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		// old one List<String> terminalDetailsList =
		// transactionService.getAllGpayTidbymerchantFK();

		List<String> terminalDetailsList = transactionService.getAllGpayTidbymerchantFK1();

		transactionService.getGrabTransactionqrForSettlement(paginationBean, terminalDetailsList, null, null);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {
				/*
				 * TerminalDetails terminalDetails = transactionService
				 * .getTerminalDetailsByTid(forSettlement.getTid() .toString());
				 */

				/*
				 * if (terminalDetails != null) {
				 * 
				 * forSettlement.setMerchantName(terminalDetails .getContactName()); }
				 */

				MobileUser terminalDetails = transactionService
						.getMobileUserByGpayTidqr(forSettlement.getTid().toString());

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

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	// Grabpay Ecom

	@RequestMapping(value = { "/listgrabpayecom" }, method = RequestMethod.GET)
	public String displayGrabPayecomTransactionSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("/grabpay Ecom");

		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * Merchant currentMerchant = merchantService.loadMerchant(myName);
		 */

		logger.info(" GRAB_PAY ECOM Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionGrabPayecomList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		List<String> terminalDetailsList = transactionService.getAllGpayTidbymerchantFK();

		transactionService.getGrabTransactionecomForSettlement(paginationBean, terminalDetailsList, null, null);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {
				/*
				 * TerminalDetails terminalDetails = transactionService
				 * .getTerminalDetailsByTid(forSettlement.getTid() .toString());
				 */

				/*
				 * if (terminalDetails != null) {
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

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	// search grab pay
	@RequestMapping(value = "/searchgrabpay", method = RequestMethod.GET)
	public String displaygrabTransactionSearch(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("/displaygrabTransactionSearch");

		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * Merchant currentMerchant = merchantService.loadMerchant(myName);
		 */

		logger.info(" GRAB_PAY Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionGrabPayList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		List<String> terminalDetailsList = transactionService.getAllGpayTidbymerchantFK();

		transactionService.getGrabTransactionForSettlement(paginationBean, terminalDetailsList, fromDate, toDate);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {
				/*
				 * TerminalDetails terminalDetails =
				 * transactionService.getTerminalDetailsByTid(forSettlement.getTid().toString())
				 * ; if (terminalDetails != null) {
				 * 
				 * forSettlement.setMerchantName(terminalDetails.getContactName()); }
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

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	// search grabpay qr

	@RequestMapping(value = "/searchgrabpayqr", method = RequestMethod.GET)
	public String displaygrabqrTransactionSearch(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("/displaygrabqrTransactionSearch");

		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * Merchant currentMerchant = merchantService.loadMerchant(myName);
		 */

		logger.info(" GRAB_PAY QR Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionGrabPayqrList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		List<String> terminalDetailsList = transactionService.getAllGpayTidbymerchantFK1();

		transactionService.getGrabTransactionqrForSettlement(paginationBean, terminalDetailsList, fromDate, toDate);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {
				/*
				 * TerminalDetails terminalDetails =
				 * transactionService.getTerminalDetailsByTid(forSettlement.getTid().toString())
				 * ; if (terminalDetails != null) {
				 * 
				 * forSettlement.setMerchantName(terminalDetails.getContactName()); }
				 */

				MobileUser terminalDetails = transactionService
						.getMobileUserByGpayTidqr(forSettlement.getTid().toString());

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

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	// search grabpay ecom

	@RequestMapping(value = "/searchgrabpayecom", method = RequestMethod.GET)
	public String displaygrabecomTransactionSearch(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("/displaygrabecomTransactionSearch");

		/*
		 * HttpSession session=request.getSession(); String myName = (String)
		 * session.getAttribute("userName");
		 * 
		 * Merchant currentMerchant = merchantService.loadMerchant(myName);
		 */

		logger.info(" GRAB_PAY ECOM Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionGrabPayecomList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		List<String> terminalDetailsList = transactionService.getAllGpayTidbymerchantFK();

		transactionService.getGrabTransactionecomForSettlement(paginationBean, terminalDetailsList, fromDate, toDate);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {
				/*
				 * TerminalDetails terminalDetails =
				 * transactionService.getTerminalDetailsByTid(forSettlement.getTid().toString())
				 * ; if (terminalDetails != null) {
				 * 
				 * forSettlement.setMerchantName(terminalDetails.getContactName()); }
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

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	// export grabpay
	@RequestMapping(value = "/exportgrabpay", method = RequestMethod.GET)
	public ModelAndView getgrabTransactionSearch(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam(required = false) String export) {

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		List<String> terminalDetailsList = transactionService.getAllGpayTidbymerchantFK();

		transactionService.getGrabTransactionForSettlement(paginationBean, terminalDetailsList, fromDate, toDate);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {

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

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("grapPayTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("grapPayTxnPdf", "txnList", list);
		}

	}

	// export Grabpay Qr

	@RequestMapping(value = "/exportgrabpayqr", method = RequestMethod.GET)
	public ModelAndView getgrabqrTransactionSearch(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam(required = false) String export) {

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		List<String> terminalDetailsList = transactionService.getAllGpayTidbymerchantFK1();

		transactionService.getGrabTransactionqrForSettlement(paginationBean, terminalDetailsList, fromDate, toDate);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {

				MobileUser terminalDetails = transactionService
						.getMobileUserByGpayTidqr(forSettlement.getTid().toString());

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

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("grapPayqrTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("grapPayqrTxnPdf", "txnList", list);
		}

	}

	// export Grabpay Ecom

	@RequestMapping(value = "/exportgrabpayecom", method = RequestMethod.GET)
	public ModelAndView getgrabecomTransactionSearch(final Model model, final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate, @RequestParam("toDate") String toDate,
			HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam(required = false) String export) {

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		List<String> terminalDetailsList = transactionService.getAllGpayTidbymerchantFK();

		transactionService.getGrabTransactionecomForSettlement(paginationBean, terminalDetailsList, fromDate, toDate);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (ForSettlement forSettlement : paginationBean.getItemList()) {

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

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("grapPayecomTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("grapPayecomTxnPdf", "txnList", list);
		}

	}

	// Settlement Summary
	@RequestMapping(value = { "/settlement/{currPage}" }, method = RequestMethod.GET)
	public String settlement(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info("Settlement Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionSettSummary", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" Settlement Summary:" + principal.getName());

		double hMDR = Double.parseDouble("0.00");
		double mMDR = Double.parseDouble("0.00");
		double deduction = Double.parseDouble("0.00");
		double TTA = Double.parseDouble("0.00");
		double TNA = Double.parseDouble("0.00");

		SettlementMDR trResponse = transactionService.loadLatestSettlement();

		logger.info(" Host MDR:" + trResponse.getHostMdrAmt());
		logger.info(" Mobi MDR:" + trResponse.getMobiMdrAmt());
		logger.info(" Deduction:" + trResponse.getExtraDeductAmt());
		logger.info(" Total Txn Amt:" + trResponse.getTxnAmount());
		logger.info(" Total Net Amt:" + trResponse.getNetAmount());

		if (!trResponse.getHostMdrAmt().isEmpty()) {
			hMDR = Double.parseDouble(trResponse.getHostMdrAmt());
		}

		if (!trResponse.getMobiMdrAmt().isEmpty()) {
			mMDR = Double.parseDouble(trResponse.getMobiMdrAmt());
		}

		if (!trResponse.getExtraDeductAmt().isEmpty()) {
			deduction = Double.parseDouble(trResponse.getExtraDeductAmt());
		}

		if (!trResponse.getTxnAmount().isEmpty()) {
			TTA = Double.parseDouble(trResponse.getTxnAmount());
		}

		if (!trResponse.getNetAmount().isEmpty()) {
			TNA = Double.parseDouble(trResponse.getNetAmount());
		}

		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);

		String hMDR1 = df.format(hMDR);
		String mMDR1 = df.format(mMDR);
		String deduction1 = df.format(deduction);
		String TTA1 = df.format(TTA);
		String TNA1 = df.format(TNA);

		model.addAttribute("hostMdrAmt", hMDR1);
		model.addAttribute("mobiMdrAmt", mMDR1);
		model.addAttribute("extraDeductAmt", deduction1);
		model.addAttribute("txnAmount", TTA1);
		model.addAttribute("netAmount", TNA1);
		model.addAttribute("setDate", trResponse.getDate());

		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
		paginationBean.setCurrPage(currPage);

		transactionService.listLatestSettlement(paginationBean);
		/*
		 * if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() ==
		 * null || paginationBean.getItemList().size() == 0) {
		 * model.addAttribute("responseData", "No Records found"); // table // response
		 * } else { model.addAttribute("responseData", null); }
		 * 
		 * model.addAttribute("paginationBean", paginationBean);
		 * 
		 * return TEMPLATE_DEFAULT;
		 */

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (SettlementMDR settMDR : paginationBean.getItemList()) {

				double hMDR2 = Double.parseDouble("0.00");
				double mMDR2 = Double.parseDouble("0.00");
				double deduction2 = Double.parseDouble("0.00");
				double TTA2 = Double.parseDouble("0.00");
				double TNA2 = Double.parseDouble("0.00");

				/*
				 * logger.info(" Loop Host MDR:" + settMDR.getMobiMdrAmt());
				 * logger.info(" Loop Mobi MDR:" + settMDR.getHostMdrAmt() );
				 * logger.info(" Loop Deduction:" + settMDR.getExtraDeductAmt());
				 * logger.info(" Loop Total Txn Amt:" + settMDR.getTxnAmount());
				 * logger.info(" Loop Total Net Amt:" + settMDR.getNetAmount());
				 */

				if (!settMDR.getHostMdrAmt().isEmpty()) {
					hMDR2 = Double.parseDouble(settMDR.getHostMdrAmt());
				}

				if (!settMDR.getMobiMdrAmt().isEmpty()) {
					mMDR2 = Double.parseDouble(settMDR.getMobiMdrAmt());
				}

				if (!settMDR.getExtraDeductAmt().isEmpty()) {
					deduction2 = Double.parseDouble(settMDR.getExtraDeductAmt());
				}

				if (!settMDR.getTxnAmount().isEmpty()) {
					TTA2 = Double.parseDouble(settMDR.getTxnAmount());
				}

				if (!settMDR.getNetAmount().isEmpty()) {
					TNA2 = Double.parseDouble(settMDR.getNetAmount());
				}

				String hMDR3 = df.format(hMDR2);
				String mMDR3 = df.format(mMDR2);
				String deduction3 = df.format(deduction2);
				String TTA3 = df.format(TTA2);
				String TNA3 = df.format(TNA2);

				settMDR.setMobiMdrAmt(mMDR3);
				settMDR.setHostMdrAmt(hMDR3);
				settMDR.setExtraDeductAmt(deduction3);
				settMDR.setTxnAmount(TTA3);
				settMDR.setNetAmount(TNA3);

			}

			model.addAttribute("paginationBean", paginationBean);

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	/* Get current day balance for all the merchant */
	@RequestMapping(value = {"/settlementDetails"}, method = RequestMethod.GET)
	public String settlementDetails(final Model model, final java.security.Principal principal) {
		logger.info("SETTLEMENT DETAILS FOR ALL MERCHANT");

		PageBean pageBean = new PageBean("settlementDetails list", "transaction/settlementDetailsList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");

		logger.info(" Settlement Summary " + principal.getName());
		PaginationBean<SettlementDetailsList> paginationBean = new PaginationBean<>();
		PaginationBean<SettlementDetailsList> paginationBean2 = new PaginationBean<>(); //selected merchant
		//paginationBean.setCurrPage(currPage);

		transactionService.getSettlementDetails(paginationBean);
		transactionService.getSettlementDetails(paginationBean2);

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("merchantDetails", paginationBean);
		model.addAttribute("paginationBean", paginationBean2);

		logger.info("SIZE  " + paginationBean.getItemList().size());
		return TEMPLATE_DEFAULT;
	}

	/* Get current day balance by business name for specific merchant */
	@RequestMapping(value = {"/settlementDetailsByBusinessName"}, method = RequestMethod.GET)
	public String settlementDetailsByBusinessName(final Model model,@RequestParam String businessName, final java.security.Principal principal) {

		logger.info("SETTLEMENT DETAILS FOR SPECIFIC MERCHANT BY BUSINESS_NAME");

		PageBean pageBean = new PageBean("settlementDetails list", "transaction/settlementDetailsList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");

		logger.info(" Settlement Summary " + principal.getName());
		PaginationBean<SettlementDetailsList> paginationBean = new PaginationBean<>();
		//selected merchant
		PaginationBean<SettlementDetailsList> paginationBean2 = new PaginationBean<>();
		//paginationBean.setCurrPage(currPage);

		transactionService.getSettlementDetails(paginationBean);
		transactionService.getSettlementDetailsByMerchant(paginationBean2,businessName);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantDetails", paginationBean);
		model.addAttribute("paginationBean", paginationBean2);
		model.addAttribute("selectedMerchant", paginationBean2.getItemList().get(0).getBusinessName());
		model.addAttribute("currentMerchant",businessName);

		logger.info("SIZE  " + paginationBean.getItemList().size());
		return TEMPLATE_DEFAULT;
	}

	/* Export current day balance as PDF & CSV */
	@RequestMapping(value={"/exportSettlementDetails"} , method=RequestMethod.GET)
	public ModelAndView exportSettlementDetails(final Model model,@RequestParam String businessName, @RequestParam String exportType, final java.security.Principal principal)
	{
		logger.info("Export CurrentDayBalance as CSV file");

		try {
			logger.info("SETTLEMENT DETAILS ");
			PageBean pageBean = new PageBean("settlementDetails list", "transaction/settlementDetailsList", Module.TRANSACTION,
					"transaction/sideMenuTransaction");

			logger.info(" Settlement Summary " + principal.getName());
			PaginationBean<SettlementDetailsList> paginationBean = new PaginationBean<>();

			transactionService.getSettlementDetailsByMerchant(paginationBean,businessName);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("paginationBean", paginationBean);
			logger.info(" SIZE  " + paginationBean.getItemList().size());

			if(exportType.equals("PDF")) {
				return new ModelAndView("SettlementDetailsPdf", "txnList", paginationBean.getItemList());
			}
			else {
				return new ModelAndView("SettlementDetailsCSV", "txnList", paginationBean.getItemList());
			}

		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
			return null;
		}
	}

	@RequestMapping(value = { "/mobiliteSettlement/{currPage}" }, method = RequestMethod.GET)
	public String mobiliteSettlement(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info("Settlement Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionMobiliteSettSummary",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" Settlement Summary:" + principal.getName());

		double hMDR = Double.parseDouble("0.00");
		double mMDR = Double.parseDouble("0.00");
		double deduction = Double.parseDouble("0.00");
		double TTA = Double.parseDouble("0.00");
		double TNA = Double.parseDouble("0.00");

		SettlementMDR trResponse = transactionService.loadLatestmobiliteSettlement();

		logger.info(" Host MDR:" + trResponse.getSubHostMdrAmt());
		logger.info(" Mobi MDR:" + trResponse.getSubMobiMdrAmt());
		logger.info(" Deduction:" + trResponse.getExtraDeductAmt());
		logger.info(" Total Txn Amt:" + trResponse.getTxnAmount());
		logger.info(" Total Net Amt:" + trResponse.getSubNetAmount());

		if (!trResponse.getSubHostMdrAmt().isEmpty()) {
			hMDR = Double.parseDouble(trResponse.getSubHostMdrAmt());
		}

		if (!trResponse.getSubMobiMdrAmt().isEmpty()) {
			mMDR = Double.parseDouble(trResponse.getSubMobiMdrAmt());
		}

		if (!trResponse.getExtraDeductAmt().isEmpty()) {
			deduction = Double.parseDouble(trResponse.getExtraDeductAmt());
		}

		if (!trResponse.getTxnAmount().isEmpty()) {
			TTA = Double.parseDouble(trResponse.getTxnAmount());
		}

		if (!trResponse.getSubNetAmount().isEmpty()) {
			TNA = Double.parseDouble(trResponse.getSubNetAmount());
		}

		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);

		logger.info("hMDR:" + hMDR);
		logger.info(" mMDR:" + mMDR);

		String hMDR1 = df.format(hMDR);
		String mMDR1 = df.format(mMDR);
		String deduction1 = df.format(deduction);
		String TTA1 = df.format(TTA);
		String TNA1 = df.format(TNA);

		logger.info("hMDR1:" + hMDR1);
		logger.info(" mMDR1:" + mMDR1);

		model.addAttribute("subHostMdrAmt", hMDR1);
		model.addAttribute("subMobiMdrAmt", mMDR1);
		model.addAttribute("extraDeductAmt", deduction1);
		model.addAttribute("txnAmount", TTA1);
		model.addAttribute("subNetAmount", TNA1);
		model.addAttribute("setDate", trResponse.getDate());

		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
		paginationBean.setCurrPage(currPage);

		transactionService.listLatestmobiliteSettlement(paginationBean);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (SettlementMDR settMDR : paginationBean.getItemList()) {

				double hMDR2 = Double.parseDouble("0.00");
				double mMDR2 = Double.parseDouble("0.00");
				double deduction2 = Double.parseDouble("0.00");
				double TTA2 = Double.parseDouble("0.00");
				double TNA2 = Double.parseDouble("0.00");

				/*
				 * logger.info(" Loop Host MDR:" + settMDR.getMobiMdrAmt());
				 * logger.info(" Loop Mobi MDR:" + settMDR.getHostMdrAmt() );
				 * logger.info(" Loop Deduction:" + settMDR.getExtraDeductAmt());
				 * logger.info(" Loop Total Txn Amt:" + settMDR.getTxnAmount());
				 * logger.info(" Loop Total Net Amt:" + settMDR.getNetAmount());
				 */

				if (!settMDR.getSubHostMdrAmt().isEmpty()) {
					hMDR2 = Double.parseDouble(settMDR.getSubHostMdrAmt());
				}

				if (!settMDR.getSubMobiMdrAmt().isEmpty()) {
					mMDR2 = Double.parseDouble(settMDR.getSubMobiMdrAmt());
				}

				if (!settMDR.getExtraDeductAmt().isEmpty()) {
					deduction2 = Double.parseDouble(settMDR.getExtraDeductAmt());
				}

				if (!settMDR.getTxnAmount().isEmpty()) {
					TTA2 = Double.parseDouble(settMDR.getTxnAmount());
				}

				if (!settMDR.getSubNetAmount().isEmpty()) {
					TNA2 = Double.parseDouble(settMDR.getSubNetAmount());
				}

				String hMDR3 = df.format(hMDR2);
				String mMDR3 = df.format(mMDR2);
				String deduction3 = df.format(deduction2);
				String TTA3 = df.format(TTA2);
				String TNA3 = df.format(TNA2);

				settMDR.setSubMobiMdrAmt(mMDR3);
				settMDR.setSubHostMdrAmt(hMDR3);
				settMDR.setExtraDeductAmt(deduction3);
				settMDR.setTxnAmount(TTA3);
				settMDR.setSubNetAmount(TNA3);

			}

			model.addAttribute("paginationBean", paginationBean);

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	@RequestMapping(value = { "/bizappSettlement/{currPage}" }, method = RequestMethod.GET)
	public String bizappSettlement(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info("Settlement Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionBizappSettSummary",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" Settlement Summary:" + principal.getName());

		double hMDR = Double.parseDouble("0.00");
		double mMDR = Double.parseDouble("0.00");
		double deduction = Double.parseDouble("0.00");
		double TTA = Double.parseDouble("0.00");
		double TNA = Double.parseDouble("0.00");

		// SettlementMDR trResponse = transactionService.loadmobiliteSettlement(null);

		BizAppSettlement trResponse = transactionService.loadLatestbizappSettlement();

		logger.info(" Host MDR:" + trResponse.getHostMdrAmt());
		logger.info(" Mobi MDR:" + trResponse.getMobiMdrAmt());
		logger.info(" Deduction:" + trResponse.getDetectionAmt());
		logger.info(" Total Txn Amt:" + trResponse.getGrossAmt());
		logger.info(" Total Net Amt:" + trResponse.getNetAmt());

		if (!trResponse.getHostMdrAmt().isEmpty()) {
			hMDR = Double.parseDouble(trResponse.getHostMdrAmt());
		}

		if (!trResponse.getMobiMdrAmt().isEmpty()) {
			mMDR = Double.parseDouble(trResponse.getMobiMdrAmt());
		}

		if (!trResponse.getDetectionAmt().isEmpty()) {
			deduction = Double.parseDouble(trResponse.getDetectionAmt());
		}

		if (!trResponse.getGrossAmt().isEmpty()) {
			TTA = Double.parseDouble(trResponse.getGrossAmt());
		}

		if (!trResponse.getNetAmt().isEmpty()) {
			TNA = Double.parseDouble(trResponse.getNetAmt());
		}

		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);

		logger.info("hMDR:" + hMDR);
		logger.info(" mMDR:" + mMDR);

		String hMDR1 = df.format(hMDR);
		String mMDR1 = df.format(mMDR);
		String deduction1 = df.format(deduction);
		String TTA1 = df.format(TTA);
		String TNA1 = df.format(TNA);

		logger.info("hMDR1:" + hMDR1);
		logger.info(" mMDR1:" + mMDR1);

		model.addAttribute("HostMdrAmt", hMDR1);
		model.addAttribute("MobiMdrAmt", mMDR1);
		model.addAttribute("DeductAmt", deduction1);
		model.addAttribute("txnAmount", TTA1);
		model.addAttribute("NetAmount", TNA1);
		model.addAttribute("setDate", trResponse.getSettlementDate());

		PaginationBean<BizAppSettlement> paginationBean = new PaginationBean<BizAppSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listLatestbizappSettlement(paginationBean);

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (BizAppSettlement settMDR : paginationBean.getItemList()) {

				double hMDR2 = Double.parseDouble("0.00");
				double mMDR2 = Double.parseDouble("0.00");
				double deduction2 = Double.parseDouble("0.00");
				double TTA2 = Double.parseDouble("0.00");
				double TNA2 = Double.parseDouble("0.00");

				/*
				 * logger.info(" Loop Host MDR:" + settMDR.getMobiMdrAmt());
				 * logger.info(" Loop Mobi MDR:" + settMDR.getHostMdrAmt() );
				 * logger.info(" Loop Deduction:" + settMDR.getExtraDeductAmt());
				 * logger.info(" Loop Total Txn Amt:" + settMDR.getTxnAmount());
				 * logger.info(" Loop Total Net Amt:" + settMDR.getNetAmount());
				 */

				if (!settMDR.getHostMdrAmt().isEmpty()) {
					hMDR2 = Double.parseDouble(settMDR.getHostMdrAmt());
				}

				if (!settMDR.getMobiMdrAmt().isEmpty()) {
					mMDR2 = Double.parseDouble(settMDR.getMobiMdrAmt());
				}

				if (!settMDR.getDetectionAmt().isEmpty()) {
					deduction2 = Double.parseDouble(settMDR.getDetectionAmt());
				}

				if (!settMDR.getGrossAmt().isEmpty()) {
					TTA2 = Double.parseDouble(settMDR.getGrossAmt());
				}

				if (!settMDR.getNetAmt().isEmpty()) {
					TNA2 = Double.parseDouble(settMDR.getNetAmt());
				}

				String hMDR3 = df.format(hMDR2);
				String mMDR3 = df.format(mMDR2);
				String deduction3 = df.format(deduction2);
				String TTA3 = df.format(TTA2);
				String TNA3 = df.format(TNA2);

				settMDR.setMobiMdrAmt(mMDR3);
				settMDR.setHostMdrAmt(hMDR3);
				settMDR.setDetectionAmt(deduction3);
				settMDR.setGrossAmt(TTA3);
				settMDR.setNetAmt(TNA3);

			}

			model.addAttribute("paginationBean", paginationBean);

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	// Settlement search
	@RequestMapping(value = { "/searchSettlement" }, method = RequestMethod.GET)
	public String searchSettlement(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  Settlement " + date);

		PageBean pageBean = new PageBean("transactions list", "transaction/transactionSettSummary", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		double hMDR = Double.parseDouble("0.00");
		double mMDR = Double.parseDouble("0.00");
		double deduction = Double.parseDouble("0.00");
		double TTA = Double.parseDouble("0.00");
		double TNA = Double.parseDouble("0.00");

		SettlementMDR trResponse = transactionService.loadSettlement(date, date1);

		logger.info(" Host MDR:" + trResponse.getHostMdrAmt());
		logger.info(" Mobi MDR:" + trResponse.getMobiMdrAmt());
		logger.info(" Deduction:" + trResponse.getExtraDeductAmt());
		logger.info(" Total Txn Amt:" + trResponse.getTxnAmount());
		logger.info(" Total Net Amt:" + trResponse.getNetAmount());

		if (!trResponse.getHostMdrAmt().isEmpty()) {
			hMDR = Double.parseDouble(trResponse.getHostMdrAmt());
		}

		if (!trResponse.getMobiMdrAmt().isEmpty()) {
			mMDR = Double.parseDouble(trResponse.getMobiMdrAmt());
		}

		if (!trResponse.getExtraDeductAmt().isEmpty()) {
			deduction = Double.parseDouble(trResponse.getExtraDeductAmt());
		}

		if (!trResponse.getTxnAmount().isEmpty()) {
			TTA = Double.parseDouble(trResponse.getTxnAmount());
		}

		if (!trResponse.getNetAmount().isEmpty()) {
			TNA = Double.parseDouble(trResponse.getNetAmount());
		}

		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);

		String hMDR1 = df.format(hMDR);
		String mMDR1 = df.format(mMDR);
		String deduction1 = df.format(deduction);
		String TTA1 = df.format(TTA);
		String TNA1 = df.format(TNA);

		model.addAttribute("hostMdrAmt", hMDR1);
		model.addAttribute("mobiMdrAmt", mMDR1);
		model.addAttribute("extraDeductAmt", deduction1);
		model.addAttribute("txnAmount", TTA1);
		model.addAttribute("netAmount", TNA1);
		model.addAttribute("setDate", trResponse.getDate());

		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
		paginationBean.setCurrPage(currPage);
		transactionService.listSettlement(paginationBean, date, date1);
		/*
		 * if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() ==
		 * null || paginationBean.getItemList().size() == 0) {
		 * model.addAttribute("responseData", "No Records found"); // table // response
		 * } else { model.addAttribute("responseData", null); }
		 * model.addAttribute("paginationBean", paginationBean);
		 * 
		 * return TEMPLATE_DEFAULT;
		 */

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (SettlementMDR settMDR : paginationBean.getItemList()) {

				double hMDR2 = Double.parseDouble("0.00");
				double mMDR2 = Double.parseDouble("0.00");
				double deduction2 = Double.parseDouble("0.00");
				double TTA2 = Double.parseDouble("0.00");
				double TNA2 = Double.parseDouble("0.00");

				/*
				 * logger.info(" Loop Host MDR:" + settMDR.getMobiMdrAmt());
				 * logger.info(" Loop Mobi MDR:" + settMDR.getHostMdrAmt() );
				 * logger.info(" Loop Deduction:" + settMDR.getExtraDeductAmt());
				 * logger.info(" Loop Total Txn Amt:" + settMDR.getTxnAmount());
				 * logger.info(" Loop Total Net Amt:" + settMDR.getNetAmount());
				 */

				if (!settMDR.getHostMdrAmt().isEmpty()) {
					hMDR2 = Double.parseDouble(settMDR.getHostMdrAmt());
				}

				if (!settMDR.getMobiMdrAmt().isEmpty()) {
					mMDR2 = Double.parseDouble(settMDR.getMobiMdrAmt());
				}

				if (!settMDR.getExtraDeductAmt().isEmpty()) {
					deduction2 = Double.parseDouble(settMDR.getExtraDeductAmt());
				}

				if (!settMDR.getTxnAmount().isEmpty()) {
					TTA2 = Double.parseDouble(settMDR.getTxnAmount());
				}

				if (!settMDR.getNetAmount().isEmpty()) {
					TNA2 = Double.parseDouble(settMDR.getNetAmount());
				}

				String hMDR3 = df.format(hMDR2);
				String mMDR3 = df.format(mMDR2);
				String deduction3 = df.format(deduction2);
				String TTA3 = df.format(TTA2);
				String TNA3 = df.format(TNA2);

				settMDR.setMobiMdrAmt(mMDR3);
				settMDR.setHostMdrAmt(hMDR3);
				settMDR.setExtraDeductAmt(deduction3);
				settMDR.setTxnAmount(TTA3);
				settMDR.setNetAmount(TNA3);

			}

			model.addAttribute("paginationBean", paginationBean);

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	// Settlement search
	@RequestMapping(value = { "/searchMobiliteSettlement" }, method = RequestMethod.GET)
	public String searchMobiliteSettlement(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  Settlement " + date);

		PageBean pageBean = new PageBean("transactions list", "transaction/transactionMobiliteSettSummary",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		double hMDR = Double.parseDouble("0.00");
		double mMDR = Double.parseDouble("0.00");
		double deduction = Double.parseDouble("0.00");
		double TTA = Double.parseDouble("0.00");
		double TNA = Double.parseDouble("0.00");

		SettlementMDR trResponse = transactionService.loadmobiliteSettlement(date, date1);

		logger.info(" Host MDR:" + trResponse.getSubHostMdrAmt());
		logger.info(" Mobi MDR:" + trResponse.getSubMobiMdrAmt());
		logger.info(" Deduction:" + trResponse.getExtraDeductAmt());
		logger.info(" Total Txn Amt:" + trResponse.getTxnAmount());
		logger.info(" Total Net Amt:" + trResponse.getSubNetAmount());

		if (!trResponse.getSubHostMdrAmt().isEmpty()) {
			hMDR = Double.parseDouble(trResponse.getSubHostMdrAmt());
		}

		if (!trResponse.getSubMobiMdrAmt().isEmpty()) {
			mMDR = Double.parseDouble(trResponse.getSubMobiMdrAmt());
		}

		if (!trResponse.getExtraDeductAmt().isEmpty()) {
			deduction = Double.parseDouble(trResponse.getExtraDeductAmt());
		}

		if (!trResponse.getTxnAmount().isEmpty()) {
			TTA = Double.parseDouble(trResponse.getTxnAmount());
		}

		if (!trResponse.getSubNetAmount().isEmpty()) {
			TNA = Double.parseDouble(trResponse.getSubNetAmount());
		}

		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);

		logger.info("hMDR:" + hMDR);
		logger.info(" mMDR:" + mMDR);

		String hMDR1 = df.format(hMDR);
		String mMDR1 = df.format(mMDR);
		String deduction1 = df.format(deduction);
		String TTA1 = df.format(TTA);
		String TNA1 = df.format(TNA);

		logger.info("hMDR1:" + hMDR1);
		logger.info(" mMDR1:" + mMDR1);

		model.addAttribute("subHostMdrAmt", hMDR1);
		model.addAttribute("subMobiMdrAmt", mMDR1);
		model.addAttribute("extraDeductAmt", deduction1);
		model.addAttribute("txnAmount", TTA1);
		model.addAttribute("subNetAmount", TNA1);
		model.addAttribute("setDate", trResponse.getDate());

		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
		paginationBean.setCurrPage(currPage);
		transactionService.listmobiliteSettlement(paginationBean, date, date1);
		/*
		 * if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() ==
		 * null || paginationBean.getItemList().size() == 0) {
		 * model.addAttribute("responseData", "No Records found"); // table // response
		 * } else { model.addAttribute("responseData", null); }
		 * model.addAttribute("paginationBean", paginationBean);
		 * 
		 * return TEMPLATE_DEFAULT;
		 */

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (SettlementMDR settMDR : paginationBean.getItemList()) {

				double hMDR2 = Double.parseDouble("0.00");
				double mMDR2 = Double.parseDouble("0.00");
				double deduction2 = Double.parseDouble("0.00");
				double TTA2 = Double.parseDouble("0.00");
				double TNA2 = Double.parseDouble("0.00");

				/*
				 * logger.info(" Loop Host MDR:" + settMDR.getMobiMdrAmt());
				 * logger.info(" Loop Mobi MDR:" + settMDR.getHostMdrAmt() );
				 * logger.info(" Loop Deduction:" + settMDR.getExtraDeductAmt());
				 * logger.info(" Loop Total Txn Amt:" + settMDR.getTxnAmount());
				 * logger.info(" Loop Total Net Amt:" + settMDR.getNetAmount());
				 */

				if (!settMDR.getSubHostMdrAmt().isEmpty()) {
					hMDR2 = Double.parseDouble(settMDR.getSubHostMdrAmt());
				}

				if (!settMDR.getSubMobiMdrAmt().isEmpty()) {
					mMDR2 = Double.parseDouble(settMDR.getSubMobiMdrAmt());
				}

				if (!settMDR.getExtraDeductAmt().isEmpty()) {
					deduction2 = Double.parseDouble(settMDR.getExtraDeductAmt());
				}

				if (!settMDR.getTxnAmount().isEmpty()) {
					TTA2 = Double.parseDouble(settMDR.getTxnAmount());
				}

				if (!settMDR.getSubNetAmount().isEmpty()) {
					TNA2 = Double.parseDouble(settMDR.getSubNetAmount());
				}

				String hMDR3 = df.format(hMDR2);
				String mMDR3 = df.format(mMDR2);
				String deduction3 = df.format(deduction2);
				String TTA3 = df.format(TTA2);
				String TNA3 = df.format(TNA2);

				settMDR.setSubMobiMdrAmt(mMDR3);
				settMDR.setSubHostMdrAmt(hMDR3);
				settMDR.setExtraDeductAmt(deduction3);
				settMDR.setTxnAmount(TTA3);
				settMDR.setSubNetAmount(TNA3);

			}

			model.addAttribute("paginationBean", paginationBean);

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	@RequestMapping(value = { "/searchBizappSettlement" }, method = RequestMethod.GET)
	public String searchBizappSettlement(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  Settlement " + date);

		PageBean pageBean = new PageBean("transactions list", "transaction/transactionBizappSettSummary",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		double hMDR = Double.parseDouble("0.00");
		double mMDR = Double.parseDouble("0.00");
		double deduction = Double.parseDouble("0.00");
		double TTA = Double.parseDouble("0.00");
		double TNA = Double.parseDouble("0.00");

		BizAppSettlement trResponse = transactionService.loadbizappSettlement(date, date1);

		logger.info(" Host MDR:" + trResponse.getHostMdrAmt());
		logger.info(" Mobi MDR:" + trResponse.getMobiMdrAmt());
		logger.info(" Deduction:" + trResponse.getDetectionAmt());
		logger.info(" Total Txn Amt:" + trResponse.getGrossAmt());
		logger.info(" Total Net Amt:" + trResponse.getNetAmt());

		if (!trResponse.getHostMdrAmt().isEmpty()) {
			hMDR = Double.parseDouble(trResponse.getHostMdrAmt());
		}

		if (!trResponse.getMobiMdrAmt().isEmpty()) {
			mMDR = Double.parseDouble(trResponse.getMobiMdrAmt());
		}

		if (!trResponse.getDetectionAmt().isEmpty()) {
			deduction = Double.parseDouble(trResponse.getDetectionAmt());
		}

		if (!trResponse.getGrossAmt().isEmpty()) {
			TTA = Double.parseDouble(trResponse.getGrossAmt());
		}

		if (!trResponse.getNetAmt().isEmpty()) {
			TNA = Double.parseDouble(trResponse.getNetAmt());
		}

		DecimalFormat df = new DecimalFormat("0.00");
		df.setRoundingMode(RoundingMode.HALF_UP);

		logger.info("hMDR:" + hMDR);
		logger.info(" mMDR:" + mMDR);

		String hMDR1 = df.format(hMDR);
		String mMDR1 = df.format(mMDR);
		String deduction1 = df.format(deduction);
		String TTA1 = df.format(TTA);
		String TNA1 = df.format(TNA);

		logger.info("hMDR1:" + hMDR1);
		logger.info(" mMDR1:" + mMDR1);

		model.addAttribute("HostMdrAmt", hMDR1);
		model.addAttribute("MobiMdrAmt", mMDR1);
		model.addAttribute("DeductAmt", deduction1);
		model.addAttribute("txnAmount", TTA1);
		model.addAttribute("NetAmount", TNA1);
		model.addAttribute("setDate", trResponse.getSettlementDate());

		PaginationBean<BizAppSettlement> paginationBean = new PaginationBean<BizAppSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listbizappSettlement(paginationBean, date, date1);
		/*
		 * if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() ==
		 * null || paginationBean.getItemList().size() == 0) {
		 * model.addAttribute("responseData", "No Records found"); // table // response
		 * } else { model.addAttribute("responseData", null); }
		 * model.addAttribute("paginationBean", paginationBean);
		 * 
		 * return TEMPLATE_DEFAULT;
		 */

		logger.info("No of Records: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {

			for (BizAppSettlement settMDR : paginationBean.getItemList()) {

				double hMDR2 = Double.parseDouble("0.00");
				double mMDR2 = Double.parseDouble("0.00");
				double deduction2 = Double.parseDouble("0.00");
				double TTA2 = Double.parseDouble("0.00");
				double TNA2 = Double.parseDouble("0.00");

				/*
				 * logger.info(" Loop Host MDR:" + settMDR.getMobiMdrAmt());
				 * logger.info(" Loop Mobi MDR:" + settMDR.getHostMdrAmt() );
				 * logger.info(" Loop Deduction:" + settMDR.getExtraDeductAmt());
				 * logger.info(" Loop Total Txn Amt:" + settMDR.getTxnAmount());
				 * logger.info(" Loop Total Net Amt:" + settMDR.getNetAmount());
				 */

				if (!settMDR.getHostMdrAmt().isEmpty()) {
					hMDR2 = Double.parseDouble(settMDR.getHostMdrAmt());
				}

				if (!settMDR.getMobiMdrAmt().isEmpty()) {
					mMDR2 = Double.parseDouble(settMDR.getMobiMdrAmt());
				}

				if (!settMDR.getDetectionAmt().isEmpty()) {
					deduction2 = Double.parseDouble(settMDR.getDetectionAmt());
				}

				if (!settMDR.getGrossAmt().isEmpty()) {
					TTA2 = Double.parseDouble(settMDR.getGrossAmt());
				}

				if (!settMDR.getNetAmt().isEmpty()) {
					TNA2 = Double.parseDouble(settMDR.getNetAmt());
				}

				String hMDR3 = df.format(hMDR2);
				String mMDR3 = df.format(mMDR2);
				String deduction3 = df.format(deduction2);
				String TTA3 = df.format(TTA2);
				String TNA3 = df.format(TNA2);

				settMDR.setMobiMdrAmt(mMDR3);
				settMDR.setHostMdrAmt(hMDR3);
				settMDR.setDetectionAmt(deduction3);
				settMDR.setGrossAmt(TTA3);
				settMDR.setNetAmt(TNA3);

			}

			model.addAttribute("paginationBean", paginationBean);

			return TEMPLATE_DEFAULT;
		} else {
			model.addAttribute("paginationBean", paginationBean);

			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_DEFAULT;
		}

	}

	@RequestMapping(value = { "/cancelPaymentEzyPOD/{id}" }, method = RequestMethod.GET)
	public String cancelPaymentEzyPODTransactionDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("Transactions Details", "transaction/voidpayment/CancelEzypodPaymentConfirm",
				null);

		ForSettlement fs = transactionService.getForSettlement(id);

		logger.info("transacion id: " + fs.getTrxId());
		Merchant merchant = transactionService.loadMerchantDetails(fs.getMid());

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

		return TEMPLATE_DEFAULT;
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
						"transaction/voidpayment/CancelEzypodPaymentConfirm", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", motoTxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("Transactions Details", "transaction/voidpayment/EzypodVoidDone",
						null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("Transactions Details",
					"transaction/voidpayment/CancelEzypodPaymentConfirm", null);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", motoTxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/cancelPaymentRecplus/{id}" }, method = RequestMethod.GET)
	public String cancelTransactionDetails(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("Transactions Details", "transaction/voidpayment/RecplusCancelPaymentConfirm",
				null);

		ForSettlement fs = transactionService.getForSettlement(id);

		logger.info("transacion id: " + fs.getTrxId());
		Merchant merchant = transactionService.loadMerchantDetails(fs.getMid());

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

		return TEMPLATE_DEFAULT;
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
						"transaction/voidpayment/RecplusCancelPaymentConfirm", null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", motoTxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("Transactions Details", "transaction/voidpayment/RecplusVoidDone",
						null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("Transactions Details",
					"transaction/voidpayment/RecplusCancelPaymentConfirm", null);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", motoTxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/cancelEzywayPayment/{id}" }, method = RequestMethod.GET)
	public String cancelPaymentTransactionDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("Transactions Details", "transaction/voidpayment/CancelPaymentConfirm", null);

		ForSettlement fs = transactionService.getForSettlement(id);

		logger.info("transacion id: " + fs.getTrxId());
		Merchant merchant = transactionService.loadMerchantDetails(fs.getMid());

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

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/cancelPaymentByMerchant" }, method = RequestMethod.POST)
	public String motoSubmitTransaction(final Model model, @ModelAttribute("txnDet") MotoTxnDet motoTxnDet,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");

		ResponseDetails data = MotoPaymentCommunication.CancelPayment(motoTxnDet);
		if (data != null) {
			if (data.getResponseCode().equals("0001")) {
				PageBean pageBean = new PageBean("Transactions Details", "transaction/voidpayment/CancelPaymentConfirm",
						null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("txnDet", motoTxnDet);
				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {
				PageBean pageBean = new PageBean("Transactions Details", "transaction/voidpayment/voidPaymentDone",
						null);
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {
			PageBean pageBean = new PageBean("Transactions Details", "transaction/voidpayment/CancelPaymentConfirm",
					null);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("txnDet", motoTxnDet);
			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}

		return TEMPLATE_DEFAULT;
	}

	// ezylink paydee

	@RequestMapping(value = { "/ezylinkList" }, method = RequestMethod.GET)
	public String displayEzyLinkTransactionSummary(final Model model,

			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("EzyLinkSummaryList");
		PageBean pageBean = new PageBean("transactions list", "transaction/EzyLinkSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllForsettlementTransaction(paginationBean, null, null, "EZYLINK");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchEzylink" }, method = RequestMethod.GET)
	public String displayEzyLinkTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		String txnType = null;
		logger.info("search  Transaction" + txnType);

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/EzyLinkSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.searchAllForsettlementTransaction(paginationBean, fromDate, toDate, "EZYLINK");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("txnType", txnType);
		request.setAttribute("txnType", txnType);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/exportEzylink", method = RequestMethod.GET)
	public ModelAndView getExcelEzyLink(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "EZYLINK");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("paydeeTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("paydeeTxnPdf", "txnList", list);
		}
	}

	@RequestMapping(value = { "/fpxTxnSummary" }, method = RequestMethod.GET)
	public String displayFpxTransactionSummary(final Model model,

			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("um transaction summary admin");
		model.addAttribute("role",transactionService.getRoleFromUserName(principal.getName()));
		PageBean pageBean = new PageBean("transactions list", "transaction/FPXSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		// List<Merchant> merchant1 = merchantService.loadFpxMerchant();

//		transactionService.listFPXTransaction(paginationBean, null, null);
		transactionService.listFPXTransaction(paginationBean, null, null, null, "NULL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		// model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchFpxTxnSummary" }, method = RequestMethod.GET)
	public String searchFpxTxnSummary(final Model model, @RequestParam final String fromDate,
			@RequestParam final String toDate, @RequestParam final String id,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("searchFpxTxnSummary admin" + fromDate + "::" + toDate);
		model.addAttribute("role",transactionService.getRoleFromUserName(principal.getName()));


		String fromDate1 = HtmlUtils.htmlEscape(fromDate);
		String toDate1 = HtmlUtils.htmlEscape(toDate);

		PageBean pageBean = new PageBean("transactions list", "transaction/FPXSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		// List<Merchant> merchant1 = merchantService.loadFpxMerchant();

		if (id != "") {
			Merchant midDetails = mobileUserService.loadIserMidDetails(Long.parseLong(id));
			transactionService.listFPXTransactionByMid(paginationBean, midDetails, fromDate1, toDate1);
		} else {
			transactionService.listFPXTransactionsearch(paginationBean, fromDate1, toDate1);
		}

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		// model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/exportFpxTxnSummary" }, method = RequestMethod.GET)
	public ModelAndView getExportfpxSettlemen(final Model model, @RequestParam final String fromDate,
			@RequestParam final String toDate, @RequestParam final String id,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam(required = false) String export, final java.security.Principal principal) {
		model.addAttribute("role",transactionService.getRoleFromUserName(principal.getName()));


		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		// List<Merchant> merchant1 = merchantService.loadFpxMerchant();

		if (id != "") {
			Merchant midDetails = mobileUserService.loadIserMidDetails(Long.parseLong(id));
			transactionService.listFPXTransactionByMidexport(paginationBean, midDetails, fromDate, toDate);
		} else {
			transactionService.listFPXTransactionsearchexport(paginationBean, fromDate, toDate);
		}

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		List<FpxTransaction> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("fpxTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("fpxTxnPdf", "txnList", list);
		}

	}

	@RequestMapping(value = { "/listBoost" }, method = RequestMethod.GET)
	public String displayBoostTransactionSummary(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info(" transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/BoostSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllForsettlementTransaction(paginationBean, null, null, "BOOST");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	// rkboostqr
	@RequestMapping(value = { "/listBoostqr/{currPage}" }, method = RequestMethod.GET)
	public String displayBoostQrTransactionSummary(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info(" transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/BoostQRSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllForsettlementTransactionQR(paginationBean, null, null, "BOOST");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	// rkboostecom
	@RequestMapping(value = { "/listBoostecom/{currPage}" }, method = RequestMethod.GET)
	public String displayBoostEcomTransactionSummary(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info(" transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/BoostEcomSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllForsettlementTransactionEcom(paginationBean, null, null, "BOOST");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchBoostList" }, method = RequestMethod.GET)
	public String displayBoostTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		PageBean pageBean = new PageBean("transactions list", "transaction/BoostSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchAllForsettlementTransaction(paginationBean, fromDate, toDate, "BOOST");

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	// rkboostecom
	@RequestMapping(value = { "/searchBoostecomList" }, method = RequestMethod.GET)
	public String displayBoostecomTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		PageBean pageBean = new PageBean("transactions list", "transaction/BoostEcomSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchecomForsettlementTransaction(paginationBean, date, date1, "BOOST");

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	// rkboostqr
	@RequestMapping(value = { "/searchBoostqrList" }, method = RequestMethod.GET)
	public String displayBoostqrTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		PageBean pageBean = new PageBean("transactions list", "transaction/BoostQRSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchqrForsettlementTransaction(paginationBean, date, date1, "BOOST");

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/exportBoost", method = RequestMethod.GET)
	public ModelAndView getExcelBoost(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

//		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "BOOST");
		transactionService.searchAllForsettlementTransactionForBoostExport(paginationBean, date, date1, "BOOST",
				export);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
			// response
		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("BoostTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("BoostTxnPdf", "txnList", list);
		}
	}

	// rkboostqr
	@RequestMapping(value = "/exportBoostQR", method = RequestMethod.GET)
	public ModelAndView getExcelBoostQR(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchqrForsettlementTransaction(paginationBean, date, date1, "BOOST");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("BoostQRTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("BoostQRTxnPdf", "txnList", list);
		}
	}

	// rkboostecom
	@RequestMapping(value = "/exportBoostecom", method = RequestMethod.GET)
	public ModelAndView getExcelBoostEcom(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchecomForsettlementTransaction(paginationBean, date, date1, "BOOST");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("BoostEcomTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("BoostEcomTxnPdf", "txnList", list);
		}
	}

	@RequestMapping(value = { "/allUMlist/{currPage}" }, method = RequestMethod.GET)
	public String displayAllUMTransactionSummary(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal, @RequestParam(required = false) final String date,
			@RequestParam(required = false) final String date1, @RequestParam(required = false) final String status,
			@RequestParam(required = true, defaultValue = "1") final String responseData) {
		logger.info("about to list all  transaction");

		PageBean pageBean = new PageBean("transactions list", "transaction/UmAlltransactionList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		logger.info(" All Transaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		logger.info("all transaction list status: " + status);
		String fromDate1 = null;
		String toDate1 = null;

		String status1 = null;
		if ((date != null && date1 != null) && (!date.isEmpty() && !date1.isEmpty())) {
			try {
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date1));
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		if (status != null) {// || !(status.equals(""))){
			/*
			 * status1 = ""; }else{
			 */
			status1 = status;
		}
		/*
		 * transactionService.listAllTransactionDetailsbyAdmin(paginationBean,
		 * fromDate1, toDate1,status1);
		 * 
		 */
		transactionService.listAllUmTransactionDetailsbyAdmin(paginationBean, fromDate1, toDate1, status1);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/allUmSearch1" }, method = RequestMethod.GET)
	public String displayAllUmTransactionList(final Model model, @RequestParam(required = false) final String date,
			@RequestParam(required = false) final String date1, @RequestParam(required = false) final String status,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("all  Transaction in search controller");
		logger.info("inside search controler " + date + " " + date1);

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		String dat = null;
		String dat1 = null;
		String status1 = null;

		if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())) {
			try {
				dat = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
				dat1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
				logger.info("inside search controler " + dat + " " + dat1);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		if (status != null) {

			status1 = status;

		}
		PageBean pageBean = new PageBean("transactions list", "transaction/UmAlltransactionList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		transactionService.listAllUmTransactionDetailsbyAdmin(paginationBean, dat, dat1, status1);

		model.addAttribute("date", dat);
		model.addAttribute("date1", dat1);
		model.addAttribute("status", status1);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/allUmExport" }, method = RequestMethod.GET)
	public ModelAndView getAllUmExcel(@RequestParam(required = false) final String date,
			@RequestParam(required = false) final String date1, @RequestParam(required = false) final String status,
			@RequestParam(required = false) String export, final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list all  Transaction export" + status);

		String fromDate1 = null;
		String toDate1 = null;
		String status1 = null;
		if ((date != null && date1 != null) && (!date.isEmpty() && !date1.isEmpty())) {

			try {
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date1));

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (status != null) {// || status.equals("F")){

			status1 = status;

		}

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllUmTransactionDetailsbyAdmin(paginationBean, fromDate1, toDate1, status1);

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

	// san exylink lite

	@RequestMapping(value = { "/ezylinkliteList/{currPage}" }, method = RequestMethod.GET)
	public String displayEzyLinkLiteTransactionSummary(final Model model,

			@PathVariable final int currPage, final java.security.Principal principal) {
		logger.info("um transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/EzyLinkliteSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllForsettlementTransaction(paginationBean, null, null, "EZYLINKLITE");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	// sanezylink lite

	@RequestMapping(value = { "/searchEzylite" }, method = RequestMethod.GET)
	public String displayEzyliteTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		String txnType = null;
		logger.info("search  Transaction" + txnType);
		PageBean pageBean = new PageBean("transactions list", "transaction/EzyLinkliteSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "EZYLINKLITE");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("txnType", txnType);
		request.setAttribute("txnType", txnType);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/exportEzylite", method = RequestMethod.GET)
	public ModelAndView getExcelEzylite(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchAllForsettlementTransaction(paginationBean, date, date1, "EZYLINKLITE");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("paydeeTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("paydeeTxnPdf", "txnList", list);
		}
	}

	// EZYWIRE+ SUMMARY RK

	@RequestMapping(value = { "/umEzywireplusList/{currPage}" }, method = RequestMethod.GET)
	public String displayUmEzywireplusTransactionSummary(final Model model,

			@PathVariable final int currPage, final java.security.Principal principal) {
		logger.info("um transaction summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUmEzywireplusList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllUmEzywireplusTransaction(paginationBean, null, null, "ALL");

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchumEzywireplus" }, method = RequestMethod.GET)
	public String displayUmEzywireplusTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1,
			/* @RequestParam final String txnType, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		String txnType = null;
		logger.info("search  Transaction" + txnType);
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUmEzywireplusList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.searchAllUmEzywireplusTransaction(paginationBean, date, date1, txnType);
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("txnType", txnType);
		request.setAttribute("txnType", txnType);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/exportUmEzywireplus", method = RequestMethod.GET)
	public ModelAndView getUmExcelEzywireplus(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		String txnType = null;
		logger.info("Um Export Transaction Summary.." + txnType);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchAllUmEzywireplusTransaction(paginationBean, date, date1, txnType);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
			// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("EzywireplusTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("EzywireplusTxnPdf", "txnList", list);
		}
	}

	// rk

	@RequestMapping(value = { "/umSplitList/{currPage}" }, method = RequestMethod.GET)
	public String umSplitList(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info(" UM-EZYSPLIT Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMSplitList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-EZYSPLIT Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		transactionService.listUMSplitTransaction(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {

			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}
	// rk

	@RequestMapping(value = { "/searchUMSplit" }, method = RequestMethod.GET)
	public String searchUMSplit(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  UM-SPLIT Transaction ");
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMSplitList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		transactionService.listUMSplitTransaction(paginationBean, date, date1, "ALL");
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	// rk

	@RequestMapping(value = "/umSplitExport", method = RequestMethod.GET)
	public ModelAndView umSplitExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//	                @RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("UM_EZYSPLIT Export ");

		/* Merchant currentMerchant = merchantService.loadMerchant(myName); */
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		/*
		 * logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		 */

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportUMSplitTransactionAdmin(paginationBean, dat, dat1, "ALL");
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("umAdminTxnExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("umAdminTxnPdf", "umTxnList", list1);
		}

	}

	// Grabpay summary new
	@RequestMapping(value = { "/AdminGrabpayList" }, method = RequestMethod.GET)
	public String displaysettlementSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("Grabpay Summary Admin");

		PageBean pageBean = new PageBean("transactions list", "transaction/GrabpaySummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		logger.info("Grabpay Summary Admin:" + principal.getName());

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		model.addAttribute("pageBean", pageBean);

//		transactionService.ListGrabpaySummaryAdmin(paginationBean, currentMerchant, null, null);
		transactionService.ListGrabpaySummaryAdmin(paginationBean, currentMerchant, null, null, null, "NULL", null);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	// Grabpay summary new
	@RequestMapping(value = { "/SearchGrabpayList" }, method = RequestMethod.GET)
	public String displaysettlesearch(final Model model, final java.security.Principal principal,
			@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		logger.info("about to list settelement  summary");

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/GrabpaySummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		logger.info(" Search Grabpay Summary Admin:" + principal.getName());

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		model.addAttribute("pageBean", pageBean);

		// transactionService.ListGrabpaySummaryAdmin(paginationBean, currentMerchant,
		// date, date1);
		transactionService.ListGrabpaySummaryAdmin(paginationBean, currentMerchant, fromDate, toDate, null, "NULL",
				null);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response

		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/ExportGrabpayList" }, method = RequestMethod.GET)
	public ModelAndView displaysettleexport(@RequestParam(required = false) final String date,
			@RequestParam(required = false) final String date1, HttpServletRequest request,
			@RequestParam(required = false) String export, final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info(" Export Grabpay Summary Admin:");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

//		transactionService.ListGrabpaySummaryAdmin(paginationBean, currentMerchant, date, date1, null, "NULL");
		transactionService.ListGrabpaySummaryAdmin(paginationBean, currentMerchant, date, date1, null, "NULL", export);
		// transactionService.ListGrabpaySummaryAdmin(paginationBean, currentMerchant,
		// date, date1);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<ForSettlement> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("grapPayTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("grapPayTxnPdf", "txnList", list);
		}

	}

	// EZYSETTLE SUMMARY BY ADMIN - START (27/03/2022)

//	@RequestMapping(value = { "/EzySettleList/{currPage}" }, method = RequestMethod.GET)
//	public String EzySettleList(final Model model, @PathVariable final int currPage,
//			final java.security.Principal principal) {
//
//		logger.info("EZYSETTLE SUMMARY BY ADMIN");
//		PageBean pageBean = new PageBean("transactions list", "transaction/EzySettleSummaryList", Module.TRANSACTION,
//				"transaction/sideMenuTransaction");
//		model.addAttribute("pageBean", pageBean);
//		logger.info("EZYSETTLE SUMMARY BY ADMIN USERNAME:" + principal.getName());
//		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
//		paginationBean.setCurrPage(currPage);
//
//		transactionService.ListofEzySettleSummary(paginationBean, null, null, null);
//
//		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
//				|| paginationBean.getItemList().size() == 0) {
//			model.addAttribute("responseData", "No Records found"); // table
//																	// response
//		} else {
//			model.addAttribute("responseData", null);
//		}
//		model.addAttribute("paginationBean", paginationBean);
//
//		return TEMPLATE_DEFAULT;
//
//	}
	
//	@RequestMapping(value = { "/EzySettleList/{currPage}" }, method = RequestMethod.GET)
//	 public String EzySettleList(final Model model, @PathVariable final int currPage,
//	   final java.security.Principal principal) {
//
//	  logger.info("EZYSETTLE SUMMARY BY ADMIN");
//	  PageBean pageBean = new PageBean("transactions list", "transaction/EzySettleSummaryList", Module.TRANSACTION,
//	    "transaction/sideMenuTransaction");
//	  model.addAttribute("pageBean", pageBean);
//	  logger.info("EZYSETTLE SUMMARY BY ADMIN USERNAME:" + principal.getName());
//	  PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
//	  paginationBean.setCurrPage(currPage);
//
//	  transactionService.ListofEzySettleSummary(paginationBean, null, null,false);
//
//	  if (paginationBean.getItemList() == null || paginationBean.getItemList().isEmpty()
//	    ) {
//	   model.addAttribute("responseData", "No Records found"); // table
//	                 // response
//	  } else {
//	   model.addAttribute("responseData", null);
//	  }
//	  model.addAttribute("paginationBean", paginationBean);
//
//	  return TEMPLATE_DEFAULT;
//
//	 }
//	 

//	@RequestMapping(value = { "/searchEzySettleList" }, method = RequestMethod.GET)
//	public String searchEzySettleList(HttpServletRequest request, final Model model, @RequestParam final String date,
//			@RequestParam final String date1, @RequestParam final String txntype,
//			@RequestParam(required = false, defaultValue = "1") final int currPage) {
//
//		logger.info("SEARCH ------ EZYSETTLE SUMMARY BY ADMIN");
//
//		String fromDate = HtmlUtils.htmlEscape(date);
//		String toDate = HtmlUtils.htmlEscape(date1);
//
//		PageBean pageBean = new PageBean("transactions list", "transaction/EzySettleSummaryList", Module.TRANSACTION,
//				"transaction/sideMenuTransaction");
//		model.addAttribute("pageBean", pageBean);
//
//		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
//		paginationBean.setCurrPage(currPage);
//
//		transactionService.ListofEzySettleSummary(paginationBean, fromDate, toDate, txntype);
//
//		model.addAttribute("paginationBean", paginationBean);
//
//		return TEMPLATE_DEFAULT;
//
//	}

//	@RequestMapping(value = "/exportEzySettleList", method = RequestMethod.GET)
//	public ModelAndView exportsearchEzySettleList(@RequestParam final String date, @RequestParam final String date1,
//			@RequestParam final String txntype, @RequestParam(required = false, defaultValue = "1") final int currPage,
//			final Model model, @RequestParam(required = false) String export) {
//
//		logger.info("EXPORT ------ EZYSETTLE SUMMARY BY ADMIN");
//		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
//		paginationBean.setCurrPage(currPage);
//
//		transactionService.ListofEzySettleSummary(paginationBean, date, date1, txntype);
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
//			return new ModelAndView("EzySettleListExcel", "txnList", list);
//		}
//	}

	// EZYSETTLE SUMMARY BY ADMIN - END (27/03/2022)

	// M1 PAY SUMMARY BY ADMIN - START (29/07/2022)

	@RequestMapping(value = { "/m1PayTransaction" }, method = RequestMethod.GET)
	public String m1PayTransaction(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("M1 PAY SUMMARY BY ADMIN");
		PageBean pageBean = new PageBean("transactions list", "transaction/M1PaySummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("M1 PAY SUMMARY BY ADMIN USERNAME:" + principal.getName());
		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.ListofM1PaySummary(paginationBean, null, null, null, null);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/searchm1PayTransaction" }, method = RequestMethod.GET)
	public String searchm1PayTransaction(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam final String txntype,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("SEARCH ------ M1 PAY SUMMARY BY ADMIN");

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/M1PaySummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.ListofM1PaySummary(paginationBean, fromDate, toDate, txntype, null);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/exportm1PayTransaction", method = RequestMethod.GET)
	public ModelAndView exportm1PayTransaction(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam final String txntype, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final Model model, @RequestParam(required = false) String export) {

		logger.info("EXPORT ------ M1 PAY SUMMARY BY ADMIN");
		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.ListofM1PaySummary(paginationBean, date, date1, txntype, export);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<SettlementModel> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("M1PayListExcel", "txnList", list);

		} else {
			return new ModelAndView("M1PayListExcel", "txnList", list);
		}
	}

	// M1 PAY SUMMARY BY ADMIN - END (29/07/2022)

	// BNPL SUMMARY ADMIN _ START(30/11/2022)

	@RequestMapping(value = { "/bnplSummaryAdmin" }, method = RequestMethod.GET)
	public String bnplTransaction(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("BNPL SUMMARY BY ADMIN");
		PageBean pageBean = new PageBean("transactions list", "transaction/BnplSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("BNPL SUMMARY BY ADMIN USERNAME:" + principal.getName());
		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.ListofBnplSummary(paginationBean, null, null, null);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	// searchbnplADMIN

	@RequestMapping(value = { "/searchBnplAdmin" }, method = RequestMethod.GET)
	public String searchBnplTransaction(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam final String txntype,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("SEARCH ------ BNPL SUMMARY BY ADMIN");

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("transactions list", "transaction/BnplSummaryList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.ListofBnplSummary(paginationBean, fromDate, toDate, txntype);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	// ExportBnplAdmin

	@RequestMapping(value = "/exportBnplAdmin", method = RequestMethod.GET)
	public ModelAndView exportBnplTransaction(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam final String txntype, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final Model model, @RequestParam(required = false) String export) {

		logger.info("EXPORT ------ BNPL SUMMARY BY ADMIN");
		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.ListofBnplSummary(paginationBean, date, date1, txntype);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<SettlementModel> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("BnplListExcel", "txnList", list);

		} else {
			return new ModelAndView("BnplListExcel", "txnList", list);
		}
	}

	@RequestMapping(value = { "/merchantTopupList" }, method = RequestMethod.GET)
	public String merchantTopupList(final Model model, final java.security.Principal principal) {

		PageBean pageBean = new PageBean("merchant Topup List", "PayoutUser/merchantTopupListByAdmin",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		// oldtopup
		// List<Merchant> merchant = merchantService.loadMerchant();
		List<Merchant> merchant = merchantService.loadMerchant1();
		for (Merchant m : merchant) {
			if (m != null) {

				if (m.getBusinessName() != null && !m.getBusinessName().isEmpty()) {
					m.setBusinessName(m.getBusinessName().toUpperCase());

				} else {
					m.setBusinessName("NIL");

				}

			}
		}
		logger.info("Username : " + principal.getName());
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/merchantDetails" }, method = RequestMethod.GET)
	public String displayMerchantDetails(final Model model, @RequestParam("id") Long id,

			final java.security.Principal principal, HttpServletRequest request) {

		logger.info("Merchant ID: " + id);
		String currentAmount = null;

		Merchant merchant = merchantService.loadMerchantbyid(id);

		PageBean pageBean = new PageBean("merchant Topup List", "PayoutUser/merchantTopupListByAdmin",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		// oldtopup
		// List<Merchant> merchant1 = merchantService.loadMerchant();
		List<Merchant> merchant1 = merchantService.loadMerchant1();
		PayoutGrandDetail payoutDetail = merchantService.PayoutGrandDetailbymerchantid(id);

		String id2 = Long.toString(id);
		SettlementBalance settleBalance = merchantService.settleMerchant(id2);
		logger.info(" ******old NetAmt  : " + settleBalance.getNetAmt());
		logger.info(" ******old DepositAmt  : " + settleBalance.getDepositAmount());
		logger.info(" ******old TotalAmt  : " + settleBalance.getTotalAmount());

		if (payoutDetail.getSaturationAmount() != null && !payoutDetail.getSaturationAmount().isEmpty()) {
			double amount = Double.parseDouble(payoutDetail.getSaturationAmount());
			amount = amount / 100;
			DecimalFormat myFormatter = new DecimalFormat("###0.00");
			currentAmount = myFormatter.format(amount);

			logger.info("Available Saturation Amount: " + currentAmount);
		} else {
			currentAmount = "0.00";
			logger.info("Available Saturation Amount: " + currentAmount);
		}

		Set<String> merchantNameList = new HashSet<String>();

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("merchantName", merchant.getUsername());
		model.addAttribute("merchant", merchant);
		model.addAttribute("merchantNameList", merchantNameList);
		model.addAttribute("currentAmount", settleBalance.getDepositAmount());
		model.addAttribute("netAmt", settleBalance.getNetAmt());

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/updateTopup" }, method = RequestMethod.GET)
	public String updatePayoutStatus(@RequestParam final Long id, final Model model,
			@RequestParam final String topupAmount, @RequestParam final String oldDepositAmount,
			final java.security.Principal principal, final HttpServletRequest request) {

		PageBean pageBean = new PageBean("merchant Topup List", "PayoutUser/merchantTopupListByAdmin",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		logger.info("Deposit Amount Update via Admin Portal ");
		logger.info("Merchant ID : " + id);
		logger.info("New Deposit Amount: " + topupAmount);
		logger.info("oldDepositAmount Amount: " + oldDepositAmount);
		DecimalFormat myFormatter = new DecimalFormat("###0.00");

		double oldDepositAmt = Double.parseDouble(oldDepositAmount);
		double newDepositAmt = Double.parseDouble(topupAmount);

		double depositAmt = oldDepositAmt + newDepositAmt;

		String depositAmount = myFormatter.format(depositAmt);

		Merchant merchant = merchantService.loadMerchantbyid(id);
		PayoutGrandDetail oldpayoutDetail = merchantService.PayoutGrandDetailbymerchantid(id);

		String topupAmt = null;
		String limitAmt = null;
		String settlement = null;

		// Update the Fields of Payout Grand Detail Table - Start
		if (oldpayoutDetail != null) {

			// User Topup Amount and Topup Date - Start

			if (depositAmount.contains(".")) {

				topupAmt = String.format("%012d", (long) Double.parseDouble(depositAmount.replace(".", "")));

				logger.info("Pharsed Topup Amount (.) :" + topupAmt);
			} else {
				topupAmt = String.format("%012d", (long) Double.parseDouble(depositAmount) * 100);
				logger.info("Pharsed Topup Amount :" + topupAmt);
			}

			Date currentDate = new Date();
			String topupDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);
			logger.info("Topup Date : " + topupDate);
			logger.info("topupAmt  : " + depositAmount);
			oldpayoutDetail.setTopupAmount(topupAmt);
			String idString = String.valueOf(id);
			logger.info("ID String  : " + idString);

			boolean responseData = false;
//			responseData = merchantService.addDepositAmount(depositAmount, idString);
			LoginResponse responseValue = new LoginResponse();

			// Obtain mobiApiKey by merchantId
			String motoApiKey = merchantDao.loadMobileUserById(Long.valueOf(id)).getMotoApiKey();

			responseValue = merchantService.addDepositAmount(oldDepositAmount, topupAmount, id, motoApiKey);

			logger.info("responseData from external API : " + responseData);
			if (responseData) {
				// mail function
				String responsedata = sendEmailtoMerchant(oldDepositAmt, newDepositAmt, merchant, depositAmt);

			} else {
				logger.info("Deposit Not update ");
			}
//			merchantService.addDepositAmount(depositAmount,idString);
			// User Topup Amount - End

			merchantService.savePayoutGrandDetail(oldpayoutDetail);
		}
		List<Merchant> merchantList = merchantService.loadMerchant1();

		for (Merchant m : merchantList) {
			if (m != null) {

				if (m.getBusinessName() != null && !m.getBusinessName().isEmpty()) {
					m.setBusinessName(m.getBusinessName().toUpperCase());

				} else {
					m.setBusinessName("NIL");

				}

			}
		}
		logger.info("Username : " + principal.getName());
		model.addAttribute("loginname", principal.getName());

		model.addAttribute("merchant1", merchantList);
		model.addAttribute("pageBean", pageBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/fpxSettlementSummary" }, method = RequestMethod.GET)
	public String fpxSettlementSummary(final Model model,

			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("fpxSettlementSummary");
		PageBean pageBean = new PageBean("transactions list", "transaction/FPXDailySettlementList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/exportFpxSettlementSummary" }, method = RequestMethod.GET)
	public ModelAndView getExportfpxSettlement(final Model model, @RequestParam final String fromDate,
			@RequestParam final String id, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam(required = false) String export, final java.security.Principal principal) {

		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		if (id != "") {
			Merchant midDetails = mobileUserService.loadIserMidDetails(Long.parseLong(id));

		} else {
			transactionService.listFPXSettlementsearchexport(paginationBean, fromDate);
		}
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found");
		} else {
			model.addAttribute("responseData", null);
		}
		List<FpxTransaction> list2 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {
			return new ModelAndView("fpxSettlement", "txnList2", list2);
		} else {
			return new ModelAndView("fpxSettlement", "txnList2", list2);
		}
	}

	public static String sendEmailtoMerchant(double oldTopupAmount, double newTopupAmount, Merchant merchant,
			double depositAmount) {

		DecimalFormat myFormatter = new DecimalFormat("#,##0.00");
		String oldDepositAmt = myFormatter.format(oldTopupAmount);
		String newDepositAmt = myFormatter.format(newTopupAmount);
		String totalDepositAmt = myFormatter.format(depositAmount);

		logger.info("oldDepositAmt :" + oldDepositAmt);
		logger.info("newDepositAmt :" + newDepositAmt);
		logger.info("totalDepositAmt :" + totalDepositAmt);

		String response = null;
		// EZYWIRE AS USERNAME & password mobiversa
		String fromAddress = PropertyLoad.getFile().getProperty("FROMMAIL");

		logger.info("Merchant Mail :" + merchant.getEmail());
		String toAddress = merchant.getEmail() + "," + PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_TO");
		String ccAddress = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_MAIL_CC");
		String subject = PropertyLoad.getFile().getProperty("MERCHANT_TOPUP_TEXT_BODY");
		String fromName = PropertyLoad.getFile().getProperty("FROMNAME");

		logger.info("Email from :" + fromAddress);
		logger.info("Sending Email to :" + toAddress);
		logger.info("Email subject :" + subject);

		logger.info("Email From Name :" + fromName);
		logger.info("Sending Email to :" + toAddress);
		logger.info("Sending Email to :" + toAddress);

		StringBuilder result = new StringBuilder();
		result.append("Dear ").append(merchant.getBusinessName()).append(",\n\n").append(
				"We're pleased to notify you that a Payout Deposit Amount has been approved and updated in your Merchant Portal.")
				.append("\n\nHere are the details: \n\nPrevious Balance (a): RM ").append(oldDepositAmt)
				.append("\nDeposit Amount (b): RM ").append(newDepositAmt).append("\nTotal Balance (a+b): RM ")
				.append(totalDepositAmt)
				.append("\n\nFor any inquiries or support, reach out to us on our WhatsApp Group or csmobi@gomobi.io")
				.append("\n\nThis is an automated notification. Kindly refrain from replying.\n\nRegards,\n")
				.append("Mobi Asia Sdn. Bhd.\nWebsite: https://gomobi.io");

		String textBody = result.toString();

		ElasticEmail message = new ElasticEmail(fromAddress, subject, fromName, toAddress, ccAddress, ccAddress, null,
				textBody);
		ElasticEmailClient client = new ElasticEmailClient();

		try {
			response = client.sendMessage1(message);
			logger.info("Email Sent Successfully to " + toAddress);
			logger.info("output Response : " + response);

		} catch (Exception pe) {
			response = "400";
			logger.info("Invalid Signature Base64 String");
		}
		return response;
	}

	// Divya changes

	// fpx Monthly Settlement Report

	@RequestMapping(value = { "/fpxSettlementReport" }, method = RequestMethod.GET)
	public String fpxSettlementReport(final Model model,

			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("fpxSettlementSummary");
		PageBean pageBean = new PageBean("transactions list", "transaction/FPXMonthlySettlementReport",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		List<Object[]> businessNamesAndUsernames = transactionService.getBusinessNamesAndUsernames();
		model.addAttribute("businessNamesAndUsernames", businessNamesAndUsernames);

		return TEMPLATE_DEFAULT;
	}

	// Export Fpx Settlement Monthly Report

	@RequestMapping(value = { "/exportFpxMonthlySettlementSummary" }, method = RequestMethod.GET)
	public ModelAndView getExportfpxMonthlySettlement(final Model model, @RequestParam final String fromDate,
			@RequestParam(required = false) final String id, @RequestParam(required = false) final String businessName,
			@RequestParam final String username, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam(required = false) String export, final java.security.Principal principal) {

		logger.info("FpxMonthlySettlementSummary :");
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		if (id != "") {
			Merchant midDetails = mobileUserService.loadIserMidDetails(Long.parseLong(id));
			// transactionService.listFPXTransactionByMidexport(paginationBean, midDetails,
			// fromDate);
		} else {
			transactionService.FpxMonthlySettlementSummaryexport(paginationBean, fromDate, businessName, username);
			logger.info(username);
		}

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found");

		} else {
			model.addAttribute("responseData", null);

		}

		List<FpxTransaction> list2 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("FpxMonthlySettlementReport", "txnList3", list2);

		} else {
			return new ModelAndView("FpxMonthlySettlementReport", "txnList3", list2);
		}

	}

	// Boost Monthly Settlement Report

	@RequestMapping(value = { "/boostSettlementReport" }, method = RequestMethod.GET)
	public String boostSettlementReport(final Model model,

			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("boostSettlementReport");
		PageBean pageBean = new PageBean("transactions list", "transaction/BoostMonthlySettlementReport",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		List<Object[]> businessNamesAndUsernames = transactionService.getBusinessNamesAndUsernames();
		model.addAttribute("businessNamesAndUsernames", businessNamesAndUsernames);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/exportBoostMonthlySettlementSummary" }, method = RequestMethod.GET)
	public ModelAndView exportBoostMonthlySettlementSummary(final Model model, @RequestParam final String fromDate,
			@RequestParam(required = false) final String id, @RequestParam(required = false) final String businessName,
			@RequestParam final String username, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam(required = false) String export, final java.security.Principal principal) {

		logger.info("exportBoostMonthlySettlementSummary :");
		PaginationBean<BoostDailyRecon> paginationBean = new PaginationBean<BoostDailyRecon>();
		paginationBean.setCurrPage(currPage);

		if (id != "") {
			Merchant midDetails = mobileUserService.loadIserMidDetails(Long.parseLong(id));
			// transactionService.listFPXTransactionByMidexport(paginationBean, midDetails,
			// fromDate);
		} else {
			transactionService.BoostMonthlySettlementSummaryexport(paginationBean, fromDate, businessName, username);
			logger.info(username);
		}

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found");

		} else {
			model.addAttribute("responseData", null);

		}

		List<BoostDailyRecon> list2 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("BoostMonthlySettlementReport", "txnList3", list2);
		} else {
			return new ModelAndView("BoostMonthlySettlementReport", "txnList3", list2);
		}

	}

	// host bank switch - Reterive all merchant List
	@RequestMapping(value = { "/allMerchantList" }, method = RequestMethod.GET)
	public String viewAllMerchantList(final Model model,
			@RequestParam(value = "VALUE", required = false) String selectedBank,
			final java.security.Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/AllMerchantList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");

		List<String> bankList = transactionService.getHostBankList();

		model.addAttribute("bankList", bankList);
		model.addAttribute("pageBean", pageBean);

		return TEMPLATE_DEFAULT;
	}

	// host bank switch - Update SellerId for allMerchants
	@RequestMapping(value = { "/updateAllMerchantSellerID" }, method = RequestMethod.GET)
	public String updateAllMerchantSellerID(final Model model,
			@RequestParam(value = "VALUE", required = true) String selectedBank,
			final java.security.Principal principal) {

		if (Objects.nonNull(selectedBank) && !selectedBank.equals("All")) {

			logger.info("Host bank switch API Triggered - For All Merchants, Selected Bank : " + selectedBank
					+ ", from: " + principal.getName());
			transactionService.updateAllMerchantSellerID(selectedBank);
		} else {
			logger.error("Invalid Request");
		}

		PageBean pageBean = new PageBean("transactions list", "transaction/AllMerchantList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");

		List<String> bankList = transactionService.getHostBankList();

		model.addAttribute("bankList", bankList);
		model.addAttribute("pageBean", pageBean);

		return TEMPLATE_DEFAULT;
	}

	// host bank switch - Update SellerId for Selected Merchants
	@RequestMapping(value = { "/updateSelectedMerchantSellerID" }, method = RequestMethod.POST)
	public String updateSelectedMerchantSellerID(final Model model, HttpServletRequest request,
			@RequestParam(value = "selectedMerchants", required = true) List<String> selectedMerchants,
			@RequestParam(value = "bank_name", required = true) String bankName,
			final java.security.Principal principal) {

		if (Objects.nonNull(bankName) && !bankName.equals("All")) {

			logger.info("Host bank switch API Triggered - For Merchant's : " + selectedMerchants + ", Selected Bank : "
					+ bankName + ", from: " + principal.getName());
			transactionService.updateSelectedMerchantSellerID(bankName, selectedMerchants);
		} else {
			logger.error("Invalid Request");
		}

		PageBean pageBean = new PageBean("transactions list", "transaction/CustomMerchantList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");

		List<String> bankList = transactionService.getHostBankList();
		List<Merchant> merchant = merchantService.loadMerchant();

		model.addAttribute("bankList", bankList);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/customMerchantList" }, method = RequestMethod.GET)
	public String CustomMerchantList(final Model model, final java.security.Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/CustomMerchantList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");

		List<String> bankList = transactionService.getHostBankList();
		List<Merchant> merchant = merchantService.loadMerchant();

		model.addAttribute("bankList", bankList);
		model.addAttribute("merchant1", merchant);
		model.addAttribute("pageBean", pageBean);

		return TEMPLATE_DEFAULT;
	}

	// withdraw and Deposit Summary

	@RequestMapping(value = { "/depositDetails" }, method = RequestMethod.GET)
	public String displayDepositdetails(final Model model, @RequestParam(required = false) final String fromDate,
			@RequestParam(required = false) final String toDate, @RequestParam(required = false) final String VALUE,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("Login to depositDetails ---> Summary");
		logger.info("Business Name: " + VALUE);
		String fromDateString = fromDate;
		String toDateString = toDate;

		PageBean pageBean = new PageBean("transactions list", "transaction/DepositDetails", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("depositDetails Summary:" + principal.getName());
		PaginationBean<WithdrawDeposit> paginationBean = new PaginationBean<WithdrawDeposit>();
		paginationBean.setCurrPage(currPage);

		List<Object[]> businessNamesAndIds = transactionService.getBusinessNamesAndIds();
		logger.info("Load--->businessNamesAndIds");
		transactionService.listDepositDetails(paginationBean, fromDateString, toDateString, currPage);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		// model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("fromDate", fromDateString); // Add fromDate to model
		model.addAttribute("toDate", toDateString);
		model.addAttribute("businessNamesAndIds", businessNamesAndIds);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchdepositDetailsUsingId" }, method = RequestMethod.GET)
	public String searchdepositDetailsUsingId(final Model model,
			@RequestParam(required = false, value = "merchantId") final String merchantId,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("searchdepositDetailsUsingId ---> Summary");
		logger.info("Business Name: " + merchantId);
		Merchant currentMerchant = merchantService.loadMerchantById(merchantId);
		String merchantName = currentMerchant.getBusinessName();
		logger.info("Merchant Name ::::" + merchantName);

		PageBean pageBean = new PageBean("transactions list", "transaction/DepositDetails", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("searchdepositDetailsUsingId::::::" + principal.getName());
		PaginationBean<WithdrawDeposit> paginationBean = new PaginationBean<>();
		paginationBean.setCurrPage(currPage);

		List<Object[]> businessNamesAndIds = transactionService.getBusinessNamesAndIds();

		logger.info("Load--->Deposit Details using MerchantId");
		transactionService.listDepositDetailsUsingId(paginationBean, currPage, merchantId);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
			// response
		} else {
			model.addAttribute("responseData", null);
		}

		// model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("merchantId", merchantId);
		model.addAttribute("merchantName", merchantName);
		model.addAttribute("businessNamesAndIds", businessNamesAndIds);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/withdrawDetails" }, method = RequestMethod.GET)
	public String displaywithdrawDetails(final Model model, @RequestParam(required = false) final String fromDate,
			@RequestParam(required = false) final String toDate,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("Login to withdrawDetails ---> Summary");

		String fromDateString = fromDate;
		String toDateString = toDate;

		PageBean pageBean = new PageBean("transactions list", "transaction/WithdrawDetails", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("WithdrawDetails Summary:" + principal.getName());
		PaginationBean<WithdrawDeposit> paginationBean = new PaginationBean<WithdrawDeposit>();
		paginationBean.setCurrPage(currPage);

		List<Object[]> businessNamesAndIds = transactionService.getBusinessNamesAndIds();

		transactionService.listWithdrawDetails(paginationBean, fromDateString, toDateString, currPage);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		// model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("fromDate", fromDateString); // Add fromDate to model
		model.addAttribute("toDate", toDateString);
		model.addAttribute("businessNamesAndIds", businessNamesAndIds);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/searchwithdrawDetailsUsingId" }, method = RequestMethod.GET)
	public String searchwithdrawDetailsUsingId(final Model model,
			@RequestParam(required = false, value = "merchantId") final String merchantId,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("searchwithdrawDetailsUsingId ---> Summary");
		logger.info("Business Name: " + merchantId);
		Merchant currentMerchant = merchantService.loadMerchantById(merchantId);
		String merchantName = currentMerchant.getBusinessName();
		logger.info("Merchant Name ::::" + merchantName);

		PageBean pageBean = new PageBean("transactions list", "transaction/WithdrawDetails", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("searchwithdrawDetailsUsingId::::::" + principal.getName());
		PaginationBean<WithdrawDeposit> paginationBean = new PaginationBean<>();
		paginationBean.setCurrPage(currPage);

		List<Object[]> businessNamesAndIds = transactionService.getBusinessNamesAndIds();

		logger.info("Load--->Withdraw Details using MerchantId");
		transactionService.listWithdrawDetailsUsingId(paginationBean, currPage, merchantId);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
			// response
		} else {
			model.addAttribute("responseData", null);
		}

		// model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("merchantId", merchantId);
		model.addAttribute("merchantName", merchantName);
		model.addAttribute("businessNamesAndIds", businessNamesAndIds);

		return TEMPLATE_DEFAULT;
	}

	// dhivya changes

	@RequestMapping(value = { "/FinanceReport/{currPage}" }, method = RequestMethod.GET)
	public String FinanceReport(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info("FinanceReport for Payin and Payout Transactions:::");
		PageBean pageBean = new PageBean("transactions list", "transaction/financeReport", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		List<Object[]> businessNamesAndUsernames = transactionService.getBusinessNamesAndUsernames();
		logger.info("After this businessNamesAndUsernames Service:::");

		// transactionService.listpreauthfee(paginationBean, null, null);

		model.addAttribute("paginationBean", paginationBean);

		model.addAttribute("businessNamesAndUsernames", businessNamesAndUsernames);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = "/exportPayinTxnAsEmail", method = RequestMethod.GET)
	public String exportPayoutAsEmail(@RequestParam("date") String date, @RequestParam("date1") String date1,
			@RequestParam(value = "currPage", defaultValue = "1") final int currPage, final Model model,
			@RequestParam("emailId") String emailId, @RequestParam(value = "export", required = false) String export,
			@RequestParam("username") String username) {

		logger.info(
				"Export payin transaction as XLS......................................................................");

		try {
			logger.info("from date : " + date);
			logger.info("to date : " + date1);
			logger.info("UserName :" + username);
			final String fromDate = convertDateFormatTo_YYY_MM_dd(date);
			final String toDate = convertDateFormatTo_YYY_MM_dd(date1);
			logger.info("After format from date : " + date);
			logger.info("After format to date : " + date1);

			Merchant currentMerchant = merchantService.loadMerchant(username);
			String merchantName = currentMerchant.getBusinessShortName();
			String businessName = currentMerchant.getBusinessName();
			Long merchantId = currentMerchant.getId();

			if ("EXCEL".equalsIgnoreCase(export)) {
				ExecutorService executor = Executors.newSingleThreadExecutor();

				CompletableFuture.runAsync(() -> {
					List<FinanceReport> payinReportData = transactionService.getPayinTxnDetailsBetweenDates(fromDate,
							toDate, currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getFpxMid(),
							currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getMid(),
							currentMerchant.getMid().getEzywayMid(), currentMerchant.getMid().getUmMid(),
							currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getBnplMid(),
							currentMerchant.getMid().getBoostMid(), currentMerchant.getMid().getTngMid(),
							currentMerchant.getMid().getShoppyMid(), currentMerchant.getMid().getGrabMid(), currentMerchant.getMid().getFiuuMid(),
							currentMerchant);

					byte[] payinExcelContent = transactionService
							.generatePayinDetailExcelContentForEmail(payinReportData, fromDate, toDate, businessName);

					logger.info("generatePayinDetailExcelContentForEmail");
					String enblPayout = currentMerchant.getEnblPayout();
					logger.info("Payout is Enabled");
					List<FinanceReport> payoutReportData = transactionService.getPayoutTxnDetailsBetweenDates(fromDate,
							toDate, merchantId, currentMerchant);
					byte[] payoutExcelContent = transactionService
							.generatePayoutReportXLSContentForEmail(payoutReportData, fromDate, toDate, merchantName);

					Map<String, byte[]> attachments = new HashMap<>();
					attachments.put("PayinTransactionReport.xls", payinExcelContent);
					attachments.put("PayoutTransactionReport.xls", payoutExcelContent);

					new FinanceReportUtils().sendEmailWithAttachments(attachments, fromDate, toDate, emailId);
				}, executor).exceptionally(ex -> {
					logger.error("Exception in sending E-mail: " + ex.getMessage(), ex);
					return null;
				}).thenRun(executor::shutdown);
			} else {
				logger.warn("Export type must be EXCEL");
			}
		} catch (Exception e) {
			logger.error("Exception in export XLS for payout user: ", e);
		}

		PageBean pageBean = new PageBean("payout transactions list", "transaction/financeReport", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<>();
		paginationBean.setCurrPage(currPage);
		List<Object[]> businessNamesAndUsernames = transactionService.getBusinessNamesAndUsernames();

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("businessNamesAndUsernames", businessNamesAndUsernames);
		model.addAttribute("isEmailSent", "true");

		return TEMPLATE_DEFAULT;
	}

	public static String convertDateFormatTo_YYY_MM_dd(String inputDate) {
		// Define input and output date formats
		try {
			DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// Parse input date and format it to desired output format
			LocalDate date = LocalDate.parse(inputDate, inputFormatter);
			String outputDate = date.format(outputFormatter);
			return outputDate;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Fpx host switch - Fpx Host details
	@RequestMapping(value = { "/fpxHostList" }, method = RequestMethod.GET)
	public String hostDetailsList(final Model model,
			@RequestParam(value = "VALUE", required = false) String selectedBank,
			final java.security.Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/FpxHostSwitch_AllMerchants",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		List<String> fpxHostList = transactionService.getFpxHostList();

		model.addAttribute("fpxHostList", fpxHostList);
		model.addAttribute("pageBean", pageBean);

		return TEMPLATE_DEFAULT;
	}

	// Fpx host switch - Reterive all merchant List
	@RequestMapping(value = { "/allActiveMerchantsList" }, method = RequestMethod.GET)
	public String allActiveMerchantsList(final Model model, final java.security.Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/FpxHostSwitch_CustomMerchants",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		List<String> fpxHostList = transactionService.getFpxHostList();
		List<Merchant> activeMerchantsList = merchantService.loadMerchant();

		model.addAttribute("fpxHostList", fpxHostList);
		model.addAttribute("merchant1", activeMerchantsList);
		model.addAttribute("pageBean", pageBean);

		return TEMPLATE_DEFAULT;
	}

	// FPX host switch - Update HostId for allMerchants
	@RequestMapping(value = { "/updateAllMerchantHostId" }, method = RequestMethod.GET)
	public String updateAllMerchantsHostId(final Model model,
			@RequestParam(value = "VALUE", required = true) String hostName, final java.security.Principal principal) {

		try {
			if (Objects.nonNull(hostName) && !hostName.equals("All")) {

				ExecutorService executor = Executors.newSingleThreadExecutor();
				CompletableFuture.runAsync(() -> {
					logger.info("FPX HostSwitch API Triggered - For All Merchants, Selected Host : " + hostName
							+ ", from: " + principal.getName());
					transactionService.updateAllMerchantsHostId(hostName);
				}, executor);
			} else {
				logger.error("Invalid Request");
			}
		} finally {
			PageBean pageBean = new PageBean("transactions list", "transaction/FpxHostSwitch_AllMerchants",
					Module.TRANSACTION, "transaction/sideMenuTransaction");

			List<String> fpxHostList = transactionService.getFpxHostList();

			model.addAttribute("fpxHostList", fpxHostList);
			model.addAttribute("isUpdated", true);
			model.addAttribute("pageBean", pageBean);
		}

		return TEMPLATE_DEFAULT;
	}

	// FPX host switch - Update HostId for Selected Merchants
	@RequestMapping(value = { "/updateSelectedMerchantHostId" }, method = RequestMethod.POST)
	public String updateSelectedMerchantsHostId(final Model model, HttpServletRequest request,
			@RequestParam(value = "selectedMerchants", required = true) List<String> selectedMerchants,
			@RequestParam(value = "host_name", required = true) String hostName,
			final java.security.Principal principal) {

		try {
			if (Objects.nonNull(hostName) && !hostName.equals("All")) {

				ExecutorService executor = Executors.newSingleThreadExecutor();
				CompletableFuture.runAsync(() -> {
					logger.info("Host bank switch API Triggered - For Merchant's : " + selectedMerchants
							+ ", Selected host: " + hostName + ", from: " + principal.getName());
					transactionService.updateSelectedMerchantHostId(hostName, selectedMerchants);
				}, executor).thenRun(() -> {
				    executor.shutdown();
				    try {
				        if (!executor.awaitTermination(2, TimeUnit.MINUTES)) {
				            executor.shutdownNow();
				        }
				    } catch (InterruptedException e) {
				        executor.shutdownNow();
				    }
				});;
			} else {
				logger.error("Invalid Request");
			}
		} finally {
			PageBean pageBean = new PageBean("transactions list", "transaction/FpxHostSwitch_CustomMerchants",
					Module.TRANSACTION, "transaction/sideMenuTransaction");

			List<String> fpxHostList = transactionService.getFpxHostList();
			List<Merchant> merchant = merchantService.loadMerchant();

			model.addAttribute("fpxHostList", fpxHostList);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("isUpdated", true);
			model.addAttribute("merchant1", merchant);
		}

		return TEMPLATE_DEFAULT;
	}
	
	@RequestMapping(value = { "/EzySettleList/{currPage}" }, method = RequestMethod.GET)
    public String EzySettleList(final Model model, @PathVariable final int currPage,
          final java.security.Principal principal) {

       logger.info("EZYSETTLE SUMMARY BY ADMIN");
       PageBean pageBean = new PageBean("transactions list", "transaction/EzySettleSummaryList", Module.TRANSACTION,
             "transaction/sideMenuTransaction");
       model.addAttribute("pageBean", pageBean);
       logger.info("EZYSETTLE SUMMARY BY ADMIN USERNAME:" + principal.getName());
       PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
       paginationBean.setCurrPage(currPage);

       transactionService.ListofEzySettleSummary(paginationBean, null, null,false);

       if (paginationBean.getItemList() == null || paginationBean.getItemList().isEmpty()
             ) {
          model.addAttribute("responseData", "No Records found"); // table
                                                    // response
       } else {
          model.addAttribute("responseData", null);
       }
       model.addAttribute("paginationBean", paginationBean);

       return TEMPLATE_DEFAULT;

    }

    @RequestMapping(value = { "/searchEzySettleList" }, method = RequestMethod.GET)
    public String searchEzySettleList(HttpServletRequest request, final Model model, @RequestParam final String date,
          @RequestParam final String date1,
          @RequestParam(required = false, defaultValue = "1") final int currPage) {

       logger.info("SEARCH ------ EZYSETTLE SUMMARY BY ADMIN");

       String fromDate = HtmlUtils.htmlEscape(date);
       String toDate = HtmlUtils.htmlEscape(date1);

       PageBean pageBean = new PageBean("transactions list", "transaction/EzySettleSummaryList", Module.TRANSACTION,
             "transaction/sideMenuTransaction");
       model.addAttribute("pageBean", pageBean);

       PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
       paginationBean.setCurrPage(currPage);

       transactionService.ListofEzySettleSummary(paginationBean, fromDate, toDate,false);
       if (paginationBean.getItemList() == null || paginationBean.getItemList().isEmpty()
       ) {
          model.addAttribute("responseData", "No Records found"); // table
          // response
       } else {
          model.addAttribute("responseData", null);
       }

       model.addAttribute("paginationBean", paginationBean);

       return TEMPLATE_DEFAULT;

    }

    @RequestMapping(value = "/exportEzySettleList", method = RequestMethod.GET)
    public ModelAndView exportsearchEzySettleList(@RequestParam final String date, @RequestParam final String date1,
           @RequestParam(required = false, defaultValue = "1") final int currPage,
          final Model model, @RequestParam(required = false) String export) {

       logger.info("EXPORT ------ EZYSETTLE SUMMARY BY ADMIN");
       PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
       paginationBean.setCurrPage(currPage);

       transactionService.ListofEzySettleSummary(paginationBean, date, date1,true);
       logger.info("After Transaction DAo");

//     if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
//        logger.info("checkkkkkk");
//        model.addAttribute("responseData", "No Records found"); // table
//        // response
//
//     } else {
//        model.addAttribute("responseData", null);
//     }
//     logger.info(paginationBean.getItemList()==null);
//     List<SettlementModel> list = paginationBean.getItemList();
//     logger.info("Size of the list  "+list.toString());
//     if (list==null || list.isEmpty())
       if(paginationBean.getItemList()==null || paginationBean.getItemList().isEmpty())
       {
          logger.info("Inside null Or Empty List");
          PageBean pageBean = new PageBean("transactions list", "transaction/EzySettleSummaryList", Module.TRANSACTION,
                "transaction/sideMenuTransaction");
          model.addAttribute("pageBean", pageBean);
          paginationBean.setItemList(new ArrayList<>());
          logger.info("The Item list is "+paginationBean.getItemList()+"  and the size is ");
          model.addAttribute("paginationBean", paginationBean);
          model.addAttribute("exportData", "noRecords");
          ModelAndView modelAndView = new ModelAndView(TEMPLATE_DEFAULT);
          return modelAndView;
       } else {
          List<SettlementModel> list=paginationBean.getItemList();
          if (!(export.equals("PDF"))) {
             return new ModelAndView("EzySettleListExcel", "txnList", list);
          } else {
             return new ModelAndView("EzySettleListPdf", "txnList", list);
          }
       }
    }
    
    //IPN Configuration
 
	/*
	 * @RequestMapping(value = { "/Ipnconfig" }, method = RequestMethod.GET) public
	 * String Ipnconfig(final Model model, @RequestParam(required = false) final
	 * String VALUE,
	 * 
	 * @RequestParam(required = false, defaultValue = "1") final int currPage, final
	 * java.security.Principal principal) {
	 */
    
	@RequestMapping(value = { "/ipnconfig" }, method = RequestMethod.GET)
	public String Ipnconfig(final Model model, @RequestParam(required = false) final String VALUE,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		logger.info("IPN Configuration Method");

		PageBean pageBean = new PageBean("transactions list", "transaction/requestIPNInfo", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		List<Object[]> businessNamesAndUsernames = transactionService.getBusinessNamesAndUsernames();
	
		model.addAttribute("businessNamesAndUserNames", businessNamesAndUsernames);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/updateIPNField" }, method = RequestMethod.POST)
	public String updateIPNField(final Model model, HttpServletRequest request,
	        @RequestParam(value = "ip_address", required = false) String ipAddress,
	        @RequestParam(value = "payment_method1", required = false) String paymentMethod1,
	        @RequestParam(value = "ipn_url1", required = false) String ipnUrl1,
	        @RequestParam(value = "payment_method2", required = false) String paymentMethod2,
	        @RequestParam(value = "ipn_url2", required = false) String ipnUrl2,
	        @RequestParam(value = "payment_method3", required = false) String paymentMethod3,
	        @RequestParam(value = "ipn_url3", required = false) String ipnUrl3,
	        @RequestParam(value = "payment_method4", required = false) String paymentMethod4,
	        @RequestParam(value = "ipn_url4", required = false) String ipnUrl4,
	        @RequestParam(value = "payment_method5", required = false) String paymentMethod5,
	        @RequestParam(value = "ipn_url5", required = false) String ipnUrl5,
	        @RequestParam(value = "payment_method6", required = false) String paymentMethod6,
	        @RequestParam(value = "ipn_url6", required = false) String ipnUrl6,
	        @RequestParam(value = "payment_method7", required = false) String paymentMethod7,
	        @RequestParam(value = "ipn_url7", required = false) String ipnUrl7, String username,
	        final java.security.Principal principal) {

	    logger.info("Updating IPN Field Configuration");
	    logger.info("Merchant Username: " + username);
	    List<Object[]> businessNamesAndUsernames = transactionService.getBusinessNamesAndUsernames();
	    
	    Merchant currentMerchant = merchantService.loadMerchant(username);
	    String businessName = currentMerchant.getBusinessName();
	    Long merchantId = currentMerchant.getId();
	    String fpxMid = currentMerchant.getMid().getFpxMid();
	    String umezywayMid = currentMerchant.getMid().getUmEzywayMid();
	    String boostMid = currentMerchant.getMid().getBoostMid();
	    String grabMid = currentMerchant.getMid().getGrabMid();
	    String tngMid = currentMerchant.getMid().getTngMid();
	    String shoppyMid = currentMerchant.getMid().getShoppyMid();

	    logger.info("merchantId: " + merchantId);
	    
	    logger.info("Collect all IPN details in a map");
	    
	    Map<String, String> ipnDetails = new HashMap<>();
	    if (paymentMethod1 != null && !paymentMethod1.isEmpty() && ipnUrl1 != null && !ipnUrl1.isEmpty()) {
	        ipnDetails.put(paymentMethod1, ipnUrl1);
	    }
	    if (paymentMethod2 != null && !paymentMethod2.isEmpty() && ipnUrl2 != null && !ipnUrl2.isEmpty()) {
	        ipnDetails.put(paymentMethod2, ipnUrl2);
	    }
	    if (paymentMethod3 != null && !paymentMethod3.isEmpty() && ipnUrl3 != null && !ipnUrl3.isEmpty()) {
	        ipnDetails.put(paymentMethod3, ipnUrl3);
	    }
	    if (paymentMethod4 != null && !paymentMethod4.isEmpty() && ipnUrl4 != null && !ipnUrl4.isEmpty()) {
	        ipnDetails.put(paymentMethod4, ipnUrl4);
	    }
	    if (paymentMethod5 != null && !paymentMethod5.isEmpty() && ipnUrl5 != null && !ipnUrl5.isEmpty()) {
	        ipnDetails.put(paymentMethod5, ipnUrl5);
	    }
	    if (paymentMethod6 != null && !paymentMethod6.isEmpty() && ipnUrl6 != null && !ipnUrl6.isEmpty()) {
	        ipnDetails.put(paymentMethod6, ipnUrl6);
	    }

	    String finalFpxMid = null;
	    String finalBoostMid = null;
	    String finalGrabMid = null;
	    String finalTngMid = null;
	    String finalShoppyMid = null;
	    String finalumezywayMid = null;

	    // Determine the appropriate MIDs based on available MIDs and payment methods
	    for (String paymentMethod : ipnDetails.keySet()) {
	        if (paymentMethod.equalsIgnoreCase("fpx")) {
	            if ((fpxMid != null && !fpxMid.isEmpty()) || (umezywayMid != null && !umezywayMid.isEmpty())) {
	                finalFpxMid = fpxMid != null && !fpxMid.isEmpty() ? fpxMid : umezywayMid;
	                logger.info("finalFpxMid::: " + finalFpxMid);
	            } else {
	                logger.info("No MID present for FPX, skipping FPX payment method.");
	            }
	        } else if (paymentMethod.equalsIgnoreCase("boost")) {
	            if ((boostMid != null && !boostMid.isEmpty()) || (umezywayMid != null && !umezywayMid.isEmpty())) {
	                finalBoostMid = boostMid != null && !boostMid.isEmpty() ? boostMid : umezywayMid;
	                logger.info("finalBoostMid::: " + finalBoostMid);
	            } else {
	                logger.info("No MID present for Boost, skipping Boost payment method.");
	            }
	        } else if (paymentMethod.equalsIgnoreCase("grabpay")) {
	            if ((grabMid != null && !grabMid.isEmpty()) || (umezywayMid != null && !umezywayMid.isEmpty())) {
	                finalGrabMid = grabMid != null && !grabMid.isEmpty() ? grabMid : umezywayMid;
	                logger.info("finalGrabMid::: " + finalGrabMid);
	            } else {
	                logger.info("No MID present for GrabPay, skipping GrabPay payment method.");
	            }
	        } else if (paymentMethod.equalsIgnoreCase("tng")) {
	            if ((tngMid != null && !tngMid.isEmpty()) || (umezywayMid != null && !umezywayMid.isEmpty())) {
	                finalTngMid = tngMid != null && !tngMid.isEmpty() ? tngMid : umezywayMid;
	                logger.info("finalTngMid::: " + finalTngMid);
	            } else {
	                logger.info("No MID present for TNG, skipping TNG payment method.");
	            }
	        } else if (paymentMethod.equalsIgnoreCase("shopee pay")) {
	            if ((shoppyMid != null && !shoppyMid.isEmpty()) || (umezywayMid != null && !umezywayMid.isEmpty())) {
	                finalShoppyMid = shoppyMid != null && !shoppyMid.isEmpty() ? shoppyMid : umezywayMid;
	                logger.info("finalShoppyMid::: " + finalShoppyMid);
	            } else {
	                logger.info("No MID present for Shopee Pay, skipping Shopee Pay payment method.");
	            }
	        } else if (paymentMethod.equalsIgnoreCase("cards")) {
	            if (umezywayMid != null && !umezywayMid.isEmpty()) {
	                finalumezywayMid = umezywayMid;
	                logger.info("finalumezywayMid::: " + finalumezywayMid);
	            } else {
	                logger.info("No MID present for Cards, skipping Cards payment method.");
	            }
	        }
	    }

	    // Prepare a map for MIDs to send via email
	    Map<String, String> midDetails = new HashMap<>();
	    if (finalFpxMid != null) {
	        midDetails.put("fpx", finalFpxMid);
	    }
	    if (finalBoostMid != null) {
	        midDetails.put("boost", finalBoostMid);
	    }
	    if (finalGrabMid != null) {
	        midDetails.put("grabpay", finalGrabMid);
	    }
	    if (finalTngMid != null) {
	        midDetails.put("tng", finalTngMid);
	    }
	    if (finalShoppyMid != null) {
	        midDetails.put("shopee pay", finalShoppyMid);
	    }
	    if (finalumezywayMid != null) {
	        midDetails.put("Cards", finalumezywayMid);
	    }


	    if (!midDetails.isEmpty()) {
	        String emailResponse = new EmailUtils().sendIPNConfigurationEmail(currentMerchant, ipAddress, ipnDetails, midDetails);
	        logger.info("Email response: " + emailResponse);
	    } else {
	        logger.error("Merchant does not have a suitable MID to send the email");
	    }

	    PageBean pageBean = new PageBean("transactions list", "transaction/requestIPNInfo", Module.TRANSACTION, "transaction/sideMenuTransaction");
	    model.addAttribute("pageBean", pageBean);
	    model.addAttribute("businessNamesAndUserNames", businessNamesAndUsernames);
		model.addAttribute("isEmailSent", "true");

	    return TEMPLATE_DEFAULT;
	}
	
	@RequestMapping(value = "/exportAllPayoutTxnAsEmail", method = RequestMethod.GET)
	public String exportAllPayoutTxnAsEmail(@RequestParam("date") String date, @RequestParam("date1") String date1,
			@RequestParam(value = "currPage", defaultValue = "1") final int currPage, final Model model,
			@RequestParam("emailId") String emailId, @RequestParam(value = "export", required = false) String export) {

		logger.info(
				"Export Payout transaction as XLS for All......................................................................");

		try {
			logger.info("from date : " + date);
			logger.info("to date : " + date1);

			final String fromDate = convertDateFormatTo_YYY_MM_dd(date);
			final String toDate = convertDateFormatTo_YYY_MM_dd(date1);
			logger.info("After format from date : " + date);
			logger.info("After format to date : " + date1);

			if ("EXCEL".equalsIgnoreCase(export)) {
				ExecutorService executor = Executors.newSingleThreadExecutor();

				CompletableFuture.runAsync(() -> {
					logger.info("generateAllPaoutDetailExcelContentForEmail");
					logger.info("Payout is Enabled");
					List<FinanceReport> payoutReportData = transactionService.getAllPayoutTxnDetailsBetweenDates(fromDate,
							toDate);
					byte[] payoutExcelContent = transactionService
							.generateAllPayoutReportXLSContentForEmail(payoutReportData, fromDate, toDate);

					Map<String, byte[]> attachments = new HashMap<>();
					attachments.put("PayoutTransactionReport.xls", payoutExcelContent);

					new FinanceReportUtils().sendEmailWithAttachments(attachments, fromDate, toDate, emailId);
				}, executor).exceptionally(ex -> {
					logger.error("Exception in sending E-mail: " + ex.getMessage(), ex);
					return null;
				}).thenRun(executor::shutdown);
			} else {
				logger.warn("Export type must be EXCEL");
			}
		} catch (Exception e) {
			logger.error("Exception in export XLS for payout user: ", e);
		}

		PageBean pageBean = new PageBean("payout transactions list", "transaction/financeReport", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<>();
		paginationBean.setCurrPage(currPage);
		List<Object[]> businessNamesAndUsernames = transactionService.getBusinessNamesAndUsernames();

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("businessNamesAndUsernames", businessNamesAndUsernames);
		model.addAttribute("isEmailSent", "true");

		return TEMPLATE_DEFAULT;
	}

	// For payout 'Transaction Approval'
	@RequestMapping(value = { "/payout/list/exceeded-limit-approvals" }, method = RequestMethod.GET)
	public String listPayoutTransactionsApproval(Model model,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			final java.security.Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/payoutTransactionApproval",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		currentPage = Math.max(currentPage, 1); // Ensure currentPage is at least 1

		// Retrieve payout transaction details for amount 25kRM > or specified max payout amount for the corresponding merchant.
		transactionService.fetchMaxPayoutExceededTransactions(currentPage, model, pageBean);

		return TEMPLATE_DEFAULT;
	}
	
	@RequestMapping(value = "/payout/transactions/search/exceeded-limit", method = RequestMethod.GET)
	public String searchOnHoldPayout(HttpServletRequest request, Model model,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			@RequestParam final String searchType,
			@RequestParam final String searchValue,
			final java.security.Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/payoutTransactionApproval",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		
		transactionService.searchExceededLimitPayoutTransactions(model, pageBean, searchType,searchValue, currentPage);
		
		return TEMPLATE_DEFAULT;
	}
	
	@RequestMapping(value = { "/payout/exceeded-limit/transactionApproval" }, method = RequestMethod.GET)
	public String payoutTransactionsApproval(HttpServletRequest request,
			Model model,
			@RequestParam final String invoiceID,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			final java.security.Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/payoutTransactionApproval",
				Module.TRANSACTION, "transaction/sideMenuTransaction");

		final String decodedInvoiceID = new String(Base64.getDecoder().decode(invoiceID), StandardCharsets.UTF_8);
		
		logger.info("Payout exceeded-limit transaction approval triggered, trigger by: " + principal.getName() + " InvoideId : " + decodedInvoiceID);
		
		// Obtain PAYOUT_HOLD_TXN by INVOICE_ID_PROOF
		PayoutHoldTxn payoutHoldTxnDetails = accountEnquiryDao.getPayoutHoldTxnByInvoiceIDProof(decodedInvoiceID);

				
		// Validate if the Transaction has been initiated/processed already
		if (payoutHoldTxnDetails == null ||
				    (payoutHoldTxnDetails.getStatus() == null || !payoutHoldTxnDetails.getStatus().trim().equalsIgnoreCase("ExceededMaxLimit"))) {

		    logger.warn("Payout approval 'FAILED' because no transaction details were found for Invoice ID: {"+decodedInvoiceID+"}");
			model.addAttribute("rareScenario","Yes");//for handling in the UI jsp
			model.addAttribute("rareScenarioAction",payoutHoldTxnDetails.getStatus());

		}  else {
			// Update the status in PAYOUT_HOLD_TXN to 'Approved'
			int isUpdated = accountEnquiryDao.updatePayoutHoldTxnStatusByInvoiceID(decodedInvoiceID, "Approved", principal.getName());

			// Return JSP.
			model.addAttribute("action", "approve");

			if (isUpdated > 0) {
				// Trigger externalApi api to process payout.
				CompletableFuture.runAsync(() -> {
					transactionService.handlePayoutTransactionApproval(decodedInvoiceID, payoutHoldTxnDetails,principal.getName());
				}).exceptionally(ex -> {
					logger.error("Processing payout exceeded limit - approval failed. InvoiceId: " + decodedInvoiceID + " . Exception : " + ex.getMessage(), ex);
					return null;
				});
			} else {
				logger.warn("Failed to update PAYOUT_HOLD_TXN status, so triggering external-api was prevented. InvoideId : " + decodedInvoiceID);
			}
		}
		transactionService.fetchMaxPayoutExceededTransactions(Math.max(currentPage, 1), model, pageBean);
		return TEMPLATE_DEFAULT;
	}
	
	@RequestMapping(value = { "/payout/exceeded-limit/transactionReject" }, method = RequestMethod.GET)
	public String payoutTransactionsReject(HttpServletRequest request, final Model model,
			@RequestParam final String invoiceID, @RequestParam final String reason,
			@RequestParam(required = false, defaultValue = "1") int currentPage,
			final java.security.Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "transaction/payoutTransactionApproval",
				Module.TRANSACTION, "transaction/sideMenuTransaction");


		final String decodedInvoiceID = new String(Base64.getDecoder().decode(invoiceID), StandardCharsets.UTF_8);
		
		logger.info("Payout exceeded-limit transaction reject triggered, trigger by: " + principal.getName() + " InvoideId : " + decodedInvoiceID);

		// Obtain PAYOUT_HOLD_TXN by INVOICE_ID_PROOF
		PayoutHoldTxn payoutHoldTxnDetails = accountEnquiryDao.getPayoutHoldTxnByInvoiceIDProof(decodedInvoiceID);

		// Validate the Transaction has been initiated/processed already.?
		if (payoutHoldTxnDetails == null || 
			    (payoutHoldTxnDetails.getStatus() == null || !payoutHoldTxnDetails.getStatus().trim().equalsIgnoreCase("ExceededMaxLimit"))) {
			// If already initiated, Send Warn email through email r .............!
			logger.warn("Payout exceeded-limit transaction rejection 'FAILED' because the status is not 'ExceededMaxLimit'. InvoiceID: " + decodedInvoiceID);
			model.addAttribute("rareScenario","Yes");
			model.addAttribute("rareScenarioAction",payoutHoldTxnDetails.getStatus());
		} else {
			// Change the rejected status in PAYOUT_HOLD_TXN & PAYOUT_DETAIL
			int updatePayoutHoldTxnStatus = accountEnquiryDao.updatePayoutHoldTxnStatusByInvoiceID(decodedInvoiceID, "Rejected", principal.getName());
//			int updatePayoutTxnDetailsStatus = accountEnquiryDao.updatePayoutTxnDetailsStatusAndDeclinedReasonByInvoiceId(decodedInvoiceID, "pd", reason);

//			if (payoutHoldTxnDetails.getCreatedDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
//					.isBefore(LocalDateTime.now().minusDays(2))) {
//				// Handle the reject if the transaction is rejected after 2 days
//				logger.warn("Payout Transaction is getting rejected after 2 days. InvoiceId: " + decodedInvoiceID);
//			}
			
			// Return JSP.
			model.addAttribute("action", "reject");

			// Trigger the rejection email. If values are updated
			if (updatePayoutHoldTxnStatus > 0) {
			    CompletableFuture.runAsync(() -> {
			        transactionService.handlePayoutTransactionReject(decodedInvoiceID, payoutHoldTxnDetails,principal.getName(),reason);
				}).exceptionally(ex -> {
					logger.error("Processing payout exceeded limit - rejection failed. InvoiceId: " + decodedInvoiceID + " .Exception : " + ex.getMessage(), ex);
					return null;
				});
			} else {
				logger.warn(
						"Failed to update PAYOUT_HOLD_TXN status or PAYOUT_TXN_DETAILS status, so triggering email was prevented. InvoiceId: " + decodedInvoiceID);
			}
		}
		transactionService.fetchMaxPayoutExceededTransactions(Math.max(currentPage, 1), model, pageBean);
		return TEMPLATE_DEFAULT;
	}
	@RequestMapping(value = {"/sendFpxIpn"}, method = RequestMethod.GET)
	public @ResponseBody AjaxResponseBody sendFpxIpn(@RequestParam String fpxTxnId) {
		try {
			return currencyExchangeService.sendFpxIpnViaAdmin(fpxTxnId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new AjaxResponseBody();
	}

	@RequestMapping(value = {"/getHitCount"}, method = RequestMethod.GET)
	public @ResponseBody AjaxResponseBody getHitCount(@RequestParam String fpxTxnId) {

		return currencyExchangeService.getHitCount(fpxTxnId);
	}

}