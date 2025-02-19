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
import com.mobiversa.common.bo.MobiProductMDR;

public class UMChargeBackPDF extends AbstractPdfView {
	
	

//}
	
	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		//Map<String,String> txnList = (Map<String,String>) model.get("txnList");
		
		List<MobiProductMDR> txnList = (List<MobiProductMDR>) model.get("txnList");
		
		// doc.add(new Paragraph("Mobiversa"));
		 PdfPTable table = new PdfPTable(6);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {2.0f, 2.0f, 2.0f, 2.0f, 3.0f,2.0f});
	        table.setSpacingBefore(10);
	         
	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        //font.setColor(BaseColor.WHITE);
	         
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        //cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(5);
        


		table.addCell("Date");
		table.addCell("MID");
		table.addCell("Status");
		table.addCell("Amount");		
		

	
		
		
		 for (MobiProductMDR forSet : txnList) {
	            table.addCell(forSet.getTimeStamp());
	            table.addCell(forSet.getMid());
	            table.addCell(forSet.getStatus().name());
	            table.addCell(forSet.getHostMdr());
	       
	        
	        }

		doc.add(table);
	}
	
	
	
	
	
	
	
}