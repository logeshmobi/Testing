<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- <html lang="en-US">
<head> -->
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


</style>
</head>
<!-- <script type="text/javascript">

jQuery(document).ready(function() {
$( "#motoTid" ).change(function() {
   var motoTid=document.getElementById("motoTid").value;
   if(motoTid <=8 && motoTid >=8)
   {
   alert("Enter 8 Digit of MotoTid");
   }
});
});

</script> -->

<!-- <script type="text/javascript">
jQuery(document).ready(function() {
$('#merchantName').select2();
$('#referenceNo').select2();
$('#motorefNo').select2();
$('#ezypassrefNo').select2();
$('#ezyrecrefNo').select2();
$('#ezywayrefNo').select2();

$('#connectType').select2();
$('#deviceType').select2();








//$('#renewalPeriod').select2();
});  
    </script>
 -->


 <script lang="JavaScript">
 
 
 
 function disableRow1() {

		//alert("test123");

		var i = document.getElementById("connectType").value;
		//alert(i);

		if (i == "WIFI") {
			//document.getElementById("agType").value;

			document.getElementById("preAuth1").style.display = 'none';

			//document.getElementById("BankDetails1").style.display = 'none';

		} else if (i == "BT") {

			document.getElementById("preAuth1").style.display = '';

			//document.getElementById("BankDetails1").style.display = '';
		}

	}
 

       function checkBox()
       {
	
	var preAuth = document.getElementById("preAuth").value;
	
	alert("check preAuth:" + preAuth);
	
       }

function checkDevice(){
	//alert("checking device-----------");
	var tid = document.getElementById("tid").value;
	var e = document.getElementById("referenceNo").value;
	var deviceId = document.getElementById("deviceId").value;

		if (tid != '' && tid != null) {
			//alert("check tid valid");
			if (!allnumeric(document.form1.tid, 8, 8)) {
				return false;
			}

			if (deviceId != '' && deviceId != null) {
				if (!alphanumeric(document.form1.deviceId, 15, 16)) {
					return false;
				}
			} else {
				alert("Please Select DeviceId");
				return false;
			}
			
			if (e == null || e == '' || e == 'refNo') {

				alert("Please Select Reference No");
				//form.submit = false; 
				return false;
			}
		}
		return true;
	
}
function checkMotoDevice(){
	//alert("checking moto device-----------");
	var motoTid = document.getElementById("motoTid").value;
	var motorefNo = document.getElementById("motorefNo").value;
	var motodeviceId = document.getElementById("motodeviceId").value;

	if (motoTid != '' && motoTid != null) {
		if (!allnumeric(document.form1.motoTid, 8, 8)) {
			return false;
		}

		if (motodeviceId != '' && motodeviceId != null) {
			if (!alphanumeric(document.form1.motodeviceId, 15, 16)) {
				return false;
			}
		} else {
			alert("Please Select Moto DeviceId");
			return false;
		}
		
		if (motorefNo == null || motorefNo == ''
			|| motorefNo == 'refNo') {

		alert("Please Select Moto Reference No");
		//form.submit = false; 
		return false;
		}
		}
	return true;
}

function checkEzypassDevice(){
	//alert("checking ezypass device-----------");
	var ezypassTid = document.getElementById("ezypassTid").value;
	var ezypassrefNo = document.getElementById("ezypassrefNo").value;
	var ezypassdeviceId = document.getElementById("ezypassdeviceId").value;

	if (ezypassTid != '' && ezypassTid != null) {
		if (!allnumeric(document.form1.ezypassTid, 8, 8)) {
			return false;
		}

		if (ezypassdeviceId != '' && ezypassdeviceId != null) {
			if (!alphanumeric(document.form1.ezypassdeviceId, 15, 16)) {
				return false;
			}
		} else {
			alert("Please Select Ezypass DeviceId");
			return false;
		}
		
		if (ezypassrefNo == null || ezypassrefNo == ''
			|| ezypassrefNo == 'refNo') {

			alert("Please Select Ezypass Reference No");
		//form.submit = false; 
		return false;
		}
	}
	return true;
}

