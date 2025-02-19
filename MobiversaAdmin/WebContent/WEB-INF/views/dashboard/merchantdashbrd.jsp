<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@page import="com.mobiversa.payment.controller.HomeController"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page import="com.mobiversa.payment.util.PropertyLoader"%>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<%@ page import="org.apache.commons.codec.binary.Hex" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="com.mobiversa.common.bo.Merchant" %>


<html>

<head>

<!--bootstarp new -->
<!--<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/bootstrap/bootstrap.min.css">-->
<!--<script
	src="${pageContext.request.contextPath}/resourcesNew1/dist/bootstrap/bootstrap.min.js"></script>-->
<!--<script
	src="${pageContext.request.contextPath}/resourcesNew1/dist/bootstrap/jquery-3.2.1.slim.min.js"></script>-->
<!--<script
	src="${pageContext.request.contextPath}/resourcesNew1/dist/bootstrap/popper.min.js"></script>-->

<!--  <link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew/css/bootstrap.min.css"> -->




<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/custom.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/fonts.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/font-awesome-master/css/font-awesome.min.css">

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<style>
.scroll-remove::-webkit-scrollbar {
	-webkit-appearance: none;
}

.scroll-remove::-webkit-scrollbar:vertical {
	width: 6px;
}

.scroll-remove::-webkit-scrollbar:horizontal {
	height: 6px;
}

.scroll-remove::-webkit-scrollbar-thumb {
	background-color: rgba(0, 0, 0, .5);
	border-radius: 10px;
	border: 2px solid #ffffff;
}

.scroll-remove:-webkit-scrollbar-track {
	border-radius: 6px;
	background-color: #ffffff;
}

.c-0e89e3 {
	color: #0e89e3;
}

.fs-80-pt {
	font-size: 80%;
}

.w15p {
	width: 15%;
}

.h1-mobi, .h2-mobi, .h3-mobi, .h4-mobi, .h5-mobi, .h6-mobi {
	margin-top: 0 !important;
	margin-bottom: .5rem !important;
	font-weight: 500 !important;
	line-height: 1.2 !important;
}

.btn.disabled, .control-btn:disabled, .btn:disabled, fieldset:disabled .btn
	{
	pointer-events: none;
	opacity: .65;
}

.mobi-btxtb {
	color: #005baa;
	font-weight: bold;
}

.h3-mobi {
	font-size: 1.75rem !important;
}

.col-1 {
	width: 8.33%;
	float: left;
	padding: 0 0.75rem
}

.col-2 {
	width: 16.66%;
	float: left;
	padding: 0 0.75rem
}

.col-3 {
	width: 25%;
	float: left;
	padding: 0 0.75rem
}

.col-4 {
	width: 33.33%;
	float: left;
	padding: 0 0.75rem
}

.col-5 {
	width: 41.66%;
	float: left;
	padding: 0 0.75rem
}

.col-6 {
	width: 50%;
	float: left;
	/*padding: 0 0.75rem*/
}

.col-7 {
	width: 58.33%;
	float: left;
	padding: 0 0.75rem
}

.col-8 {
	width: 66.66%;
	float: left;
	padding: 0 0.75rem
}

.col-9 {
	width: 75%;
	float: left;
	padding: 0 0.75rem
}

.col-10 {
	width: 83.33%;
	float: left;
	padding: 0 0.75rem
}

.col-11 {
	width: 91.66%;
	float: left;
	padding: 0 0.75rem
}

.col-12 {
	width: 100%;
	float: left;
	padding: 0 0.75rem height:
}

.c-8 {
	height: 100%;
}

.hide_key {
	display: none;
}

@media only screen and (max-width: 768px) {
	[class*="col-"] {
		width: 100%;
		float: left /* padding: 0 0.75rem */
	}
}

.table-new thead tr {
	background: #d7e7ea;
}

.mobi-float-end {
	float: right !important;
}

.table-new thead tr th {
	border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important;
	padding: 10px !important;
}

.table-new tbody tr td {
	padding: 10px !important;
	border: none !important;
}

.table-new tbody tr {
	border-bottom: none !important;
}

.bg-f3fbfd {
	background-color: #f3fbfd;
}

.br-5 {
	border-radius: 5px;
}

.c-092540 {
	color: #092540;
}

.h-139 {
	height: 139px;
}

.h-200 {
	height: 200px;
}

.c-0e89e3 {
	color: #0e89e3;
}

.c-151931 {
	color: #151931;
}

.mb-40 {
	margin-bottom: 40px;
}

.chart_div svg {
	display: block !important;
}

.chart_div figure {
	margin: 0px;
}

.btccc {
	border-top: solid 1px #ccc;
}

.align-items-center .c-151931 {
	color: #151931 !important;
	z-index: 2;
}

.back-color {
	background-color: #fff;
}

.table-head {
	background-color: #005baa !important;
}

.cancel-Ezysettle-Button:hover {
	color: #fff;
	background-color: #676668;
}

.submit-Ezysettle-Button:hover {
	color: #fff !important;
	background-color: #683966 !important;
}
</style>

<script src="https://code.highcharts.com/highcharts.js"></script>

<style>
td, th {
	padding: 0px 8px;
}

thead th {
	border-bottom: 1px solid #ffa500;
	color: #4377a2;
}

.bgA1A0A2 {
	background-color: #A1A0A2
}

.bcA1A0A2 {
	border: solid 1px #A1A0A2
}
</style>


<style>
.mobi-modal {
	position: fixed;
	top: 25%;
	left: 0;
	z-index: 1060;
	width: 100%;
	height: 100%;
	overflow-x: hidden;
	overflow-y: auto;
	outline: 0;
}

.mobi-modal-dialog {
	max-width: 500px;
	margin: 1.75rem auto;
}

.mobi-modal-content {
	position: relative;
	display: flex;
	flex-direction: column;
	width: 100%;
	pointer-events: auto;
	background-color: #fff;
	background-clip: padding-box;
	border: 1px solid rgba(0, 0, 0, .2);
	border-radius: 14px;
	outline: 0;
}

.mobi-popup-small {
	font-size: 13px;
	color: #808080;
}

.mobi-modal-header {
	display: flex;
	flex-shrink: 0;
	align-items: center;
	justify-content: space-between;
	padding: 1rem 1rem;
	/*	border-bottom: 1px solid #dee2e6; */
	border-top-left-radius: calc(0.3rem - 1px);
	border-top-right-radius: calc(0.3rem - 1px);
	font-size: 22px !important;
}

.mobi-modal-body {
	position: relative;
	flex: 1 1 auto;
	padding: 1rem;
	font-size: 20px !important;
}

.fz-22 {
	font-size: 22px
}

