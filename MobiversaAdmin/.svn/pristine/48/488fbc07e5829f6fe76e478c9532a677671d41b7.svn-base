package com.mobiversa.payment.controller;

import java.security.Principal;
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
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.MasterMerchant;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.BaseDataImpl;
import com.mobiversa.payment.service.AdminService;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.MasterMerchantService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.validator.AddAgentUserValidator;

/*import com.mobiversa.payment.validator.AddBankUserValidator;*/

@Controller
@RequestMapping(value = MasterMerchantController.URL_BASE)
public class MasterMerchantController extends BaseController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private MasterMerchantService masterMerchantService;
	
	@Autowired
	private AgentService agentService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AddAgentUserValidator validator;

	public static final String URL_BASE = "/masterMerchant";

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("default page display");
		return "redirect:" + URL_BASE + "/list/1";
	}
	
	
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayMasterMerchantList(final Model model,
			@PathVariable final int currPage,
			final java.security.Principal principal) {
		
		PageBean pageBean = new PageBean("Agent", "masterMerchant/masterMerchantDetail",
				Module.AGENT, "agent/sideMenuAgent");

		logger.info(" Agent Summary for Admin :" + principal.getName());


		model.addAttribute("pageBean", pageBean);
		PaginationBean<MasterMerchant> paginationBean = new PaginationBean<MasterMerchant>();
		paginationBean.setCurrPage(currPage);
		masterMerchantService.listMasterMerchant(paginationBean);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}
	
	
	@RequestMapping(value = { "/detail/{id}" }, method = RequestMethod.GET)
	public String listMasterMerchant(final Model model, @PathVariable final long id) {
		logger.info("Request to display agent based on ID: " + id);
		PageBean pageBean = new PageBean("Master Merchant Detail",
				"masterMerchant/masterMerchantDetailsView", Module.AGENT,
				"merchant/sideMenuMerchant");
		MasterMerchant masterMerchant = masterMerchantService.loadMasterMerchantByPk(id);
	
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("masterMerchant", masterMerchant);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/addMasterMerchant" }, method = RequestMethod.GET)
	public String displayAddAgent(@ModelAttribute("masterMerchant") MasterMerchant masterMerchant,
			Model model) {
		logger.info("about to  add agent details" + masterMerchant.getMailId());
		PageBean pageBean = new PageBean("agent Detail",
				"masterMerchant/addMasterMerchant/addMasterMerchantDetails", Module.AGENT,
				"agent/sideMenuAgent");
		
		List<Agent> agent1 = agentService.loadAgent();

		logger.info("Total Number of Agents:" + agent1.size());

		Set<String> agentNameList = new HashSet<String>();
		for (Agent t : agent1) {
			String firstName = t.getFirstName();
			String mailId = t.getUsername();
			agentNameList.add("AGENT~" + firstName.toString() + "~" + mailId);
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("masterMerchant", masterMerchant);
		model.addAttribute("agentNameList", agentNameList);

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/addMasterMerchant" }, method = RequestMethod.POST)
	public String processAddUserForm(
			@ModelAttribute("masterMerchant") final MasterMerchant masterMerchant,
			final java.security.Principal principal,
			final HttpServletRequest request, final ModelMap model,
			final BindingResult errors) {
		logger.info("About to add New Agent :" + masterMerchant.getMailId());
		MasterMerchant masterMerchant1 = masterMerchantService.loadMMbyMailId(masterMerchant.getMailId());

		logger.info("Display agent name:" + masterMerchant.getFirstName());
		PageBean pageBean = null;
		
		List<Agent> agent1 = agentService.loadAgent();

		logger.info("Total Number of Agents:" + agent1.size());

		Set<String> agentNameList = new HashSet<String>();
		for (Agent t : agent1) {
			String firstName = t.getFirstName();
			String mailId = t.getUsername();
			agentNameList.add("AGENT~" + firstName.toString() + "~" + mailId);
		}
		
		
		BaseDataImpl  baseData = new BaseDataImpl();
		
		MasterMerchant  a =baseData.vaildated(masterMerchant);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			pageBean = new PageBean(" Agent add Details",
					"masterMerchant/addMasterMerchant/addMasterMerchantDetails", Module.AGENT,
					"agent/sideMenuAgent");
			model.addAttribute("responseErrorData", "Form cleared that contains HTML tags "); // table
																		// response
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("masterMerchant", masterMerchant);
			model.addAttribute("agentNameList", agentNameList);

			// return "redirect:/agent1/addAgent";
			return TEMPLATE_DEFAULT;
		}
		
		
		if (masterMerchant1 != null) {
			pageBean = new PageBean(" Agent add Details",
					"masterMerchant/addMasterMerchant/addMasterMerchantDetails", Module.AGENT,
					"agent/sideMenuAgent");
			model.addAttribute("responseData", "Email already exist"); // table
			model.addAttribute("agentNameList", agentNameList);															// response
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("masterMerchant", masterMerchant);

			// return "redirect:/agent1/addAgent";
			return TEMPLATE_DEFAULT;

		} else {

			pageBean = new PageBean("Agent user add Details",
					"masterMerchant/addMasterMerchant/addMasterMerchantDetails", Module.AGENT,
					"agent/sideMenuAgent");
			model.addAttribute("responseData", null);
			model.addAttribute("agentNameList", agentNameList);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("masterMerchant", masterMerchant);
			// PCI
			request.getSession(true).setAttribute("addMasterMerchantSession", masterMerchant);

			return "redirect:/masterMerchant/masterMerchantDetailsReview";
		}
	}

	@RequestMapping(value = { "/masterMerchantDetailsReview" }, method = RequestMethod.GET)
	public String displayAddAgentConfirmation(final ModelMap model,
			final HttpServletRequest request,
			final java.security.Principal principal)
	{
		
		PageBean pageBean = null;

		logger.info("About to add Agent Details ReviewAndConfirm");
		MasterMerchant masterMerchant = (MasterMerchant) request.getSession(true).getAttribute(
				"addMasterMerchantSession");
		List<Agent> agent1 = agentService.loadAgent();

		logger.info("Total Number of Agents:" + agent1.size());

		Set<String> agentNameList = new HashSet<String>();
		for (Agent t : agent1) {
			String firstName = t.getFirstName();
			String mailId = t.getUsername();
			agentNameList.add("AGENT~" + firstName.toString() + "~" + mailId);
		}

		if (masterMerchant == null) {
			// redirect user to the add page if there's no agent in session.

			return "redirect:" + URL_BASE + "/addMasterMerchant/1";

		}
		
		
		BaseDataImpl  baseData = new BaseDataImpl();
		
		MasterMerchant  a =baseData.vaildated(masterMerchant);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			pageBean = new PageBean(" Agent add Details",
					"masterMerchant/addMasterMerchant/addMasterMerchantDetails", Module.AGENT,
					"agent/sideMenuAgent");
			model.addAttribute("responseErrorData", "Form cleared that contains HTML tags "); // table
																		// response
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("masterMerchant", masterMerchant);
			model.addAttribute("agentNameList", agentNameList);

			// return "redirect:/agent1/addAgent";
			return TEMPLATE_DEFAULT;
		}
		

		pageBean = new PageBean("Agent user add Details",
				"masterMerchant/addMasterMerchant/addMasterMerchantReviewandConfirm", Module.AGENT,
				"agent/sideMenuAgent");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("masterMerchant", masterMerchant);
		model.addAttribute("agentNameList", agentNameList);
		// return "redirect:/agent1/agentDetailsReviewAndConfirm";
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/masterMerchantDetailsReviewAndConfirm" }, method = RequestMethod.POST)
	public String confirmAddAgent(@ModelAttribute("masterMerchant") final MasterMerchant masterMerchant,
			final Model model, final java.security.Principal principal,
			final HttpServletRequest request) {
		logger.info("about to add Agent Details Confirms");
		PageBean pageBean = new PageBean("Succefully New Agent Added",
				"masterMerchant/addMasterMerchant/addMasterMerchantSuccess", Module.MASTER_MERCHANT,
				"agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);
		logger.info(" admin login person Name:" + principal.getName());
		MasterMerchant MasterMerchantSavedInHttpSession = (MasterMerchant) request.getSession(true)
				.getAttribute("addMasterMerchantSession");

		if (MasterMerchantSavedInHttpSession == null) {
			return "redirect:/masterMerchant/addMasterMerchant";
		}

		model.addAttribute(MasterMerchantSavedInHttpSession);
		
		
		BaseDataImpl  baseData = new BaseDataImpl();
		
		MasterMerchant  a =baseData.vaildated(MasterMerchantSavedInHttpSession);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			pageBean = new PageBean(" Agent add Details",
					"masterMerchant/addMasterMerchant/addMasterMerchantDetails", Module.AGENT,
					"agent/sideMenuAgent");
			model.addAttribute("responseErrorData", "Form cleared that contains HTML tags "); // table
																		// response
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("masterMerchant", MasterMerchantSavedInHttpSession);

			// return "redirect:/agent1/addAgent";
			request.getSession(true).removeAttribute("addMasterMerchantSession");
			return TEMPLATE_DEFAULT;
		}
		
		
		MasterMerchant masterMerchantuser = masterMerchantService.addMasterMerchant(MasterMerchantSavedInHttpSession);
		/*if (masterMerchantuser != null) {
			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(
					masterMerchantuser.getUsername(), principal.getName(), "addMasterMerchant");
			if (auditTrail.getUsername() != null) {
				logger.info("masterMerchantuser: " + masterMerchantuser.getUsername()
						+ " Added by Admin " + principal.getName());
			}
		}*/
		request.getSession(true).removeAttribute("addMasterMerchantSession");
		return TEMPLATE_DEFAULT;
		// return "redirect:" + URL_BASE + "/list/1";
	}
	
	
	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public String displayEditmm(final HttpServletRequest request,final Model model,
			@PathVariable final Long id) {
		logger.info("Request to display mm edit");
		PageBean pageBean = new PageBean("Master merchant Detail",
				"masterMerchant/edit/masterMerchantEditDetails", Module.AGENT,
				"agent/sideMenuAgent");
		MasterMerchant masterMerchant = masterMerchantService.loadMasterMerchantByPk(id);
		
		
		List<Agent> agent1 = agentService.loadAgent();

		logger.info("Total Number of Agents:" + agent1.size());
		Set<String> agentNameList = new HashSet<String>();
		for (Agent t : agent1) {
			String firstName = t.getFirstName();
			String mailId = t.getUsername();
			agentNameList.add("AGENT~" + firstName.toString() + "~" + mailId);
		}
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("masterMerchant", masterMerchant);
		model.addAttribute("agentNameList", agentNameList);
		
		String  err = (String) request.getSession(true).getAttribute("editErSession");
		
		if(err!=null) {
		if(err.equalsIgnoreCase("Yes")) {
			
			logger.info("err::::::" + err);
			model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
			request.getSession(true).removeAttribute("editErSession");
		}
		}
		
		
		return TEMPLATE_DEFAULT;
		
	}
	
	
	@RequestMapping(value = { "/editMasterMerchant" }, method = RequestMethod.POST)
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
			@RequestParam("agentName") final String agentName,
			@RequestParam("bankAcc") final String bankAcc,
			@RequestParam("nricNo") final String nricNo,
			@RequestParam("salutation") final String salutation,
			@RequestParam("status") final CommonStatus status) {

		
		PageBean pageBean = new PageBean("Agent user Edit Details",
				"masterMerchant/edit/masterMerchantEditDetails", Module.AGENT,
				"agent/sideMenuAgent");
		logger.info("state: "+state+" "+salutation);
		
		List<Agent> agent1 = agentService.loadAgent();

		logger.info("Total Number of Agents:" + agent1.size());
		Set<String> agentNameList = new HashSet<String>();
		for (Agent t : agent1) {
			String agfirstName = t.getFirstName();
			String mailId = t.getUsername();
			agentNameList.add("AGENT~" + agfirstName.toString() + "~" + mailId);
		}
		
		MasterMerchant masterMerchant = masterMerchantService.loadMasterMerchantByPk(id);
		masterMerchant.setFirstName(firstName);
		masterMerchant.setLastName(lastName);
		masterMerchant.setAddr1(addr1);
		masterMerchant.setAddr2(addr2);
		masterMerchant.setBankName(bankName);
		masterMerchant.setPostCode(postCode);
		masterMerchant.setPhoneNo(phoneNo);
		masterMerchant.setCity(city);
		masterMerchant.setState(state);
		masterMerchant.setSalutation(salutation);
		masterMerchant.setBankAcc(bankAcc);
		masterMerchant.setNricNo(nricNo);
		masterMerchant.setId(id);
		masterMerchant.setStatus(status);
		masterMerchant.setAgentName(agentName);
		/* agent.setEmail(mailId); */
		
		
        BaseDataImpl  baseData = new BaseDataImpl();
		
        MasterMerchant  a =baseData.vaildated(masterMerchant);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");

			return "redirect:/masterMerchant/edit/"+id+"";
		}
		
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("masterMerchant", masterMerchant);
		model.addAttribute("agentNameList", agentNameList);
		request.getSession(true).setAttribute("editMasterMerchantSession", masterMerchant);
		// return TEMPLATE_DEFAULT;
		return "redirect:/masterMerchant/editReviewandConfirm";

	}

	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditAgentReview(final Model model,
			final HttpServletRequest request) {
		logger.info("about to edit masterMerchant Details ReviewAndConfirm");

		MasterMerchant masterMerchant = (MasterMerchant) request.getSession(true).getAttribute(
				"editMasterMerchantSession");
		if (masterMerchant == null) {
			return "redirect:" + URL_BASE + "/editMasterMerchant/1";
		}
		
		List<Agent> agent1 = agentService.loadAgent();

		logger.info("Total Number of Agents:" + agent1.size());
		Set<String> agentNameList = new HashSet<String>();
		for (Agent t : agent1) {
			String agfirstName = t.getFirstName();
			String mailId = t.getUsername();
			agentNameList.add("AGENT~" + agfirstName.toString() + "~" + mailId);
		}
		PageBean pageBean = new PageBean("MM Edit Review Detail",
				"masterMerchant/edit/masterMerchantReviewandConfirm", Module.AGENT,
				"agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("masterMerchant", masterMerchant);
		model.addAttribute("agentNameList", agentNameList);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.POST)
	public String confirmEditAgent(@ModelAttribute("masterMerchant") final MasterMerchant masterMerchant,
			final Model model, Principal principal,
			final HttpServletRequest request) {
		logger.info("about to edit masterMerchant Details Confirms");
		PageBean pageBean = new PageBean("Successfully masterMerchant edited",
				"masterMerchant/edit/editMasterMerchantSuccess", Module.AGENT,
				"agent/edit/sideMenuBankUser");

		model.addAttribute("pageBean", pageBean);
		MasterMerchant masterMerchantSavedInHttpSession = (MasterMerchant) request.getSession(true)
				.getAttribute("editMasterMerchantSession");
		if (masterMerchantSavedInHttpSession == null) {
			return "redirect:/masterMerchant/editMasterMerchant";
		}
		
		  BaseDataImpl  baseData = new BaseDataImpl();
			
		  MasterMerchant  a =baseData.vaildated(masterMerchantSavedInHttpSession);
			
			
			if(a != null) {
				logger.info("Contains HTML tags");
				request.getSession(true).setAttribute("editErSession", "yes");

				return "redirect:/masterMerchant/edit/"+a.getId()+"";
			}
		

		logger.info("MM id-----------: " + masterMerchantSavedInHttpSession.getId());
		
		MasterMerchant masterMerchantUser = masterMerchantService.
				loadMasterMerchantByPk(masterMerchantSavedInHttpSession.getId());
		
		logger.info("MM username: " + masterMerchantUser.getUsername());
		logger.info("MM username: " + masterMerchantUser.getSalutation());
	
		logger.info("MM username: " + masterMerchantUser.getState());
		logger.info("MM username: " + masterMerchantUser.getStatus());

		model.addAttribute(masterMerchantSavedInHttpSession);
		masterMerchantService.updateMasterMerchant(masterMerchantSavedInHttpSession);
		
		logger.info("username at end::"+masterMerchantSavedInHttpSession.getUsername());
		logger.info("principal name at end::"+principal.getName());
		/*if (masterMerchantSavedInHttpSession != null) {
			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(
					masterMerchantSavedInHttpSession.getFirstName(), principal.getName(),
					"editMasterMerchant");
			if (auditTrail.getUsername() != null) {
				logger.info("MasterMerchant: " + masterMerchantSavedInHttpSession.getFirstName()
						+ " Edited by Admin " + principal.getName());
			}
		}*/
		request.getSession(true).removeAttribute("editMasterMerchantSession");

		return TEMPLATE_DEFAULT;

	}

	@RequestMapping(value = { "/editUsuccessful" }, method = RequestMethod.POST)
	public String displayEditMerchantUnsuccessful(final Model model) {
		PageBean pageBean = new PageBean("Agent Detail",
				"masterMerchant/edit/editMasterMerchantUnsuccess", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);

		throw new UnsupportedOperationException("to be implemented");
	}
	

	}
