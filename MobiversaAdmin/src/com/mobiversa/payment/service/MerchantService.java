package com.mobiversa.payment.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.AuditTrailAction;
import com.mobiversa.common.bo.BankUser;
import com.mobiversa.common.bo.BoostDailyRecon;
import com.mobiversa.common.bo.CommonOperationStatus;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.EwalletTxnDetails;
import com.mobiversa.common.bo.EzyRecurringPayment;
import com.mobiversa.common.bo.FileUpload;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.GrabPaymentTxn;
import com.mobiversa.common.bo.HostBankDetails;
import com.mobiversa.common.bo.InternalTable;
import com.mobiversa.common.bo.JustSettle;
import com.mobiversa.common.bo.KeyManager;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantDetails;
import com.mobiversa.common.bo.MerchantInfo;
import com.mobiversa.common.bo.MerchantStatusHistory;
import com.mobiversa.common.bo.MerchantUserRole;
import com.mobiversa.common.bo.MobiLiteMerchant;
import com.mobiversa.common.bo.MobiLiteTerminal;
import com.mobiversa.common.bo.MobileOTP;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.MotoTxnDetails;
import com.mobiversa.common.bo.PayoutBankBalance;
import com.mobiversa.common.bo.PayoutDepositDetails;
import com.mobiversa.common.bo.PayoutDetail;
import com.mobiversa.common.bo.PayoutGrandDetail;
import com.mobiversa.common.bo.RefundRequest;
import com.mobiversa.common.bo.SettlementBalance;
import com.mobiversa.common.bo.SettlementMDR;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.bo.TransactionRequest;
import com.mobiversa.common.bo.UMEcomTxnResponse;
import com.mobiversa.common.bo.UMMidTxnLimit;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.controller.bean.ResponseDetails1;
import com.mobiversa.payment.controller.bean.SubmerchantApi;
import com.mobiversa.payment.dao.AgentDao;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dao.MobileUserDao;
import com.mobiversa.payment.dao.SubAgentDao;
import com.mobiversa.payment.dto.LoginResponse;
import com.mobiversa.payment.dto.MerchantDTO;
import com.mobiversa.payment.dto.MerchantGPVData;
import com.mobiversa.payment.dto.MerchantPaymentDetailsDto;
import com.mobiversa.payment.dto.MerchtCustMail;
import com.mobiversa.payment.dto.ProductWiseAmount;
import com.mobiversa.payment.dto.RegAddMerchant;
import com.mobiversa.payment.util.EmailUtils;
import com.mobiversa.payment.util.MobiliteTrackDetails;
import com.mobiversa.payment.util.PayeeDetails;
import com.mobiversa.payment.util.Payer;
import com.mobiversa.payment.util.PayoutModel;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.PropertyLoader;
import com.mobiversa.payment.util.RandomPassword;
import com.mobiversa.payment.util.SendSMSMessage;
import com.postmark.java.Attachment;
import com.postmark.java.EmailTemplet2;
import com.postmark.java.MerchantActivation;
import com.postmark.java.MsgDto;
import com.postmark.java.NameValuePair;
import com.postmark.java.PostmarkClient;
import com.postmark.java.PostmarkException;
import com.postmark.java.PostmarkMessage;
import com.postmark.java.PostmarkResponse;
import com.postmark.java.TempletFields;
import com.sun.mail.iap.ConnectionException;

@Service
public class MerchantService {// extends BaseDAOImpl {

	@Autowired
	protected SessionFactory sessionFactory;

	@Autowired
	private MerchantDao merchantDAO;

	@Autowired
	private AgentDao agentDAO;

	@Autowired
	private SubAgentDao subAgentDAO;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private MobileUserDao mobileuserDAO;

	private static final Logger logger = Logger.getLogger(MerchantService.class.getName());

	/**
	 * Load a merchant based on Primary Key (PK)
	 * 
	 * @param id merchant table's primary Key
	 */
	public Merchant loadMerchantByPk(final Long id) {
		Merchant merchant = merchantDAO.loadEntityByKey(Merchant.class, id);
		if (merchant == null) {
			throw new RuntimeException("Merchant Not found. ID:: " + id);
		}
		return merchant;
	}

	public Merchant loadMerchantBym_fk(Long id) {
		Merchant merchant = merchantDAO.loadEntityByKey(Merchant.class, id);
		if (merchant == null) {
			throw new RuntimeException("Merchant Not found. merchant fk_ID:: " + id);
		}
		return merchant;

	}

	/**
	 * Load a merchantstatushistory based on Primary Key (PK)
	 * 
	 * @param id merchant table's primary Key
	 */

	public MerchantStatusHistory loadMerchantStatusHistoryByPk(final Merchant merchant)

	{
		return merchantDAO.loadMerchantStatusHistoryID(merchant);
	}

	public void searchMerchant(final String businessName, final PaginationBean<Merchant> paginationBean) {
		merchantDAO.findByUserNames(businessName, paginationBean);
	}

	public Boolean updateMerchantByPk(final Long id, final Merchant merchant) {

		Boolean status = false;
		merchant.setId(id);
		try {
			Merchant modifiedMerchant = merchantDAO.saveOrUpdateEntity(merchant);
			status = true;
		} catch (HibernateException e) {
			status = false;
		}
		return status;
	}

	/**
	 * List all the bank users
	 * 
	 * @param paginationBean
	 * @param currentMerchant
	 * @param toDate
	 * @param fromDate
	 */
	// @javax.transaction.Transactional
	public void listRecurringMerchant(final PaginationBean<EzyRecurringPayment> paginationBean,
			Merchant currentMerchant, String fromDate, String toDate) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		// logger.info("mid: "+currentMerchant.getMid().getMid()+" motoMid:
		// "+currentMerchant.getMid().getMotoMid());
		// criterionList.add(Restrictions.ne("status",null));
		criterionList.add(Restrictions.or(Restrictions.ne("status", ""), Restrictions.isNotNull("status")));
		criterionList.add(Restrictions.in("mid", new String[] { currentMerchant.getMid().getMid(),

				currentMerchant.getMid().getMotoMid(), currentMerchant.getMid().getEzyrecMid() }));

		if (fromDate != null && toDate != null) {
			criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
		}
		// criterionList.add((Restrictions.eq("status", CommonStatus.CANCELLED)));

		merchantDAO.listRecurringMerchantUser(paginationBean, criterionList);
	}

	public void listMotoTxnReqMerchant(final PaginationBean<MotoTxnDetails> paginationBean, String fromDate,
			String toDate) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		if (fromDate != null && toDate != null) {
			criterionList.add(Restrictions.between("timestamp", fromDate, toDate));
		}
		// criterionList.add((Restrictions.eq("status", CommonStatus.CANCELLED)));

		merchantDAO.listMotoTxnReqMerchantUser(paginationBean, criterionList);
	}

	public void listMotoReqMerchant(final PaginationBean<MotoTxnDetails> paginationBean, String fromDate, String toDate,
			String status) {

		merchantDAO.loadReqMotoData(paginationBean, fromDate, toDate, status);
	}

	public void merchantListRecurring(final PaginationBean<EzyRecurringPayment> paginationBean, String fromDate,
			String toDate) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		// logger.info("mid: "+currentMerchant.getMid().getMid()+" motoMid:
		// "+currentMerchant.getMid().getMotoMid());
		// criterionList.add(Restrictions.ne("status",));

		criterionList.add(Restrictions.or(Restrictions.ne("status", ""), Restrictions.isNotNull("status")));
		if (fromDate != null && toDate != null) {
			criterionList.add(Restrictions.between("timeStamp", fromDate, toDate));
		}
		// criterionList.add((Restrictions.eq("status", CommonStatus.CANCELLED)));

		// merchantDAO.merchantListRecurring(paginationBean, criterionList);
		merchantDAO.listRecurringMerchantUser(paginationBean, criterionList);
	}

	public EzyRecurringPayment loadMerchantRecurring(final Long recId) {
		logger.info("login person serarching loadmerchant: " + recId);
		return merchantDAO.loadMerchantRecurring(recId);
	}

	/*
	 * public int UpdateRecurringStatus(final Long recId,String status) {
	 * logger.info("login person serarching loadmerchant: "+recId); return
	 * merchantDAO.UpdateRecurringStatus(status,recId); }
	 */

	public EzyRecurringPayment UpdateRecurringStatus(EzyRecurringPayment ezyrec) {
		logger.info("login person serarching loadmerchant: " + ezyrec.getId());
		// return merchantDAO.UpdateRecurringStatus(status,recId);
		return merchantDAO.saveOrUpdateEntity(ezyrec);
	}

	public void listMerchant(final PaginationBean<Merchant> paginationBean) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		// criterionList.add(Restrictions.eq("status", CommonStatus.ACTIVE));
		criterionList.add(Restrictions.not(Restrictions.eq("status", CommonStatus.CANCELLED)));
		criterionList.add(Restrictions.not(Restrictions.eq("status", CommonStatus.PENDING)));
		criterionList.add(Restrictions.not(Restrictions.eq("status", CommonStatus.SUBMITTED)));
		criterionList.add(Restrictions.eq("role", MerchantUserRole.BANK_MERCHANT));

		// merchantDAO.listMerchantUser(paginationBean, criterionList);

		merchantDAO.listMerchantUserByMid(paginationBean);
	}

	public void listMerchants(final PaginationBean<MerchantDTO> paginationBean) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		// criterionList.add(Restrictions.eq("status", CommonStatus.ACTIVE));
		criterionList.add(Restrictions.not(Restrictions.eq("status", CommonStatus.CANCELLED)));
		criterionList.add(Restrictions.not(Restrictions.eq("status", CommonStatus.PENDING)));
		criterionList.add(Restrictions.not(Restrictions.eq("status", CommonStatus.SUBMITTED)));
		criterionList.add(Restrictions.eq("role", MerchantUserRole.BANK_MERCHANT));

		// merchantDAO.listMerchantUser(paginationBean, criterionList);

		merchantDAO.listMerchantUsersByMid(paginationBean);
	}

	public TransactionRequest listMerchantCardDetails(BigInteger TxnId) {

		return merchantDAO.listMerchantCardDetails(TxnId);
	}

	public void doSuspendMerchant(final Long id, final String reason, final String suspendDescription) {
		Merchant merchant = loadMerchantByPk(id);
		if (!CommonStatus.ACTIVE.equals(merchant.getStatus())) {
			// merchant status isn't active, then how do we suspend merchant?
			throw new RuntimeException("unable to suspend a non-active merchant");
		}

		CommonStatus status = CommonStatus.SUSPENDED;

		MerchantStatusHistory history = new MerchantStatusHistory();
		history.setMerchant(merchant);
		history.setCreatedDate(new Date());
		history.setReason(reason);
		history.setDescription(suspendDescription);
		history.setStatus(status);
		history.setUserId(merchant.getUsername());
		merchantDAO.updateMerchantStatus(id, status, history);
	}

	public void doUnSuspendMerchant(final Long id, final String reason, final String unSuspendDescription) {
		Merchant merchant = loadMerchantByPk(id);
		if (!CommonStatus.SUSPENDED.equals(merchant.getStatus())) {
			// merchant status isn't suspend, then how do we active merchant?
			throw new RuntimeException("unable to unsuspend a active merchant");
		}

		CommonStatus status = CommonStatus.ACTIVE;

		MerchantStatusHistory history = new MerchantStatusHistory();
		history.setMerchant(merchant);
		history.setCreatedDate(new Date());
		history.setReason(reason);
		history.setDescription(unSuspendDescription);
		history.setStatus(status);
		history.setId(id);
		merchantDAO.updateMerchantStatus(id, status, history);
	}

