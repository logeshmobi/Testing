<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Edit MDR Rates</title>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet">

<style>
@import
	url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap')
	;

.container-fluid {
	font-family: "Poppins", sans-serif;
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

.select2-container--default .select2-selection--single .select2-selection__arrow
	{
	top: 5px !important;
}

#overlay {
	z-index: 10 !important;
}

.row .select2-container {
	z-index: 9 !important;
	width: 85% !important;
	font-size: 14px !important;
}

input[type=search]:not(.browser-default){
font-size:14px !important;
}
.select2-container--default .select2-results__option .select2-results__option
	{
	font-size: 14px !important;
}

/*.button_tab_container {*/
/*    display: flex;*/

/*}*/
.button-tabs, .mdr_button-tabs {
	color: #85858570;
	width: 100%;
	background: #fff;
	border: none;
	border-bottom: 4px solid #85858570;
	padding: 15px;
	font-weight: 600;
	font-family: "Poppins", sans-serif;
	cursor: pointer;
}

.p-0 {
	padding: 0 !important;
}

.button-tabs:focus, .mdr_button-tabs:focus {
	background-color: #fff !important;
}

.tab_active {
	color: #005baa;
	border-bottom: 4px solid #005baa;
	cursor: pointer;
}

#mainmerchant_option, #submerchant_option {
	padding: 20px 0px;
}

.ws-nowrap {
	white-space: nowrap;
}

.input-field.col label {
	font-size: 18px;
}

.input-field.col input::placeholder {
	font-size: 14px !important;
	color: #70707080 !important;
}

.select-wrapper input.select-dropdown {
	font-size: 14px !important;
	color: #707070;
}


input[type="text"]:not(.browser-default) {
    border-bottom: 1.5px solid orange !important;
    color: #707070;
    font-size: 13px !important;
    font-family: "Poppins", sans-serif;
}


input[type="text"]:not (.browser-default )::placeholder {
	color: #D0D0D0 !important;
}

input[type="text"]:not (.browser-default ):focus:not ([readonly] ) +label
	{
	color: #707070;
}

.select-wrapper ul {
	top: 10px !important;
}

.mdr_button-tabs {
	white-space: nowrap;
}

.paymentmethod_text {
	color: #707070;
	font-weight: 600;
	font-size: 15px;
	text-align: left;
}

input[type=text]:not(.browser-default){
	font-size: 14px !important;
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

input[type=text]:not(.browser-default ):focus:not([readonly] ) +label
	{
	color: #707070 !important;
}

 #label_businessname {
            position: static !important;
            color: #858585;
            font-size: 15px;
        }
        
