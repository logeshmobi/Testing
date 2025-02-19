package com.mobiversa.payment.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.mobiversa.payment.util.SettlementModel;

public class SettlementSummaryExcel extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HSSFSheet excelSheet = workbook.createSheet("Transaction List");
		setExcelHeader(excelSheet);

		List txnList = (List) model.get("txnList");
		setExcelRows(excelSheet, txnList);

	}

	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Date");
		excelHeader.createCell(1).setCellValue("Amount(RM)");
		excelHeader.createCell(2).setCellValue("Net Amount");
		excelHeader.createCell(3).setCellValue("Host MDR");
		excelHeader.createCell(4).setCellValue("Mobi MDR");
		excelHeader.createCell(5).setCellValue("MID");
		excelHeader.createCell(6).setCellValue("Masked Pan");
		excelHeader.createCell(7).setCellValue("Status");
		excelHeader.createCell(8).setCellValue("RRN");
		excelHeader.createCell(9).setCellValue("Invoice ID");
		excelHeader.createCell(10).setCellValue("Payment Method");

	}

	public void setExcelRows(HSSFSheet excelSheet, List<SettlementModel> txnList) {
		int record = 1;
		for (SettlementModel txn : txnList) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(txn.getDate());
			excelRow.createCell(1).setCellValue(txn.getTxnAmount());
			excelRow.createCell(2).setCellValue(txn.getNetAmount());
			excelRow.createCell(3).setCellValue(txn.getHostMdrAmt());
			excelRow.createCell(4).setCellValue(txn.getMobiMdrAmt());
			excelRow.createCell(5).setCellValue(txn.getMid());
			excelRow.createCell(6).setCellValue(txn.getMaskedPan());
			excelRow.createCell(7).setCellValue(txn.getStatus());
			excelRow.createCell(8).setCellValue(txn.getRrn());
			excelRow.createCell(9).setCellValue(txn.getInvoiceId());
			excelRow.createCell(10).setCellValue(txn.getCardBrand() + " " + txn.getCardType());

		}
	}

}
