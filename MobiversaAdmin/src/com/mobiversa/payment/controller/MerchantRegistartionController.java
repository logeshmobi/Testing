package com.mobiversa.payment.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.SubAgentService;
import com.mobiversa.payment.service.TransactionService;

@Controller
@RequestMapping(value = MerchantRegistartionController.URL_BASE)

public class MerchantRegistartionController extends BaseController {

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private MerchantDao merchantDAO;

	@Autowired
	private AgentService agentService;

	@Autowired
	private SubAgentService subAgentService;

	public static final String URL_BASE = "/registartion";
//
//	private static final String INITIAL_BOOST_MID = "BST";
//	private static final String INITIAL_GRAB_MID = "GRB";
//	private static final String INITIAL_FPX_MID = "FPX";
//	private static final String INITIAL_TNG_MID = "TNG";
//	private static final String INITIAL_SPP_MID = "SPP";
//	private static final int MID_LENGTH = 11;

	private static final Logger logger = Logger.getLogger(MerchantRegistartionController.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		return "redirect:" + URL_BASE + "/register/1";
	}
	
	
	@RequestMapping(value = { "/addMerchant" }, method = RequestMethod.GET)
	public String displayAddMerchant(final HttpServletRequest request, Model model, final java.security.Principal principal) {
		
		
		return null;
	}

