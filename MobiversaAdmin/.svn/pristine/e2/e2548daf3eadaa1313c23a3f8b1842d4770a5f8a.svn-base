<%@page import="com.mobiversa.payment.controller.MobileUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

<style>

.drop-details .select-wrapper input.select-dropdown {
	border: 1px solid #005baa;
	border-radius: 16px;
	padding: 0 12px;
	color: #005baa;
}

.drop-details .select-wrapper .caret {
	fill: #005baa;
}

.error {
	color: red;
	font-weight: bold;
}


.select2-dropdown {
	border: 2px solid #2e5baa;
}

.select2-container--default .select2-selection--single {
	border: none;
}

.select-search .select-wrapper input {
	display: none !important;
}

.select-search .select-wrapper input {
	display: none !important;
}

.select2-results__options li {
	list-style-type: none;
	padding: 4px 0px 4px 4px !important;
	font-size: 18px !important;
}

.select2-results ul {
	max-height: 250px;
	curser: pointer;
}

.select2-results ul li:hover {
	background-color: #005baa !important;
	color: #fff !important;
}

.select-search-hidden .select2-container {
	display: none !important;
}


.tab_ul {
	display: flex;
	width: 100%;
}

.tab_ul li {
	display: flex;
	/* margin-left: 10px; */
	padding: 10px 20px;
	background-color: #eeeae9;
	width: 100%;
	cursor: pointer;
	margin: auto;
	display: table;
}

.tb_active {
	background-color: #005baa !important;
	color: #fff;
	text-align: center
}

.content_area {
	padding: 30px;
}

.page-wrapper {
	min-height: calc(50vh - 64px);
}

#submit-btn {
	background-color: #54b74a;
	color: #fff;
	border-radius: 10px;
	margin: auto;
	display: table;
}

.mob-br {
	display: none;
}

@media screen and (max-width:768px) {
	.tab_ul {
		display: block !important;
		width: auto;
	}
	.tab_ul li {
		float: left;
		display: block;
		width: auto;
	}
	.mob-br {
		display: block;
		white-space: pre-line;
	}
}


</style>

