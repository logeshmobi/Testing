package com.mobiversa.payment.controller;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.RegSubAgent;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.SubAgentMenuService;
import com.mobiversa.payment.service.TransactionService;
/*import com.mobiversa.payment.validator.AddBankUserValidator;*/


@Controller
@RequestMapping(value = AgentSubMenuController.URL_BASE)

public class AgentSubMenuController extends BaseController {
	
	@Autowired
	private SubAgentMenuService subAgentMenuService;
	
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private TransactionService transactionService;
	
	
	
	public static final String URL_BASE = "/subagent";
	
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("default page display");
		return "redirect:" + URL_BASE + "/list/1";
	}
	
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displaySubAgentList(final Model model, @PathVariable final int currPage,java.security.Principal principal,final SubAgent subagent) {
		logger.info("about to list all agents");
		PageBean pageBean = new PageBean("Agent", "agentLogin/subAgentSummary", Module.AGENT,
				"agent/sideMenuAgent");
		Agent agent=agentService.loadAgentbyMailId(principal.getName());
		logger.info("SubAgent Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<SubAgent> paginationBean = new PaginationBean<SubAgent>();
		paginationBean.setCurrPage(currPage);
		subAgentMenuService.listSubAgent(paginationBean,agent);
		model.addAttribute("paginationBean", paginationBean);
		
		
		return TEMPLATE_AGENT;
	}
	
	@RequestMapping(value = { "/detail" }, method = RequestMethod.POST)
	public String listSubAgent(final Model model,@RequestParam("id") final Long id,/* @PathVariable final Long id,*/
			java.security.Principal principal) {
		logger.info("agent /detail controller  " + id);
		PageBean pageBean = new PageBean("Merchant Detail", "agentLogin/subAgentDetails", Module.AGENT,
				"agent/sideMenuAgent");
		SubAgent subagent = subAgentMenuService.loadSubAgentByPk(id);
		logger.info("SubAgent Name:" + subagent.getName() + ":"+ "subAgent MailId:" + subagent.getMailId() + ":" + "SubAgent Details Displayed by:"+ principal.getName() );
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", subagent);
		return TEMPLATE_AGENT;
	}

	@RequestMapping(value = { "/addSubAgent" }, method = RequestMethod.GET)
	public String displayAddsubAgent(@ModelAttribute("subagent")  final RegSubAgent regSubAgent,
			Model model,java.security.Principal principal) {
		logger.info("about to  add agent details" + regSubAgent.getMailId());
		
		
		List<Agent> agent1=agentService.loadCurrentAgent(principal.getName());
		logger.info("SubAgent logged by:" + principal.getName() );
		 Set<String> agentNameList = new HashSet<String>();
			for(Agent t : agent1) {
				String firstName = t.getFirstName();
				String mailId = t.getUsername();
				agentNameList.add(firstName.toString()+"~"+mailId);
		PageBean pageBean = new PageBean("agent Detail", "agentLogin/addSubAgent/addSubAgentDetails", Module.AGENT,
				"agent/sideMenuAgent");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", regSubAgent);
		model.addAttribute("agentNameList", agentNameList);
			}
		return TEMPLATE_AGENT;
		
	}
	
	@RequestMapping(value = { "/addSubAgent" }, method = RequestMethod.POST)
	public String processSubAgentForm(@ModelAttribute("subagent") final RegSubAgent regSubAgent,
			java.security.Principal principal,final HttpServletRequest request, final ModelMap model, final BindingResult errors) {
		SubAgent subagent1 = subAgentMenuService.loadAgentbyMailId(regSubAgent.getMailId());
			PageBean pageBean = new PageBean("Agent user add Details", "agentLogin/addSubAgent/addSubAgentDetails", Module.AGENT,
					"agent/sideMenuAgent");
			model.addAttribute("responseData", null);
			
			logger.info("Add SubAgent logged by:" + principal.getName() );
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", regSubAgent);
		//PCI
		request.getSession(true).setAttribute("addAgentSession", regSubAgent);
		//return TEMPLATE_DEFAULT;

		return "redirect:/subagent/subAgentDetailsReviewAndConfirm";

	}

	
	@RequestMapping(value = { "/subAgentDetailsReviewAndConfirm" }, method = RequestMethod.GET)
	public String displayAddSubAgentConfirmation(final ModelMap model, final HttpServletRequest request,final RegSubAgent regSubAgent) {

		logger.info("about to add Agent Details ReviewAndConfirm");
		RegSubAgent subagent = (RegSubAgent) request.getSession(true).getAttribute("addAgentSession");
		if (subagent == null) {
			return "redirect:/subagent/addSubAgent/1";
			
		}
		PageBean pageBean = new PageBean("Agent user add Details",
				"agentLogin/addSubAgent/subAgentReviewConfirm", Module.AGENT, "agent/sideMenuAgent");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", subagent);
		return TEMPLATE_AGENT;
	}

	@RequestMapping(value = { "/subAgentDetailsReviewAndConfirm" }, method = RequestMethod.POST)
	public String confirmAddSubAgent(@ModelAttribute("agent") final RegSubAgent regSubAgent, final Model model,
			final HttpServletRequest request,java.security.Principal principal) {
		logger.info("about to add Agent Details Confirms");
		PageBean pageBean = new PageBean("Succefully New Agent Added",
				"agentLogin/addSubAgent/addSubAgentSuccess", Module.AGENT, "agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);
		RegSubAgent AgentSavedInHttpSession = (RegSubAgent) request.getSession(true).getAttribute("addAgentSession");
		
		logger.info("SubAgentName:" + AgentSavedInHttpSession.getName() + ":" + "SubAgent Added By:" + principal.getName() );
		if (AgentSavedInHttpSession == null) {
			return "redirect:/subagent/addSubAgent";
		}
		model.addAttribute(AgentSavedInHttpSession);
		SubAgent subAgent=subAgentMenuService.addSubAgent(AgentSavedInHttpSession);
		if(subAgent!=null){
			AuditTrail auditTrail = agentService.updateAuditTrailByAgent(
					principal.getName(), AgentSavedInHttpSession.getName(), "addSubAgent");

			if (auditTrail != null) {
				logger.info("Sub agent added by Agent : " + principal.getName());
			}
			}
		model.addAttribute("subagent", subAgent);
		request.getSession(true).removeAttribute("addAgentSession");
		return TEMPLATE_AGENT;
	}

	@RequestMapping(value = { "/edit" },method = RequestMethod.POST)
	public String displayEditAgent(final Model model,@RequestParam("id") final Long id,/* @PathVariable final Long id,*/final HttpServletRequest request,java.security.Principal principal) {
		logger.info("Request to display agent edit");
		
		List<Agent> agent1=agentService.loadCurrentAgent(principal.getName());
		logger.info("Total merchant:" + agent1.size()); 
		 Set<String> agentNameList = new HashSet<String>();
			for(Agent t : agent1) {
				String firstName = t.getFirstName();
				String mailId = t.getUsername();
				agentNameList.add(firstName.toString()+"~"+mailId);
		
		
		PageBean pageBean = new PageBean("Agent Detail", "agentLogin/editSubAgent1/editSubAgentDetails", Module.AGENT,
				"agent/sideMenuAgent");
		SubAgent subagent = subAgentMenuService.loadSubAgentByPk(id);
		
		
		RegSubAgent rsa =new RegSubAgent();
		if(subagent.getId() != null)
		{
		rsa.setId(subagent.getId().toString());
		}
		rsa.setName(subagent.getName());
		rsa.setSalutation(subagent.getSalutation());
		rsa.setAddr1(subagent.getAddr1());
		rsa.setAddr2(subagent.getAddr2());
		rsa.setCity(subagent.getCity());
		rsa.setPostCode(subagent.getPostCode());
		rsa.setState(subagent.getState());
		rsa.setMailId(subagent.getMailId());
		rsa.setType(subagent.getType());
		rsa.setCode(subagent.getCode());
		rsa.setPhoneNo(subagent.getPhoneNo());
		
		Agent agent = agentService.loadAgentByPk(Long.parseLong(subagent.getAgent().getId().toString()));
		String agentName ="";
		if(agent != null){
			agentName = agent.getFirstName()+"~"+agent.getUsername();
		}
		rsa.setAgentName(agentName);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", rsa);
		model.addAttribute("agentNameList", agentNameList);
		request.getSession(true).setAttribute("editAgentSession", rsa);
		}
		return TEMPLATE_AGENT;
     //return "redirect:/agent1/edit/editagentDetail";
	}
	@RequestMapping(value = { "/editSubAgent" },method = RequestMethod.POST)
	public String processEditAgentForm(
			final HttpServletRequest request, java.security.Principal principal,
			final ModelMap model,
			@RequestParam("id") final String id,
			
			
			@RequestParam("name") final String name,
			@RequestParam("mailId") final String mailId,
			@RequestParam("addr2") final String addr2,
			@RequestParam("addr1") final String addr1,	
			@RequestParam("postCode") final String postCode,
			@RequestParam("city") final String city,
			@RequestParam("phoneNo") final String phoneNo,
			@RequestParam("state") final String state,
			@RequestParam("salutation") final String salutation,
			@RequestParam("agentName")final String agentName,
			@RequestParam("type")final String type,
			@RequestParam("code")final String code
			//@RequestParam("state")final String state
	
	) {
		//List<Agent> agent1=agentService.loadCurrentAgent(principal.getName());
		PageBean pageBean = new PageBean("Agent user Edit Details", "agentLogin/editSubAgent1/editSubAgentDetails",
				Module.AGENT, "agent/sideMenuAgent");
		RegSubAgent regsubagent= new RegSubAgent();
		regsubagent.setId(id);
		regsubagent.setSalutation(salutation);
		regsubagent.setName(name);
		regsubagent.setMailId(mailId);
		regsubagent.setAddr1(addr1);
		regsubagent.setAddr2(addr2);
		regsubagent.setCity(city);
		regsubagent.setPostCode(postCode);
		regsubagent.setState(state);
		regsubagent.setPhoneNo(phoneNo);
		regsubagent.setType(type);
		regsubagent.setAgentName(agentName);
		regsubagent.setCode(code);
		regsubagent.setState(state);
	
		logger.info("Edit SubAgent logged by:" + principal.getName());
		
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", regsubagent);
		//PCI 
		request.getSession(true).setAttribute("editAgentSession",regsubagent);
	
	return "redirect:/subagent/editReviewandConfirm";
		

	}
	
	
	
	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditAgentReview(final Model model, final HttpServletRequest request) {
		logger.info("about to edit Agent Details ReviewAndConfirm");
		
		RegSubAgent subagent = (RegSubAgent) request.getSession(true).getAttribute("editAgentSession");
		if (subagent == null) {
			return "redirect:/subagent/editSubAgent";
		}
		PageBean pageBean = new PageBean("Agent Edit Review Detail", "agentLogin/editSubAgent1/editSubAgentConfirm",
				Module.AGENT, "agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", subagent);
		return TEMPLATE_AGENT;
	}
	@RequestMapping(value = { "/editReviewandConfirm"}, method = RequestMethod.POST)
	public String confirmEditAgent( final Model model,java.security.Principal principal,
			final HttpServletRequest request) {
		logger.info("about to edit Agent Details Confirms");
		PageBean pageBean = new PageBean("Successfully Agent edited", "agentLogin/editSubAgent1/editSubAgentSuccess",
				Module.AGENT, "agent/edit/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		RegSubAgent agentSavedInHttpSession = (RegSubAgent) request.getSession(true).getAttribute("editAgentSession");
		
		logger.info("SubAgentName:" + agentSavedInHttpSession.getName() + ":" + "SubAgent Updated By:" + principal.getName() );
		if (agentSavedInHttpSession == null) {
			return "redirect:/subagent/editSubAgent";
		}
		model.addAttribute("subagent",agentSavedInHttpSession);
		SubAgent subagent = subAgentMenuService.loadSubAgentByPk(Long.parseLong(agentSavedInHttpSession.getId().toString()));
		subAgentMenuService.updateSubAgent(agentSavedInHttpSession,subagent);
		request.getSession(true).removeAttribute("editAgentSession");

      return TEMPLATE_AGENT;
	}
	
	
	@RequestMapping(value = { "/editUsuccessful" }, method = RequestMethod.POST)
	public String displayEditMerchantUnsuccessful(final Model model) {
		PageBean pageBean = new PageBean("Agent Detail", "agent/edit/editAgentUnsuccess", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);

		throw new UnsupportedOperationException("to be implemented");
	}
}