function checkEzyrecDevice(){
	//alert("checking ezyrec device-----------");
	var ezyrecTid = document.getElementById("ezyrecTid").value;
	var ezyrecrefNo = document.getElementById("ezyrecrefNo").value;
	var ezyrecdeviceId = document.getElementById("ezyrecdeviceId").value;

	if (ezyrecTid != '' && ezyrecTid != null) {
		if (!allnumeric(document.form1.ezyrecTid, 8, 8)) {
			return false;
		}

		if (ezyrecdeviceId != '' && ezyrecdeviceId != null) {
			if (!alphanumeric(document.form1.ezyrecdeviceId, 15, 16)) {
				return false;
			}
		} else {
			alert("Please Select Ezyrec DeviceId");
			return false;
		}
		
		if (ezyrecrefNo == null || ezyrecrefNo == ''
			|| ezyrecrefNo == 'refNo') {

		alert("Please Select Ezyrec Reference No");
		//form.submit = false; 
		return false;
	}

	
	}
	return true;
	
}


function validateEzyWayDevice(){
	//alert("checking device-----------");
	var ezywayTid = document.getElementById("ezywayTid").value;
	var ezywayrefNo = document.getElementById("ezywayrefNo").value;
	var ezywaydeviceId = document.getElementById("ezywaydeviceId").value;

		if (ezywayTid != '' && ezywayTid != null) {
			//alert("check tid valid");
			if (!allnumeric(document.form1.ezywayTid, 8, 8)) {
				return false;
			}

			if (ezywaydeviceId != '' && ezywaydeviceId != null) {
				if (!alphanumeric(document.form1.ezywaydeviceId, 15, 16)) {
					return false;
				}
			} else {
				alert("Please Select EzyWay DeviceId");
				return false;
			}
			
			if (ezywayrefNo == null || ezywayrefNo == '' || ezywayrefNo == 'refNo') {

				alert("Please Select EzyWay Ref No");
				//form.submit = false; 
				return false;
			}
		}
		return true;
	
}


function validateFormData(){
	//alert("validate form data");
	
	
	 if(!checkDevice()){
		return false;
	}
	if(!checkMotoDevice()){
		return false;
	}
	
	if(!validateEzyWayDevice()){
		return false;
	}
	if(!checkEzyrecDevice())
		{
		return false;
		}
	if(!checkEzypassDevice()){
		return false;
	} 
	/* if(!checkDevice() || !checkMotoDevice() || !validateEzyWayDevice() || !checkEzyrecDevice() 
			|| !checkEzypassDevice()){
		return false;
	} */
	
	
	 return true;
}

function validate(){
//alert("check validation: "+validateFormData());
	if(validateFormData()){
		return true;
	}else{
		return false;
	}
}

