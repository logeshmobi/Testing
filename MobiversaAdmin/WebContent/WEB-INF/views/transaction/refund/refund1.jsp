
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page
	import="com.mobiversa.payment.controller.MerchantWebUploadTCController"%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">


<script type="text/javascript">
	window.history.replaceState({}, document.title, "/MobiversaAdmin/"
			+ "upTC/");
</script>


<style>
.td {
	text-align: right;
}

.curved-btn {
	border-radius: 20px;
}

.move-left {
	position: relative;
	left: 40px;
	width: 100px;
}

.short-underline {
	border: none;
	border-bottom: 1px solid black;
}

#exampleModalCenter {
	z-index: 99;
	width: 25%;
	font-size: 24px;
	font-weight: 400;
	font-family: 'Poppins', sans-serif;
	text-align: center;
}

#button-container {
	display: flex;
	justify-content: center;
}

.button-class {
	float: right;
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
</style>


<script type="text/javascript">
	function findTxn() {
		$("#overlay").show();
		var TxnId = document.getElementById("txnId").value;

		var type = document.getElementById("type");
		console.log(type);
		if (type === null) {
			console.log("Please Select Product Type");
			alert("Please Select Product Type");
		} else if (type.value === "") {
			console.log("Please Select Refund Type");
			alert("Please Select Product Type");
		} else if (type.value === "All") {
			console.log("Please Select Refund Type");
			alert("Please Select Product Type");
		}

		// Get the selected option's value
		var selectType = type.value;

		console.log("Selected value:", selectType);

		document.location.href = '${pageContext.request.contextPath}/refund/findTxnId?TxnId='
				+ TxnId + '&selectType=' + selectType;

		form.submit;

	}

	function selectType() {

		var refund = document.getElementById("SelectRefund").value;
		console.log(" selected refund : ", refund);
		switch (refund) {
		case "fullRefund":
			var amount = document.getElementById("txnAmt").value;

			document.getElementById("hiddenAmountField").value = amount;
			document.getElementById('hiddenAmountField').setAttribute(
					'readonly', true);

			console.log("Full Refund selected : ", amount);
			break;
		case "ParRefund":
			document.getElementById("hiddenAmountField").value = '';
			document.getElementById('hiddenAmountField').removeAttribute(
					'readonly');

			console.log("Partial Refund selected");
			break;
		default:

			console.log("Choose Type selected");
			break;
		}
	}

	function checkTransaction() {
		$("#overlay").show();

		var refundAmt = document.getElementById("hiddenAmountField").value;
		var invoice = document.getElementById("invoice").value;
		var TxnId = document.getElementById("TxnIDdata").value;
		/*  var partialRefundAmt = document.getElementById("hiddenPartialAmountField").value; */
		var productType = document.getElementById("productType").value;
		var txnMid = document.getElementById("txnMid").value;
		var transactionAmount = document.getElementById("transactionAmount").value;
		var selectType = document.getElementById("selectType").value;
		var refund = document.getElementById("SelectRefund").value;

		if (refundAmt > transactionAmount) {
			alert("Refund Amount Invalid");
			console.log(refundAmt);
			console.log(transactionAmount);
			console.log(TxnId);
			console.log(selectType);
			document.location.href = '${pageContext.request.contextPath}/refund/findTxnId?TxnId='
					+ TxnId + '&selectType=' + selectType;

			form.submit;
		} else if (refundAmt == '') {
			alert("Refund Amount Invalid");

			document.location.href = '${pageContext.request.contextPath}/refund/findTxnId?TxnId='
					+ TxnId + '&selectType=' + selectType;

			form.submit;
		} else {

			document.location.href = '${pageContext.request.contextPath}/refund/checkTxn?refundAmt='
					+ refundAmt
					+ '&invoice='
					+ invoice
					+ '&TxnId='
					+ TxnId
					+ '&productType='
					+ productType
					+ '&txnMid='
					+ txnMid
					+ '&transactionAmount='
					+ transactionAmount
					+ '&selectType=' + selectType + '&refund=' + refund;

			form.submit;
		}
		/*  
		 $(document).ready(function() {
			  var refundAmt = $("#hiddenAmountField").val();
			  var partialRefundAmt = $("#hiddenPartialAmountField").val();

			  // Show refundAmt value in the HTML page
			  $("#refundAmtValue").text(refundAmt);

			  // Hide the content if refundAmt is null or empty
			  if (!refundAmt) {
			    $("#refundAmountContainer").hide();
			  }
			});
		 */

		/*  if(refundAmt!=null){
			 document.getElementById("hiddenPartialAmountField").disabled = true;
			 document.getElementById("hiddenAmountField").disabled = false;
		 }else if(partialRefundAmt!=null){
			 document.getElementById("hiddenAmountField").disabled = true;
			 document.getElementById("hiddenPartialAmountField").disabled = false;
		 } */
		/* 	debugger;
		 console.log("refundAmt :",refundAmt);
		 alert(TxnId);
		 debugger; */

	}

	function proccespopup() {
		$("#overlay").show();

		var merchantId = document.getElementById("merchantId").value;
		var refundAmount = document.getElementById("refundAmount").value;
		var txnId = document.getElementById("refundTxnID").value;
		var settlementDate = document.getElementById("settlementDate").value;
		var productType = document.getElementById("productType").value;
		var txnMid = document.getElementById("txnMid").value;
		var totalSettlementAmount = document
				.getElementById("totalSettlementAmount").value;
		var transactionAmount = document.getElementById("transactionAmount1").value;

		var selectType = document.getElementById("selectType").value;
		var refundType = document.getElementById("refundType").value;
		//alert(refundType);
		/* var baseUrl = '${pageContext.request.contextPath}/refund/proccedToRefund';
		var queryParams = `?merchantId=${merchantId}&refundAmount=${refundAmount}&settlementDate=${settlementDate}&txnId=${txnId}&productType=${productType}&txnMid=${txnMid}&totalSettlementAmount=${totalSettlementAmount}&transactionAmount=${transactionAmount}&selectType=${selectType}`;

		var fullUrl = baseUrl + queryParams;

		document.location.href = fullUrl;
		 */

		if (selectType.toLowerCase() === "cards" || selectType.toLowerCase() === "fpx") {

			document.location.href = '${pageContext.request.contextPath}/refund/proccedToRefundCard?merchantId='
					+ merchantId
					+ '&refundAmount='
					+ refundAmount
					+ '&txnId='
					+ txnId
					+ '&settlementDate='
					+ settlementDate
					+ '&productType='
					+ productType
					+ '&txnMid='
					+ txnMid
					+ '&totalSettlementAmount='
					+ totalSettlementAmount
					+ '&transactionAmount='
					+ transactionAmount
					+ '&selectType=' + selectType + '&refundType=' + refundType;

			form.submit;

		} else {
			document.location.href = '${pageContext.request.contextPath}/refund/proccedToRefundWallet?merchantId='
					+ merchantId
					+ '&refundAmount='
					+ refundAmount
					+ '&txnId='
					+ txnId
					+ '&settlementDate='
					+ settlementDate
					+ '&productType='
					+ productType
					+ '&txnMid='
					+ txnMid
					+ '&totalSettlementAmount='
					+ totalSettlementAmount
					+ '&transactionAmount='
					+ transactionAmount
					+ '&selectType=' + selectType + '&refundType=' + refundType;

			form.submit;
		}

	}
</script>

</head>

<body>
	<div class="container-fluid">

		<div id="overlay">
			<div id="overlay_text">
				<img class="img-fluid"
					src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
			</div>
		</div>


		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Merchant List </strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>







		<c:if test="${checkrefund == 'success'}">

			<div class="modal fade bd-example-modal-xl pop-body"
				style="width: 500px !important; height: 325px !important; border-radius: 15px;"
				id="exampleModalCenter" tabindex="-1" role="dialog"
				aria-labelledby="mySmallModalLabel" aria-hidden="true"
				style="text-align:center;">
				<div class="modal-dialog modal-xl">
					<div class="modal-content "
						style="padding: 0 !important; margin: 0 !important;">
						<p class="pop-head"
							style="background-color: #f9f9f9; width: 100%; height: 60px; color: #005baa; padding-top: 12px; border-bottom: 2px solid #ffa500;">Confirmation</p>
						<img
							src="${pageContext.request.contextPath}/resourcesNew1/assets/refundSuccess.png"
							width="60px !important; height:60px !important;">
						<!-- <p id="innerText" style="font-size: 22px; color: rgb(10, 0, 0);">
							Proceed To The Refund Request</p>
						 -->
						<br> <br>
						<div style="text-align: left; font-size: 15px; margin-left: 45px;">
							<div class="row">
								<span id="txnId">Refund Txn Id : <span id="txnId">${refundTxnId}</span></span>
							</div>
							<div class="row">
								<span id="txnId">Product Type : <span id="txnId">${selectType}</span></span>
							</div>
							<div class="row">
								<span id="txnId">Amount : <span id="txnId">${refundamount}</span></span>
							</div>
						</div>


						<%-- <label for="Email">Transaction ID</label> <input type="text"
										 name="txnId" value="${refundTxnId}"
										id="txnId" path="txnId" readonly> --%>


						<%-- <label for="Email">Refund Type :</label> <input type="text"
										name="txnId" value="${selectType}"
										id="txnId" path="txnId" readonly> --%>
						<%-- <span id="txnId">${selectType}</span>
							<span id="txnId">${refundamount}</span> --%>

						<%-- <div class="input-field col s12 m6 l6 ">
									<label for="Email">Refund Amount</label> <input type="text"
										class="short-underline" name="txnId" value="${refundamount}"
										id="txnId" path="txnId" readonly>

						</div>
						 --%>




						<button type="button" class="btn btn-light"
							style="padding-left: 50px; padding-right: 50px; width: 167px; border-color: #2961ff; border-radius: 20px; color: #2961ff; background-color: white;"
							onclick="closepopup()">Close</button>
						<!-- <button type="button" class="btn btn-primary blue-btn"
							style="width: 40%;">
							<span style="text-align: center "
								onclick="closepopup()">Close</span>
						</button> -->
						<button type="button" class="btn btn-primary"
							style="padding-left: 50px; padding-right: 50px;"
							onclick="proccespopup()">Proceed to refund</button>
						<!-- style="border-radius: 20px !important; border: 2px solid #005baa; width: 120px; background-color: #f9f9f9; color: #005baa;" -->

					</div>
				</div>
			</div>


		</c:if>






		<c:if test="${checkrefund == 'failure' || checkrefund == null }">

			<div class="modal fade bd-example-modal-xl pop-body"
				style="width: 530px !important; height: 310px !important; border-radius: 15px;"
				id="exampleModalCenter" tabindex="-1" role="dialog"
				aria-labelledby="mySmallModalLabel" aria-hidden="true"
				style="text-align:center;">
				<div class="modal-dialog modal-xl">
					<div class="modal-content "
						style="padding: 0 !important; margin: 0 !important;">
						<p class="pop-head"
							style="background-color: #f9f9f9; width: 100%; height: 60px; color: #005baa; padding-top: 12px; border-bottom: 2px solid #ffa500;">Alert</p>
						<img
							src="${pageContext.request.contextPath}/resourcesNew1/assets/noRefund.png"
							width="60px !important; height:60px !important;">
						<p id="innerText" style="font-size: 22px; color: rgb(10, 0, 0);">
							No settlements for this account in the next 5 days. <br>Please
							try again tomorrow
						</p>

						<button type="button" class="btn btn-primary"
							style="padding-left: 50px; padding-right: 50px; width: 220px"
							onclick="closepopup()">Close</button>

					</div>
				</div>
			</div>


		</c:if>





		<c:if test="${refunded == 'Refunded' }">

			<div class="modal fade bd-example-modal-xl pop-body"
				style="width: 530px !important; height: 310px !important; border-radius: 15px;"
				id="exampleModalCenter" tabindex="-1" role="dialog"
				aria-labelledby="mySmallModalLabel" aria-hidden="true"
				style="text-align:center;">
				<div class="modal-dialog modal-xl">
					<div class="modal-content "
						style="padding: 0 !important; margin: 0 !important;">
						<p class="pop-head"
							style="background-color: #f9f9f9; width: 100%; height: 60px; color: #005baa; padding-top: 12px; border-bottom: 2px solid #ffa500;">Alert</p>
						<img
							src="${pageContext.request.contextPath}/resourcesNew1/assets/noRefund.png"
							width="60px !important; height:60px !important;">
						<p id="innerText" style="font-size: 22px; color: rgb(10, 0, 0);">
							This Transaction's Refund Already Executed.</p>

						<button type="button" class="btn btn-primary"
							style="padding-left: 50px; padding-right: 50px; width: 220px"
							onclick="closepopup()">Close</button>

					</div>
				</div>
			</div>
		</c:if>

		<c:if test="${checkrefund == 'refundsuccess' }">

			<div class="modal fade bd-example-modal-xl pop-body"
				id="exampleModalCenter" tabindex="-1" role="dialog"
				aria-labelledby="mySmallModalLabel" aria-hidden="true"
				style="text-align: center; border-radius: 9px;">
				<div class="modal-dialog modal-xl">

					<div class="modal-header" style="text-align: right;">
						<button type="button" class="close" data-dismiss="modal"
							onclick="closepopup()"
							style="border-radius: 8px; margin-right: 10px; margin-top: 10px; border-color: white;">&times;</button>
					</div>
					<div class="modal-body">
						<img
							src="${pageContext.request.contextPath}/resourcesNew1/assets/success.png"
							style="width: 87px;">
					</div>
					<div class="modal-footer" style="text-align: center;">
						<p>Success</p>
					</div>

				</div>
			</div>
		</c:if>

		<c:if test="${checkrefund == 'refundError' }">

			<div class="modal fade bd-example-modal-xl pop-body"
				id="exampleModalCenter" tabindex="-1" role="dialog"
				aria-labelledby="mySmallModalLabel" aria-hidden="true"
				style="text-align: center; border-radius: 9px;">
				<div class="modal-dialog modal-xl">

					<div class="modal-header" style="text-align: right;">
						<button type="button" class="close" data-dismiss="modal"
							onclick="closepopup()"
							style="border-radius: 8px; margin-right: 10px; margin-top: 10px; border-color: white;">&times;</button>
					</div>
					<div class="modal-body">
						<img
							src="${pageContext.request.contextPath}/resourcesNew1/assets/error.png"
							style="width: 87px;">
					</div>
					<div class="modal-footer" style="text-align: center;">
						<p>Failed</p>
					</div>

				</div>
			</div>
		</c:if>



		<input type="hidden" id="invoice" name="invoice" value="${invoice}">
		<input type="hidden" id="merchantId" name="merchantId"
			value="${merchantId}"> <input type="hidden" id="refundAmount"
			name="refundAmount" value="${refundamount}"> <input
			type="hidden" id="refundTxnID" name="refundTxnID"
			value="${refundTxnId}"> <input type="hidden" id="TxnIDdata"
			name="TxnIDdata" value="${TxnId}"> <input type="hidden"
			id="settlementDate" name="settlementDate" value="${settlementDate}">
		<input type="hidden" id="productType" name="productType"
			value="${productType}"> <input type="hidden" id="txnMid"
			name="txnMid" value="${txnMid}"> <input type="hidden"
			id="totalSettlementAmount" name="totalSettlementAmount"
			value="${totalSettlementAmount}"> <input type="hidden"
			id="transactionAmount" name="transactionAmount" value="${Amt}">
		<input type="hidden" id="transactionAmount1" name="transactionAmount1"
			value="${transactionAmount}"> <input type="hidden"
			id="selectType" name="selectType" value="${selectType}"> <input
			type="hidden" id="refundType" name="refundType" value="${refundType}">




		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">

						<div class="d-flex align-items-center">
							<h5>Enter the Transaction ID</h5>
						</div>



						<div class="row">

							<div class="input-field col s12 m6 l6 ">
								<label for="Email">Transaction ID</label> <input type="text"
									class="short-underline" placeholder="Enter Transaction ID ..."
									name="txnId" id="txnId" path="txnId">
							</div>

							<%-- <div class="input-field col s12 m6 l6 ">
								<label for="Email">Transaction ID</label> <input type="text"
									placeholder="Enter Transaction ID" name="username"
									id="username" value="${merchant.username}" path="username">
							</div> --%>





							<label style="margin-left: -24%;">Refund Type </label>
							<div class="input-field col s12 m3 l3">
								<select id="type" name="type_1">
									<option value="All">-- Choose --</option>
									<option value="cards">Cards</option>
									<option value="boost">Boost</option>
									<option value="grab">Grab</option>
									<option value="tng">TNG</option>
									<option value="spp">SPP</option>

								</select>

							</div>




							<br>
							<button type="button"
								class="btn btn-primary curved-btn move-left" onclick="findTxn()">Submit</button>

							<%-- <a href="javascript:void(0)" class="submitBtn" id="openNewWin"
								onclick="javascript: openNewWin('${merchant.username}','${merchant.id }')">
								<font color="white">Submit</font>
							</a> --%>


						</div>



					</div>
				</div>
			</div>



		</div>





		<c:if test="${TxnId != null || Amt != null}">

			<div class="row">
				<div class="col s12">

					<div id="overlay">
						<div id="overlay_text">
							<img class="img-fluid"
								src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
						</div>
					</div>



					<div class="card border-radius">
						<div class="card-content padding-card">
							<div class="d-flex align-items-center">
								<h5>Transaction Details</h5>
							</div>
							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Email">Transaction ID</label> <input type="text"
										class="short-underline" name="txnId" value="${TxnId}"
										id="txnId" path="txnId" readonly>

								</div>
								<div class="input-field col s12 m6 l6 ">
									<label for="Email">Transaction Amount</label> <input
										type="text" class="short-underline" name="txnId"
										value="${Amt}" id="txnAmt" path="txnId" readonly>

								</div>

								<c:choose>
									<c:when
										test="${selectType.toLowerCase() == 'tng' || selectType.toLowerCase() == 'spp' }">
										<div class="input-field col s12 m6 l6">
											<select name="txnId" id="SelectRefund"
												class="short-underline" onchange="selectType()">
												<option value="">Choose Type</option>
												<option value="fullRefund">Full Refund</option>

											</select> <label for="tx"> Refund Type</label>
										</div>

									</c:when>

									<c:otherwise>

										<div class="input-field col s12 m6 l6">
											<select name="txnId" id="SelectRefund"
												class="short-underline" onchange="selectType()">
												<option value="">Choose Type</option>
												<option value="fullRefund">Full Refund</option>
												<option value="ParRefund">Partial Refund</option>
											</select> <label for="tx"> Refund Type</label>
										</div>

									</c:otherwise>
								</c:choose>




								<div class="input-field col s12 m6 l6 ">
									<label for="Email"> Refund Amount </label> <input type="text"
										placeholder="0.00" class="short-underline" name="txnId"
										id="hiddenAmountField" path="txnId" readonly>

								</div>

								<!-- 	<div class="input-field col s12 m6 l6 ">
									<label for="Email"> </label> <input type="text"
										class="short-underline" name="txnId" id="hiddenPartialAmountField"
										path="txnId">
								</div> -->
								<br>


								<div class="row">
									<div class="input-field col s7 ">
										<div class="button-class">

											<button type="button"
												class="btn btn-primary curved-btn text-center "
												onclick="checkTransaction()">Submit</button>
										</div>
									</div>
								</div>


							</div>
						</div>
					</div>
				</div>
			</div>


		</c:if>


	</div>








	<%--  </form> --%>

</body>

<script type="text/javascript">
	document.getElementById("exampleModalCenter").style.display = "block";

	function closepopup() {
		document.getElementById("exampleModalCenter").style.display = "none";
		document.getElementById("pop-bg-color").style.display = "none";
	}

	jQuery(document).ready(function() {
		$('#merchantName').select2();

	});
</script>


</html>

