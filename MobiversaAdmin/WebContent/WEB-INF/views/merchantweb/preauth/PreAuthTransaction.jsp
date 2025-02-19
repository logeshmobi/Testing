
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">


<!-- <meta charset="utf-8"> -->
<!-- <meta name="viewport" content="width=device-width, initial-scale=1"> -->

<meta name="viewport"
	content="width=device-width, minimum-scale=1.0, maximum-scale = 1.0, user-scalable = no">

<style>
.td {
	text-align: right;
}

#main-wrapper {
	background-color: #37373758;
}
@
midea

 

screen


(
max-width


:


40
%
)
</style>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/intlTelInput.css">

<script lang="JavaScript">
	function checkExpireDevice() {

		var expDate = document.getElementById('expDate').value;

		if (expDate == 0) {
			alert("Your Device has Expired for this username..\nPlease Renewal it");
			return false;
		} else {
			return true;
		}

	}
</script>

<script type="text/javascript">
	window.onload = getLocation();

	function getLocation() {
		/* var latitudeAndLongitude=document.getElementById("latitudeAndLongitude"),
		location={
		   latitude:'',
		   longitude:''
		};
		 */

		// alert("getlocation.");
		if (navigator.geolocation) {
			navigator.geolocation.getCurrentPosition(showPosition);
		} else {
			latitudeAndLongitude.innerHTML = "Geolocation is not supported by this browser.";
		}
	}

	function showPosition(position) {
		location.latitude = position.coords.latitude;
		location.longitude = position.coords.longitude;
		document.getElementById("latitude").value = position.coords.latitude;
		document.getElementById("longitude").value = position.coords.longitude;
		/*  latitudeAndLongitude.innerHTML="Latitude: " + position.coords.latitude + 
		 "<br>Longitude: " + position.coords.longitude;  */
		var geocoder = new google.maps.Geocoder();
		var latLng = new google.maps.LatLng(location.latitude,
				location.longitude);

		if (geocoder) {
			geocoder.geocode({
				'latLng' : latLng
			}, function(results, status) {
				if (status == google.maps.GeocoderStatus.OK) {
					console.log(results[0].formatted_address);
					$('#address').html(
							'Address:' + results[0].formatted_address);
				} else {
					$('#address').html('Geocoding failed: ' + status);
					console.log("Geocoding failed: " + status);
				}
			}); //geocoder.geocode()
		}
	}
</script>


<!-- <script lang="JavaScript"> -->
<!--  	function phcodeon() { -->



<!-- 	 var phcode1 = document.getElementById("phcode1").value; -->
<!-- 	alert("phcode: "+phcode1); -->
<!--  		//document.getElementById("mobile").value = phcode1 + " "; -->
<!-- 		//document.getElementById("mobile").focus(); -->

<!--  	} -->
<!-- </script> -->

<script lang="JavaScript">
	$(document)
			.ready(
					function() {
						$('#mobile')
								.blur(
										function(e) {
											if (!validatePhone('mobile')) {
												$('#spnPhoneStatus')
														.html(
																'*<font color=blue>Please specify a valid phone number..!</font>');
												$('#spnPhoneStatus').css(
														'color', 'red');
											} else {
												$('#spnPhoneStatus').html('');
												document.getElementById("code")
														.focus();
												//$('#spnPhoneStatus').css('color', 'green'); 
												/* var str1 = document.getElementById("phno1").value;
												 var str2 = document.getElementById("phno2").value;
												 var str=str1+str2;
												str = str1.replace (/,/g, "");
												document.getElementById("phno1").value=str1;
												alert("phno: "+document.getElementById("phno").value); */
											}
										});
					});

	function validatePhone(txtPhone) {

		var phone_number = document.getElementById("mobile").value;
		a = phone_number.replace(/\s+/g, "");
		// alert("length a: "+a.length);
		//var filter = /^\d+$/;
		//var filter = /^[0-9]+$/;
		// var filter = /([0-9]{10})|(\([0-9]{3}\)\s+[0-9]{3}\-[0-9]{4})/;
		var filter = /^((\+[1-9]{1,4}[ \-]*)|(\([0-9]{2,3}\)[ \-]*)|([0-9]{2,4})[ \-]*)*?[0-9]{3,4}?[ \-]*[0-9]{3,4}?$/;
		if (filter.test(a) && a.length > 9) {
			return true;
		} else {
			//document.getElementById("phno").value=' ';
			/*  document.getElementById("phno").focus(); */
			return false;
		}
	}
