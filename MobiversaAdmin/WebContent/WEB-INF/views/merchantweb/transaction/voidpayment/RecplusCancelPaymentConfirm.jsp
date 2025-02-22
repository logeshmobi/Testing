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
function cancel(){
	
	
	document.getElementById('form1').action = '${pageContext.request.contextPath}/transactionweb/ezyrecpluslist';
	  document.getElementById('form1').submit();
}



</script>
<body >
<div class="container-fluid">    
  <div class="row">
	 <div class="card border-radius">
        <div class="card-content padding-card">
<form:form method="post" id="form1" name="form1" commandName="txnDet"
		action="${pageContext.request.contextPath}/transactionweb/cancelRecplusPaymentByMerchant" >

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
        <div class="d-flex align-items-center">
        <h3 >EZYRECPLUS Transaction Details</h3>
        </div>
						<form:input type="hidden" value="${txnDet.tid}" path="tid" />
						<form:input type="hidden" value="${txnDet.trxId}" path="trxId" />
						<form:input type="hidden" value="${txnDet.merchantId}" path="merchantId" />
						<form:input type="hidden" value="${txnDet.mid}" path="mid" />
						<form:input type="hidden" value="${txnDet.expectedDate}" path="expectedDate" />
						<form:input type="hidden" value="${txnDet.amount}" path="amount" />
						<form:input type="hidden" value="${txnDet.contactName}" path="contactName" />
						<form:input type="hidden" value="${txnDet.pan}" path="pan" />
						<form:input type="hidden" value="${txnDet.apprCode}" path="apprCode" />
						<form:input type="hidden" value="${txnDet.hostType}" path="hostType" />
						
							
							<table class="table table-striped">
								<tbody>
								<tr>
										<td><label class="control-label">TID:</label></td>
										<td>${txnDet.tid}</td>
									</tr>
									<tr>
										<td><label class="control-label">MID:</label></td>
										<td>${txnDet.mid}</td>
										
									</tr>
									<tr>
										<td><label class="control-label">Transaction Date/Time:</label></td>
										<td>${txnDet.expectedDate}</td>
										
									</tr>

									<tr>
										<td><label class="control-label">Approve Code:</label></td>
										<td>${txnDet.apprCode}</td>
										
									</tr>
									<tr>
										<td><label class="control-label">Amount</label></td>
										<td>${txnDet.amount}</td>
										
										
									</tr>									<tr>
										<td><label class="control-label">Card Holder Name</label></td>
										<td>${txnDet.contactName}</td>
										 
									</tr>
									<tr>
										<td><label class="control-label">Card No:</label></td>
										<td>${txnDet.pan}</td>
										 
									</tr>


								</tbody>
							</table>
							
							</div>
				
						
								<div class="row">
						<div class="input-field col s12 m6 l6 ">

											<button type="submit" class="btn btn-primary blue-btn">Void Payment</button>
										
											<button type="submit" class="btn btn-primary blue-btn" onclick="cancel()">
											<a href="${pageContext.request.contextPath}/transactionweb/ezyrecpluslist"
											style="color:white;">Cancel</a>
											
											</button>
										</div>
									</div>
			
			
<style>
							
				.select-wrapper .caret { fill: #005baa;}
							
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
	
			
				

	
	
	
	
	
	</form:form>
	</div></div></div></div>
	</body>
	</html>
	
	