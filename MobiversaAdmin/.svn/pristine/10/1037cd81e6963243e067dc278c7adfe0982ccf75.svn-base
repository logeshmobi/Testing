<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.UserProfileController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>

<link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist/dist/chartist.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/c3/c3.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/style.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/dashboard1.css" rel="stylesheet">
	
	  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.date.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.time.css">
 
<%-- <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/noBack.js"></script> --%>
<script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>

<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<style>
.tooltip {
	position: relative;
	display: inline-block;
	border-bottom: 1px dotted black;
}

err: {
	container: 'tooltip'
}

.tooltip .tooltiptext {
	visibility: hidden;
	width: 120px;
	background-color: #555;
	color: #fff;
	text-align: center;
	border-radius: 6px;
	padding: 5px 0;
	position: absolute;
	z-index: 1;
	bottom: 125%;
	left: 50%;
	margin-left: -60px;
	opacity: 0;
	transition: opacity 1s;
}

.tooltip .tooltiptext::after {
	content: "";
	position: absolute;
	top: 100%;
	left: 50%;
	margin-left: -5px;
	border-width: 5px;
	border-style: solid;
	border-color: #555 transparent transparent transparent;
}

.tooltip:hover .tooltiptext {
	visibility: visible;
	opacity: 1;
}
</style>

<!-- <script lang="JavaScript"> 
	//new changes for change password start 13-01-2017 
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
	//end
</script>-->
</head>
<body>


 <div class="container-fluid">    
  <div class="row">
	<div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

		<div class="content-wrapper" >


			<div class="row" >

<h3 class="card-title">Your Merchant Account Password has been Reseted by Admin '${userName}'</h3>
				<div class="col-md-6 formContianer" >
					
					
					<div class="card">

						<div class="card-body">

							<div class="form1">
								<div class="form-group">
									<label class="control-label">Email has been sent to merchant</label> <input
										class="form-control" type="text" value="${businessName}"
										readonly>
								</div>
								<div class="form-group">
									<label class="control-label">Email ID</label> <input
										class="form-control" type="text" value="${email}"
										readonly>
								</div>
								
								






							</div>


						</div>



					</div>
					
					<button class="submitBtn"  id="buttonSub" type="submit" >
<a href="${pageContext.request.contextPath}/admmerchant/list" style="color:white;">Done</a>
</button>
		<style>
		.submitBtn {padding: 8px 20px;
    border-radius: 10px;
    background-color: #54b74a;
    color: #fff;
    margin: auto;
    display: table;}</style>			
				</div>
			</div>

		</div>
		</div></div>
		</div></div>
		</div>
		

	

	
</body>
</html>
