package com.mobiversa.payment.dao;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.KManager;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiLiteUser;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.MobileUserStatusHistory;
import com.mobiversa.common.bo.TID;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.bo.UMKManager;
import com.mobiversa.common.bo.UMMidTxnLimit;
import com.mobiversa.common.dto.MobileUserDTO;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.MobileUserData;
import com.mobiversa.payment.dto.RegMobileUser;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class MobileUserDaoImpl extends BaseDAOImpl implements MobileUserDao {

	@Override
	@Transactional(readOnly = false)
	public void listMobileUser(final PaginationBean<MobileUser> paginationBean, final ArrayList<Criterion> props) {
		logger.info("check activateDate:" + "activateDate");
		super.getPaginationItemsByPage(paginationBean, MobileUser.class, props, Order.desc("activateDate"));
	}

	@Override
	public TID getTID(final MobileUser mobileUser) {
		TID tid = (TID) super.getSessionFactory().createCriteria(TID.class)
				.add(Restrictions.eq("mobileUser", mobileUser)).setMaxResults(1).uniqueResult();
		return tid;

	}

	@Override
	@Transactional(readOnly = false)
	public void updateMobileUserStatus(final Long id, final CommonStatus status,
			final MobileUserStatusHistory history) {

		getSessionFactory().save(history);

		// String query = "update " + MobileUser.class.getName() + " c set c.status
		// =:status where id =:id";
		String query = "update MobileUser c set c.status =:status where id =:id";
		int updatedEntities = super.getSessionFactory().createQuery(query).setParameter("status", status)
				.setLong("id", id).executeUpdate();
		if (updatedEntities != 1) {
			throw new RuntimeException(
					"Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
							+ updatedEntities);
		}
		// auto commit

	}

	@Override
	@SuppressWarnings("unchecked")
	public void findByUserNames(final String username, final PaginationBean<MobileUserDTO> paginationBean) {
		// CHANGE INTERFACE
		Session session = sessionFactory.getCurrentSession();
		List users = session
				/*
				 * .createQuery( "from BankUser where lower(username) like '%test%'")
				 */
				.createQuery("from MobileUser where username=:username").setParameter("username", username)
				.setMaxResults(paginationBean.getItemsPerPage()).setFirstResult(paginationBean.getStartIndex()).list();
		paginationBean.setItemList(users);

	}

	@Override
	public MobileUserStatusHistory loadMobileStatusHistoryID(final MobileUser mobileUser) {

		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("rawtypes")
		MobileUserStatusHistory history = (MobileUserStatusHistory) session
				.createQuery("from MobileUserStatusHistory where mobileUser=:mobileUser order by ID desc")
				.setParameter("mobileUser", mobileUser).setMaxResults(1).uniqueResult();

		return history;

	}

	@Override
	public List<MobileUser> getMobileUser(final Merchant merchant) {
		return sessionFactory.getCurrentSession().createCriteria(MobileUser.class)
				.add(Restrictions.eq("merchant", merchant)).list();
	}

	@Override
	public MobileUser loadMobileUserBoostandMoto(Long id) {
		logger.info("id: " + id);
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class).add(Restrictions.eq("id", id))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public void merchantBasedMobileUsers(final String username, final PaginationBean<MobileUser> paginationBean) {
		Session session = sessionFactory.getCurrentSession();
		List users = session

				.createQuery("from MobileUser  where username=:username").setParameter("username", username)
				.setMaxResults(paginationBean.getItemsPerPage()).setFirstResult(paginationBean.getStartIndex()).list();
		paginationBean.setItemList(users);
	}

	@Override
	public List listMobileUsers(final Merchant merchant) {
		if (merchant == null) {
			throw new IllegalArgumentException("sorry, merchant var cannot be null");
		}
		/*
		 * return sessionFactory.getCurrentSession() .createQuery("from c in class " +
		 * MobileUser.class.getName() + " where c.merchant=:merchantx")
		 * .setParameter("merchantx", merchant).list();
		 */
		return sessionFactory.getCurrentSession().createQuery("from MobileUser where c.merchant=:merchantx")
				.setParameter("merchantx", merchant).list();
	}

	@Override
	public List listTIDUsers(final Merchant merchant) {
		if (merchant == null) {
			throw new IllegalArgumentException("sorry, merchant var cannot be null");
		}

		// get all mobile users that belongs to the same merchant
		List<MobileUser> mobileUserList = getMobileUser(merchant);

		/*
		 * return sessionFactory.getCurrentSession() .createQuery("from c in class " +
		 * TID.class.getName() + " where c.mobileUser IN :mobileUserList")
		 * .setParameter("mobileUserList", mobileUserList).list();
		 */
		return sessionFactory.getCurrentSession().createQuery("from TID where c.mobileUser IN :mobileUserList")
				.setParameter("mobileUserList", mobileUserList).list();
	}

	// mobile user updated methods started 31/05/2016
	@Override
	@Transactional(readOnly = false)
	public int addDeviceIdData(String did) {

		// TerminalDetails did= new TerminalDetails();
		// MID mID = new MID();
		// BigInteger dfg = new BigInteger(id.toString());
		Session session = sessionFactory.getCurrentSession();
		/* String sql="insert into MID(MID,MERCHANT_FK) values ('?','?')"; */

		// String sql= " INSERT INTO TERMINAL_DETAILS ( DEVICE_ID ) Values ( '"+ did
		// +"') ";
		String sql = " INSERT INTO TERMINAL_DETAILS ( DEVICE_ID ) Values ( ':did') ";

		// String sql= "update MID set merchant_fk ="+merchant_id+" where id="+m_id;

		logger.info(" Query :" + sql);

		Query insertQuery = session.createQuery(sql);
		insertQuery.setString("did", did);
		// Query insertQuery = session.createSQLQuery("insert into MID(MID,MERCHANT_FK)
		// values ('"+mid+"',"+dfg+")");
		/*
		 * insertQuery.setParameter(0, mid); insertQuery.setBigInteger(1, dfg);
		 */
		int a = insertQuery.executeUpdate();

		// int result = insertQuery.executeUpdate();
		return a;

	}

	@Override
	public String loadBusinessName(final String mid) {
		String businessName = null;
		String sql = "select m.BUSINESS_NAME from MERCHANT m where m.ID in (select a.MERCHANT_FK from MID a "
				+ "where a.MID= :mid or a.MOTO_MID=:mid or a.EZYPASS_MID=:mid or a.EZYREC_MID=:mid or a.EZYWAY_MID=:mid or a.UM_MID=:mid or a.FIUU_MID=:mid)";
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("mid", mid);
		@SuppressWarnings("unchecked")
		List<String> resultSet = sqlQuery.list();
		for (String rec : resultSet) {
			if (rec != null) {
				businessName = rec.toString();
			} else {
				businessName = "-";
			}
		}
		// logger.info("businessname: "+businessName);
		return businessName;
	}

	@Override
	public UMMidTxnLimit loadDetByMid(final String mid) {

		logger.info("mid: " + mid);
		return (UMMidTxnLimit) getSessionFactory().createCriteria(UMMidTxnLimit.class).add(Restrictions.eq("mid", mid))
				.setMaxResults(1).uniqueResult();

		/*
		 * UMMidTxnLimit um = new UMMidTxnLimit(); String
		 * sql="Select u.MID,u.HASH_KEY,u.DTL from UMMIDTXNLIMIT u Where u.MID = :mid";
		 * 
		 * Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		 * sqlQuery.setString("mid", mid);
		 * 
		 * @SuppressWarnings("unchecked") List<Object[]> resultSet = sqlQuery.list();
		 * for (Object[] rec : resultSet) {
		 * 
		 * if(rec[0]!=null) {
		 * 
		 * um.setMid(rec[0].toString());
		 * 
		 * }else { um.setMid(""); } if(rec[1]!=null) {
		 * 
		 * um.setHashKey(rec[1].toString());
		 * 
		 * }else { um.setHashKey(""); } if(rec[2]!=null) {
		 * 
		 * um.setDtl(rec[2].toString());
		 * 
		 * }else { um.setDtl(""); } logger.info("for loop mid: "+um.getMid()); }
		 * 
		 * logger.info("mid: "+um.getMid()); return um;
		 */

	}

	@Override
	public String loadMidData(final String mer_id) {
		// logger.info(" Query :"+mer_id);
		/* Session session = sessionFactory.getCurrentSession() */;
		// String sql= " select mid from MID where MERCHANT_FK = "+mer_id+" ";
		String sql = " select mid from MID where MERCHANT_FK = :mer_id";
		String mid = null;
		// logger.info(" Query :"+sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("mer_id", mer_id);
		@SuppressWarnings("unchecked")
		List<String> resultSet = sqlQuery.list();
		for (String rec : resultSet) {
			mid = rec.toString();
		}
		// logger.info(" Query :"+mid);
		return mid;
	}

	@Override
	public Merchant loadIserMidDetails(Long id) {
		logger.info("MerchantDaoImpl:loadMid: " + id);

		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.like("id", id))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public Merchant loadMidDetails(Long mid) {
		logger.info("MerchantDaoImpl:loadMid: " + mid);

		return (Merchant) getSessionFactory().createCriteria(Merchant.class).add(Restrictions.like("mid", mid))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public List<MobileUser> loadMobileUserByFK(final Long mer_id) {
		try {
			// logger.info(" Query :"+mer_id);

			/* Session session = sessionFactory.getCurrentSession() */
			;
			// String sql= " select mid from MID where MERCHANT_FK = "+mer_id+" ";
			String sql = "select m.ENBL_MOTO,m.ENBL_BOOST from MOBILE_USER m inner join MERCHANT mm on "
					+ "m.MERCHANT_FK=mm.ID  where m.MERCHANT_FK = :mer_id";
			String mid = null;
			// logger.info(" Query :"+sql);
			ArrayList<MobileUser> fss = new ArrayList<MobileUser>();
			Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
			sqlQuery.setLong("mer_id", mer_id);
			@SuppressWarnings("unchecked")
			List<Object[]> resultSet = sqlQuery.list();
			for (Object[] rec : resultSet) {
				MobileUser mobileUser = new MobileUser();
				if (rec[0] != null) {
					mobileUser.setEnableMoto(rec[0].toString());

					// logger.info("enableBoost: "+mobileUser.getEnableBoost());
				}

				if (rec[1] != null) {
					mobileUser.setEnableBoost(rec[1].toString());

					// logger.info("enableMoto: "+mobileUser.getEnableMoto());
				}

				fss.add(mobileUser);

			}
			// logger.info(" Query :"+mid);
			return fss;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public MobiLiteUser loadMobiliteUserByFk(Long merchantId) {
		logger.info("loadMobiliteUserByFk " + merchantId);
		return (MobiLiteUser) getSessionFactory().createCriteria(MobiLiteUser.class)
				.add(Restrictions.like("merchant.id", merchantId)).setMaxResults(1).uniqueResult();
	}

	@Override
	public String loadMobileUserByFKBoost(final Long mer_id) {
		// logger.info(" id for searh boost :"+mer_id);

		/* Session session = sessionFactory.getCurrentSession() */;
		// String sql= " select mid from MID where MERCHANT_FK = "+mer_id+" ";
		String sql = "select m.ENBL_BOOST from MOBILE_USER m inner join MERCHANT mm on "
				+ "m.MERCHANT_FK=mm.ID  where m.MERCHANT_FK = :mer_id and m.ENBL_BOOST= 'Yes'";
		String boost = "No";
		// logger.info(" Query :"+sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setLong("mer_id", mer_id);

		List<String> resultSet = sqlQuery.list();
		// for (Object[] rec : resultSet) {
		for (String rec : resultSet) {
			if (rec != null) {
				boost = "Yes";
			}
		}

		return boost;
		/*
		 * ArrayList<MobileUser> fss = new ArrayList<MobileUser>(); Query sqlQuery =
		 * super.getSessionFactory().createSQLQuery(sql); sqlQuery.setLong("mer_id",
		 * mer_id);
		 * 
		 * @SuppressWarnings("unchecked") List<Object[]> resultSet = sqlQuery.list();
		 * for (Object[] rec : resultSet) { MobileUser mobileUser=new MobileUser();
		 * if(rec[0]!=null) { mobileUser.setEnableBoost(rec[0].toString());
		 * logger.info("enableBoost: "+mobileUser.getEnableBoost()); }
		 * 
		 * 
		 * if(rec[1]!=null) { mobileUser.setEnableMoto(rec[1].toString());
		 * logger.info("enableMoto: "+mobileUser.getEnableMoto()); }
		 * 
		 * fss.add(mobileUser);
		 * 
		 * 
		 * } //logger.info(" Query :"+mid); return fss;
		 */

	}

	@Override
	public String loadMobileUserByFKMoto(final Long mer_id) {
		// logger.info(" id for search moto :"+mer_id);

		/* Session session = sessionFactory.getCurrentSession() */;
		// String sql= " select mid from MID where MERCHANT_FK = "+mer_id+" ";
		String sql = "select m.ENBL_MOTO from MOBILE_USER m inner join MERCHANT mm on "
				+ "m.MERCHANT_FK=mm.ID  where m.MERCHANT_FK = :mer_id and m.ENBL_MOTO= 'Yes'";
		String moto = "No";
		// logger.info(" Query :"+sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setLong("mer_id", mer_id);

		List<String> resultSet = sqlQuery.list();
		for (String rec : resultSet) {
			if (rec != null) {
				moto = "Yes";
			}
		}

		return moto;
		/*
		 * ArrayList<MobileUser> fss = new ArrayList<MobileUser>(); Query sqlQuery =
		 * super.getSessionFactory().createSQLQuery(sql); sqlQuery.setLong("mer_id",
		 * mer_id);
		 * 
		 * @SuppressWarnings("unchecked") List<Object[]> resultSet = sqlQuery.list();
		 * for (Object[] rec : resultSet) { MobileUser mobileUser=new MobileUser();
		 * if(rec[0]!=null) { mobileUser.setEnableBoost(rec[0].toString());
		 * logger.info("enableBoost: "+mobileUser.getEnableBoost()); }
		 * 
		 * 
		 * if(rec[1]!=null) { mobileUser.setEnableMoto(rec[1].toString());
		 * logger.info("enableMoto: "+mobileUser.getEnableMoto()); }
		 * 
		 * fss.add(mobileUser);
		 * 
		 * 
		 * } //logger.info(" Query :"+mid); return fss;
		 */

	}

	public String getMidData(final String mer_id) {

		MID mid = new MID();
		// TerminalDetails did= new TerminalDetails();
		// MID mID = new MID();
		// BigInteger dfg = new BigInteger(id.toString());
		Session session = sessionFactory.getCurrentSession();
		/* String sql="insert into MID(MID,MERCHANT_FK) values ('?','?')"; */

		// String sql= " select mid from MID where MERCHANT_FK = "+mer_id+" ";
		String sql = " select mid from MID where MERCHANT_FK = :mer_id";

		// String sql= "update MID set merchant_fk ="+merchant_id+" where id="+m_id;

		logger.info(" Query :" + sql);

		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("mer_id", mer_id);
		// Query insertQuery = session.createSQLQuery("insert into MID(MID,MERCHANT_FK)
		// values ('"+mid+"',"+dfg+")");
		/*
		 * insertQuery.setParameter(0, mid); insertQuery.setBigInteger(1, dfg);
		 */
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		for (Object[] rec : resultSet) {
			// fs.setLocation(rec[0].toString());
		}

		// int result = insertQuery.executeUpdate();
		return "";

	}

	// mobileuser methods end 31052016
	@Override
	@Transactional(readOnly = false)
	public void changePwdMobileUser(MobileUser mobileUser) {
		logger.info("changePwdMobileUser data " + mobileUser.getId());

		// String query = "update " + MobileUser.class.getName() + " c set c.status
		// =:status,c.password=:password where id =:id";
		// String query = "update " + MobileUser.class.getName() + " c set c.status ='"+
		// mobileUser.getStatus()+"',c.password='"+mobileUser.getPassword()+"' where id
		// ="+mobileUser.getId();
		String query = "update MobileUser c set c.status = :status"
				+ ",c.password= :password, c.failedLoginAttempt=0 ,c.suspendDate = null where id = :id";
		int updatedEntities = super.getSessionFactory().createQuery(query)
				.setParameter("status", mobileUser.getStatus()).setString("password", mobileUser.getPassword())
				.setLong("id", mobileUser.getId()).executeUpdate();
		if (updatedEntities != 1) {
			/*
			 * throw new RuntimeException(
			 * "Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
			 * + updatedEntities);
			 */
		}

	}

	@Override
	@Transactional(readOnly = false)
	public void editMobileUserDetails(MobileUser mobileUser) {
		logger.info("changePwdMobileUser data " + mobileUser.getId());

		// String query = "update " + MobileUser.class.getName() + " c set c.status
		// =:status,c.password=:password where id =:id";
		// String query = "update " + MobileUser.class.getName() + " c set c.status ='"+
		// mobileUser.getStatus()+"',c.password='"+mobileUser.getPassword()+"' where id
		// ="+mobileUser.getId();
		String query = "update MobileUser c set c.status = :status"
				+ ",c.password= :password, c.failedLoginAttempt=0 ,c.suspendDate = null " + "where id = :id";
		int updatedEntities = super.getSessionFactory().createQuery(query)
				.setParameter("status", mobileUser.getStatus()).setString("password", mobileUser.getPassword())
				.setLong("id", mobileUser.getId()).executeUpdate();
		if (updatedEntities != 1) {
			/*
			 * throw new RuntimeException(
			 * "Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
			 * + updatedEntities);
			 */
		}

	}

	// new method mobileuser 17062016
	@Override
	public RegMobileUser loadMobileUserDeviceId(String deviceId) {
		// TODO Auto-generated method stub

		return (RegMobileUser) getSessionFactory().createCriteria(RegMobileUser.class)
				.add(Restrictions.eq("deviceId", deviceId)).setMaxResults(1).uniqueResult();
	}
	// return null;

	// demo method 20062016

	@Override
	public TerminalDetails loadDeviceId(String deviceId) {
		// TODO Auto-generated method stub

		// logger.info("check device id existing: "+deviceId);
		return (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.like("deviceId", deviceId)).setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public int updateKManager(String refNo, String tid) {

		logger.info("KManager data " + refNo + " " + tid);
		// String query = "update " + KManager.class.getName() + " k set k.tid ='"+ tid
		// +"' where k.refNo ='"+refNo +"'";
		String query = "update KManager k set k.tid = :tid where k.refNo = :refNo";
		int updatedEntities = super.getSessionFactory().createQuery(query).setParameter("tid", tid)
				.setString("refNo", refNo).executeUpdate();
		/*
		 * if (updatedEntities != 2) { throw new RuntimeException(
		 * "Rows updated should always be TWO. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
		 * + updatedEntities); }
		 */

		return updatedEntities;
	}

	@Override
	@Transactional(readOnly = false)
	public int updateUM_KManager(String um_refNo, String um_tid) {

		logger.info("UM_KManager data " + um_refNo + " " + um_tid);
		// String query = "update " + KManager.class.getName() + " k set k.tid ='"+ tid
		// +"' where k.refNo ='"+refNo +"'";
		String query = "update UMKManager k set k.tid = :um_tid where k.refNo = :um_refNo";
		int updatedEntities = super.getSessionFactory().createQuery(query).setParameter("um_tid", um_tid)
				.setString("um_refNo", um_refNo).executeUpdate();
		/*
		 * if (updatedEntities != 2) { throw new RuntimeException(
		 * "Rows updated should always be TWO. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
		 * + updatedEntities); }
		 */

		return updatedEntities;
	}

	@Override
	@Transactional(readOnly = false)
	public int updateUMTxnLimit(String hashkey, String dtl, String mid, String redirectUrl) {

		logger.info("UMTxnLimit data " + hashkey + " " + dtl);

		String query = "update UMMidTxnLimit u set u.hashKey = :hashkey, u.dtl= :dtl, u.redirectUrl= :redirectUrl where u.mid = :mid";
		int updatedEntities = super.getSessionFactory().createQuery(query).setParameter("hashkey", hashkey)
				.setParameter("dtl", dtl).setParameter("redirectUrl", redirectUrl).setString("mid", mid)
				.executeUpdate();

		return updatedEntities;
	}

	@Override
	@Transactional(readOnly = false)
	public int updateMotoMobileuser(String motoTid, String searchTid, String updateType) {

		logger.info("Mobileuser data " + searchTid);
		String query = null;
		// String query = "update " + KManager.class.getName() + " k set k.tid ='"+ tid
		// +"' where k.refNo ='"+refNo +"'";

		if (updateType.equals("ezywire")) {
			query = "update MobileUser m set m.enableMoto = 'Yes',m.motoTid = '" + motoTid + "' where m.tid = '"
					+ searchTid + "'";
		} else if (updateType.equals("ezyrec")) {
			query = "update MobileUser m set m.enableMoto = 'Yes',m.motoTid = '" + motoTid + "' where m.ezyrecTid = '"
					+ searchTid + "'";

		} else if (updateType.equals("ezypass")) {
			query = "update MobileUser m set m.enableMoto = 'Yes',m.motoTid = '" + motoTid + "' where m.ezypassTid = '"
					+ searchTid + "'";

		}
		logger.info(query);
		return super.getSessionFactory().createQuery(query).executeUpdate();
	}

	@Override
	@Transactional(readOnly = false)
	public int updateEzyRecMobileuser(String ezyrecTid, String searchTid, String updateType) {

		logger.info("Mobileuser data " + searchTid);
		String query = null;
		// String query = "update " + KManager.class.getName() + " k set k.tid ='"+ tid
		// +"' where k.refNo ='"+refNo +"'";

		if (updateType.equals("ezywire")) {
			query = "update MobileUser m set m.ezyrecTid = '" + ezyrecTid + "' where m.tid = '" + searchTid + "'";
		} else if (updateType.equals("moto")) {
			query = "update MobileUser m set m.ezyrecTid = '" + ezyrecTid + "' where m.motoTid = '" + searchTid + "'";

		} else if (updateType.equals("ezypass")) {
			query = "update MobileUser m set m.ezyrecTid = '" + ezyrecTid + "' where m.ezypassTid = '" + searchTid
					+ "'";

		}
		return super.getSessionFactory().createQuery(query).executeUpdate();
	}

	@Override
	@Transactional(readOnly = false)
	public int updateEzywireMobileuser(String tid, String searchTid, String updateType, String preAuth) {

		logger.info("Mobileuser data " + tid);

		// String query = "update " + KManager.class.getName() + " k set k.tid ='"+ tid
		// +"' where k.refNo ='"+refNo +"'";

		String query = null;
		if (updateType.equals("moto")) {

			query = "update MobileUser m set m.preAuth = '" + preAuth + "',m.tid = '" + tid + "' where m.motoTid = '"
					+ searchTid + "'";
		} else if (updateType.equals("ezyrec")) {

			query = "update MobileUser m set m.preAuth = '" + preAuth + "',m.tid = '" + tid + "' where m.ezyrecTid = '"
					+ searchTid + "'";
		} else if (updateType.equals("ezypass")) {
			query = "update MobileUser m set m.preAuth = '" + preAuth + "',m.tid = '" + tid + "' where m.ezypassTid = '"
					+ searchTid + "'";
		}

		int updatedEntities = super.getSessionFactory().createQuery(query).executeUpdate();

		return updatedEntities;
	}

	@Override
	@Transactional(readOnly = false)
	public int updateMobileuserEzypass(String ezypassTid, String searchTid, String updateType) {

		logger.info("Mobileuser data " + searchTid);

		// String query = "update " + KManager.class.getName() + " k set k.tid ='"+ tid
		// +"' where k.refNo ='"+refNo +"'";
		String query = null;
		if (updateType.equals("ezywire")) {
			query = "update MobileUser m set m.enableEzpass = 'Yes',m.ezypassTid = '" + ezypassTid + "' where m.tid = '"
					+ searchTid + "'";
		} else if (updateType.equals("ezyrec")) {
			query = "update MobileUser m set m.enableEzpass = 'Yes',m.ezypassTid = '" + ezypassTid
					+ "' where m.ezyrecTid = '" + searchTid + "'";
		} else if (updateType.equals("moto")) {
			query = "update MobileUser m set m.enableEzpass = 'Yes',m.ezypassTid = '" + ezypassTid
					+ "' where m.motoTid = '" + searchTid + "'";

		}

		return super.getSessionFactory().createQuery(query).executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MobileUser> loadMobileUserDetails(Long id) {

		logger.info("load mobile user details bigint: id " + id);
		return (List<MobileUser>) getSessionFactory().createCriteria(MobileUser.class)
				.add(Restrictions.eq("merchant.id", id)).list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MobileUser> loadUmMobileUserDetails(Long id) {

		logger.info("load Um mobile user details bigint: id " + id);
		return (List<MobileUser>) getSessionFactory().createCriteria(MobileUser.class)
				.add(Restrictions.eq("merchant.id", id)).add(Restrictions.eq("merchant.merchantType", "U")).list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public MobileUser loadMobileUsertidDetails(String tid) {

		logger.info("load mobile user details: tid " + tid);
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class).add(Restrictions.eq("tid", tid))
				.setMaxResults(1).uniqueResult();

	}

	@Override
	public MobileUser loadMobileUserMototidDetails(String motoTid) {

		logger.info("load mobile user details: motoTid " + motoTid);
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class)
				.add(Restrictions.eq("motoTid", motoTid)).setMaxResults(1).uniqueResult();

	}

	@Override
	public MobileUser loadMobileUserEzywaytidDetails(String ezywayTid) {

		logger.info("load mobile user details: ezywayTid " + ezywayTid);
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class)
				.add(Restrictions.eq("ezywayTid", ezywayTid)).setMaxResults(1).uniqueResult();

	}

	@Override
	public MobileUser loadMobileUserEzyRectidDetails(String ezyrecTid) {

		logger.info("load mobile user details: ezyrecTid " + ezyrecTid);
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class)
				.add(Restrictions.eq("ezyrecTid", ezyrecTid)).setMaxResults(1).uniqueResult();

	}

	@Override
	public MobileUser loadMobileUserEzypasstidDetails(String ezypassTid) {

		logger.info("load mobile user details: ezypassTid " + ezypassTid);
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class)
				.add(Restrictions.eq("ezypassTid", ezypassTid)).setMaxResults(1).uniqueResult();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<KManager> loadRefNoToTid() {
		Criterion key1 = Restrictions.like("keyName", "TMK");
		Criterion key2 = Restrictions.like("keyName", "TAK");
		Disjunction orExp = Restrictions.or(key1, key2, key2);
		List<KManager> listKMgr = new ArrayList<KManager>();
		listKMgr = getSessionFactory().createCriteria(KManager.class).add(Restrictions.isNull("tid")).add(orExp).list();
		return listKMgr;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UMKManager> loadUmRefNoToTid() {
		List<UMKManager> listKMgr = new ArrayList<UMKManager>();
		listKMgr = getSessionFactory().createCriteria(UMKManager.class).add(Restrictions.isNull("tid")).list();
		return listKMgr;
	}

	@Override
	@Transactional(readOnly = true)
	public void listMobileUserDetails(PaginationBean<MobileUser> paginationBean, ArrayList<Criterion> props,
			String date, String date1) {

		logger.info("Inside   listAllTransaction : " + date + "     " + date1);
		String dat = null;
		String dat1 = null;
		String year1 = null;
		String year2 = null;
		ArrayList<MobileUser> fss = new ArrayList<MobileUser>();
		String sql = null;

		if ((date == null || date1 == null) || (date.equals("") || date1.equals(""))) {

			Date dt = new Date();

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			dat = dateFormat.format(dt);
			dat = dat + "-01";
			/*
			 * SimpleDateFormat yr1 = new SimpleDateFormat("yyyy"); year1 = yr1.format(dt);
			 */

			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			dat1 = dateFormat1.format(dt1);

		} else {

			dat = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("MM/dd/yyyy").parse(dat));
			} catch (ParseException e) {

				e.printStackTrace();
			}

			dat1 = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("MM/dd/yyyy").parse(dat1));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		sql = "  m.activateDate,select m.username,m.email,m.status from MOBILE_USER m where m.status='ACTIVE' "
				+ " and  m.activateDate between :dat and :dat1 " + "order by m.activateDate desc ";

		/*
		 * m.activateDate, m.
		 * activateDate"select f.state , f.BUSINESS_NAME AS MerchantName ,a.time_stamp , sum(a.AMOUNT)  TotalAmount , ag.FIRST_NAME "
		 * +
		 * "from FOR_SETTLEMENT a INNER JOIN MID m on a.MID=m.MID INNER JOIN MERCHANT f "
		 * + "ON f.MID_FK=m.ID INNER JOIN AGENT ag on f.AGID_FK=ag.id " +
		 * "where a.status='S' and time_stamp between :dat  and :dat1" +
		 * " group by a.MID,a.date order by a.time_stamp desc";
		 */

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setString("dat", dat);
		sqlQuery.setString("dat1", dat1);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			MobileUser fs = new MobileUser();

			RegMobileUser rm = new RegMobileUser();

			rm.setUsername(fs.getUsername());
			rm.setEmailId(fs.getEmail());
			rm.setActivateDate(fs.getActivateDate().toString());

			String rd = null;
			try {

				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));

			} catch (Exception e) {

				e.printStackTrace();
			}

			// fs.setActivateDate(rec[0].toString());

			// fs.setActivateDate(rd);
			fs.setUsername(rec[0].toString());
			fs.setEmail(rec[1].toString());
			/*
			 * rm.setActivateDate(rd);
			 * 
			 * 
			 * rm.setUsername(rec[1].toString()); rm.setEmailId(rec[2].toString());
			 */
			fss.add(fs);
		}
		paginationBean.setItemList(fss);
		// paginationBean.setTotalRowCount(fss.size());
	}

	// }

	// new method for mobile user wifi 22/06/2017
	@Override
	@Transactional(readOnly = false)
	public int loadTerminalDetailsByMid(String mid, String connectType) {

		Session session = sessionFactory.getCurrentSession();
		connectType = "BT";
		int count = 0;
		/* String sql="insert into MID(MID,MERCHANT_FK) values ('?','?')"; */

		// String sql= " select mid from MID where MERCHANT_FK = "+mer_id+" ";

		logger.info("check terminal details mid count :" + mid);
		String sql = "select count(*) from TERMINAL_DETAILS where MERCHANT_ID =:mid and CONNECT_TYPE=:connectType";
		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);// .addEntity(ForSettlement.class);
		sqlQuery.setParameter("mid", mid);
		sqlQuery.setParameter("connectType", connectType);

		@SuppressWarnings("unchecked")
		List<BigInteger> resultSet1 = sqlQuery.list();

		for (BigInteger rec : resultSet1) {

			count = Integer.parseInt(rec.toString());

		}

		/*
		 * logger.info("Query : " + sql); count= sqlQuery.executeUpdate();
		 */
		logger.info("MID count : " + count);
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TerminalDetails> loadTerminalDetailsByMidAndType(String mid, String connectType) {
		List<TerminalDetails> listTerminalDetail = new ArrayList<TerminalDetails>();
		listTerminalDetail = getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.eq("merchantId", mid)).add(Restrictions.eq("connectType", connectType)).list();
		return listTerminalDetail;
		// .setMaxResults(1).uniqueResult();
	}

	@Override
	public MobileUser loadMobileUserbyTidAndType(String tid, String connectType) {
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class).add(Restrictions.eq("tid", tid))
				.add(Restrictions.eq("connectType", connectType)).setMaxResults(1).uniqueResult();
	}

	@Override
	public TerminalDetails loadTerminalDetailsByTid(String tid) {

		// logger.info("check tid existing: "+tid);
		return (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.like("tid", tid))

				.setMaxResults(1).uniqueResult();
	}

	@Override
	public TerminalDetails loadTerminalDetailsByActivationcode(String activationCode) {

		// logger.info("check tid existing: "+tid);
		return (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.like("activationCode", activationCode))

				.setMaxResults(1).uniqueResult();
	}

	@Override
	public TerminalDetails loadTerminalDetailsByAnyTid(RegMobileUser regMob) {

		logger.info("check tid existing: " + regMob.getTid() + " " + regMob.getMotoTid() + " " + regMob.getEzypassTid()
				+ " " + regMob.getEzyrecTid());

		Disjunction orExp = Restrictions.disjunction();
		orExp.add(Restrictions.eq("mid", regMob.getTid()));
		orExp.add(Restrictions.eq("motoMid", regMob.getMotoTid()));
		orExp.add(Restrictions.eq("ezypassMid", regMob.getEzypassTid()));
		orExp.add(Restrictions.eq("ezyrecMid", regMob.getEzyrecTid()));
		return (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class).add(orExp).setMaxResults(1)
				.uniqueResult();
	}

	@Override
	public TerminalDetails loadTerminalDetailsByAnyTids(RegMobileUser regMob) {

		logger.info("check tid existing: " + regMob.getTid() + " " + regMob.getMotoTid() + " " + regMob.getEzypassTid()
				+ " " + regMob.getEzyrecTid());

		Disjunction orExp = Restrictions.disjunction();
		orExp.add(Restrictions.eq("tid", regMob.getTid()));
		orExp.add(Restrictions.eq("tid", regMob.getMotoTid()));
		orExp.add(Restrictions.eq("tid", regMob.getEzypassTid()));
		orExp.add(Restrictions.eq("tid", regMob.getEzyrecTid()));
		orExp.add(Restrictions.eq("tid", regMob.getgPayTid()));
		return (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class).add(orExp).setMaxResults(1)
				.uniqueResult();
	}

	@Override
	public TerminalDetails loadTerminalDetailsByTidAndMotoTid(String tid, String motoTid) {

		logger.info("check tid existing: " + tid + " mototid: " + motoTid);

		return (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.eq("motoTid", motoTid)).add(Restrictions.eq("tid", tid))

				.setMaxResults(1).uniqueResult();
	}

	@Override
	public List<TerminalDetails> loadTerminalDetailsByMid(String mid) {

		return (List<TerminalDetails>) getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.like("merchantId", mid))

				.list();

	}

	@Override
	public List<MobileUser> listMobileUserexpt(final ArrayList<Criterion> props, String date, String date1) {
		// TODO Auto-generated method stub

		String dat = null;
		String dat1 = null;
		String year1 = null;
		String year2 = null;
		ArrayList<MobileUser> mobileUserSummary = new ArrayList<MobileUser>();

		// TerminalDetails reader = readerDAO.loadTerminalByDevice(id);
		String sql = null;

		if (date != null) {

			dat = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat));
				logger.info("check activation date1:" + dat);
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
		if (date1 == null || date1.equals("")) {
			Date dt1 = new Date();
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			dat1 = dateFormat1.format(dt1);
			logger.info("check activation date2:" + dat1);

		} else {

			dat1 = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat1));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}

		/*
		 * sql ="select m.ACTIVATE_DATE,m.USERNAME from mobile_user m " +
		 * "where m.ACTIVATE_DATE between :dat and :dat1" +
		 * " order by m.ACTIVATE_DATE desc" ;
		 */
		sql = "select m.ACTIVATE_DATE,m.USERNAME,d.TID ,d.DEVICE_ID,d.SUSPENDED_DATE,d.MERCHANT_ID,d.DEVICE_TYPE from"
				+ " TERMINAL_DETAILS d INNER JOIN  MOBILE_USER m on m.TID = d.TID or m.MOTO_TID=d.TID or m.EZYPASS_TID=d.TID or "
				+ "m.EZYWAY_TID=d.TID or m.EZYREC_TID=d.TID or m.FIUU_TID=d.TID "
				+ "where d.ACTIVATED_DATE between :dat and :dat1 order by d.ACTIVATED_DATE desc";

		/*
		 * sql =
		 * "select m.ACTIVATE_DATE, m.USERNAME,t.TID,t.DEVICE_ID,t.SUSPENDED_DATE from TERMINAL_DETAILS t "
		 * +
		 * "INNER JOIN MOBILE_USER m on m.TID = t.TID or m.MOTO_TID=t.TID or m.EZYPASS_TID=t.TID "
		 * + "where m.ACTIVATE_DATE " +
		 * "between  :dat and :dat1 order by m.ACTIVATE_DATE desc";
		 */

		logger.info("Query : " + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("dat", dat);
		sqlQuery.setString("dat1", dat1);

		List<Object[]> resultSet = sqlQuery.list();
		logger.info("resultset size:" + resultSet.size());
		for (Object[] rec : resultSet) {
			MobileUser mobileUserList = new MobileUser();

			logger.info("activate date from mobile user:" + mobileUserList.getActivateDate());
			// String rd = null;
			try {
				/*
				 * rd = new SimpleDateFormat("dd-MMM-yyyy") .format(new
				 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss") .parse(rec[0].toString()));
				 */

				/*
				 * Date actDate = mobileUserList.getActivateDate();
				 * 
				 * DateTime fromDate = new DateTime(actDate);
				 */
				mobileUserList.setActivateDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[0].toString()));
				logger.info(" activation date from mobile user2131321:" + mobileUserList.getActivateDate());
			} catch (ParseException e) {

				e.printStackTrace();
			}

			mobileUserList.setUsername(rec[1].toString());
			mobileUserList.setTid(rec[2].toString());

			mobileUserList.setDeviceId(rec[3].toString());
			if (rec[4] != null) {
				try {
					mobileUserList.setSuspendDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[4].toString()));
				} catch (ParseException e) {

					e.printStackTrace();
				}
			}
			mobileUserList.setConnectType(rec[5].toString());
			if (rec[6] != null) {
				mobileUserList.setDeviceType(rec[6].toString());

			} else {
				mobileUserList.setDeviceType("EZYWIRE");

			}
			logger.info("check username:" + mobileUserList.getUsername());
			mobileUserSummary.add(mobileUserList);

		}
		return mobileUserSummary;
	}

	@Override
	public MobileUserData loadMobileUserByTid(String tid) {
		String sql = null;
		Query sqlQuery = null;
		MobileUserData mud = new MobileUserData();

		/*
		 * sql =
		 * "select d.DEVICE_ID,d.TID,d.RENEWAL_DATE,d.SUSPENDED_DATE,m.USERNAME,d.ACTIVATED_DATE,d.ACTIVE_STATUS,m.PRE_AUTH,m.ENBL_BOOST,"
		 * + "m.ENBL_MOTO from  TERMINAL_DETAILS d INNER JOIN  " +
		 * " MOBILE_USER m on m.TID = d.TID  where m.TID =:dat ";
		 */

		sql = "select d.DEVICE_ID,d.TID,d.RENEWAL_DATE,d.SUSPENDED_DATE,m.USERNAME,d.ACTIVATED_DATE,d.ACTIVE_STATUS,m.PRE_AUTH,m.ENBL_BOOST,"
				+ "m.ENBL_MOTO from  TERMINAL_DETAILS d INNER JOIN  "
				+ " MOBILE_USER m on m.TID = d.TID or m.MOTO_TID=d.TID or m.EZYWAY_TID=d.TID or m.EZYPASS_TID=d.TID or m.FIUU_TID=d.TID "
				+ "or m.EZYREC_TID=d.TID where d.TID =:dat";

		logger.info("query:" + sql);
		sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("dat", tid);

		List<Object[]> resultSet = sqlQuery.list();
		logger.info("resultset size:" + resultSet.size());
		for (Object[] rec : resultSet) {
			mud.setDeviceId(rec[0].toString());
			mud.setTid(rec[1].toString());
			if (rec[2] != null) {
				try {
					String rd = null;
					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[2].toString()));
					mud.setRenewalDate(rd);

					// activateDate.add(rd);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				mud.setRenewalDate("");
			}
			if (rec[3] != null) {
				try {
					String rd = null;
					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[3].toString()));
					mud.setExpiryDate(rd);

					// activateDate.add(rd);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				mud.setExpiryDate("");
			}
			mud.setMobileUserName(rec[4].toString());
			if (rec[5] != null) {
				try {
					String rd = null;
					rd = new SimpleDateFormat("dd/MM/yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[5].toString()));
					mud.setActivationDate(rd);

					// activateDate.add(rd);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				mud.setActivationDate("");
			}
			if (rec[6] != null) {
				mud.setStatus(rec[6].toString());
			} else {
				mud.setStatus("");
			}

			if (rec[7] != null) {
				mud.setPreAuth(rec[7].toString());
			} else {
				mud.setPreAuth("No");
			}
			if (rec[8] != null) {
				mud.setEnableBoost(rec[8].toString());
			} else {
				mud.setEnableBoost("No");
			}
			if (rec[9] != null) {
				mud.setEnableMoto(rec[9].toString());
			} else {
				mud.setEnableMoto("No");
			}

		}
		return mud;
	}

	public MobileUser loadMobileUserbyTid(String tid) {
		/*
		 * logger.info("get tid base data: "+tid); return (MobileUser)
		 * getSessionFactory().createCriteria(MobileUser.class).add(Restrictions.eq(
		 * "tid", tid)) .setMaxResults(1).uniqueResult();
		 */

		logger.info("get tid base data: " + tid);

		Criterion ezywireTid = Restrictions.like("tid", tid);
		Criterion motoTid = Restrictions.like("motoTid", tid);
		Criterion ezypassTid = Restrictions.like("ezypassTid", tid);
		Criterion ezyrecTid = Restrictions.like("ezypassTid", tid);
		Criterion fiuuTid = Restrictions.like("fiuuTid", tid);

		// Criterion contact = Restrictions.like("googleId", username);
		Disjunction orExp = Restrictions.or(ezywireTid, motoTid, ezypassTid, ezyrecTid,fiuuTid);

		return (MobileUser) sessionFactory.getCurrentSession().createCriteria(MobileUser.class).add(orExp)
				.setMaxResults(1).uniqueResult();
	}

	public MobileUser loadMobileUserbyMerchantFK(String merchantID) {
		logger.info("get merchantID base data: " + merchantID);
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class)
				.add(Restrictions.eq("merchant.id", Long.valueOf(merchantID))).setMaxResults(1).uniqueResult();

	}

	public List<MobileUser> loadMobileUsersbyMerchantFK(String merchantID) {
		logger.info("get merchantID base data: " + merchantID);
		return (List<MobileUser>) getSessionFactory().createCriteria(MobileUser.class)
				.add(Restrictions.eq("merchant.id", Long.valueOf(merchantID))).add(Restrictions.isNull("ezywayTid"))
				.add(Restrictions.isNull("gpayTid")).list();

	}

	/*
	 * public List<MobileUser> loadMerchant() {
	 * logger.info("MerchantDaoImpl:loadMerchant"); return (List<MobileUser>)
	 * getSessionFactory().createCriteria(MobileUser.class).add(Restrictions.eq(
	 * "status",CommonStatus.ACTIVE)).list();
	 * 
	 * }
	 */
	/*
	 * public MobileUser loadMobileUserDatabyTid(String tid) {
	 * logger.info("get tid base data: "+tid);
	 * 
	 * Criterion user = Restrictions.like("tid", tid); Criterion motoTid =
	 * Restrictions.like("motoTid", tid); Criterion ezypasstid =
	 * Restrictions.like("ezypasstid", tid);
	 * 
	 * //Criterion contact = Restrictions.like("googleId", username); Disjunction
	 * orExp = Restrictions.or(user, motoTid,ezypasstid);
	 * 
	 * 
	 * 
	 * return (MobileUser)
	 * sessionFactory.getCurrentSession().createCriteria(MobileUser.class)
	 * .add(orExp).setMaxResults(1).uniqueResult();
	 * 
	 * 
	 * }
	 */
	// old
	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = false) public void updateMobileUserByTid(String
	 * tid, String preAuth, long id) {
	 * 
	 * //String query =
	 * "update MobileUser c set c.preAuth =:preAuth where c.id =:id and c.tid =:tid"
	 * ; String query =
	 * "update MOBILEUSER c set c.PREAUTH =:preAuth where c.ID =:id and c.TID =:tid"
	 * ; int updatedEntities =
	 * super.getSessionFactory().createQuery(query).setParameter("preAuth", preAuth)
	 * .setLong("id", id).setString("tid", tid).executeUpdate(); if (updatedEntities
	 * != 1) { throw new RuntimeException(
	 * "Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
	 * + updatedEntities); }
	 * 
	 * }
	 */

	// new
	@Override
	@Transactional(readOnly = false)
	public void updateMobileUserByTid(String tid, String preAuth, long id, String enableBoost, String enableMoto) {

		// String query = "update MobileUser c set c.preAuth =:preAuth where c.id =:id
		// and c.tid =:tid";
		logger.info("tid :" + tid + "  id: " + id + " enableBoost: " + enableBoost + " enableMoto: " + enableMoto);

		String query = "update MobileUser c set c.preAuth =:preAuth,c.enableBoost =:enableBoost,"
				+ "c.enableMoto =:enableMoto where c.id =:id and c.tid =:tid "
				+ "or c.motoTid =:tid or c.ezypassTid =:tid or c.fiuuTid =:tid";
		logger.info("Query: " + query);

		int updatedEntities = super.getSessionFactory().createQuery(query).setParameter("preAuth", preAuth)
				.setParameter("enableBoost", enableBoost).setParameter("enableMoto", enableMoto).setLong("id", id)
				.setParameter("tid", tid).executeUpdate();
		if (updatedEntities != 1) {
			throw new RuntimeException(
					"Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
							+ updatedEntities);
		}

	}

	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = false) public void
	 * updateTerminalDetailsByTid(TerminalDetails terminalDetails) {
	 * logger.info("check: "+terminalDetails.getRenewalDate()+" "+
	 * terminalDetails.getSuspendedDate());
	 * 
	 * String query =
	 * "update TerminalDetails c set c.suspendedDate =:suspendedDate, " +
	 * "c.renewalDate =:renewalDate, c.remarks =:remarks where c.deviceId =:deviceId and c.tid =:tid"
	 * ; int updatedEntities =
	 * super.getSessionFactory().createQuery(query).setParameter("suspendedDate",
	 * terminalDetails.getSuspendedDate()) .setDate("renewalDate",
	 * terminalDetails.getRenewalDate()).setString("remarks",
	 * terminalDetails.getRemarks()) .setString("deviceId",
	 * terminalDetails.getDeviceId()).setString("tid",
	 * terminalDetails.getTid()).executeUpdate(); if (updatedEntities != 1) { throw
	 * new RuntimeException(
	 * "Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
	 * + updatedEntities); }
	 * 
	 * }
	 * 
	 */

	// rkmobilesumstatusissue
	@Override
	@Transactional(readOnly = false)
	public void updateTerminalDetailsByTid(TerminalDetails terminalDetails) {
		logger.info("check: " + terminalDetails.getRenewalDate() + " " + terminalDetails.getSuspendedDate() + ""
				+ terminalDetails.getActiveStatus());
		System.out.println("ekkada chudara " + terminalDetails.getActiveStatus());

		String query = "update TerminalDetails c set c.suspendedDate =:suspendedDate, "
				+ "c.renewalDate =:renewalDate, c.activeStatus=:activeStatus, c.remarks =:remarks where c.deviceId =:deviceId and c.tid =:tid";
		int updatedEntities = super.getSessionFactory().createQuery(query)
				.setParameter("suspendedDate", terminalDetails.getSuspendedDate())
				.setDate("renewalDate", terminalDetails.getRenewalDate())
				.setString("activeStatus", terminalDetails.getActiveStatus())
				.setString("remarks", terminalDetails.getRemarks()).setString("deviceId", terminalDetails.getDeviceId())
				.setString("tid", terminalDetails.getTid()).executeUpdate();
		if (updatedEntities != 1) {
			throw new RuntimeException(
					"Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
							+ updatedEntities);
		}

	}

	@Override
	public MobileUser loadMobileUserByIdAndName(long id, String username) {

		logger.info(" checkMobileUser Dao Impl : " + username);
		Criterion user = Restrictions.like("username", username);
		Criterion facebook = Restrictions.like("facebookId", username);
		Criterion google = Restrictions.like("googleId", username);
		Criterion email = Restrictions.like("email", username);
		// Criterion contact = Restrictions.like("googleId", username);
		Disjunction orExp = Restrictions.or(user, facebook, google, email);

		/*
		 * return (MobileUser)
		 * sessionFactory.getCurrentSession().createCriteria(MobileUser.class)
		 * .add(Restrictions.ne("id", id)).
		 * add(Restrictions.disjunction().add(Restrictions.like("username", username))
		 * .add(Restrictions.like("facebookId", username))
		 * .add(Restrictions.like("googleId", username))
		 * ).setMaxResults(1).uniqueResult();
		 */

		return (MobileUser) sessionFactory.getCurrentSession().createCriteria(MobileUser.class)
				.add(Restrictions.ne("id", id)).add(orExp).setMaxResults(1).uniqueResult();

		// return null;
	}

	@Override
	public List<MobileUser> loadMobileUserByIdAndEmail(long id, String email) {

		logger.info("id to check email: " + id);
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(MobileUser.class).add(Restrictions.eq("email", email))
				.add(Restrictions.ne("id", id)).list();

	}

	@Override
	public List<MobileUser> loadMobileUserByIdAndContact(long id, String contact) {

		logger.info("id to check contact: " + id + " : " + contact);
		Session session = sessionFactory.getCurrentSession();
		return session.createCriteria(MobileUser.class).add(Restrictions.eq("contact", contact))
				.add(Restrictions.ne("id", id)).list();

	}

	public MobileUser loadMobileUserDeviceMapping() {
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class)
				.add(Restrictions.eq("username", "testdevice")).setMaxResults(1).uniqueResult();
	}

	@Override
	@Transactional(readOnly = false)
	public void updateMobileUserWithTid(MobileUser regmob) {
		long id = regmob.getId();
		String username = regmob.getUsername();
		String tid = regmob.getTid();
		String preAuth = regmob.getPreAuth();
		String motoTid = regmob.getMotoTid();
		String ezyrecTid = regmob.getEzyrecTid();

		String query = "update MobileUser c set c.preAuth =:preAuth , c.tid =:tid, c.motoTid =:motoTid, "
				+ "c.ezyrecTid =:ezyrecTid where c.id =:id and c.username =:username";
		int updatedEntities = super.getSessionFactory().createQuery(query).setParameter("preAuth", preAuth)
				.setParameter("username", username).setLong("id", id).setString("tid", tid)
				.setString("ezyrecTid", ezyrecTid).setString("motoTid", motoTid).executeUpdate();
		if (updatedEntities != 1) {
			throw new RuntimeException(
					"Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
							+ updatedEntities);
		}

	}

	@Override
	public List<MobileUser> loadMobileUser() {
		return (List<MobileUser>) getSessionFactory().createCriteria(MobileUser.class)
				.add(Restrictions.eq("status", CommonStatus.ACTIVE)).list();

	}

	@Override
	public MobileUser loadGrabPayTid(String grapPayTid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UMMidTxnLimit loadUmMidTxnLimitDetails(String motoMid) {

		logger.info("loadUmMidTxnLimitDetails: motoMid " + motoMid);
		return (UMMidTxnLimit) getSessionFactory().createCriteria(UMMidTxnLimit.class)
				.add(Restrictions.eq("mid", motoMid)).setMaxResults(1).uniqueResult();

	}

	@Override
	public UMMidTxnLimit loadDtlMidDetails(Long id) {
		logger.info("MerchantDaoImpl:loadMid: " + id);

		return (UMMidTxnLimit) getSessionFactory().createCriteria(UMMidTxnLimit.class).add(Restrictions.like("id", id))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public MobileUser getMobileUserData(final Merchant merchant) {
		return (MobileUser) this.sessionFactory.getCurrentSession().createCriteria(MobileUser.class)
				.add(Restrictions.eq("merchant", merchant)).setMaxResults(1).uniqueResult();
	}


}
