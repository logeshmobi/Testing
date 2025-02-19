package com.mobiversa.payment.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.BankUser;
import com.mobiversa.common.bo.BoostDailyRecon;
import com.mobiversa.common.bo.EwalletTxnDetails;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.GrabPayFile;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantInfo;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.payment.controller.bean.DateTime;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.TransactionDao;
import com.mobiversa.payment.dto.DashBoardData;
import com.mobiversa.payment.dto.MerchantPaymentDetailsDto;
import com.mobiversa.payment.dto.ReaderList;
import com.mobiversa.payment.dto.RegAddMerchant;
import com.mobiversa.payment.dto.ThreeMonthTxnData;
import com.mobiversa.payment.service.AccountEnquiryService;
import com.mobiversa.payment.service.AdminService;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.DashBoardService;
import com.mobiversa.payment.service.JenfiService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.NonMerchantService;
import com.mobiversa.payment.service.ReaderService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.DashboardAmount;
import com.mobiversa.payment.util.EzysettleUtils;
import com.mobiversa.payment.util.PayoutModel;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.SettlementModel;
import com.mobiversa.payment.util.Utils;

@Controller
@RequestMapping()
public class HomeController extends BaseController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private MobileUserService mobileUser;

	@Autowired
	private AgentService agentService;

	@Autowired
	private AccountEnquiryService accountEnquiryService;

	@Autowired
	private DashBoardService dashBoardService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private NonMerchantService nonmerchantService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private TransactionDao transactionDAO;

	@Autowired
	private ReaderService readerService;

	@Autowired
	JenfiService jenfiService;

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public String rootPage() {
		return "redirect:/auth/login";
	}

	@RequestMapping(value = { "/merchants/testPayout", "/merchants/testPayout*",
			"/merchants/testPayout**" }, method = RequestMethod.GET)
	public String viewPageMerchantWeb(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {
		logger.info("@@@@@ WELCOME TO TEST PAYOUT !!! @@@@@ Login Person :" + principal.getName());

		PageBean pageBean = new PageBean("HOME AGENT_WEB", "PayoutUser/TestPayout/PayoutRequest", Module.HOME_WEB);

		String mobiApiKey = "b07ad9f31df158edb188a41f725899bc";
		String service = "PAYOUT_TXN_REQ";
		String businessRegNo = "8754648217513";
		String bankName = "Ambank Malaysia Berhad";
		String bicCode = "ARBKMYKL";
		String bankAccNo = "8881048358879";
		String subMid = "201100000012450";

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobiApiKey", mobiApiKey);
		model.addAttribute("service", service);
		model.addAttribute("businessRegNo", businessRegNo);
		model.addAttribute("bankName", bankName);
		model.addAttribute("bicCode", bicCode);
		model.addAttribute("bankAccNo", bankAccNo);
		model.addAttribute("subMid", subMid);

		return TEMPLATE_TEST_PAYOUT;
	}

	@RequestMapping(value = { "/bank/user", "/bank/user*", "/bank/user**" }, method = RequestMethod.GET)
	public String defaultPage(final Model model, final java.security.Principal principal, HttpServletRequest request) {
		logger.info("@@@@@ WELCOME TO MOBIVERSA ADMIN PORTAL !!! @@@@@");
		PageBean pageBean = new PageBean("Dash Board", "dashboard/dashbrd", Module.ADMIN, "admin/sideMenuBankUser");

		HttpSession session = request.getSession();
		// logger.info("check dashboard details:");

		/*
		 * logger.info("redirecting user to login screen from:" +
		 * request.getRequestURI());
		 * 
		 * logger.info("current session id: in /login \n"+session.getId());
		 */

//		String captcha1 = null;

//		String captcha1 = (String) session.getAttribute("txtCompare1");
//		String captcha2 = (String) session.getAttribute("txtCompare2");

//		logger.info("txtCompare1 :::::::::login:::::::::"+ captcha1);
//		logger.info("txtCompare2 :::::::::login:::::::::"+ captcha2);

//		if ((captcha1 != null && captcha2 != null) && (!captcha1.isEmpty() && !captcha2.isEmpty()))
//
//		{
//			logger.info("if test 01:::::::::");
//			
//			if (captcha1.equals(captcha2)) {	
//				
//				session.removeAttribute("txtCompare1");
//				session.removeAttribute("txtCompare2");
//				
//				logger.info("in test 02:::::::::equal");
//
//			}else {
//				logger.info("in test 02:::::::::not equal");
//				
//				
//				session.removeAttribute("txtCompare1");
//				session.removeAttribute("txtCompare2");
//				
//				return "redirect:/auth/login/error";
//			}
//			
//		}else {
//			logger.info("else test 01:::::::::");
//			
//			
//			session.removeAttribute("txtCompare1");
//			session.removeAttribute("txtCompare2");
//			
//			return "redirect:/auth/login/error";
//		}
//		
//		session.removeAttribute("txtCompare1");
//		session.removeAttribute("txtCompare2");

		model.addAttribute("pageBean", pageBean);

		// Total Device

		// int totalDevice = dashBoardService.getTotalDevice();
		int totalDevice = 0;
		model.addAttribute("totalDevice", totalDevice);
		// logger.info("check total device:" + totalDevice );
		// Total transaction Current month

		// String totalTxn = dashBoardService.getCurrentMonthTxn();
		String totalTxn = null;
		logger.info("check total amount:" + totalTxn);
		model.addAttribute("totalTxn", totalTxn);
		/*
		 * totalTxn = "1,234,300.00"; logger.info("check total amount:" + totalTxn );
		 */
		// logger.info("check dashboard details:");

		PaginationBean<DashBoardData> paginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check 5 recent txn Amount Data ");
		// dashBoardService.getLastFiveTxn(paginationBean);

		logger.info(" received 5 recent  txn Amount Data ");
		model.addAttribute("fiveTxnList", paginationBean);

		// Total Merchant
		// String totalMerchant = dashBoardService.getTotalMerchant();
		String totalMerchant = null;
		// logger.info("check totalmerchant:" + totalMerchant);
		model.addAttribute("totalMerchant", totalMerchant);
		// logger.info("check dashboard details:");
		List<ThreeMonthTxnData> data = new ArrayList<ThreeMonthTxnData>();
		// new changes for bar chart 05072017
		// List<ThreeMonthTxnData> txnCountData = new ArrayList<ThreeMonthTxnData>();
		PaginationBean<ThreeMonthTxnData> paginationBean1 = new PaginationBean<ThreeMonthTxnData>();
		// paginationBean1.setCurrPage(currPage);
		// logger.info("check dashboard details787878:");
		List<ThreeMonthTxnData> txnListData = new ArrayList<ThreeMonthTxnData>();
		txnListData = dashBoardService.getThreeMonthTxn();
		// logger.info("check dashboard details457454:");

		// logger.info("check dashboard txn details:" + txnListData.size());
		// for(ThreeMonthTxnData txnMonthData: txnCountData){
		/*
		 * if(txnListData.size()<=0) {
		 * 
		 * 
		 * }
		 */
		for (ThreeMonthTxnData txnMonthData : txnListData) {

			// logger.info("check monthly count:" + txnMonthData );

			// logger.info("Controller agentDet : "+txnMonthData.getCount());
			// logger.info("Controller amount : "+txnMonthData.getAmount());
			// logger.info("Controller date : "+txnMonthData.getDate1());
			String a3 = "\"";
			for (String a1 : txnMonthData.getDate1()) {
				a3 = a3 + a1;
				a3 = a3 + "\",\"";
			}
			// System.out.println("Data : "+a3);
			a3 = a3 + "\"";
			// System.out.println("Data : "+a3);
			a3 = a3.replace(",\"\"", "");
			// System.out.println("Data : "+a3);
			txnMonthData.setMonth(a3);

			String b3 = "";
			for (String b1 : txnMonthData.getAmount()) {
				b3 = b3 + b1;
				b3 = b3 + ",";
			}
			// System.out.println("Data : "+b3);
			b3 = b3 + ",";
			// System.out.println("Data : "+b3);
			b3 = b3.replace(",,", "");
			// System.out.println("Data : "+b3);
			txnMonthData.setAmountData(b3);

			String c3 = "";
			for (String c1 : txnMonthData.getCount()) {
				c3 = c3 + c1;
				c3 = c3 + ",";
			}
			// System.out.println("Data : "+c3);
			c3 = c3 + ",";
			// System.out.println("Data : "+c3);
			c3 = c3.replace(",,", "");
			// System.out.println("Data : "+c3);
			txnMonthData.setCountData(c3);

			model.addAttribute("threeMonthTxn", txnMonthData);

			data.add(txnMonthData);

		}
		// End Agent

		String a1 = null, a2 = null, a3 = null;
		Float min = 0.0f, max = 0.0f;
		for (ThreeMonthTxnData t : txnListData) {
			logger.info(" check= Amount Data " + t.getAmountData());
			String[] amt = t.getAmountData().split(",");
			a1 = amt[0];
			a2 = amt[1];
			a3 = amt[2];
			Float[] a = { Float.valueOf(a1), Float.valueOf(a2), Float.valueOf(a3) };
			Arrays.sort(a);

			min = a[0]; // assume first elements as smallest number
			max = a[0]; // assume first elements as largest number
			for (int i = 1; i < a.length; i++) // iterate for loop from arrays 1st index (second element)
			{
				if (a[i] > max) {
					max = a[i];
				}
				if (a[i] < min) {
					min = a[i];
				}
			}

		}

		logger.info("min: " + max + " max: " + min);

		// Math.round(1004/1000)*1000
		int rounded = DashboardAmount.roundNum(Math.round(max));
		logger.info("rounded value : " + rounded);
		model.addAttribute("stepsize", rounded / 10);
		model.addAttribute("max", rounded);
		paginationBean1.setItemList(data);

		model.addAttribute("paginationBean", paginationBean1);
		AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(principal.getName(), principal.getName(), "login");

		if (auditTrail != null) {
			logger.info("Logged in by Admin : " + principal.getName());
		}

		// String threeMonthTxn = dashBoardService.getThreeMonthTxn();

		// model.addAttribute("month",threeMonthTxn);
		// model.addAttribute("", arg1)

		String[] accessibleUsers = PropertyLoad.getFile().getProperty("ACCESSIBLE_USERS").split(",");

		for (int i = 0; i < accessibleUsers.length; i++) {
			// logger.info("usernames: "+userName[i].toString());
		}
		List<String> accessibleUsersList = Arrays.asList(accessibleUsers);

		/*
		 * String[] Users= userNameList.toArray(new String[userNameList.size()]);
		 * 
		 * for(int i=0;i<Users.length;i++) { logger.info("Users: "+Users[i].toString());
		 * }
		 * 
		 * logger.info(userNameList.contains("adminuser"));
		 * 
		 */

		BankUser bankUser = adminService.loadBankUser(principal.getName());
		// session.setAttribute("adminName", bankUser.getFirstName());
		session.setAttribute("adminUserName", bankUser.getUsername());
		model.addAttribute("loginname", principal.getName());
		session.setAttribute("userName", bankUser.getUsername());
		session.setAttribute("userRole", "BANK_ADMIN");
		session.setAttribute("accessibleUsersList", accessibleUsersList);
		request.setAttribute("adminusernamelogout", bankUser.getUsername());
		return TEMPLATE_DEFAULT;
		/*
		 * PageBean pageBean = new PageBean("BANK HOME", "home/home", Module.HOME);
		 * model.addAttribute("pageBean", pageBean); return TEMPLATE_DEFAULT;
		 */
	}

	@RequestMapping(value = { "/merchant/merchantweb", "/merchant/merchantweb*",
			"/merchant/merchantweb**" }, method = RequestMethod.GET)
	public String defaultPageMerchantWeb(final Model model, final java.security.Principal principal,
			HttpServletRequest request, RegAddMerchant regAddMerchant) {
		model.addAttribute("isActualMerchant","no");
		logger.info("@@@@@ WELCOME TO MERCHANTWEB PORTAL !!! @@@@@ Login Person :" + principal.getName());

		HttpSession session = request.getSession();
		String userName = principal.getName();

		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}

		logger.info("User Name :" + userName);
		Merchant merchant = merchantService.loadMerchant(userName);
		MerchantInfo merchantInfo = merchantService.loadMerchantInfoByFk(String.valueOf(merchant.getId()));


		logger.info("is merchantInfo null " + (merchantInfo == null));
		if (!Objects.isNull(merchantInfo)){
			logger.info(merchantInfo.isJenfiEnabled()
					? String.format("Jenfi enabled for current merchant %s with ID %s", merchant.getBusinessName(), merchant.getId())
					: "Jenfi not enabled for the current merchant");
			model.addAttribute("isJenfiEnabled", jenfiService.checkJenfiEnabled(merchantInfo.isJenfiEnabled()));
		}
		String merchanttype = merchant.getMerchantType();
		logger.info("current merchant type : "+merchanttype);
// We used MODIFIED_BY field for checking Ezysettle is enable or not
		String checkIfSettle = merchant.getModifiedBy();
		logger.info("checkIfEzySettle===>" + checkIfSettle);
		String checkname = PropertyLoad.getFile().getProperty("DISABLE_EZYSETTLE");

		if (userName.equalsIgnoreCase(checkname)) {
			PageBean pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee", Module.HOME_WEB);
			model.addAttribute("pageBean", pageBean);
		} else {
			PageBean pageBean;
			/* Check if either merchanttype or checkIfSettle is null, empty, or blank */
			if (Utils.isNullOrEmpty(merchanttype) || Utils.isNullOrEmpty(checkIfSettle)) {
				pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee", Module.HOME_WEB);
			} else if ("Yes".equalsIgnoreCase(checkIfSettle) && ("u".equalsIgnoreCase(merchanttype) || "fiuu".equalsIgnoreCase(merchanttype))) {
				pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrd", Module.HOME_WEB);
			} else {
				pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee", Module.HOME_WEB);
			}
			model.addAttribute("pageBean", pageBean);
		}

		String EzypodCheck = "NO";
		MID mid = merchantService.loadMidByMerchant_PK((merchant.getId()).toString());
// for api key table start

		PaginationBean<ReaderList> paginationBean2 = new PaginationBean<ReaderList>();
		List<ReaderList> readerdata = new ArrayList<ReaderList>();
		readerdata = readerService.getReaderList(merchant);

		logger.info("after Reader Size : " + readerdata.size());
		paginationBean2.setItemList(readerdata);
		model.addAttribute("paginationBean2", paginationBean2);
// for api key table end
		String preauth = Optional.ofNullable(merchant.getPreAuth()).orElse("No");
		session.setAttribute("preAuth", preauth);
		logger.info("HomeController Merchant id: " + merchant.getId());

		String ezyLinkCheck = Optional.ofNullable(merchant.getAuth3DS())
				.map(auth3DS -> auth3DS.equalsIgnoreCase("Yes") ? "YES" : "NO").orElse("NO");

		List<MobileUser> mobileuser = mobileUser.loadMobileUserByFk(merchant.getId());

		String accountEnquiryFromMobileUser = accountEnquiryService.getEnableAccountEnquiry(merchant.getId());
		logger.info("Account enquiry enabled for merchant : " + accountEnquiryFromMobileUser);
		session.setAttribute("enableAccountEnquiry", accountEnquiryFromMobileUser);

		String eblBoost = mobileuser.stream().map(MobileUser::getEnableBoost).filter(Objects::nonNull)
				.map(String::toLowerCase).filter(s -> s.equalsIgnoreCase("Yes")).findFirst().orElse("No");
		session.setAttribute("enableBoost", eblBoost);

		String eblMoto = mobileuser.stream().map(MobileUser::getEnableMoto).filter(Objects::nonNull)
				.map(String::toLowerCase).filter(s -> s.equalsIgnoreCase("Yes")).findFirst().orElse("No");
		session.setAttribute("enableMoto", eblMoto);

//		checking for duitnow(enabled or disabled)
		List<MerchantPaymentDetailsDto> merchantPaymentDetailsDto = merchantService.loadMerchantPaymentDetailsByFk(merchant.getId());
		Optional<MerchantPaymentDetailsDto> optionalDetails = merchantPaymentDetailsDto.stream()
			    .filter(merchantPaymentDetails -> "true".equalsIgnoreCase(merchantPaymentDetails.getEnablePaymentMethod()))
			    .findFirst();

			if (optionalDetails.isPresent()) {
			    MerchantPaymentDetailsDto merchantPaymentDetails = optionalDetails.get();
			    String paymentMethod = merchantPaymentDetails.getPaymentMethod();
			    if ("PM-101".equals(paymentMethod)) {
			        logger.info("DuitNow QR is enabled for merchant id " + merchant.getId());
			        session.setAttribute("enableDuitnow", merchantPaymentDetails);
			    }
			} else {
			    logger.info("DuitNow QR is not enabled for merchant id " + merchant.getId());
			    session.setAttribute("enableDuitnow", null);
			}



// Check if the Merchant is Enable PreAuth or Not
		if (merchant.getPreAuth() == null) {
			merchant.setPreAuth("No");
		}
		String ezyAuthEnable = "No";

		if (merchant.getMid().getMotoMid() != null
				&& (merchant.getPreAuth().equals("Yes") || merchant.getPreAuth() == "Yes")) {
			ezyAuthEnable = "Yes";
		} else if (merchant.getMid().getUmMotoMid() != null
				&& (merchant.getPreAuth().equals("Yes") || merchant.getPreAuth() == "Yes")) {
			ezyAuthEnable = "Yes";
		}
		if (ezyAuthEnable.equalsIgnoreCase("Yes")) {
			session.setAttribute("ezyAuthEnable", "Yes");
		} else {
			session.setAttribute("ezyAuthEnable", "No");
		}

		session.setAttribute("merchantUserName", merchant.getBusinessName());

		String typename = PropertyLoad.getFile().getProperty("MERCHANT_TYPE");

		if (merchanttype != null && (merchanttype.equalsIgnoreCase(typename) || merchanttype.equalsIgnoreCase("FIUU"))) {

			if (checkIfSettle != null && !checkIfSettle.trim().isEmpty() && checkIfSettle.equalsIgnoreCase("Yes")) {

				Utils utils = new Utils();
				EzysettleUtils ezysettleUtils = new EzysettleUtils();
				DateTime mytDateAndTime = utils.currentMYT();

				// Service - To Check Holiday Given 5 dates
				List<String> finalSettlementDate = transactionService.tocheckholiday(mytDateAndTime.getDate(),
						mytDateAndTime.getTime());

				List<String> merchantMids = utils.loadMid(merchant);

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

//			/* Filter transactions for the current day: Do not use current day transactions for Ezysettle. */
//			finalSettlementDate = ezysettleUtils.filterCurrentDayTransactions(finalSettlementDate,mobiMdr,transactionDAO);

				String settlementdateone = finalSettlementDate.get(0);
				String settlementdatetwo = finalSettlementDate.get(1);
				String settlementdatethree = finalSettlementDate.get(2);
				String settlementdatefour = finalSettlementDate.get(3);
				String settlementdatefive = finalSettlementDate.get(4);

//	boolean executed = false;
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
										.loadNetAmountandsettlementdatebyCardEzysettle(settlementdate, merchant);

								String cardSettlementAmount = String.valueOf(getsettlementonedatacard == null ? 0.0
										: Double.parseDouble(getsettlementonedatacard.getNetAmount()));
								logger.info("Settlement Amount Card : " + cardSettlementAmount + "Settlement Date :"
										+ settlementdate);
								settledAmountsProductVise.merge(settlementdate, "Card " + cardSettlementAmount,
										(oldValue, newValue) -> oldValue + ", " + newValue);

								return cardSettlementAmount;
							}
						});

						callables.add(new Callable<String>() {
							public String call() throws Exception {
								BoostDailyRecon getsettlementonedataboost = transactionService
										.loadNetAmountandsettlementdatebyBoostEzysettle(settlementdate, merchant);

								String boostSettlementAmount = String.valueOf(getsettlementonedataboost == null ? 0.0
										: Double.parseDouble(getsettlementonedataboost.getNetAmount()));
								logger.info("Settlement Amount Boost : " + boostSettlementAmount + "Settlement Date :"
										+ settlementdate);
								settledAmountsProductVise.merge(settlementdate, "Boost  " + boostSettlementAmount,
										(oldValue, newValue) -> oldValue + ", " + newValue);

								return boostSettlementAmount;
							}
						});

						callables.add(new Callable<String>() {
							public String call() throws Exception {
								GrabPayFile getsettlementonedatagrabpay = transactionService
										.loadNetAmountandsettlementdatebyGrabpayEzysettle(settlementdate, merchant);

								String grabPaySettlementAmount = String
										.valueOf(getsettlementonedatagrabpay == null ? 0.0
												: Double.parseDouble(getsettlementonedatagrabpay.getNetAmt()));
								logger.info("Settlement Amount Grabpay : " + grabPaySettlementAmount
										+ "Settlement Date :" + settlementdate);
								settledAmountsProductVise.merge(settlementdate, "Grab  " + grabPaySettlementAmount,
										(oldValue, newValue) -> oldValue + ", " + newValue);
								return grabPaySettlementAmount;
							}
						});

						callables.add(new Callable<String>() {
							public String call() throws Exception {
								FpxTransaction getsettlementonedatafpx = transactionService
										.loadNetAmountandsettlementdatebyFpxEzysettle(settlementdate, merchant);

								String fpxSettlementAmount = String.valueOf(getsettlementonedatafpx == null ? 0.0
										: Double.parseDouble(getsettlementonedatafpx.getPayableAmt()));
								logger.info("Settlement Amount Fpx : " + fpxSettlementAmount + "Settlement Date :"
										+ settlementdate);
								settledAmountsProductVise.merge(settlementdate, "Fpx  " + fpxSettlementAmount,
										(oldValue, newValue) -> oldValue + ", " + newValue);
								return fpxSettlementAmount;
							}
						});

						callables.add(new Callable<String>() {
							public String call() throws Exception {
								EwalletTxnDetails getsettlementonedatam1Pay = transactionService
										.loadNetAmountandsettlementdatebym1PayEzysettle(settlementdate, merchant);

								String m1paySettlementAmount = String.valueOf(getsettlementonedatam1Pay == null ? 0.0
										: Double.parseDouble(getsettlementonedatam1Pay.getPayableAmt()));
								logger.info("Settlement Amount M1pay : " + m1paySettlementAmount + "Settlement Date :"
										+ settlementdate);
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

								/* Use the List to handle net amount assignment based on index */
								double amount = Double.parseDouble(result);
								/* Accumulate amount */
								totalAmount += amount;
								if (!executed) {
//					// validate cutoff time
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
											settledAmountsMap.put(settlementdate, totalAmount);

										} else {
											logger.info("After cutoff time");
											logger.info("Settlementdateone not equal");
											settledAmountsMap.put(settlementdate, totalAmount);
										}
									}
									executed = true;
								} else {
									logger.info("TotalAmount **** : " + totalAmount);
									settledAmountsMap.put(settlementdate, totalAmount);
								}

							}

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

					/*
					 * Filter transactions for the current day: Do not use current day transactions
					 * for Ezysettle.
					 */

					LocalDate currentDate = LocalDate.now();
					DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
					logger.info("Sorted Settlement Data : " + entry.getKey() + " : " + entry.getValue());
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

				}

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
					e1.printStackTrace();
				}

				logger.info(" settlementdateone   " + settlementdateone);
				logger.info(" settlementdatetwo   " + settlementdatetwo);
				logger.info(" settlementdatethree   " + settlementdatethree);
				logger.info(" settlementdatefour   " + settlementdatefour);
				logger.info(" settlementdatefive   " + settlementdatefive);

				/* Service - Settlement Net Amount of 5 days */

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

				model.addAttribute("Fonedate", Fonedate);
				model.addAttribute("Ftwodate", Ftwodate);
				model.addAttribute("Fthreedate", Fthreedate);
				model.addAttribute("Ffourdate", Ffourdate);
				model.addAttribute("Ffivedate", Ffivedate);

				/* Taking settlement of 5 days Week value */

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
				"login");

		if (auditTrail != null) {
			logger.info("Logged in by Merchant : " + principal.getName());
		}
		//JENFI CHANGES
		jenfiService.bindJenfiModelAttributes(merchant,model);
		logger.info("getEzyrecMid" + merchant.getMid().getEzyrecMid());
		logger.info("MErchant User Name : " + merchant.getUsername());
		model.addAttribute("merchant", merchant);
		model.addAttribute("checkDeviceStatus", "YES");
		model.addAttribute("deviceCheck", "YES");
		model.addAttribute("loginname", principal.getName());
		session.setAttribute("userName", merchant.getUsername());
		session.setAttribute("merchant", merchant);
		session.setAttribute("userRole", "BANK_ADMIN");

		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/merchants/merchantsweb" }, method = RequestMethod.GET)
	public String viewPageMerchantWeb(final Model model, final java.security.Principal principal,
			HttpServletRequest request, RegAddMerchant regAddMerchant, @RequestParam("username") String userName,
			@RequestParam("id") Long id) {
		model.addAttribute("isActualMerchant","yes");
		logger.info("@@@@@ VIEW MERCHANTWEB PORTAL  by Admin !!! @@@@@ Login Person :" + principal.getName()
				+ " Merchant: " + userName);
		HttpSession session = request.getSession(true);
		logger.info("current session id: in merchant \n" + session.getId());
		// for api key table start
		Merchant currentMerchant = merchantService.loadMerchant(userName);
		MID mid = merchantService.loadMidByMerchant_PK(String.valueOf(currentMerchant.getId()));

		String merchanttype = currentMerchant.getMerchantType();
		logger.info("merchant type : "+merchanttype);

		// We used MODIFIED_BY field for checking Ezysettle is enable or not
		String checkIfSettle = currentMerchant.getModifiedBy();

		logger.info("checkIfEzySettle===>" + checkIfSettle);
		PageBean pageBean = null;
		String checkname = PropertyLoad.getFile().getProperty("DISABLE_EZYSETTLE");
		if (currentMerchant.getUsername().equalsIgnoreCase(checkname)) {
			pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee", Module.HOME_WEB);
		} else {
			/* Check if either merchanttype or checkIfSettle is null, empty, or blank */
			if (Utils.isNullOrEmpty(merchanttype) || Utils.isNullOrEmpty(checkIfSettle)) {
				pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee", Module.HOME_WEB);
			} else if ("Yes".equalsIgnoreCase(checkIfSettle) && ("u".equalsIgnoreCase(merchanttype) || "fiuu".equalsIgnoreCase(merchanttype))) {
				pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrd", Module.HOME_WEB);
			} else {
				pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee", Module.HOME_WEB);
			}
		}
		model.addAttribute("pageBean", pageBean);

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
		

//		checking for duitnow(enabled or disabled)
		List<MerchantPaymentDetailsDto> merchantPaymentDetailsDto = merchantService.loadMerchantPaymentDetailsByFk(currentMerchant.getId());
		Optional<MerchantPaymentDetailsDto> optionalDetails = merchantPaymentDetailsDto.stream()
			    .filter(merchantPaymentDetails -> "true".equalsIgnoreCase(merchantPaymentDetails.getEnablePaymentMethod()))
			    .findFirst();

			if (optionalDetails.isPresent()) {
			    MerchantPaymentDetailsDto merchantPaymentDetails = optionalDetails.get();
			    String paymentMethod = merchantPaymentDetails.getPaymentMethod();
			    if ("PM-101".equals(paymentMethod)) {
				    session.setAttribute("enableDuitnow", merchantPaymentDetails);
			        logger.info("DuitNow QR is enabled for merchant id " + currentMerchant.getId());
			    }
			} else {
			    logger.info("DuitNow QR is not enabled for merchant id " + currentMerchant.getId());
			    session.setAttribute("enableDuitnow", null);
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
				Utils utils = new Utils();
				DateTime mytDateAndTime = utils.currentMYT();

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

//			String merchantMid = utils.loadMid(currentMerchant);
//			MobiMDR mobiMdr = transactionService.loadMobiMdr(merchantMid);
				EzysettleUtils ezysettleUtils = new EzysettleUtils();

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

								return cardSettlementAmount;
							}
						});

						callables.add(new Callable<String>() {
							public String call() throws Exception {
								BoostDailyRecon getsettlementonedataboost = transactionService
										.loadNetAmountandsettlementdatebyBoostEzysettle(settlementdate,
												currentMerchant);

								String boostSettlementAmount = String.valueOf(getsettlementonedataboost == null ? 0.0
										: Double.parseDouble(getsettlementonedataboost.getNetAmount()));
								logger.info("Settlement Amount Boost : " + boostSettlementAmount + "Settlement Date :"
										+ settlementdate);
								settledAmountsProductVise.merge(settlementdate, "Boost  " + boostSettlementAmount,
										(oldValue, newValue) -> oldValue + ", " + newValue);

								return boostSettlementAmount;
							}
						});

						callables.add(new Callable<String>() {
							public String call() throws Exception {
								GrabPayFile getsettlementonedatagrabpay = transactionService
										.loadNetAmountandsettlementdatebyGrabpayEzysettle(settlementdate,
												currentMerchant);

								String grabPaySettlementAmount = String
										.valueOf(getsettlementonedatagrabpay == null ? 0.0
												: Double.parseDouble(getsettlementonedatagrabpay.getNetAmt()));
								logger.info("Settlement Amount Grabpay : " + grabPaySettlementAmount
										+ "Settlement Date :" + settlementdate);
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
								settledAmountsProductVise.merge(settlementdate, "Fpx  " + fpxSettlementAmount,
										(oldValue, newValue) -> oldValue + ", " + newValue);
								return fpxSettlementAmount;
							}
						});

						callables.add(new Callable<String>() {
							public String call() throws Exception {
								EwalletTxnDetails getsettlementonedatam1Pay = transactionService
										.loadNetAmountandsettlementdatebym1PayEzysettle(settlementdate,
												currentMerchant);

								String m1paySettlementAmount = String.valueOf(getsettlementonedatam1Pay == null ? 0.0
										: Double.parseDouble(getsettlementonedatam1Pay.getPayableAmt()));
								logger.info("Settlement Amount M1pay : " + m1paySettlementAmount + "Settlement Date :"
										+ settlementdate);
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
											settledAmountsMap.put(settlementdate, totalAmount);

										} else {
											logger.info("After cutoff time");
											logger.info("Settlementdateone not equal");
											settledAmountsMap.put(settlementdate, totalAmount);
										}
									}
									executed = true;
								} else {
									logger.info("TotalAmount **** : " + totalAmount);
									settledAmountsMap.put(settlementdate, totalAmount);
								}

							}

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

