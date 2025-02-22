package com.mobiversa.payment.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mobiversa.common.bo.HostBankDetails;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantDetails;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.dto.MerchtCustMail;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.util.PropertyLoader;

//import org.springframework.validation.Validator;
@Controller
@RequestMapping(value = MerchantWebUploadTCController.URL_BASE)
public class MerchantWebUploadTCController extends BaseController {

	@Autowired
	private MerchantService merchantService;

	public static final String URL_BASE = "/upTC";

	@RequestMapping(value = { "/uploadTermsandCond" }, method = RequestMethod.GET)
	public String uploadTermsandConditons(final Model model,
			/* @ModelAttribute("merchantuser") final MerchtCustMail regMobileUser, */
			final java.security.Principal principal) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("Merchant", "merchant/uploadTC/uploadTCDetails", Module.READER,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());

		List<Merchant> merchant = merchantService.loadMerchant();

		for (Merchant m : merchant) {
			if (m != null) {

				if (m.getBusinessName() != null && !m.getBusinessName().isEmpty()) {
					m.setBusinessName(m.getBusinessName().toUpperCase());
					// logger.info("businessname: "+m.getBusinessName());
				} else {
					m.setBusinessName("NIL");
					// logger.info("businessname: "+m.getBusinessName());
				}

			}
		}

		/*
		 * List<Merchant> merchant1 = merchantService.loadMerchant();
		 * logger.info("admin login person:" + merchant1); Set<String> merchantNameList
		 * = new HashSet<String>(); for(Merchant t : merchant1) { String businessName =
		 * t.getBusinessName();
		 * 
		 * 
		 * 
		 * try { if(t.getMid().getMid()!=null) {
		 * //logger.info("mid: "+t.getMid().getMid().toString());
		 * merchantNameList.add(businessName.toString()+"~"+t.getMid().getMid()); }
		 * 
		 * else { merchantNameList.add(businessName.toString()+"~"+"null mid"); } }
		 * catch(NullPointerException e) { //logger.info(e); }
		 * 
		 * }
		 * 
		 */

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("merchant1", merchant);

//			model.addAttribute("merchant1", merchant1); //RegMobileUser
//			model.addAttribute("merchantNameList", merchantNameList);
		// model.addAttribute("merchantList", merchantList);

