package com.mobiversa.payment.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobiversa.common.bo.Batch;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.Settlement;
import com.mobiversa.common.bo.Transaction;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.MobileUserDao;
import com.mobiversa.payment.dao.SettlementsDao;

@Service
public class SettlementsService {
	@Autowired
	private SettlementsDao settlementsDAO;
	@Autowired
	private MobileUserDao mobileuserDAO;

	public Settlement loadSettlementsByPk(final Long id) {
		Settlement settlements = settlementsDAO.loadEntityByKey(Settlement.class, id);
		if (settlements == null) {
			throw new RuntimeException("Settlements Not found. ID:: " + id);
		}
		return settlements;
	}

	
	public void listSettlements(final PaginationBean<Batch> paginationBean) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		settlementsDAO.listSettlements(paginationBean, criterionList);
	}

	
	public void listSettlement(final PaginationBean<Settlement> paginationBean) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		settlementsDAO.listSettlement(paginationBean, criterionList);
	}

	// list of current logged in merchant settlement list
	
	public void listSettlementsMerchant(final PaginationBean<Batch> paginationBean, final Merchant currentMerchant) {

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (currentMerchant != null) {
			@SuppressWarnings("rawtypes")
			List allTIDSUsers = mobileuserDAO.listTIDUsers(currentMerchant);
			if (allTIDSUsers != null) {
				criterionList.add(Restrictions.in("tid", allTIDSUsers));
			}
		}
		settlementsDAO.listMerchantSettlements(paginationBean, criterionList);
	}

	// loading batch by id
	public Batch loadBatchByPk(final Long id) {
		Batch batch = settlementsDAO.loadEntityByKey(Batch.class, id);
		if (batch == null) {
			throw new RuntimeException("Batch Not found. ID:: " + id);
		}
		return batch;
	}

	// list of transactions based on view batch details
	
	public void listBatchStatusBased(final PaginationBean<Transaction> paginationBean, final Batch batch) {

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (batch != null) {
			@SuppressWarnings("rawtypes")
			List allBatchDetails = settlementsDAO.listTransactionBatch(batch);
			// List allMobileUsers = mobileuserDAO.listMobileUsers(merchant);
			if ((allBatchDetails != null) && (allBatchDetails.size() > 0)) {
				criterionList.add(Restrictions.in("batch", allBatchDetails));

			}
		}

		settlementsDAO.listTransactionBatchUsers(paginationBean, criterionList);
	}

	/*
	 * // to view list of transactions based on mobileuser
	 * 
	 * @javax.transaction.Transactional public void listTransactionTID( final
	 * PaginationBean<Transaction> paginationBean, final Merchant merchant) {
	 * 
	 * ArrayList<Criterion> criterionList = new ArrayList<Criterion>(); if
	 * (merchant != null) {
	 * 
	 * @SuppressWarnings("rawtypes") List allMobileUsers =
	 * mobileuserDAO.listMobileUsers(merchant); if ((allMobileUsers != null) &&
	 * (allMobileUsers.size() > 0)) { criterionList
	 * .add(Restrictions.in("mobileUser", allMobileUsers));
	 * 
	 * } }
	 * 
	 * transactionDAO.listTransactionTIDUsers(paginationBean, criterionList); }
	 */
}
