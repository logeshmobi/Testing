package com.mobiversa.payment.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.dao.MerchantWebDao;

//public class MyMerchantWebService implements UserDetailsService {
@Service //("merchantUserDetailsService")
public class MyMerchantWebService implements UserDetailsService {

	@Autowired
	private MerchantWebDao merchantWebDao;

	protected final Logger logger = Logger.getLogger(MyMerchantWebService.class.getName());

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		logger.info("authenticating using MyMerchantWebService"+username);
		if(!(username==null||username=="")){
		Merchant user = merchantWebDao.findByUserName(username);
		/*
		 * logger.info("searching db for merchant using " + username + " : " +
		 * user); return user; }
		 */
		if(user!=null&&CommonStatus.ACTIVE.equals(user.getStatus()))
		{
			return user;	
		}else{
			logger.info("returns null");
			return null;
		}
		}
			return null;
		

	}

	public MerchantWebDao getUserDao() {
		return merchantWebDao;
	}

	public void setmerchantWebDao(final MerchantWebDao merchantWebDao) {
		this.merchantWebDao = merchantWebDao;
	}
}
