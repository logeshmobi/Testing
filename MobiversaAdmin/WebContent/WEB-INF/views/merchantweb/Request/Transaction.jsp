<html lang="en">
<%@page
	import="com.mobiversa.payment.controller.MerchantWebUMTransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<head>
<!-- Add SweetAlert2 CDN -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">

<style type="text/css">
.page-wrapper {
	background: none !important;
}
</style>

<style type="text/css">
@
-webkit-keyframes chartjs-render-animation {from { opacity:0.99
	
}

to {
	opacity: 1
}

}
@
keyframes chartjs-render-animation {
	from {: opacity: 0.99
}

to {
	opacity: 1
}

}
.chartjs-render-monitor {
	-webkit-animation: chartjs-render-animation 0.001s;
	animation: chartjs-render-animation 0.001s;
}

.jqstooltip {
	position: absolute;
	left: 0px;
	top: 0px;
	visibility: hidden;
	background: rgb(0, 0, 0) transparent;
	background-color: rgba(0, 0, 0, 0.6);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000,
		endColorstr=#99000000);
	-ms-filter:
		"progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000)";
	color: white;
	font: 10px arial, san serif;
	text-align: left;
	white-space: nowrap;
	padding: 5px;
	border: 1px solid white;
	z-index: 10000;
}

.jqsfield {
	color: white;
	font: 10px arial, san serif;
	text-align: left;
}

@media ( max-width : 480px) {
	.navbtn {
		display: none;
	}
	.dashbtn {
		display: block !important;
	}
}

#right {
	width: 330px;

         background: #fff;

         margin-left: auto;

         margin-right: auto;

/*           padding: 65px 0px!important;  */

          min-height: calc(100vh - 122px); 
}

#right .menu {
	text-align: center;

         font-family: "Helvetica";

         background-color: #005baa;

         color:#fff;

         font-weight:700;

         padding: 12px;

         margin: auto;
}

#right .menu1 {
	background: #005baa;
	color: #fff;
}

.r-bg {
	background-color: #e2e5ee;
	padding: 15px;
	position:relative;
}

input[type=text] {
	border: 0px !important;
	width: 95% !important;
	outline: 0px !important;
	font-size: 18px !important;
	border-radius: 10px !important;
	padding: 8px !important;
}

:focus-visible {
	outline: 0px !important;
}

.mob-btn {
	width: 100%;
/* 	padding-top: 15px; */
}

.mob-btn .mob-btn-1 {
	margin: 3px;
	width: 29%;
	height: 80px;
	background-color: #ffffff;
	border: 0px;
	box-shadow: rgba(99, 99, 99, 0.2) 0px 2px 8px 0px;
	border-radius: 3px;
	color: #34356e;
	font-weight: 700;
	font-size: 17px;
	cursor: pointer;
}

.mob-btn .mob-red {
	color: #ee100f !important;
}

.mt-1 {
	margin-top: 10px;
}

.alertbox-align {
	margin: auto;
	width: 50%;
}

.alertbox {
	display: none;
	color: white;
	padding: 8px;
	background: #48474e;
	border-radius: 5px;
	font-size: 14px;
	position: absolute;
	z-index: 99999;
	font-family: "Helvetica";
	text-align: center;
}

 input:not([type]):focus:not([readonly]), input[type=text]:not(.browser-default):focus:not([readonly]), input[type=password]:not(.browser-default):focus:not([readonly]), input[type=email]:not(.browser-default):focus:not([readonly]), input[type=url]:not(.browser-default):focus:not([readonly]), input[type=time]:not(.browser-default):focus:not([readonly]), input[type=date]:not(.browser-default):focus:not([readonly]), input[type=datetime]:not(.browser-default):focus:not([readonly]), input[type=datetime-local]:not(.browser-default):focus:not([readonly]), input[type=tel]:not(.browser-default):focus:not([readonly]), input[type=number]:not(.browser-default):focus:not([readonly]), input[type=search]:not(.browser-default):focus:not([readonly]), textarea.materialize-textarea:focus:not([readonly]){

            box-shadow: none !important;

         }


.pay {
	font-family: "Helvetica";
	color: #72757c;
	font-size: 13px;
	/* margin-left:65px; */
	font-weight: 700;
}

