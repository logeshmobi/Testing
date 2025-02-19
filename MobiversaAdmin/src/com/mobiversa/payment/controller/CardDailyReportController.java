package com.mobiversa.payment.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mobiversa.common.bo.Merchant;
import com.mobiversa.payment.controller.bean.PageBean;
import com.mobiversa.payment.controller.bean.PageBean.Module;
import com.mobiversa.payment.controller.bean.PaginationBean;
import com.mobiversa.payment.service.MerchantService;

@Controller
@RequestMapping(value = CardDailyReportController.URL_BASE)
public class CardDailyReportController extends BaseController {

	@Autowired
	private MerchantService merchantService;

	public static final String URL_BASE = "/DailyReport";
	private static final Logger logger = Logger.getLogger(CardDailyReportController.class);

	@RequestMapping(value = { "/Card" }, method = RequestMethod.GET)
	public String DailyReport(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/CardDailyReport",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		String ezyMid = currentMerchant.getMid().getMid();
		String ezyUmMid = currentMerchant.getMid().getUmMid();
		String ezyWayMid = currentMerchant.getMid().getEzywayMid();
		String ezywayUmMid = currentMerchant.getMid().getUmEzywayMid();
		String ezymotoUmMid = currentMerchant.getMid().getUmMotoMid();
		String motoMid = currentMerchant.getMid().getMotoMid();
		
		String fiuuMid = currentMerchant.getMid().getFiuuMid();
		
		String mid = null;
		List<String> midList = new ArrayList<String>();
		
		// File Name - Date Format
					List<String> dateList = new ArrayList<String>();
					// File Name
					List<String> nameList = new ArrayList<String>();

		if (ezyMid != null) {
			midList.add(ezyMid);
		}
		if (ezyUmMid != null) {
			midList.add(ezyUmMid);
		}
		if (ezyWayMid != null) {
			midList.add(ezyWayMid);
		}
		if (ezywayUmMid != null) {
			midList.add(ezywayUmMid);
		}
		if (ezymotoUmMid != null) {
			midList.add(ezymotoUmMid);
		}
		if (motoMid != null) {
			midList.add(motoMid);
		}
		
		if(fiuuMid != null) {
			midList.add(fiuuMid);
		}

		String merchantName = null;
		String ReportPath = null;

		for (String s : midList) {
			System.out.println(s);
			String mid1 = s;

			logger.info("Mid value : " + mid1);

			// To Check Merchant Type is Paydee or Umobile

			if ((currentMerchant.getMerchantType() == null)
					|| (currentMerchant.getMerchantType().equalsIgnoreCase("P"))) {

				ReportPath = "C:\\Mobi_config\\pdf\\";

				String MerchantName = currentMerchant.getBusinessName().toUpperCase();
				merchantName = MerchantName.replace("\\", "").replace(" ", "").replace("/", "") + "_";
				logger.info(" PAYDEE ----- Append File Name Format in Merchant Name = " + merchantName);

			} else if (currentMerchant != null && (currentMerchant.getMerchantType().equalsIgnoreCase("U") || currentMerchant.getMerchantType().equalsIgnoreCase("FIUU"))) {

				// ReportPath =
				// "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\UMobilePDF\\";

				ReportPath = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\UMobilePDF\\";

				String MerchantName = currentMerchant.getBusinessShortName().toUpperCase();

				merchantName = "MOBI_" + MerchantName.replace("\\", "").replace(" ", "").replace("/", "") + "_";
				logger.info(" UMOBILE ----- Append File Name Format in Merchant Name = " + merchantName);

			}

			// Default Previous Three Days

			List<String> previous3dateslist = new ArrayList<String>();

			

			try {

				for (int i = 1; i < 4; i++) {
					LocalDate Date = LocalDate.now().minusDays(i);
					logger.info("Default Previous Three Days = " + Date);
					DateTimeFormatter DatePattern = DateTimeFormatter.ofPattern("ddMMyyyy");
					String FormatDate = Date.format(DatePattern);
					previous3dateslist.add(FormatDate.toString());
				}

				// File Read From ReportPath Based On Merchant Type

				for (String CheckFiledate : previous3dateslist) {

					String TransactionDate = CheckFiledate;
					String FileName = mid1 + merchantName + TransactionDate;
					String MerchantPdfName = ReportPath + mid1 + merchantName + TransactionDate + ".pdf";

					// Encode MerchantPdfName To base64
//				String encodedMerchantPdfName = Base64.getEncoder().encodeToString(MerchantPdfName.getBytes());
//				logger.info("encoded MerchantPdfName = " + encodedMerchantPdfName);

					String PdfName = MerchantPdfName.replace("\\", "-");

					File f = new File(MerchantPdfName);
					logger.info("Merchant PDF Name = " + FileName);

					if (f.exists()) {

						logger.info("PDF Found = " + FileName);

						String FormatFileDate = new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("ddMMyyyy").parse(TransactionDate));

						dateList.add(FormatFileDate);
						nameList.add(PdfName);

					} else {
						logger.info(" PDf Not Found " + FileName);

					}

				}

			} catch (Exception e) {
				logger.error("Error To Read File From ReportPath", e);
				e.printStackTrace();
			}
		}

