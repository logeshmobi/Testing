<%@page import="com.mobiversa.payment.controller.AgentSubController"%>
<%@page import="com.mobiversa.common.bo.AgentUserRole"%>
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

   
 <html lang="en-US">
<head>


<script lang="JavaScript">



 function loadSelectData() {
	
	/* if(!validateEmail(document.form1.mailId,10,20))
		{
		
		return false;
		}
	 */
		
	var e8 = document.getElementById("salutation").value;
       
       if (e8 == null || e8 == '' ) {
       
			alert("Please Select salutation");
			 //form.submit = false; 
			return false;
			}
		
	
	if(!allLetterSpaceSpecialCharacter(document.form1.name,3,20))
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
		   
		    if(!allLetter(document.form1.city,3,15))
		{
		  return false;
		 } 
		 if(!alphanumeric(document.form1.postCode,5,5))
		{
		
		return false;
		}
		
		if(!allnumeric(document.form1.phoneNo,9,11))
		{
		return false;
		}
		
		var e1 = document.getElementById("state").value;
       
       if (e1 == null || e1 == '' ) {
       
			alert("Please Select state");
			 //form.submit = false; 
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


function loadtype(type,type1){
	
	var i=0;
	for(i = 0 ; i<type1.options.length ; i++){
		if (type1.options[i].value == type)
			{

		
			type1.options[i].selected = true;
		}
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
	 
	
	 var agent = document.getElementById("AgentUserName").value;
	//alert('agent val : '+agent);
	var agent1 = document.getElementById("agentName"); 
	loadtype(agent,agent1);
	}


</script>
</head>
<body onload="loadDropData()">
<div class="container-fluid">   

 <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Edit SubAgent</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
    
    
<form action="${pageContext.request.contextPath}<%=AgentSubController.URL_BASE%>/editSubAgent" method="post" name="form1">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <input type="hidden" name="id" value="${subagent.id}" />
 <!-- <div style="overflow:auto;border:1px;width:100%"> -->
 <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        
         <div class="d-flex align-items-center">
		 <button type="button" class="btn btn-primary blue-btn"><a style="color:#fff !important;"
          href="${pageContext.request.contextPath}/agent5/addSubAgent">Add New <i class="material-icons">add</i></a></button>
         </div>
            
            <div class="d-flex align-items-center">
          		 <h3 class="text-white">Sub AgCode: ${subagent.code}				
						<input type="hidden" class="form-control" id="code" name="code" value="${subagent.code}" >
					Mail Id:	${subagent.mailId}
												
						<input type="hidden" class="form-control" id="mailId" name="mailId" value="${subagent.mailId}">
						</h3></div>
	 
						 <div class="row">									
                           <div class="input-field col s12 m6 l6 ">
									
									<input type="hidden" value="${subagent.salutation}" id="sal1"  />
									<select  name="salutation"  id="salutation"  style="width:100%">   <!-- selected="true" disabled="disabled" -->
										<option   value="">- Select Salutation -</option>
									<option value="Miss">Miss</option>
									<option value="Mr">Mr</option>
									<option value="Mrs">Mrs</option>
									<option value="Dr">Dr</option>
									<option value="Dato"> Dato</option>
									<option value="Datin"> Datin</option> 
									</select>	
									<label>Salutation</label>								
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label  for="Name">Name</label>
								<input type="text" id="name" name="name" value="${subagent.name}" >
								</div>
							</div>	
							



                  <div class="row">									
                           <div class="input-field col s12 m6 l6 ">
									<label  for="Name">Address1</label>
								<input type="text"  id="addr1" name="addr1" value="${subagent.addr1}" >
								</div>
							 <div class="input-field col s12 m6 l6 ">
									<label  for="Name">Address2</label>
								<input type="text" id="addr2" name="addr2" value="${subagent.addr2}" >
								</div>
							 <div class="input-field col s12 m6 l6 ">
								<label  for="Name">City</label>
							<%-- 	<label for="Name"  >${subagent.Name}</label> --%>						
						<input type="text" id="txtMid" name="city" value="${subagent.city}" >
						
						
						</div></div>	
					
						
						

							<div class="row">									
                           <div class="input-field col s12 m6 l6 ">
								<label  for="Name">Post Code</label>
							<%-- 	<label for="Name"  >${subagent.Name}</label> --%>						
						<input type="text"  id="txtMid" name="postCode" value="${subagent.postCode}" >
						
						
						</div>
						<div class="input-field col s12 m6 l6 ">
								
						<input type="hidden" value="${subagent.state}" id="state1"/>
							<select  name="state" id="state" style="width:100%">
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
								<label  for="Name">State</label>
						
						</div></div>	
						
					
					
					
					<div class="row">	

							<div class="input-field col s12 m6 l6 ">
								<label  for="Name">Phone Number</label>
							<%-- 	<label for="Name"  >${subagent.Name}</label> --%>						
						<input type="text" class="form-control" id="txtMid" name="phoneNo" value="${subagent.phoneNo}" >
						
						
						</div>
						<div class="input-field col s12 m6 l6 ">
								
								<input type="hidden" value="${subagent.type}" id="type"/>
								
						<select   id="agtype1" name="type" style="width:100%">  <%-- value="${subagent.type} --%>
									
									<option value="<%=AgentUserRole.SUBAGENT.name()%>">SUBAGENT</option>
								</select>
								<label  for="Name">Ag Type</label>
						
						
						</div>
						<div class="input-field col s12 m6 l6 ">
								
								<input type="hidden" name="AgentUserName" id="AgentUserName" value="${agentName}"/>
								
						<select  name="agentName" id="agentName" style="width:100%"> <%-- value="${subagent.type} --%>
									
								<c:forEach items="${agentNameList}" var="agentName">
								<option selected value="${agentName}">${agentName}</option>
			</c:forEach>
			</select>
			<label  for="Name">Agent Name</label>
					</div>		
						
						
								
						
						</div>	
		

			<div class="row" >
						<div class="input-field col s12 m6 l6 ">
		<div class="button-class" style="float:left;">	
		 <button type="submit" class="btn btn-primary blue-btn" onclick=" return loadSelectData()" >Submit</button>
		 </div>	</div></div>
								
				<style>
						
				.select-wrapper .caret { fill: #005baa;}				
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				
				</style>			
		 </div>
	</div>
						
	</div>	
 
</div>
</form>
</div>

</body>
</html>
