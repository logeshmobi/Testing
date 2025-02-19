<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="com.mobiversa.payment.controller.SettlementUserController"%>
<%@page import="com.mobiversa.common.bo.SettlementMDR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style>
.error {
	color: red;
	font-weight: bold;
}

.asterisk_input:before {
	content: " *";
	color: #062630;
	position: absolute;
	margin: 0px 0px 0px -20px;
	font-size: xx-large;
	padding: 0 5px 0 0;
}
</style>
</head>

<script lang="JavaScript">
	function loadSelectData() {

		var payoutStatus = document.getElementById('validateStatus').value;
		if (payoutStatus == null || payoutStatus == '') {

			alert("Please update the payout status.");

		} else {
			document.getElementById('form1').submit();

		}

	}
</script>

<body>
	<form:form method="post"
		action="payoutDataUser/updatePayoutStatus/${payoutData.invoiceIdProof}/${payoutData.merchant.id}"
		commandName="payoutData" name="form1" id="form1">
		<div class="container-fluid">
			<div class="row">
				<div class="col s12">
					<div class="card blue-bg text-white">
						<div class="card-content">
							<div class="d-flex align-items-center">
								<h3 class="text-white">
									<strong>Change Payout Status</strong>
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
								<div class="input-field col s12 m6 l6 ">
									<label for="Merchant Name">Merchant Name</label> <input
										type="text" id="mid" placeholder="" name="createdBy"
										path="createdBy" value="${payoutData.createdBy}"
										readonly="readonly" />
								</div>

								<div class="input-field col s12 m6 l6 ">
									<label for="Customer Name">Customer Name</label> <input
										type="text" id="payeeName" placeholder="" name="payeeName"
										path="payeeName" value="${payoutData.payeeName}"
										readonly="readonly" />
								</div>

								<div class="input-field col s12 m6 l6 ">
									<label for="Business Registration No">Business
										Registration No</label> <input type="text" id="payeeBRN"
										placeholder="" name="payeeBRN" path="payeeBRN"
										value="${payoutData.payeeBRN}" readonly="readonly" />
								</div>

								<div class="input-field col s12 m6 l6 ">
									<label for="Account No">Account No</label> <input type="text"
										id="payeeAccNumber" placeholder="" name="payeeAccNumber"
										path="payeeAccNumber" value="${payoutData.payeeAccNumber}"
										readonly="readonly" />
								</div>

								<div class="input-field col s12 m6 l6 ">
									<label for="Bank Name">Bank Name</label> <input type="text"
										id="payeeBankName" placeholder="" name="payeeBankName"
										path="payeeBankName" value="${payoutData.payeeBankName}"
										readonly="readonly" />
								</div>




								<div class="input-field col s12 m6 l6 ">
									<label for="Payout Amount(RM)">Payout Amount(RM)</label> <input
										type="text" id="payoutAmount" placeholder=""
										name="payoutAmount" path="payoutAmount"
										value="${payoutData.payoutAmount}" readonly="readonly" />
								</div>

								<div class="input-field col s12 m6 l6 ">
									<label for="Payout Date">Payout Date</label> <input type="text"
										id="payoutDate" placeholder="" name="payoutDate"
										path="payoutDate" value="${payoutData.payoutDate}"
										readonly="readonly" />
								</div>

								<div class="input-field col s12 m6 l6 ">
									<%-- 	<form:select path="payoutStatus" class="form-control"
										value="${payoutStatus}" name="payoutStatus"> --%>
									<form:select path="payoutStatus" id="validateStatus"
										class="form-control" value="" name="payoutStatus">
										<form:option value="" disabled="disabled"></form:option>
										<form:option value="pp">Paid</form:option>
										<%-- 	<form:option value="F">Failed</form:option>
										<form:option value="">Requested</form:option>
										<form:option value="S">Processed</form:option>
										<form:option value="A">Processing</form:option> --%>
									</form:select>
									<label for="Payout Status">Payout Status</label>
								</div>




								<div class="row">
									<div class="input-field col s12 m6 l6 ">
										<div class="button-class">
											<button class="btn btn-primary blue-btn" type="button"
												onclick=" return loadSelectData()">Submit</button>
										</div>
									</div>
								</div>
							</div>
							<style>
.select-wrapper .caret {
	fill: #005baa;
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

/* .submitBtn {
	padding: 8px 20px;
	border-radius: 10px;
	background-color: #54b74a;
	color: #fff;
	margin: auto;
	display: table;
} */
</style>


						</div>
					</div>
				</div>

			</div>
		</div>
	</form:form>
</body>
</html>