//add merchant methods
	public Merchant addMerchant(final Merchant entity)

	{ /* ,final Agent entity1 */
		// new changes 09052016//
		/*
		 * System.out.println("agentname:" +entity1.getUsername()); Agent agent =
		 * agentDAO.loadAgentbyMailId(entity1.getUsername());
		 */

		/*
		 * String sql = null;
		 * 
		 * sql="select id from agent where  ='"+ agentName +"'"; logger.info("Query : "
		 * + sql); Query sqlQuery = super.getSessionFactory().createSQLQuery(sql);
		 * 
		 * @SuppressWarnings("unchecked")
		 */

		// System.out.println("entity" + entity.getAgID());
		String agentData = entity.getBusinessShortName();
		// String agentData = entity.getSignedPackage();
		String mailId = null;
		// if(agentData != null || !(agentData.equals(""))){
		if (!(agentData.equals(""))) {
			String agentmail[] = agentData.split("~");
			// System.out.println("Agent Data "+agentmail);
			mailId = agentmail[1];
		}
		// System.out.println("Agent Mail : "+ mailId);
		Agent agent = agentDAO.loadAgentbyMailId(mailId);

		// System.out.println("Agent Data : "+ agent.getId());

		Merchant merchant = new Merchant();

		BigInteger bi = new BigInteger(agent.getId().toString());
		// System.out.println("Data sfsfsfdf : "+ bi);
		entity.setAgID(bi);
		// entity.setSignedPackage("");
		entity.setBusinessShortName("");

		logger.info("merchant :" + entity.getFirstName());
		entity.setEnabled(true);
		logger.info("merchant :" + entity.getEmail());
		entity.setUsername(entity.getEmail());

		// logger.info("merchant :"+entity.getUsername());

		entity.setStatus(CommonStatus.ACTIVE);
		/*
		 * Date date = new Date(); String activateDate= new
		 * SimpleDateFormat("yyyy-MM-dd").format(date); logger.info("merchant:" +
		 * entity.getActivateDate());
		 */
		entity.setActivateDate(new Date());
		// logger.info("merchant:" + entity.getActivateDate());
		String genPwd = new RandomPassword().generateRandomString();
		// logger.info("code1 :"+genPwd);
		// entity.setPassword("abc123");
		MID mm = new MID();
		String mid = entity.getNricAcc();
		// String mid = entity.getWaiverMonth();
		// BigInteger bi1 = new BigInteger(merchant.getId().toString());
		// logger.info("middd :"+mid);

		if (mid.length() < 15) {
			for (int i = mid.length(); i < 15; i++) {
				mid = "0" + mid;
			}
		}
		// logger.info("middd :"+mid);
		mm.setMid(mid);

		entity.setMid(mm);
		// logger.info("ddmid :"+mm.getMid());
		entity.setNricAcc("");
		// entity.setWaiverMonth("");
		entity.setPassword(encoder.encode(genPwd));
		// logger.info("code1 :"+entity.getPassword());
		entity.setRole(MerchantUserRole.BANK_MERCHANT);
		/* entity.setAgId(entity.()); */

		merchant = merchantDAO.saveOrUpdateEntity(entity);

		logger.info("code1 :" + merchant.getId() + "  : " + merchant.getMid().getId());

		// logger.info("code1 :"+merchant.getId()+" :
		// "+merchant.getMid().getMerchant().getId());

		/*
		 * entity.getWaiverMonth(); MID mm = new MID();
		 * mm.setMid(entity.getWaiverMonth()); mm.
		 */

		// MID mid=merchantDAO.saveOrUpdateEntity(mm);
		// System.out.println(" Merchant id : "+merchant.getId());
		// String mid = entity.getWaiverMonth();
		// System.out.println(" Merchantttt id : "+mid);
		// BigInteger bi1 = new BigInteger(merchant.getId().toString());
		int updt = merchantDAO.updateMIDData(merchant.getMid().getId(), merchant.getId());

		// logger.info(" Data : "+ updt);

		TempletFields tempField = new TempletFields();

		tempField.setFirstName(entity.getFirstName());
		tempField.setLastName(entity.getLastName());
		tempField.setUserName(entity.getUsername());
		tempField.setPassword(genPwd);
		tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));

		List<NameValuePair> headers = new ArrayList<NameValuePair>();
		headers.add(new NameValuePair("HEADER", "test"));
		// EZYWIRE AS USERNAME & password mobiversa
		String fromAddress = "info@gomobi.io";
		// String apiKey = "c652b570-9500-4534-8eb6-96a78c10c8b8";
		String apiKey = PropertyLoader.getFile().getProperty("APIKEY");
		String toAddress = entity.getUsername();

		// String toAddress = "karthiga@mobiversa.com";
		/*
		 * String ccMail="ethan@mobiversa.com"; String bccMail =
		 * "premkumar@mobiversa.com";
		 */

		String ccMail = PropertyLoader.getFile().getProperty("MOBILEUSER_ADD_CCMAIL");
		String bccMail = PropertyLoader.getFile().getProperty("MOBILEUSER_ADD_BCCMAIL");

		// String subject = "Account Creation Mail";// set
		String subject = PropertyLoader.getFile().getProperty("WEBMAIL_SUBJECT");

		String emailBody = EmailTemplet2.sentTempletContent(tempField);
		// String emailBody = MerchantCreation.sentEamil(tempField);
		/*
		 * Attachment logo = new Attachment( "mobiversa_logo1.jpg", "image/jpg",//"",
		 * "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"
		 * +
		 * "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kGvJzbLY5phnRvaW6fZns5Nmkspxar2vHZruv8ANbn6qftEfBOz+Ofw9uNHfZBq1uTcabdt/wAspgMbSf7jD5WH0PUCvyj8QeH9R8K61e6Rq1pLYalZyGKe3mXDIw/zkHoQQa+i9A/4KDfEzSY1jvrfRNaA6yXNo0ch49Y3Vf8Ax2uL+NX7RFp8coYp9Z8E6dY69FGI01jT7iRJcD+FlIIdc9jyOxGTXh5Hg8yytvD1oqVN9U9n87aH0GfY3K82SxNCTjVXRp6r5X1R5PpOrNpLXTIpLTW7wBlfaV3Y5rWuPGTS6JBaRxTQXUcSQmSOfEbBH3q2wDO7Pq2O+M1zNFfaOKbuz4GyOtvPHCahcagJ7Hy7S9gWN47d9jK+8Ss4OCPmkycY6EDsKnf4ifarh2uLEmKWW5MgSbDeXLAkOASp+YBM7u5PQVxdFTyRFyo66TxtbyRzJ/Z8ir/oPlD7QCV+zoUG47OdwPYDFLN48WYPL9hZLoLdRRkTfu9k7MW3rt+YrvODkZ+XjjnkKKOSIcqOz/4WIftLSmwJzMsoXz+mLcw4+777vwx71Xs/GkGnnSVttPkigsD5hh+0DE0hBDSN8mcnOB6AYrlKKOSIcqO10fxtGbzT4biP7LAjWKNOZCwjEDElsBe+enb3qr461DT757FbCbcsKyL5KvvjiBbf8rFEJJZnJ44457DlKKORJ3Qcqvc6/wD4TqKPUba+jsZRNuVriNrk+UxERiOxdvykqc5O4g4xgda0njBVs1s4LaRbeO3W2jaSbL4E/nEkgDOTkAdhjrXM0UckQ5UddcePXuHv2Now+1S30g/fZ2C4CDb93nbs/HPapY/iI8d0s32EPia1lKvLniK3aEjpwW3Fs9j61xlFHJHsHKjo9R8WG4t7q3gS4SKSGOBGmuN7qqOz4OFAx82MAADHc81dXx1FFqNtfR2EglBU3EbXR8p8RGIlFx8pKnOTuIIGOM54+inyIfKjT13VV1a7SRBMIo41iT7RL5j7QTjJAA79AAAAPqcyiiq20GFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAf/2Q==",
		 * "cid:mobiversa_logo1");
		 * 
		 * Attachment faceBook = new Attachment( "mobi_facebook.jpg", "image/jpg",
		 * "iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAmlJREFUeNrkWr1KA0EQ3hxpbBQEBbHwtAiWwVYhBgTTJUIKu1zhA+QBLFL4AMkbJL2S+AReXkBsBG3kYmMhKqc2VnEmmQvneZfcz+5lVweGwHGZ/b6dn/2ZywyHQ5ZUqqdX+/DjqA66EfDqANQCNVHPz4pm0rEzcQkQaAO0AroUc3wbtAfajksmMgEC3gAtML7SR7tRiYQmAMAxNJqgZSZWLkHrQMTiRgDAY5i0E4RKnNAygERv1otaCPA4690UwTMaq0tjx/cAGMBZr7H5Sgc8YUT2gCTgUWqEJTwBcp0M4N0kmqFCiBK2y+SUI29i/yBApfIm5YSNWp3y7hLrDaGmxOCd6tT09QCtsFc8R9vRl1n1YJPl9EW/PEtiuuis2FnXwwZP8CelHCvtrYvyRIM2juMQotnntrfZ3V4VCR6lQJgnOWDwtH58uJVGPhjuEKrwtLy2svDr2fXtC7swLXb/9M5rmBFmjVwhvPJwBj+qSIhdc5JBtLx+fokwmx6B5w9xBHSmrujZKQfw0AKLSuR3Ei5kjmxo85g2rEi8ZC4Ebh/e1CZw92hzs5XlYcQbz345wSnm5fAAbwIDhfEPkIClMAELCZgKEzDVJ0BHM1tB8DZid6pQT0ECPXcZbStIoD0hQGHUVwh837mV0ETdSgiWCVbvzRzGVVly8Jcw+5WgrURd8opkE0b/vRDdORoSEzC8rSfN5+SEYdSSEHzLr+UU2KGRqMGBEtil0aacYfEPHZnBzzwP0B9bcw6bqTn599usrsTOs3ETWnidZ+MuTKj92f/51CCACManWh97TCHjqM5S/NzmW4ABAKyPAl50Q17BAAAAAElFTkSuQmCC",
		 * "cid:mobi_facebook");
		 * 
		 * 
		 * Attachment twitter = new Attachment( "mobi_twitter.jpg", "image/jpg",
		 * "iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAA7FJREFUeNrcWktPE1EUPjOt0PKwSkJSI5GiJMYdkZ0LrSZucCGGH0AXxp0JS5ddumzduikLdxIgUVYay8YNUVwQQyKPQnglJmCRdiptrecMd5ph2mnvnRmGqV9yQmjnzv2+ex73ValSqYBdvPhyGMU/mkXQ+k0e3UDLoKXJXg5fTNvtW7IqgJGOoY2ihSz2n0WbQUtZFSMsgBGPo90DZzFP7xUVwi0AiVNoJNAew9liFm0ChWQcE4DkKUxSNkLFSmjFUMRMswdlDvI06tMukgfW1zTr27oH8AU06uNwvphET8SEPeAR8oRxxoVfAHOdF8jrRSS4Qogl7DR4E0+MiX1KACuV31xOWNHqNKQvscYQSniYvFadEnU9wGbYT9AauK/N2HoPxN3ouadNgg6f7dfET3lAdPSJQL7M31tfUIaRqwG4HvJXPytg+/ebCizsF6vCbnT5q//zekF7Y0xE/vNb3fBmNQdbyt+mzw52+eDpzc6azwM4CGMDQQj6JAgHfXC79wLMbRZEaBDntBZCo7ytiNDldglJdakj2wxjkY6G349cC6jkD/5UYEcpi4SXyllm4SNceWgEScTd3raGoUNieUDPPeoLiIRmiLjLbBdlCSSCRvDZYIfqmdrvJe537ebK8PpHTpRC1C8qgNxsBCUn2Vq2BIuYhKtHJdg/rgiRTy7nrIyhKiAi0oJc/HnvGO6E20yFaFXmoMAXD0rJ8r48IjfYgJvGdZgjeSm8rnTaL/hN0C9baaWv505gj6McW96RGUG1n0qek9hVyu4JIExl8o4KoKR3VcDKURmm1hVHPEGVS6RiOSKAMIDrFt5JqhEW+dc+pgI2rDT8sFeAQtn+6C/YE7BBAjJWWpLbaTVpR8TcdsGuAzMkIG21NY3eq++/4eP2sXA+UA5t2SifDGlbAvQI+iUh8jZDpypA29D84l2RPgwHTgjjJDvQ7ROabSnc3q7lYemw5AT5LG5oLmlT6gzvOdD6UREehNuFZ+OvP4vwblsR2sk1gXq8orFI8QqgOWBlJa9uAYd72rGcynXF0Gjv4AS1jJVmKVu0VetNkDKeSlAuOH3mf1aYx/CJnsuphEOI18zE7JxltgXIz+pvcYxLiQk4Ob7zKrKMY/21EDtzjHlYQMx49VSzmGOnv0kPkk/Wu3IyvaHx0AUHwfSWxnQ5zRpMepl80/0Aa5g857BpmJP//zWrLrGHXJonqI8hHvLcHjB4Iwqt+FMDEyEUn631Y48GYjSLgIs/t/knwACo4Jk88RURpAAAAABJRU5ErkJggg==",
		 * "cid:mobi_twitter");
		 * 
		 * Attachment link = new Attachment( "mobiversa_link.jpg", "image/jpg",
		 * "iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAABAxJREFUeNrkWl1IFFEUPq7rT65pC66JYrtYbA8paVhUFCoUFEEp1EP44DzZS5SvBYEQve9GT/ayPvSSD7sGkhDhLkFFSRi5hZK1S1iZha6kYCHbPeO9w8w4s+6dnVmnOnDYv5k733fvOd89994tSKfTkKtdGLvTTl6Y+4h7dS5NEk8Qj6IPdVyO5vrsAqMEKGiBeCfxSoPPTxGPEA8ZJcNNgALvJ94G5loM2+UlkjUBAhxDI0D8HFhrw8T7CJGEaQQIeAyTUA6hYiS0BEIistmFjizAY6+H8wge6LPC9NnGR4A0gL3eA1trg2QkBO4RsAl4tB6KJXsCdOjsAF5OIpBVCNGEDYM9rUud2AoCVCon8pywvOrULJdYdQgFbAyeqVNAcwToDDuWDxQVhcWwtPYrlyY62IztlH3ZbyVov8sNXd6D0LrTL333bWURIh+ew6P597zN9dPCcX0ErO79k5490Nt4Svf3J7Nv4PZ0zNAosBwQ8gk+kfoKy79Xpc/H65rE6zhNkIdQp1UEuvd2KIDfnXoM08sLYh4Iu4+I4EUADYd5Q0nEXBhvq8LwuWQVgbc/PkKTexdGKwQnR0TwaKvpNXi3+AVO1DZCcaETXEWlMJR4wdN0Kbk+5mTJYJUh4Gvj92G7swhmV5cVv6ESza8sgKuyxmjz7ZYQOOquB2951foa8ud3eLrwSVM260pc4DMOXiLgM1MqrzaegeqyHYrve0nCDsRHRSLyuaBv32np8/jctJFH+hwZFuDcanPrUPcG8GiuohJo9TQowN/Yf1bR++HkSyOP9TqskEqUSNR2VB21zmuBH5gclZKb15xmg0fQgfhDKWExztl7PfAGZmJzCGiBv/n6gSJhrQSfE4HNwF/xt0F9uQdezc+IOXCstkl8NRO8YQKoNpnAX6xvkWZYLZk0CzwjkORVIqwqM4XNyOc4eLZVSCTk1ad8NjbBkkggwUtAXhJjwqonKfyMqhOaeQYNZLSYTSzNmT1nJpAALgwMbRNi76vLA5awNaUusactAC03sZyOGr3bU+YWwarBo9pcbzkv5orFFnXQpVmK5y6MZTbDYknMSCBgJpX4G5JQEzRzgY/YmQpFePaBcBnIVAgT9UC1X6wq1Ypzb2os17VvRhjyXYkQz50ogVgeyGsdNXgzpVLHQupdCe5kxskMV1LyAg6rSizMTJRKLYuR8GnX2pUY4x0J1ssmbJXw7kooN7ZoMg8bbTGP4IflpzjqcrqPV5HybCmKETQJ0D1HwcYEBPXR04YFDd39DdoQfFDryEn3hMZGBxxouqc0uktKesOgncFnJCAjEdzisMmYk//+MasssZtzmSd4dB7WT2Ei2Vz8//zVQIcIxuff9WePDGSY+yCPf7f5I8AA9ij5OQxvFDsAAAAASUVORK5CYII=",
		 * "cid:mobiversa_link");
		 * 
		 * Attachment linkedIn = new Attachment( "mobi_linkedin.jpg", "image/jpg",
		 * "iVBORw0KGgoAAAANSUhEUgAAADAAAAAwCAYAAABXAvmHAAAACXBIWXMAAAsTAAALEwEAmpwYAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAuNJREFUeNpi/P//PwOlQKRmpwOQgmEFIJbHofQhED8A4gMg/KbF/QCldjOS6wGooxOAOACI+cm0/yMQbwDiBeR6hmQPQB3eAMT2DNQFB0HmkuoRoj0AdDgoaUwAYn8G2oKNQFwA9MgDqnkA6HhQMllAQVIhJ2klAD2xgZBCJiIcDwr19XR0PAPUrvVQu8mPAaABoFCPZxhYsBAYEwkkx8AgcTwIxEPdQrwHoFE3GByP7IkJRCUhaIZdzzA4QSB6xkbxALSovEDnDEtq6WSAXMSiJ6EJg9jxsNJpAtYYgNaw+4kxJUVNnKEyWJuBn5uV4ePX3wzta68yzLn1kp4ecYTV2Mgx0ECsbpjjwUECpEF8OoMGlCQEDX2i2zYwx+Pi0wHYQ90Mj4EEknISMNng49MJJCB7IIAUnaA0D3M0LA8MAAC7mVG4egfRmXcQAkcmaC9qqAIHFnI8ACzCsDU/8MprNe1lSNeXZnDWk2DQURRAkVt16CHD1GP3Ga5++UmWBxToEVSrYo0xHA4DYXbyDO7GUgxxM08wHH33jRRjFZjwdMCpCnA5HrkoXpRuwSDGxkKKsfJMgylBgzwBSmakABZ6OQ5W3D779B3MN5YVZMj318AsGy1kGZpPPxx8HqhadpFh5cO3cP62F5/ANLon5MW5STKXbklo//OPGGJnH7+n2Fy6eeDVrz8YYmdIK3FweuDhQGVabJ4iETwEeeDBEK6JH4A8cGAIe+DA0PcAtGv2cQg6/iPI7bBSaMMQ9MAG5GJ0wRD0wAL0UYkDDNQf86cVOAhMPg5kj0oMAtCAURNDM/PGIeD4jcizOOhNiYJBXiJ9hLoRe1sIOuaYMIg9kIA+9cSEpb8LKp4mDkLHT8Q25YRzhmYQTXCAAM5ZGiY8Iw8gDQsHs+MJ9gegGicOcLLBmyeH/zQrUsY2oFM9AbLDgBjHEx0DaLHhwDAUlxrg8AgofQ6txR54PAPDCgx0XG4DEGAAJEwuWRxfuw0AAAAASUVORK5CYII=",
		 * "cid:mobi_linkedin");
		 */

		Attachment logo = new Attachment("mobiversa_logo1.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("NEW_LOGO"), "cid:mobiversa_logo1");

		Attachment faceBook = new Attachment("mobi_facebook.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("FACEBOOK"), "cid:mobi_facebook");

		Attachment twitter = new Attachment("mobi_twitter.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("TWITTER"), "cid:mobi_twitter");

		Attachment link = new Attachment("mobiversa_link.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("LINK"), "cid:mobiversa_link");

		Attachment linkedIn = new Attachment("mobi_linkedin.jpg", "image/jpg",
				PropertyLoader.getFile().getProperty("LINKEDIN"), "cid:mobi_linkedin");

		Attachment webLogo = new Attachment("webLogo.jpg", "image/jpg", PropertyLoader.getFile().getProperty("webLogo"),
				"cid:webLogo");

		List<Attachment> attachments = new ArrayList<Attachment>();
		attachments.add(logo);
		attachments.add(webLogo);
		attachments.add(faceBook);
		attachments.add(twitter);
		attachments.add(link);
		attachments.add(linkedIn);

		/* bccMail, */
		PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress, fromAddress, bccMail, ccMail, subject,
				emailBody, true, "test-email", null, attachments);
		PostmarkClient client = new PostmarkClient(apiKey);
		if (!(entity.getUsername() == null || entity.getUsername().equals(""))) {
			try {
				client.sendMessage(message);
				logger.info("Email Sent Successfully to" + entity.getUsername());
			} catch (PostmarkException pe) {
				logger.info("Invalid Signature Base64 String");

			}
		}

		return merchant;
	}

	public MerchantDetails loadMerchantDetails(Merchant currentMerchant) {
		return merchantDAO.loadMerchantPoints(currentMerchant);

	}

	public MerchantDetails uploadMerchantPdfTC(MerchtCustMail entity, Merchant merchant)

	{
		MerchantDetails merchantDetails = merchantDAO.loadMerchantPoints(merchant);
		if (merchantDetails != null) {
			// merchantDetails.setMerchantId(entity.getMerchantId());
			merchantDetails.setMerchantName(merchantDetails.getMerchantName());
			entity.setMerchantName(merchantDetails.getMerchantName());
			// merchantDetails.setMid(merchantDetails.getMid());
			merchantDetails.setMid(entity.getMid());
			logger.info("mid: " + entity.getMid());
			logger.info("path: " + entity.getmFilepath());
			/*
			 * if(entity.getCustMailList()!=null) {
			 * merchantDetails.setCustMailList(entity.getCustMailList());}
			 */

			merchantDetails.setTermsCondsPath(entity.getmFilepath());

			merchantDetails = merchantDAO.saveOrUpdateEntity(merchantDetails);
		}

		return merchantDetails;

	}

	public void updateMerchant(final Merchant merchant) {

		// logger.info("Service : about to list all merchant1");
		merchant.setStatus(CommonStatus.ACTIVE);
		// logger.info("Service : about to list all merchant2");
		merchantDAO.saveOrUpdateEntity(merchant);
		// return merchantDAO.saveOrUpdateEntity(merchant);

	}

	public Merchant loadMerchant(final String username) {
		// logger.info("login person serarching loadmerchant: "+username);
		logger.info("<==MERCHANT DETAILS LOADING DAO==>");

		return merchantDAO.loadMerchant(username);
	}

	public Merchant loadMerchantbyBussinessName(final String username) {

		logger.info("<==MERCHANT DETAILS LOADING DAO==>");

		return merchantDAO.loadMerchantbyBussinessName(username);
	}

	public List<MobileUser> loadMobileUserByFkTid(long merchantId) {

		logger.info("<==Mobile User DETAILS LOADING DAO==>");

		return merchantDAO.loadMobileUserByFkTid(merchantId);
	}

	public void updateBalanceNetAmt(String amount, String merchantId) {
		merchantDAO.updateBalanceNetAmt(amount, merchantId);
	}

	public Payer getPayerAmount(String resDate, Merchant merchant,String status) {
		logger.info("PAYER");
		return merchantDAO.getPayerAmount(resDate, merchant,status);
	}

	public Payer getPayerAmountJust(String resDate, Merchant merchant) {
		logger.info("PAYER");
		return merchantDAO.getPayerAmountJust(resDate, merchant);
	}

//	public List<PayeeDetails> getPayeeDetails(String resDate, Merchant merchant) {
//		logger.info("PAYEE");
//		return merchantDAO.getPayeeDetails(resDate, merchant);
//	}
//	

//	public List<PayeeDetails> getPayeeDetailsJust(String resDate, Merchant merchant) {
//		logger.info("PAYEE");
//		return merchantDAO.getPayeeDetailsJust(resDate,merchant);
//	}
	public String updateFinalStatus(String resDate, Merchant merchant) {
		// logger.info("login person serarching loadmerchant: "+username);
		logger.info("Update");

		return merchantDAO.updateFinalStatus(resDate, merchant);
	}

	/*
	 * public SettlementUser loadSettlementUser(final String username) {
	 * //logger.info("login person serarching loadmerchant: "+username); return
	 * merchantDAO.loadSettlementUser(username); }
	 */

	public MobiLiteMerchant loadMobiLiteMerchant(final String username) {
		// logger.info("login person serarching loadmerchant: "+username);
		return merchantDAO.loadMobiLiteMerchant(username);
	}

	public MobiLiteMerchant loadMobiliteMerchant(final String username) {
		// logger.info("login person serarching loadmerchant: "+username);
		return merchantDAO.loadMobiliteMerchant(username);
	}

	public KeyManager validateCaptcha(String captcha2) {

		return merchantDAO.validatecaptcha(captcha2);

	}

	public boolean deleteCaptcha(String captcha2) {

		// logger.info("check captcha delete...");
		return merchantDAO.deleteCaptcha(captcha2);

	}

	public Merchant loadmobileMerchant(final String mid) {
		// logger.info("loadmobilemerchant: "+mid);
		MID mid1 = merchantDAO.loadMidtoUpdateAudit(mid);

		return merchantDAO.loadmobileMerchant(mid1);
	}

	public Merchant loadMerchantByMid(final String mid) {
		return merchantDAO.loadMerchant(mid);
	}

	public Merchant loadMerchantbymid(final String mid) {
		logger.info("loadmobilemerchant: " + mid);
		MID mid1 = merchantDAO.loadMid(mid);

		return merchantDAO.loadMerchantbyid(mid1);
	}

	public Merchant loadMerchantbyid(final Long id) {
		logger.info("loadmerchant: " + id);
		// MID mid1 = merchantDAO.loadMid(mid);

		return merchantDAO.loadMerchantbymerchantid(id);
	}

	public MobileUser loadBankByMerchantFk(Merchant merchant) {
		logger.info("load bank by merchant fk id: " + merchant.getId());
		// MID mid1 = merchantDAO.loadMid(mid);

		return merchantDAO.loadBankByMerchantFk(merchant);
	}

	public HostBankDetails loadBankBySellerId(MobileUser mUser) {
		logger.info("load bank by seller id: " + mUser.getFpxSellerId());
		// MID mid1 = merchantDAO.loadMid(mid);

		return merchantDAO.loadBankBySellerId(mUser);
	}

	// Update Sub Merchant Details added on - 18/10/2021

	public void updateMerchantByNativeQuery(Merchant merchant) {

		logger.info("In UpdateMerchantByNativequery for ID , State , Email , BusinessName , City : " + merchant.getId()
				+ " ---- " + merchant.getState() + " ---- " + merchant.getEmail() + " ---- "
				+ merchant.getBusinessName() + " ---- " + merchant.getCity());
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"update MERCHANT set STATE = :state , EMAIL = :email , BUSINESS_NAME = :businessName , CITY = :city , "
						+ " SALUTATION = :salutation , CONTACT_PERSON_NAME = :contactPersonName , CONTACT_PERSON_PHONE_NUMBER = :contactPersonPhoneNo , "
						+ " TRADING_NAME = :tradingName , WEBSITE = :website , BUSINESS_REGISTRATION_NUMBER = :businessRegistrationNumber , "
						+ " BUSINESS_SHORTNAME = :businessShortName , BUSINESS_TYPE = :businessType , BUSINESS_ADDRESS2 = :businessAddress2 , "
						+ " NATURE_OF_BUSINESS = :natureOfBusiness , POSTCODE = :postcode , COUNTRY =:country , OWNER_SAL =:ownerSalutation ,  "
						+ " OWNER_NAME =:ownerName , OWNER_CONTACT_NUMBER = :ownerContactNo , OWNER_PASSPORT_NO = :ownerPassportNo , "
						+ " BANK_NAME = :bankName , BANK_ACC = :bankAcc where ID = :id");
		query.setParameter("id", merchant.getId());
		query.setParameter("state", merchant.getState());
		query.setParameter("email", merchant.getEmail());
		query.setParameter("businessName", merchant.getBusinessName());
		query.setParameter("city", merchant.getCity());
		query.setParameter("salutation", merchant.getSalutation());
		query.setParameter("contactPersonName", merchant.getContactPersonName());
		query.setParameter("contactPersonPhoneNo", merchant.getContactPersonPhoneNo());
		query.setParameter("tradingName", merchant.getTradingName());
		query.setParameter("website", merchant.getWebsite());
		query.setParameter("businessRegistrationNumber", merchant.getBusinessRegistrationNumber());
		query.setParameter("businessShortName", merchant.getBusinessShortName());
		query.setParameter("businessType", merchant.getBusinessType());
		query.setParameter("businessAddress2", merchant.getBusinessAddress2());
		query.setParameter("natureOfBusiness", merchant.getNatureOfBusiness());
		query.setParameter("postcode", merchant.getPostcode());
		query.setParameter("country", merchant.getCountry());
		query.setParameter("ownerSalutation", merchant.getOwnerSalutation());
		query.setParameter("ownerName", merchant.getOwnerName());
		query.setParameter("ownerContactNo", merchant.getOwnerContactNo());
		query.setParameter("ownerPassportNo", merchant.getOwnerPassportNo());
		query.setParameter("bankName", merchant.getBankName());
		query.setParameter("bankAcc", merchant.getBankAcc());

		query.executeUpdate();

	}

	public Merchant loadMidtoUpdateAudit(final String mid) {
		logger.info("loadmobilemerchant: " + mid);
		MID mid1 = merchantDAO.loadMidtoUpdateAudit(mid);

		return merchantDAO.loadMerchantbyid(mid1);
	}

	public Merchant loadMerchantbyMotoMid(final String motoMid) {
		// logger.info("loadmobilemerchant: "+motoMid);
		MID mid1 = merchantDAO.loadMotoMid(motoMid);
		return merchantDAO.loadMerchantbyid(mid1);
	}

	public Merchant loadMerchantbyEzyRecMid(final String ezyrecMid) {
		logger.info("loadmobilemerchant: " + ezyrecMid);
		MID mid1 = merchantDAO.loadEzyrecMid(ezyrecMid);
		return merchantDAO.loadMerchantbyid(mid1);
	}

	public Merchant loadMerchantbyEzypassMid(final String ezypassMid) {
		logger.info("loadmobilemerchant: " + ezypassMid);
		MID mid1 = merchantDAO.loadEzyPassMid(ezypassMid);
		return merchantDAO.loadMerchantbyid(mid1);
	}

	public Merchant loadMerchantbyumMotoMid(final String umMotoMid) {
		logger.info("loadmobilemerchant: " + umMotoMid);
		MID mid1 = merchantDAO.loadumMotoMid(umMotoMid);
		return merchantDAO.loadMerchantbyid(mid1);
	}

	public Merchant loadMerchantbyumEzywayMid(final String umEzywayMid) {
		logger.info("loadmobilemerchant: " + umEzywayMid);
		MID mid1 = merchantDAO.loadumMotoMid(umEzywayMid);
		return merchantDAO.loadMerchantbyid(mid1);
	}

	/*
	 * public String generatePassword() { String
	 * newPassword=randomPassword.generateRandomString();
	 * 
	 * 
	 * return newPassword;
	 * 
	 * 
	 * }
	 */

	public int changeMerchantPassWord(String Username, String newPwd, String OldPwd) {

		// Merchant merchant = (Merchant)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// boolean matches = encoder.matches(OldPwd, merchant.getPassword());

		Merchant merchant = merchantDAO.loadMerchant(Username);

		boolean matches = encoder.matches(OldPwd, merchant.getPassword());

		if (matches == true) {

			int n = merchantDAO.changeMerchantPassWord(Username, encoder.encode(newPwd), encoder.encode(OldPwd));
			return n;

		} else {
			return 0;
		}
	}

	public int changeMobiliteMerchantPassWord(String Username, String newPwd, String OldPwd) {

		MobiLiteMerchant merchant = merchantDAO.loadMobiliteMerchant(Username);

		boolean matches = encoder.matches(OldPwd, merchant.getPassword());

		if (matches == true) {

			int n = merchantDAO.changeMobiliteMerchantPassWord(Username, encoder.encode(newPwd),
					encoder.encode(OldPwd));
			return n;

		} else {
			return 0;
		}
	}

	public Merchant changeMerchantPassWordByAdminManualy(Merchant merchant) {
		// logger.info("merchant :" + merchant.getUsername()+":
		// "+merchant.getPassword());
		// String newPwd = new RandomPassword().generateRandomString();
		// logger.info("encoded pwd: "+encoder.encode(merchant.getPassword()));
		// logger.info("decoded pwd: "+encoder.encode(arg0));
		// logger.info("merchant username: "+merchant.getUsername()+" password
		// :"+newPwd+"saluation: "+merchant.getSalutation());

		merchant.setPassword(encoder.encode(merchant.getPassword()));

		// int n = merchantDAO.changeMerchantPassWordByAdmin( merchant.getUsername(),
		// encoder.encode(merchant.getPassword()));

		// System.out.println("encder Pwd: "+encoder.encode(merchant.getPassword())));
		// logger.info("status to check: "+n);
		return merchantDAO.saveOrUpdateEntity(merchant);
	}

	public MobileUser changeMobileuserPassWordByAdminManualy(MobileUser mobileUser) {
		// logger.info("mobileuser password "+mobileUser.getPassword()+" :
		// "+mobileUser.getUsername());

		mobileUser.setPassword(encoder.encode(mobileUser.getPassword()));
		mobileUser.setFailedLoginAttempt(0);
		mobileUser.setSuspendDate(null);
		mobileUser.setStatus(CommonStatus.ACTIVE);

		// logger.info("encode Gen PWD : "+mobileUser.getPassword());
		// MobileUser mobileuser = merchantDAO.saveOrUpdateEntity(mobileUser);

		return merchantDAO.saveOrUpdateEntity(mobileUser);
	}

	public String changeMerchantPassWordByAdmin(Merchant merchant) throws JMSException {

		// Merchant merchant=merchantDAO.loadMerchant(Username);
		logger.info("merchant :" + merchant.getUsername());
		String newPwd = new RandomPassword().generateRandomString();

		// logger.info("merchant username: "+merchant.getUsername()+" password
		// :"+newPwd+"saluation: "+merchant.getSalutation());
		int n = merchantDAO.changeMerchantPassWordByAdmin(merchant.getUsername(), encoder.encode(newPwd));

		// Eamil Generate

		/*
		 * TempletFields tempField = new TempletFields();
		 * 
		 * tempField.setFirstName(merchant.getFirstName());
		 * tempField.setLastName(merchant.getLastName());
		 * 
		 * tempField.setUserName(merchant.getUsername()); tempField.setPassword(newPwd);
		 * 
		 * 
		 * logger.info("merchant password changed details:"+merchant.getUsername()+" "
		 * +merchant.getFirstName()+" "+newPwd);
		 * 
		 * tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new
		 * java.util.Date()));
		 */
		MsgDto md = new MsgDto();
		md.setFirstName(merchant.getContactPersonName());
		md.setLastName(merchant.getLastName());
		md.setUserName(merchant.getUsername());
		md.setMerchantEmail(merchant.getEmail());
		md.setPassword(newPwd);
		md.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));

		SendSMSMessage send = new SendSMSMessage();
		try {
			send.sendResetMerchantPasswordEmail(md);
		} catch (ConnectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * List<NameValuePair> headers = new ArrayList<NameValuePair>(); headers.add(new
		 * NameValuePair("HEADER", "test")); //EZYWIRE AS USERNAME & password mobiversa
		 * String fromAddress = "info@gomobi.io"; //String apiKey =
		 * "c652b570-9500-4534-8eb6-96a78c10c8b8"; String apiKey =
		 * PropertyLoader.getFile().getProperty("APIKEY"); String toAddress =
		 * merchant.getEmail();
		 * 
		 * 
		 * 
		 * String ccMail=PropertyLoader.getFile().getProperty( "MOBILEUSER_ADD_CCMAIL");
		 * String bccMail = PropertyLoader.getFile().getProperty(
		 * "MOBILEUSER_ADD_BCCMAIL");
		 * 
		 * // String subject =
		 * PropertyLoader.getFile().getProperty("MERCHANTPASS_SUBJECT");
		 * 
		 * //String emailBody = MerchantResetTemplate.sentTempletContent(tempField);
		 * String emailBody = MerchantResetPwd.sentTempletContent(tempField);
		 */
		// old changes
		/*
		 * Attachment logo = new Attachment("mobiversa_logo1.jpg", "image/jpg",
		 * PropertyLoader.getFile().getProperty("NEW_LOGO"),"cid:mobiversa_logo1");
		 * Attachment logo = new Attachment("mobiversa_logo1.jpg", "image/jpg",
		 * PropertyLoader.getFile().getProperty("BANNER"),"cid:mobiversa_logo1");
		 * 
		 * Attachment faceBook = new Attachment("mobi_facebook.jpg", "image/jpg",
		 * PropertyLoader.getFile().getProperty("FACEBOOK"), "cid:mobi_facebook");
		 * 
		 * Attachment twitter = new Attachment("mobi_twitter.jpg", "image/jpg",
		 * PropertyLoader.getFile().getProperty("TWITTER"), "cid:mobi_twitter");
		 * 
		 * Attachment link = new Attachment("mobiversa_link.jpg", "image/jpg",
		 * PropertyLoader.getFile().getProperty("LINK"), "cid:mobiversa_link");
		 * 
		 * Attachment linkedIn = new Attachment("mobi_linkedin.jpg", "image/jpg",
		 * PropertyLoader.getFile().getProperty("LINKEDIN"), "cid:mobi_linkedin");
		 */

		// new changes
		/*
		 * List<Attachment> attachments = new ArrayList<Attachment>(); Attachment
		 * activationBannerImg = new Attachment( "mobiLogo.png",
		 * "image/jpg",PropertyLoader.getFile().getProperty("ACTIVATIONBANNERIMG"),
		 * "cid:activationBannerImg"); attachments.add(activationBannerImg);
		 * 
		 * PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress,
		 * fromAddress, ccMail, bccMail, subject, emailBody, true, "test-email", null,
		 * attachments); PostmarkClient client = new PostmarkClient(apiKey);
		 * 
		 * try { client.sendMessage(message);
		 * logger.info("CCmail: "+ccMail+" BCCmail: "+bccMail);
		 * logger.info("Email Sent Successfully to: " + merchant.getEmail()); } catch
		 * (PostmarkException pe) {
		 * System.out.println("You must specify a valid 'To' email address.");
		 * 
		 * }
		 */

		// new changes

		return newPwd;
	}

	// @javax.transaction.Transactional
	public Merchant loadMerchantbyEmail(String email) {
		Merchant merchant = new Merchant();
		return merchantDAO.loadMerchantbyEmail(email);
		// return agent;

	}

	// new method
	// @javax.transaction.Transactional
	public List<Merchant> loadMerchant() {

		return merchantDAO.loadMerchant();
	}

	public List<Merchant> loadMerchant1() {

		return merchantDAO.loadMerchant1();
	}

	public void saveSettlement(String settlementAmount, String topupAmt, String id) {
		// TODO Auto-generated method stub
		merchantDAO.savesettlement(settlementAmount, topupAmt, id);

	}

	public SettlementBalance settleMerchant(String id) {
		return merchantDAO.settleMerchant(id);
	}

	public List<Merchant> loadFpxMerchant() {

		return merchantDAO.loadFpxMerchant();
	}

	public List<Merchant> loadMerchantByNOB(String nob) {

		return merchantDAO.loadMerchantByNOB(nob);
	}

	public List<MID> loadMIDByNOB(List<Long> ids) {

		return merchantDAO.loadMIDByNOB(ids);
	}

	/*
	 * public List<MID> loadMIDByNOB(List<MID> ids) {
	 * 
	 * return merchantDAO.loadMIDByNOB(ids); }
	 */

	public List<Merchant> loadMerchantByAdmin() {

		return merchantDAO.loadMerchantByAdmin();
	}

	public RegAddMerchant addMerchant1(final RegAddMerchant entity) {

		String agentData = entity.getAgentName();// getBusinessShortName();
		// String agentData = entity.getSignedPackage();
		String mailId = null;
		String type = null;
		Agent agent = null;
		SubAgent subAgent = null;
		// if(agentData != null || !(agentData.equals(""))){
		if (!(agentData.equals(""))) {
			String agentmail[] = agentData.split("~");
			// System.out.println("Agent Data "+agentmail);
			type = agentmail[0];
			mailId = agentmail[2];
		}
		// System.out.println("Agent Mail : "+ mailId+" "+type);
		if (type.equals("AGENT")) {
			agent = agentDAO.loadAgentbyMailId(mailId);

			// System.out.println("Agent Data : "+ agent.getId());
		}
		if (type.equals("SUBAGENT")) {
			subAgent = subAgentDAO.loadAgentbyMailId(mailId);

			// System.out.println("SubAgent Data : "+ subAgent.getId());
		}

		// new change for agent Name 24/05/2016

		Merchant merchant = new Merchant();
		// RandomPassword rpwd = new RandomPassword();
		BigInteger bi = null;
		if (agent != null) {

			bi = new BigInteger(agent.getId().toString());
			// System.out.println("Data agent : "+ bi);
		}

		BigInteger subAgID = null;
		if (subAgent != null) {
			subAgID = new BigInteger(subAgent.getId().toString());
			// System.out.println("Data subagent : "+ subAgID);
		}

		// System.out.println("Data sfsfsfdf : "+ bi);

		// Merchant merchant = new Merchant();

		merchant.setAgID(bi);
		merchant.setSubAgID(subAgID);
		// System.out.println("Data merchant : "+ merchant.getAgID()+"
		// "+merchant.getSubAgID());
		// entity.setSignedPackage("");
		merchant.setBusinessName(entity.getBusinessName().toUpperCase());
		merchant.setBusinessShortName("");
		merchant.setEnabled(true);
		merchant.setContactPersonName(entity.getName());
		merchant.setSalutation(entity.getSalutation());
		merchant.setContactPersonPhoneNo(entity.getContactNo());
		merchant.setEmail(entity.getEmail());
		merchant.setBusinessAddress1(entity.getBusinessAddress());
		merchant.setStatus(CommonStatus.PENDING);

		merchant.setCreatedDate(new Date());
		// entity.setActivateDate(new Date());
		// entity.setPassword(encoder.encode(entity.getPassword()));

		Merchant merchant1 = merchantDAO.saveOrUpdateEntity(merchant);

		// System.out.println("Agent Data : "+ entity.getFileId() +" MerchantId
		// :"+merchant1.getId());

		if (entity.getFileId() != null) {

			/*
			 * System.out.println(" Inside File Cond"); String fileIds[] = null;
			 * if(entity.getFileId().contains("~")){ fileIds =
			 * entity.getFileId().split("~"); }else{
			 * System.out.println(" Inside File Cond else"); fileIds[0]= entity.getFileId();
			 * }
			 * 
			 * for(int i = 0; i < fileIds.length ; i++){
			 */

			FileUpload fileUpload = loadFileUpload(entity.getFileId());
			// System.out.println(" Inside File loop :"+fileIds[i]);
			// FileUpload fileUpload = loadFileUpload(fileIds[i]);

			fileUpload.setMerchantId(merchant1.getId().toString());

			FileUpload fileUpload1 = merchantDAO.updateFileById(fileUpload);

			// System.out.println("Merchant service MerchantId Data :
			// "+fileUpload1.getFileId());
			// }
		}

		return entity;

	}
	// new method agent portal add merchant 17062016

	// @javax.transaction.Transactional
	public void listMerchant1(final PaginationBean<Merchant> paginationBean, final String agId) {
		BigInteger dfg = new BigInteger(agId.toString());

		// System.out.println("Inside listAllTransaction:::::");
		// ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		if (agId != null) {
			criterionList.add(Restrictions.eq("agID", dfg));
		}

		merchantDAO.listAgentMerchant(paginationBean, criterionList);
	}

	// new changes add merchant 21062016

	public RegAddMerchant addMerchant(final RegAddMerchant entity) throws JMSException // return type need to change as
																						// RegAddMerchant

	{

		logger.info("Merchant Type : " + entity.getMerchantType());
		logger.info("VCC  : " + entity.getVcc());
		logger.info("Pre-Auth  : " + entity.getPreAuth());
		logger.info("Auto Settled  : " + entity.getAutoSettled());
		logger.info("OTP  : " + entity.getAuth3DS());

		logger.info("Paydee MID :");
		logger.info("mid : " + entity.getMid());
		logger.info("ezymotomid : " + entity.getEzymotomid());
		logger.info("ezypassmid : " + entity.getEzypassmid());
		logger.info("ezywaymid  : " + entity.getEzywaymid());
		logger.info("ezyrecmid  : " + entity.getEzyrecmid());

		logger.info("UMobile MID :");
		logger.info("umMid : " + entity.getUmMid());
		logger.info("umMotoMid : " + entity.getUmMotoMid());
		logger.info("umEzypassMid : " + entity.getUmEzypassMid());
		logger.info("umEzywayMid  : " + entity.getUmEzywayMid());
		logger.info("umEzyrecMid  : " + entity.getUmEzyrecMid());

		// Paydee
		String mid = entity.getMid();
		String ezymotomid = entity.getEzymotomid();
		String ezypassmid = entity.getEzypassmid();
		String ezywaymid = entity.getEzywaymid();
		String ezyrecmid = entity.getEzyrecmid();

		// UMobile
		String um_mid = entity.getUmMid();
		String um_motomid = entity.getUmMotoMid();
		String um_ezypassmid = entity.getUmEzypassMid();
		String um_ezywaymid = entity.getUmEzywayMid();
		String um_ezyrecmid = entity.getUmEzyrecMid();

		String agentData = entity.getAgentName();
		String agentMail[] = null;
		if (agentData.contains("~")) {
			agentMail = agentData.split("~");
		}
		String type = agentMail[0];
		String agEmail = agentMail[2];

		logger.info("Agent Type : " + type);
		logger.info("Mail : " + agEmail);

		Agent agent = null;
		SubAgent subAgent = null;
		if (type.equals("AGENT")) {
			agent = agentDAO.loadAgentbyMailId(agEmail);
		}
		if (type.equals("SUBAGENT")) {
			subAgent = subAgentDAO.loadAgentbyMailId(agEmail);
		}
		BigInteger AgID = null;
		if (agent != null) {
			AgID = new BigInteger(agent.getId().toString());
			logger.info("Agent ID : " + AgID);
		}
		BigInteger subAgID = null;
		if (subAgent != null) {
			subAgID = new BigInteger(subAgent.getId().toString());
			logger.info("Sub-Agent ID : " + subAgID);
		}

		Merchant merchant = new Merchant();
		merchant.setAgID(AgID);
		merchant.setSubAgID(subAgID);
		merchant.setBusinessShortName(entity.getRegisteredName());
		merchant.setBusinessName(entity.getBusinessName().toUpperCase());
		merchant.setBusinessRegistrationNumber(entity.getBusinessRegNo());
		merchant.setBusinessAddress1(entity.getRegisteredAddress());
		merchant.setBusinessAddress2(entity.getBusinessAddress());
		merchant.setBusinessAddress3(entity.getMailingAddress());
		merchant.setContactPersonName(entity.getName());
		merchant.setContactPersonPhoneNo(entity.getContactNo());
		merchant.setEmail(entity.getEmail());
		merchant.setSalutation(entity.getSalutation());
		merchant.setUsername(entity.getOfficeEmail());
		merchant.setWebsite(entity.getWebsite());
		merchant.setBusinessContactNumber(entity.getOfficeNo());
		merchant.setFaxNo(entity.getFaxNo());
		merchant.setBankName(entity.getBankName());
		merchant.setBankAcc(entity.getBankAccNo());
		merchant.setState(entity.getBusinessState());
		merchant.setCity(entity.getBusinessCity());
		merchant.setPostcode(entity.getBusinessPostCode());
		merchant.setReferralId(entity.getReferralId());
		merchant.setWaiverMonth(entity.getWavierMonth());
		merchant.setTradingName(entity.getTradingName());
		merchant.setYearIncorporated(entity.getYearIncorporated());
		merchant.setSignedPackage(entity.getSignedPackage());
		merchant.setReaderSerialNo(entity.getNoOfReaders());
		merchant.setResidentialAddress(entity.getRegisteredAddress());
		merchant.setBusinessType(entity.getBusinessType());
		merchant.setCompanyType(entity.getCompanyType());
		merchant.setNatureOfBusiness(entity.getNatureOfBusiness());
		merchant.setPermiseType(entity.getDocuments());
		merchant.setRemarks(entity.getStatusRemarks());
		merchant.setPreAuth(entity.getPreAuth());
		merchant.setAuth3DS(entity.getAuth3DS());
		merchant.setMdr(entity.getMdr());
		merchant.setAutoSettled(entity.getAutoSettled());
		merchant.setMerchantType(entity.getMerchantType());
		merchant.setEzyMotoVcc(entity.getVcc());
		merchant.setAccType(entity.getAccType());

		String ownerSalutation = "";
		String ownerName = "";
		String passportNo = "";
		String residentialAddress = "";
		String ownerContactNo = "";
		if (entity.getOwnerCount() == "2" || entity.getOwnerCount().equals("2")) {
			ownerSalutation = entity.getOwnerSalutation1() + "~" + entity.getOwnerSalutation2();
			ownerName = entity.getOwnerName1() + "~" + entity.getOwnerName2();
			passportNo = entity.getPassportNo1() + "~" + entity.getPassportNo2();
			ownerContactNo = entity.getOwnerContactNo1() + "~" + entity.getOwnerContactNo2();
			residentialAddress = entity.getResidentialAddress1() + "~" + entity.getResidentialAddress2();

		} else if (entity.getOwnerCount() == "3" || entity.getOwnerCount().equals("3")) {
			ownerSalutation = entity.getOwnerSalutation1() + "~" + entity.getOwnerSalutation2() + "~"
					+ entity.getOwnerSalutation3();
			ownerName = entity.getOwnerName1() + "~" + entity.getOwnerName2() + "~" + entity.getOwnerName3();
			passportNo = entity.getPassportNo1() + "~" + entity.getPassportNo2() + "~" + entity.getPassportNo3();
			ownerContactNo = entity.getOwnerContactNo1() + "~" + entity.getOwnerContactNo2() + "~"
					+ entity.getOwnerContactNo3();
			residentialAddress = entity.getResidentialAddress1() + "~" + entity.getResidentialAddress2() + "~"
					+ entity.getResidentialAddress3();

		} else if (entity.getOwnerCount() == "4" || entity.getOwnerCount().equals("4")) {
			ownerSalutation = entity.getOwnerSalutation1() + "~" + entity.getOwnerSalutation2() + "~"
					+ entity.getOwnerSalutation3() + "~" + entity.getOwnerSalutation4();
			ownerName = entity.getOwnerName1() + "~" + entity.getOwnerName2() + "~" + entity.getOwnerName3() + "~"
					+ entity.getOwnerName4();
			passportNo = entity.getPassportNo1() + "~" + entity.getPassportNo2() + "~" + entity.getPassportNo3() + "~"
					+ entity.getPassportNo4();
			ownerContactNo = entity.getOwnerContactNo1() + "~" + entity.getOwnerContactNo2() + "~"
					+ entity.getOwnerContactNo3() + "~" + entity.getOwnerContactNo4();
			residentialAddress = entity.getResidentialAddress1() + "~" + entity.getResidentialAddress2() + "~"
					+ entity.getResidentialAddress3() + "~" + entity.getResidentialAddress4();

		} else if (entity.getOwnerCount() == "5" || entity.getOwnerCount().equals("5")) {
			ownerSalutation = entity.getOwnerSalutation1() + "~" + entity.getOwnerSalutation2() + "~"
					+ entity.getOwnerSalutation3() + "~" + entity.getOwnerSalutation4() + "~"
					+ entity.getOwnerSalutation5();
			ownerName = entity.getOwnerName1() + "~" + entity.getOwnerName2() + "~" + entity.getOwnerName3() + "~"
					+ entity.getOwnerName4() + "~" + entity.getOwnerName5();
			passportNo = entity.getPassportNo1() + "~" + entity.getPassportNo2() + "~" + entity.getPassportNo3() + "~"
					+ entity.getPassportNo4() + "~" + entity.getPassportNo5();
			ownerContactNo = entity.getOwnerContactNo1() + "~" + entity.getOwnerContactNo2() + "~"
					+ entity.getOwnerContactNo3() + "~" + entity.getOwnerContactNo4() + "~"
					+ entity.getOwnerContactNo5();
			residentialAddress = entity.getResidentialAddress1() + "~" + entity.getResidentialAddress2() + "~"
					+ entity.getResidentialAddress3() + "~" + entity.getResidentialAddress4() + "~"
					+ entity.getResidentialAddress5();

		} else {
			ownerSalutation = entity.getOwnerSalutation1();
			ownerName = entity.getOwnerName1();
			passportNo = entity.getPassportNo1();
			ownerContactNo = entity.getOwnerContactNo1();
			residentialAddress = entity.getResidentialAddress1();

		}

		merchant.setOwnerSalutation(ownerSalutation);
		merchant.setOwnerName(ownerName);
		merchant.setOwnerPassportNo(passportNo);
		merchant.setOwnerContactNo(ownerContactNo);
		merchant.setResidentialAddress(residentialAddress);
		merchant.setEnabled(true);
		merchant.setUsername(entity.getOfficeEmail());
		merchant.setCreatedDate(new Date());
		merchant.setRole(MerchantUserRole.BANK_MERCHANT);

		// Setting MID details
		MID mm = new MID();

		// Paydee
		if (mid != null && !(mid.isEmpty())) {
			if (mid.length() < 15) {
				for (int i = mid.length(); i < 15; i++) {
					mid = "0" + mid;
				}
			}
		} else {
			mid = null;
		}
		if (ezymotomid != null && !(ezymotomid.isEmpty())) {
			if (ezymotomid.length() < 15) {
				for (int i = ezymotomid.length(); i < 15; i++) {
					ezymotomid = "0" + ezymotomid;
				}
			}
		} else {
			ezymotomid = null;
		}
		if (ezywaymid != null && !(ezywaymid.isEmpty())) {
			if (ezywaymid.length() < 15) {
				for (int i = ezywaymid.length(); i < 15; i++) {
					ezywaymid = "0" + ezywaymid;
				}
			}
		} else {
			ezywaymid = null;
		}
		if (ezyrecmid != null && !(ezyrecmid.isEmpty())) {
			if (ezyrecmid.length() < 15) {
				for (int i = ezyrecmid.length(); i < 15; i++) {
					ezyrecmid = "0" + ezyrecmid;
				}
			}
		} else {
			ezyrecmid = null;
		}
		if (ezypassmid != null && !(ezypassmid.isEmpty())) {
			if (ezypassmid.length() < 15) {
				for (int i = ezypassmid.length(); i < 15; i++) {
					ezypassmid = "0" + ezypassmid;
				}
			}
		} else {
			ezypassmid = null;
		}

		// UMobile
		if (um_mid != null && !(um_mid.isEmpty())) {
			if (um_mid.length() < 15) {
				for (int i = um_mid.length(); i < 15; i++) {
					um_mid = "0" + um_mid;
				}
			}
		} else {
			um_mid = null;
		}
		if (um_motomid != null && !(um_motomid.isEmpty())) {
			if (um_motomid.length() < 15) {
				for (int i = um_motomid.length(); i < 15; i++) {
					um_motomid = "0" + um_motomid;
				}
			}
		} else {
			um_motomid = null;
		}
		if (um_ezywaymid != null && !(um_ezywaymid.isEmpty())) {
			if (um_ezywaymid.length() < 15) {
				for (int i = um_ezywaymid.length(); i < 15; i++) {
					um_ezywaymid = "0" + um_ezywaymid;
				}
			}
		} else {
			um_ezywaymid = null;
		}
		if (um_ezyrecmid != null && !(um_ezyrecmid.isEmpty())) {
			if (um_ezyrecmid.length() < 15) {
				for (int i = um_ezyrecmid.length(); i < 15; i++) {
					um_ezyrecmid = "0" + um_ezyrecmid;
				}
			}
		} else {
			um_ezyrecmid = null;
		}
		if (um_ezypassmid != null && !(um_ezypassmid.isEmpty())) {
			if (um_ezypassmid.length() < 15) {
				for (int i = um_ezypassmid.length(); i < 15; i++) {
					um_ezypassmid = "0" + um_ezypassmid;
				}
			}
		} else {
			um_ezypassmid = null;
		}

		// Paydee
		mm.setMid(mid);
		mm.setMotoMid(ezymotomid);
		mm.setEzypassMid(ezypassmid);
		mm.setEzywayMid(ezywaymid);
		mm.setEzyrecMid(ezyrecmid);

		// UMobile
		mm.setUmMid(um_mid);
		mm.setUmMotoMid(um_motomid);
		mm.setUmEzypassMid(um_ezypassmid);
		mm.setUmEzywayMid(um_ezywaymid);
		mm.setUmEzyrecMid(um_ezyrecmid);

		merchant.setMid(mm);

		logger.info("Status : " + entity.getStatus());

		if (entity.getStatus().equalsIgnoreCase("PENDING")) {
			merchant.setStatus(CommonStatus.PENDING);
		} else if (entity.getStatus().equalsIgnoreCase("SUBMITTED")) {
			merchant.setStatus(CommonStatus.SUBMITTED);
		} else if (entity.getStatus().equalsIgnoreCase("REJECTED")) {
			merchant.setStatus(CommonStatus.REJECTED);
		} else if (entity.getStatus().equalsIgnoreCase("ACTIVE")) {

			logger.info("Status Marked ACTIVE ");
			logger.info("About to Send Credential to Merchant ");

			merchant.setStatus(CommonStatus.ACTIVE);
			merchant.setActivateDate(new Date());
			RandomPassword rpwd = new RandomPassword();
			String genPwd = rpwd.generateRandomString();
			merchant.setPassword(encoder.encode(genPwd));
			TempletFields tempField = new TempletFields();
			tempField.setSalutation(merchant.getSalutation());
			tempField.setFirstName(merchant.getContactPersonName());
			tempField.setUserName(merchant.getUsername());
			tempField.setPassword(genPwd);
			tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));

			MsgDto md = new MsgDto();
			md.setFirstName(merchant.getContactPersonName());
			md.setUserName(merchant.getUsername());
			md.setPassword(genPwd);
			md.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));

			/*
			 * SendSMSMessage send=new SendSMSMessage(); send.sendMerchantRegEmail(md);
			 */

			/* logger.info("Successfully Sent Credential to Merchant " ); */

			List<NameValuePair> headers = new ArrayList<NameValuePair>();
			headers.add(new NameValuePair("HEADER", "test"));

			String fromAddress = "info@gomobi.io";
			String apiKey = PropertyLoader.getFile().getProperty("APIKEY");
			String toAddress = merchant.getUsername();
			String ccMail = PropertyLoader.getFile().getProperty("MOBILEUSER_ADD_CCMAIL");
			String bccMail = PropertyLoader.getFile().getProperty("MOBILEUSER_ADD_BCCMAIL");
			String subject = PropertyLoader.getFile().getProperty("WEBMAIL_SUBJECT");

			String emailBody = MerchantActivation.sentTempletContent(tempField);

			// String emailBody = EmailTemplet10.sentTempletContent(tempField);
			/*
			 * Attachment logo = new Attachment("mobiversa_logo1.jpg", "image/jpg",
			 * PropertyLoader.getFile().getProperty("NEW_LOGO"), "cid:mobiversa_logo1");
			 * 
			 * Attachment faceBook = new Attachment("mobi_facebook.jpg", "image/jpg",
			 * PropertyLoader.getFile().getProperty("FACEBOOK"), "cid:mobi_facebook");
			 * 
			 * Attachment twitter = new Attachment("mobi_twitter.jpg", "image/jpg",
			 * PropertyLoader.getFile().getProperty("TWITTER"), "cid:mobi_twitter");
			 * 
			 * Attachment link = new Attachment("mobiversa_link.jpg", "image/jpg",
			 * PropertyLoader.getFile().getProperty("LINK"), "cid:mobiversa_link");
			 * 
			 * Attachment linkedIn = new Attachment("mobi_linkedin.jpg", "image/jpg",
			 * PropertyLoader.getFile().getProperty("LINKEDIN"), "cid:mobi_linkedin");
			 * 
			 * List<Attachment> attachments = new ArrayList<Attachment>();
			 * attachments.add(logo); attachments.add(faceBook); attachments.add(twitter);
			 * attachments.add(link); attachments.add(linkedIn);
			 */

			List<Attachment> attachments = new ArrayList<Attachment>();

			Attachment mobiImg = new Attachment("mobiImg.jpg", "image/jpg",
					PropertyLoader.getFile().getProperty("MOBIIMG"), "cid:mobiImg");

			Attachment fbImg = new Attachment("mobi_facebook.jpg", "image/jpg",
					PropertyLoader.getFile().getProperty("FBIMG"), "cid:fbImg");

			Attachment twitImg = new Attachment("twitImg.jpg", "image/jpg",
					PropertyLoader.getFile().getProperty("TWITIMG"), "cid:twitImg");

			Attachment InstaImg = new Attachment("InstaImg.jpg", "image/jpg",
					PropertyLoader.getFile().getProperty("INSTAIMG"), "cid:InstaImg");

			Attachment linkedInImg = new Attachment("linkedInImg.jpg", "image/jpg",
					PropertyLoader.getFile().getProperty("LINKEDINIMG"), "cid:linkedInImg");

			Attachment emailImg = new Attachment("emailImg.jpg", "image/jpg",
					PropertyLoader.getFile().getProperty("EMAILIMG"), "cid:emailImg");

			Attachment tollfreeImg = new Attachment("tollfreeImg.jpg", "image/jpg",
					PropertyLoader.getFile().getProperty("TOLLFREEIMG"), "cid:tollfreeImg");

			Attachment telephoneImg = new Attachment("telephoneImg.jpg", "image/jpg",
					PropertyLoader.getFile().getProperty("TELEPHONEIMG"), "cid:telephoneImg");

			Attachment webImg = new Attachment("webImg.png", "image/jpg",
					PropertyLoader.getFile().getProperty("WEBIMG"), "cid:webImg");
			Attachment activationBannerImg = new Attachment("activationBannerImg.png", "image/jpg",
					PropertyLoader.getFile().getProperty("ACTIVATIONBANNERIMG"), "cid:activationBannerImg");

			attachments.add(activationBannerImg);
			attachments.add(webImg);
			attachments.add(mobiImg);
			attachments.add(fbImg);
			attachments.add(twitImg);
			attachments.add(InstaImg);
			attachments.add(linkedInImg);
			attachments.add(emailImg);
			attachments.add(tollfreeImg);
			attachments.add(telephoneImg);

			PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress, fromAddress, ccMail, bccMail, subject,
					emailBody, true, "test-email", null, attachments);
			PostmarkClient client = new PostmarkClient(apiKey);

			try {
				PostmarkResponse mailSent = client.sendMessage(message);
				if (mailSent.getStatus().equals("SUCCESS")) {
					logger.info("Merchant Activation Email Sent Successfully to" + merchant.getEmail());
				}
			} catch (PostmarkException pe) {
				System.out.println("Invalid Signature Base64 String");
			}
		}

		logger.info("About to add Merchant ");
		merchant = merchantDAO.saveOrUpdateEntity(merchant);
		logger.info("Merchant Added Successfully");

		MerchantDetails merchantDetails1 = new MerchantDetails();
		merchantDetails1.setMerchantId(merchant.getId().toString());
		merchantDetails1.setMerchantCategory(merchant.getNatureOfBusiness());

		if ((merchant.getMerchantType() != null) && (merchant.getMerchantType().equals("U"))) {

			if (um_mid != null && !um_mid.isEmpty()) {
				logger.info("Merchantdetails um_mid:" + um_mid);
				merchantDetails1.setMid(um_mid);

			} else if (um_motomid != null && !um_motomid.isEmpty()) {
				logger.info("Merchantdetails um_motomid:" + um_motomid);
				merchantDetails1.setMid(um_motomid);

			} else if (um_ezywaymid != null && !um_ezywaymid.isEmpty()) {
				logger.info("Merchantdetails um_ezywaymid:" + um_ezywaymid);
				merchantDetails1.setMid(um_ezywaymid);

			} else if (um_ezyrecmid != null && !um_ezyrecmid.isEmpty()) {
				logger.info("Merchantdetails um_ezyrecmid:" + um_ezyrecmid);
				merchantDetails1.setMid(um_ezyrecmid);

			} else {
				logger.info("Merchantdetails um_ezypassmid:" + um_ezypassmid);
				merchantDetails1.setMid(um_ezypassmid);
			}

		} else if ((merchant.getMerchantType() != null) && (merchant.getMerchantType().equals("P"))) {
			if (mid != null && !mid.isEmpty()) {
				logger.info("Merchantdetails mid:" + mid);
				merchantDetails1.setMid(mid);
			} else if (ezymotomid != null && !ezymotomid.isEmpty()) {
				logger.info("Merchantdetails ezymotomid:" + ezymotomid);
				merchantDetails1.setMid(ezymotomid);
			} else if (ezypassmid != null && !ezypassmid.isEmpty()) {
				logger.info("Merchantdetails ezypassmid:" + ezypassmid);
				merchantDetails1.setMid(ezypassmid);
			} else if (ezywaymid != null && !ezywaymid.isEmpty()) {
				logger.info("Merchantdetails ezywaymid:" + ezywaymid);
				merchantDetails1.setMid(ezywaymid);
			} else {
				logger.info("Merchantdetails ezyrecmid:" + ezyrecmid);
				merchantDetails1.setMid(ezyrecmid);
			}

		} else {
			if (mid != null && !mid.isEmpty()) {
				logger.info("Merchantdetails mid:" + mid);
				merchantDetails1.setMid(mid);
			} else if (ezymotomid != null && !ezymotomid.isEmpty()) {
				logger.info("Merchantdetails ezymotomid:" + ezymotomid);
				merchantDetails1.setMid(ezymotomid);
			} else if (ezypassmid != null && !ezypassmid.isEmpty()) {
				logger.info("Merchantdetails ezypassmid:" + ezypassmid);
				merchantDetails1.setMid(ezypassmid);
			} else if (ezywaymid != null && !ezywaymid.isEmpty()) {
				logger.info("Merchantdetails ezywaymid:" + ezywaymid);
				merchantDetails1.setMid(ezywaymid);
			} else {
				logger.info("Merchantdetails ezyrecmid:" + ezyrecmid);
				merchantDetails1.setMid(ezyrecmid);
			}

		}

		/*
		 * if (mid != null) { merchantDetails1.setMid(mid); } else if (ezymotomid !=
		 * null) { merchantDetails1.setMid(ezymotomid); } else {
		 * merchantDetails1.setMid(ezypassmid); }
		 */
		merchantDetails1.setPoints("100");
		merchantDetails1.setMerchantName(merchant.getBusinessName());

		logger.info("About to add Merchant details ");
		merchantDetails1 = merchantDAO.saveOrUpdateEntity(merchantDetails1);
		logger.info("Merchant details  Added Successfully");

		if (entity.getFileId() != null) {
			String fileIds[] = null;
			if (entity.getFileId().contains("~")) {
				fileIds = entity.getFileId().split("~");
				for (int i = 0; i < fileIds.length; i++) {
					FileUpload fileUpload = loadFileUpload(fileIds[i]);
					fileUpload.setMerchantId(merchant.getId().toString());
					FileUpload fileUpload1 = merchantDAO.updateFileById(fileUpload);
				}
			} else {
				FileUpload fileUpload = loadFileUpload(entity.getFileId());
				fileUpload.setMerchantId(merchant.getId().toString());
				FileUpload fileUpload1 = merchantDAO.updateFileById(fileUpload);
			}
		}
		int updt = merchantDAO.updateMIDData(merchant.getMid().getId(), merchant.getId());
		return entity;
	}

