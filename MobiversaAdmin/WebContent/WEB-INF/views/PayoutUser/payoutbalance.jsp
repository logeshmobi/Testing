<%@page import="com.mobiversa.payment.controller.PayoutUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="java.util.ResourceBundle"%>
<%
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

.eye-symbol {
	font-size: 24px; /* adjust the font size as needed */
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

#New Popup changes

.modal {
  display: none;
  position: fixed;
  z-index: 1;
  left: 0;
  top: 0;
  width: 80%;
  height: 80%;
  overflow: auto;
  background-color: rgba(0,0,0,0.4);
}

.modal-content {
  background-color: #fefefe;
  margin: 10% auto;
  padding: 20px;
  border: 1px solid #888;
  width: 80%;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
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

</script>
<body class="">
	<div class="container-fluid">
		<div class="row">



			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>PAYOUT Balance</strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>
<%--- 
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
		</div> --%>

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
									
										<th>Business Name</th>
										<th  Style="text-align: right;">Overdraft Limit</th>
										<th  Style="text-align: right;">Available Balance</th>
										<th  Style="text-align: right;">Overdraft Utilized</th>
<%--										<th Style="text-align: right;">OverDraft Amount</th>--%>
										<!-- <th>Action</th> -->

									</tr>
								</thead>
								<tbody>

<%--									<c:forEach items="${paginationBean.itemList}" var="dto"--%>
<%--										varStatus="loop">--%>
<%--										<tr class="dto-${loop.index}">--%>
<%--											<td >${dto.businessname}</td>--%>
<%--											<td Style="text-align: right;">${dto.overDraftLimit}</td>--%>
<%--											<td  Style="text-align: right;">${dto.availableBalance}</td>--%>
<%--											<td id="Overdraft-Utilized" Style="text-align: right;"></td>--%>
<%--&lt;%&ndash;											 <td  Style="text-align: right;">${dto.totalamount}</td>&ndash;%&gt;--%>
<%--&lt;%&ndash;											 <td  Style="text-align: right;">${dto.overDraftAmt}</td>&ndash;%&gt;--%>

<%--										</tr>--%>
<%--									</c:forEach>--%>


<c:forEach items="${paginationBean.itemList}" var="dto" varStatus="loop">
	<tr class="dto-${loop.index}">
		<td>${dto.businessname}</td>
		<td style="text-align: right;">${dto.overDraftLimit}</td>
		<td style="text-align: right;">${dto.availableBalance}</td>
		<td id="Overdraft-Utilized-${loop.index}" style="text-align: right;"></td>
	</tr>
	<script>
		var overDraftLimit = "${dto.overDraftLimit.trim()}";
		var availableBalance = "${dto.availableBalance.trim()}";

		// Remove commas from the numeric strings
		var overDraftLimitCleaned = overDraftLimit.replace(/,/g, '');
		var availableBalanceCleaned = availableBalance.replace(/,/g, '');

		// parse
		var overdraftLimitNum = parseFloat(overDraftLimitCleaned);
		var availableBalanceNum = parseFloat(availableBalanceCleaned);

		console.log(overdraftLimitNum);
		console.log(availableBalanceNum);

		var settlementAmt = availableBalanceNum-overdraftLimitNum;
		console.log(overdraftLimitNum);
		console.log(availableBalanceNum);
		console.log("settlementAmt is"+settlementAmt);


		// Check
		if (!isNaN(overdraftLimitNum) && !isNaN(availableBalanceNum) &&!isNaN(settlementAmt) ) {
			if (settlementAmt < 0 ) {
				// append
				document.getElementById("Overdraft-Utilized-${loop.index}").innerText = "Yes";
				document.getElementById("Overdraft-Utilized-${loop.index}").style.color = "red";
			} else {
				document.getElementById("Overdraft-Utilized-${loop.index}").innerText = "No";
				document.getElementById("Overdraft-Utilized-${loop.index}").style.color = "#1dd41d";
			}
		} else {
			console.log("!!!!");
		}
	</script>
</c:forEach>














								</tbody>
							</table>

						</div>
					</div>
				</div>

			</div>
		</div>
	</div>


<%--- <div id="overlay-popup"></div>
	<div class="mobi-modal" id="mobi_modal_popup" tabindex="-1">
		<div class="mobi-modal-dialog">
			<div
				class="mobi-modal-content mobi-modal-content-center mobi-modal-content-mobile mobi-60-per">
				<div class="mobi-modal-header">
					<h5 class="mobi-modal-title mobi-text-dark"></h5>
					<img class="img-fluid mobi-close" onclick="closeDialog()"
						src="${pageContext.request.contextPath}/resourcesNew1/assets/close.png">

				</div>--%>

<%---			<div
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

				</div> ----%>

		<%-- 		<div class="mobi-modal-footer"></div> --%>

			</div>
		</div>
	</div>




	<%--	$(document).ready(function() {
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

								//�alert("Sucess");
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {
							// Handle any errors that occur during the request
							alert("error");
							closeDialog()
							console.error(errorThrown);
						}
					});

		} --%>

		//saravana

	<%--	function statuschange() {
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

		}   --%>



</body>

</html>