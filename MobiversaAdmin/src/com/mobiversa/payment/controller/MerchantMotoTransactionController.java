package com.mobiversa.payment.controller;

import java.io.IOException;
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
import com.mobiversa.common.bo.ForSettlement;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.MobileUser;
import com.mobiversa.common.bo.MotoVCDetails;
import com.mobiversa.common.bo.TerminalDetails;
import com.mobiversa.payment.connect.MotoPaymentCommunication;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.BaseDataImpl;
import com.mobiversa.payment.dto.Country;
import com.mobiversa.payment.dto.MotoTxnDet;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.MotoWebService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.AESencrp;
import com.mobiversa.payment.util.EncryptCard;
import com.mobiversa.payment.util.ResponseDetails;

@Controller
@RequestMapping(value = MerchantMotoTransactionController.URL_BASE)
public class MerchantMotoTransactionController extends BaseController {

	@Autowired
	private MerchantService merchantService;
	@Autowired
	private TransactionService transactionService;

	@Autowired
	private MotoWebService motoWebService;
	
	

	public static final String URL_BASE = "/transactionMoto";
	private static final Logger logger = Logger.getLogger(MerchantMotoTransactionController.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		logger.info("Test1 defaultpage");
		return "redirect:" + URL_BASE + "/list/1";
	}

	@RequestMapping(value = { "/motoTransaction" }, method = RequestMethod.GET)
	public String motoTransaction(final Model model, @ModelAttribute("motoTxnDet") MotoTxnDet motoTxnDet,
			final java.security.Principal principal, HttpServletRequest request) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		logger.info("moto transaction");

		// logger.info("currrent page " + currPage);
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("tcurrently logged in user is: " + currentMerchant.getUsername());
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Moto/MotoTransaction",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

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

		String hostType = null;

		logger.info("currentMerchant.getMerchantType() " + currentMerchant.getMerchantType());

		if (currentMerchant.getMerchantType() == null) {
			hostType = "P";
		} else {

			if (currentMerchant.getMerchantType().isEmpty()) {
				hostType = "P";
			} else {
				hostType = currentMerchant.getMerchantType();
			}

		}

		List<MobileUser> mobileuser1 = new ArrayList<MobileUser>();

		if (hostType.equals("P")) {

			logger.info("moto mid: " + currentMerchant.getMid().getMotoMid());
			List<TerminalDetails> terminalDetails = transactionService
					.getTerminalDetails(currentMerchant.getMid().getMotoMid());

			for (TerminalDetails t : terminalDetails) {
				// logger.info("moto tid ttttt: " + t.getTid()+" expiry date:
				// "+t.getSuspendedDate());
				if (t.getTid() != null) {

					MobileUser m = transactionService.getMobileUserByMotoTid(t.getTid());

					if (m != null) {
						m.setFailedLoginAttempt(0);
						logger.info("moto tid mmm: " + m.getMotoTid() + " expiry date: " + t.getSuspendedDate());
						boolean status = validateExpiryDate(t.getSuspendedDate().toString());
						if (status) {

							m.setFailedLoginAttempt(1);
						}
						logger.info("status: " + m.getFailedLoginAttempt());
						if (m.getMotoTid() != null) {

							logger.info("mobile username: " + m.getUsername() + m.getMotoTid());
							mobileuser1.add(m);

						}

					}

				}
			}

		}

		if (hostType.equals("U")) {

			logger.info("UM moto mid: " + currentMerchant.getMid().getUmMotoMid());
			List<TerminalDetails> terminalDetails = transactionService
					.getTerminalDetails(currentMerchant.getMid().getUmMotoMid());
			// List<MobileUser> mobileuser1 = new ArrayList<MobileUser>();

			for (TerminalDetails t : terminalDetails) {
				// logger.info("moto tid ttttt: " + t.getTid()+" expiry date:
				// "+t.getSuspendedDate());
				if (t.getTid() != null) {

					MobileUser m = transactionService.getMobileUserByMotoTid(t.getTid());

					if (m != null) {
						m.setFailedLoginAttempt(0);
						logger.info("Um moto tid mmm: " + m.getMotoTid() + " expiry date: " + t.getSuspendedDate());
						boolean status = validateExpiryDate(t.getSuspendedDate().toString());
						if (status) {

							m.setFailedLoginAttempt(1);
						}
						logger.info("status: " + m.getFailedLoginAttempt());
						if (m.getMotoTid() != null) {

							logger.info("mobile username: " + m.getUsername() + m.getMotoTid());
							mobileuser1.add(m);

						}

					}

				}
			}

		}

