<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Approve MDR Rates</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourcesNew/css/tagify.css">
<script
	src="${pageContext.request.contextPath}/resourcesNew/js/tagify.min.js"></script>


<style>
@import
	url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap')
	;

/* body {
            font-family: "Poppins", sans-serif;
        } */
:root {
	--orange: #F9C84C;
	--green: #51CB49;
	--red: #F34657;
	--badge-color: #EC3E3E;
	--badge-text-color: #fff;
	--icon-size: 20px;
	--badge-size: 20px;
	--container-padding: 10px 20px;
	--font-size: 13px;
	--shadow-color: rgba(255, 77, 77, 0.6);
}

.request_option {
	position: relative;
}

.request_option .badge {
	position: absolute;
	top: -3px;
	left: -2px;
	width: var(--badge-size);
	height: var(--badge-size);
	background-color: var(--badge-color);
	color: var(--badge-text-color);
	border-radius: 50%;
	display: flex;
	justify-content: center;
	align-items: center;
	font-size: 10px;
	font-weight: 400;
	box-shadow: 0 0 20px var(--shadow-color);
}

.active_status, .pending, .suspended, .terminated, .rejected,
	#status_in_popup {
	display: flex;
	align-items: center;
	justify-content: flex-start;
}

.active_status span {
	color: var(--green);
	margin-left: 5px;
	font-size: 13px;
	font-weight: 500;
}

.pending span {
	color: var(--orange);
	margin-left: 5px;
	font-size: 13px;
	font-weight: 500;
}

.suspended span, .terminated span, .rejected span {
	color: var(--red);
	margin-left: 5px;
	font-size: 13px;
	font-weight: 500;
}

.request_btn {
	color: #fff;
	background-color: #005baa;
	border-radius: 50px;
	display: flex;
	align-items: center;
	justify-content: center;
	font-family: "Poppins", sans-serif;
	height: 45px;
}

.request_btn span {
	margin-left: 8px;
	font-size: 12px;
}

.request_btn:hover, .request_btn:focus {
	background-color: #005baa;
	color: #fff;
}

.container-fluid {
	padding: 20px 18px !important;
	font-family: "Poppins", sans-serif !important;
}

.select2-dropdown {
	font-family: "Poppins", sans-serif !important;
	z-index: 1;
}

.select2-container--default .select2-search--dropdown .select2-search__field
	{
	font-family: "Poppins", sans-serif !important;
}

.heading_text {
	font-family: "Poppins", sans-serif !important;
	font-weight: 600 !important;
	font-size: 20px !important;
}

.mb-0 {
	margin-bottom: 0 !important;
}

.select2-container--default .select2-selection--single {
	border: none !important;
}

#label_businessname {
	position: static !important;
	color: #858585;
}

.businessname .select2-container {
	margin: 7px 0 !important;
	border: 2px solid #005baa !important;
}

.row .select2-container {
	padding: 4px 10px !important;
	font-size: 14px !important;
	z-index: 1 !important;
}

.select2-container--default .select2-selection--single .select2-selection__arrow
	{
	top: 4px;
	right: 10px;
}

.align-center {
	text-align: center;
}

.heading_row {
	border-bottom: 1.5px solid #F5A623 !important;
}

input[type=search]:not(.browser-default) {
	border-bottom: 1.5px solid #F5A623 !important;
}

input[type=search]:not(.browser-default):focus:not([readonly]) {
	box-shadow: none !important;
}

#data_list_table tr td {
	font-weight: 400;
	font-size: 13px;
	color: #929292;
	padding: 12px;
}

#data_list_table tr th {
	font-weight: 600;
	font-size: 14px;
	padding: 12px;
	white-space: nowrap;
}

.btn_more {
	border: none;
	background-color: transparent;
	cursor: pointer;
	display: flex;
	align-items: center;
	justify-content: center;
	box-shadow: none !important;
}

.btn_more:hover, .btn_more:focus {
	background-color: transparent !important;
}

#more_details {
	/*cursor: pointer;*/
	display: flex;
	align-items: center;
	justify-content: center;
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
	border-bottom: 1.5px solid #F5A623;
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
	padding: 10px 0;
}

.modal-content {
	padding: 10px 30px;
	font-family: "Poppins", sans-serif;
}

@media only screen and (min-width: 993px) {
	.row .col.offset-l4 {
		margin-left: 30.33333%;
	}
}

@media only screen and (min-width: 993px) {
	.row .col.offset-l3 {
		margin-left: 22%;
	}
}

.closebtn {
	background-color: #005baa;
	border-radius: 50px;
	height: 33px !important;
	line-height: 33px !important;
	padding: 0 30px;
	font-size: 12px;
}

.closebtn:hover, .closebtn:focus {
	background-color: #005baa !important;
}

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
	font-family: 'Poppins', sans-serif;
}

#pagination a, #pagination i {
	display: inline-block;
	vertical-align: middle;
	width: 2.2rem;
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
	margin: 0 2px;
	border-radius: 1px;
	border: 1px solid #005baa;
	cursor: pointer;
	background-color: white;
	color: #005baa;
	height: 2.3rem;
	font-weight: 400 !important;
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
</style>


<style>
.result_popup_messages {
	font-size: 17px;
}

.popup_messages {
	color: #515151;
	font-size: 18px;
	margin-bottom: 0;
}