</script>

<script lang="JavaScript">
	function loadCancelData() {
		//alert("fcancel data");

		event.preventDefault();
		document.location.href = "${pageContext.request.contextPath}/admmerchant/merdashBoard";
		form.submit;
	}
</script>

<script>
	$(document)
			.ready(
					function() {
						// bind change event to select
						$('#tids')
								.on(
										'change',
										function() {
											// alert(this.value);
											var data = this.value.split(",");
											var tid = data[0];
											var expDate = data[1];
											document.getElementById('tid').value = data[0];
											document.getElementById('expDate').value = data[1];

											if (expDate == 0) {
												alert("Your Device has Expired for this username..\nPlease Renewal it");
												return false;
											}

										});
					});
</script>

<script lang="JavaScript">
	$(document).ready(function() {

		$("#c_code").change(function() {

			ValidateAmount();
		});

	});

	function ValidateAmount() {
		var amount = document.getElementById("c_code").value;

		if (amount.includes(",")) {

			amount = amount.replace(/,/g, "");

		}
		if (!isNaN(amount)) {
			if (amount.includes(".")) {

				var array1 = [];
				array1 = amount.split(".");
				//alert("array1[1]: "+array1[1]);
				if (array1[0] == '' || array1[0] == null) {
					array1[0] = "0";
				}
				if (array1[1].length >= 2) {
					amount = array1[0] + "." + array1[1].substring(0, 2);
					//alert("Yours Requested Amount to do Transaction "+amount);
					document.getElementById("c_code").value = amount;
					return true;

				} else if (array1[1].length >= 1) {
					amount = amount + "0";
					//alert("Yours Requested Amount to do Transaction "+amount);
					document.getElementById("c_code").value = amount;
					return true;

				} else {
					amount = amount + "00";
					// alert("Yours Requested Amount to do Transaction "+amount);
					document.getElementById("c_code").value = amount;
					return true;
				}

			} else {
				amount = amount + ".00";
				// alert("Yours Requested Amount to do Transaction "+amount);
				document.getElementById("c_code").value = amount;
				return true;
			}
		} else {

			alert("Enter Proper Amount.");
			document.getElementById("c_code").focus();
			return false;
		}

	}
</script>

<script lang="JavaScript">
	function SubmitFormAmount() {
		var amount = document.getElementById("c_code").value;

		if (amount.includes(",")) {

			amount = amount.replace(/,/g, "");

		}
		if (!isNaN(amount)) {
			if (amount.includes(".")) {

				var array1 = [];
				array1 = amount.split(".");
				//alert("array1[1]: "+array1[1]);
				if (array1[1].length >= 2) {
					amount = array1[0] + "." + array1[1].substring(0, 2);
					//alert("Yours Requested Amount to do Transaction "+amount);
					document.getElementById("c_code").value = amount;
					return true;

				} else if (array1[1].length >= 1) {
					amount = amount + "0";
					// alert("Yours Requested Amount to do Transaction "+amount);
					document.getElementById("c_code").value = amount;
					return true;
				} else {
					amount = amount + "00";
					//alert("Yours Requested Amount to do Transaction "+amount);
					document.getElementById("c_code").value = amount;
					return true;
				}

			} else {
				amount = amount + ".00";
				// alert("Yours Requested Amount to do Transaction "+amount);
				document.getElementById("c_code").value = amount;
				return true;
			}
		} else {

			alert("Enter Proper Amount.");
			document.getElementById("c_code").focus();
			return false;
		}

	}
</script>

