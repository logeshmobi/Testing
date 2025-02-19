package com.mobiversa.payment.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobiversa.common.bo.PushMessage;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.PushNotificationDao;

@Service
public class PushNotificationService {
	
	@Autowired
	private PushNotificationDao pushNotificationDAO;
	
	private static final Logger logger=Logger.getLogger(PushNotificationService.class.getName());

	public void addNotification(PushMessage push) {
		pushNotificationDAO.saveOrUpdateEntity(push);
	}
	
	public void listAllNotificationsbyAdmin(
			PaginationBean<PushMessage> paginationBean, String fromDate1,
			String toDate1, String status) {

		pushNotificationDAO.listAllNotificationsbyAdmin(paginationBean, fromDate1, toDate1, status);

	}
	
		public PushMessage getNotification(String trxId) {
			return pushNotificationDAO.getNotification(trxId);
		}
		
		public void updateNotification(PushMessage push) {
			pushNotificationDAO.saveOrUpdateEntity(push);
		}

}
