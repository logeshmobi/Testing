package com.mobiversa.payment.dao;

import java.util.ArrayList;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.BankUser;
import com.mobiversa.common.bo.BankUserStatusHistory;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface WebUserDao extends BaseDAO {
	
	public void findByUserNames(final String username, final PaginationBean<BankUser> paginationBean);

	public BankUser findByUserName(final String username);

	void listBankUser(PaginationBean<BankUser> paginationBean, ArrayList<Criterion> props);

	BankUser loadBankUserByID(long id);
	
	public BankUser loadBankUser(String userName);

	public BankUserStatusHistory loadBankUserStatusHistoryID(BankUser bankUser);

	public void updateBankUserStatus(Long id, CommonStatus status, BankUserStatusHistory history);
	
	public int changeUserPassWord(String Username,String newPwd,String OldPwd);

}
