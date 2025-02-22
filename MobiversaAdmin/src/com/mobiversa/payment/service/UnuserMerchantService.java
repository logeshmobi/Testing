package com.mobiversa.payment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.ReportDataDAO;
import com.mobiversa.payment.dto.ReportForSettlement;

@Service
public class UnuserMerchantService {
	
	@Autowired
	private ReportDataDAO reportDataDAO;
	
	
	//@javax.transaction.Transactional
	public void unusedMerchantList(final PaginationBean<ReportForSettlement> paginationBean,final String days) {
		System.out.println("Inside  unusedMerchantList");
		reportDataDAO.getDetails(paginationBean,days);
	}
	public void unusedMerchantListUM(final PaginationBean<ReportForSettlement> paginationBean,final String days) {
		
		System.out.println("Inside  unusedMerchantList");
		reportDataDAO.getDetailsUM(paginationBean,days);
	}
}
