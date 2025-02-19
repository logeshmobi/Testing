<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Master search</title>


<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet">


<style>
@import
	url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap')
	;

body {
	font-family: "Poppins", sans-serif !important;
}

.row .col {
	padding: 0 1.2rem !important;
}

.input-field label {
	left: 1.2rem !important;
}

.moredetails-open {
	overflow-y: scroll !important;
}

.align-center {
	text-align: center !important;
}

.align-right {
	text-align: right !important;
}

.box-shadow-none {
	box-shadow: none !important;
}

.shadow-sm {
	box-shadow: 0px 5px 16px 2px #f3f2f2 !important;
}

.color-blue {
	color: #005baa;
}

.color-green {
	color: #0C9F02;
}

.color_lightgreen {
	color: #45da3b;
}

.color-red {
	color: red;
}

.color-orange {
	color: orange;
}

.color-skyblue {
	color: #49CCF9;
}

.color-grey {
	color: #858585;
}

.fw-600 {
	font-weight: 600;
}

.fw-500 {
	font-weight: 500;
}

.ml-1 {
	margin-left: 4px !important;
}

.m-0 {
	margin: 0;
}

.cursor-pointer {
	cursor: pointer;
}

.radius-10 {
	border-radius: 10px !important;
}

.font-size-md {
	font-size: 1.4rem;
}

.input-field>label {
	font-size: 1.3rem !important;
}

.mt-0 {
	margin-top: 0 !important;
}

.mb-0 {
	margin-bottom: 0 !important;
}

.mb-1 {
	margin-bottom: 4px !important;
}

.mt-2 {
	margin-top: 2rem !important;
}

.mt-1 {
	margin-top: 1rem !important;
}

input[type=text]:not(.browser-default) {
	margin: 8px 0 8px 0 !important;
	font-size: 13px !important;
	border-bottom: 1.5px solid #F5A623 !important;
}

.select-wrapper input.select-dropdown {
	border-bottom: 1.5px solid #F5A623 !important;
	font-family: "Poppins", sans-serif !important;
}

.blue-btn {
	background-color: #005baa !important;
	border-radius: 50px !important;
	text-transform: capitalize !important;
	font-size: 0.9rem !important;
	padding: 0 20px !important;
}

.select-wrapper .caret {
	fill: #005baa !important;
}

input[type=text]:not(.browser-default):focus:not([readonly]) {
	box-shadow: none !important;
}

input[type=text]:not(.browser-default):focus:not([readonly])+label {
	color: #929292 !important;
}

.dropdown-content li>span {
	color: #929292 !important;
}

.dropdown-content li:hover {
	background-color: #005baa !important;
}

.dropdown-content li:hover span {
	color: #fff !important;
}

.dropdown-content {
	top: 10px !important;
}

.scrollable-table {
	overflow-x: auto;
	text-wrap: nowrap;
	scrollbar-width: thin;
}

.summary_table th {
	color: #005baa;
	font-weight: 600 !important;
	border-bottom: 2px solid #F5A623;
	font-size: 13px !important;
	padding: 10px 25px;
}

.summary_table td {
	color: #929292;
	font-weight: 400 !important;
	/* border-bottom: 1.5px solid #ddd ; */
	font-size: 13px !important;
	padding: 10px 25px;
}

/* modal style */
.modal-overlay {
	opacity: 0.3 !important;
}

.modal-header {
	padding: 10px 6px;
	height: auto;
	width: 100%;
	text-align: center;
	border-bottom: 1.5px solid #F5A623;
	font-size: 17px;
}

.footer {
	background-color: #EFF8FF !important;
	display: flex;
	align-items: center;
	justify-content: center;
}

.closebtn {
	height: 32px !important;
	line-height: 32px !important;
	min-width: 15%;
	width: fit-content;
	font-size: 12px !important;
}

.content-declinedreason {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	padding: 7px 30px !important;
}

.content-moredetails {
	padding: 7px 24px !important;
}

.txn-table {
	height: 65vh;
	overflow-y: auto;
	scrollbar-width: none;
}

.content-moredetails table thead tr th {
	padding: 10px 0px;
	font-size: 18px;
}

.content-moredetails table thead tr {
	border-bottom: none !important;
}

.header_txndetails {
	padding: 8px 6px 14px 6px;
	border-bottom: 2px solid #F5A623;
}

.header_paymentslip {
	padding: 12px 6px 12px 6px;
	border-bottom: 2px solid #F5A623;
}

/* more details modal   */
.modal-container {
	display: none;
	position: fixed;
	z-index: 10;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgb(0, 0, 0);
	background-color: rgba(0, 0, 0, 0.4);
	scrollbar-width: none;
}

.moredetails-modal-class {
	min-width: 55%;
	width: fit-content;
	max-width: 65%;
	background-color: #fff;
	border-radius: 10px !important;
	margin: 1% auto;
}

.moredetails_footer, .footer_slip, .footer_reason {
	border-radius: 0 0 10px 10px;
	background-color: #fafafa;
	padding: 4px 6px;
	height: 56px;
	width: 100%;
	text-align: right;
}

/* slip modal */
.modal-container_slip {
	display: none;
	position: fixed;
	z-index: 10;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgb(0, 0, 0);
	background-color: rgba(0, 0, 0, 0.4);
	scrollbar-width: none;
}

.viewreceipt-modal-class {
	min-width: 360px !important;
	width: fit-content !important;
	max-width: 33% !important;
	background-color: #fff !important;
	border-radius: 10px !important;
	margin: 3% auto;
}

/* decline reason modal */
.modal_container_declinereason {
	display: none;
	position: fixed;
	z-index: 10;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgb(0, 0, 0);
	background-color: rgba(0, 0, 0, 0.4);
	scrollbar-width: none;
}

.decline-reason-modal-class {
	min-width: 407px !important;
	width: fit-content !important;
	max-width: 35% !important;
	background-color: #fff !important;
	border-radius: 10px !important;
	margin: 10% auto;
}

.txn-popuptable tr {
	border-bottom: none !important;
}

.txn-details-table tr {
	display: flex;
}

.txn-details-table tr td {
	padding: 2px 0px;
	display: flex;
	flex-direction: column;
	align-items: flex-start;
	justify-content: center;
	overflow-wrap: anywhere;
}

.hyphen {
	text-align: center;
	font-size: 23px;
	font-weight: 400;
	color: #858585;
	flex: 0.3;
}

.colan {
	text-align: left;
	font-size: 13px;
	font-weight: 400;
	color: #858585;
	flex: 0.1 !important;
}

.data_option {
	flex: 0.9;
	font-weight: 600 !important;
	color: #858585 !important;
	font-size: 14px;
}

.data_value {
	flex: 1;
	color: #858585;
	font-size: 14px;
}

.slip_outer_border {
	padding: 15px 28px;
}

.slip_inner_border {
	padding: 10px 15px;
	border: 1.5px solid #ffc15d;
	border-radius: 10px;
	background-color: rgb(237, 250, 255) !important;
}

.watermark {
	background: url(Images/mobi-watermark.svg);
	background-repeat: no-repeat;
	background-position: center;
}

.slip_payment_details tbody tr {
	border-bottom: none !important;
	display: flex;
	padding: 4px 0px;
}

.slip_payment_details tbody tr td {
	padding: 2px 0px;
	flex: 1;
	font-size: 12px;
	overflow-wrap: anywhere;
}

.content-viewreceipt {
	font-size: 12px !important;
}

.slip_option {
	flex: 0.7 !important;
}

#overlay_text {
	position: absolute;
	top: 50%;
	left: 50%;
	font-size: 50px;
	color: #FFF;
	transform: translate(-50%, -50%);
}

#overlay_text .img-fluid {
	max-width: 100%;
}

#overlay_text img {
	height: 150px;
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
	z-index: 2;
	cursor: pointer;
}
</style>

 <script
            src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2.0.5/FileSaver.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/dom-to-image/2.6.0/dom-to-image.min.js"></script>

</head>


<body class="bodycontent">

	<div id="overlay">
		<div id="overlay_text">
			<img class="img-fluid"
				src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
		</div>
	</div>


	<div class="container-fluid ">

		<%-- <h1>text is :${merchantName}</h1> --%>

		<div class="row">
			<div class="col s12 m12">
				<div class="card box-shadow-none">
					<div class="card-content">

						<!-- heading -->
						<div class="row">
							<div class="col s12 m12">
								<div class="card shadow-sm radius-10">
									<div class="card-content color-blue"
										style="padding: 24px 30px;">
										<p class="font-size-md">
											<strong class="fw-600">Master Search</strong>
										</p>
									</div>
								</div>
							</div>
						</div>
						<!-- /heading -->

						<!-- search option -->
						<div class="row">
							<div class="col s12 m12">
								<div class="card shadow-sm radius-10">
									<div class="card-content ">


										<div class="row mb-0">
											<%--                                            <form action="${pageContext.request.contextPath}/searchNew/fpxMasterSearch" method="post"--%>
											<%--                                                onsubmit="return validateForm()">--%>

											<form id="RequestForm" method="post"
												onsubmit="return validateForm()">

												<%--                                                <div class="input-field  col s12 m3 l3" style="">--%>
												<%--                                                    <select class="" id="payment_method" name = 'payment_method'>--%>
												<%--                                                        <option value="" selected>Choose Payment type</option>--%>
												<%--                                                        <option value="Card"> Payin - Card Transaction</option>--%>
												<%--                                                        <option value="FPX"> Payin - FPX-Internet Banking</option>--%>
												<%--                                                        <option value="Ewallet"> Payin - E-Wallet</option>--%>
												<%--                                                        <option value="Payout">Payouts</option>--%>
												<%--                                                    </select>--%>
												<%--                                                    <label class="" style="font-size: 1.1rem !important;">Payment--%>
												<%--                                                        Method</label>--%>
												<%--                                                </div>--%>

												<%--                                                <div class="input-field  col s12 m3 l3" id=" search_type_col" style="">--%>
												<%--                                                    <select class="" id="search_type" name="search_type">--%>

												<%--                                                        <option value="" selected style="color: #ddd !important;">Choose Search Type</option>--%>
												<%--                                                        <!--  <option value="Card">Card Transaction</option>--%>
												<%--                                                        <option value="FPX">FPX-Internet Banking</option>--%>
												<%--                                                        <option value="Ewallet">E-Wallet</option>--%>
												<%--                                                        <option value="Payout">Payout</option> -->--%>
												<%--                                                    </select>--%>
												<%--                                                    <label class="" style="font-size: 1.1rem !important;">Choose Type</label>--%>
												<%--                                                </div>--%>

												<%--                                                <div class="input-field col s12 m3 l4" style="">--%>
												<%--                                                    <input placeholder="501754244340934_MNTX, 501754200390934_MNTX"--%>
												<%--                                                        id="transaction_ids" type="text" class="validate">--%>
												<%--                                                    <label for="transaction_ids">Enter ID's <span--%>
												<%--                                                            style="font-size: 12px; color: #9C8EF2;">(Upto 20--%>
												<%--                                                            ID's)</span></label>--%>
												<%--                                                </div>--%>

												<%--                                                <input type="hidden" id="dataArray" name="dataArray">--%>
												<%--                                                --%>
												<%--                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">--%>

												<%--                                                <div class="input-field   col s12 m3 l2 mt-2 "--%>
												<%--                                                    style=" text-align: center;">--%>
												<%--                                                    <button class="btn waves-effect waves-light blue-btn"--%>
												<%--                                                        type="submit">Search</button>--%>

												<%--                                                </div>--%>
												<div class="input-field  col s12 m6 l3" style="">
													<select class="" id="payment_method" name='payment_method'>
														<option value="" selected>Choose Payment Method</option>
														<c:if test="${isCardEnabled == 'YES' }">
															<option value="Card">Payin - Card Transaction</option>
														</c:if>
														<option value="FPX">Payin - FPX-Internet Banking</option>
														<option value="Ewallet">Payin - E-Wallet</option>
														<c:if test="${isPayoutEnabled.toUpperCase() == 'YES' }">
															<option value="Payout">Payouts</option>
														</c:if>
													</select> <label class="" style="font-size: 1.1rem !important;">Payment
														Method</label>
												</div>

												<div class="input-field hide col s12 m6 l3"
													id="payment_type_col" style="">
													<select class="" id="payment_type" name="payment_type"
														tabindex="-1">

														<option value="" selected style="color: #ddd !important;">Choose
															Payment Type</option>
														<option value="Boost">Boost</option>
														<option value="Grab">Grab Pay</option>
														<option value="Touch'N_Go">Touch'N Go</option>
														<option value="Shopeepay">Shopee Pay</option>
													</select> <label class="" style="font-size: 1.1rem !important;">Payment
														Type</label>
												</div>

												<%--                                            <div class="input-field  col s12 m6 l3" id=" search_type_col" style="">--%>
												<%--                                                <select class="" id="search_type" name="search_type">--%>

												<%--                                                    <option value="" selected style="color: #ddd !important;">Choose--%>
												<%--                                                        Search Type--%>
												<%--                                                    </option>--%>
												<%--                                                </select>--%>
												<%--                                                <label class="" style="font-size: 1.1rem !important;">Choose--%>
												<%--                                                    Type</label>--%>
												<%--                                            </div>--%>


												<div class="input-field  col s12 m6 l3"
													id=" search_type_col" style="">

													<div class="hide search_type_div"
														id="search_type_col_empty">
														<select class="search_type_empty " id="search_type_empty"
															name="search_type_empty">
															<option value="" selected style="color: #ddd !important;">Choose
																Search Type</option>
														</select> <label class="" style="font-size: 1.1rem !important;"
															for="search_type_card">Choose Type</label>
													</div>

													<div class="hide search_type_div" id="search_type_col_card">
														<select class="search_type_card " id="search_type_card"
															name="search_type_card">
															<option value="" selected style="color: #ddd !important;">Choose
																Search Type</option>
															<option value="ReferenceNo">Reference No</option>
															<option value="ApprovalCode">Approval Code</option>
															<option value="RRN">RRN</option>
															<option value="CardNumber">Card Number</option>
														</select> <label class="" style="font-size: 1.1rem !important;"
															for="search_type_card">Choose Type</label>
													</div>


													<%--                                                 fpx choose type--%>

													<div class="hide search_type_div" id="search_type_col_fpx">
														<select class="search_type_fpx " id="search_type_fpx"
															name="search_type_fpx">
															<option value="" selected style="color: #ddd !important;">Choose
																Search Type</option>
															<option value="ReferenceNo">Reference No</option>
															<option value="ApprovalCode">Approval Code</option>
														</select> <label class="" style="font-size: 1.1rem !important;"
															for="search_type_fpx">Choose Type</label>
													</div>


													<%--                                               //ewallet --%>

													<div class="hide search_type_div"
														id="search_type_col_ewallet_grabboost">
														<select class="search_type_ewallet_boost_grab "
															id="search_type_ewallet_boost_grab"
															name="search_type_ewallet_boost_grab">
															<option value="" selected style="color: #ddd !important;">Choose
																Search Type</option>
															<option value="Reference_No">Reference No</option>
															<option value="Approval_Code">Approval code</option>
															<option value="RRN">RRN</option>
														</select> <label class="" style="font-size: 1.1rem !important;"
															for="search_type_ewallet_boost_grab">Choose Type</label>
													</div>

													<div class="hide search_type_div"
														id="search_type_col_tngspp">
														<select class="search_type_ewallet_tng_spp "
															id="search_type_ewallet_tng_spp"
															name="search_type_ewallet_tng_spp">
															<option value="" selected style="color: #ddd !important;">Choose
																Search Type</option>
															<option value="Reference_No">Reference No</option>
															<option value="Approval_Code">Approval Code</option>

														</select> <label class="" style="font-size: 1.1rem !important;"
															for="search_type_ewallet_tng_spp">Choose Type</label>
													</div>


													<div class="hide search_type_div"
														id="search_type_col_payout">
														<select class="search_type_payout "
															id="search_type_payout" name="search_type_payout">
															<option value="" selected style="color: #ddd !important;">Choose
																Search Type</option>
															<option value="Transaction_Id">Transaction ID</option>
															<option value="Payout_Id">Payout Id</option>
														</select> <label class="" style="font-size: 1.1rem !important;"
															for="search_type_fpx">Choose Type</label>
													</div>


												</div>


												<!-- mobi loading logo -->
												<div id="overlay" id="loading-gif">
													<div id="overlay_text">
														<img class="img-fluid"
															src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
													</div>
												</div>


												<style>
#overlay_text {
	position: absolute;
	top: 50%;
	left: 50%;
	font-size: 50px;
	color: #FFF;
	transform: translate(-50%, -50%);
}

#overlay_text .img-fluid {
	max-width: 100%;
}

#overlay_text img {
	height: 150px;
}

.hidden {
	pointer-events: none;
	opacity: 0.5;
	cursor: not-allowed;
	color: #999;
}

input[type=text]::placeholder {
	color: #D0D0D0;
}

/*.card_summary tr th,.card_summary tr td{*/
/*    padding:10px !important;*/
/*}*/
.card_summary tr th, .card_summary tr td {
	padding: 10px 15px !important;
}
</style>


												<div class="tooltip-container input-field col s12 m6 l4"
													id="searchinput_div" style="">
													<input placeholder="501754244340934, 501754200390934"
														id="transaction_ids" type="text" class="validate">
													<!-- 	<label for="transaction_ids">Enter ID's <span
														style="font-size: 12px; color: #9C8EF2;">(Upto 20
															ID's)</span></label> -->
													<span class="tooltip-text" id="tooltip_text"
														style="word-break: keep-all; font-size: 12px">separate
														each IDs with a comma (,)</span> <label for="transaction_ids"
														id="transaction_ids_label">Enter ID's <span
														style="font-size: 12px; color: #9C8EF2 !important;">(Upto
															20 ID's)</span></label>
												</div>

												<input type="hidden" id="dataArray" name="dataArray">
												<input type="hidden" name="${_csrf.parameterName}"
													value="${_csrf.token}">

												<div class="input-field  col s12 m3 l2 mt-2 "
													id="search_div" style="text-align: center;">
													<button class="btn waves-effect waves-light blue-btn"
														type="submit">Search</button>
												</div>


											</form>
										</div>


									</div>
								</div>
							</div>
						</div>
						<!-- /search option -->


<style>
    .tooltip-container {
        position: relative;
        display: inline-block;
    }



    #searchinput_div label,
    #searchinput_div label span {
        color: #707070 !important;
    }


    #searchinput_div input:focus + label,
    #searchinput_div input:focus + label span {
        color: #707070 !important;
    }


    .tooltip-container .tooltip-text {
        visibility: hidden;
        width: 160px;
        background-color: black;
        color: white;
        text-align: center;
        border-radius: 6px;
        padding: 5px 0;
        position: absolute;
        z-index: 1;
        bottom: 125%; /* Position the tooltip above the input */
        left: 50%;
        margin-left: -80px; /* Use half of the tooltip width to center it */
        opacity: 0;
        transition: opacity 0.3s;
    }

    .tooltip-container .tooltip-text::after {
        content: "";
        position: absolute;
        top: 100%; /* Arrow at the bottom */
        left: 50%;
        margin-left: -5px;
        border-width: 5px;
        border-style: solid;
        border-color: #555 transparent transparent transparent; /* Arrow color */
    }

    .tooltip-container:hover .tooltip-text {
        visibility: visible;
        opacity: 1;
    }

</style>

<script>
    document.addEventListener('DOMContentLoaded', function () {
        const inputField = document.getElementById('transaction_ids');
        const tooltip = document.querySelector('.tooltip-text');

        // Show tooltip on hover
        inputField.addEventListener('mouseover', function () {
            if (inputField.value === '') {
                tooltip.style.visibility = 'visible';
                tooltip.style.opacity = '1';
            }
        });

        // Hide tooltip when mouse leaves input field
        inputField.addEventListener('mouseout', function () {
            tooltip.style.visibility = 'hidden';
            tooltip.style.opacity = '0';
        });

        // Show tooltip on focus if input is empty
        inputField.addEventListener('focus', function () {
            if (inputField.value === '') {
                tooltip.style.visibility = 'visible';
                tooltip.style.opacity = '1';
            }
        });

        // Hide tooltip on blur
        inputField.addEventListener('blur', function () {
            tooltip.style.visibility = 'hidden';
            tooltip.style.opacity = '0';
        });

        // Hide tooltip on input and do not show again if input is not empty
        inputField.addEventListener('input', function () {
            tooltip.style.visibility = 'hidden';
            tooltip.style.opacity = '0';
        });
    });


