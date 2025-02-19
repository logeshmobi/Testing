<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.AgentSubMenuController"%>
<%@page import="com.mobiversa.common.bo.AgentUserRole"%>

<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
   <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
     <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
   <%--   <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
    --%>
    <script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "subagent/");
</script>
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


</style>
</head>

<script type="text/javascript">

jQuery(document).ready(function() {
$('#salutation').select2();
$('#state').select2();
$('#agtype1').select2();
$('#agentName').select2();



});  
    </script>
  <!--   <script type="text/javascript">
var uri = window.location.toString();
if (uri.indexOf("subagent/addSubAgent") > 0) {
    var clean_uri = uri.substring(0, uri.indexOf("subagent/addSubAgent"));
    window.history.replaceState({}, document.title, clean_uri);
}
</script> -->
<script lang="JavaScript">
   
       
      function loadSelectData()
      
       { 	
      //  alert("test data"); 	
        
        if(!validateEmail(document.form1.mailId,10,20))
		{
		
		return false;
		}
		
		var a = document.getElementById("salutation").value;
       
       if (a == null || a == '' ) {
       
			alert("Please Select salutation");
			/* form.submit = false; */
			return false;
			}
      
			 if(!allLetterSpaceSpecialCharacter(document.form1.name, 3, 30))
		{
		
		return false;
		} 
		
		if(!stringlength(document.form1.addr1, 3, 100))
		{
		   return false;
		   }
		   
		   if(!stringlength(document.form1.addr2, 3, 100))
		{
		   return false;
		   }
		   
		   
		   if(!stringlength(document.form1.city, 3, 20))
		{
		   return false;
		   } 
		   
		   
		   if(!allnumeric(document.form1.postCode, 5, 5))
		{
		   return false;
		   }
		   
		    if(!allnumeric(document.form1.phoneNo, 9, 11))
		{
		   return false;
		   }
		   
		   
		   var e = document.getElementById("state").value;
       
       if (e == null || e == '' ) {
       
			alert("Please Select state");
			//form.submit = false; 
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
       
     
        
       //allnumeric tid & reference No validation
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
       
       //alphanumeric deviceID validation
       
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
	
	
	
       
       function allLetterSpaceSpecialCharacter(inputtxt, minlength, maxlength) {
		//alert("TEste :"+ inputtxt);
		var field = inputtxt.value;
		var mnlen = minlength;
		var mxlen = maxlength;
		// var uname = document.registration.username;  
		var letters = /^[ A-Za-z0-9_()./&-]*$/; /* /^[a-zA-Z0-9- ]*$/; *//*  /^[A-Za-z]+$/; */
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
       </script>
 
<body>


				<form:form method="post" action="addSubAgent" commandName="subagent"
					name="form1" id="form1">
					<div style="overflow:auto;border:1px;width:100%">
					 <div class="content-wrapper">

			<div class="row">

				<div class="col-md-12 formContianer">
					<h3 class="card-title">Add SubAgent</h3>

					<div class="card">
		<!-- 	<div class="card-body formbox"> -->
						<div class="row">
							<div class="form-group col-md-4">
									<label for="MailID">Mail Id</label>
									
									<form:input type="text" class="form-control" id="txtRegName" placeholder="mailId" name="mailId" path="mailId"/>
								</div>
						
						
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="Salutation">Salutation</label>
									
								<form:select class="form-control" id="salutation"  path="salutation" name="salutation" style="width:100%">
									<form:option value="">Salutation</form:option>
									<form:option value="Miss">Miss</form:option>
									<form:option value="Mr">Mr</form:option>
									<form:option value="Mrs">Mrs</form:option>
									<form:option value="Dr">Dr</form:option>
									<form:option value="Dato"> Dato</form:option>
									<form:option value="Datin"> Datin</form:option>
									</form:select>
									
									</div>
									
									</div>
									
								
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="MailID">Name</label>
									
									<form:input type="text" class="form-control" id="txtRegName" placeholder="name" name="name" path="name"/>
								</div>
							</div>
						</div>				
							
						
							<div class="row">
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="Address1">Address1</label>
										<form:input type="text" class="form-control" id="txtRegName" placeholder="addr1" name="addr1" path="addr1"/>
								</div></div>
							
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="Address1">Address2</label>
									
									<form:input type="text" class="form-control" id="txtRegName" placeholder="addr2" name="addr2" path="addr2"/>
								</div></div>
						
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="Address1">City</label>
									
									<form:input type="text" class="form-control" id="txtRegName" placeholder="city" name="city" path="city"/>
								</div>
							</div>
						</div>	
						
							<div class="row">
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="postCode">Post Code</label>
	                               <form:input type="text" class="form-control" id="txtRegName" placeholder="postCode" name="postCode" path="postCode"/>
								</div>
							</div>
						<div class="form-group col-md-4">
								<div class="form-group">
									<label for="postCode">Phone Number</label>
									
									<form:input type="text" class="form-control" id="txtRegName" placeholder="phoneNo" name="phoneNo" path="phoneNo"/>
								</div>
							</div>
						
									
						
								
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="State">State</label>
									
							<form:select class="form-control" name="state1" id="state" path="state" style="width:100%">
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
								
								
								</div>
								
								</div>
								</div>
								
									<div class="row">
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="AgType">Ag Type</label>
									
									<select class="form-control" path="type" id="agtype1" name="type" style="width:100%">
									<option value="<%=AgentUserRole.SUBAGENT.name()%>">SUBAGENT</option>
									</select>
								</div>
							</div>
					
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="AgentName">Agent Name</label>
									
									<input type="hidden" name="AgentUserName"
		id="AgentUserName" value="${agentName}">
		<select class="form-control" name="agentName" id="agentName" style="width:100%">
		
		<c:forEach items="${agentNameList}" var="agentName">
				<option selected value="${agentName}">${agentName}</option>
			</c:forEach>
			</select>
								</div>
							</div>
						</div>	
						
							</div>

				</div>
		</div>			
				<button class="btn btn-primary" type="submit" id="submit"
					  onclick="return loadSelectData()">Submit</button>    <!-- onclick=" return loadSelectData()" -->
		
	
			
		</div>
	<!-- 	</div> -->
			

</div>
	</form:form>

</body>
</html>
