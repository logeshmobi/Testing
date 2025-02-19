package com.mobiversa.payment.controller.bean;

import com.mobiversa.payment.dto.PayoutAccountEnquiryDto;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PaginationBean<T> {

	private int currPage;
	// default to 12
	private int itemsPerPage = 3000;
	private List<T> itemList;
	private long totalRowCount = 0;
	
	private List<String> itemListt;
	private List<String> filenamelist; 
	
	private List<String> dateList;
	private List<String> nameList;
	
    private int fullCount;
	private String dateFromBackend;
	private String date1FromBackend;
	
	private String selectType;
	private String TXNtype;
	
	private String deviceID;
	private String TID2;
	private String Status;
	
	private String responseCode;
	
    private String querySize;
	private String ChooseType;
	private String ewalletPaymentType;

	private PayoutAccountEnquiryDto payoutAccountEnquiryDto;

	
	
	

	public String getChooseType() {
		return ChooseType;
	}

	public void setChooseType(String chooseType) {
		ChooseType = chooseType;
	}

	public String getEwalletPaymentType() {
		return ewalletPaymentType;
	}

	public void setEwalletPaymentType(String ewalletPaymentType) {
		this.ewalletPaymentType = ewalletPaymentType;
	}

	public String getQuerySize() {
		return querySize;
	}

	public String setQuerySize(String querySize) {
		 return this.querySize = querySize;
	}


	
	

	public String getSelectType() {
		return selectType;
	}

	public String setSelectType(String selectType) {
		return this.selectType = selectType;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}

	public String getTID2() {
		return TID2;
	}

	public String setTID2(String tID2) {
		return this.TID2 = tID2;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public int getFullCount() {
		return fullCount;
	}

	public Integer setFullCount(int fullCount) {
		return this.fullCount = fullCount;
	}

	public String getDateFromBackend() {
		return dateFromBackend;
	}

	public String setDateFromBackend(String dateFromBackend) {
		return this.dateFromBackend = dateFromBackend;
	}

	public String getDate1FromBackend() {
		return date1FromBackend;
	}

	public String setDate1FromBackend(String date1FromBackend) {
		return this.date1FromBackend = date1FromBackend;
	}


	public String getTXNtype() {
		return TXNtype;
	}

	public String setTXNtype(String tXNtype) {
		return this.TXNtype = tXNtype;
	}
	
	public List<String> getDateList() {
		return dateList;
	}

	public void setDateList(List<String> dateList) {
		this.dateList = dateList;
	}

	public List<String> getNameList() {
		return nameList;
	}

	public void setNameList(List<String> nameList) {
		this.nameList = nameList;
	}

	public List<String> getItemListt() {
		return itemListt;
	}

	public void setItemListt(List<String> itemListt) {
		this.itemListt = itemListt;
	}

	public List<String> getFilenamelist() {
		return filenamelist;
	}

	public void setFilenamelist(List<String> filenamelist) {
		this.filenamelist = filenamelist;
	}

	public static int getTotalpagination() {
		return totalPagination;
	}

	public final int getStartIndex() {
		return ((currPage * itemsPerPage) - itemsPerPage);
	}

	public final int getTableViewRowNumber(final int row) {
		return getStartIndex() + row;
	}

	public final int getCurrPage() {
		return currPage;
	}

	public final void setCurrPage(final int currPage) {
		this.currPage = currPage;
	}

	public final int getItemsPerPage() {
		return itemsPerPage;
	}

	/**
	 * Set the total number of items to query, default to 15
	 * 
	 * @param itemsPerPage
	 */
	public final void setItemsPerPage(final int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public final List<T> getItemList() {
		return itemList;
	}

	public final void setItemList(final List<T> itemList) {
		this.itemList = itemList;
	}

	public final long getTotalRowCount() {
		return totalRowCount;
	}

	public final void setTotalRowCount(final long totalRowCount) {
		this.totalRowCount = totalRowCount;
	}

	public final int getTotalOfPages() {
		if (itemList == null) {
			return 0;
		}

		return new Long(Math.round(Math.ceil((1d * totalRowCount) / itemsPerPage))).intValue();
	}

	public final boolean isNextPageAvailable() {
		return currPage < getTotalOfPages();
	}

	public final boolean isPreviousPageAvailable() {
		return currPage == 1 ? false : currPage <= getTotalOfPages();
	}

	private static final int totalPagination = 5;

	public final ArrayList<Integer> getPaginationNumbers() {

		TreeSet<Integer> list = new TreeSet<Integer>();
		int startPoint = getCurrPage();
		list.add(startPoint);
		// int totalNumberOfNext = getTotalOfPages() - getCurrPage();
		int totalNumberofPrevious = getCurrPage() - 1;
		// add two previous page first
		for (int q = Math.min(totalNumberofPrevious, 2); q > 0; q--) {
			list.add(--startPoint);
		}

		startPoint = getCurrPage();
		for (int q = (totalPagination - list.size()); ((q > 0) && (startPoint < getTotalOfPages())); q--) {
			list.add(++startPoint);
		}

		for (int q = totalPagination; (list.size() != totalPagination) && (list.first() != 1); q--) {
			list.add(list.first() - 1);
		}
		return new ArrayList<Integer>(list);
	}

	
	@Override
	public String toString() {
		return "PaginationBean [currPage=" + currPage + ", itemsPerPage=" + itemsPerPage + ", totalRowCount="
				+ fullCount + ", dateFromBackend=" + dateFromBackend + ", date1FromBackend=" + date1FromBackend
				+ ", TXNtype=" + TXNtype + "]";
	}

	public PayoutAccountEnquiryDto getPayoutAccountEnquiryDto() {
		return payoutAccountEnquiryDto;
	}

	public void setPayoutAccountEnquiryDto(PayoutAccountEnquiryDto payoutAccountEnquiryDto) {
		this.payoutAccountEnquiryDto = payoutAccountEnquiryDto;
	}
}
