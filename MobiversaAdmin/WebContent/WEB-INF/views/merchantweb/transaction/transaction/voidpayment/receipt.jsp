<%@page	import="com.mobiversa.payment.controller.MerchantWebTransactionController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html>
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link href="/resourcesNew/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
	 <script src="/resourcesNew/js/jQuery-2.1.4.min.js" type="text/javascript"></script>
	 <script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
<link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet"
	type="text/css" />
<style>
@media only screen and (max-width: 690px) { "
	.container {
		width: 100% !important;
	}
	"
}

"
.flexibleImage { "
	max-width: 100px !important;
	"
	width
	100%
	!important;
	"
}
"
</style>
</head>
<body style="font-family: 'Oswald', sans-serif; color: #666;">
	<table class="container" width="100%">
		<table align="center" width="300"
			style="margin: 20px auto; background: #fff; border: 3px solid #ccc; border-style: double; padding: 10px;">
			<tr>
				<td align="center">
					<!--  <div style="margin-left:278px"> <img
					src="/resourcesNew/img/mobiversa logo-1.png">
					style="margin-left: 180px"> --> <img
					src="data:image/jpg;base64,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" />

				</td>
			</tr>
			<tr>
				<td>
				
					
		        <%--  <table cellpadding="0" cellspacing="0" align = "center" style="font-size:16px;">
		         <tr>
		         <td style="padding:5px;text-align:center;font-size:16px;display:block;font-family:Lao UI;">${dto.merchantName}</td>
		         </tr>
		         <tr>
		         <td style="padding:5px;text-align:center;font-size:10px;font-family:Lao UI;">
		         ${dto.merchantAddr1}, ${dto.merchantAddr2}<br/>
		        ${dto.merchantCity}, ${dto.merchantPostCode}<br/>
		         ${dto.merchantContNo}
		         </td>
		         </tr>
		         </table>			 --%>
				
					
					<table cellpadding="0" cellspacing="0" align="center"
						style="font-size: 16px;">
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family: Lao UI;">First Name</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family: Lao UI;">${dto.firstName}</td>
						</tr>
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family: Lao UI;">Last Name</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family: Lao UI;">
								${dto.lastName}</td>
						</tr>
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family: Lao UI;">Merchant Name</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family: Lao UI;">
								${dto.businessName}</td>
						</tr>
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family: Lao UI;">Contact No</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family: Lao UI;">
								${dto.businessContactNumber}</td>
						</tr>
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family: Lao UI;">EMail Id</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family: Lao UI;">
								${dto.email}</td>
						</tr>
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family: Lao UI;">Address</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family: Lao UI;">
								 ${dto.businessAddress1}<br/> 
		        				${dto.postcode}<br/>${dto.city}<br/>
		        				${dto.state}
		        				</td>
						</tr>
						
					</table>
					
		</table>

	</table>

</body>
</html>
