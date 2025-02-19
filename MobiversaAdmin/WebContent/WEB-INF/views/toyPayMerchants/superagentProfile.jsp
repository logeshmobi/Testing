<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.SuperAgentController"%>
 <%@page import="com.mobiversa.common.bo.Agent"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
  <!-- <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport"> -->
<!--  <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" /> -->
  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 
 <script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
 
 <style>



​
	.form-control{
		height:auto;
		margin-bottom: 0;
		}
		

</style>
</head>


<script lang="JavaScript">

function loadSelectData() {
	//alert("fcancel data");
	
	
	 document.location.href = "${pageContext.request.contextPath}/superagent/superagentProfportal/toypaychangePassWord";
	form.submit;
}

</script>
<body >
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

 <form method="post" >
   <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
 <div class="row">									
               <div class="input-field col s12 m6 l6 ">
               <label for="UserName">User Name</label>
								
				<input type="text"  id="UserName"
					 value="${agent.username}" readonly="readonly"/>
				</div>
				<div class="input-field col s12 m6 l6 ">
               <label for="firstName">First Name</label>
								
				<input type="text"  id=firstName
					 value="${agent.firstName}" readonly="readonly"/>
				</div>
				<div class="input-field col s12 m6 l6 ">
               <label for="lastName">Last Name</label>
								
				<input type="text"  id="lastName"
					 value="${agent.lastName}" readonly="readonly"/>
				</div>
				
				<div class="input-field col s12 m6 l6 ">
               <label for="businessAddress1">Address</label>
								
				<input type="text"  id="businessAddress1"
					 value="${agent.addr1}" readonly="readonly"/>
				</div>
				<div class="input-field col s12 m6 l6 ">
               <label for="businessContactNumber">Contact Number</label>
								
				<input type="text"  id="businessContactNumber"
					 value="${agent.phoneNo}" readonly="readonly"/>
				</div>
				<div class="input-field col s12 m6 l6 ">
               <label for="businessAddress1">Email</label>
								
				<input type="text"  id="email"
					 value="${agent.mailId}" readonly="readonly"/>
				</div>
				</div>
				
				<div class="row">		
			<div class="input-field col s12 m6 l6 ">	
			<button class="btn btn-primary blue-btn" type="button"
				onclick="loadSelectData()">Change Password</button>
				</div></div></div></div></div></div>
             
</form>



  </div>
          
	
</body>
</html>