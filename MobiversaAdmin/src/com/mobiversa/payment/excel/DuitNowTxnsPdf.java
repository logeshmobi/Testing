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
import com.mobiversa.payment.dto.DuitnowTxnDto;
public class DuitNowTxnsPdf extends AbstractPdfView{
	 @Override
	    protected void buildPdfDocument(Map model, Document doc,
	            PdfWriter writer, HttpServletRequest request,
	            HttpServletResponse response) throws Exception {

        List<DuitnowTxnDto> txnList = (List<DuitnowTxnDto>) model.get("duitnowTxnList");
        
        String fileName = "DuitNow_QR_Transactions.pdf";
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");


	        doc.setPageSize(PageSize.A4);
	        doc.open();

	        String logoPath = request.getServletContext().getRealPath("/resourcesNew1/assets/MobiBlueLogo.png");
	        Image logo = Image.getInstance(logoPath);
	        logo.scaleAbsolute(100f, 50f);
	        logo.setAlignment(Image.ALIGN_CENTER); 
	        doc.add(logo); 

	        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14, new Color(0, 91, 170));
	        Paragraph title = new Paragraph("DuitNow QR Transaction Report", titleFont);
	        title.setAlignment(Paragraph.ALIGN_CENTER);
	        title.setSpacingAfter(12);
	        doc.add(title);

	        // Add some spacing after the title
	     //   doc.add(new Phrase("\n"));

	        PdfPTable table = new PdfPTable(8);
	        table.setWidthPercentage(100.0f);
	        table.setWidths(new float[] {2.0f, 2.0f, 3.0f, 2.5f, 3.5f, 3.0f, 2.5f, 2.5f});
	        table.setSpacingBefore(10f);

	        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD , 8,new Color(45, 45, 45));

	        table.addCell(createHeaderCell("Date", headerFont));
	        table.addCell(createHeaderCell("Time", headerFont));
	        table.addCell(createHeaderCell("Amount", headerFont));
	        table.addCell(createHeaderCell("Invoice ID", headerFont));
	        table.addCell(createHeaderCell("Transaction ID", headerFont));
			table.addCell(createHeaderCell("Net Amount", headerFont));
			table.addCell(createHeaderCell("Status", headerFont));
	        table.addCell(createHeaderCell("Payment Date", headerFont));

	        int i = 0;
	        for (DuitnowTxnDto forSet : txnList) {
	            table.addCell(createDataCell(forSet.getCreatedDate(), i));
	            table.addCell(createDataCell(forSet.getCreatedTime(), i));
	            table.addCell(createDataCell(String.valueOf(forSet.getTxnAmount()), i));
	            table.addCell(createDataCell(forSet.getInvoiceId(), i));
	            table.addCell(createDataCell(forSet.getTransactionId(), i));
				table.addCell(createDataCell(String.valueOf(forSet.getNetAmount()), i));
	            table.addCell(createDataCell(forSet.getStatus(), i));
				table.addCell(createDataCell(forSet.getSettlementDate(), i));
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


}