<script lang="JavaScript">
	$(document)
			.ready(
					function() {
						$("#form1")
								.submit(
										function(event) {
											//alert("validate form");
											//alert(validateForm());
											/* 	function validateForm1(){ */

											var amount = document
													.getElementById("c_code").value;

											if (amount < 5) {

												alert("Enter Amount greater than 5RM.");
												document.getElementById(
														"c_code").focus();
												return false;

											}

											if (validateForm()
													&& validatemailmobno()
													&& SubmitFormAmount()
													&& checkExpireDevice()) {

												var r = confirm("PREAUTH Transaction Amount is RM "
														+ document
																.getElementById("c_code").value
														+ " \nDo You Want to Submit the Transaction..?");
												//JSalert();
												//alert("confirmed transaction: "+r);
												if (r == true) {

													return true;
													//$("form").submit();
												} else {
													return false;
												}

											} else {
												return false;
											}

										});
					});

	//}

	function validatemailmobno() {

		var email = document.getElementById("email").value;
		var mobileno = document.getElementById('mobile').value;

		if ((email == null || email == '')
				&& (mobileno == null || mobileno == ''))

		{
			alert("Enter Email Or MobileNumber ")

			document.form1.email.focus();

			return false;

		}

		else {

			if (email !== null && email !== '') {

				//  var validRegex = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;

				var validRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

				if (email.match(validRegex)) {

					return true;

				} else {

					alert("Invalid email address!");

					document.form1.email.focus();

					return false;

				}
			}

			else if (mobileno !== null && mobileno !== '')

				var validRegexmobile = /^[0-9]+$/;

			{

				if (mobileno.match(validRegexmobile)) {

					return true;

				} else {

					alert("Enter valid mobile number!");

					return false;
				}

			}

		}

	}

	function validateForm() {
		var amount = document.getElementById("c_code").value;

		//var phcode = document.getElementById("phcode").value;
		var phno = document.getElementById("mobile").value;
		var tid = document.getElementById("tid").value;
		var referrence = document.getElementById("reference").value;

		//alert(amount+" "+contactName+" "+phcode+" "+phno+" "+tid+" "+referrence);

		//contactName

		//amount
		if (amount == null || amount == '') {
			alert("Empty Amount..Please fill it..");

			document.getElementById("c_code").focus();
			// $( "span" ).text( "Amount field is empty..Please fill it..." ).show();
			return false;
		}

		//referrence
		else if (referrence == null || referrence == '') {
			alert("Empty Reference field..Please fill it..");
			document.getElementById("reference").focus();
			// $( "span" ).text( "Refference field is empty..Please fill it..." ).show();
			return false;
		}

		//deviceid
		else if (tid == null || tid == '') {
			alert("Empty DeviceId..Please Select it..");
			document.getElementById("tid").focus();
			//$( "span" ).text( "DeviceId field is empty..Please fill it..." ).show();
			return false;
		}

		else {
			return true;
		}

	}
</script>


</head>

<body>
	<div class="container-fluid mobi-top-padding-0">


		<!--  EZYLINK  -->
		<style>
.side-panel-dialog {
	/* top: 0; */
	width: 100vw;
	height: 100vh;
	position: fixed;
}

.side-panel-header {
	/*padding: 20px;*/
	z-index: 2;
	width: 100%;
	background-color: white;
	position: sticky;
	right: 0;
	top: 0;
	border-bottom: solid 1px #bababa;
}

.bb {
	border-bottom: solid 1px #bababa;
}

.side-panel-footer {
	padding: 20px;
	width: 100%;
	background-color: white;
	/* position: sticky;*/
	/* right: 0;
bottom: 0;*/
}

/* Extra small devices (phones, 600px and down) */
@media only screen and (max-width: 600px) {
	.mobi-top-padding-0 {
		padding-top: 0px !important;
	}
	.nav-wrapper {
		box-shadow: none !important;
	}
	.side-panel-container {
		position: fixed;
		right: 0;
		/* top: 0; */
		z-index: 2001;
		width: 100vw !important;
		min-height: 100vh !important;
		height: 100vh !important;
		background-color: white;
		box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		-webkit-box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		-moz-box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		border-radius: .2rem;
		overflow-y: auto;
	}
}