//Newly added on 05052021 - Santhosh

	public RegAddMerchant addSubMerchant(final RegAddMerchant entity) throws JMSException // return type need to change
																							// as RegAddMerchant
	{
//
//		logger.info("Merchant Type : " + entity.getMerchantType());
//		logger.info("VCC  : " + entity.getVcc());
//		logger.info("Pre-Auth  : " + entity.getPreAuth());
//		logger.info("Auto Settled  : " + entity.getAutoSettled());
//		logger.info("OTP  : " + entity.getAuth3DS());
//
//		BigInteger AgID = null;
//		BigInteger subAgID = null;
//
//		Merchant merchant = new Merchant();
//		merchant.setAgID(AgID);
//		merchant.setSubAgID(subAgID);
//		merchant.setBusinessShortName(entity.getRegisteredName());
//		merchant.setBusinessName(entity.getBusinessName().toUpperCase());
//		merchant.setBusinessRegistrationNumber(entity.getBusinessRegNo());
//		merchant.setBusinessAddress1(entity.getRegisteredAddress());
//		merchant.setBusinessAddress2(entity.getBusinessAddress());
//		merchant.setBusinessAddress3(entity.getMailingAddress());
//		merchant.setContactPersonName(entity.getName());
//		merchant.setContactPersonPhoneNo(entity.getContactNo());
//		merchant.setEmail(entity.getEmail());
//		merchant.setSalutation(entity.getSalutation());
//		merchant.setUsername(entity.getEmail());
//		merchant.setWebsite(entity.getWebsite());
//		merchant.setBusinessContactNumber(entity.getOfficeNo());
//		merchant.setFaxNo(entity.getFaxNo());
//		merchant.setBankName(entity.getBankName());
//		merchant.setBankAcc(entity.getBankAccNo());
//		merchant.setState(entity.getBusinessState());
//		merchant.setCity(entity.getBusinessCity());
//		merchant.setPostcode(entity.getBusinessPostCode());
//		merchant.setReferralId(entity.getReferralId());
//		merchant.setWaiverMonth(entity.getWavierMonth());
//		merchant.setTradingName(entity.getTradingName());
//		merchant.setYearIncorporated(entity.getYearIncorporated());
//		merchant.setSignedPackage(entity.getSignedPackage());
//		merchant.setReaderSerialNo(entity.getNoOfReaders());
//		merchant.setResidentialAddress(entity.getRegisteredAddress());
//		merchant.setBusinessType(entity.getBusinessType());
//		merchant.setCompanyType(entity.getCompanyType());
//		merchant.setNatureOfBusiness(entity.getNatureOfBusiness());
//		merchant.setPermiseType(entity.getDocuments());
//		merchant.setRemarks(entity.getStatusRemarks());
//		merchant.setPreAuth(entity.getPreAuth());
//		merchant.setAuth3DS(entity.getAuth3DS());
//		merchant.setMdr(entity.getMdr());
//		merchant.setAutoSettled(entity.getAutoSettled());
//		merchant.setMerchantType(entity.getMerchantType());
//		merchant.setEzyMotoVcc(entity.getVcc());
//		merchant.setAccType(entity.getAccType());
//		merchant.setMmId(entity.getMmid());
//
//		String ownerSalutation = "";
//		String ownerName = "";
//		String passportNo = "";
//		String residentialAddress = "";
//		String ownerContactNo = "";
//
//		ownerSalutation = entity.getOwnerSalutation1();
//		ownerName = entity.getOwnerName1();
//		passportNo = entity.getPassportNo1();
//		ownerContactNo = entity.getOwnerContactNo1();
//		residentialAddress = entity.getResidentialAddress1();
//
//		merchant.setOwnerSalutation(ownerSalutation);
//		merchant.setOwnerName(ownerName);
//		merchant.setOwnerPassportNo(passportNo);
//		merchant.setOwnerContactNo(ownerContactNo);
//		merchant.setResidentialAddress(residentialAddress);
//		merchant.setEnabled(true);
//		merchant.setUsername(entity.getEmail());
//		merchant.setCreatedDate(new Date());
//		merchant.setRole(MerchantUserRole.BANK_MERCHANT);
//
//		// Setting MID details
//		MID mm = new MID();
//
//		MobileOTP sequenceNo = null;
//
//		if (entity.getMerchantType() != null) {
//			if (entity.getMerchantType().equals("U") || entity.getMerchantType() == "U") {
//				sequenceNo = merchantDAO.checkOTP("2011", "2011");
//			} else if (entity.getMerchantType().equals("P") || entity.getMerchantType() == "P") {
//				sequenceNo = merchantDAO.checkOTP("1011", "1011");
//			}
//		} else {
//			logger.info("Merchant Type Empty");
//		}
//
//		logger.info("mid : " + sequenceNo.getOptData());
//
//		mm.setSubMerchantMID(sequenceNo.getOptData());
//
//		merchant.setMid(mm);
//
//		entity.setMid(sequenceNo.getOptData());
//		entity.setBusinessName(merchant.getBusinessName());
//
//		logger.info("Status Marked ACTIVE ");
//		logger.info("About to Send Credential to Merchant ");
//
//		merchant.setStatus(CommonStatus.ACTIVE);
//		merchant.setActivateDate(new Date());
//		RandomPassword rpwd = new RandomPassword();
//		String genPwd = rpwd.generateRandomString();
//		merchant.setPassword(encoder.encode(genPwd));
//		TempletFields tempField = new TempletFields();
//		tempField.setSalutation(merchant.getSalutation());
//		tempField.setFirstName(merchant.getContactPersonName());
//		tempField.setUserName(merchant.getUsername());
//		tempField.setPassword(genPwd);
//		tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
//
//		MsgDto md = new MsgDto();
//		md.setFirstName(merchant.getContactPersonName());
//		md.setUserName(merchant.getUsername());
//		md.setPassword(genPwd);
//		md.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
//
//		logger.info("About to add Merchant ");
//
//		PayoutGrandDetail pg = new PayoutGrandDetail();
//
//		pg.setSettleNetAmt("000000000000");
//
//		merchant.setPayoutGrandDetail(pg);
//		merchant = merchantDAO.saveOrUpdateEntity(merchant);
//		logger.info("Merchant Added Successfully");
//
//		MerchantDetails merchantDetails1 = new MerchantDetails();
//		merchantDetails1.setMerchantId(merchant.getId().toString());
//		merchantDetails1.setMerchantCategory(merchant.getNatureOfBusiness());
//		if (sequenceNo.getOptData() != null && !sequenceNo.getOptData().isEmpty()) {
//			logger.info("Merchantdetails mid:" + sequenceNo.getOptData());
//			merchantDetails1.setMid(sequenceNo.getOptData());
//		}
//
//		merchantDetails1.setPoints("100");
//		merchantDetails1.setMerchantName(merchant.getBusinessName());
//
//		logger.info("About to add Merchant details ");
//		merchantDetails1 = merchantDAO.saveOrUpdateEntity(merchantDetails1);
//		logger.info("Merchant details Added Successfully");
//
//		int updt = merchantDAO.updateMIDData(merchant.getMid().getId(), merchant.getId());
//
//		int uppg = merchantDAO.updateMKData(merchant.getPayoutGrandDetail().getId(), merchant.getId());
//
//		logger.info(" Update uppg Data : " + updt);
//
//		Long updatedSubMid = Long.parseLong(sequenceNo.getOptData());
//		logger.info("Current Sequence :" + updatedSubMid);
//		updatedSubMid = updatedSubMid + 1;
//		logger.info("Updated Sequence :" + updatedSubMid);
//		logger.info("String Updated Sequence :" + updatedSubMid.toString());
//
//		String key = null;
//
//		if (entity.getMerchantType().equalsIgnoreCase("P")) {
//			key = "1011";
//		} else if (entity.getMerchantType().equalsIgnoreCase("U")) {
//			key = "2011";
//		}
//
//		merchantDAO.updateMobileOTP(updatedSubMid.toString(), key);
//
//		logger.info("Entity MID" + entity.getMid());
//		logger.info("Entity business" + entity.getBusinessName());
//
//		RegAddMerchant samp = new RegAddMerchant();
//		samp.setMid(entity.getMid());
//		samp.setBusinessName(entity.getBusinessName());
//		samp.setOfficeEmail(entity.getEmail());
//
//		return samp;

		logger.info("Merchant Type : " + entity.getMerchantType());
		logger.info("VCC  : " + entity.getVcc());
		logger.info("Pre-Auth  : " + entity.getPreAuth());
		logger.info("Auto Settled  : " + entity.getAutoSettled());
		logger.info("OTP  : " + entity.getAuth3DS());

		BigInteger AgID = null;
		BigInteger subAgID = null;

		Merchant merchant = new Merchant();
		merchant.setAgID(AgID);
		merchant.setSubAgID(subAgID);
		merchant.setBusinessShortName(entity.getRegisteredName());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setBusinessName(entity.getBusinessName().toUpperCase());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setBusinessRegistrationNumber(entity.getBusinessRegNo());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setBusinessAddress1(entity.getRegisteredAddress());
		logger.info("Business Address1: " + merchant.getBusinessAddress1());
		merchant.setBusinessAddress2(entity.getBusinessAddress());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setBusinessAddress3(entity.getMailingAddress());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setContactPersonName(entity.getName());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setContactPersonPhoneNo(entity.getContactNo());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setEmail(entity.getEmail());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setSalutation(entity.getSalutation());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());

		merchant.setUsername("");
