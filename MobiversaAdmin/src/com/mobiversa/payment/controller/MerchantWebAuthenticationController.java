
package com.mobiversa.payment.controller;

import java.net.ConnectException;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.KeyManager;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.dto.LoginResponse;
import com.mobiversa.payment.exception.MobiException;
import com.mobiversa.payment.exception.MobiSuccess;
import com.mobiversa.payment.exception.Status;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.util.CaptchaGenerate;

@Controller
@RequestMapping("/webAuthentication")
public class MerchantWebAuthenticationController {
	@Autowired
	private MerchantService merchantService;
	
	private static final PasswordEncoder encoder = new BCryptPasswordEncoder();
	

	private static final Logger logger = Logger.getLogger(MerchantWebAuthenticationController.class.getName());

	
	@RequestMapping(value = { "/", "/*", "", "/**/*" }, method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	String defEndpoint() {
		return "$";
	}
	
	@RequestMapping(value = "/captchaRequest", method = RequestMethod.GET ,  produces = { "application/json" })
	public @ResponseBody LoginResponse getCaptcha(HttpServletRequest request) {
		logger.info("Captcha Request....");
		LoginResponse response = new LoginResponse();
		String oldCaptcha = null;
		String captcha = null;
		try {
			oldCaptcha = request.getHeader("oldCaptcha");
			if (oldCaptcha != null && !oldCaptcha.isEmpty()) {
				boolean status = merchantService.deleteCaptcha(oldCaptcha);
				/*if (status)
					logger.info("Old Captcha deleted");*/
			}

			captcha = CaptchaGenerate.getRandomCaptcha();
			KeyManager captchaGenerated = merchantService.saveGeneratedCaptcha(captcha);

			if (captchaGenerated != null) {
				// logger.info("captcha saved in db KeyManager : " + captchaGenerated.getTid());
				throw new MobiSuccess(Status.SUCCESS);
			} else {
				throw new MobiException(Status.CONNECTION_TIME_OUT);
			}
			
		} catch (MobiException e) {
			response.setResponseMessage("FAILURE");
			response.setResponseCode("0001");
			response.setResponseDescription(e.getMessage());
			return response;
		}catch(MobiSuccess e1) {
			response.setResponseMessage("SUCCESS");
			response.setResponseCode("0000");
			response.setResponseDescription("Successfully Captcha Generated");
			response.setCaptcha(captcha);
			return response;
		}
		
		
	}

	@RequestMapping(value = "/loginapp", method = RequestMethod.GET , produces = { "application/json" })
	public @ResponseBody String ValidateAuthentication(HttpServletRequest request,HttpSession session) throws MobiException{
		logger.info("login: validate credentials..");
		
		LoginResponse response = new LoginResponse();
		String captcha = null,password = null,username = null,sessionID = null;
		try {
			captcha = request.getHeader("captcha");
			password = request.getHeader("password");
			username = request.getHeader("username");
			sessionID = request.getHeader("sessionID");
		} catch (Exception e) {
			logger.info("Exception : "+e.getMessage());
		}
		logger.info("validating data login"+captcha+" "+password+" "+username);

		try {
			
		response.setSessionID(sessionID);
		
		if ((captcha != null && password != null) && (!captcha.isEmpty() && !password.isEmpty())
				&& (username != null && !username.isEmpty())){
			
			Merchant merchant = merchantService.loadMerchant(username);
		
			
			if (merchant != null) {
				boolean isVerified = encoder.matches(password, merchant.getPassword());
				//logger.info("db password: "+merchant.getPassword()+" raw password: "+password);
				//logger.info("validating password: "+isVerified);
				if(!isVerified) {
					throw new MobiException(Status.INVALID_PASSWORD);
					 
				}
				KeyManager keyManager = merchantService.validateCaptcha(captcha);
				if (keyManager != null) {
					if (keyManager.getTid().equals(captcha)) {
						AuditTrail auditTrail =	merchantService.updateAuditTrailByMerchant(username, merchant.getBusinessName(),"loginByEzyRec" );
						
						response.setResponseCode("0000");
						response.setResponseMessage("SUCCESS");
						response.setResponseDescription("Valid Credentials");
						response.setBusinessName(merchant.getBusinessName());
						response.setUserName(username);
						response.setEmail(merchant.getEmail());
						response.setRegistrationNo(merchant.getBusinessRegistrationNumber());
						response.setWebsite(merchant.getWebsite());
						response.setMerchantAddress(merchant.getBusinessAddress1());
						response.setMerchantID(merchant.getId().toString());
						response.setExpiryDate(merchant.getTerminateDate());
						response.setPhoneNumber(merchant.getBusinessContactNumber());
						response.setOwnerContactNumber(merchant.getOwnerContactNo());
						logger.info(new Gson().toJson(response));
						
						return new Gson().toJson(response);
						
					}else {
						
						//response.setResponseDescription("Captcha not matching");
						throw new MobiException(Status.INVALID_CAPTCHA);
					}
				} else {
					throw new MobiException(Status.INVALID_CAPTCHA);
				}

			}else{
				throw new MobiException(Status.INVALID_USERNAME);
			}

		} else {
			throw new MobiException(Status.EMPTY_DATA);
		}
		}catch(MobiException e) {
			response.setResponseCode("0001");
			response.setResponseMessage("FAILURE");
			response.setResponseDescription(e.getMessage());
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			logger.info(new Gson().toJson(response));
			return new Gson().toJson(response);
			
			//response.setResponseDescription("Captcha not matching");
		}
		
	}
	
	
	@RequestMapping(value = "/logoutapp", method = RequestMethod.GET , produces = { "application/json" })
	public @ResponseBody String logout(HttpServletRequest request,HttpSession session) throws MobiException{
		logger.info("logout: validate credentials..");
		
		LoginResponse response = new LoginResponse();
		String businessName=null,username = null,sessionID = null;
		try {
			businessName = request.getHeader("businessName");
			username = request.getHeader("username");
			sessionID = request.getHeader("sessionID");
		} catch (Exception e) {
			logger.info("Exception : "+e.getMessage());
		}
		logger.info("validating data for logout"+sessionID+" "+businessName+" "+username);

		try {
			
		response.setSessionID(sessionID);
		
		if ((sessionID != null && businessName != null) && (!sessionID.isEmpty() && !businessName.isEmpty())
				&& (username != null && !username.isEmpty())){
			
			Merchant merchant = merchantService.loadMerchant(username);
			
			if (merchant != null) {
				AuditTrail auditTrail =	merchantService.updateAuditTrailByMerchant(username, businessName,"logoutByEzyRec" );
				response.setResponseCode("0000");
				response.setResponseMessage("SUCCESS");
				response.setResponseDescription("Logout Success");
				response.setUserName(username);
				response.setBusinessName(businessName);
				response.setMerchantID(merchant.getId().toString());
				logger.info(new Gson().toJson(response));
				return new Gson().toJson(response);
		} 
		}else {
			throw new MobiException(Status.EMPTY_DATA);
		}
		}catch(MobiException e) {
			response.setResponseCode("0001");
			response.setResponseMessage("FAILURE");
			response.setResponseDescription(e.getMessage());
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			logger.info(new Gson().toJson(response));
			return new Gson().toJson(response);
			
			//response.setResponseDescription("Captcha not matching");
		}
		return new Gson().toJson(response);
		
		
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET , produces = { "application/json" })
	public @ResponseBody String forgotPassword(HttpServletRequest request,HttpSession session) throws MobiException, ConnectException{
		logger.info("forgotPassword: validate credentials..");
		
		LoginResponse response = new LoginResponse();
		String businessName=null,username = null,sessionID = null;
		try {
			//businessName = request.getHeader("businessName");
			username = request.getHeader("username");
			sessionID = request.getHeader("sessionID");
		} catch (Exception e) {
			logger.info("Exception : "+e.getMessage());
		}
		logger.info("validating data for forgot password "+sessionID+" "+username);

		try {
			
		response.setSessionID(sessionID);
		
		if ((sessionID != null) && (!sessionID.isEmpty())
				&& (username != null && !username.isEmpty())){
			
			Merchant merchant = merchantService.loadMerchant(username);
			
			if (merchant != null) {
				String pwd = null;
				try {
					pwd = merchantService.changeMerchantPassWordByAdmin(merchant);
				} catch (JMSException e) {
				
					logger.info("Exception : "+e.getMessage());
				}
				AuditTrail auditTrail =	merchantService.updateAuditTrailByMerchant(username, businessName,"forgotPassword" );
				response.setResponseCode("0000");
				response.setResponseMessage("SUCCESS");
				response.setResponseDescription("Password Successfully Changed and Mail has sent to Merchant Email.");
				response.setUserName(username);
				response.setBusinessName(businessName);
				response.setMerchantID(merchant.getId().toString());
				logger.info(new Gson().toJson(response));
				return new Gson().toJson(response);
		} 
		}else {
			throw new MobiException(Status.EMPTY_DATA);
		}
		}catch(MobiException e) {
			response.setResponseCode("0001");
			response.setResponseMessage("FAILURE");
			response.setResponseDescription(e.getMessage());
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			logger.info(new Gson().toJson(response));
			return new Gson().toJson(response);
			
			//response.setResponseDescription("Captcha not matching");
		}
		return new Gson().toJson(response);
		
		
	}

	
	/*@RequestMapping(value = "/regenerateCaptcha", method = RequestMethod.GET, consumes = "application/json")
	public @ResponseBody LoginResponse regetCaptcha(final HttpSession session, final HttpServletRequest request) {
		//System.out.println("generateing captcha");
		String oldCaptcha = request.getParameter("txtCompare1");
		//logger.info("old captcha: " + oldCaptcha);

		char[] alphanum = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
				'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
				'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', '0' };

		String a = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";
		String b = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";
		String c = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";
		String d = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";
		String e = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";
		String f = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";
		String g = alphanum[(int) Math.floor(Math.random() * alphanum.length)] + "";

		String captchaCode = a + ' ' + b + ' ' + ' ' + c + ' ' + d + ' ' + e + ' ' + f + ' ' + g;

		String newCaptcha = captchaCode.trim().replaceAll("\\s+", "").toString();
		//logger.info("regenerated captcha by server: " + newCaptcha + " " + captchaCode);

		LoginResponse response = new LoginResponse("Done", captchaCode);

		boolean status = merchantService.deleteCaptcha(oldCaptcha);
		//logger.info("deleted result: " + status);
		if (status)
			logger.info("Captchan regenerated");

		KeyManager captchaGenerated = merchantService.saveGeneratedCaptcha(newCaptcha);

		if (captchaGenerated != null) {
			//logger.info("recaptcha saved in db KeyManager : " + captchaGenerated.getTid());
		}
		return response;
	}

	@RequestMapping(value = "/deleteValidatedCaptcha", method = RequestMethod.GET, consumes = "application/json")
	public @ResponseBody LoginResponse deleteValidatedCaptcha(final HttpSession session, final HttpServletRequest request) {
		// ,@PathVariable String txtCompare1,@PathVariable String txtCompare2

		String captcha1 = request.getParameter("txtCompare1");
		String date = request.getParameter("txtCompare2");

		//logger.info("validating captcha to delete after submitting form" + captcha1);
		LoginResponse response1 = null;

		if ((captcha1 != null && !captcha1.isEmpty()))

		{

			KeyManager keyManager = merchantService.validateCaptcha(captcha1);
			if (keyManager != null) {
				if (keyManager.getTid().equals(captcha1)) {

					boolean deletedCaptcha = merchantService.deleteCaptcha(captcha1);

					if (deletedCaptcha) {
						// logger.info("captcha successfully deleted");
						response1 = new LoginResponse("Success", "Captcha Validated and Deleted from DB");

					} else {
						response1 = new LoginResponse("errorMsg", "Not Deleted from DB");
					}
				}
			}

		}
		return response1;

	}

	@RequestMapping(value = "/validateCaptcha", method = RequestMethod.GET, consumes = "application/json")
	public @ResponseBody LoginResponse validateCaptcha(final HttpSession session, final HttpServletRequest request) {
		// ,@PathVariable String txtCompare1,@PathVariable String txtCompare2

		LoginResponse response1 = null;
		//System.out.println("generateing captcha");

		String captcha1 = request.getParameter("txtCompare1");
		String captcha2 = request.getParameter("txtCompare2");
		String username = request.getParameter("username");

		// logger.info("validating captcha"+captcha1+" "+captcha2+" "+username);

		if ((captcha1 != null && captcha2 != null) && (!captcha1.isEmpty() && !captcha1.isEmpty())
				&& (username != null && !username.isEmpty()))

		{
			//logger.info("validate captcha and merchant: ");
			Merchant merchant = merchantService.loadMerchant(username);

			if (merchant != null) {
				//logger.info("checking merchant credentials");
				KeyManager keyManager = merchantService.validateCaptcha(captcha1);
				if (keyManager != null) {
					if (keyManager.getTid().equals(captcha2)) {
						response1 = new LoginResponse("Success", "Captcha Validated");
						//logger.info("Captcha  matching");

					}

					else {
						request.setAttribute("errorMsg", "Wrong Captcha");
						response1 = new LoginResponse("errorMsg", "Captcha not matching");
						//logger.info("Captcha not matching");

					}
				} else {
					request.setAttribute("errorMsg", "Wrong Captcha");
					response1 = new LoginResponse("errorMsg", "Captcha not matching");
					//logger.info("Captcha not matching");
					// return "redirect: /auth/login/error";

				}

			}

			// agent user

			Agent agent = (Agent) agentWebDao.findByAgentName(username);

			if (agent != null) {
				//logger.info("checking agent credentials");
				KeyManager keyManager = merchantService.validateCaptcha(captcha1);
				if (keyManager != null) {
					if (keyManager.getTid().equals(captcha2)) {
						response1 = new LoginResponse("Success", "Captcha Validated");
						//logger.info("Captcha  matching");

					}

					else {
						request.setAttribute("errorMsg", "Wrong Captcha");
						response1 = new LoginResponse("errorMsg", "Captcha not matching");
						//logger.info("Captcha not matching");

					}
				} else {
					request.setAttribute("errorMsg", "Wrong Captcha");
					response1 = new LoginResponse("errorMsg", "Captcha not matching");
					//logger.info("Captcha not matching");
					// return "redirect: /auth/login/error";

				}

			}
			// bank
			BankUser user = (BankUser) webUserDao.findByUserName(username);

			if (user != null) {
				//logger.info("checking admin credentials");
				KeyManager keyManager = merchantService.validateCaptcha(captcha1);
				if (keyManager != null) {
					if (keyManager.getTid().equals(captcha2)) {
						response1 = new LoginResponse("Success", "Captcha Validated");

						//logger.info("Captcha  matching");
					}

					else {
						request.setAttribute("errorMsg", "Wrong Captcha");
						response1 = new LoginResponse("errorMsg", "Captcha not matching");
						//logger.info("Captcha not matching");

					}
				} else {
					request.setAttribute("errorMsg", "Wrong Captcha");
					response1 = new LoginResponse("errorMsg", "Captcha not matching");
					//logger.info("Captcha not matching");
					// return "redirect: /auth/login/error";

				}

			}
			if (user == null && agent == null && merchant == null) {
				//logger.info("Wronng Credentials");
				request.setAttribute("errorMsg", "Wrong Credentials");
				response1 = new LoginResponse("errorMsg", "Wronng Credentials");
				//logger.info("Wronng Credentials");
			}

		} else {

			response1 = new LoginResponse("Fail", "Empty Fields.. Please Fill it..!");
		//	System.out.println("Empty Fields.. Please Fill it..!");
			// return "redirect: /auth/login/error";

		}
		return response1;

	}
*/
	
	
	
	
	
	
	
	
	
}