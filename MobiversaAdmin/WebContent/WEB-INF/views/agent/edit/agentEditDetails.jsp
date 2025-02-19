<%@page import="com.mobiversa.payment.controller.AdminAgentController"%>
<%@page import="com.mobiversa.common.bo.AgentUserRole"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

 <html lang="en-US">
<head>

    

 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
<script lang="JavaScript">

function loadCancelData() {
	//alert("fcancel data");
	
	
	 document.location.href = "${pageContext.request.contextPath}/agent1/list/1";
	form.submit;
}
function loadSelectData() {
 // alert("test data");

  var a = document.getElementById("salutation").value;
       
       if (a == null || a == '' ) {
       
			alert("Please Select salutation");
			/* form.submit = false; */
			return false;
			}
		
		if(!allLetterSpace(document.form1.firstName, 3, 30))
		{
		return false;
		}
		if(!allLetterSpace(document.form1.lastName,3,30))
		{
		return false;
		}
		if(!stringlength(document.form1.addr1, 3, 50))
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
		if(!allLetterSpace(document.form1.city,3,15))
		
		{
		return false;
		}
		
	var a1 = document.getElementById("state").value;
       
       if (a1 == null || a1 == '' ) {
       
			alert("Please Select state");
			/* form.submit = false; */
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
		
		var e6 = document.getElementById("agType").value;
	 	
	 	//alert(e6);
	 	
		if(e6 == "AGENT")
		{ 
		
		
		if(!allLetterSpace(document.form1.bankName, 3, 15))
					
			{
			return false;
			}	
				if(!allnumeric(document.form1.bankAcc, 10, 16))
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
		           if(!allLetterSpace(document.form1.bankName, 3, 15))
					
			{
			return false;
			}	
		}
				if(document.form1.bankAcc.value != '')
		{
					if(!allnumeric(document.form1.bankAcc, 10, 16))
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
 
	 $(document).ready(function() {
		//alert("test data"); 
		$("select").change(function() {
			$(this).find("option:selected").each(function() {
				if ($(this).attr("value") == "STAFF") {
					$(".bank_name").hide();

				} else if ($(this).attr("value") == "AGENT") {

					$(".bank_name").show();
				}

			});
		}).change();
	});
	
	
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
		} /* else{
			type1.options[0].selected = true;
		}  */
	 }  

} 


function loadtype1(type,type1){
	
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
 
//contact sal
	//alert('sal');
	 var sal = document.getElementById("salutation").value;
	//alert('sal val : '+sal);
	var salu = document.getElementById("salutation1");
	loadtype1(sal,salu);
	
	 var stats = document.getElementById("status").value;
		//alert('sal val : '+sal);
		var status = document.getElementById("status1");
		loadtype1(stats,status);
	
	//state
	//alert('state');
	var st = document.getElementById("state").value;
	//alert('state val : '+st);
	var state = document.getElementById("state1");
	loadtype1(st,state);
	
	//alert('agType');
	var agtype = document.getElementById("agType1").value;
	//alert('state val : '+agtype);
	var agtype1 = document.getElementById("agType");
	loadtype1(agtype,agtype1);
	
}	



function disableRow(){

 //alert("test123");
  
  var i =document.getElementById("agType1").value;
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
		
		/* document.getElementById("owner22").style.display = 'none';
		document.getElementById("owner3").style.display = 'none';
		document.getElementById("owner33").style.display = 'none';
		document.getElementById("owner4").style.display = 'none';
		document.getElementById("owner44").style.display = 'none';
		document.getElementById("owner5").style.display = 'none';
		document.getElementById("owner55").style.display = 'none'; */
}