function loadSelectData(){ 
	
		var expiryDate = document.getElementById("expiryDate").value;
			if (expiryDate == '' || expiryDate == null) {
				alert("please fill Expiry Date");
				document.getElementById("expiryDate").focus();
				return false;

			}
			var renewalPeriod = document.getElementById("renewalPeriod").value;

			if (renewalPeriod == null || renewalPeriod == '') {

				alert("Please Fill Renewal Period");
				document.getElementById("renewalPeriod").focus();
				//form.submit = false; 
				return false;
			}
			var businessName = document.getElementById("businessName").value;
			if (businessName == '' || businessName == null) {

				alert("Please Fill BusinessName..");
				document.getElementById("businessName").focus();
				return false;

			}

			var contactName = document.getElementById("contactName").value;
			if (contactName == '' || contactName == null) {
				alert("Please Fill ContactName..");
				document.getElementById("contactName").focus();
				return false;

			}
			
			var tid = document.getElementById("tid").value;
			
			var motoTid = document.getElementById("motoTid").value;
			
			var ezypassTid = document.getElementById("ezypassTid").value;
			
			var ezyrecTid = document.getElementById("ezyrecTid").value;
			
			
			var ezywayTid = document.getElementById("ezywayTid").value;
			
			
			
			if((tid == '' || tid == null) && (motoTid == '' || motoTid == null) && 
					(ezypassTid == '' || ezypassTid == null) && (ezywayTid == '' || ezywayTid == null) &&
					(ezyrecTid == '' || ezyrecTid == null)){
				alert("No TID to Register..Please Fill Anyone TID Details..");
				return false;
			}
			
			
			

			
			var e = document.getElementById("referenceNo").value;
			var deviceId = document.getElementById("deviceId").value;

			if (tid != '' && tid != null) {
				//alert("check tid valid");
				 if (!allnumeric(document.form1.tid, 8, 8)) {
					document.getElementById("tid").focus();
					return false;
				} 

				if (deviceId != '' && deviceId != null) {
					if (!alphanumeric(document.form1.deviceId, 15, 16)) {
						document.getElementById("deviceId").focus();
						return false;
					}
				} else {
					alert("Please Select DeviceId");
					document.getElementById("deviceId").focus();
					return false;
				}

				if (e == null || e == '' || e == 'refNo') {

					alert("Please Select Reference No");
					document.getElementById("e").focus();
					//form.submit = false; 
					return false;
				}
			}
			
			
			var motorefNo = document.getElementById("motorefNo").value;
			var motodeviceId = document.getElementById("motodeviceId").value;

			if (motoTid != '' && motoTid != null) {
				if (!allnumeric(document.form1.motoTid, 8, 8)) {
					document.getElementById("motoTid").focus();
					return false;
				}

				if (motodeviceId != '' && motodeviceId != null) {
					if (!alphanumeric(document.form1.motodeviceId, 15, 16)) {
						document.getElementById("motodeviceId").focus();
						return false;
					}
				} else {
					alert("Please Select Moto DeviceId");
					document.getElementById("motodeviceId").focus();
					return false;
				}

				if (motorefNo == null || motorefNo == ''
						|| motorefNo == 'refNo') {

					alert("Please Select Moto Reference No");
					document.getElementById("motorefNo").focus();
					//form.submit = false; 
					return false;
				}

			}
			
			
			var ezypassrefNo = document.getElementById("ezypassrefNo").value;
			var ezypassdeviceId = document.getElementById("ezypassdeviceId").value;

			if (ezypassTid != '' && ezypassTid != null) {
				if (!allnumeric(document.form1.ezypassTid, 8, 8)) {
					document.getElementById("ezypassTid").focus();
					return false;
				}

				if (ezypassdeviceId != '' && ezypassdeviceId != null) {
					if (!alphanumeric(document.form1.ezypassdeviceId, 15, 16)) {
						document.getElementById("ezypassdeviceId").focus();
						return false;
					}
				} else {
					alert("Please Select Ezypass DeviceId");
					document.getElementById("ezypassdeviceId").focus();
					return false;
				}

				if (ezypassrefNo == null || ezypassrefNo == ''
						|| ezypassrefNo == 'refNo') {

					alert("Please Select Ezypass Reference No");
					document.getElementById("ezypassrefNo").focus();
					//form.submit = false; 
					return false;
				}

			}
			
			
			var ezywayrefNo = document.getElementById("ezywayrefNo").value;
			var ezywaydeviceId = document.getElementById("ezywaydeviceId").value;

			if (ezywayTid != '' && ezywayTid != null) {
				if (!allnumeric(document.form1.ezywayTid, 8, 8)) {
					document.getElementById("ezywayTid").focus();
					return false;
				}

				if (ezywaydeviceId != '' && ezywaydeviceId != null) {
					
					if (!alphanumeric(document.form1.ezywaydeviceId, 15, 16)) {
						document.getElementById("ezywaydeviceId").focus();
						return false;
					}
				} else {
					alert("Please Select Ezyway DeviceId");
					document.getElementById("ezywaydeviceId").focus();
					return false;
				}

				if (ezywayrefNo == null || ezywayrefNo == ''
						|| ezywayrefNo == 'refNo') {

					alert("Please Select Ezyway Reference No");
					document.getElementById("ezywayrefNo").focus();
					//form.submit = false; 
					return false;
				}
			}
			
			
			var ezyrecrefNo = document.getElementById("ezyrecrefNo").value;
			var ezyrecdeviceId = document.getElementById("ezyrecdeviceId").value;

			if (ezyrecTid != '' && ezyrecTid != null) {
				if (!allnumeric(document.form1.ezyrecTid, 8, 8)) {
					document.getElementById("ezyrecTid").focus();
					return false;
				}

				if (ezyrecdeviceId != '' && ezyrecdeviceId != null) {
					if (!alphanumeric(document.form1.ezyrecdeviceId, 15, 16)) {
						document.getElementById("ezyrecdeviceId").focus();
						return false;
					}
				} else {
					alert("Please Select Ezyrec DeviceId");
					document.getElementById("ezyrecdeviceId").focus();
					return false;
				}

				if (ezyrecrefNo == null || ezyrecrefNo == ''
						|| ezyrecrefNo == 'refNo') {

					alert("Please Select Ezyrec Reference No");
					document.getElementById("ezyrecrefNo").focus();
					//form.submit = false; 
					return false;
				}

			}

			
			return true;

		}
		// }    

		//allnumeric tid & reference No validation
		function allnumeric(inputtxt, minlength, maxlength) {
			var field = inputtxt.value;

			var mnlen = minlength;
			var mxlen = maxlength;
			//var uzip = document.registration.zip;  
			var numbers = /^[0-9]+$/;
			if ((field.length < mnlen) || (field.length > mxlen)) {
				alert("Please input the " + inputtxt.name + " between " + mnlen
						+ " and " + mxlen + " characters");
				return false;
			} else if (field.match(numbers)) {
				// Focus goes to next field i.e. email.  
				//document.registration.email.focus();  
				return true;
			} else {
				alert(inputtxt.name + ' must have numeric characters only');
				//uzip.focus();  
				return false;
			}
		}

		//alphanumeric deviceID validation

		function alphanumeric(inputtxt, minlength, maxlength) {
			var field = inputtxt.value;
			var mnlen = minlength;
			var mxlen = maxlength;
			// var uadd = document.registration.address;  
			var letters = /^[0-9a-zA-Z]+$/;
			if ((field.length < mnlen) || (field.length > mxlen)) {
				alert("Please input the " + inputtxt.name + " between " + mnlen
						+ " and " + mxlen + " characters");
				return false;
			} else if (field.match(letters)) {
				// Focus goes to next field i.e. Country.  
				//document.registration.country.focus();  
				return true;
			} else {
				alert(inputtxt.name + ' must have alphanumeric characters only');
				// uadd.focus();  
				return false;
			}
		}

		function allLetterSpace(inputtxt, minlength, maxlength) {

			var field = inputtxt.value;
			var mnlen = minlength;
			var mxlen = maxlength;
			// var uname = document.registration.username;  
			var letters = /^[a-zA-Z0-9 ]*$/;/*  /^[A-Za-z]+$/; */
			if ((field.length == 0) || (field.length < mnlen)
					|| (field.length > mxlen)) {
				alert("Please input the " + inputtxt.name + " between " + mnlen
						+ " and " + mxlen + " characters");
				return false;
			} else if (field.match(letters)) {
				// Focus goes to next field i.e. Address.  
				//document.form1.address.focus();  
				return true;
			} else {
				alert(inputtxt.name + ' must have alphanumeric and space only');
				//uname.focus();  
				return false;
			}
		}

		function validateEmail(inputtxt) {
			//alert(inputtxt);
			var field = inputtxt.value;
			//var uemail = document.registration.email;  
			var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
			if (field.match(mailformat)) {
				//document.registration.desc.focus();  
				return true;

			} else if (field.length == 0) {
				alert("You have entered  " + inputtxt.name + " address!");
				//uemail.focus();  
				return false;
			} else {
				alert("You have entered an invalid " + inputtxt.name
						+ " address!");
			}
		}

		function checkRenewal() {

			var monthNames = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
					"Aug", "Sep", "Oct", "Nov", "Dec" ];

			var renewal = document.getElementById("renewalPeriod").value;

			//alert("check renewal:" + renewal);

			/* var myDate = new Date();
			myDate.setDate(myDate.getDate() + renewal); */

			var CurrentDate = new Date();
			/* alert("check 2222222222222 :" + CurrentDate.getFullYear());
			var d = new Date(CurrentDate.getFullYear(), CurrentDate.getMonth(), CurrentDate.getDate());
			d.setMonth(d.getMonth() + parseInt(renewal)); */

			/* var year = CurrentDate.getFullYear(); */
			//CurrentDate.setMonth(CurrentDate.getMonth() + renewal);
			/* alert("check renewal1111111111111111111111 :" + d);
			alert("check renewal1:" + CurrentDate.getMonth()); */
			CurrentDate.setMonth(CurrentDate.getMonth() + parseInt(renewal));
			//alert("check renewal1123 :" + CurrentDate.getMonth());
			//CurrentDate.setFullYear(CurrentDate.getFullYear());

			//alert("check renewal2:" + CurrentDate);

			var day = CurrentDate.getDate();
			var monthIndex = monthNames[CurrentDate.getMonth()];
			var year = CurrentDate.getFullYear();

			document.getElementById("expiryDate").value = day + "-"
					+ monthIndex + "-" + year;
			//.setMonth(CurrentDate.getMonth() + renewal);

			//alert("check renewal3:" + document.getElementById("expiryDate").value);

		}
	</script>
       
       <script>
    $(function(){
      // bind change event to select
      $('#merchantName').on('change', function () {
          var url = $(this).val(); // get selected value
          //alert(url);
          if (url) { // require a URL
              window.location = url; // redirect
             // alert(window.location);
          }
          return false;
      });
    });
