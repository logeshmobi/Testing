package com.mobiversa.payment.dao;

import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.ReportForSettlement;

public interface ReportDataDAO {
	
	public void getDetails(final PaginationBean<ReportForSettlement> paginationBean,String days);
	
	void getDetailsUM(PaginationBean<ReportForSettlement> paginationBean, String days);

}
