<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<html lang="en-US">
<head>

<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css"
	rel="stylesheet" />
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>


<script type="text/javascript">
	window.history.replaceState({}, document.title, "/MobiversaAdmin/"
			+ "admin/");
</script>

<script type="text/javascript">
	function load() {

		//$('#testing').click(function(){

		swal(
				{
					title : "Are you sure? you want to add this Merchant Details",
					text : "it will be added..!",
					type : "warning",
					showCancelButton : true,
					confirmButtonText : "Yes, add it!",
					cancelButtonText : "No, cancel!",
					closeOnConfirm : false,
					closeOnCancel : false,
				/*  confirmButtonClass: 'btn btn-success',
				cancelButtonClass: 'btn btn-danger', */

				},
				function(isConfirm) {
					if (isConfirm) {

						//swal("Added!", "Your Merchant Promotion details added","success");
						$("#form-add").submit();

					} else {
						// swal("Cancelled", "Your Merchant Promotion details not added", "error"); 
						var url = "${pageContext.request.contextPath}/admin/addMerchant";
						$(location).attr('href', url);
						//return true;
					}
				});
		// });

	}
</script>
<script type="text/javascript">
	function load1() {

		//$('#testing').click(function(){

		swal(
				{
					title : "Are you sure? Do you want to Cancel to add Merchant Details",
					text : "Registration of Merchant Cancelled..!",
					type : "warning",
					showCancelButton : true,
					confirmButtonText : "Yes, Don't Register!",
					cancelButtonText : "No, Do Register!",
					closeOnConfirm : false,
					closeOnCancel : true,
				/*  confirmButtonClass: 'btn btn-success',
				cancelButtonClass: 'btn btn-danger', */

				},
				function(isConfirm) {
					if (isConfirm) {

						swal("Added!", "Your Merchant Promotion details added","success");
						/* var url = "${pageContext.request.contextPath}/admin/addMerchant";
						$(location).attr('href', url); */

					}
				});
		// });

	}
</script>



</head>