.select2-container{
margin: 5px 0 !important;
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
								<strong class="heading_text">Edit MDR Rates</strong>

							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>


		<%--   choose merchant box --%>

		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content ">
						<div class="row">
<!-- 							<div class="input-field col s12 m3 l3">Select Business Name</div>
 -->
							<div class="input-field col s12 m5 l5">
							<label for="merchantName" id="label_businessname">Select Business Name</label>
								<select name="merchantName" id="merchantName"
									path="merchantName"
									onchange="javascript:location.href = this.value;"
									class="browser-default select-filter">
									<!-- onclick="javascript: locate();"> -->
									<optgroup label="Business Names" style="width: 100%">
										<option selected value=""><c:out
												value="Business Name" /></option>

										<c:forEach items="${merchant1}" var="merchant1">
											<c:if
												test="${merchant1.mid.mid!=null || merchant1.mid.motoMid!=null || merchant1.mid.ezypassMid!=null
								|| merchant1.mid.ezywayMid!=null || merchant1.mid.ezyrecMid!=null || merchant1.mid.umMotoMid != null
								 || merchant1.mid.umEzyrecMid != null || merchant1.mid.umEzywayMid != null || merchant1.mid.umEzypassMid != null
								  || merchant1.mid.umMid != null || merchant1.mid.splitMid!=null || merchant1.mid.boostMid!=null || merchant1.mid.grabMid!=null ||merchant1.mid.fpxMid!=null || merchant1.mid.tngMid!=null || merchant1.mid.shoppyMid!=null || merchant1.mid.bnplMid!=null || merchant1.mid.fiuuMid!=null }">
												<option
													value="${pageContext.request.contextPath}/editMdr/getMidList?id=${merchant1.id}">
													${merchant1.businessName}
													<c:choose>
														<c:when test="${merchant1.mid.mid!=null}">
                                                        ~${merchant1.mid.mid }
                                                    </c:when>
														<c:when test="${merchant1.mid.motoMid!=null}">
                                                        ~${merchant1.mid.motoMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.ezywayMid!=null}">
                                                        ~${merchant1.mid.ezywayMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.ezyrecMid!=null}">
                                                        ~${merchant1.mid.ezyrecMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.splitMid!=null}">
                                                        ~${merchant1.mid.splitMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.umMotoMid!=null}">
                                                        ~${merchant1.mid.umMotoMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.umEzyrecMid!=null}">
                                                        ~${merchant1.mid.umEzyrecMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.umEzywayMid!=null}">
                                                        ~${merchant1.mid.umEzywayMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.umEzypassMid!=null}">
                                                        ~${merchant1.mid.umEzypassMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.umMid!=null}">
                                                        ~${merchant1.mid.umMid}
                                                    </c:when>

														<c:when test="${merchant1.mid.boostMid!=null}">
                                                        ~${merchant1.mid.boostMid}
                                                    </c:when>

														<c:when test="${merchant1.mid.grabMid!=null}">
                                                        ~${merchant1.mid.grabMid}
                                                    </c:when>

														<c:when test="${merchant1.mid.fpxMid!=null}">
                                                        ~${merchant1.mid.fpxMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.tngMid!=null}">
                                                        ~${merchant1.mid.tngMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.shoppyMid!=null}">
                                                        ~${merchant1.mid.shoppyMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.bnplMid!=null}">
                                                        ~${merchant1.mid.bnplMid}
                                                    </c:when>
														<c:when test="${merchant1.mid.fiuuMid!=null}">
                                                        ~${merchant1.mid.fiuuMid}
                                                    </c:when>
														<c:otherwise>
                                                        ~${merchant1.mid.ezypassMid}
                                                    </c:otherwise>
													</c:choose>


												</option>

											</c:if>

										</c:forEach>
									</optgroup>
								</select>
							</div>

						</div>
					</div>

				</div>
			</div>
		</div>



		<%--   merchant detail tab --%>
		<c:if test="${businessName != null }">
			<div class="row" id="merchantdetails_card">
				<div class="col s12">
					<div class="card blue-bg text-white">
						<div class="card-content">
							<div class="d-flex align-items-center">
								<h3 class="text-white mb-0 ">
									<strong class="heading_text">Merchant Details</strong>
								</h3>
							</div>


							<%--           button tabs         --%>
							<div class="row " style="padding: 0.50rem 0.75rem;">
								<div class="col s12 m6 l6 p-0">
									<button type="button" class="button-tabs"
										id="main_merchant_tab" onclick="mainMerchantTabOpen()">Main
										Merchant</button>
								</div>
								<div class="col s12 m6 l6 p-0">
									<button type="button" class="button-tabs" id="sub_merchant_tab"
										onclick="subMerchantTabOpen()">Sub Merchant</button>
								</div>
							</div>

							<%--                    mid options --%>
							<div id="mainmerchant_option">
								<div class="row main_merchant_mid_options"
									style="padding: 0.50rem 0.75rem;">
									<div class="col s12 m4 l4 pr-0 input-field">

										<input placeholder="MNTX" name="merchant_name"
											id="merchant_name" type="text" class=""
											value="${businessName}" readonly> <label
											for="merchant_name" class="ws-nowrap" style="">Main Merchant Name</label>

									</div>

									<div class="col s12 m4 l4 offset-m1 offset-l1 input-field "
										id="main_merchant_mid_col" style="">
										<select class="" id="main_merchant_mid"
											name='main_merchant_mid'
											onchange="getMDROfMainMerchant(this.value,'${merchantId}','${businessName}')">
											<option value="" selected disabled>Choose MID</option>
											<c:forEach items="${midList}" var="dto">
												<option value="${dto}">${dto}</option>
											</c:forEach>
										</select> <label class="" style="font-size: 14px;">MID</label>


									</div>
								</div>
							

							<%--                  mdr rates options  --%>

							<div class="row">
								<div class="col s12">
									<div class="card blue-bg text-white">
										<div class="card-content">

											<div class="row product_btn_row"
												style="padding: 0.50rem 0.75rem;">


												<c:set var="response" value="${response.mdrDetails}" />
												<c:set var="type" value="${response.type}" />
												<c:if test="${type.contains('FPX')}">
													<div class="col s12 m3 l3 p-0" id="fpx_col">
														<button type="button" class="mdr_button-tabs"
															id="fpx_mdr_btn">Internet Banking</button>
													</div>


												</c:if>
												<c:if
													test="${type.contains('VISA') ||type.contains('MASTER')  ||type.contains('UNIONPAY')}">
													<div class="col s12 m3 l3 p-0" id="cards_col">
														<button type="button" class="mdr_button-tabs"
															id="cards_mdr_btn">Card</button>
													</div>
												</c:if>
												<c:if
													test="${type.contains('BOOST') || type.contains('GRAB') || type.contains('SHOPPY') || type.contains('TNG')}">
													<div class="col s12 m3 l3 p-0" id="ewallet_col">
														<button type="button" class="mdr_button-tabs"
															id="ewallet_mdr_btn">eWallets</button>
													</div>
												</c:if>
												<c:if test="${type.contains('PAYOUT')}">
													<div class="col s12 m3 l3 p-0" id="payout_col">
														<button type="button" class="mdr_button-tabs"
															id="payout_mdr_btn">Payout</button>
													</div>
												</c:if>

											</div>

											<div class="row">
												<div class="col s12 m12 l12">
													<span
														style="color: red; font-size: 14px; text-align: center; padding: 5px 0;"
														id="error_message"></span>
												</div>
											</div>


											<c:choose>
												<c:when test="${empty response}">

												</c:when>
												<c:otherwise>

													<c:if test="${empty type}">

														<div class="row">
															<div class="col s12 m12 l12">
																<span
																	style="color: red; font-size: 14px; text-align: center;">No
																	MDR was found for this merchant ID.</span>
															</div>
														</div>
													</c:if>
												</c:otherwise>
											</c:choose>





											<c:if test="${type.contains('FPX')}">
												<div class="row content" id="fpx_container"
													style="padding: 2rem 0; display: none;">
													<div class="col s12 m4 l4 input-field" style="">
														<p class="paymentmethod_text">FPX Internet Banking
															MDR(%)</p>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="0.0" name="fpx_merchantmdr"
															id="fpx_merchantmdr" type="text" class=""
															value="${response.fpxMerchantMDR}"> <label
															for="fpx_merchantmdr" style="white-space: nowrap;"
															inputmode="decimal">Merchant MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="0.0" name="fpx_hostmdr"
															id="fpx_hostmdr" type="text" class=""
															value="${response.fpxHostMDR}" readonly> <label
															for="fpx_hostmdr" style="white-space: nowrap;"
															inputmode="decimal">Host MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="0.0" name="fpx_mobimdr"
															id="fpx_mobimdr" type="text" class=""
															value="${response.fpxMobiMDR}" readonly> <label
															for="fpx_mobimdr" style="white-space: nowrap;"
															inputmode="decimal">Mobi MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="0.0" name="fpx_minimummdr"
															id="fpx_minimummdr" type="text" class=""
															value="${response.fpxMinimumMDR}"> <label
															for="fpx_minimummdr" style="white-space: nowrap;"
															inputmode="decimal">Minimum Value</label>
													</div>
												</div>
											</c:if>

											<%--                                cards mdr inputs    --%>

											<c:if
												test="${type.contains('VISA') ||type.contains('MASTER')  ||type.contains('UNIONPAY')}">


												<div id="cards_container" class="content"
													style="display: none;">

													<div class="row">
														<div class="col s12 m4 l4 input-field"></div>
														<div class="input-field col s12 m2 l1">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/visacard.svg"
																alt="visa" width="80" height="80">
														</div>
														<div
															class="col s12 m2 l1 offset-l2 offset-m1 input-field ">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/mastercard.svg"
																alt="mastercard" width="80" height="80">
														</div>
														<div
															class="col s12 m2 l1 offset-l2 offset-m1 input-field ">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/unionpay.svg"
																alt="unionpay" width="80" height="80">
														</div>
													</div>


													<%--                                    local debit card --%>
													<div class="row" style="padding: 0.5rem 0;">
														<div class="col s12 m4 l4 input-field" style="">
															<p class="paymentmethod_text">MDR For Local Debit
																Card(%)</p>
														</div>
														<div class="col s12 m2 l2  input-field ">
															<%--                                                <img class="show-on-small" src="${pageContext.request.contextPath}/resourcesNew1/assets/visacard.svg" width="25" height="25">--%>
															<input placeholder="0.0" name="localdebitvisamdr"
																id="localdebitvisamdr" type="text" class=""
																value="${response.visaLocalDebitCardMDR}"
																inputmode="decimal"> <label
																for="localdebitvisamdr" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="localdebitmastermdr"
																id="localdebitmastermdr" type="text" class=""
																inputmode="decimal"
																value="${response.masterLocalDebitCardMDR}"> <label
																for="localdebitmastermdr" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="localdebitunionmdr"
																id="localdebitunionmdr" type="text" class=""
																inputmode="decimal"
																value="${response.unionPayLocalDebitCardMDR}"> <label
																for="localdebitunionmdr" style="white-space: nowrap;">MDR</label>
														</div>
													</div>

													<%--                                        local credit card --%>

													<div class="row" style="padding: 0.5rem 0;">
														<div class="col s12 m4 l4 input-field" style="">
															<p class="paymentmethod_text">MDR For Local Credit
																Card(%)</p>
														</div>
														<div class="col s12 m2 l2  input-field ">
															<input placeholder="0.0" name="localcreditvisamdr"
																id="localcreditvisamdr" type="text" class=""
																inputmode="decimal"
																value="${response.visaLocalCreditCardMDR}"> <label
																for="localcreditvisamdr" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="localcreditmastermdr"
																id="localcreditmastermdr" type="text" class=""
																inputmode="decimal"
																value="${response.masterLocalCreditCardMDR}"> <label
																for="localcreditmastermdr" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="localcreditunionmdr"
																id="localcreditunionmdr" type="text" class=""
																inputmode="decimal"
																value="${response.unionPayLocalCreditCardMDR}">
															<label for="localcreditunionmdr"
																style="white-space: nowrap;">MDR</label>
														</div>
													</div>

													<%--                                        foreign debit card --%>

													<div class="row" style="padding: 0.5rem 0;">
														<div class="col s12 m4 l4 input-field" style="">
															<p class="paymentmethod_text">MDR For Foreign Debit
																Card(%)</p>
														</div>
														<div class="col s12 m2 l2  input-field ">
															<input placeholder="0.0" name="foreigndebitvisamdr"
																id="foreigndebitvisamdr" type="text" class=""
																inputmode="decimal"
																value="${response.visaForeignDebitCardMDR}"> <label
																for="foreigndebitvisamdr" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="foreigndebitmastermdr"
																id="foreigndebitmastermdr" type="text" class=""
																inputmode="decimal"
																value="${response.masterForeignDebitCardMDR}"> <label
																for="foreigndebitmastermdr" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="foreigndebitunionmdr"
																id="foreigndebitunionmdr" type="text" class=""
																inputmode="decimal"
																value="${response.unionPayForeignDebitCardMDR}">
															<label for="foreigndebitunionmdr"
																style="white-space: nowrap;">MDR</label>
														</div>
													</div>

													<%--                                        foreign credit card --%>

													<div class="row" style="padding: 0.5rem 0;">
														<div class="col s12 m4 l4 input-field" style="">
															<p class="paymentmethod_text">MDR For Foreign Credit
																Card(%)</p>
														</div>
														<div class="col s12 m2 l2 input-field ">
															<input placeholder="0.0" name="foreigncreditvisamdr"
																id="foreigncreditvisamdr" type="text" class=""
																inputmode="decimal"
																value="${response.visaForeignCreditCardMDR}"> <label
																for="foreigncreditvisamdr" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="foreigncreditmastermdr"
																id="foreigncreditmastermdr" type="text" class=""
																inputmode="decimal"
																value="${response.masterForeignCreditCardMDR}">
															<label for="foreigncreditmastermdr"
																style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="foreigncreditunionmdr"
																id="foreigncreditunionmdr" type="text" class=""
																inputmode="decimal"
																value="${response.unionPayForeignCreditCardMDR}">
															<label for="foreigncreditunionmdr"
																style="white-space: nowrap;">MDR</label>
														</div>
													</div>


												</div>

											</c:if>
											<%--                                   ewallet mdr contents --%>
											<c:if
												test="${type.contains('BOOST') || type.contains('GRAB') || type.contains('SHOPPY') || type.contains('TNG')}">

												<div class="row content" id="ewallet_container"
													style="padding: 2rem 0; display: none;">
													<%--                                       boost mdr --%>

													<c:if test="${type.contains('BOOST')}">

														<div class="row mx-0">
															<div class="col s12 m4 l4 input-field" style="">
																<p class="paymentmethod_text">MDR For Boost (%)</p>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="boost_merchantmdr"
																	id="boost_merchantmdr" type="text" class=""
																	inputmode="decimal"
																	value="${response.boostMerchantMDR}"> <label
																	for="boost_merchantmdr" style="white-space: nowrap;">Merchant
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="boost_hostmdr"
																	id="boost_hostmdr" type="text" inputmode="decimal"
																	class="" value="${response.boostHostMDR}" readonly>
																<label for="boost_hostmdr" style="white-space: nowrap;">Host
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="boost_mobimdr"
																	id="boost_mobimdr" type="text" inputmode="decimal"
																	class="" value="${response.boostMobiMDR}" readonly>
																<label for="boost_mobimdr" style="white-space: nowrap;">Mobi
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="boost_minimummdr"
																	id="boost_minimummdr" type="text" inputmode="decimal"
																	class="" value="${response.boostMinimumMDR}"> <label
																	for="boost_minimummdr" style="white-space: nowrap;">Minimum
																	Value</label>
															</div>
														</div>
													</c:if>

													<%--    grabpay --%>
													<c:if test="${type.contains('GRAB')}">
														<div class="row mx-0">
															<div class="col s12 m4 l4 input-field" style="">
																<p class="paymentmethod_text">MDR For GrabPay (%)</p>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="grab_merchantmdr"
																	id="grab_merchantmdr" type="text" inputmode="decimal"
																	class="" value="${response.grabMerchantMDR}"> <label
																	for="grab_merchantmdr" style="white-space: nowrap;">Merchant
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="grab_hostmdr"
																	id="grab_hostmdr" type="text" inputmode="decimal"
																	class="" value="${response.grabHostMDR}" readonly>
																<label for="grab_hostmdr" style="white-space: nowrap;">Host
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="grab_mobimdr"
																	id="grab_mobimdr" type="text" inputmode="decimal"
																	class="" value="${response.grabMobiMDR}" readonly>
																<label for="grab_mobimdr" style="white-space: nowrap;">Mobi
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="grab_minimummdr"
																	id="grab_minimummdr" type="text" inputmode="decimal"
																	class="" value="${response.grabMinimumMDR}"> <label
																	for="grab_minimummdr" style="white-space: nowrap;">Minimum
																	Value</label>
															</div>
														</div>
													</c:if>
													<%--                                        tng --%>
													<c:if test="${type.contains('TNG')}">
														<div class="row mx-0">
															<div class="col s12 m4 l4 input-field" style="">
																<p class="paymentmethod_text">MDR For Touch'N Go (%)</p>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="tng_merchantmdr"
																	id="tng_merchantmdr" type="text" inputmode="decimal"
																	class="" value="${response.tngMerchantMDR}"> <label
																	for="tng_merchantmdr" style="white-space: nowrap;">Merchant
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="tng_hostmdr"
																	id="tng_hostmdr" type="text" inputmode="decimal"
																	class="" value="${response.tngHostMDR}" readonly>
																<label for="tng_hostmdr" style="white-space: nowrap;">Host
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="tng_mobimdr"
																	id="tng_mobimdr" type="text" inputmode="decimal"
																	class="" value="${response.tngMobiMDR}" readonly>
																<label for="tng_mobimdr" style="white-space: nowrap;">Mobi
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="tng_minimummdr"
																	id="tng_minimummdr" type="text" inputmode="decimal"
																	class="" value="${response.tngMinimumMDR}"> <label
																	for="tng_minimummdr" style="white-space: nowrap;">Minimum
																	Value</label>
															</div>
														</div>
													</c:if>
													<%--                                        shopee pay--%>

													<c:if test="${type.contains('SHOPPY')}">
														<div class="row mx-0">
															<div class="col s12 m4 l4 input-field" style="">
																<p class="paymentmethod_text">MDR For Shopee Pay (%)</p>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="spp_merchantmdr"
																	id="spp_merchantmdr" type="text" inputmode="decimal"
																	class="" value="${response.sppMerchantMDR}"> <label
																	for="spp_merchantmdr" style="white-space: nowrap;">Merchant
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="spp_hostmdr"
																	id="spp_hostmdr" type="text" inputmode="decimal"
																	class="" value="${response.sppHostMDR}" readonly>
																<label for="spp_hostmdr" style="white-space: nowrap;">Host
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="spp_mobimdr"
																	id="spp_mobimdr" type="text" inputmode="decimal"
																	class="" value="${response.sppMobiMDR}" readonly>
																<label for="spp_mobimdr" style="white-space: nowrap;">Mobi
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="spp_minimummdr"
																	id="spp_minimummdr" type="text" inputmode="decimal"
																	class="" value="${response.sppMinimumMDR}"> <label
																	for="spp_minimummdr" style="white-space: nowrap;">Minimum
																	Value</label>
															</div>
														</div>
													</c:if>
												</div>
											</c:if>
											<%----%>

											<%--                                    payout content--%>

											<c:if test="${type.contains('PAYOUT')}">
												<div class="row content" id="payout_container"
													style="padding: 2rem 0; display: none;">
													<div class="col s12 m4 l4 input-field" style="">
														<p class="paymentmethod_text">MDR For Payout (%)</p>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="0.0" name="payout_merchantmdr"
															id="payout_merchantmdr" type="text" inputmode="decimal"
															class="" value="${response.payoutMerchantMDR}"> <label
															for="payout_merchantmdr" style="white-space: nowrap;">Merchant
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="0.0" name="payout_hostmdr"
															id="payout_hostmdr" type="text" inputmode="decimal"
															class="" value="${response.payoutHostMDR}" readonly>
														<label for="payout_hostmdr" style="white-space: nowrap;">Host
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="0.0" name="payout_mobimdr"
															id="payout_mobimdr" type="text" inputmode="decimal"
															class="" value="${response.payoutMobiMDR}" readonly>
														<label for="payout_mobimdr" style="white-space: nowrap;">Mobi
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="0.0" name="payout_minimummdr"
															id="payout_minimummdr" type="text" inputmode="decimal"
															class="" value="${response.payoutMinimumMDR}"> <label
															for="payout_minimummdr" style="white-space: nowrap;">Minimum
															Value</label>
													</div>
												</div>
											</c:if>
											<%--                                    --%>
										</div>
									</div>
								</div>
							</div>
							
							
							<div style="text-align: center;">
								<button type="button" id="submit_btn" class="btn btn-primary"
									style="padding: 0 20px;">Submit</button>
							</div>
							
						</div>
						
						
						<%--                   sub merchant mid --%>

								<div id="submerchant_option" class="">
									<div class="row sub_merchant_mid_options"
										style="padding: 0.50rem 0.75rem;">
										<div class="col s12 m4 l4 pr-0 input-field">

											<select class="" id="sub_merchant_name"
												name="sub_merchant_name"
												onchange="fetchSubMidList(this.value,'${merchantId}')">
												<option value="" selected disabled>Choose SubMerchant</option>
												<c:forEach items="${subMerchantList}" var="submerchant">
													<option value="${submerchant.id}">${submerchant.businessName}</option>
												</c:forEach>
											</select> <label class="ws-nowrap" style="font-size: 14px;">Select Sub Merchant</label>

										</div>

										<div class="col s12 m4 l4 offset-m1 offset-l1 input-field"
											style="">
											<select class="" id="sub_merchant_mid" name="sub_merchant_mid" onchange="getMDROfSubMerchant(this.value,'${merchantId}','${submerchantMerchantId}','${businessName}','${subMerchantBusinessName}')">
												<option value="" selected disabled>Choose MID</option>
												<c:forEach items="${subMidList}" var="subMidList">
													<option value="${subMidList}">${subMidList}</option>
												</c:forEach>
											</select> <label class="" style="font-size: 14px;">MID</label>


										</div>
									</div>
									
									
									
									<!-- sub merchant's mdr columns -->
									
							<div class="row">
								<div class="col s12">
									<div class="card blue-bg text-white">
										<div class="card-content">

											<div class="row product_btn_row"
												style="padding: 0.50rem 0.75rem;">


												<c:set var="submerchant_response" value="${subMerchantResponse.mdrDetails}" />
												<c:set var="submerchant_type" value="${subMerchantResponse.type}" />
												<c:if test="${submerchant_type.contains('FPX')}">
													<div class="col s12 m3 l3 p-0" id="fpx_col_submerchant">
														<button type="button" class="mdr_button-tabs submerchant_mdr_button_tabs"
															id="fpx_mdr_btn_submerchant">Internet Banking</button>
													</div>


												</c:if>
												<c:if
													test="${submerchant_type.contains('VISA') ||submerchant_type.contains('MASTER')  ||submerchant_type.contains('UNIONPAY')}">
													<div class="col s12 m3 l3 p-0" id="cards_col_submerchant">
														<button type="button" class="mdr_button-tabs submerchant_mdr_button_tabs"
															id="cards_mdr_btn_submerchant">Card</button>
													</div>
												</c:if>
												<c:if
													test="${submerchant_type.contains('BOOST') || submerchant_type.contains('GRAB') || submerchant_type.contains('SHOPPY') || submerchant_type.contains('TNG')}">
													<div class="col s12 m3 l3 p-0" id="ewallet_col_submerchant">
														<button type="button" class="mdr_button-tabs submerchant_mdr_button_tabs"
															id="ewallet_mdr_btn_submerchant">eWallets</button>
													</div>
												</c:if>
												<c:if test="${submerchant_type.contains('PAYOUT')}">
													<div class="col s12 m3 l3 p-0" id="payout_col_submerchant">
														<button type="button" class="mdr_button-tabs submerchant_mdr_button_tabs"
															id="payout_mdr_btn_submerchant">Payout</button>
													</div>
												</c:if>

											</div>

											<div class="row">
												<div class="col s12 m12 l12">
													<span
														style="color: red; font-size: 14px; text-align: center; padding: 5px 0;"
														id="error_message_submerchant"></span>
												</div>
											</div>


											<c:choose>
												<c:when test="${empty submerchant_response}">

												</c:when>
												<c:otherwise>

													<c:if test="${empty submerchant_type}">

														<div class="row">
															<div class="col s12 m12 l12">
																<span
																	style="color: red; font-size: 14px; text-align: center;">No
																	MDR was found for this merchant ID.</span>
															</div>
														</div>
													</c:if>
												</c:otherwise>
											</c:choose>





											<c:if test="${submerchant_type.contains('FPX')}">
												<div class="row content" id="fpx_container_submerchant"
													style="padding: 2rem 0; display: none;">
													<div class="col s12 m4 l4 input-field" style="">
														<p class="paymentmethod_text">FPX Internet Banking
															MDR(%)</p>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="0.0" name="fpx_merchantmdr_submerchant"
															id="fpx_merchantmdr_submerchant" type="text" class=""
															value="${submerchant_response.fpxMerchantMDR}"> <label
															for="fpx_merchantmdr_submerchant" style="white-space: nowrap;"
															inputmode="decimal">Merchant MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="0.0" name="fpx_hostmdr_submerchant"
															id="fpx_hostmdr_submerchant" type="text" class=""
															value="${submerchant_response.fpxHostMDR}" readonly> <label
															for="fpx_hostmdr_submerchant" style="white-space: nowrap;"
															inputmode="decimal">Host MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="0.0" name="fpx_mobimdr_submerchant"
															id="fpx_mobimdr_submerchant" type="text" class=""
															value="${submerchant_response.fpxMobiMDR}" readonly> <label
															for="fpx_mobimdr_submerchant" style="white-space: nowrap;"
															inputmode="decimal">Mobi MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="0.0" name="fpx_minimummdr_submerchant"
															id="fpx_minimummdr_submerchant" type="text" class=""
															value="${submerchant_response.fpxMinimumMDR}"> <label
															for="fpx_minimummdr_submerchant" style="white-space: nowrap;"
															inputmode="decimal">Minimum Value</label>
													</div>
												</div>
											</c:if>

											<%--                                cards mdr inputs    --%>

											<c:if
												test="${submerchant_type.contains('VISA') ||submerchant_type.contains('MASTER')  ||submerchant_type.contains('UNIONPAY')}">


												<div id="cards_container_submerchant" class="content_submerchant"
													style="display: none;">

													<div class="row">
														<div class="col s12 m4 l4 input-field"></div>
														<div class="input-field col s12 m2 l1">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/visacard.svg"
																alt="visa" width="80" height="80">
														</div>
														<div
															class="col s12 m2 l1 offset-l2 offset-m1 input-field ">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/mastercard.svg"
																alt="mastercard" width="80" height="80">
														</div>
														<div
															class="col s12 m2 l1 offset-l2 offset-m1 input-field ">
															<img
																src="${pageContext.request.contextPath}/resourcesNew1/assets/unionpay.svg"
																alt="unionpay" width="80" height="80">
														</div>
													</div>


													<%--                                    local debit card --%>
													<div class="row" style="padding: 0.5rem 0;">
														<div class="col s12 m4 l4 input-field" style="">
															<p class="paymentmethod_text">MDR For Local Debit
																Card(%)</p>
														</div>
														<div class="col s12 m2 l2  input-field ">
															<%--                                                <img class="show-on-small" src="${pageContext.request.contextPath}/resourcesNew1/assets/visacard.svg" width="25" height="25">--%>
															<input placeholder="0.0" name="localdebitvisamdr_submerchant"
																id="localdebitvisamdr_submerchant" type="text" class=""
																value="${submerchant_response.visaLocalDebitCardMDR}"
																inputmode="decimal"> <label
																for="localdebitvisamdr_submerchant" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="localdebitmastermdr_submerchant"
																id="localdebitmastermdr_submerchant" type="text" class=""
																inputmode="decimal"
																value="${submerchant_response.masterLocalDebitCardMDR}"> <label
																for="localdebitmastermdr_submerchant" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="localdebitunionmdr_submerchant"
																id="localdebitunionmdr_submerchant" type="text" class=""
																inputmode="decimal"
																value="${submerchant_response.unionPayLocalDebitCardMDR}"> <label
																for="localdebitunionmdr_submerchant" style="white-space: nowrap;">MDR</label>
														</div>
													</div>

													<%--                                        local credit card --%>

													<div class="row" style="padding: 0.5rem 0;">
														<div class="col s12 m4 l4 input-field" style="">
															<p class="paymentmethod_text">MDR For Local Credit
																Card(%)</p>
														</div>
														<div class="col s12 m2 l2  input-field ">
															<input placeholder="0.0" name="localcreditvisamdr_submerchant"
																id="localcreditvisamdr_submerchant" type="text" class=""
																inputmode="decimal"
																value="${submerchant_response.visaLocalCreditCardMDR}"> <label
																for="localcreditvisamdr_submerchant" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="localcreditmastermdr_submerchant"
																id="localcreditmastermdr_submerchant" type="text" class=""
																inputmode="decimal"
																value="${submerchant_response.masterLocalCreditCardMDR}"> <label
																for="localcreditmastermdr_submerchant" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="localcreditunionmdr_submerchant"
																id="localcreditunionmdr_submerchant" type="text" class=""
																inputmode="decimal"
																value="${submerchant_response.unionPayLocalCreditCardMDR}">
															<label for="localcreditunionmdr_submerchant"
																style="white-space: nowrap;">MDR</label>
														</div>
													</div>

													<%--                                        foreign debit card --%>

													<div class="row" style="padding: 0.5rem 0;">
														<div class="col s12 m4 l4 input-field" style="">
															<p class="paymentmethod_text">MDR For Foreign Debit
																Card(%)</p>
														</div>
														<div class="col s12 m2 l2  input-field ">
															<input placeholder="0.0" name="foreigndebitvisamdr_submerchant"
																id="foreigndebitvisamdr_submerchant" type="text" class=""
																inputmode="decimal"
																value="${submerchant_response.visaForeignDebitCardMDR}"> <label
																for="foreigndebitvisamdr_submerchant" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="foreigndebitmastermdr_submerchant"
																id="foreigndebitmastermdr_submerchant" type="text" class=""
																inputmode="decimal"
																value="${submerchant_response.masterForeignDebitCardMDR}"> <label
																for="foreigndebitmastermdr_submerchant" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="foreigndebitunionmdr_submerchant"
																id="foreigndebitunionmdr_submerchant" type="text" class=""
																inputmode="decimal"
																value="${submerchant_response.unionPayForeignDebitCardMDR}">
															<label for="foreigndebitunionmdr_submerchant"
																style="white-space: nowrap;">MDR</label>
														</div>
													</div>

													<%--                                        foreign credit card --%>

													<div class="row" style="padding: 0.5rem 0;">
														<div class="col s12 m4 l4 input-field" style="">
															<p class="paymentmethod_text">MDR For Foreign Credit
																Card(%)</p>
														</div>
														<div class="col s12 m2 l2 input-field ">
															<input placeholder="0.0" name="foreigncreditvisamdr_submerchant"
																id="foreigncreditvisamdr_submerchant" type="text" class=""
																inputmode="decimal"
																value="${submerchant_response.visaForeignCreditCardMDR}"> <label
																for="foreigncreditvisamdr_submerchant" style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="foreigncreditmastermdr_submerchant"
																id="foreigncreditmastermdr_submerchant" type="text" class=""
																inputmode="decimal"
																value="${submerchant_response.masterForeignCreditCardMDR}">
															<label for="foreigncreditmastermdr_submerchant"
																style="white-space: nowrap;">MDR</label>
														</div>
														<div
															class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
															<input placeholder="0.0" name="foreigncreditunionmdr_submerchant"
																id="foreigncreditunionmdr_submerchant" type="text" class=""
																inputmode="decimal"
																value="${submerchant_response.unionPayForeignCreditCardMDR}">
															<label for="foreigncreditunionmdr_submerchant"
																style="white-space: nowrap;">MDR</label>
														</div>
													</div>


												</div>

											</c:if>
											<%--                                   ewallet mdr contents --%>
											<c:if
												test="${submerchant_type.contains('BOOST') || submerchant_type.contains('GRAB') || submerchant_type.contains('SHOPPY') || submerchant_type.contains('TNG')}">

												<div class="row content" id="ewallet_container_submerchant"
													style="padding: 2rem 0; display: none;">
													<%--                                       boost mdr --%>

													<c:if test="${submerchant_type.contains('BOOST')}">

														<div class="row mx-0">
															<div class="col s12 m4 l4 input-field" style="">
																<p class="paymentmethod_text">MDR For Boost (%)</p>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="boost_merchantmdr_submerchant"
																	id="boost_merchantmdr_submerchant" type="text" class=""
																	inputmode="decimal"
																	value="${submerchant_response.boostMerchantMDR}"> <label
																	for="boost_merchantmdr_submerchant" style="white-space: nowrap;">Merchant
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="boost_hostmdr_submerchant"
																	id="boost_hostmdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.boostHostMDR}" readonly>
																<label for="boost_hostmdr_submerchant" style="white-space: nowrap;">Host
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="boost_mobimdr_submerchant"
																	id="boost_mobimdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.boostMobiMDR}" readonly>
																<label for="boost_mobimdr_submerchant" style="white-space: nowrap;">Mobi
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="boost_minimummdr_submerchant"
																	id="boost_minimummdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.boostMinimumMDR}"> <label
																	for="boost_minimummdr_submerchant" style="white-space: nowrap;">Minimum
																	Value</label>
															</div>
														</div>
													</c:if>

													<%--    grabpay --%>
													<c:if test="${submerchant_type.contains('GRAB')}">
														<div class="row mx-0">
															<div class="col s12 m4 l4 input-field" style="">
																<p class="paymentmethod_text">MDR For GrabPay (%)</p>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="grab_merchantmdr_submerchant"
																	id="grab_merchantmdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.grabMerchantMDR}"> <label
																	for="grab_merchantmdr_submerchant" style="white-space: nowrap;">Merchant
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="grab_hostmdr_submerchant"
																	id="grab_hostmdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.grabHostMDR}" readonly>
																<label for="grab_hostmdr_submerchant" style="white-space: nowrap;">Host
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="grab_mobimdr_submerchant"
																	id="grab_mobimdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.grabMobiMDR}" readonly>
																<label for="grab_mobimdr_submerchant" style="white-space: nowrap;">Mobi
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="grab_minimummdr_submerchant"
																	id="grab_minimummdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.grabMinimumMDR}"> <label
																	for="grab_minimummdr_submerchant" style="white-space: nowrap;">Minimum
																	Value</label>
															</div>
														</div>
													</c:if>
													<%--                                        tng --%>
													<c:if test="${submerchant_type.contains('TNG')}">
														<div class="row mx-0">
															<div class="col s12 m4 l4 input-field" style="">
																<p class="paymentmethod_text">MDR For Touch'N Go (%)</p>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="tng_merchantmdr_submerchant"
																	id="tng_merchantmdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.tngMerchantMDR}"> <label
																	for="tng_merchantmdr_submerchant" style="white-space: nowrap;">Merchant
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="tng_hostmdr_submerchant"
																	id="tng_hostmdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.tngHostMDR}" readonly>
																<label for="tng_hostmdr_submerchant" style="white-space: nowrap;">Host
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="tng_mobimdr_submerchant"
																	id="tng_mobimdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.tngMobiMDR}" readonly>
																<label for="tng_mobimdr_submerchant" style="white-space: nowrap;">Mobi
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="tng_minimummdr_submerchant"
																	id="tng_minimummdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.tngMinimumMDR}"> <label
																	for="tng_minimummdr_submerchant" style="white-space: nowrap;">Minimum
																	Value</label>
															</div>
														</div>
													</c:if>
													<%--                                        shopee pay--%>

													<c:if test="${submerchant_type.contains('SHOPPY')}">
														<div class="row mx-0">
															<div class="col s12 m4 l4 input-field" style="">
																<p class="paymentmethod_text">MDR For Shopee Pay (%)</p>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="spp_merchantmdr_submerchant"
																	id="spp_merchantmdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.sppMerchantMDR}"> <label
																	for="spp_merchantmdr_submerchant" style="white-space: nowrap;">Merchant
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="spp_hostmdr_submerchant"
																	id="spp_hostmdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.sppHostMDR}" readonly>
																<label for="spp_hostmdr_submerchant" style="white-space: nowrap;">Host
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field disabled">
																<input placeholder="0.0" name="spp_mobimdr_submerchant"
																	id="spp_mobimdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.sppMobiMDR}" readonly>
																<label for="spp_mobimdr_submerchant" style="white-space: nowrap;">Mobi
																	MDR</label>
															</div>
															<div class="col s12 m2 l2 input-field ">
																<input placeholder="0.0" name="spp_minimummdr_submerchant"
																	id="spp_minimummdr_submerchant" type="text" inputmode="decimal"
																	class="" value="${submerchant_response.sppMinimumMDR}"> <label
																	for="spp_minimummdr_submerchant" style="white-space: nowrap;">Minimum
																	Value</label>
															</div>
														</div>
													</c:if>
												</div>
											</c:if>
											<%----%>

											<%--                                    payout content--%>

											<c:if test="${submerchant_type.contains('PAYOUT')}">
												<div class="row content" id="payout_container_submerchant"
													style="padding: 2rem 0; display: none;">
													<div class="col s12 m4 l4 input-field" style="">
														<p class="paymentmethod_text">MDR For Payout (%)</p>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="0.0" name="payout_merchantmdr_submerchant"
															id="payout_merchantmdr_submerchant" type="text" inputmode="decimal"
															class="" value="${submerchant_response.payoutMerchantMDR}"> <label
															for="payout_merchantmdr_submerchant" style="white-space: nowrap;">Merchant
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="0.0" name="payout_hostmdr_submerchant"
															id="payout_hostmdr_submerchant" type="text" inputmode="decimal"
															class="" value="${submerchant_response.payoutHostMDR}" readonly>
														<label for="payout_hostmdr_submerchant" style="white-space: nowrap;">Host
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="0.0" name="payout_mobimdr_submerchant"
															id="payout_mobimdr_submerchant" type="text" inputmode="decimal"
															class="" value="${submerchant_response.payoutMobiMDR}" readonly>
														<label for="payout_mobimdr_submerchant" style="white-space: nowrap;">Mobi
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="0.0" name="payout_minimummdr_submerchant"
															id="payout_minimummdr_submerchant" type="text" inputmode="decimal"
															class="" value="${submerchant_response.payoutMinimumMDR}"> <label
															for="payout_minimummdr_submerchant" style="white-space: nowrap;">Minimum
															Value</label>
													</div>
												</div>
											</c:if>
											<%--                                    --%>
										</div>
									</div>
								</div>
							</div>
							
							
							<div style="text-align: center;">
								<button type="button" id="submit_btn_submerchant" class="btn btn-primary"
									style="padding: 0 20px;">Submit</button>
							</div>
							
						</div>

						</div>
					</div>
				</div>
			</div>
		</c:if>


		<!-- mid from response  -->


		<input type="hidden" id="midToUpdateMDR" value="${mid}" />
		
		<input type="hidden" id="midToUpdateMDR_submerchant" value="${midToUpdateMDR_submerchant}" />
		
		<input type="hidden" id="subMerchantBusinessName" value="${subMerchantBusinessName}" />
		
		<input type="hidden" id="submerchantMerchantId" name="submerchantMerchantId" value="${submerchantMerchantId}">


		<c:set var="firstType" value="${response.type[0]}" />

		<c:set var="typeList" value="${response.type}" />

		<c:set var="firstType_submerchant" value="${subMerchantResponse.type[0]}" />

		<c:set var="typeList_submerchant" value="${subMerchantResponse.type}" />



		
		
		
		<input type="hidden" id="isSubMerchant" value="${isSubMerchant}">
		
			
		
