package com.mobiversa.payment.controller;

import java.math.BigInteger;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.dto.AgentResponseDTO;
import com.mobiversa.common.dto.MerchantSettlementDTO;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.AgentVolumeData;
import com.mobiversa.payment.dto.DashBoardData;
import com.mobiversa.payment.dto.MerchantGPVData;
import com.mobiversa.payment.dto.SixMonthTxnData;
import com.mobiversa.payment.dto.ThreeMonthTxnData;
import com.mobiversa.payment.service.AdminService;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.DashBoardService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.PromotionService;
import com.mobiversa.payment.service.SubAgentService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.DashboardAmount;
import com.mobiversa.payment.util.UMEzyway;

@Controller
@RequestMapping(value = SuperAgentController.URL_BASE)

public class SuperAgentController extends BaseController {

	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private DashBoardService dashBoardService;
	
	@Autowired
	private PromotionService promotionService;
	@Autowired
	private AgentService agentService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private SubAgentService subAgentService;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private MobileUserService mobileUserService;

	// superagent all the menus started 18-04-2017
	public static final String URL_BASE = "/superagent";

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("default page display");
		return "redirect:" + URL_BASE + "/list/1";
	}

// agent summary super agent login 18-04-2017
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayAgentList(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal) {
		// logger.info("about to list all agents");
		PageBean pageBean = new PageBean("Agent", "SuperAgent/agentSummary", Module.AGENT, "agent/sideMenuAgent");

		logger.info(" Agent Summary for Admin :" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Agent> paginationBean = new PaginationBean<Agent>();
		
		
		
		paginationBean.setCurrPage(currPage);
		agentService.listAgent(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_SUPER_AGENT;
	}

// merchant summary in super agent login 18-04-2017
	@RequestMapping(value = { "/merchantSummary/{currPage}" }, method = RequestMethod.GET)
	public String displayMerchantList(final Model model, final Merchant merchant, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("Merchant", "SuperAgent/merchantSummary", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		paginationBean.setCurrPage(currPage);
		merchantService.listMerchant(paginationBean);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_SUPER_AGENT;
	}
	
	
	@RequestMapping(value = { "/merchantGPVSummary/{currPage}" }, method = RequestMethod.GET)
	public String displayMerchantGPVList(final Model model, final Merchant merchant, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("Merchant", "SuperAgent/merchantList", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		paginationBean.setCurrPage(currPage);
		
		merchantService.listMerchantGPVDetails(paginationBean);
		
		model.addAttribute("paginationBean", paginationBean);
		
		return TEMPLATE_SUPER_AGENT;
	}
	
	
	@RequestMapping(value = { "/merchantDetailSummary/{currPage}" }, method = RequestMethod.GET)
	public String displayMerchantDetailsList(final Model model, final Merchant merchant, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("Merchant", "SuperAgent/merchantList", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		paginationBean.setCurrPage(currPage);
		
		/*List<Merchant> merchantList = merchantService.loadMerchant();
		int totalMerchant = merchantList.size();
		int quotient = totalMerchant / 10;
		int Remainder = totalMerchant % 10;
		int nloops = 0;
		
		logger.info("merchantList" + merchantList.size());
		logger.info("quotient" + quotient);
		logger.info("Remainder" + Remainder);
		
		if(Remainder !=0) {
			nloops = quotient +1 ;
			logger.info("added nloops" + nloops);
		}else {
			nloops = quotient;
			logger.info("nloops" + nloops);
		}
		
		ArrayList<Integer> pageNumbers=new ArrayList<Integer>();
		
		if(nloops == 1) {
			pageNumbers.add(1);
		}else if(nloops == 2) {
			pageNumbers.add(1);
			pageNumbers.add(2);
		}else {
			
			for(int i =1 ; i <= 3 ;i++) {
				pageNumbers.add(i);
			}
		}
		
		String offset = null;
		int currtPage = currPage - 1;
		
		if(currtPage == 0) {
			logger.info("First Page :" + currtPage);
			offset = String.valueOf(currtPage);
		}else {
			logger.info("Page :" + currtPage);
			currtPage = 10* currtPage;
			logger.info("currtPage = 10* currtPage ->" + currtPage);
			offset = String.valueOf(currPage);
		}
		
		if(offset != null) {
			offset = offset ;
			logger.info("Offset Value :"+ offset);
		}else {
			offset = "0";
			logger.info("Offset Default :"+ offset);
		}*/
		
		merchantService.listMerchantDetails(paginationBean);
		
		/*List<Merchant> merchant1 = merchantService.loadMerchant();
		List<MID> ids = new ArrayList<MID>();
	    for(Merchant t: merchant1) {
	    	ids.add(t.getMid());
	    }
	    
	    logger.info("ids:  "+ids);
	    
		StringBuffer str = new StringBuffer();
	    StringBuffer strUm = new StringBuffer();
	    List<String> midList = new ArrayList<String>();
	    List<String> ummidList = new ArrayList<String>();
	    for(MID mi : ids) {
	    	
	    	if(mi.getMid() != null) {
	    		if(!mi.getMid().isEmpty()) {
	    			str.append("\"");
	    			str.append(mi.getMid());
	    			str.append("\",");
	    			midList.add(mi.getMid());
	    			
	    		}
	    		
	    	}
	    	if(mi.getMotoMid() != null) {
	    		if(!mi.getMotoMid().isEmpty()) {
	    			str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");
	    			midList.add(mi.getMotoMid());
	    		}
	    		
	    	}
	    	if(mi.getEzypassMid() != null) {
	    		if(!mi.getEzypassMid().isEmpty()) {
	    			str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");
	    			midList.add(mi.getEzypassMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getEzywayMid() != null) {
	    		if(!mi.getEzywayMid().isEmpty()) {
	    			str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");
	    			midList.add(mi.getEzywayMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getEzyrecMid() != null) {
	    		if(!mi.getEzyrecMid().isEmpty()) {
	    			str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");
	    			midList.add(mi.getEzyrecMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getUmMid() != null) {
	    		if(!mi.getUmMid().isEmpty()) {
	    			str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");
	    			midList.add(mi.getUmMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getUmMotoMid() != null) {
	    		if(!mi.getUmMotoMid().isEmpty()) {
	    			str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");
	    			midList.add(mi.getUmMotoMid());
	    		}
	    		
	    	}
	    	if(mi.getUmEzywayMid() != null) {
	    		if(!mi.getUmEzywayMid().isEmpty()) {
	    			str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");
	    			midList.add(mi.getUmEzywayMid());
	    		}
	    		
	    	}
	    	
	    }
	    int u =0;
	    for(String strMid : midList) {
	    	
	    	if(u == 0) {
	    		str.append("\"");
    			str.append(strMid);
    			str.append("\"");
    			u++;
	    	}else {		    	
		    	str.append(",\"");
    			str.append(strMid);
    			str.append("\"");
	    	}
	    }
	    logger.info("String of MIDs:  "+str);
		merchantService.getMerchantGPV(str,paginationBean);*/

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_SUPER_AGENT;
	}
	
	
	@RequestMapping(value = { "/viewMerchantDetailSummary/{mercid}/{agentName}" }, method = RequestMethod.GET)
	public String listMerchant(final Model model, @PathVariable final String mercid,
			@PathVariable final String agentName,
			final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("Request to display merchant based on ID: " + mercid);
		
		
		long id =  Long.parseLong(mercid);
		logger.info("merchant id: " + id);
		logger.info("agentName: " + agentName);
		PageBean pageBean = new PageBean("Merchant Detail",
				"SuperAgent/merchantDetail", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		PaginationBean<TerminalDetails> paginationBean = new PaginationBean<TerminalDetails>();
		paginationBean.setCurrPage(currPage);
		
		Merchant merchant = merchantService.loadMerchantByPk(id);
		logger.info("Display Merchant Name " + merchant.getBusinessName());
		logger.info("Display MID " + merchant.getMid().getMid());
		
		List<String> midList = new ArrayList<String>();
		String mid=null,motoMid=null,ezypassMid=null,ezywayMid=null,
				ezyrecMid=null,gPayMid=null,umMid=null,umEzyway=null,umEzymoto=null;
		if(merchant.getMid().getMid()!=null) {
			mid=merchant.getMid().getMid();
			midList.add(mid);
		}else if(merchant.getMid().getMotoMid()!=null) {
			motoMid=merchant.getMid().getMotoMid();
			midList.add(motoMid);
		}else if(merchant.getMid().getEzypassMid()!=null) {
			ezypassMid=merchant.getMid().getEzypassMid();
			midList.add(ezypassMid);
		}else if(merchant.getMid().getEzyrecMid()!=null) {
			ezyrecMid=merchant.getMid().getEzyrecMid();
			midList.add(ezyrecMid);
		}else if(merchant.getMid().getEzywayMid()!=null) {
			ezywayMid=merchant.getMid().getEzywayMid();
			midList.add(ezywayMid);
		}
		else if(merchant.getMid().getGpayMid()!=null) {
			gPayMid=merchant.getMid().getGpayMid();
			midList.add(gPayMid);
		}
		else if(merchant.getMid().getUmMid()!=null){
			umMid=merchant.getMid().getUmMid();
			midList.add(umMid);
		}
		else if(merchant.getMid().getUmEzywayMid()!=null){
			umEzyway=merchant.getMid().getUmEzywayMid();
			midList.add(umEzyway);
		}
		else if(merchant.getMid().getUmMotoMid()!=null){
			umEzymoto=merchant.getMid().getUmMotoMid();
			midList.add(umEzymoto);
		}
		
		StringBuffer midStr = new StringBuffer();
		int u =0;
	    for(String strMid : midList) {
	    	
	    	if(u == 0) {
	    		midStr.append("\"");
	    		midStr.append(strMid);
	    		midStr.append("\"");
    			u++;
	    	}else {		    	
	    		midStr.append(",\"");
	    		midStr.append(strMid);
	    		midStr.append("\"");
	    	}
	    }
	    logger.info("String of MIDs:  "+midStr);
		
		//TerminalDetails termDet = merchantService.loadTerminalDetailsByMid(merchant.getMid().getMid());
		
		List<String> productTypeList = new ArrayList<String>();
		productTypeList = merchantService.getProductDetails(midStr);
		
		List<String> yearList = new ArrayList<String>();
		yearList = getConsecutiveYears();
		
		merchantService.loadTerminalDetails(midStr,paginationBean);
		if (paginationBean.getItemList().isEmpty()
				|| paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		
		PaginationBean<TerminalDetails> paginationBean1 = new PaginationBean<TerminalDetails>();
		paginationBean1.setCurrPage(currPage);
		
		merchantService.loadCurrentTxnDetails(merchant,paginationBean1,agentName);
		logger.info("paginationBean1.getItemList()"+paginationBean1.getItemList());
		if (paginationBean1.getItemList().isEmpty()
				|| paginationBean1.getItemList() == null
				|| paginationBean1.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("productTypeList", productTypeList);
		model.addAttribute("yearList", yearList);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("paginationBean1", paginationBean1);
		return TEMPLATE_SUPER_AGENT;
	}
	
	
	/*@RequestMapping(value = { "/loadTxnDetails/{mercid}/{currPage}" }, method = RequestMethod.GET)
	public String loadTransactionList(final Model model,
			@PathVariable final int currPage,
			@PathVariable final String mercid,
			final java.security.Principal principal) {
		logger.info("Request to display merchant based on ID: " + mercid);
		
		long id =  Long.parseLong(mercid);
		logger.info("merchant id: " + id);
		PageBean pageBean = new PageBean("Merchant Detail",
				"SuperAgent/merchantTxnDetail", Module.MERCHANT,
				"merchant/sideMenuMerchant");		
		
		Merchant merchant = merchantService.loadMerchantByPk(id);
		logger.info("Display Merchant Name " + merchant.getBusinessName());
		logger.info("Display MID " + merchant.getMid().getMid());
		
		List<String> midList = new ArrayList<String>();
		String mid=null,motoMid=null,ezypassMid=null,ezywayMid=null,
				ezyrecMid=null,gPayMid=null,umMid=null,umEzyway=null,umEzymoto=null;
		if(merchant.getMid().getMid()!=null) {
			mid=merchant.getMid().getMid();
			midList.add(mid);
		}else if(merchant.getMid().getMotoMid()!=null) {
			motoMid=merchant.getMid().getMotoMid();
			midList.add(motoMid);
		}else if(merchant.getMid().getEzypassMid()!=null) {
			ezypassMid=merchant.getMid().getEzypassMid();
			midList.add(ezypassMid);
		}else if(merchant.getMid().getEzyrecMid()!=null) {
			ezyrecMid=merchant.getMid().getEzyrecMid();
			midList.add(ezyrecMid);
		}else if(merchant.getMid().getEzywayMid()!=null) {
			ezywayMid=merchant.getMid().getEzywayMid();
			midList.add(ezywayMid);
		}
		else if(merchant.getMid().getGpayMid()!=null) {
			gPayMid=merchant.getMid().getGpayMid();
			midList.add(gPayMid);
		}
		else if(merchant.getMid().getUmMid()!=null){
			umMid=merchant.getMid().getUmMid();
			midList.add(umMid);
		}
		else if(merchant.getMid().getUmEzywayMid()!=null){
			umEzyway=merchant.getMid().getUmEzywayMid();
			midList.add(umEzyway);
		}
		else if(merchant.getMid().getUmMotoMid()!=null){
			umEzymoto=merchant.getMid().getUmMotoMid();
			midList.add(umEzymoto);
		}
		
		StringBuffer midStr = new StringBuffer();
		int u =0;
	    for(String strMid : midList) {
	    	
	    	if(u == 0) {
	    		midStr.append("\"");
	    		midStr.append(strMid);
	    		midStr.append("\"");
    			u++;
	    	}else {		    	
	    		midStr.append(",\"");
	    		midStr.append(strMid);
	    		midStr.append("\"");
	    	}
	    }
	    logger.info("String of MIDs:  "+midStr);
		
		//TerminalDetails termDet = merchantService.loadTerminalDetailsByMid(merchant.getMid().getMid());
		
		List<String> productTypeList = new ArrayList<String>();
		productTypeList = merchantService.getProductDetails(midStr);
		
		List<String> yearList = new ArrayList<String>();
		yearList = getConsecutiveYears();
		
		PaginationBean<TerminalDetails> paginationBean = new PaginationBean<TerminalDetails>();
		paginationBean.setCurrPage(currPage);
		
		merchantService.loadCurrentTxnDetails(merchant,paginationBean);
		logger.info("paginationBean.getItemList()"+paginationBean.getItemList());
		
		if (paginationBean.getItemList().isEmpty()
				|| paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("productTypeList", productTypeList);
		model.addAttribute("yearList", yearList);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_SUPER_AGENT;
	}*/
	
	@RequestMapping(value = { "/searchTxn" }, method = RequestMethod.GET)
	public String searchTransactionList(HttpServletRequest request,
			final Model model,
			@RequestParam final String period,
			@RequestParam final String productType,
			@RequestParam final String mercid,
			@RequestParam final String year,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("searchTransactionList");
		logger.info("period::"+period);
		logger.info("productType::"+productType);
		
		
		long id =  Long.parseLong(mercid);
		
		PageBean pageBean = new PageBean("Merchant Detail",
				"SuperAgent/merchantDetail", Module.MERCHANT,
				"merchant/sideMenuMerchant");		
		
		Merchant merchant = merchantService.loadMerchantByPk(id);
		logger.info("Display Merchant Name " + merchant.getBusinessName());
		logger.info("Display MID " + merchant.getMid().getMid());
		
		
		List<String> midList = new ArrayList<String>();
		String mid=null,motoMid=null,ezypassMid=null,ezywayMid=null,
				ezyrecMid=null,gPayMid=null,umMid=null,umEzyway=null,umEzymoto=null;
		if(merchant.getMid().getMid()!=null) {
			mid=merchant.getMid().getMid();
			midList.add(mid);
		}else if(merchant.getMid().getMotoMid()!=null) {
			motoMid=merchant.getMid().getMotoMid();
			midList.add(motoMid);
		}else if(merchant.getMid().getEzypassMid()!=null) {
			ezypassMid=merchant.getMid().getEzypassMid();
			midList.add(ezypassMid);
		}else if(merchant.getMid().getEzyrecMid()!=null) {
			ezyrecMid=merchant.getMid().getEzyrecMid();
			midList.add(ezyrecMid);
		}else if(merchant.getMid().getEzywayMid()!=null) {
			ezywayMid=merchant.getMid().getEzywayMid();
			midList.add(ezywayMid);
		}
		else if(merchant.getMid().getGpayMid()!=null) {
			gPayMid=merchant.getMid().getGpayMid();
			midList.add(gPayMid);
		}
		else if(merchant.getMid().getUmMid()!=null){
			umMid=merchant.getMid().getUmMid();
			midList.add(umMid);
		}
		else if(merchant.getMid().getUmEzywayMid()!=null){
			umEzyway=merchant.getMid().getUmEzywayMid();
			midList.add(umEzyway);
		}
		else if(merchant.getMid().getUmMotoMid()!=null){
			umEzymoto=merchant.getMid().getUmMotoMid();
			midList.add(umEzymoto);
		}
		
		logger.info("midList::"+midList);
		
		StringBuffer midStr = new StringBuffer();
		int u =0;
	    for(String strMid : midList) {
	    	
	    	if(u == 0) {
	    		midStr.append("\"");
	    		midStr.append(strMid);
	    		midStr.append("\"");
    			u++;
	    	}else {		    	
	    		midStr.append(",\"");
	    		midStr.append(strMid);
	    		midStr.append("\"");
	    	}
	    }
	    logger.info("String of MIDs:  "+midStr);
		
		List<String> productTypeList = new ArrayList<String>();
		
		productTypeList = merchantService.getProductDetails(midStr);
		
		List<String> yearList = new ArrayList<String>();
		yearList = getConsecutiveYears();
		
		PaginationBean<TerminalDetails> paginationBean = new PaginationBean<TerminalDetails>();
		paginationBean.setCurrPage(currPage);
		
		merchantService.loadTerminalDetails(midStr,paginationBean);
		if (paginationBean.getItemList().isEmpty()
				|| paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		PaginationBean<TerminalDetails> paginationBean1 = new PaginationBean<TerminalDetails>();
		paginationBean1.setCurrPage(currPage);
		
		merchantService.searchTxnDetails(merchant,paginationBean1,period,productType,year);
		
		logger.info("paginationBean1.getItemList()"+paginationBean1.getItemList());
		if (paginationBean1.getItemList().isEmpty()
				|| paginationBean1.getItemList() == null
				|| paginationBean1.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("productTypeList", productTypeList);
		model.addAttribute("yearList", yearList);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("paginationBean1", paginationBean1);
		
		return TEMPLATE_SUPER_AGENT;
	}

// subagent summary for SuperAgent Login 18-04-2017
	@RequestMapping(value = { "/subAgentSummary/{currPage}" }, method = RequestMethod.GET)
	public String displaySubAgentList(final Model model,@PathVariable final int currPage,
			java.security.Principal principal) {
		logger.info("about to list all agents");
		PageBean pageBean = new PageBean("Agent", "agent/SubAgentSummary", Module.AGENT, "agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<SubAgent> paginationBean = new PaginationBean<SubAgent>();

		paginationBean.setCurrPage(currPage);
		subAgentService.listSubAgent(paginationBean);
		logger.info("display subagent details:" + currPage);
		logger.info("check paginationBean:" + paginationBean.getCurrPage());
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_SUPER_AGENT;
	}

// transaction summary for superAgent start 18-04-2017
	@RequestMapping(value = { "/transactionSummary/{currPage}" }, method = RequestMethod.GET)
	public String displayTransactionSummary(final Model model,

	@PathVariable final int currPage, final java.security.Principal principal) {
		logger.info("about to list all  transaction");
		PageBean pageBean = new PageBean("transactions list", "SuperAgent/TransactionSummary", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Transaction Summary:" + principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		transactionService.listAllTransaction(paginationBean, null, null,"ALL");
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_SUPER_AGENT;
	}

	// transaction summary search condition for superAgent submenu 18-04-2017
	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String displayTransactionList(final Model model, @RequestParam final String date,
			@RequestParam final String date1,@RequestParam final String txnType,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list all  Transactiona");
		PageBean pageBean = new PageBean("transactions list", "SuperAgent/TransactionSummary", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.listAllTransaction(paginationBean, date, date1,txnType);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_SUPER_AGENT;

	}

	// transaction summary for merchant details in superAgent Transaction
	// subMernu 18-04-2017
	@RequestMapping(value = { "/merchantdetails" }, method = RequestMethod.GET)
	public String displayTransactionDetails(final Model model, @RequestParam final String merchantName,
			java.security.Principal principal,
			@RequestParam final String date/*
											 * ,@RequestParam final String date1
											 */,
			@RequestParam final String city, @PathVariable String txnType, 

	@RequestParam(required = false, defaultValue = "1") final int currPage) {// ,@PathVariable
																				// final
																				// String
																				// merchant)
																				// {

		logger.info("about to list all  Transactions");
		PageBean pageBean = new PageBean("Transactions Details", "SuperAgent/transactionsView", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		ForSettlement merchant = transactionService.loadMerchantName(merchantName);

		logger.info(" All Transaction Summary:" + principal.getName());

		logger.info(" Transaction Summary Merchant Details:" + principal.getName() + ":" + merchantName);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		transactionService.loadMerchantName(paginationBean, merchantName,
				date,txnType); /* merchantName,City */

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("namedd", merchantName);
		model.addAttribute("nameyy", city);
		return TEMPLATE_SUPER_AGENT;
	}

	// transaction summary for superAgent login 18-04-2017
	@RequestMapping(value = { "/agentdetails/{agentName}" }, method = RequestMethod.GET)
	public String displayAgentDetails(final Model model, @PathVariable final String agentName,

	@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list all  Transactions :" + agentName);

		PageBean pageBean = new PageBean("Transactions Details", "SuperAgent/agentMerchantList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		AgentResponseDTO list = new AgentResponseDTO();
		ArrayList<MerchantSettlementDTO> merlist = new ArrayList<MerchantSettlementDTO>();
		PaginationBean<MerchantSettlementDTO> paginationBean = new PaginationBean<MerchantSettlementDTO>();

		paginationBean.setCurrPage(currPage);
		/* String name[] = agentName.split("~"); */
		list = transactionService.loadAgentName(agentName);

		String agName = null;
		String agCode = null;
		String agCity = null;
		String agPhoneNo = null;

		agName = list.getAgName();
		agCode = list.getAgCode();
		agCity = list.getAgCity();
		agPhoneNo = list.getAgPhoneNo();
		merlist = (ArrayList<MerchantSettlementDTO>) list.getMerSettle();
		for (MerchantSettlementDTO msd : merlist) {
			/*
			 * System.out.println("DATA : "+msd.getMerchantAddr1());
			 * System.out.println("DATA : "+msd.getMerchantAddr2());
			 * System.out.println("DATA : "+msd.getMerchantCity());
			 * System.out.println("DATA : "+msd.getMerchantName());
			 * System.out.println("DATA : "+msd.getMerchantPostcode());
			 */
		}
		paginationBean.setItemList(merlist);

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("agentName", agName);
		model.addAttribute("agentCode", agCode);
		model.addAttribute("agentCity", agCity);
		model.addAttribute("agentPhone", agPhoneNo);

		return TEMPLATE_SUPER_AGENT;
	}

	// transaction summary export in superAgent 18-04-2017
	@RequestMapping(value = "/export", method = RequestMethod.GET)
	public ModelAndView getExcel(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam final String txnType,@RequestParam(required = false) String export) {

		logger.info("about to getExcel report data");

		List<ForSettlement> list = transactionService.exportAllTransaction(date, date1,txnType);
		// System.out.println("export test:" + export);
		if (!(export.equals("PDF"))) {

			return new ModelAndView("txnListExcel", "txnList", list);

		} else {
			return new ModelAndView("txnListPdf1", "txnList", list);

		}

	}
	// end trasnaction summary menu methods 18-04-2017

	// superAgent agent volume summary for superAgent Login 18-04-2017
	@RequestMapping(value = { "/agentVolumeSummary/{currPage1}" }, method = RequestMethod.GET)
	public String displayAgenVolumetList(final Model model, @PathVariable final int currPage1,
			java.security.Principal principal, final Long id, String username) {// ,String
																				// agentName)
																				// {
		logger.info("About to list all agents::::::"+currPage1);
		PageBean pageBean = new PageBean("Agent", "SuperAgent/agentVolumeSummary", Module.AGENT, "agent/sideMenuAgent");

		model.addAttribute("pageBean", pageBean);

		List<AgentVolumeData> data = new ArrayList<AgentVolumeData>();
		PaginationBean<AgentVolumeData> paginationBean1 = new PaginationBean<AgentVolumeData>();
		paginationBean1.setCurrPage(currPage1);
		List<AgentVolumeData> listAgentVolumeData = new ArrayList<AgentVolumeData>();
		List<AgentVolumeData> listAgentVolumeData1 = new ArrayList<AgentVolumeData>();
		List<Agent> agent = agentService.loadAgent();
		
		int totalAgent = agent.size();
		int quotient = totalAgent / 10;
		int Remainder = totalAgent % 10;
		int nloops = 0;
		
		logger.info("totalAgent" + agent.size());
		logger.info("quotient" + quotient);
		logger.info("Remainder" + Remainder);
		
		if(Remainder !=0) {
			nloops = quotient +1 ;
			logger.info("added nloops" + nloops);
		}else {
			nloops = quotient;
			logger.info("nloops" + nloops);
		}
		
		ArrayList<Integer> pageNumbers=new ArrayList<Integer>();
		
		if(nloops == 1) {
			pageNumbers.add(1);
		}else if(nloops == 2) {
			pageNumbers.add(1);
			pageNumbers.add(2);
		}else {
			
			for(int i =1 ; i <= 3 ;i++) {
				pageNumbers.add(i);
			}
		}
		
		String offset = null;
		int currPage = currPage1 - 1;
		
		if(currPage == 0) {
			logger.info("First Page :" + currPage);
			offset = String.valueOf(currPage);
		}else {
			logger.info("Page :" + currPage);
			currPage = 10* currPage;
			logger.info("currPage = 10* currPage ->" + currPage);
			/*currPage = currPage - 1;
			logger.info("currPage = currPage - 1 ->" + currPage);
			offset = agent.get(currPage).getId().toString();*/
			offset = String.valueOf(currPage);
		}
		
		if(offset != null) {
			offset = offset ;
			logger.info("Offset Value :"+ offset);
		}else {
			offset = "0";
			logger.info("Offset Default :"+ offset);
		}
		
		List<Agent> agent1 = agentService.loadOffsetAgent(offset);
		
		 StringBuffer str = new StringBuffer();
		int j =0;
		for (Agent t : agent1) {
	    	
	    	if(j == 0) {
	    		str.append("\'");
   			str.append(t.getId());
   			str.append("\'");
   			j++;
	    	}else {		    	
		    	str.append(",\'");
   			str.append(t.getId());
   			str.append("\'");
	    	}
	    }
	    logger.info("String of Agent IDs:  "+str);
		
	    listAgentVolumeData= transactionService.agentVolumeUM(str);
		logger.info("listAgentVolumeData Size : "+listAgentVolumeData.size());
		
		listAgentVolumeData1= transactionService.agentVolumeForsettle(str);
		logger.info("listAgentVolumeData1 Size : "+listAgentVolumeData1.size());
		
		//Method to get current and last three months name
		int cDate = 0;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;
		List<Integer> listMonth = getAllMonth(cDate);
		List<String> date = new ArrayList<String>();
//		System.out.println("listMonth: "+listMonth.size());
		for (int i = 0 ; i < listMonth.size(); i++) {
			date.add(getMonth(cDate));
			cDate--;
		}
//		System.out.println("date: "+date);
		
		
		for (Agent t : agent1) {
			
			AgentVolumeData finalVolumeData = new AgentVolumeData();
			
//			List<String> dateUM = new ArrayList<String>();
			List<String> amountUM = new ArrayList<String>();
//			List<String> dateFOR = new ArrayList<String>();
			List<String> amountFOR = new ArrayList<String>();
			int count = 0;
			int count1 = 0;
			
			for(int i=0 ; i < listAgentVolumeData.size();i++) {
				
//				logger.info("First loop  : " + listAgentVolumeData.get(i).getAgId()); 
				
				if(listAgentVolumeData.get(i).getAgId().equals(t.getId().toString())) {
					
					int date1 = Integer.parseInt(listAgentVolumeData.get(i).getMonth());
//					logger.info("date1  : " + date1 );
//					logger.info("month  : " + listMonth.get(count).intValue());
					while (listMonth.get(count).intValue() != date1) {
//						dateUM.add(getMonth(listMonth.get(count).intValue()));
						amountUM.add("0.00");
						count++;

					}// else{
//					dateUM.add(getMonth(date1));
					Double d = new Double(listAgentVolumeData.get(i).getAmount1());
					d = d / 100;

					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(d);
					amountUM.add(output);
					count++;
					
					
				}
				
			}
			
			
			for (int k = 0; k < listAgentVolumeData1.size(); k++) {
				
//				logger.info("Second loop  : " + listAgentVolumeData1.get(k).getAgId());

				if (listAgentVolumeData1.get(k).getAgId().equals(t.getId().toString())) {

					int date1 = Integer.parseInt(listAgentVolumeData1.get(k).getMonth());
//					logger.info("month  : " + date1 + " : " + listMonth.get(count1).intValue());
					while (listMonth.get(count1).intValue() != date1) {
//						dateFOR.add(getMonth(listMonth.get(count1).intValue()));
						amountFOR.add("0.00");
						count1++;

					} // else{
//					dateFOR.add(getMonth(date1));
					Double d = new Double(listAgentVolumeData1.get(k).getAmount1());
					d = d / 100;

					String pattern = "###0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(d);
					amountFOR.add(output);
					count1++;

				}

			}
			
			// UM Amount String List to Long list
//			logger.info("List UMTxn String Amount ::" + amountUM);
//			logger.info("List UMTxn String Amount Size ::" + amountUM.size());
			
			for (int y = amountUM.size() ;  y < 4 ; y++) {
				amountUM.add("0.00");
			}
		//	logger.info("After List UMTxn String Amount Size ::" + amountUM.size());
			
			List<Double> forLongAmountUM = new ArrayList<Double>(amountUM.size());
			for (String s : amountUM)
				forLongAmountUM.add(Double.parseDouble(s));
//			logger.info("List UMTxn Long Amount ::" + forLongAmountUM);
//			logger.info("List UMTxn Long Amount Size ::" + forLongAmountUM.size());

			// forSettlement Amount String List to Long list
//			logger.info("List forTxn String Amount ::" + amountFOR);
//			logger.info("List forTxn String Amount Size ::" + amountFOR.size());
			
			for (int z = amountFOR.size() ;  z < 4 ;z++) {
				amountFOR.add("0.00");
			}
//			logger.info("After List forTxn String Amount Size ::" + amountFOR.size());
			
			List<Double> forLongAmount = new ArrayList<Double>(amountFOR.size());
			for (String s : amountFOR)
				forLongAmount.add(Double.parseDouble(s));
//			logger.info("List forTxn Long Amount ::" + forLongAmount);
//			logger.info("List forTxn Long Amount Size ::" + forLongAmount.size());
			
			
			
			//Add two long list value and convert string list
			
			int len = 0;
			if(forLongAmountUM.size() > forLongAmount.size()) {
				len = forLongAmountUM.size();
			}else {
				len = forLongAmount.size();
			}
			
			List<Double> newAmount = new ArrayList<Double>(len);
			for (int i =0; i< len ; i++) {
				Double Amount = forLongAmount.get(i) + forLongAmountUM.get(i);
//				logger.info("List Amount ::"+Amount);
				newAmount.add(Amount);
			}
			
//			logger.info("List newAmount Long Amount ::"+newAmount);
//			logger.info("List newAmount Long Amount Size ::"+newAmount.size());
			
			
			List<String> newAmountString = new ArrayList<String>(newAmount.size());
			for(Double s : newAmount) 
				newAmountString.add(String.valueOf(s));
				
				
				//To Check agent txn of last four months
				Double Total = 0.00;
				for (int i =0; i< newAmount.size() ; i++) {
					Total = Total + newAmount.get(i);
				}
				
				logger.info("Total ::"+Total);
				
				if(Total == 0.00) {
					finalVolumeData.setTxnPresent("No");
				}else {
					finalVolumeData.setTxnPresent("Yes");
				}
			
			
			finalVolumeData.setAgId(t.getId().toString());
			finalVolumeData.setAgentName(t.getFirstName());
			finalVolumeData.setAgentDet(t.getId().toString() + "~AGENT~" +t.getFirstName());
			finalVolumeData.setAmount(newAmountString);
			finalVolumeData.setDate(date);
			
			data.add(finalVolumeData);
				
		}

		paginationBean1.setItemList(data);

		model.addAttribute("paginationBean", paginationBean1);
		model.addAttribute("offset", currPage1);
		model.addAttribute("pageNumbers", pageNumbers);
		model.addAttribute("lastPage", nloops);

		return TEMPLATE_SUPER_AGENT;
	}
	
	private static List<Integer> getAllMonth(int month) {
		System.out.println("month debug... "+month);
		List<Integer> listMonth = new ArrayList<Integer>();
		for (int i = 0; i <4 ; i++) {
			if (month == 0) {
				System.out.println("debug 0");
				listMonth.add(12);
			} else if (month == -1) {
				System.out.println("debug -1");
				listMonth.add(11);
			} else if (month == -2) {
				System.out.println("debug -2");
				listMonth.add(10);
			} else {
				System.out.println("debug else ");
				listMonth.add(month);
			}
			month--;
		}
		return listMonth;
	}

	// merchant volume details in superAgent login
	/*@RequestMapping(value = { "/merchantvolume/{merchantName}" }, method = RequestMethod.GET)
	public String displayMerchantVolume(final Model model, @PathVariable final String merchantName,
			java.security.Principal principal, @RequestParam(required = false, defaultValue = "1") final int currPage) {

		PageBean pageBean = new PageBean("Agent", "agent/merchantVolumeSummary", Module.AGENT, "agent/sideMenuAgent");

		List<AgentVolumeData> data = new ArrayList<AgentVolumeData>();
		Agent agent = agentService.loadAgentbyMailId(principal.getName());
		List<SubAgent> subagent = subAgentService.loadSubAgentbyId(agent);

		model.addAttribute("pageBean", pageBean);

		List<AgentVolumeData> listMerchantVolumeData = new ArrayList<AgentVolumeData>();

		PaginationBean<AgentVolumeData> paginationBean1 = new PaginationBean<AgentVolumeData>();
		paginationBean1.setCurrPage(currPage);

		listMerchantVolumeData = transactionService.merchantVolumeDataByAgent(merchantName,agent.getId());

		for (AgentVolumeData merchantVolumeData : listMerchantVolumeData) {

			// logger.info("Controller merchantId :
			// "+merchantVolumeData.getAgId());
			logger.info("Controller agentName : " + merchantVolumeData.getAgentName());
			// logger.info("Controller agentDet :
			// "+merchantVolumeData.getAgentDet());
			logger.info("Controller amount : " + merchantVolumeData.getAmount());
			logger.info("Controller date : " + merchantVolumeData.getDate());

			data.add(merchantVolumeData);

		}

		paginationBean1.setItemList(data);

		model.addAttribute("paginationBean", paginationBean1);
		return TEMPLATE_SUPER_AGENT;

	}*/
	
	@RequestMapping(value={"/merchantvolume/{merchantName}/{index}"},method=RequestMethod.GET)
	public String displayMerchantVolume(final Model model, @PathVariable String merchantName,
			 @PathVariable String index,
			  java.security.Principal principal,@RequestParam(required = false, defaultValue = "1") final int currPage)
	{
		logger.info("agent check-------------"+merchantName);
		int offSet=100;
		PageBean pageBean = new PageBean("Agent", "agent/merchantVolumeSummary", Module.AGENT,
					"agent/sideMenuAgent");
		
		List<AgentVolumeData> data = new ArrayList<AgentVolumeData>();
		Agent agent = agentService.loadAgentbyMailId(principal.getName());
		List<SubAgent> subagent = subAgentService.loadSubAgentbyId(agent);

		model.addAttribute("pageBean", pageBean);

		List<AgentVolumeData> listMerchantVolumeData = new ArrayList<AgentVolumeData>();

		PaginationBean<AgentVolumeData> paginationBean1 = new PaginationBean<AgentVolumeData>();
		paginationBean1.setCurrPage(currPage);
		
		

//		/float recordCount=Float.parseFloat(transactionService.merchantCount(merchantName)),recordavg=0.0f;
		
		
		int recordCount=Integer.parseInt(transactionService.merchantCount(merchantName)),recordavg=0;
		logger.info("recordCount: "+recordCount);
		
		/*if(recordCount >= 10)
			recordavg=Math.round(recordCount/10);
		else
			recordavg=1.0f;
		
		recordavg = (recordCount >= offSet) ? Math.round(recordCount/offSet): 1.0f;
		System.out.println("average offset: "+recordavg);

		List<String> addList=new ArrayList<String>();
		for(int i=1;i<=recordavg;i++) {
			addList.add(String.valueOf(i));
		}
		*/
		
		 recordavg = (recordCount%10)>0?(recordCount/10)+1:(recordCount/10) ;
			System.out.println("Mod : "+recordavg);

			List<String> addList=new ArrayList<String>();
			for(int i=1;i<=recordavg;i++) {
				addList.add(String.valueOf(i));
			}
		
		
		logger.info("addList: "+addList.toString());
		logger.info("agent name: "+merchantName);
		//listMerchantVolumeData= transactionService.merchantVolumeDataByAgent(merchantName,agent.getId());
		
		logger.info("offset data--debug...."+String.valueOf((Integer.parseInt(index)-1)*offSet));
		/*listMerchantVolumeData= transactionService.merchantVolumeDataByAgent(merchantName,agent.getId(),
				String.valueOf((Integer.parseInt(index)-1)*10));
		*/
		listMerchantVolumeData= transactionService.merchantVolumeDataInTxnSummary(merchantName,agent.getId(),
				String.valueOf((Integer.parseInt(index)-1)*10));
		
		
		logger.info("data added : "+listMerchantVolumeData);
		paginationBean1.setItemList(listMerchantVolumeData);
		
		model.addAttribute("count", addList);
		model.addAttribute("agentName", merchantName);
		model.addAttribute("paginationBean", paginationBean1);
		model.addAttribute("offset", index);
		model.addAttribute("merchantCount", recordavg);
		return TEMPLATE_SUPER_AGENT;

	}

	public String getMonth(int m) {

		// System.out.println(" Data :"+m);
		String mon = "";
		switch (m) {
		case 1:
			mon = "JAN";
			break;
		case 2:
			mon = "FEB";
			break;
		case 3:
			mon = "MAR";
			break;
		case 4:
			mon = "APR";
			break;
		case 5:
			mon = "MAY";
			break;
		case 6:
			mon = "JUN";
			break;
		case 7:
			mon = "JUL";
			break;
		case 8:
			mon = "AUG";
			break;
		case 9:
			mon = "SEP";
			break;
		case 10:
			mon = "OCT";
			break;
		case 11:
			mon = "NOV";
			break;
		case 12:
			mon = "DEC";
			break;

		default:
			mon = "";
			break;
		}

		return mon;

	}
	
	private static List<String> getConsecutiveYears() {
		List<String> yearsList = new ArrayList<String>();
		String currentYear = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
		yearsList.add(currentYear);
		Calendar prevYear = Calendar.getInstance();
		
		for(int i=1; i<5;i++) {	    
	    prevYear.add(Calendar.YEAR, -1);
	    int year= prevYear.get(Calendar.YEAR);
	    String yearStr = Integer.toString(year);
	    yearsList.add(yearStr);
		}
		
		 return yearsList;
	}
	// agent volume submenu end methods 18-04-2017

	// All transaction summary for SuperAgent start
	@RequestMapping(value = { "/allTransactionSummary/{currPage}" }, method = RequestMethod.GET)
	public String displayTransactionSummary(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal, @RequestParam(required = false) final String date,
			@RequestParam(required = false) final String date1, @RequestParam(required = false) final String status,
			@RequestParam(required = true, defaultValue = "1") final String responseData) {
		logger.info("about to list all  transaction");
		PageBean pageBean = new PageBean("transactions list", "SuperAgent/allTransactionSummary", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		logger.info(" All Transaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);

		String dat = null;
		String dat1 = null;

		String status1 = null;
		if (date != null) {// || !date.equals("1")){
			dat = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			logger.info("date1: " + dat);
		}

		if (date1 != null) {// || !date1.equals("1")){
			dat1 = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat1));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			logger.info("date2 : " + dat1);
		}
		if (status != null) {// || !(status.equals(""))){

			status1 = status;
		}
		transactionService.listAllTransactionDetails(paginationBean, dat, dat1, status1);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		// Start New Changes
		for (ForSettlement forSettlement : paginationBean.getItemList()) {
			if (!forSettlement.getStatus().equals("CT")) {
				TerminalDetails terminalDetails = transactionService
						.getTerminalDetailsByTid(forSettlement.getTid().toString());
	
				if (terminalDetails != null) {
					// logger.info("Contact :"+terminalDetails.getContactName());
					forSettlement.setMerchantName(terminalDetails.getContactName());
				}
			}
			if (forSettlement.getAmount() != null) {
				double amount = 0;
				amount = Double.parseDouble(forSettlement.getAmount()) / 100;
				// forSettlement.setAmount(amount+"0");
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				// System.out.println(" Amount :"+output);
				forSettlement.setAmount(output);
			}
			if (forSettlement.getStatus().equals("S")) {
				forSettlement.setStatus("SETTLED");
			}
			if (forSettlement.getStatus().equals("P")) {
				forSettlement.setStatus("PENDING");
			}
			if (forSettlement.getStatus().equals("A")) {
				forSettlement.setStatus("NOT SETTLED");
			}
			if (forSettlement.getStatus().equals("C")) {
				forSettlement.setStatus("CANCELLED");
			}
			if (forSettlement.getStatus().equals("R")) {
				forSettlement.setStatus("REVERSAL");
			}
			if (forSettlement.getStatus().equals("CT")) {
				forSettlement.setStatus("CASH");
			}
			if ( forSettlement.getTime() != null) {
				try {
					String sd = forSettlement.getTimeStamp();
					String rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sd));
					String rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
					// forSettlement.setDate(rd+"-"+forSettlement.getTxnYear());
					forSettlement.setDate(rd);
					forSettlement.setTime(rt);
				} catch (ParseException e) {
				}

			}

		}

		// End New Changes

		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_SUPER_AGENT;
	}

	// All transaction controller search condition for superAgent 18-04-2017
	 @RequestMapping(value = { "/search1" }, method = RequestMethod.GET)
		public String displayAllTransactionList(final Model model, 
				@RequestParam(required = false ) final String date,
			@RequestParam(required = false) final String date1,
			@RequestParam(required = false) final String status,
			@RequestParam(required = false, defaultValue = "1") final int currPage) throws ParseException {
		logger.info("about to list all  Transactiona");
		
		String dat = null;
		String dat1 = null;
		String status1 = null;
		if ((date != null && date1 != null) && (!date.isEmpty() && !date1.isEmpty())) {
			
			dat = date;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat));
			
			dat1 = date1;
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy").parse(dat1));
			
		}
		
		if(status != null){// || status.equals("F")){
		/*	status1 = null;
			System.out.println(" Data Status null");
		}else{*/
			status1 = status;
		
		}
		PageBean pageBean = new PageBean("transactions list", "SuperAgent/allTransactionSummary", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		
			paginationBean.setCurrPage(currPage);
			
			transactionService.listAllTransactionDetails(paginationBean,dat,dat1,status1);
			model.addAttribute("date", date);
			model.addAttribute("date1", date1);
			model.addAttribute("status", status1);
		/*transactionService.listSearchTransactionDetails(paginationBean, dat, dat1);*/
		
		if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null)
		{
			model.addAttribute("responseData", "No Records found"); //table response
			
		}else {
			model.addAttribute("responseData", null);
		}
		
		//Start New Changes
				for(ForSettlement forSettlement:paginationBean.getItemList()){
					if (!forSettlement.getStatus().equals("CT")) {
						TerminalDetails terminalDetails = transactionService
								.getTerminalDetailsByTid(forSettlement.getTid().toString());
			
						if (terminalDetails != null) {
							// logger.info("Contact :"+terminalDetails.getContactName());
							forSettlement.setMerchantName(terminalDetails.getContactName());
						}
					}
					if(forSettlement.getAmount()!=null){
						double amount=0;
						amount=Double.parseDouble(forSettlement.getAmount())/100;
						//forSettlement.setAmount(amount+"0");
						String pattern = "#,##0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						String output = myFormatter.format(amount);
						//System.out.println(" Amount :"+output);
						forSettlement.setAmount(output);
					}
					if(forSettlement.getStatus().equals("S")){
						forSettlement.setStatus("SETTLED");
					}if(forSettlement.getStatus().equals("P")){
						forSettlement.setStatus("PENDING");
					}
					if(forSettlement.getStatus().equals("A")){
						forSettlement.setStatus("NOT SETTLED");
					}
					if(forSettlement.getStatus().equals("C")){
						forSettlement.setStatus("CANCELLED");
					}
					if(forSettlement.getStatus().equals("R")){
						forSettlement.setStatus("REVERSAL");
					}
					if(forSettlement.getStatus().equals("CT")){
						forSettlement.setStatus("CASH");
					}
					
					if(forSettlement.getDate()!=null&&forSettlement.getTime()!=null){
						try {
							String sd=forSettlement.getTimeStamp();
							//String rd = new SimpleDateFormat("dd-MMM").format(new SimpleDateFormat("MMdd").parse(sd));
							String rd = new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sd));
							String rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
							//forSettlement.setDate(rd+"-"+forSettlement.getTxnYear());
							forSettlement.setDate(rd);
							forSettlement.setTime(rt);
						} catch (ParseException e) {}

					}			
					
				}
		
				//End New Changes	
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_SUPER_AGENT;
		
		}

	//}

	// All transaction summary export
	@RequestMapping(value = "/allTransaction", method = RequestMethod.GET)
	public ModelAndView getExport(@RequestParam final String date, @RequestParam final String date1,
			@RequestParam final String txnType,@RequestParam(required = false) String export) {

		logger.info("about to getExcel report data");

		List<ForSettlement> list = transactionService.exportAllTransaction(date, date1,txnType);
		// System.out.println("export test:" + export);
		if (!(export.equals("PDF"))) {

			return new ModelAndView("txnListExcel", "txnList", list);

		} else {
			return new ModelAndView("txnListPdf1", "txnList", list);
		}
	}

	
	//dashboard 
	@RequestMapping(value = { "/superAgent/dashBoard" }, method = RequestMethod.GET)
	public String webDashBoard(final Model model,final java.security.Principal principal,HttpServletRequest request) {
		logger.info("Login Person in dash Board:"+principal.getName());
		PageBean pageBean = new PageBean("DashBoard", 
				"SuperAgent/dashbrd",
	
				Module.ADMIN, "admin/sideMenuBankUser");

		//List<DashBoardData> listDBD = new ArrayList<DashBoardData>();
		model.addAttribute("pageBean", pageBean);
		
		//Total Device
		
		int totalDevice = dashBoardService.getTotalDevice();
		
		model.addAttribute("totalDevice", totalDevice);
		
		//Total transaction Current month
		
		String totalTxn = dashBoardService.getCurrentMonthTxn();
		
		model.addAttribute("totalTxn", totalTxn);
		
		//Total Merchant
		
		String totalMerchant = dashBoardService.getTotalMerchant();
		
		model.addAttribute("totalMerchant", totalMerchant);
		
		PaginationBean<DashBoardData> paginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check 5 recent txn Amount Data ");
		dashBoardService.getLastFiveTxn(paginationBean);
		
		logger.info(" received 5 recent  txn Amount Data ");
		model.addAttribute("fiveTxnList", paginationBean);	
		
		logger.info("check dashboard details:");
        List<ThreeMonthTxnData> data = new ArrayList<ThreeMonthTxnData>();
	//new changes for bar chart 05072017
	//List<ThreeMonthTxnData> txnCountData = new ArrayList<ThreeMonthTxnData>();
	
	
	PaginationBean<ThreeMonthTxnData> paginationBean1 = new PaginationBean<ThreeMonthTxnData>();
	//paginationBean1.setCurrPage(currPage);
	//logger.info("check dashboard details787878:");
	List<ThreeMonthTxnData> txnListData = new ArrayList<ThreeMonthTxnData>();
	txnListData= dashBoardService.getThreeMonthTxn(); 
	//logger.info("check dashboard details457454:");
	
	logger.info("check dashboard txn details:" + txnListData.size());
	//for(ThreeMonthTxnData txnMonthData: txnCountData){
	for(ThreeMonthTxnData txnMonthData: txnListData){
		
		//logger.info("check monthly count:" + txnMonthData );
		
		//logger.info("Controller agentDet : "+txnMonthData.getCount());
		//logger.info("Controller amount : "+txnMonthData.getAmount());
		//logger.info("Controller date : "+txnMonthData.getDate1());
		
		
		String a3="\"";
		for(String a1:txnMonthData.getDate1()){
			a3=a3+a1;
			a3=a3+"\",\"";
    	}
    	//System.out.println("Data : "+a3);
    	a3=a3+"\"";
    	//System.out.println("Data : "+a3);
    	a3=a3.replace(",\"\"", "");
    	//System.out.println("Data : "+a3);
    	txnMonthData.setMonth(a3);
    	
    	String b3="";
		for(String b1:txnMonthData.getAmount()){
			b3=b3+b1;
			b3=b3+",";
    	}
    	//System.out.println("Data : "+b3);
    	b3=b3+",";
    	//System.out.println("Data : "+b3);
    	b3=b3.replace(",,", "");
    	//System.out.println("Data : "+b3);
    	txnMonthData.setAmountData(b3);
    	
    	String c3="";
		for(String c1:txnMonthData.getCount()){
			c3=c3+c1;
			c3=c3+",";
    	}
    	//System.out.println("Data : "+c3);
    	c3=c3+",";
    	//System.out.println("Data : "+c3);
    	c3=c3.replace(",,", "");
    	//System.out.println("Data : "+c3);
    	txnMonthData.setCountData(c3);
		 model.addAttribute("threeMonthTxn", txnMonthData);
		 
		
		data.add(txnMonthData);
	
	}
	//End Agent 
	
	paginationBean1.setItemList(data);

	
	model.addAttribute("paginationBean", paginationBean1);
		
		return TEMPLATE_SUPER_AGENT;
	}
	
	// end all transaction summary menus

	
	
	
	// merchant summary search condition 08082017
	
	
	@RequestMapping(value = { "/merchantSearch" }, method = RequestMethod.GET)
	public String displaySearchMerchant(final Model model,
			
			@RequestParam(required = false, defaultValue = "1") final int currPage,@RequestParam final String date,
			@RequestParam final String date1) {
		logger.info("about to search Merchant based on search String:: " + date);
		
		logger.info("about to search Merchant based on search String:: " + date1);

		PageBean pageBean = new PageBean("Search Merchant", "merchant/merchantList", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();

		paginationBean.setCurrPage(currPage);

		merchantService.listMerchantSearch(paginationBean,date,date1,null);

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_SUPER_AGENT;
	}

	
	
	@RequestMapping(value = "/merchantExport", method = RequestMethod.GET)
	public ModelAndView getPdf(@RequestParam  final String date,@RequestParam  final String date1,
			@RequestParam (required = false ) String export) {
	/*public ModelAndView getExcel(final Model model, @RequestParam  final String date,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {*/
		//List animalList = animalService.getAnimalList();
		logger.info("about to getExcel report data");
		/*PageBean pageBean = new PageBean("transactions list", "transaction/transactionList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
         model.addAttribute("pageBean", pageBean);

  		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
  		paginationBean.setCurrPage(currPage);*/
		//System.out.println(" Controller Date :"+ date);
		
		//PaginationBean<MobileUser> paginationBean = new PaginationBean<MobileUser>();
		//paginationBean.setCurrPage(currPage);
		//mobileUserService.listMobileUsers(paginationBean,date,date1);
		
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();

		//paginationBean.setCurrPage(currPage);

		merchantService.listMerchantSearch(paginationBean,date,date1,null);
		
		//List<Merchant> list =merchantService.merchantSummaryExport(date,date1);
		List<Merchant> list =paginationBean.getItemList();
		logger.info("check mobile user from date:" + date);
		
		logger.info("check mobile user to date:" + date1);
		System.out.println("export test:" + export);
		if(!(export.equals("PDF"))){
			
		return new ModelAndView("txnMerchantExcel", "txnList", list);

		}else{
			return new ModelAndView("txnMerchantPdf", "txnList", list);
		}
	}
	@RequestMapping(value = { "/txnagentdashBoard" }, method = RequestMethod.GET)
	public String webAgentTxnDashBoard(final Model model,final java.security.Principal principal,HttpServletRequest request) {
		logger.info("Login Person in dash Board:"+principal.getName());
		PageBean pageBean = new PageBean("Dash Board", "hotelMerchants/dashboard",
				Module.MERCHANT, "admin/sideMenuBankUser");

		model.addAttribute("pageBean", pageBean);
		 HttpSession session = request.getSession();
		 Agent agent = agentService.loadAgent(principal.getName());
		   // String username = (String)request.getAttribute("un");
		 logger.info("display agent web : " + agent.getFirstName());
		 session.setAttribute("agentUserName",  agent.getFirstName());
		 
		    int totalDevice = dashBoardService.getAgentTotalDevice(agent.getId());
		    
		    model.addAttribute("totalDevice", totalDevice);
		    
		   /* String totalTxn = dashBoardService.getAgentCurrentMonthTxn(agent.getId());*/
		    
		    String totalTxn = dashBoardService.getAgentCurrentMonthTxnByAgID(agent.getId());
			
		    
		    logger.info("totalTxn: " + totalTxn);
			model.addAttribute("totalTxn", totalTxn);
			
			String totalMerchant = dashBoardService.getAgentTotalMerchant(agent.getId());
			
			model.addAttribute("totalMerchant", totalMerchant);
			logger.info("totalMerchant: " + totalMerchant);
			
			PaginationBean<DashBoardData> paginationBean = new PaginationBean<DashBoardData>();
			logger.info(" check 5 recent txn Amount Data ");
			List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
			
			List<MID> ids = new ArrayList<MID>();
		    for(Merchant t: merchant1) {
		    	ids.add(t.getMid());
		    }
		    
		    logger.info("ids:  "+ids);
		    
			StringBuffer str = new StringBuffer();
		    StringBuffer strUm = new StringBuffer();
		    List<String> midList = new ArrayList<String>();
		    List<String> ummidList = new ArrayList<String>();
		    for(MID mi : ids) {
		    	
		    	if(mi.getMid() != null) {
		    		if(!mi.getMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMid());
		    			str.append("\",");*/
		    			midList.add(mi.getMid());
		    			
		    		}
		    		
		    	}
		    	if(mi.getMotoMid() != null) {
		    		if(!mi.getMotoMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			midList.add(mi.getMotoMid());
		    		}
		    		
		    	}
		    	if(mi.getEzypassMid() != null) {
		    		if(!mi.getEzypassMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			midList.add(mi.getEzypassMid());
		    		}
		    		
		    	}
		    	
		    	if(mi.getEzywayMid() != null) {
		    		if(!mi.getEzywayMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			midList.add(mi.getEzywayMid());
		    		}
		    		
		    	}
		    	
		    	if(mi.getEzyrecMid() != null) {
		    		if(!mi.getEzyrecMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			midList.add(mi.getEzyrecMid());
		    		}
		    		
		    	}
		    	
		    	if(mi.getUmMid() != null) {
		    		if(!mi.getUmMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			midList.add(mi.getUmMid());
		    		}
		    		
		    	}
		    	
		    	if(mi.getUmMotoMid() != null) {
		    		if(!mi.getUmMotoMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			ummidList.add(mi.getUmMotoMid());
		    		}
		    		
		    	}
		    	if(mi.getUmEzywayMid() != null) {
		    		if(!mi.getUmEzywayMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			ummidList.add(mi.getUmEzywayMid());
		    		}
		    		
		    	}
		    	
		    }
		    int u =0;
		    for(String strMid : midList) {
		    	
		    	if(u == 0) {
		    		str.append("\"");
	    			str.append(strMid);
	    			str.append("\"");
	    			u++;
		    	}else {		    	
			    	str.append(",\"");
	    			str.append(strMid);
	    			str.append("\"");
		    	}
		    }
		    logger.info("String of MIDs:  "+str);
		    
		    
		    int v=0;
		    for(String strMid : ummidList) {

		    	if(v == 0) {
		    		strUm.append("\"");
		    		strUm.append(strMid);
		    		strUm.append("\"");
	    			v++;
		    	}else {		    	
		    		strUm.append(",\"");
		    		strUm.append(strMid);
		    		strUm.append("\"");
		    	}
		    
		    }
		    
		    logger.info("String of UMMIDs:  "+strUm);
		    dashBoardService.getHotelMerchantFiveTxn(paginationBean,str,strUm);
			
			logger.info(" received 5 recent  txn Amount Data ");
			model.addAttribute("fiveTxnList", paginationBean);
			
			
			List<SixMonthTxnData> sixMonData = new ArrayList<SixMonthTxnData>(); 
			 sixMonData= dashBoardService.getHotelMerchantSixMonTxn(agent.getId(),strUm); 
			
			
			 PaginationBean<SixMonthTxnData> paginationBean1 = new PaginationBean<SixMonthTxnData>();
				List<SixMonthTxnData> data = new ArrayList<SixMonthTxnData>();
					for(SixMonthTxnData txnMonthData: sixMonData){
					
					//logger.info("check monthly count12333:" + txnMonthData );
					
					//logger.info("Controller agentDet123333 : "+txnMonthData.getCount());
					//logger.info("Controller amount23333 : "+txnMonthData.getAmount());
					//logger.info("Controller date33333 : "+txnMonthData.getDate1());
					String a3="\"";
					for(String a1:txnMonthData.getDate1()){
						a3=a3+a1;
		   			a3=a3+"\",\"";
			    	}
			    	//System.out.println("Data : "+a3);
			    	a3=a3+"\"";
			    	//System.out.println("Data : "+a3);
			    	a3=a3.replace(",\"\"", "");
			    	//System.out.println("Data : "+a3);
			    	txnMonthData.setMonth(a3);
			    	
			    	String b3="";
					for(String b1:txnMonthData.getAmount()){
						b3=b3+b1;
		   			b3=b3+",";
			    	}
			    	//System.out.println("Data : "+b3);
			    	b3=b3+",";
			    	//System.out.println("Data : "+b3);
			    	b3=b3.replace(",,", "");
			    	//System.out.println("Data : "+b3);
			    	txnMonthData.setAmountData(b3);
			    	
			    	
			    	
			    	String c3="";
					for(String c1:txnMonthData.getCount()){
						c3=c3+c1;
		   			c3=c3+",";
			    	}
			    	//System.out.println("Data : "+c3);
			    	c3=c3+",";
			    	//System.out.println("Data : "+c3);
			    	c3=c3.replace(",,", "");
			    	//System.out.println("Data : "+c3);
			    	txnMonthData.setCountData(c3); 
					
					
					
					model.addAttribute("threeMonthTxn", txnMonthData);
					 
					
					data.add(txnMonthData);
				
				}
				//End Agent 

				String a1=null,a2=null,a3=null,a4=null,a5=null,a6=null;
				 Float min = 0.0f,max=0.0f;
				for(SixMonthTxnData t: sixMonData) {
		   		logger.info(" check= Amount Data "+t.getAmountData());
		   		String[] amt=t.getAmountData().split(",");
		   		a1=amt[0];
		   		a2=amt[1];
		   		a3=amt[2];
		   		a4=amt[3];
		   		a5=amt[4];
		   		a6=amt[5];
		   		Float[] a= {Float.valueOf(a1),Float.valueOf(a2),Float.valueOf(a3)
		   				,Float.valueOf(a4),Float.valueOf(a5),Float.valueOf(a6)};

		   		Arrays.sort(a);
		   		
		   		 min = a[0]; //  assume first elements as smallest number
		   		 max = a[0]; //  assume first elements as largest number
		   		 for (int i = 1; i < a.length; i++)  // iterate for loop from arrays 1st index (second element)
		   			{
		   				if (a[i] > max) 
		   				{
		   					max = a[i];
		   				}
		   				if (a[i] < min) 
		   				{
		   					min = a[i];
		   				}
		   			}
		   		 
		   		
		   	}
				
				 logger.info("min: "+max +" max: "+min);
				
				
				//Math.round(1004/1000)*1000
				int rounded=DashboardAmount.roundNum(Math.round(max));
				logger.info("rounded value : "+rounded );
				model.addAttribute("stepsize",  rounded/10);
				model.addAttribute("max", rounded);
				paginationBean1.setItemList(data);

				AuditTrail auditTrail = agentService.updateAuditTrailByAgent(
						principal.getName(), principal.getName(), "login");

				if (auditTrail != null) {
					logger.info("Logged in by Hotel Merchant Agent : " + principal.getName());
				}
		
		model.addAttribute("paginationBean", paginationBean1);
		session.setAttribute("userName",  agent.getUsername()); 
		
		session.setAttribute("userRole",  "SUPERAGENT_USER");
		
		return TEMPLATE_HOTELMERCHANT;
	}
	
	@RequestMapping(value = { "/ChangeMerchantPasswordByAgent" }, method = RequestMethod.GET)
	public String changeMerchantPasswordByAgent(final Model model,
	final java.security.Principal principal, HttpServletRequest request) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("Merchant",
				"hotelMerchants/ChangePassword/MerchantDetails", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		 HttpSession session = request.getSession();
		 Agent agent = agentService.loadAgent(principal.getName());
		   
		 logger.info("display agent web : " + agent.getFirstName());

		
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("merchant1", merchant1);

		return TEMPLATE_HOTELMERCHANT;
	}
	
	@RequestMapping(value = { "/MerchantDetailsToChangePassword" }, method = RequestMethod.GET)
	public String dispTermsandConditons(final Model model,HttpServletRequest request,
			@RequestParam("mid") Long id,/*
										 * @ModelAttribute("merchantuser") final
										 * MerchtCustMail regMobileUser,
										 */
			final java.security.Principal principal) {
		logger.info("/MerchantDetailsToChangePassword: " + id);
		
		
		
		Merchant merchant = merchantService.loadMerchantByPk(id);

		PageBean pageBean = new PageBean("Merchant",
				"hotelMerchants/ChangePassword/MerchantDetails", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		 HttpSession session = request.getSession();
		 Agent agent = agentService.loadAgent(principal.getName());
		   
		 logger.info("display agent web : " + agent.getFirstName());
		logger.info("admin login person:" + principal.getName());

		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
		List<MobileUser> mobileuser = promotionService
				.loadMobileUserDetails(merchant.getId());
		
		model.addAttribute("pageBean", pageBean);
		
		model.addAttribute("merchant", merchant);
		model.addAttribute("merchant1", merchant1);
		
		model.addAttribute("mobileuser", mobileuser);

		return TEMPLATE_HOTELMERCHANT;
	}
	
	@RequestMapping(value = { "/ChangeMerchPass" }, method = RequestMethod.GET)
	public String MerchantChangePasswordByAdmin(final Model model,
			final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("id") Long id,
			//@RequestParam("mid") String mid,
			//@RequestParam("motoMid") String motoMid,
			@RequestParam("merchantPass") String merchantPass,
			@RequestParam("businessName") String businessName) {

		logger.info("check merchant id : " + id);
	//	logger.info("check mid : " + mid);
		Merchant merchant = merchantService.loadMerchantbyid(id);
		// logger.info("admin merchant new password "+merchantService.generatePassword());
		// logger.info("admin  merchant  uname "+merchant.getUsername());
		merchant.setPassword(merchantPass);
		// logger.info("merchant getpass: "+merchant.getPassword());
		merchant = merchantService
				.changeMerchantPassWordByAdminManualy(merchant);
		logger.info("display merchant name:" + merchant.getUsername()
				+ "first name: " + merchant.getFirstName());

		if (merchant != null) {

			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(
					merchant.getUsername(), principal.getName(),
					"resetPassword");
			if (auditTrail.getUsername() != null) {
				logger.info("Merchant :" + auditTrail.getUsername()
						+ " Password Successfully changed by agent: "
						+ auditTrail.getModifiedBy());
				;
			}
		}

		PageBean pageBean = new PageBean("Merchant",
				"hotelMerchants/ChangePassword/PasswordChangedSuccess",
				Module.MERCHANT, "merchant/sideMenuMerchant");
		model.addAttribute("msg",
				"Merchant Password Changed Successfully by Agent '"
						+ principal.getName() + "'");
		// model.addAttribute("mid", mid);
		model.addAttribute("merchant", merchant);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("newPassword", merchantPass);
		return TEMPLATE_HOTELMERCHANT;
	}

	@RequestMapping(value = { "/ChangeMobileuserPass" }, method = RequestMethod.GET)
	public String MobileuserChangePasswordByAdmin(final Model model,
			final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("merchant_Id") Long merchant_Id,
			@RequestParam("mobileuserPass") String mobileuserPass,
			
			@RequestParam("mobileUserId") Long id) {
		logger.info("check : /ChangeMobileuserPass");
		
		String mid=null;
		
		logger.info(merchant_Id + " : " + id);
		Merchant merchant = merchantService.loadMerchantbyid(merchant_Id);

		if(merchant.getMid().getMid()!=null) {
			mid=merchant.getMid().getMid();
		}else if(merchant.getMid().getMotoMid()!=null) {
			mid=merchant.getMid().getMotoMid();
		}else {
			mid=merchant.getMid().getEzypassMid();
		}
		
		logger.info(" merchant username " + merchant.getUsername());

		MobileUser mobileuser = mobileUserService.loadMobileUserByPk(id);
		logger.info("mobileuser name: " + mobileuser.getUsername());
		mobileuser.setPassword(mobileuserPass);

		mobileuser = merchantService
				.changeMobileuserPassWordByAdminManualy(mobileuser);

		if (mobileuser != null) {

			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(
					merchant.getUsername(), principal.getName(),
					"resetPassword");
			if (auditTrail.getUsername() != null) {
				logger.info("Merchant :" + auditTrail.getUsername()
						+ " Password Successfully changed by Agent: "
						+ auditTrail.getModifiedBy());
				;
			}
		}

		PageBean pageBean = new PageBean("Merchant",
				"hotelMerchants/ChangePassword/PasswordChangedSuccess",
				Module.MERCHANT, "merchant/sideMenuMerchant");
		model.addAttribute("msg",
				"MobileUser Password Changed Successfully by Agent "
						+ principal.getName());
		model.addAttribute("mid", mid);
		model.addAttribute("mobileUserName", mobileuser.getUsername());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("newPassword", mobileuserPass);
		return TEMPLATE_HOTELMERCHANT;
	}
	
	@RequestMapping(value = { "/transaction/umMotoList/{currPage}" }, method = RequestMethod.GET)
	public String umMotoList(final Model model,HttpServletRequest request,
	@PathVariable final int currPage, final java.security.Principal principal) {
		logger.info(" UM-MOTO Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list",
				"hotelMerchants/transaction/transactionUMMotoList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		 HttpSession session = request.getSession();
		 Agent agent = agentService.loadAgent(principal.getName());
		   
		 logger.info("display agent web : " + agent.getFirstName());

		
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
		
		List<MID> ids = new ArrayList<MID>();
	    for(Merchant t: merchant1) {
	    	ids.add(t.getMid());
	    }
	    
	    logger.info("ids:  "+ids);
	    
	    List<String> ummotomidList = new ArrayList<String>();
	    
	    for(MID mi : ids) {
	    	
	    	if(mi.getUmMotoMid() != null) {
	    		if(!mi.getUmMotoMid().isEmpty()) {
	    			ummotomidList.add(mi.getUmMotoMid());
	    		}
	    		
	    	}
	    	
	    }
		
	    logger.info("ummotomidList:  "+ummotomidList);
	    StringBuffer strUm = new StringBuffer();
	    int j=0;
	    for(String strMid : ummotomidList) {

	    	if(j == 0) {
	    		strUm.append("\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
    			j++;
	    	}else {		    	
	    		strUm.append(",\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
	    	}
	    
	    }
	    
		logger.info(" UM-Moto Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		/*transactionService.listUMMotoTransaction(paginationBean, null, null, "ALL");*/
		
		transactionService.listUMMotoTransactionByAgent(paginationBean, null, null,strUm, "EZYMOTO");
		if (paginationBean.getItemList().isEmpty()
				|| paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_HOTELMERCHANT;
	}
	
	@RequestMapping(value = { "/transaction/searchUMMoto" }, method = RequestMethod.GET)
	public String umMotoList(HttpServletRequest request,final java.security.Principal principal,
			final Model model,
			@RequestParam final String date,
			@RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  UM-MOTO Transaction ");
		PageBean pageBean = new PageBean("transactions list",
				"hotelMerchants/transaction/transactionUMMotoList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		 HttpSession session = request.getSession();
		 Agent agent = agentService.loadAgent(principal.getName());
		   
		 logger.info("display agent web : " + agent.getFirstName());

		
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
		
		List<MID> ids = new ArrayList<MID>();
	    for(Merchant t: merchant1) {
	    	ids.add(t.getMid());
	    }
	    
	    logger.info("ids:  "+ids);
	    
	    List<String> ummotomidList = new ArrayList<String>();
	    
	    for(MID mi : ids) {
	    	
	    	if(mi.getUmMotoMid() != null) {
	    		if(!mi.getUmMotoMid().isEmpty()) {
	    			ummotomidList.add(mi.getUmMotoMid());
	    		}
	    		
	    	}
	    	
	    }
		
	    logger.info("ummotomidList:  "+ummotomidList);
	    StringBuffer strUm = new StringBuffer();
	    int j=0;
	    for(String strMid : ummotomidList) {

	    	if(j == 0) {
	    		strUm.append("\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
   			j++;
	    	}else {		    	
	    		strUm.append(",\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
	    	}
	    
	    }
	    

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*transactionService.listAllTransaction(paginationBean, date, date1,
				txnType);*/
		
		
		transactionService.listUMMotoTransactionByAgent(paginationBean, date, date1,strUm,"EZYMOTO");
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_HOTELMERCHANT;

	}
	
	@RequestMapping(value = "/transaction/umMotoExport", method = RequestMethod.GET)
	public ModelAndView getExportUMMoto(
			final Model model,
			final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//			@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export,HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
	 
		HttpSession session = request.getSession();
		 Agent agent = agentService.loadAgent(principal.getName());
		   
		 logger.info("display agent web : " + agent.getFirstName());

		
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
		
		List<MID> ids = new ArrayList<MID>();
	    for(Merchant t: merchant1) {
	    	ids.add(t.getMid());
	    }
	    
	    logger.info("ids:  "+ids);
	    
	    List<String> ummotomidList = new ArrayList<String>();
	    
	    for(MID mi : ids) {
	    	
	    	if(mi.getUmMotoMid() != null) {
	    		if(!mi.getUmMotoMid().isEmpty()) {
	    			ummotomidList.add(mi.getUmMotoMid());
	    		}
	    		
	    	}
	    	
	    }
		
	    logger.info("ummotomidList:  "+ummotomidList);
	    StringBuffer strUm = new StringBuffer();
	    int j=0;
	    for(String strMid : ummotomidList) {

	    	if(j == 0) {
	    		strUm.append("\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
  			j++;
	    	}else {		    	
	    		strUm.append(",\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
	    	}
	    
	    }
	    
	
		
		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*transactionService.exportUMMotoTransactionAdmin(paginationBean, dat, dat1,"ALL");*/
		
		transactionService.exportUMMotoTransactionAgent(paginationBean, dat, dat1,strUm,"EZYMOTO");
		logger.info("No of Records: "+paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty()
				|| paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}
		
		
		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("hotelMerchantUmExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("hotelMerchantUmPdf", "umTxnList", list1);
		}
	 
 }
	
	@RequestMapping(value = { "/superagentProfportal/agentPassDetails" }, method = RequestMethod.GET)
	public String agentDetails(final Model model, final Principal principal) {
		PageBean pageBean = new PageBean("agent Details", "hotelMerchants/superagentProfile",
				Module.AGENT, "agentweb/sideMenuMerchantWeb");
		Agent agent = agentService.loadAgent(principal.getName());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("agent", agent);
		return TEMPLATE_HOTELMERCHANT;
	}
	
	@RequestMapping(value = { "/superagentProfportal/agentchangePassWord" }, method = RequestMethod.GET)
	public String changePassword(final Model model, final Principal principal,HttpServletRequest request) {
		logger.info("Login Person change password");
		PageBean pageBean = new PageBean("agentProfile", "hotelMerchants/superagentchangePassword",
				Module.AGENT, "agentweb/sideMenuAgentWeb");
		model.addAttribute("pageBean", pageBean);
		HttpSession session = request.getSession();
	    session.setAttribute("agentUserName",  principal.getName());
		return TEMPLATE_HOTELMERCHANT;
	}
	
	@RequestMapping(value = { "/superagentProfdetails/confirmPasswordbyagent" }, method = RequestMethod.POST)
	public String confirmPassword(final Model model, final Principal principal,HttpServletRequest request) {
		logger.info("Old PassWord"+request.getParameter("password")+"New Password"+request.getParameter("newPassword"));
		int count=agentService.changeAgentPassWord(principal.getName(), request.getParameter("newPassword"), request.getParameter("password"));
		PageBean pageBean=null;
		if(count==1){
			
		 pageBean = new PageBean("Agent Profile", "hotelMerchants/superagentChangePasswordSuccess",
				Module.AGENT, "agentweb/sideMenuAgentWeb");
		}else{
			
			 pageBean = new PageBean("Agent Profile", "hotelMerchants/superagentChangePasswordFailure",
						Module.AGENT, "agentweb/sideMenuAgentWeb");
		}
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_HOTELMERCHANT;
	}
	
	@RequestMapping(value = { "/getSuperAgentRecentTxn" }, method = RequestMethod.GET)
	public String getSuperAgLatestTxnDashBoard(final Model model,final java.security.Principal principal) {
		logger.info("Login Person in dash Board:"+principal.getName());
		
		Agent agent = agentService.loadAgent(principal.getName());
		PaginationBean<DashBoardData> txnPaginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check first 100 txn Amount Data ");
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
		
		List<MID> ids = new ArrayList<MID>();
	    for(Merchant t: merchant1) {
	    	ids.add(t.getMid());
	    }
	    
	    logger.info("ids:  "+ids);
	    
		StringBuffer str = new StringBuffer();
	    StringBuffer strUm = new StringBuffer();
	    List<String> midList = new ArrayList<String>();
	    List<String> ummidList = new ArrayList<String>();
	    for(MID mi : ids) {
	    	
	    	if(mi.getMid() != null) {
	    		if(!mi.getMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMid());
	    			str.append("\",");*/
	    			midList.add(mi.getMid());
	    			
	    		}
	    		
	    	}
	    	if(mi.getMotoMid() != null) {
	    		if(!mi.getMotoMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getMotoMid());
	    		}
	    		
	    	}
	    	if(mi.getEzypassMid() != null) {
	    		if(!mi.getEzypassMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getEzypassMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getEzywayMid() != null) {
	    		if(!mi.getEzywayMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getEzywayMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getEzyrecMid() != null) {
	    		if(!mi.getEzyrecMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getEzyrecMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getUmMid() != null) {
	    		if(!mi.getUmMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getUmMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getUmMotoMid() != null) {
	    		if(!mi.getUmMotoMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			ummidList.add(mi.getUmMotoMid());
	    		}
	    		
	    	}
	    	if(mi.getUmEzywayMid() != null) {
	    		if(!mi.getUmEzywayMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			ummidList.add(mi.getUmEzywayMid());
	    		}
	    		
	    	}
	    	
	    }
	    int u =0;
	    for(String strMid : midList) {
	    	
	    	if(u == 0) {
	    		str.append("\"");
    			str.append(strMid);
    			str.append("\"");
    			u++;
	    	}else {		    	
		    	str.append(",\"");
    			str.append(strMid);
    			str.append("\"");
	    	}
	    }
	    logger.info("String of MIDs:  "+str);
	    
	    
	    int v=0;
	    for(String strMid : ummidList) {

	    	if(v == 0) {
	    		strUm.append("\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
    			v++;
	    	}else {		    	
	    		strUm.append(",\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
	    	}
	    
	    }
	    
	    logger.info("String of UMMIDs:  "+strUm);
		
		
		dashBoardService.getAgentLastHundredTxn(txnPaginationBean,str,strUm);
		
		logger.info(" received first 100 txn Amount Data ");
		
		PageBean pageBean = new PageBean("Dash Board", "SuperAgent/completeAgReport",
				Module.ADMIN, "admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("fiveTxnList", txnPaginationBean);
		return TEMPLATE_SUPER_AGENT;
	}
	
	@RequestMapping(value = { "/getSuperAgentRecentTxnUmobile" }, method = RequestMethod.GET)
	public String getSuperAgLatestTxnDashBrdUmobile(
			final Model model,final java.security.Principal principal) {
		logger.info("Login Person in dash Board:"+principal.getName());
		
		Agent agent = agentService.loadAgent(principal.getName());
		PaginationBean<DashBoardData> txnPaginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check first 100 txn Amount Data ");
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
		
		List<MID> ids = new ArrayList<MID>();
	    for(Merchant t: merchant1) {
	    	ids.add(t.getMid());
	    }
	    
	    logger.info("ids:  "+ids);
	    
		StringBuffer str = new StringBuffer();
	    StringBuffer strUm = new StringBuffer();
	    List<String> midList = new ArrayList<String>();
	    List<String> ummidList = new ArrayList<String>();
	    for(MID mi : ids) {
	    	
	    	if(mi.getMid() != null) {
	    		if(!mi.getMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMid());
	    			str.append("\",");*/
	    			midList.add(mi.getMid());
	    			
	    		}
	    		
	    	}
	    	if(mi.getMotoMid() != null) {
	    		if(!mi.getMotoMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getMotoMid());
	    		}
	    		
	    	}
	    	if(mi.getEzypassMid() != null) {
	    		if(!mi.getEzypassMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getEzypassMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getEzywayMid() != null) {
	    		if(!mi.getEzywayMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getEzywayMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getEzyrecMid() != null) {
	    		if(!mi.getEzyrecMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getEzyrecMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getUmMid() != null) {
	    		if(!mi.getUmMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getUmMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getUmMotoMid() != null) {
	    		if(!mi.getUmMotoMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			ummidList.add(mi.getUmMotoMid());
	    		}
	    		
	    	}
	    	if(mi.getUmEzywayMid() != null) {
	    		if(!mi.getUmEzywayMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			ummidList.add(mi.getUmEzywayMid());
	    		}
	    		
	    	}
	    	
	    }
	    int u =0;
	    for(String strMid : midList) {
	    	
	    	if(u == 0) {
	    		str.append("\"");
    			str.append(strMid);
    			str.append("\"");
    			u++;
	    	}else {		    	
		    	str.append(",\"");
    			str.append(strMid);
    			str.append("\"");
	    	}
	    }
	    logger.info("String of MIDs:  "+str);
	    
	    
	    int v=0;
	    for(String strMid : ummidList) {

	    	if(v == 0) {
	    		strUm.append("\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
    			v++;
	    	}else {		    	
	    		strUm.append(",\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
	    	}
	    
	    }
	    
	    logger.info("String of UMMIDs:  "+strUm);
		
	   
	    dashBoardService.getAgentLastHundredUmTxn(txnPaginationBean,str,strUm);
		
		logger.info(" received first 100 txn Amount Data ");
		
		PageBean pageBean = new PageBean("Dash Board", "SuperAgent/completeAgReport",
				Module.ADMIN, "admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("fiveTxnList", txnPaginationBean);
		return TEMPLATE_SUPER_AGENT;
	}
	
	@RequestMapping(value = { "/getHotelMerchantRecentTxn" }, method = RequestMethod.GET)
	public String getHotelMerchantRecentTxn(
			final Model model,final java.security.Principal principal) {
		logger.info("Login Person in dash Board:"+principal.getName());
		
		Agent agent = agentService.loadAgent(principal.getName());
		PaginationBean<DashBoardData> txnPaginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check first 100 txn Amount Data ");
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
		
		List<MID> ids = new ArrayList<MID>();
	    for(Merchant t: merchant1) {
	    	ids.add(t.getMid());
	    }
	    
	    logger.info("ids:  "+ids);
	    
		StringBuffer str = new StringBuffer();
	    StringBuffer strUm = new StringBuffer();
	    List<String> midList = new ArrayList<String>();
	    List<String> ummidList = new ArrayList<String>();
	    for(MID mi : ids) {
	    	
	    	if(mi.getMid() != null) {
	    		if(!mi.getMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMid());
	    			str.append("\",");*/
	    			midList.add(mi.getMid());
	    			
	    		}
	    		
	    	}
	    	if(mi.getMotoMid() != null) {
	    		if(!mi.getMotoMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getMotoMid());
	    		}
	    		
	    	}
	    	if(mi.getEzypassMid() != null) {
	    		if(!mi.getEzypassMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getEzypassMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getEzywayMid() != null) {
	    		if(!mi.getEzywayMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getEzywayMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getEzyrecMid() != null) {
	    		if(!mi.getEzyrecMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getEzyrecMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getUmMid() != null) {
	    		if(!mi.getUmMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getUmMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getUmMotoMid() != null) {
	    		if(!mi.getUmMotoMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			ummidList.add(mi.getUmMotoMid());
	    		}
	    		
	    	}
	    	if(mi.getUmEzywayMid() != null) {
	    		if(!mi.getUmEzywayMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			ummidList.add(mi.getUmEzywayMid());
	    		}
	    		
	    	}
	    	
	    }
	    int u =0;
	    for(String strMid : midList) {
	    	
	    	if(u == 0) {
	    		str.append("\"");
    			str.append(strMid);
    			str.append("\"");
    			u++;
	    	}else {		    	
		    	str.append(",\"");
    			str.append(strMid);
    			str.append("\"");
	    	}
	    }
	    logger.info("String of MIDs:  "+str);
	    
	    
	    int v=0;
	    for(String strMid : ummidList) {

	    	if(v == 0) {
	    		strUm.append("\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
    			v++;
	    	}else {		    	
	    		strUm.append(",\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
	    	}
	    
	    }
	    
	    logger.info("String of UMMIDs:  "+strUm);
		
	   
	    dashBoardService.getHotelMerchantLastHundredUmTxn(txnPaginationBean,str,strUm);
		
		logger.info(" received first 100 txn Amount Data ");
		
		PageBean pageBean = new PageBean("Dash Board", "hotelMerchants/completeAgReport",
				Module.ADMIN, "admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("fiveTxnList", txnPaginationBean);
		return TEMPLATE_HOTELMERCHANT;
	}
	
	
	@RequestMapping(value = { "/agentVolumeDetailSummary/{currPage1}" }, method = RequestMethod.GET)
	public String displayAgenDetailVolumetList(final Model model, @PathVariable final int currPage1,
			java.security.Principal principal, final Long id, String username) {// ,String
																				// agentName)
																				// {
		logger.info("About to list all agents::::::"+currPage1);
		PageBean pageBean = new PageBean("Agent", "SuperAgent/agentTotalVolumeSummary", Module.AGENT, "agent/sideMenuAgent");

		model.addAttribute("pageBean", pageBean);

		List<AgentVolumeData> data = new ArrayList<AgentVolumeData>();
		PaginationBean<AgentVolumeData> paginationBean1 = new PaginationBean<AgentVolumeData>();
		paginationBean1.setCurrPage(currPage1);
		List<AgentVolumeData> listAgentVolumeData = new ArrayList<AgentVolumeData>();
		List<Agent> agent = agentService.loadAgent();
		
		
		 StringBuffer str = new StringBuffer();
			int j =0;
			for (Agent t : agent) {
		    	
		    	if(j == 0) {
		    		str.append("\'");
	   			str.append(t.getId());
	   			str.append("\'");
	   			j++;
		    	}else {		    	
			    	str.append(",\'");
	   			str.append(t.getId());
	   			str.append("\'");
		    	}
		    }
		    logger.info("String of Agent IDs:  "+str);
	   
	    listAgentVolumeData= transactionService.agentTotalVolume(str);
		logger.info("listAgentVolumeData Size : "+listAgentVolumeData.size());
		
		
		/*for(AgentVolumeData agentVolumeData: listAgentVolumeData){
			logger.info("Controller agId : "+agentVolumeData.getAgId());
			logger.info("Controller agentName : "+agentVolumeData.getAgentName());
			logger.info("Controller agentDet : "+agentVolumeData.getAgentDet());
			logger.info("Controller amount : "+agentVolumeData.getAmount());
			logger.info("Controller date : "+agentVolumeData.getDate());
			
			data.add(agentVolumeData);
		
		}*/
		
		//Method to get current and last three months name
				int cDate = 0;
				Date dt = new Date();
				cDate = dt.getMonth() + 1;
				List<Integer> listMonth = getAllMonth(cDate);
				List<String> date = new ArrayList<String>();
//				System.out.println("listMonth: "+listMonth.size());
				for (int i = 0 ; i < listMonth.size(); i++) {
					date.add(getMonth(cDate));
					cDate--;
				}
//				System.out.println("date: "+date);
				Map<String, String> monthandamt = new HashMap<>(); 
				for(int x=0; x<listMonth.size();x++)
					{
					monthandamt.put(listMonth.get(x).toString(), "0.00");
					//monthandamt.put(getMonth(listMonth.get(x)), "0.00");
					}	
				
				AgentVolumeData finalVolumeData = new AgentVolumeData();
				List<String> amountUM = new ArrayList();
				
				List<String> month = new ArrayList();
	//for (Agent t : agent) {
		
//		List<String> amountUM = new ArrayList<String>();
		int count = 0;
		int count1 = 0;
		
		//for (Agent t : agent) {
		for(int i=0 ; i < listAgentVolumeData.size();i++) {
			
			logger.info("First loop  : " + listAgentVolumeData.get(i).getAgId()); 
		
			if(!listAgentVolumeData.get(i).getAgId().equals(finalVolumeData.getAgId())&&
					finalVolumeData.getAgId()!=null) {
				
				logger.info("date: " + month);
				logger.info("amountUM: " + amountUM);
			    finalVolumeData.setAmount(amountUM);
			    for(String key :monthandamt.keySet()) {	
					
					month.add(getMonth(Integer.parseInt(key)));
				}
			    finalVolumeData.setDate(month);             
			    data.add(finalVolumeData);
			    finalVolumeData = new AgentVolumeData();
			    amountUM = new ArrayList();
			    month = new ArrayList();
			    for(int x=0; x<listMonth.size();x++)
				{
			    	monthandamt.replace(listMonth.get(x).toString(), "0.00");
			    	//monthandamt.replace(getMonth(listMonth.get(x)), "0.00");
				}
			    count1=1;
			}else {
				count1=1;
			}
		if(count1==1||
				finalVolumeData.getAgId()==null) {	
			
			Double d = new Double(listAgentVolumeData.get(i).getAmount1());
			d = d / 100;
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(d);
			monthandamt.replace(listAgentVolumeData.get(i).getMonth(), output);
			
			//monthandamt.replace(getMonth(Integer.parseInt(listAgentVolumeData.get(i).getMonth())), output);
			int date1 = Integer.parseInt(listAgentVolumeData.get(i).getMonth());
			logger.info("date  : " + date1 );
			logger.info("month  : " + listMonth.get(count).intValue());
			amountUM = new ArrayList(monthandamt.values());
			
			finalVolumeData.setAgentName(listAgentVolumeData.get(i).getAgentName());
			finalVolumeData.setAgId(listAgentVolumeData.get(i).getAgId());
	        logger.info("AgentId  : " + listAgentVolumeData.get(i).getAgId());
	        finalVolumeData.setTxnPresent("Yes");	        
	        
		}
		
        }
		finalVolumeData.setAmount(amountUM);
	    finalVolumeData.setDate(month);             
	    data.add(finalVolumeData);
	paginationBean1.setItemList(data);
    model.addAttribute("paginationBean",paginationBean1);
	return TEMPLATE_SUPER_AGENT;
	}
	
	@RequestMapping(value={"/listMerchantGPV"},method=RequestMethod.GET)
	public String listMerchantGPV(final Model model,java.security.Principal principal)
	{
		logger.info("about to list all merchants for this agent");
		PageBean pageBean = new PageBean("Agent", "SuperAgent/MerchantGPVByAgent", Module.AGENT,
				"agent/sideMenuAgent");
		//logger.info("about to list all agents");
		
		
		logger.info("Super Agent Portal Currently logged by "+ principal.getName());
		
		List<MerchantGPVData> merchantsGPV =  merchantService.listMerchantGPVDetailsBySuperAgent();
		
		logger.info("merchantsGPV::"+ merchantsGPV);
		
		
		List<MerchantGPVData> merchantsFinalGPV = new ArrayList<MerchantGPVData>();
		
		//new change
		
		List<String> MerchantId=new ArrayList<String>();
		
		for(int i=0 ; i < merchantsGPV.size();i++) {
			MerchantId.add(merchantsGPV.get(i).getMerchantId()+"~"+merchantsGPV.get(i).getMonthName());
		}
		
		 Map<String, Integer> nameAndCount = new HashMap<>();

	     // build hash table with count
	     for (String name : MerchantId) {
	    	 logger.info("hashmap::"+ name);
	         Integer count = nameAndCount.get(name);
	         if (count == null) {
	             nameAndCount.put(name, 1);
	         } else {
	             nameAndCount.put(name, ++count);
	         }
	     }
	     MerchantGPVData finalMerchant = new MerchantGPVData();
	    
	     for(int i=0 ; i < merchantsGPV.size();i++) {

	         if (nameAndCount.get(merchantsGPV.get(i).getMerchantId()+"~"+merchantsGPV.get(i).getMonthName()) > 1) {
	        	 
	        	 if((!merchantsGPV.get(i).getMerchantId().equals(finalMerchant.getMerchantId()) 
	        			 ||  !merchantsGPV.get(i).getMonthName().equals(finalMerchant.getMonthName())
	        			 )
	        			 && finalMerchant.getMerchantId()!=null){	
	        		 
	        		 
	        		 merchantsFinalGPV.add(finalMerchant);
	        		 finalMerchant = new MerchantGPVData();
	        	 }
					finalMerchant.setMerchantName(merchantsGPV.get(i).getMerchantName());
					finalMerchant.setAgentName(merchantsGPV.get(i).getAgentName());
					finalMerchant.setMerchantId(merchantsGPV.get(i).getMerchantId());
					finalMerchant.setMonthName(merchantsGPV.get(i).getMonthName());
					if(merchantsGPV.get(i).getMerchantType().equals("P")) {
						finalMerchant.setMerchantType("Paydee");
					}else if(merchantsGPV.get(i).getMerchantType().equals("FIUU")) {
						finalMerchant.setMerchantType("FIUU");
					}else {
						finalMerchant.setMerchantType("Umobile");
					}
					
					
					//EZYWAY
					if(merchantsGPV.get(i).getIsEzyway() != null) {
						
						logger.info("EZYWAY  "+merchantsGPV.get(i).getIsEzyway());
						
						finalMerchant.setIsEzyway(merchantsGPV.get(i).getIsEzyway());
						finalMerchant.setEzywayAmt(merchantsGPV.get(i).getEzywayAmt());
					}

					//EZYLINK
					if(merchantsGPV.get(i).getIsEzylink() != null) {
						
						logger.info("EZYLINK  "+merchantsGPV.get(i).getIsEzylink());
						finalMerchant.setIsEzylink(merchantsGPV.get(i).getIsEzylink());
						finalMerchant.setEzylinkAmt(merchantsGPV.get(i).getEzylinkAmt());
					}

					
					//EZYREC
					if(merchantsGPV.get(i).getIsEzyrec() != null || merchantsGPV.get(i).getIsRecurring() != null) {
						
						if(merchantsGPV.get(i).getRecurringAmt() == null) {
							merchantsGPV.get(i).setRecurringAmt("0");
						}
						if(merchantsGPV.get(i).getEzyrecAmt() == null) {
							merchantsGPV.get(i).setEzyrecAmt("0");
						}
						Double tempamt=Double.parseDouble(merchantsGPV.get(i).getEzyrecAmt())+ 
								Double.parseDouble(merchantsGPV.get(i).getRecurringAmt());
						String pattern = "#,##0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						String Txn = myFormatter.format(tempamt);
						finalMerchant.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
						finalMerchant.setEzyrecAmt(Txn);
					}
					
//					//RECURRING
//					if(merchantsGPV.get(i).getIsRecurring() != null) {
//						
//						finalMerchant.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
//						finalMerchant.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
//					}

					//EZYMOTO
					if(merchantsGPV.get(i).getIsEzymoto() != null) {
						
						logger.info("EZYMOTO"+merchantsGPV.get(i).getIsEzymoto());
						finalMerchant.setIsEzymoto(merchantsGPV.get(i).getIsEzymoto());
						finalMerchant.setEzymotoAmt(merchantsGPV.get(i).getEzymotoAmt());
					}

					//EZYWIRE
					if(merchantsGPV.get(i).getIsEzywire() != null) {
						
						finalMerchant.setIsEzywire(merchantsGPV.get(i).getIsEzywire());
						finalMerchant.setEzywireAmt(merchantsGPV.get(i).getEzywireAmt());
					}

					
				/*	//RECURRING
					if(merchantsGPV.get(i).getIsRecurring() != null) {
						
						finalMerchant.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
						finalMerchant.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
					}*/

					
					//RECPLUS
					if(merchantsGPV.get(i).getIsRecplus() != null) {
						
						finalMerchant.setIsRecplus(merchantsGPV.get(i).getIsRecplus());
						finalMerchant.setRecplusAmt(merchantsGPV.get(i).getRecplusAmt());
					}

					//EZYMOTO-VCC
					if(merchantsGPV.get(i).getIsEzymotoVcc() != null) {
						
						finalMerchant.setIsEzymotoVcc(merchantsGPV.get(i).getIsEzymotoVcc());
						finalMerchant.setEzymotoVccAmt(merchantsGPV.get(i).getEzymotoVccAmt());
					}

					//EZYAUTH
					if(merchantsGPV.get(i).getIsEzyauth() != null) {
						
						finalMerchant.setIsEzyauth(merchantsGPV.get(i).getIsEzyauth());
						finalMerchant.setEzyauthAmt(merchantsGPV.get(i).getEzyauthAmt());
					}

					logger.info("finalMerchant:"+ finalMerchant);
	         }
	         
	         else{
					logger.info("else condition:"+ merchantsGPV.get(i).getMerchantName());
					MerchantGPVData finalMerchant1 = new MerchantGPVData();
					finalMerchant1.setMerchantName(merchantsGPV.get(i).getMerchantName());
					finalMerchant1.setAgentName(merchantsGPV.get(i).getAgentName());
					finalMerchant1.setMerchantId(merchantsGPV.get(i).getMerchantId());
					finalMerchant1.setMonthName(merchantsGPV.get(i).getMonthName());
					finalMerchant1.setProductType(merchantsGPV.get(i).getProductType());
					finalMerchant1.setIsEzywire(merchantsGPV.get(i).getIsEzywire());
					finalMerchant1.setIsEzymoto(merchantsGPV.get(i).getIsEzymoto());
					finalMerchant1.setIsEzyway(merchantsGPV.get(i).getIsEzyway());
					finalMerchant1.setIsEzylink(merchantsGPV.get(i).getIsEzylink());
					finalMerchant1.setIsEzymotoVcc(merchantsGPV.get(i).getIsEzymotoVcc());
					finalMerchant1.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
					finalMerchant1.setIsEzyauth(merchantsGPV.get(i).getIsEzyauth());
					finalMerchant1.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
					finalMerchant1.setIsRecplus(merchantsGPV.get(i).getIsRecplus());
					finalMerchant1.setEzywireAmt(merchantsGPV.get(i).getEzywireAmt());
					finalMerchant1.setEzymotoAmt(merchantsGPV.get(i).getEzymotoAmt());
					finalMerchant1.setEzywayAmt(merchantsGPV.get(i).getEzywayAmt());
					//finalMerchant1.setEzyrecAmt(merchantsGPV.get(i).getEzyrecAmt());
					finalMerchant1.setEzymotoVccAmt(merchantsGPV.get(i).getEzymotoVccAmt());
					finalMerchant1.setEzylinkAmt(merchantsGPV.get(i).getEzylinkAmt());
					finalMerchant1.setEzyauthAmt(merchantsGPV.get(i).getEzyauthAmt());
					//finalMerchant1.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
					finalMerchant1.setRecplusAmt(merchantsGPV.get(i).getRecplusAmt());
					
					if(merchantsGPV.get(i).getIsEzyrec() != null || merchantsGPV.get(i).getIsRecurring() != null) {
						
						if(merchantsGPV.get(i).getRecurringAmt() == null) {
							merchantsGPV.get(i).setRecurringAmt("0");
						}
						if(merchantsGPV.get(i).getEzyrecAmt() == null) {
							merchantsGPV.get(i).setEzyrecAmt("0");
						}
						Double tempamt=Double.parseDouble(merchantsGPV.get(i).getEzyrecAmt())+ 
								Double.parseDouble(merchantsGPV.get(i).getRecurringAmt());
						String pattern = "#,##0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						String Txn = myFormatter.format(tempamt);
						finalMerchant1.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
						finalMerchant1.setEzyrecAmt(Txn);
					}
					
					if(merchantsGPV.get(i).getMerchantType().equals("P")) {
						finalMerchant1.setMerchantType("Paydee");
					}else if(merchantsGPV.get(i).getMerchantType().equals("FIUU")) {
						finalMerchant1.setMerchantType("FIUU");
					}else {
						finalMerchant1.setMerchantType("Umobile");
					}
					merchantsFinalGPV.add(finalMerchant1);
					
				}
	     } 
	    
	
		merchantsFinalGPV.add(finalMerchant);
		
		
		logger.info("merchantsFinalGPV:"+ merchantsFinalGPV);
		
		int cDate = 0;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;

		System.out.println("cDate: "+cDate);
		List<Integer> listMonth = getAllMonth(cDate);
		logger.info("listMonth::"+ listMonth);
		List<MerchantGPVData> fourthMonList = new ArrayList<MerchantGPVData>();
		List<MerchantGPVData> ThirdMonList = new ArrayList<MerchantGPVData>();
		List<MerchantGPVData> SecondMonList = new ArrayList<MerchantGPVData>();
		List<MerchantGPVData> FirstMonList = new ArrayList<MerchantGPVData>();
		
		
		for(MerchantGPVData merchant1 : merchantsFinalGPV) {
			Double total = 0.00;
			Integer month = Integer.parseInt(merchant1.getMonthName());
			logger.info("month::"+ month+",merchant name::"+merchant1.getMerchantName());
		
			if(merchant1.getEzymotoAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzymotoAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzywayAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzywayAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzywireAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzywireAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzymotoVccAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzymotoVccAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzyrecAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzyrecAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getRecplusAmt() != null) {
				Double d = Double.parseDouble(merchant1.getRecplusAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzylinkAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzylinkAmt().replace(",", ""));
				total = total+d;
			}
			
			if(total != null) {
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String Txn = myFormatter.format(total);
				merchant1.setTotalGpv(Txn);
			}
			
			
			
			if(month == listMonth.get(0)) {
				
				fourthMonList.add(merchant1);
				logger.info("fourthMonList::"+ fourthMonList);
			}
			if(month == listMonth.get(1)) {
				
				ThirdMonList.add(merchant1);
				
				logger.info("ThirdMonList::"+ ThirdMonList);
			}
			if(month == listMonth.get(2)) {
				
				SecondMonList.add(merchant1);
				logger.info("SecondMonList::"+ SecondMonList);
			}
			if(month == listMonth.get(3)) {
				
				FirstMonList.add(merchant1);
				logger.info("FirstMonList"+ FirstMonList);
			}
			
			
		}
		
		
		
		
		
		model.addAttribute("pageBean", pageBean);
		
		model.addAttribute("fourthMonList", fourthMonList);
		model.addAttribute("ThirdMonList", ThirdMonList);
		model.addAttribute("SecondMonList", SecondMonList);
		model.addAttribute("FirstMonList",FirstMonList);
		logger.info("listMerchantVolumeSummary completed");

		return TEMPLATE_SUPER_AGENT;
		
	}
	
	@RequestMapping(value = { "/merchantGPVExport" }, method = RequestMethod.GET)
	public ModelAndView merchantGPVExport(final Model model,HttpServletRequest request,
			java.security.Principal principal,
			@RequestParam("month") String id,
			@RequestParam(required = false) String export) {
		
		logger.info("about to export all merchants for this agent");
		
		
		
		List<MerchantGPVData> merchantsGPV =  merchantService.listMerchantGPVDetailsBySuperAgent();		
		
		logger.info("merchantsGPV::"+ merchantsGPV);
		List<MerchantGPVData> merchantsFinalGPV = new ArrayList<MerchantGPVData>();
		
		//new change
		
		List<String> MerchantId=new ArrayList<String>();
		
		for(int i=0 ; i < merchantsGPV.size();i++) {
			MerchantId.add(merchantsGPV.get(i).getMerchantId()+"~"+merchantsGPV.get(i).getMonthName());
		}
		
		 Map<String, Integer> nameAndCount = new HashMap<>();

	     // build hash table with count
	     for (String name : MerchantId) {
	    	 logger.info("hashmap::"+ name);
	         Integer count = nameAndCount.get(name);
	         if (count == null) {
	             nameAndCount.put(name, 1);
	         } else {
	             nameAndCount.put(name, ++count);
	         }
	     }
	     MerchantGPVData finalMerchant = new MerchantGPVData();
	    
	     for(int i=0 ; i < merchantsGPV.size();i++) {

	         if (nameAndCount.get(merchantsGPV.get(i).getMerchantId()+"~"+merchantsGPV.get(i).getMonthName()) > 1) {
	        	 
	        	 if((!merchantsGPV.get(i).getMerchantId().equals(finalMerchant.getMerchantId()) 
	        			 ||  !merchantsGPV.get(i).getMonthName().equals(finalMerchant.getMonthName())
	        			 )
	        			 && finalMerchant.getMerchantId()!=null){	
	        		 
	        		 merchantsFinalGPV.add(finalMerchant);
	        		 finalMerchant = new MerchantGPVData();
	        	 }
					finalMerchant.setMerchantName(merchantsGPV.get(i).getMerchantName());
					finalMerchant.setAgentName(merchantsGPV.get(i).getAgentName());
					finalMerchant.setMerchantId(merchantsGPV.get(i).getMerchantId());
					finalMerchant.setMonthName(merchantsGPV.get(i).getMonthName());
					if(merchantsGPV.get(i).getMerchantType().equals("P")) {
						finalMerchant.setMerchantType("Paydee");
					}else if(merchantsGPV.get(i).getMerchantType().equals("FIUU")) {
						finalMerchant.setMerchantType("FIUU");
					}else {
						finalMerchant.setMerchantType("Umobile");
					}
					
						
					//EZYWAY
					if(merchantsGPV.get(i).getIsEzyway() != null) {
						
						logger.info("EZYWAY  "+merchantsGPV.get(i).getIsEzyway());
						
						finalMerchant.setIsEzyway(merchantsGPV.get(i).getIsEzyway());
						finalMerchant.setEzywayAmt(merchantsGPV.get(i).getEzywayAmt());
					}

					//EZYLINK
					if(merchantsGPV.get(i).getIsEzylink() != null) {
						
						logger.info("EZYLINK  "+merchantsGPV.get(i).getIsEzylink());
						finalMerchant.setIsEzylink(merchantsGPV.get(i).getIsEzylink());
						finalMerchant.setEzylinkAmt(merchantsGPV.get(i).getEzylinkAmt());
					}

					
					//EZYREC
					if(merchantsGPV.get(i).getIsEzyrec() != null || merchantsGPV.get(i).getIsRecurring() != null) {
						
						if(merchantsGPV.get(i).getRecurringAmt() == null) {
							merchantsGPV.get(i).setRecurringAmt("0");
						}
						if(merchantsGPV.get(i).getEzyrecAmt() == null) {
							merchantsGPV.get(i).setEzyrecAmt("0");
						}
						Double tempamt=Double.parseDouble(merchantsGPV.get(i).getEzyrecAmt())+ 
								Double.parseDouble(merchantsGPV.get(i).getRecurringAmt());
						String pattern = "#,##0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						String Txn = myFormatter.format(tempamt);
						finalMerchant.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
						finalMerchant.setEzyrecAmt(Txn);
					}
					
//					//RECURRING
//					if(merchantsGPV.get(i).getIsRecurring() != null) {
//						
//						finalMerchant.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
//						finalMerchant.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
//					}

					//EZYMOTO
					if(merchantsGPV.get(i).getIsEzymoto() != null) {
						
						logger.info("EZYMOTO"+merchantsGPV.get(i).getIsEzymoto());
						finalMerchant.setIsEzymoto(merchantsGPV.get(i).getIsEzymoto());
						finalMerchant.setEzymotoAmt(merchantsGPV.get(i).getEzymotoAmt());
					}

					//EZYWIRE
					if(merchantsGPV.get(i).getIsEzywire() != null) {
						
						finalMerchant.setIsEzywire(merchantsGPV.get(i).getIsEzywire());
						finalMerchant.setEzywireAmt(merchantsGPV.get(i).getEzywireAmt());
					}

					
				/*	//RECURRING
					if(merchantsGPV.get(i).getIsRecurring() != null) {
						
						finalMerchant.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
						finalMerchant.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
					}*/

					
					//RECPLUS
					if(merchantsGPV.get(i).getIsRecplus() != null) {
						
						finalMerchant.setIsRecplus(merchantsGPV.get(i).getIsRecplus());
						finalMerchant.setRecplusAmt(merchantsGPV.get(i).getRecplusAmt());
					}

					//EZYMOTO-VCC
					if(merchantsGPV.get(i).getIsEzymotoVcc() != null) {
						
						finalMerchant.setIsEzymotoVcc(merchantsGPV.get(i).getIsEzymotoVcc());
						finalMerchant.setEzymotoVccAmt(merchantsGPV.get(i).getEzymotoVccAmt());
					}

					//EZYAUTH
					if(merchantsGPV.get(i).getIsEzyauth() != null) {
						
						finalMerchant.setIsEzyauth(merchantsGPV.get(i).getIsEzyauth());
						finalMerchant.setEzyauthAmt(merchantsGPV.get(i).getEzyauthAmt());
					}
				
					
					logger.info("finalMerchant:"+ finalMerchant);
	         }
	         
	         else{
					logger.info("else condition:"+ merchantsGPV.get(i).getMerchantName());
					MerchantGPVData finalMerchant1 = new MerchantGPVData();
					finalMerchant1.setMerchantName(merchantsGPV.get(i).getMerchantName());
					finalMerchant1.setAgentName(merchantsGPV.get(i).getAgentName());
					finalMerchant1.setMerchantId(merchantsGPV.get(i).getMerchantId());
					finalMerchant1.setMonthName(merchantsGPV.get(i).getMonthName());
					finalMerchant1.setProductType(merchantsGPV.get(i).getProductType());
					finalMerchant1.setIsEzywire(merchantsGPV.get(i).getIsEzywire());
					finalMerchant1.setIsEzymoto(merchantsGPV.get(i).getIsEzymoto());
					finalMerchant1.setIsEzyway(merchantsGPV.get(i).getIsEzyway());
					finalMerchant1.setIsEzylink(merchantsGPV.get(i).getIsEzylink());
					finalMerchant1.setIsEzymotoVcc(merchantsGPV.get(i).getIsEzymotoVcc());
					finalMerchant1.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
					finalMerchant1.setIsEzyauth(merchantsGPV.get(i).getIsEzyauth());
					finalMerchant1.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
					finalMerchant1.setIsRecplus(merchantsGPV.get(i).getIsRecplus());
					finalMerchant1.setEzywireAmt(merchantsGPV.get(i).getEzywireAmt());
					finalMerchant1.setEzymotoAmt(merchantsGPV.get(i).getEzymotoAmt());
					finalMerchant1.setEzywayAmt(merchantsGPV.get(i).getEzywayAmt());
					//finalMerchant1.setEzyrecAmt(merchantsGPV.get(i).getEzyrecAmt());
					finalMerchant1.setEzymotoVccAmt(merchantsGPV.get(i).getEzymotoVccAmt());
					finalMerchant1.setEzylinkAmt(merchantsGPV.get(i).getEzylinkAmt());
					finalMerchant1.setEzyauthAmt(merchantsGPV.get(i).getEzyauthAmt());
					//finalMerchant1.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
					finalMerchant1.setRecplusAmt(merchantsGPV.get(i).getRecplusAmt());
					
					if(merchantsGPV.get(i).getIsEzyrec() != null || merchantsGPV.get(i).getIsRecurring() != null) {
						
						if(merchantsGPV.get(i).getRecurringAmt() == null) {
							merchantsGPV.get(i).setRecurringAmt("0");
						}
						if(merchantsGPV.get(i).getEzyrecAmt() == null) {
							merchantsGPV.get(i).setEzyrecAmt("0");
						}
						Double tempamt=Double.parseDouble(merchantsGPV.get(i).getEzyrecAmt())+ 
								Double.parseDouble(merchantsGPV.get(i).getRecurringAmt());
						String pattern = "#,##0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						String Txn = myFormatter.format(tempamt);
						finalMerchant1.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
						finalMerchant1.setEzyrecAmt(Txn);
					}
					if(merchantsGPV.get(i).getMerchantType().equals("P")) {
						finalMerchant1.setMerchantType("Paydee");
					}else if(merchantsGPV.get(i).getMerchantType().equals("FIUU")) {
						finalMerchant1.setMerchantType("FIUU");
					}else {
						finalMerchant1.setMerchantType("Umobile");
					}
					
					merchantsFinalGPV.add(finalMerchant1);
					
				}
	     } 
	    
	
		merchantsFinalGPV.add(finalMerchant);
		
		
		logger.info("merchantsFinalGPV:"+ merchantsFinalGPV);
		
		int cDate = 0;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;

		System.out.println("cDate: "+cDate);
		List<Integer> listMonth = getAllMonth(cDate);
		logger.info("listMonth::"+ listMonth);
		List<MerchantGPVData> fourthMonList = new ArrayList<MerchantGPVData>();
		List<MerchantGPVData> ThirdMonList = new ArrayList<MerchantGPVData>();
		List<MerchantGPVData> SecondMonList = new ArrayList<MerchantGPVData>();
		List<MerchantGPVData> FirstMonList = new ArrayList<MerchantGPVData>();
		
		List<MerchantGPVData> FinalMonListData = new ArrayList<MerchantGPVData>();
		
		for(MerchantGPVData merchant1 : merchantsFinalGPV) {
			
			Double total = 0.00;
			Integer month = Integer.parseInt(merchant1.getMonthName());
			logger.info("month::"+ month+",merchant name::"+merchant1.getMerchantName());
			
			if(merchant1.getEzymotoAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzymotoAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzywayAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzywayAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzywireAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzywireAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzymotoVccAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzymotoVccAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzyrecAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzyrecAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getRecplusAmt() != null) {
				Double d = Double.parseDouble(merchant1.getRecplusAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzylinkAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzylinkAmt().replace(",", ""));
				total = total+d;
			}
			
			if(total != null) {
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String Txn = myFormatter.format(total);
				merchant1.setTotalGpv(Txn);
			}
			
			
			
			if(id.equals("fourthMon")) {			
				if(month == listMonth.get(0)) {
					
					fourthMonList.add(merchant1);
					logger.info("fourthMonList::"+ fourthMonList);
					FinalMonListData = fourthMonList;
				}
			}
			if(id.equals("thirdMon")) {		
				if(month == listMonth.get(1)) {
					
					ThirdMonList.add(merchant1);					
					logger.info("ThirdMonList::"+ ThirdMonList);
					FinalMonListData = ThirdMonList;
				}
			}
			if(id.equals("secondMon")) {		
				if(month == listMonth.get(2)) {
					
					SecondMonList.add(merchant1);
					logger.info("SecondMonList::"+ SecondMonList);
					FinalMonListData = SecondMonList;
				}
			}
			if(id.equals("firstMon")) {	
				if(month == listMonth.get(3)) {
					
					FirstMonList.add(merchant1);
					logger.info("FirstMonList"+ FirstMonList);
					FinalMonListData = FirstMonList;
				}
			}
			

		}
		

	
		if (!(export.equals("PDF"))) {
			logger.info("Generate Excel");
			return new ModelAndView("merchantGpvAgentExcel", "merchantGPVList", FinalMonListData);
		} else {
			logger.info("Generate PDF");
			return new ModelAndView("merchantGpvAgentPdf", "merchantGPVList", FinalMonListData);
		}
				
	}
	
	
	@RequestMapping(value={"/listMerchantGPVByAgent/{agId}"},method=RequestMethod.GET)
	public String listMerchantGPVByAgent(final Model model,
			 @PathVariable String agId,
			java.security.Principal principal)
	{
		logger.info("about to list all merchants for this agent");
		PageBean pageBean = new PageBean("Agent", "SuperAgent/MerchantGPVDetails", Module.AGENT,
				"agent/sideMenuAgent");
		//logger.info("about to list all agents");
		
		
		logger.info("Agent Portal Currently logged by "+ principal.getName());
				
		
		Agent agent=agentService.loadAgentByIdPk(Long.valueOf(agId));			
		
		model.addAttribute("pageBean", pageBean);
		
		logger.info("Agent name"+ agent.getFirstName());
		logger.info("Agent ID"+ agent.getId());

		BigInteger agid = BigInteger.valueOf(agent.getId()); 

		//merchantService.listMerchantGPVDetails(paginationBean);
		String agentId = agent.getId().toString(); 
		List<MerchantGPVData> merchantsGPV =  merchantService.listMerchantGPVDetailsByAgent(agentId);
		
		logger.info("merchantsGPV::"+ merchantsGPV);
		//List<Merchant> merchantsGPV1 =  merchantsGPV;
		

		List<MerchantGPVData> merchantsFinalGPV = new ArrayList<MerchantGPVData>();
		
		//new change
		
		List<String> MerchantId=new ArrayList<String>();
		
		for(int i=0 ; i < merchantsGPV.size();i++) {
			MerchantId.add(merchantsGPV.get(i).getMerchantId()+"~"+merchantsGPV.get(i).getMonthName());
		}
		
		 Map<String, Integer> nameAndCount = new HashMap<>();

	     // build hash table with count
	     for (String name : MerchantId) {
	    	 logger.info("hashmap::"+ name);
	         Integer count = nameAndCount.get(name);
	         if (count == null) {
	             nameAndCount.put(name, 1);
	         } else {
	             nameAndCount.put(name, ++count);
	         }
	     }
	     MerchantGPVData finalMerchant = new MerchantGPVData();
	    
	     for(int i=0 ; i < merchantsGPV.size();i++) {

	    	 if (nameAndCount.get(merchantsGPV.get(i).getMerchantId()+"~"+merchantsGPV.get(i).getMonthName()) > 1) {
	        	 
	        	 if((!merchantsGPV.get(i).getMerchantId().equals(finalMerchant.getMerchantId()) 
	        			 ||  !merchantsGPV.get(i).getMonthName().equals(finalMerchant.getMonthName())
	        			 )
	        			 && finalMerchant.getMerchantId()!=null){	
	        		 
	        		 merchantsFinalGPV.add(finalMerchant);
	        		 finalMerchant = new MerchantGPVData();
	        	 }
					finalMerchant.setMerchantName(merchantsGPV.get(i).getMerchantName());
					finalMerchant.setAgentName(merchantsGPV.get(i).getAgentName());
					finalMerchant.setMerchantId(merchantsGPV.get(i).getMerchantId());
					finalMerchant.setMonthName(merchantsGPV.get(i).getMonthName());
					if(merchantsGPV.get(i).getMerchantType().equals("P")) {
						finalMerchant.setMerchantType("Paydee");
					}else if(merchantsGPV.get(i).getMerchantType().equals("FIUU")) {
						finalMerchant.setMerchantType("FIUU");
					}else {
						finalMerchant.setMerchantType("Umobile");
					}
					
					//EZYWAY
					if(merchantsGPV.get(i).getIsEzyway() != null) {
						
						logger.info("EZYWAY  "+merchantsGPV.get(i).getIsEzyway());
						
						finalMerchant.setIsEzyway(merchantsGPV.get(i).getIsEzyway());
						finalMerchant.setEzywayAmt(merchantsGPV.get(i).getEzywayAmt());
					}

					//EZYLINK
					if(merchantsGPV.get(i).getIsEzylink() != null) {
						
						logger.info("EZYLINK  "+merchantsGPV.get(i).getIsEzylink());
						finalMerchant.setIsEzylink(merchantsGPV.get(i).getIsEzylink());
						finalMerchant.setEzylinkAmt(merchantsGPV.get(i).getEzylinkAmt());
					}

					
					//EZYREC
					if(merchantsGPV.get(i).getIsEzyrec() != null || merchantsGPV.get(i).getIsRecurring() != null) {
						
						if(merchantsGPV.get(i).getRecurringAmt() == null) {
							merchantsGPV.get(i).setRecurringAmt("0");
						}
						if(merchantsGPV.get(i).getEzyrecAmt() == null) {
							merchantsGPV.get(i).setEzyrecAmt("0");
						}
						Double tempamt=Double.parseDouble(merchantsGPV.get(i).getEzyrecAmt())+ 
								Double.parseDouble(merchantsGPV.get(i).getRecurringAmt());
						String pattern = "#,##0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						String Txn = myFormatter.format(tempamt);
						finalMerchant.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
						finalMerchant.setEzyrecAmt(Txn);
					}

					//EZYMOTO
					if(merchantsGPV.get(i).getIsEzymoto() != null) {
						
						logger.info("EZYMOTO"+merchantsGPV.get(i).getIsEzymoto());
						finalMerchant.setIsEzymoto(merchantsGPV.get(i).getIsEzymoto());
						finalMerchant.setEzymotoAmt(merchantsGPV.get(i).getEzymotoAmt());
					}

					//EZYWIRE
					if(merchantsGPV.get(i).getIsEzywire() != null) {
						
						finalMerchant.setIsEzywire(merchantsGPV.get(i).getIsEzywire());
						finalMerchant.setEzywireAmt(merchantsGPV.get(i).getEzywireAmt());
					}

					
					//RECURRING
					/*if(merchantsGPV.get(i).getIsRecurring() != null) {
						
						finalMerchant.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
						finalMerchant.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
					}*/

					
					//RECPLUS
					if(merchantsGPV.get(i).getIsRecplus() != null) {
						
						finalMerchant.setIsRecplus(merchantsGPV.get(i).getIsRecplus());
						finalMerchant.setRecplusAmt(merchantsGPV.get(i).getRecplusAmt());
					}

					//EZYMOTO-VCC
					if(merchantsGPV.get(i).getIsEzymotoVcc() != null) {
						
						finalMerchant.setIsEzymotoVcc(merchantsGPV.get(i).getIsEzymotoVcc());
						finalMerchant.setEzymotoVccAmt(merchantsGPV.get(i).getEzymotoVccAmt());
					}

					//EZYAUTH
					if(merchantsGPV.get(i).getIsEzyauth() != null) {
						
						finalMerchant.setIsEzyauth(merchantsGPV.get(i).getIsEzyauth());
						finalMerchant.setEzyauthAmt(merchantsGPV.get(i).getEzyauthAmt());
					}

					logger.info("finalMerchant:"+ finalMerchant);
	         }
	         
	         else{
					logger.info("else condition:"+ merchantsGPV.get(i).getMerchantName());
					MerchantGPVData finalMerchant1 = new MerchantGPVData();
					finalMerchant1.setMerchantName(merchantsGPV.get(i).getMerchantName());
					finalMerchant1.setAgentName(merchantsGPV.get(i).getAgentName());
					finalMerchant1.setMerchantId(merchantsGPV.get(i).getMerchantId());
					finalMerchant1.setMonthName(merchantsGPV.get(i).getMonthName());
					finalMerchant1.setProductType(merchantsGPV.get(i).getProductType());
					finalMerchant1.setIsEzywire(merchantsGPV.get(i).getIsEzywire());
					finalMerchant1.setIsEzymoto(merchantsGPV.get(i).getIsEzymoto());
					finalMerchant1.setIsEzyway(merchantsGPV.get(i).getIsEzyway());
					finalMerchant1.setIsEzylink(merchantsGPV.get(i).getIsEzylink());
					finalMerchant1.setIsEzymotoVcc(merchantsGPV.get(i).getIsEzymotoVcc());
					finalMerchant1.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
					finalMerchant1.setIsEzyauth(merchantsGPV.get(i).getIsEzyauth());
					finalMerchant1.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
					finalMerchant1.setIsRecplus(merchantsGPV.get(i).getIsRecplus());
					finalMerchant1.setEzywireAmt(merchantsGPV.get(i).getEzywireAmt());
					finalMerchant1.setEzymotoAmt(merchantsGPV.get(i).getEzymotoAmt());
					finalMerchant1.setEzywayAmt(merchantsGPV.get(i).getEzywayAmt());
					//finalMerchant1.setEzyrecAmt(merchantsGPV.get(i).getEzyrecAmt());
					finalMerchant1.setEzymotoVccAmt(merchantsGPV.get(i).getEzymotoVccAmt());
					finalMerchant1.setEzylinkAmt(merchantsGPV.get(i).getEzylinkAmt());
					finalMerchant1.setEzyauthAmt(merchantsGPV.get(i).getEzyauthAmt());
					//finalMerchant1.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
					finalMerchant1.setRecplusAmt(merchantsGPV.get(i).getRecplusAmt());
					if(merchantsGPV.get(i).getIsEzyrec() != null || merchantsGPV.get(i).getIsRecurring() != null) {
						
						if(merchantsGPV.get(i).getRecurringAmt() == null) {
							merchantsGPV.get(i).setRecurringAmt("0");
						}
						if(merchantsGPV.get(i).getEzyrecAmt() == null) {
							merchantsGPV.get(i).setEzyrecAmt("0");
						}
						Double tempamt=Double.parseDouble(merchantsGPV.get(i).getEzyrecAmt())+ 
								Double.parseDouble(merchantsGPV.get(i).getRecurringAmt());
						String pattern = "#,##0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						String Txn = myFormatter.format(tempamt);
						finalMerchant1.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
						finalMerchant1.setEzyrecAmt(Txn);
					}
					
					if(merchantsGPV.get(i).getMerchantType().equals("P")) {
						finalMerchant1.setMerchantType("Paydee");
					}else if(merchantsGPV.get(i).getMerchantType().equals("FIUU")) {
						finalMerchant1.setMerchantType("FIUU");
					}else {
						finalMerchant1.setMerchantType("Umobile");
					}
					
					merchantsFinalGPV.add(finalMerchant1);
					
				}
	     } 
	    

		merchantsFinalGPV.add(finalMerchant);
		
		logger.info("merchantsFinalGPV:"+ merchantsFinalGPV);
		int cDate = 0;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;

		System.out.println("cDate: "+cDate);
		
		List<Integer> listMonth = getAllMonth(cDate);
		logger.info("listMonth::"+ listMonth);
		List<MerchantGPVData> fourthMonList = new ArrayList<MerchantGPVData>();
		List<MerchantGPVData> ThirdMonList = new ArrayList<MerchantGPVData>();
		List<MerchantGPVData> SecondMonList = new ArrayList<MerchantGPVData>();
		List<MerchantGPVData> FirstMonList = new ArrayList<MerchantGPVData>();
		
		
		for(MerchantGPVData merchant1 : merchantsFinalGPV) {
			Double total = 0.00;
			Integer month = Integer.parseInt(merchant1.getMonthName());
			logger.info("month::"+ month);
			if(merchant1.getEzymotoAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzymotoAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzywayAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzywayAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzywireAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzywireAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzymotoVccAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzymotoVccAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzyrecAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzyrecAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getRecplusAmt() != null) {
				Double d = Double.parseDouble(merchant1.getRecplusAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzylinkAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzylinkAmt().replace(",", ""));
				total = total+d;
			}
			
			if(total != null) {
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String Txn = myFormatter.format(total);
				merchant1.setTotalGpv(Txn);
			}
			
			
			
			if(month == listMonth.get(0)) {
				
				fourthMonList.add(merchant1);
				logger.info("fourthMonList::"+ fourthMonList);
			}
			if(month == listMonth.get(1)) {
				
				ThirdMonList.add(merchant1);
				
				logger.info("ThirdMonList::"+ ThirdMonList);
			}
			if(month == listMonth.get(2)) {
				
				SecondMonList.add(merchant1);
				logger.info("SecondMonList::"+ SecondMonList);
			}
			if(month == listMonth.get(3)) {
				
				FirstMonList.add(merchant1);
				logger.info("FirstMonList"+ FirstMonList);
			}
			
			
		}
		
		
		
		
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("agent", agent);
		model.addAttribute("fourthMonList", fourthMonList);
		model.addAttribute("ThirdMonList", ThirdMonList);
		model.addAttribute("SecondMonList", SecondMonList);
		model.addAttribute("FirstMonList",FirstMonList);
		logger.info("listMerchantVolumeSummary completed");

		return TEMPLATE_SUPER_AGENT;
		
	}
	
	
	@RequestMapping(value = { "/merchantGPVExportByAgent" }, method = RequestMethod.GET)
	public ModelAndView merchantGPVExportByAgent(final Model model,HttpServletRequest request,
			java.security.Principal principal,
			@RequestParam("month") String id,
			@RequestParam("agId") String agId,
			@RequestParam(required = false) String export) {
		
		logger.info("about to export all merchants for this agent");
		Agent agent=agentService.loadAgentByIdPk(Long.valueOf(agId));		
		

		logger.info("Agent name"+ agent.getFirstName());
		logger.info("Agent ID"+ agent.getId());

		BigInteger agid = BigInteger.valueOf(agent.getId()); 

		//merchantService.listMerchantGPVDetails(paginationBean);
		String agentId = agent.getId().toString(); 
		List<MerchantGPVData> merchantsGPV =  merchantService.listMerchantGPVDetailsByAgent(agentId);
		
		logger.info("merchantsGPV::"+ merchantsGPV);
		//List<Merchant> merchantsGPV1 =  merchantsGPV;
		

		List<MerchantGPVData> merchantsFinalGPV = new ArrayList<MerchantGPVData>();
		
		//new change
		
		List<String> MerchantId=new ArrayList<String>();
		
		for(int i=0 ; i < merchantsGPV.size();i++) {
			MerchantId.add(merchantsGPV.get(i).getMerchantId()+"~"+merchantsGPV.get(i).getMonthName());
		}
		
		 Map<String, Integer> nameAndCount = new HashMap<>();

	     // build hash table with count
	     for (String name : MerchantId) {
	    	 logger.info("hashmap::"+ name);
	         Integer count = nameAndCount.get(name);
	         if (count == null) {
	             nameAndCount.put(name, 1);
	         } else {
	             nameAndCount.put(name, ++count);
	         }
	     }
	     MerchantGPVData finalMerchant = new MerchantGPVData();
	    
	     for(int i=0 ; i < merchantsGPV.size();i++) {

	    	 if (nameAndCount.get(merchantsGPV.get(i).getMerchantId()+"~"+merchantsGPV.get(i).getMonthName()) > 1) {
	        	 
	        	 if((!merchantsGPV.get(i).getMerchantId().equals(finalMerchant.getMerchantId()) 
	        			 ||  !merchantsGPV.get(i).getMonthName().equals(finalMerchant.getMonthName())
	        			 )
	        			 && finalMerchant.getMerchantId()!=null){	
	        		 
	        		 merchantsFinalGPV.add(finalMerchant);
	        		 finalMerchant = new MerchantGPVData();
	        	 }
					finalMerchant.setMerchantName(merchantsGPV.get(i).getMerchantName());
					finalMerchant.setAgentName(merchantsGPV.get(i).getAgentName());
					finalMerchant.setMerchantId(merchantsGPV.get(i).getMerchantId());
					finalMerchant.setMonthName(merchantsGPV.get(i).getMonthName());
					if(merchantsGPV.get(i).getMerchantType().equals("P")) {
						finalMerchant.setMerchantType("Paydee");
					}else if(merchantsGPV.get(i).getMerchantType().equals("FIUU")) {
						finalMerchant.setMerchantType("FIUU");
					}else {
						finalMerchant.setMerchantType("Umobile");
					}
					
					//EZYWAY
					if(merchantsGPV.get(i).getIsEzyway() != null) {
						
						logger.info("EZYWAY  "+merchantsGPV.get(i).getIsEzyway());
						
						finalMerchant.setIsEzyway(merchantsGPV.get(i).getIsEzyway());
						finalMerchant.setEzywayAmt(merchantsGPV.get(i).getEzywayAmt());
					}

					//EZYLINK
					if(merchantsGPV.get(i).getIsEzylink() != null) {
						
						logger.info("EZYLINK  "+merchantsGPV.get(i).getIsEzylink());
						finalMerchant.setIsEzylink(merchantsGPV.get(i).getIsEzylink());
						finalMerchant.setEzylinkAmt(merchantsGPV.get(i).getEzylinkAmt());
					}

					
					//EZYREC
					if(merchantsGPV.get(i).getIsEzyrec() != null || merchantsGPV.get(i).getIsRecurring() != null) {
						
						if(merchantsGPV.get(i).getRecurringAmt() == null) {
							merchantsGPV.get(i).setRecurringAmt("0");
						}
						if(merchantsGPV.get(i).getEzyrecAmt() == null) {
							merchantsGPV.get(i).setEzyrecAmt("0");
						}
						Double tempamt=Double.parseDouble(merchantsGPV.get(i).getEzyrecAmt())+ 
								Double.parseDouble(merchantsGPV.get(i).getRecurringAmt());
						String pattern = "#,##0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						String Txn = myFormatter.format(tempamt);
						finalMerchant.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
						finalMerchant.setEzyrecAmt(Txn);
					}

					//EZYMOTO
					if(merchantsGPV.get(i).getIsEzymoto() != null) {
						
						logger.info("EZYMOTO"+merchantsGPV.get(i).getIsEzymoto());
						finalMerchant.setIsEzymoto(merchantsGPV.get(i).getIsEzymoto());
						finalMerchant.setEzymotoAmt(merchantsGPV.get(i).getEzymotoAmt());
					}

					//EZYWIRE
					if(merchantsGPV.get(i).getIsEzywire() != null) {
						
						finalMerchant.setIsEzywire(merchantsGPV.get(i).getIsEzywire());
						finalMerchant.setEzywireAmt(merchantsGPV.get(i).getEzywireAmt());
					}

					
					//RECURRING
					/*if(merchantsGPV.get(i).getIsRecurring() != null) {
						
						finalMerchant.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
						finalMerchant.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
					}*/

					
					//RECPLUS
					if(merchantsGPV.get(i).getIsRecplus() != null) {
						
						finalMerchant.setIsRecplus(merchantsGPV.get(i).getIsRecplus());
						finalMerchant.setRecplusAmt(merchantsGPV.get(i).getRecplusAmt());
					}

					//EZYMOTO-VCC
					if(merchantsGPV.get(i).getIsEzymotoVcc() != null) {
						
						finalMerchant.setIsEzymotoVcc(merchantsGPV.get(i).getIsEzymotoVcc());
						finalMerchant.setEzymotoVccAmt(merchantsGPV.get(i).getEzymotoVccAmt());
					}

					//EZYAUTH
					if(merchantsGPV.get(i).getIsEzyauth() != null) {
						
						finalMerchant.setIsEzyauth(merchantsGPV.get(i).getIsEzyauth());
						finalMerchant.setEzyauthAmt(merchantsGPV.get(i).getEzyauthAmt());
					}

					logger.info("finalMerchant:"+ finalMerchant);
	         }
	         
	         else{
					logger.info("else condition:"+ merchantsGPV.get(i).getMerchantName());
					MerchantGPVData finalMerchant1 = new MerchantGPVData();
					finalMerchant1.setMerchantName(merchantsGPV.get(i).getMerchantName());
					finalMerchant1.setAgentName(merchantsGPV.get(i).getAgentName());
					finalMerchant1.setMerchantId(merchantsGPV.get(i).getMerchantId());
					finalMerchant1.setMonthName(merchantsGPV.get(i).getMonthName());
					finalMerchant1.setProductType(merchantsGPV.get(i).getProductType());
					finalMerchant1.setIsEzywire(merchantsGPV.get(i).getIsEzywire());
					finalMerchant1.setIsEzymoto(merchantsGPV.get(i).getIsEzymoto());
					finalMerchant1.setIsEzyway(merchantsGPV.get(i).getIsEzyway());
					finalMerchant1.setIsEzylink(merchantsGPV.get(i).getIsEzylink());
					finalMerchant1.setIsEzymotoVcc(merchantsGPV.get(i).getIsEzymotoVcc());
					finalMerchant1.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
					finalMerchant1.setIsEzyauth(merchantsGPV.get(i).getIsEzyauth());
					finalMerchant1.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
					finalMerchant1.setIsRecplus(merchantsGPV.get(i).getIsRecplus());
					finalMerchant1.setEzywireAmt(merchantsGPV.get(i).getEzywireAmt());
					finalMerchant1.setEzymotoAmt(merchantsGPV.get(i).getEzymotoAmt());
					finalMerchant1.setEzywayAmt(merchantsGPV.get(i).getEzywayAmt());
					//finalMerchant1.setEzyrecAmt(merchantsGPV.get(i).getEzyrecAmt());
					finalMerchant1.setEzymotoVccAmt(merchantsGPV.get(i).getEzymotoVccAmt());
					finalMerchant1.setEzylinkAmt(merchantsGPV.get(i).getEzylinkAmt());
					finalMerchant1.setEzyauthAmt(merchantsGPV.get(i).getEzyauthAmt());
					//finalMerchant1.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
					finalMerchant1.setRecplusAmt(merchantsGPV.get(i).getRecplusAmt());
					if(merchantsGPV.get(i).getIsEzyrec() != null || merchantsGPV.get(i).getIsRecurring() != null) {
						
						if(merchantsGPV.get(i).getRecurringAmt() == null) {
							merchantsGPV.get(i).setRecurringAmt("0");
						}
						if(merchantsGPV.get(i).getEzyrecAmt() == null) {
							merchantsGPV.get(i).setEzyrecAmt("0");
						}
						Double tempamt=Double.parseDouble(merchantsGPV.get(i).getEzyrecAmt())+ 
								Double.parseDouble(merchantsGPV.get(i).getRecurringAmt());
						String pattern = "#,##0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						String Txn = myFormatter.format(tempamt);
						finalMerchant1.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
						finalMerchant1.setEzyrecAmt(Txn);
					}
					
					if(merchantsGPV.get(i).getMerchantType().equals("P")) {
						finalMerchant1.setMerchantType("Paydee");
					}else if(merchantsGPV.get(i).getMerchantType().equals("FIUU")) {
						finalMerchant1.setMerchantType("FIUU");
					}else {
						finalMerchant1.setMerchantType("Umobile");
					}
					finalMerchant1.setTotalGpv(merchantsGPV.get(i).getTotalGpv());
					merchantsFinalGPV.add(finalMerchant1);
					
				}
	     } 
	    

		merchantsFinalGPV.add(finalMerchant);
		
		logger.info("merchantsFinalGPV:"+ merchantsFinalGPV);
		int cDate = 0;
		Date dt = new Date();
		cDate = dt.getMonth() + 1;

		System.out.println("cDate: "+cDate);
		
		List<Integer> listMonth = getAllMonth(cDate);
		logger.info("listMonth::"+ listMonth);
		List<MerchantGPVData> fourthMonList = new ArrayList<MerchantGPVData>();
		List<MerchantGPVData> ThirdMonList = new ArrayList<MerchantGPVData>();
		List<MerchantGPVData> SecondMonList = new ArrayList<MerchantGPVData>();
		List<MerchantGPVData> FirstMonList = new ArrayList<MerchantGPVData>();
		
		
		List<MerchantGPVData> FinalMonListData = new ArrayList<MerchantGPVData>();
		
		for(MerchantGPVData merchant1 : merchantsFinalGPV) {
			Double total = 0.00;
			Integer month = Integer.parseInt(merchant1.getMonthName());
			logger.info("month::"+ month);
			if(merchant1.getEzymotoAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzymotoAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzywayAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzywayAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzywireAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzywireAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzymotoVccAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzymotoVccAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzyrecAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzyrecAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getRecplusAmt() != null) {
				Double d = Double.parseDouble(merchant1.getRecplusAmt().replace(",", ""));
				total = total+d;
			}
			
			if(merchant1.getEzylinkAmt() != null) {
				Double d = Double.parseDouble(merchant1.getEzylinkAmt().replace(",", ""));
				total = total+d;
			}
			
			if(total != null) {
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String Txn = myFormatter.format(total);
				merchant1.setTotalGpv(Txn);
			}
			
			
			
			if(id.equals("fourthMon")) {			
				if(month == listMonth.get(0)) {
					
					fourthMonList.add(merchant1);
					logger.info("fourthMonList::"+ fourthMonList);
					FinalMonListData = fourthMonList;
				}
			}
			if(id.equals("thirdMon")) {		
				if(month == listMonth.get(1)) {
					
					ThirdMonList.add(merchant1);					
					logger.info("ThirdMonList::"+ ThirdMonList);
					FinalMonListData = ThirdMonList;
				}
			}
			if(id.equals("secondMon")) {		
				if(month == listMonth.get(2)) {
					
					SecondMonList.add(merchant1);
					logger.info("SecondMonList::"+ SecondMonList);
					FinalMonListData = SecondMonList;
				}
			}
			if(id.equals("firstMon")) {	
				if(month == listMonth.get(3)) {
					
					FirstMonList.add(merchant1);
					logger.info("FirstMonList"+ FirstMonList);
					FinalMonListData = FirstMonList;
				}
			}
			
		}
		


		if (!(export.equals("PDF"))) {
			logger.info("Generate Excel");
			return new ModelAndView("merchantGpvAgentExcel1", "merchantGPVList", FinalMonListData);
		} else {
			logger.info("Generate PDF");
			return new ModelAndView("merchantGpvAgentPdf1", "merchantGPVList", FinalMonListData);
		}
	}
	
	
	@RequestMapping(value = { "/toypayMerchantdashBoard" }, method = RequestMethod.GET)
	public String toypayTxnDashBoard(final Model model,final java.security.Principal principal,HttpServletRequest request) {
		logger.info("Login Person in dash Board:"+principal.getName());
		PageBean pageBean = new PageBean("Dash Board", "toyPayMerchants/dashboard",
				Module.MERCHANT, "admin/sideMenuBankUser");

		model.addAttribute("pageBean", pageBean);
		 HttpSession session = request.getSession();
		 Agent agent = agentService.loadAgent(principal.getName());
		   // String username = (String)request.getAttribute("un");
		 logger.info("display agent web : " + agent.getFirstName());
		 session.setAttribute("agentUserName",  agent.getFirstName());
		 
		    int totalDevice = dashBoardService.getAgentTotalDevice(agent.getId());
		    
		    model.addAttribute("totalDevice", totalDevice);
		    
		   String totalTxn = dashBoardService.getToypayCurrentMonthTxnByAgID(agent.getId());

		    logger.info("totalTxn: " + totalTxn);
			model.addAttribute("totalTxn", totalTxn);
			
			String totalMerchant = dashBoardService.getAgentTotalMerchant(agent.getId());
			
			model.addAttribute("totalMerchant", totalMerchant);
			logger.info("totalMerchant: " + totalMerchant);
			
			PaginationBean<DashBoardData> paginationBean = new PaginationBean<DashBoardData>();
			logger.info(" check 5 recent txn Amount Data ");
			List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
			
			List<MID> ids = new ArrayList<MID>();
		    for(Merchant t: merchant1) {
		    	ids.add(t.getMid());
		    }
		    
		    logger.info("ids:  "+ids);
		    
			StringBuffer str = new StringBuffer();
		    StringBuffer strUm = new StringBuffer();
		    List<String> midList = new ArrayList<String>();
		    List<String> ummidList = new ArrayList<String>();
		    for(MID mi : ids) {
		    	
		    	if(mi.getMid() != null) {
		    		if(!mi.getMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMid());
		    			str.append("\",");*/
		    			midList.add(mi.getMid());
		    			
		    		}
		    		
		    	}
		    	if(mi.getMotoMid() != null) {
		    		if(!mi.getMotoMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			midList.add(mi.getMotoMid());
		    		}
		    		
		    	}
		    	if(mi.getEzypassMid() != null) {
		    		if(!mi.getEzypassMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			midList.add(mi.getEzypassMid());
		    		}
		    		
		    	}
		    	
		    	if(mi.getEzywayMid() != null) {
		    		if(!mi.getEzywayMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			midList.add(mi.getEzywayMid());
		    		}
		    		
		    	}
		    	
		    	if(mi.getEzyrecMid() != null) {
		    		if(!mi.getEzyrecMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			midList.add(mi.getEzyrecMid());
		    		}
		    		
		    	}
		    	
		    	if(mi.getUmMid() != null) {
		    		if(!mi.getUmMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			midList.add(mi.getUmMid());
		    		}
		    		
		    	}
		    	
		    	if(mi.getUmMotoMid() != null) {
		    		if(!mi.getUmMotoMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			ummidList.add(mi.getUmMotoMid());
		    		}
		    		
		    	}
		    	if(mi.getUmEzywayMid() != null) {
		    		if(!mi.getUmEzywayMid().isEmpty()) {
		    			/*str.append("\"");
		    			str.append(mi.getMotoMid());
		    			str.append("\",");*/
		    			ummidList.add(mi.getUmEzywayMid());
		    		}
		    		
		    	}
		    	
		    }
		    int u =0;
		    for(String strMid : midList) {
		    	
		    	if(u == 0) {
		    		str.append("\"");
	    			str.append(strMid);
	    			str.append("\"");
	    			u++;
		    	}else {		    	
			    	str.append(",\"");
	    			str.append(strMid);
	    			str.append("\"");
		    	}
		    }
		    logger.info("String of MIDs:  "+str);
		    
		    
		    int v=0;
		    for(String strMid : ummidList) {

		    	if(v == 0) {
		    		strUm.append("\"");
		    		strUm.append(strMid);
		    		strUm.append("\"");
	    			v++;
		    	}else {		    	
		    		strUm.append(",\"");
		    		strUm.append(strMid);
		    		strUm.append("\"");
		    	}
		    
		    }
		    
		    logger.info("String of UMMIDs:  "+strUm);
		    dashBoardService.getToypayMerchantFiveTxn(paginationBean,str,strUm);
			
			logger.info(" received 5 recent  txn Amount Data ");
			model.addAttribute("fiveTxnList", paginationBean);
			
			
			List<SixMonthTxnData> sixMonData = new ArrayList<SixMonthTxnData>(); 
			
			 
			 sixMonData= dashBoardService.getToypayMerchantSixMonTxn(agent.getId(),strUm); 
			
			
			 PaginationBean<SixMonthTxnData> paginationBean1 = new PaginationBean<SixMonthTxnData>();
				List<SixMonthTxnData> data = new ArrayList<SixMonthTxnData>();
					for(SixMonthTxnData txnMonthData: sixMonData){
					
					//logger.info("check monthly count12333:" + txnMonthData );
					
					//logger.info("Controller agentDet123333 : "+txnMonthData.getCount());
					//logger.info("Controller amount23333 : "+txnMonthData.getAmount());
					//logger.info("Controller date33333 : "+txnMonthData.getDate1());
					String a3="\"";
					for(String a1:txnMonthData.getDate1()){
						a3=a3+a1;
		   			a3=a3+"\",\"";
			    	}
			    	//System.out.println("Data : "+a3);
			    	a3=a3+"\"";
			    	//System.out.println("Data : "+a3);
			    	a3=a3.replace(",\"\"", "");
			    	//System.out.println("Data : "+a3);
			    	txnMonthData.setMonth(a3);
			    	
			    	String b3="";
					for(String b1:txnMonthData.getAmount()){
						b3=b3+b1;
		   			b3=b3+",";
			    	}
			    	//System.out.println("Data : "+b3);
			    	b3=b3+",";
			    	//System.out.println("Data : "+b3);
			    	b3=b3.replace(",,", "");
			    	//System.out.println("Data : "+b3);
			    	txnMonthData.setAmountData(b3);
			    	
			    	
			    	
			    	String c3="";
					for(String c1:txnMonthData.getCount()){
						c3=c3+c1;
		   			c3=c3+",";
			    	}
			    	//System.out.println("Data : "+c3);
			    	c3=c3+",";
			    	//System.out.println("Data : "+c3);
			    	c3=c3.replace(",,", "");
			    	//System.out.println("Data : "+c3);
			    	txnMonthData.setCountData(c3); 
					
					
					
					model.addAttribute("threeMonthTxn", txnMonthData);
					 
					
					data.add(txnMonthData);
				
				}
				//End Agent 

				String a1=null,a2=null,a3=null,a4=null,a5=null,a6=null;
				 Float min = 0.0f,max=0.0f;
				for(SixMonthTxnData t: sixMonData) {
		   		logger.info(" check= Amount Data "+t.getAmountData());
		   		String[] amt=t.getAmountData().split(",");
		   		a1=amt[0];
		   		a2=amt[1];
		   		a3=amt[2];
		   		a4=amt[3];
		   		a5=amt[4];
		   		a6=amt[5];
		   		Float[] a= {Float.valueOf(a1),Float.valueOf(a2),Float.valueOf(a3)
		   				,Float.valueOf(a4),Float.valueOf(a5),Float.valueOf(a6)};

		   		Arrays.sort(a);
		   		
		   		 min = a[0]; //  assume first elements as smallest number
		   		 max = a[0]; //  assume first elements as largest number
		   		 for (int i = 1; i < a.length; i++)  // iterate for loop from arrays 1st index (second element)
		   			{
		   				if (a[i] > max) 
		   				{
		   					max = a[i];
		   				}
		   				if (a[i] < min) 
		   				{
		   					min = a[i];
		   				}
		   			}
		   		 
		   		
		   	}
				
				 logger.info("min: "+max +" max: "+min);
				
				
				//Math.round(1004/1000)*1000
				int rounded=DashboardAmount.roundNum(Math.round(max));
				logger.info("rounded value : "+rounded );
				model.addAttribute("stepsize",  rounded/10);
				model.addAttribute("max", rounded);
				paginationBean1.setItemList(data);

				AuditTrail auditTrail = agentService.updateAuditTrailByAgent(
						principal.getName(), principal.getName(), "login");

				if (auditTrail != null) {
					logger.info("Logged in by toy pay Merchant Agent : " + principal.getName());
				}
		
		model.addAttribute("paginationBean", paginationBean1);
		session.setAttribute("userName",  agent.getUsername()); 
		
		session.setAttribute("userRole",  "SUPERAGENT_USER");
		
		return TEMPLATE_TOYPAYMERCHANT;
	}
	
	@RequestMapping(value = { "/ChangeMerchantPasswordByToypay" }, method = RequestMethod.GET)
	public String changeMerchantPasswordByAgentToypay(final Model model,
	final java.security.Principal principal, HttpServletRequest request) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("Merchant",
				"toyPayMerchants/ChangePassword/MerchantDetails", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		 HttpSession session = request.getSession();
		 Agent agent = agentService.loadAgent(principal.getName());
		   
		 logger.info("display agent web : " + agent.getFirstName());

		
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("merchant1", merchant1);

		return TEMPLATE_TOYPAYMERCHANT;
	}
	
	@RequestMapping(value = { "/MerchantDetailsToChangePasswordByToypay" }, method = RequestMethod.GET)
	public String dispTermsandConditonsbyToypay(final Model model,HttpServletRequest request,
			@RequestParam("mid") Long id,/*
										 * @ModelAttribute("merchantuser") final
										 * MerchtCustMail regMobileUser,
										 */
			final java.security.Principal principal) {
		logger.info("/MerchantDetailsToChangePassword: " + id);
		
		
		
		Merchant merchant = merchantService.loadMerchantByPk(id);

		PageBean pageBean = new PageBean("Merchant",
				"toyPayMerchants/ChangePassword/MerchantDetails", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		 HttpSession session = request.getSession();
		 Agent agent = agentService.loadAgent(principal.getName());
		   
		 logger.info("display agent web : " + agent.getFirstName());
		logger.info("admin login person:" + principal.getName());

		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
		List<MobileUser> mobileuser = promotionService
				.loadMobileUserDetails(merchant.getId());
		
		model.addAttribute("pageBean", pageBean);
		
		model.addAttribute("merchant", merchant);
		model.addAttribute("merchant1", merchant1);
		
		model.addAttribute("mobileuser", mobileuser);

		return TEMPLATE_TOYPAYMERCHANT;
	}
	
	@RequestMapping(value = { "/ChangeMerchPassByToypay" }, method = RequestMethod.GET)
	public String MerchantChangePasswordByToypay(final Model model,
			final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("id") Long id,
			//@RequestParam("mid") String mid,
			//@RequestParam("motoMid") String motoMid,
			@RequestParam("merchantPass") String merchantPass,
			@RequestParam("businessName") String businessName) {

		logger.info("check merchant id : " + id);
	//	logger.info("check mid : " + mid);
		Merchant merchant = merchantService.loadMerchantbyid(id);
		// logger.info("admin merchant new password "+merchantService.generatePassword());
		// logger.info("admin  merchant  uname "+merchant.getUsername());
		merchant.setPassword(merchantPass);
		// logger.info("merchant getpass: "+merchant.getPassword());
		merchant = merchantService
				.changeMerchantPassWordByAdminManualy(merchant);
		logger.info("display merchant name:" + merchant.getUsername()
				+ "first name: " + merchant.getFirstName());

		if (merchant != null) {

			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(
					merchant.getUsername(), principal.getName(),
					"resetPassword");
			if (auditTrail.getUsername() != null) {
				logger.info("Merchant :" + auditTrail.getUsername()
						+ " Password Successfully changed by agent: "
						+ auditTrail.getModifiedBy());
				;
			}
		}

		PageBean pageBean = new PageBean("Merchant",
				"toyPayMerchants/ChangePassword/PasswordChangedSuccess",
				Module.MERCHANT, "merchant/sideMenuMerchant");
		model.addAttribute("msg",
				"Merchant Password Changed Successfully by Agent '"
						+ principal.getName() + "'");
		// model.addAttribute("mid", mid);
		model.addAttribute("merchant", merchant);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("newPassword", merchantPass);
		return TEMPLATE_TOYPAYMERCHANT;
	}

	@RequestMapping(value = { "/ChangeMobileuserPassByToypay" }, method = RequestMethod.GET)
	public String MobileuserChangePasswordByToypay(final Model model,
			final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("merchant_Id") Long merchant_Id,
			@RequestParam("mobileuserPass") String mobileuserPass,
			
			@RequestParam("mobileUserId") Long id) {
		logger.info("check : /ChangeMobileuserPass");
		
		String mid=null;
		
		logger.info(merchant_Id + " : " + id);
		Merchant merchant = merchantService.loadMerchantbyid(merchant_Id);

		if(merchant.getMid().getMid()!=null) {
			mid=merchant.getMid().getMid();
		}else if(merchant.getMid().getMotoMid()!=null) {
			mid=merchant.getMid().getMotoMid();
		}else {
			mid=merchant.getMid().getEzypassMid();
		}
		
		logger.info(" merchant username " + merchant.getUsername());

		MobileUser mobileuser = mobileUserService.loadMobileUserByPk(id);
		logger.info("mobileuser name: " + mobileuser.getUsername());
		mobileuser.setPassword(mobileuserPass);

		mobileuser = merchantService
				.changeMobileuserPassWordByAdminManualy(mobileuser);

		if (mobileuser != null) {

			AuditTrail auditTrail = adminService.updateAuditTrailByAdmin(
					merchant.getUsername(), principal.getName(),
					"resetPassword");
			if (auditTrail.getUsername() != null) {
				logger.info("Merchant :" + auditTrail.getUsername()
						+ " Password Successfully changed by Agent: "
						+ auditTrail.getModifiedBy());
				;
			}
		}

		PageBean pageBean = new PageBean("Merchant",
				"toyPayMerchants/ChangePassword/PasswordChangedSuccess",
				Module.MERCHANT, "merchant/sideMenuMerchant");
		model.addAttribute("msg",
				"MobileUser Password Changed Successfully by Agent "
						+ principal.getName());
		model.addAttribute("mid", mid);
		model.addAttribute("mobileUserName", mobileuser.getUsername());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("newPassword", mobileuserPass);
		return TEMPLATE_TOYPAYMERCHANT;
	}
	
	
	@RequestMapping(value = { "/transaction/searchUMEzyway" }, method = RequestMethod.GET)
	public String umEzywayList(HttpServletRequest request,final java.security.Principal principal,
			final Model model,
			@RequestParam final String date,
			@RequestParam final String date1,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search  UM-MOTO Transaction ");
		PageBean pageBean = new PageBean("transactions list",
				"toyPayMerchants/transaction/transactionUMEzywayList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		 HttpSession session = request.getSession();
		 Agent agent = agentService.loadAgent(principal.getName());
		   
		 logger.info("display agent web : " + agent.getFirstName());

		
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
		
		List<MID> ids = new ArrayList<MID>();
	    for(Merchant t: merchant1) {
	    	ids.add(t.getMid());
	    }
	    
	    logger.info("ids:  "+ids);
	    
	    List<String> umezywaymidList = new ArrayList<String>();
	    
	    for(MID mi : ids) {
	    	
	    	if(mi.getUmEzywayMid() != null) {
	    		if(!mi.getUmEzywayMid().isEmpty()) {
	    			umezywaymidList.add(mi.getUmEzywayMid());
	    		}
	    		
	    	}
	    	
	    }
		
	    logger.info("umezywaymidList:  "+umezywaymidList);
	    StringBuffer strUm = new StringBuffer();
	    int j=0;
	    for(String strMid : umezywaymidList) {

	    	if(j == 0) {
	    		strUm.append("\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
   			j++;
	    	}else {		    	
	    		strUm.append(",\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
	    	}
	    
	    }
	    

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listUMEzywayTransactionByAgent(paginationBean, date, date1,strUm,"EZYWAY");
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_TOYPAYMERCHANT;

	}
	
	@RequestMapping(value = "/transaction/umEzywayExport", method = RequestMethod.GET)
	public ModelAndView getExportUMEzyway(
			final Model model,
			final java.security.Principal principal,
			@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//			@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export,HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
	 
		HttpSession session = request.getSession();
		 Agent agent = agentService.loadAgent(principal.getName());
		   
		 logger.info("display agent web : " + agent.getFirstName());

		
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
		
		List<MID> ids = new ArrayList<MID>();
	    for(Merchant t: merchant1) {
	    	ids.add(t.getMid());
	    }
	    
	    logger.info("ids:  "+ids);
	    
	    List<String> umezywaymidList = new ArrayList<String>();
	    
	    for(MID mi : ids) {
	    	
	    	if(mi.getUmEzywayMid() != null) {
	    		if(!mi.getUmEzywayMid().isEmpty()) {
	    			umezywaymidList.add(mi.getUmEzywayMid());
	    		}
	    		
	    	}
	    	
	    }
		
	    logger.info("umEzywaymidList:  "+umezywaymidList);
	    StringBuffer strUm = new StringBuffer();
	    int j=0;
	    for(String strMid : umezywaymidList) {

	    	if(j == 0) {
	    		strUm.append("\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
  			j++;
	    	}else {		    	
	    		strUm.append(",\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
	    	}
	    
	    }
	    
	
		
		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		/*transactionService.exportUMMotoTransactionAdmin(paginationBean, dat, dat1,"ALL");*/
		
		transactionService.exportUMEzywayTransactionAgent(paginationBean, dat, dat1,strUm,"EZYWAY");
		logger.info("No of Records: "+paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty()
				|| paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}
		
		
		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("hotelMerchantUmExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("hotelMerchantUmPdf", "umTxnList", list1);
		}
	 
 }
	
	
	@RequestMapping(value = { "/transaction/umEzywayList/{currPage}" }, method = RequestMethod.GET)
	public String umEzywayList(final Model model,HttpServletRequest request,
	@PathVariable final int currPage, final java.security.Principal principal) {
		logger.info(" UM-Ezyway Transaction Summary admin");
		PageBean pageBean = new PageBean("transactions list",
				"toyPayMerchants/transaction/transactionUMEzywayList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		 HttpSession session = request.getSession();
		 Agent agent = agentService.loadAgent(principal.getName());
		   
		 logger.info("display agent web : " + agent.getFirstName());

		
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
		
		List<MID> ids = new ArrayList<MID>();
	    for(Merchant t: merchant1) {
	    	ids.add(t.getMid());
	    }
	    
	    logger.info("ids:  "+ids);
	    
	    List<String> umezywaymidList = new ArrayList<String>();
	    
	    for(MID mi : ids) {
	    	
	    	if(mi.getUmEzywayMid() != null) {
	    		if(!mi.getUmEzywayMid().isEmpty()) {
	    			umezywaymidList.add(mi.getUmEzywayMid());
	    		}
	    		
	    	}
	    	
	    }
		
	    logger.info("umezywaymidList:  "+umezywaymidList);
	    StringBuffer strUm = new StringBuffer();
	    int j=0;
	    for(String strMid : umezywaymidList) {

	    	if(j == 0) {
	    		strUm.append("\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
    			j++;
	    	}else {		    	
	    		strUm.append(",\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
	    	}
	    
	    }
	    
		logger.info(" UM-Ezyway Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);
		/*transactionService.listUMMotoTransaction(paginationBean, null, null, "ALL");*/
		
		transactionService.listUMEzywayTransactionByAgent(paginationBean, null, null,strUm, "EZYWAY");
		if (paginationBean.getItemList().isEmpty()
				|| paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_TOYPAYMERCHANT;
	}
	
	
	@RequestMapping(value = { "/getToypayMerchantRecentTxn" }, method = RequestMethod.GET)
	public String getToypayMerchantRecentTxn(
			final Model model,final java.security.Principal principal) {
		logger.info("Login Person in dash Board:"+principal.getName());
		
		Agent agent = agentService.loadAgent(principal.getName());
		PaginationBean<DashBoardData> txnPaginationBean = new PaginationBean<DashBoardData>();
		logger.info(" check first 100 txn Amount Data ");
		List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
		
		List<MID> ids = new ArrayList<MID>();
	    for(Merchant t: merchant1) {
	    	ids.add(t.getMid());
	    }
	    
	    logger.info("ids:  "+ids);
	    
		StringBuffer str = new StringBuffer();
	    StringBuffer strUm = new StringBuffer();
	    List<String> midList = new ArrayList<String>();
	    List<String> ummidList = new ArrayList<String>();
	    for(MID mi : ids) {
	    	
	    	if(mi.getMid() != null) {
	    		if(!mi.getMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMid());
	    			str.append("\",");*/
	    			midList.add(mi.getMid());
	    			
	    		}
	    		
	    	}
	    	if(mi.getMotoMid() != null) {
	    		if(!mi.getMotoMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getMotoMid());
	    		}
	    		
	    	}
	    	if(mi.getEzypassMid() != null) {
	    		if(!mi.getEzypassMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getEzypassMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getEzywayMid() != null) {
	    		if(!mi.getEzywayMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getEzywayMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getEzyrecMid() != null) {
	    		if(!mi.getEzyrecMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getEzyrecMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getUmMid() != null) {
	    		if(!mi.getUmMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			midList.add(mi.getUmMid());
	    		}
	    		
	    	}
	    	
	    	if(mi.getUmMotoMid() != null) {
	    		if(!mi.getUmMotoMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			ummidList.add(mi.getUmMotoMid());
	    		}
	    		
	    	}
	    	if(mi.getUmEzywayMid() != null) {
	    		if(!mi.getUmEzywayMid().isEmpty()) {
	    			/*str.append("\"");
	    			str.append(mi.getMotoMid());
	    			str.append("\",");*/
	    			ummidList.add(mi.getUmEzywayMid());
	    		}
	    		
	    	}
	    	
	    }
	    int u =0;
	    for(String strMid : midList) {
	    	
	    	if(u == 0) {
	    		str.append("\"");
    			str.append(strMid);
    			str.append("\"");
    			u++;
	    	}else {		    	
		    	str.append(",\"");
    			str.append(strMid);
    			str.append("\"");
	    	}
	    }
	    logger.info("String of MIDs:  "+str);
	    
	    
	    int v=0;
	    for(String strMid : ummidList) {

	    	if(v == 0) {
	    		strUm.append("\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
    			v++;
	    	}else {		    	
	    		strUm.append(",\"");
	    		strUm.append(strMid);
	    		strUm.append("\"");
	    	}
	    
	    }
	    
	    logger.info("String of UMMIDs:  "+strUm);
		
	   
	    dashBoardService.getToypayMerchantLastHundredUmTxn(txnPaginationBean,str,strUm);
		
		logger.info(" received first 100 txn Amount Data ");
		
		PageBean pageBean = new PageBean("Dash Board", "toyPayMerchants/completeAgReport",
				Module.ADMIN, "admin/sideMenuBankUser");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("fiveTxnList", txnPaginationBean);
		return TEMPLATE_TOYPAYMERCHANT;
	}
	
	@RequestMapping(value = { "/superagentProfportal/toypayPassDetails" }, method = RequestMethod.GET)
	public String toypayPassDetails(final Model model, final Principal principal) {
		PageBean pageBean = new PageBean("agent Details", "toyPayMerchants/superagentProfile",
				Module.AGENT, "agentweb/sideMenuMerchantWeb");
		Agent agent = agentService.loadAgent(principal.getName());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("agent", agent);
		return TEMPLATE_TOYPAYMERCHANT;
	}
	
	@RequestMapping(value = { "/superagentProfportal/toypaychangePassWord" }, method = RequestMethod.GET)
	public String toypaychangePassword(final Model model, final Principal principal,HttpServletRequest request) {
		logger.info("Login Person change password");
		PageBean pageBean = new PageBean("agentProfile", "toyPayMerchants/superagentchangePassword",
				Module.AGENT, "agentweb/sideMenuAgentWeb");
		model.addAttribute("pageBean", pageBean);
		HttpSession session = request.getSession();
	    session.setAttribute("agentUserName",  principal.getName());
		return TEMPLATE_TOYPAYMERCHANT;
	}
	
	@RequestMapping(value = { "/superagentProfdetails/confirmPasswordbytoypay" }, method = RequestMethod.POST)
	public String toypayconfirmPassword(final Model model, final Principal principal,HttpServletRequest request) {
		logger.info("Old PassWord"+request.getParameter("password")+"New Password"+request.getParameter("newPassword"));
		int count=agentService.changeAgentPassWord(principal.getName(), request.getParameter("newPassword"), request.getParameter("password"));
		PageBean pageBean=null;
		if(count==1){
			
		 pageBean = new PageBean("Agent Profile", "toyPayMerchants/superagentChangePasswordSuccess",
				Module.AGENT, "agentweb/sideMenuAgentWeb");
		}else{
			
			 pageBean = new PageBean("Agent Profile", "toyPayMerchants/superagentChangePasswordFailure",
						Module.AGENT, "agentweb/sideMenuAgentWeb");
		}
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_TOYPAYMERCHANT;
	}



}
