package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.Batch;
import com.mobiversa.common.bo.Settlement;
import com.mobiversa.common.bo.Transaction;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface SettlementsDao extends BaseDAO {
	// lsit of first page of creditsettlement in adminconsole
	public void listSettlements(PaginationBean<Batch> paginationBean, ArrayList<Criterion> props);

	public void listSettlement(PaginationBean<Settlement> paginationBean, ArrayList<Criterion> props);

	// list of currently logged merchant settlement list
	public void listMerchantSettlements(PaginationBean<Batch> paginationBean, ArrayList<Criterion> props);

	// list of view batch details
	public List listTransactionBatch(Batch batch);

	// list of view batch details
	public void listTransactionBatchUsers(PaginationBean<Transaction> paginationBean, ArrayList<Criterion> props);

}