//		merchant.setUsername("null");
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setWebsite(entity.getWebsite());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setBusinessContactNumber(entity.getOfficeNo());
		logger.info("Business Contact Number: " + merchant.getBusinessContactNumber());
		merchant.setFaxNo(entity.getFaxNo());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setBankName(entity.getBankName());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setBankAcc(entity.getBankAccNo());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setState(entity.getBusinessState());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setCity(entity.getBusinessCity());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setPostcode(entity.getBusinessPostCode());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setReferralId(entity.getReferralId());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setWaiverMonth(entity.getWavierMonth());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setTradingName(entity.getTradingName());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setYearIncorporated(entity.getYearIncorporated());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setSignedPackage(entity.getSignedPackage());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setReaderSerialNo(entity.getNoOfReaders());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setResidentialAddress(entity.getRegisteredAddress());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setBusinessType(entity.getBusinessType());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setCompanyType(entity.getCompanyType());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setNatureOfBusiness(entity.getNatureOfBusiness());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setPermiseType(entity.getDocuments());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setRemarks(entity.getStatusRemarks());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setPreAuth(entity.getPreAuth());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setAuth3DS(entity.getAuth3DS());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setMdr(entity.getMdr());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setAutoSettled(entity.getAutoSettled());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setMerchantType(entity.getMerchantType());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setEzyMotoVcc(entity.getVcc());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setAccType(entity.getAccType());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());
		merchant.setMmId(entity.getMmid());
		logger.info("Business ShortName: " + merchant.getBusinessShortName());

		String ownerSalutation = "";
		String ownerName = "";
		String passportNo = "";
		String residentialAddress = "";
		String ownerContactNo = "";

		ownerSalutation = entity.getOwnerSalutation1();
		ownerName = entity.getOwnerName1();
		passportNo = entity.getPassportNo1();
		ownerContactNo = entity.getOwnerContactNo1();
		residentialAddress = entity.getResidentialAddress1();

		merchant.setOwnerSalutation(ownerSalutation);
		merchant.setOwnerName(ownerName);
		merchant.setOwnerPassportNo(passportNo);
		merchant.setOwnerContactNo(ownerContactNo);
		merchant.setResidentialAddress(residentialAddress);
		merchant.setEnabled(true);

//		merchant.setUsername(entity.getEmail());
		merchant.setCreatedDate(new Date());
		merchant.setRole(MerchantUserRole.BANK_MERCHANT);

		// Setting MID details
		MID mm = new MID();

		MobileOTP sequenceNo = null;
		// MobileOTP sequenceNo;
		logger.info(" ****** Merchant Type Empty 1234556 *******" + entity.getMerchantType());

		if (entity.getMerchantType() != null) {
			if (entity.getMerchantType().equals("U") || entity.getMerchantType() == "U") {
				sequenceNo = merchantDAO.checkOTP("2011", "2011");
			} else if (entity.getMerchantType().equals("P") || entity.getMerchantType() == "P"
					|| entity.getMerchantType().equalsIgnoreCase("null") || entity.getMerchantType().equals("NULL")
					|| entity.getMerchantType().equals("")) {
				sequenceNo = merchantDAO.checkOTP("1011", "1011");
			}
		} else if (entity.getMerchantType() == null) {
			logger.info("Merchant Type Empty");
			logger.info(" ****** Merchant Type Empty *******" + entity.getMerchantType());
			sequenceNo = merchantDAO.checkOTP("1011", "1011");

		}
		logger.info("########  mid get : #######");

		logger.info("mid : " + sequenceNo.getOptData());
		mm.setSubMerchantMID(sequenceNo.getOptData());
		logger.info("mid12345 : " + mm.getSubMerchantMID());

		String mid1 = sequenceNo.getOptData();

		merchant.setMid(mm);

		entity.setMid(sequenceNo.getOptData());
		entity.setBusinessName(merchant.getBusinessName());

		logger.info("Status Marked ACTIVE ");
		logger.info("About to Send Credential to Merchant ");

		merchant.setStatus(CommonStatus.ACTIVE);
		merchant.setActivateDate(new Date());
		RandomPassword rpwd = new RandomPassword();
		String genPwd = rpwd.generateRandomString();

		/* */
		merchant.setPassword(encoder.encode(genPwd));
