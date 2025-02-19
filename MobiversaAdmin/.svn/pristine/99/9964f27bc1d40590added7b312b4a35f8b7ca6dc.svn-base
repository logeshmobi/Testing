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
<script type="text/javascript">
function load1(){
	var url = "${pageContext.request.contextPath}/grabPay/addGrabPay";
	$(location).attr('href',url);
} 

</script>
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
	
	<div class="container-fluid">  
<div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>GrabPay User Review</strong></h3>
          </div>
          
          
        </div>
      </div>
    </div>
    </div>

<form:form method="POST" id="form1" action="${pageContext.request.contextPath}/grabPay/grabPayUserDetailsConfirm"
			name="form1" commandName="mobileUser">	
			<div class="row">
	<div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        <div class="d-flex align-items-center">
				           <h5> GrabPay User Details</h5>
				          </div>
					
					
						
					<div class="row">
					<div class="input-field col s3 ">
									<label for="contactName">GrabPay MID</label>
						
							<input type="text"  path="ezyrecMid" id="gPayMid" readonly="readonly"
								name="gPayMid" Placeholder="GrabPay MID" value="${mobileUser.gPayMid }"/>
							<input type="hidden" name="merchantID" id="merchantID" 
								 path="merchantID" value="${mobileUser.merchantID }"/>
								 <input type="hidden" class="form-control" name="id" id="id" 
								 path="id" value="${mobileUser.id }"/>
				
								
						</div>
						<div class="input-field col s3 ">
									<label for="contactName">Contact Name</label>
						
							<input type="text" path="contactName" id="contactName" readonly="readonly"
								name="contactName" Placeholder="contactName" value="${mobileUser.contactName }"/>

								
						</div>
						<div class="input-field col s3 ">
								<label for="Renewal Period">Renewal Period(In Months)</label>
								<input type="text"  name="renewalPeriod" id="renewalPeriod"  readonly="readonly" 
								value="1200" onblur="checkRenewal()" path="renewalPeriod" value="${mobileUser.renewalPeriod }"/>
				
							
						</div>
						
						<div class="input-field col s3 ">
								<label for="Expiry Date">Expiry Date</label>
								<input type="text"  id="expiryDate" path="expiryDate" readonly="readonly" 
									placeholder="expiryDate" name="expiryDate" value="${mobileUser.expiryDate }"/>
							
						</div>
						
						<div class="input-field col s3 ">
								<label for="Remarks">Remarks</label>
								<input type="text" id="remarks" path="remarks" readonly="readonly" 
									placeholder="remarks" name="remarks"  value="${mobileUser.remarks }"/>
							</div>
							
							
					</div></div></div></div></div>
					
		<div class="row">
	<div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        <div class="d-flex align-items-center">
				           <h5> GrabPay MobileUser Details</h5>
				          </div>			
					
					
						
						<div class="row">

							<div class="input-field col s3 ">
									<label for="gPayTid">TID</label>
									<input type="text"  id="gPayTid" readonly="readonly" 
										placeholder="GrabPay TID" name="gPayTid" path="gPayTid"  value="${mobileUser.gPayTid }"/>
									
								 
							</div>
							<div class="input-field col s3 ">
											<label for="gPaydeviceId">Device Id</label>
											<input type="text" id="gPaydeviceId" value="${mobileUser.gPaydeviceId }"
												placeholder="gPaydeviceId" name="gPaydeviceId"  path="gPaydeviceId" readonly="readonly" />
									
							</div>
							
							<div class="input-field col s3 ">
											<label for="gPayrefNo">MobileUser Name</label>
											<input type="text" id="gPayrefNo" value="${mobileUser.gPayrefNo }"
												placeholder="gPayrefNo" name="gPayrefNo"  path="gPayrefNo" readonly="readonly"/>
									</div>
							</div>
							
							
							
							
						


						
	
	<button class="btn btn-primary icon-btn" type="submit"	onclick="return loadSelectData();">Submit</button>
	<input type="button"  class="btn btn-default icon-btn"  onclick="load1()" value="Cancel">	
	</div></div></div></div>		
	</form:form>
					</div>
				
	</body>
	</html>
	
