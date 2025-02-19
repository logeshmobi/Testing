package com.mobiversa.payment.service;

import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.MasterMerchant;
import com.mobiversa.common.bo.MasterMerchantUserRole;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.AgentDao;
import com.mobiversa.payment.dao.MasterMerchantDao;
import com.mobiversa.payment.dao.WebUserDao;
import com.mobiversa.payment.util.RandomPassword;

@Service
public class MasterMerchantService {

	@Autowired
	private MasterMerchantDao masterMerchantDAO;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private AgentDao agentDAO;
	
	
	//@Autowired
	private WebUserDao userDAO;
 
	  private static final Logger logger=Logger.getLogger(MasterMerchantService.class.getName());
	
	  
	  public MasterMerchant loadMMbyMailId(String email){
		  MasterMerchant mastermerchant = new MasterMerchant();
			return masterMerchantDAO.loadMMbyMailId(email);
			//return agent;
			
		}
	  
	  public void listMasterMerchant(final PaginationBean<MasterMerchant> paginationBean) {
			logger.info("Service : about to list all Master Merchants");
			ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
			criterionList.add(Restrictions.eq("status", CommonStatus.ACTIVE));
			masterMerchantDAO.listMasterMerchantUser(paginationBean, criterionList);
		}
	  
	  
	  public MasterMerchant loadMasterMerchantByPk(final Long id) {
		  MasterMerchant mm = masterMerchantDAO.loadEntityByKey(MasterMerchant.class, id);
			if (mm == null) {
				throw new RuntimeException("Master Merchant Not found. ID:: " + id);
			}
			return mm;
		}
	  
	  public MasterMerchant addMasterMerchant(final MasterMerchant entity) {
			
		  MasterMerchant masterMerchant = new MasterMerchant();
			
			RandomPassword rpwd = new RandomPassword();
			//logger.info("agent1 :"+entity.getFirstName());
			entity.setEnabled(true);
			//logger.info("agent1 :"+entity.getMailId());
			entity.setUsername(entity.getMailId());
			
			//logger.info("agent1 :"+entity.getUsername());
			
			entity.setStatus(CommonStatus.ACTIVE);
			entity.setActivateDate(new Date());
			
			
			  String agentData = entity.getAgentName(); String agentMail[] = null; if
			  (agentData.contains("~")) { agentMail = agentData.split("~"); } String type =
			  agentMail[0]; String agEmail = agentMail[2];
			  
			  logger.info("Agent Type : " + type); logger.info("Mail : " + agEmail);
			  
			  Agent agent = null; agent = agentDAO.loadAgentbyMailId(agEmail); String AgID
			  = null; if (agent != null) { AgID = agent.getId().toString();
			  logger.info("Agent ID : " + AgID); }
			  
			  entity.setAgId(AgID);
			 
			
			
			String genPwd = rpwd.generateRandomString();
			//logger.info("code1 :"+genPwd);
			//entity.setPassword("abc123");
			
			entity.setPassword(encoder.encode(genPwd));
			//logger.info("code1 :"+entity.getPassword());
			entity.setRole(MasterMerchantUserRole.MM_USER);
			
			
			
			logger.info("masterMerchantmail Id:" + entity.getMailId());
			
				
			masterMerchant =  masterMerchantDAO.saveOrUpdateEntity(entity);
			
			/*TempletFields tempField = new TempletFields();
			
			tempField.setFirstName(merchant1.getFirstName());
			tempField.setLastName(merchant1.getLastName());
			if(masterMerchant.getSalutation() != null){
				tempField.setSalutation(masterMerchant.getSalutation());
			}else{
				tempField.setSalutation("Ms");
			}
			tempField.setFirstName(masterMerchant.getFirstName());
			tempField.setUserName(masterMerchant.getUsername());
			tempField.setPassword(genPwd);
			tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy").format(new java.util.Date()));
			
			
			List<NameValuePair> headers = new ArrayList<NameValuePair>();
			headers.add(new NameValuePair("HEADER", "test"));
	        //EZYWIRE AS USERNAME & password mobiversa
			String fromAddress = "info@gomobi.io";
			//String apiKey = "c652b570-9500-4534-8eb6-96a78c10c8b8";
			String apiKey = PropertyLoader.getFile().getProperty("APIKEY");
			String toAddress = masterMerchant.getUsername();
			String ccMail="ethan@mobiversa.com";
			String bccMail = "premkumar@mobiversa.com";
			//String subject = "Account Creation Mail";// set
			String subject = PropertyLoader.getFile().getProperty("WEBMAIL_SUBJECT");
			
			
			String emailBody = EmailTemplet11.sentTempletContent(tempField);
			
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

			PostmarkMessage message = new PostmarkMessage(fromAddress, toAddress,
					fromAddress, ccMail, bccMail, subject, emailBody, true, "test-email",
					null, attachments);
			PostmarkClient client = new PostmarkClient(apiKey);*/
			/*if(!(entity.getUsername() == null|| entity.getUsername().equals(""))){
			try {
				client.sendMessage(message);
				logger.info("Email Sent Successfully to"+entity.getUsername());
			} catch (PostmarkException pe) {
				logger.info("Invalid Signature Base64 String");
				
			}
			}*/
			
			return masterMerchant;
		}
	  
	  
	  
		@SuppressWarnings("unchecked")
		public void updateMasterMerchant(final MasterMerchant masterMerchant) {
			//logger.info("Service : about to list all agent1"+agent.getAgType());
			//Agent agent1 = new Agent();
			logger.info("Service : about to list all MasterMerchant"+masterMerchant.getMailId());
			
			MasterMerchant masterMerchant1 = masterMerchantDAO
					.loadMasterMerchantbyId(masterMerchant.getId());
			
			
			  String agentData = masterMerchant.getAgentName(); String agentMail[] = null;
			  if (agentData.contains("~")) { agentMail = agentData.split("~"); } String
			  type = agentMail[0]; String agEmail = agentMail[2];
			  
			  logger.info("Agent Type : " + type); logger.info("Mail : " + agEmail);
			  
			  Agent agent = null; agent = agentDAO.loadAgentbyMailId(agEmail); String AgID
			  = null; if (agent != null) { AgID = agent.getId().toString();
			  logger.info("Agent ID : " + AgID); }
			 
			
			
			masterMerchant1.setStatus(masterMerchant.getStatus());
			//logger.info("Service : about to list all agent2");
			masterMerchant1.setFirstName(masterMerchant.getFirstName());
			masterMerchant1.setLastName(masterMerchant.getLastName());
			masterMerchant1.setSalutation(masterMerchant.getSalutation());
			masterMerchant1.setAddr1(masterMerchant.getAddr1());
			masterMerchant1.setAddr2(masterMerchant.getAddr2());
			masterMerchant1.setBankAcc(masterMerchant.getBankAcc());
			masterMerchant1.setBankAcc(masterMerchant.getBankAcc());
			masterMerchant1.setBankName(masterMerchant.getBankName());
			masterMerchant1.setCity(masterMerchant1.getCity());
			masterMerchant1.setCode(masterMerchant.getCode());
			masterMerchant1.setPhoneNo(masterMerchant1.getPhoneNo());
			masterMerchant1.setPostCode(masterMerchant.getPostCode());
			masterMerchant1.setState(masterMerchant.getState());
			masterMerchant1.setMailId(masterMerchant.getMailId());
			masterMerchant1.setNricNo(masterMerchant.getNricNo());
			masterMerchant1.setAgId(AgID);
			
			
			masterMerchantDAO.saveOrUpdateEntity(masterMerchant1);

		}
		
	
	}