	@RequestMapping(value = { "/updateMerchantDetails" }, method = RequestMethod.POST)
	public String UpdateMerchantDetails(final HttpServletRequest request, Model model, final java.security.Principal principal) {
		
		
		return null;
	}
	
//
//	@RequestMapping(value = { "/addMerchant" }, method = RequestMethod.GET)
//	public String displayAddMerchant(@ModelAttribute("merchant") final RegAddMerchant regAddMerchant,
//			final HttpServletRequest request, Model model, final java.security.Principal principal) {
//
//		logger.info("About to Add merchant details");
//		logger.info(" Admin login person Name:" + principal.getName());
//
//		PageBean pageBean = new PageBean("Merchant Detail", "merchant/addmerchant/addMerchantDetails", Module.MERCHANT,
//				"merchant/sideMenuMerchant");
//
//		model.addAttribute("pageBean", pageBean);
//
//		String err = (String) request.getSession(true).getAttribute("addErSession");
//		if (err != null) {
//			if (err.equalsIgnoreCase("Yes")) {
//
//				logger.info("err::::::" + err);
//				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
//				request.getSession(true).removeAttribute("editErSession");
//			}
//		}
//
////		model.addAttribute("stateList", AddMerchantOptionList.stateList());
//		model.addAttribute("documentsList", AddMerchantOptionList.documentsList());
//		model.addAttribute("CategoryList", AddMerchantOptionList.CategoryList());
//		model.addAttribute("natureOfBusinessList", AddMerchantOptionList.natureOfBusinessList());
//
//		return TEMPLATE_DEFAULT;
//	}
//
//	@RequestMapping(value = { "/addMerchantReview" }, method = RequestMethod.POST)
//	public String addmerchantDetails(@ModelAttribute("merchant") final RegAddMerchant regAddMerchant,
//			final HttpServletRequest request, final ModelMap model, final BindingResult errors,
//			final Principal principal) {
//
//		logger.info("Add Merchant Details Reviewing and Confirmation ");
//		logger.info("Admin login person Name:" + principal.getName());
//		
//		List<Agent> agent1 = agentService.loadAgent();
//		logger.info("Number of Agents:" + agent1.size());
//		Set<String> agentNameList = new HashSet<String>();
//
//		PageBean pageBean = new PageBean("Merchant Detail", "merchant/addmerchant/addMerchantDetailsReview", Module.MERCHANT,
//				"merchant/sideMenuMerchant");
//
//		model.addAttribute("pageBean", pageBean);
//
//
//		String responseDataOfficeEmail = null;
//
//
//		logger.info("Merchant Type : " + regAddMerchant.getMerchantType());
//		logger.info("VCC  : " + regAddMerchant.getVcc());
//		logger.info("Pre-Auth  : " + regAddMerchant.getPreAuth());
//		logger.info("Auto Settled  : " + regAddMerchant.getAutoSettled());
//		logger.info("OTP  : " + regAddMerchant.getAuth3DS());
//
////		if (regAddMerchant.getMerchantType() != null) {
////			if (regAddMerchant.getMerchantType().equals("U") || regAddMerchant.getMerchantType() == "U") {
////				logger.info("Merchant Type setted as U ");
////				regAddMerchant.setMerchantType("U");
////			} else if (regAddMerchant.getMerchantType().equals("P") || regAddMerchant.getMerchantType() == "P") {
////				logger.info("Merchant Type setted as P");
////				regAddMerchant.setMerchantType("P");
////			}
////		} else {
////			logger.info("Merchant Type Empty");
////		}
//
//		
//
//		logger.info("Official Mail  : " + regAddMerchant.getOfficeEmail());
//
//		Merchant offEmail = merchantService.loadMerchantbyEmail(regAddMerchant.getOfficeEmail());
//
//		responseDataOfficeEmail = offEmail != null ? " Office Email already exist" : null;
//
//		logger.info("Office Email:::::" + offEmail + ":  " + responseDataOfficeEmail);
//
//		logger.info("Paydee MID :");
//		logger.info("mid : " + regAddMerchant.getMid());
//		logger.info("ezymotomid : " + regAddMerchant.getEzymotomid());
//		logger.info("ezypassmid : " + regAddMerchant.getEzypassmid());
//		logger.info("ezywaymid  : " + regAddMerchant.getEzywaymid());
//		logger.info("ezyrecmid  : " + regAddMerchant.getEzyrecmid());
//
//		String boostMid = autoGenerateBoostMid();
//		String grabMid = autoGenerateGrabMid();
//		String fpxMid = autoGenerateFpxMid();
//		String tngMid = autoGenerateTngMid();
//		String sppMid = autoGenerateSppMid();
//		
//		//If card will come generate Umezymoto mid umezywire
//		String cardMid = autoGenerateCardMid();
//				
//		
//		BaseDataImpl baseData = new BaseDataImpl();
//
//		RegAddMerchant a = baseData.vaildated(regAddMerchant);
//
//		
//		// Security purpose for virus theft
//		String responseDataError = null;
//		if (a != null) {
//			logger.info("Contains HTML tags");
//			responseDataError = a != null ? "Contains HTML tags" : null;
//		}
//
//		logger.info("responseDataOfficeEmail  : " + responseDataOfficeEmail);
//		logger.info("responseDataError  : " + responseDataError);
//
//		model.addAttribute("pageBean", pageBean);
//		model.addAttribute("merchant", regAddMerchant);
//		model.addAttribute("agent");
//		// PCI
//		request.getSession(true).setAttribute("addMerchantSession", regAddMerchant);
//
//		return TEMPLATE_DEFAULT;
//
//	}
//
//	
//	
//	@RequestMapping(value = { "/merchantInsert" }, method = RequestMethod.POST)
//	public String merchantAddinDatabase(@ModelAttribute("merchant") final RegAddMerchant regAddMerchant,
//			final HttpServletRequest request, final ModelMap model, final BindingResult errors,
//			final Principal principal) {
//
//		logger.info("Submitted to Add Merchant Details");
//		logger.info("Admin login person Name:" + principal.getName());
//		PageBean pageBean = null;
//
//		List<Agent> agent1 = agentService.loadAgent();
//		logger.info("Number of Agents:" + agent1.size());
//		Set<String> agentNameList = new HashSet<String>();
//
//	
//		String responseDataOfficeEmail = null;
//
//
//		logger.info("Merchant Type : " + regAddMerchant.getMerchantType());
//		logger.info("VCC  : " + regAddMerchant.getVcc());
//		logger.info("Pre-Auth  : " + regAddMerchant.getPreAuth());
//		logger.info("Auto Settled  : " + regAddMerchant.getAutoSettled());
//		logger.info("OTP  : " + regAddMerchant.getAuth3DS());
//
//		logger.info("Paydee MID :");
//		logger.info("mid : " + regAddMerchant.getMid());
//		logger.info("ezymotomid : " + regAddMerchant.getEzymotomid());
//		logger.info("ezypassmid : " + regAddMerchant.getEzypassmid());
//		logger.info("ezywaymid  : " + regAddMerchant.getEzywaymid());
//		logger.info("ezyrecmid  : " + regAddMerchant.getEzyrecmid());
//
//		String boostMid = autoGenerateBoostMid();
//		String grabMid = autoGenerateGrabMid();
//		String fpxMid = autoGenerateFpxMid();
//		String tngMid = autoGenerateTngMid();
//		String sppMid = autoGenerateSppMid();
//		
//		
//		//If card will come generate Umezymoto mid umezywire
//		String cardMid = autoGenerateCardMid();
//		
//		
//		
//		BaseDataImpl baseData = new BaseDataImpl();
//
//		RegAddMerchant a = baseData.vaildated(regAddMerchant);
//
//		
//		// Security purpose for virus theft
//		String responseDataError = null;
//		if (a != null) {
//			logger.info("Contains HTML tags");
//			responseDataError = a != null ? "Contains HTML tags" : null;
//		}
//
//		logger.info("responseDataOfficeEmail  : " + responseDataOfficeEmail);
//		logger.info("responseDataError  : " + responseDataError);
//
//		model.addAttribute("pageBean", pageBean);
//		model.addAttribute("merchant", regAddMerchant);
//		model.addAttribute("agent");
//		// PCI
//		request.getSession(true).setAttribute("addMerchantSession", regAddMerchant);
//
//		return TEMPLATE_DEFAULT;
//
//	}
//
//	public String autoGenerateBoostMid() {
//		return autoGenerateMid(INITIAL_BOOST_MID);
//	}
//	public String autoGenerateGrabMid() {
//		return autoGenerateMid(INITIAL_GRAB_MID);
//	}
//	public String autoGenerateFpxMid() {
//		return autoGenerateMid(INITIAL_FPX_MID);
//	}
//	public String autoGenerateTngMid() {
//		return autoGenerateMid(INITIAL_TNG_MID);
//	}
//	public String autoGenerateSppMid() {
//		return autoGenerateMid(INITIAL_SPP_MID);
//	}
//	private String autoGenerateMid(String initialMid) {
//		String responseMid;
//		String mid;
//		Random random = new Random();
//		do {
//			int randomNumber = random.nextInt(900000) + 100000;
//			mid = initialMid +  String.format("%05d", 0)  + randomNumber;
//			responseMid = merchantService.checkExistMid(mid) != null ? "MID already Exist" : null;
//
//		} while (responseMid != null);
//
//		return mid;
//	}
//	
//	private String autoGenerateCardMid() {
//		String responseMid;
//		String mid;
//		Random random = new Random();
//		do {
//			int randomNumber = random.nextInt(900000) + 100000;
//			mid = String.format("%09d", 0)  + randomNumber;
//			responseMid = merchantService.checkExistMid(mid) != null ? "MID already Exist" : null;
//		} while (responseMid != null);
//
//		return mid;
//	}
//	
//	
//	
////	public String autoGenerateBoostMid() {
////
////		Random random = new Random();
////		// Generate a random 6-digit number
////		int randomNumber = random.nextInt(900000) + 100000;
////
////		String responseBoostMid = null;
////		String IntialBoostMid = "BST";
////		String boostMid = String.valueOf(randomNumber);
////
////		// Create 6 digit number
////		for (int i = boostMid.length(); i < 11; i++) {
////			boostMid = "0" + boostMid;
////		}
////		IntialBoostMid = IntialBoostMid + boostMid;
////
////		responseBoostMid = merchantService.checkExistMid(IntialBoostMid) != null ? "Boost MID already Exist" : null;
////		String responseMid =null;
////		if(responseBoostMid != null) {
////		    responseMid =	autoGenerateBoostMid();
////		}
////		
////		
////		return responseMid;
////	}
//
////	public String autoGenerateGrabMid() {
////
////		Random random = new Random();
////		// Generate a random 6-digit number
////		int randomNumber = random.nextInt(900000) + 100000;
////
////		String responseGrabMid = null;
////		String IntialGrabMid = "GRB";
////		String GrabMid = String.valueOf(randomNumber);
////
////		// Create 6 digit number
////		for (int i = GrabMid.length(); i < 11; i++) {
////			GrabMid = "0" + GrabMid;
////		}
////		IntialGrabMid = IntialGrabMid + GrabMid;
////
////		responseGrabMid = merchantService.checkExistMid(IntialGrabMid) != null ? "grab MID already Exist" : null;
////		String responseMid = null;
////		if (responseGrabMid != null) {
////			responseMid = autoGenerateBoostMid();
////		}
////
////		return responseMid;
////	}

}
