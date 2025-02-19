package com.mobiversa.payment.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.dto.DuitNowTrxResponseDto;
import com.mobiversa.payment.dto.DuitnowTxnDto;
import com.mobiversa.payment.service.DuitNowTxnService;
import com.mobiversa.payment.service.MerchantService;

@Controller
@RequestMapping(value = DuitNowController.URL_BASE)
public class DuitNowController extends BaseController{

    public static final String URL_BASE = "/duitnow";

    @Autowired
     MerchantService  merchantService;

    @Autowired
    DuitNowTxnService duitNowTxnService;

    @RequestMapping(value = {"/getMerchantDuitnow"}, method = RequestMethod.GET)
    public String getMerchantDuitNowSummary(final Model model, final java.security.Principal principal,
                                            HttpServletRequest request,
                                            @RequestParam(required = false, defaultValue = "1") final int currPage,
                                            @RequestParam(required = false) String fromDate,
                                            @RequestParam(required = false) String toDate) {
        logger.info("in the merchant duitnowsummary..");
        logger.info("from Date : "+fromDate);
        logger.info("to Date : "+toDate);
        logger.info(" curr page : "+currPage);
        HttpSession session = request.getSession();
        String myName = (String) session.getAttribute("userName");
        Merchant merchant =  merchantService.loadMerchant(myName);
        logger.info("currently logged in as " + myName+" :::: "+merchant.getId());
        PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/merchantDuitnow(E-wallet)",
                PageBean.Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
            PaginationBean<DuitnowTxnDto> paginationBean = new PaginationBean<>();
        paginationBean.setCurrPage(currPage);
        model.addAttribute("pageBean", pageBean);
        DuitNowTrxResponseDto duitnowTxnDtoList = null;

        if (fromDate == null || fromDate.trim().isEmpty() || toDate == null || toDate.trim().isEmpty()) {
            fromDate = null;
            toDate = null;
        }
            duitnowTxnDtoList = duitNowTxnService.getDuitnowTxnDetails(fromDate, toDate, currPage, merchant.getId().toString());
            paginationBean.setItemList(duitnowTxnDtoList.getTransactions());
            paginationBean.setQuerySize(String.valueOf(duitnowTxnDtoList.getTotalRecords()));

        paginationBean.setDateFromBackend(fromDate);
        paginationBean.setDate1FromBackend(toDate);
        model.addAttribute("paginationBean", paginationBean);
        model.addAttribute("merchantId", merchant.getId().toString());

        return TEMPLATE_MERCHANT;
    }

