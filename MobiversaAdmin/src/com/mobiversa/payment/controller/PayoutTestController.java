package com.mobiversa.payment.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.common.bo.PayoutDetail;
import com.mobiversa.payment.connect.ResponseData;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.controller.bean.TestPayoutBean;
import com.mobiversa.payment.dao.MerchantDao;
import com.mobiversa.payment.service.MerchantService;
import com.mobiversa.payment.service.TransactionService;
import com.mobiversa.payment.util.BankCode;
import com.mobiversa.payment.util.PayoutModel;
import com.mobiversa.payment.util.PropertyLoad;

@Controller
@RequestMapping(value = PayoutTestController.URL_BASE)
public class PayoutTestController extends BaseController {

	@Autowired
	private MerchantService merchantService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private MerchantDao merchantDAO;

	public static final String URL_BASE = "/testpayout";

	private static final Logger logger = Logger.getLogger(PayoutTestController.class);

	@RequestMapping(value = { "", "/", "/**/*" }, method = RequestMethod.GET)
	public String defaultPage() {
		return "redirect:" + URL_BASE + "/1";
	}

	// Intiate Api
	@RequestMapping(value = { "/payoutRequest" }, method = RequestMethod.GET)
	public String mobipayout(final Model model, final java.security.Principal principal, HttpServletRequest request,@RequestParam(required = false, defaultValue = "1") final int currPage) {

		logger.info("mobi Test Payout Request ");
		
		PageBean pageBean = new PageBean("HOME MERCHANTWEB","PayoutUser/TestPayout/PayoutRequest",
				Module.PAYOUT_TEST);
		model.addAttribute("pageBean", pageBean);


		return TEMPLATE_TEST_PAYOUT;
	}

	// Uat Payout call
	@RequestMapping(value = { "/validate-uat" }, method = RequestMethod.POST)
	public String mobiUATpayout(final Model model, final java.security.Principal principal,
			HttpServletRequest request,@RequestParam("mobiapikey") String mobiapikey,@RequestParam("Service") String Service,@RequestParam("BusinessRegNo") String BusinessRegNo,@RequestParam("Bankname") String Bankname,@RequestParam("BIC_Code") String BIC_Code,@RequestParam("BankaccNo") String BankaccNo,@RequestParam("Customername") String Customername,@RequestParam("amount") String amount,@RequestParam("Submid") String Submid,@RequestParam("PayoutId") String PayoutId) {

		logger.info("mobiApiKey :"+mobiapikey);
		logger.info("Service : "+Service);
		logger.info("businessRegNo : "+BusinessRegNo);
		logger.info("bankName : "+Bankname);
		logger.info("bicCode : "+BIC_Code);
		logger.info("bankAccNo : "+BankaccNo);
		logger.info("customerName : "+Customername);
		logger.info("amount :"+amount);
		logger.info("subMID :"+Submid);
		logger.info("payoutid :"+PayoutId);
		
		
		TestPayoutBean payoutApi = null;
		payoutApi = PayoutTest(mobiapikey,Service,BusinessRegNo,Bankname,BIC_Code,BankaccNo,Customername,amount,Submid,PayoutId);
		
		if(payoutApi.getResponseCode().equals("0000")) {
			
			logger.info("Bank Response Code :" + payoutApi.getResponseCode());
			logger.info("Bank Response Message :" + payoutApi.getResponseMessage());
			logger.info("Bank Response Description :" + payoutApi.getResponseDescription());
			logger.info("Bank Response transaction Id :" + payoutApi.getResponseData().getTrxId());
			
			PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/TestPayout/SuccessPayoutRequest", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			
			model.addAttribute("responseCode", payoutApi.getResponseCode());
			model.addAttribute("responseMessage", payoutApi.getResponseMessage());
			model.addAttribute("responseDescription", payoutApi.getResponseDescription());
			model.addAttribute("txnId", payoutApi.getResponseData().getTrxId());
			
		}
		else {
			logger.info("Bank Response Code :" + payoutApi.getResponseCode());
			logger.info("Bank Response Message :" + payoutApi.getResponseMessage());
			logger.info("Bank Response Description :" + payoutApi.getResponseDescription());
			logger.info("Bank Response failure Reason :" + payoutApi.getFailureReason());
			
			PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/TestPayout/FailurePayoutRequest", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);
			
			model.addAttribute("responseCode", payoutApi.getResponseCode());
			model.addAttribute("responseMessage", payoutApi.getResponseMessage());
			model.addAttribute("responseDescription", payoutApi.getResponseDescription());
			model.addAttribute("failureReason", payoutApi.getFailureReason());
			
		}
		
		return TEMPLATE_TEST_PAYOUT;
	}