		String err = (String) request.getSession(true).getAttribute("editErSession");

		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {

				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("editErSession");
			}
		}

		// model.addAttribute("mobileuser", motoTxnDet);
		model.addAttribute("mobileuser", mobileuser1);
		model.addAttribute("listCountry", countryList);
		// model.addAttribute("terminalDetails", terminalDetails);

		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/motoLinkTransaction" }, method = RequestMethod.GET)
	public String motoLinkTransaction(final Model model, @ModelAttribute("motoTxnDet") MotoTxnDet motoTxnDet,
			final java.security.Principal principal, HttpServletRequest request) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		logger.info("moto link  transaction");

		// logger.info("currrent page " + currPage);
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("tcurrently logged in user is: " + currentMerchant.getUsername());
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Moto/MotoLinkTransaction",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

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

		String hostType = null;

		logger.info("currentMerchant.getMerchantType() " + currentMerchant.getMerchantType());

		if (currentMerchant.getMerchantType() == null) {
			hostType = "P";
		} else {

			if (currentMerchant.getMerchantType().isEmpty()) {
				hostType = "P";
			} else {
				hostType = currentMerchant.getMerchantType();
			}

		}

		List<MobileUser> mobileuser1 = new ArrayList<MobileUser>();

		if (hostType.equals("P")) {

			logger.info("moto mid: " + currentMerchant.getMid().getMotoMid());
			List<TerminalDetails> terminalDetails = transactionService
					.getTerminalDetails(currentMerchant.getMid().getMotoMid());

			for (TerminalDetails t : terminalDetails) {
				// logger.info("moto tid ttttt: " + t.getTid()+" expiry date:
				// "+t.getSuspendedDate());
				if (t.getTid() != null) {

					MobileUser m = transactionService.getMobileUserByMotoTid(t.getTid());

					if (m != null) {
						m.setFailedLoginAttempt(0);
						logger.info("moto tid mmm: " + m.getMotoTid() + " expiry date: " + t.getSuspendedDate());
						boolean status = validateExpiryDate(t.getSuspendedDate().toString());
						if (status) {

							m.setFailedLoginAttempt(1);
						}
						logger.info("status: " + m.getFailedLoginAttempt());
						if (m.getMotoTid() != null) {

							logger.info("mobile username: " + m.getUsername() + m.getMotoTid());
							mobileuser1.add(m);

						}

					}

				}
			}

		}

		if (hostType.equals("U")) {

			logger.info("UM moto mid: " + currentMerchant.getMid().getUmMotoMid());
			List<TerminalDetails> terminalDetails = transactionService
					.getTerminalDetails(currentMerchant.getMid().getUmMotoMid());
			// List<MobileUser> mobileuser1 = new ArrayList<MobileUser>();

			for (TerminalDetails t : terminalDetails) {
				// logger.info("moto tid ttttt: " + t.getTid()+" expiry date:
				// "+t.getSuspendedDate());
				if (t.getTid() != null) {

					MobileUser m = transactionService.getMobileUserByMotoTid(t.getTid());

					if (m != null) {
						m.setFailedLoginAttempt(0);
						logger.info("Um moto tid mmm: " + m.getMotoTid() + " expiry date: " + t.getSuspendedDate());
						boolean status = validateExpiryDate(t.getSuspendedDate().toString());
						if (status) {

							m.setFailedLoginAttempt(1);
						}
						logger.info("status: " + m.getFailedLoginAttempt());
						if (m.getMotoTid() != null) {

							logger.info("mobile username: " + m.getUsername() + m.getMotoTid());
							mobileuser1.add(m);

						}

					}

				}
			}

		}

		String err = (String) request.getSession(true).getAttribute("editErSession");

		if (err != null) {
			if (err.equalsIgnoreCase("Yes")) {

				logger.info("err::::::" + err);
				model.addAttribute("responseErrorData", "Form refreshed that contains HTML tags");
				request.getSession(true).removeAttribute("editErSession");
			}
		}

		// model.addAttribute("mobileuser", motoTxnDet);
		model.addAttribute("mobileuser", mobileuser1);
		model.addAttribute("listCountry", countryList);
		// model.addAttribute("terminalDetails", terminalDetails);

		model.addAttribute("merchant", currentMerchant);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/motoSubmit" }, method = RequestMethod.POST)
	public String motoSubmitTransaction(final Model model, @ModelAttribute("motoTxnDet") MotoTxnDet motoTxnDet,
			final java.security.Principal principal, HttpServletRequest request, HttpServletResponse response) {

		logger.info("/motoSubmit");
		
		
		logger.info("amount " + motoTxnDet.getAmount()+" reference " + motoTxnDet.getReferrence()
//		+" cvv "+motoTxnDet.getCvvno()
		+ " name on card "+motoTxnDet.getNameoncard()
//		+" card no "+motoTxnDet.getCardno()
		+" exp date "+motoTxnDet.getExpDate());
		
		
		logger.info("email :" + motoTxnDet.getEmail());
		String expdate = motoTxnDet.getExpDate();
		expdate=  expdate.replaceAll(" ", "");
		expdate= expdate.replaceAll("/", "");
		//expdate=expdate.substring(2,4)+expdate.substring(0,2);
		
		logger.info("exp date after changing format  "+expdate);
		
		motoTxnDet.setExpDate(expdate);
		
		String cardno = motoTxnDet.getCardno();
		cardno = cardno.replaceAll(" ", "");
//		logger.info("card no after changing format  "+cardno);
		motoTxnDet.setCardno(cardno);
		
		
		//in this no need to consider about this object mototxn  - old code - need to check the params of motoTxnDet obj is enough
		MotoTxnDet mototxn = new MotoTxnDet();
		mototxn.setAmount(motoTxnDet.getAmount());
		mototxn.setContactName(motoTxnDet.getContactName());
		// mototxn.setTid(motoTxnDet.getTid());
		// mototxn.setMotoMid(motoTxnDet.getMotoMid());
		mototxn.setReferrence(motoTxnDet.getReferrence());
		mototxn.setExpectedDate(motoTxnDet.getExpectedDate());
		mototxn.setEmail(motoTxnDet.getEmail());

		motoTxnDet.setWhatsapp(motoTxnDet.getWhatsapp());
		logger.info("MultiOption :" + motoTxnDet.getMultiOption());
		motoTxnDet.setMultiOption(motoTxnDet.getMultiOption());

		if (motoTxnDet.getPhno() != null && !motoTxnDet.getPhno().isEmpty()) {
			String phn = motoTxnDet.getPhncode1() + motoTxnDet.getPhno();
			logger.info("Entire Phone Number " + phn);
			mototxn.setPhno(phn);
			logger.info("Entire Phone Number " + mototxn.getPhno() + " " + mototxn.getPhno().replaceAll(" ", ""));
			motoTxnDet.setPhno(mototxn.getPhno().replaceAll(" ", ""));
		}

		TerminalDetails data = null;

		if (motoTxnDet.getAmount().contains(".")) {
			motoTxnDet.setAmount(
					String.format("%012d", (long) Double.parseDouble(motoTxnDet.getAmount().replace(".", ""))));
		} else {
			motoTxnDet.setAmount(String.format("%012d", (long) Double.parseDouble(motoTxnDet.getAmount()) * 100));
		}
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMerchantType() == null) {
			mototxn.setHostType("P");
			motoTxnDet.setHostType("P");
			mototxn.setMotoMid(currentMerchant.getMid().getMotoMid());
			motoTxnDet.setMotoMid(currentMerchant.getMid().getMotoMid());
		} else {
			if (currentMerchant.getMerchantType().isEmpty()) {
				mototxn.setHostType("P");
				motoTxnDet.setHostType("P");
				mototxn.setMotoMid(currentMerchant.getMid().getMotoMid());
				motoTxnDet.setMotoMid(currentMerchant.getMid().getMotoMid());
			} else {
				mototxn.setHostType(currentMerchant.getMerchantType());
				motoTxnDet.setHostType(currentMerchant.getMerchantType());
				if (currentMerchant.getMerchantType().equals("P")) {
					mototxn.setMotoMid(currentMerchant.getMid().getMotoMid());
					motoTxnDet.setMotoMid(currentMerchant.getMid().getMotoMid());
				}else if(currentMerchant.getMerchantType().equals("FIUU")) {
					mototxn.setMotoMid(currentMerchant.getMid().getFiuuMid());
					motoTxnDet.setMotoMid(currentMerchant.getMid().getFiuuMid());
				}else {
					mototxn.setMotoMid(currentMerchant.getMid().getUmMotoMid());
					motoTxnDet.setMotoMid(currentMerchant.getMid().getUmMotoMid());
				}

			}
		}

		logger.info("MID : " + motoTxnDet.getMotoMid());
		// Load Tid by Mid
		try {
			data = merchantService.loadTerminalDetailsByMid(motoTxnDet.getMotoMid());
			motoTxnDet.setTid(data.getTid());
		} catch (Exception e) {

			logger.info("Not Found TID For this MID "+e);

		}
		logger.info("TID : " + motoTxnDet.getTid());
		
		
		
		
		String carddetails = EncryptCard.EncryptCardDet(motoTxnDet);
		
		logger.info("card details "+carddetails);
		
		motoTxnDet.setCardDetails(carddetails);
		logger.info("card details after setting "+carddetails);

		BaseDataImpl baseData = new BaseDataImpl();

		MotoTxnDet a = baseData.vaildated(motoTxnDet);

		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");

			return "redirect:/transactionUmweb/Transaction";
		}

		logger.info("moto watsapp :" + motoTxnDet.getWhatsapp());
		
		logger.info("before sending request values are  mid "+motoTxnDet.getMotoMid()+"  tid  "+motoTxnDet.getTid()+"  carddetails "+motoTxnDet.getCardDetails()+" cvv "+motoTxnDet.getCvvno()
		+" expdate "+motoTxnDet.getExpDate()+"  nameoncard "+motoTxnDet.getNameoncard()+" amount " + motoTxnDet.getAmount()+" reference " + motoTxnDet.getReferrence());

		

		PageBean pageBean = new PageBean("transactions list", "merchantweb/Moto/MotoPaymentSuccess", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		ResponseDetails rd = null;
		logger.info("merchant type is "+currentMerchant.getMerchantType());
		try {
			if(currentMerchant.getMerchantType() == null || currentMerchant.getMerchantType().isEmpty() || currentMerchant.getMerchantType().equalsIgnoreCase("P"))
			{
				logger.info("inside paydee");
				rd = motoWebService.paydeemotopaymentRequest(motoTxnDet);
			}
			else if(currentMerchant.getMerchantType().equalsIgnoreCase("FIUU")) {
				logger.info("inside fiuu");
				rd = motoWebService.fiuuMotoPaymentRequest(motoTxnDet);
			}else {	
				logger.info("inside u-mobile");
				rd = motoWebService.umobilemotopaymentRequest(motoTxnDet);	
			}
				
				
			if (rd != null) {
				if (rd.getResponseMessage().equals("Connection refused")) {
					rd.setResponseDescription(
							"Failed..Unable to Send SMS/Email to do Transaction Due to Connection Refused.");
				}
				
			}
		} catch (IllegalStateException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		model.addAttribute("Success", rd.getResponseDescription());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}
	
	//motoviapreauth-rk
	
	@RequestMapping(value = { "/authviamotoSubmit" }, method = RequestMethod.POST)
	public String preauthviamotoSubmitTransaction(final Model model, @ModelAttribute("motoTxnDet") MotoTxnDet motoTxnDet,
			final java.security.Principal principal, HttpServletRequest request, HttpServletResponse response) {

		logger.info("/authviamotoSubmit");
		
		
		logger.info("amount " + motoTxnDet.getAmount()+" reference " + motoTxnDet.getReferrence()
//		+" cvv "+motoTxnDet.getCvvno()
		+ " name on card "+motoTxnDet.getNameoncard()
//		+" card no "+motoTxnDet.getCardno()
		+" exp date "+motoTxnDet.getExpDate());
		
		
		logger.info("email :" + motoTxnDet.getEmail());
		String expdate = motoTxnDet.getExpDate();
		expdate=  expdate.replaceAll(" ", "");
		expdate= expdate.replaceAll("/", "");
		//expdate=expdate.substring(2,4)+expdate.substring(0,2);
		
		logger.info("exp date after changing format  "+expdate);
		
		motoTxnDet.setExpDate(expdate);
		
		String cardno = motoTxnDet.getCardno();
		cardno = cardno.replaceAll(" ", "");
//		logger.info("card no after changing format  "+cardno);
		motoTxnDet.setCardno(cardno);
		
		
		//in this no need to consider about this object mototxn  - old code - need to check the params of motoTxnDet obj is enough
		MotoTxnDet mototxn = new MotoTxnDet();
		mototxn.setAmount(motoTxnDet.getAmount());
		mototxn.setContactName(motoTxnDet.getContactName());
		// mototxn.setTid(motoTxnDet.getTid());
		// mototxn.setMotoMid(motoTxnDet.getMotoMid());
		mototxn.setReferrence(motoTxnDet.getReferrence());
		mototxn.setExpectedDate(motoTxnDet.getExpectedDate());
		mototxn.setEmail(motoTxnDet.getEmail());

		motoTxnDet.setWhatsapp(motoTxnDet.getWhatsapp());
		logger.info("MultiOption :" + motoTxnDet.getMultiOption());
		motoTxnDet.setMultiOption(motoTxnDet.getMultiOption());

		if (motoTxnDet.getPhno() != null && !motoTxnDet.getPhno().isEmpty()) {
			String phn = motoTxnDet.getPhncode1() + motoTxnDet.getPhno();
			logger.info("Entire Phone Number " + phn);
			mototxn.setPhno(phn);
			logger.info("Entire Phone Number " + mototxn.getPhno() + " " + mototxn.getPhno().replaceAll(" ", ""));
			motoTxnDet.setPhno(mototxn.getPhno().replaceAll(" ", ""));
		}

		TerminalDetails data = null;

		if (motoTxnDet.getAmount().contains(".")) {
			motoTxnDet.setAmount(
					String.format("%012d", (long) Double.parseDouble(motoTxnDet.getAmount().replace(".", ""))));
		} else {
			motoTxnDet.setAmount(String.format("%012d", (long) Double.parseDouble(motoTxnDet.getAmount()) * 100));
		}
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		if (currentMerchant.getMerchantType() == null) {
			mototxn.setHostType("P");
			motoTxnDet.setHostType("P");
			mototxn.setMotoMid(currentMerchant.getMid().getMotoMid());
			motoTxnDet.setMotoMid(currentMerchant.getMid().getMotoMid());
		} else {
			if (currentMerchant.getMerchantType().isEmpty()) {
				mototxn.setHostType("P");
				motoTxnDet.setHostType("P");
				mototxn.setMotoMid(currentMerchant.getMid().getMotoMid());
				motoTxnDet.setMotoMid(currentMerchant.getMid().getMotoMid());
			} else {
				mototxn.setHostType(currentMerchant.getMerchantType());
				motoTxnDet.setHostType(currentMerchant.getMerchantType());
				if (currentMerchant.getMerchantType().equals("P")) {
					mototxn.setMotoMid(currentMerchant.getMid().getMotoMid());
					motoTxnDet.setMotoMid(currentMerchant.getMid().getMotoMid());
				}else if (currentMerchant.getMerchantType().equals("FIUU")) {
					mototxn.setMotoMid(currentMerchant.getMid().getFiuuMid());
					motoTxnDet.setMotoMid(currentMerchant.getMid().getFiuuMid());
				} else {
					mototxn.setMotoMid(currentMerchant.getMid().getUmMotoMid());
					motoTxnDet.setMotoMid(currentMerchant.getMid().getUmMotoMid());
				}

			}
		}

		logger.info("MID : " + motoTxnDet.getMotoMid());
		// Load Tid by Mid
		try {
			data = merchantService.loadTerminalDetailsByMid(motoTxnDet.getMotoMid());
			motoTxnDet.setTid(data.getTid());
		} catch (Exception e) {

			logger.info("Not Found TID For this MID "+e);

		}
		logger.info("TID : " + motoTxnDet.getTid());
		
		
		
		
		String carddetails = EncryptCard.EncryptCardDet(motoTxnDet);
		
		logger.info("card details "+carddetails);
		
		motoTxnDet.setCardDetails(carddetails);
		logger.info("card details after setting "+carddetails);

		BaseDataImpl baseData = new BaseDataImpl();

		MotoTxnDet a = baseData.vaildated(motoTxnDet);

		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");

			return "redirect:/transactionUmweb/Transaction";
		}

		logger.info("moto watsapp :" + motoTxnDet.getWhatsapp());
		
		logger.info("before sending request values are  mid "+motoTxnDet.getMotoMid()+"  tid  "+motoTxnDet.getTid()+"  carddetails "+motoTxnDet.getCardDetails()+" cvv "+motoTxnDet.getCvvno()
		+" expdate "+motoTxnDet.getExpDate()+"  nameoncard "+motoTxnDet.getNameoncard()+" amount " + motoTxnDet.getAmount()+" reference " + motoTxnDet.getReferrence());

		

		PageBean pageBean = new PageBean("transactions list", "merchantweb/Moto/AuthviaMotoPaymentSuccess", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		ResponseDetails rd = null;
		logger.info("merchant type is "+currentMerchant.getMerchantType());
		try {
			if(currentMerchant.getMerchantType() == null || currentMerchant.getMerchantType().isEmpty() || currentMerchant.getMerchantType().equalsIgnoreCase("P"))
			{
				logger.info("inside paydee");
				rd = motoWebService.paydeeauthviamotopaymentRequest(motoTxnDet);
				
			}
			else if(currentMerchant.getMerchantType().equalsIgnoreCase("U")) {
				
				logger.info("inside u-mobile");
				rd = motoWebService.umobileauthviamotopaymentRequest(motoTxnDet);				
			}
			else if(currentMerchant.getMerchantType().equalsIgnoreCase("FIUU")) {
				
				logger.info("inside fiuu ");
				rd = motoWebService.fiuuAuthViaMotoPaymentRequest(motoTxnDet);				
			}
				
				
			if (rd != null) {
				if (rd.getResponseMessage().equals("Connection refused")) {
					rd.setResponseDescription(
							"Failed..Unable to Send SMS/Email to do Transaction Due to Connection Refused.");
				}
				
			}
		} catch (IllegalStateException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}

		model.addAttribute("Success", rd.getResponseDescription());
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/motoLinkSubmit" }, method = RequestMethod.POST)
	public String motoLinkSubmit(final Model model, @ModelAttribute("motoTxnDet") MotoTxnDet motoTxnDet,
			final java.security.Principal principal, HttpServletRequest request, HttpServletResponse response) {

		logger.info("/motoLinkSubmit");

		logger.info("phncode " + motoTxnDet.getPhncode1());
		logger.info("amount " + motoTxnDet.getAmount());
		logger.info("phnno" + motoTxnDet.getPhno());
		// logger.info("tid " + motoTxnDet.getTid());
		logger.info("reference " + motoTxnDet.getReferrence());
		logger.info("MultiOption :" + motoTxnDet.getMultiOption());
		logger.info("email :" + motoTxnDet.getEmail());
		logger.info("latitude :" + motoTxnDet.getLatitude());
		logger.info("latitude :" + motoTxnDet.getLongitude());

		BaseDataImpl baseData = new BaseDataImpl();

		MotoTxnDet a = baseData.vaildated(motoTxnDet);

		if (a != null) {
			logger.info("Contains HTML tags");
			request.getSession(true).setAttribute("editErSession", "yes");

			return "redirect:/transactionUmweb/Transaction";
		}

		logger.info("moto watsapp :" + motoTxnDet.getWhatsapp());

		MotoTxnDet mototxn = new MotoTxnDet();
		mototxn.setAmount(motoTxnDet.getAmount());
		if (motoTxnDet.getPhno() != null && !motoTxnDet.getPhno().isEmpty()) {
			String phn = motoTxnDet.getPhncode1() + motoTxnDet.getPhno();
			logger.info("Entire Phone Number " + phn);
			mototxn.setPhno(phn);
			logger.info("Entire Phone Number " + mototxn.getPhno() + " " + mototxn.getPhno().replaceAll(" ", ""));
			motoTxnDet.setPhno(mototxn.getPhno().replaceAll(" ", ""));
		}
		// mototxn.setContactName(motoTxnDet.getContactName());
		// mototxn.setTid(motoTxnDet.getTid());
		// mototxn.setMotoMid(motoTxnDet.getMotoMid());
		mototxn.setReferrence(motoTxnDet.getReferrence());
		// mototxn.setExpectedDate(motoTxnDet.getExpectedDate());

		mototxn.setEmail(motoTxnDet.getEmail());

		// motoTxnDet.setWhatsapp(motoTxnDet.getWhatsapp());

		if (motoTxnDet.getMultiOption() != null && motoTxnDet.getMultiOption().equalsIgnoreCase("yes")) {
			motoTxnDet.setMultiOption("Yes");
			logger.info("Inside Yes");
		} else {
			motoTxnDet.setMultiOption("No");
		}

		logger.info("MultiOption i :" + motoTxnDet.getMultiOption());
		if (motoTxnDet.getAmount().contains(".")) {
			motoTxnDet.setAmount(
					String.format("%012d", (long) Double.parseDouble(motoTxnDet.getAmount().replace(".", ""))));
		} else {
			motoTxnDet.setAmount(String.format("%012d", (long) Double.parseDouble(motoTxnDet.getAmount()) * 100));
		}
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		TerminalDetails data = null;
		// New Changes 25/04/2022 - Start
		if (currentMerchant.getMerchantType() == null) {
			mototxn.setHostType("P");
			motoTxnDet.setHostType("P");

			if (currentMerchant.getMid().getMotoMid() != null) {
				mototxn.setMotoMid(currentMerchant.getMid().getMotoMid());
				motoTxnDet.setMotoMid(currentMerchant.getMid().getMotoMid());

				data = merchantService.loadTerminalDetailsByMid(currentMerchant.getMid().getMotoMid());
			} else if (currentMerchant.getMid().getBoostMid() != null) {
				mototxn.setMotoMid(currentMerchant.getMid().getBoostMid());
				motoTxnDet.setMotoMid(currentMerchant.getMid().getBoostMid());
			}

		} else {
			if (currentMerchant.getMerchantType().isEmpty()) {
				mototxn.setHostType("P");
				motoTxnDet.setHostType("P");
				if (currentMerchant.getMid().getMotoMid() != null) {
					mototxn.setMotoMid(currentMerchant.getMid().getMotoMid());
					motoTxnDet.setMotoMid(currentMerchant.getMid().getMotoMid());
				} else if (currentMerchant.getMid().getBoostMid() != null) {
					mototxn.setMotoMid(currentMerchant.getMid().getBoostMid());
					motoTxnDet.setMotoMid(currentMerchant.getMid().getBoostMid());
				}
			} else {
				mototxn.setHostType(currentMerchant.getMerchantType());
				motoTxnDet.setHostType(currentMerchant.getMerchantType());
				if (currentMerchant.getMerchantType().equals("P")) {
					if (currentMerchant.getMid().getMotoMid() != null) {
						mototxn.setMotoMid(currentMerchant.getMid().getMotoMid());
						motoTxnDet.setMotoMid(currentMerchant.getMid().getMotoMid());
					} else if (currentMerchant.getMid().getBoostMid() != null) {
						mototxn.setMotoMid(currentMerchant.getMid().getBoostMid());
						motoTxnDet.setMotoMid(currentMerchant.getMid().getBoostMid());
					}
				} else if(currentMerchant.getMerchantType().equals("U")) {

					if (currentMerchant.getMid().getUmMotoMid() != null) {
						mototxn.setMotoMid(currentMerchant.getMid().getUmMotoMid());
						motoTxnDet.setMotoMid(currentMerchant.getMid().getUmMotoMid());
					} else if (currentMerchant.getMid().getBoostMid() != null) {
						mototxn.setMotoMid(currentMerchant.getMid().getBoostMid());
						motoTxnDet.setMotoMid(currentMerchant.getMid().getBoostMid());
					}
				}else if(currentMerchant.getMerchantType().equals("FIUU")) {

					if (currentMerchant.getMid().getFiuuMid() != null) {
						mototxn.setMotoMid(currentMerchant.getMid().getFiuuMid());
						motoTxnDet.setMotoMid(currentMerchant.getMid().getFiuuMid());
					} else if (currentMerchant.getMid().getBoostMid() != null) {
						mototxn.setMotoMid(currentMerchant.getMid().getBoostMid());
						motoTxnDet.setMotoMid(currentMerchant.getMid().getBoostMid());
					}
				}

			}
		}
		logger.info("MID : " + motoTxnDet.getMotoMid());
		logger.info("Host Type : " + motoTxnDet.getHostType());
		// Load Tid by Mid
		try {
			data = merchantService.loadTerminalDetailsByMid(motoTxnDet.getMotoMid());
			motoTxnDet.setTid(data.getTid());
		} catch (Exception e) {

			logger.info("Not Found TID For this MID");

		}
		logger.info("TID : " + motoTxnDet.getTid());

		PageBean pageBean = new PageBean("transactions list", "merchantweb/Request/Transaction", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
				
//		ResponseDetails rd = null;
//		try {
//			rd = motoWebService.motoRequest(motoTxnDet);
//			if (rd != null) {
//				if (rd.getResponseMessage().equals("Connection refused")) {
//					rd.setResponseMessage(
//							"Failed..Unable to Send SMS/Email to do Transaction Due to Connection Refused.");
//				}
//				rd.setResponseDescription(rd.getResponseData().getInvoiceId());
//
//			}
//		} catch (IllegalStateException e) {
//
//			e.printStackTrace();
//		} catch (IOException e) {
//
//			e.printStackTrace();
//		}
		
		ResponseDetails rd = new ResponseDetails();
		rd.setResponseDescription("Oops.. Unable to make transaction due to connection refusal.");
		try {
			rd = motoWebService.motoRequest(motoTxnDet);
			
			if(rd.getResponseCode().equals("0000") && rd.getResponseData() != null && rd.getResponseData().getInvoiceId() != null) {
				rd.setResponseDescription(rd.getResponseData().getInvoiceId());
			} else {
				rd.setResponseDescription("Oops.. Unable to make transaction due to connection refusal.");
			}
		} catch (Exception e) {
			logger.error("Exception connecting payment module: " + e.getMessage(), e);
		}

		if (rd !=null && rd.getResponseData() != null && rd.getResponseData().getOpt() != null) {
			logger.info("SMS link: " + rd.getResponseData().getOpt());
		} else {
			logger.warn("ResponseDescription or Smslink is null.");
		}

//		model.addAttribute("Success", rd.getResponseDescription());
		
		model.addAttribute("viaEmail", motoTxnDet.getEmail() == null || motoTxnDet.getEmail().trim().isEmpty() ? "false" : "true");
		model.addAttribute("description", rd.getResponseDescription());		
//		model.addAttribute("smsUrl", "http/mobi.kavi@n");
		model.addAttribute("smsUrl",
				(rd != null && rd.getResponseData() != null && rd.getResponseData().getOpt() != null)
						? rd.getResponseData().getOpt()
						: "");

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/motoRecurTransaction" }, method = RequestMethod.GET)
	public String motoRecurTransaction(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		logger.info("Recurring transaction");
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("tcurrently logged in user is: " + currentMerchant.getUsername());
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/MotoRecurringTransaction",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

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
				logger.info("moto tid: " + t.getTid());
				List<MobileUser> mobileuser = transactionService.getMobileUser(t.getTid());
				for (MobileUser m : mobileuser) {
					if (m.getMotoTid() != null) {

						logger.info("mobile username: " + m.getUsername() + m.getMotoTid());
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
	public String motoRecurSubmitTransaction(final Model model, @ModelAttribute("motoTxnDet") MotoTxnDet motoTxnDet,
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
		 * ()+motoTxnDet.getAmount()+motoTxnDet.getContactName() +motoTxnDet.getEmail
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
		motoTxnDet.setAmount(String.format("%012d", (long) Double.parseDouble(motoTxnDet.getAmount()) * 100));

		// logger.info("check params: " +
		// contactName+" "+phno+" "+amount+" "+referrence+" "+tid);

		Merchant currentMerchant = merchantService.loadMerchant(principal.getName());
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());

		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/motoTransaction",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		MotoTxnDet mototxn = new MotoTxnDet();
		ResponseDetails rd = null;
		/*
		 * mototxn.setAmount(amount.toString()); mototxn.setPhno(phno.toString());
		 * mototxn.setContactName(contactName); mototxn.setTid(tid);
		 * mototxn.setMotoMid(currentMerchant.getMid().getMotoMid());
		 * mototxn.setReferrence(referrence); mototxn.setExpectedDate(expectedDate);
		 * mototxn.setEmail(email);
		 */
		try {
			rd = motoWebService.motoRequest(motoTxnDet);
			if (rd != null) {
				// logger.info("success response..");
				logger.info("Response Data.." + rd.getResponseData().getInvoiceId());
				request.setAttribute("responseData", rd.getResponseData().getInvoiceId());
				request.setAttribute("responseData1", rd.getResponseData().getOpt());
				request.setAttribute("responseSuccess", rd.getResponseMessage());
				model.addAttribute("responseSuccess", rd.getResponseMessage());
				model.addAttribute("responseData1", rd.getResponseData().getOpt());
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
				logger.info("moto tid: " + t.getTid());
				List<MobileUser> mobileuser = transactionService.getMobileUser(t.getTid());
				for (MobileUser m : mobileuser) {
					if (m.getMotoTid() != null) {

						logger.info("mobile username: " + m.getUsername() + m.getMotoTid());
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
		request.setAttribute("responseData", rd.getResponseData().getInvoiceId());
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/recurringList" }, method = RequestMethod.GET)
	public String motoRecurList(final Model model, final java.security.Principal principal,
			HttpServletRequest request) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		logger.info("Merchant: Recurring list");

		// logger.info("currrent page " + currPage);
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("tcurrently logged in user is: " + currentMerchant.getUsername());
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Recurring/RecurringUsersList",
				Module.MERCHANT, "merchantweb/transaction/sideMenuTransaction");
		PaginationBean<EzyRecurringPayment> paginationBean = new PaginationBean<EzyRecurringPayment>();
		// paginationBean.setCurrPage(currPage);
		merchantService.listRecurringMerchant(paginationBean, currentMerchant, null, null);

		logger.info("itemList: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {
			for (EzyRecurringPayment e : paginationBean.getItemList()) {
				try {

					if (e.getMaskedPan() != null) {

						String pan = e.getMaskedPan().substring(e.getMaskedPan().length() - 8);
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
						// logger.info("exp date: "+e.getExpDate());
						String endDate = null;

						e.setExpDate(new SimpleDateFormat("MMM-yyyy")
								.format(new SimpleDateFormat("yyMM").parse(e.getExpDate())));
					}
					if (e.getAmount() != null) {

						String output = new DecimalFormat("#,##0.00").format(Double.parseDouble(e.getAmount()) / 100);

						e.setAmount(output);
					}
					if (e.getLastTriggerDate() != null) {

						e.setLastTriggerDate(new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getLastTriggerDate())));

					}
					if (e.getEndDate() != null) {

						e.setEndDate(new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getEndDate())));
					}
					if (e.getNextTriggerDate() != null) {

						e.setNextTriggerDate(new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getNextTriggerDate())));
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
	public String motoEditRecurring(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @PathVariable final long recId) {

		logger.info("Merchant: moto Edit Recurring  " + principal.getName() + " RecId: " + recId);

		PageBean pageBean = new PageBean("Recurring list", "merchantweb/Recurring/editRecurringDetails",
				Module.MERCHANT, "merchant/sideMenuMerchant");

		PaginationBean<EzyRecurringPayment> paginationBean = new PaginationBean<EzyRecurringPayment>();
		// paginationBean.setCurrPage(currPage);

		EzyRecurringPayment ezyRec = merchantService.loadMerchantRecurring(recId);

		try {

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

			if (ezyRec.getAmount() != null) {

				String output = new DecimalFormat("#,##0.00").format(Double.parseDouble(ezyRec.getAmount()) / 100);

				ezyRec.setAmount(output);
			}
			if (ezyRec.getStartDate() != null) {

				try {

					ezyRec.setStartDate(new SimpleDateFormat("dd-MMM-yyyy")
							.format(new SimpleDateFormat("dd/MM/yyyy").parse(ezyRec.getStartDate())));

				} catch (ParseException e1) {

					e1.printStackTrace();
				}

			}
			if (ezyRec.getExpDate() != null) {
				// logger.info("exp date: "+e.getExpDate());
				String endDate = null;

				try {

					ezyRec.setExpDate(new SimpleDateFormat("MMM-yyyy")
							.format(new SimpleDateFormat("yyMM").parse(ezyRec.getExpDate())));

				} catch (ParseException e1) {

					e1.printStackTrace();
				}

			}
			/*
			 * if (ezyRec.getEndDate() != null) { String endDate=null; endDate=new
			 * SimpleDateFormat("dd-MMM-yyyy") .format(new SimpleDateFormat("dd/mm/yyyy")
			 * .parse(ezyRec.getEndDate()));
			 * logger.info("start date: "+ezyRec.getEndDate()); ezyRec.setEndDate(endDate);
			 * logger.info("start date: "+ezyRec.getEndDate()); }if (ezyRec.getStartDate()
			 * != null) {
			 * 
			 * String startDate=null; startDate=new SimpleDateFormat("dd-MMM-yyyy")
			 * .format(new SimpleDateFormat("dd/mm/yyyy") .parse(ezyRec.getStartDate()));
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
	public String motoUpdateRecurring(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("id") final Long recId,
			@RequestParam("status") final String status, @RequestParam("mid") final String mid) {

		logger.info("Admin-Merchant: moto Update Recurring  " + principal.getName() + " RecId: " + recId);

		logger.info(mid + " : " + recId + " : " + status);
		PageBean pageBean = new PageBean("Recurring list", "merchantweb/Recurring/UpdateRecurSuccessful",
				Module.MERCHANT, "merchant/sideMenuMerchant");

		EzyRecurringPayment ezyRec = merchantService.loadMerchantRecurring(recId);
		ezyRec.setStatus(status);

		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		date = calendar.getTime();
		int year = calendar.getWeekYear();
		long mon = date.getMonth() + 1;
		int day = date.getDate();

		String fromDateToSearch = null;
		String toDateToSearch = null;
		String modifiedDate = year + "-" + mon + "-" + day;

		logger.info("modifiedDate: " + modifiedDate);
		try {
			modifiedDate = new SimpleDateFormat("yyyy/MM/dd")
					.format(new SimpleDateFormat("yyyy-MM-dd").parse(modifiedDate));

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		logger.info("modifiedDate: " + modifiedDate);
		ezyRec.setRemarks(principal.getName() + " on " + modifiedDate);
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
		if (ezyRec != null) {

			AuditTrail auditTrail = merchantService.updateAuditTrailByMerchant(principal.getName(), principal.getName(),
					"RecurringStatusUpdate");

			if (auditTrail != null) {
				logger.info("Recurring Status: " + status + " by Merchant : " + principal.getName());

			}
		}
		// model.addAttribute("paginationBean", paginationBean);

		// model.addAttribute("mid", mid);
		ezyRec.setRemarks("Card No: " + ezyRec.getMaskedPan() + " " + status + " By " + principal.getName());
		model.addAttribute("ezyRec", ezyRec);

		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	@RequestMapping(value = { "/searchRecurringList" }, method = RequestMethod.GET)
	public String motoSearchRecurList(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("date") String fromDate, @RequestParam("date1") String toDate) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		logger.info("moto Search Recurring list");

		logger.info("fromDate: " + fromDate + " toDate: " + toDate);

		try {
			fromDate = new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(fromDate));
			toDate = new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(toDate));

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		logger.info("fromDate: " + fromDate + " toDate: " + toDate);
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("tcurrently logged in user is: " + currentMerchant.getUsername());
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Recurring/RecurringUsersList",
				Module.MERCHANT, "merchantweb/transaction/sideMenuTransaction");
		PaginationBean<EzyRecurringPayment> paginationBean = new PaginationBean<EzyRecurringPayment>();
		// paginationBean.setCurrPage(currPage);
		merchantService.listRecurringMerchant(paginationBean, currentMerchant, fromDate, toDate);

		logger.info("itemList: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {
			for (EzyRecurringPayment e : paginationBean.getItemList()) {
				try {

					if (e.getMaskedPan() != null) {

						String pan = e.getMaskedPan().substring(e.getMaskedPan().length() - 8);
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

						String output = new DecimalFormat("#,##0.00").format(Double.parseDouble(e.getAmount()) / 100);

						e.setAmount(output);
					}
					if (e.getExpDate() != null) {
						// logger.info("exp date: "+e.getExpDate());
						String endDate = null;

						e.setExpDate(new SimpleDateFormat("MMM-yyyy")
								.format(new SimpleDateFormat("yyMM").parse(e.getExpDate())));
					}
					if (e.getLastTriggerDate() != null) {

						e.setLastTriggerDate(new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getLastTriggerDate())));

					}
					if (e.getEndDate() != null) {

						e.setEndDate(new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getEndDate())));
					}
					if (e.getNextTriggerDate() != null) {

						e.setNextTriggerDate(new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getNextTriggerDate())));
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
	public ModelAndView motoExportRecurList(final Model model, final java.security.Principal principal,
			HttpServletRequest request, @RequestParam("date") String fromDate, @RequestParam("date1") String toDate,
			@RequestParam("export") String export) {

		HttpSession session = request.getSession();
		// logger.info("about to list all transaction");
		String myName = (String) session.getAttribute("userName");
		logger.info("moto Search Recurring list");

		logger.info("fromDate: " + fromDate + " toDate: " + toDate);

		try {
			fromDate = new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(fromDate));
			toDate = new SimpleDateFormat("yyyy-mm-dd").format(new SimpleDateFormat("dd/mm/yyyy").parse(toDate));

		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		logger.info("fromDate: " + fromDate + " toDate: " + toDate);
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("tcurrently logged in user is: " + currentMerchant.getUsername());
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Recurring/RecurringUsersList",
				Module.MERCHANT, "merchantweb/transaction/sideMenuTransaction");
		PaginationBean<EzyRecurringPayment> paginationBean = new PaginationBean<EzyRecurringPayment>();
		// paginationBean.setCurrPage(currPage);
		merchantService.listRecurringMerchant(paginationBean, currentMerchant, fromDate, toDate);

		logger.info("itemList: " + paginationBean.getItemList().size());

		if (paginationBean.getItemList().size() > 0) {
			for (EzyRecurringPayment e : paginationBean.getItemList()) {
				try {

					if (e.getMaskedPan() != null) {

						String pan = e.getMaskedPan().substring(e.getMaskedPan().length() - 8);
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
						// logger.info("exp date: "+e.getExpDate());

						e.setExpDate(new SimpleDateFormat("MMM-yyyy")
								.format(new SimpleDateFormat("yyMM").parse(e.getExpDate())));
					}
					if (e.getAmount() != null) {

						String output = new DecimalFormat("#,##0.00").format(Double.parseDouble(e.getAmount()) / 100);

						e.setAmount(output);
					}
					if (e.getLastTriggerDate() != null) {

						e.setLastTriggerDate(new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getLastTriggerDate())));

					}
					if (e.getEndDate() != null) {

						e.setEndDate(new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getEndDate())));
					}
					if (e.getNextTriggerDate() != null) {

						e.setNextTriggerDate(new SimpleDateFormat("dd/MM/yyyy")
								.format(new SimpleDateFormat("dd/MM/yyyy").parse(e.getNextTriggerDate())));
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

	public boolean validateExpiryDate(String expDate) {
		boolean validate = false;

		Date now = new Date();

		logger.info("Today : " + now + " : " + expDate);

		String resDate = null;
		try {

			resDate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("yyyy-MM-dd").parse(expDate));

			String resTime = new SimpleDateFormat("HH:mm:ss")
					.format(new SimpleDateFormat("HH:mm:ss").parse(expDate.substring(11)));
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

		} else {
			logger.info(" Day Expired ");
			validate = false;
		}
		logger.info("status: " + validate);
		return validate;

	}

	// Moto VC

	@RequestMapping(value = { "/motovc" }, method = RequestMethod.GET)
	public String displayMotovc(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {

		logger.info("motovc");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("currently logged in as " + myName);
		logger.info("transaction type checking /motovc");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		logger.info("moto list transaction: " + currentMerchant);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Moto/motoVC", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<ForSettlement> paginationBean = new PaginationBean<ForSettlement>();
		paginationBean.setCurrPage(currPage);

		logger.info("Merchant Type : " + currentMerchant.getMerchantType());

		if (currentMerchant.getMerchantType() == null) {

			logger.info("Merchant Type: " + currentMerchant.getMerchantType());

			logger.info("Moto Mid: " + currentMerchant.getMid().getMotoMid());
			model.addAttribute("mid", currentMerchant.getMid().getMotoMid());
			model.addAttribute("tid", transactionService.getVCTid(currentMerchant.getMid().getMotoMid()));
			model.addAttribute("mType", "P");

		} else if (currentMerchant.getMerchantType().equalsIgnoreCase("P")) {

			logger.info("Merchant Type: " + currentMerchant.getMerchantType());

			logger.info("Moto Mid: " + currentMerchant.getMid().getMotoMid());
			model.addAttribute("mid", currentMerchant.getMid().getMotoMid());
			model.addAttribute("tid", transactionService.getVCTid(currentMerchant.getMid().getMotoMid()));
			model.addAttribute("mType", "P");

		} else if (currentMerchant.getMerchantType().equalsIgnoreCase("U")) {

			logger.info("Merchant Type: " + currentMerchant.getMerchantType());

			logger.info("UM Moto Mid: " + currentMerchant.getMid().getUmMotoMid());
			model.addAttribute("mid", currentMerchant.getMid().getUmMotoMid());
			model.addAttribute("tid", transactionService.getVCTid(currentMerchant.getMid().getUmMotoMid()));
			model.addAttribute("mType", "U");

		} else {
			logger.info("Merchant Type not provided error ");

		}

		model.addAttribute("responseData", "No Record found");
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// Add Moto VC

	@RequestMapping(value = { "/addvc" }, method = RequestMethod.POST)
	public String addvc(@RequestParam("mid") String mid, @RequestParam("tid") String tid,
			@RequestParam("mType") String mType, @RequestParam("job") String job,

			@RequestParam("status1") String status1, @RequestParam("cName1") String cName1,
			@RequestParam("cNumber1") String cNumber1, @RequestParam("lcvv1") String lcvv1,
			@RequestParam("lextd1") String lextd1, @RequestParam("lamount1") String lamount1,
			@RequestParam("cref1") String cref1,

			@RequestParam("status2") String status2, @RequestParam("cName2") String cName2,
			@RequestParam("cNumber2") String cNumber2, @RequestParam("lcvv2") String lcvv2,
			@RequestParam("lextd2") String lextd2, @RequestParam("lamount2") String lamount2,
			@RequestParam("cref2") String cref2,

			@RequestParam("status3") String status3, @RequestParam("cName3") String cName3,
			@RequestParam("cNumber3") String cNumber3, @RequestParam("lcvv3") String lcvv3,
			@RequestParam("lextd3") String lextd3, @RequestParam("lamount3") String lamount3,
			@RequestParam("cref3") String cref3,

			@RequestParam("status4") String status4, @RequestParam("cName4") String cName4,
			@RequestParam("cNumber4") String cNumber4, @RequestParam("lcvv4") String lcvv4,
			@RequestParam("lextd4") String lextd4, @RequestParam("lamount4") String lamount4,
			@RequestParam("cref4") String cref4,

			@RequestParam("status5") String status5, @RequestParam("cName5") String cName5,
			@RequestParam("cNumber5") String cNumber5, @RequestParam("lcvv5") String lcvv5,
			@RequestParam("lextd5") String lextd5, @RequestParam("lamount5") String lamount5,
			@RequestParam("cref5") String cref5,

			@RequestParam("status6") String status6, @RequestParam("cName6") String cName6,
			@RequestParam("cNumber6") String cNumber6, @RequestParam("lcvv6") String lcvv6,
			@RequestParam("lextd6") String lextd6, @RequestParam("lamount6") String lamount6,
			@RequestParam("cref6") String cref6,

			@RequestParam("status7") String status7, @RequestParam("cName7") String cName7,
			@RequestParam("cNumber7") String cNumber7, @RequestParam("lcvv7") String lcvv7,
			@RequestParam("lextd7") String lextd7, @RequestParam("lamount7") String lamount7,
			@RequestParam("cref7") String cref7,

			@RequestParam("status8") String status8, @RequestParam("cName8") String cName8,
			@RequestParam("cNumber8") String cNumber8, @RequestParam("lcvv8") String lcvv8,
			@RequestParam("lextd8") String lextd8, @RequestParam("lamount8") String lamount8,
			@RequestParam("cref8") String cref8,

			@RequestParam("status9") String status9, @RequestParam("cName9") String cName9,
			@RequestParam("cNumber9") String cNumber9, @RequestParam("lcvv9") String lcvv9,
			@RequestParam("lextd9") String lextd9, @RequestParam("lamount9") String lamount9,
			@RequestParam("cref9") String cref9,

			@RequestParam("status10") String status10, @RequestParam("cName10") String cName10,
			@RequestParam("cNumber10") String cNumber10, @RequestParam("lcvv10") String lcvv10,
			@RequestParam("lextd10") String lextd10, @RequestParam("lamount10") String lamount10,
			@RequestParam("cref10") String cref10,

			@RequestParam("status11") String status11, @RequestParam("cName11") String cName11,
			@RequestParam("cNumber11") String cNumber11, @RequestParam("lcvv11") String lcvv11,
			@RequestParam("lextd11") String lextd11, @RequestParam("lamount11") String lamount11,
			@RequestParam("cref11") String cref11,

			@RequestParam("status12") String status12, @RequestParam("cName12") String cName12,
			@RequestParam("cNumber12") String cNumber12, @RequestParam("lcvv12") String lcvv12,
			@RequestParam("lextd12") String lextd12, @RequestParam("lamount12") String lamount12,
			@RequestParam("cref12") String cref12,

			@RequestParam("status13") String status13, @RequestParam("cName13") String cName13,
			@RequestParam("cNumber13") String cNumber13, @RequestParam("lcvv13") String lcvv13,
			@RequestParam("lextd13") String lextd13, @RequestParam("lamount13") String lamount13,
			@RequestParam("cref13") String cref13,

			@RequestParam("status14") String status14, @RequestParam("cName14") String cName14,
			@RequestParam("cNumber14") String cNumber14, @RequestParam("lcvv14") String lcvv14,
			@RequestParam("lextd14") String lextd14, @RequestParam("lamount14") String lamount14,
			@RequestParam("cref14") String cref14,

			@RequestParam("status15") String status15, @RequestParam("cName15") String cName15,
			@RequestParam("cNumber15") String cNumber15, @RequestParam("lcvv15") String lcvv15,
			@RequestParam("lextd15") String lextd15, @RequestParam("lamount15") String lamount15,
			@RequestParam("cref15") String cref15,

			@RequestParam(required = false, defaultValue = "1") final int currPage, final HttpServletRequest request,
			final Model model, final java.security.Principal principal) throws Exception {

		logger.info("addvc");

		logger.info("mid::::" + mid);
		logger.info("tid::::" + tid);
		logger.info("mType::::" + mType);
		logger.info("job::::" + job);

		PageBean pageBean = new PageBean("transactions list", "merchantweb/Moto/motoVC", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");

		model.addAttribute("pageBean", pageBean);

		List<MotoVCDetails> vcList = new ArrayList<>();

		if (!status1.equals(" ")) {
			cNumber1 = cNumber1.replaceAll("[\\s\\-()]", "");
			lextd1 = lextd1.replaceAll("[\\s\\-()]", "");
			String mm = lextd1.substring(0, 2);
			String yy = lextd1.substring(Math.max(lextd1.length() - 2, 0));

			/*
			 * logger.info("card NO::::"+cNumber1); logger.info("EXDT::::"+lextd1);
			 * logger.info("mm::::"+mm); logger.info("yy::::"+yy);
			 */

			lextd1 = yy + mm;
//			logger.info("Changed EXDT::::"+lextd1);

			MotoVCDetails vc1 = new MotoVCDetails();
			vc1.setMid(mid);
			vc1.setTid(tid);
			vc1.setMerchantType(mType);
			vc1.setNameOnCard(cName1);
			vc1.setTxnDetails(cNumber1 + "~" + lcvv1 + "~" + lextd1);
			vc1.setAmount(lamount1);
			vc1.setReference(cref1);
			if (status1.equals("false")) {
				vc1.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc1.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc1.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc1.setCreatedDate(new Date());
			vcList.add(vc1);

		}

		if (!status2.equals(" ")) {

			cNumber2 = cNumber2.replaceAll("[\\s\\-()]", "");
			lextd2 = lextd2.replaceAll("[\\s\\-()]", "");
			String mm = lextd2.substring(0, 2);
			String yy = lextd2.substring(Math.max(lextd2.length() - 2, 0));
			lextd2 = yy + mm;

			MotoVCDetails vc2 = new MotoVCDetails();
			vc2.setMid(mid);
			vc2.setTid(tid);
			vc2.setMerchantType(mType);
			vc2.setNameOnCard(cName2);
			vc2.setTxnDetails(cNumber2 + "~" + lcvv2 + "~" + lextd2);
			vc2.setAmount(lamount2);
			vc2.setReference(cref2);
			if (status2.equals("false")) {
				vc2.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc2.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc2.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc2.setCreatedDate(new Date());
			vcList.add(vc2);

		}

		if (!status3.equals(" ")) {

			cNumber3 = cNumber3.replaceAll("[\\s\\-()]", "");
			lextd3 = lextd3.replaceAll("[\\s\\-()]", "");
			String mm = lextd3.substring(0, 2);
			String yy = lextd3.substring(Math.max(lextd3.length() - 2, 0));
			lextd3 = yy + mm;

			MotoVCDetails vc3 = new MotoVCDetails();
			vc3.setMid(mid);
			vc3.setTid(tid);
			vc3.setMerchantType(mType);
			vc3.setNameOnCard(cName3);
			vc3.setTxnDetails(cNumber3 + "~" + lcvv3 + "~" + lextd3);
			vc3.setAmount(lamount3);
			vc3.setReference(cref3);
			if (status3.equals("false")) {
				vc3.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc3.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc3.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc3.setCreatedDate(new Date());
			vcList.add(vc3);

		}

		if (!status4.equals(" ")) {

			cNumber4 = cNumber4.replaceAll("[\\s\\-()]", "");
			lextd4 = lextd4.replaceAll("[\\s\\-()]", "");
			String mm = lextd4.substring(0, 2);
			String yy = lextd4.substring(Math.max(lextd4.length() - 2, 0));
			lextd4 = yy + mm;

			MotoVCDetails vc4 = new MotoVCDetails();
			vc4.setMid(mid);
			vc4.setTid(tid);
			vc4.setMerchantType(mType);
			vc4.setNameOnCard(cName4);
			vc4.setTxnDetails(cNumber4 + "~" + lcvv4 + "~" + lextd4);
			vc4.setAmount(lamount4);
			vc4.setReference(cref4);
			if (status4.equals("false")) {
				vc4.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc4.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc4.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc4.setCreatedDate(new Date());
			vcList.add(vc4);

		}

		if (!status5.equals(" ")) {

			cNumber5 = cNumber5.replaceAll("[\\s\\-()]", "");
			lextd5 = lextd5.replaceAll("[\\s\\-()]", "");
			String mm = lextd5.substring(0, 2);
			String yy = lextd5.substring(Math.max(lextd5.length() - 2, 0));
			lextd5 = yy + mm;

			MotoVCDetails vc5 = new MotoVCDetails();
			vc5.setMid(mid);
			vc5.setTid(tid);
			vc5.setMerchantType(mType);
			vc5.setNameOnCard(cName5);
			vc5.setTxnDetails(cNumber5 + "~" + lcvv5 + "~" + lextd5);
			vc5.setAmount(lamount5);
			vc5.setReference(cref5);
			if (status5.equals("false")) {
				vc5.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc5.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc5.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc5.setCreatedDate(new Date());
			vcList.add(vc5);

		}

		if (!status6.equals(" ")) {

			cNumber6 = cNumber6.replaceAll("[\\s\\-()]", "");
			lextd6 = lextd6.replaceAll("[\\s\\-()]", "");
			String mm = lextd6.substring(0, 2);
			String yy = lextd6.substring(Math.max(lextd6.length() - 2, 0));
			lextd6 = yy + mm;

			MotoVCDetails vc6 = new MotoVCDetails();
			vc6.setMid(mid);
			vc6.setTid(tid);
			vc6.setMerchantType(mType);
			vc6.setNameOnCard(cName6);
			vc6.setTxnDetails(cNumber6 + "~" + lcvv6 + "~" + lextd6);
			vc6.setAmount(lamount6);
			vc6.setReference(cref6);
			if (status6.equals("false")) {
				vc6.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc6.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc6.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc6.setCreatedDate(new Date());
			vcList.add(vc6);

		}

		if (!status7.equals(" ")) {

			cNumber7 = cNumber7.replaceAll("[\\s\\-()]", "");
			lextd7 = lextd7.replaceAll("[\\s\\-()]", "");
			String mm = lextd7.substring(0, 2);
			String yy = lextd7.substring(Math.max(lextd7.length() - 2, 0));
			lextd7 = yy + mm;

			MotoVCDetails vc7 = new MotoVCDetails();
			vc7.setMid(mid);
			vc7.setTid(tid);
			vc7.setMerchantType(mType);
			vc7.setNameOnCard(cName7);
			vc7.setTxnDetails(cNumber7 + "~" + lcvv7 + "~" + lextd7);
			vc7.setAmount(lamount7);
			vc7.setReference(cref7);
			if (status7.equals("false")) {
				vc7.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc7.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc7.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc7.setCreatedDate(new Date());
			vcList.add(vc7);

		}

		if (!status8.equals(" ")) {

			cNumber8 = cNumber8.replaceAll("[\\s\\-()]", "");
			lextd8 = lextd8.replaceAll("[\\s\\-()]", "");
			String mm = lextd8.substring(0, 2);
			String yy = lextd8.substring(Math.max(lextd8.length() - 2, 0));
			lextd8 = yy + mm;

			MotoVCDetails vc8 = new MotoVCDetails();
			vc8.setMid(mid);
			vc8.setTid(tid);
			vc8.setMerchantType(mType);
			vc8.setNameOnCard(cName8);
			vc8.setTxnDetails(cNumber8 + "~" + lcvv8 + "~" + lextd8);
			vc8.setAmount(lamount8);
			vc8.setReference(cref8);
			if (status8.equals("false")) {
				vc8.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc8.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc8.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc8.setCreatedDate(new Date());
			vcList.add(vc8);

		}

		if (!status9.equals(" ")) {

			cNumber9 = cNumber9.replaceAll("[\\s\\-()]", "");
			lextd9 = lextd9.replaceAll("[\\s\\-()]", "");
			String mm = lextd9.substring(0, 2);
			String yy = lextd9.substring(Math.max(lextd9.length() - 2, 0));
			lextd9 = yy + mm;

			MotoVCDetails vc9 = new MotoVCDetails();
			vc9.setMid(mid);
			vc9.setTid(tid);
			vc9.setMerchantType(mType);
			vc9.setNameOnCard(cName9);
			vc9.setTxnDetails(cNumber9 + "~" + lcvv9 + "~" + lextd9);
			vc9.setAmount(lamount9);
			vc9.setReference(cref9);
			if (status9.equals("false")) {
				vc9.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc9.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc9.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc9.setCreatedDate(new Date());
			vcList.add(vc9);

		}

		if (!status10.equals(" ")) {

			cNumber10 = cNumber10.replaceAll("[\\s\\-()]", "");
			lextd10 = lextd10.replaceAll("[\\s\\-()]", "");
			String mm = lextd10.substring(0, 2);
			String yy = lextd10.substring(Math.max(lextd10.length() - 2, 0));
			lextd10 = yy + mm;

			MotoVCDetails vc10 = new MotoVCDetails();
			vc10.setMid(mid);
			vc10.setTid(tid);
			vc10.setMerchantType(mType);
			vc10.setNameOnCard(cName10);
			vc10.setTxnDetails(cNumber10 + "~" + lcvv10 + "~" + lextd10);
			vc10.setAmount(lamount10);
			vc10.setReference(cref10);
			if (status10.equals("false")) {
				vc10.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc10.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc10.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc10.setCreatedDate(new Date());
			vcList.add(vc10);

		}

		if (!status11.equals(" ")) {

			cNumber11 = cNumber11.replaceAll("[\\s\\-()]", "");
			lextd11 = lextd11.replaceAll("[\\s\\-()]", "");
			String mm = lextd11.substring(0, 2);
			String yy = lextd11.substring(Math.max(lextd11.length() - 2, 0));
			lextd11 = yy + mm;

			MotoVCDetails vc11 = new MotoVCDetails();
			vc11.setMid(mid);
			vc11.setTid(tid);
			vc11.setMerchantType(mType);
			vc11.setNameOnCard(cName11);
			vc11.setTxnDetails(cNumber11 + "~" + lcvv11 + "~" + lextd11);
			vc11.setAmount(lamount11);
			vc11.setReference(cref11);
			if (status11.equals("false")) {
				vc11.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc11.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc11.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc11.setCreatedDate(new Date());
			vcList.add(vc11);

		}

		if (!status12.equals(" ")) {

			cNumber12 = cNumber12.replaceAll("[\\s\\-()]", "");
			lextd12 = lextd12.replaceAll("[\\s\\-()]", "");
			String mm = lextd12.substring(0, 2);
			String yy = lextd12.substring(Math.max(lextd12.length() - 2, 0));
			lextd12 = yy + mm;

			MotoVCDetails vc12 = new MotoVCDetails();
			vc12.setMid(mid);
			vc12.setTid(tid);
			vc12.setMerchantType(mType);
			vc12.setNameOnCard(cName12);
			vc12.setTxnDetails(cNumber12 + "~" + lcvv12 + "~" + lextd12);
			vc12.setAmount(lamount12);
			vc12.setReference(cref12);
			if (status12.equals("false")) {
				vc12.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc12.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc12.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc12.setCreatedDate(new Date());
			vcList.add(vc12);

		}

		if (!status13.equals(" ")) {

			cNumber13 = cNumber13.replaceAll("[\\s\\-()]", "");
			lextd13 = lextd13.replaceAll("[\\s\\-()]", "");
			String mm = lextd13.substring(0, 2);
			String yy = lextd13.substring(Math.max(lextd13.length() - 2, 0));
			lextd13 = yy + mm;

			MotoVCDetails vc13 = new MotoVCDetails();
			vc13.setMid(mid);
			vc13.setTid(tid);
			vc13.setMerchantType(mType);
			vc13.setNameOnCard(cName13);
			vc13.setTxnDetails(cNumber13 + "~" + lcvv13 + "~" + lextd13);
			vc13.setAmount(lamount13);
			vc13.setReference(cref13);
			if (status13.equals("false")) {
				vc13.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc13.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc13.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc13.setCreatedDate(new Date());
			vcList.add(vc13);

		}

		if (!status14.equals(" ")) {

			cNumber14 = cNumber14.replaceAll("[\\s\\-()]", "");
			lextd14 = lextd14.replaceAll("[\\s\\-()]", "");
			String mm = lextd14.substring(0, 2);
			String yy = lextd14.substring(Math.max(lextd14.length() - 2, 0));
			lextd14 = yy + mm;

			MotoVCDetails vc14 = new MotoVCDetails();
			vc14.setMid(mid);
			vc14.setTid(tid);
			vc14.setMerchantType(mType);
			vc14.setNameOnCard(cName14);
			vc14.setTxnDetails(cNumber14 + "~" + lcvv14 + "~" + lextd14);
			vc14.setAmount(lamount14);
			vc14.setReference(cref14);
			if (status14.equals("false")) {
				vc14.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc14.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc14.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc14.setCreatedDate(new Date());
			vcList.add(vc14);

		}

		if (!status15.equals(" ")) {

			cNumber15 = cNumber15.replaceAll("[\\s\\-()]", "");
			lextd15 = lextd15.replaceAll("[\\s\\-()]", "");
			String mm = lextd15.substring(0, 2);
			String yy = lextd15.substring(Math.max(lextd15.length() - 2, 0));
			lextd15 = yy + mm;

			MotoVCDetails vc15 = new MotoVCDetails();
			vc15.setMid(mid);
			vc15.setTid(tid);
			vc15.setMerchantType(mType);
			vc15.setNameOnCard(cName15);
			vc15.setTxnDetails(cNumber15 + "~" + lcvv15 + "~" + lextd15);
			vc15.setAmount(lamount15);
			vc15.setReference(cref15);
			if (status15.equals("false")) {
				vc15.setStatus(CommonStatus.SUBMITTED);
			} else {
				if (job.equals("save")) {
					vc15.setStatus(CommonStatus.SUBMITTED);
				} else {
					vc15.setStatus(CommonStatus.ACTIVE);
				}
			}
			vc15.setCreatedDate(new Date());
			vcList.add(vc15);

		}

		logger.info("Total number of cards to save ::::::::::::" + vcList.size());

		String txnDetails = null;
		String tidTid = null;
//		String dtxnDetails =null;
		String txnAmount = null;
		for (int i = 0; i < vcList.size(); i++) {
			// logger.info("S.No="+(i+1)+"nameOnCard="+vcList.get(i).getNameOnCard()+","+"txnDatils="+vcList.get(i).getTxnDetails()+","+"amount="+vcList.get(i).getAmount()+","+"status="+vcList.get(i).getStatus());

			if (tid != null && !tid.equals(" ")) {

				if (tid != null && !tid.equals(" ")) {
					tidTid = tid + tid;
					tidTid = tidTid.replaceAll("\\s", "");
				}
//				logger.info("tidTid " + tidTid);
				txnDetails = vcList.get(i).getTxnDetails();
				txnDetails = txnDetails.replaceAll("\\s", "");
//				logger.info("txnDetails " + txnDetails);
				txnDetails = AESencrp.encrypt(txnDetails, tidTid);
//				logger.info("Encrypted txnDetails " + txnDetails);
				txnDetails = AESencrp.hexaToAscii(txnDetails, false);
//				logger.info(" json Object HEXA Encrypted txnDetails " + txnDetails);
				vcList.get(i).setTxnDetails(txnDetails);
				/*
				 * txnDetails = AESencrp.hexaToAscii(txnDetails,true);
				 * logger.info(" json Object HEXA Decrypted txnDetails " + txnDetails);
				 * dtxnDetails = AESencrp.decrypt1(txnDetails,tidTid);
				 * logger.info(" txnDetails " +dtxnDetails);
				 */
//				logger.info("Amount " + vcList.get(i).getAmount());

				if (vcList.get(i).getAmount().contains(".")) {
					vcList.get(i).setAmount(String.format("%012d",
							(long) Double.parseDouble(vcList.get(i).getAmount().replace(".", ""))));
				} else {
					vcList.get(i).setAmount(
							String.format("%012d", (long) Double.parseDouble(vcList.get(i).getAmount()) * 100));
				}

//				logger.info("converted Amount " + vcList.get(i).getAmount());

			}
			transactionService.addVC(vcList.get(i));

		}

		if (job.equals("submit")) {

			logger.info("Transaction trigger started");

			ResponseDetails data = MotoPaymentCommunication.motoVC(mid, tid, "ACTIVE");

			List<MotoVCDetails> list1 = null;

			if (data != null) {

				if (data.getResponseCode().equals("0000")) {
					logger.info("Transaction trigger finished");
				} else if (data.getResponseCode().equals("0001")) {
					logger.info("failure code::" + data.getResponseCode());
				}
				logger.info("Transaction trigger finished");

			} else {
				logger.info("Transaction trigger failed due to technical error");

				list1 = transactionService.getActiveMoto(mid);
				logger.info("size :::::: " + list1.size());
				for (int i = 0; i < list1.size(); i++) {
					logger.info("id:::::" + list1.get(i).getId());
					list1.get(i).setStatus(CommonStatus.SUBMITTED);
					transactionService.addVC(list1.get(i));
				}
			}

		}

		model.addAttribute("tid", tid);
		model.addAttribute("mid", mid);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

	// MOTO VC Summary

	@RequestMapping(value = { "/vcSummary" }, method = RequestMethod.GET)
	public String vcSummary(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request)
			throws Exception {

		logger.info("MOTO VC Summary");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Moto/vcSummary", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		logger.info(" MOTO VC Transaction Summary:" + principal.getName());
		PaginationBean<MotoVCDetails> paginationBean = new PaginationBean<MotoVCDetails>();
		paginationBean.setCurrPage(currPage);
		String merchantType = null;

		logger.info("MOTO VC Merchant Type :" + currentMerchant.getMerchantType());

		if (currentMerchant.getMerchantType() == null) {
			merchantType = "P";
		} else if (currentMerchant.getMerchantType().isEmpty()) {
			merchantType = "P";
		} else {
			merchantType = currentMerchant.getMerchantType();
		}

		if (merchantType.equalsIgnoreCase("P")) {

			logger.info("MOTO VC Summary MOTO Mid :" + currentMerchant.getMid().getMotoMid());
			transactionService.motoVC(paginationBean, null, null, currentMerchant.getMid().getMotoMid());
		} else if (merchantType.equalsIgnoreCase("U") || merchantType.equalsIgnoreCase("FIUU")) {
			logger.info("MOTO VC Summary UM MOTO Mid :" + currentMerchant.getMid().getUmMotoMid());
			transactionService.motoVC(paginationBean, null, null, currentMerchant.getMid().getUmMotoMid());
		} else {

			model.addAttribute("responseData", "Invalid Data found");
		}

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// MOTO VC search
	@RequestMapping(value = { "/searchMotoVC" }, method = RequestMethod.GET)
	public String searchMotoVC(HttpServletRequest request, final Model model, @RequestParam final String date,
			@RequestParam final String date1, @RequestParam(required = false, defaultValue = "1") final int currPage, final java.security.Principal principal)
			throws Exception {
		logger.info("search  MOTO VC Summary ");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Moto/vcSummary", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);

		PaginationBean<MotoVCDetails> paginationBean = new PaginationBean<MotoVCDetails>();
		paginationBean.setCurrPage(currPage);

		/*
		 * transactionService.listAllTransaction(paginationBean, date, date1, txnType);
		 */
		if (currentMerchant.getMerchantType().equalsIgnoreCase("P")) {
			logger.info("MOTO VC Search Summary MOTO Mid :" + currentMerchant.getMid().getMotoMid());
			transactionService.motoVC(paginationBean, null, null, currentMerchant.getMid().getMotoMid());
		} else if (currentMerchant.getMerchantType().equalsIgnoreCase("U") || currentMerchant.getMerchantType().equalsIgnoreCase("FIUU")) {
			logger.info("MOTO VC Search Summary UM MOTO Mid :" + currentMerchant.getMid().getUmMotoMid());
			transactionService.motoVC(paginationBean, null, null, currentMerchant.getMid().getUmMotoMid());
		}
		// logger.info("check from date:" + date);
		// logger.info("check to date:" + date1);
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		
		return TEMPLATE_MERCHANT;

	}

	// MOTO VC Export
	@RequestMapping(value = "/MotoVCExport", method = RequestMethod.GET)
	public ModelAndView MotoVCExport(final Model model, @RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate,
//						@RequestParam("txnStatus") String txnStatus,
			@RequestParam("export") String export, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final java.security.Principal principal) throws Exception {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		// logger.info(" for test merchant id :"+currentMerchant.getMid().getMid());
		logger.info("current Merchant: " + currentMerchant.getMid().getMotoMid());

		/*
		 * String dat = null; String dat1 = null;
		 */

		String dat = fromDate;
		String dat1 = toDate;
		PaginationBean<MotoVCDetails> paginationBean = new PaginationBean<MotoVCDetails>();
		paginationBean.setCurrPage(currPage);

		// String txnStatus="A";
//					transactionService.exportUMEzywayTransaction(paginationBean, dat, dat1,txnStatus,currentMerchant.getMid().getUmEzywayMid(),"ALL");
		if (currentMerchant.getMerchantType().equalsIgnoreCase("P")) {
			logger.info("MOTO VC Excel MOTO Mid :" + currentMerchant.getMid().getMotoMid());
			transactionService.motoVC(paginationBean, null, null, currentMerchant.getMid().getMotoMid());
		} else if (currentMerchant.getMerchantType().equalsIgnoreCase("U") || currentMerchant.getMerchantType().equalsIgnoreCase("FIUU")) {
			logger.info("MOTO VC Excel UM MOTO Mid :" + currentMerchant.getMid().getUmMotoMid());
			transactionService.motoVC(paginationBean, null, null, currentMerchant.getMid().getUmMotoMid());
		}
		logger.info("No of Records: " + paginationBean.getItemList().size());
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response

		} else {
			model.addAttribute("responseData", null);
		}

		List<MotoVCDetails> list1 = paginationBean.getItemList();
		if (!(export.equals("PDF"))) {
			return new ModelAndView("txnListVCExcel", "vcTxnList", list1);
		} else {
			return new ModelAndView("txnListVCPdf", "vcTxnList", list1);
		}

	}

	// MOTO VC Cancel
	@RequestMapping(value = { "/MotoVCCancel" }, method = RequestMethod.GET)
	public String MotoVCCancel(HttpServletRequest request, final Model model, @RequestParam final String id,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final java.security.Principal principal) throws Exception {

		logger.info("MOTO VC Cancel");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Moto/vcSummary", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<MotoVCDetails> paginationBean = new PaginationBean<MotoVCDetails>();
		paginationBean.setCurrPage(currPage);

		/*
		 * logger.info("Moto VC Transaction ID " + id);
		 * 
		 * MotoVCDetails vc = transactionService.motoVCById(id);
		 * vc.setStatus(CommonStatus.CANCELLED); transactionService.addVC(vc);
		 */

		List<String> ids = Arrays.asList(id.split("\\s*,\\s*"));

		for (int i = 0; i < ids.size(); i++) {

			logger.info("Moto VC Transaction ID " + ids.get(i));
			MotoVCDetails vc = transactionService.motoVCById(ids.get(i));
			vc.setStatus(CommonStatus.CANCELLED);
			transactionService.addVC(vc);

		}

		logger.info("Moto VC Transaction CANCELLED Successfully");

		if (currentMerchant.getMerchantType().equalsIgnoreCase("P")) {
			logger.info("MOTO VC Cancellation Summary MOTO Mid :" + currentMerchant.getMid().getMotoMid());
			transactionService.motoVC(paginationBean, null, null, currentMerchant.getMid().getMotoMid());
		} else if (currentMerchant.getMerchantType().equalsIgnoreCase("U") || currentMerchant.getMerchantType().equalsIgnoreCase("FIUU")) {
			logger.info("MOTO VC Cancellation Summary UM MOTO Mid :" + currentMerchant.getMid().getUmMotoMid());
			transactionService.motoVC(paginationBean, null, null, currentMerchant.getMid().getUmMotoMid());
		}
		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// MOTO Transaction
	@RequestMapping(value = { "/MotoVCTxn" }, method = RequestMethod.GET)
	public String MotoVCTxn(HttpServletRequest request, final Model model, @RequestParam final String id,
			@RequestParam(required = false, defaultValue = "1") final int currPage, final java.security.Principal principal) throws Exception {

		logger.info("MOTO VC Transaction");

		logger.info("Moto VC Transaction IDs " + id);

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		// String myName = principal.getName();
		logger.info("currently logged in as " + myName);
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		PageBean pageBean = new PageBean("transactions list", "merchantweb/Moto/vcSummary", Module.TRANSACTION_WEB,
				"merchantweb/transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		PaginationBean<MotoVCDetails> paginationBean = new PaginationBean<MotoVCDetails>();
		paginationBean.setCurrPage(currPage);

		List<String> ids = Arrays.asList(id.split("\\s*,\\s*"));

		for (int i = 0; i < ids.size(); i++) {

			logger.info("Moto VC Transaction ID " + ids.get(i));
			MotoVCDetails vc = transactionService.motoVCById(ids.get(i));
			vc.setStatus(CommonStatus.ACTIVE);
			transactionService.addVC(vc);

		}

		logger.info("Moto VC Transaction Status changed Successfully");

		/*
		 * List<TerminalDetails> terminalDetailsList = transactionService
		 * .getTerminalDetails(currentMerchant.getMid().getMotoMid());
		 * 
		 * logger.info("tid: " + terminalDetailsList.get(0).getTid());
		 */
		// Properties prop = new Properties();

		ResponseDetails data = null;

		String hostType = null;
		logger.info("Merchant Type :" + currentMerchant.getMerchantType());

		if (currentMerchant.getMerchantType() == null) {
			hostType = "P";
		} else {

			if (currentMerchant.getMerchantType().isEmpty()) {
				hostType = "P";
			} else {
				hostType = currentMerchant.getMerchantType();
			}

		}

		if (hostType.equalsIgnoreCase("P")) {

			logger.info("mid: " + currentMerchant.getMid().getMotoMid());
			logger.info("tid: " + transactionService.getVCTid(currentMerchant.getMid().getMotoMid()));

			logger.info("Transaction trigger started");

			// ResponseDetails
			// data=MotoPaymentCommunication.motoVC(currentMerchant.getMid().getMotoMid(),terminalDetailsList.get(0).getTid(),"ACTIVE");
			data = MotoPaymentCommunication.motoVC(currentMerchant.getMid().getMotoMid(),
					transactionService.getVCTid(currentMerchant.getMid().getMotoMid()), "ACTIVE");

		} else if (hostType.equalsIgnoreCase("U") || hostType.equalsIgnoreCase("FIUU")) {
			logger.info("mid: " + currentMerchant.getMid().getUmMotoMid());
			logger.info("tid: " + transactionService.getVCTid(currentMerchant.getMid().getUmMotoMid()));

			logger.info("Transaction trigger started");

			// ResponseDetails
			// data=MotoPaymentCommunication.motoVC(currentMerchant.getMid().getMotoMid(),terminalDetailsList.get(0).getTid(),"ACTIVE");
			data = MotoPaymentCommunication.motoVC(currentMerchant.getMid().getUmMotoMid(),
					transactionService.getVCTid(currentMerchant.getMid().getUmMotoMid()), "ACTIVE");
		}

		List<MotoVCDetails> list1 = null;

		if (data != null) {

			if (data.getResponseCode().equals("0000")) {
				logger.info("Transaction trigger finished");
			} else if (data.getResponseCode().equals("0001")) {
				logger.info("failure code::" + data.getResponseCode());
			}

			logger.info("Transaction trigger finished");
		} else {
			logger.info("Transaction trigger failed due to technical error");
			if (hostType.equalsIgnoreCase("P")) {

				list1 = transactionService.getActiveMoto(currentMerchant.getMid().getMotoMid());

			} else if (hostType.equalsIgnoreCase("U")) {

				list1 = transactionService.getActiveMoto(currentMerchant.getMid().getUmMotoMid());

			}
			logger.info("size :::::: " + list1.size());
			for (int i = 0; i < list1.size(); i++) {
				logger.info("id:::::" + list1.get(i).getId());
				list1.get(i).setStatus(CommonStatus.SUBMITTED);
				transactionService.addVC(list1.get(i));
			}
		}

		if (hostType.equalsIgnoreCase("P")) {

			logger.info("Getting Paydee VCC cards");

			transactionService.motoVC(paginationBean, null, null, currentMerchant.getMid().getMotoMid());

		} else if (hostType.equalsIgnoreCase("U")) {

			logger.info("Getting Umobile VCC cards");

			transactionService.motoVC(paginationBean, null, null, currentMerchant.getMid().getUmMotoMid());

		}

		if (paginationBean.getItemList().isEmpty() || paginationBean.getItemList() == null
				|| paginationBean.getItemList().size() == 0) {
			model.addAttribute("responseData", "No Records found"); // table
																	// response
		} else {
			model.addAttribute("responseData", null);
		}
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

}
