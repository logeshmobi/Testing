package com.mobiversa.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobiversa.common.bo.Batch;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.SettlementsService;

@Controller
@RequestMapping(value = MerchantWebSettlementController.URL_BASE)
public class MerchantWebSettlementController extends BaseController {

	@Autowired
	private SettlementsService settlementService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private MobileUserService mobileUserService;
	public static final String URL_BASE = "/settlementweb";

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayMerchantSettlementSummary(final Model model,

	@PathVariable final int currPage, final java.security.Principal principal) {
		//logger.info("about to list all  transaction");

		PageBean pageBean = new PageBean("Settlements  list", "merchantweb/creditsettlement/creditSettlementList",
				Module.SETTLEMENT_WEB, "merchantweb/creditsettlement/sideMenuCreditSettlement");
		model.addAttribute("pageBean", pageBean);
		
		String myName = principal.getName();
		//logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		logger.info("the merchant obj based on currently logged in user is: " + currentMerchant);

		PaginationBean<Batch> paginationBean = new PaginationBean<Batch>();
		paginationBean.setCurrPage(currPage);
		// settlementService.listSettlements(paginationBean);
		settlementService.listSettlementsMerchant(paginationBean,currentMerchant);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}
}
