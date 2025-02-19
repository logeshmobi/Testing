<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@page import="com.mobiversa.payment.controller.MDRDetailsController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<html lang="en-US">
<head>
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<style>
  
  input:focus { 
    outline: none !important;
    border:1px solid red;
    box-shadow: 0 0 10px #719ECE;
}
</style>
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>

<script lang="JavaScript">

function load1(){
	var url = "${pageContext.request.contextPath}/MDR/proMDRList";
	$(location).attr('href',url);
} 

</script>
</head>

<body>
<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Product MDR Review</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>
<form:form method="POST" id="form1" action="${pageContext.request.contextPath}/MDR/updateProMDRConfirm"
	name="form1" commandName="mobileUser">
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
		 <div class="d-flex align-items-center">
				           <h5> MDR Details</h5>
				          </div>
						
						<div class="row">
							<div class="input-field col s12 m6 l6 ">
									<label>MID</label> <input type="text"
										id="mid" value="${mobileUser.mid}" placeholder="mid"
										name="mid" path="mid" readonly="readonly" />
								</div>
							

							<div class="input-field col s12 m6 l6 ">
									<label>Product</label> <input type="text" 
										id="prodType" value="${mobileUser.prodType}" placeholder="prodType"
										name="prodType" path="prodType" readonly="readonly" />
								</div>
							</div>
						

						<div class="row">
							<div class="input-field col s3 ">
										<label>MOBI MDR</label> <input type="number" step=".01"
											id="mobiMdr"
											value="${mobileUser.mobiMdr}"
											placeholder="mobiMdr" name="mobiMdr"
											path="mobiMdr" readonly="readonly" />
									</div>
								
								<div class="input-field col s3 ">

									
										<label>Host MDR</label> <input type="number" step=".01"
											 id="hostMdr"
											value="${mobileUser.hostMdr}"
											placeholder="hostMdr" name="hostMdr"
											path="hostMdr" readonly="readonly" />
									
								</div>
								
								<div class="input-field col s3 ">
										<label>Status</label> <input type="text" 
											id="status"
											value="${mobileUser.status}"
											placeholder="status" name="status"
											path="status" readonly="readonly" />
									</div>
								</div>
								
					
					<button class="btn btn-primary icon-btn" type="submit">Submit</button>
					<input type="button" class="btn btn-default icon-btn" onclick="load1()" value="Cancel">
					</div></div></div></div>
				</form:form>
			</div>

</body>