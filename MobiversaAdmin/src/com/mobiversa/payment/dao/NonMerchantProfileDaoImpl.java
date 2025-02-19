package com.mobiversa.payment.dao;


import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = false)
public class NonMerchantProfileDaoImpl  extends BaseDAOImpl implements NonMerchantProfileDao {
	
	
	@Autowired
	protected SessionFactory sessionFactory;
	protected final Logger logger=Logger.getLogger(MerchantWebDaoImpl.class.getName());
	

	/*@Override
	public NonMerchant loadNonMerchant(final String username) {
		logger.info("NonMerchantDaoImpl:loadNonMerchant");
		return (NonMerchant) getSessionFactory().createCriteria(NonMerchant.class).add(Restrictions.eq("username", username))
				.setMaxResults(1).uniqueResult();
	}
*/

	

}
