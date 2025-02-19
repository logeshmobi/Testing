package com.mobiversa.payment.excel;

import com.mobiversa.payment.dto.SettlementDetailsList;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mobiversa.payment.util.PayoutModel;

public class SettlementDetailsPdf extends AbstractPdfView {
    @Override
    protected void buildPdfDocument(Map model, Document doc, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        // Map<String,String> txnList = (Map<String,String>) model.get("txnList");

        List<SettlementDetailsList> txnList = (List<SettlementDetailsList>) model.get("txnList");


        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(100.0f);
        table.setWidths(new float[]{3.0f, 2.0f, 2.0f, 2.0f, 2.0f});
        table.setSpacingBefore(4);


        // define font for table header row
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        // font.setColor(BaseColor.WHITE);

        // define table header cell
        PdfPCell cell = new PdfPCell();
        // cell.setBackgroundColor(BaseColor.BLUE);
        cell.setPadding(5);

        table.addCell("Business Name");
        table.addCell("Previous Balance");
        table.addCell("Settlement Amount");
        table.addCell("Settlement Date");
        table.addCell("Current Balance");

        for (SettlementDetailsList set : txnList) {
            table.addCell(set.getBusinessName());
            table.addCell(set.getPreviousBalance());
            table.addCell(set.getSettlementAmount());
            table.addCell(set.getSettlementDate());
            table.addCell(set.getRunningBalance());
        }
        doc.add(table);
    }
}

