package com.mobiversa.payment.dao;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.MasterMerchant;
import com.mobiversa.payment.controller.bean.PaginationBean;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MasterMerchantDaoImpl extends BaseDAOImpl implements MasterMerchantDao {
	protected static final Logger logger=Logger.getLogger(MasterMerchantDaoImpl.class.getName());
	
	
	
	@Override
	public MasterMerchant loadMMbyMailId(String email) {
		return (MasterMerchant) getSessionFactory().createCriteria(MasterMerchant.class).add(Restrictions.eq("username", email))
				.setMaxResults(1).uniqueResult();
		
	}
	
	
	@Override
	@Transactional(readOnly = true)
	public void listMasterMerchantUser(final PaginationBean<MasterMerchant> paginationBean, final ArrayList<Criterion> props) {
		logger.info("listMasterMerchantUser : about to list all MasterMerchant");
		super.getPaginationItemsByPage(paginationBean, MasterMerchant.class, props,Order.desc("activateDate"));
		

	}
	
	
	@Override
	public MasterMerchant loadMasterMerchantbyId(Long id) {
		return (MasterMerchant) getSessionFactory().createCriteria(MasterMerchant.class).add(Restrictions.eq("id", id))
				.setMaxResults(1).uniqueResult();
		
	}

}
