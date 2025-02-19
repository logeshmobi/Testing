package com.mobiversa.payment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.Reader;
import com.mobiversa.common.bo.ReaderStatusHistory;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.ReaderDao;
import com.mobiversa.payment.dto.ReaderList;

@Service
public class ReaderService {
	@Autowired
	private ReaderDao readerDAO;

	private static final Logger logger = Logger
			.getLogger(ReaderService.class.getName());

	/**
	 * Load a reader based on Primary Key (PK)
	 * 
	 * @param id
	 *            reader table's primary Key
	 */
	public Reader loadReaderByPk(final Long id) {
		Reader reader = readerDAO.loadEntityByKey(Reader.class, id);
		if (reader == null) {
			throw new RuntimeException("Reader Not found. ID:: " + id);
		}
		return reader;
	}
	public TerminalDetails loadTerminalByDevice(final String deviceId){
		TerminalDetails terminal=readerDAO.getTerminalDetails(deviceId);
		if(terminal==null){
			throw new RuntimeException("Terminal Not Found ID:"+ deviceId);
		}
		return terminal;
	}

	/*
	 * public ReaderStatusHistory loadReaderStatusByPk(final Long id) {
	 * ReaderStatusHistory readerStatusHistory = readerDAO.loadEntityByKey(
	 * ReaderStatusHistory.class, id); if (readerStatusHistory == null) { throw
	 * new RuntimeException("Reader Not found. ID:: " + id); } return
	 * readerStatusHistory; }
	 */
	public ReaderStatusHistory loadReaderStatusByPk(final Reader reader) {
		return readerDAO.loadReaderStatusHistoryID(reader);
	}

	public void searchReader(final String serialNumber, final PaginationBean<Reader> paginationBean) {
		readerDAO.findByReaderNames(serialNumber, paginationBean);
	}

	public Boolean updateReaderByPk(final Long id, final Reader reader) {

		Boolean status = false;
		reader.setId(id);
		try {
			Reader modifiedReader = readerDAO.saveOrUpdateEntity(reader);
			status = true;
		} catch (HibernateException e) {
			status = false;
		}
		return status;
	}
	public TerminalDetails loadingTerminalDetailsByDeviceId(final TerminalDetails terminalDetails){
			return terminalDetails;
		}
		
		
	

	/**
	 * List all the reader users
	 * 
	 * @param paginationBean
	 */
	/*
	 * @javax.transaction.Transactional public void listReader(final
	 * PaginationBean<Reader> paginationBean) { ArrayList<Criterion>
	 * criterionList = new ArrayList<Criterion>();
	 * readerDAO.listReaderUser(paginationBean, criterionList); }
	 */
	/*@javax.transaction.Transactional
	public void listReadersMerchant(final PaginationBean<Reader> paginationBean, final Merchant merchant) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (merchant != null) {
			criterionList.add(Restrictions.eq("merchant", merchant));
		}
		readerDAO.listReaderUser(paginationBean, criterionList);
	}*/

	@Transactional
	public void listReadersServiceByMerchant(final PaginationBean<TerminalDetails> paginationBean,final TerminalDetails merchantId){
		ArrayList<Criterion> criterionList= new ArrayList<Criterion>();
		if(merchantId!=null){
			criterionList.add(Restrictions.eq("merchantId",merchantId));
		}
		readerDAO.listReadersOfMerchant(paginationBean, criterionList);
		
	}
	
	@Transactional
	public void listReadersServiceByTerminalDetails(final PaginationBean<TerminalDetails> paginationBean){
		ArrayList<Criterion> criterionlList=new ArrayList<Criterion>();
		readerDAO.getTerminalDetails(paginationBean, criterionlList);
	}
	
	/*@javax.transaction.Transactional
	public void listMerchant(final PaginationBean<Merchant> paginationBean) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		merchantDAO.listMerchantUser(paginationBean, criterionList);
	}
*/
	