.paymode {
/* 	margin: 10px; */
	background-color: #ffffff;
	border: 0px;
	/* box-shadow: rgba(0, 0, 0, 0.19) 0px 10px 20px, rgba(0, 0, 0, 0.23) 0px 6px 6px; */
	border-radius: 3px;
	color: #34356e;
	font-weight: 700;
	font-size: 17px;
	cursor: pointer;
/* 	padding: 5px; */
	height: 85px;
}

.paylink {
	 text-align:center;

         left: 0;

         bottom: 0;

         width: 100%;

         background-color: #fff;
}

.container-fluid {
	padding: 0px !important;
}

@media only screen and (max-device-width: 480px) {
	.menu {
		font-size: 25px;
		padding: 15px !important;
	}
	#right {
		width: 100%;
		position: scroll;
	}
	input[type=text] {
		font-size: 15px !important;
		height: 35px !important;
	}
	.row .col {
		padding: 0px !important;
	}
	.r-bg {
		padding: 10px;
	}
	.mob-btn .mob-btn-1 {
		margin: 4px;
		font-size: 25px;
	}
	.pay {
		font-size: 15px;
	}
	.paylink {
		position: fixed;
	}
	.paymode {
/* 		margin-bottom: 49px; */
		height: 100px;
	}
	.alertbox {
		/* width: 70%; */
		font-size: 15px;
	}
}
input
:not
 
(
[
type
]
 
)
:focus
:not
 
(
[
readonly
]
 
),
input
[
type
=
text
]
:not
 
(
.browser-default

	
)
:focus
:not
 
(
[
readonly
]
 
),
input
[
type
=
password
]
:not
 
(
.browser-default
 
)
:focus
:not

	
(
[
readonly
]
 
),
input
[
type
=
email
]
:not
 
(
.browser-default
 
)
:focus
:not
 
(
[
readonly
]

	
),
input
[
type
=
url
]
:not
 
(
.browser-default
 
)
:focus
:not
 
(
[
readonly
]
 
),
input
[
type
=
time
]
:not
 
(
.browser-default
 
)
:focus
:not
 
(
[
readonly
]
 
),
input
[
type
=
date
]
:not

	
(
.browser-default
 
)
:focus
:not
 
(
[
readonly
]
 
),
input
[
type
=
datetime
]
:not
 
(
.browser-default

	
)
:focus
:not
 
(
[
readonly
]
 
),
input
[
type
=
datetime-local
]
:not
 
(
.browser-default

	
)
:focus
:not
 
(
[
readonly
]
 
),
input
[
type
=
tel
]
:not
 
(
.browser-default
 
)
:focus
:not

	
(
[
readonly
]
 
),
input
[
type
=
number
]
:not
 
(
.browser-default
 
)
:focus
:not
 
(
[
readonly
]

	
),
input
[
type
=
search
]
:not
 
(
.browser-default
 
)
:focus
:not
 
(
[
readonly
]
 
),
textarea
.materialize-textarea
:focus
:not
 
(
[
readonly
]
 
)
{
box-shadow
:
 
none
 
!
important
;


}
</style>
</head>

<style>
.topbar nav .mailbox.dropdown-content {
	min-width: 240px;
}

.u-text {
	margin: auto;
	display: table;
}
</style>
<style>
.c-0e89e3 {
	color: #0e89e3;
}

.fs-80-pt {
	font-size: 80%;
}

.w15p {
	width: 15%;
}

.col-1 {
	width: 8.33%;
	float: left;
	padding: 0 0.75rem
}

.col-2 {
	width: 16.66%;
	float: left;
	padding: 0 0.75rem
}

.col-3 {
	width: 25%;
	float: left;
	padding: 0 0.75rem
}

.col-4 {
	width: 33.33%;
	float: left;
	padding: 0 0.75rem
}

.col-5 {
	width: 41.66%;
	float: left;
	padding: 0 0.75rem
}

.col-6 {
	width: 50%;
	float: left;
	padding: 0 0.75rem
}

.col-7 {
	width: 58.33%;
	float: left;
	padding: 0 0.75rem
}

.col-8 {
	width: 66.66%;
	float: left;
	padding: 0 0.75rem
}

.col-9 {
	width: 75%;
	float: left;
	padding: 0 0.75rem
}

.col-10 {
	width: 83.33%;
	float: left;
	padding: 0 0.75rem
}

.col-11 {
	width: 91.66%;
	float: left;
	padding: 0 0.75rem
}

.col-12 {
	width: 100%;
	float: left;
	padding: 0 0.75rem
}