/* Small devices (portrait tablets and large phones, 600px and up) */
@media only screen and (min-width: 600px) {
	.side-panel-container {
		position: fixed;
		right: 0;
		/* top: 0; */
		z-index: 2001;
		width: 100vw;
		min-height: 100vh !important;
		height: 100vh !important;
		background-color: white;
		box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		-webkit-box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		-moz-box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		border-radius: .2rem;
		overflow-y: auto;
	}
}

/* Medium devices (landscape tablets, 768px and up) */
@media only screen and (min-width: 768px) {
	.side-panel-container {
		position: fixed;
		right: 0;
		/* top: 0; */
		z-index: 2001;
		width: 100vw;
		min-height: 100vh !important;
		height: 100vh !important;
		background-color: white;
		box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		-webkit-box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		-moz-box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		border-radius: .2rem;
		overflow-y: auto;
	}
}

/* Large devices (laptops/desktops, 992px and up) */
@media only screen and (min-width: 992px) {
	.side-panel-container {
		position: fixed;
		right: 0;
		/* top: 0; */
		z-index: 2001;
		/* width: 600px;*/
		width: auto;
		min-height: 100vh !important;
		height: 100vh !important;
		background-color: white;
		box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		-webkit-box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		-moz-box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		border-radius: .2rem;
		overflow-y: auto;
	}
}

/* Extra large devices (large laptops and desktops, 1200px and up) */
@media only screen and (min-width: 1200px) {
	.side-panel-container {
		position: fixed;
		right: 0;
		top: 65px;
		z-index: 2001;
		/* width: 900px;*/
		width: auto;
		min-height: 100vh !important;
		height: 100vh !important;
		background-color: white;
		box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		-webkit-box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		-moz-box-shadow: 0 3px 6px 0 rgba(0, 0, 0, 0.16);
		border-radius: .2rem;
		overflow-y: auto;
	}
}

.card-body .card-title {
	color: #2e5ea3;
	font-weight: bold;
}

.c092540 {
	color: #092540;
}

.c9f9f9f {
	color: #9f9f9f;
}

.c969696 {
	color: #969696;
}

.mobi-c969696 {
	font-size: 16px; ! important;
	color: #414755 !important;
}

.mobi-color {
	color: #414755 !important;
}

.mobi-c9f9f9f {
	font-size: -0.325em !important;
	color: #414755 !important;
}

.keybpad-btn {
	border-radius: 4px;
	border: solid 1px #dedede !important;
	background-color: #f8f8f8 !important;
	width: 40px;
	box-shadow: -1px 1px 0px 0px #ccc !important;
}

.br707070 {
	border: solid 1px #707070;
}

.too_small {
	font-size: .675em;
}

.keybpad-btn:focus {
	border: solid 1px #005baa;
	box-shadow: none;
	border-radius: 4px;
}

.x-btn {
	width: 4px;
	height: 4px;
	padding: 0.3rem;
	border: solid 1px #DDD;
}

.btn-mobi {
	background: #005baa !important;
}

.mobi-container, .mobi-container-fluid, .mobi-container-lg,
	.mobi-container-md, .mobi-container-sm, .mobi-container-xl,
	.mobi-container-xxl {
	width: 100%;
	padding-right: var(- -bs-gutter-x, .75rem);
	padding-left: var(- -bs-gutter-x, .75rem);
	margin-right: auto;
	margin-left: auto;
}

.mobi-btn-group-vertical>.mobi-btn-group:not (:first-child ),
	.mobi-btn-group-vertical>.mobi-btn:not (:first-child ) {
	margin-top: -1px;
}

.mobi-btn-group-vertical>.mobi-btn, .mobi-btn-group-vertical>.mobi-btn-group
	{
	width: 100%;
}

.mobi-btn-group, .mobi-btn-group-vertical {
	position: relative;
	display: inline-flex;
	vertical-align: middle;
}

.mobi-mb-2 {
	margin-bottom: .5rem !important;
}

