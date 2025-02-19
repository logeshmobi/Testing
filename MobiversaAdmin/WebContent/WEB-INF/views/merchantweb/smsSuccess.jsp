<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebUMTransactionController"%>
<%@page import="com.mobiversa.payment.util.MobiliteTrackDetails"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<style>
.error {
	color: red;
	font-weight: bold;
}

.asterisk_input:before {
	content: " *";
	color: #062630;
	position: absolute;
	margin: 0px 0px 0px -20px;
	font-size: xx-large;
	padding: 0 5px 0 0;
}


</style>
</head>

 <!-- <script type="text/javascript">
jQuery(document).ready(function() {
$('#sal1').select2();
$('#state').select2();
$('#agType').select2();


}); 
    </script> --> 


<body>

<form:form method="get" action="transactionUmweb/ezyLinkSSList" commandName="trackDet"
		name="form1" id="form1">
	
<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Track Details Response</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>

<div class="row">				
	 <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
  <div class="d-flex align-items-center">
              <h3>${responsemsg}</h3>
              </div>
              
              <div class="row">
			<div class="input-field col s12 m12 l12 ">
			
				<button  type="submit" class="btn btn-primary backBtn">Back</button>
			
			</div>
			</div>
</div>
	<style>
						
				.select-wrapper .caret { fill: #005baa;}
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				
				.submitBtn {padding: 8px 20px;
    border-radius: 10px;
    background-color: #54b74a;
    color: #fff;
    margin: auto;
    display: table;}
				</style>			


				</div>
			</div>
		</div>

	
</div>
</form:form>
</body>
</html>
