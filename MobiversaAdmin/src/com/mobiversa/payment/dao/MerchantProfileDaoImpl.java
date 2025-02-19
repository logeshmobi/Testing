package com.mobiversa.payment.dao;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PaginationBean;

@Repository
@Transactional(readOnly = false)
public class MerchantProfileDaoImpl  extends BaseDAOImpl implements MerchantProfileDao {
	
	
	@Autowired
	protected SessionFactory sessionFactory;
	protected final Logger logger=Logger.getLogger(MerchantWebDaoImpl.class.getName());
	

	@Override
	public Merchant loadMerchant(final String username) {
		logger.info("MerchantDaoImpl:loadMerchant");
		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("username", username))
				.setMaxResults(1).uniqueResult();
	}

}
