<%@page import="com.mobiversa.payment.controller.PayoutUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="java.util.ResourceBundle"%>
<%
	ResourceBundle resource = ResourceBundle.getBundle("config");
	String actionimg = resource.getString("NEWACTION");
	String voidimg = resource.getString("NEWVOID");
	String refundimg = resource.getString("NEWREFUND");
	String eyeimg = resource.getString("NEWEYE");
%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<meta http-equiv="Content-Security-Policy"
	content="default-src 
    'self'">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/custom.css">
</head>



<style>
#merchantName:hover {
	color: 275ca8;
}

#agentName:hover {
	color: 275ca8;
}

.example_e1:focus {
	outline: none !important;
}

.table-border-bottom tr {
	border-bottom: 1px solid rgba(0, 0, 0, 0.12);
}

.key_hover:hover {
	cursor: pointer;
}

.example_e1 {
	display: inline-block;
	margin-bottom: 0;
	font-weight: 600;
	text-align: left;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	background-image: none;
	border: 0;
	color: rgb(39, 92, 168);
	letter-spacing: 1px;
	text-transform: uppercase;
	padding: 10px 15px;
	font-size: 13px;
	line-height: 1.428571429;
	border-radius: 25px;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	transition: box-shadow 0.3s cubic-bezier(0.35, 0, 0.25, 1), transform
		0.2s cubic-bezier(0.35, 0, 0.25, 1), background-color 0.3s ease-in-out;
	font-style: Arial, Helvetica, sans-serif;
	border-radius: 15px;
}

.example_e1:hover {
	color: rgb(39, 92, 168);
	font-weight: 600 !important;
	-webkit-box-shadow: 0px 5px 40px -10px rgba(0, 0, 0, 0.57);
	-moz-box-shadow: 0px 5px 40px -10px rgba(0, 0, 0, 0.57);
	transition: all 0.3s ease 0s;
	border: 2px solid #cfcfd1;
	outline: 0 !important;
}

.hide_key {
	display: none;
}
</style>
<script type="text/javascript">
	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");

	});
</script>

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

.c969696 {
	color: #080808;;
	font-size: 1.2rem !important;
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
</style>

<style>
.export_div .select-wrapper {
	width: 65%;
	float: left;
}

.datepicker {
	width: 80% !important;
}

.select-wrapper .caret {
	fill: #005baa;
}

.mobi-text-right {
	text-align: right;
}

.addUserBtn, .addUserBtn:hover {
	background-color: #fff;
	border: 1px solid #005baa;
	border-radius: 20px;
	color: #005baa;
	font-weight: 600;
}

.blue-btn {
	background-color: #005baa;
	color: #fff;
	border-radius: 20px;
}

.button-class {
	float: right;
}

.float-right {
	float: right;
}
</style>


<style>
td, th {
	padding: 7px 8px;
	color: #707070;
}

.table-border-bottom td {
	border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important;
}

thead th {
	border-bottom: 1px solid #ffa500;
	color: #4377a2;
}

img.refund_icon_6 {
	width: 40px;
	height: 40px;
	margin: 10px 9px 0 10px;
	object-fit: contain;
}

.w24 {
	width: 24px;
}

#decl {
	left: 40%;
	bottom: 40%;
	display: none;
	z-index: 199;
}

.decline-side-box {
	display: block;
	width: 25rem;
	height: 13rem;
	background-color: #FFFFFF;
	position: fixed;
	border-radius: 10px;
	border: 1px solid #FFDEAD;
}

.text-wrap-user {
	position: absolute;
	width: 22rem;
	height: 11rem;
	background-color: #FFFFFF;
	left: 20px;
	top: 10px;
	border-radius: 10px;
}

.text-style-decl {
	font-size: 1.6rem;
	font-weight: 500;
	font-family: sans-serif;
	color: #4377a2;
	padding: 15px 25px;
	margin: 5px 0;
}

#decline-reason {
	padding: 15px 0 0 0;
	font-size: 1.2rem;
	margin: 0 30px;
	width: 75%;
}

.bn632-hover {
	width: 80px;
	font-size: 16px;
	font-weight: 600;
	color: #fff;
	cursor: pointer;
	margin: 25px;
	height: 40px;
	text-align: center;
	border: none;
	background-size: 300% 100%;
	border-radius: 50px;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out;
	position: absolute;
	left: 35px;
	bottom: -20px;
}

