<%@page import="com.mobiversa.payment.controller.AgentSubController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">


  <style>
	td, th { padding: 7px 8px; color:#707070;}
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
           <h3 class="text-white">  <strong>SubAgent Details</strong></h3>
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
	<td>Email</td>
	<td>${subagent.mailId}</td>
	</tr>
	<tr>
	<td>Ag Code</td>
	<td>${subagent.code}</td>
	</tr>
	<tr>
	<td>Salutation</td>
	<td>${subagent.salutation}</td>
	</tr>
	<tr>
	<td>Name</td>
	<td>${subagent.name}</td>
	</tr>
	<tr>
	<td>Address1</td>
	<td>${subagent.addr1}</td>
	</tr>
	<tr>
	<td>Address2</td>
	<td>${subagent.addr2}</td>
	</tr>
	<tr>
	<td>City</td>
	<td>${subagent.city}</td>
	</tr>
	<tr>
	<td>State</td>
	<td>${subagent.state}</td>
	</tr>
	<tr>
	<td>Post Code</td>
	<td>${subagent.postCode}</td>
	</tr>
	<tr>
	<td>Phone No</td>
	<td>${subagent.phoneNo}</td>
	</tr>
	<tr>
	<td>Ag Type</td>
	<td>${subagent.type}</td>
	</tr>

	</tbody>
	</table>
		
	</div>
	<button type="button" class="btn btn-primary blue-btn"><a href="${pageContext.request.contextPath}/<%=AgentSubController.URL_BASE%>/edit/${subagent.id}">
	Edit Details</a></button>
	</div>
<style>
						
				.select-wrapper .caret { fill: #005baa;}
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>			

		</div>

	

	</div>
	</div></div>
	
	
 </body></html>				