//		merchant.setPassword("null");
		TempletFields tempField = new TempletFields();
		tempField.setSalutation(merchant.getSalutation());
		tempField.setFirstName(merchant.getContactPersonName());
		logger.info(".>>>>>>>>" + tempField.getFirstName());
		tempField.setUserName(merchant.getUsername());
		tempField.setPassword(genPwd);
		tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));

		MsgDto md = new MsgDto();
		md.setFirstName(merchant.getContactPersonName());
		md.setUserName(merchant.getUsername());
		md.setPassword(genPwd);
		md.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));

		logger.info("About to add Merchant ");

		PayoutGrandDetail pg = new PayoutGrandDetail();

		pg.setSettleNetAmt("000000000000");

		merchant.setPayoutGrandDetail(pg);

		// sakthi added
		// Add Submerchant api Starting

		logger.info("Add Submerchant Api Start Here");
		SubmerchantApi addSubmerchant = new SubmerchantApi();
		ResponseDetails1 re = addSubmerchant.addSubMerchant(merchant, mm);
		// merchant = merchantDAO.saveOrUpdateEntity(merchant);
		// TODO need to add api here
		logger.info("Merchant Added Successfully");

		MerchantDetails merchantDetails1 = new MerchantDetails();

		logger.info("Merchant added after : " + re.getId());
		logger.info("Merchant added after : " + merchant.getId());

		merchantDetails1.setMerchantId(re.getId());
		merchantDetails1.setMerchantCategory(re.getNatureOfBusiness());
		if (sequenceNo.getOptData() != null && !sequenceNo.getOptData().isEmpty()) {
			logger.info("Merchantdetails mid:" + sequenceNo.getOptData());
			merchantDetails1.setMid(sequenceNo.getOptData());
		}

		merchantDetails1.setPoints("100");
		merchantDetails1.setMerchantName(re.getBusinessName());

		logger.info("About to add Merchant details ");
		// TODO Call api here - 1
		addSubmerchant.merchantDetails1(merchantDetails1);
		// merchantDetails1 = merchantDAO.saveOrUpdateEntity(merchantDetails1);
		logger.info("Merchant details Added Successfully");
		// TODO Call api here - 2
		long mid = re.getMid();
		String id = re.getId();
		logger.info("Merchant details Added Successfully" + mid);
		logger.info("Merchant details Added Successfully" + id);
		// int updt = merchantDAO.updateMIDData(merchant.getMid().getId();,
		// merchant.getId());
		addSubmerchant.updateMIDData(mid, id);

		// TODO Call api here - 3
		long pgMid = re.getPayoutGrandDetail();
		addSubmerchant.updateMKData(pgMid, mid);
		// int uppg = merchantDAO.updateMKData(merchant.getPayoutGrandDetail().getId(),
		// merchant.getId());
		// logger.info(" Update uppg Data : " + updt);
		Long updatedSubMid = Long.parseLong(sequenceNo.getOptData());
		logger.info("Current Sequence :" + updatedSubMid);
		updatedSubMid = updatedSubMid + 1;
		logger.info("Updated Sequence :" + updatedSubMid);
		logger.info("String Updated Sequence :" + updatedSubMid.toString());

		String key = null;
		logger.info("    (((())))))    merchant type ********" + entity.getMerchantType());
		if (entity.getMerchantType() == null || entity.getMerchantType().equalsIgnoreCase("P")) {
			key = "1011";
		} else if (entity.getMerchantType().equalsIgnoreCase("U")) {
			key = "2011";
		}
		// TODO Call api here - 4
		String submid = updatedSubMid.toString();
		addSubmerchant.updateMobileOTP(submid, key);
		// merchantDAO.updateMobileOTP(updatedSubMid.toString(), key);

		logger.info("Entity MID" + entity.getMid());
		logger.info("Entity business" + entity.getBusinessName());

		RegAddMerchant samp = new RegAddMerchant();
		samp.setMid(entity.getMid());
		samp.setBusinessName(entity.getBusinessName());
		samp.setOfficeEmail(entity.getEmail());

		return samp;

	}

	// new method 24062016 mid already exist

	/*
	 * @javax.transaction.Transactional public MID loadMid(String mid){ MID mid1 =
	 * new MID(); return merchantDAO.loadMid(mid); }
	 */

	// new method for pending merchant 24062016

	// @javax.transaction.Transactional
	public void listMerchant1(final PaginationBean<Merchant> paginationBean) {
		// System.out.println("Inside listAllTransaction:::::");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		criterionList.add(Restrictions.not(Restrictions.eq("status", CommonStatus.ACTIVE)));
		merchantDAO.listMerchantUser1(paginationBean, criterionList);
	}

	// @javax.transaction.Transactional
	public Merchant updateMerchant(final RegAddMerchant regMerchant, Merchant merchant, String mailStatus) {
		// Merchant merchant1=new Merchant();

		/*
		 * Agent agentOldDetail = agentDAO.loadAgentDetailsbyId(merchant.getAgID());
		 * 
		 * SecondaryAgent secAgentHistory = new SecondaryAgent();
		 * 
		 * secAgentHistory.setAgName(agentOldDetail.getFirstName());
		 * secAgentHistory.setPrimaryId(agentOldDetail.getId().toString());
		 * secAgentHistory.setChangedBy("EDIT MERCHANT");
		 * secAgentHistory.setCreatedBy("PORTAL");
		 * secAgentHistory.setReason("MERCHANT UPDATE"); secAgentHistory=
		 * merchantDAO.saveOrUpdateEntity(secAgentHistory);
		 */
		String agentData = regMerchant.getAgentName();
		agentData = agentData.replace(",", "");
		// String agentData = entity.getSignedPackage();

		logger.info("agentData:::::: " + agentData);
		logger.info("regMerchant.getId():::::: " + regMerchant.getId());
		String mailId = null;
		String type = null;
		Agent agent = null;
		SubAgent subAgent = null;
		// if(agentData != null || !(agentData.equals(""))){
		if (!(agentData.equals(""))) {
			String agentmail[] = agentData.split("~");
			// System.out.println("Agent Data "+agentmail);
			type = agentmail[0];
			mailId = agentmail[2];
		}
		merchant.setId(Long.parseLong(regMerchant.getId()));
		// System.out.println(" Data Id "+merchant.getId() + " "+merchant.getMid());

		logger.info("type:::::: " + type);

		// System.out.println("Agent Mail : "+ mailId+" "+type);
		if (type.equals("AGENT")) {
			agent = agentDAO.loadAgentbyMailId(mailId);

			logger.info("Agent Data : " + agent.getId());

			// System.out.println("Agent Data : "+ agent.getId());
		}
		if (type.equals("SUBAGENT")) {
			subAgent = subAgentDAO.loadAgentbyMailId(mailId);

			logger.info("SubAgent Data : " + subAgent.getId());

			// System.out.println("SubAgent Data : "+ subAgent.getId());
		}
		/* merchant1.getId(merchant.setId().toString()); */

		BigInteger bi = null;
		if (agent != null) {

			bi = new BigInteger(agent.getId().toString());

			logger.info("Data agent : " + bi);

			// System.out.println("Data agent : "+ bi);
		}

		BigInteger subAgID = null;
		if (subAgent != null) {
			subAgID = new BigInteger(subAgent.getId().toString());

			logger.info("Data subagent : " + subAgID);

			// System.out.println("Data subagent : "+ subAgID);
		}

		merchant.setAgID(bi);
		merchant.setSubAgID(subAgID);

		// umobile mid

		// }

		merchant.setBusinessName(regMerchant.getBusinessName().toUpperCase());
		merchant.setBusinessShortName(regMerchant.getRegisteredName());
		merchant.setBusinessRegistrationNumber(regMerchant.getBusinessRegNo());
		merchant.setBusinessAddress1(regMerchant.getRegisteredAddress());
		merchant.setBusinessAddress2(regMerchant.getBusinessAddress());
		merchant.setBusinessAddress3(regMerchant.getMailingAddress());
		merchant.setSalutation(regMerchant.getSalutation());
		merchant.setContactPersonName(regMerchant.getName());
		merchant.setContactPersonPhoneNo(regMerchant.getContactNo());
		merchant.setEmail(regMerchant.getEmail());
		merchant.setWebsite(regMerchant.getWebsite());
		merchant.setBusinessContactNumber(regMerchant.getOfficeNo());
		merchant.setFaxNo(regMerchant.getFaxNo());
		merchant.setBusinessType(regMerchant.getBusinessType());
		merchant.setCompanyType(regMerchant.getCompanyType());
		merchant.setNatureOfBusiness(regMerchant.getNatureOfBusiness());
		merchant.setPermiseType(regMerchant.getDocuments());
		merchant.setBankName(regMerchant.getBankName());
		merchant.setBankAcc(regMerchant.getBankAccNo());
		merchant.setState(regMerchant.getBusinessState());
		merchant.setCity(regMerchant.getBusinessCity());
		merchant.setPostcode(regMerchant.getBusinessPostCode());
		merchant.setReferralId(regMerchant.getReferralId());
		merchant.setWaiverMonth(regMerchant.getWavierMonth());
		merchant.setTradingName(regMerchant.getTradingName());
		merchant.setReaderSerialNo(regMerchant.getNoOfReaders());
		merchant.setYearIncorporated(regMerchant.getYearIncorporated());
		merchant.setSignedPackage(regMerchant.getSignedPackage());
		merchant.setRemarks(regMerchant.getStatusRemarks());
		merchant.setAccType(regMerchant.getAccType());

		String ownerSalutation = "";
		String ownerName = "";
		String passportNo = "";
		String residentialAddress = "";
		String ownerContactNo = "";

		// System.out.println(" Owner's Count :"+merchant.getOwnerCount());

		if (regMerchant.getOwnerCount() == "2" || regMerchant.getOwnerCount().equals("2")) {
			ownerSalutation = regMerchant.getOwnerSalutation1() + "~" + regMerchant.getOwnerSalutation2();
			ownerName = regMerchant.getOwnerName1() + "~" + regMerchant.getOwnerName2();
			passportNo = regMerchant.getPassportNo1() + "~" + regMerchant.getPassportNo2();
			ownerContactNo = regMerchant.getOwnerContactNo1() + "~" + regMerchant.getOwnerContactNo2();
			residentialAddress = regMerchant.getResidentialAddress1() + "~" + regMerchant.getResidentialAddress2();

		} else if (regMerchant.getOwnerCount() == "3" || regMerchant.getOwnerCount().equals("3")) {
			ownerSalutation = regMerchant.getOwnerSalutation1() + "~" + regMerchant.getOwnerSalutation2() + "~"
					+ regMerchant.getOwnerSalutation3();
			ownerName = regMerchant.getOwnerName1() + "~" + regMerchant.getOwnerName2() + "~"
					+ regMerchant.getOwnerName3();
			passportNo = regMerchant.getPassportNo1() + "~" + regMerchant.getPassportNo2() + "~"
					+ regMerchant.getPassportNo3();
			ownerContactNo = regMerchant.getOwnerContactNo1() + "~" + regMerchant.getOwnerContactNo2() + "~"
					+ regMerchant.getOwnerContactNo3();
			residentialAddress = regMerchant.getResidentialAddress1() + "~" + regMerchant.getResidentialAddress2() + "~"
					+ regMerchant.getResidentialAddress3();

		} else if (regMerchant.getOwnerCount() == "4" || regMerchant.getOwnerCount().equals("4")) {
			ownerSalutation = regMerchant.getOwnerSalutation1() + "~" + regMerchant.getOwnerSalutation2() + "~"
					+ regMerchant.getOwnerSalutation3() + "~" + regMerchant.getOwnerSalutation4();
			ownerName = regMerchant.getOwnerName1() + "~" + regMerchant.getOwnerName2() + "~"
					+ regMerchant.getOwnerName3() + "~" + regMerchant.getOwnerName4();
			passportNo = regMerchant.getPassportNo1() + "~" + regMerchant.getPassportNo2() + "~"
					+ regMerchant.getPassportNo3() + "~" + regMerchant.getPassportNo4();
			ownerContactNo = regMerchant.getOwnerContactNo1() + "~" + regMerchant.getOwnerContactNo2() + "~"
					+ regMerchant.getOwnerContactNo3() + "~" + regMerchant.getOwnerContactNo4();
			residentialAddress = regMerchant.getResidentialAddress1() + "~" + regMerchant.getResidentialAddress2() + "~"
					+ regMerchant.getResidentialAddress3() + "~" + regMerchant.getResidentialAddress4();

		} else if (regMerchant.getOwnerCount() == "5" || regMerchant.getOwnerCount().equals("5")) {
			ownerSalutation = regMerchant.getOwnerSalutation1() + "~" + regMerchant.getOwnerSalutation2() + "~"
					+ regMerchant.getOwnerSalutation3() + "~" + regMerchant.getOwnerSalutation4() + "~"
					+ regMerchant.getOwnerSalutation5();
			ownerName = regMerchant.getOwnerName1() + "~" + regMerchant.getOwnerName2() + "~"
					+ regMerchant.getOwnerName3() + "~" + regMerchant.getOwnerName4() + "~"
					+ regMerchant.getOwnerName5();
			passportNo = regMerchant.getPassportNo1() + "~" + regMerchant.getPassportNo2() + "~"
					+ regMerchant.getPassportNo3() + "~" + regMerchant.getPassportNo4() + "~"
					+ regMerchant.getPassportNo5();
			ownerContactNo = regMerchant.getOwnerContactNo1() + "~" + regMerchant.getOwnerContactNo2() + "~"
					+ regMerchant.getOwnerContactNo3() + "~" + regMerchant.getOwnerContactNo4() + "~"
					+ regMerchant.getOwnerContactNo5();
			residentialAddress = regMerchant.getResidentialAddress1() + "~" + regMerchant.getResidentialAddress2() + "~"
					+ regMerchant.getResidentialAddress3() + "~" + regMerchant.getResidentialAddress4() + "~"
					+ regMerchant.getResidentialAddress5();

		} else {
			ownerSalutation = regMerchant.getOwnerSalutation1();
			ownerName = regMerchant.getOwnerName1();
			passportNo = regMerchant.getPassportNo1();
			ownerContactNo = regMerchant.getOwnerContactNo1();
			residentialAddress = regMerchant.getResidentialAddress1();

		}

		merchant.setOwnerSalutation(ownerSalutation);
		merchant.setOwnerName(ownerName);
		merchant.setOwnerPassportNo(passportNo);
		merchant.setOwnerContactNo(ownerContactNo);
		merchant.setResidentialAddress(residentialAddress);

		merchant.setMdr(regMerchant.getMdr());
		merchant.setPreAuth(regMerchant.getPreAuth());
		merchant.setAuth3DS(regMerchant.getAuth3DS());
		merchant.setAutoSettled(regMerchant.getAutoSettled());

		merchant.setModifiedBy(regMerchant.getEzysettle());

		logger.info("regezysettle: " + regMerchant.getEzysettle());
		// merchant.setForeignCard(regMerchant.getForeignCard());
		logger.info("regMerchant.getForeignCard(): " + regMerchant.getForeignCard());
		logger.info("regMerchant.getStatus(): " + regMerchant.getStatus());
		if (regMerchant.getStatus() != null) {
			if (regMerchant.getStatus().equalsIgnoreCase("ACTIVE")) {
				merchant.setActivateDate(new Date());
				merchant.setStatus(CommonStatus.ACTIVE);
				RandomPassword rpwd = new RandomPassword();
				String genPwd = rpwd.generateRandomString();
				merchant.setPassword(encoder.encode(genPwd));
			} else if (regMerchant.getStatus().equalsIgnoreCase("PENDING")) {
				merchant.setUsername(regMerchant.getOfficeEmail());
				merchant.setCreatedDate(new Date());
				merchant.setStatus(CommonStatus.PENDING);
			} else if (regMerchant.getStatus().equalsIgnoreCase("SUBMITTED")) {
				merchant.setUsername(regMerchant.getOfficeEmail());
				merchant.setModifiedDate(new Date());
				merchant.setStatus(CommonStatus.SUBMITTED);
			} else if (regMerchant.getStatus().equalsIgnoreCase("REJECTED")) {
				merchant.setUsername(regMerchant.getOfficeEmail());
				merchant.setModifiedDate(new Date());
				merchant.setStatus(CommonStatus.REJECTED);
			}
		}
		if (regMerchant.getFileId() != null) {

			logger.info("regMerchant.getFileId(): ");

			// System.out.println(" Inside File Cond");
			String fileIds[] = null;
			if (regMerchant.getFileId().contains("~")) {
				fileIds = regMerchant.getFileId().split("~");
				for (int i = 0; i < fileIds.length; i++) {

					// FileUpload fileUpload = loadFileUpload(entity.getFileId());
					// System.out.println(" Inside File loop :"+fileIds[i]);
					FileUpload fileUpload = loadFileUpload(fileIds[i]);

					fileUpload.setMerchantId(merchant.getId().toString());

					FileUpload fileUpload1 = merchantDAO.updateFileById(fileUpload);

					// System.out.println("Merchant service MerchantId Data :
					// "+fileUpload1.getFileId());
				}
			} else {
				// System.out.println(" Inside File Cond else");
				// fileIds[0]= merchant.getFileId();

				// FileUpload fileUpload = loadFileUpload(entity.getFileId());
				// System.out.println(" Inside File loop :"+merchant.getFileId());
				FileUpload fileUpload = loadFileUpload(regMerchant.getFileId());

				fileUpload.setMerchantId(merchant.getId().toString());

				FileUpload fileUpload1 = merchantDAO.updateFileById(fileUpload);

				// System.out.println("Merchant service MerchantId Data :
				// "+fileUpload1.getFileId());

			}

		}

		logger.info("gonna edit merch" + merchant.getId());

		merchantDAO.saveOrUpdateEntity1(merchant);

		String ezypassmid = null, mid = null, ezymotomid = null, ezywaymid = null, ezyrecmid = null;
		String um_ezypassmid = null, um_mid = null, um_ezymotomid = null, um_ezywaymid = null, um_ezyrecmid = null;

		MID midTab = null, motoMidTab = null, ezypassMidTab = null, ezyrecMidTab = null, ezywayMidTab = null;
		MID um_midTab = null, um_motoMidTab = null, um_ezypassMidTab = null, um_ezyrecMidTab = null,
				um_ezywayMidTab = null;
		MID mID = null;

		if (regMerchant.getMid() != null) {
			if (!(regMerchant.getMid().isEmpty())) {
				mid = regMerchant.getMid();
				if (regMerchant.getMid().length() <= 15) {
					for (int i = mid.length(); i < 15; i++) {
						mid = "0" + mid;

					}
					logger.info("mid: " + mid);

				}
				// midTab = merchantDAO.loadMidtoUpdateAudit(mid);

			}
		}

		if (regMerchant.getEzymotomid() != null) {
			if (!(regMerchant.getEzymotomid().isEmpty())) {
				ezymotomid = regMerchant.getEzymotomid();
				if (regMerchant.getEzymotomid().length() <= 15) {
					for (int i = ezymotomid.length(); i < 15; i++) {
						ezymotomid = "0" + ezymotomid;
					}
					logger.info("ezymotomid: " + ezymotomid);

				}
				// motoMidTab = merchantDAO.loadMidtoUpdateAudit(ezymotomid);

			}
		}

		if (regMerchant.getEzypassmid() != null) {
			if (!(regMerchant.getEzypassmid().isEmpty())) {
				ezypassmid = regMerchant.getEzypassmid();
				if (regMerchant.getEzypassmid().length() <= 15) {
					for (int i = ezypassmid.length(); i < 15; i++) {
						ezypassmid = "0" + ezypassmid;

					}
					logger.info("ezypassmid: " + ezypassmid);
					// ezypassMidTab = merchantDAO.loadMidtoUpdateAudit(ezypassmid);

				}
			}
		}

		if (regMerchant.getEzyrecmid() != null) {
			if (!(regMerchant.getEzyrecmid().isEmpty())) {
				ezyrecmid = regMerchant.getEzyrecmid();
				if (regMerchant.getEzyrecmid().length() <= 15) {
					for (int i = ezyrecmid.length(); i < 15; i++) {
						ezyrecmid = "0" + ezyrecmid;

					}
					logger.info("ezyrecmid: " + ezyrecmid);
					// ezyrecMidTab = merchantDAO.loadMidtoUpdateAudit(ezyrecmid);

				}
			}
		}
		if (regMerchant.getEzywaymid() != null) {
			if (!(regMerchant.getEzywaymid().isEmpty())) {
				ezywaymid = regMerchant.getEzywaymid();
				if (regMerchant.getEzywaymid().length() <= 15) {
					for (int i = ezywaymid.length(); i < 15; i++) {
						ezywaymid = "0" + ezywaymid;
					}
					logger.info("ezywaymid: " + ezywaymid);
					// ezywayMidTab =merchantDAO.loadMidtoUpdateAudit(ezywaymid);

				}
			}
		}

		// umobile

		if (regMerchant.getUmMid() != null) {
			if (!(regMerchant.getUmMid().isEmpty())) {
				um_mid = regMerchant.getUmMid();
				if (regMerchant.getUmMid().length() <= 15) {
					for (int i = um_mid.length(); i < 15; i++) {
						um_mid = "0" + um_mid;

					}
					logger.info("um_mid: " + um_mid);

				}
				// midTab = merchantDAO.loadMidtoUpdateAudit(um_mid);

			}
		}

		if (regMerchant.getUmMotoMid() != null) {
			if (!(regMerchant.getUmMotoMid().isEmpty())) {
				um_ezymotomid = regMerchant.getUmMotoMid();
				if (regMerchant.getUmMotoMid().length() <= 15) {
					for (int i = um_ezymotomid.length(); i < 15; i++) {
						um_ezymotomid = "0" + um_ezymotomid;
					}
					logger.info("ezymotomid: " + um_ezymotomid);

				}
				// motoMidTab = merchantDAO.loadMidtoUpdateAudit(um_ezymotomid);

			}
		}

		if (regMerchant.getUmEzypassMid() != null) {
			if (!(regMerchant.getUmEzypassMid().isEmpty())) {
				um_ezypassmid = regMerchant.getUmEzypassMid();
				if (regMerchant.getUmEzypassMid().length() <= 15) {
					for (int i = um_ezypassmid.length(); i < 15; i++) {
						um_ezypassmid = "0" + um_ezypassmid;

					}
					logger.info("um_ezypassmid: " + ezypassmid);
					// ezypassMidTab = merchantDAO.loadMidtoUpdateAudit(um_ezypassmid);

				}
			}
		}

		if (regMerchant.getUmEzyrecMid() != null) {
			if (!(regMerchant.getUmEzyrecMid().isEmpty())) {
				um_ezyrecmid = regMerchant.getUmEzyrecMid();
				if (regMerchant.getUmEzyrecMid().length() <= 15) {
					for (int i = um_ezyrecmid.length(); i < 15; i++) {
						um_ezyrecmid = "0" + um_ezyrecmid;

					}
					logger.info("um_ezyrecmid: " + um_ezypassmid);
					// ezyrecMidTab = merchantDAO.loadMidtoUpdateAudit(um_ezyrecmid);

				}
			}
		}
		if (regMerchant.getUmEzywayMid() != null) {
			if (!(regMerchant.getUmEzywayMid().isEmpty())) {
				um_ezywaymid = regMerchant.getUmEzywayMid();
				if (regMerchant.getUmEzywayMid().length() <= 15) {
					for (int i = um_ezywaymid.length(); i < 15; i++) {
						um_ezywaymid = "0" + um_ezywaymid;
					}
					logger.info("um_ezywaymid: " + um_ezywaymid);
					// ezywayMidTab =merchantDAO.loadMidtoUpdateAudit(um_ezywaymid);

				}
			}
		}

		// uMobile changes

		logger.info("regMerchant.getMerchantType(): " + regMerchant.getMerchantType());
		if (regMerchant.getMerchantType() != null) {
			if (regMerchant.getMerchantType().equals("P")) {
				if (merchant.getMid().getMid() != null) {
					mID = merchantDAO.loadMid(merchant.getMid().getMid());
				} else if (merchant.getMid().getMotoMid() != null) {
					mID = merchantDAO.loadMotoMid(merchant.getMid().getMotoMid());
				} else if (merchant.getMid().getEzypassMid() != null) {
					mID = merchantDAO.loadEzyPassMid(merchant.getMid().getEzypassMid());
				} else if (merchant.getMid().getEzyrecMid() != null) {
					mID = merchantDAO.loadEzyrecMid(merchant.getMid().getEzyrecMid());
				} else if (merchant.getMid().getEzywayMid() != null) {
					mID = merchantDAO.loadEzywayMid(merchant.getMid().getEzywayMid());
				}
			} else {
				if (merchant.getMid().getUmMid() != null) {
					mID = merchantDAO.loadUMMid(merchant.getMid().getUmMid());
				} else if (merchant.getMid().getUmEzypassMid() != null) {
					mID = merchantDAO.loadUMEzyPassMid(merchant.getMid().getUmEzypassMid());
				} else if (merchant.getMid().getUmEzyrecMid() != null) {
					mID = merchantDAO.loadUMEzyrecMid(merchant.getMid().getUmEzyrecMid());
				} else if (merchant.getMid().getUmEzywayMid() != null) {
					mID = merchantDAO.loadUMEzywayMid(merchant.getMid().getUmEzywayMid());
				} else if (merchant.getMid().getUmMotoMid() != null) {
					mID = merchantDAO.loadUMMotoMid(merchant.getMid().getUmMotoMid());
				}
			}
		} else {
			if (merchant.getMid().getMid() != null) {
				mID = merchantDAO.loadMid(merchant.getMid().getMid());
			} else if (merchant.getMid().getMotoMid() != null) {
				mID = merchantDAO.loadMotoMid(merchant.getMid().getMotoMid());
			} else if (merchant.getMid().getEzypassMid() != null) {
				mID = merchantDAO.loadEzyPassMid(merchant.getMid().getEzypassMid());
			} else if (merchant.getMid().getEzyrecMid() != null) {
				mID = merchantDAO.loadEzyrecMid(merchant.getMid().getEzyrecMid());
			} else if (merchant.getMid().getEzywayMid() != null) {
				mID = merchantDAO.loadEzywayMid(merchant.getMid().getEzywayMid());
			}

		}

		/*
		 * if(regMerchant.getMerchantType()==null) { if (merchant.getMid().getMid() !=
		 * null) { mID = merchantDAO.loadMid(merchant.getMid().getMid()); } else if
		 * (merchant.getMid().getMotoMid() != null) { mID =
		 * merchantDAO.loadMotoMid(merchant.getMid().getMotoMid()); } else if
		 * (merchant.getMid().getEzypassMid() != null) { mID =
		 * merchantDAO.loadEzyPassMid(merchant.getMid().getEzypassMid()); } else if
		 * (merchant.getMid().getEzyrecMid() != null) { mID =
		 * merchantDAO.loadEzyrecMid(merchant.getMid().getEzyrecMid()); } else if
		 * (merchant.getMid().getEzywayMid() != null) { mID =
		 * merchantDAO.loadEzywayMid(merchant.getMid().getEzywayMid()); } }else { if
		 * (merchant.getMid().getUmMid() != null) { mID =
		 * merchantDAO.loadUMMid(merchant.getMid().getUmMid()); }else if
		 * (merchant.getMid().getUmEzypassMid() != null) { mID =
		 * merchantDAO.loadUMEzyPassMid(merchant.getMid().getUmEzypassMid()); }else if
		 * (merchant.getMid().getUmEzyrecMid() != null) { mID =
		 * merchantDAO.loadUMEzyrecMid(merchant.getMid().getUmEzyrecMid()); }else if
		 * (merchant.getMid().getUmEzywayMid() != null) { mID =
		 * merchantDAO.loadUMEzywayMid(merchant.getMid().getUmEzywayMid()); }else if
		 * (merchant.getMid().getUmMotoMid() != null) { mID =
		 * merchantDAO.loadUMMotoMid(merchant.getMid().getUmMotoMid()); }
		 * 
		 * }
		 */

		if ((merchant.getMid().getMid() == null) && (mid != null)) {
			mID.setMid(mid);
		}
		if ((merchant.getMid().getMotoMid() == null) && (ezymotomid != null)) {
			mID.setMotoMid(ezymotomid);
		}
		if ((merchant.getMid().getEzywayMid() == null) && (ezywaymid != null)) {
			mID.setEzywayMid(ezywaymid);
		}
		if ((merchant.getMid().getEzyrecMid() == null) && (ezyrecmid != null)) {
			mID.setEzyrecMid(ezyrecmid);
		}
		if ((merchant.getMid().getEzypassMid() == null) && (ezypassmid != null)) {
			mID.setEzypassMid(ezypassmid);
		}
		logger.info("ummid:" + um_mid);
		if ((merchant.getMid().getUmMid() == null) && (um_mid != null)) {
			mID.setUmMid(um_mid);
		}
		if ((merchant.getMid().getUmMotoMid() == null) && (um_ezymotomid != null)) {
			mID.setUmMotoMid(um_ezymotomid);
		}
		if ((merchant.getMid().getUmEzywayMid() == null) && (um_ezywaymid != null)) {
			logger.info("um_ezywaymid if:" + um_ezywaymid);
			logger.info("mID" + mID.getUmEzywayMid());
			mID.setUmEzywayMid(um_ezywaymid);
		}
		if ((merchant.getMid().getUmEzyrecMid() == null) && (um_ezyrecmid != null)) {
			mID.setUmEzyrecMid(um_ezyrecmid);
		}
		if ((merchant.getMid().getUmEzypassMid() == null) && (um_ezypassmid != null)) {
			mID.setUmEzypassMid(um_ezypassmid);
		}

		/*
		 * if (merchant.getMid().getMid() == null) { mID.setMid(mid); } if
		 * (merchant.getMid().getMotoMid() == null) { mID.setMotoMid(ezymotomid); } if
		 * (merchant.getMid().getEzywayMid() == null) { mID.setEzywayMid(ezywaymid); }
		 * if (merchant.getMid().getEzyrecMid() == null) { mID.setEzyrecMid(ezyrecmid);
		 * } if (merchant.getMid().getEzypassMid() == null) {
		 * mID.setEzypassMid(ezypassmid); } logger.info("ummid:"+um_mid); if
		 * (merchant.getMid().getUmMid() == null) { mID.setUmMid(um_mid); } if
		 * (merchant.getMid().getUmMotoMid() == null) { mID.setUmMotoMid(um_ezymotomid);
		 * } if (merchant.getMid().getUmEzywayMid() == null) {
		 * mID.setUmEzywayMid(um_ezywaymid); } if (merchant.getMid().getUmEzyrecMid() ==
		 * null) { mID.setUmEzyrecMid(um_ezyrecmid); } if
		 * (merchant.getMid().getUmEzypassMid() == null) {
		 * mID.setUmEzypassMid(um_ezypassmid); }
		 */

		mID = merchantDAO.saveOrUpdateEntity(mID);

		// merchant.setMid(mID);

		return merchant;

	}

	// @javax.transaction.Transactional
	public Merchant updateMerchants(final RegAddMerchant regMerchant, Merchant merchant, String mailStatus,
									String manualSettlement, MerchantInfo merchantInfo, String accountEnquiryEnabled,String quickPayoutEnabled/*,String quickPayoutUrl*/) {
		// Merchant merchant1=new Merchant();

		/*
		 * Agent agentOldDetail = agentDAO.loadAgentDetailsbyId(merchant.getAgID());
		 * 
		 * SecondaryAgent secAgentHistory = new SecondaryAgent();
		 * 
		 * secAgentHistory.setAgName(agentOldDetail.getFirstName());
		 * secAgentHistory.setPrimaryId(agentOldDetail.getId().toString());
		 * secAgentHistory.setChangedBy("EDIT MERCHANT");
		 * secAgentHistory.setCreatedBy("PORTAL");
		 * secAgentHistory.setReason("MERCHANT UPDATE"); secAgentHistory=
		 * merchantDAO.saveOrUpdateEntity(secAgentHistory);
		 */
		String agentData = regMerchant.getAgentName();
		agentData = agentData.replace(",", "");
		// String agentData = entity.getSignedPackage();

		logger.info("agentData:::::: " + agentData);
		logger.info("regMerchant.getId():::::: " + regMerchant.getId());
		String mailId = null;
		String type = null;
		Agent agent = null;
		SubAgent subAgent = null;
		// if(agentData != null || !(agentData.equals(""))){
		if (!(agentData.equals(""))) {
			String agentmail[] = agentData.split("~");
			// System.out.println("Agent Data "+agentmail);
			type = agentmail[0];
			mailId = agentmail[2];
		}
		merchant.setId(Long.parseLong(regMerchant.getId()));
		// System.out.println(" Data Id "+merchant.getId() + " "+merchant.getMid());

		logger.info("type:::::: " + type);

		// System.out.println("Agent Mail : "+ mailId+" "+type);
		if (type.equals("AGENT")) {
			agent = agentDAO.loadAgentbyMailId(mailId);

			logger.info("Agent Data : " + agent.getId());

			// System.out.println("Agent Data : "+ agent.getId());
		}
		if (type.equals("SUBAGENT")) {
			subAgent = subAgentDAO.loadAgentbyMailId(mailId);

			logger.info("SubAgent Data : " + subAgent.getId());

			// System.out.println("SubAgent Data : "+ subAgent.getId());
		}
		/* merchant1.getId(merchant.setId().toString()); */

		BigInteger bi = null;
		if (agent != null) {

			bi = new BigInteger(agent.getId().toString());

			logger.info("Data agent : " + bi);

			// System.out.println("Data agent : "+ bi);
		}

		BigInteger subAgID = null;
		if (subAgent != null) {
			subAgID = new BigInteger(subAgent.getId().toString());

			logger.info("Data subagent : " + subAgID);

			// System.out.println("Data subagent : "+ subAgID);
		}

		merchant.setAgID(bi);
		merchant.setSubAgID(subAgID);

		// umobile mid

		// }

		merchant.setBusinessName(regMerchant.getBusinessName().toUpperCase());
		merchant.setBusinessShortName(regMerchant.getRegisteredName());
		merchant.setBusinessRegistrationNumber(regMerchant.getBusinessRegNo());
		merchant.setBusinessAddress1(regMerchant.getRegisteredAddress());
		merchant.setBusinessAddress2(regMerchant.getBusinessAddress());
		merchant.setBusinessAddress3(regMerchant.getMailingAddress());
		merchant.setSalutation(regMerchant.getSalutation());
		merchant.setContactPersonName(regMerchant.getName());
		merchant.setContactPersonPhoneNo(regMerchant.getContactNo());
		merchant.setEmail(regMerchant.getEmail());
		merchant.setWebsite(regMerchant.getWebsite());
		merchant.setBusinessContactNumber(regMerchant.getOfficeNo());
		merchant.setFaxNo(regMerchant.getFaxNo());
		merchant.setBusinessType(regMerchant.getBusinessType());
		merchant.setCompanyType(regMerchant.getCompanyType());
		merchant.setNatureOfBusiness(regMerchant.getNatureOfBusiness());
		merchant.setPermiseType(regMerchant.getDocuments());
		merchant.setBankName(regMerchant.getBankName());
		merchant.setBankAcc(regMerchant.getBankAccNo());
		merchant.setState(regMerchant.getBusinessState());
		merchant.setCity(regMerchant.getBusinessCity());
		merchant.setPostcode(regMerchant.getBusinessPostCode());
		merchant.setReferralId(regMerchant.getReferralId());
		merchant.setWaiverMonth(regMerchant.getWavierMonth());
		merchant.setTradingName(regMerchant.getTradingName());
		merchant.setReaderSerialNo(regMerchant.getNoOfReaders());
		merchant.setYearIncorporated(regMerchant.getYearIncorporated());
		merchant.setSignedPackage(regMerchant.getSignedPackage());
		merchant.setRemarks(regMerchant.getStatusRemarks());
		merchant.setAccType(regMerchant.getAccType());

		String ownerSalutation = "";
		String ownerName = "";
		String passportNo = "";
		String residentialAddress = "";
		String ownerContactNo = "";

		// System.out.println(" Owner's Count :"+merchant.getOwnerCount());

		if (regMerchant.getOwnerCount() == "2" || regMerchant.getOwnerCount().equals("2")) {
			ownerSalutation = regMerchant.getOwnerSalutation1() + "~" + regMerchant.getOwnerSalutation2();
			ownerName = regMerchant.getOwnerName1() + "~" + regMerchant.getOwnerName2();
			passportNo = regMerchant.getPassportNo1() + "~" + regMerchant.getPassportNo2();
			ownerContactNo = regMerchant.getOwnerContactNo1() + "~" + regMerchant.getOwnerContactNo2();
			residentialAddress = regMerchant.getResidentialAddress1() + "~" + regMerchant.getResidentialAddress2();

		} else if (regMerchant.getOwnerCount() == "3" || regMerchant.getOwnerCount().equals("3")) {
			ownerSalutation = regMerchant.getOwnerSalutation1() + "~" + regMerchant.getOwnerSalutation2() + "~"
					+ regMerchant.getOwnerSalutation3();
			ownerName = regMerchant.getOwnerName1() + "~" + regMerchant.getOwnerName2() + "~"
					+ regMerchant.getOwnerName3();
			passportNo = regMerchant.getPassportNo1() + "~" + regMerchant.getPassportNo2() + "~"
					+ regMerchant.getPassportNo3();
			ownerContactNo = regMerchant.getOwnerContactNo1() + "~" + regMerchant.getOwnerContactNo2() + "~"
					+ regMerchant.getOwnerContactNo3();
			residentialAddress = regMerchant.getResidentialAddress1() + "~" + regMerchant.getResidentialAddress2() + "~"
					+ regMerchant.getResidentialAddress3();

		} else if (regMerchant.getOwnerCount() == "4" || regMerchant.getOwnerCount().equals("4")) {
			ownerSalutation = regMerchant.getOwnerSalutation1() + "~" + regMerchant.getOwnerSalutation2() + "~"
					+ regMerchant.getOwnerSalutation3() + "~" + regMerchant.getOwnerSalutation4();
			ownerName = regMerchant.getOwnerName1() + "~" + regMerchant.getOwnerName2() + "~"
					+ regMerchant.getOwnerName3() + "~" + regMerchant.getOwnerName4();
			passportNo = regMerchant.getPassportNo1() + "~" + regMerchant.getPassportNo2() + "~"
					+ regMerchant.getPassportNo3() + "~" + regMerchant.getPassportNo4();
			ownerContactNo = regMerchant.getOwnerContactNo1() + "~" + regMerchant.getOwnerContactNo2() + "~"
					+ regMerchant.getOwnerContactNo3() + "~" + regMerchant.getOwnerContactNo4();
			residentialAddress = regMerchant.getResidentialAddress1() + "~" + regMerchant.getResidentialAddress2() + "~"
					+ regMerchant.getResidentialAddress3() + "~" + regMerchant.getResidentialAddress4();

		} else if (regMerchant.getOwnerCount() == "5" || regMerchant.getOwnerCount().equals("5")) {
			ownerSalutation = regMerchant.getOwnerSalutation1() + "~" + regMerchant.getOwnerSalutation2() + "~"
					+ regMerchant.getOwnerSalutation3() + "~" + regMerchant.getOwnerSalutation4() + "~"
					+ regMerchant.getOwnerSalutation5();
			ownerName = regMerchant.getOwnerName1() + "~" + regMerchant.getOwnerName2() + "~"
					+ regMerchant.getOwnerName3() + "~" + regMerchant.getOwnerName4() + "~"
					+ regMerchant.getOwnerName5();
			passportNo = regMerchant.getPassportNo1() + "~" + regMerchant.getPassportNo2() + "~"
					+ regMerchant.getPassportNo3() + "~" + regMerchant.getPassportNo4() + "~"
					+ regMerchant.getPassportNo5();
			ownerContactNo = regMerchant.getOwnerContactNo1() + "~" + regMerchant.getOwnerContactNo2() + "~"
					+ regMerchant.getOwnerContactNo3() + "~" + regMerchant.getOwnerContactNo4() + "~"
					+ regMerchant.getOwnerContactNo5();
			residentialAddress = regMerchant.getResidentialAddress1() + "~" + regMerchant.getResidentialAddress2() + "~"
					+ regMerchant.getResidentialAddress3() + "~" + regMerchant.getResidentialAddress4() + "~"
					+ regMerchant.getResidentialAddress5();

		} else {
			ownerSalutation = regMerchant.getOwnerSalutation1();
			ownerName = regMerchant.getOwnerName1();
			passportNo = regMerchant.getPassportNo1();
			ownerContactNo = regMerchant.getOwnerContactNo1();
			residentialAddress = regMerchant.getResidentialAddress1();

		}

		merchant.setOwnerSalutation(ownerSalutation);
		merchant.setOwnerName(ownerName);
		merchant.setOwnerPassportNo(passportNo);
		merchant.setOwnerContactNo(ownerContactNo);
		merchant.setResidentialAddress(residentialAddress);

		merchant.setMdr(regMerchant.getMdr());
		merchant.setPreAuth(regMerchant.getPreAuth());
		merchant.setAuth3DS(regMerchant.getAuth3DS());
		merchant.setAutoSettled(regMerchant.getAutoSettled());

		merchant.setModifiedBy(regMerchant.getEzysettle());

		/* ManualSettlement */

		try {

			merchantDAO.findMobileuserByMerchantId(merchant, manualSettlement);
			merchantDAO.updateAccountEnquiryAndQuickPayout(merchant,accountEnquiryEnabled,quickPayoutEnabled/*,quickPayoutUrl*/);
			logger.info("findMobileuserByMerchantId  :");

			logger.info("manualSettlement : " + manualSettlement);

		} catch (Exception e) {
			logger.error("Error updating MobileUser manual settlement: " + e.getMessage(), e);
		}

		/* ManualSettlement */

		logger.info("regezysettle: " + regMerchant.getEzysettle());
		// merchant.setForeignCard(regMerchant.getForeignCard());
		logger.info("regMerchant.getForeignCard(): " + regMerchant.getForeignCard());
		logger.info("regMerchant.getStatus(): " + regMerchant.getStatus());

		DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

		if ("Suspended".equalsIgnoreCase(regMerchant.getStatus())) {
			merchant.setStatus(CommonStatus.SUSPENDED);
		} else if ("Terminated".equalsIgnoreCase(regMerchant.getStatus())) {
			merchant.setStatus(CommonStatus.TERMINATED);
		} else if ("Active".equalsIgnoreCase(regMerchant.getStatus())) {
			merchant.setStatus(CommonStatus.ACTIVE);
		}
		LocalDateTime timestamp = LocalDateTime.now();
		String formattedTimestamp = timestamp.format(FORMATTER);
		logger.info("Merchant Status Changed to: " + merchant.getStatus() + " at " + formattedTimestamp);

		if (regMerchant.getStatus() != null) {
			if (regMerchant.getStatus().equalsIgnoreCase("ACTIVE")) {
				merchant.setActivateDate(new Date());
				merchant.setStatus(CommonStatus.ACTIVE);
				RandomPassword rpwd = new RandomPassword();
				String genPwd = rpwd.generateRandomString();
				merchant.setPassword(encoder.encode(genPwd));
			} else if (regMerchant.getStatus().equalsIgnoreCase("PENDING")) {
				merchant.setUsername(regMerchant.getOfficeEmail());
				merchant.setCreatedDate(new Date());
				merchant.setStatus(CommonStatus.PENDING);
			} else if (regMerchant.getStatus().equalsIgnoreCase("SUBMITTED")) {
				merchant.setUsername(regMerchant.getOfficeEmail());
				merchant.setModifiedDate(new Date());
				merchant.setStatus(CommonStatus.SUBMITTED);
			} else if (regMerchant.getStatus().equalsIgnoreCase("REJECTED")) {
				merchant.setUsername(regMerchant.getOfficeEmail());
				merchant.setModifiedDate(new Date());
				merchant.setStatus(CommonStatus.REJECTED);
			}
		}
		if (regMerchant.getFileId() != null) {

			logger.info("regMerchant.getFileId(): ");

			// System.out.println(" Inside File Cond");
			String fileIds[] = null;
			if (regMerchant.getFileId().contains("~")) {
				fileIds = regMerchant.getFileId().split("~");
				for (int i = 0; i < fileIds.length; i++) {

					// FileUpload fileUpload = loadFileUpload(entity.getFileId());
					// System.out.println(" Inside File loop :"+fileIds[i]);
					FileUpload fileUpload = loadFileUpload(fileIds[i]);

					fileUpload.setMerchantId(merchant.getId().toString());

					FileUpload fileUpload1 = merchantDAO.updateFileById(fileUpload);

					// System.out.println("Merchant service MerchantId Data :
					// "+fileUpload1.getFileId());
				}
			} else {
				// System.out.println(" Inside File Cond else");
				// fileIds[0]= merchant.getFileId();

				// FileUpload fileUpload = loadFileUpload(entity.getFileId());
				// System.out.println(" Inside File loop :"+merchant.getFileId());
				FileUpload fileUpload = loadFileUpload(regMerchant.getFileId());

				fileUpload.setMerchantId(merchant.getId().toString());

				FileUpload fileUpload1 = merchantDAO.updateFileById(fileUpload);

				// System.out.println("Merchant service MerchantId Data :
				// "+fileUpload1.getFileId());

			}

		}

		logger.info("gonna edit merch" + merchant.getId());

		merchantDAO.saveOrUpdateEntity1(merchant);
		// insert or update secondary email on basis on condition
		if(merchantInfo!=null && merchantInfo.getId()==null) {
			logger.info("perform insert ");
			merchantDAO.insertMerchantIfNotFound(String.valueOf(merchant.getId()),merchantInfo.getSecoundaryEmail());
		}else if (merchantInfo!=null && merchantInfo.getId()!=null){
			logger.info("perform update ");
			merchantDAO.updateSecondaryEmail(String.valueOf(merchant.getId()),merchantInfo.getSecoundaryEmail());
		}

		String ezypassmid = null, mid = null, ezymotomid = null, ezywaymid = null, ezyrecmid = null;
		String um_ezypassmid = null, um_mid = null, um_ezymotomid = null, um_ezywaymid = null, um_ezyrecmid = null, fiuuMid = null;

		MID midTab = null, motoMidTab = null, ezypassMidTab = null, ezyrecMidTab = null, ezywayMidTab = null;
		MID um_midTab = null, um_motoMidTab = null, um_ezypassMidTab = null, um_ezyrecMidTab = null,
				um_ezywayMidTab = null;
		MID mID = null;

		if (regMerchant.getMid() != null) {
			if (!(regMerchant.getMid().isEmpty())) {
				mid = regMerchant.getMid();
				if (regMerchant.getMid().length() <= 15) {
					for (int i = mid.length(); i < 15; i++) {
						mid = "0" + mid;

					}
					logger.info("mid: " + mid);

				}
				// midTab = merchantDAO.loadMidtoUpdateAudit(mid);

			}
		}

		if (regMerchant.getEzymotomid() != null) {
			if (!(regMerchant.getEzymotomid().isEmpty())) {
				ezymotomid = regMerchant.getEzymotomid();
				if (regMerchant.getEzymotomid().length() <= 15) {
					for (int i = ezymotomid.length(); i < 15; i++) {
						ezymotomid = "0" + ezymotomid;
					}
					logger.info("ezymotomid: " + ezymotomid);

				}
				// motoMidTab = merchantDAO.loadMidtoUpdateAudit(ezymotomid);

			}
		}

		if (regMerchant.getEzypassmid() != null) {
			if (!(regMerchant.getEzypassmid().isEmpty())) {
				ezypassmid = regMerchant.getEzypassmid();
				if (regMerchant.getEzypassmid().length() <= 15) {
					for (int i = ezypassmid.length(); i < 15; i++) {
						ezypassmid = "0" + ezypassmid;

					}
					logger.info("ezypassmid: " + ezypassmid);
					// ezypassMidTab = merchantDAO.loadMidtoUpdateAudit(ezypassmid);

				}
			}
		}

		if (regMerchant.getEzyrecmid() != null) {
			if (!(regMerchant.getEzyrecmid().isEmpty())) {
				ezyrecmid = regMerchant.getEzyrecmid();
				if (regMerchant.getEzyrecmid().length() <= 15) {
					for (int i = ezyrecmid.length(); i < 15; i++) {
						ezyrecmid = "0" + ezyrecmid;

					}
					logger.info("ezyrecmid: " + ezyrecmid);
					// ezyrecMidTab = merchantDAO.loadMidtoUpdateAudit(ezyrecmid);

				}
			}
		}
		if (regMerchant.getEzywaymid() != null) {
			if (!(regMerchant.getEzywaymid().isEmpty())) {
				ezywaymid = regMerchant.getEzywaymid();
				if (regMerchant.getEzywaymid().length() <= 15) {
					for (int i = ezywaymid.length(); i < 15; i++) {
						ezywaymid = "0" + ezywaymid;
					}
					logger.info("ezywaymid: " + ezywaymid);
					// ezywayMidTab =merchantDAO.loadMidtoUpdateAudit(ezywaymid);

				}
			}
		}

		// umobile

		if (regMerchant.getUmMid() != null) {
			if (!(regMerchant.getUmMid().isEmpty())) {
				um_mid = regMerchant.getUmMid();
				if (regMerchant.getUmMid().length() <= 15) {
					for (int i = um_mid.length(); i < 15; i++) {
						um_mid = "0" + um_mid;

					}
					logger.info("um_mid: " + um_mid);

				}
				// midTab = merchantDAO.loadMidtoUpdateAudit(um_mid);

			}
		}

		if (regMerchant.getUmMotoMid() != null) {
			if (!(regMerchant.getUmMotoMid().isEmpty())) {
				um_ezymotomid = regMerchant.getUmMotoMid();
				if (regMerchant.getUmMotoMid().length() <= 15) {
					for (int i = um_ezymotomid.length(); i < 15; i++) {
						um_ezymotomid = "0" + um_ezymotomid;
					}
					logger.info("ezymotomid: " + um_ezymotomid);

				}
				// motoMidTab = merchantDAO.loadMidtoUpdateAudit(um_ezymotomid);

			}
		}

		if (regMerchant.getUmEzypassMid() != null) {
			if (!(regMerchant.getUmEzypassMid().isEmpty())) {
				um_ezypassmid = regMerchant.getUmEzypassMid();
				if (regMerchant.getUmEzypassMid().length() <= 15) {
					for (int i = um_ezypassmid.length(); i < 15; i++) {
						um_ezypassmid = "0" + um_ezypassmid;

					}
					logger.info("um_ezypassmid: " + ezypassmid);
					// ezypassMidTab = merchantDAO.loadMidtoUpdateAudit(um_ezypassmid);

				}
			}
		}

		if (regMerchant.getUmEzyrecMid() != null) {
			if (!(regMerchant.getUmEzyrecMid().isEmpty())) {
				um_ezyrecmid = regMerchant.getUmEzyrecMid();
				if (regMerchant.getUmEzyrecMid().length() <= 15) {
					for (int i = um_ezyrecmid.length(); i < 15; i++) {
						um_ezyrecmid = "0" + um_ezyrecmid;

					}
					logger.info("um_ezyrecmid: " + um_ezypassmid);
					// ezyrecMidTab = merchantDAO.loadMidtoUpdateAudit(um_ezyrecmid);

				}
			}
		}
		if (regMerchant.getUmEzywayMid() != null) {
			if (!(regMerchant.getUmEzywayMid().isEmpty())) {
				um_ezywaymid = regMerchant.getUmEzywayMid();
				if (regMerchant.getUmEzywayMid().length() <= 15) {
					for (int i = um_ezywaymid.length(); i < 15; i++) {
						um_ezywaymid = "0" + um_ezywaymid;
					}
					logger.info("um_ezywaymid: " + um_ezywaymid);
					// ezywayMidTab =merchantDAO.loadMidtoUpdateAudit(um_ezywaymid);

				}
			}
		}
		
		if(regMerchant.getFiuuMid() != null) {
			fiuuMid = regMerchant.getFiuuMid();
		}
		
		logger.info("reg fiuu mid : "+fiuuMid);

		// uMobile changes

		logger.info("regMerchant.getMerchantType(): " + regMerchant.getMerchantType());
		if (regMerchant.getMerchantType() != null) {
			if (regMerchant.getMerchantType().equals("P")) {
				if (merchant.getMid().getMid() != null) {
					mID = merchantDAO.loadMid(merchant.getMid().getMid());
				} else if (merchant.getMid().getMotoMid() != null) {
					mID = merchantDAO.loadMotoMid(merchant.getMid().getMotoMid());
				} else if (merchant.getMid().getEzypassMid() != null) {
					mID = merchantDAO.loadEzyPassMid(merchant.getMid().getEzypassMid());
				} else if (merchant.getMid().getEzyrecMid() != null) {
					mID = merchantDAO.loadEzyrecMid(merchant.getMid().getEzyrecMid());
				} else if (merchant.getMid().getEzywayMid() != null) {
					mID = merchantDAO.loadEzywayMid(merchant.getMid().getEzywayMid());
				}
			} else {
				if (merchant.getMid().getUmMid() != null) {
					logger.info("UMmid : " + merchant.getMid().getUmMid());
					mID = merchantDAO.loadUMMid(merchant.getMid().getUmMid());
				}
//				else if (merchant.getMid().getUmMid() == null) {
//					mID = merchantDAO.loadUMEzywayMid(merchant.getMid().getUmEzywayMid());
//				}
				else if (merchant.getMid().getUmEzypassMid() != null) {
					logger.info("UMEzypass Mid : " + merchant.getMid().getUmEzypassMid());
					mID = merchantDAO.loadUMEzyPassMid(merchant.getMid().getUmEzypassMid());
				} else if (merchant.getMid().getUmEzyrecMid() != null) {
					logger.info("UMEzyrec Mid : " + merchant.getMid().getUmEzypassMid());
					mID = merchantDAO.loadUMEzyrecMid(merchant.getMid().getUmEzyrecMid());
				} else if (merchant.getMid().getUmEzywayMid() != null) {
					logger.info("UMEzyway Mid : " + merchant.getMid().getUmEzywayMid());
					mID = merchantDAO.loadUMEzywayMid(merchant.getMid().getUmEzywayMid());
				} else if (merchant.getMid().getUmMotoMid() != null) {
					logger.info("UMMoto Mid : " + merchant.getMid().getUmMotoMid());
					mID = merchantDAO.loadUMMotoMid(merchant.getMid().getUmMotoMid());
				} else if (merchant.getMid().getFpxMid() != null) {
					logger.info("Fpx Mid : " + merchant.getMid().getFpxMid());
					mID = merchantDAO.loadFPXMid(merchant.getMid().getFpxMid());
				} else if (merchant.getMid().getBoostMid() != null) {
					logger.info("Boost Mid : " + merchant.getMid().getBoostMid());
					mID = merchantDAO.loadBoostMid(merchant.getMid().getBoostMid());
				} else if (merchant.getMid().getFiuuMid() != null) {
					logger.info("Fiuu Mid : " + merchant.getMid().getFiuuMid());
					mID = merchantDAO.loadFiuuMid(merchant.getMid().getFiuuMid());
				}else {
					logger.info("inside else block if merchant id null");
					mID = merchantDAO.loadEwalletMid(merchant.getMid().getMid());
					logger.info("MID: " + mID);
				} 
//				else {
//					mID = merchant.getMid();
//				}

			}
		} else {
			if (merchant.getMid().getMid() != null) {
				mID = merchantDAO.loadMid(merchant.getMid().getMid());
			} else if (merchant.getMid().getMotoMid() != null) {
				mID = merchantDAO.loadMotoMid(merchant.getMid().getMotoMid());
			} else if (merchant.getMid().getEzypassMid() != null) {
				mID = merchantDAO.loadEzyPassMid(merchant.getMid().getEzypassMid());
			} else if (merchant.getMid().getEzyrecMid() != null) {
				mID = merchantDAO.loadEzyrecMid(merchant.getMid().getEzyrecMid());
			} else if (merchant.getMid().getEzywayMid() != null) {
				mID = merchantDAO.loadEzywayMid(merchant.getMid().getEzywayMid());
			}

		}

		/*
		 * if(regMerchant.getMerchantType()==null) { if (merchant.getMid().getMid() !=
		 * null) { mID = merchantDAO.loadMid(merchant.getMid().getMid()); } else if
		 * (merchant.getMid().getMotoMid() != null) { mID =
		 * merchantDAO.loadMotoMid(merchant.getMid().getMotoMid()); } else if
		 * (merchant.getMid().getEzypassMid() != null) { mID =
		 * merchantDAO.loadEzyPassMid(merchant.getMid().getEzypassMid()); } else if
		 * (merchant.getMid().getEzyrecMid() != null) { mID =
		 * merchantDAO.loadEzyrecMid(merchant.getMid().getEzyrecMid()); } else if
		 * (merchant.getMid().getEzywayMid() != null) { mID =
		 * merchantDAO.loadEzywayMid(merchant.getMid().getEzywayMid()); } }else { if
		 * (merchant.getMid().getUmMid() != null) { mID =
		 * merchantDAO.loadUMMid(merchant.getMid().getUmMid()); }else if
		 * (merchant.getMid().getUmEzypassMid() != null) { mID =
		 * merchantDAO.loadUMEzyPassMid(merchant.getMid().getUmEzypassMid()); }else if
		 * (merchant.getMid().getUmEzyrecMid() != null) { mID =
		 * merchantDAO.loadUMEzyrecMid(merchant.getMid().getUmEzyrecMid()); }else if
		 * (merchant.getMid().getUmEzywayMid() != null) { mID =
		 * merchantDAO.loadUMEzywayMid(merchant.getMid().getUmEzywayMid()); }else if
		 * (merchant.getMid().getUmMotoMid() != null) { mID =
		 * merchantDAO.loadUMMotoMid(merchant.getMid().getUmMotoMid()); }
		 * 
		 * }
		 */

		if ((merchant.getMid().getMid() == null) && (mid != null)) {
			mID.setMid(mid);
		}
		if ((merchant.getMid().getMotoMid() == null) && (ezymotomid != null)) {
			mID.setMotoMid(ezymotomid);
		}
		if ((merchant.getMid().getEzywayMid() == null) && (ezywaymid != null)) {
			mID.setEzywayMid(ezywaymid);
		}
		if ((merchant.getMid().getEzyrecMid() == null) && (ezyrecmid != null)) {
			mID.setEzyrecMid(ezyrecmid);
		}
		if ((merchant.getMid().getEzypassMid() == null) && (ezypassmid != null)) {
			mID.setEzypassMid(ezypassmid);
		}
		logger.info("ummid:" + um_mid);
		if ((merchant.getMid().getUmMid() == null) && (um_mid != null)) {
			mID.setUmMid(um_mid);
		}
		if ((merchant.getMid().getUmMotoMid() == null) && (um_ezymotomid != null)) {
			mID.setUmMotoMid(um_ezymotomid);
		}
		if ((merchant.getMid().getUmEzywayMid() == null) && (um_ezywaymid != null)) {
			logger.info("um_ezywaymid if:" + um_ezywaymid);
			logger.info("mID" + mID.getUmEzywayMid());
			mID.setUmEzywayMid(um_ezywaymid);
		}
		if ((merchant.getMid().getUmEzyrecMid() == null) && (um_ezyrecmid != null)) {
			mID.setUmEzyrecMid(um_ezyrecmid);
		}
		if ((merchant.getMid().getUmEzypassMid() == null) && (um_ezypassmid != null)) {
			mID.setUmEzypassMid(um_ezypassmid);
		}
		
		if ((merchant.getMid().getFiuuMid() == null) && (fiuuMid != null)) {
			mID.setFiuuMid(fiuuMid);
		}


		MobileUser mobileUserdetails = mobileuserDAO.getMobileUserData(merchant);

		// Payout Max txn limit changes.
		if (regMerchant != null && regMerchant.getMaxPayoutTxnLimit() != null
				&& isGreaterThanZero(regMerchant.getMaxPayoutTxnLimit())
				&& !arePayoutLimitsEqual(mobileUserdetails.getPayoutTransactionLimit(), regMerchant.getMaxPayoutTxnLimit())) {

			logger.info("Payout max transaction limit included, Updating as: " + convertMaxPayoutTxnLimitToBigDecimal(regMerchant));

			mobileUserdetails.setPayoutTransactionLimit(convertMaxPayoutTxnLimitToBigDecimal(regMerchant));
			this.merchantDAO.saveOrUpdateEntity(mobileUserdetails);

			// Trigger e-mail to acknowledge the Merchant/Operation that the MAX payout limit has set.
//			EmailUtils emailUtils = new EmailUtils();
//			emailUtils.sendPayoutMaxTxnLimitHandlerEmailToOperation(merchant, convertMaxPayoutTxnLimitToBigDecimal(regMerchant), userName);
//			emailUtils.sendPayoutMaxTxnLimitHandlerEmailToMerchant(merchant, convertMaxPayoutTxnLimitToBigDecimal(regMerchant), userName);
		} else if (regMerchant != null && regMerchant.getMaxPayoutTxnLimit() != null
				&& isEqual2Zero(regMerchant.getMaxPayoutTxnLimit())) {
//					    && arePayoutLimitsEqual(mobileUserdetails.getPayoutTransactionLimit(), regMerchant.getMaxPayoutTxnLimit())) {

			logger.info("Payout max transaction limit removed, Updating as 'Null'. Given value : " + regMerchant.getMaxPayoutTxnLimit());
			mobileUserdetails.setPayoutTransactionLimit(null);
			this.merchantDAO.saveOrUpdateEntity(mobileUserdetails);
		} else if(regMerchant != null && (regMerchant.getMaxPayoutTxnLimit() == null || regMerchant.getMaxPayoutTxnLimit().trim().isEmpty())) {

			logger.info("Payout max transaction limit removed, Updating as 'Null'. Given value is blank/null: " + regMerchant.getMaxPayoutTxnLimit());
			mobileUserdetails.setPayoutTransactionLimit(null);
			this.merchantDAO.saveOrUpdateEntity(mobileUserdetails);
		} else {
			logger.warn("Payout max transaction limit not updated. Given value: " + regMerchant.getMaxPayoutTxnLimit());
		}

		// Async Payout Changes
		logger.info("Goan to update merchant details, IsAsyncPayoutEnabled: " + regMerchant.getIsAsyncPayoutEnabled()
				+ ", payoutIpnUrl: " + regMerchant.getPayoutIpnUrl() + ", asyncEnableReason: "
				+ regMerchant.getAsyncEnableReason());

		if (regMerchant != null && regMerchant.getIsAsyncPayoutEnabled() != null
				&& regMerchant.getPayoutIpnUrl() != null && mobileUserdetails != null) {

			if (regMerchant.getIsAsyncPayoutEnabled().toUpperCase().equals("NO")
					&& mobileUserdetails.getEnableAsyncPayout() != null
					&& mobileUserdetails.getEnableAsyncPayout().toUpperCase().equals("NO")) {

				// Ignore the UPDATE
				logger.warn(
						"Ignoring the enable async payout enable update because the compared given data and DB data matched.");
			} else if (regMerchant.getIsAsyncPayoutEnabled().toUpperCase().equals("NO")) {

				mobileUserdetails.setEnableAsyncPayout("No");
				merchantDAO.disableAsyncPayoutHandlerDetailsInMobileUser(mobileUserdetails, merchant.getId());
			} else if (regMerchant.getIsAsyncPayoutEnabled().toUpperCase().equals("YES")
					&& mobileUserdetails.getEnableAsyncPayout() != null
					&& mobileUserdetails.getEnableAsyncPayout().toUpperCase().equals("YES")
					&& mobileUserdetails.getPayoutNotificationUrl().equals(regMerchant.getPayoutIpnUrl())) {

				// Ignore the UPDATE
				logger.warn(
						"Ignoring the enable async payout enable update because the compared given data and DB data matched.");
			} else if (regMerchant.getIsAsyncPayoutEnabled().toUpperCase().equals("YES")) {

				// Trigger e-mail to update in Data base
				new EmailUtils().sendPayoutAsyncHandlerEmail(merchant, regMerchant.getIsAsyncPayoutEnabled(),
						regMerchant.getPayoutIpnUrl(), regMerchant.getAsyncEnableReason());
			}
		} else {
			logger.warn("Payout Async Handler: Not Updated");
		}

		mID = merchantDAO.saveOrUpdateEntity(mID);
		logger.info("mid's also updated..");

		// merchant.setMid(mID);

		return merchant;

	}
	// end update merchant usine pending merchant controller 03/07/2016

	private static boolean isGreaterThanZero(String input) {

		if (input == null || input.trim().isEmpty()) {
			return false; // Return false for null or empty strings
		}

		try {
			BigDecimal value = new BigDecimal(input.trim());

			return value.compareTo(BigDecimal.ZERO) > 0;
		} catch (NumberFormatException e) {
			logger.error("Invalid format for BigDecimal: " + input, e);
			return false; // Return false for invalid input
		}
	}

	public boolean arePayoutLimitsEqual(BigDecimal payoutLimit1, String payoutLimit2) {
	    if (payoutLimit1 == null || payoutLimit2 == null) {
	        return false;
	    }

	    try {
	        BigDecimal limit2 = new BigDecimal(payoutLimit2);
	        return payoutLimit1.compareTo(limit2) == 0;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}

	private static BigDecimal convertMaxPayoutTxnLimitToBigDecimal(RegAddMerchant regMerchant) {

		String input = regMerchant.getMaxPayoutTxnLimit();
		if (input == null || input.replace(",", "").trim().isEmpty()) {
			return BigDecimal.ZERO; // Return BigDecimal.ZERO for null or empty strings
		}

		try {
	        BigDecimal value = new BigDecimal(input.replace(",", "").trim());
	        return value.setScale(2, RoundingMode.HALF_UP);
		} catch (NumberFormatException e) {
			logger.error("Invalid format for BigDecimal: " + input, e);
			return BigDecimal.ZERO; // Return BigDecimal.ZERO for invalid input
		}
	}

	private static boolean isEqual2Zero(String input) {

		if (input == null || input.trim().isEmpty()) {
			return false; // Return false for null or empty strings
		}

		try {
			BigDecimal value = new BigDecimal(input.trim());

			return value.compareTo(BigDecimal.ZERO) == 0; // Corrected equality operator
		} catch (NumberFormatException e) {
			logger.error("Invalid format for BigDecimal: " + input, e);
			return false; // Return false for invalid input
		}
	}

	// @javax.transaction.Transactional
	public MID loadMid(String mid) {

		return merchantDAO.loadMid(mid);
	}

	public MID checkExistMid(String mid) {

		return merchantDAO.loadMidtoUpdateAudit(mid);
	}

	public MID loadMotoMid(String motomid) {
		MID mid1 = new MID();
		return merchantDAO.loadMotoMid(motomid);
	}

	public MID loadEzyPassMid(String ezypassmid) {
		MID mid1 = new MID();
		return merchantDAO.loadEzyPassMid(ezypassmid);
	}
	// new method for pending merchant 24062016

	/*
	 * @javax.transaction.Transactional public void listMerchant1(final
	 * PaginationBean<Merchant> paginationBean) {
	 * System.out.println("Inside  listAllTransaction:::::"); ArrayList<Criterion>
	 * criterionList = new ArrayList<Criterion>();
	 * criterionList.add(Restrictions.not(Restrictions.eq("status",
	 * CommonStatus.ACTIVE))); merchantDAO.listMerchantUser1(paginationBean,
	 * criterionList); }
	 */

	// @javax.transaction.Transactional
	public FileUpload storeFileUpload(FileUpload fileUpload) {

		return merchantDAO.saveOrUpdateEntity(fileUpload);
	}

	// @javax.transaction.Transactional
	public FileUpload loadFileUpload(String fileId) {

		// BigInteger bi = new BigInteger(fileId);

		return merchantDAO.loadFileById(fileId);
	}

	// @javax.transaction.Transactional
	public List<FileUpload> loadFileDet(String merchantId) {

		// BigInteger bi = new BigInteger(fileId);

		return merchantDAO.loadFileByMerchantId(merchantId);
	}
	// new method for merchant summary search condition 16062017

	public void listMerchantSearch(final PaginationBean<Merchant> paginationBean, final String date, final String date1,
			String type) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		// logger.info("check activateDate42323:" + type);
		criterionList.add(Restrictions.not(Restrictions.eq("status", CommonStatus.CANCELLED)));
		criterionList.add(Restrictions.not(Restrictions.eq("status", CommonStatus.PENDING)));
		criterionList.add(Restrictions.not(Restrictions.eq("status", CommonStatus.SUBMITTED)));
		if (type != null && !type.isEmpty()) {
			if (type.equals("MERCHANT")) {
				// logger.info("check activateDate42323:" +
				// MerchantUserRole.BANK_MERCHANT.toString());
				criterionList.add(Restrictions.eq("role", MerchantUserRole.BANK_MERCHANT));
			} else {
				// logger.info("check activateDate42323:" +
				// MerchantUserRole.NON_MERCHANT.toString());
				criterionList.add(Restrictions.eq("role", MerchantUserRole.NON_MERCHANT));
			}
		}

		// Date dat = null;
		// Date dat1 = null;
		if (date != null) {
			// criterionList.add(Restrictions.eq("activateDate", data));
			// logger.info("check activateDate42323:" + date);
			String dat = date;

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			try {
				dat = format.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat));
				Timestamp ts = Timestamp.valueOf(dat);
				logger.info("From Date: " + ts);
				criterionList.add(Restrictions.ge("activateDate", ts));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (date1 != null) {
			String dat1 = date1;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				dat1 = format.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat1));
				Timestamp ts = Timestamp.valueOf(dat1);
				logger.info("To Date: " + ts);
				criterionList.add(Restrictions.lt("activateDate", ts));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// criterionList.add(Restrictions.lt("activateDate", dat1));
			// logger.info("check activateDate to date121212:" + date1);
		}
		merchantDAO.listMerchantSearch(paginationBean, criterionList);
	}

