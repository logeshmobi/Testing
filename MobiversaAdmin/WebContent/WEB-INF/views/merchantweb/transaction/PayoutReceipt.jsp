<%@page
	import="com.mobiversa.payment.controller.MerchantWebTransactionController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.ResourceBundle"%>
<%
ResourceBundle resource = ResourceBundle.getBundle("config");
String payoutslip = resource.getString("PAYOUT_SLIP");

%>


<html>
<head>
<script async defer
	src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBvMCKdhSLSy_4FKtIZFZbHr2spqdxzNiQ&callback=initMap">
	
</script>

<link rel="icon" type="image/gif" sizes="16x16"
	href="${pageContext.request.contextPath}/resourcesNew/img/newMobiMIcon.png" />
<title>Mobiversa Receipt</title>
<meta charset="UTF-8">
<meta http-equiv="x-ua-compatible" content="IE=edge">
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
<body style="font-family: Helvetica; color: #666;">
	<center>
		<table class="container" width="380" border="0"
			style="border-radius: 10px; background-color: #005baa;">

			<tr>
				<td>
					<table style="width: 100%;">
						<tr>

							<c:set var="txnType" value="${dto.payoutstatus}" />

							<c:if test="${dto.payoutstatus == 'paid'}">
								<td style="color: #42cc15; padding-left: 10px; font-size: 18px;">${dto.txnType}</td>
							</c:if>


							<td align="right"><img style="height: 45px !important"
								; src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAF8AAAAtCAYAAADIprc/AAAAAXNSR0IArs4c6QAABAxJREFUeAHtWb1uE0EQthG0YAoKKq6DVPEbcJFoqGKegEtDTUkX8wbJE9h5AkiFqHx+glyUBtHkQo9wKhASMt9nbtHdePZ8P4niJDPS6Ha++WZ297u79SnpdMxMAVPAFDAFTAFTwBQwBUwBU8AUMAVMgZugQPcmbKLqHubzeQzuPM/vdrtb+bjuGD0foeYl/CH8M/p9qdvjVvAh1JK12TiavYD/FE3ftel5Y2uFSIuwzWbR4ETp+RvY/Sp971QhGcerwIaSuQfsmYIvQSb+kiS1gERh/wH2VcFvN6QcEYUf37rqoN8G/Fuu7y+MB1X73LavnSWx8XXSSgOIza+czUzwFO1SE19RgE+ohNuKL/vVie9WJWPdAbhPBP/M3Wnko1x+hvEBcrwWDDy+lu5JYe4QvKRAKglQ30N6Gx7kaCnGU/ThtZFl+3uO4iBrwLVzbWkWL11Q8xjgU5H4jpoTgbULMdEQLo1YH34qE4h/wCM3K8Yh/BSu2QQgRfUa8gF8BC+zCZKhr4mnsAd85MkRnsD7Wk/gb+DSPmncVhhm8Ik/k7OLOEI8EJgWHvkWmNXzZla1Xa2Xp/jIg0s4kj1BuFLxT+UKlZiiVRVuqGyQb1YT08Rq0idfU3gDkGglftvv/ECKpcQ8TkqPlFzN69y4g82x7kMeqzEeob4gVo1aH7XpWtR+bcVn03P4+8ynBErsEDly9+Gsk8ZzPciBEcb52KWOMXgF38rcN+8Q+ap2AKLrue8p4voGntzlwZh0CJfG8z7Iz4o4lqQs3hO80MMLHQ/5ROEkLp+/gvdR4RLqOZ4nTzh0HHcF5jvuxjnOlR47ifIpNnSLE9eC+KiLkT8THIb/xcJ4U8kPFYxQ5MH7HtzB+9laXLy4Aksw0N6AoEBsEbQ9duKqcys3iaWpUr8QC09eoOQIxRqO/jPg51puBUaRfRYrCe2BUGirobbir56hIcNzs9gtKGn5oCTnS/V8CeBarskNVqdYW/HV1f4DQy2nndsZj29EmW2XJCMllypYI2jdxT9UdrUrhc6OqJHCPcMbVHassIQ//CN44SlHvIcc/9wgbVU/yffGlf+24+1wuQkKIJ9MijSBODGu9D48hBOXNpaAJ46A8yYkWZ49g2wsL2MJNI3XWnw8tfxsnWJz2hMYAqf7jF9Se76kggfA6GV2UOFNKqsv5Nb92OFiB/DjwqpXB/xRHECo2WpqZQbX8LYyuwJx7cXPBAyxF+3817ZIkfo1ntAdrYnA2DO84JvZWXvxKQI3DecbsAXnTTiHS5sC2AGPwqcy6YvBHSPn+koaRXc9L/ItWszTlbNdlxi/BQHWSq/1rzvwSw19QxJwU2JezUwBU8AUMAVMAVPAFDAFTAFTwBQwBa6JAn8BgcK/gljfEOkAAAAASUVORK5CYII=">

							</td>


						</tr>
					</table>
				</td>

			</tr>

			<tr>
				<td>

					<table style="width: 100%;">
						<tr>
							<td
								style="color: #FFF; padding-left: 10px; font-size: 18px; font-weight: bold;">RM
								${dto.total}</td>
							<td style="color: #FFF; text-align: right; font-size: 11px;">${dto.createdDate}
								, ${dto.createdTime}
								</td>
						</tr>
					</table>

				</td>
			</tr>

			<tr>
				<td>

					<table align="center" width=" 380" border="0"
						style="border-bottom-left-radius: 15px; border-bottom-right-radius: 15px; background: #EDFBFF; border: 2px solid #005baa; border-style: solid; padding: 10px;">

						<tr>
							<td
								style="padding-left: 5px; font-size: 12px; font-family: Helvetica;">
								<b style="color: #3e4152;">Hi </b><BR />

							</td>
						</tr>

						<tr>
							<td
								style="padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #909090;">

								You authorised a payment of RM ${dto.total} to
								${dto.merchantName}</td>
						</tr>

						<tr>
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 12px; font-family: Helvetica; color: #9b9b9b;">

								<p></p>
							</td>
						</tr>

						<tr>
						<c:if test="${dto.payoutstatus == 'Paid'}">	
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 13px; font-family: Helvetica; color: #9b9b9b;">

								PAYMENT DESCRIPTION</td>
								</c:if>
						</tr>
						<tr>
							<td
								style="padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #d9d9d9; border-top: 1px;">

							</td>
						</tr>

						<tr>
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 12px;  font-family: Helvetica; color: #3e4152; text-transform: capitalize;">

								${dto.paymentdescription}</td>
						</tr>

				



				

						<tr>
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 12px; font-family: Helvetica; color: #9b9b9b;">

								<p></p>
							</td>
						</tr>


						<!-- TRANSACTION DETAILS -->
						<tr>
							<td
								style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 13px; font-family: Helvetica; color: #9b9b9b;">

								TRANSACTION DETAILS</td>
						</tr>

					
						<tr>
							<td>

								<table style="width: 100%;">
									<tr>
									<c:if test="${dto.payoutstatus == 'Paid'}">	
										<td
											style="width: 50%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											SRC Ref No</td>
										<td
											style="width: 5%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											:</td>
										<td
											style="width: 45%; padding-right: 5px;  padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152; word-break: break-all;">

											${dto.srcrefno}</td>
											</c:if>
									</tr>
									
								</table>
							</td>

						</tr>
					
					


						<tr>
							<td>

								<table style="width: 100%;">
									<tr>
										<td
											style="width: 50%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											Invoice Id</td>
										<td
											style="width: 5%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											:</td>
										<td
											style="width: 45%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152; word-break: break-all;">

											${dto.invoiceNo}</td>
									</tr>
								</table>
							</td>

						</tr>


                    <tr>
							<td>

								<table style="width: 100%;">
									<tr>
										<td
											style="width: 50%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											Status</td>
										<td
											style="width: 5%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											:</td>
										<td
											style="width: 45%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152; word-break: break-all;">

											${dto.payoutstatus}</td>
									</tr>
								</table>
							</td>

						</tr>
						
						  <tr>
							<td>

								<table style="width: 100%;">
													
									<tr>
									<c:if test="${dto.payoutstatus == 'Declined'}">	
										<td
											style="width: 50%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											Failure Reason</td>
										<td
											style="width: 5%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											:</td>
										<td
											style="width: 45%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152; word-break: break-all;">

											${dto.failurereason}</td>
												</c:if>
									</tr>
								</table>
								
							</td>

						</tr>


              <tr>
							<td>

								<table style="width: 100%;">
									<tr>
										<td
											style="width: 50%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											Payout Type</td>
										<td
											style="width: 5%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											:</td>
										<td
											style="width: 45%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											${dto.payoutType}</td>
									</tr>
								</table>
							</td>

						</tr>




						<tr>
							<td>



								<table style="width: 100%">
									<tr>
										<td
											style="width: 50%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											Payment Method</td>
										<td
											style="width: 5%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">

											:</td>
										<td
											style="width: 45%; padding-right: 5px; padding-left: 5px; font-size: 12px; font-family: Helvetica; color: #3e4152;">


										<img class="w24" width="50" height="50"
																src='data:image/png;base64,<%= payoutslip %> ' />


</td>
</tr>
</table>
</td>



 </tr>




<!-- TRANSACTION DETAILS -->
<tr>
<td style="padding-left: 5px;padding-right:5px;font-weight: 500;font-size: 12px; font-family: Helvetica;color:#9b9b9b;">

<p></p>
</td>
</tr>

<tr>
<td style="color:#8c8c8c;text-align:center;font-size:10px;">

For questions about this email contact: <span style=color:#005baa;">csmobi@gomobi.io</span>
										</td>

									</tr>

									<tr>
										<td
											style="padding-left: 5px; padding-right: 5px; font-weight: 500; font-size: 12px; font-family: Helvetica; color: #9b9b9b;">

											<p></p>
										</td>
									</tr>

									<tr>
										<td
											style="color: #005baa; text-align: center; font-size: 10px;">

											Mobi Asia Sdn. Bhd"</td>

									</tr>


								</table>
							</td>

						</tr>

						<!--CUSTOMER CARD DETAILS -->

					</table>

				</td>
			</tr>
		</table>

		</td>
		</tr>

		</table>
	</center>



</body>
</html>
