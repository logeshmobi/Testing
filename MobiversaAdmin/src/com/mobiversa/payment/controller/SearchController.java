package com.mobiversa.payment.controller;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.PayoutHoldTxn;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.MasterSearchDao;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.exception.MobiException;
import com.mobiversa.payment.service.AccountEnquiryService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.PayoutModel;
import com.mobiversa.payment.util.UMEzyway;

@Controller
@RequestMapping(value = SearchController.URL_BASE)
public class SearchController extends BaseController {

	@Autowired
	private MasterSearchDao masterSearchDao;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private AccountEnquiryService accountEnquiryService;

	@Autowired
	private MerchantDao merchantDao;

//	ExecutorService executorService = Executors.newFixedThreadPool(Integer.parseInt(PropertyLoad.getFile().getProperty("THREAD_COUNT_FOR_ACCOUNT_ENQUIRY")));


	public static final String URL_BASE = "/searchNew"; //$NON-NLS-1$

	private static final Logger logger = Logger.getLogger(SearchController.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {

		return "redirect:" + URL_BASE + "/find"; //$NON-NLS-1$ //$NON-NLS-2$
	}



	@RequestMapping(value = { "/find" }, method = RequestMethod.GET)
	public String FindResults(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
			@RequestParam final String VALUE, @RequestParam final String TXNTYPE, @RequestParam final String Type) {

		logger.info("We PASS VALUE from JSP : " + VALUE); //$NON-NLS-1$
		logger.info("We Pass TXNTYPE from JSP : " + TXNTYPE); //$NON-NLS-1$
		logger.info("We Pass TYPE from JSP : " + Type); //$NON-NLS-1$

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName"); //$NON-NLS-1$
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = currentMerchant.getBusinessName();

		
		// FPX SEARCH IMPLEMENTATION

		if (TXNTYPE.equals("FPX1")) { //$NON-NLS-1$

			PageBean pageBean = new PageBean("transactions list", "transaction/merchantportalFPXTransaction", //$NON-NLS-1$ //$NON-NLS-2$
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction"); //$NON-NLS-1$
			model.addAttribute("pageBean", pageBean); //$NON-NLS-1$

			PaginationBean<com.mobiversa.payment.util.UMEzyway> paginationBean = new PaginationBean<com.mobiversa.payment.util.UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) { //$NON-NLS-1$

//				transactionService.merchantFpxtranscation(paginationBean, null, null,
//						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getFpxMid(),
//						currentMerchant.getMid().getUmMotoMid(), VALUE, "FPX1", currentMerchant); 

				transactionService.merchantFpxtranscationSearch(paginationBean, null, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getFpxMid(),
						currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getMid(), "FPX1",
						currentMerchant, VALUE);

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ap_Code")) { //$NON-NLS-1$

//				transactionService.merchantFpxtranscationSearch(paginationBean, null, null,
//						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getFpxMid(),
//						currentMerchant.getMid().getUmMotoMid(), VALUE, "FPX2", currentMerchant); //$NON-NLS-1$

				transactionService.merchantFpxtranscationSearch(paginationBean, null, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getFpxMid(),
						currentMerchant.getMid().getUmMotoMid(), currentMerchant.getMid().getMid(), "FPX2",
						currentMerchant, VALUE);

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}
		}

		// UM_EZYWAY CARD SEARCH STARTS

		else if (TXNTYPE.equals("UM_CARD1")) { //$NON-NLS-1$

			PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzywayList", //$NON-NLS-1$ //$NON-NLS-2$
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction"); //$NON-NLS-1$
			model.addAttribute("pageBean", pageBean); //$NON-NLS-1$

			PaginationBean<com.mobiversa.payment.util.UMEzyway> paginationBean = new PaginationBean<com.mobiversa.payment.util.UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("RRN")) { //$NON-NLS-1$

				transactionService.listUMEzywayTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), "UM_CARD_RRN", currentMerchant); //$NON-NLS-1$

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("CARD_NO")) { //$NON-NLS-1$

				transactionService.listUMEzywayTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), "UM_CARD_CARDNO", currentMerchant); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ap_Code")) { //$NON-NLS-1$

				transactionService.listUMEzywayTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), "UM_CARD_ApCode", currentMerchant); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ref")) { //$NON-NLS-1$

				transactionService.listUMEzywayTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), "UM_CARD_REF", currentMerchant); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}
		}

		// UM CARD FAILED SEARCH FILTER

		else if (TXNTYPE.equals("EZYWAY_FAIL")) {

			PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzywayFailList",
					Module.TRANSACTION, "transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			logger.info("Transaction Summary:" + principal.getName());
			PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) {
				logger.info("search by RRN No :" + VALUE);
//					transactionService.listFPXTransaction(paginationBean, null, null,null,"NULL");
				transactionService.listcardfailedTransaction(paginationBean, null, null, VALUE, "EZYWAY_FAIL1");

				model.addAttribute("paginationBean", paginationBean);
			}

		}

		// UM CARD FAILED SEARCH FILTER END

		// UM-EZYWIRE CARD SEARCH IMPLEMENTATION

		else if (TXNTYPE.equals("UM_EZYWIRE_CARD")) { //$NON-NLS-1$

			PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzywireList1", //$NON-NLS-1$ //$NON-NLS-2$
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction"); //$NON-NLS-1$
			model.addAttribute("pageBean", pageBean); //$NON-NLS-1$

			PaginationBean<com.mobiversa.payment.util.UMEzyway> paginationBean = new PaginationBean<com.mobiversa.payment.util.UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("RRN")) { //$NON-NLS-1$

				transactionService.listUMEzywireTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmMid(), "UM_EZYWIRE_CARD_RRN", currentMerchant);

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("CARD_NO")) { //$NON-NLS-1$

				transactionService.listUMEzywireTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmMid(), "UM_EZYWIRE_CARD_CARDNO", currentMerchant);
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ap_Code")) { //$NON-NLS-1$

				transactionService.listUMEzywireTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmMid(), "UM_EZYWIRE_CARD_AP", currentMerchant);
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ref")) { //$NON-NLS-1$

				transactionService.listUMEzywireTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmMid(), "UM_EZYWIRE_CARD_REF", currentMerchant);
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}
		}

		// UM_EZYLINK CARD SEARCH INTEGRATION

		else if (TXNTYPE.equals("UM_CARD_LINK")) { //$NON-NLS-1$

			PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMLinkList", //$NON-NLS-1$ //$NON-NLS-2$
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction"); //$NON-NLS-1$
			model.addAttribute("pageBean", pageBean); //$NON-NLS-1$

			PaginationBean<com.mobiversa.payment.util.UMEzyway> paginationBean = new PaginationBean<com.mobiversa.payment.util.UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("RRN")) { //$NON-NLS-1$

				transactionService.listUMLinkTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmMotoMid(), "UM_CARD_LINK_RRN", currentMerchant); //$NON-NLS-1$

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("CARD_NO")) { //$NON-NLS-1$

				transactionService.listUMLinkTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmMotoMid(), "UM_CARD_LINK_CARDNO", currentMerchant); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ap_Code")) { //$NON-NLS-1$

				transactionService.listUMLinkTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmMotoMid(), "UM_CARD_LINK_AP", currentMerchant); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ref")) { //$NON-NLS-1$

				transactionService.listUMLinkTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmMotoMid(), "UM_CARD_LINK_REF", currentMerchant); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}
		}

		// UM_EZYMOTO CARD SEARCH INTEGRATION

		else if (TXNTYPE.equals("UM_CARD_MOTO")) { //$NON-NLS-1$

			PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMMotoList", //$NON-NLS-1$ //$NON-NLS-2$
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction"); //$NON-NLS-1$
			model.addAttribute("pageBean", pageBean); //$NON-NLS-1$

			PaginationBean<com.mobiversa.payment.util.UMEzyway> paginationBean = new PaginationBean<com.mobiversa.payment.util.UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("RRN")) { //$NON-NLS-1$

				transactionService.listUMMotoTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmMotoMid(),currentMerchant.getMid().getFiuuMid(), "UM_CARD_MOTO_RRN", currentMerchant); //$NON-NLS-1$

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("CARD_NO")) { //$NON-NLS-1$

				transactionService.listUMMotoTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmMotoMid(),currentMerchant.getMid().getFiuuMid(), "UM_CARD_MOTO_CARDNO", currentMerchant); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ap_Code")) { //$NON-NLS-1$

				transactionService.listUMMotoTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmMotoMid(),currentMerchant.getMid().getFiuuMid(), "UM_CARD_MOTO_AP", currentMerchant); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ref")) { //$NON-NLS-1$

				transactionService.listUMMotoTransaction(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmMotoMid(),currentMerchant.getMid().getFiuuMid(), "UM_CARD_MOTO_REF", currentMerchant); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}
		}

		// UM-EZYAUTH SEARCH IMPLEMENTATION

		else if (TXNTYPE.equals("UM_CARD_AUTH")) { //$NON-NLS-1$

			PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/transactionUMEzyauthList", //$NON-NLS-1$ //$NON-NLS-2$
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction"); //$NON-NLS-1$
			model.addAttribute("pageBean", pageBean); //$NON-NLS-1$

			PaginationBean<com.mobiversa.payment.util.UMEzyway> paginationBean = new PaginationBean<com.mobiversa.payment.util.UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("RRN")) { //$NON-NLS-1$

				transactionService.listUMEzyauthMerchantTransaction(paginationBean, VALUE, null, currentMerchant,
						"UM_CARD_AUTH_RRN"); //$NON-NLS-1$

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("CARD_NO")) { //$NON-NLS-1$

				transactionService.listUMEzyauthMerchantTransaction(paginationBean, VALUE, null, currentMerchant,
						"UM_CARD_AUTH_CARDNO"); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ap_Code")) { //$NON-NLS-1$

				transactionService.listUMEzyauthMerchantTransaction(paginationBean, VALUE, null, currentMerchant,
						"UM_CARD_AUTH_AP"); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ref")) { //$NON-NLS-1$

				transactionService.listUMEzyauthMerchantTransaction(paginationBean, VALUE, null, currentMerchant,
						"UM_CARD_AUTH_REF"); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}
		}
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantName);
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/findWallets" }, method = RequestMethod.GET)
	public String FindWalletResults(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
			@RequestParam final String VALUE, @RequestParam final String TXNTYPE, @RequestParam final String Type) {

		logger.info("We PASS VALUE from JSP : " + VALUE); //$NON-NLS-1$
		logger.info("We Pass TXNTYPE from JSP : " + TXNTYPE); //$NON-NLS-1$
		logger.info("We Pass TYPE from JSP : " + Type); //$NON-NLS-1$

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName"); //$NON-NLS-1$
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantname = currentMerchant.getBusinessName();

		if (TXNTYPE.equals("TNG1")) { //$NON-NLS-1$

			PageBean pageBean = new PageBean("transactions list", "transaction/merchantTNGpay(E-wallet)", //$NON-NLS-1$ //$NON-NLS-2$
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction"); //$NON-NLS-1$
			model.addAttribute("pageBean", pageBean); //$NON-NLS-1$

			PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) { //$NON-NLS-1$

				transactionService.merchantEwallet(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getEzywayMid(),
						currentMerchant.getMid().getUmMid(), currentMerchant.getMid().getUmMotoMid(),
						currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getMid(),
						currentMerchant.getMid().getBnplMid(), currentMerchant.getMid().getBoostMid(),
						currentMerchant.getMid().getTngMid(), currentMerchant.getMid().getShoppyMid(),
						currentMerchant.getMid().getGrabMid(), "TNG_REF", currentMerchant); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ap_Code")) { //$NON-NLS-1$

				transactionService.merchantEwallet(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getEzywayMid(),
						currentMerchant.getMid().getUmMid(), currentMerchant.getMid().getUmMotoMid(),
						currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getMid(),
						currentMerchant.getMid().getBnplMid(), currentMerchant.getMid().getBoostMid(),
						currentMerchant.getMid().getTngMid(), currentMerchant.getMid().getShoppyMid(),
						currentMerchant.getMid().getGrabMid(), "TNG_AP", currentMerchant); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

		}

		else if (TXNTYPE.equals("SHOPPEE1")) { //$NON-NLS-1$

			PageBean pageBean = new PageBean("transactions list", "transaction/merchantSHOPPYpay(E-wallet)", //$NON-NLS-1$ //$NON-NLS-2$
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction"); //$NON-NLS-1$
			model.addAttribute("pageBean", pageBean); //$NON-NLS-1$

			PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) { //$NON-NLS-1$

				transactionService.merchantEwallet(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getEzywayMid(),
						currentMerchant.getMid().getUmMid(), currentMerchant.getMid().getUmMotoMid(),
						currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getMid(),
						currentMerchant.getMid().getBnplMid(), currentMerchant.getMid().getBoostMid(),
						currentMerchant.getMid().getTngMid(), currentMerchant.getMid().getShoppyMid(),
						currentMerchant.getMid().getGrabMid(), "SHOPPEE_REF", currentMerchant); //$NON-NLS-1$

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ap_Code")) { //$NON-NLS-1$

				transactionService.merchantEwallet(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getEzywayMid(),
						currentMerchant.getMid().getUmMid(), currentMerchant.getMid().getUmMotoMid(),
						currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getMid(),
						currentMerchant.getMid().getBnplMid(), currentMerchant.getMid().getBoostMid(),
						currentMerchant.getMid().getTngMid(), currentMerchant.getMid().getShoppyMid(),
						currentMerchant.getMid().getGrabMid(), "SHOPPEE_AP", currentMerchant); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

		}
		// GRABPAY SEARCH IMPLEMENTATION

		else if (TXNTYPE.equals("GRAB1")) { //$NON-NLS-1$

			PageBean pageBean = new PageBean("transactions list", "transaction/merchantGrab(E-wallet)", //$NON-NLS-1$ //$NON-NLS-2$
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction"); //$NON-NLS-1$
			model.addAttribute("pageBean", pageBean); //$NON-NLS-1$

			PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) { //$NON-NLS-1$

				transactionService.merchantEwallet(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getEzywayMid(),
						currentMerchant.getMid().getUmMid(), currentMerchant.getMid().getUmMotoMid(),
						currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getMid(),
						currentMerchant.getMid().getBnplMid(), currentMerchant.getMid().getBoostMid(),
						currentMerchant.getMid().getTngMid(), currentMerchant.getMid().getShoppyMid(),
						currentMerchant.getMid().getGrabMid(), "GRABPAY_REF", currentMerchant); //$NON-NLS-1$

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ap_Code")) { //$NON-NLS-1$

				transactionService.merchantEwallet(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getEzywayMid(),
						currentMerchant.getMid().getUmMid(), currentMerchant.getMid().getUmMotoMid(),
						currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getMid(),
						currentMerchant.getMid().getBnplMid(), currentMerchant.getMid().getBoostMid(),
						currentMerchant.getMid().getTngMid(), currentMerchant.getMid().getShoppyMid(),
						currentMerchant.getMid().getGrabMid(), "GRABPAY_AP", currentMerchant); //$NON-NLS-1$

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("RRN")) { //$NON-NLS-1$

				transactionService.merchantEwallet(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getEzywayMid(),
						currentMerchant.getMid().getUmMid(), currentMerchant.getMid().getUmMotoMid(),
						currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getMid(),
						currentMerchant.getMid().getBnplMid(), currentMerchant.getMid().getBoostMid(),
						currentMerchant.getMid().getTngMid(), currentMerchant.getMid().getShoppyMid(),
						currentMerchant.getMid().getGrabMid(), "GRABPAY_RRN", currentMerchant); //$NON-NLS-1$

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

		}

		// BNPL SEARCH IMPLEMENTATION

		else if (TXNTYPE.equals("BNPL1")) { //$NON-NLS-1$

			PageBean pageBean = new PageBean("transactions list", "transaction/merchantBNPL(E-wallet)", //$NON-NLS-1$ //$NON-NLS-2$
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction"); //$NON-NLS-1$
			model.addAttribute("pageBean", pageBean); //$NON-NLS-1$

			PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) { //$NON-NLS-1$

				transactionService.merchantEwallet(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getEzywayMid(),
						currentMerchant.getMid().getUmMid(), currentMerchant.getMid().getUmMotoMid(),
						currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getMid(),
						currentMerchant.getMid().getBnplMid(), currentMerchant.getMid().getBoostMid(),
						currentMerchant.getMid().getTngMid(), currentMerchant.getMid().getShoppyMid(),
						currentMerchant.getMid().getGrabMid(), "BNPL_REF", currentMerchant);//$NON-NLS-1$

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ap_Code")) { //$NON-NLS-1$

				transactionService.merchantEwallet(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getEzywayMid(),
						currentMerchant.getMid().getUmMid(), currentMerchant.getMid().getUmMotoMid(),
						currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getMid(),
						currentMerchant.getMid().getBnplMid(), currentMerchant.getMid().getBoostMid(),
						currentMerchant.getMid().getTngMid(), currentMerchant.getMid().getShoppyMid(),
						currentMerchant.getMid().getGrabMid(), "BNPL_AP", currentMerchant);//$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

		}

		//

		// BOOST SEARCH INTEGRATION STARTS

		else if (TXNTYPE.equals("BOOST1")) { //$NON-NLS-1$

			PageBean pageBean = new PageBean("transactions list", "transaction/merchantBoost(E-wallet)", //$NON-NLS-1$ //$NON-NLS-2$
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction"); //$NON-NLS-1$
			model.addAttribute("pageBean", pageBean); //$NON-NLS-1$

			PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
			paginationBean.setCurrPage(currPage);

			if (Type.equals("Ref")) { //$NON-NLS-1$

				transactionService.merchantEwallet(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getEzywayMid(),
						currentMerchant.getMid().getUmMid(), currentMerchant.getMid().getUmMotoMid(),
						currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getMid(),
						currentMerchant.getMid().getBnplMid(), currentMerchant.getMid().getBoostMid(),
						currentMerchant.getMid().getTngMid(), currentMerchant.getMid().getShoppyMid(),
						currentMerchant.getMid().getGrabMid(), "BOOST_REF", currentMerchant); //$NON-NLS-1$

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("Ap_Code")) { //$NON-NLS-1$

				transactionService.merchantEwallet(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getEzywayMid(),
						currentMerchant.getMid().getUmMid(), currentMerchant.getMid().getUmMotoMid(),
						currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getMid(),
						currentMerchant.getMid().getBnplMid(), currentMerchant.getMid().getBoostMid(),
						currentMerchant.getMid().getTngMid(), currentMerchant.getMid().getShoppyMid(),
						currentMerchant.getMid().getGrabMid(), "BOOST_AP", currentMerchant); //$NON-NLS-1$
				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

			if (Type.equals("RRN")) { //$NON-NLS-1$

				transactionService.merchantEwallet(paginationBean, VALUE, null,
						currentMerchant.getMid().getUmEzywayMid(), currentMerchant.getMid().getEzywayMid(),
						currentMerchant.getMid().getUmMid(), currentMerchant.getMid().getUmMotoMid(),
						currentMerchant.getMid().getUmEzyrecMid(), currentMerchant.getMid().getMid(),
						currentMerchant.getMid().getBnplMid(), currentMerchant.getMid().getBoostMid(),
						currentMerchant.getMid().getTngMid(), currentMerchant.getMid().getShoppyMid(),
						currentMerchant.getMid().getGrabMid(), "BOOST_RRN", currentMerchant); //$NON-NLS-1$

				model.addAttribute("paginationBean", paginationBean); //$NON-NLS-1$
			}

		}
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("merchantName",merchantname);
		return TEMPLATE_MERCHANT;
	}

//	@RequestMapping(value = { "/searchPayoutId" }, method = RequestMethod.GET)
//	public String searchPayoutId(final Model model, final java.security.Principal principal,
//			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
//			@RequestParam final String VALUE) {
//
//		logger.info("We PASS VALUE from JSP : " + VALUE); //$NON-NLS-1$
//		
//	
//		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutList",
//				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//
//		model.addAttribute("pageBean", pageBean);
//		
//		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
//		paginationBean.setCurrPage(currPage);
//		
////		transactionService.listPayoutTransactionByMerchant(paginationBean, date, date1, merchantName);
//		transactionService.searchPayoutTransactionList(paginationBean,VALUE);
//
//		model.addAttribute("paginationBean", paginationBean); 
//		model.addAttribute("loginname", principal.getName());
//		
//		return TEMPLATE_MERCHANT;
//	}
//	

	@RequestMapping(value = { "/searchPayoutId" }, method = RequestMethod.GET)
	public String searchPayoutId(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
			@RequestParam final String VALUE, @RequestParam final String transaction_type) {
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant m = merchantService.loadMerchant(myName);
//		String merchant = m.getBusinessShortName();
		String merchant = String.valueOf(m.getId());

		logger.info("value is " + VALUE);
		logger.info("type is " + transaction_type);
		logger.info("We PASS VALUE from JSP : " + VALUE); //$NON-NLS-1$
		PageBean pageBean = new PageBean("payout transactions list", "merchantweb/transaction/PayoutList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
		paginationBean.setCurrPage(currPage);
		model.addAttribute("xpayId",m.getId());
//	     transactionService.listPayoutTransactionByMerchant(paginationBean, date, date1, merchantName);
		transactionService.searchPayoutTransactionList(paginationBean, VALUE, transaction_type, merchant);
		paginationBean.setTXNtype(transaction_type);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchPayoutAndTxnIdPayoutLogin" }, method = RequestMethod.GET)

	public String searchPayoutAndTxnIdPayoutLogin(final Model model, final java.security.Principal principal,

			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,

			@RequestParam final String VALUE, @RequestParam final String transaction_type) {

		HttpSession session = request.getSession();

		String myName = (String) session.getAttribute("userName");

		logger.info("value is " + VALUE);

		logger.info("type is " + transaction_type);

		logger.info("We PASS VALUE from JSP : " + VALUE); //$NON-NLS-1$

		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/PayoutList", Module.TRANSACTION,

				"transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();

		paginationBean.setCurrPage(currPage);

		transactionService.searchPayoutLoginTransactionList(paginationBean, VALUE, transaction_type);

		paginationBean.setTXNtype(transaction_type);

		model.addAttribute("paginationBean", paginationBean);

		model.addAttribute("loginname", principal.getName());

		return TEMPLATE_PAYOUTUSER;

	}

	// karuppusamy master search
	@RequestMapping(value = { "/getMasterSearch" }, method = RequestMethod.GET)
	public String getMasterSearch(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {
		HttpSession session = request.getSession();
		PageBean pageBean = new PageBean("master search", "merchantweb/transaction/mastersearch", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		String myName1 = (String) session.getAttribute("userName");
		logger.info("User logged: " + myName1);
		Merchant merchant1 = merchantService.loadMerchant(myName1);
		MobileUser mobileUser = merchantDao.loadMobileUserById(merchant1.getId());

		if (mobileUser.getEzywayTid() != null || mobileUser.getMotoTid() != null) {
			model.addAttribute("isCardEnabled", "YES");
		} else {
			model.addAttribute("isCardEnabled", "NO");
		}
		logger.info("merchant name is " + merchant1.getBusinessName());
		String merchantName = merchant1.getBusinessName();
		model.addAttribute("merchantName", merchantName);
		model.addAttribute("pageBean", pageBean);
		PaginationBean<Object> paginationBean = new PaginationBean<Object>();
		paginationBean.setChooseType("");
		paginationBean.setEwalletPaymentType("");
		List<String> searchValue = new ArrayList<>();
		searchValue.add(" ");
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("isPayoutEnabled", merchant1.getEnblPayout());
		isCardEnabled(merchant1.getId(), model);
		model.addAttribute("loginname",principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// master search by fpx
	@SuppressWarnings({ "nls", "unqualified-field-access" })
	@RequestMapping(value = { "/fpxMasterSearch" }, method = RequestMethod.POST)
	public String fpxMasterSearch(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request,
			@RequestParam("payment_method") final String paymentMethod,
			@RequestParam("search_type_fpx") final String chooseType,
			@RequestParam("dataArray") final List<String> searchValue) {
		// Master search by fpx with (REFERENCENO and APPROVALCODE )
		logger.info("Master search by fpx with searchValue " + searchValue + "  paymentMethod " + paymentMethod
				+ " chooseType " + chooseType);
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("Username " + myName);
		Merchant merchantObj = merchantService.loadMerchant(myName);
		String merchant = merchantObj.getBusinessName();
		ArrayList<Object> filterList = null;
		PaginationBean<Object> paginationBean = new PaginationBean<Object>();
		paginationBean.setCurrPage(currPage);
		filterList = masterSearchDao.masterSearchByFPX(paginationBean, merchantObj, merchant,
				chooseType.toUpperCase().trim(), searchValue);
		PageBean pageBean = new PageBean("master search", "merchantweb/transaction/mastersearch", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		paginationBean.setItemList(filterList.isEmpty() ? new ArrayList<>() : filterList);
		String txnType = paginationBean.setTXNtype(paymentMethod);
		paginationBean.setChooseType(chooseType);
		String myName1 = (String) session.getAttribute("userName");
		Merchant merchant1 = merchantService.loadMerchant(myName1);
		String merchantName = merchant1.getBusinessName();
		model.addAttribute("merchantName", merchantName);
		paginationBean.setTXNtype(paymentMethod);
		paginationBean.setChooseType(chooseType);
		model.addAttribute("txnType", txnType);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("isPayoutEnabled", merchant1.getEnblPayout());
		isCardEnabled(merchant1.getId(), model);
		model.addAttribute("loginname",principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// master search for payout
	@SuppressWarnings({ "nls", "unqualified-field-access" })
	@RequestMapping(value = { "/payoutMasterSearch" }, method = RequestMethod.POST)
	public String payoutMasterSearch(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currentPage, HttpServletRequest request,
			@RequestParam("payment_method") final String paymentMethod,
			@RequestParam("dataArray") final List<String> searchValue, @RequestParam String search_type_payout) {

		logger.info("MasterSearch by payout with payout_id or transaction_id ");
		logger.info("Master search by payout with searchValue " + searchValue + " paymentMethod " + paymentMethod
				+ " search type :" + search_type_payout);
		HttpSession session = request.getSession();
		ArrayList<Object> filterList = null;
		PaginationBean<Object> paginationBean = new PaginationBean<Object>();
		paginationBean.setCurrPage(currentPage);
		String myName1 = (String) session.getAttribute("userName");
		logger.info("User logged: " + myName1);
		Merchant merchant1 = merchantService.loadMerchant(myName1);
		String merchantName = merchant1.getBusinessName();
		if ("Payout".equals(paymentMethod)) {
			filterList = masterSearchDao.masterSearchByPayout(paginationBean, String.valueOf(merchant1.getId()),
					searchValue, currentPage, search_type_payout);
		}
		PageBean pageBean = new PageBean("master search", "merchantweb/transaction/mastersearch", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		paginationBean.setItemList(filterList.isEmpty() ? new ArrayList<>() : filterList);
		String txnType = paginationBean.setTXNtype(paymentMethod);
		paginationBean.setTXNtype(paymentMethod);
		model.addAttribute("merchantName", merchantName);
		model.addAttribute("txnType", txnType);
		paginationBean.setChooseType(search_type_payout);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("searchValue", searchValue);
		model.addAttribute("isPayoutEnabled", merchant1.getEnblPayout());
		isCardEnabled(merchant1.getId(), model);
		model.addAttribute("loginname",principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// master search for ewallets
	@SuppressWarnings({ "nls", "unqualified-field-access" })
	@RequestMapping(value = { "/ewalletMasterSearch" }, method = RequestMethod.POST)
	public String ewalletInvoiceIdOrTngTxnIDMasterSearch(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currentPage, HttpServletRequest request,
			@RequestParam("payment_method") final String paymentMethod,
			@RequestParam("dataArray") final List<String> searchValue, @RequestParam String payment_type,
			@RequestParam("search_type_ewallet_boost_grab") String search_type,
			@RequestParam("search_type_ewallet_tng_spp") String search_type_tng_spp,
			@RequestParam("search_type_ewallet_qrtxns") String search_type_qrtxns) {

		// Master search by Ewallet (ShopeePay,Touch N Go,Boost and GrabPay)
		logger.info("Master search by ewallet : payment method " + paymentMethod + " searchValue " + searchValue
				+ " search type : " + search_type + " " + search_type_tng_spp + " with payment type: " + payment_type);
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("User logged: " + myName);
		Merchant merchant = merchantService.loadMerchant(myName);
		ArrayList<Object> filterList = null;
		PaginationBean<Object> paginationBean = new PaginationBean<Object>();
		paginationBean.setCurrPage(currentPage);

		filterList = (payment_type.equalsIgnoreCase("Shopeepay"))
				? masterSearchDao.searchSppTransactionByInvoiceIdOrTngTxnID(paginationBean, merchant, searchValue,
						payment_type, search_type_tng_spp)
				: (payment_type.equalsIgnoreCase("Touch'N_Go"))
						? masterSearchDao.searchTngTransactionByInvoiceIdOrTngTxnID(paginationBean, merchant,
								searchValue, payment_type, search_type_tng_spp)
						: (payment_type.equalsIgnoreCase("Grab"))
								? masterSearchDao.searchGrabTransactionByReferenceNoOrRrnOrApprovalCode(paginationBean,
										merchant, searchValue, payment_type, search_type)
								: (payment_type.equalsIgnoreCase("Boost"))
										? masterSearchDao.searchBoostTransactionByReferenceNoOrRrnOrApprovalCode(
												paginationBean, merchant, searchValue, payment_type, search_type)
								:  (payment_type.equalsIgnoreCase("DuitNow"))
								? masterSearchDao.searchQrTransactionByReferenceNoOrApprovalCode(
								paginationBean, merchant, searchValue, payment_type,search_type_qrtxns)
								: new ArrayList<>();
		PageBean pageBean = new PageBean("master search", "merchantweb/transaction/mastersearch", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		paginationBean.setItemList(filterList.isEmpty() ? new ArrayList<>() : filterList);
		paginationBean.setTXNtype(paymentMethod);
		if (payment_type.equalsIgnoreCase("Shopeepay") || payment_type.equalsIgnoreCase("Touch'N_Go")) {
			paginationBean.setChooseType(search_type_tng_spp);
		}else if(payment_type.equalsIgnoreCase("duitnow")){
			paginationBean.setChooseType(search_type_qrtxns);
		}else {
			paginationBean.setChooseType(search_type);
		}
		paginationBean.setEwalletPaymentType(payment_type);
		model.addAttribute("txnType", paymentMethod);
		model.addAttribute("payment_type", payment_type);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("ewalletPaymentMethod", "Touch'N_Go".equalsIgnoreCase(payment_type) ? "Touch N Go" : payment_type);

		model.addAttribute("searchValue", searchValue);
		model.addAttribute("isPayoutEnabled", merchant.getEnblPayout());
		isCardEnabled(merchant.getId(), model);
		model.addAttribute("loginname",principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// master search by card
	@RequestMapping(value = { "/cardMasterSearch" }, method = RequestMethod.POST)
	public String cardMasterSearch(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currentPage, HttpServletRequest request,
			@RequestParam("payment_method") final String paymentMethod,
			@RequestParam("dataArray") final List<String> searchValue,
			@RequestParam("search_type_card") String search_type) {

		// Master search by card (ReferenceNo, ApprovalCode, card number and RRn)\
		logger.info("MasterSearch  card selected :    paymentMethod is  " + paymentMethod + " searchValue "
				+ searchValue + " search_type " + search_type);
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("User logged: " + myName);
		Merchant merchant = merchantService.loadMerchant(myName);
		ArrayList<Object> filterList = null;
		PaginationBean<Object> paginationBean = new PaginationBean<Object>();
		paginationBean.setCurrPage(currentPage);
		model.addAttribute("searchValue", searchValue);
		filterList = masterSearchDao.searchCardByReferenceNoOrApprovalCodeOrRrnOrCardNumber(paginationBean, merchant,
				searchValue, search_type);
		PageBean pageBean = new PageBean("master search", "merchantweb/transaction/mastersearch", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		paginationBean.setItemList(filterList.isEmpty() ? new ArrayList<>() : filterList);
		paginationBean.setTXNtype(paymentMethod);
		paginationBean.setChooseType(search_type);
		model.addAttribute("txnType", paymentMethod);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		model.addAttribute("isPayoutEnabled", merchant.getEnblPayout());
		isCardEnabled(merchant.getId(), model);
		model.addAttribute("loginname",principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// controller for master search pagination
	@RequestMapping(value = { "/PaginationMasterSearch" }, method = RequestMethod.GET)
	public String PaginationMasterSearch(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currentPage, HttpServletRequest request,
			@RequestParam("payment_method") final String paymentMethod,
			// payment method - fpx , ewallet , card
			@RequestParam("dataArray") final List<String> searchValue, @RequestParam String chooseType,
			// rrn - ref - appr
			@RequestParam String payment_type
	// for ewallet - grab - ssp
	) {

		// controller for handling pagination for all (Payout,FPX,card and Ewallets)
		logger.info("Master search pagination controller searchValue " + searchValue + " paymentMethod " + paymentMethod
				+ " chooseType " + chooseType + " payment_type " + payment_type);
		HttpSession session = request.getSession();
		String myName1 = (String) session.getAttribute("userName");
		logger.info("User logged: " + myName1);
		Merchant merchant1 = merchantService.loadMerchant(myName1);
		logger.info("merchant name is " + merchant1.getBusinessName());
		ArrayList<Object> filterList = null;
		PaginationBean<Object> paginationBean = new PaginationBean<Object>();
		paginationBean.setCurrPage(currentPage);
		PageBean pageBean = new PageBean("master search", "merchantweb/transaction/mastersearch", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		String merchantName = merchant1.getBusinessName();
		if (paymentMethod.equals("Payout")) {
			filterList = masterSearchDao.masterSearchByPayout(paginationBean, String.valueOf(merchant1.getId()), searchValue, currentPage,
					chooseType);
		} else if (paymentMethod.equals("Card")) {
			filterList = masterSearchDao.searchCardByReferenceNoOrApprovalCodeOrRrnOrCardNumber(paginationBean,
					merchant1, searchValue, chooseType);
		} else if (paymentMethod.equalsIgnoreCase("FPX")) {
			filterList = masterSearchDao.masterSearchByFPX(paginationBean, merchant1, merchant1.getBusinessName(),
					chooseType, searchValue);
		} else if (paymentMethod.equalsIgnoreCase("Ewallet")) {
			switch (payment_type.toUpperCase().trim()) {
			case "SHOPEEPAY":
				filterList = masterSearchDao.searchSppTransactionByInvoiceIdOrTngTxnID(paginationBean, merchant1,
						searchValue, payment_type, chooseType);
				break;
			case "TOUCH'N_GO":
				filterList = masterSearchDao.searchTngTransactionByInvoiceIdOrTngTxnID(paginationBean, merchant1,
						searchValue, payment_type, chooseType);
				break;
			case "GRAB":
				filterList = masterSearchDao.searchGrabTransactionByReferenceNoOrRrnOrApprovalCode(paginationBean,
						merchant1, searchValue, payment_type, chooseType);
				break;
			case "BOOST":
				filterList = masterSearchDao.searchBoostTransactionByReferenceNoOrRrnOrApprovalCode(paginationBean,
						merchant1, searchValue, payment_type, chooseType);
				break;
			case "DUITNOW":
				filterList = masterSearchDao.searchQrTransactionByReferenceNoOrApprovalCode(paginationBean,
						merchant1, searchValue, payment_type, chooseType);
			break;
			default:
				logger.error("Invalid filter type in master search pagination");
				filterList = new ArrayList<>();
				break;
			}
		} else if (paymentMethod.equalsIgnoreCase("Card")) {
			masterSearchDao.searchCardByReferenceNoOrApprovalCodeOrRrnOrCardNumber(paginationBean, merchant1,
					searchValue, chooseType);
		} else {
			logger.info("invalid choice");
			filterList = new ArrayList<>();
		}
		paginationBean.setItemList(filterList.isEmpty() ? new ArrayList<>() : filterList);
		paginationBean.setChooseType(chooseType);
		//model.addAttribute("ewalletPaymentMethod", payment_type);
		
		model.addAttribute("ewalletPaymentMethod", 
			    "Touch'N_Go".equalsIgnoreCase(payment_type) ? "Touch N Go" : payment_type);

		
		model.addAttribute("txnType", paginationBean.setTXNtype(paymentMethod));
		model.addAttribute("paginationBean", paginationBean);
		paginationBean.setEwalletPaymentType(payment_type);
		paginationBean.setTXNtype(paymentMethod);
		paginationBean.setChooseType(chooseType);
		model.addAttribute("searchValue", searchValue);

		// dynamically showing payout and card:
		model.addAttribute("isPayoutEnabled", merchant1.getEnblPayout());
		isCardEnabled(merchant1.getId(), model);
		model.addAttribute("loginname",principal.getName());
		return TEMPLATE_MERCHANT;
	}

	private MobileUser isCardEnabled(Long id, Model model) {
		logger.info("id is " + id);
		MobileUser mobileUser = merchantDao.loadMobileUserById(id);
		if (mobileUser.getEzywayTid() != null || mobileUser.getMotoTid() != null) {
			model.addAttribute("isCardEnabled", "YES");
		} else {
			model.addAttribute("isCardEnabled", "NO");
		}
		return mobileUser;
	}

	@RequestMapping(value = "/accountEnquiry" , method = RequestMethod.GET)
	public String accountEnquiry(HttpServletRequest request , final Model model,
								 @RequestParam(required = false, defaultValue = "1") final int currentPage,
								 final java.security.Principal principal
	) throws MobiException {
		logger.info("Account Enquiry Response-->"+request.getRequestURI());
		logger.info(request.getUserPrincipal().getName());
		logger.info(request.getUserPrincipal().getName());
		logger.info("MERCHANT NAME : "+principal.getName());
		logger.info(principal.toString());

		accountEnquiryService.listApprovalTransactions(model,currentPage,principal.getName(),null,false,"default");
		return TEMPLATE_MERCHANT;
	}


	@RequestMapping(value="/approvePayout" ,method=RequestMethod.GET)
	public String approvePayout(HttpServletRequest request,final Model model,
								@RequestParam final String invoiceID,
								@RequestParam(required = false ,defaultValue = "1")final int currentPage,
								final java.security.Principal principal) throws Exception {
		String decodedInvoiceID = new String(Base64.getDecoder().decode(invoiceID), StandardCharsets.UTF_8);
		boolean rareScenario = false;
		logger.info("Decode Invoice : "+decodedInvoiceID);
		logger.info("Encoded  Invoice : "+invoiceID);
		try {
			PayoutHoldTxn payoutHoldTxn = accountEnquiryService.statusUpdate(decodedInvoiceID);
			ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();
			CompletableFuture.runAsync(() -> {
				Thread.currentThread().setContextClassLoader(currentClassLoader);
				try {
					accountEnquiryService.contactExternalApi(payoutHoldTxn,"Approved",null);
					accountEnquiryService.approveOrRejectEmail(payoutHoldTxn, null, principal.getName());

				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}catch (MobiException e)
		{
			rareScenario = true;
			e.printStackTrace();
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		accountEnquiryService.listApprovalTransactions(model, currentPage, principal.getName(), decodedInvoiceID,rareScenario,"approve");
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value="/rejectPayout" ,method=RequestMethod.GET)
	public String rejectPayout(HttpServletRequest request,final Model model,
							   @RequestParam final String invoiceID,
							   @RequestParam final String reason,
							   @RequestParam(required = false ,defaultValue = "1")final int currentPage,
							   final java.security.Principal principal) throws Exception {
		String decodedInvoiceID = new String(Base64.getDecoder().decode(invoiceID), StandardCharsets.UTF_8);
		boolean rareScenario = false;
		logger.info("Decode Invoice : "+decodedInvoiceID);
		try {

			PayoutHoldTxn payoutHoldTxn = accountEnquiryService.rejectPayout(decodedInvoiceID, reason,model);
			ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

			CompletableFuture.runAsync(() -> {
				try {
					Thread.currentThread().setContextClassLoader(currentClassLoader);
					 accountEnquiryService.contactExternalApi(payoutHoldTxn, "Rejected", reason);
					accountEnquiryService.approveOrRejectEmail(payoutHoldTxn, reason, principal.getName());
				} catch (Exception e) {
					e.printStackTrace();
					logger.info("Exception occured while sending rejection email for the invoice Id : " + decodedInvoiceID);
				}
			});
		}catch (MobiException e){
			rareScenario = true;
			e.printStackTrace();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		accountEnquiryService.listApprovalTransactions(model,currentPage,principal.getName(),decodedInvoiceID,rareScenario,"reject");
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = "/searchOnHoldPayout" , method = RequestMethod.GET)
	public String searchOnHoldPayout(HttpServletRequest request , final Model model,
									 @RequestParam final String idType,
									 @RequestParam final String id,
									 final java.security.Principal principal
	) throws MobiException {

//
		accountEnquiryService.findByID(idType,id,principal.getName(),model);

		return TEMPLATE_MERCHANT;
	}




}