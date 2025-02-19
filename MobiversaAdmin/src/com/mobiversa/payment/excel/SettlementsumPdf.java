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
import com.mobiversa.common.bo.SettlementMDR;

public class SettlementsumPdf extends AbstractPdfView {

	
	@Override
	protected void buildPdfDocument(Map model, Document doc,
		PdfWriter writer, HttpServletRequest request,
		HttpServletResponse response) throws Exception {
		
		//Map<String,String> txnList = (Map<String,String>) model.get("txnList");
		
		List<SettlementMDR> txnList = (List<SettlementMDR>) model.get("txnList");
		
		// doc.add(new Paragraph("Mobiversa"));
		 PdfPTable table = new PdfPTable(9);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {1.5f, 1.5f, 2.5f, 2.0f, 2.0f,1.0f , 2.0f,1.0f ,1.0f});
	        table.setSpacingBefore(10);
	         
	        // define font for table header row
	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
	        //font.setColor(BaseColor.WHITE);
	         
	        // define table header cell
	        PdfPCell cell = new PdfPCell();
	        //cell.setBackgroundColor(BaseColor.BLUE);
	        cell.setPadding(1);
	        
//	        table.addCell("Settlement Date");
//			table.addCell("MID");
//			table.addCell("Host MDR");
//			table.addCell("Mobi MDR");
//			table.addCell("Transaction amount");
//			
//			
//
//			 for (FpxTransaction forSet : txnList) {
//		           table.addCell(forSet.getSettledDate());
//		            table.addCell(forSet.getMid());
//		            table.addCell(forSet.getHostMdrAmt());
//		            table.addCell(forSet.getMobiMdrAmt());
//		            table.addCell(forSet.getTxnAmount());
//		         
//		            
//		        
//		          
//		        }
			 
			 
	            table.addCell("TxnDate");
	            table.addCell("Mid");
				table.addCell("Tid");
				table.addCell("TxnAmount");
				table.addCell("NetAmount");
				table.addCell("MdrAmount");
				table.addCell("SettlementDate");
				table.addCell("InvoiceId");
				table.addCell("Status");
				
				

				 for (SettlementMDR settlesum : txnList) {
			        
			            table.addCell(settlesum.getDate());
			            table.addCell(settlesum.getMid());
			            table.addCell(settlesum.getTid());
			            table.addCell(settlesum.getTxnAmount());
			            table.addCell(settlesum.getNetAmount());
			            table.addCell(settlesum.getMdrAmt());
			            table.addCell(settlesum.getSettlementDate());
			            table.addCell(settlesum.getInvoiceId());
			            table.addCell(settlesum.getStatus());
			         
			            
			          
			        }

			 
			 
			 

			doc.add(table);
		}
        
}
