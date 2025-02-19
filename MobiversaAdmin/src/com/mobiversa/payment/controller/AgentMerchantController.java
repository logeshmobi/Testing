package com.mobiversa.payment.controller;


//import java.sql.Date;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*import antlr.collections.List;*/
import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.FileUpload;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.RegAddMerchant;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.SubAgentService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.PropertyLoader;
import com.mobiversa.payment.validator.AddAgentUserValidator;



@Controller
@RequestMapping(value = AgentMerchantController.URL_BASE)
public class AgentMerchantController extends BaseController {
	
	
	
	@Autowired
	private AgentService agentService;
	
	
	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private SubAgentService subAgentService;
	
	@Autowired
	private MobileUserService mobileUser;
	
	@Autowired
	private AddAgentUserValidator validator;

	//private String merchantName;

	/* private static final String merchantModel = "merchant"; */

	public static final String URL_BASE = "/agent2";
	
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("Default Page Agent2");
		return "redirect:" + URL_BASE + "/list/1";
	}

	
	
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayMerchantList(final Model model, @PathVariable final int currPage,java.security.Principal principal,
			Merchant merchant) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("merchantSummaryList", "agent/merchantSummaryList", Module.AGENT,
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
	
	
	@RequestMapping(value = { "/addMerchant" }, method = RequestMethod.GET)
	public String displayAddMerchant(@ModelAttribute("merchant")  RegAddMerchant merchant,  Model model,java.security.Principal principal) {
		logger.info("about to add Agent SamePage");	
		List<Agent> agent1=agentService.loadCurrentAgent(principal.getName());
		
		logger.info("Merchant Added in  Agent Portal logged by :" + principal.getName());
		
		List<SubAgent> subagent=subAgentService.loadCurrentSubAgent(agent1.get(0));
		 Set<String> agentNameList = new HashSet<String>();
			for(Agent t : agent1) {
				String firstName = t.getFirstName();
				String mailId = t.getUsername();
				agentNameList.add("AGENT~"+firstName.toString()+"~"+mailId);
			}
	
			for(SubAgent s : subagent)
			{
				String agentName = s.getName();
				String emailId = s.getMailId();
				agentNameList.add("SUBAGENT~"+agentName+"~"+emailId);
			}

			PageBean pageBean = new PageBean("Merchant Detail", "agent/addMerchantAgentPage/addMerchant", Module.AGENT,
				"agent/sideMenuAgent");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("agentNameList", agentNameList);
		model.addAttribute("agent", agent1);
		return TEMPLATE_AGENT;
		
	}
	
	@RequestMapping(value = { "/addMerchant" }, method = RequestMethod.POST)
	public String processAddUserForm(@ModelAttribute("merchant") final RegAddMerchant merchant,
			final HttpServletRequest request, final ModelMap model, final BindingResult errors,java.security.Principal principal) {
		logger.info("about to add Agent SamePage");	
		//List<Agent> agent1=agentService.loadCurrentAgent(principal.getName());
		
		PageBean pageBean = new PageBean("Merchant user add Details", "agent/addMerchantAgentPage/addMerchant", Module.AGENT,
				"agent/sideMenuAgent");
		 //start pay File
		
		String fileIds = "";
		 if (!merchant.getPayFile().isEmpty()) {
			 FileOutputStream fos = null;
			 BufferedOutputStream stream = null;
			 String dirData= null;
			 String fileData = null;
			 byte[] bytes =null;
			 File dir =null;
			 File serverFile = null;
			 String rootPath = null;
				try {
					 bytes = merchant.getPayFile().getBytes();
					Date fileid = new Date();
					String fileId = new SimpleDateFormat("yyyyMMddhhmmss").format(fileid);
					
					String adminName=principal.getName();
					Date dt = new Date();
					String date = new SimpleDateFormat("yyyyMMdd").format(dt);
					rootPath = PropertyLoader.getFile().getProperty("AGENTPATH");
					//dirData = rootPath + File.separator + merchant.getBusinessName()+ File.separator+date;
					dirData = date;
					//File dir = new File(rootPath + File.separator + merchant.getBusinessName()+ File.separator+date);
					dir = new File(dirData.toString());
					if (!dir.exists())
						dir.mkdirs();
					//fileData = dir.getAbsolutePath()+ File.separator + merchant.getPayFile().getOriginalFilename();
					fileData =  merchant.getPayFile().getOriginalFilename();
					//fileData1 = new StringBuffer(dir.getAbsolutePath()+ File.separator + merchant.getPayFile().getOriginalFilename());
					/*File serverFile = new File(dir.getAbsolutePath()
							+ File.separator + merchant.getPayFile().getOriginalFilename());*/
					serverFile = new File(fileData);
					fos = new FileOutputStream(serverFile);
					//fos = new FileOutputStream(new File(fileData));
					stream = new BufferedOutputStream(
							fos);
					stream.write(bytes);
					stream.close();
					fos.close();
					
					FileUpload fileUpload = new FileUpload();
					
					fileUpload.setFileId(fileId);
					fileUpload.setFileName(merchant.getPayFile().getOriginalFilename());
					fileUpload.setFilePath(serverFile.getAbsolutePath());
					fileUpload.setMerchantId("");
					fileUpload.setMerchantName(merchant.getBusinessName());
					fileUpload.setCreatedBy(adminName);
					fileUpload.setCreatedDate(new Date());
					
					FileUpload fileUpload1 =merchantService.storeFileUpload(fileUpload);
					
					merchant.setPayFName(merchant.getPayFile().getOriginalFilename());
					
					fileIds = fileUpload1.getId().toString();
				} catch (Exception e) {
				}finally{
					
					if(stream != null){
						try{
						stream.close();
						}catch(Exception e) {}
					}
					if(fos != null){
						try{
							fos.close();
						}catch(Exception e) {}
					}
				}
			}
		
		 merchant.setFileId(fileIds);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		model.addAttribute("agent");
		//PCI
		request.getSession(true).setAttribute("addMerchantSession", merchant);
		
		//return TEMPLATE_AGENT;
		return "redirect:/agent2/merchantDetailsReviewAndConfirm";
	}

	
	@RequestMapping(value = {"/merchantDetailsReviewAndConfirm" }, method = RequestMethod.GET)
	public String displayAddUserConfirmation(final ModelMap model, final HttpServletRequest request) {
		logger.info("about to add Merchant Details ReviewAndConfirm");
		RegAddMerchant merchant = (RegAddMerchant) request.getSession(true).getAttribute("addMerchantSession");
		if (merchant == null) {
			return "redirect:" + URL_BASE + "/list/1";		
		}
		PageBean pageBean = new PageBean("Merchant user add Details",
				"agent/addMerchantAgentPage/addMerchantConfirm", Module.AGENT, "agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		
		return TEMPLATE_AGENT;
	}		
	@RequestMapping(value = {"/merchantDetailsReviewAndConfirm"}, method = RequestMethod.POST)
	public String confirmAddMerchant(@ModelAttribute("merchant") final RegAddMerchant merchant, final Model model,final String agentName,
			final HttpServletRequest request,final java.security.Principal principal) {
		logger.info("about to add Merchant Details Confirms");	
		PageBean pageBean = new PageBean("Succefully New Merchant Added",
				
				"agent/addMerchantAgentPage/addMerchantSucess", Module.AGENT, "agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);
		
		RegAddMerchant MerchantSavedInHttpSession = (RegAddMerchant) request.getSession(true).getAttribute("addMerchantSession");
		
		logger.info("Merchant Name:" +MerchantSavedInHttpSession.getBusinessName() + ":"+ "Merchant Added by:" + principal.getName() );
		if (MerchantSavedInHttpSession == null) {
			return "redirect:/agent2/addMerchant";
		}
		model.addAttribute("merchant",MerchantSavedInHttpSession);
		merchantService.addMerchant1(MerchantSavedInHttpSession);
		request.getSession(true).removeAttribute("addMerchantSession");
		return  TEMPLATE_AGENT;
	}
	
	
	@RequestMapping(value = { "/hotelMerchantslist/{currPage}" }, method = RequestMethod.GET)
	public String displayHotelMerchantList(final Model model, @PathVariable final int currPage,java.security.Principal principal,
			Merchant merchant) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("merchantSummaryList", "hotelMerchants/merchantSummaryList", Module.AGENT,
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
			MobileUser mobileuser=mobileUser.loadMobileUserbyMerchantFK(mer.getId().toString());
			logger.info("mobileuser:" + mobileuser +"Tid::"+mobileuser.getMotoTid() );
			mer.setBusinessName(mer.getBusinessName().toUpperCase());
			mer.setBusinessAddress1(mer.getBusinessAddress1()+","+mer.getBusinessAddress2()+","+mer.getBusinessAddress3());
			mer.setCountry(mobileuser.getMotoTid());
			logger.info("mid:" + mer.getMid().getUmMotoMid());
			lstMerchant.add(mer);
		}
		paginationBean.setItemList(lstMerchant);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_HOTELMERCHANT;
	}
	
	
	@RequestMapping(value = { "/toyPayMerchantslist/{currPage}" }, method = RequestMethod.GET)
	public String displaytoyPayMerchantsList(final Model model, @PathVariable final int currPage,java.security.Principal principal,
			Merchant merchant) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("merchantSummaryList", "toyPayMerchants/merchantSummaryList", Module.AGENT,
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
			MobileUser mobileuser=mobileUser.loadMobileUserbyMerchantFK(mer.getId().toString());
			logger.info("mobileuser:" + mobileuser +"Tid::"+mobileuser.getMotoTid() );
			mer.setBusinessName(mer.getBusinessName().toUpperCase());
			mer.setBusinessAddress1(mer.getBusinessAddress1()+","+mer.getBusinessAddress2()+","+mer.getBusinessAddress3());
			mer.setCountry(mobileuser.getEzywayTid());
			logger.info("mid:" + mer.getMid().getUmEzywayMid());
			lstMerchant.add(mer);
		}
		paginationBean.setItemList(lstMerchant);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_TOYPAYMERCHANT;
	}
}
