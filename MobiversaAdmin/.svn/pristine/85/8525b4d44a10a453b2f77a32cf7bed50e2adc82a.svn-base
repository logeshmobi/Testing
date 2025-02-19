<%@page import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<!--  <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" /> -->
 <script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
</head>
<body>

<div class="pageWrap">

		<!-- Page content -->
		
		
		<div class="pageContent ">
			<div class="container">
				<div class="boxHeader pageBoxHeader clearfix">
					<div class="pull-left">
						<h1 class="pageTitle">
							<a href="#" title="#"> Edit MobileUser Details </a>
						</h1>

</div></div></div></div>
</div>
























<%-- <div style="float-left:30px;" class="col-xs-12 padding-10">
<!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
           Edit MobileUser Details
          </h1>  
       </section>
   </div>   
 --%>

	<!--edit a merchant block ends here  -->

<form action="<%=MerchantWebMobileController.URL_BASE%>/editMobileuser" method="post" >
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <input type="hidden" name="id" value="${mobileUser.id}" />

<!-- merchant details block start here -->
<div><b>Terminal Details</b></div><br>
<div class="form-horizontal" > 
		<div class="form-group"> 
<!-- <div class="input-group"> -->
<label class="col-md-2 control-label">Terminal ID:</label>
<div class="col-md-4">
<input type="text"  value="${tid.tid}"/>
</div>
</div>
</div>
<!--merchant details block end here  -->

<!-- business details block start here -->
<div><b>MobileUser Details</b></div><br>
<div class="form-horizontal" > 
<div class="form-group">
<label class="col-md-2 control-label">MobileUser ID:</label>
<div class="col-md-4">
<input name="username" type="text"  value="${mobileUser.username}" />
</div></div>
 
<div class="form-group">  
<label class="col-md-2 control-label">First Name:</label>
<div class="col-md-4">
 <input name="firstName"  value="${mobileUser.firstName}" />
 </div></div>
 
 <div class="form-group">  
<label class="col-md-2 control-label">Last Name:</label>
<div class="col-md-4">
 <input name="lastName" type="text"  value="${mobileUser.lastName}" /></div></div>
 

<div class="form-group">
<label class="col-md-2 control-label">Contact No:</label>
<div class="col-md-4">
 <input name="contact" type="text"   value="${mobileUser.contact}" />
</div></div>
<div class="form-group">
<label class="col-md-2 control-label">Email Address::</label>
<div class="col-md-4">
<input name="email" type="text"  value= "${mobileUser.email }"/>
</div></div>


	
	<div class="form-group col-xs-6"> 
<div>
 <input type="submit" class="submitBtn" value="submit"></div>
</div>
	
 
  </div> 
 
 <!-- transaction limit end here  -->
</form>

<!-- </body> -->
</html>







 