package com.mobiversa.payment.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.xerces.impl.dv.util.Base64;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantDetails;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.Promotion;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dao.PromotionDao;
import com.mobiversa.payment.dto.AddMerchantPromo;
import com.mobiversa.payment.dto.MerchtCustMail;
import com.mobiversa.payment.notification.AndroidPushNotification;
import com.mobiversa.payment.notification.IosPushNotifications;
import com.mobiversa.payment.util.PropertyLoader;
import com.postmark.java.Attachment;
import com.postmark.java.NameValuePair;
import com.postmark.java.PostmarkClient;
import com.postmark.java.PostmarkException;
import com.postmark.java.PostmarkMessage;
import com.postmark.java.PromoEmailTemplet1;
import com.postmark.java.PromoEmailTemplet2;
import com.postmark.java.PromoSampleEmailTemplet;
import com.postmark.java.TempletFields;

@SuppressWarnings("unused")
@Service
public class PromotionService { // extends BaseDAOImpl

	@Autowired
	private PromotionDao promotionDAO;
	
	@Autowired
	private MerchantDao merchantDAO;
	
	@Autowired
	private MerchantService merchantService;
	
	


	private static final Logger logger = Logger
			.getLogger(PromotionService.class.getName());

	public void listPromotions(final PaginationBean<Promotion> paginationBean,
			Merchant merchant) {

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		
		
		criterionList.add(Restrictions.in("mid", new String[] { merchant.getMid().getMid(),merchant.getMid().getMotoMid(),
				merchant.getMid().getEzypassMid()}	));
		criterionList.add(Restrictions.not(Restrictions.eq("status",
				CommonStatus.CANCELLED)));
		criterionList.add(Restrictions.not(Restrictions.eq("status",
				CommonStatus.DELETED)));
		promotionDAO.listPromotions(paginationBean, criterionList);

	}

	// add mail upload for promotion 22/05/2017
	public MerchtCustMail addCustMail(final MerchtCustMail entity,Merchant merchant)

	{
		//MerchantDetails merchantDetails = promotionDAO.loadMerchantPoints(entity.getMid());
		MerchantDetails merchantDetails = promotionDAO.loadMerchantPointsallmid(merchant);
		merchantDetails.setMerchantId(entity.getMerchantId());
		// merchantDetails.setMerchantId(entity.getMerchantId());

		/*
		 * if(entity.getEmailText()!=null) {
		 */
		// logger.info("check email list in text box:" +
		// entity.getCustMailList());
		merchantDetails.setCustMailList(entity.getCustMailList());
		// logger.info("after      check email list in text box:" +
		// merchantDetails.getCustMailList());
		/*
		 * }else { merchantDetails.setCustMailList(entity.getCustMailList()); }
		 */

		merchantDetails.setMerchantName(entity.getMerchantName());
		merchantDetails.setMid(entity.getMid());
		// logger.info("check email list in text box:" + entity.getEmailText());

		merchantDetails.setMailFile(entity.getmFilepath());

		merchantDetails.setStatus(CommonStatus.PENDING);

		merchantDetails.setMerchantLogo(entity.getMerchantLogo());

		merchantDetails = promotionDAO.saveOrUpdateEntity(merchantDetails);
		return entity;

	}// end
	
