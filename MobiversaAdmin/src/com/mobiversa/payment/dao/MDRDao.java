package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.BizAppSettlement;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiMDR;
import com.mobiversa.common.bo.MobiProductMDR;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.common.bo.UMMidTxnLimit;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.RegMobileUser;

public interface MDRDao extends BaseDAO  {
	


//	public List<MobiMDR> getMDRDetailsByMid(PaginationBean<MobiMDR> paginationBean,List<String> midDetails);
	public List<MobiMDR> getMDRDetailsByMid(MID mid);


	public MobiMDR loadCardAndMDRDetails(String mid);

	public MobiMDR loadMDRById(String mid,String brand);

	public  List<SettlementMDR> listAllSettlementMDR();
	
	public  void listSettlementMDRById(PaginationBean<SettlementMDR> paginationBean, ArrayList<Criterion> criterionList);


	public void listproMDRByMid(PaginationBean<MobiProductMDR> paginationBean, ArrayList<Criterion> criterionList);


	public MobiProductMDR loadProMDR(String mid, String prodType);


	public MobiProductMDR loadProMDR(String string);


	public void exportSettlementMDRByAdmin(PaginationBean<SettlementMDR> paginationBean,
			ArrayList<Criterion> criterionList, MID mid, String string);


	public void listChargeBackByMid(PaginationBean<MobiProductMDR> paginationBean, ArrayList<Criterion> criterionList);


	public void listAllChargeBack(PaginationBean<RegMobileUser> paginationBean, MID object, String object2);


	public List<SettlementMDR> listMIDByDate(String date);


	List<SettlementMDR> listAllMobiliteSettlementMDR();


	public void exportMobiliteSettlementMDRByAdmin(PaginationBean<SettlementMDR> paginationBean,
			ArrayList<Criterion> criterionList, MID mid, String date);


	void listBizappSettlementMDRById(PaginationBean<BizAppSettlement> paginationBean,
			ArrayList<Criterion> criterionList);


	List<BizAppSettlement> listBizAppSettlementMDR();


	public void exportbizappSettlementMDRByAdmin(PaginationBean<BizAppSettlement> paginationBean,
			ArrayList<Criterion> criterionList, String date);


	void listAllFpxSettlementMDR(PaginationBean<FpxTransaction> paginationBean, String from);


	public void exportFpxSettlementMDRByAdmin(PaginationBean<FpxTransaction> paginationBean,
			ArrayList<Criterion> criterionList, MID mid, String date);


	void listFpxSettlementMDRByMid(PaginationBean<FpxTransaction> paginationBean, Merchant merchant, String date);


	//List<MdrRates> getMDRDetailsByRateId(StringBuffer midList);


	int getMDRValuesCount(String mid);


	List<MobiMDR> getMobiMDRDetails(String mid);


	List<MobiMDR> getMDRDetailsByRateId(StringBuffer midList);


	public UMMidTxnLimit Adddtl(RegMobileUser entity);
	

}
