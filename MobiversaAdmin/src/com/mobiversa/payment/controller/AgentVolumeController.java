package com.mobiversa.payment.controller;



import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.MasterMerchant;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.AgentVolumeData;
import com.mobiversa.payment.dto.MerchantGPVData;
import com.mobiversa.payment.service.AgentService;
/*import com.mobiversa.payment.dto.ListData;
import com.mobiversa.payment.dto.ListData1;
import com.mobiversa.payment.dto.ListDate;*/
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.SubAgentMenuService;
import com.mobiversa.payment.service.SubAgentService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.validator.AddAgentUserValidator;
/*import com.mobiversa.payment.validator.AddBankUserValidator;*/

@SuppressWarnings("unused")
@Controller
@RequestMapping(value = AgentVolumeController.URL_BASE)

public class AgentVolumeController extends BaseController {
	
	@Autowired
	private SubAgentService subAgentService;
	
	@Autowired
	private SubAgentMenuService subAgentMenuService;
	
	@Autowired
	private MobileUserService mobileUserService;
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AddAgentUserValidator validator;

	
	public static final String URL_BASE = "/agentVolume";

	private static final Logger logger = Logger
			.getLogger(AgentVolumeController.class);
	
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
    System.out.println("default page display");
		return "redirect:" + URL_BASE + "/list/1";
	}
	
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displaySubAgentList(final Model model, @PathVariable final int currPage,java.security.Principal principal){//,String agentName) {
		logger.info("about to list all agents");
		PageBean pageBean = new PageBean("Agent", "agent/agentVolumeSummary", Module.AGENT,
				"agent/sideMenuAgent");
		//logger.info("about to list all agents");
		
		
		logger.info("Agent Portal Currently logged by "+ principal.getName());
				
		Agent agent=agentService.loadAgentbyMailId(principal.getName());
					
		model.addAttribute("pageBean", pageBean);
		
		List<AgentVolumeData> data = new ArrayList<AgentVolumeData>();
		PaginationBean<AgentVolumeData> paginationBean1 = new PaginationBean<AgentVolumeData>();
		paginationBean1.setCurrPage(currPage);
		
		/* Start Sub Agent */
	/*	List<SubAgent> listSubAgent=subAgentService.loadSubAgentbyId(agent);
		//if( listSubAgent.size() > 0){
		List<AgentVolumeData> listSubAgentVolumeData = new ArrayList<AgentVolumeData>();
		for(SubAgent su : listSubAgent){
			listSubAgentVolumeData = transactionService.subAgentVolumeData(su);
		
			for(AgentVolumeData subAgentVolumeData : listSubAgentVolumeData){
			   logger.info("subAgId:" + subAgentVolumeData.getAgId());
			   logger.info("subAgentName:" + subAgentVolumeData.getAgentName());
			   logger.info("Controller txn Type : "+subAgentVolumeData.getTxnType());
			   logger.info("subagentDet:" + subAgentVolumeData.getAgentDet());
			   logger.info("subAgent amount:" + subAgentVolumeData.getAmount());
			   logger.info("subAgent Date:" + subAgentVolumeData.getDate());
			   data.add(subAgentVolumeData);
			}
		}*/
		/* End Sub Agent */
		
		/*Start Agent */
		List<AgentVolumeData> listAgentVolumeData = new ArrayList<AgentVolumeData>();
		listAgentVolumeData= transactionService.agentVolumeData(agent.getFirstName());
		logger.info("listAgentVolumeData Size : "+listAgentVolumeData.size());
		
		List<AgentVolumeData> listAgentVolumeData1 = new ArrayList<AgentVolumeData>();
		listAgentVolumeData1= transactionService.agentVolumeData1(agent.getFirstName());
		logger.info("listAgentVolumeData1 Size : "+listAgentVolumeData1.size());
		
		
		//forSettlement Amount String List to Long list
		List<String> forStringAmount = listAgentVolumeData.get(0).getAmount();
			logger.info("List forTxn String Amount ::"+forStringAmount);
			logger.info("List forTxn String Amount Size ::"+forStringAmount.size());
		List<Double> forLongAmount = new ArrayList<Double>(forStringAmount.size());
		for(String s : forStringAmount) 
			forLongAmount.add(Double.parseDouble(s));
			logger.info("List forTxn Long Amount ::"+forLongAmount);
			logger.info("List forTxn Long Amount Size ::"+forLongAmount.size());
		
		//UM Amount String  List to Long list
		List<String> forStringAmountUM = listAgentVolumeData1.get(0).getAmount();
			logger.info("List UMTxn String Amount ::"+forStringAmountUM);
			logger.info("List UMTxn String Amount Size ::"+forStringAmountUM.size());
		List<Double> forLongAmountUM = new ArrayList<Double>(forStringAmountUM.size());
		for(String s : forStringAmountUM) 
			forLongAmountUM.add(Double.parseDouble(s));
			logger.info("List UMTxn Long Amount ::"+forLongAmountUM);
			logger.info("List UMTxn Long Amount Size ::"+forLongAmountUM.size());
		
	    //Add two long list value and convert string list
			
			int len = 0;
			if(forLongAmountUM.size() > forLongAmount.size()) {
				len = forLongAmountUM.size();
			}else {
				len = forLongAmount.size();
			}
			
			List<Double> newAmount = new ArrayList<Double>(len);
			for (int i =0; i< len ; i++) {
				Double Amount = forLongAmount.get(i) + forLongAmountUM.get(i);
				logger.info("List Amount ::"+Amount);
				newAmount.add(Amount);
			}
			
			logger.info("List newAmount Long Amount ::"+newAmount);
			logger.info("List newAmount Long Amount Size ::"+newAmount.size());
			
			
			List<String> newAmountString = new ArrayList<String>(newAmount.size());
			for(Double s : newAmount) 
				newAmountString.add(String.valueOf(s));
				logger.info("List newAmount String Amount ::"+newAmountString);
				logger.info("List newAmount String Amount Size ::"+newAmountString.size());
		
//	}
		listAgentVolumeData.get(0).setAmount(newAmountString);
		
	
		for(AgentVolumeData agentVolumeData: listAgentVolumeData){
			logger.info("Controller agId : "+agentVolumeData.getAgId());
			logger.info("Controller agentName : "+agentVolumeData.getAgentName());
			logger.info("Controller agentDet : "+agentVolumeData.getAgentDet());
			logger.info("Controller amount : "+agentVolumeData.getAmount());
			logger.info("Controller date : "+agentVolumeData.getDate());
			
			data.add(agentVolumeData);
		
		}
		/*End Agent */
		
		paginationBean1.setItemList(data);
	
		
		model.addAttribute("paginationBean", paginationBean1);

		return TEMPLATE_AGENT;
		}
		

public String getMonth(int m){
		
		//System.out.println(" Data :"+m);
		String mon ="";
		switch(m){
		case 1:
			mon="JAN";
			break;
		case 2:
			mon="FEB";
			break;
		case 3:
			mon="MAR";
			break;
		case 4:
			mon="APR";
			break;
		case 5:
			mon="MAY";
			break;
		case 6:
			mon="JUN";
			break;
		case 7:
			mon="JUL";
			break;
		case 8:
			mon="AUG";
			break;
		case 9:
			mon="SEP";
			break;
		case 10:
			mon="OCT";
			break;
		case 11:
			mon="NOV";
			break;
		case 12:
		  mon="DEC";
		break;
		
		
		default:
			 mon="";
				break;	
		}
	
		return mon;
		
	}
	


