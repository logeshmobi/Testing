<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@page import="com.mobiversa.payment.controller.HomeController"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<%@page import="com.mobiversa.payment.util.PropertyLoader"%>


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
	width: 88.66%;
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
	/* border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important; */
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

/* .c-151931 {
	color: #151931;
} */
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

/* .align-items-center .c-151931 {
	color: #151931 !important;
	z-index: 2;
} */
.back-color {
	background-color: #fff;
}

.table-head {
	background-color: #005baa !important;
}
</style>

<script src="https://code.highcharts.com/highcharts.js"></script>

<style>
td, th {
	padding: 0px 8px;
}

thead th {
	border-bottom: 1px solid #ffa500;
	color: #005BAA !important;
}

.bgA1A0A2 {
	background-color: #A1A0A2
}

.bcA1A0A2 {
	border: solid 1px #A1A0A2
}

.bgd7e7ea {
	background: white !important;
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
	border-radius: 0.3rem;
	outline: 0;
}

.mobi-modal-header {
	display: flex;
	flex-shrink: 0;
	align-items: center;
	justify-content: space-between;
	padding: 1rem 1rem;
	border-bottom: 1px solid #dee2e6;
	border-top-left-radius: calc(0.3rem - 1px);
	border-top-right-radius: calc(0.3rem - 1px);
	font-size: 22px !important;
}

.mobi-modal-body {
	position: relative;
	flex: 1 1 auto;
	padding: 1rem;
	font-size: 18px !important;
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
	border-top: 1px solid #dee2e6;
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
</style>




<style>
h3 {
	font-size: 40px;
	/* 	line-height: 110%; */
	margin: 0;
}

.mobiapikey {
	font-size: 15px !important;
}

.statuscheckbtn {
	padding: 8px 22px;
	border-radius: 25px;
	background-color: #005baa;
	border: 2px solid #005baa !important;
	color: #fff;
	cursor: pointer;
	height: 25%;
}

.mobiapikey {
	display: flex !important;
	/*  flex-direction: row !important; */
	align-items: flex-end !important;
}

}
.btnfield {
	height: 100% !important;
}

.responsecontent {
	flex-direction: column !important;
	align-items: stretch !important;
	justify-content: space-between !important;
}

.responsedata {
	margin-bottom: 0px !important;
	margin-top: 10px !important;
}

input::placeholder {
	color: #9e9e9e !important;
}

.responsedatacontent {
	margin-left: 3px !important
}

#exampleModalCenter {
	z-index: 99;
	width: 25%;
	font-size: 24px;
	font-weight: 400;
	font-family: 'Poppins', sans-serif;
	text-align: center;
	border-radius: 25px;
}

.test {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 90;
	background-color: rgba(0, 0, 0, 0.5);
}

.mb-0 {
	padding-bottom: 0px !important;
}

@media ( max-width : 767px) {
	.d-flex {
		display: flex;
	}
}

.innerText {
	white-space: nowrap;
}

.modal::-webkit-scrollbar {
	display: none;
}

/* Hide scrollbar for IE, Edge and Firefox */
.modal {
	-ms-overflow-style: none; /* IE and Edge */
	scrollbar-width: none; /* Firefox */
}

input::placeholder {
	font-size: 12px; /* Adjust the font size as needed */
}

input:not([type]), input[type=text]:not(.browser-default), input[type=password]:not(.browser-default),
	input[type=email]:not(.browser-default), input[type=url]:not(.browser-default),
	input[type=time]:not(.browser-default), input[type=date]:not(.browser-default),
	input[type=datetime]:not(.browser-default), input[type=datetime-local]:not(.browser-default),
	input[type=tel]:not(.browser-default), input[type=number]:not(.browser-default),
	input[type=search]:not(.browser-default), textarea.materialize-textarea
	{
	background-color: transparent !important;
	border: none !important;
	/* border-bottom: 1px solid #9e9e9e !important; */
	border-bottom: 1.5px solid #F5A623 !important;
	border-radius: 0;
	outline: none;
	height: 3rem;
	width: 90%;
	font-size: 16px;
	margin: 0 0 8px 0;
	padding: 0;
	box-shadow: none;
	box-sizing: content-box;
	transition: box-shadow .3s, border .3s;
}

