package com.mobiversa.payment.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/webApp")
public class MobileEndpointController {
	private static Logger logger = Logger.getLogger(MobileEndpointController.class);
	

	@RequestMapping(value = { "/", "/*", "", "/**/*" }, method = { RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody
	String defEndpoint() {
		return "$";
	}

	/**
	 * logout the mobile user session
	 * <p>
	 * Work Flow
	 * </P>
	 * <ol>
	 * <li>removing all sessions</li>
	 * </ol>
	 * 
	 * @Return It is a void function
	 */
	@RequestMapping(value = "/logoutSession", method = RequestMethod.POST)
	public @ResponseBody String logOutsession(final HttpSession session,final HttpServletRequest request) {
		
		System.out.println(" testing logoutsession:.................");
		return "testing";
		
	}

	@SuppressWarnings({ "null", "deprecation" })
	@RequestMapping(value = "/mobi_jsonservice", method = RequestMethod.POST, produces = { "application/json" })
	public @ResponseBody
	String mobileMessageJsonDoprocess(final HttpServletRequest request, final HttpServletResponse response)  {
		
		
		System.out.println("testing mobi_json servicce.................");
		return "testing";
		
	}
	
	
	
}