.bn632-hover:hover {
	background-position: 100% 0;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out;
}

.bn632-hover:focus {
	outline: none;
}

.bn632-hover.bn26 {
	background-image: linear-gradient(to right, #25aae1, #4481eb, #04befe, #3f86ed);
	box-shadow: 0 4px 15px 0 rgba(65, 132, 234, 0.75);
}

.cancelbtn {
	width: 80px;
	font-size: 16px;
	font-weight: 600;
	color: #fff;
	cursor: pointer;
	margin: 20px;
	height: 40px;
	text-align: center;
	border: none;
	background-size: 300% 100%;
	border-radius: 50px;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out;
	position: absolute;
	left: 160px;
	bottom: -15px;
}

.cancelbtn:hover {
	background-position: 100% 0;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out;
}

.cancelbtn:focus {
	outline: none;
}

.cancelbtn.bn25 {
	/*   background-image: linear-gradient(
    to right,
    #29323c,
    #485563,
    #2b5876,
    #4e4376
  ); */
	background-color: #7D7D7D;
	/* box-shadow: 0 4px 15px 0 rgba(45, 54, 65, 0.75); */
}
</style>


<script lang="JavaScript">
	function loadSelectData() {
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;

		var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
		var toDate = new Date(e1);//.toDateString("yyyy-MM-dd");	

		var fromday = fromDate.getDate();
		var frommon = fromDate.getMonth() + 1;
		var fromyear = fromDate.getFullYear();

		var today = toDate.getDate();
		var tomon = toDate.getMonth() + 1;
		var toyear = toDate.getFullYear();

		var fromdateString = fromyear + '-'
				+ (frommon <= 9 ? '0' + frommon : frommon) + '-'
				+ (fromday <= 9 ? '0' + fromday : fromday);
		var todateString = toyear + '-' + (tomon <= 9 ? '0' + tomon : tomon)
				+ '-' + (today <= 9 ? '0' + today : today);

		if (e == null || e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");

		}

		else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */

			document.location.href = '${pageContext.request.contextPath}/payoutDataUser/searchPayout?date='
					+ fromdateString + '&date1=' + todateString;

			form.submit;

		}
	}

	function loadExpData() {
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("export1").value;

		var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
		var toDate = new Date(e1);//.toDateString("yyyy-MM-dd");	

		var fromday = fromDate.getDate();
		var frommon = fromDate.getMonth() + 1;
		var fromyear = fromDate.getFullYear();

		var today = toDate.getDate();
		var tomon = toDate.getMonth() + 1;
		var toyear = toDate.getFullYear();

		var fromdateString = fromyear + '-'
				+ (frommon <= 9 ? '0' + frommon : frommon) + '-'
				+ (fromday <= 9 ? '0' + fromday : fromday);
		var todateString = toyear + '-' + (tomon <= 9 ? '0' + tomon : tomon)
				+ '-' + (today <= 9 ? '0' + today : today);

		if (e == null || e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");

		} else if (e2 == null || e2 == '') {

			alert("Please Select Export Type");

		} else {

			document.getElementById("datevalex1").value = fromdateString;
			document.getElementById("datevalex2").value = todateString;

			document.location.href = '${pageContext.request.contextPath}/payoutDataUser/exportPayout?date='
					+ fromdateString + '&date1=' + todateString

					+ '&export=' + e2;
			form.submit;

		}
	}

	function loadDropDate13() {

		var e = document.getElementById("export");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("export1").value = strUser;

	}

	function loadDate(inputtxt, outputtxt) {

		var field = inputtxt.value;

		outputtxt.value = field;
	}
	
	//Payout Sales Slip - Start

	function openPayoutslip(invoiceid){

	var url=window.location;
	var src = document.getElementById('popOutiFrame').src;
	src=url+'transactionUmweb/Payoutslip/'+invoiceid;
	var h = 600;
	var w = 1000;
	var title = "Mobiversa Receipt";

	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
	var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;

	var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
	var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;

	var left = ((width / 2) - (w / 2)) + dualScreenLeft;
	var top = ((height / 2) - (h / 2)) + dualScreenTop;

	var newWindow = window.open(src, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);

	if (window.focus) {
	 newWindow.focus();
	}
		
	}

	//payout Sales Slip - End

	
	