@RequestMapping(value={"/merchantvolume/{merchantName}/{index}"},method=RequestMethod.GET)
public String displayMerchantVolume(final Model model, @PathVariable final String merchantName,
		 @PathVariable String index, java.security.Principal principal,
		 @RequestParam(required = false, defaultValue = "1") final int currPage)
{
	logger.info("agent check merchantVolume-------------"+merchantName);
	
	int offSet=10;
	PageBean pageBean = new PageBean("Agent", "agent/merchantVolumeSummary", Module.AGENT,
				"agent/sideMenuAgent");
	
	List<AgentVolumeData> data = new ArrayList<AgentVolumeData>();
	Agent agent=agentService.loadAgentbyMailId(principal.getName()); 
	//List<SubAgent> subagent=subAgentService.loadSubAgentbyId(agent);
	
	model.addAttribute("pageBean", pageBean);
	
	
	List<AgentVolumeData> listMerchantVolumeData = new ArrayList<AgentVolumeData>();
	
	PaginationBean<AgentVolumeData> paginationBean1 = new PaginationBean<AgentVolumeData>();
	paginationBean1.setCurrPage(currPage);
	
	//listMerchantVolumeData= transactionService.merchantVolumeData(merchantName);
	//float recordCount=Float.parseFloat(transactionService.merchantCount(merchantName)),recordavg=0.0f;
	int recordCount=Integer.parseInt(transactionService.merchantCount(merchantName)),recordavg=0;
	logger.info("recordCount: "+recordCount);
	
	/*if(recordCount >= 10)
		recordavg=Math.round(recordCount/10);
	else
		recordavg=1.0f;*/
	
/*	recordavg = (recordCount >= offSet) ? Math.round(recordCount/offSet): 1.0f;
	System.out.println("average offset: "+recordavg);*/
	
	 recordavg = (recordCount%10)>0?(recordCount/10)+1:(recordCount/10) ;
	System.out.println("Mod : "+recordavg);

	List<String> addList=new ArrayList<String>();
	for(int i=1;i<=recordavg;i++) {
		addList.add(String.valueOf(i));
	}
	logger.info("addList: "+addList.toString());
	logger.info("agent name: "+merchantName);
	//listMerchantVolumeData= transactionService.merchantVolumeDataByAgent(merchantName,agent.getId());
	
	/*logger.info("offset data--debug...."+String.valueOf((Integer.parseInt(index)-1)*100));
	listMerchantVolumeData= transactionService.merchantVolumeDataByAgent(merchantName,agent.getId(),
			String.valueOf((Integer.parseInt(index)-1)*100));*/
	
	logger.info("offset data--debug...."+String.valueOf((Integer.parseInt(index)-1)*10));
	listMerchantVolumeData= transactionService.merchantVolumeDataByAgent(merchantName,agent.getId(),
			String.valueOf((Integer.parseInt(index)-1)*10));
	
	
	for(AgentVolumeData merchantVolumeData: listMerchantVolumeData){
		
		
		//logger.info("Controller merchantId : "+merchantVolumeData.getAgId());
		//logger.info("Controller agentName : "+merchantVolumeData.getAgentName());
		//logger.info("Controller txn Type : "+merchantVolumeData.getTxnType());
		//logger.info("Controller agentDet : "+merchantVolumeData.getAgentDet());
		//logger.info("Controller amount : "+merchantVolumeData.getAmount());
		//logger.info("Controller date : "+merchantVolumeData.getDate());
		
		
		data.add(merchantVolumeData);
	
	}
	
	
	paginationBean1.setItemList(data);
	
	logger.info("count to display"+addList);
	
	model.addAttribute("count", addList);
	model.addAttribute("agentName", merchantName);
	model.addAttribute("paginationBean", paginationBean1);
	model.addAttribute("offset", index);
	model.addAttribute("merchantCount", recordavg);
	
	return TEMPLATE_AGENT;
	
}


