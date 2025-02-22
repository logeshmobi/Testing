package com.mobiversa.payment.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;

import org.apache.log4j.Logger;
/*import org.hibernate.annotations.common.util.impl.Log_.logger;*/
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.InternalTable;
import com.mobiversa.common.bo.KManager;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiLiteUser;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.MobileUserStatusHistory;
import com.mobiversa.common.bo.TID;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.bo.TerminalRenewal;
import com.mobiversa.common.bo.UMKManager;
import com.mobiversa.common.bo.UMMidTxnLimit;
import com.mobiversa.common.dto.MobileUserDTO;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.MDRDao;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dao.MobileUserDao;
import com.mobiversa.payment.dao.ReaderDao;
import com.mobiversa.payment.dao.UMMidTxnLimitDAO;
import com.mobiversa.payment.dto.MobileUserData;
import com.mobiversa.payment.dto.RegMobileUser;
import com.mobiversa.payment.util.DeviceRandomNumber;
import com.mobiversa.payment.util.RandomPassword;
import com.mobiversa.payment.util.SendSMSMessage;
import com.postmark.java.MsgDto;

@Service
public class MobileUserService{ //  extends BaseDAOImpl {
	 private static final Logger logger=Logger.getLogger(MobileUserService.class.getName());

	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private MobileUserDao mobileuserDAO;
	
	@Autowired
	private MDRDao  mdrDAO;
	
	@Autowired
	private MerchantDao merchantDAO;
	
	@Autowired
	private ReaderDao readerDao;
	
	@Autowired
	private UMMidTxnLimitDAO umMidTxnLimitDAO;
	
	

	public MobileUser loadMobileUserByPk(final Long id) {
		MobileUser mobileuser = mobileuserDAO.loadEntityByKey(MobileUser.class, id);
		if (mobileuser == null) {
			throw new RuntimeException("MobileUser Not found. ID:: " + id);
		}
		return mobileuser;
	}
	
	
	
	public MobileUser updateGrabPayTid(String gPayTid) {
		return mobileuserDAO.loadGrabPayTid(gPayTid);
	}

	
		public List<MobileUser> loadMobileUserByFk(final Long long1) {
			//logger.info("id: "+long1);
			//MobileUser mobileuser = mobileuserDAO.loadMobileUserBoostandMoto(id);
			List<MobileUser> mobileuser = mobileuserDAO.loadMobileUserByFK(long1);
			if (mobileuser == null) {
				throw new RuntimeException("MobileUser Not found. ID:: " + long1);
			}
			return mobileuser;
		}
		
		
		public MobiLiteUser loadMobiliteUserByFk(final Long long1) {
			//logger.info("id: "+long1);
			//MobileUser mobileuser = mobileuserDAO.loadMobileUserBoostandMoto(id);
			MobiLiteUser mobiliteuser = mobileuserDAO.loadMobiliteUserByFk(long1);
			if (mobiliteuser == null) {
				throw new RuntimeException("mobiliteuser Not found. ID:: " + long1);
			}
			return mobiliteuser;
		}
		
		public String loadMobileUserByFkBoost(final Long long1) {
			//logger.info("id: "+long1);
			//MobileUser mobileuser = mobileuserDAO.loadMobileUserBoostandMoto(id);
			/*List<MobileUser> mobileuser = mobileuserDAO.loadMobileUserByFK(long1);
			if (mobileuser == null) {
				throw new RuntimeException("MobileUser Not found. ID:: " + long1);
			}*/
			return mobileuserDAO.loadMobileUserByFKBoost(long1);
		}
		
		public String loadMobileUserByFkMoto(final Long long1) {
			//logger.info("id: "+long1);
			//MobileUser mobileuser = mobileuserDAO.loadMobileUserBoostandMoto(id);
			/*List<MobileUser> mobileuser = mobileuserDAO.loadMobileUserByFK(long1);
			if (mobileuser == null) {
				throw new RuntimeException("MobileUser Not found. ID:: " + long1);
			}*/
			return mobileuserDAO.loadMobileUserByFKMoto(long1);
		}
		
	public MobileUserStatusHistory loadMobileUserStatusByPk(final MobileUser mobileUser)

	{
		return mobileuserDAO.loadMobileStatusHistoryID(mobileUser);
	}
	

	public List<MobileUser> getMerchant(final Merchant merchant) {
		return mobileuserDAO.getMobileUser(merchant);
	}

	/*public AuditTrail updateAuditTrailMobileUser(Long id) {
		
		MobileUser mobileuser=loadMobileUserByPk(id);
		logger.info(" mobile user id and merchant name : "+mobileuser.getId()+" "+mobileuser.getMerchant().getUsername());
		
		AuditTrail auditTrail=new AuditTrail();
		auditTrail.setDescription("MobileUser Login Details Edited");
		auditTrail.setUserType("MOBILE_USER");
		auditTrail.setAction(AuditTrailAction.MERCHANT_EDIT_MOBILEUSER);
		auditTrail.setCreatedBy(null);
		auditTrail.setCreatedDate(null);
		auditTrail.setModifiedBy(mobileuser.getMerchant().getUsername());
		auditTrail.setModifiedDate(new Date());
		auditTrail.setStatus(CommonOperationStatus.SUCCESS);
		auditTrail.setUsername(mobileuser.getUsername());
		
		return mobileuserDAO.saveOrUpdateEntity(auditTrail);
	}
	*/
	
	
	/**
	 * JSP page for mobile User list requires MID, TID. <br>
	 * To achieve this objective, we will query for the MobileUser based on the
	 * paging information and then for each Mobile user retrieved from db, we
	 * will manually query for its associated TID, place them in a DTO object
	 * 
	 * @param paginationBean
	 * @return
	 */

