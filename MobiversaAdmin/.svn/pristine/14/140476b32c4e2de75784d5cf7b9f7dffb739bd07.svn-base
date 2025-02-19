<%@page import="com.mobiversa.payment.controller.MerchantWebReaderController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
<%@page language="java" contentType="application/x-www-form-urlencoded; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<script lang="JavaScript">
function load() {
	//alert("fcancel data");
	 document.location.href = "${pageContext.request.contextPath}/directDebit/authDDTransaction";
	form.submit;
}
</script>


</head>
<body >
<div class="container-fluid"> 
<div class="row" >

			 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        <div class="d-flex align-items-center">
			<p><h3 style="color: blue;font-weight: bold;">${rd.responseDescription}</h3></p></div>
				<div class="row">


						<form method="get">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />
						 <div class="d-flex align-items-center">
							<h3 class="card-title">EZYAUTH Transaction Details</h3>
							</div>
							<table class="table table-striped">
								<tbody>
									<tr>
										<td><label class="control-label">Name</label></td>
										<td>${motoTxnDet.contactName }</td>

									</tr>
									<tr>
										<td><label class="control-label">Phone Number</label></td>
										<td>${motoTxnDet.phno}</td>

									</tr>

									<tr>
										<td><label class="control-label">Email</label></td>
										<td>${motoTxnDet.email}</td>

									</tr>
									<tr>
										<td><label class="control-label">Amount</label></td>
										<td>${motoTxnDet.amount }</td>


									</tr>

									<tr>
										<td><label class="control-label">Reference</label></td>
										<td>${motoTxnDet.referrence }</td>

									</tr>
									<%-- <tr>
										<td><label class="control-label">Delivery Date / CheckIn
													Date</label></td>
										<td>${motoTxnDet.expectedDate }</td>
										 
									</tr> --%>


								</tbody>
							</table>
						</form>
						<!-- edit details -->

					</div>
					<div class="row">
						<div class="input-field col s12 m3 l3">

							<button type="button" class="btn btn-primary blue-btn" onClick="load()">Done</button>
						</div>
					</div>
				</div>
			</div>

				

				
			</div>
		
	</div>
	
</body>
</html>