@RequestMapping(value={"/listmerchantvolume"},method=RequestMethod.GET)
public String listMerchantVolumeSummary(final Model model,java.security.Principal principal)
{
	logger.info("about to list all merchants for this agent");
	PageBean pageBean = new PageBean("Agent", "agent/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	//logger.info("about to list all agents");
	
	
	logger.info("Agent Portal Currently logged by "+ principal.getName());
			
	Agent agent=agentService.loadAgentbyMailId(principal.getName());
				
	model.addAttribute("pageBean", pageBean);
	
	logger.info("Agent name"+ agent.getFirstName());
	logger.info("Agent ID"+ agent.getId());

	BigInteger agid = BigInteger.valueOf(agent.getId()); 

	
	List<Merchant> merchant1 = transactionService.getMerchantDataByAgent(agid);
	Set<String> merchantNameList = new HashSet<String>();
	for (Merchant t : merchant1) {
		String businessName = t.getBusinessName();
		String email = t.getUsername();
		merchantNameList.add(businessName.toString() + "~" + email);
	}
	logger.info("merchantNameList"+merchantNameList);
	
	pageBean = new PageBean("Agent", "agent/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	model.addAttribute("pageBean", pageBean);
	model.addAttribute("merchantNameList", merchantNameList);
	model.addAttribute("merchant1", merchant1);
	model.addAttribute("agent", agent);

	logger.info("listMerchantVolumeSummary completed");

	return TEMPLATE_AGENT;
	
}

@RequestMapping(value={"/listmerchantvolumesummary"},method=RequestMethod.GET)
public String MerchantVolumeSummary(final Model model,
		@RequestParam("id") Long merchantid,
		java.security.Principal principal)
{
	logger.info("listmerchantvolumesummary for this agent: "+merchantid);
	
	PageBean pageBean = new PageBean("Agent", "agent/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	List<AgentVolumeData> data = new ArrayList<AgentVolumeData>();
	
	PaginationBean<AgentVolumeData> paginationBean1 = new PaginationBean<AgentVolumeData>();
	paginationBean1.setCurrPage(1);
	
	
	Agent agent=agentService.loadAgentbyMailId(principal.getName());
	BigInteger agid = BigInteger.valueOf(agent.getId()); 

	
	List<Merchant> merchant1 = transactionService.getMerchantDataByAgent(agid);
	Set<String> merchantNameList = new HashSet<String>();
	for (Merchant t : merchant1) {
		String businessName = t.getBusinessName();
		String email = t.getUsername();
		merchantNameList.add(businessName.toString() + "~" + email);
	}
	/*List<String> listMid = transactionService.loadmidBymerchant(merchantid);
	logger.info("listMid: "+listMid);*/
	
	
	/*Merchant midDetails = mobileUserService.loadIserMidDetails(merchantid);
	logger.info("midDetails: "+midDetails);
	logger.info("mid: "+midDetails.getMid().getMid());
	logger.info("mid: "+midDetails.getMid().getEzyrecMid());
	logger.info("mid: "+midDetails.getMid().getEzypassMid());
	logger.info("mid: "+midDetails.getMid().getEzywayMid());*/
	
	List<AgentVolumeData> listAVD = new ArrayList<AgentVolumeData>();
	listAVD = transactionService.getMerchantVolByMid(String.valueOf(merchantid));
	logger.info("listAVD: "+listAVD);
	
	List<AgentVolumeData> listAVD1 = new ArrayList<AgentVolumeData>();
	listAVD1 = transactionService.getMerchantVolByMid1(String.valueOf(merchantid));
	logger.info("listAVD1: "+listAVD1);
	
	
	//forSettlement Amount String List to Long list
	List<String> forStringAmount = listAVD.get(0).getAmount();
		logger.info("List forTxn String Amount ::"+forStringAmount);
		logger.info("List forTxn String Amount Size ::"+forStringAmount.size());
	List<Double> forLongAmount = new ArrayList<Double>(forStringAmount.size());
	for(String s : forStringAmount) 
		forLongAmount.add(Double.parseDouble(s));
		logger.info("List forTxn Long Amount ::"+forLongAmount);
		logger.info("List forTxn Long Amount Size ::"+forLongAmount.size());
	
	//UM Amount String  List to Long list
	List<String> forStringAmountUM = listAVD1.get(0).getAmount();
		logger.info("List UMTxn String Amount ::"+forStringAmountUM);
		logger.info("List UMTxn String Amount Size ::"+forStringAmountUM.size());
	List<Double> forLongAmountUM = new ArrayList<Double>(forStringAmountUM.size());
	for(String s : forStringAmountUM) 
		forLongAmountUM.add(Double.parseDouble(s));
		logger.info("List UMTxn Long Amount ::"+forLongAmountUM);
		logger.info("List UMTxn Long Amount Size ::"+forLongAmountUM.size());
	
    //Add two long list value and convert string list
		
		int len = 0;
		if(forLongAmountUM.size() > forLongAmount.size()) {
			len = forLongAmountUM.size();
		}else {
			len = forLongAmount.size();
		}
		
		List<Double> newAmount = new ArrayList<Double>(len);
		for (int i =0; i< len ; i++) {
			Double Amount = forLongAmount.get(i) + forLongAmountUM.get(i);
			logger.info("List Amount ::"+Amount);
			newAmount.add(Amount);
		}
		
		logger.info("List newAmount Long Amount ::"+newAmount);
		logger.info("List newAmount Long Amount Size ::"+newAmount.size());
		
		
		List<String> newAmountString = new ArrayList<String>(newAmount.size());
		for(Double s : newAmount) 
			newAmountString.add(String.valueOf(s));
			logger.info("List newAmount String Amount ::"+newAmountString);
			logger.info("List newAmount String Amount Size ::"+newAmountString.size());
	
//}
		listAVD.get(0).setAmount(newAmountString);
	

	for(AgentVolumeData merchantVolumeData: listAVD){

		data.add(merchantVolumeData);
	
	}
	
	
	paginationBean1.setItemList(data);
	
	model.addAttribute("pageBean", pageBean);
	model.addAttribute("paginationBean", paginationBean1);
	model.addAttribute("merchantNameList", merchantNameList);
	model.addAttribute("merchant1", merchant1);


	return TEMPLATE_AGENT;
	
}
/*@RequestMapping(value={"/listmerchantvolumesummary"},method=RequestMethod.GET)
public String MerchantVolumeSummary(final Model model,
		@RequestParam("id") Long merchantid,
		@RequestParam("type") String merchantType,
		java.security.Principal principal)
{
	logger.info("listmerchantvolumesummary for this agent: "+merchantid);
	
	PageBean pageBean = new PageBean("Agent", "agent/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	List<AgentVolumeData> data = new ArrayList<AgentVolumeData>();
	
	PaginationBean<AgentVolumeData> paginationBean1 = new PaginationBean<AgentVolumeData>();
	paginationBean1.setCurrPage(1);
	
	
	Agent agent=agentService.loadAgentbyMailId(principal.getName());
BigInteger agid = BigInteger.valueOf(agent.getId()); 

	
	List<Merchant> merchant1 = transactionService.getMerchantDataByAgent(agid);
	Set<String> merchantNameList = new HashSet<String>();
	for (Merchant t : merchant1) {
		String businessName = t.getBusinessName();
		String email = t.getUsername();
		merchantNameList.add(businessName.toString() + "~" + email);
	}
	
	
	List<AgentVolumeData> listAVD = new ArrayList<AgentVolumeData>();
	listAVD = transactionService.getMerchantVolByMid(String.valueOf(merchantid),merchantType);
	BigInteger merid = BigInteger.valueOf(merchantid); 

	Merchant m = transactionService.getMerchantTypeByID(String.valueOf(merchantid));
	String merchantType=m.getMerchantType();
	logger.info("merchantType: "+merchantType);
	listAVD = transactionService.getMerchantVolByMid(String.valueOf(merchantid),merchantType);
	
	logger.info("listAVD: "+listAVD);
	

	for(AgentVolumeData merchantVolumeData: listAVD){

		data.add(merchantVolumeData);
	
	}
	
	
	paginationBean1.setItemList(data);
	
	model.addAttribute("pageBean", pageBean);
	model.addAttribute("paginationBean", paginationBean1);
	model.addAttribute("merchantNameList", merchantNameList);
	model.addAttribute("merchant1", merchant1);


	return TEMPLATE_AGENT;
	
}*/
  

/*@RequestMapping(value={"/merchantvolume/{merchantName}"},method=RequestMethod.GET)
public String displayMerchantVolume(final Model model, @PathVariable final String merchantName,
		  java.security.Principal principal,@RequestParam(required = false, defaultValue = "1") final int currPage)
{
	logger.info("Agent - Merchants list");
	PageBean pageBean = new PageBean("merchantSummaryList", "agent/merchantTxnSummary", Module.AGENT,
			"merchant/sideMenuMerchant");
	Agent agent=agentService.loadAgentbyMailId(principal.getName());

	model.addAttribute("pageBean", pageBean);
	logger.info("Merchant Summary:" + principal.getName() );
	PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
	paginationBean.setCurrPage(currPage);
	merchantService.listMerchant1(paginationBean,agent.getId().toString());
	List<Merchant> lstMerchant = new ArrayList<Merchant>();
	
	List<Merchant> listMerchant= paginationBean.getItemList();
	for(Merchant mer : listMerchant){
		mer.setBusinessName(mer.getBusinessName().toUpperCase());
		lstMerchant.add(mer);
	}
	paginationBean.setItemList(lstMerchant);
	model.addAttribute("paginationBean", paginationBean);
	return TEMPLATE_AGENT;
}


@RequestMapping(value={"/merchantTxnvolumes"},method=RequestMethod.GET)
public String displayMerchantVolumes(final Model model, @RequestParam final String merchantID,
		@RequestParam String merchantName,
		  java.security.Principal principal)
{
	logger.info("Agent - Merchants volume list "+merchantID);
	PageBean pageBean = new PageBean("merchantSummaryList", "agent/merchantTransaction", Module.AGENT,
			null);
	
	Agent agent=agentService.loadAgentbyMailId(principal.getName());

	model.addAttribute("pageBean", pageBean);
	logger.info("Merchant Summary:" + principal.getName() );
	PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
	//paginationBean.setCurrPage(currPage);
	
	
	List<AgentVolumeData> list = new ArrayList<AgentVolumeData>();
	
	list= transactionService.getMerchantVolumeByMID(merchantID,agent.getId());
	//String merchantName=null;
	List<String> date = new ArrayList<String>();
	if(list.size()==0) {
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("merchantName", merchantName);
		model.addAttribute("responseData", "Data not Available");
	}else {
		
		
		for(AgentVolumeData AgentVolumeData: list) {
			logger.info(AgentVolumeData.getMerchantName());
			merchantName=AgentVolumeData.getMerchantName();
			date.add(AgentVolumeData.getMonth());
			logger.info(AgentVolumeData.getMonth());
			logger.info(AgentVolumeData.getAmount1());
			logger.info(AgentVolumeData.getTxnType());
			model.addAttribute("paginationBean", paginationBean);
			model.addAttribute("merchantName", merchantName);
			
			model.addAttribute("dto", list);
			model.addAttribute("date", date);
		}

		List<String> newList=new ArrayList<String>();
		 for (String element : list.getDate()) { 
			  
	         // If this element is not present in newList 
	         // then add it 
	         if (!list.getDate().contains(element)) { 

	        	 newList.add(element); 
	         } 
	         list.setDate(newList);
	     } 
		
		//paginationBean.setItemList(listMerchantVolumeData);
		
	}
	
	//return "agent/merchantTransaction";
	
	model.addAttribute("hideMenu", true);
	
	return TEMPLATE_AGENT1;
}

*/

@RequestMapping(value={"/listHotelMerchantvolByAgent"},method=RequestMethod.GET)
public String listHotelMerchantVolumeByAg(final Model model,java.security.Principal principal)
{
	logger.info("about to list all merchants for this agent");
	PageBean pageBean = new PageBean("Agent", "hotelMerchants/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	//logger.info("about to list all agents");
	
	
	logger.info("Agent Portal Currently logged by "+ principal.getName());
			
	Agent agent=agentService.loadAgentbyMailId(principal.getName());
				
	model.addAttribute("pageBean", pageBean);
	
	logger.info("Agent name"+ agent.getFirstName());
	logger.info("Agent ID"+ agent.getId());

	List<AgentVolumeData> data = new ArrayList<AgentVolumeData>();
	
	PaginationBean<AgentVolumeData> paginationBean1 = new PaginationBean<AgentVolumeData>();
	paginationBean1.setCurrPage(1);
	
	List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
	
	List<MID> ids = new ArrayList<MID>();
    for(Merchant t: merchant1) {
    	ids.add(t.getMid());
    }
    
    logger.info("ids:  "+ids);
    
	StringBuffer str = new StringBuffer();
    StringBuffer strUm = new StringBuffer();
    List<String> midList = new ArrayList<String>();
    List<String> ummidList = new ArrayList<String>();
    for(MID mi : ids) {
 
    	if(mi.getUmMid() != null) {
    		if(!mi.getUmMid().isEmpty()) {
    			/*str.append("\"");
    			str.append(mi.getMotoMid());
    			str.append("\",");*/
    			midList.add(mi.getUmMid());
    		}
    		
    	}
    	
    	if(mi.getUmMotoMid() != null) {
    		if(!mi.getUmMotoMid().isEmpty()) {
    			/*str.append("\"");
    			str.append(mi.getMotoMid());
    			str.append("\",");*/
    			ummidList.add(mi.getUmMotoMid());
    		}
    		
    	}
    	if(mi.getUmEzywayMid() != null) {
    		if(!mi.getUmEzywayMid().isEmpty()) {
    			/*str.append("\"");
    			str.append(mi.getMotoMid());
    			str.append("\",");*/
    			ummidList.add(mi.getUmEzywayMid());
    		}
    		
    	}
    	
    }
    
    int v=0;
    for(String strMid : ummidList) {

    	if(v == 0) {
    		strUm.append("\"");
    		strUm.append(strMid);
    		strUm.append("\"");
			v++;
    	}else {		    	
    		strUm.append(",\"");
    		strUm.append(strMid);
    		strUm.append("\"");
    	}
    
    }
    
    logger.info("String of UMMIDs:  "+strUm);
    
    List<AgentVolumeData> listAgentVolumeData = new ArrayList<AgentVolumeData>();
	
    listAgentVolumeData = transactionService.getHotelMerchantVolByUMid(strUm);
	
	//listAVD = transactionService.getHotelMerchantVolByMid(String.valueOf(merchantid));
	logger.info("listAgentVolumeData"+listAgentVolumeData);
	
	//Method to get current and last three months name
			int cDate = 0;
			Date dt = new Date();
			cDate = dt.getMonth() + 1;
			List<Integer> listMonth = getAllMonth(cDate);
			List<String> date = new ArrayList<String>();
//			System.out.println("listMonth: "+listMonth.size());
			for (int i = 0 ; i < listMonth.size(); i++) {
				date.add(getMonth(cDate));
				cDate--;
			}
//			System.out.println("date: "+date);
			
			for (Merchant t : merchant1) {
				
				AgentVolumeData finalVolumeData = new AgentVolumeData();
				List<String> amountUM = new ArrayList<String>();

				List<String> amountFOR = new ArrayList<String>();
				int count = 0;
				int count1 = 0;
				
				for(int i=0 ; i < listAgentVolumeData.size();i++) {
				
					if(listAgentVolumeData.get(i).getAgId().equals(t.getId().toString())) {
						
						int date1 = Integer.parseInt(listAgentVolumeData.get(i).getMonth());
					while (listMonth.get(count).intValue() != date1) {
//							dateUM.add(getMonth(listMonth.get(count).intValue()));
							amountUM.add("0.00");
							count++;

						}// else{
//						dateUM.add(getMonth(date1));
						Double d = new Double(listAgentVolumeData.get(i).getAmount1());
						d = d / 100;

						String pattern = "#,##0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						String output = myFormatter.format(d);
						amountUM.add(output);
						count++;
						
						
					}
					
				}

				
				for (int y = amountUM.size() ;  y < 4 ; y++) {
					amountUM.add("0.00");
				}
				
				
				List<String> amountRev = new ArrayList<String>();
				for (int i = amountUM.size() - 1; i >= 0; i--) {
					amountRev.add(amountUM.get(i));
				}
				
				List<String> DateRev = new ArrayList<String>();
				for (int i = date.size() - 1; i >= 0; i--) {
					DateRev.add(date.get(i));
				}
				
				finalVolumeData.setAgId(t.getId().toString());
				finalVolumeData.setAgentName(t.getBusinessName());
				finalVolumeData.setAmount(amountRev);
				finalVolumeData.setDate(DateRev);

				data.add(finalVolumeData);
	
			}
	
	pageBean = new PageBean("Agent", "hotelMerchants/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	paginationBean1.setItemList(data);
	
	model.addAttribute("pageBean", pageBean);
	model.addAttribute("paginationBean", paginationBean1);
	model.addAttribute("merchant1", merchant1);
	model.addAttribute("agent", agent);

	logger.info("listMerchantVolumeSummary completed");

	return TEMPLATE_HOTELMERCHANT;
	
}

@RequestMapping(value={"/listHotelMerchantvolume"},method=RequestMethod.GET)
public String listHotelMerchantVolumeSummary(final Model model,java.security.Principal principal)
{
	logger.info("about to list all merchants for this agent");
	PageBean pageBean = new PageBean("Agent", "hotelMerchants/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	//logger.info("about to list all agents");
	
	
	logger.info("Agent Portal Currently logged by "+ principal.getName());
			
	Agent agent=agentService.loadAgentbyMailId(principal.getName());
				
	model.addAttribute("pageBean", pageBean);
	
	logger.info("Agent name"+ agent.getFirstName());
	logger.info("Agent ID"+ agent.getId());

	
	BigInteger agid = BigInteger.valueOf(agent.getId()); 

	
	List<Merchant> merchant1 = transactionService.getMerchantDataByAgent(agid);
	Set<String> merchantNameList = new HashSet<String>();
	for (Merchant t : merchant1) {
		String businessName = t.getBusinessName();
		String email = t.getUsername();
		merchantNameList.add(businessName.toString() + "~" + email);
	}
	logger.info("merchantNameList"+merchantNameList);
	
	pageBean = new PageBean("Agent", "hotelMerchants/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	model.addAttribute("pageBean", pageBean);
	model.addAttribute("merchantNameList", merchantNameList);
	model.addAttribute("merchant1", merchant1);
	model.addAttribute("agent", agent);

	logger.info("listMerchantVolumeSummary completed");

	return TEMPLATE_HOTELMERCHANT;
	
}

@RequestMapping(value={"/listHotelMerchantvolumesummary"},method=RequestMethod.GET)
public String HotelMerchantVolumeSummary(final Model model,
		@RequestParam("id") Long merchantid,
		java.security.Principal principal)
{
	logger.info("listHotelMerchantvolumesummary for this agent: "+merchantid);
	
	PageBean pageBean = new PageBean("Agent", "hotelMerchants/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	List<AgentVolumeData> data = new ArrayList<AgentVolumeData>();
	
	PaginationBean<AgentVolumeData> paginationBean1 = new PaginationBean<AgentVolumeData>();
	paginationBean1.setCurrPage(1);
	
	
	Agent agent=agentService.loadAgentbyMailId(principal.getName());
	BigInteger agid = BigInteger.valueOf(agent.getId()); 

	
	List<Merchant> merchant1 = transactionService.getMerchantDataByAgent(agid);
	Set<String> merchantNameList = new HashSet<String>();
	for (Merchant t : merchant1) {
		String businessName = t.getBusinessName();
		String email = t.getUsername();
		merchantNameList.add(businessName.toString() + "~" + email);
	}
	
	List<AgentVolumeData> listAVD = new ArrayList<AgentVolumeData>();
	
	listAVD = transactionService.getHotelMerchantVolByMid(String.valueOf(merchantid));
	logger.info("listAVD: "+listAVD);
	

	for(AgentVolumeData merchantVolumeData: listAVD){

		data.add(merchantVolumeData);
	
	}
	
	/*List<AgentVolumeData> listAVD = new ArrayList<AgentVolumeData>();
	listAVD = transactionService.getMerchantVolByMid(String.valueOf(merchantid));
	logger.info("listAVD: "+listAVD);
	
	List<AgentVolumeData> listAVD1 = new ArrayList<AgentVolumeData>();
	listAVD1 = transactionService.getMerchantVolByMid1(String.valueOf(merchantid));
	logger.info("listAVD1: "+listAVD1);
	
	
	//forSettlement Amount String List to Long list
	List<String> forStringAmount = listAVD.get(0).getAmount();
		logger.info("List forTxn String Amount ::"+forStringAmount);
		logger.info("List forTxn String Amount Size ::"+forStringAmount.size());
	List<Double> forLongAmount = new ArrayList<Double>(forStringAmount.size());
	for(String s : forStringAmount) 
		forLongAmount.add(Double.parseDouble(s));
		logger.info("List forTxn Long Amount ::"+forLongAmount);
		logger.info("List forTxn Long Amount Size ::"+forLongAmount.size());
	
	//UM Amount String  List to Long list
	List<String> forStringAmountUM = listAVD1.get(0).getAmount();
		logger.info("List UMTxn String Amount ::"+forStringAmountUM);
		logger.info("List UMTxn String Amount Size ::"+forStringAmountUM.size());
	List<Double> forLongAmountUM = new ArrayList<Double>(forStringAmountUM.size());
	for(String s : forStringAmountUM) 
		forLongAmountUM.add(Double.parseDouble(s));
		logger.info("List UMTxn Long Amount ::"+forLongAmountUM);
		logger.info("List UMTxn Long Amount Size ::"+forLongAmountUM.size());
	
    //Add two long list value and convert string list
		
		int len = 0;
		if(forLongAmountUM.size() > forLongAmount.size()) {
			len = forLongAmountUM.size();
		}else {
			len = forLongAmount.size();
		}
		
		List<Double> newAmount = new ArrayList<Double>(len);
		for (int i =0; i< len ; i++) {
			Double Amount = forLongAmount.get(i) + forLongAmountUM.get(i);
			logger.info("List Amount ::"+Amount);
			newAmount.add(Amount);
		}
		
		logger.info("List newAmount Long Amount ::"+newAmount);
		logger.info("List newAmount Long Amount Size ::"+newAmount.size());
		
		
		List<String> newAmountString = new ArrayList<String>(newAmount.size());
		for(Double s : newAmount) 
			newAmountString.add(String.valueOf(s));
			logger.info("List newAmount String Amount ::"+newAmountString);
			logger.info("List newAmount String Amount Size ::"+newAmountString.size());
	
//}
		listAVD.get(0).setAmount(newAmountString);
	

	for(AgentVolumeData merchantVolumeData: listAVD){

		data.add(merchantVolumeData);
	
	}
	*/
	
	paginationBean1.setItemList(data);
	
	model.addAttribute("pageBean", pageBean);
	model.addAttribute("paginationBean", paginationBean1);
	model.addAttribute("merchantNameList", merchantNameList);
	model.addAttribute("merchant1", merchant1);


	return TEMPLATE_HOTELMERCHANT;
	
}

private static List<Integer> getAllMonth(int month) {
	System.out.println("month debug... "+month);
	List<Integer> listMonth = new ArrayList<Integer>();
	for (int i = 0; i <4 ; i++) {
		if (month == 0) {
			System.out.println("debug 0");
			listMonth.add(12);
		} else if (month == -1) {
			System.out.println("debug -1");
			listMonth.add(11);
		} else if (month == -2) {
			System.out.println("debug -2");
			listMonth.add(10);
		} else {
			System.out.println("debug else ");
			listMonth.add(month);
		}
		month--;
	}
	return listMonth;
}


@RequestMapping(value={"/listMerchantGPV"},method=RequestMethod.GET)
public String listMerchantGPV(final Model model,java.security.Principal principal)
{
	logger.info("about to list all merchants for this agent");
	PageBean pageBean = new PageBean("Agent", "agent/MerchantGPVByAgent", Module.AGENT,
			"agent/sideMenuAgent");
	//logger.info("about to list all agents");
	
	
	logger.info("Agent Portal Currently logged by "+ principal.getName());
			
	Agent agent=agentService.loadAgentbyMailId(principal.getName());
				
	model.addAttribute("pageBean", pageBean);
	
	logger.info("Agent name"+ agent.getFirstName());
	logger.info("Agent ID"+ agent.getId());

	BigInteger agid = BigInteger.valueOf(agent.getId()); 

	//merchantService.listMerchantGPVDetails(paginationBean);
	String agentId = agent.getId().toString(); 
	List<MerchantGPVData> merchantsGPV =  merchantService.listMerchantGPVDetailsByAgent(agentId);
	
	logger.info("merchantsGPV::"+ merchantsGPV);
	//List<Merchant> merchantsGPV1 =  merchantsGPV;
	

	List<MerchantGPVData> merchantsFinalGPV = new ArrayList<MerchantGPVData>();
	
	//new change
	
	List<String> MerchantId=new ArrayList<String>();
	
	for(int i=0 ; i < merchantsGPV.size();i++) {
		MerchantId.add(merchantsGPV.get(i).getMerchantId()+"~"+merchantsGPV.get(i).getMonthName());
	}
	
	 Map<String, Integer> nameAndCount = new HashMap<>();

     // build hash table with count
     for (String name : MerchantId) {
    	 logger.info("hashmap::"+ name);
         Integer count = nameAndCount.get(name);
         if (count == null) {
             nameAndCount.put(name, 1);
         } else {
             nameAndCount.put(name, ++count);
         }
     }
     MerchantGPVData finalMerchant = new MerchantGPVData();
    
     for(int i=0 ; i < merchantsGPV.size();i++) {

    	 if (nameAndCount.get(merchantsGPV.get(i).getMerchantId()+"~"+merchantsGPV.get(i).getMonthName()) > 1) {
        	 
        	 if((!merchantsGPV.get(i).getMerchantId().equals(finalMerchant.getMerchantId()) 
        			 ||  !merchantsGPV.get(i).getMonthName().equals(finalMerchant.getMonthName())
        			 )
        			 && finalMerchant.getMerchantId()!=null){	
        		 
        		 merchantsFinalGPV.add(finalMerchant);
        		 finalMerchant = new MerchantGPVData();
        	 }
				finalMerchant.setMerchantName(merchantsGPV.get(i).getMerchantName());
				finalMerchant.setAgentName(merchantsGPV.get(i).getAgentName());
				finalMerchant.setMerchantId(merchantsGPV.get(i).getMerchantId());
				finalMerchant.setMonthName(merchantsGPV.get(i).getMonthName());
				if(merchantsGPV.get(i).getMerchantType().equals("P")) {
					finalMerchant.setMerchantType("Paydee");
				}else if(merchantsGPV.get(i).getMerchantType().equals("FIUU")) {
					finalMerchant.setMerchantType("FIUU");
				}else {
					finalMerchant.setMerchantType("Umobile");
				}
				
				//EZYWAY
				if(merchantsGPV.get(i).getIsEzyway() != null) {
					
					logger.info("EZYWAY  "+merchantsGPV.get(i).getIsEzyway());
					
					finalMerchant.setIsEzyway(merchantsGPV.get(i).getIsEzyway());
					finalMerchant.setEzywayAmt(merchantsGPV.get(i).getEzywayAmt());
				}

				//EZYLINK
				if(merchantsGPV.get(i).getIsEzylink() != null) {
					
					logger.info("EZYLINK  "+merchantsGPV.get(i).getIsEzylink());
					finalMerchant.setIsEzylink(merchantsGPV.get(i).getIsEzylink());
					finalMerchant.setEzylinkAmt(merchantsGPV.get(i).getEzylinkAmt());
				}

				
				//EZYREC
				if(merchantsGPV.get(i).getIsEzyrec() != null || merchantsGPV.get(i).getIsRecurring() != null) {
					
					if(merchantsGPV.get(i).getRecurringAmt() == null) {
						merchantsGPV.get(i).setRecurringAmt("0");
					}
					if(merchantsGPV.get(i).getEzyrecAmt() == null) {
						merchantsGPV.get(i).setEzyrecAmt("0");
					}
					Double tempamt=Double.parseDouble(merchantsGPV.get(i).getEzyrecAmt())+ 
							Double.parseDouble(merchantsGPV.get(i).getRecurringAmt());
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(tempamt);
					finalMerchant.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
					finalMerchant.setEzyrecAmt(Txn);
				}

				//EZYMOTO
				if(merchantsGPV.get(i).getIsEzymoto() != null) {
					
					logger.info("EZYMOTO"+merchantsGPV.get(i).getIsEzymoto());
					finalMerchant.setIsEzymoto(merchantsGPV.get(i).getIsEzymoto());
					finalMerchant.setEzymotoAmt(merchantsGPV.get(i).getEzymotoAmt());
				}

				//EZYWIRE
				if(merchantsGPV.get(i).getIsEzywire() != null) {
					
					finalMerchant.setIsEzywire(merchantsGPV.get(i).getIsEzywire());
					finalMerchant.setEzywireAmt(merchantsGPV.get(i).getEzywireAmt());
				}

				
				//RECURRING
				/*if(merchantsGPV.get(i).getIsRecurring() != null) {
					
					finalMerchant.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
					finalMerchant.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
				}*/

				
				//RECPLUS
				if(merchantsGPV.get(i).getIsRecplus() != null) {
					
					finalMerchant.setIsRecplus(merchantsGPV.get(i).getIsRecplus());
					finalMerchant.setRecplusAmt(merchantsGPV.get(i).getRecplusAmt());
				}

				//EZYMOTO-VCC
				if(merchantsGPV.get(i).getIsEzymotoVcc() != null) {
					
					finalMerchant.setIsEzymotoVcc(merchantsGPV.get(i).getIsEzymotoVcc());
					finalMerchant.setEzymotoVccAmt(merchantsGPV.get(i).getEzymotoVccAmt());
				}

				//EZYAUTH
				if(merchantsGPV.get(i).getIsEzyauth() != null) {
					
					finalMerchant.setIsEzyauth(merchantsGPV.get(i).getIsEzyauth());
					finalMerchant.setEzyauthAmt(merchantsGPV.get(i).getEzyauthAmt());
				}

				logger.info("finalMerchant:"+ finalMerchant);
         }
         
         else{
				logger.info("else condition:"+ merchantsGPV.get(i).getMerchantName());
				MerchantGPVData finalMerchant1 = new MerchantGPVData();
				finalMerchant1.setMerchantName(merchantsGPV.get(i).getMerchantName());
				finalMerchant1.setAgentName(merchantsGPV.get(i).getAgentName());
				finalMerchant1.setMerchantId(merchantsGPV.get(i).getMerchantId());
				finalMerchant1.setMonthName(merchantsGPV.get(i).getMonthName());
				finalMerchant1.setProductType(merchantsGPV.get(i).getProductType());
				finalMerchant1.setIsEzywire(merchantsGPV.get(i).getIsEzywire());
				finalMerchant1.setIsEzymoto(merchantsGPV.get(i).getIsEzymoto());
				finalMerchant1.setIsEzyway(merchantsGPV.get(i).getIsEzyway());
				finalMerchant1.setIsEzylink(merchantsGPV.get(i).getIsEzylink());
				finalMerchant1.setIsEzymotoVcc(merchantsGPV.get(i).getIsEzymotoVcc());
				finalMerchant1.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
				finalMerchant1.setIsEzyauth(merchantsGPV.get(i).getIsEzyauth());
				finalMerchant1.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
				finalMerchant1.setIsRecplus(merchantsGPV.get(i).getIsRecplus());
				finalMerchant1.setEzywireAmt(merchantsGPV.get(i).getEzywireAmt());
				finalMerchant1.setEzymotoAmt(merchantsGPV.get(i).getEzymotoAmt());
				finalMerchant1.setEzywayAmt(merchantsGPV.get(i).getEzywayAmt());
				//finalMerchant1.setEzyrecAmt(merchantsGPV.get(i).getEzyrecAmt());
				finalMerchant1.setEzymotoVccAmt(merchantsGPV.get(i).getEzymotoVccAmt());
				finalMerchant1.setEzylinkAmt(merchantsGPV.get(i).getEzylinkAmt());
				finalMerchant1.setEzyauthAmt(merchantsGPV.get(i).getEzyauthAmt());
				//finalMerchant1.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
				finalMerchant1.setRecplusAmt(merchantsGPV.get(i).getRecplusAmt());
				if(merchantsGPV.get(i).getIsEzyrec() != null || merchantsGPV.get(i).getIsRecurring() != null) {
					
					if(merchantsGPV.get(i).getRecurringAmt() == null) {
						merchantsGPV.get(i).setRecurringAmt("0");
					}
					if(merchantsGPV.get(i).getEzyrecAmt() == null) {
						merchantsGPV.get(i).setEzyrecAmt("0");
					}
					Double tempamt=Double.parseDouble(merchantsGPV.get(i).getEzyrecAmt())+ 
							Double.parseDouble(merchantsGPV.get(i).getRecurringAmt());
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(tempamt);
					finalMerchant1.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
					finalMerchant1.setEzyrecAmt(Txn);
				}
				
				if(merchantsGPV.get(i).getMerchantType().equals("P")) {
					finalMerchant1.setMerchantType("Paydee");
				}else if(merchantsGPV.get(i).getMerchantType().equals("FIUU")) {
					finalMerchant1.setMerchantType("FIUU");
				}else {
					finalMerchant1.setMerchantType("Umobile");
				}
				
				merchantsFinalGPV.add(finalMerchant1);
				
			}
     } 
    

	merchantsFinalGPV.add(finalMerchant);
	
	logger.info("merchantsFinalGPV:"+ merchantsFinalGPV);
	int cDate = 0;
	Date dt = new Date();
	cDate = dt.getMonth() + 1;

	System.out.println("cDate: "+cDate);
	
	List<Integer> listMonth = getAllMonth(cDate);
	logger.info("listMonth::"+ listMonth);
	List<MerchantGPVData> fourthMonList = new ArrayList<MerchantGPVData>();
	List<MerchantGPVData> ThirdMonList = new ArrayList<MerchantGPVData>();
	List<MerchantGPVData> SecondMonList = new ArrayList<MerchantGPVData>();
	List<MerchantGPVData> FirstMonList = new ArrayList<MerchantGPVData>();
	
	
	for(MerchantGPVData merchant1 : merchantsFinalGPV) {
		Double total = 0.00;
		Integer month = Integer.parseInt(merchant1.getMonthName());
		logger.info("month::"+ month);
		if(merchant1.getEzymotoAmt() != null) {
			Double d = Double.parseDouble(merchant1.getEzymotoAmt().replace(",", ""));
			total = total+d;
		}
		
		if(merchant1.getEzywayAmt() != null) {
			Double d = Double.parseDouble(merchant1.getEzywayAmt().replace(",", ""));
			total = total+d;
		}
		
		if(merchant1.getEzywireAmt() != null) {
			Double d = Double.parseDouble(merchant1.getEzywireAmt().replace(",", ""));
			total = total+d;
		}
		
		if(merchant1.getEzymotoVccAmt() != null) {
			Double d = Double.parseDouble(merchant1.getEzymotoVccAmt().replace(",", ""));
			total = total+d;
		}
		
		if(merchant1.getEzyrecAmt() != null) {
			Double d = Double.parseDouble(merchant1.getEzyrecAmt().replace(",", ""));
			total = total+d;
		}
		
		if(merchant1.getRecplusAmt() != null) {
			Double d = Double.parseDouble(merchant1.getRecplusAmt().replace(",", ""));
			total = total+d;
		}
		
		if(merchant1.getEzylinkAmt() != null) {
			Double d = Double.parseDouble(merchant1.getEzylinkAmt().replace(",", ""));
			total = total+d;
		}
		
		if(total != null) {
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String Txn = myFormatter.format(total);
			merchant1.setTotalGpv(Txn);
		}
		
		
		
		if(month == listMonth.get(0)) {
			
			fourthMonList.add(merchant1);
			logger.info("fourthMonList::"+ fourthMonList);
		}
		if(month == listMonth.get(1)) {
			
			ThirdMonList.add(merchant1);
			
			logger.info("ThirdMonList::"+ ThirdMonList);
		}
		if(month == listMonth.get(2)) {
			
			SecondMonList.add(merchant1);
			logger.info("SecondMonList::"+ SecondMonList);
		}
		if(month == listMonth.get(3)) {
			
			FirstMonList.add(merchant1);
			logger.info("FirstMonList"+ FirstMonList);
		}
		
		
	}
	
	
	
	
	
	model.addAttribute("pageBean", pageBean);
	model.addAttribute("agent", agent);
	model.addAttribute("fourthMonList", fourthMonList);
	model.addAttribute("ThirdMonList", ThirdMonList);
	model.addAttribute("SecondMonList", SecondMonList);
	model.addAttribute("FirstMonList",FirstMonList);
	logger.info("listMerchantVolumeSummary completed");

	return TEMPLATE_AGENT;
	
}


@RequestMapping(value = { "/merchantGPVExport" }, method = RequestMethod.GET)
public ModelAndView merchantGPVExport(final Model model,HttpServletRequest request,
		java.security.Principal principal,
		@RequestParam("month") String id,
		@RequestParam(required = false) String export) {
	
	logger.info("about to export all merchants for this agent");
	Agent agent=agentService.loadAgentbyMailId(principal.getName());
	

	logger.info("Agent name"+ agent.getFirstName());
	logger.info("Agent ID"+ agent.getId());

	BigInteger agid = BigInteger.valueOf(agent.getId()); 

	//merchantService.listMerchantGPVDetails(paginationBean);
	String agentId = agent.getId().toString(); 
	List<MerchantGPVData> merchantsGPV =  merchantService.listMerchantGPVDetailsByAgent(agentId);
	
	logger.info("merchantsGPV::"+ merchantsGPV);
	//List<Merchant> merchantsGPV1 =  merchantsGPV;
	

	List<MerchantGPVData> merchantsFinalGPV = new ArrayList<MerchantGPVData>();
	
	//new change
	
	List<String> MerchantId=new ArrayList<String>();
	
	for(int i=0 ; i < merchantsGPV.size();i++) {
		MerchantId.add(merchantsGPV.get(i).getMerchantId()+"~"+merchantsGPV.get(i).getMonthName());
	}
	
	 Map<String, Integer> nameAndCount = new HashMap<>();

     // build hash table with count
     for (String name : MerchantId) {
    	 logger.info("hashmap::"+ name);
         Integer count = nameAndCount.get(name);
         if (count == null) {
             nameAndCount.put(name, 1);
         } else {
             nameAndCount.put(name, ++count);
         }
     }
     MerchantGPVData finalMerchant = new MerchantGPVData();
    
     for(int i=0 ; i < merchantsGPV.size();i++) {

    	 if (nameAndCount.get(merchantsGPV.get(i).getMerchantId()+"~"+merchantsGPV.get(i).getMonthName()) > 1) {
        	 
        	 if((!merchantsGPV.get(i).getMerchantId().equals(finalMerchant.getMerchantId()) 
        			 ||  !merchantsGPV.get(i).getMonthName().equals(finalMerchant.getMonthName())
        			 )
        			 && finalMerchant.getMerchantId()!=null){	
        		 
        		 merchantsFinalGPV.add(finalMerchant);
        		 finalMerchant = new MerchantGPVData();
        	 }
				finalMerchant.setMerchantName(merchantsGPV.get(i).getMerchantName());
				finalMerchant.setAgentName(merchantsGPV.get(i).getAgentName());
				finalMerchant.setMerchantId(merchantsGPV.get(i).getMerchantId());
				finalMerchant.setMonthName(merchantsGPV.get(i).getMonthName());
				if(merchantsGPV.get(i).getMerchantType().equals("P")) {
					finalMerchant.setMerchantType("Paydee");
				}else if(merchantsGPV.get(i).getMerchantType().equals("FIUU")) {
					finalMerchant.setMerchantType("FIUU");
				}else {
					finalMerchant.setMerchantType("Umobile");
				}
				
				//EZYWAY
				if(merchantsGPV.get(i).getIsEzyway() != null) {
					
					logger.info("EZYWAY  "+merchantsGPV.get(i).getIsEzyway());
					
					finalMerchant.setIsEzyway(merchantsGPV.get(i).getIsEzyway());
					finalMerchant.setEzywayAmt(merchantsGPV.get(i).getEzywayAmt());
				}

				//EZYLINK
				if(merchantsGPV.get(i).getIsEzylink() != null) {
					
					logger.info("EZYLINK  "+merchantsGPV.get(i).getIsEzylink());
					finalMerchant.setIsEzylink(merchantsGPV.get(i).getIsEzylink());
					finalMerchant.setEzylinkAmt(merchantsGPV.get(i).getEzylinkAmt());
				}

				
				//EZYREC
				if(merchantsGPV.get(i).getIsEzyrec() != null || merchantsGPV.get(i).getIsRecurring() != null) {
					
					if(merchantsGPV.get(i).getRecurringAmt() == null) {
						merchantsGPV.get(i).setRecurringAmt("0");
					}
					if(merchantsGPV.get(i).getEzyrecAmt() == null) {
						merchantsGPV.get(i).setEzyrecAmt("0");
					}
					Double tempamt=Double.parseDouble(merchantsGPV.get(i).getEzyrecAmt())+ 
							Double.parseDouble(merchantsGPV.get(i).getRecurringAmt());
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(tempamt);
					finalMerchant.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
					finalMerchant.setEzyrecAmt(Txn);
				}

				//EZYMOTO
				if(merchantsGPV.get(i).getIsEzymoto() != null) {
					
					logger.info("EZYMOTO"+merchantsGPV.get(i).getIsEzymoto());
					finalMerchant.setIsEzymoto(merchantsGPV.get(i).getIsEzymoto());
					finalMerchant.setEzymotoAmt(merchantsGPV.get(i).getEzymotoAmt());
				}

				//EZYWIRE
				if(merchantsGPV.get(i).getIsEzywire() != null) {
					
					finalMerchant.setIsEzywire(merchantsGPV.get(i).getIsEzywire());
					finalMerchant.setEzywireAmt(merchantsGPV.get(i).getEzywireAmt());
				}

				
				//RECURRING
				/*if(merchantsGPV.get(i).getIsRecurring() != null) {
					
					finalMerchant.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
					finalMerchant.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
				}*/

				
				//RECPLUS
				if(merchantsGPV.get(i).getIsRecplus() != null) {
					
					finalMerchant.setIsRecplus(merchantsGPV.get(i).getIsRecplus());
					finalMerchant.setRecplusAmt(merchantsGPV.get(i).getRecplusAmt());
				}

				//EZYMOTO-VCC
				if(merchantsGPV.get(i).getIsEzymotoVcc() != null) {
					
					finalMerchant.setIsEzymotoVcc(merchantsGPV.get(i).getIsEzymotoVcc());
					finalMerchant.setEzymotoVccAmt(merchantsGPV.get(i).getEzymotoVccAmt());
				}

				//EZYAUTH
				if(merchantsGPV.get(i).getIsEzyauth() != null) {
					
					finalMerchant.setIsEzyauth(merchantsGPV.get(i).getIsEzyauth());
					finalMerchant.setEzyauthAmt(merchantsGPV.get(i).getEzyauthAmt());
				}

				logger.info("finalMerchant:"+ finalMerchant);
         }
         
         else{
				logger.info("else condition:"+ merchantsGPV.get(i).getMerchantName());
				MerchantGPVData finalMerchant1 = new MerchantGPVData();
				finalMerchant1.setMerchantName(merchantsGPV.get(i).getMerchantName());
				finalMerchant1.setAgentName(merchantsGPV.get(i).getAgentName());
				finalMerchant1.setMerchantId(merchantsGPV.get(i).getMerchantId());
				finalMerchant1.setMonthName(merchantsGPV.get(i).getMonthName());
				finalMerchant1.setProductType(merchantsGPV.get(i).getProductType());
				finalMerchant1.setIsEzywire(merchantsGPV.get(i).getIsEzywire());
				finalMerchant1.setIsEzymoto(merchantsGPV.get(i).getIsEzymoto());
				finalMerchant1.setIsEzyway(merchantsGPV.get(i).getIsEzyway());
				finalMerchant1.setIsEzylink(merchantsGPV.get(i).getIsEzylink());
				finalMerchant1.setIsEzymotoVcc(merchantsGPV.get(i).getIsEzymotoVcc());
				finalMerchant1.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
				finalMerchant1.setIsEzyauth(merchantsGPV.get(i).getIsEzyauth());
				finalMerchant1.setIsRecurring(merchantsGPV.get(i).getIsRecurring());
				finalMerchant1.setIsRecplus(merchantsGPV.get(i).getIsRecplus());
				finalMerchant1.setEzywireAmt(merchantsGPV.get(i).getEzywireAmt());
				finalMerchant1.setEzymotoAmt(merchantsGPV.get(i).getEzymotoAmt());
				finalMerchant1.setEzywayAmt(merchantsGPV.get(i).getEzywayAmt());
				//finalMerchant1.setEzyrecAmt(merchantsGPV.get(i).getEzyrecAmt());
				finalMerchant1.setEzymotoVccAmt(merchantsGPV.get(i).getEzymotoVccAmt());
				finalMerchant1.setEzylinkAmt(merchantsGPV.get(i).getEzylinkAmt());
				finalMerchant1.setEzyauthAmt(merchantsGPV.get(i).getEzyauthAmt());
				//finalMerchant1.setRecurringAmt(merchantsGPV.get(i).getRecurringAmt());
				finalMerchant1.setRecplusAmt(merchantsGPV.get(i).getRecplusAmt());
				if(merchantsGPV.get(i).getIsEzyrec() != null || merchantsGPV.get(i).getIsRecurring() != null) {
					
					if(merchantsGPV.get(i).getRecurringAmt() == null) {
						merchantsGPV.get(i).setRecurringAmt("0");
					}
					if(merchantsGPV.get(i).getEzyrecAmt() == null) {
						merchantsGPV.get(i).setEzyrecAmt("0");
					}
					Double tempamt=Double.parseDouble(merchantsGPV.get(i).getEzyrecAmt())+ 
							Double.parseDouble(merchantsGPV.get(i).getRecurringAmt());
					String pattern = "#,##0.00";
					DecimalFormat myFormatter = new DecimalFormat(pattern);
					String Txn = myFormatter.format(tempamt);
					finalMerchant1.setIsEzyrec(merchantsGPV.get(i).getIsEzyrec());
					finalMerchant1.setEzyrecAmt(Txn);
				}
				
				if(merchantsGPV.get(i).getMerchantType().equals("P")) {
					finalMerchant1.setMerchantType("Paydee");
				}else if(merchantsGPV.get(i).getMerchantType().equals("FIUU")) {
					finalMerchant1.setMerchantType("FIUU");
				}else {
					finalMerchant1.setMerchantType("Umobile");
				}
				finalMerchant1.setTotalGpv(merchantsGPV.get(i).getTotalGpv());
				merchantsFinalGPV.add(finalMerchant1);
				
			}
     } 
    

	merchantsFinalGPV.add(finalMerchant);
	
	logger.info("merchantsFinalGPV:"+ merchantsFinalGPV);
	int cDate = 0;
	Date dt = new Date();
	cDate = dt.getMonth() + 1;

	System.out.println("cDate: "+cDate);
	
	List<Integer> listMonth = getAllMonth(cDate);
	logger.info("listMonth::"+ listMonth);
	List<MerchantGPVData> fourthMonList = new ArrayList<MerchantGPVData>();
	List<MerchantGPVData> ThirdMonList = new ArrayList<MerchantGPVData>();
	List<MerchantGPVData> SecondMonList = new ArrayList<MerchantGPVData>();
	List<MerchantGPVData> FirstMonList = new ArrayList<MerchantGPVData>();
	
	
	List<MerchantGPVData> FinalMonListData = new ArrayList<MerchantGPVData>();
	
	for(MerchantGPVData merchant1 : merchantsFinalGPV) {
		Double total = 0.00;
		Integer month = Integer.parseInt(merchant1.getMonthName());
		logger.info("month::"+ month);
		if(merchant1.getEzymotoAmt() != null) {
			Double d = Double.parseDouble(merchant1.getEzymotoAmt().replace(",", ""));
			total = total+d;
		}
		
		if(merchant1.getEzywayAmt() != null) {
			Double d = Double.parseDouble(merchant1.getEzywayAmt().replace(",", ""));
			total = total+d;
		}
		
		if(merchant1.getEzywireAmt() != null) {
			Double d = Double.parseDouble(merchant1.getEzywireAmt().replace(",", ""));
			total = total+d;
		}
		
		if(merchant1.getEzymotoVccAmt() != null) {
			Double d = Double.parseDouble(merchant1.getEzymotoVccAmt().replace(",", ""));
			total = total+d;
		}
		
		if(merchant1.getEzyrecAmt() != null) {
			Double d = Double.parseDouble(merchant1.getEzyrecAmt().replace(",", ""));
			total = total+d;
		}
		
		if(merchant1.getRecplusAmt() != null) {
			Double d = Double.parseDouble(merchant1.getRecplusAmt().replace(",", ""));
			total = total+d;
		}
		
		if(merchant1.getEzylinkAmt() != null) {
			Double d = Double.parseDouble(merchant1.getEzylinkAmt().replace(",", ""));
			total = total+d;
		}
		
		if(total != null) {
			String pattern = "#,##0.00";
			DecimalFormat myFormatter = new DecimalFormat(pattern);
			String Txn = myFormatter.format(total);
			merchant1.setTotalGpv(Txn);
		}
		
		
		
		if(id.equals("fourthMon")) {			
			if(month == listMonth.get(0)) {
				
				fourthMonList.add(merchant1);
				logger.info("fourthMonList::"+ fourthMonList);
				FinalMonListData = fourthMonList;
			}
		}
		if(id.equals("thirdMon")) {		
			if(month == listMonth.get(1)) {
				
				ThirdMonList.add(merchant1);					
				logger.info("ThirdMonList::"+ ThirdMonList);
				FinalMonListData = ThirdMonList;
			}
		}
		if(id.equals("secondMon")) {		
			if(month == listMonth.get(2)) {
				
				SecondMonList.add(merchant1);
				logger.info("SecondMonList::"+ SecondMonList);
				FinalMonListData = SecondMonList;
			}
		}
		if(id.equals("firstMon")) {	
			if(month == listMonth.get(3)) {
				
				FirstMonList.add(merchant1);
				logger.info("FirstMonList"+ FirstMonList);
				FinalMonListData = FirstMonList;
			}
		}
		
	}
	


	if (!(export.equals("PDF"))) {
		logger.info("Generate Excel");
		return new ModelAndView("merchantGpvAgentExcel1", "merchantGPVList", FinalMonListData);
	} else {
		logger.info("Generate PDF");
		return new ModelAndView("merchantGpvAgentPdf1", "merchantGPVList", FinalMonListData);
	}
}

@RequestMapping(value={"/listToypayMerchantvolByAgent"},method=RequestMethod.GET)
public String listToypayMerchantVolumeByAg(final Model model,java.security.Principal principal)
{
	logger.info("about to list all merchants for this agent");
	PageBean pageBean = new PageBean("Agent", "toyPayMerchants/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	//logger.info("about to list all agents");
	
	
	logger.info("Agent Portal Currently logged by "+ principal.getName());
			
	Agent agent=agentService.loadAgentbyMailId(principal.getName());
				
	model.addAttribute("pageBean", pageBean);
	
	logger.info("Agent name"+ agent.getFirstName());
	logger.info("Agent ID"+ agent.getId());

	List<AgentVolumeData> data = new ArrayList<AgentVolumeData>();
	
	PaginationBean<AgentVolumeData> paginationBean1 = new PaginationBean<AgentVolumeData>();
	paginationBean1.setCurrPage(1);
	
	List<Merchant> merchant1 = merchantService.loadMerchantByAgID(agent.getId());
	
	List<MID> ids = new ArrayList<MID>();
    for(Merchant t: merchant1) {
    	ids.add(t.getMid());
    }
    
    logger.info("ids:  "+ids);
    
	StringBuffer str = new StringBuffer();
    StringBuffer strUm = new StringBuffer();
    List<String> midList = new ArrayList<String>();
    List<String> ummidList = new ArrayList<String>();
    for(MID mi : ids) {
 
    	if(mi.getUmMid() != null) {
    		if(!mi.getUmMid().isEmpty()) {
    			/*str.append("\"");
    			str.append(mi.getMotoMid());
    			str.append("\",");*/
    			midList.add(mi.getUmMid());
    		}
    		
    	}
    	
    	if(mi.getUmMotoMid() != null) {
    		if(!mi.getUmMotoMid().isEmpty()) {
    			/*str.append("\"");
    			str.append(mi.getMotoMid());
    			str.append("\",");*/
    			ummidList.add(mi.getUmMotoMid());
    		}
    		
    	}
    	if(mi.getUmEzywayMid() != null) {
    		if(!mi.getUmEzywayMid().isEmpty()) {
    			/*str.append("\"");
    			str.append(mi.getMotoMid());
    			str.append("\",");*/
    			ummidList.add(mi.getUmEzywayMid());
    		}
    		
    	}
    	
    }
    
    int v=0;
    for(String strMid : ummidList) {

    	if(v == 0) {
    		strUm.append("\"");
    		strUm.append(strMid);
    		strUm.append("\"");
			v++;
    	}else {		    	
    		strUm.append(",\"");
    		strUm.append(strMid);
    		strUm.append("\"");
    	}
    
    }
    
    logger.info("String of UMMIDs:  "+strUm);
    
    List<AgentVolumeData> listAgentVolumeData = new ArrayList<AgentVolumeData>();
	
    listAgentVolumeData = transactionService.getHotelMerchantVolByUMid(strUm);
	
	//listAVD = transactionService.getHotelMerchantVolByMid(String.valueOf(merchantid));
	logger.info("listAgentVolumeData"+listAgentVolumeData);
	
	//Method to get current and last three months name
			int cDate = 0;
			Date dt = new Date();
			cDate = dt.getMonth() + 1;
			List<Integer> listMonth = getAllMonth(cDate);
			List<String> date = new ArrayList<String>();
//			System.out.println("listMonth: "+listMonth.size());
			for (int i = 0 ; i < listMonth.size(); i++) {
				date.add(getMonth(cDate));
				cDate--;
			}
//			System.out.println("date: "+date);
			
			for (Merchant t : merchant1) {
				
				AgentVolumeData finalVolumeData = new AgentVolumeData();
				List<String> amountUM = new ArrayList<String>();

				List<String> amountFOR = new ArrayList<String>();
				int count = 0;
				int count1 = 0;
				
				for(int i=0 ; i < listAgentVolumeData.size();i++) {
				
					if(listAgentVolumeData.get(i).getAgId().equals(t.getId().toString())) {
						
						int date1 = Integer.parseInt(listAgentVolumeData.get(i).getMonth());
					while (listMonth.get(count).intValue() != date1) {
//							dateUM.add(getMonth(listMonth.get(count).intValue()));
							amountUM.add("0.00");
							count++;

						}// else{
//						dateUM.add(getMonth(date1));
						Double d = new Double(listAgentVolumeData.get(i).getAmount1());
						d = d / 100;

						String pattern = "#,##0.00";
						DecimalFormat myFormatter = new DecimalFormat(pattern);
						String output = myFormatter.format(d);
						amountUM.add(output);
						count++;
						
						
					}
					
				}

				
				for (int y = amountUM.size() ;  y < 4 ; y++) {
					amountUM.add("0.00");
				}
				
				
				List<String> amountRev = new ArrayList<String>();
				for (int i = amountUM.size() - 1; i >= 0; i--) {
					amountRev.add(amountUM.get(i));
				}
				
				List<String> DateRev = new ArrayList<String>();
				for (int i = date.size() - 1; i >= 0; i--) {
					DateRev.add(date.get(i));
				}
				
				finalVolumeData.setAgId(t.getId().toString());
				finalVolumeData.setAgentName(t.getBusinessName());
				finalVolumeData.setAmount(amountRev);
				finalVolumeData.setDate(DateRev);

				data.add(finalVolumeData);
	
			}
	
	pageBean = new PageBean("Agent", "toyPayMerchants/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	paginationBean1.setItemList(data);
	
	model.addAttribute("pageBean", pageBean);
	model.addAttribute("paginationBean", paginationBean1);
	model.addAttribute("merchant1", merchant1);
	model.addAttribute("agent", agent);

	logger.info("listMerchantVolumeSummary completed");

	return TEMPLATE_TOYPAYMERCHANT;
	
}


@RequestMapping(value={"/listMasterMerchantVolume"},method=RequestMethod.GET)
public String listMasterMerchantVolumeSummary(final Model model,java.security.Principal principal)
{
	logger.info("about to list all merchants for this agent");
	PageBean pageBean = new PageBean("Agent", "masterMerchant/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	//logger.info("about to list all agents");
	
	
	logger.info("Agent Portal Currently logged by "+ principal.getName());
			
	Agent agent=agentService.loadAgentbyMailId(principal.getName());
				
	model.addAttribute("pageBean", pageBean);
	
	logger.info("Agent name"+ agent.getFirstName());
	logger.info("Agent ID"+ agent.getId());

	BigInteger agid = BigInteger.valueOf(agent.getId()); 

	List<MasterMerchant> merchant1 = transactionService.getMasterMerchantDataByAgent(agent.getId().toString());
	Set<String> merchantNameList = new HashSet<String>();
	for (MasterMerchant t : merchant1) {
		
		String email = t.getUsername();
		merchantNameList.add(email+ "~" + t.getMmId());
	}
	logger.info("merchantNameList"+merchantNameList);
	
	pageBean = new PageBean("masterMerchant", "masterMerchant/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	model.addAttribute("pageBean", pageBean);
	model.addAttribute("merchantNameList", merchantNameList);
	model.addAttribute("merchant1", merchant1);
	model.addAttribute("agent", agent);

	logger.info("listMerchantVolumeSummary completed");

	return TEMPLATE_AGENT;
	
}


@RequestMapping(value={"/listmastermerchantvolumesummary"},method=RequestMethod.GET)
public String MasterMerchantVolumeSummary(final Model model,
		@RequestParam("id") String merchantid,
		java.security.Principal principal)
{
	logger.info("listmastermerchantvolumesummary for this agent: "+merchantid);
	
	PageBean pageBean = new PageBean("masterMerchant", "masterMerchant/merchantVolume", Module.AGENT,
			"agent/sideMenuAgent");
	List<AgentVolumeData> data = new ArrayList<AgentVolumeData>();
	
	PaginationBean<AgentVolumeData> paginationBean1 = new PaginationBean<AgentVolumeData>();
	paginationBean1.setCurrPage(1);
	
	
	
	Agent agent=agentService.loadAgentbyMailId(principal.getName());
	BigInteger agid = BigInteger.valueOf(agent.getId()); 

	
	List<MasterMerchant> merchant1 = transactionService.getMasterMerchantDataByAgent(agent.getId().toString());
	Set<String> merchantNameList = new HashSet<String>();
	for (MasterMerchant t : merchant1) {
		
		String email = t.getUsername();
		merchantNameList.add(email+ "~" + t.getMmId());
	}

	
	List<AgentVolumeData> listAVD = new ArrayList<AgentVolumeData>();
	
	listAVD = transactionService.getMerchantVolBymmId(merchantid);
	logger.info("listAVD: "+listAVD);
	
	List<AgentVolumeData> listAVD1 = new ArrayList<AgentVolumeData>();
	
	listAVD1 = transactionService.getMerchantVolBymmId1(merchantid);
	logger.info("listAVD1: "+listAVD1);
	
	
	//forSettlement Amount String List to Long list
	List<String> forStringAmount = listAVD.get(0).getAmount();
		logger.info("List forTxn String Amount ::"+forStringAmount);
		logger.info("List forTxn String Amount Size ::"+forStringAmount.size());
	List<Double> forLongAmount = new ArrayList<Double>(forStringAmount.size());
	for(String s : forStringAmount) 
		forLongAmount.add(Double.parseDouble(s));
		logger.info("List forTxn Long Amount ::"+forLongAmount);
		logger.info("List forTxn Long Amount Size ::"+forLongAmount.size());
	
	//UM Amount String  List to Long list
	List<String> forStringAmountUM = listAVD1.get(0).getAmount();
		logger.info("List UMTxn String Amount ::"+forStringAmountUM);
		logger.info("List UMTxn String Amount Size ::"+forStringAmountUM.size());
	List<Double> forLongAmountUM = new ArrayList<Double>(forStringAmountUM.size());
	for(String s : forStringAmountUM) 
		forLongAmountUM.add(Double.parseDouble(s));
		logger.info("List UMTxn Long Amount ::"+forLongAmountUM);
		logger.info("List UMTxn Long Amount Size ::"+forLongAmountUM.size());
	
    //Add two long list value and convert string list
		
		int len = 0;
		if(forLongAmountUM.size() > forLongAmount.size()) {
			len = forLongAmountUM.size();
		}else {
			len = forLongAmount.size();
		}
		
		List<Double> newAmount = new ArrayList<Double>(len);
		for (int i =0; i< len ; i++) {
			Double Amount = forLongAmount.get(i) + forLongAmountUM.get(i);
			logger.info("List Amount ::"+Amount);
			newAmount.add(Amount);
		}
		
		logger.info("List newAmount Long Amount ::"+newAmount);
		logger.info("List newAmount Long Amount Size ::"+newAmount.size());
		
		
		List<String> newAmountString = new ArrayList<String>(newAmount.size());
		for(Double s : newAmount) 
			newAmountString.add(String.valueOf(s));
			logger.info("List newAmount String Amount ::"+newAmountString);
			logger.info("List newAmount String Amount Size ::"+newAmountString.size());
	
//}
		listAVD.get(0).setAmount(newAmountString);
	

	for(AgentVolumeData merchantVolumeData: listAVD){

		data.add(merchantVolumeData);
	
	}
	
	
	paginationBean1.setItemList(data);
	
	model.addAttribute("pageBean", pageBean);
	model.addAttribute("paginationBean", paginationBean1);
	model.addAttribute("merchantNameList", merchantNameList);
	model.addAttribute("merchant1", merchant1);


	return TEMPLATE_AGENT;
	
}


}


