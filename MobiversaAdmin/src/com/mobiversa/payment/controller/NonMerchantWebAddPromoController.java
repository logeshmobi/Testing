package com.mobiversa.payment.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MerchantDetails;
import com.mobiversa.common.bo.MobileUser;
//import com.mobiversa.common.bo.MerchantCustMail;
import com.mobiversa.common.bo.Promotion;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.AddMerchantPromo;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.PromotionService;
import com.mobiversa.payment.util.PropertyLoader;

@SuppressWarnings("unused")
@Controller
@RequestMapping(value = NonMerchantWebAddPromoController.URL_BASE)
public class NonMerchantWebAddPromoController extends BaseController {

	public static final String URL_BASE = "/promotionwebNonmerchant1";

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private PromotionService promotionService;

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {

		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/list/{currPage}" }, method = RequestMethod.GET)
	public String listMerchantPromotions(final Model model,
			@PathVariable final int currPage,
			final java.security.Principal principal,
			final HttpServletRequest request) {
		logger.info("promotion summary List");
		PageBean pageBean = new PageBean("MerchantWeb Promotion",
				"nonmerchantweb/promotion/nonMerchantpromotionList", Module.PROMOTION,
				null);
		model.addAttribute("pageBean", pageBean);
		String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		PaginationBean<Promotion> paginationBean = new PaginationBean<Promotion>();
		paginationBean.setCurrPage(currPage);

		request.getSession(true).setAttribute("CURRENT_USER_MERCHANT",
				currentMerchant);
		promotionService.listPromotions(paginationBean, currentMerchant);

		for (Promotion promotion : paginationBean.getItemList()) {
		}
		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_NONMERCHANT;

	}

	// *** add merchant code started
	@RequestMapping(value = { "/addMerchantPromotion" }, method = RequestMethod.GET)
	public String displayAddMerchantPromo(
			@ModelAttribute("merchantPromo") final AddMerchantPromo addMerchantPromo,
			final HttpServletRequest request, Model model,
			java.security.Principal principal) {

		String myName = principal.getName();
		logger.info("check login person in promotion:" + myName);
		logger.info("addmerchant promtion for non merchant first (): " + myName);

		Merchant currentMerchant = merchantService.loadMerchant(myName);
		logger.info("non merchant id: " + currentMerchant.getId());
		MerchantDetails merchantDetails = promotionService
				.loadMerchantPoints(currentMerchant.getMid().getMid());

		/*
		 * if (merchantDetails.getPoints() != null) {
		 * logger.info("check merchant details points:" +
		 * merchantDetails.getPoints()); }
		 */

		logger.info("check merchant mid: " + currentMerchant.getMid().getMid());

		logger.info("check nonmerchant id: " + currentMerchant.getId());

		List<MobileUser> mobileuser = promotionService
				.loadMobileUserDetails(currentMerchant.getId());

		Set<String> mobUsername = new HashSet<String>();
		for (MobileUser t : mobileuser) {
			String username = t.getUsername();
			logger.info("mobile user name: " + username);
			addMerchantPromo.setUsername(username);
			mobUsername.add(username);
		}
		Set<String> tid = new HashSet<String>();
		for (MobileUser t : mobileuser)
		{
			if(t.getTid()!=null)
			{
			String tids = t.getTid();
			logger.info("mobileuser promotion: " + tids);
			addMerchantPromo.setTid(tids);
			tid.add(tids);
			}
		}
		
		if ((merchantDetails.getPoints()) != null) 
		{
			
			addMerchantPromo.setPoints(merchantDetails.getPoints());
			

			logger.info("check merchant points" + addMerchantPromo.getPoints());

		}

		addMerchantPromo.setMerchantId(currentMerchant.getId().toString());
		// addMerchantPromo.setMerchantLogo(new
		// String(merchantDetails.getMerchantLogo()));
		addMerchantPromo.setMid(currentMerchant.getMid().getMid());

		addMerchantPromo.setMerchantName(currentMerchant.getBusinessName());
		PageBean pageBean = new PageBean("Merchant Detail",
				"nonmerchantweb/promotion/addpromo/addPromononMerchant", Module.PROMOTION,
				null);

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("merchant", currentMerchant);

		// model.addAttribute("tidList", tidList);
		model.addAttribute("mobUsername", mobUsername);

		logger.info("-------------------add promotion next  page to be display:--------");
		return TEMPLATE_NONMERCHANT;
	}

	@RequestMapping(value = { "/addMerchantPromotion" }, method = RequestMethod.POST)
	public String processAddUserForm(
			@ModelAttribute("merchantPromo") final AddMerchantPromo addMerchantPromo,
			HttpServletRequest request, final ModelMap model,
			final BindingResult errors, final Principal principal,
			HttpServletRequest response,
			@RequestParam("points") final String points) {
		// logger.info("about to add Merchant SamePage addMerchantPromotion 2nd method");
		logger.info("addmerchant promtion for non merchant second (): ");

		String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		addMerchantPromo.setMerchantId(currentMerchant.getId().toString());
		logger.info("getid: "+currentMerchant.getId());
		addMerchantPromo.setMid(currentMerchant.getMid().getMid());
	
		logger.info("nonmerchnat mid: " + addMerchantPromo.getMid());
		logger.info("nonmerchnat id: " + addMerchantPromo.getMerchantId());

		
		List<MobileUser> mobileuser = promotionService
				.loadMobileUserDetails(currentMerchant.getId());

		Set<String> mobUsername = new HashSet<String>();
		for (MobileUser t : mobileuser) {
			String username = t.getUsername();
			logger.info("mobile user name: " + username);
			addMerchantPromo.setUsername(username);
			mobUsername.add(username);
		}
		Set<String> tid = new HashSet<String>();
		
		for (MobileUser t : mobileuser)
		{
			if(t.getTid()!=null)
			{
			String tids = t.getTid();
			logger.info("mobileuser promotion: " + tids);
			addMerchantPromo.setTid(tids);
			tid.add(tids);
			}
		}
		
		MerchantDetails merchantDetails = promotionService
				.loadMerchantPoints(currentMerchant.getMid().getMid());
		// logger.info("get points:" + merchantDetails.getPoints());
		addMerchantPromo.setPoints(merchantDetails.getPoints());

		// logger.info("get points:" + addMerchantPromo.getPoints());
		addMerchantPromo.setMerchantName(merchantDetails.getMerchantName());
		AddMerchantPromo addMerchantPromo1 = new AddMerchantPromo();

		String imgRead = null;
		String imgRead1 = null;
		String filePath = null;
		BufferedImage img1 = null;
		BufferedImage img2 = null;
		try {
			img1=ImageIO.read(addMerchantPromo.getImageFile().getInputStream());
			img2=ImageIO.read(addMerchantPromo.getImageFile1().getInputStream());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		PageBean pageBean = null;

		if((img1==null) && (img2==null) ){
			pageBean = new PageBean("Merchant Detail",
					"nonmerchantweb/promotion/addpromo/addPromononMerchant",
					Module.PROMOTION, null);

			logger.info("directory not exists...");
			model.addAttribute("responseData",
					"Image1 and Image2 is not an Image Format.."); // table
																	// response
			// logger.info("check image file folder:");
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("merchant", currentMerchant);
			model.addAttribute("mobUsername", mobUsername);

			return TEMPLATE_NONMERCHANT;
		}
		else
		{
		// promotion image file1
		if (!addMerchantPromo.getImageFile().isEmpty()) 
		{
			if(img1==null || img1.getWidth()==0)
			{
				pageBean = new PageBean("Merchant Detail",
						"nonmerchantweb/promotion/addpromo/addPromononMerchant",
						Module.PROMOTION, null);

				logger.info("directory not exists...");
				model.addAttribute("responseData",
						"Image1 is not an Image Format.."); // table
																		// response
				// logger.info("check image file folder:");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("merchant", currentMerchant);
				model.addAttribute("mobUsername", mobUsername);

				return TEMPLATE_NONMERCHANT;
			}
			else
			{
				try {
					byte[] bytes = addMerchantPromo.getImageFile().getBytes();
					Date fileid = new Date();
					String fileId = new SimpleDateFormat("yyyyMMddhhmmss")
							.format(fileid);
					Date dt = new Date();
					String date = new SimpleDateFormat("yyyyMMdd").format(dt);
					String rootPath = PropertyLoader.getFile().getProperty(
							"NONMERCHANTPROMO");
					// String rootPath =
					// PropertyLoader.getFile().getProperty("MERCHANTPROMO");
					/*
					 * if(rootPath.equals("z:\\promotions\\")) {
					 */
					/*
					 * File dir = new File( rootPath + File.separator +
					 * currentMerchant.getMid().getMid() + File.separator + date);
					 */

				File dir = new File(rootPath);

				// String rootPath= "z:/promotions";
				// logger.info("check file path:" + rootPath);
				// File dir = new File(rootPath);
				if (!dir.exists()) {
					pageBean = new PageBean("Merchant Detail",
							"nonmerchantweb/promotion/addpromo/addPromononMerchant",
							Module.PROMOTION, null);

					logger.info("directory not exists...");
					model.addAttribute("responseData",
							" Invalid directory,unable to save the image"); // table
																			// response
					// logger.info("check image file folder:");
					model.addAttribute("pageBean", pageBean);
					model.addAttribute("merchant", currentMerchant);
					model.addAttribute("mobUsername", mobUsername);

					return TEMPLATE_NONMERCHANT;
				} else {
					BufferedOutputStream stream = null;
					FileInputStream fs = null;
					try {
						dir = new File(rootPath + File.separator
								+ currentMerchant.getMid().getMid()
								+ File.separator + date);
						/* if (!dir.exists()) */
						dir.mkdirs();
						/*
						 * String fileData = dir.getAbsolutePath() +
						 * File.separator+
						 * addMerchantPromo.getImageFile().getOriginalFilename
						 * (); File serverFile = new File(fileData);
						 */
						File serverFile = new File(dir.getAbsolutePath()
								+ File.separator
								+ addMerchantPromo.getImageFile()
										.getOriginalFilename());
						// BufferedOutputStream stream = new
						// BufferedOutputStream(new
						// FileOutputStream(serverFile));
						stream = new BufferedOutputStream(new FileOutputStream(
								serverFile));
						stream.write(bytes);
						stream.close();
						img1 = ImageIO.read(new File(dir.getAbsolutePath()
								+ File.separator
								+ addMerchantPromo.getImageFile()
										.getOriginalFilename()));
						// try {
						File img = new File(dir.getAbsolutePath()
								+ File.separator
								+ addMerchantPromo.getImageFile()
										.getOriginalFilename());
						filePath = dir.getAbsolutePath()
								+ File.separator
								+ addMerchantPromo.getImageFile()
										.getOriginalFilename();
						// FileInputStream fs = new FileInputStream(img);
						fs = new FileInputStream(img);
						byte imagedata[] = new byte[(int) img.length()];
						fs.read(imagedata);
						imgRead = Base64.encode(imagedata);
						fs.close();
						addMerchantPromo1.setPromoImgFilePath1(dir
								.getAbsolutePath()
								+ File.separator
								+ addMerchantPromo.getImageFile()
										.getOriginalFilename());
						logger.info("nonmerchant promotion image filePath:"
								+ addMerchantPromo1.getPromoImgFilePath1());
					} catch (IOException e) {
						if (fs != null) {
							try {
								fs.close();
							} catch (IOException ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
						}
						if (stream != null) {
							try {
								stream.close();
							} catch (IOException ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
						}
						e.printStackTrace();
					} finally {
						if (fs != null) {
							try {
								fs.close();
							} catch (IOException ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
						}
						if (stream != null) {
							try {
								stream.close();
							} catch (IOException ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
						}
					}
					// }
				}
			} catch (Exception e) {
				}
			}
			
		}
		// promotion ImageFile2

		if (!addMerchantPromo.getImageFile1().isEmpty()) {
			if(img2==null || img2.getWidth()==0)
			{
				pageBean = new PageBean("Merchant Detail",
						"nonmerchantweb/promotion/addpromo/addPromononMerchant",
						Module.PROMOTION, null);

				logger.info("directory not exists...");
				model.addAttribute("responseData",
						"Image2 is not an Image Format.."); // table
																		// response
				// logger.info("check image file folder:");
				model.addAttribute("pageBean", pageBean);
				model.addAttribute("merchant", currentMerchant);
				model.addAttribute("mobUsername", mobUsername);

				return TEMPLATE_NONMERCHANT;
			}
			else
			{
				try {
					byte[] bytes = addMerchantPromo.getImageFile1().getBytes();
					Date fileid = new Date();
					String fileId = new SimpleDateFormat("yyyyMMddhhmmss")
							.format(fileid);
					Date dt = new Date();
					String date = new SimpleDateFormat("yyyyMMdd").format(dt);
					String rootPath = PropertyLoader.getFile().getProperty(
							"NONMERCHANTPROMO");

				/*
				 * File dir = new File( rootPath + File.separator +
				 * currentMerchant.getMid().getMid() + File.separator + date);
				 */
				File dir = new File(rootPath);
				/*
				 * String rootPath= "z:/promotions"; File dir = new
				 * File("z:/promotions");
				 */
				if (!dir.exists()) {
					pageBean = new PageBean("Merchant Detail",
							"nonmerchantweb/promotion/addpromo/addPromononMerchant",
							Module.PROMOTION, null);

					logger.info("directory not exists2....");
					model.addAttribute("responseData",
							" Invalid directry,unable to save the image"); // table
																			// response

					model.addAttribute("pageBean", pageBean);
					model.addAttribute("merchant", currentMerchant);
					model.addAttribute("mobUsername", mobUsername);

					return TEMPLATE_NONMERCHANT;
				} else {
					BufferedOutputStream stream = null;
					FileInputStream fs = null;
					try {
						dir = new File(rootPath + File.separator
								+ currentMerchant.getMid().getMid()
								+ File.separator + date);
						/* if (!dir.exists()) */
						dir.mkdirs();
						File serverFile = new File(dir.getAbsolutePath()
								+ File.separator
								+ addMerchantPromo.getImageFile1()
										.getOriginalFilename());
						// BufferedOutputStream stream = new
						// BufferedOutputStream(new
						// FileOutputStream(serverFile));
						stream = new BufferedOutputStream(new FileOutputStream(
								serverFile));
						stream.write(bytes);
						stream.close();

						img2 = ImageIO.read(new File(dir.getAbsolutePath()
								+ File.separator
								+ addMerchantPromo.getImageFile1()
										.getOriginalFilename()));
						// try {

						File img = new File(dir.getAbsolutePath()
								+ File.separator
								+ addMerchantPromo.getImageFile1()
										.getOriginalFilename());

						// FileInputStream fs = new FileInputStream(img);
						fs = new FileInputStream(img);
						byte imagedata[] = new byte[(int) img.length()];
						fs.read(imagedata);
						imgRead1 = Base64.encode(imagedata);
						fs.close();

						addMerchantPromo1.setPromoImgFilePath2(dir
								.getAbsolutePath()
								+ File.separator
								+ addMerchantPromo.getImageFile1()
										.getOriginalFilename());
						logger.info("nonmerchant promotion image filePath:"
								+ addMerchantPromo1.getPromoImgFilePath2());
					} catch (IOException e) {
						if (fs != null) {
							try {
								fs.close();
							} catch (IOException ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
						}
						if (stream != null) {
							try {
								stream.close();
							} catch (IOException ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
						}
						/*
						 * if(stream != null){ stream.close(); } if(fs != null){
						 * fs.close(); }
						 */
						e.printStackTrace();
					} finally {
						if (fs != null) {
							try {
								fs.close();
							} catch (IOException ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
						}
						if (stream != null) {
							try {
								stream.close();
							} catch (IOException ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
						}
					}

					}
				} catch (Exception e) {
				}
			}
		}

		}
		addMerchantPromo1.setPromoImage1(imgRead.getBytes());

		addMerchantPromo1.setPromoLogo1(imgRead);

		if (imgRead1 != null) {

			addMerchantPromo1.setPromoImage2(imgRead1.getBytes());
			addMerchantPromo1.setPromoLogo2(imgRead1);
		}

		addMerchantPromo1.setTid(addMerchantPromo.getTid());
		//addMerchantPromo1.setDeviceId(addMerchantPromo.getTid()());
		//addMerchantPromo1.setMerchantId(addMerchantPromo.getMerchantId());
		addMerchantPromo1.setMid(addMerchantPromo.getMid());
		addMerchantPromo1.setMerchantName(addMerchantPromo.getMerchantName());
		addMerchantPromo1.setMerchantId(addMerchantPromo.getMerchantId());
		addMerchantPromo1.setSendType(addMerchantPromo.getSendType());
		addMerchantPromo1.setPromoName(addMerchantPromo.getPromoName());
		addMerchantPromo1.setPromoDesc(addMerchantPromo.getPromoDesc());
		addMerchantPromo1.setUsername(addMerchantPromo.getUsername());
		// new changes for promotion validity date 11082017

		/*
		 * logger.info("validity date format in promo:" +
		 * addMerchantPromo.getValidityDate()); String dat1 =
		 * addMerchantPromo.getValidityDate();
		 * logger.info("validity date format in promo:" + dat1);
		 * SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		 * try { dat1 = dateFormat1.format(new
		 * SimpleDateFormat("dd/MM/yyyy").parse(dat1));
		 * logger.info("validity date format in promo:" + dat1); } catch
		 * (ParseException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } addMerchantPromo1.setValidityDate(dat1);
		 */

		// new change for promo valid date 18082017

		String dat1 = addMerchantPromo.getValidFrom();
		String dat2 = addMerchantPromo.getValidTo();
		logger.info("validity date format in promo:" + dat1);
		/*SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
					.parse(dat1));
			logger.info("validity date format in promo from date:" + dat1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		addMerchantPromo1.setValidFrom(dat1);

		/*SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			dat2 = dateFormat2.format(new SimpleDateFormat("dd/MM/yyyy")
					.parse(dat2));
			logger.info("validity date format in promo to date:" + dat2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		addMerchantPromo1.setValidTo(dat2);

		// addMerchantPromo1.setValidityDate(addMerchantPromo.getValidityDate());
		logger.info("check validity date:" + addMerchantPromo1.getValidFrom());

		logger.info("check validity date:" + addMerchantPromo1.getValidTo());
		// end

		if (addMerchantPromo.getMerchantName() != null) 
		{
			addMerchantPromo1.setpCode(addMerchantPromo.getMerchantName()
					.substring(0, 3).trim().toUpperCase()
					+ RandomStringUtils.randomNumeric(5));
		}
		addMerchantPromo1.setPromoDesc(addMerchantPromo.getPromoDesc());
		addMerchantPromo1.setPoints(points);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantPromo1", addMerchantPromo1);
		model.addAttribute("merchant", currentMerchant);
		/* model.addAttribute("mobUsername", mobUsername); */

		// PCI
		request.getSession(true).setAttribute("addMerchantPromotionSession",
				addMerchantPromo1);
		return "redirect:/promotionwebNonmerchant1/merchantPromoDetailsReviewAndConfirm";
	}

	@RequestMapping(value = { "/merchantPromoDetailsReviewAndConfirm" }, method = RequestMethod.GET)
	public String displayAddUserConfirmation(final ModelMap model,
			final HttpServletRequest request,
			final AddMerchantPromo merchantPromo1, final Principal principal) {
		logger.info("add NONmerchant confirm page:");

		AddMerchantPromo merchantPromo = (AddMerchantPromo) request.getSession(
				true).getAttribute("addMerchantPromotionSession");

		logger.info("username:mid:merchantname" + merchantPromo.getUsername()
				+ ":" + merchantPromo.getMid() + ":"
				+ merchantPromo.getMerchantName());

		if (merchantPromo == null) {
			return "redirect:" + URL_BASE + "/list/1";
		}
		PageBean pageBean = new PageBean("Merchant user add Details",
				"nonmerchantweb/promotion/addpromo/addPromoReviewandConfirmNonMerchant",
				Module.PROMOTION, null);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantPromo", merchantPromo);
		return TEMPLATE_NONMERCHANT;
	}

	@RequestMapping(value = { "/merchantPromoDetailsReviewAndConfirm" }, method = RequestMethod.POST)
	public String confirmAddMerchant(
			@ModelAttribute("merchantPromo1") final Promotion promotion,
			final Model model, final AddMerchantPromo merchantPromo,
			final HttpServletRequest request, final Principal principal) {
		logger.info("merchant promotion added successfully");
		PageBean pageBean = new PageBean(
				"Succefully New Merchant Added",
				"nonmerchantweb/promotion/addpromo/addnonMerchantPromoAlldoneSuccessful",
				Module.PROMOTION, null);
		// logger.info("about to add Merchant Details Confirms");
		model.addAttribute("pageBean", pageBean);

		AddMerchantPromo addMerchantPromotionSession = (AddMerchantPromo) request
				.getSession(true).getAttribute("addMerchantPromotionSession");

		if (addMerchantPromotionSession == null) {
			return "redirect:/promotionwebNonmerchant1/addMerchantPromotion";
		}

		model.addAttribute(addMerchantPromotionSession);
		// logger.info("about to add Merchant Details Confirms12133");
		promotionService.addnonMerchantPromo(addMerchantPromotionSession);

		// logger.info("about to add Merchant Details Confirms4444444");
		model.addAttribute("merchantPromo", addMerchantPromotionSession);
		// logger.info("about to add Merchant Details Confirms77777777");
		request.getSession(true).removeAttribute("addMerchantPromotionSession");
		return TEMPLATE_NONMERCHANT;
	}

	@RequestMapping(value = { "/detail" }, method = RequestMethod.POST)
	public String listMerchant(final Model model,@RequestParam("id") Long id/* @PathVariable final long id*/)
			throws ParseException {
		// logger.info("Request to display merchant based on ID: " + id);
		PageBean pageBean = new PageBean("Merchant Promo Detail",
				"nonmerchantweb/promotion/nonMerchantpromotionDetail",
				Module.NON_MERCHANT, null);
		Promotion mobiPromo = promotionService.loadMerchantByPk(id);

		Long promoid=mobiPromo.getId();
		logger.info("promoid id edit: "+promoid);
		logger.info("merchant id edit: "+mobiPromo.getMid()+":merchant name: "+mobiPromo.getMerchantName());
		logger.info("nonmerchant promotion details display before edit:"
				+ mobiPromo.getPromoCode());
		// MerchantCustMail merchtMail =
		// promotionService.loadMerchantbyMailId(mobiPromo.getMid());
		
		
		Merchant currentMerchant = merchantService.loadmobileMerchant(mobiPromo.getMid());

		logger.info("merchant id: " + currentMerchant.getId());
		
		List<MobileUser> mobileuser = promotionService
				.loadMobileUserDetails(currentMerchant.getId());

		
		Set<String> tid = new HashSet<String>();
		for (MobileUser t : mobileuser)
		{
			if(t.getTid()!=null)
			{
			String tids = t.getTid();
			logger.info("mobileuser promotion: " + tids);
			mobiPromo.setTid(tids);
			tid.add(tids);
			}
		}
		String username = null;
		Set<String> mobUsername = new HashSet<String>();
		for (MobileUser t : mobileuser) {
			 username = t.getUsername();
			logger.info("mobile user name: " + username);
			//mobiPromo.setMerchantName(username);
			mobUsername.add(username);
		}

		
		mobiPromo.setMerchantId(String.valueOf(currentMerchant.getId()));
		MerchantDetails merchantDetails = promotionService
				.loadMerchantPoints(mobiPromo.getMid());

		//TerminalDetails td = promotionService.loadTid(mobiPromo.getTid());

		AddMerchantPromo amp = new AddMerchantPromo();
		/*if (td != null) {
			amp.setDeviceId(td.getDeviceId());

			// logger.info("display deviceid:"+ amp.getDeviceId());

		}*/
		amp.setId(mobiPromo.getId().toString());
		amp.setMerchantId(mobiPromo.getMerchantId());
		amp.setUsername(username);
		amp.setMerchantName(mobiPromo.getMerchantName());
		amp.setMid(mobiPromo.getMid());
		amp.setpCode(mobiPromo.getPromoCode());
		amp.setPromoName(mobiPromo.getPromoName());
		amp.setPromoDesc(mobiPromo.getPromoDesc());
		amp.setStatus(mobiPromo.getStatus().toString());
		amp.setTid(mobiPromo.getTid());
		
		// logger.info("check merchant count:" + mobiPromo.getMsgCount());
		amp.setPoints(mobiPromo.getMsgCount());
		/*
		 * String date = mobiPromo.getValidityDate(); //String
		 * sd=forSettlement.getTimeStamp(); String rd = new
		 * SimpleDateFormat("dd-MMM-yyyy").format(new
		 * SimpleDateFormat("yyyy-MM-dd").parse(date));
		 * 
		 * logger.info("edit validity date:" + rd);
		 * 
		 * amp.setValidityDate(rd);
		 */

		String rdFrom = new SimpleDateFormat("yyyy-MM-dd")
				.format(new SimpleDateFormat("dd/MM/yyyy").parse(mobiPromo
						.getValidFrom()));

		String rdTo = new SimpleDateFormat("yyyy-MM-dd")
				.format(new SimpleDateFormat("dd/MM/yyyy").parse(mobiPromo
						.getValidTo()));

		logger.info("format date : " + rdFrom + " " + rdTo);

		amp.setValidFrom(rdFrom);

		amp.setValidTo(rdTo);

		StringBuffer sf = new StringBuffer();
		String filePath = null;
		String filePath1 = null;
		FileInputStream fs = null;
		FileInputStream fs1 = null;
		try {
			filePath = mobiPromo.getImgPath1();
			filePath1 = mobiPromo.getImgPath2();

			logger.info("display file path:" + filePath);
			if (filePath != null) {
				File img = new File(filePath);
				byte imagedata[] = new byte[(int) img.length()];
				// FileInputStream fs = new FileInputStream(img);
				fs = new FileInputStream(img);
				fs.read(imagedata);
				String imgRead = Base64.encode(imagedata);
				// logger.info("display file path2:" + imgRead);
				amp.setPromoLogo1(imgRead);
				logger.info("check promo image1:" + amp.getPromoLogo1());
				fs.close();
			}
			if (filePath1 != null) {

				// filePath1 = mobiPromo.getImgPath2();
				File img1 = new File(filePath1);
				// logger.info("display file path1:" + "filePath");
				// FileInputStream fs1 = new FileInputStream(img1);
				fs1 = new FileInputStream(img1);
				byte imagedata1[] = new byte[(int) img1.length()];
				fs1.read(imagedata1);

				String imgRead1 = Base64.encode(imagedata1);
				logger.info("display file path2:" + imgRead1);
				amp.setPromoLogo2(imgRead1);
				logger.info("display file path2:" + amp.getPromoLogo2());

				fs1.close();
			}

		} catch (IOException e) {
			// e.printStackTrace();
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (fs1 != null) {
				try {
					fs1.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fs1 != null) {
				try {
					fs1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantPromo", amp);
		logger.info("nonmerhcant promotion edit method called:");
		return TEMPLATE_NONMERCHANT;
	}

	@RequestMapping(value = { "editNonMerchantEzyAds" }, method = RequestMethod.POST)
	public String displayEditMerchantPromotion(final Model model,
			/*@PathVariable final Long id*/ final HttpServletRequest request,
			@RequestParam("id") Long id,
			final Principal principal) throws ParseException {
		PageBean pageBean = new PageBean("Merchant Detail",
				"nonmerchantweb/promotion/editpromo/nonMerchantpromotionEditDetails",
				Module.PROMOTION, null);
		logger.info("id non merchat "+id);
		Promotion mobiPromo = promotionService.loadMerchantByPk(id);
		
		logger.info("merhcant promotion edit details:" + mobiPromo.getTid());
		
		MerchantDetails merchantDetails = promotionService
				.loadMerchantPoints(mobiPromo.getMid());
		//TerminalDetails td = promotionService.loadTid(mobiPromo.getTid());
		
		Merchant currentMerchant = merchantService.loadmobileMerchant(mobiPromo.getMid());

		logger.info("merchant id: " + currentMerchant.getId());

		List<MobileUser> mobileuser = promotionService
				.loadMobileUserDetails(currentMerchant.getId());

		
		Set<String> tid = new HashSet<String>();
		for (MobileUser t : mobileuser)
		{
			if(t.getTid()!=null)
			{
			String tids = t.getTid();
			logger.info("mobileuser promotion: " + tids);
			mobiPromo.setTid(tids);
			tid.add(tids);
			}
		}
		String username =null;
		Set<String> mobUsername = new HashSet<String>();
		for (MobileUser t : mobileuser) {
			 username = t.getUsername();
			logger.info("mobile user name: " + username);
			
			mobUsername.add(username);
		}


		
		
		
		
		
		AddMerchantPromo amp = new AddMerchantPromo();
		//amp.setDeviceId(td.getDeviceId());
		amp.setId(mobiPromo.getId().toString());
		amp.setMerchantId(mobiPromo.getMerchantId());
		amp.setUsername(username);
		amp.setMerchantName(mobiPromo.getMerchantName());
		amp.setMid(mobiPromo.getMid());
		amp.setpCode(mobiPromo.getPromoCode());
		amp.setPromoName(mobiPromo.getPromoName());
		amp.setPromoDesc(mobiPromo.getPromoDesc());
		amp.setStatus(mobiPromo.getStatus().toString());
		amp.setPoints(mobiPromo.getMsgCount());
		amp.setTid(mobiPromo.getTid());
		amp.setStatus(mobiPromo.getStatus().toString());
		// String date = mobiPromo.getValidityDate();
		// String sd=forSettlement.getTimeStamp();
		// String rd = new SimpleDateFormat("dd-MMM-yyyy").format(new
		// SimpleDateFormat("yyyy-MM-dd").parse(date));

		// logger.info("edit validity dat3313131e:" + rd);

		// amp.setValidityDate(rd);

		String rdFrom = new SimpleDateFormat("yyyy-MM-dd")
				.format(new SimpleDateFormat("dd/MM/yyyy").parse(mobiPromo
						.getValidFrom()));

		String rdTo = new SimpleDateFormat("yyyy-MM-dd")
				.format(new SimpleDateFormat("dd/MM/yyyy").parse(mobiPromo
						.getValidTo()));

		amp.setValidFrom(rdFrom);

		amp.setValidTo(rdTo);

		StringBuffer sf = new StringBuffer();
		String filePath = null;
		String filePath1 = null;
		FileInputStream fs = null;
		FileInputStream fs1 = null;
		try {
			if (mobiPromo.getImgPath1() != null) {
				filePath = mobiPromo.getImgPath1();
				// logger.info("display file path:" + filePath);
				File img = new File(filePath);
				// logger.info("display file path1:" + "filePath");
				// logger.info("display file path2:" + img);
				// FileInputStream fs = new FileInputStream(img);
				fs = new FileInputStream(img);
				byte imagedata[] = new byte[(int) img.length()];
				fs.read(imagedata);
				String imgRead = Base64.encode(imagedata);
				amp.setPromoLogo1(imgRead);
				fs.close();
			}
			if (mobiPromo.getImgPath2() != null) {
				filePath1 = mobiPromo.getImgPath2();

				File img1 = new File(filePath1);
				// FileInputStream fs1 = new FileInputStream(img1);
				fs1 = new FileInputStream(img1);
				byte imagedata1[] = new byte[(int) img1.length()];
				fs1.read(imagedata1);
				String imgRead1 = Base64.encode(imagedata1);

				amp.setPromoLogo2(imgRead1);
				fs1.close();
			}

		} catch (IOException e) {
			// e.printStackTrace();
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (fs1 != null) {
				try {
					fs1.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (fs1 != null) {
				try {
					fs1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantPromo", amp);
		logger.info("edit merchant promotion confirm page");
		request.getSession(true).setAttribute("editMerchantPromoSession", amp);
		return TEMPLATE_NONMERCHANT;
	}

	@RequestMapping(value = { "/editMerchantPromotion" }, method = RequestMethod.POST)
	public String processEditUserForm(
			@ModelAttribute("mobiPromo") final AddMerchantPromo addMerchantPromo,

			final HttpServletRequest request, final ModelMap model,
			final Principal principal,
			@RequestParam("id") final Long id,
			@RequestParam("status") final String status,
			@RequestParam(required = false) final String points,
			@RequestParam("pCode") final String pCode,
			@RequestParam("merchantName") final String merchantName,
			@RequestParam("mid") final String mid,
			@RequestParam("promoName") final String promoName,
			@RequestParam("promoDesc") final String promoDesc,
			@RequestParam("promoLogo1") final String promoLogo1,
			@RequestParam("username") final String username,
			// @RequestParam("validityDate") final String validityDate,
			@RequestParam(required = false) final String validFrom,
			@RequestParam(required = false) final String validTo,
			@RequestParam(required = false) final String promoLogo2)
			throws ParseException {
		logger.info("Request to display nonmerchantpromotion edit");
		Promotion mobiPromo = promotionService.loadMerchantByPk(id);
		AddMerchantPromo promo = new AddMerchantPromo();
		// logger.info("edit merchant promotion request details display:" +
		// mobiPromo.getMid());
		MerchantDetails merchantDetails = promotionService
				.loadMerchantPoints(mobiPromo.getMid());

		{
			PageBean pageBean = new PageBean("Merchant promotion Edit Details",
					"nonmerchantweb/promotion/editpromo/nonMerchantpromotionEditDetails",
					Module.NON_MERCHANT, null);
			// logger.info("display promo Id:" + promo.getId());
			promo.setUsername(username);
			promo.setId(id.toString());
			promo.setMerchantName(merchantName);
			promo.setMid(mid);
			promo.setId(id.toString());
			promo.setPromoName(promoName);
			promo.setpCode(pCode);
			// logger.info("promo code testing:" + promo.getpCode());
			promo.setPromoDesc(promoDesc);
			// promo.setValidFrom(validFrom);
			// promo.setValidTo(validTo);
			promo.setStatus(status);
			if (promo.getStatus().equals("REPOST")) {
				promo.setPoints(points);
				String date = validFrom;
				logger.info("edit validity date121313" + validFrom);
				SimpleDateFormat dateFormat1 = new SimpleDateFormat(
						"yyyy-MM-dd");
				try {
					date = dateFormat1
							.format(new SimpleDateFormat("dd/MM/yyyy")
									.parse(date));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("edit promo validity date:" + date);
				promo.setValidFrom(date);

				String date1 = validTo;
				logger.info("edit validity date121313" + validFrom);
				SimpleDateFormat dateFormat2 = new SimpleDateFormat(
						"yyyy-MM-dd");
				try {
					date1 = dateFormat2.format(new SimpleDateFormat(
							"dd/MM/yyyy").parse(date1));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				logger.info("edit promo validity date:" + date);
				promo.setValidTo(date1);
			}

			else {
				promo.setPoints(mobiPromo.getMsgCount());

				String rdFrom = new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("dd/MM/yyyy")
								.parse(mobiPromo.getValidFrom()));

				String rdTo = new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("dd/MM/yyyy")
								.parse(mobiPromo.getValidTo()));

				promo.setValidFrom(rdFrom);

				promo.setValidTo(rdTo);

				// promo.setValidFrom(mobiPromo.getValidFrom());

				// promo.setValidTo(mobiPromo.getValidTo());
				logger.info("check promotion valid date cancelled condfdfdf222:"
						+ promo.getValidTo());
				logger.info("check promotion valid date cancelled cond65657:"
						+ validTo);
				logger.info("check promotion valid date cancelled cond9999:"
						+ validFrom);
			}
			// logger.info("display merchant status:" + promo.getStatus());
			promo.setPromoLogo1(promoLogo1);
			promo.setPromoLogo2(promoLogo2);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("merchantPromo", promo);
			// PCI
			request.getSession(true).setAttribute("editMerchantPromoSession",
					promo);
		}
		return "redirect:/promotionwebNonmerchant1/editReviewandConfirm";
	}

	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditAgentReview(final Model model,
			final HttpServletRequest request) {
		logger.info("about to edit Promotion Details ReviewAndConfirm");

		AddMerchantPromo promo = (AddMerchantPromo) request.getSession(true)
				.getAttribute("editMerchantPromoSession");
		if (promo == null) {
			return "redirect:" + URL_BASE + "/editMerchantPromotion/1";
		}
		PageBean pageBean = new PageBean(
				"Merchant promotion Edit Review Details",
				"nonmerchantweb/promotion/editpromo/nonmerchantPromoReviewAndConfirm",
				Module.NON_MERCHANT, "agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantPromo", promo);
		return TEMPLATE_NONMERCHANT;
	}

	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.POST)
	public String confirmEditPromotion(final Promotion promotion,
			final Model model, final HttpServletRequest request) {
		logger.info("about to edit Promotion Details Confirms");
		PageBean pageBean = new PageBean(
				"Successfully promotion details edited",
				"nonmerchantweb/promotion/editpromo/nonmerchantPromoAllDone",
				Module.NON_MERCHANT, null);

		model.addAttribute("pageBean", pageBean);
		AddMerchantPromo merchantPromoSavedInHttpSession = (AddMerchantPromo) request
				.getSession(true).getAttribute("editMerchantPromoSession");
		if (merchantPromoSavedInHttpSession == null) {
			return "redirect:/promotionwebNonmerchant1/editMerchantPromotion";
		}
		model.addAttribute("promo", merchantPromoSavedInHttpSession);

		Promotion promo = promotionService.loadMerchantByPk(Long
				.parseLong(merchantPromoSavedInHttpSession.getId()));

		MerchantDetails merchantDetails = promotionService
				.loadMerchantPoints(promo.getMid());

		promotionService.updatePromotionDetails(
				merchantPromoSavedInHttpSession, promo);

		request.getSession(true).removeAttribute("editMerchantPromoSession");

		return TEMPLATE_NONMERCHANT;

	}

}