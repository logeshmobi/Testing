package com.mobiversa.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.Batch;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.Transaction;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.SettlementsService;
import com.mobiversa.payment.service.TransactionService;

@Controller
@RequestMapping(value = CreditSettlementsController.URL_BASE)
public class CreditSettlementsController extends BaseController {

	@Autowired
	private SettlementsService settlementService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;

	public static final String URL_BASE = "/settlements";

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayTransactionSummary(final Model model,

	@PathVariable final int currPage) {
		logger.info("about to list all  transaction");
		PageBean pageBean = new PageBean("transactions list", "creditsettlements/creditSettlementList",
				Module.SETTLEMENT, "creditsettlements/sideMenuCreditSettlement");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<Batch> paginationBean = new PaginationBean<Batch>();
		paginationBean.setCurrPage(currPage);
		settlementService.listSettlements(paginationBean);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/viewAllCreditSettlement/{id}" }, method = RequestMethod.GET)
	public String displayAllCreditSettlements(final Model model, @PathVariable final long id,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list all  transaction");
		PageBean pageBean = new PageBean("View All Credit Settlements", "creditsettlements/creditSettlementView",
				Module.SETTLEMENT, "creditsettlements/sideMenuCreditSettlement");
		model.addAttribute("pageBean", pageBean);
		Merchant merchant = merchantService.loadMerchantByPk(id);
		PaginationBean<Batch> paginationBean = new PaginationBean<Batch>();
		paginationBean.setCurrPage(currPage);
		settlementService.listSettlements(paginationBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/batchDetails/{id}" }, method = RequestMethod.GET)
	public String displayBatchDetails(final Model model, @PathVariable final long id,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list all  transaction");
		PageBean pageBean = new PageBean("View Transaction Details", "creditsettlements/successfulBatch",
				Module.SETTLEMENT, "creditsettlements/sideMenuCreditSettlement");
		model.addAttribute("pageBean", pageBean);
		// PaginationBean<Batch> paginationBean = new PaginationBean<Batch>();
		PaginationBean<Transaction> paginationBean = new PaginationBean<Transaction>();
		paginationBean.setCurrPage(currPage);
		Transaction transaction = transactionService.loadTransactionByPk(id);
		Batch batch = transaction.getBatch();
		// settlementService.listSettlements(paginationBean);
		// model.addAttribute("merchant", merchant);
		settlementService.listBatchStatusBased(paginationBean, batch);
		model.addAttribute("transaction", transaction);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/viewTransactionDetails/{id}" }, method = RequestMethod.GET)
	public String displayTransactionDetails(final Model model, @PathVariable final long id,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("about to list all  transaction");
		PageBean pageBean = new PageBean("View Transaction Details", "creditsettlements/viewTransactionDetails",
				Module.SETTLEMENT, "creditsettlements/sideMenuCreditSettlement");
		model.addAttribute("pageBean", pageBean);
		Merchant merchant = merchantService.loadMerchantByPk(id);

		model.addAttribute("merchant", merchant);

		return TEMPLATE_DEFAULT;
	}
}
