package com.mobiversa.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiLiteMerchant;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.DashBoardDao;
import com.mobiversa.payment.dto.DashBoardData;
import com.mobiversa.payment.dto.SixMonthTxnData;
import com.mobiversa.payment.dto.ThreeMonthTxnData;
import com.mobiversa.payment.dto.TopFiveMerchant;


@Service
public class DashBoardService {
	
	@Autowired
	private DashBoardDao dashBoardDao;
	
	
	//@javax.transaction.Transactional
	public List<TopFiveMerchant> listTopFiveMerchantDetails() {
		//System.out.println("Inside service to get top five merchant");
		
		return dashBoardDao.getTopFiveMerchantDet();
	}
	
	//@javax.transaction.Transactional
	public List<TopFiveMerchant> listAgentWiseDetails() {
		//System.out.println("Inside service to get top five merchant");
		
		return dashBoardDao.getAgentWiseDet();
	}
	
	
	
	
	
	//@javax.transaction.Transactional
	public List<DashBoardData> listTxnVolCnt() {
		//System.out.println("Inside service to get Txn Vol count");
		
		return dashBoardDao.getTxnVolumeCount();
	}
	
	//@javax.transaction.Transactional
	public List<DashBoardData> listMerchantWiseDet() {
		//System.out.println("Inside service to get top five merchant");
		
		return dashBoardDao.getTopMerchant();
	}
	//@javax.transaction.Transactional
	public List<DashBoardData> listTxnStateDetails() {
		//System.out.println("Inside service to get state wise Txn");
		
		return dashBoardDao.getTxnStateWise();
	}
	
	//@javax.transaction.Transactional
	public List<DashBoardData> listMerchantDeviceDet() {
		//System.out.println("Inside service to get merchant and device");
		
		return dashBoardDao.getMerchantDevice();
	}
	
	//@javax.transaction.Transactional
	public List<DashBoardData> listDeviceTxnDet() {
		//System.out.println("Inside service to get device and txn");
		
		return dashBoardDao.getDeviceTxnList();
	}
	
	
	//New change
	
	//@javax.transaction.Transactional
	public int getTotalDevice() {
		//System.out.println("Inside getTotalDevice");
		
		return dashBoardDao.getTotalDevice();
	}
	
	//@javax.transaction.Transactional
	public String getCurrentMonthTxn() {
		//System.out.println("Inside getCurrentMonthTxn");
		
		return dashBoardDao.getCurrentMonthTxn();
	}
	
	public void getLastFiveTxn(PaginationBean<DashBoardData> paginationBean) {
		//System.out.println("Inside getCurrentMonthTxn");
		
		dashBoardDao.getLastFiveTxn(paginationBean);
	}
	
	//@javax.transaction.Transactional
	public String getTotalMerchant() {
		//System.out.println("Inside getTotalMerchant");
		
		return dashBoardDao.getTotalMerchant();
	}
	
	// New Change - Merchant DashBoard
	
	//@javax.transaction.Transactional
	public int getMerchantTotalDevice(String mid) {
		//System.out.println("Inside getMerchantTotalDevice");
		
		return dashBoardDao.getMerchantTotalDevice(mid);
	}
	
	
	public int getMobiliteMerchantTotalDevice(String tid) {
		//System.out.println("Inside getMerchantTotalDevice");
		
		return dashBoardDao.getMobiliteMerchantTotalDevice(tid);
	}
	
	
	//@javax.transaction.Transactional
	/*public String getMerchantCurrentMonthTxndup(String mid,String motoMid) {
		
		
		//return dashBoardDao.getMerchantCurrentMonthTxn(mid,motoMid);
	}
	*/
	public String getMerchantCurrentMonthTxn(Merchant merchant) {
		//System.out.println("Inside getMerchantCurrentMonthTxn");
		
		return dashBoardDao.getMerchantCurrentMonthTxn(merchant);
	}
	
	public String getMobiliteMerchantCurrentMonthTxn(MobiLiteMerchant merchant) {
		//System.out.println("Inside getMerchantCurrentMonthTxn");
		
		return dashBoardDao.getMobiliteMerchantCurrentMonthTxn(merchant);
	}

