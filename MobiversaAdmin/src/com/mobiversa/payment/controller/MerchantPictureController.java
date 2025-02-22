package com.mobiversa.payment.controller;


import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.RegAddMerchant;
import com.mobiversa.payment.service.MerchantProfileService;
import com.mobiversa.payment.service.MerchantService;
@Controller
@RequestMapping(value = MerchantPictureController.URL_BASE)
public class MerchantPictureController extends BaseController{
	
	public static final String URL_BASE = "/merchantUpldProfile";
	@Autowired
	private MerchantProfileService merchantProfService;
	
	@Autowired
	private MerchantService merchantService;
	static final Logger logger = Logger.getLogger(MerchantPictureController.class.getName());
	
	
	//@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {

		return "redirect:" + URL_BASE + "/list/1";
	}
	
	
	
	
	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayMerchantList(final Model model,final Merchant merchant, @PathVariable final int currPage,final java.security.Principal principal) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("Merchant", "merchant/merchantList", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());
		PaginationBean<Merchant> paginationBean = new PaginationBean<Merchant>();
		paginationBean.setCurrPage(currPage);
		merchantService.listMerchant(paginationBean);

		model.addAttribute("paginationBean", paginationBean);
		
		if(principal.getName().equals("bhuvi@mobiversa.com"))
		{
			return TEMPLATE_SUPER_AGENT;
		}
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}
	
	
/*	@RequestMapping(value = { "/addMerchantPicture" }, method = RequestMethod.GET)
	public String merchantDetails(final Model model, final Principal principal, final RegAddMerchant regAddMerchant,@PathVariable final long id) {
	
		
	
		//Merchant merchant = merchantProfService.loadMerchant(principal.getName());
		logger.info("merchantprofile add page testing:" + principal.getName());
		
		Merchant merchant = merchantService.loadMerchantByPk(id);
		//regAddMerchant.setBusinessName(merchant.getBusinessName());
		
		//regAddMerchant.setMid(merchant.getMid().getMid());
		//logger.info("display merchant name:" + merchant.getBusinessName());
		//RegAddMerchant regAddMerchant1 = new RegAddMerchant();
		StringBuffer sf = new StringBuffer();
		String filePath1 = null;
		try {
			filePath1 = merchant.getMerchantProfile();
			logger.info("display file path:" + filePath1);
			File img = new File(filePath1);
			logger.info("display file path1:" + "filePath");
			logger.info("display file path2:" + img);
			FileInputStream fs = new FileInputStream(img);
			byte imagedata[] = new byte[(int) img.length()];
			fs.read(imagedata);
			String imgRead1 = Base64.encode(imagedata);
			
			regAddMerchant.setMerchantLogo(imgRead1);
			 //logger.info("merhcant file path:" + regAddMerchant.getMerchantLogo());
	
		fs.close();
		}  catch (IOException e) {
		e.printStackTrace();
		
	
	}
		
		PageBean pageBean = new PageBean("Merchant Detail", "merchantweb/merchantProfile/uploadProfile/upldProf", Module.PROMOTION,
				"merchant/sideMenuMerchant");
		
		
		logger.info("merchantprofile add page testing1313131:");

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("regAddMerchant", regAddMerchant);

		
	
		return TEMPLATE_MERCHANT;
	}*/

	/*	@RequestMapping(value = { "/addMerchantPicture" }, method = RequestMethod.POST)
		public String merchantProfile(final Model model, final Principal principal, HttpServletRequest request,
				final RegAddMerchant regAddMerchant) {
			
	    Merchant merchant = merchantProfService.loadMerchant(principal.getName());
	    logger.info("merchantprofile add page testing:" + merchant.getBusinessName());
	    
	    
	    

	    regAddMerchant.setId(merchant.getId().toString());
	    regAddMerchant.setMid(merchant.getMid().getMid());
		regAddMerchant.setBusinessName(merchant.getBusinessName());
		regAddMerchant.setOfficeEmail(merchant.getUsername());
		RegAddMerchant regAddMerchant1 = new RegAddMerchant();
		StringBuffer sf = new StringBuffer();
		String filePath1 = null;
		try {
			filePath1 = merchant.getMerchantProfile();
			logger.info("display file path:" + filePath1);
			File img = new File(filePath1);
			logger.info("display file path1:" + "filePath");
			logger.info("display file path2:" + img);
			FileInputStream fs = new FileInputStream(img);
			byte imagedata[] = new byte[(int) img.length()];
			fs.read(imagedata);
			String imgRead1 = Base64.encode(imagedata);
			
			regAddMerchant.setMerchantLogo(imgRead1);
			 logger.info("merhcant file path:" + regAddMerchant.getMerchantLogo());
	
		fs.close();
		}  catch (IOException e) {
		e.printStackTrace();
	}
		
	    
		String imgRead = null;
		
		String filePath = null;
		BufferedImage img1 = null;
		BufferedImage img2 = null;

		PageBean pageBean = null;
		logger.info("merchantprofile add page testing:" + merchant.getBusinessName());
		// promotion image file1
		if (!regAddMerchant.getImageFile().isEmpty()) {
			try {
				byte[] bytes = regAddMerchant.getImageFile().getBytes();
				Date fileid = new Date();
				String fileId = new SimpleDateFormat("yyyyMMddhhmmss").format(fileid);
				Date dt = new Date();
				String date = new SimpleDateFormat("yyyyMMdd").format(dt);
				String rootPath = PropertyLoader.getFile().getProperty("MERCHANTPROMO");
				File dir = new File(
						rootPath + File.separator + merchant.getMid().getMid() + File.separator + date);
				if (!dir.exists())
					dir.mkdirs();
				File serverFile = new File(
						dir.getAbsolutePath() + File.separator + regAddMerchant.getImageFile().getOriginalFilename());
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				img1 = ImageIO.read(new File(dir.getAbsolutePath() + File.separator
						+ regAddMerchant.getImageFile().getOriginalFilename()));
				try {
					File img = new File(dir.getAbsolutePath() + File.separator
							+ regAddMerchant.getImageFile().getOriginalFilename());
					filePath = dir.getAbsolutePath() + File.separator
							+ regAddMerchant.getImageFile().getOriginalFilename();
					FileInputStream fs = new FileInputStream(img);
					byte imagedata[] = new byte[(int) img.length()];
					fs.read(imagedata);
					imgRead = Base64.encode(imagedata);
					fs.close();
					regAddMerchant1.setMerchantImgFilePath(dir.getAbsolutePath() + File.separator
							+ regAddMerchant.getImageFile().getOriginalFilename());
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
			}
		}
		regAddMerchant1.setMerchantImage1(imgRead.getBytes());

		regAddMerchant1.setMerchantProfile(imgRead);
		
		
	   logger.info("merchant profile:" + regAddMerchant1.getMerchantImage1().toString());
	   
	   logger.info("check merchant profile1:" + regAddMerchant1.getMerchantProfile());
	
	
	   
	   regAddMerchant1.setMid(regAddMerchant.getMid());
	   regAddMerchant1.setBusinessName(regAddMerchant.getBusinessName());
	   regAddMerchant1.setId(regAddMerchant.getId());
		
	   regAddMerchant1.setOfficeEmail(regAddMerchant.getOfficeEmail());
	   regAddMerchant1.setMerchantLogo(regAddMerchant.getMerchantLogo());
	   
	   logger.info("check emrchant logo dto file:" + regAddMerchant1.getMerchantLogo());
	   
	  
		model.addAttribute("pageBean", pageBean);
		//model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("regAddMerchant", regAddMerchant1);
		request.getSession(true).setAttribute("addMerchantProfileSession", regAddMerchant1);
		return "redirect:/merchantUpldProfile/merchantProfileDetailsReviewAndConfirm";
	}*/
		
		
		@RequestMapping(value = { "/merchantProfileDetailsReviewAndConfirm" }, method = RequestMethod.GET)
		public String displayAddUserConfirmation(final ModelMap model, final HttpServletRequest request,
				final RegAddMerchant regAddMerchant, final Principal principal) {
			logger.info("merchantprofile add page testing:");
			RegAddMerchant regMerchant = (RegAddMerchant) request.getSession(true)
					.getAttribute("addMerchantProfileSession");
			if (regMerchant == null) {
				return "redirect:" + URL_BASE + "/list/1";
			}
			PageBean pageBean = new PageBean("Merchant user add Details",
					"merchantweb/merchantProfile/uploadProfile/addProfileReviewandConfirm", Module.PROMOTION,
					"merchant/sideMenuMerchant");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("regAddMerchant", regMerchant);
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}

		@RequestMapping(value = { "/merchantProfileDetailsReviewAndConfirm" }, method = RequestMethod.POST)
		public String confirmAddMerchant(@ModelAttribute("merchantPromo1") final Merchant merchant, final Model model,
				final RegAddMerchant regAddMerchant, final HttpServletRequest request, final Principal principal) {
			
			logger.info("merchantprofile add page testing13321312:");
			PageBean pageBean = new PageBean("Succefully New Merchant Added",
					"merchantweb/merchantProfile/uploadProfile/upldProfSuccess", Module.PROMOTION,
					"merchant/sideMenuMerchant");
			logger.info("about to add Merchant Details Confirms");
			model.addAttribute("pageBean", pageBean);

			RegAddMerchant addMerchantProfileSession = (RegAddMerchant) request.getSession(true)
					.getAttribute("addMerchantProfileSession");

			if (addMerchantProfileSession == null) {
				return "redirect:/promotionweb1/addMerchantPromotion";
			}

			model.addAttribute(addMerchantProfileSession);
			logger.info("about to add Merchant Details Confirms12133");
			merchantProfService.addMerchantProf(addMerchantProfileSession);
			// merchantService.addMerchantPromo(addMerchantPromotionSession);
			logger.info("about to add Merchant Details Confirms4444444");
			model.addAttribute("regAddMerchant", addMerchantProfileSession);
			logger.info("about to add Merchant Details Confirms77777777");
			request.getSession(true).removeAttribute("addMerchantProfileSession");
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}


}