.mobi-modal-footer {
	display: flex;
	flex-wrap: wrap;
	flex-shrink: 0;
	align-items: center;
	justify-content: flex-end;
	padding: 0.75rem;
	/*	border-top: 1px solid #dee2e6; */
	border-bottom-right-radius: calc(0.3rem - 1px);
	border-bottom-left-radius: calc(0.3rem - 1px);
}

.mobi-modal-title {
	margin-bottom: 0;
	line-height: 1.5;
}

.mobi-popup-btn-secondary {
	color: #fff !important;
	background-color: #A1A0A2 !important;
	border-color: #A1A0A2 !important;
}

.mobi-text-dark {
	padding: 14px;
	color: #212529;
}

.mobi-text-dark1 {
	color: #212529;
}

.mobi-popup-btn {
	display: inline-block;
	font-weight: 400;
	line-height: 1.5;
	color: #212529;
	text-align: center;
	text-decoration: none;
	vertical-align: middle;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
	background-color: transparent;
	border: 1px solid transparent;
	padding: 0.375rem 0.75rem;
	font-size: 1rem;
	border-radius: 0.25rem;
	transition: color .15s ease-in-out, background-color .15s ease-in-out,
		border-color .15s ease-in-out, box-shadow .15s ease-in-out;
}

#mobi_modal_popup {
	display: none;
}

#mobi_modal_popup_failure {
	display: none;
}

#overlay-popup_failure {
	position: fixed;
	display: none;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: #000;
	opacity: 0.5;
	z-index: 99;
	cursor: pointer;
}

#eligible-ezysettle {
	display: none;
}

#overlay-popup {
	position: fixed;
	display: none;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: #000;
	opacity: 0.5;
	z-index: 99;
	cursor: pointer;
}

#overlay-popup1 {
	position: fixed;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: #000;
	opacity: 0.5;
	z-index: 99;
	cursor: pointer;
}

.mobi-text-right {
	text-align: right;
}

.date_circle {
	width: 80px;
	height: 80px;
	border-radius: 40px;
	color: #fff;
	font-weight: 600;
	display: flex;
	align-items: center !important;
	justify-content: center !important;
	font-size: 20px;
}

.gradient864cf5 {
	background: rgb(134, 76, 245);
	background: linear-gradient(156deg, rgba(134, 76, 245, 1) 39%,
		rgba(161, 115, 250, 1) 75%, rgba(159, 119, 238, 1) 100%);
}

.gradientf551c1 {
	background: rgb(245, 81, 193);
	background: linear-gradient(156deg, rgba(245, 81, 193, 1) 39%,
		rgba(251, 135, 215, 1) 75%, rgba(255, 161, 237, 1) 100%);
}

.gradient5b45f8 {
	background: rgb(91, 69, 248);
	background: linear-gradient(156deg, rgba(91, 69, 248, 1) 39%,
		rgba(119, 100, 252, 1) 75%, rgba(119, 100, 252, 1) 100%);
}

.gradient11ac4d {
	background: rgb(17, 172, 77);
	background: linear-gradient(156deg, rgba(17, 172, 77, 1) 39%,
		rgba(55, 200, 106, 1) 75%, rgba(67, 215, 116, 1) 100%);
}

.gradientfc496e {
	background: rgb(252, 73, 110);
	background: linear-gradient(156deg, rgba(252, 73, 110, 1) 39%,
		rgba(254, 73, 111, 1) 75%, rgba(254, 122, 149, 1) 100%);
}

.mobi-mr-10 {
	/*	margin-right: 10px; */
	margin-right: 43px;
}

/*	Ezysettle check box */
/* .hiddencheck-box {
   display: none !important;
} */
</style>



</head>

<body class="body-mobi">

<%
	String uri =null;
%>


<c:if test="${isActualMerchant.toLowerCase() == 'no'}">
<%
	//Jenfi
	Merchant merchant = (Merchant) request.getAttribute("merchant");

	System.out.println("Merchant @######## : " +  merchant.getEmail());

	String secret_api_key = (String) request.getAttribute("secretApiKey");
	String base_url = (String) request.getAttribute("baseUrl");
	String partner_ref = (String) request.getAttribute("patnerRef");
	    String merchant_ref = (String) request.getAttribute("merchant_ref");
//	    String merchant_ref = "5f0e0d32-5262-4cb0-aa8c-dbede0e8c1b4-1234";
//	String merchant_ref = "96ba65fb3-c86186-JOlY18-9abZZYe-005bo645358zzS44-4";
	String merchant_legal_name = merchant.getBusinessName();
	//For UAT Merchant - Email need to be uniqnetstat -ano | findstr :8005
	String merchant_email = merchant.getEmail();
//	String merchant_email = "sheikMobi0186@gmail.com";
	String merchant_currency = (String) request.getAttribute("currencyType");

	System.out.println("secret_api_key : " + secret_api_key);
	System.out.println("base_url: " + base_url);
	System.out.println("partner_ref : " + partner_ref);
	System.out.println("merchant_ref : " + merchant_ref);
	System.out.println("merchant_legal_name : " + merchant_legal_name);
	System.out.println("merchant_email : " + merchant_email);
	System.out.println("merchant_currency : " + merchant_currency);

	// Hash raw values (not URL encoded)
	String raw_signature = String.join("+", partner_ref, merchant_ref, merchant_legal_name, merchant_email,
			merchant_currency, secret_api_key);

	// Create a MessageDigest instance and hash the raw_signature using SHA-256
	MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
	byte[] hashedBytes = messageDigest.digest(raw_signature.getBytes(StandardCharsets.UTF_8));
	String signed_signature = new String(Hex.encodeHex(hashedBytes));

	// Now URL Encode the query parameters
	String queryParams = String.format(
			"partner_ref=%s&merchant_ref=%s&merchant_legal_name=%s&merchant_email=%s&merchant_currency=%s&sig=%s",
			URLEncoder.encode(partner_ref, "UTF-8"), URLEncoder.encode(merchant_ref, "UTF-8"),
			URLEncoder.encode(merchant_legal_name, "UTF-8"), URLEncoder.encode(merchant_email, "UTF-8"),
			URLEncoder.encode(merchant_currency, "UTF-8"), URLEncoder.encode(signed_signature, "UTF-8"));

	 uri = String.format("https://cdn.partners.%s/static/bundles/jcard.js?%s", base_url, queryParams);
