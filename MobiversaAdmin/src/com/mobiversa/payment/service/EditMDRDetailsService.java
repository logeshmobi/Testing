package com.mobiversa.payment.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.dao.EditMDRDetailsDao;
import com.mobiversa.payment.dto.EditMDRDetailsBean;

@Service
public class EditMDRDetailsService {

	@Autowired
	private EditMDRDetailsDao editMDRDetailDao;

	private static final Logger logger = Logger.getLogger(EditMDRDetailsService.class.getName());

	@SuppressWarnings("nls")
	public List<String> getMidList(Long merchantId) {

		List<String> midList = new ArrayList<String>();
		Merchant merchantDetail = this.editMDRDetailDao.loadMerchantByID(merchantId);

		if (Objects.nonNull(merchantDetail) && Objects.nonNull(merchantDetail.getMid())) {

			MID mid = merchantDetail.getMid();
			midList.addAll(Arrays.asList(
					formatMid(getAsString(merchantId), "PAYOUT"),
					formatMid(mid.getFpxMid(), "FPX"),
					formatMid(mid.getBoostMid(), "BOOST"),
					formatMid(mid.getShoppyMid(), "SHOPPY"),
					formatMid(mid.getGrabMid(), "GRAB"),
					formatMid(mid.getTngMid(), "TNG"),
					formatMid(mid.getUmEzywayMid(), "EZYWAY"),
					formatMid(mid.getUmMotoMid(), "EZYMOTO"),
					formatMid(mid.getFiuuMid(), "FIUU")));

			midList.removeIf(Objects::isNull);
		}
		return midList;
	}
	
	@SuppressWarnings({ "nls", "static-method" })
	public EditMDRDetailsBean setMdrDetails(String updateMdrDetails)
			throws JsonProcessingException, IOException {

		EditMDRDetailsBean bean = new EditMDRDetailsBean();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode rootNode = mapper.readTree(updateMdrDetails);

		// FPX Mdr Details
		JsonNode fpxNode = rootNode.path("fpx");
		if (!fpxNode.isMissingNode()) {
			bean.setFpxMerchantMDR(fpxNode.path("merchantmdr").asText());
			bean.setFpxHostMDR(fpxNode.path("hostmdr").asText());
			bean.setFpxMobiMDR(fpxNode.path("mobimdr").asText());
			bean.setFpxMinimumMDR(fpxNode.path("minimummdr").asText());
		}

		// Boost Mdr Details
		JsonNode boostNode = rootNode.path("ewallet").path("boost");
		if (!boostNode.isMissingNode()) {
			bean.setBoostMerchantMDR(boostNode.path("merchantmdr").asText());
			bean.setBoostHostMDR(boostNode.path("hostmdr").asText());
			bean.setBoostMobiMDR(boostNode.path("mobimdr").asText());
			bean.setBoostMinimumMDR(boostNode.path("minimummdr").asText());
		}

		// Grab Mdr Details
		JsonNode grabNode = rootNode.path("ewallet").path("grab");
		if (!grabNode.isMissingNode()) {
			bean.setGrabMerchantMDR(grabNode.path("merchantmdr").asText());
			bean.setGrabHostMDR(grabNode.path("hostmdr").asText());
			bean.setGrabMobiMDR(grabNode.path("mobimdr").asText());
			bean.setGrabMinimumMDR(grabNode.path("minimummdr").asText());
		}

		// TNG Mdr Details
		JsonNode tngNode = rootNode.path("ewallet").path("tng");
		if (!tngNode.isMissingNode()) {
			bean.setTngMerchantMDR(tngNode.path("merchantmdr").asText());
			bean.setTngHostMDR(tngNode.path("hostmdr").asText());
			bean.setTngMobiMDR(tngNode.path("mobimdr").asText());
			bean.setTngMinimumMDR(tngNode.path("minimummdr").asText());
		}

		// SPP Mdr Details
		JsonNode sppNode = rootNode.path("ewallet").path("spp");
		if (!sppNode.isMissingNode()) {
			bean.setSppMerchantMDR(sppNode.path("merchantmdr").asText());
			bean.setSppHostMDR(sppNode.path("hostmdr").asText());
			bean.setSppMobiMDR(sppNode.path("mobimdr").asText());
			bean.setSppMinimumMDR(sppNode.path("minimummdr").asText());
		}

		// Payout Mdr Details
		JsonNode payoutNode = rootNode.path("payout");
		if (!payoutNode.isMissingNode()) {
			bean.setPayoutMerchantMDR(payoutNode.path("merchantmdr").asText());
			bean.setPayoutHostMDR(payoutNode.path("hostmdr").asText());
			bean.setPayoutMobiMDR(payoutNode.path("mobimdr").asText());
			bean.setPayoutMinimumMDR(payoutNode.path("minimummdr").asText());
		}

		// Visa Mdr Details
		JsonNode visaNode = rootNode.path("cards").path("visa");
		if (!visaNode.isMissingNode()) {
			bean.setVisaLocalDebitCardMDR(visaNode.path("localdebitmdr").asText());
			bean.setVisaLocalCreditCardMDR(visaNode.path("localcreditmdr").asText());
			bean.setVisaForeignDebitCardMDR(visaNode.path("foriegndebitmdr").asText());
			bean.setVisaForeignCreditCardMDR(visaNode.path("foriegncreditmdr").asText());
		}

		// Master Mdr Details
		JsonNode masterNode = rootNode.path("cards").path("master");
		if (!masterNode.isMissingNode()) {
			bean.setMasterLocalDebitCardMDR(masterNode.path("localdebitmdr").asText());
			bean.setMasterLocalCreditCardMDR(masterNode.path("localcreditmdr").asText());
			bean.setMasterForeignDebitCardMDR(masterNode.path("foriegndebitmdr").asText());
			bean.setMasterForeignCreditCardMDR(masterNode.path("foriegncreditmdr").asText());
		}

		// UnionPay Mdr Details
		JsonNode unionPayNode = rootNode.path("cards").path("union");
		if (!unionPayNode.isMissingNode()) {
			bean.setUnionPayLocalDebitCardMDR(unionPayNode.path("localdebitmdr").asText());
			bean.setUnionPayLocalCreditCardMDR(unionPayNode.path("localcreditmdr").asText());
			bean.setUnionPayForeignDebitCardMDR(unionPayNode.path("foriegndebitmdr").asText());
			bean.setUnionPayForeignCreditCardMDR(unionPayNode.path("foriegncreditmdr").asText());
		}

//		 Minimum Value
//		bean.setMinValue(rootNode.path("payout").path("minimummdr").asText());

		// Mid
		bean.setMid(rootNode.path("mid").path("mid").asText());

		return bean;
	}
	
