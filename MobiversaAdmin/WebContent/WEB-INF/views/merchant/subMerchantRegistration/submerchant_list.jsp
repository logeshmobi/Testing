<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>sub merchant list</title>

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
:root {
	--orange: #F9C84C;
	--green: #51CB49;
	--red: #F34657;
	--purple: #7B68EE;
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

.active_status, .pending, .suspended, .terminated, .rejected, #status_in_popup
	{
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

.recheck span {
	color: var(--purple);
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
	box-shadow: 5px 5px 10px -3px rgba(0, 90, 170, 0.2), /* Right shadow */
                    -5px 5px 10px -3px rgba(0, 90, 170, 0.2),
		/* Left shadow */
                    0 5px 10px -3px rgba(0, 90, 170, 0.2) !important;
	/* Bottom shadow */
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
	width: 40%;
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

#reason_textbox {
	width: 70%;
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
	height: 70vh;
	overflow-y: auto;
	scrollbar-width: none;
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
								<strong class="heading_text">Sub Merchant List</strong>
							</h3>
							<div class="request_option">
								<div id="request_badge" class="badge"></div>
								<button id="request_btn" class="btn request_btn " type="button"
									onclick="checkRequest()">
									<img
										src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/requests.svg"
										width="18" height="18"> <span>Requests</span>
								</button>
							</div>
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


										<c:forEach var="merchant" items="${mainmerchant}">
											<%--   <c:if test="${merchant[0] != ''}"> --%>
											<%-- 	 <option value="${pageContext.request.contextPath}/submerchant/operation-child/search/submerchant?mmId=${merchant.merchant.mmId}&merchantName=${merchant.merchant.businessName}">
                                                    ${merchant.merchant.mmId}
                                            </option> 
 --%>

											<option
												value="${pageContext.request.contextPath}/submerchant/operation-child/search/submerchant?mmId=${merchant.mmId}&merchantName=${merchant.businessName}">
												${merchant.businessName}</option>
											<%--   </c:if> --%>
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
											<th class="">Sub merchant MID</th>
											<th class="">Main Merchant</th>
											<th style="">Status</th>
											<th style="text-align: -webkit-center;">Preview</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${submerchantList}" var="dto">
											<tr>
											<!--  ${dto.createdDate}-->
												<td>${fn:substring(dto.createdDate, 0, 10)}</td>
												<td>${dto.businessName}</td>
												<td>${dto.subMerchantMID}</td>
												<td>${dto.mmId}</td>


												<td style="" id="status"><c:if
														test="${dto.status == 'ACTIVE'}">
														<div class="active_status">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/active.svg"
																width="18" height="18"> <span>Active</span>
														</div>
													</c:if> <c:if
														test="${dto.status == 'PENDING' || dto.status == 'APPROVED' || dto.status == 'SUBMITTED'}">
														<div class="pending">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/pending.svg"
																width="19" height="19"> <span>Pending</span>
														</div>
													</c:if>
													
													<c:if
														test="${dto.status == 'REPOST'}">
														<div class="recheck">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/recheck.svg"
																width="19" height="19"> <span>Recheck</span>
														</div>
													</c:if>
													
													 <c:if test="${dto.status == 'SUSPENDED'}">
														<div class="suspended">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/suspended.svg"
																width="20" height="20"> <span>Suspended</span>
														</div>
													</c:if> <c:if test="${dto.status == 'TERMINATED'}">
														<div class="terminated">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/terminated.svg"
																width="20" height="20"> <span>Terminated</span>
														</div>
													</c:if> 
													
													<c:if test="${dto.status == 'REJECTED'}">
														<div class="rejected">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/rejected.svg"
																width="20" height="20"> <span>Rejected</span>
														</div>
													</c:if>
													
													
													
													</td>


												<td style="text-align: -webkit-center;" id="more_details">
													<button class="btn_more"
														onclick="openPreviewPopup('${dto.status}', '${dto.createdDate}', '${dto.businessName}', '${dto.subMerchantMID}', '${dto.country}', '${dto.website}', '${dto.email}','${dto.natureOfBusiness}','${dto.subMerchantDescription}')">
														<img
															src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/more.svg"
															width="20" height="20" alt="">
													</button>
												</td>


											</tr>

										</c:forEach>



										<c:if test="${paginationBean.itemList.size() == 0 }">
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
									type="button" onclick="closeResultModal()" name="action">Close
								</button>

							</div>
						</div>
					</div>
				</div>
			</div>


			<%--       preview popup --%>

			<%--        <div class="preview_overlay" id="preview_overlay">--%>
			<%--            <div class="row modal_row">--%>
			<%--                <div class="col offset-l3 offset-m2 s12 m8 l7">--%>
			<%--                    <div id="preview-modal" class="preview_modal" method="post">--%>
			<%--                        <form action="${pageContext.request.contextPath}/registartion/submerchantStatusUpdate/"--%>
			<%--                              method="post" id="previewForm">--%>
			<%--                            <input type="hidden" name="${_csrf.parameterName}"--%>
			<%--                                   value="${_csrf.token}">--%>
			<%--                            <div class="main_contents">--%>
			<%--                                <div class="modal-header">--%>
			<%--                                    <p class="mb-0  popup_head">Sub Merchant Details <a id="close_xmark"--%>
			<%--                                    >--%>
			<%--                                        <img src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/cross_mark.svg"--%>
			<%--                                             width="12" height="12">--%>
			<%--                                    </a></p>--%>

			<%--                                </div>--%>

			<%--                                <div class="modal-content preview_modal_content">--%>
			<%--                                    <div>--%>
			<%--                                        <p class="header_text mb-0">Sub Merchant Details</p>--%>
			<%--                                    </div>--%>
			<%--                                    <div>--%>
			<%--                                        <table class="preview_table">--%>
			<%--                                            <thead>--%>
			<%--                                            </thead>--%>
			<%--                                            <tbody>--%>
			<%--                                            <tr>--%>
			<%--                                                <td class="left_data">Activated Date</td>--%>
			<%--                                                <td class="hyphen hyphen2">-</td>--%>
			<%--                                                <td class="right_data" id="date">04-06-2024</td>--%>
			<%--                                            </tr>--%>
			<%--                                            <tr>--%>
			<%--                                                <td class="left_data">Business Name</td>--%>
			<%--                                                <td class="hyphen hyphen2">-</td>--%>
			<%--                                                <td class="right_data" id="businessName">NEW WAY TECHNOLOGIES LIMITED</td>--%>
			<%--                                            </tr>--%>

			<%--                                            <tr>--%>
			<%--                                                <td class="left_data">Sub Merchant MID</td>--%>
			<%--                                                <td class="hyphen hyphen2">-</td>--%>
			<%--                                                <td class="right_data" id="submerchantMID">201100000000185</td>--%>
			<%--                                            </tr>--%>
			<%--                                            <tr>--%>
			<%--                                                <td class="left_data">Country</td>--%>
			<%--                                                <td class="hyphen hyphen2">-</td>--%>
			<%--                                                <td class="right_data" id="country">Malaysia</td>--%>
			<%--                                            </tr>--%>
			<%--                                            <tr>--%>
			<%--                                                <td class="left_data">Website</td>--%>
			<%--                                                <td class="hyphen hyphen2">-</td>--%>
			<%--                                                <td class="right_data">--%>
			<%--                                                    <a class="website_link"--%>
			<%--                                                       href="https://www.newway.com" id="website">www.newway.com</a>--%>
			<%--                                                </td>--%>
			<%--                                            </tr>--%>
			<%--                                            <tr>--%>
			<%--                                                <td class="left_data">Email</td>--%>
			<%--                                                <td class="hyphen hyphen2">-</td>--%>
			<%--                                                <td class="right_data" id="email">newway@contact.in</td>--%>
			<%--                                            </tr>--%>
			<%--                                            <tr>--%>
			<%--                                                <td class="left_data">Industry</td>--%>
			<%--                                                <td class="hyphen hyphen2">-</td>--%>
			<%--                                                <td class="right_data" id="industry">Travel & Hotel Services</td>--%>
			<%--                                            </tr>--%>
			<%--                                            <tr>--%>
			<%--                                                <td class="left_data">Notes</td>--%>
			<%--                                                <td class="hyphen hyphen2">-</td>--%>
			<%--                                                <td class="right_data" id="notes">please Recheck the MDR Amount for Ewallets</td>--%>
			<%--                                            </tr>--%>
			<%--                                            <tr>--%>
			<%--                                                <td class="left_data">Status</td>--%>
			<%--                                                <td class="hyphen hyphen2">-</td>--%>
			<%--                                                <td class="right_data">--%>
			<%--                                                    <div id="status_in_popup">--%>
			<%--                                                        <img id="status-img" src="" width="18" height="18">--%>
			<%--                                                        <span id="status-text"></span>--%>
			<%--                                                    </div>--%>

			<%--                                                </td>--%>
			<%--                                            </tr>--%>
			<%--                                            </tbody>--%>
			<%--                                        </table>--%>

			<%--                                        <c:if test="${status != 'REJECTED'}">--%>
			<%--                                            <div>--%>
			<%--                                                <p class="left_data">Action</p>--%>
			<%--                                                <div class="status_radio">--%>

			<%--                                                    <c:if test="${status == 'ACTIVE' || status == 'SUSPENDED' || status == 'PENDING'}">--%>
			<%--                                                        <label>--%>
			<%--                                                            <input type="radio" id="terminate_status_update"--%>
			<%--                                                                   name="status_update" value="Terminate">--%>
			<%--                                                            <span class="label_text right_data">Terminated</span>--%>
			<%--                                                        </label>--%>
			<%--                                                    </c:if>--%>
			<%--                                                    <c:if test="${status == 'ACTIVE'}">--%>
			<%--                                                        <label>--%>
			<%--                                                            <input type="radio" id="suspend_status_update"--%>
			<%--                                                                   name="status_update"--%>
			<%--                                                                   value="Suspend"--%>
			<%--                                                            > <span class="label_text right_data">Suspended</span>--%>
			<%--                                                        </label>--%>
			<%--                                                    </c:if>--%>
			<%--                                                    <c:if test="${status == 'TERMINATED' || status == 'SUSPENDED' || status == 'PENDING'}">--%>
			<%--                                                        <label>--%>
			<%--                                                            <input type="radio" id="active_status_update"--%>
			<%--                                                                   name="status_update"--%>
			<%--                                                                   value="Active"--%>
			<%--                                                            > <span class="label_text right_data">Active</span>--%>
			<%--                                                        </label>--%>
			<%--                                                    </c:if>--%>
			<%--                                                    <c:if test="${status == 'TERMINATED'}">--%>
			<%--                                                        <label>--%>
			<%--                                                            <input type="radio" id="pending_status_update"--%>
			<%--                                                                   name="status_update"--%>
			<%--                                                                   value="Pending"--%>
			<%--                                                            > <span class="label_text right_data">Pending</span>--%>
			<%--                                                        </label>--%>
			<%--                                                    </c:if>--%>

			<%--                                                </div>--%>
			<%--                                                <textarea rows="4" cols="4" id="reason_textbox" name="comment"--%>
			<%--                                                          class="reason_text"--%>
			<%--                                                          placeholder="Type reason here..."></textarea>--%>


			<%--                                            </div>--%>
			<%--                                        </c:if>--%>


			<%--                                    </div>--%>

			<%--                                </div>--%>
			<%--                            </div>--%>
			<%--                            <div class="align-center modal-footer footer">--%>
			<%--                                <button id="close_btn" class="btn cancelbtn" type="button"--%>
			<%--                                        name="action">Cancel--%>
			<%--                                </button>--%>
			<%--                                <button id="confirm_preview_update_btn" class="btn confirmbtn " type="submit" ${status == 'Rejected' ? 'disabled' : ''}>Confirm--%>
			<%--                                </button>--%>

			<%--                            </div>--%>
			<%--                        </form>--%>
			<%--                    </div>--%>
			<%--                </div>--%>
			<%--            </div>--%>

			<%--        </div>--%>


			<div class="preview_overlay" id="preview_overlay"
				style="display: none;">
				<div class="row modal_row">
					<div class="col offset-l3 offset-m2 s12 m8 l7">
						<div id="preview-modal" class="preview_modal">
							<form
								action="${pageContext.request.contextPath}/submerchant/operation-child/preview/submerchant"
								method="post" id="previewForm">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}"> <input type="hidden"
									id="submerchantBusinessName" name="submerchantBusinessName"
									value="" /> <input type="hidden" name="currPage"
									id="pageNumber" value="${paginationBean.currPage}">

								<div class="main_contents">
									<div class="modal-header">
										<p class="mb-0  popup_head">
											Sub Merchant Details <a id="close_xmark"> <img
												src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/cross_mark.svg"
												width="12" height="12">
											</a>
										</p>
									</div>

									<div class="modal-content preview_modal_content">
										<div>
											<p class="header_text mb-0">Sub Merchant Details</p>
										</div>
										<div>
											<table class="preview_table">
												<thead>
												</thead>
												<tbody>
													<tr>
														<td class="left_data">Activated Date</td>
														<td class="hyphen hyphen2">-</td>
														<td class="right_data" id="date"></td>
													</tr>
													<tr>
														<td class="left_data">Business Name</td>
														<td class="hyphen hyphen2">-</td>
														<td class="right_data" id="businessName"></td>
													</tr>

													<tr>
														<td class="left_data">Sub Merchant MID</td>
														<td class="hyphen hyphen2">-</td>
														<td class="right_data" id="submerchantMID"></td>
													</tr>
													<tr>
														<td class="left_data">Country</td>
														<td class="hyphen hyphen2">-</td>
														<td class="right_data" id="country"></td>
													</tr>
													<tr>
														<td class="left_data">Website</td>
														<td class="hyphen hyphen2">-</td>
														<td class="right_data"><a class="website_link"
															href="#" id="website"></a></td>
													</tr>
													<tr>
														<td class="left_data">Email</td>
														<td class="hyphen hyphen2">-</td>
														<td class="right_data" id="email"></td>
													</tr>
													<tr>
														<td class="left_data">Industry</td>
														<td class="hyphen hyphen2">-</td>
														<td class="right_data" id="industry"></td>
													</tr>
													<tr>
														<td class="left_data">Notes</td>
														<td class="hyphen hyphen2">-</td>
														<td class="right_data" id="notes"></td>
													</tr>
													<tr>
														<td class="left_data">Status</td>
														<td class="hyphen hyphen2" style="flex: 0.18 !important;">-</td>
														<td class="right_data">
															<div id="status_in_popup">
																<img id="status-img" src="" width="18" height="18">
																<span id="status-text"></span>
															</div>
														</td>
													</tr>
												</tbody>
											</table>
											<div id="action_row" style="display: none;">
												<p class="left_data">Action</p>
												<div colspan="2" class="right_data">
													<div class="status_radio">
														<label id="terminate_label" style="display: none;">
															<input type="radio" id="terminate_status_update"
															name="status_update" value="terminated"> <span
															class="label_text">Terminated</span>
														</label> <label id="suspend_label" style="display: none;">
															<input type="radio" id="suspend_status_update"
															name="status_update" value="suspended"> <span
															class="label_text">Suspended</span>
														</label> <label id="active_label" style="display: none;">
															<input type="radio" id="active_status_update"
															name="status_update" value="Active"> <span
															class="label_text">Active</span>
														</label> <label id="pending_label" style="display: none;">
															<input type="radio" id="pending_status_update"
															name="status_update" value="Pending"> <span
															class="label_text">Pending</span>
														</label>
														
														<label id="recheck_label" style="display: none;">
															<input type="radio" id="pending_status_update"
															name="status_update" value="repost"> <span
															class="label_text">Rechecked</span>
														</label>
														
													</div>
													<textarea rows="4" cols="4" id="reason_textbox"
														name="comment" class="reason_text"
														placeholder="Type reason here..."></textarea>
												</div>
											</div>

										</div>
									</div>
								</div>
								<div class="align-center modal-footer footer">
									<button id="close_btn" class="btn cancelbtn" type="button"
										name="action">Cancel</button>
									<button id="confirm_preview_update_btn" class="btn confirmbtn"
										type="submit" disabled>Confirm</button>
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
	<input type="hidden" id="requestCount" value="${requestCount}" />
	<input type="hidden" id="status_value" value="" />

	<input type="hidden" id="merchant_name" value="${merchantName}" />

	</div>


	<script>

    document.addEventListener('DOMContentLoaded', function () {
        fetchRequestCount();
    });

    function checkRequest() {

        document.getElementById("overlay").style.display = "block";
        document.location.href = '${pageContext.request.contextPath}/submerchant/operation-child/request/initial/submerchant';

    }

    function fetchRequestCount() {
        const requestCount = document.getElementById("requestCount").value;
        updateBadge(requestCount);
    }

    function updateBadge(count) {
        const badge = document.getElementById('request_badge');
        if (count > 9) {
            badge.innerText = '9+';
        } else {
            badge.innerText = count;
        }
    }

    function displayStatus(status) {
        const statusimage = document.getElementById("status-img");
        const statustext = document.getElementById("status-text");

        statustext.style.marginLeft = "5px";
        statustext.style.fontWeight = "500";

        switch (status.toUpperCase()) {
            case "ACTIVE":
                statusimage.src = "${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/active.svg";
                statustext.innerText = "Active";
                statustext.style.color = "var(--green)";
                break;
            case "SUBMITTED":
            	  statusimage.src = "${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/pending.svg";
                  statustext.innerText = "Pending";
                  statustext.style.color = "var(--orange)";
                  break;
            case "PENDING":
                statusimage.src = "${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/pending.svg";
                statustext.innerText = "Pending";
                statustext.style.color = "var(--orange)";
                break;
                
            case "REPOST":
                statusimage.src = "${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/recheck.svg";
                statustext.innerText = "Recheck";
                statustext.style.color = "var(--purple)";
                break;
            case "APPROVED":
                statusimage.src = "${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/pending.svg";
                statustext.innerText = "Pending";
                statustext.style.color = "var(--orange)";
                break;    
            case "SUSPENDED":
                statusimage.src = "${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/suspended.svg";
                statustext.innerText = "Suspended";
                statustext.style.color = "var(--red)";
                break;
            case "TERMINATED":
                statusimage.src = "${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/terminated.svg";
                statustext.innerText = "Terminated";
                statustext.style.color = "var(--red)";
                statusimage.width = "22"
                statusimage.height = "22"
                break;
            case "REJECTED":
                statusimage.src = "${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/rejected.svg";
                statustext.innerText = "Rejected";
                statustext.style.color = "var(--red)";
                statusimage.width = "22"
                statusimage.height = "22"

                break;
            default:

                statusimage.src = "";
                statustext.innerText = "";
                break;
        }
    }


    function openMoreDetailsPopup() {
        document.getElementById("action_overlay").style.display = "block";
    }


    function closeResultModal() {

        document.getElementById("result_overlay").style.display = "none";
    }

    var sentPageNo = document.getElementById("pageNumber");
    var currpage = document.getElementById("pgnum").value;
    
      console.log("curr page:", currpage);
   /*  if (sentPageNo && currpage !== null) {
        sentPageNo.value = currpage;
        console.log("curr page:", currpage, sentPageNo.value);
    } else {
    	 console.log("Element not found or value is null");
    
    } */
        

    document.addEventListener('DOMContentLoaded', function () {
        const radioButtons = document.querySelectorAll('input[name="status_update"]');
        const textarea = document.getElementById('reason_textbox');
        const proceedButton = document.getElementById('confirm_preview_update_btn');
        const closeButton = document.getElementById('close_btn');
        const close_xmarkBtn = document.getElementById('close_xmark');
        const actionModal = document.getElementById("preview_overlay");
        const form = document.getElementById('previewForm');
        const overlay = document.getElementById('overlay');
        
        
        

        

        function validateForm() {
            if (textarea && radioButtons) {
                const isRadioSelected = Array.from(radioButtons).some(radio => radio.checked);
                const isTextareaFilled = textarea.value.trim().length > 0;

                if (isRadioSelected && isTextareaFilled) {
                    proceedButton.disabled = false;
                    proceedButton.style.opacity = 1;
                } else {
                    proceedButton.disabled = true;
                    proceedButton.style.opacity = 0.5;
                }
            }
        }


        if (radioButtons) {
            radioButtons.forEach(radio => {
                radio.addEventListener('change', validateForm);
            });
        }
        if (textarea) {
            textarea.addEventListener('input', validateForm);
        }

        validateForm();

        proceedButton.addEventListener('click', function (event) {
            event.preventDefault();
            actionModal.style.display = "none"
            overlay.style.display = 'block';
            
           

            setTimeout(() => {
                form.submit();
            }, 5000);
        });

        closeButton.addEventListener('click', function () {
            closePreviewPopup();
            validateForm();
        });

        close_xmarkBtn.addEventListener('click', function () {
            closePreviewPopup();
            validateForm();
        });


    });

    function openPreviewPopup(status, activatedDate, businessName, submerchantMID, country, website, email, industry, notes) {
        // Update modal content
        document.getElementById("date").innerText = activatedDate;
        document.getElementById("businessName").innerText = businessName;
        document.getElementById("submerchantMID").innerText = submerchantMID;
        document.getElementById("country").innerText = country;
        document.getElementById("website").innerText = website;
        document.getElementById("email").innerText = email;
        document.getElementById("industry").innerText = industry;
        document.getElementById("notes").innerText = notes;
        document.getElementById("submerchantBusinessName").value = businessName;

        // Display status (you should have a function to handle this)
        displayStatus(status);

        // Show/hide action row based on status
        const actionRow = document.getElementById("action_row");
        if (status === 'REPOST' || status === 'ACTIVE' || status === 'SUSPENDED' || status === 'TERMINATED') {
            actionRow.style.display = "block";
        } else {
            actionRow.style.display = "none";
        }

        // Show/hide radio buttons based on status
        const terminateLabel = document.getElementById("terminate_label");
        const suspendLabel = document.getElementById("suspend_label");
        const activeLabel = document.getElementById("active_label");
        const pendingLabel = document.getElementById("pending_label");
        
        const recheckLabel = document.getElementById("recheck_label");

        // Reset all radio buttons to hide initially
        terminateLabel.style.display = "none";
        suspendLabel.style.display = "none";
        activeLabel.style.display = "none";
        pendingLabel.style.display = "none";
        recheckLabel.style.display = "none";
        
        // Display appropriate radio button based on status
        switch (status) {
            case 'ACTIVE':
                suspendLabel.style.display = "inline-block";
                terminateLabel.style.display = "inline-block";
                break;
            case 'SUSPENDED':
                activeLabel.style.display = "inline-block";
                terminateLabel.style.display = "inline-block";
                break;
            case 'PENDING':
               /*  activeLabel.style.display = "inline-block";
                terminateLabel.style.display = "inline-block";
                */
                actionRow.style.display = "none";
                break;
            case 'REPOST':
            	recheckLabel.style.display = "inline-block";
                break;
            case 'SUBMITTED':
               /*  activeLabel.style.display = "inline-block";
                terminateLabel.style.display = "inline-block"; */
                actionRow.style.display = "none";
                break;
            case 'APPROVED':
               /*  activeLabel.style.display = "inline-block";
                terminateLabel.style.display = "inline-block"; */
                actionRow.style.display = "none";
                break;
            case 'TERMINATED':
                activeLabel.style.display = "inline-block";
                pendingLabel.style.display = "block";
                break;
            default:
                actionRow.style.display = "none";
                break;
        }

        // Enable/disable Confirm button based on status
        const confirmButton = document.getElementById("confirm_preview_update_btn");
        if (status === 'REJECTED') {
            confirmButton.disabled = true;
            confirmButton.style.opacity = 0.5;
        }

        // Show the modal overlay
        document.getElementById("preview_overlay").style.display = "block";
        // Scroll to the top of the modal content
        document.querySelector('.preview_modal_content').scrollTop = 0;
    }


    // function openPreviewPopup(status) {
    //
    //     let status_value = document.getElementById("status_value");
    //     status_value.value = status;
    //     displayStatus(status);
    //
    //
    //
    //     document.getElementById("preview_overlay").style.display = "block";
    //     document.querySelector('.preview_modal_content').scrollTop = 0;
    //
    //
    //
    // }

    function closePreviewPopup() {

        document.getElementById("preview_overlay").style.display = "none";
        document.getElementById("previewForm").reset();

        document.querySelector('.preview_modal_content').scrollTop = 0;

    }


    function updateResultPopup(result) {

        document.getElementById("result_overlay").style.display = "block";

        const approveImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Successful.svg';
        const rejectImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Declined.svg';
        const result_popup_image = document.getElementById('result_popup_image');
        const result_popup_text = document.getElementById('result_popup_text');

        if (result === 'true') {
            result_popup_image.src = approveImgPath;
            result_popup_text.innerText = 'Sub merchant status has been updated successfully..';
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

        var currPage = document.getElementById("pgnum").value;
        var merchantName = document.getElementById("merchant_name").value;
        var mmId = "";
       
       
      /*   document.location.href = '${pageContext.request.contextPath}/submerchant/operation-child/initial?&currPage=' + currPage;
 */
        document.location.href = '${pageContext.request.contextPath}/submerchant/operation-child/search/submerchant?mmId='+ mmId+'&merchantName='+ merchantName+'&currPage=' + currPage ;


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