			PaginationBean<String> paginationBean = new PaginationBean<String>();
			paginationBean.setDateList(dateList);
			paginationBean.setNameList(nameList);
			model.addAttribute("paginationBean", paginationBean);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	// SearchPDF

	@RequestMapping(value = { "/SearchCardPDF" }, method = RequestMethod.GET)
	public String SearchCardPDF(final Model model, final java.security.Principal principal,
			@RequestParam final String fromDate, @RequestParam final String toDate, HttpServletRequest request) {
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/CardDailyReport",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("fromDate  " + fromDate);
		logger.info("toDate  " + toDate);

		String merchantName = null;
		String ReportPath = null;

		String ezyMid = currentMerchant.getMid().getMid();
		String ezyUmMid = currentMerchant.getMid().getUmMid();
		String ezyWayMid = currentMerchant.getMid().getEzywayMid();
		String ezywayUmMid = currentMerchant.getMid().getUmEzywayMid();
		String ezymotoUmMid = currentMerchant.getMid().getUmMotoMid();
		String motoMid = currentMerchant.getMid().getMotoMid();
		String fiuuMid = currentMerchant.getMid().getFiuuMid();

		String mid = null;
		List<String> midList = new ArrayList<String>();
		
		List<String> dateList = new ArrayList<String>();
		// File Name
		List<String> nameList = new ArrayList<String>();

		if (ezyMid != null) {
			midList.add(ezyMid);
		}
		if (ezyUmMid != null) {
			midList.add(ezyUmMid);
		}
		if (ezyWayMid != null) {
			midList.add(ezyWayMid);
		}
		if (ezywayUmMid != null) {
			midList.add(ezywayUmMid);
		}
		if (ezymotoUmMid != null) {
			midList.add(ezymotoUmMid);
		}
		if (motoMid != null) {
			midList.add(motoMid);
		}
		
		if(fiuuMid != null) {
			midList.add(fiuuMid);
		}

		for (String s : midList) {
			System.out.println(s);
			String mid1 = s;

			logger.info("Mid value : " + mid1);

			// To Check Merchant Type is Paydee or Umobile

			if ((currentMerchant.getMerchantType() == null)
					|| (currentMerchant.getMerchantType().equalsIgnoreCase("P"))) {

				ReportPath = "C:\\Mobi_config\\pdf\\";

				String MerchantName = currentMerchant.getBusinessName().toUpperCase();
				merchantName = MerchantName.replace("\\", "").replace(" ", "").replace("/", "") + "_";
				logger.info(" PAYDEE ----- Append File Name Format in Merchant Name = " + merchantName);

			} else if (currentMerchant != null && (currentMerchant.getMerchantType().equalsIgnoreCase("U") || currentMerchant.getMerchantType().equalsIgnoreCase("FIUU"))) {

				ReportPath = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\UMobilePDF\\";

				String MerchantName = currentMerchant.getBusinessShortName().toUpperCase();

				merchantName = "MOBI_" + MerchantName.replace("\\", "").replace(" ", "").replace("/", "") + "_";
				logger.info(" UMOBILE ----- Append File Name Format in Merchant Name = " + merchantName);

			}

			// File Name - Date Format
			

			try {

				String Fromdate = new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
				String Todate = new SimpleDateFormat("yyyy-MM-dd")
						.format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));

				// From and To Date Given By User and Get a List of Dates between Two Dates

				String FromDate = Fromdate;
				String ToDate = Todate;
				LocalDate start = LocalDate.parse(FromDate).minusDays(1);
				LocalDate end = LocalDate.parse(ToDate).minusDays(1);

				// Added List of Dates
				List<String> totalDates = new ArrayList<>();

				while (!start.isAfter(end)) {

					start = start.plusDays(1);
					DateTimeFormatter DatePattern = DateTimeFormatter.ofPattern("ddMMyyyy");

					String FormatDate = start.format(DatePattern);
					totalDates.add(FormatDate);
					logger.info("List Of Dates From and To " + start);

				}

				// File Read From ReportPath Based On From and To

				for (String CheckFiledate : totalDates) {

					String TransactionDate = CheckFiledate;
					String FileName = mid1 + merchantName + TransactionDate;
					String MerchantPdfName = ReportPath + mid1 + merchantName + TransactionDate + ".pdf";

					String PdfName = MerchantPdfName.replace("\\", "-");

					File f = new File(MerchantPdfName);
					if (f.exists()) {

						logger.info("PDF Found = " + FileName);
						logger.info("PDF Found MerchantPdfName = " + MerchantPdfName);

						String FormatFileDate = new SimpleDateFormat("dd-MMM-yyyy")
								.format(new SimpleDateFormat("ddMMyyyy").parse(TransactionDate));

						dateList.add(FormatFileDate);
						nameList.add(PdfName);

					} else {
						logger.info(" PDf Not Found " + FileName);

					}

				}

			}
		
	
			catch (Exception e) {
				logger.error("Error To Read File ReportPath From and To Date", e);
				e.printStackTrace();
			}
		}

			PaginationBean<String> paginationBean = new PaginationBean<String>();
			paginationBean.setDateList(dateList);
			paginationBean.setNameList(nameList);
			model.addAttribute("paginationBean", paginationBean);
			model.addAttribute("pageBean", pageBean);
			model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