	//@javax.transaction.Transactional
	public String getnonMerchantCurrentMonthTxn(String mid) {
		//System.out.println("Inside getMerchantCurrentMonthTxn");
		
		return dashBoardDao.getnonMerchantCurrentMonthTxn(mid);
	}

/*	//@javax.transaction.Transactional
	public int getMerchantTotalDeviceCount(String mid) {
		//System.out.println("Inside getMerchantTotalDeviceCount");
		
		return dashBoardDao.getMerchantTotalDeviceCount(mid);
	}*/
	
	
	//@javax.transaction.Transactional
		public int getMerchantTotalDeviceCount(Merchant merchant) {
			//System.out.println("Inside getMerchantTotalDeviceCount");
			
			return dashBoardDao.getMerchantTotalDeviceCount(merchant);
		}
	
		
		public int getMobiLiteMerchantTotalDeviceCount(MobiLiteMerchant merchant) {
			//System.out.println("Inside getMerchantTotalDeviceCount");
			
			return dashBoardDao.getMobiliteMerchantTotalDeviceCount(merchant);
		}
	
	
	//New Change  - Agent Dashboard
	
	//@javax.transaction.Transactional
	public int getAgentTotalDevice(long id) {
		//System.out.println("Inside getAgentTotalDevice");
		
		return dashBoardDao.getAgentTotalDevice(id);
	}
	
	//@javax.transaction.Transactional
	public String getAgentCurrentMonthTxn(long id) {
		//System.out.println("Inside getAgentCurrentMonthTxn");
		
		return dashBoardDao.getAgentCurrentMonthTxn(id);
	}
	
	public String getAgentMerchantMonthTxn(StringBuffer str) {
		//System.out.println("Inside getAgentCurrentMonthTxn");
		
		return dashBoardDao.getAgentMerchantMonthTxn(str);
	}
	
	//@javax.transaction.Transactional
	public String getAgentTotalMerchant(long id) {
		//System.out.println("Inside getAgentTotalMerchant");
		
		return dashBoardDao.getAgentTotalMerchant(id);
	}

	
	
	
//Admin dashboard new ui 14072017
	//@javax.transaction.Transactional
	public List<ThreeMonthTxnData> getThreeMonthTxn() {
		//System.out.println("Inside getCurrentMonthTxn");
		
		return dashBoardDao.getThreeMonthTxn();
	}
	
	
	
//merchant dashboard new UI14072017
	//@javax.transaction.Transactional
	/*public List<ThreeMonthTxnData> getMerchantTxnCount(String mid,String motoMid) {
		//System.out.println("Inside getCurrentMonthTxn");
		
		return dashBoardDao.getMerchantTxnCount(mid,motoMid);
	}*/
	public List<ThreeMonthTxnData> getMerchantTxnCount(Merchant merchant) {
		//System.out.println("Inside getCurrentMonthTxn");
		
		return dashBoardDao.getMerchantTxnCount(merchant);
	}
	public List<ThreeMonthTxnData> getnonMerchantTxnCount(String mid) {
		//System.out.println("Inside getCurrentMonthTxn");
		
		return dashBoardDao.getnonMerchantTxnCount(mid);
	}
	
	
	//@javax.transaction.Transactional
		public List<ThreeMonthTxnData> getAgentTxnCount(long id) {
			//System.out.println("Inside getAgentTotalMerchant");
			
			return dashBoardDao.getAgentTxnCount(id);
		}
		
		
		
		public List<String> getNobList() {
			//System.out.println("Inside getAgentTotalMerchant");
			
			return dashBoardDao.loadNobList();
		}

		public void getLastHundredTxn(PaginationBean<DashBoardData> txnPaginationBean) {
			dashBoardDao.getLastHundredTxn(txnPaginationBean);
			
		}
		
		public void getAgentLastHundredTxn(PaginationBean<DashBoardData> txnPaginationBean,StringBuffer pMid,
				StringBuffer uMid) {
			dashBoardDao.getAgentLastHundredTxn(txnPaginationBean,pMid,uMid);
			
		}
		
		public void getHotelMerchantLastHundredUmTxn(PaginationBean<DashBoardData> txnPaginationBean,StringBuffer pMid,
				StringBuffer uMid) {
			dashBoardDao.getHotelMerchantLastHundredUmTxn(txnPaginationBean,pMid,uMid);
			
		}
		
		public void getToypayMerchantLastHundredUmTxn(PaginationBean<DashBoardData> txnPaginationBean,StringBuffer pMid,
				StringBuffer uMid) {
			dashBoardDao.getToypayMerchantLastHundredUmTxn(txnPaginationBean,pMid,uMid);
			
		}
		
		public void getAgentLastHundredUmTxn(PaginationBean<DashBoardData> txnPaginationBean,StringBuffer pMid,
				StringBuffer uMid) {
			dashBoardDao.getAgentLastHundredUmTxn(txnPaginationBean,pMid,uMid);
			
		}



