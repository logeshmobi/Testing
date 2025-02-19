package com.mobiversa.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.ReportForSettlement;
import com.mobiversa.payment.service.UnuserMerchantService;


@Controller
@RequestMapping(value = UnuserMerchantController.URL_BASE)
public class UnuserMerchantController extends BaseController {
	
	@Autowired
	private UnuserMerchantService unuserMerchantService;
	
	public static final String URL_BASE = "/unusedmerchant";
	
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		return "redirect:" + URL_BASE + "/list/1";
	}
	
	
	
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayTransactionSummary(final Model model,final java.security.Principal principal,
	@PathVariable final int currPage) {
		logger.info("about to list all  transaction");
		PageBean pageBean = new PageBean("merchant list", "merchantreport/merchantUnusedList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ReportForSettlement> paginationBean = new PaginationBean<ReportForSettlement>();
		logger.info("PreAuth Summary :" + principal.getName());
		paginationBean.setCurrPage(currPage);
		//unuserMerchantService.listPreAuthTransaction(paginationBean, null, null);
		unuserMerchantService.unusedMerchantList(paginationBean,"180");
		if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null || paginationBean.getItemList().size() == 0 )
		{
			model.addAttribute("responseData", "No Records found"); //table response
		}else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}
	
	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String displayTransactionList(final Model model, @RequestParam final String days,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list all  Transactiona");
		PageBean pageBean = new PageBean("merchant list", "merchantreport/merchantUnusedList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ReportForSettlement> paginationBean = new PaginationBean<ReportForSettlement>();
		paginationBean.setCurrPage(currPage);

		//transactionService.listPreAuthTransaction(paginationBean, date, date1);
		unuserMerchantService.unusedMerchantList(paginationBean,days);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}

}
