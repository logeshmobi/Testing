package com.mobiversa.payment.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
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
import org.springframework.web.util.HtmlUtils;

import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileOTP;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.common.bo.TransactionRequest;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.CardType;
import com.mobiversa.payment.util.SendSMSMessage;
import com.postmark.java.MsgDto;
import com.sun.mail.iap.ConnectionException;

@Controller
@RequestMapping(value = AllTransactionController.URL_BASE)
public class AllTransactionController extends BaseController {
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private MerchantDao merchantDAO;
	
	
	@Autowired
	private MobileUserService mobileUserService;
	
	public static final String URL_BASE = "/transaction1";
	private static final Logger logger = Logger.getLogger(AllTransactionController.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage()
	{ 
		return "redirect:" + URL_BASE + "/list/1";
	}
	/*@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayTransactionSummary(final Model model,@PathVariable final int currPage,
			final java.security.Principal principal,
			@RequestParam(required = false)final String fromDate,
			@RequestParam(required = false) final String toDate,
			@RequestParam(required = false) final String status,
			@RequestParam(required = true, defaultValue = "1") final String  responseData) {
		logger.info("about to list all  transaction");
		
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionList1", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		logger.info(" All Transaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		
		paginationBean.setCurrPage(currPage);
				
		String fromDate1 = null;
		String toDate1 = null;
		
		String status1 = null;
		if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())) 
		{
			try {
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			} 
			catch (ParseException e) {e.printStackTrace();}
			
		}

		
		if(status != null){// || !(status.equals(""))){
			status1 = "";
		}else{
			status1 = status;
		}
		transactionService.listAllTransactionDetails(paginationBean,fromDate1,toDate1,status1);
		
		if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null)
		{
			model.addAttribute("responseData", "No Records found"); //table response
			
		}else {
			model.addAttribute("responseData", null);
		}
		
		//Start New Changes
		for(ForSettlement forSettlement:paginationBean.getItemList()){

			if(!forSettlement.getStatus().equals("CT") && !forSettlement.getStatus().equals("CV")){

              TerminalDetails terminalDetails = transactionService.getTerminalDetailsByTid(forSettlement.getTid().toString());
          
				if(terminalDetails != null )
				{
					//logger.info("Contact :"+terminalDetails.getContactName());	
					 forSettlement.setMerchantName(terminalDetails.getContactName()); 
				}
			}
			
			if(forSettlement.getMid()!=null)
			{
				//logger.info("All transactionlist mid "+forSettlement.getMid());
				Merchant merchant=transactionService.loadMerchantbymid(forSettlement.getMid());
				if(merchant!=null)
				{
				forSettlement.setNumOfSale(merchant.getBusinessName());
				//logger.info("All transactionlist mid "+forSettlement.getNumOfSale());
				}
			}
			if(forSettlement.getAmount()!=null){
				double amount=0;
				amount=Double.parseDouble(forSettlement.getAmount())/100;
				//forSettlement.setAmount(amount+"0");
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				//System.out.println(" Amount :"+output);
				forSettlement.setAmount(output);
			}
			if(forSettlement.getStatus().equals("S")){
				forSettlement.setStatus("SETTLED");
			}if(forSettlement.getStatus().equals("P")){
				forSettlement.setStatus("PENDING");
			}
			if(forSettlement.getStatus().equals("A")){
				forSettlement.setStatus("NOT SETTLED");
			}
			if(forSettlement.getStatus().equals("C")){
				forSettlement.setStatus("CANCELLED");
			}
			if(forSettlement.getStatus().equals("R")){
				forSettlement.setStatus("REVERSAL");
			}
			if(forSettlement.getStatus().equals("CT")){
				forSettlement.setStatus("CASH SALE");
			}if(forSettlement.getStatus().equals("CV")){
				forSettlement.setStatus("CASH CANCELLED");
			}
			//if(forSettlement.getDate()!=null&&forSettlement.getTime()!=null){
			if(forSettlement.getTime()!=null){
				try {
					
					String rd = new SimpleDateFormat("dd-MMM-yyyy").format(
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(forSettlement.getTimeStamp()));
					String rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
					//forSettlement.setDate(rd+"-"+forSettlement.getTxnYear());
					forSettlement.setDate(rd);
					forSettlement.setTime(rt);
				} catch (ParseException e) {}

			}	
			
			
		}	
		
		//End New Changes
		
		model.addAttribute("paginationBean", paginationBean);
		//model.addAttribute("responseData", responseData);

		return TEMPLATE_DEFAULT;
		//return "redirect:/transaction1/search1/";
	}
*/
	
	@RequestMapping(value = { "/list" }, method = RequestMethod.GET)
	public String displayTransactionSummary(final Model model,@RequestParam(required = false, defaultValue = "1") final int currPage,
			final java.security.Principal principal,
			@RequestParam(required = false)final String date,
			@RequestParam(required = false) final String date1,
			@RequestParam(required = false) final String status,
			@RequestParam(required = true, defaultValue = "1") final String  responseData) {
		logger.info("about to list all  transaction");
		
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionList1", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		logger.info(" All Transaction Summary:" + principal.getName());
		model.addAttribute("pageBean", pageBean);
		
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		
		paginationBean.setCurrPage(currPage);
			
		logger.info("all transaction list status: "+status);
		String fromDate1 = null;
		String toDate1 = null;
		
		String status1 = null;
		if ((date != null && date1 != null) && (!date.isEmpty() && !date1.isEmpty())) 
		{
			try {
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date1));
			} 
			catch (ParseException e) {e.printStackTrace();}
			
		}

		
		if(status != null){// || !(status.equals(""))){
			/*status1 = "";
		}else{*/
			status1 = status;
		}
		transactionService.listAllTransactionDetailsbyAdmin(paginationBean, fromDate1, toDate1,status1);
	
