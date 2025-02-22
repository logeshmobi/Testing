package com.mobiversa.payment.controller;

import org.apache.log4j.Logger;

public class BaseController {

	protected final String TEMPLATE_DEFAULT = "template/tpl_default";
	protected final String TEMPLATE_MERCHANT = "template/tpl_merchant";
	protected final String TEMPLATE_NONMERCHANT = "template/tpl_nonMerchant";
	protected final String TEMPLATE_MOBILITEMERCHANT = "template/tpl_mobiliteMerchant";
	protected final String TEMPLATE_SETTLEMENTUSER = "template/tpl_settlementUser";
	protected final String TEMPLATE_PAYOUTUSER = "template/tpl_payoutUser";
	protected final String TEMPLATE_AGENT = "template/tpl_agent";
	protected final String TEMPLATE_HOTELMERCHANT = "template/tpl_hotelMerchant";
	protected final String TEMPLATE_TOYPAYMERCHANT = "template/tpl_toypayMerchant";
	protected final String TEMPLATE_AGENT1 = "template/tpl_agentEmpty";

	// new test payout
	protected final String TEMPLATE_TEST_PAYOUT = "template/tpl_payoutTest";
//	protected final String TEMPLATE_TEST_PAYOUT = "PayoutUser/TestPayout/PayoutRequest";

	protected final String TEMPLATE_DGTECH = "template/tpl_dgTech";

	protected final String MODEL_ATTRIBUTE_PAGE_BEAN = "pageBean";
	protected final Logger logger = Logger.getLogger(BaseController.class.getName());
	protected static final String REDIRECT_LOGIN = "redirect:/auth/login";
	// new template for superAgent 18-04-2017
	protected final String TEMPLATE_SUPER_AGENT = "template/tpl_superAgent";
	protected final String TEMPLATE_NEW = "template/tpl_new";

	// end
	public enum SESSION_ATTRIBUTE {

	}

}
