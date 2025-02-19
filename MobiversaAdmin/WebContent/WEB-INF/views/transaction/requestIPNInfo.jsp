<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400&display=swap"
	rel="stylesheet">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

<style>
.container-fluid {
	font-family: "Poppins", sans-serif;
}

@media ( max-width : 576px) {
	#ipndetails_container {
		padding: 1rem !important;
	}
}

#overlay {
	position: fixed;
	display: none;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(0, 0, 0, 0.5);
	z-index: 100;
	cursor: pointer;
}

.ff_poppins {
	font-family: "Poppins", sans-serif !important;
}

.error {
	color: #e15353;
	font-size: 13px;
}

.mb-0 {
	margin-bottom: 0 !important;
}

.mb-3 {
	margin-bottom: 20px !important;
}

.label {
	font-size: 0.95rem;
	color: #787878;
	padding: 0;
}

input[type=text]:not(.browser-default) {
	height: 2.8rem;
	color: #333739 !important;
}

input[type=text]::placeholder {
	color: #A9A9A9;
}

.select-wrapper input.select-dropdown {
	font-size: 13px !important;
	color: #333739;
	border-bottom: 1.5px solid #ffa50095 !important;
	font-family: "Poppins", sans-serif;
}

.dropdown-content li {
	min-height: 40px !important;
}

.dropdown-content li>span {
	padding: 8px 16px !important;
}

input[type=text]:not(.browser-default):focus:not([readonly]) {
	box-shadow: none !important;
}

#plus_icon {
	margin-left: 2px;
}

.add_btn {
	float: right;
	background-color: #fff;
	color: #005baa;
	border: 2px solid #005baa;
	border-radius: 50px;
	display: flex;
	align-items: center;
	padding: 0 !important;
	font-weight: 600;
	font-family: Poppins;
	font-size: 12px;
	cursor: default;
}

.submit_btn {
	background-color: #005BAA;
	color: #FFFFFF;
	border-radius: 50px;
	font-weight: 600;
	font-size: 13px;
	height: 2.8rem;
	padding: 0 30px;
	font-family: Poppins;
	text-align: center;
}

.add_btn img {
	cursor: pointer;
}

.submit_btn:hover, .submit_btn:focus {
	background-color: #005BAA;
	color: #FFFFFF;
	box-shadow: 5px 5px 10px -3px rgba(0, 90, 170, 0.2), /* Right shadow */
                 -5px 5px 10px -3px rgba(0, 90, 170, 0.2),
		/* Left shadow */
                 0 5px 10px -3px rgba(0, 90, 170, 0.2) !important;
	/* Bottom shadow */
}

#plus_icon:hover, #minus_icon:hover {
	border-radius: 50%;
	background-color: #D4D2D350;
}

.add_btn:hover, .add_btn:focus {
	background-color: #fff;
	color: #005baa;
	box-shadow: none;
}

.card {
	border-radius: 10px;
	box-shadow: 5px 5px 10px -3px rgba(45, 45, 45, 0.05), /* Right shadow */
		
                     -5px 5px 10px -3px rgba(45, 45, 45, 0.05),
		/* Left shadow */
                     0 5px 10px -3px rgba(45, 45, 45, 0.05) !important;
	/* Bottom shadow */
}

#minus_icon {
	padding: 2px;
	margin-left: 2px;
}

#plus_icon {
	margin-right: 2px;
}

.confirm_overlay, .result_overlay {
	display: none;
	position: fixed;
	z-index: 100;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	/*background-color: rgb(0, 0, 0);*/
	background-color: rgba(0, 0, 0, 0.2);
	scrollbar-width: none;
}

.confirm_modal, .result_modal {
	background-color: #fff;
	border-radius: 10px !important;
	margin: 1% auto;
}

.modal_row {
	width: 100%;
	height: 100%;
	align-content: center;
}

.modal-header {
	color: #005BAA;
	text-align: center;
	padding: 10px;
	border-bottom: 1.5px solid orange;
	font-weight: 500;
	font-size: 16px;
}