</script>

						<!-- table content -->
						<div class="row">
							<div class="col s12">
								<div class="card shadow-sm radius-10">
									<div class="card-content ">
										<div class=" scrollable-table">
											<table class="card_summary summary_table">
												<thead>
													<tr>
														<th>Date</th>
														<th>Time</th>
														<c:if test="${txnType == 'Ewallet'}">
															<th>Reference No</th>
														</c:if>
														<%-- <c:if test="${txnType != 'Ewallet'}">
                                                    <th>Transaction ID</th>
                                                </c:if> --%>
														<c:if test="${txnType != 'Ewallet'}">
															<!-- Nested condition to check if chooseType is 'Transaction_Id' -->
															<c:if
																test="${paginationBean.chooseType == 'Transaction_Id'}">
																<th>Transaction ID</th>
															</c:if>

															<!-- Nested condition to check if chooseType is 'Payout_Id' -->
															<c:if test="${paginationBean.chooseType == 'Payout_Id'}">
																<th>Payout ID</th>
															</c:if>
															<c:if
																test="${paginationBean.chooseType == 'ApprovalCode'}">
																<th>Approval Code</th>
															</c:if>
															<c:if
																test="${paginationBean.chooseType == 'ReferenceNo'}">
																<th>Reference No</th>
															</c:if>

															<c:if
																test="${paginationBean.chooseType != 'Transaction_Id' && paginationBean.chooseType != 'Payout_Id' && paginationBean.chooseType != 'ReferenceNo' && paginationBean.chooseType != 'ApprovalCode'}">
																<th>Transaction ID</th>
															</c:if>
														</c:if>
														<c:if test="${txnType == 'Card'}">
															<th style="text-align: left">Card number</th>
														</c:if>
														<c:if test="${txnType == 'Ewallet'}">
															<th>Payment Method</th>
														</c:if>

														<th class="align-right">Amount(RM)</th>

														<th>Status</th>

														<th class="align-center">Reason</th>
														<th class="align-center">More Details</th>
													</tr>
												</thead>


												<c:if test="${txnType == 'FPX'}">
													<c:forEach items="${paginationBean.itemList}" var="dto">

														<tr>
															<td>${dto.transactionDate}</td>
															<td>${dto.transactionTime}</td>
															<%-- <td>${dto.transactionId}</td> --%>
															<c:if
																test="${paginationBean.chooseType == 'ApprovalCode'}">
																<td>${dto.transactionId}</td>
															</c:if>
															<c:if
																test="${paginationBean.chooseType == 'ReferenceNo'}">
																<td>${dto.referenceNo}</td>
															</c:if>
															<td class="align-right">${dto.transactionAmount}</td>
															<td>
																<div
																	style="display: flex; align-items: center; justify-content: flex-start">

																	<c:if test="${dto.status == 'SETTLED'}">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/paid.svg"
																			width="20" height="20" alt="">
																		<span class="color-orange fw-500 ml-1;"
																			style="color: #28e528; text-transform: capitalize !important;">
																			Settled</span>
																	</c:if>

																	<c:if test="${dto.status == 'NOT SETTLED'}">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg"
																			width="20" height="20" alt="">
																		<span class="color-orange fw-500 ml-1;"
																			style="color: orange; text-transform: capitalize !important;">
																			Not Settled</span>
																	</c:if>

																	<c:if test="${dto.status == 'VOIDED'}">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Void.svg"
																			width="20" height="20" alt="">
																		<span class="color-orange fw-500 ml-1;"
																			style="color: #a49999; text-transform: capitalize !important;">
																			Voided</span>
																	</c:if>

																	<c:if test="${dto.status == 'REFUNDED'}">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Refunded.svg"
																			width="20" height="20" alt="">
																		<span class="color-orange fw-500 ml-1;"
																			style="color: #5d7d9a; text-transform: capitalize !important;">
																			Refunded</span>
																	</c:if>

																	<c:if test="${dto.status == 'EZYSETTLE'}">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/ezysettle.svg"
																			width="20" height="20" alt="">
																		<span class="color-orange fw-500 ml-1;"
																			style="color: #005BAA; text-transform: capitalize !important;">
																			EZYSETTLE </span>
																	</c:if>

																	<c:if test="${dto.status == 'FAILED'}">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Declined.svg"
																			width="20" height="20" alt="">
																		<span class="color-orange fw-500 ml-1;"
																			style="color: red; text-transform: capitalize !important;">
																			Failed</span>
																	</c:if>


																</div>
															</td>
															<td class="align-center "><c:if
																	test="${dto.debitAuthCode != '00'}">
																	<button class="declined" id="decline_reason_icon"
																		onclick="openDeclineReasonModal(
                                                                                '${dto.declinedReason}',
                                                                                '${txnType}'
                                                                                )"
																		style="border: none; background-color: transparent; cursor: pointer;">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/blureye.svg"
																			width="20" height="20" alt=""
																			style="margin-top: 4.5px;">
																	</button>
																</c:if></td>
															<td class="align-center">


																<button class="" id="more_details_icon"
																	onclick="openFpxMoredetailsModal(
                                                                            '${dto.transactionDate}',
                                                                            '${dto.transactionTime}',
                                                                            '${dto.transactionId}',
                                                                            '${dto.transactionAmount}',
                                                                            '${dto.bankName}',
                                                                            '${dto.status}',
                                                                            '${dto.mid}',
                                                                            '${dto.tid}',
                                                                            '${dto.mdrAmount}',
                                                                            '${dto.settlementAmount}',
                                                                            '${dto.settlementDate}',
                                                                            '${dto.timestamp}',
                                                                            '${dto.subMerchantMID}',
                                                                            '${dto.referenceNo}',
                                                                            '${dto.sellerExOrderNo}',
                                                                            '${dto.debitAuthCode}',
                                                                            '${dto.buyerName}',
                                                                            '${dto.merchanName}',
                                                                            '${dto.address}',
                                                                            '${dto.contactNumber}',
                                                                            '${dto.bankName}',
                                                                            '${dto.fpxSlipBankName}',
                                                                            '${dto.state}'
                                                                            // RefNo,ppId
                                                                            )"
																	style="border: none; background-color: transparent; cursor: pointer;">
																	<img
																		src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/more.svg"
																		width="20" height="20" alt=""
																		style="margin-top: 4.5px;">
																</button>


															</td>
														</tr>
													</c:forEach>


												</c:if>


												<%--for payout --%>

												<c:if test="${txnType == 'Payout'}">
													<c:forEach items="${paginationBean.itemList}" var="dto">
														<tr>
															<td>${dto.payoutdate}</td>
															<td>${dto.paidTime}</td>
															<%-- <td>${dto.invoiceidproof}</td> --%>
															<c:if test="${paginationBean.chooseType == 'Payout_Id'}">
																<td>${dto.payoutId}</td>
															</c:if>
															<c:if
																test="${paginationBean.chooseType == 'Transaction_Id'}">
																<td>${dto.invoiceidproof}</td>
															</c:if>
															<td style="text-align: right">${dto.payoutamount}</td>
															<td>
																<div
																	style="display: flex; align-items: center; justify-content: flex-start;">
																	<c:choose>
																		<c:when test="${dto.payoutstatus eq 'Paid'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/paid.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="color: #11bf36; font-weight: 500;">Paid</span>
																		</c:when>
																		<c:when test="${dto.payoutstatus eq 'Declined'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Declined.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="font-weight: 500; color: red">Declined</span>
																		</c:when>
																		<c:when test="${dto.payoutstatus eq 'Pending'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="font-weight: 500; color: orange">Pending</span>
																		</c:when>
																		<c:when test="${dto.payoutstatus eq 'In Process'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets//on_process.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="font-weight: 500; color: #8A8A8A">In
																				Process</span>
																		</c:when>
																		<c:when test="${dto.payoutstatus eq 'Requested'}">
																			<span></span>
																		</c:when>
																		<c:otherwise>
																			<!-- Handle unknown status -->
																		</c:otherwise>
																	</c:choose>
																</div>
															</td>


															<%-- <td class="align-center  "><c:if
																	test="${dto.payoutstatus == 'Declined'}">
																	<button class="declined" id="decline_reason_icon"
																		onclick="openDeclineReasonModal(
                                                                                '${dto.failurereason}',
                                                                                '${txnType}'
                                                                                )"
																		style="border: none; background-color: transparent; cursor: pointer;">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/blureye.svg"
																			width="20" height="20" alt=""
																			style="margin-top: 4.5px;">

																	</button>
																</c:if></td> --%>
															<td class="align-center  "><c:if
																	test="${dto.payoutstatus == 'Declined'}">
																	<button class="declined" id="decline_reason_icon"
																		onclick="openDeclineReasonModal(
                        '${dto.failurereason}',
                        '${txnType}'
                        )"
																		style="border: none; background-color: transparent; cursor: pointer;">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/blureye.svg"
																			width="20" height="20" alt=""
																			style="margin-top: 4.5px;">

																	</button>
																</c:if> <c:if test="${dto.payoutstatus == 'Pending'}">
																	<button class="declined" id="decline_reason_icon"
																		onclick="openPendingModal(
                        'Please check by the end of day',
                        )"
																		style="border: none; background-color: transparent; cursor: pointer;">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/blureye.svg"
																			width="20" height="20" alt=""
																			style="margin-top: 4.5px;">

																	</button>
																</c:if></td>
															<td class="align-center ">
																<button class="" id="more_details_icon"
																	onclick="openPayoutMoredetailsModal(
																		 '${dto.payeeaccnumber}',
                                                                         '${dto.payeebankname}',
                                                                         '${dto.invoiceidproof}',
                                                                         '${dto.payoutamount}',
                                                                         '${dto.payoutstatus}',
                                                                         '${dto.timeStamp}',
                                                                         '${dto.paidTime}',
                                                                         '${dto.paidDate}',
                                                                         '${dto.submerchantMid}',
                                                                         '${dto.mmId}',
                                                                         '${dto.failurereason}',
                                                                         '${dto.payeebrn}',
                                                                         '${dto.createdby}',
                                                                         '${dto.payeename}',
                                                                         '${dto.payoutdate}',
                                                                         '${dto.srcrefno}',
                                                                         '${dto.payouttype}',
                                                                         '${dto.payoutId}',
                                                                         '${dto.paymentReference}'
                                                                            )"
																	style="border: none; background-color: transparent; cursor: pointer;">
																	<img
																		src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/more.svg"
																		width="20" height="20" alt=""
																		style="margin-top: 4.5px;">
																</button>
															</td>
														</tr>
													</c:forEach>


												</c:if>


												<%--                                            <p>:::::${txnType}</p>--%>
												<%--for ewallet--%>

												<c:if test="${txnType == 'Ewallet'}">
													<c:forEach items="${paginationBean.itemList}" var="dto">
														<tr>
															<td>${dto.transactionDate}</td>
															<td>${dto.transactionTime}</td>
															<td>${dto.referanceNo}</td>
															<td>${ewalletPaymentMethod}</td>
															<td style="text-align: right">${dto.transactionAmount}</td>
															<%--                                                        <!--                                                             <td>${dto.status}</td> -->--%>
															<td>
																<div
																	style="display: flex; align-items: center; justify-content: flex-start;">
																	<c:choose>
																		<c:when test="${dto.status eq 'SETTLED'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/paid.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="color: #11bf36; font-weight: 500;">Settled</span>
																		</c:when>
																		<c:when test="${dto.status eq 'NOT SETTLED'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1 color-orange"
																				style="font-weight: 500;">Not Settled</span>
																		</c:when>
																		<c:when test="${dto.status eq 'VOID'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Void.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="color: gray; font-weight: 500;">Voided</span>
																		</c:when>
																		<c:when test="${dto.status eq 'REFUNDED'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Refunded.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="color: #5a87ad; font-weight: 500;">Refunded</span>
																		</c:when>
																		<c:when test="${dto.status eq 'FAILED'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Declined.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="color: red; font-weight: 500;">Failed</span>
																		</c:when>
																		<c:when test="${dto.status eq 'PENDING'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="color: orange; font-weight: 500;">PENDING</span>
																		</c:when>
																		<c:when test="${dto.status eq 'EZYSETTLE'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/ezysettle.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="color: #005BAA; font-weight: 500;">EZYSETTLE</span>
																		</c:when>
																		<c:otherwise>
																			<!-- Handle unknown status -->
																		</c:otherwise>
																	</c:choose>
																</div>
															</td>

															<td class="align-center">
																<%--                                                            <c:if test="${dto.status eq 'FAILED'}">--%>
																<%--&lt;%&ndash;                                                                <button class="declined" id="decline_reason_icon"&ndash;%&gt;--%>
																<%--&lt;%&ndash;                                                                        onclick="openDeclineReasonModal(&ndash;%&gt;--%>
																<%--&lt;%&ndash;                                                                                '${dto.failureReason}',&ndash;%&gt;--%>
																<%--&lt;%&ndash;                                                                                '${txnType}'&ndash;%&gt;--%>
																<%--&lt;%&ndash;                                                                                )"&ndash;%&gt;--%>
																<%--&lt;%&ndash;                                                                        style="border: none;background-color: transparent;cursor: pointer;">&ndash;%&gt;--%>
																<%--&lt;%&ndash;                                                                    <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/blureye.svg"&ndash;%&gt;--%>
																<%--&lt;%&ndash;                                                                         width="20" height="20"&ndash;%&gt;--%>
																<%--&lt;%&ndash;                                                                         alt="" style="margin-top: 4.5px;">&ndash;%&gt;--%>

																<%--&lt;%&ndash;                                                                </button>&ndash;%&gt;--%>
																<%--                                                            </c:if>--%>
															</td>


															<td class="align-center ">


																<button class="" id="more_details_icon"
																	onclick="openEwalletMoredetailsModal(
                                                                            '${dto.transactionDate}',
                                                                            '${dto.transactionTime}',
                                                                            '${dto.transactionID}',
                                                                            '${dto.transactionAmount}',
                                                                            '${ewalletPaymentType}',
                                                                            // 'Touch\'N_Go',
                                                                            '${dto.status}',
                                                                            '${dto.mid}',
                                                                            '${dto.tid}',
                                                                            '${dto.mdrAmount}',
                                                                            '${dto.settlementAmount}',
                                                                            '${dto.settlementDate}',
                                                                            '${dto.rrn}',
                                                                            '${dto.approvalCode}',
                                                                            // slip
                                                                            '${dto.subMerchantMID}',
                                                                            '${dto.referanceNo}',
                                                                            '${dto.timeStamp}',
                                                                            '${dto.merchantName}',
                                                                            '${dto.address}',
                                                                            '${dto.contactNumber}',
                                                                            '${dto.state}'

                                                                            )"
																	style="border: none; background-color: transparent; cursor: pointer;">
																	<img
																		src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/more.svg"
																		width="20" height="20" alt=""
																		style="margin-top: 4.5px;">
																</button>


															</td>
														</tr>
													</c:forEach>
												</c:if>


												<%--                                                for card--%>


												<c:if test="${txnType == 'Card'}">
													<c:forEach items="${paginationBean.itemList}" var="dto">
														<tr>
															<td>${dto.transactionDate}</td>
															<td>${dto.transactionTime}</td>
															<td>${dto.transactionId}</td>
															<td style="text-align: left;">${dto.cardNumber}</td>
															<td style="text-align: right">${dto.transactionAmount}</td>


															<td>
																<div
																	style="display: flex; align-items: center; justify-content: flex-start;">
																	<c:choose>
																		<c:when test="${dto.status eq 'SETTLED'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/paid.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="color: #11bf36; font-weight: 500;">Settled</span>
																		</c:when>
																		<c:when test="${dto.status eq 'NOT SETTLED'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1 color-orange"
																				style="font-weight: 500;">Not Settled</span>
																		</c:when>
																		<c:when test="${dto.status eq 'VOIDED'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Void.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="color: gray; font-weight: 500;">Voided</span>
																		</c:when>
																		<c:when test="${dto.status eq 'REFUNDED'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Refunded.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="color: #5a87ad; font-weight: 500;">Refunded</span>
																		</c:when>
																		<c:when test="${dto.status eq 'PREAUTH'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Pre-auth.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="font-weight: 500; color: #7B68EE">Pre-Auth</span>
																		</c:when>
																		<c:when test="${dto.status eq 'EZYSETTLE'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/ezysettle.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="font-weight: 500; color: #7B68EE">EZYSETTLE</span>
																		</c:when>
																		<c:when test="${dto.status eq 'FAILED'}">
																			<img
																				src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Declined.svg"
																				width="20" height="20" alt="">
																			<span class="fw-500 ml-1"
																				style="color: red; font-weight: 500;">Failed</span>
																		</c:when>
																		<c:otherwise>
																			<!-- Handle unknown status -->
																		</c:otherwise>
																	</c:choose>
																</div>
															</td>


															<td class="align-center  "><c:if
																	test="${dto.status eq 'FAILED'}">
																	<button class="declined" id="decline_reason_icon"
																		onclick="openDeclineReasonModal(
                                                                                '${dto.declinedReason}',
                                                                                '${txnType}'
                                                                                )"
																		style="border: none; background-color: transparent; cursor: pointer;">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/blureye.svg"
																			width="20" height="20" alt=""
																			style="margin-top: 4.5px;">

																	</button>
																</c:if></td>

															<td class="align-center">


																<button class="" id="more_details_icon"
																	onclick="openCardMoredetailsModal(

                                                                            '${dto.transactionDate}',
                                                                            '${dto.transactionTime}',
                                                                            '${dto.transactionId}',
                                                                            '${dto.transactionAmount}',
                                                                            '${dto.paymentMethod}',
                                                                            '${dto.status}',
                                                                            '${dto.cardNumber}',
                                                                            '${dto.cardHolderName}',
                                                                            '${dto.mid}',
                                                                            '${dto.tid}',
                                                                            '${dto.rrn}',
                                                                            '${dto.approvalCode}',
                                                                            '${dto.mdrAmount}',
                                                                            '${dto.netAmount}',
                                                                            '${dto.cardType}',
                                                                            '${dto.merchantName}',
                                                                            '${dto.address}',
                                                                            '${dto.contactNumber}',
                                                                            '${dto.auth3Ds}',
                                                                            '${dto.timestamp}',
                                                                            '${dto.refereceNo}',
                                                                            '${dto.state}',
                                                                            '${dto.settlementDateCard}'
                                                                            )"
																	style="border: none; background-color: transparent; cursor: pointer;">
																	<img
																		src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/more.svg"
																		width="20" height="20" alt=""
																		style="margin-top: 4.5px;">
																</button>

															</td>
														</tr>
													</c:forEach>
												</c:if>


												</tbody>
											</table>
										</div>

										<div colspan="8"
											style="text-align: center; border-bottom: solid; border-bottom-color: white !important; margin-top: 20px">
											<div id="no-data">
												<p></p>
											</div>
										</div>


									</div>
								</div>
							</div>
						</div>

						<!-- table content end -->
					</div>
				</div>
			</div>
		</div>


		<!-- modal for decline reason -->

		<div class="declined_layer modal_container_declinereason"
			id="modalContainer_declinereason">
			<div id="decline-reason" class=" decline-reason-modal-class">
				<div class="modal-header">
					<div class="color-blue fw-500 ">Response</div>
				</div>
				<div class="modal-content content-declinedreason">
					<img
						src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Declined.svg"
						width="40" height=40 " alt="">
					<p class="align-center failure_reason payout_decline_reason"></p>
				</div>
				<div class=" align-center modal-footer footer footer_reason">
					<button id="closedeclined"
						class=" btn waves-effect waves-light blue-btn closebtn"
						type="button" onclick="closeDeclinedReason()" name="action">Close
					</button>
				</div>
			</div>
		</div>



		<div class="declined_layer modal_container_declinereason"
			id="modalContainer_pendingreason">
			<div id="decline-reason" class="decline-reason-modal-class">
				<div class="modal-header">
					<div class="color-blue fw-500 ">Reason</div>
				</div>
				<div class="modal-content content-declinedreason">
					<img
						src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg"
						width="40" height=40 " alt="">
					<p style="color: black; margin-top: 15px; margin-bottom: 15px;"
						class="align-center failure_reason payout_pending_reason"></p>
				</div>
				<div class=" align-center modal-footer footer footer_reason">
					<button id="closedeclined"
						class=" btn waves-effect waves-light blue-btn closebtn"
						type="button" onclick="closePendingReason()" name="action">Close
					</button>
				</div>
			</div>
		</div>


		<!-- view card receipt modal -->
		<div class="slip_layer modal-container_slip"
			id="cardModalContainer_receipt">
			<div id="view_receipt" class=" viewreceipt-modal-class">
				<div class="outer_header" style="padding: 0px 10px;">
					<div class="modal-header header_paymentslip">
						<div class="color-blue fw-600" style="font-size: 18px;">
							Payment Slip
							<button id="closeslip_xmark" onclick="closeCardSlip()"
								style="float: right; margin: 2px; background-color: transparent; border: none;">
								<img
									src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/xmark.svg"
									width="20" height="20" class="cursor-pointer modal-close"
									alt="">
							</button>
						</div>
					</div>
				</div>
				<div class="modal-content content-viewreceipt">


					<div class="slip_outer_border" style="border-radius: 10px;">

						<div class="slip-top_blue_topic shadow-sm radius-10" style="">
							<div class="head_blueback  "
								style="border-top-left-radius: 10px; border-top-right-radius: 10px; color: #fff; background-color: #005baa; padding: 10px 20px;">

								<div class="txn_name_and_mobi"
									style="display: flex; align-items: center; justify-content: space-between;">
									<div id="txn_name" class=""
										style="font-size: 17px; text-wrap: wrap;">EZYWAY SALE</div>
									<div id="mobi_text" class="fw-600"
										style="font-size: 26px; text-wrap: nowrap;">mobi</div>
								</div>

								<div class="amount_date"
									style="display: flex; align-items: center; justify-content: space-between;">
									<div id="amount_top" class="fw-600 card_top_amount"
										style="font-size: 20px; text-wrap: nowrap;"></div>
									<div id="date_top" class="card_top_timestamp"
										style="font-size: 12px; font-weight: 300; text-wrap: nowrap;">

									</div>
								</div>

							</div>


							<div style="padding: 15px;">
								<div class="slip_inner_border watermark" style="">

									<div class="customername_row mb-1">
										<span class="fw-500">Hi </span> <span
											class="fw-500 card_slip_holder"
											style="text-transform: capitalize; padding-left: 3px;">
											,</span>
									</div>

									<div class="merchantname_row mb-1">
										<span class="" style="text-transform: capitalize;"></span> <span>
											You have authorized a payment of </span> <span
											class="color_lightgreen fw-600 card_slip_amount"
											style="text-wrap: nowrap;"></span> <span
											class="card_slip_merchantName"> <span class=""></span>.
										</span>
									</div>

									<div class="payment_description">
										<p class="color-grey fw-500 mb-1"
											style="font-size: 14px !important;">Paid To</p>
										<span class="card_slip_merchantNameSlip"></span><br>

										<div id="card_address_Field">
											<span class="card-slip-address"></span><br
												class="card-slip-country-br"> <span
												class="card-slip-country"></span><br
												class="card-slip-country1-br">
										</div>
										<%--                                    <span class="card_Slip_address"></span><br>--%>
										<span class="card_Slip_contactNumber"></span>


									</div>


									<div class="slip_transaction_details"
										style="line-height: 14px;">
										<p class="color-grey fw-500 mb-1"
											style="font-size: 14px !important; margin-top: 6px;">
											Merchant Information</p>

										<table class="slip_payment_details">
											<thead>

											</thead>
											<tbody>
												<tr>
													<td class="slip_option">Mid</td>
													<td class="colan">:</td>
													<td class="card_slip_mid"></td>
												</tr>
												<tr>
													<td class="slip_option">Tid</td>
													<td class="colan">:</td>
													<td class="card_slip_tid"></td>
												</tr>
											</tbody>
										</table>


									</div>


									<div class="slip_transaction_details">
										<p class="color-grey fw-500 mb-1"
											style="font-size: 14px !important; margin-top: 6px">
											Customer Card Details</p>

										<table class="slip_payment_details" style="line-height: 12px">
											<thead>

											</thead>
											<tbody>
												<tr>
													<td class="slip_option">Card Holder</td>
													<td class="colan">:</td>
													<td class="card_slip_cardHolder"
														style="text-transform: capitalize"></td>
												</tr>
												<tr>
													<td class="slip_option">Card Number</td>
													<td class="colan">:</td>
													<td class="card_slip_cardNumber"></td>
												</tr>
												<tr>
													<td class="slip_option">Card type</td>
													<td class="colan">:</td>
													<td class="card_slip_cardType"></td>
												</tr>
											</tbody>
										</table>


									</div>


									<div class="slip_transaction_details">
										<p class="color-grey fw-500 mb-1"
											style="font-size: 14px !important; margin-top: 5px;">
											Transaction Details</p>

										<table class="slip_payment_details" style="line-height: 13px">
											<thead>

											</thead>
											<tbody>
												<tr>
													<td class="slip_option">RRN</td>
													<td class="colan">:</td>
													<td class="card_slip_rrn"></td>
												</tr>
												<tr>
													<td class="slip_option">Approval Code</td>
													<td class="colan">:</td>
													<td class="card_slip_approvalCode"></td>
												</tr>
												<tr>
													<td class="slip_option">Reference</td>
													<td class="colan">:</td>
													<td class="card_slip_reference"></td>
												</tr>
												<tr>
													<td class="slip_option">3D Secure Authenticated</td>
													<td class="colan">:</td>
													<td class="card_slip_3d_secure_auth"></td>
												</tr>
												<tr>
													<td class="slip_option">Payment Method</td>
													<td class="colan">:</td>
													<td class="card_payment_method"></td>
												</tr>
											</tbody>
										</table>


									</div>

									<div class="contact_info"
										style="padding: 3px 0; font-size: 10px !important;">
										<p class="m-0 color-grey align-center">Contact for any
											questions regarding this payment</p>
										<p class="m-0 color-blue align-center">csmobi@gomobi.io</p>
									</div>

								</div>
							</div>

						</div>

					</div>

				</div>
				<div class=" align-center modal-footer footer footer_slip">
					<button id="closecardslip"
						class="btn waves-effect waves-light blue-btn closebtn"
						type="button" onclick="closeCardSlip()" name="action">Close
					</button>
				</div>
			</div>
		</div>


		<!-- view ewallet receipt modal -->

		<div class="slip_layer modal-container_slip"
			id="ewalletModalContainer_receipt">
			<div id="view_receipt" class=" viewreceipt-modal-class">
				<div class="outer_header ewallet_Scroll_div"
					style="padding: 0px 10px;">
					<div class="modal-header header_paymentslip">
						<div class="color-blue fw-600" style="font-size: 18px;">
							Payment Slip
							<button id="closeslip_xmark" onclick="closeEwalletSlip()"
								style="float: right; margin: 2px; background-color: transparent; border: none;">
								<img
									src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/xmark.svg"
									width="20" height="20" class="cursor-pointer modal-close"
									alt="">
							</button>
						</div>


					</div>
				</div>
				<div class="modal-content content-viewreceipt">


					<div class="slip_outer_border" style="border-radius: 10px;">

						<div class="slip-top_blue_topic shadow-sm radius-10" style="">
							<div class="head_blueback  "
								style="border-top-left-radius: 10px; border-top-right-radius: 10px; color: #fff; background-color: #005baa; padding: 10px 20px;">

								<div class="txn_name_and_mobi"
									style="display: flex; align-items: center; justify-content: space-between;">
									<div id="txn_name" class="ewallet_slip_top_type"
										style="font-size: 17px; text-wrap: wrap;"></div>
									<div id="mobi_text" class="fw-600"
										style="font-size: 26px; text-wrap: nowrap;">mobi</div>
								</div>

								<div class="amount_date"
									style="display: flex; align-items: center; justify-content: space-between;">
									<div id="amount_top" class="fw-600 ewallet-slip-amount"
										style="font-size: 20px; text-wrap: nowrap;"></div>
									<div id="date_top" class="ewallet_slip_timestamp"
										style="font-size: 12px; font-weight: 300; text-wrap: nowrap;">

									</div>
								</div>

							</div>


							<div style="padding: 15px;">
								<div class="slip_inner_border watermark" style="">

									<div class="customername_row mb-1">
										<span class="fw-500">Hi ,</span> <span class="fw-500"
											style="text-transform: capitalize; padding-left: 3px;">

										</span>
									</div>

									<div class="merchantname_row mb-1">
										<%--                                    <span class="ewallet_slip_merchant_name" style="text-transform: uppercase;"></span>--%>
										<span> You have authorized a payment of </span> <span
											class="color_lightgreen fw-600 ewallet_slip_payment_text"
											style="text-wrap: nowrap;"></span> <span>to</span> <span
											class="ewallet_slip_merchantNameName"
											style="text-transform: capitalize"></span> <span> <%--                                          <span class="ewallet_slip_merchant_date"></span>--%>
											.
										</span>
									</div>

									<div class="payment_description">
										<p class="color-grey fw-500 mb-1"
											style="font-size: 14px !important;">Paid To</p>
										<span class=""></span> <span
											class="ewallet_slip_merchantName_desc"
											style="text-transform: capitalize;"></span><br>

										<%--                                    <span class="ewallet_slip_address"></span><br>--%>
										<div id="ewallet_address_Field">
											<span class="ewallet-slip-address"></span><br
												class="ewallet-slip-country-br"> <span
												class="ewallet-slip-country"></span><br
												class="ewallet-slip-country1-br">
										</div>
										<span class="ewallet_slip_phone"></span>

									</div>


									<div class="slip_transaction_details">
										<p class="color-grey fw-500 mb-1"
											style="font-size: 14px !important; margin-top: 3px">
											Merchant Information</p>

										<table class="slip_payment_details">
											<thead>

											</thead>
											<tbody>
												<tr>
													<td class="slip_option">Mid</td>
													<td class="colan">:</td>
													<td class="ewallet_slip_mid"></td>
												</tr>
												<tr>
													<td class="slip_option">Tid</td>
													<td class="colan">:</td>
													<td class="ewallet_slip_tid"></td>
												</tr>
											</tbody>
										</table>


									</div>


									<div class="slip_transaction_details">
										<p class="color-grey fw-500 mb-1"
											style="font-size: 14px !important;">Transaction Details</p>

										<table class="slip_payment_details">
											<thead>

											</thead>
											<tbody>
												<tr id="rrn_for_Boost_Grab" style="display: none">
													<td class="slip_option">RRN</td>
													<td class="colan">:</td>
													<td class="ewallet_slip_rrn"></td>
												</tr>
												<tr>
													<td class="slip_option">Approval Code</td>
													<td class="colan">:</td>
													<td class="ewallet_slip_approvalCode"></td>
												</tr>
												<tr>
													<td class="slip_option">Reference</td>
													<td class="colan">:</td>
													<td class="ewallet_slip_reference"></td>
												</tr>
												<tr>
													<td class="slip_option">Payment Method</td>
													<td class="colan">:</td>
													<td class="ewallet_paymentMethod_slip"></td>
												</tr>
											</tbody>
										</table>


									</div>

									<div class="contact_info"
										style="padding: 3px 0; font-size: 10px !important;">
										<p class="m-0 color-grey align-center">Contact for any
											questions regarding this payment</p>
										<p class="m-0 color-blue align-center">csmobi@gomobi.io</p>
									</div>

								</div>
							</div>

						</div>

					</div>

				</div>
				<div class=" align-center modal-footer footer footer_slip">
					<button id="closeewalletslip"
						class="btn waves-effect waves-light blue-btn closebtn"
						type="button" onclick="closeEwalletSlip()" name="action">Close
					</button>
				</div>
			</div>
		</div>


		<!--         <div class="slip_layer modal-container_slip" id="ewalletModalContainer_receipt"> -->
		<!--             <div id="view_receipt" class=" viewreceipt-modal-class"> -->
		<!--                 <div class="outer_header" style="padding: 0px 10px;"> -->
		<!--                     <div class="modal-header header_paymentslip"> -->
		<!--                         <div class="color-blue fw-600" style="font-size: 18px ;">Payment Slip <button id ="closeslip_xmark"  onclick="closeEwalletSlip()" -->
		<!--                                 style="float: right; margin: 2px; background-color: transparent; border: none;"><img src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/xmark.svg" width="20" height="20" -->
		<!--                                     class="cursor-pointer modal-close" alt=""></button></div> -->


		<!--                     </div> -->
		<!--                 </div> -->
		<!--                 <div class="modal-content content-viewreceipt"> -->


		<!--                     <div class="slip_outer_border" style="border-radius: 10px;"> -->

		<!--                         <div class="slip-top_blue_topic shadow-sm radius-10" style=""> -->
		<!--                             <div class="head_blueback  " -->
		<!--                                 style="border-top-left-radius: 10px; border-top-right-radius: 10px;color : #fff;background-color: #005baa; padding: 10px 20px;"> -->

		<!--                                 <div class="txn_name_and_mobi" -->
		<!--                                     style="display: flex;align-items:center;justify-content: space-between;"> -->
		<!--                                     <div id="txn_name" class="" style="font-size: 17px;text-wrap: wrap;">Payout -->
		<!--                                         Transaction</div> -->
		<!--                                     <div id="mobi_text" class="fw-600" style="font-size: 26px;text-wrap: nowrap; ">mobi -->
		<!--                                     </div> -->
		<!--                                 </div> -->

		<!--                                 <div class="amount_date" -->
		<!--                                     style="display: flex;align-items:center;justify-content: space-between;"> -->
		<!--                                     <div id="amount_top" class="fw-600" style="font-size: 20px;text-wrap: nowrap;">RM -->
		<!--                                         45.34</div> -->
		<!--                                     <div id="date_top" class="" -->
		<!--                                         style="font-size: 12px; font-weight: 300;text-wrap: nowrap;">29-Feb-2024, -->
		<!--                                         <span>11:03:34</span> -->
		<!--                                     </div> -->
		<!--                                 </div> -->

		<!--                             </div> -->


		<!--                             <div style="padding: 15px;"> -->
		<!--                                 <div class="slip_inner_border watermark" style=""> -->

		<!--                                     <div class="customername_row mb-1"> -->
		<!--                                         <span class="fw-500">Hi</span><span class="fw-500" -->
		<!--                                             style="text-transform: capitalize; padding-left: 7px;">MOHAMMAD SHAIFUL BIN -->
		<!--                                             ZAIMI,</span> -->
		<!--                                     </div> -->

		<!--                                     <div class="merchantname_row mb-1"> -->
		<!--                                         <span class="" style="text-transform: uppercase;">VALETAX INTERNATIONAL</span> -->
		<!--                                         <span> -->
		<!--                                             has -->
		<!--                                             made payment of </span><span class="color_lightgreen fw-600" -->
		<!--                                             style="text-wrap: nowrap;">RM 45.31</span><span> to you on -->
		<!--                                             <span>29/02/2024</span>.</span> -->
		<!--                                     </div> -->

		<!--                                     <div class="payment_description"> -->
		<!--                                         <p class="color-grey fw-500 mb-1" style="font-size: 14px !important;">Payment -->
		<!--                                             Description -->
		<!--                                         </p> -->
		<!--                                         <span class="">Payment to </span><span class="" -->
		<!--                                             style="text-transform: uppercase;">MOHAMMAD -->
		<!--                                             SHAIFUL BIN ZAIMI</span> -->

		<!--                                     </div> -->


		<!--                                     <div class="slip_transaction_details"> -->
		<!--                                         <p class="color-grey fw-500 mb-1" style="font-size: 14px !important;"> -->
		<!--                                             Transaction -->
		<!--                                             Details -->
		<!--                                         </p> -->

		<!--                                         <table class="slip_payment_details"> -->
		<!--                                             <thead> -->

		<!--                                             </thead> -->
		<!--                                             <tbody> -->
		<!--                                                 <tr> -->
		<!--                                                     <td class="slip_option">SRC Ref No</td> -->
		<!--                                                     <td class="colan">:</td> -->
		<!--                                                     <td>BULKPAY_12345009789040</td> -->
		<!--                                                 </tr> -->
		<!--                                                 <tr> -->
		<!--                                                     <td class="slip_option">Invoice Id</td> -->
		<!--                                                     <td class="colan">:</td> -->
		<!--                                                     <td>PO_2402290211521782</td> -->
		<!--                                                 </tr> -->
		<!--                                                 <tr> -->
		<!--                                                     <td class="slip_option">Status</td> -->
		<!--                                                     <td class="colan">:</td> -->
		<!--                                                     <td>Paid</td> -->
		<!--                                                 </tr> -->
		<!--                                                 <tr> -->
		<!--                                                     <td class="slip_option">Payout Type</td> -->
		<!--                                                     <td class="colan">:</td> -->
		<!--                                                     <td>Normal</td> -->
		<!--                                                 </tr> -->
		<!--                                                 <tr> -->
		<!--                                                     <td class="slip_option">Payment Method</td> -->
		<!--                                                     <td class="colan">:</td> -->
		<!--                                                     <td> -->
		<!--                                                         <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Payout.svg" width="25" height="25" alt=""> -->
		<!--                                                     </td> -->
		<!--                                                 </tr> -->
		<!--                                             </tbody> -->
		<!--                                         </table> -->


		<!--                                     </div> -->

		<!--                                     <div class="contact_info" style="padding: 3px 0;font-size: 10px !important; "> -->
		<!--                                         <p class="m-0 color-grey align-center">Contact for any questions regarding this -->
		<!--                                             payment -->
		<!--                                         </p> -->
		<!--                                         <p class="m-0 color-blue align-center">csmobi@gomobi.io</p> -->
		<!--                                     </div> -->

		<!--                                 </div> -->
		<!--                             </div> -->

		<!--                         </div> -->

		<!--                     </div> -->

		<!--                 </div> -->
		<!--                 <div class=" align-center modal-footer footer footer_slip"> -->
		<!--                     <button id="closeewalletslip" class="btn waves-effect waves-light blue-btn closebtn" type="button" onclick="closeEwalletSlip()" -->
		<!--                         name="action">Close</button> -->
		<!--                 </div> -->
		<!--             </div> -->
		<!--         </div> -->

		<!-- view fpx receipt modal -->
		<div class="slip_layer modal-container_slip"
			id="fpxModalContainer_receipt">
			<div id="view_receipt" class=" viewreceipt-modal-class">
				<div class="outer_header fpx_Scroll_div" style="padding: 0px 10px;">
					<div class="modal-header header_paymentslip">
						<div class="color-blue fw-600" style="font-size: 18px;">
							Payment Slip
							<button id="closeslip_xmark" onclick="closeFpxSlip()"
								style="float: right; margin: 2px; background-color: transparent; border: none;">
								<img
									src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/xmark.svg"
									width="20" height="20" class="cursor-pointer modal-close"
									alt="">
							</button>
						</div>
					</div>
				</div>
				<div class="modal-content content-viewreceipt">


					<div id="hiddenContainer" style="display: none;"></div>


					<div class="slip_outer_border" style="border-radius: 10px;">

						<div class="slip-top_blue_topic shadow-sm radius-10" style="">
							<div class="head_blueback  "
								style="border-top-left-radius: 10px; border-top-right-radius: 10px; color: #fff; background-color: #005baa; padding: 10px 20px;">

								<div class="txn_name_and_mobi"
									style="display: flex; align-items: center; justify-content: space-between;">
									<div id="txn_name" class=""
										style="font-size: 17px; text-wrap: wrap;">FPX Slip</div>
									<div id="mobi_text" class="fw-600"
										style="font-size: 26px; text-wrap: nowrap;">mobi</div>
								</div>

								<div class="amount_date"
									style="display: flex; align-items: center; justify-content: space-between;">
									<div id="amount_top" class="fw-600 fpx-slip-amt"
										style="font-size: 20px; text-wrap: nowrap;"></div>
									<div id="date_top" class="timestamp-value"
										style="font-size: 12px; font-weight: 300; text-wrap: nowrap;">
										<%--                                        29-Feb-2024,--%>
										<span> <%--                                            11:03:34--%>
										</span>
									</div>
								</div>

							</div>


							<div style="padding: 15px;">
								<div class="slip_inner_border watermark" style="">

									<div class="customername_row mb-1">
										<span class="fw-500">Hi</span> <span
											class="fw-500 fpx-slip-buyerName"
											style="text-transform: capitalize; padding-left: 2px;">


										</span>
									</div>

									<div class="merchantname_row mb-1">
										<span class="" style="text-transform: capitalize;">${merchantName}</span>
										<span> has made payment of </span> <span
											class="color_lightgreen fw-600 fpx-slip-amt-below"
											style="text-wrap: nowrap;"></span> <span> to you on <span
											class="settledDate-below"></span>.
										</span>
									</div>

									<div class="payment_description">
										<p class="color-grey fw-500 mb-1"
											style="font-size: 14px !important;">Paid To</p>
										<span class=""></span> <span class="fpx-slip-merchantName"
											style="text-transform: capitalize;"></span><br>

										<div id="fpx_address_Field">
											<span class="fpx-slip-address"></span><br
												class="fpx-slip-country-br"> <span
												class="fpx-slip-country"></span><br
												class="fpx-slip-country1-br">
										</div>

										<span class="fpx-slip-phone"></span>


									</div>


									<div class="slip_transaction_details">
										<p class="color-grey fw-500 mb-1"
											style="font-size: 14px !important;">Merchant Informatiom
										</p>

										<table class="slip_payment_details">
											<thead>

											</thead>
											<tbody>
												<tr>
													<td class="slip_option">MID</td>
													<td class="colan">:</td>
													<td class="src-ref-no"></td>
												</tr>
												<tr>
													<td class="slip_option">TID</td>
													<td class="colan">:</td>
													<td class="ppid-no"></td>
												</tr>
											</tbody>
										</table>


									</div>


									<div class="slip_transaction_details">
										<p class="color-grey fw-500 mb-1"
											style="font-size: 14px !important;">Transaction Details</p>

										<table class="slip_payment_details">
											<thead>

											</thead>
											<tbody>
												<tr>
													<td class="slip_option">Bank Name</td>
													<td class="colan">:</td>
													<td class="fpx-slip-bankName"></td>
												</tr>
												<tr>
													<td class="slip_option">FPX Transaction ID</td>
													<td class="colan">:</td>
													<td class="fpx-slip-transactionId"></td>
												</tr>
												<tr>
													<td class="slip_option">Reference</td>
													<td class="colan">:</td>
													<td class="fpx-slip-reference"></td>
												</tr>
												<tr>
													<td class="slip_option">Payment Method</td>
													<td class="colan">:</td>
													<td><img
														src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Fpx.svg"
														width="70" height="30" alt=""></td>
												</tr>
											</tbody>
										</table>


									</div>

									<div class="contact_info"
										style="padding: 3px 0; font-size: 10px !important;">
										<p class="m-0 color-grey align-center">Contact for any
											questions regarding this payment</p>
										<p class="m-0 color-blue align-center">csmobi@gomobi.io</p>
									</div>

								</div>
							</div>

						</div>
					</div>
				</div>
				<div class=" align-center modal-footer footer footer_slip">
					<button id="closefpxslip"
						class="btn waves-effect waves-light blue-btn closebtn"
						type="button" onclick="closeFpxSlip()" name="action">Close
					</button>
				</div>
			</div>
		</div>


		<!-- view payout receipt modal -->
		<%-- <div class="slip_layer modal-container_slip"
			id="payoutModalContainer_receipt">
			<div id="view_receipt" class=" viewreceipt-modal-class">
				<div class="outer_header payout_Scroll_div"
					style="padding: 0px 10px;">
					<div class="modal-header header_paymentslip">
						<div class="color-blue fw-600" style="font-size: 18px;">
							Payment Slip
							<button id="closeslip_xmark" onclick="closePayoutSlip()"
								style="float: right; margin: 2px; background-color: transparent; border: none;">
								<img
									src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/xmark.svg"
									width="20" height="20" class="cursor-pointer modal-close"
									alt="">
							</button>
						</div>
					</div>
				</div>
				<div class="modal-content content-viewreceipt">


					<div class="slip_outer_border" style="border-radius: 10px;">

						<div class="slip-top_blue_topic shadow-sm radius-10" style="">
							<div class="head_blueback  "
								style="border-top-left-radius: 10px; border-top-right-radius: 10px; color: #fff; background-color: #005baa; padding: 10px 20px;">

								<div class="txn_name_and_mobi"
									style="display: flex; align-items: center; justify-content: space-between;">
									<div id="txn_name" class=""
										style="font-size: 17px; text-wrap: wrap;">Payout
										Transaction</div>
									<div id="mobi_text" class="fw-600"
										style="font-size: 26px; text-wrap: nowrap;">mobi</div>
								</div>

								<div class="amount_date"
									style="display: flex; align-items: center; justify-content: space-between;">
									<div id="payout-payoutAmountRM-slip-value" class="fw-600"
										style="font-size: 20px; text-wrap: nowrap;"></div>
									<div id="payout-formattedDate-slip-value" class=""
										style="font-size: 12px; font-weight: 300; text-wrap: nowrap;"></div>
								</div>

							</div>


							<div style="padding: 15px;">
								<div class="slip_inner_border watermark" style="">

									<div class="customername_row mb-1">
										<span class="fw-500">Hi ,</span> <span class="fw-500"
											id="payout-payeename-slip-value"
											style="text-transform: capitalize;">,</span>
									</div>

									<div class="merchantname_row mb-1">
										<span class="" style="text-transform: capitalize;"
											id="payout-merchantName-slip-value"></span> <span> has
											made payment of </span><span id="payout-payoutAmount-slip-value"
											class="color_lightgreen fw-600" style="text-wrap: nowrap;"></span><span>
											to you on <span id="payout-payoutDate-slip-value"></span>.
										</span>
									</div>

									<div class="payment_description">
										<p class="color-grey fw-500 mb-1"
											style="font-size: 14px !important;">Payment Description</p>
										<span class="">Payment to </span><span class=""
											style="text-transform: capitalize;"
											id="payout-payeenameForDesc-slip-value"></span>

									</div>


									<div class="slip_transaction_details">
										<p class="color-grey fw-500 mb-1"
											style="font-size: 14px !important;">Transaction Details</p>

										<table class="slip_payment_details">
											<thead>

											</thead>
											<tbody>
												<tr>
													<td class="slip_option">SRC Ref No</td>
													<td class="colan">:</td>
													<td id="payout-srcRefNo-slip-value"></td>
												</tr>
												<tr>
													<td class="slip_option">Invoice Id</td>
													<td class="colan">:</td>
													<td id="payout-invoiceIdProof-slip-value"></td>
												</tr>
												<tr>
													<td class="slip_option">Status</td>
													<td class="colan">:</td>
													<td id="payout-payoutStatus-slip-value"></td>
												</tr>
												<tr>
													<td class="slip_option">Payout Type</td>
													<td class="colan">:</td>
													<td id="payout-payoutType-slip-value"></td>
												</tr>
												<tr>
													<td class="slip_option">Payment Method</td>
													<td class="colan">:</td>
													<td><img
														src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Payout.svg"
														width="25" height="25" alt=""></td>
												</tr>
											</tbody>
										</table>


									</div>

									<div class="contact_info"
										style="padding: 3px 0; font-size: 10px !important;">
										<p class="m-0 color-grey align-center">Contact for any
											questions regarding this payment</p>
										<p class="m-0 color-blue align-center">csmobi@gomobi.io</p>
									</div>

								</div>
							</div>

						</div>

					</div>

				</div>
				<div class=" align-center modal-footer footer footer_slip">
					<button id="closepayoutslip"
						class="btn waves-effect waves-light blue-btn closebtn"
						type="button" onclick="closePayoutSlip()" name="action">Close
					</button>
				</div>
			</div>
		</div>
 --%>

