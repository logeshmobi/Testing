<%@page	import="com.mobiversa.payment.controller.MerchantWebReaderController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>


<script type="text/javascript">
function load1()
 {
	var url = "${pageContext.request.contextPath}/readerweb/list/1";
	$(location).attr('href', url);
 }
</script>
<script type="text/javascript">
function load()
{
	
// $('#testing').click(function(){
	 
	
      	swal({
      		title: "Are you sure? you want to edit this reader  Details",
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
      			
       			//swal("Added!", "Your Merchant Promotion details added","success");
      			$("#form-edit").submit();
      			
      			
      		} else {
      			 //swal("Cancelled", "Your Merchant Promotion details not added", "error"); 
      			var url = "${pageContext.request.contextPath}/readerweb/list/1";
		$(location).attr('href', url);
      			//return true;
      		}
      	});
    //  });
 
}
</script>

<body>
<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Edit Reader Details</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>

	<form
		action="${pageContext.request.contextPath}<%=MerchantWebReaderController.URL_BASE%>/editReaderReviewandConfirm"
		method="post" id="form-edit" commandName="reader">

		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
            <div class="d-flex align-items-center">
				          
				          </div>
	
					
					
					<div class="row">		
									<div class="input-field col s12 m6 l6  ">
									<label for="PromoName">Device Id</label>
										<input type="text" id="deviceId" name="deviceId" value="${reader.deviceId}" readonly="readonly">	
									
				</div>
									
										<input type="hidden" id="olddeviceId" name="olddeviceId" value="${olddeviceId}" readonly="readonly">	
									
									





                        <div class="input-field col s12 m6 l6  ">
									<label for="PromoName">TID</label>
										<input type="text"  id="tid" name="tid" value="${reader.tid}" readonly="readonly">	
								
									</div>
									
									
									<div class="input-field col s12 m6 l6  ">
									<label for="PromoName">Mid</label>
										<input type="text"  id="merchantId" name="merchantId" value="${reader.merchantId}" readonly="readonly">	
									
									
									</div>
									<div class="input-field col s12 m6 l6  ">
					
									<label for="DeviceHolderName">DeviceHolder Name</label>
										<input type="text" id="contactName" name="contactName" value="${reader.contactName}" readonly="readonly">	
									
									</div>
									
									<div class="input-field col s12 m6 l6  ">
					
									<label for="ACTIVE STATUS">ACTIVE STATUS</label>
										<input type="text" id="activeStatus" name="activeStatus" value="${reader.activeStatus}" readonly="readonly">	
									</div>
									
			</div>	


<div class="row">		
		<div class="input-field col s12 m6 l6  ">
				<button type="button" id="testing" class="btn btn-primary icon-btn" onclick="return load()">Confirm</button>
				<!--id="testing"  -->

				<button type="button" class="btn btn-default icon-btn" onclick="load1()">Cancel</button>
				<!-- id="testing1" -->

		</div>
</div>

		</div>
</div>
	</form>
	
	</div>
</body>
</html>


