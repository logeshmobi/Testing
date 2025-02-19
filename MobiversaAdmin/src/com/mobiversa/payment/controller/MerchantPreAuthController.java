package com.mobiversa.payment.controller;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mobiversa.common.bo.CountryCurPhone;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.PreAuthorization;
import com.mobiversa.common.bo.Receipt;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.bo.TransactionRequest;
import com.mobiversa.common.bo.TransactionResponse;
import com.mobiversa.common.bo.UMEcomTxnResponse;
import com.mobiversa.payment.connect.MotoPaymentCommunication;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.Country;
import com.mobiversa.payment.dto.DataTransferObject;
import com.mobiversa.payment.dto.PreAuthTxnDet;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MotoWebService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.CardType;
import com.mobiversa.payment.util.PreauthModel;
import com.mobiversa.payment.util.ResponseDetails;

@Controller
@RequestMapping(value = MerchantPreAuthController.URL_BASE)
public class MerchantPreAuthController extends BaseController {

	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;
	private MotoWebService motoWebService;

	public static final String URL_BASE = "/merchantpreauth";
	private static final Logger logger = Logger.getLogger(MerchantPreAuthController.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		// logger.info("Test1");
		return "redirect:" + URL_BASE + "/PreAuth";
	}

	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayTransactionSummary(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @PathVariable final int currPage) {
		logger.info("PreAuth transaction");
		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/transactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");

		// Merchant currentMerchant = merchantService.loadMerchant(principal.getName());

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		model.addAttribute("pageBean", pageBean);

		PaginationBean<PreAuthorization> paginationBean = new PaginationBean<PreAuthorization>();

		paginationBean.setCurrPage(currPage);
		transactionService.getPreAuthTxn(paginationBean, currentMerchant);
		logger.info("currently logged in as" + paginationBean.getItemList());

		for (PreAuthorization forSettlement : paginationBean.getItemList()) {

			if (forSettlement.getAmount() != null) {
				double amount = 0;
				amount = Double.parseDouble(forSettlement.getAmount()) / 100;
				forSettlement.setAmount(amount + "0");
			}
			if (forSettlement.getTxnType() == null) {
				/*
				 * if () { forSettlement.setStatus("SETTLED"); }
				 */
				if (forSettlement.getStatus().equals("P") || forSettlement.getStatus().equals("A")
						|| forSettlement.getStatus().equals("S")) {
					forSettlement.setStatus("PREAUTH SALE");
				}

				if (forSettlement.getStatus().equals("C")) {
					forSettlement.setStatus("PREAUTH CANCEL");
				}
				if (forSettlement.getStatus().equals("D") || forSettlement.getStatus().equals("E")) {
					forSettlement.setStatus("PRE-AUTHORIZATION");
				}

				if (forSettlement.getStatus().equals("R")) {
					forSettlement.setStatus("REVERSAL");
				}
			} else {

				/*
				 * if (forSettlement.getStatus().equals("S")) {
				 * forSettlement.setStatus("SETTLED"); }
				 */
				if (forSettlement.getStatus().equals("P") || forSettlement.getStatus().equals("A")
						|| forSettlement.getStatus().equals("S")) {
					forSettlement.setStatus("EZYAUTH SALE");
				}

				if (forSettlement.getStatus().equals("C")) {
					forSettlement.setStatus("EZYAUTH CANCEL");
				}
				if (forSettlement.getStatus().equals("D") || forSettlement.getStatus().equals("E")) {
					forSettlement.setStatus("EZYAUTH");
				}

				if (forSettlement.getStatus().equals("R")) {
					forSettlement.setStatus("REVERSAL");
				}

			}
			if (forSettlement.getDate() != null && forSettlement.getTime() != null) {
				try {

					String rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(forSettlement.getTimeStamp()));

					String rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
					forSettlement.setDate(rd);
					forSettlement.setTime(rt);
				} catch (ParseException e) {
				}

			}

			logger.info("loadTxnDetailsByID: " + forSettlement.getTrxId());
			TransactionRequest txReq = transactionService.loadTxnDetailsByID(forSettlement.getTrxId());
			forSettlement.setNumOfSale(txReq.getMaskedPan());// maskedpan
			logger.info("NumOfSale: " + forSettlement.getNumOfSale());
		}

		model.addAttribute("paginationBean", paginationBean);
		// TODO to get tid and device id