.confirmbtn {
	background-color: #005baa;
	border-radius: 50px;
	height: 33px !important;
	line-height: 33px !important;
	padding: 0 30px;
	font-size: 12px;
	margin: 0 10px;
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
	padding: 0 25px;
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

.preview_overlay, .action_overlay, .result_overlay, .confirm_overlay,
	.confirm_approval_overlay {
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

.preview_modal, .action_modal, .result_modal, .confirm_modal,
	.confirm_approval_modal {
	background-color: #fff;
	border-radius: 10px !important;
	margin: 1% auto;
}

.update_popup-modal {
	background-color: #fff;
	border-radius: 10px !important;
	margin: 1% auto;
}

.modal-header {
	padding: 10px 6px;
	height: auto;
	width: 100%;
	text-align: center;
	border-bottom: 1.5px solid #F5A623;
	font-size: 16px;
}

.footer {
	background-color: #EFF8FF !important;
	display: flex;
	align-items: center;
	justify-content: center;
}

.preview_footer {
	padding: 10px 20px !important;
	justify-content: flex-end;
}

.preview_confirm_btn {
	margin: 0 3px;
}

.align-center {
	text-align: center;
}

.modal_row {
	width: 100%;
	height: 100%;
	align-content: center;
}

.modal-header {
	color: #005BAA;
	text-align: center;
	padding: 13px 10px;
	border-bottom: 1.5px solid #F5A623;
	font-weight: 500;
	font-size: 16px;
}

.confirm_modal_header {
	padding: 10px;
}

.modal-content {
	padding: 15px 14px;
}

.modal-footer {
	background-color: #EFF8FF;
	border-bottom-left-radius: 10px;
	border-bottom-right-radius: 10px;
	padding: 10px 0;
}

.modal-content {
	padding: 15px 15px;
	font-family: "Poppins", sans-serif;
	color: #515151 !important;
}

.closebtn {
	background-color: #005baa;
	border-radius: 50px;
	height: 33px !important;
	line-height: 33px !important;
	padding: 0 30px;
	font-size: 12px;
	font-family: "Poppins";
}

.closebtn:hover, .closebtn:focus {
	background-color: #005baa !important;
	padding: 0 30px;
	box-shadow: 5px 5px 10px -3px rgba(0, 90, 170, 0.2), /* Right shadow */
                    -5px 5px 10px -3px rgba(0, 90, 170, 0.2),
		/* Left shadow */
                    0 5px 10px -3px rgba(0, 90, 170, 0.2) !important;
	/* Bottom shadow */
}

.content_updatepopup {
	display: flex;
	flex-direction: column;
	align-items: center;
	justify-content: center;
	padding: 7px 30px !important;
}

.popup_head, .header_text {
	font-size: 18px;
	color: #005baa;
	font-weight: 600;
}

.preview_table tr {
	border-bottom: none !important;
	display: flex;
}

.preview_table td, .preview_table th {
	padding: 10px 0 !important;
	font-size: 14px;
}

.preview_table .left_data, .left_data {
	color: #8D8D8D;
	font-weight: 600;
	flex: 0.8;
}

.hyphen2 {
	flex: 0.2 !important;
}

.preview_table .hyphen {
	color: #8D8D8D;
	font-weight: 600;
	flex: 0.3;
}

.preview_table .right_data, .right_data {
	color: #8D8D8D;
	flex: 1;
}

#close_xmark {
	color: #005baa !important;
	font-weight: 400;
	float: right;
	cursor: pointer;
	border-radius: 50%;
	padding: 2px 6px;
}

/*.card-inner {*/

/*        box-shadow:*/
/*                5px 5px 10px -3px rgba(45, 45, 45, 0.15),  !* Right shadow *!*/
/*                -5px 5px 10px -3px rgba(45, 45, 45, 0.15), !* Left shadow *!*/
/*                0 5px 10px -3px rgba(45, 45, 45, 0.15) !important; !* Bottom shadow *!*/
/*    }*/
.card-inner {
	box-shadow: 5px 5px 10px -3px rgba(45, 45, 45, 0.08), /* Right shadow */
		
		
		
		
		
		
		
		
		
		
		
		
		
                    -5px 5px 10px -3px rgba(45, 45, 45, 0.08),
		/* Left shadow */
                    0 5px 10px -3px rgba(45, 45, 45, 0.08) !important;
	/* Bottom shadow */
}

#close_xmark:hover, #close_xmark:focus {
	background-color: #2d2d2d08;
}

.main_contents {
	padding: 0 15px;
}

.radio-group {
	display: flex;
	justify-content: center;
	margin-bottom: 10px;
}

.website_link {
	text-decoration: underline;
	color: #005baa;
}

.status_radio {
	width: 45%;
	display: flex;
	justify-content: space-between;
	margin-bottom: 10px;
}

.status_radio label {
	margin: 0px;
}

[type="radio"] {
	/*opacity: 1 !important;*/
	
}

.radio-group label {
	margin: 0 10px;
	font-size: 15px;
}

[type="radio"]:not(:checked)+span, [type="radio"]:checked+span {
	padding-left: 30px !important;
}

[type="radio"]+span:before, [type="radio"]+span:after {
	width: 19px;
	height: 19px;
	margin: 3px;
}

.reason_text::placeholder {
	color: #D0D0D0;
}

.recheck_modalcontent {
	padding: 10px 30px;
}

.reason_text {
	width: 100%;
	height: 6rem;
	padding: 10px;
	background-color: transparent;
	resize: none;
	border-radius: 5px;
	border: 1px solid #70707070 !important;
	scrollbar-width: thin;
	color: #586570;
	font-family: "Poppins", sans-serif;
	font-size: 14px;
	margin: 8px 0;
	box-sizing: border-box;
}

[type="radio"]:checked+span:after {
	background-color: transparent;
	border: 4px solid #005baa;
}

[type="radio"]:not(:checked)+span:before {
	border: 1px solid #5a5a5a;
}

#data_list_table tbody tr {
	border-bottom: 1.5px solid rgba(0, 0, 0, 0.12);
}

#overlay {
	z-index: 100;
}

.preview_modal_content {
	height: 75vh;
	overflow-y: auto;
	scrollbar-width: none;
}

/*    mdr rates style */
#overlay {
	z-index: 100 !important;
}

.button-tabs, .mdr_button-tabs {
	color: #85858570;
	width: 100%;
	background: #fff;
	border: none;
	border-bottom: 2px solid #85858570;
	padding: 5px 15px;
	font-weight: 500;
	font-family: "Poppins", sans-serif;
	cursor: pointer;
	font-size: 12px;
}

.p-0 {
	padding: 0 !important;
}

.button-tabs:focus, .mdr_button-tabs:focus {
	background-color: #fff !important;
}

.tab_active {
	color: #005baa;
	border-bottom: 2px solid #005baa;
	cursor: pointer;
}

.ws-nowrap {
	white-space: nowrap;
}

.preview_table {
	margin-bottom: 5px;
}

.mdr_card {
	padding: 14px 24px !important;
}

.mdr_card .input-field.col label {
	font-size: 14px;
}

.input-field.col input::placeholder {
	font-size: 10px !important;
}

input[type="text"]:not(.browser-default) {
	border-bottom: 1.5px solid orange !important;
	color: #707070;
	font-size: 12px !important;
	height: 2rem;
	font-family: "Poppins", sans-serif;
}

input[type="text"]:not (.browser-default )::placeholder {
	color: #D0D0D0 !important;
}

input[type="text"]:not (.browser-default ):focus:not ([readonly] )+label
	{
	color: #707070;
}

.mdr_button-tabs {
	white-space: nowrap;
}

.paymentmethod_text {
	color: #333333;
	font-weight: 500;
	font-size: 11px;
	text-align: left;
}

input[type=text]:not(.browser-default) {
	font-size: 12px !important;
	color: #707070 !important;
}

.disabled {
	opacity: 0.5;
	pointer-events: none;
}

.mx-0 {
	margin-left: 0 !important;
	margin-right: 0 !important;
}

input[type=text]:not(.browser-default ):focus:not([readonly] )+label {
	color: #707070 !important;
}

@media only screen and (min-width: 601px) and (max-width: 992px) {
	.mdr_card .input-field.col label {
		font-size: 10px !important;
	}
	input[type=text]:not(.browser-default) {
		font-size: 10px !important;
		color: #707070 !important;
		height: 1.5rem;
	}
	.paymentmethod_text {
		font-size: 9px;
	}
	.input-field.col input::placeholder {
		font-size: 10px !important;
	}
	.button-tabs, .mdr_button-tabs {
		font-size: 9px;
	}
}

