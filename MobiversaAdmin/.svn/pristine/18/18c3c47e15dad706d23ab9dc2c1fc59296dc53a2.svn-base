<%@page import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
<div>
 <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            MobileUser Details
          </h1>  
       </section>
       </div>
 </head>    
<div>
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">Your are Currently viewing Mobileuser ID/Terminal ID: ${mobileuser.mid.mid}</div>
		<div class="pull-right">
		<a href="<%=MerchantWebMobileController.URL_BASE%>/detail/${mobileuser.id}" class="btn btn-default">View mobileuser Details</a>
			<div class="clearfix"></div>
		</div>
	</div>
	<div class="col-xs-12 padding-10">
		<div class="pull-left h2">MobileUser Details</div>
		<div class="pull-right">
			<a href="<%=MerchantWebMobileController.URL_BASE%>/edit/${mobileUser.id}" class="btn btn-default">Edit Mobile User Details</a>
		</div>
	</div>

	<div class="col-xs-12 bar-status-history padding-top-10">
		<div>
			<div class="pull-left">
				Registration Date :
				<fmt:formatDate value="${mobileUser.activateDate}" pattern="dd MMMM yyyy" />
				<c:if test="${not empty mobileUser.terminateDate }">
	    / Termination Date : <fmt:formatDate value="${mobileUser.terminateDate}" pattern="dd MMMM yyyy" />
				</c:if>
			</div>
			<a href="#" class="btn btn-primary pull-right">Status History</a>
		</div>
	</div>
	<div class="padding-10 col-xs-12">
		<div class="h3">Terminal Details</div>
</div>
		<div class="row static-info">
			<div class="col-md-5 name">Terminal ID:</div>
			<div class="col-md-7 value">${terminal.id}</div>
		</div>
		
<div class="padding-10 col-xs-12">
		<div class="h3">Mobile User Details</div>
		
		<div class="row static-info">
			<div class="col-md-5 name">Mobile User ID:</div>
			<div class="col-md-7 value">${mobileUser.username}</div>
		</div>
		<div class="row static-info">
			<div class="col-md-5 name">Full Name:</div>
			<div class="col-md-7 value">${mobileUser.firstName}/${mobileUser.lastName}</div>
		</div>
  <div class="row static-info">
			<div class="col-md-5 name">Contact Number:</div>
			<div class="col-md-7 value">${mobileUser.contact}</div>
		</div>

<div class="row static-info">
			<div class="col-md-5 name">Email Address:</div>
			<div class="col-md-7 value">${mobileUser.email}</div>
		</div>
 
 <div class="padding-10 col-xs-12">
		<div class="h3">Authorization</div>
		<div class="pull-left">
		Able to perform credit settelement on the mobile app
		</div>
		
	 <div class="col-xs-12 bar-bottom-button">
		<div class="col-xs-12 col-sm-6 padding-20">
			<c:choose>
				<c:when test="${mobileUser.status == 'ACTIVE' }">
					<a href="<%=MerchantWebMobileController.URL_BASE%>/suspend/${mobileUser.id}" class="btn btn-primary">Suspend MobileUser</a>
				</c:when>
				<c:otherwise>
					<a href="<%=MerchantWebMobileController.URL_BASE%>/unsuspend/${mobileUser.id}" class="btn btn-primary">Un-Suspend MobileUser</a>
				</c:otherwise>
				
			</c:choose>
		</div> 

		<div class="col-xs-12 col-sm-6 padding-20">
			<a href="#" class="btn btn-primary">Reset PIN</a> <a href="#"
				class="btn btn-default">View Transactions</a> </div></div>
</div>
</div>
</div>