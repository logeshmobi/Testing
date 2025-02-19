package com.mobiversa.payment.controller;


import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.TransactionService;

@Controller
@RequestMapping(value = AllTransactionSuperAgentController.URL_BASE)
public class AllTransactionSuperAgentController extends BaseController {
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private TransactionService transactionService;
	
	public static final String URL_BASE = "/superagent2";
	
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage()
	{ 
		return "redirect:" + URL_BASE + "/list/1";
	}
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayTransactionSummary(final Model model,@PathVariable final int currPage,
			final java.security.Principal principal,
			@RequestParam(required = false)final String fromDate,
			@RequestParam(required = false) final String toDate,
			@RequestParam(required = false) final String status,
			@RequestParam(required = true, defaultValue = "1") final String  responseData) 
	{
		
		PageBean pageBean = new PageBean("transactions list", "SuperAgent/transactionList1", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		
		model.addAttribute("pageBean", pageBean);
		
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		
		paginationBean.setCurrPage(currPage);
				
		String fromDate1 = null;
		String toDate1 = null;
		String status1 = null;
		if((fromDate != null && toDate!=null) && (!fromDate.isEmpty() && !toDate.isEmpty()))
		{// || !date.equals("1")){
			try
			{
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(fromDate));
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(toDate));
			 
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			
			}
		if(status != null){// || !(status.equals(""))){
			/*status1 = "";
		}else{*/
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
					
					String rd = new SimpleDateFormat("dd-MMM-yyyy").format(
							new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(forSettlement.getTimeStamp()));
					String rt = new SimpleDateFormat("HH:mm:ss").format(
							new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
					//forSettlement.setDate(rd+"-"+forSettlement.getTxnYear());
					forSettlement.setDate(rd);
					forSettlement.setTime(rt);
				} catch (ParseException e) {}

			}	
			
			
		}	
		
		//End New Changes
		
		model.addAttribute("paginationBean", paginationBean);
		//model.addAttribute("responseData", responseData);

		return TEMPLATE_SUPER_AGENT;
		//return "redirect:/transaction1/search1/";
	}

	 @RequestMapping(value = { "/search1" }, method = RequestMethod.GET)
		public String displayAllTransactionList(final Model model, 
				@RequestParam(required = false ) final String fromDate,
			@RequestParam(required = false) final String toDate,
			@RequestParam(required = false) final String status,
			@RequestParam(required = false, defaultValue = "1") final int currPage){
		logger.info("about to list all  Transactiona");
		
		String fromDate1 = null;
		String toDate1 = null;
		String status1 = null;
		if ((fromDate != null && toDate != null) && (!fromDate.isEmpty() && !toDate.isEmpty()))
		{
			try {
				fromDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(fromDate));
				toDate1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(toDate));
			} 
			catch (ParseException e) {
				
				e.printStackTrace();
			}
			
		}
		
		if(status != null){// || status.equals("F")){
		/*	status1 = null;
			System.out.println(" Data Status null");
		}else{*/
			status1 = status;
		
		}
		PageBean pageBean = new PageBean("transactions list", "SuperAgent/transactionList1", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		
		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		
			paginationBean.setCurrPage(currPage);
			
			transactionService.listAllTransactionDetails(paginationBean,fromDate1,toDate1,status1);
			model.addAttribute("date", fromDate1);
			model.addAttribute("date1", toDate1);
			model.addAttribute("status", status1);
		/*transactionService.listSearchTransactionDetails(paginationBean, dat, dat1);*/
		
		if(paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null)
		{
			model.addAttribute("responseData", "No Records found"); //table response
			
		}else {
			model.addAttribute("responseData", null);
		}
		
		//Start New Changes
				for(ForSettlement forSettlement:paginationBean.getItemList()){
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
							String rd = new SimpleDateFormat("dd-MMM-yyyy").format(
									new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(forSettlement.getTimeStamp()));
							String rt = new SimpleDateFormat("HH:mm:ss").format(
									new SimpleDateFormat("HHmmss").parse(forSettlement.getTime()));
							
							forSettlement.setDate(rd);
							forSettlement.setTime(rt);
						} catch (ParseException e) {}

					}			
					
				}
		
				//End New Changes	
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_SUPER_AGENT;
		
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
	 
	 
	

	 }


