<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page	import="com.mobiversa.payment.controller.MobiliteWebController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

 <script lang="text/javascript">
 
 function checkData()
 {
 alert("check");
 var newPassword=document.getElementById("newPassword").value;
 var name=document.getElementById("name").value;
 var oldpassword=document.getElementById("password").value;
 var confirmPassword=document.getElementById("confirmPassword").value; 

 if(newPassword==null || newPassword==''){
 alert('new password should not empty');
 document.getElementById("newPassword").focus();
  return false;
 }
  else if(name==null || name==''){
 alert('name should not empty');
 document.getElementById("name").focus();
  return false;
 }
 else if(oldpassword==null || oldpassword==''){
 alert('old password should not empty');
 document.getElementById("password").focus();
 return false;
 }
 else if(confirmPassword==null || confirmPassword=='')
 {
 alert('confirm Password should not empty');
 document.getElementById("confirmPassword").focus();
 return false;
 }
 
 else
 {
 return true;
 }
 }
 </script>
</head>


<body>
<div class="container-fluid">  
  
 <div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Change Password </strong></h3>
          </div>
          
          <div class="d-flex align-items-center">
         <h3 style="color:#008000 !important">Password updated Successfully</h3>
         </div>
          
          
        </div>
      </div>
    </div>
    </div>
	
	<form method="post"
		action="${pageContext.request.contextPath}/merchantProfile/confirmPassword">

 <div class="row">
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

	<div class="row"> 		
                       
					<div class="input-field col s12 ">
					<input type="text" value ="${merchantUserName}" readonly> 
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
                  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
                  
						</div>	


				
<div class="row"> 		
                       
					<div class="input-field col s12 ">
			<input  type="submit"
				class="btn btn-primary blue-btn" value="submit" onclick="return loadSelectData()">
			</div>
			
		<!-- 	<div class="input-field col s12 ">

		 <div class="d-flex align-items-center">
         <h3 >Password updated Successfully</h3>
         </div>
		</div> -->
		</div>
		</div></div>
		</div></div>
	</form>
	</div>



</body>
</html>