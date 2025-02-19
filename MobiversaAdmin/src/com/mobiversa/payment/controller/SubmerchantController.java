package com.mobiversa.payment.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.MasterMerchant;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantInfo;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.payment.controller.bean.MDRDetailsBean;
import com.mobiversa.payment.controller.bean.OperationParentDataBean;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dao.SubMerchantRegistrationDao;
import com.mobiversa.payment.dto.SubmerchantDto;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.SubMerchantRegistrationService;
import com.mobiversa.payment.util.SubmerchantEmails;

@Controller
@RequestMapping(value = SubmerchantController.URL_BASE)
public class SubmerchantController extends BaseController {

	public static final String URL_BASE = "/submerchant";

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private MerchantDao merchantDao;

	@Autowired
	private SubMerchantRegistrationService submerchantService;

	@Autowired
	private SubMerchantRegistrationDao subMerchantRegistrationDao;

	@Autowired
	private PasswordEncoder encoder;

	@RequestMapping(value = { "/add-submerchant" }, method = RequestMethod.GET)
	public String addSubmerchant(final HttpServletRequest request, Model model,
			final java.security.Principal principal) {

		logger.info("Main Merchant UserName :" + principal.getName());

		PageBean pageBean = new PageBean("Initiate Submerchant Registration",
				"merchant/subMerchantRegistration/addSubmerchant", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("LoadedBusinessNames",getAllBusinessNames());
		return TEMPLATE_MERCHANT;
	}
	
	
	public String getAllBusinessNames(){
		try {
			List<String> validUsername = merchantService.loadAllMerchant();
			List<String> capitalizedBusinessnames = validUsername.stream()
					.filter(u -> u != null)
					.map(String::toUpperCase)
					.collect(Collectors.toList());
			ObjectMapper objectMapper = new ObjectMapper();
			String validUsernamesJson = "";
			try {
				validUsernamesJson = objectMapper.writeValueAsString(capitalizedBusinessnames);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return validUsernamesJson;
		} catch (Exception e) {
			logger.info("exception :: " + e + " " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(value = { "/add-submerchant/Request" }, method = RequestMethod.POST)
	public String addSubmerchantRequest(final HttpServletRequest request, Model model,
			@RequestParam("businessName") String businessName, @RequestParam("website") String website,
			@RequestParam("industry") String industry, @RequestParam("email") String email,
			@RequestParam("country_name") String country, final java.security.Principal principal) {

		logger.info("Merchant add submerchant ");
		logger.info("Main merchant Username :" + principal.getName());
		logger.info("BusinessName came from JSP :" + businessName);
		logger.info("Website came from JSP :" + website);
		logger.info("Industry came from JSP :" + industry);
		logger.info("email came from JSP :" + email);
		logger.info("country came from JSP :" + country);

		PageBean pageBean = new PageBean("Initiate Submerchant Registration",
				"merchant/subMerchantRegistration/addSubmerchant", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		Merchant currentMerchant = merchantService.loadMerchant(principal.getName());
		boolean registrationResponse = false;
		try {
			registrationResponse = submerchantService.addSubMerchantDetails(currentMerchant, businessName, website,
					industry, country, email, encoder);
		} catch (Exception e) {
			registrationResponse = false;
			logger.error("Exception while Submerchant Registration " + e);
		}

		logger.info("Registration Response :" + registrationResponse);

		model.addAttribute("loginname", principal.getName());
		model.addAttribute("registrationResponse", registrationResponse);
		model.addAttribute("LoadedBusinessNames",getAllBusinessNames());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/risk-compilence/intail" }, method = RequestMethod.GET)
	public String addSubmerchantRequest(final HttpServletRequest request, Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		try {

			logger.info("risk-compilence initial view");

			PageBean pageBean = new PageBean("Initiate Submerchant Registration",
					"merchant/subMerchantRegistration/approvesubmerchant", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			List<MerchantInfo> merchantInfo = subMerchantRegistrationDao.loadApproveSubmerchant();

			model.addAttribute("submerchantList", merchantInfo);

			List<MasterMerchant> masterMerchantList = subMerchantRegistrationDao.loadMasterMerchant();
			List<Merchant> mainMerchantList = masterMerchantList.stream()
					.map(merchant -> merchantDao.loadMerchantbymerchantid(Long.parseLong(merchant.getMerchantId())))
					.collect(Collectors.toList());

			model.addAttribute("mainmerchantList", mainMerchantList);

			PaginationBean<MerchantInfo> pagination = new PaginationBean<>();
			pagination.setCurrPage(currPage);
			pagination.setItemList(merchantInfo);
			pagination.setQuerySize(String.valueOf(merchantInfo.size()));
			model.addAttribute("paginationBean", pagination);

		} catch (Exception e) {
			logger.error("Exception in (/risk-compilence/intail)" + e.getMessage(), e);
		}
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/risk-compilence/search/submerchant" }, method = RequestMethod.GET)
	public String loadSubmerchantUsingMainMerchant(final HttpServletRequest request, Model model,
			@RequestParam("mmId") String mmid, @RequestParam("merchantName") String merchantName,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		logger.info("Risk-compilence search based on main merchant");
		logger.info("MMID came from JSP :" + mmid);
		logger.info("MerchantName came from JSP :" + merchantName);

		try {

			PageBean pageBean = new PageBean("Initiate Submerchant Registration",
					"merchant/subMerchantRegistration/approvesubmerchant", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			List<MerchantInfo> submerchantList = null;
			List<MerchantInfo> submerchantListCount = null;
			if (merchantName == null || merchantName.isEmpty()) {
				submerchantList = subMerchantRegistrationDao.loadsubmerchantInRiskandCompilence(currPage);
				submerchantListCount = subMerchantRegistrationDao.loadsubmerchantInRiskandCompilenceSize();
			} else {
				submerchantList = subMerchantRegistrationDao.loadsubmerchantInRiskandCompilence(merchantName, currPage);
				submerchantListCount = subMerchantRegistrationDao.loadsubmerchantInRiskandCompilenceSize(merchantName);
			}

			logger.info("Load submerchantList in Risk&compilence size " + submerchantList.size());

			List<MasterMerchant> masterMerchantList = subMerchantRegistrationDao.loadMasterMerchant();
			List<Merchant> mainMerchantList = masterMerchantList.stream()
					.map(merchant -> merchantDao.loadMerchantbymerchantid(Long.parseLong(merchant.getMerchantId())))
					.collect(Collectors.toList());

			model.addAttribute("mainmerchantList", mainMerchantList);
			model.addAttribute("submerchantList", submerchantList);
			model.addAttribute("merchantName", merchantName);

			PaginationBean<MerchantInfo> pagination = new PaginationBean<>();
			pagination.setCurrPage(currPage);
			pagination.setItemList(submerchantList);
			pagination.setQuerySize(String.valueOf(submerchantListCount.size()));
			model.addAttribute("paginationBean", pagination);

		} catch (Exception e) {
			logger.error("Exception in (/risk-compilence/search/submerchant)" + e.getMessage(), e);
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/risk-compilence/validate" }, method = RequestMethod.POST)
	public String addSubmerchantRequestValidate(final HttpServletRequest request, Model model,
			@RequestParam("submerchantName") String submerchantBusinessName, @RequestParam("decision") String decision,
			@RequestParam("comment") String description,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		try {

			// Once the Risk and Compliance team validates the merchant details, they are
			// moved to the operations team.
			logger.info("Risk and Compilence After Approve or Reject Merchant");
			logger.info("MerchantName came from JSP :" + submerchantBusinessName);
			logger.info("Decision came from JSP :" + decision);
			description = description.replace("'", "");
			logger.info("Description came from JSP :" + description);

			PageBean pageBean = new PageBean("Initiate Submerchant Registration",
					"merchant/subMerchantRegistration/approvesubmerchant", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			boolean updateSubmerchantStatus = false;
			if ("approve".equalsIgnoreCase(decision)) {
				decision = CommonStatus.APPROVED.toString();
				updateSubmerchantStatus = true;
			} else if ("reject".equalsIgnoreCase(decision)) {
				decision = CommonStatus.REJECTED.toString();
				updateSubmerchantStatus = false;
			}
			logger.info("Decision :" + decision);
			int updatestatus = subMerchantRegistrationDao.updateMerchantStatusAndReason(decision, description,
					submerchantBusinessName, principal.getName());

			if (updatestatus != 0 && decision.equalsIgnoreCase(CommonStatus.APPROVED.toString())) {
				Merchant merchantData = merchantDao.loadMerchantbyBussinessName(submerchantBusinessName);
				SubmerchantEmails emails = new SubmerchantEmails();
				emails.sendMailToOperationChild(merchantData);
			} else {
				logger.info("Merchant Status and Reason won't update and status not in approved ");
			}

//			List<Merchant> mainMerchantList = merchantDao.loadMainMerchant();
//			mainMerchantList = mainMerchantList.stream()
//					.filter(m -> m != null && m.getMmId() != null && !m.getMmId().isEmpty()
//							&& !m.getMmId().equals("--Select the Master Merchant--"))
//					.filter(distinctByKey(m -> m.getMmId())).collect(Collectors.toList());

			// New
			List<MasterMerchant> masterMerchantList = subMerchantRegistrationDao.loadMasterMerchant();
			List<Merchant> mainMerchantList = masterMerchantList.stream()
					.map(merchant -> merchantDao.loadMerchantbymerchantid(Long.parseLong(merchant.getMerchantId())))
					.collect(Collectors.toList());

			model.addAttribute("mainmerchantList", mainMerchantList);

			List<MerchantInfo> merchantInfo = subMerchantRegistrationDao.loadApproveSubmerchant();
			model.addAttribute("submerchantList", merchantInfo);

			PaginationBean<MerchantInfo> pagination = new PaginationBean<>();
			model.addAttribute("updateSubmerchantStatus", updateSubmerchantStatus);

			pagination.setCurrPage(currPage);
			pagination.setItemList(merchantInfo);
			pagination.setQuerySize(String.valueOf(merchantInfo.size()));
			model.addAttribute("paginationBean", pagination);

		} catch (Exception e) {
			logger.error("Exception in (/risk-compilence/validate)" + e.getMessage(), e);
		}

		return TEMPLATE_DEFAULT;
	}

	/* Operation-child */
	@RequestMapping(value = { "/operation-child/initial" }, method = RequestMethod.GET)
	public String operationinitialLoad(final HttpServletRequest request, Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		try {
			logger.info("Operation login to merchant registration view ");
			logger.info("currPage came from JSP :" + currPage);

			PageBean pageBean = new PageBean("Initiate Submerchant Registration",
					"merchant/subMerchantRegistration/submerchant_list", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			List<SubmerchantDto> merchantInfo = subMerchantRegistrationDao
					.loadSubmerchantListForOperationChildvalidationWithpagination(currPage);
			
//			int submerchantListsize = 0;
//			submerchantListsize = subMerchantRegistrationDao.loadSubmerchantListForOperationChildvalidation();

			// Load approved merchants
			List<MerchantInfo> approveSubmerchantList = subMerchantRegistrationDao.loadApprovedMerchant();

			logger.info("MobileUsers size in operation initial : " + merchantInfo.size());
			logger.info("filteredSubmerchant : " + approveSubmerchantList);

			List<MasterMerchant> masterMerchantList = subMerchantRegistrationDao.loadMasterMerchant();
			List<Merchant> mainMerchantList = masterMerchantList.stream()
					.map(merchant -> merchantDao.loadMerchantbymerchantid(Long.parseLong(merchant.getMerchantId())))
					.collect(Collectors.toList());

			model.addAttribute("mainmerchant", mainMerchantList);

			model.addAttribute("submerchantList", merchantInfo);
			model.addAttribute("requestCount", approveSubmerchantList.size());
			model.addAttribute("merchantName", null);

			PaginationBean<SubmerchantDto> pagination = new PaginationBean<>();
			pagination.setCurrPage(currPage);
			pagination.setItemList(merchantInfo);
			if (merchantInfo != null) {
				if (!merchantInfo.isEmpty()) {
					int submerchantListSize = merchantInfo.get(0).getSubmerchantListSizeForOperationChild();
					pagination.setQuerySize(String.valueOf(submerchantListSize));
					logger.info("Submerchant list size set to: " + submerchantListSize);
				} else {
					pagination.setQuerySize("0");
					logger.warn("merchantInfo list is empty. Setting query size to default value of 0.");
				}
			} else {
				pagination.setQuerySize("0");
				logger.error("merchantInfo list is null. Setting query size to default value of 0.");
			}
			model.addAttribute("paginationBean", pagination);
		} catch (Exception e) {
			logger.error("Exception in (/operation-child/initial)" + e.getMessage(), e);
		}
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/operation-child/preview/submerchant" }, method = RequestMethod.POST)
	public String subMerchantPreviewOperationTeam(final HttpServletRequest request, Model model,
			@RequestParam("submerchantBusinessName") String merchantName,
			@RequestParam("status_update") String decision, @RequestParam("comment") String description,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		try {
			logger.info("Operation-child preview Submerchant ");
			PageBean pageBean = new PageBean("Initiate Submerchant Registration",
					"merchant/subMerchantRegistration/submerchant_list", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			logger.info("CurrPage came from JSP : " + currPage);
			logger.info("Operation child decision came from JSP : " + decision);
			description = description.replace("'", "");
			logger.info("Operation child description came from JSP : " + description);

			switch (decision.toLowerCase()) {
			case "approve":
				decision = CommonStatus.APPROVED.toString();
				break;
			case "suspended":
				decision = CommonStatus.SUSPENDED.toString();
				break;
			case "pending":
				decision = CommonStatus.PENDING.toString();
				break;
			case "terminated":
				decision = CommonStatus.TERMINATED.toString();
				break;
			case "active":
				decision = CommonStatus.ACTIVE.toString();
				break;
			case "repost":
				decision = CommonStatus.SUBMITTED.toString();
				break;
			default:
				break;
			}

			String descriptionUpdatedBy = principal.getName();

			logger.info("Operation-child login :" + descriptionUpdatedBy);
			logger.info("Decision :" + decision.toUpperCase() + " Description : " + description
					+ " Description updated by :" + descriptionUpdatedBy);

			int afftectedRows = subMerchantRegistrationDao.updateMerchantStatusAndReason(decision.toUpperCase(),
					description, merchantName, descriptionUpdatedBy);

			boolean updateSubmerchantStatus = false;
			if (afftectedRows <= 0) {
				updateSubmerchantStatus = false;
			} else {
				updateSubmerchantStatus = true;
			}

			if (decision.equalsIgnoreCase(CommonStatus.REPOST.toString())) {
				Merchant merchant = subMerchantRegistrationDao.findMerchantBusinessName(merchantName);
				SubmerchantEmails emails = new SubmerchantEmails();
				emails.operationParentForRecheckMail(merchant, description);
			}

			List<SubmerchantDto> merchantInfo = subMerchantRegistrationDao
					.loadSubmerchantListForOperationChildvalidationWithpagination(currPage);
//			int submerchantListsize = 0;
//			submerchantListsize = subMerchantRegistrationDao.loadSubmerchantListForOperationChildvalidation();
			List<MerchantInfo> approveSubmerchantList = subMerchantRegistrationDao.loadApprovedMerchant();

			List<MasterMerchant> masterMerchantList = subMerchantRegistrationDao.loadMasterMerchant();
			List<Merchant> mainMerchantList = masterMerchantList.stream()
					.map(merchant -> merchantDao.loadMerchantbymerchantid(Long.parseLong(merchant.getMerchantId())))
					.collect(Collectors.toList());

			model.addAttribute("mainmerchant", mainMerchantList);
			model.addAttribute("submerchantList", merchantInfo);
			model.addAttribute("requestCount", approveSubmerchantList.size());
			model.addAttribute("updateSubmerchantStatus", updateSubmerchantStatus);

			PaginationBean<SubmerchantDto> pagination = new PaginationBean<>();
			pagination.setCurrPage(currPage);
			pagination.setItemList(merchantInfo);
			if (merchantInfo != null) {
				if (!merchantInfo.isEmpty()) {
					int submerchantListSize = merchantInfo.get(0).getSubmerchantListSizeForOperationChild();
					pagination.setQuerySize(String.valueOf(submerchantListSize));
					logger.info("Submerchant list size set to: " + submerchantListSize);
				} else {
					pagination.setQuerySize("0");
					logger.warn("merchantInfo list is empty. Setting query size to default value of 0.");
				}
			} else {
				pagination.setQuerySize("0");
				logger.error("merchantInfo list is null. Setting query size to default value of 0.");
			}
			model.addAttribute("paginationBean", pagination);

		} catch (Exception e) {
			logger.error("Exception in (/operation-child/preview/submerchant)" + e.getMessage(), e);
		}

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/operation-child/search/submerchant" }, method = RequestMethod.GET)
	public String loadSubmerchantUsingMainMerchantOperationTeam(final HttpServletRequest request, Model model,
			@RequestParam("mmId") String mmid, @RequestParam("merchantName") String merchantName,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		try {

			logger.info("Operation-child search Submerchant ");
			logger.info("MMID came from JSP : " + mmid);
			logger.info("MerchantName came from JSP : " + merchantName);

			PageBean pageBean = new PageBean("Initiate Submerchant Registration",
					"merchant/subMerchantRegistration/submerchant_list", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			// need to check mmid or merchant name
			// New change JSP
			List<SubmerchantDto> merchantInfo = null;
			if (merchantName == null || merchantName.isEmpty()) {
				merchantInfo = subMerchantRegistrationDao
						.loadSubmerchantListForOperationChildvalidationWithpagination(currPage);

			} else {
				merchantInfo = subMerchantRegistrationDao
						.loadSubmerchantListForOperationChildvalidationWithpagination(currPage, merchantName);

			}
			List<MerchantInfo> approveSubmerchantList = subMerchantRegistrationDao.loadApprovedMerchant();

			// New
			List<MasterMerchant> masterMerchantList = subMerchantRegistrationDao.loadMasterMerchant();
			List<Merchant> mainMerchantList = masterMerchantList.stream()
					.map(merchant -> merchantDao.loadMerchantbymerchantid(Long.parseLong(merchant.getMerchantId())))
					.collect(Collectors.toList());

			model.addAttribute("requestCount", approveSubmerchantList.size());
			model.addAttribute("submerchantList", merchantInfo);
			model.addAttribute("mainmerchant", mainMerchantList);
			model.addAttribute("merchantName", merchantName);

			PaginationBean<SubmerchantDto> pagination = new PaginationBean<>();

			pagination.setCurrPage(currPage);
			pagination.setItemList(merchantInfo);
			if (merchantInfo != null) {
				if (!merchantInfo.isEmpty()) {
					int submerchantListSize = merchantInfo.get(0).getSubmerchantListSizeForOperationChild();
					pagination.setQuerySize(String.valueOf(submerchantListSize));
					logger.info("Submerchant list size set to: " + submerchantListSize);
				} else {
					pagination.setQuerySize("0");
					logger.warn("merchantInfo list is empty. Setting query size to default value of 0.");
				}
			} else {
				pagination.setQuerySize("0");
				logger.error("merchantInfo list is null. Setting query size to default value of 0.");
			}
			model.addAttribute("paginationBean", pagination);

		} catch (Exception e) {
			logger.error("Exception in (/operation-child/search/submerchant)" + e.getMessage(), e);
		}

		return TEMPLATE_DEFAULT;
	}

	/* Operation team request button */

	@RequestMapping(value = { "/operation-child/request/initial/submerchant" }, method = RequestMethod.GET)
	public String loadSubmerchantRequestTabOperationTeam(final HttpServletRequest request, Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {
		try {
			logger.info("Operation-child request initial Submerchant ");
			PageBean pageBean = new PageBean("operation-child request initial Submerchant Registration",
					"merchant/subMerchantRegistration/submerchant_request", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			Map<String, Object> merchantMap = subMerchantRegistrationDao.loadSubMerchantApprove(currPage);
			long totalCount = (long) merchantMap.get("totalCount");
			List<Merchant> merchantList = (List<Merchant>) merchantMap.get("merchants");

			List<MasterMerchant> masterMerchantList = subMerchantRegistrationDao.loadMasterMerchant();
			List<Merchant> mainMerchantList = masterMerchantList.stream()
					.map(merchant -> merchantDao.loadMerchantbymerchantid(Long.parseLong(merchant.getMerchantId())))
					.collect(Collectors.toList());

			model.addAttribute("mainmerchantList", mainMerchantList);
			model.addAttribute("submerchantList", merchantList);

			logger.info("submerchant List size : " + merchantList.size());
			logger.info("mainmerchantList List size : " + mainMerchantList.size());
			logger.info("submerchant List Query size : " + totalCount);

			PaginationBean<Merchant> pagination = new PaginationBean<>();

			pagination.setCurrPage(currPage);
			pagination.setItemList(merchantList);
			pagination.setQuerySize(String.valueOf(totalCount));
			model.addAttribute("paginationBean", pagination);
		} catch (Exception e) {
			logger.error("Exception in (/operation-child/request/initial/submerchant)" + e.getMessage(), e);
		}
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/operation-child/request/search/submerchant" }, method = RequestMethod.GET)
	public String loadSubmerchantUsingMainMerchantOperationTeamRequest(final HttpServletRequest request, Model model,
			@RequestParam("mmId") String mmid, @RequestParam("merchantName") String merchantName,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		try {

			logger.info("Operation-child request search Submerchant ");
			logger.info("Operation-child search submrchant");
			logger.info("mmid came from JSP : " + mmid);
			logger.info("MerchantName came from JSP : " + merchantName);

			PageBean pageBean = new PageBean("Initiate Submerchant Registration",
					"merchant/subMerchantRegistration/submerchant_request", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			Map<String, Object> merchantMap;

			if (merchantName == null || merchantName.isEmpty()) {
				merchantMap = subMerchantRegistrationDao.loadSubMerchantApprove(currPage);
			} else {
				merchantMap = subMerchantRegistrationDao.loadMainMerchantUsingMMIDoperationTeam(merchantName, currPage);
			}

			long submerchantListQuerySize = (long) merchantMap.get("totalCount");
			List<Merchant> merchantListUsingMMID = (List<Merchant>) merchantMap.get("merchants");
			logger.info("SubMerchantListQuerySize : " + submerchantListQuerySize);

			List<MasterMerchant> masterMerchantList = subMerchantRegistrationDao.loadMasterMerchant();
			List<Merchant> mainMerchantList = masterMerchantList.stream()
					.map(merchant -> merchantDao.loadMerchantbymerchantid(Long.parseLong(merchant.getMerchantId())))
					.collect(Collectors.toList());

			logger.info("Submerchant List Size " + merchantListUsingMMID.size());
			logger.info("MerchantList List Size " + mainMerchantList.size());
			logger.info("Submerchant List Query Size " + submerchantListQuerySize);

			model.addAttribute("submerchantList", merchantListUsingMMID);
			model.addAttribute("mainmerchantList", mainMerchantList);
			model.addAttribute("merchantName", merchantName);

			PaginationBean<Merchant> pagination = new PaginationBean<>();
			pagination.setCurrPage(currPage);
			pagination.setItemList(mainMerchantList);
			pagination.setQuerySize(String.valueOf(submerchantListQuerySize));
			model.addAttribute("paginationBean", pagination);

		} catch (Exception e) {
			logger.error("Exception in (/operation-child/request/search/submerchant)" + e.getMessage(), e);
		}

		return TEMPLATE_DEFAULT;
	}

	/* Request button */

	@RequestMapping(value = { "/operation-child/request/search/preview" }, method = RequestMethod.GET)
	public String loadSubmerchantUsingMainMerchantOperationTeamRequest(final HttpServletRequest request, Model model,
			@RequestParam("merchantName") String merchantname, final java.security.Principal principal) {

		try {
			logger.info("Operation-child request search Preview ");
			logger.info("Username : " + principal.getName());
			logger.info("Operation-Child request Search ");
			logger.info("Merchantname came from JSP : " + merchantname);

			PageBean pageBean = new PageBean("Initiate Submerchant Registration",
					"merchant/subMerchantRegistration/submerchant_mdrrates", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			Merchant merchant = subMerchantRegistrationDao.loadSubMerchant(merchantname);
			model.addAttribute("merchant", merchant);
		} catch (Exception e) {
			logger.info("Exception in (/operation-child/request/search/preview)" + e.getMessage(), e);
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/operation-child/request/mdrRates" }, method = RequestMethod.POST)
	public String loadSubmerchantUsingMainMerchantOperationTeamAddMdr(final HttpServletRequest request, Model model,
			@RequestParam("mdrRates") String mdrRates, final java.security.Principal principal) {

		try {
			logger.info("Operation-child request MdrRates ");
			logger.info("Username : " + principal.getName());
			logger.info("Mdr Details from JSP : " + mdrRates);

			PageBean pageBean = new PageBean("Initiate Submerchant Registration",
					"merchant/subMerchantRegistration/submerchant_mdrrates", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			Gson gson = new Gson();
			MDRDetailsBean mdrDetails = gson.fromJson(mdrRates, MDRDetailsBean.class);

			logger.info("Mdr Details after toString : " + mdrDetails.toString());

			JSONObject jsonObject = new JSONObject(mdrRates);
			jsonObject.remove("merchantDetail");
			String yamlString = convertToYaml(jsonObject, 0);
			System.out.println(yamlString);

			Merchant merchant = new Merchant();
			MobiMDR mobiversaMdr = new MobiMDR();
			boolean updateSubmerchantStatus = false;
			if (mdrDetails != null) {
				merchant = subMerchantRegistrationDao.loadSubMerchant(mdrDetails.getMerchantDetail().getMerchantName());
				logger.info("merchant : " + merchant);
				logger.info("businessName : " + merchant.getBusinessName());
				int affectedRows = submerchantService.addMobiversaMdr(mdrDetails, merchant, yamlString);
				logger.info("Succesfully updated ");

				if (affectedRows <= 0) {
					updateSubmerchantStatus = false;
				} else {
					updateSubmerchantStatus = true;
				}
			}
			model.addAttribute("updateSubmerchantStatus", updateSubmerchantStatus);
			model.addAttribute("merchant", merchant);
		} catch (Exception e) {
			logger.info("Exception in (/operation-child/request/mdrRates) " + e.getMessage(), e);
		}

		return TEMPLATE_DEFAULT;
	}

	/* Operation parent login */

	@RequestMapping(value = { "/operation-parent/initial/submerchant" }, method = RequestMethod.GET)
	public String loadSubmerchantUsingMainMerchantOperationParentRequest(final HttpServletRequest request, Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal) {

		try {
			logger.info("Operation-parent Initial Submerchant ");
			logger.info("CurrPage came from JSP : " + currPage);

			PageBean pageBean = new PageBean("Initiate Submerchant Registration",
					"merchant/subMerchantRegistration/checkMDRRates", Module.TRANSACTION_WEB,
					"merchantweb/transaction/checkMDRRates");
			model.addAttribute("pageBean", pageBean);

//			List<Merchant> merchant = new ArrayList<>();
//			List<Merchant> submerchantMerchant = new ArrayList<>();
//			List<Merchant> submerchantListQuerySize = new ArrayList<>();

			Map<String, Object> merchantMap;

			merchantMap = subMerchantRegistrationDao.loadSubMerchantAfterApproveOperationteam(currPage);
//			submerchantListQuerySize = subMerchantRegistrationDao.loadSubMerchantAfterApproveOperationteam();

			long submerchantListQuerySize = (long) merchantMap.get("totalCount");
			List<Merchant> merchant = (List<Merchant>) merchantMap.get("merchants");

			List<OperationParentDataBean> operationParentData = new ArrayList<>();
			MDRDetailsBean mobiversaMdr;
			for (Merchant merchantData : merchant) {

				mobiversaMdr = submerchantService.loadMdrList(merchantData);
				logger.info("mobiversaMdr : " + mobiversaMdr.toString());
				operationParentData = submerchantService.datasOperationHead(mobiversaMdr, merchantData,
						operationParentData);
				logger.info("operationParentData : " + operationParentData.size());
			}
			logger.info("List size : " + operationParentData.size());

			for (OperationParentDataBean ope : operationParentData) {
				logger.info("MMID : " + ope.getMmId() + " Bussiness : " + ope.getBussinessName());
			}

			List<MasterMerchant> masterMerchantList = subMerchantRegistrationDao.loadMasterMerchant();
			List<Merchant> mainMerchantList = masterMerchantList.stream().map(
					merchantData -> merchantDao.loadMerchantbymerchantid(Long.parseLong(merchantData.getMerchantId())))
					.collect(Collectors.toList());

			logger.info("mainMerchantList : " + mainMerchantList);
			model.addAttribute("mainmerchantList", mainMerchantList);
			model.addAttribute("submerchant", operationParentData);

			PaginationBean<Merchant> pagination = new PaginationBean<>();
			pagination.setCurrPage(currPage);
			pagination.setItemList(mainMerchantList);
			pagination.setQuerySize(String.valueOf(submerchantListQuerySize));
			model.addAttribute("paginationBean", pagination);
		} catch (Exception e) {
			logger.error("Exception in (/operation-parent/initial/submerchant)" + e.getMessage(), e);
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/operation-parent/search/submerchant" }, method = RequestMethod.GET)
	public String loadSubmerchantUsingMainMerchantOperationParentSearchRequest(final HttpServletRequest request,
			Model model, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam("mmId") String mmID, @RequestParam("merchantName") String merchantName,
			final java.security.Principal principal) {

		try {
			logger.info("Operation-parent search Submerchant ");
			logger.info("MMID came from JSP : " + mmID);
			logger.info("merchantName came from JSP : " + merchantName);
			PageBean pageBean = new PageBean("Initiate Submerchant Registration",
					"merchant/subMerchantRegistration/checkMDRRates", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			List<Merchant> submerchantMerchant = new ArrayList<>();
			Map<String, Object> merchantMap;

			if (merchantName == null || merchantName.isEmpty()) {

				merchantMap = subMerchantRegistrationDao.loadSubMerchantAfterApproveOperationteam(currPage);
			} else {
				merchantMap = subMerchantRegistrationDao.loadMerchantsUsingMMIDAndName(merchantName, currPage);
			}

			long submerchantListQuerySize = (long) merchantMap.get("totalCount");
			List<Merchant> merchant = (List<Merchant>) merchantMap.get("merchants");

			List<OperationParentDataBean> operationParentData = new ArrayList<>();
			MDRDetailsBean mobiversaMdr;
			for (Merchant merchantData : merchant) {
				mobiversaMdr = submerchantService.loadMdrList(merchantData);
				logger.info("mobiversaMdr : " + mobiversaMdr.toString());
				operationParentData = submerchantService.datasOperationHead(mobiversaMdr, merchantData,
						operationParentData);
				logger.info("operationParentData : " + operationParentData.size());
			}
			logger.info("List size : " + operationParentData.size());

			List<MasterMerchant> masterMerchantList = subMerchantRegistrationDao.loadMasterMerchant();
			List<Merchant> mainMerchantList = masterMerchantList.stream().map(
					merchantData -> merchantDao.loadMerchantbymerchantid(Long.parseLong(merchantData.getMerchantId())))
					.collect(Collectors.toList());

			model.addAttribute("mainmerchantList", mainMerchantList);
			model.addAttribute("submerchant", operationParentData);
			model.addAttribute("merchantName", merchantName);

			PaginationBean<Merchant> pagination = new PaginationBean<>();
			pagination.setCurrPage(currPage);
			pagination.setItemList(mainMerchantList);
			pagination.setQuerySize(String.valueOf(submerchantListQuerySize));
			model.addAttribute("paginationBean", pagination);

		} catch (Exception e) {
			logger.error("Exception in (/operation-parent/search/submerchant)" + e.getMessage(), e);
		}
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/operation-parent/preview/submerchant/approve" }, method = RequestMethod.POST)
	public String loadSubmerchantUsingMainMerchantOperationParentPreviewRequest(final HttpServletRequest request,
			Model model, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam("mmId") String mmID, @RequestParam("subMerchantName") String businessName,
			@RequestParam("emails") String email, final java.security.Principal principal) {

		try {
			logger.info("Operation-parent Preview Submerchant Approve ");
			logger.info("MMID came from JSP : " + mmID);
			logger.info("businessName came from JSP : " + businessName);
			logger.info("email came from JSP : " + email);

			PageBean pageBean = new PageBean("Initiate Submerchant Registration",
					"merchant/subMerchantRegistration/checkMDRRates", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			List<Merchant> submerchantMerchant = new ArrayList<>();

			String status = CommonStatus.ACTIVE.toString();
			int statusUpdate = subMerchantRegistrationDao.updateMerchantStatusOperationTeam(status, businessName);

			Map<String, Object> merchantMap;

			merchantMap = subMerchantRegistrationDao.loadSubMerchantAfterApproveOperationteam(currPage);

			long submerchantListQuerySize = (long) merchantMap.get("totalCount");
			List<Merchant> merchant = (List<Merchant>) merchantMap.get("merchants");

			List<OperationParentDataBean> operationParentData = new ArrayList<>();
			MDRDetailsBean mobiversaMdr;
			for (Merchant merchantData : merchant) {

				mobiversaMdr = submerchantService.loadMdrList(merchantData);
				logger.info("mobiversaMdr : " + mobiversaMdr.toString());
				operationParentData = submerchantService.datasOperationHead(mobiversaMdr, merchantData,
						operationParentData);
				logger.info("operationParentData : " + operationParentData.size());
			}
			logger.info("List size : " + operationParentData.size());

			List<MasterMerchant> masterMerchantList = subMerchantRegistrationDao.loadMasterMerchant();
			List<Merchant> mainMerchantList = masterMerchantList.stream().map(
					merchantData -> merchantDao.loadMerchantbymerchantid(Long.parseLong(merchantData.getMerchantId())))
					.collect(Collectors.toList());

			model.addAttribute("mainmerchantList", mainMerchantList);
			model.addAttribute("submerchant", operationParentData);

			PaginationBean<Merchant> pagination = new PaginationBean<>();
			pagination.setCurrPage(currPage);
			pagination.setItemList(mainMerchantList);
			pagination.setQuerySize(String.valueOf(submerchantListQuerySize));
			model.addAttribute("paginationBean", pagination);

			boolean updateSubmerchantStatus = false;
			if (statusUpdate <= 0) {
				updateSubmerchantStatus = false;
			} else {

				Merchant merchantData = merchantDao.loadMerchantbyBussinessName(businessName);
				Merchant mainMerchantData = merchantDao.loadMerchantbyBussinessName(merchantData.getMmId());
				SubmerchantEmails emails = new SubmerchantEmails();
				emails.sendMailToOperationParentForApprove(merchantData, email, mainMerchantData);
				updateSubmerchantStatus = true;
			}

			model.addAttribute("updateSubmerchantStatus", updateSubmerchantStatus);

		} catch (Exception e) {
			logger.error("Exception in (operation-parent/preview/submerchant/approve) : " + e.getMessage(), e);
		}
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/operation-parent/preview/submerchant/recheck" }, method = RequestMethod.POST)
	public String loadSubmerchantUsingMainMerchantOperationParentRecheckRequest(final HttpServletRequest request,
			Model model, @RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam("mmId") String mmID, @RequestParam("subMerchantName") String businessName,
			@RequestParam("comment") String description, final java.security.Principal principal) {

		try {
			logger.info("Operation-parent Preview Submerchant Recheck ");
			logger.info("MMID came from JSP : " + mmID);
			logger.info("BusinessName came from JSP : " + businessName);
			description = description.replace("'", "");
			logger.info("Description came from JSP : " + description);

			PageBean pageBean = new PageBean("Initiate Submerchant Registration",
					"merchant/subMerchantRegistration/checkMDRRates", Module.TRANSACTION_WEB,
					"merchantweb/transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

//			List<Merchant> merchant = new ArrayList<>();
			List<Merchant> submerchantMerchant = new ArrayList<>();
//			List<Merchant> submerchantListQuerySize = new ArrayList<>();

			String status = CommonStatus.REPOST.toString();
//			String status = CommonStatus.PENDING.toString();
			int statusUpdate = subMerchantRegistrationDao.updateMerchantStatusAndReason(status, description,
					businessName, principal.getName());

//			merchant = merchantDao.loadSubMerchantAfterApproveOperationteam(currPage);
//			submerchantListQuerySize = merchantDao.loadSubMerchantAfterApproveOperationteam();

			Map<String, Object> merchantMap;

			merchantMap = subMerchantRegistrationDao.loadSubMerchantAfterApproveOperationteam(currPage);

			long submerchantListQuerySize = (long) merchantMap.get("totalCount");
			List<Merchant> merchant = (List<Merchant>) merchantMap.get("merchants");

			List<OperationParentDataBean> operationParentData = new ArrayList<>();
			MDRDetailsBean mobiversaMdr;
			for (Merchant merchantData : merchant) {

				mobiversaMdr = submerchantService.loadMdrList(merchantData);
				logger.info("mobiversaMdr : " + mobiversaMdr.toString());
				operationParentData = submerchantService.datasOperationHead(mobiversaMdr, merchantData,
						operationParentData);
				logger.info("operationParentData : " + operationParentData.size());
			}
			logger.info("List size : " + operationParentData.size());

			List<MasterMerchant> masterMerchantList = subMerchantRegistrationDao.loadMasterMerchant();
			List<Merchant> mainMerchantList = masterMerchantList.stream().map(
					merchantData -> merchantDao.loadMerchantbymerchantid(Long.parseLong(merchantData.getMerchantId())))
					.collect(Collectors.toList());

			model.addAttribute("mainmerchantList", mainMerchantList);
			model.addAttribute("submerchant", operationParentData);

			PaginationBean<Merchant> pagination = new PaginationBean<>();
			pagination.setCurrPage(currPage);
			pagination.setItemList(mainMerchantList);
			pagination.setQuerySize(String.valueOf(submerchantListQuerySize));
			model.addAttribute("paginationBean", pagination);

			boolean updateSubmerchantStatus = false;
			boolean updateIssue = false;
			if (statusUpdate <= 0) {
				updateSubmerchantStatus = false;
				updateIssue = false;
			} else {
				Merchant merchantData = merchantDao.loadMerchantbyBussinessName(businessName);
				SubmerchantEmails emails = new SubmerchantEmails();
				emails.sendMailToOperationParentForRecheck(merchantData, description);
				updateSubmerchantStatus = true;
				updateIssue = true;
			}
			model.addAttribute("updateRecheckStatus", updateSubmerchantStatus);

		} catch (Exception e) {
			logger.error("Exception in (operation-parent/preview/submerchant/recheck) : " + e.getMessage(), e);
		}
		return TEMPLATE_DEFAULT;
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}

	private static String convertToYaml(JSONObject jsonObject, int indent) {
		StringBuilder yaml = new StringBuilder();
		String indentStr = new String(new char[indent]).replace("\0", "  ");

		for (Object keyObject : jsonObject.keySet()) {
			String key = (String) keyObject;
			Object value = jsonObject.get(key);

			if (value instanceof JSONObject) {
				yaml.append(indentStr).append(key).append(":\n");
				yaml.append(convertToYaml((JSONObject) value, indent + 1));
			} else if (value instanceof JSONArray) {
				yaml.append(indentStr).append(key).append(":\n");
				yaml.append(convertToYaml((JSONArray) value, indent + 1));
			} else {
				yaml.append(indentStr).append(key).append(": ");
				if (value.toString().isEmpty()) {
					yaml.append("0.0\n");
				} else {
					yaml.append(value).append("\n");
				}
			}
		}

		return yaml.toString();
	}

	private static String convertToYaml(JSONArray jsonArray, int indent) {
		StringBuilder yaml = new StringBuilder();
		String indentStr = new String(new char[indent]).replace("\0", "  ");

		for (int i = 0; i < jsonArray.length(); i++) {
			Object value = jsonArray.get(i);

			if (value instanceof JSONObject) {
				yaml.append(indentStr).append("-\n");
				yaml.append(convertToYaml((JSONObject) value, indent + 1));
			} else if (value instanceof JSONArray) {
				yaml.append(indentStr).append("-\n");
				yaml.append(convertToYaml((JSONArray) value, indent + 1));
			} else {
				yaml.append(indentStr);
				if (value.toString().isEmpty()) {
					yaml.append(" 0.0\n"); // If value is empty, append "0.0"
				} else {
					yaml.append(" ").append(value).append("\n");
				}
			}
		}

		return yaml.toString();
	}

}