//new changes for email preview for approved status 10072017

	public Merchant getMerchantByMid(String mid) {
		MID mid1 = merchantDAO.loadMid(mid);
		Merchant merchant = merchantDAO.loadMerchant(mid1);
		return merchant;
	}

	public List<Merchant> merchantSummaryExport(final String date, final String date1) {
		System.out.println("Inside  monthly Transaction Export");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		return merchantDAO.listMerchantSummary(criterionList, date, date1);
	}

	public AuditTrail updateAuditTrailByMerchant(String userName, String merchantName, String auditTrailAction) {

		AuditTrail auditTrail = new AuditTrail();
		auditTrail.setCreatedBy(null);
		auditTrail.setCreatedDate(null);
		auditTrail.setModifiedBy(merchantName);
		auditTrail.setModifiedDate(new Date());
		auditTrail.setStatus(CommonOperationStatus.SUCCESS);
		auditTrail.setUsername(userName);

		switch (auditTrailAction) {
		// completed
		case "loginByEzyRec":
			auditTrail.setDescription("Logged in through EzyRec App");
			auditTrail.setAction(AuditTrailAction.LOGIN);
			auditTrail.setUserType("MERCHANT");
			break;
		case "logoutByEzyRec":
			auditTrail.setDescription("Logged out through EzyRec App");
			auditTrail.setAction(AuditTrailAction.LOGOUT);
			auditTrail.setUserType("MERCHANT");
			break;
		case "forgotPassword":
			auditTrail.setDescription("Password Changed through EzyRec App");
			auditTrail.setAction(AuditTrailAction.MERCHANT_CHANGE_PASSWORD);
			auditTrail.setUserType("MERCHANT");
			break;
		case "RecurringStatusUpdate":
			auditTrail.setDescription("Recurr Status Updated by Admin");
			auditTrail.setAction(AuditTrailAction.ADMIN_RECUR_STATUS_UPDATE);
			auditTrail.setUserType("MERCHANT");
			break;
		case "editMobileUserLogin":
			auditTrail.setDescription("MobileUser Login Details Edited by Merchant");
			auditTrail.setUserType("MOBILEUSER");
			auditTrail.setAction(AuditTrailAction.MERCHANT_EDIT_MOBILEUSER);
			break;
		// completed
		case "editEzyAds":
			auditTrail.setDescription("EZYAds Edited by Merchant");
			auditTrail.setUserType("MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_EDIT_EZYADS);
			break;

		// completed
		case "mailUpload":
			// logger.info("audittrail action: "+auditTrailAction);
			auditTrail.setDescription("EZYAds CustomerMail Uploaded by Merchant");
			auditTrail.setUserType("MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_MAILUPLOAD);
			break;

		case "addEzyAds":
			auditTrail.setDescription("EZYAds Added by Merchant");
			auditTrail.setUserType("MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_ADD_EZYADS);
			break;

		// completed
		case "editReader":
			auditTrail.setDescription("Reader Details Edited by Merchant");
			auditTrail.setUserType("READER");
			auditTrail.setAction(AuditTrailAction.MERCHANT_EDIT_READER);
			break;
		// completed
		case "changePassword":
			auditTrail.setDescription("Merchant Password ReChanged by Merchant");
			auditTrail.setUserType("MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_CHANGE_PASSWORD);
			break;
		// completed
		case "login":
			auditTrail.setDescription("Logged in by Merchant");
			auditTrail.setUserType("MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_LOGIN);
			break;

		case "viewByAdmin":
			auditTrail.setDescription("Merchant Portal Viewed by Admin");
			auditTrail.setUserType("MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_LOGIN);
			break;

		case "logout":
			auditTrail.setDescription("Logged out by Merchant");
			auditTrail.setUserType("MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_LOGOUT);
			break;

		default:
			auditTrail.setDescription(null);
			auditTrail.setUserType(null);
			auditTrail.setAction(null);
			break;
		}

		return merchantDAO.saveOrUpdateEntity(auditTrail);

	}

	public AuditTrail updateAuditTrailByNonMerchant(String userName, String merchantName, String auditTrailAction) {

		AuditTrail auditTrail = new AuditTrail();
		auditTrail.setCreatedBy(null);
		auditTrail.setCreatedDate(null);
		auditTrail.setModifiedBy(merchantName);
		auditTrail.setModifiedDate(new Date());
		auditTrail.setStatus(CommonOperationStatus.SUCCESS);
		auditTrail.setUsername(userName);

		switch (auditTrailAction) {
		// completed
		case "editMobileUserLogin":
			auditTrail.setDescription("MobileUser Login Details Edited by Merchant");
			auditTrail.setUserType("MOBILEUSER");
			auditTrail.setAction(AuditTrailAction.MERCHANT_EDIT_MOBILEUSER);
			break;
		// completed
		case "editEzyAds":
			auditTrail.setDescription("EZYAds Edited by Merchant");
			auditTrail.setUserType("NON_MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_EDIT_EZYADS);
			break;

		// completed
		case "mailUpload":
			// logger.info("audittrail action: "+auditTrailAction);
			auditTrail.setDescription("EZYAds CustomerMail Uploaded by Merchant");
			auditTrail.setUserType("NON_MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_MAILUPLOAD);
			break;

		case "addEzyAds":
			auditTrail.setDescription("EZYAds Added by Merchant");
			auditTrail.setUserType("NON_MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_ADD_EZYADS);
			break;

		// completed
		case "editReader":
			auditTrail.setDescription("Reader Details Edited by Merchant");
			auditTrail.setUserType("READER");
			auditTrail.setAction(AuditTrailAction.MERCHANT_EDIT_READER);
			break;
		// completed
		case "changePassword":
			auditTrail.setDescription("Merchant Password ReChanged by Merchant");
			auditTrail.setUserType("NON_MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_CHANGE_PASSWORD);
			break;
		// completed
		case "login":
			auditTrail.setDescription("Logged in by Merchant");
			auditTrail.setUserType("NON_MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_LOGIN);
			break;

		case "viewByAdmin":
			auditTrail.setDescription("Merchant Portal Viewed by Admin");
			auditTrail.setUserType("NON_MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_LOGIN);
			break;

		case "logout":
			auditTrail.setDescription("Logged out by Merchant");
			auditTrail.setUserType("NON_MERCHANT");
			auditTrail.setAction(AuditTrailAction.MERCHANT_LOGOUT);
			break;

		default:
			auditTrail.setDescription(null);
			auditTrail.setUserType(null);
			auditTrail.setAction(null);
			break;
		}

		return merchantDAO.saveOrUpdateEntity(auditTrail);

	}

	public KeyManager saveGeneratedCaptcha(String captcha) {
		KeyManager captchaGenerate = new KeyManager();

		captchaGenerate.setTid(captcha);
		return merchantDAO.saveOrUpdateEntity(captchaGenerate);

	}

	protected Session getSessionFactory() {
		return sessionFactory.getCurrentSession();
	}

	public List<Merchant> loadMerchantData() {
		ArrayList<Merchant> transactionList = new ArrayList<Merchant>();

		String sql1 = "select m.MERCHANT_ID,m.BUSINESS_NAME from merchant m ";

		logger.info("query: " + sql1);
		Query sqlQuery = this.getSessionFactory().createSQLQuery(sql1).addEntity(Merchant.class);// .setParameter("mid",
																									// mid);

		List<Object[]> resultSet = sqlQuery.list();
		logger.info("Number of records in the List : " + resultSet.size());
		for (Object[] rec : resultSet) {
			Merchant fs = new Merchant();
			if (rec[0] != null) {
				fs.setBusinessName(rec[0].toString());
			}
			if (rec[1] != null) {
				fs.setMdr(rec[1].toString());
			}
			transactionList.add(fs);

		}

		return transactionList;
	}

	public MID loadMerchantbyid(Merchant merchant) {
		/* logger.info("MerchantDaoImpl:loadMerchant MID"); */
		// TODO Auto-generated method stub
		return (MID) getSessionFactory().createCriteria(MID.class).add(Restrictions.eq("merchant", merchant))
				.setMaxResults(1).uniqueResult();
	}

	public MID loadMidByMerchant_PK(String id) {

		return merchantDAO.loadMidByMerchant_PK(id);
	}

	public TerminalDetails loadTerminalDetailsByMid(String mid) {

		return merchantDAO.loadTerminalDetailsByMid(mid);

	}

	public MobiLiteTerminal loadMobiliteTerminalDetailsByMid(Long mid) {

		return merchantDAO.loadMobiliteTerminalDetailsByMid(mid);

	}

	public void loadTerminalDetails(StringBuffer midStr, PaginationBean<TerminalDetails> paginationBean) {

		merchantDAO.loadTerminalDetails(midStr, paginationBean);

	}

	public void loadCurrentTxnDetails(Merchant merchant, PaginationBean<TerminalDetails> paginationBean,
			String agentName) {

		merchantDAO.loadCurrentTxnDetails(merchant, paginationBean, agentName);

	}

	public void searchTxnDetails(Merchant merchant, PaginationBean<TerminalDetails> paginationBean, String period,
			String productType, String year) {

		merchantDAO.searchTxnDetails(merchant, paginationBean, period, productType, year);

	}

	public List<String> getProductDetails(StringBuffer midStr) {

		return merchantDAO.getProductDetails(midStr);

	}

	public List<Merchant> loadUMMerchant() {
		return merchantDAO.loadUMMerchant();
	}

	public String getMerchantCurrentMonthTxnByNOB(StringBuffer str, StringBuffer strUm) {

		return merchantDAO.getMerchantCurrentMonthTxnByNOB(str, strUm);
	}

	public String getMerchantDailyTxnByNOB(StringBuffer str, StringBuffer strUm) {

		return merchantDAO.getMerchantDailyTxnByNOB(str, strUm);
	}

	public String getMerchantWeeklyTxnByNOB(StringBuffer str, StringBuffer strUm) {

		return merchantDAO.getMerchantWeeklyTxnByNOB(str, strUm);
	}

	public List<Merchant> loadMerchantByAgID(Long id) {

		BigInteger agid = BigInteger.valueOf(id);
		return merchantDAO.loadMerchantByAgID(agid);
	}

	public void listPayLaterMerchants(final PaginationBean<Merchant> paginationBean) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		merchantDAO.listPayLaterMerchants(paginationBean);
	}

	public void searchPayLaterMerchants(final PaginationBean<Merchant> paginationBean, String date, String date1) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		merchantDAO.searchPayLaterMerchants(paginationBean, date, date1);
	}

	public void listMerchantDetails(final PaginationBean<Merchant> paginationBean) {

		merchantDAO.listMerchantDetails(paginationBean);
	}

	public void listMerchantGPVDetails(final PaginationBean<Merchant> paginationBean) {

		merchantDAO.listMerchantGPVDetails(paginationBean);
	}

	public List<MerchantGPVData> listMerchantGPVDetailsByAgent(String agId) {

		return merchantDAO.listMerchantGPVDetailsByAgent(agId);
	}

	public List<MerchantGPVData> listMerchantGPVDetailsBySuperAgent() {

		return merchantDAO.listMerchantGPVDetailsBySuperAgent();
	}

	public List<MerchantGPVData> exportMerchantGPVDetailsBySuperAgent() {

		return merchantDAO.listMerchantGPVDetailsBySuperAgent();
	}

	public void getMerchantGPV(StringBuffer str, final PaginationBean<Merchant> paginationBean) {

		merchantDAO.getMerchantGPV(str, paginationBean);
	}

	public Merchant validateMerchantEmailId(String emailId) {

		return merchantDAO.validateMerchantEmailId(emailId);
	}

	public int updateTrackDetails(MobiliteTrackDetails det) {

		return merchantDAO.updateTrackDetails(det);
	}

	public void updateStatusDetails(SettlementMDR settle) {

		logger.info("RRN = " + settle.getRrn() + " " + "STATUS = " + settle.getStatus());
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("update SETTLEMENT_MDR set STATUS = :status where RRN = :rrn");
		query.setParameter("status", settle.getStatus());
		query.setParameter("rrn", settle.getRrn());

		query.executeUpdate();

	}

	public void updateBoostStatusDetails(BoostDailyRecon settle) {

		logger.info("RRN = " + settle.getBoostTxnID() + " " + "STATUS = " + settle.getPayment());
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery("update BOOST_DLY_RECON set PAYMENT = :status where BOOSTTXNID = :rrn");
		query.setParameter("status", settle.getPayment());
		query.setParameter("rrn", settle.getBoostTxnID());

		query.executeUpdate();

	}

	public void updateFpxStatusDetails(FpxTransaction settle) {

		logger.info("RRN = " + settle.getFpxTxnId() + " " + "Invoice Id = " + settle.getSellerOrderNo() + " "
				+ "STATUS = " + settle.getStatus());
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"update FPX_TRANSACTION set STATUS = :status where FPXTXNID = :rrn and SELLERORDERNO = :invoiceid ");
		query.setParameter("status", settle.getStatus());
		query.setParameter("rrn", settle.getFpxTxnId());
		query.setParameter("invoiceid", settle.getSellerOrderNo());

		query.executeUpdate();

	}

	public int updateEzylinkssTrackDetails(MobiliteTrackDetails det) {

		return merchantDAO.updateEzylinkssTrackDetails(det);
	}

	// Newly added on 07042021 - Santhosh

	public Merchant validateMerchantUserName(String uname) {
		return merchantDAO.loadMerchantDetails(uname);
	}

	public MobileOTP checkOTP(String mno, String emailId) {
		return merchantDAO.checkOTP(mno, emailId);
	}

	// rksubmerchant
	public void listsubMerchantSearch(final PaginationBean<Merchant> paginationBean, final String date,
			final String date1, String type) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		// logger.info("check activateDate42323:" + type);
		criterionList.add(Restrictions.not(Restrictions.eq("status", CommonStatus.CANCELLED)));
		criterionList.add(Restrictions.not(Restrictions.eq("status", CommonStatus.PENDING)));
		criterionList.add(Restrictions.not(Restrictions.eq("status", CommonStatus.SUBMITTED)));
		criterionList.add(Restrictions.eq("mmid", "DEPANSUM MALAYSIA SDN BHD"));
		if (type != null && !type.isEmpty()) {
			if (type.equals("MERCHANT")) {
				// logger.info("check activateDate42323:" +
				// MerchantUserRole.BANK_MERCHANT.toString());
				criterionList.add(Restrictions.eq("role", MerchantUserRole.BANK_MERCHANT));
			} else {
				// logger.info("check activateDate42323:" +
				// MerchantUserRole.NON_MERCHANT.toString());
				criterionList.add(Restrictions.eq("role", MerchantUserRole.NON_MERCHANT));
			}
		}

		// Date dat = null;
		// Date dat1 = null;
		if (date != null) {
			// criterionList.add(Restrictions.eq("activateDate", data));
			// logger.info("check activateDate42323:" + date);
			String dat = date;

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

			try {
				dat = format.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat));
				Timestamp ts = Timestamp.valueOf(dat);
				logger.info("From Date: " + ts);
				criterionList.add(Restrictions.ge("activateDate", ts));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (date1 != null) {
			String dat1 = date1;
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			try {
				dat1 = format.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat1));
				Timestamp ts = Timestamp.valueOf(dat1);
				logger.info("To Date: " + ts);
				criterionList.add(Restrictions.lt("activateDate", ts));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// criterionList.add(Restrictions.lt("activateDate", dat1));
			// logger.info("check activateDate to date121212:" + date1);
		}
		merchantDAO.listMerchantSearch(paginationBean, criterionList);
	}

	// rksubmerchant
	public void listsubMerchant(final PaginationBean<Merchant> paginationBean, String date, String date1, Long id) {
		Timestamp from = null;
		Timestamp to = null;
		if (date != null) {

			String dat = date;

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			try {
				dat = format.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat));
				from = Timestamp.valueOf(dat);
				logger.info("From Date: " + from);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (date1 != null) {
			String dat1 = date1;

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				dat1 = format.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat1));
				to = Timestamp.valueOf(dat1);
				logger.info("To Date: " + to);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		merchantDAO.listsubMerchantUserByMid(paginationBean, from, to, id);
	}

	// rksubmerchant
	public void listsubMerchantdefault(final PaginationBean<Merchant> paginationBean, Long id, String date1) {
		// public void listsubMerchantdefault(final PaginationBean<Merchant>
		// paginationBean, String date1) {
		Timestamp from = null;
		Timestamp to = null;
//        if (date != null) {
//
//            String dat = date;
//
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//
//            try {
//                dat = format.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat));
//                from = Timestamp.valueOf(dat);
//                logger.info("From Date: " + from);
//
//            } catch (ParseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        if (date1 != null) {
//            String dat1 = date1;
//
//            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//            try {
//                dat1 = format.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat1));
//                to = Timestamp.valueOf(dat1);
//                logger.info("To Date: " + to);
//
//            } catch (ParseException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//        }

		merchantDAO.listsubMerchantUserByMiddefault(paginationBean, id, from, to);
	}

//	public void listsubMerchant(final PaginationBean<Merchant> paginationBean, String type) {
//
//		merchantDAO.listsubMerchant(paginationBean, type);
//	}

	public void listsubMerchant(final PaginationBean<Merchant> paginationBean, String type) {

		merchantDAO.listsubMerchant1(paginationBean, type);
	}

	public void subMerchantListByAdmin(final PaginationBean<ForSettlement> paginationBean, String fromdate,
			String todate, Merchant findMerchant, String type, List<MobileUser> mobileuser) {

		merchantDAO.subMerchantListByAdmin(paginationBean, fromdate, todate, findMerchant, type, mobileuser);
	}

//	public void listadminsubMerchantdefault(final PaginationBean<Merchant> paginationBean) {
//
//		merchantDAO.listadminsubMerchantdefault(paginationBean);
//	}

	// new

	public void listadminsubMerchantdefault(final PaginationBean<Merchant> paginationBean,
			List<String> submerchantList) {

		merchantDAO.listadminsubMerchantdefault1(paginationBean, submerchantList);
	}

	// rk newely added dashboard changes
	public int getsucesscount(String mid1, String mid2, String mid3, String mid4, String mid5) {
		// TODO Auto-generated method stub
		return merchantDAO.getsucesscount(mid1, mid2, mid3, mid4, mid5);
	}

	public int getfailurecount(String mid1, String mid2, String mid3, String mid4, String mid5) {
		// TODO Auto-generated method stub
		return merchantDAO.getfailurecount(mid1, mid2, mid3, mid4, mid5);
	}

	public int getumsucesscount(String mid1, String mid2, String mid3, String mid4, String mid5, String mid6) {
		// TODO Auto-generated method stub
		return merchantDAO.getumsucesscount(mid1, mid2, mid3, mid4, mid5, mid6);
	}

	public int getumfailurecount(String mid1, String mid2, String mid3, String mid4, String mid5, String mid6) {
		// TODO Auto-generated method stub
		return merchantDAO.getumfailurecount(mid1, mid2, mid3, mid4, mid5, mid6);
	}

	// rk productwiseamount
	public void listproductwiseamount(final PaginationBean<ProductWiseAmount> paginationBean, Merchant merchant,
			Model model) {

		merchantDAO.listproductwiseamount(paginationBean, merchant, model);
	}
	// rk newely added dashboard changes

	// PAYOUT BY DHINESH & RK - START

	public void updatePayoutStatus(PayoutDetail payoutDetail) {

//		logger.info("TXN ID = " + payoutDetail.getInvoiceIdProof() + " " + "PAYOUT STATUS = "
//				+ payoutDetail.getPayoutStatus());
//		Session session = sessionFactory.getCurrentSession();
//		Query query = session.createSQLQuery(
//				"update PAYOUT_DETAIL set PAYOUT_STATUS = :payoutStatus , PAID_DATE = :paidDate , PAID_TIME = :paidTime where INVOICE_ID_PROOF = :invoiceIdProof");
//		query.setParameter("payoutStatus", payoutDetail.getPayoutStatus());
//		query.setParameter("invoiceIdProof", payoutDetail.getInvoiceIdProof());
//		query.setParameter("paidDate", payoutDetail.getPaidDate());
//		query.setParameter("paidTime", payoutDetail.getPaidTime());
//
//		query.executeUpdate();
		logger.info("TXN ID = " + payoutDetail.getInvoiceIdProof() + " " + "PAYOUT STATUS = "
				+ payoutDetail.getPayoutStatus() + "PAYOUT PAID DATE" + payoutDetail.getPaidDate());
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(
				"update PAYOUT_DETAIL set PAYOUT_STATUS = :payoutStatus , PAID_DATE = :paidDate , PAID_TIME = :paidTime, PAYOUT_DATE = :paidDate where INVOICE_ID_PROOF = :invoiceIdProof");
		query.setParameter("payoutStatus", payoutDetail.getPayoutStatus());
		query.setParameter("invoiceIdProof", payoutDetail.getInvoiceIdProof());
		query.setParameter("paidDate", payoutDetail.getPaidDate());
		query.setParameter("paidTime", payoutDetail.getPaidTime());

		query.executeUpdate();

	}

	public void declinePayoutStatus(PayoutDetail payoutDetail) {
//        logger.info("Decline Payout Status = " + payoutDetail.getPayoutStatus());
//         
//          Session session = sessionFactory.openSession();
//          Query query = session.createSQLQuery("update PAYOUT_DETAIL set PAYOUT_STATUS = :status , PAYMENT_REASON = :declineReason where INVOICE_ID_PROOF = :invoiceIdProof");
//          query.setParameter("status", payoutDetail.getPayoutStatus());
//          query.setParameter("invoiceIdProof", payoutDetail.getInvoiceIdProof());
//          query.setParameter("declineReason", payoutDetail.getPaymentReason());
//          query.executeUpdate();
//          session.close();

		logger.info("Decline Payout Status = " + payoutDetail.getPayoutStatus());
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery(
				"update PAYOUT_DETAIL set PAYOUT_STATUS = :status , PAYMENT_REASON = :declineReason,PAID_TIME = :paidTime , PAID_DATE = :paidDate , PAYOUT_DATE = :paidDate where INVOICE_ID_PROOF = :invoiceIdProof");
		query.setParameter("status", payoutDetail.getPayoutStatus());
		query.setParameter("invoiceIdProof", payoutDetail.getInvoiceIdProof());
		query.setParameter("declineReason", payoutDetail.getPaymentReason());
		query.setParameter("paidTime", payoutDetail.getPaidTime());
		query.setParameter("paidDate", payoutDetail.getPaidDate());
		query.executeUpdate();
		session.close();

	}

	public PayoutGrandDetail PayoutGrandDetailbymerchantid(Long id) {
		return merchantDAO.PayoutGrandDetailbymerchantid(id);
	}

	public PayoutGrandDetail savePayoutGrandDetail(PayoutGrandDetail payoutDetail) {
		return merchantDAO.saveOrUpdateEntity(payoutDetail);
	}

	// PAYOUT BY DHINESH & RK - END

	public List<UMMidTxnLimit> UmmidTxnlimit() {

		return merchantDAO.UmmidTxnlimit();
	}

	// 27-03-23 PAYOUT BALANCE

	public void loadPayoutbalance(final PaginationBean<PayoutModel> paginationBean) {
		// TODO Auto-generated method stub
		merchantDAO.loadPayoutbalance(paginationBean);

	}

	public SettlementBalance SearchMerchantSettlement(final String merchantId) {

		return merchantDAO.SearchMerchantSettlement(merchantId);
	}

	public PayoutBankBalance oldbankbalance() {

		return merchantDAO.oldbankbalance();
	}

	public void updateBankAmount(String amount, String date) {

		merchantDAO.updateBankAmount(amount, date);
	}

	public void updateOverdraft(double overdraft, double netamount, String merchantId) {

		merchantDAO.updateOverdraft(overdraft, netamount, merchantId);
	}

	public void updateoverdraftwithdepo(double overdraft, double settlementbalance, double depositamount,
			double totalBalance, String merchantId) {

		merchantDAO.updateoverdraftwithdepo(overdraft, settlementbalance, depositamount, totalBalance, merchantId);
	}

	public void updatedepositAmount(double settlementbalance, double depositamount, double totalBalance,
			String merchantId) {

		merchantDAO.updatedepositAmount(settlementbalance, depositamount, totalBalance, merchantId);
	}

	public void updatedepositAmountPayoutGrand(double depositamount, Merchant merchantData) {

		merchantDAO.updatedepositAmountPayoutGrand(depositamount, merchantData);
	}

	public Merchant findMerchant(String merchantId) {

		return merchantDAO.findMerchant(merchantId);
	}

	/*
	 * public void updatedepositAmountPayoutGrand( double depositamount) {
	 * 
	 * merchantDAO.updatedepositAmountPayoutGrand( depositamount); }
	 */

	public UMEcomTxnResponse ezywayChrckTransaction(String txnID, String mid) {

		return merchantDAO.ezywayChrckTransaction(txnID, mid);
	}

	public FpxTransaction fpxCheckTransaction(String txnID, String mid) {

		return merchantDAO.fpxCheckTransaction(txnID, mid);
	}

	public ForSettlement ezywireCheckTransaaction(String txnID, String mid) {

		return merchantDAO.ezywireCheckTransaaction(txnID, mid);
	}

	public UMEcomTxnResponse ezyAuthCheckTransaaction(String txnID, String mid) {

		return merchantDAO.ezyAuthCheckTransaaction(txnID, mid);
	}

	public FpxTransaction findbyFpxData(String txnID) {

		return merchantDAO.findbyFpxData(txnID);
	}

	public UMEcomTxnResponse findbyCardEzywayData(String txnID) {

		return merchantDAO.findbyCardEzywayData(txnID);
	}

	public ForSettlement findbyCardEzywireData(String txnID) {

		return merchantDAO.findbyCardEzywireData(txnID);
	}

	public ForSettlement findbyCardEzywire(String invoice) {

		return merchantDAO.findbyCardEzywire(invoice);
	}

	public UMEcomTxnResponse findbyCardEzyway(String invoice) {

		return merchantDAO.findbyCardEzyway(invoice);
	}

	public UMEcomTxnResponse findbyCardEzyAuthData(String txnID) {

		return merchantDAO.findbyCardEzyAuthData(txnID);
	}

	public UMEcomTxnResponse findbyCardEzyAuth(String invoiceId) {

		return merchantDAO.findbyCardEzyAuth(invoiceId);
	}

	public UMEcomTxnResponse findbyCardEzyMotoData(String txnID) {

		return merchantDAO.findbyCardEzyMotoData(txnID);
	}

	public UMEcomTxnResponse findbyCardEzyMoto(String invoiceId) {

		return merchantDAO.findbyCardEzyMoto(invoiceId);
	}

	public UMEcomTxnResponse findbyCardEzyLinkData(String txnID) {

		return merchantDAO.findbyCardEzyLinkData(txnID);
	}

	public UMEcomTxnResponse findbyCardEzyLink(String invoiceId) {

		return merchantDAO.findbyCardEzyLink(invoiceId);
	}

	public MID findbyMerchantMid(String mid) {

		return merchantDAO.findbyMerchantMid(mid);
	}

	public String cardTransaction(MID mid, String currentDate) {

		return merchantDAO.cardTransaction(mid, currentDate);
	}

	public String walletsTransaction(MID mid, String currentDate) {

		return merchantDAO.walletsTransaction(mid, currentDate);
	}

	public String m1PayTranascton(MID mid, String currentDate) {

		return merchantDAO.m1PayTranascton(mid, currentDate);
	}

	public ForSettlement findbyBoostData(String txnID, String txnMid) {

		return merchantDAO.findbyBoostData(txnID, txnMid);
	}

	public ForSettlement findbyBoostData(String txnID) {

		return merchantDAO.findbyBoostData(txnID);
	}

	public ForSettlement findbyBoost(String invoiceId) {
		return merchantDAO.findbyBoost(invoiceId);
	}

	public ForSettlement findbyGrabData(String txnID) {

		return merchantDAO.findbyGrabData(txnID);
	}

	public ForSettlement findbyGrab(String invoiceId) {

		return merchantDAO.findbyGrab(invoiceId);
	}

	public EwalletTxnDetails findbyM1PayData(String txnID) {

		return merchantDAO.findbyM1PayData(txnID);
	}

	public EwalletTxnDetails findbyM1Pay(String invoiceId) {

		return merchantDAO.findbyM1Pay(invoiceId);
	}

	public Merchant findbyMerchant(MID mid) {

		return merchantDAO.findbyMerchant(mid);
	}

	public Merchant findRefundMerchant(final long merchantId) {

		logger.info("<==MERCHANT DETAILS LOADING DAO==>");

		return merchantDAO.findRefundMerchant(merchantId);
	}

	public RefundRequest saveRefundRequest(RefundRequest refundRequest) {
		return merchantDAO.saveOrUpdateEntity(refundRequest);
	}

	public void updateRefundStatus(String intiateDate, String txnId) {
		merchantDAO.updateRefundStatus(intiateDate, txnId);
	}

	public RefundRequest loadRequestRefundList(String intiateDate, String txnId) {
		return merchantDAO.loadRequestRefundList(intiateDate, txnId);
	}

	public void updateFinalRefundStatus(String intiateDate, String txnId) {
		merchantDAO.updateFinalRefundStatus(intiateDate, txnId);
	}

	public void finalfpxRefundStatus(FpxTransaction fpxData, String settlementDate, String txnMid) {
		merchantDAO.finalfpxRefundStatus(fpxData, settlementDate, txnMid);
	}

	public void finalCardEzywayRefundStatus(UMEcomTxnResponse cardEzywayData, String settlementDate, String txnMid) {
		merchantDAO.finalCardEzywayRefundStatus(cardEzywayData, settlementDate, txnMid);
	}

	public void finalCardEzywireRefundStatus(String txnId, String settlementDate, String txnMid) {
		merchantDAO.finalCardEzywireRefundStatus(txnId, settlementDate, txnMid);
	}

	public void finalCardEzyAuthRefundStatus(String txnId, String settlementDate, String txnMid) {
		merchantDAO.finalCardEzyAuthRefundStatus(txnId, settlementDate, txnMid);
	}

	public void finalCardEzyEzyMotoRefundStatus(String txnId, String settlementDate, String txnMid) {
		merchantDAO.finalCardEzyEzyMotoRefundStatus(txnId, settlementDate, txnMid);
	}

	public void finalCardEzyLinkRefundStatus(String txnId, String settlementDate, String txnMid) {
		merchantDAO.finalCardEzyLinkRefundStatus(txnId, settlementDate, txnMid);
	}

	public void finalBoostRefundStatus(String txnId, String settlementDate, String txnMid) {
		merchantDAO.finalBoostRefundStatus(txnId, settlementDate, txnMid);
	}

	public void finalGrabRefundStatus(String txnId, String settlementDate, String txnMid) {
		merchantDAO.finalGrabRefundStatus(txnId, settlementDate, txnMid);
	}

	public void finalM1PayRefundStatus(String txnId, String settlementDate, String txnMid) {
		merchantDAO.finalM1PayRefundStatus(txnId, settlementDate, txnMid);
	}

	public boolean refundRequeststatusUpdate(String txnId, String settlementDate) {
		return merchantDAO.refundRequeststatusUpdate(txnId, settlementDate);
	}

	public boolean refundRequeststatusUpdateBoost(String txnId, String settlementDate, String RfNum) {
		return merchantDAO.refundRequeststatusUpdateBoost(txnId, settlementDate, RfNum);
	}

	public boolean refundRequestCardstatusUpdate(String txnId, String settlementDate) {
		return merchantDAO.refundRequestCardstatusUpdate(txnId, settlementDate);
	}

	public RefundRequest findbyValidRefundAmount(Merchant merchantdata, String settlementDate) {

		return merchantDAO.findbyValidRefundAmount(merchantdata, settlementDate);
	}

	public UMEcomTxnResponse ezymotoCheckTransaction(String txnID, String mid) {

		return merchantDAO.ezymotoCheckTransaction(txnID, mid);
	}

	public UMEcomTxnResponse ezylinkCheckTransaction(String txnID, String mid) {

		return merchantDAO.ezylinkCheckTransaction(txnID, mid);
	}

	public ForSettlement boostCheckTransaction(String txnID, String mid) {

		return merchantDAO.boostCheckTransaction(txnID, mid);
	}

	public ForSettlement grabCheckTransaction(String txnID, String mid) {

		return merchantDAO.grabCheckTransaction(txnID, mid);
	}

	public EwalletTxnDetails m1PayCheckTransaction(String txnID, String mid) {

		return merchantDAO.m1PayCheckTransaction(txnID, mid);
	}

	public RefundRequest checkTranactionIsValid(String txnID) {

		return merchantDAO.checkTranactionIsValid(txnID);
	}

	public LoginResponse addDepositAmount(String oldDeposit, String newDeposit, long id, String motoApiKey) {

		LoginResponse responseValue = new LoginResponse();
		int responseCode = 0;
		String responseCodeData = "0001";

		try {
			// API endpoint URL
//			URL url = new URL("http://localhost:8087/externalapi/updateDepositAmount");
			logger.info("Deposit Api Url :" + PropertyLoad.getFile().getProperty("ADD_DEPOSIT_API"));
			URL url = new URL(PropertyLoad.getFile().getProperty("ADD_DEPOSIT_API"));

			// Open a connection to the URL
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Set the request method to POST
			connection.setRequestMethod("POST");

			// Enable input/output streams
			connection.setDoOutput(true);

			// Set the request headers
			connection.setRequestProperty("Content-Type", "application/json");

			// Create a Java object to represent your data
			JsonObject jsonBody = new JsonObject();
			jsonBody.addProperty("service", "UPDATE_DEPOSIT");
			jsonBody.addProperty("oldDepositAmount", oldDeposit);
			jsonBody.addProperty("newDepositAmount", newDeposit);
			jsonBody.addProperty("merchantIdData", id);
			jsonBody.addProperty("mobiApiKey", motoApiKey);

			// Use Gson to convert the Java object to a JSON string
			String data = new Gson().toJson(jsonBody);

			// Get the output stream of the connection and write the data to it
			try (OutputStream os = connection.getOutputStream()) {
				byte[] input = data.getBytes(StandardCharsets.UTF_8);
				os.write(input, 0, input.length);
			}

			// Get the HTTP response code
			responseCode = connection.getResponseCode();
			logger.info("Response Code: " + responseCode);

			String responseData = null;

			if (responseCode == HttpURLConnection.HTTP_OK) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					responseData = response.toString(); // Store the response data
					// Process the response as needed
					logger.info("API Response: " + responseData);

					JSONObject jsonObject = new JSONObject(responseData);
					// Get the nested object under "responseData"

					JSONObject responseDataInExternalApi = jsonObject.getJSONObject("updateResponseData");

					responseCodeData = jsonObject.getString("responseCode");
					logger.info("responseCodeData : " + responseCodeData);

					// Get the value of "depositAmount"
					String depositAmount = responseDataInExternalApi.getString("depositAmount");
					String referenceNo = responseDataInExternalApi.getString("referenceNo");

					responseValue.setDepositAmount(depositAmount);
					responseValue.setReferenceNo(referenceNo);
				}
			} else {
				logger.error("API Request failed with response code: " + responseCode);
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Response Code: " + responseCode);

		if (responseCodeData.equals("0000")) {
			responseValue.setResponse("true");
			return responseValue;
		} else {
			responseValue.setResponse("false");
			return responseValue;
		}

	}

	// Old add deposit API
//	public LoginResponse addDepositAmount(String oldDeposit,String newDeposit, long id) {
//
//		LoginResponse responseValue = new LoginResponse();
//		
//		int responseCode = 0;
//		
//		try {
//			// API endpoint URL
////			URL url = new URL("http://localhost:8087/externalapi/updateDepositAmount");
//			logger.info("Deposit Api Url :" + PropertyLoad.getFile().getProperty("ADD_DEPOSIT_API"));
//			URL url = new URL(PropertyLoad.getFile().getProperty("ADD_DEPOSIT_API"));
//
//			// Open a connection to the URL
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//			// Set the request method to POST
//			connection.setRequestMethod("POST");
//
//			// Enable input/output streams
//			connection.setDoOutput(true);
//
//			// Set the request headers
//			connection.setRequestProperty("Content-Type", "application/json");
//
//			// Create a Java object to represent your data
//			JsonObject jsonBody = new JsonObject();
//			jsonBody.addProperty("service", "UPDATE_DEPOSIT");
//			jsonBody.addProperty("oldDepositAmount", oldDeposit);
//			jsonBody.addProperty("newDepositAmount", newDeposit);
//			jsonBody.addProperty("merchantIdData", id);
//
//			// Use Gson to convert the Java object to a JSON string
//			String data = new Gson().toJson(jsonBody);
//
//			// Get the output stream of the connection and write the data to it
//			try (OutputStream os = connection.getOutputStream()) {
//				byte[] input = data.getBytes(StandardCharsets.UTF_8);
//				os.write(input, 0, input.length);
//			}
//
//			// Get the HTTP response code
//			responseCode = connection.getResponseCode();
//			logger.info("Response Code: " + responseCode);
//
//			String responseData = null;
//			
//			if (responseCode == HttpURLConnection.HTTP_OK) {
//	            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
//	                StringBuilder response = new StringBuilder();
//	                String line;
//	                while ((line = reader.readLine()) != null) {
//	                    response.append(line);
//	                }
//	                responseData = response.toString(); // Store the response data
//	                // Process the response as needed
//	                logger.info("API Response: " + responseData);
//	                
//	                JSONObject jsonObject = new JSONObject(responseData);
//
//	                // Get the nested object under "responseData"
//	                JSONObject responseDataInExternalApi = jsonObject.getJSONObject("updateResponseData");
//
//	                // Get the value of "depositAmount"
//	                String depositAmount = responseDataInExternalApi.getString("depositAmount");
//	                String olddepositAmount = responseDataInExternalApi.getString("olddepositAmount");
//	                
//	                responseValue.setDepositAmount(depositAmount);
//	                responseValue.setOlddepositAmount(olddepositAmount);
//	            }
//	        } else {
//	            logger.warn("API Request failed with response code: " + responseCode);
//	        }
//			
//			
//			connection.disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.info("Response Code: " + responseCode);
//
//		if (responseCode == 200) {
//			
//			responseValue.setResponse("true");
//			return responseValue;
//		} else {
//			responseValue.setResponse("false");
//			return responseValue;
//		}
//
//	}
//	

//	public boolean addDepositAmount(String depositAmount, String idString) {
//
//		int responseCode = 0;
//
//		try {
//			// API endpoint URL
////			URL url = new URL("http://localhost:8087/externalapi/updateDepositAmount");
//			logger.info("Deposit Api Url :" + PropertyLoad.getFile().getProperty("ADD_DEPOSIT_API"));
//			URL url = new URL(PropertyLoad.getFile().getProperty("ADD_DEPOSIT_API"));
//
//			// Open a connection to the URL
//			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//
//			// Set the request method to POST
//			connection.setRequestMethod("POST");
//
//			// Enable input/output streams
//			connection.setDoOutput(true);
//
//			// Set the request headers
//			connection.setRequestProperty("Content-Type", "application/json");
//
//			// Create a Java object to represent your data
//			JsonObject jsonBody = new JsonObject();
//			jsonBody.addProperty("depositAmount", depositAmount);
//			jsonBody.addProperty("idString", idString);
//
//			// Use Gson to convert the Java object to a JSON string
//			String data = new Gson().toJson(jsonBody);
//
//			// Get the output stream of the connection and write the data to it
//			try (OutputStream os = connection.getOutputStream()) {
//				byte[] input = data.getBytes(StandardCharsets.UTF_8);
//				os.write(input, 0, input.length);
//			}
//
//			// Get the HTTP response code
//			responseCode = connection.getResponseCode();
//			logger.info("Response Code: " + responseCode);
//
//			connection.disconnect();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		logger.info("Response Code: " + responseCode);
//
//		if (responseCode == 200) {
//			return true;
//		} else {
//			return false;
//		}
//
////		 merchantDAO.addDepositAmount(depositAmount,idString);
//	}

	public GrabPaymentTxn loadTxnByPartnerTxId(String txnId) {
		return merchantDAO.loadTxnByPartnerTxId(txnId);
	}

	public TerminalDetails loadTerminalByTID(String tid) {
		return merchantDAO.loadTerminalByTID(tid);
	}

	public Long getStan(String tid) {
		return merchantDAO.getStan(tid);
	}

	public void updateInternalTable(InternalTable internalTable) {
		merchantDAO.updateInternalTable(internalTable);
	}

	public GrabPaymentTxn updateGrabPaymentTxn(GrabPaymentTxn gptr) {
		return merchantDAO.updateGrabPaymentTxn(gptr);
	}

	public ForSettlement loadForSettlementByPartnerTxId(String partnerTxId) {
		return merchantDAO.loadForSettlementByPartnerTxId(partnerTxId);
	}

	public ForSettlement saveForSettlement(ForSettlement forset) {
		return merchantDAO.saveForSettlement(forset);
	}

	public PayoutDetail searchTransaction(String transactionID) {
		return merchantDAO.searchTransaction(transactionID);
	}

	public void updateBoostVoidStatus(String transactionId) {
		merchantDAO.updateBoostVoidStatus(transactionId);
	}

	public void updateBoostRefundStatus(String transactionId) {
		merchantDAO.updateBoostRefundStatus(transactionId);
	}

	public EwalletTxnDetails findbyTouchnGoAndShopeePayData(String txnID) {

		return merchantDAO.findbyTouchnGoAndShopeePayData(txnID);
	}

	public void updateTouchnGoRefundStatus(String transactionId, String invoiceId) {
		merchantDAO.updateTouchnGoRefundStatus(transactionId, invoiceId);
	}

	public void updateShopeePayRefundStatus(String transactionId, String invoiceId) {
		merchantDAO.updateShopeePayRefundStatus(transactionId, invoiceId);
	}

	public MobileUser loadMobileUserById(Long id) {
		return merchantDAO.loadMobileUserById(id);
	}

	public PayoutDepositDetails checkdepositDetails(String referenceNo) {
		return merchantDAO.checkdepositDetails(referenceNo);
	}

	public void exportsubMerchant(final PaginationBean<Merchant> paginationBean, String type) {

		merchantDAO.exportsubMerchant(paginationBean, type);

	}

	// Ezysettle v2
	public PayeeDetails getPayeeDetails(String resDate, Merchant merchant,String status) {
		logger.info("PAYEE");
		return merchantDAO.getPayeeDetails(resDate, merchant,status);
	}

	public JustSettle findByMidandDate(Merchant merchant) {

		String mid = Optional
				.ofNullable(merchant.getMid().getUmMid()).orElse(
						Optional.ofNullable(merchant.getMid().getUmEzywayMid())
								.orElse(Optional.ofNullable(merchant.getMid().getUmMotoMid())
										.orElse(Optional.ofNullable(merchant.getMid().getBoostMid())
												.orElse(Optional.ofNullable(merchant.getMid().getGrabMid())
														.orElse(Optional.ofNullable(merchant.getMid().getFpxMid())
																.orElse(Optional.ofNullable(merchant.getMid().getTngMid())
																		.orElse(Optional.ofNullable(merchant.getMid().getFiuuMid())
																				.orElse(merchant.getMid().getShoppyMid()))))))));

		logger.info("Mid : " + mid);

		return merchantDAO.findByMidandDate(mid);
	}

	public Merchant loadMerchantById(String id) {
		logger.info("<==MERCHANT DETAILS LOADING DAO BY ID==>");
		return merchantDAO.loadMerchantById(id);
	}

	
	public Agent loadagentUsername(String merchantName) {
		logger.info("Username load by merchant name");
		return merchantDAO.loadagentUsername(merchantName);
	}
	
	public void listMerchantsFromAgent(final PaginationBean<MerchantDTO> paginationBean,long agentId) {
		 
		merchantDAO.listMerchantsFromAgent(paginationBean,agentId);
	}
	
	public void listMerchantsFromAgentsearch(final PaginationBean<MerchantDTO> paginationBean,String fromDate,String toDate,long agentId) {
		 
		merchantDAO.listMerchantsFromAgentsearch(paginationBean, fromDate, toDate,agentId);
	}

	@Transactional
	public MerchantInfo loadMerchantInfoByFk(String id) {
		return merchantDAO.loadmerchantInfoByFK(id);
	}

	public BankUser loadBankUserByUsername(String username) {
		return merchantDAO.loadBankUserByUsername(username);
	}


	public void updateOtpTime(String time, String idOrUsername, String field, String tableName) {
		merchantDAO.updateOtpTime(time, idOrUsername, field, tableName);
	}

	@Transactional
	public void insertMerchantInfoByFK(String formattedTime, String fk, String fieldName, String tblName) {
		merchantDAO.insertMerchantInfoByFk(formattedTime, fk, fieldName, tblName);
	}

	public List<String> loadAllMerchant() {

		return merchantDAO.loadAllMerchant();
	}
	
	public List<MerchantPaymentDetailsDto> loadMerchantPaymentDetailsByFk(Long long1) {
		
		List<MerchantPaymentDetailsDto> merchantPaymentDetails = merchantDAO.loadMerchantPaymentDetailsByFk(long1);
		if (merchantPaymentDetails == null) {
			 logger.info("No Merchant Payment Details found for ID: {}"+ long1);
			 return Collections.emptyList(); 
		}
		return merchantPaymentDetails;
	}

	@Transactional
	public int insertMerchantInfoForJenfi(String id,String uniqueID){
		return merchantDAO.insertMerchantInfoForJenfi(id,uniqueID);

	}

	@Transactional
	public int updateuniqueIDForJenfi(String id,String uniqueID){
		return merchantDAO.updateuniqueIDForJenfi(id,uniqueID);
	}

	public List<MerchantDTO> getMerchantDtoDataFromMerchantData(List<Merchant> merchantList ){
		return merchantDAO.getMerchantDtoList(merchantList);
	}
	

}