		logger.info("mid: " + currentMerchant.getMid().getMid());
		List<TerminalDetails> terminalDetails = transactionService.getTerminalDetails(currentMerchant.getMid().getMid(),
				currentMerchant.getMid().getMotoMid(), currentMerchant.getMid().getEzyrecMid());
		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			tidSet.add(mtid.toString());
			// tidSet.add(t.getDeviceId());
		}
		ArrayList<String> dIdSet = new ArrayList<String>();
		for (TerminalDetails t : terminalDetails) {
			// String did = t.getDeviceId();
			String did = t.getDeviceId().toString();
			dIdSet.add(did.toString());

		}
		model.addAttribute("devIdList", dIdSet);
		model.addAttribute("tidList", tidSet);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String displayTransactionSearchByTid(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam(required = false) final String tid,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) String status,
			// @RequestParam (required = false )String txnType,

			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		String fromDate1 = null;
		String toDate1 = null;
		PageBean pageBean = null;
		/*
		 * logger.info("fromDate & toDate & Tid :" + fromDate + ": & :" + toDate +
		 * ": & :" + tid + ": & :" + txnType);
		 */
		logger.info("fromDate & toDate & Tid :" + fromDate + ": & :" + toDate + ": & :" + tid);
		if (fromDate != null && toDate != null) {
			try {
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
		logger.info("fromDate & toDate & Tid :" + fromDate1 + ": & :" + toDate1 + ": & :" + tid);

		PaginationBean<PreAuthorization> paginationBean = new PaginationBean<PreAuthorization>();
		paginationBean.setCurrPage(currPage);

		Merchant merchant = merchantService.loadMerchant(myName);

		transactionService.searchPreAuth(fromDate1, toDate1, tid, status, paginationBean, merchant);

		model.addAttribute("fromDate", fromDate1);

		model.addAttribute("toDate", toDate1);

		model.addAttribute("tid", tid);

		model.addAttribute("status", status);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		logger.info("test data " + paginationBean.getItemList());
		for (PreAuthorization forSettlement : paginationBean.getItemList()) {
			if (forSettlement.getAmount() != null) {
				double amount = 0;
				amount = Double.parseDouble(forSettlement.getAmount()) / 100;
				forSettlement.setAmount(amount + "0");
			}
			if (forSettlement.getTxnType() == null) {
				/*
				 * if (forSettlement.getStatus().equals("S")) {
				 * forSettlement.setStatus("SETTLED"); }
				 */
				if (forSettlement.getStatus().equals("P") || forSettlement.getStatus().equals("A")
						|| forSettlement.getStatus().equals("S")) {
					forSettlement.setStatus("PREAUTH SALE");
				}

				if (forSettlement.getStatus().equals("C")) {
					forSettlement.setStatus("PREAUTH CANCEL");
				}
				if (forSettlement.getStatus().equals("D") || forSettlement.getStatus().equals("E")) {
					forSettlement.setStatus("PRE-AUTHORIZATION");
				}

				if (forSettlement.getStatus().equals("R")) {
					forSettlement.setStatus("REVERSAL");
				}
			} else {

				/*
				 * if (forSettlement.getStatus().equals("S")) {
				 * forSettlement.setStatus("SETTLED"); }
				 */
				if (forSettlement.getStatus().equals("P") || forSettlement.getStatus().equals("A")
						|| forSettlement.getStatus().equals("S")) {
					forSettlement.setStatus("EZYAUTH SALE");
				}

				if (forSettlement.getStatus().equals("C")) {
					forSettlement.setStatus("EZYAUTH CANCEL");
				}
				if (forSettlement.getStatus().equals("D") || forSettlement.getStatus().equals("E")) {
					forSettlement.setStatus("EZYAUTH");
				}

				if (forSettlement.getStatus().equals("R")) {
					forSettlement.setStatus("REVERSAL");
				}

			}
			if (forSettlement.getDate() != null && forSettlement.getTime() != null) {
				try {

					String rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd").parse(forSettlement.getTimeStamp()));
					String rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
					forSettlement.setDate(rd);
					forSettlement.setTime(rt);
				} catch (ParseException e) {
				}

			}

			logger.info("loadTxnDetailsByID: " + forSettlement.getTrxId());
			TransactionRequest txReq = transactionService.loadTxnDetailsByID(forSettlement.getTrxId());
			forSettlement.setNumOfSale(txReq.getMaskedPan());// maskedpan
			logger.info("NumOfSale: " + forSettlement.getNumOfSale());
		}
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info("currently logged in as " +"currently logged in as " );
		List<TerminalDetails> terminalDetails = null;

		/*
		 * if (!txnType.equals("MOTO")) { pageBean = new PageBean("transactions list",
		 * "merchantweb/preauth/transactionList", Module.TRANSACTION_WEB,
		 * "merchantweb/transaction/sideMenuTransaction"); terminalDetails =
		 * transactionService .getTerminalDetails(currentMerchant.getMid().getMid());
		 * 
		 * } else {
		 */
		pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/transactionList", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		terminalDetails = transactionService.getTerminalDetails(currentMerchant.getMid().getMid(),
				currentMerchant.getMid().getMotoMid(), currentMerchant.getMid().getEzyrecMid());

		// }

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {

			String mtid = t.getTid();

			tidSet.add(mtid.toString());
		}

		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId().toString();
			dIdSet.add(did.toString());
		}

		model.addAttribute("devIdList", dIdSet);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("tidList", tidSet);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

// Paydee - EZYAUTH SUMMARY NEW Added on 17-05-2022	- Start

	@RequestMapping(value = { "/PreAuth" }, method = RequestMethod.GET)
	public String PaydeePreAuthList(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list PreAuth  transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/transactionListNew",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<PreauthModel> paginationBean = new PaginationBean<PreauthModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.PreAuthList(paginationBean, null, null, currentMerchant);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_NEW;
	}

	@RequestMapping(value = { "/searchPreAuthPaydee" }, method = RequestMethod.GET)
	public String PaydeesearchPreAuth(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search PreAuth Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/transactionListNew",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<PreauthModel> paginationBean = new PaginationBean<PreauthModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.PreAuthList(paginationBean, date, date1, currentMerchant);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_NEW;

	}

	@RequestMapping(value = "/ExportPreAuthPaydee", method = RequestMethod.GET)
	public ModelAndView paydeeExportPreAuth(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<PreauthModel> paginationBean = new PaginationBean<PreauthModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.PreAuthList(paginationBean, dat, dat1, currentMerchant);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found");

		} else {
			model.addAttribute("responseData", null);
		}

		List<PreauthModel> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("preauthMerTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("preauthMerTxnPdf", "txnList", list);
		}

	}