.hide_key {
	display: none;
}

@media only screen and (max-width: 768px) {
	[class*="col-"] {
		width: 100%;
		float: left;
		padding: 0 0.75rem
	}
}

.table-new thead tr {
	background: #d7e7ea;
}

.table-new thead tr th {
	border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important;
	padding: 10px !important;
}

.table-new tbody tr td {
	padding: 10px !important;
}

.table-new tbody tr {
	border-bottom: none !important;
}

.bg-f3fbfd {
	background-color: #f3fbfd;
}

.br-5 {
	border-radius: 5px;
}

.c-092540 {
	color: #092540;
}

.c-0e89e3 {
	color: #0e89e3;
}

.c-151931 {
	color: #151931;
}

.mb-40 {
	margin-bottom: 40px;
}

.chart_div svg {
	display: block !important;
}

.chart_div figure {
	margin: 0px;
}

.btccc {
	border-top: solid 1px #ccc;
}

.align-items-center .c-151931 {
	color: #151931 !important;
	z-index: 2;
}

     button {

    touch-action: manipulation !important;

}
</style>

<body cz-shortcut-listen="true">
<!-- 	<div class="container-fluid"> -->
<!-- 		<div class="row"> -->
<!-- 			<div class="col s12"> -->
<!-- 				<div class="d-flex align-items-center"> -->
					<div id="right">
						<!-- <div class="menu menu1">Home</div> -->
						<div class="menu" id="keypad">RM 0.00</div>
						<input type="hidden" class="" value="" id="code">
						<div class="r-bg">
							<input type="text" value="" id="getInvoice"
								style="background-color: #ffffff !important; border-bottom: 0px !important; height: 30px !important;"
								placeholder="Invoice/Reference Id">
							<div class="mob-btn">
								<div class="mobi-btn-group">
									<button type="button" class="mob-btn-1" onclick="keypad(1)">1</button>
									<button type="button" class="mob-btn-1" onclick="keypad(2)">2</button>
									<button type="button" class="mob-btn-1" onclick="keypad(3)">3</button>
								</div>
								<div class="mobi-btn-group">
									<button type="button" class="mob-btn-1" onclick="keypad(4)">4</button>
									<button type="button" class="mob-btn-1" onclick="keypad(5)">5</button>
									<button type="button" class="mob-btn-1" onclick="keypad(6)">6</button>
								</div>
								<div class="mobi-btn-group">
									<button type="button" class="mob-btn-1" onclick="keypad(7)">7</button>
									<button type="button" class="mob-btn-1" onclick="keypad(8)">8</button>
									<button type="button" class="mob-btn-1" onclick="keypad(9)">9</button>
								</div>
								<div class="mobi-btn-group">
									<button type="button" class="mob-btn-1 mob-red"
										onclick="keypad('C')">C</button>
									<button type="button" class="mob-btn-1" onclick="keypad(0)">0</button>
									<button type="button" class="mob-btn-1" onclick="keypad('X')">&#8592;</button>
								</div>
							</div>
						</div>
						<div class="mt-1  paylink">
