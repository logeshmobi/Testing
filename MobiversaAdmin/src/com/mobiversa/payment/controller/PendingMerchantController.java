package com.mobiversa.payment.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mobiversa.common.bo.Agent;
import com.mobiversa.common.bo.FileUpload;
import com.mobiversa.common.bo.MID;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.SubAgent;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.RegAddMerchant;
import com.mobiversa.payment.service.AgentService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.SubAgentService;
import com.mobiversa.payment.util.PropertyLoader;
import com.mobiversa.payment.validator.AddAgentUserValidator;
//import org.springframework.validation.Validator;

@Controller
@RequestMapping(value = PendingMerchantController.URL_BASE)
public class PendingMerchantController extends BaseController {

	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private AgentService agentService;
	
	@Autowired
	private SubAgentService subAgentService;
	
	
	@Autowired
	private AddAgentUserValidator validator;

	/* private static final String merchantModel = "merchant"; */

	public static final String URL_BASE = "/merchant1";

	/**
	 * Default wildcard page to redirect invalid page mapping to the default
	 * page.
	 * <p>
	 * for example <code>/merchant/testing</code> will be caught in the
	 * requestMapping and therefore user is redirected to display all merchant,
	 * instead of showing a 404 page.
	 * 
	 * @return
	 */
	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("Pending merchant default page");
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
	public String displayMerchantList(final Model model,final Merchant merchant, @PathVariable final int currPage) {
		//logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("Merchant", "merchant/pendingMerchantList", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();

		paginationBean.setCurrPage(currPage);
		merchantService.listMerchant1(paginationBean);
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}
	
	
		