<c:if test="${not empty updated and updated}">
    <div class="outer_overlay" id="outer_overlay">
        <div class="row modal_row">
            <div class="col offset-l4 offset-m3 s12 m6 l4">
                <div id="update_popup-modal" class="update_popup-modal">
                    <div class="modal-header">
                        <p class="mb-0">Notification</p>
                    </div>
                    <div class="modal-content ">
                        <div class="align-center">
                            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/Successful.svg" width="50" height="50">
                        </div>
                        <p class="align-center popup_messages">MDR rates successfully updated.</p>
                    </div>
                    <div class=" align-center modal-footer footer">
                        <button id="closesuccess" class="btn blue-btn closebtn" type="button" onclick="closePopupModal()" name="action">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

<c:if test="${not empty updated and not updated}">
    <div class="outer_overlay" id="outer_overlay2">
        <div class="row modal_row">
            <div class="col offset-l4 offset-m3 s12 m6 l4">
                <div id="update_popup-modal2" class="update_popup-modal">
                    <div class="modal-header">
                        <p class="mb-0">Notification</p>
                    </div>
                    <div class="modal-content ">
                        <div class="align-center">
                            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/Declined.svg" width="50" height="50">
                        </div>
                        <p class="align-center popup_messages">An error occurred while updating MDR rates.</p>
                    </div>
                    <div class="align-center modal-footer footer">
                        <button id="closedeclined" class="btn blue-btn closebtn" type="button" onclick="closePopupModal2()" name="action">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</c:if>

   <div class="confirm_overlay" id="confirm_overlay">
        <div class="row modal_row">
            <div class="col offset-l4 offset-m3 s12 m6 l4">
                <div id="confirm_modal" class="confirm_modal">
                    <div class="modal-header">
                        <p class="mb-0">Confirmation</p>
                    </div>
                    <div class="modal-content ">
                        <div class="align-center">
                            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/confirmation.svg" width="50" height="50">
                        </div>
                        <p class="align-center popup_messages">Would you like to proceed with these MDR rates?</p>

						<form id="hiddenForm" action="${pageContext.request.contextPath}/editMdr/updateMdr/" method="post" style="display: none;">
						
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
						<input type="hidden" name="updateMdrDetails" id="updateMdrDetails"> 
						<input type="hidden" name="mid" id="mid" value="${mid}"> 
						<input type="hidden" name="businessName" id="businessName" value="${businessName}">
						<input type="hidden" id="merchantId" name="merchantId" value="${merchantId}">
						</form>
                    </div>
                    <div class="align-center modal-footer footer">
                        <button id="close_confirm_mainmerchant_btn" class="btn cancelbtn" type="button" onclick="closeConfirmPopup()" name="action">Cancel</button>
                        <button id="confirm_mainmerchant_btn" class="btn confirmbtn " type="button" onclick="submitMainMerchantMdrDetails()" name="action">Confirm</button>                       
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    
       <div class="confirm_overlay" id="confirm_overlay_submerchant">
        <div class="row modal_row">
            <div class="col offset-l4 offset-m3 s12 m6 l4">
                <div id="confirm_modal_submerchant" class="confirm_modal">
                    <div class="modal-header">
                        <p class="mb-0">Confirmation</p>
                    </div>
                    <div class="modal-content ">
                        <div class="align-center">
                            <img src="${pageContext.request.contextPath}/resourcesNew1/assets/confirmation.svg" width="50" height="50">
                        </div>
                        <p class="align-center popup_messages">Would you like to proceed with these MDR rates?</p>

						<form id="hiddenFormForSubmerchant" action="${pageContext.request.contextPath}/editMdr/updateSubMerchantMdr/" method="post" style="display: none;">
						
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" /> 
						<input type="hidden" name="updateMdrDetailsOfSubMerchant" id="updateMdrDetailsOfSubMerchant"> 
						<input type="hidden" name="submerchant_mid" id="submerchant_mid" value="${midToUpdateMDR_submerchant}"> 
						<input type="hidden" name="businessName" id="businessName" value="${businessName}">
						<input type="hidden" name="submerchant_businessName" id="submerchant_businessName" value="${subMerchantBusinessName}">
						<input type="hidden" id="merchantId" name="merchantId" value="${merchantId}">
						<input type="hidden" id="submerchantId" name="submerchantId" value="${submerchantMerchantId}">
								 
						</form>
                    </div>
                    <div class="align-center modal-footer footer">
                        <button id="close_confirm_submerchant_btn" class="btn cancelbtn" type="button" onclick="closeConfirmPopupForSubmerchant()" name="action">Cancel</button>
                        <button id="confirm_submerchant_btn" class="btn confirmbtn " type="button" onclick="submitSubMerchantMdrDetails()" name="action">Confirm</button>                       
                    </div>
                </div>
            </div>
        </div>
    </div>


