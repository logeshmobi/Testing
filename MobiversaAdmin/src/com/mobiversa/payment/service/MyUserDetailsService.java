package com.mobiversa.payment.service;

import java.util.Date;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.BankUser;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.dao.AgentWebDao;
import com.mobiversa.payment.dao.MerchantWebDao;
import com.mobiversa.payment.dao.UserDao;
import com.mobiversa.payment.dao.WebUserDao;

@Service
public class MyUserDetailsService implements UserDetailsService {

	protected final Logger logger = Logger.getLogger(MyUserDetailsService.class.getName());

	@Autowired
	private WebUserDao webUserDao;
	
	@Autowired
	private MerchantWebDao merchantWebDao;
	
	@Autowired
	private AgentWebDao agentWebDao;

	private PasswordEncoder encoder;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
	logger.info("authenticating using MyUserDetailsService :"+username);
		// Programmatic transaction management
		/*
		 * return transactionTemplate.execute(new
		 * TransactionCallback<UserDetails>() { public UserDetails
		 * doInTransaction(TransactionStatus status) {
		 * com.mobiversa.payment..User user = userDao.findByUserName(username);
		 * List<GrantedAuthority> authorities =
		 * buildUserAuthority(user.getUserRole()); return
		 * buildUserForAuthentication(user, authorities); } });
		 */
		if(!(username==null||username=="")){
				BankUser user = webUserDao.findByUserName(username);
				/*if(user != null){*/
					return user;
				/*}else{
					Merchant merchant = merchantWebDao.findByUserName(username);
					if(merchant != null){
						return merchant;
					}else{
						Agent agent = agentWebDao.findByAgentName(username);
						if( agent != null){
							return agent;
						}else{
							return null;
						}
					}
				}*/
				/*logger.info("username status   .......+" + user.getStatus());
				logger.info("searching db for bankUser using " + username + " : " + user);
				if(user!=null&&CommonStatus.ACTIVE.equals(user.getStatus())){
		            logger.info("bank user returns not null");*/
					//return user;
				/*}
				else{
					logger.info("return null");
					return null;
				}*/
		}return null;
	}

	public WebUserDao getWebUserDao() {
		return webUserDao;
	}

	public void setWebUserDao(WebUserDao webUserDao) {
		this.webUserDao = webUserDao;
	}

	/*public MerchantWebDao getMerchantWebDao() {
		return merchantWebDao;
	}

	public void setMerchantWebDao(MerchantWebDao merchantWebDao) {
		this.merchantWebDao = merchantWebDao;
	}

	public AgentWebDao getAgentWebDao() {
		return agentWebDao;
	}

	public void setAgentWebDao(AgentWebDao agentWebDao) {
		this.agentWebDao = agentWebDao;
	}
*/
	
	/*public void updateUser(BankUser user){
		webUserDao.saveOrUpdateEntity(user);
	}*/
	

	/*public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(final UserDao userDao) {
		this.userDao = userDao;
	}

	
	@SuppressWarnings("deprecation")
	public UserDetails loadUsersByUsername(final String username,
			final String password) throws UsernameNotFoundException {
		logger.info("authenticating using MyUserDetailsService");

		BankUser user = userDao.findByUserName(username);
		Boolean checkBoolean = encoder.matches(password, user.getPassword());
		logger.info("Searching db for bankUser using " + username + " : "+ user);
		if (checkBoolean) {
			logger.info("password  matches");
			if (!CommonStatus.ACTIVE.equals(user.getStatus())) { // Status is Not Active
				logger.info("....status not in active.....");
				Date supDate =user.getSuspendDate();
				Date now = new Date();
				if(supDate.getDate() < now.getDate()){ //For Locked User checking the date
					user.setActivateDate(now); // Updating activate date as now 
					user.setSuspendDate(null); // Updating suspended date 
					user.setStatus(CommonStatus.ACTIVE);// Updating Status as Active
					userDao.saveOrUpdateEntity(user);
					return user;
				}else{// Locked user trying in same date will not allow
					return null;
				}
			}else{
				Date actDate = user.getActivateDate();
				DateTime fromDate = new DateTime(actDate);
				DateTime toDate = new DateTime(new Date());
				if(Months.monthsBetween(toDate, fromDate).getMonths() > 90){ //Checking for password expiry
					user.setActivateDate(new Date()); // updating activated date
					userDao.saveOrUpdateEntity(user);
					user.setEnabled(false); //asked to reset password
					return user; // If password changed before 90 days (expired) 
				}else{
					return user;
				}
			}
		}else{
			int attempt = user.getFailedLoginAttempt();// Getting failed attempt
			Date supDate = user.getSuspendDate(); // Getting suspended date
			if(supDate.equals(new Date())){
				if(attempt < 5 ){
					attempt = attempt +1;
					user.setFailedLoginAttempt(attempt);
					userDao.saveOrUpdateEntity(user);
				}else{
					user.setStatus(CommonStatus.SUSPENDED); // Attempt > 4 to lock the user
					user.setSuspendDate(new Date()); // updating supdended date in DB and suspending the user
					userDao.saveOrUpdateEntity(user);
				}
			}else{
				user.setFailedLoginAttempt(1);
				userDao.saveOrUpdateEntity(user);
			}
		}
		return null;
	}
	 */
}
