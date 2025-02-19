<%@page
	import="com.mobiversa.payment.controller.MerchantWebReaderController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@page
	import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
<%@ page language="java"
	contentType="application/x-www-form-urlencoded; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<script lang="JavaScript">
function load() {
	//alert("fcancel data");
	 document.location.href = "${pageContext.request.contextPath}/merchantpreauth/preAuthTransaction";
	form.submit;
}
</script>
<script type="text/javascript">

	window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "");
	</script>
<script type="text/javascript">
function load1(){
	var url = "${pageContext.request.contextPath}/<%=MerchantWebMobileController.URL_BASE%>/changepwd/${mobileUser.id}";
	$(location).attr('href',url);
} 

</script>

</head>
<body >
	
		<div style="overflow: auto; border: 1px; width: 100%">
			<div class="content-wrapper">

<p><h3 style="color: blue;font-weight: bold;">${requestScope.responseData}</h3></p>
				<div class="row">

					<div class="col-md-6">
						<!-- <div class="card"> -->


						<div class="card" style="width: 50rem;">
						
						<form method="get">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
						
							<h3 class="card-title">EZYAUTH Transaction Details</h3>
							<table class="table table-striped">
								<tbody>
									<tr>
										<td><label class="control-label">Name</label></td>
										<td>${preAuthTxnDet.contactName }</td>
										
									</tr>
									<tr>
										<td><label class="control-label">Phone
													Number</label></td>
										<td>${preAuthTxnDet.phno}</td>
										
									</tr>

									<tr>
										<td><label class="control-label">Email</label></td>
										<td>${preAuthTxnDet.email}</td>
										
									</tr>
									<tr>
										<td><label class="control-label">Amount</label></td>
										<td>${preAuthTxnDet.amount }</td>
										
										
									</tr>

									<tr>
										<td><label class="control-label">Reference</label></td>
										<td>${preAuthTxnDet.referrence }</td>
										 
									</tr>
									<tr>
										<td><label class="control-label">Delivery Date / CheckIn
													Date</label></td>
										<td>${preAuthTxnDet.expectedDate }</td>
										 
									</tr>


								</tbody>
							</table>
							</form>
							
				
						</div>
<div class="form-group col-md-4 i">
										<div class="form-group">

											<button type="button" class="btn btn-primary"
				onClick="load()">Done</button>
										</div>
									</div>
					</div>
				</div>

			
				
			</div>
		</div>
	
	
</body>
</html>