</script>
<script>
function mainDet(){
	alert("maindet");
	var businessName=document.getElementById('businessName').value;
	alert("maindet--"+businessName+"---");
	var mainDiv=document.getElementById('mainDet');
	var mainDivt=document.getElementById('mainDett');
	if(businessName!=null && businessName!=''){
		alert("maindet1--"+businessName+"---");
		mainDiv.style.display='block';
		mainDivt.style.display='block';
		}else{
			alert("maindet0--"+businessName+"---");
			mainDiv.style.display='none';
			mainDivt.style.display='none';
		}
}
</script>
    
<body onclick="document.getElementById('emtpyTID').style.display='none';">
	
	
	
			
			
			<div class="content-wrapper">
			<h2 class="card-title" style="color:blue;">Add Mobile User</h2>
			<c:if test="${ responseEmptyMids!=null }">
			
  				<center>  
  					<p id="emtpyTID"> <h3 style="color:red;">* Empty TID Details.. Please Fill Anyone TID Details...</h3></p></center>
 			</c:if>
 			<c:if test="${responseEmptyDeviceID!=null }">
			
  				<center>  
  					<p id="emtpyDeviceID"> <h3 style="color:red;">* Empty DeviceID Details..</h3></p></center>
 			</c:if>
 			
			
			<div class="row">

				<div class="col-md-12 formContianer">
					

					<div class="card">
					
					<h3 class="card-title">Merchant Details</h3>
					
						<div class="row">
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="Email">Business Name</label>
									</div>
									</div>
									<div class="row">
							<div class="form-group col-md-4">
								<div class="form-group">
									<select class="form-control" name="merchantName"
								id="merchantName" path="merchantName">
								<optgroup label="Business Names" style="width:100%">
								<option selected value=""><c:out value="business Name" /></option>
								<%-- <c:forEach items="${merchantNameList}" var="merchantName">
									<option value="${merchantName}">${merchantName}</option>


								</c:forEach> --%>
								<c:forEach items="${merchant1}" var="merchant1">
									<option value="${pageContext.request.contextPath}/mobileUser/finduserDetails?id=${merchant1.id}">
									
									
									<%-- <c:out value="${merchant1.mid.mid}~${merchant1.businessName}~${merchant1.role}"> --%>
									<%-- ${merchant1.id}~${merchant1.businessName}~${merchant1.email}~${merchant1.role} --%><%-- </c:out> --%>
									${merchant1.businessName}~${merchant1.username}~${merchant1.role}
									
									</option>

								</c:forEach>
								</optgroup>
							</select>
							<input type="text" class="form-control" id="businessName" readonly
								Placeholder="businessName" value="${mobileUser.businessName }"/>
							</div>
							</div>
							
						</div>	</div></div>
				<form:form method="GET" id="form1" action="${pageContext.request.contextPath}/mobileUser/regMobileUserDetails"
			name="form1" commandName="mobileUser">	
					
					<input type="hidden" class="form-control" path="businessName" id="businessName" readonly
								name="businessName" Placeholder="businessName" value="${mobileUser.businessName }"/>
					<c:if test="${mobileUser.businessName !=null }">
						<div class="card">
					
					<h3 class="card-title">MobileUser Details</h3>
					
						
							<div class="row">
						<div class="form-group col-md-4">
								<div class="form-group">
									<label for="contactName">Contact Name</label>
						
							<input type="text" class="form-control" path="contactName" id="contactName"
								name="contactName" Placeholder="contactName" value="${mobileUser.contactName }"/>

						</div>
					</div>
					<div class="form-group col-md-4">
						<div class="form-group">
							<label for="Renewal Period">Renewal Period(In Months)</label>
							<input type="text" class="form-control" name="renewalPeriod" id="renewalPeriod" 
							value="12" onblur="checkRenewal()" path="renewalPeriod" value="${mobileUser.renewalPeriod }"/>
				<%-- <form:select class="form-control"  name="renewalPeriod" path="renewalPeriod" id="renewalPeriod" style="width:100%" 
									onchange="checkRenewal()">
					<option value="">- Renewal Period -</option>
					<option value=1>1 Month</option>
                    <!-- <option value="61">2 Months</option> -->
                    <!-- <option value=3>3 Months</option>
                    <option value=6>6 Months</option> -->
                    <option value=12>1 Year</option>
                    <option value=24>2 Years</option>
 				</form:select>	 --%>
					</div>
				</div>
						
						<div class="form-group col-md-4">
			<div class="form-group">
				<label for="Expiry Date">Expiry Date</label>
				<input type="text" class="form-control" id="expiryDate" path="expiryDate"
				placeholder="expiryDate" name="expiryDate" value=""/>
			</div>
		</div>
						
				<div class="form-group col-md-4">
			<div class="form-group">
				<label for="Remarks">Remarks</label>
				<input type="text" class="form-control" id="remarks" path="remarks"
				placeholder="remarks" name="remarks"  value=""/>
			</div>
		</div>		
							
								</div></div></c:if>
					<c:if test="${mobileUser.mid !=null }">
					<input type="hidden" value="${mobileUser.mid }" name="mid" path="mid"/>
					<div class="card">
						<h3 class="card-title">MobileUser Ezywire Details 
						<c:if test="${mobileUser.mid !=null }"> <font color="blue">Mid: ${mobileUser.mid }</font></c:if></h3>

						<div class="row">

							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="tid">TID</label>
									<input type="text" class="form-control" id="tid"
										placeholder="tid" name="tid" path="tid"  value=""/>
									<div>
										<%-- <c:if test="${responseDatatid != null}"> --%>
											<H4 style="color: #ff4000;" align="center">${responseDatatid}</H4>
										<%-- </c:if> --%>
									</div>
								 </div>
							</div>
							<div class="form-group col-md-4">
										<div class="form-group">
											<label for="Email">Device Id</label>
											<input type="text" class="form-control" id="deviceId" value=""
												placeholder="deviceId" name="deviceId"  path="deviceId"/>
											<div>
												<c:if test="${responseDatadeviceid != null}">
													<H4 style="color: #ff4000;" align="center">${responseDatadeviceid}</H4>
												</c:if>
											</div>
										</div>
							</div>
							
							
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="Email">Reference No</label>
									<select class="form-control" name="referenceNo" path="referenceNo"
										id="referenceNo"  style="width:100%" value="">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${refNoList}" var="refNo">
											<option value="${refNo}">${refNo}</option>


										</c:forEach>
									</select>
								</div>
							</div>
							
							<div class="form-group col-md-4">
									<div class="form-group">
										<label for="Pre_Auth">Pre-Auth</label>
										
										<div class="radiobuttons">
											<label> <input type="radio" name="preAuth" path="preAuth"
													 value="Yes" id="preAuth" /> <span>Yes</span>
											</label> <label> <input type="radio" checked="checked"
													name="preAuth" value="No"  id="preAuth"  path="preAuth"/> <span>No</span>
											</label>
										</div>
									</div>
								</div>
							
							
						</div>
					</div>
