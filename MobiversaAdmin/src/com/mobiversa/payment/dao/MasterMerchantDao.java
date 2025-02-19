package com.mobiversa.payment.dao;


import java.util.ArrayList;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.MasterMerchant;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface MasterMerchantDao extends BaseDAO {


	public MasterMerchant loadMMbyMailId(String email);

	void listMasterMerchantUser(PaginationBean<MasterMerchant> paginationBean, ArrayList<Criterion> props);

	MasterMerchant loadMasterMerchantbyId(Long id);
	
	
	
	
	
}
