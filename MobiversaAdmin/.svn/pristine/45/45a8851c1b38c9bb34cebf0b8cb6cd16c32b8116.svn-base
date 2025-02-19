package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.Batch;
import com.mobiversa.common.bo.Settlement;
import com.mobiversa.common.bo.SettlementStatus;
import com.mobiversa.common.bo.Transaction;
import com.mobiversa.payment.controller.bean.PaginationBean;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class SettlementsDaoImpl extends BaseDAOImpl implements SettlementsDao {

	@Override
	@Transactional(readOnly = true)
	public void listSettlements(final PaginationBean<Batch> paginationBean, final ArrayList<Criterion> props) {
		super.getPaginationItemsByPage(paginationBean, Batch.class, props, Order.asc("id"));
	}

	@Override
	public void listSettlement(final PaginationBean<Settlement> paginationBean, final ArrayList<Criterion> props) {
		super.getPaginationItemsByPage(paginationBean, Settlement.class, props, Order.asc("id"));
	}

	@Override
	@Transactional(readOnly = true)
	public void listMerchantSettlements(final PaginationBean<Batch> paginationBean, final ArrayList<Criterion> tid) {
		super.getPaginationItemsByPage(paginationBean, Batch.class, tid, Order.asc("id"));
	}

	// list of transaction based on view batch details
	@Override
	@Transactional(readOnly = true)
	public void listTransactionBatchUsers(final PaginationBean<Transaction> paginationBean,
			final ArrayList<Criterion> batch) {
		super.getPaginationItemsByPage(paginationBean, Transaction.class, batch, Order.asc("id"));
	}

	@Override
	public List listTransactionBatch(final Batch batch) {
		if (batch == null) {
			throw new IllegalArgumentException("sorry, merchant var cannot be null");
		}
		return sessionFactory.getCurrentSession()
				.createQuery("from Batch where c.status=:successful")
				.setParameter("successful", SettlementStatus.SUCCESSFUL).list();
	}
}