		// model.addAttribute("refNoList", listRefNo);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/merchantDetailsTC" }, method = RequestMethod.GET)
	public String dispTermsandConditons(final Model model, @RequestParam("id") Long id, /*
																						 * @ModelAttribute(
																						 * "merchantuser") final
																						 * MerchtCustMail regMobileUser,
																						 */
			final java.security.Principal principal) {
		logger.info("/merchantDetailsTC: " + id);

		/*
		 * String[] array1= mid.split("~",2); mid=array1[1];
		 * System.out.println(array1[1]);
		 */
		/*
		 * for (String temp: array1){ System.out.println(temp); }
		 */
		// Merchant merchant = merchantService.loadMerchantbymid(mid);
		Merchant merchant = merchantService.loadMerchantbyid(id);
		logger.info("data: " + merchant.getMid().getMid());
		logger.info("data: " + merchant.getBusinessAddress1());
		logger.info("data: " + merchant.getBusinessName());
		logger.info("data: " + merchant.getBusinessContactNumber());
		logger.info("data: " + merchant.getEmail());

		// logger.info("/merchantDetailsTC: "+mid1);
		PageBean pageBean = new PageBean("Merchant", "merchant/uploadTC/uploadTCDetails", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());

		List<Merchant> merchant1 = merchantService.loadMerchant();
		Set<String> merchantNameList = new HashSet<String>();
		for (Merchant t : merchant1) {
			String businessName = t.getBusinessName();

			try {
				if (t.getMid().getMid() != null) {
					// logger.info("mid: "+t.getMid().getMid().toString());
					merchantNameList.add(businessName.toString() + "~" + t.getMid().getMid());
				}
				/*
				 * else { merchantNameList.add(businessName.toString()+"~"+"null mid"); }
				 */
			} catch (NullPointerException e) {
				// logger.info(e);
			}

		}
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant1);
		/* model.addAttribute("merchantuser", regMobileUser); */
		model.addAttribute("merchant", merchant);
		// model.addAttribute("merchantNameList", merchantNameList);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/up1/uploadmerchantTC" }, method = RequestMethod.POST)
	public String uploadMerchantPdfTC(/* @ModelAttribute("merchantuser") final MerchtCustMail merchtCustMail2, */
			@RequestParam("mailFile") MultipartFile mailFile, @RequestParam("merchantID") Long id,
			@RequestParam("selectedMid") String mid1,

			final HttpServletRequest request, final Model model

	) {

		// MerchantDetails merchtpdfUpload=new MerchantDetails();
		logger.info("mailFile" + mailFile);
		MerchtCustMail merchtCustMail = new MerchtCustMail();
		merchtCustMail.setMailFile(mailFile);
		logger.info("merchantID: " + id);
		logger.info("mid1111111: " + mid1);

		logger.info("/uploadmerchantTC " + merchtCustMail.getMid());
		String fileContentType = merchtCustMail.getMailFile().getContentType();
		logger.info("filetype: " + fileContentType);
		String contentTypes = "application/pdf";
		// private static final List<String> contentTypes = Arrays.asList("image/png",
		// "image/jpeg", "image/gif");
		// if(contentTypes.contains(fileContentType)) {

		Merchant currentMerchant = merchantService.loadMerchantbyid(id);
		// Merchant currentMerchant =
		// merchantService.loadMerchantbymid(merchtCustMail.getMid());

		MerchantDetails merchantDetails = merchantService.loadMerchantDetails(currentMerchant);
		// logger.info("merchant id: "+merchantDetails.getMid());

		PageBean pageBean = new PageBean("Merchant", "merchant/uploadTC/uploadTCSuccess", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		// start upload File
		MerchtCustMail merchtCustMail1 = new MerchtCustMail();
		String midType = null;
		String mid = null;
		if (merchantDetails != null) {
			if (currentMerchant.getMid().getMid() != null) {
				// if(currentMerchant.getMid().getMid().equals(merchantDetails.getMid())) {
				if (currentMerchant.getMid().getMid().equals(mid1)) {
					midType = "EZYWIRE";
					mid = mid1;
				}
			} else if (currentMerchant.getMid().getMotoMid() != null) {
				if (currentMerchant.getMid().getMotoMid().equals(mid1)) {
					midType = "EZYMOTO";
					mid = mid1;
				}
			} else if (currentMerchant.getMid().getEzypassMid() != null) {
				if (currentMerchant.getMid().getEzypassMid().equals(mid1)) {
					midType = "EZYPASS";
					mid = mid1;
				}
			} else if (currentMerchant.getMid().getEzyrecMid() != null) {
				if (currentMerchant.getMid().getEzyrecMid().equals(mid1)) {
					midType = "EZYREC";
					mid = mid1;
				}
			} else if (currentMerchant.getMid().getEzywayMid() != null) {
				if (currentMerchant.getMid().getEzywayMid().equals(mid1)) {
					midType = "EZYWAY";
					mid = mid1;
				}
			} else if (currentMerchant.getMid().getUmMid() != null) {
				if (currentMerchant.getMid().getUmMid().equals(mid1)) {
					midType = "UMOBILE EZYWIRE";
					mid = mid1;
				}
			} else if (currentMerchant.getMid().getUmMotoMid() != null) {
				if (currentMerchant.getMid().getUmMotoMid().equals(mid1)) {
					midType = "UMOBILE EZYMOTO";
					mid = mid1;
				}
			} else if (currentMerchant.getMid().getUmEzypassMid() != null) {
				if (currentMerchant.getMid().getUmEzypassMid().equals(mid1)) {
					midType = "UMOBILE EZYPASS";
					mid = mid1;
				}
			} else if (currentMerchant.getMid().getUmEzyrecMid() != null) {
				if (currentMerchant.getMid().getUmEzyrecMid().equals(mid1)) {
					midType = "UMOBILE EZYREC";
					mid = mid1;
				}
			} else if (currentMerchant.getMid().getUmEzywayMid() != null) {
				if (currentMerchant.getMid().getUmEzywayMid().equals(mid1)) {
					midType = "UMOBILE EZYWAY";
					mid = mid1;
				}
			}
		}
		logger.info("mid---: " + mid + " " + midType);

		String fileName = merchtCustMail.getMailFile().getOriginalFilename();

		logger.info("fileName" + fileName);

		if (merchantDetails != null) {
			// merchtCustMail1.setMid(merchantDetails.getMid());

			merchtCustMail1.setMid(mid1);
			if (contentTypes.equals(fileContentType)) {
				if (!merchtCustMail.getMailFile().isEmpty()) {
					BufferedOutputStream stream = null;
					try {
						byte[] bytes = merchtCustMail.getMailFile().getBytes();
						Date fileid = new Date();
						String fileId = new SimpleDateFormat("yyyyMMddhhmmss").format(fileid);

						Date dt = new Date();
						String date = new SimpleDateFormat("yyyyMMdd").format(dt);
						String rootPath = PropertyLoader.getFile().getProperty("TCUPLOAD");
						File dir = new File(
								rootPath + File.separator + merchantDetails.getMid() + File.separator + date);

						if (!dir.exists())
							dir.mkdirs();
						File serverFile = new File(dir.getAbsolutePath() + File.separator
								+ merchtCustMail.getMailFile().getOriginalFilename());
						// BufferedOutputStream stream = new
						// BufferedOutputStream(new
						// FileOutputStream(serverFile));
						stream = new BufferedOutputStream(new FileOutputStream(serverFile));
						stream.write(bytes);
						stream.close();

						String text = new String(bytes);
						StringBuffer sf = new StringBuffer();

						try (BufferedReader br = new BufferedReader(new FileReader(dir.getAbsolutePath()
								+ File.separator + merchtCustMail.getMailFile().getOriginalFilename()))) {

							String sCurrentLine;

							while ((sCurrentLine = br.readLine()) != null) {
								// logger.info(sCurrentLine);
								sf.append(sCurrentLine);
							}

							// e.printStackTrace();
						}
						// logger.info("mail list file:" + sf.toString());
						// merchtCustMail1.setCustMailList(sf.toString());
						merchtCustMail1.setmFilepath(dir.getAbsolutePath() + File.separator
								+ merchtCustMail.getMailFile().getOriginalFilename());
						// logger.info("mail list pdf"+merchtCustMail1.getCustMailList());
						logger.info("mail list filepath" + merchtCustMail1.getmFilepath());
					} catch (Exception e) {
					} finally {
						if (stream != null) {
							try {
								stream.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}

					logger.info("mail list filepath final" + merchtCustMail1.getmFilepath());
					logger.info("mid  final" + merchtCustMail1.getMid());

					merchantDetails = merchantService.uploadMerchantPdfTC(merchtCustMail1, currentMerchant);
					if (merchantDetails != null) {
						request.setAttribute("responseData", "Terms and Conditions File Uploaded Successfully..!!");
						request.setAttribute("mid", mid);
						request.setAttribute("midType", midType);
						model.addAttribute("pageBean", pageBean);
					} else {
						request.setAttribute("responseData", "Invalid Merchant User and  Mid ");
						model.addAttribute("pageBean", pageBean);
					}
				}

				else {
					PageBean pageBean1 = new PageBean("Merchant", "merchant/uploadTC/uploadTCDetails", Module.MERCHANT,
							"merchant/sideMenuMerchant");
					request.setAttribute("responseData", "Please select Pdf file");
					model.addAttribute("pageBean", pageBean1);
				}
			}

			else {
				PageBean pageBean1 = new PageBean("Merchant", "merchant/uploadTC/uploadTCDetails", Module.MERCHANT,
						"merchant/sideMenuMerchant");
				request.setAttribute("responseData", "Please select a Valid Pdf file");
				model.addAttribute("pageBean", pageBean1);

			}

		} else {
			request.setAttribute("responseData", "Unable to Upload the TC File for this User in MerchantDetails");
			model.addAttribute("pageBean", pageBean);
		}
		// logger.info("type of mid: "+midType+" "+ merchantDetails.getMid());

		request.setAttribute("merchantName", merchtCustMail.getBusinessName());
		request.setAttribute("mid", mid);
		request.setAttribute("midType", midType);
		model.addAttribute("merchant", currentMerchant);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/merchantList" }, method = RequestMethod.GET)
	public String MerchantList(final Model model,
			/* @ModelAttribute("merchantuser") final MerchtCustMail regMobileUser, */
			final java.security.Principal principal) {
		logger.info("about to list all merchants");
		PageBean pageBean = new PageBean("Merchant", "merchant/MerchantList/viewMerchantProfile", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());

		List<Merchant> merchant = merchantService.loadMerchant();

		for (Merchant m : merchant) {
			if (m != null) {

				if (m.getBusinessName() != null && !m.getBusinessName().isEmpty()) {
					m.setBusinessName(m.getBusinessName().toUpperCase());

				} else {
					m.setBusinessName("NIL");

				}

			}
		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchant1", merchant); // RegMobileUser
		// model.addAttribute("merchantNameList", merchantNameList);
		// model.addAttribute("merchantList", merchantList);

		// model.addAttribute("refNoList", listRefNo);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/merchantDetails" }, method = RequestMethod.GET)
	public String displayMerchantDetails(final Model model, @RequestParam("id") Long id, /*
																							 * @ModelAttribute(
																							 * "merchantuser") final
																							 * MerchtCustMail
																							 * regMobileUser,
																							 */
			final java.security.Principal principal, HttpServletRequest request) {
		logger.info("/merchantDetails: " + id);

		/*
		 * String[] array1= mid.split("~",2); mid=array1[1];
		 * System.out.println(array1[1]);
		 */
		/*
		 * for (String temp: array1){ System.out.println(temp); }
		 */
		
		Merchant merchant = merchantService.loadMerchantbyid(id);
		MobileUser mUser = merchantService.loadBankByMerchantFk(merchant);
		HostBankDetails hDetails = merchantService.loadBankBySellerId(mUser);

		/*
		 * logger.info("data: "+merchant.getMid().getMid());
		 * logger.info("data: "+merchant.getBusinessAddress1());
		 * logger.info("data: "+merchant.getBusinessName());
		 * logger.info("data: "+merchant.getBusinessContactNumber());
		 * logger.info("data: "+merchant.getEmail());
		 */

		// logger.info("/merchantDetailsTC: "+mid1);
		PageBean pageBean = new PageBean("Merchant", "merchant/MerchantList/viewMerchantProfile", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		logger.info("admin login person:" + principal.getName());

		List<Merchant> merchant1 = merchantService.loadMerchant();

		Set<String> merchantNameList = new HashSet<String>();

		model.addAttribute("pageBean", pageBean);
		/* model.addAttribute("merchantuser", regMobileUser); */
		model.addAttribute("merchant1", merchant1);
		// request.setAttribute("merchantName", merchant.getUsername());
		model.addAttribute("merchantName", merchant.getUsername());
		model.addAttribute("merchant", merchant);
		model.addAttribute("merchantNameList", merchantNameList);
		model.addAttribute("hDetails",hDetails);
		
		return TEMPLATE_DEFAULT;
	}
	/*
	 * @RequestMapping(value = { "/checkMailContent" }, method = RequestMethod.POST)
	 * public String viewMerchantPdfTC(
	 * 
	 * final HttpServletRequest request,final HttpServletResponse response,final
	 * Model model
	 * 
	 * ) { String status=null; CheckMailContent c=new CheckMailContent();
	 * c.sendEmailTest(); return status;
	 * 
	 * }
	 */

}