<!-- new design for payslip payout -->

		<div id="payoutModalContainer_receipt"
			class="slip_layer modal-container_slip" style="z-index: 99">
			<section class="payout-slip-main-container poppins-regular" id="closeClickOnOutside">
				<div class="slip-card" id="slip-card-id">
					<div class="outer_header payout_Scroll_div"
						style="padding: 0px 10px;">
						<div class="title-logo" style="position: relative">
							<%--       <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mobi_logo.svg" width="55" height="55"/> --%>
							<h3
								style="font-family: 'Poppins', sans-serif; color: #005baa; font-weight: 800; font-size: 25px !important; margin-bottom: 0 !important;">
								mobi</h3>
							<%--  <img style="position: absolute; top: 3px; right: 10px; height: 20px !important; width: 20px !important; cursor: pointer;" 
                         src="${pageContext.request.contextPath}/resourcesNew1/assets/xmark.svg" 
                         width="35" height="35" onclick="closeXpayModal()"/> --%>

							<img id="cancel-download"
								style="position: absolute; top: 3px; right: 10px; height: 20px ! important; width: 20px !important; cursor: pointer;"
								src="${pageContext.request.contextPath}/resourcesNew1/assets/xmark.svg"
								width="35" height="35" onclick="closeXpayModal()" /> <img
								id="active-download"
								style="position: absolute; top: 3px; right: 40px; height: 20px !important; width: 20px !important; cursor: pointer;"
								src="${pageContext.request.contextPath}/resourcesNew1/assets/download.svg"
								width="35" height="35" onclick="downloadPayoutSlip()">
						</div>

						<hr class="horizontal-line">

						<div class="main-status poppins-semibold">
							<p class="status status-success">Successful</p>
							<div class="status-container">
								<p class="sub-head">Transaction Summary</p>
								<p class="amount poppins-regular">
									 <span class="poppins-semibold amount-value"
										id="new_slip_amount"></span>
								</p>
								<p class="time-stamp poppins-semibold" id="">
								 <span class="poppins-semibold amount-value"
										id="paidDate"></span>
										 <span class="poppins-semibold amount-value"
										id="paidTime"></span>
								</p>
								<hr class="horizontal-default">
							</div>
						</div>

						<div class="transaction-details">
							<table>
								<tr class="no_border_bottom">
									<th class="poppins-regular xpay_slip_whiteSpace">From</th>
									<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak"
										style="text-transform: uppercase;" id="new_slip_merchant"></td>
								</tr>
								<tr class="no_border_bottom">
									<th class="poppins-regular xpay_slip_whiteSpace">To</th>
									<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak"
										style="text-transform: uppercase;" id="new_slip_recipient"></td>
								</tr>
								<tr class="no_border_bottom">
									<th class="poppins-regular xpay_slip_whiteSpace">Reference
										No</th>
									<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak"
										id="new_slip_payoutId"></td>
								</tr>
								
								<tr class="no_border_bottom">
									<th class="poppins-regular xpay_slip_whiteSpace">Transaction
										ID</th>
									<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak"
										id="new_slip_invoiceidproff"></td>
								</tr>

								<tr class="no_border_bottom">
									<th class="poppins-regular xpay_slip_whiteSpace">Payment
										Type</th>
									<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak"
										id="new_slip_paymentType"></td>
								</tr>
				
								<tr class="no_border_bottom">
									<th class="poppins-regular xpay_slip_whiteSpace">Payment
										Method</th>
									<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak">Payout</td>

								<tr class="no_border_bottom">
									<th class="poppins-regular xpay_slip_whiteSpace">Remarks</th>
									<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak"
										id="new_slip_paymentReference"></td>
								</tr>
							</table>
							<div class="bill-box-container">
								<div class="poppins-medium">Transfer Amount</div>
								<div class="poppins-semibold"
									style="font-size: 1rem; color: var(--value-color);"
									id="new_slip_transferAmt"></div>
							</div>
						</div>
						<hr class="horizontal-default">
						<div class="notes-section">
							<strong>Note</strong>
						<p class="notes" style= "font-size: 11px !important">
							This receipt is computer generated and no signature is required.
							For support, contact  <a href="mailto:csmobi@gomobi.io" style="color: #005BAA; text-decoration: underline; ">csmobi@gomobi.io</a>
						</p>

						</div>
					</div>
				</div>
			</section>
		</div>


		<style>
