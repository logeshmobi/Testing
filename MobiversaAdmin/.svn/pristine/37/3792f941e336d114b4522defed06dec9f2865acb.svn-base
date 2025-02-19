package com.mobiversa.payment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.MonthlyTxnDao;
import com.mobiversa.payment.dto.MonthlyTxnDetails;

@Service
public class MonthlyTransactionService {
	
	@Autowired
	private MonthlyTxnDao monthlyTxnDao;
	
	
	//@javax.transaction.Transactional
	public void monthlyTxnMerchantList(final PaginationBean<MonthlyTxnDetails> paginationBean,final String month,final String year) {
		System.out.println("Inside  monthly Transaction List");
		
		monthlyTxnDao.getMonthlyTxnDetails(paginationBean, month, year);
	}
	
	public List<MonthlyTxnDetails> monthlyTxnMerchantExport(final String month,final String year) {
		System.out.println("Inside  monthly Transaction Export");
		 return monthlyTxnDao.getMonthlyTxnExport(month, year);
	}

}
