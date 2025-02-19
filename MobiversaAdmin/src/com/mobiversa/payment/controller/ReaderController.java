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
import com.mobiversa.common.bo.Reader;
import com.mobiversa.common.bo.ReaderStatusHistory;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.ReaderService;

@Controller
@RequestMapping(value = ReaderController.URL_BASE)
public class ReaderController extends BaseController {
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private ReaderService readerService;

	public static final String URL_BASE = "/reader";

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		return "redirect:" + URL_BASE + "/list/1";
	}

	
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayReadersList(final Model model, @PathVariable final int currPage){
		logger.info("about to display  List of Terminals");
		PageBean pageBean=new PageBean("Reader","reader/readerList", Module.READER,"reader/sideMenuReader");
		model.addAttribute("pageBean", pageBean);
		//TerminalDetails terminalDtails=new TerminalDetails();
		PaginationBean<TerminalDetails> paginationBean=new PaginationBean<TerminalDetails>();
		paginationBean.setCurrPage(currPage);
		readerService.listReadersServiceByTerminalDetails(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		
		return TEMPLATE_DEFAULT;
	}
	
	@RequestMapping(value = { "/viewReaderList/{id}" }, method = RequestMethod.GET)
	public String displayMoblieUsersList(final Model model, @PathVariable final long id) {
		logger.info("about to list all  readers");
		PageBean pageBean = new PageBean("Reader list", "reader/viewReaderList", Module.READER, "reader/sideMenuReader");

		Merchant merchant = merchantService.loadMerchantByPk(id);

		PaginationBean<TerminalDetails> paginationBean = new PaginationBean<TerminalDetails>();
		//readerService.listReadersMerchant(paginationBean, merchant);

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/details/{id}" }, method = RequestMethod.GET)
	public String detailsMobileUser(final Model model, @PathVariable final String id) {
		logger.info("Request to display reader details based on ID: " + id);
		PageBean pageBean = new PageBean("Reader Detail", "reader/readerDetail", Module.READER, "reader/sideMenuReader");
		//Merchant merchant = merchantService.loadMerchantByPk(id);
		//Reader reader = readerService.loadReaderByPk(id);
		TerminalDetails terminalDetails=readerService.loadTerminalByDevice(id);
		model.addAttribute("pageBean", pageBean);
		//model.addAttribute("merchant", merchant);
		model.addAttribute("terminalDetails", terminalDetails);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String displaySearchReader(final Model model, @RequestParam final String serialNumber,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to search Merchant based on search String:: " + serialNumber);

		PageBean pageBean = new PageBean("Search Reader", "reader/readerSearch", Module.READER, "reader/sideMenuReader");

		PaginationBean<Reader> paginationBean = new PaginationBean<Reader>();

		paginationBean.setCurrPage(currPage);

		readerService.searchReader(serialNumber, paginationBean);

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/suspend/{id}" }, method = RequestMethod.GET)
	public String displaySuspendReader(final Model model, @PathVariable final Long id,
			@ModelAttribute("readerStatusHistory") final ReaderStatusHistory readerStatusHistory,
			@ModelAttribute("errorList") final ArrayList<String> errorList) {
		PageBean pageBean = new PageBean("Reader Detail", "reader/suspend/readerSuspend", Module.READER,
				"reader/sideMenuReader");
		pageBean.addJS("mobileuser/suspend/mobileUserSuspend.js");
		Merchant merchant = merchantService.loadMerchantByPk(id);
		Reader reader = readerService.loadReaderByPk(id);
		if (!CommonStatus.ACTIVE.equals(merchant.getStatus())) {
			// reader status isn't active, then how do we suspend
			// reader?
			return URL_BASE + "/detail/" + id;
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("reader", reader);
		model.addAttribute("now", new Date());
		model.addAttribute("readerStatusHistory", readerStatusHistory);
		model.addAttribute("errorList", errorList);

		/* logger.warn(" */
		/*
		 * model.addAttribute("reasonList", new String[] {
		 * "Fraud Case Investigation", "Others" });
		 */

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
	public String doSuspendReader(final Model model, @RequestParam final Long id, @RequestParam final String reason,
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
				ReaderStatusHistory readerStatusHistory = new ReaderStatusHistory();
				readerStatusHistory.setReason(reason);
				readerStatusHistory.setDescription(description);

				redirectAttributes.addFlashAttribute("readerStatusHistory", readerStatusHistory);
				redirectAttributes.addFlashAttribute("errorList", errorList);

				return "redirect:" + URL_BASE + "/suspend/" + id;
			}
			readerService.doSuspendReader(id, reason, description);
			return "redirect:" + URL_BASE + "/suspendDone?mode=success&id=" + id;
		} catch (Exception e) {
			// reporting purpose.
			logger.error("error suspending reader", e);
			return "redirect:" + URL_BASE + "/suspendDone?mode=fail&id=" + id + "&eventID=1";
		}
	}

	@RequestMapping(value = { "/suspendDone" }, method = RequestMethod.GET)
	public String displaySuspendReaderun(final Model model, @RequestParam final String mode,
			@RequestParam final long id, @RequestParam(required = false) final Long eventID) {
		PageBean pageBean = new PageBean("Reader Suspend Done", "reader/suspend/readerSuspendAlldoneSuccessful",
				Module.READER, "reader/sideMenuReader");
		boolean success = ("success".equals(mode));
		Reader reader = readerService.loadReaderByPk(id);
		ReaderStatusHistory readerStatusHistory = readerService.loadReaderStatusByPk(reader);
		model.addAttribute("readerStatusHistory", readerStatusHistory);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("reader", reader);
		model.addAttribute("success", success);
		model.addAttribute("eventID", eventID);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/unsuspend/{id}" }, method = RequestMethod.GET)
	public String displayUnSuspendReader(final Model model, @PathVariable final Long id,
			@ModelAttribute("readerStatusHistory") final ReaderStatusHistory readerStatusHistory,
			@ModelAttribute("errorList") final ArrayList<String> errorList) {
		PageBean pageBean = new PageBean("Reader Detail", "reader/suspend/readerUnSuspend", Module.READER,
				"reader/sideMenuReader");
		pageBean.addJS("mobileuser/suspend/mobileUserSuspend.js");

		Reader reader = readerService.loadReaderByPk(id);
		if (!CommonStatus.SUSPENDED.equals(reader.getStatus())) {
			// reader status isn't active, then how do we suspend
			// reader?
			return URL_BASE + "/detail/" + id;
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("now", new Date());
		model.addAttribute("reader", reader);
		model.addAttribute("errorList", errorList);

		// FIXME load reasonList from resourceBundle
		logger.warn("FIXME:: load reasonList from resourceBundle");
		/*
		 * model.addAttribute("reasonList", new String[] {
		 * "Fraud Case Investigation", "Others" });
		 */

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/unsuspend/dounSuspend" }, method = RequestMethod.POST)
	public String doUnSuspendReader(final Model model, @RequestParam final Long id, @RequestParam final String reason,
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
				ReaderStatusHistory readerStatusHistory = new ReaderStatusHistory();
				readerStatusHistory.setReason(reason);
				readerStatusHistory.setDescription(description);

				redirectAttributes.addFlashAttribute("readerStatusHistory", readerStatusHistory);
				redirectAttributes.addFlashAttribute("errorList", errorList);

				return "redirect:" + URL_BASE + "/unsuspend/" + id;
			}
			readerService.doUnSuspendReader(id, reason, description);
			return "redirect:" + URL_BASE + "/unsuspendDone?mode=success&id=" + id;
		} catch (Exception e) {
			// FIXME log event to db and return eventID to controller for
			// reporting purpose.
			logger.error("error suspending reader", e);
			return "redirect:" + URL_BASE + "/unsuspendDone?mode=fail&id=" + id + "&eventID=1";
		}
	}

	@RequestMapping(value = { "/unsuspendDone" }, method = RequestMethod.GET)
	public String displayUnSuspendReader(final Model model, @RequestParam final String mode,
			@RequestParam final long id, @RequestParam(required = false) final Long eventID) {
		PageBean pageBean = new PageBean("Reader Un-Suspend Done", "reader/suspend/readerUnSuspendAlldoneSuccessful",
				Module.READER, "reader/sideMenuReader");
		boolean success = ("success".equals(mode));
		Reader reader = readerService.loadReaderByPk(id);
		ReaderStatusHistory readerStatusHistory = readerService.loadReaderStatusByPk(reader);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("reader", reader);
		model.addAttribute("success", success);
		model.addAttribute("eventID", eventID);
		model.addAttribute("readerStatusHistory", readerStatusHistory);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/registerMerchantReader" }, method = RequestMethod.GET)
	public String addMerchantsMobileUser(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to add reader");
		PageBean pageBean = new PageBean("AddReader", "reader/register/registerMerchantReader", Module.READER,
				"reader/sideMenuReader");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		paginationBean.setCurrPage(currPage);
		merchantService.listMerchant(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/newReader/{idx}" }, method = RequestMethod.GET)
	public String registerNewReader(final Model model, @ModelAttribute("reader") final Reader reader,
			final HttpServletRequest request, @PathVariable final long idx) {
		PageBean pageBean = new PageBean("Register New Reader", "reader/register/registerNewReader", Module.READER,
				"reader/sideMenuReader");

		Merchant merchant = merchantService.loadMerchantByPk(idx);
		request.getSession(true).setAttribute("CURRENT_USER_MERCHANT", merchant);
		model.addAttribute("merchant", merchant);
		model.addAttribute("pageBean", pageBean);

		model.addAttribute("reader", reader);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/newReader/{idx}" }, method = RequestMethod.POST)
	public String addMobileUserSession(@ModelAttribute("reader") final Reader reader, final HttpServletRequest request,
			final Model model) {
		logger.info("about to add mobileuser session");
		PageBean pageBean = new PageBean("Register New Reader", "reader/register/registerNewReader", Module.READER,
				"reader/sideMenuReader");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("reader", reader);
		//PCI
		request.getSession(true).setAttribute("addReaderSession", reader);
		Merchant merchant = (Merchant) request.getSession(true).getAttribute("CURRENT_USER_MERCHANT");
		model.addAttribute("merchant", merchant);
		return "redirect:/reader/reviewandConfirm";
	}

	@RequestMapping(value = { "/reviewandConfirm" }, method = RequestMethod.GET)
	public String addMobileUserReviewandConfirm(final Model model, final HttpServletRequest request) {
		logger.info("about to add mobileuser review and confirm");

		Reader reader = (Reader) request.getSession(true).getAttribute("addReaderSession");
		Merchant merchant = (Merchant) request.getSession(true).getAttribute("CURRENT_USER_MERCHANT");

		if (reader == null) {
			// redirect user to the add page if there's no mobileUser in
			// session.
			return "redirect:/reader/registerMerchantReader";
		}
		PageBean pageBean = new PageBean("Reader add Details", "reader/register/registrationDetails", Module.READER,
				"reader/sideMenuReader");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("reader", reader);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/readerDetailsConfirm" }, method = RequestMethod.POST)
	public String confirmAddMobileUser(@ModelAttribute("reader") final Reader reader, final Model model,
			final HttpServletRequest request) {
		logger.info("about to add Reader Details Confirms");
		PageBean pageBean = new PageBean("Succefully New Reader Added",
				"reader/register/registerReaderAlldoneSuccessful", Module.READER, "reader/sideMenuReader");
		model.addAttribute("pageBean", pageBean);
		Reader readerUserSavedInHttpSession = (Reader) request.getSession(true).getAttribute("addReaderSession");
		if (readerUserSavedInHttpSession == null) {
			return "redirect:/reader/registerMerchantReader";
		}
		model.addAttribute(readerUserSavedInHttpSession);
		Merchant merchant = (Merchant) request.getSession(true).getAttribute("CURRENT_USER_MERCHANT");
		readerUserSavedInHttpSession.setMerchant(merchant);

		readerService.addReaderUser(readerUserSavedInHttpSession);
		model.addAttribute("merchant", merchant);
		request.getSession(true).removeAttribute("addReaderSession");

		return TEMPLATE_DEFAULT;
	}
}
