package com.mobiversa.payment.controller;

import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.DataTransferObject;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.TransactionService;

@Controller
@RequestMapping(value = NonMerchantWebTransactionController.URL_BASE)
public class NonMerchantWebTransactionController extends BaseController {

	@Autowired
	private MobileUserService mobileUserService;
	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;

	public static final String URL_BASE = "/transaction/nonmerchantweb";
	private static final Logger logger = Logger
			.getLogger(NonMerchantWebTransactionController.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("Test1");
		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayCashTransactionSummary(final Model model,
			final java.security.Principal principal,
			@PathVariable final int currPage) {

		PageBean pageBean = new PageBean("transactions list",
				"nonmerchantweb/transaction/transactionList",
				Module.TRANSACTION_WEB, "nonmerchantweb/transaction");
		model.addAttribute("pageBean", pageBean);
		String myName = principal.getName();
		logger.info("currently logged in as non merchant " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		logger.info(" for test merchant id :"+currentMerchant.getId());
		logger.info("about to list all  transaction non merchant");
		
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();

		paginationBean.setCurrPage(currPage);
		transactionService.getForSettlementnonmerchant(paginationBean, currentMerchant);
		logger.info("the merchant obj based on currently logged in user is non merchant: "
				+ currentMerchant.getId());
		logger.info("Paginationbean: "
				+ paginationBean.getItemList().size());
		
		
		if (paginationBean.getItemList().size() > 0) {

			logger.info("inside if: " + currentMerchant.getUsername());
			for (ForSettlement forSettlement : paginationBean.getItemList()) 
			{
				logger.info("inside for tid details:"
						+ forSettlement.getTid().toString());

				if (forSettlement.getAmount()!= null)
				{
					logger.info("inside for if terminal details contact Name:"
							+ forSettlement.getAmount());
					double amount = 0;
					amount = Double.parseDouble(forSettlement.getAmount()) / 100;
					// forSettlement.setAmount(amount+"0");
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String output = myFormatter.format(amount);
					// System.out.println(" Amount :"+output);
					forSettlement.setAmount(output);
				}
				if (forSettlement.getStatus().equals("CT"))
				{
					forSettlement.setStatus("CASH SALE");
				}
				if (forSettlement.getStatus().equals("CV"))
				{
					forSettlement.setStatus("CASH CANCELLED");
				}
				
				if (forSettlement.getDate() != null && forSettlement.getTime() != null)
				{
					try {
						// String sd=forSettlement.getDate()+new
						// SimpleDateFormat("y").format(new java.util.Date());
						String rd = new SimpleDateFormat("dd-MMM-yyyy")
						.format(new SimpleDateFormat("yyyy-MM-dd")
								.parse(forSettlement.getTimeStamp()));
						
						String rt = new SimpleDateFormat("HH:mm:ss")
						.format(new SimpleDateFormat("HHmmss")
								.parse(forSettlement.getTime()));
						
						/*String sd = ;
						String rd = new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("yyyy-MM-dd")
										.parse(sd));
						String rt = new SimpleDateFormat("HH:mm:ss")
								.format(new SimpleDateFormat("HHmmss")
										.parse(forSettlement.getTime()));*/
						forSettlement.setDate(rd);
						forSettlement.setTime(rt);
					} catch (ParseException e) {
					}

				}
			}

			model.addAttribute("paginationBean", paginationBean);
			// TODO to get tid and device id
			List<TerminalDetails> terminalDetails = transactionService
					.getTerminalDetails(currentMerchant.getMid().getMid());
			Set<String> tidSet = new HashSet<String>();
			for (TerminalDetails t : terminalDetails) {
				String mtid = t.getTid();
				tidSet.add(mtid.toString());
				// tidSet.add(t.getDeviceId());
			}
			ArrayList<String> dIdSet = new ArrayList<String>();
			for (TerminalDetails t : terminalDetails) {
				String did = t.getDeviceId();
				dIdSet.add(did.toString());

			}
			model.addAttribute("devIdList", dIdSet);
			model.addAttribute("tidList", tidSet);
			return TEMPLATE_NONMERCHANT;
		} 
		else
		{
			model.addAttribute("paginationBean", paginationBean);
			/* model.addAttribute("devIdList", dIdSet); */
			model.addAttribute("responseData", "No Record found");
			return TEMPLATE_NONMERCHANT;
		}

	}

	// transactionService.searchMobileUserTransactions(i, paginationBean);

	// model.addAttribute("tidSet", tidSet);
	// model.addAttribute("paginationBean", paginationBean);

	/*
	 * return TEMPLATE_MERCHANT; }
	 */
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String displayTransactionSearchByTid(
			final Model model,
			final java.security.Principal principal,
			/*@RequestParam("tid") final String tid,*/
			@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
			/*@RequestParam("status") String status,
*/
			/*
			 * @RequestParam(value="false") final String fromDate,
			 * 
			 * @RequestParam(value="false") final String toDate,
			 * 
			 * @RequestParam(value="false") final String tid,
			 * 
			 * @RequestParam(value="false") final String status,
			 */

			/* @RequestParam String devId, */
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": & :"+tid+":"+status);

		String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list",
				"nonmerchantweb/transaction/transactionList",
				Module.TRANSACTION_WEB, "nonmerchantweb/transaction");
		logger.info(" in search:"+currentMerchant.getMid().getMid());
		// logger.info("the merchant obj based on currently logged in user is: "
		// + currentMerchant);
		/*
		 * PageBean pageBean = new PageBean("transactions list",
		 * "merchantweb/transaction/transactionList", Module.TRANSACTION_WEB,
		 * "merchantweb/transaction/sideMenuTransaction");
		 * model.addAttribute("pageBean", pageBean);
		 */
		/*
		 * if(!(fromDate.equals(null)||toDate.equals(null)||fromDate.equals("")||
		 * toDate.equals(""))){
		 */

		// new changes//
		// logger.info("test Data:");
		String dat = null;
		String dat1 = null;

		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": & :"+tid+": & :"+status);
		if (!(fromDate == null || fromDate.equals(""))
				&& !(toDate == null || toDate.equals(""))) {

			/*
			 * Date dt = new Date();
			 * 
			 * SimpleDateFormat dateFormat = new SimpleDateFormat("MMdd"); dat =
			 * dateFormat.format(dt); Date dt1 = new Date(); SimpleDateFormat
			 * dateFormat1 = new SimpleDateFormat("MMdd"); dat1 =
			 * dateFormat1.format(dt1); logger.info("date1:" + dat);
			 * logger.info("date1" + dat1);
			 * 
			 * } else {
			 */
			// logger.info(" DD else data:" + fromDate);
			/* logger.info("DD else data: " + date1); */
			/*
			 * SimpleDateFormat dateFormat1 = new
			 * SimpleDateFormat("yyyy-MM-dd"); try { fromDate =
			 * dateFormat1.format(new
			 * SimpleDateFormat("dd/MM/yyyy").parse(fromDate)); } catch
			 * (ParseException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); } logger.info("check from date:" +
			 * fromDate);
			 */

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy")
						.parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			/*
			 * dat = fromDate; String sub[] = dat.split("/"); dat = sub[0] +
			 * sub[1];
			 */
			// logger.info("check from date:" + fromDate);
			// logger.info("check from date:" + dat);

			// logger.info("DD sub : " + dat);
			// System.out.println("DD sub : " + dat);
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
						.parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			/*
			 * dat1 = toDate; String sub1[] = dat1.split("/"); dat1 = sub1[0] +
			 * sub1[1];
			 */
			// System.out.println("dat1:" + dat1);
		}
		/*
		 * 2015-10-22 try { fromDate = new SimpleDateFormat("MMdd").format(new
		 * SimpleDateFormat("MMdd").parse(fromDate)); toDate = new
		 * SimpleDateFormat("MMdd").format(new
		 * SimpleDateFormat("MMdd").parse(toDate)); } catch (ParseException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * }
		 */

