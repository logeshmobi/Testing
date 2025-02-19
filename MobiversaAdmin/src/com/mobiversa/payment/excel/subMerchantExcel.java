package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.common.bo.Merchant;

public class subMerchantExcel extends AbstractExcelView {
	
	  	@Override
	    protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
	                                      HttpServletResponse response) throws Exception {

	        HSSFSheet excelSheet = workbook.createSheet("subMerchantList");
	        setExcelHeader(excelSheet);

	        logger.info("Start Excel");
	        @SuppressWarnings("unchecked")
			List<Merchant> merchantList = (List<Merchant>) model.get("subMerchantList");
	        logger.info("merchantList :"+merchantList);
	        setExcelRows(excelSheet, merchantList);
	    }

	    public void setExcelHeader(HSSFSheet excelSheet) {
	        HSSFRow excelHeader = excelSheet.createRow(0);
	        excelHeader.createCell(0).setCellValue("Activate Date");
	        excelHeader.createCell(1).setCellValue("Business Name");
	        excelHeader.createCell(2).setCellValue("Email");
	        excelHeader.createCell(3).setCellValue("City");
	        excelHeader.createCell(4).setCellValue("State");
	        excelHeader.createCell(5).setCellValue("Main Merchant");
	        excelHeader.createCell(6).setCellValue("Sub Merchant MID");
	    }

	    public void setExcelRows(HSSFSheet excelSheet, List<Merchant> merchantList) {
	        int record = 1;
	        for (Merchant merchant : merchantList) {
	            HSSFRow excelRow = excelSheet.createRow(record++);
	            logger.info("date :"+merchant.getCreatedBy());
	            
	            excelRow.createCell(0).setCellValue(merchant.getCreatedBy());
	            excelRow.createCell(1).setCellValue(merchant.getBusinessName());
	            excelRow.createCell(2).setCellValue(merchant.getEmail());
	            excelRow.createCell(3).setCellValue(merchant.getCity());
	            excelRow.createCell(4).setCellValue(merchant.getState());
	            excelRow.createCell(5).setCellValue(merchant.getMmId());
				excelRow.createCell(6).setCellValue(merchant.getMobiId()); 
	        }
	    }

}

