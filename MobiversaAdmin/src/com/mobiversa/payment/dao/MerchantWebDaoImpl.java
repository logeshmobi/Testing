package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiLiteMerchant;
import com.mobiversa.payment.controller.bean.PaginationBean;

@Repository
@Transactional(readOnly = false)
public class MerchantWebDaoImpl extends BaseDAOImpl implements MerchantWebDao {
	@Autowired
	protected SessionFactory sessionFactory;
	protected final Logger logger = Logger.getLogger(MerchantWebDaoImpl.class.getName());

	@Override
	public Merchant findByUserName(final String username) {

		logger.info("Username Find Data in merchant table  ");

		logger.info("username: " + username);
		logger.info("Loading merchant based on userName :" + username);
		Session session = sessionFactory.getCurrentSession();

		List users = session.createQuery("from Merchant where username=:username and status in ('ACTIVE','SUSPENDED')")
				.setParameter("username", username).setMaxResults(1).list();

		if (users.size() > 0) {
			logger.info("merchant is not null");
			return (Merchant) users.get(0);
		} else {
			logger.info("merchant return null");
			return null;
		}
	}

	@Override
	public MobiLiteMerchant findByMobiliteUserName(final String username) {
		logger.info("username: " + username);
		logger.info("Loading mobilite merchant based on userName :" + username);
		Session session = sessionFactory.getCurrentSession();

		List users = session.createQuery("from MobiLiteMerchant where username=:username and status ='ACTIVE'")
				.setParameter("username", username).setMaxResults(1).list();

		if (users.size() > 0) {
			return (MobiLiteMerchant) users.get(0);
		} else {
			logger.info("MobiLiteMerchant return null");
			return null;
		}
	}

	/*
	 * @Override public SettlementUser findBySettlementUserName(final String
	 * username) { logger.info("username: "+username);
	 * logger.info("Loading SettlementUser based on userName :"+username); Session
	 * session = sessionFactory.getCurrentSession();
	 * 
	 * List users = session.
	 * createQuery("from SettlementUser where username=:username and status ='ACTIVE'"
	 * ).setParameter("username", username) .setMaxResults(1).list();
	 * 
	 * 
	 * if (users.size() > 0) { return (SettlementUser) users.get(0); } else {
	 * logger.info("SettlementUser return null"); return null; } }
	 */

	@Override
	@SuppressWarnings("unchecked")
	public Merchant loadMerchantByID(final long id) {
		logger.info("loading merchant by id :" + id);
		Session session = sessionFactory.getCurrentSession();
		List users = session.createQuery("from Merchant where id=:id").setParameter("id", id).setMaxResults(1).list();

		if (users.size() > 0) {
			return (Merchant) users.get(0);
		} else {
			return null;
		}

	}

	@Override
	public void listMerchantUser(final PaginationBean<Merchant> paginationBean, final ArrayList<Criterion> props) {
		super.getPaginationItemsByPage(paginationBean, Merchant.class, props, Order.asc("id"));

	}

}
