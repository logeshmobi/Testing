package com.mobiversa.payment.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.MobileUserStatusHistory;
import com.mobiversa.common.dto.MobileUserDTO;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;

@Controller
@RequestMapping(value = MerchantWebMobileController.URL_BASE)
public class MerchantWebMobileController extends BaseController {
	@Autowired
	private MobileUserService mobileUserService;
	@Autowired
	private MerchantService merchantService;

	public static final String URL_BASE = "/mobileUserweb";

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {

		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String listMerchantMobileUsers(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal, final HttpServletRequest request) {
		logger.info("Request to display mobileUser List");
		PageBean pageBean = new PageBean("MerchantWeb MobileUsers", "merchantweb/mobileuser/mobileUserList",
				Module.MOBILEUSER_WEB, "merchantweb/sideMenuMerchantWebMobile");
		model.addAttribute("pageBean", pageBean);
		String myName = principal.getName();
		
		logger.info("MobileUser Summary :" + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		request.getSession(true).setAttribute("CURRENT_USER_MERCHANT", currentMerchant);
		PaginationBean<MobileUserDTO> paginationBean = mobileUserService.listMobileUsers1(currPage, currentMerchant);

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/details/{id}" }, method = RequestMethod.GET)
	public String detailsMobileUser(final Model model, @PathVariable final long id,final java.security.Principal principal) {
		logger.info("Request to display mobileUser based on ID: " + id);
		PageBean pageBean = new PageBean("Mobileuser Detail", "merchantweb/mobileuser/mobileUserDetails",
				Module.MOBILEUSER_WEB, "merchantweb/sideMenuMerchantWebMobile");
		MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);
		Merchant merchant = merchantService.loadMerchantByPk(id);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("merchant", merchant);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/suspend/{id}" }, method = RequestMethod.GET)
	public String displaySuspendMobileUser(final Model model, @PathVariable final Long id,
			@ModelAttribute("mobileuserStatusHistory") final MobileUserStatusHistory mobileuserStatusHistory,
			@ModelAttribute("errorList") final ArrayList<String> errorList,final java.security.Principal principal) {
		PageBean pageBean = new PageBean("MobileUser Detail",
				"merchantweb/mobileuser/suspendMobileUser/suspendMobileuser", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");
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
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
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
	public String doSuspendMobileUser(final Model model, @RequestParam final Long id,
			@RequestParam final String reason, @RequestParam final String description,
			final RedirectAttributes redirectAttributes) {
		
		logger.info("Request to display mobileUser doSuspend  " + id);
		try {
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
			return "redirect:" + URL_BASE + "/list/1";
			// return "redirect:" + URL_BASE + "/suspendDone?mode=success&id=" +
			// id;
		} catch (Exception e) {
			logger.error("error suspending mobileuser", e);
			return "redirect:" + URL_BASE + "/list/1";
			// return "redirect:" + URL_BASE + "/suspendDone?mode=fail&id=" + id
			// + "&eventID=1";
		}
	}

	@RequestMapping(value = { "/suspendDone" }, method = RequestMethod.GET)
	public String displaySuspendMobileUserDone(final Model model,

	@RequestParam final String mode, @RequestParam final long id, @RequestParam(required = false) final Long eventID,final java.security.Principal principal) {
		logger.info("Request to display mobileUser suspended successfully  " + id);
		PageBean pageBean = new PageBean("MobileUser Suspend Done",
				"merchantweb/mobileuser/suspendMobileUser/suspendDone", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");
		boolean success = ("success".equals(mode));

		MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);
		MobileUserStatusHistory mobileuserStatusHistory = mobileUserService.loadMobileUserStatusByPk(mobileUser);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("success", success);
		model.addAttribute("eventID", eventID);
		model.addAttribute("mobileuserStatusHistory", mobileuserStatusHistory);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/unsuspend/{id}" }, method = RequestMethod.GET)
	public String displayUnSuspendMobileUser(final Model model, @PathVariable final Long id,
			@ModelAttribute("mobileuserStatusHistory") final MobileUserStatusHistory mobileuserStatusHistory,
			@ModelAttribute("errorList") final ArrayList<String> errorList,final java.security.Principal principal) {
		logger.info("Request to display mobileUser Unsuspend  " + id);
		PageBean pageBean = new PageBean("MobileUser Detail",
				"merchantweb/mobileuser/unsuspendMobileUser/unsuspendMobileuser", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");
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
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/unsuspend/dounSuspend" }, method = RequestMethod.POST)
	public String doUnSuspendMobileUser(final Model model, @RequestParam final Long id,
			@RequestParam final String reason, @RequestParam final String description,
			final RedirectAttributes redirectAttributes) {
		logger.info("Request to display mobileUser doUnSuspend " + id);
		try {
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

			return "redirect:" + URL_BASE + "/list/1";
		} catch (Exception e) {
			logger.error("error suspending mobileUser", e);
			return "redirect:" + URL_BASE + "/unsuspendDone?mode=fail&id=" + id + "&eventID=1";
			// return "redirect:" + URL_BASE + "/list/1";
		}
	}

	@RequestMapping(value = { "/unsuspendDone" }, method = RequestMethod.GET)
	public String displayUnSuspendMobileUserDone(final Model model, @RequestParam final String mode,
			@RequestParam final long id, @RequestParam(required = false) final Long eventID,final java.security.Principal principal) {
		logger.info("Request to display mobileUser unsuspended Successfully " + id);
		PageBean pageBean = new PageBean("MobileUser Un-Suspend Done",
				"merchantweb/mobileuser/unsuspendMobileUser/unsuspendAlldone", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");
		boolean success = ("success".equals(mode));
		MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);
		MobileUserStatusHistory mobileuserStatusHistory = mobileUserService.loadMobileUserStatusByPk(mobileUser);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("success", success);
		model.addAttribute("eventID", eventID);
		model.addAttribute("mobileuserStatusHistory", mobileuserStatusHistory);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public String displayEditMobileUser(final Model model, @PathVariable final Long id,final java.security.Principal principal) {
		logger.info("Request to display mobileuser edit");
		PageBean pageBean = new PageBean("MobileUser Detail",
				"merchantweb/mobileuser/editMobileUser/editMobileuserDetails", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");
		MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/editMobileuser" }, method = RequestMethod.POST)
	public String displayEditMobileUserSes(final Model model, final HttpServletRequest request,
			@RequestParam("id") final Long id, @RequestParam("firstName") final String firstName,
			@RequestParam("lastName") final String lastName, @RequestParam("email") final String email,
			@RequestParam("username") final String username, @RequestParam("contact") final String contact) {

		logger.info("about to  Edit Particular MobileUser   " + id);
		PageBean pageBean = new PageBean("MobileUser Detail",
				"merchantweb/mobileuser/editMobileUser/editMobileuserDetails", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");
		MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);
		mobileUser.setFirstName(firstName);
		mobileUser.setLastName(lastName);
		mobileUser.setEmail(email);
		mobileUser.setUsername(username);
		mobileUser.setContact(contact);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("bankUser", mobileUser);
		request.getSession(true).setAttribute("editMobileUserSession", mobileUser);

		return "redirect:/mobileUserweb/editMobileUserReviewandConfirm";
		// return "redirect:/mobileUserweb/editMobileuserDetails";

	}

	@RequestMapping(value = { "/editMobileUserReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditMobileUserConfirm(final Model model, final HttpServletRequest request,final java.security.Principal principal) {

		logger.info("about to  Edit Particular MobileUser Confirm  ");

		MobileUser mobileUser = (MobileUser) request.getSession(true).getAttribute("editMobileUserSession");
		if (mobileUser == null) {
			return "redirect:/mobileUserweb/editMobileuser";

		}
		PageBean pageBean = new PageBean("Mobile user add Details",
				"merchantweb/mobileuser/editMobileUser/reviewAndConfirm", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/confirmEditMobileUserDetails" }, method = RequestMethod.POST)
	public String confirmEditMobileUser(@ModelAttribute("mobileUser") final MobileUser mobileUser, final Model model,
			final HttpServletRequest request)
	{
		logger.info("about to add MobileUser Details Confirms");
		PageBean pageBean = new PageBean("Succefully MobileUser edited",
				"merchantweb/mobileuser/editMobileUser/editMobileuserSuccess", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");
		model.addAttribute("pageBean", pageBean);
		MobileUser mobileUserSavedInHttpSession = (MobileUser) request.getSession(true).getAttribute(
				"editMobileUserSession");
		if (mobileUserSavedInHttpSession == null) {
			return "redirect:/mobileUserweb/editMobileuser";
		}

		model.addAttribute(mobileUserSavedInHttpSession);
		request.getSession(true).removeAttribute("editMobileUserSession");

		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { /* "/addMobileUser/{id}" */"/addMobileUser" }, method = RequestMethod.GET)
	public String addMobileUser(final Model model, 
			@ModelAttribute("mobileUser") final MobileUser mobileUser/*,@Path Variable final long id */,final java.security.Principal principal)
	{
		//logger.info("about to add mobileuser");
		PageBean pageBean = new PageBean("AddMobileUser", "merchantweb/mobileuser/addMobileUser/addMobileuserDetails",
				Module.MOBILEUSER_WEB, "merchantweb/sideMenuMerchantWebMobile");
		/* Merchant merchant = merchantService.loadMerchantByPk(id); */
		/* model.addAttribute("merchant", merchant); */
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/addMobileUser"/* "/addMobileUserSession" */}, method = RequestMethod.POST)
	public String addMobileUser(@ModelAttribute("mobileUser") final MobileUser mobileUser,
			final HttpServletRequest request, final Model model 
			/* , @RequestParam("id" ) final Long id */) {
		//logger.info("about to add mobileuser session");
		PageBean pageBean = new PageBean("AddMobileUser", "merchantweb/mobileuser/addMobileUser/addMobileuserDetails",
				Module.MOBILEUSER_WEB, "merchantweb/sideMenuMerchantWebMobile");

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("mobileUser", mobileUser);
		//PCI
		request.getSession(true).setAttribute("addMobileUserSession", mobileUser);

		return "redirect:/mobileUserweb/reviewandConfirm";
	}

	@RequestMapping(value = { "/reviewandConfirm" }, method = RequestMethod.GET)
	public String addMobileUserReviewandConfirm(final Model model, final HttpServletRequest request,final java.security.Principal principal) {
		logger.info("about to add mobileuser review and confirm");
		MobileUser mobileUser = (MobileUser) request.getSession(true).getAttribute("addMobileUserSession");
		if (mobileUser == null) {
			// redirect user to the add page if there's no mobileUser in
			// session.
			return "redirect:/mobileUserweb/addMobileUserSession";
		}
		PageBean pageBean = new PageBean("Mobile user add Details",
				"merchantweb/mobileuser/addMobileUser/reviewAndConfirm", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/mobileUserDetailsConfirm" }, method = RequestMethod.POST)
	public String confirmAddMobileUser(@ModelAttribute("mobileUser") final MobileUser mobileUser, final Model model,
			final HttpServletRequest request /* ,  @RequestParam("id" ) final Long id */) 
	{
		logger.info("about to add MobileUser Details Confirms");
		PageBean pageBean = new PageBean("Succefully New MobileUser Added",
				"merchantweb/mobileuser/addMobileUser/alldoneSuccessful", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");
		model.addAttribute("pageBean", pageBean);
		MobileUser mobileUserSavedInHttpSession = (MobileUser) request.getSession(true).getAttribute(
				"addMobileUserSession");
		if (mobileUserSavedInHttpSession == null) {
			return "redirect:/mobileUserweb/addMobileUserSession";
		}
		model.addAttribute(mobileUserSavedInHttpSession);
		Merchant merchant = (Merchant) request.getSession(true).getAttribute("CURRENT_USER_MERCHANT");
		mobileUserSavedInHttpSession.setMerchant(merchant);
		request.getSession(true).removeAttribute("addMobileUserSession");

		return "redirect:" + URL_BASE + "/list/1";
	}
	
	
	@RequestMapping(value = { "/changepwd/{id}" }, method = RequestMethod.GET)
	public String changeEditMobileUser(final Model model, @PathVariable final Long id,final java.security.Principal principal) {
		//logger.info("Request to display mobileuser Change Password");
		PageBean pageBean = new PageBean("MobileUser Detail",
				"merchantweb/mobileuser/editMobileUser/changePwdMobileuserDetails", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");
		MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);
		
		logger.info("mobileUser Id:" +  mobileUser.getId() + ":" +  "UserName:"  + mobileUser.getUsername() + ":" + "MobileUser password Edit Page"+":"+  principal.getName());

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}
	
	@RequestMapping(value = { "/changePwdMobileuser" }, method = RequestMethod.POST)
	public String changePwdMobileUser(final Model model, final HttpServletRequest request,
			@RequestParam("id") final Long id, @RequestParam("firstName") final String firstName,
			@RequestParam("lastName") final String lastName, @RequestParam("email") final String email,
			@RequestParam("username") final String username, @RequestParam("contact") final String contact,
			@RequestParam("password") final String password,@RequestParam("repassword") final String repassword,final java.security.Principal principal) {

		logger.info("about to  Change Password Particular MobileUser   " + id);
		PageBean pageBean = new PageBean("MobileUser Detail",
				"merchantweb/mobileuser/editMobileUser/changePwdmobileUser", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");
		if( !password.equals(repassword) ){
			
			model.addAttribute("responseData", "Password and Confirm password mismatch"); //table response
			 pageBean = new PageBean("MobileUser Detail",
					"merchantweb/mobileuser/editMobileUser/changePwdMobileuserDetails", Module.MOBILEUSER_WEB,
					"merchantweb/sideMenuMerchantWebMobile");
			
		}else {
			model.addAttribute("responseData", null);
		}
		MobileUser mobileUser = new MobileUser();
		mobileUser.setId(id);
		mobileUser.setFirstName(firstName);
		mobileUser.setLastName(lastName);
		mobileUser.setEmail(email);
		mobileUser.setUsername(username);
		mobileUser.setContact(contact);
		mobileUser.setPassword(password);
		MobileUser mobileUser1 = mobileUserService.changePwdMobileUser(mobileUser);
		logger.info("mobileUser Id" + ":"+ mobileUser1.getId() + ":" +  "UserName"  +":"+ mobileUser1.getUsername() + ":" + "MobileUser password changed by :"+":"+  principal.getName());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser1);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}
	
}
