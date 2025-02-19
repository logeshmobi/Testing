<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@page import="com.mobiversa.payment.controller.MDRDetailsController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en-US">
<head>
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<style>
  
  input:focus { 
    outline: none !important;
    border:1px solid red;
    box-shadow: 0 0 10px #719ECE;
}
</style>
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>

<!-- <script type="text/javascript">
	jQuery(document).ready(function() {
		$('#status1').select2();
	});
</script> -->
<script lang="JavaScript">

function loadSelectData(){ 
	
	
	var mobiMDR = document.getElementById("mobiMDR").value;
	

		if (mobiMDR == null || mobiMDR == '') {

			alert("Please Fill MOBI MDR");
			document.getElementById("mobiMDR").focus();
			return false;
		}
		

		return true;

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
           <h3 class="text-white">  <strong>Update Product MDR</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
	

	<form:form method="POST" id="form1" action="${pageContext.request.contextPath}/MDR/updateProMDRReview" name="form1" commandName="mobileUser">	
		<input type="hidden" class="form-control" path="id" id="id" readonly name="id" Placeholder="id" value="${mdrObj.id}"/>
	<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
				           <h5> MDR Details</h5>
				          </div>
					<div class="row">

					<div class="input-field col s12 m6 l6 ">
								<label>Product</label> 
								<input type="hidden" name="mid"
									id="mid" value="${mdrObj.mid}"> 
								<!-- <input type ="text" class="form-control" id="domcreditmerchantMDR" placeholder="Merchant MDR"/>  -->
								<input type="text"
									id="prodType" placeholder="Merchant MDR"
									name="prodType" path="prodType"
									value="${mdrObj.prodType}" readonly="readonly"/>
							</div>
						

					<div class="input-field col s12 m6 l6 ">

											<label for="MerchantMDR">Mobi MDR</label>
											<!-- <input type ="text" class="form-control" id="domcreditmerchantMDR" placeholder="Merchant MDR"/>  -->
											<input type="number" step=".01" 
												id="mobiMDR" placeholder="Merchant MDR"
												name="mobiMDR" path="mobiMDR"
												value="${mdrObj.mobiMdr}" />
										</div>
									<div class="input-field col s12 m6 l6 ">
											<label>Host MDR</label>
											<!-- <input type ="text" class="form-control" placeholder="Host MDR"> -->
											<input type="number" step=".01"
												id="hostMDR" placeholder="Host MDR"
												name="hostMDR" path="hostMDR" value="${mdrObj.hostMdr}" readonly="readonly"
												 />
										
									</div>
									
									<div class="input-field col s12 m6 l6 ">
											<input type="hidden" name="status"
												id="status" value="${mdrObj.status}"> <select name="status1"
												 id="status1"
												onchange="document.getElementById('status').value=document.getElementById('status1').value;">
												<option selected value="${mdrObj.status}">${mdrObj.status}</option>

													<option value="ACTIVE">ACTIVE</option>

													<option value="SUSPENDED">SUSPENDED</option>

											</select>
											<label >Status</label> 
										</div>
									</div>

						<button class="submitBtn" type="submit"  onclick="return loadSelectData();">Submit</button>		 
			</div></div></div></div>
			</form:form>

</div>

</body>