    @RequestMapping(value = {"/filterDuitnowData"}, method = RequestMethod.GET)
    public String filterDuitnowTxn(final Model model, final java.security.Principal principal,
                                   HttpServletRequest request,
                                   @RequestParam(required = false, defaultValue = "1") final int currPage,
                                   @RequestParam(required = false) String searchId,
                                   @RequestParam(required = false) String searchType,
                                   @RequestParam(required = false) String merchantId){

        logger.info("filter ID : "+searchId);
        logger.info("filter ID type : "+searchType);
        logger.info("filter merchantId  : "+merchantId);
        PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/merchantDuitnow(E-wallet)",
                PageBean.Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
        PaginationBean<DuitnowTxnDto> paginationBean = new PaginationBean<>();
        model.addAttribute("pageBean", pageBean);
        DuitNowTrxResponseDto duitnowTxnDtoList = null;

            duitnowTxnDtoList = duitNowTxnService.getFilterData(searchId,searchType,merchantId);
            paginationBean.setItemList(duitnowTxnDtoList.getTransactions());
            paginationBean.setQuerySize(String.valueOf(duitnowTxnDtoList.getTotalRecords()));

        paginationBean.setCurrPage(currPage);
        model.addAttribute("paginationBean", paginationBean);
        model.addAttribute("merchantId", merchantId);

        return TEMPLATE_MERCHANT;

    }


//    @RequestMapping(value = {"/exportDuitnowTxns"}, method = RequestMethod.GET)
//    public ModelAndView exportDuitnowData(final Model model, final java.security.Principal principal,
//                                          HttpServletRequest request,
//                                          @RequestParam(required = false) String fromDate,
//                                          @RequestParam(required = false) String toDate,
//                                          @RequestParam("export") String exportType,
//                                          @RequestParam(required = false) String merchantId){
//        logger.info("from Date export: "+fromDate);
//        logger.info("to Date export : "+toDate);
//        logger.info(" export type  : "+exportType);
//        List<DuitnowTxnDto> duitnowTxnDtoList = new ArrayList<DuitnowTxnDto>();
//
//        PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/merchantDuitnow(E-wallet)",
//                PageBean.Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//
//        PaginationBean<DuitnowTxnDto> paginationBean = new PaginationBean<>();
//        model.addAttribute("pageBean", pageBean);
//        model.addAttribute("merchantId", merchantId);
//        if (fromDate == null || fromDate.trim().isEmpty() || toDate == null || toDate.trim().isEmpty()) {
//            fromDate = null;
//            toDate = null;
//        }
//        duitnowTxnDtoList = duitNowTxnService.exportDuitNowTxnDetails(fromDate, toDate, exportType, paginationBean, merchantId);
//
//        if (duitnowTxnDtoList.isEmpty()) {
//            logger.warn("No DuitNow transactions found for the specified dates"+fromDate+" and "+toDate+". An empty file will be generated.");
//        }
//
//        switch (exportType.toUpperCase()) {
//            case "PDF":
//                return new ModelAndView("DuitNowTxnsPdf", "duitnowTxnList", duitnowTxnDtoList);
//            case "EXCEL":
//                return new ModelAndView("DuitNowTxnsExcel", "duitnowTxnList", duitnowTxnDtoList);
//            default:
//                throw new IllegalArgumentException("Unsupported export type: " + exportType);
//        }
//    }
    
    @RequestMapping(value = {"/exportDuitnowTxns"}, method = RequestMethod.GET)
    public ModelAndView exportDuitnowData(final Model model, final java.security.Principal principal,
                                          HttpServletRequest request,
                                          @RequestParam(required = false) String fromDate,
                                          @RequestParam(required = false) String toDate,
                                          @RequestParam(required = false, defaultValue = "1") final int currPage,
                                          @RequestParam("export") String exportType,
                                          @RequestParam(required = false) String merchantId){
        logger.info("from Date export: "+fromDate);
        logger.info("to Date export : "+toDate);
        logger.info(" export type  : "+exportType);
        List<DuitnowTxnDto> duitnowTxnDtoList = new ArrayList<DuitnowTxnDto>();

        PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/merchantDuitnow(E-wallet)",
                PageBean.Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

        PaginationBean<DuitnowTxnDto> paginationBean = new PaginationBean<>();
        paginationBean.setCurrPage(currPage);
        

        if (fromDate == null || fromDate.trim().isEmpty() || toDate == null || toDate.trim().isEmpty()) {
            fromDate = null;
            toDate = null;
        }
        duitnowTxnDtoList = duitNowTxnService.exportDuitNowTxnDetails(fromDate, toDate, exportType, paginationBean, merchantId);
        paginationBean.setItemList(duitnowTxnDtoList);
        paginationBean.setCurrPage(currPage);
        
        
        if (duitnowTxnDtoList.isEmpty() || duitnowTxnDtoList.size() == 0) {
            logger.warn("No DuitNow transactions found for the specified dates"+fromDate+" and "+toDate+". An empty file will be generated.");
       
            model.addAttribute("pageBean", pageBean);
			model.addAttribute("paginationBean", paginationBean);
			ModelAndView modelAndView = new ModelAndView(TEMPLATE_MERCHANT);
			logger.info("size of pagination bean list :" + paginationBean.getItemList().size());

			return modelAndView;
			
        }else {
        	if ((exportType.equals("PDF"))) {
        		model.addAttribute("pageBean", pageBean);
    			model.addAttribute("paginationBean", paginationBean);
                return new ModelAndView("DuitNowTxnsPdf", "duitnowTxnList", duitnowTxnDtoList);
        	}else {
        		model.addAttribute("pageBean", pageBean);
    			model.addAttribute("paginationBean", paginationBean);
                return new ModelAndView("DuitNowTxnsExcel", "duitnowTxnList", duitnowTxnDtoList);
        	}
        }
     
    }