.mobi-mt-2 {
	margin-top: .5rem !important;
}

.mobi-align-items-center {
	align-items: center !important;
}

.mobi-gap-3 {
	gap: 1rem !important;
}

.mobi-dropdown, .mobi-dropend, .mobi-dropstart, .mobi-dropup {
	position: relative;
	margin-left: 20px;
}

.mobi-mt-2 {
	margin-top: .5rem !important;
}

.mobi-mb-2 {
	margin-bottom: .5rem !important;
}

.mobi-img-fluid {
	max-width: 100%;
	height: auto;
}

.mobi-me-3 {
	margin-right: 1rem !important;
}

.mobi-small, mobi-small {
	/* font-size: .875em; */
	font-size: 16px;
}

.mobi-border {
	mobi-border: 1px solid #dee2e6 !important;
}

.mobi-float-end {
	float: right !important;
}

.mobi-btn-close {
	box-sizing: content-box;
	width: 1em;
	height: 1em;
	padding: .25em .25em;
	color: #000;
	background: transparent
		url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16' fill='%23000'%3e%3cpath d='M.293.293a1 1 0 011.414 0L8 6.586 14.293.293a1 1 0 111.414 1.414L9.414 8l6.293 6.293a1 1 0 01-1.414 1.414L8 9.414l-6.293 6.293a1 1 0 01-1.414-1.414L6.586 8 .293 1.707a1 1 0 010-1.414z'/%3e%3c/svg%3e")
		center/1em auto no-repeat;
	mobi-border: 0;
	mobi-border-radius: .25rem;
	opacity: .5;
}

.mobi-px-5 {
	padding-right: 3rem !important;
	padding-left: 3rem !important;
}

.mobi-tab-content>.mobi-active {
	display: block;
}

.mobi-fade {
	transition: opacity .15s linear;
}

.mobi-mt-2 {
	margin-top: .5rem !important;
}

.mobi-btn-group-vertical {
	flex-direction: column;
	align-items: flex-start;
	justify-content: center;
}

.mobi-btn-group, .mobi-btn-group-vertical {
	position: relative;
	display: inline-flex;
	vertical-align: middle;
}

.mobi-btn-group-vertical>.btn, .mobi-btn-group-vertical>.btn-group {
	width: 100%;
}

.mobi-btn-group, .mobi-btn-group-vertical {
	position: relative;
	display: inline-flex;
	vertical-align: middle;
}

.mobi-row { -
	-bs-gutter-x: 1.5rem; -
	-bs-gutter-y: 0;
	display: flex;
	flex-wrap: wrap;
	margin-top: calc(var(- -bs-gutter-y)* -1);
	margin-right: calc(var(- -bs-gutter-x)/-2);
	margin-left: calc(var(- -bs-gutter-x)/-2);
}

.mobi-col-12 {
	flex: 0 0 auto;
	width: 100%;
}

.mobi-mb-1 {
	margin-bottom: .25rem !important;
}

.mobi-mb-3 {
	margin-bottom: 1rem !important;
}

.btn-mobi {
	color: #fff;
	background-color: #005baa;
	mobi-border-color: #005baa;
	paddig-top: 5px;
	padding-bottom: 5px;
	padding-right: 71px;
	padding-left: 74px;
	font-size: 20px;
}

.btn-cancel-mobi {
	color: #fff;
	background-color: #005baa;
	mobi-border-color: #005baa;
	padding-right: 34px;
	padding-left: 31px;
	font-size: 20px;
}

.mobi-py-1 {
	padding-top: .25rem !important;
	padding-bottom: .25rem !important;
}

.mobi-me-2 {
	margin-right: .5rem !important;
}

.mobi-ms-2 {
	margin-left: .5rem !important;
}

.mobi-btn-group-vertical>.mobi-btn, .mobi-btn-group>.mobi-btn {
	position: relative;
	flex: 1 1 auto;
}

.mobi-rounded {
	border-radius: .25rem !important;
}

