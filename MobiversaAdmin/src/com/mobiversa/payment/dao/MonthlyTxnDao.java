package com.mobiversa.payment.dao;

import java.util.List;

import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.MonthlyTxnDetails;

public interface MonthlyTxnDao {
	
	public void getMonthlyTxnDetails(final PaginationBean<MonthlyTxnDetails> paginationBean,String month,String year);
	
	public List<MonthlyTxnDetails> getMonthlyTxnExport(String month,String year);

}
