package com.mobiversa.payment.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.ThreeMonthTxnData;
import com.mobiversa.payment.service.DashBoardService;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.util.DashboardAmount;

@Controller
// @RequestMapping(value = MerchantWebMobileController.URL_BASE)
public class NonMerchantProfileController extends BaseController {
	// public static final String URL_BASE = "/merchantProfile";

	// @Autowired
	// private MobileUserService mobileUserService;

	@Autowired
	private DashBoardService dashBoardService;

	@Autowired
	private MerchantService merchantService;

	/*
	 * @Autowired private NonMerchantService nonmerchantService;
	 */

	static final Logger logger = Logger
			.getLogger(NonMerchantProfileController.class.getName());

	// @RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {

		return "redirect:/nonmerchant/nonmerchantweb";
	}

	@RequestMapping(value = { "/nonmerchProfportal/nonmerchDetailsweb" }, method = RequestMethod.GET)
	public String nonmerchantDetails(final Model model,
			final Principal principal) {
		logger.info("in noonmerchantprofile/nonmerchant url");
		PageBean pageBean = new PageBean("Mobileuser Detail",
				"home/nonmerchantProfile", Module.NON_MERCHANT,
				"merchantweb/sideMenuMerchantWebMobile");
		Merchant nonmerchant = merchantService
				.loadMerchant(principal.getName());
		model.addAttribute("pageBean", pageBean);
		// model.addAttribute("mobileUser", mobileUser);
		model.addAttribute("nonmerchant", nonmerchant);
		return TEMPLATE_NONMERCHANT;
	}

	@RequestMapping(value = { "/nonmerchProfdetails/changePassWordbynonMerch" }, method = RequestMethod.GET)
	public String changePassword(final Model model, final Principal principal) {
		// logger.info("In change password" );
		PageBean pageBean = new PageBean("NonMerchant Pofile",
				"home/NonMerchantchangePassword", Module.NON_MERCHANT,
				"merchantweb/sideMenuMerchantWebMobile");
		// Merchant merchant =
		// merchantService.loadMerchant(principal.getName());
		model.addAttribute("pageBean", pageBean);
		// model.addAttribute("mobileUser", mobileUser);
		// model.addAttribute("merchant", merchant);
		return TEMPLATE_NONMERCHANT;
	}

	@RequestMapping(value = { "/nonmerchProfile/confirmPasswordbynonMerch" }, method = RequestMethod.POST)
	public String confirmPassword(final Model model, final Principal principal,
			HttpServletRequest request,
			@RequestParam("newPassword") final String newPassword,
			@RequestParam("password") final String password) {
		logger.info("Old PassWord" + password + "New Password " + newPassword);

		int count = merchantService.changeMerchantPassWord(principal.getName(),
				newPassword, password);

		logger.info("confirm password after count" + principal.getName() + " "
				+ count);

		PageBean pageBean = null;

		if (count == 1)
		{
			logger.info("Success");
			pageBean = new PageBean("NonMerchant Pofile",
					"home/changePassSucssNonMer", Module.NON_MERCHANT,
					"nonmerchantweb/sideMenuNonMerchantWebMobile");
		} else {
			logger.info("Failure");
			pageBean = new PageBean("NonMerchant Pofile",
					"home/changePassFailNonMer", Module.NON_MERCHANT,
					"nonmerchantweb/sideMenuNonMerchantWebMobile");
		}
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_NONMERCHANT;
	}

	@RequestMapping(value = { "/nonmerchant/dashBoard" }, method = RequestMethod.GET)
	public String merchantWebDashBoard(final Model model,
			final java.security.Principal principal, HttpServletRequest request) {
		logger.info("Login Person in dash Board:" + principal.getName());
		PageBean pageBean = new PageBean("Dash Board",
				"dashboard/nonmerchantdashbrd", Module.MERCHANT,
				"nonmerchantweb/sideMenuNonMerchantWebMobile");

		HttpSession session = request.getSession();
		// String username = (String)request.getAttribute("un");
		// Merchant merchant = me

		logger.info("Login Person in dash Board:" + principal.getName());
		Merchant merchant = merchantService.loadMerchant(principal.getName());
		session.setAttribute("merchantUserName", merchant.getFirstName());

		logger.info("display merchant mid:id:username"
				+ merchant.getMid().getMid() + ":" + merchant.getId() + ":"
				+ merchant.getUsername());

		/*
		 * int totalDevice =
		 * dashBoardService.getMerchantTotalDevice(merchant.getMid().getMid());
		 * 
		 * model.addAttribute("totalDevice", totalDevice);
		 */
		String totalTxn = dashBoardService
				.getnonMerchantCurrentMonthTxn(merchant.getMid().getMid());

		model.addAttribute("totalTxn", totalTxn);

		// logger.info("check dashboard details:");
		List<ThreeMonthTxnData> data = new ArrayList<ThreeMonthTxnData>();
		// new changes for bar chart 05072017
		// List<ThreeMonthTxnData> txnCountData = new
		// ArrayList<ThreeMonthTxnData>();

		PaginationBean<ThreeMonthTxnData> paginationBean1 = new PaginationBean<ThreeMonthTxnData>();
		// paginationBean1.setCurrPage(currPage);
		// logger.info("check dashboard details787878:");
		List<ThreeMonthTxnData> txnListData = new ArrayList<ThreeMonthTxnData>();
		// logger.info("check mid details:" + merchant.getMid().getMid() );
		txnListData = dashBoardService.getnonMerchantTxnCount(merchant.getMid()
				.getMid());
		// logger.info("check dashboard details457454:");

		// logger.info("check dashboard txn details45677:" +
		// txnListData.size());
		// for(ThreeMonthTxnData txnMonthData: txnCountData){
		for (ThreeMonthTxnData txnMonthData : txnListData) {

			// logger.info("check monthly count12333:" + txnMonthData );

			// logger.info("Controller agentDet123333 : "+txnMonthData.getCount());
			// logger.info("Controller amount23333 : "+txnMonthData.getAmount());
			// logger.info("Controller date33333 : "+txnMonthData.getDate1());
			String a3 = "\"";
			for (String a1 : txnMonthData.getDate1()) {
				a3 = a3 + a1;
				a3 = a3 + "\",\"";
			}
			// System.out.println("Data : "+a3);
			a3 = a3 + "\"";
			// System.out.println("Data : "+a3);
			a3 = a3.replace(",\"\"", "");
			// System.out.println("Data : "+a3);
			txnMonthData.setMonth(a3);

			String b3 = "";
			for (String b1 : txnMonthData.getAmount()) {
				b3 = b3 + b1;
				b3 = b3 + ",";
			}
			// System.out.println("Data : "+b3);
			b3 = b3 + ",";
			// System.out.println("Data : "+b3);
			b3 = b3.replace(",,", "");
			// System.out.println("Data : "+b3);
			txnMonthData.setAmountData(b3);

			String c3 = "";
			for (String c1 : txnMonthData.getCount()) {
				c3 = c3 + c1;
				c3 = c3 + ",";
			}
			// System.out.println("Data : "+c3);
			c3 = c3 + ",";
			System.out.println("Data : " + c3);
			c3 = c3.replace(",,", "");
			// System.out.println("Data : "+c3);
			txnMonthData.setCountData(c3);

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("threeMonthTxn", txnMonthData);

			data.add(txnMonthData);

		}
		// End Agent
		String a1=null,a2=null,a3=null;
		 Float min = 0.0f,max=0.0f;
		for(ThreeMonthTxnData t: txnListData) {
  		logger.info(" check= Amount Data "+t.getAmountData());
  		String[] amt=t.getAmountData().split(",");
  		a1=amt[0];
  		a2=amt[1];
  		a3=amt[2];
  		Float[] a= {Float.valueOf(a1),Float.valueOf(a2),Float.valueOf(a3)};
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
		model.addAttribute("deviceCheck", "No");

		model.addAttribute("paginationBean", paginationBean1);

		return TEMPLATE_NONMERCHANT;

		/*
		 * model.addAttribute("pageBean", pageBean); HttpSession session =
		 * request.getSession(); logger.info("check nonmerchant user name:" +
		 * principal.getName()); Merchant nonmerchant =
		 * nonmerchantService.loadnonMerchant(principal.getName());
		 * session.setAttribute("nonmerchantUserName",
		 * nonmerchant.getUsername()); return TEMPLATE_NONMERCHANT;
		 */
	}

}