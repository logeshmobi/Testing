package com.mobiversa.payment.dao;



import java.util.ArrayList;
import java.util.List;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface MasterSearchDao {
	

	ArrayList<Object> masterSearchByFPX(PaginationBean<Object> paginationBean,Merchant merchantObj,String merchant,String chooseType,
															   List<String> listOfValues);

	ArrayList<Object>masterSearchByPayout(PaginationBean<Object> paginationBean,String merchantID, List<String> listOfSearchValue, int currentPage,String search_type);

	 ArrayList<Object> searchSppTransactionByInvoiceIdOrTngTxnID(final PaginationBean<Object> paginationBean,
														  Merchant merchant, List<String> searchValue, String payment_type,String chooseType);


	ArrayList<Object> searchTngTransactionByInvoiceIdOrTngTxnID(final PaginationBean<Object> paginationBean,
																Merchant merchant, List<String> searchValue, String payment_type,String chooseType);


	public ArrayList<Object> searchGrabTransactionByReferenceNoOrRrnOrApprovalCode(final PaginationBean<Object> paginationBean,
																				   Merchant merchant, List<String> searchValue, String payment_type,String chooseType);
	
	ArrayList<Object> searchQrTransactionByReferenceNoOrApprovalCode(final PaginationBean<Object> paginationBean,
			Merchant merchant, List<String> searchValue, String payment_type,String chooseType);

	ArrayList<Object> searchBoostTransactionByReferenceNoOrRrnOrApprovalCode(
			final PaginationBean<Object> paginationBean,
			Merchant merchant, List<String> searchValue, String payment_type,String chooseType) ;




	 ArrayList<Object> searchCardByReferenceNoOrApprovalCodeOrRrnOrCardNumber(
			final PaginationBean<Object> paginationBean, Merchant merchant, List<String> searchList,
			String searchType);
}