function disableRow1(){

 //alert("test123");
  
  var i =document.getElementById("agType1").value;
  document.getElementById("agType").value=i;
  
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
		/* document.getElementById("owner22").style.display = 'none';
		document.getElementById("owner3").style.display = 'none';
		document.getElementById("owner33").style.display = 'none';
		document.getElementById("owner4").style.display = 'none';
		document.getElementById("owner44").style.display = 'none';
		document.getElementById("owner5").style.display = 'none';
		document.getElementById("owner55").style.display = 'none'; */

 
function phonenumber(inputtxt)  
{  
  var phoneno = /^\d{10}$/;  
  if(inputtxt.value.match(phoneno))  
  {  
      return true;  
  }  
  else  
  {  
     alert("Not a valid Phone Number");  
     return false;  
  }  
  }  
  
  
  function postcode(inputtxt)  
{  
	/* alert(len1);
	int len =len1; */
  var postcode = /^\d{5}$/;  
  if(inputtxt.value.match(postcode))  
  {  
      return true;  
  }  
   else if(inputtxt.value.length ==0) 
  {  
     alert("Please enter "+inputtxt.name);  
     return false;  
  }   
  else
  {  
     alert("Not a valid "+inputtxt.name);  
     return false;  
  }  
  }  
  
  
  //****************************************************************//
  
  
  // This function will validate Name.  
  function allLetter(inputtxt, minlength, maxlength)    
  {   
   /* alert("test"); */
  var field = inputtxt.value;   
  var mnlen = minlength;  
  var mxlen = maxlength;  
 // var uname = document.registration.username;  
  var letters = /^[A-Za-z]+$/;
  if((field.length == 0 ) || (field.length<mnlen) || (field.length > mxlen))  
	{   
	alert("Please input the "+inputtxt.name+" between " +mnlen+ " and " +mxlen+ " characters");  
	return false;  
	}    
  else if(field.match(letters))  
  {  
  // Focus goes to next field i.e. Address.  
  //document.form1.address.focus();  
  return true;  
  }  
  else  
  {  
  alert(inputtxt.name+' must have alphabet characters only');  
  //uname.focus();  
  return false;  
  }  
  } 
  // This function will validate Address.  
 function alphanumeric(inputtxt, minlength, maxlength)  
  {
   var field = inputtxt.value;   
  var mnlen = minlength;  
  var mxlen = maxlength;     
 // var uadd = document.registration.address;  
  var letters = /^[0-9a-zA-Z]+$/; 
  if((field.length<mnlen) || (field.length > mxlen))  
	{   
	alert("Please input the "+inputtxt.name+" between " +mnlen+ " and " +mxlen+ " characters");  
	return false;  
	}     
  else if(field.match(letters))  
  {  
  // Focus goes to next field i.e. Country.  
  //document.registration.country.focus();  
  return true;  
  }  
  else  
  {  
  alert(inputtxt.name+' must have alphanumeric characters only');  
 // uadd.focus();  
  return false;  
  }  
  }  
 
 // This function will validate ZIP Code.  
 function allnumeric(inputtxt, minlength, maxlength)  
  {
   var field = inputtxt.value;  
  
  var mnlen = minlength;  
  var mxlen = maxlength;    
  //var uzip = document.registration.zip;  
  var numbers = /^[0-9]+$/;  
  if((field.length < mnlen) || (field.length > mxlen))  
	{   
	alert("Please input the "+inputtxt.name+" between " +mnlen+ " and " +mxlen+ " characters");  
	return false;  
	}     
  else if(field.match(numbers))  
  {  
  // Focus goes to next field i.e. email.  
  //document.registration.email.focus();  
  return true;  
  }  
  else  
  {  
  alert(inputtxt.name+' must have numeric characters only');  
  //uzip.focus();  
  return false;  
  }  
  }   
 // This function will validate Email.  
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
  
  function stringlength(inputtxt, minlength, maxlength)  
{ 
     /* alert("test address");  */ 
var field = inputtxt.value;   
var mnlen = minlength;  
var mxlen = maxlength;  
  
 if((field.length<mnlen) || (field.length > mxlen))    
{   
alert("Please input the "+inputtxt.name+" between " +mnlen+ " and " +mxlen+ " characters");  
return false;  
}  
else  
{   
//alert('Your userid have accepted.');  
return true;  
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
 //agtype page load execute this function//
 
   
 
  


</script>
</head>
<body onload ="loadDropData();disableRow1()" >
<div class="container-fluid">   

 <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Edit Agent</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
    
    <form action="${pageContext.request.contextPath}<%=AdminAgentController.URL_BASE%>/editAgent" 
method="post" name="form1">
 <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <input type="hidden" name="id" value="${agent.id}" />
 
        <div class="d-flex align-items-center">
		 <button type="button" class="btn btn-primary blue-btn"><a style="color:#fff !important;"
          href="${pageContext.request.contextPath}/agent1/addAgent">Add New <i class="material-icons">add</i></a></button>
         </div>
        
       
			  <div class="d-flex align-items-center">
          		 <h3 class="text-white"> Ag Code: ${agent.code}				
						<input type="hidden" class="form-control" id="code" name="code" value="${agent.code}" >
					Mail Id:	${agent.mailId}
												
						<input type="hidden" class="form-control" id="mailId" name="mailId" value="${agent.mailId}">
						</h3>
            </div>
						 <div class="row">									
                            <div class="input-field col s12 m6 l6 ">

                        
           <input type="hidden" class="form-control" value="${agent.salutation}" id="salutation" name="salutation"/>
						<select  name="salutation1"  id="salutation1" 
						onchange="document.getElementById('salutation').value=this.value;">
							
							<option   value="" >Salutation</option>
									<option value="Miss" >Miss</option>
									<option value="Mr" >Mr</option>
									<option value="Mrs" >Mrs</option>
									<option value="Dr" >Dr</option>
									<option value="Dato"> Dato</option>
                                    <option value="Datin"> Datin</option> 
									<%-- <form:option value="">Others</form:option> --%>
									</select> 
									 <label  for="salutation">Salutation</label>
									</div>
									
									<div class="input-field col s12 m6 l6 ">
									<label  for="fName">First Name</label>
								<input type="text"  id="firstName" name="firstName" value="${agent.firstName}" >
								</div>
							
							<div class="input-field col s12 m6 l6 ">
									<label  for="lName">Last Name</label>
								<input type="text" id="lastName" name="lastName" value="${agent.lastName}" >
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label  for="addr1">Address1</label>
								<input type="text" id="addr1" name="addr1" value="${agent.addr1}" >
								</div>
							</div>	
							<div class="row">
							<div class="input-field col s12 m6 l6 ">
									<label  for="addr2">Address2</label>
								<input type="text" id="addr2" name="addr2" value="${agent.addr2}" >
								</div>
							
							<div class="input-field col s12 m6 l6 ">
									<label  for="city">City</label>
								<input type="text"  id="city" name="city" value="${agent.city}" >
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label  for="addr1">Post Code</label>
								<input type="text"  id="postCode" name="postCode" value="${agent.postCode}" >
								</div>
							</div>	
							
							
							
							
								<div class="row">
							<div class="input-field col s12 m6 l6 ">
									
								<input type="hidden" value="${agent.state}" id="state" name="state">

                              <select class="form-control" name="state1" id="state1"
									 onchange="document.getElementById('state').value=this.value;">
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
								<label  for="state">State</label>
								</div>
							
							<div class="input-field col s12 m6 l6 ">
									<label  for="phoneNo">Phone Number</label>
								<input type="text"  id="phoneNo" name="phoneNo" value="${agent.phoneNo}" >
								</div>
							<div class="input-field col s12 m6 l6 ">
									
								<input type="hidden" class="form-control" id="agType" name="agType" value="${agent.agType}" >
								
                            	<select    name="agType1" id="agType1" onchange="disableRow1()" ><!-- onchange="loadData()" -->  <%--  value="${agType}" --%>
	<!-- <option value="agType" >AgType</option> -->
  <option  value="<%=AgentUserRole.STAFF.name() %>">STAFF</option>
   <option value="<%=AgentUserRole.AGENT.name() %>">AGENT</option>
   <%--  <option value="<%=AgentUserRole.SUPERAGENT.name() %>">SUPERAGENT</option> --%>
</select>
<label  for="agType">Ag Type</label>
								</div>
							<div class="input-field col s12 m6 l6 ">
									
								<input type="hidden" class="form-control" id="status" name="status" value="${agent.status}" >
								
                            	<select   name="status1" id="status1" 
                            	onchange="document.getElementById('status').value=this.value;">
                            		<option  value="ACTIVE">ACTIVE</option>
									<option value="SUSPENDED">SUSPENDED</option>
  								</select>
  								<label  for="agType">Status</label>
								</div>
							</div>		
									
							
									<div class="row" >
									<div style="display: none;" id="BankDetails">
							<div class="input-field col s12 m6 l6 ">
									<label  for="fName">Bank Name</label>
								<input type="text" id="bankName" name="bankName" value="${agent.bankName}" >
								</div>
							
							
							<div class="input-field col s12 m6 l6 ">
									<label  for="bankAcc">Bank Account</label>
								<input type="text" id="bankAcc" name="bankAcc" value="${agent.bankAcc}" >
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label  for="NricAccount">Nric Account</label>
								<input type="text" id="nricNo" name="nricNo" value="${agent.nricNo}" >
								</div>
								</div>
							</div>	
								

						<div class="row" >
						<div class="input-field col s12 m6 l6 ">
								<div class="button-class" style="float:left;">	
							  <button type="submit"  class="btn btn-primary blue-btn" onclick="return loadSelectData()">Submit</button>
								   <button type="button"  class="export-btn waves-effect waves-light btn btn-round indigo" onclick="return loadCancelData()">Cancel</button>
				
								</div></div></div>	
								
				<style>
						
				.select-wrapper .caret { fill: #005baa;}				
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				
				.addUserBtn,.addUserBtn:hover {
				background-color: #fff;border: 1px solid #005baa;border-radius: 20px;color: #005baa; font-weight:600;}
				</style>			
		</div>
	
	</div>							   		
</div></div>
</form>
</div>

</body>
</html>
