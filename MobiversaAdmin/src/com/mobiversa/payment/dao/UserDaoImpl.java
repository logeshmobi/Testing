package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.BankUser;
import com.mobiversa.common.bo.BankUserStatusHistory;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PaginationBean;

@Component
@Repository
@Transactional(readOnly = false)
public class UserDaoImpl extends BaseDAOImpl implements UserDao {
protected static final Logger logger=Logger.getLogger(UserDaoImpl.class.getName());
	@Autowired
	protected SessionFactory sessionFactory;

	@Override
	@SuppressWarnings("unchecked")
	public void findByUserNames(final String username, final PaginationBean<BankUser> paginationBean) {
		// CHANGE INTERFACE
		Session session = sessionFactory.getCurrentSession();
		List users = session
		/*
		 * .createQuery( "from BankUser where lower(username) like '%test%'")
		 */
		/* .createQuery("from BankUser where username=:username") */

		.createQuery("from BankUser where username LIKE :username").setParameter("username", "%" + username + "%")
				.setMaxResults(paginationBean.getItemsPerPage()).setFirstResult(paginationBean.getStartIndex()).list();

		paginationBean.setItemList(users);

	}

	@Override
	public BankUser findByUserName(final String username) {

		Session session = sessionFactory.getCurrentSession();

		List users = session.createQuery("from BankUser where username=:username").setParameter("username", username)
				.setMaxResults(1).list();

		if (users.size() > 0) {
			logger.info(" bank user Size greater than zero");
			return (BankUser) users.get(0);
		} else {
			logger.info("bank user is null");
			return null;
		}
	}

	@Override
	public BankUser loadBankUserByID(final long id) {

		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("rawtypes")
		List users = session.createQuery("from BankUser where id=:id").setParameter("id", id).setMaxResults(1).list();

		if (users.size() > 0) {
			return (BankUser) users.get(0);
		} else {
			return null;
		}

	}
	
	@Override
	@Transactional(readOnly = true)
	public BankUser validateAdminEmailId(String emailId) {
		logger.info("validateAgentEmailId ");
		
		return (BankUser) getSessionFactory().createCriteria(BankUser.class).add(Restrictions.eq("email", emailId))
				.setMaxResults(1).uniqueResult();
		
	}

	@Override
	public void listBankUser(final PaginationBean<BankUser> paginationBean, final ArrayList<Criterion> props) {
		super.getPaginationItemsByPage(paginationBean, BankUser.class, props, Order.asc("id"));

	}

	@Override
	@Transactional(readOnly = false)
	public void updateBankUserStatus(final Long id, final CommonStatus status, final BankUserStatusHistory history) {

		getSessionFactory().save(history);

		//String query = "update " + BankUser.class.getName() + " c set c.status =:status where id =:id";
		String query = "update BankUser c set c.status =:status where id =:id";
		int updatedEntities = super.getSessionFactory().createQuery(query).setParameter("status", status)
				.setLong("id", id).executeUpdate();
		if (updatedEntities != 1) {
			throw new RuntimeException(
					"Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
							+ updatedEntities);
		}
		// auto commit

	}

	/* dao impl for retrieve bank user status history */
	@Override
	public BankUserStatusHistory loadBankUserStatusHistoryID(final BankUser bankUser) {

		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("rawtypes")
		BankUserStatusHistory history = (BankUserStatusHistory) session
				.createQuery("from BankUserStatusHistory where bankUser=:bankUser order by ID desc")
				.setParameter("bankUser", bankUser).setMaxResults(1).uniqueResult();

		return history;

	}
	
	@Override
	
	public BankUser loadBankUser(String username) 
	{
		
		return (BankUser) getSessionFactory().createCriteria(BankUser.class).add(Restrictions.eq("username", username))
				.setMaxResults(1).uniqueResult();
	}
	
@Override
	
	public Merchant loadadmmerchant(String username) 
	{
		System.out.println("loadadmmerchant" + username);
		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("username", username))
				.setMaxResults(1).uniqueResult();
	}
	
@Override
@Transactional(readOnly = false)
public int changeAdminMerchPassWord(String Username, String password, String oldPwd) {
	String query = "update MERCHANT c set c.password =:password where username =:userName";
	int rs = sessionFactory.openSession().createQuery(query).setParameter("password", password)
			.setParameter("password1", oldPwd).setParameter("userName", Username).executeUpdate();
	return rs;
}


	@Override
	@Transactional(readOnly = false)
	public int changeUserPassWord(String Username, String newPwd, String oldPwd, String oldPwd1) {
		String query = "update BankUser c set c.password =:password,c.password1 =:password1,"
				+ "c.password2 =:password2  where username =:userName";
		int rs = sessionFactory.openSession().createQuery(query).setParameter("password", newPwd)
				.setParameter("password1", oldPwd).setParameter("password2", oldPwd1)
				.setParameter("userName", Username).executeUpdate();
		return rs;
	}
	
	/*@Override
	@Transactional(readOnly = false)
	public int changeUserPassWord(String Username, String newPwd, String OldPwd) {
		String query = "update BankUser c set c.password =:password where username =:userName";
		int rs = sessionFactory.openSession().createQuery(query).setParameter("password", newPwd)
				.setParameter("userName", Username).executeUpdate();
		return rs;
	}*/

	public List<String> getValidUsername(){
		try {
			
			String unionQuery = "SELECT USERNAME FROM mobiversa.MERCHANT WHERE STATUS = 'ACTIVE' " +
					"UNION " +
					"SELECT USERNAME FROM mobiversa.AGENT WHERE STATUS = 'ACTIVE' " +
					"UNION " +
					"SELECT USERNAME FROM mobiversa.BANK_USER WHERE STATUS = 'ACTIVE'";			
			
//			String unionQuery = "SELECT USERNAME FROM mobiversa.MERCHANT WHERE STATUS = 'ACTIVE' " +
//					"UNION " +
//					"SELECT USERNAME FROM mobiversa.AGENT WHERE STATUS = 'ACTIVE' " +
//					"UNION " +
//					"SELECT USERNAME FROM mobiversa.BANK_USER WHERE STATUS = 'ACTIVE'";

			List<String> allUsernames = sessionFactory.getCurrentSession()
					.createSQLQuery(unionQuery)
					.list();

			return allUsernames;

		} catch (HibernateException e) {
			logger.info("exception :"+e+" "+e.getMessage());
			throw new RuntimeException(e);

		}
	}

}
