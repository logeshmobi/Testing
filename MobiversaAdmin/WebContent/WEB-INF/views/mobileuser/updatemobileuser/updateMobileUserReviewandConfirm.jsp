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
<link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>


 <script type="text/javascript">
function load1(){
	var url = "${pageContext.request.contextPath}/mobileUser/updateMobileUser";
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
      			 var url = "${pageContext.request.contextPath}/mobileUser/updateMobileUser"; 
		$(location).attr('href',url);
      			
      		}
      	});
    //  });
}
   </script>   
<body onload="">
<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Update Mobile User </strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
	
 <form:form action="mobileUser/mobileUserDetailsConfirmtoUpdate" method="post"
		commandName="mobileUser" id="form-add">
		 
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	<!-- <div style="overflow:auto;border:1px;width:50%;"> -->
	
	
	
		<div class="row">				
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        	<div class="row">
						<div class="input-field col s12 m6 l6 ">
				
						<label for="mailId">Business Name</label>
  							<input type="text"
							id="merchantName" name="merchantName" path="merchantName"
							value="${mobileUser.merchantName}" readonly>

 						</div>
					
				
					<div class="input-field col s12 m6 l6 ">
							 <label for="mailId">Contact Number</label> <input type="text"
										id="contactNo" name="contactNo" path="contactNo"
										value="${mobileUser.contactNo}" readonly>


						 </div>
					<div class="input-field col s12 m6 l6 ">
								 <label for="mailId">Email</label> <input type="text"
									 id="emailId" name="emailId" path="emailId"
										value="${mobileUser.emailId}" readonly>


						</div>
					<div class="input-field col s12 m6 l6 ">
								<label for="contactName">Contact Name</label>

									<%-- <label  name="code" for="code"  >${subagent.code}</label> ${mobileUser.contactName}--%>
								<input type="text" id="contactName" path="contactName"
										name="contactName" value="${mobileUser.contactName}" readonly>


						</div>
					<div class="input-field col s12 m6 l6 ">
								<label for="Expiry Date">Expiry Date</label> <input type="text"
										id="expiryDate" name="expiryDate" path="expiryDate"
										value="${mobileUser.expiryDate}" readonly>


						</div>
					<div class="input-field col s12 m6 l6 ">
								<label for="Remarks">Remarks</label> <input type="text"
									id="remarks" name="remarks" path="remarks"
										value="${mobileUser.remarks}" readonly>


						</div>
					</div>
				</div>
			</div></div></div>
			<c:if test="${mobileUser.mid != null && not empty mobileUser.tid}">
			<div class="row">				
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
         <div class="d-flex align-items-center">
				<h3>MobileUser Ezywire Details <font style="color:blue;">Mid: ${mobileUser.mid}</font></h3>
				</div>
				<input type="hidden"  id="mid" name="mid" value="${mobileUser.mid}" 
				path="mid" readonly>
			    <div class="row">
					<div class="input-field col s12 m6 l6 ">
							<label for="tid">TID</label>
							<input type="text" " id="tid" path="tid"
							name="tid" value="${mobileUser.tid}" readonly>


						</div>
					
					<c:if test="${mobileUser.updateType =='ezywire'}">
					<div class="input-field col s12 m6 l6 ">
							<label for="referenceNo">User Name</label>
							<input type="text" id="ezywiremobusername" path="ezywiremobusername"
							name="ezywiremobusername" value="${mobileUser.ezywiremobusername}" readonly>
						</div>
					
					</c:if>
					<c:if test="${mobileUser.updateType !='ezywire'}">
					<div class="input-field col s12 m6 l6 ">
							<label for="motodeviceId">Device Id</label>
							<input type="text"  path="deviceId" id="deviceId" name="deviceId" value="${mobileUser.deviceId}" readonly>
						</div>
					<div class="input-field col s12 m6 l6 ">
							<label for="motorefNo">Reference Id</label>
							<input type="text"  id="referenceNo" name="referenceNo"
							 path="referenceNo" value="${mobileUser.referenceNo}"readonly>


						</div>
					<div class="input-field col s12 m6 l6 ">
							<label for="preAuth">Pre-Auth</label>
							<input type="text" id="preAuth" name="preAuth"
							 path="referenceNo" value="${mobileUser.preAuth}"readonly>


						</div>
					
					
					</c:if>
				</div>
				</div></div>
			</div></div>
