package com.mobiversa.payment.dao;

import com.mobiversa.common.bo.PushMessage;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface PushNotificationDao extends BaseDAO {

//	public void saveOrUpdateEntity(PushMessage push);
	
	public void listAllNotificationsbyAdmin(
			PaginationBean<PushMessage> paginationBean, String date, String date1,String status);
	
	public PushMessage getNotification(String trxId);

}
