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


<script lang="JavaScript">
	function validatefinalsubmit() {
		document.getElementById("form1").submit();
		return true;
	}

	function loadCancelData() {
		//alert("fcancel data");
		document.location.href = "${pageContext.request.contextPath}/admin/submerchantSum";
		form.submit;
	}
</script>


</head>

<body>
	<form
		action="${pageContext.request.contextPath}<%=AdminController.URL_BASE%>/editsubmerchantuser"
		method="post" id="form1" name="form1" commandName="merchant">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> <input type="hidden" name="id"
			value="${salutation}" />
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



			<div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">


							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Activation Date">Activation Date</label> <input
										type="text" name="activationDate" readonly="readonly"
										value="${ADate}" />
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="Business Name">BusinessName</label> <input
										type="hidden" id="business" name="business1"
										value="${merchant.businessName}" /> <input type="text"
										id="businessName" name="businessName" class="form-control"
										value="${merchant.businessName}" />
								</div>

							</div>




							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Email">Email</label> <input type="hidden"
										id="email" name="email1" value="${merchant.email}" /> <input
										type="text" id="email" name="email" class="form-control"
										value="${merchant.email}" />
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="City">City</label> <input type="hidden" id="city"
										name="city1" value="${merchant.city}" /> <input type="text"
										id="city" name="city" class="form-control"
										value="${merchant.city}" />
								</div>



							</div>

							<div class="row">


								<div class="input-field col s12 m6 l6 ">
									<label for="State">State</label> <input type="hidden"
										id="state" name="state1" value="${merchant.state}" /> <input
										type="text" id="state" name="state" class="form-control"
										value="${merchant.state}" />
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="MID">MID</label> <input type="text" name="mid"
										readonly="readonly" value="${Submid}" />
								</div>

							</div>

							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<select name="salutation">
										<option value="Miss"
											${merchant.salutation == "Miss" ? 'selected="selected"' : ''}>Miss</option>
										<option value="Mr"
											${merchant.salutation == "Mr" ? 'selected="selected"' : ''}>Mr</option>
										<option value="Mrs"
											${merchant.salutation == "Mrs" ? 'selected="selected"' : ''}>Mrs</option>
									</select> <label for="name">Title</label>
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="Contact Person Name">Contact Person Name</label> <input
										type="hidden" id="cpName1" name="cpName1"
										value="${merchant.contactPersonName}" /> <input type="text"
										id="cpName" name="cpName" class="form-control"
										value="${merchant.contactPersonName}" />
								</div>

							</div>

							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Contact Person Number">Contact Person
										Number</label> <input type="hidden" id="cpNo1" name="cpNo1"
										value="${merchant.contactPersonPhoneNo}" /> <input
										type="text" id="cpNo" name="cpNo" class="form-control"
										value="${merchant.contactPersonPhoneNo}" />
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="Trading Name">Trading Name</label> <input
										type="hidden" id="TradingName1" name="TradingName1"
										value="${merchant.tradingName}" /> <input type="text"
										id="TradingName" name="TradingName" class="form-control"
										value="${merchant.tradingName}" />
								</div>

							</div>

							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Website">Website</label> <input type="hidden"
										id="website1" name="website1" value="${merchant.website}" />
									<input type="text" id="website" name="website"
										class="form-control" value="${merchant.website}" />
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="Business Reg-No">Business Reg-No</label> <input
										type="hidden" id="businessRegNo1" name="businessRegNo1"
										value="${merchant.businessRegistrationNumber}" /> <input
										type="text" id="businessRegNo" name="businessRegNo"
										class="form-control"
										value="${merchant.businessRegistrationNumber}" />
								</div>

							</div>

							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Business Reg-Name">Business Reg-Name</label> <input
										type="hidden" id="businessRegName1" name="businessRegName1"
										value="${merchant.businessShortName}" /> <input type="text"
										id="businessRegName" name="businessRegName"
										class="form-control" value="${merchant.businessShortName}" />
								</div>


								<div class="input-field col s12 m6 l6 ">
									<select class="form-control" name="businessType">
										<option value="Sole Proprietor"
											${merchant.businessType == "Sole Proprietor" ? 'selected="selected"' : ''}>Sole Proprietor</option>
										<option value="Partnership"
											${merchant.businessType == "Partnership" ? 'selected="selected"' : ''}>Partnership</option>
										<option value="Private Limited"
											${merchant.businessType == "Private Limited" ? 'selected="selected"' : ''}>Private Limited</option>
										<option value="Limited"
											${merchant.businessType == "Limited" ? 'selected="selected"' : ''}>Limited</option>
										<option value="Association"
											${merchant.businessType == "Association" ? 'selected="selected"' : ''}>Association</option>
										<option value="LLP"
											${merchant.businessType == "LLP" ? 'selected="selected"' : ''}>LLP</option>
									</select> <label for="name">Business Type</label>
								</div>

							</div>

							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Business Reg-Name">Business Address</label>
									<textarea id="businessAddress" name="businessAddress"
										class="form-control">${merchant.businessAddress2}</textarea>

								</div>


								<div class="input-field col s12 m6 l6 ">
									<select class="form-control" name="businessNature">
										<c:forEach items="${natureOfBusinessList}" var="businessNature">
											<option value="${businessNature}"  ${merchant.natureOfBusiness == businessNature ? 'selected="selected"' : ''}>${businessNature}</option>
										</c:forEach>

									</select><label for="name">Nature of Business</label>
								</div>


							</div>

							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Business PostalCode">Business PostalCode</label> <input
										type="hidden" id="businessPostCode1" name="businessPostCode1"
										value="${merchant.postcode}" /> <input type="text"
										id="businessPostCode" name="businessPostCode"
										class="form-control" value="${merchant.postcode}" />
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="Country">Business Country</label> <input
										type="hidden" id="businessCountry1" name="businessCountry1"
										value="${merchant.country}" /> <input type="text"
										id="businessCountry" name="businessCountry"
										class="form-control" value="${merchant.country}" />
								</div>


							</div>

							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<select class="form-control" name="ownerSalutation">
										<option value="Miss"
											${merchant.ownerSalutation == "Miss" ? 'selected="selected"' : ''}>Miss</option>
										<option value="Mr"
											${merchant.ownerSalutation == "Mr" ? 'selected="selected"' : ''}>Mr</option>
										<option value="Mrs"
											${merchant.ownerSalutation == "Mrs" ? 'selected="selected"' : ''}>Mrs</option>
									</select> <label for="name">Owner Salutation</label>
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="Owner/Partner Name">Owner/Partner Name</label> <input
										type="hidden" id="ownerName1" name="ownerName1"
										value="${merchant.ownerName}" /> <input type="text"
										id="ownerName" name="ownerName" class="form-control"
										value="${merchant.ownerName}" />
								</div>


							</div>


							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Owner/Partner Contact">Owner/Partner
										Contact</label> <input type="hidden" id="ownerContact1"
										name="ownerContact1" value="${merchant.ownerContactNo}" /> <input
										type="text" id="ownerContact" name="ownerContact"
										class="form-control" value="${merchant.ownerContactNo}" />
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="NRIC/Passport">NRIC/Passport</label> <input
										type="hidden" id="ownerPassport1" name="ownerPassport1"
										value="${merchant.ownerPassportNo}" /> <input type="text"
										id="ownerPassport" name="ownerPassport" class="form-control"
										value="${merchant.ownerPassportNo}" />
								</div>


							</div>

							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<label for="Bank Name">Bank Name Contact</label> <input
										type="hidden" id="bankName1" name="bankName1"
										value="${merchant.bankName}" /> <input type="text"
										id="bankName" name="bankName" class="form-control"
										value="${merchant.bankName}" />
								</div>


								<div class="input-field col s12 m6 l6 ">
									<label for="Account No">Account No</label> <input type="hidden"
										id="accountNo1" name="accountNo1" value="${merchant.bankAcc}" />
									<input type="text" id="accountNo" name="accountNo"
										class="form-control" value="${merchant.bankAcc}" />
								</div>


							</div>


							<div class="row">

								<div class="input-field col s12 m6 l6 ">
									<div class="button-class">
										<button type="submit" class="btn btn-primary blue-btn"
											onclick="return validatefinalsubmit();">Submit</button>

										<button type="button" class="btn btn-primary blue-btn"
											onclick="return loadCancelData()">Cancel</button>

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
					</div>

				</div>
			</div>
		</div>
	</form>
</body>
</html>