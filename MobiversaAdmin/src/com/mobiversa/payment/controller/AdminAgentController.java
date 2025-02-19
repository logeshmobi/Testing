package com.mobiversa.payment.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.BaseDataImpl;
import com.mobiversa.payment.service.AdminService;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.validator.AddAgentUserValidator;

/*import com.mobiversa.payment.validator.AddBankUserValidator;*/

@Controller
@RequestMapping(value = AdminAgentController.URL_BASE)
public class AdminAgentController extends BaseController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private AgentService agentService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AddAgentUserValidator validator;

	public static final String URL_BASE = "/agent1";

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("default page display");
		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayAgentList(final Model model,
			@PathVariable final int currPage,
			final java.security.Principal principal) {
		// logger.info("about to list all agents");
		PageBean pageBean = new PageBean("Agent", "agent/agentDetail",
				Module.AGENT, "agent/sideMenuAgent");

		logger.info(" Agent Summary for Admin :" + principal.getName());

		/*
		 * if(principal.getName().equals("bhuvi@mobiversa.com")) {
		 * 
		 * return TEMPLATE_SUPER_AGENT; }
		 */

		model.addAttribute("pageBean", pageBean);
		PaginationBean<Agent> paginationBean = new PaginationBean<Agent>();
		paginationBean.setCurrPage(currPage);
		agentService.listAgent(paginationBean);

		model.addAttribute("paginationBean", paginationBean);

		/*
		 * if(principal.getName().equals("justin@mobiversa.com")) {
		 * 
		 * return TEMPLATE_SUPER_AGENT; }
		 */
		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/detail/{id}" }, method = RequestMethod.GET)
	public String listAgent(final Model model, @PathVariable final long id) {
		logger.info("Request to display agent based on ID: " + id);
		PageBean pageBean = new PageBean("Merchant Detail",
				"agent/agentDetailsView", Module.AGENT,
				"merchant/sideMenuMerchant");
		Agent agent = agentService.loadAgentByPk(id);
		// logger.info("Display agent Name:" + agent.getFirstName());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("agent", agent);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/addAgent" }, method = RequestMethod.GET)
	public String displayAddAgent(@ModelAttribute("agent") Agent agent,
			Model model) {
		logger.info("about to  add agent details" + agent.getMailId());
		PageBean pageBean = new PageBean("agent Detail",
				"agent/addagent/addAgentDetails", Module.AGENT,
				"agent/sideMenuAgent");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("agent", agent);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/addAgent" }, method = RequestMethod.POST)
	public String processAddUserForm(
			@ModelAttribute("agent") final Agent agent,
			final java.security.Principal principal,
			final HttpServletRequest request, final ModelMap model,
			final BindingResult errors) {
		logger.info("About to add New Agent :" + agent.getMailId());
		Agent agent1 = agentService.loadAgentbyMailId(agent.getMailId());

		logger.info("Display agent name:" + agent.getFirstName());
		PageBean pageBean = null;
		
		
		
		BaseDataImpl  baseData = new BaseDataImpl();
		
		Agent  a =baseData.vaildated(agent);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			pageBean = new PageBean(" Agent add Details",
					"agent/addagent/addAgentDetails", Module.AGENT,
					"agent/sideMenuAgent");
			model.addAttribute("responseErrorData", "Form cleared that contains HTML tags "); // table
																		// response
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("agent", agent);

			// return "redirect:/agent1/addAgent";
			return TEMPLATE_DEFAULT;
		}
		
		
		if (agent1 != null) {
			pageBean = new PageBean(" Agent add Details",
					"agent/addagent/addAgentDetails", Module.AGENT,
					"agent/sideMenuAgent");
			model.addAttribute("responseData", "Email already exist"); // table
																		// response
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("agent", agent);

			// return "redirect:/agent1/addAgent";
			return TEMPLATE_DEFAULT;

		} else {

			pageBean = new PageBean("Agent user add Details",
					"agent/addagent/addAgentDetails", Module.AGENT,
					"agent/sideMenuAgent");
			model.addAttribute("responseData", null);

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("agent", agent);
			// PCI
			request.getSession(true).setAttribute("addAgentSession", agent);

			return "redirect:/agent1/agentDetailsReviewAndConfirm";
		}
	}

	@RequestMapping(value = { "/agentDetailsReviewAndConfirm" }, method = RequestMethod.GET)
	public String displayAddAgentConfirmation(final ModelMap model,
			final HttpServletRequest request,
			final java.security.Principal principal)
	{
		
		PageBean pageBean = null;

		logger.info("About to add Agent Details ReviewAndConfirm");
		Agent agent = (Agent) request.getSession(true).getAttribute(
				"addAgentSession");

		if (agent == null) {
			// redirect user to the add page if there's no agent in session.

			return "redirect:" + URL_BASE + "/addAgent/1";

		}
		
		
		BaseDataImpl  baseData = new BaseDataImpl();
		
		Agent  a =baseData.vaildated(agent);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			pageBean = new PageBean(" Agent add Details",
					"agent/addagent/addAgentDetails", Module.AGENT,
					"agent/sideMenuAgent");
			model.addAttribute("responseErrorData", "Form cleared that contains HTML tags "); // table
																		// response
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("agent", agent);

			// return "redirect:/agent1/addAgent";
			return TEMPLATE_DEFAULT;
		}
		

		pageBean = new PageBean("Agent user add Details",
				"agent/addagent/addAgentReviewandConfirm", Module.AGENT,
				"agent/sideMenuAgent");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("agent", agent);
		// return "redirect:/agent1/agentDetailsReviewAndConfirm";
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/agentDetailsReviewAndConfirm" }, method = RequestMethod.POST)
	public String confirmAddAgent(@ModelAttribute("agent") final Agent agent,
			final Model model, final java.security.Principal principal,
			final HttpServletRequest request) {
		logger.info("about to add Agent Details Confirms");
		PageBean pageBean = new PageBean("Succefully New Agent Added",
				"agent/addagent/addAgentSuccess", Module.AGENT,
				"agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);
		logger.info(" admin login person Name:" + principal.getName());
		Agent AgentSavedInHttpSession = (Agent) request.getSession(true)
				.getAttribute("addAgentSession");

		if (AgentSavedInHttpSession == null) {
			return "redirect:/agent1/addAgent";
		}

		model.addAttribute(AgentSavedInHttpSession);
		
		
		BaseDataImpl  baseData = new BaseDataImpl();
		
		Agent  a =baseData.vaildated(AgentSavedInHttpSession);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			pageBean = new PageBean(" Agent add Details",
					"agent/addagent/addAgentDetails", Module.AGENT,
					"agent/sideMenuAgent");
			model.addAttribute("responseErrorData", "Form cleared that contains HTML tags "); // table
																		// response
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("agent", AgentSavedInHttpSession);

			// return "redirect:/agent1/addAgent";
			request.getSession(true).removeAttribute("addAgentSession");
			return TEMPLATE_DEFAULT;
		}
		
		
		Agent agentuser = agentService.addAgent(AgentSavedInHttpSession);
		if (agentuser != null) {
			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(
					agentuser.getUsername(), principal.getName(), "addAgent");
			if (auditTrail.getUsername() != null) {
				logger.info("Agent: " + agentuser.getUsername()
						+ " Added by Admin " + principal.getName());
			}
		}
		request.getSession(true).removeAttribute("addAgentSession");
		return TEMPLATE_DEFAULT;
		// return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public String displayEditAgent(final HttpServletRequest request,final Model model,
			@PathVariable final Long id) {
		logger.info("Request to display agent edit");
		PageBean pageBean = new PageBean("Agent Detail",
				"agent/edit/agentEditDetails", Module.AGENT,
				"agent/sideMenuAgent");
		Agent agent = agentService.loadAgentByPk(id);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("agent", agent);
		
		String  err = (String) request.getSession(true).getAttribute("editErSession");
		
		if(err!=null) {
		if(err.equalsIgnoreCase("Yes")) {
			
			logger.info("err::::::" + err);
			model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
			request.getSession(true).removeAttribute("editErSession");
		}
		}
		
		
		return TEMPLATE_DEFAULT;
		// return "redirect:/agent1/edit/editagentDetail";
	}

	@RequestMapping(value = { "/editAgent" }, method = RequestMethod.POST)
	public String processEditAgentForm(final HttpServletRequest request,
			final ModelMap model, @RequestParam("id") final long id,

			@RequestParam("firstName") final String firstName,
			@RequestParam("lastName") final String lastName,
			@RequestParam("addr1") final String addr1,
			@RequestParam("addr2") final String addr2,
			@RequestParam("city") final String city,
			@RequestParam("postCode") final String postCode,
			@RequestParam("state") final String state,
			@RequestParam("phoneNo") final String phoneNo,
			@RequestParam("bankName") final String bankName,
			@RequestParam("agType") final String agType,
			@RequestParam("bankAcc") final String bankAcc,
			@RequestParam("nricNo") final String nricNo,
			@RequestParam("salutation") final String salutation,
			@RequestParam("status") final CommonStatus status

	) {

		// logger.info("Request to display agent edit");
		PageBean pageBean = new PageBean("Agent user Edit Details",
				"agent/edit/agentEditDetails", Module.AGENT,
				"agent/sideMenuAgent");
		logger.info("state: "+state+" "+salutation);
		Agent agent = agentService.loadAgentByPk(id);
		agent.setFirstName(firstName);
		agent.setLastName(lastName);
		agent.setAddr1(addr1);
		agent.setAddr2(addr2);
		agent.setBankName(bankName);
		agent.setPostCode(postCode);
		agent.setPhoneNo(phoneNo);
		agent.setCity(city);
		agent.setState(state);
		agent.setSalutation(salutation);
		agent.setBankAcc(bankAcc);
		agent.setNricNo(nricNo);
		agent.setAgType(agType);
		agent.setId(id);
		agent.setStatus(status);
		/* agent.setEmail(mailId); */
		
		
        BaseDataImpl  baseData = new BaseDataImpl();
		
        Agent  a =baseData.vaildated(agent);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");

			return "redirect:/agent1/edit/"+id+"";
		}
		
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("agent", agent);
		request.getSession(true).setAttribute("editAgentSession", agent);
		// return TEMPLATE_DEFAULT;
		return "redirect:/agent1/editReviewandConfirm";

	}

	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditAgentReview(final Model model,
			final HttpServletRequest request) {
		logger.info("about to edit Agent Details ReviewAndConfirm");

		Agent agent = (Agent) request.getSession(true).getAttribute(
				"editAgentSession");
		if (agent == null) {
			return "redirect:" + URL_BASE + "/editAgent/1";
		}
		PageBean pageBean = new PageBean("Agent Edit Review Detail",
				"agent/edit/agentReviewandConfirm", Module.AGENT,
				"agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("agent", agent);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.POST)
	public String confirmEditAgent(@ModelAttribute("agent") final Agent agent,
			final Model model, Principal principal,
			final HttpServletRequest request) {
		logger.info("about to edit Agent Details Confirms");
		PageBean pageBean = new PageBean("Successfully Agent edited",
				"agent/edit/editAgentSuccess", Module.AGENT,
				"agent/edit/sideMenuBankUser");

		model.addAttribute("pageBean", pageBean);
		Agent agentSavedInHttpSession = (Agent) request.getSession(true)
				.getAttribute("editAgentSession");
		if (agentSavedInHttpSession == null) {
			return "redirect:/agent1/editAgent";
		}
		
		  BaseDataImpl  baseData = new BaseDataImpl();
			
	        Agent  a =baseData.vaildated(agentSavedInHttpSession);
			
			
			if(a != null) {
				logger.info("Contains HTML tags");
				request.getSession(true).setAttribute("editErSession", "yes");

				return "redirect:/agent1/edit/"+a.getId()+"";
			}
		

		logger.info("agent id-----------: " + agentSavedInHttpSession.getId());
		Agent agentuser = agentService.loadAgentByPk(agentSavedInHttpSession
				.getId());
		logger.info("agent username: " + agentSavedInHttpSession.getUsername());
		logger.info("agent username: " + agentSavedInHttpSession.getSalutation());
		logger.info("agent username: " + agentSavedInHttpSession.getAgType());
		logger.info("agent username: " + agentSavedInHttpSession.getState());
		logger.info("agent username: " + agentSavedInHttpSession.getStatus());

		model.addAttribute(agentSavedInHttpSession);
		agentService.updateAgent(agentSavedInHttpSession);

		if (agentSavedInHttpSession != null) {
			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(
					agentSavedInHttpSession.getUsername(), principal.getName(),
					"editAgent");
			if (auditTrail.getUsername() != null) {
				logger.info("Agent: " + agentSavedInHttpSession.getUsername()
						+ " Edited by Admin " + principal.getName());
			}
		}
		request.getSession(true).removeAttribute("editAgentSession");

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/editUsuccessful" }, method = RequestMethod.POST)
	public String displayEditMerchantUnsuccessful(final Model model) {
		PageBean pageBean = new PageBean("Agent Detail",
				"agent/edit/editAgentUnsuccess", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);

		throw new UnsupportedOperationException("to be implemented");
	}
}