%>
</c:if>







	<div id="overlay">
		<div id="overlay_text">
			<img class="img-fluid"
				src="${pageContext.request.contextPath}/resourcesNew/img/loader.gif">
		</div>
	</div>



	<div class="container-fluid">
		<div class="row res">
			<div class="col s12">
				<div>
					<c:if test="${isJenfiEnabled.toLowerCase() == 'true'}">

						<div class="" style="margin-left: 1rem;">
							<script async src="<%=uri%>"></script>
						</div>
					</c:if>
					<h5
						style="color: #005baa !important; font-weight: bold; float: left;">Dashboard
					</h5>
					<c:if
						test="${not empty merchant.mid.motoMid || not empty merchant.mid.umMotoMid}">
						<c:if
							test="${ezyAuthEnable == Yes || merchant.auth3DS == 'No' || merchant.auth3DS == 'Yes'}">
							<a
								href="${pageContext.request.contextPath}/transactionUmweb/Transaction"
								class="btn-large mb-2 dashbtn"
								style="background-color: #1c60ac; margin-top: -15px; float: right; display: block">Payment Request</a>
						</c:if>
					</c:if>
				</div>
			</div>
		</div>


		<div class="row-c" id="">



			<div class="col-8">

				<div class="row-c">



					<div class="col-4 mb-5" onclick="onselectdivone()">
						<div
							class="card-mobi h-200 p-3 mobi-flex mobi-justify-content-center"
							style="justify-content: flex-start;">
							<div
								class="row-c mb-3 d-flex align-items-center justify-content-center">
								<div class="col-4">

									<img class="img-fluid"
										src="${pageContext.request.contextPath}/resourcesNew1/assets/${cdateone}.png">
								</div>
								<div class="col-8">
									<c:choose>
										<c:when test="${Status == 'ACTIVE'}">
											<input disabled
												class="float-end payout-count check-20 check-mobi-right"
												type="checkbox" id="box1" name="monday_check">
										</c:when>

										<c:when test="${TotalsettledateoneNetAmount == '0.00'}">
											<input disabled
												class="float-end payout-count check-20 check-mobi-right"
												type="checkbox" id="box1" name="monday_check">
										</c:when>
										<c:otherwise>
											<input onclick="onselectdivone()"
												class="float-end payout-count check-20 check-mobi-right"
												type="checkbox" id="box1" name="monday_check">
										</c:otherwise>
									</c:choose>
								</div>

							</div>
							<div class="row-c">
								<div class="col-12">
									<h6 class="c6e6e6e small mobi-text-right mobi-mr-10 h6-mobi">On
										${Fonedate}</h6>
									<input type="hidden" id="input-date-one" name="hiddenid"
										value="${Fsettlementdateone}">
									<h3
										class="c484849 mobi-text-right mobi-mr-10 font-Rubik h3-mobi">RM
										${TotalsettledateoneNetAmount}</h3>
									<input type="hidden" id="input-netamt-one" name="one-net"
										value="${TotalsettledateoneNetAmount}">
								</div>
							</div>
						</div>
					</div>

					<div class="col-4 mb-5" onclick="onselectdivtwo()">
						<div class="card-mobi h-200 p-3">
							<div
								class="row-c mb-3 d-flex align-items-center justify-content-center">
								<div class="col-4">

									<img class="img-fluid"
										src="${pageContext.request.contextPath}/resourcesNew1/assets/${cdatetwo}.png">
								</div>
								<div class="col-8">
									<div id="loginCheckBox" class="loginCheckBox">
										<c:choose>
											<c:when test="${TotalsettledatetwoNetAmount == '0.00'}">
												<input disabled
													class="float-end payout-count check-20 check-mobi-right"
													type="checkbox" id="box2" name="monday_check">
											</c:when>
											<c:otherwise>
												<input onclick="onselectdivtwo()"
													class="float-end payout-count check-20 check-mobi-right"
													type="checkbox" id="box2" name="monday_check">
											</c:otherwise>
										</c:choose>
									</div>
									<!--  	<input class="float-end payout-count check-20 check-mobi-right"
										type="checkbox" id="box2" name="monday_check"> -->
								</div>
							</div>
							<div class="row-c">
								<div class="col-12">
									<h6 class="c6e6e6e small mobi-text-right mobi-mr-10 h6-mobi">On
										${Ftwodate}</h6>
									<input type="hidden" id="input-date-two" name="hiddenid"
										value="${Fsettlementdatetwo}">
									<h3
										class="c484849 mobi-text-right mobi-mr-10 font-Rubik h3-mobi">RM
										${TotalsettledatetwoNetAmount}</h3>
									<input type="hidden" id="input-netamt-two" name="hiddenid2"
										value="${TotalsettledatetwoNetAmount}">
								</div>
							</div>
						</div>
					</div>


					<div class="col-4 mb-5" onclick="onselectdivthree()">
						<div class="card-mobi h-200 p-3">
							<div
								class="row-c mb-3 d-flex align-items-center justify-content-center">
								<div class="col-4">

									<img class="img-fluid"
										src="${pageContext.request.contextPath}/resourcesNew1/assets/${cdatethree}.png">
								</div>
								<div class="col-8">
									<div id="loginCheckBox" class="loginCheckBox">
										<c:choose>
											<c:when test="${TotalsettledatethreeNetAmount == '0.00'}">
												<input disabled
													class="float-end payout-count check-20 check-mobi-right"
													type="checkbox" id="box3" name="monday_check">
											</c:when>
											<c:otherwise>
												<input onclick="onselectdivthree()"
													class="float-end payout-count check-20 check-mobi-right"
													type="checkbox" id="box3" name="monday_check">
											</c:otherwise>
										</c:choose>

										<!--  	<input class="float-end payout-count check-20 check-mobi-right"
										type="checkbox" id="box3" name="monday_check"> -->
									</div>
								</div>
							</div>
							<div class="row-c">
								<div class="col-12">
									<h6
										class="c6e6e6e mobi-text-right  mobi-mr-10 mobi-mr-10small h6-mobi">
										On ${Fthreedate}</h6>
									<input type="hidden" id="input-date-three" name="hiddenid"
										value="${Fsettlementdatethree}">
									<h3
										class="c484849 mobi-text-right mobi-mr-10 font-Rubik h3-mobi">RM
										${TotalsettledatethreeNetAmount}</h3>
									<input type="hidden" id="input-netamt-three" name="hiddenid3"
										value="${TotalsettledatethreeNetAmount}">
								</div>
							</div>
						</div>
					</div>











					<div class="col-4 mb-5" onclick="onselectdivfour()">
						<div class="card-mobi h-200 p-3">
							<div
								class="row-c mb-3 d-flex align-items-center justify-content-center">
								<div class="col-4">

									<img class="img-fluid"
										src="${pageContext.request.contextPath}/resourcesNew1/assets/${cdatefour}.png">
								</div>
								<div class="col-8">
									<div id="loginCheckBox" class="loginCheckBox">
										<c:choose>
											<c:when test="${TotalsettledatefourNetAmount == '0.00'}">
												<input disabled
													class="float-end payout-count check-20 check-mobi-right"
													type="checkbox" id="box4" name="monday_check">
											</c:when>
											<c:otherwise>
												<input onclick="onselectdivfour()"
													class="float-end payout-count check-20 check-mobi-right"
													type="checkbox" id="box4" name="monday_check">
											</c:otherwise>
										</c:choose>
									</div>

									<!--  	<input class="float-end payout-count check-20 check-mobi-right"
										type="checkbox" id="box4" name="monday_check"> -->
								</div>
							</div>
							<div class="row-c">
								<div class="col-12">
									<h6 class="c6e6e6e mobi-text-right mobi-mr-10 small h6-mobi">On
										${Ffourdate}</h6>
									<input type="hidden" id="input-date-four" name="hiddenid"
										value="${Fsettlementdatefour}">
									<h3
										class="c484849 mobi-text-right mobi-mr-10 font-Rubik h3-mobi">RM
										${TotalsettledatefourNetAmount}</h3>
									<input type="hidden" id="input-netamt-four" name="hiddenid4"
										value="${TotalsettledatefourNetAmount}">
								</div>
							</div>
						</div>
					</div>





					<div class="col-4 mb-5" onclick="onselectdivfive()">
						<div class="card-mobi h-200 p-3">
							<div
								class="row-c mb-3 d-flex align-items-center justify-content-center">
								<div class="col-4">

									<img class="img-fluid"
										src="${pageContext.request.contextPath}/resourcesNew1/assets/${cdatefive}.png">
								</div>
								<div class="col-8">

									<div id="loginCheckBox" class="loginCheckBox">
										<c:choose>
											<c:when test="${TotalsettledatefiveNetAmount == '0.00'}">
												<input disabled
													class="float-end payout-count check-20 check-mobi-right"
													type="checkbox" id="box5" name="monday_check">
											</c:when>
											<c:otherwise>
												<input onclick="onselectdivfive()"
													class="float-end payout-count check-20 check-mobi-right"
													type="checkbox" id="box5" name="monday_check">
											</c:otherwise>
										</c:choose>
									</div>
									<!-- 	<input class="float-end payout-count check-20 check-mobi-right"
										type="checkbox" id="box5" name="monday_check">  -->
								</div>
							</div>
							<div class="row-c">
								<div class="col-12">
									<h6 class="c6e6e6e mobi-text-right mobi-mr-10 small h6-mobi">On
										${Ffivedate}</h6>
									<input type="hidden" id="input-date-five" name="hiddenid"
										value="${Fsettlementdatefive}">
									<h3
										class="c484849 mobi-text-right mobi-mr-10 font-Rubik h3-mobi">RM
										${TotalsettledatefiveNetAmount}</h3>
									<input type="hidden" id="input-netamt-five" name="hiddenid5"
										value="${TotalsettledatefiveNetAmount}">
								</div>
							</div>
						</div>
					</div>





					<div class="col-4 c-8 mb-5" id="withdrawTab">
						<div class="card-mobi h-139 p-3 rounded-0">

							<div class="row-c p-3">
								<div class="col-12">
									<h3 class="c484849 h3-mobi text-center-mobi">
										<span id="payout-check-count">0</span> <span
											id="settlementText"></span>
									</h3>
									<h6 class="small c6e6e6e h6-mobi text-center-mobi "></h6>
								</div>
							</div>

						</div>

						<div class="card-mobi">
							<div class="">
								<div class="col-12 pe-0 ps-0">
									<button type="button" id="button-ctrl"
										onclick="showSidePanel()"
										class="btn-custom-3 btn-mobi bg005baa1 text-white-mobi col-12 control-btn"
										disabled>Withdraw</button>
								</div>

							</div>
						</div>




					</div>




				</div>
			</div>
			<div class="col-4 mb-2 side-panal" style="display: none;"
				id="side-panel">

				<div class="card-mobi p-3 rounded-0 ">
					<h3 class="c092540 h3-mobi">Settlements</h3>
					<div class="row-c p-2">




						<div
							class="col-2 d-flex align-items-center justify-content-center bg005baa rounded">
							<i class="fa-lg fa fa-calendar-o text-white-mobi"
								aria-hidden="true"></i>
						</div>
						<div class="col-10">
							<h6 class="small c969696 h6-mobi">Date</h6>
							<h6 class="c151931 h6-mobi" id="in-payoutdate"></h6>
							<input type="hidden" id="input-payout-date" name="hiddenid"
								value="0">
						</div>

						<input type="hidden" id="input-payout-vendor-fee" name="hiddenid"
							value="0">


					</div>
					<div class="row-c p-2">
						<div class="col-12 mobi-mt-30">
							<h6 class="c7e7e7e small mobi-mt-2 h6-mobi fz22">DETAILS</h6>
							<table class="table mt-2 mobi-mb-2 table-new fz18">
								<tbody>
									<tr>
										<td class="c969696">Total Net Amount (RM)</td>
										<td id="in-payamt" class="c151931 fz-22 mobi-float-end"></td>
									</tr>
									<input type="hidden" id="input-payable-amt" name="hiddenid"
										value="0">
									<tr>
										<td class="c969696">Withdrawal Fee (RM)</td>
										<td id="in-withdraw" class="c151931 fz-22 mobi-float-end"></td>
									</tr>
									<input type="hidden" id="input-withdraw-amt" name="hiddenid"
										value="0">
									<tr>
										<td class="c969696">Payable Amount (RM)</td>
										<td id="in-netamt" class="c151931 fz-22 mobi-float-end"></td>
									</tr>
									<input type="hidden" id="input-netamt-amt" name="hiddenid"
										value="0">



									<!-- <tr>
											<td class="c969696">Request Amount (RM)</td>
										<td id="curlec-request-amount" class="c151931 fz-22 mobi-float-end"></td>
									</tr> -->
									<!-- 
									<tr>
										
									</tr> -->

									<input type="hidden" id="curlec-request-amt"
										name="curlec-request-amount-data" value="">
								</tbody>
							</table>
							<!-- Not eligible condition -->
							<%--  <div id="eligible-ezysettle" style="color: #E66767;">  <img src="${pageContext.request.contextPath}/resourcesNew1/assets/information-line.svg" alt="SVG Image">   <span style ="text-align: center;"> Insufficient balance for EZYSETTLE withdrawal </span> </div> --%>
							<div id="eligible-ezysettle"
								style="text-align: center; color: #E66767; margin-top: -10px; position: absolute; left: 30px; font-size: 14px">
								<img style="vertical-align: middle; width: 20px; height: 20px;"
									src="${pageContext.request.contextPath}/resourcesNew1/assets/information-line.svg"
									alt=""> <span style="vertical-align: middle">Insufficient
									balance for EZYSETTLE withdrawal</span>
							</div>
							<%-- <div id="eligible-ezysettle" style="color: #E66767;">  <img src="${pageContext.request.contextPath}/resourcesNew1/assets/information-line.svg" alt="SVG Image">   <span style ="text-align: center;"> Insufficient balance for EZYSETTLE withdrawal </span> </div> --%>
						</div>
					</div>
				</div>




				<div class="card-mobi">
					<div class="row-c">
						<div class="col-6 pe-0">
							<button type="button" onclick="closeSidePanel()"
								id="cancelEzysettleButton"
								class="btn-custom-1 btn-mobi bgA1A0A2 bcA1A0A2  text-white-mobi col-12 cancel-Ezysettle-Button">Cancel</button>
						</div>
						<div class="col-6 ps-0">
							<button type="button" onclick="closepayoutPanel()"
								id="submitEzysettleButton"
								class="btn-custom-2 btn-mobi bg005baa1 text-white-mobi col-12 submit-Ezysettle-Button">Submit</button>
						</div>
					</div>
				</div>
			</div>
		</div>


		<%
		String usernames = PropertyLoader.getFile().getProperty("DG_TECK_USER_NAME");
		String[] validUsernames = usernames != null ? usernames.split(",") : new String[0]; // Assuming usernames are comma-separated

		java.security.Principal principal = request.getUserPrincipal();
		String loginName = (principal != null) ? principal.getName() : "";

		boolean isValid = false;
		for (String username : validUsernames) {
			if (loginName.equals(username.trim())) {
				isValid = true;
				break;
			}
		}
		%>


		<div class="row ">
			<div class="col-8 mb-40">
				<div class="">
					<h6 class="c-092540"></h6>
					<div class="table-responsive scroll-remove m-b-20 m-t-15">
						<table class="table-new bg-f3fbfd">
							<thead>
								<tr class=" bgd7e7ea">
									<th class="c-151931">App Username</th>
									<th class="c-151931">TID</th>
									<th class="c-151931">API key</th>

									<th class="c-151931">Expiry Date</th>
									<th class="c-151931">Ref No</th>

									<%
									if (!isValid) {
									%>

									<th class="c-151931">Edit</th>
									<%
									}
									%>


								</tr>
							</thead>
							<tbody class="back-color">

								<c:forEach items="${paginationBean2.itemList}" var="dtoo"
									varStatus="count">
									<tr>
										<td class="c-151931">${dtoo.mobileUserName}</td>
										<td class="c-151931">${dtoo.tid}</td>
										<td class="c-0e89e3 fs-80-pt"><span
											onclick="show_key('${paginationBean2.itemList[count.index]}')"
											id="hide_key_${paginationBean2.itemList[count.index]}">
												View key <!-- <img  src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABkAAAAPBAMAAADjSHnWAAAABGdBTUEAALGPC/xhBQAAAAFzUkdCAK7OHOkAAAAwUExURUdwTCCP7x+S5B6S5h6S5h6R5B+S5R2S5BuP5B+R5RyT5x2T5hiP5x6R5R+S5R+S5dsDYYsAAAAPdFJOUwAQ8HifkM1gMN9AUCCw4M+dzv0AAACUSURBVAjXY2BgYOCxnf8/ZQMDBPD4/weCT4YQXvz/fxZcQf9/gmXF/v+/yMDAof//KwOY+icg7M6w/v9/BwYGof//PzPO/x/A9v//FwaG/P//f7H///+BGWhSAZzHCuQZgFR+Z3wPUwk05SeD8HWG/WBTGI6BKagNDIzx/38WcsX//wRxG8d9ZJcx8Jjp/08pZMAAAGB3VY53Q2EuAAAAAElFTkSuQmCC"> -->
												<i class="fa fa-eye"></i>

										</span> <span id="show_key_${paginationBean2.itemList[count.index]}"
											class="hide_key">${dtoo.motoapikey}</span></td>


										<td class="c-151931">${dtoo.expiryDate}</td>
										<td class="c-151931">${dtoo.refno}</td>



										<%
										if (!isValid) {
										%>
										<c:if test="${not empty dtoo.expiryDate}">

											<td>
											<%-- 	<form method="post" id="form-edit"
													action="${pageContext.request.contextPath}/readerweb/detail">

													<input type="hidden" name="deviceId"
														value="${dtoo.deviceId}" /> <input type="hidden"
														name="${_csrf.parameterName}" value="${_csrf.token}">

													<c:if test="${not empty dtoo.expiryDate}">


														<center>
															<button type="submit" class="editBtn">
																<i class="material-icons">create</i>
															</button>
														</center>

													</c:if>


												</form> --%>
												<style>
.editBtn {
	color: #039be5;
	background: none !important;
	text-decoration: none !important;
	border: none;
	-webkit-tap-highlight-color: transparent;
}
</style>


											</td>
										</c:if>
										<%
										}
										%>


									</tr>
								</c:forEach>



							</tbody>
						</table>
					</div>
				</div>
			</div>
			<!-- <div class="col-4 mb-40">

				<div class="table-responsive m-b-20 m-t-15">
					<table class="table-new bg-ffffff">
						<thead>
							<tr class=" bgd7e7ea">
								<th class="c-151931">Success / Failure Ratio Breakdown</th>
							</tr>
						</thead>
						<tr>
							<td>
								<div class="chart_div">
									<figure class="highcharts-figure">
										<div id="pie_chart_container"></div>
									</figure>
								</div>
							</td>
						</tr>
					</table>
				</div>


			</div> -->







			<!-- 

			Modal
			<div class="modal fade" id="enable-success-popup"
				data-backdrop="static" tabindex="-1"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">Status</h5>
							<button type="button" class="btn-close"></button>
						</div>
						<div class="modal-body">
							<p>Early Settlement Successfully Completed....</p>
						</div>

						<div class="modal-footer">

							<button type="button" onClick="refreshPage()"
								class="btn btn-primary">Ok</button>
						</div>
					</div>
				</div>
			</div> -->


			<div id="overlay-popup"></div>
			<div class="mobi-modal" id="mobi_modal_popup" tabindex="-1">
				<div class="mobi-modal-dialog">
					<div class="mobi-modal-content">
						<div class="mobi-modal-header">
							<h5 class="mobi-modal-title mobi-text-dark"></h5>
							<img class="img-fluid mobi-close" onclick="refreshPage()"
								src="${pageContext.request.contextPath}/resourcesNew1/assets/close.png">

						</div>



						<div class="mobi-modal-body mobi-text-sucess">
							<img class="img-fluid mobi-sucess"
								src="${pageContext.request.contextPath}/resourcesNew1/assets/success.png">
							<!--  	<p class="mobi-popup-small">Payment has been Processed</p>  -->
							<div>
								<p class="mobi-text-dark">
									You will receive <span class="mobi-btxtb">RM <span
										id="sucess-netamt"></span></span>
								</p>
							</div>
							<div>
								<p class="mobi-text-dark1">
									on <span class="mobi-btxtb" id="sucess-date"></span>
								</p>
							</div>
						</div>
						<div class="mobi-modal-footer"></div>
					</div>
				</div>
			</div>






			<div id="overlay-popup_failure"></div>
			<div class="mobi-modal" id="mobi_modal_popup_failure" tabindex="-1">
				<div class="mobi-modal-dialog">
					<div class="mobi-modal-content">
						<div class="mobi-modal-header">
							<h5 class="mobi-modal-title mobi-text-dark"></h5>
							<img class="img-fluid mobi-close" onclick="refreshPage()"
								src="${pageContext.request.contextPath}/resourcesNew1/assets/close.png">
						</div>
						<div class="mobi-modal-body mobi-text-sucess">
							<img class="img-fluid mobi-sucess"
								src="${pageContext.request.contextPath}/resourcesNew1/assets/Failure.png">
							<div>
								<p class="mobi-text-dark" id="dynamic-content"> </p>
							</div>
						</div>
						<div class="mobi-modal-footer">
							<!-- <button type="button" id="mobi_popup_btn_close"
								onclick="refreshPage()"
								class="mobi-popup-btn mobi-popup-btn-secondary">Close</button> -->
						</div>
					</div>
				</div>
			</div>




			<!-- Restrict Ezysettle Payable Amount goes Negative  -->
			<div id="overlay-popup_failure"></div>
			<%-- <div class="mobi-modal" id="eligible-ezysettle" tabindex="-1">
				<div class="mobi-modal-dialog">
					<div class="mobi-modal-content">
						<div class="mobi-modal-header">
							<h5 class="mobi-modal-title mobi-text-dark"></h5>
							<img class="img-fluid mobi-close" onclick="refreshPage()"
								src="${pageContext.request.contextPath}/resourcesNew1/assets/close.png">
						</div>
						<div class="mobi-modal-body mobi-text-sucess">
							<img class="img-fluid mobi-sucess"
								src="${pageContext.request.contextPath}/resourcesNew1/assets/Failure.png">
							<div>
								<p class="mobi-text-dark">Insufficient balance for EZYSETTLE withdrawal.</p>
							</div>
						</div>
						<div class="mobi-modal-footer">
							<!-- <button type="button" id="mobi_popup_btn_close"
								onclick="refreshPage()"
								class="mobi-popup-btn mobi-popup-btn-secondary">Close</button> -->
						</div>
					</div>
				</div>
			</div>
 --%>


			<!--  New Product Pop   -->
			<div id="overlay-popup1"></div>
			<div class="mobi-modal" id="mobi_modal_featurepopup"
				onclick="closefeature()" tabindex="-1">
				<div class="mobi-modal-dialog">
					<div class="mobi-modal-content">
						<div class="mobi-modal-header">
							<h5 class="mobi-modal-title mobi-text-dark"></h5>

						</div>

						<div class="mobi-modal-body mobi-text-sucess">
							<div class="mobi-text-dark"
								style="display: flex; flex-direction: row; align-items: center">
								<span class="mobi-btxtb">New product: Try out EZYSETTLE,
									to get your funds faster <span><img
										style="height: 30px; width: 30px"
										class="img-fluid mobi-sucess"
										src="${pageContext.request.contextPath}/resourcesNew1/assets/smile.jpg"></span>
								</span>
							</div>
						</div>
						<div class="mobi-modal-footer">
							<button type="button" id="mobi_popup_btn_close"
								class="mobi-popup-btn mobi-popup-btn-secondary"
								onclick="closefeature()">Close</button>
						</div>
					</div>
				</div>
			</div>




			<script>
				$(document).ready(
						function() {
							// Get the login name from the backend
							const loginName = "${loginname}"; // This value needs to be provided from your backend

							const loginCheckBox1 = document
									.getElementById("box1");
							const loginCheckBox2 = document
									.getElementById("box2");
							const loginCheckBox3 = document
									.getElementById("box3");
							const loginCheckBox4 = document
									.getElementById("box4");
							const loginCheckBox5 = document
									.getElementById("box5");
							const withdrawtab = document
									.getElementById("withdrawTab");

							/* alert(loginCheckBox1,loginCheckBox2,loginCheckBox3,loginCheckBox4,loginCheckBox5); */
							// Check if the login name matches "mobi" or "pcitest" or "test"
							if (loginName.toLowerCase() === "pcitest"
									|| loginName.toLowerCase() === "mobi"
									|| loginName.toLowerCase() === "ethan" || loginName.toLowerCase() === "masterAgent@mobiversa.com") {

								withdrawtab.style.display = "none";
								loginCheckBox1.style.display = "none";
								loginCheckBox2.style.display = "none";
								loginCheckBox3.style.display = "none";
								loginCheckBox4.style.display = "none";
								loginCheckBox5.style.display = "none";

							} else {
								// Hide the login checkbox

								withdrawtab.style.display = "block";
								loginCheckBox1.style.display = "block";
								loginCheckBox2.style.display = "block";
								loginCheckBox3.style.display = "block";
								loginCheckBox4.style.display = "block";
								loginCheckBox5.style.display = "block";

							}
						});

				const inputPayableAmt = document
						.getElementById("input-payable-amt");
				const inputwithdrawfee = document
						.getElementById("input-withdraw-amt");
				const inputNetamt = document.getElementById("input-netamt-amt");
				/* const inputPaydate = document
						.getElementById("input-payout-date"); */
				
				
				function show_key(id) {

					document.getElementById('show_key_' + id).style.display = "block";
					document.getElementById('hide_key_' + id).style.display = "none";

				}

				/* Highcharts.chart('pie_chart_container', {
					chart: {
						type: 'pie',

						options3d: {
							enabled: true,
							alpha: 45,

						}


					},

					colors: ['#005baa', '#a1a0a2'],
					title: {
						text: ''
					},
					subtitle: {
						text: ''
					},
					credits: {
						enabled: false
					},

					plotOptions: {
						pie: {
							innerSize: 150,
							depth: 45
						}
					},

					tooltip: {
						headerFormat: '<span style="font-size:11px">{series.name}</span><br>',
						pointFormat: '<span style="color:{point.color}">{point.name}</span>: <b>{point.y:.2f}%</b> <br/>'
					},


					series: [{
						name: '',
						data:
							[
								['Success', ${ sucess }],
								['Failure', ${ failure }],

							]
					}]
				}); */

				function closeSidePanel() {
					document.getElementById("side-panel").style.display = "none";

					document.getElementById('payout-check-count').innerHTML = 0;

					
					
					var ipnetone = "${TotalsettledateoneNetAmount}";
					var ipnettwo = "${TotalsettledatetwoNetAmount}";
					var ipnetthree = "${TotalsettledatethreeNetAmount}";
					var ipnetfour = "${TotalsettledatefourNetAmount}";
					var ipnetfive = "${TotalsettledatefiveNetAmount}";

					var status = "${Status}";

					if (ipnetone == "0.00" || status == "ACTIVE") {
						$('#box1').attr('disabled', true);
					} else {
						$('#box1').attr('disabled', false);
						$('#box1').attr('checked', false);
					}

					if (ipnettwo == "0.00") {
						$('#box2').attr('disabled', true);
					} else {
						$('#box2').attr('disabled', false);
						$('#box2').attr('checked', false);
					}
					if (ipnetthree == "0.00") {
						$('#box3').attr('disabled', true);
					} else {
						$('#box3').attr('disabled', false);
						$('#box3').attr('checked', false);
					}
					if (ipnetfour == "0.00") {
						$('#box4').attr('disabled', true);
					} else {
						$('#box4').attr('disabled', false);
						$('#box4').attr('checked', false);
					}

					if (ipnetfive == "0.00") {
						$('#box5').attr('disabled', true);
					} else {
						$('#box5').attr('disabled', false);
						$('#box5').attr('checked', false);
					}

				}
				
				
				function closepayoutPanel() {
					$("#overlay").show();
				/* 	alert("closePayout"); */
					setTimeout(() => {
						
					document.getElementById("side-panel").style.display = "none";
					var FinalPayableAmt = document.getElementById('input-payable-amt').value;
					var FinalWithdrawfee = document.getElementById('input-withdraw-amt').value;
					var FinalNetamt = document.getElementById('input-netamt-amt').value;
					var FinalPayoutdate = document.getElementById('input-payout-date').value;
					
					console.log("FinalPayoutdate : "+FinalPayoutdate);
					console.log("get id : "+document.getElementById('input-payout-date').value)
					var curlecRequestAmt = document.getElementById('curlec-request-amt').value;
					var finalCurlecFee = document.getElementById('input-payout-vendor-fee').value;
					
					checkBox1 = document.getElementById('box1');
					checkBox2 = document.getElementById('box2');
					checkBox3 = document.getElementById('box3');
					checkBox4 = document.getElementById('box4');
					checkBox5 = document.getElementById('box5');

					if (checkBox1.checked) {

						var Finalipdateone = document
								.getElementById("input-date-one").value;

					} else {
						var Finalipdateone = null;
					}
					if (checkBox2.checked) {
						var Finalipdatetwo = document
								.getElementById("input-date-two").value;
					} else {
						var Finalipdatetwo = null;
					}
					if (checkBox3.checked) {
						var Finalipdatethree = document
								.getElementById("input-date-three").value;
					} else {
						var Finalipdatethree = null;
					}

					if (checkBox4.checked) {

						var Finalipdatefour = document
								.getElementById("input-date-four").value;
					} else {
						var Finalipdatefour = null;
					}

					if (checkBox5.checked) {
						var Finalipdatefive = document
								.getElementById("input-date-five").value;

					} else {
						var Finalipdatefive = null;
					}
					
					
				

					$.ajax({
								async : false,
								type : "GET",
								url : "${pageContext.request.contextPath}/transactionweb/Addpayoutdata",
								data : {
									"FinalPayableAmt" : FinalPayableAmt,
									"FinalWithdrawfee" : FinalWithdrawfee,
									"FinalNetamt" : FinalNetamt,
									"FinalPayoutdate" : FinalPayoutdate,
									"Finalipdateone" : Finalipdateone,
									"Finalipdatetwo" : Finalipdatetwo,
									"Finalipdatethree" : Finalipdatethree,
									"Finalipdatefour" : Finalipdatefour,
									"Finalipdatefive" : Finalipdatefive,
									"curlecRequestAmount" : curlecRequestAmt,
									"finalCurlecFee" : finalCurlecFee
								},
								success : function(result) {

									/* alert("after getting backend",result.ezysettleVaild); */
									document.getElementById('sucess-date').innerHTML = result.sucessdate;
									document.getElementById('sucess-netamt').innerHTML = result.FinalNetamt;

									if(result.ezysettleVaild.toLowerCase() === "yes"){
									 	$("#mobi_modal_popup").show();
										$("#overlay-popup").show(); 
									}else if(result.ezysettleVaild.toLowerCase() === "no"){
										$("#mobi_modal_popup_failure").show();
										$("#overlay-popup_failure").show();
										 $("#dynamic-content").text("Retry Ezysettle After 5 Minutes."); 
										/* $("#dynamic-content").text("Oops! We're having trouble with the server. Please try again in a few minutes or contact our customer support team for assistance."); */
									}
									else{
										$("#mobi_modal_popup_failure").show();
										$("#overlay-popup_failure").show();
										 $("#dynamic-content").text("Oops! We're having trouble with the server. Please try again in a few minutes or contact our customer support team for assistance.");
										//need change
									}
									$("#overlay").hide();
								},
								error : function(jqXHR, textStatus, errorThrown) {
									// Hide loader on error
									$("#overlay").hide();
									// Handle error
									console.log("Error:", errorThrown);
								}
							});
					}, 10);
				}

				function showSidePanel() {
					 
					$("#overlay").show();
					document.getElementById("side-panel").style.display = "block";
					$('#button-ctrl').prop('disabled', true);

					checkBox1 = document.getElementById('box1');
					checkBox2 = document.getElementById('box2');
					checkBox3 = document.getElementById('box3');
					checkBox4 = document.getElementById('box4');
					checkBox5 = document.getElementById('box5');

					if (checkBox1.checked) {

						var ipdateone = document
								.getElementById("input-date-one").value;
						var ipnetamtone = document
								.getElementById("input-netamt-one").value;
						$('#box1').attr('disabled', true);
					} else {
						var ipdateone = null;
						var ipnetamtone = null;
						$('#box1').attr('disabled', true);
					}
					if (checkBox2.checked) {

						var ipdatetwo = document
								.getElementById("input-date-two").value;
						var ipnetamttwo = document
								.getElementById("input-netamt-two").value;
						$('#box2').attr('disabled', true);
					} else {
						var ipdatetwo = null;
						var ipnetamttwo = null;
						$('#box2').attr('disabled', true);
					}
					if (checkBox3.checked) {

						var ipdatethree = document
								.getElementById("input-date-three").value;
						var ipnetamtthree = document
								.getElementById("input-netamt-three").value;
						$('#box3').attr('disabled', true);
					} else {
						var ipdatethree = null;
						var ipnetamtthree = null;
						$('#box3').attr('disabled', true);
					}

					if (checkBox4.checked) {

						var ipdatefour = document
								.getElementById("input-date-four").value;
						var ipnetamtfour = document
								.getElementById("input-netamt-four").value;
						$('#box4').attr('disabled', true);
					} else {
						var ipdatefour = null;
						var ipnetamtfour = null;
						$('#box4').attr('disabled', true);
					}

					if (checkBox5.checked) {

						var ipdatefive = document
								.getElementById("input-date-five").value;
						var ipnetamtfive = document
								.getElementById("input-netamt-five").value;
						$('#box5').attr('disabled', true);
					} else {
						var ipdatefive = null;
						var ipnetamtfive = null;
						$('#box5').attr('disabled', true);
					}

					$.ajax({

								async : false,
								type : "GET",
								url : "${pageContext.request.contextPath}/transactionweb/Addwithdraw",
								data : {
									"ipdateone" : ipdateone,
									"ipnetamtone" : ipnetamtone,
									"ipdatetwo" : ipdatetwo,
									"ipnetamttwo" : ipnetamttwo,
									"ipdatethree" : ipdatethree,
									"ipnetamtthree" : ipnetamtthree,
									"ipdatefour" : ipdatefour,
									"ipnetamtfour" : ipnetamtfour,
									"ipdatefive" : ipdatefive,
									"ipnetamtfive" : ipnetamtfive
								},

								success : function(result) {
									
									$("#overlay").hide();
									document.getElementById('in-payamt').innerHTML = result.payableAmount;
									document.getElementById('in-withdraw').innerHTML = result.withdrawfeeAmount;
									document.getElementById('in-netamt').innerHTML = result.netAmount;
									
									/*  document.getElementById('curlec-request-amount').innerHTML = result.curlecRequestAmount;
   */
								
									/* document.getElementById('eligible-ezysettle').innerHTML = result.ezysettleEligible;
									 */
									 
									 console.log("Payoutdate ",result.payoutDate);
									

									//When the Ezysettle amount goes negative, the submit button is disabled.
									var disableEzysettleButton = document.getElementById('submitEzysettleButton');
							
									 /* alert(result.ezysettleEligible.toLowerCase()); */
									if (result.ezysettleEligible.toLowerCase() === 'yes') {
										/* alert("Ezysettle Eligible Yes"); */
										document.getElementById('eligible-ezysettle').style.display = 'none'; 
										 disableEzysettleButton.disabled = false;
										 disableEzysettleButton.style.opacity=1;
									} else {
										/* alert("Ezysettle Eligible No"); */
										document.getElementById('eligible-ezysettle').style.display = 'block'; 
										disableEzysettleButton.disabled = true;
										disableEzysettleButton.style.opacity=0.5;
										
									}
									 
									
									 const curlecRequestAmt = document
											.getElementById("curlec-request-amt");

									curlecRequestAmt.value = result.curlecRequestAmount;
									console.log("curlec Request amount : ",curlecRequestAmt.value);
									/* alert("curlec "+curlecRequestAmt.value)
									console.log("curlec ",curlecRequestAmt.value);
									console.log("Backend curlec request ",result.curlecRequestAmount);
									
									 */
									/* inputPayableAmt.value = result.payableAmount
									inputwithdrawfee.value = result.withdrawfeeAmount
									inputNetamt.value = result.netAmount
									inputPaydate.value = result.payoutDate
									
									 
									document.getElementById('in-payoutdate').innerHTML = result.paydate */
									const inputCurlecFee = document.getElementById("input-payout-vendor-fee");
									 inputCurlecFee.value = result.curlecFee;
									 console.log("inputCurlecFee get response from backend : ",inputCurlecFee.value);
										
									 
									const inputPaydate = document.getElementById("input-payout-date");
									 inputPaydate.value = result.payoutDate
									 console.log("inputPaydate get response from backend : ",inputPaydate.value);
										
									inputPayableAmt.value = result.payableAmount
									inputwithdrawfee.value = result.withdrawfeeAmount
									inputNetamt.value = result.netAmount
									/* inputPaydate.value = result.payoutDate */
									
									document.getElementById('in-payoutdate').innerHTML = result.paydate

								},
								error : function(result) {
									$("#overlay").hide();
									// Handle error
									console.log("Error:", errorThrown);
								}
							});
				}

		

				//Success-Popup - Close Function

				function refreshPage() {
					window.location.reload();

				}

				//user can tap div trigger the checkbox	
				function onselectdivone() {

					$('#box1').trigger('click');
				}
				function onselectdivtwo() {

					$('#box2').trigger('click');
				}
				function onselectdivthree() {

					$('#box3').trigger('click');
				}
				function onselectdivfour() {

					$('#box4').trigger('click');
				}
				function onselectdivfive() {

					$('#box5').trigger('click');
				}

				window.onload = function() {

					$("#mobi_modal_featurepopup").show();
					$("#overlay-popup1").show();

				}

				function closefeature() {
					document.getElementById('mobi_modal_featurepopup').style.display = "none";
					document.getElementById('overlay-popup1').style.display = "none";
				}
			</script>
		</div>
	</div>



	<script>
	
	
	
	
	function settlementWithdraw(){
		  // Get the span element
        var span = document.getElementById("payout-check-count");
        var settlementText = document.getElementById("settlementText");

        // Check if the span element exists
        if (span) {
            // Check the value of the span
            if (span.innerHTML === "0" || span.innerHTML === "1") {
                // Update the text accordingly
                settlementText.innerHTML = "Settlement";
                console.log("settlementText below one",settlementText);
            } else {
                settlementText.innerHTML = "Settlements";
                console.log("settlementText",settlementText);
            }
        } else {
            console.error("Span element with id 'payout-check-count' not found.");
        }
	}
	
    // Wait for the document to fully load before executing the script
    document.addEventListener("DOMContentLoaded", function() {
    	 settlementWithdraw();
    	 var box1 = document.getElementById("box1");
    	 var box2 = document.getElementById("box2");
    	 var box3 = document.getElementById("box3");
    	 var box4 = document.getElementById("box4");
    	 var box5 = document.getElementById("box5");
    	
    	
    	 box1.addEventListener("change", function() {
    		 settlementWithdraw();
    		
         });
    	 box2.addEventListener("change", function() {
    		 settlementWithdraw();
    		
         });
    	 box3.addEventListener("change", function() {
    		 settlementWithdraw();
    		
         });
    	 box4.addEventListener("change", function() {
    		 settlementWithdraw();
    		
         });
    	 box5.addEventListener("change", function() {
    		 settlementWithdraw();
    		
         });
    });
</script>



</body>
<script>
	var isJenfiEnabled = ${isJenfiEnabled};
	console.log(isJenfiEnabled)
	var isActualMerchant  =${isActualMerchant};
	console.log(isActualMerchant);
</script>

</html>