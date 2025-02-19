<%@page
	import="com.mobiversa.payment.controller.SettlementUserController"%>
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
</head>
<%-- <script type="text/javascript"
	src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script> --%>
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
<!-- <script type="text/javascript">
	jQuery(document).ready(function() {

		$('#export').select2();
		$('#txnType').select2();
	});
</script> -->
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
</style>


<script lang="JavaScript">
	function loadSelectData() {
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("txnType").value;

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

			alert("Please Select Payment Method");

		}

		else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			var TxnType = document.getElementById("txnType").value;
			document.location.href = '${pageContext.request.contextPath}/settlementDataUser/searchSettlementMDR?date='
					+ fromdateString
					+ '&date1='
					+ todateString
					+ '&txntype='
					+ e2;
			form.submit;

		}
	}

	function loadExpData() {
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("export1").value;
		var txnType = document.getElementById("txnType").value;

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

		} else if (txnType == null || txnType == '') {

			alert("Please Select Payment Method");

		}
		else if (e2 == null || e2 == '') {

			alert("Please Select Export Type");

		}  else {

			document.getElementById("datevalex1").value = fromdateString;
			document.getElementById("datevalex2").value = todateString;

			document.location.href = '${pageContext.request.contextPath}/settlementDataUser/exportSettlementMDR?date='
					+ fromdateString
					+ '&date1='
					+ todateString
					+ '&txntype='
					+ txnType + '&export=' + e2;
			form.submit;

		}
	}

	function loadDropDate13() {

		var e = document.getElementById("export");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("export1").value = strUser;

	}

	function loadDropDate14() {

		var e = document.getElementById("txnType");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("txnType1").value = strUser;

	}

	function loaddata() {

		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e5 = document.getElementById("export1").value;

		if (e == null || e1 == null || e == '' || e1 == '') {
			alert("Please select date(s)");

		} else {

			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/umEzywayExport?fromDate='
					+ e + '&toDate=' + e1 + '&export=' + e5;
			form.submit();

		}
	}

	function loadDate(inputtxt, outputtxt) {

		var field = inputtxt.value;

		outputtxt.value = field;
	}
</script>
<body class="">
	<div class="container-fluid">
		<div class="row">



			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Settlement Summary</strong>
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


								<select name="txnType" id="txnType"
									onchange="return loadDropDate14();">
									<option selected value="">Choose</option>
									<option value="CARD">CARD</option>
									<option value="BOOST">BOOST</option>
									<!-- <option value="GRABPAY">GRABPAY</option> -->
									<option value="FPX">FPX</option>


								</select> <label for="name">Payment Method</label> <input type="hidden"
									name="txnType1" id="txnType1">
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

		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">

						<div class="table-responsive m-b-20 m-t-15">
							<table id="data_list_table"
								class="table table-striped table-bordered">
								<thead>
									<tr>
										<th>Date</th>
										<th>Amount(RM)</th>
										<th>Net Amount</th>
										<th>Host MDR</th>
										<th>Mobi MDR</th>
										<th>MID</th>
										<th>Masked Pan</th>
										<th>Status</th>
										<th>RRN</th>
										<th>Invoice ID</th>
										<th>Payment Method</th>
										<th>Change</th>

									</tr>
								</thead>
								<tbody>

									<c:forEach items="${paginationBean.itemList}" var="dto">
										<tr>
											<fmt:parseDate value="${dto.date}" pattern="yyyyMMdd"
												var="myDate" />
											<td><fmt:formatDate pattern="dd-MMM-yyyy"
													value="${myDate}" /></td>
											<td style="text-align: right;">${dto.txnAmount}</td>
											<td style="text-align: right;">${dto.netAmount}</td>
											<td style="text-align: right;">${dto.hostMdrAmt}</td>
											<td style="text-align: right;">${dto.mobiMdrAmt}</td>
											<td>${dto.mid}</td>
											<td>${dto.maskedPan}</td>
											<td>${dto.status}</td>
											<td>${dto.rrn}</td>
											<td>${dto.invoiceId}</td>
											<td>${dto.cardBrand}&nbsp;${dto.cardType}</td>



											<td style="text-align: center;"><c:if
													test="${dto.cardBrand == 'MASTERCARD' || dto.cardBrand == 'VISA' }">
													<a
														href="${pageContext.request.contextPath}/settlementDataUser/changeStatus/${dto.rrn}">
														<i class="material-icons">create</i>

													</a>
												</c:if> <c:if test="${dto.cardBrand == 'BOOST'}">
													<a
														href="${pageContext.request.contextPath}/settlementDataUser/changeBoostStatus/${dto.rrn}">
														<i class="material-icons">create</i>

													</a>
												</c:if> <c:if test="${dto.cardBrand == 'FPX' }">
													<a
														href="${pageContext.request.contextPath}/settlementDataUser/changeFpxStatus/${dto.rrn}/${dto.invoiceId}">
														<i class="material-icons">create</i>
													</a>
												</c:if></td>

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
		$(document).ready(function() {
			// $('#data_list_table').DataTable();
		});

		$(document).ready(function() {
			$('#data_list_table').DataTable({
				"bSort" : false
			});
		});
	</script>

</body>

</html>