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
						commandName="prtxn"
						action="${pageContext.request.contextPath}/merchantpreauth/ProcesswirePreAuthVoid1">



						<!-- <div class="d-flex align-items-center">

							<p>
							<h3 style="color: blue; font-weight: bold;">PREAUTH Transaction
								Details to Void</h3>
							</p>

						</div> -->

						<div class="row">



							<form:input type="hidden" value="${prtxn.tid}" path="tid" />
							<form:input type="hidden" value="${prtxn.trxId}"
								path="trxId" />
							<form:input type="hidden" value="${prtxn.hostType}"
								path="hostType" />

							<div class="d-flex align-items-center">
								<h3 class="card-title">PREAUTH Transaction Details</h3>
							</div>
							<table class="table table-striped" width="100%">
								<tbody>

									<tr>
										<td><label class="control-label">Transaction
												Date/Time:</label></td>
										<td>${prtxn.date}&nbsp;${prtxn.time}</td>

									</tr>

									<tr>
										<td><label class="control-label">Amount</label></td>
										<td>${prtxn.amount}</td>


									</tr>
									<tr>
										<td><label class="control-label">TID:</label></td>
										<td>${prtxn.tid}</td>
									</tr>



									<tr>
										<td><label class="control-label">Approval Code:</label></td>
										<td>${prtxn.aidResponse}</td>

									</tr>

									<tr>
										<td><label class="control-label">Reference</label></td>
										<td>${prtxn.invoiceId}</td>

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