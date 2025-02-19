package com.mobiversa.payment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.AgentDao;
import com.mobiversa.payment.dao.SubAgentDao;
import com.mobiversa.payment.dao.UserDao;
import com.mobiversa.payment.dto.RegSubAgent;
//import com.postmark.java.EmailTemplet;

@Service
public class SubAgentService {

	@Autowired
	private SubAgentDao subAgentDAO;
	
	@Autowired
	private AgentDao agentDAO;


	@Autowired
	private PasswordEncoder encoder;
	
	//@Autowired
	private UserDao userDAO;
 
	  private static final Logger logger=Logger.getLogger(SubAgentService.class.getName());

	
	
	
	//@javax.transaction.Transactional
	public void listSubAgent(final PaginationBean<SubAgent> paginationBean) {
		logger.info("Service : about to list all agent1");
		ArrayList<Criterion> criterionList = new ArrayList<Criterion>();
		logger.info("Service : about to list all agent2");
		subAgentDAO.listSubAgentUser(paginationBean, criterionList);
	}
	
	
	 public  SubAgent loadSubAgentByPk(final Long id) {
			SubAgent subagent = subAgentDAO.loadEntityByKey(SubAgent.class, id);
			if (subagent == null) {
				throw new RuntimeException("SubAgent Not found. ID:: " + id);
			}
			return subagent;
		}

	 
	 //@javax.transaction.Transactional
		public SubAgent loadAgentbyMailId(String email){
			SubAgent subagent = new SubAgent();
			return subAgentDAO.loadAgentbyMailId(email);
			//return agent;
			
		}
	 
	 
	 public SubAgent addSubAgent(final RegSubAgent entity) {
			
			SubAgent subagent = new SubAgent();
			
			
			String agentData = entity.getAgentName();
			String agentMail[] = null;
			if(agentData.contains("~")){
				agentMail = agentData.split("~");
				
			}
			String agEmail = agentMail[1];
			Agent agent = agentDAO.loadAgentbyMailId(agEmail);
			
			System.out.println("Agent Data : "+ agent.getId());
			//RandomPassword rpwd = new RandomPassword();
			//logger.info("agent1 :"+entity.getFirstName());
			//entity.setEnabled(true);
			logger.info("agent1 :"+entity.getMailId());
			//entity.setUsername(entity.getMailId());
			
			//logger.info("agent1 :"+entity.getUsername());
			
			subagent.setStatus(CommonStatus.ACTIVE);
			logger.info("agent1 :"+entity.getType());
			String code = subAgentDAO.loadMaxID(entity.getType());
			logger.info("code :"+code);
			
			int acode = Integer.parseInt(code);
			acode++;
			
			logger.info("code1 :"+acode);
			code = String.valueOf(acode);
			logger.info("code1 :"+code);
			String fillZero="";
			for(int a=code.length(); a<4; a++){
				fillZero = fillZero+"0";
			}
			String agCode = fillZero+ String.valueOf(code);
			logger.info("code1 :"+agCode);
			//entity.setCode(agCode);
			subagent.setCode(agCode);
			subagent.setName(entity.getName());
			subagent.setSalutation(entity.getSalutation());
			subagent.setAddr1(entity.getAddr1());
			subagent.setAddr2(entity.getAddr2());
			subagent.setCity(entity.getCity());
			subagent.setPostCode(entity.getPostCode());
			subagent.setState(entity.getState());
			subagent.setMailId(entity.getMailId());
			subagent.setPhoneNo(entity.getPhoneNo());
			subagent.setType(entity.getType());
			subagent.setCreatedDate(new Date());
			
			//RegSubAgent regSubAgent=new RegSubAgent();
			 //RegSubAgent regSubAgent1=new RegSubAgent();
				
				
				Long bi = new Long(agent.getId().toString());
				System.out.println("Data   sfsfsfdf : "+ bi);
				//subagent.setId(bi);
				//subagent.set
				subagent.setAgent(agent);
				
				//entity.setAgentName("");
			//String genPwd = rpwd.generateRandomString();
			//logger.info("code1 :"+genPwd);
			//entity.setPassword("abc123");
			
			//entity.setPassword(encoder.encode(genPwd));
			//logger.info("code1 :"+entity.getPassword());
			//entity.setRole(AgentUserRole.AGENT_USER);
			System.out.println("add agent:" + subagent);
			SubAgent subagent1 =  subAgentDAO.saveOrUpdateEntity(subagent);
			System.out.println("code:" + entity.getCode());
			
			
			return subagent1;
		}
	 
	 
	 //@javax.transaction.Transactional
		public SubAgent updateSubAgent(final RegSubAgent regsubagent,SubAgent subagent) {
		 
		 System.out.println(" Data Id "+regsubagent.getId());
		 subagent.setId(Long.parseLong(regsubagent.getId()));
		 
		 subagent.setName(regsubagent.getName());
		 subagent.setSalutation(regsubagent.getSalutation());
		 subagent.setAddr1(regsubagent.getAddr1());
		 subagent.setAddr2(regsubagent.getAddr2());
		 subagent.setCity(regsubagent.getCity());
		 subagent.setPostCode(regsubagent.getPostCode());
		 subagent.setState(regsubagent.getState());
		 subagent.setPhoneNo(regsubagent.getPhoneNo());
		 subagent.setType(regsubagent.getType());
		 subagent.setMailId(regsubagent.getMailId());
		 subagent.setCode(regsubagent.getCode());
		 
			subagent.setStatus(CommonStatus.ACTIVE);
			
	 	subAgentDAO.saveOrUpdateEntity(subagent);
			
			return subagent;

		}
	 
	 
	 public List<SubAgent> loadCurrentSubAgent(final Agent agent) {
			return subAgentDAO.loadCurrentSubAgent(agent);
		}
	 
	 public List<SubAgent> loadSubAgent() {
			return subAgentDAO.loadSubAgent();
		}
	 
	
	 
	 // agent volume summary  method used 08/08/2016
	 
	 //@javax.transaction.Transactional
		public List<SubAgent> loadSubAgentbyId(Agent agent){
			//SubAgent subagent = new SubAgent();
			return subAgentDAO.loadSubAgentById(agent);
			//return agent;
			
		}
		
}