<body >

	<form:form action="admin/merchantDetailsReviewAndConfirm" method="post"
		commandName="merchant" id="form-add">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
		<input type="hidden" name="id" value="${submerchant.id}" />
		<div class="container-fluid">
			<div class="row">
				<div class="col s12">
					<div class="card blue-bg text-white">
						<div class="card-content">
							<div class="d-flex align-items-center">
								<h3 class="text-white">
									<strong> Add Merchant </strong>
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
							<div class="d-flex align-items-center">
								<h5>Merchant Details*</h5>
							</div>


							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="first_name">Title</label> <input type="text"
										id="salutation" placeholder="salutation" name="salutation"
										value="${submerchant.salutation}" disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="first_name">Contact Person Name</label> <input
										type="text" id="name" placeholder="name" name="name"
										value="${submerchant.name}" disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="name">Contact Number</label> <input type="text"
										id="contactNo" placeholder="contactNo" name="contactNo"
										value="${submerchant.contactNo}" disabled="disabled">
								</div>
							</div>

							<div class="row">

								<div class="input-field col s12 m4 l4">
									<label for="name">Contact Person IC</label> <input type="text"
										id="contactIc" placeholder="contactIc" name="contactIc"
										value="${submerchant.contactIc}" disabled="disabled">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>


			<%-- <div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">
							<div class="d-flex align-items-center">
								<h5>MID Details*</h5>
							</div>
							<div class="row">
								<div class="input-field col s12 m4 l4">
									<input type="text" id="mid" placeholder="mid" name="mid"
										value="${submerchant.mid}" disabled="disabled"> <label
										for="mid">MERCHANT MID</label>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div> --%>

			<div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">
							<div class="d-flex align-items-center">
								<h5>Business Details*</h5>
							</div>


							<div class="row">

								<div class="input-field col s12 m4 l4">
									<label for="Trading Name">Trading Name</label> <input
										type="text" id="tradingName" placeholder="tradingName"
										name="tradingName" value="${submerchant.tradingName}"
										disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="Email">Email</label> <input type="text" id="email"
										placeholder="email" name="email" value="${submerchant.email}"
										disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="Website">Website</label> <input type="text"
										id="website" placeholder="website" name="website"
										value="${submerchant.website}" disabled="disabled">
								</div>

							</div>

							<div class="row">
								<div class="input-field col s12 m4 l4">
									<label for="name">Business Reg-No</label> <input type="text"
										id="businessRegNo" placeholder="businessRegNo"
										name="businessRegNo" value="${submerchant.businessRegNo}"
										disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="name">Business Reg-Name</label> <input type="text"
										id="businessName" placeholder="businessName"
										name="businessName" value="${submerchant.businessName}"
										disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="Business">Business Type</label> <input type="text"
										id="businessType" placeholder="businessType"
										name="businessType" value="${submerchant.businessType}"
										disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="Nature">Nature of Business</label> <input
										type="text" id="natureOfBusiness"
										placeholder="natureOfBusiness" name="natureOfBusiness"
										value="${submerchant.natureOfBusiness}" disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="name">Business Address</label> <input type="text"
										id="businessAddress" placeholder="businessAddress"
										name="businessAddress" value="${submerchant.businessAddress}"
										disabled="disabled">
								</div>
							</div>

							<div class="row">

								<div class="input-field col s12 m4 l4">
									<label for="first_name">Business City</label> <input
										type="text" id="businessCity" placeholder="businessCity"
										name="businessCity" value="${submerchant.businessCity}"
										disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="name">Business State</label> <input type="text"
										id="businessState" placeholder="businessState"
										name="businessState" value="${submerchant.businessState}"
										disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="name">Business PostalCode</label> <input
										type="text" id="businessPostCode"
										placeholder="businessPostCode" name="businessPostCode"
										value="${submerchant.businessPostCode}" disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="first_name">Business Country</label> <input
										type="text" id="businessCountry" placeholder="businessCountry"
										name="businessCountry" value="${submerchant.businessCountry}"
										disabled="disabled">
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
							<div class="d-flex align-items-center">
								<h5>Owner Details*</h5>
							</div>


							<div class="row">


								<div class="input-field col s12 m4 l4">
									<label>Owner Salutation</label> <input type="text"
										id="ownerSalutation1" placeholder="ownerSalutation1"
										name="ownerSalutation1" value="${submerchant.ownerSalutation1}"
										disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="OwnerName1">Owner/Partner Name</label> <input
										type="text" id="ownerName1" placeholder="ownerName1"
										name="ownerName1" value="${submerchant.ownerName1}"
										disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="ContactNo1">Owner/Partner Contact</label> <input
										type="text" id="ownerContactNo1" placeholder="ownerContactNo1"
										name="ownerContactNo1" value="${submerchant.ownerContactNo1}"
										disabled="disabled">
								</div>
								<div class="input-field col s12 m4 l4">
									<label for="NRIC1">NRIC/ Passport</label> <input type="text"
										id="PassportNo1" placeholder="passportNo1" name="passportNo1"
										value="${submerchant.passportNo1}" disabled="disabled">
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
							<div class="d-flex align-items-center">
								<h5>Account Details</h5>
							</div>


							<div class="row">
								<div class="input-field col s12 m4 l4">
									<!-- <input id="Email" type="text" class="validate"> -->
									<label for="Bank Name">Bank Name</label> <input type="text"
										id="bankName" placeholder="bankName" name="bankName"
										value="${submerchant.bankName}" disabled="disabled">
								</div>

								<div class="input-field col s12 m4 l4">
									<!-- <input id="Account No" type="text" class="validate"> -->
									<label for="Account No">Account No</label> <input type="text"
										id="bankAccNo" placeholder="bankAccNo" name="bankAccNo"
										value="${submerchant.bankAccNo}" disabled="disabled">
								</div>

							</div>

							<div class="row">
								<div class="col s12 m4 l4"></div>
								<div class="col s12 m4 l4">
									<div class="button-class" style="float: left;">
										<button type="button" id="testing" onclick=" return load()"
											class="btn btn-primary blue-btn">Confirm</button>
										<button type="button" onclick="load1()"
											class="export-btn waves-effect waves-light btn btn-round indigo">Cancel</button>
									</div>
									<div class="col s12 m4 l4"></div>
								</div>

							</div>


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
</style>


		</div>
	</form:form>



</body>
</html>