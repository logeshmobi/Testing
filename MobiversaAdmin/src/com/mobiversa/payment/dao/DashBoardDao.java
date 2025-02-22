package com.mobiversa.payment.dao;

import java.util.List;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiLiteMerchant;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.DashBoardData;
import com.mobiversa.payment.dto.SixMonthTxnData;
import com.mobiversa.payment.dto.ThreeMonthTxnData;
import com.mobiversa.payment.dto.TopFiveMerchant;

public interface DashBoardDao extends BaseDAO {
	
	public List<TopFiveMerchant> getTopFiveMerchantDet();
	
	public List<TopFiveMerchant> getAgentWiseDet();
	
	public List<DashBoardData> getTxnVolumeCount();
	
	public List<DashBoardData> getTopMerchant();
	
	public List<DashBoardData> getTxnStateWise();
	
	public List<DashBoardData> getMerchantDevice();
	
	public List<DashBoardData> getDeviceTxnList();
	
	//new Changes
	public int getTotalDevice();
	
	public String getCurrentMonthTxn();
	
	public void getLastFiveTxn(PaginationBean<DashBoardData> paginationBean);
	
	public String getTotalMerchant();
	
	// New Changes  - Merchant DashBoard
	public int getMerchantTotalDevice(String mid);
	
	
	
	
	// New Changes  - Agent DashBoard
	public int getAgentTotalDevice(long id);
	
	public String getAgentTotalMerchant(long id);
	
	public String getAgentCurrentMonthTxn(long id);
	
	
	
	public List<ThreeMonthTxnData> getThreeMonthTxn();
	
	
	
	
	public List<ThreeMonthTxnData> getAgentTxnCount(long id);

	public int getMerchantTotalDeviceCount(String mid);

	public String getnonMerchantCurrentMonthTxn(String mid);

	public List<ThreeMonthTxnData> getnonMerchantTxnCount(String mid);
	
	public List<String> loadNobList();

	
	//public String getMerchantCurrentMonthTxn(String mid,String moto_mid);
	public String getMerchantCurrentMonthTxn(Merchant merchant);
	

	//public List<ThreeMonthTxnData> getMerchantTxnCount(String mid,String motoMid);
	public List<ThreeMonthTxnData> getMerchantTxnCount(Merchant merchant);

	public int getMerchantTotalDeviceCount(Merchant merchant);

	public void getLastHundredTxn(PaginationBean<DashBoardData> txnPaginationBean);

	public void getLastHundredTxnUmobile(PaginationBean<DashBoardData> txnPaginationBean);

	

	

	public String getMerchantDailyTxn(Merchant merchant);

	public String getMerchantWeeklyTxn(Merchant merchant);

	String getAgentCurrentMonthTxnByAgID(long id);

	List<ThreeMonthTxnData> getAgentTxnCountByAgID(long id);

	void getAgentLastFiveTxn(PaginationBean<DashBoardData> paginationBean, StringBuffer pMid);

	void getAgentLastHundredTxn(PaginationBean<DashBoardData> paginationBean, StringBuffer pMid, StringBuffer uMid);

	void getAgentLastHundredUmTxn(PaginationBean<DashBoardData> paginationBean, StringBuffer pMid, StringBuffer uMid);

	List<SixMonthTxnData> getMerchantSixMonTxn(Merchant merchant);

	public String getAgentMerchantMonthTxn(StringBuffer str);

	public void getHotelMerchantFiveTxn(PaginationBean<DashBoardData> paginationBean, StringBuffer pMid,
			StringBuffer uMid);

	void getMerchantLastFiveTxn(PaginationBean<DashBoardData> paginationBean, Merchant merchant, String ezypodcheck, String ezylinkCheck);

	void getMerchantHundredTxn(PaginationBean<DashBoardData> paginationBean, Merchant merchant, String ezypodcheck, String ezyLinkCheck);

	public List<SixMonthTxnData> getHotelMerchantSixMonTxn(long id, StringBuffer strum);

	public void getHotelMerchantLastHundredUmTxn(PaginationBean<DashBoardData> txnPaginationBean, StringBuffer pMid,
			StringBuffer uMid);

	String getToypayCurrentMonthTxnByAgID(long id);

	void getToypayMerchantFiveTxn(PaginationBean<DashBoardData> paginationBean, StringBuffer pMid, StringBuffer uMid);

	List<SixMonthTxnData> getToypayMerchantSixMonTxn(long id, StringBuffer uMid);

	void getToypayMerchantLastHundredUmTxn(PaginationBean<DashBoardData> paginationBean, StringBuffer pMid,
			StringBuffer uMid);

	String getMobiliteMerchantCurrentMonthTxn(MobiLiteMerchant merchant);

	int getMobiliteMerchantTotalDevice(String mid);

	String getMobiliteMerchantDailyTxn(MobiLiteMerchant merchant);

	String getMobiliteMerchantWeeklyTxn(MobiLiteMerchant merchant);

	void getMobiLiteMerchantLastFiveTxn(PaginationBean<DashBoardData> paginationBean, MobiLiteMerchant merchant);

	List<SixMonthTxnData> getMobiLiteMerchantSixMonTxn(MobiLiteMerchant merchant);

	int getMobiliteMerchantTotalDeviceCount(MobiLiteMerchant merchant);

	void getMobiliteMerchantHundredTxn(PaginationBean<DashBoardData> paginationBean, MobiLiteMerchant merchant);

	

	
}