</c:if>

			
			<c:if test="${mobileUser.motoMid != null  && not empty mobileUser.motoTid}">	
			<div class="row">				
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
         <div class="d-flex align-items-center">
				<h3 >MobileUser EzyMoto Details   <font style="color:blue;">Mid: ${mobileUser.motoMid}</font></h3>
		</div>
				<input type="hidden"  path="motoMid" id="motoMid" name="motoMid" value="${mobileUser.motoMid}" readonly>
				<div class="row">
					<div class="input-field col s12 m6 l6 ">
							<label for="motoTid">TID</label>
							<input type="text" path="motoTid" id="motoTid" name="motoTid" value="${mobileUser.motoTid}" readonly>

						</div>
					
					<c:if test="${mobileUser.updateType =='moto'}">
					<div class="input-field col s12 m6 l6 ">
							<label for="motousername">User Name</label>
							<input type="text" path="motousername" id="motousername" 
							name="motousername" value="${mobileUser.motousername}" readonly>
						</div>
					
					</c:if>
					<c:if test="${mobileUser.updateType !='moto'}">
					<div class="input-field col s12 m6 l6 ">
							<label for="motodeviceId">Device Id</label>
							<input type="text"  path="motodeviceId" id="motodeviceId" name="motodeviceId" value="${mobileUser.motodeviceId}" readonly>
						
					</div>

			
					<div class="input-field col s12 m6 l6 ">
							<label for="motorefNo">Reference Id</label>
							<input type="text"  id="motorefNo" name="motorefNo" path="motorefNo" value="${mobileUser.motorefNo}"readonly>


						
					</div>
					<div class="input-field col s12 m6 l6 ">
							<label for="motopreAuth">Pre-Auth</label>
							<input type="text"  id="motopreAuth" name="motopreAuth"
							 path="motopreAuth" value="${mobileUser.motopreAuth}"readonly>


						</div>
					
					
					</c:if>
				</div>
			</div></div></div></div>
			</c:if>	
			
			<!-- start ezyrec -->
			<c:if test="${mobileUser.ezyrecMid != null && not empty mobileUser.ezyrecTid}">	
			<div class="row">				
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
         <div class="d-flex align-items-center">
				<h3>MobileUser EzyRec Details   <font style="color:blue;">Mid: ${mobileUser.ezyrecMid}</font></h3>
				</div>
				<input type="hidden" class="form-control" path="ezyrecMid" id="ezyrecMid" name="ezyrecMid" value="${mobileUser.ezyrecMid}" readonly>
				<div class="row">
					<div class="input-field col s12 m6 l6 ">
							<label for="ezyrecTid">TID</label>
							<input type="text" path="ezyrecTid" id="ezyrecTid" 
							name="ezyrecTid" value="${mobileUser.ezyrecTid}" readonly>

						</div>
					
					<c:if test="${mobileUser.updateType =='ezyrec'}">
					<div class="input-field col s12 m6 l6 ">
							<label for="ezyrecusername">User Name</label>
							<input type="text"  path="ezyrecusername" id="ezyrecusername" 
							name="ezyrecusername" value="${mobileUser.ezyrecusername}" readonly>
						</div>
					
					</c:if>
					<c:if test="${mobileUser.updateType !='ezyrec'}">
					<div class="input-field col s12 m6 l6 ">
							<label for="ezyrecdeviceId">Device Id</label>
							<input type="text"  path="ezyrecdeviceId" id="ezyrecdeviceId" 
							name="ezyrecdeviceId" value="${mobileUser.ezyrecdeviceId}" readonly>
						</div>
					<div class="input-field col s12 m6 l6 ">
							<label for="ezyrecrefNo">Reference Id</label>
							<input type="text"  id="ezyrecrefNo" name="ezyrecrefNo" 
							path="ezyrecrefNo" value="${mobileUser.ezyrecrefNo}"readonly>


						</div>
					
					
					</c:if>
				</div>
			</div></div></div></div>
			</c:if>	
			
			<!-- end ezyrec -->
			
			
			<c:if test="${mobileUser.ezypassMid != null  && not empty mobileUser.ezypassTid}">					
				<div class="row">				
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
         <div class="d-flex align-items-center">
					<h3>MobileUser EzyPass Details <font style="color:blue;">Mid: ${mobileUser.ezypassMid}</font></h3>
			</div>		
										
					<input type="hidden" path="ezypassMid" id="ezypassMid" name="ezypassMid" value="${mobileUser.ezypassMid}" readonly>
					<div class="row">
						<div class="input-field col s12 m6 l6 ">
								<label for="ezypassTid">TID</label>
								<input type="text" path="ezypassTid" id="ezypassTid" 
								name="ezypassTid" value="${mobileUser.ezypassTid}" readonly>
							</div>
						
						<c:if test="${mobileUser.updateType =='ezypass'}">
						<div class="input-field col s12 m6 l6 ">
							<label for="ezypassusername">User Name</label>
							<input type="text"  path="ezypassusername" id="ezypassusername" 
							name="ezypassusername" value="${mobileUser.ezypassusername}" readonly>
						</div>
					
					</c:if>
						<c:if test="${mobileUser.updateType !='ezypass'}">
						<div class="input-field col s12 m6 l6 ">
								<label for="ezypassdeviceId">Device Id</label>
								<input type="text" path="ezypassdeviceId" id="ezypassdeviceId" 
								name="ezypassdeviceId" value="${mobileUser.ezypassdeviceId}" readonly>
							</div>
						

			
						<div class="input-field col s12 m6 l6 ">
								<label for="ezypassrefNo">Reference Id</label>
								<input type="text" id="ezypassrefNo" name="ezypassrefNo" path="ezypassrefNo" value="${mobileUser.ezypassrefNo}"readonly>


							</div>
						
						</c:if>
					</div>					

				</div>
					</div></div></div>	
			</c:if>
			
			
			<!-- start umobile mid  -->
			<c:if test="${mobileUser.um_mid != null && not empty mobileUser.um_tid}">
			<div class="row">				
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
         <div class="d-flex align-items-center">
				<h3>MobileUser Ezywire Details <font style="color:blue;">Mid: ${mobileUser.um_mid}</font></h3>
				</div>
				<input type="hidden" id="um_mid" name="um_mid" value="${mobileUser.um_mid}" 
				path="um_mid" readonly>
			    <div class="row">
					<div class="input-field col s12 m6 l6 ">
							<label for="um_tid">TID</label>
							<input type="text" id="um_tid" path="um_tid"
							name="um_tid" value="${mobileUser.um_tid}" readonly>


						
					</div>
					<c:if test="${mobileUser.updateType =='ummobile'}">
					<div class="input-field col s12 m6 l6 ">
							<label for="referenceNo">User Name</label>
							<input type="text" id="ezywiremobusername" path="ezywiremobusername"
							name="ezywiremobusername" value="${mobileUser.ezywiremobusername}" readonly>
						</div>
					
					</c:if>
					<c:if test="${mobileUser.updateType !='ummobile'}">
					<div class="input-field col s12 m6 l6 ">
							<label for="motodeviceId">Device Id</label>
							<input type="text" path="um_deviceId" id="um_deviceId" 
							name="um_deviceId" value="${mobileUser.um_deviceId}" readonly>
						</div>
					<div class="input-field col s12 m6 l6 ">
							<label for="motorefNo">Reference Id</label>
							<input type="text" id="um_refNo" name="um_refNo"
							 path="um_refNo" value="${mobileUser.um_refNo}"readonly>


						</div>
					
					</c:if>
					</div>
				</div>
				</div></div>
			</div>
