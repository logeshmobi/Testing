package com.mobiversa.payment.excel;

import static com.mobiversa.payment.excel.ExportCsvColumn.PAYOUTLOGIN_EXPORT_HEADER;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import com.mobiversa.payment.util.PayoutModel;
import com.opencsv.CSVWriter;

public class PayoutLoginSummaryExcel extends AbstractView{
	
	
	
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
	        writer.writeNext(PAYOUTLOGIN_EXPORT_HEADER.getColumnNames().toArray(new String[0]));
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
