<%@page import="com.mobiversa.payment.controller.AgentSubMenuController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
 
<!--  <script src="http://code.jquery.com/jquery-1.10.2.js"   type="text/javascript"></script> -->
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

<script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "subagent/");
</script>

<script type="text/javascript">
function load()
{
	
 //$('#testing').click(function(){
	 
	
      	swal({
      		title: "Are you sure? you want to add this SubAgent Details",
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
      			 var url = "${pageContext.request.contextPath}/agent2/list";
 $(location).attr('href',url);
      			//return true;
      		}
      	});
    //  });
 
}


function load1()
{
	var url = "${pageContext.request.contextPath}/subagent/list";
	$(location).attr('href',url);
}
</script>



 <form:form action="${pageContext.request.contextPath}/subagent/subAgentDetailsReviewAndConfirm"  method="post" commandName="subagent" id="form-add" >
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
  
 <div class="content-wrapper">
         
        <div class="row">
	        <div class="col-md-12 formContianer">
             
              
             
              <h3 class="card-title">Add SubAgent Details</h3> 
              
            <div class="card">
       
			 <div class="row">
 
			 
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">Mail Id</label>
							
									<input type="text" class="form-control" id="mailId"
										placeholder="mailId" name="mailId" path="mailId"  value= "${subagent.mailId}" readonly="readonly"/>
									</div>
									</div>


<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">Salutation</label>
							
									<input type="text" class="form-control" id="salutation"
										placeholder="salutation" name="salutation" path="salutation"  value= "${subagent.salutation}" readonly="readonly"/>
									</div>
									</div>


                      <div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">Name</label>
							
									<input type="text" class="form-control" id="name"
										placeholder="name" name="name" path="name"  value= "${subagent.name}" readonly="readonly"/>
									</div>
									</div>
</div>

 <div class="row">
 
			 
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">Address1</label>
							
									<input type="text" class="form-control" id="addr1"
										placeholder="addr1" name="addr1" path="addr1"  value= "${subagent.addr1}" readonly="readonly"/>
									</div>
									</div>


							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">Address2</label>
							
									<input type="text" class="form-control" id="addr1"
										placeholder="addr1" name="addr1" path="addr1"  value= "${subagent.addr2}" readonly="readonly"/>
									</div>
									</div>

 
			 
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">City</label>
							
									<input type="text" class="form-control" id="city"
										placeholder="city" name="city" path="city"  value= "${subagent.city}" readonly="readonly"/>
									</div>
									</div>
</div>
 
			 <div class="row"> 
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">State</label>
							
									<input type="text" class="form-control" id="state"
										placeholder="state" name="state" path="state"  value= "${subagent.state}" readonly="readonly"/>
									</div>
									</div>
									
									
							<!-- 		
							</div>
 <div class="row">		 -->
	
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">Post Code</label>
							
									<input type="text" class="form-control" id="postCode"
										placeholder="postCode" name="postCode" path="postCode"  value= "${subagent.postCode}" readonly="readonly"/>
									</div>
									</div>
									
						
			 
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">Phone Number</label>
							
									<input type="text" class="form-control" id="phoneNo"
										placeholder="phoneNo" name="phoneNo" path="phoneNo"  value= "${subagent.phoneNo}" readonly="readonly"/>
									</div>
									</div>
									
										
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">Ag Type</label>
							
									<input type="text" class="form-control" id="type"
										placeholder="type" name="type" path="type"  value= "${subagent.type}" readonly="readonly"/>
									</div>
									</div>
									
									
									<div class="form-group col-md-4">
								<div class="form-group">
									<label for="salutation">Agent Name</label>
							
									<input type="text" class="form-control" id="agentName"
										placeholder="agentName" name="agentName" path="agentName"  value= "${subagent.agentName}" readonly="readonly"/>
									</div>
									</div>
</div>
</div>
</div>

   </div>
  <button type="button" onclick="load()" id="testing" class="btn btn-primary icon-btn">Confirm</button> <!-- id="testing" -->
      
    
      <button type="button" onclick="load1()" class="btn btn-default icon-btn">Cancel</button> <!-- id="testing1" -->
 
    
    </div>
 </form:form>


</body>
</html>