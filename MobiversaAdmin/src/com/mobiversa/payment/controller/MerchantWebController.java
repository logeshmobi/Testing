package com.mobiversa.payment.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.BoostDailyRecon;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.EwalletTxnDetails;
import com.mobiversa.common.bo.EzyRecurringPayment;
import com.mobiversa.common.bo.FileUpload;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.GrabPayFile;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantInfo;
import com.mobiversa.common.bo.MerchantStatusHistory;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.MotoTxnDetails;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.common.bo.UMMidTxnLimit;
import com.mobiversa.payment.controller.bean.DateTime;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.controller.bean.UpdateApi;
import com.mobiversa.payment.dao.MobileUserDao;
import com.mobiversa.payment.dao.TransactionDao;
import com.mobiversa.payment.dto.BaseDataImpl;
import com.mobiversa.payment.dto.MerchantDTO;
import com.mobiversa.payment.dto.ReaderList;
import com.mobiversa.payment.dto.RegAddMerchant;
import com.mobiversa.payment.dto.RegMobileUser;
import com.mobiversa.payment.service.AdminService;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.DashBoardService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.PromotionService;
import com.mobiversa.payment.service.ReaderService;
import com.mobiversa.payment.service.SubAgentService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.AddMerchantOptionList;
import com.mobiversa.payment.util.EzysettleUtils;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.PropertyLoader;
import com.mobiversa.payment.util.Utils;
import com.mobiversa.payment.validator.AddAgentUserValidator;

//import org.springframework.validation.Validator;
@Controller
@RequestMapping(value = MerchantWebController.URL_BASE)
public class MerchantWebController extends BaseController {

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
	private SubAgentService subAgentService;

	@Autowired
	private PromotionService promotionService;

	@Autowired
	private AddAgentUserValidator validator;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private ReaderService readerService;

	@Autowired
	private MobileUserDao mobileuserDAO;

	@Autowired
	private TransactionDao transactionDAO;

	/* private static final String merchantModel = "merchant"; */

	public static final String URL_BASE = "/admmerchant";
	private static final Logger logger = Logger.getLogger(MerchantWebController.class);

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