<script lang="JavaScript">

	$(document).ready(function() {
		// Initialize select2
		$("#selUser").select2();
		$(".select-filter").select2();

	});
	
	
	$(function() {
		$(".tab_ul li")
				.click(
						function() {
							$(".tab_ul li")
									.removeClass(
											"tb_active");
							$(this)
									.addClass(
											"tb_active");
							id = $(this).attr(
									"id");
							$(
									'.content_area .row')
									.hide();
							$('.' + id).show();

						});
	});

	/* $('.datepicker').pickadate(); */


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

	function checkBox() {
		var preAuth = document.getElementById("preAuth").value;
		alert("check preAuth:" + preAuth);
	}

	function checkDevice() {
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
	function checkMotoDevice() {
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

			if (motorefNo == null || motorefNo == '' || motorefNo == 'refNo') {
				alert("Please Select Moto Reference No");
				//form.submit = false; 
				return false;
			}
		}
		return true;
	}

	function checkEzypassDevice() {
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

	function checkEzyrecDevice() {
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

	function validateEzyWayDevice() {
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

			if (ezywayrefNo == null || ezywayrefNo == ''
					|| ezywayrefNo == 'refNo') {

				alert("Please Select EzyWay Ref No");
				//form.submit = false; 
				return false;
			}
		}
		return true;

	}

	function validateFormData() {
		//alert("validate form data");

		if (!checkDevice()) {
			return false;
		}
		if (!checkMotoDevice()) {
			return false;
		}

		if (!validateEzyWayDevice()) {
			return false;
		}
		if (!checkEzyrecDevice()) {
			return false;
		}
		if (!checkEzypassDevice()) {
			return false;
		}
		/* if(!checkDevice() || !checkMotoDevice() || !validateEzyWayDevice() || !checkEzyrecDevice() 
				|| !checkEzypassDevice()){
			return false;
		} */

		return true;
	}

	function validate() {
		//alert("check validation: "+validateFormData());
		if (validateFormData()) {
			return true;
		} else {
			return false;
		}
	}

	function loadSelectData() {

		var businessName = document.getElementById("businessName").value;
		//alert("businessName:"+businessName);
		if (businessName == '' || businessName == null) {
			alert("Please Fill BusinessName..");
			document.getElementById("businessName").focus();
			return false;
		}

		var contactName = document.getElementById("contactName").value;
		//alert("contactName:"+contactName);
		if (contactName == '' || contactName == null) {
			alert("Please Fill ContactName..");
			document.getElementById("contactName").focus();
			return false;

		}

		var renewalPeriod = document.getElementById("renewalPeriod").value;
		//alert("renewalPeriod:"+renewalPeriod);
		if (renewalPeriod == null || renewalPeriod == '') {
			alert("Please Fill Renewal Period");
			document.getElementById("renewalPeriod").focus();
			//form.submit = false; 
			return false;
		}

		var expiryDate = document.getElementById("expiryDate").value;
		//alert("expiryDate:"+expiryDate);
		if (expiryDate == '' || expiryDate == null) {
			alert("please fill Expiry Date");
			document.getElementById("datepicker").focus();
			return false;

		}

		var tid = document.getElementById("um_tid").value;
		var motoTid = document.getElementById("um_motoTid").value;
		var ezypassTid = document.getElementById("um_ezypassTid").value;
		var ezyrecTid = document.getElementById("um_ezyrecTid").value;
		var ezywayTid = document.getElementById("um_ezywayTid").value;

		/* alert("tid:"+tid);
		alert("motoTid:"+motoTid);
		alert("ezypassTid:"+ezypassTid);
		alert("ezyrecTid:"+ezyrecTid);
		alert("ezywayTid:"+ezywayTid);
		
		alert("tid:"+tid+"motoTid:"+motoTid+"ezypassTid:"+ezypassTid+
			  "ezyrecTid:"+ezyrecTid+"ezywayTid:"+ezywayTid); */

		if ((tid == '' || tid == null) && (motoTid == '' || motoTid == null)
				&& (ezypassTid == '' || ezypassTid == null)
				&& (ezywayTid == '' || ezywayTid == null)
				&& (ezyrecTid == '' || ezyrecTid == null)) {
			alert("No TID to Register..Please Fill Anyone TID Details..");
			return false;
		}

		var e = document.getElementById("um_refNo").value;
		var deviceId = document.getElementById("um_deviceId").value;

		if (tid != '' && tid != null) {
			//alert("check tid valid");
			if (!allnumeric(document.form1.um_tid, 8, 8)) {
				document.getElementById("um_tid").focus();
				return false;
			}

			if (deviceId != '' && deviceId != null) {
				if (!alphanumeric(document.form1.um_deviceId, 15, 16)) {
					document.getElementById("um_deviceId").focus();
					return false;
				}
			} else {
				alert("Please Select DeviceId");
				document.getElementById("um_deviceId").focus();
				return false;
			}

			if (e == null || e == '' || e == 'um_refNo') {

				alert("Please Select Reference No");
				document.getElementById("um_refNo").focus();
				//form.submit = false; 
				return false;
			}
		}

		var motorefNo = document.getElementById("hashkey1").value;
		var dtl1 = document.getElementById("DTL1").value;
		var motodeviceId = document.getElementById("um_motodeviceId").value;

		if (motoTid != '' && motoTid != null) {
			if (!allnumeric(document.form1.um_motoTid, 8, 8)) {
				document.getElementById("um_motoTid").focus();
				return false;
			}

			if (motodeviceId != '' && motodeviceId != null) {
				if (!alphanumeric(document.form1.um_motodeviceId, 15, 16)) {
					document.getElementById("um_motodeviceId").focus();
					return false;
				}
			} else {
				alert("Please Select Moto DeviceId");
				document.getElementById("um_motodeviceId").focus();
				return false;
			}

			if (motorefNo == null || motorefNo == '') {

				alert("Please Enter Hash KEY");
				document.getElementById("hashkey1").focus();
				//form.submit = false; 
				return false;
			}

			if (dtl1 == null || dtl1 == '') {

				alert("Please Enter Daily Transaction Limit");
				document.getElementById("DTL1").focus();
				//form.submit = false; 
				return false;
			}

		}

		var ezypassrefNo = document.getElementById("um_ezypassrefNo").value;
		var ezypassdeviceId = document.getElementById("um_ezypassdeviceId").value;

		if (ezypassTid != '' && ezypassTid != null) {
			if (!allnumeric(document.form1.um_ezypassTid, 8, 8)) {
				document.getElementById("um_ezypassTid").focus();
				return false;
			}

			if (ezypassdeviceId != '' && ezypassdeviceId != null) {
				if (!alphanumeric(document.form1.um_ezypassdeviceId, 15, 16)) {
					document.getElementById("um_ezypassdeviceId").focus();
					return false;
				}
			} else {
				alert("Please Select Ezypass DeviceId");
				document.getElementById("um_ezypassdeviceId").focus();
				return false;
			}

			if (ezypassrefNo == null || ezypassrefNo == ''
					|| ezypassrefNo == 'um_ezypassrefNo') {

				alert("Please Select Ezypass Reference No");
				document.getElementById("um_ezypassrefNo").focus();
				//form.submit = false; 
				return false;
			}

		}

		var ezywayrefNo = document.getElementById("hashkey").value;
		var dtl = document.getElementById("DTL").value;
		var url = document.getElementById("domainUrl").value;
		var ezywaydeviceId = document.getElementById("um_ezywaydeviceId").value;

		if (ezywayTid != '' && ezywayTid != null) {
			if (!allnumeric(document.form1.um_ezywayTid, 8, 8)) {
				document.getElementById("um_ezywayTid").focus();
				return false;
			}

			if (ezywaydeviceId != '' && ezywaydeviceId != null) {

				if (!alphanumeric(document.form1.um_ezywaydeviceId, 15, 16)) {
					document.getElementById("um_ezywaydeviceId").focus();
					return false;
				}
			} else {
				alert("Please Select Ezyway DeviceId");
				document.getElementById("um_ezywaydeviceId").focus();
				return false;
			}

			if (ezywayrefNo == null || ezywayrefNo == '') {

				alert("Please Enter Hash KEY");
				document.getElementById("hashkey").focus();
				//form.submit = false; 
				return false;
			}
			if (dtl == null || dtl == '') {

				alert("Please Enter Daily Transaction Limit");
				document.getElementById("DTL").focus();
				//form.submit = false; 
				return false;
			}
			if (url == null || url == '') {

				alert("Please Enter Domain URL");
				document.getElementById("domainUrl").focus();
				//form.submit = false; 
				return false;
			}

		}

		var ezyrecrefNo = document.getElementById("um_ezyrecrefNo").value;
		var ezyrecdeviceId = document.getElementById("um_ezyrecdeviceId").value;
		var rechashkey = document.getElementById("recHashkey").value;
		var recDTL = document.getElementById("recDTL").value;

		if (ezyrecTid != '' && ezyrecTid != null) {
			if (!allnumeric(document.form1.um_ezyrecTid, 8, 8)) {
				document.getElementById("um_ezyrecTid").focus();
				return false;
			}

			if (ezyrecdeviceId != '' && ezyrecdeviceId != null) {
				if (!alphanumeric(document.form1.um_ezyrecdeviceId, 15, 16)) {
					document.getElementById("um_ezyrecdeviceId").focus();
					return false;
				}
			} else {
				alert("Please Select Ezyrec DeviceId");
				document.getElementById("um_ezyrecdeviceId").focus();
				return false;
			}

			if (ezyrecrefNo == null || ezyrecrefNo == ''
					|| ezyrecrefNo == 'um_ezyrecrefNo') {

				alert("Please Select Ezyrec Reference No");
				document.getElementById("um_ezyrecrefNo").focus();
				//form.submit = false; 
				return false;
			}
			
			if (recDTL == null || recDTL == '') {

				alert("Please Enter Daily Transaction Limit");
				document.getElementById("recDTL").focus();
				//form.submit = false; 
				return false;
			}
			
			if (rechashkey == null || rechashkey == '') {

				alert("Please Enter Hashkey");
				document.getElementById("recHashkey").focus();
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
			alert("You have entered an invalid " + inputtxt.name + " address!");
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

		document.getElementById("expiryDate").value = day + "-" + monthIndex
				+ "-" + year;
		//.setMonth(CurrentDate.getMonth() + renewal);

		//alert("check renewal3:" + document.getElementById("expiryDate").value);
	}

	/*Find Merchant*/
	$(function() {
		// bind change event to select
		$('#merchantName').on('change', function() {
			var url = $(this).val(); // get selected value
			//alert(url);
			if (url) { // require a URL
				window.location = url; // redirect
				// alert(window.location);
			}
			return false;
		});
	});

	function loadDate(inputtxt, outputtxt) {
		// alert("test data123");
		var field = inputtxt.value;
		//var field1 = outputtxt.value;
		//alert(field+" : "+outputtxt.value);
		//document.getElementById("date11").value=field;
		outputtxt.value = field;
		//alert(outputtxt.value);
		// alert(document.getElementById("date11").value);
	}

	/* To display cards */
	function addrow() {

		var mid = document.getElementById("mid").value;
		var motoMid = document.getElementById("motoMid").value;
		var ezypassMid = document.getElementById("ezypassMid").value;
		var ezyrecMid = document.getElementById("ezyrecMid").value;
		var ezywayMid = document.getElementById("ezywayMid").value;

		var um_mid = document.getElementById("um_mid").value;
		var um_motoMid = document.getElementById("um_motoMid").value;
		var um_ezypassMid = document.getElementById("um_ezypassMid").value;
		var um_ezyrecMid = document.getElementById("um_ezyrecMid").value;
		var um_ezywayMid = document.getElementById("um_ezywayMid").value;

		/* alert("mid:"+mid);
		alert("motoMid:"+motoMid);
		alert("ezypassMid:"+ezypassMid);
		alert("ezyrecMid:"+ezyrecMid);
		alert("ezywayMid:"+ezywayMid);
		
		alert("um_mid:"+um_mid);
		alert("um_motoMid:"+um_motoMid);
		alert("um_ezypassMid:"+um_ezypassMid);
		alert("um_ezyrecMid:"+um_ezyrecMid);
		alert("um_ezywayMid:"+um_ezywayMid);  */

		/*Paydee*/

		if (mid == "") {
			document.getElementById('midCard').style.display = 'none';
		} else {
			document.getElementById('midCard').style.display == 'block';
		}

		if (motoMid == "") {
			document.getElementById('motoMidCard').style.display = 'none';
		} else {
			document.getElementById('motoMidCard').style.display == 'none';
		}

		if (ezypassMid == "") {
			document.getElementById('ezypassMidCard').style.display = 'none';
		} else {
			document.getElementById('ezypassMidCard').style.display == 'none';
		}

		if (ezyrecMid == "") {
			document.getElementById('ezyrecMidCard').style.display = 'none';
		} else {
			document.getElementById('ezyrecMidCard').style.display == 'none';
		}

		if (ezywayMid == "") {
			document.getElementById('ezywayMidCard').style.display = 'none';
		} else {
			document.getElementById('ezywayMidCard').style.display == 'none';
		}

		/*UMobile*/

		if (um_mid == "") {
			document.getElementById('um_midCard').style.display = 'none';
		} else {
			document.getElementById('um_midCard').style.display == 'block';
		}

		if (um_motoMid == "") {
			document.getElementById('um_motoMidCard').style.display = 'none';
		} else {
			document.getElementById('um_motoMidCard').style.display == 'block';
		}

		if (um_ezypassMid == "") {
			document.getElementById('um_ezypassMidCard').style.display = 'none';
		} else {
			document.getElementById('um_ezypassMidCard').style.display == 'block';
		}

		if (um_ezyrecMid == "") {
			document.getElementById('um_ezyrecMidCard').style.display = 'none';
		} else {
			document.getElementById('um_ezyrecMidCard').style.display == 'block';
		}

		if (um_ezywayMid == "") {
			document.getElementById('um_ezywayMidCard').style.display = 'none';
		} else {
			document.getElementById('um_ezywayMidCard').style.display == 'block';
		}

	}
</script>
</head>
<body onload="addrow();checkRenewal();"
	onclick="document.getElementById('emtpyTID').style.display='none';">
	<div class="container-fluid">
		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong> Add UMobile User </strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<c:if test="${ responseEmptyMids!=null }">

				<center>
					<p id="emtpyTID">
					<h3 style="color: red;">* Empty TID Details.. Please Fill
						Anyone TID Details...</h3>
					</p>
				</center>
			</c:if>
			<c:if test="${responseEmptyDeviceID!=null }">

				<center>
					<p id="emtpyDeviceID">
					<h3 style="color: red;">* Empty DeviceID Details..</h3>
					</p>
				</center>
			</c:if>
			<c:if test="${responseErrorData!=null }">
				<center>
					<p id="responseErrorData">
					<h3 style="color: red;">${responseErrorData}</h3>
					</p>
				</center>
			</c:if>
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">
						<div class="row">
							<div class="input-field col s12 m3 l3">Business Name</div>
							<div class="input-field col s12 m5 l5">
								<select name="merchantName"
									class="browser-default select-filter" id="merchantName"
									path="merchantName">
									<optgroup label="Business Names" style="width: 100%">
										<option selected value=""><c:out
												value="business Name" /></option>
										<c:forEach items="${merchant1}" var="merchant1">
											<option
												value="${pageContext.request.contextPath}/mobileUser/finduserDetails?id=${merchant1.id}">


												<%-- <c:out value="${merchant1.mid.mid}~${merchant1.businessName}~${merchant1.role}"> --%>
												<%-- ${merchant1.id}~${merchant1.businessName}~${merchant1.email}~${merchant1.role} --%><%-- </c:out> --%>
												${merchant1.businessName}~${merchant1.username}~${merchant1.merchantType}

											</option>

										</c:forEach>
									</optgroup>
								</select>
							</div>

						</div>

						<div class="row">
							<div class="input-field col s12 m3 l3"></div>
							
							<div class="input-field col s12 m3 l3 select-search">

								<input type="text" id="businessName" readonly
									Placeholder="businessName" value="${mobileUser.businessName }" />

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		

		<form:form method="GET" id="form1"
			action="${pageContext.request.contextPath}/mobileUser/regUMobileUserDetails"
			name="form1" commandName="mobileUser">


			<input type="hidden" class="form-control" path="businessName"
				id="businessName" readonly name="businessName"
				Placeholder="businessName" value="${mobileUser.businessName }" />

			<input type="hidden" class="form-control" path="merchantType"
				id="merchantType" readonly name="merchantType"
				Placeholder="merchantType" value="${mobileUser.merchantType }" />
			<c:if test="${mobileUser.businessName !=null }">
				<div class="row">
					<div class="col s12">
						<div class="card border-radius">
							<div class="card-content padding-card">
								<div class="d-flex align-items-center">

									<ul class="tab_ul">
										<li class="tb_active" id="mobileuser">MobileUser Details</li>
										<c:if
											test="${mobileUser.um_mid !=null && !empty mobileUser.um_mid}">
											<li id="umezywire">UM Ezywire Details</li>
										</c:if>
										<c:if
											test="${mobileUser.um_motoMid !=null && !empty mobileUser.um_motoMid}">
											<li id="umezymoto">UM Ezymoto Details</li>
										</c:if>
										<c:if
											test="${mobileUser.um_ezywayMid !=null && !empty mobileUser.um_ezywayMid}">
											<li id="umezyway">UM Ezyway Details</li>
										</c:if>
										<c:if
											test="${mobileUser.um_ezyrecMid !=null && !empty mobileUser.um_ezyrecMid}">
											<li id="umezyrec">UM Ezyrec Details</li>
										</c:if>
										<c:if
											test="${mobileUser.um_ezypassMid !=null && !empty mobileUser.um_ezypassMid}">
											<li id="umezypass">UM Ezypass Details</li>
										</c:if>
									</ul>

								</div>

								
								<div style="clear: both;"></div>

								<div class="content_area">

									<div class="row mobileuser">
										<div class="input-field col s12 m6 l6 " id="name">
											<input type="text" path="contactName" id="contactName"
												name="contactName" value="${mobileUser.contactName }" /> <label
												for="first_name">Contact Name</label>
										</div>
										<div class="input-field col s12 m6 l6 " id="name">
											<input id="renewalPeriod" type="text" onblur="checkRenewal()"
												path="renewalPeriod" value="${mobileUser.renewalPeriod}">
											<label for="name">Renewal Period(In Months)</label>
										</div>
										<div class="input-field col s12 m6 l6 ">
											<input type="text" id="remarks" path="remarks"
												placeholder="remarks" name="remarks" value="" /> <label
												for="name">Remarks</label>
										</div>

										<div class="input-field col s12 m6 l6 ">
											<input type="text" id="expiryDate" path="expiryDate"
												placeholder="expiryDate" name="expiryDate" value="" /> <label
												for="name">Expiry Date</label>
										</div>
									</div>


									<div class="row ezywire" id="midCard" style="display: none;">
										<input type="hidden" value="${mobileUser.mid }" name="mid"
											path="mid" />
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">MobileUser Ezywire Details
												Mid:</h5>
										</div>
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">${mobileUser.mid }</h5>
										</div>


										<div class="input-field col s12 m6 l6 ">
											<label for="tid">TID</label> <input type="text" id="tid"
												placeholder="tid" name="tid" path="tid" value="" />
											<c:if test="${responseDatatid != null}">
												<H4 style="color: #ff4000;" align="center">${responseDatatid}</H4>
											</c:if>
										</div>
										<div class="input-field col s12 m6 l6 ">
											<label for="Email">Device Id</label> <input type="text"
												class="form-control" id="deviceId" value=""
												placeholder="deviceId" name="deviceId" path="deviceId" />
											<c:if test="${responseDatadeviceid != null}">
												<H4 style="color: #ff4000;" align="center">${responseDatadeviceid}</H4>
											</c:if>
										</div>

										<div class="input-field col s12 m6 l6 ">

											<select name="referenceNo"
												class="browser-default select-filter" path="referenceNo"
												id="referenceNo" style="width: 100%" value="">
												<option selected value=""><c:out
														value="Reference No" /></option>
												<c:forEach items="${refNoList}" var="refNo">
													<option value="${refNo}">${refNo}</option>
												</c:forEach>
											</select>
											<!-- <label for="Email">Reference No</label>	  -->
										</div>

										<div class="input-field col s12 m6 l6 ">
											Pre - Auth :
											<p>
												<label><input type="radio" name="preAuth"
													path="preAuth" value="Yes" id="preAuth" /><span>Yes</span></label>
												<label><input type="radio" checked="checked"
													name="preAuth" value="No" id="preAuth" path="preAuth" /> <span>No</span></label>
											</p>

										</div>

									</div>

									<div class="row ezymoto" id="motoMidCard"
										style="display: none;">
										<input type="hidden" value="${mobileUser.motoMid}"
											name="motoMid" path="motoMid" />
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">MobileUser Ezymoto Details
												Mid:</h5>
										</div>
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">${mobileUser.motoMid}</h5>
										</div>


										<div class="input-field col s12 m6 l6 ">
											<label for="Email">TID</label> <input type="text"
												id="motoTid" value="" name="motoTid" path="motoTid" />
											<c:if test="${responseDatamototid != null}">
												<H4 style="color: #ff4000;" align="center">${responseDatamototid}</H4>
											</c:if>
										</div>
										<div class="input-field col s12 m6 l6 ">
											<label for="Email">Device Id</label> <input type="text"
												id="motodeviceId" value="" name="motodeviceId"
												path="motodeviceId" />
											<c:if test="${responseDatamotodeviceid != null }">
												<H4 style="color: #ff4000;" align="center">${responseDatamotodeviceid}</H4>
											</c:if>
										</div>

										<div class="input-field col s12 m6 l6 ">
											<select name="motorefNo"
												class="browser-default select-filter" path="motorefNo"
												value="" id="motorefNo" style="width: 100%">
												<option selected value=""><c:out
														value="Reference No" /></option>
												<c:forEach items="${refNoList}" var="refNo">
													<option value="${refNo}">${refNo}</option>


												</c:forEach>
											</select>
											<!-- <label for="Email">Reference No</label>	 -->
										</div>
										<div class="input-field col s12 m6 l6 ">
											Pre - Auth :
											<p>
												<label><input type="radio" name="motopreAuth"
													path="motopreAuth" value="Yes" id="motopreAuth" /> <span>Yes</span></label>
												<label> <input type="radio" checked="checked"
													name="motopreAuth" value="No" id="motopreAuth"
													path="motopreAuth" /> <span>No</span></label>
											</p>


										</div>

									</div>



									<div class="row ezyway" id="ezywayMidCard"
										style="display: none;">
										<input type="hidden" value="${mobileUser.ezywayMid}"
											name="ezywayMid" path="ezywayMid" />
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">MobileUser Ezyway Details
												Mid:</h5>
										</div>
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">${mobileUser.ezywayMid}</h5>
										</div>


										<div class="input-field col s12 m6 l6 ">
											<label for="ezywayTid">TID</label> <input type="text"
												id="ezywayTid" value="${mobileUser.ezywayTid}"
												placeholder="ezywayTid" name="ezywayTid" path="ezywayTid" />

											<c:if test="${responseDataezywaytid != null}">
												<H4 style="color: #ff4000;" align="center">${responseDataezywaytid}</H4>
											</c:if>


										</div>
										<div class="input-field col s12 m6 l6 ">
											<label for="ezywaydeviceId">Device Id</label> <input
												type="text" id="ezywaydeviceId" value=""
												placeholder="ezywaydeviceId" name="ezywaydeviceId"
												path="ezywaydeviceId" />

											<c:if test="${responseDatamotodeviceid != null }">
												<H4 style="color: #ff4000;" align="center">${responseDatamotodeviceid}</H4>
											</c:if>

										</div>


										<div class="input-field col s12 m6 l6 ">
											<!-- <label for="ezywayrefNo">Reference No</label> -->
											<select name="ezywayrefNo"
												class="browser-default select-filter" path="ezywayrefNo"
												value="" id="ezywayrefNo">
												<option selected value=""><c:out
														value="Reference No" /></option>
												<c:forEach items="${refNoList}" var="refNo">
													<option value="${refNo}">${refNo}</option>
												</c:forEach>
											</select>
										</div>
									</div>

									<div class="row ezyrec" id="ezyrecMidCard"
										style="display: none;">
										<input type="hidden" value="${mobileUser.ezyrecMid}"
											name="ezyrecMid" path="ezyrecMid" />
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">MobileUser Ezyrec Details
												Mid:</h5>
										</div>
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">${mobileUser.ezyrecMid}</h5>
										</div>


										<div class="input-field col s12 m6 l6 ">
											<label for="ezyrecTid">TID</label> <input type="text"
												id="ezyrecTid" value="" placeholder="ezyrecTid"
												name="ezyrecTid" path="ezyrecTid" />

											<c:if test="${responseDataezyrectid != null}">
												<H4 style="color: #ff4000;" align="center">${responseDataezyrectid}</H4>
											</c:if>


										</div>
										<div class="input-field col s12 m6 l6 ">
											<label for="ezyrecdeviceId">Device Id</label> <input
												type="text" id="ezyrecdeviceId" value=""
												placeholder="ezyrecdeviceId" name="ezyrecdeviceId"
												path="ezyrecdeviceId" />

											<c:if test="${responseDataezyrecdeviceid != null }">
												<H4 style="color: #ff4000;" align="center">${responseDataezyrecdeviceid}</H4>
											</c:if>

										</div>


										<div class="input-field col s12 m6 l6 ">

											<select name="ezyrecrefNo"
												class="browser-default select-filter" path="ezyrecrefNo"
												value="" id="ezyrecrefNo" style="width: 100%">
												<option selected value=""><c:out
														value="Reference No" /></option>
												<c:forEach items="${refNoList}" var="refNo">
													<option value="${refNo}">${refNo}</option>
												</c:forEach>
											</select>
											<!-- <label for="ezyrecrefNo">Reference No</label> -->
										</div>

										<div class="input-field col s12 m6 l6 ">
											Ezy-POD :
											<p>
												<label><input type="radio" name="ezypod"
													path="ezypod" value="Yes" id="ezypod" /><span>Yes</span></label> <label><input
													type="radio" checked="checked" name="ezypod" value="No"
													id="ezypod" path="ezypod" /> <span>No</span></label>
											</p>


										</div>
									</div>


									<div class="row ezypass" id="ezypassMidCard"
										style="display: none;">
										<input type="hidden" value="${mobileUser.ezypassMid}"
											name="ezypassMid" path="ezypassMid" />
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">MobileUser Ezypass Details
												Mid:</h5>
										</div>
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">${mobileUser.ezypassMid}</h5>
										</div>


										<div class="input-field col s12 m6 l6 ">
											<label for="ezypassTid">TID</label> <input type="text"
												id="ezypassTid" value="" placeholder="ezypassTid"
												name="ezypassTid" path="ezypassTid" />
											<c:if test="${responseDataezypasstid != null }">
												<H4 style="color: #ff4000;" align="center">${responseDataezypasstid}</H4>
											</c:if>

										</div>
										<div class="input-field col s12 m6 l6 ">
											<label for="ezypassdeviceId">Device Id</label> <input
												type="text" class="form-control" id="ezypassdeviceId"
												value="" placeholder="ezypassdeviceId"
												name="ezypassdeviceId" path="ezypassdeviceId" />
											<c:if test="${responseDataepassdeviceid != null }">
												<H4 style="color: #ff4000;" align="center">${responseDataepassdeviceid}</H4>
											</c:if>
										</div>


										<div class="input-field col s12 m6 l6 ">

											<select name="ezypassrefNo"
												class="browser-default select-filter" path="ezypassrefNo"
												id="ezypassrefNo" style="width: 100%" value="">
												<option selected value=""><c:out
														value="Reference No" /></option>
												<c:forEach items="${refNoList}" var="refNo">
													<option value="${refNo}">${refNo}</option>


												</c:forEach>
											</select>
											<!-- <label for="ezypassrefNo">Reference No</label> -->
										</div>

									</div>



									<div class="row umezywire" id="um_midCard"
										style="display: none;">

										<input type="hidden" value="${mobileUser.um_mid }"
											name="um_mid" path="um_mid" />
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">MobileUser UM Ezywire Details
												Mid:</h5>
										</div>
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">${mobileUser.um_mid }</h5>
										</div>


										<div class="input-field col s12 m6 l6 ">
											<label for="um_tid">TID</label> <input type="text"
												id="um_tid" placeholder="um_tid" name="um_tid" path="um_tid"
												value="" />

											<c:if test="${responseDataUM_tid != null}">
												<H4 style="color: #ff4000;" align="center">${responseDataUM_tid}</H4>
											</c:if>

										</div>

										<div class="input-field col s12 m6 l6 ">
											<label for="um_deviceId">Device Id</label> <input type="text"
												id="um_deviceId" value="" placeholder="um_deviceId"
												name="um_deviceId" path="um_deviceId" />
											<c:if test="${responseDataUM_deviceid != null}">
												<H4 style="color: #ff4000;" align="center">${responseDataUM_deviceid}</H4>
											</c:if>

										</div>


										<div class="input-field col s12 m6 l6 ">
											<select name="um_refNo" class="browser-default select-filter"
												path="um_refNo" id="um_refNo" style="width: 100%" value="">
												<option selected value=""><c:out
														value="Reference No" /></option>
												<c:forEach items="${umrefNoList}" var="refNo">
													<option value="${refNo}">${refNo}</option>


												</c:forEach>
											</select>
											<!-- <label for="um_refNo">Reference No</label> -->
										</div>

										<div class="input-field col s12 m6 l6 ">
											Pre-Auth :
											<p>
												<label><input type="radio" name="um_preAuth"
													path="um_preAuth" value="Yes" id="um_preAuth" /> <span>Yes</span></label>
												<label><input type="radio" checked="checked"
													name="um_preAuth" value="No" id="um_preAuth"
													path="um_preAuth" /> <span>No</span></label>
											</p>


										</div>
									</div>

									<div class="row umezymoto" id="um_motoMidCard"
										style="display: none;">
										<input type="hidden" value="${mobileUser.um_motoMid}"
											name="um_motoMid" path="um_motoMid" />
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">MobileUser UM Ezymoto Details
												Mid:</h5>
										</div>
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">${mobileUser.um_motoMid }</h5>
										</div>


										<div class="input-field col s12 m6 l6 ">
											<label for="um_motoTid">TID</label> <input type="text"
												id="um_motoTid" value="" placeholder="um_motoTid"
												name="um_motoTid" path="um_motoTid" />
											<c:if test="${responseDataUM_mototid != null}">
												<H4 style="color: #ff4000;" align="center">${responseDataUM_mototid}</H4>
											</c:if>
										</div>

										<div class="input-field col s12 m6 l6 ">
											<label for="um_motodeviceId">Device Id</label> <input
												type="text" id="um_motodeviceId" value=""
												placeholder="um_motodeviceId" name="um_motodeviceId"
												path="um_motodeviceId" />
											<c:if test="${responseDataUM_motodeviceid != null }">
												<H4 style="color: #ff4000;" align="center">${responseDataUM_motodeviceid}</H4>
											</c:if>
										</div>

										<div class="input-field col s12 m6 l6 ">
											<label for="hashKey">HashKey</label> <input type="text"
												id="hashkey1" value="${mobileUser.hashkey}"
												placeholder="hashkey" name="hashkey1" path="hashkey1" />
										</div>
										<div class="input-field col s12 m6 l6 ">
											<label for="DTL">DTL</label> <input type="text" id="DTL1"
												value="${mobileUser.DTL}" placeholder="DTL" name="DTL1"
												path="DTL1" />
										</div>


										<div class="input-field col s12 m6 l6 ">

											<select name="um_motorefNo"
												class="browser-default select-filter" path="um_motorefNo"
												value="" id="um_motorefNo" style="width: 100%">
												<option selected value=""><c:out
														value="Reference No" /></option>
												<c:forEach items="${umrefNoList}" var="refNo">
													<option value="${refNo}">${refNo}</option>
												</c:forEach>
											</select>
											<!-- <label for="um_motorefNo">Reference No</label> -->
										</div>

										<div class="input-field col s12 m6 l6 ">
											Pre-Auth :
											<p>
												<label><input type="radio" name="motopreAuth"
													path="motopreAuth" value="Yes" id="motopreAuth" /> <span>Yes</span></label>
												<label><input type="radio" checked="checked"
													name="motopreAuth" value="No" id="motopreAuth"
													path="motopreAuth" /> <span>No</span></label>
											</p>


										</div>

										<div class="input-field col s12 m6 l6 ">
											EZYMOTO VCC :
											<p>
												<label><input type="radio" name="vcc" path="vcc"
													value="Yes" id="vcc" /> <span>Yes</span></label> <label><input
													type="radio" checked="checked" name="vcc" value="No"
													id="vcc" path="vcc" /><span>No</span></label>
											</p>


										</div>
									</div>

									<div class="row umezyway" id="um_ezywayMidCard"
										style="display: none;">
										<input type="hidden" value="${mobileUser.um_ezywayMid}"
											name="um_ezywayMid" path="um_ezywayMid" />
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">MobileUser UM Ezyway Details
												Mid:</h5>
										</div>
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">${mobileUser.um_ezywayMid }</h5>
										</div>


										<div class="input-field col s12 m6 l6 ">
											<label for="um_ezywayTid">TID</label> <input type="text"
												id="um_ezywayTid" value="${mobileUser.um_ezywayTid}"
												placeholder="um_ezywayTid" name="um_ezywayTid"
												path="um_ezywayTid" />
											<c:if test="${responseDataUM_ezywaytid != null}">
												<H4 style="color: #ff4000;" align="center">${responseDataUM_ezywaytid}</H4>
											</c:if>
										</div>

										<div class="input-field col s12 m6 l6 ">
											<label for="um_ezywaydeviceId">Device Id</label> <input
												type="text" id="um_ezywaydeviceId" value=""
												placeholder="um_ezywaydeviceId" name="um_ezywaydeviceId"
												path="um_ezywaydeviceId" />
											<c:if test="${responseDataUM_motodeviceid != null }">
												<H4 style="color: #ff4000;" align="center">${responseDataUM_motodeviceid}</H4>
											</c:if>
										</div>


										<div class="input-field col s12 m6 l6 ">
											<select name="um_ezywayrefNo"
												class="browser-default select-filter" path="um_ezywayrefNo"
												value="" id="um_ezywayrefNo" style="width: 100%">
												<option selected value=""><c:out
														value="Reference No" /></option>
												<c:forEach items="${umrefNoList}" var="refNo">
													<option value="${refNo}">${refNo}</option>
												</c:forEach>
											</select>
											<!-- <label for="um_ezywayrefNo">Reference No</label> -->
										</div>

										<div class="input-field col s12 m6 l6 ">
											<label for="hashKey">HashKey</label> <input type="text"
												id="hashkey" value="${mobileUser.hashkey}"
												placeholder="hashkey" name="hashkey" path="hashkey" />
										</div>
										<div class="input-field col s12 m6 l6 ">
											<label for="DTL">DTL</label> <input type="text" id="DTL"
												value="${mobileUser.DTL}" placeholder="DTL" name="DTL"
												path="DTL" />
										</div>

										<div class="input-field col s12 m6 l6 ">
											<label for="domainUrl">Domain URL</label> <input type="text"
												id="domainUrl" value="${mobileUser.domainUrl}"
												placeholder="domainUrl" name="domainUrl" path="domainUrl" />
										</div>

									</div>

									<div class="row umezyrec" id="um_ezyrecMidCard"
										style="display: none;">
										<input type="hidden" value="${mobileUser.um_ezyrecMid}"
											name="um_ezyrecMid" path="um_ezyrecMid" />
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">MobileUser UM Ezyrec Details
												Mid:</h5>
										</div>
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">${mobileUser.um_ezyrecMid }</h5>
										</div>


										<div class="input-field col s12 m6 l6 ">
											<label for="um_ezyrecTid">TID</label> <input type="text"
												id="um_ezyrecTid" value="" placeholder="um_ezyrecTid"
												name="um_ezyrecTid" path="um_ezyrecTid" />
											<c:if test="${responseDataUM_ezyrectid != null}">
												<H4 style="color: #ff4000;" align="center">${responseDataUM_ezyrectid}</H4>
											</c:if>
										</div>

										<div class="input-field col s12 m6 l6 ">
											<label for="um_ezyrecdeviceId">Device Id</label> <input
												type="text" id="um_ezyrecdeviceId" value=""
												placeholder="um_ezyrecdeviceId" name="um_ezyrecdeviceId"
												path="um_ezyrecdeviceId" />
											<c:if test="${responseDataUM_ezyrecdeviceid != null }">
												<H4 style="color: #ff4000;" align="center">${responseDataUM_ezyrecdeviceid}</H4>
											</c:if>
										</div>


										<div class="input-field col s12 m6 l6 ">

											<select name="um_ezyrecrefNo"
												class="browser-default select-filter" path="um_ezyrecrefNo"
												value="" id="um_ezyrecrefNo" style="width: 100%">
												<option selected value=""><c:out
														value="Reference No" /></option>
												<c:forEach items="${umrefNoList}" var="refNo">
													<option value="${refNo}">${refNo}</option>
												</c:forEach>
											</select>
											<!-- <label for="um_ezyrecrefNo">Reference No</label> -->
										</div>
										
										<div class="input-field col s12 m6 l6 ">
											<label for="hashKey">HashKey</label> <input type="text"
												id="recHashkey" value="${mobileUser.hashkey}"
												placeholder="recHashkey" name="recHashkey" path="recHashkey" />
										</div>
										<div class="input-field col s12 m6 l6 ">
											<label for="DTL">DTL</label> <input type="text" id="recDTL"
												value="${mobileUser.DTL}" placeholder="recDTL" name="recDTL"
												path="recDTL" />
										</div>

									</div>
									<div class="row umezypass" id="um_ezypassMidCard"
										style="display: none;">
										<input type="hidden" value="${mobileUser.um_ezypassMid}"
											name="um_ezypassMid" path="um_ezypassMid" />
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">MobileUser UM Ezypass Details
												Mid:</h5>
										</div>
										<div class="col s12 m4 l6">
											<h5 style="color: #005baa">${mobileUser.um_ezypassMid }</h5>
										</div>


										<div class="input-field col s12 m6 l6 ">
											<label for="um_ezypassTid">TID</label> <input type="text"
												id="um_ezypassTid" value="" placeholder="um_ezypassTid"
												name="um_ezypassTid" path="um_ezypassTid" />
											<c:if test="${responseDataUM_ezypasstid != null }">
												<H4 style="color: #ff4000;" align="center">${responseDataUM_ezypasstid}</H4>
											</c:if>
										</div>

										<div class="input-field col s12 m6 l6 ">
											<label for="um_ezypassdeviceId">Device Id</label> <input
												type="text" id="um_ezypassdeviceId" value=""
												placeholder="um_ezypassdeviceId" name="um_ezypassdeviceId"
												path="um_ezypassdeviceId" />
											<c:if test="${responseDataUM_epassdeviceid != null }">
												<H4 style="color: #ff4000;" align="center">${responseDataUM_epassdeviceid}</H4>
											</c:if>
										</div>


										<div class="input-field col s12 m6 l6 ">
											<select name="um_ezypassrefNo"
												class="browser-default select-filter" path="um_ezypassrefNo"
												id="um_ezypassrefNo" style="width: 100%" value="">
												<option selected value=""><c:out
														value="Reference No" /></option>
												<c:forEach items="${umrefNoList}" var="refNo">
													<option value="${refNo}">${refNo}</option>
												</c:forEach>
											</select>
											<!-- <label for="um_ezypassrefNo">Reference No</label> -->
										</div>

									</div>



								</div>
								<c:if test="${mobileUser.businessName !=null }">
									<button type="submit" class="btn btn-success" id="submit-btn"
										onclick="return loadSelectData();">Submit</button>
									<br />
								</c:if>

							</div>
						</div>
					</div>
				</div>
			</c:if>

		</form:form>

	</div>
</body>
</html>