	// add merchant promo in merchantweb login
	public Promotion addMerchantPromo(final AddMerchantPromo entity) // ,java.security.Principal
																			// principal
	{

		Promotion promotion = new Promotion();

		logger.info("merchant ID: "+entity.getMerchantId());
		
		MID mid=null;
		
		if(entity.getMerchantId()!=null)
		{
			mid=merchantDAO.loadMerchantbyMerchant_FK(Long.valueOf(entity.getMerchantId()));
			
			
		}
		if(mid!=null)
		{
			logger.info("check mid table: "+mid.getId()+" : "+mid.getEzypassMid()+" : "+mid.getMid()+"  : "+mid.getMotoMid());
			
			if(mid.getMid()!=null)
			{
				//promotion.setMid(entity.getMid());
				entity.setMid(mid.getMid());
			}
			if(mid.getMotoMid()!=null)
			{
				//promotion.setMid(entity.getMotoMid());
				entity.setMotoMid(mid.getMotoMid());
			}
			if(mid.getEzypassMid()!=null)
			{
				//promotion.setMid(entity.getEzypassMid());
				entity.setEzypassMid(mid.getEzypassMid());
			}
			if(mid.getEzyrecMid()!=null)
			{
				//promotion.setMid(entity.getEzypassMid());
				entity.setEzyrecMid(mid.getEzyrecMid());
				
			}
		}
		
		logger.info("check mid details: "+entity.getMid()+" :moto: "+entity.getMotoMid()+" :ezypaass: "+entity.getEzypassMid());
		MobileUser mobileUser = promotionDAO
				.loadMobileUserDetailsByUsername(entity.getUsername());

		if (mobileUser != null) {
			if(mobileUser.getTid()!=null)
			{
				
				logger.info("tid: "+mobileUser.getTid());
				//promotion.setTid(mobileUser.getTid());
				entity.setTid(mobileUser.getTid());
			}
			if(mobileUser.getMotoTid()!=null)
			{
				logger.info("moto tid: "+mobileUser.getMotoTid());
				//promotion.setTid(mobileUser.getMotoTid());
				entity.setMotoTid(mobileUser.getMotoTid());
			}
			if(mobileUser.getEzypassTid()!=null)
			{
				logger.info("ezypass tid: "+mobileUser.getEzypassTid());
				//promotion.setTid(mobileUser.getEzypassTid());
				entity.setEzypassTid(mobileUser.getEzypassTid());
			}
			if(mobileUser.getEzyrecTid()!=null)
			{
				logger.info("ezypass tid: "+mobileUser.getEzyrecTid());
				//promotion.setTid(mobileUser.getEzypassTid());
				entity.setEzyrecTid(mobileUser.getEzyrecTid());
			}
			
		}

		
		logger.info("mid: "+entity.getMid()+" :  tid: "+entity.getTid());
		logger.info("moto mid: "+entity.getMotoMid()+" : moto tid: "+entity.getMotoTid());
		logger.info("ezypass mid: "+entity.getEzypassMid()+" : ezypass tid: "+entity.getEzypassTid());
		logger.info("ezyrec mid: "+entity.getEzyrecMid()+" : ezypass tid: "+entity.getEzyrecTid());
		
		Merchant currentmerchant=merchantDAO.loadMerchant(mid);
		
		
		MerchantDetails merchantDetails1 = promotionDAO
				.loadMerchantPointsbyMid(currentmerchant);
		
		/*if(merchantDetails1.getMid()!=null)
		{
			logger.info("merchant mid in merchantdetails: "+merchantDetails1.getMid());
		}*/
		// MobileUser mobileUser =

		/*
		 * TerminalDetails td = promotionDAO.loadDeviceID(entity.getDeviceId());
		 * 
		 * // logger.info("get deviceId:" + entity.getDeviceId()); if (td !=
		 * null) { entity.setTid(td.getTid());
		 * 
		 * }
		 */

		if(entity.getMid()!=null && entity.getTid()!=null){ 
			promotion.setMid(entity.getMid());
			promotion.setTid(entity.getTid());
			logger.info("promotion mid: "+promotion.getMid()+" : tid: "+promotion.getTid());
		}
		else if(entity.getMotoMid()!=null && entity.getMotoTid()!=null ){
			promotion.setMid(entity.getMotoMid());
			promotion.setTid(entity.getMotoTid());
			logger.info("promotion moto mid: "+promotion.getMid()+" : moto tid: "+promotion.getTid());
		}
		else if(entity.getEzypassMid()!=null && entity.getEzypassTid()!=null ){
			promotion.setMid(entity.getEzypassMid());
			promotion.setTid(entity.getEzypassTid());
			logger.info("promotion ezypass mid: "+promotion.getMid()+" : ezypass tid: "+promotion.getTid());
		}
		else if(entity.getEzyrecMid()!=null && entity.getEzyrecTid()!=null ){
			logger.info("ezyre; "+entity.getEzyrecMid());
		promotion.setMid(entity.getEzyrecMid());
		promotion.setTid(entity.getEzyrecTid());
		logger.info("promotion ezyrec mid: "+promotion.getMid()+" : ezypass tid: "+promotion.getTid());
	}
		
		logger.info("promotion mid: "+promotion.getMid()+" promotion tid: "+promotion.getTid());
		promotion.setMerchantId(entity.getMerchantId());
		promotion.setPromoName(entity.getPromoName());
		promotion.setMerchantType(entity.getSendType());
		promotion.setMerchantName(entity.getMerchantName());
		/*promotion.setMid(promotion.getMid());
		promotion.setTid(promotion.getTid());*/

		promotion.setSentType(entity.getSendType());

		promotion.setStatus(CommonStatus.PENDING);
		promotion.setMsgFlag("P");
		promotion.setCreatedBy(currentmerchant.getBusinessName());
		promotion.setValidFrom(entity.getValidFrom());
		promotion.setValidTo(entity.getValidTo());
		promotion.setPromoCode(entity.getpCode().toUpperCase());
		promotion.setCreatedDate(new Date());
		promotion.setImgPath1(entity.getPromoImgFilePath1());
		promotion.setMsgCount(entity.getPoints());
		promotion.setPromoDesc(entity.getPromoDesc());
		promotion.setImgPath2(entity.getPromoImgFilePath2());
		promotion.setMerchantType(entity.getMerchantType());
		
		int count = Integer.parseInt(merchantDetails1.getPoints())
				- Integer.parseInt(entity.getPoints());
		logger.info("get current count value:" + count);

	logger.info("send type: "+promotion.getSentType()+ " merhant type: "+promotion.getMerchantType());
		
		if(promotion.getTid()!=null){
			promotion = promotionDAO.saveOrUpdateEntity(promotion);
			merchantDetails1.setPoints(Integer.toString(count));
			merchantDetails1 = promotionDAO.saveOrUpdateEntity(merchantDetails1);
		}
		else{
			
			logger.info("mobileUser name not available.. so unable to add promotion");
		}
		
		

		if (promotion.getTid() != null) {
			logger.info("check entity status:" + promotion.getStatus());

			/*MID mid = merchantDAO.loadMid(promotion.getMid());
			Merchant merchant = merchantDAO.loadMerchant(mid);*/

			TempletFields tempField = new TempletFields();

			// tempField.setFirstName(merchant.getFirstName());
			// tempField.setLastName(merchant.getLastName());
			// PropertyLoader.getFile().getProperty("STAFF_NAME");
			// tempField.setSalutation(merchant.getSalutation());
			// tempField.setFirstName(merchant.getContactPersonName());
			// tempField.setUserName(merchant.getUsername());
			// tempField.setPassword(genPwd);
			tempField.setMerchantName(currentmerchant.getBusinessName());
			tempField.setFirstName(PropertyLoader.getFile().getProperty(
					"PROMOTION_EMAIL_NAME"));
			tempField.setPromoCode(promotion.getPromoCode());
			tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy")
					.format(new java.util.Date()));

			List<NameValuePair> headers = new ArrayList<NameValuePair>();
			headers.add(new NameValuePair("HEADER", "test"));
			// EZYWIRE AS USERNAME & password mobiversa
			String fromAddress = "info@gomobi.io";
			// String apiKey = "c652b570-9500-4534-8eb6-96a78c10c8b8";
			String apiKey = PropertyLoader.getFile().getProperty("APIKEY");
			
			/*String toAddress = "yusri@mobiversa.com,ethan@mobiversa.com";
			String ccMail="rachel@mobiversa.com";
			*/
			String toAddress = "karthiga@mobiversa.com";
			 String ccMail=null;
			// String bccMail = "yusri@mobiversa.com";
			// String subject = "Account Creation Mail";// set
			String subject = PropertyLoader.getFile().getProperty(
					"PROMOTION_EMAIL_SUBJECT");

			String emailBody = PromoSampleEmailTemplet
					.sentTempletContent(tempField);
			PostmarkMessage message = new PostmarkMessage(fromAddress,
					toAddress, fromAddress, ccMail, subject, emailBody, true,
					"test-email", null, null);
			PostmarkClient client = new PostmarkClient(apiKey);

			try {
				client.sendMessage(message);
				System.out.println("Email Sent Successfully to" +entity.getUsername());
			} catch (PostmarkException pe) {
				System.out.println("Invalid Signature Base64 String");

			}
		}
		else
		{
			promotion=null;
			logger.info("mobileUser name not available.. so unable to send email..");
		}
		//logger.info("new promotion add Testmail Sent Succesfully to Mobiversa Admin:");
		return promotion;
	}

	public AddMerchantPromo addnonMerchantPromo(final AddMerchantPromo entity) // ,java.security.Principal
	// principal
	{

		Promotion promo = new Promotion();

		logger.info("merchant id: " + entity.getMerchantId());
		logger.info("merchant mid: " + entity.getMid());
		MerchantDetails merchantDetails1 = promotionDAO
				.loadMerchantPoints(entity.getMid());
		logger.info("merchant id: " + Long.parseLong(entity.getMerchantId()));

		/*
		 * List<MobileUser> mobileuser = promotionDAO
		 * .loadMobileUserDetails(Long.parseLong(entity.getMerchantId()));
		 * 
		 * logger.info("merchant id: " +
		 * Long.parseLong(entity.getMerchantId()));
		 * 
		 * Set<Long> tid = new HashSet<Long>(); for (MobileUser t : mobileuser)
		 * { if(t.getTid()!=null) { Long tids = Long.parseLong(t.getTid());
		 * logger.info("mobile user name: " + tids);
		 * promo.setTid(entity.getTid()); tid.add(tids); } }
		 */

		MobileUser mobileUser = promotionDAO
				.loadMobileUserDetailsByUsername(entity.getUsername());

		if (mobileUser != null) {
			promo.setTid(mobileUser.getTid());
		}

		promo.setMerchantId(entity.getMerchantId());
		promo.setPromoName(entity.getPromoName());
		promo.setMerchantName(entity.getMerchantName());
		promo.setMid(entity.getMid());

		promo.setSentType(entity.getSendType());

		promo.setStatus(CommonStatus.PENDING);

		// promotion validity date changes start
		/*
		 * logger.info("check validity date21323123:" +
		 * entity.getValidityDate());
		 * mm.setValidityDate(entity.getValidityDate());
		 * logger.info("check validity date:" + mm.getValidityDate());
		 */
		// end

		promo.setValidFrom(entity.getValidFrom());
		promo.setValidTo(entity.getValidTo());
		promo.setPromoCode(entity.getpCode().toUpperCase());
		promo.setCreatedDate(new Date());
		promo.setImgPath1(entity.getPromoImgFilePath1());

		promo.setImgPath2(entity.getPromoImgFilePath2());

		

		/*
		 * if (entity.getPoints().equals(merchantDetails1.getPoints())) { //
		 * logger.info("get points from merchant details:" //
		 * +merchantDetails1.getPoints()); merchantDetails1 = promotionDAO
		 * .saveOrUpdateEntity(merchantDetails1);
		 * 
		 * }
		 * 
		 * else {
		 */
		// }

		StringBuffer sf = new StringBuffer();
		String img1 = null;

		promo.setMsgCount(entity.getPoints());
		promo.setPromoDesc(entity.getPromoDesc());
		promo.setMsgFlag("P");

		if(entity.getPoints()!=null)
		{
			int count = Integer.parseInt(merchantDetails1.getPoints())
					- Integer.parseInt(entity.getPoints());
			logger.info("get current count value:" + count);
			logger.info("add promitonn msg points: "+entity.getPoints());
			merchantDetails1.setPoints(Integer.toString(count));

		}
		
		merchantDetails1 = promotionDAO.saveOrUpdateEntity(merchantDetails1);
		
		promo = promotionDAO.saveOrUpdateEntity(promo);

		if (promo != null) {
			logger.info("check entity status:" + promo.getStatus());

			MID mid = merchantDAO.loadMid(promo.getMid());
			Merchant merchant = merchantDAO.loadMerchant(mid);

			TempletFields tempField = new TempletFields();

			// tempField.setFirstName(merchant.getFirstName());
			// tempField.setLastName(merchant.getLastName());
			// PropertyLoader.getFile().getProperty("STAFF_NAME");
			// tempField.setSalutation(merchant.getSalutation());
			// tempField.setFirstName(merchant.getContactPersonName());
			// tempField.setUserName(merchant.getUsername());
			// tempField.setPassword(genPwd);
			tempField.setMerchantName(merchant.getBusinessName());
			tempField.setFirstName(PropertyLoader.getFile().getProperty(
					"PROMOTION_EMAIL_NAME"));
			tempField.setPromoCode(promo.getPromoCode());
			tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy")
					.format(new java.util.Date()));

			List<NameValuePair> headers = new ArrayList<NameValuePair>();
			headers.add(new NameValuePair("HEADER", "test"));
			// EZYWIRE AS USERNAME & password mobiversa
			String fromAddress = "info@gomobi.io";
			// String apiKey = "c652b570-9500-4534-8eb6-96a78c10c8b8";
			String apiKey = PropertyLoader.getFile().getProperty("APIKEY");
			
			String toAddress = "yusri@mobiversa.com,ethan@mobiversa.com";
			String ccMail="rachel@mobiversa.com";
			
			/*String toAddress = "karthiga@mobiversa.com";
		 String ccMail=null;*/
			// String bccMail = "yusri@mobiversa.com";
			// String subject = "Account Creation Mail";// set
			String subject = PropertyLoader.getFile().getProperty(
					"PROMOTION_EMAIL_SUBJECT");

			String emailBody = PromoSampleEmailTemplet
					.sentTempletContent(tempField);
			PostmarkMessage message = new PostmarkMessage(fromAddress,
					toAddress, fromAddress, ccMail, subject, emailBody, true,
					"test-email", null, null);
			PostmarkClient client = new PostmarkClient(apiKey);

			try {
				client.sendMessage(message);
				// System.out.println("Email Sent Successfully to" +
				// entity.getUsername());
			} catch (PostmarkException pe) {
				System.out.println("Invalid Signature Base64 String");

			}
		}
		logger.info("new promotion add Testmail Sent Succesfully to Mobiversa Admin:");
		return entity;
	}

	// / new method for promotion
	public Promotion loadMerchantByPk(final Long id) {
		logger.info("promotion load promotion id of merchant: " + id);
		Promotion mPromo = promotionDAO.loadEntityByKey(Promotion.class, id);
		if (mPromo == null) {
			throw new RuntimeException("Merchant Not found. ID:: " + id);
		}
		return mPromo;
	}

	// updated promotion details in admin login
	// @javax.transaction.Transactional

	public Promotion updateMerchantPromotion(final AddMerchantPromo promo,
			Promotion mobiPromo) {

		Promotion promotion = new Promotion();

		Merchant currentMerchant = merchantService.loadMidtoUpdateAudit(mobiPromo
				.getMid());
		MerchantDetails merchantDetails = promotionDAO
				.loadMerchantPointsallmid(currentMerchant);
		// MerchantDetails merchantDetails = new MerchantDetails();
		/*MerchantDetails merchantDetails = promotionDAO
				.loadMerchantPointsallmid(mobiPromo.getMid());*/
		promotion.setPromoName(promo.getPromoName());

		mobiPromo.setId(Long.parseLong(promo.getId()));

		mobiPromo.setPromoName(promo.getPromoName());
		mobiPromo.setPromoDesc(promo.getPromoDesc());

		// mobiPromo.setValidityDate(promo.getValidityDate());

		if (mobiPromo.getStatus() != null) {
			if (promo.getStatus().equalsIgnoreCase("APPROVED")) {

				mobiPromo.setStatus(CommonStatus.APPROVED);
				mobiPromo.setModifiedDate(new Date());
				mobiPromo.setActivateDate(new Date());
				// if (promo.getStatus().equals("APPROVED")) {

				//MID mid = merchantDAO.loadMid(mobiPromo.getMid());
				//Merchant merchant = merchantDAO.loadMerchant(mid);

				List<NameValuePair> headers = new ArrayList<NameValuePair>();
				headers.add(new NameValuePair("HEADER", "test"));
				// EZYWIRE AS USERNAME & password mobiversa
				String fromAddress = "info@gomobi.io";
				// String apiKey = "c652b570-9500-4534-8eb6-96a78c10c8b8";
				String apiKey = PropertyLoader.getFile().getProperty("APIKEY");
				String toAddress = currentMerchant.getEmail();
				/*String ccMail = "karthiga@mobiversa.com";
				String bccMail = null;*/

				
				 String ccMail = "premkumar@mobiversa.com"; 
				 String bccMail ="ethan@mobiversa.com,yusri@mobiversa.com";
				 
				// String subject = "Account Creation Mail";// set
				// String subject =
				// PropertyLoader.getFile().getProperty("PROMOMAIL_SUBJECT");
				String subject = mobiPromo.getPromoName();

				TempletFields tempField = new TempletFields();

				tempField.setPromoName(mobiPromo.getPromoName());
				tempField.setPromoCode(mobiPromo.getPromoCode());
				tempField.setPromoDesc(mobiPromo.getPromoDesc());
				tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy")
						.format(new java.util.Date()));
				tempField.setMerchantAddress(currentMerchant.getBusinessAddress1());
				tempField.setMerchantPhoneno(currentMerchant
						.getBusinessContactNumber());
				tempField.setMerchantName(currentMerchant.getBusinessName());

				StringBuffer sf = new StringBuffer();
				String img1 = null;
				String img2 = null;
				if (mobiPromo.getImgPath1() != null) {
					FileInputStream fs = null;
					try {

						/*
						 * logger.info("disply promo image file path1:" +
						 * promotion.getImgPath1());
						 */
						File img = new File(mobiPromo.getImgPath1());
						tempField.setPromoImg1(img.getName());
						fs = new FileInputStream(img);
						byte imagedata[] = new byte[(int) img.length()];
						fs.read(imagedata);
						// String imgread = Base64.encode(fs.t)
						String imgRead = Base64.encode(imagedata);

						// System.out.println("Data");
						sf.append(imgRead);
						fs.close();
						img1 = imgRead.toString();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (fs != null) {
							try {
								fs.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}

				if (mobiPromo.getImgPath2() != null) {
					FileInputStream fs = null;
					try {

						logger.info("disply promo iamge file path2:"
								+ mobiPromo.getImgPath2());
						File img = new File(mobiPromo.getImgPath2());
						tempField.setPromoImg2(img.getName());
						fs = new FileInputStream(img);
						byte imagedata[] = new byte[(int) img.length()];
						fs.read(imagedata);
						String imgRead1 = Base64.encode(imagedata);

						sf.append(imgRead1);
						fs.close();
						img2 = imgRead1.toString();

					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (fs != null) {
							try {
								fs.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}

				String emailBody = null;

				Attachment faceBook = new Attachment("mobi_facebook.jpg",
						"image/jpg", PropertyLoader.getFile().getProperty(
								"PROMO_FACEBOOK"), "cid:mobi_facebook");

				Attachment twitter = new Attachment("mobi_twitter.jpg",
						"image/jpg", PropertyLoader.getFile().getProperty(
								"PROMO_TWITTER"), "cid:mobi_twitter");

				Attachment link = new Attachment("mobiversa_link.jpg",
						"image/jpg", PropertyLoader.getFile().getProperty(
								"PROMO_LINK"), "cid:mobiversa_link");

				Attachment insta = new Attachment("mobi_linkedin.jpg",
						"image/jpg", PropertyLoader.getFile().getProperty(
								"PROMO_INSTAGRAM"), "cid:mobi_instagram");

				Attachment logo = new Attachment("mobiversa_logo1.jpg",
						"image/jpg", PropertyLoader.getFile().getProperty(
								"PROMO_MOBI_LOGO"), "cid:mobiversa_logo1");

				/*
				 * Attachment Ezywire = new Attachment("Ezywire_Logo.png",
				 * "image/jpg", PropertyLoader.getFile().getProperty("EZYWIRE"),
				 * "cid:Ezywire_Logo");
				 */

				List<Attachment> attachments = new ArrayList<Attachment>();
				attachments.add(logo);
				attachments.add(faceBook);
				attachments.add(twitter);
				attachments.add(link);
				attachments.add(insta);
				// attachments.add(Ezywire);

				if (mobiPromo.getImgPath2() != null) {
					// Attachment promoImage1 = new Attachment("promo_image1",
					// "image/jpg", img1, "cid:promo_image1");
					// Attachment promoImage2 = new Attachment("promo_image2",
					// "image/jpg", img2, "cid:promo_image2");

					Attachment promoimg1 = new Attachment(
							tempField.getPromoImg1(), "image/jpg", img1, "cid:"
									+ tempField.getPromoImg1());
					Attachment promoimg2 = new Attachment(
							tempField.getPromoImg2(), "image/jpg", img2, "cid:"
									+ tempField.getPromoImg2());

					// logger.info("attachement image1:" +
					// attachments.add(promoImage1));
					// logger.info("attachement image1:" +
					// attachments.add(promoImage1));

					// tempField.setPromoImg2(file.getName());
					attachments.add(promoimg1);
					attachments.add(promoimg2);
					emailBody = PromoEmailTemplet2
							.sentTempletContent(tempField);
				} else {
					// Attachment promoImage1 = new Attachment("promo_image1",
					// "image/jpg", img1, "cid:promo_image1");
					Attachment promoimg1 = new Attachment(
							tempField.getPromoImg1(), "image/jpg", img1, "cid:"
									+ tempField.getPromoImg1());

					attachments.add(promoimg1);
					emailBody = PromoEmailTemplet1
							.sentTempletContent(tempField);
				}
				/* bccMail, */
				PostmarkMessage message = new PostmarkMessage(fromAddress,
						toAddress, fromAddress, ccMail, bccMail, subject,
						emailBody, true, "test-email", null, attachments);
				PostmarkClient client = new PostmarkClient(apiKey);

				try {
					client.sendMessage(message);
					logger.info("Email Sent Successfully to"
							+ currentMerchant.getEmail());
				} catch (PostmarkException pe) {
					logger.info("Invalid Signature Base64 String");

				}

				// get tid for Push notification
				MobileUser mobi = promotionDAO.loadMobileUser(mobiPromo
						.getTid());

				if (mobi != null) 
				{
					if (mobi.getDeviceType().equals("IOS")) 
					{
						IosPushNotifications obj1 = new IosPushNotifications();
						logger.info("check push notification for IOS :"
								+ mobi.getDeviceToken());
						obj1.sendNotification(mobi.getDeviceToken(), "1",
								mobiPromo.getPromoCode(),
								mobiPromo.getPromoName());
					}
					else {
						AndroidPushNotification obj = new AndroidPushNotification();
						logger.info("check push notification for Android:"
								+ mobi.getDeviceToken());

						try {

							obj.sendNotification(mobi.getDeviceToken(),
									mobiPromo.getPromoCode(),
									mobiPromo.getPromoName());

							logger.info("check push notification:"
									+ mobi.getDeviceToken());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}

			else if (promo.getStatus().equalsIgnoreCase("PENDING")) {

				mobiPromo.setPromoCode(promo.getpCode());

				mobiPromo.setCreatedDate(new Date());

				mobiPromo.setStatus(CommonStatus.PENDING);
				logger.info("merchant promotion status:"
						+ mobiPromo.getStatus());

			}

			else if (promo.getStatus().equalsIgnoreCase("REPOST")) {

				mobiPromo.setModifiedDate(new Date());

				mobiPromo.setStatus(CommonStatus.REPOST);

				logger.info("merchant promotion status:"
						+ mobiPromo.getStatus());
			}

			else if (promo.getStatus().equalsIgnoreCase("CANCELLED")) {

				mobiPromo.setModifiedDate(new Date());

				mobiPromo.setStatus(CommonStatus.CANCELLED);
				// logger.info("merchant promotion status:" +
				// mobiPromo.getStatus());

				// logger.info("check merchant details:" +
				// mobiPromo.getMsgCount());

				// logger.info("check merchant mid from merchantdetails table:"
				// +mobiPromo.getMid());
				int count = Integer.parseInt(merchantDetails.getPoints())
						+ Integer.parseInt(mobiPromo.getMsgCount());

				logger.info("merchant details from merchant details table:"
						+ merchantDetails.getPoints());
				logger.info("get current count value:" + count);

				merchantDetails.setPoints(Integer.toString(count));
				merchantDetails = promotionDAO
						.saveOrUpdateEntity(merchantDetails);

				// logger.info("merchant promotion status:" +
				// mobiPromo.getStatus());

			}

			// }
			// }

			else if (promo.getStatus().equalsIgnoreCase("DELETED")) {

				mobiPromo.setModifiedDate(new Date());

				mobiPromo.setStatus(CommonStatus.DELETED);
				// logger.info("merchant promotion status:" +
				// mobiPromo.getStatus());

			}

			/*
			 * else if (promo.getStatus().equalsIgnoreCase("APPROVED")) {
			 * 
			 * mobiPromo.setModifiedDate(new Date());
			 * 
			 * mobiPromo.setStatus(CommonStatus.APPROVED);
			 * logger.info("merchant promotion status:" +
			 * mobiPromo.getStatus());
			 * 
			 * }
			 */

			promotionDAO.saveOrUpdateEntity1(mobiPromo);

		}

		return mobiPromo;

	}

	// merchant promotion details in admin portal 03/03/2017
	// @javax.transaction.Transactional
	public void listMerchantPromo(final PaginationBean<Promotion> paginationBean) {

		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		// criterionList.add(Restrictions.not(Restrictions.eq("status",
		// CommonStatus.APPROVED)));
		criterionList.add(Restrictions.not(Restrictions.eq("status",
				CommonStatus.CANCELLED)));
		criterionList.add(Restrictions.not(Restrictions.eq("status",
				CommonStatus.DELETED)));
		criterionList.add(Restrictions.not(Restrictions.eq("status",
				CommonStatus.SENT)));
		promotionDAO.listMerchantPromo(paginationBean, criterionList);
	}

	public MerchantDetails loadMerchantPoints(String mid) {
		// Agent agent = new Agent();
		logger.info("in loadmerchnatpoints : " + mid);
		return promotionDAO.loadMerchantPoints(mid);
		// return agent;

	}

	public MerchantDetails loadMerchantPointsallmid(Merchant merchant) {
		// Agent agent = new Agent();
		logger.info("in loadmerchnatpoints : " );
		return promotionDAO.loadMerchantPointsallmid(merchant);
		// return agent;

	}

	public MerchantDetails loadMerchantPointsbymid(Merchant merchant) {
		// Agent agent = new Agent();
		if(merchant.getMid().getMid()!=null){
		logger.info("in loadmerchnatpoints mid : " + merchant.getMid().getMid());
		}
		else if(merchant.getMid().getMid()!=null){
			logger.info("in loadmerchnatpoints motomid: " + merchant.getMid().getMotoMid());
			}
		else if(merchant.getMid().getMid()!=null){
			logger.info("in loadmerchnatpoints ezypassmid: " + merchant.getMid().getEzypassMid());
			}
		return promotionDAO.loadMerchantPointsbyMid(merchant);
		// return agent;

	}
	public MerchantDetails UpdateMerchantPoints(String mid,String points) {
		
		logger.info("merchatn id UpdateMerchantPoints  "+mid+":"+points);
		MerchantDetails merchantdetails=promotionDAO.loadMerchantPoints(mid);
		 int count = Integer.parseInt(points);
		if (points != null) 
		{
			 count = Integer.parseInt(merchantdetails.getPoints())-Integer.parseInt(points);
			 merchantdetails.setPoints(String.valueOf(count));
			 logger.info("message total count: "+count);
			
		
		}
		return promotionDAO.saveOrUpdateEntity(merchantdetails);
		// return agent;

	}
	/*
	 * public MerchantDetails loadnonMerchantPoints(int id) { // Agent agent =
	 * new Agent(); logger.info("in loadmerchnatpoints : "+id); return
	 * promotionDAO.loadnonMerchantPoints(id); // return agent;
	 * 
	 * }
	 */

	// updated promotion details edited in merchant login
	public Promotion updatePromotionDetails(final AddMerchantPromo merchantPromo, Promotion promo)
	{

		MerchantDetails merchantDetails1 = promotionDAO.loadMerchantPoints(promo.getMid());

		if (merchantPromo.getStatus() != null) {
			if (merchantPromo.getStatus().equalsIgnoreCase("REPOST")) {
				int count = 0;
				if (merchantPromo.getPoints() != null) {
					 count = Integer.parseInt(merchantDetails1.getPoints())
							- Integer.parseInt(merchantPromo.getPoints());
				}

				

				promo.setMsgCount(merchantPromo.getPoints());

				merchantDetails1.setPoints(Integer.toString(count));

				promo.setModifiedDate(new Date());

				logger.info("check validity date service method page 2:"
						+ promo.getValidFrom());
				// promo.setValidityDate(merchantPromo.getValidityDate());
				promo.setValidFrom(merchantPromo.getValidFrom());
				promo.setValidTo(merchantPromo.getValidTo());

				promo.setStatus(CommonStatus.REPOST);
				logger.info("merchant promotion status:" + promo.getStatus());

			}
			if (merchantPromo.getStatus().equalsIgnoreCase("CANCELLED")) {

				promo.setModifiedDate(new Date());
				promo.setStatus(CommonStatus.CANCELLED);
				// logger.info("merchant promotion status:" +
				// promo.getStatus());
				// logger.info("check merchant details:" + promo.getMsgCount());

				// logger.info("check merchant mid from merchantdetails table:"
				// +promo.getMid());
				int count = Integer.parseInt(merchantDetails1.getPoints())
						+ Integer.parseInt(promo.getMsgCount());

				// logger.info("merchant details from merchant details table:" +
				// merchantDetails1.getPoints());
				// logger.info("get current count value:" + count) ;

				// logger.info("get points from merchant details:"
				// +merchantDetails1.getPoints());

				merchantDetails1.setPoints(Integer.toString(count));
				merchantDetails1 = promotionDAO
						.saveOrUpdateEntity(merchantDetails1);

				// logger.info("merchant promotion status:" +
				// promo.getStatus());

			}

			// }

			if (merchantPromo.getStatus().equalsIgnoreCase("DELETED")) {

				promo.setModifiedDate(new Date());
				promo.setStatus(CommonStatus.DELETED);
				// logger.info("merchant promotion status:" +
				// promo.getStatus());

			}

			if (merchantPromo.getStatus().equalsIgnoreCase("APPROVED")) {

				promo.setActivateDate(new Date());
				promo.setStatus(CommonStatus.APPROVED);
				logger.info("nonmerchant promotion status:" + promo.getStatus());

			}

			if (merchantPromo.getStatus().equalsIgnoreCase("SENT")) {

				promo.setActivateDate(new Date());
				promo.setStatus(CommonStatus.SENT);
				logger.info("nonmerchant promotion status:" + promo.getStatus());

			}

			if (merchantPromo.getStatus().equalsIgnoreCase("PENDING")) {

				promo.setModifiedDate(new Date());
				promo.setStatus(CommonStatus.PENDING);
				logger.info("nonmerchant promotion status:" + promo.getStatus());
			}

			promotionDAO.saveOrUpdateEntity1(merchantDetails1);

			promotionDAO.saveOrUpdateEntity1(promo);
		}

		return promo;
	}

	public Merchant loadMerchantByMid(final String mid) {
		return promotionDAO.loadMerchantByMid(mid);
	}

	public List<TerminalDetails> loadTerminalDetails(final String mid) {
		return promotionDAO.loadTerminalDetails(mid);
	}

	public List<MobileUser> loadMobileUserDetails(final Long id) {
		logger.info("load mobile user details: id " + id);
		return promotionDAO.loadMobileUserDetails(id);
	}

	public List<MobileUser> loadMobileUserDetailsbytid(String tid) {
		logger.info("load mobile user details: tid " + tid);
		return promotionDAO.loadMobileUserDetailsbytid(tid);
	}
	/*
	 * public List<MobileUser> loadMobileUserDetails(final Long id) {
	 * logger.info("load mobile user details: mid "+id); return
	 * promotionDAO.loadMobileUserDetails(id); }
	 */
	public TerminalDetails loadTid(final String tid) {
		return promotionDAO.loadTid(tid);
	}

	// new method for promotion summary search condition 11072017

	public void listPromotionSearch(
			final PaginationBean<Promotion> paginationBean, final String date,
			final String date1) {
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();

		// Date dat = null;
		// Date dat1 = null;
		if (date != null) {
			// criterionList.add(Restrictions.eq("activateDate", data));
			// logger.info("check activateDate42323:" + date);
			String dat = date;

			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSS");

			try {
				dat = format.format(new SimpleDateFormat("MM/dd/yyyy")
						.parse(dat));
				Timestamp ts = Timestamp.valueOf(dat);
				// logger.info("check from date1:" + ts);
				criterionList.add(Restrictions.ge("activateDate", ts));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (date1 != null) {
			String dat1 = date1;
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss.SSS");
			try {
				dat1 = format.format(new SimpleDateFormat("MM/dd/yyyy")
						.parse(dat1));
				Timestamp ts = Timestamp.valueOf(dat1);
				// logger.info("check to date2:" + ts);
				criterionList.add(Restrictions.lt("activateDate", ts));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// criterionList.add(Restrictions.lt("activateDate", dat1));
			// logger.info("check activateDate to date121212:" + date1);
		}
		promotionDAO.listPromotionSearch(paginationBean, criterionList);
	}
	// }

	public Merchant loadMerchantbymid(final String mid) {
		logger.info("loadmobilemerchant: " + mid);
		MID mid1 = merchantDAO.loadMid(mid);

		return merchantDAO.loadMerchantbyid(mid1);
	}

	
}
