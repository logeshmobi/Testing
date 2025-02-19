package com.mobiversa.payment.dao;

import java.util.ArrayList;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface SettlementWebDao extends BaseDAO {
	
	
	public void listSettlement(PaginationBean<ForSettlement> paginationBean, ArrayList<Criterion> props, String merchantName);
	
	public String getDeviceIdByTid(String tID);

}