    @RequestMapping(value = {"/getAdminDuitnow"}, method = RequestMethod.GET)
    public String getAdminDuitNowSummary(final Model model, final java.security.Principal principal,
                                            HttpServletRequest request,
                                            @RequestParam(required = false, defaultValue = "1") final int currPage,
                                            @RequestParam(required = false) String fromDate,
                                            @RequestParam(required = false) String toDate) {
        logger.info("in the admin duitnowsummary..");
        logger.info("from Date : "+fromDate);
        logger.info("to Date : "+toDate);
        logger.info(" curr page : "+currPage);
        HttpSession session = request.getSession();
        String myName = (String) session.getAttribute("userName");
        logger.info("currently logged in as " + myName);
        PageBean pageBean = new PageBean("transactions list", "transaction/DuitnowTransactionSummary",
                PageBean.Module.TRANSACTION, "transaction/sideMenuTransaction");
        PaginationBean<DuitnowTxnDto> paginationBean = new PaginationBean<>();
        paginationBean.setCurrPage(currPage);
     //   model.addAttribute("pageBean", pageBean);

        DuitNowTrxResponseDto duitnowTxnDtoList = null;
        try {
            if (fromDate == null || fromDate.trim().isEmpty() || toDate == null || toDate.trim().isEmpty()) {
                fromDate = null;
                toDate = null;
            }

            duitnowTxnDtoList = duitNowTxnService.getAllDuitnowTxnDetails(fromDate, toDate, currPage);
            paginationBean.setItemList(duitnowTxnDtoList.getTransactions());
            paginationBean.setQuerySize(String.valueOf(duitnowTxnDtoList.getTotalRecords()));

            paginationBean.setDateFromBackend(fromDate);
            paginationBean.setDate1FromBackend(toDate);
            model.addAttribute("pageBean", pageBean);
            model.addAttribute("paginationBean", paginationBean);

            return TEMPLATE_DEFAULT;
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("pageBean", pageBean);
            logger.info("exception : "+e.getMessage());
            return TEMPLATE_DEFAULT;
        }
    }


    @RequestMapping(value = {"/searchData"}, method = RequestMethod.GET)
    public String searchDuitnowTxn(final Model model, final java.security.Principal principal,
                                   HttpServletRequest request,
                                   @RequestParam(required = false, defaultValue = "1") final int currPage,
                                   @RequestParam(required = false) String searchId,
                                   @RequestParam(required = false) String searchType){

        logger.info("filter ID : "+searchId);
        logger.info("filter ID type : "+searchType);
        PageBean pageBean = new PageBean("transactions list", "transaction/DuitnowTransactionSummary",
                PageBean.Module.TRANSACTION, "transaction/sideMenuTransaction");
        PaginationBean<DuitnowTxnDto> paginationBean = new PaginationBean<>();
        model.addAttribute("pageBean", pageBean);
        DuitNowTrxResponseDto duitnowTxnDtoList = null;

        duitnowTxnDtoList = duitNowTxnService.searchSpecificData(searchId,searchType);
        paginationBean.setQuerySize(String.valueOf(duitnowTxnDtoList.getTotalRecords()));
        paginationBean.setItemList(duitnowTxnDtoList.getTransactions());

        paginationBean.setCurrPage(currPage);
        model.addAttribute("paginationBean", paginationBean);
        return TEMPLATE_DEFAULT;

    }

