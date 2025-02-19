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

body {
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

input[type=search]:not (.browser-default ) {
	font-size: 14px !important;
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
}

.select-wrapper input.select-dropdown {
	font-size: 14px !important;
	color: #707070;
}

input[type="text"]:not (.browser-default ) {
	border-bottom: 1.5px solid orange !important;
	color: #707070;
	font-size: 14px;
}

input[type="text"]:not (.browser-default )::placeholder {
	color: #70707070;
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

.disabled {
	opacity: 0.5;
	pointer-events: none;
}

.mx-0 {
	margin-left: 0 !important;
	margin-right: 0 !important;
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
							<div class="input-field col s12 m3 l3">Select Business Name</div>

							<div class="input-field col s12 m5 l5">

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
								  || merchant1.mid.umMid != null || merchant1.mid.splitMid!=null || merchant1.mid.boostMid!=null || merchant1.mid.grabMid!=null ||merchant1.mid.fpxMid!=null || merchant1.mid.tngMid!=null || merchant1.mid.shoppyMid!=null || merchant1.mid.bnplMid!=null }">
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
		<c:if test="${businessName != null}">
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
											value="${businessName}"> <label
											for="merchant_name" class="ws-nowrap" style="">Main
											Merchant Name</label>

									</div>

									<div class="col s12 m4 l4 offset-m1 offset-l1 input-field"
										style="">
										<select class="" id="main_merchant_mid"
											name='main_merchant_mid' onchange="getMDROfMainMerchant(this.value)">
											<option value="" selected>Choose MID</option>
											 <c:forEach items="${midList}" var="dto">
            											<option value="${dto}">${dto}</option>
       										 </c:forEach>
										</select> <label class="" style="font-size: 14px;">MID</label>


									</div>
								</div>
							</div>

							<%--                   sub merchant mid --%>

							<div id="submerchant_option" class="">
								<div class="row sub_merchant_mid_options"
									style="padding: 0.50rem 0.75rem;">
									<div class="col s12 m4 l4 pr-0 input-field">

										<select class="" id="sub_merchant_name"
											name="sub_merchant_name">
											<option value="" selected>Choose SubMerchant</option>
											 <c:forEach items="${subMerchantList}" var="submerchant">
            										<option value="${submerchant.id}">${submerchant.businessName}</option>
       										 </c:forEach>
										</select> <label class="ws-nowrap" style="font-size: 14px;">Sub
											Merchant Name</label>

									</div>

									<div class="col s12 m4 l4 offset-m1 offset-l1 input-field"
										style="">
										<select class="" id="sub_merchant_mid" name="sub_merchant_mid">
											<option value="" selected>Choose MID</option>
											<option value="">MID 1</option>
											<option value="">MID 2</option>
											<option value="">MID 3</option>
											<option value="">MID 4</option>
										</select> <label class="" style="font-size: 14px;">MID</label>


									</div>
								</div>
							</div>

							<%--                  mdr rates options  --%>

							<div class="row">
								<div class="col s12">
									<div class="card blue-bg text-white">
										<div class="card-content">
											<div class="row " style="padding: 0.50rem 0.75rem;">
												<div class="col s12 m3 l3 p-0">
													<button type="button" class="mdr_button-tabs"
														id="fpx_mdr_btn">Internet
														Banking</button>
												</div>
												<div class="col s12 m3 l3 p-0">
													<button type="button" class="mdr_button-tabs"
														id="cards_mdr_btn">Card
													</button>
												</div>
												<div class="col s12 m3 l3 p-0">
													<button type="button" class="mdr_button-tabs"
														id="ewallet_mdr_btn">eWallets
													</button>
												</div>
												<div class="col s12 m3 l3 p-0">
													<button type="button" class="mdr_button-tabs"
														id="payout_mdr_btn">Payout
													</button>
												</div>

											</div>

											<div class="row content" id="fpx_container"
												style="padding: 2rem 0; display: none;">
												<div class="col s12 m4 l4 input-field" style="">
													<p class="paymentmethod_text">FPX Internet Banking
														MDR(%)</p>
												</div>
												<div class="col s12 m2 l2 input-field ">
													<input placeholder="1.6" name="fpx_merchantmdr"
														id="fpx_merchantmdr" type="text" class=""> <label
														for="fpx_merchantmdr" style="white-space: nowrap;">Merchant
														MDR</label>
												</div>
												<div class="col s12 m2 l2 input-field disabled">
													<input placeholder="1.6" name="fpx_hostmdr"
														id="fpx_hostmdr" type="text" class=""> <label
														for="fpx_hostmdr" style="white-space: nowrap;">Host
														MDR</label>
												</div>
												<div class="col s12 m2 l2 input-field disabled">
													<input placeholder="1.6" name="fpx_mobimdr"
														id="fpx_mobimdr" type="text" class=""> <label
														for="fpx_mobimdr" style="white-space: nowrap;">Mobi
														MDR</label>
												</div>
												<div class="col s12 m2 l2 input-field ">
													<input placeholder="1.6" name="fpx_minimummdr"
														id="fpx_minimummdr" type="text" class=""> <label
														for="fpx_minimummdr" style="white-space: nowrap;">Minimum
														MDR</label>
												</div>
											</div>

											<%--                                cards mdr inputs    --%>

											<div id="cards_container" class="content"
												style="display: none;">

												<div class="row">
													<div class="col s12 m4 l4 input-field"></div>
													<div class="input-field col s12 m2 l1">
														<img
															src="${pageContext.request.contextPath}/resourcesNew1/assets/visacard.svg"
															alt="visa" width="80" height="80">
													</div>
													<div class="col s12 m2 l1 offset-l2 offset-m1 input-field ">
														<img
															src="${pageContext.request.contextPath}/resourcesNew1/assets/mastercard.svg"
															alt="mastercard" width="80" height="80">
													</div>
													<div class="col s12 m2 l1 offset-l2 offset-m1 input-field ">
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
														<input placeholder="1.6" name="localdebitvisamdr"
															id="localdebitvisamdr" type="text" class=""> <label
															for="localdebitvisamdr" style="white-space: nowrap;">MDR</label>
													</div>
													<div class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
														<input placeholder="1.6" name="localdebitmastermdr"
															id="localdebitmastermdr" type="text" class=""> <label
															for="localdebitmastermdr" style="white-space: nowrap;">MDR</label>
													</div>
													<div class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
														<input placeholder="1.6" name="localdebitunionmdr"
															id="localdebitunionmdr" type="text" class=""> <label
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
														<input placeholder="1.6" name="localcreditvisamdr"
															id="localcreditvisamdr" type="text" class=""> <label
															for="localcreditvisamdr" style="white-space: nowrap;">MDR</label>
													</div>
													<div class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
														<input placeholder="1.6" name="localcreditmastermdr"
															id="localcreditmastermdr" type="text" class=""> <label
															for="localcreditmastermdr" style="white-space: nowrap;">MDR</label>
													</div>
													<div class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
														<input placeholder="1.6" name="localcreditunionmdr"
															id="localcreditunionmdr" type="text" class=""> <label
															for="localcreditunionmdr" style="white-space: nowrap;">MDR</label>
													</div>
												</div>

												<%--                                        foreign debit card --%>

												<div class="row" style="padding: 0.5rem 0;">
													<div class="col s12 m4 l4 input-field" style="">
														<p class="paymentmethod_text">MDR For Foreign Debit
															Card(%)</p>
													</div>
													<div class="col s12 m2 l2  input-field ">
														<input placeholder="1.6" name="foriegndebitvisamdr"
															id="foriegndebitvisamdr" type="text" class=""> <label
															for="foriegndebitvisamdr" style="white-space: nowrap;">MDR</label>
													</div>
													<div class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
														<input placeholder="1.6" name="foriegndebitmastermdr"
															id="foriegndebitmastermdr" type="text" class="">
														<label for="foriegndebitmastermdr"
															style="white-space: nowrap;">MDR</label>
													</div>
													<div class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
														<input placeholder="1.6" name="foriegndebitunionmdr"
															id="foriegndebitunionmdr" type="text" class=""> <label
															for="foriegndebitunionmdr" style="white-space: nowrap;">MDR</label>
													</div>
												</div>

												<%--                                        foreign credit card --%>

												<div class="row" style="padding: 0.5rem 0;">
													<div class="col s12 m4 l4 input-field" style="">
														<p class="paymentmethod_text">MDR For Foreign Credit
															Card(%)</p>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="1.6" name="foriegncreditvisamdr"
															id="foriegncreditvisamdr" type="text" class=""> <label
															for="foriegncreditvisamdr" style="white-space: nowrap;">MDR</label>
													</div>
													<div class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
														<input placeholder="1.6" name="foriegncreditmastermdr"
															id="foriegncreditmastermdr" type="text" class="">
														<label for="foriegncreditmastermdr"
															style="white-space: nowrap;">MDR</label>
													</div>
													<div class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
														<input placeholder="1.6" name="foriegncreditunionmdr"
															id="foriegncreditunionmdr" type="text" class="">
														<label for="foriegncreditunionmdr"
															style="white-space: nowrap;">MDR</label>
													</div>
												</div>


											</div>

											<%--                                   ewallet mdr contents --%>

											<div class="row content" id="ewallet_container"
												style="padding: 2rem 0; display: none;">
												<%--                                       boost mdr --%>
												<div class="row mx-0">
													<div class="col s12 m4 l4 input-field" style="">
														<p class="paymentmethod_text">MDR For Boost (%)</p>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="1.6" name="boost_merchantmdr"
															id="boost_merchantmdr" type="text" class=""> <label
															for="boost_merchantmdr" style="white-space: nowrap;">Merchant
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="1.6" name="boost_hostmdr"
															id="boost_hostmdr" type="text" class=""> <label
															for="boost_hostmdr" style="white-space: nowrap;">Host
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="1.6" name="boost_mobimdr"
															id="boost_mobimdr" type="text" class=""> <label
															for="boost_mobimdr" style="white-space: nowrap;">Mobi
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="1.6" name="boost_minimummdr"
															id="boost_minimummdr" type="text" class=""> <label
															for="boost_minimummdr" style="white-space: nowrap;">Minimum
															MDR</label>
													</div>
												</div>
												<%--    grabpay --%>
												<div class="row mx-0">
													<div class="col s12 m4 l4 input-field" style="">
														<p class="paymentmethod_text">MDR For GrabPay (%)</p>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="1.6" name="grab_merchantmdr"
															id="grab_merchantmdr" type="text" class=""> <label
															for="grab_merchantmdr" style="white-space: nowrap;">Merchant
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="1.6" name="grab_hostmdr"
															id="grab_hostmdr" type="text" class=""> <label
															for="grab_hostmdr" style="white-space: nowrap;">Host
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="1.6" name="grab_mobimdr"
															id="grab_mobimdr" type="text" class=""> <label
															for="grab_mobimdr" style="white-space: nowrap;">Mobi
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="1.6" name="grab_minimummdr"
															id="grab_minimummdr" type="text" class=""> <label
															for="grab_minimummdr" style="white-space: nowrap;">Minimum
															MDR</label>
													</div>
												</div>
												<%--                                        tng --%>
												<div class="row mx-0">
													<div class="col s12 m4 l4 input-field" style="">
														<p class="paymentmethod_text">MDR For Touch'N Go (%)</p>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="1.6" name="tng_merchantmdr"
															id="tng_merchantmdr" type="text" class=""> <label
															for="tng_merchantmdr" style="white-space: nowrap;">Merchant
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="1.6" name="tng_hostmdr"
															id="tng_hostmdr" type="text" class=""> <label
															for="tng_hostmdr" style="white-space: nowrap;">Host
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="1.6" name="tng_mobimdr"
															id="tng_mobimdr" type="text" class=""> <label
															for="tng_mobimdr" style="white-space: nowrap;">Mobi
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="1.6" name="tng_minimummdr"
															id="tng_minimummdr" type="text" class=""> <label
															for="tng_minimummdr" style="white-space: nowrap;">Minimum
															MDR</label>
													</div>
												</div>
												<%--                                        shopee pay--%>
												<div class="row mx-0">
													<div class="col s12 m4 l4 input-field" style="">
														<p class="paymentmethod_text">MDR For Shopee Pay (%)</p>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="1.6" name="spp_merchantmdr"
															id="spp_merchantmdr" type="text" class=""> <label
															for="spp_merchantmdr" style="white-space: nowrap;">Merchant
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="1.6" name="spp_hostmdr"
															id="spp_hostmdr" type="text" class=""> <label
															for="spp_hostmdr" style="white-space: nowrap;">Host
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field disabled">
														<input placeholder="1.6" name="spp_mobimdr"
															id="spp_mobimdr" type="text" class=""> <label
															for="spp_mobimdr" style="white-space: nowrap;">Mobi
															MDR</label>
													</div>
													<div class="col s12 m2 l2 input-field ">
														<input placeholder="1.6" name="spp_minimummdr"
															id="spp_minimummdr" type="text" class=""> <label
															for="spp_minimummdr" style="white-space: nowrap;">Minimum
															MDR</label>
													</div>
												</div>
											</div>
											<%----%>

											<%--                                    payout content--%>
											<div class="row content" id="payout_container"
												style="padding: 2rem 0; display: none;">
												<div class="col s12 m4 l4 input-field" style="">
													<p class="paymentmethod_text">MDR For Payout (%)</p>
												</div>
												<div class="col s12 m2 l2 input-field ">
													<input placeholder="1.6" name="payout_merchantmdr"
														id="payout_merchantmdr" type="text" class=""> <label
														for="payout_merchantmdr" style="white-space: nowrap;">Merchant
														MDR</label>
												</div>
												<div class="col s12 m2 l2 input-field disabled">
													<input placeholder="1.6" name="payout_hostmdr"
														id="payout_hostmdr" type="text" class=""> <label
														for="payout_hostmdr" style="white-space: nowrap;">Host
														MDR</label>
												</div>
												<div class="col s12 m2 l2 input-field disabled">
													<input placeholder="1.6" name="payout_mobimdr"
														id="payout_mobimdr" type="text" class=""> <label
														for="payout_mobimdr" style="white-space: nowrap;">Mobi
														MDR</label>
												</div>
												<div class="col s12 m2 l2 input-field ">
													<input placeholder="1.6" name="payout_minimummdr"
														id="payout_minimummdr" type="text" class=""> <label
														for="payout_minimummdr" style="white-space: nowrap;">Minimum
														MDR</label>
												</div>
											</div>
											<%--                                    --%>
										</div>
									</div>
								</div>
							</div>

							<div style="text-align: center;">
								<button type="button" class="btn btn-primary"
									style="padding: 0 20px;">Submit</button>
							</div>


						</div>
					</div>
				</div>
			</div>
		</c:if>


	</div>


	<!-- Script -->
	<script type="text/javascript">
    $(document).ready(function () {
        $(".select-filter").select2();
    });

    document.addEventListener('DOMContentLoaded', function () {
        var mainMerchantBtn = document.getElementById("main_merchant_tab");
        mainMerchantBtn.classList.add("tab_active");

        var submerchant_option = document.getElementById("submerchant_option");
        var mainmerchant_option = document.getElementById("mainmerchant_option");


        submerchant_option.classList.add("hide");
        mainmerchant_option.classList.remove("hide");


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
    }

    //     mdr tabs changing functionality
    document.addEventListener('DOMContentLoaded', function () {

        var fpx_mdr_btn = document.getElementById("fpx_mdr_btn");
        var card_mdr_btn = document.getElementById("cards_mdr_btn");
        var ewallet_mdr_btn = document.getElementById("ewallet_mdr_btn");
        var payout_mdr_btn = document.getElementById("payout_mdr_btn");

        var fpx_mdr_container = document.getElementById("fpx_mdr_container");
        var card_mdr_container = document.getElementById("card_mdr_container");


        fpxMdrContentOpen(fpx_mdr_btn, card_mdr_btn, ewallet_mdr_btn, payout_mdr_btn);

        fpx_mdr_btn.addEventListener('click', function (event) {
            event.preventDefault();
            fpxMdrContentOpen(fpx_mdr_btn, card_mdr_btn, ewallet_mdr_btn, payout_mdr_btn);
        });
        card_mdr_btn.addEventListener('click', function (event) {
            event.preventDefault();
            cardsMdrContentOpen(fpx_mdr_btn, card_mdr_btn, ewallet_mdr_btn, payout_mdr_btn);
        });
        ewallet_mdr_btn.addEventListener('click', function (event) {
            event.preventDefault();
            ewalletMdrContentOpen(fpx_mdr_btn, card_mdr_btn, ewallet_mdr_btn, payout_mdr_btn);
        });
        payout_mdr_btn.addEventListener('click', function (event) {
            event.preventDefault();
            payoutMdrContentOpen(fpx_mdr_btn, card_mdr_btn, ewallet_mdr_btn, payout_mdr_btn);
        });

    });

    function fpxMdrContentOpen(fpx_mdr_btn, card_mdr_btn, ewallet_mdr_btn, payout_mdr_btn) {
        fpx_mdr_btn.classList.add("tab_active");
        card_mdr_btn.classList.remove("tab_active");
        ewallet_mdr_btn.classList.remove("tab_active");
        payout_mdr_btn.classList.remove("tab_active");
    }

    function cardsMdrContentOpen(fpx_mdr_btn, card_mdr_btn, ewallet_mdr_btn, payout_mdr_btn) {
        fpx_mdr_btn.classList.remove("tab_active");
        card_mdr_btn.classList.add("tab_active");
        ewallet_mdr_btn.classList.remove("tab_active");
        payout_mdr_btn.classList.remove("tab_active");
    }

    function ewalletMdrContentOpen(fpx_mdr_btn, card_mdr_btn, ewallet_mdr_btn, payout_mdr_btn) {
        fpx_mdr_btn.classList.remove("tab_active");
        card_mdr_btn.classList.remove("tab_active");
        ewallet_mdr_btn.classList.add("tab_active");
        payout_mdr_btn.classList.remove("tab_active");
    }

    function payoutMdrContentOpen(fpx_mdr_btn, card_mdr_btn, ewallet_mdr_btn, payout_mdr_btn) {
        fpx_mdr_btn.classList.remove("tab_active");
        card_mdr_btn.classList.remove("tab_active");
        ewallet_mdr_btn.classList.remove("tab_active");
        payout_mdr_btn.classList.add("tab_active");
    }

    //     mdr content showing dynamically

    document.addEventListener("DOMContentLoaded", function () {
        var tabs = document.querySelectorAll('.mdr_button-tabs');

        var defaultContentId = 'fpx_container';
        document.getElementById(defaultContentId).style.display = 'block';

        tabs.forEach(function (tab) {
            tab.addEventListener('click', function () {
                var tabId = this.getAttribute('id');
                var contentId = tabId.replace('_mdr_btn', '_container');

                // Hide all content
                var contents = document.querySelectorAll('.content');
                contents.forEach(function (content) {
                    content.style.display = 'none';
                });

                // Show content associated with clicked tab
                document.getElementById(contentId).style.display = 'block';
            });
        });
    });


// load merchant and assigned to input

    document.addEventListener('DOMContentLoaded', function() {

        var selectElement = document.getElementById('merchantName');

        selectElement.addEventListener('change', function(event) {

            var selectedValue = event.target.value;


            if (selectedValue) {

                window.location.href = selectedValue;

            }
        });
    });
    
    function getMDROfMainMerchant(mid){
    	
    	document.location.href = '${pageContext.request.contextPath}/editMdr/getMdr?mid='
    	    + mid;
    }



</script>
</body>

</html>