</c:if>

					<c:if test="${mobileUser.motoMid !=null }">
					<input type="hidden" value="${mobileUser.motoMid}" name="motoMid" path="motoMid"/>
					<div class="card">
						<h3 class="card-title">MobileUser EzyMoto Details <font color="blue"> Mid: ${mobileUser.motoMid}</font> </h3>

						<div class="row">

							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="motoTid">TID</label>
									<input type="text" class="form-control" id="motoTid" value=""
										placeholder="motoTid" name="motoTid"  path="motoTid"/>
									<div>
										<c:if test="${responseDatamototid != null}">
											<H4 style="color: #ff4000;" align="center">${responseDatamototid}</H4>
										</c:if>
									</div>
								 </div>
							</div>
							<div class="form-group col-md-4">
										<div class="form-group">
											<label for="Email">Device Id</label>
											<input type="text" class="form-control" id="motodeviceId" value=""
												placeholder="motodeviceId" name="motodeviceId"  path="motodeviceId"/>
											<div>
												<c:if test="${responseDatamotodeviceid != null }">
													<H4 style="color: #ff4000;" align="center">${responseDatamotodeviceid}</H4>
												</c:if>
											</div>
										</div>
							</div>
							
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="Email">Reference No</label>
									<select class="form-control" name="motorefNo" path="motorefNo" value=""
										id="motorefNo"  style="width:100%">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${refNoList}" var="refNo">
											<option value="${refNo}">${refNo}</option>


										</c:forEach>
									</select>
								</div>
							</div>
							
							<div class="form-group col-md-4">
									<div class="form-group">
										<label for="Moto_Pre_Auth">Pre-Auth</label>
										
										<div class="radiobuttons">
											<label> <input type="radio" name="motopreAuth" path="motopreAuth"
													 value="Yes" id="motopreAuth" /> <span>Yes</span>
											</label> <label> <input type="radio" checked="checked"
													name="motopreAuth" value="No"  id="motopreAuth"  path="motopreAuth"/> <span>No</span>
											</label>
										</div>
									</div>
								</div>
							
						</div>
					</div>
					
					
					
					</c:if>

					<!-- 	start -->
