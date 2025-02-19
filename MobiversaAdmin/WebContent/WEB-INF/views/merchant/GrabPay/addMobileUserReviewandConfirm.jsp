<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- <script src="http://code.jquery.com/jquery-1.10.2.js"   type="text/javascript"></script> -->
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
	 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/main.js"></script> 
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/sweetalert.min.js"></script>
<style>

</style>

 <script type="text/javascript">
function load1(){
	var url = "${pageContext.request.contextPath}/mobileUser/addMobileUser/1";
	$(location).attr('href',url);
} 

</script>
 
 <script type="text/javascript">
 
	function disableRow() {

		// alert("test123");

		var i = document.getElementById("connectType").value;
		//alert(i);

		/* if (i == "WIFI") {
			//document.getElementById("agType").value;

			document.getElementById("preAuth1").style.display = 'none';
			//document.getElementById("BankDetails1").style.display = 'none';

		} else if (i == "BT") {
			document.getElementById("preAuth1").style.display = '';

			//document.getElementById("BankDetails1").style.display = '';

		} */

		/* document.getElementById("owner22").style.display = 'none';
		document.getElementById("owner3").style.display = 'none';
		document.getElementById("owner33").style.display = 'none';
		document.getElementById("owner4").style.display = 'none';
		document.getElementById("owner44").style.display = 'none';
		document.getElementById("owner5").style.display = 'none';
		document.getElementById("owner55").style.display = 'none'; */
	}
	

function load()
{
	
 //$('#demoSwal').click(function(){
      	swal({
      		title: "Are you sure? you want to add this MobileUser ",
      		text: "it will be added..!",
      		type: "warning",
      		showCancelButton: true,
      		confirmButtonText: "Yes, add it!",
      		cancelButtonText: "No, cancel!",
      		closeOnConfirm: false,
      		closeOnCancel: false
      	}, function(isConfirm) {
      		if (isConfirm) {
      			//swal("Added!", "Your agent details added.", "success");
      			$("#form-add").submit();
      		} else {
      			//swal("Cancelled", "Your agent details not added", "error");
      			 var url = "${pageContext.request.contextPath}/mobileUser/addMobileUser"; 
		$(location).attr('href',url);
      			
      		}
      	});
    //  });
}
   </script>   
<body onload="">
	
 <form:form action="mobileUserDetailsConfirm" method="post"
		commandName="mobileUser" id="form-add">
		 
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	<!-- <div style="overflow:auto;border:1px;width:50%;"> -->
	
	
	
	<div class="content-wrapper">
	<div class="row">
		<div class="col-md-12 formContianer">
			<h3 class="card-title">Merchant Details </h3>
            <div class="card">
				<div class="row">
					<div class="form-group col-md-4">
						<div class="form-group">
						<label for="mailId">Business Name</label>
  							<input type="text"
							class="form-control" id="merchantName" name="merchantName" path="merchantName"
							value="${mobileUser.merchantName}" readonly>

 						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-4">
						<div class="form-group">
							 <label for="mailId">Contact Number</label> <input type="text"
										class="form-control" id="contactNo" name="contactNo" path="contactNo"
										value="${mobileUser.contactNo}" readonly>


						 </div>
					</div>
          			<div class="form-group col-md-4">
						<div class="form-group">
								 <label for="mailId">Email</label> <input type="text"
										class="form-control" id="emailId" name="emailId" path="emailId"
										value="${mobileUser.emailId}" readonly>


						</div>
					</div>
					<div class="form-group col-md-4">
						<div class="form-group">
								<label for="contactName">Contact Name</label>

									<%-- <label  name="code" for="code"  >${subagent.code}</label> ${mobileUser.contactName}--%>
								<input type="text" class="form-control" id="contactName" path="contactName"
										name="contactName" value="${mobileUser.contactName}" readonly>


						</div>
					</div>

					<div class="form-group col-md-4">
						<div class="form-group">
								<label for="Expiry Date">Expiry Date</label> <input type="text"
										class="form-control" id="expiryDate" name="expiryDate" path="expiryDate"
										value="${mobileUser.expiryDate}" readonly>


						</div>
					</div>

					<div class="form-group col-md-4">
						<div class="form-group">
								<label for="Remarks">Remarks</label> <input type="text"
										class="form-control" id="remarks" name="remarks" path="remarks"
										value="${mobileUser.remarks}" readonly>


						</div>
					</div>
				</div>
			</div>
			<c:if test="${mobileUser.mid!=null && not empty mobileUser.tid}">
			<div class="card">
				<h3 class="card-title">MobileUser Ezywire Details <font style="color:blue;">Mid: ${mobileUser.mid}</font></h3>
				<input type="hidden" class="form-control" id="mid" name="mid" value="${mobileUser.mid}" 
				path="mid" readonly>
			    <div class="row">
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="tid">TID</label>
							<input type="text" class="form-control" id="tid" path="tid"
							name="tid" value="${mobileUser.tid}" readonly>


						</div>
					</div>
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="referenceNo">Device Id</label>
							<input type="text" class="form-control" id="deviceId" path="deviceId"
							name="deviceId" value="${mobileUser.deviceId}" readonly>
						</div>
					</div>
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="referenceNo">Reference Id</label>
							<input type="text" class="form-control" id="referenceNo" path="referenceNo"
							name="referenceNo" value="${mobileUser.referenceNo}"readonly>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="preAuth">Pre-Auth</label>
							<input type="text" class="form-control" id="preAuth" name="preAuth" path="preAuth"
							 value="${mobileUser.preAuth}" readonly>
						</div>
					</div>
				</div>
				
					
				
			</div>
