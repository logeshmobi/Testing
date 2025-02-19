<%@page import="com.mobiversa.payment.controller.MerchantWebTransactionController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
 <script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
</head>
<body>
<div style="float-left:30px;" class="col-xs-12 padding-10">
<!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            Transaction Details
          </h1>  
       </section>
   </div>  
    
	 <div class="col-xs-12 bar-status-history padding-top-10">
		<div>
			<div class="pull-left">
				Transaction Date :
				<fmt:formatDate value="${transaction.createdDate}" pattern="dd MMMM yyyy" />
				/ Transaction Time :<fmt:formatDate type="time" value="${transaction.modifiedDate}" />
		    </div>
		</div>
	</div><br><br>
	<%-- <div>
	<div class="col-xs-12 bar-status-history padding-top-10">
	<div>The credit settelement for this transaction is still pending</div>
	<div class="pull-left">
	<a href="<%=TransactionController.URL_BASE%>/voidPayment/${transaction.id}" class="btn btn-default">Void Payment Now</a>
	</div>
	<div class="padding-10 col-xs-12">
	<div class="row static-info">
			<div class="col-md-5 name">Invoice Batch No:</div>
			<div class="col-md-7 value">${tid.batchID}</div>
		</div>
		<div class="row static-info">
			<div class="col-md-5 name">Payment Status:</div>
			<div class="col-md-7 value">${transaction.status}</div>
		</div>
		<div class="row static-info">
			<div class="col-md-5 name">Amount:</div>
			<div class="col-md-7 value">${transaction.transactionAmount}</div>
		</div>
	</div></div>
	</div> --%>
<div style="font-size:16px;margin-bottom:10px;margin-left:-19px;"class="padding-10 col-xs-12">
         <b>Customer Details</b>
      </div>    	
      <!-- <button type="button" class="btn btn-info" data-toggle="collapse" data-target="#demo">Customer Details</button>
      <div id="demo" class="collapse"> -->
  <!--  <div><b>Customer Details</b></div>  -->   
  <div class="col-xs-12 padding-10">
  <div>	
<table style="width:30%"class="table table-striped">
<tbody> 
<tr >
<td style="font-size:75%;">Card Holder Name:</td>
<td style="font-size:75%;">${transaction.cardHolderName}}</td></tr>
<tr >
<td style="font-size:75%;">Issuer/Card Application:</td>
<td style="font-size:75%;">${transaction.issuerReferral}</td></tr>
<tr >
<td style="font-size:75%;">Primary Account No(PAN):</td>
<td style="font-size:75%;">${transaction.pan}</td></tr>
<tr >
<td style="font-size:75%;">PAN Sequence:</td>
<td style="font-size:75%;">${transaction.panSequence}</td></tr>
<tr >
<td style="font-size:75%;">Expiry Date:</td>
<td style="font-size:75%;">${transaction.cardExpiry}</td></tr>
<tr >
<td style="font-size:75%;">Card Holder Name:</td>
<td style="font-size:75%;">${transaction.cardHolderName}}</td></tr>
</tbody>
</table>
</div>
      
      



<div style="font-size:16px;margin-bottom:10px;margin-right:20px;" class="padding-10 col-xs-12"> 
		<b>Receipt Details</b></div>
<div>
<table style="width:30%" class="table table-striped">
<tr>
<td style="font-size:75%;">MID:</td>
<td style="font-size:75%;" >${transaction.mid}</td></tr>
<tr>
<td style="font-size:75%;">Business Name:</td>
<td style="font-size:75%;" >${merchant.businessName}</td></tr>
<tr>
<td style="font-size:75%;">TID:</td>
<td style="font-size:75%;" >${transaction.tid}</td></tr>
<tr>
<td style="font-size:75%;">Approval Code:</td>
<td style="font-size:75%;" >${transaction.approvalCode}</td></tr>
<tr>
<td style="font-size:75%;">Ref No:</td>
<td style="font-size:75%;" >${transaction.issuerReferral}</td></tr>
<tr>
<td style="font-size:75%;">Application ID Integrated Credit Card(AIDICC):</td>
<td style="font-size:75%;" >${transaction.aidcc}</td></tr>
<tr>
<td style="font-size:75%;">TC:</td>
<td style="font-size:75%;" >${transaction.terminalCapabilities}</td></tr>
<tr>
<td style="font-size:75%;">Mobile User ID:</td>
<td style="font-size:75%;" >${mobileUser.username}</td></tr>
<tr>
<td style="font-size:75%;">Authorization Code:</td>
<td style="font-size:75%;" >${transaction.authCode}</td></tr>
</table></div>



<div style="font-size:16px;margin-bottom:10px;margin-right:20px;" class="padding-10 col-xs-12"> 
		<b>Transaction Details</b></div>
<div>
<table style="width:30%" class="table table-striped">
<tr>
<td style="font-size:75%;">Application Effective Data:</td>
<td style="font-size:75%;" >${transaction.applicationEffectiveDate}</td></tr>
<tr>
<td style="font-size:75%;">Application Interchange Profile:</td>
<td style="font-size:75%;" >${transaction.applicationInterchangeProfile}</td></tr>
<tr>
<td style="font-size:75%;">Application transaction Counter:</td>
<td style="font-size:75%;" >${transaction.applicationTransactionCounter}</td></tr>
<tr>
<td style="font-size:75%;">Application Usage Control:</td>
<td style="font-size:75%;" >${transaction.applicationUsageControl}</td></tr>
<tr>
<td style="font-size:75%;">Authorise Response Code:</td>
<td style="font-size:75%;" >${transaction.authResponseCode}</td></tr>
<tr>
<td style="font-size:75%;">Card verification result:</td>
<td style="font-size:75%;" >${transaction.cardVerificationResult} </td></tr>
<tr>
<td style="font-size:75%;">CVM List:</td>
<td style="font-size:75%;">${transaction.cvmList}</td></tr>
<tr>
<td style="font-size:75%;">Issuer Authorise Data:</td>
<td style="font-size:75%;">${transaction.issuerAuthData}</td></tr>
<tr>
<td style="font-size:75%;">Issuer Country COde:</td>
<td style="font-size:75%;">${transaction.issuerCountryCode}</td></tr>
<tr>
<td style="font-size:75%;">Issuer Apllication Data:</td>
<td style="font-size:75%;">${transaction.issuerApplicationData}</td></tr>
<tr>
<td style="font-size:75%;">Issuer Country COde:</td>
<td style="font-size:75%;">${transaction.issuerCountryCode}</td></tr>
<tr>
<td style="font-size:75%;">Terminal Country Code:</td>
<td style="font-size:75%;">${transaction.terminalCountryCode}</td></tr>
<tr>
<td style="font-size:75%;">Terminal Verification Result:</td>
<td style="font-size:75%;">${transaction.terminalVerificationResults}</td></tr>
<tr>
<td style="font-size:75%;">Transaction Status Info:</td>
<td style="font-size:75%;">${transaction.transactionStatusInfo}</td></tr>
<tr>
<td style="font-size:75%;">Terminal Capabilities:</td>
<td style="font-size:75%;">${transaction.terminalCapabilities}</td></tr>
<tr>
<td style="font-size:75%;">Cryptogram information Data(CID):</td>
<td style="font-size:75%;">${transaction.cid}</td></tr>
</table></div>

<div>
        <div class="pull-left">
		<input style="width:20%"class="btn btn-primary" value="Email">
		<input style="width:20%" class="btn btn-primary" value="Print">
		<input style="width:20%" class="btn btn-primary" value="PDF">
		
		</div>
		</div>
	
</body>
</html>