<style>
		
	  .outer_overlay {
            display: block;
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
        
        .popup_messages{
        	color:#72777B !important;
        }
        
        .confirmbtn{
         	background-color: #005baa;
            border-radius: 50px;
            height: 33px !important;
            line-height: 33px !important;
            padding: 0 30px;
            font-size: 12px;
            margin: 0 5px;
            font-family: "Poppins";
            
            
        }
        
          .confirmbtn:hover, .confirmbtn:focus{
            background-color: #005baa !important;
        }
        
        .cancelbtn{
         	background-color: #fff;
            border-radius: 50px;
            height: 33px !important;
            line-height: 33px !important;
            padding: 0 30px;
            font-size: 12px;
            border: 1px solid #005baa;
            margin: 0 5px;
            color: #005baa;
    		font-family: "Poppins";
        }
        
        .cancelbtn:hover, .cancelbtn:focus{
            background-color: #fff !important;
            color: #005baa !important;
        }
        
        .confirm_overlay{
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
        
         .confirm_modal {
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
            font-size: 17px;
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
        
        
        .modal_row{
            width: 100%;
            height: 100%;
            align-content: center;
        }

        .modal-header{
            color: #005BAA;
            text-align: center;
            padding: 10px;
            border-bottom: 1.5px solid orange;
            font-weight: 500;
            font-size: 16px;
        }

        .modal-content{
            padding: 15px 24px;
        }

        .modal-footer{
            background-color: #EFF8FF;
            border-bottom-left-radius: 10px;
            border-bottom-right-radius: 10px;
            padding: 10px 0;
        }

        .modal-content{
            padding: 10px 30px;
            font-family: "Poppins",sans-serif;
        }

        .closebtn{
            background-color: #005baa;
            border-radius: 50px;
            height: 33px !important;
            line-height: 33px !important;
            padding: 0 30px;
            font-size: 12px;
            font-family: "Poppins";
        }
        .closebtn:hover, .closebtn:focus{
            background-color: #005baa !important;
        }
        
         .content_updatepopup {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            padding: 7px 30px !important;

        }


		</style>
	
	</div>


	<!-- Script -->
	<script type="text/javascript">
    $(document).ready(function () {
        $(".select-filter").select2();
    });
    
    
    function closePopupModal() {
    	  document.getElementById("outer_overlay").style.display = "none";
    	}
    
    function closePopupModal2() {
  	  document.getElementById("outer_overlay2").style.display = "none";
  	}
    
    function openConfirmPopup(){
  	  document.getElementById("confirm_overlay").style.display = "block";
  	
  			
    }
    
    function closeConfirmPopup(){
    	  document.getElementById("confirm_overlay").style.display = "none";
    }
    
    function openConfirmPopupForSubmerchant(){
    	  document.getElementById("confirm_overlay_submerchant").style.display = "block";
    }
    
    function closeConfirmPopupForSubmerchant(){
  	  document.getElementById("confirm_overlay_submerchant").style.display = "none";
  	}
    

    function submitMainMerchantMdrDetails(){
    	document.getElementById("confirm_overlay").style.display = "none";
    	$("#overlay").show();
        collectAndSubmitFormData();
        
    }
    
    function submitSubMerchantMdrDetails(){
    	document.getElementById("confirm_overlay_submerchant").style.display = "none";
    	$("#overlay").show();
    	collectAndSubmitFormDataForSubMerchant();
    }
    
	
	//var mid = document.getElementById('midToUpdateMDR');
 
	

    document.addEventListener('DOMContentLoaded', function () {
    	
    	var isSubMerchant = document.getElementById('isSubMerchant').value;

    	if(isSubMerchant){
    		subMerchantTabOpen();
    	}else{
    		mainMerchantTabOpen();
    	}
    	
    	
    	//console.log("sub merchant id : ",${submerchantMerchantId});
    

    });
    
    
    document.addEventListener('DOMContentLoaded', function() {
        var submit_button = document.getElementById('submit_btn');
        var submit_btn_submerchant = document.getElementById('submit_btn_submerchant');
        
        
        var typeList = [
            <c:forEach items="${response.type}" var="type" varStatus="loop">
                "${type}"${!loop.last ? ',' : ''}
            </c:forEach>
        ];

        var typeList_submerchant = [
            <c:forEach items="${subMerchantResponse.type}" var="type" varStatus="loop">
                "${type}"${!loop.last ? ',' : ''}
            </c:forEach>
        ];

        //console.log(typeList); 
        //console.log(typeList_submerchant); 

        if ((typeList && typeList.length > 0) || (typeList_submerchant && typeList_submerchant.length > 0)) {
            submit_button.disabled = false;
            submit_button.style.opacity = 1;
            
            submit_btn_submerchant.disabled = false;
            submit_btn_submerchant.style.opacity = 1;
        } else {
            // Disable the submit button
            submit_button.disabled = true;
            submit_button.style.opacity = 0.5;
            
            submit_btn_submerchant.disabled = true;
            submit_btn_submerchant.style.opacity = 0.5;
        }
    });



    function mainMerchantTabOpen() {
        var mainMerchantBtn = document.getElementById("main_merchant_tab");
        var subMerchantBtn = document.getElementById("sub_merchant_tab");
        mainMerchantBtn.classList.add("tab_active");
        subMerchantBtn.classList.remove("tab_active");

        var submerchant_option = document.getElementById("submerchant_option");
        var mainmerchant_option = document.getElementById("mainmerchant_option");


        submerchant_option.classList.add("hide");
        mainmerchant_option.classList.remove("hide");
        
        
 		var submit_button = document.getElementById('submit_btn');
 		
        
        var typeList = [
            <c:forEach items="${response.type}" var="type" varStatus="loop">
                "${type}"${!loop.last ? ',' : ''}
            </c:forEach>
        ];


        if (typeList && typeList.length > 0) {
            submit_button.disabled = false;
            submit_button.style.opacity = 1;
        } else {
            // Disable the submit button
            submit_button.disabled = true;
            submit_button.style.opacity = 0.5;
        }

    }
    
    

    function subMerchantTabOpen() {
        var subMerchantBtn = document.getElementById("sub_merchant_tab");
        var mainMerchantBtn = document.getElementById("main_merchant_tab");

        var submerchant_option = document.getElementById("submerchant_option");
        var mainmerchant_option = document.getElementById("mainmerchant_option");

        subMerchantBtn.classList.add("tab_active");
        mainMerchantBtn.classList.remove("tab_active");

        submerchant_option.classList.remove("hide");
        mainmerchant_option.classList.add("hide");
        
        var submit_btn_submerchant = document.getElementById('submit_btn_submerchant');
        

        var typeList_submerchant = [
            <c:forEach items="${subMerchantResponse.type}" var="type" varStatus="loop">
                "${type}"${!loop.last ? ',' : ''}
            </c:forEach>
        ];
 

        if (typeList_submerchant && typeList_submerchant.length > 0) {
        	submit_btn_submerchant.disabled = false;
        	submit_btn_submerchant.style.opacity = 1;
        } else {
           
        	 submit_btn_submerchant.disabled = true;
             submit_btn_submerchant.style.opacity = 0.5;
        }
    }

   
 document.addEventListener("DOMContentLoaded", function () {
    var tabs = document.querySelectorAll('.mdr_button-tabs');

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

 
 
 
 
 document.addEventListener("DOMContentLoaded", function () {
    var tabs = document.querySelectorAll('.submerchant_mdr_button_tabs');

    tabs.forEach(function (tab) {
        tab.addEventListener('click', function () {
            var tabId = this.getAttribute('id');
            var contentId = tabId.replace('_mdr_btn_submerchant', '_container_submerchant');

          
            tabs.forEach(function (tab) {
                tab.classList.remove('tab_active');
            });

            
            this.classList.add('tab_active');

            var contents = document.querySelectorAll('.content_submerchant');
            contents.forEach(function (content) {
                content.style.display = 'none';
            });

            document.getElementById(contentId).style.display = 'block';
        });
    });
}); 

 


// load merchant and assigned to input

    document.addEventListener('DOMContentLoaded', function() {
    	
    	

        var selectElement = document.getElementById('merchantName');

        selectElement.addEventListener('change', function(event) {

            var selectedValue = event.target.value;

            $("#overlay").show();
            if (selectedValue) {
		
                window.location.href = selectedValue;

            }
        });
    });
    
    function getMDROfMainMerchant(mid,id,businessName){
    	$("#overlay").show();
    	document.location.href = '${pageContext.request.contextPath}/editMdr/getMdr?mid='
    	    + mid+ '&id=' +id+'&businessName=' +businessName;
    }
    
    function getMDROfSubMerchant(mid,id,submerchantid,businessName,subMerchantBusinessName){
    	$("#overlay").show();
    	document.location.href = '${pageContext.request.contextPath}/editMdr/getSubMerchantMdr?mid='
    	    + mid+'&id='+id+'&submerchantId='+submerchantid+ '&businessName=' +businessName+'&subMerchantBusinessName=' +subMerchantBusinessName;
    }
    
    function fetchSubMidList(id,mainmerchant_id){
    	$("#overlay").show();
    	document.location.href = '${pageContext.request.contextPath}/editMdr/getSubMidList?id='
    	    + id+ '&mainMerchantId=' +mainmerchant_id;
    }
    
   

var ids = [
    'fpx_merchantmdr',
	'fpx_hostmdr',
    'fpx_mobimdr',
    'fpx_minimummdr',
    'localdebitvisamdr',
    'localcreditvisamdr',
    'foreigndebitvisamdr',
    'foreigncreditvisamdr',
    'localdebitmastermdr',
    'localcreditmastermdr',
    'foreigndebitmastermdr',
    'foreigncreditmastermdr',
    'localdebitunionmdr',
    'localcreditunionmdr',
    'foreigndebitunionmdr',
    'foreigncreditunionmdr',
    'boost_merchantmdr',
    'boost_hostmdr',
    'boost_mobimdr',
    'boost_minimummdr',
    'grab_merchantmdr',
    'grab_hostmdr',
    'grab_mobimdr',
    'grab_minimummdr',
    'tng_merchantmdr',
    'tng_hostmdr',
    'tng_mobimdr',
    'tng_minimummdr',
    'spp_merchantmdr',
    'spp_hostmdr',
    'spp_mobimdr',
    'spp_minimummdr',
    'payout_merchantmdr',
    'payout_hostmdr',
    'payout_mobimdr',
    'payout_minimummdr'
];


var ids_submerchant = [
    'fpx_merchantmdr_submerchant', 'fpx_hostmdr_submerchant', 'fpx_mobimdr_submerchant', 'fpx_minimummdr_submerchant', 'localdebitvisamdr_submerchant', 'localcreditvisamdr_submerchant',
    'foreigndebitvisamdr_submerchant', 'foreigncreditvisamdr_submerchant', 'localdebitmastermdr_submerchant', 'localcreditmastermdr_submerchant', 'foreigndebitmastermdr_submerchant', 'foreigncreditmastermdr_submerchant',
    'localdebitunionmdr_submerchant', 'localcreditunionmdr_submerchant', 'foreigndebitunionmdr_submerchant', 'foreigncreditunionmdr_submerchant', 'boost_merchantmdr_submerchant', 'boost_hostmdr_submerchant',
    'boost_mobimdr_submerchant', 'boost_minimummdr_submerchant', 'grab_merchantmdr_submerchant', 'grab_hostmdr_submerchant', 'grab_mobimdr_submerchant', 'grab_minimummdr_submerchant', 'tng_merchantmdr_submerchant',
    'tng_hostmdr_submerchant', 'tng_mobimdr_submerchant', 'tng_minimummdr_submerchant', 'spp_merchantmdr_submerchant', 'spp_hostmdr_submerchant', 'spp_mobimdr_submerchant', 'spp_minimummdr_submerchant',
    'payout_merchantmdr_submerchant', 'payout_hostmdr_submerchant', 'payout_mobimdr_submerchant', 'payout_minimummdr_submerchant'
];





    
    

// Function to collect form data and submit the form

function collectAndSubmitFormData() {
    var formData = {};
 
    formData.fpx = {
        merchantmdr: $('#fpx_merchantmdr').val(),
        hostmdr: $('#fpx_hostmdr').val(),
        mobimdr: $('#fpx_mobimdr').val(),
        minimummdr: $('#fpx_minimummdr').val(),
       
    };
    formData.cards = {
            visa: {
                localdebitmdr: $('#localdebitvisamdr').val(),
                localcreditmdr: $('#localcreditvisamdr').val(),
                foriegndebitmdr: $('#foreigndebitvisamdr').val(),
                foriegncreditmdr: $('#foreigncreditvisamdr').val()
            },
            master: {
                localdebitmdr: $('#localdebitmastermdr').val(),
                localcreditmdr: $('#localcreditmastermdr').val(),
                foriegndebitmdr: $('#foreigndebitmastermdr').val(),
                foriegncreditmdr: $('#foreigncreditmastermdr').val()
            },
            union: {
                localdebitmdr: $('#localdebitunionmdr').val(),
                localcreditmdr: $('#localcreditunionmdr').val(),
                foriegndebitmdr: $('#foreigndebitunionmdr').val(),
                foriegncreditmdr: $('#foreigncreditunionmdr').val()
            }
        };
    
   		formData.ewallet = {
            boost: {
                merchantmdr: $('#boost_merchantmdr').val(),
                hostmdr: $('#boost_hostmdr').val(),
                mobimdr: $('#boost_mobimdr').val(),
                minimummdr: $('#boost_minimummdr').val()
            },
            grab: {
                merchantmdr: $('#grab_merchantmdr').val(),
                hostmdr: $('#grab_hostmdr').val(),
                mobimdr: $('#grab_mobimdr').val(),
                minimummdr: $('#grab_minimummdr').val()
            },
            tng: {
                merchantmdr: $('#tng_merchantmdr').val(),
                hostmdr: $('#tng_hostmdr').val(),
                mobimdr: $('#tng_mobimdr').val(),
                minimummdr: $('#tng_minimummdr').val()
            },
            spp: {
                merchantmdr: $('#spp_merchantmdr').val(),
                hostmdr: $('#spp_hostmdr').val(),
                mobimdr: $('#spp_mobimdr').val(),
                minimummdr: $('#spp_minimummdr').val()
            }
        };
   	  formData.payout = {
   	         merchantmdr: $('#payout_merchantmdr').val(),
   	         hostmdr: $('#payout_hostmdr').val(),
   	         mobimdr: $('#payout_mobimdr').val(),
   	         minimummdr: $('#payout_minimummdr').val(),
   	        
   	     };
   	  
   	  formData.mid = {
   			  mid : $('#midToUpdateMDR').val(),
   	  }
   	  
   	 $('#updateMdrDetails').val(JSON.stringify(formData));

   	 
      
      $('#hiddenForm').submit();

}


function collectAndSubmitFormDataForSubMerchant() {
    var formData = {};

    formData.fpx = {
        merchantmdr: $('#fpx_merchantmdr_submerchant').val(),
        hostmdr: $('#fpx_hostmdr_submerchant').val(),
        mobimdr: $('#fpx_mobimdr_submerchant').val(),
        minimummdr: $('#fpx_minimummdr_submerchant').val(),
    };

    formData.cards = {
        visa: {
            localdebitmdr: $('#localdebitvisamdr_submerchant').val(),
            localcreditmdr: $('#localcreditvisamdr_submerchant').val(),
            foriegndebitmdr: $('#foreigndebitvisamdr_submerchant').val(),
            foriegncreditmdr: $('#foreigncreditvisamdr_submerchant').val()
        },
        master: {
            localdebitmdr: $('#localdebitmastermdr_submerchant').val(),
            localcreditmdr: $('#localcreditmastermdr_submerchant').val(),
            foriegndebitmdr: $('#foreigndebitmastermdr_submerchant').val(),
            foriegncreditmdr: $('#foreigncreditmastermdr_submerchant').val()
        },
        union: {
            localdebitmdr: $('#localdebitunionmdr_submerchant').val(),
            localcreditmdr: $('#localcreditunionmdr_submerchant').val(),
            foriegndebitmdr: $('#foreigndebitunionmdr_submerchant').val(),
            foriegncreditmdr: $('#foreigncreditunionmdr_submerchant').val()
        }
    };

    formData.ewallet = {
        boost: {
            merchantmdr: $('#boost_merchantmdr_submerchant').val(),
            hostmdr: $('#boost_hostmdr_submerchant').val(),
            mobimdr: $('#boost_mobimdr_submerchant').val(),
            minimummdr: $('#boost_minimummdr_submerchant').val()
        },
        grab: {
            merchantmdr: $('#grab_merchantmdr_submerchant').val(),
            hostmdr: $('#grab_hostmdr_submerchant').val(),
            mobimdr: $('#grab_mobimdr_submerchant').val(),
            minimummdr: $('#grab_minimummdr_submerchant').val()
        },
        tng: {
            merchantmdr: $('#tng_merchantmdr_submerchant').val(),
            hostmdr: $('#tng_hostmdr_submerchant').val(),
            mobimdr: $('#tng_mobimdr_submerchant').val(),
            minimummdr: $('#tng_minimummdr_submerchant').val()
        },
        spp: {
            merchantmdr: $('#spp_merchantmdr_submerchant').val(),
            hostmdr: $('#spp_hostmdr_submerchant').val(),
            mobimdr: $('#spp_mobimdr_submerchant').val(),
            minimummdr: $('#spp_minimummdr_submerchant').val()
        }
    };

    formData.payout = {
        merchantmdr: $('#payout_merchantmdr_submerchant').val(),
        hostmdr: $('#payout_hostmdr_submerchant').val(),
        mobimdr: $('#payout_mobimdr_submerchant').val(),
        minimummdr: $('#payout_minimummdr_submerchant').val(),
    };

    formData.mid = {
        mid: $('#midToUpdateMDR_submerchant').val(),
    };

    
    $('#updateMdrDetailsOfSubMerchant').val(JSON.stringify(formData));

  	 
    
    $('#hiddenFormForSubmerchant').submit();
    
}


var idsMap = {
	    'fpx_merchantmdr': 'FPX Merchant MDR',
	    'fpx_hostmdr': 'FPX Host MDR',
	    'fpx_mobimdr': 'FPX Mobi MDR',
	    'fpx_minimummdr': 'FPX Minimum Value',
	    'localdebitvisamdr': 'Local Debit Visa MDR',
	    'localcreditvisamdr': 'Local Credit Visa MDR',
	    'foreigndebitvisamdr': 'Foreign Debit Visa MDR',
	    'foreigncreditvisamdr': 'Foreign Credit Visa MDR',
	    'localdebitmastermdr': 'Local Debit Master MDR',
	    'localcreditmastermdr': 'Local Credit Master MDR',
	    'foreigndebitmastermdr': 'Foreign Debit Master MDR',
	    'foreigncreditmastermdr': 'Foreign Credit Master MDR',
	    'localdebitunionmdr': 'Local Debit Union MDR',
	    'localcreditunionmdr': 'Local Credit Union MDR',
	    'foreigndebitunionmdr': 'Foreign Debit Union MDR',
	    'foreigncreditunionmdr': 'Foreign Credit Union MDR',
	    'boost_merchantmdr': 'Boost Merchant MDR',
	    'boost_hostmdr': 'Boost Host MDR',
	    'boost_mobimdr': 'Boost Mobi MDR',
	    'boost_minimummdr': 'Boost Minimum Value',
	    'grab_merchantmdr': 'Grab Merchant MDR',
	    'grab_hostmdr': 'Grab Host MDR',
	    'grab_mobimdr': 'Grab Mobi MDR',
	    'grab_minimummdr': 'Grab Minimum Value',
	    'tng_merchantmdr': 'TNG Merchant MDR',
	    'tng_hostmdr': 'TNG Host MDR',
	    'tng_mobimdr': 'TNG Mobi MDR',
	    'tng_minimummdr': 'TNG Minimum Value',
	    'spp_merchantmdr': 'Shopee pay Merchant MDR',
	    'spp_hostmdr': 'Shopee pay Host MDR',
	    'spp_mobimdr': 'Shopee pay Mobi MDR',
	    'spp_minimummdr': 'Shopee pay Minimum Value',
	    'payout_merchantmdr': 'Payout Merchant MDR',
	    'payout_hostmdr': 'Payout Host MDR',
	    'payout_mobimdr': 'Payout Mobi MDR',
	    'payout_minimummdr': 'Payout Minimum Value'
	};
	
var idsMap_submerchant = {
	    'fpx_merchantmdr_submerchant': "Sub Merchant's FPX Merchant MDR",
	    'fpx_hostmdr_submerchant': "Sub Merchant's FPX Host MDR",
	    'fpx_mobimdr_submerchant': "Sub Merchant's FPX Mobi MDR",
	    'fpx_minimummdr_submerchant': "Sub Merchant's FPX Minimum Value",
	    'localdebitvisamdr_submerchant': "Sub Merchant's Local Debit Visa MDR",
	    'localcreditvisamdr_submerchant': "Sub Merchant's Local Credit Visa MDR",
	    'foreigndebitvisamdr_submerchant': "Sub Merchant's Foreign Debit Visa MDR",
	    'foreigncreditvisamdr_submerchant': "Sub Merchant's Foreign Credit Visa MDR",
	    'localdebitmastermdr_submerchant': "Sub Merchant's Local Debit Master MDR",
	    'localcreditmastermdr_submerchant': "Sub Merchant's Local Credit Master MDR",
	    'foreigndebitmastermdr_submerchant': "Sub Merchant's Foreign Debit Master MDR",
	    'foreigncreditmastermdr_submerchant': "Sub Merchant's Foreign Credit Master MDR",
	    'localdebitunionmdr_submerchant': "Sub Merchant's Local Debit Union MDR",
	    'localcreditunionmdr_submerchant': "Sub Merchant's Local Credit Union MDR",
	    'foreigndebitunionmdr_submerchant': "Sub Merchant's Foreign Debit Union MDR",
	    'foreigncreditunionmdr_submerchant': "Sub Merchant's Foreign Credit Union MDR",
	    'boost_merchantmdr_submerchant': "Sub Merchant's Boost Merchant MDR",
	    'boost_hostmdr_submerchant': "Sub Merchant's Boost Host MDR",
	    'boost_mobimdr_submerchant': "Sub Merchant's Boost Mobi MDR",
	    'boost_minimummdr_submerchant': "Sub Merchant's Boost Minimum Value",
	    'grab_merchantmdr_submerchant': "Sub Merchant's Grab Merchant MDR",
	    'grab_hostmdr_submerchant': "Sub Merchant's Grab Host MDR",
	    'grab_mobimdr_submerchant': "Sub Merchant's Grab Mobi MDR",
	    'grab_minimummdr_submerchant': "Sub Merchant's Grab Minimum Value",
	    'tng_merchantmdr_submerchant': "Sub Merchant's TNG Merchant MDR",
	    'tng_hostmdr_submerchant': "Sub Merchant's TNG Host MDR",
	    'tng_mobimdr_submerchant': "Sub Merchant's TNG Mobi MDR",
	    'tng_minimummdr_submerchant': "Sub Merchant's TNG Minimum Value",
	    'spp_merchantmdr_submerchant': "Sub Merchant's Shopee pay Merchant MDR",
	    'spp_hostmdr_submerchant': "Sub Merchant's Shopee pay Host MDR",
	    'spp_mobimdr_submerchant': "Sub Merchant's Shopee pay Mobi MDR",
	    'spp_minimummdr_submerchant': "Sub Merchant's Shopee pay Minimum Value",
	    'payout_merchantmdr_submerchant': "Sub Merchant's Payout Merchant MDR",
	    'payout_hostmdr_submerchant': "Sub Merchant's Payout Host MDR",
	    'payout_mobimdr_submerchant': "Sub Merchant's Payout Mobi MDR",
	    'payout_minimummdr_submerchant': "Sub Merchant's Payout Minimum Value"
	};

	
	
	
	

	
	
$('#submit_btn').click(function () {
    let showAlert = false;
    var error_msg = document.getElementById('error_message');
  
    outerLoop:
    for (let i = 0; i < ids.length; i++) {
        const element = document.getElementById(ids[i]);
        if (element) {
            const input_validate = parseFloat(element.value);
            const max_mdr_value = 10.00;
            //console.log("compare : " + ids[i] + (input_validate > max_mdr_value));
            if (input_validate > max_mdr_value) {
                showAlert = true;
                error_msg.style.color = "red";
                error_msg.style.display = "block";
                error_msg.innerHTML = "Maximum MDR Value is 10.00 RM. Please Enter the valid MDR in the " + idsMap[ids[i]];
                break outerLoop;
            }else if (
                    input_validate <= 0 &&
                    (
                        ids[i].includes('_merchantmdr') ||
                       /*  ids[i].includes('_minimummdr') || */
                        ids[i].includes('visamdr') ||
                        ids[i].includes('mastermdr') ||
                        ids[i].includes('unionmdr')
                    )
                ) {
                showAlert = true;
                error_msg.style.color = "red";
                error_msg.style.display = "block";
                error_msg.innerHTML = "MDR Should not be 0. Please Enter the valid MDR in the " + idsMap[ids[i]];
                break outerLoop;
            }
        }
    }
    
    if(!showAlert){   	
    	openConfirmPopup();
    }
    
});
    
    
$('#submit_btn_submerchant').click(function () {
    let showAlert = false;

    var error_msg_submerchant = document.getElementById('error_message_submerchant');


    outerLoop:
        // Validation for ids_submerchant array
        for (let i = 0; i < ids_submerchant.length; i++) {
            const element = document.getElementById(ids_submerchant[i]);
            if (element) {
                const input_validate = parseFloat(element.value);
                const max_mdr_value = 10.00;
                //console.log("compare : " + ids_submerchant[i] + (input_validate > max_mdr_value));
                if (input_validate > max_mdr_value) {
                    showAlert = true;
                    error_msg_submerchant.style.color = "red";
                    error_msg_submerchant.style.display = "block";
                    error_msg_submerchant.innerHTML = "Maximum MDR Value is 10.00 RM. Please Enter the valid MDR in the " + idsMap_submerchant[ids_submerchant[i]];
                    break outerLoop; // Exit the loop if validation fails
                }else if (
                        input_validate <= 0 &&
                        (
                        		ids_submerchant[i].includes('_merchantmdr') ||
                        		/* ids_submerchant[i].includes('_minimummdr') || */
                        		ids_submerchant[i].includes('visamdr') ||
                        		ids_submerchant[i].includes('mastermdr') ||
                        		ids_submerchant[i].includes('unionmdr')
                        )
                    ){
                    showAlert = true;
                    error_msg_submerchant.style.color = "red";
                    error_msg_submerchant.style.display = "block";
                    error_msg_submerchant.innerHTML = "MDR Should not be 0. Please Enter the valid MDR in the " + idsMap_submerchant[ids_submerchant[i]];
                    break outerLoop;
                }
            }
        }    
    
    if(!showAlert){
    	
    	openConfirmPopupForSubmerchant()
    }
    
});
	



document.addEventListener("DOMContentLoaded", function () {

    var firstType = "${firstType}";
    
    ////console.log("fist type : ",firstType);

    if (firstType === 'FPX') {
        showContentByType('fpx');

      	
    } else if (firstType === 'VISA' || firstType === 'MASTER' || firstType === 'UNIONPAY') {
        showContentByType('cards');
      
    } else if (firstType === 'BOOST' || firstType === 'GRAB' || firstType === 'SHOPPY' || firstType === 'TNG') {
        showContentByType('ewallet');
       
    } else if (firstType === 'PAYOUT') {
        showContentByType('payout');
       
    }
    
  
    //col space for btns dynamically

    function showContentByType(type) {
        if (type) {
            
            var typeLower = type.toLowerCase();
  
            
            
            var typeContainer = document.getElementById(typeLower + '_container');

            var typeBtn = document.getElementById(typeLower + '_mdr_btn');
            
            if (typeContainer) {
                typeContainer.style.display = 'block';
                typeBtn.classList.add('tab_active');
                
            } else {
                console.error("Container for type not found:", type);
            }
        } else {
            console.error("Type is empty or not valid");
        }
    }
    
 

    ids.forEach(id => {
        const input = document.getElementById(id);
        var error_message = document.getElementById('error_message');
        if (input) {
            input.addEventListener('input', function(event) {
            	error_message.style.display = "none";
                var value = event.target.value;
                const mdrValue = value.replace(/[^0-9]/g, '');
                var formatedvalue = (parseFloat(mdrValue) / 100).toFixed(2);
                var final_mdr = formatedvalue;
                //console.log(isNaN(final_mdr));
                if (isNaN(final_mdr)) {
                    event.target.value = '0.00';
                }else{
                event.target.value = final_mdr;
                }
                //console.log("Final value : ", final_mdr);
                
                
             // If input is a merchant MDR input, calculate mobi MDR
                if (id.includes('merchantmdr')) {
                    // Extract the prefix to find the corresponding host MDR input
                    const prefix = id.replace('_merchantmdr', '');
                    const hostInputId = prefix + '_hostmdr';
                    const mobiInputId = prefix + '_mobimdr';

                    // Find the host MDR input
                    const hostInput = document.getElementById(hostInputId);
                    if (hostInput) {
                        // Get the values of merchant MDR and host MDR inputs
                        const hostValue = parseFloat(hostInput.value) || 0;
                        const merchantValue = parseFloat(final_mdr) || 0;

                        // Calculate mobi MDR by adding merchant and host MDR values
                        const mobiValue = (merchantValue - hostValue).toFixed(2);
                        
                       
                       // Remove the leading minus sign, if present
		                   //  const mobimainmerchantmdr = mobiValue.toString().replace(/^(-)?/, '');
		                     const mobimainmerchantmdr = mobiValue.toString();
		
		                     // Set the calculated mobi MDR value
		                     const mobiInputMainMerchant = document.getElementById(mobiInputId);
		                     if (mobiInputMainMerchant) {
		                    	 mobiInputMainMerchant.value = mobimainmerchantmdr;
		                     }
                    }
                }

            });
        }
    });
								  
});




/* sub merchant content showing   */

document.addEventListener("DOMContentLoaded", function () {
    var firstTypeSubmerchant = "${firstType_submerchant}";

    if (firstTypeSubmerchant === 'FPX') {
        showContentByTypeSubmerchant('fpx');
    } else if (firstTypeSubmerchant === 'VISA' || firstTypeSubmerchant === 'MASTER' || firstTypeSubmerchant === 'UNIONPAY') {
        showContentByTypeSubmerchant('cards');
    } else if (firstTypeSubmerchant === 'BOOST' || firstTypeSubmerchant === 'GRAB' || firstTypeSubmerchant === 'SHOPPY' || firstTypeSubmerchant === 'TNG') {
        showContentByTypeSubmerchant('ewallet');
    } else if (firstTypeSubmerchant === 'PAYOUT') {
        showContentByTypeSubmerchant('payout');
    }

    function showContentByTypeSubmerchant(type) {
        if (type) {
            var typeLower = type.toLowerCase();
            var typeContainer = document.getElementById(typeLower + '_container_submerchant');
            var typeBtn = document.getElementById(typeLower + '_mdr_btn_submerchant');
            
            if (typeContainer) {
                typeContainer.style.display = 'block';
                typeBtn.classList.add('tab_active');
            } else {
                console.error("Container for type not found:", type);
            }
        } else {
            console.error("Type is empty or not valid");
        }
    }

    ids_submerchant.forEach(id => {
        const input = document.getElementById(id);
        var error_msg_submerchant = document.getElementById('error_message_submerchant');
        
        if (input) {
            input.addEventListener('input', function(event) {
            	error_msg_submerchant.style.display = "none";
                var value = event.target.value;
                const mdrValue = value.replace(/[^0-9]/g, '');
                var formatedvalue = (parseFloat(mdrValue) / 100).toFixed(2);
                var final_mdr = formatedvalue;
                
                if (isNaN(final_mdr)) {
                    event.target.value = '0.00';
                } else {
                    event.target.value = final_mdr;
                }
                
                // If input is a merchant MDR input, calculate mobi MDR
                if (id.includes('merchantmdr_submerchant')) {
                    // Extract the prefix to find the corresponding host MDR input
                    const prefix = id.replace('_merchantmdr_submerchant', '');
                    const hostInputId = prefix + '_hostmdr_submerchant';
                    const mobiInputId = prefix + '_mobimdr_submerchant';

                    // Find the host MDR input
                    const hostInput = document.getElementById(hostInputId);
                    if (hostInput) {
                        // Get the values of merchant MDR and host MDR inputs
                        const hostValue = parseFloat(hostInput.value) || 0;
                        const merchantValue = parseFloat(final_mdr) || 0;

                     
                        const mobiValue = (merchantValue - hostValue).toFixed(2);

                 
		                     //const mobisubmerchantmdr = mobiValue.toString().replace(/^(-)?/, '');
		                     const mobisubmerchantmdr = mobiValue.toString();
		
		                     // Set the calculated mobi MDR value
		                     const mobiInputSubmerchant = document.getElementById(mobiInputId);
		                     if (mobiInputSubmerchant) {
		                    	 mobiInputSubmerchant.value = mobisubmerchantmdr;
		                     }
                        
                        
                    }
                }
            });
        }
    });  
});



function enableColumnSize(fpx, cards, ewallet, payout) {
    //console.log(fpx + " " + cards + " " + ewallet + " " + payout);

    var fpx_col = document.getElementById('fpx_col');
    var cards_col = document.getElementById('cards_col');
    var ewallet_col = document.getElementById('ewallet_col');
    var payout_col = document.getElementById('payout_col');

    var fpx_col_submerchant = document.getElementById('fpx_col_submerchant');
    var cards_col_submerchant = document.getElementById('cards_col_submerchant');
    var ewallet_col_submerchant = document.getElementById('ewallet_col_submerchant');
    var payout_col_submerchant = document.getElementById('payout_col_submerchant');

    // Reset all columns to default state
    if (fpx_col) fpx_col.classList.remove('l12', 'm12', 'l3', 'm3', 'l6', 'm6', 'l4', 'm4');
    if (cards_col) cards_col.classList.remove('l12', 'm12', 'l3', 'm3', 'l6', 'm6', 'l4', 'm4');
    if (ewallet_col) ewallet_col.classList.remove('l12', 'm12', 'l3', 'm3', 'l6', 'm6', 'l4', 'm4');
    if (payout_col) payout_col.classList.remove('l12', 'm12', 'l3', 'm3', 'l6', 'm6', 'l4', 'm4');

    if (fpx_col_submerchant) fpx_col_submerchant.classList.remove('l12', 'm12', 'l3', 'm3', 'l6', 'm6', 'l4', 'm4');
    if (cards_col_submerchant) cards_col_submerchant.classList.remove('l12', 'm12', 'l3', 'm3', 'l6', 'm6', 'l4', 'm4');
    if (ewallet_col_submerchant) ewallet_col_submerchant.classList.remove('l12', 'm12', 'l3', 'm3', 'l6', 'm6', 'l4', 'm4');
    if (payout_col_submerchant) payout_col_submerchant.classList.remove('l12', 'm12', 'l3', 'm3', 'l6', 'm6', 'l4', 'm4');

    var trueCount = 0;
    if (fpx) trueCount++;
    if (cards) trueCount++;
    if (ewallet) trueCount++;
    if (payout) trueCount++;

    //console.log("true count : ", trueCount);

    if (trueCount === 1) {
        if (fpx) {
            if (fpx_col) fpx_col.classList.add('l12', 'm12');
            if (fpx_col_submerchant) fpx_col_submerchant.classList.add('l12', 'm12');
        }
        if (cards) {
            if (cards_col) cards_col.classList.add('l12', 'm12');
            if (cards_col_submerchant) cards_col_submerchant.classList.add('l12', 'm12');
        }
        if (ewallet) {
            if (ewallet_col) ewallet_col.classList.add('l12', 'm12');
            if (ewallet_col_submerchant) ewallet_col_submerchant.classList.add('l12', 'm12');
        }
        if (payout) {
            if (payout_col) payout_col.classList.add('l12', 'm12');
            if (payout_col_submerchant) payout_col_submerchant.classList.add('l12', 'm12');
        }
    } else if (trueCount === 2) {
        if (fpx) {
            if (fpx_col) fpx_col.classList.add('l6', 'm6');
            if (fpx_col_submerchant) fpx_col_submerchant.classList.add('l6', 'm6');
        }
        if (cards) {
            if (cards_col) cards_col.classList.add('l6', 'm6');
            if (cards_col_submerchant) cards_col_submerchant.classList.add('l6', 'm6');
        }
        if (ewallet) {
            if (ewallet_col) ewallet_col.classList.add('l6', 'm6');
            if (ewallet_col_submerchant) ewallet_col_submerchant.classList.add('l6', 'm6');
        }
        if (payout) {
            if (payout_col) payout_col.classList.add('l6', 'm6');
            if (payout_col_submerchant) payout_col_submerchant.classList.add('l6', 'm6');
        }
    } else if (trueCount === 3) {
        if (fpx) {
            if (fpx_col) fpx_col.classList.add('l4', 'm4');
            if (fpx_col_submerchant) fpx_col_submerchant.classList.add('l4', 'm4');
        }
        if (cards) {
            if (cards_col) cards_col.classList.add('l4', 'm4');
            if (cards_col_submerchant) cards_col_submerchant.classList.add('l4', 'm4');
        }
        if (ewallet) {
            if (ewallet_col) ewallet_col.classList.add('l4', 'm4');
            if (ewallet_col_submerchant) ewallet_col_submerchant.classList.add('l4', 'm4');
        }
        if (payout) {
            if (payout_col) payout_col.classList.add('l4', 'm4');
            if (payout_col_submerchant) payout_col_submerchant.classList.add('l4', 'm4');
        }
    } else if (trueCount === 4) {
        if (fpx) {
            if (fpx_col) fpx_col.classList.add('l3', 'm3');
            if (fpx_col_submerchant) fpx_col_submerchant.classList.add('l3', 'm3');
        }
        if (cards) {
            if (cards_col) cards_col.classList.add('l3', 'm3');
            if (cards_col_submerchant) cards_col_submerchant.classList.add('l3', 'm3');
        }
        if (ewallet) {
            if (ewallet_col) ewallet_col.classList.add('l3', 'm3');
            if (ewallet_col_submerchant) ewallet_col_submerchant.classList.add('l3', 'm3');
        }
        if (payout) {
            if (payout_col) payout_col.classList.add('l3', 'm3');
            if (payout_col_submerchant) payout_col_submerchant.classList.add('l3', 'm3');
        }
    }
}



/* dynamic content shown  */

document.addEventListener("DOMContentLoaded", function () {

    var fpx = false;
    var cards = false;
    var ewallet = false;
    var payout = false;

    // Check if the first list is present
    <c:if test="${not empty typeList}">
        <c:forEach var="type" items="${typeList}">
            //console.log("Type:", "${type}");
            if ("${type}" === 'FPX') { 
                fpx = true;  
            } else if ("${type}" === 'VISA' || "${type}" === 'MASTER' || "${type}" === 'UNIONPAY') {      
                cards = true;
            } else if ("${type}" === 'BOOST' || "${type}" === 'GRAB' || "${type}" === 'SHOPPY' || "${type}" === 'TNG') {   
                ewallet = true;
            } else if ("${type}" === 'PAYOUT') {
                payout = true;
            }
        </c:forEach> 
    </c:if>

    // Check if the second list is present
    <c:if test="${not empty typeList_submerchant}">
        <c:forEach var="type_submerchant" items="${typeList_submerchant}">
            //console.log("Second Type:", "${type}");
            if ("${type_submerchant}" === 'FPX') { 
                fpx = true;  
            } else if ("${type_submerchant}" === 'VISA' || "${type_submerchant}" === 'MASTER' || "${type_submerchant}" === 'UNIONPAY') {      
                cards = true;
            } else if ("${type_submerchant}" === 'BOOST' || "${type_submerchant}" === 'GRAB' || "${type_submerchant}" === 'SHOPPY' || "${type_submerchant}" === 'TNG') {   
                ewallet = true;
            } else if ("${type_submerchant}" === 'PAYOUT') {
                payout = true;
            }
        </c:forEach> 
    </c:if>

    enableColumnSize(fpx, cards, ewallet, payout);


    
    
   // mid to update on select dropdown when page refresh 
     	
 	var mid_to_update = document.getElementById('midToUpdateMDR').value;

 if (mid_to_update) {

     var selectDropdown = document.getElementById('main_merchant_mid');
     
     ////console.log("Value to update:", mid_to_update);
     
     for (var i = 0; i < selectDropdown.options.length; i++) {
    	 
    	// //console.log("Comparing with option:", selectDropdown.options[i].value);
    	 
         if (selectDropdown.options[i].value === mid_to_update) {
        	 
        //	 //console.log("Match found at index:", i);
          
             selectDropdown.selectedIndex = i;
             break;
         }
     }
     
   
     M.updateTextFields();
 }


 var submerchant_to_update = document.getElementById('subMerchantBusinessName').value.trim();
 //console.log("sub merchant business name:", submerchant_to_update);

 if (submerchant_to_update) {
     var submerchant_selectDropdown = document.getElementById('sub_merchant_name');
     //console.log("Value to update:", submerchant_to_update);

     for (var j = 0; j < submerchant_selectDropdown.options.length; j++) {
         var optionText = submerchant_selectDropdown.options[j].innerText.trim();
         //console.log("Comparing with option:", optionText);
         if (optionText === submerchant_to_update) {
             //console.log("Match found at index:", j, optionText);
             submerchant_selectDropdown.selectedIndex = j;
             break;
         }
     }

     M.updateTextFields();
 }

 
 
 var submid_to_update = document.getElementById('midToUpdateMDR_submerchant').value;

 if (submid_to_update) {

     var submid_selectDropdown = document.getElementById('sub_merchant_mid');
     
     ////console.log("Value to update:", mid_to_update);
     
     for (var i = 0; i < submid_selectDropdown.options.length; i++) {
    	 
    	// //console.log("Comparing with option:", selectDropdown.options[i].value);
    	 
         if (submid_selectDropdown.options[i].value === submid_to_update) {
        	 
        //	 //console.log("Match found at index:", i);
          
             submid_selectDropdown.selectedIndex = i;
             break;
         }
     }
     
   
     M.updateTextFields();
 }
 
 

});


</script>
</body>

</html>