	@RequestMapping(value = { "/checkStatus" }, method = RequestMethod.GET)
	public String statusCheck(final Model model,
			final java.security.Principal principal) {

		logger.info("check Status " );
		
		PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/TestPayout/CheckStatus", Module.TRANSACTION,
				"transaction/sideMenuTransaction");
		model.addAttribute("pageBean", pageBean);
		
		
		return TEMPLATE_TEST_PAYOUT;
	}
	
	@RequestMapping(value = { "/searchTxnStatus" }, method = RequestMethod.POST)
	public String searchStatusCheck(final Model model,@RequestParam("txnIdorPayoutId") String txnIdorPayoutId,
			final java.security.Principal principal) {

		logger.info("txnIdorPayoutId :"+txnIdorPayoutId);
//		TestPayoutBean checkStatus = null;
//		checkStatus = checkStatusAPI(txnIdorPayoutId);
		
		PayoutDetail payout = null;
		payout = merchantService.searchTransaction(txnIdorPayoutId);

		if(payout != null) {
			PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/TestPayout/StatusSuccessRecords", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			
			
			ResponseData lr = new ResponseData();
			lr.setCustomerName("mobi Test");
			lr.setMerchantName(payout.getMerchant().getBusinessName());
			lr.setAmount(payout.getPayoutAmount());
			lr.setBankName(payout.getMerchant().getBankName());
			lr.setBankAccNo(payout.getMerchant().getBankAcc());
			lr.setTransactionId(payout.getInvoiceIdProof());
			
			
			
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("responseCode", "0000");
			model.addAttribute("responseMessage", "Success");
			model.addAttribute("responseDescription", "Transacton is Success");
			model.addAttribute("responseData", lr);
			
			logger.info("response Merchant Name :"+payout.getMerchant().getBusinessName()); //$NON-NLS-1$
			logger.info("response Transaction Id :"+payout.getInvoiceIdProof());
			logger.info("response amount :"+payout.getPayoutAmount());
			logger.info("response Bank Name :"+payout.getMerchant().getBankName());
			logger.info("response Bank Acc No :"+payout.getMerchant().getBankAcc());
			
			
		}else {
			PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/TestPayout/StatusFailureRecords", Module.TRANSACTION,
					"transaction/sideMenuTransaction");
			model.addAttribute("pageBean", pageBean);

			model.addAttribute("pageBean", pageBean);
			model.addAttribute("responseCode", "0001");
			model.addAttribute("responseMessage", "Failed");
			model.addAttribute("responseDescription", "Transacton is Failure");
//			model.addAttribute("failureReason", checkStatus.getFailureReason());
//			
		}
		
		
		