// Paydee - EZYAUTH SUMMARY NEW Added on 17-05-2022	- End

	@RequestMapping(value = { "/details/{id}" }, method = RequestMethod.GET)
	public String displayTransactionDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal)
			throws InvalidKeyException, NoSuchAlgorithmException, IOException, URISyntaxException {
		logger.info("in Transaction details");

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");

		/*
		 * PageBean pageBean = new PageBean("Transactions Details",
		 * "merchantweb/transaction/CardReceiptNew", null);
		 */
		PageBean pageBean = new PageBean("Transactions Details", "merchantweb/transaction/receipt_v0.2", null);
		logger.info("Transaction Id :" + id);
		TransactionRequest trRequest = transactionService.loadTransactionRequest(id);
		TransactionResponse trResponse = transactionService.loadTransactionResponse(id);
		// Merchant merchant =
		// merchantService.loadMerchant(principal.getName());
		Merchant merchant = merchantService.loadMerchant(myName);
		PreAuthorization settle = transactionService.getPreAuthTxn(id);
		Receipt a = transactionService.getReceiptSignature(id);

		DataTransferObject dt = new DataTransferObject();
		if (a != null) {
			if (a.getSignature() != null) {
				String signdata = new String(a.getSignature());
				// System.out.println(" String sign :"+signdata);
				dt.setSign(signdata);
			}
		}

		String txn = settle.getStatus();
		String pinEntry = settle.getPinEntry();
		dt.setPinEntry(pinEntry);
		if (settle.getTxnType() == null) {
			if (txn.equals("D") || txn.equals("E")) {
				dt.setTxnType("PRE-AUTHORIZATION");

			} else if (txn.equals("P") || txn.equals("A") || txn.equals("S")) {
				dt.setTxnType("PREAUTH SALE");

			} else {
				dt.setTxnType("PREAUTH CANCEL");

			}
		} else {

			if (txn.equals("D") || txn.equals("E")) {
				dt.setTxnType("EZYAUTH");

			} else if (txn.equals("P") || txn.equals("A") || txn.equals("S")) {
				dt.setTxnType("EZYAUTH SALE");

			} else {
				dt.setTxnType("EZYAUTH CANCEL");

			}

		}

		dt.setMerchantName(merchant.getBusinessName());
		dt.setMerchantAddr1(merchant.getBusinessAddress1());
		dt.setMerchantAddr2(merchant.getBusinessAddress2());
		dt.setMerchantCity(merchant.getCity());
		dt.setMerchantPostCode(merchant.getPostcode());
		dt.setMerchantContNo(merchant.getBusinessContactNumber());
		dt.setMerchantState(merchant.getState());

		dt.setLatitude(trRequest.getLatitude());
		dt.setLongitude(trRequest.getLongitude());
		// dt.setMapUrl(UrlSigner.GenerateMapImage(dt.getLatitude(),
		// dt.getLongitude()));
		// new changes for receipt
		dt.setBatchNo(settle.getBatchNo());
		logger.info("get batchno:" + dt.getBatchNo());

		if (settle.getInvoiceId() != null) {

			dt.setRefNo(settle.getInvoiceId());
			logger.info("invoice id:" + dt.getRefNo());
		} else {
			dt.setRefNo("");
		}

		// dt.setTxnType(trRequest.getTxnType());
		dt.setTid(trRequest.getTid());
		dt.setMid(trRequest.getMid());
		dt.setAmount(trRequest.getAmount());
		dt.setAdditionAmount(trRequest.getAdditionalAmount());
		// String rrn=HexatoAscii.hexaToAscii(trResponse.getRrn(), true);
		// dt.setRrn(rrn);
		dt.setAid(trRequest.getAid());
		dt.setStan(trRequest.getStan());
		dt.setMaskedPan(trRequest.getMaskedPan());
		/*
		 * if(trRequest.getAid()!=null&&trRequest.getCardHolderName()!=null){
		 * //dt.setCardType(CardType.getCardType(trRequest.getAid()));
		 * dt.setCardHolderName(trRequest.getCardHolderName()); }
		 */
		// new changes for receipt 23082017

		if (trRequest.getCardHolderName() != null) {
			dt.setCardHolderName(trRequest.getCardHolderName());

		} else {
			dt.setCardHolderName("");
		}
		if (trRequest.getCardScheme() != null && trRequest.getCardType() != null) {

			dt.setCardType(trRequest.getCardScheme() + " " + trRequest.getCardType());

			logger.info("Card Type from Txn Request Card Scheme and Card Type : " + dt.getCardType());

		} else if (trRequest.getApplicationLabel() != null) {
			dt.setCardType(trRequest.getApplicationLabel());
		} else if (trRequest.getApplicationLabel() == null && trRequest.getAid() != null) {
			dt.setCardType(CardType.getCardType(trRequest.getAid()));
			// mJson.setCardHolderName(trRequest.getCardHolderName());
		} else {
			dt.setCardType(" ");
		}
		// end
		/*
		 * dt.setDate(trResponse.getLocalDate()); dt.setTime(trResponse.getLocalTime());
		 */

		// String
		// resposecode=HexatoAscii.hexaToAscii(trResponse.getAidResponse(),
		// true);
		// dt.setResponseCode(resposecode);

		/*
		 * String sd = trResponse.getLocalDate() + new SimpleDateFormat("y").format(new
		 * java.util.Date());
		 */
		try {

			String rd = new SimpleDateFormat("dd-MMM-yyyy")
					.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(settle.getTimeStamp()));
			String rt = new SimpleDateFormat("HH:mm:ss")
					.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(settle.getTimeStamp()));
			dt.setDate(rd);
			dt.setTime(rt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String cardNum = null;
		if (trRequest.getMaskedPan() == null || trRequest.getMaskedPan().length() < 4) {
			cardNum = trRequest.getMaskedPan();
		} else {
			cardNum = trRequest.getMaskedPan().substring(trRequest.getMaskedPan().length() - 4);
		}
		String finalNum = String.format("XXXX %s", cardNum);
		dt.setCardNo(finalNum);
		double amount = 0;
		logger.info("Amount For receipt :" + trRequest.getAmount());
		if (trRequest.getAmount() != null) {
			amount = Double.parseDouble(trRequest.getAmount()) / 100;
		}
		double tips = 0;
		double total = amount;
		if (trRequest.getAdditionalAmount() != null) {
			tips = Double.parseDouble(trRequest.getAdditionalAmount()) / 100;
			amount = amount - tips;
			total = amount + tips;

		}
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		String output1 = myFormatter.format(tips);
		String output2 = myFormatter.format(total);
		// System.out.println(" Amount :"+output);
		// forSettlement.setAmount(output);
		dt.setAdditionAmount(output1);
		dt.setAmount(output);
		dt.setTotal(output2);
		dt.setCardHolderName(trRequest.getCardHolderName());
		dt.setAid(trRequest.getAid());
		// dt.setCardType(CardType.getCardType(trRequest.getAid()));
		// dt.setBatchNo(trRequest.getBatchNo());
		dt.setTraceNo(trRequest.getStan());
		dt.setRrn(hexaToAscii(trResponse.getRrn(), true));
		dt.setTC(trRequest.getStan() + trRequest.getTid());
		dt.setAid(trResponse.getAidResponse());
		if (settle != null) {
			dt.setBatchNo(settle.getBatchNo());
		}
		String resposecode = hexaToAscii(trResponse.getAidResponse(), true);

		dt.setApprCode(resposecode);
		/*
		 * Merchant merchant = merchantService.loadMerchantByPk(id); Transaction
		 * transaction = transactionService.loadTransactionByPk(id); MobileUser
		 * mobileUser = mobileUserService.loadMobileUserByPk(id);
		 * model.addAttribute("transaction", transaction);
		 * model.addAttribute("mobileUser", mobileUser); model.addAttribute("merchant",
		 * merchant);
		 */
		// model.addAttribute("dto", dt);
		request.setAttribute("dto", dt);
		model.addAttribute("pageBean", pageBean);
		// return "merchantweb/preauth/receipt";
		return "merchantweb/transaction/receipt_v0.2";
		// return "redirect:/transactionweb/list/1";
	}

	@RequestMapping(value = { "/ezyAuthlist/{currPage}" }, method = RequestMethod.GET)
	public String displayEzyAtuhTransactionSummary(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @PathVariable final int currPage) {
		// logger.info("about to list all transaction");
		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/transactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");

		// Merchant currentMerchant = merchantService.loadMerchant(principal.getName());

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		model.addAttribute("pageBean", pageBean);

		PaginationBean<PreAuthorization> paginationBean = new PaginationBean<PreAuthorization>();

		paginationBean.setCurrPage(currPage);
		transactionService.getPreAuthTxn(paginationBean, currentMerchant);
		// logger.info("currently logged in as"+ paginationBean.getItemList() );
		for (PreAuthorization forSettlement : paginationBean.getItemList()) {
			if (forSettlement.getAmount() != null) {
				double amount = 0;
				amount = Double.parseDouble(forSettlement.getAmount()) / 100;
				forSettlement.setAmount(amount + "0");
			}
			if (forSettlement.getTxnType().equals("MOTO")) {
				if (forSettlement.getStatus().equals("S")) {
					forSettlement.setStatus("SETTLED");
				}
				if (forSettlement.getStatus().equals("P")) {
					forSettlement.setStatus("EZYAUTH SALE");
				}
				if (forSettlement.getStatus().equals("A")) {
					forSettlement.setStatus("EZYAUTH SALE");
				}
				if (forSettlement.getStatus().equals("C")) {
					forSettlement.setStatus("EZYAUTH CANCEL");
				}
				if (forSettlement.getStatus().equals("D")) {
					forSettlement.setStatus("EZYAUTH");
				}
				if (forSettlement.getStatus().equals("E")) {
					forSettlement.setStatus("EZYAUTH");
				}
				if (forSettlement.getStatus().equals("R")) {
					forSettlement.setStatus("REVERSAL");
				}
			} else {
				if (forSettlement.getStatus().equals("S")) {
					forSettlement.setStatus("SETTLED");
				}
				if (forSettlement.getStatus().equals("P")) {
					forSettlement.setStatus("PREAUTH SALE");
				}
				if (forSettlement.getStatus().equals("A")) {
					forSettlement.setStatus("PREAUTH SALE");
				}
				if (forSettlement.getStatus().equals("C")) {
					forSettlement.setStatus("PREAUTH CANCEL");
				}
				if (forSettlement.getStatus().equals("D")) {
					forSettlement.setStatus("PRE-AUTHORIZATION");
				}
				if (forSettlement.getStatus().equals("E")) {
					forSettlement.setStatus("PRE-AUTHORIZATION");
				}
				if (forSettlement.getStatus().equals("R")) {
					forSettlement.setStatus("REVERSAL");
				}
			}

			if (forSettlement.getDate() != null && forSettlement.getTime() != null) {
				try {

					String rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(forSettlement.getTimeStamp()));

					String rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
					forSettlement.setDate(rd);
					forSettlement.setTime(rt);
				} catch (ParseException e) {
				}

			}
		}

		model.addAttribute("paginationBean", paginationBean);
		// TODO to get tid and device id

		logger.info("Motomid: " + currentMerchant.getMid().getMotoMid());
		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMotoMid());
		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			if (t.getTid() != null) {
				String mtid = t.getTid();
				tidSet.add(mtid.toString());
				// tidSet.add(t.getDeviceId());
			}
		}
		ArrayList<String> dIdSet = new ArrayList<String>();
		for (TerminalDetails t : terminalDetails) {
			// String did = t.getDeviceId();
			if (t.getDeviceId() != null) {
				String did = t.getDeviceId().toString();
				dIdSet.add(did.toString());
			}

		}
		model.addAttribute("devIdList", dIdSet);
		model.addAttribute("tidList", tidSet);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/preAuthTransaction" }, method = RequestMethod.GET)
	public String preAuthTransaction(final Model model, @ModelAttribute("preAuthTxnDet") PreAuthTxnDet preAuthTxnDet,
			final java.security.Principal principal, HttpServletRequest request) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("preAuth transaction currently logged : " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());

		PageBean pageBean = new PageBean("transactions list", "merchantweb/preauth/PreAuthTransaction",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		List<CountryCurPhone> listCountry = transactionService.loadCountryCurrency();

		List<Country> countryList = new ArrayList<Country>();

		for (CountryCurPhone ccp : listCountry) {
			Country cty = new Country();
			cty.setCountryCode(ccp.getCountryIso3());
			cty.setCountryIso(ccp.getCountryIso2());
			cty.setCountryName(ccp.getCountryName());
			cty.setPhoneCode("+" + ccp.getCountryPhone());
			countryList.add(cty);
		}

		String motoMid = null;
		List<TerminalDetails> terminalDetails = null;

		if (currentMerchant.getMerchantType() == null || currentMerchant.getMerchantType().equalsIgnoreCase("P")) {
			if (currentMerchant.getMid().getMotoMid() != null) {
				terminalDetails = transactionService.getTerminalDetails(currentMerchant.getMid().getMotoMid());
				motoMid = currentMerchant.getMid().getMotoMid();
			}

		} else if (currentMerchant.getMerchantType() != null
				&& currentMerchant.getMerchantType().equalsIgnoreCase("U")) {
			if (currentMerchant.getMid().getUmMotoMid() != null) {
				terminalDetails = transactionService.getTerminalDetails(currentMerchant.getMid().getUmMotoMid());
				motoMid = currentMerchant.getMid().getUmMotoMid();
			}
		}

		List<MobileUser> mobileuser1 = new ArrayList<MobileUser>();

		for (TerminalDetails t : terminalDetails) {
			if (t.getTid() != null) {
				// logger.info("moto tid: " + t.getTid());

				MobileUser m = transactionService.getMobileUserByMotoTid(t.getTid());

				if (m != null) {
					m.setFailedLoginAttempt(0);
					logger.info("moto tid mmm: " + m.getMotoTid() + " expiry date: " + t.getSuspendedDate());
					boolean status = validateExpiryDate(t.getSuspendedDate().toString());
					if (status) {

						m.setFailedLoginAttempt(1);
					}
					logger.info("status: " + m.getFailedLoginAttempt());
					if (m.getMotoTid() != null) {

						logger.info("mobile username: " + m.getUsername() + m.getMotoTid());
						mobileuser1.add(m);

					}

				}
			}
		}
		model.addAttribute("mobileuser", mobileuser1);
		model.addAttribute("motoMid", motoMid);
		model.addAttribute("listCountry", countryList);
		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/preAuthSubmit" }, method = RequestMethod.POST)
	public String preAuthSubmitTransaction(final Model model,
			@ModelAttribute("preAuthTxnDet") PreAuthTxnDet preAuthTxnDet, final java.security.Principal principal,
			HttpServletRequest request, HttpServletResponse response) {

		TerminalDetails data = null;
		preAuthTxnDet.setPhno(preAuthTxnDet.getPhno().replaceAll(" ", ""));
		logger.info("/preAuthSubmit");

		logger.info("phncode " + preAuthTxnDet.getPhncode1());
		logger.info("amount " + preAuthTxnDet.getAmount());
		logger.info("phnno" + preAuthTxnDet.getPhno());
		logger.info("reference " + preAuthTxnDet.getReferrence());
		logger.info("email :" + preAuthTxnDet.getEmail());
		logger.info("latitude :" + preAuthTxnDet.getLatitude());
		logger.info("latitude :" + preAuthTxnDet.getLongitude());

		if (preAuthTxnDet.getAmount().contains(".")) {

			preAuthTxnDet.setAmount(
					String.format("%012d", (long) Double.parseDouble(preAuthTxnDet.getAmount().replace(".", ""))));
			logger.info(preAuthTxnDet.getAmount());
		}

		else {
			preAuthTxnDet.setAmount(String.format("%012d", (long) Double.parseDouble(preAuthTxnDet.getAmount()) * 100));
			logger.info(preAuthTxnDet.getAmount());
		}
		logger.info("PreAuth amout: " + preAuthTxnDet.getAmount());
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("PreAuth trans currently logged as: " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		logger.info("hostType: " + currentMerchant.getMerchantType());

		if (currentMerchant.getMerchantType() == null) {
			preAuthTxnDet.setHostType("P");

		} else {
			logger.info("hostType: " + currentMerchant.getMerchantType());
			preAuthTxnDet.setHostType(currentMerchant.getMerchantType());
		}

		if (preAuthTxnDet.getPhno() != null && !preAuthTxnDet.getPhno().isEmpty()) {
			String phn = preAuthTxnDet.getPhncode1() + preAuthTxnDet.getPhno();
			logger.info("Entire Phone Number " + phn);
			preAuthTxnDet.setPhno(phn);
			logger.info("Entire Phone Number " + preAuthTxnDet.getPhno() + " "
					+ preAuthTxnDet.getPhno().replaceAll(" ", ""));
			preAuthTxnDet.setPhno(preAuthTxnDet.getPhno().replaceAll(" ", ""));
		}

		if (currentMerchant.getMerchantType() == null || currentMerchant.getMerchantType().equalsIgnoreCase("P")) {
			if (currentMerchant.getMid().getMotoMid() != null) {
				preAuthTxnDet.setMotoMid(currentMerchant.getMid().getMotoMid());
			} else if (currentMerchant.getMid().getMid() != null) {

				preAuthTxnDet.setMotoMid(currentMerchant.getMid().getMid());

			}
		} else if (currentMerchant.getMerchantType() != null
				&& currentMerchant.getMerchantType().equalsIgnoreCase("U")) {
			if (currentMerchant.getMid().getUmMotoMid() != null) {

				preAuthTxnDet.setMotoMid(currentMerchant.getMid().getUmMotoMid());
			} else if (currentMerchant.getMid().getUmMid() != null) {

				preAuthTxnDet.setMotoMid(currentMerchant.getMid().getUmMid());

			}
		}else if (currentMerchant.getMerchantType() != null
				&& currentMerchant.getMerchantType().equalsIgnoreCase("FIUU")) {
			if (currentMerchant.getMid().getFiuuMid() != null) {
				preAuthTxnDet.setMotoMid(currentMerchant.getMid().getFiuuMid());
			} 
		}

		logger.info("MID : " + preAuthTxnDet.getMotoMid());
		// Load Tid by Mid
		try {
			data = merchantService.loadTerminalDetailsByMid(preAuthTxnDet.getMotoMid());
			preAuthTxnDet.setTid(data.getTid());
		} catch (Exception e) {

			logger.info("Not Found TID For this MID");

		}
		logger.info("TID : " + preAuthTxnDet.getTid());

		PageBean pageBean = new PageBean("transactions list", "merchantweb/Request/Transaction", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		ResponseDetails rd = null;
		try {
			rd = motoWebService.preAuthRequest(preAuthTxnDet);
			if (rd != null) {
				preAuthTxnDet.setAmount(
						new DecimalFormat("#,##0.00").format(Double.parseDouble(preAuthTxnDet.getAmount()) / 100));
				Date date1;
				logger.info("Response Data.." + rd.getResponseData().getInvoiceId());
				request.setAttribute("responseData", rd.getResponseData().getInvoiceId());
				request.setAttribute("responseData1", rd.getResponseData().getOpt());
				request.setAttribute("responseSuccess", rd.getResponseMessage());
			}
		} catch (Exception e) {
			logger.error("Exception : " + e.getMessage(), e);
		}

		model.addAttribute("viaEmail", preAuthTxnDet.getEmail() == null || preAuthTxnDet.getEmail().trim().isEmpty() ? "false" : "true");
		model.addAttribute("description", 
			    (rd != null && rd.getResponseData() != null && rd.getResponseData().getInvoiceId() != null)
			        ? rd.getResponseData().getInvoiceId()
			        : "Oops.. Unable to make transaction due to connection refusal."
			);		
		model.addAttribute("smsUrl", 
			    (rd != null && rd.getResponseData() != null && rd.getResponseData().getOpt() != null) 
			        ? rd.getResponseData().getOpt() 
			        : ""
			);
		model.addAttribute("pageBean", pageBean);
//		model.addAttribute("Success",
//				(rd != null && rd.getResponseData() != null && rd.getResponseData().getInvoiceId() != null)
//						? rd.getResponseData().getInvoiceId()
//						: "");
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	public static String hexaToAscii(String s, boolean toString) {

		String retString = "";
		String tempString = "";
		int offset = 0;
		if (toString) {
			for (int i = 0; i < s.length() / 2; i++) {

				tempString = s.substring(offset, offset + 2);
				retString += tempString.equalsIgnoreCase("1c") ? "[1C]" : decodeHexString(tempString);
				offset += 2;
			} // end for
		} else {

			for (int i = 0; i < s.length(); i++) {

				tempString = s.substring(offset, offset + 1);
				retString += encodeHexString(tempString);
				offset += 1;
			} // end for
		}
		return retString;
	} // end hexaToAscii

	public static String decodeHexString(String hexText) {

		String decodedText = null;
		String chunk = null;

		if (hexText != null && hexText.length() > 0) {
			int numBytes = hexText.length() / 2;

			byte[] rawToByte = new byte[numBytes];
			int offset = 0;
			for (int i = 0; i < numBytes; i++) {
				chunk = hexText.substring(offset, offset + 2);
				offset += 2;
				rawToByte[i] = (byte) (Integer.parseInt(chunk, 16) & 0x000000FF);
			}
			// System.out.println(rawToByte.toString());
			decodedText = new String(rawToByte);
		}
		return decodedText;
	}

	public static String encodeHexString(String sourceText) {
		byte[] rawData = sourceText.getBytes();
		StringBuffer hexText = new StringBuffer();
		String initialHex = null;
		int initHexLength = 0;

		for (int i = 0; i < rawData.length; i++) {
			// System.out.println("raw "+rawData[i]);
			int positiveValue = rawData[i] & 0x000000FF;
			initialHex = Integer.toHexString(positiveValue);
			initHexLength = initialHex.length();
			while (initHexLength++ < 2) {
				hexText.append("0");
			}
			hexText.append(initialHex);
		}
		return hexText.toString().toUpperCase();
	}

	public boolean validateExpiryDate(String expDate) {
		boolean validate = false;

		Date now = new Date();

		logger.info("Today : " + now + " : " + expDate);

		String resDate = null;
		try {

			resDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(expDate));

			String resTime = new SimpleDateFormat("HH:mm:ss")
					.format(new SimpleDateFormat("HH:mm:ss").parse(expDate.substring(11)));
			logger.info("After Date : " + resDate + " : " + resTime);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		LocalDate date1 = LocalDate.parse(resDate);
		LocalDate date2 = LocalDate.now();

		int days = Days.daysBetween(date2, date1).getDays();
		logger.info(" Days : " + days);

		if (days > 0) {

			validate = true;

		} else {
			logger.info(" Day Expired ");
			validate = false;
		}
		logger.info("status: " + validate);
		return validate;

	}

	@RequestMapping(value = { "/export" }, method = RequestMethod.GET)
	public ModelAndView displayExportTransactionSearchByTid(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam(required = false) final String tid,
			@RequestParam(required = false) String fromDate, @RequestParam(required = false) String toDate,
			@RequestParam(required = false) String status, @RequestParam(required = false) String export,

			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		String fromDate1 = null;
		String toDate1 = null;
		PageBean pageBean = null;
		/*
		 * logger.info("fromDate & toDate & Tid :" + fromDate + ": & :" + toDate +
		 * ": & :" + tid + ": & :" + txnType);
		 */
		logger.info("fromDate & toDate & Tid :" + fromDate + ": & :" + toDate + ": & :" + tid);
		if (fromDate != null && toDate != null) {
			try {
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

		}
		logger.info("fromDate & toDate & Tid :" + fromDate1 + ": & :" + toDate1 + ": & :" + tid);

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<PreAuthorization> paginationBean = new PaginationBean<PreAuthorization>();
		paginationBean.setCurrPage(currPage);

		transactionService.searchPreAuth(fromDate1, toDate1, tid, status, paginationBean, currentMerchant);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		logger.info("test data " + paginationBean.getItemList());
		for (PreAuthorization forSettlement : paginationBean.getItemList()) {
			if (forSettlement.getAmount() != null) {
				double amount = 0;
				amount = Double.parseDouble(forSettlement.getAmount()) / 100;
				forSettlement.setAmount(amount + "0");
			}
			if (forSettlement.getTxnType() == null) {
				/*
				 * if (forSettlement.getStatus().equals("S")) {
				 * forSettlement.setStatus("SETTLED"); }
				 */
				if (forSettlement.getStatus().equals("P") || forSettlement.getStatus().equals("A")
						|| forSettlement.getStatus().equals("S")) {
					forSettlement.setStatus("PREAUTH SALE");
				}

				if (forSettlement.getStatus().equals("C")) {
					forSettlement.setStatus("PREAUTH CANCEL");
				}
				if (forSettlement.getStatus().equals("D") || forSettlement.getStatus().equals("E")) {
					forSettlement.setStatus("PRE-AUTHORIZATION");
				}

				if (forSettlement.getStatus().equals("R")) {
					forSettlement.setStatus("REVERSAL");
				}
			} else {

				/*
				 * if (forSettlement.getStatus().equals("S")) {
				 * forSettlement.setStatus("SETTLED"); }
				 */
				if (forSettlement.getStatus().equals("P") || forSettlement.getStatus().equals("A")
						|| forSettlement.getStatus().equals("S")) {
					forSettlement.setStatus("EZYAUTH SALE");
				}

				if (forSettlement.getStatus().equals("C")) {
					forSettlement.setStatus("EZYAUTH CANCEL");
				}
				if (forSettlement.getStatus().equals("D") || forSettlement.getStatus().equals("E")) {
					forSettlement.setStatus("EZYAUTH");
				}

				if (forSettlement.getStatus().equals("R")) {
					forSettlement.setStatus("REVERSAL");
				}

			}
			if (forSettlement.getDate() != null && forSettlement.getTime() != null) {
				try {

					String rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd").parse(forSettlement.getTimeStamp()));
					String rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
					forSettlement.setDate(rd);
					forSettlement.setTime(rt);
				} catch (ParseException e) {
				}

			}

			logger.info("loadTxnDetailsByID: " + forSettlement.getTrxId());
			TransactionRequest txReq = transactionService.loadTxnDetailsByID(forSettlement.getTrxId());
			forSettlement.setNumOfSale(txReq.getMaskedPan());// maskedpan
			logger.info("NumOfSale: " + forSettlement.getNumOfSale());
		}
		List<PreAuthorization> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("preauthMerTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("preauthMerTxnPdf", "txnList", list);
		}

	}

	// New PreAuth Summary Started on 04-05-22

	@RequestMapping(value = { "/PreAuthList" }, method = RequestMethod.GET)
	public String PreAuthList(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list PreAuth  transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/PreauthtransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<PreauthModel> paginationBean = new PaginationBean<PreauthModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.PreAuthList(paginationBean, null, null, currentMerchant);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchPreAuth" }, method = RequestMethod.GET)
	public String searchPreAuth(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage, final java.security.Principal principal) {
		logger.info("search PreAuth Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/PreauthtransactionList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<PreauthModel> paginationBean = new PaginationBean<PreauthModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.PreAuthList(paginationBean, date, date1, currentMerchant);

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/ExportPreAuth", method = RequestMethod.GET)
	public ModelAndView ExportPreAuth(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<PreauthModel> paginationBean = new PaginationBean<PreauthModel>();
		paginationBean.setCurrPage(currPage);

	//	transactionService.PreAuthList(paginationBean, dat, dat1, currentMerchant);
		transactionService.PreAuthListExport(paginationBean, dat, dat1, currentMerchant);
		
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found");

		} else {
			model.addAttribute("responseData", null);
		}

		List<PreauthModel> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("preauthMerTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("preauthMerTxnPdf", "txnList", list);
		}

	}

	@RequestMapping(value = { "/PreAuthList1" }, method = RequestMethod.GET)
	public String PreAuthList1(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list PreAuth  transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/PreauthtransactionList1",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<PreauthModel> paginationBean = new PaginationBean<PreauthModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.PreAuthList1(paginationBean, null, null, currentMerchant);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/searchPreAuth1" }, method = RequestMethod.GET)
	public String searchPreAuth1(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage, final java.security.Principal principal) {
		logger.info("search PreAuth Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		//logger.info("search preauth :");
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/PreauthtransactionList1",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<PreauthModel> paginationBean = new PaginationBean<PreauthModel>();
		paginationBean.setCurrPage(currPage);

		transactionService.PreAuthList1(paginationBean, date, date1, currentMerchant);

		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = "/ExportPreAuth1", method = RequestMethod.GET)
	public ModelAndView ExportPreAuth1(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<PreauthModel> paginationBean = new PaginationBean<PreauthModel>();
		paginationBean.setCurrPage(currPage);

		//transactionService.PreAuthList1(paginationBean, dat, dat1, currentMerchant);
		transactionService.PreAuthList1Export(paginationBean, dat, dat1, currentMerchant);

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found");

		} else {
			model.addAttribute("responseData", null);
		}

		List<PreauthModel> list = paginationBean.getItemList();

		if (!(export.equals("PDF"))) {

			return new ModelAndView("preauthMerTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("preauthMerTxnPdf", "txnList", list);
		}

	}

	// New PreAuth Summary Ended on 04-05-22

	// PreAuth Void Services Start

	@RequestMapping(value = { "/PreAuthVoid/{id}" }, method = RequestMethod.GET)
	public String PreAuthVoid(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/voidpreauth/PreAuthVoidDetails",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		BigInteger txid = new BigInteger(id);

		logger.info("Transaction ID: " + txid);

		PreAuthorization preAuth = transactionService.loadPreAuthorizationbyTxnId(txid);

		if (currentMerchant.getMerchantType() == null) {

			preAuth.setHostType("P");
		} else if (currentMerchant.getMerchantType().equalsIgnoreCase("P")) {
			preAuth.setHostType("P");
		} else if (currentMerchant.getMerchantType() != null
				&& currentMerchant.getMerchantType().equalsIgnoreCase("U")) {

			preAuth.setHostType("U");

		}

		if (preAuth.getTimeStamp() != null) {
			String rd = null;
			String rt = null;
			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(preAuth.getTimeStamp()));
				rt = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(preAuth.getTimeStamp()));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			preAuth.setDate(rd);
			preAuth.setTime(rt);
		} else {
			preAuth.setDate("");
			preAuth.setTime("");
		}

		if (preAuth.getAmount() == null) {
			preAuth.setAmount("");
		} else if (preAuth.getAmount() != null) {

			double amount = 0;
			amount = Double.parseDouble(preAuth.getAmount()) / 100;
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(amount);
			preAuth.setAmount(output);

		} else {
			preAuth.setAmount("");
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("preAuth", preAuth);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/ProcessPreAuthVoid" }, method = RequestMethod.POST)
	public String ProcessPreAuthVoid(final Model model, @ModelAttribute("preAuth") PreAuthorization preAuth,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {

		logger.info("Processing PRE-AUTH : VOID");

		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/voidpreauth/PreAuthVoidSuccess",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		String status = null;
		ResponseDetails data = MotoPaymentCommunication.PreAuthVoid(preAuth);
		if (data != null) {
			if (data.getResponseCode().equals("0000")) {
				logger.info("Void Successfully");
				status = "Transaction Voided ";
			} else {
				logger.info("Void Failed");
				status = "Failed To Void Transaction";
			}
		}

		model.addAttribute("status", status);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/PreAuthVoid1/{id}" }, method = RequestMethod.GET)
	public String PreAuthVoid1(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/voidpreauth/PreAuthVoidDetails1",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("Transaction ID: " + id);

		UMEcomTxnResponse umtxn = transactionService.loadUMEcomTxnResponsebyTxnId(id);

		if (currentMerchant.getMerchantType() == null) {

			umtxn.setF261_HostID("P");
		} else if (currentMerchant.getMerchantType().equalsIgnoreCase("P")) {
			umtxn.setF261_HostID("P");
		} else if (currentMerchant.getMerchantType() != null
				&& currentMerchant.getMerchantType().equalsIgnoreCase("U")) {

			umtxn.setF261_HostID("U");

		}else if (currentMerchant.getMerchantType() != null
				&& currentMerchant.getMerchantType().equalsIgnoreCase("FIUU")) {

			umtxn.setF261_HostID("FIUU");

		}

		if (umtxn.getTimeStamp() != null) {
			String rd = null;
			String rt = null;
			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(umtxn.getTimeStamp()));
				rt = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(umtxn.getTimeStamp()));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			umtxn.setH003_TDT(rd);
			umtxn.setH004_TTM(rt);
		} else {
			umtxn.setH003_TDT("");
			umtxn.setH003_TDT("");
		}

		if (umtxn.getF007_TxnAmt() == null) {
			umtxn.setF007_TxnAmt("");
		} else if (umtxn.getF007_TxnAmt() != null) {

			double amount = 0;
			amount = Double.parseDouble(umtxn.getF007_TxnAmt()) / 100;
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(amount);
			umtxn.setF007_TxnAmt(output);

		} else {
			umtxn.setF007_TxnAmt("");
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("umtxn", umtxn);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// rk added(06/07/22) - preauth void for wire
	@RequestMapping(value = { "/PreAuthWireVoid1/{id}" }, method = RequestMethod.GET)
	public String PreAuthWireVoid1(final Model model, @PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/voidpreauth/PreAuthWireVoidDetails1",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("Transaction ID: " + id);

		BigInteger bid = new BigInteger(id);

		logger.info("after conversion trx id is " + bid);

		PreAuthorization prtxn = transactionService.loadPreAuthorizationbyTxnId(bid);

		if (currentMerchant.getMerchantType() == null) {

			prtxn.setHostType("P");
		} else if (currentMerchant.getMerchantType().equalsIgnoreCase("P")) {
			prtxn.setHostType("P");
		} else if (currentMerchant.getMerchantType() != null
				&& currentMerchant.getMerchantType().equalsIgnoreCase("U")) {

			prtxn.setHostType("U");

		}

		if (prtxn.getTimeStamp() != null) {
			String rd = null;
			String rt = null;
			try {
				rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(prtxn.getTimeStamp()));
				rt = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(prtxn.getTimeStamp()));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			prtxn.setDate(rd);
			prtxn.setTime(rt);
		} else {
			prtxn.setDate("");
			prtxn.setTime("");
		}

		if (prtxn.getAmount() == null) {
			prtxn.setAmount("");
		} else if (prtxn.getAmount() != null) {

			double amount = 0;
			amount = Double.parseDouble(prtxn.getAmount()) / 100;
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String output = myFormatter.format(amount);
			prtxn.setAmount(output);

		} else {
			prtxn.setAmount("");
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("prtxn", prtxn);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/ProcessPreAuthVoid1" }, method = RequestMethod.POST)
	public String ProcessPreAuthVoid1(final Model model, @ModelAttribute("umtxn") UMEcomTxnResponse umtxn,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {

		logger.info("Processing PRE-AUTH : VOID");

		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/voidpreauth/PreAuthVoidSuccess1",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		String status = null;
		ResponseDetails data = null;
		if(umtxn.getF261_HostID().equals("FIUU")) {
			 data = MotoPaymentCommunication.FiuuCancelPaymentForAuth(umtxn);
		}else {
		 data = MotoPaymentCommunication.PreAuthVoid1(umtxn);
		}
		if (data != null) {
			if (data.getResponseCode().equals("0000")) {
				logger.info("Void Successfully");
				status = "Transaction Voided ";
			} else {
				logger.info("Void Failed");
				status = "Failed To Void Transaction";
			}
		}

		model.addAttribute("status", status);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// rk added (7-7-2022)
	@RequestMapping(value = { "/ProcesswirePreAuthVoid1" }, method = RequestMethod.POST)
	public String ProcesswirePreAuthVoid1(final Model model, @ModelAttribute("prtxn") PreAuthorization prtxn,
			HttpServletRequest request, HttpServletResponse response, final java.security.Principal principal) {

		logger.info("Processing Wire PRE-AUTH : VOID");

		PageBean pageBean = new PageBean("transactions list", "merchantweb/EZYAUTH/voidpreauth/PreAuthVoidSuccess1",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		String status = null;
		ResponseDetails data = MotoPaymentCommunication.wirePreAuthVoid1(prtxn);
		if (data != null) {
			if (data.getResponseCode().equals("0000")) {
				logger.info("Void Successfully");
				status = "Transaction Voided ";
			} else {
				logger.info("Void Failed");
				status = "Failed To Void Transaction";
			}
		}

		model.addAttribute("status", status);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// PreAuth Void Services End

	// PreAuth Convert To Sale Service - Start

	@RequestMapping(value = "/ConvertToSale")
	public @ResponseBody PreauthModel ConvertToSale(@RequestParam String amount,

			@RequestParam String userAmount, @RequestParam String txnid, HttpServletRequest request,
			HttpServletResponse response, Model model) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		double txnAmount = 0;
		double preAmount = 0;
		double sAmount = 0;
		double eAmount = 0;
		double fAmount = 0;
		double usersAmount = 0;
		String status = null;
		String preAmt = null;
		PreauthModel preAuth = new PreauthModel();

		logger.info("Amount: " + amount);
		logger.info("userAmount: " + userAmount);
		logger.info("txnid: " + txnid);
		
		String finalAmount = amount.replace(",", "");
		String finaluserAmount = userAmount.replace(",", "");
		logger.info("finalAmt: " + finalAmount);
		logger.info("finaluserAmount: " + finaluserAmount);

//        if (amount != null && !amount.isEmpty()) {
//            preAmount = Double.parseDouble(amount);
//            logger.info("preAmount: " + preAmount);
//            fAmount = preAmount * 20 / 100;
//            logger.info(" Calculate preAmount by 20%  : " + fAmount);
//
//            sAmount = preAmount + fAmount;
//            eAmount = preAmount - fAmount;
//            preAmt = amount;
//        }

		if (finaluserAmount != null && !finaluserAmount.isEmpty()) {
			preAmount = Double.parseDouble(finalAmount);
			logger.info("preAmount: " + preAmount);
			usersAmount = Double.parseDouble(finaluserAmount);
			logger.info("usersAmount: " + usersAmount);
			fAmount = preAmount * 20 / 100;
			logger.info(" Calculate preAmount by 20%  : " + fAmount);

			sAmount = preAmount + fAmount;
			eAmount = preAmount - fAmount;
			preAmt = finaluserAmount;

			logger.info("sAmount: " + sAmount);
			logger.info("eAmount: " + eAmount);

//			if (usersAmount >= eAmount && usersAmount <= sAmount) {
			if (usersAmount <= sAmount) {
				logger.info("Convert To Sale is Processing: ");

				// Amount
				if (preAmt.contains(".")) {

					preAuth.setAmount(String.format("%012d", (long) Double.parseDouble(preAmt.replace(".", ""))));

					logger.info("Pharsed Amount" + preAuth.getAmount());
				} else {
					preAuth.setAmount(String.format("%012d", (long) Double.parseDouble(preAmt) * 100));
					logger.info("Pharsed Amount" + preAuth.getAmount());
				}
				// Merchant ID

				preAuth.setMerchantId(currentMerchant.getId());
				logger.info("Merchant ID " + currentMerchant.getId());

				// Host Type

				String merchantType = currentMerchant.getMerchantType();

				if (merchantType == null) {

					preAuth.setMerchantType("P");

				} else if (merchantType.equalsIgnoreCase("P")) {
					preAuth.setMerchantType("P");

				} else if (merchantType.equalsIgnoreCase("U")) {

					preAuth.setMerchantType("U");

				}

				logger.info("Merchant Type " + preAuth.getMerchantType());

				// Transaction ID

				preAuth.setTxnid(txnid);
				logger.info("Transaction ID " + txnid);

				ResponseDetails data = MotoPaymentCommunication.ConvertToSale(preAuth);
				if (data != null) {
					if (data.getResponseCode().equals("0000")) {
						logger.info("Convert To Sale Done");
						preAuth.setResponseCode("0000");
						preAuth.setResponseDescription("Convert To Sale Done ");
					} else {
						logger.info("Failed To Convert To Sale");
						preAuth.setResponseCode("0000");
						preAuth.setResponseDescription("Failed To Convert To Sale");
					}
				}

			} else {
				preAuth.setResponseCode("0001");
				preAuth.setResponseDescription("Please enter an amount that is  greater than 20% for PreAuth Amount.");
			}

		} else {

			preAmt = finalAmount;

			logger.info("Convert To Sale is Processing: ");

			// Amount
			if (preAmt.contains(".")) {

				preAuth.setAmount(String.format("%012d", (long) Double.parseDouble(preAmt.replace(".", ""))));

				logger.info("Pharsed Amount" + preAuth.getAmount());
			} else {
				preAuth.setAmount(String.format("%012d", (long) Double.parseDouble(preAmt) * 100));
				logger.info("Pharsed Amount" + preAuth.getAmount());
			}
			// Merchant ID

			preAuth.setMerchantId(currentMerchant.getId());
			logger.info("Merchant ID " + currentMerchant.getId());

			// Host Type

			String merchantType = currentMerchant.getMerchantType();

			if (merchantType == null) {

				preAuth.setMerchantType("P");

			} else if (merchantType.equalsIgnoreCase("P")) {
				preAuth.setMerchantType("P");

			} else if (merchantType.equalsIgnoreCase("U")) {

				preAuth.setMerchantType("U");

			}

			logger.info("Merchant Type " + preAuth.getMerchantType());

			// Transaction ID

			preAuth.setTxnid(txnid);
			logger.info("Transaction ID " + txnid);

			ResponseDetails data = MotoPaymentCommunication.ConvertToSale(preAuth);

			if (data != null) {
				if (data.getResponseCode().equals("0000")) {
					logger.info("Convert To Sale Done");
					preAuth.setResponseCode("0000");
					preAuth.setResponseDescription("Convert To Sale Done ");
				} else {
					logger.info("Failed To Convert To Sale");
					preAuth.setResponseCode("0000");
					preAuth.setResponseDescription("Failed To Convert To Sale");
				}
			}

		}
		return preAuth;

	}
	// PreAuth Convert To Sale Service - End

}
