package com.mobiversa.payment.dao;

import java.util.List;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.dto.EditMDRDetailsBean;

public interface EditMDRDetailsDao {

	List<Merchant> loadMerchant();
	
	List<Merchant> loadSubMerchants(Merchant merchant);

	Merchant loadMerchantByID(long id);
	
	List<Merchant> loadAllSubMerchantByMmid(Merchant merchantData);

	List<Merchant> loadSubMerchantByID(List<Long> id);

	EditMDRDetailsBean loadFPXMdrDetails(String mid);

	EditMDRDetailsBean loadGrabMdrDetails(String mid);

	EditMDRDetailsBean loadBoostMdrDetails(String mid);

	EditMDRDetailsBean loadTngMdrDetails(String mid);

	EditMDRDetailsBean loadSppMdrDetails(String mid);

	EditMDRDetailsBean loadM1PayMdrDetails(String mid);

	EditMDRDetailsBean loadUMEzywayMdrDetails(String mid);

	EditMDRDetailsBean loadUMEzyMotoMdrDetails(String mid);

	EditMDRDetailsBean loadPayoutMdrDetails(String merchantId);

	int updateFPXMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid);

	int updateGrabMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid);

	int updateBoostMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid);

	int updateTngMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid);

	int updateSppMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid);

	int updateM1PayMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid);

	int updateUMEzywayMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid);

	int updateUMEzyMotoMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid);
	
	int updateFiuuMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid);

	int updatePayoutMdrDetails(EditMDRDetailsBean updateMdrDetails, String merchantId);
	
	int updateVisaCardMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid);

	int updateMasterCardMdrDetails(EditMDRDetailsBean updateMdrDetails, String mid);

	int updateUnionPayMdrDetails(EditMDRDetailsBean updateMdrDetails, String merchantId);	
}