	/*// listreaders based on merchant
	@javax.transaction.Transactional
	public void listAllReaders(final PaginationBean<Reader> paginationBean) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		readerDAO.listAllReaders(paginationBean, criterionList);
	}
*/
	public void doSuspendReader(final Long id, final String reason, final String suspendDescription) {
		Reader reader = loadReaderByPk(id);
		if (!CommonStatus.ACTIVE.equals(reader.getStatus())) {
			// reader status isn't active, then how do we suspend reader?
			throw new RuntimeException("unable to suspend a non-active reader");
		}

		CommonStatus status = CommonStatus.SUSPENDED;

		ReaderStatusHistory history = new ReaderStatusHistory();
		history.setReader(reader);
		history.setCreatedDate(new Date());
		history.setReason(reason);
		history.setDescription(suspendDescription);
		history.setStatus(status);
		history.setUserId(reader.getSerialNumber());
		readerDAO.updateReaderStatus(id, status, history);
	}

	public void doUnSuspendReader(final Long id, final String reason, final String unSuspendDescription) {
		Reader reader = loadReaderByPk(id);
		if (!CommonStatus.SUSPENDED.equals(reader.getStatus())) {
			// reader status isn't suspend, then how do we active reader?
			throw new RuntimeException("unable to unsuspend a active reader");
		}

		CommonStatus status = CommonStatus.ACTIVE;

		ReaderStatusHistory history = new ReaderStatusHistory();
		history.setReader(reader);
		history.setCreatedDate(new Date());
		history.setReason(reason);
		history.setDescription(unSuspendDescription);
		history.setStatus(status);
		history.setUserId(reader.getSerialNumber());
		readerDAO.updateReaderStatus(id, status, history);
	}

	/* adding a reader */
	public Reader addReaderUser(final Reader entity) {
		System.out.println("entity" + entity.getMerchant());
		entity.setStatus(CommonStatus.ACTIVE);
		// entity.setPassword(encoder.encode(entity.getPassword()));

		return readerDAO.saveOrUpdateEntity(entity);

	}
	/*
	 * @javax.transaction.Transactional public void listReader(final
	 * PaginationBean<Reader> paginationBean, final Merchant merchant) {
	 * ArrayList<Criterion> criterionList = new ArrayList<Criterion>(); if
	 * (merchant != null) { criterionList.add(Restrictions.eq("merchant",
	 * merchant)); } readerDAO.listReaderUser(paginationBean, criterionList); }
	 */

	public TerminalDetails loadTerminalDetails(String merchantId) {
	return readerDAO.loadTerminalDetails(merchantId);
	}
	/**
	 * Load Terminal Details Based On Merchant Id
	 * With pagination
	 */
	public void getTerminalDetails(final
			  PaginationBean<TerminalDetails> paginationBean, final String mid){
		 ArrayList<Criterion> criterionList = new ArrayList<Criterion>(); 

		  criterionList.add(Restrictions.eq("merchantId",  mid));
		  readerDAO.getTerminalDetails(paginationBean, criterionList);
	}
	
	
	
	//new method for mobile user name 31072017
	public TerminalDetails getTerminalDetails1(final String mid){
		 ArrayList<Criterion> criterionList = new ArrayList<Criterion>(); 

		  criterionList.add(Restrictions.eq("merchantId",  mid));
		 return readerDAO.getTerminalDetails1(criterionList,mid);
	}
	
	
	
	public MobileUser getMobileUserName(final String tid){
		 ArrayList<Criterion> criterionList = new ArrayList<Criterion>(); 

		 // criterionList.add(Restrictions.eq("tid",  tid));
		return readerDAO.getMobileUserName(tid);
		
	}
	
	public MobileUser getMobileUserNames(final String tid){
		 ArrayList<Criterion> criterionList = new ArrayList<Criterion>(); 
		 logger.info("tid to search: "+tid);
		 // criterionList.add(Restrictions.eq("tid",  tid));
		// return readerDAO.getMobileUserName(tid);
		 return readerDAO.getMobileUserNames(tid);
	}
	
	
	public List<ReaderList> getReaderList(Merchant merchant){
		// ArrayList<Criterion> criterionList = new ArrayList<Criterion>(); 

		  //criterionList.add(Restrictions.eq("merchantId",  mid));
		//System.out.println("check details:");
		 return readerDAO.getReaderList(merchant);
	}
	
	
	
