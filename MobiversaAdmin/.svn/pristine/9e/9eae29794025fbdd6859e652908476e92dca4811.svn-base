package com.mobiversa.payment.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.dto.MonthlyTxnDetails;
import com.mobiversa.payment.dto.ReportForSettlement;
import com.mobiversa.payment.service.MonthlyTransactionService;
import com.mobiversa.payment.service.UnuserMerchantService;

@Controller
@RequestMapping(value = MonthlyTransactionController.URL_BASE)
public class MonthlyTransactionController extends BaseController {
	
	@Autowired
	private MonthlyTransactionService monthlyTransactionService;
	
	public static final String URL_BASE = "/monthlytxn";
	
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		return "redirect:" + URL_BASE + "/list/1";
	}
	
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayMonthlyTrnSummary(final Model model,final java.security.Principal principal,

	@PathVariable final int currPage) {
		logger.info("about to list all  transaction");
		PageBean pageBean = new PageBean("merchant list", "merchantreport/monthlytransaction", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<MonthlyTxnDetails> paginationBean = new PaginationBean<MonthlyTxnDetails>();
		logger.info("PreAuth Summary :" + principal.getName());
		paginationBean.setCurrPage(currPage);
		//unuserMerchantService.listPreAuthTransaction(paginationBean, null, null);
		//unuserMerchantService.unusedMerchantList(paginationBean,"180");
		monthlyTransactionService.monthlyTxnMerchantList(paginationBean, null, null);
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
	public String displayMonthlyTrnList(final Model model, @RequestParam final String month,@RequestParam final String year,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list all  Transactiona");
		PageBean pageBean = new PageBean("merchant list", "merchantreport/monthlytransaction", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<MonthlyTxnDetails> paginationBean = new PaginationBean<MonthlyTxnDetails>();
		paginationBean.setCurrPage(currPage);

		//transactionService.listPreAuthTransaction(paginationBean, date, date1);
		//unuserMerchantService.unusedMerchantList(paginationBean,days);
		monthlyTransactionService.monthlyTxnMerchantList(paginationBean, month, year);
		
		
		logger.info("check monthly transaction details:" + month);
		
		logger.info("monthly transaction details:" + year);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}
	
	 @RequestMapping(value = { "/export" }, method = RequestMethod.GET)
		public ModelAndView getExcel(@RequestParam(required = false ) final String month,
			@RequestParam(required = false) final String year,
			@RequestParam (required = false ) String export,
			@RequestParam(required = false, defaultValue = "1") final int currPage) throws ParseException {
		logger.info("about to list all  Transactiona :"+export);
		
		List<MonthlyTxnDetails> list = monthlyTransactionService.monthlyTxnMerchantExport(month, year);
		
		
		if(!(export.equals("PDF"))){
			return new ModelAndView("txnMListExcel", "txnList", list);
		}else{
			logger.info("about to list all  Transactiona :"+export);
			return new ModelAndView("txnMListPdf", "txnList", list);
		}


		
	 }

}
