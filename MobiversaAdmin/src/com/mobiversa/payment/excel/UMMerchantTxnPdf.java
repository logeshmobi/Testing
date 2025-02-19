package com.mobiversa.payment.excel;


import java.awt.Color;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mobiversa.payment.util.UMEzyway;

public class UMMerchantTxnPdf extends AbstractPdfView {
	  @Override
	    protected void buildPdfDocument(Map model, Document doc,
	            PdfWriter writer, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {

	        List<UMEzyway> txnList = (List<UMEzyway>) model.get("umTxnList");

	        doc.setPageSize(PageSize.A4);
	        doc.open();

	        String logoPath = request.getServletContext().getRealPath("/resourcesNew1/assets/MobiBlueLogo.png");
	        Image logo = Image.getInstance(logoPath);
	        logo.scaleAbsolute(100f, 50f); 
	        logo.setAlignment(Image.ALIGN_CENTER); 
	        doc.add(logo); 

	        
	        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new Color(0, 91, 170));
	        Paragraph title = new Paragraph("Card Transaction Report", titleFont);
	        title.setAlignment(Paragraph.ALIGN_CENTER); 
	        title.setSpacingAfter(12);
	        doc.add(title);

	        // Add some spacing after the title
	      //  doc.add(new Phrase("\n"));



	        PdfPTable table = new PdfPTable(8);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {1.5f, 1.5f,1.0f,2.5f, 2.0f, 2.0f,1.0f,2.0f});
	        table.setSpacingBefore(10f);

	        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 10, new Color(45, 45, 45));

	        table.addCell(createHeaderCell("Txn Date", headerFont));
	        table.addCell(createHeaderCell("Amount", headerFont));
	        table.addCell(createHeaderCell("Name on Card", headerFont));
	        table.addCell(createHeaderCell("Invoice ID", headerFont));
	        table.addCell(createHeaderCell("Transaction ID", headerFont));
	        table.addCell(createHeaderCell("Card No", headerFont));
	        table.addCell(createHeaderCell("Card Type", headerFont));
	        table.addCell(createHeaderCell("SubMerchant MID", headerFont));

	        int i = 0;
	        for (UMEzyway forSet : txnList) {
	            table.addCell(createDataCell(forSet.getDate(), i));
	            table.addCell(createDataCell(forSet.getF007_TXNAMT(), i));
	            table.addCell(createDataCell(forSet.getF268_CHNAME(), i));
	            table.addCell(createDataCell(forSet.getF270_ORN(), i));
	            table.addCell(createDataCell(forSet.getF011_AUTHIDRESP(), i));
	            table.addCell(createDataCell(forSet.getPAN(), i));
	            table.addCell(createDataCell(forSet.getCardType(), i));
	            table.addCell(createDataCell(forSet.getSubmerchantmid(), i));
	            i++;
	        }

	        doc.add(table);
	        doc.close();
	    }

	    private static PdfPCell createHeaderCell(String text, Font font) {
	        PdfPCell cell = new PdfPCell(new Phrase(text, font));
	        cell.setPaddingTop(8); 
	        cell.setPaddingBottom(8); 
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
	        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 
	        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM); 
	        cell.setBorderColor(new Color(211, 211, 211)); 
	        cell.setBackgroundColor(new Color(211, 211, 211));  
	        return cell;
	    }

	    private static PdfPCell createDataCell(String text, int index) {
	        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 8);
	        PdfPCell cell = new PdfPCell(new Phrase(text, dataFont));
	        cell.setPaddingTop(9);
	        cell.setPaddingBottom(9); 
	        cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
	        cell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 
	        cell.setBorder(Rectangle.TOP | Rectangle.BOTTOM); 
	        cell.setBorderColor(new Color(192, 192, 192)); 
	        return cell;
	    }
//	
//
////}
//	
//	@Override
//	protected void buildPdfDocument(Map model, Document doc,
//		PdfWriter writer, HttpServletRequest request,
//		HttpServletResponse response) throws Exception {
//		
//		//Map<String,String> txnList = (Map<String,String>) model.get("txnList");
//		
//		List<UMEzyway> txnList = (List<UMEzyway>) model.get("umTxnList");
//		
//		// doc.add(new Paragraph("Mobiversa"));
//		 PdfPTable table = new PdfPTable(8);
//	        table.setWidthPercentage(100.0f);
//	        table.setWidths(new float[] {1.5f, 1.5f, 2.5f, 2.0f, 2.0f,1.0f,1.0f,2.5f});
//	        table.setSpacingBefore(10);
//	         
//	        // define font for table header row
//	        Font font = FontFactory.getFont(FontFactory.HELVETICA);
//	        //font.setColor(BaseColor.WHITE);
//	        
//	         
//	        // define table header cell
//	        PdfPCell cell = new PdfPCell();
//	        //cell.setBackgroundColor(BaseColor.BLUE);
//	        cell.setPadding(1);
//	        
//	        table.addCell("Transaction Date");
//			table.addCell("Amount");
//			table.addCell("Name on Card");
//			table.addCell("Reference");
//			table.addCell("Approve Code");
//			table.addCell("Card No");
//			table.addCell("Card Type");
//			table.addCell("Sub Merchant MID");
//			
//			
//
//			 for (UMEzyway forSet : txnList) {
//		            table.addCell(forSet.getDate());
//		            table.addCell(forSet.getF007_TXNAMT());
//		            table.addCell(forSet.getF268_CHNAME());
//		            table.addCell(forSet.getF270_ORN());
//		            table.addCell(forSet.getF011_AUTHIDRESP());
//		            table.addCell(forSet.getPAN());
//		            table.addCell(forSet.getCardType());
//		            table.addCell(forSet.getSubmerchantmid());
//		            
//		         //   table.addCell(forSet.getMerchantName());
//		          
//		        }
//
//			doc.add(table);
//		}
        
}