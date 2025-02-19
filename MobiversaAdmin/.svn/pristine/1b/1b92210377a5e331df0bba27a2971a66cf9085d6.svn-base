<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.AdminAgentController"%>
<%@page import="com.mobiversa.common.bo.AgentUserRole"%>
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
</head>

 <!-- <script type="text/javascript">
jQuery(document).ready(function() {
$('#sal1').select2();
$('#state').select2();
$('#agType').select2();


}); 
    </script> --> 
<script lang="JavaScript">

	function loadSelectData() {


		if(!validateEmail(document.form1.mailId))
		{
		return false;
		}
		var a = document.getElementById("sal1").value;
       
       if (a == null || a == '' ) {
       
			alert("Please Select salutation");
			/* form.submit = false; */
			return false;
			}
		
		if(!allLetterSpace(document.form1.firstName, 3, 30))
		{
		return false;
		}
		if(!allLetterSpace(document.form1.lastName, 3, 30))
		{
		return false;
		}
		if(!stringlength(document.form1.addr1, 3, 100))
		{
		return false;
		}
		
		if(document.form1.addr2.value != '')
		{
		if(!stringlength(document.form1.addr2, 3, 50))
		{
		return false;
		}
		}
		if(!allLetterSpace(document.form1.city, 3, 15))
		
		{
		return false;
		}
		
		if(!allnumeric(document.form1.postCode, 5, 5))
		{
		return false;
		}
		
	var a1 = document.getElementById("state").value;
       
       if (a1 == null || a1 == '' ) {
       
			alert("Please Select state");
			/* form.submit = false; */
			return false;
			}
			
		
		
		if(!allnumeric(document.form1.phoneNo, 9, 11))
		{
		return false;
		}
		
			 
	 	var e6 = document.getElementById("agType").value;
	 	
	 	//alert(e6);
	 	
		if(e6 == "AGENT")
		{ 
		
		
		if(!allLetterSpace(document.form1.bankName, 3, 15))
					
			{
			return false;
			}	
				if(! allnumeric(document.form1.bankAcc, 10, 16))
					{
					return false;
					}
					
				
					if(!allnumeric(document.form1.nricNo, 9, 15))
					{
					return false;
					} 
					
								
		}else if(e6 == "STAFF")
			{
			
				 if(document.form1.bankName.value != '')
		{ 
		           if(!allLetter(document.form1.bankName, 3, 15))
					
			{
			return false;
			}	
		}
				if(document.form1.bankAcc.value != '')
		{
					if(! allnumeric(document.form1.bankAcc, 10, 16))
					{
					return false;
					}
					
		}
					
					if(document.form1.nricNo.value != '')
		{
					if(!allnumeric(document.form1.nricNo, 9, 15))
					{
					return false;
					} 
					
			}
						
			}		
 	
		$("select").change(function() {
			$(this).find("option:selected").each(function() {
				if ($(this).attr("value") == "STAFF") {
					$(".bank_name").hide();
					
					
					

				} else if ($(this).attr("value") == "AGENT") {

					$(".bank_name").show();
					
				
					
			}
			});
		}).change();

		}


	function phonenumber(inputtxt) {
		var phoneno = /^\d{10}$/;
		if (inputtxt.value.match(phoneno)) {
			return true;
		} else {
			alert("Not a valid Phone Number");
			return false;
		}
	}

	function postcode(inputtxt) {
		/* alert(len1);
		int len =len1; */
		var postcode = /^\d{5}$/;
		if (inputtxt.value.match(postcode)) {
			return true;
		} else if (inputtxt.value.length == 0) {
			alert("Please enter " + inputtxt.name);
			return false;
		} else {
			alert("Not a valid " + inputtxt.name);
			return false;
		}
	}

	// This function will validate Name.  
	function allLetter(inputtxt, minlength, maxlength) {

		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters = /^[A-Za-z]+$/;
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
	// This function will validate Address.  
	function alphanumeric(inputtxt, minlength, maxlength) {
		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uadd = document.registration.address;  
		var letters = /^[0-9a-zA-Z]+$/;
		if ((field.length < mnlen) || (field.length > mxlen)) {
			alert("Please input the " + inputtxt.name + " between " + mnlen
					+ " and " + mxlen + " characters");
			return false;
		} else if (field.match(letters)) {
			// Focus goes to next field i.e. Country.  
			//document.registration.country.focus();  
			return true;
		} else {
			alert(inputtxt.name + ' must have alphanumeric characters only');
			// uadd.focus();  
			return false;
		}
	}

	// This function will validate ZIP Code.  
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
	
	
	
	function allLetterSpace(inputtxt, minlength, maxlength) {

		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters =  /^[a-zA-Z0-9 ]*$/;/*  /^[A-Za-z]+$/; */
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
			alert(inputtxt.name + ' must have alphanumeric and space only');
			//uname.focus();  
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
			{
			}
		}
	}

	
	function disableRow1(){

 //alert("test123");
  
  var i =document.getElementById("agType").value;
   //alert(i);
   
    if(i == "STAFF")
    {
     //document.getElementById("agType").value;
    
		document.getElementById("BankDetails").style.display = 'none';
		
		document.getElementById("BankDetails1").style.display = 'none';
		
	} else if(i == "AGENT")
	{
	
	document.getElementById("BankDetails").style.display = '';
	
	document.getElementById("BankDetails1").style.display = '';
	}
	
}
	

