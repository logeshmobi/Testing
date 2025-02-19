package com.postmark.java;

public class PromoEmailTemplet {

	public static String sentTempletContent(PromoTempletFields templetFields) {

		return "<!DOCTYPE html>"
				+ "<html xmlns:fb=\"http://www.facebook.com/2008/fbml\" xmlns:og=\"http://opengraph.org/schema/\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">"
				+ "<meta property=\"og:title\" content=\"Account Creation\">"
				+ "<meta property=\"fb:page_id\" content=\"43929265776\">"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1\">"
				+ "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">"
				+ "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
				+ "		<title>Account Creation</title>"
				+ "    <style type=\"text/css\">" + "		p{"
				+ "			margin:10px 0;" + "			padding:0;" + "		}"
				+ "		table{" + "			border-collapse:collapse;"
				+ "		}" + "		h1,h2,h3,h4,h5,h6{"
				+ "			display:block;" + "			margin:0;"
				+ "			padding:0;" + "		}" + "		img,a img{"
				+ "			border:0;" + "			height:auto;"
				+ "			outline:none;" + "			text-decoration:none;"
				+ "		}" + "		body,#bodyTable,#bodyCell{"
				+ "			height:100%;" + "			margin:0;"
				+ "			padding:0;" + "			width:100%;" + "		}"
				+ "		#outlook a{" + "			padding:0;" + "		}"
				+ "		img{" + "			-ms-interpolation-mode:bicubic;"
				+ "		}" + "		table{" + "			mso-table-lspace:0pt;"
				+ "			mso-table-rspace:0pt;" + "		}"
				+ "		.ReadMsgBody{" + "			width:100%;" + "		}"
				+ "		.ExternalClass{" + "			width:100%;"
				+ "		}" + "		p,a,li,td,blockquote{"
				+ "			mso-line-height-rule:exactly;" + "		}"
				+ "		a[href^=tel],a[href^=sms]{" + "			color:inherit;"
				+ "			cursor:default;"
				+ "			text-decoration:none;" + "		}"
				+ "		p,a,li,td,body,table,blockquote{"
				+ "			-ms-text-size-adjust:100%;"
				+ "			-webkit-text-size-adjust:100%;" + "		}"
				+ "		.ExternalClass,.ExternalClass p,.ExternalClass td,.ExternalClass div,.ExternalClass span,.ExternalClass font{"
				+ "			line-height:100%;" + "		}"
				+ "		a[x-apple-data-detectors]{"
				+ "			color:inherit !important;"
				+ "			text-decoration:none !important;"
				+ "			font-size:inherit !important;"
				+ "			font-family:inherit !important;"
				+ "			font-weight:inherit !important;"
				+ "			line-height:inherit !important;" + "		}"
				+ "		#bodyCell{" + "			padding:10px;" + "		}"
				+ "		a.mcnButton{" + "			display:block;" + "		}"
				+ "		.mcnImage{" + "			vertical-align:bottom;"
				+ "		}" + "		.mcnTextContent{"
				+ "			word-break:break-word;" + "		}"
				+ "		.mcnTextContent img{"
				+ "			height:auto !important;" + "		}"
				+ "		.mcnDividerBlock{"
				+ "			table-layout:fixed !important;" + "		}"
				+ "		body,#bodyTable{"
				+ "			background-color:#FAFAFA;" + "		}"
				+ "		#bodyCell{" + "			border-top:0;" + "		}"
				+ "		h1{" + "			color:#202020;"
				+ "			font-family:Helvetica;" + "			font-size:26px;"
				+ "			font-style:normal;" + "			font-weight:bold;"
				+ "			line-height:125%;"
				+ "			letter-spacing:normal;"
				+ "			text-align:left;" + "		}" + "		h2{"
				+ "			color:#202020;" + "			font-family:Helvetica;"
				+ "			font-size:22px;" + "			font-style:normal;"
				+ "			font-weight:bold;" + "			line-height:125%;"
				+ "			letter-spacing:normal;"
				+ "			text-align:left;" + "		}" + "		h3{"
				+ "			color:#202020;" + "			font-family:Helvetica;"
				+ "			font-size:20px;" + "			font-style:normal;"
				+ "			font-weight:bold;" + "			line-height:125%;"
				+ "			letter-spacing:normal;"
				+ "			text-align:left;" + "		}" + "		h4{"
				+ "			color:#202020;" + "			font-family:Helvetica;"
				+ "			font-size:18px;" + "			font-style:normal;"
				+ "			font-weight:bold;" + "			line-height:125%;"
				+ "			letter-spacing:normal;"
				+ "			text-align:left;" + "		}"
				+ "		#templatePreheader{"
				+ "			background-color:#FAFAFA;"
				+ "			border-top:0;" + "			border-bottom:0;"
				+ "			padding-top:9px;" + "			padding-bottom:9px;"
				+ "		}"
				+ "		#templatePreheader .mcnTextContent,#templatePreheader .mcnTextContent p{"
				+ "			color:#656565;" + "			font-family:Helvetica;"
				+ "			font-size:12px;" + "			line-height:150%;"
				+ "			text-align:left;" + "		}"
				+ "		#templatePreheader .mcnTextContent a,#templatePreheader .mcnTextContent p a{"
				+ "			color:#656565;" + "			font-weight:normal;"
				+ "			text-decoration:underline;" + "		}"
				+ "		#templateHeader{"
				+ "			background-color:#FFFFFF;"
				+ "			border-top:0;" + "			border-bottom:0;"
				+ "			padding-top:9px;" + "			padding-bottom:0;"
				+ "		}"
				+ "		#templateHeader .mcnTextContent,#templateHeader .mcnTextContent p{"
				+ "			color:#202020;" + "			font-family:Helvetica;"
				+ "			font-size:16px;" + "			line-height:150%;"
				+ "			text-align:left;" + "		}"
				+ "		#templateHeader .mcnTextContent a,#templateHeader .mcnTextContent p a{"
				+ "			color:#2BAADF;" + "			font-weight:normal;"
				+ "			text-decoration:underline;" + "		}"
				+ "		#templateBody{" + "			background-color:#FFFFFF;"
				+ "			border-top:0;"
				+ "			border-bottom:2px solid #EAEAEA;"
				+ "			padding-top:0;" + "			padding-bottom:9px;"
				+ "		}"
				+ "		#templateBody .mcnTextContent,#templateBody .mcnTextContent p{"
				+ "			color:#202020;" + "			font-family:Helvetica;"
				+ "			font-size:16px;" + "			line-height:150%;"
				+ "			text-align:left;" + "		}"
				+ "		#templateBody .mcnTextContent a,#templateBody .mcnTextContent p a{"
				+ "			color:#2BAADF;" + "			font-weight:normal;"
				+ "			text-decoration:underline;" + "		}"
				+ "		#templateFooter{"
				+ "			background-color:#084c9e;"
				+ "			border-top:0;" + "			border-bottom:0;"
				+ "			padding-top:9px;" + "			padding-bottom:9px;"
				+ "		}"
				+ "		#templateFooter .mcnTextContent,#templateFooter .mcnTextContent p{"
				+ "			color:#656565;" + "			font-family:Helvetica;"
				+ "			font-size:12px;" + "			line-height:150%;"
				+ "			text-align:center;" + "		}"
				+ "		#templateFooter .mcnTextContent a,#templateFooter .mcnTextContent p a{"
				+ "			color:#656565;" + "			font-weight:normal;"
				+ "			text-decoration:underline;" + "		}"
				+ "	@media only screen and (min-width:768px){"
				+ "		.templateContainer{"
				+ "			width:600px !important;" + "		}" + "}</style>"
				+ " </head>"
				+ " <body id=\"archivebody\" style=\"height: 100%;margin: 0;padding: 0;width: 100%;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;background-color: #FAFAFA;\"> "
				+ " <div id=\"awesomewrap\"> "

				+ "        <center>"
				+ "            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" height=\"100%\" width=\"100%\" id=\"bodyTable\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;height: 100%;margin: 0;padding: 0;width: 100%;background-color: #FAFAFA;\">"
				+ "                <tbody><tr>"
				+ "                    <td align=\"center\" valign=\"top\" id=\"bodyCell\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;height: 100%;margin: 0;padding: 10px;width: 100%;border-top: 0;\">"
				+ "                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" class=\"templateContainer\" >"
				+ "                            <tbody><tr>"
				+ "                                <td valign=\"top\" id=\"templatePreheader\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;background-color: #FAFAFA;border-top: 0;border-bottom: 0;padding-top: 9px;padding-bottom: 9px;\"><table width=\"100%\" class=\"mcnTextBlock\" style=\"min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				+ "    <tbody class=\"mcnTextBlockOuter\">" + "        <tr>"
				+ "            <td class=\"mcnTextBlockInner\" valign=\"top\" style=\"padding-top: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				+ "            </td>" + "        </tr>" + "    </tbody>"
				+ "</table></td>" + "                            </tr>"
				+ "                            <tr>"
				+ "                                <td valign=\"top\" id=\"templateHeader\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;background-color: #FFFFFF;border-top: 0;border-bottom: 0;padding-top: 9px;padding-bottom: 0;\"><table width=\"100%\" class=\"mcnImageBlock\" style=\"min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				+ "    <tbody class=\"mcnImageBlockOuter\">"
				+ "            <tr>"
				+ "                <td class=\"mcnImageBlockInner\" valign=\"top\" style=\"padding: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				+ "                    <table width=\"100%\" align=\"left\" class=\"mcnImageContentContainer\" style=\"min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				+ "                        <tbody><tr>"
				+ "                            <td class=\"mcnImageContent\" valign=\"top\" style=\"padding-right: 9px;padding-left: 9px;padding-top: 0;padding-bottom: 0;text-align: center;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				+ "                                        <img width=\"564\" align=\"center\" class=\"mcnImage\" style=\"max-width: 1200px;padding-bottom: 0;display: inline !important;vertical-align: bottom;border: 0;height: auto;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;\" alt=\"\" src=\"cid:mobiversa_logo1\" >"
				+ "                            </td>"
				+ "                        </tr>"
				
				+ "                    </tbody></table>"
				+ "                </td>" + "            </tr>" + "    </tbody>"
				+ "</table></td>" + "                            </tr>"
				+ "                            <tr>"
				+ "                                <td valign=\"top\" id=\"templateBody\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;background-color: #FFFFFF;border-top: 0;border-bottom: 2px solid #EAEAEA;padding-top: 0;padding-bottom: 9px;\"><table width=\"100%\" class=\"mcnTextBlock\" style=\"min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				+ "    <tbody class=\"mcnTextBlockOuter\">" + "        <tr>"
				+ "            <td class=\"mcnTextBlockInner\" valign=\"top\" style=\"padding-top: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				+ "                <table width=\"564\" align=\"center\" class=\"mcnTextContentContainer\" style=\"max-width: 100%;min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				+ "                    <tbody><tr>"
				+ "                        <td class=\"mcnTextContent\" valign=\"top\" style=\"padding-top: 0;padding-right: 18px;padding-bottom: 9px;padding-left: 18px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;word-break: break-word;color: #202020;font-family: Helvetica;font-size: 16px;line-height: 150%;text-align: left;\">"
				//+ "                            <h1 style=\"display: block;margin: 0;padding: 0;color: #202020;font-family: Helvetica;font-size: 26px;font-style: normal;font-weight: bold;line-height: 125%;letter-spacing: normal;text-align: left;\">Your Merchant&nbsp;Account Has Been Created</h1>"
				+ "                            <h1 style=\"display: block;margin: 0;padding: 0;color: #202020;font-family: Helvetica;font-size: 26px;font-style: normal;font-weight: bold;line-height: 125%;letter-spacing: normal;text-align: left;\">Welcome to EZYWIRE! We have Approved  your EZYWIRE Promotion.</h1>"
				+ "<p style=\"margin: 10px 0;padding: 0;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;color: #202020;font-family: Helvetica;font-size: 16px;line-height: 150%;text-align: left;\">"
				+ "Dear&nbsp;" + templetFields.getSalutation() + "."
				
				+ "Congratulations! your Customer Promotion  has been approved on Mobiversa. Please&nbsp;follow the&nbsp;link below to sign into your account :<br>"
				+ "<br>"
				/*+ "<a href=\"http://www.ezywire.com\" "*/
				+ "<a href=\"https://portal.mobiversa.com\" "
				+ "style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;color: #656565;font-weight: normal;text-decoration: underline;\"><span style=\"color:#656565\">"
				+ "portal.mobiversa.com</span></a>" + "<br>" + "<br>"
				+ "Please use your temporary login username and password as below.<br>"
				+ "<br>"
				+ "<span style=\"font-size:14px\"><span style=\"font-family:arial,helvetica neue,helvetica,sans-serif\"><span lang=\"EN-MY\">"
				+ "<font color=\"#000000\">USER ID :</font><strong><font color=\"#000000\">"
				+ templetFields.getPromoName() + "&nbsp;,<br>" + "<br>"
				+ templetFields.getPromoCode() + "&nbsp;,<br>" + "<br>"
				+ templetFields.getPromoDesc() + "&nbsp;,<br>" + "<br>"
				
				/*+ "<font color=\"#000000\">PASSWORD : </font><strong><font color=\"#000000\">"*/
				
				
				+ "<tbody class=\"mcnImageBlockOuter\">"
						+ "            <tr>"
						+ "                <td class=\"mcnImageBlockInner\" valign=\"top\" style=\"padding: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
						+ "                    <table width=\"100%\" align=\"left\" class=\"mcnImageContentContainer\" style=\"min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
						+ "                        <tbody><tr>"
						+ "                            <td class=\"mcnImageContent\" valign=\"top\" style=\"padding-right: 9px;padding-left: 9px;padding-top: 0;padding-bottom: 0;text-align: center;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
						+ "                                        <img width=\"564\" align=\"center\" class=\"mcnImage\" style=\"max-width: 1200px;padding-bottom: 0;display: inline !important;vertical-align: bottom;border: 0;height: auto;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;\" alt=\"\" src= data:image/jpg;base64,<c:out value='${templetFields.getPromoImage1()}'  />" 
						+ "                            </td>"
						+ "                        </tr>"
						
						+ "                    </tbody></table>"
						+ "                </td>" + "            </tr>" + "    </tbody>"
						+ "</table></td>" + "                            </tr>"
				
				+"<br><br>" 
				+ "<tbody class=\"mcnImageBlockOuter\">"
				+ "            <tr>"
				+ "                <td class=\"mcnImageBlockInner\" valign=\"top\" style=\"padding: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				+ "                    <table width=\"100%\" align=\"left\" class=\"mcnImageContentContainer\" style=\"min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				+ "                        <tbody><tr>"
				+ "                            <td class=\"mcnImageContent\" valign=\"top\" style=\"padding-right: 9px;padding-left: 9px;padding-top: 0;padding-bottom: 0;text-align: center;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				+ "                                        <img width=\"564\" align=\"center\" class=\"mcnImage\" style=\"max-width: 1200px;padding-bottom: 0;display: inline !important;vertical-align: bottom;border: 0;height: auto;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;\" alt=\"\" src= data:image/jpg;base64,<c:out value='${templetFields.getPromoImage2()}'  />" 
				+ "                            </td>"
				+ "                        </tr>"
				
				+ "                    </tbody></table>"
				+ "                </td>" + "            </tr>" + "    </tbody>"
				+ "</table></td>" + "                            </tr>"
				
				/*+ templetFields.getPromoImage2()*/
				+ "</font></strong></span></span></span><br>" + "<br>"
				/*+ templetFields.getPromoImage2()
				+ "</font></strong></span></span></span><br>" + "<br>"*/
				+ "In case you need any clarification, please contact <i>info@gomobi.io</i>.<br>"
				+ "<br>"
				+ "This is an auto generated ID and password, please feel free to change your credentials on your first login.</p>"
				+ "                        </td>" + "                    </tr>"
				+ "                </tbody></table>" + "            </td>"
				+ "        </tr>" + "    </tbody>" + "</table></td>"
				+ "                            </tr>"
				+ "                            <tr>"
				// + " <td valign=\"top\" id=\"templateFooter\"
				// style=\"mso-line-height-rule: exactly;-ms-text-size-adjust:
				// 100%;-webkit-text-size-adjust: 100%;background-color:
				// #084c9e;border-top: 0;border-bottom: 0;padding-top:
				// 9px;padding-bottom: 9px;\">"
				// + "<table width=\"100%\" class=\"mcnFollowBlock\"
				// style=\"min-width: 100%;border-collapse:
				// collapse;mso-table-lspace: 0pt;mso-table-rspace:
				// 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust:
				// 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				
				+"  <td align=\"center\">"
				+" <table width=\"564\" >"
				+"	<tr>"
				+"	<td>"//link
				
				+"  <table width=\"564\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;background-color: #084c9e;border-top: 0;border-bottom: 0;padding-top: 9px;padding-bottom: 9px;\">"
				+"	<tr>"
				
				+" <td>"
				
				+" <table width=\"50%\" align=\"center\">"
				+"	<tr>"
				+"	<td>"//link
				
				+"<table align=\"left\" style=\"display: inline;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
						  +
						  "                                                <tbody><tr>"
						  +
						  "                                                    <td class=\"mcnFollowContentItemContainer\" valign=\"top\" style=\"padding-right: 10px;padding-bottom: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
						  +
						  "                                                        <table width=\"100%\" class=\"mcnFollowContentItem\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
						  +
						  "                                                            <tbody><tr>"
						  +
						  "                                                                <td align=\"left\" valign=\"middle\" style=\"padding-top: 5px;padding-right: 10px;padding-bottom: 5px;padding-left: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
						  +
						  "                                                                    <table width=\"\" align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
						  +
						  "                                                                        <tbody><tr>"
						  +
						  "                                                                                <td width=\"24\" align=\"center\" class=\"mcnFollowIconContent\" valign=\"middle\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
						  +
						  "                                                                                    <a href=\"http://www.facebook.com/mobiversa\" target=\"_blank\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"><img width=\"24\" height=\"24\" style=\"display: block;border: 0;height: auto;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;\" src=\"cid:mobi_facebook\"></a>"
						  +
						  "                                                                                </td>"
						  +
						  "                                                                        </tr>"
						  +
						  "                                                                    </tbody></table>"
						  +
						  "                                                                </td>"
						  +
						  "                                                            </tr>"
						  +
						  "                                                        </tbody></table>"
						  + "                                                    </td>"
						  + "                                                </tr>" +
						  "                                            </tbody></table>"
						  
				
				+" </td>"
				+" <td>"
				
				 +"<table align=\"left\" style=\"display: inline;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
						  +
						  "                                                <tbody><tr>"
						  +
						  "                                                    <td class=\"mcnFollowContentItemContainer\" valign=\"top\" style=\"padding-right: 10px;padding-bottom: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
						  +
						  "                                                        <table width=\"100%\" class=\"mcnFollowContentItem\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
						  +
						  "                                                            <tbody><tr>"
						  +
						  "                                                                <td align=\"left\" valign=\"middle\" style=\"padding-top: 5px;padding-right: 10px;padding-bottom: 5px;padding-left: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
						  +
						  "                                                                    <table width=\"\" align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
						  +
						  "                                                                        <tbody><tr>"
						  +
						  "                                                                                <td width=\"24\" align=\"center\" class=\"mcnFollowIconContent\" valign=\"middle\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
						  +
						  "                                                                                    <a href=\"http://www.twitter.com/mobiversa\" target=\"_blank\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"><img width=\"24\" height=\"24\" style=\"display: block;border: 0;height: auto;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;\" src=\"cid:mobi_twitter\"></a>"
						  +
						  "                                                                                </td>"
						  +
						  "                                                                        </tr>"
						  +
						  "                                                                    </tbody></table>"
						  +
						  "                                                                </td>"
						  +
						  "                                                            </tr>"
						  +
						  "                                                        </tbody></table>"
						  + "                                                    </td>"
						  + "                                                </tr>" +
						  "                                            </tbody></table>"
				
				+" </td>"
				+" <td>"
				
				+"                                            <table align=\"left\" style=\"display: inline;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  +
				  "                                                <tbody><tr>"
				  +
				  "                                                    <td class=\"mcnFollowContentItemContainer\" valign=\"top\" style=\"padding-right: 10px;padding-bottom: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                        <table width=\"100%\" class=\"mcnFollowContentItem\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                            <tbody><tr>"
				  +
				  "                                                                <td align=\"left\" valign=\"middle\" style=\"padding-top: 5px;padding-right: 10px;padding-bottom: 5px;padding-left: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                    <table width=\"\" align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                        <tbody><tr>"
				  +
				  "                                                                                <td width=\"24\" align=\"center\" class=\"mcnFollowIconContent\" valign=\"middle\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                                    <a href=\"http://www.mobiversa.com/\" target=\"_blank\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"><img width=\"24\" height=\"24\" style=\"display: block;border: 0;height: auto;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;\" src=\"cid:mobiversa_link\"></a>"
				  +
				  "                                                                                </td>"
				  +
				  "                                                                        </tr>"
				  +
				  "                                                                    </tbody></table>"
				  +
				  "                                                                </td>"
				  +
				  "                                                            </tr>"
				  +
				  "                                                        </tbody></table>"
				  + "                                                    </td>"
				  + "                                                </tr>" +
				  "                                            </tbody></table>"
				  			  
				+" </td>"
				+" <td>"
				+"                                            <table align=\"left\" style=\"display: inline;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  +
				  "                                                <tbody><tr>"
				  +
				  "                                                    <td class=\"mcnFollowContentItemContainer\" valign=\"top\" style=\"padding-right: 0;padding-bottom: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                        <table width=\"100%\" class=\"mcnFollowContentItem\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                            <tbody><tr>"
				  +
				  "                                                                <td align=\"left\" valign=\"middle\" style=\"padding-top: 5px;padding-right: 10px;padding-bottom: 5px;padding-left: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                    <table width=\"\" align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                        <tbody><tr>"
				  +
				  "                                                                                <td width=\"24\" align=\"center\" class=\"mcnFollowIconContent\" valign=\"middle\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                                    <a href=\"http://www.linkedin.com/company/mobiversa?\" target=\"_blank\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"><img width=\"24\" height=\"24\" style=\"display: block;border: 0;height: auto;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;\" src=\"cid:mobi_linkedin\"></a>"
				  +
				  "                                                                                </td>"
				  +
				  "                                                                        </tr>"
				  +
				  "                                                                    </tbody></table>"
				  +
				  "                                                                </td>"
				  +
				  "                                                            </tr>"
				  +
				  "                                                        </tbody></table>"
				  + "                                                    </td>"
				  + "                                                </tr>" +
				  "                                            </tbody></table>"
				
				+" </td>"
				
				+"	</tr>"
				+"</table>"
				
				+"<table width=\"100%\" class=\"mcnDividerBlock\" style=\"min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;table-layout: fixed !important;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  + "    <tbody class=\"mcnDividerBlockOuter\">" +
				  "        <tr>" +
				  "            <td class=\"mcnDividerBlockInner\" style=\"padding: 10px 18px 25px;min-width: 100%;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                <table width=\"100%\" class=\"mcnDividerContent\" style=\"border-top-color: #EEEEEE;border-top-width: 2px;border-top-style: solid;min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  + "                    <tbody><tr>" +
				  "                        <td style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  + "                            <span></span>" +
				  "                        </td>" + "                    </tr>"
				  + "                </tbody></table>" + "            </td>" +
				  "        </tr>" + "    </tbody>" +
				  "</table>"
				 +" <table width=\"100%\" class=\"mcnTextBlock\" style=\"min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
						  + "    <tbody class=\"mcnTextBlockOuter\">" + "        <tr>"
						  +
						  "            <td class=\"mcnTextBlockInner\" valign=\"top\" style=\"padding-top: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
						  +
						  "                <table width=\"100%\" align=\"left\" class=\"mcnTextContentContainer\" style=\"max-width: 100%;min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
						  + "                    <tbody><tr>" +
						  "                        <td class=\"mcnTextContent\" valign=\"top\" style=\"padding-top: 0;padding-right: 18px;padding-bottom: 9px;padding-left: 18px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;word-break: break-word;color: #656565;font-family: Helvetica;font-size: 12px;line-height: 150%;text-align: center;\">"
						  +
						  "                            <div style=\"text-align: center;\"><span style=\"color:#ffffff\"><em>Copyright © 2016 Mobiversa, All rights reserved.</em><br>"
						  +
						  "<em>You are receiving this email because you have created an account with Mobiversa.</em><br>"
						  + "<br>" + "Our mailing address is:" + "<br>" +
						  "<b>Mobiversa</b> <br>" +
						  "#07-01, Wisma UOA Damansara II, No. 6, Changkat Semantan<br>"
						  + "Damansara Heights, 50490 Kuala Lumpur<br>" + "Malaysia" +
						  "<br>" + "<br>" + "</span></div>" +
						  "                        </td>" + "                    </tr>"
						  + "                </tbody></table>" + "            </td>" +
						  "        </tr>" + "    </tbody>" + "</table>"
				
				+" </td>"
				
				+"	</tr>"
				+"</table>"
				
				+"	</td>"
				+" </tr>"
				/*+" <tr>"
				+"	<td>"//line
				+" 	Line"
				+"	</td>"
				+" </tr>"
				+" <tr>"
				+"	<td>"//address
				+" Address	"
				+"	</td>"	
				+" </tr>"*/
				
				
				/*+"     <td valign=\"top\" id=\"templateFooter\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;background-color: #084c9e;border-top: 0;border-bottom: 0;padding-top: 9px;padding-bottom: 9px;\">"
				+ "	<table width=\"564\" class=\"mcnFollowBlock\" style=\"min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				+ "    <tbody class=\"mcnFollowBlockOuter\">" + "        <tr>"
				+ "            <td align=\"center\" class=\"mcnFollowBlockInner\" valign=\"top\" style=\"padding: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				
				  +
				  "                <table width=\"564\" class=\"mcnFollowContentContainer\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  + "    <tbody><tr>" +
				  "        <td align=\"center\" style=\"padding-left: 9px;padding-right: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "            <table width=\"564\" class=\"mcnFollowContent\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  + "                <tbody><tr>" +
				  "                    <td align=\"center\" valign=\"top\" style=\"padding-top: 9px;padding-right: 9px;padding-left: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                        <table width=\"564\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  + "                            <tbody><tr>" +
				  "                                <td align=\"center\" valign=\"top\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                            <table align=\"left\" style=\"display: inline;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  +
				  "                                                <tbody><tr>"
				  +
				  "                                                    <td class=\"mcnFollowContentItemContainer\" valign=\"top\" style=\"padding-right: 10px;padding-bottom: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                        <table width=\"100%\" class=\"mcnFollowContentItem\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                            <tbody><tr>"
				  +
				  "                                                                <td align=\"left\" valign=\"middle\" style=\"padding-top: 5px;padding-right: 10px;padding-bottom: 5px;padding-left: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                    <table width=\"\" align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                        <tbody><tr>"
				  +
				  "                                                                                <td width=\"24\" align=\"center\" class=\"mcnFollowIconContent\" valign=\"middle\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                                    <a href=\"http://www.facebook.com/mobiversa\" target=\"_blank\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"><img width=\"24\" height=\"24\" style=\"display: block;border: 0;height: auto;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;\" src=\"cid:mobi_facebook\"></a>"
				  +
				  "                                                                                </td>"
				  +
				  "                                                                        </tr>"
				  +
				  "                                                                    </tbody></table>"
				  +
				  "                                                                </td>"
				  +
				  "                                                            </tr>"
				  +
				  "                                                        </tbody></table>"
				  + "                                                    </td>"
				  + "                                                </tr>" +
				  "                                            </tbody></table>"
				  +
				  "                                             <table align=\"left\" style=\"display: inline;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  +
				  "                                                <tbody><tr>"
				  +
				  "                                                    <td class=\"mcnFollowContentItemContainer\" valign=\"top\" style=\"padding-right: 10px;padding-bottom: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                        <table width=\"100%\" class=\"mcnFollowContentItem\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                            <tbody><tr>"
				  +
				  "                                                                <td align=\"left\" valign=\"middle\" style=\"padding-top: 5px;padding-right: 10px;padding-bottom: 5px;padding-left: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                    <table width=\"\" align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                        <tbody><tr>"
				  +
				  "                                                                                <td width=\"24\" align=\"center\" class=\"mcnFollowIconContent\" valign=\"middle\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                                    <a href=\"http://www.twitter.com/mobiversa\" target=\"_blank\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"><img width=\"24\" height=\"24\" style=\"display: block;border: 0;height: auto;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;\" src=\"cid:mobi_twitter\"></a>"
				  +
				  "                                                                                </td>"
				  +
				  "                                                                        </tr>"
				  +
				  "                                                                    </tbody></table>"
				  +
				  "                                                                </td>"
				  +
				  "                                                            </tr>"
				  +
				  "                                                        </tbody></table>"
				  + "                                                    </td>"
				  + "                                                </tr>" +
				  "                                            </tbody></table>"
				  +
				  "                                            <table align=\"left\" style=\"display: inline;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  +
				  "                                                <tbody><tr>"
				  +
				  "                                                    <td class=\"mcnFollowContentItemContainer\" valign=\"top\" style=\"padding-right: 10px;padding-bottom: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                        <table width=\"100%\" class=\"mcnFollowContentItem\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                            <tbody><tr>"
				  +
				  "                                                                <td align=\"left\" valign=\"middle\" style=\"padding-top: 5px;padding-right: 10px;padding-bottom: 5px;padding-left: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                    <table width=\"\" align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                        <tbody><tr>"
				  +
				  "                                                                                <td width=\"24\" align=\"center\" class=\"mcnFollowIconContent\" valign=\"middle\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                                    <a href=\"http://www.mobiversa.com/\" target=\"_blank\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"><img width=\"24\" height=\"24\" style=\"display: block;border: 0;height: auto;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;\" src=\"cid:mobiversa_link\"></a>"
				  +
				  "                                                                                </td>"
				  +
				  "                                                                        </tr>"
				  +
				  "                                                                    </tbody></table>"
				  +
				  "                                                                </td>"
				  +
				  "                                                            </tr>"
				  +
				  "                                                        </tbody></table>"
				  + "                                                    </td>"
				  + "                                                </tr>" +
				  "                                            </tbody></table>"
				  +
				  "                                            <table align=\"left\" style=\"display: inline;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  +
				  "                                                <tbody><tr>"
				  +
				  "                                                    <td class=\"mcnFollowContentItemContainer\" valign=\"top\" style=\"padding-right: 0;padding-bottom: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                        <table width=\"100%\" class=\"mcnFollowContentItem\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                            <tbody><tr>"
				  +
				  "                                                                <td align=\"left\" valign=\"middle\" style=\"padding-top: 5px;padding-right: 10px;padding-bottom: 5px;padding-left: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                    <table width=\"\" align=\"left\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                        <tbody><tr>"
				  +
				  "                                                                                <td width=\"24\" align=\"center\" class=\"mcnFollowIconContent\" valign=\"middle\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                                                                                    <a href=\"http://www.linkedin.com/company/mobiversa?\" target=\"_blank\" style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\"><img width=\"24\" height=\"24\" style=\"display: block;border: 0;height: auto;outline: none;text-decoration: none;-ms-interpolation-mode: bicubic;\" src=\"cid:mobi_linkedin\"></a>"
				  +
				  "                                                                                </td>"
				  +
				  "                                                                        </tr>"
				  +
				  "                                                                    </tbody></table>"
				  +
				  "                                                                </td>"
				  +
				  "                                                            </tr>"
				  +
				  "                                                        </tbody></table>"
				  + "                                                    </td>"
				  + "                                                </tr>" +
				  "                                            </tbody></table>"
				  + "                                </td>" +
				  "                            </tr>" +
				  "                        </tbody></table>" +
				  "                    </td>" + "                </tr>" +
				  "            </tbody></table>" + "        </td>" +
				  "    </tr>" + "</tbody></table>" + "            </td>" +
				  "        </tr>" + "    </tbody>" +
				  "</table><table width=\"100%\" class=\"mcnDividerBlock\" style=\"min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;table-layout: fixed !important;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  + "    <tbody class=\"mcnDividerBlockOuter\">" +
				  "        <tr>" +
				  "            <td class=\"mcnDividerBlockInner\" style=\"padding: 10px 18px 25px;min-width: 100%;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                <table width=\"100%\" class=\"mcnDividerContent\" style=\"border-top-color: #EEEEEE;border-top-width: 2px;border-top-style: solid;min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  + "                    <tbody><tr>" +
				  "                        <td style=\"mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  + "                            <span></span>" +
				  "                        </td>" + "                    </tr>"
				  + "                </tbody></table>" + "            </td>" +
				  "        </tr>" + "    </tbody>" +
				  "</table><table width=\"100%\" class=\"mcnTextBlock\" style=\"min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  + "    <tbody class=\"mcnTextBlockOuter\">" + "        <tr>"
				  +
				  "            <td class=\"mcnTextBlockInner\" valign=\"top\" style=\"padding-top: 9px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\">"
				  +
				  "                <table width=\"100%\" align=\"left\" class=\"mcnTextContentContainer\" style=\"max-width: 100%;min-width: 100%;border-collapse: collapse;mso-table-lspace: 0pt;mso-table-rspace: 0pt;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
				  + "                    <tbody><tr>" +
				  "                        <td class=\"mcnTextContent\" valign=\"top\" style=\"padding-top: 0;padding-right: 18px;padding-bottom: 9px;padding-left: 18px;mso-line-height-rule: exactly;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;word-break: break-word;color: #656565;font-family: Helvetica;font-size: 12px;line-height: 150%;text-align: center;\">"
				  +
				  "                            <div style=\"text-align: center;\"><span style=\"color:#ffffff\"><em>Copyright © 2016 Mobiversa, All rights reserved.</em><br>"
				  +
				  "<em>You are receiving this email because you have created an account with Mobiversa.</em><br>"
				  + "<br>" + "Our mailing address is:" + "<br>" +
				  "<b>Mobiversa</b> <br>" +
				  "#07-01, Wisma UOA Damansara II, No. 6, Changkat Semantan<br>"
				  + "Damansara Heights, 50490 Kuala Lumpur<br>" + "Malaysia" +
				  "<br>" + "<br>" + "</span></div>" +
				  "                        </td>" + "                    </tr>"
				  + "                </tbody></table>" + "            </td>" +
				  "        </tr>" + "    </tbody>" + "</table>"
				 
				+ " </td>" + "                            </tr>"*/
				+ "                        </tbody></table>"
				+ "                    </td>" + "                </tr>"
				+ "            </tbody></table>" + "        </center>" + "</div>"
				+ "</body></html>";
	}

}