.mobi-btn {
	display: inline-block;
	font-weight: 400;
	line-height: 1.5;
	color: #212529;
	text-align: center;
	text-decoration: none;
	vertical-align: middle;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
	background-color: transparent;
	border: 1px solid transparent;
	padding: .375rem .75rem;
	padding-top: 0.375rem;
	padding-bottom: 0.375rem;
	font-size: 1rem;
	border-radius: .25rem;
	transition: color .15s ease-in-out, background-color .15s ease-in-out,
		border-color .15s ease-in-out, box-shadow .15s ease-in-out;
}

.mobi-form-label {
	margin-bottom: .5rem;
}

.mobi-form-check-inline {
	display: inline-block;
	margin-right: 1rem;
}

.mobi-form-check-input[type="checkbox"] {
	border-radius: .25em;
}

.mobi-form-check .mobi-form-check-input {
	float: left;
	margin-left: -1.5em;
}

.mobi-form-check-input {
	width: 1em;
	height: 1em;
	margin-top: .25em;
	vertical-align: top;
	background-color: #fff;
	background-repeat: no-repeat;
	background-position: center;
	background-size: contain;
	border: 1px solid rgba(0, 0, 0, .25);
	-webkit-appearance: none;
	-moz-appearance: none;
	appearance: none;
	-webkit-print-color-adjust: exact;
	color-adjust: exact;
}
</style>

		<form:form method="post" id="form1" name="form1"
			commandName="preAuthTxnDet"
			action="${pageContext.request.contextPath}/merchantpreauth/preAuthSubmit">

			<form:input type="hidden" path="motoMid" name="motoMid" id="motoMid"
				value="${motoMid}" />


			<div class="side-panel-dialog" id="mobi-ezylink-dialog">
				<div class="side-panel-container">
					<div class="side-panel-header">

						<div class="d-none d-md-block">
							<div
								class="mobi-container-fluid  d-grid mobi-gap-3 mobi-align-items-center mobi-mt-2 mobi-color"
								style="grid-template-columns: 1fr 1fr; display: grid;">
								<div class="mobi-dropdown">
									<img class="mobi-img-fluid mobi-me-3" style="width: 35px"
										src='data:image/png;base64,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 ' />

								</div>

								<div class="">
									<span
										class="w-100  mobi-me-3 mobi-small-20 color-005baa mobi-font-bold">
										Preauth Transaction </span>

								</div>


							</div>
						</div>




						<div class="d-block d-md-none">
							<div
								class="mobi-container-fluid  d-grid mobi-gap-3 mobi-align-items-center mobi-mt-2 mobi-color">


								<div class="mobi-text-center">
									<span
										class="w-100  mobi-me-3 mobi-small-20 color-005baa mobi-font-bold">Preauth
										Transaction </span>

								</div>


							</div>
						</div>






						<!--		<div
							class="mobi-container-fluid d-block d-md-none d-grid mobi-gap-3 mobi-align-items-center mobi-mt-2 mobi-color" style="grid-template-columns: 1fr 1fr; display: grid;">
							

							<div class="">
								<span class="w-100  mobi-me-3 mobi-small"> PREAUTH
									TRANSACTION </span>

							</div>


						</div>  -->


					</div>
					<div class="side-panel-body">
						<div class="mobi-px-5 ">



							<div class="mobi-tab-content" id="pills-tabContent">
								<div class="tab-pane mobi-fade show mobi-active" id="mdr-rates"
									role="tabpanel" aria-labelledby="mdr-rates-tab">


									<!--<h6 class="mobi-c9f9f9f mobi-mt-2">Amount Details</h6>-->




									<div class="mobi-btn-group-vertical mobi-col-12 ml-4 mt-1"
										role="group" aria-label="Basic example">
										<div class="btn-group">
											<input type="text" value="RM 0.00" disabled
												class="text-center form-control mobi-color mobi-mb-2 mobi-text-dark"
												id="keypad"> <input type="hidden" class="" value=""
												id="code" value=""> <input type="hidden" class=""
												value="" id="c_code" value="" name="amount" path="amount">
										</div>
										<div class="mobi-btn-group">
											<button type="button"
												class="mobi-btn mobi-rounded keybpad-btn mobi-py-1  mobi-me-2 mobi-mb-1 mt-1"
												onclick="keypadd(1)">1</button>
											<button type="button"
												class="mobi-btn mobi-rounded keybpad-btn mobi-py-1 mobi-ms-2 mobi-me-2 mobi-mb-1 mt-1"
												onclick="keypadd(2)">2</button>
											<button type="button"
												class="mobi-btn mobi-rounded keybpad-btn mobi-py-1 mobi-ms-2  mobi-mb-1 mt-1"
												onclick="keypadd(3)">3</button>
										</div>
										<div class="mobi-btn-group">
											<button type="button"
												class="mobi-btn mobi-rounded keybpad-btn mobi-py-1  mobi-me-2 mobi-mb-1 mt-1"
												onclick="keypadd(4)">4</button>
											<button type="button"
												class="mobi-btn mobi-rounded keybpad-btn mobi-py-1 mobi-ms-2 mobi-me-2 mobi-mb-1 mt-1"
												onclick="keypadd(5)">5</button>
											<button type="button"
												class="mobi-btn mobi-rounded keybpad-btn mobi-py-1 mobi-ms-2  mobi-mb-1 mt-1"
												onclick="keypadd(6)">6</button>
										</div>
										<div class="mobi-btn-group">
											<button type="button"
												class="mobi-btn mobi-rounded keybpad-btn mobi-py-1  mobi-me-2 mobi-mb-1 mt-1"
												onclick="keypadd(7)">7</button>
											<button type="button"
												class="mobi-btn mobi-rounded keybpad-btn mobi-py-1 mobi-ms-2 mobi-me-2 mobi-mb-1 mt-1"
												onclick="keypadd(8)">8</button>
											<button type="button"
												class="mobi-btn mobi-rounded keybpad-btn mobi-py-1 mobi-ms-2  mobi-mb-1 mt-1"
												onclick="keypadd(9)">9</button>
										</div>
										<div class="mobi-btn-group">
											<button type="button"
												class="mobi-btn mobi-rounded keybpad-btn mobi-py-1  mobi-me-2 mobi-mb-1 mt-1 text-danger"
												onclick="keypadd('C')">C</button>
											<button type="button"
												class="mobi-btn mobi-rounded keybpad-btn mobi-py-1 mobi-ms-2 mobi-me-2 mobi-mb-1 mt-1"
												onclick="keypadd(0)">0</button>
											<button type="button"
												class="mobi-btn mobi-rounded keybpad-btn mobi-py-1 mobi-ms-2  mobi-mb-1 mt-1"
												onclick="keypadd('X')">&#8592;</button>
										</div>
									</div>



									<div class="mobi-row">
										<div class="mobi-col-12">
											<div class="mobi-mb-1">
												<label for="Product" class="mobi-form-label  mobi-c969696">Enter
													Reference</label> <input type="text" class="form-control"
													name="referrence" path="referrence" id="reference"
													placeholder="">
											</div>
										</div>
									</div>

									<input type="hidden" id="expDate" name="expDate" path="expDate" />


									<form:input type="hidden" id="tid" path="tid" value="" />



									<div class="mobi-row">
										<div class="mobi-col-12">
											<div class="mobi-mb-1">
												<label for="Product" class="mobi-form-label  mobi-c969696">Choose
													User Name</label> <select
													class="browser-default custom_select mobi-text-dark"
													onchange="this.value;" name="tids" id="tids">
													<option selected value=""><c:out
															value="Choose User Name" /></option>

													<c:forEach items="${mobileuser}" var="mobileuser">
														<option value="${mobileuser.motoTid}"
															title="${mobileuser.motoTid}">${mobileuser.username}</option>
													</c:forEach>
												</select>

											</div>
										</div>
									</div>


									<div class="mobi-row">
										<div class="mobi-col-12">
											<div class="mobi-mb-1">
												<label for="Product" class="mobi-form-label  mobi-c969696">Email
													Address</label> <input type="text" class="form-control" id="email"
													name="email" placeholder="">
											</div>
										</div>
									</div>

									<input type="hidden" name="phncode1" id="phcode1" value="60">

									<div class="mobi-row">
										<div class="mobi-col-12">
											<div class="mobi-mb-1">
												<!--                                     <label for="Product" class="mobi-form-label too_small c969696">Mobile Number</label> -->

												<input type="tel" class="form-control" value="" name="phno"
													id="mobile" placeholder="Enter your mobile number">
											</div>
										</div>

										<%-- <center>
											<span id="spnPhoneStatus"></span>
										</center> --%>
									</div>


								</div>

							</div>
						</div>

						<!--<div class="bb"></div>  -->
						<form:input type="hidden" name="latitude" value="" id="latitude"
							path="latitude" />
						<form:input type="hidden" name="longitude" value="" id="longitude"
							path="longitude" />

						<div class="side-panel-footer mobi-mb-2">
							<div class="margin-b-4em">

								<button class="btn mobi-p-30  btn-mobi mobi-float-end mobi-mb-2">Submit</button>
								<button class="btn  mobi-p-30 mobi-mb-2 btn-cancel-mobi"
									onclick="loadCancelData()">Cancel</button>

							</div>
						</div>



					</div>
				</div>
			</div>

		</form:form>
	</div>

	<script
		src="${pageContext.request.contextPath}/resourcesNew/js/intlTelInput.js"></script>



	<script>
		var input = document.querySelector("#mobile");

		iti = window.intlTelInput(input, {

			// allowDropdown: false,
			// autoHideDialCode: false,
			// autoPlaceholder: "off",
			// dropdownContainer: document.body,
			// excludeCountries: ["us"],
			// formatOnDisplay: false,
			// geoIpLookup: function(callback) {
			//   $.get("http://ipinfo.io", function() {}, "jsonp").always(function(resp) {
			//     var countryCode = (resp && resp.country) ? resp.country : "";
			//     callback(countryCode);
			//   });
			// },
			//hiddenInput: "full_number",
			// initialCountry: "auto",
			// localizedCountries: { 'de': 'Deutschland' },
			// nationalMode: false,
			// onlyCountries: ['us', 'gb', 'ch', 'ca', 'do'],
			// placeholderNumberType: "MOBILE",
			// preferredCountries: ['cn', 'jp'],
			initialCountry : "my",
			separateDialCode : true,
			// onlyCountries: ['my', 'in', 'sg','cn','id'],
			nationalMode : true,
			preferredCountries : [ 'my', 'sg' ],
			utilsScript : "assets/js/utils.js",
		});

		input.addEventListener("countrychange", function() {
			var countryData = iti.getSelectedCountryData();
			//$("#tel").val(countryData.dialCode);
			//$("#country").val(countryData.name);
			document.getElementById("phcode1").value = countryData.dialCode;
			console.log(countryData);

		});
	</script>

	<script>
		function keypadd(num) {
			existing_val = document.getElementById('code').value;
			if (num == 'C') {
				document.getElementById('code').value = 0;
			}
			if (num == 'X') {
				current_val = document.getElementById('code').value = document
						.getElementById('code').value;
				current_val = current_val.slice(0, -1);
				document.getElementById('code').value = current_val;
			} else if (num == 'C') {
				current_val = 0;
			} else {
				if (existing_val.length <= 12)
					current_val = document.getElementById('code').value = document
							.getElementById('code').value
							+ num;
				else
					current_val = document.getElementById('code').value = document
							.getElementById('code').value;
			}
			current_val_decimal = (current_val / 100);
			current_val_decimal_display = (Math
					.round(current_val_decimal * 100) / 100).toFixed(2);

			document.getElementById('c_code').value = current_val_decimal_display;

			document.getElementById('keypad').value = 'RM '
					+ current_val_decimal_display;

		}
	</script>

	<script>
		function closeSidePanel() {
			document.getElementById("mobi-ezylink-dialog").style.display = "none"
		}
	</script>


</body>

</html>

