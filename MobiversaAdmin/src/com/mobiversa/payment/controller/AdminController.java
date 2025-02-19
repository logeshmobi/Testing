package com.mobiversa.payment.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.BankUser;
import com.mobiversa.common.bo.BankUserRole;
import com.mobiversa.common.bo.BankUserStatusHistory;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.controller.bean.SubmerchantApi;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dto.BaseDataImpl;
import com.mobiversa.payment.dto.DashBoardData;
import com.mobiversa.payment.dto.RegAddMerchant;
import com.mobiversa.payment.dto.ThreeMonthTxnData;
import com.mobiversa.payment.service.AdminService;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.DashBoardService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.util.AddMerchantOptionList;
import com.mobiversa.payment.util.DashboardAmount;
import com.mobiversa.payment.validator.AddBankUserValidator;

@Controller
@RequestMapping(value = AdminController.URL_BASE)
public class AdminController extends BaseController {

	public static final String URL_BASE = "/admin";

	@Autowired
	private AdminService adminService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private DashBoardService dashBoardService;

	@Autowired
	private MobileUserService mobileUser;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private AddBankUserValidator validator;

	@Autowired
	private MerchantDao merchantDao;

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {

		return "redirect:" + URL_BASE + "/user/list/1";
	}

	@RequestMapping(value = { "/user/list/{currPage}" }, method = RequestMethod.GET)
	public String listMerchant(final Model model, @PathVariable final int currPage) {
		logger.info("about to  List of BankUsers");
		PageBean pageBean = new PageBean("Bank User", "admin/user/list", Module.ADMIN, "admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<BankUser> paginationBean = new PaginationBean<BankUser>();
		paginationBean.setCurrPage(currPage);

		adminService.listBankUser(paginationBean);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String displaySearchBankUser(final Model model,

			@RequestParam final String username,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to search BankUser based on search String::   " + username);

		PageBean pageBean = new PageBean("Search BankUser", "admin/user/bankUserSearch", Module.ADMIN,
				"admin/sideMenuBankUser");
		PaginationBean<BankUser> paginationBean = new PaginationBean<BankUser>();
		paginationBean.setCurrPage(currPage);

		adminService.searchBankUser(username, paginationBean);

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/bankUserDetails/{id}" }, method = RequestMethod.GET)
	public String displayUserDetail(final Model model, @PathVariable final long id) {
		logger.info("about to  Details  of Particular BankUsers   " + id);
		BankUser bankUser = adminService.loadBankUser(id);
		BankUserStatusHistory bankUserStatusHistory = adminService.loadBankUserStatusHistoryID(bankUser);
		PageBean pageBean = new PageBean("Bank User Details " + bankUser.getUsername(), "admin/user/bankUserDetail",
				Module.ADMIN, "admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("bankUser", bankUser);
		model.addAttribute("bankUserStatusHistory ", bankUserStatusHistory);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/addBankUser" }, method = RequestMethod.GET)
	public String displayAddUserForm(@ModelAttribute("bankUser") final BankUser bankUser,
			final HttpServletRequest request, final ModelMap model, final BindingResult errors) {
		logger.info("about to add BankUser First Page");
		PageBean pageBean = new PageBean("Bank user add Details", "admin/user/addbankuser/addbankuser", Module.ADMIN,
				"admin/sideMenuBankUser");

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("bankUser", bankUser);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/addBankUser" }, method = RequestMethod.POST)
	public String processAddUserForm(@ModelAttribute("bankUser") final BankUser bankUser,
			final HttpServletRequest request, final ModelMap model, final BindingResult errors) {
		logger.info("about to add BankUser SamePage");
		if (validator.supports(bankUser.getClass())) {
			validator.validate(bankUser, errors);
			if (errors.hasErrors()) {
				PageBean pageBean1 = new PageBean("Bank user add Details", "admin/user/addbankuser/addbankuser",
						Module.ADMIN, "admin/sideMenuBankUser");
				model.addAttribute("pageBean", pageBean1);
				return TEMPLATE_DEFAULT;
			}
		}

		// no error found in FORM validator
		PageBean pageBean = new PageBean("Bank user add Details", "admin/user/addbankuser/addbankuser", Module.ADMIN,
				"admin/sideMenuBankUser");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("bankUser", bankUser);
		// PCI
		request.getSession(true).setAttribute("addBankUserSession", bankUser);

		return "redirect:/admin/bankUserDetailsReviewAndConfirm";
	}

	@RequestMapping(value = { "/bankUserDetailsReviewAndConfirm" }, method = RequestMethod.GET)
	public String displayAddUserConfirmation(final ModelMap model, final HttpServletRequest request) {
		logger.info("about to add BankUser Details ReviewAndConfirm");
		BankUser bankUser = (BankUser) request.getSession(true).getAttribute("addBankUserSession");
		if (bankUser == null) {
			// redirect user to the add page if there's no bankUser in session.
			return "redirect:/admin/addBankUser";
		}
		PageBean pageBean = new PageBean("Bank user add Details", "admin/user/addbankuser/reviewandconfirm",
				Module.ADMIN, "admin/sideMenuBankUser");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("bankUser", bankUser);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/bankUserDetailsReviewAndConfirm" }, method = RequestMethod.POST)
	public String confirmAddBankUser(@ModelAttribute("bankUser") final BankUser bankUser, final Model model,
			final HttpServletRequest request) {
		logger.info("about to add BankUser Details Confirms");
		PageBean pageBean = new PageBean("Succefully New BankUser Added", "admin/user/addbankuser/addbankusersucces",
				Module.ADMIN, "admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		BankUser bankUserSavedInHttpSession = (BankUser) request.getSession(true).getAttribute("addBankUserSession");
		if (bankUserSavedInHttpSession == null) {
			return "redirect:/admin/addBankUser";
		}
		model.addAttribute(bankUserSavedInHttpSession);
		adminService.addBankUser(bankUserSavedInHttpSession);
		request.getSession(true).removeAttribute("addBankUserSession");

		// return TEMPLATE_DEFAULT;
		return "redirect:" + URL_BASE + "/user/list/1";
	}

	@RequestMapping(value = { "/suspend/{id}" }, method = RequestMethod.GET)
	public String displaySuspendBankUser(final Model model, @PathVariable final Long id,
			@ModelAttribute("bankUserStatusHistory") final BankUserStatusHistory bankUserStatusHistory,
			@ModelAttribute("errorList") final ArrayList<String> errorList) {
		logger.info("about to  Suspend Particular BankUser   " + id);
		PageBean pageBean = new PageBean("Suspend Bank User Reason Details",
				"admin/user/suspendbankuser/suspendbankuser", Module.ADMIN, "admin/sideMenuBankUser");
		pageBean.addJS("admin/suspend/adminSuspend.js");
		BankUser bankUser = adminService.loadBankUser(id);
		if (!CommonStatus.ACTIVE.equals(bankUser.getStatus())) {
			// merchant status isn't active, then how do we suspend merchant?
			return URL_BASE + "/user/detail/" + id;
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("bankUser", bankUser);
		model.addAttribute("now", new Date());
		model.addAttribute("bankUserStatusHistory", bankUserStatusHistory);
		model.addAttribute("errorList", errorList);
		return TEMPLATE_DEFAULT;
	}

	/**
	 * Suspend bankuser
	 * <p>
	 * wireframe 03a.02a
	 * </p>
	 * 
	 * @param model
	 * @param id    PK ID of bankuser
	 * @return
	 */
	@RequestMapping(value = { "/suspend/doSuspend" }, method = RequestMethod.POST)
	public String doSuspendBankUser(final Model model, @RequestParam final Long id, @RequestParam final String reason,
			@RequestParam final String description, final HttpServletRequest request,
			final RedirectAttributes redirectAttributes) {
		logger.info("about to  doSuspend Particular BankUser " + id);
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
				BankUserStatusHistory bankUserStatusHistory = new BankUserStatusHistory();
				bankUserStatusHistory.setReason(reason);
				bankUserStatusHistory.setDescription(description);

				redirectAttributes.addFlashAttribute("bankUserStatusHistory", bankUserStatusHistory);
				redirectAttributes.addFlashAttribute("errorList", errorList);

				return "redirect:" + URL_BASE + "/suspend/" + id;
			}

			adminService.doSuspendBankUser(id, reason, description);

			// return "redirect:" + URL_BASE + "/suspendDone?mode=success&id=" + id;

			return "redirect:" + URL_BASE + "/user/list/1";
		} catch (Exception e) {
			// FIXME log event to db and return eventID to controller for
			// reporting purpose.
			logger.error("error suspending bankuser", e);
			// return "redirect:" + URL_BASE + "/suspendDone?mode=fail&id=" + id +
			// "&eventID=1";

			return "redirect:" + URL_BASE + "/user/list/1";
		}
	}

	@RequestMapping(value = { "/suspendDone" }, method = RequestMethod.GET)
	public String displaySuspendBankUserDone(final Model model,

			@RequestParam final String mode, @RequestParam final long id,

			@RequestParam(required = false) final Long eventID) {
		logger.info("about to Successfully Suspended Particular BankUser");
		PageBean pageBean = new PageBean("Suspend Bank User - All Done(Successfully)",
				"admin/user/suspendbankuser/suspendUserAlldoneSuccessful", Module.ADMIN, "admin/sideMenuBankUser");
		BankUser bankUser = adminService.loadBankUser(id);
		BankUserStatusHistory bankUserStatusHistory = adminService.loadBankUserStatusHistoryID(bankUser);
		boolean success = ("success".equals(mode));
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("bankUserStatusHistory", bankUserStatusHistory);
		model.addAttribute("bankUser", bankUser);
		model.addAttribute("success", success);
		model.addAttribute("eventID", eventID);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/unsuspend/{id}" }, method = RequestMethod.GET)
	public String displayUnSuspendBankUser(final Model model, @PathVariable final Long id,
			@ModelAttribute("bankUserStatusHistory") final BankUserStatusHistory bankUserStatusHistory,
			@ModelAttribute("errorList") final ArrayList<String> errorList) {
		logger.info("about to  unSuspend Particular BankUser  " + id);
		PageBean pageBean = new PageBean("Un-Suspend Bank User Reason Details",
				"admin/user/suspendbankuser/unsuspendBankUser", Module.ADMIN, "admin/sideMenuBankUser");
		pageBean.addJS("admin/suspend/adminSuspend.js");
		BankUser bankUser = adminService.loadBankUser(id);
		if (!CommonStatus.SUSPENDED.equals(bankUser.getStatus())) {
			// merchant status isn't active, then how do we suspend merchant?
			return URL_BASE + "/user/detail/" + id;
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("bankUser", bankUser);
		model.addAttribute("now", new Date());
		model.addAttribute("bankUserStatusHistory", bankUserStatusHistory);
		model.addAttribute("errorList", errorList);
		return TEMPLATE_DEFAULT;
	}

	/**
	 * Suspend bankuser
	 * <p>
	 * wireframe 03a.02a
	 * </p>
	 * 
	 * @param model
	 * @param id    PK ID of merchant
	 * @return
	 */
	@RequestMapping(value = { "/unsuspend/dounSuspend" }, method = RequestMethod.POST)
	public String doUnSuspendBankUser(final Model model, @RequestParam final Long id, @RequestParam final String reason,
			@RequestParam final String description, final RedirectAttributes redirectAttributes) {
		logger.info("about to  DounSuspend Particular BankUser");
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
				BankUserStatusHistory bankUserStatusHistory = new BankUserStatusHistory();
				bankUserStatusHistory.setReason(reason);
				bankUserStatusHistory.setDescription(description);

				redirectAttributes.addFlashAttribute("bankUserStatusHistory", bankUserStatusHistory);
				redirectAttributes.addFlashAttribute("errorList", errorList);

				return "redirect:" + URL_BASE + "/unsuspend/" + id;
			}

			adminService.doUnSuspendBankUser(id, reason, description);
			return "redirect:" + URL_BASE + "/user/list/1";
		} catch (Exception e) {

			logger.error("error suspending bankuser", e);

			return "redirect:" + URL_BASE + "/user/list/1";
		}
	}

	@RequestMapping(value = { "/unsuspendDone" }, method = RequestMethod.GET)
	public String displayUnSuspendBankUserDone(final Model model,

			@RequestParam final String mode, @RequestParam final long id,
			@RequestParam(required = false) final Long eventID) {
		logger.info("about to  unSuspend Particular BankUsers Successfully");
		PageBean pageBean = new PageBean("Suspend Bank User - All Done(Successfully)",
				"admin/user/suspendbankuser/unsuspendAlldoneSuccessful", Module.ADMIN, "admin/sideMenuBankUser");
		boolean success = ("success".equals(mode));
		BankUser bankUser = adminService.loadBankUser(id);
		BankUserStatusHistory bankUserStatusHistory = adminService.loadBankUserStatusHistoryID(bankUser);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("success", success);
		model.addAttribute("eventID", eventID);
		model.addAttribute("bankUserStatusHistory", bankUserStatusHistory);
		model.addAttribute("bankUser", bankUser);
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

	// Start Edit Bankuser request

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public String displayEditBankUser(final Model model, final HttpServletRequest request,
			@PathVariable final Long id) {
		logger.info("about to  Edit Particular BankUser   " + id);
		PageBean pageBean = new PageBean("BankUser Detail", "admin/user/edit/bankUserEditDetail", Module.ADMIN,
				"admin/sideMenuBankUser");
		BankUser bankUser = adminService.loadBankUser(id);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("bankUser", bankUser);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/editBankuser" }, method = RequestMethod.POST)
	public String displayEditBankUserSes(final Model model, final HttpServletRequest request,
			@RequestParam("id") final Long id, @RequestParam("firstName") final String firstName,
			@RequestParam("lastName") final String lastName, @RequestParam("email") final String email,
			@RequestParam("role") final BankUserRole role) {

		logger.info("about to  Edit Particular BankUser   " + id);
		PageBean pageBean = new PageBean("BankUser Detail", "admin/user/edit/bankUserEditDetail", Module.ADMIN,
				"admin/sideMenuBankUser");
		BankUser bankUser = adminService.loadBankUser(id);

		bankUser.setFirstName(firstName);
		bankUser.setLastName(lastName);
		bankUser.setEmail(email);
		bankUser.setRole(role);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("bankUser", bankUser);

		request.getSession(true).setAttribute("editBankUserSession", bankUser);

		return "redirect:/admin/editBankuserReviewandConfirm";

	}

	@RequestMapping(value = { "/editBankuserReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditBankUserConfirm(final Model model, final HttpServletRequest request) {

		logger.info("about to  Edit Particular BankUser Confirm  ");

		BankUser bankUser = (BankUser) request.getSession(true).getAttribute("editBankUserSession");
		if (bankUser == null) {
			// redirect user to the add page if there's no bankUser in session.
			return "redirect:/admin/editBankuser";
		}
		PageBean pageBean = new PageBean("Bank user edit Details", "admin/user/edit/bankUserReviewandConfirm",
				Module.ADMIN, "admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("bankUser", bankUser);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/confirmEditBankUserDetails" }, method = RequestMethod.GET)
	public String confirmEditBankUser(@ModelAttribute("bankUser") final BankUser bankUser, final Model model,
			final HttpServletRequest request) {
		logger.info("about to add BankUser Details Confirms");
		PageBean pageBean = new PageBean("Succefully BankUser edited", "admin/user/edit/bankUserAlldoneSuccessful",
				Module.ADMIN, "admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		BankUser bankUserSavedInHttpSession = (BankUser) request.getSession(true).getAttribute("editBankUserSession");
		if (bankUserSavedInHttpSession == null) {
			return "redirect:/admin/editBankuser}";
		}
		model.addAttribute(bankUserSavedInHttpSession);
		adminService.updateBankUser(bankUserSavedInHttpSession);
		request.getSession(true).removeAttribute("editBankUserSession");

		// return TEMPLATE_DEFAULT;
		return "redirect:" + URL_BASE + "/user/list/1";
	}
	// End of Edit Bankuser request

	@RequestMapping(value = { "/adm/dashBoard" }, method = RequestMethod.GET)
	public String merchantWebDashBoard(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {
		logger.info("Login Person in dash Board:" + principal.getName());
		PageBean pageBean = new PageBean("Dash Board", "dashboard/dashbrd", Module.ADMIN, "admin/sideMenuBankUser");

		List<DashBoardData> listDBD = new ArrayList<DashBoardData>();
		model.addAttribute("pageBean", pageBean);

		listDBD = dashBoardService.listDeviceTxnDet();

		logger.info(" listDeviceTxnDet :" + listDBD.size());
		String data = "";
		String data1 = "";

		// List<Double> data2= new ArrayList<Double>();
		for (DashBoardData fs : listDBD) {

			Double d = new Double(fs.getAmount());
			d = d / 100;

			String txnDate = "";
			if (fs.getTxnDate().equals("1")) {
				txnDate = "Jan";
			} else if (fs.getTxnDate().equals("2")) {
				txnDate = "Feb";
			} else if (fs.getTxnDate().equals("3")) {
				txnDate = "Mar";
			} else if (fs.getTxnDate().equals("4")) {
				txnDate = "Apr";
			} else if (fs.getTxnDate().equals("5")) {
				txnDate = "May";
			} else if (fs.getTxnDate().equals("6")) {
				txnDate = "Jun";
			} else if (fs.getTxnDate().equals("7")) {
				txnDate = "Jul";
			} else if (fs.getTxnDate().equals("8")) {
				txnDate = "Aug";
			} else if (fs.getTxnDate().equals("9")) {
				txnDate = "Sep";
			} else if (fs.getTxnDate().equals("10")) {
				txnDate = "Oct";
			} else if (fs.getTxnDate().equals("11")) {
				txnDate = "Nov";
			} else if (fs.getTxnDate().equals("12")) {
				txnDate = "Dec";
			}

			data = data + "['" + txnDate + "'," + d + "]";
			data1 = data1 + "['" + txnDate + "'," + fs.getCount() + "]";

		}

		data = data.replace("][", "],[");
		data1 = data1.replace("][", "],[");

		model.addAttribute("amt", data);
		model.addAttribute("cnt", data1);

		List<DashBoardData> listDBD1 = new ArrayList<DashBoardData>();
		listDBD1 = dashBoardService.listTxnVolCnt();

		logger.info(" listTxnVolCnt :" + listDBD1.size());
		String data11 = "";
		String data22 = "";

		for (DashBoardData fs : listDBD1) {
			// System.out.println(" amount :"+fs.getAmount());
			// System.out.println(" count :"+fs.getCount());
			// System.out.println(" month :"+fs.getTxnDate());

			Double d = null;
			if (fs.getAmount() != null) {
				d = new Double(fs.getAmount());
				d = d / 100;
			} else {
				d = 0.0;
			}
			String txnDate = "";
			if (fs.getTxnDate().equals("1")) {
				txnDate = "Jan";
			} else if (fs.getTxnDate().equals("2")) {
				txnDate = "Feb";
			} else if (fs.getTxnDate().equals("3")) {
				txnDate = "Mar";
			} else if (fs.getTxnDate().equals("4")) {
				txnDate = "Apr";
			} else if (fs.getTxnDate().equals("5")) {
				txnDate = "May";
			} else if (fs.getTxnDate().equals("6")) {
				txnDate = "Jun";
			} else if (fs.getTxnDate().equals("7")) {
				txnDate = "Jul";
			} else if (fs.getTxnDate().equals("8")) {
				txnDate = "Aug";
			} else if (fs.getTxnDate().equals("9")) {
				txnDate = "Sep";
			} else if (fs.getTxnDate().equals("10")) {
				txnDate = "Oct";
			} else if (fs.getTxnDate().equals("11")) {
				txnDate = "Nov";
			} else if (fs.getTxnDate().equals("12")) {
				txnDate = "Dec";
			}

			data11 = data11 + "['" + txnDate + "'," + d + "]";
			data22 = data22 + "['" + txnDate + "'," + fs.getCount() + "]";

		}

		data11 = data11.replace("][", "],[");
		data22 = data22.replace("][", "],[");

		model.addAttribute("amount", data11);
		model.addAttribute("count", data22);

		List<DashBoardData> listDBD2 = new ArrayList<DashBoardData>();
		listDBD2 = dashBoardService.listMerchantDeviceDet();

		logger.info(" listMerchantDeviceDet :" + listDBD2.size());
		String data13 = "";
		String data14 = "";
		String txnDate = "";
		for (DashBoardData fs : listDBD2) {
			// System.out.println(" name :"+fs.getMerchantName());
			// System.out.println(" count :"+fs.getCount());
			// System.out.println(" month :"+fs.getTxnDate());
			if (fs.getTxnDate().equals("1")) {
				txnDate = "Jan";
			} else if (fs.getTxnDate().equals("2")) {
				txnDate = "Feb";
			} else if (fs.getTxnDate().equals("3")) {
				txnDate = "Mar";
			} else if (fs.getTxnDate().equals("4")) {
				txnDate = "Apr";
			} else if (fs.getTxnDate().equals("5")) {
				txnDate = "May";
			} else if (fs.getTxnDate().equals("6")) {
				txnDate = "Jun";
			} else if (fs.getTxnDate().equals("7")) {
				txnDate = "Jul";
			} else if (fs.getTxnDate().equals("8")) {
				txnDate = "Aug";
			} else if (fs.getTxnDate().equals("9")) {
				txnDate = "Sep";
			} else if (fs.getTxnDate().equals("10")) {
				txnDate = "Oct";
			} else if (fs.getTxnDate().equals("11")) {
				txnDate = "Nov";
			} else if (fs.getTxnDate().equals("12")) {
				txnDate = "Dec";
			}

			if (fs.getMerchantName().equals("Merchant")) {
				data13 = data13 + "['" + txnDate + "'," + fs.getCount() + "]";
			} else if (fs.getMerchantName().equals("TerminalDetails")) {
				data14 = data14 + "['" + txnDate + "'," + fs.getCount() + "]";
			}
			// data2.add(d);

		}

		data13 = data13.replace("][", "],[");
		data14 = data14.replace("][", "],[");

		model.addAttribute("amount1", data13);
		model.addAttribute("count1", data14);

		// Transaction State Wise

		List<DashBoardData> listDBD3 = new ArrayList<DashBoardData>();
		listDBD3 = dashBoardService.listTxnStateDetails();

		logger.info(" listTxnStateDetails :" + listDBD3.size());
		String data23 = "";

		for (DashBoardData fs : listDBD3) {
			// System.out.println(" merchant Name :"+fs.getMerchantName());
			// System.out.println(" Amount :"+fs.getAmount());
			Double d = new Double(fs.getAmount());
			d = d / 100;
			data23 = data23 + "['" + fs.getMerchantName() + "'," + d + "]";

		}

		data23 = data23.replace("][", "],[");

		model.addAttribute("state", data23);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/adm1/dashBoard" }, method = RequestMethod.GET)
	public String webDashBoard(final Model model, final java.security.Principal principal, HttpServletRequest request) {
		logger.info("Login Person in dash Board:" + principal.getName());
		PageBean pageBean = new PageBean("Dash Board", "dashboard/dashbrd", Module.ADMIN, "admin/sideMenuBankUser");

		// List<DashBoardData> listDBD = new ArrayList<DashBoardData>();
		model.addAttribute("pageBean", pageBean);

		// Total Device

		int totalDevice = dashBoardService.getTotalDevice();

		model.addAttribute("totalDevice", totalDevice);

		// Total transaction Current month

		String totalTxn = dashBoardService.getCurrentMonthTxn();

		logger.info("check total txn:" + totalTxn);

		/*
		 * totalTxn = "1,234,300.00"; logger.info("check total amount111:" + totalTxn );
		 */
		model.addAttribute("totalTxn", totalTxn);

		PaginationBean<DashBoardData> paginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check 5 recent txn Amount Data ");
		dashBoardService.getLastFiveTxn(paginationBean);

		logger.info(" received 5 recent  txn Amount Data ");
		model.addAttribute("fiveTxnList", paginationBean);
		// Total Merchant

		String totalMerchant = dashBoardService.getTotalMerchant();

		model.addAttribute("totalMerchant", totalMerchant);

		logger.info("check dashboard details:");
		List<ThreeMonthTxnData> data = new ArrayList<ThreeMonthTxnData>();
		// new changes for bar chart 05072017
		// List<ThreeMonthTxnData> txnCountData = new ArrayList<ThreeMonthTxnData>();

		PaginationBean<ThreeMonthTxnData> paginationBean1 = new PaginationBean<ThreeMonthTxnData>();
		// paginationBean1.setCurrPage(currPage);
		// logger.info("check dashboard details787878:");
		List<ThreeMonthTxnData> txnListData = new ArrayList<ThreeMonthTxnData>();
		txnListData = dashBoardService.getThreeMonthTxn();
		// logger.info("check dashboard details457454:");

		logger.info("check dashboard txn details:" + txnListData.size());
		// for(ThreeMonthTxnData txnMonthData: txnCountData){
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

		return TEMPLATE_DEFAULT;
	}

	// @RequestMapping(value = { "/merchantadm/dashBoard" }, method =
	// RequestMethod.GET)
	@RequestMapping(value = { "/merdashBoard" }, method = RequestMethod.GET)
	public String webMerchantDashBoard(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {
		logger.info("dash Board: merchant adminctrlr" + principal.getName());
		PageBean pageBean = new PageBean("Dash Board", "dashboard/merchantdashbrd", Module.MERCHANT,
				"admin/sideMenuBankUser");

		/*
		 * PageBean pageBean = new PageBean("Dash Board", "dashboard/Newdashboard",
		 * Module.MERCHANT, "admin/sideMenuBankUser");
		 */

		model.addAttribute("pageBean", pageBean);
		HttpSession session = request.getSession();
		// String username = (String)request.getAttribute("un");
		// Merchant merchant = me

		logger.info("Login Person in dash Board in admincontroller:" + principal.getName());
		Merchant merchant = merchantService.loadMerchant(principal.getName());
		session.setAttribute("merchantUserName", merchant.getFirstName());

		logger.info("id: " + merchant.getId());
		if (merchant.getPreAuth() != null) {
			model.addAttribute("preAuth", merchant.getPreAuth());
		} else {
			model.addAttribute("preAuth", "No");
		}
		List<MobileUser> mobileuser = mobileUser.loadMobileUserByFk(merchant.getId());

		Set<String> enableBoost = new HashSet<String>();
		for (MobileUser mu : mobileuser) {
			String enableBoost1 = mu.getEnableBoost();
			if (enableBoost1 != null) {
				logger.info("enableBoost1: " + enableBoost1);
				enableBoost.add(enableBoost1.toString());

			}

		}
		if (enableBoost.contains("Yes")) {

			model.addAttribute("enableBoost", "Yes");
		} else {
			model.addAttribute("enableBoost", "No");
		}

		Set<String> enableMoto = new HashSet<String>();
		for (MobileUser mu : mobileuser) {
			String enableMoto1 = mu.getEnableMoto();
			if (enableMoto1 != null) {
				logger.info("enableMoto: " + enableMoto1);
				enableMoto.add(enableMoto1.toString());
			}
			// tidSet.add(t.getDeviceId());
		}
		if (enableMoto.contains("Yes")) {
			model.addAttribute("enableMoto", "Yes");
		} else {
			model.addAttribute("enableMoto", "No");
		}

		logger.info("display merchant web " + merchant.getMid().getMid());

		String ezyLinkCheck = "NO";
		if (merchant.getAuth3DS() != null) {

			if (merchant.getAuth3DS().equalsIgnoreCase("Yes")) {
				ezyLinkCheck = "YES";
			} else {
				ezyLinkCheck = "NO";

			}

		}

		String EzypodCheck = "NO";
		MID mid = merchantService.loadMidByMerchant_PK(String.valueOf(merchant.getId()));
		// String ezyPodSumCheck = null;
		if (mid.getEzyrecMid() != null) {

			TerminalDetails termDetails = merchantService.loadTerminalDetailsByMid(mid.getEzyrecMid());

			if ((termDetails.getDeviceType() == "EZYPOD") || (termDetails.getDeviceType().equals("EZYPOD"))) {
				EzypodCheck = "YES";
				session.setAttribute("ezyPodCheck", "Yes");
				// ezyPodSumCheck = "YES";
			}
		}

		int totalDevice = dashBoardService.getMerchantTotalDevice(merchant.getMid().getMid());

		model.addAttribute("totalDevice", totalDevice);

		String totalTxn = dashBoardService.getMerchantCurrentMonthTxn(merchant);

		model.addAttribute("totalTxn", totalTxn);

		String dailytxn = dashBoardService.getMerchantDailyTxn(merchant);

		logger.info("dailytxn:  " + dailytxn);
		model.addAttribute("dailytxn", dailytxn);

		String weeklytxn = dashBoardService.getMerchantWeeklyTxn(merchant);

		logger.info("weeklytxn:  " + weeklytxn);
		model.addAttribute("weeklytxn", weeklytxn);

		PaginationBean<DashBoardData> paginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check 5 recent txn Amount Data ");
		dashBoardService.getMerchantLastFiveTxn(paginationBean, merchant, EzypodCheck, ezyLinkCheck);

		logger.info(" received 5 recent  txn Amount Data ");
		model.addAttribute("fiveTxnList", paginationBean);

		/*
		 * //logger.info("check dashboard details:"); List<ThreeMonthTxnData> data = new
		 * ArrayList<ThreeMonthTxnData>(); //new changes for bar chart 05072017
		 * //List<ThreeMonthTxnData> txnCountData = new ArrayList<ThreeMonthTxnData>();
		 * 
		 * 
		 * PaginationBean<ThreeMonthTxnData> paginationBean1 = new
		 * PaginationBean<ThreeMonthTxnData>(); //paginationBean1.setCurrPage(currPage);
		 * //logger.info("check dashboard details787878:"); List<ThreeMonthTxnData>
		 * txnListData = new ArrayList<ThreeMonthTxnData>();
		 * //logger.info("check mid details:" + merchant.getMid().getMid() );
		 * txnListData= dashBoardService.getMerchantTxnCount(merchant);
		 * //logger.info("check dashboard details457454:");
		 * 
		 * //logger.info("check dashboard txn details45677:" + txnListData.size());
		 * //for(ThreeMonthTxnData txnMonthData: txnCountData){ for(ThreeMonthTxnData
		 * txnMonthData: txnListData){
		 * 
		 * //logger.info("check monthly count12333:" + txnMonthData );
		 * 
		 * //logger.info("Controller agentDet123333 : "+txnMonthData.getCount());
		 * //logger.info("Controller amount23333 : "+txnMonthData.getAmount());
		 * //logger.info("Controller date33333 : "+txnMonthData.getDate1()); String
		 * a3="\""; for(String a1:txnMonthData.getDate1()){ a3=a3+a1; a3=a3+"\",\""; }
		 * //System.out.println("Data : "+a3); a3=a3+"\"";
		 * //System.out.println("Data : "+a3); a3=a3.replace(",\"\"", "");
		 * //System.out.println("Data : "+a3); txnMonthData.setMonth(a3);
		 * 
		 * String b3=""; for(String b1:txnMonthData.getAmount()){ b3=b3+b1; b3=b3+","; }
		 * //System.out.println("Data : "+b3); b3=b3+",";
		 * //System.out.println("Data : "+b3); b3=b3.replace(",,", "");
		 * //System.out.println("Data : "+b3); txnMonthData.setAmountData(b3);
		 * 
		 * String c3=""; for(String c1:txnMonthData.getCount()){ c3=c3+c1; c3=c3+","; }
		 * //System.out.println("Data : "+c3); c3=c3+",";
		 * System.out.println("Data : "+c3); c3=c3.replace(",,", "");
		 * //System.out.println("Data : "+c3); txnMonthData.setCountData(c3);
		 * 
		 * 
		 * 
		 * model.addAttribute("threeMonthTxn", txnMonthData);
		 * 
		 * 
		 * data.add(txnMonthData);
		 * 
		 * }
		 */
		// End Agent

		/*
		 * paginationBean1.setItemList(data);
		 * 
		 * 
		 * model.addAttribute("paginationBean", paginationBean1);
		 */

		logger.info("in admincontroller dashboard:");
		model.addAttribute("checkDeviceStatus", "NO");
		model.addAttribute("loginname", principal.getName());

		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/nonmerchant/dashBoard" }, method = RequestMethod.GET)
	public String webnonMerchantDashBoard(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {
		logger.info("Login Person in dash Board: admincontroller" + principal.getName());
		PageBean pageBean = new PageBean("Dash Board", "dashboard/nonmerchantdashbrd", Module.MERCHANT,
				"admin/sideMenuBankUser");

		/*
		 * PageBean pageBean = new PageBean("Dash Board", "dashboard/Newdashboard",
		 * Module.MERCHANT, "admin/sideMenuBankUser");
		 */

		model.addAttribute("pageBean", pageBean);
		HttpSession session = request.getSession();
		// String username = (String)request.getAttribute("un");
		// Merchant merchant = me

		logger.info("Login Person in dash Board:" + principal.getName());
		Merchant merchant = merchantService.loadMerchant(principal.getName());
		session.setAttribute("merchantUserName", merchant.getFirstName());

		logger.info("display nonmerchant web dashboard" + merchant.getMid().getMid());

		// int totalDevice =
		// dashBoardService.getMerchantTotalDevice(merchant.getMid().getMid());

		// model.addAttribute("totalDevice", totalDevice);

		String totalTxn = dashBoardService.getnonMerchantCurrentMonthTxn(merchant.getMid().getMid());

		model.addAttribute("totalTxn", totalTxn);

		// logger.info("check dashboard details:");
		List<ThreeMonthTxnData> data = new ArrayList<ThreeMonthTxnData>();
		// new changes for bar chart 05072017
		// List<ThreeMonthTxnData> txnCountData = new ArrayList<ThreeMonthTxnData>();

		PaginationBean<ThreeMonthTxnData> paginationBean1 = new PaginationBean<ThreeMonthTxnData>();
		// paginationBean1.setCurrPage(currPage);
		// logger.info("check dashboard details787878:");
		List<ThreeMonthTxnData> txnListData = new ArrayList<ThreeMonthTxnData>();
		// logger.info("check mid details:" + merchant.getMid().getMid() );
		txnListData = dashBoardService.getnonMerchantTxnCount(merchant.getMid().getMid());
		// logger.info("check dashboard details457454:");

		// logger.info("check dashboard txn details45677:" + txnListData.size());
		// for(ThreeMonthTxnData txnMonthData: txnCountData){
		for (ThreeMonthTxnData txnMonthData : txnListData) {

			// logger.info("check monthly count12333:" + txnMonthData );

			// logger.info("Controller agentDet123333 : "+txnMonthData.getCount());
			// logger.info("Controller amount23333 : "+txnMonthData.getAmount());
			// logger.info("Controller date33333 : "+txnMonthData.getDate1());
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
			System.out.println("Data : " + c3);
			c3 = c3.replace(",,", "");
			// System.out.println("Data : "+c3);
			txnMonthData.setCountData(c3);

			model.addAttribute("threeMonthTxn", txnMonthData);

			data.add(txnMonthData);

		}
		// End Agent

		paginationBean1.setItemList(data);
		model.addAttribute("deviceCheck", "NO");

		model.addAttribute("paginationBean", paginationBean1);

		return TEMPLATE_NONMERCHANT;
	}

	@RequestMapping(value = { "/agent/dashBoard" }, method = RequestMethod.GET)
	public String webAgentDashBoard(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {
		logger.info("Login Person in dash Board:" + principal.getName());
		PageBean pageBean = new PageBean("Dash Board", "dashboard/agentdashbrd", Module.MERCHANT,
				"admin/sideMenuBankUser");

		model.addAttribute("pageBean", pageBean);
		HttpSession session = request.getSession();
		Agent agent = agentService.loadAgent(principal.getName());
		// String username = (String)request.getAttribute("un");
		logger.info("display agent web : " + agent.getFirstName());
		session.setAttribute("agentUserName", agent.getFirstName());

		int totalDevice = dashBoardService.getAgentTotalDevice(agent.getId());

		model.addAttribute("totalDevice", totalDevice);

		String totalMerchant = dashBoardService.getAgentTotalMerchant(agent.getId());

		model.addAttribute("totalMerchant", totalMerchant);

		PaginationBean<DashBoardData> paginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check 5 recent txn Amount Data ");
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());

		List<MID> ids = new ArrayList<MID>();
		for (Merchant t : merchant1) {
			ids.add(t.getMid());
		}

		logger.info("ids:  " + ids);

		StringBuffer str = new StringBuffer();
		StringBuffer strUm = new StringBuffer();
		List<String> midList = new ArrayList<String>();
		List<String> ummidList = new ArrayList<String>();
		for (MID mi : ids) {

			if (mi.getMid() != null) {
				if (!mi.getMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMid()); str.append("\",");
					 */
					midList.add(mi.getMid());

				}

			}
			if (mi.getMotoMid() != null) {
				if (!mi.getMotoMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getMotoMid());
				}

			}
			if (mi.getEzypassMid() != null) {
				if (!mi.getEzypassMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getEzypassMid());
				}

			}

			if (mi.getEzywayMid() != null) {
				if (!mi.getEzywayMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getEzywayMid());
				}

			}

			if (mi.getEzyrecMid() != null) {
				if (!mi.getEzyrecMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getEzyrecMid());
				}

			}

			if (mi.getUmMid() != null) {
				if (!mi.getUmMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getUmMid());
				}

			}

			if (mi.getUmMotoMid() != null) {
				if (!mi.getUmMotoMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getUmMotoMid());
				}

			}
			if (mi.getUmEzywayMid() != null) {
				if (!mi.getUmEzywayMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getUmEzywayMid());
				}

			}

		}
		int u = 0;
		for (String strMid : midList) {

			if (u == 0) {
				str.append("\"");
				str.append(strMid);
				str.append("\"");
				u++;
			} else {
				str.append(",\"");
				str.append(strMid);
				str.append("\"");
			}
		}
		logger.info("String of MIDs:  " + str);

		dashBoardService.getAgentLastFiveTxn(paginationBean, str);

		logger.info(" received 5 recent  txn Amount Data ");
		model.addAttribute("fiveTxnList", paginationBean);

		// String totalTxn = dashBoardService.getAgentCurrentMonthTxn(agent.getId());

		String totalTxn = dashBoardService.getAgentMerchantMonthTxn(str);

		model.addAttribute("totalTxn", totalTxn);

		// logger.info("check dashboard details:");
		List<ThreeMonthTxnData> data = new ArrayList<ThreeMonthTxnData>();
		// new changes for bar chart 05072017
		// List<ThreeMonthTxnData> txnCountData = new ArrayList<ThreeMonthTxnData>();

		PaginationBean<ThreeMonthTxnData> paginationBean1 = new PaginationBean<ThreeMonthTxnData>();
		// paginationBean1.setCurrPage(currPage);
		// logger.info("check dashboard details787878:");
		List<ThreeMonthTxnData> txnListData = new ArrayList<ThreeMonthTxnData>();
		// logger.info("check mid details:" + agent.getId());
		txnListData = dashBoardService.getAgentTxnCount(agent.getId());
		// logger.info("check dashboard details457454:");

		logger.info("check dashboard txn details45677:" + txnListData.size());
		for (ThreeMonthTxnData txnMonthData : txnListData) {

			// logger.info("check monthly count12333:" + txnMonthData );

			// logger.info("Controller agentDet1 : "+txnMonthData.getCount());
			// logger.info("Controller amount2 : "+txnMonthData.getAmount());
			// logger.info("Controller date3 : "+txnMonthData.getDate1());
			String a3 = "\"";

			// String a3="\"";
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

		paginationBean1.setItemList(data);

		model.addAttribute("paginationBean", paginationBean1);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/txnDashBoard" }, method = RequestMethod.GET)
	public String webTxnDashBoard(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {
		logger.info("Login Person in dash Board:" + principal.getName());

//		List<String> nobList= dashBoardService.getNobList();

		/*
		 * List<Merchant> merchant1 = merchantService.loadMerchant(); Set<String>
		 * nobList = new HashSet<String>(); for (Merchant t : merchant1) {
		 * if(t.getNatureOfBusiness()!=null) { if(!t.getNatureOfBusiness().isEmpty()){
		 * String nob = t.getNatureOfBusiness(); nobList.add(nob.toString()); } } }
		 * logger.info("nobList"+nobList);
		 */
		PageBean pageBean = new PageBean("NOB details", "dashboard/txnDetailsDashboard", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("pageBean", pageBean);
		// model.addAttribute("nobList", nobList);
		model.addAttribute("nobList", AddMerchantOptionList.natureOfBusinessList());
		// model.addAttribute("merchant1", merchant1);
		logger.info("webTxnDashBoard completed");

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/merchantTxnDetails" }, method = RequestMethod.GET)
	public String webTxnDetails(final Model model,
			/* @PathVariable("id") Long id, */
			@RequestParam("nob") String nob, final java.security.Principal principal) {
		logger.info("nob selected:" + nob);

		/*
		 * List<Merchant> merchant1 = merchantService.loadMerchant(); Set<String>
		 * nobList = new HashSet<String>(); for (Merchant t : merchant1) {
		 * if(t.getNatureOfBusiness()!=null) { if(!t.getNatureOfBusiness().isEmpty()){
		 * String nobs = t.getNatureOfBusiness(); nobList.add(nobs.toString()); } } }
		 */

		// List<Merchant> merchantList = merchantService.loadMerchantByNOB(nob);

		// logger.info("merchantList for nob selected:"+nob+"::"+merchantList);

		DateTime dateTime = new DateTime();
		logger.info("3 " + dateTime);
		logger.info("4 " + dateTime.minusDays(1));
		LocalDate lastDate = dateTime.minusDays(1).toLocalDate();
		String lastDay = lastDate.toString();

		List<Merchant> merchantList = merchantService.loadMerchantByNOB(nob);
		// List<Long> ids = new ArrayList<Long>();
		List<MID> ids = new ArrayList<MID>();
		for (Merchant t : merchantList) {
			ids.add(t.getMid());
		}

		logger.info("ids:  " + ids);

		StringBuffer str = new StringBuffer();
		StringBuffer strUm = new StringBuffer();
		List<String> midList = new ArrayList<String>();
		List<String> ummidList = new ArrayList<String>();
		for (MID mi : ids) {

			if (mi.getMid() != null) {
				if (!mi.getMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMid()); str.append("\",");
					 */
					midList.add(mi.getMid());

				}

			}
			if (mi.getMotoMid() != null) {
				if (!mi.getMotoMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getMotoMid());
				}

			}
			if (mi.getEzypassMid() != null) {
				if (!mi.getEzypassMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getEzypassMid());
				}

			}

			if (mi.getEzywayMid() != null) {
				if (!mi.getEzywayMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getEzywayMid());
				}

			}

			if (mi.getEzyrecMid() != null) {
				if (!mi.getEzyrecMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getEzyrecMid());
				}

			}

			if (mi.getUmMid() != null) {
				if (!mi.getUmMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getUmMid());
				}

			}

			if (mi.getUmMotoMid() != null) {
				if (!mi.getUmMotoMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					ummidList.add(mi.getUmMotoMid());
				}

			}
			if (mi.getUmEzywayMid() != null) {
				if (!mi.getUmEzywayMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					ummidList.add(mi.getUmEzywayMid());
				}

			}

		}
		int i = 0;
		for (String strMid : midList) {

			if (i == 0) {
				str.append("\"");
				str.append(strMid);
				str.append("\"");
				i++;
			} else {
				str.append(",\"");
				str.append(strMid);
				str.append("\"");
			}
		}
		logger.info("String of MIDs:  " + str);

		int j = 0;
		for (String strMid : ummidList) {

			if (j == 0) {
				strUm.append("\"");
				strUm.append(strMid);
				strUm.append("\"");
				j++;
			} else {
				strUm.append(",\"");
				strUm.append(strMid);
				strUm.append("\"");
			}

		}

		logger.info("String of UMMIDs:  " + strUm);

		String totalMonTxn = merchantService.getMerchantCurrentMonthTxnByNOB(str, strUm);

		logger.info("totalMonTxn:  " + totalMonTxn);

		String dailytxn = merchantService.getMerchantDailyTxnByNOB(str, strUm);

		logger.info("dailytxn:  " + dailytxn);

		String weeklytxn = merchantService.getMerchantWeeklyTxnByNOB(str, strUm);

		logger.info("weeklytxn:  " + weeklytxn);

		PageBean pageBean = new PageBean("NOB details", "dashboard/txnDetailsDashboard", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		model.addAttribute("nob", nob);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("nobList", AddMerchantOptionList.natureOfBusinessList());
		model.addAttribute("totalMonTxn", totalMonTxn);
		model.addAttribute("dailytxn", dailytxn);
		model.addAttribute("weeklytxn", weeklytxn);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/getRecentTxn" }, method = RequestMethod.GET)
	public String getLatestTxnDashBoard(final Model model, final java.security.Principal principal) {
		logger.info("Login Person in dash Board:" + principal.getName());

		PaginationBean<DashBoardData> txnPaginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check first 100 txn Amount Data ");
		dashBoardService.getLastHundredTxn(txnPaginationBean);

		logger.info(" received first 100 txn Amount Data ");

		PageBean pageBean = new PageBean("Dash Board", "dashboard/completeReport", Module.ADMIN,
				"admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("fiveTxnList", txnPaginationBean);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/getRecentTxnUmobile" }, method = RequestMethod.GET)
	public String getLatestTxnDashBrdUmobile(final Model model, final java.security.Principal principal) {
		logger.info("Login Person in dash Board:" + principal.getName());

		PaginationBean<DashBoardData> txnPaginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check first 100 txn Amount Data ");
		dashBoardService.getLastHundredTxnUmobile(txnPaginationBean);

		logger.info(" received first 100 txn Amount Data ");

		PageBean pageBean = new PageBean("Dash Board", "dashboard/completeReport", Module.ADMIN,
				"admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("fiveTxnList", txnPaginationBean);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/getMerchantRecentTxn" }, method = RequestMethod.GET)
	public String getMerchantTxnDashBoard(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {
		logger.info("Login Person in dash Board:" + principal.getName());
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		String EzypodCheck = "NO";

		MID mid = merchantService.loadMidByMerchant_PK(String.valueOf(currentMerchant.getId()));
		// String ezyPodSumCheck = null;
		if (mid.getEzyrecMid() != null) {

			TerminalDetails termDetails = merchantService.loadTerminalDetailsByMid(mid.getEzyrecMid());

			if ((termDetails.getDeviceType() == "EZYPOD") || (termDetails.getDeviceType().equals("EZYPOD"))) {
				EzypodCheck = "YES";
			}
		}

		String ezyLinkCheck = "NO";
		if (currentMerchant.getAuth3DS() != null) {

			if (currentMerchant.getAuth3DS().equalsIgnoreCase("Yes")) {
				ezyLinkCheck = "YES";
			} else {
				ezyLinkCheck = "NO";

			}

		}
		PaginationBean<DashBoardData> txnPaginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check first 100 txn Amount Data ");
		dashBoardService.getMerchantHundredTxn(txnPaginationBean, currentMerchant, EzypodCheck, ezyLinkCheck);

		logger.info(" received first 100 txn Amount Data ");

		PageBean pageBean = new PageBean("Dash Board", "dashboard/completeMerchantReport", Module.MERCHANT,
				"admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("fiveTxnList", txnPaginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/forgotPasswordCheck" }, method = RequestMethod.GET)
	public String forgotPasswordCheck(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/addSubMerchant" }, method = RequestMethod.GET)
	public String displayAddSubMerchant(@ModelAttribute("merchantt") final RegAddMerchant regAddMerchant,
			final HttpServletRequest request, Model model, final java.security.Principal principal) {

		logger.info("About to Add Sub merchant details");
		logger.info(" Admin login person Name:" + principal.getName());

		PageBean pageBean = new PageBean("Merchant Detail", "merchant/addsubmerchant/addMerchantDetails",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		String err = (String) request.getSession(true).getAttribute("addErSession");
		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {

				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("editErSession");
			}
		}

		String UmEzywayMid = null;
		String boostMid = null;

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
		}

		if (currentMerchant.getMid().getBoostMid() != null) {
			boostMid = currentMerchant.getMid().getBoostMid();
		}

		String userName = principal.getName();

		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal and local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else if (userName.equalsIgnoreCase("FINANCE@DLOCAL.COM")
				|| userName.equalsIgnoreCase("daria.ar@monetix.pro")) {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}

//		model.addAttribute("agentNameList", agentNameList);
		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("stateList", AddMerchantOptionList.stateList());
		model.addAttribute("documentsList", AddMerchantOptionList.documentsList());
		model.addAttribute("CategoryList", AddMerchantOptionList.CategoryList());
		model.addAttribute("natureOfBusinessList", AddMerchantOptionList.natureOfBusinessList());
		model.addAttribute("UmEzywayMid", UmEzywayMid);
		model.addAttribute("boostMid", boostMid);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/addMerchant" }, method = RequestMethod.POST)
	public String processAddUserForm(@ModelAttribute("merchantt") final RegAddMerchant regAddMerchant,
			final HttpServletRequest request, final ModelMap model, final BindingResult errors,
			final Principal principal) {

		logger.info("Submitted to Add Sub Merchant Details");
		logger.info("Admin login person Name:" + principal.getName());

		Merchant mMerchant = merchantDao.loadMerchant(principal.getName());

		PageBean pageBean = null;

		String Mid = null;
		String responseDataMidExist = null;
		String responseDataOfficeEmail = null;

		if (mMerchant != null) {
			if (mMerchant.getMerchantType() != null) {
				if (mMerchant.getMerchantType().equalsIgnoreCase("U")) {
					regAddMerchant.setMerchantType("U");
					regAddMerchant.setMid("2011");
				} else {
					regAddMerchant.setMerchantType("P");
					regAddMerchant.setMid("1011");
				}
			} else {
				regAddMerchant.setMerchantType("P");
				regAddMerchant.setMid("1011");
			}
		}

		regAddMerchant.setVcc("No");
		regAddMerchant.setPreAuth("No");
		regAddMerchant.setAutoSettled("No");
		regAddMerchant.setAuth3DS("Yes");

		logger.info("Merchant Type : " + regAddMerchant.getMerchantType());
		logger.info("VCC  : " + regAddMerchant.getVcc());
		logger.info("Pre-Auth  : " + regAddMerchant.getPreAuth());
		logger.info("Auto Settled  : " + regAddMerchant.getAutoSettled());
		logger.info("OTP  : " + regAddMerchant.getAuth3DS());
		logger.info("Official Mail  : " + regAddMerchant.getOfficeEmail());

		String UmEzywayMid = null;
		String boostMid = null;
		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
		}

		if (currentMerchant.getMid().getBoostMid() != null) {
			boostMid = currentMerchant.getMid().getBoostMid();
		}

		String userName = principal.getName();

		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else if (userName.equalsIgnoreCase("FINANCE@DLOCAL.COM")
				|| userName.equalsIgnoreCase("daria.ar@monetix.pro")) {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}
		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("UmEzywayMid", UmEzywayMid);
		model.addAttribute("boostMid", boostMid);
		model.addAttribute("pageBean", pageBean);
		// model.addAttribute("merchant", regAddMerchant);
		// PCI
		request.getSession(true).setAttribute("addSubMerchantSession", regAddMerchant);

		return "redirect:/admin/merchantDetailsReviewAndConfirm";
	}

	@RequestMapping(value = { "/merchantDetailsReviewAndConfirm" }, method = RequestMethod.GET)
	public String displayAddUserConfirmation(final ModelMap model, final HttpServletRequest request,
			final RegAddMerchant regAddMerchant, final java.security.Principal principal) {

		logger.info("About to Add Sub Merchant Details  , View Review And Confirm");
		RegAddMerchant merchant = (RegAddMerchant) request.getSession(true).getAttribute("addSubMerchantSession");

		if (merchant == null) {
			return "redirect:" + URL_BASE + "/admin/addSubMerchant";
		}
		PageBean pageBean = new PageBean("Merchant user add Details",
				"merchant/addsubmerchant/addMerchantReviewandConfirm", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		String UmEzywayMid = null;
		String boostMid = null;
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
		}

		if (currentMerchant.getMid().getBoostMid() != null) {
			boostMid = currentMerchant.getMid().getBoostMid();
		}

		String userName = principal.getName();

		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else if (userName.equalsIgnoreCase("FINANCE@DLOCAL.COM")
				|| userName.equalsIgnoreCase("daria.ar@monetix.pro")) {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}
		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("UmEzywayMid", UmEzywayMid);
		model.addAttribute("boostMid", boostMid);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("submerchant", merchant);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/merchantDetailsReviewAndConfirm" }, method = RequestMethod.POST)
	public String confirmAddMerchant(@ModelAttribute("merchant") final Merchant merchant, final Model model,
			final RegAddMerchant regAddMerchant, final java.security.Principal principal,
			final HttpServletRequest request) {

		PageBean pageBean = new PageBean("Succefully New Merchant Added",
				"merchant/addsubmerchant/addMerchantAlldoneSuccessful", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
//API
		logger.info("About to Add Sub Merchant Details  , Submit Review And Confirm");
		logger.info("Merchant will Add by :" + principal.getName());

		Merchant mMerchant = merchantDao.loadMerchant(principal.getName());

		RegAddMerchant MerchantSavedInHttpSession = (RegAddMerchant) request.getSession(true)
				.getAttribute("addSubMerchantSession");
		if (MerchantSavedInHttpSession == null) {
			return "redirect:/admin/addMerchant";
		}

		/* Unwanted Validation */
//		BaseDataImpl baseData = new BaseDataImpl();
//		RegAddMerchant a = baseData.vaildated(MerchantSavedInHttpSession);

//		String responseDataError = null;
//		if (a != null) {
//			logger.info("Contains HTML tags");
//			request.getSession(true).setAttribute("addErSession", "yes");
//			return "redirect:/admin/addMerchant";
//		}

		logger.info("Business Name :" + MerchantSavedInHttpSession.getBusinessName());
//		regAddMerchant.setMmid(principal.getName());
//		MerchantSavedInHttpSession.setMmid(principal.getName());
//sakthi
		regAddMerchant.setMmid(mMerchant.getBusinessName());
		MerchantSavedInHttpSession.setMmid(mMerchant.getBusinessName());

		model.addAttribute(MerchantSavedInHttpSession);
		RegAddMerchant regAddmerchant = null;
		try {
			logger.info("System in control to  add  Sub merchant:");
			regAddmerchant = merchantService.addSubMerchant(MerchantSavedInHttpSession);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("in merchantDetailsReviewAndConfirm(post) and email is " + MerchantSavedInHttpSession.getEmail());

//		Merchant sMerchant = merchantDao.loadMerchant(MerchantSavedInHttpSession.getEmail());
		Merchant sMerchant = merchantDao.loadMerchantDetail1(MerchantSavedInHttpSession.getBusinessName());

//	logger.info("user name is "+sMerchant.getUsername());

		logger.info("Erchant Object : " + sMerchant);
		logger.info("Erchant email : " + sMerchant.getEmail());
		logger.info("Erchant mid : " + sMerchant.getMid().getSubMerchantMID());

		if (sMerchant != null) {
			logger.info("About to LOG in Audit trail:");
			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(sMerchant.getEmail(), principal.getName(),
					"addSubMerchant");

			if (auditTrail.getUsername() != null) {
				logger.info("Sub Merchant :" + auditTrail.getUsername() + " Successfully Added by Merchant: "
						+ auditTrail.getModifiedBy());
			}
		}

		logger.info("MID" + sMerchant.getMid().getSubMerchantMID());
		logger.info("BN" + sMerchant.getBusinessName());

		String UmEzywayMid = null;
		String boostMid = null;
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
		}

		if (currentMerchant.getMid().getBoostMid() != null) {
			boostMid = currentMerchant.getMid().getBoostMid();
		}

		String userName = principal.getName();

		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else if (userName.equalsIgnoreCase("FINANCE@DLOCAL.COM")
				|| userName.equalsIgnoreCase("daria.ar@monetix.pro")) {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}
		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("boostMid", boostMid);
		model.addAttribute("UmEzywayMid", UmEzywayMid);
		model.addAttribute("smerchant", sMerchant);
		request.getSession(true).removeAttribute("addSubMerchantSession");
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// rksubmerchant
	@RequestMapping(value = { "/submerchantSum" }, method = RequestMethod.GET)
	public String displayAddSubMerchantsUM(@ModelAttribute("merchant") final RegAddMerchant regAddMerchant,
			final HttpServletRequest request, Model model, final java.security.Principal principal) {

		logger.info("About to Add Sub merchant details");
		logger.info(" Admin login person Name:" + principal.getName());

		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/submerchantList", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		// paginationBean.setCurrPage(currPage);

		String UmEzywayMid = null;
		String boostMid = null;
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		Long merchantid = currentMerchant.getId();

		merchantService.listsubMerchantdefault(paginationBean, merchantid, null);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
		}

		if (currentMerchant.getMid().getBoostMid() != null) {
			boostMid = currentMerchant.getMid().getBoostMid();
		}

		String userName = principal.getName();

		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else if (userName.equalsIgnoreCase("FINANCE@DLOCAL.COM")
				|| userName.equalsIgnoreCase("daria.ar@monetix.pro")) {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}
		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("boostMid", boostMid);
		model.addAttribute("UmEzywayMid", UmEzywayMid);

		model.addAttribute("paginationBean", paginationBean);

		/*
		 * if(principal.getName().equals(bhuvi@mobiversa.com)) { return
		 * TEMPLATE_SUPER_AGENT; }
		 */
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// rksubmerchantsearch
	@RequestMapping(value = { "/submersearch" }, method = RequestMethod.GET)
	public String displaySearchMerchant(final Model model, final HttpServletRequest request,
			final java.security.Principal principal,

			@RequestParam(required = false, defaultValue = "1") final int currPage, @RequestParam final String date,
			@RequestParam final String date1) {
		logger.info("about to search Merchant based on search String:: " + date);

		logger.info("about to search Merchant based on search String:: " + date1);
		// String type=null;
		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/submerchantList", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();

		paginationBean.setCurrPage(currPage);

		logger.info("userrolle: " + paginationBean.getItemList());
		model.addAttribute("pageBean", pageBean);

		model.addAttribute("paginationBean", paginationBean);

		String UmEzywayMid = null;
		String boostMid = null;
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		Long merchantid = currentMerchant.getId();

		merchantService.listsubMerchant(paginationBean, date, date1, merchantid);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
		}

		if (currentMerchant.getMid().getBoostMid() != null) {
			boostMid = currentMerchant.getMid().getBoostMid();
		}
		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("UmEzywayMid", UmEzywayMid);
		model.addAttribute("boostMid", boostMid);

		String userName = principal.getName();

		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else if (userName.equalsIgnoreCase("FINANCE@DLOCAL.COM")
				|| userName.equalsIgnoreCase("daria.ar@monetix.pro")) {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// rksubmerchantexport
	@RequestMapping(value = "/submerexport", method = RequestMethod.GET)
	public ModelAndView getExcel(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false) String export, final Model model, final HttpServletRequest request,
			final java.security.Principal principal) {
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

		// List<MobileUser>
		// mobileuser=mobileUser.loadMobileUserByFk(merchant.getId());

		// List<Merchant> list
		// =merchantService.merchantSummaryExport(date,date1);

//        logger.info("check mobile user from date:" + date);
//         logger.info("Item count: " + paginationBean.getItemList().size());
//        for (Merchant m1 : list) {
//
//               if (m1.getAgID() != null) {
//                      Agent agent = agentService.loadAgentByIdPk(m1.getAgID().longValue());
//                      if (agent != null) {
//                             logger.info("agent name" + agent.getUsername());
//                             m1.setRemarks(agent.getUsername());
//                      } else {
//                             m1.setRemarks("");
//                      }
//               } else {
//                      m1.setRemarks("");
//               }
//               MID m = m1.getMid();
//               if (m != null) {
//                      // if(m.getMid()==null && m.getMid().isEmpty()){
//                      if (m.getMid() == null) {
//                             // logger.info("no mid");
//                             m.setMid("-");
//
//                      } else {
//
//                             m.setMid(m.getMid());
//                      }
//                      if (m.getMotoMid() != null) {
//                             m.setMotoMid(m.getMotoMid());
//                             // logger.info("moto mid "+m.getMotoMid());
//                      } else {
//                             m.setMotoMid("-");
//                      }
//                      if (m.getEzywayMid() != null) {
//                             m.setEzywayMid(m.getEzywayMid());
//
//                      } else {
//                             m.setEzywayMid("-");
//                      }
//                      if (m.getEzyrecMid() != null) {
//                             m.setEzyrecMid(m.getEzyrecMid());
//
//                      } else {
//                             m.setEzyrecMid("-");
//                      }
//
//                      if (m.getUmMid() != null) {
//                             m.setUmMid(m.getUmMid());
//
//                      } else {
//                             m.setUmMid("-");
//                      }
//
//                      if (m.getUmMotoMid() != null) {
//                             m.setUmMotoMid(m.getUmMotoMid());
//
//                      } else {
//                             m.setUmMotoMid("-");
//                      }
//
//                      if (m.getUmEzywayMid() != null) {
//                             m.setUmEzywayMid(m.getUmEzywayMid());
//
//                      } else {
//                             m.setUmEzywayMid("-");
//                      }
//                      
//                      
//                      if (m.getUmEzyrecMid() != null) {
//                             m.setUmEzyrecMid(m.getUmEzyrecMid());
//
//                      } else {
//                             m.setUmEzyrecMid("-");
//                      }
//
//                      m1.setMid(m);
//                      // logger.info(" mid: "+m1.getMid().getMid()+" motomid:
//                      // "+m1.getMid().getMotoMid());//+" ezypassmid: "+m1.getMid().getEzypassMid());
//               }
//
//               // logger.info(" mid: "+m1.getMid().getMid()+" motomid:
//               // "+m1.getMid().getMotoMid());//+" ezypassmid: "+m1.getMid().getEzypassMid());
//
//               if (m1.getRole().toString().equals("BANK_MERCHANT")) {
//                      if (m1.getPreAuth() != null && m1.getPreAuth().equals("Yes")) {
//                             m1.setPreAuth("Yes");
//                      } else {
//                             m1.setPreAuth("No");
//                      }
//                      // logger.info("MobileUser : " + m1.getId());
//                      m1.setAutoSettled(mobileUser.loadMobileUserByFkBoost(m1.getId()));
//                      m1.setFaxNo(mobileUser.loadMobileUserByFkMoto(m1.getId()));
//               } else {
//                      m1.setAutoSettled("No");
//                      m1.setFaxNo("No");
//                      m1.setPreAuth("No");
//               }
//
//        }

		// logger.info("check mobile user to date:" + date1);
		// System.out.println("export test:" + export);

		String UmEzywayMid = null;
		String boostMid = null;
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		Long merchantid = currentMerchant.getId();
		merchantService.listsubMerchant(paginationBean, date, date1, merchantid);

		List<Merchant> list = paginationBean.getItemList();

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
		}

		if (currentMerchant.getMid().getBoostMid() != null) {
			boostMid = currentMerchant.getMid().getBoostMid();
		}

		model.addAttribute("UmEzywayMid", UmEzywayMid);

		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("boostMid", boostMid);

		String userName = principal.getName();

		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else if (userName.equalsIgnoreCase("FINANCE@DLOCAL.COM")
				|| userName.equalsIgnoreCase("daria.ar@monetix.pro")) {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}
		if (!(export.equals("PDF"))) {
			logger.info("here excel");

			return new ModelAndView("txnsubMerchantExcel", "txnList", list);

		} else {
			logger.info("here pdf");
			return new ModelAndView("txnMerchantPdf", "txnList", list);

		}
	}

//edit sub merchant profile start added on 18-10-2021

	@RequestMapping(value = { "/edit" }, method = RequestMethod.POST)
	public String displayEditMerchantUser(final HttpServletRequest request, final Model model,
			@RequestParam("id") long id, final java.security.Principal principal) {

		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/edit/submerchantEditDetails",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		Merchant merchant = merchantService.loadMerchantbyid(id);

		logger.info("Details Merchantuser :" + merchant.getId() + ":" + principal.getName());

		String Submid = merchant.getMid().getSubMerchantMID();

		logger.info("current Merchant Sub Merchant Mid = " + Submid);

		Date activatedate = merchant.getActivateDate();

		logger.info(" activatedate = " + merchant.getActivateDate());

		Long salutation = merchant.getId();

		logger.info(" Current Merchant ID = " + salutation);

		String ADate = new SimpleDateFormat("dd-MMM-yyy").format(activatedate);

		logger.info(" ADate = " + ADate);

		String err = (String) request.getSession(true).getAttribute("editErSession1");

		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {

				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("editErSession1");
			}
		}

		String UmEzywayMid = null;
		String boostMid = null;

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
		}

		if (currentMerchant.getMid().getBoostMid() != null) {
			boostMid = currentMerchant.getMid().getBoostMid();
		}

		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else if (userName.equalsIgnoreCase("FINANCE@DLOCAL.COM")
				|| userName.equalsIgnoreCase("daria.ar@monetix.pro")) {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}
		model.addAttribute("boostMid", boostMid);
		model.addAttribute("UmEzywayMid", UmEzywayMid);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("Submid", Submid);
		model.addAttribute("ADate", ADate);
		model.addAttribute("salutation", salutation);
		model.addAttribute("natureOfBusinessList", AddMerchantOptionList.natureOfBusinessList());
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/editsubmerchantuser" }, method = RequestMethod.POST)
	public String displayEditMobileUserSes(final Model model, final HttpServletRequest request,
			@RequestParam("id") long id, @RequestParam("activationDate") final String activationDate,

			@RequestParam("businessName") final String businessName, @RequestParam("email") final String email,
			@RequestParam("city") final String city, @RequestParam("state") final String state,
			@RequestParam("mid") final String mid, @RequestParam("salutation") final String salutation1,
			@RequestParam("cpName") final String cpName, @RequestParam("cpNo") final String cpNo,
			@RequestParam("TradingName") final String TradingName, @RequestParam("website") final String website,
			@RequestParam("businessRegNo") final String businessRegNo,
			@RequestParam("businessRegName") final String businessRegName,
			@RequestParam("businessType") final String businessType,
			@RequestParam("businessAddress") final String businessAddress,
			@RequestParam("businessNature") final String businessNature,
			@RequestParam("businessPostCode") final String businessPostCode,
			@RequestParam("businessCountry") final String businessCountry,
			@RequestParam("ownerSalutation") final String ownerSalutation,
			@RequestParam("ownerName") final String ownerName, @RequestParam("ownerContact") final String ownerContact,
			@RequestParam("ownerPassport") final String ownerPassport, @RequestParam("bankName") final String bankName,
			@RequestParam("accountNo") final String accountNo, final java.security.Principal principal) {

		logger.info("/editSubMerchant user " + id);
		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/edit/submerchantEditDetails",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		String Submid = mid;
		logger.info("Sub Merchant MID = " + mid);

		String Adate = activationDate;
		logger.info(" activatedate = " + activationDate);

		Long salutation = id;
		logger.info(" Current Merchant ID = " + salutation);

		Merchant merchant = new Merchant();

		logger.info("businessName = " + businessName);
		logger.info("email = " + email);
		logger.info("city = " + city);
		logger.info("state = " + state);
		logger.info("mid = " + mid);
		logger.info("salutation = " + salutation1);
		logger.info("contact person Name = " + cpName);
		logger.info("contact person No = " + cpNo);
		logger.info("TradingName = " + TradingName);
		logger.info("website = " + website);
		logger.info("businessRegNo = " + businessRegNo);
		logger.info("businessRegName = " + businessRegName);
		logger.info("businessType = " + businessType);
		logger.info("businessAddress = " + businessAddress);
		logger.info("businessNature = " + businessNature);
		logger.info("businessPostCode = " + businessPostCode);
		logger.info("businessCountry = " + businessCountry);
		logger.info("ownerSalutation = " + ownerSalutation);
		logger.info("ownerName = " + ownerName);
		logger.info("ownerContact = " + ownerContact);
		logger.info("ownerPassport = " + ownerPassport);
		logger.info("bankName = " + bankName);
		logger.info("accountNo = " + accountNo);

		merchant.setBusinessName(businessName);
		merchant.setEmail(email);
		merchant.setCity(city);
		merchant.setState(state);
		merchant.setSalutation(salutation1);
		merchant.setContactPersonName(cpName);
		merchant.setContactPersonPhoneNo(cpNo);
		merchant.setTradingName(TradingName);
		merchant.setWebsite(website);
		merchant.setBusinessRegistrationNumber(businessRegNo);
		merchant.setBusinessShortName(businessRegName);
		merchant.setBusinessType(businessType);
		merchant.setBusinessAddress2(businessAddress);
		merchant.setNatureOfBusiness(businessNature);
		merchant.setPostcode(businessPostCode);
		merchant.setCountry(businessCountry);
		merchant.setOwnerSalutation(ownerSalutation);
		merchant.setOwnerName(ownerName);
		merchant.setOwnerContactNo(ownerContact);
		merchant.setOwnerPassportNo(ownerPassport);
		merchant.setBankName(bankName);
		merchant.setBankAcc(accountNo);

		BaseDataImpl baseData = new BaseDataImpl();
		Merchant m = baseData.vaildated(merchant);

		if (m != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession1", "yes");

			return "redirect:/admin/editsubmerchantuser/1";
		}

		String UmEzywayMid = null;
		String boostMid = null;
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
		}

		if (currentMerchant.getMid().getBoostMid() != null) {
			boostMid = currentMerchant.getMid().getBoostMid();
		}

		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else if (userName.equalsIgnoreCase("FINANCE@DLOCAL.COM")
				|| userName.equalsIgnoreCase("daria.ar@monetix.pro")) {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}

		model.addAttribute("UmEzywayMid", UmEzywayMid);
		model.addAttribute("boostMid", boostMid);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("salutation", salutation);
		model.addAttribute("Submid", Submid);
		model.addAttribute("Adate", Adate);

		request.getSession(true).setAttribute("editsubmerchantSession", merchant);

		request.getSession(true).setAttribute("Session", Submid);

		request.getSession(true).setAttribute("Session1", Adate);

		request.getSession(true).setAttribute("Session2", salutation);

		return "redirect:/admin/editSubMerchantUserReviewandConfirm";

	}

	@RequestMapping(value = { "/editSubMerchantUserReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditMerchantUserConfirm(final Model model, final HttpServletRequest request,
			final java.security.Principal principal) {

		logger.info("editSubMerchantUserReviewandConfirm ");

		Merchant merchant = (Merchant) request.getSession(true).getAttribute("editsubmerchantSession");

		String Submid = (String) request.getSession(true).getAttribute("Session");
		String Adate = (String) request.getSession(true).getAttribute("Session1");
		Long salutation = (Long) request.getSession(true).getAttribute("Session2");

		if (merchant == null) {
			return "redirect:" + URL_BASE + "/editsubmerchantuser/1";
		}

		PageBean pageBean = new PageBean("Sub Merchant edit Details",
				"merchantweb/edit/SubMerchantUserReviewandConfirm", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		String UmEzywayMid = null;
		String boostMid = null;
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
		}

		if (currentMerchant.getMid().getBoostMid() != null) {
			boostMid = currentMerchant.getMid().getBoostMid();
		}

		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else if (userName.equalsIgnoreCase("FINANCE@DLOCAL.COM")
				|| userName.equalsIgnoreCase("daria.ar@monetix.pro")) {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}

		model.addAttribute("UmEzywayMid", UmEzywayMid);
		model.addAttribute("boostMid", boostMid);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("Submid", Submid);
		model.addAttribute("Adate", Adate);
		model.addAttribute("salutation", salutation);

		request.getSession(true).removeAttribute("Session");
		request.getSession(true).removeAttribute("Session1");
		request.getSession(true).removeAttribute("Session2");
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/editSubMerchantUserReviewandConfirmPost" }, method = RequestMethod.POST)
	public String confirmEditMerchantUser(final Model model, final java.security.Principal principal,
			final HttpServletRequest request, @RequestParam("id") long id,
			@RequestParam("businessName") final String businessName, @RequestParam("email") String email,
			@RequestParam("city") String city, @RequestParam("state") String state,
			@RequestParam("mid") final String mid, @RequestParam("salutation") final String salutation1,
			@RequestParam("cpName") final String cpName, @RequestParam("cpNo") final String cpNo,
			@RequestParam("TradingName") final String TradingName, @RequestParam("website") final String website,
			@RequestParam("businessRegNo") final String businessRegNo,
			@RequestParam("businessRegName") final String businessRegName,
			@RequestParam("businessType") final String businessType,
			@RequestParam("businessAddress") final String businessAddress,
			@RequestParam("businessNature") final String businessNature,
			@RequestParam("businessPostCode") final String businessPostCode,
			@RequestParam("businessCountry") final String businessCountry,
			@RequestParam("ownerSalutation") final String ownerSalutation,
			@RequestParam("ownerName") final String ownerName, @RequestParam("ownerContact") final String ownerContact,
			@RequestParam("ownerPassport") final String ownerPassport, @RequestParam("bankName") final String bankName,
			@RequestParam("accountNo") final String accountNo) {
//
//		logger.info("editSubMerchantUserReviewandConfirmPost");
//
//		Merchant merchant = merchantService.loadMerchantbyid(id);
//
//		logger.info("id" + id);
//
//		logger.info("businessName = " + businessName);
//		logger.info("email = " + email);
//		logger.info("city = " + city);
//		logger.info("state = " + state);
//		logger.info("mid = " + mid);
//		logger.info("salutation = " + salutation1);
//		logger.info("contact person Name = " + cpName);
//		logger.info("contact person No = " + cpNo);
//		logger.info("TradingName = " + TradingName);
//		logger.info("website = " + website);
//		logger.info("businessRegNo = " + businessRegNo);
//		logger.info("businessRegName = " + businessRegName);
//		logger.info("businessType = " + businessType);
//		logger.info("businessAddress = " + businessAddress);
//		logger.info("businessNature = " + businessNature);
//		logger.info("businessPostCode = " + businessPostCode);
//		logger.info("businessCountry = " + businessCountry);
//		logger.info("ownerSalutation = " + ownerSalutation);
//		logger.info("ownerName = " + ownerName);
//		logger.info("ownerContact = " + ownerContact);
//		logger.info("ownerPassport = " + ownerPassport);
//		logger.info("bankName = " + bankName);
//		logger.info("accountNo = " + accountNo);
//
//		merchant.setBusinessName(businessName);
//		merchant.setEmail(email);
//		merchant.setCity(city);
//		merchant.setState(state);
//		merchant.setSalutation(salutation1);
//		merchant.setContactPersonName(cpName);
//		merchant.setContactPersonPhoneNo(cpNo);
//		merchant.setTradingName(TradingName);
//		merchant.setWebsite(website);
//		merchant.setBusinessRegistrationNumber(businessRegNo);
//		merchant.setBusinessShortName(businessRegName);
//		merchant.setBusinessType(businessType);
//		merchant.setBusinessAddress2(businessAddress);
//		merchant.setNatureOfBusiness(businessNature);
//		merchant.setPostcode(businessPostCode);
//		merchant.setCountry(businessCountry);
//		merchant.setOwnerSalutation(ownerSalutation);
//		merchant.setOwnerName(ownerName);
//		merchant.setOwnerContactNo(ownerContact);
//		merchant.setOwnerPassportNo(ownerPassport);
//		merchant.setBankName(bankName);
//		merchant.setBankAcc(accountNo);
//
//		// merchantService.updateMerchant(merchant);
//
//		merchantService.updateMerchantByNativeQuery(merchant);
//
//		PageBean pageBean = new PageBean(" Merchant  Details", "merchantweb/edit/submerchantUserAlldoneSuccessful",
//				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//
//		Long salutation = id;
//
//		BaseDataImpl baseData = new BaseDataImpl();
//
//		Merchant m = baseData.vaildated(merchant);
//
//		if (m != null) {
//			logger.info("Contains HTML tags");
//			request.getSession(true).setAttribute("editErSession1", "yes");
//
//			return "redirect:/admin/editsubmerchantuser/1";
//		}
//
//		request.getSession(true).removeAttribute("editsubmerchantSession");
//
//		String UmEzywayMid = null;
//		String boostMid = null;
//
//		HttpSession session = request.getSession();
//		String myName = (String) session.getAttribute("userName");
//		logger.info("All transaction currently logged by: " + myName);
//		Merchant currentMerchant = merchantService.loadMerchant(myName);
//
//		if (currentMerchant.getMid().getUmEzywayMid() != null) {
//			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
//		}
//
//		if (currentMerchant.getMid().getBoostMid() != null) {
//			boostMid = currentMerchant.getMid().getBoostMid();
//		}
//
//		String userName = principal.getName();
//		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
//			userName = "FINANCE@DLOCAL.COM";
//
//			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");
//
//			model.addAttribute("localAdmin", "Yes");
//		}  else if(userName.equalsIgnoreCase("FINANCE@DLOCAL.COM") || userName.equalsIgnoreCase("daria.ar@monetix.pro")) {
//
//			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
//			model.addAttribute("localAdmin", "No");
//
//		}
//
//		model.addAttribute("UmEzywayMid", UmEzywayMid);
//		model.addAttribute("boostMid", boostMid);
//
//		model.addAttribute("pageBean", pageBean);
//		model.addAttribute("merchant", merchant);
//		model.addAttribute("salutation", salutation);
//
//		return TEMPLATE_MERCHANT;
		logger.info("editSubMerchantUserReviewandConfirmPost");

		Merchant merchant = merchantService.loadMerchantbyid(id);

		logger.info("id" + id);
		logger.info("businessName = " + businessName);
		logger.info("email = " + email);
		logger.info("city = " + city);
		logger.info("state = " + state);
		logger.info("mid = " + mid);
		logger.info("salutation = " + salutation1);
		logger.info("contact person Name = " + cpName);
		logger.info("contact person No = " + cpNo);
		logger.info("TradingName = " + TradingName);
		logger.info("website = " + website);
		logger.info("businessRegNo = " + businessRegNo);
		logger.info("businessRegName = " + businessRegName);
		logger.info("businessType = " + businessType);
		logger.info("businessAddress = " + businessAddress);
		logger.info("businessNature = " + businessNature);
		logger.info("businessPostCode = " + businessPostCode);
		logger.info("businessCountry = " + businessCountry);
		logger.info("ownerSalutation = " + ownerSalutation);
		logger.info("ownerName = " + ownerName);
		logger.info("ownerContact = " + ownerContact);
		logger.info("ownerPassport = " + ownerPassport);
		logger.info("bankName = " + bankName);
		logger.info("accountNo = " + accountNo);

		merchant.setBusinessName(businessName);
		merchant.setEmail(email);
		merchant.setCity(city);
		merchant.setState(state);
		merchant.setSalutation(salutation1);
		merchant.setContactPersonName(cpName);
		merchant.setContactPersonPhoneNo(cpNo);
		merchant.setTradingName(TradingName);
		merchant.setWebsite(website);
		merchant.setBusinessRegistrationNumber(businessRegNo);
		merchant.setBusinessShortName(businessRegName);
		merchant.setBusinessType(businessType);
		merchant.setBusinessAddress2(businessAddress);
		merchant.setNatureOfBusiness(businessNature);
		merchant.setPostcode(businessPostCode);
		merchant.setCountry(businessCountry);
		merchant.setOwnerSalutation(ownerSalutation);
		merchant.setOwnerName(ownerName);
		merchant.setOwnerContactNo(ownerContact);
		merchant.setOwnerPassportNo(ownerPassport);
		merchant.setBankName(bankName);
		merchant.setBankAcc(accountNo);

		// merchantService.updateMerchant(merchant);

		// sakthi added
		// Update Submerchant api Starting
		logger.info("Update Submerchant Api Start Here");
		SubmerchantApi updateSubmerchant = new SubmerchantApi();
		updateSubmerchant.updateMerchantByNativeQuery(merchant);

		// merchantService.updateMerchantByNativeQuery(merchant);

		PageBean pageBean = new PageBean(" Merchant  Details", "merchantweb/edit/submerchantUserAlldoneSuccessful",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		Long salutation = id;

		BaseDataImpl baseData = new BaseDataImpl();

		Merchant m = baseData.vaildated(merchant);

		if (m != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession1", "yes");

			return "redirect:/admin/editsubmerchantuser/1";
		}

		request.getSession(true).removeAttribute("editsubmerchantSession");

		String UmEzywayMid = null;
		String boostMid = null;

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("All transaction currently logged by: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMid().getUmEzywayMid() != null) {
			UmEzywayMid = currentMerchant.getMid().getUmEzywayMid();
		}

		if (currentMerchant.getMid().getBoostMid() != null) {
			boostMid = currentMerchant.getMid().getBoostMid();
		}

		String userName = principal.getName();
		if (userName.equalsIgnoreCase("01FINANCE@DLOCAL.COM")) {
			userName = "FINANCE@DLOCAL.COM";

			logger.info("::::::::::: Dlocal local Admin 'Yes' ::::::::::");

			model.addAttribute("localAdmin", "Yes");
		} else if (userName.equalsIgnoreCase("FINANCE@DLOCAL.COM")
				|| userName.equalsIgnoreCase("daria.ar@monetix.pro")) {

			logger.info("::::::::::: Dlocal local Admin 'No' ::::::::::");
			model.addAttribute("localAdmin", "No");

		}

		model.addAttribute("UmEzywayMid", UmEzywayMid);
		model.addAttribute("boostMid", boostMid);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("salutation", salutation);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// edit sub merchant profile end added on 18-10-2021

	// SUB MERCHANT SUMMARY BY ADMIN

	// rksubmerchant
	@RequestMapping(value = { "/submerchantSum1" }, method = RequestMethod.GET)
	public String displayAddSubMerchantsUMadmin(@ModelAttribute("merchant") final RegAddMerchant regAddMerchant,
			final HttpServletRequest request, Model model, final java.security.Principal principal) {

		logger.info("About to Add Sub merchant details");
		logger.info(" Admin login person Name:" + principal.getName());

		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/submerchantListadmin", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		// paginationBean.setCurrPage(currPage);

		Long id = (long) 0000;

		/* merchantService.listsubMerchantdefault(paginationBean, id, null); */

		merchantService.listsubMerchantdefault(paginationBean, id, null);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	// rksubmerchantsearch
	@RequestMapping(value = { "/submersearch1" }, method = RequestMethod.GET)
	public String displaySearchMerchantadmin(final Model model, final HttpServletRequest request,
			final java.security.Principal principal,

			@RequestParam(required = false, defaultValue = "1") final int currPage, @RequestParam final String date,
			@RequestParam final String date1) {
		logger.info("about to search Merchant based on search String:: " + date);

		logger.info("about to search Merchant based on search String:: " + date1);
		// String type=null;

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/submerchantListadmin", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();

		paginationBean.setCurrPage(currPage);
		Long id = (long) 0000;
		merchantService.listsubMerchant(paginationBean, fromDate, toDate, id);

		logger.info("userrolle: " + paginationBean.getItemList());
		model.addAttribute("pageBean", pageBean);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	// rksubmerchantexport
	@RequestMapping(value = "/submerexport1", method = RequestMethod.GET)
	public ModelAndView getExceladmin(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false) String export, final Model model, final HttpServletRequest request,
			final java.security.Principal principal) {
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
		Long id = (long) 0000;
		merchantService.listsubMerchant(paginationBean, date, date1, id);
		// List<MobileUser>
		// mobileuser=mobileUser.loadMobileUserByFk(merchant.getId());

		// List<Merchant> list
		// =merchantService.merchantSummaryExport(date,date1);

		List<Merchant> list = paginationBean.getItemList();
//        logger.info("check mobile user from date:" + date);
//         logger.info("Item count: " + paginationBean.getItemList().size());
//        for (Merchant m1 : list) {
//
//               if (m1.getAgID() != null) {
//                      Agent agent = agentService.loadAgentByIdPk(m1.getAgID().longValue());
//                      if (agent != null) {
//                             logger.info("agent name" + agent.getUsername());
//                             m1.setRemarks(agent.getUsername());
//                      } else {
//                             m1.setRemarks("");
//                      }
//               } else {
//                      m1.setRemarks("");
//               }
//               MID m = m1.getMid();
//               if (m != null) {
//                      // if(m.getMid()==null && m.getMid().isEmpty()){
//                      if (m.getMid() == null) {
//                             // logger.info("no mid");
//                             m.setMid("-");
//
//                      } else {
//
//                             m.setMid(m.getMid());
//                      }
//                      if (m.getMotoMid() != null) {
//                             m.setMotoMid(m.getMotoMid());
//                             // logger.info("moto mid "+m.getMotoMid());
//                      } else {
//                             m.setMotoMid("-");
//                      }
//                      if (m.getEzywayMid() != null) {
//                             m.setEzywayMid(m.getEzywayMid());
//
//                      } else {
//                             m.setEzywayMid("-");
//                      }
//                      if (m.getEzyrecMid() != null) {
//                             m.setEzyrecMid(m.getEzyrecMid());
//
//                      } else {
//                             m.setEzyrecMid("-");
//                      }
//
//                      if (m.getUmMid() != null) {
//                             m.setUmMid(m.getUmMid());
//
//                      } else {
//                             m.setUmMid("-");
//                      }
//
//                      if (m.getUmMotoMid() != null) {
//                             m.setUmMotoMid(m.getUmMotoMid());
//
//                      } else {
//                             m.setUmMotoMid("-");
//                      }
//
//                      if (m.getUmEzywayMid() != null) {
//                             m.setUmEzywayMid(m.getUmEzywayMid());
//
//                      } else {
//                             m.setUmEzywayMid("-");
//                      }
//                      
//                      
//                      if (m.getUmEzyrecMid() != null) {
//                             m.setUmEzyrecMid(m.getUmEzyrecMid());
//
//                      } else {
//                             m.setUmEzyrecMid("-");
//                      }
//
//                      m1.setMid(m);
//                      // logger.info(" mid: "+m1.getMid().getMid()+" motomid:
//                      // "+m1.getMid().getMotoMid());//+" ezypassmid: "+m1.getMid().getEzypassMid());
//               }
//
//               // logger.info(" mid: "+m1.getMid().getMid()+" motomid:
//               // "+m1.getMid().getMotoMid());//+" ezypassmid: "+m1.getMid().getEzypassMid());
//
//               if (m1.getRole().toString().equals("BANK_MERCHANT")) {
//                      if (m1.getPreAuth() != null && m1.getPreAuth().equals("Yes")) {
//                             m1.setPreAuth("Yes");
//                      } else {
//                             m1.setPreAuth("No");
//                      }
//                      // logger.info("MobileUser : " + m1.getId());
//                      m1.setAutoSettled(mobileUser.loadMobileUserByFkBoost(m1.getId()));
//                      m1.setFaxNo(mobileUser.loadMobileUserByFkMoto(m1.getId()));
//               } else {
//                      m1.setAutoSettled("No");
//                      m1.setFaxNo("No");
//                      m1.setPreAuth("No");
//               }
//
//        }

		// logger.info("check mobile user to date:" + date1);
		// System.out.println("export test:" + export);

		if (!(export.equals("PDF"))) {
			logger.info("here excel");

			return new ModelAndView("txnsubMerchantExcel", "txnList", list);

		} else {
			logger.info("here pdf");
			return new ModelAndView("txnMerchantPdf", "txnList", list);

		}
	}

	@RequestMapping("/emailCheck")
	@ResponseBody
	public String check(@RequestParam String email, HttpServletRequest request, HttpServletResponse response,
			Model model) {

		logger.info("About to check Email : " + email);
		String responseDataOfficeEmail = null;
		String result = null;
		Merchant offEmail = merchantService.loadMerchantbyEmail(email);
		responseDataOfficeEmail = offEmail != null ? "  Email already exist" : null;

		if (responseDataOfficeEmail != null) {
			result = "Exist";
		} else {
			result = "NotExist";
		}
		logger.info("Check Email Result : " + result);
		return result;
	}

//	@RequestMapping(value = { "/adminsubmerchant" }, method = RequestMethod.GET)
//	public String displayAddSubMerchantsSummery(@ModelAttribute("merchant") final RegAddMerchant regAddMerchant,
//			final HttpServletRequest request, Model model, final java.security.Principal principal,
//			@RequestParam(required = false, defaultValue = "1") final int currPage) {
// 
//		logger.info("About to Add Sub merchant details");
//		logger.info(" Admin login person Name:" + principal.getName());
// 
//		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/adminsubmerchantList", Module.MERCHANT,
//				"merchant/sideMenuMerchant");
// 
//		model.addAttribute("pageBean", pageBean);
//		logger.info("admin login person:" + principal.getName());
//		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
//		paginationBean.setCurrPage(currPage);
// 
//		merchantService.listadminsubMerchantdefault(paginationBean);
//		
//		
// 
//		model.addAttribute("paginationBean", paginationBean);
//		if (paginationBean.getItemList().size() == 0) {
//			model.addAttribute("responseData", "No Record found");
//		}
// 
//		return TEMPLATE_DEFAULT;
//	}

	// new karuppusamy

	@RequestMapping(value = { "/adminsubmerchant" }, method = RequestMethod.GET)
	public String displayAddSubMerchantsSummery(@ModelAttribute("merchant") final RegAddMerchant regAddMerchant,
			final HttpServletRequest request, Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		List<String> submerchantList = new ArrayList<>();
		logger.info("About to Add Sub merchant details");
		logger.info(" Admin login person Name:" + principal.getName());

		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/adminsubmerchantList", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		paginationBean.setCurrPage(currPage);

		merchantService.listadminsubMerchantdefault(paginationBean, submerchantList);
		logger.info("submerchantList are :" + submerchantList);
		model.addAttribute("DefaultQuerySubMer", submerchantList);

		request.getSession().setAttribute("submerchantList", submerchantList);

		model.addAttribute("paginationBean", paginationBean);
		if (paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Record found");
		}

		return TEMPLATE_DEFAULT;
	}

//	search btn triggering controller old
//	@RequestMapping(value = { "/adminsubmerchantsearch" }, method = RequestMethod.GET)
//	public String displaySearchsubMerchantadmin(final Model model, final HttpServletRequest request,
//			final java.security.Principal principal,
//			@RequestParam(required = false, defaultValue = "1") final int currPage,
//			@RequestParam("type") final String type) {
//		logger.info(" search Type :: " + type);
//
//		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/adminsubmerchantList", Module.MERCHANT,
//				"merchant/sideMenuMerchant");
//
//		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
//
//		paginationBean.setCurrPage(currPage);
//
//		merchantService.listsubMerchant(paginationBean, type);
//
//		logger.info("userrolle: " + paginationBean.getItemList());
//		model.addAttribute("pageBean", pageBean);
//		model.addAttribute("type", type);
//		model.addAttribute("paginationBean", paginationBean);
//		if (paginationBean.getItemList().size() == 0) {
//			model.addAttribute("responseData", "No Record found");
//		}
//		return TEMPLATE_DEFAULT;
//
//	}

//	search btn triggering controller new

	@RequestMapping(value = { "/adminsubmerchantsearch" }, method = RequestMethod.GET)
	public String displaySearchsubMerchantadmin(final Model model, final HttpServletRequest request,
			final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam("type") final String type) {
		logger.info(" search Type :: " + type);

		List<String> submerchantList = (List<String>) request.getSession().getAttribute("submerchantList");

		logger.info("retreived merchant list are: " + submerchantList);
		model.addAttribute("DefaultQuerySubMer", submerchantList);
		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/adminsubmerchantList", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();

		paginationBean.setCurrPage(currPage);

		merchantService.listsubMerchant(paginationBean, type);

		logger.info("userrolle: " + paginationBean.getItemList());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("type", type);
		model.addAttribute("paginationBean", paginationBean);
		if (paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Record found");
		}
		return TEMPLATE_DEFAULT;

	}

	// eye icon on click controller
//	@RequestMapping(value = { "/submerchantSummaryDetails" }, method = RequestMethod.GET)
//	public String displaysubMerchantadminDetails(final Model model, final HttpServletRequest request,
//			final java.security.Principal principal,
//			@RequestParam(required = false, defaultValue = "1") final int currPage,
//			@RequestParam("bussinessname") final String bussinessname, @RequestParam("indexId") final String indexId) {
//		logger.info(" search bussinessname :: " + bussinessname);
//		logger.info(" search indexId :: " + indexId);
//
//		PageBean pageBean = new PageBean("Merchant Detail", "transaction/refund/submerchantSummary", Module.MERCHANT,
//				"merchant/sideMenuMerchant");
//
//		Date currentDate = new Date();
//		String fromdate = null;
//		String todate = null;
//
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTime(currentDate);
//		calendar.add(Calendar.DAY_OF_MONTH, -1);
//		Date previousDate = calendar.getTime();
//
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		fromdate = dateFormat.format(previousDate);
//		todate = dateFormat.format(currentDate);
//
//		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
//
//		paginationBean.setCurrPage(currPage);
//
////		Merchant findMerchant= merchantService.loadMerchant(bussinessname);
//
//		Merchant findMerchant = merchantService.loadMerchantbyBussinessName(bussinessname);
//
//		logger.info(" findMerchant :: " + findMerchant);
//		logger.info(" merchant Id :: " + findMerchant.getId());
//		List<MobileUser> mobileuser = null;
//
//		mobileuser = merchantService.loadMobileUserByFkTid(findMerchant.getId());
//
//		merchantService.subMerchantListByAdmin(paginationBean, fromdate, todate, findMerchant, null, mobileuser);
//
////		merchantService.listsubMerchant(paginationBean, type);
//
//		logger.info("userrolle: " + paginationBean.getItemList());
//		model.addAttribute("pageBean", pageBean);
//		model.addAttribute("bussinessname", bussinessname);
//
//		model.addAttribute("paginationBean", paginationBean);
//
//		return TEMPLATE_DEFAULT;
//
//	}
//
//	
//	

	@RequestMapping(value = { "/submerchantSummaryDetails" }, method = RequestMethod.GET)
	public String displaysubMerchantadminDetails(final Model model, final HttpServletRequest request,
			final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam("bussinessname") final String bussinessname, @RequestParam("indexId") final String indexId) {

		logger.info(" search bussinessname :: " + bussinessname);
		logger.info(" search indexId :: " + indexId);

		PageBean pageBean = new PageBean("Merchant Detail", "transaction/refund/submerchantSummary", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		Date currentDate = new Date();
		String fromdate = null;
		String todate = null;

		java.time.LocalDate todayDate = java.time.LocalDate.now();
		java.time.LocalDate prevDay = java.time.LocalDate.now().minusDays(1);

//     Calendar calendar = Calendar.getInstance();
//     calendar.setTime(currentDate);
//     calendar.add(Calendar.DAY_OF_MONTH, -1);
//     Date previousDate = calendar.getTime();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		fromdate = formatter.format(prevDay);
		todate = formatter.format(todayDate);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

//     Merchant findMerchant= merchantService.loadMerchant(bussinessname);

		Merchant findMerchant = merchantService.loadMerchantbyBussinessName(bussinessname);

		logger.info(" findMerchant :: " + findMerchant);
		logger.info(" merchant Id :: " + findMerchant.getId());
		List<MobileUser> mobileuser = null;

		mobileuser = merchantService.loadMobileUserByFkTid(findMerchant.getId());

		merchantService.subMerchantListByAdmin(paginationBean, fromdate, todate, findMerchant, null, mobileuser);

//     merchantService.listsubMerchant(paginationBean, type);

		logger.info("userrolle: " + paginationBean.getItemList());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("bussinessname", bussinessname);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/searchsubmerchantSummaryDetails" }, method = RequestMethod.GET)
	public String displaySearchsubMerchantadminDetails(final Model model, final HttpServletRequest request,
			final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam("fromDate") final String fromDate, @RequestParam("toDate") final String toDate,
			@RequestParam("bussinessname") final String bussinessname) {
		logger.info(" search bussinessname :: " + bussinessname);

		logger.info(" search fromDate :: " + fromDate);
		logger.info(" search toDate :: " + toDate);

		PageBean pageBean = new PageBean("Merchant Detail", "transaction/refund/submerchantSummary", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		String fromdate = null;
		String todate = null;
		if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())) {
			try {
				fromdate = new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
				todate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
//				logger.info("inside search controler " + dat + " " + dat1);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

//		Merchant findMerchant= merchantService.loadMerchant(bussinessname);
//		MobileUser findmerchantFk=merchantService.loadMerchantFk(findMerchant);

		Merchant findMerchant = merchantService.loadMerchantbyBussinessName(bussinessname);

		logger.info(" findMerchant :: " + findMerchant);
		logger.info(" merchant Id :: " + findMerchant.getId());

		List<MobileUser> mobileuser = null;

		mobileuser = merchantService.loadMobileUserByFkTid(findMerchant.getId());

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		merchantService.subMerchantListByAdmin(paginationBean, fromdate, todate, findMerchant, null, mobileuser);

//		merchantService.listsubMerchant(paginationBean, type);

		logger.info("userrolle: " + paginationBean.getItemList());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("bussinessname", bussinessname);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/adminsubmerchantexport" }, method = RequestMethod.GET)
	public ModelAndView displayExportsubMerchantadmin(final Model model, final HttpServletRequest request,
			final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam("type") final String type, @RequestParam("export") final String export) {

		logger.info(" export Type :: " + type);
		logger.info(" export  :: " + export);

		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/adminsubmerchantList", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		paginationBean.setCurrPage(currPage);
		merchantService.exportsubMerchant(paginationBean, type);

		logger.info("userrolle: " + paginationBean.getItemList());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("type", type);
		model.addAttribute("paginationBean", paginationBean);

		List<Merchant> list1 = paginationBean.getItemList();
		return new ModelAndView("subMerchantExcel", "subMerchantList", list1);
	}

}
