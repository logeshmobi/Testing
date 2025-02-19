package com.mobiversa.payment.service;


import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.AgentUserRole;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.FileUpload;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantDetails;
import com.mobiversa.common.bo.MerchantStatusHistory;
import com.mobiversa.common.bo.MerchantUserRole;
import com.mobiversa.common.bo.Promotion;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.AgentDao;
import com.mobiversa.payment.dao.BaseDAOImpl;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.dao.MerchantProfileDao;
import com.mobiversa.payment.dao.PromotionDao;
import com.mobiversa.payment.dao.SubAgentDao;
import com.mobiversa.payment.dto.AddMerchantPromo;
import com.mobiversa.payment.dto.RegAddMerchant;
import com.mobiversa.payment.util.PropertyLoader;
import com.mobiversa.payment.util.RandomPassword;
import com.postmark.java.Attachment;
import com.postmark.java.EmailTemplet1;
import com.postmark.java.EmailTemplet10;
import com.postmark.java.EmailTemplet2;
import com.postmark.java.NameValuePair;
import com.postmark.java.PostmarkClient;
import com.postmark.java.PostmarkException;
import com.postmark.java.PostmarkMessage;
import com.postmark.java.TempletFields;


@Service
public class MerchantProfileService {
	
	@Autowired
	private MerchantProfileDao merchantProfDAO;
	 private static final Logger logger=Logger.getLogger(MerchantProfileService.class.getName());
	
	
	public Merchant loadMerchant(final String username) {
		return merchantProfDAO.loadMerchant(username);
	}
	
	
	
	 public RegAddMerchant addMerchantProf(final RegAddMerchant entity)  // return type need to change as RegAddMerchant
		
		{ 
		 
		 
		 Merchant merchant1 = merchantProfDAO.loadMerchant(entity.getOfficeEmail());

		 Merchant merchant = new Merchant();
		 logger.info("check email:" + entity.getOfficeEmail());
		 
		 entity.setBusinessName(merchant1.getBusinessName());
		 //entity.setMid(merchant1.getMid().getMid());
		// entity.getId(merchant1.getId().toString());
		 
		
		// merchant.setMerchantProfile(entity.getMerchantImgFilePath());
		 
		 if(!(entity.getMerchantProfile().equals(entity.getMerchantLogo())))
			{
				logger.info("check merchant image12132323:" + entity.getMerchantLogo());
			 entity.setMerchantLogo(entity.getMerchantProfile());
				
			}
		 
		 logger.info("merchant image file path 1:" + entity.getMerchantImgFilePath());
		// merchant1.setMerchantProfile(entity.getMerchantImgFilePath());
		 
		 
		 
		// logger.info("check merchant email path:" + merchant1.getMerchantProfile());
		 
		 merchant1 = merchantProfDAO.saveOrUpdateEntity(merchant1);
		
		 
		 return entity;
		}

}
