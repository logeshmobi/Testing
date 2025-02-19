<%@page import="com.mobiversa.payment.controller.AgentSubController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


 <!-- <script src="http://code.jquery.com/jquery-1.10.2.js"   type="text/javascript"></script> -->
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>

<script type="text/javascript">
function load1()
 {
 var url = "${pageContext.request.contextPath}/agent5/list";
 $(location).attr('href',url);
 }
</script>
<script type="text/javascript">
function load()
{
	
// $('#testing').click(function(){
	 
	
      	swal({
      		title: "Are you sure? you want to edit this SubAgent Details",
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
      			/* swal("Cancelled", "Your agent details not added", "error"); */
      			 var url = "${pageContext.request.contextPath}/agent5/list"; 
		$(location).attr('href',url);
      			//return true;
      		}
      	});
      //});
 
}
</script>


<body>

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
    
   
<form action="${pageContext.request.contextPath}<%=AgentSubController.URL_BASE%>/editReviewandConfirm" method="post" id="form-edit">

 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />

 <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
                             <div class="d-flex align-items-center">
          		 <h3 class="text-white">  SubAgCode: ${subagent.code}				
						<input type="hidden" class="form-control" id="code" name="code" value="${subagent.code}" >
					MailId:	${subagent.mailId}
												
						<input type="hidden" class="form-control" id="mailId" name="mailId" value="${subagent.mailId}">
		</h3></div>
            

<div class="row">									
                            <div class="input-field col s12 m6 l6 ">
									<label  for="salutation">Salutation</label>
								<input type="text" id="txtMid" name="salutation" value="${subagent.salutation}" readonly>	
								</div>
							 <div class="input-field col s12 m6 l6 ">
									<label  for="name">Name</label>
								<input type="text" id="txtMid" name="name" value="${subagent.name}" readonly>	
									
								
								</div>
							</div>	
							

						<div class="row">									
                            <div class="input-field col s12 m6 l6 ">
									<label  for="addr1">Address1</label>
									<input type="text" id="txtMid" name="addr1" value="${subagent.addr1}" readonly>	
									
								</div>
								 <div class="input-field col s12 m6 l6 ">
									<label  for="addr2">Address2</label>
								<input type="text"  id="txtMid" name="addr2" value="${subagent.addr2}" readonly>
									
								</div>
								 <div class="input-field col s12 m6 l6 ">
									<label  for="city">City</label>
									<input type="text"id="txtMid" name="city" value="${subagent.city}" readonly>
									<%-- ${subagent.city} --%>
								</div>
								 <div class="input-field col s12 m6 l6 ">
									<label  for="postCode">PostCode</label>
									<input type="text" id="txtMid" name="postCode" value="${subagent.postCode}" readonly>
									<%-- ${subagent.postCode} --%>
								</div></div>
								
						
						
						
						<div class="row">	
						
						 <div class="input-field col s12 m6 l6 ">
									<label  for="state">State</label>
									<input type="text"  id="txtMid" name="state" value="${subagent.state}" readonly>
									<%-- ${subagent.state} --%>
								</div>
								 <div class="input-field col s12 m6 l6 ">
									<label  for="phoneNo">PhoneNumber</label>
									<input type="text" id="txtMid" name="phoneNo" value="${subagent.phoneNo}" readonly>
									<%-- ${subagent.phoneNo} --%>
								</div>
								 <div class="input-field col s12 m6 l6 ">
									<label  for="addr2">AgType</label>
									<input type="text" id="txtMid" name="type" value="${subagent.type}" readonly>
									<%-- ${subagent.type} --%>
								</div>
						 <div class="input-field col s12 m6 l6 ">
									<label  for="addr2">AgentName</label>
									<input type="text"  id="txtMid" name="agentName" value="${subagent.agentName}" readonly>
									<%-- ${subagent.agentName} --%>
								</div></div>
		<div class="row" >
						<div class="input-field col s12 m6 l6 ">				
     <div class="button-class" style="float:left;">	
      <input type="button" id="testing" class="btn btn-primary blue-btn" value="Confirm" onclick = "load()">
     <input type="button" id="" class="export-btn waves-effect waves-light btn btn-round indigo" value="Cancel" onclick="load1()">
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
 
   </form>  
   </div> 
 
  </body>