</c:if>


<c:if test="${mobileUser.um_motoMid != null}">	
			<div class="row">				
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
         <div class="d-flex align-items-center">
				<h3 >MobileUser UMEzyMoto Details   <font style="color:blue;">Mid: ${mobileUser.um_motoMid}</font></h3>
		</div>
		<input type="hidden" name="updateType" value="umobilemoto" path="updateType"/>
				<input type="hidden"  path="um_motoMid" id="um_motoMid" name="um_motoMid" value="${mobileUser.um_motoMid}" readonly>
				<div class="row">
									
					<div class="input-field col s12 m6 l6 ">
							<label for="motousername">User Name</label>
							<input type="text" path="motousername" id="motousername" 
							name="motousername" value="${mobileUser.motousername}" readonly>
						</div>
						<div class="input-field col s12 m6 l6 ">
						<label for="hashKey">HashKey</label>
						<input type="text" id="hashkey" value="${mobileUser.hashkey}"
						placeholder="hashkey" name="hashkey"  path="hashkey" readonly/>									
					</div>
					
					<div class="input-field col s12 m6 l6 ">
					<label for="DTL">DTL</label>
					<input type="text" id="DTL" value="${mobileUser.DTL}"
						placeholder="DTL" name="DTL"  path="DTL" readonly/>
					</div>
					
					
					
				</div>
			</div></div></div></div>
			</c:if>	
			
			<c:if test="${mobileUser.um_ezywayMid != null}">	
			<div class="row">				
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
         <div class="d-flex align-items-center">
				<h3 >MobileUser UMEzyway Details   <font style="color:blue;">Mid: ${mobileUser.um_ezywayMid}</font></h3>
		</div>
		<input type="hidden" name="updateType" value="umobileezyway" path="updateType"/>
				<input type="hidden"  path="um_ezywayMid" id="um_ezywayMid" name="um_ezywayMid" value="${mobileUser.um_ezywayMid}" readonly>
				<div class="row">
									
					<div class="input-field col s12 m6 l6 ">
							<label for="motousername">User Name</label>
							<input type="text" path="ezywayusername" id="ezywayusername" 
							name="ezywayusername" value="${mobileUser.ezywayusername}" readonly>
						</div>
						<div class="input-field col s12 m6 l6 ">
						<label for="hashKey">HashKey</label>
						<input type="text" id="hashkey1" value="${mobileUser.hashkey1}"
						placeholder="hashkey" name="hashkey1"  path="hashkey1" readonly/>									
					</div>
					
					<div class="input-field col s12 m6 l6 ">
					<label for="DTL">DTL</label>
					<input type="text" id="DTL1" value="${mobileUser.DTL1}"
						placeholder="DTL" name="DTL1"  path="DTL1" readonly/>
					</div>
					
					
					
				</div>
			</div></div></div></div>
			</c:if>	
	
			
		<div class="row">
				<div class="col s12 m4 l4"></div>
				 <div class="col s12 m4 l4">
				  <div class="button-class" style="float:left;">	
					<input type="button" id="demoSwal" class="btn btn-primary icon-btn"   onclick="return load()" value="Confirm">	
		<input type="button"  class="btn btn-default icon-btn"  onclick="load1()" value="Cancel">
					</div>
				<div class="col s12 m4 l4"></div>
				</div>  
                   
                   </div>
								
	<style>
						
				.select-wrapper .caret { fill: #005baa;}				
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>	
		
		
		<!-- 
		<input type="button" id="demoSwal" class="btn btn-primary icon-btn"   onclick="return load()" value="Confirm">
		
		
		<input type="button"  class="btn btn-default icon-btn"  onclick="load1()" value="Cancel">
	
 -->
	
	</form:form>
</div>






</body>
</html>
