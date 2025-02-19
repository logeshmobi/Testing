<%@page import="com.mobiversa.payment.controller.AdminController"%>
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
</head>
<body>
<div style="float-left:30px;" class="col-xs-12 padding-10">
<!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            Bankuser Details
          </h1>  
       </section>
   </div>   
	 

<%-- <div>
<div>
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">You are Currently viewing user: ${bankUser.username }</div>
		<div class="pull-right">
			<input name="search" type="button" value="Switch Bankuser" class="btn btn-default" />
			<div class="clearfix"></div>
		</div>
	</div> --%>
	<div class="col-xs-12 padding-10">
		
		
	
			
	<div style="font-size:16px;margin-bottom:10px;margin-left:-19px;" class="padding-10 col-xs-12"> 
		<b>BankUser Details</b></div>
<div>
<table style="width:40%" class="table table-striped">
<tr>
<td style="font-size:75%;">Bank User ID:</td>
<td style="font-size:75%;" >${bankUser.firstName}&emsp;${bankUser.lastName}</td></tr>
<tr>
<td style="font-size:75%;">Role:</td>
<td style="font-size:75%;">${bankUser.role.value}</td></tr>
<tr>
<td style="font-size:75%;">Email Address:</td>
<td style="font-size:75%;">${bankUser.email}</td></tr>
<tr>
<td style="font-size:75%;">Business Contact No:</td>
<td style="font-size:75%;">${merchant.businessContactNumber}</td></tr>
<tr>
<td style="font-size:75%;">Status:</td>
<td style="font-size:75%;">${bankUser.status}</td></tr>
<tr>
</table></div>

	
			<c:choose>
				<c:when test="${bankUser.status == 'ACTIVE' }">
					<a href="<%=AdminController.URL_BASE%>/suspend/${bankUser.id}" class="btn btn-primary">Suspend </a>
				</c:when>
				<c:otherwise>
					<a href="<%=AdminController.URL_BASE%>/unsuspend/${bankUser.id}" class="btn btn-primary">Unsuspend </a>
				</c:otherwise>
			</c:choose>
			
			<a href="<%=AdminController.URL_BASE%>/edit/${bankUser.id}" class="btn btn-primary">Edit Details</a>
		</div>
	


	</html>
