package com.mobiversa.payment.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantDetails;
import com.mobiversa.common.bo.MobileUser;
//import com.mobiversa.common.bo.MerchantCustMail;
import com.mobiversa.common.bo.Promotion;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.AddMerchantPromo;
import com.mobiversa.payment.service.AdminService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.PromotionService;
import com.mobiversa.payment.util.PropertyLoader;
import com.postmark.java.Attachment;
import com.postmark.java.NameValuePair;
import com.postmark.java.PostmarkClient;
import com.postmark.java.PostmarkException;
import com.postmark.java.PostmarkMessage;
import com.postmark.java.PromoEmailTemplet1;
import com.postmark.java.PromoEmailTemplet2;
import com.postmark.java.TempletFields;
//import com.mobiversa.common.bo.MerchantCustMail;

@Controller
@RequestMapping(value = PromotionAdminController.URL_BASE)

public class PromotionAdminController extends BaseController {

	public static final String URL_BASE = "/promotionAdmin";

	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MobileUserService mobileUserService;

	@Autowired
	private PromotionService promotionService;

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {

		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String displayMerchantList(final Model model,
			final Promotion merchantPromo, @PathVariable final int currPage) {
		logger.info("about to list all merchants promotion");
		PageBean pageBean = new PageBean("Merchant",
				"adminpromo/promotionAdminList", Module.PROMOTION,
				"merchant/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);
		PaginationBean<Promotion> paginationBean = new PaginationBean<Promotion>();

		paginationBean.setCurrPage(currPage);
		promotionService.listMerchantPromo(paginationBean);
		for (Promotion mp : paginationBean.getItemList()) {

		}
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;
	}

	// search condition for promotion summary 11072017

	@RequestMapping(value = { "/search" }, method = RequestMethod.GET)
	public String displaySearchMerchant(final Model model,

	@RequestParam(required = false, defaultValue = "1") final int currPage,
			@RequestParam final String date, @RequestParam final String date1) {
		logger.info("about to search Merchant based on search String:: " + date);

		logger.info("about to search Merchant based on search String:: "
				+ date1);

		PageBean pageBean = new PageBean("Search Merchant",
				"adminpromo/promotionAdminList", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		PaginationBean<Promotion> paginationBean = new PaginationBean<Promotion>();

		paginationBean.setCurrPage(currPage);

		promotionService.listPromotionSearch(paginationBean,date,date1);

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("paginationBean", paginationBean);

		return TEMPLATE_DEFAULT;
	}
	@RequestMapping(value = { "/detail/{id}" }, method = RequestMethod.GET)
	public String listMerchant(final Model model, @PathVariable final long id,
			final AddMerchantPromo addMerchantPromo) throws ParseException {
		logger.info("Request to display merchant based on ID: " + id);
		PageBean pageBean = new PageBean("Merchant Detail",
				"adminpromo/merchantPromoEditDetails", Module.PROMOTION,
				"merchant/sideMenuMerchant");
		Promotion promotion = promotionService.loadMerchantByPk(id);

		logger.info("merchant id: " + promotion.getMid());

		

		Merchant currentMerchant = merchantService.loadMidtoUpdateAudit(promotion
				.getMid());
		MerchantDetails merchantDetails = promotionService
				.loadMerchantPointsallmid(currentMerchant);
		
		logger.info("mechantdetails: "+merchantDetails.getPoints());
		addMerchantPromo.setMerchantId(currentMerchant.getId().toString());

		List<MobileUser> mobileuser = promotionService
				.loadMobileUserDetails(currentMerchant.getId());

		Set<String> mobUsername = new HashSet<String>();
		for (MobileUser t : mobileuser) {
			String username = t.getUsername();
			logger.info("mobile user name: " + username);
			addMerchantPromo.setUsername(username);
			mobUsername.add(username);
		}

		StringBuffer sf = new StringBuffer();
		FileInputStream fs = null;
		FileInputStream fs1 = null;
		try {

			/* logger.info("disply promo iamge file path:" +
			 promotion.getImgPath1());*/
			File img = new File(promotion.getImgPath1());
			//FileInputStream fs = new FileInputStream(img);
			fs = new FileInputStream(img);
			byte imagedata[] = new byte[(int) img.length()];
			fs.read(imagedata);
			String imgRead = Base64.encode(imagedata);

			if (promotion.getImgPath2() != null) {

				File img1 = new File(promotion.getImgPath2());
				//FileInputStream fs1 = new FileInputStream(img1);
				byte imagedata1[] = new byte[(int) img1.length()];
				fs1 = new FileInputStream(img1);
				
				fs1.read(imagedata1);

				String imgRead1 = Base64.encode(imagedata1);
				sf.append(imgRead1);
				fs1.close();
				addMerchantPromo.setPromoLogo2(imgRead1);
			}

			sf.append(imgRead);

			fs.close();

			addMerchantPromo.setPromoImage1(imgRead.getBytes());
			addMerchantPromo.setPromoLogo1(imgRead);

		} catch (IOException e) {
			if(fs != null){
				try {
					fs.close();
				} catch (IOException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}
			}
			if(fs1 != null){
				try {
					fs1.close();
				} catch (IOException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			if(fs != null){
				try {
					fs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fs1 != null){
				try {
					fs1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		addMerchantPromo.setMerchantId(promotion.getMerchantId());
		addMerchantPromo.setMerchantName(promotion.getMerchantName());

		addMerchantPromo.setmFilepath(promotion.getImgPath1());
		addMerchantPromo.setMid(promotion.getMid());
		addMerchantPromo.setPromoDesc(promotion.getPromoDesc());
		addMerchantPromo.setpCode(promotion.getPromoCode());
		addMerchantPromo.setPromoName(promotion.getPromoName());
		addMerchantPromo.setStatus(promotion.getStatus().toString());
		// addMerchantPromo.setValidityDate(promotion.getValidityDate());

		

		String rdFrom = new SimpleDateFormat("yyyy-MM-dd")
				.format(new SimpleDateFormat("dd/MM/yyyy").parse(promotion
						.getValidFrom()));

		String rdTo = new SimpleDateFormat("yyyy-MM-dd")
				.format(new SimpleDateFormat("dd/MM/yyyy").parse(promotion
						.getValidTo()));

		addMerchantPromo.setValidFrom(rdFrom);
		

		addMerchantPromo.setValidTo(rdTo);
		// addMerchantPromo.setValidityDate(rd);
		// new changes for promotion points 11072017 start
		addMerchantPromo.setPoints(promotion.getMsgCount());
		logger.info("check message count:" + addMerchantPromo.getPoints());
//end
		TerminalDetails td = promotionService.loadTid(promotion.getTid());

		if (td != null) {
			addMerchantPromo.setDeviceId(td.getDeviceId());

		}

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobiPromo", addMerchantPromo);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public String displayEditMerchant(final Model model,
			@PathVariable final Long id, final HttpServletRequest request,
			final AddMerchantPromo addMerchantPromo) throws ParseException {

		logger.info("edit promotion for admin login details checked:");

		PageBean pageBean = new PageBean("Merchant Detail",
				"adminpromo/editpromo/promotionEditDetails1", Module.PROMOTION,
				"merchant/sideMenuMerchant");

		Promotion promotion = promotionService.loadMerchantByPk(id);

		
		Merchant currentMerchant = merchantService.loadMidtoUpdateAudit(promotion
				.getMid());
		MerchantDetails merchantDetails = promotionService
				.loadMerchantPointsallmid(currentMerchant);
		/*MerchantDetails merchantDetails = promotionService
				.loadMerchantPoints(promotion.getMid());*/

		AddMerchantPromo amp = new AddMerchantPromo();

		/*Merchant currentMerchant = merchantService.loadMerchantbymid(promotion
				.getMid());*/

		List<MobileUser> mobileuser = promotionService
				.loadMobileUserDetails(currentMerchant.getId());

		Set<String> mobUsername = new HashSet<String>();
		for (MobileUser t : mobileuser) {
			String username = t.getUsername();
			logger.info("mobile user name: " + username);
			amp.setUsername(username);
			mobUsername.add(username);
		}

		StringBuffer sf = new StringBuffer();
		FileInputStream fs = null;
		try {

			File img = new File(promotion.getImgPath1());

			//FileInputStream fs = new FileInputStream(img);
			fs = new FileInputStream(img);
			byte imagedata[] = new byte[(int) img.length()];
			fs.read(imagedata);
			// String imgread = Base64.encode(fs.t)
			String imgRead = Base64.encode(imagedata);

			sf.append(imgRead);
			fs.close();
			 logger.info("display promo:" + imgRead);
			amp.setPromoLogo1(imgRead);
			amp.setPromoImgFilePath1(promotion.getImgPath1());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(fs !=null){
				try {
					fs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (promotion.getImgPath2() != null) {
			//FileInputStream fs = null;
			try {

				/* logger.info("disply promo iamge file path2:" +
				 promotion.getImgPath2());*/
				File img = new File(promotion.getImgPath2());

				//FileInputStream fs = new FileInputStream(img);
				fs = new FileInputStream(img);
				byte imagedata[] = new byte[(int) img.length()];
				fs.read(imagedata);
				// String imgread = Base64.encode(fs.t)
				String imgRead1 = Base64.encode(imagedata);

				sf.append(imgRead1);
				fs.close();
				 logger.info("display promo:" + imgRead1);
				amp.setPromoLogo2(imgRead1);
				amp.setPromoImgFilePath2(promotion.getImgPath2());

			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(fs !=null){
					try {
						fs.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		//logger.info("promotion id :" + amp.getId());
		amp.setId(promotion.getId().toString());
		amp.setMerchantId(promotion.getMerchantId());
		amp.setMerchantName(promotion.getMerchantName());
		amp.setMid(promotion.getMid());

		amp.setPromoDesc(promotion.getPromoDesc());
		amp.setPromoName(promotion.getPromoName());
		amp.setpCode(promotion.getPromoCode());
		// msg count & email preview added for admin page 11072017 start
		amp.setPoints(promotion.getMsgCount());
		// amp.setValidityDate(promotion.getValidityDate());

		String sd = promotion.getValidFrom();
		String rd = new SimpleDateFormat("dd/MM/yyyy")
				.format(new SimpleDateFormat("dd/MM/yyyy").parse(sd));
		logger.info("check validity date:" + rd);
		amp.setValidFrom(rd);

		String sd1 = promotion.getValidTo();
		String rd1 = new SimpleDateFormat("dd/MM/yyyy")
				.format(new SimpleDateFormat("dd/MM/yyyy").parse(sd1));
		logger.info("check validity date:" + rd1);
		amp.setValidTo(rd);

		logger.info("check email preview points:2323:" + amp.getPoints());
		amp.setEmailPreview(addMerchantPromo.getEmailPreview());
		logger.info("check email preview4444:" + amp.getEmailPreview());
		
//end
		TerminalDetails td = promotionService.loadTid(promotion.getTid());
logger.info("promotion edit tid details:" + promotion.getTid());
		if (td != null) {
			amp.setDeviceId(td.getDeviceId());

			// logger.info("display deviceid:"+ addMerchantPromo.getDeviceId());

		}
		// amp.setPromoLogo(new String (promotion.getPromoImg1()));
		// amp.setEmailList(mobiPromo.getCustMailList());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("mobiPromo", amp);

		amp.setStatus(promotion.getStatus().toString());
		//PCI
		request.getSession(true).setAttribute("editMerchantPromoSession", amp);

		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/editMerchantPromotion" }, method = RequestMethod.POST)
	public String processEditUserForm(
			@ModelAttribute("addMerchantPromo") final AddMerchantPromo addMerchantPromo,
			final java.security.Principal principal,

			final HttpServletRequest request,
			final ModelMap model,
			@RequestParam("id") final Long id,

			@RequestParam("merchantName") final String merchantName,
			@RequestParam("mid") final String mid,
			@RequestParam("promoName") final String promoName,
			@RequestParam("validFrom") final String validFrom,
			// @RequestParam("emailList") final String emailList,
			@RequestParam("promoDesc") final String promoDesc,
			// @RequestParam("promoLogo")final String promoLogo,
			// @RequestParam("mLogo")final String mLogo,
			@RequestParam("pCode") final String pCode,
			@RequestParam("promoLogo1") final String promoLogo1,
			@RequestParam(required = false) final String promoLogo2,
			@RequestParam("status") final String status,
			@RequestParam("username") final String username,
			@RequestParam("emailPreview") final String emailPreview,
			@RequestParam("points") final String points,
			@RequestParam("validTo") final String validTo)

	{

		{
			logger.info("edit merhcant promotion details to change status:");

			PageBean pageBean = new PageBean("Merchant promotion Edit Details",
					"adminpromo/editpromo/promotionEditDetails1",
					Module.PROMOTION, "merchant/sideMenuMerchant");

			Promotion mobiPromo = promotionService.loadMerchantByPk(id);
			
			Merchant currentMerchant = merchantService.loadMidtoUpdateAudit(mobiPromo
					.getMid());
			MerchantDetails merchtMail = promotionService
					.loadMerchantPointsallmid(currentMerchant);
			logger.info("promotion mid to be display:" + mobiPromo.getMid());

			AddMerchantPromo amp = new AddMerchantPromo();

			amp.setMerchantId(id.toString());

			amp.setPromoDesc(promoDesc);
			amp.setPromoName(promoName);
			amp.setMid(mid);
			amp.setMerchantName(merchantName);
			amp.setpCode(pCode);
			amp.setPromoLogo1(promoLogo1);
			amp.setPromoLogo2(promoLogo2);
			amp.setUsername(username);
			amp.setStatus(status);
			amp.setPoints(points);
			amp.setValidFrom(validFrom);
			amp.setValidTo(validTo);
			logger.info("display msg count:" + amp.getPoints() + "" + points);

			logger.info("check status:" + amp.getStatus() + "  " + status);

			// new changes for test email for approved status 06072017

			logger.info("test email112333:" + emailPreview);
			
			amp.setEmailPreview(emailPreview);
			logger.info("test email:" + amp.getEmailPreview());
			
			
			/*if (amp.getStatus().equalsIgnoreCase("APPROVED")) {*/
			if (amp.getStatus().equals("APPROVED")) {

				if (amp.getEmailPreview() != null) {
					// MID mid = merchantService.loadMid(mobiPromo.getMid());
					// Merchant merchant = merchantService.loadMerchant(mid);
					List<NameValuePair> headers = new ArrayList<NameValuePair>();
					headers.add(new NameValuePair("HEADER", "test"));
					// EZYWIRE AS USERNAME & password mobiversa
					String fromAddress = "info@gomobi.io";
					// String apiKey = "c652b570-9500-4534-8eb6-96a78c10c8b8";
					String apiKey = PropertyLoader.getFile().getProperty(
							"APIKEY");
					String toAddress = amp.getEmailPreview();
					String ccMail = null;

					// String ccMail = "premkumar@mobiversa.com";
					// String bccMail = "premkumar@mobiversa.com";
					// String subject = "Account Creation Mail";// set
					// String subject =
					// PropertyLoader.getFile().getProperty("PROMOMAIL_SUBJECT");
					String subject = mobiPromo.getPromoName();

				//	Merchant merchant = merchantService.getMerchantByMid(mid);

					TempletFields tempField = new TempletFields();

					tempField.setPromoName(mobiPromo.getPromoName());
					tempField.setPromoCode(mobiPromo.getPromoCode());
					tempField.setPromoDesc(mobiPromo.getPromoDesc());
					tempField.setDate(new SimpleDateFormat("dd-MMM-yyyy")
							.format(new java.util.Date()));
					tempField
							.setMerchantAddress(currentMerchant.getBusinessAddress1());
					tempField.setMerchantPhoneno(currentMerchant
							.getBusinessContactNumber());
					tempField.setMerchantName(currentMerchant.getBusinessName());

				StringBuffer sf = new StringBuffer();
				String img1 = null;
				String img2 = null;
				if (mobiPromo.getImgPath1() != null) {
					FileInputStream fs = null;
					try {

						/* logger.info("disply promo image file path1:" +
								 mobiPromo.getImgPath1());*/
						File img = new File(mobiPromo.getImgPath1());
						tempField.setPromoImg1(img.getName());
						fs = new FileInputStream(img);
						byte imagedata[] = new byte[(int) img.length()];
						fs.read(imagedata);
						// String imgread = Base64.encode(fs.t)
						String imgRead = Base64.encode(imagedata);

						//System.out.println("Data");
						sf.append(imgRead);
						fs.close();
						img1 = imgRead.toString();
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						if(fs !=null){
							try {
								fs.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}

				if (mobiPromo.getImgPath2() != null) {
					FileInputStream fs = null;
					try {

						/*logger.info("disply promo iamge file path2:" +
						 mobiPromo.getImgPath2());*/
						File img = new File(mobiPromo.getImgPath2());
						tempField.setPromoImg2(img.getName());
						fs = new FileInputStream(img);
						byte imagedata[] = new byte[(int) img.length()];
						fs.read(imagedata);
						String imgRead1 = Base64.encode(imagedata);

						sf.append(imgRead1);
						fs.close();
						img2 = imgRead1.toString();

					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						if(fs !=null){
							try {
								fs.close();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}

				String emailBody = null;

					Attachment faceBook = new Attachment("mobi_facebook.jpg",
							"image/jpg", PropertyLoader.getFile().getProperty(
									"PROMO_FACEBOOK"), "cid:mobi_facebook");

					Attachment twitter = new Attachment("mobi_twitter.jpg",
							"image/jpg", PropertyLoader.getFile().getProperty(
									"PROMO_TWITTER"), "cid:mobi_twitter");

					Attachment link = new Attachment("mobiversa_link.jpg",
							"image/jpg", PropertyLoader.getFile().getProperty(
									"PROMO_LINK"), "cid:mobiversa_link");

					Attachment insta = new Attachment("mobi_linkedin.jpg",
							"image/jpg", PropertyLoader.getFile().getProperty(
									"PROMO_INSTAGRAM"), "cid:mobi_instagram");

					Attachment logo = new Attachment("mobiversa_logo1.jpg",
							"image/jpg", PropertyLoader.getFile().getProperty(
									"PROMO_MOBI_LOGO"), "cid:mobiversa_logo1");

					Attachment Ezywire = new Attachment("Ezywire_Logo.png",
							"image/jpg", PropertyLoader.getFile().getProperty(
									"EZYWIRE"), "cid:Ezywire_Logo");

					List<Attachment> attachments = new ArrayList<Attachment>();
					attachments.add(logo);
					attachments.add(faceBook);
					attachments.add(twitter);
					attachments.add(link);
					attachments.add(insta);
					// attachments.add(Ezywire);

					if (mobiPromo.getImgPath2() != null) {
						// Attachment promoImage1 = new
						// Attachment("promo_image1", "image/jpg", img1,
						// "cid:promo_image1");
						// Attachment promoImage2 = new
						// Attachment("promo_image2", "image/jpg", img2,
						// "cid:promo_image2");
						Attachment promoimg1 = new Attachment(
								tempField.getPromoImg1(), "image/jpg", img1,
								"cid:" + tempField.getPromoImg1());
						Attachment promoimg2 = new Attachment(
								tempField.getPromoImg2(), "image/jpg", img2,
								"cid:" + tempField.getPromoImg2());

					// logger.info("attachement image1:" +
					//attachments.add(promoImage1));
					 //logger.info("attachement image1:" +
					// attachments.add(promoImage1));

						attachments.add(promoimg1);
						attachments.add(promoimg2);
						emailBody = PromoEmailTemplet2
								.sentTempletContent(tempField);
					} else {
						// Attachment promoImage1 = new
						// Attachment("promo_image1", "image/jpg", img1,
						// "cid:promo_image1");
						Attachment promoimg1 = new Attachment(
								tempField.getPromoImg1(), "image/jpg", img1,
								"cid:" + tempField.getPromoImg1());
						attachments.add(promoimg1);
						emailBody = PromoEmailTemplet1
								.sentTempletContent(tempField);
					}
					/* bccMail, */
					PostmarkMessage message = new PostmarkMessage(fromAddress,
							toAddress, fromAddress, ccMail, subject, emailBody,
							true, "test-email", null, attachments);
					PostmarkClient client = new PostmarkClient(apiKey);

				try {
					client.sendMessage(message);
					logger.info("Email Sent Successfully to" + emailPreview);
				} catch (PostmarkException pe) {
					logger.info("Invalid Signature Base64 String");

				}

			}
			StringBuffer sf = new StringBuffer();
			FileInputStream fs = null;
			try {

				/* logger.info("disply promo iamge file path:" +
				 mobiPromo.getImgPath1());*/
				File img = new File(mobiPromo.getImgPath1());

				//FileInputStream fs = new FileInputStream(img);
				fs = new FileInputStream(img);
				byte imagedata[] = new byte[(int) img.length()];
				fs.read(imagedata);
				// String imgread = Base64.encode(fs.t)
				String imgRead = Base64.encode(imagedata);

				// System.out.println("Data");
				sf.append(imgRead);
				fs.close();
				 //logger.info("display promo:" + imgRead);
				addMerchantPromo.setPromoLogo1(imgRead);
			} catch (IOException e) {
				e.printStackTrace();
			}finally{
				if(fs !=null){
					try {
						fs.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			}

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("mobiPromo", addMerchantPromo);
			// PCI
			request.getSession(true).setAttribute("editMerchantPromoSession",
					addMerchantPromo);
		}
		return "redirect:/promotionAdmin/editReviewandConfirm";
	}

	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditMerchantPromotionReview(final Model model,
			final HttpServletRequest request) {
		logger.info("about to edit Merchant promotion Details ReviewAndConfirm");

		AddMerchantPromo promo = (AddMerchantPromo) request.getSession(true)
				.getAttribute("editMerchantPromoSession");

		if (promo == null) {

		
			return "redirect:" + URL_BASE + "/editMerchantPromotion/1";
		}

		logger.info("about to edit Merchant promotion Details status:"
				+ promo.getStatus());
		logger.info("check merchant mail confirm page:" + promo.getDeviceId());

		PageBean pageBean = new PageBean("Merchant Edit Review Detail",
				"adminpromo/editpromo/merchantPromoReviewAndConfirm",
				Module.PROMOTION, "merchant/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);
		
		model.addAttribute("merchantPromo", promo);
		return TEMPLATE_DEFAULT;
	}

	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.POST)
	public String confirmEditMerchantPromotion(final Model model,
			final HttpServletRequest request, Principal principal) {
		logger.info("about to edit Merchant Details Confirms");
		PageBean pageBean = new PageBean("Successfully Merchant edited",
				"adminpromo/editpromo/merchantPromoAllDone", Module.PROMOTION,
				"merchant/edit/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);

		logger.info("about to edit Merchant promotion Details ReviewAndConfirm success");
		AddMerchantPromo merchantSavedInHttpSession = (AddMerchantPromo) request
				.getSession(true).getAttribute("editMerchantPromoSession");
		if (merchantSavedInHttpSession == null) {
			return "redirect:/promotionAdmin/detail";
		}
		logger.info("changed by admin usrname: " + principal.getName());
		logger.info("id " +merchantSavedInHttpSession.getId() );
		logger.info("merchant name " +merchantSavedInHttpSession.getMerchantName());
		logger.info("merchant id " +merchantSavedInHttpSession.getMerchantId());
		logger.info("mer mid " +merchantSavedInHttpSession.getMid());
		
		Merchant merchant=merchantService.loadmobileMerchant(merchantSavedInHttpSession.getMid());				
						
		model.addAttribute("mobiPromo", merchantSavedInHttpSession);
		Promotion mobiPromo = promotionService.loadMerchantByPk(Long
				.parseLong(merchantSavedInHttpSession.getId()));

		 logger.info("merchant username: "+merchant.getUsername());
		Promotion promoStatus = promotionService.updateMerchantPromotion(
				merchantSavedInHttpSession, mobiPromo);
		if (promoStatus != null) {
			AuditTrail auditTrail = adminService
					.updateAuditTrailByAdmin(
							merchant.getUsername(),principal.getName(), "EzyAdsStatusUpdate");
			if (auditTrail.getUsername() != null) {
				logger.info("EZYAds Status of Merchant: "
						+ merchant.getUsername()
						+ " updated by Admin "+principal.getName());
			}
		}

		// logger.info("about to edit Merchant promotion Details ReviewAndConfirm111");
		logger.info("merchant promotion edited succesfully:");
		request.getSession(true).removeAttribute("editMerchantPromoSession");

		return TEMPLATE_DEFAULT;
	}

}
