<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
 <%@page import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
 <%@page import="com.mobiversa.common.bo.Merchant"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
 <script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
</head>
<body class="">
<div>
 <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            Add A MobileUser
          </h1>  
       </section>
       </div>
       <div>
       <section class="content">
       <div>


<form:form method="post" commandName="mobileUser">
 
<div>
<div class="h3"><b>MobileUser Details</b></div><br>
</div>
<div class="form-horizontal" > 
<div class="form-group has-feedback"> 
<label class="col-md-2 control-label">MobileUser ID:</label>
<div class="col-md-1">
<form:input type="text"  path="username"/>
</div>
</div>

<div class="form-group has-feedback"> 
<!-- <div class="input-group"> -->
<label class="col-md-2 control-label">First Name:</label>
<div class="col-md-1">
<form:input type="text"  path="firstName"/>
</div>
</div>
<div class="form-group"> 
<!-- <div class="input-group"> -->
<label class="col-md-2 control-label">Last Name:</label>
<div class="col-md-1">
<form:input type="text"   path="lastName"/>
</div>
</div>

<div class="form-group"> 
<!-- <div class="input-group"> -->
<label class="col-md-2 control-label">Contact Number:</label>
<div class="col-md-1">
<form:input type="text"  path="contact"/>
</div>
</div>
<div class="form-group"> 
<!-- <div class="input-group"> -->
<label class="col-md-2 control-label">Password:</label>
<div class="col-md-1">
<form:input type="password"   path="password"/>
</div>
</div>
<div class="form-group"> 
<!-- <div class="input-group"> -->
<label class="col-md-2 control-label">Email Address:</label>
<div class="col-md-1">
<form:input type="text"   path="email"/>
</div>
</div>
</div>

 <div class="form-group"> 
<div>
 <input style="float:left:30px;"type="submit" class="submitBtn" value="submit"></div>
  </div> 
</form:form>

</section>
</div>
</body>
</html>