package com.mobiversa.payment.dao;

import java.util.ArrayList;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiLiteMerchant;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface MerchantWebDao extends BaseDAO {

	public Merchant findByUserName(final String username);

	void listMerchantUser(PaginationBean<Merchant> paginationBean, ArrayList<Criterion> props);

	Merchant loadMerchantByID(long id);

	MobiLiteMerchant findByMobiliteUserName(String username);

	//SettlementUser findBySettlementUserName(String username);

}
