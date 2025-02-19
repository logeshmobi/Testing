<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.AgentProfileController"%>
<%@page import="com.mobiversa.common.bo.Agent"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!--  <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" /> -->
<script
	src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>

<script lang="JavaScript">
	function checkPassword() {
		// alert("test data");
		var error = "";

		var e = document.getElementById("newPassword").value;

		var e1 = document.getElementById("confirmPassword").value;

		var e2 = document.getElementById("password").value;

		/*  var re = /^(?=.*[0-9][a-z][A-Z][!@#$%^&*]){8,15}$/; */
		// var re = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,15}$/;
		var re = /^(?=.*[!@#$%^&*][a-zA-Z0-9])[a-zA-Z0-9!@#$%^&*]{8,15}$/;
		//alert("test data");

		if (e2 == null || e2 == '') {
			alert("Please enter Old Password");
			return false;
		} else if (e == null || e == '') {
			alert("Please enter New Password");
			return false;

		} else if ((e.length < 8))

		{
			//alert("test data 11111");
			error = "The password is the minimum eight digits. \n";
			alert("The password is the wrong length:" + error);
			return false;

		}

		else if ((e.length > 15))

		{
			//alert("test data 11111");
			error = "The password is the maximum 15 digits. \n";
			alert("The password is the wrong length:" + error);
			return false;

		} else if (!re.test(e)) {
			alert("Error:  password   must contain alpha numeric with special Characters ");
			//form1.e.focus();

			return false;

		} else if (e1 == null || e1 == '') {
			alert("Please enter Confirm Password");
			return false;
		} else if (e1 == e2) {
			alert("Old Password and New Password are same");
			return false;
		} else if (e1 != e) {
			alert("New Password and Confirm password Not Match");
			return false;
		}

		else {

			return true;
		}
	}
</script>
</head>
<body>






	<div class="pageWrap">

		<div class="pageContent">
			<div class="container">
				<div class="boxHeader pageBoxHeader clearfix">
					<div class="pull-left">
						<h1 class="pageTitle">

							<a href="#" title="#"> Change Password</a>
						</h1>
						<!-- style="color:#0989af;" -->


					</div>
				</div>
			</div>
		</div>
	</div>





	<form method="post" name="form1" data-parsley-validate
		class="form-horizontal"
		action="<c:url value='/agentProfile/confirmPassword'/>"
		onsubmit="return checkPassword();">
		<!--  onsubmit="return checkPassword();" -->



		<div class="row  col-xs-12 col-sm-5">
			<div class="box rte box-without-bottom-padding">

				<h2 class="boxHeadline">Agent Details</h2>

				<div class="tableWrap table-responsive">
					<table class="table table-striped">
						<tbody>
							<tr>
								<td>UserName</td>
								<td>${agentUserName}</td>
							</tr>
							<tr>
								<td>Old Password</td>
								<td><input type="password" class="form-control"
									id="password" placeholder="OldPassword" name="password" /></td>
							</tr>
							<tr>
								<td>New Password</td>
								<td><input type="password" class="form-control"
									id="newPassword" placeholder="NewPassword" name="newPassword"
									title="Password must contain at least 8 characters, including UPPER/lowercase and numbers /^(?=.*[0-9][a-z][A-Z] [!@#$%^&*])" /></td>
								<!--  <i class="fa fa-lock"></i></td>  required data-parsley-error-message="Wrong passwordformat"-->
							</tr>
							<tr>
								<td>Confirm Password</td>
								<td><input type="password" class="form-control"
									placeholder="ConfirmPassword" name="confirmPassword"
									id="confirmPassword"
									title="Password must contain at least 8 characters, including UPPER/lowercase and numbers /^(?=.*[0-9][a-z][A-Z] [!@#$%^&*])" /></td>
								<!--  <i class="fa fa-lock"></i></td> required data-parsley-error-message="Wrong password format" -->
							</tr>

						</tbody>

					</table>

				</div>
			</div>

			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />


			<div class="form-group">
				<button type="submit" name="submit" class="btn btn-orange submit">Submit</button>
				<!-- onclick ="CheckPassword1(document.form1.newPassword)" -->
			</div>


			<span class="tooltiptext" style="color: #ff4000;">Password
				should contain at least 8 characters</span> <br>
			<br> <span class="tooltiptext" style="color: #ff4000;">Password
				should contain at least one UPPER/Lowercase [a-zA-Z]</span> <br>
			<br> <span class="tooltiptext" style="color: #ff4000;">Password
				should contain at least one numeric and special characters
				[0-9!@#$%^&*]</span> <br>
			<br>


		</div>






	</form>
</body>
</html>

