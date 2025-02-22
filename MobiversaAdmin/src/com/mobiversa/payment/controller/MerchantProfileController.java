package com.mobiversa.payment.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.ThreeMonthTxnData;
import com.mobiversa.payment.service.DashBoardService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;

@Controller
//@RequestMapping(value = MerchantWebMobileController.URL_BASE)
public class MerchantProfileController extends BaseController {
	//public static final String URL_BASE = "/merchantProfile";

	@Autowired
	private MobileUserService mobileUserService;
	@Autowired
	private MobileUserService mobileUser;
	
	@Autowired
	private DashBoardService dashBoardService;
	
	@Autowired
	private MerchantService merchantService;
	static final Logger logger = Logger.getLogger(MerchantProfileController.class.getName());
	
//@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
public String defaultPage() {

	return "redirect:/merchant/merchantweb";
}

@RequestMapping(value = { "/merchProf/detailsMerchProf" }, method = RequestMethod.GET)
public String merchantDetails(final Model model, final Principal principal,
		HttpServletRequest request) {
	PageBean pageBean = new PageBean("Mobileuser Detail", "home/merchantProfile",
			Module.MERCHANT, "merchantweb/sideMenuMerchantWebMobile");
	
	HttpSession session=request.getSession();
	// logger.info("about to list all  transaction");
	String myName = (String) session.getAttribute("userName");
	
	//Merchant merchant = merchantService.loadMerchant(principal.getName());
	Merchant merchant = merchantService.loadMerchant(myName);
	model.addAttribute("pageBean", pageBean);
	//model.addAttribute("mobileUser", mobileUser);
	model.addAttribute("merchant", merchant);
	model.addAttribute("loginname", principal.getName());
	return TEMPLATE_MERCHANT;
}
@RequestMapping(value = { "/merchProf/changePassWordbyMerch" }, method = RequestMethod.GET)
public String changePassword(final Model model, final Principal principal,
		HttpServletRequest request) {
	//logger.info("In change password" );
	HttpSession session=request.getSession();
	// logger.info("about to list all  transaction");
	String myName = (String) session.getAttribute("userName");
	PageBean pageBean = new PageBean("Merchant Pofile", "home/changePassword",
			Module.MERCHANT, "merchantweb/sideMenuMerchantWebMobile");
	Merchant merchant = merchantService.loadMerchant(myName);
	model.addAttribute("pageBean", pageBean);
	//model.addAttribute("mobileUser", mobileUser);
	model.addAttribute("merchant", merchant);
	model.addAttribute("loginname", principal.getName());
	return TEMPLATE_MERCHANT;
}

