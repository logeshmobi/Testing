
<%@page import="com.mobiversa.payment.controller.AdminAgentController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>

<script type="text/javascript">
function load1()
 {
 var url = "${pageContext.request.contextPath}/agent1/list";
 $(location).attr('href',url);
 }
</script>

 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
<script type="text/javascript">
function load()
{
	

	 
	
      	swal({
      		title: "Are you sure? you want to edit this Agent Details",
      		text: "it will be edited..!",
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
      			
       			//swal("Added!", "Your agent details added","success");
      			$("#form-edit").submit();
      			
      			
      		} else {
      			// swal("Cancelled", "Your agent details not added", "error"); 
      			 var url = "${pageContext.request.contextPath}/agent1/list"; 
		$(location).attr('href',url);
      			//return true;
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
		document.getElementById("BankDetails2").style.display = 'none';
		
	} else if(i == "AGENT")
	{
	document.getElementById("BankDetails").style.display = '';
	
	document.getElementById("BankDetails1").style.display = '';
	document.getElementById("BankDetails2").style.display = '';
	
	}
		
}


</script>

   <body  onload="disableRow()"> 
   <div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Agent Summary</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
    
    
<form action="${pageContext.request.contextPath}<%=AdminAgentController.URL_BASE%>/editReviewandConfirm" method="post" id="form-edit">

<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

        
       <div class="d-flex align-items-center">
          		 <h3 class="text-white">  Ag Code: ${agent.code}				
						<input type="hidden" class="form-control" id="code" name="code" value="${agent.code}" >
					Mail Id:	${agent.mailId}
												
						<input type="hidden" class="form-control" id="mailId" name="mailId" value="${agent.mailId}">
						</h3>
           

						
		<div class="row">									
                            <div class="input-field col s12 m6 l6 ">
									<label for="salutation">Salutation</label>
									<input type="text" id="txtMid" name="salutation" value="${agent.salutation}" readonly="readonly">	
									<%-- ${agent.salutation} --%>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="salutation">First Name</label>
										<input type="text" class="form-control" id="firstName" name="firstName" value="${agent.firstName}" readonly="readonly">	
									<%-- ${agent.firstName} --%>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="LastName">Last Name</label>
										<input type="text" class="form-control" id="lastName" name="lastName" value="${agent.lastName}" readonly="readonly">	
									<%-- ${agent.lastName} --%>
									</div>
									</div>		
									
									
								
				  <div class="row">							
									
									<div class="input-field col s12 m6 l6 ">
									<label for="addr1">Address1</label>
										<input type="text" class="form-control" id="addr1" name="addr1" value="${agent.addr1}" readonly="readonly">	
									<%-- ${agent.addr1} --%>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="addr2">Address2</label>
										<input type="text" class="form-control" id="addr2" name="addr2" value="${agent.addr2}" readonly="readonly">	
									<%-- ${agent.addr2} --%>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="city">City</label>
										<input type="text" class="form-control" id="city" name="city" value="${agent.city}" readonly="readonly">	
									<%-- ${agent.city} --%>
									</div>
									</div>
									
							
					<div class="row">	
					
					
					<div class="input-field col s12 m6 l6 ">
									<label for="PostCode">Post Code</label>
										<input type="text" class="form-control" id="postCode" name="postCode" value="${agent.postCode}" readonly="readonly">	
									<%-- ${agent.postCode} --%>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="state">State</label>
										<input type="text" class="form-control" id="state" name="state" value="${agent.state}" readonly="readonly">	
									<%-- ${agent.state} --%>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="phoneNo">Phone Number</label>
										<input type="text" class="form-control" id="phoneNo" name="phoneNo" value="${agent.phoneNo}" readonly="readonly">	
									<%-- ${agent.phoneNo} --%>
									</div>
									</div>
						
							
						
						<!--  <div class="card-body formbox"> -->	
						<div class="row">	
							<div class="input-field col s12 m6 l6 ">
									<label for="agType">Ag Type</label>
									<input type="text" class="form-control" id="agType" value="${agent.agType}" readonly="readonly">
									
									<%-- ${agent.agType} --%>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="agType">Status</label>
									<input type="text" class="form-control" id="status" value="${agent.status}" readonly="readonly">
									
									<%-- ${agent.agType} --%>
									</div>
									</div>
									
							<div class="input-field col s12 m6 l6 ">
							<div  style="display: none;" id="BankDetails">
								
									<label for="bankName">Bank Name</label>
										<input type="text" class="form-control" id="bankName" name="bankName" value="${agent.bankName}" readonly="readonly">	
									<%-- ${agent.bankName} --%>
									</div></div>
									<div class="input-field col s12 m6 l6 ">
							<div  style="display: none;" id="BankDetails1">
								
								<label for="bankName">Bank Account</label>
	<input type="text" class="form-control" id="bankAcc" name="bankAcc" value="${agent.bankAcc}" readonly="readonly">	
									<%-- ${agent.bankAcc} --%>
									</div>
									
								</div>	
						</div>		
					
							
							<div class="row">
								<div class="input-field col s12 m6 l6 ">
							<div  style="display: none;" id="BankDetails2">
								
									<label for="nricAcc">Nric Account</label>
										<input type="text" class="form-control" id="nricNo" name="nricNo" value="${agent.nricNo}" readonly="readonly">	
									<%-- ${agent.nricNo} --%>
									</div>
									</div>
								</div>	
								
								
										
	


     <div class="row" >
						<div class="input-field col s12 m6 l6 ">
     <div class="button-class" style="float:left;">	
      <input type="button" id="testing" class="btn btn-primary blue-btn" value="Confirm" onclick="load()">
     <input type="button" id="" class="export-btn waves-effect waves-light btn btn-round indigo" value="Cancel" onclick="load1()">
   </div></div></div></div>
   </div></div>
   <style>
						
				.select-wrapper .caret { fill: #005baa;}				
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>	
    
   
   </div>
  </form>
  </div>

</body>







 