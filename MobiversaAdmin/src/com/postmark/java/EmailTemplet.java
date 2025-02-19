package com.postmark.java;

import com.mobiversa.payment.dto.TempletFields;

public class EmailTemplet {
	
	public static String sentRefundTempletContent(TempletFields templetFields) {

		return  "<!DOCTYPE html>"
				+ "<html>"

				+ "<head>"
				    + "<title></title>"
				    + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
				    + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
				    + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />"
				    + "<style type=\"text/css\">"
				        + "@media screen {"
								            + "@font-face {"
								                + "font-family: 'Lato';"
								                + "font-style: normal;"
								                + "font-weight: 400;"
								                + "src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');"
								            + "}"

								            + "@font-face {"
								                + "font-family: 'Lato';"
								                + "font-style: normal;"
								                + "font-weight: 700;"
								                + "src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');"
								            + "}"

								            + "@font-face {"
								                + "font-family: 'Lato';"
								                + "font-style: italic;"
								                + "font-weight: 400;"
								                + "src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');"
								            + "}"

								            + "@font-face {"
								                + "font-family: 'Lato';"
								                + "font-style: italic;"
								                + "font-weight: 700;"
								                + "src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');"
								            + "}"
								        + "}"

								        + "/* CLIENT-SPECIFIC STYLES */"
								        + "body,"
								        + "table,"
								        + "td,"
								        + "a {"
								            + "-webkit-text-size-adjust: 100%;"
								            + "-ms-text-size-adjust: 100%;"
								        + "}"

								        + "table,"
								        + "td {"
								            + "mso-table-lspace: 0pt;"
								            + "mso-table-rspace: 0pt;"
								        + "}"

								        + "img {"
								            + "-ms-interpolation-mode: bicubic;"
								        + "}"

								        + "/* RESET STYLES */"
								        + "img {"
								            + "border: 0;"
								            + "height: auto;"
								            + "line-height: 100%;"
								            + "outline: none;"
								            + "text-decoration: none;"
								        + "}"

								        + "table {"
								            + "border-collapse: collapse !important;"
								        + "}"

								        + "body {"
								            + "height: 100% !important;"
								            + "margin: 0 !important;"
								            + "padding: 0 !important;"
								            + "width: 100% !important;"
								        + "}"
										
										

								        + "/* iOS BLUE LINKS */"
								        + "a[x-apple-data-detectors] {"
								            + "color: inherit !important;"
								            + "text-decoration: none !important;"
								            + "font-size: inherit !important;"
								            + "font-family: inherit !important;"
								            + "font-weight: inherit !important;"
								            + "line-height: inherit !important;"
								        + "}"

								        + "/* MOBILE STYLES */"
								        + "@media screen and (max-width:600px) {"
								            + "h1 {"
								                + "font-size: 32px !important;"
								                + "line-height: 32px !important;"
								            + "}"
								            + "p {"
							                	+ "font-size: 15px !important;"
							                	+ "line-height: 15px !important;"
							                + "}"
							               
								        + "}"

								        + "/* ANDROID CENTER FIX */"
								        + "div[style*=\"margin: 16px 0;\"] {"
								            + "margin: 0 !important;"
								        + "}"
				    + "</style>"
				+ "</head>"

				+ "<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">"
				    + "<!-- HIDDEN PREHEADER TEXT -->"
				    + "<div style=\"display: none; font-size: 1px; color: #fefefe; line-height: 1px; font-family: 'Lato', Helvetica, Arial, sans-serif; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden;\">We're happy to have you here! Get ready to do easy contactless payment.</div>"
				    + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
				        + "<!-- LOGO -->"
				        + "<tr>"
				            + "<td bgcolor=\"#195ba8\" align=\"center\">"
				                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">"
				                    + "<tr>"
				                        + "<td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\"></td>"
				                    + "</tr>"
				                + "</table>"
				            + "</td>"
				        + "</tr>"
				        + "<tr>"
				            + "<td bgcolor=\"#195ba8\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">"
				                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">"
				                    + "<tr>"
				                        + "<td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">"
				                            + "<img src=\"cid:otp_logo\"	style=\"display: block; border: 0px; width:100%\" />"
				                        + "</td>"
				                    + "</tr>"
				                + "</table>"
				            + "</td>"
				        + "</tr>"
				        + "<tr>"
				            + "<td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">"
				                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\">"
				                    + "<tr>"
				                        + "<td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 55px 0px 55px; color: #195ba8; \">"
//				                            + "<h3 >Hi "+ templetFields.getFirstName()+"</h3>"
//											+ "<p> "+ templetFields.getSalutation() + ". "+ templetFields.getFirstName()+"</p>"
											+ "<p> Below are the refund transaction details.</p>"
				                            + "<p  style=\"font-size: 16px; \"><b>DATE         :</b><span>"+ templetFields.getDate()+"</span></p>"
				                            + "<p  style=\"font-size: 16px; \"><b>MID          :</b><span>"+ templetFields.getMid()+"</span></p>"
//				                            + "<p  style=\"font-size: 16px; \"><b>APPROVE CODE :</b><span>"+ templetFields.getRefNo()+"</span></p>"
				                            + "<p  style=\"font-size: 16px; \"><b>PAN          :</b><span>"+ templetFields.getMaskedPan()+"</span></p>"
				                            + "<p  style=\"font-size: 16px; \"><b>AMOUNT       :</b><span>"+ templetFields.getAmount()+"</span></p>"
//				                            + "<p> In case you need any further assistance, please contact <i>csmobi@gomobi.io</i> </p>"
				                            + "<p> Regards,<br> Team Mobi.</p>"
										+ "</td>"
				                    + "</tr>"
									
									/*+ "<tr>"
				                        + "<td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 55px 0px 55px; color: #195ba8; \">"
				                            + "<div  align=\"center\" style=\"border-radius: 7px;  border: 2px dashed #005BAAB3; \">"
											+ "<p>Amount Requested</p>"					
											+ "<h1>RM "+ templetFields.getAmount()+"</h1>"
											+ "</div>"	
										+ "</td>"
				                    + "</tr>"*/
										
				                    /*+ "<tr>"
			                        + "<td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 55px 0px 55px; color: #195ba8; \">"

										+ "<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" >"
										   + "<tr >"
												+ "<td align=\"center\" style=\"border-radius: 7px;  border: 2px dashed #005BAAB3;\">"
													+ "<pre style=\"padding: 0px 180px 0px 180px;font-family: auto;\">Amount Requested</pre>"					
													+ "<h1>RM "+ templetFields.getAmount()+" </h1>"
												+ "</td>"	
											+ "</tr>" 
										+ "</table>"
										
									+ "</td>"
			                    + "</tr>"*/
									
			                    

									
									
									
				                + "</table>"
				            + "</td>"
				        + "</tr>"
				    + "</table>"
				+ "</body>"

				+ "</html>";
	}
	
	
}
