<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.mobiversa.payment.controller.MasterMerchantController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>




 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	
	
	</style>

<script type="text/javascript">
function load1()
 {
	var url = "${pageContext.request.contextPath}/masterMerchant/addMasterMerchant";
 $(location).attr('href',url);
 }
</script>
<script type="text/javascript">
function load()
{

 swal({
   		title: "Are you sure? you want to add this Master Merchant Details",
   		text: "it will be added..!",
   		type: "warning",
   		showCancelButton: true,
   		confirmButtonText: "Yes, add it!",
   		cancelButtonText: "No, cancel!",
   		closeOnConfirm: false,
   		closeOnCancel: false,
   	   confirmButtonClass: 'btn btn-success',
   	  cancelButtonClass: 'btn btn-danger',
      		
      	}, function(isConfirm) {
      		if (isConfirm) {
      			
      		
      			
      			
      			
      			$("#form-add").submit();
      			
      			
      		} else {
      			
      			 var url = "${pageContext.request.contextPath}/masterMerchant/list"; 
		$(location).attr('href',url);
      			
      		}
      	}); 
    
 
}
</script>


  <script type="text/javascript">
  function disableRow(){

 // alert("test123");
  
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
		
		/* document.getElementById("owner22").style.display = 'none';
		document.getElementById("owner3").style.display = 'none';
		document.getElementById("owner33").style.display = 'none';
		document.getElementById("owner4").style.display = 'none';
		document.getElementById("owner44").style.display = 'none';
		document.getElementById("owner5").style.display = 'none';
		document.getElementById("owner55").style.display = 'none'; */
}
  </script> 

 
 

<body onload="disableRow()">

<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Add Master Merchant Details</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
    


<form:form action="masterMerchant/masterMerchantDetailsReviewAndConfirm" method="post" commandName="masterMerchant" id="form-add">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

    <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

 <div class="row">
 <div class="input-field col s12 m6 l6 ">
									<label for="salutation">Mail Id</label>
								<%-- 	${agent.salutation} --%>
									<input type="text" id="firstName"
										placeholder="mailId" name="mailId" path="mailId"  value= "${masterMerchant.mailId}" readonly="readonly"/>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="salutation">Salutation</label>
								<%-- 	${agent.salutation} --%>
									<input type="text"  id="firstName"
										placeholder="firstName" name="firstName" path="firstName"  value= "${masterMerchant.salutation}" readonly="readonly"/>
									</div>
							<div class="input-field col s12 m6 l6 ">
								
									<label for="salutation">First Name</label>
									<%-- ${agent.firstName} --%>
									<input type="text" class="form-control" id="firstName"
										placeholder="firstName" name="firstName" path="firstName"  value= "${masterMerchant.firstName}" readonly="readonly"/>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="LastName">Last Name</label>
									<%-- ${agent.lastName} --%>
									<input type="text" class="form-control" id="lastName"
										placeholder="lastName" name="lastName" path="firstName"  value= "${masterMerchant.lastName}" readonly="readonly"/>
									</div>
									</div>
									
								
							 <div class="row">
 										<div class="input-field col s12 m6 l6 ">
									<label for="addr1">Address1</label>
									<%-- ${agent.addr1} --%>
									<%-- <input type="text" class="form-control" id="addr1"
										placeholder="addr1" name="addr1" path="addr1"  value= "${agent.addr1}" readonly="readonly"/> --%>
										
										<textarea  readonly="readonly" row="2"  column="2" max-length="120" class="form-control" 
										id="addr1" style="word-break:break-all;" name="addr1"  readonly="readonly">${masterMerchant.addr1}</textarea>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="addr2">Address2</label>
									<%-- ${agent.addr2} --%>
									<%-- <input type="text" class="form-control" id="firstName"
										placeholder="firstName" name="firstName" path="firstName"  value= "${agent.firstName}" readonly="readonly"/> --%>
										<textarea  readonly="readonly" row="2"  column="2" max-length="120" class="form-control" 
										id="addr2" style="word-break:break-all;" name="addr2"  readonly="readonly">${masterMerchant.addr2}</textarea>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="city">City</label>
									<%-- ${agent.city} --%>
									<input type="text" class="form-control" id="city"
										placeholder="city" name="city" path="city"  value= "${masterMerchant.city}" readonly="readonly"/>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="PostCode">Post Code</label>
									<%-- ${agent.postCode} --%>
									<input type="text" class="form-control" id="postCode"
										placeholder="postCode" name="postCode" path="postCode"  value= "${masterMerchant.postCode}" readonly="readonly"/>
									</div>
									</div>
									
									 <div class="row">
 										<div class="input-field col s12 m6 l6 ">
									<label for="phoneNo">Phone Number</label>
									<%-- ${agent.phoneNo} --%>
									<input type="text" class="form-control" id="phoneNo"
										placeholder="phoneNo" name="phoneNo" path="phoneNo"  value= "${masterMerchant.phoneNo}" readonly="readonly"/>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="state">State</label>
									<%-- ${agent.state} --%>
									<input type="text" class="form-control" id="state"
										placeholder="state" name="state" path="state"  value= "${masterMerchant.state}" readonly="readonly"/>
									</div>
									
									<%-- <div class="input-field col s12 m6 l6 ">
									<label for="agType">Ag Type</label>
									<input type="hidden" id="agType" value="${agent.agType}">
									${agent.agType}
									<input type="text" class="form-control" id="agType"
										placeholder="agType" name="agType" path="agType"  value= "${agent.agType}" readonly="readonly"/>
									</div> --%>
									
									<div class="input-field col s12 m6 l6 ">
									<label for="state">Agent Name</label>
									<%-- ${agent.state} --%>
									<input type="text" class="form-control" id="agentName"
										placeholder="agentName" name="agentName" path="agentName"  value= "${masterMerchant.agentName}" readonly="readonly"/>
									</div>
									
									</div>
									