<c:if test="${mobileUser.ezywayMid !=null }">
					<input type="hidden" value="${mobileUser.ezywayMid}" name="ezywayMid" path="ezywayMid"/>
					<div class="card">
						<h3 class="card-title">MobileUser EzyWay Details <font color="blue"> Mid: ${mobileUser.ezywayMid}</font> </h3>

						<div class="row">

							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="ezywayTid">TID</label>
									<input type="text" class="form-control" id="ezywayTid" value="${mobileUser.ezywayTid}"
										placeholder="ezywayTid" name="ezywayTid"  path="ezywayTid"/>
									<div>
										<c:if test="${responseDataezywaytid != null}">
											<H4 style="color: #ff4000;" align="center">${responseDataezywaytid}</H4>
										</c:if>
									</div>
								 </div>
							</div>
							<div class="form-group col-md-4">
										<div class="form-group">
											<label for="ezywaydeviceId">Device Id</label>
											<input type="text" class="form-control" id="ezywaydeviceId" value=""
												placeholder="ezywaydeviceId" name="ezywaydeviceId"  path="ezywaydeviceId"/>
											<div>
												<c:if test="${responseDatamotodeviceid != null }">
													<H4 style="color: #ff4000;" align="center">${responseDatamotodeviceid}</H4>
												</c:if>
											</div>
										</div>
							</div>
							
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="ezywayrefNo">Reference No</label>
									<select class="form-control" name="ezywayrefNo" path="ezywayrefNo" value=""
										id="ezywayrefNo"  style="width:100%">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${refNoList}" var="refNo">
											<option value="${refNo}">${refNo}</option>


										</c:forEach>
									</select>
								</div>
							</div>
							
							
							
						</div>
					</div>
					
					
					
					</c:if>

					<!-- end -->


					<!-- start -->
