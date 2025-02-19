package com.mobiversa.payment.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.SubAgentService;

@SuppressWarnings("unused")
@Controller
public class SuperAgentchngePwdController extends BaseController {

	@Autowired
	private AgentService agentService;

	@Autowired
	private SubAgentService subAgentService;
	static final Logger logger = Logger.getLogger(SuperAgentchngePwdController.class.getName());

	public static final String URL_BASE = "/superAgentPwd";

	public String defaultPage() {

		return "redirect:/superagent1/superAgentPwd";
	}

	// superAgent changePassword profile 18-04-2017

	@RequestMapping(value = { "/superAgentProfile/agentDetails" }, method = RequestMethod.GET)
	public String agentDetails(final Model model, final Principal principal) {
		PageBean pageBean = new PageBean("agent Details", "SuperAgent/superAgentProfile", Module.AGENT,
				"agentweb/sideMenuMerchantWeb");
		Agent agent = agentService.loadAgent(principal.getName());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("agent", agent);
		return TEMPLATE_SUPER_AGENT;
	}

	@RequestMapping(value = { "/superAgentProfile/changePassWord1" }, method = RequestMethod.GET)
	public String changePassword(final Model model, final Principal principal, HttpServletRequest request) {
		logger.info("Login Person change password");
		PageBean pageBean = new PageBean("agentProfile", "home/agentchangePassword", Module.AGENT,
				"agentweb/sideMenuAgentWeb");
		model.addAttribute("pageBean", pageBean);
		HttpSession session = request.getSession();
		session.setAttribute("agentUserName", principal.getName());
		return TEMPLATE_SUPER_AGENT;
	}

	@RequestMapping(value = { "/superagent1/dashBoard" }, method = RequestMethod.GET)
	public String agentWebDashBoard(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {
		logger.info("Login Person in dash Board:" + principal.getName());
		PageBean pageBean = new PageBean("Dash Board", "home/dashBoard", Module.AGENT, "agentweb/sideMenuAgentWeb");
		model.addAttribute("pageBean", pageBean);
		HttpSession session = request.getSession();
		Agent agent = agentService.loadAgent(principal.getName());
		session.setAttribute("agentUserName", agent.getFirstName());
		return TEMPLATE_SUPER_AGENT;
	}

	@RequestMapping(value = { "/superAgentProfile/confirmPassword" }, method = RequestMethod.POST)
	public String confirmPassword(final Model model, final Principal principal, HttpServletRequest request) {
		logger.info("Old PassWord" + request.getParameter("password") + "New Password"
				+ request.getParameter("newPassword"));
		int count = agentService.changeAgentPassWord(principal.getName(), request.getParameter("newPassword"),
				request.getParameter("password"));
		PageBean pageBean = null;
		if (count == 1) {

			pageBean = new PageBean("Agent Profile", "SuperAgent/changePasswordSuccess", Module.AGENT,
					"agentweb/sideMenuAgentWeb");
		} else {

			pageBean = new PageBean("Agent Profile", "SuperAgent/changePasswordFailure", Module.AGENT,
					"agentweb/sideMenuAgentWeb");
		}
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_SUPER_AGENT;
	}
}

// end change password profile in superAgent 18-04-2017
// }
