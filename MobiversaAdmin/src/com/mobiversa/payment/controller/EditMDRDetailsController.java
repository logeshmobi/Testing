package com.mobiversa.payment.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import static com.mobiversa.payment.service.AgniService.refreshCache;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.dao.EditMDRDetailsDao;
import com.mobiversa.payment.dto.EditMDRDetailsBean;
import com.mobiversa.payment.dto.EditMdrResponse;
import com.mobiversa.payment.service.EditMDRDetailsService;

@Controller
@RequestMapping(value = EditMDRDetailsController.URL_BASE)
@SuppressWarnings("nls")
public class EditMDRDetailsController extends BaseController {

	@Autowired
	private EditMDRDetailsService editMDRDetailsService;
	@Autowired
	private EditMDRDetailsDao editMDRDetailDao;

	private static final Logger logger = Logger.getLogger(MDRDetailsController.class);
	public static final String URL_BASE = "/editMdr";

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public static String defaultPage() {
		logger.info("Default url");
		return "redirect:" + URL_BASE + "/addMDRDetails";
	}

	@RequestMapping(value = { "/getAllMerchantsDetails" }, method = RequestMethod.GET)
	public String getAllMerchantsDetails(Model model, final HttpServletRequest request,
			final java.security.Principal principal) {
		
		PageBean pageBean = new PageBean("MDRDetails", "transaction/editMdr/editMdr", Module.MOBILE_USER,
				"mobileuser/sideMenuMobileUser");
		
		logger.info("Edit MDR Triggered. Admin User: " + principal.getName());

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", getAllMerchantDetails());

		return this.TEMPLATE_DEFAULT;
	}

	private List<Merchant> getAllMerchantDetails() {
		return this.editMDRDetailDao.loadMerchant();
	}

//	@SuppressWarnings("nls")
//	public List<String> getMidList(Long merchantId) {
//
//		List<String> midList = new ArrayList<String>();
//		Merchant merchantDetail = this.editMDRDetailDao.loadMerchantByID(merchantId);
//
//		if (Objects.nonNull(merchantDetail) && Objects.nonNull(merchantDetail.getMid())) {
//
//			MID mid = merchantDetail.getMid();
//			midList.addAll(Arrays.asList(
//					formatMid(getAsString(merchantId), "PAYOUT"),
//					formatMid(mid.getFpxMid(), "FPX"),
//					formatMid(mid.getBoostMid(), "BOOST"),
//					formatMid(mid.getShoppyMid(), "SHOPPY"),
//					formatMid(mid.getGrabMid(), "GRAB"),
//					formatMid(mid.getTngMid(), "TNG"),
//					formatMid(mid.getUmEzywayMid(), "EZYWAY"),
//					formatMid(mid.getUmMotoMid(), "EZYMOTO"))
//					);
//
//			midList.removeIf(Objects::isNull);
//		}
//		return midList;
//	}

