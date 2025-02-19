<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@page import="com.mobiversa.common.bo.MID"%>

 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%> 
<html lang="en-US">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mobile User Summary</title>    
    
  
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
           <h3 class="text-white">  <strong> Mobile User  </strong>   &nbsp;&nbsp;<small>  Mobile User Summary</small> </h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>

	<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

		   <div class="table-responsive m-b-20 m-t-15">
            <table class="">
              	<tbody>
						
						<tr>
						<td>Activation Date</td>
						<td>${mobileUser.activationDate}</td>
						</tr>
						<tr>
						<td>Mobile UserName</td>
						<td>${mobileUser.mobileUserName}</td>
						</tr>
						<tr>
						<td>Status</td>
						<td>${mobileUser.status}</td>
						</tr>
						<tr>
						<td>Renewal Date</td>
						<td>${mobileUser.renewalDate}</td>
						</tr>
						
						<tr>
						<td>Expiry Date</td>
						<td>${mobileUser.expiryDate}</td>
						</tr>

						</tbody>
            </table>
          </div>
          			<form  method="post" id="form-edit" 
					action="${pageContext.request.contextPath}<%=MobileUserController.URL_BASE%>/edit" > 
	 
	       <input type="hidden" name="id" value="${mobileUser.tid}" />
	      
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
			
				<center><button type="submit"class="btn btn-primary blue-btn" value="Edit Details">Edit Details</button></center>
				</form>
			<style>
						
				.select-wrapper .caret { fill: #005baa;}
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>			
				
				 
        </div>
      </div>
    </div>
    </div>
	
	 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
     
  </div>



</body> 
</html>