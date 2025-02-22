package com.mobiversa.payment.controller;

import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.PreAuthorization;
import com.mobiversa.common.bo.Receipt;
import com.mobiversa.common.bo.TransactionRequest;
import com.mobiversa.common.bo.TransactionResponse;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.DataTransferObject;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.CardType;

@Controller
@RequestMapping(value = PreAuthTxnController.URL_BASE)
public class PreAuthTxnController  extends BaseController {
	
	@Autowired
	private TransactionService transactionService;
	
	public static final String URL_BASE = "/preauthtxn";
	
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		return "redirect:" + URL_BASE + "/list/1";
	}
	
	
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String displayTransactionSummary(final Model model,

			@RequestParam(required = false, defaultValue = "1") final int currPage,final java.security.Principal principal) {
		//logger.info("about to list all  transaction");
		PageBean pageBean = new PageBean("transactions list", "preauth/transactionList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Admin Login Person:" + principal.getName());
		PaginationBean<PreAuthorization> paginationBean = new PaginationBean<PreAuthorization>();
		paginationBean.setCurrPage(currPage);
		transactionService.listPreAuthTransaction(paginationBean, null, null,null);
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
	public String displayTransactionList(final Model model, @RequestParam final String date,
			final java.security.Principal principal,
			@RequestParam final String date1,@RequestParam  String txnType, 
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		//logger.info("about to list all  Transactiona");
		PageBean pageBean = null;
		logger.info("SEARCH PreAuth Summary :" + principal.getName()+" "+txnType);
		 pageBean = new PageBean("transactions list", "preauth/transactionList", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
		if(txnType.equals("ALL") || txnType=="ALL"){
			txnType=null;
		}
		/*else{
			 pageBean = new PageBean("transactions list", "preauth/EZYAUTH/transactionList", Module.TRANSACTION,
						"transaction/sideMenuTransaction");
		}*/
		model.addAttribute("pageBean", pageBean);

		PaginationBean<PreAuthorization> paginationBean = new PaginationBean<PreAuthorization>();
		paginationBean.setCurrPage(currPage);
		
		transactionService.listPreAuthTransaction(paginationBean, date, date1,txnType);
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;

	}
	
	@RequestMapping(value = { "/export" }, method = RequestMethod.GET)
	public ModelAndView displayExportTransactionList(final Model model,
			@RequestParam final String date,
			final java.security.Principal principal,
			@RequestParam final String date1,
			@RequestParam  String txnType, 
			@RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam(required = false) String export) {
		
		logger.info("SEARCH PreAuth Summary :" + principal.getName()+" "+txnType);
		 
		if(txnType.equals("ALL") || txnType=="ALL"){
			txnType=null;
		}


		PaginationBean<PreAuthorization> paginationBean = new PaginationBean<PreAuthorization>();
		paginationBean.setCurrPage(currPage);
		
		transactionService.listPreAuthTransaction(paginationBean, date, date1,txnType);
		
		if (paginationBean.getItemList().isEmpty()
				|| paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}
		
		List<PreAuthorization> list = paginationBean.getItemList();
		
		if (!(export.equals("PDF"))) {

			return new ModelAndView("preauthTxnExcel", "txnList", list);

		} else {
			return new ModelAndView("preauthTxnPdf", "txnList", list);
		}

	}
	
	@RequestMapping(value = { "/merchanttxn" }, method = RequestMethod.GET)
	public String displayTransactionmerchDetails(final Model model, 
			@RequestParam final String merchantName,java.security.Principal principal,
			@RequestParam final String date/*,@RequestParam final String date1*/,@RequestParam final String city,
			@RequestParam String txnType,
			/*@PathVariable String City,*/

	@RequestParam(required = false, defaultValue = "1") final int currPage){//,@PathVariable final String merchant) {
	
		logger.info("about to list all  Transactions : "+txnType);
		
		PageBean pageBean = new PageBean("Transactions Details", "preauth/transactionsView", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<PreAuthorization> paginationBean = new PaginationBean<PreAuthorization>();
		paginationBean.setCurrPage(currPage);
		transactionService.loadPreAuthByName(paginationBean,merchantName,date,txnType); /*merchantName,City*/
		model.addAttribute("paginationBean",paginationBean);
		model.addAttribute("namedd", merchantName);
		model.addAttribute("nameyy", city);
		return TEMPLATE_DEFAULT;
	}
	
	
	@RequestMapping(value = { "/details/{id}" }, method = RequestMethod.GET)
	public String displayTransactionDetails(final Model model, @PathVariable final String id,
			HttpServletRequest request, HttpServletResponse response, Principal principal) {
		logger.info("PreAuth and EzyAuth Receipt details Receipt " + id);

		
		//PageBean pageBean=new PageBean("Transactions Details", "merchantweb/transaction/CardReceiptNew", null);
		 
		PageBean pageBean = new PageBean("Transactions Details", "preauth/receipt_v0.2", null);
		
		TransactionRequest trRequest = transactionService.loadTransactionRequest(id);
		TransactionResponse trResponse = transactionService.loadTransactionResponse(id);

		Merchant merchant = transactionService.loadMerchantDetails(trRequest.getMid());
		// Merchant merchant = mid.getMerchant();
		PreAuthorization settle = transactionService.getPreAuthTxn(id);
		Receipt a = new Receipt();
		a = transactionService.getReceiptSignature(id);

		DataTransferObject dt = new DataTransferObject();
		if (a != null) {
			if (a.getSignature() != null) {
				String signdata = new String(a.getSignature());
				dt.setSign(signdata);
			}
		}
		logger.info("txnType: " + settle.getTxnType());
		if (settle.getTxnType() == null) {
			settle.setTxnType("PREAUTH");
		} else if(settle.getTxnType().equals("MOTO")) {
			settle.setTxnType("EZYAUTH");
		}else {
			settle.setTxnType("EZYREC");
		}
		String txn = settle.getStatus();
		if (settle.getTxnType().equals("EZYAUTH")) {

			if (txn.equals("D") || txn.equals("E")) {
				dt.setTxnType("EZYAUTH");

			} else if (txn.equals("P") || txn.equals("A") || txn.equals("S")) {
				dt.setTxnType("EZYAUTH SALE");

			} else {
				dt.setTxnType("EZYAUTH CANCEL");

			}
		}else if (settle.getTxnType().equals("EZYREC")) {

			if (txn.equals("D") || txn.equals("E")) {
				dt.setTxnType("EZYREC");

			} else if (txn.equals("P") || txn.equals("A") || txn.equals("S")) {
				dt.setTxnType("EZYREC SALE");

			} else {
				dt.setTxnType("EZYREC CANCEL");

			}
		}
		else {
			if (txn.equals("D") || txn.equals("E")) {
				dt.setTxnType("PRE-AUTHORIZATION");

			} else if (txn.equals("P") || txn.equals("A") || txn.equals("S")) {
				dt.setTxnType("PREAUTH SALE");

			} else {
				dt.setTxnType("PREAUTH CANCEL");

			}
		}

		logger.info("txnType: "+dt.getTxnType());
		dt.setMerchantName(merchant.getBusinessName());
		dt.setMerchantAddr1(merchant.getBusinessAddress1());
		dt.setMerchantAddr2(merchant.getBusinessAddress2());
		dt.setMerchantCity(merchant.getCity());
		dt.setMerchantPostCode(merchant.getPostcode());
		dt.setMerchantContNo(merchant.getBusinessContactNumber());
		dt.setMerchantState(merchant.getState());

		dt.setLatitude(trRequest.getLatitude());
		dt.setLongitude(trRequest.getLongitude());
//new changes for preauth receipt 		
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
		//dt.setAmount(trRequest.getAmount());
		dt.setAmount(settle.getAmount());
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

//new changes for receipt 23082017

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
//end
		/*dt.setDate(trResponse.getLocalDate());
		dt.setTime(trResponse.getLocalTime());*/
		// String resposecode=HexatoAscii.hexaToAscii(trResponse.getAidResponse(),
		// true);
		// dt.setResponseCode(resposecode);
		String st = trResponse.getLocalTime();
		String sd = trResponse.getLocalDate() + new SimpleDateFormat("y").format(new java.util.Date());
		try {

			/*String rd = new SimpleDateFormat("dd-MMM-y").format(new SimpleDateFormat("MMddy").parse(sd));
			String rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(st));*/
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
//		if (trRequest.getAmount() != null) {
//			amount = Double.parseDouble(trRequest.getAmount()) / 100;
//		}
		
		if (settle.getAmount() != null) {
			amount = Double.parseDouble(settle.getAmount()) / 100;
		}
		double tips = 0;
		double total = amount;
		/*if (trRequest.getAdditionalAmount() != null) {
			tips = Double.parseDouble(trRequest.getAdditionalAmount()) / 100;
			amount = amount - tips;
			total = amount + tips;

		}*/
		
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
		dt.setTotal(output);
		//dt.setTotal(output2);
		dt.setCardHolderName(trRequest.getCardHolderName());
		dt.setAid(trRequest.getAid());
		// dt.setCardType(CardType.getCardType(trRequest.getAid()));
		// dt.setBatchNo(trRequest.getBatchNo());
		dt.setTraceNo(trRequest.getStan());
		dt.setRrn(hexaToAscii(trResponse.getRrn(), true));
		dt.setTC(trRequest.getStan() + trRequest.getTid());
		dt.setAid(trResponse.getAidResponse());
		/*
		 * if(settle!=null){ dt.setBatchNo(settle.getBatchNo()); }
		 */
		String resposecode = hexaToAscii(trResponse.getAidResponse(), true);

		dt.setApprCode(resposecode);
		request.setAttribute("dto", dt);
		model.addAttribute("pageBean", pageBean);
		// return "preauth/receipt";
		// return "merchantweb/transaction/CardReceiptNew";
		return "preauth/receipt_v0.2";

		// return "redirect:/transactionweb/list/1";
	}
	
	@RequestMapping(value = { "/ezyauthlist/{currPage}" }, method = RequestMethod.GET)
	public String displayEzyAuthTrxSummary(final Model model,

	@PathVariable final int currPage,final java.security.Principal principal) {
		//logger.info("about to list all  transaction");
		PageBean pageBean = new PageBean("transactions list", "preauth/EZYAUTH/transactionList", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info("Admin Login Person:" + principal.getName());
		logger.info("moto_preauth transaction:  ");
		PaginationBean<PreAuthorization> paginationBean = new PaginationBean<PreAuthorization>();
		paginationBean.setCurrPage(currPage);
		transactionService.listPreAuthTransaction(paginationBean, null, null,null);
		if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null || paginationBean.getItemList().size() == 0 )
		{
			model.addAttribute("responseData", "No Records found"); //table response
		}else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}
	
	
	
	@RequestMapping(value = { "/merchant_ezyauthtxn" }, method = RequestMethod.GET)
	public String displayEzyauthMerchDetails(final Model model, @RequestParam final String merchantName,java.security.Principal principal,
			@RequestParam final String date/*,@RequestParam final String date1*/,@RequestParam final String city,
			@RequestParam final String txnType,

	@RequestParam(required = false, defaultValue = "1") final int currPage){//,@PathVariable final String merchant) {
	
		//logger.info("about to list all  Transactions");
		PageBean pageBean = new PageBean("Transactions Details", "preauth/EZYAUTH/transactionsView", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<PreAuthorization> paginationBean = new PaginationBean<PreAuthorization>();
		paginationBean.setCurrPage(currPage);
		transactionService.loadPreAuthByName(paginationBean,merchantName,date,txnType); /*merchantName,City*/
		model.addAttribute("paginationBean",paginationBean);
		model.addAttribute("namedd", merchantName);
		model.addAttribute("nameyy", city);
		return TEMPLATE_DEFAULT;
	}
	
	
	/*@RequestMapping(value = { "/EzyAuth_details/{id}" }, method = RequestMethod.GET)
	public String displayEzyauthDetails(final Model model, @PathVariable final String id,HttpServletRequest request,HttpServletResponse response,Principal principal) {
		logger.info("in Transaction details Receipt");

		//PageBean pageBean=new PageBean("Transactions Details", "preauth/receipt", null);
		PageBean pageBean=new PageBean("Transactions Details", "merchantweb/transaction/CardReceiptNew", null);
		TransactionRequest trRequest=	transactionService.loadTransactionRequest(id);
		TransactionResponse trResponse= transactionService.loadTransactionResponse(id);
		
		Merchant merchant = transactionService.loadMerchantDetails(trRequest.getMid());
		//Merchant merchant = mid.getMerchant();
		PreAuthorization settle= transactionService.getPreAuthTxn(id);
		Receipt a = new Receipt();
		a = transactionService.getReceiptSignature(id);
		
		 DataTransferObject dt=new DataTransferObject();
		 if(a != null){
			 if(a.getSignature() != null){
				 String signdata = new String(a.getSignature());
				 dt.setSign(signdata);
			 }
		 }
		 String txn = settle.getStatus();
		 
		 if(txn.equals("D") || txn.equals("E")){
			 dt.setTxnType("EZY-AUTHORIZATION");
			 
		 }else if(txn.equals("P") || txn.equals("A") || txn.equals("S")){
			 dt.setTxnType("EZY-AUTH SALE");
			 
		 }else{
			 dt.setTxnType("EZY-AUTH CANCEL");
			 
		 }
		 
		dt.setMerchantName(merchant.getBusinessName());
		dt.setMerchantAddr1(merchant.getBusinessAddress1());
		dt.setMerchantAddr2(merchant.getBusinessAddress2());
		dt.setMerchantCity(merchant.getCity());
		dt.setMerchantPostCode(merchant.getPostcode());
		dt.setMerchantContNo(merchant.getBusinessContactNumber());
		
		dt.setLatitude(trRequest.getLatitude());
		dt.setLongitude(trRequest.getLongitude());
//new changes for preauth receipt 		
		dt.setBatchNo(settle.getBatchNo());
		logger.info("get batchno:" + dt.getBatchNo());
		
		
		if(settle.getInvoiceId()!= null)
{
			
			dt.setRefNo(settle.getInvoiceId());
		logger.info("invoice id:" + dt.getRefNo());
}else
{
dt.setRefNo("");
}

			
		 //dt.setTxnType(trRequest.getTxnType());
		 dt.setTid(trRequest.getTid());
		 dt.setMid(trRequest.getMid());
		 dt.setAmount(trRequest.getAmount());
		 dt.setAdditionAmount(trRequest.getAdditionalAmount());
			//String rrn=HexatoAscii.hexaToAscii(trResponse.getRrn(), true);
			//dt.setRrn(rrn);
			dt.setAid(trRequest.getAid());
			dt.setStan(trRequest.getStan());
			dt.setMaskedPan(trRequest.getMaskedPan());
			if(trRequest.getAid()!=null&&trRequest.getCardHolderName()!=null){
				//dt.setCardType(CardType.getCardType(trRequest.getAid()));
				dt.setCardHolderName(trRequest.getCardHolderName());
			}
			
//new changes for receipt 23082017
			
			if(trRequest.getCardHolderName()!= null)
			{
				dt.setCardHolderName(trRequest.getCardHolderName());
				
			}else
			{
				dt.setCardHolderName("");
			}
			if(trRequest.getCardScheme() != null && trRequest.getCardType() != null){
				
				dt.setCardType(trRequest.getCardScheme()+" "+trRequest.getCardType());
				
				logger.info("Card Type from Txn Request Card Scheme and Card Type : "+dt.getCardType());
			
			
	}else if(trRequest.getApplicationLabel()!=null){
		dt.setCardType(trRequest.getApplicationLabel());
  }else if(trRequest.getApplicationLabel() == null && trRequest.getAid() != null){
	  dt.setCardType(CardType.getCardType(trRequest.getAid()));
  //mJson.setCardHolderName(trRequest.getCardHolderName());
  }else{
	  dt.setCardType(" ");
  }
//end
			dt.setDate(trResponse.getLocalDate());
			dt.setTime(trResponse.getLocalTime());
				//String resposecode=HexatoAscii.hexaToAscii(trResponse.getAidResponse(), true);
				//dt.setResponseCode(resposecode);
				String st=trResponse.getLocalTime();
				String sd=trResponse.getLocalDate()+new SimpleDateFormat("y").format(new java.util.Date());
				try {

				    String rd = new SimpleDateFormat("dd-MMM-y").format(new SimpleDateFormat("MMddy").parse(sd));
				    String rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(st));
				    dt.setDate(rd);
				    dt.setTime(rt);
				} catch (ParseException e) {
				    e.printStackTrace();
				}
				String cardNum=null;
				if (trRequest.getMaskedPan() == null || trRequest.getMaskedPan().length() < 4) {
					cardNum = trRequest.getMaskedPan();
				} else {
					cardNum = trRequest.getMaskedPan().substring(trRequest.getMaskedPan().length() - 4);
				}
				String finalNum=String.format("XXXX %s", cardNum);
				dt.setCardNo(finalNum);
				double amount=0;
				if(trRequest.getAmount()!=null){
				amount=Double.parseDouble(trRequest.getAmount())/100;
				}
				double tips=0;
				double total=amount;
				if(trRequest.getAdditionalAmount()!=null){
					tips=Double.parseDouble(trRequest.getAdditionalAmount())/100;
					amount=amount-tips;
					total=amount+tips;
					
				}
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				String output1 = myFormatter.format(tips);
				String output2 = myFormatter.format(total);
				//System.out.println(" Amount :"+output);
				//forSettlement.setAmount(output);
				dt.setAdditionAmount(output1);
				dt.setAmount(output);
				dt.setTotal(output2);
				dt.setCardHolderName(trRequest.getCardHolderName());
				dt.setAid(trRequest.getAid());
				//dt.setCardType(CardType.getCardType(trRequest.getAid()));
				//dt.setBatchNo(trRequest.getBatchNo());
				dt.setTraceNo(trRequest.getStan());
				dt.setRrn(hexaToAscii(trResponse.getRrn(), true));
				dt.setTC(trRequest.getStan()+trRequest.getTid());
				dt.setAid(trResponse.getAidResponse());
				if(settle!=null){
				dt.setBatchNo(settle.getBatchNo());
				}
				String resposecode=hexaToAscii(trResponse.getAidResponse(), true);
				
				dt.setApprCode(resposecode);
		request.setAttribute("dto", dt);
		model.addAttribute("pageBean", pageBean);
		//return "preauth/receipt";
		 return "merchantweb/transaction/CardReceiptNew";
		//return "redirect:/transactionweb/list/1";
	}
	
	*/
	
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



}
