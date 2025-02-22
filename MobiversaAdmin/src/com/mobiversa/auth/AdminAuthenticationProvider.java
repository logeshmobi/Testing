package com.mobiversa.auth;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.Months;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.BankUser;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiLiteMerchant;
import com.mobiversa.payment.dao.AgentWebDao;
import com.mobiversa.payment.dao.MerchantWebDao;
import com.mobiversa.payment.dao.WebUserDao;

//@Component
public class AdminAuthenticationProvider extends DaoAuthenticationProvider {
	
	@Autowired
	private UserDetailsService myUserDetailsService;
	
	/*@Autowired
	private MerchantUserDetailsService myMerchantUserDetailsService;*/
	
	@Autowired
	private WebUserDao webUserDao;
	
	@Autowired
	private MerchantWebDao merchantWebDao;
	
	@Autowired
	private AgentWebDao agentWebDao;

	@Autowired
	private PasswordEncoder encoder;

	

	/*public UserDetailsService getMyUserDetailsService() {
		return myUserDetailsService;
	}



	public void setMyUserDetailsService(UserDetailsService myUserDetailsService) {
		this.myUserDetailsService = myUserDetailsService;
	}
*/


	
	public Authentication authenticate(Authentication authentication) throws AuthenticationException 
	{
	//logger.info(" LimitLoginAuthenticationProvider Data " );
	 Authentication userdetails = null;
		String username = authentication.getName();
        String password = (String) authentication.getCredentials();
     //  logger.info(" LimitLoginAuthenticationProvider Data1 " +username);
        //logger.info(" LimitLoginAuthenticationProvider Data " +password);
      //UserDetails user = (BankUser) myUserDetailsService.loadUserByUsername(username);
        
        
        
        
       Merchant merchant = (Merchant) merchantWebDao.findByUserName(username); 
     
        //MobiLiteMerchant merchant = (MobiLiteMerchant) merchantWebDao.findByMobiliteUserName(username); 
        
        if( merchant != null)
        {
        	System.out.println("password :"+merchant.getPassword());
        	System.out.println("username  :"+merchant.getUsername());
        	Boolean checkBoolean = encoder.matches(password, merchant.getPassword());
        	if(checkBoolean)
        	{
        		Collection<? extends GrantedAuthority> authorities = merchant.getAuthorities();
        		
        	  //  logger.info("authorities...."+authorities);
        		return new UsernamePasswordAuthenticationToken(merchant, password, authorities);
        	}
        	else
        	{
        		 return userdetails;
        	 }
        	
        }
        
        //mobilite merchant
        
        MobiLiteMerchant mobilitemerchant = (MobiLiteMerchant) merchantWebDao.findByMobiliteUserName(username); 
        
        if( mobilitemerchant != null)
        {
        	
        	Boolean checkBoolean = encoder.matches(password, mobilitemerchant.getPassword());
        	if(checkBoolean)
        	{
        		Collection<? extends GrantedAuthority> authorities = mobilitemerchant.getAuthorities();
        		
        	  //  logger.info("authorities...."+authorities);
        		return new UsernamePasswordAuthenticationToken(mobilitemerchant, password, authorities);
        	}
        	else
        	{
        		 return userdetails;
        	 }
        	
        }
        
        //settlementUser
		/*
		 * SettlementUser settlementUser = (SettlementUser)
		 * merchantWebDao.findBySettlementUserName(username);
		 * 
		 * if( settlementUser != null) {
		 * 
		 * Boolean checkBoolean = encoder.matches(password,
		 * settlementUser.getPassword()); if(checkBoolean) { Collection<? extends
		 * GrantedAuthority> authorities = settlementUser.getAuthorities();
		 * 
		 * // logger.info("authorities...."+authorities); return new
		 * UsernamePasswordAuthenticationToken(settlementUser, password, authorities); }
		 * else { return userdetails; }
		 * 
		 * }
		 */
        
        Agent agent = (Agent) agentWebDao.findByAgentName(username);    
       
        if( agent != null)
        {
        	 logger.info("Auth  username : "+ username);
             logger.info("agent username : "+ agent.getUsername() );
        	logger.info("inside agent  "+username+"  "+password+"  "+agent.getPassword() );
	    	Boolean checkBoolean = encoder.matches(password, agent.getPassword());
	    	if(checkBoolean){
	    		logger.info(" inside agent check boolean");
	    		Collection<? extends GrantedAuthority> authorities = agent.getAuthorities();
	    		return new UsernamePasswordAuthenticationToken(agent, password, authorities);
	    	}else{
	    		 return userdetails;
	    	}
        }
        
        
       /* User user1 =  (User) myUserDetailsService.loadUserByUsername(username);
        if (user1 == null) {
            throw new BadCredentialsException("Username not found.");
        }else if(user1.getUserRole().equals(MerchantUserRole.BANK_MERCHANT)){
        	Merchant user = (Merchant) myUserDetailsService.loadUserByUsername(username);        	
        	Boolean checkBoolean = encoder.matches(password, user.getPassword());
        	if(checkBoolean){
        		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        		return new UsernamePasswordAuthenticationToken(user, password, authorities);
        	}else{
        		 return userdetails;
        	 }
        }else if(user1.getUserRole().equals(AgentUserRole.AGENT_USER)){ 
        	
        	Agent user = (Agent) myUserDetailsService.loadUserByUsername(username);        	
        	Boolean checkBoolean = encoder.matches(password, user.getPassword());
        	if(checkBoolean){
        		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        		return new UsernamePasswordAuthenticationToken(user, password, authorities);
        	}else{
        		 return userdetails;
        	 }
	    }else{*/
        
        BankUser user = (BankUser) webUserDao.findByUserName(username);
        
        if( user != null){
	    	
	    	BankUser user1 =  (BankUser) myUserDetailsService.loadUserByUsername(username);
	    	
	        //BankUser user = (BankUser) myUserDetailsService.loadUserByUsername(username);
	        //Authentication userdetails = null;
	        boolean status = true;
	       
	        Boolean checkBoolean = encoder.matches(password, user.getPassword());
			//logger.info("Searching db for bankUser using " + username + " : "+ user);
			if (checkBoolean) {
				//logger.info("password  matches");
				if (!CommonStatus.ACTIVE.equals(user.getStatus())) { // Status is Not Active
					logger.info("....status not in active.....");
					Date supDate =user.getSuspendDate();
					Date now = new Date();
					String sd = new SimpleDateFormat("dd-MMM-y").format(supDate);
					String rd = new SimpleDateFormat("dd-MMM-y").format(now);
					if(!sd.equals(rd)){ //For Locked User checking the date
					//	logger.info("....ACTIVE For Locked User checking the date IF.....");
						user.setActivateDate(now); // Updating activate date as now 
						user.setSuspendDate(null); // Updating suspended date 
						user.setStatus(CommonStatus.ACTIVE);// Updating Status as Active
						user.setFailedLoginAttempt(0);
						//userDao.saveOrUpdateEntity(user);
						//((MyUserDetailsService) myUserDetailsService).updateUser(user);
						webUserDao.saveOrUpdateEntity(user);
						//return (Authentication) user1;
						status = true;
					}else{// Locked user trying in same date will not allow
					//	logger.info("....ACTIVE For Locked User checking the date ELSE.....");
						//return null;
						status = false;
					}
				}else{
					//logger.info("....INACTIVE For Locked User checking the date.....");
					Date actDate = user.getActivateDate();
					DateTime fromDate = new DateTime(actDate);
					DateTime toDate = new DateTime(new Date());
					//logger.info("....INACTIVE For Locked User checking the date....."+fromDate +" : "+ toDate);
					//logger.info("....INACTIVE For Locked User checking the date....."+Months.monthsBetween(fromDate,toDate).getMonths());
					if(Months.monthsBetween(fromDate,toDate).getMonths() > 3){ //Checking for password expiry
						//logger.info("....INACTIVE For Locked User checking the date IF.....");
						user.setActivateDate(new Date()); // updating activated date
						//userDao.saveOrUpdateEntity(user);
						user.setEnabled(false); //asked to reset password
						//((MyUserDetailsService) myUserDetailsService).updateUser(user);
						webUserDao.saveOrUpdateEntity(user);
						//return (Authentication) user1; // If password changed before 90 days (expired) 
						status = false;
					}else{
						//logger.info("....INACTIVE For Locked User checking the date ELSE.....");
						//return (Authentication) user1;
						user.setEnabled(true);
						user.setFailedLoginAttempt(0);
						user.setSuspendDate(null);
						webUserDao.saveOrUpdateEntity(user);
						
						
					}
				}
			}else{
				status = false;
				logger.info("....password not match ELSE.....");
				int attempt = user.getFailedLoginAttempt();// Getting failed attempt
				Date supDate = user.getSuspendDate(); // Getting suspended date
				if(supDate != null){
					String sd = new SimpleDateFormat("dd-MMM-y").format(supDate);
					String rd = new SimpleDateFormat("dd-MMM-y").format(new Date());
					if(sd.equals(rd)){
						//logger.info("....password erterttr not match ELSE.....");
						if(attempt < 5 ){
						//	logger.info("....password not match ELSE sffdfsff.....");
							attempt = attempt +1;
							user.setFailedLoginAttempt(attempt);
							logger.info("login attempt: "+attempt);
							//userDao.saveOrUpdateEntity(user);
							//((MyUserDetailsService) myUserDetailsService).updateUser(user);
							webUserDao.saveOrUpdateEntity(user);
						}else{
							//logger.info("....password not match ELSE trtdvgdgfdgfddgfdgdfgf.....");
							user.setStatus(CommonStatus.SUSPENDED); // Attempt > 4 to lock the user
							user.setSuspendDate(new Date()); // updating supdended date in DB and suspending the user
							//userDao.saveOrUpdateEntity(user);
							//((MyUserDetailsService) myUserDetailsService).updateUser(user);
							webUserDao.saveOrUpdateEntity(user);
						}
					}
				}else{
					//logger.info("....password not match ELSE ELSE.....");
					user.setFailedLoginAttempt(1);
					user.setSuspendDate(new Date());
					//userDao.saveOrUpdateEntity(user);
					//((MyUserDetailsService) myUserDetailsService).updateUser(user);
					webUserDao.saveOrUpdateEntity(user);
				}
				//return null;
			}
			//return null;
		//}
	        
	        
	        
	        if(status){
	        
		       // System.out.println(" LimitLoginAuthenticationProvider Data2 " +user.getPassword());
		        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		 
		       // System.out.println(" LimitLoginAuthenticationProvider Data3 " +user.getPassword());
		        //return new UsernamePasswordAuthenticationToken(user, password, authorities);
		        userdetails =  new UsernamePasswordAuthenticationToken(user, password, authorities);
	        //return (Authentication) user;
	        }
	        
	        return userdetails;
	    }
        
        return userdetails;
	}
	///}
}
