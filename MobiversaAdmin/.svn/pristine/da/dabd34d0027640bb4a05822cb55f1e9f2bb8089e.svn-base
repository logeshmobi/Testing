package com.mobiversa.payment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.BankUser;
import com.mobiversa.common.bo.KeyManager;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiLiteMerchant;
import com.mobiversa.payment.dao.AgentWebDao;
import com.mobiversa.payment.dao.WebUserDao;
import com.mobiversa.payment.dto.Response;
import com.mobiversa.payment.service.MerchantService;

@Controller
@RequestMapping("/generate")
public class GenerateCaptcha {
	@Autowired
	private MerchantService merchantService;

	@Autowired
	private WebUserDao webUserDao;

	@Autowired
	private AgentWebDao agentWebDao;

	private static final Logger logger = Logger.getLogger(GenerateCaptcha.class.getName());

	@RequestMapping(value = "/generateCaptcha", method = RequestMethod.GET)
	public @ResponseBody Response getCaptcha() {
		//System.out.println("generateing captcha");

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

		String captcha = captchaCode.trim().replaceAll("\\s+", "").toString();
		// logger.info("generated captcha by server: "+captcha +" login captcha
		// "+captchaCode);

		Response response = new Response("Success", captchaCode);

		KeyManager captchaGenerated = merchantService.saveGeneratedCaptcha(captcha);

		if (captchaGenerated != null) {
			//logger.info("captcha saved in db KeyManager : " + captchaGenerated.getTid());
		}
		return response;
	}