<c:if test="${mobileUser.ezyrecMid !=null }">
					<input type="hidden" value="${mobileUser.ezyrecMid}" name="ezyrecMid" path="ezyrecMid"/>
					<div class="card">
						<h3 class="card-title">MobileUser EzyRec Details <font color="blue"> Mid: ${mobileUser.ezyrecMid}</font> </h3>

						<div class="row">

							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="ezyrecTid">TID</label>
									<input type="text" class="form-control" id="ezyrecTid" value=""
										placeholder="ezyrecTid" name="ezyrecTid"  path="ezyrecTid"/>
									<div>
										<c:if test="${responseDataezyrectid != null}">
											<H4 style="color: #ff4000;" align="center">${responseDataezyrectid}</H4>
										</c:if>
									</div>
								 </div>
							</div>
							<div class="form-group col-md-4">
										<div class="form-group">
											<label for="ezyrecdeviceId">Device Id</label>
											<input type="text" class="form-control" id="ezyrecdeviceId" value=""
												placeholder="ezyrecdeviceId" name="ezyrecdeviceId"  path="ezyrecdeviceId"/>
											<div>
												<c:if test="${responseDataezyrecdeviceid != null }">
													<H4 style="color: #ff4000;" align="center">${responseDataezyrecdeviceid}</H4>
												</c:if>
											</div>
										</div>
							</div>
							
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="ezyrecrefNo">Reference No</label>
									<select class="form-control" name="ezyrecrefNo" path="ezyrecrefNo" value=""
										id="ezyrecrefNo"  style="width:100%">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${refNoList}" var="refNo">
											<option value="${refNo}">${refNo}</option>


										</c:forEach>
									</select>
								</div>
							</div>
							
							
							
						</div>
						<div class="row">
						<div class="form-group col-md-4">
									<div class="form-group">
										<label for="EzyPOD">Ezy-POD</label>
										
										<div class="radiobuttons">
											<label> <input type="radio" name="ezypod" path="ezypod"
													 value="Yes" id="ezypod" /> <span>Yes</span>
											</label> <label> <input type="radio" checked="checked"
													name="ezypod" value="No"  id="ezypod"  path="ezypod"/> <span>No</span>
											</label>
										</div>
									</div>
								</div>
						</div>
					</div>
					
					
					
					</c:if>

					<!-- end -->



			<c:if test="${mobileUser.ezypassMid !=null }">
					<input type="hidden" value="${mobileUser.ezypassMid}" name="ezypassMid" path="ezypassMid"/>
					<div class="card">
						<h3 class="card-title">MobileUser EzyPass Details <font color="blue">
						Mid:${mobileUser.ezypassMid}</font></h3>

						<div class="row">

							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="tid">TID</label>
									<input type="text" class="form-control" id="ezypassTid" value=""
										placeholder="ezypassTid" name="ezypassTid"  path="ezypassTid"/>
									<div>
										<c:if test="${responseDataezypasstid != null }">
											<H4 style="color: #ff4000;" align="center">${responseDataezypasstid}</H4>
										</c:if>
									</div>
								 </div>
							</div>
							<div class="form-group col-md-4">
										<div class="form-group">
											<label for="Email">Device Id</label>
											<input type="text" class="form-control" id="ezypassdeviceId" value=""
												placeholder="ezypassdeviceId" name="ezypassdeviceId"  path="ezypassdeviceId"/>
											<div>
												<c:if test="${responseDataepassdeviceid != null }">
													<H4 style="color: #ff4000;" align="center">${responseDataepassdeviceid}</H4>
												</c:if>
											</div>
										</div>
							</div>
							
							<div class="form-group col-md-4">
								<div class="form-group">
									<label for="Email">Reference No</label>
									<select class="form-control" name="ezypassrefNo" path="ezypassrefNo"
										id="ezypassrefNo" style="width:100%" value="">
										<option selected value=""><c:out value="refNo" /></option>
										<c:forEach items="${refNoList}" var="refNo">
											<option value="${refNo}">${refNo}</option>


										</c:forEach>
									</select>
								</div>
							</div>
							
							
							
						</div>
					</div>
					
					
					
					</c:if>


						<!-- <button class="btn btn-primary icon-btn" type="submit"
							onclick="return loadSelectData();">Submit</button> -->
							<!-- <button class="btn btn-primary icon-btn" type="submit"
							onclick="checkDevice();checkMotoDevice();checkEzywayDevice();checkEzyrecDevice();">Submit</button> -->
	
	<c:if test="${mobileUser.businessName !=null }">
	<button class="submitBtn" type="submit"
							onclick="return loadSelectData();">Submit</button></c:if>
							<%-- <c:if test="${mobileUser.businessName !=null }">
	<button class="btn btn-primary icon-btn" type="submit"
							>Submit</button></c:if> --%>
	</form:form>
					</div>
				</div>
			</div>
		


	</body>
	</html>
	
