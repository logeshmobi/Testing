package com.mobiversa.payment.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MotoWebService;
import com.mobiversa.payment.service.TransactionService;

@Controller
@RequestMapping(value = MerchantWebRecurringListController.URL_BASE)
public class MerchantWebRecurringListController extends BaseController {

	
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private MotoWebService motoWebService;
	
	/*@Autowired
	MotoTransaction mototxn;*/
	

	public static final String URL_BASE = "/recurring";
	private static final Logger logger = Logger
			.getLogger(MerchantWebRecurringListController.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("Test1 defaultpage");
		return "redirect:" + URL_BASE + "/list/1";
	}

	
	
	
	
	
}
