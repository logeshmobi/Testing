//package com.mobiversa.payment.excel;
//
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.poi.hssf.usermodel.HSSFRow;
//import org.apache.poi.hssf.usermodel.HSSFSheet;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.springframework.web.servlet.view.document.AbstractExcelView;
//
//import com.mobiversa.payment.util.PayoutModel;
//
//public class PayoutSummaryExcel extends AbstractExcelView {
//
//	@Override
//	protected void buildExcelDocument(Map model, HSSFWorkbook workbook, HttpServletRequest request,
//			HttpServletResponse response) throws Exception {
//
//		HSSFSheet excelSheet = workbook.createSheet("PAYOUT_Report");
//		setExcelHeader(excelSheet);
//
//		List txnList = (List) model.get("txnList");
//		setExcelRows(excelSheet, txnList);
//
//	}
//
//	public void setExcelHeader(HSSFSheet excelSheet) {
//		HSSFRow excelHeader = excelSheet.createRow(0);
//		excelHeader.createCell(0).setCellValue("TIME STAMP");
//		excelHeader.createCell(1).setCellValue("Merchant Name");
//		excelHeader.createCell(2).setCellValue("Customer Name");
//		excelHeader.createCell(3).setCellValue("BRN/IC");
//		excelHeader.createCell(4).setCellValue("Account No");
//		excelHeader.createCell(5).setCellValue("Bank Name");
//		excelHeader.createCell(6).setCellValue("Transaction_ID");
//		excelHeader.createCell(7).setCellValue("Amount");
//		excelHeader.createCell(8).setCellValue("Payout Fee");
//		excelHeader.createCell(9).setCellValue("Status");
//		excelHeader.createCell(10).setCellValue("Date");
//		excelHeader.createCell(11).setCellValue("Paid Time");
//		excelHeader.createCell(12).setCellValue("Paid Date");
//		excelHeader.createCell(13).setCellValue("Submerchant Mid");
//		excelHeader.createCell(14).setCellValue("Submerchant Name");
//		excelHeader.createCell(15).setCellValue("Payout Id");
//		excelHeader.createCell(16).setCellValue("Decline Reason");
//		excelHeader.createCell(17).setCellValue("Payout Type");
//	}
//
//	public void setExcelRows(HSSFSheet excelSheet, List<PayoutModel> txnList) {
//		int record = 1;
//		for (PayoutModel txn : txnList) {
//			HSSFRow excelRow = excelSheet.createRow(record++);
//			excelRow.createCell(0).setCellValue(txn.getCreateddate());
//			excelRow.createCell(1).setCellValue(txn.getCreatedby());
//			excelRow.createCell(2).setCellValue(txn.getPayeename());
//			excelRow.createCell(3).setCellValue(txn.getPayeebrn());
//			excelRow.createCell(4).setCellValue(txn.getPayeeaccnumber());
//			excelRow.createCell(5).setCellValue(txn.getPayeebankname());
//			excelRow.createCell(6).setCellValue(txn.getInvoiceidproof());
//			excelRow.createCell(7).setCellValue(txn.getPayoutamount());
//			excelRow.createCell(8).setCellValue(txn.getPayoutfee());
//			excelRow.createCell(9).setCellValue(txn.getPayoutstatus());
//			excelRow.createCell(10).setCellValue(txn.getPayoutdate());
//			excelRow.createCell(11).setCellValue(txn.getPaidTime());
//			excelRow.createCell(12).setCellValue(txn.getPaidDate());
//			excelRow.createCell(13).setCellValue(txn.getSubmerchantMid());
//			excelRow.createCell(14).setCellValue(txn.getMmId());
//			excelRow.createCell(15).setCellValue(txn.getPayoutId());
//			excelRow.createCell(16).setCellValue(txn.getFailurereason());
//			excelRow.createCell(17).setCellValue(txn.getPayouttype());
//		}
//	}
//}
package com.mobiversa.payment.excel;

import static com.mobiversa.payment.excel.ExportCsvColumn.PAYOUT_EXPORT_HEADER;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.mobiversa.payment.util.PayoutModel;
import com.opencsv.CSVWriter;

public class PayoutSummaryExcel extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"PAYOUT_Report.csv\"");

        List<PayoutModel> txnList = (List<PayoutModel>) model.get("txnList");

        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(response.getOutputStream()))) {
            setCSVHeader(writer);
            setCSVRows(writer, txnList);
        } catch (IOException e) {
            throw new RuntimeException("Error writing CSV response", e);
        }
    }

    public void setCSVHeader(CSVWriter writer) {
        writer.writeNext(PAYOUT_EXPORT_HEADER.getColumnNames().toArray(new String[0]));
    }

    public void setCSVRows(CSVWriter writer, List<PayoutModel> txnList) {
        for (PayoutModel txn : txnList) {
            String[] row = {
                    txn.getCreateddate(),
                    txn.getCreatedby(),
                    txn.getPayeename(),
                    txn.getPayeebrn(),
                    txn.getPayeeaccnumber(),
                    txn.getPayeebankname(),
                    txn.getInvoiceidproof(),
                    txn.getPayoutamount(),
                    txn.getPayoutfee(),
                    txn.getPayoutstatus(),
                    txn.getPayoutdate(),
                    txn.getPaidTime(),
                    txn.getPaidDate(),
                    txn.getSubmerchantMid(),
                    txn.getMmId(),
                    txn.getPayoutId(),
                    txn.getFailurereason(),
                    txn.getPayouttype(),
                    txn.getCurlecRefNo()
            };
            writer.writeNext(row);
        }
    }
}