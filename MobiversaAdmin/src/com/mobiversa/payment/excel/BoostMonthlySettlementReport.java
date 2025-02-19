package com.mobiversa.payment.excel;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.BoostDailyRecon;

public class BoostMonthlySettlementReport extends AbstractExcelView{


		@Override
		protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {

			logger.info("BoostMonthlySettlementReport");
			
			HSSFSheet excelSheet = workbook.createSheet("Settlement List for Boost");
			setExcelHeader(excelSheet);
			
			List txnList = (List)model.get("txnList3");
			setExcelRows(excelSheet,txnList);
			
		}

		@SuppressWarnings("static-method")
		public void setExcelHeader(HSSFSheet excelSheet) {
			HSSFRow excelHeader = excelSheet.createRow(0);
			excelHeader.createCell(0).setCellValue("DATE");
			excelHeader.createCell(1).setCellValue("ONLINEPTRTXNID");
			excelHeader.createCell(2).setCellValue("TXNAMOUNT");
			excelHeader.createCell(3).setCellValue("HOST_AMT");
			excelHeader.createCell(4).setCellValue("MOBI_MDR_AMT");
			excelHeader.createCell(5).setCellValue("MERCHANT_MDR");
			excelHeader.createCell(6).setCellValue("NETAMOUNT");
			excelHeader.createCell(7).setCellValue("SETTLE_DATE");

		}
		
		@SuppressWarnings("static-method")
		public void setExcelRows(HSSFSheet excelSheet, List<BoostDailyRecon> txnList){
			int record = 1;
			 for (BoostDailyRecon txn : txnList) {
			        HSSFRow excelRow = excelSheet.createRow(record++);
			        excelRow.createCell(0).setCellValue(txn.getDate()); // MID
			        excelRow.createCell(1).setCellValue(txn.getOnlinePtrTxnID()); // TID
			        excelRow.createCell(2).setCellValue(txn.getTxnAmount()); // TX_DATE
			        excelRow.createCell(3).setCellValue(txn.getMdrAmount()); // TXNAMOUNT
			        excelRow.createCell(4).setCellValue(txn.getMdrRebateAmount()); // MDR_AMT
			        excelRow.createCell(5).setCellValue(txn.getMdrRate()); // MOBI_MDR_AMT
			        excelRow.createCell(6).setCellValue(txn.getNetAmount()); // HOST_MDR_AMT
			        excelRow.createCell(7).setCellValue(txn.getSettleDate()); // PAYABLEAMT
			    
			    }
		}


	}


