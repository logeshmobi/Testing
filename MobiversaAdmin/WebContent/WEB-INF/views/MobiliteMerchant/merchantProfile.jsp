<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page	import="com.mobiversa.payment.controller.MobiliteWebController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">

 <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>

</head>


<script lang="JavaScript">

function loadSelectData() {
	//alert("fcancel data");
	
	
	 document.location.href = "${pageContext.request.contextPath}/mobilite/merchProf/changePassWordbyMerch";
	form.submit;
}

</script>

<body>


	





	<form method="post">
<div class="container-fluid">  
  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Change Password </strong></h3>
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
               <label for="UserName">User Name</label>
								
				<input type="text"  id="UserName"
					 value="${merchant.username}" readonly="readonly"/>
				</div>
				<div class="input-field col s12 m6 l6 ">
               <label for="firstName">First Name</label>
								
				<input type="text"  id=firstName
					 value="${merchant.firstName}" readonly="readonly"/>
				</div>
				<div class="input-field col s12 m6 l6 ">
               <label for="lastName">Last Name</label>
								
				<input type="text"  id="lastName"
					 value="${merchant.lastName}" readonly="readonly"/>
				</div>
				<div class="input-field col s12 m6 l6 ">
               <label for="businessName">Business Name</label>
								
				<input type="text"  id="businessName"
					 value="${merchant.businessName}" readonly="readonly"/>
				</div>
				<div class="input-field col s12 m6 l6 ">
               <label for="businessAddress1">Address</label>
								
				<input type="text"  id="businessAddress1"
					 value="${merchant.businessAddress1}" readonly="readonly"/>
				</div>
				<div class="input-field col s12 m6 l6 ">
               <label for="businessContactNumber">Contact Number</label>
								
				<input type="text"  id="businessContactNumber"
					 value="${merchant.businessContactNumber}" readonly="readonly"/>
				</div>
				<div class="input-field col s12 m6 l6 ">
               <label for="businessAddress1">Email</label>
								
				<input type="text"  id="email"
					 value="${merchant.email}" readonly="readonly"/>
				</div>
				</div>
            <div class="row">		
			<div class="input-field col s12 m6 l6 ">	
			<button class="btn btn-primary blue-btn" type="button"
				onclick="loadSelectData()">Change PassWord</button>
				</div></div>
				

		</div>
		</div>
			</div>
</div>
</div>







	</form>


