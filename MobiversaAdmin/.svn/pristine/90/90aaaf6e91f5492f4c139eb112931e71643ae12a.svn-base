<%@page
	import="com.mobiversa.payment.controller.MerchantWebReaderController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@page
	import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
<%@ page language="java"
	contentType="application/x-www-form-urlencoded; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html>
<head>
<meta charset="ISO-8859-1">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "");
</script>
<title>Insert title here</title>


<style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
</head>
<body>

<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Reader Details</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
    
    <div class="row">
     <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
					<form method="get">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						
						
 
  
           
		   <div class="table-responsive m-b-20 m-t-15">
		   
		   
          
           	<table id="data_list_table" class="table table-striped table-bordered">

							<tbody>
								<tr>
									<td>Device Id</td>
									<td>${reader.deviceId}</td>
									<td><input type="hidden" id="deviceId" name="deviceId" value="${reader.deviceId}" readonly="readonly"></td>
								</tr>
								<tr>
									<td>TID</td>
									<td>${reader.tid}</td>
									<td><input type="hidden" id="tid" 	name="tid" value="${reader.tid}" readonly="readonly"></td>
								</tr>
								<tr>
									<td>Mid</td>
									<td>${reader.merchantId}</td>
									<td><input type="hidden"  id="merchantId" name="merchantId"
											value="${reader.merchantId}" readonly="readonly"></td>
								</tr>
								<tr>
									<td>Device Holder Name</td>
									<td>${reader.contactName}</td>
									<td><input type="hidden" id="contactName" name="contactName"
											value="${reader.contactName}" readonly="readonly"></td>
								</tr>
								<tr>
									<td>Mobile User Name</td>
									<td>${reader.deviceName}</td>
									<td><input type="hidden" id="mobileUser" name="mobileUser"
											value="${reader.deviceName}" readonly="readonly"></td>
								</tr>
							</tbody>
						</table>
						</div>
					</form>
				
				<div class="row">
				<div class="input-field col s3 ">
							<form method="post" id="form-edit" action="${pageContext.request.contextPath}<%=MerchantWebReaderController.URL_BASE%>/edit"> 
	 
								<input type="hidden" name="deviceId" value="${reader.deviceId}" />
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
								<button type="submit" class="btn btn-primary blue-btn" value="Edit Details">Edit Details</button>
							</form>
						</div>
						<div class="input-field col s3 ">
							<form  method="post" id="form-edit" 
								action="${pageContext.request.contextPath}/<%=MerchantWebReaderController.URL_BASE%>/logindetails" > 
								<input type="hidden" name="tid" value="${reader.tid}" />
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
								<button type="submit" class="btn btn-primary blue-btn" value="Edit Details">Change Login Details</button>
							</form>
							</div> 
					</div>	
					
	<style>
							
				.select-wrapper .caret { fill: #005baa;}
							
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
		</div></div></div></div></div>

	
</body>
</html>