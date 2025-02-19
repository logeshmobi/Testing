package com.mobiversa.payment.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mobiversa.common.bo.CountryCurPhone;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.dto.BaseDataImpl;
import com.mobiversa.payment.dto.Country;
import com.mobiversa.payment.dto.MotoTxnDet;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MotoWebService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.AESencrp;
import com.mobiversa.payment.util.ResponseDetails;

@Controller
@RequestMapping(value = MerchantDDTransactionController.URL_BASE)
public class MerchantDDTransactionController extends BaseController {

	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private MotoWebService motoWebService;

	public static final String URL_BASE = "/directDebit";
	private static final Logger logger = Logger
			.getLogger(MerchantDDTransactionController.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("Test1 defaultpage");
		return "redirect:" + URL_BASE + "/list/1";
	}

	

	@RequestMapping(value = { "/authDDTransaction" }, method = RequestMethod.GET)
	public String motoTransaction(final Model model,@ModelAttribute("motoTxnDet") MotoTxnDet motoTxnDet,
			final java.security.Principal principal, HttpServletRequest request) {

		logger.info("Auth Direct Debit");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");

		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		logger.info("Obtained User Name: "+ currentMerchant.getUsername());
		PageBean pageBean = new PageBean("transactions list",
				"merchantweb/DirectDebit/AuthDDTransaction",Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

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
		
		String hostType=null;
		
		logger.info("Merchant Type :" + currentMerchant.getMerchantType());

		if(currentMerchant.getMerchantType() == null) {
			hostType="P";
		}else {			
		
			if(currentMerchant.getMerchantType().isEmpty()) {
				hostType="P";
			}else {
				hostType=currentMerchant.getMerchantType();
			}

		}
		
		List<MobileUser> mobileuser1 = new ArrayList<MobileUser>();
		
		if(hostType.equals("P")) {
			
			logger.info("MOTO MID: "+currentMerchant.getMid().getMotoMid());
			List<TerminalDetails> terminalDetails = transactionService.getTerminalDetails(currentMerchant.getMid().getMotoMid());

	
			for (TerminalDetails t : terminalDetails) {
				if (t.getTid() != null) {
					
					MobileUser m = transactionService.getMobileUserByAuthTid(t.getTid());
					
					if (m!=null) {
						m.setFailedLoginAttempt(0);
						logger.info("MOTO TID : " + m.getMotoTid()+" expiry date: "+t.getSuspendedDate());
						boolean status=validateExpiryDate(t.getSuspendedDate().toString());
						if(status){
							
							m.setFailedLoginAttempt(1);
						}
						logger.info("Failure Attempt Status: "+m.getFailedLoginAttempt());
						if (m.getMotoTid() != null) {
	
							logger.info("MobileUser Name: " + m.getUsername() + "MobileUser TID: " + m.getMotoTid());
							mobileuser1.add(m);
	
						}
	
					}
					
				}
			}
			
			
		}
			
		if (hostType.equals("U")) {

			logger.info("UM MOTO MID: " + currentMerchant.getMid().getUmMotoMid());
			
			currentMerchant.getMid().setMotoMid(currentMerchant.getMid().getUmMotoMid());
			
			logger.info("MOTO MID::::::::: " + currentMerchant.getMid().getMotoMid());
			
			List<TerminalDetails> terminalDetails = transactionService
					.getTerminalDetails(currentMerchant.getMid().getUmMotoMid());

			for (TerminalDetails t : terminalDetails) {
//				 logger.info("moto tid ttttt: " + t.getTid()+" expiry date:"+t.getSuspendedDate());
				if (t.getTid() != null) {

					MobileUser m = transactionService.getMobileUserByAuthTid(t.getTid());

					if (m != null) {
						m.setFailedLoginAttempt(0);
						logger.info("UM MOTO TID: " + m.getMotoTid() + " expiry date: " + t.getSuspendedDate());
						boolean status = validateExpiryDate(t.getSuspendedDate().toString());
						if (status) {

							m.setFailedLoginAttempt(1);
						}
						logger.info("Failure Attempt Status: " + m.getFailedLoginAttempt());
						if (m.getMotoTid() != null) {

							logger.info("MobileUser Name: " + m.getUsername() + "MobileUser TID: " + m.getMotoTid());
							mobileuser1.add(m);

						}

					}

				}
			}

		}
		
		
		model.addAttribute("mobileuser", mobileuser1);
		model.addAttribute("listCountry", countryList);
		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("pageBean", pageBean);
		
		String  err = (String) request.getSession(true).getAttribute("editErSession");
		
		if(err!=null) {
		if(err.equalsIgnoreCase("Yes")) {
			
			logger.info("err::::::" + err);
			model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
			request.getSession(true).removeAttribute("editErSession");
		}
		}
		
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	
	
	@RequestMapping(value = { "/authDDSubmit" }, method = RequestMethod.POST)
	public String motoSubmitTransaction(final Model model,
			@ModelAttribute("motoTxnDet") MotoTxnDet motoTxnDet,
			final java.security.Principal principal,
			HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("/authDDSubmit");
		
		BaseDataImpl  baseData = new BaseDataImpl();
		
		MotoTxnDet  a =baseData.vaildated(motoTxnDet);
		logger.info("authDDSubmit con");
		
		if(a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");

			return "redirect:/directDebit/authDDTransaction";
		}
		
		logger.info("contactName::::::" + motoTxnDet.getContactName());
		logger.info("email::::::" + motoTxnDet.getEmail());
		logger.info("phno::::::" + motoTxnDet.getPhno());
		logger.info("receiptVia::::::" + motoTxnDet.getReceiptVia());
		logger.info("tid::::::" + motoTxnDet.getTid());
		logger.info("referrence::::::" + motoTxnDet.getReferrence());
		logger.info("amount::::::" + motoTxnDet.getAmount());
		logger.info("cardName::::::" + motoTxnDet.getcName());
		logger.info("cardNo::::::" + motoTxnDet.getcNumber());
		logger.info("exdt::::::" + motoTxnDet.getExpDate());
		logger.info("cvv::::::" + motoTxnDet.getCvv());
		logger.info("hostType::::::" + motoTxnDet.getHostType());
		logger.info("motoMid::::::" + motoTxnDet.getMotoMid());
		logger.info("latitude::::::" + motoTxnDet.getLatitude());
		logger.info("longitude::::::" + motoTxnDet.getLongitude());
		
		
		MotoTxnDet mototxn = new MotoTxnDet();
		
		mototxn.setContactName(motoTxnDet.getContactName());
		mototxn.setEmail(motoTxnDet.getEmail());
		mototxn.setPhno(motoTxnDet.getPhno());
		mototxn.setReceiptVia(motoTxnDet.getReceiptVia());
		mototxn.setTid(motoTxnDet.getTid());
		mototxn.setReferrence(motoTxnDet.getReferrence());
		mototxn.setAmount(motoTxnDet.getAmount());
		mototxn.setcName(motoTxnDet.getcName());
		mototxn.setcNumber(motoTxnDet.getcNumber().replaceAll("\\b(\\d{6})\\d+(\\d{4})", "$1XXXXXX$2"));
		mototxn.setExpectedDate(motoTxnDet.getExpectedDate());
		mototxn.setMotoMid(motoTxnDet.getMotoMid());
		mototxn.setHostType(motoTxnDet.getHostType());
		
		motoTxnDet.setPhno(motoTxnDet.getPhno().replaceAll(" ", ""));
		
		if (motoTxnDet.getAmount().contains(".")) {
			motoTxnDet.setAmount(String.format("%012d", (long) Double.parseDouble(motoTxnDet.getAmount().replace(".", ""))));
		}else {
			motoTxnDet.setAmount(String.format("%012d", (long) Double.parseDouble(motoTxnDet.getAmount()) * 100));
		}
		
		logger.info("Pharsed amount::::::" + motoTxnDet.getAmount());
		
		String cNumber = motoTxnDet.getcNumber().replaceAll("[\\s\\-()]", "");
		String extd = motoTxnDet.getExpDate().replaceAll("[\\s\\-()]", "");
		String cvv = motoTxnDet.getCvv().replaceAll("[\\s\\-()]", "");
		String mm = extd.substring(0, 2);
		String yy = extd.substring(Math.max(extd.length() - 2, 0));
		extd = yy+mm;
		
		logger.info("extd::::::" + extd);
		
		motoTxnDet.setCardDetails(cNumber+"#"+cvv+"#"+extd);
		
		logger.info("CardDetails::::::" + motoTxnDet.getCardDetails());
		
		String txnDetails =null;
		String tidTid =null;
		if (motoTxnDet.getTid() != null && !motoTxnDet.getTid().equals(" ")) {
			tidTid = motoTxnDet.getTid() + "00000000";
			tidTid = tidTid.replaceAll("\\s", ""); 
		}
		logger.info("tidTid " + tidTid);
		txnDetails = motoTxnDet.getCardDetails();
		txnDetails = txnDetails.replaceAll("\\s", "");
		logger.info("txnDetails " + txnDetails);
		try {
			txnDetails = AESencrp.encrypt(txnDetails,tidTid);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.info("Encrypted txnDetails " + txnDetails);
		txnDetails = AESencrp.hexaToAscii(txnDetails,false);
		
		motoTxnDet.setCardDetails(txnDetails);
		
		
		PageBean pageBean = new PageBean("transactions list", "merchantweb/DirectDebit/AuthSuccess", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		ResponseDetails rd = null;
		try {
			rd = motoWebService.authDDRequest(motoTxnDet);
			if (rd != null) {
				if(rd.getResponseMessage().equals("Connection refused")) {
					rd.setResponseMessage("Failed..Unable to Send SMS/Email to do Transaction Due to Connection Refused.");
				}
				//logger.info(" responseData: "+rd.getResponseData().getInvoiceId());
				//rd.setResponseDescription(rd.getResponseDescription());

			}
		} catch (IllegalStateException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		logger.info("Description:::::: " + rd.getResponseDescription());
		request.setAttribute("rd", rd);
		model.addAttribute("rd", rd);
		model.addAttribute("motoTxnDet", mototxn);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

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
	
}