	@RequestMapping(value = "/regenerateCaptcha", method = RequestMethod.GET, consumes = "application/json")
	public @ResponseBody Response regetCaptcha(final HttpSession session, final HttpServletRequest request) {
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

		Response response = new Response("Done", captchaCode);

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
	public @ResponseBody Response deleteValidatedCaptcha(final HttpSession session, final HttpServletRequest request) {
		// ,@PathVariable String txtCompare1,@PathVariable String txtCompare2

		String captcha1 = request.getParameter("txtCompare1");
		String date = request.getParameter("txtCompare2");

		//logger.info("validating captcha to delete after submitting form" + captcha1);
		Response response1 = null;

		if ((captcha1 != null && !captcha1.isEmpty()))

		{

			KeyManager keyManager = merchantService.validateCaptcha(captcha1);
			if (keyManager != null) {
				if (keyManager.getTid().equals(captcha1)) {

					boolean deletedCaptcha = merchantService.deleteCaptcha(captcha1);

					if (deletedCaptcha) {
						// logger.info("captcha successfully deleted");
						response1 = new Response("Success", "Captcha Validated and Deleted from DB");

					} else {
						response1 = new Response("errorMsg", "Not Deleted from DB");
					}
				}
			}

		}
		return response1;

	}

	@RequestMapping(value = "/validateCaptcha", method = RequestMethod.GET, consumes = "application/json")
	public @ResponseBody Response validateCaptcha(final HttpSession session, final HttpServletRequest request) {
		// ,@PathVariable String txtCompare1,@PathVariable String txtCompare2

		Response response1 = null;
		//System.out.println("generateing captcha");

		String captcha1 = request.getParameter("txtCompare1");
		String captcha2 = request.getParameter("txtCompare2");
		String username = request.getParameter("username");
		
		session.setAttribute("txtCompare1",  captcha1);
		session.setAttribute("txtCompare2",  captcha2);

		logger.info("validating captcha  "+captcha1+" "+captcha2+" "+username);
try {
		if ((captcha1 != null && captcha2 != null) && (!captcha1.isEmpty() && !captcha2.isEmpty())
				&& (username != null && !username.isEmpty()))

		{
			
			MobiLiteMerchant mobilitemerchant =null;
//mobilite merchant
			try {
			 mobilitemerchant = merchantService.loadMobiLiteMerchant(username);
			logger.info("checking mobilitemerchant credentials");
			if (mobilitemerchant != null) {
				logger.info("mobilitemerchant:"+mobilitemerchant.getRole());
				KeyManager keyManager = merchantService.validateCaptcha(captcha1);
				if (keyManager != null) {
					if (keyManager.getTid().equals(captcha2)) {
						response1 = new Response("Success", "Captcha Validated");
						//logger.info("Captcha  matching");

					}

					else {
						request.setAttribute("errorMsg", "Wrong Captcha");
						response1 = new Response("errorMsg", "Captcha not matching");
						//logger.info("Captcha not matching");

					}
				} else {
					request.setAttribute("errorMsg", "Wrong Captcha");
					response1 = new Response("errorMsg", "Captcha not matching");
					//logger.info("Captcha not matching");
					// return "redirect: /auth/login/error";

				}

			}
			}
			catch(Exception e) {
			e.printStackTrace();
			logger.info("error inside capcha  ---"+e.getMessage());
				
			}
			
			
			//logger.info("validate captcha and merchant: ");
			Merchant merchant = merchantService.loadMerchant(username);
			logger.info("checking merchant credentials");

			if (merchant != null) {
				logger.info("merchant:"+merchant.getUsername());
				//logger.info("checking merchant credentials");
				KeyManager keyManager = merchantService.validateCaptcha(captcha1);
				if (keyManager != null) {
					if (keyManager.getTid().equals(captcha2)) {
						response1 = new Response("Success", "Captcha Validated");
						//logger.info("Captcha  matching");

					}

					else {
						request.setAttribute("errorMsg", "Wrong Captcha");
						response1 = new Response("errorMsg", "Captcha not matching");
						//logger.info("Captcha not matching");

					}
				} else {
					request.setAttribute("errorMsg", "Wrong Captcha");
					response1 = new Response("errorMsg", "Captcha not matching");
					//logger.info("Captcha not matching");
					// return "redirect: /auth/login/error";

				}

			}

			
			
			/*
			 * SettlementUser settlementUser = merchantService.loadSettlementUser(username);
			 * logger.info("checking settlementUser credentials");
			 * 
			 * if (settlementUser != null) {
			 * logger.info("settlementUser:"+settlementUser.getUsername());
			 * 
			 * logger.info("settlementUser email:"+settlementUser.getEmail());
			 * //logger.info("checking merchant credentials"); KeyManager keyManager =
			 * merchantService.validateCaptcha(captcha1); if (keyManager != null) { if
			 * (keyManager.getTid().equals(captcha2)) { response1 = new Response("Success",
			 * "Captcha Validated"); //logger.info("Captcha  matching");
			 * 
			 * }
			 * 
			 * else { request.setAttribute("errorMsg", "Wrong Captcha"); response1 = new
			 * Response("errorMsg", "Captcha not matching");
			 * //logger.info("Captcha not matching");
			 * 
			 * } } else { request.setAttribute("errorMsg", "Wrong Captcha"); response1 = new
			 * Response("errorMsg", "Captcha not matching");
			 * //logger.info("Captcha not matching"); // return
			 * "redirect: /auth/login/error";
			 * 
			 * }
			 * 
			 * }
			 */
			
			
			// agent user

			Agent agent = (Agent) agentWebDao.findByAgentName(username);

			if (agent != null) {
				//logger.info("checking agent credentials");
				KeyManager keyManager = merchantService.validateCaptcha(captcha1);
				if (keyManager != null) {
					if (keyManager.getTid().equals(captcha2)) {
						response1 = new Response("Success", "Captcha Validated");
						//logger.info("Captcha  matching");

					}

					else {
						request.setAttribute("errorMsg", "Wrong Captcha");
						response1 = new Response("errorMsg", "Captcha not matching");
						//logger.info("Captcha not matching");

					}
				} else {
					request.setAttribute("errorMsg", "Wrong Captcha");
					response1 = new Response("errorMsg", "Captcha not matching");
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
						response1 = new Response("Success", "Captcha Validated");

						//logger.info("Captcha  matching");
					}

					else {
						request.setAttribute("errorMsg", "Wrong Captcha");
						response1 = new Response("errorMsg", "Captcha not matching");
						session.removeAttribute("txtCompare1");
						session.removeAttribute("txtCompare2");
						//logger.info("Captcha not matching");

					}
				} else {
					request.setAttribute("errorMsg", "Wrong Captcha");
					response1 = new Response("errorMsg", "Captcha not matching");
					session.removeAttribute("txtCompare1");
					session.removeAttribute("txtCompare2");
					//logger.info("Captcha not matching");
					// return "redirect: /auth/login/error";

				}

			}
			if (user == null && agent == null && merchant == null && mobilitemerchant == null) {
				//logger.info("Wronng Credentials");
				request.setAttribute("errorMsg", "Wrong Credentials");
				response1 = new Response("errorMsg", "Wronng Credentials");
				//logger.info("Wronng Credentials");
			}

		} else {

			response1 = new Response("Fail", "Empty Fields.. Please Fill it..!");
			session.removeAttribute("txtCompare1");
			session.removeAttribute("txtCompare2");
		//	System.out.println("Empty Fields.. Please Fill it..!");
			// return "redirect: /auth/login/error";

		}
		
	}
	catch(Exception ee) {
	ee.printStackTrace();
	logger.info("error outside capcha  ---"+ee.getMessage());
		
	}
		
		return response1;

	}

}