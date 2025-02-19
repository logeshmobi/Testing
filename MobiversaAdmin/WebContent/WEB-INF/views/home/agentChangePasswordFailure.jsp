<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.AgentProfileController"%>
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
 
</head>
<body class="">

 <div class="container-fluid">  

 <div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Change Password </strong></h3>
          </div>
          
          <div class="d-flex align-items-center">
         <h3 style="color:#FF0000 !important">Password Update Failed</h3>
			
		</div>
          
          
        </div>
      </div>
    </div>
    </div>
	


<form method="post" action="<c:url value='/agentProfdetails/confirmPasswordbyagent' />">
 
<div class="row">
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

	<div class="row"> 		
                       
					<div class="input-field col s12 ">
					<input type="text" value ="${agentUserName}" readonly> 
						<label for="name">User Name</label>
                    
                  </div>
                  
                  <div class="input-field col s12 ">
						 <input type="password" placeholder="Old Password" name="password"  id="password">
						<label for="name">Old Password</label>
					</div>
                  
                  
                  <div class="input-field col s12 ">
						<input  type="password" 
                    id="newPassword" name="newPassword" placeholder="NewPassword">
						<label for="name">New Password</label>
					</div>
					
					<div class="input-field col s12 ">
						<input  
                   type="password" placeholder="ConfirmPassword"  placeholder="ConfirmPassword" name=""  id="newPassword" >
                
						<label for="name">Confirm Password</label>
					</div>
                  
                  </div>
							
							
				 
			<input type="hidden" name="${_csrf.parameterName}"	value="${_csrf.token}" />
				 </div>		
			</div>
					 
				 
		<div class="row"> 		
                       
					<div class="input-field col s12 ">
			<input  type="submit"
				class="btn btn-primary icon-btn" value="submit" onclick="return loadSelectData()">
			

	<!-- 		<div class="d-flex align-items-center">
         <h3 >Password updated failed</h3>
			</div> -->
		</div>
		</div>
		</div></div>
	</form>
	</div>
				 
				 
				 
				 
				 
		

 
</body>
</html>