package com.mobiversa.payment.excel;

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

public class PayoutSummaryPdf extends AbstractPdfView {

//}
	@Override
	protected void buildPdfDocument(Map model, Document doc, PdfWriter writer, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// Map<String,String> txnList = (Map<String,String>) model.get("txnList");

		List<PayoutModel> txnList = (List<PayoutModel>) model.get("txnList");

		// doc.add(new Paragraph("Mobiversa"));
//		PdfPTable table = new PdfPTable(11);
//		table.setWidthPercentage(100.0f);
//		table.setWidths(new float[] { 2.0f, 2.0f, 2.0f, 2.0f, 3.0f, 2.0f, 2.0f, 2.5f, 2.0f,2.5f,2.0f });
//		table.setSpacingBefore(10);

		
		
		PdfPTable table = new PdfPTable(12);
		table.setWidthPercentage(100.0f);
		table.setWidths(new float[] { 2.0f, 2.0f, 2.0f, 2.0f, 3.0f, 2.0f, 2.0f, 2.5f, 2.0f,2.5f,2.0f, 3.0f });
		table.setSpacingBefore(11);

		
		
		// define font for table header row
		Font font = FontFactory.getFont(FontFactory.HELVETICA);
		// font.setColor(BaseColor.WHITE);

		// define table header cell
		PdfPCell cell = new PdfPCell();
		// cell.setBackgroundColor(BaseColor.BLUE);
		cell.setPadding(5);

		table.addCell("TIME STAMP");
		table.addCell("Merchant Name");
		table.addCell("Customer Name");
		table.addCell("Business Reg No");
		table.addCell("Account No");
		table.addCell("Bank Name");
		table.addCell("Transaction ID");
		table.addCell("Amount");
		table.addCell("Status");
		table.addCell("Date");
		table.addCell("Paid Time");
		table.addCell("Payout Type");

		for (PayoutModel set : txnList) {
			table.addCell(set.getCreateddate());
			table.addCell(set.getCreatedby());
			table.addCell(set.getPayeename());
			table.addCell(set.getPayeebrn());
			table.addCell(set.getPayeeaccnumber());
			table.addCell(set.getPayeebankname());
			table.addCell(set.getInvoiceidproof());
			table.addCell(set.getPayoutamount());
			table.addCell(set.getPayoutstatus());
			table.addCell(set.getPayoutdate());
			table.addCell(set.getPaidTime());
			table.addCell(set.getPayouttype());

		}

		doc.add(table);
	}

}