    @RequestMapping(value = {"/exportAllDuitnowTransactions"}, method = RequestMethod.GET)
    public ModelAndView exportAdminTxnData(final Model model, final java.security.Principal principal,
                                          HttpServletRequest request,
                                          @RequestParam(required = false) String fromDate,
                                          @RequestParam(required = false) String toDate,
                                          @RequestParam("export") String exportType){
        logger.info("from Date export: "+fromDate);
        logger.info("to Date export : "+toDate);
        logger.info(" export type  : "+exportType);

        PageBean pageBean = new PageBean("transactions list", "transaction/DuitnowTransactionSummary",
                PageBean.Module.TRANSACTION, "transaction/sideMenuTransaction");
        List<DuitnowTxnDto> duitnowTxnDtoList = new ArrayList<DuitnowTxnDto>();

        PaginationBean<DuitnowTxnDto> paginationBean = new PaginationBean<>();
        model.addAttribute("pageBean", pageBean);
        if (fromDate == null || fromDate.trim().isEmpty() || toDate == null || toDate.trim().isEmpty()) {
            fromDate = null;
            toDate = null;
        }
        duitnowTxnDtoList = duitNowTxnService.exportAllDuitNowTxnDetails(fromDate, toDate, exportType, paginationBean);


//        if (duitnowTxnDtoList.isEmpty()) {
//            logger.warn("No DuitNow transactions found for the specified dates"+fromDate+" and "+toDate+". An empty file will be generated.");
//        }
//
//        switch (exportType.toUpperCase()) {
//            case "PDF":
//                return new ModelAndView("DuitNowTxnsPdf", "duitnowTxnList", duitnowTxnDtoList);
//            case "EXCEL":
//                return new ModelAndView("DuitNowTxnsExcel", "duitnowTxnList", duitnowTxnDtoList);
//            default:
//                throw new IllegalArgumentException("Unsupported export type: " + exportType);
//        }
        
        paginationBean.setItemList(duitnowTxnDtoList);
        
        
        if (duitnowTxnDtoList.isEmpty() || duitnowTxnDtoList.size() == 0) {
            logger.warn("No DuitNow transactions found for the specified dates"+fromDate+" and "+toDate+". An empty file will be generated.");
       
            model.addAttribute("pageBean", pageBean);
			model.addAttribute("paginationBean", paginationBean);
			ModelAndView modelAndView = new ModelAndView(TEMPLATE_DEFAULT);
			logger.info("size of pagination bean list :" + paginationBean.getItemList().size());

			return modelAndView;
			
        }else {
        	if ((exportType.equals("PDF"))) {
        		model.addAttribute("pageBean", pageBean);
    			model.addAttribute("paginationBean", paginationBean);
                return new ModelAndView("DuitNowTxnsPdf", "duitnowTxnList", duitnowTxnDtoList);
        	}else {
        		model.addAttribute("pageBean", pageBean);
    			model.addAttribute("paginationBean", paginationBean);
                return new ModelAndView("DuitNowTxnsExcel", "duitnowTxnList", duitnowTxnDtoList);
        	}
        }
    }


//    payout login


