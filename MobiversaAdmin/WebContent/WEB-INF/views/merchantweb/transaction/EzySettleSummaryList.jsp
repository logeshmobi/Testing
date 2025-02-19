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
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link
			href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400&display=swap"
			rel="stylesheet">
	<meta charset="UTF-8">
	<meta
			content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
			name="viewport">

	<!-- Script tag for Datepicker -->

	<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script
			src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

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

	#pagination {
		display: inline-block;
		vertical-align: middle;
		border-radius: 1px;
		padding: 1px 2px 4px 2px;
		border-top: 1px solid transparent;
		border-bottom: 1px solid transparent;
		background-color: transparent;
		float: right;
		margin-right: 15px;
		margin-bottom: 10px;
		/* background-image: -webkit-linear-gradient(top, #DBDBDB, #E2E2E2);
          background-image:    -moz-linear-gradient(top, #DBDBDB, #E2E2E2);
          background-image:     -ms-linear-gradient(top, #DBDBDB, #E2E2E2);
          background-image:      -o-linear-gradient(top, #DBDBDB, #E2E2E2);
          background-image:         linear-gradient(top, #DBDBDB, #E2E2E2); */
		/*  position:absolute;
          left:62rem;
          bottom:1rem; */
		font-family: 'Poppins', sans-serif;
		/* width:20%;
          height:6%; */
	}

	#pagination a, #pagination i {
		display: inline-block;
		vertical-align: middle;
		width: 2.2rem;
		/*  color: #7D7D7D; */
		text-align: center;
		font-size: 16px;
		padding: 2.5px;
		-webkit-user-select: none;
		-moz-user-select: none;
		-ms-user-select: none;
		-o-user-select: none;
		user-select: none;
	}

	#pagination a {
		/* margin: 0 2px 0 2px; */
		margin: 0 2px;
		border-radius: 1px;
		border: 1px solid #005baa;
		cursor: pointer;
		/* box-shadow: inset 0 1px 0 0 #D7D7D7, 0 1px 2px #666; */
		/* text-shadow: 0 1px 1px #FFF; */
		background-color: white;
		color: #005baa;
		height: 2.3rem;
		vertical-align: middle;
		padding-top: 4px;
	}

	#pagination i {
		/*  margin: 0 3px 0 3px; */

	}

	#pagination a.current {
		border: 1px solid #005baa;
		box-shadow: 0 1px 1px #999;
		background-color: #005baa;
		color: white;
	}

	#exampleModalCenter {
		z-index: 99;
		width: 25%;
		font-size: 24px;
		font-weight: 400;
		font-family: 'Poppins', sans-serif;
		text-align: center;
	}

	.test {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		z-index: 10;
		background-color: rgba(0, 0, 0, 0.5);
	}

	#close {
		color: #fff;
		background-color: #005baa;
	}

	#overlay_text {
		position: absolute;
		top: 50%;
		left: 50%;
		font-size: 50px;
		color: #FFF;
		transform: translate(-50%, -50%);
	}

	#overlay_text .img-fluid {
		max-width: 100%;
	}

	#overlay_text img {
		height: 150px;
	}

	#overlay {
		position: fixed;
		display: none;
		width: 100%;
		height: 100%;
		top: 0;
		left: 0;
		right: 0;
		bottom: 0;
		background-color: rgba(0, 0, 0, 0.5);
		z-index: 2;
		cursor: pointer;
	}

	.pop-body {
		border-radius: 25px;
	}

	.mb-0 {
		padding-bottom: 0px !important;
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
	.three-btn-one {
		background-color: white !important;
		color: #005baa !important;
		border-style: solid;
		border-color: #005baa !important;
		border-width: 2px;
		font-weight: 530;
		height: 42px !important;
		line-height: 40px !important;
		padding: 0 30px !important;
		margin-left: 14px !important;
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
	.tooltip-container {
		position: relative;
		display: inline-block;
	}

	.tooltip {
		display: none;
		position: absolute;
		bottom: 100%;
		left: 50%;
		transform: translateX(-50%);
		padding: 14px 10px;
		justify-content: center;
		align-items: center;
		gap: 10px;
		border-radius: 6px;
		background: #2D2D2D;
		box-shadow: 0px 4px 30px 0px rgba(0, 0, 0, 0.05);
		color: white;
		font-size: 12px;
		z-index: 1000;
	}

	.tooltip-container:hover .tooltip {
		display: inline-flex;
	}

	.tooltip-arrow {
		width: 0;
		height: 0;
		border-left: 5px solid transparent;
		border-right: 5px solid transparent;
		border-top: 5px solid #2D2D2D;
		position: absolute;
		top: 100%;
		left: 50%;
		transform: translateX(-50%);
	}
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
		$("#overlay").show();
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		// var e2 = document.getElementById("txnType").value;
		var PageNumber = document.getElementById("pgnum").value;

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
			$("#overlay").hide();


		}
		// else if (e2 == null || e2 == '') {
		//
		// 	alert("Please Select Payment Method");
		//
		// }

		else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			// var TxnType = document.getElementById("txnType").value;
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchEzySettleList?date='
					+ fromdateString
					+ '&date1='
					+ todateString
					// + '&txntype='
					// + e2
					+'&currPage='
					+ PageNumber;
			form.submit;

		}
	}

	function loadSelectData1() {
		$("#overlay").show();
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("FromDate").value;
		var e1 = document.getElementById("From1Date").value;
		// var e2 = document.getElementById("TXNType1").value;
		var PageNumber = document.getElementById("pgnum").value;

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



		document.getElementById("dateval1").value = fromdateString;
		document.getElementById("dateval2").value = todateString;
		/* document.getElementById("txnType").value = e2; */
		/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
		// var TxnType = document.getElementById("TXNType1").value;
		document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchEzySettleList?date='
				+ fromdateString
				+ '&date1='
				+ todateString
				// + '&txntype='
				// + e2
				+'&currPage='
				+ PageNumber;
		form.submit;


	}

	function loadExpData() {
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("export1").value;
		// var txnType = document.getElementById("txnType").value;

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
			$("#overlay").hide();
		}
		// } else if (txnType == null || txnType == '') {
		//
		// 	alert("Please Select Payment Method");

		// }
		else if (e2 == null || e2 == '') {

			alert("Please Select Export Type");
			$("#overlay").hide();
		} else {

			document.getElementById("datevalex1").value = fromdateString;
			document.getElementById("datevalex2").value = todateString;

			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/exportEzySettleList?date='
					+ fromdateString
					+ '&date1='
					+ todateString+
					// + '&txntype='
					// + txnType +
					'&export=' + e2;
			form.submit();

		}
	}

	function loadDropDate13() {

		var e = document.getElementById("export");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("export1").value = strUser;

	}

	// function loadDropDate14() {
	//
	// 	var e = document.getElementById("txnType");
	//
	// 	var strUser = e.options[e.selectedIndex].value;
	// 	document.getElementById("txnType1").value = strUser;
	//
	// }

	function loaddata() {

		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e5 = document.getElementById("export1").value;

		if (e == null || e1 == null || e == '' || e1 == '') {
			alert("Please select date(s)");
			$("#overlay").hide();
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
	function toggleKey(index) {
		const toggleContainer = document.getElementById(`toggle_rrn_`+index);
		const invoiceSpan = document.getElementById(`invoice_`+index);
		const header=document.getElementById('invoice_id_header');

		if (!toggleContainer || !invoiceSpan) {
			console.error('Element not found');
			return;
		}

		if (toggleContainer.classList.contains('showing-id')) {
			// Currently showing the ID, switch back to the icon
			toggleContainer.innerHTML = `<img class="toggle-icon" width="20" height="20" src="${pageContext.request.contextPath}/resourcesNew1/assets/blureye.svg" />`;
			toggleContainer.classList.remove('showing-id');
		} else {
			// Currently showing the icon, switch to showing the ID
			toggleContainer.innerHTML = invoiceSpan.textContent;
			toggleContainer.classList.add('showing-id');
			header.style.textAlign='center';
		}
	}


	jQuery(function() {
		var date = new Date();
		var currentMonth = date.getMonth();
		var currentDate = date.getDate();
		var currentYear = date.getFullYear();

		$('.datepicker').datepicker({
			minDate: new Date(currentYear, currentMonth-2, currentDate),
			maxDate: new Date(currentYear, currentMonth, currentDate+1)
		});
	});

	$('.pickadate-clear-buttons').pickadate({
		close: 'Close Picker',
		formatSubmit: 'dd/mm/yyyy',


	});
</script>
<body class="">
<div id="overlay">
	<div id="overlay_text">
		<img class="img-fluid"
			 src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
	</div>
</div>
<div class="test" id="pop-bg-color"></div>
<div class="container-fluid mb-0" id="pop-bg">
	<div class="row">



		<div class="col s12">
			<div class="card blue-bg text-white">
				<div class="card-content">
					<div class="d-flex align-items-center">
						<h3 class="text-white">
							<strong>EZYSETTLE Summary</strong>
						</h3>
					</div>


				</div>
			</div>
		</div>
	</div>

	<div class="modal fade bd-example-modal-xl pop-body"
		 style="width: 500px !important; height: 270px !important;"
		 id="exampleModalCenter" tabindex="-1" role="dialog"
		 aria-labelledby="mySmallModalLabel" aria-hidden="true"
		 style="text-align:center;">
		<div class="modal-dialog modal-xl">
			<div class="modal-content "
				 style="padding: 0 !important; margin: 0 !important;">
				<p class="pop-head"
				   style="background-color: #f9f9f9; width: 100%; height: 60px; color: #005baa; padding-top: 12px; border-bottom: 2px solid #ffa500;">Information</p>
				<img
						src="${pageContext.request.contextPath}/resourcesNew1/assets/NoRecordPNG.png"
						width="60px !important; height:60px !important;">
				<p id="innerText" style="font-size: 22px;"></p>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary"
							data-dismiss="modal" id="close" onclick="closepopup()"
							style="width: 106px !important; height: 38px !important; font-size: 18px; border-radius: 50px !important; margin-right: 187px !important; letter-spacing: 0.8px; font-family: 'Poppins', sans-serif; font-weight: medium; transform: translateY(-10px);">Close</button>
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
							<label for="from" style="margin: 0px;">
								From Date </label> <input type="hidden" name="date11" id="date11"
						<c:out value="${fromDate}"/>> <input type="text"
															 id="from" name="fromDate" class="validate datepicker"
															 onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
							<i class="material-icons prefix">date_range</i>


						</div>

						<div class="input-field col s12 m3 l3">

							<label for="to" style="margin: 0px;">To Date</label>
							<input type="hidden" name="date12" id="date12"
							<c:out value="${toDate}"/>> <input id="to" type="text"
															   name="toDate" class="datepicker"
															   onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
							<i class="material-icons prefix">date_range</i>
						</div>

<%--						<div class="input-field col s12 m3 l3">--%>


<%--							<select name="txnType" id="txnType"--%>
<%--									onchange="return loadDropDate14();">--%>
<%--								<option selected value="">Choose</option>--%>
<%--								<option value="CARD">CARD</option>--%>
<%--								<option value="BOOST">BOOST</option>--%>
<%--								<option value="GRABPAY">GRABPAY</option>--%>
<%--								<option value="FPX">FPX</option>--%>


<%--							</select> <label for="name">Payment Method</label> <input type="hidden"--%>
<%--																					  name="txnType1" id="txnType1">--%>
<%--						</div>--%>


						<div class="input-field col s12 m3 l3">

							<input type="hidden" name="export1" id="export1"
							<c:out value="${status}"/>> <select name="export"
																id="export" onchange="return loadDropDate13();">
							<option selected value="">Choose</option>
							<!-- <option value="PDF">PDF</option> -->
							<option value="EXCEL">EXCEL</option>
						</select> <label>Export Type</label>
						</div>

						<div class="input-field col s12 m3 l3">
							<div class="button-class" style="float: left !important;     display: flex;">

								<input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2">
								<button class="btn btn-primary blue-btn three-btn-one" type="button" style="margin-right: -8px;"
										onclick="return loadSelectData();">Search</button>


								<input type="hidden" name="dateex1" id="datevalex1"> <input
									type="hidden" name="dateex2" id="datevalex2">
								<button
										class="export-btn waves-effect waves-light btn btn-round indigo three-btn-one"
										 type="button" onclick="return loadExpData();">Export</button>
							</div>
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>

<%--	<div class="row">--%>
<%--		<div class="col s12">--%>
<%--			<div class="card border-radius">--%>
<%--				<div class="card-content padding-card">--%>

<%--					<div class="table-responsive m-b-20 m-t-15" id="page-table">--%>
<%--						<table id="data_list_table1"--%>
<%--							   class="table table-striped table-bordered">--%>
<%--							<thead>--%>
<%--							<tr>--%>
<%--								<th>Date</th>--%>
<%--								<th>MID</th>--%>
<%--								<th>TID</th>--%>
<%--								<th>Amount(RM)</th>--%>
<%--&lt;%&ndash;								<th>Card Number</th>&ndash;%&gt;--%>
<%--&lt;%&ndash;								<th>RRN</th>&ndash;%&gt;--%>
<%--								<th>Invoice ID</th>--%>
<%--								<th>Status</th>--%>
<%--&lt;%&ndash;								<th>Payment Method</th>&ndash;%&gt;--%>
<%--								<th>MDR Amount</th>--%>
<%--								<!-- <th>Net Amount</th>--%>
<%--                                <th>Payment Date</th> -->--%>
<%--								<th>Net Amount</th>--%>
<%--&lt;%&ndash;								<th>Payment Date</th>&ndash;%&gt;--%>
<%--							</tr>--%>
<%--							</thead>--%>
<%--							<tbody>--%>

<%--							<c:forEach items="${paginationBean.itemList}" var="dto"--%>
<%--									   varStatus="id">--%>
<%--								<tr>--%>
<%--									<fmt:parseDate value="${dto.date}" pattern="yyyyMMdd"--%>
<%--												   var="myDate" />--%>
<%--									<td><fmt:formatDate pattern="dd/MM/yyyy"--%>
<%--														value="${myDate}" /></td>--%>
<%--									<td>${dto.mid}</td>--%>
<%--									<td>${dto.tid}</td>--%>
<%--									<td style="text-align: right;">${dto.txnAmount}</td>--%>
<%--&lt;%&ndash;									<td>${dto.maskedPan}</td>&ndash;%&gt;--%>
<%--&lt;%&ndash;									<td class='key_hover'><span&ndash;%&gt;--%>
<%--&lt;%&ndash;											onclick="show_key('${id.index}')" id="hide_key_${id.index}"><img&ndash;%&gt;--%>
<%--&lt;%&ndash;											class="w24" src='data:image/png;base64,<%=eyeimg%> ' /> </span> <span&ndash;%&gt;--%>
<%--&lt;%&ndash;											onclick="hide_key('${id.index}')" id="show_key_${id.index}"&ndash;%&gt;--%>
<%--&lt;%&ndash;											class="hide_key">"{dto.rrn}"</span></td>&ndash;%&gt;--%>
<%--&lt;%&ndash;									<td class='key_hover'><span&ndash;%&gt;--%>
<%--&lt;%&ndash;											onclick="show_rrn('${id.index}')" id="hide_rrn_${id.index}"><img&ndash;%&gt;--%>
<%--&lt;%&ndash;											class="w24" src='data:image/png;base64,<%=eyeimg%> ' /> </span> <span&ndash;%&gt;--%>
<%--&lt;%&ndash;											onclick="hide_rrn('${id.index}')" id="show_rrn_${id.index}"&ndash;%&gt;--%>
<%--&lt;%&ndash;											class="hide_key">${dto.invoiceId}</span></td>&ndash;%&gt;--%>
<%--									<td>${dto.status}</td>--%>
<%--&lt;%&ndash;									<td>${dto.cardBrand}&nbsp;${dto.cardType}</td>&ndash;%&gt;--%>
<%--									<td style="text-align: right;">${dto.mdrAmount}</td>--%>
<%--									<td style="text-align: right;">${dto.netAmount}</td>--%>
<%--&lt;%&ndash;									<td>${dto.paymentDate}</td>&ndash;%&gt;--%>

<%--								</tr>--%>
<%--							</c:forEach>--%>
<%--							</tbody>--%>
<%--						</table>--%>

<%--					</div>--%>
<%--				</div>--%>
<%--			</div>--%>

<%--		</div>--%>
<%--	</div>--%>
<%--</div>--%>

	<div class="row">
		<div class="col s12">
			<div class="card border-radius">
				<div class="card-content padding-card">
					<div class="table-responsive m-b-20 m-t-15" id="page-table">
						<table id="data_list_table1" class="table table-striped table-bordered">
							<thead>
							<tr>
								<th style="font-size: 14px;">Date</th>
								<th style="font-size: 14px;">MID</th>
								<th style="font-size: 14px;">TID</th>
								<th style="text-align: right; font-size: 14px;">Amt(RM)</th>
								<th style="text-align: right; font-size: 14px;">MDR Amt(RM)</th>
								<th style="text-align: right; font-size: 14px;">Net Amt(RM)</th>
								<th style="font-size: 14px;">Status</th>
								<th id ="invoice_id_header" style="font-size: 14px;text-align: center">Invoice ID</th>
							</tr>
							</thead>
							<tbody>
							<c:forEach items="${paginationBean.itemList}" var="dto" varStatus="id">
								<tr>
									<fmt:parseDate value="${dto.date}" pattern="yyyy-MM-dd" var="myDate" />
									<td><fmt:formatDate pattern="dd/MM/yyyy" value="${myDate}" /></td>
									<td>
										<c:choose>
											<c:when test="${fn:length(dto.mid) > 16}">
												<div class="tooltip-container">
													<span class="truncated-text">${fn:substring(dto.mid, 0, 16)}...</span>
													<div class="tooltip">
														<div class="tooltip-arrow"></div>
															${dto.mid}
													</div>
												</div>
											</c:when>
											<c:otherwise>
												${dto.mid}
											</c:otherwise>
										</c:choose>
									</td>
									<td>${dto.tid}</td>
									<td style="text-align: right;">${dto.netAmount}</td>
									<td style="text-align: right;">${dto.mdrAmount}</td>
									<td style="text-align: right;">${dto.txnAmount}</td>
									<c:if test="${dto.status == 'Success'}">
										<td style="text-align: left; display: flex; align-items: center; justify-content: flex-start; font-size: 14px; margin-top: 5px;">
											<img src="${pageContext.request.contextPath}/resourcesNew1/assets/paid.svg"
												 width="20" height="20" style="margin-right: 5px;"/>
											<span style="color: #51CB49; font-weight: 600;">Success</span>
										</td>
									</c:if>
									<c:if test="${dto.status == 'Pending'}">
										<td style="text-align: left; display: flex; align-items: center; justify-content: flex-start; font-size: 14px; margin-top: 5px; ">
											<img src="${pageContext.request.contextPath}/resourcesNew1/assets/pending.svg"
												 width="20" height="20" style="margin-right: 5px;"/>
											<span style="color: #F9C84C; font-weight: 600;">Pending</span>
										</td>
									</c:if>
									<td class='key_hover' style="text-align: center;">
										<c:choose>
											<c:when test="${empty dto.invoiceId || dto.invoiceId.trim() == ''}">
												<div class="tooltip-container">
													<img class="" width="20" height="20" src="${pageContext.request.contextPath}/resourcesNew1/assets/Disable-eye.svg" />
													<div class="tooltip">
														<div class="tooltip-arrow"></div>
														Invoice Id not available
													</div>
												</div>
											</c:when>
											<c:otherwise>
												<div class="toggle-container" id="toggle_rrn_${id.index}" onclick="toggleKey(${id.index})">
													<img class="toggle-icon" width="20" height="20" src="${pageContext.request.contextPath}/resourcesNew1/assets/blureye.svg" />
												</div>
												<span id="invoice_${id.index}" class="hide_key">${dto.invoiceId}</span>
											</c:otherwise>
										</c:choose>
									</td>								</tr>
							</c:forEach>


							</tbody>
						</table>
						<div id="empty_res"></div>
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
<div id="pagination"></div>
<input type="hidden" id="pgnum">
<input type="hidden" id="FromDate">
<input type="hidden" id="From1Date">
	<div id="exportData" data-export="${exportData}"></div>

<%--<input type="hidden" id="TXNType1">--%>
<script>
	document.getElementById("pop-bg-color").style.display = "none";
	var fromDateServer = document.getElementById("FromDate").value = "${paginationBean.dateFromBackend}";
	var from1DateServer = document.getElementById("From1Date").value = "${paginationBean.date1FromBackend}";
	var exportData = document.getElementById("exportData").getAttribute("data-export");

	<%--var TransactionType = document.getElementById("TXNType1").value="${paginationBean.TXNtype}";  /*  ${paginationBean.TXNtype}*/--%>
	if(exportData === 'noRecords')
	{
		document.getElementById("exampleModalCenter").style.display = "block";
		document.getElementById("pop-bg-color").style.display = "block";
		document.getElementById("page-table").style.display = "none";
		document.getElementById("innerText").innerHTML = "Sorry, No Records Found";
		document.getElementById("innerText").style.fontWeight = "400";
		document.getElementById("innerText").style.color = "#171717";
		document.getElementById("nxt").style.cursor = "not-allowed";
		document.getElementById("nxt").style.opacity = "0.6";
		document.getElementById("nxt").disabled = "disabled";
	}

	function closepopup() {
		document.getElementById("exampleModalCenter").style.display = "none";
		document.getElementById("pop-bg-color").style.display = "none";
        document.location.href = '${pageContext.request.contextPath}/transactionUmweb/EzySettleList';
        form.submit();


    }

</script>
	<script>
		/* * * * * * * * * * * * * * * * *
         * Pagination
         * javascript page navigation
         * * * * * * * * * * * * * * * * */

		function dynamic(pgNo){
			document.getElementById("pgnum").value = pgNo;
			loadSelectData1();
		}

		function previous(pgNo){
			pgNo--;
			document.getElementById("pgnum").value = pgNo;
			loadSelectData1();
		}

		function next(pgNo){
			pgNo++;
			document.getElementById("pgnum").value = pgNo;
			loadSelectData1();
		}

		var Pagination = {

			code: '',

			// --------------------
			// Utility
			// --------------------

			// converting initialize data
			Extend: function(data) {
				data = data || {};
				// Pagination.size = data.size || 300;
				//console.log(Pagination.size);
				// Pagination.size = Math.ceil(${paginationBean.fullCount}/10) ||100;

				// old

				/* Pagination.size = ((${paginationBean.currPage})+4) ||100; */
				Pagination.size = Math.ceil(${paginationBean.querySize} / 20);

				/* Pagination.page = data.page || 1; */
				Pagination.page = ${paginationBean.currPage} || 1;
				Pagination.step = ((data.step)-4) || 3;
			},

			// add pages by number (from [s] to [f])
			Add: function(s, f) {
				for (var i = s; i < f; i++) {
					Pagination.code += '<a onclick="dynamic('+i+')">' + i + '</a>';
				}
			},

			// add last page with separator
			/*   Last: function() {
                  Pagination.code += '<i>...</i>';
              },
               */

			/*    // old
            Last: function() {
                Pagination.code += '<a onclick="dynamic(((Pagination.page)+1))">'+ ((Pagination.page)+1)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+2))">'+ ((Pagination.page)+2)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+3))">'+ ((Pagination.page)+3)+ '</a>'+'<i>...</i>';
            },
     */
			// old
			// add first page with separator
			/* 	First: function() {
                    if(Pagination.page==1){

                        Pagination.code += '<i>...</i>'+'<a onclick="dynamic(Pagination.page)" id="page1">'+Pagination.page+'</a>';

                    }
                    else{
                        Pagination.code += '<a>1</a>'+'<i>...</i>'+'<a  onclick="dynamic(((Pagination.page)-1))">'+((Pagination.page)-1)+'</a>'+'<a id="page2" onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';
                    }
                }, */


			// newer chnagess

			First: function() {
				Pagination.code += '<a onclick="dynamic(1)">1</a>';
				if (Pagination.page > 3) {
					Pagination.code += '<i>...</i>';
				}
				for (var i = Math.max(2, Pagination.page - 1); i <= Pagination.page; i++) {
					//directly goes to 1 instead of re-arranging
					Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
				}
			},

			// arranges ...
			Last: function() {

				// total page
				var lastPage = Math.ceil(${paginationBean.querySize} / 20);
				// three pg no after 1st pg no
				if (lastPage > Pagination.page + 3) {
					// generate <a> tag for 3 pg no
					for (var i = Pagination.page + 1; i <= Pagination.page + 3; i++) {
						Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
					}
					Pagination.code += '<i>...</i>';
//		         Pagination.code += '<a onclick="dynamic(' + lastPage + ')">' + lastPage + '</a>';
				} else {
					// if less than 3 page generate <a> tag
					for (var i = Pagination.page + 1; i <= lastPage; i++) {
						Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
					}
				}
			},




			// --------------------
			// Handlers
			// --------------------

			// change page
			Click: function() {
				Pagination.page = +this.innerHTML;
				Pagination.Start();
				dynamic(page);
			},

			// previous page
			Prev: function() {

				Pagination.page--;
				if (Pagination.page < 1) {
					Pagination.page = 1;
				}
				Pagination.Start();
				dynamic(page);
			},



			// next page


			Next: function() {
				Pagination.page++;
				if (Pagination.page > Pagination.size) {
					Pagination.page = Pagination.size;
				}
				Pagination.Start();
				dynamic(page);
			},



			// --------------------
			// Script
			// --------------------

			// binding pages
			Bind: function() {
				var a = Pagination.e.getElementsByTagName('a');
				for (var i = 0; i < a.length; i++) {
					if (+a[i].innerHTML === Pagination.page) a[i].className = 'current';
					a[i].addEventListener('click', Pagination.Click, false);
				}
			},

			// write pagination
			Finish: function() {
				Pagination.e.innerHTML = Pagination.code;
				Pagination.code = '';
				Pagination.Bind();
			},

			// find pagination type
			Start: function() {
				if (Pagination.size < Pagination.step * 2 + 6) {
					Pagination.Add(1, Pagination.size + 1);
				}
				else if (Pagination.page < Pagination.step * 2 + 1) {
					Pagination.Add(1, Pagination.step * 2 + 4);
					Pagination.Last();
				}
				else if (Pagination.page > Pagination.size - Pagination.step * 2) {
					Pagination.First();
					Pagination.Add(Pagination.size - Pagination.step * 2 - 2, Pagination.size + 1);
				}
				else {
					Pagination.First();
					Pagination.Add(Pagination.page - Pagination.step, Pagination.page + Pagination.step + 1);
					Pagination.Last();
				}
				Pagination.Finish();
			},



			// --------------------
			// Initialization
			// --------------------

			// binding buttons
			Buttons: function(e) {
				var nav = e.getElementsByTagName('a');

				nav[0].addEventListener('click', Pagination.Prev, false);
				nav[1].addEventListener('click', Pagination.Next, false);
			},

			// create skeleton
			Create: function(e) {

				var html = [
					'<a onclick="previous(${paginationBean.currPage})" style="width:7rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px; " id="previous">&#60;&#60; Previous</a>', // previous button
					'<span></span>',  // pagination container
					'<a onclick="next(${paginationBean.currPage})" style="width:6.5rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;" id="nxt">Next &#62;&#62;</a>'  // next button
				];

				e.innerHTML = html.join('');
				Pagination.e = e.getElementsByTagName('span')[0];
				Pagination.Buttons(e);


				// old
				/* if (${paginationBean.currPage} == 1) {
				var previousButton = document.getElementById("previous");
				previousButton.style.pointerEvents = "none";
				previousButton.style.opacity = "0.5";
			} */

				// newer chnages

				if (${paginationBean.currPage} == 1) {
					var previousButton = document.getElementById("previous");
					previousButton.style.pointerEvents = "none";
					previousButton.style.opacity = "0.5";
				}
				// my chnages
				if (${paginationBean.currPage} == Pagination.size) {
					var nextButton = document.getElementById("nxt");
					nextButton.style.pointerEvents = "none";
					nextButton.style.opacity = "0.5";
				}
				if (Pagination.size == 0) {
					var nextButton = document.getElementById("nxt");
					nextButton.style.pointerEvents = "none";
					nextButton.style.opacity = "0.5";
				}
				if (Pagination.size == 0) {
					var paginationContainer = document.getElementById("pagination");
					paginationContainer.style.display = "none";
				}


			},

			// init
			Init: function(e, data) {
				Pagination.Extend(data);
				Pagination.Create(e);
				Pagination.Start();
			}
		};


		var init = function() {
			Pagination.Init(document.getElementById('pagination'), {
				step: 3   // pages before and after current
			});
		};

		document.addEventListener('DOMContentLoaded', init, false);
	</script>
	<input type="hidden" id="responseData" value="${responseData}"/>
	<script>

		var responseData = document.getElementById("responseData").value;

		if(typeof responseData !== 'undefined' && responseData==='No Records found') {
			document.getElementById("empty_res").innerText = "No data available";
		}
	</script>

	<style>
		input.datepicker:focus, input.datepicker:active {
			box-shadow: none !important;
			-moz-box-shadow: none;
			outline: none;
			border-color: initial;
		}
		input.datepicker {
			box-shadow: none !important;
			outline: none !important;
		}
		#empty_res{
			display: flex;
			justify-content: center;
			margin-top: 10px;
		}

	</style>

</body>

</html>