//				totalAmounts.add(entry.getValue());
					/*
					 * Filter transactions for the current day: Do not use current day transactions
					 * for Ezysettle.
					 */
//				totalAmounts = ezysettleUtils.filterCurrentDayTransactions(entry.getKey(), entry.getValue(), mobiMdr,
//						transactionDAO, totalAmounts);

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
				}

				logger.info("TotalsettledateoneNetAmount => " + totalAmounts.get(0));
				logger.info("TotalsettledatetwoNetAmount => " + totalAmounts.get(1));
				logger.info("TotalsettledatethreeNetAmount => " + totalAmounts.get(2));
				logger.info("TotalsettledatefourNetAmount => " + totalAmounts.get(3));
				logger.info("TotalsettledatefiveNetAmount => " + totalAmounts.get(4));

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
		model.addAttribute("checkDeviceStatus", "YES");
		model.addAttribute("deviceCheck", "YES");
		session.setAttribute("userName", currentMerchant.getUsername());
		session.setAttribute("merchant", currentMerchant);
		session.setAttribute("userRole", "BANK_MERCHANT");
		return TEMPLATE_MERCHANT;
	}

//	@RequestMapping(value = { "/merchants/merchantsweb" }, method = RequestMethod.GET)
//	public String viewPageMerchantWeb(final Model model, final java.security.Principal principal,
//			HttpServletRequest request, RegAddMerchant regAddMerchant, @RequestParam("username") String userName,
//			@RequestParam("id") Long id) {
//		logger.info("@@@@@ VIEW MERCHANTWEB PORTAL  by Admin !!! @@@@@ Login Person :" + principal.getName()
//				+ " Merchant: " + userName);
//		HttpSession session = request.getSession(true);
//		logger.info("current session id: in merchant \n" + session.getId());
//
//		// for api key table start
//		Merchant currentMerchant = merchantService.loadMerchant(userName);
//		String merchanttype = currentMerchant.getMerchantType();
//
//		// We used MODIFIED_BY field for checking Ezysettle is enable or not
//
//		String checkIfSettle = currentMerchant.getModifiedBy();
//
//		logger.info("checkIfEzySettle===>" + checkIfSettle);
//
//		String checkname = PropertyLoad.getFile().getProperty("DISABLE_EZYSETTLE");
//		if (currentMerchant.getUsername().equalsIgnoreCase(checkname)) {
//			PageBean pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee", Module.HOME_WEB);
//			model.addAttribute("pageBean", pageBean);
//		} else {
////      *******   Disable **********
//			// merchantdashbrdpaydee
//
//			if (merchanttype == null || checkIfSettle == null) {
//				logger.info("inside merchanttype is NULL");
//				PageBean pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee",
//						Module.HOME_WEB);
//				model.addAttribute("pageBean", pageBean);
//			} else if (checkIfSettle.equalsIgnoreCase("No")) {
//				logger.info("inside P merchanttype is P");
//				PageBean pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee",
//						Module.HOME_WEB);
//				model.addAttribute("pageBean", pageBean);
//
//				// merchantdashbrdpaydee
//			} else if (checkIfSettle.equalsIgnoreCase("Yes")) {
//
//				logger.info("inside U merchanttype is U");
//				PageBean pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrd", Module.HOME_WEB);
//
//				model.addAttribute("pageBean", pageBean);
//			}
//
//		}
//		PaginationBean<ReaderList> paginationBean2 = new PaginationBean<ReaderList>();
//		List<ReaderList> readerdata = new ArrayList<ReaderList>();
//		readerdata = readerService.getReaderList(currentMerchant);
//		logger.info("after Reader Size : " + readerdata.size());
//		paginationBean2.setItemList(readerdata);
//		model.addAttribute("paginationBean2", paginationBean2);
//		// for api key table end
//
//		session.setAttribute("MerchantProfile", "Yes");
//		session.setAttribute("userRole", "BANK_MERCHANT");
//		String userName1 = (String) session.getAttribute("userName");
//		String userRole1 = (String) session.getAttribute("userRole");
//		int totalDevice;
//		logger.info("Merchant_profile :");
//		Merchant merchant = merchantService.loadMerchantbyid(id);
//
//		String ezyPodSumCheck = null;
//		String EzypodCheck = "NO";
//
//		MID mid = merchantService.loadMidByMerchant_PK(String.valueOf(merchant.getId()));
//
//		if (mid.getEzyrecMid() != null) {
//
//			TerminalDetails termDetails = merchantService.loadTerminalDetailsByMid(mid.getEzyrecMid());
//
//			if ((termDetails.getDeviceType() == "EZYPOD") || (termDetails.getDeviceType().equals("EZYPOD"))) {
//				EzypodCheck = "YES";
//
//				session.setAttribute("ezyPodCheck", "Yes");
//
//			} else if ((termDetails.getDeviceType() == "EZYREC") || (termDetails.getDeviceType().equals("EZYREC"))) {
//
//				session.setAttribute("ezyPodCheck", "No");
//
//			} else {
//
//				session.setAttribute("ezyPodCheck", "No");
//
//			}
//
//		}
//
//		logger.info("id: " + merchant.getId());
//		if (merchant.getPreAuth() != null) {
//			session.setAttribute("preAuth", merchant.getPreAuth());
//		} else {
//			session.setAttribute("preAuth", "No");
//		}
//		logger.info("merchant id: " + merchant.getId());
//		List<MobileUser> mobileuser = mobileUser.loadMobileUserByFk(merchant.getId());
//
//		String eblBoost = "No";
//		for (MobileUser mu : mobileuser) {
//			if (mu.getEnableBoost() != null) {
//				if (mu.getEnableBoost().equalsIgnoreCase("Yes")) {
//					eblBoost = "Yes";
//				}
//			}
//		}
//		if (eblBoost.equalsIgnoreCase("Yes")) {
//
//			session.setAttribute("enableBoost", "Yes");
//
//		} else {
//			session.setAttribute("enableBoost", "No");
//
//		}
//
//		String eblMoto = "No";
//		for (MobileUser mu : mobileuser) {
//			if (mu.getEnableMoto() != null) {
//
//				if (mu.getEnableMoto().equalsIgnoreCase("Yes")) {
//					eblMoto = "Yes";
//
//				}
//
//			}
//		}
//
//		if (eblMoto.equalsIgnoreCase("Yes")) {
//			session.setAttribute("enableMoto", "Yes");
//
//		} else {
//			session.setAttribute("enableMoto", "No");
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
//		logger.info("ezyAuthEnable: **" + ezyAuthEnable);
//
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
//		session.setAttribute("merchantUserName", merchant.getBusinessName());
//		logger.info("merchantUserName" + merchant.getBusinessName());
//		// logger.info("***EZysettle ***");
//		String typename = PropertyLoad.getFile().getProperty("MERCHANT_TYPE");
//		logger.info("***EZysettle ***");
//		logger.info("merchant Type :" + typename);
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
//
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
//
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
//		AuditTrail auditTrail = merchantService.updateAuditTrailByMerchant(principal.getName(), principal.getName(),
//				"viewByAdmin");
//
//		if (auditTrail != null) {
//			logger.info("Merchant Portal Viewed by Admin : " + principal.getName());
//		}
//
//		logger.info("ezypodchk" + EzypodCheck);
//		logger.info("getEzyrecMid" + merchant.getMid().getEzyrecMid());
//		model.addAttribute("merchant", merchant);
//		logger.info("in homecontroller dashboard: ");
//
//		model.addAttribute("loginname", principal.getName());
//		model.addAttribute("checkDeviceStatus", "YES");
//		model.addAttribute("deviceCheck", "YES");
//		session.setAttribute("userName", merchant.getUsername());
//		session.setAttribute("merchant", merchant);
//		session.setAttribute("userRole", "BANK_MERCHANT");
//		return TEMPLATE_MERCHANT;
//	}

	@RequestMapping(value = { "/superagent/settlementUserlogin", "/superagent/settlementUserlogin*",
			"/superagent/settlementUserlogin**" }, method = RequestMethod.GET)
	public String defaultPagesettlementUserlogin(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {
		logger.info("@@@@@ WELCOME TO SETTLEMENTUSER PORTAL !!! @@@@@");
		logger.info("Login Person :" + principal.getName());
		PageBean pageBean = new PageBean("HOME AGENT_WEB", "SettlementUser/SettlementMDRList", Module.HOME_WEB);
//System.out.println("display agent web : " + principal.getName() );
		model.addAttribute("pageBean", pageBean);
		HttpSession session = request.getSession();

		Agent agent = agentService.loadAgent(principal.getName());

		session.setAttribute("userName", agent.getUsername());

		PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();

		transactionService.listSettlementMDRTransaction(paginationBean, null, null, null);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table // response
		} else {
			model.addAttribute("responseData", null);
		}

		AuditTrail auditTrail = agentService.updateAuditTrailByAgent(principal.getName(), principal.getName(), "login");

		if (auditTrail != null) {
			logger.info("Logged in by Hotel Merchant Agent : " + principal.getName());
		}

		model.addAttribute("paginationBean", paginationBean);
		session.setAttribute("userName", agent.getUsername());
		model.addAttribute("loginname", principal.getName());
		session.setAttribute("userRole", "SUPERAGENT_USER");

		return TEMPLATE_SETTLEMENTUSER;
//end	 
	}

	@RequestMapping(value = { "/superagent/payoutUserlogin", "/superagent/payoutUserlogin*",
			"/superagent/payoutUserlogin**" }, method = RequestMethod.GET)
	public String defaultPagepayoutUserlogin(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {
		logger.info("@@@@@ WELCOME TO PAYOUTUSER PORTAL !!! @@@@@");
		logger.info("Login Person :" + principal.getName());
		PageBean pageBean = new PageBean("HOME AGENT_WEB", "PayoutUser/PayoutList", Module.HOME_WEB);
		model.addAttribute("pageBean", pageBean);
		HttpSession session = request.getSession();

		Agent agent = agentService.loadAgent(principal.getName());

		session.setAttribute("userName", agent.getUsername());

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();

		transactionService.listPayoutTransaction(paginationBean, null, null, null);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found");
		} else {
			model.addAttribute("responseData", null);
		}

		AuditTrail auditTrail = agentService.updateAuditTrailByAgent(principal.getName(), principal.getName(), "login");

		if (auditTrail != null) {
			logger.info("Logged in by Payout User : " + principal.getName());
		}

		model.addAttribute("paginationBean", paginationBean);
		session.setAttribute("userName", agent.getUsername());
		model.addAttribute("loginname", principal.getName());
		session.setAttribute("userRole", "SUPERAGENT_USER");

		return TEMPLATE_PAYOUTUSER;
	}

	// for 403 access denied page
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public ModelAndView accesssDenied() {
		logger.error("access denied");
		ModelAndView model = new ModelAndView();

		// check if user is login
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			UserDetails userDetail = (UserDetails) auth.getPrincipal();
			model.addObject("username", userDetail.getUsername());
		}

		model.setViewName("403");
		return model;
	}

	public String getMonth(int m) {

		// System.out.println(" Data :"+m);
		String mon = "";
		switch (m) {
		case 1:
			mon = "JAN";
			break;
		case 2:
			mon = "FEB";
			break;
		case 3:
			mon = "MAR";
			break;
		case 4:
			mon = "APR";
			break;
		case 5:
			mon = "MAY";
			break;
		case 6:
			mon = "JUN";
			break;
		case 7:
			mon = "JUL";
			break;
		case 8:
			mon = "AUG";
			break;
		case 9:
			mon = "SEP";
			break;
		case 10:
			mon = "OCT";
			break;
		case 11:
			mon = "NOV";
			break;
		case 12:
			mon = "DEC";
			break;

		default:
			mon = "";
			break;
		}

		return mon;

	}

	public int roundNum(int max) {
		// max=934234240;
		int len = String.valueOf(max).length();
		int rounded = 0;

		if (len == 9) {
			rounded = ((Math.round(max) + 99999999) / 100000000) * 100000000;
		} else if (len == 8) {
			rounded = ((Math.round(max) + 9999999) / 10000000) * 10000000;
		} else if (len == 7) {
			rounded = ((Math.round(max) + 999999) / 1000000) * 1000000;
		} else if (len == 6) {
			rounded = ((Math.round(max) + 99999) / 100000) * 100000;
		} else if (len == 5) {
			rounded = ((Math.round(max) + 9999) / 10000) * 10000;
		} else if (len == 4) {
			rounded = ((Math.round(max) + 999) / 1000) * 1000;
		} else if (len == 3) {
			rounded = ((Math.round(max) + 99) / 100) * 100;
		}
		String data = String.valueOf(rounded);
		String d = data.replaceFirst(Pattern.quote(data.substring(0, 1)), "10");

		return Integer.parseInt(d);

	}

