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
import com.mobiversa.common.bo.BizAppSettlement;

public class bizappMerchantSummaryPdf extends AbstractPdfView {
	
	

//}
	
	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		//Map<String,String> txnList = (Map<String,String>) model.get("txnList");
		
		List<BizAppSettlement> txnList = (List<BizAppSettlement>) model.get("settlementMDRList");
		
		// doc.add(new Paragraph("Mobiversa"));
		 PdfPTable table = new PdfPTable(7);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {1.5f, 1.5f, 2.5f, 2.0f, 2.0f,1.0f,1.0f});
	        table.setSpacingBefore(10);
	         
	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        //font.setColor(BaseColor.WHITE);
	         
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        //cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(1);
	        
	        table.addCell("Settlement Date");
			table.addCell("Bank Name");
			table.addCell("Transaction Amount");
			table.addCell("Host MDR");
			table.addCell("Mobi MDR");
			table.addCell("Merchnat MDR");
			table.addCell("Detection Amount");
			table.addCell("Net Amount");
			

			 for (BizAppSettlement forSet : txnList) {
		           table.addCell(forSet.getSettlementDate());
		            table.addCell(forSet.getBankName());
		            table.addCell(forSet.getGrossAmt());
		            table.addCell(forSet.getHostMdrAmt());
		            table.addCell(forSet.getMobiMdrAmt());
		            table.addCell(forSet.getMdrAmt());
		            table.addCell(forSet.getDetectionAmt());
		            table.addCell(forSet.getNetAmt());
		            
		        
		        }

			doc.add(table);
		}
        
}