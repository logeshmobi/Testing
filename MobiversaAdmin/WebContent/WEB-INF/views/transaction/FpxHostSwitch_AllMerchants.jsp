<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@page import="com.mobiversa.common.bo.MID"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page import="java.util.ResourceBundle"%>

<%
	ResourceBundle resource = ResourceBundle.getBundle("config");
	String actionimg = resource.getString("NEWACTION");
	String voidimg = resource.getString("NEWVOID");
	String refundimg = resource.getString("NEWREFUND");
	String eyeimg = resource.getString("NEWEYE");
%>
<html lang="en-US">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/virtual-select.min.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resourcesNew1/dist/js/virtual-select.min.js"></script>


</head>
<body>

	<input type="hidden" id="bussinessName" name="bussinessName" value="0">
	<input type="hidden" id="index" name="index" value="0">

	<div class="container-fluid">
		<div class="row">



			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>FPX Host Switch</strong>
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>


		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content">

						<div class="d-flex align-items-center">
							<h5>All Merchants</h5>
						</div>

						<div class="row  ">
							<div class="input-field col s12 m5 l5 bank_list">
								<p class="titleofinput">Select Host for all Merchants</p>

								<select id="host_name" name="host_name"
									placeholder="choose host" data-search="true">
									<option value="All" style="color: #DEDEDE;">Choose
										Host</option>
									<c:forEach var="host" items="${fpxHostList}">
										<option value="${host}">${host}</option>
									</c:forEach>
								</select>

							</div>

							<div class="input-field col s12 m7 l7 submitbtncolumn">
								<button type="button" id="submitButton"
									class="btn btn-primary curved-btn move-left custom-btn"
									onclick="loadSearch()" disabled>Submit</button>
							</div>

						</div>

						<br>

					</div>
				</div>
			</div>
		</div>


		<input type="hidden" id="isUpdated" value="${isUpdated}" />


		<script>
			VirtualSelect.init({
				ele : '#host_name'
			});

			document.addEventListener('DOMContentLoaded', function() {
				var typeBank = document.getElementById('host_name');
				var submitButton = document.getElementById('submitButton');

				// Initial state
				submitButton.disabled = true;
				submitButton.style.opacity = 0.5;

				typeBank.addEventListener('change', function() {
					enableSubmitButton();
				});

				function enableSubmitButton() {
					var selectedBank = typeBank.value;

					if (selectedBank !== 'All') {
						submitButton.disabled = false;
						submitButton.style.opacity = 1;
					} else {
						submitButton.disabled = true;
						submitButton.style.opacity = 0.5;
					}
				}
			});

			/*     function validateForm() {
			 var selectedBank = document.getElementById('bank_name').value;
			 return selectedBank !== 'All';
			 } */

			function loadSearch() {
				$("#overlay").show();
				var Value = document.getElementById("host_name").value;
				document.location.href = '${pageContext.request.contextPath}/transaction/updateAllMerchantHostId?VALUE='
						+ Value;
				form.submit;
			}

			function openConfirmApprovalPopup() {
				document.getElementById("confirm_approval_overlay").style.display = "block";
			}

			function closeConfirmApprovalPopup() {
				document.getElementById("confirm_approval_overlay").style.display = "none";
				const emails_input = document.getElementById("approval_emails");
				emails_input.value = '';
			}

			document.addEventListener('DOMContentLoaded', function() {

				var updated = document.getElementById("isUpdated").value;
				if (updated === "true") {
					openConfirmApprovalPopup();
				}
			});
		</script>


		<style>
.titleofinput {
	margin-bottom: 2px !important;
	color: #858585;
}

.card .padding-card {
	padding: none !important;
}

.vscomp-wrapper {
	color: #858585;
	display: inline-flex;
	flex-wrap: wrap;
	font-family: sans-serif;
	font-size: 15px;
	position: relative;
	text-align: left;
	width: 100%;
	border: none;
	border-bottom: 1px solid #ffa500;
}

.pop-comp-wrapper {
	z-index: 2;
	transform: none !important;
	max-width: 380px;
	display: inline-flex;
	transition-duration: 300ms;
	opacity: 1;
	box-shadow: none !important;
	position: absolute;
	top: 4.5vh !important;
	left: 0;
}

.vscomp-toggle-button {
	align-items: center;
	background-color: #fff;
	border: none;
	cursor: pointer;
	display: flex;
	padding: 7px 30px 7px 10px;
	position: relative;
	width: 100%;
}

.vscomp-toggle-all-checkbox {
	display: none !important;
}

.vscomp-search-input {
	width: 100%;
}

.vscomp-toggle-button
,
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




 




)
{
box-shadow








:




 




none




 




!
important








;
}
.vscomp-search-container {
	align-items: center;
	border-bottom: none;
	display: flex;
	height: 40px;
	padding: 0 5px 0 15px;
	position: relative;
}

.vscomp-dropbox {
	color: #858585;
}

.vscomp-option-text {
	font-size: 14px !important;
}

.bank_list .vscomp-wrapper.has-clear-button.has-value .vscomp-clear-button
	{
	display: none;
}

.vscomp-wrapper.multiple .vscomp-option.selected .checkbox-icon::after {
	transform: none !important;
	border-color: #512da8;
	border-left-color: rgba(0, 0, 0, 0);
	border-top-color: rgba(0, 0, 0, 0);
	width: 50%;
}

.vscomp-wrapper .checkbox-icon::after {
	transition-duration: none;
	border: none;
	content: "";
	display: inline-block;
	height: 100%;
	width: 100%;
}