	//@javax.transaction.Transactional
	public void listMobileUsers(final PaginationBean<MobileUser> paginationBean,final String date,
			final String date1) {
		
		//System.out.println("Inside  listAllTransaction:::::");
		
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		
		//Date dat = null;
		//Date dat1 = null;
		if(date!= null)
		{
			//criterionList.add(Restrictions.eq("activateDate", data));
		logger.info("check activateDate42323:" + date);
		String dat =date;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		
		try {
			dat = format.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat));
			Timestamp ts = Timestamp.valueOf(dat);
			logger.info("check from date1:" + ts);
			criterionList.add(Restrictions.ge("activateDate", ts));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		 if(date1!= null)
		{
			String dat1 =date1;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				dat1 = format.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat1));
				Timestamp ts = Timestamp.valueOf(dat1);
				logger.info("check to date2:" + ts);
				criterionList.add(Restrictions.lt("activateDate", ts));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			//criterionList.add(Restrictions.lt("activateDate", dat1));
			//logger.info("check activateDate to date121212:" + date1);
		}
		mobileuserDAO.listMobileUser(paginationBean, criterionList);
		//return listMobileUsers(pageNo, null);
	}
	
	
	public void listMobileUsersNew(final PaginationBean<MobileUserData> paginationBean,final String date,
			final String date1) {
		
		readerDao.listTerminalDetails(paginationBean, date, date1);
		/*if(date1.equals("ACTIVE")){
			MobileUser mu = new MobileUser();
			mu.setStatus(CommonStatus.ACTIVE);
		}
		String a = CommonStatus.ACTIVE.toString();
*/
		//return listMobileUsers(pageNo, null);
	}

	
	//@javax.transaction.Transactional
	public PaginationBean<MobileUserDTO> listMobileUsers1(final int pageNo, final Merchant merchant) {

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		// added by CK to filter mobileUser based on Merchant
		if (merchant != null) {
			criterionList.add(Restrictions.eq("merchant", merchant));
		}
		PaginationBean<MobileUser> paginationMobileUser = new PaginationBean<>();
		mobileuserDAO.listMobileUser(paginationMobileUser, criterionList);

		// construct DTO paginationBean
		PaginationBean<MobileUserDTO> paginationDTO = new PaginationBean<>();
		paginationDTO.setCurrPage(pageNo);
		paginationDTO.setItemsPerPage(paginationMobileUser.getItemsPerPage());

		ArrayList<MobileUserDTO> mobileUserDTOList = new ArrayList<>();
		for (MobileUser mobileUser : paginationMobileUser.getItemList()) {
			final TID tid = mobileuserDAO.getTID(mobileUser);
			mobileUserDTOList.add(new MobileUserDTO(mobileUser, tid));
		}
		paginationDTO.setItemList(mobileUserDTOList);
		return paginationDTO;

	}

	public void searchMobileUser(final String username, final PaginationBean<MobileUserDTO> paginationBean) {
		mobileuserDAO.findByUserNames(username, paginationBean);
	}

	public void doSuspendMobileUser(final Long id, final String reason, final String suspendDescription) {
		MobileUser mobileUser = loadMobileUserByPk(id);
		if (!CommonStatus.ACTIVE.equals(mobileUser.getStatus())) {
			// merchant status isn't active, then how do we suspend merchant?
			throw new RuntimeException("unable to suspend a non-active merchant");
		}

		CommonStatus status = CommonStatus.SUSPENDED;

		MobileUserStatusHistory history = new MobileUserStatusHistory();
		history.setMobileUser(mobileUser);
		history.setCreatedDate(new Date());
		history.setReason(reason);
		history.setDescription(suspendDescription);
		history.setStatus(status);
		history.setUserId(mobileUser.getUsername());
		mobileuserDAO.updateMobileUserStatus(id, status, history);
	}

	public void doUnSuspendMobileUser(final Long id, final String reason, final String unSuspendDescription) {
		MobileUser mobileUser = loadMobileUserByPk(id);
		if (!CommonStatus.SUSPENDED.equals(mobileUser.getStatus())) {
			// mobileuser status isn't suspend, then how do we active
			// mobileuser?
			throw new RuntimeException("unable to unsuspend a active mobileuser");
		}

		CommonStatus status = CommonStatus.ACTIVE;

		MobileUserStatusHistory history = new MobileUserStatusHistory();

		history.setMobileUser(mobileUser);
		history.setCreatedDate(new Date());
		history.setReason(reason);
		history.setDescription(unSuspendDescription);
		history.setStatus(status);
		history.setUserId(mobileUser.getUsername());
		mobileuserDAO.updateMobileUserStatus(id, status, history);
	}

	/* mobile user updated methods 31/05/2016 */
	/*@SuppressWarnings("unused")
	// @javax.transaction.Transactional
	public MobileUser addMobileUser(final RegMobileUser entity) {

		RegMobileUser merchantData = entity;
		String merchantDetails = null;
		if (!(merchantData.equals(""))) {
			String merchantmail[] = merchantData.getMerchantName().split("~");

			merchantDetails = merchantmail[1];
		}
		
		int count = mobileuserDAO.loadTerminalDetailsByMid(entity.getMid(), entity.getConnectType());
		
		
		
				
		//MobileUser mobileuser1 = new MobileUser();
		
		
		
		Merchant merchant = merchantDAO.loadMerchantbyEmail(merchantDetails);
		
		
		
		if(count > 0 && entity.getConnectType().equals("WIFI")){
			logger.info("Already Exist MID:" + entity.getMid());
			// load mobileuser based on mid/ tid / device to return
			List<TerminalDetails> terminalDetailsList = mobileuserDAO.loadTerminalDetailsByMidAndType(entity.getMid(),entity.getConnectType());
			
			logger.info("check terminal Details:" + entity.getConnectType());
			
			
			for(TerminalDetails td :terminalDetailsList){
				MobileUser mobileUserByTid = mobileuserDAO.loadMobileUserbyTidAndType(td.getTid(),"WIFI");	
				//logger.info("check mobile user tid:" + mobileUserByTid.getTid());
				//logger.info("check mobile user tid:" + mobileUserByTid.getConnectType());
				if(mobileUserByTid != null) 
					{
						mobileuser1 = mobileUserByTid;
					}
			}
		}//else{
			//MobileUser mobileuser = new MobileUser();
			
			if (!(merchantData.equals(""))) {
				String merchantmail[] = merchantData.getMerchantName().split("~");

				merchantDetails = merchantmail[1];
			}
			
			mobileuser.setActivateDate(new Date());
			mobileuser.setContact(entity.getContactNo());
			mobileuser.setEmail(entity.getEmailId());
			mobileuser.setFirstName(entity.getFirstName());
			mobileuser.setSalutation(entity.getSalutation());
			mobileuser.setConnectType(entity.getConnectType());

			logger.info("set entity device id:" + entity.getTid());
			mobileuser.setTid(entity.getTid());

			mobileuser.setPreAuth(entity.getPreAuth());

			mobileuser.setStatus(CommonStatus.ACTIVE);

			MobileUserRandomPassword rm = new MobileUserRandomPassword();
			String genPwd = rm.generateRandomString();

			mobileuser.setPassword(encoder.encode(genPwd));

			String contactName = entity.getFirstName();

			int fname_len = contactName.length();

			String conName[] = null;

			if (contactName != null) {
				if (fname_len > 3) {
					if (contactName.contains(" ")) {
						// contactName = contactName.substring(0, 4);
						conName = contactName.split(" ");
						contactName = conName[0];
					}
				} else {
					for (int t = fname_len; t < 4; t++) {
						contactName = contactName + "0";
					}

				}
			}

			String deviceid = entity.getDeviceId().substring(entity.getDeviceId().length() - 4,
					entity.getDeviceId().length());

			String userdata = contactName + deviceid;

			mobileuser.setUsername(userdata);
			mobileuser.setMerchant(merchant);
			mobileuser1 = mobileuserDAO.saveOrUpdateEntity(mobileuser);
			
			
			 TempletFields tempField = new TempletFields();
				
				//tempField.setFirstName(merchant1.getFirstName());
				//tempField.setLastName(merchant1.getLastName());
				if(entity.getSalutation() != null){
					tempField.setSalutation(entity.getSalutation());
				}else{
					tempField.setSalutation("Ms");
					
				}
				tempField.setFirstName(entity.getFirstName());
				//tempField.setUserName(entity.getUsername());
				//tempField.setUserName(userdata);
				//tempField.setPassword(genPwd);
				if(entity.getDeviceId().length()==14)
				{
					logger.info("device type:" + tempField.getDeviceId());
				tempField.setDeviceId(entity.getDeviceType()+entity.getDeviceId());
				logger.info("device type:" + tempField.getDeviceId());
				}
				else if(entity.getDeviceId().length()==12)
				{
					tempField.setDeviceId(entity.getDeviceType()+entity.getDeviceId());
				}
				else
				{
					tempField.setDeviceId(entity.getDeviceId());
				}
				tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
				
				
				List<NameValuePair> headers = new ArrayList<NameValuePair>();
				headers.add(new NameValuePair("HEADER", "test"));
			    //EZYWIRE AS USERNAME & password mobiversa
				String fromAddress = "info@gomobi.io";
				//String apiKey = "c652b570-9500-4534-8eb6-96a78c10c8b8";
				String apiKey = PropertyLoader.getFile().getProperty("APIKEY");
				String toAddress = entity.getEmailId();
				
				logger.info(" To Address:" + toAddress);
				
				// toAddress = "nandhakumar@mobiversa.com";
				 
				// logger.info(" To Address:" + toAddress);
				
				String ccMail="ethan@mobiversa.com";
				String bccMail = "premkumar@mobiversa.com,yusri@mobiversa.com";
				
				//String ccMail="bhuvaneswari@mobiversa.com";
				//String bccMail = "nandhakumar@mobiversa.com,mani@mobiversa.com";
				//String subject = "Account Creation Mail";// set
				String subject = PropertyLoader.getFile().getProperty("MOBILEACT_SUBJECT"); 
				
				
				String emailBody = EmailTemplet13.sentTempletContent(tempField);

				
				Attachment logo = new Attachment("mobiversa_logo1.jpg", "image/jpg",
						PropertyLoader.getFile().getProperty("NEW_LOGO"),"cid:mobiversa_logo1");

				Attachment faceBook = new Attachment("mobi_facebook.jpg", "image/jpg",
						PropertyLoader.getFile().getProperty("FACEBOOK"),
						"cid:mobi_facebook");

				Attachment twitter = new Attachment("mobi_twitter.jpg", "image/jpg",
						PropertyLoader.getFile().getProperty("TWITTER"),
						"cid:mobi_twitter");

				Attachment link = new Attachment("mobiversa_link.jpg", "image/jpg",
						PropertyLoader.getFile().getProperty("LINK"),
						"cid:mobiversa_link");

				Attachment linkedIn = new Attachment("mobi_linkedin.jpg", "image/jpg",
						PropertyLoader.getFile().getProperty("LINKEDIN"),
						"cid:mobi_linkedin");

				List<Attachment> attachments = new ArrayList<Attachment>();
				attachments.add(logo);
				attachments.add(faceBook);
				attachments.add(twitter);
				attachments.add(link);
				attachments.add(linkedIn);
				//ccMail, bccMail,
				PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress,
						fromAddress, ccMail, bccMail, subject, emailBody, true, "test-email",
						null, attachments);
				PostmarkClient client = new PostmarkClient(apiKey);

				try {
					client.sendMessage(message);
					logger.info("Email Sent Successfully to" + entity.getEmailId());
				} catch (PostmarkException pe) {
					System.out.println("Invalid Signature Base64 String");

				}
				
				///End Email Content data
			
			
		//}


		

		String merData = null;
		String tid = entity.getDeviceId();

		String mid = mobileuserDAO.loadMidData(merchant.getId().toString());
	
		TerminalDetails mm = new TerminalDetails();

		//mobileuser.setMerchant(merchant);
		if(entity.getDeviceId().length() == 14)
		{
			logger.info("device type:" + entity.getDeviceType());
			mm.setDeviceId(entity.getDeviceType()+entity.getDeviceId());
			logger.info("check old deviceId:" + mm.getDeviceId());
		}else if(entity.getDeviceId().length() == 12)
		{
			logger.info("device type new device:" + entity.getDeviceType());
			mm.setDeviceId(entity.getDeviceType() + entity.getDeviceId());
			logger.info("check new deviceId:" + mm.getDeviceId());
		}else{
			mm.setDeviceId(entity.getDeviceId());
			logger.info("check wrong deviceId:" + mm.getDeviceId());
		}

		mm.setDeviceId("WP" + entity.getDeviceId());
		mm.setMerchantId(mid);
		mm.setActivatedDate(new Date());
		mm.setActiveStatus("ACTIVE");
		mm.setTid(entity.getTid());
		mm.setRefNo(entity.getReferenceNo());
		mm.setContactName(entity.getContactName());
		logger.info("connect type in terminal details table:" + entity.getConnectType());
		mm.setConnectType(entity.getConnectType());
		logger.info("connect type in terminal details table:" + mm.getConnectType());
		
		
		mm.setRenewalDate(new Date());
		logger.info("Renewal Date:" + mm.getRenewalDate());
		Date date1 = null;
		 try {
			 date1=new SimpleDateFormat("dd-MMM-yyyy").parse(entity.getExpiryDate());
			 logger.info("Expiry Date:" + date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		mm.setSuspendedDate(date1);
		logger.info("Expiry Date:" + mm.getSuspendedDate());
		mm.setRemarks(entity.getRemarks());
		logger.info("Remarks:" + mm.getRemarks());
		
		InternalTable it = new InternalTable();
		it.setBatchId(1l);
		it.setStan(1l);
		it.setInvoiceNo(1l);
		it.setTid(entity.getTid());
		it.setMid(mid);
		//mobileuser1 = mobileuserDAO.saveOrUpdateEntity(mobileuser);
		mobileuserDAO.saveOrUpdateEntity(mm);
		mobileuserDAO.saveOrUpdateEntity(it);

		int update = mobileuserDAO.updateKManager(entity.getReferenceNo(), entity.getTid());

		
		return mobileuser1;

	}*/
	
	//new changes for adding mobileuser details
	
	public RegMobileUser addGrabPayUser(final RegMobileUser regMobileUser) {
	
		Merchant merchant=merchantDAO.loadMerchantbymerchantid(Long.valueOf(regMobileUser.getMerchantID()));
	    MID mid=merchant.getMid();
	    mid.setGpayMid(regMobileUser.getgPayMid());
		
		MobileUser mob=loadMobileUserbyMerchantFK(regMobileUser.getMerchantID());
		mob.setGpayTid(regMobileUser.getgPayTid());
		regMobileUser.setTid(mob.getTid());
		regMobileUser.setMotoTid(mob.getMotoTid());
		regMobileUser.setEzypassTid(mob.getEzypassTid());
		regMobileUser.setEzyrecTid(mob.getEzyrecTid());
		
		TerminalDetails td=mobileuserDAO.loadTerminalDetailsByAnyTids(regMobileUser);
		regMobileUser.setActivationCode(td.getActivationCode());
		TerminalDetails terminalDetails = new TerminalDetails();
		terminalDetails.setDeviceId(regMobileUser.getgPaydeviceId());
		terminalDetails.setDeviceType("GPAY");
		terminalDetails.setMerchantId(regMobileUser.getgPayMid());
		terminalDetails.setActivatedDate(new Date());
		terminalDetails.setActiveStatus("ACTIVE");
		terminalDetails.setTid(regMobileUser.getgPayTid());
		//terminalDetails.setRefNo(regMobileUser.getgPayrefNo());
		terminalDetails.setContactName(regMobileUser.getContactName());
		terminalDetails.setPreAuth(regMobileUser.getPreAuth());
		terminalDetails.setConnectType("BT");
		terminalDetails.setRenewalDate(new Date());

		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		terminalDetails.setSuspendedDate(date1);
		terminalDetails.setRemarks(regMobileUser.getRemarks());
		terminalDetails.setActivationCode(td.getActivationCode());
		terminalDetails = mobileuserDAO.saveOrUpdateEntity(terminalDetails);
		if (terminalDetails != null) {
			logger.info("GPAY device registered successfully in terminaldetails..");
		}
		/*InternalTable internalTab = new InternalTable();
		internalTab.setBatchId(1l);
		internalTab.setStan(1l);
		internalTab.setInvoiceNo(1l);
		internalTab.setTid(regMobileUser.getgPayTid());
		internalTab.setMid(regMobileUser.getgPayMid());
		internalTab = mobileuserDAO.saveOrUpdateEntity(internalTab);
		if (internalTab != null) {
			logger.info("GPAY device registered successfully in internalTable");
		}

		int update = mobileuserDAO.updateKManager(regMobileUser.getgPayrefNo(), regMobileUser.getgPayTid());

		if (update != 0) {
			logger.info("GPAY device registered successfully in Kmanager");
		}*/
		
		
		merchantDAO.saveOrUpdateEntity(mid);
		mobileuserDAO.saveOrUpdateEntity(mob);
		
		return regMobileUser;
		
		
		
		
	}
	
	public RegMobileUser updateGrabPayUser(final RegMobileUser regMobileUser) {
		
		MobileUser mob=loadMobileUserbyMerchantFK(regMobileUser.getMerchantID());
		mob.setGpayTid(regMobileUser.getgPayTid());

		
		TerminalDetails td=mobileuserDAO.loadTerminalDetailsByAnyTids(regMobileUser);
		regMobileUser.setActivationCode(td.getActivationCode());
		TerminalDetails terminalDetails = new TerminalDetails();
		terminalDetails.setDeviceId(regMobileUser.getgPaydeviceId());
		terminalDetails.setDeviceType("GPAY");
		terminalDetails.setMerchantId(regMobileUser.getgPayMid());
		terminalDetails.setActivatedDate(new Date());
		terminalDetails.setActiveStatus("ACTIVE");
		terminalDetails.setTid(regMobileUser.getgPayTid());
		//terminalDetails.setRefNo(regMobileUser.getgPayrefNo());
		terminalDetails.setContactName(regMobileUser.getContactName());
		terminalDetails.setPreAuth(regMobileUser.getPreAuth());
		terminalDetails.setConnectType("BT");
		terminalDetails.setRenewalDate(new Date());
		terminalDetails.setTid(regMobileUser.getgPayTid());
		

		Date date1 = null;
		try {
			date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

		} catch (ParseException e) {
			e.printStackTrace();
		}
		terminalDetails.setSuspendedDate(date1);
		terminalDetails.setRemarks(regMobileUser.getRemarks());
		terminalDetails.setActivationCode(td.getActivationCode());
		terminalDetails = mobileuserDAO.saveOrUpdateEntity(terminalDetails);
		if (terminalDetails != null) {
			logger.info("GPAY device registered successfully in terminaldetails..");
		}
		
		mobileuserDAO.saveOrUpdateEntity(mob);
		MobileUser mobileuser=loadMobileUserByPk(Long.valueOf(regMobileUser.getMobileId()));
		logger.info("tid: "+regMobileUser.getTid());
		logger.info("mototid: "+regMobileUser.getMotoTid());
		logger.info("ezyrectid: "+regMobileUser.getEzyrecTid());
		logger.info("ezypasstid: "+regMobileUser.getEzypassTid());
		mobileuser.setGpayTid(regMobileUser.getgPayTid());
		
		mobileuserDAO.saveOrUpdateEntity(mobileuser);
		return regMobileUser;
		
	
	}
	
	
	@SuppressWarnings("static-method")
	public RegMobileUser setMobileUserData(final RegMobileUser regMobileUser) {
		
		//Paydee
		if (regMobileUser.getEzyrecTid() == null || regMobileUser.getEzyrecTid().isEmpty()) {
			// logger.info("empty ezyrec");
			regMobileUser.setEzyrecTid(null);
		}
		if (regMobileUser.getEzywayTid() == null || regMobileUser.getEzywayTid().isEmpty()) {
			// logger.info("empty ezyway");
			regMobileUser.setEzywayTid(null);
		}
		if (regMobileUser.getEzypassTid() == null || regMobileUser.getEzypassTid().isEmpty()) {
			// logger.info("empty getEzypassTid");
			regMobileUser.setEzypassTid(null);
		}
		if (regMobileUser.getTid() == null || regMobileUser.getTid().isEmpty()) {
			// logger.info("empty getTid");
			regMobileUser.setTid(null);
		}
		if (regMobileUser.getMotoTid() == null || regMobileUser.getMotoTid().isEmpty()) {
			// logger.info("empty getMotoTid");
			regMobileUser.setMotoTid(null);
		}

		// check Empty DeviceID details
		if (regMobileUser.getEzyrecdeviceId() == null || regMobileUser.getEzyrecdeviceId().isEmpty()) {
			// logger.info("empty ezyrec");
			regMobileUser.setEzyrecdeviceId(null);
		}
		if (regMobileUser.getEzywaydeviceId() == null || regMobileUser.getEzywaydeviceId().isEmpty()) {
			// logger.info("empty ezyway");
			regMobileUser.setEzywaydeviceId(null);
		}
		if (regMobileUser.getEzypassdeviceId() == null || regMobileUser.getEzypassdeviceId().isEmpty()) {
			// logger.info("empty getEzypassTid");
			regMobileUser.setEzypassdeviceId(null);
		}
		if (regMobileUser.getDeviceId() == null || regMobileUser.getDeviceId().isEmpty()) {
			// logger.info("empty getTid");
			regMobileUser.setDeviceId(null);
		}
		if (regMobileUser.getMotodeviceId() == null || regMobileUser.getMotodeviceId().isEmpty()) {
			// logger.info("empty getMotoTid");
			regMobileUser.setMotodeviceId(null);
		}
		
		// check Empty MID details
				if (regMobileUser.getMid() == null || regMobileUser.getMid().isEmpty()) {
					// logger.info("empty ezyrec");
					regMobileUser.setMid(null);
				}
				if (regMobileUser.getEzywayMid() == null || regMobileUser.getEzywayMid().isEmpty()) {
					// logger.info("empty ezyway");
					regMobileUser.setEzywayMid(null);
				}
				if (regMobileUser.getEzypassMid() == null || regMobileUser.getEzypassMid().isEmpty()) {
					// logger.info("empty getEzypassTid");
					regMobileUser.setEzypassMid(null);
				}
				if (regMobileUser.getEzyrecMid() == null || regMobileUser.getEzyrecMid().isEmpty()) {
					// logger.info("empty getTid");
					regMobileUser.setEzyrecMid(null);
				}
				if (regMobileUser.getMotoMid() == null || regMobileUser.getMotoMid().isEmpty()) {
					// logger.info("empty getMotoTid");
					regMobileUser.setMotoMid(null);
				}
		
		
		//UMobile
		if (regMobileUser.getUm_ezyrecTid() == null || regMobileUser.getUm_ezyrecTid().isEmpty()) {
			// logger.info("empty ezyrec");
			regMobileUser.setUm_ezyrecTid(null);
		}
		if (regMobileUser.getUm_ezywayTid() == null || regMobileUser.getUm_ezywayTid().isEmpty()) {
			// logger.info("empty ezyway");
			regMobileUser.setUm_ezywayTid(null);
		}
		if (regMobileUser.getUm_ezypassTid() == null || regMobileUser.getUm_ezypassTid().isEmpty()) {
			// logger.info("empty getEzypassTid");
			regMobileUser.setUm_ezypassTid(null);
		}
		if (regMobileUser.getUm_tid() == null || regMobileUser.getUm_tid().isEmpty()) {
			// logger.info("empty getTid");
			regMobileUser.setUm_tid(null);
		}
		if (regMobileUser.getUm_motoTid() == null || regMobileUser.getUm_motoTid().isEmpty()) {
			regMobileUser.setUm_motoTid(null);
		}

		// check Empty DeviceID details
		if (regMobileUser.getUm_ezyrecdeviceId() == null || regMobileUser.getUm_ezyrecdeviceId().isEmpty()) {
			// logger.info("empty ezyrec");
			regMobileUser.setUm_ezyrecdeviceId(null);
		}
		if (regMobileUser.getUm_ezywaydeviceId() == null || regMobileUser.getUm_ezywaydeviceId().isEmpty()) {
			// logger.info("empty ezyway");
			regMobileUser.setUm_ezywaydeviceId(null);
		}
		if (regMobileUser.getUm_ezypassdeviceId() == null || regMobileUser.getUm_ezypassdeviceId().isEmpty()) {
			// logger.info("empty getEzypassTid");
			regMobileUser.setUm_ezypassdeviceId(null);
		}
		if (regMobileUser.getUm_deviceId() == null || regMobileUser.getUm_deviceId().isEmpty()) {
			// logger.info("empty getTid");
			regMobileUser.setUm_deviceId(null);
		}
		if (regMobileUser.getUm_motodeviceId() == null || regMobileUser.getUm_motodeviceId().isEmpty()) {
			// logger.info("empty getMotoTid");
			regMobileUser.setUm_motodeviceId(null);
		}
		
		
		// check Empty MID details
		if (regMobileUser.getUm_mid() == null || regMobileUser.getUm_mid().isEmpty()) {
			// logger.info("empty ezyrec");
			regMobileUser.setUm_mid(null);
		}
		if (regMobileUser.getUm_ezywayMid() == null || regMobileUser.getUm_ezywayMid().isEmpty()) {
			// logger.info("empty ezyway");
			regMobileUser.setUm_ezywayMid(null);
		}
		if (regMobileUser.getUm_ezypassMid() == null || regMobileUser.getUm_ezypassMid().isEmpty()) {
			// logger.info("empty getEzypassTid");
			regMobileUser.setUm_ezypassMid(null);
		}
		if (regMobileUser.getUm_ezyrecMid() == null || regMobileUser.getUm_ezyrecMid().isEmpty()) {
			// logger.info("empty getTid");
			regMobileUser.setUm_ezyrecMid(null);
		}
		if (regMobileUser.getUm_motoMid() == null || regMobileUser.getUm_motoMid().isEmpty()) {
			// logger.info("empty getMotoTid");
			regMobileUser.setUm_motoMid(null);
		}

		
		logger.info("Merchant Type: "+regMobileUser.getMerchantType());
		
		if(regMobileUser.getMerchantType()!=null) {
			logger.info("Existing Merchant Type: "+regMobileUser.getMerchantType());
		}else {
			regMobileUser.setMerchantType("P");
		}
		
		
		if(regMobileUser.getMerchantType()!=null) {
			//---UMOBILE----
			if(regMobileUser.getMerchantType().equalsIgnoreCase("U")) {
				if (regMobileUser.getUm_tid() != null) {
					if (!regMobileUser.getUm_tid().matches("[0-9]+")
							|| !(regMobileUser.getUm_tid().length() >= 8 && regMobileUser.getUm_tid().length() < 9)) {
						// resDataum_tid = "Invalid TID";
						logger.info("RESPONSE: INVALID UMTID");
						regMobileUser.setResUTid(false);
						regMobileUser.setRespUTid("INVALID UMTID");

					}else {
						
						if (loadTerminalDetailsByTid(regMobileUser.getUm_tid()) != null) {
							logger.info("RESPONSE: UMTID already exist");
							regMobileUser.setResUTid(false);
							regMobileUser.setRespUTid("UMTID already exist");
						}
					}

					if (!regMobileUser.getUm_deviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getUm_deviceId().length() >= 15
									&& regMobileUser.getUm_deviceId().length() < 17)) {
						// resDataum_deviceid = "Invalid DeviceID";
						logger.info("RESPONSE: " + "INVALID UMDeviceID");
						regMobileUser.setResUDeviceID(false);
						regMobileUser.setRespUDeviceID("INVALID UMDeviceID");
					}else {
						
						if (loadDeviceId(regMobileUser.getUm_deviceId()) != null) {
							// resDataum_deviceid = "DeviceID already exist";
							logger.info("RESPONSE: " + "UMDeviceID already exist");
							regMobileUser.setResUDeviceID(false);
							regMobileUser.setRespUDeviceID("UMDeviceID already exist");
						}

						// throw new MobiException(Status.INVALID_EZYRECTID);

					}

				}else {
					
					regMobileUser.setResUTid(true);
					regMobileUser.setResUDeviceID(true);
				}
				
				

				if (regMobileUser.getUm_motoTid()!= null && regMobileUser.getUm_motodeviceId()!=null) {
					if (!regMobileUser.getUm_motoTid().matches("[0-9]+") || 
							!(regMobileUser.getUm_motoTid().length() >= 8 && regMobileUser.getUm_motoTid().length() < 9)) {
						//resDataum_mototid = "Invalid TID";
						logger.info("RESPONSE: " + "INVALID UMMOTOTID");
						regMobileUser.setResUMotoTid(false);
						regMobileUser.setRespUMotoTid("INVALID UMMOTOTID");
						
					}else {
						if (loadTerminalDetailsByTid(regMobileUser.getUm_motoTid()) != null) {
							//resDataum_mototid = "Tid already exist";
							logger.info("RESPONSE: " + "EXIST UMMOTOTID");
							regMobileUser.setResUMotoTid(false);
							regMobileUser.setRespUMotoTid("EXIST UMMOTOTID");
						}
					
						// throw new MobiException(Status.INVALID_MOTOTID);
					}

					if (!regMobileUser.getUm_motodeviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getUm_motodeviceId().length() >= 15
									&& regMobileUser.getUm_motodeviceId().length() < 17)) {
						//resDataum_motodeviceid = "Invalid DeviceID";
						logger.info("RESPONSE: " + "INVALID UMDEVICEID");
						regMobileUser.setResUMotoDeviceID(false);
						regMobileUser.setRespUMotoDeviceID("INVALID UMDEVICEID");
						
					}else {
						if (loadDeviceId(regMobileUser.getUm_motodeviceId()) != null) {
							// logger.info("moto check device id: "+checkDeviceID2.getDeviceId());
							logger.info("RESPONSE: " + "EXIST UMDEVICEID");
							regMobileUser.setResUMotoDeviceID(false);
							regMobileUser.setRespUMotoDeviceID("EXIST UMDEVICEID");
						}
						
						// throw new MobiException(Status.INVALID_EZYRECTID);

					}
				
				}else {
					regMobileUser.setResUMotoTid(true);
					regMobileUser.setResUMotoDeviceID(true);
				}
				
				logger.info(regMobileUser.isResUMotoTid()+" "+regMobileUser.isResUMotoDeviceID());
				
				if (regMobileUser.getUm_ezypassTid() != null) {
					if (!regMobileUser.getUm_ezypassTid().matches("[0-9]+") || 
							!(regMobileUser.getUm_ezypassTid().length() >= 8 && regMobileUser.getUm_ezypassTid().length() < 9)) {
						logger.info("RESPONSE: " + "INVALID UMEZYPASSTID");
						regMobileUser.setResUEzypassTid(false);
						regMobileUser.setRespUEzypassDeviceID("INVALID UMEZYPASSTID");
					}else {
						if (loadTerminalDetailsByTid(regMobileUser.getUm_ezypassTid()) != null) {
							logger.info("RESPONSE: " + "EXIST UMEZYPASSTID");
							regMobileUser.setResUEzypassTid(false);
							regMobileUser.setRespUEzypassDeviceID("EXIST UMEZYPASSTID");
						}
					}

					if (!regMobileUser.getUm_ezypassdeviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getUm_ezypassdeviceId().length() >= 15
									&& regMobileUser.getUm_ezypassdeviceId().length() < 17)) {
						//resDataum_ezypassdeviceid = "Invalid DeviceID";
						logger.info("RESPONSE: " + "INVALID UMEZYPASSDEVICEID");
						regMobileUser.setResUEzypassDeviceID(false);
						regMobileUser.setRespUEzypassDeviceID("INVALID UMEZYPASSDEVICEID");
					}else {
						if (loadDeviceId(regMobileUser.getUm_ezypassdeviceId()) != null) {
							logger.info("RESPONSE: " + "EXIST UMEZYPASSDEVICEID");
							regMobileUser.setResUEzypassDeviceID(false);
							regMobileUser.setRespUEzypassDeviceID("EXIST UMEZYPASSDEVICEID");
						}
					}
				
				}else {
					regMobileUser.setResUEzypassTid(true);
					regMobileUser.setResUEzypassDeviceID(true);
				}
				if (regMobileUser.getUm_ezywayTid() != null) {
					if (!regMobileUser.getUm_ezywayTid().matches("[0-9]+") || 
							!(regMobileUser.getUm_ezywayTid().length() >= 8 && regMobileUser.getUm_ezywayTid().length() < 9)) {
						//resDataum_ezywaytid = "Invalid TID";
						logger.info("RESPONSE: " + "INVALID UMEZYWAYTID");
						regMobileUser.setResEzywayTid(false);
						regMobileUser.setRespEzywayTid("INVALID UMEZYWAYTID");
					}else {
						if (loadTerminalDetailsByTid(regMobileUser.getUm_ezywayTid()) != null) {
							logger.info("RESPONSE: " + "EXIST UMEZYPASSTID");
							regMobileUser.setResEzywayTid(false);
							regMobileUser.setRespEzywayTid("EXIST UMEZYWAYTID");
						}
						
						// throw new MobiException(Status.INVALID_EZYWAYTID);

					}

					if (!regMobileUser.getUm_ezywaydeviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getUm_ezywaydeviceId().length() >= 15
									&& regMobileUser.getUm_ezywaydeviceId().length() < 17)) {
						//resDataum_ezywaydeviceid = "Invalid DeviceID";
						logger.info("RESPONSE: " + "INVALID UMEZYWAYDEVICEID");
						regMobileUser.setResUEzywayDeviceID(false);
						regMobileUser.setRespUEzywayDeviceID("INVALID UMEZYWAYDEVICEID");
					}else {
						if (loadDeviceId(regMobileUser.getUm_ezywaydeviceId()) != null) {
							logger.info("RESPONSE: " + "EXIST UMEZYWAYDEVICEID");
							regMobileUser.setResUEzywayDeviceID(false);
							regMobileUser.setRespUEzywayDeviceID("EXIST UMEZYWAYDEVICEID");
						}
						
						// throw new MobiException(Status.INVALID_EZYRECTID);

					}
				
				}else {
					regMobileUser.setResEzywayTid(true);
					regMobileUser.setResUEzywayDeviceID(true);
				}
				
				
				if (regMobileUser.getUm_ezyrecTid() != null) {
					if (!regMobileUser.getUm_ezyrecTid().matches("[0-9]+") || 
							!(regMobileUser.getUm_ezyrecTid().length() >= 8 && regMobileUser.getUm_ezyrecTid().length() <9)) {
						//resDataum_ezyrectid = "Invalid TID";
						logger.info("RESPONSE: " + "INVALID UMEZYRECTID");
						regMobileUser.setResUEzyrecTid(false);
						regMobileUser.setRespUEzyrecTid("INVALID UMEZYRECTID");
					}else {
						if (loadTerminalDetailsByTid(regMobileUser.getUm_ezyrecTid()) != null) {
							logger.info("RESPONSE: " + "EXIST UMEZYRECTID");
							regMobileUser.setResUEzyrecTid(false);
							regMobileUser.setRespUEzyrecTid("EXIST UMEZYRECTID");
						}
					
					}

					if (!regMobileUser.getUm_ezyrecdeviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getUm_ezyrecdeviceId().length() >= 15
									&& regMobileUser.getUm_ezyrecdeviceId().length() < 17)) {
						//resDataum_ezyrecdeviceid = "Invalid DeviceID";
						logger.info("RESPONSE: " + "INVALID UMEZYRECDEVICEID");
						regMobileUser.setResUEzyrecDeviceID(false);
						regMobileUser.setRespUEzyrecDeviceID("INVALID UMEZYRECDEVICEID");
					}else {
						if (loadDeviceId(regMobileUser.getUm_ezyrecdeviceId()) != null) {
							logger.info("RESPONSE: " + "EXIST UMEZYRECDEVICEID");
							regMobileUser.setResUEzyrecDeviceID(false);
							regMobileUser.setRespUEzyrecDeviceID("EXIST UMEZYRECDEVICEID");
						}
						
					}

				
				}else {
					regMobileUser.setResUEzyrecTid(true);
					regMobileUser.setResUEzyrecDeviceID(true);
				}if (regMobileUser.getUm_tid() != null) {
					if (!regMobileUser.getUm_tid().matches("[0-9]+")
							|| !(regMobileUser.getUm_tid().length() >= 8 && regMobileUser.getUm_tid().length() < 9)) {
						// resDataum_tid = "Invalid TID";
						logger.info("RESPONSE: INVALID UMTID");
						regMobileUser.setResUTid(false);
						regMobileUser.setRespUTid("INVALID UMTID");

					}else {
						
						
						if (loadTerminalDetailsByTid(regMobileUser.getUm_tid()) != null) {
							logger.info("RESPONSE: UMTID already exist");
							regMobileUser.setResUTid(false);
							regMobileUser.setRespUTid("UMTID already exist");
						}
					}

					if (!regMobileUser.getUm_deviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getUm_deviceId().length() >= 15
									&& regMobileUser.getUm_deviceId().length() < 17)) {
						// resDataum_deviceid = "Invalid DeviceID";
						logger.info("RESPONSE: " + "INVALID UMDeviceID");
						regMobileUser.setResUDeviceID(false);
						regMobileUser.setRespUDeviceID("INVALID UMDeviceID");
					}else {
						
						if (loadDeviceId(regMobileUser.getUm_deviceId()) != null) {
							// resDataum_deviceid = "DeviceID already exist";
							logger.info("RESPONSE: " + "UMDeviceID already exist");
							regMobileUser.setResUDeviceID(false);
							regMobileUser.setRespUDeviceID("UMDeviceID already exist");
						}

						// throw new MobiException(Status.INVALID_EZYRECTID);

					}

				}else {
					
					regMobileUser.setResUTid(true);
					regMobileUser.setResUDeviceID(true);
				}
				
				

				if (regMobileUser.getUm_motoTid()!= null && regMobileUser.getUm_motodeviceId()!=null) {
					if (!regMobileUser.getUm_motoTid().matches("[0-9]+") || 
							!(regMobileUser.getUm_motoTid().length() >= 8 && regMobileUser.getUm_motoTid().length() < 9)) {
						//resDataum_mototid = "Invalid TID";
						logger.info("RESPONSE: " + "INVALID UMMOTOTID");
						regMobileUser.setResUMotoTid(false);
						regMobileUser.setRespUMotoTid("INVALID UMMOTOTID");
						
					}else {
						if (loadTerminalDetailsByTid(regMobileUser.getUm_motoTid()) != null) {
							//resDataum_mototid = "Tid already exist";
							logger.info("RESPONSE: " + "EXIST UMMOTOTID");
							regMobileUser.setResUMotoTid(false);
							regMobileUser.setRespUMotoTid("EXIST UMMOTOTID");
						}
					
						// throw new MobiException(Status.INVALID_MOTOTID);
					}

					if (!regMobileUser.getUm_motodeviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getUm_motodeviceId().length() >= 15
									&& regMobileUser.getUm_motodeviceId().length() < 17)) {
						//resDataum_motodeviceid = "Invalid DeviceID";
						logger.info("RESPONSE: " + "INVALID UMDEVICEID");
						regMobileUser.setResUMotoDeviceID(false);
						regMobileUser.setRespUMotoDeviceID("INVALID UMDEVICEID");
						
					}else {
						if (loadDeviceId(regMobileUser.getUm_motodeviceId()) != null) {
							// logger.info("moto check device id: "+checkDeviceID2.getDeviceId());
							logger.info("RESPONSE: " + "EXIST UMDEVICEID");
							regMobileUser.setResUMotoDeviceID(false);
							regMobileUser.setRespUMotoDeviceID("EXIST UMDEVICEID");
						}
						
						// throw new MobiException(Status.INVALID_EZYRECTID);

					}
				
				}else {
					regMobileUser.setResUMotoTid(true);
					regMobileUser.setResUMotoDeviceID(true);
				}
				
				logger.info(regMobileUser.isResUMotoTid()+" "+regMobileUser.isResUMotoDeviceID());
				
				if (regMobileUser.getUm_ezypassTid() != null) {
					if (!regMobileUser.getUm_ezypassTid().matches("[0-9]+") || 
							!(regMobileUser.getUm_ezypassTid().length() >= 8 && regMobileUser.getUm_ezypassTid().length() < 9)) {
						logger.info("RESPONSE: " + "INVALID UMEZYPASSTID");
						regMobileUser.setResUEzypassTid(false);
						regMobileUser.setRespUEzypassDeviceID("INVALID UMEZYPASSTID");
					}else {
						if (loadTerminalDetailsByTid(regMobileUser.getUm_ezypassTid()) != null) {
							logger.info("RESPONSE: " + "EXIST UMEZYPASSTID");
							regMobileUser.setResUEzypassTid(false);
							regMobileUser.setRespUEzypassDeviceID("EXIST UMEZYPASSTID");
						}
					}

					if (!regMobileUser.getUm_ezypassdeviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getUm_ezypassdeviceId().length() >= 15
									&& regMobileUser.getUm_ezypassdeviceId().length() < 17)) {
						//resDataum_ezypassdeviceid = "Invalid DeviceID";
						logger.info("RESPONSE: " + "INVALID UMEZYPASSDEVICEID");
						regMobileUser.setResUEzypassDeviceID(false);
						regMobileUser.setRespUEzypassDeviceID("INVALID UMEZYPASSDEVICEID");
					}else {
						if (loadDeviceId(regMobileUser.getUm_ezypassdeviceId()) != null) {
							logger.info("RESPONSE: " + "EXIST UMEZYPASSDEVICEID");
							regMobileUser.setResUEzypassDeviceID(false);
							regMobileUser.setRespUEzypassDeviceID("EXIST UMEZYPASSDEVICEID");
						}
					}
				
				}else {
					regMobileUser.setResUEzypassTid(true);
					regMobileUser.setResUEzypassDeviceID(true);
				}
				if (regMobileUser.getUm_ezywayTid() != null) {
					if (!regMobileUser.getUm_ezywayTid().matches("[0-9]+") || 
							!(regMobileUser.getUm_ezywayTid().length() >= 8 && regMobileUser.getUm_ezywayTid().length() < 9)) {
						//resDataum_ezywaytid = "Invalid TID";
						logger.info("RESPONSE: " + "INVALID UMEZYWAYTID");
						regMobileUser.setResEzywayTid(false);
						regMobileUser.setRespEzywayTid("INVALID UMEZYWAYTID");
					}else {
						if (loadTerminalDetailsByTid(regMobileUser.getUm_ezywayTid()) != null) {
							logger.info("RESPONSE: " + "EXIST UMEZYPASSTID");
							regMobileUser.setResEzywayTid(false);
							regMobileUser.setRespEzywayTid("EXIST UMEZYWAYTID");
						}
						
						// throw new MobiException(Status.INVALID_EZYWAYTID);

					}

					if (!regMobileUser.getUm_ezywaydeviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getUm_ezywaydeviceId().length() >= 15
									&& regMobileUser.getUm_ezywaydeviceId().length() < 17)) {
						//resDataum_ezywaydeviceid = "Invalid DeviceID";
						logger.info("RESPONSE: " + "INVALID UMEZYWAYDEVICEID");
						regMobileUser.setResUEzywayDeviceID(false);
						regMobileUser.setRespUEzywayDeviceID("INVALID UMEZYWAYDEVICEID");
					}else {
						if (loadDeviceId(regMobileUser.getUm_ezywaydeviceId()) != null) {
							logger.info("RESPONSE: " + "EXIST UMEZYWAYDEVICEID");
							regMobileUser.setResUEzywayDeviceID(false);
							regMobileUser.setRespUEzywayDeviceID("EXIST UMEZYWAYDEVICEID");
						}
						
						// throw new MobiException(Status.INVALID_EZYRECTID);

					}
				
				}else {
					regMobileUser.setResEzywayTid(true);
					regMobileUser.setResUEzywayDeviceID(true);
				}
				
				
				if (regMobileUser.getUm_ezyrecTid() != null) {
					if (!regMobileUser.getUm_ezyrecTid().matches("[0-9]+") || 
							!(regMobileUser.getUm_ezyrecTid().length() >= 8 && regMobileUser.getUm_ezyrecTid().length() <9)) {
						//resDataum_ezyrectid = "Invalid TID";
						logger.info("RESPONSE: " + "INVALID UMEZYRECTID");
						regMobileUser.setResUEzyrecTid(false);
						regMobileUser.setRespUEzyrecTid("INVALID UMEZYRECTID");
					}else {
						if (loadTerminalDetailsByTid(regMobileUser.getUm_ezyrecTid()) != null) {
							logger.info("RESPONSE: " + "EXIST UMEZYRECTID");
							regMobileUser.setResUEzyrecTid(false);
							regMobileUser.setRespUEzyrecTid("EXIST UMEZYRECTID");
						}
					
					}

					if (!regMobileUser.getUm_ezyrecdeviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getUm_ezyrecdeviceId().length() >= 15
									&& regMobileUser.getUm_ezyrecdeviceId().length() < 17)) {
						//resDataum_ezyrecdeviceid = "Invalid DeviceID";
						logger.info("RESPONSE: " + "INVALID UMEZYRECDEVICEID");
						regMobileUser.setResUEzyrecDeviceID(false);
						regMobileUser.setRespUEzyrecDeviceID("INVALID UMEZYRECDEVICEID");
					}else {
						if (loadDeviceId(regMobileUser.getUm_ezyrecdeviceId()) != null) {
							logger.info("RESPONSE: " + "EXIST UMEZYRECDEVICEID");
							regMobileUser.setResUEzyrecDeviceID(false);
							regMobileUser.setRespUEzyrecDeviceID("EXIST UMEZYRECDEVICEID");
						}
						
					}

				
				}else {
					regMobileUser.setResUEzyrecTid(true);
					regMobileUser.setResUEzyrecDeviceID(true);
				}
				
			}else if (regMobileUser.getMerchantType().equalsIgnoreCase("P")) {
				

				if (regMobileUser.getTid() != null) {
					
					if (!regMobileUser.getTid().matches("[0-9]+") || 
							!(regMobileUser.getTid().length() >= 8 && regMobileUser.getTid().length() < 9)) {
						//responseDatatid = "Invalid TID";
						logger.info("RESPONSE: " + "INVALID TID");
						regMobileUser.setResTid(false);
						regMobileUser.setRespTid("INVALID TID");
					}else {
						if (loadTerminalDetailsByTid(regMobileUser.getTid()) != null) {
							logger.info("RESPONSE: " + "EXIST TID");
							regMobileUser.setResTid(false);
							regMobileUser.setRespTid("EXIST TID");
						}
					
					}
					if (!regMobileUser.getDeviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getDeviceId().length() >= 15 && regMobileUser.getDeviceId().length() < 17)) {
						//responseDatadeviceid = "Invalid DeviceID";
						logger.info("RESPONSE: " + "INVALID DEVICEID");

						regMobileUser.setResDeviceID(false);
						regMobileUser.setRespDeviceID("INVALID DEVICEID");
						// throw new MobiException(Status.INVALID_EZYRECTID);

					}else {
						if (loadDeviceId(regMobileUser.getDeviceId()) != null) {
							logger.info("RESPONSE: " + "EXIST DEVICEID");
							regMobileUser.setResDeviceID(false);
							regMobileUser.setRespDeviceID("EXIST DEVICEID");
						}
						
					}
				}else {
					regMobileUser.setResTid(true);
					regMobileUser.setResDeviceID(true);
				}
				
				
				if (regMobileUser.getMotoTid() != null) {
					if (!regMobileUser.getMotoTid().matches("[0-9]+")  || 
							!(regMobileUser.getMotoTid().length() >= 8 && regMobileUser.getMotoTid().length() < 9)) {
						//responseDatamototid = "Invalid TID";
						logger.info("RESPONSE: " + "INVALID MOTOTID");
						regMobileUser.setResMotoTid(false);
						regMobileUser.setRespMotoTid("INVALID MOTOTID");
						}else {
							if (loadTerminalDetailsByTid(regMobileUser.getMotoTid()) != null) {
								logger.info("RESPONSE: " + "EXIST MOTOTID");
								regMobileUser.setResMotoTid(false);
								regMobileUser.setRespMotoTid("EXIST MOTOTID");
							}
						
						}

					if (!regMobileUser.getMotodeviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getMotodeviceId().length() >= 15
									&& regMobileUser.getMotodeviceId().length() < 17)) {
						//responseDatamotodeviceid = "Invalid DeviceID";
						logger.info("RESPONSE: " + "INVALID MOTODEVICEID");

						regMobileUser.setResMotoDeviceID(false);
						regMobileUser.setRespMotoDeviceID("INVALID MOTODEVICEID");
						// throw new MobiException(Status.INVALID_EZYRECTID);

					}else {
						if (loadDeviceId(regMobileUser.getMotodeviceId()) != null) {
							logger.info("RESPONSE: " + "EXIST MOTODEVICEID");
							regMobileUser.setResMotoDeviceID(false);
							regMobileUser.setRespMotoDeviceID("EXIST MOTODEVICEID");
						}
						
					}
				
				}else {
					regMobileUser.setResMotoTid(true);
					regMobileUser.setResMotoDeviceID(true);
				}
				
				if (regMobileUser.getEzypassTid() != null) {
					if (!regMobileUser.getEzypassTid().matches("[0-9]+")
							 || !(regMobileUser.getEzypassTid().length() >= 8 && regMobileUser.getEzypassTid().length() < 9)) {
						//responseDataezypasstid = "Invalid TID";
						logger.info("RESPONSE: " + "INVALID EZYPASSTID");

						regMobileUser.setResEzypassTid(false);
						regMobileUser.setRespEzypassTid("INVALID EZYPASSTID");
					}else {
						if (loadTerminalDetailsByTid(regMobileUser.getEzypassTid()) != null) {
							logger.info("RESPONSE: " + "EXIST EZYPASSTID");
							regMobileUser.setResEzypassTid(false);
							regMobileUser.setRespEzypassTid("EXIST EZYPASSTID");
						}
					}
					if (!regMobileUser.getEzypassdeviceId().matches("[a-zA-Z0-9]*")
								|| !(regMobileUser.getEzypassdeviceId().length() >= 15
										&& regMobileUser.getEzypassdeviceId().length() < 17)) {
							//responseDataezypassdeviceid = "Invalid DeviceID";
							logger.info("RESPONSE: " + "INVLAID EZYPASSDEVICEID");

							regMobileUser.setResEzypassDeviceID(false);
							regMobileUser.setRespEzypassDeviceID("INVLAID EZYPASSDEVICEID");
							// throw new MobiException(Status.INVALID_EZYRECTID);

						}else {
							if (loadDeviceId(regMobileUser.getEzypassdeviceId()) != null) {
								logger.info("RESPONSE: " + "EXIST EZYPASSDEVICEID");
								regMobileUser.setResEzypassDeviceID(false);
								regMobileUser.setRespEzypassDeviceID("EXIST EZYPASSDEVICEID");
							}
							
						
					
					}
				}else {
					regMobileUser.setResEzypassTid(true);
					regMobileUser.setResEzypassDeviceID(true);
				}
				
				
				if (regMobileUser.getEzywayTid() != null) {
					if (!regMobileUser.getEzywayTid().matches("[0-9]+") ||
							!(regMobileUser.getEzywayTid().length() >= 8 && regMobileUser.getEzywayTid().length() < 9)) {
						//responseDataezywaytid = "Invalid TID";
						logger.info("RESPONSE: INVLAID EZYWAYTID");
						regMobileUser.setResEzywayTid(false);
						regMobileUser.setRespEzywayTid("INVALID EZYWAYTID");
						// throw new MobiException(Status.INVALID_EZYWAYTID);
					}else {
						if (loadTerminalDetailsByTid(regMobileUser.getEzywayTid()) != null) {
							logger.info("RESPONSE: EXIST EZYWAYTID");
							regMobileUser.setResEzywayTid(false);
							regMobileUser.setRespEzywayTid("EXIST EZYWAYTID");
						}
					}
					
						if (!regMobileUser.getEzywaydeviceId().matches("[a-zA-Z0-9]*")
								|| !(regMobileUser.getEzywaydeviceId().length() >= 15
										&& regMobileUser.getEzywaydeviceId().length() < 17)) {
							//responseDataezywaydeviceid = "Invalid DeviceID";
							logger.info("RESPONSE: INVALID EZYWAYDEVICEID");
							regMobileUser.setResEzywayDeviceID(false);
							regMobileUser.setRespEzywayDeviceID("INVALID EZYWAYDEVICEID");
							// throw new MobiException(Status.INVALID_EZYRECTID);

						}else {
							if (loadDeviceId(regMobileUser.getEzywaydeviceId()) != null) {
								logger.info("RESPONSE: EXIST EZYWAYDEVICEID");
								regMobileUser.setResEzywayDeviceID(false);
								regMobileUser.setRespEzywayDeviceID("EXIST EZYWAYDEVICEID");
							}
					
					}
				}else {
					regMobileUser.setResEzywayTid(true);
					regMobileUser.setResEzywayDeviceID(true);
				}
				
				if (regMobileUser.getEzyrecTid() != null) {
					if (!regMobileUser.getEzyrecTid().matches("[0-9]+") ||
							!(regMobileUser.getEzyrecTid().length() >= 8 && regMobileUser.getEzyrecTid().length() <9)) {
						//responseDataezyrectid = "Invalid TID";
						logger.info("RESPONSE: INVALID EZYRECTID");

						regMobileUser.setResEzyrecTid(false);
						regMobileUser.setRespEzyrecTid("INVALID EZYRECTID");
						// throw new MobiException(Status.INVALID_EZYRECTID);
					}else {
						if (loadTerminalDetailsByTid(regMobileUser.getEzyrecTid()) != null) {
							logger.info("RESPONSE: EXIST EZYRECTID");
							regMobileUser.setResEzyrecTid(false);
							regMobileUser.setRespEzyrecTid("EXIST EZYRECTID");
						}
					}
					
						if (!regMobileUser.getEzyrecdeviceId().matches("[a-zA-Z0-9]*")
								|| !(regMobileUser.getEzyrecdeviceId().length() >= 15
										&& regMobileUser.getEzyrecdeviceId().length() < 17)) {
							//responseDataezyrecdeviceid = "Invalid DeviceID";
							logger.info("RESPONSE: INVALID EZYRECDEVICEID");

							regMobileUser.setResEzyrecDeviceID(false);
							regMobileUser.setRespEzyrecDeviceID("INVALID EZYRECDEVICEID");
							// throw new MobiException(Status.INVALID_EZYRECTID);

						}else {
							if (loadDeviceId(regMobileUser.getEzyrecdeviceId()) != null) {
								logger.info("RESPONSE: EXIST EZYRECDEVICEID");
								regMobileUser.setResEzyrecDeviceID(false);
								regMobileUser.setRespEzyrecDeviceID("EXIST EZYRECDEVICEID");
							}
					
					}

					
					
				}else {
					regMobileUser.setResEzyrecTid(true);
					regMobileUser.setResEzyrecDeviceID(true);
				}

			}
			

			
			
		}else {
			if (regMobileUser.getTid() != null) {
				
				if (!regMobileUser.getTid().matches("[0-9]+") || 
						!(regMobileUser.getTid().length() >= 8 && regMobileUser.getTid().length() < 9)) {
					//responseDatatid = "Invalid TID";
					logger.info("RESPONSE: " + "INVALID TID");
					regMobileUser.setResTid(false);
					regMobileUser.setRespTid("INVALID TID");
				}else {
					if (loadTerminalDetailsByTid(regMobileUser.getTid()) != null) {
						logger.info("RESPONSE: " + "EXIST TID");
						regMobileUser.setResTid(false);
						regMobileUser.setRespTid("EXIST TID");
					}
				
				}
				if (!regMobileUser.getDeviceId().matches("[a-zA-Z0-9]*")
						|| !(regMobileUser.getDeviceId().length() >= 15 && regMobileUser.getDeviceId().length() < 17)) {
					//responseDatadeviceid = "Invalid DeviceID";
					logger.info("RESPONSE: " + "INVALID DEVICEID");

					regMobileUser.setResDeviceID(false);
					regMobileUser.setRespDeviceID("INVALID DEVICEID");
					// throw new MobiException(Status.INVALID_EZYRECTID);

				}else {
					if (loadDeviceId(regMobileUser.getDeviceId()) != null) {
						logger.info("RESPONSE: " + "EXIST DEVICEID");
						regMobileUser.setResDeviceID(false);
						regMobileUser.setRespDeviceID("EXIST DEVICEID");
					}
					
				}
			}else {
				regMobileUser.setResTid(true);
				regMobileUser.setResDeviceID(true);
			}
			
			
			if (regMobileUser.getMotoTid() != null) {
				if (!regMobileUser.getMotoTid().matches("[0-9]+")  || 
						!(regMobileUser.getMotoTid().length() >= 8 && regMobileUser.getMotoTid().length() < 9)) {
					//responseDatamototid = "Invalid TID";
					logger.info("RESPONSE: " + "INVALID MOTOTID");
					regMobileUser.setResMotoTid(false);
					regMobileUser.setRespMotoTid("INVALID MOTOTID");
					}else {
						if (loadTerminalDetailsByTid(regMobileUser.getMotoTid()) != null) {
							logger.info("RESPONSE: " + "EXIST MOTOTID");
							regMobileUser.setResMotoTid(false);
							regMobileUser.setRespMotoTid("EXIST MOTOTID");
						}
					
					}

				if (!regMobileUser.getMotodeviceId().matches("[a-zA-Z0-9]*")
						|| !(regMobileUser.getMotodeviceId().length() >= 15
								&& regMobileUser.getMotodeviceId().length() < 17)) {
					//responseDatamotodeviceid = "Invalid DeviceID";
					logger.info("RESPONSE: " + "INVALID MOTODEVICEID");

					regMobileUser.setResMotoDeviceID(false);
					regMobileUser.setRespMotoDeviceID("INVALID MOTODEVICEID");
					// throw new MobiException(Status.INVALID_EZYRECTID);

				}else {
					if (loadDeviceId(regMobileUser.getMotodeviceId()) != null) {
						logger.info("RESPONSE: " + "EXIST MOTODEVICEID");
						regMobileUser.setResMotoDeviceID(false);
						regMobileUser.setRespMotoDeviceID("EXIST MOTODEVICEID");
					}
					
				}
			
			}else {
				regMobileUser.setResMotoTid(true);
				regMobileUser.setResMotoDeviceID(true);
			}
			
			if (regMobileUser.getEzypassTid() != null) {
				if (!regMobileUser.getEzypassTid().matches("[0-9]+")
						 || !(regMobileUser.getEzypassTid().length() >= 8 && regMobileUser.getEzypassTid().length() < 9)) {
					//responseDataezypasstid = "Invalid TID";
					logger.info("RESPONSE: " + "INVALID EZYPASSTID");

					regMobileUser.setResEzypassTid(false);
					regMobileUser.setRespEzypassTid("INVALID EZYPASSTID");
				}else {
					if (loadTerminalDetailsByTid(regMobileUser.getEzypassTid()) != null) {
						logger.info("RESPONSE: " + "EXIST EZYPASSTID");
						regMobileUser.setResEzypassTid(false);
						regMobileUser.setRespEzypassTid("EXIST EZYPASSTID");
					}
				}
				if (!regMobileUser.getEzypassdeviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getEzypassdeviceId().length() >= 15
									&& regMobileUser.getEzypassdeviceId().length() < 17)) {
						//responseDataezypassdeviceid = "Invalid DeviceID";
						logger.info("RESPONSE: " + "INVLAID EZYPASSDEVICEID");

						regMobileUser.setResEzypassDeviceID(false);
						regMobileUser.setRespEzypassDeviceID("INVLAID EZYPASSDEVICEID");
						// throw new MobiException(Status.INVALID_EZYRECTID);

					}else {
						if (loadDeviceId(regMobileUser.getEzypassdeviceId()) != null) {
							logger.info("RESPONSE: " + "EXIST EZYPASSDEVICEID");
							regMobileUser.setResEzypassDeviceID(false);
							regMobileUser.setRespEzypassDeviceID("EXIST EZYPASSDEVICEID");
						}
						
					
				
				}
			}else {
				regMobileUser.setResEzypassTid(true);
				regMobileUser.setResEzypassDeviceID(true);
			}
			
			
			if (regMobileUser.getEzywayTid() != null) {
				if (!regMobileUser.getEzywayTid().matches("[0-9]+") ||
						!(regMobileUser.getEzywayTid().length() >= 8 && regMobileUser.getEzywayTid().length() < 9)) {
					//responseDataezywaytid = "Invalid TID";
					logger.info("RESPONSE: INVLAID EZYWAYTID");
					regMobileUser.setResEzywayTid(false);
					regMobileUser.setRespEzywayTid("INVALID EZYWAYTID");
					// throw new MobiException(Status.INVALID_EZYWAYTID);
				}else {
					if (loadTerminalDetailsByTid(regMobileUser.getEzywayTid()) != null) {
						logger.info("RESPONSE: EXIST EZYWAYTID");
						regMobileUser.setResEzywayTid(false);
						regMobileUser.setRespEzywayTid("EXIST EZYWAYTID");
					}
				}
				
					if (!regMobileUser.getEzywaydeviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getEzywaydeviceId().length() >= 15
									&& regMobileUser.getEzywaydeviceId().length() < 17)) {
						//responseDataezywaydeviceid = "Invalid DeviceID";
						logger.info("RESPONSE: INVALID EZYWAYDEVICEID");
						regMobileUser.setResEzywayDeviceID(false);
						regMobileUser.setRespEzywayDeviceID("INVALID EZYWAYDEVICEID");
						// throw new MobiException(Status.INVALID_EZYRECTID);

					}else {
						if (loadDeviceId(regMobileUser.getEzywaydeviceId()) != null) {
							logger.info("RESPONSE: EXIST EZYWAYDEVICEID");
							regMobileUser.setResEzywayDeviceID(false);
							regMobileUser.setRespEzywayDeviceID("EXIST EZYWAYDEVICEID");
						}
				
				}
			}else {
				regMobileUser.setResEzywayTid(true);
				regMobileUser.setResEzywayDeviceID(true);
			}
			
			if (regMobileUser.getEzyrecTid() != null) {
				if (!regMobileUser.getEzyrecTid().matches("[0-9]+") ||
						!(regMobileUser.getEzyrecTid().length() >= 8 && regMobileUser.getEzyrecTid().length() <9)) {
					//responseDataezyrectid = "Invalid TID";
					logger.info("RESPONSE: INVALID EZYRECTID");

					regMobileUser.setResEzyrecTid(false);
					regMobileUser.setRespEzyrecTid("INVALID EZYRECTID");
					// throw new MobiException(Status.INVALID_EZYRECTID);
				}else {
					if (loadTerminalDetailsByTid(regMobileUser.getEzyrecTid()) != null) {
						logger.info("RESPONSE: EXIST EZYRECTID");
						regMobileUser.setResEzyrecTid(false);
						regMobileUser.setRespEzyrecTid("EXIST EZYRECTID");
					}
				}
				
					if (!regMobileUser.getEzyrecdeviceId().matches("[a-zA-Z0-9]*")
							|| !(regMobileUser.getEzyrecdeviceId().length() >= 15
									&& regMobileUser.getEzyrecdeviceId().length() < 17)) {
						//responseDataezyrecdeviceid = "Invalid DeviceID";
						logger.info("RESPONSE: INVALID EZYRECDEVICEID");

						regMobileUser.setResEzyrecDeviceID(false);
						regMobileUser.setRespEzyrecDeviceID("INVALID EZYRECDEVICEID");
						// throw new MobiException(Status.INVALID_EZYRECTID);

					}else {
						if (loadDeviceId(regMobileUser.getEzyrecdeviceId()) != null) {
							logger.info("RESPONSE: EXIST EZYRECDEVICEID");
							regMobileUser.setResEzyrecDeviceID(false);
							regMobileUser.setRespEzyrecDeviceID("EXIST EZYRECDEVICEID");
						}
				
				}

				
				
			}else {
				regMobileUser.setResEzyrecTid(true);
				regMobileUser.setResEzyrecDeviceID(true);
			}

		}
		
		
		
		return regMobileUser;
		
	}
	
	@SuppressWarnings({ "unused", "null" })
	// @javax.transaction.Transactional
	public RegMobileUser setDataForUpdateMobileUser(final RegMobileUser regMobileUser) {
		
		if (regMobileUser.getEzyrecTid() == null || regMobileUser.getEzyrecTid().isEmpty()) {
			// logger.info("empty ezyrec");
			regMobileUser.setEzyrecTid(null);
		}
		if (regMobileUser.getEzywayTid() == null || regMobileUser.getEzywayTid().isEmpty()) {
			// logger.info("empty ezyway");
			regMobileUser.setEzywayTid(null);
		}
		if (regMobileUser.getEzypassTid() == null || regMobileUser.getEzypassTid().isEmpty()) {
			// logger.info("empty getEzypassTid");
			regMobileUser.setEzypassTid(null);
		}
		if (regMobileUser.getTid() == null || regMobileUser.getTid().isEmpty()) {
			// logger.info("empty getTid");
			regMobileUser.setTid(null);
		}
		if (regMobileUser.getMotoTid() == null || regMobileUser.getMotoTid().isEmpty()) {
			// logger.info("empty getMotoTid");
			regMobileUser.setMotoTid(null);
		}
		
		// check Empty DeviceID details

		if (regMobileUser.getEzyrecdeviceId() == null || regMobileUser.getEzyrecdeviceId().isEmpty()) {
			// logger.info("empty ezyrec");
			regMobileUser.setEzyrecdeviceId(null);
		}
		if (regMobileUser.getEzywaydeviceId() == null || regMobileUser.getEzywaydeviceId().isEmpty()) {
			// logger.info("empty ezyway");
			regMobileUser.setEzywaydeviceId(null);
		}
		if (regMobileUser.getEzypassdeviceId() == null || regMobileUser.getEzypassdeviceId().isEmpty()) {
			// logger.info("empty getEzypassTid");
			regMobileUser.setEzypassdeviceId(null);
		}
		if (regMobileUser.getDeviceId() == null || regMobileUser.getDeviceId().isEmpty()) {
			// logger.info("empty getTid");
			regMobileUser.setDeviceId(null);
		}
		if (regMobileUser.getMotodeviceId() == null || regMobileUser.getMotodeviceId().isEmpty()) {
			// logger.info("empty getMotoTid");
			regMobileUser.setMotodeviceId(null);
		}
		
		//umobile
		
		if (regMobileUser.getUm_ezyrecTid() == null || regMobileUser.getUm_ezyrecTid().isEmpty()) {
			// logger.info("empty ezyrec");
			regMobileUser.setUm_ezyrecTid(null);
		}
		if (regMobileUser.getUm_ezywayTid() == null || regMobileUser.getUm_ezywayTid().isEmpty()) {
			// logger.info("empty ezyway");
			regMobileUser.setUm_ezywayTid(null);
		}
		if (regMobileUser.getUm_ezypassTid() == null || regMobileUser.getUm_ezypassTid().isEmpty()) {
			// logger.info("empty getEzypassTid");
			regMobileUser.setUm_ezypassTid(null);
		}
		if (regMobileUser.getUm_tid() == null || regMobileUser.getUm_tid().isEmpty()) {
			// logger.info("empty getTid");
			regMobileUser.setUm_tid(null);
		}
		if (regMobileUser.getUm_motoTid() == null || regMobileUser.getUm_motoTid().isEmpty()) {
			// logger.info("empty getMotoTid");
			regMobileUser.setUm_motoTid(null);
		}

		// check Empty DeviceID details

		if (regMobileUser.getUm_ezyrecdeviceId() == null || regMobileUser.getUm_ezyrecdeviceId().isEmpty()) {
			// logger.info("empty ezyrec");
			regMobileUser.setUm_ezyrecdeviceId(null);
		}
		if (regMobileUser.getUm_ezywaydeviceId() == null || regMobileUser.getUm_ezywaydeviceId().isEmpty()) {
			// logger.info("empty ezyway");
			regMobileUser.setUm_ezywaydeviceId(null);
		}
		if (regMobileUser.getUm_ezypassdeviceId() == null || regMobileUser.getUm_ezypassdeviceId().isEmpty()) {
			// logger.info("empty getEzypassTid");
			regMobileUser.setUm_ezypassdeviceId(null);
		}
		if (regMobileUser.getUm_deviceId() == null || regMobileUser.getUm_deviceId().isEmpty()) {
			// logger.info("empty getTid");
			regMobileUser.setUm_deviceId(null);
		}
		if (regMobileUser.getUm_motodeviceId() == null || regMobileUser.getUm_motodeviceId().isEmpty()) {
			// logger.info("empty getMotoTid");
			regMobileUser.setUm_motodeviceId(null);
		}

		

			
		
		if (regMobileUser.getTid() != null && !regMobileUser.getUpdateType().equals("ezywire")
				&& regMobileUser.getDeviceId()!=null) {
			//validating ezywire tid
			if (!regMobileUser.getTid().matches("[0-9]+") || 
					!(regMobileUser.getTid().length() >= 8 && regMobileUser.getTid().length() < 9)) {
				//responseDatatid = ;
				logger.info("responseDatatid: " + "Invalid TID");
				regMobileUser.setResTid(false);

			}else {
				logger.info("tid to check :" + regMobileUser.getTid());
				
				if (loadTerminalDetailsByTid(regMobileUser.getTid()) != null) {
					//responseDatatid = "Tid already exist";
					regMobileUser.setResTid(false);
				}
			}
			
			//validating ezywire deviceID
			if (regMobileUser.getDeviceId() != null) {
				if (!regMobileUser.getDeviceId().matches("[a-zA-Z0-9]*")
						|| !(regMobileUser.getDeviceId().length() >= 15 && regMobileUser.getDeviceId().length() < 17)) {
					//responseDatadeviceid = "Invalid DeviceID";
					logger.info("responseDatadeviceid: " + "Invalid DeviceID");
					regMobileUser.setResDeviceID(false);

				}else {
					if (loadDeviceId(regMobileUser.getDeviceId()) != null) {
						//responseDatadeviceid = "DeviceID already exist";
						logger.info("responseDatadeviceid: " + "DeviceID already exist");
						regMobileUser.setResDeviceID(false);
						
					}
				}
			}
		}else {
			regMobileUser.setResDeviceID(true);
			regMobileUser.setResTid(true);
		}
		if (regMobileUser.getMotoTid() != null && !regMobileUser.getUpdateType().equals("moto")
				&& regMobileUser.getMotodeviceId()!=null) {
			//validating mototid
			if (!regMobileUser.getMotoTid().matches("[0-9]+") || 
					!(regMobileUser.getMotoTid().length() >= 8 && regMobileUser.getMotoTid().length() < 9)) {
				//responseDatamototid = "Invalid TID";
				logger.info("responseDatamototid: Invalid TID");
				regMobileUser.setResMotoTid(false);

			}else{
				logger.info("mototid to check :" + regMobileUser.getMotoTid());
				//motoTD = mobileUserService.loadTerminalDetailsByTid(regMobileUser.getMotoTid());
				if (loadTerminalDetailsByTid(regMobileUser.getMotoTid()) != null) {
					//responseDatamototid = "Tid already exist";
					regMobileUser.setResMotoTid(false);
					
				}
			}
			
			//validating motodeviceID
			if (regMobileUser.getMotodeviceId() != null) {
				if (!regMobileUser.getMotodeviceId().matches("[a-zA-Z0-9]*")
						|| !(regMobileUser.getMotodeviceId().length() >= 15
								&& regMobileUser.getMotodeviceId().length() < 17)) {
					//responseDatamotodeviceid = "Invalid DeviceID";
					//logger.info("responseDatamotodeviceid: " + responseDatamotodeviceid);

					regMobileUser.setResMotoDeviceID(false);

				}else {
					logger.info("device id to check moto:" + regMobileUser.getMotodeviceId());
					//motoDID = mobileUserService.loadDeviceId(regMobileUser.getMotodeviceId());
					if (loadDeviceId(regMobileUser.getMotodeviceId()) != null) {
						//responseDatamotodeviceid = "DeviceID already exist";
						regMobileUser.setResMotoDeviceID(false);
					}
				}
			}
		}else {
			regMobileUser.setResMotoDeviceID(true);
			regMobileUser.setResMotoTid(true);
		}
		if (regMobileUser.getEzypassTid() != null && !regMobileUser.getUpdateType().equals("ezypass")) {
			//validatinng ezypassTid
			if (!regMobileUser.getEzypassTid().matches("[0-9]+")
					|| !(regMobileUser.getEzypassTid().length() >= 8 && regMobileUser.getEzypassTid().length() < 9)) {
				//responseDataezypasstid = "Invalid TID";
				//logger.info("responseDataezypasstid: " + responseDataezypasstid);

				regMobileUser.setResEzypassTid(false);

			}else {
				logger.info("ezypass tid to check :" + regMobileUser.getEzypassTid());
				//ezypassTD = mobileUserService.loadTerminalDetailsByTid(regMobileUser.getEzypassTid());
				if (loadTerminalDetailsByTid(regMobileUser.getEzypassTid()) != null) {
					//responseDataezypasstid = "Tid already exist";
					regMobileUser.setResEzypassTid(false);
				}
			}
			
			//validating ezypassdeviceID
			if (regMobileUser.getEzypassdeviceId() != null) {
				if (!regMobileUser.getEzypassdeviceId().matches("[a-zA-Z0-9]*")
						|| !(regMobileUser.getEzypassdeviceId().length() >= 15
								&& regMobileUser.getEzypassdeviceId().length() < 17)) {
					//responseDataepassdeviceid = "Invalid DeviceID";
					
					//logger.info("responseDataezypassdeviceid: " + responseDataepassdeviceid);

					regMobileUser.setResEzypassDeviceID(false);

				}else {
					logger.info("device id to check ezypass:" + regMobileUser.getEzypassdeviceId());
					//ezypassDID = mobileUserService.loadDeviceId(regMobileUser.getEzypassdeviceId());
					if (loadDeviceId(regMobileUser.getEzypassdeviceId()) != null) {
						//responseDataepassdeviceid = "DeviceID already exist";
						regMobileUser.setResEzypassDeviceID(false);
					}
				}
			}
		}else {
			regMobileUser.setResEzypassDeviceID(true);
			regMobileUser.setResEzypassTid(true);
		}
		
		if (regMobileUser.getEzyrecTid() != null && !regMobileUser.getUpdateType().equals("ezyrec")) {
			//validating ezyrecTid
			if (!regMobileUser.getEzyrecTid().matches("[0-9]+") || 
					!(regMobileUser.getEzyrecTid().length() >= 8 && regMobileUser.getEzyrecTid().length() <9)) {
				//responseDataezyrectid = "Invalid TID";
				//logger.info("responseDataezyrectid: " + responseDataezyrectid);

				regMobileUser.setResEzyrecTid(false);

			}else {
				logger.info("ezyrec tid to check :" + regMobileUser.getEzyrecTid());
				//ezyrecTD = mobileUserService.loadTerminalDetailsByTid(regMobileUser.getEzyrecTid());
				if (loadTerminalDetailsByTid(regMobileUser.getEzyrecTid()) != null) {
					//responseDataezyrectid = "Tid already exist";
					regMobileUser.setResEzyrecTid(false);
				}
			}
			
			//validating ezyrecDeviceID
			if (regMobileUser.getEzyrecdeviceId() != null) {
				if (!regMobileUser.getEzyrecdeviceId().matches("[a-zA-Z0-9]*")
						|| !(regMobileUser.getEzyrecdeviceId().length() >= 15
								&& regMobileUser.getEzyrecdeviceId().length() < 17)) {
					//responseDataezyrecdeviceid = "Invalid DeviceID";
					//logger.info("responseDataezyrecdeviceid: " + responseDataezyrecdeviceid);

					regMobileUser.setResEzyrecDeviceID(false);

				}else {
					logger.info("device id to check ezyrec:" + regMobileUser.getEzyrecdeviceId());
					//ezyrecDID = mobileUserService.loadDeviceId(regMobileUser.getEzyrecdeviceId());
					if (loadDeviceId(regMobileUser.getEzyrecdeviceId()) != null) {
						//responseDataezyrecdeviceid = "DeviceID already exist";
						regMobileUser.setResEzyrecDeviceID(false);
						
					}
				}

			}
		}else {
			regMobileUser.setResEzyrecDeviceID(true);
			regMobileUser.setResEzyrecTid(true);
		}

		//umobille
		if (regMobileUser.getUm_tid() != null && !regMobileUser.getUpdateType().equals("umobile")
				&& regMobileUser.getUm_deviceId()!=null) {
			//validating ezywire tid
			if (!regMobileUser.getUm_tid().matches("[0-9]+") || 
					!(regMobileUser.getUm_tid().length() >= 8 && regMobileUser.getUm_tid().length() < 9)) {
				//responseDatatid = ;
				logger.info("responseDatatid: " + "Invalid UTID");
				regMobileUser.setResUTid(false);

			}else {
				logger.info("tid to check :" + regMobileUser.getUm_tid());
				
				if (loadTerminalDetailsByTid(regMobileUser.getUm_tid()) != null) {
					//responseDatatid = "Tid already exist";
					logger.info("responseDatatid: " + "Exist UTID");
					regMobileUser.setResUTid(false);
				}
			}
			
			//validating ezywire deviceID
			
				if (!regMobileUser.getUm_deviceId().matches("[a-zA-Z0-9]*")
						|| !(regMobileUser.getUm_deviceId().length() >= 15 && regMobileUser.getUm_deviceId().length() < 17)) {
					//responseDatadeviceid = "Invalid DeviceID";
					logger.info("responseDatadeviceid: " + "Invalid UDeviceID");
					regMobileUser.setResUDeviceID(false);

				}else {
					if (loadDeviceId(regMobileUser.getUm_deviceId()) != null) {
						//responseDatadeviceid = "DeviceID already exist";
						logger.info("responseDatadeviceid: " + "UDeviceID already exist");
						regMobileUser.setResUDeviceID(false);
						
					}
				}
			
		}else {
			regMobileUser.setResUDeviceID(true);
			regMobileUser.setResUTid(true);
		}
		
		if (regMobileUser.getUm_motoTid() != null && regMobileUser.getUpdateType().equals("umobilemoto")
				&& regMobileUser.getUm_motodeviceId()!=null) {
			//validating ezywire tid
			if (!regMobileUser.getUm_motoTid().matches("[0-9]+") || 
					!(regMobileUser.getUm_motoTid().length() >= 8 && regMobileUser.getUm_motoTid().length() < 9)) {
				//responseDatatid = ;
				logger.info("responseDatatid: " + "Invalid TID");
				regMobileUser.setResUMotoTid(false);

			}else {
				logger.info("tid to check :" + regMobileUser.getUm_motoTid());
				
				if (loadTerminalDetailsByTid(regMobileUser.getUm_motoTid()) != null) {
					logger.info("responseDatatid: Tid already exist");
					regMobileUser.setResUMotoTid(false);
				}
			}
			
			//validating ezywire deviceID
			if (regMobileUser.getUm_motodeviceId() != null) {
				if (!regMobileUser.getUm_motodeviceId().matches("[a-zA-Z0-9]*")
						|| !(regMobileUser.getUm_motodeviceId().length() >= 15 && regMobileUser.getUm_motodeviceId().length() < 17)) {
					//responseDatadeviceid = "Invalid DeviceID";
					logger.info("responseDatadeviceid: " + "Invalid DeviceID");
					regMobileUser.setResUMotoDeviceID(false);

				}else {
					if (loadDeviceId(regMobileUser.getUm_motodeviceId()) != null) {
						//responseDatadeviceid = "DeviceID already exist";
						logger.info("responseDatadeviceid: " + "DeviceID already exist");
						regMobileUser.setResUMotoDeviceID(false);
						
					}
				}
			}
		}else {
			regMobileUser.setResUMotoDeviceID(true);
			regMobileUser.setResUMotoTid(true);
		}
		
		
		if (regMobileUser.getUm_ezywayTid() != null && regMobileUser.getUpdateType().equals("umobileezyway")
				&& regMobileUser.getUm_ezywaydeviceId()!=null) {
			//validating ezywire tid
			if (!regMobileUser.getUm_ezywayTid().matches("[0-9]+") || 
					!(regMobileUser.getUm_ezywayTid().length() >= 8 && regMobileUser.getUm_ezywayTid().length() < 9)) {
				//responseDatatid = ;
				logger.info("responseDatatid: " + "Invalid TID");
				regMobileUser.setResUEzywayTid(false);

			}else {
				logger.info("tid to check :" + regMobileUser.getUm_ezywayTid());
				
				if (loadTerminalDetailsByTid(regMobileUser.getUm_ezywayTid()) != null) {
					logger.info("responseDatatid: Tid already exist");
					regMobileUser.setResUEzywayTid(false);
				}
			}
			
			//validating ezywire deviceID
			if (regMobileUser.getUm_ezywaydeviceId() != null) {
				if (!regMobileUser.getUm_ezywaydeviceId().matches("[a-zA-Z0-9]*")
						|| !(regMobileUser.getUm_ezywaydeviceId().length() >= 15 && regMobileUser.getUm_ezywaydeviceId().length() < 17)) {
					//responseDatadeviceid = "Invalid DeviceID";
					logger.info("responseDatadeviceid: " + "Invalid DeviceID");
					regMobileUser.setResUEzywayDeviceID(false);

				}else {
					if (loadDeviceId(regMobileUser.getUm_ezywaydeviceId()) != null) {
						//responseDatadeviceid = "DeviceID already exist";
						logger.info("responseDatadeviceid: " + "DeviceID already exist");
						regMobileUser.setResUEzywayDeviceID(false);
						
					}
				}
			}
		}else {
			regMobileUser.setResUEzywayDeviceID(true);
			regMobileUser.setResUEzywayTid(true);
		}
		
		if (regMobileUser.getUm_ezypassTid() != null && !regMobileUser.getUpdateType().equals("umezypass")
				&& regMobileUser.getUm_ezypassdeviceId()!=null) {
			//validating mototid
			if (!regMobileUser.getUm_ezypassTid().matches("[0-9]+") || 
					!(regMobileUser.getUm_ezypassTid().length() >= 8 && regMobileUser.getUm_ezypassTid().length() < 9)) {
				//responseDatamototid = "Invalid TID";
				logger.info("responseDataezypasstid: Invalid TID");
				regMobileUser.setResUEzypassTid(false);

			}else{
				logger.info("ezypass to check :" + regMobileUser.getUm_ezypassTid());
				//motoTD = mobileUserService.loadTerminalDetailsByTid(regMobileUser.getMotoTid());
				if (loadTerminalDetailsByTid(regMobileUser.getUm_ezypassTid()) != null) {
					//responseDatamototid = "Tid already exist";
					regMobileUser.setResUEzypassTid(false);
					
				}
			}
			
			//validating motodeviceID
			if (regMobileUser.getUm_ezypassdeviceId() != null) {
				if (!regMobileUser.getUm_ezypassdeviceId().matches("[a-zA-Z0-9]*")
						|| !(regMobileUser.getUm_ezypassdeviceId().length() >= 15
								&& regMobileUser.getUm_ezypassdeviceId().length() < 17)) {
					//responseDatamotodeviceid = "Invalid DeviceID";
					//logger.info("responseDatamotodeviceid: " + responseDatamotodeviceid);

					regMobileUser.setResUEzypassDeviceID(false);

				}else {
					logger.info("device id :" + regMobileUser.getMotodeviceId());
					//motoDID = mobileUserService.loadDeviceId(regMobileUser.getMotodeviceId());
					if (loadDeviceId(regMobileUser.getMotodeviceId()) != null) {
						logger.info("device id :DeviceID already exist");
						regMobileUser.setResUEzypassDeviceID(false);
					}
				}
			}
		}else {
			regMobileUser.setResUEzypassDeviceID(true);
			regMobileUser.setResUEzypassTid(true);
		}
		if (regMobileUser.getUm_ezyrecTid() != null && !regMobileUser.getUpdateType().equals("umezyrec")) {
			//validatinng ezypassTid
			if (!regMobileUser.getUm_ezyrecTid().matches("[0-9]+")
					|| !(regMobileUser.getUm_ezyrecTid().length() >= 8 && regMobileUser.getUm_ezyrecTid().length() < 9)) {
				//responseDataezypasstid = "Invalid TID";
				//logger.info("responseDataezypasstid: " + responseDataezypasstid);

				regMobileUser.setResUEzyrecTid(false);

			}else {
				logger.info("ezyrec tid to check :" + regMobileUser.getUm_ezyrecTid());
				//ezypassTD = mobileUserService.loadTerminalDetailsByTid(regMobileUser.getEzypassTid());
				if (loadTerminalDetailsByTid(regMobileUser.getUm_ezyrecTid()) != null) {
					logger.info("ezyrec tid to check :Tid already exist");
					regMobileUser.setResUEzyrecTid(false);
				}
			}
			
			//validating ezypassdeviceID
			if (regMobileUser.getUm_ezyrecdeviceId() != null) {
				if (!regMobileUser.getUm_ezyrecdeviceId().matches("[a-zA-Z0-9]*")
						|| !(regMobileUser.getUm_ezyrecdeviceId().length() >= 15
								&& regMobileUser.getUm_ezyrecdeviceId().length() < 17)) {
					//responseDataepassdeviceid = "Invalid DeviceID";
					
					//logger.info("responseDataezypassdeviceid: " + responseDataepassdeviceid);

					regMobileUser.setResUEzyrecDeviceID(false);

				}else {
					logger.info("device id to check ezyrec:" + regMobileUser.getUm_ezyrecdeviceId());
					//ezypassDID = mobileUserService.loadDeviceId(regMobileUser.getEzypassdeviceId());
					if (loadDeviceId(regMobileUser.getUm_ezyrecdeviceId()) != null) {
						logger.info("device id to check ezyrec: DeviceID already exist");
						regMobileUser.setResUEzyrecDeviceID(false);
					}
				}
			}
		}else {
			regMobileUser.setResUEzyrecDeviceID(true);
			regMobileUser.setResUEzyrecTid(true);
		}
		
		
		
	
		
		
		
		
		logger.info("isResDeviceID"+regMobileUser.isResDeviceID());
		logger.info("isResEzypassDeviceID"+regMobileUser.isResEzypassDeviceID());
		logger.info("isResEzyrecDeviceID"+regMobileUser.isResEzyrecDeviceID());
		logger.info("isResMotoDeviceID"+regMobileUser.isResMotoDeviceID());
		logger.info("isResUDeviceID"+regMobileUser.isResUDeviceID());
		logger.info("isResTid"+regMobileUser.isResTid());
		logger.info("isResMotoTid"+regMobileUser.isResMotoTid());
		logger.info("isResEzypassTid"+regMobileUser.isResEzypassTid());
		logger.info("isResEzyrecTid"+regMobileUser.isResEzyrecTid());
		logger.info("isResUTid"+regMobileUser.isResUTid());
	
		
		logger.info("umtid: "+regMobileUser.getUm_tid()+" um_mid: "+regMobileUser.getUm_mid());
		
		return regMobileUser;
		
		
	}
	
	
	
	//new changes for adding mobileuser details
	@SuppressWarnings({ "unused", "null" })
	// @javax.transaction.Transactional
	public RegMobileUser addMobileUser(final RegMobileUser regMobileUser) {

		boolean checkEzypod = false;
		Merchant merchant = null;
		MID mid = null;
		if (regMobileUser.getEzywayMid() != null) {
			mid = merchantDAO.loadEzywayMid(regMobileUser.getEzywayMid());
			merchant = merchantDAO.loadMerchant(mid);
		}
		if (regMobileUser.getEzyrecMid() != null) {
			if (regMobileUser.getEzypod().equals("Yes") || regMobileUser.getEzypod() == "Yes") {
				mid = merchantDAO.loadEzyrecMid(regMobileUser.getEzyrecMid());
				merchant = merchantDAO.loadMerchant(mid);
			}
		}
		DeviceRandomNumber d = new DeviceRandomNumber();
		String activationCode = null;
		String front = null;
		String back = null;

		boolean ac = false;
		do {
			front = d.generateRandomString();
			back = d.generateRandomString();

			if (regMobileUser.getTid() != null && regMobileUser.getMotoTid() != null
					&& regMobileUser.getEzypassTid() != null && regMobileUser.getEzyrecMid() != null) {
				activationCode = front + "MOBIPYT" + back;
				logger.info("activationCode MOBIPYT: " + activationCode);
			} else if (regMobileUser.getMotoTid() != null) {
				activationCode = front + "EZYMOTO" + back;
				logger.info("activationCode EZYMOTO: " + activationCode);
			} else if (regMobileUser.getEzypassTid() != null) {
				activationCode = front + "EZYPASS" + back;
				logger.info("activationCode EZYPASS: " + activationCode);
			} else if (regMobileUser.getEzyrecTid() != null) {
				activationCode = front + "EZYREC" + back;
				logger.info("activationCode EZYREC: " + activationCode);
			} else if (regMobileUser.getEzywayTid() != null) {
				activationCode = front + "MOBIPYT" + back;
				logger.info("activationCode EZYWAY: " + activationCode);
			} else {
				activationCode = front + "MOBIPYT" + back;
				logger.info("activationCode MOBI: " + activationCode);
			}

			TerminalDetails acTd = loadTerminalDetailsByActivationCode(activationCode);
			if (acTd != null) {
				ac = true;
			}

		} while (ac);

		regMobileUser.setActivationCode(activationCode);
	//	TempletFields tempField = new TempletFields();

		/*if (regMobileUser.getSalutation() != null) {
			tempField.setSalutation(regMobileUser.getSalutation());
		} else {
			tempField.setSalutation("Ms");

		}*/
		
		/*if (regMobileUser.getMotodeviceId() != null && !(regMobileUser.getMotodeviceId().isEmpty())) {
			tempField.setMotodeviceId("MOTO" + regMobileUser.getMotodeviceId());
			
			logger.info("moto device type:" + tempField.getMotodeviceId() + tempField.getActivationCode());
		}
		if (regMobileUser.getEzypassdeviceId() != null && !(regMobileUser.getEzypassdeviceId().isEmpty())) {
			tempField.setEzypassdeviceId("EZYPASS" + regMobileUser.getEzypassdeviceId());
			
			logger.info("ezypass device type:" + tempField.getEzypassdeviceId() + tempField.getActivationCode());
		}

		if (regMobileUser.getEzywaydeviceId() != null && !(regMobileUser.getEzywaydeviceId().isEmpty())) {
			tempField.setEzywaydeviceId("EZYWAY" + regMobileUser.getEzywaydeviceId());
			
			logger.info("ezypass device type:" + tempField.getEzywaydeviceId() + tempField.getActivationCode());
		}
		if (regMobileUser.getEzyrecdeviceId() != null && !(regMobileUser.getEzyrecdeviceId().isEmpty())) {
			tempField.setEzyrecdeviceId("EZYREC" + regMobileUser.getEzyrecdeviceId());
			
			logger.info("ezypass device type:" + tempField.getEzyrecdeviceId() + tempField.getActivationCode());
		}*/

		/*tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
		tempField.setActivationCode(activationCode);
		tempField.setToAddress(toAddress);
		tempField.setFirstName(regMobileUser.getMerchantName());*/
		
		
		SendSMSMessage send=new SendSMSMessage();
		MsgDto md=new MsgDto();
		md.setFirstName(regMobileUser.getMerchantName());
		//md.setLastName(merchant.getLastName());
		//md.setUserName(merchant.getUsername());
		md.setActivationCode(activationCode);
		if (regMobileUser.getDeviceId() != null && !(regMobileUser.getDeviceId().isEmpty())) {
			md.setDeviceID(regMobileUser.getDeviceId());
			logger.info("ezywire device type:" + md.getDeviceID() + md.getActivationCode());
		}
		md.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
		
		md.setMerchantEmail(regMobileUser.getEmailId());
		

		/*
		 List<NameValuePair> headers = new ArrayList<NameValuePair>();
		headers.add(new NameValuePair("HEADER", "test"));
		String fromAddress = "info@gomobi.io";
		String apiKey = PropertyLoader.getFile().getProperty("APIKEY"); 
		  String ccMail = PropertyLoader.getFile().getProperty("MOBILEUSER_ADD_CCMAIL");
		String bccMail = PropertyLoader.getFile().getProperty("MOBILEUSER_ADD_BCCMAIL");
		String toAddress = regMobileUser.getEmailId();
		String subject = PropertyLoader.getFile().getProperty("MOBILEACT_SUBJECT");
		String ezywaySubject = PropertyLoader.getFile().getProperty("EZYWAYACT_SUBJECT");
		String ezypodSubject = PropertyLoader.getFile().getProperty("EZYPODACT_SUBJECT");

		String emailBody = null;
		String emailBody_EzyPOD = null;
		String emailBody_Ezyway = null;
		// String emailBody = MobileUserCreation.sentTempletContent(tempField);

		
		List<Attachment> attachments = new ArrayList<Attachment>();

		Attachment mobiImg = new Attachment("mobiversa_logo1.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("MOBIIMG"), "cid:mobiImg");

		Attachment fbImg = new Attachment("mobi_facebook.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("FBIMG"), "cid:fbImg");

		Attachment twitImg = new Attachment("mobi_twitter.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("TWITIMG"), "cid:twitImg");

		Attachment InstaImg = new Attachment("mobiversa_link.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("INSTAIMG"), "cid:InstaImg");

		Attachment linkedInImg = new Attachment("mobi_linkedin.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("LINKEDINIMG"), "cid:linkedInImg");

		Attachment emailImg = new Attachment("mobi_twitter.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("EMAILIMG"), "cid:emailImg");

		Attachment tollfreeImg = new Attachment("mobiversa_link.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("TOLLFREEIMG"), "cid:tollfreeImg");

		Attachment telephoneImg = new Attachment("mobi_linkedin.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("TELEPHONEIMG"), "cid:telephoneImg");

		attachments.add(mobiImg);
		attachments.add(fbImg);
		attachments.add(twitImg);
		attachments.add(InstaImg);
		attachments.add(linkedInImg);
		attachments.add(emailImg);
		attachments.add(tollfreeImg);
		attachments.add(telephoneImg);*/

		if (regMobileUser.getMid() != null && regMobileUser.getTid() != null && !(regMobileUser.getTid().isEmpty())) {
			TerminalDetails terminalDetails = new TerminalDetails();
			terminalDetails.setDeviceId(regMobileUser.getDeviceId());
			terminalDetails.setDeviceType("EZYWIRE");
			terminalDetails.setMerchantId(regMobileUser.getMid());
			terminalDetails.setActivatedDate(new Date());
			terminalDetails.setActiveStatus("ACTIVE");
			terminalDetails.setTid(regMobileUser.getTid());
			terminalDetails.setRefNo(regMobileUser.getReferenceNo());
			terminalDetails.setContactName(regMobileUser.getContactName());
			terminalDetails.setPreAuth(regMobileUser.getPreAuth());
			terminalDetails.setConnectType("BT");
			terminalDetails.setRenewalDate(new Date());
			terminalDetails.setUmTid(regMobileUser.getUm_tid());

			Date date1 = null;
			try {
				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			terminalDetails.setSuspendedDate(date1);
			terminalDetails.setRemarks(regMobileUser.getRemarks());
			terminalDetails.setActivationCode(activationCode);
			terminalDetails = mobileuserDAO.saveOrUpdateEntity(terminalDetails);
			if (terminalDetails != null) {
				logger.info("ezywire device registered successfully in terminaldetails..");
			}
			InternalTable internalTab = new InternalTable();
			internalTab.setBatchId(1l);
			internalTab.setStan(1l);
			internalTab.setInvoiceNo(1l);
			internalTab.setTid(regMobileUser.getTid());
			internalTab.setMid(regMobileUser.getMid());
			internalTab = mobileuserDAO.saveOrUpdateEntity(internalTab);
			if (internalTab != null) {
				logger.info("ezywire device registered successfully in internalTable");
			}

			int update = mobileuserDAO.updateKManager(regMobileUser.getReferenceNo(), regMobileUser.getTid());

			if (update != 0) {
				logger.info("ezywire device registered successfully in Kmanager");
			}

		}
		
		
		//um

		/*if (regMobileUser.getUm_mid() != null && regMobileUser.getUm_tid() != null && !(regMobileUser.getUm_tid().isEmpty())) {
			logger.info("regMobileUser"+regMobileUser.getTid());
			TerminalDetails terminalDetails = new TerminalDetails();
			terminalDetails.setDeviceId(regMobileUser.getUm_deviceId());
			terminalDetails.setDeviceType("EZYWIRE");
			terminalDetails.setMerchantId(regMobileUser.getUm_mid());
			terminalDetails.setActivatedDate(new Date());
			terminalDetails.setActiveStatus("ACTIVE");
			terminalDetails.setTid(regMobileUser.getUm_tid());
			terminalDetails.setRefNo(regMobileUser.getUm_refNo());
			terminalDetails.setContactName(regMobileUser.getContactName());
			terminalDetails.setPreAuth(regMobileUser.getPreAuth());
			terminalDetails.setConnectType("BT");
			terminalDetails.setRenewalDate(new Date());
//			terminalDetails.setUmTid(regMobileUser.getUm_tid());

			Date date1 = null;
			try {
				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			terminalDetails.setSuspendedDate(date1);
			terminalDetails.setRemarks(regMobileUser.getRemarks());
			terminalDetails.setActivationCode(activationCode);
			terminalDetails = mobileuserDAO.saveOrUpdateEntity(terminalDetails);
			if (terminalDetails != null) {
				logger.info("ezywire device registered successfully for um in terminaldetails..");
			}
		
			InternalTable internalTab = new InternalTable();
			internalTab.setBatchId(1l);
			internalTab.setStan(1l);
			internalTab.setInvoiceNo(1l);
			internalTab.setTid(regMobileUser.getUm_tid());
			internalTab.setMid(regMobileUser.getUm_mid());
			internalTab = mobileuserDAO.saveOrUpdateEntity(internalTab);
			if (internalTab != null) {
				logger.info("ezywire device registered successfully for um in internalTable");
			}

			int update = mobileuserDAO.updateUM_KManager(regMobileUser.getUm_refNo(), regMobileUser.getUm_tid());

			if (update != 0) {
				logger.info("ezywire device registered successfully for um in Kmanager");
			}

		}*/
		
		
		if (regMobileUser.getMotoMid() != null && regMobileUser.getMotoTid() != null
				&& !(regMobileUser.getMotoTid().isEmpty())) {
			TerminalDetails motoTerminalDet = new TerminalDetails();

			motoTerminalDet.setDeviceId(regMobileUser.getMotodeviceId());
			motoTerminalDet.setDeviceType("MOTO");

			motoTerminalDet.setMerchantId(regMobileUser.getMotoMid());
			motoTerminalDet.setActivatedDate(new Date());
			motoTerminalDet.setActiveStatus("ACTIVE");
			motoTerminalDet.setTid(regMobileUser.getMotoTid());
			motoTerminalDet.setRefNo(regMobileUser.getMotorefNo());
			motoTerminalDet.setContactName(regMobileUser.getContactName());
			motoTerminalDet.setPreAuth(regMobileUser.getMotopreAuth());
			motoTerminalDet.setConnectType("BT");

			motoTerminalDet.setRenewalDate(new Date());

			Date date1 = null;
			try {
				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			motoTerminalDet.setSuspendedDate(date1);

			motoTerminalDet.setRemarks(regMobileUser.getRemarks());

			motoTerminalDet.setActivationCode(activationCode);

			motoTerminalDet = mobileuserDAO.saveOrUpdateEntity(motoTerminalDet);
			if (motoTerminalDet != null) {
				logger.info("moto device registered successfully in terminaldetails..");
			}
			InternalTable motointernalTab = new InternalTable();
			motointernalTab.setBatchId(1l);
			motointernalTab.setStan(1l);
			motointernalTab.setInvoiceNo(1l);
			motointernalTab.setTid(regMobileUser.getMotoTid());
			motointernalTab.setMid(regMobileUser.getMotoMid());
			motointernalTab = mobileuserDAO.saveOrUpdateEntity(motointernalTab);
			if (motointernalTab != null) {
				logger.info("moto device registered successfully in internalTable");
			}

			int update = mobileuserDAO.updateKManager(regMobileUser.getMotorefNo(), regMobileUser.getMotoTid());

			if (update != 0) {
				logger.info("moto device registered successfully in Kmanager");
			}
		}
		// EZYWAY
		if (regMobileUser.getEzywayMid() != null && regMobileUser.getEzywayTid() != null
				&& !(regMobileUser.getEzywayTid().isEmpty())) {
			TerminalDetails ezywayTerminalDet = new TerminalDetails();
			ezywayTerminalDet.setDeviceId(regMobileUser.getEzywaydeviceId());
			ezywayTerminalDet.setDeviceType("EZYWAY");
			ezywayTerminalDet.setMerchantId(regMobileUser.getEzywayMid());
			ezywayTerminalDet.setActivatedDate(new Date());
			ezywayTerminalDet.setActiveStatus("ACTIVE");
			ezywayTerminalDet.setTid(regMobileUser.getEzywayTid());
			ezywayTerminalDet.setRefNo(regMobileUser.getEzywayrefNo());
			ezywayTerminalDet.setContactName(regMobileUser.getContactName());
			ezywayTerminalDet.setConnectType("WEB");
			ezywayTerminalDet.setRenewalDate(new Date());

			Date date1 = null;
			try {
				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			ezywayTerminalDet.setSuspendedDate(date1);
			ezywayTerminalDet.setRemarks(regMobileUser.getRemarks());
			ezywayTerminalDet.setActivationCode(activationCode);

			ezywayTerminalDet = mobileuserDAO.saveOrUpdateEntity(ezywayTerminalDet);
			if (ezywayTerminalDet != null) {
				logger.info("ezyway device registered successfully in terminaldetails..");
			}
			InternalTable ezywayinternalTab = new InternalTable();
			ezywayinternalTab.setBatchId(1l);
			ezywayinternalTab.setStan(1l);
			ezywayinternalTab.setInvoiceNo(1l);
			ezywayinternalTab.setTid(regMobileUser.getEzywayTid());
			ezywayinternalTab.setMid(regMobileUser.getEzywayMid());
			ezywayinternalTab = mobileuserDAO.saveOrUpdateEntity(ezywayinternalTab);
			if (ezywayinternalTab != null) {
				logger.info("ezyway device registered successfully in internalTable");
			}

			String mobileUserName = null;
			String motoApiKey = null;
			String merchantName = merchant.getBusinessShortName().replaceAll("\\s", "");
			if (merchantName.length() >= 4) {
				mobileUserName = merchantName.substring(0, 4)
						+ regMobileUser.getEzywayTid().substring(regMobileUser.getEzywayTid().length() - 5);
			}

			String ezyWayPwd = new RandomPassword().generateRandomString();
			
			/// New Change start
			Date date = new Date();
			
			String df = new SimpleDateFormat("yyyyMMdd").format(date);
			
			String dt = new SimpleDateFormat("HHmmss").format(date);
			
			
			
			String key= df+regMobileUser.getEzywayTid()+dt;
			logger.info(" key: "+ key);
			
			//New change end
			String ezywayApikey = new RandomPassword().getMD5Hash("" + key);
			logger.info(" ezywayApikey: "+ ezywayApikey);
			MobileUser mobileUser = new MobileUser();
			mobileUser.setActivateDate(new Date());
			mobileUser.setContact(merchant.getContactPersonPhoneNo());
			mobileUser.setCreatedDate(new Date());
			mobileUser.setEmail(regMobileUser.getEmailId());
			mobileUser.setFirstName(regMobileUser.getContactName());
			mobileUser.setUsername(mobileUserName);
			mobileUser.setEzywayTid(regMobileUser.getEzywayTid());
			mobileUser.setConnectType("WEB");
			mobileUser.setPassword(encoder.encode(ezyWayPwd));
			mobileUser.setStatus(CommonStatus.ACTIVE);
			mobileUser.setMotoApiKey(ezywayApikey);
			mobileUser.setMerchant(merchant);
			mobileUser = mobileuserDAO.saveOrUpdateEntity(mobileUser);

			if (mobileUser != null) {

				logger.info("ezyway mobileuser table inserted successfully");
				/*tempField.setEzywayMid(regMobileUser.getEzywayMid());
				tempField.setEzywayTid(regMobileUser.getEzywayTid());
				tempField.setEzywayApiKey(ezywayApikey);
				tempField.setPassword(ezyWayPwd);
				tempField.setUserName(mobileUserName);*/
				md.setEzywayMid(regMobileUser.getEzywayMid());
				md.setEzywayTid(regMobileUser.getEzywayTid());
				md.setEzywayApiKey(ezywayApikey);
				md.setPassword(ezyWayPwd);
				md.setUserName(mobileUserName);
			}

			int update = mobileuserDAO.updateKManager(regMobileUser.getEzywayrefNo(), regMobileUser.getEzywayTid());

			if (update != 0) {
				logger.info("ezyway device registered successfully in Kmanager");
			}

			// emailBody_Ezyway = EzywayMobileUserCreation.sentTempletContent(tempField);
			/*Attachment ezywayBannerImg = new Attachment("mobi_linkedin.jpg", "image/jpg",
					PropertyLoader.getFile().getProperty("EZYWAYBANNERIMG"), "cid:ezywayBannerImg");
			attachments.add(ezywayBannerImg);
			emailBody_Ezyway = EzyWayCreation.sentTempletContent(tempField);
			PostmarkMessage message1 = new PostmarkMessage(fromAddress, toAddress, fromAddress, ccMail, bccMail,
					ezywaySubject, emailBody_Ezyway, true, "test-email", null, attachments);
			PostmarkClient client1 = new PostmarkClient(apiKey);

			try {
				PostmarkResponse mailSent = client1.sendMessage(message1);
				if (mailSent.getStatus().equals("SUCCESS")) {
					logger.info("Ezyway activation Email Sent Successfully to" + toAddress + " : "
							+ tempField.getActivationCode());
				}

			} catch (PostmarkException pe) {logger.info(pe.getMessage());}*/
			
			
			try {
				send.sendDeviceRegEmail(md);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// EZYREC
		if (regMobileUser.getEzyrecMid() != null && regMobileUser.getEzyrecTid() != null
				&& !regMobileUser.getEzyrecTid().isEmpty()) {
			logger.info("EZYPOD : " + regMobileUser.getEzypod());
			if (regMobileUser.getEzypod().equals("Yes") || regMobileUser.getEzypod() == "Yes") {
				// EZYPOD

				checkEzypod = true;

				TerminalDetails ezypodTerminalDet = new TerminalDetails();

				ezypodTerminalDet.setDeviceId(regMobileUser.getEzyrecdeviceId());
				ezypodTerminalDet.setDeviceType("EZYPOD");
				ezypodTerminalDet.setMerchantId(regMobileUser.getEzyrecMid());
				ezypodTerminalDet.setActivatedDate(new Date());
				ezypodTerminalDet.setActiveStatus("ACTIVE");
				ezypodTerminalDet.setTid(regMobileUser.getEzyrecTid());
				ezypodTerminalDet.setRefNo(regMobileUser.getEzyrecrefNo());
				ezypodTerminalDet.setContactName(regMobileUser.getContactName());

				ezypodTerminalDet.setConnectType("WEB");
				ezypodTerminalDet.setRenewalDate(new Date());
				Date date2 = null;
				try {
					date2 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

				} catch (ParseException e) {
					e.printStackTrace();
				}
				ezypodTerminalDet.setSuspendedDate(date2);
				ezypodTerminalDet.setRemarks(regMobileUser.getRemarks());
				ezypodTerminalDet.setActivationCode(activationCode);

				ezypodTerminalDet = mobileuserDAO.saveOrUpdateEntity(ezypodTerminalDet);
				if (ezypodTerminalDet != null) {
					logger.info("EZYPOD device registered successfully in terminaldetails..");
				}
				InternalTable ezypodinternalTab = new InternalTable();
				ezypodinternalTab.setBatchId(1l);
				ezypodinternalTab.setStan(1l);
				ezypodinternalTab.setInvoiceNo(1l);
				ezypodinternalTab.setTid(regMobileUser.getEzyrecTid());
				ezypodinternalTab.setMid(regMobileUser.getEzyrecMid());
				ezypodinternalTab = mobileuserDAO.saveOrUpdateEntity(ezypodinternalTab);
				if (ezypodinternalTab != null) {
					logger.info("ezypod device registered successfully in internalTable");
				}

				String mobileUserName = null;
				String motoApiKey = null;
				String merchantName = merchant.getBusinessShortName().replaceAll("\\s", "");

				if (merchantName.length() >= 4) {
					mobileUserName = merchantName.substring(0, 4)
							+ regMobileUser.getEzyrecTid().substring(regMobileUser.getEzyrecTid().length() - 5);
				}

				String ezyPODPwd = new RandomPassword().generateRandomString();
				
				
				// New Change start
				Date date = new Date();
				
				String df = new SimpleDateFormat("yyyyMMdd").format(date);
				
				String dt = new SimpleDateFormat("HHmmss").format(date);
				
				
				
				String key= df+regMobileUser.getEzyrecTid()+dt;
				logger.info(" key: "+ key);
				
				//New change end
				String ezyPODApikey = new RandomPassword().getMD5Hash("" + key);

				MobileUser mobileUser = new MobileUser();
				mobileUser.setActivateDate(new Date());
				mobileUser.setContact(merchant.getContactPersonPhoneNo());
				mobileUser.setCreatedDate(new Date());
				mobileUser.setEmail(regMobileUser.getEmailId());
				mobileUser.setFirstName(regMobileUser.getContactName());
				mobileUser.setUsername(mobileUserName);
				mobileUser.setEzyrecTid(regMobileUser.getEzyrecTid());

				mobileUser.setConnectType("WEB");
				mobileUser.setPassword(encoder.encode(ezyPODPwd));
				mobileUser.setStatus(CommonStatus.ACTIVE);

				mobileUser.setMotoApiKey(ezyPODApikey);
				mobileUser.setMerchant(merchant);

				mobileUser = mobileuserDAO.saveOrUpdateEntity(mobileUser);

				if (mobileUser != null) {

					logger.info("ezypod mobileuser table inserted successfully");
					md.setEzyrecMid(regMobileUser.getEzyrecMid());
					md.setEzyrecTid(regMobileUser.getEzyrecTid());
					md.setEzypodApiKey(ezyPODApikey);
					md.setPassword(ezyPODPwd);
					md.setUserName(mobileUserName);
					md.setEzyPOD(true);

				}

				int updateEzyPOD = mobileuserDAO.updateKManager(regMobileUser.getEzyrecrefNo(),
						regMobileUser.getEzyrecTid());

				if (updateEzyPOD != 0) {
					logger.info("ezyrec device registered successfully in Kmanager");
				}

				/*Attachment ezypodBannerImg = new Attachment("mobi_linkedin.jpg", "image/jpg",
						PropertyLoader.getFile().getProperty("EZYPODBANNERIMG"), "cid:ezypodBannerImg");
				attachments.add(ezypodBannerImg);

				emailBody_EzyPOD = EzyPODCreation.sentTempletContent(tempField);
				PostmarkMessage message1 = new PostmarkMessage(fromAddress, toAddress, fromAddress, ccMail, bccMail,
						ezypodSubject, emailBody_EzyPOD, true, "test-email", null, attachments);
				PostmarkClient client1 = new PostmarkClient(apiKey);

				try {
					PostmarkResponse mailSent = client1.sendMessage(message1);
					if (mailSent.getStatus().equals("SUCCESS")) {
						logger.info("EzyPod activation Email Sent Successfully to" + toAddress + " : "
								+ tempField.getActivationCode());

					}

				} catch (PostmarkException pe) {
					logger.info(pe.getMessage());}*/

				// end ezpod
				
				try {
					send.sendDeviceRegEmail(md);
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				TerminalDetails ezyrecTerminalDet = new TerminalDetails();

				ezyrecTerminalDet.setDeviceId(regMobileUser.getEzyrecdeviceId());
				ezyrecTerminalDet.setDeviceType("EZYREC");

				ezyrecTerminalDet.setMerchantId(regMobileUser.getEzyrecMid());
				ezyrecTerminalDet.setActivatedDate(new Date());
				ezyrecTerminalDet.setActiveStatus("ACTIVE");
				ezyrecTerminalDet.setTid(regMobileUser.getEzyrecTid());
				ezyrecTerminalDet.setRefNo(regMobileUser.getEzyrecrefNo());
				ezyrecTerminalDet.setContactName(regMobileUser.getContactName());

				ezyrecTerminalDet.setConnectType("BT");
				ezyrecTerminalDet.setRenewalDate(new Date());
				Date date1 = null;
				try {
					date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());
					logger.info("Expiry Date:" + date1);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				ezyrecTerminalDet.setSuspendedDate(date1);
				logger.info("Expiry Date:" + ezyrecTerminalDet.getSuspendedDate());
				ezyrecTerminalDet.setRemarks(regMobileUser.getRemarks());
				logger.info("Remarks:" + ezyrecTerminalDet.getRemarks());

				ezyrecTerminalDet.setActivationCode(activationCode);

				ezyrecTerminalDet = mobileuserDAO.saveOrUpdateEntity(ezyrecTerminalDet);
				if (ezyrecTerminalDet != null) {
					logger.info("Ezyrec device registered successfully in terminaldetails..");
				}
				InternalTable ezyrecinternalTab = new InternalTable();
				ezyrecinternalTab.setBatchId(1l);
				ezyrecinternalTab.setStan(1l);
				ezyrecinternalTab.setInvoiceNo(1l);
				ezyrecinternalTab.setTid(regMobileUser.getEzyrecTid());
				ezyrecinternalTab.setMid(regMobileUser.getEzyrecMid());
				ezyrecinternalTab = mobileuserDAO.saveOrUpdateEntity(ezyrecinternalTab);
				if (ezyrecinternalTab != null) {
					logger.info("Ezyrec device registered successfully in internalTable");
				}

				int update = mobileuserDAO.updateKManager(regMobileUser.getEzyrecrefNo(), regMobileUser.getEzyrecTid());

				if (update != 0) {
					logger.info("Ezyrec device registered successfully in Kmanager");
				}
			}
		}

		// EZYPASS
		if (regMobileUser.getEzypassMid() != null && regMobileUser.getEzypassTid() != null
				&& !(regMobileUser.getEzypassTid().isEmpty())) {
			TerminalDetails ezypassTerminalDet = new TerminalDetails();

			ezypassTerminalDet.setDeviceId(regMobileUser.getEzypassdeviceId());
			ezypassTerminalDet.setDeviceType("EZYPASS");
			ezypassTerminalDet.setMerchantId(regMobileUser.getEzypassMid());
			ezypassTerminalDet.setRefNo(regMobileUser.getEzypassrefNo());
			ezypassTerminalDet.setTid(regMobileUser.getEzypassTid());
			ezypassTerminalDet.setActivatedDate(new Date());
			ezypassTerminalDet.setActiveStatus("ACTIVE");
			ezypassTerminalDet.setContactName(regMobileUser.getContactName());
			ezypassTerminalDet.setConnectType("BT");
			ezypassTerminalDet.setRenewalDate(new Date());
			Date date1 = null;
			try {
				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			ezypassTerminalDet.setSuspendedDate(date1);
			logger.info("Expiry Date:" + ezypassTerminalDet.getSuspendedDate());
			ezypassTerminalDet.setRemarks(regMobileUser.getRemarks());
			logger.info("Remarks:" + ezypassTerminalDet.getRemarks());

			ezypassTerminalDet.setActivationCode(activationCode);

			ezypassTerminalDet = mobileuserDAO.saveOrUpdateEntity(ezypassTerminalDet);
			if (ezypassTerminalDet != null) {
				logger.info("ezypass device registered successfully in terminaldetails..");
			}

			InternalTable ezypassinternalTab = new InternalTable();

			ezypassinternalTab.setBatchId(1l);
			ezypassinternalTab.setStan(1l);
			ezypassinternalTab.setInvoiceNo(1l);
			ezypassinternalTab.setTid(regMobileUser.getEzypassTid());
			ezypassinternalTab.setMid(regMobileUser.getEzypassMid());
			// mobileuser1 = mobileuserDAO.saveOrUpdateEntity(mobileuser);

			ezypassinternalTab = mobileuserDAO.saveOrUpdateEntity(ezypassinternalTab);
			if (ezypassinternalTab != null) {
				logger.info("ezypass device registered successfully in internalTable");
			}

			int update = mobileuserDAO.updateKManager(regMobileUser.getEzypassrefNo(), regMobileUser.getEzypassTid());

			if (update != 0) {
				logger.info("ezypass device registered successfully in Kmanager");
			}
		}
		if (regMobileUser.getTid() != null || regMobileUser.getMotoTid() != null || regMobileUser.getEzyrecTid() != null
				|| regMobileUser.getEzypassTid() != null) {

			logger.info("checkEzypod: " + checkEzypod);
			if (!checkEzypod) {
				/* emailBody = EmailTemplet13.sentTempletContent(tempField); */
				/*Attachment activationBannerImg = new Attachment("mobi_linkedin.jpg", "image/jpg",
						PropertyLoader.getFile().getProperty("ACTIVATIONBANNERIMG"), "cid:activationBannerImg");
				attachments.add(activationBannerImg);
				Attachment androidImg = new Attachment("mobiLogo.png", "image/jpg",
						PropertyLoader.getFile().getProperty("ANDROIDIMG"), "cid:androidImg");
				attachments.add(androidImg);
				Attachment iosImg = new Attachment("mobiLogo.png", "image/jpg",
						PropertyLoader.getFile().getProperty("IOSIMG"), "cid:iosImg");
				attachments.add(iosImg);
				Attachment mobileImg = new Attachment("mobiLogo.png", "image/jpg",
						PropertyLoader.getFile().getProperty("MOBILEIMG"), "cid:mobileImg");
				attachments.add(mobileImg);

				emailBody = MobileActivationCode.sentTempletContent(tempField);
				PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress, fromAddress, ccMail, bccMail,
						subject, emailBody, true, "test-email", null, attachments);
				PostmarkClient client = new PostmarkClient(apiKey);

				try {
					client.sendMessage(message);
					logger.info(
							"ActivationCode Sent Successfully to" + toAddress + " : " + tempField.getActivationCode());
				} catch (PostmarkException pe) {
					logger.info(pe.getMessage());
				}*/
				
				/*try {
					send.sendDeviceRegEmail(md);
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

			}
			
		}
		return regMobileUser;

	}
	
	
	
	//UMobile device register
	public RegMobileUser registerUMobileUser(final RegMobileUser regMobileUser) {

		boolean checkEzypod = false;
		Merchant merchant = null;
		MID mid = null;
		if (regMobileUser.getUm_ezywayMid() != null) {
			mid = merchantDAO.loadMidtoUpdateAudit(regMobileUser.getUm_ezywayMid());
			merchant = merchantDAO.loadMerchant(mid);
		}
		if (regMobileUser.getUm_motoMid() != null) {
			mid = merchantDAO.loadMidtoUpdateAudit(regMobileUser.getUm_motoMid());
			merchant = merchantDAO.loadMerchant(mid);
		}
		if (regMobileUser.getUm_ezyrecMid() != null && regMobileUser.getUm_ezyrecTid() != null) {
			if (regMobileUser.getEzypod().equals("Yes") || regMobileUser.getEzypod() == "Yes") {
				mid = merchantDAO.loadMidtoUpdateAudit(regMobileUser.getUm_ezyrecMid());
				merchant = merchantDAO.loadMerchant(mid);
			}
		}
		DeviceRandomNumber d = new DeviceRandomNumber();
		String activationCode = null;
		String front = null;
		String back = null;

		boolean ac = false;
		do {
			front = d.generateRandomString();
			back = d.generateRandomString();

			if (regMobileUser.getUm_tid() != null && regMobileUser.getUm_motoTid() != null
					&& regMobileUser.getUm_ezypassTid() != null && regMobileUser.getUm_ezyrecTid() != null) {
				activationCode = front + "MOBIPYT" + back;
				logger.info("activationCode MOBIPYT: " + activationCode);
			} else if (regMobileUser.getUm_motoTid() != null) {
				activationCode = front + "EZYMOTO" + back;
				logger.info("activationCode EZYMOTO: " + activationCode);
			} else if (regMobileUser.getUm_ezypassTid() != null) {
				activationCode = front + "EZYPASS" + back;
				logger.info("activationCode EZYPASS: " + activationCode);
			} else if (regMobileUser.getUm_ezyrecTid() != null) {
				activationCode = front + "EZYREC" + back;
				logger.info("activationCode EZYREC: " + activationCode);
			} else if (regMobileUser.getUm_ezywayTid() != null) {
				activationCode = front + "MOBIPYT" + back;
				logger.info("activationCode EZYWAY: " + activationCode);
			} else {
				activationCode = front + "MOBIPYT" + back;
				logger.info("activationCode MOBI: " + activationCode);
			}

			TerminalDetails acTd = loadTerminalDetailsByActivationCode(activationCode);
			if (acTd != null) {
				ac = true;
			}

		} while (ac);

		regMobileUser.setActivationCode(activationCode);
	
		
		SendSMSMessage send=new SendSMSMessage();
		MsgDto md=new MsgDto();
		md.setFirstName(regMobileUser.getMerchantName());
		//md.setLastName(merchant.getLastName());
		//md.setUserName(merchant.getUsername());
		md.setActivationCode(activationCode);
		if (regMobileUser.getDeviceId() != null && !(regMobileUser.getDeviceId().isEmpty())) {
			md.setDeviceID(regMobileUser.getDeviceId());
			logger.info("ezywire device type:" + md.getDeviceID() + md.getActivationCode());
		}
		md.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
		md.setMerchantEmail(regMobileUser.getEmailId());
		
		logger.info("mail fields:" + md.getDeviceID() + md.getActivationCode()+"::::"+regMobileUser.getEmailId());
		
		/*
		 List<NameValuePair> headers = new ArrayList<NameValuePair>();
		headers.add(new NameValuePair("HEADER", "test"));
		String fromAddress = "info@gomobi.io";
		String apiKey = PropertyLoader.getFile().getProperty("APIKEY"); 
		  String ccMail = PropertyLoader.getFile().getProperty("MOBILEUSER_ADD_CCMAIL");
		String bccMail = PropertyLoader.getFile().getProperty("MOBILEUSER_ADD_BCCMAIL");
		String toAddress = regMobileUser.getEmailId();
		String subject = PropertyLoader.getFile().getProperty("MOBILEACT_SUBJECT");
		String ezywaySubject = PropertyLoader.getFile().getProperty("EZYWAYACT_SUBJECT");
		String ezypodSubject = PropertyLoader.getFile().getProperty("EZYPODACT_SUBJECT");

		String emailBody = null;
		String emailBody_EzyPOD = null;
		String emailBody_Ezyway = null;
		// String emailBody = MobileUserCreation.sentTempletContent(tempField);

		
		List<Attachment> attachments = new ArrayList<Attachment>();

		Attachment mobiImg = new Attachment("mobiversa_logo1.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("MOBIIMG"), "cid:mobiImg");

		Attachment fbImg = new Attachment("mobi_facebook.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("FBIMG"), "cid:fbImg");

		Attachment twitImg = new Attachment("mobi_twitter.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("TWITIMG"), "cid:twitImg");

		Attachment InstaImg = new Attachment("mobiversa_link.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("INSTAIMG"), "cid:InstaImg");

		Attachment linkedInImg = new Attachment("mobi_linkedin.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("LINKEDINIMG"), "cid:linkedInImg");

		Attachment emailImg = new Attachment("mobi_twitter.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("EMAILIMG"), "cid:emailImg");

		Attachment tollfreeImg = new Attachment("mobiversa_link.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("TOLLFREEIMG"), "cid:tollfreeImg");

		Attachment telephoneImg = new Attachment("mobi_linkedin.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("TELEPHONEIMG"), "cid:telephoneImg");

		attachments.add(mobiImg);
		attachments.add(fbImg);
		attachments.add(twitImg);
		attachments.add(InstaImg);
		attachments.add(linkedInImg);
		attachments.add(emailImg);
		attachments.add(tollfreeImg);
		attachments.add(telephoneImg);*/

		if (regMobileUser.getUm_mid() != null && regMobileUser.getUm_tid() != null && !(regMobileUser.getUm_tid().isEmpty())) {

			logger.info("regMobileUser"+regMobileUser.getTid());
			TerminalDetails terminalDetails = new TerminalDetails();
			terminalDetails.setDeviceId(regMobileUser.getUm_deviceId());
			terminalDetails.setDeviceType("EZYWIRE");
			terminalDetails.setMerchantId(regMobileUser.getUm_mid());
			terminalDetails.setActivatedDate(new Date());
			terminalDetails.setActiveStatus("ACTIVE");
			terminalDetails.setTid(regMobileUser.getUm_tid());
			terminalDetails.setRefNo(regMobileUser.getUm_refNo());
			terminalDetails.setContactName(regMobileUser.getContactName());
			terminalDetails.setPreAuth(regMobileUser.getPreAuth());
			terminalDetails.setConnectType("BT");
			terminalDetails.setRenewalDate(new Date());
//			terminalDetails.setUmTid(regMobileUser.getUm_tid());

			Date date1 = null;
			try {
				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			terminalDetails.setSuspendedDate(date1);
			terminalDetails.setRemarks(regMobileUser.getRemarks());
			terminalDetails.setActivationCode(activationCode);
			terminalDetails = mobileuserDAO.saveOrUpdateEntity(terminalDetails);
			if (terminalDetails != null) {
				logger.info("ezywire device registered successfully in terminaldetails..");
			}
			
			/*TerminalDetails umTerminalDetails = new TerminalDetails();
			umTerminalDetails.setDeviceId(regMobileUser.getUm_deviceId());
			umTerminalDetails.setDeviceType("EZYWIRE");
			umTerminalDetails.setMerchantId(regMobileUser.getUm_mid());
			umTerminalDetails.setActivatedDate(new Date());
			umTerminalDetails.setActiveStatus("ACTIVE");
			umTerminalDetails.setTid(regMobileUser.getUm_tid());
			umTerminalDetails.setRefNo(regMobileUser.getUm_refNo());
			umTerminalDetails.setContactName(regMobileUser.getContactName());
			umTerminalDetails.setPreAuth(regMobileUser.getPreAuth());
			umTerminalDetails.setConnectType("BT");
			umTerminalDetails.setRenewalDate(new Date());
			

			
			try {
				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			umTerminalDetails.setSuspendedDate(date1);
			umTerminalDetails.setRemarks(regMobileUser.getRemarks());
			umTerminalDetails.setActivationCode(activationCode);
			umTerminalDetails = mobileuserDAO.saveOrUpdateEntity(umTerminalDetails);
			if (umTerminalDetails != null) {
				logger.info("ezywire device registered successfully for um in terminaldetails..");
			}*/
			
			InternalTable internalTab = new InternalTable();
			internalTab.setBatchId(1l);
			internalTab.setStan(1l);
			internalTab.setInvoiceNo(1l);
			internalTab.setTid(regMobileUser.getUm_tid());
			internalTab.setMid(regMobileUser.getUm_mid());
			internalTab = mobileuserDAO.saveOrUpdateEntity(internalTab);
			if (internalTab != null) {
				logger.info("ezywire device registered successfully in internalTable");
			}

			int update = mobileuserDAO.updateUM_KManager(regMobileUser.getUm_refNo(), regMobileUser.getUm_tid());
		//	int update = mobileuserDAO.updateKManager(regMobileUser.getUm_refNo(), regMobileUser.getUm_tid());

			if (update != 0) {
				logger.info("ezywire device registered successfully for um in Kmanager");
			}
			
			

		/*	if (mobileUser != null) {

				md.setMID(regMobileUser.getUm_mid());
				md.setTID(regMobileUser.getUm_tid());
				md.setEzywayApiKey(umVCCApikey);
				md.setPassword(Pwd);
				md.setUserName(mobileUserName);
			}

			
			
			try {
				send.sendDeviceRegEmail(md);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
			

		}
		
		
		if (regMobileUser.getUm_motoMid() != null && regMobileUser.getUm_motoTid() != null
				&& !(regMobileUser.getUm_motoTid().isEmpty())) {
			
			logger.info("UM MOTO TID :::::::::::::::::.."+regMobileUser.getUm_motoTid());
			TerminalDetails motoTerminalDet = new TerminalDetails();

			motoTerminalDet.setDeviceId(regMobileUser.getUm_motodeviceId());
			motoTerminalDet.setDeviceType("UMMOTO");
			motoTerminalDet.setMerchantId(regMobileUser.getUm_motoMid());
			motoTerminalDet.setActivatedDate(new Date());
			motoTerminalDet.setActiveStatus("ACTIVE");
			motoTerminalDet.setTid(regMobileUser.getUm_motoTid());
			motoTerminalDet.setRefNo(regMobileUser.getUm_motorefNo());
			motoTerminalDet.setContactName(regMobileUser.getContactName());
			motoTerminalDet.setPreAuth(regMobileUser.getMotopreAuth());
			motoTerminalDet.setConnectType("BT");
			motoTerminalDet.setRenewalDate(new Date());

			Date date1 = null;
			try {
				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			motoTerminalDet.setSuspendedDate(date1);

			motoTerminalDet.setRemarks(regMobileUser.getRemarks());

			motoTerminalDet.setActivationCode(activationCode);

			motoTerminalDet = mobileuserDAO.saveOrUpdateEntity(motoTerminalDet);
			if (motoTerminalDet != null) {
				logger.info("moto device registered successfully in terminaldetails..");
			}
			InternalTable motointernalTab = new InternalTable();
			motointernalTab.setBatchId(1l);
			motointernalTab.setStan(1l);
			motointernalTab.setInvoiceNo(1l);
			motointernalTab.setTid(regMobileUser.getUm_motoTid());
			motointernalTab.setMid(regMobileUser.getUm_motoMid());
			motointernalTab = mobileuserDAO.saveOrUpdateEntity(motointernalTab);
			if (motointernalTab != null) {
				logger.info("moto device registered successfully in internalTable");
			}
			
			
			/*int update = mobileuserDAO.updateUM_KManager(regMobileUser.getUm_motorefNo(), regMobileUser.getUm_motoTid());

			if (update != 0) {
				logger.info("moto device registered successfully in Kmanager");
			}*/
			
			UMMidTxnLimit umtxnlt = null; //new UMMidTxnLimit();
			umtxnlt= mobileuserDAO.loadDetByMid(regMobileUser.getUm_motoMid());
			
			if(umtxnlt!=null) {
			//if(umtxnlt.getMid()!=null) {
				
				//if(umtxnlt.getMid().equals(regMobileUser.getUm_ezywayMid()) && (!umtxnlt.getMid().isEmpty())) {
					logger.info("To Update TXN limitt"+umtxnlt.getMid());
					
					
					//umtxnlt.setActivateDate(new Date());
					//umtxnlt.setStatus(CommonStatus.ACTIVE);
					umtxnlt.setMid(regMobileUser.getUm_motoMid());
					//umtxnlt.setCreatedBy("Admin");
					//umtxnlt.setCreatedDate(new Date());
					umtxnlt.setHashKey(regMobileUser.getHashkey1());
					umtxnlt.setDtl(regMobileUser.getDTL1());
					
					
					logger.info("update UMMidTxnLimit save Db" +umtxnlt.getHashKey()+"::"+umtxnlt.getDtl());
					
					
					umtxnlt = umMidTxnLimitDAO.saveOrUpdateEntity(umtxnlt);
					/*int updateDet = mobileuserDAO.updateUMTxnLimit(regMobileUser.getHashkey(), regMobileUser.getDTL(),regMobileUser.getUm_motoMid());
					
					if (updateDet != 0) {
						logger.info("ezyway device details successfully updated in UMMidTxnLimit");
					}
					*/
					
					
				}else {
					umtxnlt = new UMMidTxnLimit();
					logger.info(" To Save TXN limit");
					umtxnlt.setActivateDate(new Date());
					umtxnlt.setStatus(CommonStatus.ACTIVE);
					umtxnlt.setMid(regMobileUser.getUm_motoMid());
					umtxnlt.setCreatedBy("Admin");
					umtxnlt.setCreatedDate(new Date());
					umtxnlt.setHashKey(regMobileUser.getHashkey1());
					umtxnlt.setDtl(regMobileUser.getDTL1());

					logger.info("save UMMidTxnLimit save Db" +umtxnlt.getHashKey()+"::"+umtxnlt.getDtl());
					
					
					umtxnlt = umMidTxnLimitDAO.saveOrUpdateEntity(umtxnlt);
					
					if (umtxnlt != null) {
						logger.info("ezymoto device details successfully added in UMMidTxnLimit");
					}
					
				
		}
			
			
			
			if (regMobileUser.getVcc().equalsIgnoreCase("YES")) {
				logger.info("VCC enabled");
				
				logger.info("Business name:"+merchant.getBusinessName());
				merchant.setEzyMotoVcc("YES");
				merchant = mobileuserDAO.saveOrUpdateEntity(merchant);
				if (merchant != null) {
					logger.info("Merchant updated successfully");
				}
				
				
				String mobileUserName = null;
				String motoApiKey = null;
				String merchantName = merchant.getBusinessShortName().replaceAll("\\s", "");
				if (merchantName.length() >= 4) {
					mobileUserName = merchantName.substring(0, 4)
							+ regMobileUser.getUm_motoTid().substring(regMobileUser.getUm_motoTid().length() - 5);
				}

				
				String Pwd = new RandomPassword().generateRandomString();
				/// New Change start
				Date date = new Date();
				
				String df = new SimpleDateFormat("yyyyMMdd").format(date);
				
				String dt = new SimpleDateFormat("HHmmss").format(date);
				
				
				
				String key= df+regMobileUser.getUm_motoTid()+dt;
				logger.info(" key: "+ key);
				
				//New change end
				String umVCCApikey = new RandomPassword().getMD5Hash("" + key);
				logger.info(" umVCCApikey: "+ umVCCApikey);
				MobileUser mobileUser = new MobileUser();
				mobileUser.setActivateDate(new Date());
				mobileUser.setContact(merchant.getContactPersonPhoneNo());
				mobileUser.setCreatedDate(new Date());
				mobileUser.setEmail(regMobileUser.getEmailId());
				mobileUser.setFirstName(regMobileUser.getContactName());
				mobileUser.setUsername(mobileUserName);
				mobileUser.setMotoTid(regMobileUser.getUm_motoTid());
				mobileUser.setConnectType("WEB");
				mobileUser.setPassword(encoder.encode(Pwd));
				mobileUser.setStatus(CommonStatus.ACTIVE);
				mobileUser.setMotoApiKey(umVCCApikey);
				mobileUser.setMerchant(merchant);
				mobileUser = mobileuserDAO.saveOrUpdateEntity(mobileUser);

				if (mobileUser != null) {

					logger.info("umVCC, mobileuser table inserted successfully");
					/*tempField.setEzywayMid(regMobileUser.getEzywayMid());
					tempField.setEzywayTid(regMobileUser.getEzywayTid());
					tempField.setEzywayApiKey(ezywayApikey);
					tempField.setPassword(ezyWayPwd);
					tempField.setUserName(mobileUserName);*/
					md.setMID(regMobileUser.getUm_motoMid());
					md.setTID(regMobileUser.getUm_motoTid());
					md.setEzywayApiKey(umVCCApikey);
					md.setPassword(Pwd);
					md.setUserName(mobileUserName);
				}

				/*int update = mobileuserDAO.updateKManager(regMobileUser.getEzywayrefNo(), regMobileUser.getEzywayTid());

				if (update != 0) {
					logger.info("ezyway device registered successfully in Kmanager");
				}*/

				// emailBody_Ezyway = EzywayMobileUserCreation.sentTempletContent(tempField);
				/*Attachment ezywayBannerImg = new Attachment("mobi_linkedin.jpg", "image/jpg",
						PropertyLoader.getFile().getProperty("EZYWAYBANNERIMG"), "cid:ezywayBannerImg");
				attachments.add(ezywayBannerImg);
				emailBody_Ezyway = EzyWayCreation.sentTempletContent(tempField);
				PostmarkMessage message1 = new PostmarkMessage(fromAddress, toAddress, fromAddress, ccMail, bccMail,
						ezywaySubject, emailBody_Ezyway, true, "test-email", null, attachments);
				PostmarkClient client1 = new PostmarkClient(apiKey);

				try {
					PostmarkResponse mailSent = client1.sendMessage(message1);
					if (mailSent.getStatus().equals("SUCCESS")) {
						logger.info("Ezyway activation Email Sent Successfully to" + toAddress + " : "
								+ tempField.getActivationCode());
					}

				} catch (PostmarkException pe) {logger.info(pe.getMessage());}*/
				
				
				try {
					send.sendDeviceRegEmail(md);
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
				
			
			
		}
		// 

		if (regMobileUser.getUm_ezywayMid() != null && regMobileUser.getUm_ezywayTid() != null
				&& !(regMobileUser.getUm_ezywayTid().isEmpty())) {
			TerminalDetails ezywayTerminalDet = new TerminalDetails();
			ezywayTerminalDet.setDeviceId(regMobileUser.getUm_ezywaydeviceId());
			ezywayTerminalDet.setDeviceType("UMEZYWAY");
			ezywayTerminalDet.setMerchantId(regMobileUser.getUm_ezywayMid());
			ezywayTerminalDet.setActivatedDate(new Date());
			ezywayTerminalDet.setActiveStatus("ACTIVE");
			ezywayTerminalDet.setTid(regMobileUser.getUm_ezywayTid());
			ezywayTerminalDet.setRefNo(regMobileUser.getUm_ezywayrefNo());
			ezywayTerminalDet.setContactName(regMobileUser.getContactName());
			ezywayTerminalDet.setConnectType("WEB");
			ezywayTerminalDet.setRenewalDate(new Date());

			Date date1 = null;
			try {
				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			ezywayTerminalDet.setSuspendedDate(date1);
			ezywayTerminalDet.setRemarks(regMobileUser.getRemarks());
			ezywayTerminalDet.setActivationCode(activationCode);

			ezywayTerminalDet = mobileuserDAO.saveOrUpdateEntity(ezywayTerminalDet);
			if (ezywayTerminalDet != null) {
				logger.info("ezyway device registered successfully in terminaldetails..");
			}
			InternalTable ezywayinternalTab = new InternalTable();
			ezywayinternalTab.setBatchId(1l);
			ezywayinternalTab.setStan(1l);
			ezywayinternalTab.setInvoiceNo(1l);
			ezywayinternalTab.setTid(regMobileUser.getUm_ezywayTid());
			ezywayinternalTab.setMid(regMobileUser.getUm_ezywayMid());
			ezywayinternalTab = mobileuserDAO.saveOrUpdateEntity(ezywayinternalTab);
			if (ezywayinternalTab != null) {
				logger.info("ezyway device registered successfully in internalTable");
			}

			String mobileUserName = null;
			String motoApiKey = null;
			String merchantName = merchant.getBusinessShortName().replaceAll("\\s", "");
			if (merchantName.length() >= 4) {
				mobileUserName = merchantName.substring(0, 4)
						+ regMobileUser.getUm_ezywayTid().substring(regMobileUser.getUm_ezywayTid().length() - 5);
			}

			String ezyWayPwd = new RandomPassword().generateRandomString();
			String ezywayApikey = new RandomPassword().getMD5Hash("" + back);

			MobileUser mobileUser = new MobileUser();
			mobileUser.setActivateDate(new Date());
			mobileUser.setContact(merchant.getContactPersonPhoneNo());
			mobileUser.setCreatedDate(new Date());
			mobileUser.setEmail(regMobileUser.getEmailId());
			mobileUser.setFirstName(regMobileUser.getContactName());
			mobileUser.setUsername(mobileUserName);
			mobileUser.setEzywayTid(regMobileUser.getUm_ezywayTid());
			mobileUser.setConnectType("WEB");
			mobileUser.setPassword(encoder.encode(ezyWayPwd));
			mobileUser.setStatus(CommonStatus.ACTIVE);
			mobileUser.setMotoApiKey(ezywayApikey);
			mobileUser.setMerchant(merchant);
			mobileUser = mobileuserDAO.saveOrUpdateEntity(mobileUser);

			if (mobileUser != null) {

				logger.info("ezyway mobileuser table inserted successfully");
				/*tempField.setEzywayMid(regMobileUser.getEzywayMid());
				tempField.setEzywayTid(regMobileUser.getEzywayTid());
				tempField.setEzywayApiKey(ezywayApikey);
				tempField.setPassword(ezyWayPwd);
				tempField.setUserName(mobileUserName);*/
				md.setEzywayMid(regMobileUser.getUm_ezywayMid());
				md.setEzywayTid(regMobileUser.getUm_ezywayTid());
				md.setEzywayApiKey(ezywayApikey);
				md.setPassword(ezyWayPwd);
				md.setUserName(mobileUserName);
			}

			/*int update = mobileuserDAO.updateUM_KManager(regMobileUser.getUm_ezywayrefNo(), regMobileUser.getUm_ezywayTid());

			if (update != 0) {
				logger.info("ezyway device registered successfully in Kmanager");
			}*/
			
			UMMidTxnLimit umtxnlt = null; //new UMMidTxnLimit();
			
			umtxnlt= mobileuserDAO.loadDetByMid(regMobileUser.getUm_ezywayMid());
			
			if(umtxnlt!=null) {
			//if(umtxnlt.getMid()!=null) {
				
				//if(umtxnlt.getMid().equals(regMobileUser.getUm_ezywayMid()) && (!umtxnlt.getMid().isEmpty())) {
					logger.info("To update TXN Limit :"+umtxnlt.getMid());
					
					
					//umtxnlt.setActivateDate(new Date());
					//umtxnlt.setStatus(CommonStatus.ACTIVE);
					umtxnlt.setMid(regMobileUser.getUm_ezywayMid());
					//umtxnlt.setCreatedBy("Admin");
					//umtxnlt.setCreatedDate(new Date());
					umtxnlt.setHashKey(regMobileUser.getHashkey());
					umtxnlt.setDtl(regMobileUser.getDTL());
					
					umtxnlt.setRedirectUrl(regMobileUser.getDomainUrl());
					
					logger.info("update UMMidTxnLimit save Db" +umtxnlt.getHashKey()+"::"+umtxnlt.getDtl()+"::"+umtxnlt.getRedirectUrl());
					
					
					umtxnlt = umMidTxnLimitDAO.saveOrUpdateEntity(umtxnlt);
					/*int updateDet = mobileuserDAO.updateUMTxnLimit(regMobileUser.getHashkey(), regMobileUser.getDTL(),regMobileUser.getUm_ezywayMid());
					
					if (updateDet != 0) {
						logger.info("ezyway device details successfully updated in UMMidTxnLimit");
					}*/
					
					
					
				}else {
					umtxnlt = new UMMidTxnLimit();
					logger.info(" To Save TXN Limit");
					umtxnlt.setActivateDate(new Date());
					umtxnlt.setStatus(CommonStatus.ACTIVE);
					umtxnlt.setMid(regMobileUser.getUm_ezywayMid());
					umtxnlt.setCreatedBy("Admin");
					umtxnlt.setCreatedDate(new Date());
					umtxnlt.setHashKey(regMobileUser.getHashkey());
					umtxnlt.setDtl(regMobileUser.getDTL());
					
					umtxnlt.setRedirectUrl(regMobileUser.getDomainUrl());
					
					logger.info("save UMMidTxnLimit save Db" +umtxnlt.getHashKey()+"::"+umtxnlt.getDtl()+"::"+umtxnlt.getRedirectUrl());
					
					
					umtxnlt = umMidTxnLimitDAO.saveOrUpdateEntity(umtxnlt);
					
					if (umtxnlt != null) {
						logger.info("ezyway device details successfully added in UMMidTxnLimit");
					}
					
				}
				
			//}
			
			/*logger.info("UMMidTxnLimit save Db");
			umtxnlt.setActivateDate(new Date());
			umtxnlt.setStatus(CommonStatus.ACTIVE);
			umtxnlt.setMid(regMobileUser.getUm_ezywayMid());
			umtxnlt.setCreatedBy("Admin");
			umtxnlt.setCreatedDate(new Date());
			umtxnlt.setHashKey(regMobileUser.getHashkey());
			umtxnlt.setDtl(regMobileUser.getDTL());
			
			logger.info("UMMidTxnLimit save Db" +umtxnlt.getHashKey()+"::"+umtxnlt.getDtl());
			
			
			umtxnlt = umMidTxnLimitDAO.saveOrUpdateEntity(umtxnlt);
			
			if (umtxnlt != null) {
				logger.info("ezyway device details successfully added in UMMidTxnLimit");
			}*/
			
			
			

			// emailBody_Ezyway = EzywayMobileUserCreation.sentTempletContent(tempField);
			/*Attachment ezywayBannerImg = new Attachment("mobi_linkedin.jpg", "image/jpg",
					PropertyLoader.getFile().getProperty("EZYWAYBANNERIMG"), "cid:ezywayBannerImg");
			attachments.add(ezywayBannerImg);
			emailBody_Ezyway = EzyWayCreation.sentTempletContent(tempField);
			PostmarkMessage message1 = new PostmarkMessage(fromAddress, toAddress, fromAddress, ccMail, bccMail,
					ezywaySubject, emailBody_Ezyway, true, "test-email", null, attachments);
			PostmarkClient client1 = new PostmarkClient(apiKey);

			try {
				PostmarkResponse mailSent = client1.sendMessage(message1);
				if (mailSent.getStatus().equals("SUCCESS")) {
					logger.info("Ezyway activation Email Sent Successfully to" + toAddress + " : "
							+ tempField.getActivationCode());
				}

			} catch (PostmarkException pe) {logger.info(pe.getMessage());}*/
			
			
			try {
				send.sendDeviceRegEmail(md);
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// EZYREC
		if (regMobileUser.getUm_ezyrecMid() != null && regMobileUser.getUm_ezyrecTid() != null
				&& !regMobileUser.getUm_ezyrecTid().isEmpty()) {
			logger.info("EZYPOD : " + regMobileUser.getEzypod());
			if (regMobileUser.getEzypod().equals("Yes") || regMobileUser.getEzypod() == "Yes") {
				// EZYPOD

				checkEzypod = true;

				TerminalDetails ezypodTerminalDet = new TerminalDetails();

				ezypodTerminalDet.setDeviceId(regMobileUser.getUm_ezyrecdeviceId());
				ezypodTerminalDet.setDeviceType("EZYPOD");
				ezypodTerminalDet.setMerchantId(regMobileUser.getUm_ezyrecMid());
				ezypodTerminalDet.setActivatedDate(new Date());
				ezypodTerminalDet.setActiveStatus("ACTIVE");
				ezypodTerminalDet.setTid(regMobileUser.getUm_ezyrecTid());
				ezypodTerminalDet.setRefNo(regMobileUser.getUm_ezyrecrefNo());
				ezypodTerminalDet.setContactName(regMobileUser.getContactName());

				ezypodTerminalDet.setConnectType("WEB");
				ezypodTerminalDet.setRenewalDate(new Date());
				Date date2 = null;
				try {
					date2 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

				} catch (ParseException e) {
					e.printStackTrace();
				}
				ezypodTerminalDet.setSuspendedDate(date2);
				ezypodTerminalDet.setRemarks(regMobileUser.getRemarks());
				ezypodTerminalDet.setActivationCode(activationCode);

				ezypodTerminalDet = mobileuserDAO.saveOrUpdateEntity(ezypodTerminalDet);
				if (ezypodTerminalDet != null) {
					logger.info("EZYPOD device registered successfully in terminaldetails..");
				}
				InternalTable ezypodinternalTab = new InternalTable();
				ezypodinternalTab.setBatchId(1l);
				ezypodinternalTab.setStan(1l);
				ezypodinternalTab.setInvoiceNo(1l);
				ezypodinternalTab.setTid(regMobileUser.getUm_ezyrecTid());
				ezypodinternalTab.setMid(regMobileUser.getUm_ezyrecMid());
				ezypodinternalTab = mobileuserDAO.saveOrUpdateEntity(ezypodinternalTab);
				if (ezypodinternalTab != null) {
					logger.info("ezypod device registered successfully in internalTable");
				}

				String mobileUserName = null;
				String motoApiKey = null;
				String merchantName = merchant.getBusinessShortName().replaceAll("\\s", "");

				if (merchantName.length() >= 4) {
					mobileUserName = merchantName.substring(0, 4)
							+ regMobileUser.getUm_ezyrecTid().substring(regMobileUser.getUm_ezyrecTid().length() - 5);
				}

				String ezyPODPwd = new RandomPassword().generateRandomString();
				String ezyPODApikey = new RandomPassword().getMD5Hash("" + back);

				MobileUser mobileUser = new MobileUser();
				mobileUser.setActivateDate(new Date());
				mobileUser.setContact(merchant.getContactPersonPhoneNo());
				mobileUser.setCreatedDate(new Date());
				mobileUser.setEmail(regMobileUser.getEmailId());
				mobileUser.setFirstName(regMobileUser.getContactName());
				mobileUser.setUsername(mobileUserName);
				mobileUser.setEzyrecTid(regMobileUser.getUm_ezyrecTid());

				mobileUser.setConnectType("WEB");
				mobileUser.setPassword(encoder.encode(ezyPODPwd));
				mobileUser.setStatus(CommonStatus.ACTIVE);

				mobileUser.setMotoApiKey(ezyPODApikey);
				mobileUser.setMerchant(merchant);

				mobileUser = mobileuserDAO.saveOrUpdateEntity(mobileUser);

				if (mobileUser != null) {

					logger.info("ezypod mobileuser table inserted successfully");
					md.setEzyrecMid(regMobileUser.getUm_ezyrecMid());
					md.setEzyrecTid(regMobileUser.getUm_ezyrecTid());
					md.setEzypodApiKey(ezyPODApikey);
					md.setPassword(ezyPODPwd);
					md.setUserName(mobileUserName);
					md.setEzyPOD(true);

				}

				int updateEzyPOD = mobileuserDAO.updateUM_KManager(regMobileUser.getUm_ezyrecrefNo(),
						regMobileUser.getUm_ezyrecTid());

				if (updateEzyPOD != 0) {
					logger.info("ezyrec device registered successfully in Kmanager");
				}

				/*Attachment ezypodBannerImg = new Attachment("mobi_linkedin.jpg", "image/jpg",
						PropertyLoader.getFile().getProperty("EZYPODBANNERIMG"), "cid:ezypodBannerImg");
				attachments.add(ezypodBannerImg);

				emailBody_EzyPOD = EzyPODCreation.sentTempletContent(tempField);
				PostmarkMessage message1 = new PostmarkMessage(fromAddress, toAddress, fromAddress, ccMail, bccMail,
						ezypodSubject, emailBody_EzyPOD, true, "test-email", null, attachments);
				PostmarkClient client1 = new PostmarkClient(apiKey);

				try {
					PostmarkResponse mailSent = client1.sendMessage(message1);
					if (mailSent.getStatus().equals("SUCCESS")) {
						logger.info("EzyPod activation Email Sent Successfully to" + toAddress + " : "
								+ tempField.getActivationCode());

					}

				} catch (PostmarkException pe) {
					logger.info(pe.getMessage());}*/

				// end ezpod
				
				/*try {
					send.sendDeviceRegEmail(md);
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

			} else {
				TerminalDetails ezyrecTerminalDet = new TerminalDetails();

				ezyrecTerminalDet.setDeviceId(regMobileUser.getUm_ezyrecdeviceId());
				ezyrecTerminalDet.setDeviceType("UMEZYREC");

				ezyrecTerminalDet.setMerchantId(regMobileUser.getUm_ezyrecMid());
				ezyrecTerminalDet.setActivatedDate(new Date());
				ezyrecTerminalDet.setActiveStatus("ACTIVE");
				ezyrecTerminalDet.setTid(regMobileUser.getUm_ezyrecTid());
				ezyrecTerminalDet.setRefNo(regMobileUser.getUm_ezyrecrefNo());
				ezyrecTerminalDet.setContactName(regMobileUser.getContactName());

				ezyrecTerminalDet.setConnectType("BT");
				ezyrecTerminalDet.setRenewalDate(new Date());
				Date date1 = null;
				try {
					date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());
					logger.info("Expiry Date:" + date1);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				ezyrecTerminalDet.setSuspendedDate(date1);
				logger.info("Expiry Date:" + ezyrecTerminalDet.getSuspendedDate());
				ezyrecTerminalDet.setRemarks(regMobileUser.getRemarks());
				logger.info("Remarks:" + ezyrecTerminalDet.getRemarks());

				ezyrecTerminalDet.setActivationCode(activationCode);

				ezyrecTerminalDet = mobileuserDAO.saveOrUpdateEntity(ezyrecTerminalDet);
				if (ezyrecTerminalDet != null) {
					logger.info("Ezyrec device registered successfully in terminaldetails..");
				}
				InternalTable ezyrecinternalTab = new InternalTable();
				ezyrecinternalTab.setBatchId(1l);
				ezyrecinternalTab.setStan(1l);
				ezyrecinternalTab.setInvoiceNo(1l);
				ezyrecinternalTab.setTid(regMobileUser.getUm_ezyrecTid());
				ezyrecinternalTab.setMid(regMobileUser.getUm_ezyrecMid());
				ezyrecinternalTab = mobileuserDAO.saveOrUpdateEntity(ezyrecinternalTab);
				if (ezyrecinternalTab != null) {
					logger.info("Ezyrec device registered successfully in internalTable");
				}

				int update = mobileuserDAO.updateUM_KManager(regMobileUser.getUm_ezyrecrefNo(), regMobileUser.getUm_ezyrecTid());

				if (update != 0) {
					logger.info("Ezyrec device registered successfully in Kmanager");
				}
				
				
				UMMidTxnLimit umtxnlt = null; //new UMMidTxnLimit();
				umtxnlt= mobileuserDAO.loadDetByMid(regMobileUser.getUm_ezyrecMid());
				
				if(umtxnlt!=null) {
				//if(umtxnlt.getMid()!=null) {
					
					//if(umtxnlt.getMid().equals(regMobileUser.getUm_ezywayMid()) && (!umtxnlt.getMid().isEmpty())) {
						logger.info("To Update TXN limitt"+umtxnlt.getMid());
						
						
						//umtxnlt.setActivateDate(new Date());
						//umtxnlt.setStatus(CommonStatus.ACTIVE);
						umtxnlt.setMid(regMobileUser.getUm_ezyrecMid());
						//umtxnlt.setCreatedBy("Admin");
						//umtxnlt.setCreatedDate(new Date());
						umtxnlt.setHashKey(regMobileUser.getRecHashkey());
						umtxnlt.setDtl(regMobileUser.getRecDTL());
						
						
						logger.info("update UMMidTxnLimit save Db" +umtxnlt.getHashKey()+"::"+umtxnlt.getDtl());
						
						
						umtxnlt = umMidTxnLimitDAO.saveOrUpdateEntity(umtxnlt);
						/*int updateDet = mobileuserDAO.updateUMTxnLimit(regMobileUser.getHashkey(), regMobileUser.getDTL(),regMobileUser.getUm_motoMid());
						
						if (updateDet != 0) {
							logger.info("ezyway device details successfully updated in UMMidTxnLimit");
						}
						*/
						
						
					}else {
						umtxnlt = new UMMidTxnLimit();
						logger.info(" To Save TXN limit");
						umtxnlt.setActivateDate(new Date());
						umtxnlt.setStatus(CommonStatus.ACTIVE);
						umtxnlt.setMid(regMobileUser.getUm_ezyrecMid());
						umtxnlt.setCreatedBy("Admin");
						umtxnlt.setCreatedDate(new Date());
						umtxnlt.setHashKey(regMobileUser.getRecHashkey());
						umtxnlt.setDtl(regMobileUser.getRecDTL());

						logger.info("save UMMidTxnLimit save Db" +umtxnlt.getHashKey()+"::"+umtxnlt.getDtl());
						
						
						umtxnlt = umMidTxnLimitDAO.saveOrUpdateEntity(umtxnlt);
						
						if (umtxnlt != null) {
							logger.info("ezrec device details successfully added in UMMidTxnLimit");
						}
				
			}
		}
	}

		// EZYPASS
		if (regMobileUser.getUm_ezypassMid() != null && regMobileUser.getUm_ezypassTid() != null
				&& !(regMobileUser.getUm_ezypassTid().isEmpty())) {
			TerminalDetails ezypassTerminalDet = new TerminalDetails();

			ezypassTerminalDet.setDeviceId(regMobileUser.getUm_ezypassdeviceId());
			ezypassTerminalDet.setDeviceType("UMEZYPASS");
			ezypassTerminalDet.setMerchantId(regMobileUser.getUm_ezypassMid());
			ezypassTerminalDet.setRefNo(regMobileUser.getUm_ezypassrefNo());
			ezypassTerminalDet.setTid(regMobileUser.getUm_ezypassTid());
			ezypassTerminalDet.setActivatedDate(new Date());
			ezypassTerminalDet.setActiveStatus("ACTIVE");
			ezypassTerminalDet.setContactName(regMobileUser.getContactName());
			ezypassTerminalDet.setConnectType("BT");
			ezypassTerminalDet.setRenewalDate(new Date());
			Date date1 = null;
			try {
				date1 = new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());

			} catch (ParseException e) {
				e.printStackTrace();
			}
			ezypassTerminalDet.setSuspendedDate(date1);
			ezypassTerminalDet.setRemarks(regMobileUser.getRemarks());
			ezypassTerminalDet.setActivationCode(activationCode);

			ezypassTerminalDet = mobileuserDAO.saveOrUpdateEntity(ezypassTerminalDet);
			if (ezypassTerminalDet != null) {
				logger.info("ezypass device registered successfully in terminaldetails..");
			}

			InternalTable ezypassinternalTab = new InternalTable();

			ezypassinternalTab.setBatchId(1l);
			ezypassinternalTab.setStan(1l);
			ezypassinternalTab.setInvoiceNo(1l);
			ezypassinternalTab.setTid(regMobileUser.getUm_ezypassTid());
			ezypassinternalTab.setMid(regMobileUser.getUm_ezypassMid());
			// mobileuser1 = mobileuserDAO.saveOrUpdateEntity(mobileuser);

			ezypassinternalTab = mobileuserDAO.saveOrUpdateEntity(ezypassinternalTab);
			if (ezypassinternalTab != null) {
				logger.info("ezypass device registered successfully in internalTable");
			}

			int update = mobileuserDAO.updateUM_KManager(regMobileUser.getUm_ezypassrefNo(), regMobileUser.getUm_ezypassTid());

			if (update != 0) {
				logger.info("ezypass device registered successfully in Kmanager");
			}
		}
		if (regMobileUser.getUm_tid() != null || regMobileUser.getUm_motoTid() != null || regMobileUser.getUm_ezypassTid() != null
				|| regMobileUser.getUm_ezyrecTid() != null) {

			logger.info("checkEzypod: " + checkEzypod);
			if (!checkEzypod) {
				/* emailBody = EmailTemplet13.sentTempletContent(tempField); */
				/*Attachment activationBannerImg = new Attachment("mobi_linkedin.jpg", "image/jpg",
						PropertyLoader.getFile().getProperty("ACTIVATIONBANNERIMG"), "cid:activationBannerImg");
				attachments.add(activationBannerImg);
				Attachment androidImg = new Attachment("mobiLogo.png", "image/jpg",
						PropertyLoader.getFile().getProperty("ANDROIDIMG"), "cid:androidImg");
				attachments.add(androidImg);
				Attachment iosImg = new Attachment("mobiLogo.png", "image/jpg",
						PropertyLoader.getFile().getProperty("IOSIMG"), "cid:iosImg");
				attachments.add(iosImg);
				Attachment mobileImg = new Attachment("mobiLogo.png", "image/jpg",
						PropertyLoader.getFile().getProperty("MOBILEIMG"), "cid:mobileImg");
				attachments.add(mobileImg);

				emailBody = MobileActivationCode.sentTempletContent(tempField);
				PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress, fromAddress, ccMail, bccMail,
						subject, emailBody, true, "test-email", null, attachments);
				PostmarkClient client = new PostmarkClient(apiKey);

				try {
					client.sendMessage(message);
					logger.info(
							"ActivationCode Sent Successfully to" + toAddress + " : " + tempField.getActivationCode());
				} catch (PostmarkException pe) {
					logger.info(pe.getMessage());
				}*/
				
				/*try {
					send.sendDeviceRegEmail(md);
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/

			}
			
		}
		return regMobileUser;

	}
	
	
	
	
	
	@SuppressWarnings("unused")
	// @javax.transaction.Transactional
	public RegMobileUser updateMobileUser(final RegMobileUser regMobileUser) {

		
		String searchTid=null;
		if(regMobileUser.getUpdateType().equals("ezywire"))
		{
			searchTid=regMobileUser.getTid();
			logger.info("ezywire tid to serach for activationcode: "+searchTid);
		}
		else if(regMobileUser.getUpdateType().equals("moto"))
		{
			searchTid=regMobileUser.getMotoTid();
			logger.info("moto tid to serach for activationcode: "+searchTid);
		}
		else if(regMobileUser.getUpdateType().equals("ezypass"))
		{
			searchTid=regMobileUser.getEzypassTid();
			logger.info("ezypasstid to serach for activationcode: "+searchTid);
		}
		else if(regMobileUser.getUpdateType().equals("ezyrec"))
		{
			searchTid=regMobileUser.getEzyrecTid();
			logger.info("ezyrectid to serach for activationcode: "+searchTid);
		}
			
		TerminalDetails existTid = loadTerminalDetailsByTid(searchTid);
		
		
		DeviceRandomNumber d = new DeviceRandomNumber();
		
		
		String activationCode = null;
		boolean ac=false;
		
		if (existTid != null) {
			logger.info("activation code of"+ regMobileUser.getUpdateType()+ " tid: "+existTid.getTid()+ " : "+existTid.getActivationCode());
			
			activationCode = existTid.getActivationCode();
			logger.info("db activationCode: " + activationCode);
		} else {
			do {
				String front = d.generateRandomString();

				String back = d.generateRandomString();
				if (regMobileUser.getMid() != null && regMobileUser.getMotoMid() != null
						&& regMobileUser.getEzypassMid() != null && regMobileUser.getEzyrecMid() != null && 
						regMobileUser.getUm_mid()!=null) {
					activationCode = front + "MOBIPYT" + back;
					logger.info("Random Generated ac MOBIPYT: " + activationCode);
				} else if (regMobileUser.getMid() == null && regMobileUser.getMotoMid() != null
						&& regMobileUser.getEzypassMid() == null && regMobileUser.getEzyrecMid() == null) {
					activationCode = front + "EZYMOTO" + back;
					logger.info("activationCode EZYMOTO: " + activationCode);
				} else if (regMobileUser.getMid() == null && regMobileUser.getMotoMid() == null
						&& regMobileUser.getEzyrecMid() == null && regMobileUser.getEzypassMid() != null) {
					activationCode = front + "EZYPASS" + back;
					logger.info("activationCode EZYPASS: " + activationCode);
				} else if (regMobileUser.getMid() == null && regMobileUser.getMotoMid() == null
						&& regMobileUser.getEzyrecMid() != null && regMobileUser.getEzypassMid() == null) {
					activationCode = front + "EZYREC" + back;
					logger.info("activationCode EZYREC: " + activationCode);
				} else if (regMobileUser.getUm_mid() == null ) {
					activationCode = front + "UMOBILE" + back;
					logger.info("activationCode UMOBILE: " + activationCode);
				} 
				else {
					activationCode = front + "MOBIPYT" + back;
					logger.info("activationCode MOBIPYT: " + activationCode);
				}

				logger.info("SYSTEM generated activationCode: "+activationCode);
				TerminalDetails acTd = loadTerminalDetailsByActivationCode(activationCode);
				if (acTd != null) {
					ac = true;
				}
				logger.info("ac: "+ac);

			} while (ac);

		}
		regMobileUser.setActivationCode(activationCode);
		if(regMobileUser.getTid()!=null && regMobileUser.getUm_tid()== null && !(regMobileUser.getUpdateType().equals("ezywire")))
		{
			TerminalDetails terminalDetails = new TerminalDetails();
			logger.info("mid: "+regMobileUser.getMid());
			logger.info("without umTid");
			logger.info("update type: to update"+regMobileUser.getUpdateType());
			

			terminalDetails.setMerchantId(regMobileUser.getMid());
			terminalDetails.setActivatedDate(new Date());
			terminalDetails.setDeviceId(regMobileUser.getDeviceId());
			terminalDetails.setDeviceType("EZYWIRE");
			terminalDetails.setActiveStatus("ACTIVE");
			terminalDetails.setTid(regMobileUser.getTid());
			terminalDetails.setRefNo(regMobileUser.getReferenceNo());
			terminalDetails.setContactName(regMobileUser.getContactName());
			terminalDetails.setPreAuth(regMobileUser.getPreAuth());
			terminalDetails.setConnectType("BT");
			terminalDetails.setRenewalDate(new Date());
			
			
			
			Date date1 = null;
			 try {
				 date1=new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());
				 logger.info("Expiry Date:" + date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			 terminalDetails.setSuspendedDate(date1);
			logger.info("Expiry Date:" + terminalDetails.getSuspendedDate());
			terminalDetails.setRemarks(regMobileUser.getRemarks());
			logger.info("Remarks:" + terminalDetails.getRemarks());
			terminalDetails.setActivationCode(activationCode);
			terminalDetails=mobileuserDAO.saveOrUpdateEntity(terminalDetails);
			if(terminalDetails != null){
				logger.info("ezywire device registered successfully in terminaldetails..");
			}
			InternalTable internalTab = new InternalTable();
			internalTab.setBatchId(1l);
			internalTab.setStan(1l);
			internalTab.setInvoiceNo(1l);
			internalTab.setTid(regMobileUser.getTid());
			internalTab.setMid(regMobileUser.getMid());
			
			internalTab=mobileuserDAO.saveOrUpdateEntity(internalTab);
			if(internalTab != null)
			{
				logger.info("ezywire device registered successfully in internalTable by "+regMobileUser.getUpdateType());
			}
			
			//updateKManager
			int update = mobileuserDAO.updateKManager(regMobileUser.getReferenceNo(), regMobileUser.getTid());
			
			if(update!=0){
				logger.info("ezywire device registered successfully in Kmanager by "+regMobileUser.getUpdateType());
			}
			
			int updateMobileUser=0;
			
			
			if(regMobileUser.getUpdateType().equals("moto"))
			{
				updateMobileUser = mobileuserDAO.updateEzywireMobileuser(
						regMobileUser.getTid(), regMobileUser.getMotoTid(),
						regMobileUser.getUpdateType(),regMobileUser.getPreAuth());

				
			}
			else if(regMobileUser.getUpdateType().equals("ezyrec"))
			{
				updateMobileUser = mobileuserDAO.updateEzywireMobileuser(
						regMobileUser.getTid(), regMobileUser.getEzyrecTid(),
						regMobileUser.getUpdateType(),regMobileUser.getPreAuth());

				
			}else if(regMobileUser.getUpdateType().equals("ezypass"))
			{
				updateMobileUser = mobileuserDAO.updateEzywireMobileuser(
						regMobileUser.getTid(), regMobileUser.getEzypassTid(),
						regMobileUser.getUpdateType(),regMobileUser.getPreAuth());

				
			}
			if (updateMobileUser != 0) {
				logger.info("ezywire device registered successfully in MobileUser by "+regMobileUser.getUpdateType()+" tid");
			}
		}
		
		if (regMobileUser.getMotoTid() != null && !(regMobileUser.getUpdateType().equals("moto"))) 
		{
			logger.info("update type: to update"+regMobileUser.getUpdateType());
			TerminalDetails motoTerminalDet = new TerminalDetails();
			
			motoTerminalDet.setDeviceId(regMobileUser.getMotodeviceId());
			motoTerminalDet.setDeviceType("MOTO");
			motoTerminalDet.setMerchantId(regMobileUser.getMotoMid());
			motoTerminalDet.setActivatedDate(new Date());
			motoTerminalDet.setActiveStatus("ACTIVE");
			motoTerminalDet.setTid(regMobileUser.getMotoTid());
			motoTerminalDet.setRefNo(regMobileUser.getMotorefNo());
			motoTerminalDet.setContactName(regMobileUser.getContactName());
			motoTerminalDet.setPreAuth(regMobileUser.getMotopreAuth());
			motoTerminalDet.setConnectType("BT");
			motoTerminalDet.setRenewalDate(new Date());
			logger.info("Renewal Date:" + motoTerminalDet.getRenewalDate());
			Date date1 = null;
			 try {
				 date1=new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());
				 logger.info("Expiry Date:" + date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			 motoTerminalDet.setSuspendedDate(date1);
			
			motoTerminalDet.setRemarks(regMobileUser.getRemarks());
			motoTerminalDet.setActivationCode(activationCode);
			
			motoTerminalDet=mobileuserDAO.saveOrUpdateEntity(motoTerminalDet);
			
			if(motoTerminalDet != null){
				logger.info("moto device registered successfully in terminaldetails..");
			}
			InternalTable motointernalTab = new InternalTable();
			motointernalTab.setBatchId(1l);
			motointernalTab.setStan(1l);
			motointernalTab.setInvoiceNo(1l);
			motointernalTab.setTid(regMobileUser.getMotoTid());
			motointernalTab.setMid(regMobileUser.getMotoMid());
			// mobileuser1 = mobileuserDAO.saveOrUpdateEntity(mobileuser);

			motointernalTab = mobileuserDAO.saveOrUpdateEntity(motointernalTab);
			if (motointernalTab != null) {
				logger.info("moto device registered successfully in internalTable by "+regMobileUser.getUpdateType());
			}
			
			
			int update = mobileuserDAO.updateKManager(regMobileUser.getMotorefNo(), regMobileUser.getMotoTid());
			
			if(update!=0){
				logger.info("moto device registered successfully in Kmanager by "+regMobileUser.getUpdateType());
			}
			
			
			//updateMobileUSer
			int updateMobileUser=0;
			if(regMobileUser.getUpdateType().equals("ezywire"))
			{
				updateMobileUser = mobileuserDAO.updateMotoMobileuser(regMobileUser.getMotoTid(),
						regMobileUser.getTid(), regMobileUser.getUpdateType());

			}
			else if(regMobileUser.getUpdateType().equals("ezyrec"))
			{
				updateMobileUser = mobileuserDAO.updateMotoMobileuser(regMobileUser.getMotoTid(),
						regMobileUser.getEzyrecTid(), regMobileUser.getUpdateType());

			}
			else if(regMobileUser.getUpdateType().equals("ezypass"))
			{
				updateMobileUser = mobileuserDAO.updateMotoMobileuser(regMobileUser.getMotoTid(),
						regMobileUser.getEzypassTid(), regMobileUser.getUpdateType());

			}
			if (updateMobileUser != 0) {
				logger.info("moto device registered successfully in MobileUser by "+regMobileUser.getUpdateType()+" tid");
			}
			
		}
		
		
		//ezyrec
		
		if (regMobileUser.getEzyrecTid() != null && !(regMobileUser.getUpdateType().equals("ezyrec"))) 
		{
			logger.info("update type: to update"+regMobileUser.getUpdateType());
			TerminalDetails ezyrecTerminalDet = new TerminalDetails();
			
			
			

			ezyrecTerminalDet.setDeviceId(regMobileUser.getEzyrecdeviceId());
			ezyrecTerminalDet.setDeviceType("EZYREC");
			ezyrecTerminalDet.setMerchantId(regMobileUser.getEzyrecMid());
			ezyrecTerminalDet.setRefNo(regMobileUser.getEzyrecrefNo());
			ezyrecTerminalDet.setTid(regMobileUser.getEzyrecTid());
		//	ezyrecTerminalDet.setPreAuth(regMobileUser.getPreAuth());
			
			ezyrecTerminalDet.setActivatedDate(new Date());
			ezyrecTerminalDet.setActiveStatus("ACTIVE");
			ezyrecTerminalDet.setContactName(regMobileUser.getContactName());
			ezyrecTerminalDet.setConnectType("BT");
			ezyrecTerminalDet.setRenewalDate(new Date());
			Date date1 = null;
			 try {
				 date1=new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());
				 logger.info("Expiry Date:" + date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			 ezyrecTerminalDet.setSuspendedDate(date1);
			 ezyrecTerminalDet.setRemarks(regMobileUser.getRemarks());
			 ezyrecTerminalDet.setActivationCode(activationCode);
			
			 ezyrecTerminalDet=mobileuserDAO.saveOrUpdateEntity(ezyrecTerminalDet);
			if(ezyrecTerminalDet != null){
				logger.info("ezyrec device registered successfully in terminaldetails..");
			}
			
			InternalTable ezyrecinternalTab = new InternalTable();
			
			ezyrecinternalTab.setBatchId(1l);
			ezyrecinternalTab.setStan(1l);
			ezyrecinternalTab.setInvoiceNo(1l);
			ezyrecinternalTab.setTid(regMobileUser.getEzyrecTid());
			ezyrecinternalTab.setMid(regMobileUser.getEzyrecMid());
			// mobileuser1 = mobileuserDAO.saveOrUpdateEntity(mobileuser);

			ezyrecinternalTab = mobileuserDAO.saveOrUpdateEntity(ezyrecinternalTab);
			if (ezyrecinternalTab != null) {
				logger.info("ezyrec device registered successfully in internalTable by "+regMobileUser.getUpdateType());
			}
			
			
			int update = mobileuserDAO.updateKManager(regMobileUser.getEzyrecrefNo(), regMobileUser.getEzyrecTid());
			
			if(update!=0){
				logger.info("ezyrec device registered successfully in Kmanager by "+regMobileUser.getUpdateType());
			}
			int updateMobileUser =0;
			if(regMobileUser.getUpdateType().equals("moto"))
			{
				updateMobileUser = mobileuserDAO.updateEzyRecMobileuser(
						 regMobileUser.getEzyrecTid(),regMobileUser.getMotoTid(),regMobileUser.getUpdateType());

				
			}
			else if(regMobileUser.getUpdateType().equals("ezypass"))
			{
				updateMobileUser = mobileuserDAO.updateEzyRecMobileuser(
						regMobileUser.getEzyrecTid(),regMobileUser.getEzypassTid(), regMobileUser.getUpdateType());

				
			}
			else if(regMobileUser.getUpdateType().equals("ezywire"))
			{
				updateMobileUser = mobileuserDAO.updateEzyRecMobileuser(
						regMobileUser.getEzyrecTid(), regMobileUser.getTid(),regMobileUser.getUpdateType());

				
			}
			if (updateMobileUser != 0) {
				logger.info("ezyrec device registered successfully in MobileUser by "+regMobileUser.getUpdateType()+" tid");
			}
		}
		
		
		
		
		//ezypass
		if (regMobileUser.getEzypassTid() != null && !(regMobileUser.getUpdateType().equals("ezypass"))) 
		{
			logger.info("update type: to update"+regMobileUser.getUpdateType());
			TerminalDetails ezypassTerminalDet = new TerminalDetails();
			
			ezypassTerminalDet.setMerchantId(regMobileUser.getEzypassMid());
			ezypassTerminalDet.setRefNo(regMobileUser.getEzypassrefNo());
			ezypassTerminalDet.setTid(regMobileUser.getEzypassTid());
			ezypassTerminalDet.setDeviceId(regMobileUser.getEzypassdeviceId());
			ezypassTerminalDet.setDeviceType("EZYPASS");
			ezypassTerminalDet.setActivatedDate(new Date());
			ezypassTerminalDet.setActiveStatus("ACTIVE");
			ezypassTerminalDet.setContactName(regMobileUser.getContactName());
			ezypassTerminalDet.setConnectType("BT");
			ezypassTerminalDet.setRenewalDate(new Date());
			Date date1 = null;
			 try {
				 date1=new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());
				 logger.info("Expiry Date:" + date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			 ezypassTerminalDet.setSuspendedDate(date1);
			ezypassTerminalDet.setRemarks(regMobileUser.getRemarks());
			ezypassTerminalDet.setActivationCode(activationCode);
			
			ezypassTerminalDet=mobileuserDAO.saveOrUpdateEntity(ezypassTerminalDet);
			if(ezypassTerminalDet != null){
				logger.info("ezypass device registered successfully in terminaldetails..");
			}
			
			InternalTable ezypassinternalTab = new InternalTable();
			
			ezypassinternalTab.setBatchId(1l);
			ezypassinternalTab.setStan(1l);
			ezypassinternalTab.setInvoiceNo(1l);
			ezypassinternalTab.setTid(regMobileUser.getEzypassTid());
			ezypassinternalTab.setMid(regMobileUser.getEzypassMid());
			// mobileuser1 = mobileuserDAO.saveOrUpdateEntity(mobileuser);

			ezypassinternalTab = mobileuserDAO.saveOrUpdateEntity(ezypassinternalTab);
			if (ezypassinternalTab != null) {
				logger.info("ezypass device registered successfully in internalTable by "+regMobileUser.getUpdateType());
			}
			
			
			int update = mobileuserDAO.updateKManager(regMobileUser.getEzypassrefNo(), regMobileUser.getEzypassTid());
			
			if(update!=0){
				logger.info("ezypass device registered successfully in Kmanager by "+regMobileUser.getUpdateType());
			}
			int updateMobileUser =0;
			logger.info("updatetyp from ezypass funality: "+regMobileUser.getUpdateType());
			if(regMobileUser.getUpdateType().equals("moto"))
			{
				updateMobileUser = mobileuserDAO.updateMobileuserEzypass(
						 regMobileUser.getEzypassTid(),regMobileUser.getMotoTid(),regMobileUser.getUpdateType());

				
			}
			else if(regMobileUser.getUpdateType().equals("ezyrec"))
			{
				updateMobileUser = mobileuserDAO.updateMobileuserEzypass(
						regMobileUser.getEzypassTid(),regMobileUser.getEzyrecTid(), regMobileUser.getUpdateType());

				
			}
			else if(regMobileUser.getUpdateType().equals("ezywire"))
			{
				updateMobileUser = mobileuserDAO.updateMobileuserEzypass(
						regMobileUser.getEzypassTid(), regMobileUser.getTid(),regMobileUser.getUpdateType());

				
			}
			if (updateMobileUser != 0) {
				logger.info("ezypass device registered successfully in MobileUser by "+regMobileUser.getUpdateType()+" tid");
			}
		}
		
		//UMOBILE
		
		
		if(regMobileUser.getTid()!=null && regMobileUser.getUm_tid()!=null && (regMobileUser.getUpdateType().equals("ezywire"))) {
			
			TerminalDetails UterminalDetails = new TerminalDetails();
			logger.info("mid: "+regMobileUser.getUm_mid());
			logger.info("with umTid");
			logger.info("update type: to update"+regMobileUser.getUpdateType());
			

			UterminalDetails.setMerchantId(regMobileUser.getUm_mid());
			UterminalDetails.setActivatedDate(new Date());
			UterminalDetails.setDeviceId(regMobileUser.getUm_deviceId());
			UterminalDetails.setDeviceType("EZYWIRE");
			UterminalDetails.setActiveStatus("ACTIVE");
			UterminalDetails.setTid(regMobileUser.getUm_tid());
			UterminalDetails.setRefNo(regMobileUser.getUm_refNo());
			UterminalDetails.setContactName(regMobileUser.getContactName());
			UterminalDetails.setPreAuth(regMobileUser.getPreAuth());
			UterminalDetails.setConnectType("BT");
			UterminalDetails.setRenewalDate(new Date());
			
			
			Date date1 = null;
			 try {
				 date1=new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());
				 logger.info("Expiry Date:" + date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			 UterminalDetails.setSuspendedDate(date1);
			logger.info("Expiry Date:" + UterminalDetails.getSuspendedDate());
			UterminalDetails.setRemarks(regMobileUser.getRemarks());
			logger.info("Remarks:" + UterminalDetails.getRemarks());
			UterminalDetails.setActivationCode(activationCode);
			UterminalDetails=mobileuserDAO.saveOrUpdateEntity(UterminalDetails);
			if(UterminalDetails != null){
				logger.info("ezywire device registered successfully in terminaldetails..");
			}
			
			//TerminalDetails terminalDetails = new TerminalDetails();
			existTid.setUmTid(regMobileUser.getUm_tid());
			existTid=mobileuserDAO.saveOrUpdateEntity(existTid);
			
			
			InternalTable internalTab = new InternalTable();
			internalTab.setBatchId(1l);
			internalTab.setStan(1l);
			internalTab.setInvoiceNo(1l);
			internalTab.setTid(regMobileUser.getUm_tid());
			internalTab.setMid(regMobileUser.getUm_mid());
			
			internalTab=mobileuserDAO.saveOrUpdateEntity(internalTab);
			if(internalTab != null)
			{
				logger.info("UMOBILE device registered successfully in internalTable by "+regMobileUser.getUpdateType());
			}
			
			//updateKManager
			int update = mobileuserDAO.updateUM_KManager(regMobileUser.getUm_refNo(), regMobileUser.getUm_tid());
			
			if(update!=0){
				logger.info("ezywire device registered successfully in Kmanager by "+regMobileUser.getUpdateType());
			}
			
		}
		
		
		
	/*	if(regMobileUser.getUm_tid()!=null && !(regMobileUser.getUpdateType().equals("umobile")))
		{
			TerminalDetails UterminalDetails = new TerminalDetails();
			logger.info("mid: "+regMobileUser.getUm_mid());
			logger.info("update type: to update"+regMobileUser.getUpdateType());
			

			UterminalDetails.setMerchantId(regMobileUser.getUm_mid());
			UterminalDetails.setActivatedDate(new Date());
			UterminalDetails.setDeviceId(regMobileUser.getUm_deviceId());
			UterminalDetails.setDeviceType("UMOBILE");
			UterminalDetails.setActiveStatus("ACTIVE");
			UterminalDetails.setTid(regMobileUser.getUm_tid());
			UterminalDetails.setRefNo(regMobileUser.getUm_refNo());
			UterminalDetails.setContactName(regMobileUser.getContactName());
			UterminalDetails.setPreAuth(regMobileUser.getPreAuth());
			UterminalDetails.setConnectType("BT");
			UterminalDetails.setRenewalDate(new Date());
			
			
			Date date1 = null;
			 try {
				 date1=new SimpleDateFormat("dd-MMM-yyyy").parse(regMobileUser.getExpiryDate());
				 logger.info("Expiry Date:" + date1);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			 UterminalDetails.setSuspendedDate(date1);
			logger.info("Expiry Date:" + UterminalDetails.getSuspendedDate());
			UterminalDetails.setRemarks(regMobileUser.getRemarks());
			logger.info("Remarks:" + UterminalDetails.getRemarks());
			UterminalDetails.setActivationCode(activationCode);
			UterminalDetails=mobileuserDAO.saveOrUpdateEntity(UterminalDetails);
			if(UterminalDetails != null){
				logger.info("ezywire device registered successfully in terminaldetails..");
			}
			
			//TerminalDetails terminalDetails = new TerminalDetails();
			existTid.setUmTid(regMobileUser.getUm_tid());
			existTid=mobileuserDAO.saveOrUpdateEntity(existTid);
			
			
			InternalTable internalTab = new InternalTable();
			internalTab.setBatchId(1l);
			internalTab.setStan(1l);
			internalTab.setInvoiceNo(1l);
			internalTab.setTid(regMobileUser.getUm_tid());
			internalTab.setMid(regMobileUser.getUm_mid());
			
			internalTab=mobileuserDAO.saveOrUpdateEntity(internalTab);
			if(internalTab != null)
			{
				logger.info("UMOBILE device registered successfully in internalTable by "+regMobileUser.getUpdateType());
			}
			
			//updateKManager
			int update = mobileuserDAO.updateUM_KManager(regMobileUser.getUm_refNo(), regMobileUser.getUm_tid());
			
			if(update!=0){
				logger.info("ezywire device registered successfully in Kmanager by "+regMobileUser.getUpdateType());
			}
			
			int updateMobileUser=0;
			
			
			if(regMobileUser.getUpdateType().equals("moto"))
			{
				updateMobileUser = mobileuserDAO.updateEzywireMobileuser(
						regMobileUser.getTid(), regMobileUser.getMotoTid(),
						regMobileUser.getUpdateType(),regMobileUser.getPreAuth());

				
			}
			else if(regMobileUser.getUpdateType().equals("ezyrec"))
			{
				updateMobileUser = mobileuserDAO.updateEzywireMobileuser(
						regMobileUser.getTid(), regMobileUser.getEzyrecTid(),
						regMobileUser.getUpdateType(),regMobileUser.getPreAuth());

				
			}else if(regMobileUser.getUpdateType().equals("ezypass"))
			{
				updateMobileUser = mobileuserDAO.updateEzywireMobileuser(
						regMobileUser.getTid(), regMobileUser.getEzypassTid(),
						regMobileUser.getUpdateType(),regMobileUser.getPreAuth());

				
			}
			if (updateMobileUser != 0) {
				logger.info("ezywire device registered successfully in MobileUser by "+regMobileUser.getUpdateType()+" tid");
			}
		}*/
		

		
		return regMobileUser;

	}
	
	/* change password a mobileuser */
	public MobileUser changePwdMobileUser(final MobileUser entity) {
		//System.out.println("changePwdMobileUser" + entity.getMerchant());
		entity.setStatus(CommonStatus.ACTIVE);
		entity.setPassword(encoder.encode(entity.getPassword()));
		mobileuserDAO.changePwdMobileUser(entity);
		return entity;
	}
	
	
	
	
	//grapPay registration
	
    public RegMobileUser searchMobileUsergPayadd(final RegMobileUser entity) {
		
		RegMobileUser rmu = new RegMobileUser();
		String merchantDetails=null;
		
		rmu.setMerchantID(entity.getMerchantID());
		rmu.setFirstName(entity.getFirstName());
		rmu.setSalutation(entity.getSalutation());
		rmu.setId(entity.getId());
		rmu.setMerchantName(entity.getMerchantName());
		rmu.setMerchantusername(entity.getMerchantusername());
		rmu.setEmailId(entity.getEmailId());
		rmu.setContactNo(entity.getContactNo());
		//rmu.setPreAuth(entity.getPreAuth());
		//rmu.setMotopreAuth(entity.getMotopreAuth());
		rmu.setContactName(entity.getContactName());
		
		rmu.setRemarks(entity.getRemarks());
		rmu.setBusinessName(entity.getBusinessName());
		
		rmu.setgPayMid(entity.getgPayMid());
		DeviceRandomNumber d = new DeviceRandomNumber();
		rmu.setgPaydeviceId("GPAY0000"+d.generateRandomString());
		rmu.setgPayTid(entity.getgPayTid());
		rmu.setgPayrefNo(entity.getgPayrefNo());
		
		LocalDate date = LocalDate.now(); 
		String[] arr=date.toString().split("-");
		
		int expiryYear=Integer.parseInt(arr[0])+100;
		String expiryDate=arr[2]+"/"+arr[1]+"/"+String.valueOf(Integer.parseInt(arr[0])+100);
		String strDate = null;
		try {
			strDate = new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("dd/MM/yyyy").parse(expiryDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		rmu.setExpiryDate(strDate);
		logger.info("expiry date: "+rmu.getExpiryDate());
		
		return rmu;
		
	}
    
    
    
	
	
	
	
	
	
	@SuppressWarnings("unused")
	//@javax.transaction.Transactional
	public RegMobileUser searchMobileUseradd(final RegMobileUser entity) {
		
		RegMobileUser regMob = new RegMobileUser();
		//logger.info("ummototid`````````: "+entity.getUm_motoTid()+" "+entity.getUm_motodeviceId());
		
		logger.info("businessname" +entity.getBusinessName());
		String merchantDetails=null;
		
		Merchant merchant=null;
		logger.info("updatetype: "+entity.getUpdateType());
		
		
		if(entity.getMerchantType()!=null) {
			
			if(entity.getMerchantType().equalsIgnoreCase("U")) {
				
				logger.info("entity.getUm_mid(): "+entity.getUm_mid());
				logger.info("entity.getUm_motoMid(): "+entity.getUm_motoMid());
				logger.info("entity.getUm_ezypassMid(): "+entity.getUm_ezypassMid());
				logger.info("entity.getUm_ezywayMid(): "+entity.getUm_ezywayMid());
				logger.info("entity.getUm_ezyrecMid(): "+entity.getUm_ezyrecMid());
				
				
				
			if (entity.getUm_mid() != null) {
				MID mid1 = merchantDAO.loadMidtoUpdateAudit(entity.getUm_mid());
				mid1.setUmMid(entity.getUm_mid());
					merchant = merchantDAO.loadMerchant(mid1);
				
			}else if (entity.getUm_motoMid() != null ){
				MID mid2 = merchantDAO.loadMidtoUpdateAudit(entity.getUm_motoMid());
				mid2.setUmMotoMid(entity.getUm_motoMid());
				merchant = merchantDAO.loadMerchant(mid2);
			}else if (entity.getUm_ezypassMid() != null ) {
				MID mid3 = merchantDAO.loadMidtoUpdateAudit(entity.getUm_ezypassMid());
				mid3.setUmEzypassMid(entity.getUm_ezypassMid());
				merchant = merchantDAO.loadMerchant(mid3);
			}else if (entity.getUm_ezywayMid() != null ) {
				MID mid3 = merchantDAO.loadMidtoUpdateAudit(entity.getUm_ezywayMid());
				mid3.setUmEzywayMid(entity.getUm_ezywayMid());
				merchant = merchantDAO.loadMerchant(mid3);
			}else if (entity.getUm_ezyrecMid() != null ) {
				MID mid3 = merchantDAO.loadMidtoUpdateAudit(entity.getUm_ezyrecMid());
				mid3.setUmEzyrecMid(entity.getUm_ezyrecMid());
				merchant = merchantDAO.loadMerchant(mid3);
			}
			}else if(entity.getMerchantType().equalsIgnoreCase("P")){
				
				if (entity.getMid() != null) {
					MID mid1 = merchantDAO.loadMerchantmid(entity.getMid());
					if (mid1 != null) {
						mid1.setMid(entity.getMid());
						merchant = merchantDAO.loadMerchant(mid1);
					}

				}else if (entity.getMotoMid() != null ){
					logger.info("motomid: "+entity.getMotoMid());
					MID mid2 = merchantDAO.loadMerchantmotomid(entity.getMotoMid());
					mid2.setMotoMid(entity.getMotoMid());
					merchant = merchantDAO.loadMerchant(mid2);
				}else if (entity.getEzypassMid() != null ) {
					MID mid3 = merchantDAO.loadEzyPassMid(entity.getEzypassMid());
					mid3.setEzypassMid(entity.getEzypassMid());
					merchant = merchantDAO.loadMerchant(mid3);
				}else if (entity.getEzywayMid() != null ) {
					MID mid3 = merchantDAO.loadEzywayMid(entity.getEzywayMid());
					mid3.setEzywayMid(entity.getEzywayMid());
					merchant = merchantDAO.loadMerchant(mid3);
				}else if (entity.getEzyrecMid() != null ) {
					MID mid3 = merchantDAO.loadEzyrecMid(entity.getEzyrecMid());
					mid3.setEzyrecMid(entity.getEzyrecMid());
					merchant = merchantDAO.loadMerchant(mid3);
				}
				
				
			}
		}else {
			logger.info("getMerchantType is null");
			if (entity.getMid() != null) {
				MID mid1 = merchantDAO.loadMerchantmid(entity.getMid());
				if (mid1 != null) {
					mid1.setMid(entity.getMid());
					merchant = merchantDAO.loadMerchant(mid1);
				}

			}else if (entity.getMotoMid() != null ){
				logger.info("motomid: "+entity.getMotoMid());
				MID mid2 = merchantDAO.loadMerchantmotomid(entity.getMotoMid());
				mid2.setMotoMid(entity.getMotoMid());
				merchant = merchantDAO.loadMerchant(mid2);
			}else if (entity.getEzypassMid() != null ) {
				MID mid3 = merchantDAO.loadEzyPassMid(entity.getEzypassMid());
				mid3.setEzypassMid(entity.getEzypassMid());
				merchant = merchantDAO.loadMerchant(mid3);
			}else if (entity.getEzywayMid() != null ) {
				MID mid3 = merchantDAO.loadEzywayMid(entity.getEzywayMid());
				mid3.setEzywayMid(entity.getEzywayMid());
				merchant = merchantDAO.loadMerchant(mid3);
			}else if (entity.getEzyrecMid() != null ) {
				MID mid3 = merchantDAO.loadEzyrecMid(entity.getEzyrecMid());
				mid3.setEzyrecMid(entity.getEzyrecMid());
				merchant = merchantDAO.loadMerchant(mid3);
			}
		}
		
		
		regMob.setHashkey(entity.getHashkey());
		regMob.setDTL(entity.getDTL());
		regMob.setDomainUrl(entity.getDomainUrl());
		
		regMob.setHashkey1(entity.getHashkey1());
		regMob.setDTL1(entity.getDTL1());
		
		regMob.setRecHashkey(entity.getRecHashkey());
		regMob.setRecDTL(entity.getRecDTL());
		regMob.setVcc(entity.getVcc());
		
		
		
		if(merchant!=null){
			
			logger.info("merchant"+merchant);
		//logger.info(merchant.getContactPersonName());
	//logger.info("device id to check: "+entity.getDeviceId()+" "+entity.getReferenceNo());
	
		regMob.setId(merchant.getId());
		regMob.setFirstName(merchant.getContactPersonName());
		regMob.setSalutation(merchant.getSalutation());
		regMob.setPreAuth(entity.getPreAuth());
		regMob.setMotopreAuth(entity.getMotopreAuth());
		regMob.setMerchantName(merchant.getBusinessName());
		regMob.setMerchantusername(merchant.getUsername());
		regMob.setEmailId(merchant.getEmail());
		regMob.setContactNo(merchant.getBusinessContactNumber());
		regMob.setContactName(entity.getContactName());
		regMob.setExpiryDate(entity.getExpiryDate());
		regMob.setRemarks(entity.getRemarks());
		regMob.setBusinessName(entity.getBusinessName());
		
//		regMob.setMerchantType(merchant.getMerchantType().toString());
		
		
		regMob.setMid(entity.getMid());
		regMob.setDeviceId(entity.getDeviceId());
		regMob.setTid(entity.getTid());
		regMob.setReferenceNo(entity.getReferenceNo());
		
		regMob.setEzypassdeviceId(entity.getEzypassdeviceId());
		regMob.setEzypassMid(entity.getEzypassMid());
		regMob.setEzypassrefNo(entity.getEzypassrefNo());
		regMob.setEzypassTid(entity.getEzypassTid());
		
		regMob.setMotodeviceId(entity.getMotodeviceId());
		regMob.setMotoMid(entity.getMotoMid());
		regMob.setMotorefNo(entity.getMotorefNo());
		regMob.setMotoTid(entity.getMotoTid());

		regMob.setEzywayMid(entity.getEzywayMid());
		regMob.setEzywayTid(entity.getEzywayTid());
		regMob.setEzywayrefNo(entity.getEzywayrefNo());
		regMob.setEzywaydeviceId(entity.getEzywaydeviceId());
		
		regMob.setEzyrecMid(entity.getEzyrecMid());
		regMob.setEzyrecTid(entity.getEzyrecTid());
		regMob.setEzyrecrefNo(entity.getEzyrecrefNo());
		regMob.setEzyrecdeviceId(entity.getEzyrecdeviceId());
		regMob.setEzypod(entity.getEzypod());
		
		
		regMob.setUm_mid(entity.getUm_mid());
		regMob.setUm_tid(entity.getUm_tid());
		regMob.setUm_refNo(entity.getUm_refNo());
		regMob.setUm_deviceId(entity.getUm_deviceId());
		
		regMob.setUm_motodeviceId(entity.getUm_motodeviceId());
		regMob.setUm_motoMid(entity.getUm_motoMid());
		regMob.setUm_motorefNo(entity.getUm_motorefNo());
		regMob.setUm_motoTid(entity.getUm_motoTid());
		
		regMob.setUm_ezypassdeviceId(entity.getUm_ezypassdeviceId());
		regMob.setUm_ezypassMid(entity.getUm_ezypassdeviceId());
		regMob.setUm_ezypassrefNo(entity.getUm_ezypassrefNo());
		regMob.setUm_ezypassTid(entity.getUm_ezypassTid());
		
		regMob.setUm_ezywaydeviceId(entity.getUm_ezywaydeviceId());
		regMob.setUm_ezywayMid(entity.getUm_ezywayMid());
		regMob.setUm_ezywayrefNo(entity.getUm_ezywayrefNo());
		regMob.setUm_ezywayTid(entity.getUm_ezywayTid());
		
		regMob.setUm_ezyrecdeviceId(entity.getUm_ezyrecdeviceId());
		regMob.setUm_ezyrecMid(entity.getUm_ezyrecMid());
		regMob.setUm_ezyrecrefNo(entity.getUm_ezyrecrefNo());
		regMob.setUm_ezyrecTid(entity.getUm_ezyrecTid());
		
		}
		
		
		return regMob;
		
	}
	
	
	@SuppressWarnings("unused")
	//@javax.transaction.Transactional
	public RegMobileUser searchMobileUser(final RegMobileUser entity) {

		RegMobileUser regMob = new RegMobileUser();

		String merchantDetails = null;
		logger.info("ezyrecmid: "+entity.getEzyrecMid());
		Merchant merchant = null;
		logger.info("updatetype: " + entity.getUpdateType());

		if (entity.getMid() != null && entity.getUpdateType().equals("ezywire")) {
			regMob.setUpdateType(entity.getUpdateType());
			merchant = merchantDAO.loadMerchant(merchantDAO.loadMidtoUpdateAudit(entity.getMid()));
		}else if (entity.getMotoMid() != null && entity.getUpdateType().equals("moto")) {
			regMob.setUpdateType(entity.getUpdateType());
			merchant = merchantDAO.loadMerchant(merchantDAO.loadMidtoUpdateAudit(entity.getMotoMid()));
		} else if (entity.getEzypassMid() != null && entity.getUpdateType().equals("ezypass")) {
			regMob.setUpdateType(entity.getUpdateType());
			merchant = merchantDAO.loadMerchant(merchantDAO.loadMidtoUpdateAudit(entity.getEzypassMid()));
		} else if (entity.getEzyrecMid() != null && entity.getUpdateType().equals("ezyrec")) {
			logger.info("inside ezyrec..."+entity.getEzyrecMid());
			regMob.setUpdateType(entity.getUpdateType());
			merchant = merchantDAO.loadMerchant(merchantDAO.loadMidtoUpdateAudit(entity.getEzyrecMid()));
		}
		else if (entity.getUm_motoMid() != null && entity.getUpdateType().equals("umobilemoto")) {
			logger.info("inside Um_moto..."+entity.getUm_motoMid());
			regMob.setUpdateType(entity.getUpdateType());
			merchant = merchantDAO.loadMerchant(merchantDAO.loadMidtoUpdateAudit(entity.getUm_motoMid()));
			regMob.setHashkey(entity.getHashkey());
			regMob.setDTL(entity.getDTL());
		}
		else if (entity.getUm_ezywayMid() != null && entity.getUpdateType().equals("umobileezyway")) {
			logger.info("inside Um_ezyway..."+entity.getUm_ezywayMid());
			regMob.setUpdateType(entity.getUpdateType());
			merchant = merchantDAO.loadMerchant(merchantDAO.loadMidtoUpdateAudit(entity.getUm_ezywayMid()));
			regMob.setHashkey1(entity.getHashkey1());
			regMob.setDTL1(entity.getDTL1());
		}
		logger.info("merchant id: "+merchant.getId());
		logger.info("merchantusername: "+merchant.getUsername());
		regMob.setFirstName(merchant.getContactPersonName());
		regMob.setSalutation(merchant.getSalutation());
		regMob.setMerchantName(merchant.getBusinessName());
		regMob.setMerchantusername(merchant.getUsername());
		regMob.setEmailId(merchant.getEmail());
		regMob.setContactNo(merchant.getBusinessContactNumber());
		regMob.setRemarks(entity.getRemarks());
		regMob.setContactName(entity.getContactName());
		regMob.setExpiryDate(entity.getExpiryDate());
		regMob.setId(merchant.getId());

		logger.info("update type: "+regMob.getUpdateType()+" "+regMob.getMerchantusername());
		// ezywire
		regMob.setMid(entity.getMid());
		regMob.setDeviceId(entity.getDeviceId());
		regMob.setTid(entity.getTid());
		regMob.setReferenceNo(entity.getReferenceNo());
		regMob.setPreAuth(entity.getPreAuth());
		regMob.setEzywiremobusername(entity.getEzywiremobusername());

		// moto
		regMob.setMotodeviceId(entity.getMotodeviceId());
		regMob.setMotoMid(entity.getMotoMid());
		regMob.setMotorefNo(entity.getMotorefNo());
		regMob.setMotoTid(entity.getMotoTid());
		regMob.setMotousername(entity.getMotousername());
		if (entity.getMotopreAuth() == null) {
			regMob.setMotopreAuth("No");
		} else {
			regMob.setMotopreAuth(entity.getMotopreAuth());
		}

		// ezypass
		regMob.setEzypassusername(entity.getEzypassusername());
		regMob.setEzypassdeviceId(entity.getEzypassdeviceId());
		regMob.setEzypassMid(entity.getEzypassMid());
		regMob.setEzypassrefNo(entity.getEzypassrefNo());
		regMob.setEzypassTid(entity.getEzypassTid());

		
		// ezyrec
		regMob.setEzyrecusername(entity.getEzyrecusername());
		regMob.setEzyrecdeviceId(entity.getEzyrecdeviceId());
		regMob.setEzyrecMid(entity.getEzyrecMid());
		regMob.setEzyrecrefNo(entity.getEzyrecrefNo());
		regMob.setEzyrecTid(entity.getEzyrecTid());
		
		//umTid
		regMob.setUm_tid(entity.getUm_tid());
		regMob.setUm_deviceId(entity.getUm_deviceId());
		regMob.setUm_refNo(entity.getUm_refNo());
		regMob.setUmusername(entity.getUmusername());
		regMob.setUm_mid(entity.getUm_mid());
		
		//ummotoTid
		regMob.setUm_motoTid(entity.getUm_motoTid());
		regMob.setUm_motodeviceId(entity.getUm_motodeviceId());
		regMob.setUm_refNo(entity.getUm_refNo());
		regMob.setUmusername(entity.getUmusername());
		regMob.setUm_motoMid(entity.getUm_motoMid());
		
		//ummotoTid
		regMob.setUm_ezywayTid(entity.getUm_ezywayTid());
		regMob.setUm_ezywaydeviceId(entity.getUm_ezywaydeviceId());
		regMob.setUm_refNo(entity.getUm_refNo());
		regMob.setUmusername(entity.getUmusername());
		regMob.setEzywayusername(entity.getEzywayusername());
		regMob.setUm_ezywayMid(entity.getUm_ezywayMid());
		
		logger.info("getUm_motoTid id: "+regMob.getUm_motoTid());
		logger.info("getUm_motoMid: "+regMob.getUm_motoMid());
		return regMob;

	}

	
	@SuppressWarnings("unused")
	//@javax.transaction.Transactional
	public MobileUser updateMobileUSer(final MobileUser mobileUser) 
	{
		
		//logger.info("Service : about to list all merchant1");
		mobileUser.setStatus(CommonStatus.ACTIVE);
		//logger.info("Service : about to list all merchant2");
		  return mobileuserDAO.saveOrUpdateEntity(mobileUser);
		//return merchantDAO.saveOrUpdateEntity(merchant);

	}
	// end mobile user methods 31052016
	
	
	
	//new method mobileuser 17062016
	
	//@javax.transaction.Transactional
	public RegMobileUser loadMobileUserDeviceId(String deviceId){
		RegMobileUser regMobileUser = new RegMobileUser();
		return mobileuserDAO.loadMobileUserDeviceId(deviceId);
		//return agent;
		
	}
	
	public MobileUser loadGrabPayTid(String deviceId){
		MobileUser mobileUser = new MobileUser();
		return mobileuserDAO.loadGrabPayTid(deviceId);
		//return agent;
		
	}
	
	
	
	//new demo method mobileuser20062016
	
	//@javax.transaction.Transactional
	public TerminalDetails loadDeviceId(String deviceId){
		
		return mobileuserDAO.loadDeviceId(deviceId);
		//return agent;
		
	}
	
	//@javax.transaction.Transactional
	public List<KManager> loadKManager(){
		
		return mobileuserDAO.loadRefNoToTid();
	}
	
	public List<UMKManager> loadUmKManager(){
		
		return mobileuserDAO.loadUmRefNoToTid();
	}
	
	public List<MobileUser> loadMobileUserDetails(Long id) {

		return mobileuserDAO.loadMobileUserDetails(id);

	}
	
	public List<MobileUser> loadUmMobileUserDetails(Long id) {

		return mobileuserDAO.loadUmMobileUserDetails(id);

	}
	
	public MobileUser loadMobileUsertidDetails(String tid) {

		return mobileuserDAO.loadMobileUsertidDetails(tid);

	}
	public MobileUser loadMobileUserMototidDetails(String motoTid) {

		return mobileuserDAO.loadMobileUserMototidDetails(motoTid);

	}
	public MobileUser loadMobileUserEzywaytidDetails(String ezywayTid) {

		return mobileuserDAO.loadMobileUserEzywaytidDetails(ezywayTid);

	}
	public MobileUser loadMobileUserEzyRectidDetails(String ezyrecTid) {

		return mobileuserDAO.loadMobileUserEzyRectidDetails(ezyrecTid);

	}
	public MobileUser loadMobileUserEzypasstidDetails(String ezypassTid) {

		return mobileuserDAO.loadMobileUserEzypasstidDetails(ezypassTid);

	}
	
	// to display list of all transactions
		//@javax.transaction.Transactional
		public void listMobileUserDetails(final PaginationBean<MobileUser> paginationBean, final String data,
				final String date1) {
			//System.out.println("Inside  listAllTransaction");
			// String data= null;
			ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
			mobileuserDAO.listMobileUserDetails(paginationBean, criterionList, data, date1);
			//reportDataDAO.getDetails("90");
		}
	
		
		// new method for wifi  & BT mobile user 04072017
		
		
		public TerminalDetails loadTerminalDetailsByTid(String tid) {
			//TerminalDetails terminalDetails = new TerminalDetails();
			return mobileuserDAO.loadTerminalDetailsByTid(tid);
			// return agent;

		}
		public TerminalDetails loadTerminalDetailsByActivationCode(String activationCode) {
			//TerminalDetails terminalDetails = new TerminalDetails();
			
			return mobileuserDAO.loadTerminalDetailsByActivationcode(activationCode);
			// return agent;

		}
		
		public TerminalDetails loadTerminalDetailsByAnyTid(RegMobileUser tid) {
			//TerminalDetails terminalDetails = new TerminalDetails();
			return mobileuserDAO.loadTerminalDetailsByAnyTid(tid);
			// return agent;

		}
		
		public List<TerminalDetails> loadTerminalDetailsByMid(String mid) {
			//TerminalDetails terminalDetails = new TerminalDetails();
			return mobileuserDAO.loadTerminalDetailsByMid(mid);
			// return agent;

		}
		
		//new method for mobile user summary export 02/08/2017
		public List<MobileUser> mobileUserSummary(final String date,final String date1) {
			System.out.println("Inside  monthly Transaction Export");
			ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
			 return mobileuserDAO.listMobileUserexpt(criterionList,date, date1);
		}
		
		
		public MobileUserData loadMobileUserDataByTid(final String id)
		{
			//logger.info("id: "+id.toString());
			MobileUserData mobileuser = null;
			try{
			if(id.toString()!=null){
				logger.info("id"+id.toString());
			mobileuser = mobileuserDAO.loadMobileUserByTid(id.toString());
			if (mobileuser == null) {
				throw new RuntimeException("MobileUser Not found. ID:: " + id);
			}
			}
			}catch(Exception e){}
			return mobileuser;
			
		}
		
		public MobileUser loadMobileUserByTid(final String id) {
			MobileUser mobileuser = mobileuserDAO.loadMobileUserbyTid(id.toString());
			if (mobileuser == null) {
				throw new RuntimeException("MobileUser Not found. ID:: " + id);
			}
			return mobileuser;
		}
		
		
		public MobileUser loadMobileUserbyMerchantFK(final String id) {
			MobileUser mobileuser = mobileuserDAO.loadMobileUserbyMerchantFK(id.toString());
			if (mobileuser == null) {
				throw new RuntimeException("MobileUser Not found. ID:: " + id);
			}
			return mobileuser;
		}
		
		public List<MobileUser> loadMobileUsersbyMerchantFK(final String id) {
			List<MobileUser> mobileuser = mobileuserDAO.loadMobileUsersbyMerchantFK(id.toString());
			if (mobileuser == null) {
				throw new RuntimeException("MobileUser Not found. ID:: " + id);
			}
			return mobileuser;
		}
		public MobileUserData updateMobileUSerDetails(final MobileUserData mobileUser) 
		{
			MobileUserData mud = new MobileUserData();
			logger.info("Service : about to list all merchant1"+mobileUser.getTid());
			logger.info("expirty date: "+mobileUser.getExpiryDate()+" status: "+mobileUser.getStatus());
			MobileUser mobileUser1 = mobileuserDAO.loadMobileUserbyTid(mobileUser.getTid());
			
			if(mobileUser1 != null){
			
				logger.info("enableBoost: "+mobileUser.getEnableBoost()+" enableMoto: "+mobileUser.getEnableMoto());
				//if(!(mobileUser.getPreAuth().equals(mobileUser1.getPreAuth()))){
					
					//mobileuserDAO.updateMobileUserByTid(mobileUser.getTid(),mobileUser.getPreAuth(),mobileUser1.getId());
					mobileuserDAO.updateMobileUserByTid(mobileUser.getTid(),mobileUser.getPreAuth(),
							mobileUser1.getId(),mobileUser.getEnableBoost(),mobileUser.getEnableMoto());
					
				//}
			}
			TerminalDetails terminalDetails = mobileuserDAO.loadTerminalDetailsByTid(mobileUser.getTid());
			logger.info("terminal-------: "+terminalDetails.getTid()+" "+terminalDetails.getSuspendedDate()+" "+
					terminalDetails.getRenewalDate());
			logger.info("expirty date: "+terminalDetails.getSuspendedDate()+" status: "+terminalDetails.getActiveStatus());
			if(terminalDetails != null){
				String expiryDate = null; 
				if(terminalDetails.getSuspendedDate() != null){
					String pattern = "dd-MMM-yyyy";
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

					//String date = simpleDateFormat.format(new Date());
					expiryDate = simpleDateFormat.format(terminalDetails.getSuspendedDate());
					logger.info("expirydate: "+expiryDate);
				}
				 if(expiryDate != null){ 
					logger.info("expirydate: "+expiryDate + "::: mobileuser getExpiryDate Value : "+mobileUser.getExpiryDate());
					if(!(expiryDate.equals(mobileUser.getExpiryDate()))){
						logger.info("expirydate: "+expiryDate);
						TerminalRenewal terminalRenewal = new TerminalRenewal();
						
						terminalRenewal.setDeviceId(terminalDetails.getDeviceId());
						terminalRenewal.setRenewalDate(""+terminalDetails.getRenewalDate());
						terminalRenewal.setExpiryDate(""+terminalDetails.getSuspendedDate());
						terminalRenewal.setRemarks(terminalDetails.getRemarks());
						
						terminalRenewal = mobileuserDAO.saveOrUpdateEntity(terminalRenewal);
						
						TerminalDetails terminalDetails2 = new TerminalDetails();
						
						terminalDetails2.setRenewalDate(new Date());
						
						//if(terminalDetails.getActiveStatus().equals("ACTIVE")){
							Date date1 = null;
							 try {
								 logger.info("expiry date: "+mobileUser.getExpiryDate());
								 date1=new SimpleDateFormat("dd/MM/yyyy").parse(mobileUser.getExpiryDate());
								 logger.info("Expiry Date:" + date1);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}  
							 terminalDetails2.setSuspendedDate(date1);
							 terminalDetails2.setActiveStatus(mobileUser.getStatus());
							logger.info("Expiry Date:" + terminalDetails2.getSuspendedDate());
						/*}else{
							
						}*/
						
						//terminalDetails2.setSuspendedDate(new Date(mobileUser.getExpiryDate()));
						terminalDetails2.setTid(mobileUser.getTid());
						terminalDetails2.setDeviceId(terminalDetails.getDeviceId());
						terminalDetails2.setRemarks(mobileUser.getRemarks());
						
						mobileuserDAO.updateTerminalDetailsByTid(terminalDetails2);
						
					}
				}
			}
			
			//mobileUser.setStatus(CommonStatus.ACTIVE);
			//logger.info("Service : about to list all merchant2");
			  //return mobileuserDAO.saveOrUpdateEntity(mobileUser);
			//return merchantDAO.saveOrUpdateEntity(merchant);
			return mud;
		}
		
		
		public MobileUser editMobileUserDetails(final MobileUser entity) {
			//System.out.println("changePwdMobileUser" + entity.getMerchant());
			/*entity.setStatus(CommonStatus.ACTIVE);
			entity.setPassword(encoder.encode(entity.getPassword()));
			mobileuserDAO.editMobileUserDetails(entity);*/
			System.out.println("editMobileUserDetails successful: " + entity.getUsername());
			return merchantDAO.saveOrUpdateEntity(entity);
			//return entity;
		}
		
		public MobileUser loadMobileUserByIdName(long id,String name){
			MobileUser mobileUser1 = mobileuserDAO.loadMobileUserByIdAndName(id,name);
			return mobileUser1;
		}
		public List<MobileUser> loadMobileUserByIdEmail(long id,String email){
			logger.info("email to check: "+email);
			List<MobileUser> mobileuser= mobileuserDAO.loadMobileUserByIdAndEmail(id,email);
			for(MobileUser m:mobileuser){
				logger.info("email: "+m.getEmail());
			}
			
			return mobileuser;
		}
		public List<MobileUser> loadMobileUserByIdContact(long id,String contact){
			logger.info("contact to check: "+contact);
			List<MobileUser> mobileuser= mobileuserDAO.loadMobileUserByIdAndContact(id,contact);
			for(MobileUser m:mobileuser){
				logger.info("email: "+m.getEmail());
			}
			
			return mobileuser;
		}
		
		public MobileUser loadMobileUserDeviceMapping(){
			MobileUser mobileUser = mobileuserDAO.loadMobileUserDeviceMapping();
			/*MobileUser mobuser=new RegMobileUser();
			mobuser.setUsername(mobileUser.getUsername());
			mobuser.setStatus(mobileUser.getStatus().toString());
			mobuser.setTid(mobileUser.getTid());
			mobuser.setMotoMid(mobileUser.getMotoTid());
			mobuser.setEzypassTid(mobileUser.getEzypassTid());
			mobuser.setEzyrecTid(mobileUser.getEzyrecTid());
			mobuser.setId(mobileUser.getId());
			mobuser.setPreAuth(mobileUser.getPreAuth());*/
			return mobileUser;
		}
		
		public MobileUser updateMobileUserTid(MobileUser mobuser){
			
			MobileUserData mobileData = new MobileUserData();
			TerminalDetails terminal =null;
			mobileuserDAO.updateMobileUserWithTid(mobuser);
			
			/*mobileData.setExpiryDate(""+mobuser.getId());
			mobileData.setMobileUserName(mobuser.getUsername());
			mobileData.setPreAuth(mobuser.getPreAuth());
			mobileData.setTid(mobuser.getTid());
			mobileData.setMotoTid(mobuser.getMotoTid());
			mobileData.setStatus(mobuser.getStatus().toString());*/
			
			
		if (mobuser.getEzyrecTid() != null && !mobuser.getEzyrecTid().isEmpty()) {

			terminal = mobileuserDAO.loadTerminalDetailsByTid(mobuser.getEzyrecTid());
		} else if (mobuser.getMotoTid() != null && !mobuser.getMotoTid().isEmpty()) {

			terminal = mobileuserDAO.loadTerminalDetailsByTid(mobuser.getMotoTid());
		} else if (mobuser.getEzypassTid() != null && !mobuser.getEzypassTid().isEmpty()) {

			terminal = mobileuserDAO.loadTerminalDetailsByTid(mobuser.getEzypassTid());
		} else if (mobuser.getTid() != null && !mobuser.getTid().isEmpty()) {

			terminal = mobileuserDAO.loadTerminalDetailsByTid(mobuser.getTid());
		}
		if (terminal != null) {
			//mobileData.setDeviceId(terminal.getDeviceId());
			mobuser.setDeviceId(terminal.getDeviceId());

		}
			
			return mobuser;
			
		}
		

		public Merchant loadIserMidDetails(Long id) {
			
			return mobileuserDAO.loadIserMidDetails(id);
		}
		public Merchant loadMidDetails(Long id) {
			
			return mobileuserDAO.loadMidDetails(id);
		}


		public String loadBusinessName(String connectType) {
			// TODO Auto-generated method stub
			return mobileuserDAO.loadBusinessName(connectType);
		}


		public List<MobileUser> loadMobileUser() {
			// TODO Auto-generated method stub
			return mobileuserDAO.loadMobileUser();
		}
		
		public UMMidTxnLimit loadUmMidTxnLimitDetails(String motoMid) {

			return mobileuserDAO.loadUmMidTxnLimitDetails(motoMid);

		}
		
		@SuppressWarnings("unused")
		// @javax.transaction.Transactional
		public RegMobileUser updateUmTxnMidUser(final RegMobileUser regMobileUser) {
			
			if (regMobileUser.getUm_motoMid() != null && (regMobileUser.getUpdateType().equals("umobilemoto"))) 
			{
				logger.info("UM MOTO MID :::::::::::::::::.."+regMobileUser.getUm_motoMid());
				UMMidTxnLimit umtxnlt = null; //new UMMidTxnLimit();
				umtxnlt= mobileuserDAO.loadDetByMid(regMobileUser.getUm_motoMid());
				 String status = "NO";
				if(umtxnlt!=null) {
				//if(umtxnlt.getMid()!=null) {
					
					//if(umtxnlt.getMid().equals(regMobileUser.getUm_ezywayMid()) && (!umtxnlt.getMid().isEmpty())) {
						logger.info("To Update TXN limitt"+umtxnlt.getMid());
						
						
						//umtxnlt.setActivateDate(new Date());
						//umtxnlt.setStatus(CommonStatus.ACTIVE);
						umtxnlt.setMid(regMobileUser.getUm_motoMid());
						//umtxnlt.setCreatedBy("Admin");
						//umtxnlt.setCreatedDate(new Date());
						//umtxnlt.setHashKey(regMobileUser.getHashkey());
						umtxnlt.setDtl(regMobileUser.getDTL());
						
						
						logger.info("update UMMidTxnLimit save Db" +umtxnlt.getHashKey()+"::"+umtxnlt.getDtl());
						
						
						umtxnlt = umMidTxnLimitDAO.saveOrUpdateEntity(umtxnlt);
						status = "YES";
						/*int updateDet = mobileuserDAO.updateUMTxnLimit(regMobileUser.getHashkey(), regMobileUser.getDTL(),regMobileUser.getUm_motoMid());
						
						if (updateDet != 0) {
							logger.info("ezyway device details successfully updated in UMMidTxnLimit");
						}
						*/
						
						
					}/*else {
						umtxnlt = new UMMidTxnLimit();
						logger.info(" To Save TXN limit");
						umtxnlt.setActivateDate(new Date());
						umtxnlt.setStatus(CommonStatus.ACTIVE);
						umtxnlt.setMid(regMobileUser.getUm_motoMid());
						umtxnlt.setCreatedBy("Admin");
						umtxnlt.setCreatedDate(new Date());
						umtxnlt.setHashKey(regMobileUser.getHashkey());
						umtxnlt.setDtl(regMobileUser.getDTL());

						logger.info("save UMMidTxnLimit save Db" +umtxnlt.getHashKey()+"::"+umtxnlt.getDtl());
						
						
						umtxnlt = umMidTxnLimitDAO.saveOrUpdateEntity(umtxnlt);
						
						if (umtxnlt != null) {
							status = "YES";
							logger.info("ezymoto device details successfully added in UMMidTxnLimit");
						}
			 }*/
				regMobileUser.setStatus(status);
			}
				
				
				if (regMobileUser.getUm_ezywayMid() != null && (regMobileUser.getUpdateType().equals("umobileezyway"))) 
				{
					logger.info("UM Ezyway MID :::::::::::::::::.."+regMobileUser.getUm_ezywayMid());
					UMMidTxnLimit umtxnlt = null; //new UMMidTxnLimit();
					umtxnlt= mobileuserDAO.loadDetByMid(regMobileUser.getUm_ezywayMid());
					 String status = "NO";
					if(umtxnlt!=null) {
					//if(umtxnlt.getMid()!=null) {
						
						//if(umtxnlt.getMid().equals(regMobileUser.getUm_ezywayMid()) && (!umtxnlt.getMid().isEmpty())) {
							logger.info("To Update TXN limitt"+umtxnlt.getMid());
							
							
							//umtxnlt.setActivateDate(new Date());
							//umtxnlt.setStatus(CommonStatus.ACTIVE);
							umtxnlt.setMid(regMobileUser.getUm_ezywayMid());
							//umtxnlt.setCreatedBy("Admin");
							//umtxnlt.setCreatedDate(new Date());
							//umtxnlt.setHashKey(regMobileUser.getHashkey1());
							umtxnlt.setDtl(regMobileUser.getDTL1());
							
							
							logger.info("update UMMidTxnLimit save Db" +umtxnlt.getHashKey()+"::"+umtxnlt.getDtl());
							
							
							umtxnlt = umMidTxnLimitDAO.saveOrUpdateEntity(umtxnlt);
							status = "YES";
							/*int updateDet = mobileuserDAO.updateUMTxnLimit(regMobileUser.getHashkey(), regMobileUser.getDTL(),regMobileUser.getUm_motoMid());
							
							if (updateDet != 0) {
								logger.info("ezyway device details successfully updated in UMMidTxnLimit");
							}
							*/
							
							
						}/*else {
							umtxnlt = new UMMidTxnLimit();
							logger.info(" To Save TXN limit");
							umtxnlt.setActivateDate(new Date());
							umtxnlt.setStatus(CommonStatus.ACTIVE);
							umtxnlt.setMid(regMobileUser.getUm_ezywayMid());
							umtxnlt.setCreatedBy("Admin");
							umtxnlt.setCreatedDate(new Date());
							umtxnlt.setHashKey(regMobileUser.getHashkey1());
							umtxnlt.setDtl(regMobileUser.getDTL1());

							logger.info("save UMMidTxnLimit save Db" +umtxnlt.getHashKey()+"::"+umtxnlt.getDtl());
							
							
							umtxnlt = umMidTxnLimitDAO.saveOrUpdateEntity(umtxnlt);
							
							if (umtxnlt != null) {
								status = "YES";
								logger.info("ezymoto device details successfully added in UMMidTxnLimit");
							}
				 }*/
				
				regMobileUser.setStatus(status);
			}
			
			return regMobileUser;
		}
		
		public UMMidTxnLimit loadDtlMidDetails(Long id) {
	        
	        return mobileuserDAO.loadDtlMidDetails(id);
	    }
		
		public UMMidTxnLimit Adddtl(final RegMobileUser entity) {
	        
	        return mdrDAO.Adddtl(entity);



	   }
		
		
}

