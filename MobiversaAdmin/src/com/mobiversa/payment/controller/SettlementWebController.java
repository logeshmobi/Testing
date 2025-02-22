package com.mobiversa.payment.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.SettlementWebService;

@Controller
@RequestMapping(value = SettlementWebController.URL_BASE)
public class SettlementWebController extends BaseController {
	
	
	public static final String URL_BASE = "/settlementweb12";
	
	@Autowired
	private SettlementWebService settlementWebService;
	@Autowired
	private MerchantService merchantService;
	
	
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		return "redirect:" + URL_BASE + "/list/1";
	}
	
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displaySettlementWeb(final Model model,final java.security.Principal principal,
			HttpServletRequest request,

	@PathVariable final int currPage, @RequestParam(required = false, defaultValue = "") final String  responseData) {
		logger.info("MerchantWeb list all settlement transaction");
		PageBean pageBean = new PageBean("transactions list", "merchantweb/settlementweb/transactionList", Module.SETTLEMENT_WEB12,
				"merchantweb/sideMenuMerchantWebMobile");
		model.addAttribute("pageBean", pageBean);
		//System.out.println("Merchant Name : "+principal.getName());
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		HttpSession session=request.getSession();
		// logger.info("about to list all  transaction");
		String myName = (String) session.getAttribute("userName");
		//settlementWebService.listSettlements(paginationBean,principal.getName());
		settlementWebService.listSettlements(paginationBean,myName);
		logger.info( "" + ":"+ myName );
		if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null || paginationBean.getItemList().size() == 0)
		{
			model.addAttribute("responseDWebSettlement logged by:ata", "No Transaction found"); //table response
		}else if (responseData == null || responseData.equals("")){
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("responseData1", responseData);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}
	

	@RequestMapping(value = { "/details" }, method = RequestMethod.POST)
	//@RequestMapping(value = { "/details" }, method = RequestMethod.GET)
	public String sentSettlementWeb(final Model model,final Principal principal,HttpServletRequest request,
			/*@PathVariable final String tid,*/@RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam("tid") final String tid) {
		logger.info("about to list all settlement sentSettlementWeb :"+tid);
		
		PageBean pageBean = new PageBean("transactions list", "merchantweb/settlementweb/transactionList", Module.SETTLEMENT_WEB12,
				"merchantweb/sideMenuMerchantWebMobile");
		model.addAttribute("pageBean", pageBean);
		
		HttpSession session=request.getSession();
		// logger.info("about to list all  transaction");
		String myName = (String) session.getAttribute("userName");
		 logger.info("myName::"+myName);
		  Merchant merchant = merchantService.loadMerchant(myName);
		  logger.info("principal.getName():"+principal.getName());
		  logger.info("merchant::"+merchant);
		
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		//String data =settlementWebService.sentSettlement(principal.getName(),tid);
		String data =settlementWebService.sentSettlement(myName,tid,merchant);
		//String data ="succeeded";
		logger.info(tid + ":" + "WebSettlement logged by:" + ":"+ myName);
		//System.out.println(" Controller resData : "+ data);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("responseData", data);
		//RedirectAttributesModelMap.addFlashAttribute("employee", data);
		//return TEMPLATE_MERCHANT;
		return "redirect:" + URL_BASE + "/list/1";
	}

	
	

}