</c:if>
			
			<c:if test="${mobileUser.motoMid != null && not empty mobileUser.motoTid}">	
			<div class="card">
				<h3 class="card-title">MobileUser EzyMoto Details   <font style="color:blue;">Mid: ${mobileUser.motoMid}</font></h3>
				<input type="hidden" class="form-control" path="motoMid" id="motoMid" name="motoMid" value="${mobileUser.motoMid}" readonly>
				<div class="row">
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="motoTid">TID</label>
							<input type="text" class="form-control" path="motoTid" id="motoTid" name="motoTid" value="${mobileUser.motoTid}" readonly>

						</div>
					</div>
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="motodeviceId">Device Id</label>
							<input type="text" class="form-control" path="motodeviceId" id="motodeviceId" name="motodeviceId" value="${mobileUser.motodeviceId}" readonly>
						</div>
					</div>

			
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="motorefNo">Reference Id</label>
							<input type="text" class="form-control" id="motorefNo" name="motorefNo" path="motorefNo"
							 value="${mobileUser.motorefNo}"readonly>


						</div>
					</div>
					
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="motopreAuth">Pre-Auth</label>
							<input type="text" class="form-control" id="motopreAuth" name="motopreAuth" path="motopreAuth"
							 value="${mobileUser.motopreAuth}" readonly>
						</div>
					</div>
				
				</div>
			</div>
			</c:if>	
			
			<c:if test="${mobileUser.ezywayMid != null && not empty mobileUser.ezywayTid}">	
			<div class="card">
				<h3 class="card-title">MobileUser Ezyway Details   <font style="color:blue;">Mid: ${mobileUser.ezywayMid}</font></h3>
				<input type="hidden" class="form-control" path="ezywayMid" id="ezywayMid" name="ezywayMid" 
						value="${mobileUser.ezywayMid}" readonly>
				<div class="row">
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="ezywayTid">TID</label>
							<input type="text" class="form-control" path="ezywayTid" id="ezywayTid" name="ezywayTid" 
							value="${mobileUser.ezywayTid}" readonly>

						</div>
					</div>
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="ezywaydeviceId">Device Id</label>
							<input type="text" class="form-control" path="ezywaydeviceId" id="ezywaydeviceId" name="ezywaydeviceId" 
							value="${mobileUser.ezywaydeviceId}" readonly>
						</div>
					</div>

			
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="ezywayrefNo">Reference Id</label>
							<input type="text" class="form-control" id="ezywayrefNo" name="ezywayrefNo" path="ezywayrefNo"
							 value="${mobileUser.ezywayrefNo}"readonly>


						</div>
					</div>
					
					<%-- <div class="form-group col-md-4">
						<div class="form-group">
							<label for="motopreAuth">Pre-Auth</label>
							<input type="text" class="form-control" id="motopreAuth" name="motopreAuth" path="motopreAuth"
							 value="${mobileUser.motopreAuth}" readonly>
						</div>
					</div> --%>
				
				</div>
			</div>
			</c:if>	
			
			
			<c:if test="${mobileUser.ezyrecMid != null && not empty mobileUser.ezyrecTid}">	
			<div class="card">
				<h3 class="card-title">MobileUser EzyRec Details   <font style="color:blue;">Mid: ${mobileUser.ezyrecMid}</font></h3>
				<input type="hidden" class="form-control" path="ezyrecMid" id="ezyrecMid" name="ezyrecMid" value="${mobileUser.ezyrecMid}" readonly>
				<div class="row">
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="ezyrecTid">TID</label>
							<input type="text" class="form-control" path="ezyrecTid" id="ezyrecTid" name="ezyrecTid" 
							value="${mobileUser.ezyrecTid}" readonly>

						</div>
					</div>
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="ezyrecdeviceId">Device Id</label>
							<input type="text" class="form-control" path="ezyrecdeviceId" id="ezyrecdeviceId" name="ezyrecdeviceId" 
							value="${mobileUser.ezyrecdeviceId}" readonly>
						</div>
					</div>

			
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="ezyrecrefNo">Reference Id</label>
							<input type="text" class="form-control" id="ezyrecrefNo" name="ezyrecrefNo" path="ezyrecrefNo"
							 value="${mobileUser.ezyrecrefNo}"readonly>


						</div>
					</div>
					
					<%-- <div class="form-group col-md-4">
						<div class="form-group">
							<label for="motopreAuth">Pre-Auth</label>
							<input type="text" class="form-control" id="motopreAuth" name="motopreAuth" path="motopreAuth"
							 value="${mobileUser.motopreAuth}" readonly>
						</div>
					</div> --%>
				
				</div>
				<div class="row">
				<div class="form-group col-md-4">
						<div class="form-group">
							<label for="ezypod">EzyPOD</label>
							<input type="text" class="form-control" id="ezypod" name="ezypod" path="ezypod"
							 value="${mobileUser.ezypod}" readonly>
						</div>
					</div>
				</div>
			</div>
			</c:if>	
			
			
			
			
			<c:if test="${mobileUser.ezypassMid != null && not empty mobileUser.ezypassTid}">					
				<div class="card">
					<h3 class="card-title">MobileUser EzyPass Details   <font style="color:blue;">Mid: ${mobileUser.ezypassMid}</font></h3>
					<input type="hidden" class="form-control" path="ezypassMid" id="ezypassMid" name="ezypassMid" value="${mobileUser.ezypassMid}" readonly>
					<div class="row">
						<div class="form-group col-md-4">
							<div class="form-group">
								<label for="ezypassTid">TID</label>
								<input type="text" class="form-control" path="ezypassTid" id="ezypassTid" name="ezypassTid" value="${mobileUser.ezypassTid}" readonly>
							</div>
						</div>
						<div class="form-group col-md-4">
							<div class="form-group">
								<label for="ezypassdeviceId">Device Id</label>
								<input type="text" class="form-control" path="ezypassdeviceId" id="ezypassdeviceId" name="ezypassdeviceId" value="${mobileUser.ezypassdeviceId}" readonly>
							</div>
						</div>

			
						<div class="form-group col-md-4">
							<div class="form-group">
								<label for="ezypassrefNo">Reference Id</label>
								<input type="text" class="form-control" id="ezypassrefNo" name="ezypassrefNo" path="ezypassrefNo" value="${mobileUser.ezypassrefNo}"readonly>


							</div>
						</div>
					</div>					

				</div>
						
			</c:if>
		</div>
		<input type="button" id="demoSwal" class="btn btn-primary icon-btn"   onclick="return load()" value="Confirm">
		
		
		<input type="button"  class="btn btn-default icon-btn"  onclick="load1()" value="Cancel">
	</div>
</div>


	
	</form:form>





</body>
</html>