@import
	url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap')
	;

* {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
}

:root {
	--success-title: #36B37E;
	--failure-title: #EC3E3E;
	--card-bg: #F6F6F6;
	--hr-line: #eeeeee;
	--title-color: #333739;
	--value-color: #2D2D2D;
	--bill-box: #ECECEC;
}

.no_border_bottom {
	border-bottom: none !important;
}

/* FONT STYLE CSS  */
.poppins-light {
	font-family: "Poppins", sans-serif;
	font-weight: 300;
}

.poppins-regular {
	font-family: "Poppins", sans-serif;
	font-weight: 400;
}

.xpay_slip_wordBreak {
	word-break: break-all !important;
}

.xpay_slip_whiteSpace {
	white-space: nowrap !important;
	vertical-align: baseline !important;
}

.poppins-medium {
	font-family: "Poppins", sans-serif;
	font-weight: 500;
}

.poppins-semibold {
	font-family: "Poppins", sans-serif;
	font-weight: 600;
}

.poppins-bold {
	font-family: "Poppins", sans-serif;
	font-weight: 700;
}

/* Payout Slip Container main box  */
.payout-slip-main-container {
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
}

.slip-card {
	width: 100%;
	max-width: 24rem;
	background-color: var(--card-bg);
	border-radius: 0.5rem;
	overflow: hidden;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.title-logo {
	margin: 0.5rem auto 0.5rem auto;
	text-align: center;
}

.title-logo img {
	max-width: 100%;
	height: 1.625rem;
}

.horizontal-line {
	height: 0.3rem;
	background-color: var(--hr-line);
	border: none;
	margin: 0 auto 1rem auto;
}

.horizontal-default {
	width: 90%;
	height: 1px;
	background-color: var(--hr-line);
	color: var(--hr-line);
	margin: 0.5rem auto 0 auto;
	opacity: 0.5;
}

.main-status {
	text-align: center;
	color: var(--value-color);
}

.status {
	font-size: 1.3rem;
	margin-top: 0.25rem;
}

.status-success {
	color: var(--success-title);
}

.status-failure {
	color: var(--failure-title);
}

.status-container {
	padding: 0 0.5rem;
}

.sub-head {
	font-size: 0.7rem;
	margin-top: 0.75rem;
}

.amount {
	font-size: 1.4rem;
	color: var(--title-color);
}

.amount-value {
	color: var(--value-color);
}

.time-stamp {
	font-size: 0.7rem;
	margin-top: 0.1rem;
}

.transaction-details {
	padding: 0 1.25rem;
}

.transaction-details table {
	width: 100%;
	border-collapse: collapse;
}

.transaction-details th {
	color: var(--title-color);
}

.transaction-details th, .transaction-details td {
	padding: 0.85rem 0.3rem;
	text-align: left;
	font-size: 0.8rem;
}

.transaction-details td {
	text-align: right;
	text-wrap: wrap;
}

.bill-box-container {
	background-color: var(--bill-box);
	border-radius: 10px;
	font-size: 0.8rem;
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 0.7rem 1.5rem;
	margin-top: 0.5rem;
	color: var(--title-color);
}

.notes-section {
	padding: 0.5rem 1.5rem;
	font-size: 0.8rem;
	color: var(--title-color);
	margin-bottom: 0.5rem;
}

.notes {
	font-size: 0.75rem;
}

/* Media Queries for Responsive Design */
@media ( max-width : 768px) {
	.slip-card {
		padding: 0;
	}
	.status {
		font-size: 1.1rem;
	}
	.amount {
		font-size: 1.2rem;
	}
}

@media ( max-width : 480px) {
	.slip-card {
		padding: 0;
	}
	.status {
		font-size: 1rem;
	}
	.amount {
		font-size: 1rem;
	}
}
</style>

<!-- new design for payslip payout -->

		<!-- modal for card transaction details -->
		<div class="more_info modal-container" id="modalContainerCard">
			<div id="moredetails" class="moredetails-modal-class">
				<div class="outerborder" style="padding: 10px;">

					<div class="modal-header header_txndetails">
						<div class="color-blue fw-600" style="font-size: 18px;">
							More Details
							<button id="closecardmore_xmark" onclick="closeCard()"
								style="float: right; margin: 2px; background-color: transparent; border: none;">
								<img
									src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/xmark.svg"
									width="20" height="20" class="cursor-pointer " alt="">
							</button>
						</div>
					</div>
					<div id="table-content"></div>
					<div class="modal-content content-moredetails">
						<div class="color-blue fw-600 mt-1" style="font-size: 17px;">Transaction
							Details</div>
						<div class="txn-table">
							<table class="txn-popuptable">
								<thead>
									<tr>
										<!-- <th class="color-blue fw-600 " colspan="3">Transaction Details</th> -->
									</tr>
								</thead>

								<tbody class="txn-details-table">
									<tr>
										<td class="data_option">Date</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_date"></td>
									</tr>

									<tr>
										<td class="data_option">Time</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_time"></td>
									</tr>

									<tr>
										<td class="data_option">Transaction ID</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_txnId"></td>
									</tr>

									<tr>
										<td class="data_option">Amount (RM)</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_amount"></td>
									</tr>
									<tr>
										<td class="data_option">Payment Method</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_paymentMethod"></td>
									</tr>
									<tr>
										<td class="data_option">Transaction Status</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_status">
											<%--                                    <div style="display: flex;align-items: center;justify-content: flex-start;">--%>
											<%--                                        <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg" width="20" height="20" alt=""><span--%>
											<%--                                            class="color-orange fw-500 ml-1">Not Settled</span>--%>
											<%--                                    </div>--%>

										</td>
									</tr>

									<tr>
										<td class="data_option">Card Number</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_cardNumber"></td>
									</tr>

									<tr>
										<td class="data_option">Name On Card</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_cardHolder"
											style="text-transform: capitalize !important";></td>
									</tr>


									<tr>
										<td class="data_option">MID</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_mid"></td>
									</tr>


									<tr>
										<td class="data_option">TID</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_tid"></td>
									</tr>

									<tr>
										<td class="data_option">RRN</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_rrn"></td>
									</tr>

									<tr>
										<td class="data_option">Approval Code</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_approvalCode"></td>
									</tr>


									<tr>
										<td class="data_option">MDR Amount</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_mdrAmount"></td>
									</tr>


									<tr>
										<td class="data_option">Net Amount</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_netAmount"></td>
									</tr>


									<tr>
										<td class="data_option">Payment Date</td>
										<td class="hyphen">-</td>
										<td class="data_value card_modal_paymentDate"></td>
									</tr>


									<tr id="card_slip_card_card">
										<td class="data_option">Receipt</td>
										<td class="hyphen">-</td>
										<td class="data_value cursor-pointer receipt">

											<button type="button" id="card_slip_id"
												class="cursor-pointer" onclick="openCardSlip()"
												style="display: flex; align-items: center; justify-content: flex-start; padding: 0; background-color: transparent; border: none;">

												<img
													src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/slip.svg"
													width="20" height="20" alt=""><span
													class="color-skyblue fw-500 ml-1">View Receipt</span>

											</button>


										</td>
									</tr>

									<!-- <tr
                                style="display: flex;align-items : flex-start !important ;justify-content: flex-start !important;">
                                <td class="data_option">Response Message</td>
                                <td class="hyphen">
                                    -
                                </td>
                                <td class="data_value">Buyer Failed To Provide Necessary Info At Account
                                    Selection
                                    Page</td>
                            </tr> -->


								</tbody>
							</table>
						</div>


					</div>


				</div>
				<div class=" align-center modal-footer footer  moredetails_footer">
					<button id="closecard"
						class=" btn waves-effect waves-light blue-btn closebtn"
						type="button" onclick="closeCard()" name="action">Close</button>
				</div>
			</div>


		</div>


		<!-- modal for ewallet transaction details -->
		<div class="more_info modal-container" id="modalContainerEwallet">
			<div id="moredetails" class="moredetails-modal-class">
				<div class="outerborder" style="padding: 10px;">

					<div class="modal-header header_txndetails">
						<div class="color-blue fw-600" style="font-size: 18px;">
							More Details
							<button id="closeewalletmore_xmark" onclick="closeEwallet()"
								style="float: right; margin: 2px; background-color: transparent; border: none;">
								<img
									src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/xmark.svg"
									width="20" height="20" class="cursor-pointer " alt="">
							</button>
						</div>
					</div>
					<div class="modal-content content-moredetails">
						<div class="color-blue fw-600 mt-1" style="font-size: 17px;">Transaction
							Details</div>
						<div class="txn-table">
							<table class="txn-popuptable">
								<thead>
									<tr>
										<!-- <th class="color-blue fw-600 " colspan="3">Transaction Details</th> -->
									</tr>
								</thead>

								<tbody class="txn-details-table">
									<tr>
										<td class="data_option">Date</td>
										<td class="hyphen">-</td>
										<td class="data_value ewallet_date_more">
											<!--                                             06/02/2024 -->
										</td>
									</tr>

									<tr>
										<td class="data_option">Time</td>
										<td class="hyphen">-</td>
										<td class="data_value ewallet_time_more">
											<!--                                         10:23:46 -->
										</td>
									</tr>

									<tr>
										<td class="data_option">Transaction ID</td>
										<td class="hyphen">-</td>
										<td class="data_value ewallet_txn_more">
											<!--                                         501754244340934_MNTX -->
										</td>
									</tr>

									<tr>
										<td class="data_option">Amount (RM)</td>
										<td class="hyphen">-</td>
										<td class="data_value ewallet_amt_more">
											<!--                                         8,749.04 -->
										</td>
									</tr>
									<tr>
										<td class="data_option">Payment Method</td>
										<td class="hyphen">-</td>
										<td class="data_value ewallet_paymentMethod_more">
											<!--                                         Boost -->
										</td>
									</tr>
									<tr>
										<td class="data_option">Transaction Status</td>
										<td class="hyphen">-</td>
										<td
											class="data_value ewallet_status_more ewallet_modal_status">

											<%--                                    <div style="display: flex;align-items: center;justify-content: flex-start;">--%>
											<%--                                        <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg"--%>
											<%--                                             width="20" height="20" alt=""><span--%>
											<%--                                            class="color-orange fw-500 ml-1">Not Settled</span>--%>
											<%--                                    </div>--%>

										</td>
									</tr>

									<tr>
										<td class="data_option">MID</td>
										<td class="hyphen">-</td>
										<td class="data_value ewallet_mid_more">
											<!--                                             000000000038605 -->
										</td>
									</tr>


									<tr>
										<td class="data_option">TID</td>
										<td class="hyphen">-</td>
										<td class="data_value ewallet_tid_more">
											<!--                                             10009291 -->
										</td>
									</tr>


									<tr>
										<td class="data_option">MDR Amount</td>
										<td class="hyphen">-</td>
										<td class="data_value ewallet_mdrAmt_more">
											<!--                                             7.16 -->
										</td>
									</tr>


									<tr>
										<td class="data_option">Settlement Amount</td>
										<td class="hyphen">-</td>
										<td class="data_value ewallet_settlementAmt_more">
											<!--                                             8,100.10 -->
										</td>
									</tr>


									<tr>
										<td class="data_option">Payment Date</td>
										<td class="hyphen">-</td>
										<td class="data_value ewallet_paymentDate_more">
											<!--                                         09/03/2024 -->
										</td>
									</tr>

									<tr>
										<td class="data_option">RRN</td>
										<td class="hyphen">-</td>
										<td class="data_value ewallet_paymentMethod_rrn">
											<!--                                         406712675069 -->
										</td>
									</tr>

									<tr>
										<td class="data_option">Approval Code</td>
										<td class="hyphen">-</td>
										<td class="data_value ewallet_approvalCode_more">
											<!--                                         2402080335350061 -->
										</td>
									</tr>

									<tr id="receipt_ewallet">
										<td class="data_option">Sales Slip</td>
										<td class="hyphen">-</td>
										<td class="data_value cursor-pointer receipt">

											<button type="button" id="ewalletSlip_id"
												class="cursor-pointer" onclick="openEwalletSlip()"
												style="display: flex; align-items: center; justify-content: flex-start; padding: 0; background-color: transparent; border: none;">

												<img
													src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/slip.svg"
													width="20" height="20" alt=""><span
													class="color-skyblue fw-500 ml-1">View Receipt</span>

											</button>


										</td>
									</tr>

									<tr
										style="display: flex; align-items: flex-start !important; justify-content: flex-start !important;">
										<td class="data_option">Sub Merchant MID</td>
										<td class="hyphen">-</td>
										<td class="data_value ewallet_submerchantMid_more">
											<!--                                         2011000000000065 -->
										</td>
									</tr>


								</tbody>
							</table>
						</div>


					</div>


				</div>
				<div class=" align-center modal-footer footer  moredetails_footer">
					<button id="closeewallet"
						class=" btn waves-effect waves-light blue-btn closebtn"
						type="button" onclick="closeEwallet()" name="action">Close
					</button>
				</div>
			</div>


		</div>


		<!-- modal for fpx transaction details -->
		<div class="more_info modal-container" id="modalContainerFPX">
			<div id="moredetails" class="moredetails-modal-class">
				<div class="outerborder" style="padding: 10px;">

					<div class="modal-header header_txndetails">
						<div class="color-blue fw-600" style="font-size: 18px;">
							More Details
							<button id="closefpxmore_xmark" onclick="closeFPX()"
								style="float: right; margin: 2px; background-color: transparent; border: none;">
								<img
									src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/xmark.svg"
									width="20" height="20" class="cursor-pointer " alt="">
							</button>
						</div>
					</div>
					<div class="modal-content content-moredetails">
						<div class="color-blue fw-600 mt-1" style="font-size: 17px;">Transaction
							Details</div>
						<div class="txn-table">
							<table class="txn-popuptable">
								<thead>
									<tr>
										<!-- <th class="color-blue fw-600 " colspan="3">Transaction Details</th> -->
									</tr>
								</thead>

								<tbody class="txn-details-table">
									<tr>
										<td class="data_option">Date</td>
										<td class="hyphen">-</td>
										<td class="data_value txDate-value"></td>
									</tr>

									<tr>
										<td class="data_option">Time</td>
										<td class="hyphen">-</td>
										<td class="data_value txTime-value"></td>
									</tr>

									<tr>
										<td class="data_option">Transaction ID</td>
										<td class="hyphen">-</td>
										<td class="data_value transaction-id"></td>
									</tr>

									<tr>
										<td class="data_option">Amount (RM)</td>
										<td class="hyphen">-</td>
										<td class="data_value amt-value"></td>
									</tr>
									<tr>
										<td class="data_option">Payment Method</td>
										<td class="hyphen">-</td>
										<td class="data_value payoutMethod-value">
											<%--                                            Boost--%>
										</td>
									</tr>
									<tr>
										<td class="data_option">Transaction Status</td>
										<td class="hyphen">-</td>
										<td class="data_value transaction-status">

											<div
												style="display: flex; align-items: center; justify-content: flex-start;">

											</div>

										</td>
									</tr>

									<tr>
										<td class="data_option">MID</td>
										<td class="hyphen">-</td>
										<td class="data_value mid-value">
											<%--                                            000000000038605--%>
										</td>
									</tr>


									<tr>
										<td class="data_option">TID</td>
										<td class="hyphen">-</td>
										<td class="data_value tid-value">
											<%--                                            10009291--%>
										</td>
									</tr>


									<tr>
										<td class="data_option">MDR Amount</td>
										<td class="hyphen">-</td>
										<td class="data_value mdrAmt-value">
											<%--                                            7.16--%>
										</td>
									</tr>


									<tr>
										<td class="data_option">Settlement Amount</td>
										<td class="hyphen">-</td>
										<td class="data_value payableAmt-value"></td>
									</tr>


									<tr>
										<td class="data_option">Payment Date</td>
										<td class="hyphen">-</td>
										<td class="data_value settledDate-value"></td>
									</tr>

									<tr>
										<td class="data_option">RRN</td>
										<td class="hyphen">-</td>
										<td class="data_value" style="position: relative; left: 30px;">-</td>
									</tr>

									<tr>
										<td class="data_option">Approval Code</td>
										<td class="hyphen">-</td>
										<td class="data_value approvalCode-value"></td>
									</tr>
									<tr>
										<td class="data_option">Reference No</td>
										<td class="hyphen">-</td>
										<td class="data_value referenceNo-value"></td>
									</tr>

									<tr id="receipt_fpx">
										<td class="data_option">Sales Slip</td>
										<td class="hyphen">-</td>
										<td class="data_value cursor-pointer receipt">

											<button type="button" id="fpx_slip_id" class="cursor-pointer"
												onclick="openFpxSlip(
                                    <%--'${dto.txDate}',--%>
                                    <%--'${dto.txTime}',--%>
                                    <%--'${dto.fpxTxnId}',--%>
                                    <%--'${dto.txnAmount}',--%>
                                    <%--'${dto.paymentMethod}',--%>
                                    <%--'${dto.status}',--%>
                                    <%--'${dto.mid}',--%>
                                    <%--'${dto.tid}',--%>
                                    <%--'${dto.mdrAmt}',--%>
                                    <%--'${dto.payableAmt}',--%>
                                    <%--'${dto.settledDate}',--%>
                                    <%--'${dto.timestamp}',--%>
                                    <%--'${dto.subMerchantMID}',--%>
                                    <%--'${dto.sellerOrderNo}',--%>
                                    <%--'${dto.sellerExOrderNo}'--%>
                                            )"
												style="display: flex; align-items: center; justify-content: flex-start; padding: 0; background-color: transparent; border: none;">

												<img
													src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/slip.svg"
													width="20" height="20" alt=""><span
													class="color-skyblue fw-500 ml-1">View Receipt</span>

											</button>


										</td>
									</tr>

									<tr
										style="display: flex; align-items: flex-start !important; justify-content: flex-start !important;">
										<td class="data_option">Sub Merchant MID</td>
										<td class="hyphen">-</td>
										<td class="data_value subMerchantMID-value"></td>
									</tr>


								</tbody>
							</table>
						</div>


					</div>


				</div>
				<div class=" align-center modal-footer footer  moredetails_footer">
					<button id="closefpx"
						class=" btn waves-effect waves-light blue-btn closebtn"
						type="button" onclick="closeFPX()" name="action">Close</button>
				</div>
			</div>


		</div>

		<!-- modal for payout transaction details -->
		<div class="more_info modal-container" id="modalContainerPayout">
			<div id="moredetails" class="moredetails-modal-class">
				<div class="outerborder" style="padding: 10px;">

					<div class="modal-header header_txndetails">
						<div class="color-blue fw-600" style="font-size: 18px;">
							More Details
							<button id="closepayoutmore_xmark" onclick="closePayout()"
								style="float: right; margin: 2px; background-color: transparent; border: none;">
								<img
									src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/xmark.svg"
									width="20" height="20" class="cursor-pointer " alt="">
							</button>
						</div>
					</div>
					<div class="modal-content content-moredetails">
						<div class="color-blue fw-600 mt-1" style="font-size: 17px;">Transaction
							Details</div>
						<div class="txn-table">
							<table class="txn-popuptable">
								<thead>
									<tr>
										<!-- <th class="color-blue fw-600 " colspan="3">Transaction Details</th> -->
									</tr>
								</thead>

								<tbody class="txn-details-table">
									<tr id="payout_date_Scroll_tr">
										<td class="data_option">Date</td>
										<td class="hyphen">-</td>
										<td class="data_value payout_date_date"></td>
									</tr>

									<tr>
										<td class="data_option">Merchant Name</td>
										<td class="hyphen">-</td>
										<td class="data_value createdby-value-payout"></td>
									</tr>

									<tr>
										<td class="data_option">Customer Name</td>
										<td class="hyphen">-</td>
										<td class="data_value payeename-value-payout"></td>
									</tr>

									<tr>
										<td class="data_option">BRN/IC</td>
										<td class="hyphen">-</td>
										<td class="data_value payeebrn-value-payout"></td>
									</tr>

									<tr>
										<td class="data_option">Account Number</td>
										<td class="hyphen">-</td>
										<td class="data_value payeeaccnumber-value-payout"></td>
									</tr>

									<tr>
										<td class="data_option">Bank Name</td>
										<td class="hyphen">-</td>
										<td class="data_value payeebankname-value-payout"></td>
									</tr>
									<tr>
										<td class="data_option">Payout ID</td>
										<td class="hyphen">-</td>
										<td class="data_value payoutId-value-payout"></td>
									</tr>

									<tr>
										<td class="data_option">Transaction ID</td>
										<td class="hyphen">-</td>
										<td class="data_value invoiceidproof-value-payout"></td>
									</tr>

									<tr>
										<td class="data_option">Amount (RM)</td>
										<td class="hyphen">-</td>
										<td class="data_value payoutamount-value-payout"></td>
									</tr>
									<tr>
										<td class="data_option">Payment Method</td>
										<td class="hyphen">-</td>
										<td class="data_value">Payout</td>
									</tr>
									<tr>
										<td class="data_option">Transaction Status</td>
										<td class="hyphen">-</td>
										<td class="data_value payoutstatus-value-payout">

											<div
												style="display: flex; align-items: center; justify-content: flex-start;">
												<img
													src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg"
													width="20" height="20" alt=""><span
													class="color-orange fw-500 ml-1">Not Settled</span>
											</div>

										</td>
									</tr>

									<tr>
										<td class="data_option">Time Stamp</td>
										<td class="hyphen">-</td>
										<td class="data_value timeStamp-value-payout"></td>
									</tr>


									<tr>
										<td class="data_option">Paid/Declined Time</td>
										<td class="hyphen">-</td>
										<td class="data_value paidTime-value-payout"></td>
									</tr>


									<tr>
										<td class="data_option">Paid/Declined Date</td>
										<td class="hyphen">-</td>
										<td class="data_value paidDate-value-payout"></td>
									</tr>


									<tr>
										<td class="data_option">Sub Merchant MID</td>
										<td class="hyphen">-</td>
										<td class="data_value submerchantMid-value-payout"></td>
									</tr>


									<tr>
										<td class="data_option">Sub Merchant Name</td>
										<td class="hyphen">-</td>
										<td class="data_value mmId-value-payout"></td>
									</tr>

									<tr id="payout_decline_reason_tr">
										<td class="data_option">Decline Reason</td>
										<td class="hyphen">-</td>
										<td class="data_value failurereason-value-payout"></td>
									</tr>
									<tr id="receipt_payout">
										<td class="data_option">Sales Slip</td>
										<td class="hyphen">-</td>
										<td class="data_value cursor-pointer receipt" id="">

											<button type="button" id="payoutSlip-id"
												class="cursor-pointer" onclick="openPayoutSlip()"
												style="display: flex; align-items: center; justify-content: flex-start; padding: 0; background-color: transparent; border: none;">
												<img
													src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/slip.svg"
													width="20" height="20" alt=""><span
													class="color-skyblue fw-500 ml-1">View Receipt</span>
											</button>


										</td>
									</tr>
								</tbody>
							</table>
						</div>


					</div>


				</div>
				<div class=" align-center modal-footer footer  moredetails_footer">
					<button id="closepayout"
						class=" btn waves-effect waves-light blue-btn closebtn"
						onclick="closePayout()" type="button" name="action">Close
					</button>
				</div>
			</div>


		</div>


	</div>


	<div id="pagination"></div>
	<input type="hidden" id="pgnum">


	<input type="hidden" id="retrieve_type">


	<script>


    var size = "${paginationBean.querySize}";


    //Assuming you have a function to add a class to elements
    function addClass(element, className) {
        if (element.classList) {
            element.classList.add(className);
        } else {
            element.className += ' ' + className;
        }
    }


    function dynamic(pgNo) {
        /* alert("Page Number:"+pgNo); */
        document.getElementById("pgnum").value = pgNo;
        loadSelectData2();

    }

    function previous(pgNo) {
        /* alert("Page Number:"+pgNo); */
        pgNo--;
        document.getElementById("pgnum").value = pgNo;
        loadSelectData2();

    }

    function next(pgNo) {
        /* alert("Page Number:"+pgNo); */
        pgNo++;
        document.getElementById("pgnum").value = pgNo;
        loadSelectData2();
    }


    var Pagination = {

        code: '',

        // --------------------
        // Utility
        // --------------------

        // converting initialize data
        Extend: function (data) {
            data = data || {};
            Pagination.size = Math.ceil(${paginationBean.querySize} / 10);


            <%--Pagination.size = ((${paginationBean.currPage})+4) ||100;--%>
            /* Pagination.page = data.page || 1; */
            Pagination.page =
            ${paginationBean.currPage} ||
            1;
            Pagination.step = ((data.step) - 4) || 3;
        },

        // add pages by number (from [s] to [f])
        Add: function (s, f) {
            for (var i = s; i < f; i++) {
                Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
            }
        },

        // add last page with separator
        /*   Last: function() {
              Pagination.code += '<i>...</i>';
          },
           */
        // Last: function() {
        //     Pagination.code += '<a onclick="dynamic(((Pagination.page)+1))">'+ ((Pagination.page)+1)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+2))">'+ ((Pagination.page)+2)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+3))">'+ ((Pagination.page)+3)+ '</a>'+'<i>...</i>';
        // },
        //
        // // add first page with separator
        // First: function() {
        //     if(Pagination.page==1){
        //
        //         Pagination.code += '<i>...</i>'+'<a onclick="dynamic(Pagination.page)" id="page1">'+Pagination.page+'</a>';
        //
        //     }
        //     else{
        //         Pagination.code += '<a>1</a>'+'<i>...</i>'+'<a  onclick="dynamic(((Pagination.page)-1))">'+((Pagination.page)-1)+'</a>'+'<a id="page2" onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';
        //     }
        // },


        // newer chnagess

        First: function () {
            Pagination.code += '<a onclick="dynamic(1)">1</a>';
            if (Pagination.page > 3) {
                Pagination.code += '<i>...</i>';
            }
            for (var i = Math.max(2, Pagination.page - 1); i <= Pagination.page; i++) {
                //directly goes to 1 instead of re-arranging
                Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
            }
        },

// arranges ...
        Last: function () {

            // total page
            var lastPage = Math.ceil(${paginationBean.querySize} / 10);
            // three pg no after 1st pg no
            if (lastPage > Pagination.page + 3) {
                // generate <a> tag for 3 pg no
                for (var i = Pagination.page + 1; i <= Pagination.page + 3; i++) {
                    Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
                }
                Pagination.code += '<i>...</i>';
//         Pagination.code += '<a onclick="dynamic(' + lastPage + ')">' + lastPage + '</a>';
            } else {
                // if less than 3 page generate <a> tag
                for (var i = Pagination.page + 1; i <= lastPage; i++) {
                    Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
                }
            }
        },


        // --------------------
        // Handlers
        // --------------------

        // change page
        Click: function () {
            Pagination.page = +this.innerHTML;
            Pagination.Start();
            dynamic(page);
        },

        // previous page
        Prev: function () {

            Pagination.page--;
            if (Pagination.page < 1) {
                Pagination.page = 1;
            }
            Pagination.Start();
            dynamic(page);
        },


        // next page


        Next: function () {
            Pagination.page++;
            if (Pagination.page > Pagination.size) {
                Pagination.page = Pagination.size;
            }
            Pagination.Start();
            dynamic(page);
        },


        // --------------------
        // Script
        // --------------------

        // binding pages
        Bind: function () {
            var a = Pagination.e.getElementsByTagName('a');
            for (var i = 0; i < a.length; i++) {
                if (+a[i].innerHTML === Pagination.page) a[i].className = 'current';
                a[i].addEventListener('click', Pagination.Click, false);
            }
        },

        // write pagination
        Finish: function () {
            Pagination.e.innerHTML = Pagination.code;
            Pagination.code = '';
            Pagination.Bind();
        },

        // find pagination type
        Start: function () {
            if (Pagination.size < Pagination.step * 2 + 6) {
                Pagination.Add(1, Pagination.size + 1);
            } else if (Pagination.page < Pagination.step * 2 + 1) {
                Pagination.Add(1, Pagination.step * 2 + 4);
                Pagination.Last();
            } else if (Pagination.page > Pagination.size - Pagination.step * 2) {
                Pagination.First();
                Pagination.Add(Pagination.size - Pagination.step * 2 - 2, Pagination.size + 1);
            } else {
                Pagination.First();
                Pagination.Add(Pagination.page - Pagination.step, Pagination.page + Pagination.step + 1);
                Pagination.Last();
            }
            Pagination.Finish();
        },


        // --------------------
        // Initialization
        // --------------------

        // binding buttons
        Buttons: function (e) {
            var nav = e.getElementsByTagName('a');

            nav[0].addEventListener('click', Pagination.Prev, false);
            nav[1].addEventListener('click', Pagination.Next, false);
        },

        // create skeleton
        Create: function (e) {

            var html = [
                '<a onclick="previous(${paginationBean.currPage})" style="width:7rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px; " id="previous">&#60;&#60; Previous</a>', // previous button
                '<span></span>',  // pagination container
                '<a onclick="next(${paginationBean.currPage})" style="width:6.5rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;" id="nxt">Next &#62;&#62;</a>'  // next button
            ];

            e.innerHTML = html.join('');
            Pagination.e = e.getElementsByTagName('span')[0];
            Pagination.Buttons(e);

            <%--if (${paginationBean.currPage} == 1) {--%>
            //     var previousButton = document.getElementById("previous");
            //     previousButton.style.pointerEvents = "none";
            //     previousButton.style.opacity = "0.5";
            // }

            if (${paginationBean.currPage} == 1)
            {
                var previousButton = document.getElementById("previous");
                previousButton.style.pointerEvents = "none";
                previousButton.style.opacity = "0.5";
            }
            // my chnages
            if (${paginationBean.currPage} == Pagination.size
        )
            {
                var nextButton = document.getElementById("nxt");
                nextButton.style.pointerEvents = "none";
                nextButton.style.opacity = "0.5";
            }
            if (Pagination.size == 0) {
                var nextButton = document.getElementById("nxt");
                nextButton.style.pointerEvents = "none";
                nextButton.style.opacity = "0.5";
            }
            if (Pagination.size == 0) {
                var paginationContainer = document.getElementById("pagination");
                paginationContainer.style.display = "none";
            }

        },

        // init
        Init: function (e, data) {
            Pagination.Extend(data);
            Pagination.Create(e);
            Pagination.Start();
        }
    };


    /* * * * * * * * * * * * * * * * *
    * Initialization
    * * * * * * * * * * * * * * * * */

    var init = function () {
        Pagination.Init(document.getElementById('pagination'), {
            size: 100, // pages size
            page: 1,  // selected page
            step: 3   // pages before and after current
        });
    };

    document.addEventListener('DOMContentLoaded', init, false);


    var PageNumber = document.getElementById("pgnum").value;


</script>

	<style>
#pagination {
	display: inline-block;
	vertical-align: middle;
	border-radius: 1px;
	padding: 1px 2px 4px 2px;
	border-top: 1px solid transparent;
	border-bottom: 1px solid transparent;
	background-color: transparent;
	float: right;
	margin-right: 15px;
	margin-bottom: 10px;
	/* background-image: -webkit-linear-gradient(top, #DBDBDB, #E2E2E2);
          background-image:    -moz-linear-gradient(top, #DBDBDB, #E2E2E2);
          background-image:     -ms-linear-gradient(top, #DBDBDB, #E2E2E2);
          background-image:      -o-linear-gradient(top, #DBDBDB, #E2E2E2);
          background-image:         linear-gradient(top, #DBDBDB, #E2E2E2); */
	/*  position:absolute;
          left:62rem;
          bottom:1rem; */
	font-family: 'Poppins', sans-serif;
	/* width:20%;
          height:6%; */
}