.modal-content {
	padding: 15px 24px;
}

.modal-footer {
	background-color: #EFF8FF;
	border-bottom-left-radius: 10px;
	border-bottom-right-radius: 10px;
	padding: 11.5px 0;
}

.modal-content {
	padding: 15px 30px;
	font-family: "Poppins", sans-serif;
	color: #515151 !important;
}

.closebtn {
	background-color: #005baa;
	border-radius: 50px;
	height: 33px !important;
	line-height: 33px !important;
	padding: 0 15px;
	font-size: 12px;
	font-family: "Poppins";
}

.closebtn:hover, .closebtn:focus {
	background-color: #005baa !important;
	padding: 0 15px;
	box-shadow: 5px 5px 10px -3px rgba(0, 90, 170, 0.2), /* Right shadow */
                 -5px 5px 10px -3px rgba(0, 90, 170, 0.2),
		/* Left shadow */
                 0 5px 10px -3px rgba(0, 90, 170, 0.2) !important;
	/* Bottom shadow */
}

.popup_messages {
	color: #515151;
	font-size: 16px;
	margin-bottom: 0;
}

.confirmbtn {
	background-color: #005baa;
	border-radius: 50px;
	height: 33px !important;
	line-height: 33px !important;
	padding: 0 15px;
	font-size: 12px;
	margin: 0 5px;
	font-family: "Poppins";
}

.confirmbtn:hover, .confirmbtn:focus {
	background-color: #005baa !important;
	box-shadow: 5px 5px 10px -3px rgba(0, 90, 170, 0.2), /* Right shadow */
                 -5px 5px 10px -3px rgba(0, 90, 170, 0.2),
		/* Left shadow */
                 0 5px 10px -3px rgba(0, 90, 170, 0.2) !important;
	/* Bottom shadow */
}

.cancelbtn {
	background-color: transparent;
	border-radius: 50px;
	height: 33px !important;
	line-height: 31px !important;
	padding: 0 15px;
	font-size: 12px;
	border: 1.5px solid #005baa;
	margin: 0 5px;
	color: #005baa;
	font-family: "Poppins";
}

.cancelbtn:hover, .cancelbtn:focus {
	background-color: transparent !important;
	color: #005baa !important;
	box-shadow: 5px 5px 10px -3px rgba(0, 90, 170, 0.2), /* Right shadow */
                 -5px 5px 10px -3px rgba(0, 90, 170, 0.2),
		/* Left shadow */
                 0 5px 10px -3px rgba(0, 90, 170, 0.2) !important;
	/* Bottom shadow */
}

.update_popup-modal {
	background-color: #fff;
	border-radius: 10px !important;
	margin: 1% auto;
}

.modal-header {
	padding: 12.5px 6px;
	height: auto;
	width: 100%;
	text-align: center;
	border-bottom: 1.5px solid #F5A623;
	font-size: 14px;
	font-weight: 600;
}

.footer {
	background-color: #EFF8FF !important;
	display: flex;
	align-items: center;
	justify-content: center;
}

.align-center {
	text-align: center;
}

.dropdown-content {
	top: 0 !important;
	height: 200px !important;
	scrollbar-width: none !important;
}

.toast-red {
	background-color: #d97676 !important;
	color: white !important;
	font-size: 14px !important;
}

#label_businessname {
	position: static !important;
	color: #858585;
	font-size: 14px;
}

.row .select2-container {
	padding: 0px !important;
	border: none !important;
	margin: 10px 0 !important;
	z-index: 0;
}

.select2-container--default .select2-selection--single .select2-selection__arrow
	{
	top: 0px;
	height: 20px !important;
}

.select2-container--default .select2-selection--single {
	background-color: #fff;
	border: none;
	border-bottom: 1.5px solid #ffa50095;
	border-radius: 0px;
}

.select2-container--default .select2-selection--single .select2-selection__rendered
	{
	color: #333739;
	line-height: 20px;
	font-size: 13px;
}

