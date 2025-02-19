<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Response</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
$(document).ready(function(){
     $("#form1").submit();
});
</script>
	
	</head>
	
<body onload="onLoadSubmit()">

	<form action="${data.requestUrl}" id="form1" method="post" name="form1">
		<input type="hidden" id="mid" name="mid" value="${data.mid}" /> 
		<input type="hidden" id="sellerOrderNo" name="sellerOrderNo" value="${data.sellerOrderNo}" /> 
		<input type="hidden" id="amount" name="amount" value="${data.amount}" /> 
		<input type="hidden" id="email" name="email" value="${data.email}" /> 
		<input type="hidden" id="productDesc" name="productDesc" value="${data.productDesc}" />
		<input type="hidden" id="bankType" name="bankType" value="${data.bankType}" /> 
		<input type="hidden" id="bank" name="bank" value="${data.bank}" /> 
		<input type="hidden" id="buyerName" name="buyerName" value="${data.buyerName}" /> 
		<input type="hidden" id="merchantName" name="merchantName" value="${data.merchantName}" /> 
		<input type="hidden" name="tid" id="tid" value="${data.tid}" /> 
		<input type="hidden" name="mobiLink" id="mobiLink" value="${data.mobiLink}" /> 
		<input type="hidden" name="redirectUrl" id="redirectUrl" value="${data.redirectUrl}" />
	</form>


</body>
</html>