<!-- 							<span class="pay">Payment mode</span> -->
							<div class="payrow">
								<c:if test="${Auth3ds == 'Yes'}">
									<img
										src="${pageContext.request.contextPath}/resourcesNew/img/ezylink.png"
										class="paymode" onclick="submitEzylink()">
								</c:if>
								<c:if test="${Auth3ds == 'No'}">
									<img
										src="${pageContext.request.contextPath}/resourcesNew/img/ezymotocircle.png"
										class="paymode" onclick="submitEzymoto()">
								</c:if>
								<c:if test="${ezyAuthEnable == 'Yes'}">
								
								<c:choose>
									<c:when test="${mototid != null && mototid != '' && Auth3ds == 'No' }">
										<img
											src="${pageContext.request.contextPath}/resourcesNew/img/ezyauthcircle.png"
											class="paymode" onclick="submitEzyauthViaMoto()">
									</c:when>
									<c:otherwise>

										<img
											src="${pageContext.request.contextPath}/resourcesNew/img/ezyauthcircle.png"
											class="paymode" onclick="submitEzyauth()">
									</c:otherwise>
								</c:choose>

								</c:if>
							</div>
						</div>
					</div>
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<!--EZYLINK Form -->
	<form
		action="${pageContext.request.contextPath}<%=MerchantWebUMTransactionController.URL_BASE%>/EzyLinkRequestDetails"
		method="get" id="Initiate_Ezylink">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}"> <input type="hidden"
			name="LinkreqAmount" id="LinkreqAmt" value="0">
	</form>
	<!--EZYMOTO Form -->
	<form
		action="${pageContext.request.contextPath}<%=MerchantWebUMTransactionController.URL_BASE%>/EzyMotoRequestDetails"
		method="get" id="Initiate_Ezymoto">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}"> <input type="hidden"
			name="MotoreqAmount" id="MotoreqAmt" value="0"> <input
			type="hidden" name="getInvoice" id="reqinvoice" value="0">

		<!--          invoice val(reqinvoice) is taking from submitEzymoto() -->

	</form>
	<!--EZYAUTH Form -->
	<form
		action="${pageContext.request.contextPath}<%=MerchantWebUMTransactionController.URL_BASE%>/EzyAuthRequestDetails"
		method="get" id="Initiate_Ezyauth">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}"> <input type="hidden"
			name="AuthreqAmount" id="AuthreqAmt" value="0">
	</form>
	
	<!--EZYAUTHviaMOTO Form -->
	<form
		action="${pageContext.request.contextPath}<%=MerchantWebUMTransactionController.URL_BASE%>/EzyauthviaMOTORequestDetails"
		method="get" id="Initiate_EzyauthviaMOTO">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}"> <input type="hidden"
			name="AuthviamotoreqAmount" id="AuthviamotoreqAmt" value="0">
			<input
			type="hidden" name="getInvoice" id="authviamotoreqinvoice" value="0">

		<!--          invoice val(authviamotoreqinvoice) is taking from submitEzyauthViaMoto() and all the amounts(AuthviamotoreqAmt) are taken from keypad function -->
	</form>


<script>
	/*--------------------------------------------------Keypad Functionalities----------------------------------------------------------------------- */
	function keypad(num) {
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
		current_val_decimal_display = (Math.round(current_val_decimal * 100) / 100)
				.toFixed(2);
		document.getElementById('LinkreqAmt').value = current_val_decimal_display;
		document.getElementById('MotoreqAmt').value = current_val_decimal_display;
		document.getElementById('AuthreqAmt').value = current_val_decimal_display;
		document.getElementById('AuthviamotoreqAmt').value = current_val_decimal_display;
		jQuery("#keypad").html('RM ' + current_val_decimal_display);
	}
	/*--------------------------------------------------EZYLINK Form Submit Functionalities----------------------------------------------------------------------- */
	function submitEzylink() {
		var current_val = document.getElementById('code').value;
		var invoice = document.getElementById('getInvoice').value;
		current_val_decimal = (current_val / 100);
		let x = Math.round(current_val_decimal);
		if (current_val_decimal < 5) {
			alert("Enter an amount greater than or equal to 5 RM.");
		} else if (invoice == '') {
			alert("Invoice/Reference Id is Required");
		} else {
			var req = document.getElementById('LinkreqAmt').value;
			document.getElementById("Initiate_Ezylink").submit();
		}
	}
	/*--------------------------------------------------EZYMOTO Form Submit Functionalities----------------------------------------------------------------------- */
	function submitEzymoto() {
		var current_val = document.getElementById('code').value;
		var invoice = document.getElementById('getInvoice').value;
		current_val_decimal = (current_val / 100);
		let x = Math.round(current_val_decimal);
		if (current_val_decimal < 5) {
			alert("Enter an amount greater than or equal to 5 RM.");
		} else if (invoice == '') {
			alert("Invoice/Reference Id is Required");
		} else {
			var req = document.getElementById('MotoreqAmt').value;
			document.getElementById('reqinvoice').value = invoice;
			document.getElementById("Initiate_Ezymoto").submit();
		}
	}
	/*--------------------------------------------------EZYAUTH Form Submit Functionalities----------------------------------------------------------------------- */
	function submitEzyauth() {
		var current_val = document.getElementById('code').value;
		var invoice = document.getElementById('getInvoice').value;
		current_val_decimal = (current_val / 100);
		let x = Math.round(current_val_decimal);
		if (current_val_decimal < 5) {
			alert("Enter an amount greater than or equal to 5 RM.");
		} else if (invoice == '') {
			alert("Invoice/Reference Id is Required");
		} else {
			var req = document.getElementById('AuthreqAmt').value;
			
			document.getElementById("Initiate_Ezyauth").submit();
		}
	}
	
	/*--------------------------------------------------EZYAUTHviaMOTO Form Submit Functionalities----------------------------------------------------------------------- */
	
	function submitEzyauthViaMoto() {
		var current_val = document.getElementById('code').value;
		var invoice = document.getElementById('getInvoice').value;
		current_val_decimal = (current_val / 100);
		let x = Math.round(current_val_decimal);
		if (current_val_decimal < 5) {
			alert("Enter an amount greater than or equal to 5 RM.");
		} else if (invoice == '') {
			alert("Invoice/Reference Id is Required");
		} else {
			var req = document.getElementById('AuthviamotoreqAmt').value;
			document.getElementById('authviamotoreqinvoice').value = invoice;
			document.getElementById("Initiate_EzyauthviaMOTO").submit();
		}
	}
		
	/*--------------------------------------------------Success Popup Alert Functionalities----------------------------------------------------------------------- */
