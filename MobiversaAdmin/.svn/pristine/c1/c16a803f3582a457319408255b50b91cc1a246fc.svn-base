<%@page
	import="com.mobiversa.payment.controller.MerchantWebTransactionController"%>
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
<script src="/resourcesNew/js/jQuery-2.1.4.min.js"
	type="text/javascript"></script>
<script
	src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>

<!-- <script type="text/javascript">
	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");

	});
</script> -->
<link href="/resourcesNew/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
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
<script lang="JavaScript">
	function displaySign() {
		var sign = document.getElementById("sign").value;
		//alert("Data :"+sign);
		document.getElementById("nsign").style.display = 'none';
		document.getElementById("tsign").style.display = 'none';
		//alert("Data :"+sign);
		if (sign == null || sign == '') {
			//alert("if :"+sign);
			document.getElementById("nsign").style.display = '';
		} else {
			//alert("else :"+sign);
			document.getElementById("tsign").style.display = '';

		}
	}
</script>
</head>
<body style="font-family: 'Oswald', sans-serif; color: #666;"
	onload="displaySign();">
	<table class="container" width="100%">
	<tr>
				<td align="center" >
					<!--  <div style="margin-left:278px"> <img
					src="/resourcesNew/img/mobiversa logo-1.png">
					style="margin-left: 180px"> -->
					<img style="width: 119px !important;height: 57px !important;" alt="mobiLogo"
					src="data:image/png;base64,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"/>
					<!--  <img style="width: 200px !important;height: 55px !important;"
					src="data:image/jpg;base64,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" /> -->

				</td>
			</tr>
	<tr><td>
		<table align="center" width="400" border="0" 
			style="margin: 20px auto; background: #fff; border: 3px solid #ccc; border-style: solid; padding: 10px;">
			
			<tr>
				<td align="center" height="150" valign="middle">
					<!--  <div style="margin-left:278px"> <img
					src="/resourcesNew/img/mobiversa logo-1.png">
					style="margin-left: 180px"> -->
					
					 <img style="width: 45px !important;height: 45px !important;"
					src="data:image/jpg;base64,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" />

				<br/>
				</td>
			</tr>
			<tr><td align="center" valign="middle"><h4 style="font-family:Helvetica;font-weight: bold;color:black;">
			Payment Received</h4></td></tr>
			<tr><td align="center"><h4  style="font-family:Helvetica;font-weight: normal;color:black;">
			Thank you for your payment on <br/>${dto.date} at ${dto.time}</h4></td></tr>
			<tr><td align="center"><h4 style="font-family:Helvetica;font-weight: bold;color:#175da9;">
			${dto.txnType}</h4></td></tr>
			<tr><td align="center"><h4 style="font-family:Helvetica;font-weight: bold;color:#175da9;">
			Total&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;RM ${dto.total}</h4></td></tr>
			<tr>
				<td>

					
					<table cellpadding="0" cellspacing="0" align="center"
						style="font-size: 16px;">
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">TID</td>
								<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">:</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family:Helvetica;">${dto.tid}</td>
						</tr>
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">MID</td>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">:</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family:Helvetica;">
								${dto.mid}</td>
						</tr>
						
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">Card
								Number</td>
								<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">:</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family:Helvetica;">
								${dto.cardNo}</td>
						</tr>
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">
								Card Holder</td>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">:</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family:Helvetica;">
								${dto.cardHolderName}</td>
						</tr>
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">Card
								Type</td>
								<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">:</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family:Helvetica;">
								${dto.cardType}</td>
						</tr>
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">RR
								Number</td>
								<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">:</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family:Helvetica;">
								${dto.rrn}</td>
						</tr>

						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">Batch
								Number</td>
								<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">:</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family:Helvetica;">
								${dto.batchNo}</td>
						</tr>
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">Appr
								Code</td>
								<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">:</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family:Helvetica;">
								${dto.apprCode}</td>
						</tr>
						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">Trace
								Number</td>
								<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">:</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family:Helvetica;">
								${dto.traceNo}</td>
						</tr>


						<tr>
							<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">Ref</td>
								<td
								style="padding: 5px; color: #3F72D8; text-align: left; font-size: 16px; font-family:Helvetica;">:</td>
							<td
								style="padding: 5px; text-align: left; font-size: 13px; font-family:Helvetica;">
								${dto.refNo}</td>
						</tr>
						

						

					
					</table>
					<table cellpadding="18" cellspacing="18" align="center"
						style="font-size: 18px;">
						<tr>
							<td
								style="padding: 2px; text-align: center; font-family:Helvetica; font-size: 13px;color:black;">
								<small><b>I AGREE TO PAY ABOVE TOTAL AMOUNT ACCORDING TO<br/>
									MERCHANT'S TERM & CONDITIONS ACCEPTED BY ME.</b></small>
							</td>
						</tr>
						
					</table>
					<!-- <h4
						style="font-size: 13px; color: #0b9bff; display: block; text-align: center;">NO
						REFUND</h4> --> <!-- Map Location -->
					<table cellpadding="0" cellspacing="0" align="center" width="100%" border="0"
						style="font-size: 8px;">
						<tr>
							<td
								style="padding: 2px; text-align: center; font-family:Helvetica; font-size: 8px;">
								    101.666356
								<a
								href="https://www.google.com/maps/place/${dto.latitude},${dto.longitude}\">
									<img width="350" height="200"
									src="http://maps.google.com/maps/api/staticmap?center=${dto.latitude},${dto.longitude},
									&zoom=14&markers=${dto.latitude},${dto.longitude}|${dto.latitude},${dto.longitude}&
									path=color:0x0000FF80|weight:5|${dto.latitude},${dto.longitude}&size=350x200&sensor=TRUE_OR_FALSE"
									alt="Mobiversa Map, KL\">
							</a>
							</td>
						</tr></table>
						<table cellpadding="0" cellspacing="10" align="center" border="0" width="100%"
						style="font-size: 16px;">
						<tr>
							<%-- <td align="center"
								style="padding: 5px; text-align: center; font-size: 15px; font-family:Helvetica;color:#175da9;">
								<b>${dto.merchantName}</b><BR/>
								${dto.merchantAddr1}<br /> ${dto.merchantCity},
								${dto.merchantPostCode}<br /> 
								Phone: ${dto.merchantContNo}
							</td> --%>
							
							<td align="center"
								style="padding: 5px; text-align: center; font-size: 15px; font-family:Helvetica;color:#175da9;">
								<b>${dto.merchantName}</b><BR/>
								${dto.merchantAddr1}<br /> ${dto.merchantPostCode},${dto.merchantCity},
								<br /> ${dto.merchantState} <br /> 
								Phone: ${dto.merchantContNo}
							</td>
							
						</tr>
						<tr>
							
							<td align="center"
								style="padding: 1px; border-top: 1px dotted #ccc; color: #3F72D8; text-align: right;">
								<input type="hidden" name="sign" id="sign" value="${dto.sign}">
							</td>
						</tr>

						<tr id="tsign">
							

							<td valign="top" style="padding-top: 5px;"><img
								width="150"
								src="data:image/jpg;base64,<c:out value='${dto.sign}'/>" /></td>




						</tr>
						<tr id="nsign">
							



							<td valign="top" style="padding-top: 5px;" align="center"><small>Pin
									Verified</small></td>


						</tr>
					</table>

					
					</td></tr>
		</table></td></tr>
			<tr>
			<td align="center">
			 <img style="width: 75px !important;height: 35px !important;"
					src="data:image/jpg;base64,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" />
			
			
			</td>
			
			</tr>
	</table>

</body>
</html>