//		if(checkStatus.getResponseCode().equals("0000")) {
//			PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/TestPayout/StatusSuccessRecords", Module.TRANSACTION,
//					"transaction/sideMenuTransaction");
//			
//			model.addAttribute("pageBean", pageBean);
//			model.addAttribute("responseCode", checkStatus.getResponseCode());
//			model.addAttribute("responseMessage", checkStatus.getResponseMessage());
//			model.addAttribute("responseDescription", checkStatus.getResponseDescription());
//			model.addAttribute("responseData", checkStatus.getResponseData());
//			
//			logger.info("response customer Name :"+checkStatus.getResponseData().getCustomerName());
//			logger.info("response Merchant Name :"+checkStatus.getResponseData().getMerchantName());
//			logger.info("response Transaction Id :"+checkStatus.getResponseData().getTransactionId());
//			logger.info("response amount :"+checkStatus.getResponseData().getAmount());
//			logger.info("response Bank Name :"+checkStatus.getResponseData().getBankName());
//			logger.info("response Bank Acc No :"+checkStatus.getResponseData().getBankAccNo());
//			logger.info("response Description :"+checkStatus.getResponseDescription());
//			
//			
//		}else {
//			PageBean pageBean = new PageBean("payout transactions list", "PayoutUser/TestPayout/StatusFailureRecords", Module.TRANSACTION,
//					"transaction/sideMenuTransaction");
//			model.addAttribute("pageBean", pageBean);
//
//			model.addAttribute("pageBean", pageBean);
//			model.addAttribute("responseCode", checkStatus.getResponseCode());
//			model.addAttribute("responseMessage", checkStatus.getResponseMessage());
//			model.addAttribute("responseDescription", checkStatus.getResponseDescription());
//			model.addAttribute("failureReason", checkStatus.getFailureReason());
//			
//		}
		
