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
import com.mobiversa.payment.controller.bean.PaginationBean;

@Repository
@Transactional(readOnly = false)
public class NonMerchantWebDaoImpl extends BaseDAOImpl implements NonMerchantWebDao {
	@Autowired
	protected SessionFactory sessionFactory;
	protected final Logger logger=Logger.getLogger(NonMerchantWebDaoImpl.class.getName());
	@Override
	public Merchant findByUserName(final String username)
	{
		logger.info("Loading Non_merchant based on userName :"+username);
		Session session = sessionFactory.getCurrentSession();
		
		List users = session.createQuery("from Merchant where username=:username").setParameter("username", username)
				.setMaxResults(1).list();
		

		if (users.size() > 0) {
			return (Merchant) users.get(0);
		} else {
			logger.info("return null");
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public Merchant loadMerchantByID(final long id) {
		logger.info("loading non_merchant by id :"+id);
		Session session = sessionFactory.getCurrentSession();
		List users = session.createQuery("from Merchant where id=:id").setParameter("id", id).setMaxResults(1).list();

		if (users.size() > 0) {
			return (Merchant) users.get(0);
		} else {
			return null;
		}

	}

	@Override
	public void listNonMerchantUser(final PaginationBean<Merchant> paginationBean,
			final ArrayList<Criterion> props) {
		super.getPaginationItemsByPage(paginationBean, Merchant.class, props, Order.asc("id"));

	}

	
//	public void listNonMerchantUser(final PaginationBean<NonMerchant> paginationBean, final ArrayList<Criterion> props) {
//		
//	}


	
}
