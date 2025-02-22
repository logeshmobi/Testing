package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.payment.util.PreauthModel;

public class PreauthMerchantTxnExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Pre-Auth Transaction List");
		setExcelHeader(excelSheet);

		List txnList = (List) model.get("txnList");
		setExcelRows(excelSheet, txnList);

	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Date");
		excelHeader.createCell(1).setCellValue("Time");
		excelHeader.createCell(2).setCellValue("Amount(RM)");
		excelHeader.createCell(3).setCellValue("Card No");
		excelHeader.createCell(4).setCellValue("TID");
		excelHeader.createCell(5).setCellValue("Status");
		excelHeader.createCell(6).setCellValue("Approval Code");
		excelHeader.createCell(7).setCellValue("Reference");
	}

	public void setExcelRows(HSSFSheet excelSheet, List<PreauthModel> txnList) {
		int record = 1;
		for (PreauthModel txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getTime());
			excelRow.createCell(2).setCellValue(txn.getAmount());
			excelRow.createCell(3).setCellValue(txn.getCardNo());
			excelRow.createCell(4).setCellValue(txn.getTid());
			excelRow.createCell(5).setCellValue(txn.getStatus());
			excelRow.createCell(6).setCellValue(txn.getApprovalCode());
			excelRow.createCell(7).setCellValue(txn.getReference());

		}
	}

}
