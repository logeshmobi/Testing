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
import javax.servlet.http.HttpSession;

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

import com.mobiversa.common.bo.AuditTrail;
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
@RequestMapping(value = MerchantWebAddPromoController.URL_BASE)
public class MerchantWebAddPromoController extends BaseController {

	public static final String URL_BASE = "/promotionweb1";

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
				"merchantweb/promotion/promotionList", Module.PROMOTION,
				"merchantweb/sideMenuMerchantWebMobile");
		model.addAttribute("pageBean", pageBean);
		
		HttpSession session=request.getSession();
		// logger.info("about to list all  transaction");
		String myName = (String) session.getAttribute("userName");
		//String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PaginationBean<Promotion> paginationBean = new PaginationBean<Promotion>();
		paginationBean.setCurrPage(currPage);
		request.getSession(true).setAttribute("CURRENT_USER_MERCHANT",
				currentMerchant);
		promotionService.listPromotions(paginationBean, currentMerchant);
		//logger.info("created date: ");
		for (Promotion promotion : paginationBean.getItemList()) 
		{
			//logger.info("created date: "+promotion.getCreatedDate());
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// *** add merchant code started
	@RequestMapping(value = { "/addMerchantPromotion" }, method = RequestMethod.GET)
	public String displayAddMerchantPromo(
			@ModelAttribute("merchantPromo") final AddMerchantPromo addMerchantPromo,
			final HttpServletRequest request, Model model,
			java.security.Principal principal) {
		//String myName = principal.getName();
		HttpSession session=request.getSession();
		// logger.info("about to list all  transaction");
		String myName = (String) session.getAttribute("userName");
		logger.info("check login person in promotion:" + myName);
		logger.info("------------addpromotion  merchnat first():--------------- ");

		
		
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		/*MerchantDetails merchantDetails = promotionService
				.loadMerchantPoints(currentMerchant.getMid().getMid());*/
		MerchantDetails merchantDetails = promotionService
				.loadMerchantPointsbymid(currentMerchant);
		
		/*
		 * if (merchantDetails.getPoints() != null) {
		 * logger.info("check merchant details points:" +
		 * merchantDetails.getPoints()); }
		 */
		logger.info("check merchant mid: " + currentMerchant.getMid().getMid());
		logger.info("check merchant moto mid: " + currentMerchant.getMid().getMotoMid());
		logger.info("check merchant ezypass mid : " + currentMerchant.getMid().getEzypassMid());
		/*
		 * List<TerminalDetails> td = promotionService
		 * .loadTerminalDetails(currentMerchant.getMid().getMid());
		 * 
		 * Set<String> tidList = new HashSet<String>(); for (TerminalDetails t :
		 * td) { String Tid = t.getTid(); tidList.add(Tid); }
		 * 
		 * Set<String> deviceIdList = new HashSet<String>(); for
		 * (TerminalDetails t : td) { String deviceId = t.getDeviceId();
		 * deviceIdList.add(deviceId); }
		 */

		logger.info("check merchant mid: " + currentMerchant.getMid().getMid());

		logger.info("check merchant id: " + currentMerchant.getId());

		List<MobileUser> mobileuser = promotionService
				.loadMobileUserDetails(currentMerchant.getId());

		Set<String> mobUsername = new HashSet<String>();
		for (MobileUser t : mobileuser) {
			if (t.getUsername() != null && t.getTid()!=null) {
				String username = t.getUsername();
				logger.info("mobile user name: " + username);
				//addMerchantPromo.setUsername(username);
				mobUsername.add(username);
			}
		}
		if(merchantDetails!=null){
			if ((merchantDetails.getPoints()) != null) {

			logger.info("merchant message points: "+merchantDetails.getPoints());
			addMerchantPromo.setPoints(merchantDetails.getPoints());

			// logger.info("check merchant points" +
			// addMerchantPromo.getPoints());

		}
}

		addMerchantPromo.setMerchantId(currentMerchant.getId().toString());
		// addMerchantPromo.setMerchantLogo(new
		// String(merchantDetails.getMerchantLogo()));
		if (currentMerchant.getMid().getMid() != null)
		{
			addMerchantPromo.setMid(currentMerchant.getMid().getMid());
		}
		if (currentMerchant.getMid().getMotoMid() != null) {
			addMerchantPromo.setMotoMid(currentMerchant.getMid().getMotoMid());
		} 
		if (currentMerchant.getMid().getEzypassMid() != null)  {
			addMerchantPromo.setEzypassMid(currentMerchant.getMid().getEzypassMid());
		}
		if (currentMerchant.getMid().getEzyrecMid() != null)  {
			addMerchantPromo.setEzyrecMid(currentMerchant.getMid().getEzyrecMid());
		}
		PageBean pageBean = new PageBean("Merchant Detail",
				"merchantweb/promotion/addpromo/addPromo", Module.PROMOTION,
				"merchant/sideMenuMerchant");

		model.addAttribute("pageBean", pageBean);

		model.addAttribute("merchant", currentMerchant);

		// model.addAttribute("tidList", tid);
		model.addAttribute("mobUsername", mobUsername);
		logger.info("------------------add promotion next  page to be display:-----------------");
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/addMerchantPromotion" }, method = RequestMethod.POST)
	public String processAddUserForm(
			@ModelAttribute("merchantPromo") final AddMerchantPromo addMerchantPromo,
			HttpServletRequest request, final ModelMap model,
			final BindingResult errors, final Principal principal,
			HttpServletRequest response,
			@RequestParam("points") final String points) {
		logger.info("about to add Merchant SamePage addMerchantPromotion 2nd method");

	

		logger.info("------------addpromotion  merchnat second():--------------- "
				+ addMerchantPromo.getUsername()
				+ " : "
				+ addMerchantPromo.getPromoName());

		HttpSession session=request.getSession();
		// logger.info("about to list all  transaction");
		String myName = (String) session.getAttribute("userName");
		//String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("mobusername to give ads: "+addMerchantPromo.getUsername());
		logger.info("mid: "+addMerchantPromo.getMid());
		logger.info("moto mid: "+addMerchantPromo.getMotoMid());
		logger.info("ezypass mid: "+addMerchantPromo.getEzypassMid());
		addMerchantPromo.setMerchantId(currentMerchant.getId().toString());
		
		/*if(currentMerchant.getMid().getMid()!=null)
		{
		addMerchantPromo.setMid(currentMerchant.getMid().getMid());
		}
		if(currentMerchant.getMid().getMotoMid()!=null)
		{
			addMerchantPromo.setMid(currentMerchant.getMid().getMotoMid());
		}
		if(currentMerchant.getMid().getMotoMid()!=null)
		{
			addMerchantPromo.setMid(currentMerchant.getMid().getMotoMid());
		}*/
		addMerchantPromo.setMerchantName(currentMerchant.getBusinessName());
		
		/*MerchantDetails merchantDetails = promotionService
				.loadMerchantPoints(currentMerchant.getMid().getMid());*/
		/*MerchantDetails merchantDetails = promotionService
				.loadMerchantPointsbymid(currentMerchant);*/
		// logger.info("get points:" + merchantDetails.getPoints());
		
		List<MobileUser> mobileuser = promotionService
				.loadMobileUserDetails(currentMerchant.getId());

		Set<String> mobUsername = new HashSet<String>();
		for (MobileUser t : mobileuser) {
			if (t.getUsername() != null || !(t.getUsername().isEmpty())) {
				String username = t.getUsername();
				logger.info("mobile user name: " + username);
				//addMerchantPromo.setUsername(username);
				mobUsername.add(username);
			}
		}
		
		//addMerchantPromo.setPoints(merchantDetails.getPoints());

		// logger.info("get points:" + addMerchantPromo.getPoints());
		//addMerchantPromo.setMerchantName(merchantDetails.getMerchantName());
		
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

		// promotion image file1
		if(img1==null && img2==null)
		{
			pageBean = new PageBean("Merchant Detail",
					"merchantweb/promotion/addpromo/addPromo",
					Module.PROMOTION, "merchant/sideMenuMerchant");

			logger.info("not an imge1 and image2..wrong image format..Please verify it..");

			model.addAttribute("responseData",
					" Image1 and Image2 is Not an image..wrong image format..!"); // table
																	// response

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("merchant", currentMerchant);
			model.addAttribute("mobileuser", mobileuser);
			model.addAttribute("mobUsername", mobUsername);
			model.addAttribute("loginname", principal.getName());
			return TEMPLATE_MERCHANT;
		}
		else{
		if (!addMerchantPromo.getImageFile().isEmpty()) 
		{
			
			if(img1==null || img1.getWidth()==0)
			{
				
				pageBean = new PageBean("Merchant Detail",
						"merchantweb/promotion/addpromo/addPromo",
						Module.PROMOTION, "merchant/sideMenuMerchant");

				logger.info("not an imge..wrong image format..Please verify it..");

				model.addAttribute("responseData",
						" Image1 is Not an image..wrong image format..!"); // table
																		// response

				model.addAttribute("pageBean", pageBean);
				model.addAttribute("merchant", currentMerchant);
				model.addAttribute("mobUsername", mobUsername);
				model.addAttribute("loginname", principal.getName());
				return TEMPLATE_MERCHANT;
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
						"MERCHANTPROMO");
				logger.info("rootpath: "+rootPath);
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
							"merchantweb/promotion/addpromo/addPromo",
							Module.PROMOTION, "merchant/sideMenuMerchant");
					logger.info("image directory not exists1..");

					model.addAttribute("responseData",
							" Invalid directory,unable to save the image"); // table
					model.addAttribute("mobUsername", mobUsername);												// response
					// logger.info("check image file folder:");
					model.addAttribute("pageBean", pageBean);
					model.addAttribute("loginname", principal.getName());
					return TEMPLATE_MERCHANT;
				} else {
					BufferedOutputStream stream = null;
					FileInputStream fs = null;
					try {
						if(currentMerchant.getMid().getMid()!=null){
						dir = new File(rootPath + File.separator
								+ currentMerchant.getMid().getMid()
								+ File.separator + date);
						}
						else if(currentMerchant.getMid().getMotoMid()!=null){
							dir = new File(rootPath + File.separator
									+ currentMerchant.getMid().getMotoMid()
									+ File.separator + date);
							}
						else if(currentMerchant.getMid().getEzyrecMid()!=null){
							dir = new File(rootPath + File.separator
									+ currentMerchant.getMid().getEzyrecMid()
									+ File.separator + date);
							}
						else
						{
							dir = new File(rootPath + File.separator
									+ currentMerchant.getMid().getEzypassMid()
									+ File.separator + date);
						}
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
						logger.info("check1: "+addMerchantPromo.getImageFile()
										.getOriginalFilename());
						img1 = ImageIO.read(new File(dir.getAbsolutePath()
								+ File.separator
								+ addMerchantPromo.getImageFile()
										.getOriginalFilename()));
						logger.info("image read1: "+img1);
						// try {
						if(img1==null)
						{
							pageBean = new PageBean("Merchant Detail",
									"merchantweb/promotion/addpromo/addPromo",
									Module.PROMOTION, "merchant/sideMenuMerchant");

							logger.info("Image1 not an image..wrong image format..Please verify it..");

							model.addAttribute("responseData",
									" Image2 is Not an image..wrong image format..!"); // table
																					// response

							model.addAttribute("pageBean", pageBean);
							model.addAttribute("merchant", currentMerchant);
							model.addAttribute("mobUsername", mobUsername);
							model.addAttribute("loginname", principal.getName());
							return TEMPLATE_MERCHANT;
						}
						else{
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
						logger.info("image file path1:  "+fs.read(imagedata));
						imgRead = Base64.encode(imagedata);
						fs.close();
						addMerchantPromo1.setPromoImgFilePath1(dir
								.getAbsolutePath()
								+ File.separator
								+ addMerchantPromo.getImageFile()
										.getOriginalFilename());
						logger.info("merchant promotion image filePath:"
								+ addMerchantPromo1.getPromoImgFilePath1());
						}
					} catch (IOException e) {
						if (fs != null) {
							try {
								fs.close();
							} catch (IOException ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
						}
						if(stream != null){
							try {
								stream.close();
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
							} catch (IOException ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
						}
						if(stream != null){
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

		if (!addMerchantPromo.getImageFile1().isEmpty()) 
		{
			if(img2==null ||img2.getWidth()==0)
			{
				
				pageBean = new PageBean("Merchant Detail",
						"merchantweb/promotion/addpromo/addPromo",
						Module.PROMOTION, "merchant/sideMenuMerchant");

				logger.info("not an imge..wrong image format..Please verify it..");

				model.addAttribute("responseData",
						" Image2 is Not an image..wrong image format..!"); // table
																		// response

				model.addAttribute("pageBean", pageBean);
				model.addAttribute("merchant", currentMerchant);
				model.addAttribute("mobUsername", mobUsername);
				model.addAttribute("loginname", principal.getName());
				return TEMPLATE_MERCHANT;
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
						"MERCHANTPROMO");

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
							"merchantweb/promotion/addpromo/addPromo",
							Module.PROMOTION, "merchant/sideMenuMerchant");

					logger.info("directory not exists2....");

					model.addAttribute("responseData",
							" Invalid directry,unable to save the image"); // table
																			// response
					model.addAttribute("mobUsername", mobUsername);
					model.addAttribute("pageBean", pageBean);
					model.addAttribute("loginname", principal.getName());
					return TEMPLATE_MERCHANT;
				} else {
					BufferedOutputStream stream = null;
					FileInputStream fs = null;
					try {
						if(currentMerchant.getMid().getMid()!=null){
							dir = new File(rootPath + File.separator
									+ currentMerchant.getMid().getMid()
									+ File.separator + date);
							}
							else if(currentMerchant.getMid().getMotoMid()!=null){
								dir = new File(rootPath + File.separator
										+ currentMerchant.getMid().getMotoMid()
										+ File.separator + date);
								}
							else if(currentMerchant.getMid().getEzyrecMid()!=null){
								dir = new File(rootPath + File.separator
										+ currentMerchant.getMid().getEzyrecMid()
										+ File.separator + date);
								}
							else
							{
								dir = new File(rootPath + File.separator
										+ currentMerchant.getMid().getEzypassMid()
										+ File.separator + date);
							}
						/*dir = new File(rootPath + File.separator
								+ currentMerchant.getMid().getMid()
								+ File.separator + date);*/
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
						
						logger.info("image read2: "+img2);
						// try {
						if(img2==null)
						{
							pageBean = new PageBean("Merchant Detail",
									"merchantweb/promotion/addpromo/addPromo",
									Module.PROMOTION, "merchant/sideMenuMerchant");

							logger.info("not an imge..wrong image format..Please verify it..");

							model.addAttribute("responseData",
									" Image2 is Not an image..wrong image format..!"); // table
																					// response

							model.addAttribute("pageBean", pageBean);
							model.addAttribute("merchant", currentMerchant);
							model.addAttribute("mobUsername", mobUsername);
							model.addAttribute("loginname", principal.getName());
							return TEMPLATE_MERCHANT;
						}
						else
						{
						File img = new File(dir.getAbsolutePath()
								+ File.separator
								+ addMerchantPromo.getImageFile1()
										.getOriginalFilename());

						logger.info("image file2 : "+img);
						//FileInputStream fs = new FileInputStream(img);
						fs = new FileInputStream(img);
						byte imagedata[] = new byte[(int) img.length()];
						fs.read(imagedata);
						logger.info("image file2 : "+img);
						imgRead1 = Base64.encode(imagedata);
						fs.close();

						addMerchantPromo1.setPromoImgFilePath2(dir
								.getAbsolutePath()
								+ File.separator
								+ addMerchantPromo.getImageFile1()
										.getOriginalFilename());
						logger.info("merchant promotion image filePath:"
								+ addMerchantPromo1.getPromoImgFilePath2());
						}
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
					}finally{
						if(fs != null){
							try {
								fs.close();
							} catch (IOException ee) {
								// TODO Auto-generated catch block
								ee.printStackTrace();
							}
						}
						if(stream != null){
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

		logger.info("mobile username: "+addMerchantPromo.getUsername());
		addMerchantPromo1.setTid(addMerchantPromo.getTid());
		addMerchantPromo1.setUsername(addMerchantPromo.getUsername());
		addMerchantPromo1.setMerchantId(addMerchantPromo.getMerchantId());
		addMerchantPromo1.setMid(addMerchantPromo.getMid());
		addMerchantPromo1.setMotoMid(addMerchantPromo.getMotoMid());
		addMerchantPromo1.setEzyrecMid(addMerchantPromo.getEzyrecMid());
		addMerchantPromo1.setEzypassMid(addMerchantPromo.getEzypassMid());
		logger.info("mid: "+addMerchantPromo1.getMid());
		logger.info("moto mid: "+addMerchantPromo1.getMotoMid());
		logger.info("ezypass mid: "+addMerchantPromo1.getEzypassMid());
		addMerchantPromo1.setMerchantName(addMerchantPromo.getMerchantName());
		addMerchantPromo1.setMerchantId(addMerchantPromo.getMerchantId());
		addMerchantPromo1.setSendType(addMerchantPromo.getSendType());
		addMerchantPromo1.setMerchantType(addMerchantPromo.getMerchantType());
		addMerchantPromo1.setPromoName(addMerchantPromo.getPromoName());
		addMerchantPromo1.setPromoDesc(addMerchantPromo.getPromoDesc());

		
		logger.info("send type: "+addMerchantPromo1.getSendType());
		logger.info("merchant type: "+addMerchantPromo1.getMerchantType());
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

		String validFrom = addMerchantPromo.getValidFrom();
		String validTo = addMerchantPromo.getValidTo();
		logger.info("validity date format in promo:" + validTo);
		/*
		 * SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
		 * try { dat1 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
		 * .parse(dat1)); logger.info("validity date format in promo from date:"
		 * + dat1); } catch (ParseException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
		addMerchantPromo1.setValidFrom(validFrom);

		// SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
		/*
		 * try { dat2 = dateFormat1.format(new SimpleDateFormat("dd/MM/yyyy")
		 * .parse(dat2)); logger.info("validity date format in promo to date:" +
		 * dat2); } catch (ParseException e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); }
		 */
		addMerchantPromo1.setValidTo(validTo);

		// addMerchantPromo1.setValidityDate(addMerchantPromo.getValidityDate());
		logger.info("check validity date:" + addMerchantPromo1.getValidFrom());

		logger.info("check validity date:" + addMerchantPromo1.getValidTo());
		// end

		addMerchantPromo1.setpCode(addMerchantPromo.getMerchantName()
				.substring(0, 3).trim().toUpperCase()
				+ RandomStringUtils.randomNumeric(5));
		addMerchantPromo1.setPromoDesc(addMerchantPromo.getPromoDesc());
		addMerchantPromo1.setPoints(points);
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantPromo1", addMerchantPromo1);
		// model.addAttribute("mobusername",request.getParameter(""))
		// PCI
		request.getSession(true).setAttribute("addMerchantPromotionSession",
				addMerchantPromo1);
		return "redirect:/promotionweb1/merchantPromoDetailsReviewAndConfirm";
	}

	@RequestMapping(value = { "/merchantPromoDetailsReviewAndConfirm" }, method = RequestMethod.GET)
	public String displayAddUserConfirmation(final ModelMap model,
			final HttpServletRequest request,
			final AddMerchantPromo merchantPromo1, final Principal principal) {
		logger.info("add merchant confirm page:");
		AddMerchantPromo merchantPromo = (AddMerchantPromo) request.getSession(
				true).getAttribute("addMerchantPromotionSession");
		if (merchantPromo == null) {
			return "redirect:" + URL_BASE + "/list/1";
		}
		logger.info("mobileusername: "+merchantPromo.getUsername());
		logger.info("merchant id: "+merchantPromo.getMerchantId());
		PageBean pageBean = new PageBean("Merchant user add Details",
				"merchantweb/promotion/addpromo/addPromoReviewandConfirm",
				Module.PROMOTION, "merchant/sideMenuMerchant");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantPromo", merchantPromo);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/merchantPromoDetailsReviewAndConfirm" }, method = RequestMethod.POST)
	public String confirmAddMerchant(
			@ModelAttribute("merchantPromo1") final Promotion promotion,
			final Model model, final AddMerchantPromo merchantPromo,
			final HttpServletRequest request, final Principal principal) {
		//logger.info("merchant promotion added successfully");
		

		AddMerchantPromo addMerchantPromotionSession = (AddMerchantPromo) request
				.getSession(true).getAttribute("addMerchantPromotionSession");
		
		logger.info("username "+addMerchantPromotionSession.getUsername());
		logger.info(addMerchantPromotionSession.getMerchantName());
		if (addMerchantPromotionSession == null) {
			return "redirect:/promotionweb1/addMerchantPromotion";
		}

		logger.info("/merchantPromoDetailsReviewAndConfirm merchant id:"+addMerchantPromotionSession.getMerchantId());
		model.addAttribute(addMerchantPromotionSession);
		// logger.info("about to add Merchant Details Confirms12133");
		
		 Promotion addedPromo=promotionService.addMerchantPromo(addMerchantPromotionSession);
		
		if (addedPromo!= null) {
			Merchant currentMerchant = merchantService
					.loadMidtoUpdateAudit(addedPromo.getMid());
			logger.info("username: "+currentMerchant.getUsername()+" "+
					currentMerchant.getMid().getEzyrecMid());
			AuditTrail auditTrail = merchantService.updateAuditTrailByMerchant(
					addMerchantPromotionSession.getUsername(),
					currentMerchant.getUsername(), "addEzyAds");

			if (auditTrail.getUsername() != null) {
				logger.info("EZYAds Added Successfully by.."
						+ auditTrail.getUsername() + "  Promo code: "
						+ addMerchantPromotionSession.getpCode());
			}

			PageBean pageBean = new PageBean(
					"Succefully New Merchant Added",
					"merchantweb/promotion/addpromo/addMerchantPromoAlldoneSuccessful",
					Module.PROMOTION, "merchant/sideMenuMerchant");
			// logger.info("about to add Merchant Details Confirms");
			model.addAttribute("pageBean", pageBean);
			addMerchantPromotionSession.setMid(addedPromo.getMid());
			model.addAttribute("merchantPromo", addMerchantPromotionSession);
			request.getSession(true).removeAttribute("addMerchantPromotionSession");
		}
		else
		{
			PageBean pageBean = new PageBean(
					"UnSuccessful New Merchant Added",
					"merchantweb/promotion/addpromo/addMerchantPromoUnSuccessful",
					Module.PROMOTION, "merchant/sideMenuMerchant");
			model.addAttribute("pageBean", pageBean);
			//addMerchantPromotionSession.setMid(addedPromo.getMid());
			model.addAttribute("merchantPromo", addMerchantPromotionSession);
		}
		
		// logger.info("about to add Merchant Details Confirms77777777");
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	/*@RequestMapping(value = { "/detail/{id}" }, method = RequestMethod.GET)
	public String listMerchant(final Model model, @PathVariable final long id)
			 {*/
	@RequestMapping(value = { "/detail" }, method = RequestMethod.POST)
	public String listMerchant(final Model model,@RequestParam("status") final String status,
			@RequestParam("id") final Long id/*@PathVariable final long id*/, final Principal principal)
			 {
		
		
		logger.info("/detail");
		
		logger.info("Request to display merchant based on ID: /details" + id+" status: "+status);
		
		PageBean pageBean = new PageBean("Merchant Promo Detail",
				"merchantweb/promotion/promotionDetail", Module.MERCHANT,
				"merchant/sideMenuMerchant");
		
		Promotion mobiPromo = promotionService.loadMerchantByPk(id);

		logger.info("merchant promotion details display before edit:"
				+ mobiPromo.getPromoCode());
		// MerchantCustMail merchtMail =
		// promotionService.loadMerchantbyMailId(mobiPromo.getMid());

		MerchantDetails merchantDetails = promotionService
				.loadMerchantPoints(mobiPromo.getMid());

		Long promoid = mobiPromo.getId();
		logger.info("promoid id edit: " + promoid);
		logger.info("merchant id edit: " + mobiPromo.getMid()
				+ ":merchant name: " + mobiPromo.getMerchantName());
		logger.info("merchant promotion details display before edit:"
				+ mobiPromo.getPromoCode());
		// MerchantCustMail merchtMail =
		// promotionService.loadMerchantbyMailId(mobiPromo.getMid());

		Merchant currentMerchant = merchantService.loadmobileMerchant(mobiPromo
				.getMid());

		logger.info("merchant id: " + currentMerchant.getId());

		List<MobileUser> mobileuser = promotionService
				.loadMobileUserDetails(currentMerchant.getId());

		Set<String> tid = new HashSet<String>();
		for (MobileUser t : mobileuser) {
			if (t.getTid() != null) {
				String tids = t.getTid();
				logger.info("mobileuser promotion: " + tids);
				//mobiPromo.setTid(tids);
				tid.add(tids);
			}
		}
		String username = null;
		Set<String> mobUsername = new HashSet<String>();
		for (MobileUser t : mobileuser) {
			username = t.getUsername();
			logger.info("mobile user name: " + username);
			// mobiPromo.setMerchantName(username);
			mobUsername.add(username);
		}

		mobiPromo.setMerchantId(String.valueOf(currentMerchant.getId()));

		// TerminalDetails td = promotionService.loadTid(mobiPromo.getTid());

		AddMerchantPromo amp = new AddMerchantPromo();
		/*
		 * if (td != null) { amp.setDeviceId(td.getDeviceId());
		 * 
		 * // logger.info("display deviceid:"+ amp.getDeviceId());
		 * 
		 * }
		 */
		amp.setUsername(username);
		amp.setTid(mobiPromo.getTid());
		amp.setId(mobiPromo.getId().toString());
		amp.setMerchantId(mobiPromo.getMerchantId());
		amp.setMerchantName(mobiPromo.getMerchantName());
		amp.setMid(mobiPromo.getMid());
		amp.setpCode(mobiPromo.getPromoCode());
		amp.setPromoName(mobiPromo.getPromoName());
		amp.setPromoDesc(mobiPromo.getPromoDesc());
		amp.setStatus(mobiPromo.getStatus().toString());
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

		String validFrom = mobiPromo.getValidFrom();
		
		/*String rd = new SimpleDateFormat("yyyy-MMM-dd")
				.format(new SimpleDateFormat("dd/mm/yyyy").parse(date));*/
		amp.setValidFrom(validFrom);

		String validTo = mobiPromo.getValidTo();
		logger.info("valid date from and to; "+validFrom+":"+validTo);
		/*String rd1 = new SimpleDateFormat("yyyy-MMM-dd")
				.format(new SimpleDateFormat("dd/mm/yyyy").parse(date1));*/
		amp.setValidTo(validTo);

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
				//FileInputStream fs = new FileInputStream(img);
				fs = new FileInputStream(img);
				fs.read(imagedata);
				String imgRead = Base64.encode(imagedata);
				// logger.info("display file path2:" + imgRead);
				amp.setPromoLogo1(imgRead);
				logger.info("check promo image1:" + amp.getPromoLogo1());
				fs.close();
			}
			if (filePath1 != null) {

				//filePath1 = mobiPromo.getImgPath2();
				File img1 = new File(filePath1);
				// logger.info("display file path1:" + "filePath");
				//FileInputStream fs1 = new FileInputStream(img1);
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
			//e.printStackTrace();
			if(fs != null){
				try {
					fs.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if(fs1 != null){
					try {
						fs1.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
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
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantPromo", amp);
		logger.info("merhcant promotion edit method called:");
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	/*@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public String displayEditMerchantPromotion(final Model model,
			@PathVariable final Long id, final HttpServletRequest request,
			final Principal principal) {*/
	@RequestMapping(value = { "/editMerchantEzyAds" }, method = RequestMethod.POST)
	public String displayEditMerchantPromotion(final Model model,@RequestParam("id") Long id,
		/*	@PathVariable final Long id,*/ final HttpServletRequest request,
			final Principal principal) {
		
		
		logger.info("/editMerchantEzyAds");
		
		PageBean pageBean = new PageBean("Merchant Detail",
				"merchantweb/promotion/editpromo/promotionEditDetails",
				Module.PROMOTION, "merchant/sideMenuMerchant");
		//Long id=423L;
		logger.info("in edit controller: "+id);
		//@RequestParam("merchantId") Long id
		Promotion mobiPromo = promotionService.loadMerchantByPk(id);
		// logger.info("merhcant promotion edit details:" + mobiPromo.getTid());
		
		// TerminalDetails td = promotionService.loadTid(mobiPromo.getTid());
		Merchant currentMerchant = merchantService.loadmobileMerchant(mobiPromo
				.getMid());

		logger.info("merchant id: " + currentMerchant.getId());

		//MerchantDetails merchantDetails = promotionService.loadMerchantPointsallmid(currentMerchant);
		AddMerchantPromo amp = new AddMerchantPromo();
		List<MobileUser> mobileuser = promotionService
				.loadMobileUserDetailsbytid(mobiPromo.getTid());

		
		for (MobileUser t : mobileuser) {
			if(t.getUsername()!=null){
				amp.setUsername(t.getUsername());
			}
		}
		/*Set<String> tid = new HashSet<String>();
		for (MobileUser t : mobileuser) {
			if (t.getTid() != null) {
				String tids = t.getTid();
				if(mobiPromo.getTid()==t.getTid())
				{
					amp.setUsername(t.getUsername());
				}
				logger.info("mobileuser promotion: " + tids);
				//mobiPromo.setTid(tids);
				tid.add(tids);
			}
		}
		String username = null;
		Set<String> mobUsername = new HashSet<String>();
		for (MobileUser t : mobileuser) {
			username = t.getUsername();
			logger.info("mobile user name: " + username);

			mobUsername.add(username);
		}
*/
		
		// amp.setDeviceId(td.getDeviceId());
		//amp.setUsername(username);
		
		amp.setTid(mobiPromo.getTid());
		amp.setId(mobiPromo.getId().toString());
		amp.setMerchantId(mobiPromo.getMerchantId());
		amp.setMerchantName(mobiPromo.getMerchantName());
		amp.setMid(mobiPromo.getMid());
		amp.setpCode(mobiPromo.getPromoCode());
		amp.setPromoName(mobiPromo.getPromoName());
		amp.setPromoDesc(mobiPromo.getPromoDesc());
		amp.setStatus(mobiPromo.getStatus().toString());
		amp.setPoints(mobiPromo.getMsgCount());

		amp.setStatus(mobiPromo.getStatus().toString());
		// String date = mobiPromo.getValidityDate();
		// String sd=forSettlement.getTimeStamp();
		// String rd = new SimpleDateFormat("dd-MMM-yyyy").format(new
		// SimpleDateFormat("yyyy-MM-dd").parse(date));

		// logger.info("edit validity dat3313131e:" + rd);

		// amp.setValidityDate(rd);

		String date = mobiPromo.getValidFrom();
		/*String rd = new SimpleDateFormat("dd-MMM-yyyy")
				.format(new SimpleDateFormat("dd/mm/yyyy").parse(date));*/
		amp.setValidFrom(date);

		String date1 = mobiPromo.getValidTo();
		logger.info("valid date from and to; "+date+":"+date1);
		/*String rd1 = new SimpleDateFormat("dd-MMM-yyyy")
				.format(new SimpleDateFormat("dd/mm/yyyy").parse(date1));*/
		//logger.info("valid date from and to; "+rd+":"+rd1);
		amp.setValidTo(date1);
		
		StringBuffer sf = new StringBuffer();
		String filePath = null;
		String filePath1 = null;
		FileInputStream fs = null;
		FileInputStream fs1 = null;
		try {
			if (mobiPromo.getImgPath1() != null) {
				filePath = mobiPromo.getImgPath1();
				 //logger.info("display file path:" + filePath);
				File img = new File(filePath);
				// logger.info("display file path1:" + "filePath");
				// logger.info("display file path2:" + img);
				//FileInputStream fs = new FileInputStream(img);
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
				//FileInputStream fs1 = new FileInputStream(img1);
				fs1 = new FileInputStream(img1);
				byte imagedata1[] = new byte[(int) img1.length()];
				fs1.read(imagedata1);
				String imgRead1 = Base64.encode(imagedata1);

				amp.setPromoLogo2(imgRead1);
				fs1.close();
			}
			
		} catch (IOException e) {
			//e.printStackTrace();
			if(fs != null){
					try {
						fs.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
			if(fs1 != null){
					try {
						fs1.close();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
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
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantPromo", amp);
		logger.info("edit merchant promotion confirm page");
		request.getSession(true).setAttribute("editMerchantPromoSession", amp);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/editMerchantPromotion" }, method = RequestMethod.POST)
	public String processEditUserForm(
			@ModelAttribute("merchantPromo") final AddMerchantPromo addMerchantPromo,

			final HttpServletRequest request, final ModelMap model,
			final Principal principal
			/*@RequestParam("id") final Long id,
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
			@RequestParam(required = false) final String promoLogo2*/)
			{
		logger.info("/editMerchantPromotion"+ addMerchantPromo.getId());
		
		logger.info("merchant id to edit promo: "+addMerchantPromo.getMerchantId());
		
		Promotion mobiPromo = promotionService.loadMerchantByPk(Long.valueOf(addMerchantPromo.getId()));
		//AddMerchantPromo promo = new AddMerchantPromo();
		// logger.info("edit merchant promotion request details display:" +
		// mobiPromo.getMid());
		//MerchantDetails merchantDetails = promotionService.loadMerchantPoints(mobiPromo.getMid());

		{
			PageBean pageBean = new PageBean("Merchant promotion Edit Details",
					"merchantweb/promotion/editpromo/promotionEditDetails",
					Module.MERCHANT, "merchant/sideMenuMerchant");
			// logger.info("display promo Id:" + promo.getId());
			logger.info("merchant id: " + addMerchantPromo.getMerchantId());
			logger.info("mobileusername: " + addMerchantPromo.getUsername());
			logger.info("promo id: " + addMerchantPromo.getId());
			logger.info("merchantname: " + addMerchantPromo.getMerchantName());
			logger.info("mid : " + addMerchantPromo.getMid());
			logger.info("pcode: " + addMerchantPromo.getpCode());
			logger.info("promo name: " + addMerchantPromo.getPromoName());
			logger.info("promo desc: " + addMerchantPromo.getPromoDesc());
			logger.info("status: " + addMerchantPromo.getStatus());
			logger.info("mobileusername: " + addMerchantPromo.getUsername());
			
			
			//promo.setUsername(username);
			//promo.setId(id.toString());
			//promo.setMerchantName(merchantName);
			//promo.setMid(mid);
			//promo.setId(id.toString());
			//promo.setPromoName(promoName);
			//promo.setpCode(pCode);
			//logger.info("promo code testing:" + promo.getpCode());
			//promo.setPromoDesc(promoDesc);
			//promo.setStatus(status);
			if (addMerchantPromo.getStatus().equals("REPOST")) {
				addMerchantPromo.setPoints(addMerchantPromo.getPoints());
				
				String date = addMerchantPromo.getValidFrom();
				try {
					String rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("dd/mm/yyyy").parse(date));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				addMerchantPromo.setValidFrom(date);

				String date1 = addMerchantPromo.getValidTo();
				logger.info("valid date from and to; "+date+":"+date1);
				
				try {
					String rd1 = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("dd/mm/yyyy").parse(date1));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//logger.info("valid date from and to; "+rd+":"+rd1);
				
				//logger.info("edit validity date121313" + rd+":"+rd1);
				addMerchantPromo.setValidTo(date1);
			}

			else {
				addMerchantPromo.setPoints(mobiPromo.getMsgCount());
			
				String rd,rd1;
				try {
					rd = new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("dd/mm/yyyy").parse(mobiPromo.getValidFrom()));
					addMerchantPromo.setValidFrom(rd);

					 rd1 = new SimpleDateFormat("dd-MMM-yyyy")
					.format(new SimpleDateFormat("dd/mm/yyyy").parse(mobiPromo.getValidTo()));
			
					 addMerchantPromo.setValidTo(rd1);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
				// promo.setValidFrom(mobiPromo.getValidFrom());

				// promo.setValidTo(mobiPromo.getValidTo());
				
			}
			// logger.info("display merchant status:" + promo.getStatus());
			/*promo.setPromoLogo1(promoLogo1);
			promo.setPromoLogo2(promoLogo2);*/
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("merchantPromo", addMerchantPromo);
			// PCI
			request.getSession(true).setAttribute("editMerchantPromoSession",
					addMerchantPromo);
		}
		return "redirect:/promotionweb1/editReviewandConfirm";
	}

	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.GET)
	public String displayEditAgentReview(final Model model,
			final HttpServletRequest request, final Principal principal) {
		logger.info("about to edit Promotion Details ReviewAndConfirm");

		AddMerchantPromo promo = (AddMerchantPromo) request.getSession(true)
				.getAttribute("editMerchantPromoSession");
		if (promo == null) {
			return "redirect:" + URL_BASE + "/editMerchantPromotion/1";
		}
		PageBean pageBean = new PageBean(
				"Merchant promotion Edit Review Details",
				"merchantweb/promotion/editpromo/merchantPromoReviewAndConfirm",
				Module.MERCHANT, "agent/sideMenuAgent");
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("merchantPromo", promo);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/editReviewandConfirm" }, method = RequestMethod.POST)
	public String confirmEditPromotion(final Promotion promotion,
			final Model model, final HttpServletRequest request, final Principal principal) {
		logger.info("about to edit Promotion Details Confirms");
		PageBean pageBean = new PageBean(
				"Successfully promotion details edited",
				"merchantweb/promotion/editpromo/merchantPromoAllDone",
				Module.MERCHANT, "merchantweb/edit/sideMenuBankUser");

		model.addAttribute("pageBean", pageBean);
		AddMerchantPromo merchantPromoSavedInHttpSession = (AddMerchantPromo) request
				.getSession(true).getAttribute("editMerchantPromoSession");
		if (merchantPromoSavedInHttpSession == null) {
			return "redirect:/promotionweb1/editMerchantPromotion";
		}
		model.addAttribute("promo", merchantPromoSavedInHttpSession);

		Promotion promo = promotionService.loadMerchantByPk(Long
				.parseLong(merchantPromoSavedInHttpSession.getId()));
		Merchant currentMerchant = merchantService.loadmobileMerchant(promo
				.getMid());

		logger.info("merchant id: " + currentMerchant.getId());

		MerchantDetails merchantDetails = promotionService.loadMerchantPointsallmid(currentMerchant);
		
		/*MerchantDetails merchantDetails = promotionService
				.loadMerchantPoints(promo.getMid());*/

		promotionService.updatePromotionDetails(
				merchantPromoSavedInHttpSession, promo);

		/*Merchant currentMerchant = merchantService.loadMidtoUpdateAudit(promo
				.getMid());*/
		logger.info("current merchant: " + currentMerchant.getUsername());
		/*AuditTrail auditTrail = promotionService
				.updateAuditTrailEditPromotionByMerchant(promo.getMid());
		*/
		AuditTrail auditTrail = merchantService
				.updateAuditTrailByMerchant(currentMerchant.getUsername(), currentMerchant.getUsername(), "editEzyAds");
		
		if (auditTrail.getId() != null) {
			logger.info("EZYAds Status Edited by Merchant: "
					+ auditTrail.getUsername());
		}

		request.getSession(true).removeAttribute("editMerchantPromoSession");
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

}