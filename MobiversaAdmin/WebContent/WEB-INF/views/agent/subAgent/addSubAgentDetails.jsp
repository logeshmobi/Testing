<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.AgentSubController"%>
<%@page import="com.mobiversa.common.bo.AgentUserRole"%>
<%@page import="com.mobiversa.common.bo.SubAgent"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style>
.error {
	color: red;
	font-weight: bold;
}

.asterisk_input:before {
	content: " *";
	color: #062630;
	position: absolute;
	margin: 0px 0px 0px -20px;
	font-size: xx-large;
	padding: 0 5px 0 0;
}


</style>

 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>

<script lang="JavaScript">

	function loadSelectData() {
	
	//alert("test");
	
	if(!validateEmail(document.form1.mailId,10,20))
		{
		
		return false;
		}
	
		
	var e8 = document.getElementById("sal1").value;
       
       if (e8 == null || e8 == '' ) {
       
			alert("Please Select salutation");
			 //form.submit = false; 
			return false;
			}
		
	
	if(!allLetterSpaceSpecialCharacter(document.form1.name,3,100))
		{
		
		return false;
		}
		
		if(!stringlength(document.form1.addr1, 2, 100))
		{
		   return false;
		   }
		   
		   if(!stringlength(document.form1.addr2, 2, 100))
		{
		   return false;
		   }
		   
		    if(!allLetter(document.form1.city,3,15))
		{
		  return false;
		 } 
		 if(!alphanumeric(document.form1.postCode,5,5))
		{
		
		return false;
		}
		
			var e1 = document.getElementById("state").value;
       
       if (e1 == null || e1 == '' ) {
       
			alert("Please Select state");
			 //form.submit = false; 
			return false;
			}
		
		if(!allnumeric(document.form1.phoneNo,9,11))
		{
		return false;
		}
		
	
			
		
			var e2 = document.getElementById("agentName").value;
       
       if (e2 == null || e2 == '' ) {
       
			alert("Please Select agentName");
			 //form.submit = false; 
			return false;
			}   
	 
	
		 	}
	function allLetterSpaceSpecialCharacter(inputtxt, minlength, maxlength) {
		//alert("TEste :"+ inputtxt);
		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters = /^[ A-Za-z_()./&-]*$/; /* /^[a-zA-Z0-9- ]*$/; *//*  /^[A-Za-z]+$/; */
		if ((field.length == 0) || (field.length < mnlen)
				|| (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(letters)) {
			// Focus goes to next field i.e. Address.  
			//document.form1.address.focus();  
			return true;
		} else {
			alert(inputtxt.name + ' must have alphanumeric and space and special characters with -,&,/,() only');
			//uname.focus();  
			return false;
		}
	}
	
	
	function alphanumeric(inputtxt, minlength, maxlength) {
		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uadd = document.registration.address;  
		var letters = /^[0-9a-zA-Z-]+$/;
		if ((field.length < mnlen) || (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(letters)) {
			// Focus goes to next field i.e. Country.  
			//document.registration.country.focus();  
			return true;
		} else {
			alert(inputtxt.name + ' must have alphanumeric with - characters only');
			// uadd.focus();  
			return false;
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
	
	function validateEmail(inputtxt) {
		//alert(inputtxt);
		var field = inputtxt.value;
		//var uemail = document.registration.email;  
		var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
		if (field.match(mailformat)) {
			//document.registration.desc.focus();  
			return true;
			
		} else if (field.length==0)
		 {
			alert("You have entered  "+inputtxt.name+" address!");
			//uemail.focus();  
			return false;
		} else {
		alert("You have entered an invalid "+inputtxt.name+" address!");
		}
	}
	
	function stringlength(inputtxt, minlength, maxlength) {
		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;

		if ((field.length < mnlen) || (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else {
			//alert('Your userid have accepted.');  
			return true;
			} 
			}
			
			
			function allLetter(inputtxt, minlength, maxlength) {

		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters = /^[A-Za-z- ]+$/;
		if ((field.length == 0) || (field.length < mnlen)
				|| (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(letters)) {
			// Focus goes to next field i.e. Address.  
			//document.form1.address.focus();  
			return true;
		} else {
			alert(inputtxt.name + ' must have alphabet characters only');
			//uname.focus();  
			return false;
		}
	}
	</script>
	
<!-- <script type="text/javascript">
jQuery(document).ready(function() {
$('#sal1').select2();
$('#state').select2();
$('#agtype1').select2();
$('#agentName').select2();
});  
    </script> -->
	</head>
<body>


		<c:if test="${responseData  != null}">
			<H4 style="color: #ff4000;" align="center">${responseData}</H4>
		</c:if>
		
		<div class="row">
	<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Add SubAgent</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
    
    
    <form:form method="post" action="agent5/addSubAgent" commandName="subagent"
					name="form1" id="form1">
     <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

						<div class="row">
								<div class="input-field col s12 m6 l6 ">
									<label for="Email">Mail Id</label>
									<form:input type="email"  id="txtMid" path="mailId" name="mailId" placeholder="Email" />
									
								</div>
								<div class="input-field col s12 m6 l6 ">
									
									<form:select  name="salutation" path="salutation" id="sal1" style="width:100%">
										<form:option selected="true" disabled="disabled" value="">- Select Salutation -</form:option>
									<form:option value="Miss">Miss</form:option>
									<form:option value="Mr">Mr</form:option>
									<form:option value="Mrs">Mrs</form:option>
									<form:option value="Dr">Dr</form:option>
									<form:option value="Dato"> Dato</form:option>
									<form:option value="Datin"> Datin</form:option> 
									</form:select>	
									<label>Salutation</label>								
								</div>
								<div class="input-field col s12 m6 l6 ">
									<label for="ContactNo">Name</label>
									<form:input type="text"  id="firstName" placeholder="name" name="name" path="name"/>
								</div>
							</div>
							
							
						<div class="row">
								<div class="input-field col s12 m6 l6 ">
									<label for="Address1">Address1</label>
									<form:input type="text"  id="addr1" placeholder="Address1" name="addr1" path="addr1"/>
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="Address2">Address2</label>
									<form:input type="text"  id="addr2" placeholder="Address2" name="addr2" path="addr2"/>
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="City">City</label>
									<form:input type="text"  id="txtContactNo" placeholder="City" name="city" path="city"/>
								</div>
							</div>
							
							<div class="row">
								<div class="input-field col s12 m6 l6 ">
									<label for="postCode">Post Code</label>
									<form:input type="text"  id="txtContactNo" placeholder="postCode" name="postCode" path="postCode"/>
								</div>
							<div class="input-field col s12 m6 l6 ">
									
									
									<form:select name="state" id="state"
									path="state" style="width:100%">
									<form:option value="">STATE</form:option>
										<form:option value="SELANGOR">SELANGOR</form:option>
										<form:option value="WP KUALA LUMPUR">WP KUALA LUMPUR</form:option>
										<form:option value="WP PUTRAJAYA">WP PUTRAJAYA</form:option>
										<form:option value="SARAWAK">SARAWAK</form:option>
										<form:option value="JOHOR">JOHOR</form:option>
										<form:option value="PENANG"> PENANG</form:option>
										<form:option value=" SABAH"> SABAH</form:option>
										<form:option value="PERAK">PERAK</form:option>
										<form:option value="PAHANG">PAHANG</form:option>
										<form:option value="NEGERI SEMBILAN">NEGERI SEMBILAN</form:option>
										<form:option value="KEDAH">KEDAH</form:option>
										<form:option value="MALACCA">MALACCA</form:option>
										<form:option value="TERENGGANU">TERENGGANU</form:option>
										<form:option value="KELANTAN">KELANTAN</form:option>
										<form:option value="PERLIS"> PERLIS</form:option>
										<form:option value="LABUAN">LABUAN</form:option>
								</form:select>
								<label for="Address2">State</label>
								<%-- 	<form:input type="text" class="form-control" id="txtContactNo" placeholder="Address2" name="addr2" path="addr2"/> --%>
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="phoneNo">Phone Number</label>
									<form:input type="text"  id="txtContactNo" placeholder="phoneNo" name="phoneNo" path="phoneNo"/>
								</div>
							</div>
							
							
							<div class="row">
								<div class="input-field col s12 m6 l6 ">
									
									<form:select  path="type" id="agtype1" name="type" style="width:100%">
									<%-- <form:option value="SALES">SALES</form:option> --%>
									<%-- <form:option value="<%=AgentUserRole.SALES.name()%>" selected="true" >SALES</form:option> --%>
									<form:option value="<%=AgentUserRole.SUBAGENT.name()%>">SUBAGENT</form:option>
								</form:select>
								<label for="phoneNo">Ag Type</label>
									<%-- <form:input type="text" class="form-control" id="txtContactNo" placeholder="phoneNo" name="phoneNo" path="phoneNo"/> --%>
								</div>
							<div class="input-field col s12 m6 l6 ">
									
									
									 <select name="agentName" id="agentName" path="agentName"  style="width:100%">
										<option selected value=""><c:out value="agentName"  /></option>
										<c:forEach items="${agentNameList}" var="agentName">
											<option value="${agentName}">${agentName}</option>
										</c:forEach>
										
								</select>
								<label for="AgentName">Agent Name</label>
									<%-- <form:input type="text" class="form-control" id="addr1" placeholder="Address1" name="addr1" path="addr1"/> --%>
								</div>
							</div>
							
					<div class="row">
						<div class="input-field col s12 m6 l6 ">		
							
				<div class="button-class"  style="float:left;">
						<button class="submitBtn" type="submit"
					onclick=" return loadSelectData()" style="width: 115px; height: 36px">Submit</button>
		</div></div></div>
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
		
			

	
</div>	
							
							</div>

		</div></div>
		</form:form>
			</div>
			
			</div>
		
	
</body>
</html>
