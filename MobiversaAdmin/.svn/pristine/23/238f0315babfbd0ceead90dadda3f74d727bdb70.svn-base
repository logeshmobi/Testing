<%@page import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebTransactionController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
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
</head>
<body>
<div style="float-left:30px;" class="col-xs-12 padding-10">
<!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            Mobile User Details
          </h1>  
       </section>
   </div>   

	<div style="font-size:16px;margin-bottom:10px;margin-left:-19px;"class="padding-10 col-xs-12">
         <b>Terminal Details</b>
      </div>    	
<div>	
<table style="width:30%"class="table table-striped">
<tbody>
<tr >
<td style="font-size:75%;">MobileUser ID</td>
<td style="font-size:75%;">${mobileUser.username}</td></tr>
<tr>
<td style="font-size:75%;">Terminal ID</td>
<td style="font-size:75%;">${tid.tid}</td></tr>
</tbody>
</table>
</div>

	<div style="font-size:16px;margin-bottom:10px;margin-left:-19px;" class="padding-10 col-xs-12"> 
		<b>Mobile User Details</b></div>
<div>
<table style="width:30%" class="table table-striped">
<tr>
<td style="font-size:75%;">MobileUser ID</td>
<td style="font-size:75%;" >${mobileUser.id}</td></tr>
<tr>
<td style="font-size:75%;">Full Name</td>
<td style="font-size:75%;">${mobileUser.firstName}${mobileUser.lastName}</td></tr>
<tr>
<td style="font-size:75%;">Contact No</td>
<td style="font-size:75%;">$${mobileUser.contact}</td></tr>
<tr>
<td style="font-size:75%;">Email</td>
<td style="font-size:75%;">${mobileUser.email}</td></tr>
<tr>
</table></div>
<div style="margin-left:-40px;margin-top:10px;"class="col-xs-12 padding-10">
<div class="col-xs-9 bar-bottom-button">
		<div class="col-xs-2 padding-10">
			<c:choose>
				<c:when test="${mobileUser.status == 'ACTIVE' }">
					<a href="<%=MerchantWebMobileController.URL_BASE%>/suspend/${mobileUser.id}" class="btn btn-primary">Suspend</a>
				</c:when>
				<c:otherwise>
					<a href="<%=MerchantWebMobileController.URL_BASE%>/unsuspend/${mobileUser.id}" class="btn btn-primary">Unsuspend</a>
				</c:otherwise>
				
			</c:choose>
		</div>
		<div class="col-xs-2 padding-10">
		
			<a href="<%=MerchantWebMobileController.URL_BASE%>/edit/${mobileUser.id}" class="btn btn-primary">Edit Details</a>
		
	</div> 
	
	<div class="col-xs-2  padding-top-10">
	<a href="#" class="btn btn-primary">Reset PIN</a></div>
	   </div>
	   </div>
	   </body>
	   </html>