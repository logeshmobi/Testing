package com.mobiversa.payment.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.MobileOTP;
import com.mobiversa.common.bo.PayoutMdr;
import com.mobiversa.payment.controller.bean.MDRDetailsBean;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Cards;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Ewallet;
import com.mobiversa.payment.controller.bean.MDRDetailsBean.Payout;
import com.mobiversa.payment.controller.bean.OperationParentDataBean;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dao.SubMerchantRegistrationDao;
import com.mobiversa.payment.util.SubmerchantEmails;
import com.mobiversa.payment.util.Utils;


@Service
public class SubMerchantRegistrationService {
	
	private static final Logger logger = Logger.getLogger(MerchantService.class.getName());

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private SubMerchantRegistrationDao subMerchantRegistrationDao;
	
	@Autowired
	private MerchantDao merchantDao;
	
	public boolean addSubMerchantDetails(Merchant currentMerchant, String businessName, String website, String industry,
			String country, String email, PasswordEncoder encoder) {
		Merchant merchant = new Merchant();
		merchant.setBusinessName(businessName);
		merchant.setEmail(email);
		merchant.setWebsite(website);
		merchant.setNatureOfBusiness(industry);
		merchant.setCountry(country);

		merchant.setEnableCard("No");
		merchant.setEnableBnpl("No");
		merchant.setEnableEwallet("Yes");
		merchant.setEnableFpx("No");
		merchant.setEnblPayout("No");

		Utils util = new Utils();
		String password = util.generatePassword();
		merchant.setUsername(password);
		merchant.setPassword(encoder.encode(password));
		String payoutgrandDetailsId = subMerchantRegistrationDao.addPayoutGrandDetailInitial();
		Merchant merchantDetails = subMerchantRegistrationDao.addsubMerchantDetails(merchant, payoutgrandDetailsId, currentMerchant);

		Merchant subMerchant = merchantDao.loadMerchantDetail1(merchant.getBusinessName());
		
		int merchantinfo = subMerchantRegistrationDao.updateMerchantInfo(subMerchant);
//		int submerchantAddMobileUser = merchantDAO.addMerchantDetailsinMobileUser(subMerchant, merchantDetails);
//		need to add merchant info
		
		
		logger.info("Submerchant business name : " + subMerchant.getBusinessName());
		int merchantDetailsAffectedRows = subMerchantRegistrationDao.addMerchantDetails(subMerchant, merchant);
		MobileOTP mobileOTP = subMerchantRegistrationDao.getReference(currentMerchant);

		String midId = subMerchantRegistrationDao.addMidtable(subMerchant, mobileOTP);
		logger.info("Mid FK Id  : " + midId);
		int midAffectedRow = subMerchantRegistrationDao.addmidFk(midId, merchant.getBusinessName());
		int payoutGrandDetailsAffectedRows = subMerchantRegistrationDao.addPayoutGrandDetail(subMerchant, payoutgrandDetailsId);
		int mobileOptAffectedRows = subMerchantRegistrationDao.updateMobileOTPDetails(mobileOTP);

		SubmerchantEmails emails = new SubmerchantEmails();
		emails.sendEmailtoRiskAndCompliance(subMerchant, merchantDetails);

		return payoutGrandDetailsAffectedRows != 0 && mobileOptAffectedRows != 0;

	}
	
	
	public int addMobiversaMdr(MDRDetailsBean mdrDetails, Merchant merchantData, String yamlString) {

		int updatedMobiversaMdr = subMerchantRegistrationDao.addMobiversaMdr(mdrDetails);

		int updatePayoutMdr = subMerchantRegistrationDao.addPayoutMdr(mdrDetails, merchantData);

		logger.info("mdrDetails in merchant service : " + mdrDetails.toString());

		Merchant merchant = merchantDao.loadMerchantbyBussinessName(mdrDetails.getMerchantDetail().getMerchantName());
		logger.info("merchant : " + merchant);
		String status = CommonStatus.SUBMITTED.toString();
		subMerchantRegistrationDao.updateMerchantStatusOperationTeam(status, mdrDetails.getMerchantDetail().getMerchantName());
		if (updatedMobiversaMdr != 0) {
			SubmerchantEmails emails = new SubmerchantEmails();
			emails.sendMailToOperationParent(merchant, yamlString);
		}
		return updatedMobiversaMdr;
	}

	public MDRDetailsBean loadMdrList(Merchant merchantData) {

		String[] a = { "VISA", "MASTERCARD", "UNIONPAY", "FPX", "BOOST", "GRAB", "TNG", "SHOPPY" };

		Utils util = new Utils();
		String cardBrand = "";

		String submerchantMid = merchantData.getMid() != null ? merchantData.getMid().getSubMerchantMID() : "";
		logger.info("Sub mid : " + submerchantMid);
		MDRDetailsBean mdrDetailsBean = new MDRDetailsBean();
		Cards cards = new Cards();
		Ewallet wallet = new Ewallet();
		for (int start = 0; start < a.length; start++) {
			cardBrand = a[start];
			logger.info("cardBrand :" + cardBrand);
			MobiMDR mobimdr = subMerchantRegistrationDao.loadMdrList(submerchantMid, cardBrand);
			if (mobimdr != null) {
				mdrDetailsBean = util.mdrListViewinOperationParent(mobimdr, mdrDetailsBean, cards, wallet);
				logger.info("Mdr details Bean  :" + mdrDetailsBean.toString());
			} else {
				logger.warn("No MDR data found for card brand: " + cardBrand);
			}
		}

		// Load payout Mdr
		logger.info("merchantData to string  : " + merchantData.toString());
		PayoutMdr payoutmdr = subMerchantRegistrationDao.loadPayoutMdr(merchantData);

		if (payoutmdr != null) {
			mdrDetailsBean = util.payoutmdrListViewinOperationParent(mdrDetailsBean, payoutmdr);
		} else {
			Payout payout = new Payout();
			payout.setMerchantmdr("0.0");
			payout.setHostmdr("0.0");
			payout.setMobimdr("0.0");
			mdrDetailsBean.setPayout(payout);
			logger.info("Payout Mdr is null");
		}

		logger.info("Tostring  :" + mdrDetailsBean.toString());

		return mdrDetailsBean;
	}
	public List<OperationParentDataBean> datasOperationHead(MDRDetailsBean mobiversaMdr, Merchant merchantData,
			List<OperationParentDataBean> operationParentDataList) {
		OperationParentDataBean operationParentData = new OperationParentDataBean();
//		List<OperationParentDataBean> operationParentDataList = new ArrayList<>();

		operationParentData.setBussinessName(merchantData.getBusinessName());
		operationParentData.setCountry(merchantData.getCountry());
		operationParentData
				.setDate(merchantData.getCreatedDate() != null ? merchantData.getCreatedDate().toString() : "");
		operationParentData.setMid(merchantData.getMid() != null ? merchantData.getMid().getSubMerchantMID() : "");
		operationParentData.setNatureOfBusiness(merchantData.getNatureOfBusiness());
		operationParentData.setWebsite(merchantData.getWebsite());
		operationParentData.setMmId(merchantData.getMmId());
		operationParentData.setEmail(merchantData.getEmail() != null ? merchantData.getEmail() : "");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String json = null;
		try {
			json = objectMapper.writeValueAsString(mobiversaMdr);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		operationParentData.setMdrdetailsBeanJson(json);
		operationParentData.setMdrdetailsBean(mobiversaMdr);

		logger.info("Operation Data : " + operationParentData.toString());
		operationParentDataList.add(operationParentData);
		return operationParentDataList;
	}

}
