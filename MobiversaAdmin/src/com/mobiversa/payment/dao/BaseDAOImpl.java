package com.mobiversa.payment.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.util.PayoutModel;

public abstract class BaseDAOImpl implements BaseDAO {

	@Autowired
	protected SessionFactory sessionFactory;

	protected static final Logger logger = Logger.getLogger(BaseDAOImpl.class.getName());

	@Override
	@Transactional(readOnly = false)
	public <E> E saveOrUpdateEntity(final E entity) {
		logger.info("daoimple" + entity);
		logger.info("This is *************************");
		this.getSessionFactory().saveOrUpdate(entity);
		logger.info("*********************save or update " + entity);
		return entity;
	}

	@Override
	@Transactional(readOnly = false)
	public void saveOrUpdateEntities(final Iterable<? extends Object> entities) {
		for (Object e : entities) {
			this.getSessionFactory().saveOrUpdate(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public <E> E loadEntityByKey(final Class<E> clazz, final Long key) {
		return (E) this.getSessionFactory().get(clazz, key);
	}

	/**
	 * Get the current session Factory from Transaction Manager
	 * 
	 * @return the current session from Session Factory
	 */
	protected Session getSessionFactory() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public void getPaginationItemsByPage(final PaginationBean paginationBean,
			@SuppressWarnings("rawtypes") final Class clazz, final ArrayList<Criterion> props, final Order order) {
		Criteria crit = this.getSessionFactory().createCriteria(clazz).setMaxResults(paginationBean.getItemsPerPage())
				.setFirstResult(paginationBean.getStartIndex());
		if (order != null) {
			crit = crit.addOrder(order);
		}
		if ((props != null) && (props.size() > 0)) {
			for (Criterion prop : props) {
				crit.add(prop);
			}
		}

		@SuppressWarnings("rawtypes")
		List itemList = crit.list();// list data
		// do count rows
		Criteria crit2 = getSessionFactory().createCriteria(clazz).setProjection(Projections.count("id"));
		if ((props != null) && (props.size() > 0)) {
			for (Criterion prop : props) {
				crit2.add(prop);
			}
		}
		Object uniqueResult = crit2.uniqueResult();
		Long totalRowCount = Long.parseLong("" + (uniqueResult == null ? 0 : uniqueResult));
		paginationBean.setItemList(itemList);
		paginationBean.setTotalRowCount(totalRowCount);
		
		logger.info("Item List: " + paginationBean.getItemList());
		logger.info("Total Row Count: " + paginationBean.getTotalRowCount());

	}

	
	//new method for add merchant 05/07/2016
	
	@Override
	@Transactional(readOnly = false)
	public <E> E saveOrUpdateEntity1(final E entity) {
		logger.info("daoimple" + entity);
	
		this.getSessionFactory().saveOrUpdate(entity);
		
		logger.info("*********************save or update mobileuser" + entity);
		return entity;
	}

	public Agent loadAgentDetailsbyId(BigInteger id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void payoutTxnList(PaginationBean<PayoutModel> paginationBean, ArrayList<Criterion> criterionList,
			String date, String date1) {
		// TODO Auto-generated method stub
		
	}

	
	}

