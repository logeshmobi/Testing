<%@page import="com.mobiversa.payment.controller.MDRDetailsController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<script type="text/javascript">
function loadDate(inputtxt, outputtxt) {

	// alert("test data123");
	var field = inputtxt.value;
	//alert("Date :"+field);
	document.getElementById("receivedDate").value=field;
	
	//var field1 = outputtxt.value;
	//alert(field+" : "+outputtxt.value);
	//document.getElementById("date11").value=field;
	outputtxt.value = field;
	//alert(outputtxt.value);
	// alert(document.getElementById("date11").value);
}
function proceed(){
	
	var resCode = document.getElementById("rescode").value;
	document.getElementById("reasonCode").value= rescode.value;
	var receivedDate=document.getElementById("receivedDate").value;
	var reasonCode=document.getElementById("reasonCode").value;
	var amount=document.getElementById("cbAmount").value;
	
	if (receivedDate == null || receivedDate == '') {
		alert("Please Select Received Date");
	}else if (reasonCode == null || reasonCode == '') {
		alert("Please Enter the Reasaon");
	}else{
		/* alert("mid"+document.getElementById("mid").value);
		alert("prodType" +document.getElementById("prodType").value);
		alert("cbAmount" +document.getElementById("cbAmount").value);
		alert("hostAmount"+document.getElementById("hostAmount").value);
		alert("status"+document.getElementById("status").value);
		alert("mrn"+document.getElementById("mrn").value);
		alert("receivedDate"+document.getElementById("receivedDate").value); 
		alert("reasonCode" +document.getElementById("reasonCode").value); */
		
		alert("Are you sure,RM "+amount+" to charge back?");
		
		$("#myform").submit(); 
	}
	
	
	
	
}

</script>

<body class="">
<div class="container-fluid">  
<div class="row">
<div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
			<c:choose>
			<c:when test="${responseData != null }">
			<p><h3 style="color: blue;font-weight: bold;">${responseData}</h3></p>
			</c:when>
			<c:otherwise>
			<p><h3 style="color: blue;font-weight: bold;">Transaction Details to Charge Back</h3></p>
			</c:otherwise>
			</c:choose>
		</div></div></div></div></div>
				<div class="row">
<div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
					 <div class="d-flex align-items-center">
							<h3 class="card-title">UM-EZYWAY Charge Back Details</h3>
							</div>
							
								<div class="row">
							<div class="input-field col s12 m6 l6 ">
										<label class="control-label">MID:</label>
										<input type="text" class="" readonly value="${txnDet.mid}">
										</div>
							<div class="input-field col s12 m6 l6 ">
										<label class="control-label">Amount</label>
										
										<input type="text" class="" readonly value="${txnDet.mobiMdr}">
										</div>	
				
								</div>
								
								<div class="row">

							<div class="input-field col s12 m6 l6 ">
							<label for="from" style="margin:0px;">Received Date</label>							
							<input type="hidden"
								name="date11" id="date11" <c:out value="${fromDate}"/>>
								 <input type="text" id="from" name="date1"  class="validate datepicker"
							onchange="return loadDate(document.getElementById('from'),document.getElementById('date11'))">
							</div>
							
							<div class="input-field col s12 m6 l6 ">
							<label >Reason Code</label>
							<input type="text"  name="rescode" id="rescode" value="">
							</div>
							
							</div>

							
								<div class="row">
								<div class="input-field col s12 m3 l3">
					  			<div class="button-class">
					  			<button type="button" class="btn btn-primary" onclick="proceed();" >Charge</button>
					  			<button type="submit" class="btn btn-primary">
											<a href="${pageContext.request.contextPath}/transaction/umEzywayList/1"
											style="color:white;">Cancel</a></button>
											</div></div>
								

										
									</div>
									
									<style>
				.export_div .select-wrapper { width:65%;float:left;}
				.datepicker { width:80% !important;}				
				.select-wrapper .caret { fill: #005baa;}
				
				.addUserBtn,.addUserBtn:hover {
				background-color: #fff;border: 1px solid #005baa;border-radius: 20px;color: #005baa; font-weight:600;}
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
					</div>
				</div>

			
				
			</div>
		</div>
	
	
	</div>
	
	

	<form method="post" id="myform" name="myform" action="${pageContext.request.contextPath}/MDR/addChBack?${_csrf.parameterName}=${_csrf.token}">
				<input type="hidden" name="mid" id="mid" value="${txnDet.mid}">
				<input type="hidden" name="prodType" id="prodType" value="${txnDet.prodType}">
				<input type="hidden" name="cbAmount" id="cbAmount" value="${txnDet.mobiMdr}">
				<input type="hidden" name="hostAmount" id="hostAmount" value="${txnDet.hostMdr}">
				<input type="hidden" name="status" id="status" value="${txnDet.status}">
				<input type="hidden" name="mrn" id="mrn" value="${txnDet.remarks}">
				<input type="hidden" name="receivedDate" id="receivedDate" value=""> 
				<input type="hidden" name="reasonCode" id="reasonCode" value=" ">
			
	</form>
</body>

</html>