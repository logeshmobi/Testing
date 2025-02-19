<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!-- <script src="/resourcesNew/js/jQuery-2.1.4.min.js" type="text/javascript"></script>  -->

<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

</head>
<style>
</style>
<body>
	<div class="container-fluid">
		<div class="row">



			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Edit Merchant Details </strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>


		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">
						<div class="table-responsive m-b-20 m-t-15">
							<table class="">
								<tbody>
									<%-- <tr>

										<td>Mid</td>
										<td>${merchant.mid.mid}</td>

									</tr>
									<tr>

										<td>EZYMOTO Mid</td>
										<c:choose>
											<c:when
												test="${merchant.auth3DS == null || merchant.auth3DS == 'No' && merchant.merchantType == null || merchant.merchantType == 'P'}">
												<td>${merchant.mid.motoMid}</td>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
									</tr>
									<tr>

										<td>EZYWAY Mid</td>
										<td>${merchant.mid.ezywayMid}</td>

									</tr>
									<tr>

										<td>EZYREC Mid</td>
										<td>${merchant.mid.ezyrecMid}</td>

									</tr>
									<tr>

										<td>EZYPASS Mid</td>
										<td>${merchant.mid.ezypassMid}</td>

									</tr>
									<tr>

										<td>EZYLINK Mid</td>
										<c:choose>
											<c:when
												test="${merchant.auth3DS == 'Yes' && merchant.merchantType == null || merchant.merchantType == 'P'}">
												<td>${merchant.mid.motoMid}</td>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>

									</tr> --%>
									<tr>



										<c:choose>
											<c:when test="${not empty merchant.mid.umMid}">
												<td>UM_Mid</td>
												<td>${merchant.mid.umMid}</td>
											</c:when>

										</c:choose>

										<%-- 	<td>UM_Mid</td>
										<td>${merchant.mid.umMid}</td>
 --%>
									</tr>
									<tr>


										<c:choose>
											<c:when
												test="${not empty merchant.mid.umMotoMid && (merchant.auth3DS == null || merchant.auth3DS == 'No' || merchant.merchantType == 'U' || merchant.merchantType == 'FIUU')}">
												<td>UM_EZYMOTO Mid</td>
												<td>${merchant.mid.umMotoMid}</td>
											</c:when>
										</c:choose>

										<%-- <td>UM_EZYMOTO Mid</td>
										<c:choose>
											<c:when
												test="${merchant.auth3DS == null || merchant.auth3DS == 'No' && merchant.merchantType == 'U'}">
												<td>${merchant.mid.umMotoMid}</td>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose> --%>

									</tr>
									<tr>

										<%-- <td>UM_EZYWAY Mid</td>
										<td>${merchant.mid.umEzywayMid }</td> --%>

										<c:choose>
											<c:when test="${not empty merchant.mid.umEzywayMid}">
												<td>UM_EZYWAY Mid</td>
												<td>${merchant.mid.umEzywayMid}</td>
											</c:when>
										</c:choose>

									</tr>
									<tr>

										<%-- <td>UM_EZYREC Mid</td>
										<td>${merchant.mid.umEzyrecMid }</td>
 --%>


										<c:choose>
											<c:when test="${not empty merchant.mid.umEzyrecMid}">
												<td>UM_EZYREC Mid</td>
												<td>${merchant.mid.umEzyrecMid}</td>
											</c:when>
										</c:choose>
									</tr>

									<tr>




										<c:choose>
											<c:when
												test="${not empty merchant.mid.umMotoMid && merchant.auth3DS == 'Yes' && ( merchant.merchantType == 'U' || merchant.merchantType == 'FIUU')}">
												<td>UM_EZYLINK Mid</td>
												<td>${merchant.mid.umMotoMid}</td>
											</c:when>
										</c:choose>




										<%-- <td>UM_EZYLINK Mid</td>
										<c:choose>
											<c:when
												test="${merchant.auth3DS == 'Yes' && merchant.merchantType == 'U'}">

												<td>${merchant.mid.umMotoMid}</td>
											</c:when>
											<c:otherwise>
												<td></td>
											</c:otherwise>
										</c:choose>
 --%>
									</tr>
									
									<tr>


										<c:choose>
											<c:when
												test="${not empty merchant.mid.fiuuMid && merchant.merchantType == 'FIUU'}">
												<td>FIUU MID</td>
												<td>${merchant.mid.fiuuMid}</td>
											</c:when>
										</c:choose>
									</tr>
									
									<%-- <tr>

										<td>UM_EZYPASS Mid</td>
										<td>${merchant.mid.umEzypassMid }</td>

									</tr> --%>
									<tr>

										<%-- <td>Email</td>
										<td>${merchant.email}</td> --%>

										<c:choose>
											<c:when test="${not empty merchant.email}">
												<td>Email</td>
												<td>${merchant.email}</td>
											</c:when>
										</c:choose>

									</tr>
									
									
									

									<tr>
										<c:choose>
											<c:when test="${not empty merchantInfo.secoundaryEmail}">
												<td>Secondary email</td>
												<td>${merchantInfo.secoundaryEmail}</td>
											</c:when>
										</c:choose>


									</tr>


									<tr>



										<c:choose>
											<c:when test="${not empty merchant.businessShortName}">
												<td style="height: 30%; width: 30%">Registered Name</td>
												<td>${merchant.businessShortName}</td>
											</c:when>
										</c:choose>


										<%-- <td style="height: 30%; width: 30%">Registered Name</td>
										<td>${merchant.businessShortName}</td> --%>

									</tr>



									<tr>

										<c:choose>
											<c:when test="${not empty merchant.businessName}">
												<td>Business Name</td>
												<td>${merchant.businessName}</td>
											</c:when>
										</c:choose>
										<%-- <td>Business Name</td>
										<td>${merchant.businessName}</td> --%>

									</tr>



									<tr>

										<%-- <td>BusinessReg No</td>
										<td>${merchant.businessRegistrationNumber}</td>
 --%>


										<c:choose>
											<c:when
												test="${not empty merchant.businessRegistrationNumber}">
												<td>BusinessReg No</td>
												<td>${merchant.businessRegistrationNumber}</td>
											</c:when>
										</c:choose>

									</tr>



									<tr>

										<%-- 	<td>Registered Address</td>
										<td>${merchant.businessAddress1}</td>
 --%>


										<c:choose>
											<c:when test="${not empty merchant.businessAddress1}">
												<td>Registered Address</td>
												<td>${merchant.businessAddress1}</td>
											</c:when>
										</c:choose>
									</tr>



									<tr>

										<%-- <td>Business Address</td>
										<td>${merchant.businessAddress2}</td> --%>


										<c:choose>
											<c:when test="${not empty merchant.businessAddress2}">
												<td>Business Address</td>
												<td>${merchant.businessAddress2}</td>
											</c:when>
										</c:choose>

									</tr>




									<tr>

										<c:choose>
											<c:when test="${not empty merchant.city}">
												<td>Business City</td>
												<td>${merchant.city}</td>
											</c:when>
										</c:choose>

									</tr>



									<tr>

										<%-- <td>Business State</td>
										<td>${merchant.state}</td> --%>
										<c:choose>
											<c:when test="${not empty merchant.state}">
												<td>Business State</td>
												<td>${merchant.state}</td>
											</c:when>
										</c:choose>

									</tr>


									<tr>

										<%-- 	<td>Business PostCode</td>
										<td>${merchant.postcode}</td>
 --%>

										<c:choose>
											<c:when test="${not empty merchant.postcode}">
												<td>Business PostCode</td>
												<td>${merchant.postcode}</td>
											</c:when>
										</c:choose>
									</tr>
									<tr>

										<%-- <td>Bank OTP</td>
										<td>${merchant.auth3DS}</td> --%>
										<c:choose>
											<c:when test="${not empty merchant.auth3DS}">
												<td>Bank OTP</td>
												<td>${merchant.auth3DS}</td>
											</c:when>
										</c:choose>

									</tr>

									<tr>

										<c:choose>
											<c:when test="${not empty manualSettlement}">
												<td>Payout Settlement</td>
												<td>${manualSettlement}</td>
											</c:when>
										</c:choose>

										<%-- <td>Payout Settlement</td>
										<td>${manualSettlement}</td> --%>
									</tr>


									<!-- Merchant Status -->

									<tr>
										<c:choose>
											<c:when test="${not empty merchant.status}">
												<td>Merchant Status</td>
												<td>${merchant.status}</td>
											</c:when>
										</c:choose>
									</tr>

									<!-- Async Payout changes -->
									<tr>
										<c:choose>
											<c:when test="${not empty isPayoutAsyncEnabled}">
												<td>Payout Async Handler</td>
												<td>${isPayoutAsyncEnabled}</td>
											</c:when>
										</c:choose>
									</tr>
									<!-- Enable Account Enquiry changes -->
                                    <tr>
                                        <c:choose>
                                            <c:when test="${not empty isAccountEnquiryEnabled}">
                                                <td>Account Enquiry</td>
                                                <td>${isAccountEnquiryEnabled}</td>
                                            </c:when>
                                        </c:choose>
                                    </tr>
                                    <!-- Enable Quick Payout changes -->
                                    <tr>
                                        <c:choose>
                                            <c:when test="${not empty isQuickPayoutEnabled}">
                                                <td>Quick Payout</td>
                                                <td>${isQuickPayoutEnabled}</td>
                                            </c:when>
                                        </c:choose>
                                    </tr>

									<!-- isMaxPayoutLimitSet Payout changes -->
									<tr>
										<c:choose>
											<c:when test="${not empty isMaxPayoutLimitSet}">
												<td>Max Payout Limit Set</td>
												<td>${isMaxPayoutLimitSet}</td>
											</c:when>
										</c:choose>
									</tr>

									<c:choose>
										<c:when test="${adminusername.toLowerCase()=='ethan'}">
											<tr>


												<c:choose>
													<c:when test="${not empty merchant.foreignCard}">
														<td>Enable ForeignCard</td>
														<td>${merchant.foreignCard}</td>
													</c:when>
												</c:choose>

												<%-- <td>Enable ForeignCard</td>
												<td>${merchant.foreignCard}</td> --%>

											</tr>
										</c:when>
									</c:choose>




									<c:choose>
										<c:when
											test="${adminusername.toLowerCase()=='ethan' && not empty merchant.modifiedBy}">
											<tr>
												<td>Ezysettle</td>
												<td>${merchant.modifiedBy}</td>
											</tr>
										</c:when>
									</c:choose>

									<%-- <c:choose>
										<c:when test="${adminusername.toLowerCase()=='ethan'}">
											<tr>
												<td>Ezysettle</td>
												<td>${merchant.modifiedBy}</td>
											</tr>
										</c:when>
									</c:choose> --%>


								</tbody>
							</table>
						</div>

						<a
							href="${pageContext.request.contextPath}/<%=MerchantWebController.URL_BASE%>/edit/${merchant.id}?manualSettlement=${manualSettlement}"
							class="export-btn waves-effect waves-light btn btn-round indigo">Edit
							Details</a>
						<%-- 	<a
							href="${pageContext.request.contextPath}/<%=MerchantWebController.URL_BASE%>/ChangePasswordByAdmin/${merchant.id}"
							class="btn btn-primary blue-btn">Reset Password</a> --%>

					</div>
					<style>
.export_div .select-wrapper {
	width: 65%;
	float: left;
}

.datepicker {
	width: 80% !important;
}

.select-wrapper .caret {
	fill: #005baa;
}

.addUserBtn, .addUserBtn:hover {
	background-color: #fff;
	border: 1px solid #005baa;
	border-radius: 20px;
	color: #005baa;
	font-weight: 600;
}

.blue-btn {
	background-color: #005baa;
	color: #fff;
	border-radius: 20px;
}

.button-class {
	float: right;
}

.float-right {
	float: right;
}
</style>
				</div>

			</div>



		</div>
	</div>
</body>
</html>