	@SuppressWarnings("nls")
	public int updateMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid, String type) throws Exception {

		switch (type) {
		case "FPX":
			return this.editMDRDetailDao.updateFPXMdrDetails(updateMdrDetails, mid);
		case "BOOST":
			return this.editMDRDetailDao.updateBoostMdrDetails(updateMdrDetails, mid);
		case "GRAB":
			return this.editMDRDetailDao.updateGrabMdrDetails(updateMdrDetails, mid);
		case "SHOPPY":
			return this.editMDRDetailDao.updateSppMdrDetails(updateMdrDetails, mid);
		case "TNG":
			return this.editMDRDetailDao.updateTngMdrDetails(updateMdrDetails, mid);
		case "EZYWAY":
			return this.editMDRDetailDao.updateUMEzywayMdrDetails(updateMdrDetails, mid);
		case "EZYMOTO":
			return this.editMDRDetailDao.updateUMEzyMotoMdrDetails(updateMdrDetails, mid);
		case "FIUU":
			return this.editMDRDetailDao.updateFiuuMdrDetails(updateMdrDetails, mid);	
		case "PAYOUT":
			return this.editMDRDetailDao.updatePayoutMdrDetails(updateMdrDetails, mid);
		case "VISA":
			return this.editMDRDetailDao.updateVisaCardMdrDetails(updateMdrDetails, mid);
		case "MASTER":
			return this.editMDRDetailDao.updateMasterCardMdrDetails(updateMdrDetails, mid);
		case "UNIONPAY":
			return this.editMDRDetailDao.updateUnionPayMdrDetails(updateMdrDetails, mid);
		default:
			return 0;
		}
	}
	

	@SuppressWarnings("nls")
	public int updateSubMerchantMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid, String type) throws Exception {

		switch (type) {
		case "FPX":
			return this.editMDRDetailDao.updateFPXMdrDetails(updateMdrDetails, mid);
		case "BOOST":
			return this.editMDRDetailDao.updateBoostMdrDetails(updateMdrDetails, mid);
		case "GRAB":
			return this.editMDRDetailDao.updateGrabMdrDetails(updateMdrDetails, mid);
		case "SHOPPY":
			return this.editMDRDetailDao.updateSppMdrDetails(updateMdrDetails, mid);
		case "TNG":
			return this.editMDRDetailDao.updateTngMdrDetails(updateMdrDetails, mid);
		case "PAYOUT":
			return this.editMDRDetailDao.updatePayoutMdrDetails(updateMdrDetails, mid);
		default:
			return 0;
		}
	}
	
	@SuppressWarnings("nls")
	public EditMDRDetailsBean loadMdrDetails(String mid, String type) {

		switch (type) {
		case "FPX":
			return this.editMDRDetailDao.loadFPXMdrDetails(mid);
		case "BOOST":
			return this.editMDRDetailDao.loadBoostMdrDetails(mid);
		case "GRAB":
			return this.editMDRDetailDao.loadGrabMdrDetails(mid);
		case "SHOPPY":
			return this.editMDRDetailDao.loadSppMdrDetails(mid);
		case "TNG":
			return this.editMDRDetailDao.loadTngMdrDetails(mid);
		case "EZYWAY":
			return this.editMDRDetailDao.loadUMEzywayMdrDetails(mid);
		case "EZYMOTO":
			return this.editMDRDetailDao.loadUMEzywayMdrDetails(mid);
//			return this.editMDRDetailDao.loadUMEzyMotoMdrDetails(mid);
		case "FIUU":
			return this.editMDRDetailDao.loadUMEzywayMdrDetails(mid);	
		case "PAYOUT":
			return this.editMDRDetailDao.loadPayoutMdrDetails(mid);
		default:
			return new EditMDRDetailsBean();
		}
	}
	
	@SuppressWarnings("nls")
	public EditMDRDetailsBean loadSubMerchantMdrDetails(String mid, String type) {

		switch (type) {
		case "FPX":
			return this.editMDRDetailDao.loadFPXMdrDetails(mid);
		case "BOOST":
			return this.editMDRDetailDao.loadBoostMdrDetails(mid);
		case "GRAB":
			return this.editMDRDetailDao.loadGrabMdrDetails(mid);
		case "SHOPPY":
			return this.editMDRDetailDao.loadSppMdrDetails(mid);
		case "TNG":
			return this.editMDRDetailDao.loadTngMdrDetails(mid);
		case "PAYOUT":
			return this.editMDRDetailDao.loadPayoutMdrDetails(mid);
		default:
			return new EditMDRDetailsBean();
		}
	}
	
	private static String formatMid(String mid, String type) {
		return Objects.nonNull(mid) ? mid + " ~ " + type : null; //$NON-NLS-1$
	}

	private static String getAsString(Long longValue) {
		return String.valueOf(longValue);
	}
}