.vscomp-option {
	align-items: center;
	cursor: pointer;
	display: flex;
	flex-wrap: nowrap;
	height: 40px;
	padding: 0 15px;
	position: relative;
	justify-content: space-between;
	flex-direction: row;
}

.vscomp-wrapper .checkbox-label {
	position: relative;
	display: inline-block;
	cursor: pointer;
	color: #DEDEDE;
}

.vscomp-wrapper .checkbox-input {
	position: absolute;
	opacity: 0;
	color: #DEDEDE;
}

.vscomp-wrapper .checkbox-icon {
	position: relative;
	display: inline-block;
	width: 20px;
	height: 20px;
	border: 1px solid #D9D9D9;
	background-color: #F5F5F5;
	margin-right: 8px;
	border-radius: 2px;
}

.vscomp-wrapper .checkbox-label:hover .checkbox-icon {
	border-color: #888888;
}

.vscomp-wrapper.has-clear-button .vscomp-toggle-button {
	padding-right: 54px;
	padding-left: 0;
}

.vscomp-arrow {
	align-items: center;
	display: inline-block;
	height: 12px;
	justify-content: center;
	position: absolute;
	right: 10px;
	top: 10px;
	width: 12px;
	background-image:
		url('${pageContext.request.contextPath}/resourcesNew1/assets/caret.svg');
	background-position: center;
	background-repeat: no-repeat;
	background-size: cover
}

.vscomp-arrow::after {
	display: none;
}

.vscomp-value {
	color: #858585;
	opacity: 1;
}

.vscomp-wrapper:not (.has-value ) .vscomp-value {
	opacity: 0.8;
}

.vscomp-ele {
	max-width: 380px;
}

.pop-comp-wrapper {
	display: none;
	position: absolute;
	top: 0;
	left: 0;
	opacity: 0;
	color: #000;
	background-color: #fff;
	box-shadow: 0 2px 2px 0 rgba(0, 0, 0, .14), 0 3px 1px -2px
		rgba(0, 0, 0, .12), 0 1px 5px 0 rgba(0, 0, 0, .2);
	text-align: left;
	flex-wrap: wrap;
	z-index: 1;
}

.vscomp-search-input::placeholder {
	color: #858585 !important;
}

@media only screen and (min-width: 993px) {
	.pop-comp-wrapper {
		z-index: 2;
		transform: none !important;
		max-width: 380px;
		display: inline-flex;
		transition-duration: 300ms;
		opacity: 1;
		box-shadow: none !important;
		position: absolute;
		top: 6vh !important;
		left: 0;
	}
}

@media only screen and (min-width: 601px) {
	.submitbtncolumn {
		top: 2vh !important
	}
}

.bank_list .vscomp-option.focused {
	background-color: #fff !important;
}

.bank_list .vscomp-option:hover {
	background-color: #005baa !important;
	color: #fff !important;
}

.vscomp-wrapper.has-select-all .vscomp-search-input {
	width: 100%;
}

.vscomp-search-input::placeholder {
	font-size: 13px;
}

.submitbtncol {
	display: flex;
	align-items: center;
	justify-content: center;
}

.vscomp-wrapper.has-clear-button.has-value .vscomp-clear-button {
	display: none !important;
}

.host_bank_switch_all_merch .custom-btn {
	margin: 0px 0px !important;
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

.modal-header {
	color: #005BAA;
	text-align: center;
	padding: 10px;
	border-bottom: 1.5px solid #F5A623;
	font-weight: 500;
	font-size: 16px;
}

.modal-header p {
	font-size: 18px;
	font-weight: 500;
}

.mb-0 {
	margin-bottom: 0 !important;
}

.footer {
	background-color: #EFF8FF !important;
	display: flex;
	align-items: center;
	justify-content: center;
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
	font-size: 15px;
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

#overlay {
	z-index: 100;
}

@media only screen and (min-width: 993px) {
	.row .col.offset-l5 {
		margin-left: 33.33333%;
	}
}

@media only screen and (min-width: 993px) {
	.row .col.offset-l3 {
		margin-left: 22%;
	}
}

.modal_row {
	width: 100%;
	height: 100%;
	align-content: center;
}

.modal-content {
	padding: 15px 24px;
}

.modal_row {
	width: 100%;
	height: 100%;
	align-content: center;
}

.modal-footer {
	background-color: #EFF8FF;
	border-bottom-left-radius: 10px;
	border-bottom-right-radius: 10px;
	padding: 10px 0;
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
</style>



		<div class="confirm_approval_overlay" id="confirm_approval_overlay">
			<div class="row modal_row">
				<div class="col offset-l5 offset-m3 s12 m6 l4">

					<div id="confirm_approval_modal" class="confirm_approval_modal">
						<div class="modal-header">
							<p class="mb-0">Notification</p>
						</div>
						<div class="modal-content" style="padding: 20px 30px;">
							<div class="align-center">
								<img id="confirm_approval_image"
									src="${pageContext.request.contextPath}/resourcesNew1/assets/info.svg"
									width="40" height="40">
							</div>
							<p id="confirm_approval_text" class="align-center"
								style="font-size: 17px; margin-top: 5px;">
								Host transition initiated. A confirmation email will be sent to<span
									style="color: #005baa !important;"> csmobi@gomobi.io </span>upon
								completion.
							</p>


						</div>
						<div class="align-center modal-footer footer">

							<button id="confirm_approval_closeBtn" class="btn closebtn"
								type="button" onclick="closeConfirmApprovalPopup()">Close
							</button>

						</div>
					</div>

				</div>
			</div>
		</div>


	</div>









</body>
</html>