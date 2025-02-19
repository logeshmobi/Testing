package com.mobiversa.payment.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.HostBankDetails;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.DashBoardData;
import com.mobiversa.payment.dto.MerchantDTO;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.DashBoardService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.TransactionService;

@Controller
@RequestMapping(value = AgentController.URL_BASE)
public class AgentController extends BaseController {

	@Autowired
	private AgentService agentService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private DashBoardService dashBoardService;

	@Autowired
	private MerchantService merchantService;

	/* private static final String merchantModel = "merchant"; */

	public static final String URL_BASE = "/agent";

	/**
	 * Default wildcard page to redirect invalid page mapping to the default page.
	 * <p>
	 * for example <code>/merchant/testing</code> will be caught in the
	 * requestMapping and therefore user is redirected to display all merchant,
	 * instead of showing a 404 page.
	 * 
	 * @return
	 */
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("Default Page agent");
		return "redirect:" + URL_BASE + "/list/1";
	}

	/**
	 * Display a list of all merchants
	 * <p>
	 * Wireframe bank module 03a
	 * </p>
	 * 
	 * @param model
	 * @return
	 */
	/*
	 * @RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	 * public String displayAgenttList(final Model model, @PathVariable final int
	 * currPage) { logger.info("about to list all agent"); PageBean pageBean = new
	 * PageBean("Agent", "agent/agentList", Module.AGENT, "agent/sideMenuAgent");
	 * logger.info("about to list all agent1"); model.addAttribute("pageBean",
	 * pageBean); logger.info("about to list all agent2"); PaginationBean<Agent>
	 * paginationBean = new PaginationBean<Agent>();
	 * logger.info("about to list all agent3");
	 * paginationBean.setCurrPage(currPage);
	 * logger.info("about to list all agent4");
	 * agentService.listAgent(paginationBean);
	 * logger.info("about to list all agent5"); model.addAttribute("paginationBean",
	 * paginationBean); logger.info("about to list all agent6");
	 * System.out.println(" data : "+TEMPLATE_AGENT); return TEMPLATE_AGENT; }
	 */
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayTransactionSummary(final Model model, final Principal principal,
			@PathVariable final int currPage) {
		logger.info("displayTransactionSummary: agent : about to list all  transaction");
		PageBean pageBean = new PageBean("transactions list", "agent/transactionList", Module.AGENT,
				"agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);

		// String agentName = principal.getName();
		Agent agent = agentService.loadAgent(principal.getName());

		logger.info("Transaction Summary :" + principal.getName());
		int agentId = agent.getId().intValue();
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		agentService.listAllTransaction(paginationBean, null, null, agentId);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_AGENT;
	}

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String displayTransactionList(final Model model, @RequestParam final String date,
			@RequestParam final String date1, final Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list all  Transactiona");// @RequestParam final String agentName,
		PageBean pageBean = new PageBean("transactions list", "agent/transactionList", Module.AGENT,
				"agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);

		String agentName = principal.getName();
		Agent agent = agentService.loadAgent(principal.getName());
		logger.info("Merchant Details:" + agentName);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		int agentId = agent.getId().intValue();
		agentService.listAllTransaction(paginationBean, date, date1, agentId);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_AGENT;

	}

	// @RequestMapping(value = { "/merchantdetails/{merchantName}" }, method =
	// RequestMethod.GET)
	@RequestMapping(value = { "/merchantdetails" }, method = RequestMethod.GET)
	public String displayTransactionDetails(final Model model, @RequestParam final String merchantName,
			java.security.Principal principal, @RequestParam final String date/* ,@RequestParam final String date1 */,
			@RequestParam final String city, @PathVariable String txnType,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {// ,@PathVariable final String
																						// merchant) {

		logger.info("about to list all  Transactions");
		PageBean pageBean = new PageBean("Transactions Details", "agent/transactionsView", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		// String name[] = merchantName.split("~");
		transactionService.loadMerchantName(paginationBean, merchantName, date, txnType);

		logger.info("MerchantName:" + merchantName + ":" + "Merchant Transaction List displayed by:"
				+ principal.getName());/* merchantName,City */
		/* transactionService.loadMerchantName(paginationBean, name[1]); */
		// model.addAttribute("paginationBean", paginationBean);
		// model.addAttribute("paginationBean",paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		// request.setAttribute("errorMessage", "An error occured, the details are...");
		// String foo = "${records}";
		// request.setAttribute("records",null);

		/*
		 * if(currPage > 1){ model.addAttribute(merchant, name[1]); }else{
		 */

		/*
		 * model.addAttribute("namedd",merchantName); model.addAttribute("namedd",City);
		 */
		model.addAttribute("namedd", merchantName);
		model.addAttribute("nameyy", city);

		return TEMPLATE_AGENT;
	}

	@RequestMapping(value = { "/getAgentRecentTxn" }, method = RequestMethod.GET)
	public String getAgLatestTxnDashBoard(final Model model, final java.security.Principal principal) {
		logger.info("Login Person in dash Board:" + principal.getName());

		Agent agent = agentService.loadAgent(principal.getName());
		PaginationBean<DashBoardData> txnPaginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check first 100 txn Amount Data ");
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());

		List<MID> ids = new ArrayList<MID>();
		for (Merchant t : merchant1) {
			ids.add(t.getMid());
		}

		logger.info("ids:  " + ids);

		StringBuffer str = new StringBuffer();
		StringBuffer strUm = new StringBuffer();
		List<String> midList = new ArrayList<String>();
		List<String> ummidList = new ArrayList<String>();
		for (MID mi : ids) {

			if (mi.getMid() != null) {
				if (!mi.getMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMid()); str.append("\",");
					 */
					midList.add(mi.getMid());

				}

			}
			if (mi.getMotoMid() != null) {
				if (!mi.getMotoMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getMotoMid());
				}

			}
			if (mi.getEzypassMid() != null) {
				if (!mi.getEzypassMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getEzypassMid());
				}

			}

			if (mi.getEzywayMid() != null) {
				if (!mi.getEzywayMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getEzywayMid());
				}

			}

			if (mi.getEzyrecMid() != null) {
				if (!mi.getEzyrecMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getEzyrecMid());
				}

			}

			if (mi.getUmMid() != null) {
				if (!mi.getUmMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getUmMid());
				}

			}

			if (mi.getUmMotoMid() != null) {
				if (!mi.getUmMotoMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					ummidList.add(mi.getUmMotoMid());
				}

			}
			if (mi.getUmEzywayMid() != null) {
				if (!mi.getUmEzywayMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					ummidList.add(mi.getUmEzywayMid());
				}

			}

		}
		int u = 0;
		for (String strMid : midList) {

			if (u == 0) {
				str.append("\"");
				str.append(strMid);
				str.append("\"");
				u++;
			} else {
				str.append(",\"");
				str.append(strMid);
				str.append("\"");
			}
		}
		logger.info("String of MIDs:  " + str);

		int v = 0;
		for (String strMid : ummidList) {

			if (v == 0) {
				strUm.append("\"");
				strUm.append(strMid);
				strUm.append("\"");
				v++;
			} else {
				strUm.append(",\"");
				strUm.append(strMid);
				strUm.append("\"");
			}

		}

		logger.info("String of UMMIDs:  " + strUm);

		dashBoardService.getAgentLastHundredTxn(txnPaginationBean, str, strUm);

		logger.info(" received first 100 txn Amount Data ");

		PageBean pageBean = new PageBean("Dash Board", "dashboard/completeAgReport", Module.ADMIN,
				"admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("fiveTxnList", txnPaginationBean);
		return TEMPLATE_AGENT;
	}

	@RequestMapping(value = { "/getAgentRecentTxnUmobile" }, method = RequestMethod.GET)
	public String getAgLatestTxnDashBrdUmobile(final Model model, final java.security.Principal principal) {
		logger.info("Login Person in dash Board:" + principal.getName());

		Agent agent = agentService.loadAgent(principal.getName());
		PaginationBean<DashBoardData> txnPaginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check first 100 txn Amount Data ");
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());

		List<MID> ids = new ArrayList<MID>();
		for (Merchant t : merchant1) {
			ids.add(t.getMid());
		}

		logger.info("ids:  " + ids);

		StringBuffer str = new StringBuffer();
		StringBuffer strUm = new StringBuffer();
		List<String> midList = new ArrayList<String>();
		List<String> ummidList = new ArrayList<String>();
		for (MID mi : ids) {

			if (mi.getMid() != null) {
				if (!mi.getMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMid()); str.append("\",");
					 */
					midList.add(mi.getMid());

				}

			}
			if (mi.getMotoMid() != null) {
				if (!mi.getMotoMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getMotoMid());
				}

			}
			if (mi.getEzypassMid() != null) {
				if (!mi.getEzypassMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getEzypassMid());
				}

			}

			if (mi.getEzywayMid() != null) {
				if (!mi.getEzywayMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getEzywayMid());
				}

			}

			if (mi.getEzyrecMid() != null) {
				if (!mi.getEzyrecMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getEzyrecMid());
				}

			}

			if (mi.getUmMid() != null) {
				if (!mi.getUmMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					midList.add(mi.getUmMid());
				}

			}

			if (mi.getUmMotoMid() != null) {
				if (!mi.getUmMotoMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					ummidList.add(mi.getUmMotoMid());
				}

			}
			if (mi.getUmEzywayMid() != null) {
				if (!mi.getUmEzywayMid().isEmpty()) {
					/*
					 * str.append("\""); str.append(mi.getMotoMid()); str.append("\",");
					 */
					ummidList.add(mi.getUmEzywayMid());
				}

			}

		}
		int u = 0;
		for (String strMid : midList) {

			if (u == 0) {
				str.append("\"");
				str.append(strMid);
				str.append("\"");
				u++;
			} else {
				str.append(",\"");
				str.append(strMid);
				str.append("\"");
			}
		}
		logger.info("String of MIDs:  " + str);

		int v = 0;
		for (String strMid : ummidList) {

			if (v == 0) {
				strUm.append("\"");
				strUm.append(strMid);
				strUm.append("\"");
				v++;
			} else {
				strUm.append(",\"");
				strUm.append(strMid);
				strUm.append("\"");
			}

		}

		logger.info("String of UMMIDs:  " + strUm);

		dashBoardService.getAgentLastHundredUmTxn(txnPaginationBean, str, strUm);

		logger.info(" received first 100 txn Amount Data ");

		PageBean pageBean = new PageBean("Dash Board", "dashboard/completeAgReport", Module.ADMIN,
				"admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("fiveTxnList", txnPaginationBean);
		return TEMPLATE_AGENT;
	}

	@RequestMapping(value = { "/merchantList" }, method = RequestMethod.GET)
	public String MerchantList(final Model model, final java.security.Principal principal) {
		logger.info("Dragon tech login : ");
		PageBean pageBean = new PageBean("Merchant", "merchant/MerchantList/viewMerchantProfileFromAgent", Module.AGENT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());

		String dgUserName = principal.getName();
		Agent agent = merchantService.loadagentUsername(dgUserName);
		logger.info("agent :" + agent.toString());

		List<Merchant> agentMerchantList = merchantService.loadMerchantByAgID(agent.getId());

		for (Merchant m : agentMerchantList) {
			if (m != null) {
				if (m.getBusinessName() != null && !m.getBusinessName().isEmpty()) {
					m.setBusinessName(m.getBusinessName().toUpperCase());
				} else {
					m.setBusinessName("NIL");
				}
			}
		}
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", agentMerchantList);

		return TEMPLATE_DGTECH;
	}

	@RequestMapping(value = { "/merchantDetails" }, method = RequestMethod.GET)
	public String displayMerchantDetails(final Model model, @RequestParam("id") Long id,
			final java.security.Principal principal, HttpServletRequest request) {

		logger.info("The merchant ID came from the JSP :" + id);

		Merchant merchant = merchantService.loadMerchantbyid(id);
		MobileUser mUser = merchantService.loadBankByMerchantFk(merchant);
		HostBankDetails hDetails = merchantService.loadBankBySellerId(mUser);

		PageBean pageBean = new PageBean("Merchant", "merchant/MerchantList/viewMerchantProfileFromAgent",
				Module.MERCHANT, "merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("Agent Login Person :" + principal.getName());

		String dgUserName = principal.getName();
		Agent agent = merchantService.loadagentUsername(dgUserName);
		logger.info("agent :" + agent.toString());

		List<Merchant> agentMerchantList = merchantService.loadMerchantByAgID(agent.getId());

		Set<String> merchantNameList = new HashSet<String>();
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", agentMerchantList);
		model.addAttribute("merchantName", merchant.getUsername());
		model.addAttribute("merchant", merchant);
		model.addAttribute("merchantNameList", merchantNameList);
		model.addAttribute("hDetails", hDetails);

		return TEMPLATE_DGTECH;
	}

	@RequestMapping(value = { "/listmerchant/{agentId}/{currPage}" }, method = RequestMethod.GET)
	public String displayMerchantList(final Model model, @PathVariable String agentId, @PathVariable final int currPage,
			final java.security.Principal principal) {

		logger.info("About to list all merchants");
		PageBean pageBean = new PageBean("Merchant", "agent/merchantList", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		if (agentId.equals("0")) {
			String dgUserName = principal.getName();
			Agent agent = merchantService.loadagentUsername(dgUserName);
			agentId = String.valueOf(agent.getId());
			logger.info("Agent Id :" + agentId);
		}

		List<Merchant> merchant = merchantService.loadMerchantByAgID(Long.parseLong(agentId));

		model.addAttribute("pageBean", pageBean);
		logger.info("Agent login person:" + principal.getName());
		model.addAttribute("username", principal.getName());
		PaginationBean<MerchantDTO> paginationBean = new PaginationBean<MerchantDTO>();
		paginationBean.setCurrPage(currPage);

		merchantService.listMerchantsFromAgent(paginationBean, Long.parseLong(agentId));
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DGTECH;
	}

	@RequestMapping(value = { "/searchAgent" }, method = RequestMethod.GET)
	public String displaySearchMerchant(final Model model,
			@RequestParam(required = false, defaultValue = "1") final int currPage, @RequestParam final String date,
			@RequestParam final String date1, final java.security.Principal principal) {

		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);

		logger.info("Search Agent in our Merchant List");
		logger.info("From Date in JSP :" + date);
		logger.info("To Date in JSP :" + date1);

		PageBean pageBean = new PageBean("Search Merchant", "agent/merchantList", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		PaginationBean<MerchantDTO> paginationBean = new PaginationBean<MerchantDTO>();
		paginationBean.setCurrPage(currPage);

		String dgUserName = principal.getName();
		Agent agent = merchantService.loadagentUsername(dgUserName);
		String agentId = String.valueOf(agent.getId());
		merchantService.listMerchantsFromAgentsearch(paginationBean, fromDate, toDate, Long.parseLong(agentId));
		logger.info("List size : " + paginationBean.getItemList());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DGTECH;
	}

	@RequestMapping(value = "/exportAgentList", method = RequestMethod.GET)
	public ModelAndView getExcel(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam(required = false) String export, final java.security.Principal principal) {

		logger.info("Export Agent in our Merchant List");
		logger.info("From Date in JSP :" + date);
		logger.info("To Date in JSP :" + date1);

		PaginationBean<MerchantDTO> paginationBean = new PaginationBean<MerchantDTO>();
		String dgUserName = principal.getName();
		Agent agent = merchantService.loadagentUsername(dgUserName);
		String agentId = String.valueOf(agent.getId());
		merchantService.listMerchantsFromAgentsearch(paginationBean, date, date1, Long.parseLong(agentId));

		List<MerchantDTO> list = paginationBean.getItemList();

		logger.info("Merchant List Size : " + list.size());

		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnAgentMerchantExcel", "txnList", list);
		} else {
			return new ModelAndView("txnMerchantPdf", "txnList", list);
		}
	}

}
