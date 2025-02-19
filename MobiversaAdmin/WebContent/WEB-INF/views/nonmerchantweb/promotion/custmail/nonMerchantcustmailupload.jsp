<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="com.mobiversa.payment.controller.NonMerchantWebPromotionController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
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
.tooltip .tooltiptext {
    visibility: hidden;
    width: 120px;
    background-color: #555;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 5px 0;
    position: absolute;
    z-index: 1;
    bottom: 125%;
    left: 50%;
    margin-left: -60px;
    opacity: 0;
    transition: opacity 1s;
}

.tooltip .tooltiptext::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 50%;
    margin-left: -5px;
    border-width: 5px;
    border-style: solid;
    border-color: #555 transparent transparent transparent;
}

.tooltip:hover .tooltiptext {
    visibility: visible;
    opacity: 1;
}
.asterisk_input:before {
	content: " *";
	color: #062630;
	position: absolute;
	margin: 0px 0px 0px -20px;
	font-size: xx-large;
	padding: 0 5px 0 0;
}

.form-control {
	width: 100%;
}
</style>
<script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "promotionwebNonmerchant/");
</script>
</head>






<script lang="javascript">
function Checkfiles()


{

	
	var a = document.getElementById('mailFile');
	
	var fileName = a.value;
	var ext = fileName.substring(fileName.lastIndexOf('.') + 1);
	if(ext == "txt" || ext == "TXT" || ext == "csv" || ext == "CSV" )
	{
		return true;
	} 
	else
	{
		alert("Upload .txt or .csv files only");
		a.focus();
		return false;
	}
} 

function loadSelectData() {

	var a1 = document.getElementById("mailFile").value;
	var a2 = document.getElementById("emailText").value;
	//alert("file :"+a1+":test");
	   if ((a1 == null || a1 == '' )&& (a2 == null || a2 == '')) {

			alert("Please upload Email File or Enter EMail ");
			// form.submit = false; 
			return false;
			}
	   else if( a1 != ''){
		   //alert("Please upload Email File  ");
		   document.getElementById("emailText").value='';
		   return Checkfiles();
		  // return false;
	   }
	   if(!validateEmail(document.form1.emailText,10,20))
		{
		
		return false;
		}
	} 

	</script>




<body>

<!-- onsubmit="return Checkfiles();" -->

	<form:form method="post"
		action="merCustMailUpld?${_csrf.parameterName}=${_csrf.token}"
		commandName="merchtCustMail" name="form1" id="form1"
		enctype="multipart/form-data" >
				<div class="content-wrapper">
			<div class="row">

				<div class="col-md-12 formContianer">
					<h3 class="card-title">Add CustMailUpload</h3>

					<div class="card">
<!-- <div class="card-body formbox"> -->


					
        
			<div class="row">
				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="Business Name">Business Name</label>
					 <input class="form-control" type="text" placeholder="businessName" name="businessName"  id="businessName" value="${merchant.businessName}" readonly="readonly">
					
				</div></div>
			
				<div class="form-group col-md-4">
					<div class="form-group">
					
						<label for="Business Address">Business Address</label>
					 <input class="form-control" type="text" placeholder="businessAddress1" name="businessAddress1"  id="businessAddress1" value="${merchant.businessAddress1}" readonly="readonly">
					
				</div></div>
			
				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="ContactNumber">Contact Number</label>
					 <input class="form-control" type="text" placeholder="businessContactNumber" name="businessContactNumber"  id="businessContactNumber" value="${merchant.businessContactNumber}" readonly="readonly">
					
				</div></div>
			
				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="Email">Email</label>
					<input class="form-control" type="email" placeholder="email" name="email"  id="email" value="${merchant.email}" readonly="readonly">
					
				</div>
			</div>
				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="MID">Mid</label>
				<input class="form-control" type="text" placeholder="mid" name="mid"  id="mid" value="${merchant.mid.mid}" readonly="readonly">	
					
				</div>
			</div>
				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="mailFile">Email File</label>
					
						<form:input type="file" class="form-control" id="mailFile"
							placeholder="mailFile" name="mailFile" path="mailFile" />
						  <span class="tooltiptext" style="color: #ff4000;" >Upload .csv & .txt files only </span>

							<%-- <form:input type="text" class="form-control" id="emailText" placeholder="emailText" name="emailText"  path="emailText"  value= "${merchantPromo.emailText}"/> --%>
							
							<textarea  class="form-control" id="emailText"   name="emailText"  path="emailText" style="word-break:break-all;" rows="2" cols="2" maxlength="100">${merchantPromo.emailText}</textarea>
					<!-- 	<span id="mailError" style="color: red;"></span> -->
						
						<!--   <span class="tooltiptext" style="color: #ff4000;" >Upload .csv & .txt files only </span> -->
					</div>
			
			</div>
			
			
			
</div></div>




		


</div>


<button class="btn btn-primary" type="submit" onclick=" return loadSelectData()">Submit</button>
</div></div></div>
		</form:form>




	

</body>
</html>
