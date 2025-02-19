package com.mobiversa.payment.controller;

import java.security.Principal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobiLiteMerchant;
import com.mobiversa.common.bo.MobiLiteTerminal;
import com.mobiversa.common.bo.UMEcomTxnRequest;
import com.mobiversa.common.bo.UMEcomTxnResponse;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dao.SMSServiceImpl;
import com.mobiversa.payment.dto.DashBoardData;
import com.mobiversa.payment.dto.SixMonthTxnData;
import com.mobiversa.payment.service.AdminService;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.DashBoardService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.PromotionService;
import com.mobiversa.payment.service.SubAgentService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.DashboardAmount;
import com.mobiversa.payment.util.MobiliteTrackDetails;
import com.mobiversa.payment.util.PropertyLoad;
import com.mobiversa.payment.util.UMEzyway;
import com.mobiversa.payment.validator.AddAgentUserValidator;

//import org.springframework.validation.Validator;
@Controller
@RequestMapping(value = MobiliteWebController.URL_BASE)
public class MobiliteWebController extends BaseController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private DashBoardService dashBoardService;

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private MobileUserService mobileUserService;

	@Autowired
	private MobileUserService mobileUser;

	@Autowired
	private AgentService agentService;

	@Autowired
	private SubAgentService subAgentService;
	@Autowired
	private PromotionService promotionService;
	@Autowired
	private AddAgentUserValidator validator;

	@Autowired
	private TransactionService transactionService;

	/* private static final String merchantModel = "merchant"; */

	public static final String URL_BASE = "/mobilite";
	private static final Logger logger = Logger.getLogger(MobiliteWebController.class);

	/**
	 * Default wildcard page to redirect invalid page mapping to the default page.
	 * <p>
	 * for example <code>/merchant/testing</code> will be caught in the
	 * requestMapping and therefore user is redirected to display all merchant,
	 * instead of showing a 404 page.
	 * 
	 * @return
	 */
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("Default Page Merchant");
		return "redirect:" + URL_BASE + "/list/1";
	}

	/**
	 * Display a list of all merchants
	 * <p>
	 * Wireframe bank module 03a
	 * </p>
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayMerchantList(final Model model, final Merchant merchant, @PathVariable final int currPage,
			final java.security.Principal principal) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("Merchant", "merchant/merchantList", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		paginationBean.setCurrPage(currPage);
		merchantService.listMerchant(paginationBean);

		model.addAttribute("paginationBean", paginationBean);

		/*
		 * if(principal.getName().equals("bhuvi@mobiversa.com")) { return
		 * TEMPLATE_SUPER_AGENT; }
		 */
		return TEMPLATE_MOBILITEMERCHANT;
	}

	@RequestMapping(value = { "/ezyLinkList" }, method = RequestMethod.GET)
	public String umLinkList(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("list umLinkList transaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);

		MobiLiteMerchant currentMerchant = merchantService.loadMobiLiteMerchant(myName);

		MobiLiteTerminal termDetails = merchantService.loadMobiliteTerminalDetailsByMid(currentMerchant.getId());

		PageBean pageBean = new PageBean("transactions list", "MobiliteMerchant/transaction/transactionUMLinkList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" UM-Link Transaction Summary:" + principal.getName());
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listMobiliteLinkTransaction(paginationBean, null, null, termDetails.getTid());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_MOBILITEMERCHANT;
	}

	@RequestMapping(value = { "/searchLink" }, method = RequestMethod.GET)
	public String searchUMLink(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("search Ezylink Transaction ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		MobiLiteMerchant currentMerchant = merchantService.loadMobiLiteMerchant(myName);

		MobiLiteTerminal termDetails = merchantService.loadMobiliteTerminalDetailsByMid(currentMerchant.getId());

		PageBean pageBean = new PageBean("transactions list", "MobiliteMerchant/transaction/transactionUMLinkList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.listMobiliteLinkTransaction(paginationBean, date, date1, termDetails.getTid());

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_MOBILITEMERCHANT;

	}

	@RequestMapping(value = "/ezyLinkExport", method = RequestMethod.GET)
	public ModelAndView umLinkExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//				@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("UM_EZYLINK Export by Merchant");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		MobiLiteMerchant currentMerchant = merchantService.loadMobiLiteMerchant(myName);

		MobiLiteTerminal termDetails = merchantService.loadMobiliteTerminalDetailsByMid(currentMerchant.getId());

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<UMEzyway> paginationBean = new PaginationBean<UMEzyway>();
		paginationBean.setCurrPage(currPage);

		transactionService.exportMobiliteLinkTransaction(paginationBean, dat, dat1, termDetails.getTid());

		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<UMEzyway> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListMobiliteUMExcel", "umTxnList", list1);
		} else {
			return new ModelAndView("txnListMobiliteUMPdf", "umTxnList", list1);
		}

	}

	
	  @RequestMapping(value = { "/getTrackData/{id}" }, method = RequestMethod.GET)
	  public String displaytrackList(final Model model,
	   @PathVariable final String id, HttpServletRequest request,
	  HttpServletResponse response, Principal principal,
	  @RequestParam(required = false, defaultValue = "1") final int currPage) {
	  
	  logger.info("about to list all merchants"); PageBean pageBean = new
	  PageBean("Merchant", "MobiliteMerchant/getTrackData", Module.MERCHANT,
	  "merchant/sideMenuMerchant"); model.addAttribute("pageBean", pageBean);
	  
	  HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
	  UMEcomTxnRequest trreq = transactionService.loadUMEzywayTransactionRequest(id);
	  
	  UMEcomTxnResponse tr = transactionService.loadUMEzywayTransactionResponse(id);
	  
	  MobiLiteMerchant currentMerchant = merchantService.loadMobiLiteMerchant(myName);

	  MobiLiteTerminal termDetails = merchantService.loadMobiliteTerminalDetailsByMid(currentMerchant.getId());
	  MobiliteTrackDetails trackDet = new MobiliteTrackDetails();
	  
	  if(tr.getF007_TxnAmt() != null) {
	  	double amount = 0;
		amount = Double.parseDouble(tr.getF007_TxnAmt()) / 100;
		String pattern = "#,##0.00";
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(amount);
		 trackDet.setAmount(output);
		 
		 logger.info("output: " + output);
	  }
	  
	  trackDet.setBusinessName(currentMerchant.getBusinessName());  
	  trackDet.setTid(termDetails.getTid());
	  trackDet.setCustomerName(trreq.getF268_ChName());
	  trackDet.setCustomerPhoneNo(trreq.getF279_HP());
	
	
	  model.addAttribute("trackDet", trackDet);

	  
	  return TEMPLATE_MOBILITEMERCHANT; 
	  }
	  
	  
	  @RequestMapping(value = { "/sendTrackData" }, method = RequestMethod.POST)
		public String confirmAddAgent(@ModelAttribute("trackDet") final MobiliteTrackDetails trackDet,
				final Model model, final java.security.Principal principal,
				final HttpServletRequest request,
				 @RequestParam(required = false, defaultValue = "1") final int currPage) {
			logger.info("about to sendTrackData Confirms");
			String responseMsg = null;
			PageBean pageBean = new PageBean("Merchant", "MobiliteMerchant/smsSuccess", Module.MERCHANT,
					  "merchant/sideMenuMerchant"); model.addAttribute("pageBean", pageBean);
					
					  logger.info("sendTrackData");
			  PaginationBean<MobiliteTrackDetails> paginationBean = new PaginationBean<MobiliteTrackDetails>();
				paginationBean.setCurrPage(currPage);
				
				
				model.addAttribute("paginationBean", paginationBean);
				
				logger.info("trackdet::"+trackDet.getTrackNumber() +"::::"+trackDet.getAmount()
				 +"::::"+trackDet.getCourierName()+":::::"+trackDet.getSentDate()+"::::"+trackDet.getCustomerPhoneNo());
			
				/*
				 * trackDet.setAmount("10"); trackDet.setBusinessName("Mobi");
				 * trackDet.setTrackNumber("45678");
				 * trackDet.setCourierName("ProfessionalCourier");
				 * trackDet.setSentDate("20201120"); trackDet.setCustomerPhoneNo("0105302913");
				 */
				
				logger.info("trackdet"+trackDet.getTrackNumber());
				
				int a = merchantService.updateTrackDetails(trackDet);
				
			  String smsBody = null;
			  
			  smsBody = "Click the link to confirm the service of goods : "+PropertyLoad.getFile().getProperty("RECEIPT_SMSLINK")+trackDet.getBusinessName()+"&trackNo="+trackDet.getTrackNumber()
			  +"&courierName="+trackDet.getCourierName()
					  +"&sentDate="+trackDet.getSentDate()+"&amount="+trackDet.getAmount();
			  
			  logger.info("smsBody::"+smsBody);
			  

				if(!(trackDet.getCustomerPhoneNo()==null||trackDet.getCustomerPhoneNo().equals(""))){
					if(trackDet.getCustomerPhoneNo().length() > 7) {
					try {
						boolean recept = false;
						if(!recept) {
						SMSServiceImpl.sendSMS(trackDet.getCustomerPhoneNo(),smsBody);
						recept=true;
						 responseMsg =" Message Sent Succesfully to the mobile number :" +trackDet.getCustomerPhoneNo();
						logger.info("Message Sent Succesfully to the "+"mobile number :"+trackDet.getCustomerPhoneNo()+" and the "+"Message Body is:"+smsBody);
						}
					} catch (Exception e) {
						
						logger.info("Message Not Sent to mobile number :"+trackDet.getCustomerPhoneNo());
						 responseMsg =" Message Not Sent to mobile number :"+trackDet.getCustomerPhoneNo() 
						+"with exception: "+e;
						//throw new MobileApiException(Status.SMS_SENDING_FAILED);
					}
					}else {
						
						logger.info("Message Not Sent to mobile number :"+trackDet.getCustomerPhoneNo());
						 responseMsg =" Message Not Sent to mobile number :"+trackDet.getCustomerPhoneNo() ;
						
					}
				}
			
				
				model.addAttribute("trackDet", trackDet);
				model.addAttribute("responsemsg", responseMsg);
			return TEMPLATE_MOBILITEMERCHANT;
			
		}

	  
	  @RequestMapping(value = { "/merdashBoard" }, method = RequestMethod.GET)
		public String webMerchantDashBoard(final Model model,
				final java.security.Principal principal, HttpServletRequest request) {
			logger.info("Mobilite Merchant Dashboard: "+ principal.getName());
			PageBean pageBean = new PageBean("Dash Board",
					"dashboard/mobilitemerchantdashbrd", Module.MERCHANT,
					"admin/sideMenuBankUser");
			
			model.addAttribute("pageBean", pageBean);
			 HttpSession session = request.getSession();
			   // String username = (String)request.getAttribute("un");
			 //Merchant merchant = me
			 MobiLiteMerchant merchant = merchantService.loadMobiliteMerchant(principal.getName());
			    String EzypodCheck = "NO";
			    MID mid = merchantService.loadMidByMerchant_PK((merchant.getId()).toString());
		   	
			    	MobiLiteTerminal termDetails = merchantService.loadMobiliteTerminalDetailsByMid(merchant.getId());

			  
			    
			    //	MobiLiteUser mobiliteuser=mobileUser.loadMobiliteUserByFk(merchant.getId());
			    
			    
			   
			    session.setAttribute("merchantUserName",  merchant.getBusinessName());
			 
			   
			    
			    
			    int totalDevice = dashBoardService.getMobiliteMerchantTotalDevice(termDetails.getTid());
			    
			    model.addAttribute("currentMerchant" , merchant.getBusinessName());
			    
			    model.addAttribute("totalDevice", totalDevice);
			    
			    
			    String totalTxn = dashBoardService.getMobiliteMerchantCurrentMonthTxn(merchant);
			    logger.info("total txn:  "+totalTxn);
				
				model.addAttribute("totalTxn", totalTxn);
				
				
				
				String dailytxn = dashBoardService.getMobiliteMerchantDailyTxn(merchant);
				
				logger.info("dailytxn:  "+dailytxn);
				model.addAttribute("dailytxn", dailytxn);
				
				
				String weeklytxn = dashBoardService.getMobiliteMerchantWeeklyTxn(merchant);
				
				
				
				logger.info("weeklytxn:  "+weeklytxn);
				model.addAttribute("weeklytxn", weeklytxn);
			
				PaginationBean<DashBoardData> paginationBean = new PaginationBean<DashBoardData>();
				logger.info(" check 5 recent txn Amount Data ");
			
				dashBoardService.getMobiliteMerchantLastFiveTxn(paginationBean,merchant);

				logger.info(" received 5 recent  txn Amount Data ");
				model.addAttribute("fiveTxnList", paginationBean);
				List<SixMonthTxnData> sixMonData = new ArrayList<SixMonthTxnData>();
				 sixMonData= dashBoardService.getMobiliteMerchantSixMonTxn(merchant); 
				 
				 
				
			
			
			PaginationBean<SixMonthTxnData> paginationBean1 = new PaginationBean<SixMonthTxnData>();
			List<SixMonthTxnData> data = new ArrayList<SixMonthTxnData>();
				for(SixMonthTxnData txnMonthData: sixMonData){
				
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
		    	//System.out.println("Data : "+c3);
		    	c3=c3.replace(",,", "");
		    	//System.out.println("Data : "+c3);
		    	txnMonthData.setCountData(c3); 
				
				
				
				model.addAttribute("threeMonthTxn", txnMonthData);
				 
				
				data.add(txnMonthData);
			
			}
			//End Agent 

			String a1=null,a2=null,a3=null,a4=null,a5=null,a6=null;
			 Float min = 0.0f,max=0.0f;
			for(SixMonthTxnData t: sixMonData) {
				logger.info(" check= Amount Data "+t.getAmountData());
				String[] amt=t.getAmountData().split(",");
				a1=amt[0];
				a2=amt[1];
				a3=amt[2];
				a4=amt[3];
				a5=amt[4];
				a6=amt[5];
				Float[] a= {Float.valueOf(a1),Float.valueOf(a2),Float.valueOf(a3)
						,Float.valueOf(a4),Float.valueOf(a5),Float.valueOf(a6)};

				Arrays.sort(a);
				
				 min = a[0]; //  assume first elements as smallest number
				 max = a[0]; //  assume first elements as largest number
				 for (int i = 1; i < a.length; i++)  // iterate for loop from arrays 1st index (second element)
					{
						if (a[i] > max) 
						{
							max = a[i];
						}
						if (a[i] < min) 
						{
							min = a[i];
						}
					}
				 
				
			}
			
			 logger.info("min: "+max +" max: "+min);
			
			
			//Math.round(1004/1000)*1000
			int rounded=DashboardAmount.roundNum(Math.round(max));
			logger.info("rounded value : "+rounded );
			model.addAttribute("stepsize",  rounded/10);
			model.addAttribute("max", rounded);
			paginationBean1.setItemList(data);

			AuditTrail auditTrail = merchantService.updateAuditTrailByMerchant(
					principal.getName(), principal.getName(), "login");

			if (auditTrail != null) {
				logger.info("Logged in by Merchant : " + principal.getName());
			}
			
			model.addAttribute("paginationBean", paginationBean1);
			model.addAttribute("merchant", merchant);
			//logger.info("in homecontroller dashboard: ");
			model.addAttribute("checkDeviceStatus", "YES");	
			
			model.addAttribute("deviceCheck", "YES");
			//model.addAttribute("ezyPodCheck", ezyPodSumCheck);
			session.setAttribute("userName",  merchant.getUsername()); 
			session.setAttribute("merchant", merchant); 
			
			//session.setAttribute("ezyPodCheck", ezyPodSumCheck); 
			
			session.setAttribute("userRole",  "BANK_MERCHANT");
			//request.setAttribute("userName", principal.getName());
			return TEMPLATE_MOBILITEMERCHANT;
		}

	  
	  @RequestMapping(value = { "/getMerchantRecentTxn" }, method = RequestMethod.GET)
		public String getMerchantTxnDashBoard(final Model model,
				final java.security.Principal principal,
				HttpServletRequest request) {
			logger.info("Login Person in dash Board:"+principal.getName());
			HttpSession session=request.getSession();		
			String myName = (String) session.getAttribute("userName");
			
			logger.info("currently logged in as " + myName);
			MobiLiteMerchant currentMerchant = merchantService.loadMobiLiteMerchant(myName);
			MobiLiteTerminal termDetails = merchantService.loadMobiliteTerminalDetailsByMid(currentMerchant.getId());
			
			PaginationBean<DashBoardData> txnPaginationBean = new PaginationBean<DashBoardData>();
			logger.info(" check first 100 txn Amount Data ");
			dashBoardService.getMobiliteMerchantHundredTxn(txnPaginationBean,currentMerchant);
			
			logger.info(" received first 100 txn Amount Data ");
			
			PageBean pageBean = new PageBean("Dash Board", "dashboard/completeMerchantReport",
					Module.MERCHANT, "admin/sideMenuBankUser");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("fiveTxnList", txnPaginationBean);
			return TEMPLATE_MOBILITEMERCHANT;
		}
	  
	  
	  @RequestMapping(value = { "/merchProf/detailsMerchProf" }, method = RequestMethod.GET)
	  public String merchantDetails(final Model model, final Principal principal,
	  		HttpServletRequest request) {
	  	PageBean pageBean = new PageBean("Mobileuser Detail", "MobiliteMerchant/merchantProfile",
	  			Module.MERCHANT, "merchantweb/sideMenuMerchantWebMobile");
	  	
	  	HttpSession session=request.getSession();
	  	// logger.info("about to list all  transaction");
	  	String myName = (String) session.getAttribute("userName");
	  	
	  	//Merchant merchant = merchantService.loadMerchant(principal.getName());
	  	MobiLiteMerchant merchant = merchantService.loadMobiliteMerchant(myName);
	  	model.addAttribute("pageBean", pageBean);
	  	//model.addAttribute("mobileUser", mobileUser);
	  	model.addAttribute("merchant", merchant);
	  	return TEMPLATE_MOBILITEMERCHANT;
	  }
	  @RequestMapping(value = { "/merchProf/changePassWordbyMerch" }, method = RequestMethod.GET)
	  public String changePassword(final Model model, final Principal principal,
	  		HttpServletRequest request) {
	  	//logger.info("In change password" );
	  	HttpSession session=request.getSession();
	  	// logger.info("about to list all  transaction");
	  	String myName = (String) session.getAttribute("userName");
	  	PageBean pageBean = new PageBean("Merchant Pofile", "MobiliteMerchant/changePassword",
	  			Module.MERCHANT, "merchantweb/sideMenuMerchantWebMobile");
		MobiLiteMerchant merchant = merchantService.loadMobiliteMerchant(myName);
	  	model.addAttribute("pageBean", pageBean);
	  	//model.addAttribute("mobileUser", mobileUser);
	  	model.addAttribute("merchant", merchant);
	  	return TEMPLATE_MOBILITEMERCHANT;
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
	  		int count = merchantService.changeMobiliteMerchantPassWord(myName,
	  				newPassword,password);
	  		logger.info("inside merchantProfile/confirmPassword  "+count);
	  		PageBean pageBean = null;
	  		if (count == 1) {
	  			logger.info("Success");
	  			pageBean = new PageBean("Merchant Pofile",
	  					"MobiliteMerchant/changePasswordSuccess", Module.MERCHANT,
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
	  					"MobiliteMerchant/changePasswordFailure", Module.MERCHANT,
	  					"merchantweb/sideMenuMerchantWebMobile");
	  			model.addAttribute("merchantUserName", principal.getName());
	  		}
	  		model.addAttribute("pageBean", pageBean);
	  		return TEMPLATE_MOBILITEMERCHANT;
	  	}
		
		
	 

}
