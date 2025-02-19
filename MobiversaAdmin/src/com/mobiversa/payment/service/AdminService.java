package com.mobiversa.payment.service;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.AuditTrailAction;
import com.mobiversa.common.bo.BankUser;
import com.mobiversa.common.bo.BankUserStatusHistory;
import com.mobiversa.common.bo.CommonOperationStatus;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.UserDao;

@Service
public class AdminService {
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private UserDao userDAO;
	
	  private static final Logger logger=Logger.getLogger(AdminService.class.getName());

	public void listBankUser(final PaginationBean<BankUser> paginationBean) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		userDAO.listBankUser(paginationBean, criterionList);
	}

	public void searchBankUser(final String username, final PaginationBean<BankUser> paginationBean) {
		userDAO.findByUserNames(username, paginationBean);
	}

	public BankUser loadBankUser(final long id) {
		return userDAO.loadBankUserByID(id);
	}

	public BankUser addBankUser(final BankUser entity) {
		entity.setEnabled(true);
		entity.setStatus(CommonStatus.ACTIVE);
		entity.setPassword(encoder.encode(entity.getPassword()));

		return userDAO.saveOrUpdateEntity(entity);

	}
	
	public BankUser validateAdminEmailId(String emailId) {
		
		
		return userDAO.validateAdminEmailId(emailId);
	}

	@SuppressWarnings("unchecked")
	public void updateBankUser(final BankUser bankUser) {
		bankUser.setStatus(CommonStatus.ACTIVE);

		userDAO.saveOrUpdateEntity(bankUser);

	}

	public void doSuspendBankUser(final Long id, final String reason, final String suspendDescription) {
		BankUser bankUser = loadBankUser(id);
		if (!CommonStatus.ACTIVE.equals(bankUser.getStatus())) {
			// merchant status isn't active, then how do we suspend merchant?
			throw new RuntimeException("unable to suspend a non-active merchant");
		}

		CommonStatus status = CommonStatus.SUSPENDED;
		BankUserStatusHistory history = new BankUserStatusHistory();
		history.setBankUser(bankUser);

		history.setCreatedDate(new Date());
		history.setReason(reason);
		history.setDescription(suspendDescription);
		history.setStatus(status);
		history.setId(id);
		userDAO.updateBankUserStatus(id, status, history);
	}

	public void doUnSuspendBankUser(final Long id, final String reason, final String suspendDescription) {
		BankUser bankUser = loadBankUser(id);
		if (!CommonStatus.SUSPENDED.equals(bankUser.getStatus())) {
			// merchant status isn't active, then how do we suspend merchant?
			throw new RuntimeException("unable to suspend a non-active merchant");
		}

		CommonStatus status = CommonStatus.ACTIVE;
		BankUserStatusHistory history = new BankUserStatusHistory();
		history.setBankUser(bankUser);
		history.setCreatedDate(new Date());
		history.setReason(reason);
		history.setDescription(suspendDescription);
		history.setStatus(status);
		history.setId(id);
		userDAO.updateBankUserStatus(id, status, history);
	}

	public BankUserStatusHistory loadBankUserStatusHistoryID(final BankUser bankUser) {
		return userDAO.loadBankUserStatusHistoryID(bankUser);

	}
	
	
	public BankUser loadBankUser(final String username) {
		logger.info("username :"+username);
		return userDAO.loadBankUser(username);
	}
	
	public Merchant loadAdminMerchant(final String username) {
		logger.info("username loadAdminMerchant:"+username);
		return userDAO.loadadmmerchant(username);
	}
	
	public int changeAdmMerchPassWord(String Username,String newPwd,String OldPwd){
		
		
		logger.info("Admin Merch :"+Username+" "+newPwd+" "+OldPwd);
		
		
		
		Merchant merchant = (Merchant) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		
		logger.info("Admin Merch :"+merchant);
						
		boolean matches = encoder.matches(OldPwd,merchant.getPassword()); // Comparing previous password with user given previous password
		
		boolean matches1 = encoder.matches(newPwd,merchant.getPassword()); //Comparing previous password with new password
//		boolean matches2 = encoder.matches(newPwd,merchant.getPassword1()); //Comparing previous password1 with new password
//		boolean matches3 = encoder.matches(newPwd,merchant.getPassword2()); //Comparing previous password2 with new password
		
		if((matches==true)&& (matches1== true) ){
			int n=	userDAO.changeAdminMerchPassWord(merchant.getUsername(), encoder.encode(newPwd), encoder.encode(OldPwd));
			return n;
		}else{
			return 0;
		}
	}
	
	
	
	// Change password with history
	public int changeUserPassWord(String Username, String newPwd, String OldPwd) {

		BankUser user = (BankUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		logger.info("Admin User :" + user);
		boolean matches = encoder.matches(OldPwd, user.getPassword());
		// Comparing previous password with user given previous password

		boolean matches1 = encoder.matches(newPwd, user.getPassword()); // Comparing previous password with new password
		boolean matches2 = encoder.matches(newPwd, user.getPassword1()); // Comparing previous password1 with new
																			// password
		boolean matches3 = encoder.matches(newPwd, user.getPassword2());// Comparing previous password2 with new
																		// password
		//boolean matches4 = encoder.matches(newPwd,user.getPassword3());	//Comparing previous password3 with new password

		if ((matches == true) && (matches1 != true) && (matches2 != true) && (matches3 != true) ) {
			int n = userDAO.changeUserPassWord(Username, encoder.encode(newPwd), encoder.encode(OldPwd),
					user.getPassword1());
			return n;
		} else {
			return 0;
		}
	}
		
		public AuditTrail updateAuditTrailByAdmin(String userName,
				String adminName,String auditTrailAction) {
			
			AuditTrail auditTrail=new AuditTrail();
			auditTrail.setCreatedBy(null);
			auditTrail.setCreatedDate(null);
			auditTrail.setStatus(CommonOperationStatus.SUCCESS);
			auditTrail.setUsername(userName);
			auditTrail.setModifiedBy(adminName);
			auditTrail.setModifiedDate(new Date());
			switch(auditTrailAction){
			
			case "RecurringStatusUpdate":
				auditTrail.setDescription("Recurr Status Updated by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_RECUR_STATUS_UPDATE);
				auditTrail.setUserType("MERCHANT");
				break;
			case "addAgent":
				auditTrail.setDescription("Agent Added by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_ADD_AGENT);
				auditTrail.setUserType("AGENT");
				break;
				//completed
			case "addMerchant":
				auditTrail.setDescription("Merchant Added by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_ADD_MERCHANT);
				auditTrail.setUserType("MERCHANT");
				break;
				//completed
			case "addSubMerchant":
				auditTrail.setDescription("Sub Merchant Added by Merchant");
				auditTrail.setAction(AuditTrailAction.ADMIN_ADD_MERCHANT);
				auditTrail.setUserType("SUBMERCHANT");
				break;
				//completed
			case "addMobileUser":
				auditTrail.setDescription("MobileUser Added by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_ADD_MOBILEUSER);
				auditTrail.setUserType("MOBILEUSER");
				break;
				
			case "addMDRDetails":
				auditTrail.setDescription("MDR details Added by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_ADD_MOBILEUSER);
				auditTrail.setUserType("MOBILEUSER");
				break;
				
			case "updateMobileUser":
				auditTrail.setDescription("MobileUser Updated by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_ADD_MOBILEUSER);
				auditTrail.setUserType("MOBILEUSER");
				break;
				//completed
			case "changePassword":
				auditTrail.setDescription("Admin Password Changed by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_CHANGE_PASSWORD);
				auditTrail.setUserType("ADMIN");
				break;
				//completed
			case "editAgent":
				auditTrail.setDescription("Agent Details Edited by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_EDIT_AGENT);
				auditTrail.setUserType("AGENT");
				break;
				//completed
			case "EzyAdsStatusUpdate":
				auditTrail.setDescription("EzyAds Status Updated by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_EDIT_EZYADS);
				auditTrail.setUserType("MERCHANT");
				break;
			
				//completed
			case "editMerchant":
				auditTrail.setDescription("Merchant Details Edited by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_EDIT_MERCHANT);
				auditTrail.setUserType("MERCHANT");
				break;
				//completed
			case "editMobileUser":
				auditTrail.setDescription("MobileUser Status Changed by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_EDIT_MOBILEUSER);
				auditTrail.setUserType("MOBILEUSER");
				break;	
				//completed
			case "resetPassword":
				auditTrail.setDescription("Merchant Password Reseted by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_RESETPWD_MERCHANT);
				auditTrail.setUserType("MERCHANT");
				break;
			case "login":
				auditTrail.setDescription("Logged in by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_LOGIN);
				auditTrail.setUserType("ADMIN");
				break;
			case "logout":
				auditTrail.setDescription("Logged out by Admin");
				auditTrail.setAction(AuditTrailAction.ADMIN_LOGOUT);
				auditTrail.setUserType("ADMIN");
				/*auditTrail.setCreatedDate(new Date());
				auditTrail.setCreatedBy(userName);*/
				
				
				
				break;
			
			default:
				auditTrail.setDescription(null);
				auditTrail.setAction(null);
				auditTrail.setUserType(null);
				break;
			}
			return userDAO.saveOrUpdateEntity(auditTrail);
		}
		
		public BankUser validateAdminUserName(String uname) {
			return userDAO.loadBankUser(uname);
		}
	
	/*public int changeUserPassWord(String Username,String newPwd,String OldPwd){
		
		//Merchant merchant=merchantDAO.loadMerchant(Username);
		BankUser agent = (BankUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		logger.info("agent :"+agent);
		//logger.info("agent username: :"+agent.getUsername()+": :password :"+agent.getPassword());
				
		boolean matches = encoder.matches(OldPwd,agent.getPassword());
		
			if(matches==true){
				//logger.info("agent new password :"+newPwd+": :encrypted password :"+encoder.encode(newPwd));
				int n=	userDAO.changeUserPassWord(Username, encoder.encode(newPwd), encoder.encode(OldPwd));
				return n;
					
		}else{
			return 0;
		}
		}*/
	
}