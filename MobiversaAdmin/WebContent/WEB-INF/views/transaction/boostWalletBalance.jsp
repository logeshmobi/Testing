<%@page import="com.mobiversa.payment.controller.AllTransactionController"%>
<%@page import="com.mobiversa.payment.controller.TransactionController"%>
<%@page import="com.mobiversa.payment.util.ResponseBoostWalletApi"%>
<%@page import="com.mobiversa.common.bo.ForSettlement"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>


<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script> -->
   <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
  
 <style>
 
 .td{
 text-align:right;
 
 
 }
 
 </style>
  
  
</head>

<body>
	 <div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Boost Wallet Balance</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
	 
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">


									<div class="row" style="width:100%;">

										<div class="input-field col s12 m6 l6 ">
												<label class="control-label" style="">Current Wallet Balance</label><br>
												<input class="form-control" value="${res.currentWalletBalance}" readonly="readonly">


											</div>
										<div class="input-field col s12 m6 l6 ">

												<label class="control-label">Starting Wallet Balance</label><br>
													 
												<input class="form-control" value="${res.startingWalletBalance}" readonly="readonly">

											</div>
										<div class="input-field col s12 m6 l6 ">

										
											
												<label class="control-label">Total No Payment Transaction</label><br>
												<input class="form-control" value="${res.totalNoPaymentTransaction}" readonly="readonly">
											</div>
										<div class="input-field col s12 m6 l6 ">
												<label class="control-label">Total No Void Transaction</label><br>
												 <input class="form-control" value="${res.totalNoVoidTransaction}" readonly="readonly">
											</div>
										<div class="input-field col s12 m6 l6 ">
												<label class="control-label">Total Payment Amount</label><br> 
												<input class="form-control" value="${res.totalPaymentAmount}" readonly="readonly">

											</div>
										<div class="input-field col s12 m6 l6 ">

												<label class="control-label">Total Void Amount</label><br>

												<input class="form-control" value="${res.totalVoidAmount}" readonly="readonly">
											</div>
										</div>
</div>

										
										</div>

										
									</div>

								</div>
							</div>
				

	



	

</body>




			</html>
		
		