.padding_row {
	padding: 0.5rem 0 !important;
}

.padding_row .input-field {
	margin: 0;
}

.tagify {
	border: none;
	display: flex;
	border-bottom: 2px solid orange !important;
	--tag-inset-shadow-size: 2.1em !important;
}

.tagify:hover {
	border-bottom-color: orange !important;
}

.tagify--focus {
	border-bottom-color: orange !important;
}

.tagify:focus-within {
	border-bottom-color: orange !important;
}
</style>


</head>
<body>

	<div class="test" id="pop-bg-color"></div>
	<div id="overlay-popup"></div>

	<div id="overlay">
		<div id="overlay_text">
			<img class="img-fluid"
				src="/MobiversaAdmin/resourcesNew1/assets/loader.gif">
		</div>
	</div>

	<div class="container-fluid mb-0" id="pop-bg">

		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class=" align-items-center"
							style="display: flex; justify-content: space-between;">
							<h3 class="text-white mb-0 ">
								<strong class="heading_text">Check Sub Merchant MDR</strong>
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="row">


							<%-- merchant  choose--%>
							<div class="col s12 m4 l4 input-field businessname">
								<label for="merchantName" id="label_businessname">Select
									Main Merchant</label> <select name="merchantName" id="merchantName"
									path="merchantName"
									<%--                                    onchange="javascript:location.href = this.value;"--%>
                                    class="browser-default select-filter">

									<!-- onclick="javascript: locate();"> -->
									<optgroup label="Business Names" style="width: 100%">
										<option selected value=""><c:out
												value="Main mechant name" /></option>


										<c:forEach var="merchant" items="${mainmerchantList}">

											<option
												value="${pageContext.request.contextPath}/submerchant/operation-parent/search/submerchant?mmId=${merchant.mmId}&merchantName=${merchant.businessName}">
												${merchant.businessName}</option>

										</c:forEach>

									</optgroup>
								</select>


							</div>
						</div>
					</div>
				</div>
			</div>
		</div>


		<%--   table content --%>

		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="">

							<div class="table-responsive m-b-20 m-t-15" id="page-table">
								<table id="data_list_table"
									class="table table-striped table-bordered">
									<thead>
										<tr class="heading_row">
											<th>Date</th>
											<th>Business Name</th>
											<th class="">MID</th>
											<th class="">Main Merchant</th>
											<th style="text-align: -webkit-center;">Preview</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${submerchant}" var="dto">




											<tr>
												<td>${fn:substring(dto.date, 0, 10)}</td>
												<td>${dto.bussinessName}</td>
												<td>${dto.mid}</td>
												<td>${dto.mmId}</td>

												<%--												<td>${dto.mdrdetailsBeanJson}</td>--%>



												<td style="text-align: -webkit-center;" id="more_details">
													<button class="btn_more"
														onclick="openPreviewPopup('${dto.bussinessName}','${dto.email}','${dto.website}','${dto.natureOfBusiness}','${dto.country}','<c:out value="${dto.mdrdetailsBeanJson}"/>','${dto.mmId}')">
														<img
															src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/blureye.svg"
															width="20" height="20" alt="">
													</button>
												</td>


											</tr>

										</c:forEach>
										<c:if test="${submerchant.size() == 0 }">
											<tr id="nodata_row">
												<td colspan="6" style="text-align: center;">
													<div id="no-data">
														<p class="mb-0">No data Available</p>
													</div>
												</td>
											</tr>
										</c:if>

									</tbody>
								</table>
							</div>

						</div>
					</div>
				</div>
			</div>


			<%--       result popup --%>

			<div class="result_overlay" id="result_overlay">
				<div class="row modal_row">
					<div class="col offset-l4 offset-m3 s12 m6 l5">
						<div id="result-modal" class="result_modal">
							<div class="modal-header">
								<p class="mb-0">Notification</p>
							</div>
							<div class="modal-content ">
								<div class="align-center">
									<img id="result_popup_image"
										src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Successful.svg"
										width="40" height="40">
								</div>
								<p id="result_popup_text"
									class="align-center  result_popup_messages"></p>
							</div>
							<div class="align-center modal-footer footer">
								<button id="closebtn_result" class="btn blue-btn closebtn"
									type="button" onclick="closeResultModal()" name="action">Close
								</button>

							</div>
						</div>
					</div>
				</div>
			</div>



			<%--       confirm popup  --%>

			<div class="confirm_overlay" id="confirm_overlay">
				<div class="row modal_row">
					<div class="col offset-l4 offset-m3 s12 m6 l5">
						<div id="confirm-modal" class="confirm_modal">
							<form id="issueUpdateForm"
								action="${pageContext.request.contextPath}/submerchant/operation-parent/preview/submerchant/recheck"
								method="post" id="mdrDetailsForm">
								<div class="modal-header">
									<p class="mb-0">Confirmation</p>
								</div>
								<div class="modal-content recheck_modalcontent">
									<div class="align-center">
										<img
											src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/remarks.svg"
											width="35" height="35">
									</div>
									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}">

									<p class="align-center popup_messages">What issue would you
										like us to look into?</p>

									<textarea rows="4" cols="4" id="reason_textbox" name="comment"
										class="reason_text" placeholder="Type issue here..."></textarea>

									<input type="hidden" id="recheck_currPage" name="currPage"
										value="${paginationBean.currPage}"> <input
										type="hidden" id="recheck_mmId" name="mmId"> <input
										type="hidden" id="recheck_subMerchantName"
										name="subMerchantName">


								</div>
								<div class="align-center modal-footer footer">
									<button id="recheck_close_btn" class="btn cancelbtn"
										type="button" onclick="closeRecheckModal()" name="action">Cancel
									</button>
									<button id="submitIssueBtn" class="btn confirmbtn "
										type="submit" name="action">Submit</button>

								</div>
							</form>
						</div>
					</div>
				</div>
			</div>

			<%--       preview popup --%>

			<div class="preview_overlay" id="preview_overlay">
				<div class="row modal_row">
					<div class="col offset-l3 offset-m2 s12 m8 l7">
						<div id="preview-modal" class="preview_modal" method="post">

							<div class="main_contents">
								<div class="modal-header">
									<p class="mb-0  popup_head">
										Sub Merchant Preview <a id="close_xmark"> <img
											src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/cross_mark.svg"
											width="12" height="12">
										</a>
									</p>

								</div>

								<div class="modal-content preview_modal_content">
									<div>
										<p class="header_text mb-0">Business Details</p>
									</div>
									<div>
										<table class="preview_table">
											<thead>
											</thead>
											<tbody>

												<tr>
													<td class="left_data">Business Name</td>
													<td class="hyphen hyphen2">-</td>
													<td class="right_data" id="businessname"></td>
												</tr>
												<tr>
													<td class="left_data">Email</td>
													<td class="hyphen hyphen2">-</td>
													<td class="right_data" id="email"></td>
												</tr>

												<tr>
													<td class="left_data">Website</td>
													<td class="hyphen hyphen2">-</td>
													<td class="right_data"><a class="website_link"
														href="https://www.newway.com" id="website"></a></td>
												</tr>

												<tr>
													<td class="left_data">Industry</td>
													<td class="hyphen hyphen2">-</td>
													<td class="right_data" id="industry"></td>
												</tr>
												<tr>
													<td class="left_data">Country</td>
													<td class="hyphen hyphen2">-</td>
													<td class="right_data" id="country"></td>
												</tr>
											</tbody>
										</table>

										<div id="mdrs_showing_option">

											<div>
												<p class="header_text mb-0">MDR Rates</p>
											</div>
											<div class="row">
												<div class="col s12">
													<div class="card card-inner blue-bg text-white">
														<div class="card-content mdr_card ">

															<div class="row mb-0 product_btn_row"
																style="padding: 0.50rem 0.75rem;">


																<div class="col s12 m3 l3 p-0" id="fpx_col">
																	<button type="button" class="mdr_button-tabs"
																		id="fpx_mdr_btn">Internet Banking</button>
																</div>

																<div class="col s12 m3 l3 p-0" id="cards_col">
																	<button type="button" class="mdr_button-tabs"
																		id="cards_mdr_btn">Card</button>
																</div>

																<div class="col s12 m3 l3 p-0" id="ewallet_col">
																	<button type="button" class="mdr_button-tabs"
																		id="ewallet_mdr_btn">eWallets</button>
																</div>

																<div class="col s12 m3 l3 p-0" id="payout_col">
																	<button type="button" class="mdr_button-tabs"
																		id="payout_mdr_btn">Payout</button>
																</div>

															</div>


															<div class="row content" id="fpx_container"
																style="padding: 2rem 0; display: none;">
																<div class="col s12 m4 l4 input-field" style="">
																	<p class="paymentmethod_text">FPX Internet Banking
																		MDR(%)</p>
																</div>
																<div class="col s12 m2 l2 input-field disabled">

																	<input placeholder="0.0" name="fpx_merchantmdr"
																		id="fpx_merchantmdr" type="text" class=""
																		value="${mdrdetailsBean.fpx.hostmdr}" readonly>

																	<label for="fpx_merchantmdr"
																		style="white-space: nowrap;" inputmode="decimal">Merchant
																		MDR</label>
																</div>
																<div class="col s12 m2 l2 input-field disabled">
																	<input placeholder="0.00" name="fpx_hostmdr"
																		id="fpx_hostmdr" type="text" class="" value=""
																		readonly> <label for="fpx_hostmdr"
																		style="white-space: nowrap;" inputmode="decimal">Host
																		MDR</label>
																</div>
																<div class="col s12 m2 l2 input-field disabled">
																	<input placeholder="0.00" name="fpx_mobimdr"
																		id="fpx_mobimdr" type="text" class="" value=""
																		readonly> <label for="fpx_mobimdr"
																		style="white-space: nowrap;" inputmode="decimal">Mobi
																		MDR</label>
																</div>
																<div class="col s12 m2 l2 input-field disabled">
																	<input placeholder="0.00" name="fpx_minimummdr"
																		id="fpx_minimummdr" type="text" class="" value=""
																		readonly> <label for="fpx_minimummdr"
																		style="white-space: nowrap;" inputmode="decimal">Minimum
																		MDR</label>
																</div>
															</div>


															<%--                                cards mdr inputs    --%>


															<div id="cards_container" class="content"
																style="display: none;">

																<div class="row ">
																	<div class="col s12 m4 l4 input-field"></div>
																	<div class="input-field col s12 m2 l1">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/visacard.svg"
																			alt="visa" width="40" height="40">
																	</div>
																	<div
																		class="col s12 m2 l1 offset-l2 offset-m1 input-field ">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/mastercard.svg"
																			alt="mastercard" width="40" height="40">
																	</div>
																	<div
																		class="col s12 m2 l1 offset-l2 offset-m1 input-field ">
																		<img
																			src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/unionpay.svg"
																			alt="unionpay" width="40" height="40">
																	</div>
																</div>


																<%--                                    local debit card --%>
																<div class="row padding_row" style="padding: 0.5rem 0;">
																	<div class="col s12 m4 l4 input-field" style="">
																		<p class="paymentmethod_text">MDR For Local Debit
																			Card(%)</p>
																	</div>
																	<div class="col s12 m2 l2  input-field disabled ">
																		<%--                                                <img class="show-on-small" src="${pageContext.request.contextPath}/resourcesNew1/assets/visacard.svg" width="25" height="25">--%>
																		<input placeholder="0.00" name="localdebitvisamdr"
																			id="localdebitvisamdr" type="text" class="" value=""
																			readonly inputmode="decimal"> <label
																			for="localdebitvisamdr" style="white-space: nowrap;">MDR</label>
																	</div>
																	<div
																		class="col s12 m2 l2 offset-l1 offset-m1 input-field disabled">
																		<input placeholder="0.00" name="localdebitmastermdr"
																			id="localdebitmastermdr" type="text" class=""
																			inputmode="decimal" readonly value=""> <label
																			for="localdebitmastermdr"
																			style="white-space: nowrap;">MDR</label>
																	</div>
																	<div
																		class="col s12 m2 l2 offset-l1 offset-m1 input-field disabled ">
																		<input placeholder="0.00" name="localdebitunionmdr"
																			id="localdebitunionmdr" type="text" class=""
																			inputmode="decimal" readonly value=""> <label
																			for="localdebitunionmdr" style="white-space: nowrap;">MDR</label>
																	</div>
																</div>

																<%--                                        local credit card --%>

																<div class="row padding_row" style="padding: 0.5rem 0;">
																	<div class="col s12 m4 l4 input-field" style="">
																		<p class="paymentmethod_text">MDR For Local Credit
																			Card(%)</p>
																	</div>
																	<div class="col s12 m2 l2  input-field disabled">
																		<input placeholder="0.00" name="localcreditvisamdr"
																			id="localcreditvisamdr" type="text" class=""
																			inputmode="decimal" readonly value=""> <label
																			for="localcreditvisamdr" style="white-space: nowrap;">MDR</label>
																	</div>
																	<div
																		class="col s12 m2 l2 offset-l1 offset-m1 input-field disabled">
																		<input placeholder="0.00" name="localcreditmastermdr"
																			id="localcreditmastermdr" type="text" class=""
																			inputmode="decimal" readonly value=""> <label
																			for="localcreditmastermdr"
																			style="white-space: nowrap;">MDR</label>
																	</div>
																	<div
																		class="col s12 m2 l2 offset-l1 offset-m1 input-field disabled">
																		<input placeholder="0.00" name="localcreditunionmdr"
																			id="localcreditunionmdr" type="text" class=""
																			inputmode="decimal" readonly value=""> <label
																			for="localcreditunionmdr"
																			style="white-space: nowrap;">MDR</label>
																	</div>
																</div>

																<%--                                        foreign debit card --%>

																<div class="row padding_row" style="padding: 0.5rem 0;">
																	<div class="col s12 m4 l4 input-field" style="">
																		<p class="paymentmethod_text">MDR For Foreign
																			Debit Card(%)</p>
																	</div>
																	<div class="col s12 m2 l2  input-field disabled">
																		<input placeholder="0.00" name="foreigndebitvisamdr"
																			id="foreigndebitvisamdr" type="text" class=""
																			inputmode="decimal" readonly value=""> <label
																			for="foreigndebitvisamdr"
																			style="white-space: nowrap;">MDR</label>
																	</div>
																	<div
																		class="col s12 m2 l2 offset-l1 offset-m1 input-field disabled">
																		<input placeholder="0.00" name="foreigndebitmastermdr"
																			id="foreigndebitmastermdr" type="text" class=""
																			inputmode="decimal" readonly value=""> <label
																			for="foreigndebitmastermdr"
																			style="white-space: nowrap;">MDR</label>
																	</div>
																	<div
																		class="col s12 m2 l2 offset-l1 offset-m1 input-field disabled">
																		<input placeholder="0.00" name="foreigndebitunionmdr"
																			id="foreigndebitunionmdr" type="text" class=""
																			inputmode="decimal" readonly value=""> <label
																			for="foreigndebitunionmdr"
																			style="white-space: nowrap;">MDR</label>
																	</div>
																</div>

																<%--                                        foreign credit card --%>

																<div class="row padding_row" style="padding: 0.5rem 0;">
																	<div class="col s12 m4 l4 input-field" style="">
																		<p class="paymentmethod_text">MDR For Foreign
																			Credit Card(%)</p>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="foreigncreditvisamdr"
																			id="foreigncreditvisamdr" type="text" class=""
																			inputmode="decimal" readonly value=""> <label
																			for="foreigncreditvisamdr"
																			style="white-space: nowrap;">MDR</label>
																	</div>
																	<div
																		class="col s12 m2 l2 offset-l1 offset-m1 input-field disabled ">
																		<input placeholder="0.00"
																			name="foreigncreditmastermdr"
																			id="foreigncreditmastermdr" type="text" class=""
																			inputmode="decimal" readonly value=""> <label
																			for="foreigncreditmastermdr"
																			style="white-space: nowrap;">MDR</label>
																	</div>
																	<div
																		class="col s12 m2 l2 offset-l1 offset-m1 input-field disabled ">
																		<input placeholder="0.00" name="foreigncreditunionmdr"
																			id="foreigncreditunionmdr" type="text" class=""
																			inputmode="decimal" readonly value=""> <label
																			for="foreigncreditunionmdr"
																			style="white-space: nowrap;">MDR</label>
																	</div>
																</div>


															</div>


															<div class="row content" id="ewallet_container"
																style="padding: 2rem 0; display: none;">
																<%--                                       boost mdr --%>


																<div class="row padding_row mx-0">
																	<div class="col s12 m4 l4 input-field" style="">
																		<p class="paymentmethod_text">MDR For Boost (%)</p>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="boost_merchantmdr"
																			id="boost_merchantmdr" type="text" class=""
																			inputmode="decimal" readonly value=""> <label
																			for="boost_merchantmdr" style="white-space: nowrap;">Merchant
																			MDR</label>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="boost_hostmdr"
																			id="boost_hostmdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="boost_hostmdr" style="white-space: nowrap;">Host
																			MDR</label>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="boost_mobimdr"
																			id="boost_mobimdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="boost_mobimdr" style="white-space: nowrap;">Mobi
																			MDR</label>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="boost_minimummdr"
																			id="boost_minimummdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="boost_minimummdr" style="white-space: nowrap;">Minimum
																			MDR</label>
																	</div>
																</div>

																<div class="row padding_row mx-0">
																	<div class="col s12 m4 l4 input-field" style="">
																		<p class="paymentmethod_text">MDR For GrabPay (%)</p>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="grab_merchantmdr"
																			id="grab_merchantmdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="grab_merchantmdr" style="white-space: nowrap;">Merchant
																			MDR</label>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled ">
																		<input placeholder="0.00" name="grab_hostmdr"
																			id="grab_hostmdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="grab_hostmdr" style="white-space: nowrap;">Host
																			MDR</label>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="grab_mobimdr"
																			id="grab_mobimdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="grab_mobimdr" style="white-space: nowrap;">Mobi
																			MDR</label>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="grab_minimummdr"
																			id="grab_minimummdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="grab_minimummdr" style="white-space: nowrap;">Minimum
																			MDR</label>
																	</div>
																</div>

																<div class="row padding_row mx-0">
																	<div class="col s12 m4 l4 input-field" style="">
																		<p class="paymentmethod_text">MDR For Touch'N Go
																			(%)</p>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="tng_merchantmdr"
																			id="tng_merchantmdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="tng_merchantmdr" style="white-space: nowrap;">Merchant
																			MDR</label>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="tng_hostmdr"
																			id="tng_hostmdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="tng_hostmdr" style="white-space: nowrap;">Host
																			MDR</label>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="tng_mobimdr"
																			id="tng_mobimdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="tng_mobimdr" style="white-space: nowrap;">Mobi
																			MDR</label>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="tng_minimummdr"
																			id="tng_minimummdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="tng_minimummdr" style="white-space: nowrap;">Minimum
																			MDR</label>
																	</div>
																</div>

																<div class="row padding_row mx-0">
																	<div class="col s12 m4 l4 input-field" style="">
																		<p class="paymentmethod_text">MDR For Shopee Pay
																			(%)</p>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="spp_merchantmdr"
																			id="spp_merchantmdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="spp_merchantmdr" style="white-space: nowrap;">Merchant
																			MDR</label>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="spp_hostmdr"
																			id="spp_hostmdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="spp_hostmdr" style="white-space: nowrap;">Host
																			MDR</label>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="spp_mobimdr"
																			id="spp_mobimdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="spp_mobimdr" style="white-space: nowrap;">Mobi
																			MDR</label>
																	</div>
																	<div class="col s12 m2 l2 input-field disabled">
																		<input placeholder="0.00" name="spp_minimummdr"
																			id="spp_minimummdr" type="text" inputmode="decimal"
																			class="" value="" readonly> <label
																			for="spp_minimummdr" style="white-space: nowrap;">Minimum
																			MDR</label>
																	</div>
																</div>

															</div>

															<%----%>

															<%--                                    payout content--%>

															<div class="row content" id="payout_container"
																style="padding: 2rem 0; display: none;">
																<div class="col s12 m4 l4 input-field " style="">
																	<p class="paymentmethod_text">MDR For Payout (%)</p>
																</div>
																<div class="col s12 m2 l2 input-field disabled ">
																	<input placeholder="0.00" name="payout_merchantmdr"
																		id="payout_merchantmdr" type="text"
																		inputmode="decimal" class="" value="" readonly>
																	<label for="payout_merchantmdr"
																		style="white-space: nowrap;">Merchant MDR</label>
																</div>
																<div class="col s12 m2 l2 input-field disabled">
																	<input placeholder="0.00" name="payout_hostmdr"
																		id="payout_hostmdr" type="text" inputmode="decimal"
																		class="" value="" readonly> <label
																		for="payout_hostmdr" style="white-space: nowrap;">Host
																		MDR</label>
																</div>
																<div class="col s12 m2 l2 input-field disabled ">
																	<input placeholder="0.00" name="payout_mobimdr"
																		id="payout_mobimdr" type="text" inputmode="decimal"
																		class="" value="" readonly> <label
																		for="payout_mobimdr" style="white-space: nowrap;">Mobi
																		MDR</label>
																</div>
																<div class="col s12 m2 l2 input-field disabled">
																	<input placeholder="0.00" name="payout_minimummdr"
																		id="payout_minimummdr" type="text" inputmode="decimal"
																		class="" value="" readonly> <label
																		for="payout_minimummdr" style="white-space: nowrap;">Minimum
																		MDR</label>
																</div>
															</div>


														</div>
													</div>
												</div>
											</div>



										</div>

									</div>

								</div>
							</div>



							<div class="align-center modal-footer footer preview_footer">
								<button id="close_btn" class="btn cancelbtn" type="button"
									onclick="openRecheckPopup()" name="action">Recheck</button>
								<button id="confirm_preview_update_btn"
									class="btn confirmbtn preview_confirm_btn " type="button"
									onclick="openConfirmApprovalPopup()">Approve</button>

							</div>

						</div>
					</div>
				</div>

			</div>



			<%--		confirm approval popup	--%>
			<div class="confirm_approval_overlay" id="confirm_approval_overlay">
				<div class="row modal_row">
					<div class="col offset-l4 offset-m3 s12 m6 l5">
						<form id="confirm_approval_form"
							action="${pageContext.request.contextPath}/submerchant/operation-parent/preview/submerchant/approve"
							method="post">
							<div id="confirm_approval_modal" class="confirm_approval_modal">
								<div class="modal-header">
									<p class="mb-0">Notification</p>
								</div>
								<div class="modal-content" style="padding: 20px 30px;">
									<div class="align-center">
										<img id="confirm_approval_image"
											src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/mail.svg"
											width="45" height="45">
									</div>
									<p id="confirm_approval_text" class="align-center"
										style="font-size: 17px;">Who do you want to notify?</p>

									<input type="hidden" name="${_csrf.parameterName}"
										value="${_csrf.token}"> <input name="email_tags"
										id="approval_emails" placeholder="Enter email address..">
									<input type="hidden" id="hidden_approval_emails" name="emails">
									<input type="hidden" id="currPage" name="currPage"
										value="${paginationBean.currPage}"> <input
										type="hidden" id="mmId" name="mmId"> <input
										type="hidden" id="subMerchantName" name="subMerchantName">
								</div>
								<div class="align-center modal-footer footer">

									<button id="confirm_approval_closeBtn" class="btn cancelbtn"
										type="button" onclick="closeConfirmApprovalPopup()">Cancel
									</button>
									<button id="confirm_approval_btn" class="btn confirmbtn "
										type="submit" name="action">Confirm</button>

								</div>
							</div>
						</form>
					</div>
				</div>
			</div>


		</div>



	</div>

	<%--        action popup --%>

	</div>


	<div id="pagination"></div>
	<input type="hidden" id="pgnum">
	<input type="hidden" id="FromDate" />
	<input type="hidden" id="ToDate" />
	<input type="hidden" id="merchantId" />
	<input type="hidden" id="status_update_result"
		value="${updateSubmerchantStatus}" />
	<input type="hidden" id="status_recheck_result"
		value="${updateRecheckStatus}" />
	<%-- <input type="hidden" id="issue_update_result" value="${updateIssue}" /> --%>
	<input type="hidden" id="merchant_name" value="${merchantName}" />


	</div>


	<script>

    document.addEventListener("DOMContentLoaded", function () {

		var approval_emails = document.getElementById("approval_emails");
		var hidden_approval_emails = document.getElementById("hidden_approval_emails");
		var tagify = new Tagify(approval_emails);

		document.getElementById('confirm_approval_form').addEventListener('submit', function(event) {
			var tagifyValues = tagify.value;
			var commaSeperatedEmails = tagifyValues.map(tag => tag.value).join(',');
			hidden_approval_emails.value = commaSeperatedEmails;

			/* if (tagifyValues.length === 0) {
				event.preventDefault();
				alert("Please provide email addresses to notify");
				return;
			} */

			// Validate email addresses
			var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
			var invalidEmails = tagifyValues.filter(tag => !emailPattern.test(tag.value));

			if (invalidEmails.length > 0) {
				event.preventDefault();
				alert("Please provide valid email addresses.");
				return;
			}
			closePreviewPopup();
			closeConfirmApprovalPopup();

			document.getElementById("overlay").style.display = "block";
		});



		var tabs = document.querySelectorAll('.mdr_button-tabs');

        var fpx = document.getElementById("fpx_mdr_btn");
        var fpx_content = document.getElementById("fpx_container");
        fpx.classList.add('tab_active');
        fpx_content.style.display = "block";

        tabs.forEach(function (tab) {
            tab.addEventListener('click', function () {
                var tabId = this.getAttribute('id');
                var contentId = tabId.replace('_mdr_btn', '_container');


                tabs.forEach(function (tab) {
                    tab.classList.remove('tab_active');
                });


                this.classList.add('tab_active');

                var contents = document.querySelectorAll('.content');
                contents.forEach(function (content) {
                    content.style.display = 'none';
                });

                document.getElementById(contentId).style.display = 'block';
            });
        });
    });


    function openMoreDetailsPopup() {
        document.getElementById("action_overlay").style.display = "block";
    }

	function openConfirmApprovalPopup(){
		document.getElementById("confirm_approval_overlay").style.display = "block";
	}

	function closeConfirmApprovalPopup(){
		document.getElementById("confirm_approval_overlay").style.display = "none";
		const emails_input = document.getElementById("approval_emails");
		emails_input.value = '';
	}


    function closeResultModal() {
        document.getElementById("result_overlay").style.display = "none";
    }


    document.addEventListener('DOMContentLoaded', function() {

        const proceedButton = document.getElementById('confirm_preview_update_btn');

        const close_xmarkBtn = document.getElementById('close_xmark');
        const actionModal = document.getElementById("preview_overlay");
        const form = document.getElementById('previewForm');
        const overlay = document.getElementById('overlay');

        const submitIssueBtn = document.getElementById('submitIssueBtn');
        const recheck_close_btn = document.getElementById('recheck_close_btn');
        const issueUpdateModal = document.getElementById("confirm-modal");
        const issueUpdateForm = document.getElementById('issueUpdateForm');
        const textarea = document.getElementById('reason_textbox');


        function validateForm() {
            const isTextareaFilled = textarea.value.trim().length > 0;

            if (isTextareaFilled) {
                submitIssueBtn.disabled = false;
                submitIssueBtn.style.opacity = 1;
            } else {
                submitIssueBtn.disabled = true;
                submitIssueBtn.style.opacity = 0.5;
            }
        }


        textarea.addEventListener('input', validateForm);

        recheck_close_btn.addEventListener('click',validateForm);

        validateForm();




        submitIssueBtn.addEventListener('click', function(event) {
            event.preventDefault();
            issueUpdateModal.style.display = "none"
            overlay.style.display = 'block';

            setTimeout(() => {
                issueUpdateForm.submit();
            }, 2000);
        });

        <%--proceedButton.addEventListener('click', function(event) {--%>
        <%--    event.preventDefault();--%>
        <%--    actionModal.style.display = "none"--%>
        <%--    overlay.style.display = 'block';--%>
        <%--    var approved = 'approved';--%>
        <%--    document.location.href= '${pageContext.request.contextPath}/submerchant/operation-parent/preview/submerchant/approve';--%>

        <%--});--%>

        close_xmarkBtn.addEventListener('click', function() {
            closePreviewPopup();
        });


    });


	function openPreviewPopup(businessName, email, website, industry, country, mdrdetails,mmid) {
		document.getElementById("preview_overlay").style.display = "block";
		document.querySelector('.preview_modal_content').scrollTop = 0;


		document.getElementById("businessname").innerText = businessName;
		document.getElementById("country").innerText = country;
		document.getElementById("website").innerText = website;
		document.getElementById("email").innerText = email;
		document.getElementById("industry").innerText = industry;

		document.getElementById("subMerchantName").value = businessName;
		document.getElementById("mmId").value = mmid;

		document.getElementById("recheck_subMerchantName").value = businessName;
		document.getElementById("recheck_mmId").value = mmid;

		var dto;
		try {
			dto = JSON.parse(mdrdetails);
		} catch (e) {
			console.error("Failed to parse mdrdetails JSON:", e);
			return;
		}



			console.log("mdr fpx : ", dto);

			document.getElementById("fpx_merchantmdr").value = dto.fpx?.merchantmdr || '0.00';
			document.getElementById("fpx_hostmdr").value = dto.fpx?.hostmdr || '0.00';
			document.getElementById("fpx_mobimdr").value = dto.fpx?.mobimdr || '0.00';
			document.getElementById("fpx_minimummdr").value = dto.fpx?.minimummdr || '0.00';
			document.getElementById("localdebitvisamdr").value = dto.cards?.visa?.localdebitmdr || '0.00';
			document.getElementById("localcreditvisamdr").value = dto.cards?.visa?.localcreditmdr || '0.00';
			document.getElementById("foreigndebitvisamdr").value = dto.cards?.visa?.foriegndebitmdr || '0.00';
			document.getElementById("foreigncreditvisamdr").value = dto.cards?.visa?.foriegncreditmdr || '0.00';
			document.getElementById("localdebitmastermdr").value = dto.cards?.master?.localdebitmdr || '0.00';
			document.getElementById("localcreditmastermdr").value = dto.cards?.master?.localcreditmdr || '0.00';
			document.getElementById("foreigndebitmastermdr").value = dto.cards?.master?.foriegndebitmdr || '0.00';
			document.getElementById("foreigncreditmastermdr").value = dto.cards?.master?.foriegncreditmdr || '0.00';
			document.getElementById("localdebitunionmdr").value = dto.cards?.union?.localdebitmdr || '0.00';
			document.getElementById("localcreditunionmdr").value = dto.cards?.union?.localcreditmdr || '0.00';
			document.getElementById("foreigndebitunionmdr").value = dto.cards?.union?.foriegndebitmdr || '0.00';
			document.getElementById("foreigncreditunionmdr").value = dto.cards?.union?.foriegncreditmdr || '0.00';
			document.getElementById("boost_merchantmdr").value = dto.ewallet?.boost?.merchantmdr || '0.00';
			document.getElementById("boost_hostmdr").value = dto.ewallet?.boost?.hostmdr || '0.00';
			document.getElementById("boost_mobimdr").value = dto.ewallet?.boost?.mobimdr || '0.00';
			document.getElementById("boost_minimummdr").value = dto.ewallet?.boost?.minimummdr || '0.00';
			document.getElementById("grab_merchantmdr").value = dto.ewallet?.grab?.merchantmdr || '0.00';
			document.getElementById("grab_hostmdr").value = dto.ewallet?.grab?.hostmdr || '0.00';
			document.getElementById("grab_mobimdr").value = dto.ewallet?.grab?.mobimdr || '0.00';
			document.getElementById("grab_minimummdr").value = dto.ewallet?.grab?.minimummdr || '0.00';
			document.getElementById("tng_merchantmdr").value = dto.ewallet?.tng?.merchantmdr || '0.00';
			document.getElementById("tng_hostmdr").value = dto.ewallet?.tng?.hostmdr || '0.00';
			document.getElementById("tng_mobimdr").value = dto.ewallet?.tng?.mobimdr || '0.00';
			document.getElementById("tng_minimummdr").value = dto.ewallet?.tng?.minimummdr || '0.00';
			document.getElementById("spp_merchantmdr").value = dto.ewallet?.spp?.merchantmdr || '0.00';
			document.getElementById("spp_hostmdr").value = dto.ewallet?.spp?.hostmdr || '0.00';
			document.getElementById("spp_mobimdr").value = dto.ewallet?.spp?.mobimdr || '0.00';
			document.getElementById("spp_minimummdr").value = dto.ewallet?.spp?.minimummdr || '0.00';
			document.getElementById("payout_merchantmdr").value = dto.payout?.merchantmdr || '0.00';
			document.getElementById("payout_hostmdr").value = dto.payout?.hostmdr || '0.00';
			document.getElementById("payout_mobimdr").value = dto.payout?.mobimdr || '0.00';
			document.getElementById("payout_minimummdr").value = dto.payout?.minimummdr || '0.00';






	}




	function closePreviewPopup() {

        document.getElementById("preview_overlay").style.display = "none";
        document.querySelector('.preview_modal_content').scrollTop = 0;

		var fpx = document.getElementById("fpx_mdr_btn");
		var ewallet = document.getElementById("ewallet_mdr_btn");
		var cards = document.getElementById("cards_mdr_btn");
		var payout = document.getElementById("payout_mdr_btn");
		var fpx_content = document.getElementById("fpx_container");
		var ewallet_content = document.getElementById("ewallet_container");
		var cards_content = document.getElementById("cards_container");
		var payout_content = document.getElementById("payout_container");
		fpx.classList.add('tab_active');
		ewallet.classList.remove('tab_active');
		cards.classList.remove('tab_active');
		payout.classList.remove('tab_active');
		fpx_content.style.display = "block";
		ewallet_content.style.display = "none";
		cards_content.style.display = "none";
		payout_content.style.display = "none";

    }

    function openRecheckPopup(){

        closePreviewPopup();
        document.getElementById("confirm_overlay").style.display = "block";

    }

    function closeRecheckModal(){

        document.getElementById("confirm_overlay").style.display = "none";
        const issueForm = document.getElementById('issueUpdateForm');
        issueForm.reset();

    }

    function updateRecheckPopup(result) {

        document.getElementById("result_overlay").style.display = "block";

        const approveImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Successful.svg';
        const rejectImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Declined.svg';
        const result_popup_image = document.getElementById('result_popup_image');
        const result_popup_text = document.getElementById('result_popup_text');

        if (result === 'true') {
            result_popup_image.src = approveImgPath;
            result_popup_text.innerText = 'Recheck request has been successfully sent.';
        } else if (result === 'false') {
            result_popup_image.src = rejectImgPath;
            result_popup_text.innerText = 'An error occurred while sending the Recheck request. Please try again later.';
        }
        
    }
    

    function updateResultPopup(result) {

        document.getElementById("result_overlay").style.display = "block";

        const approveImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Successful.svg';
        const rejectImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Declined.svg';
        const result_popup_image = document.getElementById('result_popup_image');
        const result_popup_text = document.getElementById('result_popup_text');

        if (result === 'true') {
            result_popup_image.src = approveImgPath;
            result_popup_text.innerText = 'Approval request has been successfully sent.';
        } else if (result === 'false') {
            result_popup_image.src = rejectImgPath;
            result_popup_text.innerText = 'An error occurred while sending the approval request. Please try again later.';
        }
        
      /*   if (issue === 'true') { */
           /*  if (result === 'true') {
                result_popup_image.src = approveImgPath;
                result_popup_text.innerText = 'Issue has been successfully notified.';
            } else if (result === 'false') {
                result_popup_image.src = rejectImgPath;
                result_popup_text.innerText = 'An error occurred while notifying the issue. Please try again later.';
            } */
     /*    } else if(issue === 'false'){
            if (result === 'true') {
                result_popup_image.src = approveImgPath;
                result_popup_text.innerText = 'Approval request has been successfully sent.';
            } else if (result === 'false') {
                result_popup_image.src = rejectImgPath;
                result_popup_text.innerText = 'An error occurred while sending the approval request. Please try again later.';
            }
        } */

    }

    document.addEventListener('DOMContentLoaded', () => {
        const result = document.getElementById("status_update_result");
       /*  const issue = document.getElementById("issue_update_result"); */
     /*    console.log(result.value) */
     const resultRecheck = document.getElementById("status_recheck_result");
        if ((result && result.value) ) {
            updateResultPopup(result.value);
        }
        
        if((resultRecheck&&resultRecheck.value)){
        	 updateRecheckPopup(resultRecheck.value);
        }
    });

    $(function () {

        $('#merchantName').on('change', function () {
            var url = $(this).val(); // get selected value

            if (url) {
                window.location = url;
            }
            return false;
        });
    });


    $(document).ready(function () {
        $(".select-filter").select2();


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


</script>


	<script>

    console.log("size ; ", ${paginationBean.itemList.size()});

    function loadSelectData2() {

        $("#overlay").show();

        var e = document.getElementById("FromDate").value;
        var e1 = document.getElementById("ToDate").value;
        var currPage = document.getElementById("pgnum").value;
        var merchantId = document.getElementById("merchantId").value;

        var merchantName = document.getElementById("merchant_name").value;
        var mmId = "";
        
        console.log(e + " " + e1 + " " + currPage + " " + merchantId);


        var fromDate = new Date(e); //.toDateString("yyyy-MM-dd");
        var toDate = new Date(e1); //.toDateString("yyyy-MM-dd");

        var fromday = fromDate.getDate();
        var frommon = fromDate.getMonth() + 1;
        var fromyear = fromDate.getFullYear();

        var today = toDate.getDate();
        var tomon = toDate.getMonth() + 1;
        var toyear = toDate.getFullYear();

        var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
        var todateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;

        
        document.location.href = '${pageContext.request.contextPath}/submerchant/operation-parent/search/submerchant?mmId='+ mmId+'&merchantName='+ merchantName+'&currPage=' + currPage ;


       <%--  if (merchantId !== null && merchantId !== '') {

            document.location.href = '${pageContext.request.contextPath}/transaction/searchdepositDetailsUsingId?merchantId='
               + merchantId + '&currPage=' + currPage;

        } else {

            document.location.href = '${pageContext.request.contextPath}/transaction/depositDetails?date='
               + fromdateString + '&date1=' + todateString + '&currPage=' + currPage;

        } --%>


    }


    /* * * * * * * * * * * * * * * * *
     * Pagination
     * javascript page navigation
     * * * * * * * * * * * * * * * * */

    var fromDateServer = document.getElementById("FromDate").value = "${fromDate}";
    var from1DateServer = document.getElementById("ToDate").value = "${toDate}";
    <%--var merchant_Id = document.getElementById("merchantId").value="${merchantId}";--%>

    var size = "${paginationBean.querySize}";

    console.log("size " + size)


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

            // Pagination.size = data.size || 300;

            //console.log(Pagination.size);

//                 Pagination.size = Math.ceil(${paginationBean.querySize}/10) ||100;

            //set max page number

            Pagination.size = Math.ceil(${paginationBean.querySize} / 20);

//                 Pagination.size = ((${paginationBean.currPage})+4) ||100;

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

            var lastPage = Math.ceil(${paginationBean.querySize} / 20);

            // three pg no after 1st pg no

            if (lastPage > Pagination.page + 3) {

                // generate <a> tag for 3 pg no

                for (var i = Pagination.page + 1; i <= Pagination.page + 3; i++) {

                    Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';

                }

                Pagination.code += '<i>...</i>';

//                  Pagination.code += '<a onclick="dynamic(' + lastPage + ')">' + lastPage + '</a>';

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

            if (${paginationBean.currPage} == 1
        )
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

            size: 20, // pages size

            page: 1,  // selected page

            step: 3   // pages before and after current

        });

    };

    document.addEventListener('DOMContentLoaded', init, false);


</script>
</body>
</html>