    @RequestMapping(value = {"/getDuitnowTxn"}, method = RequestMethod.GET)
    public String getDuitnowTxnsInPayout(final Model model, final java.security.Principal principal,
                                         HttpServletRequest request,
                                         @RequestParam(required = false, defaultValue = "1") final int currPage,
                                         @RequestParam(required = false) String fromDate,
                                         @RequestParam(required = false) String toDate) {
        logger.info("in the payout--- duitnow summary..");
        logger.info("from Date : "+fromDate);
        logger.info("to Date : "+toDate);
        logger.info(" curr page : "+currPage);
        HttpSession session = request.getSession();
        String myName = (String) session.getAttribute("userName");
        logger.info("currently logged in as " + myName);
        PageBean pageBean = new PageBean("transactions list", "PayoutUser/DuitnowTxnsSummary",
                PageBean.Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
        PaginationBean<DuitnowTxnDto> paginationBean = new PaginationBean<>();
        paginationBean.setCurrPage(currPage);
        DuitNowTrxResponseDto duitnowTxnDtoList = null;
        try {
            if (fromDate == null || fromDate.trim().isEmpty() || toDate == null || toDate.trim().isEmpty()) {
                fromDate = null;
                toDate = null;
            }

            duitnowTxnDtoList = duitNowTxnService.getAllDuitnowTxnDetails(fromDate, toDate, currPage);
            paginationBean.setItemList(duitnowTxnDtoList.getTransactions());
            paginationBean.setQuerySize(String.valueOf(duitnowTxnDtoList.getTotalRecords()));

            paginationBean.setDateFromBackend(fromDate);
            paginationBean.setDate1FromBackend(toDate);
            model.addAttribute("pageBean", pageBean);
            model.addAttribute("paginationBean", paginationBean);

            return TEMPLATE_PAYOUTUSER;
        }catch(Exception e){
            e.printStackTrace();
            model.addAttribute("pageBean", pageBean);
            logger.info("exception : "+e.getMessage());
            return TEMPLATE_PAYOUTUSER;
        }
    }


    @RequestMapping(value = {"/duitnowDataSearch"}, method = RequestMethod.GET)
    public String filterData(final Model model, final java.security.Principal principal,
                                   HttpServletRequest request,
                                   @RequestParam(required = false, defaultValue = "1") final int currPage,
                                   @RequestParam(required = false) String searchId,
                                   @RequestParam(required = false) String searchType){

        logger.info("filter ID : "+searchId);
        logger.info("filter ID type : "+searchType);
        PageBean pageBean = new PageBean("transactions list", "PayoutUser/DuitnowTxnsSummary",
                PageBean.Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
        PaginationBean<DuitnowTxnDto> paginationBean = new PaginationBean<>();
        model.addAttribute("pageBean", pageBean);
        DuitNowTrxResponseDto duitnowTxnDtoList = null;

        duitnowTxnDtoList = duitNowTxnService.searchSpecificData(searchId,searchType);
        paginationBean.setItemList(duitnowTxnDtoList.getTransactions());
        paginationBean.setQuerySize(String.valueOf(duitnowTxnDtoList.getTotalRecords()));
        paginationBean.setCurrPage(currPage);
        model.addAttribute("paginationBean", paginationBean);
        return TEMPLATE_PAYOUTUSER;

    }

    @RequestMapping(value = {"/exportDuitnowTransactions"}, method = RequestMethod.GET)
    public ModelAndView exportData(final Model model, final java.security.Principal principal,
                                           HttpServletRequest request,
                                           @RequestParam(required = false) String fromDate,
                                           @RequestParam(required = false) String toDate,
                                           @RequestParam("export") String exportType){
        logger.info("from Date export: "+fromDate);
        logger.info("to Date export : "+toDate);
        logger.info(" export type  : "+exportType);

        PageBean pageBean = new PageBean("transactions list", "PayoutUser/DuitnowTxnsSummary",
                PageBean.Module.TRANSACTION, "transaction/sideMenuTransaction");

        PaginationBean<DuitnowTxnDto> paginationBean = new PaginationBean<>();

        List<DuitnowTxnDto> duitnowTxnDtoList = new ArrayList<DuitnowTxnDto>();

        model.addAttribute("pageBean", pageBean);
        if (fromDate == null || fromDate.trim().isEmpty() || toDate == null || toDate.trim().isEmpty()) {
            fromDate = null;
            toDate = null;
        }
        duitnowTxnDtoList = duitNowTxnService.exportAllDuitNowTxnDetails(fromDate, toDate, exportType, paginationBean);


//        if (duitnowTxnDtoList.isEmpty()) {
//            logger.warn("No DuitNow transactions found for the specified dates"+fromDate+" and "+toDate+". An empty file will be generated.");
//        }
//
//        switch (exportType.toUpperCase()) {
//            case "PDF":
//                return new ModelAndView("DuitNowTxnsPdf", "duitnowTxnList", duitnowTxnDtoList);
//            case "EXCEL":
//                return new ModelAndView("DuitNowTxnsExcel", "duitnowTxnList", duitnowTxnDtoList);
//            default:
//                throw new IllegalArgumentException("Unsupported export type: " + exportType);
//        }
        
        paginationBean.setItemList(duitnowTxnDtoList);
        
        
        if (duitnowTxnDtoList.isEmpty() || duitnowTxnDtoList.size() == 0) {
            logger.warn("No DuitNow transactions found for the specified dates"+fromDate+" and "+toDate+". An empty file will be generated.");    
            model.addAttribute("pageBean", pageBean);
			model.addAttribute("paginationBean", paginationBean);
			ModelAndView modelAndView = new ModelAndView(TEMPLATE_PAYOUTUSER);
			logger.info("size of pagination bean list :" + paginationBean.getItemList().size());
			return modelAndView;
			
        }else {
        	if ((exportType.equals("PDF"))) {
        		model.addAttribute("pageBean", pageBean);
    			model.addAttribute("paginationBean", paginationBean);
                return new ModelAndView("DuitNowTxnsPdf", "duitnowTxnList", duitnowTxnDtoList);
        	}else {
        		model.addAttribute("pageBean", pageBean);
    			model.addAttribute("paginationBean", paginationBean);
                return new ModelAndView("DuitNowTxnsExcel", "duitnowTxnList", duitnowTxnDtoList);
        	}
        }

    }

    @RequestMapping(value = {"/voidRequest"}, method = RequestMethod.GET)
    public String duitnowVoidRequest(final Model model, final java.security.Principal principal,
                             HttpServletRequest request,
                             @RequestParam(required = false, defaultValue = "1") final int currPage,
                             @RequestParam(required = false) String invoiceId,
                             @RequestParam(required = false) String amount) throws JsonProcessingException {

        logger.info("invoice id : "+invoiceId);
        logger.info("request amount : "+amount);
        logger.info("currpage : "+currPage);
        
    	HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("userName");

        PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/merchantDuitnow(E-wallet)",
                PageBean.Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
        PaginationBean<DuitnowTxnDto> paginationBean = new PaginationBean<>();
        model.addAttribute("pageBean", pageBean);
        String response = null;
        response =  duitNowTxnService.requestVoid(invoiceId,amount,userName);
        logger.info("response from server : "+response);
        
        JSONObject jsonResponse = new JSONObject(response);
        String responseCode = null;
        if(jsonResponse.getString("responseCode") != null) {
        	responseCode = jsonResponse.getString("responseCode");
        }
        String status = "";
        String errorCode = "";
        String errorMessage = "";
        if ("0000".equals(responseCode)) {
           
            if (jsonResponse.has("voidResponse")) {
               status  = jsonResponse.getJSONObject("voidResponse").getString("status");
                logger.info("Status: " + status);
            } else {
                logger.info("voidResponse not found in successful response.");
            }
        } else {
            
            if (jsonResponse.has("errorResponse")) {
                errorCode = jsonResponse.getJSONObject("errorResponse").getString("code");
                errorMessage = jsonResponse.getJSONObject("errorResponse").getString("message");
                logger.info("Error Code: " + errorCode + ", Message: " + errorMessage);
            } else {
                logger.info("Unexpected response format: " + jsonResponse.toString());
            }
        }


        
        DuitNowTrxResponseDto duitnowTxnDtoList = null;
          String  fromDate = null;
          String  toDate = null;
       
          Merchant merchant =  merchantService.loadMerchant(userName);
          duitnowTxnDtoList = duitNowTxnService.getDuitnowTxnDetails(fromDate, toDate, currPage, merchant.getId().toString());
          paginationBean.setItemList(duitnowTxnDtoList.getTransactions());
          paginationBean.setQuerySize(String.valueOf(duitnowTxnDtoList.getTotalRecords()));
          paginationBean.setCurrPage(currPage);
        model.addAttribute("merchantId", merchant.getId().toString());
        model.addAttribute("paginationBean", paginationBean);
       // model.addAttribute("voidResponse", "success");
        model.addAttribute("voidResponseCode", responseCode);
        model.addAttribute("voidStatus", status);
        model.addAttribute("voidAmount", amount);
        model.addAttribute("errorStatusCode", errorCode);
        model.addAttribute("errorMessage", errorMessage);

        return TEMPLATE_MERCHANT;

    }





}