		/*PageBean pageBean = new PageBean("transactions list",
				"nonmerchantweb/transaction/transactionList",
				Module.TRANSACTION_WEB,
				"nonmerchantweb/transaction/sideMenuTransaction");*/
		/*PageBean pageBean = new PageBean("transactions list",
				"nonmerchantweb/transaction/transactionList",
				Module.TRANSACTION_WEB,
				null);*/
		
		model.addAttribute("pageBean", pageBean);
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");
		/*transactionService.searchForSettlement(dat, dat1, tid, status,
				paginationBean, currentMerchant);*/
		transactionService.searchnonmerchantForSettlement(dat, dat1,paginationBean, currentMerchant);

		// logger.info("from date:" + dat);
		// logger.info("to date:" + dat1);
		model.addAttribute("fromDate", fromDate);

		model.addAttribute("toDate", toDate);
		/*model.addAttribute("tid", tid);

		model.addAttribute("status", status);*/
		/*
		 * logger.info("test transaction :::" + devId);
		 * model.addAttribute("devId", devId);
		 */
		// logger.info("test transaction :::"+ paginationBean.getItemList() ==
		// null);
		if (paginationBean.getItemList().isEmpty()
				|| paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		// logger.info("test data "+ paginationBean.getItemList());
		for (ForSettlement forSettlement : paginationBean.getItemList()) {
			/*TerminalDetails terminalDetails = transactionService
					.getTerminalDetailsByTid(forSettlement.getTid().toString());
			if (terminalDetails != null) {
				// logger.info("terminal details contact Name:" +
				// terminalDetails.getContactName());
				forSettlement.setMerchantName(terminalDetails.getContactName());
			}*/
			if (forSettlement.getAmount() != null) {
				double amount = 0;
				amount = Double.parseDouble(forSettlement.getAmount()) / 100;
				// forSettlement.setAmount(amount+"0");
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				// System.out.println(" Amount :"+output);
				forSettlement.setAmount(output);
			}
			if (forSettlement.getStatus().equals("CT")) {
				forSettlement.setStatus("CASH SALE");
			}
			if (forSettlement.getStatus().equals("CV"))
			{
				forSettlement.setStatus("CASH CANCELLED");
			}
			
			if (forSettlement.getDate() != null
					&& forSettlement.getTime() != null) {
				try {
					
					String sd = forSettlement.getTimeStamp();
					logger.info("date in search non mercant trans: "+sd);
					String rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd")
									.parse(sd));
					String rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss")
									.parse(forSettlement.getTime()));
					forSettlement.setDate(rd);
					forSettlement.setTime(rt);
				} catch (ParseException e) {
				}

			}
		}
		/*
		 * String myName = principal.getName();
		 * logger.info("currently logged in as " + myName); Merchant
		 * currentMerchant = merchantService.loadMerchant(myName);
		 */
		/*List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			tidSet.add(mtid.toString());
		}
		
		 * if (tid.equalsIgnoreCase(mtid)) { midTransationList.add(t); }
		 
		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}
		model.addAttribute("devIdList", dIdSet);

		model.addAttribute("tidList", tidSet);*/
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_NONMERCHANT;

	}

	@RequestMapping(value = { "/details/{id}" }, method = RequestMethod.GET)
	public String displayTransactionDetails(final Model model,
			@PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {
		// logger.info("in Transaction details");
		// PageBean pageBean = new PageBean("Transactions Details",
		// "merchantweb/transaction/receipt",
		// Module.TRANSACTION_WEB, null);
		
		
		
		/*PageBean pageBean = new PageBean("Transactions Details",
				"merchantweb/transaction/cashreceipt", null);*/
		
		 PageBean pageBean = new PageBean("Transactions Details",
				  "merchantweb/transaction/CashReceiptNew", null);
		logger.info("Transaction Id :" + id);
		/*TransactionRequest trRequest = transactionService
				.loadTransactionRequest(id);
		TransactionResponse trResponse = transactionService
				.loadTransactionResponse(id);*/

		Merchant merchant = merchantService.loadMerchant(principal.getName());

		logger.info("Mid" + ":" + merchant.getMid().getMid() + "MerchantName"
				+ ":" + merchant.getBusinessName() + ":"
				+ "Merchant Receipt logged by" + ":" + principal.getName()
				+ ":");
		ForSettlement settle = transactionService.getForSettlement(id);
		//Receipt a = transactionService.getReceiptSignature(id);

		DataTransferObject dt = new DataTransferObject();
		/*if (a != null) {
			if (a.getSignature() != null) {
				String signdata = new String(a.getSignature());
				// System.out.println(" String sign :"+signdata);
				dt.setSign(signdata);
			}
		}*/

		String txn = settle.getStatus();
		// System.out.println("Processing Code : "+txn);
		if (txn.equals("CT") ) {
			dt.setTxnType("CASH SALE");

		} else {
			dt.setTxnType("CASH CANCELLED");

		}

		dt.setMerchantName(merchant.getBusinessName());
		dt.setMerchantAddr1(merchant.getBusinessAddress1());
		dt.setMerchantAddr2(merchant.getBusinessAddress2());
		dt.setMerchantCity(merchant.getCity());
		dt.setMerchantPostCode(merchant.getPostcode());
		dt.setMerchantContNo(merchant.getBusinessContactNumber());
		dt.setMerchantState(merchant.getState());

		dt.setLatitude(settle.getLatitude());
		dt.setLongitude(settle.getLongitude());
		// new changes for receipt
		//dt.setBatchNo(settle.getBatchNo());
		//logger.info("get batchno:" + dt.getBatchNo());

		if (settle.getInvoiceId() != null) {

			dt.setRefNo(settle.getInvoiceId());
			logger.info("invoice id:" + dt.getRefNo());
		} else {
			dt.setRefNo("");
		}

		dt.setTid(settle.getTid());
		dt.setMid(settle.getMid());
		dt.setAmount(settle.getAmount());
		dt.setAdditionAmount(settle.getAdditionAmount());
		// String rrn=HexatoAscii.hexaToAscii(trResponse.getRrn(), true);
		// dt.setRrn(rrn);
		//dt.setAid(trRequest.getAid());
		dt.setStan(settle.getStan());
		/*dt.setMaskedPan(trRequest.getMaskedPan());
		if (trRequest.getAid() != null && trRequest.getCardHolderName() != null) {
			// dt.setCardType(CardType.getCardType(trRequest.getAid()));
			dt.setCardHolderName(trRequest.getCardHolderName());
		}
		dt.setDate(trResponse.getLocalDate());
		dt.setTime(trResponse.getLocalTime());*/
		// String
		// resposecode=HexatoAscii.hexaToAscii(trResponse.getAidResponse(),
		// true);
		// dt.setResponseCode(resposecode);
		String st = settle.getTime();
		// String sd=trResponse.getLocalDate()+new
		// SimpleDateFormat("y").format(new java.util.Date());
		String sd = settle.getTimeStamp();
		try {

			String rd = new SimpleDateFormat("dd-MM-yyyy")
					.format(new SimpleDateFormat("yyyy-MM-dd").parse(sd));
			String rt = new SimpleDateFormat("HH:mm:ss")
					.format(new SimpleDateFormat("HHmmss").parse(st));
			dt.setDate(rd);
			dt.setTime(rt);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//String cardNum = null;
		/*if (trRequest.getMaskedPan() == null
				|| trRequest.getMaskedPan().length() < 4) {
			cardNum = trRequest.getMaskedPan();
		} else {
			cardNum = trRequest.getMaskedPan().substring(
					trRequest.getMaskedPan().length() - 4);
		}*/
		//String finalNum = String.format("XXXX %s", cardNum);
		//dt.setCardNo(finalNum);
		double amount = 0;
		logger.info("Amount For receipt :" + settle.getAmount());
		if (settle.getAmount() != null) {
			amount = Double.parseDouble(settle.getAmount()) / 100;
		}
		double tips = 0;
		double total = amount;
		if (settle.getAdditionAmount() != null) {
			tips = Double.parseDouble(settle.getAdditionAmount()) / 100;
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
		/*dt.setCardHolderName(trRequest.getCardHolderName());
		dt.setAid(trRequest.getAid());
		if (trRequest.getAid() != null) {
			dt.setCardType(CardType.getCardType(trRequest.getAid()));
		} else {
			dt.setCardType("");
		}*/
		//dt.setBatchNo(trRequest.getBatchNo());
		dt.setTraceNo(settle.getStan());
		/*dt.setRrn(hexaToAscii(trResponse.getRrn(), true));
		dt.setTC(trRequest.getStan() + trRequest.getTid());
		dt.setAid(trResponse.getAidResponse());
		if (settle.getBatchNo() != null) {
			dt.setBatchNo(settle.getBatchNo());
		} else {
			dt.setBatchNo("");
		}*/
		if (settle.getInvoiceId() != null) {
			dt.setInvoiceNo(settle.getInvoiceId());
		} else {
			dt.setInvoiceNo("");
		}

		//String resposecode = hexaToAscii(trResponse.getAidResponse(), true);

		//dt.setApprCode(resposecode);
		/*
		 * Merchant merchant = merchantService.loadMerchantByPk(id); Transaction
		 * transaction = transactionService.loadTransactionByPk(id); MobileUser
		 * mobileUser = mobileUserService.loadMobileUserByPk(id);
		 * model.addAttribute("transaction", transaction);
		 * model.addAttribute("mobileUser", mobileUser);
		 * model.addAttribute("merchant", merchant);
		 */
		// model.addAttribute("dto", dt);
		request.setAttribute("dto", dt);
		model.addAttribute("pageBean", pageBean);
		//return "merchantweb/transaction/cashreceipt";
		return "merchantweb/transaction/CashReceiptNew";
		// return "redirect:/transactionweb/list/1";
	}

	public static String hexaToAscii(String s, boolean toString) {

		String retString = "";
		String tempString = "";
		int offset = 0;
		if (toString) {
			for (int i = 0; i < s.length() / 2; i++) {

				tempString = s.substring(offset, offset + 2);
				retString += tempString.equalsIgnoreCase("1c") ? "[1C]"
						: decodeHexString(tempString);
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

	@RequestMapping(value = "/export", method = RequestMethod.POST)
	public ModelAndView getExport(
			final Model model,
			final java.security.Principal principal,
			@RequestParam("tid") final String tid,
			@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
			@RequestParam("status") String status,
			@RequestParam("devId") String devId,
			@RequestParam("export") String export,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": & :"+tid+":"+status);
		// PageBean pageBean=new PageBean("Transactions Details",
		// "transaction/receipt", null);
		String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("the merchant obj based on currently logged in user is: "
				+ currentMerchant);
		/*
		 * PageBean pageBean = new PageBean("transactions list",
		 * "merchantweb/transaction/transactionList", Module.TRANSACTION_WEB,
		 * "merchantweb/transaction/sideMenuTransaction");
		 * model.addAttribute("pageBean", pageBean);
		 */
		/*
		 * if(!(fromDate.equals(null)||toDate.equals(null)||fromDate.equals("")||
		 * toDate.equals(""))){
		 */

		// new changes//
		// logger.info("test Data:");
		String dat = null;
		String dat1 = null;

		// logger.info("fromDate & toDate & Tid :"+fromDate+": & :"+toDate+": & :"+tid+": & :"+status);
		if (!(fromDate == null || fromDate.equals(""))
				&& !(toDate == null || toDate.equals(""))) {

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat = dateFormat.format(new SimpleDateFormat("dd/MM/yyyy")
						.parse(fromDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			/*
			 * dat = fromDate; String sub[] = dat.split("/"); dat = sub[0] +
			 * sub[1];
			 */
			logger.info("check from date:" + fromDate);
			logger.info("check from date:" + dat);

			// logger.info("DD sub : " + dat);
			// System.out.println("DD sub : " + dat);
			SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			try {
				dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
						.parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}
			/*
			 * dat1 = toDate; String sub1[] = dat1.split("/"); dat1 = sub1[0] +
			 * sub1[1];
			 */
			System.out.println("dat1:" + dat1);
		}
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);
		// logger.info("test transaction :::");
		transactionService.searchForSettlement(dat, dat1, tid, status,
				paginationBean, currentMerchant);

		/*
		 * model.addAttribute("fromDate", fromDate);
		 * 
		 * model.addAttribute("toDate", toDate);
		 * 
		 * model.addAttribute("tid", tid);
		 * 
		 * model.addAttribute("status", status);
		 */
		/* logger.info("test transaction :::" + devId); */
		// model.addAttribute("devId", devId);
		// logger.info("test transaction :::"+ paginationBean.getItemList() ==
		// null);
		if (paginationBean.getItemList().isEmpty()
				|| paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		for (ForSettlement forSettlement : paginationBean.getItemList()) {
			// logger.info("tid details:" + forSettlement.getTid().toString());
			TerminalDetails terminalDetails = transactionService
					.getTerminalDetailsByTid(forSettlement.getTid().toString());
			if (terminalDetails != null) {
				// logger.info("terminal details contact Name:" +
				// terminalDetails.getContactName());
				if (terminalDetails.getContactName() != null) {
					forSettlement.setMerchantName(terminalDetails
							.getContactName().toUpperCase());
				} else {
					forSettlement.setMerchantName("");
				}
			}
			/*
			 * logger.info("test data "+ paginationBean.getItemList());
			 * for(ForSettlement forSettlement:paginationBean.getItemList()){
			 */
			if (forSettlement.getAmount() != null) {
				double amount = 0;
				amount = Double.parseDouble(forSettlement.getAmount()) / 100;
				// forSettlement.setAmount(amount+"0");
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				// System.out.println(" Amount :"+output);
				forSettlement.setAmount(output);
			}
			if (forSettlement.getStatus().equals("CT")) {
				forSettlement.setStatus("CASH SALE");

			}
			if (forSettlement.getStatus().equals("CV"))
			{
				forSettlement.setStatus("CASH CANCELLED");
			}
			/*if (forSettlement.getStatus().equals("P")) {
				forSettlement.setStatus("PENDING");
			}*/
			/*if (forSettlement.getStatus().equals("A")) {
				forSettlement.setStatus("PENDING");
			}
			if (forSettlement.getStatus().equals("C")) {
				forSettlement.setStatus("CANCELLED");
			}
			if (forSettlement.getStatus().equals("R")) {
				forSettlement.setStatus("REVERSAL");
			}*/
			if (forSettlement.getDate() != null
					&& forSettlement.getTime() != null) {
				try {
					// String sd=forSettlement.getDate()+new
					// SimpleDateFormat("y").format(new java.util.Date());
					String sd = forSettlement.getTimeStamp();
					String rd = new SimpleDateFormat("dd-MM-yyyy")
							.format(new SimpleDateFormat("yyyy-MM-dd")
									.parse(sd));
					String rt = new SimpleDateFormat("HH:mm:ss")
							.format(new SimpleDateFormat("HHmmss")
									.parse(forSettlement.getTime()));
					forSettlement.setDate(rd);
					forSettlement.setTime(rt);
				} catch (ParseException e) {
				}

			}
		}
		/*
		 * String myName = principal.getName();
		 * logger.info("currently logged in as " + myName); Merchant
		 * currentMerchant = merchantService.loadMerchant(myName);
		 */
		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMid());

		Set<String> tidSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String mtid = t.getTid();
			// tidSet.add(mtid.toString());
		}
		/*
		 * if (tid.equalsIgnoreCase(mtid)) { midTransationList.add(t); }
		 */
		Set<String> dIdSet = new HashSet<String>();
		for (TerminalDetails t : terminalDetails) {
			String did = t.getDeviceId();
			dIdSet.add(did.toString());
		}
		/*
		 * model.addAttribute("devIdList", dIdSet);
		 * 
		 * model.addAttribute("tidList", tidSet);
		 * model.addAttribute("paginationBean", paginationBean);
		 * 
		 * return TEMPLATE_MERCHANT;
		 */

		List<ForSettlement> list1 = paginationBean.getItemList();

		// System.out.println("display list:" + list1);
		// System.out.println("Export Type:" + export);

		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListExcel1", "txnList", list1);
		} else {
			return new ModelAndView("txnListPdf", "txnList", list1);
		}

	}

}
