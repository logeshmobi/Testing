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
/* function cancel(){
	
	
	document.getElementById('form1').action = '${pageContext.request.contextPath}/transaction/cancelPaymentByAdmin';
	  document.getElementById('form1').submit();
} */



</script>

<body class="">
<div class="container-fluid">    
  <div class="row">
	 <div class="card border-radius">
        <div class="card-content padding-card">
<form:form method="post" id="form1" name="form1" commandName="txnDet"
		action="${pageContext.request.contextPath}/transaction/cancelPaymentByAdmin" >


 <div class="d-flex align-items-center">
			<c:choose>
			<c:when test="${responseData != null }">
			<p><h3 style="color: blue;font-weight: bold;">${responseData}</h3></p>
			</c:when>
			<c:otherwise>
			<p><h3 style="color: blue;font-weight: bold;">Transaction Details to Void</h3></p>
			</c:otherwise>
			</c:choose>
		</div>	
			
			<div class="row">
						<form:input type="hidden" value="${txnDet.f354_TID}" path="f354_TID" />
						<form:input type="hidden" value="${txnDet.f001_MID}" path="f001_MID" />
						<form:input type="hidden" value="${txnDet.h003_TDT}" path="h003_TDT" />
						<form:input type="hidden" value="${txnDet.h004_TTM}" path="h004_TTM" />
						<form:input type="hidden" value="${txnDet.f011_AuthIDResp}" path="f011_AuthIDResp" />
						<form:input type="hidden" value="${txnDet.f007_TxnAmt}" path="f007_TxnAmt" />
						<form:input type="hidden" value="${txnDet.f268_ChName}" path="f268_ChName" />
						<form:input type="hidden" value="${txnDet.maskedPan}" path="maskedPan" />
						<form:input type="hidden" value="${txnDet.f263_MRN}" path="f263_MRN" />
						
						 <div class="d-flex align-items-center">
							<h3 class="card-title">EZYWAY Transaction Details</h3>
							</div>
							<table class="table table-striped" width="100%">
								<tbody>
								<tr>
										<td><label class="control-label">TID:</label></td>
										<td>${txnDet.f354_TID}</td>
									</tr>
									<tr>
										<td><label class="control-label">MID:</label></td>
										<td>${txnDet.f001_MID}</td>
										
									</tr>
									<tr>
										<td><label class="control-label">Transaction Date/Time:</label></td>
										<td>${txnDet.h003_TDT}${txnDet.h004_TTM}</td>
										
									</tr>

									<tr>
										<td><label class="control-label">Approve Code:</label></td>
										<td>${txnDet.f011_AuthIDResp}</td>
										
									</tr>
									<tr>
										<td><label class="control-label">Amount</label></td>
										<td>${txnDet.f007_TxnAmt}</td>
										
										
									</tr>									<tr>
										<td><label class="control-label">Card Holder Name</label></td>
										<td>${txnDet.f268_ChName}</td>
										 
									</tr>
									<tr>
										<td><label class="control-label">Card No:</label></td>
										<td>${txnDet.maskedPan}</td>
										 
									</tr>


								</tbody>
							</table>
							
							
				</div>
						
						
								<div class="row">
										<div class="input-field col s12 m6 l6 ">
					  						
											<button type="submit" class="btn btn-primary blue-btn" >Void Payment</button>
											
											<button type="submit" class="btn btn-primary blue-btn" onclick="cancel()">
											<a href="${pageContext.request.contextPath}/transaction/umEzywayList/1"
											style="color:white;">Cancel</a>
											
											</button>
									</div></div>
											
											<style>
							
				.select-wrapper .caret { fill: #005baa;}
							
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
	
			
				
			
			</form:form>
		</div>
	
	
	
	
	
	</div></div></div>
	
</body>

</html>