.select2-dropdown {
	border: 1px solid #F5F5F5 !important;
	box-shadow: 4px 0px 5px -2px #F5F5F5, /* Right shadow */
                 -4px 0px 5px -2px #F5F5F5, /* Left shadow */
                 0px 4px 15px -2px #F5F5F5; /* Bottom shadow */
}

#businessname_select ul:not(.browser-default) {
	padding-left: 0;
	list-style-type: none;
	border-bottom-left-radius: 8px;
	border-bottom-right-radius: 8px;
}

.select2-container--default .select2-search--dropdown .select2-search__field
	{
	font-size: 13px;
	font-family: "Poppins";
	color: #333739;
	border-bottom-color: #ffa50095 !important;
}

.select2-container--default .select2-results__option .select2-results__option
	{
	padding-left: 1em;
	font-size: 13px !important;
}

input[type=search]:not(.browser-default):focus:not([readonly]) {
	box-shadow: none;
}

.select2-container--default .select2-results__option--highlighted[aria-selected]
	{
	background-color: #005BAA;
	color: white;
	font-family: "Poppins", sans-serif;
}

.select2-results__message {
	font-family: "Poppins", sans-serif;
	font-size: 13px;
}

.select2-results__group {
	display: none !important;
}
</style>


</head>
<body>

	<div id="overlay">
		<div id="overlay_text">
			<img class="img-fluid"
				src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
		</div>
	</div>

	<div class="test" id="pop-bg-color"></div>
	<div id="overlay-popup"></div>

	<div class="container-fluid mb-0" id="pop-bg">
		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="">
							<h3 class="text-white mb-0">
								<strong class="ff_poppins" style="font-size: 20px;">Add
									New IPN</strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>


		<div class="row">





			<div class="col offset-l2 offset-m1 l8 m10 s12"
				id="ipndetails_container" style="padding: 0 5rem;">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<form
							action="${pageContext.request.contextPath}/transaction/updateIPNField"
							method="post" id="ipndetails_form">
							<input type="hidden" name="${_csrf.parameterName}"
								value="${_csrf.token}">
							<div class="row" id="businessname_select"
								style="margin-bottom: 10px;">
								<div class="col s12 m7 l7">
									<label for="merchantName" id="label_businessname">Select
										Business Name</label> <select name="username" id="merchantName"
										path="merchantName" class="browser-default select-filter">

										<optgroup label="Business Names" style="width: 100%">
											<option selected value="">Choose Business Name</option>
											<c:forEach var="merchant"
												items="${businessNamesAndUserNames}">
												<c:if test="${merchant[0]!=''}">
													<option value="${merchant[1]}">${merchant[0]}</option>
												</c:if>
											</c:forEach>

										</optgroup>
									</select>
								</div>
							</div>

							<div id="details_container">

								<div class="">
									<h3 class="text-white" style="margin-bottom: 10px !important;">
										<strong class="ff_poppins" style="font-size: 16px;">Enter
											Details</strong>
									</h3>
								</div>


								<div class="row mb-3" id="ipaddress_row">
									<div class="col l7 m7 s8">
										<label class="label">IP Address<span
											style="font-size: 0.8rem">(optional)</span></label> <input
											class="ff_poppins" type="text" id="ip_address"
											name="ip_address" value="" placeholder="i.e : 192.168.11.202"
											style="text-align: left; font-size: 13px; color: #707070; border-bottom: 1.5px solid #ffa50095 !important; margin-bottom: 2px;">
										<span id="ip_address_error" class="error"></span>
									</div>

									<div class="col l5 m5 s4 ">
										<button class="btn add_btn" type="button">
											<img id="minus_icon"
												src="${pageContext.request.contextPath}/resourcesNew1/assets/minus.svg"
												width="20" height="20" style="padding-left: 2px"> <span
												style="padding: 0 5px 0 8px">Add</span> <img id="plus_icon"
												src="${pageContext.request.contextPath}/resourcesNew1/assets/plus.svg"
												width="20" height="20">

										</button>
									</div>
								</div>

								<div class="row mb-3" id="row_1">
									<div class="col l7 m7 s12">
										<label class="label">Payment Method</label> <select
											name="payment_method1" id="payment_method1" onchange="">
											<option selected value="">Choose method</option>
											<option value="Cards">Cards</option>
											<option value="FPX">FPX</option>
											<option value="Boost">Boost</option>
											<option value="Grabpay">Grabpay</option>
											<option value="Tng">Touch'n Go</option>
											<option value="Shopee pay">Shopee pay</option>
											<option value="Payout">Payout</option>
										</select> <span id="payment_method1_error" class="error"></span>
									</div>
									<div class="col l5 m5 s12">
										<label class="label">IPN Endpoint</label> <input
											class="ff_poppins" type="text" id="ipn_url1" name="ipn_url1"
											value="" placeholder="https://nike.com/ipn-tng/"
											style="text-align: left; font-size: 13px; color: #707070; border-bottom: 1.5px solid #ffa50095 !important; margin-bottom: 2px;">
										<span id="ipn_url1_error" class="error"></span>

									</div>
								</div>

								<div class="row mb-3" id="row_2" style="display: none">
									<div class="col l7 m7 s12">
										<label class="label">Payment Method</label> <select
											name="payment_method2" id="payment_method2" onchange="">
											<option selected value="">Choose method</option>
											<option value="Cards">Cards</option>
											<option value="FPX">FPX</option>
											<option value="Boost">Boost</option>
											<option value="Grabpay">Grabpay</option>
											<option value="Tng">Touch'n Go</option>
											<option value="Shopee pay">Shopee pay</option>
											<option value="Payout">Payout</option>
										</select> <span id="payment_method2_error" class="error"></span>

									</div>
									<div class="col l5 m5 s12">
										<label class="label">IPN Endpoint</label> <input
											class="ff_poppins" type="text" id="ipn_url2" name="ipn_url2"
											value="" placeholder="https://nike.com/ipn-tng/"
											style="text-align: left; font-size: 13px; color: #707070; border-bottom: 1.5px solid #ffa50095 !important; margin-bottom: 2px;">
										<span id="ipn_url2_error" class="error"></span>

									</div>
								</div>

								<div class="row mb-3" id="row_3" style="display: none">
									<div class="col l7 m7 s12">
										<label class="label">Payment Method</label> <select
											name="payment_method3" id="payment_method3" onchange="">
											<option selected value="">Choose method</option>
											<option value="Cards">Cards</option>
											<option value="FPX">FPX</option>
											<option value="Boost">Boost</option>
											<option value="Grabpay">Grabpay</option>
											<option value="Tng">Touch'n Go</option>
											<option value="Shopee pay">Shopee pay</option>
											<option value="Payout">Payout</option>
										</select> <span id="payment_method3_error" class="error"></span>
									</div>
									<div class="col l5 m5 s12">
										<label class="label">IPN Endpoint</label> <input
											class="ff_poppins" type="text" id="ipn_url3" name="ipn_url3"
											value="" placeholder="https://nike.com/ipn-tng/"
											style="text-align: left; font-size: 13px; color: #707070; border-bottom: 1.5px solid #ffa50095 !important; margin-bottom: 2px;">
										<span id="ipn_url3_error" class="error"></span>

									</div>
								</div>



								<div class="row mb-3" id="row_4" style="display: none">
									<div class="col l7 m7 s12">
										<label class="label">Payment Method</label> <select
											name="payment_method4" id="payment_method4" onchange="">
											<option selected value="">Choose method</option>
											<option value="Cards">Cards</option>
											<option value="FPX">FPX</option>
											<option value="Boost">Boost</option>
											<option value="Grabpay">Grabpay</option>
											<option value="Tng">Touch'n Go</option>
											<option value="Shopee pay">Shopee pay</option>
											<option value="Payout">Payout</option>
										</select> <span id="payment_method4_error" class="error"></span>
									</div>
									<div class="col l5 m5 s12">
										<label class="label">IPN Endpoint</label> <input
											class="ff_poppins" type="text" id="ipn_url4" name="ipn_url4"
											value="" placeholder="https://nike.com/ipn-tng/"
											style="text-align: left; font-size: 13px; color: #707070; border-bottom: 1.5px solid #ffa50095 !important; margin-bottom: 2px;">
										<span id="ipn_url4_error" class="error"></span>

									</div>
								</div>


								<div class="row mb-3" id="row_5" style="display: none">
									<div class="col l7 m7 s12">
										<label class="label">Payment Method</label> <select
											name="payment_method5" id="payment_method5" onchange="">
											<option selected value="">Choose method</option>
											<option value="Cards">Cards</option>
											<option value="FPX">FPX</option>
											<option value="Boost">Boost</option>
											<option value="Grabpay">Grabpay</option>
											<option value="Tng">Touch'n Go</option>
											<option value="Shopee pay">Shopee pay</option>
											<option value="Payout">Payout</option>
										</select> <span id="payment_method5_error" class="error"></span>
									</div>
									<div class="col l5 m5 s12">
										<label class="label">IPN Endpoint</label> <input
											class="ff_poppins" type="text" id="ipn_url5" name="ipn_url5"
											value="" placeholder="https://nike.com/ipn-tng/"
											style="text-align: left; font-size: 13px; color: #707070; border-bottom: 1.5px solid #ffa50095 !important; margin-bottom: 2px;">
										<span id="ipn_url5_error" class="error"></span>

									</div>
								</div>

								<div class="row mb-3" id="row_6" style="display: none">
									<div class="col l7 m7 s12">
										<label class="label">Payment Method</label> <select
											name="payment_method6" id="payment_method6" onchange="">
											<option selected value="">Choose method</option>
											<option value="Cards">Cards</option>
											<option value="FPX">FPX</option>
											<option value="Boost">Boost</option>
											<option value="Grabpay">Grabpay</option>
											<option value="Tng">Touch'n Go</option>
											<option value="Shopee pay">Shopee pay</option>
											<option value="Payout">Payout</option>
										</select> <span id="payment_method6_error" class="error"></span>
									</div>
									<div class="col l5 m5 s12">
										<label class="label">IPN Endpoint</label> <input
											class="ff_poppins" type="text" id="ipn_url6" name="ipn_url6"
											value="" placeholder="https://nike.com/ipn-tng/"
											style="text-align: left; font-size: 13px; color: #707070; border-bottom: 1.5px solid #ffa50095 !important; margin-bottom: 2px;">
										<span id="ipn_url6_error" class="error"></span>

									</div>
								</div>

								<div class="row mb-3" id="row_7" style="display: none">
									<div class="col l7 m7 s12">
										<label class="label">Payment Method</label> <select
											name="payment_method7" id="payment_method7" onchange="">
											<option selected value="">Choose method</option>
											<option value="Cards">Cards</option>
											<option value="FPX">FPX</option>
											<option value="Boost">Boost</option>
											<option value="Grabpay">Grabpay</option>
											<option value="Tng">Touch'n Go</option>
											<option value="Shopee pay">Shopee pay</option>
											<option value="Payout">Payout</option>
										</select> <span id="payment_method7_error" class="error"></span>
									</div>
									<div class="col l5 m5 s12">
										<label class="label">IPN Endpoint</label> <input
											class="ff_poppins" type="text" id="ipn_url7" name="ipn_url7"
											value="" placeholder="https://nike.com/ipn-tng/"
											style="text-align: left; font-size: 13px; color: #707070; border-bottom: 1.5px solid #ffa50095 !important; margin-bottom: 2px;">
										<span id="ipn_url7_error" class="error"></span>

									</div>
								</div>

								<div class="row" style="display: flex; justify-content: center;">
									<button type="button" class="btn submit_btn" id="submit_btn">Submit</button>
								</div>

							</div>
						</form>
					</div>
				</div>
			</div>
		</div>



		<div class="result_overlay" id="result_overlay">
			<div class="row modal_row">
				<div class="col offset-l4 offset-m3 s12 m6 l4">
					<div id="result-modal" class="result_modal">
						<div class="modal-header">
							<p class="mb-0">Notification</p>
						</div>
						<div class="modal-content ">
							<div class="align-center">
								<img id="result_popup_image"
									src="${pageContext.request.contextPath}/resourcesNew1/assets/Successful-icon.svg"
									width="35" height="35">
							</div>
							<p class="align-center popup_messages" id="result_popup_text"></p>
						</div>
						<div class="align-center modal-footer footer">
							<button id="closebtn_result" class="btn blue-btn closebtn"
								type="button" onclick="closeresultModal()" name="action">Close
							</button>

						</div>
					</div>
				</div>
			</div>
		</div>


		<div class="confirm_overlay" id="confirm_overlay">
			<div class="row modal_row">
				<div class="col offset-l4 offset-m3 s12 m6 l4">
					<div id="confirm-modal" class="confirm_modal">

						<div class="modal-header">
							<p class="mb-0">Confirmation</p>
						</div>
						<div class="modal-content ">
							<div class="align-center">
								<img
									src="${pageContext.request.contextPath}/resourcesNew1/assets/confirmation.svg"
									width="35" height="35">
							</div>

							<p class="align-center popup_messages">Do you want to update
								this to new URLs?</p>
						</div>
						<div class="align-center modal-footer footer">
							<button id="close_btn" class="btn cancelbtn" type="button"
								onclick="closePopupModal()" name="action">Cancel</button>
							<button id="confirm_btn" class="btn confirmbtn " type="button"
								onclick="submitDetails()" name="action">Update</button>

						</div>

					</div>
				</div>
			</div>
		</div>


		<input type="hidden" id="status_update_result"
			name="status_update_result" value="${isEmailSent}">
	</div>


	<input type="hidden" id="merchant_name" name="merchant_name" />
	<script>

    document.addEventListener('DOMContentLoaded', function () {
    	
    	
        const merchantName = document.getElementById('merchantName');
        const ipndetails_container = document.getElementById("details_container");

        const inputsAndSelects = ipndetails_container.querySelectorAll('input, select, .select-wrapper, #plus_icon, #minus_icon, #submit_btn');

        function updateContainerAccessibility() {
            if (merchantName.value && merchantName.value.trim() !== '') {
                // Enable access to inputs and selects
                inputsAndSelects.forEach(element => {
                    element.style.opacity = "1";
                    element.style.pointerEvents = "auto";
                });
                ipndetails_container.style.opacity = "1";
                ipndetails_container.style.pointerEvents = "auto";
            } else {
                // Disable access to inputs and selects
                inputsAndSelects.forEach(element => {
                    element.style.opacity = "0.5";
                    element.style.pointerEvents = "none";
                });
                ipndetails_container.style.opacity = "0.5";
                ipndetails_container.style.pointerEvents = "none";
            }
        }

        // Initial check
        updateContainerAccessibility();

  


    $(function () {

        $('#merchantName').on('change', function () {
        	updateContainerAccessibility()
            
        });
    });
    
    });


    $(document).ready(function () {



        $('.select-filter').select2({

        });

        $('.select-filter').on('select2:open', function() {
            var searchField = document.querySelector('.select2-search__field');
            if (searchField) {
                searchField.placeholder = 'search ...';
            }
        });

        var merchant_name = document.getElementById("merchant_name").value;


        if (merchant_name) {

            $('#merchantName option').each(function () {
                if ($(this).text().trim() === merchant_name.trim()) {
                    $(this).prop('selected', true);
                    // Trigger change to update Select2 display
                    $('#merchantName').val($(this).val()).trigger('change.select2');
                    return false;
                }
            });

        }

    });


    function openConfirmPopup() {
        document.getElementById("confirm_overlay").style.display = "block";

    }
    function isValidIpAddress(ip) {
        const ipv4Regex = /^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
        const ipv6Regex = /^(([0-9a-fA-F]{1,4}:){7,7}[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,7}:|([0-9a-fA-F]{1,4}:){1,6}:[0-9a-fA-F]{1,4}|([0-9a-fA-F]{1,4}:){1,5}(:[0-9a-fA-F]{1,4}){1,2}|([0-9a-fA-F]{1,4}:){1,4}(:[0-9a-fA-F]{1,4}){1,3}|([0-9a-fA-F]{1,4}:){1,3}(:[0-9a-fA-F]{1,4}){1,4}|([0-9a-fA-F]{1,4}:){1,2}(:[0-9a-fA-F]{1,4}){1,5}|[0-9a-fA-F]{1,4}:((:[0-9a-fA-F]{1,4}){1,6})|:((:[0-9a-fA-F]{1,4}){1,7}|:)|fe80:(:[0-9a-fA-F]{0,4}){0,4}%[0-9a-zA-Z]{1,}|::(ffff(:0{1,4}){0,1}:){0,1}((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])|([0-9a-fA-F]{1,4}:){1,4}:((25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9])\.){3,3}(25[0-5]|(2[0-4]|1{0,1}[0-9]){0,1}[0-9]))$/;

        console.log("ip4 : "+ipv4Regex.test(ip)+" "+" ip6 : ",ipv6Regex.test(ip));
        return ipv4Regex.test(ip) || ipv6Regex.test(ip);
    }

    function isValidUrl(url) {
        const urlPattern = /^(http|https):\/\/[a-zA-Z0-9.-]+(?:\/[a-zA-Z0-9-._~:?#@!$&'()*+,;=]*)?$/;
        return urlPattern.test(url);
    }



    function closePopupModal() {
        document.getElementById("confirm_overlay").style.display = "none";
    }


    function updateResultPopup(result) {

        document.getElementById("result_overlay").style.display = "block";

        const approveImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/Succesful-icon.svg';
        const rejectImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/Declined.svg';
        const result_popup_image = document.getElementById('result_popup_image');
        const result_popup_text = document.getElementById('result_popup_text');

        if (result === 'true') {
            result_popup_image.src = approveImgPath;
            result_popup_text.innerText = 'Please check your mail';
        } else if (result === 'false') {
            result_popup_image.src = rejectImgPath;
            result_popup_text.innerText = 'Problem Encountered. Please Try again later.';
        }
    }

    document.addEventListener('DOMContentLoaded', () => {
        const result = document.getElementById("status_update_result");
        console.log(result.value)
        if (result && result.value) {
            updateResultPopup(result.value);
        }
    });

    function closeresultModal() {
        document.getElementById("result_overlay").style.display = "none";
    }




    function submitDetails() {
        document.getElementById("confirm_overlay").style.display = "none";
        document.getElementById("overlay").style.display = "block";


        const form = document.getElementById("ipndetails_form");
        form.submit();

    }

    function isElementVisible(el) {
        if (!el) return false;

        const style = window.getComputedStyle(el);

        console.log(" result : ", style.display !== 'none' && style.visibility !== 'hidden' && el.offsetHeight > 0)
        // Check if the element itself or any of its ancestors is hidden
        return style.display !== 'none' && style.visibility !== 'hidden' && el.offsetHeight > 0;
    }


    function validateInputs() {
        let isValid = true;

        // Validate IP Address (if provided)
        const ipAddress = document.getElementById('ip_address').value.trim();
        if (ipAddress && !isValidIpAddress(ipAddress)) {
            document.getElementById('ip_address_error').innerText = 'Invalid IP address.';
            isValid = false;
        } else {
            document.getElementById('ip_address_error').innerText = '';
        }


       // const selects = Array.from(document.querySelectorAll('select[name^="payment_method"]')).filter(select => select.style.display !== 'none');

        const selects = Array.from(document.querySelectorAll('select[name^="payment_method"]'))
            .filter(select => {
                const wrapper = select.closest('.select-wrapper');
                return wrapper && isElementVisible(wrapper);
            });



        let allSelectsValid = true;

        const values = new Set(); // To track unique values
        const duplicates = new Set();

        selects.forEach(select => {

            if (!select.value && select.style.display !== 'none') {
                document.getElementById(select.id + '_error').innerText = 'Please select a payment method.';
                allSelectsValid = false;
            } else {
                document.getElementById(select.id+'_error').innerText = '';
            }

            if (select.value) {
                if (values.has(select.value)) {
                    duplicates.add(select.value);
                }
                values.add(select.value);
            }
        });

        if (duplicates.size > 0) {
            allSelectsValid = false;
            selects.forEach(select => {
                if (duplicates.has(select.value)) {
                    document.getElementById(select.id + '_error').innerText = 'Duplicate payment methods are not allowed.';
                }
            });
        }



        //const inputs = Array.from(document.querySelectorAll('input[name^="ipn_url"]')).filter(input => input.style.display !== 'none');

        const inputs = Array.from(document.querySelectorAll('input[name^="ipn_url"]'))
            .filter(input => isElementVisible(input));

        inputs.forEach(input => {
            const errorElement = document.getElementById(input.id + '_error');

            if(input.style.display !== 'none') {
                if (!input.value.trim()) {
                    // If the input field is empty
                    if (errorElement) {
                        errorElement.innerText = 'IPN URL is required.';
                    }
                    isValid = false;
                } else if (!isValidUrl(input.value.trim())) {
                    // If the input field is not empty but the URL is invalid
                    if (errorElement) {
                        errorElement.innerText = 'Invalid IPN URL.';
                    }
                    isValid = false;
                } else {
                    // If the input field is not empty and the URL is valid
                    if (errorElement) {
                        errorElement.innerText = '';
                    }
                }
            }

        });

        return isValid && allSelectsValid;
    }



    document.getElementById("submit_btn").addEventListener('click', function (event) {
        if (!validateInputs()) {
            event.preventDefault();
            return;
        }else{
            openConfirmPopup();
        }
    });


    function clearValidationAndValues(row) {
        // Clear values
        row.querySelectorAll('input[name^="ipn_url"]').forEach(input => {
            input.value = '';
        });
        row.querySelectorAll('select[name^="payment_method"]').forEach(select => {

            $("#" + select.id).val("");
            M.updateTextFields();

            $("#" + select.id).prop("selectedIndex", 0);
            $("#" + select.id).formSelect();

            select.value = '';
        });

        row.querySelectorAll('.error').forEach(error => {
            error.innerText = '';
        });
    }


    document.addEventListener('DOMContentLoaded', function () {

        var merchantName = document.getElementById("merchant_name");

        const rows = Array.from(document.querySelectorAll('[id^="row_"]'));
        let currentVisibleIndex = 0;

        function updateIcons() {

            if (merchantName.value && merchantName.value.trim() !== '') {
                document.getElementById('plus_icon').style.pointerEvents = currentVisibleIndex < rows.length - 1 ? 'auto' : 'none';
                document.getElementById('plus_icon').style.opacity = currentVisibleIndex < rows.length - 1 ? '1' : '0.5';

                document.getElementById('minus_icon').style.pointerEvents = currentVisibleIndex > 0 ? 'auto' : 'none';
                document.getElementById('minus_icon').style.opacity = currentVisibleIndex > 0 ? '1' : '0.5';
            }
        }

        function showNextRow() {
            if (currentVisibleIndex < rows.length - 1) {
                currentVisibleIndex++;
                rows[currentVisibleIndex].style.display = 'flex';
                updateIcons();
            }
        }

        function hidePreviousRow() {
            if (currentVisibleIndex > 0) {
                clearValidationAndValues(rows[currentVisibleIndex]); // Clear values and errors of the current row
                rows[currentVisibleIndex].style.display = 'none';
                currentVisibleIndex--;
                updateIcons();
            }
        }

        // Initially hide all rows except the first one
        rows.forEach((row, index) => {
            if (index > 0) row.style.display = 'none';
        });

        document.getElementById('plus_icon').addEventListener('click', showNextRow);
        document.getElementById('minus_icon').addEventListener('click', hidePreviousRow);

        // Initialize icons
        updateIcons();
    });


</script>





</body>
</html>