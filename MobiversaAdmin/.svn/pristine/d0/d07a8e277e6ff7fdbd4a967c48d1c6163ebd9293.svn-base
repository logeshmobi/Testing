package com.mobiversa.payment.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.service.AgentService;

@SuppressWarnings("unused")
@Controller
//@RequestMapping(value = AgentController.URL_BASE)
public class AgentProfileController extends BaseController {
	//public static final String URL_BASE = "/merchantProfile";

	/*@Autowired
	private MobileUserService mobileUserService;*/
	@Autowired
	private AgentService agentService;
	static final Logger logger = Logger.getLogger(AgentProfileController.class.getName());
	
//@RequestMapping(value = { "", "/", "/**" }, method = RequestMethod.GET)
public String defaultPage() {

	return "redirect:/agent/agentweb";
}

@RequestMapping(value = { "/agentProfportal/agentPassDetails" }, method = RequestMethod.GET)
public String agentDetails(final Model model, final Principal principal) {
	PageBean pageBean = new PageBean("agent Details", "home/agentProfile",
			Module.AGENT, "agentweb/sideMenuMerchantWeb");
	Agent agent = agentService.loadAgent(principal.getName());
	model.addAttribute("pageBean", pageBean);
	model.addAttribute("agent", agent);
	return TEMPLATE_AGENT;
}
@RequestMapping(value = { "/agentProfportal/agentchangePassWord" }, method = RequestMethod.GET)
public String changePassword(final Model model, final Principal principal,HttpServletRequest request) {
	logger.info("Login Person change password");
	PageBean pageBean = new PageBean("agentProfile", "home/agentchangePassword",
			Module.AGENT, "agentweb/sideMenuAgentWeb");
	model.addAttribute("pageBean", pageBean);
	HttpSession session = request.getSession();
    session.setAttribute("agentUserName",  principal.getName());
	return TEMPLATE_AGENT;
}
@RequestMapping(value = { "/agent/dashBoard" }, method = RequestMethod.GET)
public String agentWebDashBoard(final Model model,final java.security.Principal principal,HttpServletRequest request) {
	logger.info("Login Person in dash Board:"+principal.getName());
	PageBean pageBean = new PageBean("Dash Board", "home/dashBoard",
			Module.AGENT, "agentweb/sideMenuAgentWeb");
	model.addAttribute("pageBean", pageBean);
	 HttpSession session = request.getSession();
	 Agent agent = agentService.loadAgent(principal.getName());
	    session.setAttribute("agentUserName",  agent.getFirstName());
	return TEMPLATE_AGENT;
}
@RequestMapping(value = { "/agentProfdetails/confirmPasswordbyagent" }, method = RequestMethod.POST)
public String confirmPassword(final Model model, final Principal principal,HttpServletRequest request) {
	logger.info("Old PassWord"+request.getParameter("password")+"New Password"+request.getParameter("newPassword"));
	int count=agentService.changeAgentPassWord(principal.getName(), request.getParameter("newPassword"), request.getParameter("password"));
	PageBean pageBean=null;
	if(count==1){
		
	 pageBean = new PageBean("Agent Profile", "home/agentChangePasswordSuccess",
			Module.AGENT, "agentweb/sideMenuAgentWeb");
	}else{
		
		 pageBean = new PageBean("Agent Profile", "home/agentChangePasswordFailure",
					Module.AGENT, "agentweb/sideMenuAgentWeb");
	}
	model.addAttribute("pageBean", pageBean);
	return TEMPLATE_AGENT;
}
}