/* 	var showSuccessAlert = function() {
		var result = "${Success}";
		if (result != null && result != "") {
			alert(result);
		}
	} */
	
	/* --------------------------------------------------Refactored Success Popup Alert Functionalities------------------------------------------------------------ */
/*    var myFunc = function () {
		
		var result = "${Success}";
		if (result != null && result != "") {
			alert(result);
		}
		
	    var viaEmail = "${viaEmail}";

	    // Check if result is not null and not blank
	    if (viaEmail != null && viaEmail !== "" && viaEmail === "false") {
	        var smsUrl = "${smsUrl}";

	        // Check if smsUrl is null or blank
	        if (smsUrl == null || smsUrl.trim() === "") {
	            alert("Oops.. Unable to make transaction due to connection refusal.");
	        } else {
	            // Directly copy the smsUrl to clipboard
	            copyToClipboard(smsUrl)
	                .then(() => {
	                    // Display a success message
	                    alert("Payment link has been copied to clipboard!");
	                })
	                .catch((err) => {
	                    alert("Failed to copy the payment link. Please try again.");
	                    console.error("Copy failed:", err);
	                });
	        }
	    } else if (viaEmail != null && viaEmail !== "" && viaEmail === "true") {
	        // Handle the case for email display
	        var descriptionText = "${description}";
	        alert(descriptionText);
	    }
	};

	function copyToClipboard(text) {
	    // Use the Clipboard API for modern browsers
	    if (navigator.clipboard && navigator.clipboard.writeText) {
	        return navigator.clipboard.writeText(text);
	    } else {
	        // Fallback for older browsers
	        return new Promise((resolve, reject) => {
	            try {
	                var tempInput = document.createElement("input");
	                tempInput.value = text;
	                document.body.appendChild(tempInput);

	                // Select the text in the input field
	                tempInput.select();
	                tempInput.setSelectionRange(0, 99999); // For mobile devices

	                // Execute the copy command
	                document.execCommand("copy");

	                // Remove the temporary input element
	                document.body.removeChild(tempInput);

	                resolve();
	            } catch (err) {
	                reject(err);
	            }
	        });
	    }
	}
	
 	window.onload = function() {
	    setTimeout(() => {
	        showSuccessAlert();
	        handleViaEmail();  
	    }, 1000); 
	
 	window.onload = function() {
		setTimeout(myFunc, 1000);
	}  */
	
	
	
	/* var myFunc = function () {
	    var result = "${Success}";
	    if (result != null && result != "") {
	        alert(result);
	    }

	    var viaEmail = "${viaEmail}";

	    // Check if result is not null and not blank
	    if (viaEmail != null && viaEmail !== "" && viaEmail === "false") {
	        var smsUrl = "${smsUrl}";

	        // Check if smsUrl is null or blank
	        if (smsUrl == null || smsUrl.trim() === "") {
	            alert("Oops.. Unable to make transaction due to connection refusal.");
	        } else {
	            // Show the Swal popup with the URL and Copy button
	            Swal.fire({
	                title: 'Payment Link',
	                text: smsUrl,
	                showCancelButton: true,
	                cancelButtonText: 'Close',
	                confirmButtonText: 'Copy',
	                reverseButtons: true,  // Reverse the button order
	                customClass: {
	                    title: 'custom-title',  // Custom class for title
	                    confirmButton: 'custom-confirm-button',  // Custom class for confirm button
	                    cancelButton: 'custom-cancel-button'  // Custom class for cancel button (optional)
	                },
	                didOpen: () => {
	                    // Set title color
	                    document.querySelector('.swal2-title').style.color = '#005BAA';  // Set title color

	                    // Set custom colors for buttons
	                    document.querySelector('.swal2-confirm').style.backgroundColor = '#005BAA'; // "Copy" button (confirm)
	                    document.querySelector('.swal2-confirm').style.color = '#ffffff'; // Text color of the "Copy" button to white

	                    document.querySelector('.swal2-cancel').style.backgroundColor = '#6C757D'; // "Close" button (cancel)
	                    document.querySelector('.swal2-cancel').style.color = '#ffffff'; // Text color of the "Close" button to white
	                }
	            }).then((result) => {
	                if (result.isConfirmed) {
	                    // Copy to clipboard on button click
	                    copyToClipboard(smsUrl)
	                        .then(() => {
	                            // Show the success popup with custom OK button color
	                            Swal.fire({
	                                title: 'Copied!',
	                                text: 'Payment link has been copied to clipboard.',
	                                icon: 'success',
	                                confirmButtonText: 'OK',
	                                customClass: {
	                                    confirmButton: 'custom-confirm-button' // Custom class for OK button
	                                },
	                                didOpen: () => {
	                                    // Set OK button color
	                                    document.querySelector('.swal2-confirm').style.backgroundColor = '#005BAA';  // OK button background
	                                    document.querySelector('.swal2-confirm').style.color = '#ffffff';  // OK button text color
	                                }
	                            });
	                        }).catch((err) => {
	                            Swal.fire('Failed!', 'Failed to copy the payment link. Please try again.', 'error');
	                            console.error('Copy failed:', err);
	                        });
	                }
	            });
	        }
	    } else if (viaEmail != null && viaEmail !== "" && viaEmail === "true") {
	        // Handle the case for email display
	        var descriptionText = "${description}";
	        alert(descriptionText);
	    }
	};

	function copyToClipboard(text) {
	    // Use the Clipboard API for modern browsers
	    if (navigator.clipboard && navigator.clipboard.writeText) {
	        return navigator.clipboard.writeText(text);
	    } else {
	        // Fallback for older browsers
	        return new Promise((resolve, reject) => {
	            try {
	                var tempInput = document.createElement("input");
	                tempInput.value = text;
	                document.body.appendChild(tempInput);

	                // Select the text in the input field
	                tempInput.select();
	                tempInput.setSelectionRange(0, 99999); // For mobile devices

	                // Execute the copy command
	                document.execCommand("copy");

	                // Remove the temporary input element
	                document.body.removeChild(tempInput);

	                resolve();
	            } catch (err) {
	                reject(err);
	            }
	        });
	    }
	}

	window.onload = function () {
	    setTimeout(myFunc, 1000);
	}; */

	var myFunc = function () {
	    var result = "${Success}";
	    if (result != null && result != "") {
	        alert(result);
	    }

	    var viaEmail = "${viaEmail}";

	    // Check if result is not null and not blank
	    if (viaEmail != null && viaEmail !== "" && viaEmail === "false") {
	        var smsUrl = "${smsUrl}";

	        // Check if smsUrl is null or blank
	        if (smsUrl == null || smsUrl.trim() === "") {
	            alert("Oops.. Unable to make transaction due to connection refusal.");
	        } else {
	            // Show the Swal popup with the URL and Copy button
	            Swal.fire({
	                title: 'Payment Link',
	                text: 'Click the Copy button to copy the payment link.',
//	                text: smsUrl,
	                icon: 'info', // Add an info icon for better context
	                iconColor: '#005BAA',
	                showCancelButton: true,
	                cancelButtonText: 'Close',
	                confirmButtonText: 'Copy',
	                reverseButtons: true, // Reverse the button order
	                customClass: {
	                    popup: 'custom-popup', // Custom class for popup
	                    title: 'custom-title', // Custom class for title
	                    confirmButton: 'custom-confirm-button', // Custom class for confirm button
	                    cancelButton: 'custom-cancel-button' // Custom class for cancel button
	                },
	                didOpen: () => {
	                    // Apply consistent styles
	                    const popup = document.querySelector('.swal2-popup');
	                    popup.style.backgroundColor = '#ffffff'; // White background
	                    popup.style.borderRadius = '12px'; // Rounded corners
	                    popup.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.2)'; // Shadow for depth

	                    const title = document.querySelector('.swal2-title');
	                    title.style.color = '#005BAA'; // Consistent blue title color
	                    title.style.fontFamily = 'inherit'; // Inherit the default font style
	                    title.style.fontWeight = 'bold'; // Bold title

	                    // Customize buttons
	                    const confirmButton = document.querySelector('.swal2-confirm');
	                    confirmButton.style.backgroundColor = '#005BAA';
	                    confirmButton.style.color = '#ffffff';
	                    confirmButton.style.borderRadius = '8px';

	                    const cancelButton = document.querySelector('.swal2-cancel');
	                    cancelButton.style.backgroundColor = '#6C757D';
	                    cancelButton.style.color = '#ffffff';
	                    cancelButton.style.borderRadius = '8px';
	                }
	            }).then((result) => {
	                if (result.isConfirmed) {
	                    // Copy to clipboard on button click
	                    copyToClipboard(smsUrl)
	                        .then(() => {
	                            // Show the success popup with custom OK button color
	                            Swal.fire({
	                                title: 'Copied!',
	                                text: 'Payment link has been copied to clipboard.',
	                                icon: 'success',
	                                confirmButtonText: 'OK',
	                                customClass: {
	                                    confirmButton: 'custom-confirm-button' // Custom class for OK button
	                                },
	                                didOpen: () => {
	                                    const okButton = document.querySelector('.swal2-confirm');
	                                    okButton.style.backgroundColor = '#005BAA'; // OK button background
	                                    okButton.style.color = '#ffffff'; // OK button text color
	                                    okButton.style.borderRadius = '8px';
	                                }
	                            });
	                        }).catch((err) => {
	                            Swal.fire('Failed!', 'Failed to copy the payment link. Please try again.', 'error');
	                            console.error('Copy failed:', err);
	                        });
	                }
	            });
	        }
	    }else if (viaEmail != null && viaEmail !== "" && viaEmail === "true") { 
	        // Handle the case for email display
	        var descriptionText = "${description}";

	        Swal.fire({
	            title: 'Transaction Details',
	            text: descriptionText,
	            icon: 'info', // Use an information icon for context
	            iconColor: '#005BAA', // Consistent icon color
	            confirmButtonText: 'OK',
	            customClass: {
	                popup: 'custom-popup', // Custom class for popup
	                title: 'custom-title', // Custom class for title
	                confirmButton: 'custom-confirm-button' // Custom class for confirm button
	            },
	            didOpen: () => {
	                // Apply consistent styles
	                const popup = document.querySelector('.swal2-popup');
	                popup.style.backgroundColor = '#ffffff'; // White background
	                popup.style.borderRadius = '12px'; // Rounded corners
	                popup.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.2)'; // Shadow for depth

	                const title = document.querySelector('.swal2-title');
	                title.style.color = '#005BAA'; // Consistent blue title color
	                title.style.fontFamily = 'inherit'; // Inherit the default font style
	                title.style.fontWeight = 'bold'; // Bold title

	                const confirmButton = document.querySelector('.swal2-confirm');
	                confirmButton.style.backgroundColor = '#005BAA';
	                confirmButton.style.color = '#ffffff';
	                confirmButton.style.borderRadius = '8px';
	            }
	        });
	    }

	    /* } else if (viaEmail != null && viaEmail !== "" && viaEmail === "true") {
	        // Handle the case for email display
	        var descriptionText = "${description}";
	        alert(descriptionText);
	    } */
	};

	function copyToClipboard(text) {
	    // Use the Clipboard API for modern browsers
	    if (navigator.clipboard && navigator.clipboard.writeText) {
	        return navigator.clipboard.writeText(text);
	    } else {
	        // Fallback for older browsers
	        return new Promise((resolve, reject) => {
	            try {
	                var tempInput = document.createElement("input");
	                tempInput.value = text;
	                document.body.appendChild(tempInput);

	                // Select the text in the input field
	                tempInput.select();
	                tempInput.setSelectionRange(0, 99999); // For mobile devices

	                // Execute the copy command
	                document.execCommand("copy");

	                // Remove the temporary input element
	                document.body.removeChild(tempInput);

	                resolve();
	            } catch (err) {
	                reject(err);
	            }
	        });
	    }
	}

	window.onload = function () {
	    setTimeout(myFunc, 1000);
	};


</script>
</body>
</html>