	/**
	 * Display a list of all merchants
	 * <p>
	 * Wireframe bank module 03a
	 * </p>
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayMerchantList(final Model model, final Merchant merchant, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("Merchant", "merchant/merchantList", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());
		PaginationBean<MerchantDTO> paginationBean = new PaginationBean<MerchantDTO>();

		paginationBean.setCurrPage(currPage);
		merchantService.listMerchants(paginationBean);

		model.addAttribute("paginationBean", paginationBean);

		/*
		 * if(principal.getName().equals("bhuvi@mobiversa.com")) { return
		 * TEMPLATE_SUPER_AGENT; }
		 */
		return TEMPLATE_DEFAULT;
	}

	/**
	 * Display merchant search result
	 * <p>
	 * wireframe - 03a.01b
	 * </P>
	 * 
	 * // * @param model // * @param string entered by user in the search bar at the
	 * top "search // * for a company/ merchant" // * @return
	 */
	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String displaySearchMerchant(final Model model,

			@RequestParam(required = false, defaultValue = "1") final int currPage, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam("type") final String type) {
		logger.info("about to search Merchant based on search String:: " + date);

		logger.info("about to search Merchant based on search String:: " + date1);
		// String type=null;

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("Search Merchant", "merchant/merchantList", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();

		paginationBean.setCurrPage(currPage);

		merchantService.listMerchantSearch(paginationBean, fromDate, toDate, type);
		
		logger.info("userrolle: " + paginationBean.getItemList());
		model.addAttribute("pageBean", pageBean);
		
		PaginationBean<MerchantDTO> merchantDto = new PaginationBean<MerchantDTO>();
		
		List<MerchantDTO> merchantDtoList = merchantService.getMerchantDtoDataFromMerchantData(paginationBean.getItemList());
			
		merchantDto.setItemList(merchantDtoList);
		
		merchantDto.getItemList().forEach(item -> logger.info("item : "+item.getCreatedBy()+" id : "+item.getId()));

		//model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("paginationBean", merchantDto);

		return TEMPLATE_DEFAULT;
	}

	@SuppressWarnings("nls")
	@RequestMapping(value = { "/detail/{id}" }, method = RequestMethod.GET)
	public String listMerchant(final Model model, @PathVariable final long id,
			@RequestParam(value = "manualSettlement", required = false) String manualSettlement,
			final java.security.Principal principal) {
		logger.info("Request to display merchant based on ID: " + id);

		PageBean pageBean = new PageBean("Merchant Detail", "merchant/merchantDetail", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		logger.info("Display Admin Login Name: " + principal.getName());
		Merchant merchant = this.merchantService.loadMerchantByPk(id);
		logger.info("Display Merchant Name:" + merchant.getBusinessName() + " ID: " + id);
		MerchantInfo merchantInfo = this.merchantService.loadMerchantInfoByFk(String.valueOf(id));
		MobileUser mobileUser1 = this.merchantService.loadBankByMerchantFk(merchant);
		logger.info("Async payout status: " + ((mobileUser1 != null && mobileUser1.getEnableAsyncPayout() != null)
				? mobileUser1.getEnableAsyncPayout()
				: "No"));

		String id1 = Long.toString(merchant.getId());

		logger.info("Merchant Type:" + merchant.getMerchantType());

		String adminusername = principal.getName();
		logger.info("Display Admin Login Name: " + adminusername);

		logger.info("Enable Foreign Card:" + merchant.getForeignCard());

		logger.info("Paydee MID :");
		logger.info("mid : " + merchant.getMid().getMid());
		logger.info("ezymotomid : " + merchant.getMid().getMotoMid());
		logger.info("ezypassmid : " + merchant.getMid().getEzypassMid());
		logger.info("ezywaymid  : " + merchant.getMid().getEzywayMid());
		logger.info("ezyrecmid  : " + merchant.getMid().getEzyrecMid());

		logger.info("UMobile MID :");
		logger.info("umMid : " + merchant.getMid().getUmMid());
		logger.info("umMotoMid : " + merchant.getMid().getUmMotoMid());
		logger.info("umEzypassMid : " + merchant.getMid().getUmEzypassMid());
		logger.info("umEzywayMid  : " + merchant.getMid().getUmEzywayMid());
		logger.info("umEzyrecMid  : " + merchant.getMid().getUmEzyrecMid());
		
		
		logger.info("fiuuMid  : " + merchant.getMid().getFiuuMid());

		logger.info("Modified by :" + merchant.getModifiedBy());
		logger.info("**** UMobile username :" + adminusername.toLowerCase());

		if (merchant.getMerchantType() == null || merchant.getMerchantType().equals("P")) {
			if ((merchant.getAuth3DS() != null) && (merchant.getAuth3DS().equals("Yes"))) {
				merchant.setRemarks(merchant.getMid().getMotoMid());
				merchant.setTradingName("");
			} else {
				merchant.setRemarks("");
				merchant.setTradingName(merchant.getMid().getMotoMid());
			}
		} else if (merchant.getMerchantType().equals("U")) {

			if ((merchant.getAuth3DS() != null) && (merchant.getAuth3DS().equals("Yes"))) {
				merchant.setReferralId(merchant.getMid().getUmMotoMid());
				merchant.setNatureOfBusiness("");
			} else {
				merchant.setReferralId("");
				merchant.setNatureOfBusiness(merchant.getMid().getUmMotoMid());
			}

		}else if (merchant.getMerchantType().equals("FIUU")) {

			if ((merchant.getAuth3DS() != null) && (merchant.getAuth3DS().equals("Yes"))) {
				merchant.setReferralId(merchant.getMid().getFiuuMid());
				merchant.setNatureOfBusiness("");
			} else {
				merchant.setReferralId("");
				merchant.setNatureOfBusiness(merchant.getMid().getFiuuMid());
			}
		}

		logger.info("Auto Settled: " + merchant.getAutoSettled() + " Pre Auth: " + merchant.getPreAuth());
		logger.info("Auth 3DS: " + merchant.getAuth3DS());

		model.addAttribute("isPayoutAsyncEnabled",
				((mobileUser1 != null && mobileUser1.getEnableAsyncPayout() != null)
						? mobileUser1.getEnableAsyncPayout()
						: "No"));
		model.addAttribute("isMaxPayoutLimitSet",
				((mobileUser1 != null && mobileUser1.getPayoutTransactionLimit() != null)
						&& mobileUser1.getPayoutTransactionLimit().compareTo(BigDecimal.ZERO) > 0) ? "Yes" : "No");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("adminusername", adminusername);
		model.addAttribute("manualSettlement", manualSettlement);
		logger.info("addAttribute manualSettlement is" + manualSettlement);
		model.addAttribute("merchantInfo", Optional.ofNullable(merchantInfo).orElse(new MerchantInfo()));
		return this.TEMPLATE_DEFAULT;
	}

	// @RequestMapping(value = { "/AdminMerchantProfile/changePassword/{id}" },
	// method = RequestMethod.GET)
	@RequestMapping(value = { "/ChangePasswordByAdmin/{id}" }, method = RequestMethod.GET)
	public String ChangeMerchantPasswordByAdmin(final Model model, @PathVariable final long id,
			final java.security.Principal principal, HttpServletRequest request) {

		// String pwd=null;
		Merchant merchant = merchantService.loadMerchantByPk(id);
		// logger.info("admin merchant new password
		// "+merchantService.generatePassword());
		logger.info("admin  merchant email uname /ChangePasswordByAdmin " + merchant.getUsername());
		String pwd = null;
		try {
			pwd = merchantService.changeMerchantPassWordByAdmin(merchant);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("display merchant name:" + merchant.getUsername() + "first name: " + merchant.getFirstName());
		PageBean pageBean = new PageBean("Merchant Detail", "home/AdminMerchantchangePwd", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		if (pwd != null) {

			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(merchant.getUsername(), principal.getName(),
					"resetPassword");
			if (auditTrail.getUsername() != null) {
				logger.info("Merchant :" + auditTrail.getUsername() + " Password Successfully Reseted by Admin: "
						+ auditTrail.getModifiedBy());
				;
			}
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("email", merchant.getEmail());
//		model.addAttribute("username", merchant.getUsername());
		model.addAttribute("userName", principal.getName());
		model.addAttribute("businessName", merchant.getBusinessName());
		model.addAttribute("password", pwd);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/MerchantChangePassword" }, method = RequestMethod.GET)
	public String uploadTermsandConditons(final Model model,
			/* @ModelAttribute("merchantuser") final MerchtCustMail regMobileUser, */
			final java.security.Principal principal, HttpServletRequest request) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("Merchant", "merchant/ChangePassword/MerchantDetails", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());

		List<Merchant> merchant = merchantService.loadMerchant();
		List<Merchant> merchant1 = merchantService.loadMerchant();

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("merchant1", merchant1);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/MerchantDetailsToChangePassword" }, method = RequestMethod.GET)
	public String dispTermsandConditons(final Model model, @RequestParam("mid") Long id, /*
																							 * @ModelAttribute(
																							 * "merchantuser") final
																							 * MerchtCustMail
																							 * regMobileUser,
																							 */
			final java.security.Principal principal) {
		logger.info("/MerchantDetailsToChangePassword: " + id);

		Merchant merchant = merchantService.loadMerchantByPk(id);

		PageBean pageBean = new PageBean("Merchant", "merchant/ChangePassword/MerchantDetails", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());

		List<Merchant> merchant1 = merchantService.loadMerchant();
		List<MobileUser> mobileuser = promotionService.loadMobileUserDetails(merchant.getId());

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("merchant", merchant);
		model.addAttribute("merchant1", merchant1);

		model.addAttribute("mobileuser", mobileuser);

		return TEMPLATE_DEFAULT;
	}

	/*
	 * @RequestMapping(value = { "/ChangeMerchPass" }, method = RequestMethod.GET)
	 * public String MerchantChangePasswordByAdmin(final Model model, final
	 * java.security.Principal principal, HttpServletRequest
	 * request, @RequestParam("id") Long id, // @RequestParam("mid") String mid,
	 * // @RequestParam("motoMid") String motoMid,
	 * 
	 * @RequestParam("merchantPass") String
	 * merchantPass, @RequestParam("businessName") String businessName) {
	 * 
	 * logger.info("check merchant id : " + id); // logger.info("check mid : " +
	 * mid); Merchant merchant = merchantService.loadMerchantbyid(id); //
	 * logger.info("admin merchant new password //
	 * "+merchantService.generatePassword()); //
	 * logger.info("admin merchant uname "+merchant.getUsername());
	 * merchant.setPassword(merchantPass); //
	 * logger.info("merchant getpass: "+merchant.getPassword()); merchant =
	 * merchantService.changeMerchantPassWordByAdminManualy(merchant);
	 * logger.info("display merchant name:" + merchant.getUsername() +
	 * "first name: " + merchant.getFirstName());
	 * 
	 * if (merchant != null) {
	 * 
	 * AuditTrail auditTrail =
	 * adminService.updateAuditTrailByAdmin(merchant.getUsername(),
	 * principal.getName(), "resetPassword"); if (auditTrail.getUsername() != null)
	 * { logger.info("Merchant :" + auditTrail.getUsername() +
	 * " Password Successfully Reseted by Admin: " + auditTrail.getModifiedBy()); ;
	 * } }
	 * 
	 * PageBean pageBean = new PageBean("Merchant",
	 * "merchant/ChangePassword/PasswordChangedSuccess", Module.MERCHANT,
	 * "merchant/sideMenuMerchant"); model.addAttribute("msg",
	 * "Merchant Password ReChanged Successfully by Admin '" + principal.getName() +
	 * "'"); // model.addAttribute("mid", mid); model.addAttribute("merchant",
	 * merchant); model.addAttribute("pageBean", pageBean);
	 * model.addAttribute("newPassword", merchantPass); return TEMPLATE_DEFAULT; }
	 */

	// san

	@RequestMapping(value = { "/ChangeMerchPass" }, method = RequestMethod.POST)
	public String MerchantChangePasswordByAdmin(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("id") Long id,
			// @RequestParam("mid") String mid,
			// @RequestParam("motoMid") String motoMid,
			@RequestParam("merchantPass") String merchantPass, @RequestParam("businessName") String businessName) {
		System.out.println("innnnnnnnnnn " + businessName);

		logger.info("check merchant id : " + id);
		// logger.info("check mid : " + mid);
		Merchant merchant = merchantService.loadMerchantbyid(id);
		// logger.info("admin merchant new password
		// "+merchantService.generatePassword());
		// logger.info("admin merchant uname "+merchant.getUsername());
		merchant.setPassword(merchantPass);
		// logger.info("merchant getpass: "+merchant.getPassword());
		merchant = merchantService.changeMerchantPassWordByAdminManualy(merchant);
		logger.info("display merchant name:" + merchant.getUsername() + "first name: " + merchant.getFirstName());

		if (merchant != null) {

			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(merchant.getUsername(), principal.getName(),
					"resetPassword");
			if (auditTrail.getUsername() != null) {
				logger.info("Merchant :" + auditTrail.getUsername() + " Password Successfully Reseted by Admin: "
						+ auditTrail.getModifiedBy());
				;
			}
		}

		PageBean pageBean = new PageBean("Merchant", "merchant/ChangePassword/PasswordChangedSuccess", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("msg", "Merchant Password ReChanged Successfully by Admin '" + principal.getName() + "'");
		// model.addAttribute("mid", mid);
		model.addAttribute("merchant", merchant);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("newPassword", merchantPass);
		return TEMPLATE_DEFAULT;
	}

	/*
	 * @RequestMapping(value = { "/ChangeMobileuserPass" }, method =
	 * RequestMethod.GET) public String MobileuserChangePasswordByAdmin(final Model
	 * model, final java.security.Principal principal, HttpServletRequest
	 * request, @RequestParam("merchant_Id") Long merchant_Id,
	 * 
	 * @RequestParam("mobileuserPass") String mobileuserPass,
	 * 
	 * @RequestParam("username") String username
	 * 
	 * @RequestParam("mobileUserId") Long id) {
	 * logger.info("check : /ChangeMobileuserPass");
	 * 
	 * String mid=null;
	 * 
	 * logger.info(merchant_Id + " : " + id); Merchant merchant =
	 * merchantService.loadMerchantbyid(merchant_Id);
	 * 
	 * if(merchant.getMid().getMid()!=null) { mid=merchant.getMid().getMid(); }else
	 * if(merchant.getMid().getMotoMid()!=null) {
	 * mid=merchant.getMid().getMotoMid(); }else {
	 * mid=merchant.getMid().getEzypassMid(); }
	 * 
	 * logger.info(" merchant username " + merchant.getUsername());
	 * 
	 * MobileUser mobileuser = mobileUserService.loadMobileUserByPk(id);
	 * logger.info("mobileuser name: " + mobileuser.getUsername());
	 * mobileuser.setPassword(mobileuserPass);
	 * 
	 * mobileuser = merchantService
	 * .changeMobileuserPassWordByAdminManualy(mobileuser);
	 * 
	 * if (mobileuser != null) {
	 * 
	 * AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(
	 * merchant.getUsername(), principal.getName(), "resetPassword"); if
	 * (auditTrail.getUsername() != null) { logger.info("Merchant :" +
	 * auditTrail.getUsername() + " Password Successfully Reseted by Admin: " +
	 * auditTrail.getModifiedBy()); ; } }
	 * 
	 * PageBean pageBean = new PageBean("Merchant",
	 * "merchant/ChangePassword/PasswordChangedSuccess", Module.MERCHANT,
	 * "merchant/sideMenuMerchant"); model.addAttribute("msg",
	 * "MobileUser Password Changed Successfully by Admin " + principal.getName());
	 * model.addAttribute("mid", mid); model.addAttribute("mobileUserName",
	 * mobileuser.getUsername()); model.addAttribute("pageBean", pageBean);
	 * model.addAttribute("newPassword", mobileuserPass); return TEMPLATE_DEFAULT; }
	 * 
	 */

	// san

	@RequestMapping(value = { "/ChangeMobileuserPass" }, method = RequestMethod.POST)
	public String MobileuserChangePasswordByAdmin(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("merchant_Id") Long merchant_Id,
			@RequestParam("mobileuserPass") String mobileuserPass,
			/* @RequestParam("username") String username */
			@RequestParam("mobileUserId") Long id) {
		logger.info("check : /ChangeMobileuserPass");

		String mid = null;

		logger.info(merchant_Id + " : " + id);
		Merchant merchant = merchantService.loadMerchantbyid(merchant_Id);

		if (merchant.getMid().getMid() != null) {
			mid = merchant.getMid().getMid();
		} else if (merchant.getMid().getMotoMid() != null) {
			mid = merchant.getMid().getMotoMid();
		} else {
			mid = merchant.getMid().getEzypassMid();
		}

		logger.info(" merchant username " + merchant.getUsername());

		MobileUser mobileuser = mobileUserService.loadMobileUserByPk(id);
		logger.info("mobileuser name: " + mobileuser.getUsername());
		mobileuser.setPassword(mobileuserPass);

		mobileuser = merchantService.changeMobileuserPassWordByAdminManualy(mobileuser);

		if (mobileuser != null) {

			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(merchant.getUsername(), principal.getName(),
					"resetPassword");
			if (auditTrail.getUsername() != null) {
				logger.info("Merchant :" + auditTrail.getUsername() + " Password Successfully Reseted by Admin: "
						+ auditTrail.getModifiedBy());
				;
			}
		}

		PageBean pageBean = new PageBean("Merchant", "merchant/ChangePassword/PasswordChangedSuccess", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("msg", "MobileUser Password Changed Successfully by Admin " + principal.getName());
		model.addAttribute("mid", mid);
		model.addAttribute("mobileUserName", mobileuser.getUsername());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("newPassword", mobileuserPass);
		return TEMPLATE_DEFAULT;
	}

	/**
	 * Suspend Merchant
	 * <p>
	 * wireframe 03a.02a
	 * </p>
	 * 
	 * @param model
	 * @param id    PK ID of merchant
	 * @return
	 */
	@RequestMapping(value = { "/suspend/{id}" }, method = RequestMethod.GET)
	public String displaySuspendMerchant(final Model model, @PathVariable final Long id,
			@ModelAttribute("merchantStatusHistory") final MerchantStatusHistory merchantStatusHistory,
			@ModelAttribute("errorList") final ArrayList<String> errorList) {
		logger.info("Request to suspend merchant based on ID: " + id);
		PageBean pageBean = new PageBean("Merchant Detail", "merchant/suspend/merchantSuspend", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		pageBean.addJS("merchant/suspend/merchantSuspend.js");
		Merchant merchant = merchantService.loadMerchantByPk(id);
		if (!CommonStatus.ACTIVE.equals(merchant.getStatus())) {
			// merchant status isn't active, then how do we suspend merchant?
			return URL_BASE + "/detail/" + id;
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("now", new Date());
		model.addAttribute("merchantStatusHistory", merchantStatusHistory);
		model.addAttribute("errorList", errorList);

		return TEMPLATE_DEFAULT;
	}

	@ModelAttribute("reasonList")
	public List<String> populateReasonList() {
		List<String> reasonList = new ArrayList<String>();
		reasonList.add("Fraud Case Merchant");
		reasonList.add("Payment Delay");
		reasonList.add("Fake Registration");
		reasonList.add("Credit Card Fraud");
		reasonList.add("Other");
		return reasonList;
	}

	/**
	 * Suspend Merchant
	 * <p>
	 * wireframe 03a.02a
	 * </p>
	 * 
	 * @param model
	 * @param id    PK ID of merchant
	 * @return
	 */
	@RequestMapping(value = { "/suspend/doSuspend" }, method = RequestMethod.POST)
	public String doSuspendMerchant(final Model model, @RequestParam final Long id, @RequestParam final String reason,
			@RequestParam final String description, final RedirectAttributes redirectAttributes) {
		logger.info("Request to dosuspend merchant based on ID: " + id);
		try {
			// FIXME load error message from resource bundle
			ArrayList<String> errorList = new ArrayList<String>();
			if (org.apache.commons.lang.StringUtils.isBlank(reason)) {
				errorList.add("Reason field is required");
			} else if (reason.length() > 250) {
				errorList.add("Reason field length exceeded 250 characters");
			}

			if (org.apache.commons.lang.StringUtils.isBlank(description)) {
				errorList.add("Description field is required");
			} else if (description.length() > 250) {
				errorList.add("Description field length exceeded 250 characters");
			}

			if (errorList.size() > 0) {
				MerchantStatusHistory merchantStatusHistory = new MerchantStatusHistory();
				merchantStatusHistory.setReason(reason);
				merchantStatusHistory.setDescription(description);

				redirectAttributes.addFlashAttribute("merchantStatusHistory", merchantStatusHistory);
				redirectAttributes.addFlashAttribute("errorList", errorList);

				return "redirect:" + URL_BASE + "/suspend/" + id;
			}

			merchantService.doSuspendMerchant(id, reason, description);
			// return "redirect:" + URL_BASE + "/suspendDone?mode=success&id=" +
			// id;
			return "redirect:" + URL_BASE + "/list/1";

		} catch (Exception e) {
			// FIXME log event to db and return eventID to controller for
			// reporting purpose.
			logger.error("error suspending merchant", e);
			// return "redirect:" + URL_BASE + "/suspendDone?mode=fail&id=" + id
			// + "&eventID=1";
			return "redirect:" + URL_BASE + "/list/1";
		}
	}

	@RequestMapping(value = { "/suspendDone" }, method = RequestMethod.GET)
	public String displaySuspendMerchantDone(final Model model,

			@RequestParam(required = false) final String mode, @RequestParam final long id,
			@RequestParam(required = false) final Long eventID) {
		logger.info("Request to suspend Done merchant based on ID: " + id);
		PageBean pageBean = new PageBean("Merchant Suspend Done", "merchant/suspend/merchantSuspendDone",
				Module.MERCHANT, "merchant/sideMenuMerchant");
		boolean success = ("success".equals(mode));
		Merchant merchant = merchantService.loadMerchantByPk(id);
		MerchantStatusHistory merchantStatusHistory = merchantService.loadMerchantStatusHistoryByPk(merchant);

		model.addAttribute("merchant", merchant);
		model.addAttribute("merchantStatusHistory", merchantStatusHistory);
		model.addAttribute("pageBean", pageBean);

		model.addAttribute("success", success);
		model.addAttribute("eventID", eventID);

		// return TEMPLATE_DEFAULT;
		return "redirect:" + URL_BASE + "/list/1";
	}

	/**
	 * UnSuspend Merchant
	 * <p>
	 * wireframe 03a.02a
	 * </p>
	 * 
	 * @param model
	 * @param id    PK ID of merchant
	 * @return
	 */
	@RequestMapping(value = { "/unsuspend/{id}" }, method = RequestMethod.GET)
	public String displayUnSuspendMerchant(final Model model, @PathVariable final Long id,
			@ModelAttribute("merchantStatusHistory") final MerchantStatusHistory merchantStatusHistory,
			@ModelAttribute("errorList") final ArrayList<String> errorList) {
		logger.info("Request to unsuspend merchant based on ID: " + id);
		PageBean pageBean = new PageBean("Merchant Detail", "merchant/suspend/merchantUnSuspend", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		pageBean.addJS("merchant/suspend/merchantSuspend.js");
		Merchant merchant = merchantService.loadMerchantByPk(id);
		if (!CommonStatus.SUSPENDED.equals(merchant.getStatus())) {
			// merchant status isn't active, then how do we suspend merchant?
			return URL_BASE + "merchant/detail/" + id;
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("now", new Date());
		model.addAttribute("merchantStatusHistory", merchantStatusHistory);
		model.addAttribute("errorList", errorList);

		// FIXME load reasonList from resourceBundle
		// logger.warn("FIXME:: load reasonList from resourceBundle");
		/*
		 * model.addAttribute("reasonList", new String[] { "Fraud Case Investigation",
		 * "Others" });
		 */

		return TEMPLATE_DEFAULT;
	}

	/**
	 * Suspend Merchant
	 * <p>
	 * wireframe 03a.02a
	 * </p>
	 * 
	 * @param model
	 * @param id    PK ID of merchant
	 * @return
	 */
	@RequestMapping(value = { "/unsuspend/dounSuspend" }, method = RequestMethod.POST)
	public String doUnSuspendMerchant(final Model model, @RequestParam final Long id, @RequestParam final String reason,
			@RequestParam final String description, final RedirectAttributes redirectAttributes) {
		try {
			// FIXME load error message from resource bundle
			ArrayList<String> errorList = new ArrayList<String>();
			if (org.apache.commons.lang.StringUtils.isBlank(reason)) {
				errorList.add("Reason field is required");
			} else if (reason.length() > 250) {
				errorList.add("Reason field length exceeded 250 characters");
			}

			if (org.apache.commons.lang.StringUtils.isBlank(description)) {
				errorList.add("Description field is required");
			} else if (description.length() > 250) {
				errorList.add("Description field length exceeded 250 characters");
			}

			if (errorList.size() > 0) {
				MerchantStatusHistory merchantStatusHistory = new MerchantStatusHistory();
				merchantStatusHistory.setReason(reason);
				merchantStatusHistory.setDescription(description);

				redirectAttributes.addFlashAttribute("merchantStatusHistory", merchantStatusHistory);
				redirectAttributes.addFlashAttribute("errorList", errorList);

				return "redirect:" + URL_BASE + "/unsuspend/" + id;
			}

			merchantService.doUnSuspendMerchant(id, reason, description);
			// return "redirect:" + URL_BASE + "/unsuspendDone?mode=success&id="
			// + id;
			return "redirect:" + URL_BASE + "/list/1";

		} catch (Exception e) {
			// FIXME log event to db and return eventID to controller for
			// reporting purpose.
			logger.error("error suspending merchant", e);
			return "redirect:" + URL_BASE + "/unsuspendDone?mode=fail&id=" + id + "&eventID=1";
		}
	}

	@RequestMapping(value = { "/unsuspendDone" }, method = RequestMethod.GET)
	public String displayUnSuspendMerchantDone(final Model model, @RequestParam final String mode,
			@RequestParam final long id, @RequestParam(required = false) final Long eventID) {
		PageBean pageBean = new PageBean("Merchant Un-Suspend Done", "merchant/suspend/merchantUnSuspendDone",
				Module.MERCHANT, "merchant/sideMenuMerchant");
		boolean success = ("success".equals(mode));
		Merchant merchant = merchantService.loadMerchantByPk(id);
		MerchantStatusHistory merchantStatusHistory = merchantService.loadMerchantStatusHistoryByPk(merchant);

		model.addAttribute("merchant", merchant);
		model.addAttribute("pageBean", pageBean);

		model.addAttribute("success", success);
		model.addAttribute("eventID", eventID);
		model.addAttribute("merchantStatusHistory", merchantStatusHistory);

		return TEMPLATE_DEFAULT;
	}

	/**
	 * Display edit merchant page
	 * <p>
	 * wireframe 03a.01a.02
	 * </p>
	 * 
	 * // * @param model // * @param PK id of a merchant // * @return
	 */

	// *** Add merchant code started
	@RequestMapping(value = { "/addMerchant" }, method = RequestMethod.GET)
	public String displayAddMerchant(@ModelAttribute("merchant") final RegAddMerchant regAddMerchant,
			final HttpServletRequest request, Model model, final java.security.Principal principal) {

		logger.info("About to Add merchant details");
		logger.info(" Admin login person Name:" + principal.getName());

		List<Agent> agent1 = agentService.loadAgent();
		logger.info("Number of Agents:" + agent1.size());
		Set<String> agentNameList = new HashSet<String>();

		for (Agent t : agent1) {
			String firstName = t.getFirstName();
			String mailId = t.getUsername();
			agentNameList.add("AGENT~" + firstName.toString() + "~" + mailId);
		}

		List<SubAgent> subAgent = subAgentService.loadSubAgent();
		logger.info("Number of Sub-Agents:" + agent1.size());

		for (SubAgent t : subAgent) {
			String firstName = t.getName();
			String mailId = t.getMailId();
			agentNameList.add("SUBAGENT~" + firstName.toString() + "~" + mailId);
		}

		PageBean pageBean = new PageBean("Merchant Detail", "merchant/addmerchant/addMerchantDetails", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);

		String err = (String) request.getSession(true).getAttribute("addErSession");
		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {

				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("editErSession");
			}
		}

		model.addAttribute("agentNameList", agentNameList);
		model.addAttribute("stateList", AddMerchantOptionList.stateList());
		model.addAttribute("documentsList", AddMerchantOptionList.documentsList());
		model.addAttribute("CategoryList", AddMerchantOptionList.CategoryList());
		model.addAttribute("natureOfBusinessList", AddMerchantOptionList.natureOfBusinessList());
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/addMerchant" }, method = RequestMethod.POST)
	public String processAddUserForm(@ModelAttribute("merchant") final RegAddMerchant regAddMerchant,
			final HttpServletRequest request, final ModelMap model, final BindingResult errors,
			final Principal principal) {

		logger.info("Submitted to Add Merchant Details");
		logger.info("Admin login person Name:" + principal.getName());
		PageBean pageBean = null;

		List<Agent> agent1 = agentService.loadAgent();
		logger.info("Number of Agents:" + agent1.size());
		Set<String> agentNameList = new HashSet<String>();

		for (Agent t : agent1) {
			String firstName = t.getFirstName();
			String mailId = t.getUsername();
			agentNameList.add("AGENT~" + firstName.toString() + "~" + mailId);
		}

		List<SubAgent> subAgent = subAgentService.loadSubAgent();
		logger.info("Number of Sub-Agents:" + subAgent.size());
		for (SubAgent t : subAgent) {
			String firstName = t.getName();
			String mailId = t.getMailId();
			agentNameList.add("SUBAGENT~" + firstName.toString() + "~" + mailId);
		}

		// Paydee
		String ezywireMid = null, ezymotoMid = null, ezypassMid = null, ezywayMid = null, ezyrecMid = null;

		// UMobile
		String um_Mid = null, um_MotoMid = null, um_ezypassMid = null, um_ezywayMid = null, um_ezyrecMid = null;

		String EmptyMid = null;
		String responseDataMidExist = null;
		String responseDataMotomidExist = null;
		String responseDataEzypassmidExist = null;
		String responseDataEzyRecmidExist = null;
		String responseDataEzyWaymidExist = null;
		String responseDataOfficeEmail = null;

		String responseDataUM_MidExist = null;
		String responseDataUM_MotomidExist = null;
		String responseDataUM_EzypassmidExist = null;
		String responseDataUM_EzyRecmidExist = null;
		String responseDataUM_EzyWaymidExist = null;
//			String responseDataUM_OfficeEmail = null;

		logger.info("Merchant Type : " + regAddMerchant.getMerchantType());
		logger.info("VCC  : " + regAddMerchant.getVcc());
		logger.info("Pre-Auth  : " + regAddMerchant.getPreAuth());
		logger.info("Auto Settled  : " + regAddMerchant.getAutoSettled());
		logger.info("OTP  : " + regAddMerchant.getAuth3DS());

		if (regAddMerchant.getMerchantType() != null) {
			if (regAddMerchant.getMerchantType().equals("U") || regAddMerchant.getMerchantType() == "U") {
				logger.info("Merchant Type setted as U ");
				regAddMerchant.setMerchantType("U");
			} else if (regAddMerchant.getMerchantType().equals("P") || regAddMerchant.getMerchantType() == "P") {
				logger.info("Merchant Type setted as P");
				regAddMerchant.setMerchantType("P");
			}
		} else {
			logger.info("Merchant Type Empty");
		}

		if (regAddMerchant.getMid() == null && regAddMerchant.getEzymotomid() == null
				&& regAddMerchant.getEzypassmid() == null && regAddMerchant.getEzywaymid() == null
				&& regAddMerchant.getEzyrecmid() == null && regAddMerchant.getUmEzypassMid() == null
				&& regAddMerchant.getUmEzyrecMid() == null && regAddMerchant.getUmEzywayMid() == null
				&& regAddMerchant.getUmMid() == null && regAddMerchant.getUmMotoMid() == null) {

			EmptyMid = "Empty Mid Fields";

		}

		logger.info("Official Mail  : " + regAddMerchant.getOfficeEmail());

		Merchant offEmail = merchantService.loadMerchantbyEmail(regAddMerchant.getOfficeEmail());

		responseDataOfficeEmail = offEmail != null ? " Office Email already exist" : null;

		logger.info("Office Email:::::" + offEmail + ":  " + responseDataOfficeEmail);

		logger.info("Paydee MID :");
		logger.info("mid : " + regAddMerchant.getMid());
		logger.info("ezymotomid : " + regAddMerchant.getEzymotomid());
		logger.info("ezypassmid : " + regAddMerchant.getEzypassmid());
		logger.info("ezywaymid  : " + regAddMerchant.getEzywaymid());
		logger.info("ezyrecmid  : " + regAddMerchant.getEzyrecmid());

		if (regAddMerchant.getMid() != null) {
			if (!(regAddMerchant.getMid().isEmpty())) {
				ezywireMid = regAddMerchant.getMid();
				if (regAddMerchant.getMid().length() <= 15) {
					for (int i = ezywireMid.length(); i < 15; i++) {
						ezywireMid = "0" + ezywireMid;

					}
					logger.info("In ezywireMid: " + ezywireMid);
					responseDataMidExist = merchantService.checkExistMid(ezywireMid) != null ? "MID already Exist"
							: null;

				}
			}
		}

		if (regAddMerchant.getEzymotomid() != null) {
			if (!(regAddMerchant.getEzymotomid().isEmpty())) {
				ezymotoMid = regAddMerchant.getEzymotomid();
				if (regAddMerchant.getEzymotomid().length() <= 15) {
					for (int i = ezymotoMid.length(); i < 15; i++) {
						ezymotoMid = "0" + ezymotoMid;

					}
					logger.info("In ezymotoMid: " + ezymotoMid);
					responseDataMotomidExist = merchantService.checkExistMid(ezymotoMid) != null ? "MID already Exist"
							: null;

				}
			}
		}

		if (regAddMerchant.getEzypassmid() != null) {
			if (!(regAddMerchant.getEzypassmid().isEmpty())) {
				ezypassMid = regAddMerchant.getEzypassmid();
				if (regAddMerchant.getEzypassmid().length() <= 15) {
					for (int i = ezypassMid.length(); i < 15; i++) {
						ezypassMid = "0" + ezypassMid;

					}
					logger.info("In ezypassMid: " + ezypassMid);
					responseDataEzypassmidExist = merchantService.checkExistMid(ezypassMid) != null
							? "MID already Exist"
							: null;

				}
			}
		}
		if (regAddMerchant.getEzywaymid() != null) {
			if (!(regAddMerchant.getEzywaymid().isEmpty())) {
				ezywayMid = regAddMerchant.getEzywaymid();
				if (regAddMerchant.getEzywaymid().length() <= 15) {
					for (int i = ezywayMid.length(); i < 15; i++) {
						ezywayMid = "0" + ezywayMid;

					}
					logger.info("In ezywayMid: " + ezywayMid);
					responseDataEzyWaymidExist = merchantService.checkExistMid(ezywayMid) != null ? "MID already Exist"
							: null;

				}
			}
		}

		if (regAddMerchant.getEzyrecmid() != null) {
			if (!(regAddMerchant.getEzyrecmid().isEmpty())) {
				ezyrecMid = regAddMerchant.getEzyrecmid();
				if (regAddMerchant.getEzyrecmid().length() <= 15) {
					for (int i = ezyrecMid.length(); i < 15; i++) {
						ezyrecMid = "0" + ezyrecMid;

					}
					logger.info("In ezyrecMid: " + ezyrecMid);
					responseDataEzyRecmidExist = merchantService.checkExistMid(ezyrecMid) != null ? "MID already Exist"
							: null;

				}
			}
		}

		logger.info("UMobile MID :");
		logger.info("umMid : " + regAddMerchant.getUmMid());
		logger.info("umMotoMid : " + regAddMerchant.getUmMotoMid());
		logger.info("umEzypassMid : " + regAddMerchant.getUmEzypassMid());
		logger.info("umEzywayMid  : " + regAddMerchant.getUmEzywayMid());
		logger.info("umEzyrecMid  : " + regAddMerchant.getUmEzyrecMid());

		if (regAddMerchant.getUmMid() != null) {
			if (!(regAddMerchant.getUmMid().isEmpty())) {
				um_Mid = regAddMerchant.getUmMid();
				if (regAddMerchant.getUmMid().length() <= 15) {
					for (int i = um_Mid.length(); i < 15; i++) {
						um_Mid = "0" + um_Mid;

					}
					logger.info("In um_Mid: " + um_Mid);
					responseDataUM_MidExist = merchantService.checkExistMid(um_Mid) != null ? "MID already Exist"
							: null;

				}
			}
		}

		if (regAddMerchant.getUmMotoMid() != null) {
			if (!(regAddMerchant.getUmMotoMid().isEmpty())) {
				um_MotoMid = regAddMerchant.getUmMotoMid();
				if (regAddMerchant.getUmMotoMid().length() <= 15) {
					for (int i = um_MotoMid.length(); i < 15; i++) {
						um_MotoMid = "0" + um_MotoMid;

					}
					logger.info("In um_MotoMid: " + um_MotoMid);
					responseDataUM_MotomidExist = merchantService.checkExistMid(um_MotoMid) != null
							? "MID already Exist"
							: null;

				}
			}
		}

		if (regAddMerchant.getUmEzypassMid() != null) {
			if (!(regAddMerchant.getUmEzypassMid().isEmpty())) {
				um_ezypassMid = regAddMerchant.getUmEzypassMid();
				if (regAddMerchant.getUmEzypassMid().length() <= 15) {
					for (int i = um_ezypassMid.length(); i < 15; i++) {
						um_ezypassMid = "0" + um_ezypassMid;

					}
					logger.info("In um_ezypassMid: " + um_ezypassMid);
					responseDataUM_EzypassmidExist = merchantService.checkExistMid(um_ezypassMid) != null
							? "MID already Exist"
							: null;

				}
			}
		}

		if (regAddMerchant.getUmEzyrecMid() != null) {
			if (!(regAddMerchant.getUmEzyrecMid().isEmpty())) {
				um_ezyrecMid = regAddMerchant.getUmEzyrecMid();
				if (regAddMerchant.getUmEzyrecMid().length() <= 15) {
					for (int i = um_ezyrecMid.length(); i < 15; i++) {
						um_ezyrecMid = "0" + um_ezyrecMid;

					}
					logger.info("In um_ezyrecMid: " + um_ezyrecMid);
					responseDataUM_EzyRecmidExist = merchantService.checkExistMid(um_ezyrecMid) != null
							? "MID already Exist"
							: null;

				}
			}
		}

		if (regAddMerchant.getUmEzywayMid() != null) {
			if (!(regAddMerchant.getUmEzywayMid().isEmpty())) {
				um_ezywayMid = regAddMerchant.getUmEzywayMid();
				if (regAddMerchant.getUmEzywayMid().length() <= 15) {
					for (int i = um_ezywayMid.length(); i < 15; i++) {
						um_ezywayMid = "0" + um_ezywayMid;

					}
					logger.info("In um_ezywayMid: " + um_ezywayMid);

					responseDataUM_EzyWaymidExist = merchantService.checkExistMid(um_ezywayMid) != null
							? "MID already Exist"
							: null;

				}
			}
		}

		BaseDataImpl baseData = new BaseDataImpl();

		RegAddMerchant a = baseData.vaildated(regAddMerchant);

		// Security purpose for virus theft
		String responseDataError = null;
		if (a != null) {
			logger.info("Contains HTML tags");
			responseDataError = a != null ? "Contains HTML tags" : null;
		}

		logger.info("responseDataOfficeEmail  : " + responseDataOfficeEmail);

		logger.info("responseDataMidExist  : " + responseDataMidExist);
		logger.info("responseDataMotomidExist  : " + responseDataMotomidExist);
		logger.info("responseDataEzyWaymidExist  : " + responseDataEzyWaymidExist);
		logger.info("responseDataEzyRecmidExist  : " + responseDataEzyRecmidExist);
		logger.info("responseDataEzypassmidExist  : " + responseDataEzypassmidExist);

		logger.info("responseDataUM_MidExist  : " + responseDataUM_MidExist);
		logger.info("responseDataUM_MotomidExist  : " + responseDataUM_MotomidExist);
		logger.info("responseDataUM_EzyRecmidExist  : " + responseDataUM_EzyRecmidExist);
		logger.info("responseDataUM_EzypassmidExist  : " + responseDataUM_EzypassmidExist);
		logger.info("responseDataUM_EzyWaymidExist  : " + responseDataUM_EzyWaymidExist);

		logger.info("responseDataError  : " + responseDataError);

		logger.info("EmptyMid  : " + EmptyMid);

		if (responseDataMidExist != null || responseDataMotomidExist != null || responseDataEzyWaymidExist != null
				|| responseDataEzyRecmidExist != null || responseDataEzypassmidExist != null
				|| responseDataOfficeEmail != null || responseDataUM_MidExist != null
				|| responseDataUM_MotomidExist != null || responseDataUM_EzyRecmidExist != null
				|| responseDataUM_EzypassmidExist != null || responseDataUM_EzyWaymidExist != null || EmptyMid != null
				|| responseDataError != null) {

			pageBean = new PageBean(" Mid add Details", "merchant/addmerchant/addMerchantDetails", Module.MERCHANT,
					"merchant/sideMenuMerchant");
			model.addAttribute("responseDataMidExist", responseDataMidExist);// table
			model.addAttribute("responseDataMotomidExist", responseDataMotomidExist);
			model.addAttribute("responseDataEzyWaymidExist", responseDataEzyWaymidExist);
			model.addAttribute("responseDataEzyRecmidExist", responseDataEzyRecmidExist);
			model.addAttribute("responseDataEzypassmidExist", responseDataEzypassmidExist);
			model.addAttribute("responseDataError", responseDataError);

			model.addAttribute("responseDataUM_MidExist", responseDataUM_MidExist);// table
			model.addAttribute("responseDataUM_MotomidExist", responseDataUM_MotomidExist);
			model.addAttribute("responseDataUM_EzyWaymidExist", responseDataUM_EzyWaymidExist);
			model.addAttribute("responseDataUM_EzyRecmidExist", responseDataUM_EzyRecmidExist);
			model.addAttribute("responseDataUM_EzypassmidExist", responseDataUM_EzypassmidExist);

			model.addAttribute("responseDataOfficeEmail", responseDataOfficeEmail);
			model.addAttribute("EmptyMid", EmptyMid);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("regAddMerchant", regAddMerchant);

			model.addAttribute("agentNameList", agentNameList);
			model.addAttribute("stateList", AddMerchantOptionList.stateList());
			model.addAttribute("documentsList", AddMerchantOptionList.documentsList());
			model.addAttribute("CategoryList", AddMerchantOptionList.CategoryList());
			model.addAttribute("natureOfBusinessList", AddMerchantOptionList.natureOfBusinessList());

			return TEMPLATE_DEFAULT;

		}

		String fileIds = null;

		// Start of form file

		if (!regAddMerchant.getFormFile().isEmpty()) {
			FileOutputStream fos = null;
			BufferedOutputStream stream = null;
			byte[] bytes = null;
			String dirData = null;
			String fileData = null;
			try {
				bytes = regAddMerchant.getFormFile().getBytes();
				Date fileid = new Date();
				String fileId = new SimpleDateFormat("yyyyMMddhhmmss").format(fileid);

				String adminName = principal.getName();
				Date dt = new Date();
				String date = new SimpleDateFormat("yyyyMMdd").format(dt);
				String rootPath = PropertyLoader.getFile().getProperty("ADMINPATH");
				// File dir = new File(rootPath + File.separator + adminName+
				// File.separator+ regAddMerchant.getBusinessName()+
				// File.separator+date);
				dirData = rootPath + File.separator + regAddMerchant.getBusinessName() + File.separator + date;
				File dir = new File(dirData);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				fileData = dir.getAbsolutePath() + File.separator + regAddMerchant.getFormFile().getOriginalFilename();
				File serverFile = new File(fileData);
				fos = new FileOutputStream(serverFile);
				stream = new BufferedOutputStream(fos);
				stream.write(bytes);
				stream.close();

				FileUpload fileUpload = new FileUpload();

				fileUpload.setFileId(fileId);
				fileUpload.setFileName(regAddMerchant.getFormFile().getOriginalFilename());
				fileUpload.setFilePath(serverFile.getAbsolutePath());
				fileUpload.setMerchantId("");
				fileUpload.setMerchantName(regAddMerchant.getBusinessName());
				fileUpload.setCreatedBy(adminName);
				fileUpload.setCreatedDate(new Date());

				FileUpload fileUpload1 = merchantService.storeFileUpload(fileUpload);

				regAddMerchant.setFormFName(regAddMerchant.getFormFile().getOriginalFilename());

				/*
				 * logger.info("Server File Location=" +
				 * regAddMerchant.getFormFile().getContentType());
				 * logger.info("Server File Location=" +
				 * regAddMerchant.getFormFile().getOriginalFilename());
				 * logger.info("Server File Location=" + serverFile.getAbsolutePath());
				 * logger.info("Server File Location=" + fileUpload1.getId());
				 */

				fileIds = fileUpload1.getId().toString();

				// return "You successfully uploaded file=" +
				// regAddMerchant.getFile().getName();
			} catch (Exception e) {
				// return "You failed to upload " +
				// regAddMerchant.getFile().getName() + " => " + e.getMessage();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (Exception e) {
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (Exception e) {
					}
				}
			}
		}

		// end Form File

		// start doc File
		if (!regAddMerchant.getDocFile().isEmpty()) {
			FileOutputStream fos = null;
			BufferedOutputStream stream = null;
			byte[] bytes = null;
			String dirData = null;
			String fileData = null;
			try {
				bytes = regAddMerchant.getDocFile().getBytes();

				Date fileid = new Date();
				String fileId = new SimpleDateFormat("yyyyMMddhhmmss").format(fileid);

				String adminName = principal.getName();
				Date dt = new Date();
				String date = new SimpleDateFormat("yyyyMMdd").format(dt);
				String rootPath = PropertyLoader.getFile().getProperty("ADMINPATH");
				// File dir = new File(rootPath + File.separator + adminName+
				// File.separator+ regAddMerchant.getBusinessName()+
				// File.separator+date);
				dirData = rootPath + File.separator + regAddMerchant.getBusinessName() + File.separator + date;
				File dir = new File(dirData);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				fileData = dir.getAbsolutePath() + File.separator + regAddMerchant.getDocFile().getOriginalFilename();
				File serverFile = new File(fileData);
				fos = new FileOutputStream(serverFile);
				stream = new BufferedOutputStream(fos);
				stream.write(bytes);
				stream.close();

				FileUpload fileUpload = new FileUpload();

				fileUpload.setFileId(fileId);
				fileUpload.setFileName(regAddMerchant.getDocFile().getOriginalFilename());
				fileUpload.setFilePath(serverFile.getAbsolutePath());
				fileUpload.setMerchantId("");
				fileUpload.setMerchantName(regAddMerchant.getBusinessName());
				fileUpload.setCreatedBy(adminName);
				fileUpload.setCreatedDate(new Date());

				FileUpload fileUpload1 = merchantService.storeFileUpload(fileUpload);

				regAddMerchant.setDocFName(regAddMerchant.getDocFile().getOriginalFilename());

				/*
				 * logger.info("Server File Location=" +
				 * regAddMerchant.getDocFile().getContentType());
				 * logger.info("Server File Location=" +
				 * regAddMerchant.getDocFile().getOriginalFilename());
				 * logger.info("Server File Location=" + serverFile.getAbsolutePath());
				 * logger.info("Server File Location=" + fileUpload1.getId());
				 */

				// regAddMerchant.setFileId(fileUpload1.getId().toString());

				if (fileIds != null) {

					fileIds = fileIds + "~" + fileUpload1.getId().toString();
				} else {
					fileIds = fileUpload1.getId().toString();
				}

				// return "You successfully uploaded file=" +
				// regAddMerchant.getFile().getName();
			} catch (Exception e) {
				// return "You failed to upload " +
				// regAddMerchant.getFile().getName() + " => " + e.getMessage();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (Exception e) {
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (Exception e) {
					}
				}
			}
		} /*
			 * else { return "You failed to upload " + regAddMerchant.getFile().getName() +
			 * " because the file was empty."; }
			 */

		// end doc file

		// start pay File
		if (!regAddMerchant.getPayFile().isEmpty()) {
			FileOutputStream fos = null;
			BufferedOutputStream stream = null;
			byte[] bytes = null;
			String dirData = null;
			String fileData = null;
			try {
				bytes = regAddMerchant.getPayFile().getBytes();

				Date fileid = new Date();
				String fileId = new SimpleDateFormat("yyyyMMddhhmmss").format(fileid);

				String adminName = principal.getName();
				Date dt = new Date();
				String date = new SimpleDateFormat("yyyyMMdd").format(dt);
				String rootPath = PropertyLoader.getFile().getProperty("ADMINPATH");
				// File dir = new File(rootPath + File.separator + adminName+
				// File.separator+ regAddMerchant.getBusinessName()+
				// File.separator+date);
				dirData = rootPath + File.separator + regAddMerchant.getBusinessName() + File.separator + date;
				File dir = new File(dirData);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				fileData = dir.getAbsolutePath() + File.separator + regAddMerchant.getPayFile().getOriginalFilename();
				File serverFile = new File(fileData);
				fos = new FileOutputStream(serverFile);
				stream = new BufferedOutputStream(fos);
				stream.write(bytes);
				stream.close();

				FileUpload fileUpload = new FileUpload();

				fileUpload.setFileId(fileId);
				fileUpload.setFileName(regAddMerchant.getPayFile().getOriginalFilename());
				fileUpload.setFilePath(serverFile.getAbsolutePath());
				fileUpload.setMerchantId("");
				fileUpload.setMerchantName(regAddMerchant.getBusinessName());
				fileUpload.setCreatedBy(adminName);
				fileUpload.setCreatedDate(new Date());

				FileUpload fileUpload1 = merchantService.storeFileUpload(fileUpload);

				regAddMerchant.setPayFName(regAddMerchant.getPayFile().getOriginalFilename());

				/*
				 * logger.info("Server File Location=" +
				 * regAddMerchant.getPayFile().getContentType());
				 * logger.info("Server File Location=" +
				 * regAddMerchant.getPayFile().getOriginalFilename());
				 * logger.info("Server File Location=" + serverFile.getAbsolutePath());
				 * logger.info("Server File Location=" + fileUpload1.getId());
				 */
				// regAddMerchant.setFileId(fileUpload1.getId().toString());
				if (fileIds != null) {

					fileIds = fileIds + "~" + fileUpload1.getId().toString();
				} else {
					fileIds = fileUpload1.getId().toString();
				}
				// return "You successfully uploaded file=" +
				// regAddMerchant.getFile().getName();
			} catch (Exception e) {
				// return "You failed to upload " +
				// regAddMerchant.getFile().getName() + " => " + e.getMessage();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (Exception e) {
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (Exception e) {
					}
				}
			}
		} /*
			 * else { return "You failed to upload " + regAddMerchant.getFile().getName() +
			 * " because the file was empty."; }
			 */
		// end pay file

		regAddMerchant.setFileId(fileIds);

		// regAddMerchant.setMerchantLogo(imgRead.getBytes());

		// regAddMerchant.setMerchantLogo(imgRead);
		// logger.info("check merchant logo:" +
		// regAddMerchant.getMerchantLogo());

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", regAddMerchant);
		// model.addAttribute("regAddMerchant", regAddMerchant);
		model.addAttribute("agent");
		// PCI
		request.getSession(true).setAttribute("addMerchantSession", regAddMerchant);

		/*
		 * logger.info("mid: " + regAddMerchant.getMid() + " motomid: " +
		 * regAddMerchant.getEzymotomid() + " ezypassmid: " +
		 * regAddMerchant.getEzypassmid());
		 */

		// return TEMPLATE_DEFAULT;
		return "redirect:/admmerchant/merchantDetailsReviewAndConfirm";
	}

	@RequestMapping(value = { "/merchantDetailsReviewAndConfirm" }, method = RequestMethod.GET)
	public String displayAddUserConfirmation(final ModelMap model, final HttpServletRequest request,
			final RegAddMerchant regAddMerchant) {

		logger.info("About to Add Merchant Details  , View Review And Confirm");

		RegAddMerchant merchant = (RegAddMerchant) request.getSession(true).getAttribute("addMerchantSession");
		/*
		 * logger.info("/merchantDetailsReviewAndConfirm"); logger.info("mid: " +
		 * merchant.getMid() + "motomid: " + merchant.getEzymotomid() + " ezypassmid: "
		 * + merchant.getEzypassmid());
		 */
		if (merchant == null) {
			// redirect user to the add page if there's no merchant in session.
			// return "redirect:/merchant/addMerchant";
			return "redirect:" + URL_BASE + "/list/1";

		}
		PageBean pageBean = new PageBean("Merchant user add Details",
				"merchant/addmerchant/addMerchantReviewandConfirm", Module.MERCHANT, "merchant/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/merchantDetailsReviewAndConfirm" }, method = RequestMethod.POST)
	public String confirmAddMerchant(@ModelAttribute("merchant") final Merchant merchant, final Model model,
			final RegAddMerchant regAddMerchant, final java.security.Principal principal,
			final HttpServletRequest request) {

		PageBean pageBean = new PageBean("Succefully New Merchant Added",
				"merchant/addmerchant/addMerchantAlldoneSuccessful", Module.MERCHANT, "merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);

		logger.info("About to Add Merchant Details  , Submit Review And Confirm");
		logger.info("Merchant will Add by :" + principal.getName());

		RegAddMerchant MerchantSavedInHttpSession = (RegAddMerchant) request.getSession(true)
				.getAttribute("addMerchantSession");
		if (MerchantSavedInHttpSession == null) {
			return "redirect:/admmerchant/addMerchant";
		}

		BaseDataImpl baseData = new BaseDataImpl();
		RegAddMerchant a = baseData.vaildated(MerchantSavedInHttpSession);

		String responseDataError = null;
		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("addErSession", "yes");
			return "redirect:/admmerchant/addMerchant";
		}

		logger.info("Business Name :" + MerchantSavedInHttpSession.getBusinessName());

		model.addAttribute(MerchantSavedInHttpSession);
		RegAddMerchant regAddmerchant = null;
		try {
			logger.info("System in control to  add new merchant:");
			regAddmerchant = merchantService.addMerchant(MerchantSavedInHttpSession);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (regAddmerchant != null) {
			logger.info("About to LOG in Audit trail:");
			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(regAddmerchant.getOfficeEmail(),
					principal.getName(), "addMerchant");

			if (auditTrail.getUsername() != null) {
				logger.info("Merchant :" + auditTrail.getUsername() + " Successfully Added by Admin: "
						+ auditTrail.getModifiedBy());
			}
		}

		model.addAttribute("merchant", MerchantSavedInHttpSession);
		request.getSession(true).removeAttribute("addMerchantSession");
		return TEMPLATE_DEFAULT;
	}

	/**
	 * Display merchant edit successful
	 * 
	 * // * @param model // * @param
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditMerchantReview(final Model model, final HttpServletRequest request,
			@RequestParam("manualSettlement") final String manualSettlement, final java.security.Principal principal) {
		logger.info("about to edit Merchant Details ReviewAndConfirm");
		RegAddMerchant merchant = (RegAddMerchant) request.getSession(true).getAttribute("editMerchantSession");
		MerchantInfo merchantInfo = (MerchantInfo) request.getSession(true).getAttribute("editMerchantInfoSession");
		logger.info("about to edit Merchant Details ReviewAndConfirm : " + merchant.getMerchantType());

		if (merchant == null) {
			// redirect user to the add page if there's no bankUser in session.

			return "redirect:" + URL_BASE + "/editMerchant/1";
			/* return "redirect:" + URL_BASE + "/editMerchant/"; */
			// return "redirect:" + URL_BASE + "/list/1";
			// return "redirect:/merchant/editMerchant/1";
			/* return TEMPLATE_DEFAULT; */
			// return "redirect:/merchant/confirmEditMerchantDetails";
		}

		String adminusername = principal.getName();
		logger.info("Display Admin Login Name: " + adminusername);
		logger.info("moto id: " + merchant.getEzymotomid());
		logger.info("mid: " + merchant.getMid());
		logger.info("moto id: " + merchant.getEzypassmid());
		logger.info("fiuu id: " + merchant.getFiuuMid());
		logger.info("foreign card is : " + merchant.getForeignCard() + " username is " + merchant.getUsername());

		logger.info("Ezysettle : " + merchant.getEzysettle());

		PageBean pageBean = new PageBean("Merchant Edit Review Detail", "merchant/edit/merchantreviewandconfirm",
				Module.MERCHANT, "merchant/sideMenuMerchant");
		/*
		 * PageBean pageBean = new PageBean("Merchant Edit Review Detail",
		 * "merchant/edit/merchantreviewandconfirm", Module.MERCHANT,
		 * "merchant/sideMenuMerchant");
		 */
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("merchantInfo", merchantInfo != null ? merchantInfo : new MerchantInfo());
		model.addAttribute("adminusername", adminusername);
		model.addAttribute("manualSettlement", manualSettlement);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.POST)
	public String confirmEditMerchant(final Model model, final HttpServletRequest request,
			@RequestParam("manualSettlement") final String manualSettlement,
			@RequestParam("accountEnquiryEnabled") final String accountEnquiryEnabled,
			@RequestParam("quickPayoutEnabled") final String quickPayoutEnabled,
			/*@RequestParam("quickPayoutUrl") final String quickPayoutUrl,*/
			final java.security.Principal principal) {
		logger.info("about to edit Merchant Details Confirms");
		PageBean pageBean = new PageBean("Successfully Merchant edited", "merchant/edit/merchantalldonesuccessful",
				Module.MERCHANT, "merchant/edit/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);
		RegAddMerchant merchantSavedInHttpSession = (RegAddMerchant) request.getSession(true)
				.getAttribute("editMerchantSession");
		MerchantInfo merchantInfoSavedInHttpSession = (MerchantInfo) request.getSession(true)
				.getAttribute("editMerchantInfoSession");

		if (merchantSavedInHttpSession == null || merchantInfoSavedInHttpSession == null) {
			return "redirect:/admmerchant/editReviewandConfirm";
		}

		BaseDataImpl baseData = new BaseDataImpl();

		RegAddMerchant a = baseData.vaildated(merchantSavedInHttpSession);

		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");

			return "redirect:/admmerchant/edit/" + merchantSavedInHttpSession.getId() + "";
		}

		model.addAttribute("merchant", merchantSavedInHttpSession);
		model.addAttribute("merchantInfo", merchantInfoSavedInHttpSession);
		Merchant merchant = merchantService.loadMerchantByPk(Long.parseLong(merchantSavedInHttpSession.getId()));

		logger.info("login person name:," + principal.getName() + "merchant name: " + merchant.getUsername());

		String mail = "NO";

		logger.info("Ezysettle ::" + merchantSavedInHttpSession.getEzysettle());

		// saravanakumar
		logger.info("Foreigncared ::" + merchantSavedInHttpSession.getForeignCard() + "User name: : "
				+ merchantSavedInHttpSession.getUsername());
		String Foreigncard = merchantSavedInHttpSession.getForeignCard();
		String Bname = merchantSavedInHttpSession.getUsername();

		String Ezysettleapi = merchantSavedInHttpSession.getEzysettle();
		// String Bname=merchantSavedInHttpSession.getBusinessName();

		Merchant curmerchant = merchantService.updateMerchants(merchantSavedInHttpSession, merchant, mail,manualSettlement,merchantInfoSavedInHttpSession,accountEnquiryEnabled,quickPayoutEnabled/*,quickPayoutUrl*/);

		UpdateApi foreigncard1 = new UpdateApi();
		// DtlApi foreigncard1=new DtlApi();
		foreigncard1.updateForeigncard(Foreigncard, Bname);

		if (curmerchant.getUsername() != null) {

			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(merchant.getUsername(), principal.getName(),
					"editMerchant");
			if (auditTrail.getUsername() != null) {
				logger.info("Merchant :" + auditTrail.getUsername() + " Successfully Edited by Admin: "
						+ auditTrail.getModifiedBy());
				;
			}
		}
		request.getSession(true).removeAttribute("editMerchantSession");
		model.addAttribute("manualSettlement", manualSettlement);

		return TEMPLATE_DEFAULT;
		// return "redirect:" + URL_BASE + "/list/1";
	}

	// changed code
	/**
	 * Display merchant edit successful
	 * 
	 * // * @param model // * @param // * @return
	 */
	@RequestMapping(value = { "/editUsuccessful" }, method = RequestMethod.POST)
	public String displayEditMerchantUnsuccessful(final Model model) {
		PageBean pageBean = new PageBean("Merchant Detail", "merchant/merchantEditUnuccessful", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);

		throw new UnsupportedOperationException("to be implemented");
	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView getExcel(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false) String export, @RequestParam("type") String type) {
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

		merchantService.listMerchantSearch(paginationBean, date, date1, type);
		// List<MobileUser>
		// mobileuser=mobileUser.loadMobileUserByFk(merchant.getId());

		// List<Merchant> list
		// =merchantService.merchantSummaryExport(date,date1);

		List<Merchant> list = paginationBean.getItemList();
		logger.info("check mobile user from date:" + date);
		logger.info("Item count: " + paginationBean.getItemList().size());
		for (Merchant m1 : list) {

			if (m1.getAgID() != null) {
				Agent agent = agentService.loadAgentByIdPk(m1.getAgID().longValue());
				if (agent != null) {
					logger.info("agent name" + agent.getUsername());
					m1.setRemarks(agent.getUsername());
				} else {
					m1.setRemarks("");
				}
			} else {
				m1.setRemarks("");
			}
			MID m = m1.getMid();
			if (m != null) {
				// if(m.getMid()==null && m.getMid().isEmpty()){
				if (m.getMid() == null) {
					// logger.info("no mid");
					m.setMid("-");

				} else {

					m.setMid(m.getMid());
				}
				if (m.getMotoMid() != null) {
					m.setMotoMid(m.getMotoMid());
					// logger.info("moto mid "+m.getMotoMid());
				} else {
					m.setMotoMid("-");
				}
				if (m.getEzywayMid() != null) {
					m.setEzywayMid(m.getEzywayMid());

				} else {
					m.setEzywayMid("-");
				}
				if (m.getEzyrecMid() != null) {
					m.setEzyrecMid(m.getEzyrecMid());

				} else {
					m.setEzyrecMid("-");
				}

				if (m.getUmMid() != null) {
					m.setUmMid(m.getUmMid());

				} else {
					m.setUmMid("-");
				}

				if (m.getUmMotoMid() != null) {
					m.setUmMotoMid(m.getUmMotoMid());

				} else {
					m.setUmMotoMid("-");
				}

				if (m.getUmEzywayMid() != null) {
					m.setUmEzywayMid(m.getUmEzywayMid());

				} else {
					m.setUmEzywayMid("-");
				}

				if (m.getUmEzyrecMid() != null) {
					m.setUmEzyrecMid(m.getUmEzyrecMid());

				} else {
					m.setUmEzyrecMid("-");
				}

				if (m.getBoostMid() != null) {
					m.setBoostMid(m.getBoostMid());

				} else {
					m.setBoostMid("-");
				}
				if (m.getGrabMid() != null) {
					m.setGrabMid(m.getGrabMid());

				} else {
					m.setGrabMid("-");
				}
				if (m.getFpxMid() != null) {
					m.setFpxMid(m.getFpxMid());

				} else {
					m.setFpxMid("-");
				}
				if (m.getTngMid() != null) {
					m.setTngMid(m.getTngMid());

				} else {
					m.setTngMid("-");
				}
				if (m.getShoppyMid() != null) {
					m.setShoppyMid(m.getShoppyMid());

				} else {
					m.setShoppyMid("-");
				}

				if (m.getBnplMid() != null) {
					m.setBnplMid(m.getBnplMid());

				} else {
					m.setBnplMid("-");
				}
				
//				fiuu mid
				
				if (m.getFiuuMid() != null) {
					m.setFiuuMid(m.getFiuuMid());

				} else {
					m.setFiuuMid("-");
				}
				
				m1.setMid(m);
				// logger.info(" mid: "+m1.getMid().getMid()+" motomid:
				// "+m1.getMid().getMotoMid());//+" ezypassmid: "+m1.getMid().getEzypassMid());
			}

			// logger.info(" mid: "+m1.getMid().getMid()+" motomid:
			// "+m1.getMid().getMotoMid());//+" ezypassmid: "+m1.getMid().getEzypassMid());

			if (m1.getRole().toString().equals("BANK_MERCHANT")) {
				if (m1.getPreAuth() != null && m1.getPreAuth().equals("Yes")) {
					m1.setPreAuth("Yes");
				} else {
					m1.setPreAuth("No");
				}
				// logger.info("MobileUser : " + m1.getId());
				m1.setAutoSettled(mobileUser.loadMobileUserByFkBoost(m1.getId()));
				m1.setFaxNo(mobileUser.loadMobileUserByFkMoto(m1.getId()));
			} else {
				m1.setAutoSettled("No");
				m1.setFaxNo("No");
				m1.setPreAuth("No");
			}

		}

		// logger.info("check mobile user to date:" + date1);
		// System.out.println("export test:" + export);
		if (!(export.equals("PDF"))) {

			return new ModelAndView(" ", "txnList", list);

		} else {
			return new ModelAndView("txnMerchantPdf", "txnList", list);
		}
	}

//	@RequestMapping(value = { "/merdashBoard" }, method = RequestMethod.GET)
//	public String webMerchantDashBoard(final Model model, final java.security.Principal principal,
//			HttpServletRequest request) {
//
//		logger.info("Merchant Dashboard: " + principal.getName());
//		HttpSession session = request.getSession();
//		String myName = (String) session.getAttribute("userName");
//
//		logger.info("Login Person in dash Board in admincontroller:" + principal.getName());
//		// Merchant merchant =
//		// merchantService.loadMerchant(principal.getName());
//		Merchant merchant = merchantService.loadMerchant(myName);
//
//		String merchanttype = merchant.getMerchantType();
//		
//		// We used MODIFIED_BY field for checking Ezysettle is enable or not
//		
//				String checkIfSettle = merchant.getModifiedBy();
//					logger.info("CheckIfEzySettle" + checkIfSettle);
//
//		String checkname = PropertyLoad.getFile().getProperty("DISABLE_EZYSETTLE");
//		if (merchant.getUsername().equalsIgnoreCase(checkname)) {
//			PageBean pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee", Module.HOME_WEB);
//			model.addAttribute("pageBean", pageBean);
//		} else {
//
//			if (merchanttype == null || checkIfSettle == null) {
//				logger.info("inside P merchanttype is NULL");
//				PageBean pageBean = new PageBean("Dash Board", "dashboard/merchantdashbrdpaydee", Module.MERCHANT,
//						"admin/sideMenuBankUser");
//				model.addAttribute("pageBean", pageBean);
//			} else if (checkIfSettle.equalsIgnoreCase("No")) {
//				logger.info("inside P merchanttype is P");
//				PageBean pageBean = new PageBean("Dash Board", "dashboard/merchantdashbrdpaydee", Module.MERCHANT,
//						"admin/sideMenuBankUser");
//				model.addAttribute("pageBean", pageBean);
//
//			} else if (checkIfSettle.equalsIgnoreCase("Yes")) {
//
//				logger.info("inside U merchanttype is U");
//				PageBean pageBean = new PageBean("Dash Board", "dashboard/merchantdashbrd", Module.MERCHANT,
//						"admin/sideMenuBankUser");
//				model.addAttribute("pageBean", pageBean);
//			}
//
//		}
//
//		session.setAttribute("merchantUserName", merchant.getFirstName());
//		logger.info("id: " + merchant.getId());
//		if (merchant.getPreAuth() != null) {
//			session.setAttribute("preAuth", merchant.getPreAuth());
//		} else {
//			session.setAttribute("preAuth", "No");
//		}
//
//		// for api key table start
//		PaginationBean<ReaderList> paginationBean2 = new PaginationBean<ReaderList>();
//		List<ReaderList> readerdata = new ArrayList<ReaderList>();
//		readerdata = readerService.getReaderList(merchant);
//		logger.info("after Reader Size : " + readerdata.size());
//		paginationBean2.setItemList(readerdata);
//		model.addAttribute("paginationBean2", paginationBean2);
//		// for api key table end
//
//		List<MobileUser> mobileuser = mobileUser.loadMobileUserByFk(merchant.getId());
//
//		Set<String> enableBoost = new HashSet<String>();
//		for (MobileUser mu : mobileuser) {
//			String enableBoost1 = mu.getEnableBoost();
//			if (enableBoost1 != null) {
//				logger.info("enableBoost1: " + enableBoost1);
//				enableBoost.add(enableBoost1.toString());
//
//			}
//
//		}
//		if (enableBoost.contains("Yes")) {
//
//			session.setAttribute("enableBoost", "Yes");
//		} else {
//			session.setAttribute("enableBoost", "No");
//		}
//
//		Set<String> enableMoto = new HashSet<String>();
//		for (MobileUser mu : mobileuser) {
//			String enableMoto1 = mu.getEnableMoto();
//			if (enableMoto1 != null) {
//				logger.info("enableMoto: " + enableMoto1);
//				enableMoto.add(enableMoto1.toString());
//			}
//
//		}
//		if (enableMoto.contains("Yes")) {
//			session.setAttribute("enableMoto", "Yes");
//		} else {
//			session.setAttribute("enableMoto", "No");
//		}
//
//		logger.info("display merchant web " + merchant.getMid().getMid());
//		String ezyLinkCheck = "NO";
//		if (merchant.getAuth3DS() != null) {
//
//			if (merchant.getAuth3DS().equalsIgnoreCase("Yes")) {
//				ezyLinkCheck = "YES";
//			} else {
//				ezyLinkCheck = "NO";
//
//			}
//
//		}
//
//		// Check if the Merchant is Enable PreAuth or Not
//		if (merchant.getPreAuth() == null) {
//			merchant.setPreAuth("No");
//		}
//		String ezyAuthEnable = "No";
//
//		if (merchant.getMid().getMotoMid() != null
//				&& (merchant.getPreAuth().equals("Yes") || merchant.getPreAuth() == "Yes")) {
//			ezyAuthEnable = "Yes";
//		} else if (merchant.getMid().getUmMotoMid() != null
//				&& (merchant.getPreAuth().equals("Yes") || merchant.getPreAuth() == "Yes")) {
//			ezyAuthEnable = "Yes";
//		}
//		if (ezyAuthEnable.equalsIgnoreCase("Yes")) {
//
//			session.setAttribute("ezyAuthEnable", "Yes");
//
//		} else {
//			session.setAttribute("ezyAuthEnable", "No");
//
//		}
//
//		logger.info("ezyAuthEnable: " + ezyAuthEnable);
//
//		String EzypodCheck = "NO";
//		MID mid = merchantService.loadMidByMerchant_PK(String.valueOf(merchant.getId()));
//		if (mid.getEzyrecMid() != null) {
//
//			TerminalDetails termDetails = merchantService.loadTerminalDetailsByMid(mid.getEzyrecMid());
//
//			if ((termDetails.getDeviceType() == "EZYPOD") || (termDetails.getDeviceType().equals("EZYPOD"))) {
//				EzypodCheck = "YES";
//				session.setAttribute("ezyPodCheck", "Yes");
//			}
//		}
//		
//		String typename = PropertyLoad.getFile().getProperty("MERCHANT_TYPE");
//
//		if (merchanttype != null && merchanttype.equalsIgnoreCase(typename)) {
//
//			// Early Settlement Start on 31-01-22
//
//			Calendar now = Calendar.getInstance();
//			Locale malaysianLocale = new Locale("ms", "MY");
//			String pattern1 = "yyyy-MM-dd";
//			String pattern2 = "HH:mm:ss";
//
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern1);
//			SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat(pattern2);
//
//			TimeZone malaysianTimeZone = TimeZone.getTimeZone("Asia/Kuala_Lumpur");
//			simpleDateFormat.setTimeZone(malaysianTimeZone);
//			simpleDateFormat2.setTimeZone(malaysianTimeZone);
//
//			logger.info("Current Malaysia Date  = " + simpleDateFormat.format(now.getTime()));
//			logger.info("Current Malaysia Time = " + simpleDateFormat2.format(now.getTime()));
//
//			String Currentdate = simpleDateFormat.format(now.getTime());
//			String CurrentTime = simpleDateFormat2.format(now.getTime());
//
//			// Service - To Check Holiday Given 5 dates
//
//			List<String> finalSettlementDate = transactionService.tocheckholiday(Currentdate, CurrentTime);
//
//			String settlementdateone = finalSettlementDate.get(0);
//			String settlementdatetwo = finalSettlementDate.get(1);
//			String settlementdatethree = finalSettlementDate.get(2);
//			String settlementdatefour = finalSettlementDate.get(3);
//			String settlementdatefive = finalSettlementDate.get(4);
//
//			String Fsettlementdateone = null;
//			String Fsettlementdatetwo = null;
//			String Fsettlementdatethree = null;
//			String Fsettlementdatefour = null;
//			String Fsettlementdatefive = null;
//
//			try {
//				Fsettlementdateone = new SimpleDateFormat("dd-MMM-yyyy")
//						.format(new SimpleDateFormat("yyyy-MM-dd").parse(settlementdateone));
//				Fsettlementdatetwo = new SimpleDateFormat("dd-MMM-yyyy")
//						.format(new SimpleDateFormat("yyyy-MM-dd").parse(settlementdatetwo));
//				Fsettlementdatethree = new SimpleDateFormat("dd-MMM-yyyy")
//						.format(new SimpleDateFormat("yyyy-MM-dd").parse(settlementdatethree));
//				Fsettlementdatefour = new SimpleDateFormat("dd-MMM-yyyy")
//						.format(new SimpleDateFormat("yyyy-MM-dd").parse(settlementdatefour));
//				Fsettlementdatefive = new SimpleDateFormat("dd-MMM-yyyy")
//						.format(new SimpleDateFormat("yyyy-MM-dd").parse(settlementdatefive));
//
//			} catch (ParseException e1) {
//				e1.printStackTrace();
//			}
//
//			System.out.println(" settlementdateone   " + settlementdateone);
//			System.out.println(" settlementdatetwo   " + settlementdatetwo);
//			System.out.println(" settlementdatethree   " + settlementdatethree);
//			System.out.println(" settlementdatefour   " + settlementdatefour);
//			System.out.println(" settlementdatefive   " + settlementdatefive);
//
//			// Service - Settlement Net Amount of 5 days
//
//			if (settlementdateone != null) {
//				List<SettlementMDR> getsettlementonedatacard = transactionService
//						.loadNetAmountandsettlementdatebyCard(settlementdateone, merchant);
//				List<BoostDailyRecon> getsettlementonedataboost = transactionService
//						.loadNetAmountandsettlementdatebyBoost(settlementdateone, merchant);
//				List<GrabPayFile> getsettlementonedatagrabpay = transactionService
//						.loadNetAmountandsettlementdatebyGrabpay(settlementdateone, merchant);
//				List<FpxTransaction> getsettlementonedatafpx = transactionService
//						.loadNetAmountandsettlementdatebyFpx(settlementdateone, merchant);
//				List<EwalletTxnDetails> getsettlementonedatam1Pay = transactionService
//						.loadNetAmountandsettlementdatebym1Pay(settlementdateone, merchant);
//
//				double settledateoneNetAmountCard = 0;
//				double settledateoneNetAmountBoost = 0;
//				double settledateoneNetAmountGrabpay = 0;
//				double settledateoneNetAmountFpx = 0;
//				double settledateoneNetAmountm1Pay = 0;
//
//				String settlementdateonedb = getsettlementonedatacard.get(0).getSettlementDate();
//				logger.info(" settlementdateonedb   " + settlementdateonedb);
//				if (getsettlementonedatacard.get(0).getNetAmount() != null
//						&& !getsettlementonedatacard.get(0).getNetAmount().isEmpty()) {
//					logger.info(" 1 ");
//					logger.info(" getsettlementonedatacard.get(0).getNetAmount()   "
//							+ getsettlementonedatacard.get(0).getNetAmount());
//					settledateoneNetAmountCard = Double.parseDouble(getsettlementonedatacard.get(0).getNetAmount());
//					logger.info(" settledateoneNetAmountCard   " + settledateoneNetAmountCard);
//				}
//				if (getsettlementonedataboost.get(0).getNetAmount() != null
//						&& !getsettlementonedataboost.get(0).getNetAmount().isEmpty()) {
//					logger.info(" 2 ");
//					logger.info(" getsettlementonedataboost.get(0).getNetAmount()   "
//							+ getsettlementonedataboost.get(0).getNetAmount());
//					settledateoneNetAmountBoost = Double.parseDouble(getsettlementonedataboost.get(0).getNetAmount());
//					logger.info(" settledateoneNetAmountBoost   " + settledateoneNetAmountBoost);
//				}
//				if (getsettlementonedatagrabpay.get(0).getNetAmt() != null
//						&& !getsettlementonedatagrabpay.get(0).getNetAmt().isEmpty()) {
//					logger.info(" 3 ");
//					logger.info(" getsettlementonedatagrabpay.get(0).getNetAmt()  "
//							+ getsettlementonedatagrabpay.get(0).getNetAmt());
//					settledateoneNetAmountGrabpay = Double.parseDouble(getsettlementonedatagrabpay.get(0).getNetAmt());
//					logger.info(" settledateoneNetAmountGrabpay   " + settledateoneNetAmountGrabpay);
//				}
//				if (getsettlementonedatafpx.get(0).getPayableAmt() != null
//						&& !getsettlementonedatafpx.get(0).getPayableAmt().isEmpty()) {
//					logger.info(" 4 ");
//					logger.info(" getsettlementonedatafpx.get(0).getPayableAmt()  "
//							+ getsettlementonedatafpx.get(0).getPayableAmt());
//					settledateoneNetAmountFpx = Double.parseDouble(getsettlementonedatafpx.get(0).getPayableAmt());
//
//					logger.info(" settledateoneNetAmountFpx   " + settledateoneNetAmountFpx);
//				}
//				logger.info(" 5");
//				if (getsettlementonedatam1Pay.get(0).getPayableAmt() != null
//						&& !getsettlementonedatam1Pay.get(0).getPayableAmt().isEmpty()) {
//					logger.info(" 6 ");
//					logger.info(" getsettlementonedatam1Pay.get(0).getPayableAmt()  "
//							+ getsettlementonedatam1Pay.get(0).getPayableAmt());
//					settledateoneNetAmountm1Pay = Double.parseDouble(getsettlementonedatam1Pay.get(0).getPayableAmt());
//
//					logger.info(" settledateoneNetAmountm1Pay   " + settledateoneNetAmountm1Pay);
//				}
//				double FinalTotalsettledateoneNetAmount = settledateoneNetAmountCard + settledateoneNetAmountBoost
//						+ settledateoneNetAmountGrabpay + settledateoneNetAmountFpx + settledateoneNetAmountm1Pay;
//
//				String pattern = "#,##0.00";
//				DecimalFormat myFormatter = new DecimalFormat(pattern);
//				String TotalsettledateoneNetAmount = myFormatter.format(FinalTotalsettledateoneNetAmount);
//
//				logger.info(" TotalsettledateoneNetAmount   " + TotalsettledateoneNetAmount);
//
//				// model.addAttribute("settlementdateonedb", settlementdateonedb);
//
//				// validate cutoff time
//
//				String EzysettleCutoffTime = "17:00:00";
//
//				LocalTime CutoffTime = LocalTime.parse(EzysettleCutoffTime);
//				LocalTime LocalcurrentTime = LocalTime.parse(CurrentTime);
//
//				if (LocalcurrentTime.isBefore(CutoffTime)) {
//
//					logger.info("before cutoff time");
//					model.addAttribute("TotalsettledateoneNetAmount", TotalsettledateoneNetAmount);
//
//				} else if (LocalcurrentTime.isAfter(CutoffTime)) {
//
//					LocalDate Settledateone = LocalDate.parse(settlementdateone); // settledateone
//					LocalDate Tomorrowdate = LocalDate.now().plusDays(1);
//
//					if (Settledateone.equals(Tomorrowdate)) {
//						logger.info("After cutoff time");
//						logger.info("Settlementdateone equal");
//						String Status = "ACTIVE";
//						model.addAttribute("Status", Status);
//						model.addAttribute("TotalsettledateoneNetAmount", TotalsettledateoneNetAmount);
//					} else {
//						logger.info("After cutoff time");
//						logger.info("Settlementdateone not equal");
//						model.addAttribute("TotalsettledateoneNetAmount", TotalsettledateoneNetAmount);
//					}
//
//				}
//
//				// model.addAttribute("TotalsettledateoneNetAmount",
//				// TotalsettledateoneNetAmount);
//
//			}
//
//			if (settlementdatetwo != null) {
//				List<SettlementMDR> getsettlementtwodatacard = transactionService
//						.loadNetAmountandsettlementdatebyCard(settlementdatetwo, merchant);
//				List<BoostDailyRecon> getsettlementtwodataboost = transactionService
//						.loadNetAmountandsettlementdatebyBoost(settlementdatetwo, merchant);
//				List<GrabPayFile> getsettlementtwodatagrabpay = transactionService
//						.loadNetAmountandsettlementdatebyGrabpay(settlementdatetwo, merchant);
//				List<FpxTransaction> getsettlementtwodatafpx = transactionService
//						.loadNetAmountandsettlementdatebyFpx(settlementdatetwo, merchant);
//				List<EwalletTxnDetails> getsettlementtwodatam1Pay = transactionService
//						.loadNetAmountandsettlementdatebym1Pay(settlementdatetwo, merchant);
//
//				double settledatetwoNetAmountCard = 0;
//				double settledatetwoNetAmountBoost = 0;
//				double settledatetwoNetAmountGrabpay = 0;
//				double settledatetwoNetAmountFpx = 0;
//				double settledatetwoNetAmountm1Pay = 0;
//
//				String settlementdatetwodb = getsettlementtwodatacard.get(0).getSettlementDate();
//				logger.info(" settlementdatetwodb   " + settlementdatetwodb);
//				if (getsettlementtwodatacard.get(0).getNetAmount() != null
//						&& !getsettlementtwodatacard.get(0).getNetAmount().isEmpty()) {
//					settledatetwoNetAmountCard = Double.parseDouble(getsettlementtwodatacard.get(0).getNetAmount());
//					logger.info(" settledatetwoNetAmountCard   " + settledatetwoNetAmountCard);
//				}
//				if (getsettlementtwodataboost.get(0).getNetAmount() != null
//						&& !getsettlementtwodataboost.get(0).getNetAmount().isEmpty()) {
//					settledatetwoNetAmountBoost = Double.parseDouble(getsettlementtwodataboost.get(0).getNetAmount());
//					logger.info(" settledatetwoNetAmountBoost   " + settledatetwoNetAmountBoost);
//				}
//				if (getsettlementtwodatagrabpay.get(0).getNetAmt() != null
//						&& !getsettlementtwodatagrabpay.get(0).getNetAmt().isEmpty()) {
//					settledatetwoNetAmountGrabpay = Double.parseDouble(getsettlementtwodatagrabpay.get(0).getNetAmt());
//					logger.info(" settledatetwoNetAmountGrabpay   " + settledatetwoNetAmountGrabpay);
//				}
//				if (getsettlementtwodatafpx.get(0).getPayableAmt() != null
//						&& !getsettlementtwodatafpx.get(0).getPayableAmt().isEmpty()) {
//					settledatetwoNetAmountFpx = Double.parseDouble(getsettlementtwodatafpx.get(0).getPayableAmt());
//					logger.info(" settledatetwoNetAmountFpx   " + settledatetwoNetAmountFpx);
//				}
//				if (getsettlementtwodatam1Pay.get(0).getPayableAmt() != null
//						&& !getsettlementtwodatam1Pay.get(0).getPayableAmt().isEmpty()) {
//					settledatetwoNetAmountm1Pay = Double.parseDouble(getsettlementtwodatam1Pay.get(0).getPayableAmt());
//					logger.info(" settledatetwoNetAmountm1Pay   " + settledatetwoNetAmountm1Pay);
//				}
//
//				double FinalTotalsettledatetwoNetAmount = settledatetwoNetAmountCard + settledatetwoNetAmountBoost
//						+ settledatetwoNetAmountGrabpay + settledatetwoNetAmountFpx + settledatetwoNetAmountm1Pay;
//
//				String pattern = "#,##0.00";
//				DecimalFormat myFormatter = new DecimalFormat(pattern);
//				String TotalsettledatetwoNetAmount = myFormatter.format(FinalTotalsettledatetwoNetAmount);
//
//				logger.info(" TotalsettledatetwoNetAmount   " + TotalsettledatetwoNetAmount);
//
//				// model.addAttribute("settlementdatetwodb", settlementdatetwodb);
//				model.addAttribute("TotalsettledatetwoNetAmount", TotalsettledatetwoNetAmount);
//
//			}
//			if (settlementdatethree != null) {
//				List<SettlementMDR> getsettlementthreedatacard = transactionService
//						.loadNetAmountandsettlementdatebyCard(settlementdatethree, merchant);
//				List<BoostDailyRecon> getsettlementthreedataboost = transactionService
//						.loadNetAmountandsettlementdatebyBoost(settlementdatethree, merchant);
//				List<GrabPayFile> getsettlementthreedatagrabpay = transactionService
//						.loadNetAmountandsettlementdatebyGrabpay(settlementdatethree, merchant);
//				List<FpxTransaction> getsettlementthreedatafpx = transactionService
//						.loadNetAmountandsettlementdatebyFpx(settlementdatethree, merchant);
//				List<EwalletTxnDetails> getsettlementthreedatam1Pay = transactionService
//						.loadNetAmountandsettlementdatebym1Pay(settlementdatethree, merchant);
//
//				double settledatethreeNetAmountCard = 0;
//				double settledatethreeNetAmountBoost = 0;
//				double settledatethreeNetAmountGrabpay = 0;
//				double settledatethreeNetAmountFpx = 0;
//				double settledatethreeNetAmountm1Pay = 0;
//
//				String settlementdatethreedb = getsettlementthreedatacard.get(0).getSettlementDate();
//				logger.info(" settlementdatethreedb   " + settlementdatethreedb);
//
//				if (getsettlementthreedatacard.get(0).getNetAmount() != null
//						&& !getsettlementthreedatacard.get(0).getNetAmount().isEmpty()) {
//
//					settledatethreeNetAmountCard = Double.parseDouble(getsettlementthreedatacard.get(0).getNetAmount());
//					logger.info(" settledatethreeNetAmountCard   " + settledatethreeNetAmountCard);
//				}
//				if (getsettlementthreedataboost.get(0).getNetAmount() != null
//						&& !getsettlementthreedataboost.get(0).getNetAmount().isEmpty()) {
//
//					settledatethreeNetAmountBoost = Double
//							.parseDouble(getsettlementthreedataboost.get(0).getNetAmount());
//					logger.info(" settledatethreeNetAmountBoost   " + settledatethreeNetAmountBoost);
//				}
//				if (getsettlementthreedatagrabpay.get(0).getNetAmt() != null
//						&& !getsettlementthreedatagrabpay.get(0).getNetAmt().isEmpty()) {
//
//					settledatethreeNetAmountGrabpay = Double
//							.parseDouble(getsettlementthreedatagrabpay.get(0).getNetAmt());
//					logger.info(" settledatethreeNetAmountGrabpay   " + settledatethreeNetAmountGrabpay);
//				}
//				if (getsettlementthreedatafpx.get(0).getPayableAmt() != null
//						&& !getsettlementthreedatafpx.get(0).getPayableAmt().isEmpty()) {
//
//					settledatethreeNetAmountFpx = Double.parseDouble(getsettlementthreedatafpx.get(0).getPayableAmt());
//					logger.info(" settledatethreeNetAmountFpx   " + settledatethreeNetAmountFpx);
//				}
//				if (getsettlementthreedatam1Pay.get(0).getPayableAmt() != null
//						&& !getsettlementthreedatam1Pay.get(0).getPayableAmt().isEmpty()) {
//
//					settledatethreeNetAmountm1Pay = Double
//							.parseDouble(getsettlementthreedatam1Pay.get(0).getPayableAmt());
//					logger.info(" settledatethreeNetAmountm1Pay   " + settledatethreeNetAmountm1Pay);
//				}
//
//				double FinalTotalsettledatethreeNetAmount = settledatethreeNetAmountCard + settledatethreeNetAmountBoost
//						+ settledatethreeNetAmountGrabpay + settledatethreeNetAmountFpx + settledatethreeNetAmountm1Pay;
//
//				String pattern = "#,##0.00";
//				DecimalFormat myFormatter = new DecimalFormat(pattern);
//				String TotalsettledatethreeNetAmount = myFormatter.format(FinalTotalsettledatethreeNetAmount);
//
//				logger.info(" TotalsettledatethreeNetAmount   " + TotalsettledatethreeNetAmount);
//
//				// model.addAttribute("settlementdatethreedb", settlementdatethreedb);
//				model.addAttribute("TotalsettledatethreeNetAmount", TotalsettledatethreeNetAmount);
//
//			}
//			if (settlementdatefour != null) {
//				List<SettlementMDR> getsettlementfourdatacard = transactionService
//						.loadNetAmountandsettlementdatebyCard(settlementdatefour, merchant);
//				List<BoostDailyRecon> getsettlementfourdataboost = transactionService
//						.loadNetAmountandsettlementdatebyBoost(settlementdatefour, merchant);
//				List<GrabPayFile> getsettlementfourdatagrabpay = transactionService
//						.loadNetAmountandsettlementdatebyGrabpay(settlementdatefour, merchant);
//				List<FpxTransaction> getsettlementfourdatafpx = transactionService
//						.loadNetAmountandsettlementdatebyFpx(settlementdatefour, merchant);
//				List<EwalletTxnDetails> getsettlementfourdatam1Pay = transactionService
//						.loadNetAmountandsettlementdatebym1Pay(settlementdatefour, merchant);
//
//				double settledatefourNetAmountCard = 0;
//				double settledatefourNetAmountBoost = 0;
//				double settledatefourNetAmountGrabpay = 0;
//				double settledatefourNetAmountFpx = 0;
//				double settledatefourNetAmountm1Pay = 0;
//
//				String settlementdatefourdb = getsettlementfourdatacard.get(0).getSettlementDate();
//				logger.info(" settlementdatefourdb   " + settlementdatefourdb);
//
//				if (getsettlementfourdatacard.get(0).getNetAmount() != null
//						&& !getsettlementfourdatacard.get(0).getNetAmount().isEmpty()) {
//
//					settledatefourNetAmountCard = Double.parseDouble(getsettlementfourdatacard.get(0).getNetAmount());
//					logger.info(" settledatefourNetAmountCard   " + settledatefourNetAmountCard);
//				}
//				if (getsettlementfourdataboost.get(0).getNetAmount() != null
//						&& !getsettlementfourdataboost.get(0).getNetAmount().isEmpty()) {
//
//					settledatefourNetAmountBoost = Double.parseDouble(getsettlementfourdataboost.get(0).getNetAmount());
//					logger.info(" settledatefourNetAmountBoost   " + settledatefourNetAmountBoost);
//				}
//				if (getsettlementfourdatagrabpay.get(0).getNetAmt() != null
//						&& !getsettlementfourdatagrabpay.get(0).getNetAmt().isEmpty()) {
//
//					settledatefourNetAmountGrabpay = Double
//							.parseDouble(getsettlementfourdatagrabpay.get(0).getNetAmt());
//					logger.info(" settledatefourNetAmountGrabpay   " + settledatefourNetAmountGrabpay);
//				}
//				if (getsettlementfourdatafpx.get(0).getPayableAmt() != null
//						&& !getsettlementfourdatafpx.get(0).getPayableAmt().isEmpty()) {
//
//					settledatefourNetAmountFpx = Double.parseDouble(getsettlementfourdatafpx.get(0).getPayableAmt());
//					logger.info(" settledatefourNetAmountFpx   " + settledatefourNetAmountFpx);
//				}
//				if (getsettlementfourdatam1Pay.get(0).getPayableAmt() != null
//						&& !getsettlementfourdatam1Pay.get(0).getPayableAmt().isEmpty()) {
//
//					settledatefourNetAmountm1Pay = Double
//							.parseDouble(getsettlementfourdatam1Pay.get(0).getPayableAmt());
//					logger.info(" settledatefourNetAmountm1Pay   " + settledatefourNetAmountm1Pay);
//				}
//
//				double FinalTotalsettledatefourNetAmount = settledatefourNetAmountCard + settledatefourNetAmountBoost
//						+ settledatefourNetAmountGrabpay + settledatefourNetAmountFpx + settledatefourNetAmountm1Pay;
//
//				String pattern = "#,##0.00";
//				DecimalFormat myFormatter = new DecimalFormat(pattern);
//				String TotalsettledatefourNetAmount = myFormatter.format(FinalTotalsettledatefourNetAmount);
//
//				logger.info(" TotalsettledatefourNetAmount   " + TotalsettledatefourNetAmount);
//
//				// model.addAttribute("settlementdatefourdb", settlementdatefourdb);
//				model.addAttribute("TotalsettledatefourNetAmount", TotalsettledatefourNetAmount);
//
//			}
//			if (settlementdatefive != null) {
//				List<SettlementMDR> getsettlementfivedatacard = transactionService
//						.loadNetAmountandsettlementdatebyCard(settlementdatefive, merchant);
//				List<BoostDailyRecon> getsettlementfivedataboost = transactionService
//						.loadNetAmountandsettlementdatebyBoost(settlementdatefive, merchant);
//				List<GrabPayFile> getsettlementfivedatagrabpay = transactionService
//						.loadNetAmountandsettlementdatebyGrabpay(settlementdatefive, merchant);
//				List<FpxTransaction> getsettlementfivedatafpx = transactionService
//						.loadNetAmountandsettlementdatebyFpx(settlementdatefive, merchant);
//				List<EwalletTxnDetails> getsettlementfivedatam1Pay = transactionService
//						.loadNetAmountandsettlementdatebym1Pay(settlementdatefive, merchant);
//
//				double settledatefiveNetAmountCard = 0;
//				double settledatefiveNetAmountBoost = 0;
//				double settledatefiveNetAmountGrabpay = 0;
//				double settledatefiveNetAmountFpx = 0;
//				double settledatefiveNetAmountm1Pay = 0;
//
//				String settlementdatefivedb = getsettlementfivedatacard.get(0).getSettlementDate();
//				logger.info(" settlementdatefivedb   " + settlementdatefivedb);
//
//				if (getsettlementfivedatacard.get(0).getNetAmount() != null
//						&& !getsettlementfivedatacard.get(0).getNetAmount().isEmpty()) {
//
//					settledatefiveNetAmountCard = Double.parseDouble(getsettlementfivedatacard.get(0).getNetAmount());
//					logger.info(" settledatefiveNetAmountCard   " + settledatefiveNetAmountCard);
//				}
//				if (getsettlementfivedataboost.get(0).getNetAmount() != null
//						&& !getsettlementfivedataboost.get(0).getNetAmount().isEmpty()) {
//
//					settledatefiveNetAmountBoost = Double.parseDouble(getsettlementfivedataboost.get(0).getNetAmount());
//					logger.info(" settledatefiveNetAmountBoost   " + settledatefiveNetAmountBoost);
//				}
//
//				if (getsettlementfivedatagrabpay.get(0).getNetAmt() != null
//						&& !getsettlementfivedatagrabpay.get(0).getNetAmt().isEmpty()) {
//
//					settledatefiveNetAmountGrabpay = Double
//							.parseDouble(getsettlementfivedatagrabpay.get(0).getNetAmt());
//					logger.info(" settledatefiveNetAmountGrabpay   " + settledatefiveNetAmountGrabpay);
//				}
//				if (getsettlementfivedatafpx.get(0).getPayableAmt() != null
//						&& !getsettlementfivedatafpx.get(0).getPayableAmt().isEmpty()) {
//
//					settledatefiveNetAmountFpx = Double.parseDouble(getsettlementfivedatafpx.get(0).getPayableAmt());
//					logger.info(" settledatefiveNetAmountFpx   " + settledatefiveNetAmountFpx);
//				}
//				if (getsettlementfivedatam1Pay.get(0).getPayableAmt() != null
//						&& !getsettlementfivedatam1Pay.get(0).getPayableAmt().isEmpty()) {
//
//					settledatefiveNetAmountm1Pay = Double
//							.parseDouble(getsettlementfivedatam1Pay.get(0).getPayableAmt());
//					logger.info(" settledatefiveNetAmountm1Pay   " + settledatefiveNetAmountm1Pay);
//				}
//
//				double FinalTotalsettledatefiveNetAmount = settledatefiveNetAmountCard + settledatefiveNetAmountBoost
//						+ settledatefiveNetAmountGrabpay + settledatefiveNetAmountFpx + settledatefiveNetAmountm1Pay;
//
//				String pattern = "#,##0.00";
//				DecimalFormat myFormatter = new DecimalFormat(pattern);
//				String TotalsettledatefiveNetAmount = myFormatter.format(FinalTotalsettledatefiveNetAmount);
//
//				logger.info(" TotalsettledatefiveNetAmount   " + TotalsettledatefiveNetAmount);
//
//				// model.addAttribute("settlementdatefivedb", settlementdatefivedb);
//				model.addAttribute("TotalsettledatefiveNetAmount", TotalsettledatefiveNetAmount);
//
//			}
//
//			String Fonedate = Fsettlementdateone.replace("-", " ");
//			String Ftwodate = Fsettlementdatetwo.replace("-", " ");
//			String Fthreedate = Fsettlementdatethree.replace("-", " ");
//			String Ffourdate = Fsettlementdatefour.replace("-", " ");
//			String Ffivedate = Fsettlementdatefive.replace("-", " ");
//
//			model.addAttribute("Fsettlementdateone", Fsettlementdateone);
//			model.addAttribute("Fsettlementdatetwo", Fsettlementdatetwo);
//			model.addAttribute("Fsettlementdatethree", Fsettlementdatethree);
//			model.addAttribute("Fsettlementdatefour", Fsettlementdatefour);
//			model.addAttribute("Fsettlementdatefive", Fsettlementdatefive);
//
//			model.addAttribute("Fonedate", Fonedate);
//			model.addAttribute("Ftwodate", Ftwodate);
//			model.addAttribute("Fthreedate", Fthreedate);
//			model.addAttribute("Ffourdate", Ffourdate);
//			model.addAttribute("Ffivedate", Ffivedate);
//
//			// Taking settlement of 5 days Week value
//
//			LocalDate settleone = LocalDate.parse(settlementdateone);
//			LocalDate settletwo = LocalDate.parse(settlementdatetwo);
//			LocalDate settlethree = LocalDate.parse(settlementdatethree);
//			LocalDate settlefour = LocalDate.parse(settlementdatefour);
//			LocalDate settlefive = LocalDate.parse(settlementdatefive);
//			DayOfWeek settleoneweekNo = settleone.getDayOfWeek();
//			DayOfWeek settletwoweekNo = settletwo.getDayOfWeek();
//			DayOfWeek settlethreeweekNo = settlethree.getDayOfWeek();
//			DayOfWeek settlefourweekNo = settlefour.getDayOfWeek();
//			DayOfWeek settlefiveweekNo = settlefive.getDayOfWeek();
//
//			int cdateone = settleoneweekNo.getValue();
//			int cdatetwo = settletwoweekNo.getValue();
//			int cdatethree = settlethreeweekNo.getValue();
//			int cdatefour = settlefourweekNo.getValue();
//			int cdatefive = settlefiveweekNo.getValue();
//
//			model.addAttribute("cdateone", cdateone);
//			model.addAttribute("cdatetwo", cdatetwo);
//			model.addAttribute("cdatethree", cdatethree);
//			model.addAttribute("cdatefour", cdatefour);
//			model.addAttribute("cdatefive", cdatefive);
//
//			// Early Settlement End
//
//		}
//
//		model.addAttribute("checkDeviceStatus", 0);
//		session.setAttribute("merchant", merchant);
//		model.addAttribute("loginname", principal.getName());
//		return TEMPLATE_MERCHANT;
//	}

	@RequestMapping(value = { "/merdashBoard" }, method = RequestMethod.GET)
	public String webMerchantDashBoard(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("Login Person in dash Board in admincontroller:" + principal.getName());
		// Merchant merchant =
		// merchantService.loadMerchant(principal.getName());
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		MID mid = merchantService.loadMidByMerchant_PK(String.valueOf(currentMerchant.getId()));

		String merchanttype = currentMerchant.getMerchantType();
		logger.info("current merchant Type : "+merchanttype);

		// We used MODIFIED_BY field for checking Ezysettle is enable or not
		String checkIfSettle = currentMerchant.getModifiedBy();

		logger.info("checkIfEzySettle===>" + checkIfSettle);
		PageBean pageBean = null;
		String checkname = PropertyLoad.getFile().getProperty("DISABLE_EZYSETTLE");
		if (currentMerchant.getUsername().equalsIgnoreCase(checkname)) {
			pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee", Module.HOME_WEB);
		} else {

			// Check if either merchanttype or checkIfSettle is null, empty, or blank
			if (Utils.isNullOrEmpty(merchanttype) || Utils.isNullOrEmpty(checkIfSettle)) {
				pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee", Module.HOME_WEB);
			} else if ("Yes".equalsIgnoreCase(checkIfSettle) && ("u".equalsIgnoreCase(merchanttype) || "fiuu".equalsIgnoreCase(merchanttype))) {
				pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrd", Module.HOME_WEB);
			} else {
				pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee", Module.HOME_WEB);
			}
		}
		model.addAttribute("pageBean", pageBean);

		Utils utils = new Utils();
		EzysettleUtils ezysettleUtils = new EzysettleUtils();

		List<String> merchantMids = utils.loadMid(currentMerchant);
		MobiMDR mobiMdr = null;
		for (String merchantMid : merchantMids) {
			mobiMdr = transactionService.loadMobiMdr(merchantMid);
			if (mobiMdr == null) {
				logger.warn("MobiMDR not found for merchantMid: {}. Skipping this merchantMid." + merchantMid);
				/* Skip the rest of the loop and move to the next merchantMid */
				continue;
			}
			if (mobiMdr.getSettlePeriod() == null) {
				logger.warn("SettleType is null for merchantMid: {}. Loading another MID." + merchantMid);
				continue;
			}
			logger.info("Successfully loaded MobiMDR for merchantMid: {}" + merchantMid);
			break;
		}

//
//		String merchantMid = utils.loadMid(currentMerchant);
//		MobiMDR mobiMdr = transactionService.loadMobiMdr(merchantMid);

		PaginationBean<ReaderList> paginationBean2 = new PaginationBean<ReaderList>();
		List<ReaderList> readerdata = new ArrayList<ReaderList>();
		// Need to optimize more
		readerdata = readerService.getReaderList(currentMerchant);
		logger.info("after Reader Size : " + readerdata.size());
		paginationBean2.setItemList(readerdata);
		model.addAttribute("paginationBean2", paginationBean2);

		// for api key table end
		session.setAttribute("MerchantProfile", "Yes");
		session.setAttribute("userRole", "BANK_MERCHANT");
		int totalDevice;
		logger.info("Merchant_profile ");

		logger.info("merchant id : " + currentMerchant.getId());
		logger.info("Merchant Name : " + currentMerchant.getBusinessName());

		if (currentMerchant.getPreAuth() != null) {
			session.setAttribute("preAuth", currentMerchant.getPreAuth());
		} else {
			session.setAttribute("preAuth", "No");
		}

		List<MobileUser> mobileuser = mobileUser.loadMobileUserByFk(currentMerchant.getId());

		String eblBoost = mobileuser.stream().map(MobileUser::getEnableBoost).filter(Objects::nonNull)
				.map(String::toLowerCase).filter(s -> s.equalsIgnoreCase("Yes")).findFirst().orElse("No");
		session.setAttribute("enableBoost", eblBoost);

		String eblMoto = mobileuser.stream().map(MobileUser::getEnableMoto).filter(Objects::nonNull)
				.map(String::toLowerCase).filter(s -> s.equalsIgnoreCase("Yes")).findFirst().orElse("No");
		session.setAttribute("enableMoto", eblMoto);

		// Check if the Merchant is Enable PreAuth or Not
		if (currentMerchant.getPreAuth() == null) {
			currentMerchant.setPreAuth("No");
		}

		if ((currentMerchant.getMid().getMotoMid() != null || currentMerchant.getMid().getUmMotoMid() != null)
				&& currentMerchant.getPreAuth().equalsIgnoreCase("Yes")) {
			session.setAttribute("ezyAuthEnable", "Yes");
		} else {
			session.setAttribute("ezyAuthEnable", "No");
		}
		String ezyLinkCheck = (currentMerchant.getAuth3DS() != null
				&& currentMerchant.getAuth3DS().equalsIgnoreCase("Yes")) ? "YES" : "NO";
		session.setAttribute("merchantUserName", currentMerchant.getBusinessName());

		String typename = PropertyLoad.getFile().getProperty("MERCHANT_TYPE");
		logger.info("merchant Type :" + typename);
		if (merchanttype != null && (merchanttype.equalsIgnoreCase(typename) || merchanttype.equalsIgnoreCase("FIUU"))) {
			if (checkIfSettle != null && !checkIfSettle.trim().isEmpty() && checkIfSettle.equalsIgnoreCase("Yes")) {

			DateTime mytDateAndTime = utils.currentMYT();

			// Service - To Check Holiday Given 5 dates
			logger.info("Malasiyan Current Date : " + mytDateAndTime.getDate());
			logger.info("Malasiyan Current Time : " + mytDateAndTime.getTime());
			List<String> finalSettlementDate = transactionService.tocheckholiday(mytDateAndTime.getDate(),
					mytDateAndTime.getTime());

			String settlementdateone = finalSettlementDate.get(0);
			String settlementdatetwo = finalSettlementDate.get(1);
			String settlementdatethree = finalSettlementDate.get(2);
			String settlementdatefour = finalSettlementDate.get(3);
			String settlementdatefive = finalSettlementDate.get(4);

//			List<Double> totalSettledAmounts = new ArrayList<>();
			ConcurrentMap<String, Double> settledAmountsMap = new ConcurrentHashMap<>();
			ConcurrentMap<String, String> settledAmountsProductVise = new ConcurrentHashMap<>();

			finalSettlementDate.parallelStream().forEach(settlementdate -> {
				ExecutorService executorService = Executors.newFixedThreadPool(5);
				try {
					List<Callable<String>> callables = new ArrayList<Callable<String>>();
					boolean executed = false;

					callables.add(new Callable<String>() {
						public String call() throws Exception {
							SettlementMDR getsettlementonedatacard = transactionService
									.loadNetAmountandsettlementdatebyCardEzysettle(settlementdate, currentMerchant);

							String cardSettlementAmount = String.valueOf(getsettlementonedatacard == null ? 0.0
									: Double.parseDouble(getsettlementonedatacard.getNetAmount()));
							logger.info("Settlement Amount Card : " + cardSettlementAmount + "Settlement Date :"
									+ settlementdate);
							settledAmountsProductVise.merge(settlementdate, "Card " + cardSettlementAmount,
									(oldValue, newValue) -> oldValue + ", " + newValue);

//							settledAmountsProductVise.put(settlementdate, "Card " + cardSettlementAmount);
							return cardSettlementAmount;
						}
					});

					callables.add(new Callable<String>() {
						public String call() throws Exception {
							BoostDailyRecon getsettlementonedataboost = transactionService
									.loadNetAmountandsettlementdatebyBoostEzysettle(settlementdate, currentMerchant);

							String boostSettlementAmount = String.valueOf(getsettlementonedataboost == null ? 0.0
									: Double.parseDouble(getsettlementonedataboost.getNetAmount()));
							logger.info("Settlement Amount Boost : " + boostSettlementAmount + "Settlement Date :"
									+ settlementdate);
//							settledAmountsProductVise.put(settlementdate, "Boost " + boostSettlementAmount);
							settledAmountsProductVise.merge(settlementdate, "Boost  " + boostSettlementAmount,
									(oldValue, newValue) -> oldValue + ", " + newValue);

							return boostSettlementAmount;
						}
					});

					callables.add(new Callable<String>() {
						public String call() throws Exception {
							GrabPayFile getsettlementonedatagrabpay = transactionService
									.loadNetAmountandsettlementdatebyGrabpayEzysettle(settlementdate, currentMerchant);

							String grabPaySettlementAmount = String.valueOf(getsettlementonedatagrabpay == null ? 0.0
									: Double.parseDouble(getsettlementonedatagrabpay.getNetAmt()));
							logger.info("Settlement Amount Grabpay : " + grabPaySettlementAmount + "Settlement Date :"
									+ settlementdate);
//							settledAmountsProductVise.put(settlementdate, "Grab " + grabPaySettlementAmount);
							settledAmountsProductVise.merge(settlementdate, "Grab  " + grabPaySettlementAmount,
									(oldValue, newValue) -> oldValue + ", " + newValue);
							return grabPaySettlementAmount;
						}
					});

					callables.add(new Callable<String>() {
						public String call() throws Exception {
							FpxTransaction getsettlementonedatafpx = transactionService
									.loadNetAmountandsettlementdatebyFpxEzysettle(settlementdate, currentMerchant);

							String fpxSettlementAmount = String.valueOf(getsettlementonedatafpx == null ? 0.0
									: Double.parseDouble(getsettlementonedatafpx.getPayableAmt()));
							logger.info("Settlement Amount Fpx : " + fpxSettlementAmount + "Settlement Date :"
									+ settlementdate);
//							settledAmountsProductVise.put(settlementdate, "Fpx " + fpxSettlementAmount);
							settledAmountsProductVise.merge(settlementdate, "Fpx  " + fpxSettlementAmount,
									(oldValue, newValue) -> oldValue + ", " + newValue);
							return fpxSettlementAmount;
						}
					});

					callables.add(new Callable<String>() {
						public String call() throws Exception {
							EwalletTxnDetails getsettlementonedatam1Pay = transactionService
									.loadNetAmountandsettlementdatebym1PayEzysettle(settlementdate, currentMerchant);

							String m1paySettlementAmount = String.valueOf(getsettlementonedatam1Pay == null ? 0.0
									: Double.parseDouble(getsettlementonedatam1Pay.getPayableAmt()));
							logger.info("Settlement Amount M1pay : " + m1paySettlementAmount + "Settlement Date :"
									+ settlementdate);
//							settledAmountsProductVise.put(settlementdate, "M1pay " + m1paySettlementAmount);
							settledAmountsProductVise.merge(settlementdate, "M1pay  " + m1paySettlementAmount,
									(oldValue, newValue) -> oldValue + ", " + newValue);
							return m1paySettlementAmount;
						}
					});

					List<Future<String>> futures = executorService.invokeAll(callables);
					// Variable to accumulate total sum of amounts

					if (!futures.isEmpty()) {
						double totalAmount = 0.0;
						for (int i = 0; i < futures.size(); i++) {

							Future<String> future = futures.get(i);
							String result = future.get();
							logger.info("Result : " + result);
							// Use the List to handle net amount assignment based on index
							double amount = Double.parseDouble(result);
							totalAmount += amount; // Accumulate amount
							logger.info("totalAmount ++++++ ====> : " + totalAmount);
							if (!executed) {
//    						// validate cutoff time
								String EzysettleCutoffTime = "17:00:00";

								LocalTime CutoffTime = LocalTime.parse(EzysettleCutoffTime);
								LocalTime LocalcurrentTime = LocalTime.parse(mytDateAndTime.getTime());

								if (LocalcurrentTime.isBefore(CutoffTime)) {

									logger.info("before cutoff time");
									settledAmountsMap.put(settlementdate, totalAmount);

								} else if (LocalcurrentTime.isAfter(CutoffTime)) {

									LocalDate Settledateone = LocalDate.parse(settlementdate);
									LocalDate Tomorrowdate = LocalDate.now().plusDays(1);
									if (Settledateone.equals(Tomorrowdate)) {
										logger.info("After cutoff time");
										logger.info("Settlementdateone equal");
										String Status = "ACTIVE";
										model.addAttribute("Status", Status);
//										totalSettledAmounts.add(totalAmount);
										settledAmountsMap.put(settlementdate, totalAmount);

									} else {
										logger.info("After cutoff time");
										logger.info("Settlementdateone not equal");
//										totalSettledAmounts.add(totalAmount);
										settledAmountsMap.put(settlementdate, totalAmount);
									}
								}
								executed = true;
							} else {
								logger.info("TotalAmount **** : " + totalAmount);
//								totalSettledAmounts.add(totalAmount);
								settledAmountsMap.put(settlementdate, totalAmount);
							}

						}

//						totalSettledAmounts.add(totalAmount);
					}

				} catch (Exception e) {
					e.printStackTrace();
					logger.error("Exception occurred while settlement amount in settlementDetails table : " + e);
				} finally {
					executorService.shutdown();
				}
			});

			List<Double> totalAmounts = new ArrayList<>();
			List<Double> totalAmountsNew = new ArrayList<>();

			for (Map.Entry<String, String> entry : settledAmountsProductVise.entrySet()) {
				String settlementDate = entry.getKey();
				String settlementAmounts = entry.getValue();

				logger.info("Products Settlement Date : " + settlementDate + " Amount : " + settlementAmounts);

			}

			// Convert map entries to list
			List<Map.Entry<String, Double>> entryList = new ArrayList<>(settledAmountsMap.entrySet());

			// Sort the list based on keys
			Collections.sort(entryList, new Comparator<Map.Entry<String, Double>>() {
				@Override
				public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
					return o1.getKey().compareTo(o2.getKey());
				}
			
			});
			

			// Convert sorted list back to a map
			LinkedHashMap<String, Double> sortedMap = new LinkedHashMap<>();
			for (Map.Entry<String, Double> entry : entryList) {
				sortedMap.put(entry.getKey(), entry.getValue());
			}

			// Print sorted map
			for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {

				LocalDate currentDate = LocalDate.now();
				DayOfWeek dayOfWeek = currentDate.getDayOfWeek();

				if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
					logger.info("Sorted Settlement Data : " + entry.getKey() + " : " + entry.getValue());

					String settlePeriod = Optional.ofNullable(mobiMdr).map(m -> m.getSettlePeriod()).orElse(null);
					if (settlePeriod == null) {
						totalAmounts.add(0.0);
					} else {
						totalAmounts.add(entry.getValue());
					}
				} else {
					logger.info("Today is not Saturday or Sunday; skipping weekend transaction filtering.");
					totalAmounts = ezysettleUtils.filterCurrentDayTransactions(entry.getKey(), entry.getValue(),
							mobiMdr, transactionDAO, totalAmounts, currentDate);
				}

//				totalAmounts.add(entry.getValue());
//				logger.info("Sorted Settlement Data : " + entry.getKey() + " : " + entry.getValue());
			}

			logger.info("TotalsettledateoneNetAmount =>" + totalAmounts.get(0));
			logger.info("TotalsettledatetwoNetAmount =>" + totalAmounts.get(1));
			logger.info("TotalsettledatethreeNetAmount =>" + totalAmounts.get(2));
			logger.info("TotalsettledatefourNetAmount =>" + totalAmounts.get(3));
			logger.info("TotalsettledatefiveNetAmount =>" + totalAmounts.get(4));

			model.addAttribute("TotalsettledateoneNetAmount",
					String.valueOf(utils.amountFormatWithcomma(totalAmounts.get(0))));
			model.addAttribute("TotalsettledatetwoNetAmount",
					String.valueOf(utils.amountFormatWithcomma(totalAmounts.get(1))));
			model.addAttribute("TotalsettledatethreeNetAmount",
					String.valueOf(utils.amountFormatWithcomma(totalAmounts.get(2))));
			model.addAttribute("TotalsettledatefourNetAmount",
					String.valueOf(utils.amountFormatWithcomma(totalAmounts.get(3))));
			model.addAttribute("TotalsettledatefiveNetAmount",
					String.valueOf(utils.amountFormatWithcomma(totalAmounts.get(4))));

			String Fsettlementdateone = null;
			String Fsettlementdatetwo = null;
			String Fsettlementdatethree = null;
			String Fsettlementdatefour = null;
			String Fsettlementdatefive = null;

			try {
				Fsettlementdateone = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(settlementdateone));
				Fsettlementdatetwo = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(settlementdatetwo));
				Fsettlementdatethree = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(settlementdatethree));
				Fsettlementdatefour = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(settlementdatefour));
				Fsettlementdatefive = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd").parse(settlementdatefive));

			} catch (ParseException e1) {
				logger.error(e1.getMessage());
				e1.printStackTrace();
			}

			logger.info("Settlementdateone => " + settlementdateone);
			logger.info("Settlementdatetwo => " + settlementdatetwo);
			logger.info("Settlementdatethree => " + settlementdatethree);
			logger.info("Settlementdatefour => " + settlementdatefour);
			logger.info("Settlementdatefive => " + settlementdatefive);

			String Fonedate = Fsettlementdateone.replace("-", " ");
			String Ftwodate = Fsettlementdatetwo.replace("-", " ");
			String Fthreedate = Fsettlementdatethree.replace("-", " ");
			String Ffourdate = Fsettlementdatefour.replace("-", " ");
			String Ffivedate = Fsettlementdatefive.replace("-", " ");

			model.addAttribute("Fsettlementdateone", Fsettlementdateone);
			model.addAttribute("Fsettlementdatetwo", Fsettlementdatetwo);
			model.addAttribute("Fsettlementdatethree", Fsettlementdatethree);
			model.addAttribute("Fsettlementdatefour", Fsettlementdatefour);
			model.addAttribute("Fsettlementdatefive", Fsettlementdatefive);

			model.addAttribute("Fonedate", Fonedate).addAttribute("Ftwodate", Ftwodate)
					.addAttribute("Fthreedate", Fthreedate).addAttribute("Ffourdate", Ffourdate)
					.addAttribute("Ffivedate", Ffivedate);

			// Taking settlement of 5 days Week value
			LocalDate settleone = LocalDate.parse(settlementdateone);
			LocalDate settletwo = LocalDate.parse(settlementdatetwo);
			LocalDate settlethree = LocalDate.parse(settlementdatethree);
			LocalDate settlefour = LocalDate.parse(settlementdatefour);
			LocalDate settlefive = LocalDate.parse(settlementdatefive);

			DayOfWeek settleoneweekNo = settleone.getDayOfWeek();
			DayOfWeek settletwoweekNo = settletwo.getDayOfWeek();
			DayOfWeek settlethreeweekNo = settlethree.getDayOfWeek();
			DayOfWeek settlefourweekNo = settlefour.getDayOfWeek();
			DayOfWeek settlefiveweekNo = settlefive.getDayOfWeek();

			int cdateone = settleoneweekNo.getValue();
			int cdatetwo = settletwoweekNo.getValue();
			int cdatethree = settlethreeweekNo.getValue();
			int cdatefour = settlefourweekNo.getValue();
			int cdatefive = settlefiveweekNo.getValue();

			model.addAttribute("cdateone", cdateone);
			model.addAttribute("cdatetwo", cdatetwo);
			model.addAttribute("cdatethree", cdatethree);
			model.addAttribute("cdatefour", cdatefour);
			model.addAttribute("cdatefive", cdatefive);

			// Early Settlement End
			}
		}

		AuditTrail auditTrail = merchantService.updateAuditTrailByMerchant(principal.getName(), principal.getName(),
				"viewByAdmin");

		if (auditTrail != null) {
			logger.info("Merchant Portal Viewed by Admin : " + principal.getName());
		}

		model.addAttribute("merchant", currentMerchant);
		logger.info("in homecontroller dashboard: ");

		model.addAttribute("loginname", principal.getName());
		model.addAttribute("checkDeviceStatus", "0");
		model.addAttribute("deviceCheck", "YES");
		session.setAttribute("userName", currentMerchant.getUsername());
		session.setAttribute("merchant", currentMerchant);
		session.setAttribute("userRole", "BANK_MERCHANT");

		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/recurringList" }, method = RequestMethod.GET)
	public String motoRecurList(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {

		logger.info("Recurring list " + principal.getName());

		PageBean pageBean = new PageBean("Recurring list", "merchant/RecurringDetails/RecurringUsersList",
				Module.MERCHANT, "merchant/sideMenuMerchant");

		PaginationBean<EzyRecurringPayment> paginationBean = new PaginationBean<EzyRecurringPayment>();
		// paginationBean.setCurrPage(currPage);
		merchantService.merchantListRecurring(paginationBean, null, null);

		logger.info("itemList: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {
			for (EzyRecurringPayment e : paginationBean.getItemList()) {
				try {

					if (e.getMaskedPan() != null) {

						String pan = e.getMaskedPan().substring(e.getMaskedPan().length() - 8);
						if (pan.contains("f")) {
							pan = pan.replaceAll("f", "X");

							e.setMaskedPan(pan);

						} else {

							e.setMaskedPan(pan);

						}

					} else {
						e.setMaskedPan("NA");
					}

					if (e.getAmount() != null) {

						String output = new DecimalFormat("#,##0.00").format(Double.parseDouble(e.getAmount()) / 100);

						e.setAmount(output);
					}
					if (e.getStartDate() != null) {

						try {

							e.setStartDate(new SimpleDateFormat("dd/MM/yyyy")
									.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getStartDate())));

						} catch (ParseException e1) {

							e1.printStackTrace();
						}

					}
					if (e.getExpDate() != null) {
						// logger.info("exp date: "+e.getExpDate());
						String endDate = null;

						try {

							e.setExpDate(new SimpleDateFormat("MM/yyyy")
									.format(new SimpleDateFormat("yyMM").parse(e.getExpDate())));

						} catch (ParseException e1) {

							e1.printStackTrace();
						}

					}
					if (e.getEndDate() != null) {

						try {

							e.setEndDate(new SimpleDateFormat("dd/MM/yyyy")
									.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getEndDate())));

						} catch (ParseException e1) {

							e1.printStackTrace();
						}

					}
					if (e.getCustName() != null) {
						// logger.info("customer Name: "+e.getCustName());
					} else {
						// logger.info("customer name: null");
					}

					if (e.getNextTriggerDate() != null) {

						try {

							e.setNextTriggerDate(new SimpleDateFormat("dd/MM/yyyy")
									.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getNextTriggerDate())));

						} catch (ParseException e1) {

							e1.printStackTrace();
						}

					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		model.addAttribute("paginationBean", paginationBean);

		// model.addAttribute("terminalDetails", terminalDetails);

		// model.addAttribute("merchant", currentMerchant);
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/editRecurring/{recId}" }, method = RequestMethod.GET)
	public String motoEditRecurring(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @PathVariable final long recId) {

		logger.info("Admin-Merchant: moto Edit Recurring  " + principal.getName() + " RecId: " + recId);

		PageBean pageBean = new PageBean("Recurring list", "merchant/RecurringDetails/editRecurringDetails",
				Module.MERCHANT, "merchant/sideMenuMerchant");

		PaginationBean<EzyRecurringPayment> paginationBean = new PaginationBean<EzyRecurringPayment>();
		// paginationBean.setCurrPage(currPage);

		EzyRecurringPayment ezyRec = merchantService.loadMerchantRecurring(recId);

		try {

			if (ezyRec.getMaskedPan() != null) {

				String pan = ezyRec.getMaskedPan().substring(ezyRec.getMaskedPan().length() - 8);
				if (pan.contains("f")) {
					pan = pan.replaceAll("f", "X");

					ezyRec.setMaskedPan(pan);

				} else {

					ezyRec.setMaskedPan(pan);

				}

			} else {
				ezyRec.setMaskedPan("NA");
			}

			if (ezyRec.getAmount() != null) {

				String output = new DecimalFormat("#,##0.00").format(Double.parseDouble(ezyRec.getAmount()) / 100);

				ezyRec.setAmount(output);
			}
			if (ezyRec.getStartDate() != null) {

				try {

					ezyRec.setStartDate(new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("dd/MM/yyyy").parse(ezyRec.getStartDate())));

				} catch (ParseException e1) {

					e1.printStackTrace();
				}

			}
			if (ezyRec.getExpDate() != null) {
				// logger.info("exp date: "+e.getExpDate());
				String endDate = null;

				try {

					ezyRec.setExpDate(new SimpleDateFormat("MMM-yyyy")
							.format(new SimpleDateFormat("yyMM").parse(ezyRec.getExpDate())));

				} catch (ParseException e1) {

					e1.printStackTrace();
				}

			}
			/*
			 * if (ezyRec.getEndDate() != null) { String endDate=null; endDate=new
			 * SimpleDateFormat("dd-MMM-yyyy") .format(new SimpleDateFormat("dd/mm/yyyy")
			 * .parse(ezyRec.getEndDate()));
			 * logger.info("start date: "+ezyRec.getEndDate()); ezyRec.setEndDate(endDate);
			 * logger.info("start date: "+ezyRec.getEndDate()); }if (ezyRec.getStartDate()
			 * != null) {
			 * 
			 * String startDate=null; startDate=new SimpleDateFormat("dd-MMM-yyyy")
			 * .format(new SimpleDateFormat("dd/mm/yyyy") .parse(ezyRec.getStartDate()));
			 * startDate=new SimpleDateFormat("dd/mm/yyyy") .format(new
			 * SimpleDateFormat("dd-MMM-yyyy") .parse(ezyRec.getStartDate()));
			 * logger.info("start date: "+ezyRec.getStartDate());
			 * ezyRec.setStartDate(startDate);
			 * logger.info("start date: "+ezyRec.getStartDate()); }
			 */
			if (ezyRec.getCustName() != null) {
				// logger.info("customer Name: "+e.getCustName());
			} else {
				// logger.info("customer name: null");
			}

			if (ezyRec.getNextTriggerDate() != null) {

				try {

					ezyRec.setNextTriggerDate(new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("dd/MM/yyyy").parse(ezyRec.getNextTriggerDate())));

				} catch (ParseException e1) {

					e1.printStackTrace();
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		model.addAttribute("paginationBean", paginationBean);

		// model.addAttribute("terminalDetails", terminalDetails);

		// model.addAttribute("merchant", currentMerchant);
		model.addAttribute("ezyRec", ezyRec);
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/updateRecurringStatus" }, method = RequestMethod.POST)
	public String motoUpdateRecurring(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("id") final Long recId,
			@RequestParam("status") final String status, @RequestParam("mid") final String mid) {

		logger.info("Admin-Merchant: moto Update Recurring  " + principal.getName() + " RecId: " + recId);

		logger.info(mid + " : " + recId + " : " + status);
		PageBean pageBean = new PageBean("Recurring list", "merchant/RecurringDetails/UpdateRecurSuccessful",
				Module.MERCHANT, "merchant/sideMenuMerchant");

		EzyRecurringPayment ezyRec = merchantService.loadMerchantRecurring(recId);

		// PaginationBean<EzyRecurringPayment> paginationBean = new
		// PaginationBean<EzyRecurringPayment>();
		// paginationBean.setCurrPage(currPage);

		// int update = merchantService.UpdateRecurringStatus(recId, status);
		ezyRec.setStatus(status);

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		date = calendar.getTime();
		int year = calendar.getWeekYear();
		long mon = date.getMonth() + 1;
		int day = date.getDate();

		String fromDateToSearch = null;
		String toDateToSearch = null;
		String modifiedDate = year + "-" + mon + "-" + day;

		logger.info("modifiedDate: " + modifiedDate);
		try {
			modifiedDate = new SimpleDateFormat("yyyy/MM/dd")
					.format(new SimpleDateFormat("yyyy-MM-dd").parse(modifiedDate));

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		logger.info("modifiedDate: " + modifiedDate);
		ezyRec.setRemarks(principal.getName() + " on " + modifiedDate);
		ezyRec = merchantService.UpdateRecurringStatus(ezyRec);
		if (ezyRec.getMaskedPan() != null) {

			String pan = ezyRec.getMaskedPan().substring(ezyRec.getMaskedPan().length() - 8);
			if (pan.contains("f")) {
				pan = pan.replaceAll("f", "X");

				ezyRec.setMaskedPan(pan);

			} else {

				ezyRec.setMaskedPan(pan);

			}

		} else {
			ezyRec.setMaskedPan("NA");
		}
		logger.info("status: " + ezyRec.getStatus() + " card no: " + ezyRec.getMaskedPan());
		if (ezyRec != null) {

			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(principal.getName(), principal.getName(),
					"RecurringStatusUpdate");

			if (auditTrail != null) {
				logger.info("Recurring Status: " + status + " by Admin : " + principal.getName());

			}
		}
		// model.addAttribute("paginationBean", paginationBean);

		// model.addAttribute("mid", mid);
		ezyRec.setRemarks("Card No: " + ezyRec.getMaskedPan() + " " + status + " By " + principal.getName());
		model.addAttribute("ezyRec", ezyRec);

		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/searchRecurringList" }, method = RequestMethod.GET)
	public String motoSearchRecurList(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("date") String fromDate, @RequestParam("date1") String toDate) {

		logger.info("moto Search Recurring list");

		logger.info("fromDate: " + fromDate + " toDate: " + toDate);

		String fromDate1 = HtmlUtils.htmlEscape(fromDate);
		String toDate1 = HtmlUtils.htmlEscape(toDate);

		try {
			fromDate = new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(fromDate1));
			toDate = new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(toDate1));

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		logger.info("fromDate: " + fromDate + " toDate: " + toDate);

		PageBean pageBean = new PageBean("transactions list", "merchant/RecurringDetails/RecurringUsersList",
				Module.MERCHANT, "merchant/sideMenuTransaction");
		PaginationBean<EzyRecurringPayment> paginationBean = new PaginationBean<EzyRecurringPayment>();
		// paginationBean.setCurrPage(currPage);
		merchantService.merchantListRecurring(paginationBean, fromDate1, toDate1);

		logger.info("itemList: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {
			for (EzyRecurringPayment e : paginationBean.getItemList()) {
				try {

					if (e.getMaskedPan() != null) {

						String pan = e.getMaskedPan().substring(e.getMaskedPan().length() - 8);
						if (pan.contains("f")) {
							pan = pan.replaceAll("f", "X");

							e.setMaskedPan(pan);

						} else {

							e.setMaskedPan(pan);

						}

					} else {
						e.setMaskedPan("NA");
					}

					if (e.getAmount() != null) {

						String output = new DecimalFormat("#,##0.00").format(Double.parseDouble(e.getAmount()) / 100);

						e.setAmount(output);
					}
					if (e.getStartDate() != null) {

						e.setStartDate(new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getStartDate())));

					}
					if (e.getExpDate() != null) {
						// logger.info("exp date: " + e.getExpDate());
						String endDate = null;

						e.setExpDate(new SimpleDateFormat("MM/yyyy")
								.format(new SimpleDateFormat("yyMM").parse(e.getExpDate())));
					}
					if (e.getEndDate() != null) {

						e.setEndDate(new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getEndDate())));
					}
					if (e.getNextTriggerDate() != null) {

						e.setNextTriggerDate(new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getNextTriggerDate())));
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		model.addAttribute("paginationBean", paginationBean);

		// model.addAttribute("terminalDetails", terminalDetails);

		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/exportRecurringList" }, method = RequestMethod.GET)
	public ModelAndView motoExportRecurList(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("date") String fromDate, @RequestParam("date1") String toDate,
			@RequestParam("export") String export) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		logger.info("moto Search Recurring list");

		logger.info("fromDate: " + fromDate + " toDate: " + toDate);

		try {
			fromDate = new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(fromDate));
			toDate = new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(toDate));

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		logger.info("fromDate: " + fromDate + " toDate: " + toDate);
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		// Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		// logger.info("tcurrently logged in user is: "+
		// currentMerchant.getUsername());
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Recurring/RecurringUsersList",
				Module.MERCHANT, "merchantweb/transaction/sideMenuTransaction");
		PaginationBean<EzyRecurringPayment> paginationBean = new PaginationBean<EzyRecurringPayment>();
		// paginationBean.setCurrPage(currPage);
		merchantService.merchantListRecurring(paginationBean, fromDate, toDate);

		logger.info("itemList: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {
			for (EzyRecurringPayment e : paginationBean.getItemList()) {
				try {

					if (e.getMaskedPan() != null) {

						String pan = e.getMaskedPan().substring(e.getMaskedPan().length() - 8);
						if (pan.contains("f")) {
							pan = pan.replaceAll("f", "X");

							e.setMaskedPan(pan);

						} else {

							e.setMaskedPan(pan);

						}

					} else {
						e.setMaskedPan("NA");
					}

					if (e.getAmount() != null) {

						String output = new DecimalFormat("#,##0.00").format(Double.parseDouble(e.getAmount()) / 100);

						e.setAmount(output);
					}
					if (e.getExpDate() != null) {
						// logger.info("exp date: "+e.getExpDate());
						String endDate = null;

						e.setExpDate(new SimpleDateFormat("MM/yyyy")
								.format(new SimpleDateFormat("yyMM").parse(e.getExpDate())));
					}
					if (e.getStartDate() != null) {
						// logger.info("last triger date: "+ e.getLastTriggerDate());

						try {

							e.setStartDate(new SimpleDateFormat("dd/MM/yyyy")
									.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getStartDate())));

						} catch (ParseException e1) {

							e1.printStackTrace();
						}
						// logger.info("date format:" + e.getLastTriggerDate());

						// e.setLastTriggerDate(date);

						// logger.info("last triger date: "+e.getLastTriggerDate());
					}

					if (e.getEndDate() != null) {
						// logger.info("date format:" + e.getEndDate());
						try {

							e.setEndDate(new SimpleDateFormat("dd/MM/yyyy")
									.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getEndDate())));

						} catch (ParseException e1) {

							e1.printStackTrace();
						}
						// logger.info("date format:" + e.getEndDate());
					}
					if (e.getNextTriggerDate() != null) {
						// logger.info("date format:" + e.getNextTriggerDate());
						try {

							e.setNextTriggerDate(new SimpleDateFormat("dd/MM/yyyy")
									.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getNextTriggerDate())));
						} catch (ParseException e1) {

							e1.printStackTrace();
						}

					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		List<EzyRecurringPayment> list1 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {
			return new ModelAndView("recurringTxnListExcel", "txnList", list1);
		} else {
			return new ModelAndView("recurringTxnListPdf", "txnList", list1);
		}

	}

	// Moto Txn Req Details

	@RequestMapping(value = { "/motoTxnReqList" }, method = RequestMethod.GET)
	public String motoTxnReqList(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {

		logger.info("motoTxnReq list " + principal.getName());
		PageBean pageBean = new PageBean("transactions list", "merchant/MotoTrxnReqDetails/MotoTxnReqList",
				Module.MERCHANT, "merchantweb/transaction/sideMenuTransaction");
		String fromDate = null;
		String toDate = null;
		PaginationBean<MotoTxnDetails> paginationBean = new PaginationBean<MotoTxnDetails>();
		// paginationBean.setCurrPage(1);

		Date date = new Date();

		Calendar calendar = Calendar.getInstance();
		date = calendar.getTime();

		int year = calendar.getWeekYear();

		long mon = date.getMonth() + 1;
		int day = date.getDate() + 1;
		int daybefore = date.getDate() - 10;

		String dateorg2 = day + "/" + mon + "/" + year;
		String dateorg1 = daybefore + "/" + mon + "/" + year;
		logger.info("date to find: fromDate: " + dateorg1 + " toDate: " + dateorg2);
		try {
			toDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg2));
			fromDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dateorg1));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("fromDate:-- " + fromDate + " toDate: " + toDate);
		// merchantService.listMotoTxnReqMerchant(paginationBean, null, null);
		merchantService.listMotoReqMerchant(paginationBean, fromDate, toDate, null);
		logger.info("itemList: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {
			for (MotoTxnDetails e : paginationBean.getItemList()) {
				try {

					// logger.info("date and time : "+e.getReqDate());

					if (e.getAmount() != null) {

						String output = new DecimalFormat("#,##0.00").format(Double.parseDouble(e.getAmount()) / 100);

						e.setAmount(output);
					}
					if (e.getStatus() != null) {
						if (e.getStatus().equals("R")) {
							e.setStatus("Rejected");
						} else if (e.getStatus().equals("E")) {
							e.setStatus("Expired");
						}
						if (e.getStatus().equals("S")) {
							e.setStatus("Success");
						}
						if (e.getStatus().equals("F")) {
							e.setStatus("Failed");
						}
						if (e.getStatus().equals("P")) {
							e.setStatus("Pending");
						}
					} else {
						e.setStatus("Pending");
					}
					if (e.getTimestamp() != null) {
						String rd = new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(e.getTimestamp()));
						String rt = new SimpleDateFormat("HH:mm:ss")
								.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(e.getTimestamp()));
						e.setReqDate(rd);
						e.setExpectedDate(rt);
						// 2019-01-22 11:20:04
					}

					if (e.getMid() != null) {
						Merchant merchant = merchantService.loadMerchantbyMotoMid(e.getMid());
						if (merchant != null) {
							e.setName(merchant.getBusinessName());
						} else {
							e.setName(" ");
						}
					} else {
						e.setName(" ");
					}

					if (e.getTimestamp() != null) {

						String endDate = null;
						String r = new SimpleDateFormat("MMM dd yyyy HH:mm:ss")
								.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(e.getTimestamp()));
						Date now = new Date(r);
						Date addedDate2 = addDays(addDays(now, 20), 10);
						// logger.info(r+" ---- "+addedDate2);
						String rd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(
								new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(addedDate2.toString()));

						e.setExpDate(rd);

					}

					// logger.info(e.getName());

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		model.addAttribute("paginationBean", paginationBean);

		// model.addAttribute("terminalDetails", terminalDetails);

		// model.addAttribute("merchant", currentMerchant);
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/searchmotoTxnReqList" }, method = RequestMethod.GET)
	public String searchMotoTxnReqList(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("status") String status) {

		logger.info("motoTxnReq list " + principal.getName());
		PageBean pageBean = new PageBean("transactions list", "merchant/MotoTrxnReqDetails/MotoTxnReqList",
				Module.MERCHANT, "merchantweb/transaction/sideMenuTransaction");
		String dat = null;
		String dat1 = null;
		PaginationBean<MotoTxnDetails> paginationBean = new PaginationBean<MotoTxnDetails>();
		// paginationBean.setCurrPage(1);
		// merchantService.listMotoTxnReqMerchant(paginationBean, null, null);
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
		merchantService.listMotoReqMerchant(paginationBean, dat, dat1, status);
		logger.info("itemList: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {
			for (MotoTxnDetails e : paginationBean.getItemList()) {
				try {

					// logger.info("date and time : "+e.getReqDate());

					if (e.getAmount() != null) {

						String output = new DecimalFormat("#,##0.00").format(Double.parseDouble(e.getAmount()) / 100);

						e.setAmount(output);
					}
					if (e.getStatus() != null) {
						if (e.getStatus().equals("R")) {
							e.setStatus("Rejected");
						} else if (e.getStatus().equals("E")) {
							e.setStatus("Expired");
						}
						if (e.getStatus().equals("S")) {
							e.setStatus("Success");
						}
						if (e.getStatus().equals("F")) {
							e.setStatus("Failed");
						}
						if (e.getStatus().equals("P")) {
							e.setStatus("Pending");
						}
					} else {
						e.setStatus("Pending");
					}
					if (e.getTimestamp() != null) {
						String rd = new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(e.getTimestamp()));
						String rt = new SimpleDateFormat("HH:mm:ss")
								.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(e.getTimestamp()));
						e.setReqDate(rd);
						e.setExpectedDate(rt);
						// 2019-01-22 11:20:04
					}

					if (e.getMid() != null) {
						Merchant merchant = merchantService.loadMerchantbyMotoMid(e.getMid());
						if (merchant != null) {
							e.setName(merchant.getBusinessName());
						} else {
							e.setName(" ");
						}
					} else {
						e.setName(" ");
					}

					if (e.getTimestamp() != null) {

						String endDate = null;
						String r = new SimpleDateFormat("MMM dd yyyy HH:mm:ss")
								.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(e.getTimestamp()));
						Date now = new Date(r);
						Date addedDate2 = addDays(addDays(now, 20), 10);
						// logger.info(r+" ---- "+addedDate2);
						String rd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(
								new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(addedDate2.toString()));

						e.setExpDate(rd);

					}

					// logger.info(e.getName());

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		model.addAttribute("paginationBean", paginationBean);

		// model.addAttribute("terminalDetails", terminalDetails);

		// model.addAttribute("merchant", currentMerchant);
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;

	}

	public static Date addDays(Date d, int days) {
		d.setTime(d.getTime() + days * 1000 * 60 * 60 * 24);
		return d;
	}

	@RequestMapping(value = { "/exportMotoReqList" }, method = RequestMethod.GET)
	public ModelAndView motoExportReqTxnList(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("date") String fromDate, @RequestParam("date1") String toDate,
			@RequestParam("status") String status, @RequestParam("export") String export) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		logger.info("moto Search txn Req list");

		logger.info("fromDate: " + fromDate + " toDate: " + toDate);

		String dat = null;
		String dat1 = null;
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		// Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		// logger.info("tcurrently logged in user is: "+
		// currentMerchant.getUsername());
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Recurring/MotoTxnReqList", Module.MERCHANT,
				"merchantweb/transaction/sideMenuTransaction");
		PaginationBean<MotoTxnDetails> paginationBean = new PaginationBean<MotoTxnDetails>();
		// paginationBean.setCurrPage(1);
		// merchantService.listMotoTxnReqMerchant(paginationBean, null, null);
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
		merchantService.listMotoReqMerchant(paginationBean, dat, dat1, status);
		logger.info("itemList: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {
			for (MotoTxnDetails e : paginationBean.getItemList()) {
				try {

					// logger.info("date and time : "+e.getReqDate());

					if (e.getAmount() != null) {

						String output = new DecimalFormat("#,##0.00").format(Double.parseDouble(e.getAmount()) / 100);

						e.setAmount(output);
					}
					if (e.getStatus() != null) {
						if (e.getStatus().equals("R")) {
							e.setStatus("Rejected");
						} else if (e.getStatus().equals("E")) {
							e.setStatus("Expired");
						}
						if (e.getStatus().equals("S")) {
							e.setStatus("Success");
						}
						if (e.getStatus().equals("F")) {
							e.setStatus("Failed");
						}
						if (e.getStatus().equals("P")) {
							e.setStatus("Pending");
						}
					} else {
						e.setStatus("Pending");
					}
					if (e.getTimestamp() != null) {
						String rd = new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(e.getTimestamp()));
						String rt = new SimpleDateFormat("HH:mm:ss")
								.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(e.getTimestamp()));
						e.setReqDate(rd + " " + rt);
						// e.setExpectedDate(rt);
						// e.setTimestamp(rd+" "+rt);
						// 2019-01-22 11:20:04
					}

					if (e.getMid() != null) {
						Merchant merchant = merchantService.loadMerchantbyMotoMid(e.getMid());
						if (merchant != null) {
							e.setName(merchant.getBusinessName());
						} else {
							e.setName(" ");
						}
					} else {
						e.setName(" ");
					}

					if (e.getTimestamp() != null) {

						String endDate = null;
						String r = new SimpleDateFormat("MMM dd yyyy HH:mm:ss")
								.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(e.getTimestamp()));
						Date now = new Date(r);
						Date addedDate2 = addDays(addDays(now, 20), 10);
						// logger.info(r+" ---- "+addedDate2);
						String rd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(
								new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(addedDate2.toString()));

						e.setExpDate(rd);

					}

					// logger.info(e.getName());

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		List<MotoTxnDetails> list1 = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {
			return new ModelAndView("motoTxnReqListExcel", "txnList", list1);
		} else {
			return new ModelAndView("motoTxnReqListPdf", "txnList", list1);
		}

	}

	@RequestMapping(value = { "/editMerchant" }, method = RequestMethod.POST)
	public String processEditUserForm(@ModelAttribute("merchant") final RegAddMerchant regAddMerchant,
			final HttpServletRequest request, final ModelMap model, final Principal principal,
			@RequestParam("id") final Long id,
//			@RequestParam("mid") final String mid,
//			@RequestParam("ezymotomid") final String ezymotomid, @RequestParam("ezypassmid") final String ezypassmid,
//			@RequestParam("ezyrecmid") final String ezyrecmid, @RequestParam("ezywaymid") final String ezywaymid,
			@RequestParam("businessName") final String businessName,
			@RequestParam("registeredName") final String registeredName,
			@RequestParam("businessRegNo") final String businessRegNo,
			@RequestParam("registeredAddress") final String registeredAddress,
			@RequestParam("mailingAddress") final String mailingAddress,
			@RequestParam("businessAddress") final String businessAddress,
			/* @RequestParam("businessAddress3") final String businessAddress3, */
			@RequestParam("name") final String name, @RequestParam("salutation") final String salutation,
			@RequestParam("contactNo") final String contactNo, @RequestParam("email") final String email,@RequestParam("secondary_email") final String secondary_email,
			@RequestParam("ownerCount") final String ownerCount,
			@RequestParam("ownerSalutation1") final String ownerSalutation1,
			@RequestParam("ownerName1") final String ownerName1, @RequestParam("passportNo1") final String passportNo1,
			@RequestParam("ownerContactNo1") final String ownerContactNo1,
			@RequestParam("residentialAddress1") final String residentialAddress1,
			@RequestParam("ownerSalutation2") final String ownerSalutation2,
			@RequestParam("ownerName2") final String ownerName2, @RequestParam("passportNo2") final String passportNo2,
			@RequestParam("ownerContactNo2") final String ownerContactNo2,
			@RequestParam("residentialAddress2") final String residentialAddress2,
			@RequestParam("ownerSalutation3") final String ownerSalutation3,
			@RequestParam("ownerName3") final String ownerName3, @RequestParam("passportNo3") final String passportNo3,
			@RequestParam("ownerContactNo3") final String ownerContactNo3,
			@RequestParam("residentialAddress3") final String residentialAddress3,
			@RequestParam("ownerSalutation4") final String ownerSalutation4,
			@RequestParam("ownerName4") final String ownerName4, @RequestParam("passportNo4") final String passportNo4,
			@RequestParam("ownerContactNo4") final String ownerContactNo4,
			@RequestParam("residentialAddress4") final String residentialAddress4,
			@RequestParam("ownerSalutation5") final String ownerSalutation5,
			@RequestParam("ownerName5") final String ownerName5, @RequestParam("passportNo5") final String passportNo5,
			@RequestParam("ownerContactNo5") final String ownerContactNo5,
			@RequestParam("residentialAddress5") final String residentialAddress5,
			@RequestParam("officeEmail") final String officeEmail, @RequestParam("website") final String website,
			@RequestParam("officeNo") final String officeNo, @RequestParam("faxNo") final String faxNo,
			@RequestParam("businessCity") final String businessCity,
			@RequestParam("businessPostCode") final String businessPostCode,
			@RequestParam("businessState") final String businessState,
			@RequestParam("businessType") final String businessType, @RequestParam("documents") final String documents,
			@RequestParam("companyType") final String companyType,
			@RequestParam("natureOfBusiness") final String natureOfBusiness,
//			@RequestParam("signedPackage") final String signedPackage,
			@RequestParam("tradingName") final String tradingName,
			@RequestParam("noOfReaders") final String noOfReaders, @RequestParam("referralId") final String referralId,
			@RequestParam("bankName") final String bankName, @RequestParam("bankAccNo") final String bankAccNo,
			@RequestParam("wavierMonth") final String wavierMonth,
			@RequestParam("yearIncorporated") final String yearIncorporated,
			@RequestParam("accType") final String accType,
			@RequestParam("manualSettlement") final String manualSettlement,
			// @RequestParam("status")final String status,
			@RequestParam("statusRemarks") final String statusRemarks,
			@RequestParam("agentName") final String agentName, @RequestParam("preAuth") final String preAuth,
			@RequestParam("auth3DS") final String auth3DS, @RequestParam("autoSettled") final String autoSettled,
			@RequestParam("merchantStatus") final String status,
			@RequestParam(required = false) final String foreignCard,
			@RequestParam(required = false) final String ezysettle, @RequestParam("mdr") final String mdr,
			@RequestParam(required = false) String formFName, @RequestParam(required = false) String docFName,
			@RequestParam(required = false) String payFName, @RequestParam(required = false) MultipartFile formFile,
			@RequestParam(required = false) MultipartFile docFile,
			@RequestParam(required = false) MultipartFile payFile,
			@RequestParam("payoutAsyncHandler") final String payoutAsyncHandlerStatus,
			@RequestParam("payoutNotificationUrl") final String payoutIpnUrl,
			@RequestParam("reasonForAsyncPayout") final String enableAsyncPayoutReason,
			@RequestParam("maxPayoutLimit") final String maxPayoutLimit,
									  @RequestParam("accountEnquiry") final String accountEnquiry,
									  @RequestParam("quickPayout") final String quickPayout) {


		PageBean pageBean = new PageBean("Merchant user Edit Details", "merchant/edit/merchantseditdetails",
				Module.MERCHANT, "merchant/sideMenuMerchant");

		String responseDataOfficeEmail = null;
		String adminusername = principal.getName();
		logger.info("Merchant ID to check: " + regAddMerchant.getId() + ", Display Admin Login Name: " + adminusername
				+ ", payoutAsyncHandlerStatus: " + payoutAsyncHandlerStatus + ", payoutIpnUrl: " + payoutIpnUrl
				+ ", enableAsyncPayoutReason: " + enableAsyncPayoutReason
		        + ", accountEnquiryEnable: " + accountEnquiry + ", quickPayoutEnabled: " + quickPayout /* + ", quickPayoutUrl: " + quickPayoutUrl*/);

		logger.info("Max payout limit: " + maxPayoutLimit);
		if (secondary_email != null) {
			logger.info("Received Secondary email from param : " + secondary_email);
		}
		Merchant currentMerchant = merchantService.loadMerchantbyid(Long.valueOf(regAddMerchant.getId()));
		RegAddMerchant merchant = new RegAddMerchant();
		MerchantInfo merchantInfo = merchantService.loadMerchantInfoByFk(String.valueOf(id));
		if(merchantInfo!=null){
			merchantInfo.setSecoundaryEmail(secondary_email);
		}else{
			merchantInfo = new MerchantInfo();
			merchantInfo.setSecoundaryEmail(secondary_email);
		}

		merchant.setId(id.toString());
		// Async Payout Changes
		merchant.setIsAsyncPayoutEnabled(payoutAsyncHandlerStatus);
		merchant.setPayoutIpnUrl(payoutIpnUrl);
		merchant.setAsyncEnableReason(enableAsyncPayoutReason);

		// isMaxPayoutLimitSet
		merchant.setMaxPayoutTxnLimit(maxPayoutLimit);


		// Enable account enquiry & quick payout
		merchant.setIsAccountEnquiryEnabled(accountEnquiry);
		merchant.setIsQuickPayoutEnabled(quickPayout);
//		merchant.setQuickPayoutUrl(quickPayoutUrl);

		merchant.setMid(regAddMerchant.getMid());
		merchant.setEzymotomid(regAddMerchant.getEzymotomid());
		merchant.setEzywaymid(regAddMerchant.getEzywaymid());
		merchant.setEzyrecmid(regAddMerchant.getEzyrecmid());
		merchant.setEzypassmid(regAddMerchant.getEzypassmid());
		merchant.setUmEzypassMid(regAddMerchant.getUmEzypassMid());
		merchant.setUmEzyrecMid(regAddMerchant.getUmEzyrecMid());
		merchant.setUmEzywayMid(regAddMerchant.getUmEzywayMid());
		
		//fiuu mid
		merchant.setFiuuMid(regAddMerchant.getFiuuMid());
		
		merchant.setMerchantType(currentMerchant.getMerchantType());
		logger.info("Merchant Type::: " + currentMerchant.getMerchantType());
		merchant.setUmMid(regAddMerchant.getUmMid());
		merchant.setUmMotoMid(regAddMerchant.getUmMotoMid());
		merchant.setRegisteredName(registeredName);
		merchant.setBusinessName(businessName);
		merchant.setBusinessRegNo(businessRegNo);
		merchant.setRegisteredAddress(registeredAddress);
		merchant.setBusinessAddress(businessAddress);
		merchant.setMailingAddress(mailingAddress);
		merchant.setName(name);
		merchant.setContactNo(contactNo);
		merchant.setEmail(email);
		merchant.setSalutation(salutation);
		merchant.setOwnerName1(ownerName1);
		merchant.setPassportNo1(passportNo1);
		merchant.setOwnerContactNo1(ownerContactNo1);
		merchant.setOwnerSalutation1(ownerSalutation1);
		merchant.setResidentialAddress1(residentialAddress1);
		merchant.setOfficeEmail(officeEmail);
		merchant.setWebsite(website);
		merchant.setOfficeNo(officeNo);
		merchant.setFaxNo(faxNo);
		merchant.setBusinessType(businessType);
		merchant.setCompanyType(companyType);
		merchant.setNatureOfBusiness(natureOfBusiness);
		logger.info("check nob status from merchant:" + merchant.getNatureOfBusiness());
		merchant.setBankName(bankName);
		merchant.setBankAccNo(bankAccNo);
		merchant.setDocuments(documents);
		merchant.setBusinessState(businessState);
		merchant.setBusinessCity(businessCity);
		merchant.setBusinessPostCode(businessPostCode);
		merchant.setReferralId(referralId);
		merchant.setNoOfReaders(noOfReaders);
		merchant.setTradingName(tradingName);
//			merchant.setSignedPackage(signedPackage);
		merchant.setYearIncorporated(yearIncorporated);
		merchant.setWavierMonth(wavierMonth);
		// merchant.setStatus(status);
		merchant.setStatusRemarks(statusRemarks);
		merchant.setAgentName(agentName);
		logger.info("agent Name in edit: " + agentName + ":::" + merchant.getAgentName());
		merchant.setOwnerSalutation2(ownerSalutation2);
		merchant.setOwnerName2(ownerName2);
		merchant.setPassportNo2(passportNo2);
		merchant.setOwnerContactNo2(ownerContactNo2);
		merchant.setResidentialAddress2(residentialAddress2);
		merchant.setOwnerSalutation3(ownerSalutation3);
		merchant.setOwnerName3(ownerName3);
		merchant.setPassportNo3(passportNo3);
		merchant.setOwnerContactNo3(ownerContactNo3);
		merchant.setResidentialAddress3(residentialAddress3);
		merchant.setOwnerSalutation4(ownerSalutation4);
		merchant.setOwnerName4(ownerName4);
		merchant.setPassportNo4(passportNo4);
		merchant.setOwnerContactNo4(ownerContactNo4);
		merchant.setResidentialAddress4(residentialAddress4);
		merchant.setOwnerSalutation5(ownerSalutation5);
		merchant.setOwnerName5(ownerName5);
		merchant.setPassportNo5(passportNo5);
		merchant.setOwnerContactNo5(ownerContactNo5);
		merchant.setResidentialAddress5(residentialAddress5);
		merchant.setPreAuth(preAuth);
		merchant.setAuth3DS(auth3DS);
		merchant.setAutoSettled(autoSettled);
		merchant.setForeignCard(foreignCard);
		merchant.setStatus(status);
		logger.info("EditMerchant Status is:" + status);

		merchant.setEzysettle(ezysettle);

		merchant.setUsername(currentMerchant.getUsername());

		merchant.setOwnerCount(ownerCount);
		merchant.setMdr(mdr);
		merchant.setAccType(accType);
		if (formFName != null) {
			merchant.setFormFName(formFName);
		}
		if (docFName != null) {
			merchant.setDocFName(docFName);
		}
		if (payFName != null) {
			merchant.setPayFName(payFName);
		}
		BaseDataImpl baseData = new BaseDataImpl();
		RegAddMerchant a = baseData.vaildated(merchant);
		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");
			return "redirect:/admmerchant/edit/" + merchant.getId() + "";
		}
		/*
		 * if(merchant1 != null) { pageBean = new PageBean("Merchant user Edit Details",
		 * "merchant/edit/merchantseditdetails", Module.MERCHANT,
		 * "merchant/sideMenuMerchant");
		 * responseDataOfficeEmail=" Office Email already exist";
		 * model.addAttribute("merchant",merchant); String id1=merchant.getId(); return
		 * "redirect:/admmerchant/edit/"+id1; }
		 */
		String fileIds = null;
		if (formFile != null) {
			FileOutputStream fos = null;
			BufferedOutputStream stream = null;
			byte[] bytes = null;
			String dirData = null;
			String fileData = null;
			try {
				bytes = formFile.getBytes();
				// Creating the directory to store file
				// String rootPath = System.getProperty("catalina.home");
				Date fileid = new Date();
				String fileId = new SimpleDateFormat("yyyyMMddhhmmss").format(fileid);
				String adminName = principal.getName();
				Date dt = new Date();
				String date = new SimpleDateFormat("yyyyMMdd").format(dt);
				String rootPath = PropertyLoader.getFile().getProperty("ADMINPATH");
				// File dir = new File(rootPath + File.separator +
				// adminName+ File.separator+ merchant.getBusinessName()+
				// File.separator+date);
				dirData = rootPath + File.separator + merchant.getBusinessName() + File.separator + date;
				File dir = new File(dirData);
				if (!dir.exists())
					dir.mkdirs();
				// Create the file on server
				fileData = dir.getAbsolutePath() + File.separator + formFile.getOriginalFilename();
				File serverFile = new File(fileData);
				fos = new FileOutputStream(serverFile);
				stream = new BufferedOutputStream(fos);
				stream.write(bytes);
				stream.close();
				FileUpload fileUpload = new FileUpload();
				fileUpload.setFileId(fileId);
				fileUpload.setFileName(formFile.getOriginalFilename());
				fileUpload.setFilePath(serverFile.getAbsolutePath());
				fileUpload.setMerchantId("");
				fileUpload.setMerchantName(merchant.getBusinessName());
				fileUpload.setCreatedBy(adminName);
				fileUpload.setCreatedDate(new Date());
				FileUpload fileUpload1 = merchantService.storeFileUpload(fileUpload);
				merchant.setFormFName(formFile.getOriginalFilename());
				logger.info("Server File Location=" + formFile.getContentType());
				logger.info("Server File Location=" + formFile.getOriginalFilename());
				logger.info("Server File Location=" + serverFile.getAbsolutePath());
				logger.info("Server File Location=" + fileUpload1.getId());
				// regAddMerchant.setFileId(fileUpload1.getId().toString());
				fileIds = fileUpload1.getId().toString();
				// return "You successfully uploaded file=" +
				// regAddMerchant.getFile().getName();
			} catch (Exception e) {
				// return "You failed to upload " +
				// regAddMerchant.getFile().getName() + " => " +
				// e.getMessage();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (Exception e) {
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (Exception e) {
					}
				}
			}
		} /*
			 * else { return "You failed to upload " + regAddMerchant.getFile().getName() +
			 * " because the file was empty."; }
			 */
		// end Form File
		// start doc File
		if (docFile != null) {
			FileOutputStream fos = null;
			BufferedOutputStream stream = null;
			byte[] bytes = null;
			String dirData = null;
			String fileData = null;
			try {
				bytes = docFile.getBytes();
				// Creating the directory to store file
				// String rootPath = System.getProperty("catalina.home");
				Date fileid = new Date();
				String fileId = new SimpleDateFormat("yyyyMMddhhmmss").format(fileid);
				String adminName = principal.getName();
				Date dt = new Date();
				String date = new SimpleDateFormat("yyyyMMdd").format(dt);
				String rootPath = PropertyLoader.getFile().getProperty("ADMINPATH");
				// File dir = new File(rootPath + File.separator +
				// adminName+ File.separator+ merchant.getBusinessName()+
				// File.separator+date);
				dirData = rootPath + File.separator + merchant.getBusinessName() + File.separator + date;
				File dir = new File(dirData);
				if (!dir.exists())
					dir.mkdirs();
				// Create the file on server
				fileData = dir.getAbsolutePath() + File.separator + docFile.getOriginalFilename();
				File serverFile = new File(fileData);
				fos = new FileOutputStream(serverFile);
				stream = new BufferedOutputStream(fos);
				stream.write(bytes);
				stream.close();
				FileUpload fileUpload = new FileUpload();
				fileUpload.setFileId(fileId);
				fileUpload.setFileName(docFile.getOriginalFilename());
				fileUpload.setFilePath(serverFile.getAbsolutePath());
				fileUpload.setMerchantId("");
				fileUpload.setMerchantName(merchant.getBusinessName());
				fileUpload.setCreatedBy(adminName);
				fileUpload.setCreatedDate(new Date());
				FileUpload fileUpload1 = merchantService.storeFileUpload(fileUpload);
				merchant.setDocFName(docFile.getOriginalFilename());
				logger.info("Server File Location=" + docFile.getContentType());
				logger.info("Server File Location=" + docFile.getOriginalFilename());
				logger.info("Server File Location=" + serverFile.getAbsolutePath());
				logger.info("Server File Location=" + fileUpload1.getId());
				// regAddMerchant.setFileId(fileUpload1.getId().toString());
				if (fileIds != null) {
					fileIds = fileIds + "~" + fileUpload1.getId().toString();
				} else {
					fileIds = fileUpload1.getId().toString();
				}
				// return "You successfully uploaded file=" +
				// regAddMerchant.getFile().getName();
			} catch (Exception e) {
				// return "You failed to upload " +
				// regAddMerchant.getFile().getName() + " => " +
				// e.getMessage();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (Exception e) {
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (Exception e) {
					}
				}
			}
		} /*
			 * else { return "You failed to upload " + regAddMerchant.getFile().getName() +
			 * " because the file was empty."; }
			 */
		// end doc file
		// start pay File
		if (payFile != null) {
			FileOutputStream fos = null;
			BufferedOutputStream stream = null;
			byte[] bytes = null;
			String dirData = null;
			String fileData = null;
			try {
				bytes = payFile.getBytes();
				// Creating the directory to store file
				// String rootPath = System.getProperty("catalina.home");
				Date fileid = new Date();
				String fileId = new SimpleDateFormat("yyyyMMddhhmmss").format(fileid);
				String adminName = principal.getName();
				Date dt = new Date();
				String date = new SimpleDateFormat("yyyyMMdd").format(dt);
				String rootPath = PropertyLoader.getFile().getProperty("ADMINPATH");
				// File dir = new File(rootPath + File.separator +
				// adminName+ File.separator+ merchant.getBusinessName()+
				// File.separator+date);
				dirData = rootPath + File.separator + merchant.getBusinessName() + File.separator + date;
				File dir = new File(dirData);
				if (!dir.exists())
					dir.mkdirs();
				// Create the file on server
				fileData = dir.getAbsolutePath() + File.separator + payFile.getOriginalFilename();
				File serverFile = new File(fileData);
				fos = new FileOutputStream(serverFile);
				stream = new BufferedOutputStream(fos);
				stream.write(bytes);
				stream.close();
				FileUpload fileUpload = new FileUpload();
				fileUpload.setFileId(fileId);
				fileUpload.setFileName(payFile.getOriginalFilename());
				fileUpload.setFilePath(serverFile.getAbsolutePath());
				fileUpload.setMerchantId("");
				fileUpload.setMerchantName(merchant.getBusinessName());
				fileUpload.setCreatedBy(adminName);
				fileUpload.setCreatedDate(new Date());
				FileUpload fileUpload1 = merchantService.storeFileUpload(fileUpload);
				merchant.setPayFName(payFile.getOriginalFilename());
				logger.info("Server File Location=" + payFile.getContentType());
				logger.info("Server File Location=" + payFile.getOriginalFilename());
				logger.info("Server File Location=" + serverFile.getAbsolutePath());
				logger.info("Server File Location=" + fileUpload1.getId());
				// regAddMerchant.setFileId(fileUpload1.getId().toString());
				if (fileIds != null) {
					fileIds = fileIds + "~" + fileUpload1.getId().toString();
				} else {
					fileIds = fileUpload1.getId().toString();
				}
				// return "You successfully uploaded file=" +
				// regAddMerchant.getFile().getName();
			} catch (Exception e) {
				// return "You failed to upload " +
				// regAddMerchant.getFile().getName() + " => " +
				// e.getMessage();
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (Exception e) {
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (Exception e) {
					}
				}
			}
		}
		if (fileIds != null) {
			merchant.setFileId(fileIds);
		}
		model.addAttribute("responseData", null);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("adminusername", adminusername);
		model.addAttribute("manualSettlement", manualSettlement);
		// PCI
		request.getSession(true).setAttribute("editMerchantSession", merchant);
		if(merchantInfo != null) {
			request.getSession(true).setAttribute("editMerchantInfoSession", merchantInfo);
		}
		return "redirect:/admmerchant/editReviewandConfirm";
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public String displayEditMerchant(final Model model, @PathVariable final Long id,
			@RequestParam(value = "manualSettlement", required = false) String manualSettlement,
			final HttpServletRequest request, final java.security.Principal principal) {
		// logger.info("Request to display merchant edit");
		PageBean pageBean = new PageBean("Merchant Detail", "merchant/edit/merchantseditdetails", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		String adminusername = principal.getName();

		logger.info("Display Admin Login Name: " + adminusername);
		logger.info("About to display Update Merchant by ID:" + id);

		List<Agent> agent1 = agentService.loadAgent();

		logger.info("Total Number of Agents:" + agent1.size());

		Set<String> agentNameList = new HashSet<String>();
		for (Agent t : agent1) {
			String firstName = t.getFirstName();
			String mailId = t.getUsername();
			agentNameList.add("AGENT~" + firstName.toString() + "~" + mailId);
		}
		List<SubAgent> subAgent = subAgentService.loadSubAgent();

		logger.info("Total Number of Sub-Agents:" + agent1.size());
		for (SubAgent t : subAgent) {
			String firstName = t.getName();
			String mailId = t.getMailId();
			agentNameList.add("SUBAGENT~" + firstName.toString() + "~" + mailId);
		}
		// logger.info("Request to display merchant edit 123");
		Merchant merchant = merchantService.loadMerchantByPk(id);
		MerchantInfo merchantInfo = merchantService.loadMerchantInfoByFk(String.valueOf(id));
		logger.info("Merchant Details: " + "Merchant Type: " + merchant.getMerchantType() + ", Account Type: "
				+ merchant.getAccType() + ", MID: { MID: " + merchant.getMid().getMid() + ", Moto MID: "
				+ merchant.getMid().getMotoMid() + ", Ezypass MID: " + merchant.getMid().getEzypassMid()
				+ ", Ezyway MID: " + merchant.getMid().getEzywayMid() + ", Ezyrec MID: "
				+ merchant.getMid().getEzyrecMid() + " }, UMobile MID: { UM MID: " + merchant.getMid().getUmMid()
				+ ", UM Moto MID: " + merchant.getMid().getUmMotoMid() + ", UM Ezypass MID: "
				+ merchant.getMid().getUmEzypassMid() + ", UM Ezyway MID: " + merchant.getMid().getUmEzywayMid()
				+ ", UM Ezyrec MID: " + merchant.getMid().getUmEzyrecMid() + " }, Ewallet MID: { FPX MID: "
				+ merchant.getMid().getFpxMid() + ", Boost MID: " + merchant.getMid().getBoostMid() + ", Fiuu MID: "+ merchant.getMid().getFiuuMid());

//		logger.info("Merchant Type: " + merchant.getMerchantType());
//		logger.info("Account Type: " + merchant.getAccType());
//		logger.info("Paydee MID :");
//		logger.info("mid : " + merchant.getMid().getMid());
//		logger.info("ezymotomid : " + merchant.getMid().getMotoMid());
//		logger.info("ezypassmid : " + merchant.getMid().getEzypassMid());
//		logger.info("ezywaymid  : " + merchant.getMid().getEzywayMid());
//		logger.info("ezyrecmid  : " + merchant.getMid().getEzyrecMid());
//		logger.info("UMobile MID :");
//		logger.info("umMid : " + merchant.getMid().getUmMid());
//		logger.info("umMotoMid : " + merchant.getMid().getUmMotoMid());
//		logger.info("umEzypassMid : " + merchant.getMid().getUmEzypassMid());
//		logger.info("umEzywayMid  : " + merchant.getMid().getUmEzywayMid());
//		logger.info("umEzyrecMid  : " + merchant.getMid().getUmEzyrecMid());
//
//		logger.info("Ewallet MID :");
//		logger.info("fpx : " + merchant.getMid().getFpxMid());
//		logger.info("boost : " + merchant.getMid().getBoostMid());

		RegAddMerchant regMerchantDTO = new RegAddMerchant();
		regMerchantDTO.setId(merchant.getId().toString());
		// merchant.setMerchantName(merchantName);
		if (merchant.getMid() != null) {
			regMerchantDTO.setMid(merchant.getMid().getMid());
		}
		if (merchant.getMid().getEzypassMid() != null) {
			regMerchantDTO.setEzypassmid(merchant.getMid().getEzypassMid());
		}
		if (merchant.getMid().getMotoMid() != null) {
			regMerchantDTO.setEzymotomid(merchant.getMid().getMotoMid());
		}
		if (merchant.getMid().getEzywayMid() != null) {
			regMerchantDTO.setEzywaymid(merchant.getMid().getEzywayMid());
		}
		if (merchant.getMid().getEzyrecMid() != null) {
			regMerchantDTO.setEzyrecmid(merchant.getMid().getEzyrecMid());
		}
		// umobile mid
		if (merchant.getMid().getUmMid() != null) {
			regMerchantDTO.setUmMid(merchant.getMid().getUmMid());
		}
		if (merchant.getMid().getUmEzypassMid() != null) {
			regMerchantDTO.setUmEzypassMid(merchant.getMid().getUmEzypassMid());
		}
		if (merchant.getMid().getUmMotoMid() != null) {
			regMerchantDTO.setUmMotoMid(merchant.getMid().getUmMotoMid());
		}
		if (merchant.getMid().getUmEzywayMid() != null) {
			regMerchantDTO.setUmEzywayMid(merchant.getMid().getUmEzywayMid());
		}
		if (merchant.getMid().getUmEzyrecMid() != null) {
			regMerchantDTO.setUmEzyrecMid(merchant.getMid().getUmEzyrecMid());
		}

		// Umobile
		if (merchant.getMid().getFpxMid() != null) {
			logger.info("inside fpx mid " + merchant.getMid().getFpxMid());
			regMerchantDTO.setFpxMid(merchant.getMid().getFpxMid());
		}
		if (merchant.getMid().getBoostMid() != null) {
			logger.info("inside boost mid " + merchant.getMid().getBoostMid());
			regMerchantDTO.setBoostMid(merchant.getMid().getBoostMid());
		}
		
		//fiuu Mid
		if (merchant.getMid().getFiuuMid() != null) {
			logger.info("inside fiuu mid " + merchant.getMid().getFiuuMid());
			regMerchantDTO.setFiuuMid(merchant.getMid().getFiuuMid());
		}
		
		// ram.setMid(merchant.getMid().getMid());
		regMerchantDTO.setRegisteredName(merchant.getBusinessShortName());
		regMerchantDTO.setBusinessName(merchant.getBusinessName());
		regMerchantDTO.setBusinessRegNo(merchant.getBusinessRegistrationNumber());
		regMerchantDTO.setRegisteredAddress(merchant.getBusinessAddress1());
		regMerchantDTO.setBusinessAddress(merchant.getBusinessAddress2());
		regMerchantDTO.setMailingAddress(merchant.getBusinessAddress3());
		regMerchantDTO.setSalutation(merchant.getSalutation());
		regMerchantDTO.setName(merchant.getContactPersonName());
		regMerchantDTO.setContactNo(merchant.getContactPersonPhoneNo());
		regMerchantDTO.setEmail(merchant.getEmail());
		if(merchantInfo !=null) {
			regMerchantDTO.setSecoundaryEmail(merchantInfo.getSecoundaryEmail());
		}
		regMerchantDTO.setMerchantType(merchant.getMerchantType());
		regMerchantDTO.setAccType(merchant.getAccType());
		if (merchant.getOwnerSalutation() != null) {
			if (merchant.getOwnerSalutation().contains("~")) {
				String sul[] = merchant.getOwnerSalutation().split("~");
				if (sul.length == 2) {
					regMerchantDTO.setOwnerSalutation1(sul[0]);
					regMerchantDTO.setOwnerSalutation2(sul[1]);
				} else if (sul.length == 3) {
					regMerchantDTO.setOwnerSalutation1(sul[0]);
					regMerchantDTO.setOwnerSalutation2(sul[1]);
					regMerchantDTO.setOwnerSalutation3(sul[2]);
				} else if (sul.length == 4) {
					regMerchantDTO.setOwnerSalutation1(sul[0]);
					regMerchantDTO.setOwnerSalutation2(sul[1]);
					regMerchantDTO.setOwnerSalutation3(sul[2]);
					regMerchantDTO.setOwnerSalutation4(sul[3]);
				} else if (sul.length == 5) {
					regMerchantDTO.setOwnerSalutation1(sul[0]);
					regMerchantDTO.setOwnerSalutation2(sul[1]);
					regMerchantDTO.setOwnerSalutation3(sul[2]);
					regMerchantDTO.setOwnerSalutation4(sul[3]);
					regMerchantDTO.setOwnerSalutation5(sul[4]);
				}
			} else {
				regMerchantDTO.setOwnerSalutation1(merchant.getOwnerSalutation());
			}
		}
		if (merchant.getOwnerName() != null) {
			if (merchant.getOwnerName().contains("~")) {
				String name[] = merchant.getOwnerName().split("~");
				regMerchantDTO.setOwnerCount("" + name.length);
				if (name.length == 2) {
					regMerchantDTO.setOwnerName1(name[0]);
					regMerchantDTO.setOwnerName2(name[1]);
				} else if (name.length == 3) {
					regMerchantDTO.setOwnerName1(name[0]);
					regMerchantDTO.setOwnerName2(name[1]);
					regMerchantDTO.setOwnerName3(name[2]);
				} else if (name.length == 4) {
					regMerchantDTO.setOwnerName1(name[0]);
					regMerchantDTO.setOwnerName2(name[1]);
					regMerchantDTO.setOwnerName3(name[2]);
					regMerchantDTO.setOwnerName4(name[3]);
				} else if (name.length == 5) {
					regMerchantDTO.setOwnerName1(name[0]);
					regMerchantDTO.setOwnerName2(name[1]);
					regMerchantDTO.setOwnerName3(name[2]);
					regMerchantDTO.setOwnerName4(name[3]);
					regMerchantDTO.setOwnerName5(name[4]);
				}
			} else {
				regMerchantDTO.setOwnerCount("1");
				regMerchantDTO.setOwnerName1(merchant.getOwnerName());
			}
		}
		if (merchant.getOwnerPassportNo() != null) {
			if (merchant.getOwnerPassportNo().contains("~")) {
				String pass[] = merchant.getOwnerPassportNo().split("~");
				if (pass.length == 2) {
					regMerchantDTO.setPassportNo1(pass[0]);
					regMerchantDTO.setPassportNo2(pass[1]);
				} else if (pass.length == 3) {
					regMerchantDTO.setPassportNo1(pass[0]);
					regMerchantDTO.setPassportNo2(pass[1]);
					regMerchantDTO.setPassportNo3(pass[2]);
				} else if (pass.length == 4) {
					regMerchantDTO.setPassportNo1(pass[0]);
					regMerchantDTO.setPassportNo2(pass[1]);
					regMerchantDTO.setPassportNo3(pass[2]);
					regMerchantDTO.setPassportNo4(pass[3]);
				} else if (pass.length == 5) {
					regMerchantDTO.setPassportNo1(pass[0]);
					regMerchantDTO.setPassportNo2(pass[1]);
					regMerchantDTO.setPassportNo3(pass[2]);
					regMerchantDTO.setPassportNo4(pass[3]);
					regMerchantDTO.setPassportNo5(pass[4]);
				}
			} else {
				regMerchantDTO.setPassportNo1(merchant.getOwnerPassportNo());
			}
		}
		if (merchant.getResidentialAddress() != null) {
			if (merchant.getResidentialAddress().contains("~")) {
				String addr[] = merchant.getResidentialAddress().split("~");
				if (addr.length == 2) {
					regMerchantDTO.setResidentialAddress1(addr[0]);
					regMerchantDTO.setResidentialAddress2(addr[1]);
				} else if (addr.length == 3) {
					regMerchantDTO.setResidentialAddress1(addr[0]);
					regMerchantDTO.setResidentialAddress2(addr[1]);
					regMerchantDTO.setResidentialAddress3(addr[2]);
				} else if (addr.length == 4) {
					regMerchantDTO.setResidentialAddress1(addr[0]);
					regMerchantDTO.setResidentialAddress2(addr[1]);
					regMerchantDTO.setResidentialAddress3(addr[2]);
					regMerchantDTO.setResidentialAddress4(addr[3]);
				} else if (addr.length == 5) {
					regMerchantDTO.setResidentialAddress1(addr[0]);
					regMerchantDTO.setResidentialAddress2(addr[1]);
					regMerchantDTO.setResidentialAddress3(addr[2]);
					regMerchantDTO.setResidentialAddress4(addr[3]);
					regMerchantDTO.setResidentialAddress5(addr[4]);
				}
			} else {
				regMerchantDTO.setResidentialAddress1(merchant.getResidentialAddress());
			}
		}
		if (merchant.getOwnerContactNo() != null) {
			if (merchant.getOwnerContactNo().contains("~")) {
				String contact[] = merchant.getOwnerContactNo().split("~");
				if (contact.length == 2) {
					regMerchantDTO.setOwnerContactNo1(contact[0]);
					regMerchantDTO.setOwnerContactNo2(contact[1]);
				} else if (contact.length == 3) {
					regMerchantDTO.setOwnerContactNo1(contact[0]);
					regMerchantDTO.setOwnerContactNo2(contact[1]);
					regMerchantDTO.setOwnerContactNo3(contact[2]);
				} else if (contact.length == 4) {
					regMerchantDTO.setOwnerContactNo1(contact[0]);
					regMerchantDTO.setOwnerContactNo2(contact[1]);
					regMerchantDTO.setOwnerContactNo3(contact[2]);
					regMerchantDTO.setOwnerContactNo4(contact[3]);
				} else if (contact.length == 5) {
					regMerchantDTO.setOwnerContactNo1(contact[0]);
					regMerchantDTO.setOwnerContactNo2(contact[1]);
					regMerchantDTO.setOwnerContactNo3(contact[2]);
					regMerchantDTO.setOwnerContactNo4(contact[3]);
					regMerchantDTO.setOwnerContactNo5(contact[4]);
				}
			} else {
				regMerchantDTO.setOwnerContactNo1(merchant.getOwnerContactNo());
			}
		}
		regMerchantDTO.setOfficeEmail(merchant.getUsername());
		regMerchantDTO.setWebsite(merchant.getWebsite());
		regMerchantDTO.setOfficeNo(merchant.getBusinessContactNumber());
		regMerchantDTO.setFaxNo(merchant.getFaxNo());
		regMerchantDTO.setBusinessType(merchant.getBusinessType());
		regMerchantDTO.setCompanyType(merchant.getCompanyType());
		regMerchantDTO.setNatureOfBusiness(merchant.getNatureOfBusiness());
		regMerchantDTO.setBankName(merchant.getBankName());
		regMerchantDTO.setBankAccNo(merchant.getBankAcc());
		regMerchantDTO.setDocuments(merchant.getPermiseType());
		regMerchantDTO.setBusinessState(merchant.getState());
		regMerchantDTO.setBusinessCity(merchant.getCity());
		regMerchantDTO.setBusinessPostCode(merchant.getPostcode());
		regMerchantDTO.setReferralId(merchant.getReferralId());
		regMerchantDTO.setWavierMonth(merchant.getWaiverMonth());
		regMerchantDTO.setTradingName(merchant.getTradingName());
		regMerchantDTO.setNoOfReaders(merchant.getReaderSerialNo());
		regMerchantDTO.setYearIncorporated(merchant.getYearIncorporated());
		regMerchantDTO.setSignedPackage(merchant.getSignedPackage());
		regMerchantDTO.setStatusRemarks(merchant.getRemarks());
		String status = merchant.getStatus().toString();
		if (merchant.getStatus() != null) {
			regMerchantDTO.setStatus(merchant.getStatus().toString());
		}
		String merchId = merchant.getId().toString();
		logger.info("test merch id: " + merchId);
		List<FileUpload> listFile = merchantService.loadFileDet(merchId);
		int i = 0;
		for (FileUpload fu : listFile) {
			if (i == 0) {
				regMerchantDTO.setFormFName(fu.getFileName());
				// ram.setFileId
				i++;
			} else if (i == 1) {
				regMerchantDTO.setDocFName(fu.getFileName());
				i++;
			} else if (i == 2) {
				regMerchantDTO.setPayFName(fu.getFileName());
				i++;
			}
		}
		String agentName = "";
		if (merchant.getAgID() != null) {
			Agent agent = agentService.loadAgentByPk(Long.parseLong(merchant.getAgID().toString()));
			if (agent != null) {
				agentName = "AGENT~" + agent.getFirstName() + "~" + agent.getUsername();
			}
		}
		if (merchant.getSubAgID() != null) {
			SubAgent subAgent1 = subAgentService.loadSubAgentByPk(Long.parseLong(merchant.getSubAgID().toString()));
			if (subAgent1 != null) {
				agentName = "SUBAGENT~" + subAgent1.getName() + "~" + subAgent1.getMailId();
			}
		}
		/*
		 * logger.info("edit merchant: " + regMerchantDTO.getMid() + " : " +
		 * regMerchantDTO.getEzymotomid() + " : " + regMerchantDTO.getEzypassmid());
		 */
		regMerchantDTO.setAgentName(agentName);
		regMerchantDTO.setPreAuth(merchant.getPreAuth());
		if (merchant.getAuth3DS() != null) {
			regMerchantDTO.setAuth3DS(merchant.getAuth3DS());
		} else {
			regMerchantDTO.setAuth3DS("No");
		}
		regMerchantDTO.setAutoSettled(merchant.getAutoSettled());
		regMerchantDTO.setMdr(merchant.getMdr());
		regMerchantDTO.setForeignCard(merchant.getForeignCard());
		regMerchantDTO.setUsername(merchant.getUsername());

		/*
		 * logger.info("auth 3ds check: "+regMerchantDTO.getAuth3DS()+ " autoSettled: "
		 * +regMerchantDTO.getAutoSettled()+" preauth: "+regMerchantDTO.getPreAuth());
		 */

		logger.info("modified testing >>>>>>> ");
		regMerchantDTO.setEzysettle(merchant.getModifiedBy());

		logger.info("signedpackage test: " + regMerchantDTO.getSignedPackage());
		logger.info("signedpackage test: " + merchant.getSignedPackage());
		String err = (String) request.getSession(true).getAttribute("editErSession");
		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {
				logger.info("err::::::" + err);
				model.addAttribute("responseDataError", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("editErSession");
			}
		}

		// Async Payout Changes
		MobileUser asyncPayoutDetails = mobileuserDAO.getMobileUserData(merchant);

		String isAsyncPayoutEnabled = Optional.ofNullable(asyncPayoutDetails.getEnableAsyncPayout())
				.filter(s -> !s.trim().isEmpty()).map(String::toUpperCase).orElse("NO");
		String payoutIpnUrl = Optional.ofNullable(asyncPayoutDetails.getPayoutNotificationUrl())
				.filter(s -> !s.trim().isEmpty()).orElse("");
		String asyncEnableReason = Optional.ofNullable(asyncPayoutDetails.getEnableAsyncPayoutReason())
				.filter(s -> !s.trim().isEmpty()).orElse("");

		//For enable account enquiry & quick payout
		String isAccountEnquiryEnabled = Optional.ofNullable(asyncPayoutDetails.getEnableAccEnq())
				.filter(s -> !s.trim().isEmpty()).map(String::toUpperCase).orElse("NO");
		String isQuickPayoutEnabled = Optional.ofNullable(asyncPayoutDetails.getEnableQuickPayout())
				.filter(s -> !s.trim().isEmpty()).map(String::toUpperCase).orElse("NO");
//		String quickPayoutUrl = Optional.ofNullable(asyncPayoutDetails.getQuickPayoutUrl())
//				.filter(s -> !s.trim().isEmpty()) // Retain only non-empty strings
//				.orElse("");


		regMerchantDTO.setIsAsyncPayoutEnabled(isAsyncPayoutEnabled);
		regMerchantDTO.setPayoutIpnUrl(payoutIpnUrl);
		regMerchantDTO.setAsyncEnableReason(asyncEnableReason);
		regMerchantDTO.setIsAccountEnquiryEnabled(isAccountEnquiryEnabled);
		regMerchantDTO.setIsQuickPayoutEnabled(isQuickPayoutEnabled);
//		regMerchantDTO.setQuickPayoutUrl(quickPayoutUrl);


		// isMaxPayoutLimitSet changes
		String maxPayoutLimit = (asyncPayoutDetails != null && asyncPayoutDetails.getPayoutTransactionLimit() != null
				&& asyncPayoutDetails.getPayoutTransactionLimit().compareTo(BigDecimal.ZERO) > 0)
						? asyncPayoutDetails.getPayoutTransactionLimit().toString()
						: "0.0";
		regMerchantDTO.setMaxPayoutTxnLimit(maxPayoutLimit);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", regMerchantDTO);
		model.addAttribute("merchantInfo", merchantInfo);
		model.addAttribute("agentNameList", agentNameList);

		model.addAttribute("adminusername", adminusername);
		model.addAttribute("stateList", AddMerchantOptionList.stateList());
		model.addAttribute("documentsList", AddMerchantOptionList.documentsList());
		model.addAttribute("CategoryList", AddMerchantOptionList.CategoryList());
		model.addAttribute("natureOfBusinessList", AddMerchantOptionList.natureOfBusinessList());
		model.addAttribute("manualSettlement", manualSettlement);
		request.getSession(true).setAttribute("editMerchantSession", regMerchantDTO);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/dtl" }, method = RequestMethod.GET)
	public String dtl(final Model model, final java.security.Principal principal, final HttpServletRequest request) {
		// logger.info("/finduserDetails: to ADD " + id);

		// Merchant midDetails = mobileUserService.loadIserMidDetails(id);
		List<UMMidTxnLimit> ummidtxn = merchantService.UmmidTxnlimit();
		Set<String> merchantmid = new HashSet<String>();
		for (UMMidTxnLimit u : ummidtxn) {
			String mid = u.getMid();
			logger.info("Mid :" + mid);
			merchantmid.add(mid);
		}
		String adminusername1 = principal.getName();
		PageBean pageBean = new PageBean("update DTL", "merchant/dtl", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
//        List<UmmidTxnlimit> ummidtxn1 = new UmmidTxnlimit<>;

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantmid", merchantmid);
		model.addAttribute("ummidtxn", ummidtxn);
		model.addAttribute("adminusername1", adminusername1);
		String err = (String) request.getSession(true).getAttribute("dtl");

		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {

				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("dtl");
			}
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/finduser" }, method = RequestMethod.GET)
	public String FindDtlUserDetails(final Model model,

			@RequestParam("id") Long id, final HttpServletRequest request, final java.security.Principal principal) {

		logger.info("/findDtlDetails: " + id);
		PageBean pageBean = new PageBean("AddMobileUser", "merchant/dtl", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		UMMidTxnLimit midDetails = null;
		RegMobileUser regMobileUser = new RegMobileUser();
		if (id != null) {

			midDetails = mobileUserService.loadDtlMidDetails(id);
		}

		List<UMMidTxnLimit> ummidtxn = merchantService.UmmidTxnlimit();

//    List<UMMidTxnLimit> mobileusersList = mobileUserService.loadMobileUsersbyMerchantmid(id.toString());

		regMobileUser.setMid(midDetails.getMid());
		regMobileUser.setDTL(midDetails.getDtl());

		String err = (String) request.getSession(true).getAttribute("editMobiMdrErSession");

		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {

				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("editMobiMdrErSession");
			}
		}
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("ummidtxn", ummidtxn);
		model.addAttribute("midDetails", midDetails);

		model.addAttribute("mobileUser", regMobileUser);
//
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/updatedtl/{mid}" }, method = RequestMethod.POST)
	public String updateDtlDetails1(final Model model, @RequestParam("remarks") String remarks,
			@PathVariable final String mid, final HttpServletRequest request, final java.security.Principal principal) {

		logger.info("dtl" + remarks);
		UMMidTxnLimit midDetails = null;

		RegMobileUser regMobileUser = new RegMobileUser();

		System.out.println("null");

		regMobileUser.setDTL(remarks);
		regMobileUser.setMid(mid);

		System.out.println("getDTL2" + regMobileUser.getDTL());
//

//		API calling
		UpdateApi dtlapi = new UpdateApi();

		dtlapi.updateDtlDetails2(regMobileUser);
		// ------- *** update DTL *** -----------
		// midDetails = mobileUserService.Adddtl(regMobileUser);

		PageBean pageBean = new PageBean("AddDTL", "merchant/updatedtl1", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		model.addAttribute("mobileUser", regMobileUser);

		List<UMMidTxnLimit> ummidtxn = merchantService.UmmidTxnlimit();

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("midDetails", midDetails);
		request.getSession(true).setAttribute("addMDRDetailsSession", regMobileUser);

		return TEMPLATE_DEFAULT;
	}

}