<!-- <div class="row" style="display: none;" id="BankDetails1"> -->

<div class="row" id="BankDetails1">


							<div class="input-field col s12 m6 l6 ">
							
									<label for="bankName">Bank Name</label>
									<%-- ${agent.bankName} --%>
									<input type="text" class="form-control" id="bankName"
										placeholder="bankName" name="bankName" path="bankName"  value= "${masterMerchant.bankName}" readonly="readonly"/>
									
								</div>	
								
								
								<div class="input-field col s12 m6 l6 ">
									<label for="bankAcc">Bank Account</label>
									<%-- ${agent.bankAcc} --%>
									<input type="text" class="form-control" id="bankAcc"
										placeholder="bankAcc" name="bankAcc" path="bankAcc"  value= "${masterMerchant.bankAcc}" readonly="readonly"/>
									</div>
									<!-- </div> -->
								<div class="input-field col s12 m6 l6 ">
									<label for="nricAcc">Nric Account</label>
									<%-- ${agent.nricNo} --%>
									<input type="text" class="form-control" id="nricNo"
										placeholder="nricNo" name="nricNo" path="nricNo"  value= "${masterMerchant.nricNo}" readonly="readonly"/>
									</div>
									
								</div>	
								
								
							
<div class="row">
						<div class="input-field col s12 m6 l6 ">
			<div class="button-class"  style="float:left;">		  
   <input type="button" id="testing" class="btn btn-primary blue-btn" value="Confirm" onclick = "load()">
     <input type="button" id="" style="color:#fff !important" class="btn btn-primary blue-btn" value="Cancel" onclick="load1()"> 
     			</div></div></div>
	<style>
						
				.select-wrapper .caret { fill: #005baa;}
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>			</div></div></div>
						
			</div>
						</form:form>	
						</div>

 </body>
 </html>
						




		
	

	