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
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.BaseDataImpl;
import com.mobiversa.payment.dto.RegSubAgent;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.SubAgentService;
import com.mobiversa.payment.service.TransactionService;
/*import com.mobiversa.payment.validator.AddBankUserValidator;*/


@Controller
@RequestMapping(value = AgentSubController.URL_BASE)

public class AgentSubController extends BaseController {
	
	@Autowired
	private SubAgentService subAgentService;
	
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private TransactionService transactionService;
	
	
	
	public static final String URL_BASE = "/agent5";
	
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("default page display");
		return "redirect:" + URL_BASE + "/list/1";
	}
	
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displaySubAgentList(final Model model, @PathVariable final int currPage,java.security.Principal principal) {
		logger.info("about to list all agents");
		PageBean pageBean = new PageBean("Agent", "agent/subAgentDetail", Module.AGENT,
				"agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<SubAgent> paginationBean = new PaginationBean<SubAgent>();
		paginationBean.setCurrPage(currPage);
		subAgentService.listSubAgent(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		
		return TEMPLATE_DEFAULT;
	}
	@RequestMapping(value = { "/detail/{id}" }, method = RequestMethod.GET)
	public String listSubAgent(final Model model, @PathVariable final long id) {
		logger.info("Request to display agent based on ID: " + id);
		PageBean pageBean = new PageBean("Merchant Detail", "agent/subAgentDetailsView", Module.AGENT,
				"agent/sideMenuAgent");
		SubAgent subagent = subAgentService.loadSubAgentByPk(id);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", subagent);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/addSubAgent" }, method = RequestMethod.GET)
	public String displayAddsubAgent(@ModelAttribute("subagent")  final RegSubAgent regSubAgent,final HttpServletRequest request,  Model model) {
		logger.info("about to  add agent details" + regSubAgent.getMailId());
		
		
  List<Agent> agent1 =  agentService.loadAgent();
		
		
  logger.info("Total merchant:" + agent1.size()); 
		 Set<String> agentNameList = new HashSet<String>();
			for(Agent t : agent1) {
				String firstName = t.getFirstName();
				String mailId = t.getUsername();
				agentNameList.add(firstName.toString()+"~"+mailId);
		PageBean pageBean = new PageBean("agent Detail", "agent/subAgent/addSubAgentDetails", Module.AGENT,
				"agent/sideMenuAgent");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", regSubAgent);
		model.addAttribute("agentNameList", agentNameList);
			}
		
			String  err = (String) request.getSession(true).getAttribute("subAgentErSession");
			
			if(err!=null) {
			if(err.equalsIgnoreCase("Yes")) {
				
				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form cleared that contains HTML tags");
				request.getSession(true).removeAttribute("subAgentErSession");
			}
			}
			
			
		return TEMPLATE_DEFAULT;
		
	}
	
	@RequestMapping(value = { "/addSubAgent" }, method = RequestMethod.POST)
	public String processSubAgentForm(@ModelAttribute("subagent") final RegSubAgent regSubAgent,
			final HttpServletRequest request, final ModelMap model, final BindingResult errors) {
		logger.info("about to add Agent SamePage");	
		
		
		BaseDataImpl  baseData = new BaseDataImpl();
		RegSubAgent  a =baseData.vaildated(regSubAgent);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("subAgentErSession", "yes");
			return "redirect:/agent5/addSubAgent";
		}
		
		
		
		SubAgent subagent1 = subAgentService.loadAgentbyMailId(regSubAgent.getMailId());
			
		PageBean pageBean = new PageBean("Agent user add Details", "agent/subAgent/addSubAgentDetails", Module.AGENT,
					"agent/sideMenuAgent");
		model.addAttribute("responseData", null);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", regSubAgent);
		//PCI
		request.getSession(true).setAttribute("addAgentSession", regSubAgent);
		return "redirect:/agent5/subAgentDetailsReviewAndConfirm";
		
	}

	
	@RequestMapping(value = { "/subAgentDetailsReviewAndConfirm" }, method = RequestMethod.GET)
	public String displayAddSubAgentConfirmation(final ModelMap model, final HttpServletRequest request,final RegSubAgent regSubAgent) {

		logger.info("about to add Agent Details ReviewAndConfirm");
		RegSubAgent subagent = (RegSubAgent) request.getSession(true).getAttribute("addAgentSession");
		if (subagent == null) {
			// redirect user to the add page if there's no agent in session.
			
			return "redirect:" + URL_BASE + "/addSubAgent/1";
			
		}
		PageBean pageBean = new PageBean("Agent user add Details",
				"agent/subAgent/addSubAgentReviewandConfirm", Module.AGENT, "agent/sideMenuAgent");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", subagent);
		//return "redirect:/agent1/agentDetailsReviewAndConfirm";
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/subAgentDetailsReviewAndConfirm" }, method = RequestMethod.POST)
	public String confirmAddSubAgent(@ModelAttribute("agent") final RegSubAgent regSubAgent, final Model model,
			final HttpServletRequest request,final java.security.Principal principal) {
		logger.info("add confirmed subagent details");
		PageBean pageBean = new PageBean("Succefully New Agent Added",
				"agent/subAgent/addSubAgentSuccess", Module.AGENT, "agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);
		RegSubAgent AgentSavedInHttpSession = (RegSubAgent) request.getSession(true).getAttribute("addAgentSession");
		if (AgentSavedInHttpSession == null) {
			return "redirect:/agent5/addSubAgent";
		}
		
		
		BaseDataImpl  baseData = new BaseDataImpl();
		RegSubAgent  a =baseData.vaildated(AgentSavedInHttpSession);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("subAgentErSession", "yes");
			return "redirect:/agent5/addSubAgent";
		}
		
		
		model.addAttribute(AgentSavedInHttpSession);
		SubAgent subAgent=subAgentService.addSubAgent(AgentSavedInHttpSession);
		logger.info("check:   "+principal.getName());
		logger.info("check:  "+AgentSavedInHttpSession.getName());
		/*if(subAgent!=null){
		AuditTrail auditTrail = agentService.updateAuditTrailByAgent(
				principal.getName(), subAgent.getName(), "addSubAgent");

		if (auditTrail != null) {
			logger.info("Sub agent added by Agent : " + principal.getName());
		}
		}*/
		model.addAttribute("subagent", subAgent);
		request.getSession(true).removeAttribute("addAgentSession");
		return TEMPLATE_DEFAULT;
		//return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/edit/{id}" },method = RequestMethod.GET)
	public String displayEditAgent(final Model model, @PathVariable final Long id,final HttpServletRequest request) {
		logger.info("Request to display agent edit");
		
    List<Agent> agent1 =  agentService.loadAgent();
		 Set<String> agentNameList = new HashSet<String>();
			for(Agent t : agent1) {
				String firstName = t.getFirstName();
				String mailId = t.getUsername();
				agentNameList.add(firstName.toString()+"~"+mailId);
		PageBean pageBean = new PageBean("Agent Detail", "agent/editSubAgent/subAgentEditDetails", Module.AGENT,
				"agent/sideMenuAgent");
		SubAgent subagent = subAgentService.loadSubAgentByPk(id);
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
			
			String  err = (String) request.getSession(true).getAttribute("editErSession");
			
			if(err!=null) {
			if(err.equalsIgnoreCase("Yes")) {
				
				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("editErSession");
			}
			}	
			
		return TEMPLATE_DEFAULT;
     //return "redirect:/agent1/edit/editagentDetail";
	}
	@RequestMapping(value = { "/editSubAgent" },method = RequestMethod.POST)
	public String processEditAgentForm(
			final HttpServletRequest request, 
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
	) {
		
		
		logger.info("Request to display agent edit");
		{
		PageBean pageBean = new PageBean("Agent user Edit Details", "agent/editSubAgent/subAgentEditDetails",
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
	
		
		BaseDataImpl  baseData = new BaseDataImpl();
		
		RegSubAgent  a =baseData.vaildated(regsubagent);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");

			return "redirect:/agent5/edit/"+id+"";
		}
		
		
		
		//subagent.set
		
		/*agent.setFirstName(firstName);
		agent.setLastName(lastName);
		agent.setAddr1(addr1);
		
		agent.setBankName(bankName);
		agent.setPostCode(postCode);
		agent.setPhoneNo(phoneNo);
		agent.setCity(city);
		agent.setState(state);
		agent.setSalutation(salutation);
		agent.setEmail(mailId);	*/
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", regsubagent);
		//PCI 
		request.getSession(true).setAttribute("editAgentSession",regsubagent);
		}
          //return TEMPLATE_DEFAULT;
	return "redirect:/agent5/editReviewandConfirm";
		
		/*model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", subagent);
		model.addAttribute("subagent", subagent);
		request.getSession(true).setAttribute("editMobileUserSession", subagent);*/
	}
	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditAgentReview(final Model model, final HttpServletRequest request) {
		logger.info("about to edit Agent Details ReviewAndConfirm");
		
		RegSubAgent subagent = (RegSubAgent) request.getSession(true).getAttribute("editAgentSession");
		
		if (subagent == null) {
		
			return "redirect:/agent5/editSubAgent";
		}
		PageBean pageBean = new PageBean("Agent Edit Review Detail", "agent/editSubAgent/editSubAgentReviewandConfirm",
				Module.AGENT, "agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("subagent", subagent);
		return TEMPLATE_DEFAULT;
	}
	@RequestMapping(value = { "/editReviewandConfirm"}, method = RequestMethod.POST)
	public String confirmEditAgent( final Model model,
			final HttpServletRequest request) {
		logger.info("about to edit Agent Details Confirms");
		PageBean pageBean = new PageBean("Successfully Agent edited", "agent/editSubAgent/editSubAgentSuccess",
				Module.AGENT, "agent/edit/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		RegSubAgent agentSavedInHttpSession = (RegSubAgent) request.getSession(true).getAttribute("editAgentSession");
		if (agentSavedInHttpSession == null) {
			return "redirect:/agent5/editSubAgent";
		}
		
		BaseDataImpl  baseData = new BaseDataImpl();
		
		RegSubAgent  a =baseData.vaildated(agentSavedInHttpSession);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");

			return "redirect:/agent5/edit/"+a.getId()+"";
		}
		
		model.addAttribute("subagent",agentSavedInHttpSession);
		SubAgent subagent = subAgentService.loadSubAgentByPk(Long.parseLong(agentSavedInHttpSession.getId().toString()));
		subAgentService.updateSubAgent(agentSavedInHttpSession,subagent);
		request.getSession(true).removeAttribute("editAgentSession");

      return TEMPLATE_DEFAULT;
  // return "redirect:" + URL_BASE + "/list/1";
	}
	
	
	@RequestMapping(value = { "/editUsuccessful" }, method = RequestMethod.POST)
	public String displayEditMerchantUnsuccessful(final Model model) {
		PageBean pageBean = new PageBean("Agent Detail", "agent/edit/editAgentUnsuccess", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);

		throw new UnsupportedOperationException("to be implemented");
	}
}
