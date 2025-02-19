package com.mobiversa.payment.dao;

import java.util.List;
import java.util.Map;

import com.mobiversa.common.bo.MasterMerchant;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantInfo;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.MobileOTP;
import com.mobiversa.common.bo.PayoutMdr;
import com.mobiversa.payment.controller.bean.MDRDetailsBean;
import com.mobiversa.payment.dto.SubmerchantDto;

public interface SubMerchantRegistrationDao extends BaseDAO {

	public List<MerchantInfo> loadApproveSubmerchant();

	public List<MasterMerchant> loadMasterMerchant();

	public List<MerchantInfo> loadsubmerchantInRiskandCompilence(int currentPage);

	public List<MerchantInfo> loadsubmerchantInRiskandCompilenceSize();

	public List<MerchantInfo> loadsubmerchantInRiskandCompilence(String mmid, int currentPage);

	public List<MerchantInfo> loadsubmerchantInRiskandCompilenceSize(String mmid);

	public int updateMerchantStatusAndReason(String merchantAction, String reason, String submerchantBusinessName,
			String descriptionUpdatedBy);

	public List<SubmerchantDto> loadSubmerchantListForOperationChildvalidationWithpagination(int currPage);

	public List<MerchantInfo> loadApprovedMerchant();

	public Merchant findMerchantBusinessName(String businessName);

	public List<SubmerchantDto> loadSubmerchantListForOperationChildvalidationWithpagination(int currPage, String mmID);

	public Map<String, Object> loadSubMerchantApprove(int currPage);
	
	public Map<String, Object> loadMainMerchantUsingMMIDoperationTeam(String mmid,int currPage);
	
	public Merchant loadSubMerchant(String businessName);
	
	public Map<String, Object> loadSubMerchantAfterApproveOperationteam(int currPage);

	public Map<String, Object> loadMerchantsUsingMMIDAndName(String merchantName, int currPage);
	
    public int updateMerchantStatusOperationTeam(String status,String bussinessName);

	public String addPayoutGrandDetailInitial();
	
	public Merchant addsubMerchantDetails(Merchant merchant,String merchantId,Merchant mainMerchant);
	
	public int updateMerchantInfo(Merchant merchantData);
	
	public int addMerchantDetails(Merchant subMerchant, Merchant mainMerchant);
	
	public MobileOTP getReference(Merchant merchant);
	
	public String addMidtable(Merchant merchant, MobileOTP mobileOTP);
	
	public int addmidFk(String id,String merchantName);
	
	public int addPayoutGrandDetail(Merchant subMerchant,String payoutgrandDetailsId);
	
	public int updateMobileOTPDetails(MobileOTP mobileOTP);
	
	public int addMobiversaMdr(MDRDetailsBean mdrDetails);
	
	public int addPayoutMdr(MDRDetailsBean mdrDetails,Merchant merchantData);
	
	public MobiMDR loadMdrList(String mid,String cardBrand);
	
	public PayoutMdr loadPayoutMdr(Merchant merchantData);
	
}
