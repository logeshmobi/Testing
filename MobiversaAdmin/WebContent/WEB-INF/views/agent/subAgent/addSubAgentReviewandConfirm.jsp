<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@page import="com.mobiversa.payment.controller.AgentSubController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <html>

<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>


<script type="text/javascript">
function load1()
 {
	var url = "${pageContext.request.contextPath}/agent5/addSubAgent";
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
	
// $('#testing').click(function(){
	 
	
	 swal({
   		title: "Are you sure? you want to Add this SubAgent Details",
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
      			
      		
      			
      			
      			//swal("Added!", "Your agent details added","success");
      			$("#form-add").submit();
      			
      			
      		} else {
      			/* swal("Cancelled", "Your agent details not added", "error"); */
      			 var url = "${pageContext.request.contextPath}/agent5/list/1"; 
		$(location).attr('href',url);
      			//return true;
      		}
      	});
     // });
 
}
</script>
  
<body>
<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Add SubAgent Details</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>

<form:form action="agent5/subAgentDetailsReviewAndConfirm" method="post" commandName="subagent" id="form-add">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

     
    <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		
      
      <div class="row">
							<div class="input-field col s12 m6 l6 ">
									<label for="mailId">Mail Id</label>
									
									<input type="text"  id="mailId"
										placeholder="mailId" name="mailId" path="mailId"  value= "${subagent.mailId}" readonly="readonly"/>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="salutation">Salutation</label>
									
										<input type="text"  id="salutation"
										placeholder="salutation" name="salutation" path="salutation"  value= "${subagent.salutation}" readonly="readonly"/>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="name">Name</label>
									
									<input type="text" id="name"
										placeholder="name" name="name" path="name"  value= "${subagent.name}" readonly="readonly"/>
									</div>
									</div>
									
									
							<div class="row">		
									
									<div class="input-field col s12 m6 l6 ">
									<label for="addr1">Address1</label>
									
									<input type="text"  id="addr1"
										placeholder="addr1" name="addr1" path="addr1"  value= "${subagent.addr1}" readonly="readonly"/>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="addr2">Address2</label>
									
									<input type="text" id="addr2"
										placeholder="addr2" name="addr2" path="addr2"  value= "${subagent.addr2}" readonly="readonly"/>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="city">City</label>
									
										<input type="text"  id="city"
										placeholder="city" name="city" path="city"  value= "${subagent.city}" readonly="readonly"/>
									</div>
									</div>
								
	


<div class="row">		
									
									<div class="input-field col s12 m6 l6 ">
									<label for="postCode">Post Code</label>
									
										<input type="text"  id="postCode"
										placeholder="postCode" name="postCode" path="postCode"  value= "${subagent.postCode}" readonly="readonly"/>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="state">State</label>
									
									<input type="text"  id="state"
										placeholder="state" name="state" path="state"  value= "${subagent.state}" readonly="readonly"/>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="phoneNo">Phone Number</label>
									
									<input type="text" id="phoneNo"
										placeholder="phoneNo" name="phoneNo" path="phoneNo"  value= "${subagent.phoneNo}" readonly="readonly"/>
									</div>
									</div>
								




<div class="row">		
									
									<div class="input-field col s12 m6 l6 ">
									<label for="type">Ag Type</label>
									
										<input type="text"  id="type"
										placeholder="type" name="type" path="type"  value= "${subagent.type}" readonly="readonly"/>
									</div>
									<div class="input-field col s12 m6 l6 ">
									<label for="agentName">Agent Name</label>
									
										<input type="text" id="agentName"
										placeholder="agentName" name="agentName" path="agentName"  value= "${subagent.agentName}" readonly="readonly"/>
									</div>
									</div>
									
									<div class="row">
						<div class="input-field col s12 m6 l6 ">

<div class="button-class"  style="float:left;">		  
 
      <button type="button" id="testing" class="btn btn-primary blue-btn" onclick="return load()">Confirm</button>
   
      <button type="button" class="btn btn-primary blue-btn" id="testing1" onclick="load1()">Cancel</button>
  </div></div></div>
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
	</form:form>
	</div>
	
	
 </body>
 </html>



		

 

	