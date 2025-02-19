package com.mobiversa.payment.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

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

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantDetails;
import com.mobiversa.common.bo.Promotion;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.MerchtCustMail;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.PromotionService;
import com.mobiversa.payment.util.PropertyLoader;

@SuppressWarnings("unused")
@Controller
@RequestMapping(value = NonMerchantWebPromotionController.URL_BASE)
public class NonMerchantWebPromotionController extends BaseController {

	public static final String URL_BASE = "/promotionwebNonmerchant";

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private PromotionService promotionService;

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {

		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String listMerchantPromotions(final Model model, @PathVariable final int currPage,
			final java.security.Principal principal, final HttpServletRequest request) {
		logger.info("Merchant upload list:");
		PageBean pageBean = new PageBean("MerchantWeb Promotion", "nonmerchantweb/promotion/nonMerchantpromotionList",
				Module.PROMOTION, "");
		model.addAttribute("pageBean", pageBean);
		String myName = principal.getName();

		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<Promotion> paginationBean = new PaginationBean<Promotion>();

		paginationBean.setCurrPage(currPage);
		promotionService.listPromotions(paginationBean, currentMerchant);

		for (Promotion mp : paginationBean.getItemList()) {

		}

		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_NONMERCHANT;
	}

	@RequestMapping(value = { "/custMailUpld" }, method = RequestMethod.GET)
	public String displayAddMerchant(@ModelAttribute("merchtCustMail") MerchtCustMail merchtCustMail, Model model,
			java.security.Principal principal) {
		 logger.info("upload customer emaile file");
		String myName = principal.getName();
		
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("checking data in /custmailupld: merchantname: "+currentMerchant.getFirstName()+": "+myName);
		
		
		/*PageBean pageBean = new PageBean("Merchant Customer Mail Upload",
				"nonmerchantweb/promotion/custmail/nonMerchantcustmailupldsuccess", Module.PROMOTION,
				"");*/
		PageBean pageBean = new PageBean("Merchant Customer Mail Upload",
				"nonmerchantweb/promotion/custmail/nonMerchantcustmailupload", Module.PROMOTION,
				"");
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("merchtCustMail", merchtCustMail);
		return TEMPLATE_NONMERCHANT;

	}

	/*@RequestMapping(value = { "/merCustMailUpld" }, method = RequestMethod.POST) // ,java.security.Principal
																					// principal
	public String processAddUserForm(@ModelAttribute("merchtCustMail") final MerchtCustMail merchtCustMail,
			java.security.Principal principal, final HttpServletRequest request, final ModelMap model,
			HttpSession session) {*/
	@RequestMapping(value = { "/merCustMailUpld" }, method = RequestMethod.POST)
	public String uploadMerchantPdfTC(/*@ModelAttribute("merchantuser") final MerchtCustMail merchtCustMail2,*/
			@RequestParam("mailFile") MultipartFile mailFile,
			@RequestParam("mid") String mid,
			@RequestParam("emailText") String emailText,
			@RequestParam("businessName") String businessName,java.security.Principal principal, 
			final HttpServletRequest request,final Model model
			
			) 
	{
	
		MerchtCustMail merchtCustMail=new MerchtCustMail();
		merchtCustMail.setMailFile(mailFile);
		merchtCustMail.setMid(mid);
		merchtCustMail.setEmailText(emailText);
		merchtCustMail.setBusinessName(businessName);
		logger.info("/merCustMailUpld "+merchtCustMail.getMid());
		// logger.info("about to Upload customer mail1");
		String myName = principal.getName();
		// already used this method
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		MerchantDetails merchantDetails = promotionService.loadMerchantPoints((currentMerchant.getMid().getMid()));
//doubt-----------------------
		/*PageBean pageBean = new PageBean("Merchant Customer Mail Upload",
				"nonmerchantweb/promotion/custmail/custMailUploadReview", Module.PROMOTION,
				null);*/
		
		PageBean pageBean = new PageBean("Merchant Customer Mail Upload",
				"nonmerchantweb/promotion/custmail/nonMerchantcustmailupldview", Module.PROMOTION,
				null);
		
		// start pay File
		MerchtCustMail merchtCustMail1 = new MerchtCustMail();

		String fileName = merchtCustMail.getMailFile().getOriginalFilename();

		if (!merchtCustMail.getMailFile().isEmpty()) {
			BufferedOutputStream stream = null;
			try {
				byte[] bytes = merchtCustMail.getMailFile().getBytes();
				Date fileid = new Date();
				String fileId = new SimpleDateFormat("yyyyMMddhhmmss").format(fileid);

				Date dt = new Date();
				String date = new SimpleDateFormat("yyyyMMdd").format(dt);
				String rootPath = PropertyLoader.getFile().getProperty("CUSTMAIL");
				File dir = new File(
						rootPath + File.separator + currentMerchant.getMid().getMid() + File.separator + date);

				if (!dir.exists())
					dir.mkdirs();
				File serverFile = new File(
						dir.getAbsolutePath() + File.separator + merchtCustMail.getMailFile().getOriginalFilename());
				//BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				String text = new String(bytes);
				StringBuffer sf = new StringBuffer();

				try (BufferedReader br = new BufferedReader(new FileReader(
						dir.getAbsolutePath() + File.separator + merchtCustMail.getMailFile().getOriginalFilename()))) {

					String sCurrentLine;

					while ((sCurrentLine = br.readLine()) != null) {
						// logger.info(sCurrentLine);
						sf.append(sCurrentLine);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
				logger.info("mail list file:" + sf.toString());
				merchtCustMail1.setCustMailList(sf.toString());
				merchtCustMail1.setmFilepath(
						dir.getAbsolutePath() + File.separator + merchtCustMail.getMailFile().getOriginalFilename());

			} catch (Exception e) {
			}finally{
				if(stream != null){
					try {
						stream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}
		// merchant logo file started
		/*
		 * if (!merchtCustMail.getImageFile().isEmpty()) { try { byte[] bytes =
		 * merchtCustMail.getImageFile().getBytes(); Date fileid = new Date();
		 * String fileId = new
		 * SimpleDateFormat("yyyyMMddhhmmss").format(fileid);
		 * 
		 * Date dt = new Date(); String date = new
		 * SimpleDateFormat("yyyyMMdd").format(dt); String rootPath =
		 * PropertyLoader.getFile().getProperty("CUSTMAIL"); File dir = new
		 * File(rootPath + File.separator + currentMerchant.getMid().getMid() +
		 * File.separator + date); if (!dir.exists()) dir.mkdirs(); File
		 * serverFile = new File( dir.getAbsolutePath() + File.separator +
		 * merchtCustMail.getImageFile().getOriginalFilename());
		 * BufferedOutputStream stream = new BufferedOutputStream(new
		 * FileOutputStream(serverFile)); stream.write(bytes); stream.close();
		 * 
		 * StringBuffer sf = new StringBuffer();
		 * 
		 * try {
		 * 
		 * File img = new File(dir.getAbsolutePath() + File.separator +
		 * merchtCustMail.getImageFile().getOriginalFilename());
		 * 
		 * FileInputStream fs = new FileInputStream(img); byte imagedata[] = new
		 * byte[(int) img.length()]; fs.read(imagedata); String imgRead =
		 * Base64.encode(imagedata); sf.append(imgRead); fs.close();
		 * logger.info("display image :" + sf);
		 * 
		 * merchtCustMail1.setMerchantLogo(sf.toString().getBytes());
		 * merchtCustMail1.setmLogo(imgRead);
		 * merchtCustMail1.setLogoFilePath(dir.getAbsolutePath() +
		 * File.separator +
		 * merchtCustMail.getImageFile().getOriginalFilename());
		 * 
		 * } catch (IOException e) { e.printStackTrace(); }
		 * 
		 * } catch (Exception e) { }
		 */
		// merchant logo file end
		// logger.info("about to merchant cust ReviewAndConfirm");
		merchtCustMail1.setMerchantId(merchantDetails.getId().toString());
		merchtCustMail1.setMid(merchantDetails.getMid());
		merchtCustMail1.setMerchantName(merchantDetails.getMerchantName());
		merchtCustMail1.setMerchantId(merchantDetails.getMerchantId());
		merchtCustMail1.setEmail(currentMerchant.getEmail());
		merchtCustMail1.setContactNo(currentMerchant.getContactPersonPhoneNo());
		merchtCustMail1.setMerchantAddress(currentMerchant.getBusinessAddress1());
		logger.info("email text box:" + merchtCustMail.getEmailText());
		if(!merchtCustMail.getEmailText().isEmpty()){
			//logger.info("if cond email text box:" + merchtCustMail.getEmailText());
			merchtCustMail1.setCustMailList(merchtCustMail.getEmailText());
		}
		//merchtCustMail1.setEmailText(merchtCustMail.getEmailText());
		

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchtCustMail1", merchtCustMail1);
		//PCI
		request.getSession(true).setAttribute("merchtCustMailSession", merchtCustMail1);
		// }

		return "redirect:/promotionwebNonmerchant/merchtCustMailReviewAndConfirm";
	}

	@RequestMapping(value = { "/merchtCustMailReviewAndConfirm" }, method = RequestMethod.GET)
	public String displayAddUserConfirmation(final ModelMap model, final HttpServletRequest request,
			java.security.Principal principal) {
		 logger.info("cust Mail Upload confirm page");
		
		 MerchtCustMail merchtCustMail = (MerchtCustMail) request.getSession(true).getAttribute("merchtCustMailSession");
		
		 
		 if (merchtCustMail == null) {
			return "redirect:" + URL_BASE + "/list/1";
		}

		PageBean pageBean = new PageBean("Merchant user add Details", "nonmerchantweb/promotion/custmail/nonMerchantcustmailupldview",
				Module.PROMOTION, "");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchtCustMail", merchtCustMail);

		return TEMPLATE_NONMERCHANT;
	}

	@RequestMapping(value = { "/merchtCustMailReviewAndConfirm" }, method = RequestMethod.POST)
	public String confirmAddMerchant(@ModelAttribute("merchtCustMail") final MerchtCustMail merchtCustMail,
			final Model model, final HttpServletRequest request, final Principal principal) {
		 logger.info("merchant email upload success");
		PageBean pageBean = new PageBean("Succefully New Merchant Added",
				"nonmerchantweb/promotion/custmail/nonMerchantcustmailupldsuccess", Module.PROMOTION, null);
		model.addAttribute("pageBean", pageBean);
		Merchant currentMerchant = merchantService.loadmobileMerchant(merchtCustMail.getMid());
		MerchtCustMail merchtCustMailSession = (MerchtCustMail) request.getSession(true)
				.getAttribute("merchtCustMailSession");
		if (merchtCustMailSession == null)
		{
			return "redirect:/promotionwebNonmerchant/custMailUpld";
		}
		model.addAttribute("merchant", merchtCustMailSession);
		promotionService.addCustMail(merchtCustMailSession,currentMerchant);
		request.getSession(true).removeAttribute("merchtCustMailSession");
		return TEMPLATE_NONMERCHANT;
	}

}
