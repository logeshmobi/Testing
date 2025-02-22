package com.mobiversa.payment.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.Reader;
import com.mobiversa.common.bo.ReaderStatusHistory;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.MobileUserData;
import com.mobiversa.payment.dto.ReaderList;

public interface ReaderDao extends BaseDAO {


	public void findByReaderNames(final String serialNumber, final PaginationBean<Reader> paginationBean);

	//public void listReaderUser(PaginationBean<Reader> paginationBean, ArrayList<Criterion> props);
public void	listReadersOfMerchant(PaginationBean<TerminalDetails> paginationBean,ArrayList<Criterion> props);

	public void listAllReaders(PaginationBean<Reader> paginationBean, ArrayList<Criterion> props);

	public void updateReaderStatus(Long id, CommonStatus status, ReaderStatusHistory history);

	public ReaderStatusHistory loadReaderStatusHistoryID(Reader reader);

	TerminalDetails loadTerminalDetails(String merchantId);

	void getTerminalDetails(PaginationBean<TerminalDetails> paginationBean, ArrayList<Criterion> props);
	
public	void listTerminalsByDeviceId(final PaginationBean<TerminalDetails> paginationBean, ArrayList<Criterion> props);

public TerminalDetails getTerminalDetails(String deviceId);

public TerminalDetails updateReader(TerminalDetails terminalDetails,String olddeviceId);



public  TerminalDetails  getTerminalDetails1(ArrayList<Criterion> props,final String mid);
public MobileUser getMobileUserName(final String tid);


//public List<ReaderList> getReaderList(final String mid);


public MobileUser listMobileUser(final String username);



public MobileUser listMobileUserId(final Long id);


public List<MobileUser> loadMobileUser(); 

public void listTerminalDetails(PaginationBean<MobileUserData> paginationBean, String date1, String date2);

public List<ReaderList> getReaderList(Merchant merchant);

public MobileUser getMobileUserNames(String tid);


}
