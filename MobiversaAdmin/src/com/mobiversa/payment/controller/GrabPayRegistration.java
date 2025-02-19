package com.mobiversa.payment.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.KManager;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.BaseDataImpl;
import com.mobiversa.payment.dto.RegMobileUser;
import com.mobiversa.payment.exception.MobiException;
import com.mobiversa.payment.exception.Status;
import com.mobiversa.payment.service.AdminService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.DeviceRandomNumber;

@Controller
@RequestMapping(value = GrabPayRegistration.URL_BASE)
public class GrabPayRegistration extends BaseController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private MobileUserService mobileUserService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;

	private static final Logger logger = Logger.getLogger(GrabPayRegistration.class);
	public static final String URL_BASE = "/grabPay";

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		 logger.info("default url");
		return "redirect:" + URL_BASE + "/addGrabPay";
	}
	

	@RequestMapping(value = { "/addGrabPay" }, method = RequestMethod.GET)
	public String finduserDetails(final Model model, final java.security.Principal principal,final HttpServletRequest request) {
		//logger.info("/finduserDetails: to ADD " + id);
		PageBean pageBean = new PageBean("AddMobileUser", "merchant/GrabPay/addGrabPay", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		RegMobileUser regMobileUser = new RegMobileUser();

		//Merchant midDetails = mobileUserService.loadIserMidDetails(id);

		List<Merchant> merchant1 = merchantService.loadMerchant();
		List<KManager> kMgr = mobileUserService.loadKManager();
		Set<String> listRefNo = new HashSet<String>();
		for (KManager t : kMgr) {

			/*listRefNo.add("12345"); 
			listRefNo.add("12342");*/

			if (t.getRefNo() != null) {

				listRefNo.add(t.getRefNo());
				// logger.info("ref no: " + t.getRefNo());
			}
		}
		
		String  err = (String) request.getSession(true).getAttribute("addMdrErSession");
		
		if(err!=null) {
		if(err.equalsIgnoreCase("Yes")) {
			
			logger.info("err::::::" + err);
			model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
			request.getSession(true).removeAttribute("addMdrErSession");
		}
		}

		
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("merchant1", merchant1);
		//model.addAttribute("midDetails", midDetails);
		model.addAttribute("mobileUser", regMobileUser);

		model.addAttribute("refNoList", listRefNo);
		// merchantService.listMerchant(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}
	@SuppressWarnings("nls")
	@RequestMapping(value = { "/findgrabPayUserDetails" }, method = RequestMethod.GET)
	public String findgrabPayUserDetails(final Model model,

			@RequestParam("id") Long id, final java.security.Principal principal) {
		
		logger.info("/findgrabPayUserDetails: " + id+" by"+ principal.getName());
		PageBean pageBean = new PageBean("AddMobileUser", "merchant/GrabPay/addGrabPay",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		Merchant midDetails = null;
		RegMobileUser regMobileUser = new RegMobileUser();
		
			
	
		
			List<Merchant> merchant1 = merchantService.loadMerchant();
			List<KManager> kMgr = mobileUserService.loadKManager();
			Set<String> listRefNo = new HashSet<String>();
			for (KManager t : kMgr) {
				listRefNo.add("12345");
				listRefNo.add("12342");
				if (t.getRefNo() != null) {
					listRefNo.add(t.getRefNo());
					//logger.info("ref no: " + t.getRefNo());
				}
			}
			try {
				if (id != null) {
					midDetails = mobileUserService.loadIserMidDetails(id);
				}
				if (midDetails == null ) {
		
					throw new MobiException(Status.MOBILE_USER_NOT_EXISTS);
				}
			} catch (MobiException e) {

		logger.info("Exception: " + e.getMessage());
		pageBean = new PageBean("AddMobileUser", "merchant/GrabPay/addGrabPay",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		
		return this.TEMPLATE_DEFAULT;
	}
		regMobileUser.setMid(midDetails.getMid().getMid());
		regMobileUser.setMotoMid(midDetails.getMid().getMotoMid());
		regMobileUser.setEzypassMid(midDetails.getMid().getEzypassMid());
		regMobileUser.setEzywayMid(midDetails.getMid().getEzywayMid());
		regMobileUser.setEzyrecMid(midDetails.getMid().getEzyrecMid());
		regMobileUser.setMerchantusername(midDetails.getUsername());
		regMobileUser.setBusinessName(midDetails.getBusinessName());
		regMobileUser.setContactName(midDetails.getContactPersonName());
		regMobileUser.setMerchantID(String.valueOf(id));
		regMobileUser.setgPayMid(midDetails.getMid().getGpayMid());

		logger.info("mid: "+regMobileUser.getMid());
		logger.info("motomid: "+regMobileUser.getMotoMid());
		logger.info("EzypassMid: "+regMobileUser.getEzypassMid());
		logger.info("EzyrecMid: "+regMobileUser.getEzyrecMid());
		logger.info("EzywayMid: "+regMobileUser.getEzywayMid());
		
		List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(id);

	
		
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
		

	@RequestMapping(value = { "/regGrabPay" }, method = RequestMethod.POST)
	public String addMobileUserSession(@ModelAttribute("mobileUser") final RegMobileUser regMobileUser,

			final HttpServletRequest request, final Model model, final java.security.Principal principal) {
		logger.info("/regGrabPay add GrabPay by" +principal.getName()+" merchantID: "+regMobileUser.getMerchantID());
		PageBean pageBean = null;
		String responseDatagPaytid = null;
		TerminalDetails gDevice = null;
		TerminalDetails gPayTid = null;
		String responseDatagpaydeviceid = null;
		

		RegMobileUser regMobileUser1 = new RegMobileUser();
		List<Merchant> merchant1 = merchantService.loadMerchant();
		
		
		try {
		

			if (regMobileUser.getgPayTid() == null ) {

				// logger.info("Empty TID Fields..");

				model.addAttribute("responseEmptyTID", "Empty TID Fields..");

				throw new MobiException(Status.EMPTY_TID);

			}
			
			BaseDataImpl  baseData = new BaseDataImpl();
			
			 RegMobileUser  a =baseData.vaildated(regMobileUser);
				
				
				if(a != null) {
					logger.info("Contains HTML tags");
					request.getSession(true).setAttribute("addMdrErSession", "yes");
					return "redirect:/grabPay/addGrabPay";
				}

			
			Merchant merchant=merchantService.loadMerchantbyid(Long.valueOf(regMobileUser.getMerchantID()));
			
			MobileUser mob=mobileUserService.loadMobileUserbyMerchantFK(regMobileUser.getMerchantID());
			
				regMobileUser.setFirstName(merchant.getContactPersonName());
				regMobileUser.setSalutation(merchant.getSalutation());
				
				regMobileUser.setMerchantName(merchant.getBusinessName());
				regMobileUser.setMerchantusername(merchant.getUsername());
				regMobileUser.setEmailId(merchant.getEmail());
				regMobileUser.setContactNo(merchant.getBusinessContactNumber());
				regMobileUser.setMerchantID(merchant.getId().toString());
				regMobileUser.setgPayrefNo(mob.getUsername());
				regMobileUser1 = mobileUserService.searchMobileUsergPayadd(regMobileUser);
	
				regMobileUser.setId(mob.getId());
				logger.info("expirty date: "+regMobileUser1.getExpiryDate());
			
			
		/*	if (regMobileUser.getgPayTid() != null) {

				gPayTid = mobileUserService.loadTerminalDetailsByTid(regMobileUser.getgPayTid());
				if (gPayTid != null) {
					responseDatagPaytid= "Tid already exist";
				}
				if (regMobileUser.getgPaydeviceId() != null && !(regMobileUser.getgPaydeviceId().isEmpty())) {
					logger.info("ezywire device id to check:" + regMobileUser.getgPaydeviceId() + ": "
							+ regMobileUser.getgPaydeviceId());
					gDevice = mobileUserService.loadDeviceId(regMobileUser.getgPaydeviceId());
					if (gDevice != null) {
						responseDatagpaydeviceid = "DeviceID already exist";
					}
				}
			}*/

			

			if (gPayTid != null || gDevice != null ) {

				model.addAttribute("responseDatagpaydeviceid", responseDatagpaydeviceid);
				model.addAttribute("responseDatagPaytid", responseDatagPaytid);
				throw new MobiException(Status.EXIST_TID_OR_DEVICEID_DETAILS);
			}
		} catch (MobiException e) {

			logger.info("Exception: " + e.getMessage());
			pageBean = new PageBean("AddMobileUser", "merchant/GrabPay/addGrabPay",
					Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");

			model.addAttribute("pageBean", pageBean);

			model.addAttribute("mobileUser", regMobileUser);
			model.addAttribute("merchant1", merchant1);
			
			return TEMPLATE_DEFAULT;
		}
		
		 
		

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("regMobileUser", regMobileUser);
		model.addAttribute("mobileUser", regMobileUser1); // RegMobileUser

		// PCI
		request.getSession(true).setAttribute("addgrabPaySession", regMobileUser1);

		return "redirect:/grabPay/reviewandConfirm";
	}

	

	@RequestMapping(value = { "/reviewandConfirm" }, method = RequestMethod.GET)
	public String addMobileUserReviewandConfirm(final Model model, final HttpServletRequest request,
			final RegMobileUser regMobileUser) {
		logger.info("about to add mobileuser review and confirm");

		RegMobileUser mobileUser = (RegMobileUser) request.getSession(true).getAttribute("addgrabPaySession");

		// logger.info("ezypassmid: "+mobileUser.getEzypassmid()+" moto mid:
		// "+mobileUser.getMotoMid());
		// logger.info("/reviewandConfirm-- moto mid: "+mobileUser.getMotoMid());
		if (mobileUser == null) {
			return "redirect:/grabPay/addGrabPay";
		}

		 mobileUserService.searchMobileUsergPayadd(mobileUser);

		PageBean pageBean = new PageBean("Mobile user add Details",
				"merchant/GrabPay/addGrabPayReview", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;
		// return "redirect:"+URL_BASE+"/list/1";
	}
//success
	@RequestMapping(value = { "/grabPayUserDetailsConfirm" }, method = RequestMethod.POST)
	public String confirmAddMobileUser(@ModelAttribute("mobileUser") final RegMobileUser regMobileUser,
			final Model model, final java.security.Principal principal, final HttpServletRequest request) {
		logger.info("confirm to add MobileUser Details Confirms");
		PageBean pageBean = new PageBean("Succefully New MobileUser Added",
				"merchant/GrabPay/GrabPaySuccess", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		logger.info(" MobileUser added by : " + principal.getName());
		model.addAttribute("pageBean", pageBean);
		RegMobileUser mobileUserSavedInHttpSession = (RegMobileUser) request.getSession(true)
				.getAttribute("addgrabPaySession");
		
		if (mobileUserSavedInHttpSession == null) {
			return "redirect:/grabPay/addGrabPay";
		}
		
		BaseDataImpl  baseData = new BaseDataImpl();
		
		 RegMobileUser  a =baseData.vaildated(regMobileUser);
			
			
			if(a != null) {
				logger.info("Contains HTML tags");
				request.getSession(true).setAttribute("addMdrErSession", "yes");
				return "redirect:/grabPay/addGrabPay";
			}
		
		RegMobileUser mobileuser = mobileUserService.addGrabPayUser(regMobileUser);
		String mobileUserName = null;
		if (mobileUserSavedInHttpSession.getgPaydeviceId() != null) {
			mobileUserName= mobileUserSavedInHttpSession.getgPaydeviceId();
		}
		
		if(mobileUserName!=null) {
			 
			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(mobileUserName, principal.getName(),
					"addMobileUser");
			if (auditTrail != null) {
				logger.info(
						"MobileUser: " + auditTrail.getUsername() + "  Added by Admin: " + auditTrail.getModifiedBy());
			}

		}
		model.addAttribute("mobileuser", mobileuser);
		model.addAttribute("contactname", mobileUserSavedInHttpSession.getContactName());
		request.getSession(true).removeAttribute("addMobileUserSession");

		return TEMPLATE_DEFAULT;
	}
	
	@RequestMapping(value = { "/updateGrabPay" }, method = RequestMethod.GET)
	public String updateGrabPayDetails(final Model model, final java.security.Principal principal) {
		//logger.info("/finduserDetails: to ADD " + id);
		PageBean pageBean = new PageBean("AddMobileUser", "merchant/GrabPay/updateGrabpay", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		RegMobileUser regMobileUser = new RegMobileUser();

		//Merchant midDetails = mobileUserService.loadIserMidDetails(id);

		List<Merchant> merchant1 = merchantService.loadMerchant();
		

		
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("merchant1", merchant1);
		//model.addAttribute("midDetails", midDetails);
		model.addAttribute("mobileUser", regMobileUser);

		
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}
	@RequestMapping(value = { "/finduser" }, method = RequestMethod.GET)
	public String updategrabPayUserDetails(final Model model,

			@RequestParam("id") Long id,final HttpServletRequest request, final java.security.Principal principal) {
		
		logger.info("/findgrabPayUserDetails: " + id+" by"+ principal.getName());
		PageBean pageBean = new PageBean("AddMobileUser", "merchant/GrabPay/updateGrabpay",
				Module.MOBILE_USER, "mobileuser/sideMenuMobileUser");
		Merchant midDetails = null;
		RegMobileUser regMobileUser = new RegMobileUser();
		if (id != null) {
			midDetails = mobileUserService.loadIserMidDetails(id);
		}

	
		List<Merchant> merchant1 = merchantService.loadMerchant();
		List<MobileUser> mobileusersList = mobileUserService.loadMobileUsersbyMerchantFK(id.toString());
		Set<String> usernameList = new HashSet<String>();
		for (MobileUser mobileuser : mobileusersList) {
			
			if (mobileuser.getUsername()!= null) {
				usernameList.add(mobileuser.getUsername());
				//logger.info("ref no: " + t.getRefNo());
			}
		}

		regMobileUser.setMid(midDetails.getMid().getMid());
		regMobileUser.setMotoMid(midDetails.getMid().getMotoMid());
		regMobileUser.setEzypassMid(midDetails.getMid().getEzypassMid());
		regMobileUser.setEzywayMid(midDetails.getMid().getEzywayMid());
		regMobileUser.setEzyrecMid(midDetails.getMid().getEzyrecMid());
		regMobileUser.setMerchantusername(midDetails.getUsername());
		regMobileUser.setBusinessName(midDetails.getBusinessName());
		regMobileUser.setContactName(midDetails.getContactPersonName());
		regMobileUser.setMerchantID(String.valueOf(id));
		regMobileUser.setgPayMid(midDetails.getMid().getGpayMid());
		
		
		String  err = (String) request.getSession(true).getAttribute("editMobiMdrErSession");
		
		if(err!=null) {
		if(err.equalsIgnoreCase("Yes")) {
			
			logger.info("err::::::" + err);
			model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
			request.getSession(true).removeAttribute("editMobiMdrErSession");
		}
		}
		

		logger.info("mid: "+regMobileUser.getMid());
		logger.info("motomid: "+regMobileUser.getMotoMid());
		logger.info("EzypassMid: "+regMobileUser.getEzypassMid());
		logger.info("EzyrecMid: "+regMobileUser.getEzyrecMid());
		logger.info("EzywayMid: "+regMobileUser.getEzywayMid());
		
		//List<MobileUser> mobileuser = mobileUserService.loadMobileUserDetails(id);

	
		
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("merchant1", merchant1);
		model.addAttribute("midDetails", midDetails);
		model.addAttribute("mobileUser", regMobileUser);

		model.addAttribute("mob", mobileusersList);
		// merchantService.listMerchant(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}

	
	/*@RequestMapping(value = { "/updateGrabPayUser" }, method = RequestMethod.POST)
	public String updateuserDetails(final Model model, final java.security.Principal principal,
			@ModelAttribute("mobileUser") final RegMobileUser mobileUser) {
	
		PageBean pageBean = new PageBean("AddMobileUser", "merchant/GrabPay/updateGrabpay", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		RegMobileUser regMobileUser1 = new RegMobileUser();

		//Merchant midDetails = mobileUserService.loadIserMidDetails(id);

		logger.info("grabmid: "+mobileUser.getgPayMid());
		logger.info("grabpayTid: "+mobileUser.getgPayTid());
		logger.info("mobile id: "+mobileUser.getId());
		logger.info("contactname: "+mobileUser.getContactName());
		List<Merchant> merchant1 = merchantService.loadMerchant();
		//List<MobileUser> kMgr = mobileUserService.loadMobileUsersbyMerchantFK(id);
		
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("merchant1", merchant1);
		//model.addAttribute("midDetails", midDetails);
		model.addAttribute("mobileUser", mobileUser);

		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}*/

	@RequestMapping(value = { "/updateGrabPayUser" }, method = RequestMethod.POST)
	public String updateMobileUserSession(@ModelAttribute("mobileUser") final RegMobileUser mobileUser,

			final HttpServletRequest request, final Model model,final java.security.Principal principal) {
		
		logger.info("/updateGrabPay add GrabPay by" +principal.getName()+" merchantID: "+mobileUser.getMerchantID());
		
		PageBean pageBean = new PageBean("AddMobileUser", "merchant/GrabPay/updateGrabPayReview", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");

		RegMobileUser regMobileUser1 = new RegMobileUser();

		//Merchant midDetails = mobileUserService.loadIserMidDetails(id);

		logger.info("grabmid: "+mobileUser.getgPayMid());
		logger.info("grabpayTid: "+mobileUser.getgPayTid());
		logger.info("mobile id: "+mobileUser.getMobileId());
		logger.info("merchant id: "+mobileUser.getMerchantID());
		logger.info("contactname: "+mobileUser.getContactName());
		logger.info("contactname: "+mobileUser.getUsername());
		List<Merchant> merchant1 = merchantService.loadMerchant();
		//long id = Long.valueOf(mobileUser.getMobileId());
		
		
		BaseDataImpl  baseData = new BaseDataImpl();
		
		RegMobileUser  a =baseData.vaildated(mobileUser);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editMobiMdrErSession", "yes");

			return "redirect:/grabPay/finduser/"+mobileUser.getId()+"";
		}
		
		
		MobileUser mobileuser1=mobileUserService.loadMobileUserByPk(Long.valueOf(mobileUser.getMobileId()));
		
		logger.info("mobileuser1 user name"+mobileuser1.getUsername());
		logger.info("mbuser mobid"+Long.valueOf(mobileUser.getMobileId()));
		mobileUser.setUsername(mobileuser1.getUsername());
		
		DeviceRandomNumber d = new DeviceRandomNumber();
		mobileUser.setgPaydeviceId("GPAY0000"+d.generateRandomString());
		
	
		
		LocalDate date = LocalDate.now(); 
		String[] arr=date.toString().split("-");
		
		int expiryYear=Integer.parseInt(arr[0])+100;
		String expiryDate=arr[2]+"/"+arr[1]+"/"+String.valueOf(Integer.parseInt(arr[0])+100);
		String strDate = null;
		try {
			strDate = new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		mobileUser.setExpiryDate(strDate);
		logger.info("expiry date: "+mobileUser.getExpiryDate());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		model.addAttribute("merchant1", merchant1);
		//model.addAttribute("midDetails", midDetails);
		model.addAttribute("mobileUser", mobileUser);

		model.addAttribute("paginationBean", paginationBean);
		mobileUserService.updateGrabPayTid(mobileUser.getgPayTid());
		
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/updateGrabPayUserConfirm" }, method = RequestMethod.POST)
	public String addMobileUserReviewandConfirmUpdate(final Model model, final HttpServletRequest request,
			final RegMobileUser regMobileUser, final java.security.Principal principal) {
		logger.info("/updateGrabPayUserConfirm");

		/*RegMobileUser mobileUser = (RegMobileUser) request.getSession(true).getAttribute("updateGrabPaySession");

		// logger.info("ezypassmid: "+mobileUser.getEzypassmid()+" moto mid:
		// "+mobileUser.getMotoMid());
		// logger.info("/reviewandConfirm-- moto mid: "+mobileUser.getMotoMid());
		if (mobileUser == null) {
			return "redirect:/grabPay/updateGrabPay";
		}*/
		MobileUser mobuser=mobileUserService.loadMobileUserByPk(Long.valueOf(regMobileUser.getMobileId()));
		regMobileUser.setTid(mobuser.getTid());
		regMobileUser.setMotoTid(mobuser.getMotoTid());
		regMobileUser.setEzypassTid(mobuser.getEzypassTid());
		regMobileUser.setEzyrecTid(mobuser.getEzyrecTid());
		
		logger.info("grabmid: "+regMobileUser.getgPayMid());
		logger.info("grabpayTid: "+regMobileUser.getgPayTid());
		logger.info("mobile id: "+regMobileUser.getMobileId());
		logger.info("merchant id: "+regMobileUser.getMerchantID());
		logger.info("contactname: "+regMobileUser.getContactName());
		logger.info("tid: "+regMobileUser.getTid());
		logger.info("mototid: "+regMobileUser.getMotoTid());
		logger.info("ezyrectid: "+regMobileUser.getEzyrecTid());
		logger.info("ezypasstid: "+regMobileUser.getEzypassTid());
		RegMobileUser mobileuser = mobileUserService.updateGrabPayUser(regMobileUser);
		String mobileUserName = null;
		
		if(mobileUserName!=null) {
			 
			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(mobileUserName, principal.getName(),
					"addMobileUser");
			if (auditTrail != null) {
				logger.info(
						"MobileUser: " + auditTrail.getUsername() + "  Added by Admin: " + auditTrail.getModifiedBy());
			}

		}
		PageBean pageBean = new PageBean("Mobile user add Details",
				"merchant/GrabPay/updateGrabPaySuccess", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		
		model.addAttribute("mobileuser", mobileuser);
		model.addAttribute("userName", mobileuser.getUsername());
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;
		// return "redirect:"+URL_BASE+"/list/1";
	}

}
