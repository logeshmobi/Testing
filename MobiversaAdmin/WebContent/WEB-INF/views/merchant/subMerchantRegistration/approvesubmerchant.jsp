<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>approve sub merchant</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet">

<style>
@import
	url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap')
	;

/* body {
            font-family: "Poppins", sans-serif;
        } */
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

.align-right {
	text-align: right !important;
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

.complete_status {
	color: #41b441;
	font-weight: 500;
	margin-left: 5px;
}

.failed_status {
	color: red;
	font-weight: 500;
	margin-left: 5px;
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
	background-color: transparent;
	!
	important;
}

#more_details {
	/*cursor: pointer;*/
	display: flex;
	align-items: center;
	justify-content: center;
}

/*    modal */
.modal_container {
	display: none;
	position: fixed;
	z-index: 100;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgb(0, 0, 0);
	background-color: rgba(0, 0, 0, 0.4);
	scrollbar-width: none;
}

.more_details_modal_class {
	/* min-width: 400px !important;
                     width: fit-content !important;
                     max-width: 35% !important;*/
	background-color: #fff !important;
	border-radius: 10px !important;
	width: 80%;
	margin: 0 auto;
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

.confirm_modal_content {
	padding: 15px 35px !important;
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

.refno_info, .type_info {
	display: flex;
	margin-bottom: 10px !important;
}

.left_content {
	flex: 0.22;
	color: #005baa;
	white-space: nowrap;
	font-weight: 500;
}

.colon {
	flex: 0.05;
	color: #005baa;
	white-space: nowrap;
	font-weight: 500;
}

.right_content {
	flex: 1;
	color: #586570;
}

.reason_info {
	color: #005baa;
	margin-bottom: 10px !important;
	font-weight: 500;
}

.withdraw_reason_text::placeholder {
	color: #d0d0d0;
	font-size: 13px;
	padding: 5px;
}

.withdraw_reason_text {
	width: 100%;
	height: 6rem;
	padding: 5px;
	background-color: transparent;
	resize: none;
	border-radius: 5px;
	border: 1px solid #70707070;
	scrollbar-width: thin;
	color: #586570;
	font-family: "Poppins", sans-serif;
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
.popup_messages {
	color: #515151;
	font-size: 16px;
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

.preview_overlay, .action_overlay, .result_overlay {
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

.preview_modal, .action_modal, .result_modal {
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

.preview_table .left_data {
	color: #8D8D8D;
	font-weight: 600;
	flex: 0.8;
}

.preview_table .hyphen {
	color: #8D8D8D;
	font-weight: 600;
	flex: 0.3;
}

.preview_table .right_data {
	color: #8D8D8D;
	flex: 1;
	word-break: break-all !important;
}

#close_xmark {
	color: #005baa !important;
	font-weight: 400;
	float: right;
	cursor: pointer;
	border-radius: 50%;
	padding: 2px 6px;
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

.website_link {
	color: #005baa;
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
						<div class="d-flex align-items-center">
							<h3 class="text-white mb-0 ">
								<strong class="heading_text">Approve Sub Merchant</strong>

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


										<%--  <c:forEach var="merchant" items="${businessNamesAndIds}">
                                    <c:if test="${merchant[0] != ''}">
                                        <option value="${pageContext.request.contextPath}/transaction/searchdepositDetailsUsingId?merchantId=${merchant[1]}&merchantName=${merchant[0]}">
                                                ${merchant[0]}
                                        </option>

                                    </c:if>
                                </c:forEach> --%>


										<c:forEach var="merchant" items="${mainmerchantList}">
											<option
												value="${pageContext.request.contextPath}/submerchant/risk-compilence/search/submerchant?mmId=${merchant.mmId}&merchantName=${merchant.businessName}">
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
											<th style="text-align: -webkit-center;">Action</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${submerchantList}" var="dto">
											<tr>
										<td>${dto.merchant.createdDate}</td>
                                        <td>${dto.merchant.businessName}</td>
                                        <td>${dto.merchant.mid.subMerchantMID}</td> 
                                        <td>${dto.merchant.mmId}</td>
												<td style="text-align: -webkit-center;" id="preview">
													<button class="btn_more"
														onclick="openPreviewPopup('${dto.merchant.businessName}','${dto.merchant.email}','${dto.merchant.website}','${dto.merchant.natureOfBusiness}','${dto.merchant.country}')">
														<img
															src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/blureye.svg"
															width="20" height="20" alt="">
													</button>
												</td>

												<td style="text-align: -webkit-center;" id="more_details">
													<button class="btn_more" onclick="openMoreDetailsPopup('<c:out value="${dto.merchant.businessName}"/>')">
														<img
															src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/more.svg"
															width="20" height="20" alt="">
													</button>
												</td>


											</tr>

										</c:forEach>
										<c:if test="${paginationBean.querySize == 0 }">
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
								<p id="result_popup_text" class="align-center popup_messages"></p>
							</div>
							<div class="align-center modal-footer footer">
								<button id="closebtn_result" class="btn blue-btn closebtn"
									type="button" onclick="closeResultModal()" name="action">Close</button>

							</div>
						</div>
					</div>
				</div>
			</div>







			<%--       preview popup --%>

			<div class="preview_overlay" id="preview_overlay">
				<div class="row modal_row">
					<div class="col offset-l3 offset-m2 s12 m8 l7">
						<div id="preview-modal" class="preview_modal">
							<div class="main_contents">
								<div class="modal-header">
									<p class="mb-0  popup_head">
										Sub Merchant Preview <a id="close_xmark"
											onclick="closePreviewPopup()"> <img
											src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/cross_mark.svg"
											width="12" height="12">
										</a>
									</p>

								</div>
								<div class="modal-content ">
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
													<td class="hyphen">-</td>
													<td class="right_data" id="businessname"></td>
												</tr>
												<tr>
													<td class="left_data">Email</td>
													<td class="hyphen">-</td>
													<td class="right_data" id="email"></td>
												</tr>
												<tr>
													<td class="left_data">Website</td>
													<td class="hyphen">-</td>
													<td class="right_data"><a class="website_link"
														href="https://www.newway.com" id="website"></a></td>
												</tr>
												<tr>
													<td class="left_data">Industry</td>
													<td class="hyphen">-</td>
													<td class="right_data" id="industry"></td>
												</tr>
												<tr>
													<td class="left_data">Country</td>
													<td class="hyphen">-</td>
													<td class="right_data" id="country"></td>
												</tr>


											</tbody>
										</table>
									</div>

								</div>
							</div>
							<div class="align-center modal-footer footer">
								<button id="closebtn_preview" class="btn blue-btn closebtn"
									type="button" onclick="closePreviewPopup()" name="action">Close</button>

							</div>
						</div>
					</div>
				</div>
			</div>

			<%--        action popup --%>

			<div class="action_overlay" id="action_overlay">
				<div class="row modal_row">
					<div class="col offset-l4 offset-m3 s12 m6 l5">
						<div id="action-modal" class="action_modal">


							<div class="modal-header confirm_modal_header">
								<p class="mb-0">Confirmation</p>
							</div>
							<%-- <form
								action="${pageContext.request.contextPath}/registartion/statusApproveUpdate"
								id="approvalform" method="post">
								 --%>
								<form action="${pageContext.request.contextPath}/submerchant/risk-compilence/validate" id="approvalform" method="post">
                       
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}">
									
                         <input type="hidden" name="submerchantName" id="submerchantName" value="">
								<div class="modal-content confirm_modal_content ">
									<div class="align-center">
										<img id="statusImage"
											src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/addsubmerchant.svg"
											width="45" height="45">
									</div>

									<div class="radio-group">
										<label> <input type="radio" id="approved"
											name="decision" value="approve" checked
											onchange="updateContent()"> <span class="label_text">Approve</span>
										</label> <label> 
										<input type="radio" id="rejected"
											name="decision" value="reject" onchange="updateContent()">
											<span class="label_text">Reject</span>
										</label>
									</div>
									<textarea rows="4" cols="4" id="reason_text" name="comment"
										class="reason_text" placeholder="Type Something here..."></textarea>

								</div>
								<div class="align-center modal-footer footer">
									<button id="close_btn" class="btn cancelbtn" type="button"
										onclick="closeMoredetailsPopup()" name="action">Cancel</button>
									<button id="submit_approvalresult_btn" class="btn confirmbtn "
										type="submit">Proceed</button>

								</div>
							</form>

						</div>

					</div>

				</div>
			</div>


			<div id="pagination"></div>
			<input type="hidden" id="pgnum"> <input type="hidden"
				id="FromDate"> <input type="hidden" id="ToDate"> <input
				type="hidden" id="merchantId"> <input type="hidden"
				id="approve_result" value="${updateSubmerchantStatus}"> <input
				type="hidden" id="merchant_name" value="${merchantName}">

		</div>




		<script>


        document.addEventListener('DOMContentLoaded', function() {
            const radioButtons = document.querySelectorAll('input[name="decision"]');
            const textarea = document.getElementById('reason_text');
            const proceedButton = document.getElementById('submit_approvalresult_btn');
            const actionModal = document.getElementById("action_overlay");
            const form = document.getElementById('approvalform');
            const overlay = document.getElementById('overlay');

            function validateForm() {
                const selectedRadio = Array.from(radioButtons).find(radio => radio.checked);
                const isRadioSelected = !!selectedRadio;
                const isTextareaFilled = textarea.value.trim().length > 0;

                if (isRadioSelected) {
                    if (selectedRadio.value === 'reject' && isTextareaFilled) {
                        proceedButton.disabled = false;
                        proceedButton.style.opacity = 1;
                    } else if (selectedRadio.value === 'approve') {
                        proceedButton.disabled = false;
                        proceedButton.style.opacity = 1;
                    } else {
                        proceedButton.disabled = true;
                        proceedButton.style.opacity = 0.5;
                    }
                } else {
                    proceedButton.disabled = true;
                    proceedButton.style.opacity = 0.5;
                }
            }
            // function validateForm() {
            //     const isRadioSelected = Array.from(radioButtons).some(radio => radio.checked);
            //     const isTextareaFilled = textarea.value.trim().length > 0;
            //
            //     if (isRadioSelected && isTextareaFilled) {
            //         proceedButton.disabled = false;
            //         proceedButton.style.opacity = 1;
            //     } else {
            //         proceedButton.disabled = true;
            //         proceedButton.style.opacity = 0.5;
            //     }
            // }

            radioButtons.forEach(radio => {
                radio.addEventListener('change', validateForm);
            });

            textarea.addEventListener('input', validateForm);

            // Initialize button state
            validateForm();

            proceedButton.addEventListener('click', function(event) {
                event.preventDefault();
                actionModal.style.display = "none"
                overlay.style.display = 'block';

                setTimeout(() => {
                    form.submit();
                }, 2000);
            });
        });

        function closeMoredetailsPopup() {
            const actionOverlay = document.getElementById('action_overlay');
            actionOverlay.style.display = 'none';

            // Set the default radio button to checked
            const defaultRadioButton = document.getElementById('approved');
            defaultRadioButton.checked = true;
            updateContent();

            // Clear the textarea
            const textarea = document.getElementById('reason_text');
            textarea.value = '';

            // Reset the proceed button state
            const proceedButton = document.getElementById('submit_approvalresult_btn');
            proceedButton.disabled = true;
            proceedButton.style.opacity = 0.5;

            // Revalidate the form
            validateForm();
        }




        function openPreviewPopup(businessName,email,website,industry,country){
            document.getElementById("preview_overlay").style.display = "block";

            document.getElementById("businessname").innerText = businessName;
            document.getElementById("country").innerText = country;
            document.getElementById("website").innerText = website;
            document.getElementById("email").innerText = email;
            document.getElementById("industry").innerText = industry;
        }


        function openMoreDetailsPopup(submerchantName){
            document.getElementById("action_overlay").style.display = "block";
            document.getElementById("submerchantName").value = submerchantName;
            
            console.log("submerchantName :",submerchantName);
        }

        
        function closePreviewPopup(){
            document.getElementById("preview_overlay").style.display = "none";

        }

        /* function openMoreDetailsPopup(){
            document.getElementById("action_overlay").style.display = "block";
        } */


        function closeResultModal(){

            document.getElementById("result_overlay").style.display = "none";
        }

        function updateContent() {

            const approveImagePath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Successful.svg';
            const rejectImagePath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Declined.svg';
            const statusImage = document.getElementById('statusImage');
            const reasonText = document.getElementById('reason_text');

            if (document.querySelector('input[name="decision"]:checked').value === 'approve') {
                statusImage.src = approveImagePath;
                statusImage.width = 38;
                reasonText.placeholder = 'Type something here...';
            } else {
                statusImage.src = rejectImagePath;
                statusImage.width = 45;
                reasonText.placeholder = 'Type reason here...';
            }
        }

        document.addEventListener('DOMContentLoaded', () => {
            updateContent();
        });




        function updateResultPopup(result) {

            // closeMoredetailsPopup();
            // document.getElementById("overlay").style.display = "block";
            document.getElementById("result_overlay").style.display = "block";

            const approveImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Successful.svg';
            const rejectImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Declined.svg';
            const result_popup_image = document.getElementById('result_popup_image');
            const result_popup_text = document.getElementById('result_popup_text');

            if (result === 'true') {
                result_popup_image.src = approveImgPath;
                result_popup_text.innerText = 'Sub-merchant account approved and operations notified.';
            } else if(result === 'false') {
                result_popup_image.src = rejectImgPath;
                result_popup_text.innerText = 'This Sub Merchant request has been rejected.';
            }
        }

        document.addEventListener('DOMContentLoaded', () => {
            const result = document.getElementById("approve_result").value;
            if(result) {
                updateResultPopup(result);
            }
        });

        $(function(){

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

                $('#merchantName option').each(function() {
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

        console.log("size ; ",${paginationBean.itemList.size()});

        function loadSelectData2() {

             $("#overlay").show(); 

            var e = document.getElementById("FromDate").value;
            var e1 = document.getElementById("ToDate").value;
            var currPage = document.getElementById("pgnum").value;
            var merchantId = document.getElementById("merchantId").value;

            console.log(e+" "+e1+" "+currPage+" "+merchantId);
            var merchantName = document.getElementById("merchant_name").value;
            var mmId = "";

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


            document.location.href = '${pageContext.request.contextPath}/submerchant/risk-compilence/search/submerchant?mmId='+ mmId+'&merchantName='+ merchantName+'&currPage=' + currPage ;

        }


        /* * * * * * * * * * * * * * * * *
         * Pagination
         * javascript page navigation
         * * * * * * * * * * * * * * * * */

        var fromDateServer = document.getElementById("FromDate").value="${fromDate}";
        var from1DateServer = document.getElementById("ToDate").value="${toDate}";
        <%--var merchant_Id = document.getElementById("merchantId").value="${merchantId}";--%>

        var size ="${paginationBean.querySize}";

        console.log("size "+size)


        //Assuming you have a function to add a class to elements

        function addClass(element, className) {

            if (element.classList) {

                element.classList.add(className);

            } else {

                element.className += ' ' + className;

            }

        }




        function dynamic(pgNo){

            /* alert("Page Number:"+pgNo); */

            document.getElementById("pgnum").value=pgNo;

            loadSelectData2();

        }

        function previous(pgNo){

            /* alert("Page Number:"+pgNo); */

            pgNo--;

            document.getElementById("pgnum").value=pgNo;

            loadSelectData2();

        }

        function next(pgNo){

            /* alert("Page Number:"+pgNo); */

            pgNo++;

            document.getElementById("pgnum").value=pgNo;

            loadSelectData2();

        }


        var Pagination = {

            code: '',

            // --------------------

            // Utility

            // --------------------

            // converting initialize data

            Extend: function(data) {

                data = data || {};

                // Pagination.size = data.size || 300;

                //console.log(Pagination.size);

//                 Pagination.size = Math.ceil(${paginationBean.querySize}/10) ||100;

                //set max page number

                Pagination.size = Math.ceil(${paginationBean.querySize} / 20);

//                 Pagination.size = ((${paginationBean.currPage})+4) ||100;

                /* Pagination.page = data.page || 1; */

                Pagination.page = ${paginationBean.currPage} || 1;

                Pagination.step = ((data.step)-4) || 3;

            },

            // add pages by number (from [s] to [f])

            Add: function(s, f) {

                for (var i = s; i < f; i++) {

                    Pagination.code += '<a onclick="dynamic('+i+')">' + i + '</a>';

                }

            },




            // newer chnagess

            First: function() {

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

            Last: function() {

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

            Click: function() {

                Pagination.page = +this.innerHTML;

                Pagination.Start();

                dynamic(page);

            },

            // previous page

            Prev: function() {

                Pagination.page--;

                if (Pagination.page < 1) {

                    Pagination.page = 1;

                }

                Pagination.Start();

                dynamic(page);

            },


            // next page


            Next: function() {

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

            Bind: function() {

                var a = Pagination.e.getElementsByTagName('a');

                for (var i = 0; i < a.length; i++) {

                    if (+a[i].innerHTML === Pagination.page) a[i].className = 'current';

                    a[i].addEventListener('click', Pagination.Click, false);

                }

            },

            // write pagination

            Finish: function() {

                Pagination.e.innerHTML = Pagination.code;

                Pagination.code = '';

                Pagination.Bind();

            },

            // find pagination type

            Start: function() {

                if (Pagination.size < Pagination.step * 2 + 6) {

                    Pagination.Add(1, Pagination.size + 1);

                }

                else if (Pagination.page < Pagination.step * 2 + 1) {

                    Pagination.Add(1, Pagination.step * 2 + 4);

                    Pagination.Last();

                }

                else if (Pagination.page > Pagination.size - Pagination.step * 2) {

                    Pagination.First();

                    Pagination.Add(Pagination.size - Pagination.step * 2 - 2, Pagination.size + 1);

                }

                else {

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

            Buttons: function(e) {

                var nav = e.getElementsByTagName('a');

                nav[0].addEventListener('click', Pagination.Prev, false);

                nav[1].addEventListener('click', Pagination.Next, false);

            },

            // create skeleton

            Create: function(e) {

                var html = [

                    '<a onclick="previous(${paginationBean.currPage})" style="width:7rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px; " id="previous">&#60;&#60; Previous</a>', // previous button

                    '<span></span>',  // pagination container

                    '<a onclick="next(${paginationBean.currPage})" style="width:6.5rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;" id="nxt">Next &#62;&#62;</a>'  // next button

                ];


                e.innerHTML = html.join('');

                Pagination.e = e.getElementsByTagName('span')[0];

                Pagination.Buttons(e);

                if (${paginationBean.currPage} == 1) {

                    var previousButton = document.getElementById("previous");

                    previousButton.style.pointerEvents = "none";

                    previousButton.style.opacity = "0.5";

                }

                // my chnages

                if (${paginationBean.currPage} == Pagination.size) {

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

            Init: function(e, data) {

                Pagination.Extend(data);

                Pagination.Create(e);

                Pagination.Start();

            }

        };


        /* * * * * * * * * * * * * * * * *

        * Initialization

        * * * * * * * * * * * * * * * * */

        var init = function() {

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