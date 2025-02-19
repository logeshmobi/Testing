package com.mobiversa.payment.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.payment.dao.AgentWebDao;

public class MyAgentWebService implements UserDetailsService {

	@Autowired
	private AgentWebDao agentWebDao;
	
	
	protected final Logger logger = Logger.getLogger(MyAgentWebService.class.getName());

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		logger.info("authenticating using myAgentWebService"+username);
		if(!(username==null||username=="")){
			Agent user = agentWebDao.findByAgentName(username);
		/*
		 * logger.info("searching db for merchant using " + username + " : " +
		 * user); return user; }
		 */
			System.out.println(" WEB SERVICE : "+user.getFirstName());
			System.out.println(" WEB SERVICE : "+user.getStatus());
			System.out.println(" WEB SERVICE : "+user.getCode());
		if(user!=null&&CommonStatus.ACTIVE.equals(user.getStatus()))
		{
			System.out.println(" WEB awewrhgfsbvfbnSERVICE : "+user.getCode());
			return (UserDetails) user;	
		}else{
			logger.info("returns null");
			return null;
		}
		}
			return null;
	}
	
	public AgentWebDao getUserDao() {
		return agentWebDao;
	}

	public void setagentWebDao(final AgentWebDao agentWebDao) {
		this.agentWebDao = agentWebDao;
	}

	
	
}