	@RequestMapping(value = { "/merchProf/confirmPasswordbyMerch" }, method = RequestMethod.POST)
	public String confirmPassword(final Model model,final Principal principal,
			HttpServletRequest request,
			/*@RequestParam("username") final String username*/
			@RequestParam("newPassword") final String newPassword,
			@RequestParam("password") final String password) 
	{
		//logger.info("old password: "+password+" new password: "+newPassword);
		HttpSession session=request.getSession();
		
		String myName = (String) session.getAttribute("userName");
		logger.info("change pasword: "+myName);
		int count = merchantService.changeMerchantPassWord(myName,
				newPassword,password);
		logger.info("inside merchantProfile/confirmPassword  "+count);
		PageBean pageBean = null;
		if (count == 1) {
			logger.info("Success");
			pageBean = new PageBean("Merchant Pofile",
					"home/changePasswordSuccess", Module.MERCHANT,
					"merchantweb/sideMenuMerchantWebMobile");
			logger.info("currentuser: " + principal.getName());
			model.addAttribute("merchantUserName", principal.getName());
			AuditTrail auditTrail = merchantService.updateAuditTrailByMerchant(
					principal.getName(), principal.getName(), "changePassword");
			if (auditTrail.getUsername() != null) {
				logger.info("Merchant " + auditTrail.getUsername()
						+ " Password ReChanged..By Merchant "
						+ auditTrail.getUsername());
			}
		} else {
			logger.info("Failure");
			pageBean = new PageBean("Merchant Pofile",
					"home/changePasswordFailure", Module.MERCHANT,
					"merchantweb/sideMenuMerchantWebMobile");
			model.addAttribute("merchantUserName", principal.getName());
		}
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/merchant/dashBoard" }, method = RequestMethod.GET)
	public String merchantWebDashBoard(final Model model,
			final java.security.Principal principal, HttpServletRequest request) {
		logger.info("Login Person in dash Board:" + principal.getName());

		/*
		 * PageBean pageBean = new PageBean("Dash Board", "home/dashBoard",
		 * Module.MERCHANT, "merchantweb/sideMenuMerchantWebMobile");
		 * 
		 * PageBean pageBean = new PageBean("Dash Board",
		 * "dashboard/merchantdashbrd", Module.MERCHANT,
		 * "merchantweb/sideMenuMerchantWebMobile");
		 * model.addAttribute("pageBean", pageBean); HttpSession session =
		 * request.getSession(); logger.info("check merchant user name:" +
		 * principal.getName()); Merchant merchant =
		 * merchantService.loadMerchant(principal.getName());
		 * session.setAttribute("merchantUserName", merchant.getUsername());
		 * return TEMPLATE_MERCHANT;
		 */

		logger.info("Login Person in dash Board:" + principal.getName());
		PageBean pageBean = new PageBean("Dash Board",
				"dashboard/merchantdashbrd", Module.MERCHANT,
				"admin/sideMenuBankUser");

		/*
		 * PageBean pageBean = new PageBean("Dash Board",
		 * "dashboard/Newdashboard", Module.MERCHANT, "admin/sideMenuBankUser");
		 */

	model.addAttribute("pageBean", pageBean);
	 HttpSession session = request.getSession();
	   // String username = (String)request.getAttribute("un");
	 //Merchant merchant = me
	 
	 
	 logger.info("Login Person in dash Board:"+principal.getName());
	 Merchant merchant = merchantService.loadMerchant(principal.getName());
	 
	 
	 logger.info("id: "+merchant.getId());
	    if(merchant.getPreAuth() != null){
	    	model.addAttribute("preAuth",  merchant.getPreAuth());
	    }else{
	    	model.addAttribute("preAuth",  "No");
	    }
	    List<MobileUser> mobileuser=mobileUser.loadMobileUserByFk(merchant.getId());
	  
	    
	    Set<String> enableBoost = new HashSet<String>();
		for (MobileUser mu : mobileuser) {
			String enableBoost1 = mu.getEnableBoost();
			if(enableBoost1!=null)
			{
				logger.info("enableBoost1: "+enableBoost1);
				enableBoost.add(enableBoost1.toString());
				
			}
			
		}
		if(enableBoost.contains("Yes"))
		{
			
	    	model.addAttribute("enableBoost",  "Yes");
	    }
		else{
			model.addAttribute("enableBoost",  "No");
	    }
	    
		Set<String> enableMoto = new HashSet<String>();
		for (MobileUser mu : mobileuser) {
			String enableMoto1 = mu.getEnableMoto();
			if(enableMoto1!=null)
			{
			logger.info("enableMoto: "+enableMoto1);
			enableMoto.add(enableMoto1.toString());
			}
			// tidSet.add(t.getDeviceId());
		}
	    if(enableMoto.contains("Yes"))
	    {
	    	model.addAttribute("enableMoto",  "Yes");
	    }else{
	    	model.addAttribute("enableMoto",  "No");
	    }	
		
	    session.setAttribute("merchantUserName",  merchant.getFirstName());
	    
	    logger.info("display merchant mid:id:username"+merchant.getMid().getMid()+":"+merchant.getId()+":"+merchant.getUsername());
	    
		   
	    int totalDevice = dashBoardService.getMerchantTotalDevice(merchant.getMid().getMid());
	    
	    model.addAttribute("totalDevice", totalDevice);
	    
	    String totalTxn = dashBoardService.getMerchantCurrentMonthTxn(merchant);
		
		model.addAttribute("totalTxn", totalTxn);
		
		
		
		
			
	//logger.info("check dashboard details:");
        List<ThreeMonthTxnData> data = new ArrayList<ThreeMonthTxnData>();
	//new changes for bar chart 05072017
	//List<ThreeMonthTxnData> txnCountData = new ArrayList<ThreeMonthTxnData>();
	
	
	PaginationBean<ThreeMonthTxnData> paginationBean1 = new PaginationBean<ThreeMonthTxnData>();
	//paginationBean1.setCurrPage(currPage);
	//logger.info("check dashboard details787878:");
	List<ThreeMonthTxnData> txnListData = new ArrayList<ThreeMonthTxnData>();
	//logger.info("check mid details:" + merchant.getMid().getMid() );
	txnListData= dashBoardService.getMerchantTxnCount(merchant); 
	//logger.info("check dashboard details457454:");
	
	//logger.info("check dashboard txn details45677:" + txnListData.size());
	//for(ThreeMonthTxnData txnMonthData: txnCountData){
	for(ThreeMonthTxnData txnMonthData: txnListData){
		
		//logger.info("check monthly count12333:" + txnMonthData );
		
		//logger.info("Controller agentDet123333 : "+txnMonthData.getCount());
		//logger.info("Controller amount23333 : "+txnMonthData.getAmount());
		//logger.info("Controller date33333 : "+txnMonthData.getDate1());
		String a3="\"";
		for(String a1:txnMonthData.getDate1()){
			a3=a3+a1;
			a3=a3+"\",\"";
    	}
    	//System.out.println("Data : "+a3);
    	a3=a3+"\"";
    	//System.out.println("Data : "+a3);
    	a3=a3.replace(",\"\"", "");
    	//System.out.println("Data : "+a3);
    	txnMonthData.setMonth(a3);
    	
    	String b3="";
		for(String b1:txnMonthData.getAmount()){
			b3=b3+b1;
			b3=b3+",";
    	}
    	//System.out.println("Data : "+b3);
    	b3=b3+",";
    	//System.out.println("Data : "+b3);
    	b3=b3.replace(",,", "");
    	//System.out.println("Data : "+b3);
    	txnMonthData.setAmountData(b3);
    	
    	String c3="";
		for(String c1:txnMonthData.getCount()){
			c3=c3+c1;
			c3=c3+",";
    	}
    	//System.out.println("Data : "+c3);
    	c3=c3+",";
    	System.out.println("Data : "+c3);
    	c3=c3.replace(",,", "");
    	//System.out.println("Data : "+c3);
    	txnMonthData.setCountData(c3); 
		
		
		
		model.addAttribute("threeMonthTxn", txnMonthData);
		 
		
		data.add(txnMonthData);
	
	}
	//End Agent 
	
	paginationBean1.setItemList(data);
	
	
	model.addAttribute("paginationBean", paginationBean1);
		logger.info("in merchant proile contr dashboard:");
	model.addAttribute("checkDeviceStatus", 0);
	model.addAttribute("loginname", principal.getName());
	return TEMPLATE_MERCHANT;

}

}