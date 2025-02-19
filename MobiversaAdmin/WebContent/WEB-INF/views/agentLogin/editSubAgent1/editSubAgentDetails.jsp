<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.AgentSubMenuController"%>
<%@page import="com.mobiversa.common.bo.AgentUserRole"%>

<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
  
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 
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


</style>
</head>
<script type="text/javascript">
jQuery(document).ready(function() {
$('#salutation').select2();
$('#state').select2();
$('#agtype1').select2();
$('#agentName').select2();
$('#agType').select2();

});  
</script>



<script lang="JavaScript">
   

function loadCancelData() {
	//alert("fcancel data");
	
	
	 document.location.href = "${pageContext.request.contextPath}/subagent/list/1";
	form.submit;
}
       
function loadSelectData()
      
       { 	
        //alert("test data"); 	
        
     
		
		  var e1 = document.getElementById("sal1").value;
       
       if (e1 == null || e1 == '' ) {
       
			alert("Please Select salutation");
			//form.submit = false; 
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
		
		
		
		function loadtype(type,type1){
	//alert(' type'+type);
	// var bustype = document.getElementById("businessType1").value;
	//alert('type1 : '+type1);
	//var bustype1 = document.getElementById("businessType");
	//alert('ddd :'+bustype1); 
	var i=0;
	for(i = 0 ; i<type1.options.length ; i++){
		if (type1.options[i].value == type)
			{

			// Item is found. Set its property and exit

			type1.options[i].selected = true;
		}/* else{
			type1.options[0].selected = true;
		} */
	}  

}


function loadtype1(type,type1){

	//alert("test data");
	var i=0;
	
	for(i = 0 ; i<type1.options.length ; i++){
	
	//alert(type1.options[i].value.toUpperCase() +": "+ type.toUpperCase());
		if (type1.options[i].value.toUpperCase() == type.toUpperCase())
			{
			type1.options[i].selected = true;
		}
	 }  

} 
function loadDropData()
{

     //alert('sal');
	 var sal = document.getElementById("sal1").value;
	//alert('sal val : '+sal);
	var salu = document.getElementById("salutation");
	loadtype1(sal,salu);

	//state
	//alert('state');
	var st = document.getElementById("state1").value;
	//alert('state val : '+st);
	var state = document.getElementById("state");
	loadtype(st,state);
	
	//contact sal
	
	
	/* //contact sal
	alert('type');
	 var type = document.getElementById("type1").value;
	alert('sal val : '+sal);
	var salu = document.getElementById("type1");
	loadtype(sal,salu); */
	
	//agentName
	//alert('agent');
	 var agent = document.getElementById("AgentUserName").value;
	//alert('agent val : '+agent);
	var agent1 = document.getElementById("agentName"); 
	loadtype(agent,agent1);
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
 
<body class="sidebar-mini fixed" onload= "loadDropData()">

			

<form action="${pageContext.request.contextPath}/<%=AgentSubMenuController.URL_BASE%>/editSubAgent" method="post" name="form1">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <input type="hidden" name="id" value="${subagent.id}" />
			
			<div class="content-wrapper">
       
       
        <div class="row">
			
            <div class="col-md-12 formContianer">
              <h3 class="card-title">Edit SubAgent</h3>
             <!--  <a class="btn btn-primary btn-flat pull-right" href="#">Edit SubAgent <i class="fa fa-lg fa-plus"></i></a> -->
              <div class="col-md-12 formContianer tableEdit">
          	 
            <div class="card">
            	
         <!--      <h3 class="card-title userTitle">Edit<i class="fa fa-pencil pull-left" aria-hidden="true"></i> --> 
                                <h3 class="card-title userTitle">  SubAgCode: ${subagent.code}				
						<input type="hidden" class="form-control" id="code" name="code" value="${subagent.code}" >
					MailId:	${subagent.mailId}
												
						<input type="hidden" class="form-control" id="mailId" name="mailId" value="${subagent.mailId}">
						<!-- </span> --> </h3>	
				
						 
					 
						 
						 
					 <div class="row">									
                            <div class="form-group col-md-4 i">
								<div class="form-group">
									<label for="salutation">Salutation</label>
									
									<%-- <form:input type="text" class="form-control" id="txtRegName" placeholder="mailId" name="mailId" path="mailId"/> --%>
									
									
									<input type="hidden" value="${subagent.salutation}" id="sal1"/>
							
							
							<select class="form-control"  id="salutation"  name="salutation" path="salutation"  >
									<option value="">Salutation</option>
									<option value="Miss">Miss</option>
									<option value="Mr">Mr</option>
									<option value="Mrs">Mrs</option>
									<option value="Dr">Dr</option>
									<option value="Dato"> Dato</option>
									<option value="Datin">Datin</option>
									</select>
								</div>
							</div>
							<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="Name">Name</label>
								<input type="text" class="form-control" id="txtMid" name="name" value="${subagent.name}" >
												</div></div>	
						
							<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="addr1">Address1</label>
									<input type="text" class="form-control" id="txtMid" name="addr1" value="${subagent.addr1}" >
					
						</div></div>	
						</div>
						
						
						
						<div class="row">
							<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="addr1">Address2</label>
						<input type="text" class="form-control" id="txtMid" name="addr2" value="${subagent.addr2}" >
						</div>
						</div>
							<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="city">City</label>
											
						<input type="text" class="form-control" id="txtMid" name="city" value="${subagent.city}" >
						
						
						</div></div>	
					
							<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="postCode">PostCode</label>
									
						<input type="text" class="form-control" id="txtMid" name="postCode" value="${subagent.postCode}" >
						</div>	
									</div>	
						
						</div>
						
						<div class="row">
							<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="postCode">PhoneNumber</label>
												
						<input type="text" class="form-control" id="txtMid" name="phoneNo" value="${subagent.phoneNo}" >
						
						
						</div></div>	
					
							<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="state">State</label>
								
						<input type="hidden" value="${subagent.state}" id="state1"/>
							<select class="form-control" name="state" id="state"  >
									<option value="">STATE</option>
									<option value="SELANGOR">SELANGOR</option>
										<option value="WP KUALA LUMPUR">WP KUALA LUMPUR</option>
										<option value="WP PUTRAJAYA">WP PUTRAJAYA</option>
										<option value="SARAWAK">SARAWAK</option>
										<option value="JOHOR">JOHOR</option>
										<option value="PENANG"> PENANG</option>
										<option value="SABAH"> SABAH</option>
										<option value="PERAK">PERAK</option>
										<option value="PAHANG">PAHANG</option>
										<option value="NEGERI SEMBILAN">NEGERI SEMBILAN</option>
										<option value="KEDAH">KEDAH</option>
										<option value="MALACCA">MALACCA</option>
										<option value="TERENGGANU">TERENGGANU</option>
										<option value="KELANTAN">KELANTAN</option>
										<option value="PERLIS"> PERLIS</option>
										<option value="LABUAN">LABUAN</option>

								</select>
						
						</div></div>	
						
							<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="AgType">AgType</label>
								
								<input type="hidden" value="${subagent.type}" id="type"/>
								
						<select class="form-control" id="agtype1" name="type" >  <%-- value="${subagent.type} --%>
									
									<option value="<%=AgentUserRole.SUBAGENT.name()%>">SUBAGENT</option>
								</select>
						
						
						</div></div>	
						</div>	
					
						<div class="row">
							<div class="form-group col-md-4">
								<div class="form-group">
									<label  for="Email">AgentName</label>
							
								<input type="hidden" name="AgentUserName" id="AgentUserName" value="${agentName}"/>
								
						<select class="form-control" name="agentName" id="agentName"> <%-- value="${subagent.type} --%>
									
								<c:forEach items="${agentNameList}" var="agentName">
								<option selected value="${agentName}">${agentName}</option>
			</c:forEach>
			</select>
								
						
						
						</div></div>	
						</div>	
						</div>		
						
					<button class="btn btn-primary icon-btn" type="submit"  onclick="return loadSelectData()">Submit</button> 		
				    <button type="button"  class="btn btn-default icon-btn" onclick="return loadCancelData()">Cancel</button>
						
				</div>
				</div>
				</div>
				</div>
				</div>		
				</form>
						
						
					</body>
</html>
						
	