</script>

<body onload="disableRow()">
	<form:form method="post" action="agent1/addAgent" commandName="agent"
		name="form1" id="form1">
<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Add Agent </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>

<div class="row">				
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

						<div class="row">
							<div class="input-field col s12 m6 l6 ">
									<label for="Email">Mail Id</label>
									<form:input type="email" class="" id="txtMid"
										path="mailId" name="mailId" placeholder="Email" />

									
										<c:if test="${responseData  != null}">
											<H4 style="color: #ff4000;" align="center">${responseData}</H4>
										</c:if>
									
								</div>
						<div class="input-field col s12 m6 l6 ">
									
									<form:select class="" name="salutation"
										path="salutation" id="sal1" style="width:100%">
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
									<label for="ContactNo">First Name</label>
									<form:input type="text" class="" id="firstName"
										placeholder="firstName" name="firstName" path="firstName" />
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="lastName">Last Name</label>
									<form:input type="text" class="" id="lastName"
										placeholder="LastName" name="lastName" path="lastName" />
								</div>
							</div>
						

						<div class="row">
							<div class="input-field col s12 m6 l6 ">
									<label for="Address1">Address1</label>
									<form:input type="text" class="" id="addr1"
										placeholder="Address1" name="addr1" path="addr1" />
								</div>
							
							<div class="input-field col s12 m6 l6 ">
									<label for="Address2">Address2</label>
									<form:input type="text" class="" id="addr2"
										placeholder="Address2" name="addr2" path="addr2" />
								</div>
							

							<div class="input-field col s12 m6 l6 ">
									<label for="City">City</label>
									<form:input type="text" class="" id="txtContactNo"
										placeholder="City" name="city" path="city" />
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="postCode">Post Code</label>
									<form:input type="text" class="" id="txtContactNo"
										placeholder="postCode" name="postCode" path="postCode" />
								</div>
							</div>


							<div class="row">
								<div class="input-field col s12 m6 l6 ">
									

									<form:select class="" name="state" id="state" style="width:100%"
										path="state">
										<form:option value="">STATE</form:option>
										<form:option value="SELANGOR">SELANGOR</form:option>
										<form:option value="WP KUALA LUMPUR">WP KUALA LUMPUR</form:option>
										<form:option value="WP PUTRAJAYA">WP PUTRAJAYA</form:option>
										<form:option value="SARAWAK">SARAWAK</form:option>
										<form:option value="JOHOR">JOHOR</form:option>
										<form:option value="PENANG">PENANG</form:option>
										<form:option value="SABAH">SABAH</form:option>
										<form:option value="PERAK">PERAK</form:option>
										<form:option value="PAHANG">PAHANG</form:option>
										<form:option value="NEGERI SEMBILAN">NEGERI SEMBILAN</form:option>
										<form:option value="KEDAH">KEDAH</form:option>
										<form:option value="MALACCA">MALACCA</form:option>
										<form:option value="TERENGGANU">TERENGGANU</form:option>
										<form:option value="KELANTAN">KELANTAN</form:option>
										<form:option value="PERLIS">PERLIS</form:option>
										<form:option value="LABUAN">LABUAN</form:option>
									</form:select>
									<label for="Address2">State</label>

								</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="phoneNo">Phone Number</label>
									<form:input type="text"  id="txtContactNo"
										placeholder="phoneNo" name="phoneNo" path="phoneNo" />
								</div>
							</div>
						

						<div class="row">
							<div class="input-field col s12 m6 l6 ">
									<input type="hidden"
										class="" id="txtMid" name="agType1"
										value="${agent.agType}" />
									<form:select path="agType" id="agType" style="width:100%"
										name="agType" onchange="disableRow1()">
										<%-- <form:option value="STAFF">STAFF</form:option> --%>
										<form:option value="<%=AgentUserRole.STAFF.name()%>"
											selected="true">STAFF</form:option>
										<form:option value="<%=AgentUserRole.AGENT.name()%>">AGENT</form:option>
									</form:select>
									<label for="agType">Ag Type</label> 
								</div>
							</div>



							<!-- <div class="bank_name" > -->
							<div class="row" style="display: none;" id="BankDetails">
								<div class="input-field col s12 m6 l6 ">
										<label for="bankName">Bank Name</label>
										<form:input type="text"  id="bankName"
											placeholder="BankName" name="bankName" path="bankName" />
									</div>
								<div class="input-field col s12 m6 l6 ">
										<label for="bankAcc">Bank Account </label>
										<form:input type="text"  id="bankAcc"
											placeholder="BankAcc" name="bankAcc" path="bankAcc" />
									</div>

								<div class="input-field col s12 m6 l6 ">
										<label for="nricNo">Nric Account</label>
										<form:input type="text"  id="txtContactNo"
											placeholder="nricNo" name="nricNo" path="nricNo" />
									</div>
								</div>

						<div class="row">
						<div class="input-field col s12 m6 l6 ">
						<div class="button-class" >
						<button class="submitBtn" type="submit"
					onclick=" return loadSelectData()">Submit</button>
					</div></div></div>
</div>
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
		</div>

	
</div>
</form:form>
</body>
</html>
