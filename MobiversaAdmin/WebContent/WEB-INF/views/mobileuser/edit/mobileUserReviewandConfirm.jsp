<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@page import="com.mobiversa.common.bo.MID"%>

 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%> 
<html lang="en-US">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mobile User Summary</title>    
   <link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
        
 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
<script type="text/javascript">

function load()
{
	
// $('#demoSwal').click(function(){
      	swal({
      		title: "Are you sure? you want to edit this MobileUser Details",
      		text: "it will be edited..!",
      		type: "warning",
      		showCancelButton: true,
      		confirmButtonText: "Yes, edit it!",
      		cancelButtonText: "No, cancel!",
      		closeOnConfirm: false,
      		closeOnCancel: false
      	}, function(isConfirm) {
      		if (isConfirm) {
      			//swal("Added!", "Your agent details added.", "success");
      			$("#form-edit").submit();
      		} else {
      			//swal("Cancelled", "Your agent details not added", "error");
      			 var url = "${pageContext.request.contextPath}/mobileUser/editMobileUser"; 
		$(location).attr('href',url);
      			
      		}
      	});
   //   });
}
   </script>  
   
   <script type="text/javascript">
function load1(){
	var url = "${pageContext.request.contextPath}/mobileUser/list/1";
	$(location).attr('href',url);
} 

</script> 

  </head>
  <body>
<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Mobile User  </strong>   &nbsp;&nbsp;<small>  Mobile User Summary</small> </h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
<form action="${pageContext.request.contextPath}/<%=MobileUserController.URL_BASE%>/editMobileUserReviewandConfirm" method="post" id="form-edit" 
commandName="mobileUser">

<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
 <input type="hidden" name="tid" value="${mobileUser.tid}" />

	<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">

		        
      <div class="row">
					
							
							
							<div class="input-field col s12 m6 l6 ">
									<label for="firstName">Activation Date</label>
									 <input type="text"
							id="txtMid" name="code" value="${mobileUser.activationDate}" readonly>

								</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="firstName">Mobile UserName</label>
									<input type="text"
							id="txtMid" name="firstName" value="${mobileUser.mobileUserName}" readonly>

								</div>
							

</div>


<div class="row">
							<div class="input-field col s12 m6 l6 ">
									<label for="contact">Status</label>
									 <input type="text" 
							id="txtMid" name="contact" value="${mobileUser.status}" readonly>
								</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="email">Renewal Date</label>
									<input type="text"
							id="email" name="email" value="${mobileUser.renewalDate}" readonly>
									
								</div>
							
</div>	
<div class="row">						
							<div class="input-field col s12 m6 l6 ">
								<label for="Renewal Period">Renewal Period</label>
								<input type="text"  name="renewalPeriod" id="renewalPeriod" value="1" onblur="checkRenewal()"
								onchange="validateRenewal()" readonly/>
								
							</div>
							<div class="input-field col s12 m6 l6 ">
									<label for="email">Expiry Date <font style="color:red;">*${responseErr}</font></label>
									 <input type="text" 
							id="preAuth" name="preAuth" value="${mobileUser.expiryDate}" readonly>
								</div>
							

</div>



<div class="row">														
                           
     						<div class="input-field col s12 m6 l6 ">
									<label for="email">Remarks</label>
									<input type="text"  id="remarks" name="remarks" value="${mobileUser.remarks}" readonly />
								
							</div>
                           <div class="input-field col s12 m6 l6 ">
                            		<label for="Pre_Auth">Pre-Auth</label>

                            		<input type="hidden" id="testAuth" value="${mobileUser.preAuth}"/>
                                   <input type="text" 
							id="preAuth" name="preAuth" value="${mobileUser.preAuth}" readonly>
							
      </div>
     
      </div>
     
     <div class="row">														
                           
                          <div class="input-field col s12 m6 l6 ">
                            		<label for="Boost_Enable">Boost Enable</label>
                            		<input type="text" 
							id="enableBoost" name="enableBoost" value="${mobileUser.enableBoost}" readonly>
     			 </div>
    <div class="input-field col s12 m6 l6 ">
                            		<label for="Moto_Enable">EZYMOTO Enable</label>

                            		
   								<input type="text" 
							id="enableMoto" name="enableMoto" value="${mobileUser.enableMoto}" readonly>
							
    
      
		 
        </div></div>
             <div class="row">														
                           
                           <div class="input-field col s12 m6 l6 ">
                           <div class="button-class">
                           <button type="submit" id="demoSwal" onclick="load()" class="btn btn-primary blue-btn">Confirm</button> <!-- id="testing" -->

							<button type="button"  onclick="load1()" class="btn btn-primary blue-btn">Cancel</button><!--  id="testing1" -->
                           </div>
                           </div>
                           
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
  </div> 
  </form>
  </div>
           



</body> 
</html>