</script>
<body class="">
	<div class="container-fluid">
		<div class="row">



			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>PAYOUT Summary</strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">

						<div class="row">
							<div class="input-field col s12 m3 l3">
								<label for="from" style="margin: 0px;">From </label> <input
									type="hidden" name="date11" id="date11"
									<c:out value="${fromDate}"/>> <input type="text"
									id="from" name="fromDate" class="validate datepicker"
									onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
								<i class="material-icons prefix">date_range</i>


							</div>

							<div class="input-field col s12 m3 l3">

								<label for="to" style="margin: 0px;">To</label> <input
									type="hidden" name="date12" id="date12"
									<c:out value="${toDate}"/>> <input id="to" type="text"
									name="toDate" class="datepicker"
									onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
								<i class="material-icons prefix">date_range</i>
							</div>




							<div class="input-field col s12 m3 l3">

								<input type="hidden" name="export1" id="export1"
									<c:out value="${status}"/>> <select name="export"
									id="export" onchange="return loadDropDate13();">
									<option selected value="">Choose</option>
									<option value="PDF">PDF</option>
									<option value="EXCEL">EXCEL</option>
								</select> <label>Export Type</label>
							</div>

							<div class="input-field col s12 m3 l3">
								<div class="button-class" style="float: left !important;">

									<input type="hidden" name="date1" id="dateval1"> <input
										type="hidden" name="date2" id="dateval2">
									<button class="btn btn-primary blue-btn" type="button"
										onclick="return loadSelectData();">Search</button>


									<input type="hidden" name="dateex1" id="datevalex1"> <input
										type="hidden" name="dateex2" id="datevalex2">
									<button
										class="export-btn waves-effect waves-light btn btn-round indigo"
										type="button" onclick="return loadExpData();">Export</button>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>

		<input type="hidden" id="curr-procces" value="0" />
		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">

						<div class="table-responsive m-b-20 m-t-15">
							<table id="data_list_table"
								class="table table-striped table-bordered">
								<thead>
									<tr>
										<th>Merchant Name</th>
										<th>Customer Name</th>
										<th>BRN/IC</th>
										<th>Account No</th>
										<th>Bank Name</th>
										<th>Transaction ID</th>
										<th>Amount(RM)</th>
										<th>Status</th>
										<th>Date</th>
										<th>Time Stamp</th>
										<th>Paid/Decline Time</th>
										<th>Paid/Decline Date</th>
										<th>Sub Merchant MID</th>
										<th>Sub Merchant Name</th>
										<th>Payout Id</th>
										<th>Decline Reason</th>
										<th>Sales Slip</th>
										<!-- <th>Action</th> -->

									</tr>
								</thead>
								<tbody>

									<c:forEach items="${paginationBean.itemList}" var="dto"
										varStatus="loop">
										<tr class="dto-${loop.index}">
											<td>${dto.createdby}</td>
											<td>${dto.payeename}</td>
											<td>${dto.payeebrn}</td>
											<td>${dto.payeeaccnumber}</td>
											<td>${dto.payeebankname}</td>
											<td>${dto.invoiceidproof}</td>
											<td style="text-align: right;">${dto.payoutamount}</td>
											<c:choose>
												<c:when test="${dto.payoutstatus == 'Declined'}">
													<td>${dto.payoutstatus}</td>
													<%--                                             <td>${dto.payoutstatus} -&nbsp; ${dto.paymentreason}</td> --%>
												</c:when>
												<c:otherwise>
													<td id="response">${dto.payoutstatus}</td>
												</c:otherwise>
											</c:choose>
											<td>${dto.payoutdate}</td>
											<td>${dto.createddate}</td>
											<td>${dto.paidTime}</td>
											<td>${dto.paidDate}</td>
											<td>${dto.submerchantMid}</td>
											<td>${dto.mmId}</td>


											<c:choose>
												<c:when test="${dto.payoutId == 'null'}">
													<td></td>
												</c:when>
												<c:otherwise>
													<td>${dto.payoutId}</td>
												</c:otherwise>
											</c:choose>


											<td>${dto.failurereason}</td>
											
											
											
											<%--  <c:if test="${dto.payoutstatus == 'Declined'}">
												<td>${dto.failurereason}</td>
											</c:if>  --%>





											<%-- <td style="text-align: center;"><c:if
													test="${dto.payoutstatus =='Processed'}">
													<a
														href="${pageContext.request.contextPath}/payoutDataUser/updatePayoutStatus/${dto.invoiceidproof}">
														<img class="w24"
														src='data:image/png;base64,<%=actionimg%> ' />

													</a>
												</c:if></td> --%>
											<!-- <td><c:if test="${dto.payoutstatus == 'Processing'}">
											<div >
													<img id="action"
														onclick="openDialog('${dto.invoiceidproof}','${dto.merchantId}','${loop.index}')"
														class="w24" src='data:image/png;base64,<%=actionimg%> ' />
														</div>
												</c:if></td> -->


	                                       <td align="center"><c:if
													test="${dto.payoutstatus =='Paid' || dto.payoutstatus == 'Declined'}">
													<a href="javascript:void(0)" id="openPayoutslip"
														onclick="javascript: openPayoutslip('${dto.invoiceidproof}')">
														<img class="w24"
														src='data:image/png;base64,<%=actionimg%> ' />
													</a>

												</c:if>


												<div class="form-group col-md-4" id="divviewer"
													style="display: none;">
													<div class="form-group">
														<div style="clear: both">
															<iframe id="popOutiFrame" frameborder="0" scrolling="no"
																width="800" height="600"></iframe>

														</div>

													</div>
												</div></td>


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


	<div id="overlay-popup"></div>
	<div class="mobi-modal" id="mobi_modal_popup" tabindex="-1">
		<div class="mobi-modal-dialog">
			<div
				class="mobi-modal-content mobi-modal-content-center mobi-modal-content-mobile mobi-60-per">
				<div class="mobi-modal-header">
					<h5 class="mobi-modal-title mobi-text-dark"></h5>
					<img class="img-fluid mobi-close" onclick="closeDialog()"
						src="${pageContext.request.contextPath}/resourcesNew1/assets/close.png">

				</div>

				<div
					class="mobi-modal-body mobi-text-sucess mobi-100-per mobi-modal-content-center">
					<div class="mobi-float-left mobi-50-per">
						<p>
							<label><input type="radio" name="payout_radio"
								onClick="ProcessPaid()" id="check-paid" value=""><span
								class="c969696">Paid</span></label>
						</p>
					</div>
					<div class="mobi-float-left mobi-50-per">
						<p>
							<label><input type="radio" name="payout_radio"
								onclick="closeDialog()" id="check-Unpaid" value=""><span
								class="c969696">Unpaid</span></label>
						</p>
					</div>

					<!-- Sheik Edited For Decline Status - Start  -->
					<div class="mobi-float-left mobi-50-per">
						<p>
							<label><input type="radio" name="payout_radio"
								onClick="openDecline();" id="check-decline" value=""><span
								class="c969696" style="margin: 20px 0 0 20px;">Decline</span></label>
						</p>
					</div>

					<!-- Sheik Edited For Decline Status - End  -->

				</div>
				<div class="mobi-modal-footer"></div>

			</div>
		</div>
	</div>


	<div id="decl" class="decline-side-box">
		<div class="text-wrap-user">
			<label for="decline-reason" class="text-style-decl">Reason
				For Decline:</label> <input type="text" id="decline-reason"
				name="decline-reason" required maxlength="64"
				placeholder="Ex: Account Mismatch">
			<button class="bn632-hover bn26" onclick="confirmDecline();"
				id="confirmbtn">Confirm</button>
			<button class="cancelbtn bn25" onclick="closeDecline();"
				id="canceldecl">Cancel</button>
		</div>

	</div>


	<%-- 	<form method="get" id="myForm"
		action="${pageContext.request.contextPath}<%=PayoutUserController.URL_BASE%>/updatePayoutStatus">

		<input type="hidden" id="trxid" name="txnId" value="0"> <input
			type="hidden" id="merchantid" name="merchantID" value="0"> <input
			type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
	
	<form method="get" id="myForm1"
        action="${pageContext.request.contextPath}<%=PayoutUserController.URL_BASE%>/declinePayoutStatus">
       <input type="hidden" id="trxnid" name="txnId1" value="0"> <input
            type="hidden" id="merchantsid" name="merchantID1" value="0"> <input
            type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
       <input type="hidden" id="cancelreason" name="cancelReason" value="0">
    </form> --%>
	<form method="get" id="myForm"
		action="${pageContext.request.contextPath}<%=PayoutUserController.URL_BASE%>/updatePayoutPaidStatus">

		<input type="hidden" id="trxid" name="txnId" value="0"> <input
			type="hidden" id="merchantid" name="merchantID" value="0"> <input
			type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		<input type="hidden" id="payoutStatus" name="payoutStatus"
			value="PaymentPaid">
	</form>

	<form method="get" id="myForm1"
		action="${pageContext.request.contextPath}<%=PayoutUserController.URL_BASE%>/declinePayoutStatus1">
		<input type="hidden" id="trxnid" name="txnId1" value="0"> <input
			type="hidden" id="merchantsid" name="merchantID1" value="0">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}"> <input type="hidden"
			id="cancelreason" name="cancelReason" value="0"> <input
			type="hidden" id="payoutStatus1" name="payoutStatus1"
			value="PaymentDeclined">
	</form>


	<script>
		$(document).ready(function() {
			// $('#data_list_table').DataTable();
		});

		$(document).ready(
				function() {
					$('#data_list_table').DataTable({
						"bSort" : false,
						"stateSave" : true,
						"stateSaveParams" : function(settings, data) {
							data.length = settings._iDisplayLength;
						},
					});

					var savedLength = parseInt($('#data_list_table')
							.DataTable().state().length, 10);
					if (savedLength) {
						$('#data_list_table').DataTable().page.len(savedLength)
								.draw('page');
					}
				});

		function openDialog(txnId, merchantId, itemId) {

			const txnid = document.getElementById("trxid")
			const merchantid = document.getElementById("merchantid")

			const txnid1 = document.getElementById("trxnid")
			const merchantid1 = document.getElementById("merchantsid")
			//alert(txnId);
			txnid.value = txnId
			merchantid.value = merchantId

			txnid1.value = txnId
			merchantid1.value = merchantId

			document.getElementById("curr-procces").value = itemId

			$("#mobi_modal_popup").show();
			$("#overlay-popup").show();

		}

		function ProcessPaid() {

			var txnid = document.getElementById("trxid").value;
			var merchantId = document.getElementById("merchantid").value;
			var status = "pay";

			//alert(txnid" "status " "merchantId);
			// Send an AJAX request to the server
			$
					.ajax({
						url : "${pageContext.request.contextPath}/payoutDataUser/updatePayoutPaidStatus1",
						type : 'GET',
						data : {
							"merchantId" : merchantId,
							"txnid" : txnid,

							"status" : status
						},
						success : function(response) {

							if (response.responseCode == "0000") {
								//document.getElementById('response').innerHTML = response.responseCode;
								var currprocessIndx = document
										.getElementById("curr-procces").value;
								var trTag = document.querySelector('tr.dto-'
										+ currprocessIndx);
								var tdTag = trTag
										.querySelector('td:nth-of-type(8)');
								tdTag.innerText = 'Paid';
								var eyeIcon = trTag
										.querySelector('td:nth-of-type(15)');
								eyeIcon.style.display = 'none';

								statuschange();

								// alert("Sucess");
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {
							// Handle any errors that occur during the request
							alert("error");
							closeDialog()
							console.error(errorThrown);
						}
					});

		}

		//saravana

		function statuschange() {
			$("#mobi_modal_popup").hide();
			$("#overlay-popup").hide();
			const radioGroup = document.getElementsByName('payout_radio');
			for (let i = 0; i < radioGroup.length; i++) {
				radioGroup[i].checked = false;
			}

		}

		function ProcessDecline() {

			var txnid1 = document.getElementById("trxnid").value;
			var merchantId1 = document.getElementById("merchantsid").value;
			const hidDeclineBox = document.getElementById("cancelreason").value;

			document.getElementById("myForm1").submit();
		}

		function closeDialog() {
			$("#mobi_modal_popup").hide();
			$("#overlay-popup").hide();
		}

		function openDecline() {
			$("#decl").show();
			$("#overlay-popup").show();
			$("#mobi_modal_popup").hide();

		}

		function confirmDecline() {
			const declineReason = document.getElementById("decline-reason").value;
			const hidDeclineBox = document.getElementById("cancelreason").value = declineReason;
			/* alert(hidDeclineBox); */
			ProcessDecline();
		}

		function closeDecline() {
			$("#decl").hide();
			$("#overlay-popup").hide();

		}
	</script>

</body>

</html>