//	@RequestMapping(value = { "/Card" }, method = RequestMethod.GET)
//	public String DailyReport(final Model model, final java.security.Principal principal,
//			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
//		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/CardDailyReport",
//				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//		HttpSession session = request.getSession();
//		String myName = (String) session.getAttribute("userName");
//		Merchant currentMerchant = merchantService.loadMerchant(myName);
//		String merchantName = null;
//		String ReportPath = null;
//
//		// To Check Merchant Type is Paydee or Umobile
//
//		if ((currentMerchant.getMerchantType() == null) || (currentMerchant.getMerchantType().equalsIgnoreCase("P"))) {
//
//			ReportPath = "C:\\Mobi_config\\pdf\\";
//
//			String MerchantName = currentMerchant.getBusinessName().toUpperCase();
//			merchantName = MerchantName.replace("\\", "").replace(" ", "").replace("/", "") + "_";
//			logger.info(" PAYDEE ----- Append File Name Format in Merchant Name = " + merchantName);
//
//		} else if (currentMerchant != null && currentMerchant.getMerchantType().equalsIgnoreCase("U")) {
//
//			ReportPath = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\UMobilePDF\\";
//
//			String MerchantName = currentMerchant.getBusinessShortName().toUpperCase();
//
//			merchantName = "MOBI_" + MerchantName.replace("\\", "").replace(" ", "").replace("/", "") + "_";
//			logger.info(" UMOBILE ----- Append File Name Format in Merchant Name = " + merchantName);
//
//		}
//
//		// Default Previous Three Days
//
//		List<String> previous3dateslist = new ArrayList<String>();
//
//		// File Name - Date Format
//		List<String> dateList = new ArrayList<String>();
//		// File Name
//		List<String> nameList = new ArrayList<String>();
//
//		try {
//
//			for (int i = 1; i < 4; i++) {
//				LocalDate Date = LocalDate.now().minusDays(i);
//				logger.info("Default Previous Three Days = " + Date);
//				DateTimeFormatter DatePattern = DateTimeFormatter.ofPattern("ddMMYYYY");
//				String FormatDate = Date.format(DatePattern);
//				previous3dateslist.add(FormatDate.toString());
//			}
//
//			// File Read From ReportPath Based On Merchant Type
//
//			for (String CheckFiledate : previous3dateslist) {
//
//				String TransactionDate = CheckFiledate;
//				String FileName = merchantName + TransactionDate;
//				String MerchantPdfName = ReportPath + merchantName + TransactionDate + ".pdf";
//
//				// Encode MerchantPdfName To base64
////				String encodedMerchantPdfName = Base64.getEncoder().encodeToString(MerchantPdfName.getBytes());
////				logger.info("encoded MerchantPdfName = " + encodedMerchantPdfName);
//
//				String PdfName = MerchantPdfName.replace("\\", "-");
//
//				File f = new File(MerchantPdfName);
//				logger.info("Merchant PDF Name = " + FileName);
//
//				if (f.exists()) {
//
//					logger.info("PDF Found = " + FileName);
//
//					String FormatFileDate = new SimpleDateFormat("dd-MMM-yyy")
//							.format(new SimpleDateFormat("ddMMyyyy").parse(TransactionDate));
//
//					dateList.add(FormatFileDate);
//					nameList.add(PdfName);
//
//				} else {
//					logger.info(" PDf Not Found " + FileName);
//
//				}
//
//			}
//
//		} catch (Exception e) {
//			logger.error("Error To Read File From ReportPath", e);
//			e.printStackTrace();
//		}
//
//		PaginationBean<String> paginationBean = new PaginationBean<String>();
//		paginationBean.setDateList(dateList);
//		paginationBean.setNameList(nameList);
//		model.addAttribute("paginationBean", paginationBean);
//		model.addAttribute("pageBean", pageBean);
//		return TEMPLATE_MERCHANT;
//	}
//
//	// SearchPDF
//
//	@RequestMapping(value = { "/SearchCardPDF" }, method = RequestMethod.GET)
//	public String SearchCardPDF(final Model model, final java.security.Principal principal,
//			@RequestParam final String fromDate, @RequestParam final String toDate, HttpServletRequest request) {
//		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/CardDailyReport",
//				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
//
//		HttpSession session = request.getSession();
//		String myName = (String) session.getAttribute("userName");
//		Merchant currentMerchant = merchantService.loadMerchant(myName);
//
//		logger.info("fromDate  " + fromDate);
//		logger.info("toDate  " + toDate);
//
//		String merchantName = null;
//		String ReportPath = null;
//
//		// To Check Merchant Type is Paydee or Umobile
//
//		if ((currentMerchant.getMerchantType() == null) || (currentMerchant.getMerchantType().equalsIgnoreCase("P"))) {
//
//			ReportPath = "C:\\Mobi_config\\pdf\\";
//
//			String MerchantName = currentMerchant.getBusinessName().toUpperCase();
//			merchantName = MerchantName.replace("\\", "").replace(" ", "").replace("/", "") + "_";
//			logger.info(" PAYDEE ----- Append File Name Format in Merchant Name = " + merchantName);
//
//		} else if (currentMerchant != null && currentMerchant.getMerchantType().equalsIgnoreCase("U")) {
//
//			ReportPath = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\UMobilePDF\\";
//
//			String MerchantName = currentMerchant.getBusinessShortName().toUpperCase();
//
//			merchantName = "MOBI_" + MerchantName.replace("\\", "").replace(" ", "").replace("/", "") + "_";
//			logger.info(" UMOBILE ----- Append File Name Format in Merchant Name = " + merchantName);
//
//		}
//
//		// File Name - Date Format
//		List<String> dateList = new ArrayList<String>();
//		// File Name
//		List<String> nameList = new ArrayList<String>();
//
//		try {
//
//			String Fromdate = new SimpleDateFormat("yyyy-MM-dd")
//					.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
//			String Todate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));
//
//			// From and To Date Given By User and Get a List of Dates between Two Dates
//
//			String FromDate = Fromdate;
//			String ToDate = Todate;
//			LocalDate start = LocalDate.parse(FromDate).minusDays(1);
//			LocalDate end = LocalDate.parse(ToDate).minusDays(1);
//
//			// Added List of Dates
//			List<String> totalDates = new ArrayList<>();
//
//			while (!start.isAfter(end)) {
//
//				start = start.plusDays(1);
//				DateTimeFormatter DatePattern = DateTimeFormatter.ofPattern("ddMMYYYY");
//
//				String FormatDate = start.format(DatePattern);
//				totalDates.add(FormatDate);
//				logger.info("List Of Dates From and To " + start);
//
//			}
//
//			// File Read From ReportPath Based On From and To
//
//			for (String CheckFiledate : totalDates) {
//
//				String TransactionDate = CheckFiledate;
//				String FileName = merchantName + TransactionDate;
//				String MerchantPdfName = ReportPath + merchantName + TransactionDate + ".pdf";
//
//				String PdfName = MerchantPdfName.replace("\\", "-");
//
//				File f = new File(MerchantPdfName);
//				if (f.exists()) {
//
//					logger.info("PDF Found = " + FileName);
//
//					String FormatFileDate = new SimpleDateFormat("dd-MMM-yyy")
//							.format(new SimpleDateFormat("ddMMyyyy").parse(TransactionDate));
//
//					dateList.add(FormatFileDate);
//					nameList.add(PdfName);
//
//				} else {
//					logger.info(" PDf Not Found " + FileName);
//
//				}
//
//			}
//
//		}
//
//		catch (Exception e) {
//			logger.error("Error To Read File ReportPath From and To Date", e);
//			e.printStackTrace();
//		}
//
//		PaginationBean<String> paginationBean = new PaginationBean<String>();
//		paginationBean.setDateList(dateList);
//		paginationBean.setNameList(nameList);
//		model.addAttribute("paginationBean", paginationBean);
//		model.addAttribute("pageBean", pageBean);
//		return TEMPLATE_MERCHANT;
//
//	}

	@RequestMapping(value = { "/ViewCardPDF/{name}" }, method = RequestMethod.GET)
	public String ViewCardPDF(final Model model, final java.security.Principal principal, HttpServletRequest request,
			@RequestParam(required = false, defaultValue = "1") final int currPage, @PathVariable final String name,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		// Decoded MerchantPdfName To base64s

//		byte[] decodedBytes = Base64.getDecoder().decode(name);
//		String ReportPath = new String(decodedBytes);

		String Path = name.replace("-", "\\");
		String ReportPath = Path + ".pdf";

		logger.info("Merchant Request to display PDF:" + ReportPath);

		try {

			Path pdfPath = Paths.get(ReportPath);
			byte[] documentInBytes = Files.readAllBytes(pdfPath);
			response.setHeader("Content-Disposition", "inline; filename=\"report.pdf\"");
			response.setDateHeader("Expires", -1);
			response.setContentType("application/pdf");
			response.setContentLength(documentInBytes.length);
			response.getOutputStream().write(documentInBytes);
		} catch (Exception e) {
			logger.error("Error To View PDF File Name ", e);
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = { "/CardOld" }, method = RequestMethod.GET)
	public String DailyReportold(final Model model, final java.security.Principal principal,
			@RequestParam(required = false, defaultValue = "1") final int currPage, HttpServletRequest request) {
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/CardDailyReportOld",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");
		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);
		String merchantName = null;
		String ReportPath = null;

		// To Check Merchant Type is Paydee or Umobile

		if ((currentMerchant.getMerchantType() == null) || (currentMerchant.getMerchantType().equalsIgnoreCase("P"))) {

			ReportPath = "C:\\Mobi_config\\pdf\\";

			String MerchantName = currentMerchant.getBusinessName().toUpperCase();
			merchantName = MerchantName.replace("\\", "").replace(" ", "").replace("/", "") + "_";
			logger.info(" PAYDEE ----- Append File Name Format in Merchant Name = " + merchantName);

		} else if (currentMerchant != null && currentMerchant.getMerchantType().equalsIgnoreCase("U")) {

			ReportPath = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\UMobilePDF\\";

			String MerchantName = currentMerchant.getBusinessShortName().toUpperCase();

			merchantName = "MOBI_" + MerchantName.replace("\\", "").replace(" ", "").replace("/", "") + "_";
			logger.info(" UMOBILE ----- Append File Name Format in Merchant Name = " + merchantName);

		}

		// Default Previous Three Days

		List<String> previous3dateslist = new ArrayList<String>();

		// File Name - Date Format
		List<String> dateList = new ArrayList<String>();
		// File Name
		List<String> nameList = new ArrayList<String>();

		try {

			for (int i = 1; i < 4; i++) {
				LocalDate Date = LocalDate.now().minusDays(i);
				logger.info("Default Previous Three Days = " + Date);
				DateTimeFormatter DatePattern = DateTimeFormatter.ofPattern("ddMMYYYY");
				String FormatDate = Date.format(DatePattern);
				previous3dateslist.add(FormatDate.toString());
			}

			// File Read From ReportPath Based On Merchant Type

			for (String CheckFiledate : previous3dateslist) {

				String TransactionDate = CheckFiledate;
				String FileName = merchantName + TransactionDate;
				String MerchantPdfName = ReportPath + merchantName + TransactionDate + ".pdf";

				// Encode MerchantPdfName To base64
//                      String encodedMerchantPdfName = Base64.getEncoder().encodeToString(MerchantPdfName.getBytes());
//                      logger.info("encoded MerchantPdfName = " + encodedMerchantPdfName);

				String PdfName = MerchantPdfName.replace("\\", "-");

				File f = new File(MerchantPdfName);
				logger.info("Merchant PDF Name = " + FileName);

				if (f.exists()) {

					logger.info("PDF Found = " + FileName);

					String FormatFileDate = new SimpleDateFormat("dd-MMM-yyy")
							.format(new SimpleDateFormat("ddMMyyyy").parse(TransactionDate));

					dateList.add(FormatFileDate);
					nameList.add(PdfName);

				} else {
					logger.info(" PDf Not Found " + FileName);

				}

			}

		} catch (Exception e) {
			logger.error("Error To Read File From ReportPath", e);
			e.printStackTrace();
		}

		PaginationBean<String> paginationBean = new PaginationBean<String>();
		paginationBean.setDateList(dateList);
		paginationBean.setNameList(nameList);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;
	}

	@RequestMapping(value = { "/SearchCardPDFOld" }, method = RequestMethod.GET)
	public String SearchCardPDFOld(final Model model, final java.security.Principal principal,
			@RequestParam final String fromDate, @RequestParam final String toDate, HttpServletRequest request) {
		PageBean pageBean = new PageBean("transactions list", "merchantweb/transaction/CardDailyReportOld",
				Module.TRANSACTION_WEB, "merchantweb/transaction/sideMenuTransaction");

		HttpSession session = request.getSession();
		String myName = (String) session.getAttribute("userName");
		Merchant currentMerchant = merchantService.loadMerchant(myName);

		logger.info("fromDate  " + fromDate);
		logger.info("toDate  " + toDate);

		String merchantName = null;
		String ReportPath = null;

		// To Check Merchant Type is Paydee or Umobile

		if ((currentMerchant.getMerchantType() == null) || (currentMerchant.getMerchantType().equalsIgnoreCase("P"))) {

			ReportPath = "C:\\Mobi_config\\pdf\\";

			String MerchantName = currentMerchant.getBusinessName().toUpperCase();
			merchantName = MerchantName.replace("\\", "").replace(" ", "").replace("/", "") + "_";
			logger.info(" PAYDEE ----- Append File Name Format in Merchant Name = " + merchantName);

		} else if (currentMerchant != null && currentMerchant.getMerchantType().equalsIgnoreCase("U")) {

			ReportPath = "C:\\Mobi_config\\AutoRun\\Umobile\\SettlementClearence\\UMobilePDF\\";

			String MerchantName = currentMerchant.getBusinessShortName().toUpperCase();

			merchantName = "MOBI_" + MerchantName.replace("\\", "").replace(" ", "").replace("/", "") + "_";
			logger.info(" UMOBILE ----- Append File Name Format in Merchant Name = " + merchantName);

		}

		// File Name - Date Format
		List<String> dateList = new ArrayList<String>();
		// File Name
		List<String> nameList = new ArrayList<String>();

		try {

			String Fromdate = new SimpleDateFormat("yyyy-MM-dd")
					.format(new SimpleDateFormat("dd/MM/yyyy").parse(fromDate));
			String Todate = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(toDate));

			// From and To Date Given By User and Get a List of Dates between Two Dates

			String FromDate = Fromdate;
			String ToDate = Todate;
			LocalDate start = LocalDate.parse(FromDate).minusDays(1);
			LocalDate end = LocalDate.parse(ToDate).minusDays(1);

			// Added List of Dates
			List<String> totalDates = new ArrayList<>();

			while (!start.isAfter(end)) {

				start = start.plusDays(1);
				DateTimeFormatter DatePattern = DateTimeFormatter.ofPattern("ddMMYYYY");

				String FormatDate = start.format(DatePattern);
				totalDates.add(FormatDate);
				logger.info("List Of Dates From and To " + start);

			}

			// File Read From ReportPath Based On From and To

			for (String CheckFiledate : totalDates) {

				String TransactionDate = CheckFiledate;
				String FileName = merchantName + TransactionDate;
				String MerchantPdfName = ReportPath + merchantName + TransactionDate + ".pdf";

				String PdfName = MerchantPdfName.replace("\\", "-");

				File f = new File(MerchantPdfName);
				if (f.exists()) {

					logger.info("PDF Found = " + FileName);

					String FormatFileDate = new SimpleDateFormat("dd-MMM-yyy")
							.format(new SimpleDateFormat("ddMMyyyy").parse(TransactionDate));

					dateList.add(FormatFileDate);
					nameList.add(PdfName);

				} else {
					logger.info(" PDf Not Found " + FileName);

				}

			}

		}

		catch (Exception e) {
			logger.error("Error To Read File ReportPath From and To Date", e);
			e.printStackTrace();
		}

		PaginationBean<String> paginationBean = new PaginationBean<String>();
		paginationBean.setDateList(dateList);
		paginationBean.setNameList(nameList);
		model.addAttribute("paginationBean", paginationBean);
		model.addAttribute("pageBean", pageBean);
		model.addAttribute("loginname", principal.getName());
		return TEMPLATE_MERCHANT;

	}

}
