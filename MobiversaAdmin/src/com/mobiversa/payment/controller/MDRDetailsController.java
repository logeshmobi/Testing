
package com.mobiversa.payment.controller;

import static com.mobiversa.payment.service.AgniService.refreshCache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
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
import org.springframework.web.servlet.ModelAndView;

import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.BizAppSettlement;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.MobiProductMDR;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.common.bo.UMEcomTxnResponse;
import com.mobiversa.payment.connect.MotoPaymentCommunication;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.BaseDataImpl;
import com.mobiversa.payment.dto.RegMobileUser;
import com.mobiversa.payment.exception.MobiException;
import com.mobiversa.payment.exception.Status;
import com.mobiversa.payment.service.AdminService;
import com.mobiversa.payment.service.MDRDetailsService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.FileGenerate;
import com.mobiversa.payment.util.ResponseDetails;
import com.mobiversa.payment.util.UMEzyway;

@Controller
@RequestMapping(value = MDRDetailsController.URL_BASE)
public class MDRDetailsController extends BaseController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private MobileUserService mobileUserService;
	@Autowired
	private MDRDetailsService mdrDetailsService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;

	private static final Logger logger = Logger.getLogger(MDRDetailsController.class);
	public static final String URL_BASE = "/MDR";

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("default url");
		return "redirect:" + URL_BASE + "/addMDRDetails";
	}

	@RequestMapping(value = { "/addMDRDetails" }, method = RequestMethod.GET)
	public String displayAddMDR(Model model, @ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
			final HttpServletRequest request,
//			@RequestParam("id") Long id,
			final java.security.Principal principal) {
		logger.info("displayAddMDR");
		List<Merchant> merchant1 = merchantService.loadMerchant();
		Set<String> merchantNameList = new HashSet<String>();
		for (Merchant t : merchant1) {
			String businessName = t.getBusinessName();
			String email = t.getUsername();
			merchantNameList.add(businessName.toString() + "~" + email);
		}
		logger.info("merchantNameList" + merchantNameList);
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/addMDRDetails", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantNameList", merchantNameList);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("mobileUser", regMobileUser);
		logger.info("displayAddMDR completed");

		String err = (String) request.getSession(true).getAttribute("addMdrErSession");

		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {

				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("addMdrErSession");
			}
		}

		return TEMPLATE_DEFAULT;
	}

	@SuppressWarnings("nls")
	@RequestMapping(value = { "/findMIDDetails" }, method = RequestMethod.GET)
	public String findMIDDetails(final Model model,
			/* @PathVariable("id") Long id, */
			@RequestParam("id") Long id, final java.security.Principal principal) {

		logger.info("01 Find merchant to Add MDR:" + id);
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/addMDRDetails", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		List<Merchant> merchant1 = merchantService.loadMerchant();
		RegMobileUser regMobileUser = new RegMobileUser();
		Merchant midDetails = mobileUserService.loadIserMidDetails(id);
		Set<String> listMid = new HashSet<String>();
		/*
		 * if (midDetails.getMid().getMid() != null) { for (Merchant t : merchant1) { if
		 * (t.getMid()!= null) { listMid.add(t.getMid().getMid()); } } } if
		 * (midDetails.getMid().getUmMid() != null) { for (Merchant t : merchant1) { if
		 * (t.getMid()!= null) { listMid.add(t.getMid().getUmMid()); } } } if
		 * (midDetails.getMid().getUmMotoMid() != null) { for (Merchant t : merchant1) {
		 * if (t.getMid()!= null) { listMid.add(t.getMid().getUmMotoMid()); } } } if
		 * (midDetails.getMid().getUmEzywayMid() != null) { for (Merchant t : merchant1)
		 * { if (t.getMid()!= null) { listMid.add(t.getMid().getUmEzywayMid()); } } } if
		 * (midDetails.getMid().getUmEzyrecMid() != null) { for (Merchant t : merchant1)
		 * { if (t.getMid()!= null) { listMid.add(t.getMid().getUmEzyrecMid()); } } } if
		 * (midDetails.getMid().getUmEzypassMid() != null) { for (Merchant t :
		 * merchant1) { if (t.getMid()!= null) {
		 * listMid.add(t.getMid().getUmEzypassMid()); } } }
		 */

		if (midDetails.getMid().getMid() != null) {

			listMid.add(midDetails.getMid().getMid());

		}
		if (midDetails.getMid().getUmMid() != null) {

			listMid.add(midDetails.getMid().getUmMid());

		}
		if (midDetails.getMid().getUmMotoMid() != null) {

			listMid.add(midDetails.getMid().getUmMotoMid());

		}

		if (midDetails.getMid().getUmEzywayMid() != null) {

			listMid.add(midDetails.getMid().getUmEzywayMid());

		}
		if (midDetails.getMid().getUmEzyrecMid() != null) {

			listMid.add(midDetails.getMid().getUmEzyrecMid());

		}
		if (midDetails.getMid().getUmEzypassMid() != null) {

			listMid.add(midDetails.getMid().getUmEzypassMid());
		}

		logger.info("MidDetails" + listMid);
		regMobileUser.setBusinessName(midDetails.getBusinessName());
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("midList", listMid);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		logger.info("pageBean" + pageBean);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("mobileUser", regMobileUser);
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/regMDRDetails" }, method = RequestMethod.POST)
	public String regMDRDetails(Model model, final HttpServletRequest request,
			@ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
			@RequestParam("domcreditmerchantMDR") String domCreditMerchantMDR,
			@RequestParam("domcredithostMDR") String domCreditHostMDR,
			@RequestParam("domdebitmerchantMDR") String domDebitMerchantMDR,
			@RequestParam("domdebithostMDR") String domDebitHostMDR,
			@RequestParam("focreditmerchantMDR") String foCreditMerchantMDR,
			@RequestParam("focredithostMDR") String foCreditHostMDR,
			@RequestParam("fodebitmerchantMDR") String foDebitMerchantMDR,
			@RequestParam("fodebithostMDR") String foDebitHosttMDR, @RequestParam("mid") String mid,
			
			@RequestParam("domcreditmerchantMDR1") String domCreditMerchantMDR1,
            @RequestParam("domcredithostMDR1") String domCreditHostMDR1,
            @RequestParam("domdebitmerchantMDR1") String domDebitMerchantMDR1,
            @RequestParam("domdebithostMDR1") String domDebitHostMDR1,
            @RequestParam("focreditmerchantMDR1") String foCreditMerchantMDR1,
            @RequestParam("focredithostMDR1") String foCreditHostMDR1,
            @RequestParam("fodebitmerchantMDR1") String foDebitMerchantMDR1,
            @RequestParam("fodebithostMDR1") String foDebitHosttMDR1,
            
            @RequestParam("boostEcommerchantMDR") String boostEcomMerchantMDR,
            @RequestParam("boostEcomhostMDR") String boostEcomHostMDR,
            @RequestParam("boostQrmerchantMDR") String boostQrMerchantMDR,
            @RequestParam("boostQrHostMDR") String boostQrHostMDR,
            @RequestParam("grabEcommerchantMDR") String grabEcomMerchantMDR,
            @RequestParam("grabEcomhostMDR") String grabEcomHostMDR,
            @RequestParam("grabQrmerchantMDR") String grabQrMerchantMDR,
            @RequestParam("grabQrHostMDR") String grabQrHostMDR,
            @RequestParam("fpxmerchantMDR") String fpxMerchantMDR,
            @RequestParam("fpxhostMDR") String fpxHostMDR,
            @RequestParam("tngEcommerchantMDR") String tngEcomMerchantMDR,
            @RequestParam("tngEcomhostMDR") String tngEcomHostMDR,
            @RequestParam("tngQrmerchantMDR") String tngQrMerchantMDR,
            @RequestParam("tngQrHostMDR") String tngQrHostMDR,
			@RequestParam("rateId") String rateId,
			@RequestParam("brand") String brand, @RequestParam("settlePeriod") String settlePeriod,
			@RequestParam("payLater") String payLater, @RequestParam("amount") String amount,
			@RequestParam("installment") String installment,

			final java.security.Principal principal) {

		logger.info("02 Registered values to confrim");

		logger.info("settlePeriod" + settlePeriod);
		logger.info("payLater" + payLater);
		logger.info("amount" + amount);
		logger.info("installment" + installment);

		PageBean pageBean = null;
		try {
			if (regMobileUser.getMid() == null) {

				// logger.info("Empty TID Fields..");
				model.addAttribute("responseEmptyMID", "Empty MID Fields..");
				throw new MobiException(Status.EMPTY_MID);

			}
			regMobileUser.setDomCreditMerchantMDR(domCreditMerchantMDR);
			regMobileUser.setDomDebitHostMDR(domDebitHostMDR);
			regMobileUser.setDomDebitMerchantMDR(domDebitMerchantMDR);
			regMobileUser.setDomCreditHostMDR(domCreditHostMDR);
			regMobileUser.setFoCreditMerchantMDR(foCreditMerchantMDR);
			regMobileUser.setFoCreditHostMDR(foCreditHostMDR);
			regMobileUser.setFoDebitMerchantMDR(foDebitMerchantMDR);
			regMobileUser.setFoDebitHostMDR(foDebitHosttMDR);
			regMobileUser.setDomCreditMerchantMDR1(domCreditMerchantMDR1);
            regMobileUser.setDomDebitHostMDR1(domDebitHostMDR1);
            regMobileUser.setDomDebitMerchantMDR1(domDebitMerchantMDR1);
            regMobileUser.setDomCreditHostMDR1(domCreditHostMDR1);
            regMobileUser.setFoCreditMerchantMDR1(foCreditMerchantMDR1);
            regMobileUser.setFoCreditHostMDR1(foCreditHostMDR1);
            regMobileUser.setFoDebitMerchantMDR1(foDebitMerchantMDR1);
            regMobileUser.setFoDebitHostMDR1(foDebitHosttMDR1);
            
            regMobileUser.setBoostEcomMerchantMDR(boostEcomMerchantMDR);
            regMobileUser.setBoostEcomHostMDR(boostEcomHostMDR);
            regMobileUser.setBoostQrMerchantMDR(boostQrMerchantMDR);
            regMobileUser.setBoostQrHostMDR(boostQrHostMDR);
            regMobileUser.setGrabEcomMerchantMDR(grabEcomMerchantMDR);
            regMobileUser.setGrabEcomHostMDR(grabEcomHostMDR);
            regMobileUser.setGrabQrMerchantMDR(grabQrMerchantMDR);
            regMobileUser.setGrabQrHostMDR(grabQrHostMDR);
            regMobileUser.setFpxMerchantMDR(fpxMerchantMDR);
            regMobileUser.setFpxHostMDR(fpxHostMDR);
            regMobileUser.setTngEcomMerchantMDR(tngEcomMerchantMDR);
            regMobileUser.setTngEcomHostMDR(tngEcomHostMDR);
            regMobileUser.setTngQrMerchantMDR(tngQrMerchantMDR);
            regMobileUser.setTngQrHostMDR(tngQrHostMDR);
			
			
			regMobileUser.setMid(mid);
			regMobileUser.setBrand(brand);
			regMobileUser.setSettlePeriod(settlePeriod);
			regMobileUser.setPayLater(payLater);
			regMobileUser.setAmount(amount);
			regMobileUser.setInstallment(installment);
			regMobileUser.setRateId(rateId);

			logger.info("mid: " + regMobileUser.getMid());
			pageBean = new PageBean("Reg MDR Detail", "merchant/addMDR/MDRDetailsReview", Module.MOBILE_USER,
					"mobileuser/sideMenuMobileUser");
		} catch (MobiException e) {

			logger.info("Exception: " + e.getMessage());
			pageBean = new PageBean("AddMobileUser", "merchant/MDR/addMDRDetails", Module.MOBILE_USER,
					"mobileuser/sideMenuMobileUser");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("mobileUser", regMobileUser);
			return TEMPLATE_DEFAULT;
		}

		BaseDataImpl baseData = new BaseDataImpl();
		RegMobileUser a = baseData.vaildated(regMobileUser);
		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("addMdrErSession", "yes");
			return "redirect:/MDR/addMDRDetails";
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", regMobileUser);
		// logger.info("mobileUser: " + regMobileUser);
		request.getSession(true).setAttribute("addMDRDetailsSession", regMobileUser);

		// return "redirect:/MDR/addMDR/addMDRreview";
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/addMDRreview" }, method = RequestMethod.GET)
	public String addMDRReviewandConfirm(final Model model, final HttpServletRequest request,
			final RegMobileUser regMobileUser) {

		logger.info("03 About to review & confrim");

		RegMobileUser mobileUser = (RegMobileUser) request.getSession(true).getAttribute("addMDRDetailsSession");
		logger.info("mobileuser" + mobileUser);

		if (mobileUser == null) {
			return "redirect:/MDR/addMDRDetails";
		}

		PageBean pageBean = new PageBean("MDR add Details", "merchant/addMDR/MDRDetailsReview", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("pageBean", pageBean);

		return TEMPLATE_DEFAULT;

	}

	@SuppressWarnings("nls")
	@RequestMapping(value = { "/addMDRDetailsConfirm" }, method = RequestMethod.POST)
	public String addMDRDetailsConfirm(@ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
			final Model model, final java.security.Principal principal, final HttpServletRequest request) {

		logger.info("04 About to add value after confrim");

		PageBean pageBean = new PageBean("Succefully MDR Added", "merchant/addMDR/MDRDetailsSuccess",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		RegMobileUser MDRDetailsSavedInHttpSession = (RegMobileUser) request.getSession(true)
				.getAttribute("addMDRDetailsSession");

		if (MDRDetailsSavedInHttpSession == null) {
			return "redirect:/MDR/addMDRDetails";

		}

		BaseDataImpl baseData = new BaseDataImpl();
		RegMobileUser a = baseData.vaildated(MDRDetailsSavedInHttpSession);
		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("addMdrErSession", "yes");
			return "redirect:/MDR/addMDRDetails";
		}

		model.addAttribute(MDRDetailsSavedInHttpSession);
		MobiMDR regMerchantMDR = null;
		try {
			logger.info("Add Mdr Call");
			regMerchantMDR = mdrDetailsService.addMDR(MDRDetailsSavedInHttpSession);

			/*
			 * String serviceName = "MDR_SYNC"; ResponseDetails data =
			 * mdrDetailsService.sendMDRDetails(regMerchantMDR, serviceName); if (data !=
			 * null) { if (data.getResponseCode().equals("0000")) {
			 * logger.info("MDR Details Request Sent and recieved response"); } else {
			 * logger.info("Failed to send MDR Request details"); } }
			 */
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (regMerchantMDR != null) {

			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(
					(regMerchantMDR.getMid() + "~" + regMerchantMDR.getCardBrand()), principal.getName(),
					"addMDRDetails");
			if (auditTrail.getUsername() != null) {
				logger.info("MDR for :" + auditTrail.getUsername() + " Successfully Added by Admin: "
						+ auditTrail.getModifiedBy());
				logger.info("Cache refresh after ADD MDR using AGNI API");
				refreshCache();
			}
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("regMobileUser", regMobileUser);
		model.addAttribute("MDR", regMerchantMDR);
//		model.addAttribute("mid", MDRDetailsSavedInHttpSession.getMid());

		request.getSession(true).removeAttribute("addMDRDetailsSession");

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/updateMDRDetails" }, method = RequestMethod.GET)
	public String displayUpdateMDR(Model model, @ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
//			@RequestParam("id") Long id,
			final java.security.Principal principal) {
		logger.info("01 About list Update MDR List page");
		List<Merchant> merchant1 = merchantService.loadMerchant();
		Set<String> merchantNameList = new HashSet<String>();
		for (Merchant t : merchant1) {
			String businessName = t.getBusinessName();
			String email = t.getUsername();
			merchantNameList.add(businessName.toString() + "~" + email);
		}
		logger.info("merchantNameList" + merchantNameList);
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/updateMDRDetails", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantNameList", merchantNameList);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("mobileUser", regMobileUser);
		logger.info("update MDR completed");

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/updateMDRMerchant" }, method = RequestMethod.GET)
	public String updateMDRMerchant(Model model, @ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
			final HttpServletRequest request, @RequestParam("id") Long id, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("02 About to list MDR List by Merchant ID:" + id);

		PaginationBean<MobiMDR> paginationBean = new PaginationBean<MobiMDR>();
		paginationBean.setCurrPage(currPage);

		List<Merchant> merchant1 = merchantService.loadMerchant();
		Merchant midDetails = mobileUserService.loadIserMidDetails(id);
		regMobileUser.setBusinessName(midDetails.getBusinessName());
		logger.info("midDetails" + midDetails);

//		Set<String> listMid = new HashSet<String>();

		/*
		 * List<String> listMid = new ArrayList<String>(); if
		 * (midDetails.getMid().getMid() != null) {
		 * 
		 * listMid.add(midDetails.getMid().getMid());
		 * 
		 * } if (midDetails.getMid().getUmMid() != null) {
		 * 
		 * listMid.add(midDetails.getMid().getUmMid());
		 * 
		 * } if (midDetails.getMid().getUmMotoMid() != null) {
		 * 
		 * listMid.add(midDetails.getMid().getUmMotoMid());
		 * 
		 * }
		 * 
		 * if (midDetails.getMid().getUmEzywayMid() != null) {
		 * 
		 * listMid.add(midDetails.getMid().getUmEzywayMid());
		 * 
		 * } if (midDetails.getMid().getUmEzyrecMid() != null) {
		 * 
		 * listMid.add(midDetails.getMid().getUmEzyrecMid());
		 * 
		 * 
		 * } if (midDetails.getMid().getUmEzypassMid() != null) {
		 * 
		 * listMid.add(midDetails.getMid().getUmEzypassMid()); }
		 * 
		 * 
		 * logger.info("listMid"+listMid);
		 */
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/updateMDRDetails", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", regMobileUser);
		mdrDetailsService.listMDRDetailsByMid(paginationBean, midDetails.getMid());

		/*
		 * Set<String> brandDetails = new HashSet<String>(); List<MobiMDR> mdrdetails =
		 * MDRDetailsService.loadCardDetails(mid); for (MobiMDR t : mdrdetails) { String
		 * cardBrand = t.getCardBrand(); brandDetails.add(cardBrand); }
		 */

		logger.info("No of Records: " + paginationBean.getItemList().size());
		logger.info("ItemList " + paginationBean.getItemList());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}
		logger.info("ItemList " + paginationBean.getItemList().toString());
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);

		// request.setAttribute("dto", mdrObj);
		// return "redirect:/MDR/changeMDRDetails";

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/changeMDRDetails/{mid}" }, method = RequestMethod.GET)
	public String changeMDRDetails(final Model model, final HttpServletRequest request, @PathVariable final String mid,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("03 About to update MDR details by MID:" + mid);
		MobiMDR mdrObj = new MobiMDR();
		mdrObj = mdrDetailsService.loadCardAndMDRDetails(mid);

		if (mdrObj.getPayLater() == null) {
			mdrObj.setPayLater("No");
		}
		if (mdrObj.getSettlePeriod() == null) {
			mdrObj.setSettlePeriod("1");
		}
		if (mdrObj.getAmount() == null) {
			mdrObj.setAmount("0");
		}
		if (mdrObj.getInstallment() == null) {
			mdrObj.setInstallment("0");
		}
		mdrObj.getPayLater();
		mdrObj.getSettlePeriod();
		mdrObj.getAmount();
		mdrObj.getInstallment();

		logger.info("PayLater:" + mdrObj.getPayLater());
		logger.info("SettlePeriod:" + mdrObj.getSettlePeriod());
		logger.info("Amount:" + mdrObj.getAmount());
		logger.info("Installment:" + mdrObj.getInstallment());
		logger.info("Credit Card foreign host MDR:" + mdrObj.getCreditForeignHostMDR());

		PageBean pageBean = new PageBean("MDR update Details", "merchant/addMDR/changeMDRDetails", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mdrObj", mdrObj);

		String err = (String) request.getSession(true).getAttribute("editMobiMdrErSession");
		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {
				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("editMobiMdrErSession");
			}
		}

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/changeMDRDetailsReview" }, method = RequestMethod.POST)
	public String changeMDRDetailsReview(final Model model, final HttpServletRequest request,
			@ModelAttribute("mobileUser") final RegMobileUser mobileUser, final java.security.Principal principal) {

		logger.info("04 Updated values to confrim");
		logger.info("settlePeriod" + mobileUser.getSettlePeriod());
		logger.info("payLater" + mobileUser.getPayLater());
		logger.info("amount" + mobileUser.getAmount());
		logger.info("installment" + mobileUser.getInstallment());

		MobiMDR mdrObj = new MobiMDR();
		mdrObj.setCreditLocalMerchantMDR(Float.valueOf(mobileUser.getDomCreditMerchantMDR()));
		mdrObj.setCardBrand(mobileUser.getBrand());
		mdrObj.setMid(mobileUser.getMid());
		mdrObj.setCreditForeignHostMDR(Float.valueOf(mobileUser.getFoCreditHostMDR()));
		mdrObj.setCreditForeignMerchantMDR(Float.valueOf(mobileUser.getFoCreditMerchantMDR()));
		mdrObj.setCreditLocalHostMDR(Float.valueOf(mobileUser.getDomCreditHostMDR()));
		mdrObj.setCreditLocalMerchantMDR(Float.valueOf(mobileUser.getDomCreditMerchantMDR()));
		mdrObj.setDebitForeignHostMDR(Float.valueOf(mobileUser.getFoDebitHostMDR()));
		mdrObj.setDebitForeignMerchantMDR(Float.valueOf(mobileUser.getFoDebitMerchantMDR()));
		mdrObj.setDebitLocalHostMDR(Float.valueOf(mobileUser.getDomDebitHostMDR()));
		mdrObj.setDebitLocalMerchantMDR(Float.valueOf(mobileUser.getDomDebitMerchantMDR()));
		mdrObj.setSettlePeriod(mobileUser.getSettlePeriod());
		mdrObj.setPayLater(mobileUser.getPayLater());
		mdrObj.setAmount(mobileUser.getAmount());
		mdrObj.setInstallment(mobileUser.getInstallment());

		if (mdrObj == null) {
			return "redirect:/MDR/updateMDRDetails";
		}

		BaseDataImpl baseData = new BaseDataImpl();
		MobiMDR a = baseData.vaildated(mdrObj);
		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editMobiMdrErSession", "yes");
			return "redirect:/MDR/changeMDRDetails/" + mdrObj.getMid() + "";
		}

		PageBean pageBean = new PageBean("MDR add Details", "merchant/addMDR/updateMDRDetailsReview",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		model.addAttribute("mdrObj", mdrObj);
		model.addAttribute("pageBean", pageBean);
		request.getSession(true).setAttribute("updateMDRDetailsSession", mdrObj);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/updateMDRDetailsConfirm" }, method = RequestMethod.POST)
	public String updateMDRDetailsConfirm(final Model model,
			@ModelAttribute("mobileUser") final RegMobileUser regMobileUser, final java.security.Principal principal,
			final HttpServletRequest request) {

		logger.info("05 About to update, confrim values");

		PageBean pageBean = new PageBean("Succefully MDR Added", "merchant/addMDR/updateMDRSuccess", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		MobiMDR MDRDetailsSavedInHttpSession = (MobiMDR) request.getSession(true)
				.getAttribute("updateMDRDetailsSession");

		if (MDRDetailsSavedInHttpSession == null) {
			return "redirect:/MDR/updateMDRDetails";

		}

		BaseDataImpl baseData = new BaseDataImpl();
		MobiMDR a = baseData.vaildated(MDRDetailsSavedInHttpSession);
		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editMobiMdrErSession", "yes");
			return "redirect:/MDR/changeMDRDetails/" + MDRDetailsSavedInHttpSession.getMid() + "";
		}

		logger.info("MDRDetailsSavedInHttpSession: " + MDRDetailsSavedInHttpSession.getCardBrand());
//		logger.info("MDRDetailsSavedInHttpSessionid: "+MDRDetailsSavedInHttpSession.getId());
		MobiMDR updMerchantMDR = null;

		logger.info("update Mdr Call");
		updMerchantMDR = mdrDetailsService.updateMDR(MDRDetailsSavedInHttpSession);

		String serviceName = "MDR_SYNC";
		ResponseDetails data = mdrDetailsService.sendMDRDetails(updMerchantMDR, serviceName);
		if (data != null) {
			if (data.getResponseCode().equals("0000")) {
				logger.info("MDR Details Request Sent and recieved response");
			} else {
				logger.info("Failed to send MDR Request details");
			}
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("MDR", updMerchantMDR);
		request.getSession(true).removeAttribute("updateMDRDetailsSession");

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/settelmentMDR" }, method = RequestMethod.GET)
	public String displaySettlementMDR(Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("displaySettlementMDR");
		List<Merchant> merchant1 = merchantService.loadMerchant();
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/settlementMDR", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
		paginationBean.setCurrPage(currPage);

		mdrDetailsService.listAllSettlementMDR(paginationBean, null, null);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);
		logger.info("displaySettlementMDR completed");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/mobiliteSettelmentMDR" }, method = RequestMethod.GET)
	public String displayMobiliteSettlementMDR(Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("displayMobiliteSettlementMDR");
		List<Merchant> merchant1 = merchantService.loadMerchant();
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/mobiliteSettlementMDR", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
		paginationBean.setCurrPage(currPage);

		mdrDetailsService.listMobiliteAllSettlementMDR(paginationBean, null, null);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);
		logger.info("displayMobiliteSettlementMDR completed");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/settlementMDRMerchant" }, method = RequestMethod.GET)
	public String settlementMDRMerchant(HttpServletRequest request, final Model model, @RequestParam final String id,
			@RequestParam final String date, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("settlementMDRMerchant");
		logger.info("id::::::::::::" + id);
		logger.info("date::::::::::" + date);
		List<Merchant> merchant1 = merchantService.loadMerchant();
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/settlementMDR", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
		paginationBean.setCurrPage(currPage);

		if (id != "") {
			Merchant midDetails = mobileUserService.loadIserMidDetails(Long.parseLong(id));
			mdrDetailsService.listAllSettlementMDR(paginationBean, midDetails.getMid(), date);
		} else {
			mdrDetailsService.listAllSettlementMDR(paginationBean, null, date);
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);
		logger.info("displaySettlementMDR completed");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/mobiliteSettlementMDRMerchant" }, method = RequestMethod.GET)
	public String mobiliteSettlementMDRMerchant(HttpServletRequest request, final Model model,
			@RequestParam final String id, @RequestParam final String date,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("mobiliteSettlementMDRMerchant");
		logger.info("id::::::::::::" + id);
		logger.info("date::::::::::" + date);
		List<Merchant> merchant1 = merchantService.loadMerchant();
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/mobiliteSettlementMDR", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
		paginationBean.setCurrPage(currPage);

		if (id != "") {
			Merchant midDetails = mobileUserService.loadIserMidDetails(Long.parseLong(id));
			mdrDetailsService.listMobiliteAllSettlementMDR(paginationBean, midDetails.getMid(), date);
		} else {
			mdrDetailsService.listMobiliteAllSettlementMDR(paginationBean, null, date);
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);
		logger.info("mobiliteSettlementMDRMerchant completed");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = "/settlementMDRExport", method = RequestMethod.GET)
	public ModelAndView getExportUMEzyway(final Model model, @RequestParam("fromDate") String date,
			@RequestParam("id") String id, @RequestParam("export") String export, HttpServletRequest request,
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

		String dat = date;
		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
		paginationBean.setCurrPage(currPage);

		if (id != "") {
			logger.info("Merchant ID :" + id);
			Merchant midDetails = mobileUserService.loadIserMidDetails(Long.parseLong(id));
			mdrDetailsService.exportSettlementMDRByAdmin(paginationBean, midDetails.getMid(), date);

		} else {
			mdrDetailsService.exportSettlementMDRByAdmin(paginationBean, null, date);
		}

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<SettlementMDR> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			logger.info("Generate Excel");
			return new ModelAndView("settlementMDRExcel", "settlementMDRList", list1);
		} else {
			logger.info("Generate PDF");
			return new ModelAndView("settlementMDRPdf", "settlementMDRList", list1);
		}

	}

	@RequestMapping(value = "/mobilitesettlementMDRExport", method = RequestMethod.GET)
	public ModelAndView getMobiliteExportUMEzyway(final Model model, @RequestParam("fromDate") String date,
			@RequestParam("id") String id, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		String dat = date;
		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
		paginationBean.setCurrPage(currPage);

		if (id != "") {
			logger.info("Merchant ID :" + id);
			Merchant midDetails = mobileUserService.loadIserMidDetails(Long.parseLong(id));
			mdrDetailsService.exportMobiliteSettlementMDRByAdmin(paginationBean, midDetails.getMid(), date);

		} else {
			mdrDetailsService.exportMobiliteSettlementMDRByAdmin(paginationBean, null, date);
		}

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<SettlementMDR> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			logger.info("Generate Excel");
			return new ModelAndView("mobiliteSettlementMDRExcel", "settlementMDRList", list1);
		} else {
			logger.info("Generate PDF");
			return new ModelAndView("mobiliteSettlementMDRPdf", "settlementMDRList", list1);
		}

	}

	@RequestMapping(value = { "/bizappSettlement" }, method = RequestMethod.GET)
	public String displaybizappSettlement(Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("displaybizappSettlement");
		List<Merchant> merchant1 = merchantService.loadMerchant();
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/bizappSettlementMDR", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		PaginationBean<BizAppSettlement> paginationBean = new PaginationBean<BizAppSettlement>();
		paginationBean.setCurrPage(currPage);

		// mdrDetailsService.listAllSettlementMDR(paginationBean,null,null);

		mdrDetailsService.listBizappSettlementMDR(paginationBean, null);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);
		logger.info("displaySettlementMDR completed");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/bizappSettlementMDRMerchant" }, method = RequestMethod.GET)
	public String bizappSettlementMDRMerchant(HttpServletRequest request, final Model model,
			@RequestParam final String id, @RequestParam final String date,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("bizappSettlementMDRMerchant");
		logger.info("id::::::::::::" + id);
		logger.info("date::::::::::" + date);
		List<Merchant> merchant1 = merchantService.loadMerchant();
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/bizappSettlementMDR", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		PaginationBean<BizAppSettlement> paginationBean = new PaginationBean<BizAppSettlement>();
		paginationBean.setCurrPage(currPage);

		mdrDetailsService.listBizappSettlementMDR(paginationBean, date);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);
		logger.info("displaySettlementMDR completed");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = "/bizappSettlementMDRExport", method = RequestMethod.GET)
	public ModelAndView getbizappSettlementUMEzyway(final Model model, @RequestParam("fromDate") String date,
			@RequestParam("id") String id, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		String dat = date;
		PaginationBean<BizAppSettlement> paginationBean = new PaginationBean<BizAppSettlement>();
		paginationBean.setCurrPage(currPage);

		logger.info("Merchant ID :" + id);

		mdrDetailsService.exportbizappSettlementMDRByAdmin(paginationBean, date);
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<BizAppSettlement> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			logger.info("Generate Excel");
			return new ModelAndView("bizappSettlementMDRExcel", "settlementMDRList", list1);
		} else {
			logger.info("Generate PDF");
			return new ModelAndView("bizappSettlementMDRPdf", "settlementMDRList", list1);
		}

	}

	@RequestMapping(value = { "/fileRegenerate" }, method = RequestMethod.GET)
	public String fileRegenerate(Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("To display File Regeneration Page");
//		List<Merchant> merchant1 = merchantService.loadUMerchant();
//		List<SettlementMDR> merchant1 = mdrDetailsService.listMIDByDate(null);
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/fileRegenerate", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
//		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
//		paginationBean.setCurrPage(currPage);

//		mdrDetailsService.listAllSettlementMDR(paginationBean,null,null);
//		logger.info("No of Records: "+paginationBean.getItemList().size());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("settleDate", "");
//		model.addAttribute("merchant1", merchant1);
//		model.addAttribute("paginationBean", paginationBean);
//		logger.info("displaySettlementMDR completed");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/regenerateForm" }, method = RequestMethod.GET)
	public String regenerateForm(final Model model, @RequestParam("date") String date,
			final java.security.Principal principal) {
		logger.info("To display Regeneration From by Date :" + date);
//		List<Merchant> merchant1 = merchantService.loadUMerchant();
		List<SettlementMDR> merchant1 = mdrDetailsService.listMIDByDate(date);
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/fileRegenerate", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
//		PaginationBean<SettlementMDR> paginationBean = new PaginationBean<SettlementMDR>();
//		paginationBean.setCurrPage(currPage);

//		mdrDetailsService.listAllSettlementMDR(paginationBean,null,null);
//		logger.info("No of Records: "+paginationBean.getItemList().size());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("settleDate", date);
//		model.addAttribute("paginationBean", paginationBean);
//		logger.info("displaySettlementMDR completed");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/generateFile" }, method = RequestMethod.POST)
	public String generateFile(@RequestParam("mid") String mid, @RequestParam("date") String date,
			@RequestParam("merchantFile") String merchantFile, @RequestParam("mdrFile") String mdrFile,
			@RequestParam("deductionFile") String deductionFile, @RequestParam("csvFile") String csvFile,
			@RequestParam("settleType") String settleType, @RequestParam("mailTo") String mailTo,
			@RequestParam("mailCC") String mailCC,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final HttpServletRequest request,
			final Model model, final java.security.Principal principal) throws Exception {

		logger.info("MID:::::::::::" + mid);
		logger.info("Date::::::::::" + date);
		logger.info("merchantFile::" + merchantFile);
		logger.info("mdrFile:::::::" + mdrFile);
		logger.info("deductionFile:" + deductionFile);
		logger.info("csvFile:::::::" + csvFile);
		logger.info("settleType::" + settleType);
		logger.info("mailTo::::::::" + mailTo);
		logger.info("mailCC::::::::" + mailCC);

		List<SettlementMDR> merchant1 = mdrDetailsService.listMIDByDate(date);
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/fileRegenerate", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		String resDate1 = null;
		Date resDate = null;
		try {
			resDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		resDate1 = dateFormat1.format(resDate);
		logger.info("Parsed settle Date:" + resDate1);

		FileGenerate TxnDet = new FileGenerate();
		TxnDet.setMid(mid);
		TxnDet.setDate(resDate1);
		TxnDet.setMerchantFile(merchantFile);
		TxnDet.setMdrFile(mdrFile);
		TxnDet.setDeductionFile(deductionFile);
		TxnDet.setCsvFile(csvFile);
		TxnDet.setSettleType(settleType);
		TxnDet.setMailCC(mailCC);
		TxnDet.setMailTo(mailTo);

		ResponseDetails data = MotoPaymentCommunication.FileRegenerate(TxnDet);

		if (data != null) {

			logger.info("Output optained :" + data);

			if (data.getResponseCode().equals("0001")) {

				logger.info("Failure :" + data.getResponseCode());

				model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
			} else {

				logger.info("Success :" + data.getResponseCode());

				model.addAttribute("responseData", data.getResponseDescription());
			}
		} else {

			logger.info("Output not  optained ");

			model.addAttribute("responseData", data.getResponseDescription() + "...  Try Again..");
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("settleDate", date);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/addProMDR" }, method = RequestMethod.GET)
	public String addProMDR(Model model, final java.security.Principal principal, final HttpServletRequest request) {
		logger.info("addProMDR");
		List<Merchant> merchant1 = merchantService.loadMerchant();
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/addProMDR", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		logger.info("addProMDR completed");

		String err = (String) request.getSession(true).getAttribute("addProMdrErSession");

		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {

				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("addMdrErSession");
			}
		}

		return TEMPLATE_DEFAULT;
	}

	@SuppressWarnings("nls")
	@RequestMapping(value = { "/findProMID" }, method = RequestMethod.GET)
	public String findProMID(final Model model, @RequestParam("id") Long id, final java.security.Principal principal) {

		logger.info("/findProMID: to ADD " + id);
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/addProMDR", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		List<Merchant> merchant1 = merchantService.loadMerchant();
		RegMobileUser regMobileUser = new RegMobileUser();
		Merchant midDetails = mobileUserService.loadIserMidDetails(id);

		if (midDetails.getMid().getUmMid() != null) {

			logger.info("UmMid" + midDetails.getMid().getUmMid());
			regMobileUser.setUm_mid(midDetails.getMid().getUmMid());

		}
		if (midDetails.getMid().getUmMotoMid() != null) {

			logger.info("UmMotoMid" + midDetails.getMid().getUmMotoMid());
			regMobileUser.setUm_motoMid(midDetails.getMid().getUmMotoMid());

		}

		if (midDetails.getMid().getUmEzywayMid() != null) {

			logger.info("UmEzywayMid" + midDetails.getMid().getUmEzywayMid());
			regMobileUser.setUm_ezywayMid(midDetails.getMid().getUmEzywayMid());

		}
		if (midDetails.getMid().getUmEzyrecMid() != null) {

			logger.info("UmEzyrecMid" + midDetails.getMid().getUmEzyrecMid());
			regMobileUser.setUm_ezyrecMid(midDetails.getMid().getUmEzyrecMid());

		}
		if (midDetails.getMid().getUmEzypassMid() != null) {

			logger.info("UmEzypassMid" + midDetails.getMid().getUmEzypassMid());
			regMobileUser.setUm_ezypassMid(midDetails.getMid().getUmEzypassMid());
		}

		if (midDetails.getMid().getGpayMid() != null) {

			logger.info("GpayMid" + midDetails.getMid().getGpayMid());
			regMobileUser.setgPayMid(midDetails.getMid().getGpayMid());
		}

		regMobileUser.setBusinessName(midDetails.getBusinessName());
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		logger.info("pageBean" + pageBean);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("mobileUser", regMobileUser);
		model.addAttribute("merchantId", id);
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/regProMDR" }, method = RequestMethod.POST)
	public String regProMDR(Model model, final HttpServletRequest request,
			@ModelAttribute("mobileUser") final MobiProductMDR regMobileUser, @RequestParam("mobiMDR") String mobiMDR,
			@RequestParam("hostMDR") String hostMDR, @RequestParam("mid") String mid,
			final java.security.Principal principal) {

		logger.info("/regProMDR");
		String[] pack = mid.split("~");
		logger.info("mid:::::::::::::::" + pack[0]);
		logger.info("Product:::::::::::::::" + pack[1]);

		PageBean pageBean = null;
		regMobileUser.setMid(pack[0]);
		regMobileUser.setProdType(pack[1]);
		regMobileUser.setMobiMdr(mobiMDR);
		regMobileUser.setHostMdr(hostMDR);

		BaseDataImpl baseData = new BaseDataImpl();

		MobiProductMDR a = baseData.vaildated(regMobileUser);

		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("addProMdrErSession", "yes");
			return "redirect:/MDR/addProMDR";
		}

		pageBean = new PageBean("Reg MDR Detail", "merchant/addMDR/ProMDRReview", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", regMobileUser);
		logger.info("mobileUser: " + regMobileUser);
		request.getSession(true).setAttribute("addProMDRSession", regMobileUser);
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/addProMDRConfirm" }, method = RequestMethod.POST)
	public String addProMDRConfirm(@ModelAttribute("mobileUser") final MobiProductMDR regMobileUser, final Model model,
			final java.security.Principal principal, final HttpServletRequest request) {
		logger.info("confirm to add Product MDR Details Confirms");
		PageBean pageBean = new PageBean("Succefully MDR Added", "merchant/addMDR/ProMDRSuccess", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		MobiProductMDR MDRDetailsSavedInHttpSession = (MobiProductMDR) request.getSession(true)
				.getAttribute("addProMDRSession");

		if (MDRDetailsSavedInHttpSession == null) {
			return "redirect:/MDR/addProMDR";
		}

		MDRDetailsSavedInHttpSession.setStatus(CommonStatus.ACTIVE);

		model.addAttribute(MDRDetailsSavedInHttpSession);
		MobiProductMDR regMerchantMDR = null;
		logger.info("Add Mdr Call");
		regMerchantMDR = mdrDetailsService.addProMDR(MDRDetailsSavedInHttpSession);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("regMobileUser", regMobileUser);
		model.addAttribute("MDR", regMerchantMDR);

		request.getSession(true).removeAttribute("addProMDRSession");

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/proMDRList" }, method = RequestMethod.GET)
	public String proMDRList(Model model, final java.security.Principal principal) {
		logger.info("proMDRList");
		List<Merchant> merchant1 = merchantService.loadMerchant();
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/ProMDRList", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		logger.info("proMDRList completed");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/proMDRListbyId" }, method = RequestMethod.GET)
	public String proMDRListbyId(Model model, @ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
			final HttpServletRequest request, @RequestParam("id") Long id, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("proMDRListbyId");
		PaginationBean<MobiProductMDR> paginationBean = new PaginationBean<MobiProductMDR>();
		paginationBean.setCurrPage(currPage);

		List<Merchant> merchant1 = merchantService.loadMerchant();
		Merchant midDetails = mobileUserService.loadIserMidDetails(id);
		regMobileUser.setBusinessName(midDetails.getBusinessName());

		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/ProMDRList", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", regMobileUser);
		mdrDetailsService.listproMDRByMid(paginationBean, midDetails, id);

		model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/updateProMDR/{mid}/{prodType}" }, method = RequestMethod.GET)
	public String updateProMDR(final Model model, final HttpServletRequest request, @PathVariable final String mid,
			@PathVariable final String prodType,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("updateProMDR");

		logger.info("mid::::::::::" + mid);
		logger.info("prodType:::::" + prodType);

		MobiProductMDR mdrObj = new MobiProductMDR();
		mdrObj = mdrDetailsService.loadProMDR(mid, prodType);

		PageBean pageBean = new PageBean("MDR update Details", "merchant/addMDR/updateProMDR", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mdrObj", mdrObj);

		String err = (String) request.getSession(true).getAttribute("editProdMdrErSession");

		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {

				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("editErSession");
			}
		}

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/updateProMDRReview" }, method = RequestMethod.POST)
	public String updateProMDRReview(Model model, final HttpServletRequest request,
			@ModelAttribute("mobileUser") final MobiProductMDR regMobileUser, @RequestParam("mobiMDR") String mobiMDR,
			@RequestParam("hostMDR") String hostMDR, @RequestParam("mid") String mid, @RequestParam("id") String id,
			@RequestParam("prodType") String prodType, final java.security.Principal principal) {

		logger.info("/updateProMDRReview");
		logger.info("mid:::::::::::::::" + mid);
		logger.info("Product:::::::::::::::" + prodType);

		PageBean pageBean = null;
		regMobileUser.setId(Long.valueOf(id));
		regMobileUser.setMid(mid);
		regMobileUser.setProdType(prodType);
		regMobileUser.setMobiMdr(mobiMDR);
		regMobileUser.setHostMdr(hostMDR);

		BaseDataImpl baseData = new BaseDataImpl();

		MobiProductMDR a = baseData.vaildated(regMobileUser);

		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editProdMdrErSession", "yes");

			return "redirect:/MDR/updateProMDR/" + id + "/" + prodType + "";
		}

		pageBean = new PageBean("Reg MDR Detail", "merchant/addMDR/updateProMDRReview", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", regMobileUser);
		logger.info("mobileUser: " + regMobileUser);
		request.getSession(true).setAttribute("updateProMDRSession", regMobileUser);
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/updateProMDRConfirm" }, method = RequestMethod.POST)
	public String updateProMDRConfirm(@ModelAttribute("mobileUser") final MobiProductMDR regMobileUser,
			final Model model, final java.security.Principal principal, final HttpServletRequest request) {
		logger.info("confirm to update Product MDR Details ");
		PageBean pageBean = new PageBean("Succefully MDR updated", "merchant/addMDR/updateProMDRSuccess",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		MobiProductMDR MDRDetailsSavedInHttpSession = (MobiProductMDR) request.getSession(true)
				.getAttribute("updateProMDRSession");

		if (MDRDetailsSavedInHttpSession == null) {
			return "redirect:/MDR/proMDRList";
		}

		MobiProductMDR updprotMDR = mdrDetailsService.loadProMDRbyId(MDRDetailsSavedInHttpSession.getId());

		logger.info("Id:::::" + updprotMDR.getId());
		logger.info("Mid:::::" + updprotMDR.getMid());
		logger.info("Protype:::::" + updprotMDR.getProdType());
		logger.info("Status:::::" + MDRDetailsSavedInHttpSession.getStatus());

		updprotMDR.setModifiedDate(new Date());
		updprotMDR.setMobiMdr(MDRDetailsSavedInHttpSession.getMobiMdr());
		updprotMDR.setStatus(MDRDetailsSavedInHttpSession.getStatus());
		model.addAttribute(MDRDetailsSavedInHttpSession);
		MobiProductMDR regMerchantMDR = null;
		logger.info("update Mdr Call");
		regMerchantMDR = mdrDetailsService.updateProMDR(updprotMDR);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("regMobileUser", regMobileUser);
		model.addAttribute("MDR", regMerchantMDR);

		request.getSession(true).removeAttribute("updateProMDRSession");

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/chargeBack" }, method = RequestMethod.GET)
	public String chargeBack(Model model, final java.security.Principal principal) {
		logger.info("chargeBack");
		List<Merchant> merchant1 = merchantService.loadUMMerchant();
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/chargeBack", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		PaginationBean<RegMobileUser> paginationBean = new PaginationBean<RegMobileUser>();
		paginationBean.setCurrPage(1);
		mdrDetailsService.listChargeBack(paginationBean, null, null);

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		logger.info("chargeBack page loaded");
		return TEMPLATE_DEFAULT;
	}

	@SuppressWarnings("nls")
	@RequestMapping(value = { "/chargeProMID" }, method = RequestMethod.GET)
	public String chargeProMID(final Model model, @RequestParam("id") Long id,
			final java.security.Principal principal) {

		logger.info("/chargeProMID: to ADD Charge back  " + id);
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/chargeBack", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		List<Merchant> merchant1 = merchantService.loadUMMerchant();
		RegMobileUser regMobileUser = new RegMobileUser();
		Merchant midDetails = mobileUserService.loadIserMidDetails(id);

		if (midDetails.getMid().getUmMid() != null) {

			logger.info("UmMid" + midDetails.getMid().getUmMid());
			regMobileUser.setUm_mid(midDetails.getMid().getUmMid());

		}
		if (midDetails.getMid().getUmMotoMid() != null) {

			logger.info("UmMotoMid" + midDetails.getMid().getUmMotoMid());
			regMobileUser.setUm_motoMid(midDetails.getMid().getUmMotoMid());

		}

		if (midDetails.getMid().getUmEzywayMid() != null) {

			logger.info("UmEzywayMid" + midDetails.getMid().getUmEzywayMid());
			regMobileUser.setUm_ezywayMid(midDetails.getMid().getUmEzywayMid());

		}
		if (midDetails.getMid().getUmEzyrecMid() != null) {

			logger.info("UmEzyrecMid" + midDetails.getMid().getUmEzyrecMid());
			regMobileUser.setUm_ezyrecMid(midDetails.getMid().getUmEzyrecMid());

		}
		if (midDetails.getMid().getUmEzypassMid() != null) {

			logger.info("UmEzypassMid" + midDetails.getMid().getUmEzypassMid());
			regMobileUser.setUm_ezypassMid(midDetails.getMid().getUmEzypassMid());
		}

		if (midDetails.getMid().getGpayMid() != null) {

			logger.info("GpayMid" + midDetails.getMid().getGpayMid());
			regMobileUser.setgPayMid(midDetails.getMid().getGpayMid());
		}

		regMobileUser.setBusinessName(midDetails.getBusinessName());
		regMobileUser.setMerchantID(String.valueOf(id));
		PaginationBean<MobiProductMDR> paginationBean = new PaginationBean<MobiProductMDR>();
		paginationBean.setCurrPage(1);
		mdrDetailsService.listChargeBackByMid(paginationBean, midDetails.getMid(), null);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		logger.info("pageBean" + pageBean);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("mobileUser", regMobileUser);
		model.addAttribute("merchantId", id);
		return TEMPLATE_DEFAULT;

	}

	/*
	 * @RequestMapping(value = { "/regChBack " }, method = RequestMethod.POST)
	 * public String regChBack(Model model, final HttpServletRequest request,
	 * 
	 * @ModelAttribute("mobileUser") final MobiProductMDR regChBack,
	 * 
	 * @RequestParam("chBack") String chBack,
	 * 
	 * @RequestParam("mid") String mid,
	 * 
	 * @RequestParam("merchantId") String merchantId, final java.security.Principal
	 * principal) {
	 * 
	 * List<Merchant> merchant1 = merchantService.loadUMMerchant();
	 * 
	 * logger.info("/regChBack"); String[] pack = mid.split("~");
	 * logger.info("mid:::::::::::::::"+pack[0]);
	 * logger.info("Product:::::::::::::::"+pack[1]);
	 * logger.info("chBack:::::::::::::::"+chBack);
	 * logger.info("merchantId:::::::::::::::"+merchantId);
	 * 
	 * PageBean pageBean = null; regChBack.setMid(pack[0]);
	 * regChBack.setProdType("CH_BACK"); regChBack.setMobiMdr(chBack);
	 * regChBack.setHostMdr("0"); regChBack.setStatus(CommonStatus.ACTIVE); pageBean
	 * = new PageBean("Reg MDR Detail","merchant/addMDR/chargeBack",
	 * Module.MOBILE_USER,"mobileuser/sideMenuMobileUser");
	 * 
	 * MobiProductMDR regMerchantMDR = null; logger.info("Add ChargeBack Call");
	 * regMerchantMDR = mdrDetailsService.addProMDR(regChBack);
	 * logger.info("ChargeBack addded");
	 * 
	 * 
	 * logger.info(" Charge Back Summary:" + principal.getName());
	 * PaginationBean<MobiProductMDR> paginationBean = new
	 * PaginationBean<MobiProductMDR>(); paginationBean.setCurrPage(1);
	 * 
	 * Merchant midDetails =
	 * mobileUserService.loadIserMidDetails(Long.parseLong(merchantId));
	 * mdrDetailsService.listChargeBackByMid(paginationBean,midDetails.getMid(),null
	 * );
	 * 
	 * RegMobileUser MobileUser = new RegMobileUser();
	 * 
	 * if (midDetails.getMid().getUmMid() != null) {
	 * 
	 * logger.info("UmMid"+midDetails.getMid().getUmMid());
	 * MobileUser.setUm_mid(midDetails.getMid().getUmMid());
	 * 
	 * } if (midDetails.getMid().getUmMotoMid() != null) {
	 * 
	 * logger.info("UmMotoMid"+midDetails.getMid().getUmMotoMid());
	 * MobileUser.setUm_motoMid(midDetails.getMid().getUmMotoMid());
	 * 
	 * }
	 * 
	 * if (midDetails.getMid().getUmEzywayMid() != null) {
	 * 
	 * logger.info("UmEzywayMid"+midDetails.getMid().getUmEzywayMid());
	 * MobileUser.setUm_ezywayMid(midDetails.getMid().getUmEzywayMid());
	 * 
	 * } if (midDetails.getMid().getUmEzyrecMid() != null) {
	 * 
	 * logger.info("UmEzyrecMid"+midDetails.getMid().getUmEzyrecMid());
	 * MobileUser.setUm_ezyrecMid(midDetails.getMid().getUmEzyrecMid());
	 * 
	 * 
	 * } if (midDetails.getMid().getUmEzypassMid() != null) {
	 * 
	 * logger.info("UmEzypassMid"+midDetails.getMid().getUmEzypassMid());
	 * MobileUser.setUm_ezypassMid(midDetails.getMid().getUmEzypassMid()); }
	 * 
	 * if (midDetails.getMid().getGpayMid() != null) {
	 * 
	 * logger.info("GpayMid"+midDetails.getMid().getGpayMid());
	 * MobileUser.setgPayMid(midDetails.getMid().getGpayMid()); }
	 * 
	 * MobileUser.setBusinessName(midDetails.getBusinessName());
	 * MobileUser.setMerchantID(String.valueOf(merchantId));
	 * 
	 * model.addAttribute("paginationBean", paginationBean);
	 * model.addAttribute("merchant1", merchant1); model.addAttribute("pageBean",
	 * pageBean); model.addAttribute("mobileUser", MobileUser);
	 * 
	 * 
	 * return TEMPLATE_DEFAULT;
	 * 
	 * }
	 */

	@RequestMapping(value = { "/regChBack " }, method = RequestMethod.GET)
	public String regChBack(Model model, final HttpServletRequest request,
			@ModelAttribute("mobileUser") final MobiProductMDR regChBack, @RequestParam("chBack") String chBack,
			@RequestParam("mid") String mid, @RequestParam("mrn") String mrn, final java.security.Principal principal) {

		logger.info("/regChBack");
		logger.info("mid:::::::::::::::" + mid);
		logger.info("chBack:::::::::::::::" + chBack);
		logger.info("mrn:::::::::::::::" + mrn);

		PageBean pageBean = null;
		regChBack.setMid(mid);
		regChBack.setProdType("CH_BACK");
		regChBack.setMobiMdr(chBack);
		regChBack.setHostMdr("0");
		regChBack.setStatus(CommonStatus.ACTIVE);
		regChBack.setRemarks(mrn);
		pageBean = new PageBean("Reg MDR Detail", "merchant/addMDR/umChargeBack", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("txnDet", regChBack);
		logger.info("txnDet" + regChBack);
		request.getSession(true).setAttribute("addChBackSession", regChBack);
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/addChBack" }, method = RequestMethod.POST)
	public String addChBack(final Model model, @ModelAttribute("txnDet") MobiProductMDR TxnDet,
			@RequestParam("mid") String mid, @RequestParam("prodType") String prodType,
			@RequestParam("cbAmount") String cbAmount, @RequestParam("hostAmount") String hostAmount,
			@RequestParam("status") String status, @RequestParam("mrn") String mrn,
			@RequestParam("receivedDate") String receivedDate, @RequestParam("reasonCode") String reasonCode,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {
		logger.info("addChBack");
		logger.info("mid:" + mid);
		logger.info("prodType:" + prodType);
		logger.info("cbAmount:" + cbAmount);
		logger.info("hostAmount:" + hostAmount);
		logger.info("status:" + status);
		logger.info("mrn:" + mrn);
		logger.info("receivedDate:" + receivedDate);
		logger.info("reasonCode:" + reasonCode);

		Date frdate = new Date(receivedDate);
		int fromday = frdate.getDate();
		int frommon = frdate.getMonth() + 1;
		int fromyear = frdate.getYear();
		int currentFrYear = fromyear + 1900;
		String frmon = String.format("%02d", frommon);
		String frday = String.format("%02d", fromday);
		String receivedDate1 = frday + '/' + frmon + '/' + String.valueOf(currentFrYear);
		// receivedDate = String.valueOf(currentFrYear)+'-'+frmon+'-'+frday;

		TxnDet.setMid(mid);
		TxnDet.setProdType("CH_BACK");
		TxnDet.setMobiMdr(cbAmount);
		TxnDet.setHostMdr(cbAmount);
		TxnDet.setStatus(CommonStatus.ACTIVE);
		TxnDet.setRemarks(mrn);

		List<Merchant> merchant1 = merchantService.loadUMMerchant();
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMEzywayList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		MobiProductMDR regMerchantMDR = null;
		logger.info("Add ChargeBack Call");
		regMerchantMDR = mdrDetailsService.addProMDR(TxnDet);
		logger.info("ChargeBack addded");

		UMEcomTxnResponse tr = transactionService.loadUMEzywayTransactionResponse(TxnDet.getRemarks());
		tr.setStatus("CB");
		tr.setF249_TxCh(receivedDate1);
		tr.setF254_DDRespCode(reasonCode);
		transactionService.updateUMTxnRes(tr);
		logger.info("UM response updated ");

		logger.info(" UM-EZWAY Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(1);
		transactionService.listUMEzywayTransaction(paginationBean, null, null, "ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("merchant1", merchant1);
		request.getSession(true).removeAttribute("addChBackSession");

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/umChargeBackExport", method = RequestMethod.GET)
	public ModelAndView umChargeBackExport(final Model model, @RequestParam("id") Long id, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("Inside Charge Back Export");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		String export = "EXCEL";

		/* Merchant currentMerchant = merchantService.loadMerchant(myName); */
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		/*
		 * logger.info("current Merchant: " + currentMerchant.getMid().getEzywayMid());
		 */

		/*
		 * String dat = null; String dat1 = null;
		 */

		List<Merchant> merchant1 = merchantService.loadUMMerchant();
		RegMobileUser regMobileUser = new RegMobileUser();
		Merchant midDetails = mobileUserService.loadIserMidDetails(id);

		if (midDetails.getMid().getUmMid() != null) {

			logger.info("UmMid" + midDetails.getMid().getUmMid());
			regMobileUser.setUm_mid(midDetails.getMid().getUmMid());

		}
		if (midDetails.getMid().getUmMotoMid() != null) {

			logger.info("UmMotoMid" + midDetails.getMid().getUmMotoMid());
			regMobileUser.setUm_motoMid(midDetails.getMid().getUmMotoMid());

		}

		if (midDetails.getMid().getUmEzywayMid() != null) {

			logger.info("UmEzywayMid" + midDetails.getMid().getUmEzywayMid());
			regMobileUser.setUm_ezywayMid(midDetails.getMid().getUmEzywayMid());

		}
		if (midDetails.getMid().getUmEzyrecMid() != null) {

			logger.info("UmEzyrecMid" + midDetails.getMid().getUmEzyrecMid());
			regMobileUser.setUm_ezyrecMid(midDetails.getMid().getUmEzyrecMid());

		}
		if (midDetails.getMid().getUmEzypassMid() != null) {

			logger.info("UmEzypassMid" + midDetails.getMid().getUmEzypassMid());
			regMobileUser.setUm_ezypassMid(midDetails.getMid().getUmEzypassMid());
		}

		if (midDetails.getMid().getGpayMid() != null) {

			logger.info("GpayMid" + midDetails.getMid().getGpayMid());
			regMobileUser.setgPayMid(midDetails.getMid().getGpayMid());
		}

		regMobileUser.setBusinessName(midDetails.getBusinessName());
		regMobileUser.setMerchantID(String.valueOf(id));
		PaginationBean<MobiProductMDR> paginationBean = new PaginationBean<MobiProductMDR>();
		paginationBean.setCurrPage(1);
		mdrDetailsService.listChargeBackByMid(paginationBean, midDetails.getMid(), null);

		/*
		 * String dat = fromDate; String dat1 = toDate; PaginationBean<UMEzyway>
		 * paginationBean = new PaginationBean<UMEzyway>();
		 * paginationBean.setCurrPage(currPage);
		 * 
		 * if(mid.isEmpty()){ logger.info("exporting txn with Dates ");
		 * transactionService.exportUMEzywayTransactionAdmin(paginationBean, dat,
		 * dat1,"ALL"); }else{ logger.info("exporting txn with Dates and MID ");
		 * transactionService.exportUMEzywayTransactionAdmin(paginationBean, dat,
		 * dat1,mid); }
		 */

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<MobiProductMDR> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnUMCBExcel", "umCBList", list1);
		} else {
			logger.info("Need to develop for PDF");
			return new ModelAndView("txnUMCBPdf", "umCBList", list1);
		}

	}

	@RequestMapping(value = { "/payLatMerchantSummary/{currPage}" }, method = RequestMethod.GET)
	public String payLaterMerchantSummary(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info("payLaterMerchantSummary admin");
		PageBean pageBean = new PageBean("transactions list", "transaction/PayLaterMerchantSummary", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" payLaterMerchantSummary:" + principal.getName());

		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		paginationBean.setCurrPage(currPage);

		merchantService.listPayLaterMerchants(paginationBean);

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

	@RequestMapping(value = { "/plMerchantSearch" }, method = RequestMethod.GET)
	public String displaypayLaterSearch(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("displaypayLaterSearch");
		PageBean pageBean = new PageBean("transactions list", "transaction/PayLaterMerchantSummary", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		paginationBean.setCurrPage(currPage);

		merchantService.searchPayLaterMerchants(paginationBean, date, date1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = "/plMerchantExport", method = RequestMethod.GET)
	public ModelAndView exportpayLaterSearch(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final Model model,
			@RequestParam(required = false) String export) {

		logger.info("exportpayLaterSearch");

		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		paginationBean.setCurrPage(currPage);

		merchantService.searchPayLaterMerchants(paginationBean, date, date1);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<Merchant> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("payLaterMerchantExcel", "txnList", list);

		} else {
			return new ModelAndView("payLaterMerchantPdf", "txnList", list);
		}
	}

	@RequestMapping(value = { "/fpxSettelmentMDR" }, method = RequestMethod.GET)
	public String displayFpxSettlementMDR(Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("displaySettlementMDR");

		List<Merchant> merchant1 = merchantService.loadFpxMerchant();

		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/FpxSettlementMDR", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		mdrDetailsService.listFpxSettlementMDR(paginationBean, null);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);
		logger.info("displaySettlementMDR completed");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/fpxSettlementMDRMerchant" }, method = RequestMethod.GET)
	public String FPXSettlementMDRMerchant(HttpServletRequest request, final Model model, @RequestParam final String id,
			@RequestParam final String date, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("fpxsettlementMDRMerchant");
		logger.info("id::::::::::::" + id);
		logger.info("date::::::::::" + date);
		List<Merchant> merchant1 = merchantService.loadFpxMerchant();
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/FpxSettlementMDR", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		if (id != "") {
			Merchant midDetails = mobileUserService.loadIserMidDetails(Long.parseLong(id));
			mdrDetailsService.listFpxSettlementMDRByMid(paginationBean, midDetails, date);
		} else {
			mdrDetailsService.listFpxSettlementMDR(paginationBean, date);
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("paginationBean", paginationBean);
		logger.info("displaySettlementMDR completed");
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = "/fpxSettlementMDRExport", method = RequestMethod.GET)
	public ModelAndView getExportfpxSettlemen(final Model model, @RequestParam final String date,
			@RequestParam final String id, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam(required = false) String export, final java.security.Principal principal) {

		String dat = date;
		PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
		paginationBean.setCurrPage(currPage);

		if (id != "") {
			Merchant midDetails = mobileUserService.loadIserMidDetails(Long.parseLong(id));
			mdrDetailsService.listFpxSettlementMDRByMid(paginationBean, midDetails, date);
		} else {
			mdrDetailsService.listFpxSettlementMDR(paginationBean, date);
		}

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<FpxTransaction> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			logger.info("Generate Excel");
			return new ModelAndView("fpxSettlementMDRExcel", "settlementMDRList", list1);
		} else {
			logger.info("Generate PDF");
			return new ModelAndView("fpxSettlementMDRPdf", "settlementMDRList", list1);
		}

	}

	@RequestMapping(value = { "/updateMDRDetailsByRateId" }, method = RequestMethod.GET)
	public String displayUpdateMDRByRateID(Model model, @ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
//			@RequestParam("id") Long id,
			final java.security.Principal principal) {
		logger.info("01 About list Update MDR List page");
		List<Merchant> merchant1 = merchantService.loadMerchant();
		Set<String> merchantNameList = new HashSet<String>();
		for (Merchant t : merchant1) {
			String businessName = t.getBusinessName();
			String email = t.getUsername();
			merchantNameList.add(businessName.toString() + "~" + email);
		}
		logger.info("merchantNameList" + merchantNameList);
		PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/updateMDRDetailsByRateId", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantNameList", merchantNameList);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("mobileUser", regMobileUser);
		logger.info("update MDR completed");

		return TEMPLATE_DEFAULT;
	}

	/*
	 * @RequestMapping(value = { "/updateMDRMerchantByRateID" }, method =
	 * RequestMethod.GET) public String updateMDRMerchantByRateID(Model model,
	 * 
	 * @ModelAttribute("mobileUser") final RegMobileUser regMobileUser, final
	 * HttpServletRequest request,
	 * 
	 * @RequestParam("id") Long id, final java.security.Principal principal,
	 * 
	 * @RequestParam(required = false, defaultValue = "1") final int currPage) {
	 * 
	 * logger.info("02 About to list MDR List by Merchant ID:" + id);
	 * 
	 * PaginationBean<MdrRates> paginationBean = new PaginationBean<MdrRates>();
	 * paginationBean.setCurrPage(currPage);
	 * 
	 * List<Merchant> merchant1 = merchantService.loadMerchant(); Merchant
	 * midDetails = mobileUserService.loadIserMidDetails(id);
	 * regMobileUser.setBusinessName(midDetails.getBusinessName());
	 * logger.info("midDetails" + midDetails);
	 * 
	 * PageBean pageBean = new PageBean("MDR Details",
	 * "merchant/addMDR/updateMDRDetailsByRateId", Module.MOBILE_USER,
	 * "mobileuser/sideMenuMobileUser"); model.addAttribute("pageBean", pageBean);
	 * model.addAttribute("mobileUser", regMobileUser);
	 * 
	 * mdrDetailsService.listMDRDetailsByRateId(paginationBean,
	 * midDetails.getMid());
	 * 
	 * logger.info("No of Records: " + paginationBean.getItemList().size());
	 * logger.info("ItemList " + paginationBean.getItemList()); if
	 * (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() ==
	 * null) { model.addAttribute("responseData", "No Records found"); // table //
	 * response
	 * 
	 * } else { model.addAttribute("responseData", null); } logger.info("ItemList "
	 * + paginationBean.getItemList().toString()); model.addAttribute("merchant1",
	 * merchant1); model.addAttribute("paginationBean", paginationBean);
	 * 
	 * return TEMPLATE_DEFAULT; }
	 */
//rk
	
	@RequestMapping(value = { "/updateMDRMerchantByRateID" }, method = RequestMethod.GET)
    public String updateMDRMerchantByRateID(Model model,
            @ModelAttribute("mobileUser") final RegMobileUser regMobileUser, final HttpServletRequest request,
            @RequestParam("id") Long id, final java.security.Principal principal,
            @RequestParam(required = false, defaultValue = "1") final int currPage) {

 

        logger.info("02 About to list MDR List by Merchant ID:" + id);

 

    //    PaginationBean<MdrRates> paginationBean = new PaginationBean<MdrRates>();
        PaginationBean<MobiMDR> paginationBean = new PaginationBean<MobiMDR>();
        paginationBean.setCurrPage(currPage);

 

        List<Merchant> merchant1 = merchantService.loadMerchant();
        Merchant midDetails = mobileUserService.loadIserMidDetails(id);
        regMobileUser.setBusinessName(midDetails.getBusinessName());
        logger.info("midDetails" + midDetails);

 

        PageBean pageBean = new PageBean("MDR Details", "merchant/addMDR/updateMDRDetailsByRateId", Module.MOBILE_USER,
                "mobileuser/sideMenuMobileUser");
        model.addAttribute("pageBean", pageBean);
        model.addAttribute("mobileUser", regMobileUser);

 

        mdrDetailsService.listMDRDetailsByRateId(paginationBean, midDetails.getMid());

 

        logger.info("No of Records: " + paginationBean.getItemList().size());
        logger.info("ItemList " + paginationBean.getItemList());
        if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
            model.addAttribute("responseData", "No Records found"); // table
                                                                    // response

 

        } else {
            model.addAttribute("responseData", null);
        }
        logger.info("ItemList " + paginationBean.getItemList().toString());
        model.addAttribute("merchant1", merchant1);
        model.addAttribute("paginationBean", paginationBean);

 

        return TEMPLATE_DEFAULT;
    }
	
	
	
	
}
