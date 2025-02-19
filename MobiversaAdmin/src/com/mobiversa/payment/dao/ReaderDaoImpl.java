package com.mobiversa.payment.dao;

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
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.Reader;
import com.mobiversa.common.bo.ReaderStatusHistory;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.DeviceIdApi;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.MobileUserData;
import com.mobiversa.payment.dto.ReaderList;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class ReaderDaoImpl extends BaseDAOImpl implements ReaderDao {

	/*
	 * @Override
	 * 
	 * @Transactional(readOnly = true) public void listReaderUser(final
	 * PaginationBean<Reader> paginationBean, final ArrayList<Criterion> props) {
	 * super.getPaginationItemsByPage(paginationBean, Reader.class, props,
	 * Order.asc("id"));
	 * 
	 * }
	 */

	@Override
	@Transactional(readOnly = true)
	public void listTerminalsByDeviceId(final PaginationBean<TerminalDetails> paginationBean,
			ArrayList<Criterion> props) {
		super.getPaginationItemsByPage(paginationBean, TerminalDetails.class, props, Order.asc("deviceId"));

	}

	// listreaders merchant

	@Override
	@Transactional(readOnly = true)
	public void listAllReaders(final PaginationBean<Reader> paginationBean, final ArrayList<Criterion> props) {
		super.getPaginationItemsByPage(paginationBean, Reader.class, props, Order.asc("merchant"));

	}

	@Override
	@SuppressWarnings("unchecked")
	public void findByReaderNames(final String serialNumber, final PaginationBean<Reader> paginationBean) {
		// CHANGE INTERFACE
		Session session = sessionFactory.getCurrentSession();
		List users = session

				.createQuery("from Reader where serial_number LIKE :serial_number")
				.setParameter("serial_number", "%" + serialNumber + "%").setMaxResults(paginationBean.getItemsPerPage())
				.setFirstResult(paginationBean.getStartIndex()).list();

		paginationBean.setItemList(users);

	}

	@Override
	@Transactional(readOnly = false)
	public void updateReaderStatus(final Long id, final CommonStatus status, final ReaderStatusHistory history) {

		getSessionFactory().save(history);

		// String query = "update " + Reader.class.getName() + " c set c.status =:status
		// where id =:id";
		String query = "update Reader c set c.status =:status where id =:id";
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
	public ReaderStatusHistory loadReaderStatusHistoryID(final Reader reader) {
		Session session = sessionFactory.getCurrentSession();
		@SuppressWarnings("rawtypes")
		ReaderStatusHistory history = (ReaderStatusHistory) session
				.createQuery("from ReaderStatusHistory where reader=:reader order by ID desc")
				.setParameter("reader", reader).setMaxResults(1).uniqueResult();

		return history;

	}

	@Override
	public TerminalDetails loadTerminalDetails(String merchantId) {
		return (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.eq("merchantId", merchantId)).setMaxResults(1).uniqueResult();
	}

	@Override
	public void getTerminalDetails(PaginationBean<TerminalDetails> paginationBean, ArrayList<Criterion> props) {
		logger.info("In dao impl*******");
		super.getPaginationItemsByPage(paginationBean, TerminalDetails.class, props, Order.asc("deviceId"));// Order.asc("TRX_ID")

	}

	@Override
	public void listReadersOfMerchant(PaginationBean<TerminalDetails> paginationBean, ArrayList<Criterion> props) {
		// TODO Auto-generated method stub

	}

	@Override
	public TerminalDetails getTerminalDetails(String deviceId) {
		return (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.eq("deviceId", deviceId)).setMaxResults(1).uniqueResult();
	}

//	@Override
//	@Transactional(readOnly = false)
//	public TerminalDetails updateReader(TerminalDetails terminalDetails, String olddeviceId) {
//		logger.info("Update TerminalDetails data new device Id " + terminalDetails.getDeviceId() + " old device Id "
//				+ olddeviceId);
//		// String query = "update " + TerminalDetails.class.getName() + " t set
//		// t.contactName ='"+ terminalDetails.getContactName()+"' where t.deviceId
//		// ='"+terminalDetails.getDeviceId()+"'";
//
//		// checking new device id(what we are giving )is present or not start
//		TerminalDetails td = (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class)
//				.add(Restrictions.eq("deviceId", terminalDetails.getDeviceId())).setMaxResults(1).uniqueResult();
//		// olddeviceId != terminalDetails.getDeviceId() is because in some cases we
//		// won't edit device id and we will edit
//		// other things so in that scenerio td!=null satisfied and it will put dup will
//		// get exception
//
//		logger.info(terminalDetails.getDeviceId() + "   " + olddeviceId);
//		if ((td != null) && (!olddeviceId.equalsIgnoreCase(terminalDetails.getDeviceId())))
//
//		{
//			logger.info("Already DeviceId Exists");
//
//			logger.info("device id will change to  " + terminalDetails.getDeviceId() + "--");
//
//			String query = "update TerminalDetails t set t.deviceId = :deviceidchange  where t.deviceId = :deviceId";
//			int updatedEntities = super.getSessionFactory().createQuery(query)
//					.setParameter("deviceidchange", terminalDetails.getDeviceId() + "--")
//
//					.setString("deviceId", terminalDetails.getDeviceId()).executeUpdate();
//
//			if (updatedEntities != 1) {
//				throw new RuntimeException(
//						"Rows updated should always be ONE. Please check (Already DeviceId Exists)HQL Query. SQL Trx is rollbacked. updatedEntities:: "
//								+ updatedEntities);
//			}
//
//		}
//
//		// checking new device id(what we are giving )is present or not end
//
//		String query = "update TerminalDetails t set t.contactName = :contactName ,t.deviceId = :deviceId ,t.activeStatus = :activeStatus where t.deviceId = :olddeviceId";
//		int updatedEntities = super.getSessionFactory().createQuery(query)
//				.setParameter("contactName", terminalDetails.getContactName())
//				.setString("deviceId", terminalDetails.getDeviceId()).setString("olddeviceId", olddeviceId)
//				.setString("activeStatus", terminalDetails.getActiveStatus()).executeUpdate();
//		if (updatedEntities != 1) {
//			throw new RuntimeException(
//					"Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
//							+ updatedEntities);
//		}
//
//		return terminalDetails;
//	}
	@Override
	@Transactional(readOnly = false)
	public TerminalDetails updateReader(TerminalDetails terminalDetails, String olddeviceId) {
		logger.info("Update TerminalDetails data new device Id " + terminalDetails.getDeviceId() + " old device Id "
				+ olddeviceId);
		// String query = "update " + TerminalDetails.class.getName() + " t set
		// t.contactName ='"+ terminalDetails.getContactName()+"' where t.deviceId
		// ='"+terminalDetails.getDeviceId()+"'";

		// checking new device id(what we are giving )is present or not start
		TerminalDetails td = (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.eq("deviceId", terminalDetails.getDeviceId())).setMaxResults(1).uniqueResult();
		// olddeviceId != terminalDetails.getDeviceId() is because in some cases we
		// won't edit device id and we will edit
		// other things so in that scenerio td!=null satisfied and it will put dup will
		// get exception

		logger.info(terminalDetails.getDeviceId() + "   " + olddeviceId);
		if ((td != null) && (!olddeviceId.equalsIgnoreCase(terminalDetails.getDeviceId())))

		{
			logger.info("Already DeviceId Exists");

//			logger.info("device id will change to  " + terminalDetails.getDeviceId() + "--");
//
//			String query = "update TerminalDetails t set t.deviceId = :deviceidchange  where t.deviceId = :deviceId";
//			int updatedEntities = super.getSessionFactory().createQuery(query)
//					.setParameter("deviceidchange", terminalDetails.getDeviceId() + "--")
//
//					.setString("deviceId", terminalDetails.getDeviceId()).executeUpdate();
//
//			if (updatedEntities != 1) {
//				throw new RuntimeException(
//						"Rows updated should always be ONE. Please check (Already DeviceId Exists)HQL Query. SQL Trx is rollbacked. updatedEntities:: "
//								+ updatedEntities);
//			}

		}

		// checking new device id(what we are giving )is present or not end

//		String query = "update TerminalDetails t set t.contactName = :contactName ,t.deviceId = :deviceId ,t.activeStatus = :activeStatus where t.deviceId = :olddeviceId";
//		int updatedEntities = super.getSessionFactory().createQuery(query)
//				.setParameter("contactName", terminalDetails.getContactName())
//				.setString("deviceId", terminalDetails.getDeviceId()).setString("olddeviceId", olddeviceId)
//				.setString("activeStatus", terminalDetails.getActiveStatus()).executeUpdate();
//		DeviceIdApi deviceIdApi = new DeviceIdApi();
//		deviceIdApi.updatyeQuery(terminalDetails.getContactName(), terminalDetails.getDeviceId(), terminalDetails.getActiveStatus());
//		
//		if (updatedEntities != 1) {
//			throw new RuntimeException(
//					"Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
//							+ updatedEntities);
//		}
		try {
			logger.info("ContactName  :" + terminalDetails.getContactName() + "  DeviceId  :"
					+ terminalDetails.getDeviceId() + "  ActiveStatus  :" + terminalDetails.getActiveStatus()
					+ "  olddeviceId  :" + olddeviceId);
			DeviceIdApi dpi = new DeviceIdApi();
			dpi.updatyeQuery(terminalDetails.getContactName(), terminalDetails.getDeviceId(),
					terminalDetails.getActiveStatus(), olddeviceId);
			logger.info("Updated  Successfully");

			return terminalDetails;
		} catch (Exception e) {
			throw new RuntimeException(
					"Rows updated should always be ONE. Please check HQL Query. SQL Trx is rollbacked. updatedEntities:: "
							+ 0);
		}

	}

	@Override
	public TerminalDetails getTerminalDetails1(ArrayList<Criterion> props, String mid) {
		return (TerminalDetails) getSessionFactory().createCriteria(TerminalDetails.class)
				.add(Restrictions.eq("merchantId", mid)).setMaxResults(1).uniqueResult();
	}

	@Override
	public MobileUser getMobileUserName(String tid) {
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class).add(Restrictions.eq("tid", tid))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public MobileUser getMobileUserNames(String tid) {
		logger.info("tid to search: " + tid);
		Disjunction orTid = Restrictions.disjunction();
		/* Add multiple condition separated by OR clause within brackets. */
		orTid.add(Restrictions.eq("tid", tid));
		orTid.add(Restrictions.eq("motoTid", tid));
		orTid.add(Restrictions.eq("ezypassTid", tid));
		orTid.add(Restrictions.eq("ezywayTid", tid));
		orTid.add(Restrictions.eq("ezyrecTid", tid));
		orTid.add(Restrictions.eq("splitTid", tid));
		orTid.add(Restrictions.eq("fiuuTid", tid));

		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class).add(orTid).setMaxResults(1)
				.uniqueResult();
	}

// old
//	@Override
//	public List<ReaderList> getReaderList(Merchant merchant) {
//		
//		String mid=null;
//		String motoMid=null;
//		String ezypassMid=null;
//		String ezyrecMid=null;
//		String ezywayMid=null;
//		String umMid=null;
//		String umMotoMid=null;
//		String umEzypassMid=null;
//		String umEzyrecMid=null;
//		String umEzywayMid=null;
//		
//		
//		if (merchant.getMid().getMid() != null) {
//			mid = merchant.getMid().getMid();
//		}
//		if (merchant.getMid().getMotoMid() != null) {
//			motoMid = merchant.getMid().getMotoMid();
//		}
//		if (merchant.getMid().getEzypassMid() != null) {
//			ezypassMid = merchant.getMid().getEzypassMid();
//		}
//		if (merchant.getMid().getEzyrecMid() != null) {
//			ezyrecMid = merchant.getMid().getEzyrecMid();
//		}
//		if (merchant.getMid().getEzywayMid() != null) {
//			ezywayMid = merchant.getMid().getEzywayMid();
//		}
//		
//		if (merchant.getMid().getUmMid() != null) {
//			umMid = merchant.getMid().getUmMid();
//		}
//		if (merchant.getMid().getUmEzypassMid() != null) {
//			umEzypassMid = merchant.getMid().getUmEzypassMid();
//		}
//		if (merchant.getMid().getUmEzyrecMid() != null) {
//			umEzyrecMid = merchant.getMid().getUmEzyrecMid();
//		}
//		if (merchant.getMid().getUmEzywayMid() != null) {
//			umEzywayMid = merchant.getMid().getUmEzywayMid();
//		}
//		if (merchant.getMid().getUmMotoMid() != null) {
//			umMotoMid = merchant.getMid().getUmMotoMid();
//		}
//
//		List<ReaderList> readerListAll = new ArrayList<ReaderList>();
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		String sql = null;
//		
//		/*String deviceId = null;
//		String tid =null;
//		String deviceHolderName = null;
//		String mobileUserName = null;
//		String activationDate = null;
//		String expiryDate = null;*/
//		
//		/*List<String> deviceId1 = new ArrayList<String>();
//		List<String> tid1 = new ArrayList<String>();
//		List<String> contactName = new ArrayList<String>();
//		
//		List<String> activateDate = new ArrayList<String>();
//		List<String> userName = new ArrayList<String>();*/
//		//List<String> count1 = new ArrayList<String>();
//		/*CommonStatus status = null;*/
//		int count = 0;
//		
//		sql = "select d.DEVICE_ID,d.TID,d.CONTACT_NAME,m.USERNAME,d.ACTIVATED_DATE,d.ACTIVE_STATUS,"
//				+ "d.SUSPENDED_DATE,m.ID,d.DEVICE_TYPE from  TERMINAL_DETAILS d INNER JOIN  "
//				+ " MOBILE_USER m on m.TID = d.TID or m.MOTO_TID=d.TID or m.EZYPASS_TID=d.TID "
//				+ "or m.EZYWAY_TID=d.TID or m.EZYREC_TID=d.TID "
//				+ " where d.MERCHANT_ID in ("+mid+","+motoMid+","+ezypassMid+","+ezyrecMid+","+ezywayMid+","+umMotoMid+","+umEzypassMid+","+umEzyrecMid+","+umEzywayMid+","+umMid+")";
//		
//		
//		
//		logger.info("query:" + sql);
//		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
//		/*sqlQuery.setString("mid", mid);*/
//		
//		List<Object[]> resultSet = sqlQuery.list();
//		
//		
//		logger.info("Size : " + resultSet.size());
//		for (Object[] rec : resultSet) {
//			ReaderList readerList = new ReaderList();
//			//deviceId = rec[0].toString();
//			/*tid = rec[1].toString();*/
//			/*mobileUserName = rec[3].toString();*/
//			
//			if(rec[0]!=null){
//				readerList.setDeviceId(rec[0].toString());
//				//logger.info("deviceholder id: "+readerList.getDeviceId());
//				}
//				if(rec[1]!=null){
//			readerList.setTid(rec[1].toString());}
//			
//			//logger.info("deviceHolderName : " + rec[2]);
//			if(rec[2] != null && rec[2] != ""){
//				
//					readerList.setDeviceHolderName(rec[2].toString());
//				
//			}
//			if(rec[3] != null){
//			readerList.setMobileUserName(rec[3].toString());
//			}
//			
//			if(rec[4] != null){
//				try {
//					readerList.setActivationDate(new SimpleDateFormat("dd-MMM-yyyy")
//					.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//					.parse(rec[4].toString())));
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//				
//			if (rec[5].toString().equals("ACTIVE")) {
//				readerList.setStatus(CommonStatus.ACTIVE.toString());
//		}
//			if(rec[6] != null)
//			{
//				try {
//					readerList.setExpiryDate(new SimpleDateFormat("dd-MMM-yyyy")
//						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
//						.parse(rec[6].toString())));
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//					
//			}
// 			
// 			if(rec[7]!=null)
// 			{
// 				//logger.info("mob user id: "+rec[7].toString());
// 			}
// 			if(rec[8]!=null)
// 			{
// 				//logger.info("device type:  "+rec[8].toString());
// 				readerList.setDeviceType(rec[8].toString());
// 				
// 				if(readerList.getDeviceType().equals("MOTO") || readerList.getDeviceType().equals("UMMOTO") ) {
// 					
// 					if(merchant.getAuth3DS() != null) {
// 						
// 						if(merchant.getAuth3DS().equalsIgnoreCase("Yes")) {
// 	 						
// 	 						readerList.setDeviceType("EZYLINK");
// 	 	 					
// 	 	 				}else {
// 	 						readerList.setDeviceType("EZYMOTO");
// 	 	 					
// 	 	 				}
// 					}else {
// 						readerList.setDeviceType("EZYMOTO");
// 	 					
// 	 				}
// 					
// 					
// 				}else if(readerList.getDeviceType().equals("UMEZYWAY") || readerList.getDeviceType().equals("UMEZYWAY")) {
// 					readerList.setDeviceType("EZYWAY");
// 				}else if(readerList.getDeviceType().equals("RECURRING") || readerList.getDeviceType().equals("EZYREC")) {
// 					readerList.setDeviceType("EZYREC");
// 				}
// 				
// 				
// 				
// 			}else {
// 				readerList.setDeviceType("EZYWIRE");
// 			}
//			count++;
//			
//			readerListAll.add(readerList);
//			
//			
//		}
//		
//		
//		return readerListAll;
//		
//	}

	// new
	@Override
	public List<ReaderList> getReaderList(Merchant merchant) {

		String mid = null;
		String motoMid = null;
		String ezypassMid = null;
		String ezyrecMid = null;
		String ezywayMid = null;
		String umMid = null;
		String umMotoMid = null;
		String umEzypassMid = null;
		String umEzyrecMid = null;
		String umEzywayMid = null;
		String splitmid = null;
		
		String fiuuMid = null;

		if (merchant.getMid().getMid() != null) {
			mid = merchant.getMid().getMid();
		}
		if (merchant.getMid().getMotoMid() != null) {
			motoMid = merchant.getMid().getMotoMid();
		}
		if (merchant.getMid().getEzypassMid() != null) {
			ezypassMid = merchant.getMid().getEzypassMid();
		}
		if (merchant.getMid().getEzyrecMid() != null) {
			ezyrecMid = merchant.getMid().getEzyrecMid();
		}
		if (merchant.getMid().getEzywayMid() != null) {
			ezywayMid = merchant.getMid().getEzywayMid();
		}
		if (merchant.getMid().getSplitMid() != null) {
			splitmid = merchant.getMid().getSplitMid();
		}

		if (merchant.getMid().getUmMid() != null) {
			umMid = merchant.getMid().getUmMid();
		}
		if (merchant.getMid().getUmEzypassMid() != null) {
			umEzypassMid = merchant.getMid().getUmEzypassMid();
		}
		if (merchant.getMid().getUmEzyrecMid() != null) {
			umEzyrecMid = merchant.getMid().getUmEzyrecMid();
		}
		if (merchant.getMid().getUmEzywayMid() != null) {
			umEzywayMid = merchant.getMid().getUmEzywayMid();
		}
		if (merchant.getMid().getUmMotoMid() != null) {
			umMotoMid = merchant.getMid().getUmMotoMid();
		}
		
		if (merchant.getMid().getFiuuMid() != null) {
			fiuuMid = merchant.getMid().getFiuuMid();
		}

		List<ReaderList> readerListAll = new ArrayList<ReaderList>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String sql = null;

		/*
		 * String deviceId = null; String tid =null; String deviceHolderName = null;
		 * String mobileUserName = null; String activationDate = null; String expiryDate
		 * = null;
		 */

		/*
		 * List<String> deviceId1 = new ArrayList<String>(); List<String> tid1 = new
		 * ArrayList<String>(); List<String> contactName = new ArrayList<String>();
		 * 
		 * List<String> activateDate = new ArrayList<String>(); List<String> userName =
		 * new ArrayList<String>();
		 */
		// List<String> count1 = new ArrayList<String>();
		/* CommonStatus status = null; */
		int count = 0;

		sql = "select d.DEVICE_ID,d.TID,d.CONTACT_NAME,m.USERNAME,d.ACTIVATED_DATE,d.ACTIVE_STATUS,"
				+ "d.SUSPENDED_DATE,m.ID,d.DEVICE_TYPE,m.MOTO_API_KEY,d.REF_NO from TERMINAL_DETAILS d INNER JOIN "
				+ " MOBILE_USER m on m.TID = d.TID or m.MOTO_TID=d.TID or m.EZYPASS_TID=d.TID "
				+ "or m.EZYWAY_TID=d.TID or m.EZYREC_TID=d.TID or m.SPLIT_TID=d.TID or m.FIUU_TID=d.TID  "
				+ " where d.MERCHANT_ID in (:mid,:motoMid,:ezypassMid,:ezyrecMid,:ezywayMid,:umMotoMid,:umEzypassMid,:umEzyrecMid,:umEzywayMid,:umMid,:splitmid,:fiuuMid)";

		logger.info("query:" + sql);
		Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		sqlQuery.setString("mid", mid);
		sqlQuery.setString("motoMid", motoMid);
		sqlQuery.setString("ezypassMid", ezypassMid);
		sqlQuery.setString("ezyrecMid", ezyrecMid);
		sqlQuery.setString("ezywayMid", ezywayMid);
		sqlQuery.setString("umMotoMid", umMotoMid);
		sqlQuery.setString("umEzypassMid", umEzypassMid);
		sqlQuery.setString("umEzyrecMid", umEzyrecMid);
		sqlQuery.setString("umEzywayMid", umEzywayMid);
		sqlQuery.setString("umMid", umMid);
		sqlQuery.setString("splitmid", splitmid);
		sqlQuery.setString("fiuuMid", fiuuMid);

		List<Object[]> resultSet = sqlQuery.list();

		if (resultSet.size() == 0) {
			logger.info("Terminal Table data size zero ");
			logger.info("Merchant Id  " + merchant.getId());
			
		//	sql = "SELECT '' as DEVICE_ID ,'' as TID,'' as CONTACT_NAME,u.USERNAME as ACTIVATED_DATE,'' as SUSPENDED_DATE,'' as ID,'' as DEVICE_TYPE,'' as ,u.MOTO_API_KEY,'' as REF_NO FROM mobiversa.merchant r INNER JOIN mobiversa.mobile_user u ON u.MERCHANT_FK= r.ID ";
			sql = "SELECT '' as DEVICE_ID, '' as TID, '' as CONTACT_NAME, u.USERNAME ,'' as ACTIVATED_DATE, '' as ACTIVE_STATUS ,'' as SUSPENDED_DATE, '' as ID, '' as DEVICE_TYPE, u.MOTO_API_KEY ,'' as REF_NO FROM mobiversa.MERCHANT r INNER JOIN mobiversa.MOBILE_USER u ON u.MERCHANT_FK = r.ID AND r.ID = :id";

			logger.info("query:" + sql);
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);
			sqlQuery.setLong("id", merchant.getId());
			resultSet = sqlQuery.list();
		}

		logger.info("Size : " + resultSet.size());
		for (Object[] rec : resultSet) {
			ReaderList readerList = new ReaderList();
			// deviceId = rec[0].toString();
			/* tid = rec[1].toString(); */
			/* mobileUserName = rec[3].toString(); */

			if (rec[0] != null ) {
				readerList.setDeviceId(rec[0].toString());
				// logger.info("deviceholder id: "+readerList.getDeviceId());
			}
			if (rec[1] != null && !rec[1].toString().isEmpty() ) {
				readerList.setTid(rec[1].toString());
			}

			// logger.info("deviceHolderName : " + rec[2]);
			if (rec[2] != null &&  !rec[2].toString().isEmpty() ) {

				readerList.setDeviceHolderName(rec[2].toString());

			}
			if (rec[3] != null && !rec[3].toString().isEmpty() ) {
				readerList.setMobileUserName(rec[3].toString());
			}

			if (rec[4] != null && !rec[4].toString().isEmpty() ) {
				try {
					
					logger.info("activation date : "+rec[4].toString());
					readerList.setActivationDate(new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[4].toString())));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				logger.info("activation date is not : "+rec[4].toString());
				readerList.setActivationDate(" ");
			}

			if (rec[5].toString().equals("ACTIVE")) {
				readerList.setStatus(CommonStatus.ACTIVE.toString());
			}
			if (rec[6] != null && !rec[6].toString().isEmpty()) {
				try {
					readerList.setExpiryDate(new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(rec[6].toString())));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			if (rec[7] != null && !rec[7].toString().isEmpty()) {
				// logger.info("mob user id: "+rec[7].toString());
			}
			if (rec[8] != null && !rec[8].toString().isEmpty()) {
				// logger.info("device type: "+rec[8].toString());
				readerList.setDeviceType(rec[8].toString());

				if (readerList.getDeviceId().equals("MOTO") || readerList.getDeviceType().equals("UMMOTO")) {

					if (merchant.getAuth3DS() != null) {

						if (merchant.getAuth3DS().equalsIgnoreCase("Yes")) {
							if ((merchant.getMid().getSplitMid() != null && !merchant.getMid().getSplitMid().isEmpty())
									&& (readerList.getDeviceId().substring(0, 4).equalsIgnoreCase("SPLIT")))

							{
								readerList.setDeviceType("EZYSPLIT");
							}

							else {
								readerList.setDeviceType("EZYLINK");
							}

						} else {
							readerList.setDeviceType("EZYMOTO");

						}
					} else {
						readerList.setDeviceType("EZYMOTO");

					}

				} else if (readerList.getDeviceType().equals("UMEZYWAY")
						|| readerList.getDeviceType().equals("UMEZYWAY")) {
					readerList.setDeviceType("EZYWAY");
				} else if (readerList.getDeviceType().equals("RECURRING")
						|| readerList.getDeviceType().equals("EZYREC")) {
					readerList.setDeviceType("EZYREC");
				}

			} else {
				readerList.setDeviceType("EZYWIRE");
			}
			if (rec[9] != null && !rec[9].toString().isEmpty()) {

				readerList.setMotoapikey(rec[9].toString());
				logger.info("Moto Api key is : " + rec[9].toString());
			}

			if (rec[10] != null && !rec[10].toString().isEmpty()) {

				readerList.setRefno(rec[10].toString());
				logger.info("Reference no is : " + rec[10].toString());
			}

			
			count++;
			readerListAll.add(readerList);
		}
		return readerListAll;
	}



	@Override
	public void listTerminalDetails(PaginationBean<MobileUserData> paginationBean, String date1, String date2) {

		String sql = null;
		Date dat = null, dat1 = null;
		ArrayList<MobileUserData> listTerminal = new ArrayList<MobileUserData>();
		Query sqlQuery = null;

		if (date1 != null && date2 != null) {

			try {
				dat = new SimpleDateFormat("dd/MM/yyyy").parse(date1);

				dat1 = new SimpleDateFormat("dd/MM/yyyy").parse(date2);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			sql = "select d.DEVICE_ID,d.TID,d.RENEWAL_DATE,d.SUSPENDED_DATE,m.USERNAME,d.ACTIVATED_DATE,"
					+ "d.ACTIVE_STATUS,d.MERCHANT_ID from  TERMINAL_DETAILS d INNER JOIN  "
					+ " MOBILE_USER m on m.TID = d.TID or m.MOTO_TID=d.TID or m.EZYPASS_TID=d.TID or m.EZYWAY_TID=d.TID"
					+ " or m.EZYREC_TID=d.TID or m.FIUU_TID=d.TID "
					+ "where d.ACTIVATED_DATE between :dat  and :dat1 order by d.ACTIVATED_DATE desc";

			logger.info("query:" + sql);
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);
			sqlQuery.setDate("dat", dat);
			sqlQuery.setDate("dat1", dat1);
		} else {

			sql = "select d.DEVICE_ID,d.TID,d.RENEWAL_DATE,d.SUSPENDED_DATE,m.USERNAME,d.ACTIVATED_DATE,"
					+ "d.ACTIVE_STATUS,d.MERCHANT_ID from  TERMINAL_DETAILS d INNER JOIN  "
					+ " MOBILE_USER m on m.TID = d.TID or m.MOTO_TID=d.TID or m.EZYPASS_TID=d.TID  or m.EZYWAY_TID=d.TID"
					+ " or m.EZYREC_TID=d.TID or m.FIUU_TID=d.TID " + "order by d.ACTIVATED_DATE desc";

			logger.info("query:" + sql);
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);

		}

		List<Object[]> resultSet = sqlQuery.list();

		logger.info("Size : " + resultSet.size());
		for (Object[] rec : resultSet) {

			MobileUserData mud = new MobileUserData();

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
				mud.setMid(rec[7].toString());
			}

			listTerminal.add(mud);

		}

		paginationBean.setItemList(listTerminal);

	}

	@Override
	public MobileUser listMobileUser(String id) {
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class).add(Restrictions.eq("id", id))
				.setMaxResults(1).uniqueResult();
	}

	@Override
	public MobileUser listMobileUserId(final Long id) {
		return (MobileUser) getSessionFactory().createCriteria(MobileUser.class).add(Restrictions.eq("id", id))
				.setMaxResults(1).uniqueResult();
	}

	// merchant add
	@SuppressWarnings("unchecked")
	@Override
	public List<MobileUser> loadMobileUser() {

		return (List<MobileUser>) getSessionFactory().createCriteria(MobileUser.class).list();
		// .setMaxResults(1).uniqueResult().;
	}

}
