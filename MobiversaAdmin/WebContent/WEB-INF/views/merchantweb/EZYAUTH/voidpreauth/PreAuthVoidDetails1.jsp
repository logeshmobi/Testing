<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
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
	 
	 function cancel()
	 {
	 var url = "${pageContext.request.contextPath}/merchantpreauth/PreAuthList1";
	 $(location).attr('href', url);
	 }
</script>
<body class="">
	<div class="container-fluid">
		<div class="row">
			<div class="card border-radius">
				<div class="card-content padding-card">
					<form:form method="post" id="form1" name="form1"
						commandName="umtxn"
						action="${pageContext.request.contextPath}/merchantpreauth/ProcessPreAuthVoid1">



						<!-- <div class="d-flex align-items-center">

							<p>
							<h3 style="color: blue; font-weight: bold;">PREAUTH Transaction
								Details to Void</h3>
							</p>

						</div> -->

						<div class="row">



							<form:input type="hidden" value="${umtxn.f354_TID}" path="f354_TID" />
							<form:input type="hidden" value="${umtxn.f263_MRN}"
								path="f263_MRN" />
							<form:input type="hidden" value="${umtxn.f261_HostID}"
								path="f261_HostID" />

							<div class="d-flex align-items-center">
								<h3 class="card-title">PREAUTH Transaction Details</h3>
							</div>
							<table class="table table-striped" width="100%">
								<tbody>

									<tr>
										<td><label class="control-label">Transaction
												Date/Time:</label></td>
										<td>${umtxn.h003_TDT}&nbsp;${umtxn.h004_TTM}</td>

									</tr>

									<tr>
										<td><label class="control-label">Amount</label></td>
										<td>${umtxn.f007_TxnAmt}</td>


									</tr>
									<tr>
										<td><label class="control-label">TID:</label></td>
										<td>${umtxn.f354_TID}</td>
									</tr>



									<tr>
										<td><label class="control-label">Approval Code:</label></td>
										<td>${umtxn.f011_AuthIDResp}</td>

									</tr>

									<tr>
										<td><label class="control-label">Reference</label></td>
										<td>${umtxn.f270_ORN}</td>

									</tr>



								</tbody>
							</table>



						</div>
						<div class="row">
							<div class="input-field col s12 m6 l6 ">

								<button type="submit" class="btn btn-primary">Void
									Payment</button>



								<button type="button" class="btn btn-primary" onclick="cancel()">
									<a style="color: white;">Cancel</a>
								</button>
							</div>
						</div>

					</form:form>
				</div>
			</div>
		</div>



	</div>








</body>

</html>