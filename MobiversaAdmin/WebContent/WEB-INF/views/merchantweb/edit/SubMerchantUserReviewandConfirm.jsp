<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@page import="com.mobiversa.common.bo.MID"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Edit Sub Merchant Profile</title>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css"
	rel="stylesheet" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>


<style>
td, th {
	padding: 7px 8px;
	color: #707070;
}

thead th {
	border-bottom: 1px solid #ffa500;
	color: #4377a2;
}
</style>
<script type="text/javascript">
	function submitload() {
		// window.location.href = "${pageContext.request.contextPath}/transactionweb/editSubMerchantUserReviewandConfirmPost?id=${salutation}";

		document.getElementById('myform').submit();
	}

	function load() {

		swal(
				{
					title : "Are you sure? you want to edit this Sub Merchant Profile",
					text : "it will be edited..!",
					type : "warning",
					showCancelButton : true,
					confirmButtonText : "Yes, edit it!",
					cancelButtonText : "No, cancel!",
					closeOnConfirm : false,
					closeOnCancel : false
				},
				function(isConfirm) {
					if (isConfirm) {
						$("#form-edit").submit();
					} else {

						var url = "${pageContext.request.contextPath}/admin/submerchantSum";
						$(location).attr('href', url);

					}
				});

	}
</script>

<script type="text/javascript">
	function load1() {
		var url = "${pageContext.request.contextPath}/admin/submerchantSum";
		$(location).attr('href', url);
	}
</script>

</head>
<body>
	<div class="container-fluid">
		<div class="row">



			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Edit Sub Merchant Profile</strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>


		<form method="post" id="form-edit" name="form-edit"
			action="${pageContext.request.contextPath}/admin/editSubMerchantUserReviewandConfirmPost"
			commandName="merchant">

			<input type="hidden" name="id" value="${salutation}"> <input
				type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">



			<div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">


							<div class="row">



								<div class="input-field col s12 m6 l6 ">
									<label for="ActivationDate">Activation Date</label> <input
										type="text" id="activationDate" name="activationDate"
										value="${Adate}" readonly>

								</div>
								<div class="input-field col s12 m6 l6 ">
									<label for="BusinessName">BusinessName</label> <input
										type="text" id="businessName" name="businessName"
										value="${merchant.businessName}" readonly>

								</div>

							</div>


							<div class="row">
								<div class="input-field col s12 m6 l6 ">
									<label for="Email">Email</label> <input type="text" id="email"
										name="email" value="${merchant.email}" readonly>
								</div>
								<div class="input-field col s12 m6 l6 ">
									<label for="City">City</label> <input type="text" id="city"
										name="city" value="${merchant.city}" readonly>

								</div>

							</div>



							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="State">State</label> <input type="text" id="state"
										name="state" value="${merchant.state}" readonly />

								</div>
								<div class="input-field col s12 m6 l6 ">
									<label for="MID">MID</label> <input type="text" id="mid"
										name="mid" value="${Submid}" readonly>

								</div>

							</div>

							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Title">Title</label> <input type="text"
										id="salutation" name="salutation"
										value="${merchant.salutation}" readonly>
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="Contact Person Name">Contact Person Name</label> <input
										type="text" id="cpName" name="cpName"
										value="${merchant.contactPersonName}" readonly>
								</div>


							</div>

							<div class="row">


								<div class="input-field col s12 m6 l6 ">
									<label for="Contact Person Number">Contact Person
										Number</label> <input type="text" id="cpNo" name="cpNo"
										value="${merchant.contactPersonPhoneNo}" readonly>
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="Trading Name">Trading Name</label> <input
										type="text" id="TradingName" name="TradingName"
										value="${merchant.tradingName}" readonly>
								</div>


							</div>

							<div class="row">


								<div class="input-field col s12 m6 l6 ">
									<label for="Website">Website</label> <input type="text"
										id="website" name="website" value="${merchant.website}"
										readonly>
								</div>



								<div class="input-field col s12 m6 l6 ">
									<label for="Business Reg-No">Business Reg-No</label> <input
										type="text" id="businessRegNo" name="businessRegNo"
										value="${merchant.businessRegistrationNumber}" readonly>
								</div>


							</div>

							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Business Reg-Name">Business Reg-Name</label> <input
										type="text" id="businessRegName" name="businessRegName"
										value="${merchant.businessShortName}" readonly>
								</div>





								<div class="input-field col s12 m6 l6 ">
									<label for="Business Type">Business Type</label> <input
										type="text" id="businessType" name="businessType"
										value="${merchant.businessType}" readonly>
								</div>

							</div>

							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Business Address">Business Address</label> <input
										type="text" id="businessAddress" name="businessAddress"
										value="${merchant.businessAddress2}" readonly>
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="Nature of Business">Nature of Business</label> <input
										type="text" id="businessNature" name="businessNature"
										value="${merchant.natureOfBusiness}" readonly>
								</div>



							</div>

							<div class="row">



								<div class="input-field col s12 m6 l6 ">
									<label for="Business PostalCode">Business PostalCode</label> <input
										type="text" id="businessPostCode" name="businessPostCode"
										value="${merchant.postcode}" readonly>
								</div>

								<div class="input-field col s12 m6 l6 ">
									<label for="Business Country">Business Country</label> <input
										type="text" id="businessCountry" name="businessCountry"
										value="${merchant.country}" readonly>
								</div>




							</div>

							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Owner Salutation">Owner Salutation</label> <input
										type="text" id="ownerSalutation" name="ownerSalutation"
										value="${merchant.ownerSalutation}" readonly>
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="Owner/Partner Name">Owner/Partner Name</label> <input
										type="text" id="ownerName" name="ownerName"
										value="${merchant.ownerName}" readonly>
								</div>



							</div>


							<div class="row">


								<div class="input-field col s12 m6 l6 ">
									<label for="Owner/Partner Contact">Owner/Partner
										Contact</label> <input type="text" id="ownerContact"
										name="ownerContact" value="${merchant.ownerContactNo}"
										readonly>
								</div>

								<div class="input-field col s12 m6 l6 ">
									<label for="NRIC/Passport">NRIC/Passport</label> <input
										type="text" id="ownerPassport" name="ownerPassport"
										value="${merchant.ownerPassportNo}" readonly>
								</div>


							</div>

							<div class="row">


								<div class="input-field col s12 m6 l6 ">
									<label for="Bank Name Contact">Bank Name Contact</label> <input
										type="text" id="bankName" name="bankName"
										value="${merchant.bankName}" readonly>
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="Account No">Account No</label> <input type="text"
										id="accountNo" name="accountNo" value="${merchant.bankAcc}"
										readonly>
								</div>




							</div>

							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<div class="button-class">
										<button type="button" id="demoSwal" onclick="load()"
											class="btn btn-primary blue-btn">Confirm</button>
										<!-- id="testing" -->

										<button type="button" onclick="load1()"
											class="btn btn-primary blue-btn">Cancel</button>
										<!--  id="testing1" -->
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
</style>
							</div>
						</div>
					</div>




				</div>
			</div>
		</form>


	</div>


</body>
</html>