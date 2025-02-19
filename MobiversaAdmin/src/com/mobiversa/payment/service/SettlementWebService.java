package com.mobiversa.payment.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.connect.PaymentCommunication;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.SettlementWebDao;


@Service
public class SettlementWebService {
	
	private static Logger logger = Logger.getLogger(SettlementWebService.class);
	@Autowired
	private SettlementWebDao settlementWebDAO;
	
	/*@Autowired
	private MerchantDao merchantDao;*/
	
	//@javax.transaction.Transactional
	public void listSettlements(final PaginationBean<ForSettlement> paginationBean, String merchantName) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		settlementWebDAO.listSettlement(paginationBean, criterionList,merchantName);
	}
	
	//@javax.transaction.Transactional
	public String sentSettlement(final String userName,final String tid, Merchant merchant) {
		
		String deviceId =  settlementWebDAO.getDeviceIdByTid(tid);
		
		//String merchant.getMid();
		System.out.println(" Data :"+ deviceId);
		
		PaymentCommunication payment = new PaymentCommunication();
		
		String resData =payment.sendWebSettlement(userName, deviceId,merchant,tid);
		logger.info("Res data :"+ resData);
		resData = "Settlement for TID :  "+tid + " - "+resData;
		logger.info("Res data tid:"+ resData +":"+tid);
		return resData;
	}

	
	
	
}