//	
//	@RequestMapping(value = {"/merchants/testPayout","/merchants/testPayout*","/merchants/testPayout**"}, method = RequestMethod.GET)
//	public String payoutTest(final Model model, final java.security.Principal principal,
//			HttpServletRequest request) {
//		logger.info("@@@@@ WELCOME TO TEST PAYOUT !!! @@@@@ Login Person :" + principal.getName());
//	
//		PageBean pageBean = new PageBean("HOME AGENT_WEB", "PayoutUser/TestPayout/PayoutRequest", Module.HOME_WEB);
//
//		String mobiApiKey = "b07ad9f31df158edb188a41f725899bc";
//		String service = "PAYOUT_TXN_REQ";
//		String businessRegNo = "8754648217513";
//		String bankName = "Ambank Malaysia Berhad";
//		String bicCode = "ARBKMYKL";
//		String bankAccNo = "8881048358879";
//		String subMid = "201100000012450";
//		
//		model.addAttribute("pageBean", pageBean);
//		model.addAttribute("mobiApiKey", mobiApiKey);
//		model.addAttribute("service", service);
//		model.addAttribute("businessRegNo", businessRegNo);
//		model.addAttribute("bankName", bankName);
//		model.addAttribute("bicCode", bicCode);
//		model.addAttribute("bankAccNo", bankAccNo);
//		model.addAttribute("subMid", subMid);
//	
//		
//		return TEMPLATE_TEST_PAYOUT;
//	}
//
//	

