package com.mobiversa.payment.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.KManager;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.MobileUserStatusHistory;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.bo.UMKManager;
import com.mobiversa.common.bo.UMMidTxnLimit;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.BaseDataImpl;
import com.mobiversa.payment.dto.MobileUserData;
import com.mobiversa.payment.dto.RegMobileUser;
import com.mobiversa.payment.exception.MobiException;
import com.mobiversa.payment.exception.Status;
import com.mobiversa.payment.service.AdminService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.TransactionService;

@Controller
@RequestMapping(value = MobileUserController.URL_BASE)
public class MobileUserController extends BaseController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private MobileUserService mobileUserService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;

	private static final Logger logger = Logger.getLogger(MobileUserController.class);
	public static final String URL_BASE = "/mobileUser";

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		// logger.info(message);
		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayMoblieUserList(final Model model, final RegMobileUser regMobileUser,
			final HttpServletRequest request, final java.security.Principal principal,
			@PathVariable final int currPage) {
		
		logger.info("MobileUser Summary by: " + principal.getName());

		PageBean pageBean = new PageBean("MobileUser", "mobileuser/mobileuserList", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("pageBean", pageBean);
		
		// PaginationBean<MobileUser> paginationBean = new PaginationBean<MobileUser>();
		PaginationBean<MobileUserData> paginationBean = new PaginationBean<MobileUserData>();

		paginationBean.setCurrPage(currPage);
		
		// mobileUserService.listMobileUsers(paginationBean,null,null);

		mobileUserService.listMobileUsersNew(paginationBean, null, null);

		/*
		 * if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() ==
		 * null || paginationBean.getItemList().size() == 0 ) {
		 * model.addAttribute("responseData", "No Records found"); //table response
		 * }else { model.addAttribute("responseData", null); }
		 */
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String displaySearchMobileUser(

			final Model model, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam final String date, @RequestParam final String date1) {
		
		logger.info("search MobileUser" + date+" "+date1);
		
		
		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		
		PageBean pageBean = new PageBean("Search Mobileuser", "mobileuser/mobileuserList", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		// PaginationBean<MobileUser> paginationBean = new PaginationBean<MobileUser>();
		PaginationBean<MobileUserData> paginationBean = new PaginationBean<MobileUserData>();
		paginationBean.setCurrPage(currPage);
		// mobileUserService.listMobileUsers(paginationBean,null,null);

		mobileUserService.listMobileUsersNew(paginationBean, fromDate, toDate);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	// add & edit mobile User code 30052016
	@RequestMapping(value = { "/viewMobileUsers/details/{id}" }, method = RequestMethod.GET)
	public String displayMoblieUsersList(final Model model, @PathVariable final int id) {
		logger.info("about to list all  mobileuser");
		PageBean pageBean = new PageBean("MobileUser", "mobileuser/viewMobileUsers", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("pageBean", pageBean);
		Merchant merchant = merchantService.loadMerchantByPk((long) id);
		model.addAttribute("merchant", merchant);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/details" }, method = RequestMethod.POST)
	public String detailsMobileUser(final Model model, @RequestParam("id") String tid,
			/* @PathVariable final long id, */final java.security.Principal principal) {

		PageBean pageBean = new PageBean("Mobileuser Detail", "mobileuser/mobileuserDetail", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		logger.info("Details MobileUser :" + principal.getName() + " tid: " + tid);

		MobileUserData mobileUser = mobileUserService.loadMobileUserDataByTid(tid);

		// MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);
		logger.info("Details MobileUser :" + mobileUser.getTid() + ":" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/edit" }, method = RequestMethod.POST)
	public String displayEditMobileUser(final HttpServletRequest request,final Model model,
			@RequestParam("id") String tid /* @PathVariable final Long id */) {
		// logger.info("about to edit mobileuser");
		PageBean pageBean = new PageBean("MobileUser Details", "mobileuser/edit/mobileUserEditDetails",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		// MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);
		logger.info("/edit mobileuser: " + tid);
		MobileUserData mobileUser = mobileUserService.loadMobileUserDataByTid(tid);
		if (mobileUser != null) {

			logger.info("mobile user name " + mobileUser.getMobileUserName());
			logger.info("mobile user preauth " + mobileUser.getPreAuth());
			logger.info("mobile boost: " + mobileUser.getEnableBoost() + 
					"  and moto " + mobileUser.getEnableMoto());
			model.addAttribute("mobileUser", mobileUser);
			model.addAttribute("enableBoost", mobileUser.getEnableBoost());
			model.addAttribute("enableMoto", mobileUser.getEnableMoto());
		}
		logger.info("remarks: "+mobileUser.getRemarks());
		// Merchant merchant = merchantService.loadMerchantByPk(id);
		
		String  err = (String) request.getSession(true).getAttribute("editErSession");
		
		if(err!=null) {
		if(err.equalsIgnoreCase("Yes")) {
			
			logger.info("err::::::" + err);
			model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
			request.getSession(true).removeAttribute("editErSession");
		}
		}

		// model.addAttribute("merchant", merchant);
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/editMobileuser" }, method = RequestMethod.POST)
	public String displayEditMobileUserSes(final Model model, final HttpServletRequest request,
			@RequestParam("tid") final String tid, @RequestParam("activationDate") final String activationDate,

			@RequestParam("mobileUserName") final String mobileUserName, @RequestParam("preAuth") final String preAuth,
			@RequestParam("Moto") final String enableMoto, @RequestParam("Boost") final String enableBoost,
			@RequestParam("renewalDate") final String renewalDate,

			@RequestParam("status") final String status, @RequestParam("remarks") final String remarks,
			@RequestParam("expiryDate") final String expiryDate,
			@RequestParam("expiry") final String expiry) {

		logger.info("/editMobileuser " + tid + "expiryDate: " + expiryDate);
		PageBean pageBean = new PageBean("MobileUser Detail", "mobileuser/edit/mobileUserEditDetails",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		/*
		 * MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);
		 * mobileUser.setFirstName(firstName); //mobileUser.setLastName(lastName);
		 * mobileUser.setEmail(email); //mobileUser.setUsername(username);
		 * mobileUser.setContact(contact); mobileUser.setSalutation(salutation);
		 * mobileUser.setPreAuth(preAuth);
		 */
		MobileUserData mobileUser = new MobileUserData();
		if (expiryDate == null || expiryDate.isEmpty()) {

			mobileUser = mobileUserService.loadMobileUserDataByTid(tid);
			mobileUser.setExpiryDate(expiry);
			model.addAttribute("mobileUser", mobileUser);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("responseErr", "Expiry Date should not be Empty.");
			return TEMPLATE_DEFAULT;
		}

		
		mobileUser.setTid(tid);
		mobileUser.setActivationDate(activationDate);
		mobileUser.setMobileUserName(mobileUserName);
		mobileUser.setExpiryDate(expiryDate);
		mobileUser.setPreAuth(preAuth);
		mobileUser.setRenewalDate(renewalDate);
		mobileUser.setStatus(status);
		mobileUser.setRemarks(remarks);
		mobileUser.setEnableBoost(enableBoost);
		mobileUser.setEnableMoto(enableMoto);
		logger.info("boost and moto enable: " + mobileUser.getEnableBoost() + " " + mobileUser.getEnableMoto() + " "
				+ "expirydate: " + mobileUser.getExpiryDate());

		if (mobileUser.getEnableBoost() != null) {
			mobileUser.setEnableBoost(enableBoost);
		} else {
			mobileUser.setEnableBoost("");
		}
		if (mobileUser.getEnableMoto() != null) {
			mobileUser.setEnableMoto(enableMoto);
		} else {
			mobileUser.setEnableMoto("");
		}
		
		 BaseDataImpl  baseData = new BaseDataImpl();
			
		 MobileUserData  a =baseData.vaildated(mobileUser);
			
			
			if(a != null) {
				logger.info("Contains HTML tags");
				request.getSession(true).setAttribute("editErSession", "yes");

				return "redirect:/mobileUser/editMobileuser/1";
			}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("bankUser", mobileUser);
		model.addAttribute("mobileUser", mobileUser);
		request.getSession(true).setAttribute("editMobileUserSession", mobileUser);

		return "redirect:/mobileUser/editMobileUserReviewandConfirm";

	}

	@RequestMapping(value = { "/editMobileUserReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditMobileUserConfirm(final Model model, final HttpServletRequest request) {

		logger.info("editMobileUserReviewandConfirm ");

		// MobileUser mobileUser = (MobileUser)
		// request.getSession(true).getAttribute("editMobileUserSession");
		MobileUserData mobileUser = (MobileUserData) request.getSession(true).getAttribute("editMobileUserSession");
		logger.info("boost1 and moto enable1: " + mobileUser.getEnableBoost() + " " + mobileUser.getEnableMoto() + " "
				+ "expirydate1: " + mobileUser.getExpiryDate());
		logger.info("mobile user tid: " + mobileUser.getTid());
		if (mobileUser == null) {
			return "redirect:" + URL_BASE + "/editMobileuser/1";
		}

		PageBean pageBean = new PageBean("Mobile user edit Details", "mobileuser/edit/mobileUserReviewandConfirm",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/editMobileUserReviewandConfirm" }, method = RequestMethod.POST)
	public String confirmEditMobileUser(@ModelAttribute("mobileUser") final MobileUserData mobileUser,
			final Model model, final java.security.Principal principal, final HttpServletRequest request) {
		logger.info("edit MobileUser Details Confirms");
		PageBean pageBean = new PageBean("Succefully MobileUser edited", "mobileuser/edit/mobileUserAlldoneSuccessful",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		model.addAttribute("pageBean", pageBean);
		// MobileUser mobileUserSavedInHttpSession = (MobileUser)
		// request.getSession(true).getAttribute("editMobileUserSession");

		MobileUserData mobileUserSavedInHttpSession = (MobileUserData) request.getSession(true)
				.getAttribute("editMobileUserSession");

		logger.info("mobile user id: " +mobileUserSavedInHttpSession.getRemarks());

		String tid = mobileUserSavedInHttpSession.getTid();
		logger.info(mobileUserSavedInHttpSession.getMobileUserName());

		logger.info("login person name:" + principal.getName());

		if (mobileUserSavedInHttpSession == null) {
			return "redirect:/mobileUser/editMobileUserReviewandConfirm";
		}
		
		BaseDataImpl  baseData = new BaseDataImpl();
		
		 MobileUserData  a =baseData.vaildated(mobileUser);
			
			
			if(a != null) {
				logger.info("Contains HTML tags");
				request.getSession(true).setAttribute("editErSession", "yes");

				return "redirect:/mobileUser/editMobileuser/1";
			}
		

		logger.info(" MobileUser updated by : " + principal.getName() + ":" + mobileUserSavedInHttpSession.getTid()
				+ ":" + mobileUserSavedInHttpSession.getPreAuth() + " : "
				+ mobileUserSavedInHttpSession.getMobileUserName());
		model.addAttribute("mobileUser", mobileUserSavedInHttpSession);

		MobileUserData mobileuserData = mobileUserService.updateMobileUSerDetails(mobileUserSavedInHttpSession);
		if (mobileuserData != null) {

			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(
					mobileUserSavedInHttpSession.getMobileUserName(), principal.getName(), "editMobileUser");
			if (auditTrail != null) {
				logger.info("MobileUser: " + mobileUserSavedInHttpSession.getMobileUserName()
						+ " Status Changed by Admin: " + principal.getName());
			}

		}

		// mobileUserService.updateMobileUSer(mobileUserSavedInHttpSession);

		/*
		 * logger.info(" MobileUser updated by : " +
		 * principal.getName()+":"+mobileUserSavedInHttpSession.getFirstName() + ":"+
		 * mobileUserSavedInHttpSession.getEmail());
		 * model.addAttribute(mobileUserSavedInHttpSession);
		 * mobileUserService.updateMobileUSer(mobileUserSavedInHttpSession);
		 */
		request.getSession(true).removeAttribute("editMobileUserSession");

		model.addAttribute("tid", tid);
		return TEMPLATE_DEFAULT;
	}
//edit mobile user end

	
	
	@RequestMapping(value = { "/addMerchantMobileUser" }, method = RequestMethod.GET)
	public String addMerchantsMobileUser(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		// logger.info("about to add mobileuser");
		PageBean pageBean = new PageBean("AddMobileUser", "mobileuser/addmobileuser/addMerchantsMobileUser",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		logger.info("Login Person in dash Board:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		paginationBean.setCurrPage(currPage);
		// merchantService.listMerchant(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/updateMobileUser" }, method = RequestMethod.GET)
	public String updateMobileUser(final Model model, @ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
			final java.security.Principal principal, final HttpServletRequest request) {
		// logger.info("about to add mobileuser" );
		logger.info("update MobileUser by:" + principal.getName());
		List<Merchant> merchant1 = merchantService.loadMerchant();
		Long merchantId = null;
		for (Merchant t : merchant1) {
			if (t.getId() != null) {
				merchantId = t.getId();
				// logger.info("Merchant id: "+merchantId);
			}
		}
		PageBean pageBean = new PageBean("AddMobileUser", "mobileuser/updatemobileuser/updateMobileUser",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		List<KManager> kMgr = mobileUserService.loadKManager();
		Set<String> listRefNo = new HashSet<String>();
		for (KManager t : kMgr) {
			if (t.getRefNo() != null) {
				listRefNo.add(t.getRefNo());
				//logger.info("ref no: " + t.getRefNo());
			}
		}
		List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(merchantId);

		List<String> ezywiretidList = new ArrayList<String>();
		List<String> mototidList = new ArrayList<String>();
		List<String> ezypasstidList = new ArrayList<String>();
		List<String> ezyrectidList = new ArrayList<String>();
		for (MobileUser t : mobileuser) {
			if (t.getTid() != null) {
				ezywiretidList.add(t.getTid());
			}
		}
		
		for (MobileUser t : mobileuser) {
			if (t.getMotoTid() != null) {
				mototidList.add(t.getMotoTid());
			}
		}
		for (MobileUser t : mobileuser) {
			if (t.getEzypassTid() != null) {
				ezypasstidList.add(t.getEzypassTid());
			}

		}
		for (MobileUser t : mobileuser) {
			if (t.getEzyrecTid() != null) {
				ezyrectidList.add(t.getEzyrecTid());
			}

		}
		
		List<String> ummototidList = new ArrayList<String>();
		List<String> umezywaytidList = new ArrayList<String>();
		
		//update  DTL
		//List<MobileUser> ummobileuser = mobileUserService.loadUmMobileUserDetails(merchantId);
		
		for (MobileUser t : mobileuser) {
			if (t.getMotoTid() != null) {
				ummototidList.add(t.getMotoTid());
			}
		}
		
		
		for (MobileUser t : mobileuser) {
			if (t.getEzywayTid() != null) {
				umezywaytidList.add(t.getEzywayTid());
			}
		}
		
		regMobileUser.setUm_mototidList(ummototidList);
		regMobileUser.setUm_ezywaytidList(umezywaytidList);		
		regMobileUser.setEzywiretidList(ezywiretidList);
		regMobileUser.setMototidList(mototidList);
		regMobileUser.setEzypasstidList(ezypasstidList);
		regMobileUser.setEzyrectidList(ezyrectidList);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", regMobileUser); // RegMobileUser
		// model.addAttribute("merchantNameList", merchantNameList);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("refNoList", listRefNo);
		// }

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/findMobileuserDetails" }, method = RequestMethod.GET)
	public String finduserDetailstoUpdate(final Model model,

			@RequestParam("id") Long id, final java.security.Principal principal) {
		
		logger.info("/findMobileuserDetails: " + id+" by"+ principal.getName());
		PageBean pageBean = new PageBean("AddMobileUser", "mobileuser/updatemobileuser/updateMobileUser",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		Merchant midDetails = null;
		RegMobileUser regMobileUser = new RegMobileUser();
		if (id != null) {
			midDetails = mobileUserService.loadIserMidDetails(id);
		}

		/*if (midDetails != null) {
			logger.info("mid details: " + midDetails.getMid().getMid() + " : " + midDetails.getMid().getMotoMid()
					+ " : " + midDetails.getMid().getEzypassMid());
			logger.info(
					"mid details: " + midDetails.getMid().getEzywayMid() + " : " + midDetails.getMid().getEzyrecMid());
		}*/
		List<Merchant> merchant1 = merchantService.loadMerchant();
		List<KManager> kMgr = mobileUserService.loadKManager();
		Set<String> listRefNo = new HashSet<String>();
		for (KManager t : kMgr) {
			/*listRefNo.add("12345");
			listRefNo.add("12342");*/
			if (t.getRefNo() != null) {
				listRefNo.add(t.getRefNo());
				//logger.info("ref no: " + t.getRefNo());
			}
		}

		regMobileUser.setMid(midDetails.getMid().getMid());
		regMobileUser.setMotoMid(midDetails.getMid().getMotoMid());
		regMobileUser.setEzypassMid(midDetails.getMid().getEzypassMid());
		regMobileUser.setEzyrecMid(midDetails.getMid().getEzyrecMid());
		
		regMobileUser.setUm_tid(midDetails.getMid().getUmMid());
		regMobileUser.setUm_motoMid(midDetails.getMid().getUmMotoMid());
		regMobileUser.setUm_ezypassMid(midDetails.getMid().getUmEzypassMid());
		regMobileUser.setUm_ezyrecMid(midDetails.getMid().getUmEzyrecMid());
		regMobileUser.setUm_ezywayMid(midDetails.getMid().getUmEzywayMid());
		
		
		regMobileUser.setMerchantusername(midDetails.getUsername());
		regMobileUser.setBusinessName(midDetails.getBusinessName());
		regMobileUser.setContactName(midDetails.getContactPersonName());

		/*
		 * List<TerminalDetails> ezywiretidList1 = transactionService
		 * .getTerminalDetails(midDetails.getMid().getMid()); List<TerminalDetails>
		 * mototidList1 = transactionService
		 * .getTerminalDetails(midDetails.getMid().getMotoMid());
		 */

		// logger.info("merchant id: "+id);

		List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(id);

		List<String> ezywiretidList = new ArrayList<String>();
		List<String> mototidList = new ArrayList<String>();
		List<String> ezypasstidList = new ArrayList<String>();
		List<String> ezyrectidList = new ArrayList<String>();

		for (MobileUser t : mobileuser) {
			if (t.getTid() != null) {
				ezywiretidList.add(t.getTid());
				
				//logger.info("ezywire tid: " + t.getTid());
			}
		}
		for (MobileUser t : mobileuser) {
			if (t.getMotoTid() != null) {
				mototidList.add(t.getMotoTid());
				
				//logger.info("moto tid: " + t.getMotoTid());
			}
		}
		for (MobileUser t : mobileuser) {
			if (t.getEzypassTid() != null) {
				ezypasstidList.add(t.getEzypassTid());
				
				//logger.info("ezypass tidlist: " + t.getEzypassTid());
			}

		}
		for (MobileUser t : mobileuser) {
			if (t.getEzyrecTid() != null) {
				ezyrectidList.add(t.getEzyrecTid());
				
				//logger.info("ezyrec tidlist: " + t.getEzyrecTid());
			}

		}
		
		List<String> ummototidList = new ArrayList<String>();
		List<String> umezywaytidList = new ArrayList<String>();
		for (MobileUser t : mobileuser) {
			if (t.getMotoTid() != null) {
				ummototidList.add(t.getMotoTid());
			}
		}
		
		for (MobileUser t : mobileuser) {
			if (t.getEzywayTid() != null) {
				umezywaytidList.add(t.getEzywayTid());
			}
		}
		
		regMobileUser.setUm_mototidList(ummototidList);
		regMobileUser.setUm_ezywaytidList(umezywaytidList);
		
		
		regMobileUser.setMototidList(mototidList);
		regMobileUser.setEzypasstidList(ezypasstidList);
		regMobileUser.setEzyrectidList(ezyrectidList);
		regMobileUser.setEzywiretidList(ezywiretidList);
		
		
		/*logger.info("moto size: "+regMobileUser.getMototidList().size());
		logger.info("getEzywiretidList size: "+regMobileUser.getEzywiretidList().size());
		logger.info("getEzypasstidList size: "+regMobileUser.getEzypasstidList().size());
		logger.info("getEzyrectidList size: "+regMobileUser.getEzyrectidList().size());
		*/
		
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("midDetails", midDetails);
		model.addAttribute("mobileUser", regMobileUser);

		model.addAttribute("refNoList", listRefNo);
		// merchantService.listMerchant(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/findTidUserDetails" }, method = RequestMethod.GET)
	public String findEzywireuserDetailstoUpdate(final Model model,

			@RequestParam("tid") String tid, @RequestParam("mid") String mid, final java.security.Principal principal) {
		logger.info("/findTidUserDetails: " + tid);
		PageBean pageBean = new PageBean("AddMobileUser", "mobileuser/updatemobileuser/ByTidupdateMobileUser",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");

		RegMobileUser regMobileUser = new RegMobileUser();
		//logger.info("tid param: " + tid + " mid param: " + mid);

		Merchant midDetails = merchantService.loadMerchantbymid(mid);
		// Merchant midDetails=mobileUserService.loadIserMidDetails(mid);
		/*if (midDetails != null) {
			logger.info("mid details: " + midDetails.getMid().getMid() + " : " + midDetails.getMid().getMotoMid()
					+ " : " + midDetails.getMid().getEzypassMid());
		}*/
		List<Merchant> merchant1 = merchantService.loadMerchant();
		List<KManager> kMgr = mobileUserService.loadKManager();
		Set<String> listRefNo = new HashSet<String>();
		for (KManager t : kMgr) {
			
			
			/*listRefNo.add("12345");
			listRefNo.add("12342");*/
			if (t.getRefNo() != null) {

				listRefNo.add(t.getRefNo());
				// logger.info("ref no: "+t.getRefNo());
			}
		}
		
		List<UMKManager> umkMgr = mobileUserService.loadUmKManager();
		Set<String> um_listRefNo = new HashSet<String>();
		for (UMKManager t : umkMgr) {
			
			
			/*um_listRefNo.add("12345");
			um_listRefNo.add("12342");*/
			if (t.getRefNo() != null) {

				um_listRefNo.add(t.getRefNo());
				 logger.info("umref no: "+t.getRefNo());
			}
		}
		

		regMobileUser.setMid(midDetails.getMid().getMid());
		regMobileUser.setMotoMid(midDetails.getMid().getMotoMid());
		regMobileUser.setEzypassMid(midDetails.getMid().getEzypassMid());
		regMobileUser.setEzyrecMid(midDetails.getMid().getEzyrecMid());
		regMobileUser.setUm_mid(midDetails.getMid().getUmMid());
		regMobileUser.setUm_motoMid(midDetails.getMid().getUmMotoMid());
		regMobileUser.setUm_ezypassMid(midDetails.getMid().getUmEzypassMid());
		regMobileUser.setUm_ezyrecMid(midDetails.getMid().getUmEzyrecMid());
		//regMobileUser.setUm_ezywayMid(midDetails.getMid().getUmEzywayMid());
		
		regMobileUser.setTid(tid);
		regMobileUser.setBusinessName(midDetails.getBusinessName());
		regMobileUser.setContactName(midDetails.getContactPersonName());
		regMobileUser.setMerchantusername(midDetails.getUsername());

		List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(midDetails.getId());
		MobileUser mobileuserlist1 = mobileUserService.loadMobileUsertidDetails(tid);

		if (mobileuserlist1.getTid() != null) {
			//logger.info("check tid: " + mobileuserlist1.getTid());
			if (mobileuserlist1.getTid() == tid || mobileuserlist1.getTid().equals(tid)) {
				//logger.info("check username: " + mobileuserlist1.getUsername());
				regMobileUser.setEzywiremobusername(mobileuserlist1.getUsername());
				//logger.info("check ezywire mobusername: " + regMobileUser.getEzywiremobusername());
			}
		}
		if (regMobileUser.getMotoMid() != null) {
			if (mobileuserlist1.getMotoTid() != null) {
				//logger.info("moto user tid: " + mobileuserlist1.getMotoTid());
				regMobileUser.setMotoTid(mobileuserlist1.getMotoTid());

			}
		}
		if (regMobileUser.getEzyrecMid() != null) {
			if (mobileuserlist1.getEzyrecTid() != null) {
				//logger.info("Ezyrec user tid: " + mobileuserlist1.getEzyrecTid());
				regMobileUser.setEzyrecTid(mobileuserlist1.getEzyrecTid());

			}
		}
		if (regMobileUser.getEzypassMid() != null) {
			if (mobileuserlist1.getEzypassTid() != null) {
				//logger.info("ezypass user tid: " + mobileuserlist1.getEzypassTid());
				regMobileUser.setEzypassTid(mobileuserlist1.getEzypassTid());
			}

		}

		List<String> ezywiretidList = new ArrayList<String>();

		for (MobileUser t : mobileuser) {
			if (t.getTid() != null) {
				ezywiretidList.add(t.getTid());
				regMobileUser.setEzywiretidList(ezywiretidList);
				//logger.info("ezywire tid: " + t.getTid());
			}

		}

		//logger.info("Login Person in dash Board:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("tidList", mobileuserlist1);
		model.addAttribute("midDetails", midDetails);
		model.addAttribute("mobileUser", regMobileUser);

		model.addAttribute("refNoList", listRefNo);
		model.addAttribute("um_listRefNo", um_listRefNo);
		// merchantService.listMerchant(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/findMotoTidUserDetails" }, method = RequestMethod.GET)
	public String findMotouserDetailstoUpdate(final Model model,

			@RequestParam("motoTid") String motoTid, @RequestParam("motoMid") String motoMid,
			final java.security.Principal principal) {
		//logger.info("/findMotoTidUserDetails: " + motoTid);
		PageBean pageBean = new PageBean("AddMobileUser", "mobileuser/updatemobileuser/ByMotoTidupdateMobileUser",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");

		RegMobileUser regMobileUser = new RegMobileUser();
		//logger.info("tid param: " + motoTid + " mid param: " + motoMid);

		Merchant midDetails = merchantService.loadMerchantbyMotoMid(motoMid);
		// Merchant midDetails=mobileUserService.loadIserMidDetails(mid);
		/*if (midDetails != null) {
			logger.info("mid details: " + midDetails.getMid().getMid() + " : " + midDetails.getMid().getMotoMid()
					+ " : " + midDetails.getMid().getEzypassMid());
		}*/
		List<Merchant> merchant1 = merchantService.loadMerchant();
		List<KManager> kMgr = mobileUserService.loadKManager();
		Set<String> listRefNo = new HashSet<String>();
		for (KManager t : kMgr) {
			
			/*listRefNo.ad5");
			listRefNo.add("12342");*/
			
			
			if (t.getRefNo() != null) {
				listRefNo.add(t.getRefNo());
				// logger.info("ref no: "+t.getRefNo());
			}
		}

		regMobileUser.setMid(midDetails.getMid().getMid());
		regMobileUser.setMotoMid(midDetails.getMid().getMotoMid());
		regMobileUser.setEzypassMid(midDetails.getMid().getEzypassMid());
		regMobileUser.setEzyrecMid(midDetails.getMid().getEzyrecMid());
		regMobileUser.setBusinessName(midDetails.getBusinessName());
		regMobileUser.setContactName(midDetails.getContactPersonName());
		// regMobileUser.setTid(tid);

		regMobileUser.setMerchantusername(midDetails.getUsername());
		// logger.info("moto mid: "+regMobileUser.get);
		/*
		 * List<TerminalDetails> ezywiretidList1 = transactionService
		 * .getTerminalDetails(midDetails.getMid().getMid()); List<TerminalDetails>
		 * mototidList1 = transactionService
		 * .getTerminalDetails(midDetails.getMid().getMotoMid());
		 */

		// logger.info("merchant id: "+id);

		List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(midDetails.getId());
		MobileUser mobileuserlist1 = mobileUserService.loadMobileUserMototidDetails(motoTid);

		if (mobileuserlist1 != null) {
			if (regMobileUser.getMid() != null) {
				if (mobileuserlist1.getTid() != null) {
					//logger.info("tid: " + mobileuserlist1.getTid());
					regMobileUser.setTid(mobileuserlist1.getTid());

				}
			}

			if (regMobileUser.getMotoMid() != null) {
				if (mobileuserlist1.getMotoTid() != null) {
					//logger.info("moto user tid: " + mobileuserlist1.getMotoTid());
					regMobileUser.setMotoTid(mobileuserlist1.getMotoTid());
					if (mobileuserlist1.getMotoTid() == motoTid || mobileuserlist1.getMotoTid().equals(motoTid)) {
						//logger.info("check username: " + mobileuserlist1.getUsername());
						regMobileUser.setMotousername(mobileuserlist1.getUsername());
						//logger.info("check moto mobusername: " + regMobileUser.getMotousername());
					}
				}
			}
			if (regMobileUser.getEzyrecMid() != null) {
				if (mobileuserlist1.getEzyrecTid() != null) {
					//logger.info("Ezyrec user tid: " + mobileuserlist1.getEzyrecTid());
					regMobileUser.setEzyrecTid(mobileuserlist1.getEzyrecTid());
				}

			}
			if (regMobileUser.getEzypassMid() != null) {
				if (mobileuserlist1.getEzypassTid() != null) {
					//logger.info("ezypass user tid: " + mobileuserlist1.getEzypassTid());
					regMobileUser.setEzypassTid(mobileuserlist1.getEzypassTid());
				}

			}

		}

		List<String> mototidList = new ArrayList<String>();

		for (MobileUser t : mobileuser) {
			if (t.getMotoTid() != null) {
				mototidList.add(t.getMotoTid());
				regMobileUser.setMototidList(mototidList);
				//logger.info("moto tid: " + t.getMotoTid());
			}

		}

		//logger.info("Login Person in dash Board:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("tidList", mobileuserlist1);
		model.addAttribute("midDetails", midDetails);
		model.addAttribute("mobileUser", regMobileUser);

		//logger.info("merchant username at midbasedfind: " + regMobileUser.getMerchantName());
		model.addAttribute("refNoList", listRefNo);
		// merchantService.listMerchant(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/findEzyRecTidUserDetails" }, method = RequestMethod.GET)
	public String findEyRecuserDetailstoUpdate(final Model model,

			@RequestParam("ezyrecTid") String ezyrecTid, @RequestParam("ezyrecMid") String ezyrecMid,
			final java.security.Principal principal) {
		logger.info("/findEzyRecTidUserDetails: " + ezyrecTid);
		PageBean pageBean = new PageBean("AddMobileUser", "mobileuser/updatemobileuser/ByEzyRecTidupdateMobileUser",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");

		RegMobileUser regMobileUser = new RegMobileUser();
		//logger.info("EzyRec tid param: " + ezyrecTid + " EzyRec mid param: " + ezyrecMid);

		Merchant midDetails = merchantService.loadMerchantbyEzyRecMid(ezyrecMid);
		// Merchant midDetails=mobileUserService.loadIserMidDetails(mid);
		/*if (midDetails != null) {
			logger.info("mid details: " + midDetails.getMid().getMid() + " : " + midDetails.getMid().getMotoMid()
					+ " : " + midDetails.getMid().getEzypassMid() + " : " + midDetails.getMid().getEzyrecMid());
		}*/
		List<Merchant> merchant1 = merchantService.loadMerchant();
		List<KManager> kMgr = mobileUserService.loadKManager();
		Set<String> listRefNo = new HashSet<String>();
		for (KManager t : kMgr) {

			//listRefNo.add("12345");
			if (t.getRefNo() != null) {
				listRefNo.add(t.getRefNo());
				// logger.info("ref no: "+t.getRefNo());
			}
		}

		regMobileUser.setMid(midDetails.getMid().getMid());
		regMobileUser.setMotoMid(midDetails.getMid().getMotoMid());
		regMobileUser.setEzypassMid(midDetails.getMid().getEzypassMid());
		regMobileUser.setEzyrecMid(midDetails.getMid().getEzyrecMid());
		regMobileUser.setBusinessName(midDetails.getBusinessName());
		regMobileUser.setContactName(midDetails.getContactPersonName());
		// regMobileUser.setTid(tid);

		regMobileUser.setMerchantusername(midDetails.getUsername());
		
		List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(midDetails.getId());
		MobileUser mobileuserlist1 = mobileUserService.loadMobileUserEzyRectidDetails(ezyrecTid);

		if (mobileuserlist1 != null) {
			if (regMobileUser.getMid() != null) {
				if (mobileuserlist1.getTid() != null) {
					//logger.info("tid: " + mobileuserlist1.getTid());
					regMobileUser.setTid(mobileuserlist1.getTid());

				}
			}

			if (regMobileUser.getEzyrecMid() != null) {
				if (mobileuserlist1.getEzyrecTid() != null) {
					//logger.info("Ezyrec user tid: " + mobileuserlist1.getEzyrecTid());
					regMobileUser.setEzyrecTid(mobileuserlist1.getEzyrecTid());
					if (mobileuserlist1.getEzyrecTid() == ezyrecTid
							|| mobileuserlist1.getEzyrecTid().equals(ezyrecTid)) {
						//logger.info("check username: " + mobileuserlist1.getUsername());
						regMobileUser.setEzyrecusername(mobileuserlist1.getUsername());
						//logger.info("check Ezyrec mobusername: " + regMobileUser.getEzyrecusername());
					}
				}
			}

			if (regMobileUser.getMotoMid() != null) {
				if (mobileuserlist1.getMotoTid() != null) {
					//logger.info("moto user tid: " + mobileuserlist1.getMotoTid());
					regMobileUser.setMotoTid(mobileuserlist1.getMotoTid());
				}

			}
			if (regMobileUser.getEzypassMid() != null) {
				if (mobileuserlist1.getEzypassTid() != null) {
					//logger.info("ezypass user tid: " + mobileuserlist1.getEzypassTid());
					regMobileUser.setEzypassTid(mobileuserlist1.getEzypassTid());
				}

			}

		}

		List<String> ezyrectidList = new ArrayList<String>();
		for (MobileUser t : mobileuser) {
			if (t.getEzyrecTid() != null) {
				ezyrectidList.add(t.getEzyrecTid());
				regMobileUser.setEzyrectidList(ezyrectidList);
				//logger.info("ezyrec tidlist: " + t.getEzyrecTid());
			}

		}

		//logger.info("Login Person in dash Board:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("tidList", mobileuserlist1);
		model.addAttribute("midDetails", midDetails);
		model.addAttribute("mobileUser", regMobileUser);

		//logger.info("merchant username at midbasedfind: " + regMobileUser.getMerchantName());
		model.addAttribute("refNoList", listRefNo);
		// merchantService.listMerchant(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/findEzypassTidUserDetails" }, method = RequestMethod.GET)
	public String findEzypassuserDetailstoUpdate(final Model model,

			@RequestParam("ezypassTid") String ezypassTid, @RequestParam("ezypassMid") String ezypassMid,
			final java.security.Principal principal) {
		logger.info("/findEzypassTidUserDetails: " + ezypassTid);
		logger.info("Login Person in dash Board:" + principal.getName());
		PageBean pageBean = new PageBean("AddMobileUser", "mobileuser/updatemobileuser/ByEzypassTidupdateMobileUser",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");

		RegMobileUser regMobileUser = new RegMobileUser();
		//logger.info("tid param: " + ezypassTid + " mid param: " + ezypassMid);

		Merchant midDetails = merchantService.loadMerchantbyEzypassMid(ezypassMid);
		// Merchant midDetails=mobileUserService.loadIserMidDetails(mid);
		/*if (midDetails != null) {
			logger.info("mid details: " + midDetails.getMid().getMid() + " : " + midDetails.getMid().getMotoMid()
					+ " : " + midDetails.getMid().getEzypassMid());
		}*/
		List<Merchant> merchant1 = merchantService.loadMerchant();
		List<KManager> kMgr = mobileUserService.loadKManager();
		Set<String> listRefNo = new HashSet<String>();
		for (KManager t : kMgr) {




			//listRefNo.add("12345"); listRefNo.add("12344");
			 

			if (t.getRefNo() != null) {
				listRefNo.add(t.getRefNo());

			}
		}

		regMobileUser.setMid(midDetails.getMid().getMid());
		regMobileUser.setMotoMid(midDetails.getMid().getMotoMid());
		regMobileUser.setEzypassMid(midDetails.getMid().getEzypassMid());
		regMobileUser.setEzyrecMid(midDetails.getMid().getEzyrecMid());
		regMobileUser.setMerchantusername(midDetails.getUsername());
		regMobileUser.setBusinessName(midDetails.getBusinessName());
		regMobileUser.setContactName(midDetails.getContactPersonName());
		
		List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(midDetails.getId());
		MobileUser mobileuserlist1 = mobileUserService.loadMobileUserEzypasstidDetails(ezypassTid);

		if (mobileuserlist1 != null) {
			if (regMobileUser.getMid() != null) {
				if (mobileuserlist1.getTid() != null) {
					//logger.info("ezywire tid: " + mobileuserlist1.getTid());
					regMobileUser.setTid(mobileuserlist1.getTid());

				}
			}
			if (regMobileUser.getMotoMid() != null) {
				if (mobileuserlist1.getMotoTid() != null) {
					//logger.info("moto  tid: " + mobileuserlist1.getMotoTid());
					regMobileUser.setMotoTid(mobileuserlist1.getMotoTid());

				}
			}
			if (regMobileUser.getEzyrecMid() != null) {
				if (mobileuserlist1.getEzyrecTid() != null) {
					//logger.info("Ezyrec  tid: " + mobileuserlist1.getEzyrecTid());
					regMobileUser.setEzyrecTid(mobileuserlist1.getEzyrecTid());

				}
			}
			if (regMobileUser.getEzypassMid() != null) {
				if (mobileuserlist1.getEzypassTid() != null) {
					//logger.info("ezypass user tid: " + mobileuserlist1.getEzypassTid());
					regMobileUser.setEzypassTid(mobileuserlist1.getEzypassTid());
					if (mobileuserlist1.getEzypassTid() == ezypassTid
							|| mobileuserlist1.getEzypassTid().equals(ezypassTid)) {
						//logger.info("check username: " + mobileuserlist1.getUsername());
						regMobileUser.setEzypassusername(mobileuserlist1.getUsername());
						//logger.info("check ezypass mobusername: " + regMobileUser.getEzypassusername());
					}
				}

			}

		}

		List<String> ezypasstidList = new ArrayList<String>();

		for (MobileUser t : mobileuser) {
			if (t.getEzypassTid() != null) {
				ezypasstidList.add(t.getEzypassTid());
				regMobileUser.setEzypasstidList(ezypasstidList);
				
			}

		}

		
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("tidList", mobileuserlist1);
		model.addAttribute("midDetails", midDetails);
		model.addAttribute("mobileUser", regMobileUser);

		model.addAttribute("refNoList", listRefNo);
		// merchantService.listMerchant(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/updateMobileUserDetails" }, method = RequestMethod.GET)
	public String updateMobileUserSession(@ModelAttribute("mobileUser") RegMobileUser regMobileUser,

			final HttpServletRequest request, final Model model) {
		
		TerminalDetails ezywireDID = null;
		TerminalDetails motoDID = null;
		TerminalDetails ezypassDID = null;
		TerminalDetails ezyrecDID = null;

		PageBean pageBean = null;
		TerminalDetails ezywireTD = null;// ezywire
		TerminalDetails motoTD = null;// moto
		TerminalDetails ezypassTD = null;// ezypass
		TerminalDetails ezyrecTD = null;// ezyrec

		String responseDatatid = null;
		String responseDataezypasstid = null;
		String responseDatamototid = null;
		String responseDataezyrectid = null;

		String responseDatadeviceid = null;
		String responseDataepassdeviceid = null;
		String responseDatamotodeviceid = null;
		String responseDataezyrecdeviceid = null;

		List<Merchant> merchant1 = merchantService.loadMerchant();
		List<KManager> kMgr = mobileUserService.loadKManager();
		RegMobileUser regMobileUser1 =new RegMobileUser();
		Set<String> listRefNo = new HashSet<String>();
		for (KManager t : kMgr) {

			listRefNo.add("12345");
			listRefNo.add("12343");

			if (t.getRefNo() != null) {
				listRefNo.add(t.getRefNo());
				// logger.info("ref no: "+t.getRefNo());
			}
		}
		List<UMKManager> umkMgr = mobileUserService.loadUmKManager();
		Set<String> um_listRefNo = new HashSet<String>();
		for (UMKManager t : umkMgr) {
			
			
			/*um_listRefNo.add("12345");
			um_listRefNo.add("12342");*/
			if (t.getRefNo() != null) {

				um_listRefNo.add(t.getRefNo());
				 logger.info("umref no: "+t.getRefNo());
			}
		}
		try {
		
		
		
	
		regMobileUser=mobileUserService.setDataForUpdateMobileUser(regMobileUser);
		regMobileUser1 = mobileUserService.searchMobileUser(regMobileUser);
		
		
		if (!regMobileUser.isResTid() || !regMobileUser.isResDeviceID() ||
				!regMobileUser.isResMotoTid() || !regMobileUser.isResMotoDeviceID() ||
				!regMobileUser.isResEzypassTid() || !regMobileUser.isResEzypassDeviceID() ||
			//	!regMobileUser.isResEzywayTid() || !regMobileUser.isResEzywayDeviceID() ||
				!regMobileUser.isResEzyrecTid()  ||  !regMobileUser.isResEzyrecDeviceID() ||
				 !regMobileUser.isResUTid() || !regMobileUser.isResUDeviceID() ) {

		/*	model.addAttribute("responseDatatid", regMobileUser.isResTid() ? null : "Invalid TID");
			model.addAttribute("responseDatamototid", regMobileUser.isResMotoTid() ? null : "Invalid TID");
			model.addAttribute("responseDataezypasstid", regMobileUser.isResEzypassTid() ? null : "Invalid TID");
			model.addAttribute("responseDataezyrectid", regMobileUser.isResEzywayTid() ? null : "Invalid TID");
			model.addAttribute("responseDataezywaytid", regMobileUser.isResEzyrecTid() ? null : "Invalid TID");
		

			model.addAttribute("responseDatadeviceid", regMobileUser.isResDeviceID() ? null : "Invalid DeviceID");
			model.addAttribute("responseDatamotodeviceid", regMobileUser.isResEzypassDeviceID() ? null : "Invalid DeviceID");
			model.addAttribute("responseDataezypassdeviceid", regMobileUser.isResEzyrecDeviceID() ? null : "Invalid DeviceID");
			model.addAttribute("responseDataezywaydeviceid", regMobileUser.isResEzywayDeviceID() ? null : "Invalid DeviceID");
			model.addAttribute("responseDataezyrecdeviceid", regMobileUser.isResMotoDeviceID() ? null : "Invalid DeviceID");
			

		}
		
		
		
		
		if (responseDatatid != null || responseDatamototid != null || responseDataezypasstid != null
					|| responseDataezyrectid != null ||responseDatadeviceid != null || responseDatamotodeviceid != null 
					|| responseDataepassdeviceid != null || responseDataezyrecdeviceid != null) {
*/
				if (regMobileUser.getUpdateType().equals("ezywire")) {
					logger.info("iside udpated ezywire type");
					pageBean = new PageBean("UpdateMobileUser",
							"mobileuser/updatemobileuser/ByTidupdateMobileUserExist", Module.MOBILE_USER,
							"mobileuser/sideMenuMobileUser");
					

					
					List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(regMobileUser1.getId());
					List<String> ezywiretidList = new ArrayList<String>();

					for (MobileUser t : mobileuser) {
						if (t.getTid() != null) {
							ezywiretidList.add(t.getTid());
							regMobileUser.setEzywiretidList(ezywiretidList);
							
						}

					}
				
					logger.info("umtid: "+regMobileUser1.getUm_mid()+" tid: "+regMobileUser1.getUm_tid());

					if (!regMobileUser.isResMotoTid() || !regMobileUser.isResEzyrecTid() || !regMobileUser.isResEzypassTid() 
							|| !regMobileUser.isResEzypassDeviceID() || !regMobileUser.isResEzyrecDeviceID()
								|| !regMobileUser.isResMotoDeviceID() || !regMobileUser.isResUTid() || 
								!regMobileUser.isResUDeviceID()) {
						logger.info("umTid111: "+regMobileUser.getUm_tid()+"ummid1111 "+regMobileUser.getUm_mid());
							model.addAttribute("pageBean", pageBean);
							logger.info(Status.EXIST_DATA_BYEZYWIRE);
							throw new MobiException(Status.EXIST_DATA_BYEZYWIRE);

						}

				
					
					
				} else if (regMobileUser.getUpdateType().equals("moto")) {
					pageBean = new PageBean("AddMobileUser",
							"mobileuser/updatemobileuser/ByMotoTidupdateMobileUserExist", Module.MOBILE_USER,
							"mobileuser/sideMenuMobileUser");
					
					List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(regMobileUser1.getId());
					List<String> mototidList = new ArrayList<String>();

					for (MobileUser t : mobileuser) {
						if (t.getMotoTid() != null) {
							logger.info("check mototid list.."+t.getMotoTid());
							mototidList.add(t.getMotoTid());
							regMobileUser.setMototidList(mototidList);
							
						}

					}

					if (regMobileUser.isResDeviceID() || !regMobileUser.isResEzypassDeviceID() || !regMobileUser.isResEzyrecDeviceID() 
							|| !regMobileUser.isResTid() || !regMobileUser.isResEzypassTid() || !regMobileUser.isResEzyrecTid()) {
							pageBean = new PageBean("AddMobileUser",
									"mobileuser/updatemobileuser/ByMotoTidupdateMobileUserExist", Module.MOBILE_USER,
									"mobileuser/sideMenuMobileUser");
							model.addAttribute("pageBean", pageBean);
							throw new MobiException(Status.EXIST_DATA_BYMOTO);

						}

					
				} else if (regMobileUser.getUpdateType().equals("ezyrec")) {
					pageBean = new PageBean("AddMobileUser",
							"mobileuser/updatemobileuser/ByEzyRecTidupdateMobileUserExist", Module.MOBILE_USER,
							"mobileuser/sideMenuMobileUser");
					
					logger.info("mobileuser: " + regMobileUser1.getId());
					List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(regMobileUser1.getId());
					List<String> ezyrectidList = new ArrayList<String>();

					for (MobileUser t : mobileuser) {
						if (t.getEzyrecTid() != null) {
							ezyrectidList.add(t.getEzyrecTid());
							regMobileUser.setEzyrectidList(ezyrectidList);
							
						}

					}

					if (!regMobileUser.isResTid() || !regMobileUser.isResMotoTid() || !regMobileUser.isResEzypassTid() ||
							!regMobileUser.isResDeviceID() || !regMobileUser.isResMotoDeviceID()
								|| !regMobileUser.isResEzypassDeviceID()) {
							pageBean = new PageBean("AddMobileUser",
									"mobileuser/updatemobileuser/ByEzyRecTidupdateMobileUserExist", Module.MOBILE_USER,
									"mobileuser/sideMenuMobileUser");
							model.addAttribute("pageBean", pageBean);
							throw new MobiException(Status.EXIST_DATA_BYEZYREC);
						}

					
					
				} else if (regMobileUser.getUpdateType().equals("ezypass")) {
					pageBean = new PageBean("AddMobileUser",
							"mobileuser/updatemobileuser/ByEzypassTidupdateMobileUserExist", Module.MOBILE_USER,
							"mobileuser/sideMenuMobileUser");
					

					
					List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(regMobileUser1.getId());
					List<String> ezypasstidList = new ArrayList<String>();

					for (MobileUser t : mobileuser) {
						if (t.getEzypassTid() != null) {
							ezypasstidList.add(t.getEzypassTid());
							regMobileUser.setEzypasstidList(ezypasstidList);
							
						}

					}

					if (!regMobileUser.isResTid() || !regMobileUser.isResMotoTid() || !regMobileUser.isResEzyrecTid() ||
							!regMobileUser.isResDeviceID() || !regMobileUser.isResMotoDeviceID()
								|| !regMobileUser.isResEzyrecDeviceID()) {
							pageBean = new PageBean("AddMobileUser",
									"mobileuser/updatemobileuser/ByEzypassTidupdateMobileUserExist", Module.MOBILE_USER,
									"mobileuser/sideMenuMobileUser");
							model.addAttribute("pageBean", pageBean);
							model.addAttribute("mobileUser", regMobileUser);
							throw new MobiException(Status.EXIST_DATA_BYEZYPASS);
						}

				
				}
				
				
				
				model.addAttribute("mobileUser", regMobileUser);
				model.addAttribute("pageBean", pageBean);
				throw new MobiException(Status.INVALID_TID_DETAILS);

			}
		
		
		

		if (regMobileUser.getUpdateType() == null) {
			pageBean = new PageBean("UpdateMobileUser", "mobileuser/updatemobileuser/updateMobileUser",
					Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
			
			model.addAttribute("responseData", "UPDATE_TYPE_NULL"); // table // response
			model.addAttribute("pageBean", pageBean);
			throw new MobiException(Status.EMPTY_UPDATE_TYPE);
		}
		

	

		}
		catch (MobiException e) {
			// tid response data
			e.printStackTrace();
			model.addAttribute("responseDatatid", regMobileUser.isResTid()?null:"Invalid TID");
			model.addAttribute("responseDatamototid", regMobileUser.isResMotoTid()?null:"Invalid TID");
			model.addAttribute("responseDataezypasstid", regMobileUser.isResEzypassTid()?null:"Invalid TID");
			model.addAttribute("responseDataezyrectid", regMobileUser.isResEzyrecTid()?null:"Invalid TID");
			model.addAttribute("responseDataumtid", regMobileUser.isResUTid()?null:"Invalid TID");

			// deviceID response data
			model.addAttribute("responseDataumdeviceid", regMobileUser.isResUDeviceID()?null:"Invalid DeviceID");
			model.addAttribute("responseDatadeviceid", regMobileUser.isResDeviceID()?null:"Invalid DeviceID");
			model.addAttribute("responseDatamotodeviceid", regMobileUser.isResMotoDeviceID()?null:"Invalid DeviceID");
			model.addAttribute("responseDataezypassdeviceid", regMobileUser.isResEzypassDeviceID()?null:"Invalid DeviceID");
			model.addAttribute("responseDataezyrecdeviceid", regMobileUser.isResEzyrecDeviceID()?null:"Invalid DeviceID");
			logger.info("umTid: "+regMobileUser.getUm_tid()+"ummid"+regMobileUser.getUm_mid());
			model.addAttribute("mobileUser", regMobileUser);
			model.addAttribute("merchant1", merchant1);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("refNoList", listRefNo);
			model.addAttribute("um_listRefNo", um_listRefNo);
			return TEMPLATE_DEFAULT;
		}
		
	

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("regMobileUser", regMobileUser);
		model.addAttribute("mobileUser", regMobileUser1); // RegMobileUser
		
		// model.addAttribute("mobileUser");
		// PCI

		request.getSession(true).setAttribute("updateMobileUserSession", regMobileUser1);

		return "redirect:/mobileUser/reviewandConfirmupdateMobuser";
	}

	@RequestMapping(value = { "/reviewandConfirmupdateMobuser" }, method = RequestMethod.GET)
	public String updateMobileUserReviewandConfirm(final Model model, final HttpServletRequest request,
			final RegMobileUser regMobileUser) {
		logger.info("review mobileUser updates");

		RegMobileUser mobileUser = (RegMobileUser) request.getSession(true).getAttribute("updateMobileUserSession");

		// logger.info("ezypassmid: "+mobileUser.getEzypassmid()+" moto mid:
		// "+mobileUser.getMotoMid());
		// logger.info("/reviewandConfirm-- moto mid: "+mobileUser.getMotoMid());
		if (mobileUser == null) {
			return "redirect:/mobileUser/updateMobileUser";
		}

		mobileUser = mobileUserService.searchMobileUser(mobileUser);

		
		PageBean pageBean = new PageBean("Mobile user add Details",
				"mobileuser/updatemobileuser/updateMobileUserReviewandConfirm", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;
		// return "redirect:"+URL_BASE+"/list/1";
	}

	@RequestMapping(value = { "/mobileUserDetailsConfirmtoUpdate" }, method = RequestMethod.POST)
	public String confirmUpdateMobileUser(@ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
			final Model model, final java.security.Principal principal, final HttpServletRequest request) {
		logger.info("confirm to update--MobileUser added by : " + principal.getName());
		PageBean pageBean = new PageBean("Succefully New MobileUser Added",
				"mobileuser/updatemobileuser/addMobileUserAlldoneSuccessful", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		model.addAttribute("pageBean", pageBean);

		RegMobileUser mobileUserSavedInHttpSession = (RegMobileUser) request.getSession(true)
				.getAttribute("updateMobileUserSession");

		if (mobileUserSavedInHttpSession == null) {
			return "redirect:/mobileUser/updateMobileUser";
		}

		RegMobileUser mobileuser = mobileUserService.updateMobileUser(mobileUserSavedInHttpSession);
		String mobileUserName = null;
		if (mobileUserSavedInHttpSession.getDeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getDeviceId();
		}else if (mobileUserSavedInHttpSession.getMotodeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getMotodeviceId();
		}else if (mobileUserSavedInHttpSession.getEzypassdeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getEzypassdeviceId();
		}else if (mobileUserSavedInHttpSession.getEzyrecdeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getEzyrecdeviceId();
		}else if (mobileUserSavedInHttpSession.getEzywaydeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getEzywaydeviceId();
		}else if (mobileUserSavedInHttpSession.getUm_deviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getUm_deviceId();
		}else if (mobileUserSavedInHttpSession.getUm_motodeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getUm_motodeviceId();
		}
		
		if (mobileUserName != null) {
			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(mobileUserName, principal.getName(),
					"updateMobileUser");
			if (auditTrail != null) {
				logger.info("MobileUser: " + auditTrail.getUsername() + "  Updated by Admin: "
						+ auditTrail.getModifiedBy());
			}

		}
		//logger.info("activationCode: "+mobileuser.getActivationCode()+" contactname: "+mobileuser.getContactName());
		model.addAttribute("mobileuser", mobileuser);
		model.addAttribute("contactname", mobileUserSavedInHttpSession.getContactName());
		request.getSession(true).removeAttribute("addMobileUserSession");

		return TEMPLATE_DEFAULT;
	}
	
	@RequestMapping(value = { "/addMobileUser/{idx}" }, method = RequestMethod.GET)
	public String addMobileUser(final Model model, @ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
			final java.security.Principal principal, final HttpServletRequest request, @PathVariable final long idx) {
		
		logger.info("01 Ready to lauch Add Mobiluser :" + principal.getName());
		
		List<Merchant> merchant1 = merchantService.loadMerchantByAdmin();
		logger.info("Total Number of Merchants size: "+merchant1.size());
		
		PageBean pageBean = new PageBean("AddMobileUser", "mobileuser/addmobileuser/addMobileUser", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		
		List<KManager> kMgr = mobileUserService.loadKManager();
		Set<String> listRefNo = new HashSet<String>();
		for (KManager t : kMgr) {
			if (t.getRefNo() != null) {
				listRefNo.add(t.getRefNo());
				// logger.info("ref no: " + t.getRefNo());
			}
		}
		
		List<UMKManager> umkMgr = mobileUserService.loadUmKManager();
		Set<String> listUmRefNo = new HashSet<String>();
		for (UMKManager t : umkMgr) {
			if (t.getRefNo() != null) {
				listUmRefNo.add(t.getRefNo());
				// logger.info("ref no: " + t.getRefNo());
			}
		}

		
		String  err = (String) request.getSession(true).getAttribute("mobileAddErSession");
		
		if(err!=null) {
		if(err.equalsIgnoreCase("Yes")) {
			logger.info("err::::::" + err);
			model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
			request.getSession(true).removeAttribute("mobileAddErSession");
		}
		}
		
		model.addAttribute("umrefNoList", listUmRefNo);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", regMobileUser); // RegMobileUser
//		model.addAttribute("merchantNameList", merchantNameList);
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("refNoList", listRefNo);
		// }
		
		logger.info("Add Mobiluser launched ");

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/finduserDetails" }, method = RequestMethod.GET)
	public String finduserDetails(final Model model,@RequestParam("id") Long id, 
			final java.security.Principal principal) {
		
		logger.info("02 About to display mobile user by Host/Merchant type ");
		
		logger.info("Merchant ID:" + id);
		
		PageBean pageBean =null;
		RegMobileUser regMobileUser = new RegMobileUser();
		Set<String> listRefNo = new HashSet<String>();
		
		
		Merchant merchantDetails = mobileUserService.loadIserMidDetails(id);
		logger.info("Merchant Type:"+merchantDetails.getMerchantType());
		
		if(merchantDetails.getMerchantType()!=null){
			
			if(merchantDetails.getMerchantType().equals("U")) {
				pageBean = new PageBean("RegisterUDevice", "mobileuser/addUmobileuser/addUMobileUser", Module.MOBILE_USER,
						"mobileuser/sideMenuMobileUser");
				regMobileUser.setMerchantType(merchantDetails.getMerchantType());
			}else {
				pageBean = new PageBean("RegisterDevice", "mobileuser/addmobileuser/addMobileUser", Module.MOBILE_USER,
						"mobileuser/sideMenuMobileUser");
				regMobileUser.setMerchantType("P");
			}
			
		}else {
			
			pageBean = new PageBean("RegisterDevice", "mobileuser/addmobileuser/addMobileUser", Module.MOBILE_USER,
					"mobileuser/sideMenuMobileUser");
			regMobileUser.setMerchantType("P");
			
		}
		List<Merchant> merchantList = merchantService.loadMerchant();
		
		List<KManager> kMgr = mobileUserService.loadKManager();
	
		for (KManager t : kMgr) {
			if (t.getRefNo() != null) {
				listRefNo.add(t.getRefNo());
				// logger.info("ref no: " + t.getRefNo());
			}
		}
		
		List<UMKManager> umkMgr = mobileUserService.loadUmKManager();
		Set<String> listUmRefNo = new HashSet<String>();
		for (UMKManager t : umkMgr) {
			if (t.getRefNo() != null) {
				listUmRefNo.add(t.getRefNo());
				// logger.info("ref no: " + t.getRefNo());
			}
		}

		regMobileUser.setContactName(merchantDetails.getContactPersonName());
		regMobileUser.setBusinessName(merchantDetails.getBusinessName());
		
		//Paydee MID details
		regMobileUser.setMid(merchantDetails.getMid().getMid());
		regMobileUser.setMotoMid(merchantDetails.getMid().getMotoMid());
		regMobileUser.setEzypassMid(merchantDetails.getMid().getEzypassMid());
		regMobileUser.setEzywayMid(merchantDetails.getMid().getEzywayMid());
		regMobileUser.setEzyrecMid(merchantDetails.getMid().getEzyrecMid());
		
		
		//UMobile MID details.
		regMobileUser.setUm_mid(merchantDetails.getMid().getUmMid());
		regMobileUser.setUm_motoMid(merchantDetails.getMid().getUmMotoMid());
		regMobileUser.setUm_ezyrecMid(merchantDetails.getMid().getUmEzyrecMid());
		regMobileUser.setUm_ezywayMid(merchantDetails.getMid().getUmEzywayMid());
		regMobileUser.setUm_ezypassMid(merchantDetails.getMid().getUmEzypassMid());

		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("merchant1", merchantList);
		model.addAttribute("merchantDetails", merchantDetails);
		model.addAttribute("mobileUser", regMobileUser);

		model.addAttribute("refNoList", listRefNo);
		model.addAttribute("umrefNoList", listUmRefNo);
		// merchantService.listMerchant(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		
		logger.info("Selected Merchant displayed for mobile user registration ");
		
		return TEMPLATE_DEFAULT;
	}
	
	@RequestMapping(value = { "/regMobileUserDetails" }, method = RequestMethod.GET)
	public String addMobileUserSession(@ModelAttribute("mobileUser") RegMobileUser regMobileUser,
			final HttpServletRequest request, final Model model, final java.security.Principal principal) {
		
		logger.info("03 About to verify the user by:"+principal.getName());
		
		PageBean pageBean = null;
		String responseDatatid = null;
		String responseDatamototid = null;
		String responseDataezypasstid = null;
		String responseDataezywaytid = null;
		String responseDataezyrectid = null;

		TerminalDetails ezywireDevice = null;
		TerminalDetails motoDevice = null;
		TerminalDetails ezypassDevice = null;
		TerminalDetails ezywayDevice = null;
		TerminalDetails ezyrecDevice = null;

		TerminalDetails ezywireTid = null;
		TerminalDetails motoTid = null;
		TerminalDetails ezypassTid = null;
		TerminalDetails ezywayTid = null;
		TerminalDetails ezyrecTid = null;

		String responseDatadeviceid = null;
		String responseDatamotodeviceid = null;
		String responseDataezypassdeviceid = null;
		String responseDataezywaydeviceid = null;
		String responseDataezyrecdeviceid = null;

		RegMobileUser regMobileUser1 = new RegMobileUser();
		List<Merchant> merchant1 = merchantService.loadMerchant();
		List<KManager> kMgr = mobileUserService.loadKManager();
		Set<String> listRefNo = new HashSet<String>();
		for (KManager t : kMgr) {
			if (t.getRefNo() != null) {
				listRefNo.add(t.getRefNo());
				// logger.info("ref no: "+t.getRefNo());
			}
		}

		/*
		 * logger.info("tid: "+regMobileUser.getTid()==null);
		 * logger.info("tid: "+regMobileUser.getTid().isEmpty());
		 * logger.info("motoTid: "+regMobileUser.getMotoTid()==null);
		 * logger.info("motoTid: "+regMobileUser.getMotoTid().isEmpty());
		 * logger.info("ezypassTid: "+regMobileUser.getEzypassTid()==null);
		 * logger.info("ezypassTid: "+regMobileUser.getEzypassTid().isEmpty());
		 * logger.info("ezywayTid: "+regMobileUser.getEzywayTid()==null);
		 * logger.info("ezywayTid: "+regMobileUser.getEzywayTid().isEmpty());
		 * logger.info("ezyrecTid: "+regMobileUser.getEzyrecTid()==null);
		 * logger.info("ezyrecTid: "+regMobileUser.getEzyrecTid().isEmpty());
		 */
		try {

			if (regMobileUser.getTid() == null && regMobileUser.getMotoTid() == null
					&& regMobileUser.getEzypassTid() == null && regMobileUser.getEzywayTid() == null
					&& regMobileUser.getEzyrecTid() == null ) {

				// logger.info("Empty TID Fields..");

				model.addAttribute("responseEmptyTID", "Empty TID Fields..");

				throw new MobiException(Status.EMPTY_TID);

			}
			
		/*	if (regMobileUser.getTid().isEmpty() && regMobileUser.getMotoTid().isEmpty()
					&& regMobileUser.getEzypassTid().isEmpty() && regMobileUser.getEzywayTid().isEmpty()
					&& regMobileUser.getEzyrecTid().isEmpty() ) {

				// logger.info("Empty TID Fields..");

				model.addAttribute("responseEmptyTID", "Empty TID Fields..");

				throw new MobiException(Status.EMPTY_TID);

			}*/

			if (regMobileUser.getDeviceId() == null && regMobileUser.getMotodeviceId() == null
					&& regMobileUser.getEzypassdeviceId() == null && regMobileUser.getEzywaydeviceId() == null
					&& regMobileUser.getEzyrecdeviceId() == null ) {

				logger.info("Empty DeviceID Fields..");
				model.addAttribute("responseEmptyDeviceID", "Empty DeviceID Fields..");

				throw new MobiException(Status.EMPTY_DEVICEID);

			}
			
			regMobileUser=mobileUserService.setMobileUserData(regMobileUser);

			logger.info("validate tid: "+regMobileUser.isResTid());
			logger.info("validate MotoTid: "+regMobileUser.isResMotoTid());
			logger.info("validate EzypassTid: "+regMobileUser.isResEzypassTid());
			logger.info("validate EzywayTid: "+regMobileUser.isResEzywayTid());
			logger.info("validate EzyrecTid: "+regMobileUser.isResEzyrecTid());
			
			logger.info("validate DeviceID: "+regMobileUser.isResDeviceID());
			logger.info("validate MotoDeviceID: "+regMobileUser.isResMotoDeviceID());
			logger.info("validate EzypassDeviceID: "+regMobileUser.isResEzypassDeviceID());
			logger.info("validate EzywayDeviceID: "+regMobileUser.isResEzywayDeviceID());
			logger.info("validate EzyrecDeviceID: "+regMobileUser.isResEzyrecDeviceID());
			
			if (!regMobileUser.isResTid() || !regMobileUser.isResMotoTid() || !regMobileUser.isResEzypassTid() ||
					!regMobileUser.isResEzywayTid() || !regMobileUser.isResEzyrecTid() ||
					!regMobileUser.isResDeviceID() || !regMobileUser.isResEzypassDeviceID() || !regMobileUser.isResEzyrecDeviceID()
					|| !regMobileUser.isResMotoDeviceID() || !regMobileUser.isResEzywayDeviceID()) {

				model.addAttribute("responseDatadeviceid", regMobileUser.isResDeviceID() ? null : regMobileUser.getRespDeviceID());
				model.addAttribute("responseDataezypassdeviceid", regMobileUser.isResEzypassDeviceID() ? null : regMobileUser.getRespEzypassDeviceID());
				model.addAttribute("responseDataezyrecdeviceid", regMobileUser.isResEzyrecDeviceID() ? null : regMobileUser.getRespEzyrecDeviceID());
				model.addAttribute("responseDataezywaydeviceid", regMobileUser.isResEzywayDeviceID() ? null : regMobileUser.getRespEzywayDeviceID());
				model.addAttribute("responseDatamotodeviceid", regMobileUser.isResMotoDeviceID() ? null : regMobileUser.getRespMotoDeviceID());
				model.addAttribute("responseDatatid", regMobileUser.isResTid() ? null : regMobileUser.getRespTid());
				model.addAttribute("responseDatamototid", regMobileUser.isResMotoTid() ? null : regMobileUser.getRespMotoTid());
				model.addAttribute("responseDataezypasstid", regMobileUser.isResEzypassTid() ? null : regMobileUser.getRespEzypassTid());
				model.addAttribute("responseDataezywaytid", regMobileUser.isResEzywayTid() ? null : regMobileUser.getRespEzywayTid());
				model.addAttribute("responseDataezyrectid", regMobileUser.isResEzyrecTid() ? null : regMobileUser.getRespEzyrecTid());
				throw new MobiException(Status.EXIST_TID_OR_DEVICEID_DETAILS);
			}
			
			regMobileUser1 = mobileUserService.searchMobileUseradd(regMobileUser);

			/*responseDatatid = null;
			responseDatamototid = null;
			responseDataezypasstid = null;
			responseDataezywaytid = null;
			responseDataezyrectid = null;

			responseDatadeviceid = null;
			responseDatamotodeviceid = null;
			responseDataezypassdeviceid = null;
			responseDataezywaydeviceid = null;
			responseDataezyrecdeviceid = null;*/

			/*if (regMobileUser.getTid() != null) {

				ezywireTid = mobileUserService.loadTerminalDetailsByTid(regMobileUser.getTid());
				
				responseDatatid = ezywireTid != null ? "Tid already exist" : null;
				if (regMobileUser.getDeviceId() != null && !(regMobileUser.getDeviceId().isEmpty())) {
					logger.info("ezywire device id to check:" + regMobileUser.getDeviceId() + ": "
							+ regMobileUser.getDeviceId());
					ezywireDevice = mobileUserService.loadDeviceId(regMobileUser.getDeviceId());
					
					responseDatadeviceid = ezywireDevice != null ? "DeviceID already exist" : null;
					
				}
			}

			if (regMobileUser.getMotoTid() != null) {

				motoTid = mobileUserService.loadTerminalDetailsByTid(regMobileUser.getMotoTid());
				
				responseDatamototid = motoTid != null ? "Tid already exist" : null;
				if (regMobileUser.getMotodeviceId() != null && !(regMobileUser.getMotodeviceId().isEmpty())) {
					logger.info("moto device id to check:" + regMobileUser.getMotodeviceId());
					motoDevice = mobileUserService.loadDeviceId(regMobileUser.getMotodeviceId());
					
					responseDatamotodeviceid = motoDevice != null ? "DeviceID already exist" : null;
				}
			}
			if (regMobileUser.getEzypassTid() != null) {

				ezypassTid = mobileUserService.loadTerminalDetailsByTid(regMobileUser.getEzypassTid());
				
				responseDataezypasstid = ezypassTid != null ? "Tid already exist" : null;
				if (regMobileUser.getEzypassdeviceId() != null && !(regMobileUser.getEzypassdeviceId().isEmpty())) {
					logger.info("ezypass device id to check:" + regMobileUser.getEzypassdeviceId());
					ezypassDevice = mobileUserService.loadDeviceId(regMobileUser.getEzypassdeviceId());
					
					responseDataezypassdeviceid = ezypassDevice != null ? "DeviceID already exist" : null;
				}
			}
			if (regMobileUser.getEzywayTid() != null) {

				ezywayTid = mobileUserService.loadTerminalDetailsByTid(regMobileUser.getEzywayTid());
				
				responseDataezywaytid = ezywayTid != null ? "Tid already exist" : null;
				if (regMobileUser.getEzywaydeviceId() != null && !(regMobileUser.getEzywaydeviceId().isEmpty())) {
					logger.info("ezyway device id to check:" + regMobileUser.getEzywaydeviceId());
					ezywayDevice = mobileUserService.loadDeviceId(regMobileUser.getEzywaydeviceId());
					
					responseDataezywaydeviceid = ezywayDevice != null ? "DeviceID already exist" : null;
				}
			}
			if (regMobileUser.getEzyrecTid() != null) {

				ezyrecTid = mobileUserService.loadTerminalDetailsByTid(regMobileUser.getEzyrecTid());
			
				responseDataezyrectid = ezyrecTid != null ? "Tid already exist" : null;
				if (regMobileUser.getEzyrecdeviceId() != null && !(regMobileUser.getEzyrecdeviceId().isEmpty())) {
					logger.info("ezyrec device id to check:" + regMobileUser.getEzyrecdeviceId());
					ezyrecDevice = mobileUserService.loadDeviceId(regMobileUser.getEzyrecdeviceId());
				
						responseDataezyrecdeviceid = ezyrecDevice != null ? "DeviceID already exist" : null;
					
				}
			}*/

			
			/*
			if (ezywireDevice != null || motoDevice != null || ezypassDevice != null || ezypassDevice != null
					|| ezyrecDevice != null || ezywireTid != null || motoTid != null || ezypassTid != null
					|| ezywayTid != null || ezyrecTid != null) {

				model.addAttribute("responseDatatid", responseDatatid);
				model.addAttribute("responseDatamototid", responseDatamototid);
				model.addAttribute("responseDataezypasstid", responseDataezypasstid);
				model.addAttribute("responseDataezywaytid", responseDataezywaytid);
				model.addAttribute("responseDataezyrectid", responseDataezyrectid);
				model.addAttribute("responseDatadeviceid", responseDatadeviceid);
				model.addAttribute("responseDatamotodeviceid", responseDatamotodeviceid);
				model.addAttribute("responseDataezypassdeviceid", responseDataezypassdeviceid);// table response
				model.addAttribute("responseDataezywaydeviceid", responseDataezywaydeviceid);
				model.addAttribute("responseDataezyrecdeviceid", responseDataezyrecdeviceid);

				throw new MobiException(Status.EXIST_TID_OR_DEVICEID_DETAILS);
			}*/
			
			 BaseDataImpl  baseData = new BaseDataImpl();
			 RegMobileUser  a =baseData.vaildated(regMobileUser);
				if(a != null) {
					logger.info("Contains HTML tags");
					request.getSession(true).setAttribute("mobileAddErSession", "yes");
					return "redirect:/mobileUser/addMobileUser/1";
				}
			
			
		} catch (MobiException e) {

			logger.info("Exception: " + e.getMessage());
			/*pageBean = new PageBean("AddMobileUser", "mobileuser/addmobileuser/addMobileUserExist", Module.MOBILE_USER,
					"mobileuser/sideMenuMobileUser");*/
			pageBean = new PageBean("AddMobileUser", "mobileuser/addmobileuser/addMobileUser", Module.MOBILE_USER,
					"mobileuser/sideMenuMobileUser");
			model.addAttribute("responseDatadeviceid",e.getMessage());
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("mobileUser", regMobileUser);
			model.addAttribute("merchant1", merchant1);
			model.addAttribute("refNoList", listRefNo);
			return TEMPLATE_DEFAULT;
		}

		
		 BaseDataImpl  baseData = new BaseDataImpl();
		 RegMobileUser  a =baseData.vaildated(regMobileUser);
			if(a != null) {
				logger.info("Contains HTML tags");
				request.getSession(true).setAttribute("mobileAddErSession", "yes");
				return "redirect:/mobileUser/addMobileUser/1";
			}
		
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("regMobileUser", regMobileUser);
		model.addAttribute("mobileUser", regMobileUser1); // RegMobileUser

		request.getSession(true).setAttribute("addMobileUserSession", regMobileUser1);
		
		logger.info("Registered values forwarded");

		return "redirect:/mobileUser/reviewandConfirm";
	}

	/*
	 * //RegMobileUser
	 * 
	 * @RequestMapping(value = { "/addMobileUser/{idx}" }, method =
	 * RequestMethod.POST) public String
	 * addMobileUserSession(@ModelAttribute("mobileUser") final RegMobileUser
	 * regMobileUser, final HttpServletRequest request, final Model model ,
	 * 
	 * @RequestParam( "id" ) final Long idx ) {
	 * //logger.info("about to add mobileuser session");
	 * 
	 * RegMobileUser regMobileUser1 =
	 * mobileUserService.searchMobileUser(regMobileUser);
	 * 
	 * TerminalDetails terminalDetails =
	 * mobileUserService.loadDeviceId(regMobileUser.getDeviceId()); PageBean
	 * pageBean = null; if(terminalDetails!= null){ pageBean = new
	 * PageBean("AddMobileUser", "mobileuser/addmobileuser/addMobileUser",
	 * Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
	 * model.addAttribute("pageBean", pageBean); model.addAttribute("responseData",
	 * "DeviceID already exist"); //table response model.addAttribute("pageBean",
	 * pageBean); model.addAttribute("regMobileUser", regMobileUser);
	 * model.addAttribute("mobileUser",regMobileUser);
	 * 
	 * return TEMPLATE_DEFAULT; }else { PageBean pageBean1 = new
	 * PageBean("Merchant user add Details",
	 * "mobileuser/addmobileuser/addMobileUser", Module.MOBILE_USER,
	 * "mobileuser/sideMenuMobileUser"); model.addAttribute("responseData", null); }
	 * 
	 * model.addAttribute("pageBean", pageBean); model.addAttribute("regMobileUser",
	 * regMobileUser); model.addAttribute("mobileUser", regMobileUser1);
	 * //RegMobileUser
	 * 
	 * model.addAttribute("mobileUser");
	 * 
	 * request.getSession(true).setAttribute("addMobileUserSession",
	 * regMobileUser1);
	 * 
	 * return "redirect:/mobileUser/reviewandConfirm"; }
	 */

	@RequestMapping(value = { "/reviewandConfirm" }, method = RequestMethod.GET)
	public String addMobileUserReviewandConfirm(final Model model, final HttpServletRequest request,
			final RegMobileUser regMobileUser) {
		logger.info("04 About to display the value for review & confrim");

		RegMobileUser mobileUser = (RegMobileUser) request.getSession(true).getAttribute("addMobileUserSession");

		// logger.info("ezypassmid: "+mobileUser.getEzypassmid()+" moto mid:
		// "+mobileUser.getMotoMid());
		// logger.info("/reviewandConfirm-- moto mid: "+mobileUser.getMotoMid());
		if (mobileUser == null) {
			return "redirect:/mobileUser/addMobileUser";
		}

		mobileUserService.searchMobileUseradd(mobileUser);

		PageBean pageBean = new PageBean("Mobile user add Details",
				"mobileuser/addmobileuser/addMobileUserReviewandConfirm", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("pageBean", pageBean);
		
		logger.info("Values ready for review & confrim");
		return TEMPLATE_DEFAULT;
		// return "redirect:"+URL_BASE+"/list/1";
	}

	@RequestMapping(value = { "/mobileUserDetailsConfirm" }, method = RequestMethod.POST)
	public String confirmAddMobileUser(@ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
			final Model model, final java.security.Principal principal, final HttpServletRequest request) {
		
		logger.info("05 About to add Mobile User Details confrimed");
		logger.info(" Mobile User will add by : " + principal.getName());
		
		PageBean pageBean = new PageBean("Succefully New MobileUser Added",
				"mobileuser/addmobileuser/addMobileUserAlldoneSuccessful", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		model.addAttribute("pageBean", pageBean);
		RegMobileUser mobileUserSavedInHttpSession = (RegMobileUser) request.getSession(true)
				.getAttribute("addMobileUserSession");
		
		if (mobileUserSavedInHttpSession == null) {
			return "redirect:/mobileUser/addMobileUser";
		}
		
		
		 BaseDataImpl  baseData = new BaseDataImpl();
		 RegMobileUser  a =baseData.vaildated(mobileUserSavedInHttpSession);
			if(a != null) {
				logger.info("Contains HTML tags");
				request.getSession(true).setAttribute("mobileAddErSession", "yes");
				return "redirect:/mobileUser/addMobileUser/1";
			}
		
		RegMobileUser mobileuser = mobileUserService.addMobileUser(mobileUserSavedInHttpSession);
		String mobileUserName = null;
		if (mobileUserSavedInHttpSession.getDeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getDeviceId();
		}else if (mobileUserSavedInHttpSession.getMotodeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getMotodeviceId();
		}else if (mobileUserSavedInHttpSession.getEzypassdeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getEzypassdeviceId();
		}else if (mobileUserSavedInHttpSession.getEzyrecdeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getEzyrecdeviceId();
		}else if (mobileUserSavedInHttpSession.getEzywaydeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getEzywaydeviceId();
		}
		
		if(mobileUserName!=null) {
			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(mobileUserName, principal.getName(),
					"addMobileUser");
			if (auditTrail != null) {
				logger.info("MobileUser: " + auditTrail.getUsername() + "  Added by Admin: " + auditTrail.getModifiedBy());
			}

		}
		model.addAttribute("mobileuser", mobileuser);
		model.addAttribute("contactname", mobileUserSavedInHttpSession.getContactName());
		request.getSession(true).removeAttribute("addMobileUserSession");
		
		logger.info("Mobile User add process done");

		return TEMPLATE_DEFAULT;
	}

	
	
	// changed code

	@RequestMapping(value = { "/suspend/{id}" }, method = RequestMethod.GET)
	public String displaySuspendMobileUser(final Model model, @PathVariable final Long id,
			@ModelAttribute("mobileuserStatusHistory") final MobileUserStatusHistory mobileuserStatusHistory,
			@ModelAttribute("errorList") final ArrayList<String> errorList) {
		// logger.info("in suspend page ");
		PageBean pageBean = new PageBean("MobileUser Detail", "mobileuser/suspend/mobileUserSuspend",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		pageBean.addJS("mobileuser/suspend/mobileUserSuspend.js");
		MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);
		if (!CommonStatus.ACTIVE.equals(mobileUser.getStatus())) {
			return URL_BASE + "/detail/" + id;
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("now", new Date());
		model.addAttribute("mobileuserStatusHistory", mobileuserStatusHistory);
		model.addAttribute("errorList", errorList);

		return TEMPLATE_DEFAULT;
	}

	@ModelAttribute("reasonList")
	public List<String> populateReasonList() {
		List<String> reasonList = new ArrayList<String>();
		reasonList.add("Fraud Case Mobileuser");
		reasonList.add("Payment Delay");
		reasonList.add("Fake Registration");
		reasonList.add("Credit Card Fraud");
		reasonList.add("Other");
		return reasonList;
	}

	@RequestMapping(value = { "/suspend/doSuspend" }, method = RequestMethod.POST)
	public String doSuspendMobileUser(final Model model, @RequestParam final Long id, @RequestParam final String reason,
			@RequestParam final String description, final RedirectAttributes redirectAttributes) {
		logger.info("about to  dosuspend details of Mobileuser");
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
				MobileUserStatusHistory mobileuserStatusHistory = new MobileUserStatusHistory();
				mobileuserStatusHistory.setReason(reason);
				mobileuserStatusHistory.setDescription(description);

				redirectAttributes.addFlashAttribute("mobileuserStatusHistory", mobileuserStatusHistory);
				redirectAttributes.addFlashAttribute("errorList", errorList);

				return "redirect:" + URL_BASE + "/suspend/" + id;
			}

			mobileUserService.doSuspendMobileUser(id, reason, description);
			// return "redirect:" + URL_BASE + "/suspendDone?mode=success&id=" + id;
			return "redirect:" + URL_BASE + "/list/1";
		} catch (Exception e) {
			// FIXME log event to db and return eventID to controller for
			// reporting purpose.
			logger.error("error suspending mobileuser", e);
			// return "redirect:" + URL_BASE + "/suspendDone?mode=fail&id=" + id +
			// "&eventID=1";
			return "redirect:" + URL_BASE + "/list/1";

		}
	}

	@RequestMapping(value = { "/suspendDone" }, method = RequestMethod.GET)
	public String displaySuspendMobileUserDone(final Model model,

			@RequestParam final String mode, @RequestParam final long id,
			@RequestParam(required = false) final Long eventID) {
		logger.info("about to  suspendDone details of Mobileuser");
		PageBean pageBean = new PageBean("MobileUser Suspend Done",
				"mobileuser/suspend/mobileUserSuspendAlldoneSuccessful", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		boolean success = ("success".equals(mode));

		MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);
		MobileUserStatusHistory mobileuserStatusHistory = mobileUserService.loadMobileUserStatusByPk(mobileUser);
		model.addAttribute("mobileuserStatusHistory", mobileuserStatusHistory);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("success", success);
		model.addAttribute("eventID", eventID);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/unsuspend/{id}" }, method = RequestMethod.GET)
	public String displayUnSuspendMobileUser(final Model model, @PathVariable final Long id,
			@ModelAttribute("mobileuserStatusHistory") final MobileUserStatusHistory mobileuserStatusHistory,
			@ModelAttribute("errorList") final ArrayList<String> errorList) {
		logger.info("about to  unSuspend details of Mobileuser");
		PageBean pageBean = new PageBean("MobileUser Detail", "mobileuser/suspend/mobileUserUnsuspend",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		pageBean.addJS("mobileuser/suspend/mobileUserSuspend.js");

		MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);
		if (!CommonStatus.SUSPENDED.equals(mobileUser.getStatus())) {
			// MobileUser status isn't active, then how do we suspend
			// mobileUser?
			return URL_BASE + "/detail/" + id;
		}
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("pageBean", pageBean);

		model.addAttribute("now", new Date());
		model.addAttribute("mobileuserStatusHistory", mobileuserStatusHistory);
		model.addAttribute("errorList", errorList);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/unsuspend/dounSuspend" }, method = RequestMethod.POST)
	public String doUnSuspendMobileUser(final Model model, @RequestParam final Long id,
			@RequestParam final String reason, @RequestParam final String description,
			final RedirectAttributes redirectAttributes) {
		logger.info("about to  dounSuspend details of Mobileuser");

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
				MobileUserStatusHistory mobileuserStatusHistory = new MobileUserStatusHistory();
				mobileuserStatusHistory.setReason(reason);
				mobileuserStatusHistory.setDescription(description);

				redirectAttributes.addFlashAttribute("mobileuserStatusHistory", mobileuserStatusHistory);
				redirectAttributes.addFlashAttribute("errorList", errorList);
				return "redirect:" + URL_BASE + "/unsuspend/" + id;
			}

			mobileUserService.doUnSuspendMobileUser(id, reason, description);
			// return "redirect:" + URL_BASE + "/unsuspendDone?mode=success&id=" + id;
			return "redirect:" + URL_BASE + "/list/1";

		} catch (Exception e) {
			// FIXME log event to db and return eventID to controller for
			// reporting purpose.
			logger.error("error suspending mobileUser", e);
			return "redirect:" + URL_BASE + "/unsuspendDone?mode=fail&id=" + id + "&eventID=1";
		}
	}

	@RequestMapping(value = { "/unsuspendDone" }, method = RequestMethod.GET)
	public String displayUnSuspendMobileUserDone(final Model model,

			@RequestParam final String mode, @RequestParam final long id,
			@RequestParam(required = false) final Long eventID) {
		logger.info("about to  unSuspend Done details of Mobileuser");
		PageBean pageBean = new PageBean("MobileUser Un-Suspend Done",
				"mobileuser/suspend/mobileUserUnsuspendAlldoneSuccessful", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		boolean success = ("success".equals(mode));
		MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);
		MobileUserStatusHistory mobileuserStatusHistory = mobileUserService.loadMobileUserStatusByPk(mobileUser);
		model.addAttribute("mobileuserStatusHistory", mobileuserStatusHistory);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("success", success);
		model.addAttribute("eventID", eventID);

		return TEMPLATE_DEFAULT;
	}

	// new method for search condition in mobile user summary 18-04-2017
	@RequestMapping(value = { "/search1" }, method = RequestMethod.GET)
	public String displayTransactionList(final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list all  Transactiona");
		PageBean pageBean = new PageBean("transactions list", "mobileuser/mobileuserList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<MobileUser> paginationBean = new PaginationBean<MobileUser>();
		paginationBean.setCurrPage(currPage);

		mobileUserService.listMobileUserDetails(paginationBean, date, date1);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView getExcel(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false) String export) {
		/*
		 * public ModelAndView getExcel(final Model model, @RequestParam final String
		 * date,
		 * 
		 * @RequestParam(required = false, defaultValue = "1") final int currPage) {
		 */
		// List animalList = animalService.getAnimalList();
		logger.info("about to getExcel report data");
		/*
		 * PageBean pageBean = new PageBean("transactions list",
		 * "transaction/transactionList", Module.TRANSACTION,
		 * "transaction/sideMenuTransaction"); model.addAttribute("pageBean", pageBean);
		 * 
		 * PaginationBean<ForSettlement> paginationBean = new
		 * PaginationBean<ForSettlement>(); paginationBean.setCurrPage(currPage);
		 */
		// System.out.println(" Controller Date :"+ date);

		// PaginationBean<MobileUser> paginationBean = new PaginationBean<MobileUser>();
		// paginationBean.setCurrPage(currPage);
		// mobileUserService.listMobileUsers(paginationBean,date,date1);

		List<MobileUser> list = mobileUserService.mobileUserSummary(date, date1);
		for(MobileUser mob:list) {
			if(mob.getConnectType()!=null) {
				mob.setCreatedBy(mobileUserService.loadBusinessName(mob.getConnectType()));
			}
		}
		
		logger.info("check mobile user from date:" + date);

		logger.info("check mobile user to date:" + date1);
		System.out.println("export test:" + export);
		if (!(export.equals("PDF"))) {

			return new ModelAndView("txnMobileUserExcel", "txnList", list);

		} else {
			return new ModelAndView("txnMobileUserPdf", "txnList", list);
		}
	}

	@RequestMapping(value = { "/devicemap" }, method = RequestMethod.GET)
	public String displayDeviceMapping(final Model model, final java.security.Principal principal) {
		logger.info("about to device mapping");
		PageBean pageBean = new PageBean("merchant list", "devicemapping/mobileUserDevice", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		MobileUser mobileuser = mobileUserService.loadMobileUserDeviceMapping();

		logger.info("about to device mapping : " + mobileuser.getUsername());
		logger.info("about to device mapping : " + mobileuser.getTid());
		logger.info("about to device mapping : " + mobileuser.getMotoTid());
		logger.info("about to device mapping : " + mobileuser.getEzypassTid());

		model.addAttribute("mobileuser", mobileuser);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/updatedevicemap" }, method = RequestMethod.POST)
	public String displayUpdateMobileUserMap(final Model model, final HttpServletRequest request,
			@ModelAttribute("mobileuser") MobileUser mobileuser
	/*
	 * @RequestParam("id") final long id,
	 * 
	 * @RequestParam("mobileUserName") final String mobileUserName,
	 * 
	 * @RequestParam("preAuth") final String preAuth,
	 * 
	 * @RequestParam("status") final String status,
	 * 
	 * @RequestParam("tid") final String tid,
	 * 
	 * @RequestParam("motoTid") final String motoTid,
	 * 
	 * @RequestParam("ezypassTid") final String ezypassTid,
	 * 
	 * @RequestParam("ezyrecTid") final String ezyrecTid
	 */)

	/* @RequestParam("contactName") final String contactName) */

	{

		logger.info("about to  Edit Particular MobileUser   ");
		PageBean pageBean = new PageBean("MobileUser Detail", "devicemapping/mobileUserDeviceConfirm",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");

		logger.info("tid: " + mobileuser.getEzyrecTid() + mobileuser.getPreAuth());

		MobileUser mobiledata = mobileUserService.updateMobileUserTid(mobileuser);

		/*
		 * MobileUserData mobileUser = new MobileUserData(); mobileUser.setTid(tid);
		 * 
		 * mobileUser.setMobileUserName(mobileUserName);
		 * 
		 * mobileUser.setPreAuth(preAuth);
		 * 
		 * 
		 * mobileUser.setRemarks(remarks);
		 */

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobiledata);

		return TEMPLATE_DEFAULT;

	}
	
	
	//UMobile Device Register
	@RequestMapping(value = { "/regUMobileUserDetails" }, method = RequestMethod.GET)
	public String addUMobileUserSession(@ModelAttribute("mobileUser") RegMobileUser regMobileUser,
			final HttpServletRequest request, final Model model, final java.security.Principal principal) {
		
		logger.info("06 About to verify the user by:"+principal.getName());
		
		PageBean pageBean = null;
		String resDataum_tid = null;
		String resDataum_mototid = null;
		String resDataum_ezypasstid = null;
		String resDataum_ezywaytid = null;
		String resDataum_ezyrectid = null;

		TerminalDetails um_ezywireDevice = null;
		TerminalDetails um_motoDevice = null;
		TerminalDetails um_ezypassDevice = null;
		TerminalDetails um_ezywayDevice = null;
		TerminalDetails um_ezyrecDevice = null;

		TerminalDetails um_ezywireTid = null;
		TerminalDetails um_motoTid = null;
		TerminalDetails um_ezypassTid = null;
		TerminalDetails um_ezywayTid = null;
		TerminalDetails um_ezyrecTid = null;

		String resDataum_deviceid = null;
		String resDataum_motodeviceid = null;
		String resDataum_ezypassdeviceid = null;
		String resDataum_ezywaydeviceid = null;
		String resDataum_ezyrecdeviceid = null;
		
		String hashkey = regMobileUser.getHashkey();
		
		logger.info("HashKey :"+regMobileUser.getHashkey());
		logger.info("DTL :"+regMobileUser.getDTL());
		logger.info("Domain url :"+regMobileUser.getDomainUrl());
		logger.info("VCC :"+regMobileUser.getVcc());
		logger.info("HashKey1 :"+regMobileUser.getHashkey1());
		logger.info("DTL1 :"+regMobileUser.getDTL1());
		
		logger.info("recHashKey :"+regMobileUser.getRecHashkey());
		logger.info("recDTL :"+regMobileUser.getRecDTL());
		

		RegMobileUser regMobileUser1 = new RegMobileUser();
		/*regMobileUser1.setHashkey(regMobileUser.getHashkey());
		regMobileUser1.setDTL(regMobileUser.getDTL());
		regMobileUser1.setDomainUrl(regMobileUser.getDomainUrl());
		regMobileUser1.setHashkey1(regMobileUser.getHashkey1());
		regMobileUser1.setDTL1(regMobileUser.getDTL1());
		regMobileUser1.setVcc(regMobileUser.getVcc());*/
		
		
		
		List<Merchant> merchant1 = merchantService.loadMerchant();
		List<UMKManager> umkMgr = mobileUserService.loadUmKManager();
		
		Set<String> listUmRefNo = new HashSet<String>();
		for (UMKManager t : umkMgr) {
			if (t.getRefNo() != null) {
				listUmRefNo.add(t.getRefNo());
				// logger.info("ref no: " + t.getRefNo());
			}
		}
		
		try {

			if (regMobileUser.getUm_tid() == null && regMobileUser.getUm_motoTid() == null
					&& regMobileUser.getUm_ezypassTid() == null && regMobileUser.getUm_ezywayTid() == null
					&& regMobileUser.getUm_ezyrecTid() == null) {

				logger.info("Empty TID Fields..");
				model.addAttribute("responseEmptyTID", "Empty TID Fields..");
				throw new MobiException(Status.EMPTY_TID);

			}

			if (regMobileUser.getUm_deviceId() == null && regMobileUser.getUm_motodeviceId() == null
					&& regMobileUser.getUm_ezypassdeviceId() == null && regMobileUser.getUm_ezywaydeviceId() == null
					&& regMobileUser.getUm_ezyrecdeviceId() == null) {

				logger.info("Empty DeviceID Fields..");
				model.addAttribute("responseEmptyDeviceID", "Empty DeviceID Fields..");
				throw new MobiException(Status.EMPTY_DEVICEID);

			}
			
			
			logger.info("Ready to set mobile user data");
			regMobileUser=mobileUserService.setMobileUserData(regMobileUser);
			
			logger.info("mid mapped ummid"+regMobileUser.getMid());
			logger.info("mid mapped ummoto mid"+regMobileUser.getUm_motoMid());
			
			regMobileUser1 = mobileUserService.searchMobileUseradd(regMobileUser);
			
			
			logger.info("Obtained HashKey :"+regMobileUser1.getHashkey());
			logger.info("Obtained DTL :"+regMobileUser1.getDTL());
			logger.info("Obtained Domain url :"+regMobileUser1.getDomainUrl());
			logger.info("Obtained VCC :"+regMobileUser1.getVcc());
			logger.info("Obtained HashKey1 :"+regMobileUser1.getHashkey1());
			logger.info("Obtained DTL1 :"+regMobileUser1.getDTL1());
			
			
			logger.info("Obtained HashKey :"+regMobileUser1.getRecHashkey());
			logger.info("Obtained DTL :"+regMobileUser1.getRecDTL());

			
			BaseDataImpl  baseData = new BaseDataImpl();
			 RegMobileUser  a =baseData.vaildated(regMobileUser);
				if(a != null) {
					logger.info("Contains HTML tags");
					request.getSession(true).setAttribute("mobileAddErSession", "yes");
					return "redirect:/mobileUser/addMobileUser/1";
				}
				
				
				logger.info("validate tid: "+regMobileUser.isResUTid());
				logger.info("validate MotoTid: "+regMobileUser.isResUMotoTid());
				logger.info("validate EzypassTid: "+regMobileUser.isResUEzypassTid());
				logger.info("validate EzywayTid: "+regMobileUser.isResUEzywayTid());
				logger.info("validate EzyrecTid: "+regMobileUser.isResUEzyrecTid());
				
				logger.info("validate DeviceID: "+regMobileUser.isResUDeviceID());
				logger.info("validate MotoDeviceID: "+regMobileUser.isResUMotoDeviceID());
				logger.info("validate EzypassDeviceID: "+regMobileUser.isResUEzypassDeviceID());
				logger.info("validate EzywayDeviceID: "+regMobileUser.isResUEzywayDeviceID());
				logger.info("validate EzyrecDeviceID: "+regMobileUser.isResUEzyrecDeviceID());	


			if (!regMobileUser.isResUDeviceID() || !regMobileUser.isResUTid() ||
					!regMobileUser.isResUMotoDeviceID() || !regMobileUser.isResUMotoTid()
					|| !regMobileUser.isResUEzypassDeviceID() || !regMobileUser.isResUEzypassTid() ||
							!regMobileUser.isResUEzyrecDeviceID() || !regMobileUser.isResUEzyrecTid()
					|| !regMobileUser.isResUEzywayDeviceID() || !regMobileUser.isResUEzywayTid()) {

				
				model.addAttribute("resDataum_tid", regMobileUser.isResUTid() ? null : regMobileUser.getRespUTid());
				model.addAttribute("resDataum_mototid", regMobileUser.isResUMotoTid() ? null : regMobileUser.getRespUMotoTid());
				model.addAttribute("resDataum_ezypasstid", regMobileUser.isResUEzypassTid() ? null : regMobileUser.getRespUEzypassTid());
				model.addAttribute("resDataum_ezywaytid", regMobileUser.isResUEzywayTid() ? null : regMobileUser.getRespUEzywayTid());
				model.addAttribute("resDataum_ezyrectid", regMobileUser.isResUEzyrecTid() ? null : regMobileUser.getRespUEzyrecTid());
				
				model.addAttribute("resDataum_deviceid", regMobileUser.isResUDeviceID() ? null : regMobileUser.getRespUDeviceID());
				model.addAttribute("resDataum_motodeviceid", regMobileUser.isResUMotoDeviceID() ? null : regMobileUser.getRespUMotoDeviceID());
				model.addAttribute("resDataum_ezypassdeviceid", regMobileUser.isResUEzypassDeviceID() ? null : regMobileUser.getRespUEzypassDeviceID());
				model.addAttribute("resDataum_ezywaydeviceid", regMobileUser.isResUEzywayDeviceID() ? null : regMobileUser.getRespUEzywayDeviceID());
				model.addAttribute("resDataum_ezyrecdeviceid", regMobileUser.isResUEzyrecDeviceID() ? null : regMobileUser.getRespUEzyrecDeviceID());
				
				
				throw new MobiException(Status.EXIST_TID_OR_DEVICEID_DETAILS);
			}
		} catch (MobiException e) {

			logger.info("Exception: " + e.getMessage());
			pageBean = new PageBean("AddMobileUser", "mobileuser/addUmobileuser/addUMobileUser", Module.MOBILE_USER,
					"mobileuser/sideMenuMobileUser");

			model.addAttribute("pageBean", pageBean);

			model.addAttribute("mobileUser", regMobileUser);
			model.addAttribute("merchant1", merchant1);
			model.addAttribute("umrefNoList", listUmRefNo);
			return TEMPLATE_DEFAULT;
		}
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("regMobileUser", regMobileUser);
		model.addAttribute("mobileUser", regMobileUser1); // RegMobileUser

		request.getSession(true).setAttribute("addMobileUserSession", regMobileUser1);

		return "redirect:/mobileUser/um_reviewandConfirm";
	}
	
	
	
	@RequestMapping(value = { "/um_reviewandConfirm" }, method = RequestMethod.GET)
	public String addUMobileUserReviewandConfirm(final Model model, final HttpServletRequest request,
			final RegMobileUser regMobileUser) {
		
		
		logger.info("07 About to display the value for review & confrim");
		RegMobileUser mobileUser = (RegMobileUser) request.getSession(true).getAttribute("addMobileUserSession");
		
		 logger.info("domainurl: "+mobileUser.getDomainUrl());
		// logger.info("ezypassmid: "+mobileUser.getEzypassmid()+" moto mid:
		// "+mobileUser.getMotoMid());
		// logger.info("/reviewandConfirm-- moto mid: "+mobileUser.getMotoMid());
		if (mobileUser == null) {
			return "redirect:/mobileUser/addMobileUser";
		}

		mobileUserService.searchMobileUseradd(mobileUser);
		logger.info("VCC" + mobileUser.getVcc());
		logger.info("Hash Key:" + mobileUser.getHashkey());
		logger.info("DTL:" + mobileUser.getDTL());
		logger.info("Hash Key1:" + mobileUser.getHashkey1());
		logger.info("DTL1:" + mobileUser.getDTL1());
		
		logger.info("recHashkey:" + mobileUser.getRecHashkey());
		logger.info("recDTL:" + mobileUser.getRecDTL());
		
		PageBean pageBean = new PageBean("Mobile user add Details",
				"mobileuser/addUmobileuser/UMobileReviewandConfirm", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;
		// return "redirect:"+URL_BASE+"/list/1";
	}

	@RequestMapping(value = { "/um_mobileUserDetailsConfirm" }, method = RequestMethod.POST)
	public String umconfirmAddMobileUser(@ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
			final Model model, final java.security.Principal principal, final HttpServletRequest request) {
		logger.info("confirm to add MobileUser Details Confirms");
		PageBean pageBean = new PageBean("Succefully New MobileUser Added",
				"mobileuser/addUmobileuser/umobileSuccessful", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		logger.info(" MobileUser added by : " + principal.getName());
		model.addAttribute("pageBean", pageBean);
		RegMobileUser mobileUserSavedInHttpSession = (RegMobileUser) request.getSession(true)
				.getAttribute("addMobileUserSession");
		
		if (mobileUserSavedInHttpSession == null) {
			return "redirect:/mobileUser/addUMobileUser";
		}
		RegMobileUser mobileuser = mobileUserService.registerUMobileUser(mobileUserSavedInHttpSession);
		String mobileUserName = null;
		if (mobileUserSavedInHttpSession.getUm_deviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getUm_deviceId();
		}else if (mobileUserSavedInHttpSession.getUm_motodeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getUm_motodeviceId();
		}else if (mobileUserSavedInHttpSession.getUm_ezypassdeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getUm_ezypassdeviceId();
		}else if (mobileUserSavedInHttpSession.getUm_ezyrecdeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getUm_ezyrecdeviceId();
		}else if (mobileUserSavedInHttpSession.getUm_ezywaydeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getUm_ezywaydeviceId();
		}
		
		if(mobileUserName!=null) {
			 
			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(mobileUserName, principal.getName(),
					"addMobileUser");
			if (auditTrail != null) {
				logger.info("MobileUser: " + auditTrail.getUsername() + "  Added by Admin: " + auditTrail.getModifiedBy());
			}

		}
		model.addAttribute("mobileuser", mobileuser);
		model.addAttribute("contactname", mobileUserSavedInHttpSession.getContactName());
		request.getSession(true).removeAttribute("addMobileUserSession");

		return TEMPLATE_DEFAULT;
	}
	
	
	
	
	@RequestMapping(value = { "/findUmMotoTidUserDetails" }, method = RequestMethod.GET)
	public String findUmMotoTidUserDetails(final Model model,

			@RequestParam("umMotoTid") String umMotoTid, @RequestParam("umMotoMid") String umMotoMid,
			final java.security.Principal principal) {
		logger.info("/findUmMotoTidUserDetails: " + umMotoTid);
		logger.info("Login Person in dash Board:" + principal.getName());
		PageBean pageBean = new PageBean("AddMobileUser", "mobileuser/updatemobileuser/ByUmMotoTidupdateMobileUser",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");

		RegMobileUser regMobileUser = new RegMobileUser();
		//logger.info("tid param: " + ezypassTid + " mid param: " + ezypassMid);

	
		
		Merchant midDetails = merchantService.loadMerchantbyumMotoMid(umMotoMid);
		
		List<Merchant> merchant1 = merchantService.loadMerchant();
		
		regMobileUser.setMid(midDetails.getMid().getMid());
		regMobileUser.setUm_motoMid(midDetails.getMid().getUmMotoMid());
		regMobileUser.setMerchantusername(midDetails.getUsername());
		regMobileUser.setBusinessName(midDetails.getBusinessName());
		regMobileUser.setContactName(midDetails.getContactPersonName());
		regMobileUser.setUm_motoTid(umMotoTid);
		List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(midDetails.getId());
		//MobileUser mobileuserlist1 = mobileUserService.loadMobileUserEzypasstidDetails(ezypassTid);
		
		UMMidTxnLimit umMidTxnLimitDetails = mobileUserService.loadUmMidTxnLimitDetails(umMotoMid);
		
		MobileUser mobileuserlist1 = mobileUserService.loadMobileUserMototidDetails(umMotoTid);

		if (mobileuserlist1 != null) {
			

			if (regMobileUser.getUm_motoMid() != null) {
				if (mobileuserlist1.getMotoTid() != null) {
					logger.info("moto user tid: " + mobileuserlist1.getMotoTid());
					regMobileUser.setUm_motoTid(mobileuserlist1.getMotoTid());
					
					if (mobileuserlist1.getMotoTid() == umMotoTid || mobileuserlist1.getMotoTid().equals(umMotoTid)) {
						//logger.info("check username: " + mobileuserlist1.getUsername());
						regMobileUser.setMotousername(mobileuserlist1.getUsername());
						//logger.info("check moto mobusername: " + regMobileUser.getMotousername());
					}
				}
			}
			

		}

		List<String> UmmototidList = new ArrayList<String>();

		for (MobileUser t : mobileuser) {
			if (t.getMotoTid() != null) {
				UmmototidList.add(t.getMotoTid());
				regMobileUser.setUm_mototidList(UmmototidList);
				//logger.info("moto tid: " + t.getMotoTid());
			}

		}
		regMobileUser.setUpdateType("umobilemoto");
		regMobileUser.setHashkey(umMidTxnLimitDetails.getHashKey());
		regMobileUser.setDTL(umMidTxnLimitDetails.getDtl());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("tidList", mobileuserlist1);
		model.addAttribute("midDetails", midDetails);
		model.addAttribute("mobileUser", regMobileUser);
		model.addAttribute("umMidTxnLimitDetails", umMidTxnLimitDetails);
		
		// merchantService.listMerchant(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}
	
	
	@RequestMapping(value = { "/updateUmMidTxnDetails" },method = RequestMethod.GET)
	public String updateUmMidTxnDetails(@ModelAttribute("mobileUser") RegMobileUser regMobileUser,

			final HttpServletRequest request, final Model model) {
		List<Merchant> merchant1 = merchantService.loadMerchant();
		RegMobileUser regMobileUser1 =new RegMobileUser();
		PageBean pageBean = null;
		
		logger.info("regMobileUser mid: " + regMobileUser.getUm_motoMid());
		logger.info("regMobileUser update type: " + regMobileUser.getUpdateType());
		try {

			regMobileUser=mobileUserService.setDataForUpdateMobileUser(regMobileUser);
			regMobileUser1 = mobileUserService.searchMobileUser(regMobileUser);
			if(regMobileUser.getHashkey() != null) {
				regMobileUser1.setHashkey(regMobileUser.getHashkey());
			}
			
			
			if(regMobileUser.getDTL() != null) {
				regMobileUser1.setDTL(regMobileUser.getDTL());
			}
			if(regMobileUser.getHashkey1() != null) {
				regMobileUser1.setHashkey1(regMobileUser.getHashkey1());
			}
			
			
			if(regMobileUser.getDTL1() != null) {
				regMobileUser1.setDTL1(regMobileUser.getDTL1());
			}
			
			if (!regMobileUser.isResTid() || !regMobileUser.isResDeviceID() ||
					!regMobileUser.isResMotoTid() || !regMobileUser.isResMotoDeviceID() ||
					!regMobileUser.isResEzypassTid() || !regMobileUser.isResEzypassDeviceID() ||
					!regMobileUser.isResEzywayTid() || !regMobileUser.isResEzywayDeviceID() ||
					!regMobileUser.isResEzyrecTid()  ||  !regMobileUser.isResEzyrecDeviceID() ||
					 !regMobileUser.isResUTid() || !regMobileUser.isResUDeviceID() ) {
				if (regMobileUser.getUpdateType().equals("umobilemoto")) {
					pageBean = new PageBean("AddMobileUser",
							"mobileuser/updatemobileuser/ByUmMotoTidupdateMobileUserExist", Module.MOBILE_USER,
							"mobileuser/sideMenuMobileUser");
					
					List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(regMobileUser1.getId());
					List<String> UmmototidList = new ArrayList<String>();

					for (MobileUser t : mobileuser) {
						if (t.getMotoTid() != null) {
							logger.info("check mototid list.."+t.getMotoTid());
							UmmototidList.add(t.getMotoTid());
							regMobileUser.setUm_mototidList(UmmototidList);
							
						}

					}

					if (regMobileUser.isResDeviceID() || !regMobileUser.isResEzypassDeviceID() || !regMobileUser.isResEzyrecDeviceID() 
							|| !regMobileUser.isResTid() || !regMobileUser.isResEzypassTid() || !regMobileUser.isResEzyrecTid()) {
							pageBean = new PageBean("AddMobileUser",
									"mobileuser/updatemobileuser/ByUmMotoTidupdateMobileUserExist", Module.MOBILE_USER,
									"mobileuser/sideMenuMobileUser");
							model.addAttribute("pageBean", pageBean);
							throw new MobiException(Status.EXIST_DATA_BYMOTO);

						}

					
				} 
				
				if (regMobileUser.getUpdateType().equals("umobileezyway")) {
					pageBean = new PageBean("AddMobileUser",
							"mobileuser/updatemobileuser/ByUmEzywayTidupdateMobileUserExist", Module.MOBILE_USER,
							"mobileuser/sideMenuMobileUser");
					
					List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(regMobileUser1.getId());
					List<String> UmezywaytidList = new ArrayList<String>();

					for (MobileUser t : mobileuser) {
						if (t.getEzywayTid() != null) {
							logger.info("check ezywaytid list.."+t.getEzywayTid());
							UmezywaytidList.add(t.getEzywayTid());
							regMobileUser.setUm_ezywaytidList(UmezywaytidList);
							
						}

					}

					if (regMobileUser.isResDeviceID() || !regMobileUser.isResEzypassDeviceID() || !regMobileUser.isResEzyrecDeviceID() 
							|| !regMobileUser.isResTid() || !regMobileUser.isResEzypassTid() || !regMobileUser.isResEzywayTid() || !regMobileUser.isResEzyrecTid()) {
							pageBean = new PageBean("AddMobileUser",
									"mobileuser/updatemobileuser/ByUmEzywayTidupdateMobileUserExist", Module.MOBILE_USER,
									"mobileuser/sideMenuMobileUser");
							model.addAttribute("pageBean", pageBean);
							throw new MobiException(Status.EXIST_DATA_BYMOTO);

						}

					
				} 
				
				model.addAttribute("mobileUser", regMobileUser);
				model.addAttribute("pageBean", pageBean);
				throw new MobiException(Status.INVALID_TID_DETAILS);	
			}
			
		}
			catch (MobiException e) {
				// tid response data
				e.printStackTrace();
				model.addAttribute("responseDatamototid", regMobileUser.isResMotoTid()?null:"Invalid TID");
				model.addAttribute("responseDataezywaytid", regMobileUser.isResEzywayTid()?null:"Invalid TID");
				
				// deviceID response data
				
				model.addAttribute("responseDatamotodeviceid", regMobileUser.isResMotoDeviceID()?null:"Invalid DeviceID");
				model.addAttribute("responseDataezywaydeviceid", regMobileUser.isResEzywayDeviceID()?null:"Invalid DeviceID");
				
				model.addAttribute("mobileUser", regMobileUser);
				model.addAttribute("merchant1", merchant1);
				model.addAttribute("pageBean", pageBean);
				return TEMPLATE_DEFAULT;
				
			}
			
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("regMobileUser", regMobileUser);
		model.addAttribute("mobileUser", regMobileUser1); // RegMobileUser
		
	
		request.getSession(true).setAttribute("updateMotoUserSession", regMobileUser1);

		return "redirect:/mobileUser/reviewandConfirmupdateMotouser";

			
	}
	
	@RequestMapping(value = { "/reviewandConfirmupdateMotouser" }, method = RequestMethod.GET)
	public String reviewandConfirmupdateMotouser(final Model model, final HttpServletRequest request,
			final RegMobileUser regMobileUser) {
		logger.info("reviewandConfirmupdateMotouser");

		RegMobileUser mobileUser = (RegMobileUser) request.getSession(true).getAttribute("updateMotoUserSession");

		// logger.info("ezypassmid: "+mobileUser.getEzypassmid()+" moto mid:
		// "+mobileUser.getMotoMid());
		// logger.info("/reviewandConfirm-- moto mid: "+mobileUser.getMotoMid());
		if (mobileUser == null) {
			return "redirect:/mobileUser/updateMobileUser";
		}

		mobileUser = mobileUserService.searchMobileUser(mobileUser);
		
		logger.info("reviewandConfirmupdateMotouser DTL:"+mobileUser.getDTL());
		logger.info("reviewandConfirmupdateMotouser DTL1:"+mobileUser.getDTL1());
		PageBean pageBean = new PageBean("Mobile user add Details",
				"mobileuser/updatemobileuser/updateMotoUserReviewandConfirm", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;
		// return "redirect:"+URL_BASE+"/list/1";
	}

	@RequestMapping(value = { "/motoUserDetailsConfirmtoUpdate" }, method = RequestMethod.POST)
	public String motoUserDetailsConfirmtoUpdate(@ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
			final Model model, final java.security.Principal principal, final HttpServletRequest request) {
		logger.info("motoUserDetailsConfirmtoUpdate");
		PageBean pageBean = new PageBean("Succefully New MobileUser Added",
				"mobileuser/updatemobileuser/updateUmTxnMidDetailsdoneSuccess", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		model.addAttribute("pageBean", pageBean);

		RegMobileUser mobileUserSavedInHttpSession = (RegMobileUser) request.getSession(true)
				.getAttribute("updateMotoUserSession");

		if (mobileUserSavedInHttpSession == null) {
			return "redirect:/mobileUser/updateMobileUser";
		}

			
		RegMobileUser mobileuser = mobileUserService.updateUmTxnMidUser(mobileUserSavedInHttpSession);
		String Status = mobileuser.getStatus();
		
		if (Status != null && Status == "YES" ) {
			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin("Update UMTxnMidDetails", principal.getName(),
					"updateMobileUser");
			if (auditTrail != null) {
				logger.info("MobileUser: " + auditTrail.getUsername() + "  Updated by Admin: "
						+ auditTrail.getModifiedBy());
			}

		}
		//logger.info("activationCode: "+mobileuser.getActivationCode()+" contactname: "+mobileuser.getContactName());
		model.addAttribute("mobileuser", mobileuser);
		request.getSession(true).removeAttribute("addMobileUserSession");

		return TEMPLATE_DEFAULT;
	}
	
	
	@RequestMapping(value = { "/findUmEzywayTidUserDetails" }, method = RequestMethod.GET)
	public String findUmEzywayTidUserDetails(final Model model,

			@RequestParam("umEzywayTid") String umEzywayTid, @RequestParam("umEzywayMid") String umEzywayMid,
			final java.security.Principal principal) {
		logger.info("/findUmEzywayTidUserDetails: " + umEzywayTid);
		logger.info("Login Person in dash Board:" + principal.getName());
		PageBean pageBean = new PageBean("AddMobileUser", "mobileuser/updatemobileuser/ByUmEzywayTidupdateMobileUser",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");

		RegMobileUser regMobileUser = new RegMobileUser();
		//logger.info("tid param: " + ezypassTid + " mid param: " + ezypassMid);

	
		
		Merchant midDetails = merchantService.loadMerchantbyumEzywayMid(umEzywayMid);
		
		List<Merchant> merchant1 = merchantService.loadMerchant();
		
		regMobileUser.setMid(midDetails.getMid().getMid());
		regMobileUser.setUm_ezywayMid(midDetails.getMid().getUmEzywayMid());
		regMobileUser.setMerchantusername(midDetails.getUsername());
		regMobileUser.setBusinessName(midDetails.getBusinessName());
		regMobileUser.setContactName(midDetails.getContactPersonName());
		regMobileUser.setUm_ezywayTid(umEzywayTid);
		List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(midDetails.getId());
		//MobileUser mobileuserlist1 = mobileUserService.loadMobileUserEzypasstidDetails(ezypassTid);
		
		UMMidTxnLimit umMidTxnLimitDetails = mobileUserService.loadUmMidTxnLimitDetails(umEzywayMid);
		
		MobileUser mobileuserlist1 = mobileUserService.loadMobileUserEzywaytidDetails(umEzywayTid);

		if (mobileuserlist1 != null) {
			
			if (regMobileUser.getUm_ezywayMid() != null) {
				if (mobileuserlist1.getEzywayTid() != null) {
					logger.info("moto user tid: " + mobileuserlist1.getEzywayTid());
					regMobileUser.setUm_ezywayTid(mobileuserlist1.getEzywayTid());
					
					if (mobileuserlist1.getMotoTid() == umEzywayTid || mobileuserlist1.getEzywayTid().equals(umEzywayTid)) {
						//logger.info("check username: " + mobileuserlist1.getUsername());
						regMobileUser.setEzywayusername(mobileuserlist1.getUsername());
						//logger.info("check moto mobusername: " + regMobileUser.getMotousername());
					}
				}
			}
			

		}

		List<String> UmEzywaytidList = new ArrayList<String>();

		for (MobileUser t : mobileuser) {
			if (t.getEzywayTid() != null) {
				UmEzywaytidList.add(t.getEzywayTid());
				regMobileUser.setUm_ezywaytidList(UmEzywaytidList);
				//logger.info("moto tid: " + t.getMotoTid());
			}

		}
		regMobileUser.setUpdateType("umobileezyway");
		regMobileUser.setHashkey1(umMidTxnLimitDetails.getHashKey());
		regMobileUser.setDTL1(umMidTxnLimitDetails.getDtl());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("tidList", mobileuserlist1);
		model.addAttribute("midDetails", midDetails);
		model.addAttribute("mobileUser", regMobileUser);
		model.addAttribute("umMidTxnLimitDetails", umMidTxnLimitDetails);
		
		// merchantService.listMerchant(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}
	
	

}
