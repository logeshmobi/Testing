package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.Merchant;
//import com.mobiversa.common.bo.MerchantCustMail;
import com.mobiversa.common.bo.MerchantDetails;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.Promotion;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PaginationBean;

//@Component
@Repository
@Transactional(readOnly = false, rollbackFor = Exception.class)
public class PromotionDaoImpl extends BaseDAOImpl implements PromotionDao {

	@Override
	@Transactional(readOnly = false)
	public void listPromotions(PaginationBean<Promotion> paginationBean, ArrayList<Criterion> props) {

		logger.info("descending order");
		super.getPaginationItemsByPage(paginationBean, Promotion.class, props, Order.desc("id"));

	}

	/*@Override
	public String loadMaxID(String sendType) {

		String query = "select max(pCode) from merchantpromo where sendType ='" + sendType + "'";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(query);// .addEntity(MerchantPromo.class);
		@SuppressWarnings("unchecked")
		List<Object> resultSet = sqlQuery.list();

		String code = null;
		if (resultSet != null) {

			for (Object rs : resultSet) {
				if (rs != null) {
					code = rs.toString();
				} else {
					code = "0";
				}
			}
		} else {
			code = "0";
		}

		//logger.info("Agent Type : " + code);

		return code;
	}*/

	public void listMerchantPromo(PaginationBean<Promotion> paginationBean, ArrayList<Criterion> props) {
		// logger.info("MerchantDaoImpl:listMerchantUser1");
		super.getPaginationItemsByPage(paginationBean, Promotion.class, props, Order.desc("id"));
	}

	// new method for promotion and customerMail upload 22/05/2017
	@Override
	public MerchantDetails loadMerchantPointsallmid(Merchant merchant) {
		Criterion criteria=(Criterion) Restrictions.in("mid", new String[]{merchant.getMid().getMid(),merchant.getMid().getMotoMid(),
				merchant.getMid().getEzypassMid()});
		return (MerchantDetails) getSessionFactory().createCriteria(MerchantDetails.class)
				.add(criteria).setMaxResults(1).uniqueResult();
	}// end
	@Override
	public MerchantDetails loadMerchantPoints(String mid) {
		return (MerchantDetails) getSessionFactory().createCriteria(MerchantDetails.class)
				.add(Restrictions.eq("mid", mid)).setMaxResults(1).uniqueResult();
	}
	
	
	@Override
	public MerchantDetails loadMerchantPointsbyMid(Merchant merchant) {
		Criterion midCriteria = Restrictions.in("mid",new String[]{ merchant.getMid().getMid(),
				merchant.getMid().getMotoMid(),merchant.getMid().getEzypassMid(),
				merchant.getMid().getEzyrecMid()});
		
		/*Disjunction orExp = Restrictions.disjunction();
		if(merchant.getMid().getMid()!=null){
		orExp.add(Restrictions.eq("mid",  merchant.getMid().getMid()));
		}
		if(merchant.getMid().getMotoMid()!=null){
		orExp.add(Restrictions.eq("mid",  merchant.getMid().getMotoMid()));
		}
		if(merchant.getMid().getEzypassMid()!=null){
		orExp.add(Restrictions.eq("mid",  merchant.getMid().getEzypassMid()));
		}*/
		/*Criterion motoMid = Restrictions.like("mid",  );
		Criterion ezypassMid = Restrictions.like("mid",  );*/
		
		
		//Disjunction orExp = Restrictions.or(mid, motoMid,ezypassMid);
		
		return (MerchantDetails) getSessionFactory().createCriteria(MerchantDetails.class)
				.add(midCriteria).setMaxResults(1).uniqueResult();
	}
	
	@Override
	public Merchant loadMerchantByMid(String mid) {

		// TODO Auto-generated method stub
		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.eq("mid", mid))
				.setMaxResults(1).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TerminalDetails> loadTerminalDetails(String mid) {

		return (List<TerminalDetails>) getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.eq("merchantId", mid)).list();

	}

	// push notification for promotion
	@Override
	public MobileUser loadMobileUser(String tid) {

		// TODO Auto-generated method stub
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class).add(Restrictions.eq("tid", tid))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public TerminalDetails loadDeviceID(String deviceId) {

		return (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.eq("deviceId", deviceId)).setMaxResults(1).uniqueResult();
	}

	@Override
	public TerminalDetails loadTid(String tid) {

		return (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.eq("tid", tid)).setMaxResults(1).uniqueResult();
	}

	
	//new method for search condition  11072017
		@Override
		public void listPromotionSearch(PaginationBean<Promotion> paginationBean, ArrayList<Criterion> props) {
			// TODO Auto-generated method stub
			logger.info("check merchant data:");
			super.getPaginationItemsByPage(paginationBean, Promotion.class, props, Order.desc("activateDate"));
		}
	@SuppressWarnings("unchecked")
	@Override
	public List<MobileUser> loadMobileUserDetails(Long id) {

		logger.info("load mobile user details bigint: id "+id);
		return (List<MobileUser>) getSessionFactory().createCriteria(MobileUser.class)
				.add(Restrictions.eq("merchant.id",id)).list();

	}
	
	@Override
	public List<MobileUser> loadMobileUserDetailsbytid(String tid) {

		Disjunction orExp=Restrictions.disjunction();
		orExp.add(Restrictions.eq("tid", tid));
		orExp.add(Restrictions.eq("motoTid", tid));
		orExp.add(Restrictions.eq("ezypassTid", tid));
		logger.info("load mobile user details bigint: id "+tid);
		return (List<MobileUser>) getSessionFactory().createCriteria(MobileUser.class)
				.add(orExp).list();

	}
	
		@Override
		public MobileUser loadMobileUserDetailsByUsername(String username) {
			logger.info("load mobile user details : username "+username);
			return (MobileUser) getSessionFactory().createCriteria(MobileUser.class)
					.add(Restrictions.eq("username",username)).setMaxResults(1).uniqueResult();
			//return null;
		}

		
}
