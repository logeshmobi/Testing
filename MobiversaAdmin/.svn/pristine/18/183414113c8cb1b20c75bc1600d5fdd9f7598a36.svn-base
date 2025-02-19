<%@page import="com.mobiversa.payment.controller.ReaderController"%>
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
<%-- <div>
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">Your are Currently viewing MID:${terminalDetails.merchantId}</div>
		<div class="pull-right">
			<input name="search" type="button" value="View Merchant Details" class="btn btn-primary" />
			<div class="clearfix"></div>
		</div>
	</div> --%>
	<div class="col-xs-12 padding-10">
		<div class="pull-left h2">Reader Details</div>
	</div>

	<div style="font-size:16px;margin-bottom:10px;margin-left:-19px;"class="padding-10 col-xs-12">
         <b>Reader Details</b>
      </div> 
      
      <div>	
<table style="width:30%"class="table table-striped">
<tbody>
<tr>
<td style="font-size:75%;">Reader Serial No:</td>
<td style="font-size:75%;">${terminalDetails.deviceId}</td></tr>
<tr>
<td style="font-size:75%;">Status:</td>
<td style="font-size:75%;">${terminalDetails.activeStatus}</td></tr>

</tbody>
</table>
</div>
		<div style="margin-left:-40px;margin-top:10px;"class="col-xs-12 padding-10">
<div class="col-xs-9 bar-bottom-button">
		<div class="col-xs-2 padding-10">
			<c:choose>
				<c:when test="${mobileUser.status == 'ACTIVE' }">
				<a href="<%=ReaderController.URL_BASE%>/suspend/${terminalDetails.deviceId}" class="btn btn-primary">Suspend</a>
				
					
				</c:when>
				<c:otherwise>
					<a href="<%=ReaderController.URL_BASE%>/unsuspend/${terminalDetails.deviceId}" class="btn btn-primary">Un-Suspend</a>
				</c:otherwise>
				</c:choose>
        
        </div>
        </div>
        </div>
        </body>
        </html>