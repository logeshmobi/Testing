package com.mobiversa.payment.excel;

import com.mobiversa.payment.dto.SettlementDetailsList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

import static com.mobiversa.payment.excel.ExportCsvColumn.CURRENT_DAY_BALANCE_HEADER;

import java.io.IOException;
import java.io.OutputStreamWriter;
import com.mobiversa.payment.util.AmountFormatter;
import org.springframework.web.servlet.view.AbstractView;
import com.opencsv.CSVWriter;

public class SettlementDetailsCSV extends AbstractView {

    @Override
    protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"CurrentDayBalance.csv\"");

        List<SettlementDetailsList> txnList = (List<SettlementDetailsList>) model.get("txnList");

        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(response.getOutputStream()))) {
            setCSVHeader(writer);
            setCSVRows(writer, txnList);
        } catch (IOException e) {
            logger.error("Error while writing CSV for SettlementDetailsCSV file", e);
            throw new RuntimeException("Error writing CSV response", e);
        }
    }

    public void setCSVHeader(CSVWriter writer) {
        writer.writeNext(CURRENT_DAY_BALANCE_HEADER.getColumnNames().toArray(new String[0]));
    }

    public void setCSVRows(CSVWriter writer, List<SettlementDetailsList> txnList) throws Exception {
        for (SettlementDetailsList txn : txnList) {

            String[] row = {
                    txn.getBusinessName(),
                    txn.getMerchantId(),
                    !txn.getPreviousBalance().isEmpty() ? AmountFormatter.convertAmountFormat(txn.getPreviousBalance()) : "",
                    !txn.getSettlementAmount().isEmpty() ? AmountFormatter.convertAmountFormat(txn.getSettlementAmount()) : "",
                    txn.getSettlementDate(),
                    !txn.getRunningBalance().isEmpty() ? AmountFormatter.convertAmountFormat(txn.getRunningBalance()) : ""
            };
            writer.writeNext(row);
        }
    }
}
