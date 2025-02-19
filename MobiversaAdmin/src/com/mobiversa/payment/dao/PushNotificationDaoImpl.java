package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.PushMessage;
import com.mobiversa.payment.controller.bean.PaginationBean;

@Component
@Repository
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class PushNotificationDaoImpl extends BaseDAOImpl implements PushNotificationDao{

	public void listAllNotificationsbyAdmin(PaginationBean<PushMessage> paginationBean, 
			String fromDate, String toDate, String status) {

		logger.info("inside listAllTransactionDetailsbyAdmin " + " from date: " + fromDate + "toDate: " + toDate
				+ "status: " + status);

		ArrayList<PushMessage> fss = new ArrayList<PushMessage>();
		String sql = null;
		Query sqlQuery = null;
		Date date = new Date();


		if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())
				&& (!status.isEmpty() && status != null)) {
			logger.info("inside date and status criteria: " + "from date: " + fromDate + "toDate: " + toDate
					+ "status: " + status);

			sql = "select ID,ACTION_DATE,ACTION_TIME,MSG_TITLE,STATUS from PUSH_MESSAGE WHERE STATUS = :status  and ACTIVATE_DATE between :fromDate  and :toDate  order by ACTIVATE_DATE desc limit 1000";
			
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);
			sqlQuery.setString("status", status);
			sqlQuery.setString("fromDate", fromDate);
			sqlQuery.setString("toDate", toDate);

		}

		else if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())) {
			logger.info(
					"inside date criteria: " + "from date: " + fromDate + "toDate: " + toDate + "status: " + status);

			sql = "select ID,ACTION_DATE,ACTION_TIME,MSG_TITLE,STATUS from PUSH_MESSAGE WHERE  ACTIVATE_DATE between :fromDate  and :toDate  order by ACTIVATE_DATE desc limit 1000";
			
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);
			// sqlQuery.setString("status", status);
			sqlQuery.setString("fromDate", fromDate);
			sqlQuery.setString("toDate", toDate);
		}

		else {
			logger.info("from date: " + fromDate + "toDate: " + toDate + "status: " + status);
			
			sql = "select ID,ACTION_DATE,ACTION_TIME,MSG_TITLE,STATUS from PUSH_MESSAGE order by ACTIVATE_DATE desc limit 1000";
			sqlQuery = super.getSessionFactory().createSQLQuery(sql);

		}

		logger.info("Query : " + sql);
		@SuppressWarnings("unchecked")
		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			PushMessage fs = new PushMessage();
			
			if (rec[0] != null) {
				// notification ID
				fs.setId(Long.parseLong(rec[0].toString()));
			}
			if (rec[1] != null) {
				// Action Date
				fs.setActionDate(rec[1].toString());
			}
			if (rec[2] != null) {
				// Action Time
				fs.setActionTime(rec[2].toString());
			}
			if (rec[3] != null) {
				// Message Title
				fs.setMsgTitle(rec[3].toString());
			}
			if (rec[4] != null) {
				// Message Status
				if (rec[4].toString().equals("SUBMITTED")) {
					fs.setStatus(CommonStatus.SUBMITTED);
				}
				if (rec[4].toString().equals("APPROVED")) {
					fs.setStatus(CommonStatus.APPROVED);
				}
				if (rec[4].toString().equals("REJECTED")) {
					fs.setStatus(CommonStatus.REJECTED);
				}
				if (rec[4].toString().equals("SENT")) {
					fs.setStatus(CommonStatus.SENT);
				}
			}
			
			fss.add(fs);
		}
		paginationBean.setItemList(fss);
		// paginationBean.setTotalRowCount(fss.size());
	}

	
	@Override
	public PushMessage getNotification(String Id) {
		Long trx = new Long(Id);
		PushMessage fSettlement = (PushMessage) sessionFactory
				.getCurrentSession().createCriteria(PushMessage.class)
				.add(Restrictions.eq("id", trx)).setMaxResults(1)
				.uniqueResult();
		return fSettlement;

	}
	
}