//	@RequestMapping(value = {"/merchants/testPayout","/merchants/testPayout*","/merchants/testPayout**"}, method = RequestMethod.GET)
//	public String viewPageMerchantWeb(final Model model, final java.security.Principal principal,
//			HttpServletRequest request) {
//		logger.info("@@@@@ WELCOME TO PAYOUT !!! @@@@@ Login Person :" + principal.getName());
////		HttpSession session = request.getSession(true);
//		
//		logger.info("@@@@@ WELCOME TO PAYOUT !!! @@@@@ Login Person ");
//		
////		PageBean pageBean = new PageBean("HOME TEST PAYOUT", "transaction/merchantBoost", Module.HOME_WEB);
//		
//		PageBean pageBean = new PageBean("HOME TEST PAYOUT", "transaction/merchantBoost",
//				Module.HOME_WEB, "transaction/merchantBoost/sideMenuTransaction");
////		
////		model.addAttribute("pageBean", pageBean);
//		
//		
//		
////		PageBean pageBean = new PageBean("HOME MERCHANTWEB", "dashboard/merchantdashbrdpaydee", Module.HOME_WEB);
//		model.addAttribute("pageBean", pageBean);
//		
////		session.setAttribute("userRole", "BANK_MERCHANT");
//		
////		model.addAttribute("checkDeviceStatus", "YES");
////		model.addAttribute("deviceCheck", "YES");
////		model.addAttribute("loginname", principal.getName());
////		session.setAttribute("userName", "");
////		session.setAttribute("merchant", "");
////		session.setAttribute("userRole", "BANK_MERCHANT");
////		
//		logger.info("@@@@@ WELCOME ");
//		return TEMPLATE_TEST_PAYOUT;
//	}

}