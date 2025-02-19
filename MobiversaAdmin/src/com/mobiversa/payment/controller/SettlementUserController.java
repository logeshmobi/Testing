package com.mobiversa.payment.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mobiversa.common.bo.BoostDailyRecon;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.service.AdminService;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.DashBoardService;
import com.mobiversa.payment.service.MDRDetailsService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.PromotionService;
import com.mobiversa.payment.service.SubAgentService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.ResponseDetails;
import com.mobiversa.payment.util.SettlementModel;
import com.mobiversa.payment.util.UMFileGenerateDetails;
import com.mobiversa.payment.validator.AddAgentUserValidator;

//import org.springframework.validation.Validator;
@Controller
@RequestMapping(value = SettlementUserController.URL_BASE)
public class SettlementUserController extends BaseController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private DashBoardService dashBoardService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private MobileUserService mobileUserService;

	@Autowired
	private MobileUserService mobileUser;

	@Autowired
	private AgentService agentService;

	@Autowired
	private MDRDetailsService mdrDetailsService;

	@Autowired
	private SubAgentService subAgentService;
	@Autowired
	private PromotionService promotionService;
	@Autowired
	private AddAgentUserValidator validator;

	@Autowired
	private TransactionService transactionService;

	/* private static final String merchantModel = "merchant"; */

	public static final String URL_BASE = "/settlementDataUser";
	private static final Logger logger = Logger.getLogger(SettlementUserController.class);

	/**
	 * Default wildcard page to redirect invalid page mapping to the default page.
	 * <p>
	 * for example <code>/merchant/testing</code> will be caught in the
	 * requestMapping and therefore user is redirected to display all merchant,
	 * instead of showing a 404 page.
	 * 
	 * @return
	 */
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("Default Page Merchant");
		return "redirect:" + URL_BASE + "/list/1";
	}

	// New Settlement Summary changes added on 21-10-21

	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String settlement(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {

		logger.info("SettlementMDR");
		PageBean pageBean = new PageBean("transactions list", "SettlementUser/SettlementMDRList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("SettlementMDR Summary:" + principal.getName());
		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.listSettlementMDRTransaction(paginationBean, null, null, null);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_SETTLEMENTUSER;

	}

	@RequestMapping(value = { "/searchSettlementMDR" }, method = RequestMethod.GET)
	public String displayEzyLinkTransactionList(HttpServletRequest request, final Model model,
			@RequestParam final String date, @RequestParam final String date1, @RequestParam final String txntype,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		PageBean pageBean = new PageBean("transactions list", "SettlementUser/SettlementMDRList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.listSettlementMDRTransaction(paginationBean, date, date1, txntype);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_SETTLEMENTUSER;

	}

	@RequestMapping(value = "/exportSettlementMDR", method = RequestMethod.GET)
	public ModelAndView getExcelEzyLink(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam final String txntype, @RequestParam(required = false, defaultValue = "1") final int currPage,
			final Model model, @RequestParam(required = false) String export) {

		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.listSettlementMDRTransaction(paginationBean, date, date1, txntype);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<SettlementModel> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("SettlementSummaryExcel", "txnList", list);

		} else {
			return new ModelAndView("SettlementSummaryPdf", "txnList", list);
		}
	}

	@RequestMapping(value = { "/changeStatus/{rrn}" }, method = RequestMethod.GET)
	public String changeSettlementStatus(HttpServletRequest request, final Model model, @PathVariable final String rrn,
			Principal principal, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		PageBean pageBean = new PageBean("transactions list", "SettlementUser/changeSetStatus", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		logger.info("rrn::" + rrn);

		String Status1 = null;

		SettlementMDR setData = new SettlementMDR();

		setData = transactionService.loadSettlementMDR(rrn);

		if (setData.getStatus().equals("H")) {
			Status1 = "HOLD";
		} else if (setData.getStatus().equals("S")) {
			Status1 = "SETTLE";
		}

		model.addAttribute("setData", setData);
		model.addAttribute("Status1", Status1);
		model.addAttribute("rrn", rrn);

		return TEMPLATE_SETTLEMENTUSER;

	}

	@RequestMapping(value = { "/updateStatus/{rrn}" }, method = RequestMethod.POST)
	public String changeSetStatus(@ModelAttribute("setData") final SettlementMDR setData, final Model model,
			@PathVariable final String rrn, final java.security.Principal principal, final HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		PageBean pageBean = new PageBean("transactions list", "SettlementUser/success", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		logger.info("setData::" + setData.getStatus());

		setData.setRrn(rrn);

		merchantService.updateStatusDetails(setData);

		return TEMPLATE_SETTLEMENTUSER;

	}

	// Change Status By BoostTxnid - Boost_Dly_Recon
	@RequestMapping(value = { "/changeBoostStatus/{rrn}" }, method = RequestMethod.GET)
	public String changeBoostStatus(HttpServletRequest request, final Model model, @PathVariable final String rrn,
			Principal principal, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		PageBean pageBean = new PageBean("transactions list", "SettlementUser/BoostchangeSetStatus", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		logger.info("rrn::" + rrn);

		String Status1 = null;

		BoostDailyRecon setBoostdata = new BoostDailyRecon();

		setBoostdata = transactionService.loadBoostdlyrecon(rrn);

		if (setBoostdata.getPayment().equals("H")) {
			Status1 = "HOLD";
		} else if (setBoostdata.getPayment().equals("S")) {
			Status1 = "SETTLE";
		}

		model.addAttribute("setBoostdata", setBoostdata);
		model.addAttribute("Status1", Status1);
		model.addAttribute("rrn", rrn);

		return TEMPLATE_SETTLEMENTUSER;

	}

	// Update Status By BoostTxnid - Boost_Dly_Recon
	@RequestMapping(value = { "/updateBoostStatus/{rrn}" }, method = RequestMethod.POST)
	public String updateBoostStatus(@ModelAttribute("setBoostdata") final BoostDailyRecon setBoostdata,
			final Model model, @PathVariable final String rrn, final java.security.Principal principal,
			final HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		PageBean pageBean = new PageBean("transactions list", "SettlementUser/success", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		logger.info("setBoostdata::" + setBoostdata.getPayment());

		setBoostdata.setBoostTxnID(rrn);

		merchantService.updateBoostStatusDetails(setBoostdata);

		return TEMPLATE_SETTLEMENTUSER;

	}

	// Change Status By FpxTxnid and Sellerorderno - FPX Transaction
	@RequestMapping(value = { "/changeFpxStatus/{rrn}/{invoiceId}" }, method = RequestMethod.GET)
	public String changeFpxStatus(HttpServletRequest request, final Model model, @PathVariable final String rrn,
			@PathVariable final String invoiceId, Principal principal, HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		PageBean pageBean = new PageBean("transactions list", "SettlementUser/FpxchangeSetStatus", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		logger.info("rrn::" + rrn);
		logger.info("Invoice id::" + invoiceId);

		String Status1 = null;

		FpxTransaction setFpxdata = new FpxTransaction();

		setFpxdata = transactionService.loadFpxTransaction(rrn, invoiceId);

		if (setFpxdata.getStatus().equals("H")) {
			Status1 = "HOLD";
		} else if (setFpxdata.getStatus().equals("S")) {
			Status1 = "SETTLE";
		}

		model.addAttribute("setFpxdata", setFpxdata);
		model.addAttribute("Status1", Status1);
		model.addAttribute("rrn", rrn);
		model.addAttribute("invoiceId", invoiceId);

		return TEMPLATE_SETTLEMENTUSER;

	}

	// Update Status By FpxTxnid and Sellerorderno - FPX Transaction
	@RequestMapping(value = { "/updateFpxStatus/{rrn}/{invoiceId}" }, method = RequestMethod.POST)
	public String updateFpxStatus(@ModelAttribute("setFpxdata") final FpxTransaction setFpxdata, final Model model,
			@PathVariable final String rrn, @PathVariable final String invoiceId,
			final java.security.Principal principal, final HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		PageBean pageBean = new PageBean("transactions list", "SettlementUser/success", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		logger.info("setFpxdata::" + setFpxdata.getStatus());

		setFpxdata.setFpxTxnId(rrn);
		setFpxdata.setSellerOrderNo(invoiceId);

		merchantService.updateFpxStatusDetails(setFpxdata);

		return TEMPLATE_SETTLEMENTUSER;

	}

	@RequestMapping(value = { "/fileGenerate/{currPage}" }, method = RequestMethod.GET)
	public String fileGenerate(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {

		logger.info("SettlementMDR");
		PageBean pageBean = new PageBean("transactions list", "SettlementUser/fileGenerationList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("fileGenerate:" + principal.getName());

		return TEMPLATE_SETTLEMENTUSER;

	}

	@RequestMapping(value = { "/generateFileCall" }, method = RequestMethod.GET)
	public String regMDRDetails(HttpServletRequest request, final Model model, @RequestParam final String umFile,
			@RequestParam final String mercFile, @RequestParam final String mdrFile, @RequestParam final String dedFile,
			@RequestParam final String csvFile, @RequestParam final String stPeriod, @RequestParam final String date,

			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("generateFileCall");

		logger.info("umFile::" + umFile);
		logger.info("mercFile::" + mercFile);
		logger.info("mdrFile::" + mdrFile);
		logger.info("dedFile::" + dedFile);
		logger.info("csvFile::" + csvFile);
		logger.info("stPeriod::" + stPeriod);
		logger.info("date::" + date);

		PageBean pageBean = new PageBean("transactions list", "SettlementUser/FileSuccess", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		UMFileGenerateDetails umdet = new UMFileGenerateDetails();
		umdet.setCsvFile(csvFile);
		umdet.setDedFile(dedFile);
		umdet.setFrom(date);
		umdet.setMdrFile(mdrFile);
		umdet.setMercFile(mercFile);
		umdet.setUmFile(umFile);
		umdet.setStPeriod(stPeriod);

		String response = null;
		logger.info("UM File Generation Request");
		String serviceName = "AMBANK_SETTLEMENT";
		ResponseDetails data = mdrDetailsService.sendSettlementMDRDetails(umdet, serviceName);
		if (data != null) {
			if (data.getResponseCode().equals("0000")) {
				logger.info("UM File Generation Request Sent and recieved response");
				response = "UM file Generation Done";
			} else {
				logger.info("Failed to send MDR Request details");
				response = "UM file Generation Results in Failure";
			}
		}

		model.addAttribute("response", response);
		return TEMPLATE_SETTLEMENTUSER;

	}

// Dhinesh Boost , Grabpay and Fpx File Generate Start - 18-03-2022	

	// Boost File Generation - Start

	@RequestMapping(value = { "/BoostfileGenerate/{currPage}" }, method = RequestMethod.GET)
	public String BoostfileGenerate(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {

		logger.info("BoostDailyRecon");
		PageBean pageBean = new PageBean("transactions list", "SettlementUser/BoostfileGenerationList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("fileGenerate:" + principal.getName());

		return TEMPLATE_SETTLEMENTUSER;

	}

	@RequestMapping(value = { "/BoostgenerateFileCall" }, method = RequestMethod.GET)
	public String BoostgenerateFileCall(HttpServletRequest request, final Model model,
			@RequestParam final String csvFile, @RequestParam final String merFile, @RequestParam final String date,

			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("Boost-generateFileCall");

		logger.info("csvFile::" + csvFile);
		logger.info("merFile::" + merFile);
		logger.info("date::" + date);

		PageBean pageBean = new PageBean("transactions list", "SettlementUser/BoostFileSuccess", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		String Date = null;
		try {
			Date = new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		logger.info("Format Date::" + Date);

		UMFileGenerateDetails umdet = new UMFileGenerateDetails();
		umdet.setCsvFile(csvFile);
		umdet.setMercFile(merFile);
		umdet.setFrom(Date);

		String response = null;
		logger.info("BOOST File Generation Request");
		String serviceName = "BOOST_AMBANK_SETTLEMENT";

		ResponseDetails data = mdrDetailsService.sendBoostRequestDetails(umdet, serviceName);
		if (data != null) {
			if (data.getResponseCode().equals("0000")) {
				logger.info("BOOST File Generation Request Sent and received response");
				response = "BOOST File Generation Done";
			} else {
				logger.info("Failed to send BOOST Request details");
				response = "BOOST File Generation Results in Failure";
			}
		}

		model.addAttribute("response", response);
		return TEMPLATE_SETTLEMENTUSER;

	}

	// Boost File Generation - End

	// Fpx File Generation - Start

	@RequestMapping(value = { "/FpxfileGenerate/{currPage}" }, method = RequestMethod.GET)
	public String FpxfileGenerate(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {

		logger.info("FpxTransaction");
		PageBean pageBean = new PageBean("transactions list", "SettlementUser/FpxfileGenerationList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("fileGenerate:" + principal.getName());

		return TEMPLATE_SETTLEMENTUSER;

	}

	@RequestMapping(value = { "/FpxgenerateFileCall" }, method = RequestMethod.GET)
	public String FpxgenerateFileCall(HttpServletRequest request, final Model model, @RequestParam final String csvFile,
			@RequestParam final String merFile, @RequestParam final String date,

			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("Fpx-generateFileCall");

		logger.info("csvFile::" + csvFile);
		logger.info("merFile::" + merFile);
		logger.info("date::" + date);

		PageBean pageBean = new PageBean("transactions list", "SettlementUser/FpxFileSuccess", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		String Date = null;

		try {
			Date = new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(date));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("Format Date::" + Date);

		UMFileGenerateDetails umdet = new UMFileGenerateDetails();
		umdet.setCsvFile(csvFile);
		umdet.setMercFile(merFile);
		umdet.setFrom(Date);

		String response = null;
		logger.info("FPX File Generation Request");
		String serviceName = "FPX_AMBANK_SETTLEMENT";
		ResponseDetails data = mdrDetailsService.sendFpxRequestDetails(umdet, serviceName);
		if (data != null) {
			if (data.getResponseCode().equals("0000")) {
				logger.info("FPX File Generation Request Sent and received response");
				response = "FPX File Generation Done";
			} else {
				logger.info("Failed to send FPX Request details");
				response = "FPX File Generation Results in Failure";
			}
		}

		model.addAttribute("response", response);
		return TEMPLATE_SETTLEMENTUSER;

	}

	// Fpx File Generation - End

	// Grabpay File Generation - Start

	@RequestMapping(value = { "/GrabpayfileGenerate/{currPage}" }, method = RequestMethod.GET)
	public String GrabpayfileGenerate(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {

		logger.info("GrabpayFile");
		PageBean pageBean = new PageBean("transactions list", "SettlementUser/GrabpayfileGenerationList",
				Module.TRANSACTION, "transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("fileGenerate:" + principal.getName());

		return TEMPLATE_SETTLEMENTUSER;

	}

	@RequestMapping(value = { "/GrabpaygenerateFileCall" }, method = RequestMethod.GET)
	public String GrabpaygenerateFileCall(HttpServletRequest request, final Model model,
			@RequestParam final String csvFile, @RequestParam final String merFile, @RequestParam final String date,

			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("Grabpay-generateFileCall");

		logger.info("csvFile::" + csvFile);
		logger.info("merFile::" + merFile);
		logger.info("date::" + date);

		PageBean pageBean = new PageBean("transactions list", "SettlementUser/GrabpayFileSuccess", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		UMFileGenerateDetails umdet = new UMFileGenerateDetails();
		umdet.setCsvFile(csvFile);
		umdet.setMercFile(merFile);
		umdet.setFrom(date);

		String response = null;
		logger.info("GRABPAY File Generation Request");
		String serviceName = "GRAB_AMBANK_SETTLEMENT";
		ResponseDetails data = mdrDetailsService.sendGrabpayRequestDetails(umdet, serviceName);
		if (data != null) {
			if (data.getResponseCode().equals("0000")) {
				logger.info("GRABPAY File Generation Request Sent and received response");
				response = "GRABPAY File Generation Done";
			} else {
				logger.info("Failed to send GRABPAY Request details");
				response = "GRABPAY File Generation Results in Failure";
			}
		}

		model.addAttribute("response", response);
		return TEMPLATE_SETTLEMENTUSER;

	}

	// Grabpay File Generation - End

// Dhinesh Boost , Grabpay and Fpx File Generate End - 18-03-2022	

}
