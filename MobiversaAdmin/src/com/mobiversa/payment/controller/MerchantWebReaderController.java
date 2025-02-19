package com.mobiversa.payment.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.Reader;
import com.mobiversa.common.bo.ReaderStatusHistory;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.BaseDataImpl;
import com.mobiversa.payment.dto.ReaderList;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.ReaderService;
import com.mobiversa.payment.util.MobiHtmlUtils;

@Controller
@RequestMapping(value = MerchantWebReaderController.URL_BASE)
public class MerchantWebReaderController extends BaseController {

	@Autowired
	private MobileUserService mobileUserService;
	@Autowired
	private ReaderService readerService;
	@Autowired
	private MerchantService merchantService;

	@Autowired
	private PasswordEncoder encoder;

	public static final String URL_BASE = "/readerweb";

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {

		return "redirect:" + URL_BASE + "/list/1";
	}

	/*
	 * @RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	 * public String listReaders(final Model model,
	 * 
	 * @PathVariable final int currPage, final java.security.Principal principal) {
	 * PageBean pageBean = new PageBean("ReaderWeb Readers",
	 * "merchantweb/readers/readersList", Module.READER_WEB,
	 * "merchantweb/sideMenuMerchantWebReader");
	 * 
	 * model.addAttribute("pageBean", pageBean); String myName =
	 * principal.getName(); logger.info("currently logged in as " + myName); //MID
	 * mid=.getMid(); Merchant currentMerchant =
	 * merchantService.loadMerchant(myName); Merchant
	 * merchant=merchantService.loadMerchantByMid(mid);
	 * logger.info("the merchant obj based on currently logged in user is: " +
	 * currentMerchant); PaginationBean<TerminalDetails> paginationBean = new
	 * PaginationBean<TerminalDetails>();
	 */
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String listReaders(final Model model, HttpServletRequest request,

			@PathVariable final int currPage, final java.security.Principal principal) {
		PageBean pageBean = new PageBean("ReaderWeb Readers", "merchantweb/readers/readersList", Module.READER_WEB,
				"merchantweb/sideMenuMerchantWebReader");
		model.addAttribute("pageBean", pageBean);
		// String myName = principal.getName();
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		logger.info("Reader Summary:" + myName);
		// PaginationBean<TerminalDetails> paginationBean = new
		// PaginationBean<TerminalDetails>();

		List<ReaderList> data = new ArrayList<ReaderList>();

		PaginationBean<ReaderList> paginationBean = new PaginationBean<ReaderList>();

		paginationBean.setCurrPage(currPage);
		// readerService.getTerminalDetails(paginationBean,
		// currentMerchant.getMid().getMid());
		logger.info("check merchant id:" + currentMerchant.getMid().getMid());

		/*
		 * //new method for mobile user name 31072017 TerminalDetails td =
		 * readerService.getTerminalDetails1(currentMerchant.getMid().getMid());
		 * 
		 * 
		 * logger.info("check merchant id:" +td.getTid());
		 * 
		 * MobileUser mobileUserName = readerService.getMobileUserName(td.getTid());
		 * 
		 * 
		 * 
		 * logger.info("check merchant id:" + mobileUserName.getUsername());
		 * 
		 * logger.info("check merchant id:" + mobileUserName.getFirstName());
		 * 
		 * 
		 */
		List<ReaderList> data1 = new ArrayList<ReaderList>();
		data1 = readerService.getReaderList(currentMerchant);

		// logger.info("check mid status:" + currentMerchant.getMid().getMid());
		// listAgentVolumeData=
		// transactionService.agentVolumeData(agent.getFirstName());

		logger.info("after Reader  Size : " + data1.size());
		paginationBean.setItemList(data1);

		// readerService.getReaderList(paginationBean, mobileUserName.getFirstName());
		// readerService.getTerminalDetails(paginationBean,
		// mobileUserName.getFirstName());

		// readerService.getTerminalDetails1(paginationBean,
		// currentMerchant.getMid().getMid());

		// readerService.getTerminalDetails(paginationBean, mobileUName);

		request.setAttribute("merchantname", currentMerchant.getUsername());
		// request.setAttribute("mobileusername", );
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String displaySearchReader(final Model model, @RequestParam final String serialNumber,
			@RequestParam(required = false, defaultValue = "1") final int currPage,final java.security.Principal principal) {
		logger.info("about to search reader based on search String:: " + serialNumber);

		PageBean pageBean = new PageBean("Search Reader", "merchantweb/readers/readerSearch", Module.READER_WEB,
				"merchantweb/sideMenuMerchantWebReader");

		PaginationBean<Reader> paginationBean = new PaginationBean<Reader>();

		paginationBean.setCurrPage(currPage);

		readerService.searchReader(serialNumber, paginationBean);

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/detail" }, method = RequestMethod.POST)
	public String listReader(final Model model,
			@RequestParam("deviceId") final String id /* @PathVariable final String id */, HttpServletRequest request,
			final java.security.Principal principal) {
		logger.info("Request to display Reader based on ID: " + id);
		PageBean pageBean = new PageBean("Reader Detail", "merchantweb/readers/readerDetails", Module.READER_WEB,
				"merchantweb/sideMenuMerchantWebReader");
		TerminalDetails reader = readerService.loadTerminalByDevice(id);
		MobileUser mobileUser = null;
		if (reader != null) {
			mobileUser = readerService.getMobileUserNames(reader.getTid());
			logger.info("mobile user name in reader summary:" + reader.getTid());
			reader.setDeviceName(mobileUser.getUsername());
			logger.info("setDeviceName" + mobileUser.getUsername());
			reader.setDeviceType(mobileUser.getId().toString());
			logger.info("setDeviceType" + mobileUser.getId().toString());
			request.setAttribute("mobileusername", mobileUser.getUsername());
			logger.info("end if");

		}

		logger.info("Tid" + ":" + reader.getTid() + ":" + "DeviceId" + ":" + reader.getDeviceId() + ":"
				+ "Reader Details logged by :" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		logger.info("after pagebean");
		model.addAttribute("reader", reader);
		logger.info("after reader");
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	/*
	 * @RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET) public
	 * String displayEditMobileUser(final Model model, @PathVariable final String
	 * id, final java.security.Principal principal) {
	 */
	@RequestMapping(value = { "/edit" }, method = RequestMethod.POST)
	public String displayEditMobileUser(final Model model, @RequestParam("deviceId") final String id,
			final HttpServletRequest request/* @PathVariable final String id */,
			final java.security.Principal principal) {
		logger.info("about to edit mobileuser");
		PageBean pageBean = new PageBean("Reader Detail", "merchantweb/readers/edit/readereditDetails",
				Module.READER_WEB, "merchantweb/sideMenuMerchantWebReader");
		TerminalDetails reader = readerService.loadTerminalByDevice(id);
		logger.info("Tid" + ":" + reader.getTid() + ":" + "DeviceId" + ":" + reader.getDeviceId() + ":"
				+ "Reader Details logged by :" + principal.getName());

		String err = (String) request.getSession(true).getAttribute("editErSession");

		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {

				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("editErSession");
			}
		}

		model.addAttribute("reader", reader);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/editReader" }, method = RequestMethod.POST)
	public String displayEditMobileUserSes(final Model model, final HttpServletRequest request,
			@RequestParam("deviceId") final String deviceId, final java.security.Principal principal,
			@RequestParam("contactName") final String contactName,
			@RequestParam("activeStatus") final String activeStatus,
	@RequestParam("olddeviceId") final String olddeviceId)

	{

		logger.info("about to  Edit Particular Terminal Details   " + deviceId + " " + contactName +"  old device id is  "+olddeviceId);
		PageBean pageBean = new PageBean("Reader Detail", "merchantweb/readers/edit/readereditDetails",
				Module.READER_WEB, "merchantweb/sideMenuMerchantWebReader");
		TerminalDetails reader = readerService.loadTerminalByDevice(olddeviceId);

		reader.setContactName(contactName);
		reader.setActiveStatus(activeStatus);
		
		reader.setDeviceId(deviceId);
		

		BaseDataImpl baseData = new BaseDataImpl();

		TerminalDetails a = baseData.vaildated(reader);

		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");

//				return "redirect:/readerweb/edit/"+id+"";

			return "redirect:/readerweb/edit";

		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("reader", reader);
		request.getSession(true).setAttribute("editReaderSession", reader);
		request.getSession(true).setAttribute("olddeviceId", olddeviceId);

		return "redirect:/readerweb/editReaderReviewandConfirm";

	}

	@RequestMapping(value = { "/editReaderReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditMobileUserConfirm(final Model model, final HttpServletRequest request,final java.security.Principal principal) {

		logger.info("about to  Edit Particular Reader Confirm  ");
		
		
		
		
		String olddeviceId = (String) request.getSession(true).getAttribute("olddeviceId");
		
		logger.info("in editReaderReviewandConfirm -get device Id "+olddeviceId);
		

		TerminalDetails reader = (TerminalDetails) request.getSession(true).getAttribute("editReaderSession");
		if (reader == null) {
			// redirect user to the edit page if there's no MobileUser in
			// session.

			/* return "redirect:/mobileUser/editMobileuser/1"; */

			return "redirect:" + URL_BASE + "/editMobileuser/1";
		}

		PageBean pageBean = new PageBean("Reader Detail", "merchantweb/readers/edit/readerReviewandConfirm",
				Module.READER_WEB, "merchantweb/sideMenuMerchantWebReader");
		// logger.info("about to edit Merchant Details ReviewAndConfirm111");
		model.addAttribute("pageBean", pageBean);
		// logger.info("about to edit Merchant Details ReviewAndConfirm123");
		model.addAttribute("reader", reader);
		model.addAttribute("olddeviceId",olddeviceId);
		model.addAttribute("loginname", principal.getName());
		// logger.info("about to edit Merchant Details ReviewAndConfirm1234");
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/editReaderReviewandConfirm" }, method = RequestMethod.POST)
	public String confirmEditMobileUser(@ModelAttribute("reader") final TerminalDetails terminalDetails,
			final Model model, final HttpServletRequest request, final java.security.Principal principal,
			@RequestParam("olddeviceId") final String olddeviceId) {
		 logger.info("In editReaderReviewandConfirm  post method  "+ olddeviceId);
		PageBean pageBean = new PageBean("Succefully Reader edited", "merchantweb/readers/edit/readerAlldoneSuccessful",
				Module.READER_WEB, "merchantweb/sideMenuMerchantWebReader");
		// logger.info("about to edit Merchant Details Confirms1");
		model.addAttribute("pageBean", pageBean);
		// logger.info("test data::::");
		TerminalDetails readerSavedInHttpSession = (TerminalDetails) request.getSession(true)
				.getAttribute("editReaderSession");

		logger.info("Tid" + ":" + readerSavedInHttpSession.getTid() + ":" + "DeviceId" + ":"
				+ readerSavedInHttpSession.getDeviceId() + ":" + "Reader Details Updated by :" + principal.getName());
		if (readerSavedInHttpSession == null) {
			// return "redirect:/mobileUser/editMobileuser";
			return "redirect:/readerweb/editMobileUserReviewandConfirm";
		}

		BaseDataImpl baseData = new BaseDataImpl();

		TerminalDetails a = baseData.vaildated(readerSavedInHttpSession);

		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");

//				return "redirect:/readerweb/edit/"+id+"";

			return "redirect:/readerweb/edit";

		}

		model.addAttribute(readerSavedInHttpSession);
		model.addAttribute("reader", readerSavedInHttpSession);

		logger.info("Tid" + ":" + readerSavedInHttpSession.getTid() + ":" + "DeviceId" + ":"
				+ readerSavedInHttpSession.getDeviceId() + ":" + "Reader Details Updated by :" + principal.getName());
		// mobileUserService.addMobileUser(mobileUserSavedInHttpSession);

		logger.info(readerSavedInHttpSession);

		// Long l=Long.parseLong(readerSavedInHttpSession.getTid());
		TerminalDetails reader = readerService.updateReader(readerSavedInHttpSession, olddeviceId);
		MobileUser mobileuser = readerService.getMobileUserNames(readerSavedInHttpSession.getTid());

		logger.info("UpdateAuditTrail username: " + mobileuser.getUsername());

		if (reader.getMerchantId() != null) {
			AuditTrail auditTrail = merchantService.updateAuditTrailByMerchant(mobileuser.getUsername(),
					principal.getName(), "editReader");
			// AuditTrail
			// auditTrail=readerService.UpdateAuditTrail(readerSavedInHttpSession.getTid());
			if (auditTrail.getUsername() != null) {
				logger.info("Reader : " + reader.getDeviceId() + " details successfullly edited by Merchant: "
						+ auditTrail.getModifiedBy());
			}
		}
		request.getSession(true).removeAttribute("readerSavedInHttpSession");
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
		// return "redirect:/viewMobileUsers/details/1";
	}

	@RequestMapping(value = { "/suspend/{id}" }, method = RequestMethod.GET)
	public String displaySuspendReader(final Model model, @PathVariable final String id,
			@ModelAttribute("readerStatusHistory") final ReaderStatusHistory readerStatusHistory,
			@ModelAttribute("errorList") final ArrayList<String> errorList,final java.security.Principal principal) {
		logger.info("Request to suspend reader based on ID: " + id);
		PageBean pageBean = new PageBean("Reader Detail", "merchantweb/readers/suspend/readerSuspend",
				Module.READER_WEB, "merchantweb/sideMenuMerchantWebReader");
		pageBean.addJS("merchant/suspend/merchantSuspend.js");
		TerminalDetails reader = readerService.loadTerminalByDevice(id);
		if (!CommonStatus.ACTIVE.equals(reader.getKeyStatus())) {
			// reader status isn't active, then how do we suspend reader?
			return URL_BASE + "/detail/" + id;
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("reader", reader);
		model.addAttribute("now", new Date());
		model.addAttribute("readerStatusHistory", readerStatusHistory);
		model.addAttribute("errorList", errorList);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@ModelAttribute("reasonList")
	public List<String> populateReasonList() {
		List<String> reasonList = new ArrayList<String>();
		reasonList.add("Fraud Case Reader");
		reasonList.add("Payment Delay");
		reasonList.add("Fake Registration");
		reasonList.add("Credit Card Fraud");
		reasonList.add("Other");
		return reasonList;
	}

	@RequestMapping(value = { "/suspend/doSuspend" }, method = RequestMethod.POST)
	public String doSuspendReader(final Model model, @RequestParam final Long id, @RequestParam final String reason,
			@RequestParam final String description, final RedirectAttributes redirectAttributes) {
		logger.info("Request to dosuspend reader based on ID: " + id);
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
				ReaderStatusHistory readerStatusHistory = new ReaderStatusHistory();
				readerStatusHistory.setReason(reason);
				readerStatusHistory.setDescription(description);

				redirectAttributes.addFlashAttribute("readerStatusHistory", readerStatusHistory);
				redirectAttributes.addFlashAttribute("errorList", errorList);

				return "redirect:" + URL_BASE + "/suspend/" + id;
			}

			readerService.doSuspendReader(id, reason, description);
			// return "redirect:" + URL_BASE + "/suspendDone?mode=success&id=" +
			// id;
			return "redirect:" + URL_BASE + "/list/1";
		} catch (Exception e) {
			logger.error("error suspending reader", e);
			// return "redirect:" + URL_BASE + "/suspendDone?mode=fail&id=" + id
			// + "&eventID=1";
			return "redirect:" + URL_BASE + "/list/1";
		}
	}

	@RequestMapping(value = { "/suspendDone" }, method = RequestMethod.GET)
	public String displaySuspendReaderDone(final Model model,

			@RequestParam final String mode, @RequestParam final long id,
			@RequestParam(required = false) final Long eventID,final java.security.Principal principal) {
		logger.info("Request to suspend Done merchant based on ID: " + id);
		PageBean pageBean = new PageBean("Reader Suspend Done", "merchantweb/readers/suspend/readerSuspendDone",
				Module.READER_WEB, "merchantweb/sideMenuMerchantWebReader");
		boolean success = ("success".equals(mode));

		Reader reader = readerService.loadReaderByPk(id);
		ReaderStatusHistory readerStatusHistory = readerService.loadReaderStatusByPk(reader);
		model.addAttribute("readerStatusHistory", readerStatusHistory);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("reader", reader);
		model.addAttribute("success", success);
		model.addAttribute("eventID", eventID);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/unsuspend/{id}" }, method = RequestMethod.GET)
	public String displayUnSuspendReader(final Model model, @PathVariable final Long id,
			@ModelAttribute("readerStatusHistory") final ReaderStatusHistory readerStatusHistory,
			@ModelAttribute("errorList") final ArrayList<String> errorList,final java.security.Principal principal) {
		logger.info("Request to unsuspend merchant based on ID: " + id);
		PageBean pageBean = new PageBean("Reader Detail", "merchantweb/readers/unsuspend/readerUnSuspend",
				Module.READER_WEB, "merchantweb/sideMenuMerchantWebReader");
		pageBean.addJS("merchant/suspend/merchantSuspend.js");
		Reader reader = readerService.loadReaderByPk(id);
		if (!CommonStatus.SUSPENDED.equals(reader.getStatus())) {
			// merchant status isn't active, then how do we suspend merchant?
			return URL_BASE + "/detail/" + id;
		}
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("reader", reader);
		model.addAttribute("now", new Date());
		model.addAttribute("readerStatusHistory", readerStatusHistory);
		model.addAttribute("errorList", errorList);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/unsuspend/dounSuspend" }, method = RequestMethod.POST)
	public String doUnSuspendMerchant(final Model model, @RequestParam final Long id, @RequestParam final String reason,
			@RequestParam final String description, final RedirectAttributes redirectAttributes,final java.security.Principal principal) {
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
				ReaderStatusHistory readerStatusHistory = new ReaderStatusHistory();
				readerStatusHistory.setReason(reason);
				readerStatusHistory.setDescription(description);

				redirectAttributes.addFlashAttribute("readerStatusHistory", readerStatusHistory);
				redirectAttributes.addFlashAttribute("errorList", errorList);

				return "redirect:" + URL_BASE + "/unsuspend/" + id;
			}

			readerService.doUnSuspendReader(id, reason, description);
			// return "redirect:" + URL_BASE + "/unsuspendDone?mode=success&id="
			// + id;
			return "redirect:" + URL_BASE + "/list/1";

		} catch (Exception e) {
			logger.error("error suspending reader", e);
			// return "redirect:" + URL_BASE + "/unsuspendDone?mode=fail&id=" +
			// id + "&eventID=1";
			return "redirect:" + URL_BASE + "/list/1";
		}
	}

	@RequestMapping(value = { "/unsuspendDone" }, method = RequestMethod.GET)
	public String displayUnSuspendReaderDone(final Model model, @RequestParam final String mode,
			@RequestParam final long id, @RequestParam(required = false) final Long eventID,final java.security.Principal principal) {
		PageBean pageBean = new PageBean("Reader Un-Suspend Done",
				"merchantweb/readers/unsuspend/readerUnSuspendAllDone", Module.READER_WEB,
				"merchantweb/sideMenuMerchantWebReader");
		boolean success = ("success".equals(mode));

		Reader reader = readerService.loadReaderByPk(id);
		ReaderStatusHistory readerStatusHistory = readerService.loadReaderStatusByPk(reader);
		model.addAttribute("readerStatusHistory", readerStatusHistory);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("reader", reader);
		model.addAttribute("success", success);
		model.addAttribute("eventID", eventID);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// new method for mobile user change password 01082017

	@RequestMapping(value = { "/changepwd/{tid}" }, method = RequestMethod.GET)
	public String changeEditMobileUser(final Model model, @PathVariable final String tid,
			final java.security.Principal principal) {

		PageBean pageBean = new PageBean(" mobile user change password",
				"merchantweb/mobileuser/editMobileUser/changePwdMobileuserDetails", Module.MOBILEUSER_WEB,
				"merchant/sideMenuMerchant");

		// MobileUser

		// logger.info("Request to display mobileuser Change Password");

		// MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);

		/*
		 * String myName = principal.getName(); Merchant currentMerchant =
		 * merchantService.loadMerchant(myName); TerminalDetails td =
		 * readerService.getTerminalDetails1(currentMerchant.getMid().getMid());
		 * 
		 */
		logger.info("check merchant id:" + tid);

		MobileUser mobileUserName = readerService.getMobileUserName(tid);

		// PageBean pageBean = null;
		/*
		 * if(mobileUserName.getUsername()!= null) {
		 * 
		 * pageBean = new PageBean(" mobile user change password",
		 * "merchantweb/mobileuser/editMobileUser/changePwdMobileuserDetails",
		 * Module.MOBILEUSER_WEB, "merchant/sideMenuMerchant");
		 * model.addAttribute("responseData", "UserName already Exist");//table response
		 * model.addAttribute("pageBean",pageBean);
		 * model.addAttribute("mobileUser",mobileUserName);
		 * 
		 * return TEMPLATE_MERCHANT;
		 * 
		 * } else { PageBean pageBean1 = new PageBean("MobileUser Detail",
		 * "merchantweb/mobileuser/editMobileUser/changePwdMobileuserDetails",
		 * Module.MOBILEUSER_WEB, "merchantweb/sideMenuMerchantWebMobile");
		 * model.addAttribute("responseData", null);
		 * 
		 * }
		 */

		// logger.info("mobileUser Id:" + mobileUserName.getId() + ":" + "UserName:" +
		// mobileUserName.getUsername() + ":" + "MobileUser password Edit Page"+":"+
		// principal.getName());

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUserName);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/changePwdMobileuser" }, method = RequestMethod.POST)
	public String changePwdMobileUser(final Model model, final HttpServletRequest request,
			@RequestParam("id") final Long id, @RequestParam("firstName") final String firstName,
			@RequestParam("lastName") final String lastName, @RequestParam("email") final String email,
			@RequestParam("username") final String username, @RequestParam("contact") final String contact,
			@RequestParam("password") final String password, @RequestParam("repassword") final String repassword,
			final java.security.Principal principal) {

		logger.info("about to  Change Password Particular MobileUser   " + id);
		PageBean pageBean = new PageBean("MobileUser Detail",
				"merchantweb/mobileuser/editMobileUser/changePwdmobileUser", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");
		if (!password.equals(repassword)) {

			model.addAttribute("responseData", "Password and Confirm password mismatch"); // table response
			pageBean = new PageBean("MobileUser Detail",
					"merchantweb/mobileuser/editMobileUser/changePwdMobileuserDetails", Module.MOBILEUSER_WEB,
					"merchantweb/sideMenuMerchantWebMobile");

		} else {
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

		BaseDataImpl baseData = new BaseDataImpl();

		MobileUser a = baseData.vaildated(mobileUser);

		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");

//				return "redirect:/readerweb/edit/"+id+"";

			return "redirect:/readerweb/edit";

		}

		logger.info("password changes done:" + mobileUser.getPassword());
		MobileUser mobileUser1 = mobileUserService.changePwdMobileUser(mobileUser);
		logger.info("mobileUser Id" + ":" + mobileUser1.getId() + ":" + "UserName" + ":" + mobileUser1.getUsername()
				+ ":" + "MobileUser password changed by :" + ":" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser1);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// new Change 28092017

	@RequestMapping(value = { "/logindetails" }, method = RequestMethod.POST)
	public String editMobileUserLogin(final Model model, @RequestParam("tid") String tid, /*
																							 * @PathVariable final
																							 * String tid,
																							 */
			final HttpServletRequest request, final java.security.Principal principal) {

		PageBean pageBean = new PageBean(" mobile user change password",
				"merchantweb/mobileuser/editMobileUser/editMobileUserLoginDetails", Module.MOBILEUSER_WEB,
				"merchant/sideMenuMerchant");

		logger.info("check merchant tid:" + tid);

		MobileUser mobileUser = readerService.getMobileUserNames(tid);

		if (mobileUser != null) {
			logger.info("fb: " + mobileUser.getFbLogin());
			if (mobileUser.getFbLogin() != null) {
				if (mobileUser.getFbLogin().equalsIgnoreCase("No")) {
					mobileUser.setFacebookId("");
				}
			}
			if (mobileUser.getgLogin() != null) {
				if (mobileUser.getgLogin().equalsIgnoreCase("No")) {
					mobileUser.setGoogleId("");
				}
			}
		}

		/*
		 * BaseDataImpl baseData = new BaseDataImpl();
		 * 
		 * MobileUser a =baseData.vaildated(mobileUser);
		 * 
		 * 
		 * if(a != null) { logger.info("Contains HTML tags");
		 * request.getSession(true).setAttribute("editErSession", "yes");
		 * 
		 * // return "redirect:/readerweb/edit/"+id+"";
		 * 
		 * return "redirect:/readerweb/edit";
		 * 
		 * }
		 */
		// PageBean pageBean = null;
		/*
		 * if(mobileUserName.getUsername()!= null) {
		 * 
		 * pageBean = new PageBean(" mobile user change password",
		 * "merchantweb/mobileuser/editMobileUser/changePwdMobileuserDetails",
		 * Module.MOBILEUSER_WEB, "merchant/sideMenuMerchant");
		 * model.addAttribute("responseData", "UserName already Exist");//table response
		 * model.addAttribute("pageBean",pageBean);
		 * model.addAttribute("mobileUser",mobileUserName);
		 * 
		 * return TEMPLATE_MERCHANT;
		 * 
		 * } else { PageBean pageBean1 = new PageBean("MobileUser Detail",
		 * "merchantweb/mobileuser/editMobileUser/changePwdMobileuserDetails",
		 * Module.MOBILEUSER_WEB, "merchantweb/sideMenuMerchantWebMobile");
		 * model.addAttribute("responseData", null);
		 * 
		 * }
		 */

		// logger.info("mobileUser Id:" + mobileUserName.getId() + ":" + "UserName:" +
		// mobileUserName.getUsername() + ":" + "MobileUser password Edit Page"+":"+
		// principal.getName());

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobileUser", mobileUser);

		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/editMobileUserLogin" }, method = RequestMethod.POST)
	public String editMobileUserLoginDetails(final Model model, final HttpServletRequest request,
			@RequestParam("id") final Long id, @RequestParam("firstName") final String firstName,
			@RequestParam("lastName") final String lastName, @RequestParam("email") final String email,
			@RequestParam("username") final String username, @RequestParam("contact") final String contact,
			@RequestParam("dateOfBirth") final String dateOfBirth, @RequestParam("facebookId") final String facebookId,
			@RequestParam("googleId") final String googleId, @RequestParam("password") final String password,
			@RequestParam("repassword") final String repassword, final java.security.Principal principal) {

		logger.info("about to  Change Password Particular MobileUser   " + id);

		BaseDataImpl baseData = new BaseDataImpl();

		boolean status = false;

		status = MobiHtmlUtils.isHtml(firstName);
		if (status == false)
			status = MobiHtmlUtils.isHtml(lastName);
		if (status == false)
			status = MobiHtmlUtils.isHtml(email);
		if (status == false)
			status = MobiHtmlUtils.isHtml(username);
		if (status == false)
			status = MobiHtmlUtils.isHtml(contact);
		if (status == false)
			status = MobiHtmlUtils.isHtml(dateOfBirth);
		if (status == false)
			status = MobiHtmlUtils.isHtml(facebookId);
		if (status == false)
			status = MobiHtmlUtils.isHtml(googleId);
		if (status == false)
			status = MobiHtmlUtils.isHtml(password);
		if (status == false)
			status = MobiHtmlUtils.isHtml(repassword);

		logger.info("aerrr :::   " + status);

		// MobileUser a =baseData.vaildated(mobileUser);

		if (status != false) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");

//				return "redirect:/readerweb/edit/"+id+"";

			return "redirect:/readerweb/edit";

		}

		ArrayList<String> responseData = new ArrayList<String>();
		PageBean pageBean = new PageBean("MobileUser Detail",
				"merchantweb/mobileuser/editMobileUser/editMobileUserLogin", Module.MOBILEUSER_WEB,
				"merchantweb/sideMenuMerchantWebMobile");
		logger.info(dateOfBirth);

		if (!password.equals(repassword)) {

			model.addAttribute("responseData", "Password and Confirm password mismatch"); // table response
			pageBean = new PageBean("MobileUser Detail",
					"merchantweb/mobileuser/editMobileUser/editMobileUserLoginDetails", Module.MOBILEUSER_WEB,
					"merchantweb/sideMenuMerchantWebMobile");
			responseData.add("Password and Confirm password mismatch");

		} else {
			model.addAttribute("responseData", null);
		}
		logger.info("about to  Change Password Particular MobileUser1   " + id + " " + username + " " + facebookId + " "
				+ googleId + " " + contact);
		MobileUser mobileUser2 = mobileUserService.loadMobileUserByIdName(id, username);

		if (mobileUser2 != null) {
			logger.info("about to  Change Password Particular MobileUser11   " + id + " " + username + " " + facebookId
					+ " " + googleId + " " + contact);
			model.addAttribute("responseData", "Mobile User Already Exist with Username"); // table response
			pageBean = new PageBean("MobileUser Detail",
					"merchantweb/mobileuser/editMobileUser/editMobileUserLoginDetails", Module.MOBILEUSER_WEB,
					"merchantweb/sideMenuMerchantWebMobile");
			responseData.add("Mobile User Already Exist with Username");
		} else {
			model.addAttribute("responseData", null);
		}
		logger.info("about to  Change Password Particular MobileUser2   " + id + " " + username + " " + facebookId + " "
				+ googleId + " " + contact);

		MobileUser mobileUser3 = null;
		if (facebookId != null && !facebookId.isEmpty()) {
			logger.info("check facebookID" + id + " " + username + " " + facebookId);

			mobileUser3 = mobileUserService.loadMobileUserByIdName(id, facebookId);
			if (mobileUser3 != null) {
				// logger.info("check facebookID" + id +" "+username+" "+facebookId);
				logger.info("check fb ac id..." + mobileUser3.getFacebookId());
				model.addAttribute("responseData", "Mobile User Already Exist with Facebook Account"); // table response
				pageBean = new PageBean("MobileUser Detail",
						"merchantweb/mobileuser/editMobileUser/editMobileUserLoginDetails", Module.MOBILEUSER_WEB,
						"merchantweb/sideMenuMerchantWebMobile");
				responseData.add("Mobile User Already Exist with Facebook Account");
			} else {
				model.addAttribute("responseData1", null);
			}
		}

		logger.info("about to  Change Password Particular MobileUser3   " + id + " " + username + " " + facebookId + " "
				+ googleId + " " + contact);
		MobileUser mobileUser4 = null;
		if (googleId != null && !googleId.isEmpty()) {

			mobileUser4 = mobileUserService.loadMobileUserByIdName(id, googleId);
			if (mobileUser4 != null) {
				logger.info("about to  Change Password Particular MobileUser13   " + id + " " + username + " "
						+ facebookId + " " + googleId + " " + contact);
				model.addAttribute("responseData2", "Mobile User Already Exist with Google Account"); // table response
				pageBean = new PageBean("MobileUser Detail",
						"merchantweb/mobileuser/editMobileUser/editMobileUserLoginDetails", Module.MOBILEUSER_WEB,
						"merchantweb/sideMenuMerchantWebMobile");
				responseData.add("Mobile User Already Exist with Google Account");
			} else {
				model.addAttribute("responseData2", null);
			}
		}
		logger.info("about to  Change Password Particular MobileUser4   " + id + " " + username + " " + facebookId + " "
				+ googleId + " " + contact);

		// MobileUser mobileUser5 =null;
		/*
		 * if(email != null && !email.isEmpty()) {
		 * 
		 * logger.info("email address: "+email); List<MobileUser> mobileUser5 =
		 * mobileUserService.loadMobileUserByIdEmail(id,email);
		 * logger.info(mobileUser5.size()); if(mobileUser5.size()>0) {
		 * logger.info("mobileUser5.size()   " + mobileUser5.size());
		 * model.addAttribute("responseData1",
		 * "Mobile User Already Exist with Email Account"); //table response pageBean =
		 * new PageBean("MobileUser Detail",
		 * "merchantweb/mobileuser/editMobileUser/editMobileUserLoginDetails",
		 * Module.MOBILEUSER_WEB, "merchantweb/sideMenuMerchantWebMobile");
		 * responseData.add("Mobile User Already Exist with Email Account"); }else {
		 * model.addAttribute("responseData1", null); } } //MobileUser mobileUser6
		 * =null; if(contact != null && !contact.isEmpty()) {
		 * 
		 * logger.info("contact no: "+contact); List<MobileUser> mobileUser6 =
		 * mobileUserService.loadMobileUserByIdContact(id,contact);
		 * if(mobileUser6.size()>0) {
		 * 
		 * logger.info("checked contact " +contact); model.addAttribute("responseData1",
		 * "Mobile User Already Exist with contact no"); //table response pageBean = new
		 * PageBean("MobileUser Detail",
		 * "merchantweb/mobileuser/editMobileUser/editMobileUserLoginDetails",
		 * Module.MOBILEUSER_WEB, "merchantweb/sideMenuMerchantWebMobile");
		 * 
		 * }else { model.addAttribute("responseData1", null); } }
		 */

		// MobileUser mobileUser = new MobileUser();

		MobileUser mobileUser = mobileUserService.loadMobileUserByPk(id);
		mobileUser.setId(id);
		mobileUser.setFirstName(firstName);
		mobileUser.setLastName(lastName);
		mobileUser.setEmail(email);
		mobileUser.setUsername(username);
		mobileUser.setContact(contact);
		mobileUser.setDateOfBirth(dateOfBirth);
		if (password != null && !password.isEmpty()) {
			mobileUser.setPassword(encoder.encode(password));
		}
		if (googleId != null && !googleId.isEmpty()) {
			mobileUser.setgLogin("Yes");
			mobileUser.setGoogleId(googleId);
		} else {
			mobileUser.setgLogin("No");
			mobileUser.setGoogleId(null);
		}
		if (facebookId != null && !facebookId.isEmpty()) {
			mobileUser.setFbLogin("Yes");
			mobileUser.setFacebookId(facebookId);
		} else {
			mobileUser.setFbLogin("No");
			mobileUser.setFacebookId(null);
		}

		mobileUser.setFailedLoginAttempt(0);
		mobileUser.setSuspendDate(null);
		mobileUser.setStatus(CommonStatus.ACTIVE);

		/*
		 * BaseDataImpl baseData = new BaseDataImpl();
		 * 
		 * MobileUser a =baseData.vaildated(mobileUser);
		 * 
		 * 
		 * if(a != null) { logger.info("Contains HTML tags");
		 * request.getSession(true).setAttribute("editErSession", "yes");
		 * 
		 * // return "redirect:/readerweb/edit/"+id+"";
		 * 
		 * return "redirect:/readerweb/edit";
		 * 
		 * }
		 */

		/*
		 * if(mobileUser2 == null && mobileUser3 == null && mobileUser4 == null) {
		 */
		logger.info("responseData:" + responseData.size());
		if (responseData.size() <= 0) {
			logger.info("password changes done:" + mobileUser.getPassword());
			logger.info(
					mobileUser.getFacebookId() + " " + mobileUser.getGoogleId() + " " + mobileUser.getUsername() + " ");
			logger.info(
					mobileUser.getFacebookId() + " " + mobileUser.getGoogleId() + " " + mobileUser.getUsername() + " ");
			MobileUser mobileUser1 = mobileUserService.editMobileUserDetails(mobileUser);
			logger.info("mobileUser Id" + ":" + mobileUser1.getId() + ":" + "UserName" + ":" + mobileUser1.getUsername()
					+ ":" + "MobileUser password changed by :" + ":" + principal.getName());

			AuditTrail auditTrail = merchantService.updateAuditTrailByMerchant(mobileUser1.getUsername(),
					principal.getName(), "editMobileUserLogin");

			if (auditTrail.getUsername() != null) {
				logger.info("Mobile user " + auditTrail.getUsername() + " login details edited by Merchant "
						+ auditTrail.getModifiedBy());
			}

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("mobileUser", mobileUser1);
		} else {
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("mobileUser", mobileUser);
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

}
