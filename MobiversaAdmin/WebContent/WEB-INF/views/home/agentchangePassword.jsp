<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.AgentProfileController"%>
<%@page import="com.mobiversa.common.bo.Agent"%>
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

 <script lang="JavaScript">
 
 
 function checkPassword()
 {
// alert("test data");
 var error = "";


 var e = document.getElementById("newPassword").value;

 var e1 = document.getElementById("confirmPassword").value;

 var e2 = document.getElementById("password").value;
 
 /*  var re = /^(?=.*[0-9][a-z][A-Z][!@#$%^&*]){8,15}$/; */
// var re = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,15}$/;

 /* var re = /^(?=.*[!@#$%^&*][a-zA-Z0-9])[a-zA-Z0-9!@#$%^&*]{8,15}$/; */
 
 var re = /^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{8,15}$/;
 //alert("test data");

 if (e2 == null || e2 == '') {
 	alert("Please enter Old Password");
 	return false;
 } else if (e == null || e == '') {
 	alert("Please enter New Password");
 	return false;

 } else if ((e.length < 8))

 {
    //alert("test data 11111");
 	error = "The password is the minimum eight digits. \n";
 	alert("The password is the wrong length:" + error );
 	return false;

 }
 
 
 else if ((e.length > 15))

 {
    //alert("test data 11111");
 	error = "The password is the maximum 15 digits. \n";
 	alert("The password is the wrong length:" + error );
 	return false;

 }
 	 else if(!re.test(e)) {
 	      alert("Error:  password   must contain alpha numeric with special Characters ");
 	      //form1.e.focus();
 	     
 	      return false;
 	    
 	 }
  else if (e1 == null || e1 == '') {
 	alert("Please enter Confirm Password");
 	return false;
 } else if (e1 == e2) {
 	alert("Old Password and New Password are same");
 	return false;
 } else if (e1 != e) {
 	alert("New Password and Confirm password Not Match");
 	return false;
 }

 else {
	 
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
        <div class="card-content" style="padding:0px;">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Change Password</strong></h3>
          </div> 
        </div>
      </div>
    </div>
 </div>




	<form method="post" name="form1" data-parsley-validate  class="form-horizontal"
		action="<c:url value='/agentProfdetails/confirmPasswordbyagent'/>"  onsubmit="return checkPassword();"> <!--  onsubmit="return checkPassword();" -->

<div class="row">
  <div class="col  s10 m8 l10">
      <div class="card border-radius">
        <div class="card-content padding-card">
          <div class="d-flex align-items-center">
           <h5 style="color:#005baa;line-height:47px">  USERNAME <br/>
           <b><input style="border-bottom:0px!important; color:#005baa;" type="text" value ="${agentUserName}" readonly> </b></h5>
           
          </div>
          
				<div class="row passwordcard"> 		
                        <div class="col s12 m4 l6 reset-column"> 
					<div class="input-field col s12 ">
						<input type="password" placeholder="Old Password" name="password"  id="password">
						<!-- <label for="name">Old Password</label> -->
					</div>
					<div class="input-field col s12 ">
						<input type="password"  id="newPassword" name="newPassword"  placeholder="NewPassword">
						<!-- <label for="name">New Password</label> -->
					</div>
					
					<div class="input-field col s12 ">
						<input type="password" placeholder="ConfirmPassword"  name="confirmPassword"  id="confirmPassword"  >
						<!-- <label for="name">Confirm Password</label> -->
					</div>
					
					<div class="input-field col s12 ">  <button class="submitBtn">Submit</button> </div>
					 
				</div>
				
				 <div class="col s6 m2 l6">
                 <p>Password Should Contain Least 8 Characters</p>
                 <p> Password Should Contain Least One UPPER/lowercase [a-zA-Z</p>
                 <p> Password Should Contain Least One  numeric and special Characters [0-9!@#$%^&`]m 
				 </p>
                 <p> </p>

				 </div>
				 
				 <input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
				
				</div>
				
			 
				 

<style> 
.submitBtn {padding: 8px 20px;
    border-radius: 10px;
    background-color: #54b74a;
    color: #fff;
    margin: auto;
    display: table;}

.reset-column { padding:10px 40px 0px 0px !important;
border-right :1px solid #ccc;}
@media screen and (max-width:768px) {
.reset-column { padding:10px !important;
border-right :0px solid #ccc;}
.padding-card {padding:20px !important; }
}
.passwordcard{
}
</style> 
   
        </div>
      </div>
    </div>
    </div>
    
 </form>   
	</div>
</body>
</html>