#pagination a, #pagination i {
	display: inline-block;
	vertical-align: middle;
	width: 2.2rem;
	/*  color: #7D7D7D; */
	text-align: center;
	font-size: 16px;
	padding: 2.5px;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	-o-user-select: none;
	user-select: none;
}

#pagination a {
	/* margin: 0 2px 0 2px; */
	margin: 0 2px;
	border-radius: 1px;
	border: 1px solid #005baa;
	cursor: pointer;
	/* box-shadow: inset 0 1px 0 0 #D7D7D7, 0 1px 2px #666; */
	/* text-shadow: 0 1px 1px #FFF; */
	background-color: white;
	color: #005baa;
	height: 2.3rem;
	vertical-align: middle;
	padding-top: 4px;
}

#pagination i {
	/*  margin: 0 3px 0 3px; */
	
}

#pagination a.current {
	border: 1px solid #005baa;
	box-shadow: 0 1px 1px #999;
	background-color: #005baa;
	color: white;
}

.editBtn {
	text-decoration: none;
	color: #039be5;
	-webkit-tap-highlight-color: transparent !important;
	border: none;
	cursor: pointer;
	background: none;
}
</style>


	<script>


    function loadSelectData() {
        //alert("test"+document.getElementById("txnType").value);
        var e = document.getElementById("from").value;
        var e1 = document.getElementById("to").value;

        var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
        var toDate = new Date(e1);//.toDateString("yyyy-MM-dd");
        var PageNumber = document.getElementById("pgnum").value;

        var fromday = fromDate.getDate();
        var frommon = fromDate.getMonth() + 1;
        var fromyear = fromDate.getFullYear();

        var today = toDate.getDate();
        var tomon = toDate.getMonth() + 1;
        var toyear = toDate.getFullYear();

        var fromdateString = fromyear + '-'
            + (frommon <= 9 ? '0' + frommon : frommon) + '-'
            + (fromday <= 9 ? '0' + fromday : fromday);
        var todateString = toyear + '-' + (tomon <= 9 ? '0' + tomon : tomon)
            + '-' + (today <= 9 ? '0' + today : today);

        if (e == null || e == '' || e1 == null || e1 == '') {
            alert("Please Select date(s)");

        } else {
            document.getElementById("dateval1").value = fromdateString;
            document.getElementById("dateval2").value = todateString;

            /* document.getElementById("txnType").value = e2; */
            /* document.location.href = '





            <%--${pageContext.request.contextPath}/transaction/searchUMEzyway?date='--%>
    + e + '&date1=' + e1 + '&txnType=' + e2; */

            document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchPayout?date='
                + fromdateString + '&date1=' + todateString + '&currPage=' + PageNumber;


            /* Updated code...... */
            localStorage.setItem("fromDate", e);
            localStorage.setItem("toDate", e1);

            form.submit;

        }
    }


    <%--function loadSelectData2() {--%>
    <%--    var PageNumber = document.getElementById("pgnum").value;--%>
    <%--    var dataArray = document.getElementById('transaction_ids').value;--%>
    <%--    var paymentMethod = document.getElementById("payment_method").value;--%>
    <%--    var chooseType = document.getElementById("search_type").value;--%>
    <%--    // var payment_type ='SHOPEEPAY';--%>
    <%--    var payment_type =document.getElementById("payment_type").value;--%>


    function loadSelectData2() {
        $("#overlay").show();
        var PageNumber = document.getElementById("pgnum").value;
        var dataArray = document.getElementById('transaction_ids').value;
        dataArray = JSON.stringify(dataArray);
        // var dataArray = ["545301ffffff5323"];
        var paymentMethod = document.getElementById("payment_method").value;
        //var paymentMethod = document.getElementById("payment_method").value;
        var payment_type = document.getElementById("payment_type").value;


        var final_chooseType_value;

        var chooseType_card = document.getElementById("search_type_card").value;
        var chooseType_fpx = document.getElementById("search_type_fpx").value;
        var chooseType_boostgrab = document.getElementById("search_type_ewallet_boost_grab").value;
        var chooseType_tngspp = document.getElementById("search_type_ewallet_tng_spp").value;
        var chooseType_payout = document.getElementById("search_type_payout").value;

        if (chooseType_card !== "") {
            final_chooseType_value = chooseType_card;
        } else if (chooseType_fpx !== "") {
            final_chooseType_value = chooseType_fpx;
        } else if (chooseType_boostgrab !== "") {
            final_chooseType_value = chooseType_boostgrab;
        } else if (chooseType_tngspp !== "") {
            final_chooseType_value = chooseType_tngspp;
        } else if (chooseType_payout !== "") {
            final_chooseType_value = chooseType_payout;
        }


        document.location.href = '${pageContext.request.contextPath}/searchNew/PaginationMasterSearch?currentPage=' + PageNumber +
            '&dataArray=' + dataArray +
            '&payment_method=' + paymentMethod +
            '&chooseType=' + final_chooseType_value +
            '&payment_type=' + payment_type;


        form.submit;
        $("#overlay").hide();
    }

    window.addEventListener('load', function () {
        document.getElementById("transaction_ids").value = backendSearchList;

    });


    document.addEventListener('DOMContentLoaded', function () {
        var elems = document.querySelectorAll('select');
        var instances = M.FormSelect.init(elems);


    });


    var moredetails_modal = document.getElementById("modalContainerCard");

    var ewalletmoredetails_modal = document.getElementById("modalContainerEwallet");

    var fpxmoredetails_modal = document.getElementById("modalContainerFPX");


    var payoutmoredetails_modal = document.getElementById("modalContainerPayout");

    var slip_modal = document.getElementById("modalContainer_receipt");

    var declinereason_modal = document.getElementById("modalContainer_declinereason");


    var cardslip_modal = document.getElementById("cardModalContainer_receipt");

    var ewalletslip_modal = document.getElementById("ewalletModalContainer_receipt");

    var fpxslip_modal = document.getElementById("fpxModalContainer_receipt");

    var payoutslip_modal = document.getElementById("payoutModalContainer_receipt");


    function cardslipStatus(dtoStatus) {

        var imgSrc;


        switch (dtoStatus) {
            case 'mastercard':
            case 'MASTERCARD':
            case 'MASTERCARD CREDIT':
            case 'mastercard card':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/MasterCard.svg';
                break;
            case 'visa':
            case 'VISA':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Visa.svg';
                break;
            case 'CHINA UNION PAY':
            case 'china union pay':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/UnionPay.svg';
                break;
            default:
                // Handle unknown status
                return;
        }
        var status_td = document.querySelector(".card_payment_method");
        status_td.innerHTML = '';
        var container = document.createElement('div');
        container.style.display = 'flex';
        container.style.alignItems = 'center';
        container.style.justifyContent = 'flex-start';
        container.style.position = 'relative';
        container.style.bottom = '12px';


        var img = document.createElement('img');
        img.src = imgSrc;
        img.width = '50';
        img.height = '50';

        img.alt = '';


        container.appendChild(img);
        status_td.appendChild(container);

    }


    // reset modal for fpx more icon
    var originalTrContentCardMore = document.getElementById("modalContainerCard").innerHTML;
    document.getElementById("modalContainerCard").addEventListener("shown.bs.modal", function () {
        resetCardMoreModal();
    });

    function resetCardMoreModal() {
        var modalContent = document.querySelector("#modalContainerCard .modal-content");
        modalContent.scrollTop = 0;
        document.getElementById("modalContainerCard").innerHTML = originalTrContentCardMore;
    }


    function openCardMoredetailsModal(transactionDate, transactionTime, transactionId,
                                      transactionAmount,
                                      paymentMethod, status,
                                      cardNumber, cardHolderName, mid, tid, rrn,
                                      approvalCode, mdrAmount, netAmount, cardType,
                                      merchantName, address, contactNumber, auth3Ds, timestamp, refereceNo, country, settlementDateCard) {

        body.style.overflow = "hidden";
        resetCardMoreModal();
        if (status === 'FAILED') {
            document.getElementById("card_slip_card_card").classList.add("hidecontent");
        }


        setStatusImageAndTextForCard(status);
        cardslipStatus(paymentMethod);

        document.querySelector(".card_modal_date").innerHTML = transactionDate;
        document.querySelector(".card_modal_time").innerHTML = transactionTime;
        document.querySelector(".card_modal_txnId").innerHTML = transactionId;
        document.querySelector(".card_modal_amount").innerHTML = transactionAmount;
        // document.querySelector(".card_modal_status").innerHTML = setStatusImageAndText(status);
        document.querySelector(".card_modal_cardNumber").innerHTML = cardNumber;
        document.querySelector(".card_modal_cardHolder").innerHTML = cardHolderName;
        document.querySelector(".card_modal_mid").innerHTML = mid;
        document.querySelector(".card_modal_tid").innerHTML = tid;
        document.querySelector(".card_modal_rrn").innerHTML = rrn;
        document.querySelector(".card_modal_approvalCode").innerHTML = approvalCode;
        document.querySelector(".card_modal_paymentMethod").innerHTML = paymentMethod;
        document.querySelector(".card_modal_mdrAmount").innerHTML = mdrAmount;
        document.querySelector(".card_modal_netAmount").innerHTML = netAmount;
        document.querySelector(".card_modal_paymentDate").innerHTML = settlementDateCard;


        const data = {
            transactionDate: transactionDate,
            transactionTime: transactionTime,
            transactionId: transactionId,
            transactionAmount: transactionAmount,
            paymentMethod: paymentMethod,
            cardNumber: cardNumber,
            cardHolderName: cardHolderName,
            mid: mid,
            tid: tid,
            rrn: rrn,
            approvalCode: approvalCode,
            mdrAmount: mdrAmount,
            netAmount: netAmount,
            cardType: cardType,
            merchantName: merchantName,
            address: address,
            contactNumber: contactNumber,
            auth3Ds: auth3Ds,
            timestamp: timestamp,
            refereceNo: refereceNo,
            country: country
        };

        document.getElementById("card_slip_id").addEventListener("click", function () {
            openCardSlip(data);
        });


        moredetails_modal.style.display = "block";
        moredetails_modal.scrollTop = 0;
    }

    //vinoth

    function setStatusImageAndTextForCard(dtoStatus) {
        var imgSrc;
        var statusText;
        var statusColor; // Default color

        switch (dtoStatus) {
            case 'SETTLED':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/paid.svg';
                statusText = 'Settled';
                statusColor = '#11bf36';
                break;
            case 'NOT SETTLED':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg';
                statusText = 'Not Settled';
                statusColor = 'orange';
                break;
            case 'VOIDED':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Void.svg';
                statusText = 'Voided';
                statusColor = '#005baa';
                break;
            case 'REFUNDED':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Refunded.svg';
                statusText = 'Refunded';
                statusColor = 'grey';
                break;
            case 'PREAUTH':

            <%--src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Void.svg"--%>
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Pre-auth.svg';
                statusText = 'Pre-Auth';
                statusColor = '#7B68EE';
                break;
            case 'EZYSETTLE':

            <%--src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Void.svg"--%>
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Pre-auth.svg';
                statusText = 'EZYSETTLE';
                statusColor = '#005BAA';
                break;
            case 'FAILED':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Declined.svg';
                statusText = 'Failed';
                statusColor = 'red';
                break;
            default:
                // Handle unknown status
                return;
        }
        var status_td = document.querySelector(".card_modal_status");
        status_td.innerHTML = '';
        var container = document.createElement('div');
        container.style.display = 'flex';
        container.style.alignItems = 'center';
        container.style.justifyContent = 'flex-start';

        var img = document.createElement('img');
        img.src = imgSrc;
        img.width = '20';
        img.height = '20';
        img.alt = '';

        var statusSpan = document.createElement('span');
        statusSpan.className = 'fw-500 ml-1';
        statusSpan.style.color = statusColor;
        statusSpan.style.fontWeight = '500';
        statusSpan.textContent = statusText;

        container.appendChild(img);
        container.appendChild(statusSpan);
        status_td.appendChild(container);

    }


    function ewalletSlipStatus(dtoStatus) {

        var imgSrc;


        switch (dtoStatus) {
            case 'Shopeepay':
            case 'SHOPEEPAY':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/shopeepay.svg';
                break;
            case "Touch'N_Go":
            case "TOUCH'N_GO":
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/tng.svg';
                break;
            case 'Grab':
            case 'GTAB':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/grab.svg';
                break;
            case 'Boost':
            case 'BOOST':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/boost.svg';
                break;

            default:
                // Handle unknown status
                return;
        }
        var status_td = document.querySelector(".ewallet_paymentMethod_slip");
        status_td.innerHTML = '';
        var container = document.createElement('div');
        container.style.display = 'flex';
        container.style.alignItems = 'center';
        container.style.justifyContent = 'flex-start';
        container.style.position = 'relative';
        container.style.bottom = '12px';


        var img = document.createElement('img');
        img.src = imgSrc;
        img.width = '50';
        img.height = '50';

        img.alt = '';


        container.appendChild(img);
        status_td.appendChild(container);

    }

    //
    // function resetModal() {
    //     var modal = document.getElementById("modalContainerEwallet");
    //     if (!modal) {
    //         console.error("Modal element not found.");
    //         return;
    //     }
    //
    //     var body = document.body;
    //
    //     var modalContent = modal.querySelector(".content-moredetails");
    //     var tableContent = modal.querySelector(".txn-details-table");
    //     if (!tableContent) {
    //         console.error("Table content element not found.");
    //         return;
    //     }
    //     tableContent.scrollTop = 0;
    //     while (tableContent.firstChild) {
    //         tableContent.removeChild(tableContent.firstChild);
    //     }
    //     modal.style.display = "none";
    //     body.style.overflow = initialOverflow;
    // }
    //
    //
    // function resetModal() {
    //     var tbody = document.querySelector(".txn-details-table")
    //     while (tbody.firstChild) {
    //         tbody.removeChild(tbody.firstChild);
    //     }
    //     // modal.style.display = "none";
    //     body.style.overflow = initialOverflow;
    // }


    // reset modal for fpx more icon
    var originalTrContentEwalletMore = document.getElementById("modalContainerEwallet").innerHTML;
    document.getElementById("modalContainerEwallet").addEventListener("shown.bs.modal", function () {
        resetEwalletMoreModal();
    });

    function resetEwalletMoreModal() {
        var modalContent = document.querySelector("#modalContainerEwallet .modal-content");
        modalContent.scrollTop = 0;
        document.getElementById("modalContainerEwallet").innerHTML = originalTrContentEwalletMore;
    }

    function openEwalletMoredetailsModal(Date,
                                         Time, transactionID, transactionAmount, paymentMethod, status, mid, tid, mdrAmount,
                                         settlementAmount, settlementDate, rrn, approvalCode, subMerchantMID, referanceNo, timeStamp, merchantName, address, contactNumber, country) {


        // resetModal();
        resetEwalletMoreModal();

        body.style.overflow = "hidden";
        ewalletmoredetails_modal.style.display = "block";
        ewalletmoredetails_modal.scrollTop = 0;


        paymentMethod = backEndEwalletPaymentType;


        if (status === 'FAILED') {
            document.getElementById("receipt_ewallet").classList.add("hidecontent");
        } else {
            document.getElementById("receipt_fpx").classList.remove("hidecontent");
        }


        // paymentMethod = 'BOOST'
        // body.style.overflow = "hidden";
        ewalletSlipStatus(paymentMethod);
        setStatusImageAndTextForEwallet(status)
        document.querySelector(".ewallet_date_more").innerHTML = Date;
        document.querySelector(".ewallet_time_more").innerHTML = Time;
        document.querySelector(".ewallet_txn_more").innerHTML = transactionID;
        document.querySelector(".ewallet_amt_more").innerHTML = transactionAmount;

        if (paymentMethod === "Touch'N_Go") {
            paymentMethod = "Touch N go";
        }
        document.querySelector(".ewallet_paymentMethod_more").innerHTML = paymentMethod;
        // document.querySelector(".ewallet_status_more").innerHTML = status;
        document.querySelector(".ewallet_mid_more").innerHTML = mid;
        document.querySelector(".ewallet_tid_more").innerHTML = tid;
        document.querySelector(".ewallet_mdrAmt_more").innerHTML = mdrAmount;
        document.querySelector(".ewallet_settlementAmt_more").innerHTML = settlementAmount;
        document.querySelector(".ewallet_paymentDate_more").innerHTML = settlementDate;
        // document.querySelector(".ewallet_paymentMethod_rrn").innerHTML = rrn;
        document.querySelector(".ewallet_approvalCode_more").innerHTML = approvalCode;
        document.querySelector(".ewallet_submerchantMid_more").innerHTML = subMerchantMID;
        document.querySelector(".ewallet_paymentMethod_rrn").innerHTML = rrn;
        if (rrn.includes('-')) {
            document.querySelector(".ewallet_paymentMethod_rrn").style.position = 'relative';
            document.querySelector(".ewallet_paymentMethod_rrn").style.left = '30px';
        } else {
            document.querySelector(".ewallet_paymentMethod_rrn").innerHTML = rrn;

        }


        var referanceNoElement = document.createElement('div');
        referanceNoElement.id = 'referanceNo_global_id';
        referanceNoElement.textContent = referanceNo;
        document.getElementById('hiddenContainer').appendChild(referanceNoElement);

        var timeStampElement = document.createElement('div');
        timeStampElement.id = 'timeStamp_ewallet_global_id';
        timeStampElement.textContent = timeStamp;
        document.getElementById('hiddenContainer').appendChild(timeStampElement);

        var merchantNameElement = document.createElement('div');
        merchantNameElement.id = 'merchantName_ewallet_global_id';
        merchantNameElement.textContent = merchantName;
        document.getElementById('hiddenContainer').appendChild(merchantNameElement);


        const data = {
            Date: Date,
            Time: Time,
            transactionID: transactionID,
            transactionAmount: transactionAmount,
            paymentMethod: paymentMethod,
            status: status,
            mid: mid,
            tid: tid,
            mdrAmount: mdrAmount,
            settlementAmount: settlementAmount,
            settlementDate: settlementDate,
            rrn: rrn,
            approvalCode: approvalCode,
            subMerchantMID: subMerchantMID,
            referanceNo: referanceNo,
            timeStamp: timeStamp,
            merchantName: merchantName,
            address: address,
            contactNumber: contactNumber,
            country: country


        };

        document.getElementById("ewalletSlip_id").addEventListener("click", function () {
            openEwalletSlip(data);
        });


    }


    function setStatusImageAndTextForEwallet(dtoStatus) {
        var imgSrc;
        var statusText;
        var statusColor; // Default color

        switch (dtoStatus) {
            case 'SETTLED':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/paid.svg';
                statusText = 'Settled';
                statusColor = '#11bf36';
                break;
            case 'NOT SETTLED':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg';
                statusText = 'Not Settled';
                statusColor = 'orange';
                break;
            case 'VOID':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Void.svg';
                statusText = 'Voided';
                statusColor = '#005baa';
                break;
            case 'REFUNDED':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Refunded.svg';
                statusText = 'Refunded';
                statusColor = 'grey';
                break;
            case 'PENDING':
            <%--src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Void.svg"--%>
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg';
                statusText = 'Pending';
                statusColor = 'orange';
                break;
            case 'FAILED':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Declined.svg';
                statusText = 'Failed';
                statusColor = 'red';
                break;
            case 'EZYSETTLE':
                imgSrc = '${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/ezysettle.svg';
                statusText = 'EZYSETTLE';
                statusColor = '#005BAA';
                break;


            default:
                // Handle unknown status
                return;
        }
        var status_td = document.querySelector(".ewallet_modal_status");
        status_td.innerHTML = '';
        var container = document.createElement('div');
        container.style.display = 'flex';
        container.style.alignItems = 'center';
        container.style.justifyContent = 'flex-start';

        var img = document.createElement('img');
        img.src = imgSrc;
        img.width = '20';
        img.height = '20';
        img.alt = '';

        var statusSpan = document.createElement('span');
        statusSpan.className = 'fw-500 ml-1';
        statusSpan.style.color = statusColor;
        statusSpan.style.fontWeight = '500';
        statusSpan.textContent = statusText;

        container.appendChild(img);
        container.appendChild(statusSpan);
        status_td.appendChild(container);

    }


    function capitalizeStatus(str) {
        let words = str.toLowerCase().split(' ');
        for (let i = 0; i < words.length; i++) {
            words[i] = words[i].charAt(0).toUpperCase() + words[i].slice(1);
        }
        return words.join(' ');
    }


    // reset modal for fpx more icon
    var originalTrContentFPXMore = document.getElementById("modalContainerFPX").innerHTML;
    document.getElementById("modalContainerFPX").addEventListener("shown.bs.modal", function () {
        resetFpxMoreModal();
    });

    function resetFpxMoreModal() {
        var modalContent = document.querySelector("#modalContainerFPX .modal-content");
        modalContent.scrollTop = 0;
        document.getElementById("modalContainerFPX").innerHTML = originalTrContentFPXMore;
    }


    // reset modal slip for fpx slip icon
    var originalTrContentFPXSlip1111 = document.getElementById("fpxModalContainer_receipt").innerHTML;
    document.getElementById("fpxModalContainer_receipt").addEventListener("shown.bs.modal", function () {
        resetFPXSlipModal();
    });

    function resetFPXSlipModal() {
        var modalContent = document.querySelector("#fpxModalContainer_receipt .modal-content");
        modalContent.scrollTop = 0;
        document.getElementById("fpxModalContainer_receipt").innerHTML = originalTrContentFPXSlip1111;
    }


    function openFpxSlip(data) {
        resetFPXSlipModal();

        body.style.overflow = "hidden";


        // var formattedFPXtxDate= formatFPXDate(data.txDate);


        document.querySelector(".fpx-slip-amt").innerHTML = "RM " + data.txnAmount;
        document.querySelector(".fpx-slip-amt-below").innerHTML = "RM " + data.txnAmount;
        document.querySelector(".timestamp-value").innerHTML = data.timestamp.slice(0, -2);
        ;
        document.querySelector(".fpx-slip-buyerName").innerHTML = data.buyerName + ",";

        document.querySelector(".settledDate-below").innerHTML = data.txDate;
        document.querySelector(".src-ref-no").innerHTML = data.mid;
        document.querySelector(".ppid-no").innerHTML = data.tid;


        // to display address and country dynamically based on presence
        if (data.address === '' && data.country === '') {
            document.getElementById("fpx_address_Field").style.display = "none";
        }


        if (data.country === '') {
            var brElement = document.querySelector(".fpx-slip-country-br");
            if (brElement) {
                brElement.parentNode.removeChild(brElement);
            }
        } else {
            document.querySelector(".fpx-slip-country").innerHTML = data.country;
        }

        if (data.address === '') {
            var countryElement = document.querySelector(".fpx-slip-country");
            if (countryElement) {
                countryElement.parentNode.removeChild(countryElement);
            }
            var brElement = document.querySelector(".fpx-slip-country1-br");
            if (brElement) {
                brElement.parentNode.removeChild(brElement);
            }
            document.querySelector(".fpx-slip-address").innerHTML = data.country;
        } else {
            document.querySelector(".fpx-slip-address").innerHTML = data.address;
        }


        // document.querySelector(".fpx-slip-address").innerHTML = "Address : " + data.address;
        document.querySelector(".fpx-slip-phone").innerHTML = "Phone : " + data.contactNumber;
        document.querySelector(".fpx-slip-bankName").innerHTML = data.fpxSlipBankName;
        document.querySelector(".fpx-slip-transactionId").innerHTML = data.fpxTxnId;
        document.querySelector(".fpx-slip-reference").innerHTML = data.sellerOrderNo;
        document.querySelector(".fpx-slip-merchantName").innerHTML = data.merchanName;


        fpxslip_modal.style.display = "block";
        fpxmoredetails_modal.style.display = "none";

        var element = document.querySelector(".fpx_Scroll_div");
        element.scrollIntoView({block: 'start'});


    }


    function openFpxMoredetailsModal(txDate,
                                     txTime,
                                     fpxTxnId,
                                     txnAmount,
                                     paymentMethod,
                                     status,
                                     mid,
                                     tid,
                                     mdrAmt,
                                     payableAmt,
                                     settledDate,
                                     timestamp,
                                     subMerchantMID,
                                     sellerOrderNo,
                                     sellerExOrderNo,
                                     debitAuthCode,
                                     buyerName,
                                     merchanName,
                                     address,
                                     contactNumber,
                                     bankName,
                                     fpxSlipBankName,
                                     country
    ) {


        resetFpxMoreModal();

        var formattedSettledDate = formatFPXDate(settledDate);

        body.style.overflow = "hidden";

        fpxmoredetails_modal.style.display = "block";
        fpxmoredetails_modal.scrollTop = 0;


        document.querySelector(".mid-value").innerHTML = mid;
        document.querySelector(".payoutMethod-value").innerHTML = paymentMethod;
        document.querySelector(".txDate-value").innerHTML = txDate;
        document.querySelector(".txTime-value").innerHTML = txTime;
        document.querySelector(".tid-value").innerHTML = tid;
        document.querySelector(".mdrAmt-value").innerHTML = mdrAmt;
        document.querySelector(".payableAmt-value").innerHTML = payableAmt;
        document.querySelector(".settledDate-value").innerHTML = formattedSettledDate;
        document.querySelector(".subMerchantMID-value").innerHTML = subMerchantMID;
        document.querySelector(".approvalCode-value").innerHTML = fpxTxnId;
        document.querySelector(".referenceNo-value").innerHTML = sellerOrderNo;
        document.querySelector(".amt-value").innerHTML = txnAmount;

        document.querySelector(".transaction-id").innerHTML = fpxTxnId;


        var fpxStatus = document.querySelector(".transaction-status");

        if (status === 'SETTLED') {
            fpxStatus.innerHTML = `
        <div style="display: inline-flex;">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/paid.svg" width="20" height="20" alt="">
            <span class="fw-500 ml-1" style="color: #11bf36; font-weight: 500;">Settled</span>
        </div>
        `;

        } else if (status === 'NOT SETTLED') {
            fpxStatus.innerHTML = `
        <div style="display: inline-flex;">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg" width="20" height="20" alt="">
            <span class="fw-500 ml-1" style="color: orange; font-weight: 500;">Not Settled</span>
        </div>
        `;
        } else if (status === 'VOIDED') {
            fpxStatus.innerHTML = `
        <div style="display: inline-flex;">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Void.svg" width="20" height="20" alt="">
            <span class="fw-500 ml-1" style="color: gray; font-weight: 500;">Voided</span>
        </div>
        `;
        } else if (status === 'REFUNDED') {
            fpxStatus.innerHTML = `
        <div style="display: inline-flex;">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Refunded.svg" width="20" height="20" alt="">
            <span class="fw-500 ml-1" style="color: #4d7499; font-weight: 500;">Refunded</span>
        </div>
        `;
        } else if (status === 'FAILED') {
            fpxStatus.innerHTML = `
        <div style="display: inline-flex;">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Declined.svg" width="20" height="20" alt="">
            <span class="fw-500 ml-1" style="color: red; font-weight: 500;">Failed</span>
        </div>
        `;
        } else if (status === 'EZYSETTLE') {
            fpxStatus.innerHTML = `
        <div style="display: inline-flex;">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/ezysettle.svg" width="20" height="20" alt="">
            <span class="fw-500 ml-1" style="color: #005BAA; font-weight: 500;">EZYSETTLE</span>
        </div>
        `;
        } else {
            fpxStatus.innerHTML = '';
        }


        if (debitAuthCode === "00") {
            document.getElementById("receipt_fpx").classList.remove("hidecontent");
        } else {
            document.getElementById("receipt_fpx").classList.add("hidecontent");

        }


        const data = {
            txDate: txDate,
            txTime: txTime,
            fpxTxnId: fpxTxnId,
            txnAmount: txnAmount,
            paymentMethod: paymentMethod,
            status: status,
            mid: mid,
            tid: tid,
            mdrAmt: mdrAmt,
            payableAmt: payableAmt,
            settledDate: settledDate,
            timestamp: timestamp,
            subMerchantMID: subMerchantMID,
            sellerOrderNo: sellerOrderNo,
            sellerExOrderNo: sellerExOrderNo,
            buyerName: buyerName,
            merchanName: merchanName,
            address: address,
            contactNumber: contactNumber,
            bankName: bankName,
            fpxSlipBankName: fpxSlipBankName,
            country: country,
        };


        document.getElementById("fpx_slip_id").addEventListener("click", function () {
            openFpxSlip(data);
        });

    }

    // reset modal for payout more icon
    var originalTrContentPayoutMore = document.getElementById("modalContainerPayout").innerHTML;
    document.getElementById("modalContainerPayout").addEventListener("shown.bs.modal", function () {
        resetPayoutMoreModal();
    });

    function resetPayoutMoreModal() {
        var modalContent = document.querySelector("#modalContainerPayout .modal-content");
        modalContent.scrollTop = 0;
        document.getElementById("modalContainerPayout").innerHTML = originalTrContentPayoutMore;
    }


    // reset modal slip for payout slip icon
    var originalTrContentPayoutSlip = document.getElementById("payoutModalContainer_receipt").innerHTML;
    document.getElementById("payoutModalContainer_receipt").addEventListener("shown.bs.modal", function () {
        resetPayoutSlipModal();
    });

    function resetPayoutSlipModal() {
        var modalContent = document.querySelector("#payoutModalContainer_receipt .modal-content");
        modalContent.scrollTop = 0;
        document.getElementById("payoutModalContainer_receipt").innerHTML = originalTrContentPayoutSlip;
    }


	  function formatPayoutDate(dateString) {
		   // Split the input date string into day, month, and year
		    const [day, month, year] = dateString.split('/');
		    
		    // Create a new Date object using the parsed values
		    // Note: Months are 0-indexed in JavaScript (0 = January, 11 = December)
		    const date = new Date(year, month - 1, day);
		    console.log(date);
		    // Define options for formatting the date
		    const options = { day: 'numeric', month: 'long', year: 'numeric' };
		    
		    // Format the date to "23 August 2024"
		    return date.toLocaleDateString('en-GB', options);
		}
	  
   
   
	let globalTxnId=null;
   function openPayoutSlip(data) {
       // Reset modal before updating values
       /* resetPayoutSlipModal(); */

       globalTxnId = data.invoiceidproof;
		var modal = document.getElementById("payoutModalContainer_receipt");
		var modal1 =document.getElementById("closeClickOnOutside");
       // Update slip values with the provided data
        document.getElementById("new_slip_amount").innerHTML = "MYR " + data.payoutamount;
       // document.getElementById("new_slip_amount1").innerHTML = data.payoutamount;
       document.getElementById("paidDate").innerHTML =formatPayoutDate(data.paidDate);
       document.getElementById("paidTime").innerHTML = data.paidTime;

     //  document.getElementById("new_slip_timestamp").innerHTML = formatDate(data.timeStamp);
       document.getElementById("new_slip_invoiceidproff").innerHTML = data.invoiceidproof;
       document.getElementById("new_slip_merchant").innerHTML = data.createdby;
      // document.getElementById("new_slip_when").innerHTML = payoutDateNewSlip(data.payoutdate);
       document.getElementById("new_slip_paymentType").innerHTML = data.payouttype;
       document.getElementById("new_slip_recipient").innerHTML = data.payeename;
       document.getElementById("new_slip_transferAmt").innerHTML = "MYR " + data.payoutamount; 
       document.getElementById("new_slip_paymentReference").innerHTML = data.paymentReference;
       document.getElementById("new_slip_payoutId").innerHTML = data.payoutId;

       
       
       // Display the modal
       document.getElementById("payoutModalContainer_receipt").style.display = "block";
       
       // Scroll into view
       var element = document.querySelector(".payout_Scroll_div");
       element.scrollIntoView({block: 'start'});
       
       window.onclick = function(event) {
       	if (event.target === modal || event.target === modal1) {
               closeXpayModal();
           }
       };

   }

   function closeXpayModal() {
       // Close the modal
       document.getElementById("payoutModalContainer_receipt").style.display = "none";
   }



   /*  function openPayoutMoredetailsModal(payeeaccnumber,
                                        payeebankname,
                                        invoiceidproof,
                                        payoutamount,
                                        payoutstatus,
                                        timeStamp,
                                        paidTime,
                                        paidDate,
                                        submerchantMid,
                                        mmId,
                                        failurereason,
                                        payeebrn,
                                        createdby,
                                        payeename,
                                        payoutdate,
                                        srcrefno,
                                        payouttype,
                                        payoutId
    ) {


        console.log(payoutstatus);

        resetPayoutMoreModal();
        body.style.overflow = "hidden";


        var payoutDateDiv = document.createElement('div');
        payoutDateDiv.id = 'payoutDate-value-payout';
        payoutDateDiv.textContent = payoutdate;
        document.getElementById('hiddenContainer').appendChild(payoutDateDiv);


        var payoutDateDiv = document.createElement('div');
        payoutDateDiv.id = 'SrcRefNo-value-payout';
        payoutDateDiv.textContent = srcrefno;
        document.getElementById('hiddenContainer').appendChild(payoutDateDiv);


        var payoutTyepDiv = document.createElement('div');
        payoutTyepDiv.id = 'payouttype-value-payout';
        payoutTyepDiv.textContent = payouttype;
        document.getElementById('hiddenContainer').appendChild(payoutTyepDiv);


        var payoutStatusForPayout = document.querySelector(".payoutstatus-value-payout");

        if (payoutstatus === 'Paid') {
            payoutStatusForPayout.innerHTML = `
        <div style="display: inline-flex;">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/paid.svg" width="20" height="20" alt="">
            <span class="fw-500 ml-1" style="color: #11bf36; font-weight: 500;">Paid</span>
        </div>
        `;
            document.getElementById("receipt_payout").classList.remove("hidecontent");

        } else if (payoutstatus === 'Pending') {
            console.log("hitting pending");
            payoutStatusForPayout.innerHTML = `
        <div style="display: inline-flex;">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg" width="20" height="20" alt="">
            <span class="fw-500 ml-1" style="color: orange; font-weight: 500;">Pending</span>
        </div>
        `;
            document.getElementById("receipt_payout").classList.remove("hidecontent");
        } else if (payoutstatus === 'Declined') {
            payoutStatusForPayout.innerHTML = `
          <div style="display: inline-flex;">
            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/Declined.svg" width="20" height="20" alt="">
            <span class="fw-500 ml-1" style="color: red; font-weight: 500;">Declined</span>
        </div>
        `;
            document.getElementById("receipt_payout").classList.add("hidecontent");
        } else {
            console.log("error");
        }

        if (payoutstatus === 'Declined') {
            document.getElementById("payout_decline_reason_tr").classList.remove("hidecontent");
        } else if (payoutstatus === 'Paid') {
            document.getElementById("payout_decline_reason_tr").classList.add("hidecontent");
        } else if (payoutstatus === 'Requested') {
            document.getElementById("payout_decline_reason_tr").classList.remove("hidecontent");
        } else if (payoutstatus === 'Pending') {
            document.getElementById("payout_decline_reason_tr").classList.add("hidecontent");
        } else {
            return;
        }


        document.querySelector(".payeeaccnumber-value-payout").innerHTML = payeeaccnumber;
        document.querySelector(".payeebankname-value-payout").innerHTML = payeebankname;
        document.querySelector(".invoiceidproof-value-payout").innerHTML = invoiceidproof;
        document.querySelector(".payoutId-value-payout").innerHTML = payoutId;
        document.querySelector(".payoutamount-value-payout").innerHTML = payoutamount;
        document.querySelector(".timeStamp-value-payout").innerHTML = timeStamp.replace(".0", "");
        document.querySelector(".paidTime-value-payout").innerHTML = paidTime;
        document.querySelector(".paidDate-value-payout").innerHTML = paidDate;
        document.querySelector(".submerchantMid-value-payout").innerHTML = submerchantMid;
        document.querySelector(".mmId-value-payout").innerHTML = mmId;
        document.querySelector(".failurereason-value-payout").innerHTML = failurereason;
        document.querySelector(".payeebrn-value-payout").innerHTML = payeebrn;
        document.querySelector(".createdby-value-payout").innerHTML = createdby;
        document.querySelector(".payeename-value-payout").innerHTML = payeename;
        document.querySelector(".payout_date_date").innerHTML = payoutdate;

        payoutmoredetails_modal.style.display = "block";
        payoutmoredetails_modal.scrollTop = 0;

        const data = {
            payeeaccnumber: payeeaccnumber,
            payeebankname: payeebankname,
            invoiceidproof: invoiceidproof,
            payoutamount: payoutamount,
            payoutstatus: payoutstatus,
            timeStamp: timeStamp,
            paidTime: paidTime,
            paidDate: paidDate,
            submerchantMid: submerchantMid,
            mmId: mmId,
            failurereason: failurereason,
            payeebrn: payeebrn,
            createdby: createdby,
            payeename: payeename,
            payoutdate: payoutdate,
            srcrefno: srcrefno,
            payouttype: payouttype
        };

        document.getElementById("payoutSlip-id").addEventListener("click", function () {
            openPayoutSlip(data);
        });


    }
 */
 
 
 
 // new change k
 function openPayoutMoredetailsModal(payeeaccnumber,
         payeebankname,
         invoiceidproof,
         payoutamount,
         payoutstatus,
         timeStamp,
         paidTime,
         paidDate,
         submerchantMid,
         mmId,
         failurereason,
         payeebrn,
         createdby,
         payeename,
         payoutdate,
         srcrefno,
         payouttype,
         payoutId,
         paymentReference
) {



    resetPayoutMoreModal();
    body.style.overflow = "hidden";


    var payoutDateDiv = document.createElement('div');
    payoutDateDiv.id = 'payoutDate-value-payout';
    payoutDateDiv.textContent = payoutdate;
    document.getElementById('hiddenContainer').appendChild(payoutDateDiv);


    var payoutDateDiv = document.createElement('div');
    payoutDateDiv.id = 'SrcRefNo-value-payout';
    payoutDateDiv.textContent = srcrefno;
    document.getElementById('hiddenContainer').appendChild(payoutDateDiv);


    var payoutTyepDiv = document.createElement('div');
    payoutTyepDiv.id = 'payouttype-value-payout';
    payoutTyepDiv.textContent = payouttype;
    document.getElementById('hiddenContainer').appendChild(payoutTyepDiv);


    var payoutStatusForPayout = document.querySelector(".payoutstatus-value-payout");

    if (payoutstatus === 'Paid') {
        payoutStatusForPayout.innerHTML = `
    <div style="display: inline-flex;">
        <img src="${pageContext.request.contextPath}/resourcesNew1/assets/paid.svg" width="20" height="20" alt="">
        <span class="fw-500 ml-1" style="color: #11bf36; font-weight: 500;">Paid</span>
    </div>
    `;
        document.getElementById("receipt_payout").classList.remove("hidecontent");

    } else if (payoutstatus === 'Pending') {
        console.log("hitting pending");
        payoutStatusForPayout.innerHTML = `
    <div style="display: inline-flex;">
        <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg" width="20" height="20" alt="">
        <span class="fw-500 ml-1" style="color: orange; font-weight: 500;">Pending</span>
    </div>
    `;
        document.getElementById("receipt_payout").classList.add("hidecontent");
    } else if (payoutstatus === 'In Process') {
        console.log("hitting pending");
        payoutStatusForPayout.innerHTML = `
    <div style="display: inline-flex;">
        <img src="${pageContext.request.contextPath}/resourcesNew1/assets/on_process.svg" width="20" height="20" alt="">
        <span class="fw-500 ml-1" style="color: #8A8A8A; font-weight: 500;">In Process</span>
    </div>
    `;
        document.getElementById("receipt_payout").classList.add("hidecontent");
    } else if (payoutstatus === 'Declined') {
        payoutStatusForPayout.innerHTML = `
      <div style="display: inline-flex;">
        <img src="${pageContext.request.contextPath}/resourcesNew1/assets/Declined.svg" width="20" height="20" alt="">
        <span class="fw-500 ml-1" style="color: red; font-weight: 500;">Declined</span>
    </div>
    `;
        document.getElementById("receipt_payout").classList.add("hidecontent");
    } else {
        console.log("error");
    }

    if (payoutstatus === 'Declined') {
        document.getElementById("payout_decline_reason_tr").classList.remove("hidecontent");
    } else if (payoutstatus === 'Paid') {
        document.getElementById("payout_decline_reason_tr").classList.add("hidecontent");
    } else if (payoutstatus === 'Requested') {
        document.getElementById("payout_decline_reason_tr").classList.add("hidecontent");
    } else if (payoutstatus === 'Pending') {
        document.getElementById("payout_decline_reason_tr").classList.add("hidecontent");
    } else if (payoutstatus === 'In Process') {
        document.getElementById("payout_decline_reason_tr").classList.add("hidecontent");
    } else {
        return;
    }


    document.querySelector(".payeeaccnumber-value-payout").innerHTML = payeeaccnumber;
    document.querySelector(".payeebankname-value-payout").innerHTML = payeebankname;
    document.querySelector(".invoiceidproof-value-payout").innerHTML = invoiceidproof;
    document.querySelector(".payoutId-value-payout").innerHTML = payoutId;
    document.querySelector(".payoutamount-value-payout").innerHTML = payoutamount;
    document.querySelector(".timeStamp-value-payout").innerHTML = timeStamp.replace(".0","");
    document.querySelector(".paidTime-value-payout").innerHTML = paidTime;
    document.querySelector(".paidDate-value-payout").innerHTML = paidDate;
    document.querySelector(".submerchantMid-value-payout").innerHTML = submerchantMid;
    document.querySelector(".mmId-value-payout").innerHTML = mmId;
    document.querySelector(".failurereason-value-payout").innerHTML = failurereason;
    document.querySelector(".payeebrn-value-payout").innerHTML = payeebrn;
    document.querySelector(".createdby-value-payout").innerHTML = createdby;
    document.querySelector(".payeename-value-payout").innerHTML = payeename;
    document.querySelector(".payout_date_date").innerHTML = payoutdate;

    payoutmoredetails_modal.style.display = "block";
    payoutmoredetails_modal.scrollTop = 0;

    const data = {
    		  payeeaccnumber: payeeaccnumber,
    	        payeebankname: payeebankname,
    	        invoiceidproof: invoiceidproof,
    	        payoutamount: payoutamount,
    	        payoutstatus: payoutstatus,
    	        timeStamp: timeStamp,
    	        paidTime: paidTime,
    	        paidDate: paidDate,
    	        submerchantMid: submerchantMid,
    	        mmId: mmId,
    	        failurereason: failurereason,
    	        payeebrn: payeebrn,
    	        createdby: createdby,
    	        payeename: payeename,
    	        payoutdate: payoutdate,
    	        srcrefno: srcrefno,
    	        payouttype: payouttype,
    	        paymentReference: paymentReference,
    	        payoutId: payoutId
    };

    document.getElementById("payoutSlip-id").addEventListener("click", function () {
        openPayoutSlip(data);
    });


}
    function openReceiptModal() {
        slip_modal.style.display = "block";
        moredetails_modal.style.display = "none";
        slip_modal.scrollTop = 0;
    }


    function openDeclineReasonModal(failurereason, txnType) {
        var scrollbarWidth = window.innerWidth - document.documentElement.clientWidth;
        body.style.paddingRight = scrollbarWidth + "px";
        body.style.overflow = "hidden";

        document.querySelector(".payout_decline_reason").innerHTML = failurereason;
        declinereason_modal.style.display = "block";
        declinereason_modal.scrollTop = 0;
    }

    
    function openPendingModal(failurereason) {
        var scrollbarWidth = window.innerWidth - document.documentElement.clientWidth;
        body.style.paddingRight = scrollbarWidth + "px";
        body.style.overflow = "hidden";

        document.querySelector(".payout_pending_reason").innerHTML = failurereason;
        document.getElementById("modalContainer_pendingreason").style.display = "block";
        document.getElementById("modalContainer_pendingreason").scrollTop = 0;
    }


      function closePendingReason() {
        body.style.paddingRight = 0;
        body.style.overflow = initialOverflow;
        document.getElementById("modalContainer_pendingreason").style.display = "none";
    }
    
    function closeCard() {
        body.style.overflow = initialOverflow;
        moredetails_modal.style.display = "none";
    }

    function closeEwallet() {
        body.style.overflow = initialOverflow;

        ewalletmoredetails_modal.scrollTop = 0;
        ewalletmoredetails_modal.style.display = "none";
    }

    function closeFPX() {
        body.style.overflow = initialOverflow;
        fpxmoredetails_modal.style.display = "none";
    }

    function closePayout() {
        body.style.overflow = initialOverflow;
        payoutmoredetails_modal.style.display = "none";

    }

    https://mobiversa-my.sharepoint.com/personal/umar_gomobi_io/_layouts/15/onedrive.aspx?id=%2Fpersonal%2Fumar%5Fgomobi%5Fio%2FDocuments%2FMicrosoft%20Teams%20Chat%20Files%2FFrame%2040160%201%2Epng&parent=%2Fpersonal%2Fumar%5Fgomobi%5Fio%2FDocuments%2FMicrosoft%20Teams%20Chat%20Files&ga=1
        // old
        //
        // function closeDeclinedReason() {
        //     body.style.overflow = initialOverflow;
        //     declinereason_modal.style.display = "none";
        // }

        function closeDeclinedReason() {
            body.style.paddingRight = 0;
            body.style.overflow = initialOverflow;
            declinereason_modal.style.display = "none";
        }


    function openCardSlip(data) {


        document.querySelector(".card_top_amount").innerHTML = "RM " + data.transactionAmount;

        var formattedTimeStamp = formatDate(data.timestamp)
        document.querySelector(".card_top_timestamp").innerHTML = formattedTimeStamp;

        document.querySelector(".card_slip_merchantName").innerHTML = "to " + data.merchantName;
        document.querySelector(".card_slip_amount").innerHTML = "RM " + data.transactionAmount;
        // document.querySelector(".card_Slip_address").innerHTML ="Address : "+data.address;
        document.querySelector(".card_Slip_contactNumber").innerHTML = "Phone : " + data.contactNumber;
        document.querySelector(".card_slip_mid").innerHTML = data.mid;
        document.querySelector(".card_slip_tid").innerHTML = data.tid;
        document.querySelector(".card_slip_cardHolder").innerHTML = data.cardHolderName;
        document.querySelector(".card_slip_cardNumber").innerHTML = data.cardNumber;
        document.querySelector(".card_slip_cardType").innerHTML = data.cardType;
        document.querySelector(".card_slip_rrn").innerHTML = data.rrn;
        document.querySelector(".card_slip_approvalCode").innerHTML = data.approvalCode;
        document.querySelector(".card_slip_reference").innerHTML = data.refereceNo;
        document.querySelector(".card_slip_3d_secure_auth").innerHTML = data.auth3Ds;
        document.querySelector(".card_slip_holder").innerHTML = data.cardHolderName + " ,";
        document.querySelector(".card_slip_merchantNameSlip").innerHTML = data.merchantName;


        if (data.address === '' && data.country === '') {
            document.getElementById("card_address_Field").style.display = "none";
        }


        if (data.country === '') {
            var brElement = document.querySelector(".card-slip-country-br");
            if (brElement) {
                brElement.parentNode.removeChild(brElement);
            }
        } else {
            document.querySelector(".card-slip-country").innerHTML = data.country;
        }

        if (data.address === '') {
            var countryElement = document.querySelector(".card-slip-country");
            if (countryElement) {
                countryElement.parentNode.removeChild(countryElement);
            }
            var brElement = document.querySelector(".card-slip-country1-br");
            if (brElement) {
                brElement.parentNode.removeChild(brElement);
            }
            document.querySelector(".card-slip-address").innerHTML = data.country;
        } else {
            document.querySelector(".card-slip-address").innerHTML = data.address;
        }


        cardslip_modal.style.display = "block";
        moredetails_modal.style.display = "none";


        var element = document.querySelector(".outer_header");
        element.scrollIntoView({block: 'start'});

    }

    function closeCardSlip() {
        cardslip_modal.style.display = "none";
        moredetails_modal.style.display = "block";
    }


    function closeEwalletSlip() {
        ewalletslip_modal.style.display = "none";
        ewalletmoredetails_modal.style.display = "block";
    }


    function openEwalletSlip(data) {

        if (data.paymentMethod === "Touch N go") {
            data.paymentMethod = "Touch'N_Go";
        }


        ewalletslip_modal.style.display = "block";
        ewalletmoredetails_modal.style.display = "none";


        document.querySelector(".ewallet-slip-amount").innerHTML = "RM " + data.transactionAmount;
        if (data.timeStamp !== '') {
            var formattedTimeStamp = formatDate(data.timeStamp);
        }

        if (data.timeStamp !== '') {
            document.querySelector(".ewallet_slip_timestamp").innerHTML = formattedTimeStamp;
        }

        document.querySelector(".ewallet_slip_payment_text").innerHTML = "RM " + data.transactionAmount;
        document.querySelector(".ewallet_slip_merchantNameName").innerHTML = data.merchantName;
        document.querySelector(".ewallet_slip_merchantName_desc").innerHTML = data.merchantName;
        //
        // document.querySelector(".ewallet_slip_merchant_name").innerHTML = data.merchantName;
        // document.querySelector(".ewallet_slip_address").innerHTML ="Address : "+ data.address;
        document.querySelector(".ewallet_slip_phone").innerHTML = "Phone : " + data.contactNumber;
        document.querySelector(".ewallet_slip_mid").innerHTML = data.mid;
        document.querySelector(".ewallet_slip_tid").innerHTML = data.tid;
        document.querySelector(".ewallet_slip_rrn").innerHTML = data.rrn;
        document.querySelector(".ewallet_slip_approvalCode").innerHTML = data.approvalCode;
        document.querySelector(".ewallet_slip_reference").innerHTML = data.referanceNo;


        if (data.paymentMethod.toUpperCase() === "BOOST" || data.paymentMethod.toUpperCase() === "GRAB") {
            document.getElementById("rrn_for_Boost_Grab").style.display = "flex";
        } else {
            document.getElementById("rrn_for_Boost_Grab").style.display = "none";
        }


        if (data.paymentMethod.toUpperCase() === "SHOPEEPAY") {
            document.querySelector(".ewallet_slip_top_type").innerHTML = "Shopeepay Sale";
        } else if (data.paymentMethod.toUpperCase() === "BOOST") {
            document.querySelector(".ewallet_slip_top_type").innerHTML = "Boost Sale";
        } else if (data.paymentMethod.toUpperCase() === "TOUCH'N_GO") {
            document.querySelector(".ewallet_slip_top_type").innerHTML = "Touch N Go Sale";
        } else if (data.paymentMethod.toUpperCase() === "GRAB") {
            document.querySelector(".ewallet_slip_top_type").innerHTML = "Grab Sale";
        } else {
            document.querySelector(".ewallet_slip_top_type").innerHTML = "";

        }

        var element = document.querySelector(".ewallet_Scroll_div");
        element.scrollIntoView({block: 'start'});


        // to display address and country dynamically based on presence
        if (data.address === '' && data.country === '') {
            document.getElementById("ewallet_address_Field").style.display = "none";
        }


        if (data.country === '') {
            var brElement = document.querySelector(".ewallet-slip-country-br");
            if (brElement) {
                brElement.parentNode.removeChild(brElement);
            }
        } else {
            document.querySelector(".ewallet-slip-country").innerHTML = data.country;
        }

        if (data.address === '') {
            var countryElement = document.querySelector(".ewallet-slip-country");
            if (countryElement) {
                countryElement.parentNode.removeChild(countryElement);
            }
            var brElement = document.querySelector(".ewallet-slip-country1-br");
            if (brElement) {
                brElement.parentNode.removeChild(brElement);
            }
            document.querySelector(".ewallet-slip-address").innerHTML = data.country;
        } else {
            document.querySelector(".ewallet-slip-address").innerHTML = data.address;
        }


    }


    function closeFpxSlip() {
        payoutslip_modal.style.display = "none";
        fpxslip_modal.style.display = "none";
        fpxmoredetails_modal.style.display = "block";
    }


    function formatDate(dateString) {

        var parts = dateString.split(" ");
        var datePart = parts[0].split("/");
        var timePart = parts[1];
        var formattedDateString = datePart[2] + "-" + datePart[1] + "-" + datePart[0] + " " + timePart; // Added space before time
        var date = new Date(formattedDateString);


        var monthNames = ["January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"];

        var day = date.getDate();
        var monthIndex = date.getMonth();

        var year = date.getFullYear();
        var hours = date.getHours();
        var minutes = date.getMinutes();
        var seconds = date.getSeconds();
        hours = hours < 10 ? '0' + hours : hours;
        minutes = minutes < 10 ? '0' + minutes : minutes;
        seconds = seconds < 10 ? '0' + seconds : seconds;
        // var formattedDate = day+"-"+ monthNames[monthIndex].substring(0, 3) + "-" + year + ", " + " " + hours + ":" + minutes + ":" + seconds;
        var formattedDate = (day < 10 ? '0' + day : day) + "-" + monthNames[monthIndex].substring(0, 3) + "-" + year + ", " + " " + hours + ":" + minutes + ":" + seconds;

        return formattedDate;
    }

    function formatFPXDate(dateString) {

        var parts = dateString.split("-");
        var day = parseInt(parts[0]);
        var monthAbbreviation = parts[1];
        var year = parseInt(parts[2]);
        var monthNames = ["Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
        var monthIndex = monthNames.indexOf(monthAbbreviation);
        var date = new Date(year, monthIndex, day);

        var formattedDateFPXDate = (day < 10 ? '0' + day : day) + "/" + (monthIndex + 1 < 10 ? '0' + (monthIndex + 1) : monthIndex + 1) + "/" + year;

        return formattedDateFPXDate;
    }


    function closePayoutSlip() {
        payoutslip_modal.style.display = "none";
        payoutmoredetails_modal.style.display = "block";
    }


    function validateForm() {
        $("#overlay-popup").show();

        var form = document.getElementById("RequestForm");
        var transactionIds = document.getElementById("transaction_ids").value.trim();
        var paymentMethod = document.getElementById("payment_method").value;
        var search_type = document.getElementById("search_type").value;
        var payment_type = document.getElementById("payment_type").value;
        var regex = /^[a-zA-Z0-9_,\s]*$/;

        var chooseType_card = document.getElementById("search_type_card").value;
        var chooseType_empty = document.getElementById("search_type_empty").value;
        var chooseType_fpx = document.getElementById("search_type_fpx").value;
        var chooseType_payout = document.getElementById("search_type_payout").value;
        var chooseType_boostgrab = document.getElementById("search_type_ewallet_boost_grab").value;
        var chooseType_tngspp = document.getElementById("search_type_ewallet_tng_spp").value;


        // if (paymentMethod === "Card") {
        //     if (chooseType_empty === "" && chooseType_card === "" && chooseType_fpx === "" && chooseType_boostgrab === "" && chooseType_tngspp === "") {
        //         alert("Please choose any option in choose type");
        //         return false;
        //     } else if (transactionIds === "") {
        //         alert("Please enter valid transaction id's to search");
        //         return false;
        //     }
        //     // } else if (paymentMethod === "FPX") {
        //     //     if (chooseType_empty === "" && chooseType_card === "" && chooseType_fpx === "" && chooseType_boostgrab === "" && chooseType_tngspp === "") {
        //     //         alert("Please choose any option in choose type");
        //     //         return false;
        //     //     } else if (transactionIds === "") {
        //     //         alert("Please enter valid transaction id's to search");
        //     //         return false;
        //     //     }
        //     // }

        if (paymentMethod === "Card") {
            if (chooseType_empty === "" && chooseType_card === "" && chooseType_fpx === "" && chooseType_boostgrab === "" && chooseType_tngspp === "" && chooseType_payout === '') {
                alert("Please choose any option in choose type");
                return false;
            } else if (transactionIds === "") {
                if (chooseType_card.toUpperCase() === 'REFERENCENO') {
                    alert("Please enter the reference no to search");
                    return false;
                } else if (chooseType_card.toUpperCase() === 'APPROVALCODE') {
                    alert("Please enter the approval code to search");
                    return false;
                } else if (chooseType_card.toUpperCase() === 'CARDNUMBER') {
                    alert("Please enter the Card number to search");
                    return false;
                } else {
                    alert("Please enter the RRN to search.");
                    return false;
                }
            }
        } else if (paymentMethod === "FPX") {
            if (chooseType_empty === "" && chooseType_card === "" && chooseType_fpx === "" && chooseType_boostgrab === "" && chooseType_tngspp === "" && chooseType_payout === '') {
                alert("Please choose any option in choose type");
                return false;
            } else if (transactionIds === "") {
                if (chooseType_fpx.toUpperCase() === 'REFERENCENO') {
                    alert("Please enter the Reference no to search");
                    return false;
                } else {
                    alert("Please enter the approval code to search");
                    return false;

                }
                // alert("Please enter valid transaction id's to search");
                // return false;
            }
        }
            // else if (paymentMethod === "Ewallet") {
            //     if (payment_type === "") {
            //         alert("Please Choose Payment Type...");
            //         return false;
            //     } else if (chooseType_empty === "" && chooseType_card === "" && chooseType_fpx === "" && chooseType_boostgrab === "" && chooseType_tngspp === "") {
            //         alert("Please choose any option in choose type");
            //         return false;
            //     } else if (transactionIds === "") {
            //         alert("Please enter valid transaction id's to search");
            //         return false;
            //     }
            //
        // }
        else if (paymentMethod === "Ewallet") {
            if (payment_type === "") {
                alert("Please Choose Payment Type...");
                return false;
            } else if (chooseType_empty === "" && chooseType_card === "" && chooseType_fpx === "" && chooseType_boostgrab === "" && chooseType_tngspp === "" && chooseType_payout === '') {
                alert("Please choose any option in choose type");
                return false;
            } else if (transactionIds === "") {
                if (chooseType_tngspp.toUpperCase() === 'REFERENCE_NO' || chooseType_boostgrab.toUpperCase() === 'REFERENCE_NO') {
                    alert("Please enter the Reference no to search");
                    return false;
                } else if (chooseType_tngspp.toUpperCase() === 'APPROVAL_CODE' || chooseType_boostgrab.toUpperCase() === 'APPROVAL_CODE') {
                    alert("Please enter the aproval code to search");
                    return false;
                } else if (chooseType_boostgrab.toUpperCase() === 'RRN') {
                    alert("please enter the RRN to search");
                    return false;
                }
            }
        } else if (paymentMethod === "Payout") {
            if (chooseType_empty === "" && chooseType_card === "" && chooseType_fpx === "" && chooseType_boostgrab === "" && chooseType_tngspp === "" && chooseType_payout === '') {
                alert("please choose type");
                return false;
            }
            if (transactionIds === "") {

                if (chooseType_payout === 'Transaction_Id') {
                    alert("Please Enter Valid Transaction ID");
                    return false;
                } else if (chooseType_payout === 'Payout_Id') {
                    alert("Please Enter Valid Payout ID");
                    return false;
                }

            }
        } else if (paymentMethod === "") {
            alert("Please fill required fields..");
            return false;
        }

        if (!regex.test(transactionIds)) {
            alert("No special symbols allowed");
            return false;
        }

        // let input = document.getElementById('transaction_ids').value;
        let input = document.getElementById('transaction_ids').value;
        let dataArray = input.split(',').map(item => item.trim());


        if (dataArray.length > 20) {
            alert("Please enter up to 20 transaction IDs separated by commas");
            return false;
        }


        var uniqueness = true;

        let uniqueValues = new Set(dataArray);


        if (uniqueValues.size === dataArray.length) {

            if (paymentMethod === 'FPX') {
                document.getElementById("overlay").style.display = "block";
                form.action = "${pageContext.request.contextPath}/searchNew/fpxMasterSearch";
            } else if (paymentMethod === 'Payout') {
                document.getElementById("overlay").style.display = "block";
                form.action = "${pageContext.request.contextPath}/searchNew/payoutMasterSearch";
            } else if (paymentMethod === 'Ewallet') {
                document.getElementById("overlay").style.display = "block";
                form.action = "${pageContext.request.contextPath}/searchNew/ewalletMasterSearch";
            } else if (paymentMethod === 'Card') {
                document.getElementById("overlay").style.display = "block";
                form.action = "${pageContext.request.contextPath}/searchNew/cardMasterSearch";
            }


            document.getElementById("dataArray").value = JSON.stringify(dataArray);


            localStorage.setItem("searchIds", dataArray);

            return true;

        } else
            // {
            // // alert("Duplicate values not allowed")
            // let duplicateValues = dataArray.filter((item, index) => dataArray.indexOf(item) !== index);
            // alert("Duplicate values not allowed: " + Array.from(new Set(duplicateValues)).join(", "));
            // return false;
            // }


            // {
            //     let duplicateValues = dataArray.filter((item, index) => dataArray.indexOf(item) !== index);
            //     let countMap = new Map();
            //     duplicateValues.forEach(item => {
            //         // Convert item to number before using it as a key in the map
            //         let numItem = Number(item);
            //         countMap.set(numItem, (countMap.get(numItem) || 0) + 1);
            //     });
            //
            //     var message = "Do not repeate search ID's \nDuplicate values found:  ";
            //     countMap.forEach((value, key) => {
            //         // message += key +" "+" "+"("+value+")" +" ";
            //         message += key + "(" + value +")  ";
            //     });
            //
            //     if (countMap.size > 0) {
            //         alert(message.trim());
            //     } else {
            //         alert("No duplicate values found.");
            //     }
            //     return false;
            // }
        {
            let duplicateValues = dataArray.filter((item, index) => dataArray.indexOf(item) !== index);
            let countMap = new Map();
            duplicateValues.forEach(item => {
                // Convert item to string before using it as a key in the map
                let key = typeof item === 'string' ? item : JSON.stringify(item);
                countMap.set(key, (countMap.get(key) || 0) + 1);
            });

            var message = "Do not repeat search ID's \nDuplicate values found:  ";
            countMap.forEach((value, key) => {

                message += key + "(" + value + ")  ";
            });

            if (countMap.size > 0) {
                alert(message.trim());
            } else {
                alert("No duplicate values found.");
            }
            return false;
        }


    }

    $("#overlay-popup").hide();


    document.addEventListener("DOMContentLoaded", function () {

            var paymentMethodSelect = document.getElementById("payment_method");


            var searchOption;

            var chooseType_card = document.getElementById("search_type_col_card");
            var chooseType_empty = document.getElementById("search_type_col_empty");
            var chooseType_fpx = document.getElementById("search_type_col_fpx");
            var chooseType_boostgrab = document.getElementById("search_type_col_ewallet_grabboost");
            var chooseType_tngspp = document.getElementById("search_type_col_tngspp");
            var chooseType_payout = document.getElementById("search_type_col_payout");


            //
            // if (!(chooseType_card.classList.contains("hide"))) {
            //     searchOption = chooseType_card;
            // } else if (!(chooseType_fpx.classList.contains("hide"))) {
            //     searchOption = chooseType_fpx;
            // } else if (!(chooseType_boostgrab.classList.contains("hide"))) {
            //     searchOption = chooseType_boostgrab;
            // } else if (!(chooseType_tngspp.classList.contains("hide"))) {
            //     searchOption = chooseType_tngspp;
            // }


            handlePaymentMethodChange(paymentMethodSelect);


            document.getElementById("payment_method").addEventListener("change", function () {

                handlePaymentMethodChange(paymentMethodSelect);
                if (paymentMethodSelect.value === "Ewallet") {
                    handleEwalletOptionChange(chooseType_empty, chooseType_card, chooseType_fpx, chooseType_boostgrab, chooseType_tngspp, chooseType_payout);
                }

                toggleOptions();
            });

            document.getElementById("payment_type").addEventListener("change", function () {
                handleEwalletOptionChange(chooseType_empty, chooseType_card, chooseType_fpx, chooseType_boostgrab, chooseType_tngspp, chooseType_payout);


            });

            function handlePaymentMethodChange(paymentMethodSelect) {

                var val = paymentMethodSelect.value;


                var payment_type_value = document.getElementById("payment_type");
                var search_type_col = document.getElementById(" search_type_col");
                var searchInput_div = document.getElementById("searchinput_div");
                var searchbtn_div = document.getElementById("search_div");

                var chooseType_empty = document.getElementById("search_type_col_empty");
                var chooseType_card = document.getElementById("search_type_col_card");
                var chooseType_fpx = document.getElementById("search_type_col_fpx");
                var chooseType_boostgrab = document.getElementById("search_type_col_ewallet_grabboost");
                var chooseType_tngspp = document.getElementById("search_type_col_tngspp");
                var chooseType_payout = document.getElementById("search_type_col_payout");


                if (val == "Card") {
                    search_type_col.style.display = "block";
                    payment_type_col.classList.add('hide');

                    chooseType_empty.classList.add('hide');
                    chooseType_card.classList.remove('hide');
                    chooseType_fpx.classList.add('hide');
                    chooseType_boostgrab.classList.add('hide');
                    chooseType_tngspp.classList.add('hide');
                    chooseType_payout.classList.add('hide');

                    searchInput_div.classList.add('l4');
                    searchInput_div.classList.remove('l3');

                    searchbtn_div.classList.remove('l12', 'm12');
                    searchbtn_div.classList.add('l2', 'm3');

                    // backEndChooseType = backEndChooseType ? backEndChooseType : "";
                    if (backEndChooseType) {

                        $("#search_type_col #search_type_col_card .select-dropdown").val(backEndChooseType);
                        M.updateTextFields();

                        if (backEndChooseType === 'ReferenceNo') {
                            $("#search_type_card").prop("selectedIndex", 1);
                        } else if (backEndChooseType === 'ApprovalCode') {
                            $("#search_type_card").prop("selectedIndex", 2);
                        } else if (backEndChooseType === 'RRN') {
                            $("#search_type_card").prop("selectedIndex", 3);
                        } else if (backEndChooseType === 'CardNumber') {
                            $("#search_type_card").prop("selectedIndex", 4);
                        } else {
                            $("#search_type_card").prop("selectedIndex", 0);

                        }
                    }


                } else if (val == "FPX") {
                    search_type_col.style.display = "block";

                    payment_type_col.classList.add('hide');

                    chooseType_empty.classList.add('hide');
                    chooseType_card.classList.add('hide');
                    chooseType_fpx.classList.remove('hide');
                    chooseType_boostgrab.classList.add('hide');
                    chooseType_tngspp.classList.add('hide');
                    chooseType_payout.classList.add('hide');


                    searchInput_div.classList.add('l4');
                    searchInput_div.classList.remove('l3');

                    searchbtn_div.classList.remove('l12', 'm12');
                    searchbtn_div.classList.add('l2', 'm3');


                    if (backEndChooseType) {

                        $("#search_type_col  #search_type_col_fpx .select-dropdown").val(backEndChooseType);
                        M.updateTextFields();

                        if (backEndChooseType.toUpperCase() === 'REFERENCENO') {
                            $("#search_type_fpx").prop("selectedIndex", 1);
                        } else if (backEndChooseType.toUpperCase() === 'APPROVALCODE') {
                            $("#search_type_fpx").prop("selectedIndex", 2);
                        } else {
                            $("#search_type_card").prop("selectedIndex", 0);

                        }

                    }


                } else if (val == "Ewallet") {
                    payment_type_col.classList.remove('hide');
                    search_type_col.style.display = "block";

                    handleEwalletOptionChange(chooseType_empty, chooseType_card, chooseType_fpx, chooseType_boostgrab, chooseType_tngspp);

                    searchInput_div.classList.add('l3');
                    searchInput_div.classList.remove('l4');

                    searchbtn_div.classList.add('l12', 'm12');
                    searchbtn_div.classList.remove('l2', 'm3');
                    chooseType_payout.classList.add('hide');


                    if (backEndEwalletPaymentType) {
                        $("#payment_type_col .select-dropdown").val(backEndEwalletPaymentType);
                        M.updateTextFields();
                        if (backEndEwalletPaymentType.toUpperCase() === 'BOOST') {

                            $("#payment_type").prop("selectedIndex", 1);
                        } else if (backEndEwalletPaymentType.toUpperCase() === 'GRAB') {
                            $("#payment_type").prop("selectedIndex", 2);
                        } else if (backEndEwalletPaymentType.toUpperCase() === "TOUCH'N_GO") {
                            $("#payment_type").prop("selectedIndex", 3);
                        } else if (backEndEwalletPaymentType.toUpperCase() === "SHOPEEPAY") {
                            $("#payment_type").prop("selectedIndex", 4);
                        } else {
                            $("#payment_type_col  .select-dropdown").val("Choose Payment Type");
                            M.updateTextFields();
                            $("#payment_type").prop("selectedIndex", 0);
                        }


                    }


                    if (backEndChooseType) {


                        if ((payment_type_value.value).toUpperCase() === "BOOST" || (payment_type_value.value).toUpperCase() === "GRAB") {

                            $("#search_type_col #search_type_col_ewallet_grabboost .select-dropdown").val(backEndChooseType);
                            M.updateTextFields();


                            if (backEndChooseType.toUpperCase() === 'REFERENCE_NO') {
                                $("#search_type_ewallet_boost_grab").prop("selectedIndex", 1);
                            } else if (backEndChooseType.toUpperCase() === 'APPROVAL_CODE') {
                                $("#search_type_ewallet_boost_grab").prop("selectedIndex", 2);
                            } else if (backEndChooseType.toUpperCase() === 'RRN') {
                                $("#search_type_ewallet_boost_grab").prop("selectedIndex", 3);
                            }

                        } else if ((payment_type_value.value).toUpperCase() === "TOUCH'N_GO" || (payment_type_value.value).toUpperCase() === "SHOPEEPAY") {


                            $("#search_type_col #search_type_col_tngspp .select-dropdown").val(backEndChooseType);
                            M.updateTextFields();


                            if (backEndChooseType.toUpperCase() === 'REFERENCE_NO') {
                                $("#search_type_ewallet_tng_spp").prop("selectedIndex", 1);
                            } else if (backEndChooseType.toUpperCase() === 'APPROVAL_CODE') {
                                $("#search_type_ewallet_tng_spp").prop("selectedIndex", 2);
                            }


                        }
                    }
                } else if (val == "Payout") {

                    search_type_col.style.display = "block";

                    payment_type_col.classList.add('hide');

                    chooseType_empty.classList.add('hide');
                    chooseType_card.classList.add('hide');
                    chooseType_fpx.classList.add('hide');
                    chooseType_boostgrab.classList.add('hide');
                    chooseType_tngspp.classList.add('hide');
                    chooseType_payout.classList.remove('hide');


                    searchInput_div.classList.add('l4');
                    searchInput_div.classList.remove('l3');

                    searchbtn_div.classList.remove('l12', 'm12');
                    searchbtn_div.classList.add('l2', 'm3');


                    if (backEndChooseType) {

                        $("#search_type_col  #search_type_col_payout .select-dropdown").val(backEndChooseType);
                        M.updateTextFields();

                        if (backEndChooseType.toUpperCase() === 'TRANSACTION_ID') {
                            $("#search_type_payout").prop("selectedIndex", 1);
                        } else if (backEndChooseType.toUpperCase() === 'PAYOUT_ID') {
                            $("#search_type_payout").prop("selectedIndex", 2);
                        } else {
                            $("#search_type_payout").prop("selectedIndex", 0);

                        }

                    }


                } else if (val === '') {

                    search_type_col.style.display = "block";
                    chooseType_empty.classList.remove('hide');
                    chooseType_card.classList.add('hide');
                    chooseType_fpx.classList.add('hide');
                    chooseType_boostgrab.classList.add('hide');
                    chooseType_tngspp.classList.add('hide');


                    payment_type_col.classList.add('hide');
                    searchInput_div.classList.add('l4');
                    searchInput_div.classList.remove('l3');

                    searchbtn_div.classList.remove('l12', 'm12');
                    searchbtn_div.classList.add('l2', 'm3');


                }

            }


            function handleEwalletOptionChange(chooseType_empty, chooseType_card, chooseType_fpx, chooseType_boostgrab, chooseType_tngspp) {

                var payment_type_value = document.getElementById("payment_type");
                var walletOpt = payment_type_value.value.trim().toUpperCase();


                if (walletOpt === "TOUCH'N_GO" || walletOpt === 'SHOPEEPAY') {
                    chooseType_empty.classList.add('hide');
                    chooseType_card.classList.add('hide');
                    chooseType_fpx.classList.add('hide');
                    chooseType_boostgrab.classList.add('hide');
                    chooseType_tngspp.classList.remove('hide');
                } else if (walletOpt === "BOOST" || walletOpt === 'GRAB') {
                    chooseType_empty.classList.add('hide');
                    chooseType_card.classList.add('hide');
                    chooseType_fpx.classList.add('hide');
                    chooseType_boostgrab.classList.remove('hide');
                    chooseType_tngspp.classList.add('hide');
                } else if (walletOpt === "") {
                    chooseType_empty.classList.remove('hide');
                    chooseType_card.classList.add('hide');
                    chooseType_fpx.classList.add('hide');
                    chooseType_boostgrab.classList.add('hide');
                    chooseType_tngspp.classList.add('hide');
                }

            }

        }
    )
    ;


</script>


	<script>
    if (${paginationBean.itemList.size()}==0
    )
    {
        document.getElementById("no-data").innerText = "No data available";
    }

</script>
	<style>
.input-field .search_type_div label {
	font-size: 1.1rem !important;
	/* top: -15px; */
	color: #707070;
	position: absolute;
	top: 0;
	left: 0;
	font-size: 1rem;
	cursor: text;
	transition: transform .2s ease-out, color .2s ease-out;
	transform-origin: 0% 100%;
	text-align: initial;
	transform: translateY(-13px);
}

.dropdown-content li {
	min-height: 0 !important;
}

.dropdown-content li>span {
	color: #929292 !important;
	font-size: 14px !important;
	padding: 10px 16px !important;
}

/*vinoth changes*/
.hidecontent {
	display: none !important;
}
</style>

	<div id="payment_type_backend" style="display: none;">${payment_type}</div>
	<input type="hidden" id="search_type">
	<input type="hidden" id="backendSearchLists">

	<!-- <input type ="text" id="txn_type" type="hidden"> -->

	<script>

    var backEndEwalletPaymentType;

    var backEndTxnType = document.getElementById("payment_method").value = "${paginationBean.TXNtype}";


    var backEndChooseType = document.getElementById("search_type").value = "${paginationBean.chooseType}";

    if ("${paginationBean.ewalletPaymentType}") {
        backEndEwalletPaymentType = document.getElementById('payment_type').value = "${paginationBean.ewalletPaymentType}";
    } else {
        backEndEwalletPaymentType = "";
    }


    // try {
    <%--    var backEndEwalletPaymentType = "${paginationBean.ewalletPaymentType}" || "";--%>
    <%--    var backEndTxnType = "${paginationBean.TXNtype}" || "";--%>
    <%--    var backEndChooseType = "${paginationBean.chooseType}" || "";--%>
    <%--    var backendSearchList = "${searchValue}" || "";--%>
    <%--} catch (error) {--%>
    <%--    var backEndEwalletPaymentType = "";--%>
    <%--    var backEndTxnType = "";--%>
    <%--    var backEndChooseType = "";--%>
    <%--    var backendSearchList="";--%>
    <%--}--%>

    var body = document.body;
    var initialOverflow = body.style.overflow;


    try {
        var backendSearchList = document.getElementById("backendSearchLists").value = ${searchValue};
    } catch (error) {
        backendSearchList = "";
    }


    <%--var backendSearchList = document.getElementById("backendSearchLists").value = ${searchValue};--%>


    function toggleOptions() {
        var selectedOption = document.getElementById("payment_method").value;
        if (selectedOption === "Ewallet") {
            document.getElementById("payment_method").options[document.getElementById("payment_method").selectedIndex].text = "E-Wallets";
        } else if (selectedOption === "FPX") {
            document.getElementById("payment_method").options[document.getElementById("payment_method").selectedIndex].text = "FPX-Internet Banking";
        } else if (selectedOption === "Card") {
            document.getElementById("payment_method").options[document.getElementById("payment_method").selectedIndex].text = "Card Transaction";
        } else {
            var upperCaseOption = selectedOption.toUpperCase();
            if (upperCaseOption.includes("PAYIN")) {
                document.getElementById("payment_method").options[document.getElementById("payment_method").selectedIndex].text = selectedOption.replace("Payin - ", "");
            }
        }
    }

    toggleOptions();

</script>

	<script>
    var body = document.body;
    var initialOverflow = body.style.overflow;

</script>

<script>
    function downloadPayoutSlip() {
        var modalContent = document.getElementById('payoutModalContainer_receipt');
        // var hidingElements = document.getElementById('hiding_elements_div');

        if (!modalContent) {
            alert('Modal content not found');
            return;
        }

        // console.log('Hiding elements:', hidingElements);

        // Ensure text does not wrap
        var transferAmt = document.getElementById('new_slip_transferAmt');
        if (transferAmt) transferAmt.style.whiteSpace = 'nowrap';

        // Temporarily set a large enough size to capture all content
        var originalWidth = modalContent.style.width;
        var originalHeight = modalContent.style.height;
        modalContent.style.width = modalContent.scrollWidth + 'px';
        modalContent.style.height = modalContent.scrollHeight + 'px';
        // Hide specific elements
        // if (hidingElements) {
        //     hidingElements.style.display = 'none';
        //     console.log('Element hidden:', hidingElements.style.display);
        // }

        // Use html2canvas to capture the content as an image
        html2canvas(modalContent, {
            backgroundColor: null, // Transparent background
            scrollX: 0,
            scrollY: 0,
            useCORS: true,
            ignoreElements: (element) => element.id === 'cancel-download' || element.id === 'active-download',
            scale: 2,
        }).then(function (canvas) {
            // Convert canvas to image and initiate download


             var filename = globalTxnId ? globalTxnId + '.png' : 'pay_slip.png';
            var link = document.createElement('a');
            link.href = canvas.toDataURL('image/png');

            link.download = filename;
            // globalInvoiceIdProof//
            link.click();
        }).catch(function (error) {
            console.error('Error capturing the canvas:', error);
        }).finally(function () {
            // Restore original styles
            modalContent.style.width = originalWidth;
            modalContent.style.height = originalHeight;
        });
    }</script>

<style>
.rightSide_color_code_slip {
	color : #2D2D2D;
}
</style>

</body>
</html>
``
