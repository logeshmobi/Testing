<%@page
	import="com.mobiversa.payment.controller.MerchantPreAuthController"%>
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
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
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
<body>
	<style>
.mobi-padding-card {
	padding-right: 0px !important;
	padding-left: 500px !important;
}

.table {
	margin-left: auto;
	margin-right: auto;
	width: auto !important;
}

tr {
	border-bottom: none !important;
}

td {
	font-size: 16px !important;
	color: #414755 !important;
}

.mobi-control-label {
	font-size: 16px !important;
	color: #414755 !important;
}
</style>

	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong> PREAUTH Transaction Details</strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content">


						<form method="get">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}" />

							<div class="row-fluid">
								<table class="table table-striped">
									<tbody>

										<tr>
											<td colspan="2"
												style="color: #005baa !important; font-weight: bold; font-size: 20px !important;">${requestScope.responseData}</td>
										</tr>

										<tr>

											<td colspan="2" style="font-size: 20px !important;">PREAUTH
												Transaction Details</td>
										</tr>


										<tr>
											<td><label class="mobi-control-label">Phone
													Number</label></td>
											<td>${preAuthTxnDet.phno}</td>

										</tr>

										<tr>
											<td><label class="mobi-control-label">Email</label></td>
											<td>${preAuthTxnDet.email}</td>

										</tr>

										<tr>
											<td><label class="mobi-control-label">Amount</label></td>
											<td>${preAuthTxnDet.amount }</td>


										</tr>

										<tr>
											<td><label class="mobi-control-label">Reference</label></td>
											<td>${preAuthTxnDet.referrence }</td>

										</tr>

										<tr>
											<td colspan="2"><button type="button"
													class="btn btn-primary" onClick="load()">Done</button></td>
										</tr>

									</tbody>
								</table>
							</div>
						</form>



					</div>
				</div>
			</div>


		</div>
	</div>


</body>
</html>








