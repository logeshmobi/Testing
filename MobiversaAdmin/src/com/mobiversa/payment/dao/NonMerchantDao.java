package com.mobiversa.payment.dao;

import java.util.ArrayList;

import org.hibernate.criterion.Criterion;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PaginationBean;

public interface NonMerchantDao extends BaseDAO {
	
	public void findByUserNames(final String businessName, final PaginationBean<Merchant> paginationBean);

	public void listNonMerchantUser(PaginationBean<Merchant> paginationBean, ArrayList<Criterion> props);
	
	public Merchant loadNonMerchant(String username);

	public int changeNonMerchantPassWord(String Username,String newPwd,String OldPwd);

	
	/*public void updateNonMerchantStatus(Long id, CommonStatus status, NonMerchantStatusHistory history);

	public NonMerchantStatusHistory loadNonMerchantStatusHistoryID(NonMerchant nonmerchant);

	
	public NonMerchant loadNonMerchant(MID mid);
	
	
	
	public NonMerchant loadNonMerchantbyEmail(String email);
	
	public int updateMIDData(Long m_id,Long nonmerchant_id);

	public List<NonMerchant> loadNonMerchant();
	
	
	
	public void listAgentNonMerchant(PaginationBean<NonMerchant> paginationBean, ArrayList<Criterion> props);
	  
	
	public MID loadMid(String mid);
	
	public void listNonMerchantUser1(PaginationBean<NonMerchant> paginationBean, ArrayList<Criterion> props);
	
	
	public FileUpload loadFileById(String id);
	
	
	public FileUpload updateFileById(FileUpload fileUpload);
	
	public List<FileUpload> loadFileByNonMerchantId(String nonmerchId);
	
	
	
public NonMerchant loadNonMerchantDetails(String username);


//new method for merchant summary search condition 16062017


public void listNonMerchantSearch(PaginationBean<NonMerchant> paginationBean, ArrayList<Criterion> props);

	


public List<NonMerchant> listNonMerchantSummary(ArrayList<Criterion> props,final String date,
		final String date1);

*/

	

}
