package com.mobiversa.payment.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mobiversa.common.bo.AuditTrail;
import com.mobiversa.common.bo.CommonStatus;
import com.mobiversa.common.bo.CountryCurPhone;
import com.mobiversa.common.bo.EzyRecurringPayment;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.PushMessage;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.BaseDataImpl;
import com.mobiversa.payment.dto.Country;
import com.mobiversa.payment.dto.MotoTxnDet;
import com.mobiversa.payment.dto.PushNotificationDetails;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MobileUserService;
import com.mobiversa.payment.service.MotoWebService;
import com.mobiversa.payment.service.PushNotificationService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.ResponseDetails;

@Controller
@RequestMapping(value = MerchantDevicePushNotification.URL_BASE)
public class MerchantDevicePushNotification extends BaseController {

	@Autowired
	private MerchantService merchantService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private MobileUserService mobileUserService;

	@Autowired
	private MotoWebService motoWebService;
	
	@Autowired
	private PushNotificationService notificationService;

	public static final String URL_BASE = "/device";
	private static final Logger logger = Logger
			.getLogger(MerchantDevicePushNotification.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("Test1 defaultpage");
		return "redirect:" + URL_BASE + "/list/1";
	}

	

	@RequestMapping(value = { "/notificationReq" }, method = RequestMethod.GET)
	public String motoTransaction(final Model model,
			final java.security.Principal principal, HttpServletRequest request) {

		HttpSession session = request.getSession();
		// logger.info("about to list all  transaction");
		String myName = (String) session.getAttribute("userName");
		
		logger.info("currently logged in as " + myName);
		PageBean pageBean = new PageBean("transactions list",
				"notification/productNotificationDetails",Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		List<MobileUser> mobileuser = mobileUserService.loadMobileUser();
		List<String> tid=new ArrayList<String>();
		List<String> motoTid=new ArrayList<String>();
		List<String> ezywayTid=new ArrayList<String>();
		List<String> ezyrecTid=new ArrayList<String>();
		List<String> ezypassTid=new ArrayList<String>();
		
		for(MobileUser t:mobileuser) {
			if(t.getTid()!=null) {
				tid.add(t.getTid());
			}
			if(t.getMotoTid()!=null) {
				motoTid.add(t.getMotoTid());
			}
			if(t.getEzywayTid()!=null) {
				ezywayTid.add(t.getEzywayTid());
			}
			if(t.getEzyrecTid()!=null) {
				ezyrecTid.add(t.getEzyrecTid());
			}
			if(t.getEzypassTid()!=null) {
				ezypassTid.add(t.getEzypassTid());
			}
		}

		
		String  err = (String) request.getSession(true).getAttribute("addNotiErSession");
		
		if(err!=null) {
		if(err.equalsIgnoreCase("Yes")) {
			
			logger.info("err::::::innnn" + err);
			model.addAttribute("responseErrorData", "Form cleared that contains HTML tags");
			request.getSession(true).removeAttribute("addNotiErSession");
		}
		}
		
		//request.getSession(true).removeAttribute("addNotificationSession");
		
		model.addAttribute("tidNo", tid.size());
		model.addAttribute("motoTidNo", motoTid.size());
		model.addAttribute("ezywayTidNo", ezywayTid.size());
		model.addAttribute("ezyrecTidNo", ezyrecTid.size());
		
		model.addAttribute("ezypassTidNo", ezypassTid.size());
		
		int size=ezypassTid.size()+ezywayTid.size()+ezyrecTid.size()+motoTid.size()+tid.size();
		logger.info("no of all tid: "+ezypassTid.size()+" "+ezywayTid.size()+" "+ezyrecTid.size()+" "+motoTid.size()+" "+tid.size());
		model.addAttribute("allTidNo", size);
		
		model.addAttribute("pageBean", pageBean);
		return TEMPLATE_DEFAULT;

	}

	
	
	@RequestMapping(value = { "/motoSubmit" }, method = RequestMethod.POST)
	public String motoSubmitTransaction(final Model model,
			@ModelAttribute("motoTxnDet") MotoTxnDet motoTxnDet,
			final java.security.Principal principal,
			HttpServletRequest request, HttpServletResponse response) {
		MotoTxnDet mototxn = new MotoTxnDet();
		mototxn.setAmount(motoTxnDet.getAmount());
		mototxn.setPhno(motoTxnDet.getPhno());
		mototxn.setContactName(motoTxnDet.getContactName());
		mototxn.setTid(motoTxnDet.getTid());
		mototxn.setMotoMid(motoTxnDet.getMotoMid());
		mototxn.setReferrence(motoTxnDet.getReferrence());
		mototxn.setExpectedDate(motoTxnDet.getExpectedDate());
		mototxn.setEmail(motoTxnDet.getEmail());
		motoTxnDet.setPhno(motoTxnDet.getPhno().replaceAll(" ", ""));
		if (motoTxnDet.getAmount().contains(".")) {
			motoTxnDet.setAmount(
					String.format("%012d", (long) Double.parseDouble(motoTxnDet.getAmount().replace(".", ""))));
		}else {
			motoTxnDet.setAmount(String.format("%012d", (long) Double.parseDouble(motoTxnDet.getAmount()) * 100));
		}
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Moto/MotoSuccess", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		ResponseDetails rd = null;
		try {
			rd = motoWebService.motoRequest(motoTxnDet);
			if (rd != null) {
				if(rd.getResponseMessage().equals("Connection refused")) {
					rd.setResponseMessage("Failed..Unable to Send SMS/Email to do Transaction Due to Connection Refused.");
				}
				//logger.info(" responseData: "+rd.getResponseData().getInvoiceId());
				rd.setResponseDescription(rd.getResponseData().getInvoiceId());

			}
		} catch (IllegalStateException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		List<CountryCurPhone> listCountry = transactionService.loadCountryCurrency();

		List<Country> countryList = new ArrayList<Country>();

		for (CountryCurPhone ccp : listCountry) {
			Country cty = new Country();
			cty.setCountryCode(ccp.getCountryIso3());
			cty.setCountryIso(ccp.getCountryIso2());
			cty.setCountryName(ccp.getCountryName());
			cty.setPhoneCode("+" + ccp.getCountryPhone());
			countryList.add(cty);
		}

		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMotoMid());
		List<MobileUser> mobileuser1 = new ArrayList<MobileUser>();

		for (TerminalDetails t : terminalDetails) {
			if (t.getTid() != null) {
				
				MobileUser m = transactionService.getMobileUserByMotoTid(t.getTid());
				if (m != null) {
					if (m.getMotoTid() != null) {

						logger.info("mobile username: " + m.getUsername() + m.getMotoTid());
						mobileuser1.add(m);

					}

				}
			}
		}
		request.setAttribute("rd", rd);
		model.addAttribute("rd", rd);
		model.addAttribute("motoTxnDet", mototxn);
		model.addAttribute("mobileuser", mobileuser1);
		model.addAttribute("listCountry", countryList);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/motoRecurTransaction" }, method = RequestMethod.GET)
	public String motoRecurTransaction(final Model model,
			final java.security.Principal principal, HttpServletRequest request) {

		HttpSession session = request.getSession();
		// logger.info("about to list all  transaction");
		String myName = (String) session.getAttribute("userName");
		logger.info("Recurring transaction");
        logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("tcurrently logged in user is: "
				+ currentMerchant.getUsername());
		PageBean pageBean = new PageBean("transactions list",
				"merchantweb/transaction/MotoRecurringTransaction",
				Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		List<CountryCurPhone> listCountry = transactionService
				.loadCountryCurrency();

		List<Country> countryList = new ArrayList<Country>();

		for (CountryCurPhone ccp : listCountry) {
			Country cty = new Country();
			cty.setCountryCode(ccp.getCountryIso3());
			cty.setCountryIso(ccp.getCountryIso2());
			cty.setCountryName(ccp.getCountryName());
			cty.setPhoneCode("+" + ccp.getCountryPhone());
			countryList.add(cty);
		}

		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMotoMid());
		List<MobileUser> mobileuser1 = new ArrayList<MobileUser>();

		for (TerminalDetails t : terminalDetails) {
			if (t.getTid() != null) {
				logger.info("moto tid: " + t.getTid());
				List<MobileUser> mobileuser = transactionService
						.getMobileUser(t.getTid());
				for (MobileUser m : mobileuser) {
					if (m.getMotoTid() != null) {

						logger.info("mobile username: " + m.getUsername()
								+ m.getMotoTid());
						mobileuser1.add(m);

					}

				}
			}
		}

		model.addAttribute("mobileuser", mobileuser1);
		model.addAttribute("listCountry", countryList);
		// model.addAttribute("terminalDetails", terminalDetails);

		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@SuppressWarnings("null")
	@RequestMapping(value = { "/motoRecurSubmitTransaction" }, method = RequestMethod.GET)
	public String motoRecurSubmitTransaction(final Model model,
			@ModelAttribute("motoTxnDet") MotoTxnDet motoTxnDet,
			final java.security.Principal principal,
			/*
			 * @RequestParam("contactName") final String contactName1,
			 * 
			 * @RequestParam("phno") String phno1,
			 * 
			 * @RequestParam("email") String email,
			 * 
			 * @RequestParam("amount") String amount1,
			 * 
			 * @RequestParam("referrence") String referrence,
			 * 
			 * @RequestParam("expectedDate") String expectedDate,
			 * 
			 * @RequestParam("tid") String tid
			 */
			HttpServletRequest request, HttpServletResponse response) {

		// logger.info("moto submit transaction");

		// logger.info("check params: " +
		// contactName1+" "+phno1+" "+amount1+" "+referrence+" "+tid+" "+expectedDate);

		/*
		 * logger.info("mid on submit: "+motoTxnDet.getMotoMid()+motoTxnDet.getTid
		 * ()+motoTxnDet.getAmount()+motoTxnDet.getContactName()
		 * +motoTxnDet.getEmail
		 * ()+motoTxnDet.getExpectedDate()+motoTxnDet.getPhno()+
		 * motoTxnDet.getReferrence());
		 */
		// logger.info("amount: "+amount1);
		// String contactName=contactName1;

		// String phno=phno1.replaceAll(" ", "");
		motoTxnDet.setPhno(motoTxnDet.getPhno().replaceAll(" ", ""));

		// double amt =

		// long amt1 = ;
		// String amount=;
		motoTxnDet.setAmount(String.format("%012d",
				(long) Double.parseDouble(motoTxnDet.getAmount()) * 100));

		// logger.info("check params: " +
		// contactName+" "+phno+" "+amount+" "+referrence+" "+tid);

		Merchant currentMerchant = merchantService.loadMerchant(principal
				.getName());
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());

		PageBean pageBean = new PageBean("transactions list",
				"merchantweb/transaction/motoTransaction",
				Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		MotoTxnDet mototxn = new MotoTxnDet();
		ResponseDetails rd = null;
		/*
		 * mototxn.setAmount(amount.toString());
		 * mototxn.setPhno(phno.toString());
		 * mototxn.setContactName(contactName); mototxn.setTid(tid);
		 * mototxn.setMotoMid(currentMerchant.getMid().getMotoMid());
		 * mototxn.setReferrence(referrence);
		 * mototxn.setExpectedDate(expectedDate); mototxn.setEmail(email);
		 */
		try {
			rd = motoWebService.motoRequest(motoTxnDet);
			if (rd != null) {
				// logger.info("success response..");
				logger.info("Response Data.."
						+ rd.getResponseData().getInvoiceId());
				request.setAttribute("responseData", rd.getResponseData()
						.getInvoiceId());
				request.setAttribute("responseData1", rd.getResponseData()
						.getOpt());
				request.setAttribute("responseSuccess", rd.getResponseMessage());
				model.addAttribute("responseSuccess", rd.getResponseMessage());
				model.addAttribute("responseData1", rd.getResponseData()
						.getOpt());
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<CountryCurPhone> listCountry = transactionService
				.loadCountryCurrency();

		List<Country> countryList = new ArrayList<Country>();

		for (CountryCurPhone ccp : listCountry) {
			Country cty = new Country();
			cty.setCountryCode(ccp.getCountryIso3());
			cty.setCountryIso(ccp.getCountryIso2());
			cty.setCountryName(ccp.getCountryName());
			cty.setPhoneCode("+" + ccp.getCountryPhone());
			countryList.add(cty);
		}

		List<TerminalDetails> terminalDetails = transactionService
				.getTerminalDetails(currentMerchant.getMid().getMotoMid());
		List<MobileUser> mobileuser1 = new ArrayList<MobileUser>();

		for (TerminalDetails t : terminalDetails) {
			if (t.getTid() != null) {
				logger.info("moto tid: " + t.getTid());
				List<MobileUser> mobileuser = transactionService
						.getMobileUser(t.getTid());
				for (MobileUser m : mobileuser) {
					if (m.getMotoTid() != null) {

						logger.info("mobile username: " + m.getUsername()
								+ m.getMotoTid());
						mobileuser1.add(m);

					}

				}
			}
		}

		model.addAttribute("mobileuser", mobileuser1);
		model.addAttribute("merchant", currentMerchant);

		model.addAttribute("listCountry", countryList);
		// model.addAttribute("terminalDetails", terminalDetails);
		model.addAttribute("pageBean", pageBean);
		request.setAttribute("responseData", rd.getResponseData()
				.getInvoiceId());
		model.addAttribute("loginname", principal.getName());

		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/recurringList" }, method = RequestMethod.GET)
	public String motoRecurList(final Model model,
			final java.security.Principal principal, HttpServletRequest request) {

		HttpSession session = request.getSession();
		// logger.info("about to list all  transaction");
		String myName = (String) session.getAttribute("userName");
		logger.info("Merchant: Recurring list");

		// logger.info("currrent page " + currPage);
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("tcurrently logged in user is: "
				+ currentMerchant.getUsername());
		PageBean pageBean = new PageBean("transactions list",
				"merchantweb/Recurring/RecurringUsersList", Module.MERCHANT,
				"merchantweb/transaction/sideMenuTransaction");
		PaginationBean<EzyRecurringPayment> paginationBean = new PaginationBean<EzyRecurringPayment>();
		// paginationBean.setCurrPage(currPage);
		merchantService.listRecurringMerchant(paginationBean, currentMerchant,null,null);

		logger.info("itemList: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {
			for (EzyRecurringPayment e : paginationBean.getItemList()) {
				try {

					if (e.getMaskedPan() != null) {

						String pan = e.getMaskedPan().substring(
								e.getMaskedPan().length() - 8);
						if (pan.contains("f")) {
							pan = pan.replaceAll("f", "X");

							e.setMaskedPan(pan);

						} else {

							e.setMaskedPan(pan);

						}

					} else {
						e.setMaskedPan("NA");
					}
					if (e.getExpDate() != null) {
					//	logger.info("exp date: "+e.getExpDate());
						String endDate=null;
						
						e.setExpDate(new SimpleDateFormat("MMM-yyyy")
						.format(new SimpleDateFormat("yyMM")
						.parse(e.getExpDate())));
					}
					if (e.getAmount() != null) {

						String output = new DecimalFormat("#,##0.00")
								.format(Double.parseDouble(e.getAmount()) / 100);

						e.setAmount(output);
					}
					if (e.getLastTriggerDate() != null) {

						e.setLastTriggerDate(new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy")
										.parse(e.getLastTriggerDate())));

					}
					if (e.getEndDate() != null) {

						e.setEndDate(new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy")
										.parse(e.getEndDate())));
					}
					if (e.getNextTriggerDate() != null) {

						e.setNextTriggerDate(new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy")
										.parse(e.getNextTriggerDate())));
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		model.addAttribute("paginationBean", paginationBean);

		// model.addAttribute("terminalDetails", terminalDetails);

		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}
	
	
	@RequestMapping(value = { "/editRecurringStatus/{recId}" }, method = RequestMethod.GET)
	public String motoEditRecurring(final Model model,
			final java.security.Principal principal,
			HttpServletRequest request, @PathVariable final long recId) {

		logger.info("Merchant: moto Edit Recurring  "
				+ principal.getName() + " RecId: " + recId);

		PageBean pageBean = new PageBean("Recurring list",
				"merchantweb/Recurring/editRecurringDetails", Module.MERCHANT,
				"merchant/sideMenuMerchant");

		PaginationBean<EzyRecurringPayment> paginationBean = new PaginationBean<EzyRecurringPayment>();
		// paginationBean.setCurrPage(currPage);

		EzyRecurringPayment ezyRec = merchantService
				.loadMerchantRecurring(recId);

		try {

			if (ezyRec.getMaskedPan() != null) {

				String pan = ezyRec.getMaskedPan().substring(
						ezyRec.getMaskedPan().length() - 8);
				if (pan.contains("f")) {
					pan = pan.replaceAll("f", "X");

					ezyRec.setMaskedPan(pan);

				} else {

					ezyRec.setMaskedPan(pan);

				}

			} else {
				ezyRec.setMaskedPan("NA");
			}

			if (ezyRec.getAmount() != null) {

				String output = new DecimalFormat("#,##0.00").format(Double
						.parseDouble(ezyRec.getAmount()) / 100);

				ezyRec.setAmount(output);
			}
			if (ezyRec.getStartDate() != null) {

				
				
				
				try {

					
					ezyRec.setStartDate(new SimpleDateFormat("dd-MMM-yyyy")
					.format(new SimpleDateFormat("dd/MM/yyyy").parse(ezyRec
							.getStartDate())));
			

				} catch (ParseException e1) {

					e1.printStackTrace();
				}

			}
			if (ezyRec.getExpDate() != null) {
				// logger.info("exp date: "+e.getExpDate());
				String endDate = null;

				
				
				try {

					
					ezyRec.setExpDate(new SimpleDateFormat("MMM-yyyy")
					.format(new SimpleDateFormat("yyMM").parse(ezyRec
							.getExpDate())));
			

				} catch (ParseException e1) {

					e1.printStackTrace();
				}

			}
			/*
			 * if (ezyRec.getEndDate() != null) { String endDate=null;
			 * endDate=new SimpleDateFormat("dd-MMM-yyyy") .format(new
			 * SimpleDateFormat("dd/mm/yyyy") .parse(ezyRec.getEndDate()));
			 * logger.info("start date: "+ezyRec.getEndDate());
			 * ezyRec.setEndDate(endDate);
			 * logger.info("start date: "+ezyRec.getEndDate()); }if
			 * (ezyRec.getStartDate() != null) {
			 * 
			 * String startDate=null; startDate=new
			 * SimpleDateFormat("dd-MMM-yyyy") .format(new
			 * SimpleDateFormat("dd/mm/yyyy") .parse(ezyRec.getStartDate()));
			 * startDate=new SimpleDateFormat("dd/mm/yyyy") .format(new
			 * SimpleDateFormat("dd-MMM-yyyy") .parse(ezyRec.getStartDate()));
			 * logger.info("start date: "+ezyRec.getStartDate());
			 * ezyRec.setStartDate(startDate);
			 * logger.info("start date: "+ezyRec.getStartDate()); }
			 */
			if (ezyRec.getCustName() != null) {
				// logger.info("customer Name: "+e.getCustName());
			} else {
				// logger.info("customer name: null");
			}

			if (ezyRec.getNextTriggerDate() != null) {

				try {

					ezyRec.setNextTriggerDate(new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("dd/MM/yyyy").parse(ezyRec.getNextTriggerDate())));

				} catch (ParseException e1) {

					e1.printStackTrace();
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		model.addAttribute("paginationBean", paginationBean);

		// model.addAttribute("terminalDetails", terminalDetails);

		// model.addAttribute("merchant", currentMerchant);
		model.addAttribute("ezyRec", ezyRec);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}
	
	@RequestMapping(value = { "/updateRecurringStatus" }, method = RequestMethod.POST)
	public String motoUpdateRecurring(final Model model,
			final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("id") final Long recId,
			@RequestParam("status") final String status,
			@RequestParam("mid") final String mid) {

		logger.info("Admin-Merchant: moto Update Recurring  " + principal.getName() + " RecId: " + recId);

		logger.info(mid + " : " + recId + " : " + status);
		PageBean pageBean = new PageBean("Recurring list", "merchantweb/Recurring/UpdateRecurSuccessful",
				Module.MERCHANT, "merchant/sideMenuMerchant");

		EzyRecurringPayment ezyRec = merchantService.loadMerchantRecurring(recId);
		ezyRec.setStatus(status);
		
		Date date =new Date();
		Calendar calendar = Calendar.getInstance();
         date =  calendar.getTime();
		int year=calendar.getWeekYear();
		long mon = date.getMonth()+1;
		int day=date.getDate();
		
		String fromDateToSearch=null;
		String toDateToSearch=null;
		String modifiedDate=year+"-"+mon+"-"+day;
		
		
		
		logger.info("modifiedDate: "+modifiedDate);
		try {
			modifiedDate = new SimpleDateFormat("yyyy/MM/dd")
					.format(new SimpleDateFormat("yyyy-MM-dd").parse(modifiedDate));
			

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		logger.info("modifiedDate: "+modifiedDate);
		ezyRec.setRemarks(principal.getName()+" on "+modifiedDate);
		ezyRec = merchantService.UpdateRecurringStatus(ezyRec);
		if (ezyRec.getMaskedPan() != null) {

			String pan = ezyRec.getMaskedPan().substring(ezyRec.getMaskedPan().length() - 8);
			if (pan.contains("f")) {
				pan = pan.replaceAll("f", "X");

				ezyRec.setMaskedPan(pan);

			} else {

				ezyRec.setMaskedPan(pan);

			}

		} else {
			ezyRec.setMaskedPan("NA");
		}
		logger.info("status: " + ezyRec.getStatus() + " card no: " + ezyRec.getMaskedPan());
		if (ezyRec!=null) {
			
			
			AuditTrail auditTrail = merchantService.updateAuditTrailByMerchant(principal.getName(), principal.getName(),
					"RecurringStatusUpdate");

			if (auditTrail != null) {
				logger.info("Recurring Status: " + status + " by Merchant : " + principal.getName());
				
			}
		}
		//model.addAttribute("paginationBean", paginationBean);

	//	model.addAttribute("mid", mid);
		ezyRec.setRemarks("Card No: "+ezyRec.getMaskedPan()+" "+status+" By "+principal.getName());
		model.addAttribute("ezyRec", ezyRec);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}
	
	@RequestMapping(value = { "/searchRecurringList" }, method = RequestMethod.GET)
	public String motoSearchRecurList(final Model model,
			final java.security.Principal principal, HttpServletRequest request,
			@RequestParam("date") String fromDate,
			@RequestParam("date1") String toDate) {

		HttpSession session = request.getSession();
		// logger.info("about to list all  transaction");
		String myName = (String) session.getAttribute("userName");
		logger.info("moto Search Recurring list");

		 logger.info("fromDate: "+fromDate+" toDate: " + toDate);
		 
		 try {
			 fromDate=new SimpleDateFormat("yyyy-mm-dd")
				.format(new SimpleDateFormat("dd/mm/yyyy")
				.parse(fromDate));
			 toDate=new SimpleDateFormat("yyyy-mm-dd")
				.format(new SimpleDateFormat("dd/mm/yyyy")
				.parse(toDate));
			 
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 
		 logger.info("fromDate: "+fromDate+" toDate: " + toDate);
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("tcurrently logged in user is: "
				+ currentMerchant.getUsername());
		PageBean pageBean = new PageBean("transactions list",
				"merchantweb/Recurring/RecurringUsersList", Module.MERCHANT,
				"merchantweb/transaction/sideMenuTransaction");
		PaginationBean<EzyRecurringPayment> paginationBean = new PaginationBean<EzyRecurringPayment>();
		// paginationBean.setCurrPage(currPage);
		merchantService.listRecurringMerchant(paginationBean, currentMerchant,fromDate,toDate);

		logger.info("itemList: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {
			for (EzyRecurringPayment e : paginationBean.getItemList()) {
				try {

					if (e.getMaskedPan() != null) {

						String pan = e.getMaskedPan().substring(
								e.getMaskedPan().length() - 8);
						if (pan.contains("f")) {
							pan = pan.replaceAll("f", "X");

							e.setMaskedPan(pan);

						} else {

							e.setMaskedPan(pan);

						}

					} else {
						e.setMaskedPan("NA");
					}

					if (e.getAmount() != null) {

						String output = new DecimalFormat("#,##0.00")
								.format(Double.parseDouble(e.getAmount()) / 100);

						e.setAmount(output);
					}
					if (e.getExpDate() != null) {
						//logger.info("exp date: "+e.getExpDate());
						String endDate=null;
						
						e.setExpDate(new SimpleDateFormat("MMM-yyyy")
						.format(new SimpleDateFormat("yyMM")
						.parse(e.getExpDate())));
					}
					if (e.getLastTriggerDate() != null) {

						e.setLastTriggerDate(new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy")
										.parse(e.getLastTriggerDate())));

					}
					if (e.getEndDate() != null) {

						e.setEndDate(new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy")
										.parse(e.getEndDate())));
					}
					if (e.getNextTriggerDate() != null) {

						e.setNextTriggerDate(new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy")
										.parse(e.getNextTriggerDate())));
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		model.addAttribute("paginationBean", paginationBean);

		// model.addAttribute("terminalDetails", terminalDetails);

		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/exportRecurringList" }, method = RequestMethod.GET)
	public ModelAndView motoExportRecurList(final Model model,
			final java.security.Principal principal, HttpServletRequest request,
			@RequestParam("date") String fromDate,
			@RequestParam("date1") String toDate,
			@RequestParam("export") String export) {

		HttpSession session = request.getSession();
		// logger.info("about to list all  transaction");
		String myName = (String) session.getAttribute("userName");
		logger.info("moto Search Recurring list");

		 logger.info("fromDate: "+fromDate+" toDate: " + toDate);
		 
		 try {
			 fromDate=new SimpleDateFormat("yyyy-mm-dd")
				.format(new SimpleDateFormat("dd/mm/yyyy")
				.parse(fromDate));
			 toDate=new SimpleDateFormat("yyyy-mm-dd")
				.format(new SimpleDateFormat("dd/mm/yyyy")
				.parse(toDate));
			 
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		 
		 logger.info("fromDate: "+fromDate+" toDate: " + toDate);
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("tcurrently logged in user is: "
				+ currentMerchant.getUsername());
		PageBean pageBean = new PageBean("transactions list",
				"merchantweb/Recurring/RecurringUsersList", Module.MERCHANT,
				"merchantweb/transaction/sideMenuTransaction");
		PaginationBean<EzyRecurringPayment> paginationBean = new PaginationBean<EzyRecurringPayment>();
		// paginationBean.setCurrPage(currPage);
		merchantService.listRecurringMerchant(paginationBean, currentMerchant,fromDate,toDate);

		logger.info("itemList: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {
			for (EzyRecurringPayment e : paginationBean.getItemList()) {
				try {

					if (e.getMaskedPan() != null) {

						String pan = e.getMaskedPan().substring(
								e.getMaskedPan().length() - 8);
						if (pan.contains("f")) {
							pan = pan.replaceAll("f", "X");

							e.setMaskedPan(pan);

						} else {

							e.setMaskedPan(pan);

						}

					} else {
						e.setMaskedPan("NA");
					}
					if (e.getExpDate() != null) {
						//logger.info("exp date: "+e.getExpDate());
						
						
						e.setExpDate(new SimpleDateFormat("MMM-yyyy")
						.format(new SimpleDateFormat("yyMM")
						.parse(e.getExpDate())));
					}
					if (e.getAmount() != null) {

						String output = new DecimalFormat("#,##0.00")
								.format(Double.parseDouble(e.getAmount()) / 100);

						e.setAmount(output);
					}
					if (e.getLastTriggerDate() != null) {

						e.setLastTriggerDate(new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy")
										.parse(e.getLastTriggerDate())));

					}
					if (e.getEndDate() != null) {

						e.setEndDate(new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy")
										.parse(e.getEndDate())));
					}
					if (e.getNextTriggerDate() != null) {

						e.setNextTriggerDate(new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy")
										.parse(e.getNextTriggerDate())));
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		

		List<EzyRecurringPayment> list1 = paginationBean.getItemList();

		

		if (!(export.equals("PDF"))) {
			
			return new ModelAndView("recurrMerchantExcel", "txnList", list1);
		} else {
			
			return new ModelAndView("recurrMerchantPdf", "txnList", list1);
		}
		
		
		

	}
	
	
	public boolean validateExpiryDate(String expDate){
		boolean validate=false;
		


		Date now = new Date();

		logger.info("Today : " + now + " : " + expDate);

		String resDate = null;
		try {

			resDate = new SimpleDateFormat("yyyy-MM-dd")
					.format(new SimpleDateFormat("yyyy-MM-dd")
							.parse(expDate));

			String resTime = new SimpleDateFormat("HH:mm:ss")
					.format(new SimpleDateFormat("HH:mm:ss")
							.parse(expDate.substring(11)));
			logger.info("After Date : " + resDate + " : " + resTime);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		LocalDate date1 = LocalDate.parse(resDate);
		LocalDate date2 = LocalDate.now();

		int days = Days.daysBetween(date2, date1).getDays();
		logger.info(" Days : " + days);

		if (days > 0) {

			
		
				validate = true;
			
		} else{
			logger.info(" Day Expired ");
			validate=false;
		}
		logger.info("status: "+validate);
		return validate;
		
	}
	
	@RequestMapping(value = { "/addNotification" }, method = RequestMethod.POST)
	public String processAddNotificationForm(
			@RequestParam("title") final String Title, @RequestParam("date") final String Date,
			@RequestParam("hour") final String Hour, @RequestParam("minute") final String Minute,
			@RequestParam("msg") final String Msg, @RequestParam("products") final String Products,
			final HttpServletRequest request, final Model model) {
		
		logger.info("About to add New Notification ");
		logger.info("Title:::::::::" + Title);
		logger.info("Action Date:::::::::" + Date);
		logger.info("Action Hour:::::::::" + Hour);
		logger.info("Action Minute:::::::::" + Minute);
		logger.info("Msg:::::::::" + Msg);
		logger.info("Products:::::::::" + Products);
		

		/*PageBean pageBean = new PageBean("Notification add Details",
					"notification/pushNotificationReview", Module.TRANSACTION_WEB,
					"agent/sideMenuAgent");
			model.addAttribute("responseData", null);

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("agent", agent);*/
//		ArrayList<String> proId = new ArrayList<String>();
		
		PushNotificationDetails preview = new PushNotificationDetails();
		
		preview.setDate(Date);
		preview.setHour(Hour);
		preview.setMinute(Minute);
		preview.setMsgTitle(Title);
		preview.setMsgDetail(Msg);
		preview.setProducts(Products);

		List<String> values = Arrays.asList(Products.split(","));
		
		System.out.println("values::::::"+values.size());
		
		int proLen = values.size();
		int x = 0;		

		if (values.get(0).equalsIgnoreCase("1")) {
			preview.setEzywire("yes");
		}
		if (values.get(1).equalsIgnoreCase("1")) {
			preview.setEzyMoto("yes");
		}
		if (values.get(2).equalsIgnoreCase("1")) {
			preview.setEzyRec("yes");
		}
		if (values.get(3).equalsIgnoreCase("1")) {
			preview.setGrabPay("yes");
		}
		/*if (values.get(4).equalsIgnoreCase("1")) {
			preview.setMobiPass("yes");
		}
		if (values.get(5).equalsIgnoreCase("1")) {
			preview.setEzyWay("yes");
		}
		if (values.get(6).equalsIgnoreCase("1")) {
			preview.setEzyPOD("yes");
		}
		if (values.get(7).equalsIgnoreCase("1")) {
			preview.setEzyAuth("yes");
		}
		if (values.get(8).equalsIgnoreCase("1")) {
			preview.setEzyPass("yes");
		}*/

		PageBean pageBean = null;
		
        BaseDataImpl  baseData = new BaseDataImpl();
		
        PushNotificationDetails  a =baseData.vaildated(preview);
		
		
		if(a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("addNotiErSession", "yes");

			return "redirect:/device/notificationReq";
		}
		
		

			// PCI
			request.getSession(true).setAttribute("addNotificationSession", preview);

			return "redirect:/device/NotificationDetailsReviewAndConfirm";
		
	}
	
	@RequestMapping(value = { "/NotificationDetailsReviewAndConfirm" }, method = RequestMethod.GET)
	public String displayAddAgentConfirmation(final ModelMap model,
			final HttpServletRequest request,
			final java.security.Principal principal)
	{

		logger.info("About to add Notification Details ReviewAndConfirm");
		PushNotificationDetails preview = (PushNotificationDetails) request.getSession(true).getAttribute(
				"addNotificationSession");

		
		logger.info("Title:::::::::" + preview.getMsgTitle());
		logger.info("Action Date:::::::::" + preview.getDate());
		logger.info("Action Hour:::::::::" + preview.getHour());
		logger.info("Action Minute:::::::::" + preview.getMinute());
		logger.info("Msg:::::::::" + preview.getMsgDetail());
		logger.info("Products:::::::::" + preview.getProducts());
		
		logger.info("Products:::::::::" + preview.getEzyAuth());
		
		
		if (preview == null) {
			// redirect user to the add page if there's no preview in session.

			return "redirect:" + URL_BASE + "/notificationReq";

		}

		PageBean pageBean = new PageBean("Agent user add Details",
				"notification/pushNotificationReview", Module.TRANSACTION_WEB,
				"agent/sideMenuAgent");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("preview", preview);
		// return "redirect:/agent1/agentDetailsReviewAndConfirm";
		return TEMPLATE_DEFAULT;
	}
	
	
	
	
	@RequestMapping(value = { "/uploadPN" }, method = RequestMethod.POST)
	public String uploadPN(@RequestParam("title") final String Title, @RequestParam("date") final String Date,
			@RequestParam("hour") final String Hour, @RequestParam("minute") final String Minute,
			@RequestParam("msg") final String Msg, @RequestParam("products") final String Products,
			final HttpServletRequest request, final Model model) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Date createdDate = new Date();
		String actionTime = Hour + ":" + Minute;
		logger.info("/uploadPN ~ To store push notification ");
		logger.info("Created By:::::::::" + myName);
		logger.info("Created Date:::::::::" + createdDate);
		logger.info("Title:::::::::" + Title);
		logger.info("Action Date:::::::::" + Date);
		logger.info("Action Time:::::::::" + actionTime);
		logger.info("Msg:::::::::" + Msg);
		logger.info("Products:::::::::" + Products);
		logger.info("Byte_Msg:::::::::" + Msg.getBytes());

		PageBean pageBean = new PageBean("transactions list", "notification/addNotificationSuccessfull",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		PushMessage push = new PushMessage();
		push.setMsgTitle(Title);
		push.setActionDate(Date);
		push.setActionTime(actionTime);
		push.setActivateDate(createdDate);
		push.setCreatedBy(myName);
		push.setCreatedDate(createdDate);
		push.setMsgDetails(Msg.getBytes());
		push.setMsgToPush(Products);
		push.setStatus(CommonStatus.SUBMITTED);
		
		
		 BaseDataImpl  baseData = new BaseDataImpl();
			
		 PushMessage  a =baseData.vaildated(push);
			
			
			if(a != null) {
				logger.info("Contains HTML tags");
				request.getSession(true).setAttribute("addNotiErSession", "yes");

				return "redirect:/device/notificationReq";
			}
		

		notificationService.addNotification(push);
		
		PushNotificationDetails preview = (PushNotificationDetails) request.getSession(true).getAttribute(
				"addNotificationSession");
		
		model.addAttribute("preview", preview);

		request.getSession(true).removeAttribute("addNotificationSession");
		return TEMPLATE_DEFAULT;
	}
	
	@RequestMapping(value = { "/notificationList" }, method = RequestMethod.GET)
	public String displayAllTransactionList(final Model model, @RequestParam(required = false) final String date,
			@RequestParam(required = false) final String date1, @RequestParam(required = false) final String status,
			@RequestParam(required = false, defaultValue = "1") final int currPage) {
		logger.info("all push notification list");
		logger.info("inside search controller " + date + " " + date1);
		String dat = null;
		String dat1 = null;
		String status1 = null;

		if ((date != null && date1 != null) && (!date.isEmpty() && !date1.isEmpty())) {
			try {
				dat = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date));
				dat1 = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(date1));
				logger.info("inside search controller " + dat + " " + dat1);
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}

		if (status != null) {
			status1 = status;
		}
		PageBean pageBean = new PageBean("transactions list", "notification/pushNotificationList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<PushMessage> paginationBean = new PaginationBean<PushMessage>();

		paginationBean.setCurrPage(currPage);

		notificationService.listAllNotificationsbyAdmin(paginationBean, dat, dat1, status1);

		model.addAttribute("date", dat);
		model.addAttribute("date1", dat1);
		model.addAttribute("status", status1);

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table response
		} else {
			model.addAttribute("responseData", null);
		}

		model.addAttribute("paginationBean", paginationBean);
		return TEMPLATE_DEFAULT;

	}	
	
	@RequestMapping(value = { "/details/{id}" }, method = RequestMethod.GET)
	public String displayNotification(final Model model,
			@PathVariable final String id, HttpServletRequest request,
			HttpServletResponse response, Principal principal) {

		PushMessage detail = notificationService.getNotification(id);

		logger.info(" Notification Preview:" + principal.getName());
		logger.info(" Notification Id:" + id);
		
		String msgDetail = new String(detail.getMsgDetails());
		CommonStatus status = detail.getStatus() ;
		
//		ArrayList<String> proId = new ArrayList<String>();
		
		PushNotificationDetails preview = new PushNotificationDetails();
		
		preview.setId(id);
		preview.setMsgTitle(detail.getMsgTitle());
		preview.setMsgDetail(msgDetail);
		preview.setDate(detail.getActionDate());
		preview.setHour(detail.getActionTime());
		if(detail.getStatus().equals(status.SUBMITTED)){
			preview.setStatus("SUBMITTED");
		}
		if(detail.getStatus().equals(status.APPROVED)){
			preview.setStatus("APPROVED");
		}
		if(detail.getStatus().equals(status.REJECTED)){
			preview.setStatus("REJECTED");
		}
		if(detail.getStatus().equals(status.SENT)){
			preview.setStatus("SENT");
		}
		
		
       List<String> values = Arrays.asList(detail.getMsgToPush().split(","));
		
		
		int proLen = values.size();
		int x = 0;		

		if (values.get(0).equalsIgnoreCase("1")) {
			preview.setEzywire("yes");
		}
		if (values.get(1).equalsIgnoreCase("1")) {
			preview.setEzyMoto("yes");
		}
		if (values.get(2).equalsIgnoreCase("1")) {
			preview.setEzyRec("yes");
		}
		if (values.get(3).equalsIgnoreCase("1")) {
			preview.setGrabPay("yes");
		}
		/*if (values.get(4).equalsIgnoreCase("1")) {
			preview.setMobiPass("yes");
		}
		if (values.get(5).equalsIgnoreCase("1")) {
			preview.setEzyWay("yes");
		}
		if (values.get(6).equalsIgnoreCase("1")) {
			preview.setEzyPOD("yes");
		}
		if (values.get(7).equalsIgnoreCase("1")) {
			preview.setEzyAuth("yes");
		}
		if (values.get(8).equalsIgnoreCase("1")) {
			preview.setEzyPass("yes");
		}*/
		
		PageBean pageBean = new PageBean("notification Details",
				"notification/notificationDetailsView", Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		request.setAttribute("preview", preview);
		
		model.addAttribute("pageBean", pageBean);
	
		return TEMPLATE_DEFAULT;
	}
	
	/*@RequestMapping(value = { "/edit/{id}" }, method = RequestMethod.GET)
	public String displayEditAgent(final Model model,
			@PathVariable final String id) {
		logger.info("Request to display notification edit");
		PageBean pageBean = new PageBean("notifiation Detail",
				"notification/edit/notificationEditDetails", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		PushMessage detail = notificationService.getNotification(id);
		
		
		String msgDetail = new String(detail.getMsgDetails());
		CommonStatus status = detail.getStatus() ;
		
//		ArrayList<String> proId = new ArrayList<String>();
		
		PushNotificationDetails preview = new PushNotificationDetails();
		
		preview.setId(id);
		preview.setMsgTitle(detail.getMsgTitle());
		preview.setMsgDetail(msgDetail);
		preview.setDate(detail.getActionDate());
		
		List<String> time = Arrays.asList(detail.getActionTime().split(":"));
		
		preview.setHour(time.get(0));
		preview.setMinute(time.get(1));
		if(detail.getStatus().equals(status.SUBMITTED)){
			preview.setStatus("SUBMITTED");
		}
		if(detail.getStatus().equals(status.APPROVED)){
			preview.setStatus("APPROVED");
		}
		if(detail.getStatus().equals(status.REJECTED)){
			preview.setStatus("REJECTED");
		}
		if(detail.getStatus().equals(status.SENT)){
			preview.setStatus("SENT");
		}
		
		
       List<String> values = Arrays.asList(detail.getMsgToPush().split(","));
		
		
		int proLen = values.size();
		int x = 0;		

		if (values.get(0).equalsIgnoreCase("1")) {
			preview.setEzywire("yes");
		}
		if (values.get(1).equalsIgnoreCase("1")) {
			preview.setEzyMoto("yes");
		}
		if (values.get(2).equalsIgnoreCase("1")) {
			preview.setEzyRec("yes");
		}
		if (values.get(3).equalsIgnoreCase("1")) {
			preview.setEzyPass("yes");
		}
		if (values.get(4).equalsIgnoreCase("1")) {
			preview.setMobiPass("yes");
		}
		if (values.get(5).equalsIgnoreCase("1")) {
			preview.setEzyWay("yes");
		}
		if (values.get(6).equalsIgnoreCase("1")) {
			preview.setEzyPOD("yes");
		}
		if (values.get(7).equalsIgnoreCase("1")) {
			preview.setEzyAuth("yes");
		}
		
		
		
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("detail", detail);
		model.addAttribute("preview", preview);
		
		return TEMPLATE_DEFAULT;
		// return "redirect:/agent1/edit/editagentDetail";
	}
	
	@RequestMapping(value = { "/editNotification" }, method = RequestMethod.POST)
	public String processEditNotificationForm(
			@RequestParam("title") final String Title, @RequestParam("date") final String Date,
			@RequestParam("hour") final String Hour, @RequestParam("minute") final String Minute,
			@RequestParam("msg") final String Msg, @RequestParam("products") final String Products,
			@RequestParam("id") final String id, @RequestParam("status") final String Status,
			final HttpServletRequest request, final Model model) {
		
		logger.info("About to edit  Notification ");
		logger.info("Notification id:::::::::" + id);
		logger.info("Title:::::::::" + Title);
		logger.info("Action Date:::::::::" + Date);
		logger.info("Action Hour:::::::::" + Hour);
		logger.info("Action Minute:::::::::" + Minute);
		logger.info("Msg:::::::::" + Msg);
		logger.info("Products:::::::::" + Products);
		logger.info("Status:::::::::" + Status);

		PageBean pageBean = new PageBean("Notification add Details",
					"notification/pushNotificationReview", Module.TRANSACTION_WEB,
					"agent/sideMenuAgent");
			model.addAttribute("responseData", null);

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("agent", agent);
//		ArrayList<String> proId = new ArrayList<String>();
		
		PushNotificationDetails preview = new PushNotificationDetails();
		preview.setId(id);
		preview.setDate(Date);
		preview.setHour(Hour);
		preview.setMinute(Minute);
		preview.setMsgTitle(Title);
		preview.setMsgDetail(Msg);
		preview.setProducts(Products);
		preview.setStatus(Status);

		List<String> values = Arrays.asList(Products.split(","));
		
		System.out.println("values::::::"+values.size());
		
		int proLen = values.size();
		int x = 0;		

		if (values.get(0).equalsIgnoreCase("1")) {
			preview.setEzywire("yes");
		}
		if (values.get(1).equalsIgnoreCase("1")) {
			preview.setEzyMoto("yes");
		}
		if (values.get(2).equalsIgnoreCase("1")) {
			preview.setEzyRec("yes");
		}
		if (values.get(3).equalsIgnoreCase("1")) {
			preview.setEzyPass("yes");
		}
		if (values.get(4).equalsIgnoreCase("1")) {
			preview.setMobiPass("yes");
		}
		if (values.get(5).equalsIgnoreCase("1")) {
			preview.setEzyWay("yes");
		}
		if (values.get(6).equalsIgnoreCase("1")) {
			preview.setEzyPOD("yes");
		}
		if (values.get(7).equalsIgnoreCase("1")) {
			preview.setEzyAuth("yes");
		}

		

			// PCI
			request.getSession(true).setAttribute("editNotificationSession", preview);

			return "redirect:/device/editNotificationDetailsReviewAndConfirm";
		
	}

	@RequestMapping(value = { "/editNotificationDetailsReviewAndConfirm" }, method = RequestMethod.GET)
	public String displayEditNotificationConfirmation(final ModelMap model,
			final HttpServletRequest request,
			final java.security.Principal principal)
	{

		logger.info("About to edit Notification Details ReviewAndConfirm");
		PushNotificationDetails preview = (PushNotificationDetails) request.getSession(true).getAttribute(
				"editNotificationSession");

		
		logger.info("Title:::::::::" + preview.getMsgTitle());
		logger.info("Action Date:::::::::" + preview.getDate());
		logger.info("Action Hour:::::::::" + preview.getHour());
		logger.info("Action Minute:::::::::" + preview.getMinute());
		logger.info("Msg:::::::::" + preview.getMsgDetail());
		logger.info("Products:::::::::" + preview.getProducts());
		
		logger.info("Products:::::::::" + preview.getEzyAuth());
		
		
		if (preview == null) {
			// redirect user to the add page if there's no preview in session.

			return "redirect:" + URL_BASE + "/notificationReq";

		}

		PageBean pageBean = new PageBean("notification edit Details",
				"notification/edit/notificationReviewandConfirm", Module.TRANSACTION_WEB,
				"agent/sideMenuAgent");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("preview", preview);
		// return "redirect:/agent1/agentDetailsReviewAndConfirm";
		return TEMPLATE_DEFAULT;
	}*/
	
	@RequestMapping(value = { "/editPN" }, method = RequestMethod.POST)
	public String editPN(
			@RequestParam("id") final String id, @RequestParam("status") final String status,
			 @RequestParam("remark") final String remark,
			final HttpServletRequest request, final Model model) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Date modifiedDate = new Date();
		logger.info("/editPN ~ To update push notification ");
		logger.info("Modified By:::::::::" + myName);
		logger.info("Modified Date:::::::::" + modifiedDate);

		/*PageBean pageBean = new PageBean("transactions list", "notification/pushNotificationList",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);*/

		PushMessage push = notificationService.getNotification(id);
		push.setModifiedBy(myName);
		push.setModifiedDate(modifiedDate);
		push.setRemarks(remark);
		
		
		if(status.equalsIgnoreCase("SUBMITTED")){
			push.setStatus(CommonStatus.SUBMITTED);
		}
		if(status.equalsIgnoreCase("APPROVED")){
			push.setStatus(CommonStatus.APPROVED);
		}
		if(status.equalsIgnoreCase("REJECTED")){
			push.setStatus(CommonStatus.REJECTED);
		}
		if(status.equalsIgnoreCase("SENT")){
			push.setStatus(CommonStatus.SENT);
		}
		
		
		BaseDataImpl  baseData = new BaseDataImpl();
		
		 PushMessage  a =baseData.vaildated(push);
			
			
			if(a != null) {
				logger.info("Contains HTML tags");
				request.getSession(true).setAttribute("addNotiErSession", "yes");

				return "redirect:/device/"+id+"";
			}
		
		notificationService.updateNotification(push);
		
		/*PushNotificationDetails preview = (PushNotificationDetails) request.getSession(true).getAttribute(
				"addNotificationSession");*/
		

		/*return TEMPLATE_DEFAULT;*/
		return "redirect:/device/notificationList";
	}
	
	
	

}