	@RequestMapping(value = { "/getMidList" }, method = RequestMethod.GET)
	public String getMidList(Model model, final HttpServletRequest request, @RequestParam("id") Long merchantId,
			final java.security.Principal principal) {

		// Get the 'MID' of the merchant mapped in the Mobiversa_MDR table.
		List<String> midList = new ArrayList<String>();
		Merchant merchantDetail = this.editMDRDetailDao.loadMerchantByID(merchantId);
		List<Merchant> subMerchantList = new ArrayList<Merchant>();
		try {
			if (Objects.nonNull(merchantDetail) && Objects.nonNull(merchantDetail.getMid())) {

				MID mid = merchantDetail.getMid();
				midList.addAll(
						Arrays.asList(formatMid(getAsString(merchantId), "PAYOUT"),
								formatMid(mid.getFpxMid(), "FPX"),
								formatMid(mid.getBoostMid(), "BOOST"),
								formatMid(mid.getGrabMid(), "GRAB"),
								formatMid(mid.getTngMid(), "TNG"),
								formatMid(mid.getShoppyMid(), "SHOPPY"),
								formatMid(mid.getUmEzywayMid(), "EZYWAY"),
								formatMid(mid.getUmMotoMid(), "EZYMOTO"),
								formatMid(mid.getFiuuMid(), "FIUU")));

				midList.removeIf(Objects::isNull);

				logger.info("Mid's found for merchantId: " + merchantId + ", are - " + midList);
			}
			
			// Obtain the sub-merchants using the MM_ID, which is mapped to the business name.
			subMerchantList = this.editMDRDetailDao.loadSubMerchants(merchantDetail);
			
			logger.info("Sub-Merchant's found for MM_ID: " + merchantDetail.getBusinessName() + ". SubMerchant Details: " + subMerchantList.stream()
		    .map(subMerchant -> "ID: " + subMerchant.getId() + ", BusinessName: " + subMerchant.getBusinessName())
		    .collect(Collectors.joining(", ")));

//			// Load the specific sub-merchant details alone, which are mentioned in Config.
//			String showSubMerchantSummaryFor = PropertyLoad.getFile().getProperty("SHOW_EDIT_SUB_MERCHANT_FOR");
//
//			if (showSubMerchantSummaryFor != null && showSubMerchantSummaryFor.toUpperCase().equals("ALL")) {
//				subMerchantList = this.editMDRDetailDao.loadAllSubMerchantByMmid(merchantDetail);
//			} else if (showSubMerchantSummaryFor != null
//					&& Objects.equals(showSubMerchantSummaryFor, String.valueOf(merchantId))) {
//
//				// Load the specific sub-merchant details alone, which are mentioned in Config.
//				String showSelectedMerchantForDepansum = PropertyLoad.getFile()
//						.getProperty("SHOW_EDIT_SUB_MERCHANT_FOR_DEPANSUM");
//
//				if (showSelectedMerchantForDepansum != null
//						&& Objects.equals(showSelectedMerchantForDepansum.toUpperCase(), "ALL")) {
//					subMerchantList = this.editMDRDetailDao.loadAllSubMerchantByMmid(merchantDetail);
//				} else {
//					List<Long> listOfSubMerchantIds = getStringToList(showSelectedMerchantForDepansum);
//					subMerchantList = this.editMDRDetailDao.loadSubMerchantByID(listOfSubMerchantIds);
//				}
//			}
		} catch (Exception e) {

			logger.error("Exception in obtaining midList for merchantId: " + merchantId + ", Error: " + e.getMessage(), e);
		} finally {

			PageBean pageBean = new PageBean("MDRDetails", "transaction/editMdr/editMdr", Module.MOBILE_USER,
					"mobileuser/sideMenuMobileUser");

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("midList", midList);
			model.addAttribute("subMerchantList", subMerchantList);
			model.addAttribute("businessName", merchantDetail.getBusinessName());
			model.addAttribute("merchantId", merchantId);
			model.addAttribute("merchant1", getAllMerchantDetails());

		}
		return this.TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/getMdr" }, method = RequestMethod.GET)
	public String getMdr(Model model, final HttpServletRequest request, @RequestParam("mid") String mid,
			@RequestParam("id") Long merchantId, @RequestParam("businessName") String businessName,
			final java.security.Principal principal) {

		logger.info("Request Mid: " + mid);

		EditMDRDetailsBean mdrDetails = new EditMDRDetailsBean();

		EditMdrResponse response = new EditMdrResponse();

		Merchant merchantDetail = this.editMDRDetailDao.loadMerchantByID(merchantId);
		List<Merchant> subMerchantList = new ArrayList<Merchant>();

		try {
			String type = mid.substring(mid.indexOf("~") + 1).trim().toUpperCase();
			String formattedMid = mid.substring(0, mid.indexOf("~") - 1).trim();

			mdrDetails = this.editMDRDetailsService.loadMdrDetails(formattedMid, type);

			response.setType(mdrDetails.getType());
			response.setMdrDetails(mdrDetails);
			response.setMid(mid);

			logger.info("Get mdr response: " + response);
			
			// Obtain the sub-merchants using the MM_ID, which is mapped to the business name.
			subMerchantList = this.editMDRDetailDao.loadSubMerchants(merchantDetail);
			
			logger.info("Sub-Merchant's found for MM_ID: " + merchantDetail.getBusinessName() + ". SubMerchant Details: " + subMerchantList.stream()
		    .map(subMerchant -> "ID: " + subMerchant.getId() + ", BusinessName: " + subMerchant.getBusinessName())
		    .collect(Collectors.joining(", ")));

//			// Load the specific sub-merchant details alone, which are mentioned in Config.
//			String showSubMerchantSummaryFor = PropertyLoad.getFile().getProperty("SHOW_EDIT_SUB_MERCHANT_FOR");
//
//			if (showSubMerchantSummaryFor != null && showSubMerchantSummaryFor.toUpperCase().equals("ALL")) {
//				subMerchantList = this.editMDRDetailDao.loadAllSubMerchantByMmid(merchantDetail);
//			} else if (showSubMerchantSummaryFor != null
//					&& Objects.equals(showSubMerchantSummaryFor, String.valueOf(merchantId))) {
//
//				// Load the specific sub-merchant details alone, which are mentioned in Config.
//				String showSelectedMerchantForDepansum = PropertyLoad.getFile()
//						.getProperty("SHOW_EDIT_SUB_MERCHANT_FOR_DEPANSUM");
//
//				if (showSelectedMerchantForDepansum != null
//						&& Objects.equals(showSelectedMerchantForDepansum.toUpperCase(), "ALL")) {
//					subMerchantList = this.editMDRDetailDao.loadAllSubMerchantByMmid(merchantDetail);
//				} else {
//					List<Long> listOfSubMerchantIds = getStringToList(showSelectedMerchantForDepansum);
//					subMerchantList = this.editMDRDetailDao.loadSubMerchantByID(listOfSubMerchantIds);
//				}
//			}
		} catch (Exception e) {

			logger.error("Exception in obtaining MdrDetails of Mid: " + mid + ", Error: " + e.getMessage(), e);
		} finally {
			PageBean pageBean = new PageBean("MDRDetails", "transaction/editMdr/editMdr", Module.MOBILE_USER,
					"mobileuser/sideMenuMobileUser");

			model.addAttribute("pageBean", pageBean);
			// model.addAttribute("mdrDetails", mdrDetails);
			model.addAttribute("response", response);
			model.addAttribute("mid", mid);
			model.addAttribute("merchantId", merchantId);
			model.addAttribute("midList", this.editMDRDetailsService.getMidList(merchantId));
			model.addAttribute("businessName", businessName);
			model.addAttribute("subMerchantList", subMerchantList);
			model.addAttribute("merchant1", getAllMerchantDetails());

		}
		return this.TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/updateMdr" }, method = RequestMethod.POST)
	public String updateMdr(Model model, final HttpServletRequest request, @RequestParam("mid") String mid,
			@RequestParam("updateMdrDetails") String updateMdrDetailsString,
			@RequestParam("businessName") String businessName, @RequestParam("merchantId") Long merchantId,
			final java.security.Principal principal) {

		logger.info("Initiated userName: " + principal.getName() + ", Request Mid: " + mid + ", updateMdrDetails: "
				+ updateMdrDetailsString + ", businessName: " + businessName);

		// Need to change, if updated in Db, send true response
		boolean updated = false;
		List<Merchant> subMerchantList = new ArrayList<Merchant>();

		try {
			String type = mid.substring(mid.indexOf("~") + 1).trim().toUpperCase();
			String formattedMid = mid.substring(0, mid.indexOf("~") - 1).trim();

			EditMDRDetailsBean updateMdrDetails = this.editMDRDetailsService.setMdrDetails(updateMdrDetailsString);

			this.editMDRDetailsService.updateMdrDetails(updateMdrDetails, formattedMid, type);

			Merchant merchantDetail = this.editMDRDetailDao.loadMerchantByID(merchantId);
			
			// Obtain the sub-merchants using the MM_ID, which is mapped to the business name.
			subMerchantList = this.editMDRDetailDao.loadSubMerchants(merchantDetail);
			
			logger.info("Sub-Merchant's found for MM_ID: " + merchantDetail.getBusinessName() + ". SubMerchant Details: " + subMerchantList.stream()
		    .map(subMerchant -> "ID: " + subMerchant.getId() + ", BusinessName: " + subMerchant.getBusinessName())
		    .collect(Collectors.joining(", ")));


//			// Load the specific sub-merchant details alone, which are mentioned in Config.
//			String showSubMerchantSummaryFor = PropertyLoad.getFile().getProperty("SHOW_EDIT_SUB_MERCHANT_FOR");
//
//			if (showSubMerchantSummaryFor != null && showSubMerchantSummaryFor.toUpperCase().equals("ALL")) {
//				subMerchantList = this.editMDRDetailDao.loadAllSubMerchantByMmid(merchantDetail);
//			} else if (showSubMerchantSummaryFor != null
//					&& Objects.equals(showSubMerchantSummaryFor, String.valueOf(merchantId))) {
//
//				// Load the specific sub-merchant details alone, which are mentioned in Config.
//				String showSelectedMerchantForDepansum = PropertyLoad.getFile()
//						.getProperty("SHOW_EDIT_SUB_MERCHANT_FOR_DEPANSUM");
//
//				if (showSelectedMerchantForDepansum != null
//						&& Objects.equals(showSelectedMerchantForDepansum.toUpperCase(), "ALL")) {
//					subMerchantList = this.editMDRDetailDao.loadAllSubMerchantByMmid(merchantDetail);
//				} else {
//					List<Long> listOfSubMerchantIds = getStringToList(showSelectedMerchantForDepansum);
//					subMerchantList = this.editMDRDetailDao.loadSubMerchantByID(listOfSubMerchantIds);
//				}
//			}
			logger.info("Cache refresh after UPDATE MDR using AGNI API");
			refreshCache();
			updated = true;

		} catch (Exception e) {

			logger.error("Exception in updating MdrDetails of Mid: " + mid + ", Error: " + e.getMessage(), e);
			updated = false;
		} finally {
			PageBean pageBean = new PageBean("MDRDetails", "transaction/editMdr/editMdr", Module.MOBILE_USER,
					"mobileuser/sideMenuMobileUser");

			model.addAttribute("pageBean", pageBean);
//			model.addAttribute("mid", mid);
			model.addAttribute("businessName", businessName);
			model.addAttribute("merchant1", getAllMerchantDetails());
			model.addAttribute("merchantId", merchantId);
			model.addAttribute("midList", this.editMDRDetailsService.getMidList(merchantId));
			model.addAttribute("subMerchantList", subMerchantList);
			model.addAttribute("updated", updated);

		}
		return this.TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/getSubMidList" }, method = RequestMethod.GET)
	public String getSubMidList(Model model, final HttpServletRequest request, @RequestParam("id") Long subMerchantId,
			@RequestParam("mainMerchantId") Long mainMerchantId, final java.security.Principal principal) {

		// Get the 'MID' of the merchant mapped in the Mobiversa_MDR table.
		List<String> subMidList = new ArrayList<String>();
		Merchant subMerchantDetail = new Merchant();
		Merchant mainMerchantDetail = new Merchant();
		List<Merchant> subMerchantList = new ArrayList<Merchant>();
		List<Merchant> getAllMerchantDetails = new ArrayList<Merchant>();

		try {
			// Obtaining Sub-merchant details
			subMerchantDetail = this.editMDRDetailDao.loadMerchantByID(subMerchantId);
			// Obtaining Main-merchant details
			mainMerchantDetail = this.editMDRDetailDao.loadMerchantByID(mainMerchantId);
			// Obtain all merchant details
			getAllMerchantDetails = this.editMDRDetailDao.loadMerchant();

			if (Objects.nonNull(subMerchantDetail) && Objects.nonNull(subMerchantDetail.getMid())) {

				MID mid = subMerchantDetail.getMid();
				subMidList.addAll(Arrays.asList(formatMid(getAsString(subMerchantId), "PAYOUT"),
						formatMid(mid.getSubMerchantMID(), "FPX"),
						formatMid(mid.getSubMerchantMID(), "BOOST"),
						formatMid(mid.getSubMerchantMID(), "GRAB"),
						formatMid(mid.getSubMerchantMID(), "TNG"),
						formatMid(mid.getSubMerchantMID(), "SHOPPY")));		

				subMidList.removeIf(Objects::isNull);
				logger.info("Sub-Mid's found for merchantId: " + subMerchantId + ", are - " + subMidList);
			}

			// Obtain the sub-merchants using the MM_ID, which is mapped to the business name.
			subMerchantList = this.editMDRDetailDao.loadSubMerchants(mainMerchantDetail);
			
			logger.info("Sub-Merchant's found for MM_ID: " + mainMerchantDetail.getBusinessName() + ". SubMerchant Details: " + subMerchantList.stream()
		    .map(subMerchant -> "ID: " + subMerchant.getId() + ", BusinessName: " + subMerchant.getBusinessName())
		    .collect(Collectors.joining(", ")));
			
			
//			// Load the specific sub-merchant details alone, which are mentioned in Config.
//			String showSubMerchantSummaryFor = PropertyLoad.getFile().getProperty("SHOW_EDIT_SUB_MERCHANT_FOR");
//
//			if (showSubMerchantSummaryFor != null && showSubMerchantSummaryFor.toUpperCase().equals("ALL")) {
//				subMerchantList = this.editMDRDetailDao.loadAllSubMerchantByMmid(subMerchantDetail);
//			} else if (showSubMerchantSummaryFor != null
//					&& Objects.equals(showSubMerchantSummaryFor, String.valueOf(mainMerchantId))) {
//
//				// Load the specific sub-merchant details alone, which are mentioned in Config.
//				String showSelectedMerchantForDepansum = PropertyLoad.getFile()
//						.getProperty("SHOW_EDIT_SUB_MERCHANT_FOR_DEPANSUM");
//
//				if (showSelectedMerchantForDepansum != null
//						&& Objects.equals(showSelectedMerchantForDepansum.toUpperCase(), "ALL")) {
//					subMerchantList = this.editMDRDetailDao.loadAllSubMerchantByMmid(subMerchantDetail);
//				} else {
//					List<Long> listOfSubMerchantIds = getStringToList(showSelectedMerchantForDepansum);
//					subMerchantList = this.editMDRDetailDao.loadSubMerchantByID(listOfSubMerchantIds);
//				}
//			}

//			// Load the specific sub-merchant details alone, which are mentioned in Config.
//			String subMerchantMerchantIds = PropertyLoad.getFile().getProperty("EDIT_MDR_SUB_MERCHANT_MERCAHNT_ID");
//			
//			if(subMerchantMerchantIds.toUpperCase().equals("ALL")) {
//				subMerchantList = this.editMDRDetailDao.loadAllSubMerchantByMmid(subMerchantDetail);
//			} else {
//				List<Long> listOfSubMerchantIds = getStringToList(subMerchantMerchantIds);
//				subMerchantList = this.editMDRDetailDao.loadSubMerchantByID(listOfSubMerchantIds);
//			}
		} catch (Exception e) {
			logger.error("Exception in getting Sub-Mid list: " + e.getMessage() + e);
		} finally {
			PageBean pageBean = new PageBean("MDRDetails", "transaction/editMdr/editMdr", Module.MOBILE_USER,
					"mobileuser/sideMenuMobileUser");

			logger.info("sub merchant id is : " + subMerchantDetail.getId());

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("subMidList", subMidList);
			model.addAttribute("subMerchantList", subMerchantList);
			model.addAttribute("subMerchantBusinessName", subMerchantDetail.getBusinessName());
			model.addAttribute("businessName", mainMerchantDetail.getBusinessName());
			model.addAttribute("merchant1", getAllMerchantDetails);
			model.addAttribute("submerchantMerchantId", subMerchantId);
			model.addAttribute("merchantId", mainMerchantId);
			model.addAttribute("midList", this.editMDRDetailsService.getMidList(mainMerchantId));
			model.addAttribute("isSubMerchant", true);
		}
		return this.TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/getSubMerchantMdr" }, method = RequestMethod.GET)
	public String getSubMerchantMdr(Model model, final HttpServletRequest request, @RequestParam("mid") String subMid,
			@RequestParam("id") Long merchantId, @RequestParam("submerchantId") Long submerchantId,
			@RequestParam("businessName") String businessName,
			@RequestParam("subMerchantBusinessName") String submerchantBusinessName,
			final java.security.Principal principal) {

		logger.info("Request Sub-Mid: " + subMid);

		EditMDRDetailsBean mdrDetails = new EditMDRDetailsBean();
		EditMdrResponse response = new EditMdrResponse();

		Merchant subMerchantDetail = new Merchant();
		List<Merchant> subMerchantList = new ArrayList<Merchant>();
		List<String> subMidList = new ArrayList<String>();

		try {
			String type = subMid.substring(subMid.indexOf("~") + 1).trim().toUpperCase();
			String formattedMid = subMid.substring(0, subMid.indexOf("~") - 1).trim();
			
			//
			// Obtaining Sub-merchant details
			subMerchantDetail = this.editMDRDetailDao.loadMerchantByID(submerchantId);
			
			if (Objects.nonNull(subMerchantDetail) && Objects.nonNull(subMerchantDetail.getMid())) {

				MID mid = subMerchantDetail.getMid();
				subMidList.addAll(Arrays.asList(formatMid(getAsString(submerchantId), "PAYOUT"),
						formatMid(mid.getSubMerchantMID(), "FPX"),
						formatMid(mid.getSubMerchantMID(), "BOOST"),						
						formatMid(mid.getSubMerchantMID(), "GRAB"),
						formatMid(mid.getSubMerchantMID(), "TNG"),
						formatMid(mid.getSubMerchantMID(), "SHOPPY")));				

				subMidList.removeIf(Objects::isNull);
				logger.info("Sub-Mid's found for merchantId: " + submerchantId + ", are - " + subMidList);
			}			
			//

			mdrDetails = this.editMDRDetailsService.loadSubMerchantMdrDetails(formattedMid, type);

			response.setType(mdrDetails.getType());
			response.setMdrDetails(mdrDetails);
			response.setMid(subMid);

			logger.info("Get mdr response: " + response);
			
			// Obtaining Main-merchant details
			Merchant merchantDetail = this.editMDRDetailDao.loadMerchantByID(merchantId);
			
			// Obtain the sub-merchants using the MM_ID, which is mapped to the business name.
			subMerchantList = this.editMDRDetailDao.loadSubMerchants(merchantDetail);
			
//			submerchantId = subMerchantList.stream()
//				    .map(subMerchant -> subMerchant.getId())
//				    .findFirst() // Assuming you want the first ID in the list
//				    .orElse(null); 
			
			logger.info("Sub-Merchant's found for MM_ID: " + merchantDetail.getBusinessName() + ". SubMerchant Details: " + subMerchantList.stream()
		    .map(subMerchant -> "ID: " + subMerchant.getId() + ", BusinessName: " + subMerchant.getBusinessName())
		    .collect(Collectors.joining(", ")));

//			// Obtaining Sub-merchant details
//			subMerchantDetail = this.editMDRDetailDao.loadMerchantByID(submerchantId);
//
//			// Load the specific sub-merchant details alone, which are mentioned in Config.
//			String showSubMerchantSummaryFor = PropertyLoad.getFile().getProperty("SHOW_EDIT_SUB_MERCHANT_FOR");
//
//			if (showSubMerchantSummaryFor != null && showSubMerchantSummaryFor.toUpperCase().equals("ALL")) {
//				subMerchantList = this.editMDRDetailDao.loadAllSubMerchantByMmid(subMerchantDetail);
//			} else if (showSubMerchantSummaryFor != null
//					&& Objects.equals(showSubMerchantSummaryFor, String.valueOf(merchantId))) {
//
//				// Load the specific sub-merchant details alone, which are mentioned in Config.
//				String showSelectedMerchantForDepansum = PropertyLoad.getFile()
//						.getProperty("SHOW_EDIT_SUB_MERCHANT_FOR_DEPANSUM");
//
//				if (showSelectedMerchantForDepansum != null
//						&& Objects.equals(showSelectedMerchantForDepansum.toUpperCase(), "ALL")) {
//					subMerchantList = this.editMDRDetailDao.loadAllSubMerchantByMmid(subMerchantDetail);
//				} else {
//					List<Long> listOfSubMerchantIds = getStringToList(showSelectedMerchantForDepansum);
//					subMerchantList = this.editMDRDetailDao.loadSubMerchantByID(listOfSubMerchantIds);
//				}
//			}

		} catch (Exception e) {

			logger.error(
					"Exception in obtaining Sub-Merchant MdrDetails, of Sub-Mid: " + subMid + ", Error: " + e.getMessage(), e);
		} finally {
			PageBean pageBean = new PageBean("MDRDetails", "transaction/editMdr/editMdr", Module.MOBILE_USER,
					"mobileuser/sideMenuMobileUser");

			model.addAttribute("pageBean", pageBean);
			// model.addAttribute("mdrDetails", mdrDetails);
			model.addAttribute("midToUpdateMDR_submerchant", subMid);
			model.addAttribute("subMerchantResponse", response);
			model.addAttribute("merchantId", merchantId);
			model.addAttribute("submerchantMerchantId", submerchantId);
			model.addAttribute("subMidList", subMidList);
			model.addAttribute("midList", this.editMDRDetailsService.getMidList(merchantId));
			model.addAttribute("businessName", businessName);
			model.addAttribute("subMerchantBusinessName", submerchantBusinessName);
			model.addAttribute("merchant1", getAllMerchantDetails());
			model.addAttribute("isSubMerchant", true);
			model.addAttribute("subMerchantList", subMerchantList);
		}
		return this.TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/updateSubMerchantMdr" }, method = RequestMethod.POST)
	public String updateSubMerchantMdr(Model model, final HttpServletRequest request,
			@RequestParam("submerchant_mid") String subMerchantMid,
			@RequestParam("updateMdrDetailsOfSubMerchant") String updateMdrDetailsString,
			@RequestParam("businessName") String businessName,
			@RequestParam("submerchant_businessName") String submerchantBusinessName,
			@RequestParam("merchantId") Long merchantId, @RequestParam("submerchantId") Long submerchantId,
			final java.security.Principal principal) {

		logger.info("Initiated userName: " + principal.getName() + ",Request Sub-Mid: " + subMerchantMid
				+ ", update SubMerchant-Mdr Details: " + updateMdrDetailsString + ", businessName: " + businessName);

		Merchant subMerchantDetail = new Merchant();
		List<Merchant> subMerchantList = new ArrayList<Merchant>();
		List<String> subMidList = new ArrayList<String>();

		// Need to return boolean based on the values updated in db
		boolean updated = false;

		try {
			// Obtaining Sub-merchant details
			subMerchantDetail = this.editMDRDetailDao.loadMerchantByID(submerchantId);

			String type = subMerchantMid.substring(subMerchantMid.indexOf("~") + 1).trim().toUpperCase();
			String formattedMid = subMerchantMid.substring(0, subMerchantMid.indexOf("~") - 1).trim();
						
			//
			// Obtaining Sub-merchant details			
			if (Objects.nonNull(subMerchantDetail) && Objects.nonNull(subMerchantDetail.getMid())) {

				MID mid = subMerchantDetail.getMid();
				subMidList.addAll(Arrays.asList(formatMid(getAsString(submerchantId), "PAYOUT"),
						formatMid(mid.getSubMerchantMID(), "FPX"),
						formatMid(mid.getSubMerchantMID(), "BOOST"),						
						formatMid(mid.getSubMerchantMID(), "GRAB"),
						formatMid(mid.getSubMerchantMID(), "TNG"),
						formatMid(mid.getSubMerchantMID(), "SHOPPY")));				

				subMidList.removeIf(Objects::isNull);
				logger.info("Sub-Mid's found for merchantId: " + submerchantId + ", are - " + subMidList);
			}			
			//

			EditMDRDetailsBean updateMdrDetails = this.editMDRDetailsService.setMdrDetails(updateMdrDetailsString);

			this.editMDRDetailsService.updateSubMerchantMdrDetails(updateMdrDetails, formattedMid, type);
						
			// Obtain the sub-merchants using the MM_ID, which is mapped to the business name.
			Merchant merchantDetail = this.editMDRDetailDao.loadMerchantByID(merchantId);
			subMerchantList = this.editMDRDetailDao.loadSubMerchants(merchantDetail);
			
			logger.info("Sub-Merchant's found for MM_ID: " + merchantDetail.getBusinessName() + ". SubMerchant Details: " + subMerchantList.stream()
		    .map(subMerchant -> "ID: " + subMerchant.getId() + ", BusinessName: " + subMerchant.getBusinessName())
		    .collect(Collectors.joining(", ")));


//			// Load the specific sub-merchant details alone, which are mentioned in Config.
//			String showSubMerchantSummaryFor = PropertyLoad.getFile().getProperty("SHOW_EDIT_SUB_MERCHANT_FOR");
//
//			if (showSubMerchantSummaryFor != null && showSubMerchantSummaryFor.toUpperCase().equals("ALL")) {
//				subMerchantList = this.editMDRDetailDao.loadAllSubMerchantByMmid(subMerchantDetail);
//			} else if (showSubMerchantSummaryFor != null
//					&& Objects.equals(showSubMerchantSummaryFor, String.valueOf(merchantId))) {
//
//				// Load the specific sub-merchant details alone, which are mentioned in Config.
//				String showSelectedMerchantForDepansum = PropertyLoad.getFile()
//						.getProperty("SHOW_EDIT_SUB_MERCHANT_FOR_DEPANSUM");
//
//				if (showSelectedMerchantForDepansum != null
//						&& Objects.equals(showSelectedMerchantForDepansum.toUpperCase(), "ALL")) {
//					subMerchantList = this.editMDRDetailDao.loadAllSubMerchantByMmid(subMerchantDetail);
//				} else {
//					List<Long> listOfSubMerchantIds = getStringToList(showSelectedMerchantForDepansum);
//					subMerchantList = this.editMDRDetailDao.loadSubMerchantByID(listOfSubMerchantIds);
//				}
//			}

			logger.info("Cache refresh after UPDATING SUBMERCHANT MDR using AGNI API");
			refreshCache();
			updated = true;
		} catch (Exception e) {

			logger.error("Exception in updating MdrDetails of Mid: " + subMerchantMid + ", Error: " + e.getMessage(), e);
			updated = false;
		} finally {
			PageBean pageBean = new PageBean("MDRDetails", "transaction/editMdr/editMdr", Module.MOBILE_USER,
					"mobileuser/sideMenuMobileUser");

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("merchantId", merchantId);
			model.addAttribute("subMidList", subMidList);
			model.addAttribute("midList", this.editMDRDetailsService.getMidList(merchantId));
			model.addAttribute("businessName", businessName);
			model.addAttribute("subMerchantBusinessName", submerchantBusinessName);
			model.addAttribute("merchant1", getAllMerchantDetails());
			model.addAttribute("isSubMerchant", true);
			model.addAttribute("subMerchantList", subMerchantList);
			model.addAttribute("updated", updated);
			model.addAttribute("submerchantMerchantId", submerchantId);

		}
		return this.TEMPLATE_DEFAULT;
	}

	private static String formatMid(String mid, String type) {
		return Objects.nonNull(mid) ? mid + " ~ " + type : null; //$NON-NLS-1$
	}

	private static String getAsString(Long longValue) {
		return String.valueOf(longValue);
	}

//	private static List<Long> getStringToList(String midList) {
//		if (midList != null) {
//			String[] parts = midList.split(",");
//			List<Long> result = new ArrayList<>(parts.length);
//			for (String part : parts) {
//				result.add(Long.parseLong(part.trim()));
//			}
//			return result;
//		}
//		return null;
//	}
}
