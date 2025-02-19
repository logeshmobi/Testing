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
import com.mobiversa.common.bo.ForSettlement;

public class AllTxnListPdfView extends AbstractPdfView {
	
	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		List<ForSettlement> txnList = (List<ForSettlement>) model.get("txnList");
		
		 PdfPTable table = new PdfPTable(8);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {2.5f, 1.5f, 3.0f, 2.0f, 2.0f, 2.0f, 2.0f,2.0f});
	        table.setSpacingBefore(10);
	         
	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        //font.setColor(BaseColor.WHITE);
	         
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        //cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(5);
        
	
	        table.addCell("Date");
			table.addCell("Time");
			/*table.addCell("MID");*/
			table.addCell("Business Name");
			table.addCell("MID");
			table.addCell("Status");
			table.addCell("Amount");
			table.addCell("DeviceHolder Name");
			table.addCell("Location");

			
		 for (ForSettlement forSet : txnList) {
	            table.addCell(forSet.getDate());
	            table.addCell(forSet.getTime());
	           /* table.addCell(forSet.getMid());*/
	            table.addCell(forSet.getNumOfSale());
	            table.addCell(forSet.getMid());
	            table.addCell(forSet.getStatus());
	            table.addCell(forSet.getAmount());
	            table.addCell(forSet.getMerchantName());
	            table.addCell(forSet.getLocation());
	        }

		doc.add(table);
	}
	
	

}