//		String txnId = "";
		
	
		return TEMPLATE_TEST_PAYOUT;
	}

	
	
	//Summary
	@RequestMapping(value = { "/transactionSummary" }, method = RequestMethod.GET)
	public String transactionSummaryData(final Model model,
			final java.security.Principal principal,HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage) {

				logger.info("payout test Transaction Summary");
				HttpSession session = request.getSession();
				String myName = (String) session.getAttribute("userName");
				logger.info("currently logged in as " + myName);
				Merchant currentMerchant = merchantService.loadMerchant(myName);
				PageBean pageBean = new PageBean("transactions list", "PayoutUser/TestPayout/PayoutTestSummary",
						Module.TRANSACTION_WEB, "PayoutUser/TestPayout/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);

				PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
				paginationBean.setCurrPage(currPage);

				transactionService.payoutTxnList(paginationBean, null, null);

				model.addAttribute("paginationBean", paginationBean);
				
	
		return TEMPLATE_TEST_PAYOUT;
	}

	

	//Summary
	@RequestMapping(value = { "/SearchtransactionSummary" }, method = RequestMethod.GET)
	public String SearchtransactionSummaryData(final Model model,
			final java.security.Principal principal,HttpServletRequest request, @RequestParam(required = false, defaultValue = "1") final int currPage, @RequestParam final String date, @RequestParam final String date1) {

				logger.info("payout test Transaction Summary");
				HttpSession session = request.getSession();
				String myName = (String) session.getAttribute("userName");
				logger.info("currently logged in as " + myName);
				Merchant currentMerchant = merchantService.loadMerchant(myName);
				PageBean pageBean = new PageBean("transactions list", "PayoutUser/TestPayout/PayoutTestSummary",
						Module.TRANSACTION_WEB, "PayoutUser/TestPayout/sideMenuTransaction");
				model.addAttribute("pageBean", pageBean);

				PaginationBean<PayoutModel> paginationBean = new PaginationBean<PayoutModel>();
				paginationBean.setCurrPage(currPage);

				logger.info("currPage :"+currPage);
				logger.info("From date :"+date);
				logger.info("To date :"+date1);
				
				transactionService.payoutTxnList(paginationBean, date, date1);

				model.addAttribute("paginationBean", paginationBean);
				
	
		return TEMPLATE_TEST_PAYOUT;
	}

	
	
	
	public TestPayoutBean PayoutTest(String mobiapikey,String Service,String BusinessRegNo,String Bankname,String BIC_Code,String BankaccNo,String Customername,String amount,String Submid,String PayoutId) {

		TestPayoutBean testpayout = new TestPayoutBean();
		StringBuffer response1 = new StringBuffer();
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;
		try {
//			url = new URL("http://192.168.11.202:8080/externalapi/payoutservice");
			
			logger.info("Payout Test SIMULATION url  :"+ PropertyLoad.getFile().getProperty("PAYOUT_REQUEST_SIMULATION_API"));
			
			url = new URL("http://localhost:8087/externalapi/payoutSimulation");

			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json");
			JSONObject params = new JSONObject();
			BankCode bank = new BankCode();

			String bicCode = bank.getBankBicCode("Ambank Malaysia Berhad");


			logger.info("mobiApiKey :"+mobiapikey);
			logger.info("Service : "+Service);
			logger.info("businessRegNo : "+BusinessRegNo);
			logger.info("bankName : "+Bankname);
			logger.info("bicCode : "+BIC_Code);
			logger.info("bankAccNo : "+BankaccNo);
			logger.info("customerName : "+Customername);
			logger.info("amount :"+amount);
			logger.info("subMID :"+Submid);
			logger.info("payoutid :"+PayoutId);
			
			params.put("mobiApiKey", mobiapikey);
			params.put("service", Service);
			params.put("businessRegNo", BusinessRegNo);
			params.put("bankName", Bankname);
			params.put("bicCode", BIC_Code);
			params.put("bankAccNo", BankaccNo);
			params.put("customerName", Customername);
			params.put("amount", amount);
			params.put("subMID", Submid);
			params.put("payoutid", PayoutId);
			
//			params.put("mobiApiKey", "b07ad9f31df158edb188a41f725899bc");
//			params.put("service", "PAYOUT_TXN_REQ");
//			params.put("businessRegNo", "12345");
//			params.put("bankName", "Ambank Malaysia Berhad");
//			params.put("bicCode", "ARBKMYKL");
//			params.put("bankAccNo", "8881048358879");
//			params.put("customerName", "mobitest");
//			params.put("amount", "1.00");
//			params.put("subMID", "201100000012450");

			paramss = params;
			OutputStream os = con.getOutputStream();
			os.write(paramss.toString().getBytes());
			logger.info("The params That passed :" + paramss);
			os.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
			while ((inputLine = br.readLine()) != null) {
				response1.append(inputLine);
			}
			output = response1.toString();
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			testpayout = gson.fromJson(output, TestPayoutBean.class);
//		just.setResponseCode(just.getResponseCode());
		} catch (Exception p) {

			logger.info("Exception :" + p.getMessage());

		}
		return testpayout;
	}

	
	
	public TestPayoutBean checkStatusAPI(String PayoutIdorTxnId) {

		TestPayoutBean testpayout = new TestPayoutBean();
		StringBuffer response1 = new StringBuffer();
		String inputLine = null;
		String output = null;
		JSONObject paramss = null;
		URL url;

		
		logger.info("PayoutIdorTxnId :"+PayoutIdorTxnId);
		
		logger.info("Status Check Simulation API :"+PropertyLoad.getFile().getProperty("STATUS_CHECK_SIMULATION_API"));
		
		
		try {
		    // Append parameters to the URL
			
		    url = new URL(PropertyLoad.getFile().getProperty("STATUS_CHECK_SIMULATION_API") + PayoutIdorTxnId);

		    HttpURLConnection con = (HttpURLConnection) url.openConnection();

		    con.setRequestMethod("GET");

		    // Optional: Set additional headers if needed
		    // con.setRequestProperty("HeaderName", "HeaderValue");

		    // Reading the response
		    BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));
		    while ((inputLine = br.readLine()) != null) {
		        response1.append(inputLine);
		    }
		    br.close();

		    output = response1.toString();

		    Gson gson = new GsonBuilder().setPrettyPrinting().create();
		    testpayout = gson.fromJson(output, TestPayoutBean.class);

		    // Close the connection
		    con.disconnect();

		} catch (Exception e) {
		    // Handle exceptions
		    e.printStackTrace();
		}

		return testpayout;
	}
	
	
	

}
