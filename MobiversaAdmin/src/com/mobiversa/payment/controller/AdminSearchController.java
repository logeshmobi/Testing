package com.mobiversa.payment.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.FpxTransaction;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.SettlementModel;
import com.mobiversa.payment.util.UMEzyway;

@Controller
@RequestMapping(value = AdminSearchController.URL_BASE)
public class AdminSearchController extends BaseController {
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;

	public static final String URL_BASE = "/adminsearchNew";

	private static final Logger logger = Logger.getLogger(AdminSearchController.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		// logger.info("Test1 defaultpage");
		return "redirect:" + URL_BASE + "/find";
	}

	@RequestMapping(value = { "/find" }, method = RequestMethod.GET)
	public String FindResults(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
			@RequestParam final String VALUE, @RequestParam final String TXNTYPE, @RequestParam final String Type) {

		logger.info("We PASS VALUE from JSP : " + VALUE);
		logger.info("We Pass TXNTYPE from JSP : " + TXNTYPE);
		logger.info("We Pass TYPE from JSP : " + Type);
		model.addAttribute("role",transactionService.getRoleFromUserName(principal.getName()));

		if (TXNTYPE.equals("FPX1")) {

			PageBean pageBean = new PageBean("transactions list", "transaction/FPXSummaryList", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			logger.info("Transaction Summary:" + principal.getName());
			PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) {
				logger.info("Admin search Reference No :" + VALUE);
//				transactionService.listFPXTransaction(paginationBean, null, null,null,"NULL");
				transactionService.listFPXTransaction(paginationBean, null, null, VALUE, "FPX1");

				model.addAttribute("paginationBean", paginationBean);
			}

			if (Type.equals("Ap_Code")) {
				logger.info("Admin search Txn Id(Aproval_Code) :" + VALUE);
				transactionService.listFPXTransaction(paginationBean, null, null, VALUE, "FPX2");

				model.addAttribute("paginationBean", paginationBean);
			}
		}
		else if(TXNTYPE.equals("FPX2")){
			PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionFPXFailList", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			logger.info("TransactionFailedSummary:" + principal.getName());
			PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) {
				logger.info("Txn Type is FPX2 and Search using Ref No");
				logger.info("Admin search Reference No :" + VALUE);
//				transactionService.listFPXTransaction(paginationBean, null, null,null,"NULL");
				transactionService.listFPXfailedTransaction(paginationBean, null, null, VALUE, "FPX3");

				model.addAttribute("paginationBean", paginationBean);
			}

			if (Type.equals("Ap_Code")) {
				logger.info("Admin search Txn Id(Aproval_Code) :" + VALUE);
				transactionService.listFPXfailedTransaction(paginationBean, null, null, VALUE, "FPX4");

				model.addAttribute("paginationBean", paginationBean);
			}
			

			return TEMPLATE_MERCHANT;
		}



		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/findGrab" }, method = RequestMethod.GET)
	public String FindResults1(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
			@RequestParam final String VALUE, @RequestParam final String TXNTYPE, @RequestParam final String Type) {

		logger.info(" VALUE  : " + VALUE);
		logger.info(" TXNTYPE : " + TXNTYPE);
		logger.info(" TYPE : " + Type);

		if (TXNTYPE.equals("GRAB")) {

			HttpSession session = request.getSession();
			String myName = (String) session.getAttribute("userName");
			logger.info("currently logged in as " + myName);
			Merchant currentMerchant = merchantService.loadMerchant(myName);

			PageBean pageBean = new PageBean("transactions list", "transaction/GrabpaySummaryList", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
			paginationBean.setCurrPage(currPage);

			model.addAttribute("pageBean", pageBean);

//			
//			PageBean pageBean = new PageBean("transactions list", "transaction/GrabpaySummaryList", Module.TRANSACTION,
//					"transaction/sideMenuTransaction");
//			model.addAttribute("pageBean", pageBean);
//			logger.info("Transaction Summary:" + principal.getName());
//			PaginationBean<FpxTransaction> paginationBean = new PaginationBean<FpxTransaction>();
//			paginationBean.setCurrPage(currPage);
//			
			if (Type.equals("Stan")) {
				logger.info("Admin search Stan No :" + VALUE);
//			 transactionService.listFPXTransaction(paginationBean, null, null,VALUE,"GRAB");
				transactionService.ListGrabpaySummaryAdmin(paginationBean, currentMerchant, null, null, VALUE, "GRAB1",null);
				model.addAttribute("paginationBean", paginationBean);
			}

			if (Type.equals("Sub_Mid")) {
				logger.info("Admin search Sub merchant Mid :" + VALUE);
//				 transactionService.listFPXTransaction(paginationBean, null, null,VALUE,"GRAB");
				transactionService.ListGrabpaySummaryAdmin(paginationBean, currentMerchant, null, null, VALUE, "GRAB2",null);
				model.addAttribute("paginationBean", paginationBean);
			}
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/findBoost" }, method = RequestMethod.GET)
	public String FindResultsBoost(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
			@RequestParam final String VALUE, @RequestParam final String TXNTYPE, @RequestParam final String Type) {

		logger.info(" VALUE  : " + VALUE);
		logger.info(" TXNTYPE : " + TXNTYPE);
		logger.info(" TYPE : " + Type);

		if (TXNTYPE.equals("BOOST")) {

			PageBean pageBean = new PageBean("transactions list", "transaction/BoostSummaryList", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			logger.info("Transaction Summary:" + principal.getName());
			PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) {
				logger.info("Admin search Reference No :" + VALUE);
				transactionService.listAllForsettlementTransactionSearchAPI(paginationBean, null, null, "BOOST1",
						VALUE);

				model.addAttribute("paginationBean", paginationBean);
			}

			if (Type.equals("RRN")) {
				logger.info("Admin RRN :" + VALUE);
				transactionService.listAllForsettlementTransactionSearchAPI(paginationBean, null, null, "BOOST2",
						VALUE);
				model.addAttribute("paginationBean", paginationBean);
			}
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/findM1pay" }, method = RequestMethod.GET)
	public String FindResultsM1PAY(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
			@RequestParam final String VALUE, @RequestParam final String TXNTYPE, @RequestParam final String Type) {

		logger.info(" VALUE  : " + VALUE);
		logger.info(" TXNTYPE : " + TXNTYPE);
		logger.info(" TYPE : " + Type);

		if (TXNTYPE.equals("M1PAY")) {

			logger.info("M1 PAY SUMMARY BY ADMIN");
			PageBean pageBean = new PageBean("transactions list", "transaction/M1PaySummaryList", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			logger.info("M1 PAY SUMMARY BY ADMIN USERNAME:" + principal.getName());
			PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Invoice")) {
				logger.info("Admin search Invoice No : " + VALUE);

				transactionService.ListofM1PaySummarySearchApi(paginationBean, null, null, "M1pay1", VALUE);

				model.addAttribute("paginationBean", paginationBean);
			}

			if (Type.equals("Ap_Code")) {
				logger.info("Admin search Ap_Code Mid :" + VALUE);

				transactionService.ListofM1PaySummarySearchApi(paginationBean, null, null, "M1pay2", VALUE);

				model.addAttribute("paginationBean", paginationBean);
			}
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/findBnpl" }, method = RequestMethod.GET)
	public String FindResultsBnpl(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
			@RequestParam final String VALUE, @RequestParam final String TXNTYPE, @RequestParam final String Type) {

		logger.info(" VALUE  : " + VALUE);
		logger.info(" TXNTYPE : " + TXNTYPE);
		logger.info(" TYPE : " + Type);

		if (TXNTYPE.equals("BNPL")) {

			logger.info("BNPL SUMMARY BY ADMIN");
			PageBean pageBean = new PageBean("transactions list", "transaction/BnplSummaryList", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			logger.info("BNPL SUMMARY BY ADMIN USERNAME:" + principal.getName());
			PaginationBean<SettlementModel> paginationBean = new PaginationBean<SettlementModel>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Invoice")) {
				logger.info("Admin search Invoice No : " + VALUE);

				transactionService.ListofBnplSummarySearchApi(paginationBean, null, null, "BNPL1", VALUE);

				model.addAttribute("paginationBean", paginationBean);
			}

			if (Type.equals("Ap_Code")) {
				logger.info("Admin search Ap_Code Mid :" + VALUE);

				transactionService.ListofBnplSummarySearchApi(paginationBean, null, null, "BNPL2", VALUE);

				model.addAttribute("paginationBean", paginationBean);
			}
		}

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/findCard" }, method = RequestMethod.GET)
	public String FindResultsCard(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
			@RequestParam final String VALUE, @RequestParam final String TXNTYPE, @RequestParam final String Type) {

		logger.info(" VALUE  : " + VALUE);
		logger.info(" TXNTYPE : " + TXNTYPE);
		logger.info(" TYPE : " + Type);

		if (TXNTYPE.equals("CARD1")) {

			PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMEzywayList",
					Module.TRANSACTION, "transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			logger.info(" UM-EZWAY Transaction Summary:" + principal.getName());
			List<Merchant> merchant1 = merchantService.loadUMMerchant();
			model.addAttribute("merchant1", merchant1);
			PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) {
				logger.info("Admin search Ref No :" + VALUE);

				transactionService.listUMEzywayTransactionSearchApi(paginationBean, null, null, "EZYWAY1", VALUE);
				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("Card_No")) {
				logger.info("Admin search Card No :" + VALUE);

				transactionService.listUMEzywayTransactionSearchApi(paginationBean, null, null, "EZYWAY2", VALUE);
				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("Ap_Code")) {
				logger.info("Admin search Approve No :" + VALUE);

				transactionService.listUMEzywayTransactionSearchApi(paginationBean, null, null, "EZYWAY3", VALUE);
				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("RRN")) {
				logger.info("Admin search RRN No :" + VALUE);

				transactionService.listUMEzywayTransactionSearchApi(paginationBean, null, null, "EZYWAY4", VALUE);
				model.addAttribute("paginationBean", paginationBean);
			}
		} else if (TXNTYPE.equals("UMEZYWIRE")) {

			logger.info("um transaction summary admin");
			PageBean pageBean = new PageBean("transactions list", "transaction/transactionUmEzywireList",
					Module.TRANSACTION, "transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			logger.info("Transaction Summary:" + principal.getName());
			PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) {
				logger.info("Admin search Ref No :" + VALUE);
				transactionService.listAllUmEzywireTransactionSearchAPI(paginationBean, null, null, "UMEZYWIRE1",
						VALUE);
				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("Card_No")) {
				logger.info("Admin search Card No :" + VALUE);

				transactionService.listAllUmEzywireTransactionSearchAPI(paginationBean, null, null, "UMEZYWIRE2",
						VALUE);
				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("Aid_response")) {
				logger.info("Admin search Approve No :" + VALUE);

				transactionService.listAllUmEzywireTransactionSearchAPI(paginationBean, null, null, "UMEZYWIRE3",
						VALUE);
				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("RRN")) {
				logger.info("Admin search RRN No :" + VALUE);

				transactionService.listAllUmEzywireTransactionSearchAPI(paginationBean, null, null, "UMEZYWIRE4",
						VALUE);
				model.addAttribute("paginationBean", paginationBean);
			}
		} else if (TXNTYPE.equals("UMMOTO")) {

			logger.info(" UM-EZYMOTO Transaction Summary admin");
			PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMMotoList",
					Module.TRANSACTION, "transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			logger.info(" UM-Moto Transaction Summary:" + principal.getName());
			PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) {
				logger.info("Admin search Ref No :" + VALUE);
				transactionService.listUMMotoTransactionSearchApi(paginationBean, null, null, "UMMOTO1", VALUE);

				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("Card_No")) {
				logger.info("Admin search Card No :" + VALUE);
				transactionService.listUMMotoTransactionSearchApi(paginationBean, null, null, "UMMOTO2", VALUE);

				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("Aid_response")) {
				logger.info("Admin search Approve No :" + VALUE);
				transactionService.listUMMotoTransactionSearchApi(paginationBean, null, null, "UMMOTO3", VALUE);

				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("RRN")) {
				logger.info("Admin search RRN No :" + VALUE);
				transactionService.listUMMotoTransactionSearchApi(paginationBean, null, null, "UMMOTO4", VALUE);

				model.addAttribute("paginationBean", paginationBean);
			}
		}

		else if (TXNTYPE.equals("UMEZYLINK")) {

			logger.info(" UM-EZYLINK Transaction Summary admin");
			PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMLinkList",
					Module.TRANSACTION, "transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			logger.info(" UM-EZYLINK Transaction Summary:" + principal.getName());
			PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) {
				logger.info("Admin search Ref No :" + VALUE);

				transactionService.listUMLinkTransactionSearchAPI(paginationBean, null, null, "UMEZYLINK1", VALUE);

				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("Card_No")) {
				logger.info("Admin search Card No :" + VALUE);
				transactionService.listUMLinkTransactionSearchAPI(paginationBean, null, null, "UMEZYLINK2", VALUE);

				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("Aid_response")) {
				logger.info("Admin search Approve No :" + VALUE);
				transactionService.listUMLinkTransactionSearchAPI(paginationBean, null, null, "UMEZYLINK3", VALUE);

				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("RRN")) {
				logger.info("Admin search RRN No :" + VALUE);
				transactionService.listUMLinkTransactionSearchAPI(paginationBean, null, null, "UMEZYLINK4", VALUE);

				model.addAttribute("paginationBean", paginationBean);
			}
		} else if (TXNTYPE.equals("UMEZYAUTH")) {
			logger.info(" UM-EZYAUTH Transaction Summary admin");
			PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMEzyauthList",
					Module.TRANSACTION, "transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			logger.info(" UM-EZYAUTH Transaction Summary:" + principal.getName());
			PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) {
				logger.info("Admin search Ref No :" + VALUE);
			} else if (Type.equals("Card_No")) {
				logger.info("Admin search Card No :" + VALUE);
				transactionService.listUMEzyauthTransactionSearchAPI(paginationBean, null, null, "UMEZYAUTH1", VALUE);

				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("Aid_response")) {
				logger.info("Admin search Approve No :" + VALUE);
				transactionService.listUMEzyauthTransactionSearchAPI(paginationBean, null, null, "UMEZYAUTH2", VALUE);
				model.addAttribute("paginationBean", paginationBean);
			} else if (Type.equals("RRN")) {
				logger.info("Admin search RRN No :" + VALUE);
				transactionService.listUMEzyauthTransactionSearchAPI(paginationBean, null, null, "UMEZYAUTH3", VALUE);
				model.addAttribute("paginationBean", paginationBean);
			}
		}

		return TEMPLATE_DEFAULT;
	}
	
	

//	@RequestMapping(value = { "/findpayout" }, method = RequestMethod.GET)
//	public String payoutFilters(final Model model, final java.security.Principal principal,
//			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
//			@RequestParam final String VALUE, @RequestParam final String TXNTYPE, @RequestParam final String Type) {
//
//		logger.info(" VALUE  : " + VALUE);
//		logger.info(" TXNTYPE : " + TXNTYPE);
//		logger.info(" TYPE : " + Type);
//
//		if (TXNTYPE.equals("PAYOUT")) {
//
//			PageBean pageBean = new PageBean("transactions list", "transaction/transactionUMEzywayList",
//					Module.TRANSACTION, "transaction/sideMenuTransaction");
//
//			model.addAttribute("pageBean", pageBean);
//			logger.info(" UM-EZWAY Transaction Summary:" + principal.getName());
//			PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
//			paginationBean.setCurrPage(currPage);
//
//			if (Type.equals("PAYOUT_ID")) {
//				logger.info("Admin search Payout Id :" + VALUE);
//
////				transactionService.listUMEzywayTransactionSearchApi(paginationBean, null, null, "EZYWAY1",VALUE);
//
//				transactionService.listPayoutIdSearchApi(paginationBean, "PAYOUT_ID", VALUE);
//
//				model.addAttribute("paginationBean", paginationBean);
//			} else if (Type.equals("TXN_ID")) {
//				logger.info("Admin search Transaction Id :" + VALUE);
//
//				transactionService.listPayoutIdSearchApi(paginationBean, "TXN_ID", VALUE);
//				model.addAttribute("paginationBean", paginationBean);
//			} else if (Type.equals("ASSIGNEE_NAME")) {
//				logger.info("Admin search Assignee Name :" + VALUE);
//
//				transactionService.listPayoutIdSearchApi(paginationBean, "ASSIGNEE_NAME", VALUE);
//				model.addAttribute("paginationBean", paginationBean);
//			}
//
//		}
//		return TEMPLATE_DEFAULT;
//	}

}