		public void getLastHundredTxnUmobile(PaginationBean<DashBoardData> txnPaginationBean) {
			dashBoardDao.getLastHundredTxnUmobile(txnPaginationBean);
		}

		public void getMerchantLastFiveTxn(PaginationBean<DashBoardData> paginationBean, Merchant merchant, String ezypodCheck,String ezylinkCheck) {
			dashBoardDao.getMerchantLastFiveTxn(paginationBean,merchant,ezypodCheck,ezylinkCheck);
			
		}
		
		
		public void getMobiliteMerchantLastFiveTxn(PaginationBean<DashBoardData> paginationBean, MobiLiteMerchant merchant) {
			dashBoardDao.getMobiLiteMerchantLastFiveTxn(paginationBean,merchant);
			
		}

		public void getMerchantHundredTxn(PaginationBean<DashBoardData> txnPaginationBean, Merchant currentMerchant, String ezypodCheck, String ezyLinkCheck) {
			dashBoardDao.getMerchantHundredTxn(txnPaginationBean,currentMerchant,ezypodCheck,ezyLinkCheck);
			
		}
		
		public void getMobiliteMerchantHundredTxn(PaginationBean<DashBoardData> txnPaginationBean, MobiLiteMerchant currentMerchant) {
			dashBoardDao.getMobiliteMerchantHundredTxn(txnPaginationBean,currentMerchant);
			
		}


		public String getMerchantDailyTxn(Merchant merchant) {
			return dashBoardDao.getMerchantDailyTxn(merchant);
		}
		
		public String getMobiliteMerchantDailyTxn(MobiLiteMerchant merchant) {
			return dashBoardDao.getMobiliteMerchantDailyTxn(merchant);
		}

		public String getMerchantWeeklyTxn(Merchant merchant) {
			
			return dashBoardDao.getMerchantWeeklyTxn(merchant);
		}
		
		public String getMobiliteMerchantWeeklyTxn(MobiLiteMerchant merchant) {
			
			return dashBoardDao.getMobiliteMerchantWeeklyTxn(merchant);
		}
		
		public List<ThreeMonthTxnData> getAgentTxnCountByAgID(long id) {
			//System.out.println("Inside getAgentTotalMerchant");
			
			return dashBoardDao.getAgentTxnCountByAgID(id);
		}
		public String getAgentCurrentMonthTxnByAgID(long id) {
			//System.out.println("Inside getAgentCurrentMonthTxn");
			
			return dashBoardDao.getAgentCurrentMonthTxnByAgID(id);
		}
		
		
		public String getToypayCurrentMonthTxnByAgID(long id) {
			//System.out.println("Inside getAgentCurrentMonthTxn");
			
			return dashBoardDao.getToypayCurrentMonthTxnByAgID(id);
		}
		
		public void getHotelMerchantFiveTxn(PaginationBean<DashBoardData> paginationBean, StringBuffer pMid, StringBuffer uMid) {
			dashBoardDao.getHotelMerchantFiveTxn(paginationBean,pMid,uMid);
			
		}
		
		
		public void getToypayMerchantFiveTxn(PaginationBean<DashBoardData> paginationBean, StringBuffer pMid, StringBuffer uMid) {
			dashBoardDao.getToypayMerchantFiveTxn(paginationBean,pMid,uMid);
			
		}
		
		public void getAgentLastFiveTxn(PaginationBean<DashBoardData> paginationBean, StringBuffer mid) {
			dashBoardDao.getAgentLastFiveTxn(paginationBean,mid);
			
		}
		
		
		public List<SixMonthTxnData> getMerchantSixMonTxn(Merchant merchant) {
			//System.out.println("Inside getCurrentMonthTxn");
			
			return dashBoardDao.getMerchantSixMonTxn(merchant);
		}
		
		
		public List<SixMonthTxnData> getMobiliteMerchantSixMonTxn(MobiLiteMerchant merchant) {
			//System.out.println("Inside getCurrentMonthTxn");
			
			return dashBoardDao.getMobiLiteMerchantSixMonTxn(merchant);
		}
		
		public List<SixMonthTxnData> getHotelMerchantSixMonTxn(long id, StringBuffer strum) {
			//System.out.println("Inside getCurrentMonthTxn");
			
			return dashBoardDao.getHotelMerchantSixMonTxn(id,strum);
		}
		
		public List<SixMonthTxnData> getToypayMerchantSixMonTxn(long id, StringBuffer strum) {
			//System.out.println("Inside getCurrentMonthTxn");
			
			return dashBoardDao.getToypayMerchantSixMonTxn(id,strum);
		}

}