	//edit merchant new code 03/07/2016
	
	
	@RequestMapping(value = { "/detail/{id}" }, method = RequestMethod.GET)
	public String listMerchant(final Model model, @PathVariable final long id) {
		logger.info("Request to display merchant based on ID: " + id);
		PageBean pageBean = new PageBean("Merchant Detail", "merchant/pendingMerchantEditDetails", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		Merchant merchant = merchantService.loadMerchantByPk(id);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = {"/edit/{id}" }, method = RequestMethod.GET)
	public String displayEditMerchant(final Model model, @PathVariable final Long id ,final HttpServletRequest request,final RegAddMerchant regAddMerchant) {
		//logger.info("Request to display merchant edit");
          List<Agent> agent1 =  agentService.loadAgent();
          logger.info("Total merchant:" + agent1.size()); 
          Set<String> agentNameList = new HashSet<String>();
  		for(Agent t : agent1) {
  			String firstName = t.getFirstName();
  			String mailId = t.getUsername();
  			agentNameList.add("AGENT~"+firstName.toString()+"~"+mailId);
  		}
  		
  		List<SubAgent> subAgent =  subAgentService.loadSubAgent();
  		for(SubAgent t : subAgent) {
  			String firstName = t.getName();
  			String mailId = t.getMailId();
  			agentNameList.add("SUBAGENT~"+firstName.toString()+"~"+mailId);
  		}
				
		PageBean pageBean = new PageBean("Merchant Detail", "merchant/pendingmerchant/merchanteditdetails1", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		Merchant merchant = merchantService.loadMerchantByPk(id);
		
		RegAddMerchant ram=new RegAddMerchant();
		
	ram.setId(merchant.getId().toString());
		//merchant.setMerchantName(merchantName);
	if(merchant.getMid() != null){
		ram.setMid(merchant.getMid().getMid());
	}
	ram.setRegisteredName(merchant.getBusinessShortName());
	ram.setBusinessName(merchant.getBusinessName());
	ram.setBusinessRegNo(merchant.getBusinessRegistrationNumber());
	ram.setRegisteredAddress(merchant.getBusinessAddress1());
	ram.setBusinessAddress(merchant.getBusinessAddress2());
	ram.setMailingAddress(merchant.getBusinessAddress3());
	ram.setSalutation(merchant.getSalutation());
	ram.setName(merchant.getContactPersonName());
	ram.setContactNo(merchant.getContactPersonPhoneNo());
	ram.setEmail(merchant.getEmail());

	
	if(merchant.getOwnerSalutation() != null){
	if(merchant.getOwnerSalutation().contains("~")){
		String sul[] = merchant.getOwnerSalutation().split("~");
		if(sul.length == 2){
			ram.setOwnerSalutation1(sul[0]);
			ram.setOwnerSalutation2(sul[1]);
		}else if(sul.length == 3){
			ram.setOwnerSalutation1(sul[0]);
			ram.setOwnerSalutation2(sul[1]);
			ram.setOwnerSalutation3(sul[2]);
		}else if(sul.length == 4){
			ram.setOwnerSalutation1(sul[0]);
			ram.setOwnerSalutation2(sul[1]);
			ram.setOwnerSalutation3(sul[2]);
			ram.setOwnerSalutation4(sul[3]);
		}else if(sul.length == 5){
			ram.setOwnerSalutation1(sul[0]);
			ram.setOwnerSalutation2(sul[1]);
			ram.setOwnerSalutation3(sul[2]);
			ram.setOwnerSalutation4(sul[3]);
			ram.setOwnerSalutation5(sul[4]);
		}
	}else{
		ram.setOwnerSalutation1(merchant.getOwnerSalutation());
		
	}
	}
	
	if(merchant.getOwnerName() != null)
	{
	if(merchant.getOwnerName().contains("~")){
		String name[] = merchant.getOwnerName().split("~");
		ram.setOwnerCount(""+name.length);
		if(name.length == 2){
			ram.setOwnerName1(name[0]);
			ram.setOwnerName2(name[1]);
		}else if(name.length == 3){
			ram.setOwnerName1(name[0]);
			ram.setOwnerName2(name[1]);
			ram.setOwnerName3(name[2]);
		}else if(name.length == 4){
			ram.setOwnerName1(name[0]);
			ram.setOwnerName2(name[1]);
			ram.setOwnerName3(name[2]);
			ram.setOwnerName4(name[3]);
		}else if(name.length == 5){
			ram.setOwnerName1(name[0]);
			ram.setOwnerName2(name[1]);
			ram.setOwnerName3(name[2]);
			ram.setOwnerName4(name[3]);
			ram.setOwnerName5(name[4]);
		}
	}else{
		ram.setOwnerCount("1");
		
		ram.setOwnerName1(merchant.getOwnerName());
	}
	}
			
			if(merchant.getOwnerPassportNo()!=null)
			{
	if(merchant.getOwnerPassportNo().contains("~")){
		String pass[] = merchant.getOwnerPassportNo().split("~");

		if(pass.length == 2){
			ram.setPassportNo1(pass[0]);
			ram.setPassportNo2(pass[1]);
		}else if(pass.length == 3){
			ram.setPassportNo1(pass[0]);
			ram.setPassportNo2(pass[1]);
			ram.setPassportNo3(pass[2]);
		}else if(pass.length == 4){
			ram.setPassportNo1(pass[0]);
			ram.setPassportNo2(pass[1]);
			ram.setPassportNo3(pass[2]);
			ram.setPassportNo4(pass[3]);
		}else if(pass.length == 5){
			ram.setPassportNo1(pass[0]);
			ram.setPassportNo2(pass[1]);
			ram.setPassportNo3(pass[2]);
			ram.setPassportNo4(pass[3]);
			ram.setPassportNo5(pass[4]);
		}
	}else{
		ram.setPassportNo1(merchant.getOwnerPassportNo());
		
	}
			}
			
			
			if(merchant.getResidentialAddress() != null)
			{
	
	if(merchant.getResidentialAddress().contains("~")){
		String addr[] = merchant.getResidentialAddress().split("~");
		if(addr.length == 2){
			ram.setResidentialAddress1(addr[0]);
			ram.setResidentialAddress2(addr[1]);
		}else if(addr.length == 3){
			ram.setResidentialAddress1(addr[0]);
			ram.setResidentialAddress2(addr[1]);
			ram.setResidentialAddress3(addr[2]);
		}else if(addr.length == 4){
			ram.setResidentialAddress1(addr[0]);
			ram.setResidentialAddress2(addr[1]);
			ram.setResidentialAddress3(addr[2]);
			ram.setResidentialAddress4(addr[3]);
		}else if(addr.length == 5){
			ram.setResidentialAddress1(addr[0]);
			ram.setResidentialAddress2(addr[1]);
			ram.setResidentialAddress3(addr[2]);
			ram.setResidentialAddress4(addr[3]);
			ram.setResidentialAddress5(addr[4]);
		}
	}else{
		ram.setResidentialAddress1(merchant.getResidentialAddress());
		
	}
			}
			
			if(merchant.getOwnerContactNo() != null)

			{
	if(merchant.getOwnerContactNo().contains("~")){
		String contact[] = merchant.getOwnerContactNo().split("~");
		if(contact.length == 2){
			ram.setOwnerContactNo1(contact[0]);
			ram.setOwnerContactNo2(contact[1]);
		}else if(contact.length == 3){
			ram.setOwnerContactNo1(contact[0]);
			ram.setOwnerContactNo2(contact[1]);
			ram.setOwnerContactNo3(contact[2]);
		}else if(contact.length == 4){
			ram.setOwnerContactNo1(contact[0]);
			ram.setOwnerContactNo2(contact[1]);
			ram.setOwnerContactNo3(contact[2]);
			ram.setOwnerContactNo4(contact[3]);
		}else if(contact.length == 5){
			ram.setOwnerContactNo1(contact[0]);
			ram.setOwnerContactNo2(contact[1]);
			ram.setOwnerContactNo3(contact[2]);
			ram.setOwnerContactNo4(contact[3]);
			ram.setOwnerContactNo5(contact[4]);
		}
	}else{
		ram.setOwnerContactNo1(merchant.getOwnerContactNo());
		
	}
	
			}
	
	
	ram.setOfficeEmail(merchant.getUsername());
	ram.setWebsite(merchant.getWebsite());
	ram.setOfficeNo(merchant.getBusinessContactNumber());
	ram.setFaxNo(merchant.getFaxNo());
	ram.setBusinessType(merchant.getBusinessType());
	ram.setCompanyType(merchant.getCompanyType());
	ram.setNatureOfBusiness(merchant.getNatureOfBusiness());
	ram.setBankName(merchant.getBankName());
	ram.setBankAccNo(merchant.getBankAcc());
	
	ram.setDocuments(merchant.getPermiseType());
	
	ram.setBusinessState(merchant.getState());
	
	ram.setBusinessCity(merchant.getCity());
	ram.setBusinessPostCode(merchant.getPostcode());
	
	
	ram.setReferralId(merchant.getReferralId());
	ram.setWavierMonth(merchant.getWaiverMonth());
	ram.setTradingName(merchant.getTradingName());
	ram.setNoOfReaders(merchant.getReaderSerialNo());
	ram.setYearIncorporated(merchant.getYearIncorporated());
	ram.setSignedPackage(merchant.getSignedPackage());
	ram.setStatusRemarks(merchant.getRemarks());
	//String status = merchant.getStatus().toString();
	ram.setStatus(merchant.getStatus().toString());
	
		String merchId= merchant.getId().toString();
	
	List<FileUpload> listFile = merchantService.loadFileDet(merchId);
	
	logger.info("Merchant Id :" +merchId+"  File size:"+listFile.size());
	
	int i=0;
	for(FileUpload fu : listFile){
		if(i== 0){
			ram.setFormFName(fu.getFileName());
			i++;
		}else if(i == 1){
			ram.setDocFName(fu.getFileName());
			i++;
			
		}else if(i ==2){
			ram.setPayFName(fu.getFileName());
			i++;
			
		}
	}

	
	String agentName ="";
	
	if(merchant.getAgID()!= null)
	{
	
		Agent agent = agentService.loadAgentByPk(Long.parseLong(merchant.getAgID().toString()));
		if(agent != null){
			agentName = "AGENT~"+agent.getFirstName()+"~"+agent.getUsername();
		}
	}
	if(merchant.getSubAgID()!= null)
	{
	
	SubAgent subAgent1 = subAgentService.loadSubAgentByPk(Long.parseLong(merchant.getSubAgID().toString()));
	
		if(subAgent1 != null)
		{
			agentName = "SUBAGENT~"+subAgent1.getName() + "~" + subAgent1.getMailId();
		}
	}
	ram.setAgentName(agentName);
	
	ram.setPreAuth(merchant.getPreAuth());
	ram.setAutoSettled(merchant.getAutoSettled());
	ram.setMdr(merchant.getMdr());
	
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", ram);
		model.addAttribute("agentNameList", agentNameList);
		
		request.getSession(true).setAttribute("editMerchantSession", ram);
		return TEMPLATE_DEFAULT;
	}
	@RequestMapping(value = {"/editMerchant" }, method = RequestMethod.POST)
	public String processEditUserForm(

			final HttpServletRequest request, 
			final ModelMap model, 
			final RegAddMerchant regAddMerchant,
			final Principal principal,
			@RequestParam("id") final Long id,
	        @RequestParam("mid") final String mid,
			@RequestParam("businessName") final String businessName,
			
			@RequestParam("registeredName") final String registeredName,
			@RequestParam("businessRegNo") final String businessRegNo,
			@RequestParam("registeredAddress") final String registeredAddress,
			@RequestParam("mailingAddress") final String mailingAddress,
			@RequestParam("businessAddress") final String businessAddress,
			
			
			
			@RequestParam("name") final String name,
			@RequestParam("salutation") final String salutation,
			@RequestParam("contactNo") final String contactNo,
			
			@RequestParam("email") final String email,
			@RequestParam("ownerCount")final String ownerCount,
			@RequestParam("ownerName1") final String ownerName1,
			@RequestParam("ownerSalutation1") final String ownerSalutation1,
			@RequestParam("passportNo1") final String passportNo1,
			@RequestParam("ownerContactNo1") final String ownerContactNo1,
			@RequestParam("residentialAddress1") final String residentialAddress1,
			
			
			@RequestParam("ownerSalutation2") final String ownerSalutation2,
			@RequestParam("ownerName2") final String ownerName2,
			@RequestParam("passportNo2") final String passportNo2,
			@RequestParam("ownerContactNo2") final String ownerContactNo2,
			@RequestParam("residentialAddress2") final String residentialAddress2,
			
			
			@RequestParam("ownerSalutation3") final String ownerSalutation3,
			@RequestParam("ownerName3") final String ownerName3,	
			@RequestParam("passportNo3") final String passportNo3,
			@RequestParam("ownerContactNo3") final String ownerContactNo3,
			@RequestParam("residentialAddress3") final String residentialAddress3,
			
			
			@RequestParam("ownerSalutation4") final String ownerSalutation4,
			@RequestParam("ownerName4") final String ownerName4,
			@RequestParam("passportNo4") final String passportNo4,
			@RequestParam("ownerContactNo4") final String ownerContactNo4,
			@RequestParam("residentialAddress4") final String residentialAddress4,
			
			
			@RequestParam("ownerSalutation5") final String ownerSalutation5,
			@RequestParam("ownerName5") final String ownerName5,
			@RequestParam("passportNo5") final String passportNo5,
			@RequestParam("ownerContactNo5") final String ownerContactNo5,
			@RequestParam("residentialAddress5") final String residentialAddress5,
			
			@RequestParam("officeEmail") final String officeEmail,
			@RequestParam("website") final String website,
			@RequestParam("officeNo") final String officeNo,
			@RequestParam("faxNo") final String faxNo,	
			@RequestParam("businessCity") final String businessCity,
			@RequestParam("businessPostCode") final String businessPostCode,
			@RequestParam("businessState") final String businessState,
			@RequestParam("businessType") final String businessType,
			@RequestParam("documents") final String documents,
			@RequestParam("companyType") final String companyType,
			@RequestParam("natureOfBusiness") final String natureOfBusiness,
			@RequestParam("signedPackage") final String signedPackage,
			@RequestParam("tradingName") final String tradingName,		
			@RequestParam("noOfReaders") final String noOfReaders,
			@RequestParam("referralId") final String referralId,
			@RequestParam("bankName") final String bankName,
			@RequestParam("bankAccNo") final String bankAccNo,
			@RequestParam("wavierMonth") final String wavierMonth,
			@RequestParam("yearIncorporated")final String yearIncorporated,
			
			@RequestParam("status")final String status,
			@RequestParam("statusRemarks")final String statusRemarks,
			@RequestParam("agentName") final String agentName,
			@RequestParam("preAuth") final String preAuth,
			@RequestParam("autoSettled")final String autoSettled,
			@RequestParam("mdr") final String mdr,
			@RequestParam(required = false) String formFName,
			@RequestParam(required = false) String docFName,
			@RequestParam(required = false) String payFName,
			@RequestParam(required = false) MultipartFile formFile,
			@RequestParam(required = false) MultipartFile docFile,
			@RequestParam(required = false) MultipartFile payFile
			
			
			
			
			

	) {
		logger.info("Request to display merchant edit ");
		Merchant merchant1 = null;
		Merchant bmerchant=merchantService.loadMerchantByPk(Long.parseLong(regAddMerchant.getId()));
/*		if(bmerchant.getMid() != null){
		
		logger.info("Merchant MId ::::"+ bmerchant.getMid().getMid());
		}
*/		
		if(bmerchant.getUsername() != null){
			if(!(bmerchant.getUsername().equals(regAddMerchant.getOfficeEmail()))){
				 merchant1 = merchantService.loadMerchantbyEmail(regAddMerchant.getOfficeEmail());
			}
		}
		
     	MID mid1 = null;
 		if(bmerchant.getMid() == null){
		 mid1=merchantService.loadMid(regAddMerchant.getMid());
		}
		
		PageBean pageBean = null;
		
		 if(mid1!=null )
			{
			
				pageBean = new PageBean(" Mid add Details", "merchant/pendingmerchant/merchanteditdetails1",
						Module.MERCHANT, "merchant/sideMenuMerchant");
				model.addAttribute("responseData", "MID already Exist");//table response
				model.addAttribute("pageBean",pageBean);
				model.addAttribute("regAddMerchant",regAddMerchant);
				
				return TEMPLATE_DEFAULT;
			}
		 
		 
		 else if(merchant1 != null) {
				 pageBean = new PageBean(" Agent add Details", "merchant/pendingmerchant/merchanteditdetails1",
						Module.MERCHANT, "merchant/sideMenuMerchant");
				 
				model.addAttribute("responseData", " Office Email already exist"); //table response
				
				model.addAttribute("responseData1","merchantName required");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("regAddMerchant", regAddMerchant);
				
				return TEMPLATE_DEFAULT;
			} 
			else
			{
		PageBean pageBean1 = new PageBean("Merchant user Edit Details", "merchant/pendingmerchant/merchantseditdetails1",
				Module.MERCHANT, "merchant/sideMenuMerchant");
		RegAddMerchant merchant = new RegAddMerchant();
		merchant.setId(id.toString());
		merchant.setMid(mid);
		merchant.setRegisteredName(registeredName);
        merchant.setBusinessName(businessName);	
        merchant.setBusinessRegNo(businessRegNo);
        merchant.setRegisteredAddress(registeredAddress);
        merchant.setBusinessAddress(businessAddress);
        merchant.setMailingAddress(mailingAddress);
        merchant.setName(name);
        merchant.setContactNo(contactNo);
        merchant.setEmail(email);
        merchant.setSalutation(salutation);
        merchant.setOwnerName1(ownerName1);
        merchant.setPassportNo1(passportNo1);
        merchant.setOwnerContactNo1(ownerContactNo1);
        merchant.setOwnerSalutation1(ownerSalutation1);
        merchant.setResidentialAddress1(residentialAddress1);
        merchant.setOfficeEmail(officeEmail);
        merchant.setWebsite(website);
        merchant.setOfficeNo(officeNo);
        merchant.setFaxNo(faxNo);
        merchant.setBusinessType(businessType);
        merchant.setCompanyType(companyType);
        merchant.setNatureOfBusiness(natureOfBusiness);
        merchant.setBankName(bankName);
        merchant.setBankAccNo(bankAccNo);
        merchant.setDocuments(documents);
        merchant.setBusinessState(businessState);
        merchant.setBusinessCity(businessCity);
        merchant.setBusinessPostCode(businessPostCode);
        merchant.setReferralId(referralId);
        merchant.setTradingName(tradingName);
        merchant.setSignedPackage(signedPackage);
        merchant.setYearIncorporated(yearIncorporated);
        merchant.setWavierMonth(wavierMonth);
        merchant.setStatus(status);
        merchant.setStatusRemarks(statusRemarks);
        merchant.setAgentName(agentName);
        merchant.setOwnerSalutation2(ownerSalutation2);
        merchant.setOwnerName2(ownerName2);
        merchant.setPassportNo2(passportNo2);
        merchant.setOwnerContactNo2(ownerContactNo2);
        merchant.setResidentialAddress2(residentialAddress2);
        
        merchant.setOwnerSalutation3(ownerSalutation3);
        merchant.setOwnerName3(ownerName3);
        merchant.setPassportNo3(passportNo3);
        merchant.setOwnerContactNo3(ownerContactNo3);
        merchant.setResidentialAddress3(residentialAddress3);
        
        merchant.setOwnerSalutation4(ownerSalutation4);
        merchant.setOwnerName4(ownerName4);
        merchant.setPassportNo4(passportNo4);
        merchant.setOwnerContactNo4(ownerContactNo4);
        merchant.setResidentialAddress4(residentialAddress4);
        merchant.setOwnerSalutation5(ownerSalutation5);
        merchant.setOwnerName5(ownerName5);
        merchant.setPassportNo5(passportNo5);
        merchant.setOwnerContactNo5(ownerContactNo5);
        merchant.setResidentialAddress5(residentialAddress5);
        merchant.setMdr(mdr);
        merchant.setNoOfReaders(noOfReaders);
        merchant.setPreAuth(preAuth);
        merchant.setAutoSettled(autoSettled);
        merchant.setOwnerCount(ownerCount);
        
        if(formFName != null){
        	merchant.setFormFName(formFName);
        }
        if(docFName != null){
        	merchant.setDocFName(docFName);
        	
        }
        
        if(payFName != null){
        	merchant.setPayFName(payFName);
        }
        
        
        String fileIds = null;
		 
		 if (formFile != null) {
			 FileOutputStream fos = null;
			 BufferedOutputStream stream = null;
			 byte[] bytes = null;
			 String dirData= null;
			 String fileData = null;
				try {
					 bytes = formFile.getBytes();

					// Creating the directory to store file
					//String rootPath = System.getProperty("catalina.home");
					Date fileid = new Date();
					String fileId = new SimpleDateFormat("yyyyMMddhhmmss").format(fileid);
					
					String adminName=principal.getName();
					Date dt = new Date();
					String date = new SimpleDateFormat("yyyyMMdd").format(dt);
					String rootPath = PropertyLoader.getFile().getProperty("ADMINPATH");
					//File dir = new File(rootPath + File.separator + adminName+ File.separator+ merchant.getBusinessName()+ File.separator+date);
					dirData = rootPath + File.separator + merchant.getBusinessName()+ File.separator+date;
					File dir = new File(dirData);
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					/*File serverFile = new File(dir.getAbsolutePath()
							+ File.separator + formFile.getOriginalFilename());*/
					fileData = dir.getAbsolutePath()+ File.separator + formFile.getOriginalFilename();
					File serverFile = new File(fileData);
					fos = new FileOutputStream(serverFile);
					stream = new BufferedOutputStream(
							fos);
					stream.write(bytes);
					stream.close();
					fos.close();
					
					FileUpload fileUpload = new FileUpload();
					
					fileUpload.setFileId(fileId);
					fileUpload.setFileName(formFile.getOriginalFilename());
					fileUpload.setFilePath(serverFile.getAbsolutePath());
					fileUpload.setMerchantId("");
					fileUpload.setMerchantName(merchant.getBusinessName());
					fileUpload.setCreatedBy(adminName);
					fileUpload.setCreatedDate(new Date());
					
					FileUpload fileUpload1 =merchantService.storeFileUpload(fileUpload);
					
					merchant.setFormFName(formFile.getOriginalFilename());
					
					logger.info("Server File Location="
							+ formFile.getContentType());
					logger.info("Server File Location="
							+ formFile.getOriginalFilename());
					logger.info("Server File Location="
							+ serverFile.getAbsolutePath());
					logger.info("Server File Location="
							+ fileUpload1.getId());
					
					//regAddMerchant.setFileId(fileUpload1.getId().toString());
					
					fileIds = fileUpload1.getId().toString();

					//return "You successfully uploaded file=" + regAddMerchant.getFile().getName();
				} catch (Exception e) {
					//return "You failed to upload " + regAddMerchant.getFile().getName() + " => " + e.getMessage();
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
			}/* else {
				return "You failed to upload " + regAddMerchant.getFile().getName()
						+ " because the file was empty.";
			}*/
		
		 //end Form File
		 
		 
		 
		 //start doc File
		 if (docFile != null) {
			 FileOutputStream fos = null;
			 BufferedOutputStream stream = null;
			 byte[] bytes = null;
			 String dirData= null;
			 String fileData = null;
				try {
					 bytes = docFile.getBytes();

					// Creating the directory to store file
					//String rootPath = System.getProperty("catalina.home");
					Date fileid = new Date();
					String fileId = new SimpleDateFormat("yyyyMMddhhmmss").format(fileid);
					
					String adminName=principal.getName();
					Date dt = new Date();
					String date = new SimpleDateFormat("yyyyMMdd").format(dt);
					String rootPath = PropertyLoader.getFile().getProperty("ADMINPATH");
					//File dir = new File(rootPath + File.separator + adminName+ File.separator+ merchant.getBusinessName()+ File.separator+date);
					dirData = rootPath + File.separator + merchant.getBusinessName()+ File.separator+date;
					File dir = new File(dirData);
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					fileData= dir.getAbsolutePath()+ File.separator + docFile.getOriginalFilename();
					File serverFile = new File(fileData);
					fos = new FileOutputStream(serverFile);
					stream = new BufferedOutputStream(
							fos);
					stream.write(bytes);
					stream.close();
					fos.close();
					
					FileUpload fileUpload = new FileUpload();
					
					fileUpload.setFileId(fileId);
					fileUpload.setFileName(docFile.getOriginalFilename());
					fileUpload.setFilePath(serverFile.getAbsolutePath());
					fileUpload.setMerchantId("");
					fileUpload.setMerchantName(merchant.getBusinessName());
					fileUpload.setCreatedBy(adminName);
					fileUpload.setCreatedDate(new Date());
					
					FileUpload fileUpload1 =merchantService.storeFileUpload(fileUpload);
					
					merchant.setDocFName(docFile.getOriginalFilename());
					
					logger.info("Server File Location="
							+ docFile.getContentType());
					logger.info("Server File Location="
							+ docFile.getOriginalFilename());
					logger.info("Server File Location="
							+ serverFile.getAbsolutePath());
					logger.info("Server File Location="
							+ fileUpload1.getId());
					
					//regAddMerchant.setFileId(fileUpload1.getId().toString());
					
					if(fileIds != null){
						
						fileIds = fileIds+"~"+fileUpload1.getId().toString();
					}else{
						fileIds = fileUpload1.getId().toString();
					}

					//return "You successfully uploaded file=" + regAddMerchant.getFile().getName();
				} catch (Exception e) {
					//return "You failed to upload " + regAddMerchant.getFile().getName() + " => " + e.getMessage();
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
			}/* else {
				return "You failed to upload " + regAddMerchant.getFile().getName()
						+ " because the file was empty.";
			}*/
		 
		 //end doc file
		 
		 //start pay File
		 if (payFile != null) {
			 FileOutputStream fos = null;
			 BufferedOutputStream stream = null;
			 byte[] bytes = null;
			 String dirData= null;
			 String fileData = null;
				try {
					 bytes = payFile.getBytes();

					// Creating the directory to store file
					//String rootPath = System.getProperty("catalina.home");
					Date fileid = new Date();
					String fileId = new SimpleDateFormat("yyyyMMddhhmmss").format(fileid);
					
					String adminName=principal.getName();
					Date dt = new Date();
					String date = new SimpleDateFormat("yyyyMMdd").format(dt);
					String rootPath = PropertyLoader.getFile().getProperty("ADMINPATH");
					//File dir = new File(rootPath + File.separator + adminName+ File.separator+ merchant.getBusinessName()+ File.separator+date);
					dirData = rootPath + File.separator + merchant.getBusinessName()+ File.separator+date;
					File dir = new File(dirData);
					if (!dir.exists())
						dir.mkdirs();

					// Create the file on server
					fileData = dir.getAbsolutePath()+ File.separator + payFile.getOriginalFilename();
					File serverFile = new File(fileData);
					fos = new FileOutputStream(serverFile);
					stream = new BufferedOutputStream(
							fos);
					stream.write(bytes);
					stream.close();
					fos.close();
					FileUpload fileUpload = new FileUpload();
					
					fileUpload.setFileId(fileId);
					fileUpload.setFileName(payFile.getOriginalFilename());
					fileUpload.setFilePath(serverFile.getAbsolutePath());
					fileUpload.setMerchantId("");
					fileUpload.setMerchantName(merchant.getBusinessName());
					fileUpload.setCreatedBy(adminName);
					fileUpload.setCreatedDate(new Date());
					
					FileUpload fileUpload1 =merchantService.storeFileUpload(fileUpload);
					
					merchant.setPayFName(payFile.getOriginalFilename());
					
					logger.info("Server File Location="
							+ payFile.getContentType());
					logger.info("Server File Location="
							+ payFile.getOriginalFilename());
					logger.info("Server File Location="
							+ serverFile.getAbsolutePath());
					logger.info("Server File Location="
							+ fileUpload1.getId());
					
					//regAddMerchant.setFileId(fileUpload1.getId().toString());
					if(fileIds != null){
						
						fileIds = fileIds+"~"+fileUpload1.getId().toString();
					}else{
						fileIds = fileUpload1.getId().toString();
					}

					//return "You successfully uploaded file=" + regAddMerchant.getFile().getName();
				} catch (Exception e) {
					//return "You failed to upload " + regAddMerchant.getFile().getName() + " => " + e.getMessage();
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
		 if(fileIds != null){
			 merchant.setFileId(fileIds);
		 }
        
        model.addAttribute("responseData", null);
        
      
	
		//RegAddMerchant ram = (RegAddMerchant) request.getSession(true).getAttribute("editMerchantSession");
		model.addAttribute("pageBean", pageBean);
		//model.addAttribute("merchant", merchant);
		model.addAttribute("merchant", merchant);
		//PCI
		request.getSession(true).setAttribute("editMerchantSession", merchant);
		//request.getSession(true).setAttribute("editMerchantSession", merchant);
			}
		return "redirect:/merchant1/editReviewandConfirm";

	}

	/**
	 * Display merchant edit successful
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = {"/editReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditMerchantReview(final Model model, final HttpServletRequest request) {
		logger.info("about to edit Merchant Details ReviewAndConfirm");
		RegAddMerchant merchant = (RegAddMerchant) request.getSession(true).getAttribute("editMerchantSession");
		if (merchant == null) {
			return "redirect:" + URL_BASE + "/editMerchant/1";
			
		}
		
		PageBean pageBean = new PageBean("Merchant Edit Review Detail", "merchant/pendingmerchant/merchantreviewandconfirm1",
				Module.MERCHANT, "merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", merchant);
		return TEMPLATE_DEFAULT;
	}

	
	//changed code 1052016
	
	@RequestMapping(value = {"/editReviewandConfirm"}, method = RequestMethod.POST)
	public String confirmEditMerchant(@ModelAttribute("merchant") final Merchant merchant, final Model model,
			final HttpServletRequest request,RegAddMerchant regAddMerchant) {
		logger.info("about to edit Merchant Details Confirms");
		PageBean pageBean = new PageBean("Successfully Merchant edited", "merchant/pendingmerchant/editMerchantAllDoneSucessfull",
				Module.MERCHANT, "merchant/pendingmerchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		RegAddMerchant merchantSavedInHttpSession = (RegAddMerchant) request.getSession(true).getAttribute("editMerchantSession");
		if (merchantSavedInHttpSession == null) {
			return "redirect:/merchant1/editReviewandConfirm";
		}
		model.addAttribute(merchantSavedInHttpSession);
		Merchant merchant1 = merchantService.loadMerchantByPk(Long.parseLong(merchantSavedInHttpSession.getId()));
		String mail = "YES";
		merchantService.updateMerchant(merchantSavedInHttpSession,merchant1,mail);
		model.addAttribute("merchant", merchantSavedInHttpSession);
		request.getSession(true).removeAttribute("editMerchantSession");

        return TEMPLATE_DEFAULT;
	}
	







}
