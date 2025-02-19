<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <meta name="viewport" content="width=device-width" />
</head>



</style>

<!-- <script type="text/javascript">
jQuery(document).ready(function() {
$('#salutation').select2();

});  
</script> -->


<script lang="JavaScript">


	
	function loadSelectData() {
		/* alert("test data"); */
		var a = document.getElementById("tid").value;
		if (a > 0) {
			if (!allnumeric(document.form1.tid, 8, 8)) {
				return false;
			}
		}

	}

	function allnumeric(inputtxt, minlength, maxlength) {
		var field = inputtxt.value;

		var mnlen = minlength;
		var mxlen = maxlength;
		//var uzip = document.registration.zip;  
		var numbers = /^[0-9]+$/;
		if ((field.length < mnlen) || (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(numbers)) {
			// Focus goes to next field i.e. email.  
			//document.registration.email.focus();  
			return true;
		} else {
			alert(inputtxt.name + ' must have numeric characters only');
			//uzip.focus();  
			return false;
		}
	}

	function radioAuth() {
		//var a= ${merchant.preAuth};
		var a = document.getElementById("testAuth").value;
		//alert("Auth :"+a);
		if (a == 'Yes') {
			document.getElementById("preAuth1").checked = true;
		} else {
			document.getElementById("preAuth2").checked = true;
		}

	}
</script>
<body onload="radioAuth()">
<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Mapping Mobile User to TID</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>


<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">


 <form:form method="post" id="form1" name="form1" commandName="mobileuser"
		action="${pageContext.request.contextPath}/mobileUser/updatedevicemap" >

<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <form:input type="hidden" name="id" value="${mobileUser.id}" path="id"/>

					<div class="row">
							<div class="input-field col s12 m6 l6 ">
									<label for="username">Mobile UserName</label>
									<form:input type="text" id="username" name="username" class="form-control" 
									value="${mobileuser.username}" path="username" readonly="true"/>
								</div>
							
							<div class="input-field col s12 m6 l6 ">
								<label for="status">Status</label>
									<form:input type="text" id="status" name="status" class="form-control" 
									value="${mobileuser.status}" path="status" readonly="true"/>
									
								</div>
							
					</div>
					<div class="row">
							<div class="input-field col s12 m6 l6 ">
									<label for="tid">EZYWIRE TID</label>
									<form:input type="text" id="tid" name="tid" class="form-control" 
									value="${mobileuser.tid}" path="tid"/>
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="motoTid">EZYMOTO TID</label>
									<form:input type="text" id="motoTid" name="motoTid" class="form-control" 
									value="${mobileuser.motoTid}" path="motoTid"/>
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="ezyrecTid">EZYREC TID</label>
									<form:input type="text" id="ezyrecTid" name="ezyrecTid" class="form-control"
									 value="${mobileuser.ezyrecTid}" path="ezyrecTid"/>
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="ezypassTid">EZYPASS TID</label>
									<form:input type="text" id="ezypassTid" name="ezypassTid" class="form-control"
									 value="${mobileuser.ezypassTid}" path="ezypassTid"/>
								</div>
							</div>
				
					
					<div class="row">
							
							
                          <div class="input-field col s12 m6 l6 ">Pre-Auth:
                            	
                                <div class="radiobuttons">
									<label> <form:radiobutton name="preAuth"
										id="preAuth1" value="Yes" path="preAuth"/> <span>Yes</span>
									</label> <label> <form:radiobutton name="preAuth"
										id="preAuth2" value="No" path="preAuth"/> <span>No</span>
									</label>
								</div>
 						  </div></div>
      
      
         
      <button type="submit" class="submitBtn" onclick="loadSelectData()">Submit</button>
  	<style>
							
				.select-wrapper .caret { fill: #005baa;}
							
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				.submitBtn {padding: 8px 20px;
				    border-radius: 10px;
				    background-color: #54b74a;
				    color: #fff;
				    margin: auto;
				    display: table;}
				</style>
      
   </form:form>
   </div></div></div>
   </div></div></body>
      
      
      
      
      
      
      
      
      
      
      

	