.card .card-content {
	padding: 5px;
	border-radius: 0 0 2px 2px;
}

.transaction-details-box {
	border: 1.5px solid #F5A623; /* Black border */
	padding: 10px; /* Adjust the padding to control the size */
	width: 50%; /* Adjust the width as per your requirement */
	margin: 0 auto; /* Center the box horizontally */
	border-radius: 10px;
}

.card .card-content p {
	margin-bottom: 5px;
}

body {
	font-family: 'Poppins', sans-serif;
}

.detail-item {
	display: flex;
	justify-content: space-around;
	margin-bottom: 10px;
}

.detail-item span {
	flex: 1;
	text-align: left;
}

.detail-item span:nth-child(2) {
	flex: 0.3; /* Adjust the width for the second span as needed */
}

.detail-item span:nth-child(3) {
	flex: 1.7; /* Adjust the width for the third span as needed */
}

.mb-40 {
	margin-bottom: 110px;
}

.row {
	display: flex;
	align-items: center;
	justify-content: center;
}

/* thead:not(.browser-default){
border-bottom:orange !important;
} */
thead th {
	border-bottom: 1.5px solid #ffa500;
	color: #4377a2;
}

* {
	font-family: 'Poppins', sans-serif;
}

/* .text-white {
    color: none !important;
} */
</style>



</head>

<body class="body-mobi">



	<div id="overlay">
		<div id="overlay_text">
			<img class="img-fluid"
				src="${pageContext.request.contextPath}/resourcesNew/img/loader.gif">
		</div>
	</div>

	<div class="container-fluid">

		<div class="row">
			<div class="col s12">

				<c:if
					test="${not empty merchant.mid.motoMid || not empty merchant.mid.umMotoMid}">
					<c:if
						test="${ezyAuthEnable == Yes || merchant.auth3DS == 'No' || merchant.auth3DS == 'Yes'}">
						<a
							href="${pageContext.request.contextPath}/transactionUmweb/Transaction"
							class="btn-large mb-2 dashbtn"
							style="background-color: #1c60ac; margin-top: -15px; float: right; display: block">Payment
							Request</a>
					</c:if>
				</c:if>
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


		<div class="row">


			<div class="col s12">
				<div class="card blue-bg text-whitee">

					<div class="row ">

						<div class="col-8 mb-40 mx-auto">
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

													</span> <span
														id="show_key_${paginationBean2.itemList[count.index]}"
														class="hide_key">${dtoo.motoapikey}</span></td>
													<td class="c-151931">${dtoo.expiryDate}</td>

													<td class="c-151931">${dtoo.refno}</td>

													<%
													if (!isValid) {
													%>
													<c:if test="${not empty dtoo.expiryDate}">

														<td>
															<%-- <form method="post" id="form-edit"
																action="${pageContext.request.contextPath}/readerweb/detail">

																<input type="hidden" name="deviceId"
																	value="${dtoo.deviceId}" /> <input type="hidden"
																	name="${_csrf.parameterName}" value="${_csrf.token}">

																<center>
																	<button type="submit" class="editBtn">
																		<i class="material-icons">create</i>
																	</button>
																</center>
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




					</div>
				</div>
			</div>



			<script>

								function show_key(id) {

									document.getElementById('show_key_' + id).style.display = "block";
									document.getElementById('hide_key_' + id).style.display = "none";		
								}

					
							



								$("#disabled_div").children().prop('disabled', true);
								const disabled_div = document.querySelector('#disabled_div');
								disabled_div.addEventListener('click', (event) => {
								event.preventDefault();
								});
								
								$('input[name="monday_check"]').prop("disabled", true);
								
							</script>
		</div>
	</div>
</body>

</html>