		//transactionService.listAllTransactionDetails(paginationBean,fromDate1,toDate1,status1);
		
		if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null)
		{
			model.addAttribute("responseData", "No Records found"); //table response
			
		}else {
			model.addAttribute("responseData", null);
		}
		
		//Start New Changes
		/*for(ForSettlement forSettlement:paginationBean.getItemList()){

			if(!forSettlement.getStatus().equals("CT") && !forSettlement.getStatus().equals("CV")){

              TerminalDetails terminalDetails = transactionService.getTerminalDetailsByTid(forSettlement.getTid().toString());
          
				if(terminalDetails != null )
				{
					//logger.info("Contact :"+terminalDetails.getContactName());	
					 forSettlement.setMerchantName(terminalDetails.getContactName()); 
				}
			}
			
			if(forSettlement.getMid()!=null)
			{
				//logger.info("All transactionlist mid "+forSettlement.getMid());
				Merchant merchant=transactionService.loadMerchantbymid(forSettlement.getMid());
				if(merchant!=null)
				{
				forSettlement.setNumOfSale(merchant.getBusinessName());
				//logger.info("All transactionlist mid "+forSettlement.getNumOfSale());
				}
			}
			if(forSettlement.getAmount()!=null){
				double amount=0;
				amount=Double.parseDouble(forSettlement.getAmount())/100;
				//forSettlement.setAmount(amount+"0");
				String pattern = "#,##0.00";
				DecimalFormat myFormatter = new DecimalFormat(pattern);
				String output = myFormatter.format(amount);
				//System.out.println(" Amount :"+output);
				forSettlement.setAmount(output);
			}
			if(forSettlement.getStatus().equals("S")){
				forSettlement.setStatus("SETTLED");
			}if(forSettlement.getStatus().equals("P")){
				forSettlement.setStatus("PENDING");
			}
			if(forSettlement.getStatus().equals("A")){
				forSettlement.setStatus("NOT SETTLED");
			}
			if(forSettlement.getStatus().equals("C")){
				forSettlement.setStatus("CANCELLED");
			}
			if(forSettlement.getStatus().equals("R")){
				forSettlement.setStatus("REVERSAL");
			}
			if(forSettlement.getStatus().equals("CT")){
				forSettlement.setStatus("CASH SALE");
			}if(forSettlement.getStatus().equals("CV")){
				forSettlement.setStatus("CASH CANCELLED");
			}
			//if(forSettlement.getDate()!=null&&forSettlement.getTime()!=null){
			if(forSettlement.getTime()!=null)
			{
				try {
					
					String rd = new SimpleDateFormat("dd-MMM-yyyy").format(
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(forSettlement.getTimeStamp()));
					String rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
					//forSettlement.setDate(rd+"-"+forSettlement.getTxnYear());
					forSettlement.setDate(rd);
					forSettlement.setTime(rt);
				} catch (ParseException e) {}

			}	
			
			
		}	*/
		
		//End New Changes
		
		model.addAttribute("paginationBean", paginationBean);
		//model.addAttribute("responseData", responseData);

		return TEMPLATE_DEFAULT;
		//return "redirect:/transaction1/search1/";
	}

	
	
	 @RequestMapping(value = { "/search1" }, method = RequestMethod.GET)
		public String displayAllTransactionList(final Model model, 
				@RequestParam(required = false ) final String date,
			@RequestParam(required = false) final String date1,
			@RequestParam(required = false) final String status,
			@RequestParam(required = false, defaultValue = "1") final int currPage){
		logger.info("all  Transaction in search controller");
		logger.info("inside search controler "+date+" "+date1);
		String dat = null;
		String dat1 = null;
		String status1 = null;
		
		
		String fromDate = HtmlUtils.htmlEscape(date);
		String toDate = HtmlUtils.htmlEscape(date1);
		
		if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty())) 
		{
			try 
			{
				dat = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
				dat1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
			logger.info("inside search controler "+dat+" "+dat1);
			} 
			catch (ParseException e) {
				
				e.printStackTrace();
			}
			
		}
		
		if(status != null){
			
			status1 = status;
		
		}
		PageBean pageBean = new PageBean("transactions list", "transaction/transactionList1", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		
			paginationBean.setCurrPage(currPage);
			
			transactionService.listAllTransactionDetailsbyAdmin(paginationBean, dat, dat1,status1);
			
			//transactionService.listAllTransactionDetails(paginationBean,fromDate1,toDate1,status1);
			
			//logger.info("test from date:" + dat);
			//logger.info("test from date:" + dat1);
			model.addAttribute("date", dat);
			model.addAttribute("date1", dat1);
			model.addAttribute("status", status1);
		/*transactionService.listSearchTransactionDetails(paginationBean, dat, dat1);*/
		
		if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null)
		{
			model.addAttribute("responseData", "No Records found"); //table response
			
		}else {
			model.addAttribute("responseData", null);
		}
		
		//Start New Changes
				/*for(ForSettlement forSettlement:paginationBean.getItemList()){
					//if(!forSettlement.getStatus().equals("CT")){
					if(!forSettlement.getStatus().equals("CT") && !forSettlement.getStatus().equals("CV")){

			              TerminalDetails terminalDetails = transactionService.getTerminalDetailsByTid(forSettlement.getTid().toString());
			          
							if(terminalDetails != null )
							{
								//logger.info("Contact :"+terminalDetails.getContactName());	
								 forSettlement.setMerchantName(terminalDetails.getContactName()); 
							}
						}
					if(forSettlement.getMid()!=null)
					{
						//logger.info("All transactionlist mid "+forSettlement.getMid());
						Merchant merchant=transactionService.loadMerchantbymid(forSettlement.getMid());
						if(merchant!=null)
						{
						forSettlement.setNumOfSale(merchant.getBusinessName());
						//logger.info("All transactionlist mid "+forSettlement.getNumOfSale());
						}
					}
					if(forSettlement.getAmount()!=null){
						double amount=0;
						amount=Double.parseDouble(forSettlement.getAmount())/100;
						//forSettlement.setAmount(amount+"0");
						String pattern = "#,##0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						String output = myFormatter.format(amount);
						//System.out.println(" Amount :"+output);
						forSettlement.setAmount(output);
					}
					if(forSettlement.getStatus().equals("S")){
						forSettlement.setStatus("SETTLED");
					}if(forSettlement.getStatus().equals("P")){
						forSettlement.setStatus("PENDING");
					}
					if(forSettlement.getStatus().equals("A")){
						forSettlement.setStatus("NOT SETTLED");
					}
					if(forSettlement.getStatus().equals("C")){
						forSettlement.setStatus("CANCELLED");
					}
					if(forSettlement.getStatus().equals("R")){
						forSettlement.setStatus("REVERSAL");
					}
					if (forSettlement.getStatus().equals("CT")) {
						forSettlement.setStatus("CASH SALE");
					}
					if (forSettlement.getStatus().equals("CV")) {
						forSettlement.setStatus("CASH CANCELLED");
					}
					if(forSettlement.getTime()!=null){
						try {
							String sd=forSettlement.getTimeStamp();
							//String rd = new SimpleDateFormat("dd-MMM").format(new SimpleDateFormat("MMdd").parse(sd));
							String rd = new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sd));
							String rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
							//forSettlement.setDate(rd+"-"+forSettlement.getTxnYear());
							forSettlement.setDate(rd);
							forSettlement.setTime(rt);
						} catch (ParseException e) {}

					}			
					
				}*/
		
				//End New Changes	
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
		
		}
	 
	 
	 @RequestMapping(value = { "/merchantdetails/{id}" }, method = RequestMethod.GET)
	 public String displayTransactionDetails(final Model model, @PathVariable final String id,HttpServletRequest request,HttpServletResponse response,Principal principal) {
			logger.info("in Transaction details");
	 
			PageBean pageBean=new PageBean("Transactions Details", "transaction/voidpayment/receipt", null);
			logger.info("All Transaction Summary :" + principal.getName());
			
			Merchant merchant = transactionService.loadMerchantDetails(id);
			
			
			request.setAttribute("dto", merchant);
			model.addAttribute("pageBean", pageBean);
			return "transaction/voidpayment/receipt";
			//return id;
	 
	 }
	 
	 
	 
	 
	 @RequestMapping(value = { "/export" }, method = RequestMethod.GET)
		public ModelAndView getExcel(@RequestParam(required = false ) final String date,
			@RequestParam(required = false) final String date1,
			@RequestParam(required = false) final String status,
			@RequestParam (required = false ) String export,
			@RequestParam(required = false, defaultValue = "1") final int currPage){
		logger.info("about to list all  Transaction export"+ status);
		
		String fromDate1 = null;
		String toDate1 = null;
		String status1 = null;
		if ((date != null && date1 != null) && (!date.isEmpty() && !date1.isEmpty())) {
			
			
			try {
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date1));
				
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		if(status != null){// || status.equals("F")){
		/*	status1 = null;
			System.out.println(" Data Status null");
		}else{*/
			status1 = status;
		
		}
		/*PageBean pageBean = new PageBean("transactions list", "transaction/transactionList1", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		
			paginationBean.setCurrPage(currPage);*/
			
		//List<ForSettlement> list =transactionService.listEAllTransactionDetails(fromDate1,toDate1,status1);
			
		List<ForSettlement> list =transactionService.exportAllTransactionByAdmin(fromDate1, toDate1,status1);
		
		for(ForSettlement fs:list){
			/*TransactionRequest tq = merchantService.listMerchantCardDetails(fs
					.getMid());*/
			//logger.info("txnId: "+fs.getTrxId());
			TransactionRequest tr = merchantService.listMerchantCardDetails(fs.getTrxId());

			if(tr!=null){
				fs.setAidResponse(tr.getCardScheme());
				fs.setCardType(tr.getCardType());
				if(tr.getMaskedPan()!=null) {
					String maskedPan=tr.getMaskedPan().substring(tr.getMaskedPan().length() - 8);
					fs.setPan(maskedPan.contains("f") ? maskedPan.replaceAll("f","X") :  maskedPan);
					/*if(pan.contains("f")){
						pan=pan.replaceAll("f","X");
						fs.setPan(pan);
					}else{
						fs.setPan(pan);
					}*/
				}
			}
			//logger.info("pan number: "+fs.getPan());
		}
		
		/*model.addAttribute("date", date);
			model.addAttribute("date1", date1);
			model.addAttribute("status", status1);*/
		/*transactionService.listSearchTransactionDetails(paginationBean, dat, dat1);*/
		
		/*if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null)
		{
			model.addAttribute("responseData", "No Records found"); //table response
			
		}else {
			model.addAttribute("responseData", null);
		}*/
		
		//Start New Changes
				/*for(ForSettlement forSettlement:list){
					 //if(!forSettlement.getStatus().equals("CT")){
					if(!forSettlement.getStatus().equals("CT") && !forSettlement.getStatus().equals("CV")){
						TerminalDetails terminalDetails = transactionService.getTerminalDetailsByTid(forSettlement.getTid().toString());
						if(terminalDetails != null )
						{
							//logger.info("Contact :"+terminalDetails.getContactName());	
							 forSettlement.setMerchantName(terminalDetails.getContactName()); 
						}
				    }
					if(forSettlement.getMid()!=null)
					{
						//logger.info("All transactionlist mid "+forSettlement.getMid());
						Merchant merchant=transactionService.loadMerchantbymid(forSettlement.getMid());
						if(merchant!=null)
						{
						forSettlement.setNumOfSale(merchant.getBusinessName());
						//logger.info("All transactionlist mid "+forSettlement.getNumOfSale());
						}
					}
					if(forSettlement.getAmount()!=null){
						double amount=0;
						amount=Double.parseDouble(forSettlement.getAmount())/100;
						//forSettlement.setAmount(amount+"0");
						String pattern = "#,##0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						String output = myFormatter.format(amount);
						//System.out.println(" Amount :"+output);
						forSettlement.setAmount(output);
					}
					if(forSettlement.getStatus().equals("S")){
						forSettlement.setStatus("SETTLED");
					}if(forSettlement.getStatus().equals("P")){
						forSettlement.setStatus("PENDING");
					}
					if(forSettlement.getStatus().equals("A")){
						forSettlement.setStatus("NOT SETTLED");
					}
					if(forSettlement.getStatus().equals("C")){
						forSettlement.setStatus("CANCELLED");
					}
					if(forSettlement.getStatus().equals("R")){
						forSettlement.setStatus("REVERSAL");
					}
					if(forSettlement.getStatus().equals("CT")){
						forSettlement.setStatus("CASH SALE");
					}
					if(forSettlement.getStatus().equals("CV")){
						forSettlement.setStatus("CASH CANCELLED");
					}
					
					if(forSettlement.getTime()!=null){
						try {
							String sd=forSettlement.getTimeStamp();
							//String rd = new SimpleDateFormat("dd-MMM").format(new SimpleDateFormat("MMdd").parse(sd));
							String rd = new SimpleDateFormat("dd-MMM-yyyy").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sd));
							String rt = new SimpleDateFormat("HH:mm:ss").format(new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
							//forSettlement.setDate(rd+"-"+forSettlement.getTxnYear());
							forSettlement.setDate(rd);
							forSettlement.setTime(rt);
						} catch (ParseException e) {}

					}			
					
				}*/
		
				//End New Changes	
		/*model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;*/
				
				if(!(export.equals("PDF"))){
					
					return new ModelAndView("txnAListExcel", "txnList", list);
			
					}else{
						return new ModelAndView("txnAListPdf", "txnList", list);
					}
		
		}
	

	 @RequestMapping(value = { "/export1" }, method = RequestMethod.GET)
		public ModelAndView getExcelEx(@RequestParam(required = false ) final String date,
			@RequestParam(required = false) final String date1,
			@RequestParam (required = false ) String export,
			@RequestParam (required = false ) String mid,
			@RequestParam (required = false ) String tid,
			@RequestParam(required = false, defaultValue = "1") final int currPage){
			logger.info("about to list Transaction Expiry export");
			
			String fromDate1 = null;
			String toDate1 = null;
			String status1 = null;
			if ((date != null && date1 != null) && (!date.isEmpty() && !date1.isEmpty())) {
				
				
				try {
					fromDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
					toDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date1));
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}
			
				
			List<TransactionRequest> list =transactionService.exportTransactionExpiry(fromDate1, toDate1,mid,tid);
			
			for(TransactionRequest fs:list){
				
				fs.setCardHolderName(mobileUserService.loadBusinessName(fs.getMid()));

			}
			
			
					if(!(export.equals("PDF"))){
						logger.info("Excel");
						return new ModelAndView("enquiryListExcel", "txnList", list);
				
						}else{
							return new ModelAndView("enquiryListPdf", "txnList", list);
						}
			
			}
	 
	 
	 
	 
	 @RequestMapping(value = { "/merchantTransList/{currPage}" }, method = RequestMethod.GET)
		public String MerchantTransactionSummary(final Model model,@PathVariable final int currPage,
				final java.security.Principal principal,
				
				@RequestParam(required = true, defaultValue = "1") final String  responseData) {
			logger.info("Merchant transaction summary..");
			
			PageBean pageBean = new PageBean("transactions list", "transaction/merchantTransSumm", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			logger.info("Merchant Transaction Summary:" + principal.getName());
			model.addAttribute("pageBean", pageBean);
			
			PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
			
			paginationBean.setCurrPage(currPage);
				
			
			
			
			transactionService.MerchantTransactionSummByAdmin(paginationBean, null, null,null);
		
			//transactionService.listAllTransactionDetails(paginationBean,fromDate1,toDate1,status1);
			
			if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null)
			{
				model.addAttribute("responseData", "No Records found"); //table response
				
			}else {
				model.addAttribute("responseData", null);
			}
			
			for(ForSettlement dt:paginationBean.getItemList()){
				
				if(dt.getTxnId()!=null) {
					TransactionRequest trRequest=transactionService.loadTransactionRequest(dt.getTxnId());
					if (trRequest.getCardScheme() != null && trRequest.getCardType() != null) {

						dt.setCardType(trRequest.getCardScheme() + " "
								+ trRequest.getCardType());

					} else if (trRequest.getApplicationLabel() != null) {
						dt.setCardType(trRequest.getApplicationLabel());
					} else if (trRequest.getApplicationLabel() == null && trRequest.getAid() != null) {
						dt.setCardType(CardType.getCardType(trRequest.getAid()));
						
					} else {
						dt.setCardType(" ");
					}
					
					
					//maskedpan
					
					if (trRequest.getMaskedPan() != null) {
						dt.setPan(trRequest.getMaskedPan().toString());
						String pan = dt.getPan().substring(dt.getPan().length() - 8);
						if (pan.contains("f")) {
							pan = pan.replaceAll("f", "X");
							dt.setPan(pan);
						} else {
							dt.setPan(pan);
						}
					} else {
						dt.setPan("NA");
					}
	
					
				}
				
				
				
			}
			
			
			//End New Changes
			
			model.addAttribute("paginationBean", paginationBean);
		

			return TEMPLATE_DEFAULT;
			
		}

		
	 @RequestMapping(value = { "/searchTransList" }, method = RequestMethod.GET)
		public String SearchTransactionSummary(final Model model,
				final java.security.Principal principal,
				@RequestParam(required = false)final String fromDate,
				@RequestParam(required = false) final String toDate,
				@RequestParam(required = false) String status
				) {
			logger.info("Search Merchant transaction summary..");
			
			PageBean pageBean = new PageBean("transactions list", "transaction/merchantTransSumm", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			logger.info("Merchant Transaction Summary:" + principal.getName());
			model.addAttribute("pageBean", pageBean);
			
			PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
			
			//paginationBean.setCurrPage(currPage);
				
			logger.info(" Search all transaction list status: "+status);
			
			transactionService.MerchantTransactionSummByAdmin(paginationBean, fromDate, toDate,status);
		
			//transactionService.listAllTransactionDetails(paginationBean,fromDate1,toDate1,status1);
			
			if(status.isEmpty() || status==null) {
				status=null;
			}
			
			if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null)
			{
				model.addAttribute("responseData", "No Records found"); //table response
				
			}else {
				model.addAttribute("responseData", null);
			}
			
			for(ForSettlement dt:paginationBean.getItemList()){
				
				if(dt.getTxnId()!=null) {
					TransactionRequest trRequest=transactionService.loadTransactionRequest(dt.getTxnId());
					if (trRequest.getCardScheme() != null && trRequest.getCardType() != null) {

						dt.setCardType(trRequest.getCardScheme() + " "
								+ trRequest.getCardType());

					} else if (trRequest.getApplicationLabel() != null) {
						dt.setCardType(trRequest.getApplicationLabel());
					} else if (trRequest.getApplicationLabel() == null && trRequest.getAid() != null) {
						dt.setCardType(CardType.getCardType(trRequest.getAid()));
						
					} else {
						dt.setCardType(" ");
					}
					
					
					//maskedpan
					
					if (trRequest.getMaskedPan() != null) {
						dt.setPan(trRequest.getMaskedPan().toString());
						String pan = dt.getPan().substring(dt.getPan().length() - 8);
						if (pan.contains("f")) {
							pan = pan.replaceAll("f", "X");
							dt.setPan(pan);
						} else {
							dt.setPan(pan);
						}
					} else {
						dt.setPan("NA");
					}
	
					
				}
				
				
				
			}
			
			
			//End New Changes
			
			model.addAttribute("paginationBean", paginationBean);
		

			return TEMPLATE_DEFAULT;
			
		}
	 
	 @RequestMapping(value = { "/exportTransList" }, method = RequestMethod.GET)
		public ModelAndView exportTransactionSummary(final Model model,
				final java.security.Principal principal,
				@RequestParam(required = false)final String fromDate,
				@RequestParam(required = false) final String toDate,
				@RequestParam (required = false ) String export,
				@RequestParam(required = true, defaultValue = "1") final String  responseData) {
			logger.info("Merchant transaction summary..");
			
			PageBean pageBean = new PageBean("transactions list", "transaction/merchantTransSumm", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			logger.info("Merchant Transaction Summary:" + principal.getName());
			model.addAttribute("pageBean", pageBean);
			
			List<ForSettlement> list =transactionService.MerchantExportTrans(fromDate, toDate,null);
			
			for(ForSettlement dt:list){	
			
				if(dt.getTxnId()!=null) {
					TransactionRequest trRequest=transactionService.loadTransactionRequest(dt.getTxnId());
					if (trRequest.getCardScheme() != null && trRequest.getCardType() != null) {

						dt.setCardType(trRequest.getCardScheme() + " "
								+ trRequest.getCardType());

					} else if (trRequest.getApplicationLabel() != null) {
						dt.setCardType(trRequest.getApplicationLabel());
					} else if (trRequest.getApplicationLabel() == null && trRequest.getAid() != null) {
						dt.setCardType(CardType.getCardType(trRequest.getAid()));
						
					} else {
						dt.setCardType(" ");
					}
					
					
					//maskedpan
					
					if (trRequest.getMaskedPan() != null) {
						dt.setPan(trRequest.getMaskedPan().toString());
						String pan = dt.getPan().substring(dt.getPan().length() - 8);
						if (pan.contains("f")) {
							pan = pan.replaceAll("f", "X");
							dt.setPan(pan);
						} else {
							dt.setPan(pan);
						}
					} else {
						dt.setPan("NA");
					}
	
					
				}
				
				
				
			}
			
			
			//End New Changes
			if(!(export.equals("PDF"))){
				
				return new ModelAndView("merchanttxnListExcel", "txnList", list);
		
				}else{
					return new ModelAndView("merchanttxnListPdf", "txnList", list);
				}
			
		}

	 
	 @RequestMapping(value = { "/testMQEmail" }, method = RequestMethod.GET)
		public String TestMQEmail(final Model model,
				final java.security.Principal principal) throws JMSException {
			
			PageBean pageBean = new PageBean("transactions list", "transaction/TestMQEmail", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			logger.info("Email Send To MQ:" + principal.getName());
			model.addAttribute("pageBean", pageBean);
			

		
			model.addAttribute("pageBean", pageBean);
		

			return TEMPLATE_DEFAULT;
			
		}


	 
	 
	 @RequestMapping(value = { "/SendMQEmail" }, method = RequestMethod.POST)
		public String TestMQSendEmail(final Model model,@RequestParam("toAddress") String toAddress,
				@RequestParam("type") String type,
				final java.security.Principal principal) throws JMSException, ConnectionException {
			
			PageBean pageBean = new PageBean("transactions list", "transaction/TestMQEmail", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			logger.info("Email Send To MQ:" + principal.getName()+toAddress+" type: "+type);
			model.addAttribute("pageBean", pageBean);
			SendSMSMessage sm=new SendSMSMessage();
			MsgDto tm = new MsgDto();

			tm = new MsgDto();
			// messageJSON.setName("Business Name");

			tm.setDate("15-JAN-2019");
			tm.setTime("11:12:34");

			tm.setFirstName("Karthiga");
			tm.setLastName("Arul");
			tm.setUserName(toAddress);
			tm.setPassword("abc123");
			tm.setEzyPOD(false);
			tm.setActivationCode("9389MOBIPYT8765");
			/*tm.setEzywayMid("000777700007777");
			tm.setEzywayTid("77779191");
			tm.setEzywayApiKey("fdbd31f2027f20378b1a80125fc862db");
*/
			tm.setMerchantEmail("karthiga@mobiversa.com");
			
			tm.setEzyrecMid("000777700007777"); 
			tm.setEzyrecTid("77779191");
			tm.setEzypodApiKey("fdbd31f2027f20378b1a80125fc862db"); 
			tm.setEzyPOD(true);

			if(type.equals("merchantReg")) {
				sm.sendMerchantRegEmail(tm);
			}else if(type.equals("deviceReg")) {
				sm.sendDeviceRegEmail(tm);
			}else if(type.equals("reset")) {
				sm.sendResetMerchantPasswordEmail(tm);
			}
		
			model.addAttribute("pageBean", pageBean);
		

			return TEMPLATE_DEFAULT;
			
		}


	 @RequestMapping(value = { "/enquiryTransaction" }, method = RequestMethod.GET)
		public String TransactionEnquiry(final Model model,@RequestParam(required = false, defaultValue = "1") final int currPage,
				final java.security.Principal principal) {
			logger.info("Transaction Enquiry list");
			
			PageBean pageBean = new PageBean("transactions list", "transaction/transactionEnquiry", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			logger.info(" All Transaction Summary:" + principal.getName());
			model.addAttribute("pageBean", pageBean);
			
			PaginationBean<TransactionRequest> paginationBean = new PaginationBean<TransactionRequest>();
			
			paginationBean.setCurrPage(currPage);
				
			List<MID> midList=transactionService.loadAllmid();
		
			transactionService.getTransactionEnquiry(paginationBean, null, null, null,null);
			
			if(paginationBean.getItemList().size()!=0){
				for(TransactionRequest t:paginationBean.getItemList() ) {
					
					t.setCardHolderName(mobileUserService.loadBusinessName(t.getMid()));
				}
			}
			
			if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null){
				model.addAttribute("responseData", "No Records found"); //table response
			}else {
				model.addAttribute("responseData", null);
			}
			List<String> midData=new ArrayList<String>();
			for(MID mid:midList){

				//logger.info(mid.getMid()+" "+mid.getMotoMid()+" "+mid.getEzypassMid()+" "+mid.getEzyrecMid()+" "+mid.getEzywayMid());
				if(mid.getMid()!=null) {
				midData.add(mid.getMid());
				}
				if(mid.getMotoMid()!=null) {
					midData.add(mid.getMotoMid());
				}
				if(mid.getEzypassMid()!=null) {
					midData.add(mid.getEzypassMid());
				}
				if(mid.getEzyrecMid()!=null) {
					midData.add(mid.getEzyrecMid());
				}
				if(mid.getEzywayMid()!=null) {
					midData.add(mid.getEzywayMid());
				}
				
			}
			List<TerminalDetails> tidList=transactionService.loadAlltid();
			List<String> tidData=new ArrayList<String>();
			for(TerminalDetails tid:tidList) {
				tidData.add(tid.getTid());
			}
			//End New Changes
			
			model.addAttribute("paginationBean", paginationBean);
			model.addAttribute("midList", midData);
			model.addAttribute("tidList", tidData);
			//model.addAttribute("responseData", responseData);

			return TEMPLATE_DEFAULT;
			//return "redirect:/transaction1/search1/";
		}
	
	 
	 @RequestMapping(value = { "/searchenquiryTransaction" }, method = RequestMethod.GET)
		public String searchTransactionEnquiry(final Model model,@RequestParam(required = false, defaultValue = "1") final int currPage,@RequestParam("fromDate") String fromDate
				,@RequestParam("toDate") String toDate
				,@RequestParam("mid") String mid,@RequestParam("tid") String tid) {
			logger.info("seach Transaction Enquiry list");
			String fromDate1 = HtmlUtils.htmlEscape(fromDate);
			String toDate1 = HtmlUtils.htmlEscape(toDate);
			PageBean pageBean = new PageBean("transactions list", "transaction/transactionEnquiry", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			//logger.info(" All Transaction Summary:" + principal.getName());
			model.addAttribute("pageBean", pageBean);
			
			PaginationBean<TransactionRequest> paginationBean = new PaginationBean<TransactionRequest>();
			
			paginationBean.setCurrPage(currPage);
				
			if ((fromDate1 != null && toDate1 != null) && (!fromDate1.isEmpty() && !toDate1.isEmpty())) 
			{
				try 
				{
					fromDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate1));
					toDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate1));
				logger.info("inside search controler "+fromDate+" "+toDate);
				} 
				catch (ParseException e) {
					
					e.printStackTrace();
				}
				
			}
			List<MID> midList=transactionService.loadAllmid();
			
			transactionService.getTransactionEnquiry(paginationBean, fromDate, toDate, mid,tid);
			
			if(paginationBean.getItemList().size()!=0){
				for(TransactionRequest t:paginationBean.getItemList() ) {
					
					t.setCardHolderName(mobileUserService.loadBusinessName(t.getMid()));
				}
			}
			
			if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null){
				model.addAttribute("responseData", "No Records found"); //table response
			}else {
				model.addAttribute("responseData", null);
			}
			List<String> midData=new ArrayList<String>();
			for(MID mids:midList){

				//logger.info(mid.getMid()+" "+mid.getMotoMid()+" "+mid.getEzypassMid()+" "+mid.getEzyrecMid()+" "+mid.getEzywayMid());
				if(mids.getMid()!=null) {
				midData.add(mids.getMid());
				}
				if(mids.getMotoMid()!=null) {
					midData.add(mids.getMotoMid());
				}
				if(mids.getEzypassMid()!=null) {
					midData.add(mids.getEzypassMid());
				}
				if(mids.getEzyrecMid()!=null) {
					midData.add(mids.getEzyrecMid());
				}
				if(mids.getEzywayMid()!=null) {
					midData.add(mids.getEzywayMid());
				}
				
			}
			List<TerminalDetails> tidList=transactionService.loadAlltid();
			List<String> tidData=new ArrayList<String>();
			for(TerminalDetails tids:tidList) {
				tidData.add(tids.getTid());
			}
			//End New Changes
			
			model.addAttribute("paginationBean", paginationBean);
			model.addAttribute("midList", midData);
			model.addAttribute("tidList", tidData);
			return TEMPLATE_DEFAULT;
			//return "redirect:/transaction1/search1/";
		}
	 
	 
	 @RequestMapping(value = { "/update" }, method = RequestMethod.GET)
		public String updateTxn(
			@RequestParam(required = false) final String txnID,
			@RequestParam(required = false) final String status
			){
		 
			Date modifiedDate = new Date();
			logger.info("/update ~ To update All transaction history status ");
			logger.info("Modified Date:::::::::" + modifiedDate);

			/*PageBean pageBean = new PageBean("transactions list", "notification/pushNotificationList",
					Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

			model.addAttribute("pageBean", pageBean);*/
			
			
			ForSettlement settle = transactionService.getForSettlement(txnID);
			
			settle.setStatus(status);

			transactionService.updateTxnStatus(settle);
		 
		 
			return "redirect:/transaction1/list/1";
		 
		 
		
		}
	 
	 @RequestMapping(value = { "/socketErrorAlert" }, method = RequestMethod.GET)
     public String errorAlert(final Model model,
             final java.security.Principal principal) throws JMSException {
         
         PageBean pageBean = new PageBean("transactions list", "transaction/socketErrorAlert", Module.TRANSACTION,
                 "transaction/sideMenuTransaction");
         logger.info("Socket Error Alert:" + principal.getName());



         model.addAttribute("pageBean", pageBean);
         return TEMPLATE_DEFAULT;
         
     }
  
  @RequestMapping(value = { "/SendErrorUpdate" }, method = RequestMethod.POST)
     public String SendErrrorUpdate(final Model model,@RequestParam("type") String type,
             final java.security.Principal principal) throws JMSException, ConnectionException {
         
         PageBean pageBean = new PageBean("transactions list", "transaction/socketErrorAlert", Module.TRANSACTION,
                 "transaction/sideMenuTransaction");
         logger.info("Socket Error Alert Update:::::: "+type);
         MobileOTP sm= merchantService.checkOTP("SOCKETALERT", "SOCKETALERT");



         logger.info("Socket Status:::::: "+sm.getOptData());
         logger.info("Changing Socket Status:::::: "+type);
         
         merchantDAO.updateMobileOTP(type,"SOCKETALERT");
     
         logger.info("::::Status updated successfully:::::: ");
         
         model.addAttribute("pageBean", pageBean);
     



         return TEMPLATE_DEFAULT;
         
     }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
}