	@SuppressWarnings("unused")
	public TerminalDetails updateReader(final TerminalDetails terminalDetails,String olddeviceId) 
	{
		
		//logger.info("Service : about to list all merchant1");
		//terminalDetails.setStatus(CommonStatus.ACTIVE);
		//logger.info("Service : about to list all merchant2");
		  return readerDAO.updateReader(terminalDetails,olddeviceId);
		//return merchantDAO.saveOrUpdateEntity(merchant);

	}
	/*@SuppressWarnings("unused")
	public AuditTrail UpdateAuditTrail(String tid)
	{
		MobileUser mobileuser=getMobileUserName(tid);
		logger.info("UpdateAuditTrail username: "+mobileuser.getUsername());
		
		AuditTrail auditTrail=new AuditTrail();
		auditTrail.setDescription("Reader Details Edited");
		auditTrail.setUserType("READER");
		auditTrail.setAction(AuditTrailAction.MERCHANT_EDIT_READER);
		auditTrail.setCreatedBy(mobileuser.getMerchant().getUsername());
		auditTrail.setCreatedDate(mobileuser.getCreatedDate());
		auditTrail.setModifiedBy(mobileuser.getMerchant().getUsername());
		
		
		java.util.Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formats = formatter.format(date);
		logger.info("modified date first: "+formats);
		Date modifiedDate =null;
		
		try {
			modifiedDate = formatter.parse(formats);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("modified date string: "+formats);
        
        
		logger.info("modified date daete: "+formatter.format(modifiedDate));
       	auditTrail.setModifiedDate(new Date());
		
		auditTrail.setStatus(CommonOperationStatus.SUCCESS);
		
		
		auditTrail.setUsername(mobileuser.getUsername());
	
		if(auditTrail.getDescription()!=null){
		logger.info("auditTrail.getDescription(): "+auditTrail.getDescription());}
		
		if(auditTrail.getUsername()!=null){
			logger.info("auditTrail.getDescription(): "+auditTrail.getUsername());}
		if(auditTrail.getUserType()!=null){
		logger.info("auditTrail.getUserType(): "+auditTrail.getUserType());}
		if(auditTrail.getCreatedDate()!=null){
		logger.info("auditTrail.getCreatedDate(): "+auditTrail.getCreatedDate());}
		if(auditTrail.getModifiedDate()!=null){
		logger.info("auditTrail.getModifiedDate(): "+auditTrail.getModifiedDate());}
		
		if(auditTrail.getStatus()!=null){
		logger.info("auditTrail.getStatus(): "+auditTrail.getStatus());}
		
		
		if(auditTrail.getModifiedBy()!=null){
		logger.info("auditTrail.getModifiedBy(): "+auditTrail.getModifiedBy());}
		
		if(auditTrail.getAction()!=null){

		logger.info("auditTrail.getAction(): "+auditTrail.getAction());}
		
		if(auditTrail.getCreatedBy()!=null){
		logger.info("auditTrail.getCreatedBy(): "+auditTrail.getCreatedBy());}
		

		
		return readerDAO.saveOrUpdateEntity(auditTrail);
		
	} */

	
	
	
	
	//new method for mobile user change password in merchant portal 15082017
	
	//new method for edit mobile user 
			public MobileUser listMobileUser(final String id) {
				return readerDAO.listMobileUser(id);
			}
			
			
			
			//new method for edit mobile user 
			public MobileUser listMobileUserId( final Long id) {
				return readerDAO.listMobileUserId(id);
			}
//new method for mobile user 23082017
			
			public List<MobileUser> loadMobileUser() {
				return readerDAO.loadMobileUser();
			} 

			
			
			
}
