package com.mobiversa.payment.auth;

import java.util.Arrays;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.AgentUserRole;
import com.mobiversa.common.bo.MerchantUserRole;
import com.mobiversa.payment.util.PropertyLoader;

public class AuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private final static Logger logger = Logger.getLogger(AuthSuccessHandler.class.getName());

	@Override
	protected String determineTargetUrl(final HttpServletRequest request, final HttpServletResponse response) {
		// Determine the role of logged in user
		// logger.info("Login Request Map :"+request.getRequestURI());
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Collection<? extends GrantedAuthority> authList = auth.getAuthorities();

		if (authList == null) {
			logger.info("authlist null");
			return "/auth/login/error";
		} else {
			// logger.info(" TEST auth else");

			logger.info("authList::;" + authList.toString());

			logger.info("authList size::;" + authList.size());

			if (authList.contains(new SimpleGrantedAuthority(MerchantUserRole.MOBILITE_MERCHANT.name()))) {
				logger.info(
						"#################################### Logging in as MOBILITE MERCHANT #################################### ");

				// System.out.println(" AUTH MERCHANT");
				// return "/merchant/merchantweb";

				return "/mobilitemerchant/mobilitemerchantweb";

			} else if (authList.contains(new SimpleGrantedAuthority(MerchantUserRole.BANK_MERCHANT.name()))) {
				logger.info(
						"#################################### Logging in as MERCHANT #################################### ");

				// System.out.println(" AUTH MERCHANT");
				return "/merchant/merchantweb";

				// return "/mobilitemerchant/mobilitemerchantweb";

			}

			/*
			 * else if (authList.contains(new
			 * SimpleGrantedAuthority(MerchantUserRole.SETTLEMENT_USER.name()))) { logger.
			 * info("#################################### Logging in as Settlement user #################################### "
			 * );
			 * 
			 * //System.out.println(" AUTH MERCHANT"); return
			 * "/settlementuser/settlementuserweb";
			 * 
			 * //return "/mobilitemerchant/mobilitemerchantweb";
			 * 
			 * }
			 */

			/*
			 * else if (authList.contains(new
			 * SimpleGrantedAuthority(MerchantUserRole.MOBILITE_MERCHANT.name()))) { logger.
			 * info("#################################### Logging in as MOBILITE MERCHANT #################################### "
			 * );
			 * 
			 * //System.out.println(" AUTH MERCHANT"); //return "/merchant/merchantweb";
			 * 
			 * return "/mobilitemerchant/mobilitemerchantweb";
			 * 
			 * }
			 */
			else if (authList.contains(new SimpleGrantedAuthority(MerchantUserRole.NON_MERCHANT.name()))) {
				// logger.info("----AuthSuccessHandler-----");
				logger.info(
						"#################################### Logging in as NON-MERCHANT #################################### ");
				// System.out.println(" AUTH NON-MERCHANT");
				return "/nonmerchant/nonmerchantweb";
			}

			else if (authList.contains(new SimpleGrantedAuthority(AgentUserRole.AGENT_USER.name()))) {

				Agent user = (Agent) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				logger.info(
						"#################################### Logging in as AGENT #################################### ");
				String agent = user.getUsername();
				String agentUsername = PropertyLoader.getFile().getProperty("SUPER_AGENTNAME");
				String hotelMerchantname = PropertyLoader.getFile().getProperty("HOTEL_MERCHANTNAME");
				String toypayMerchantname = PropertyLoader.getFile().getProperty("TOYPAY_MERCHANTNAME");

//					String settlementuserName = PropertyLoader.getFile().getProperty(
//							"SETTLEMENT_USERNAME");
				logger.info("agent name " + agent);
				logger.info("agent user name:" + agentUsername);
				logger.info("hotelMerchantname:" + hotelMerchantname);
				logger.info("toypayMerchantname: " + toypayMerchantname);

//					logger.info("merchantRole : "+merchantRole);
				logger.info("AG type : " + user.getAgType());
				logger.info("User get Contact " + user.getContact());

				// logger.info("SETTLEMENT_USERNAME:" + settlementuserName);

				/*
				 * String multiLogin = user.getState(); String zenroomsLogin =
				 * PropertyLoader.getFile().getProperty("HOTEL_MERCHANTNAME"); String
				 * toypayLogin = PropertyLoader.getFile().getProperty("TOYPAY_MERCHANTNAME");
				 * 
				 * if()
				 */
				/*
				 * if(agent.equals(settlementuserName)) { return
				 * "/superagent/settlementUserlogin"; }
				 */

				// DG Merchant changes
				String dgUserName = PropertyLoader.getFile().getProperty("DG_TECK_USER_NAME");
				logger.info(" Dragon tech UserName : " + dgUserName);

				String[] dgUserNames = dgUserName.split(",");
				for (int i = 0; i < dgUserNames.length; i++) {
					dgUserNames[i] = dgUserNames[i].trim();
				}
				if (Arrays.asList(dgUserNames).contains(agent)) {
					logger.info("Logged In as : " + agent + ", Displaying the DG Teck Page" + "Agent id:"
							+ String.valueOf(user.getId()));
					return "/agent/listmerchant/" + String.valueOf(user.getId()) + "/" + 1;
				}

				if ((!(user.getContact() == null)) && user.getContact().equals("SP")) {
					logger.info("setlemntportal ");
					return "/superagent/settlementUserlogin";
				}

				if ((!(user.getContact() == null)) && user.getContact().equals("PP")) {
					logger.info("setlemntportal ");
					return "/superagent/payoutUserlogin";
				}

				else if (agent.equals(agentUsername)) {
					logger.info("superagent ");
					return "/superagent/agentlogin";
				} else if (agent.equals(hotelMerchantname)) {
					logger.info("hotelMerchantname ");
					return "/superagent/hotelMerchantlogin";
				} else if (agent.equals(toypayMerchantname)) {
					logger.info("toypayMerchantname ");
					return "/superagent/toypayMerchantlogin";
				}

				// Payout Test

				else if ((!(user.getContact() == null)) && user.getContact().equals("MPT")) {
					logger.info(" ********* Logging in TEST Payout ********* ");
					return "/merchants/testPayout";
				}
//					else if(user.getAgType().equals("TEST_PAY")){
//						logger.info(" ********* Logging in TEST Payout ********* ");
//						return "/merchants/testPayout";
//					}
				else
				// System.out.println("AUTH AGENT");
				{
					logger.info("agent ");

					return "/agent/agentweb";
				}
			} else {
				// System.out.println(" AUTH BANK");
				return "/bank/user";
			}
		}

	}
}