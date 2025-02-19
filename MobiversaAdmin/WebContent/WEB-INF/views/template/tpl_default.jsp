<%@page import="org.springframework.web.context.request.RequestScope"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@page
	import="com.mobiversa.payment.service.AdminService,com.mobiversa.common.bo.AuditTrail"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ page import="com.mobiversa.payment.util.PropertyLoader"%>
<%@ page import="org.slf4j.Logger, org.slf4j.LoggerFactory"%>
<!DOCTYPE html>
<html lang="en">
<head>

<link rel="icon" type="image/gif" sizes="16x16"
	href="${pageContext.request.contextPath}/resourcesNew/img/newMobiMIcon.png" />
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Mobi</title>
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script> -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>


<link
	href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist/dist/chartist.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.css"
	rel="stylesheet">

<link
	href="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/c3/c3.min.css"
	rel="stylesheet">
<link
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/style.css"
	rel="stylesheet">

<link
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/dashboard1.css"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.date.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.time.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dataTable/dataTables.bootstrap4.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dataTable/dataTables.material.min.css">
<link
	href='${pageContext.request.contextPath}/resourcesNew1/select2/dist/css/select2.min.css'
	rel='stylesheet' type='text/css'>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/iconsdashboard.css">

<style>
.test {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 10;
	background-color: rgba(0, 0, 0, 0.5);
	display: none;
}

#close {
	color: #fff;
	background-color: #005baa;
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

</head>
<body>

	<div id="overlay" id="loading-gif">
		<div id="overlay_text">
			<img class="img-fluid"
				src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
		</div>
	</div>
	<!-- <div class="test" id="pop-bg-color"></div> -->

	<script>
		const loadingGif = document.getElementById('loading-gif');
		const tab = document.getElementById('tab');

		// Add a click event listener to the tab
		function loader() {
			// Show the loading GIF
			$("#overlay").show();
			// document.getElementById("pop-bg-color").style.display ="block";

			// Perform the actions that should happen when the tab is clicked

		}
	</script>
	<div class="main-wrapper mini-sidebar" id="main-wrapper"
		data-theme="light" data-layout="vertical" data-navbarbg="skin1"
		data-sidebartype="mini-sidebar" data-sidebar-position="fixed"
		data-header-position="fixed" data-boxed-layout="full">

		<header class="topbar">

			<nav>
				<div class="nav-wrapper">

					<a href="javascript:void(0)" class="brand-logo nav-toggle"> <span
						class="icon"> <i _ngcontent-ffe-c19=""
							class="material-icons icon-image-preview">menu</i>
					</span>

					</a>

					<ul class="left">

						<li class="hide-on-large-only" style="display: none !important;">
							<a href="javascript: void(0);" class="sidebar-toggle"> <span
								class="bars bar1"></span> <span class="bars bar2"></span> <span
								class="bars bar3"></span>
						</a>
						</li>


					</ul>

					<img
						src="${pageContext.request.contextPath}/resourcesNew1/assets/MobiBlueLogo.png"
						id="mobiLogoCenterImg" alt="MobiLogo">

					<ul class="right">

						<li><a class="dropdown-trigger" href="javascript: void(0);"
							data-target="user_dropdown"> <i _ngcontent-ffe-c19=""
								class="material-icons icon-image-preview">more_vert</i></a>
							<ul id="user_dropdown"
								class="mailbox dropdown-content dropdown-user">
								<!--  <li>
            <div class="dw-user-box" style="background-color:#005baa;">
              
              <div class="u-text" style="margin:auto;display:table;">
                <h4 style="color:blue;font-size:12px;text-align:center;"><span style="padding:5px;background-color:#fff;border-radius:5px"> Admin </span></h4> <br/>
               
                 
              </div>
            </div>
          </li> -->
								<li role="separator" class="divider"></li>
								<%-- 	<li><a
									href="${pageContext.request.contextPath}/admProf/changePassWordbyAdmin"><i
										class="material-icons">account_circle</i> Reset Password</a></li> --%>
								<li><a href="#" id="link-logout"><i
										class="material-icons">power_settings_new</i> Logout</a></li>



							</ul></li>
					</ul>

				</div>
			</nav>

			<style>
.topbar nav .mailbox.dropdown-content {
	min-width: 240px;
}

.u-text {
	margin: auto;
	display: table;
}

.has-arrow {
	margin-right: 0px !important;
}
</style>


		</header>

		<aside class="left-sidebar">
			<ul id="slide-out" class="sidenav">

				<%
				// Check if the current user is valid for OPERATION_CHILD_LOGIN
				String operationChildUsernames = PropertyLoader.getFile().getProperty("OPERATION_CHILD_LOGIN");
				String[] validOperationChildUsernames = operationChildUsernames != null
						? operationChildUsernames.split(",")
						: new String[0];
				java.security.Principal principal = request.getUserPrincipal();
				
				String loginName = (principal != null) ? principal.getName() : "";
			
				boolean isValidOperationChild = false;
				for (String username : validOperationChildUsernames) {
					if (loginName.equals(username.trim())) {
						isValidOperationChild = true;
						break;
					}
				}
				
				// Check if the current user is valid for APPROVE_SUBMERCHANT
				String approveSubmerchantUsernames = PropertyLoader.getFile().getProperty("APPROVE_SUBMERCHANT");
				String[] validApproveSubmerchantUsernames = approveSubmerchantUsernames != null
						? approveSubmerchantUsernames.split(",")
						: new String[0];
				boolean isValidApproved = false;
				for (String username : validApproveSubmerchantUsernames) {
					if (loginName.equals(username.trim())) {
						isValidApproved = true;
						break;
					}
				}

				// Check if the current user is valid for OPERATION_PARENT_USERNAME
				String operationParentUsernames = PropertyLoader.getFile().getProperty("OPERATION_PARENT_USERNAME");
				String[] validOperationParentUsernames = operationParentUsernames != null
						? operationParentUsernames.split(",")
						: new String[0];
				boolean isValidOperationParent = false;
				for (String username : validOperationParentUsernames) {
					if (loginName.equals(username.trim())) {
						isValidOperationParent = true;
						break;
					}
				}
				%>





				<li>
					<ul class="collapsible">

						<li><a
							href="${pageContext.request.contextPath}/admin/adm1/dashBoard"
							class="collapsible-header " onclick="loader()"><i
								class="material-icons">dashboard</i><span class="hide-menu">
									Dashboard</span></a></li>

						<li><a
							href="${pageContext.request.contextPath}/admin/txnDashBoard"
							onclick="loader()" class="collapsible-header "><i
								class="material-icons">dashboard</i><span class="hide-menu">
									NOB</span></a></li>

						<li><a
								href="${pageContext.request.contextPath}/TxnMetrics/getTxnMetricsUI"
								onclick="loader()" class="collapsible-header "><i
								class="txns_metrics"></i><span class="hide-menu">
									Transaction Metrics</span></a></li>

						<%--   <li>
                <a href="javascript: void(0);" class="collapsible-header has-arrow"><i class="material-icons">smartphone</i><span class="hide-menu"> Mobile user </span></a>
                <div class="collapsible-body">
                    <ul>
                    
                        <li><a href="${pageContext.request.contextPath}/admin/adm1/dashBoard"><i class="material-icons">dashboard</i><span class="hide-menu"> Dashboard</span></a></li> 
                        <li><a href="${pageContext.request.contextPath}/admin/txnDashBoard"><i class="material-icons">add_box</i><span class="hide-menu">NOB</span></a></li>
                        

                    </ul>
                </div>
            </li> --%>


						<li><a
							href="${pageContext.request.contextPath}/transactionUmweb/payoutbankbalance"
							class="collapsible-header " onclick="loader()"><i
								class="material-icons">account_balance</i><span
								class="hide-menu">Bank Balance</span></a></li>


						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">smartphone</i><span class="hide-menu">
									Mobile user </span></a>
							<div class="collapsible-body">
								<ul>

									<li><a
										href="${pageContext.request.contextPath}/mobileUser/listMobile User Summary"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Mobile User Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/mobileUser/addMobileUser/1"
										onclick="loader()"><i class="material-icons">add_box</i><span
											class="hide-menu">Add Mobile User</span></a></li>
									<%-- <li><a href="${pageContext.request.contextPath}/mobileUser/addUMobileUser/1"><i class="material-icons">add_box</i><span class="hide-menu">Add UMobile User</span></a></li> --%>
									<li><a
										href="${pageContext.request.contextPath}/mobileUser/updateMobileUser"
										onclick="loader()"><i class="material-icons">update</i><span
											class="hide-menu">Update Mobile User</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/device/notificationReq"
										onclick="loader()"><i class="material-icons">notifications</i><span
											class="hide-menu">Add Notification</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/device/notificationList"
										onclick="loader()"><i class="material-icons">notifications_active</i><span
											class="hide-menu"> Notification Summary</span></a></li>

								</ul>
							</div></li>


						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">person</i><span class="hide-menu">
									Merchant </span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/admmerchant/addMerchant"
										onclick="loader()"><i class="material-icons">person_add</i><span
											class="hide-menu">Add Merchant</span></a></li>

									<%-- <li><a
										href="${pageContext.request.contextPath}/registartion/merchantRegistrationForm"
										onclick="loader()"><i class="material-icons">person_add</i><span
											class="hide-menu">Add Merchant</span></a></li>
											 --%>
									<li><a
										href="${pageContext.request.contextPath}/admmerchant/list"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Merchant Summary</span></a></li>

									<li><a
										href="${pageContext.request.contextPath}/admmerchant/dtl"
										onclick="loader()"><i class="material-icons">update</i><span
											class="hide-menu">DTL</span></a></li>



									<%-- existing	<li><a
										href="${pageContext.request.contextPath}/admin/submerchantSum1"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Sub Merchant Summary</span></a></li> --%>

									<li><a
										href="${pageContext.request.contextPath}/admin/adminsubmerchant"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Sub Merchant Summary</span></a></li>

									<li><a
										href="${pageContext.request.contextPath}/grabPay/addGrabPay"
										onclick="loader()"><i class="material-icons">add_box</i><span
											class="hide-menu">Add GrabPay</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/grabPay/updateGrabPay"
										onclick="loader()"><i class="material-icons">update</i><span
											class="hide-menu">Update GrabPay</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/upTC/uploadTermsandCond"
										onclick="loader()"><i class="material-icons">description</i><span
											class="hide-menu">Upload Terms and Conditions</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/upTC/merchantList"
										onclick="loader()"><i class="material-icons">perm_contact_calendar</i><span
											class="hide-menu">View Merchant Profile</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/admmerchant/MerchantChangePassword"
										onclick="loader()"><i class="material-icons">phonelink_lock</i><span
											class="hide-menu">Change Merchant Password</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/admmerchant/recurringList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Recurring Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/admmerchant/motoTxnReqList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYMOTO Request Details</span></a></li>

								</ul>
							</div></li>

						<!-- FPX Host Switch  -->
						<c:if test="${userName.toLowerCase() == 'kavin' || 
									  userName.toLowerCase() == 'ethan' || 
									  userName.toLowerCase() == 'rachel' || 
									  userName.toLowerCase() == 'jared chan' || 
									  userName.toLowerCase() == 'sathyaprabhu manikkam' || 
									  userName.toLowerCase() == 'tamil selvan k' || 
									  userName.toLowerCase() == 'danvanth rathinavel' || 
									  userName.toLowerCase() == 'josephraj elangoven' || 
									  userName.toLowerCase() == 'mohamed fazil' || 
									  userName.toLowerCase() == 'naveenkumar cj' || 
									  userName.toLowerCase() == 'sunilkumar marimuthu' || 
									  userName.toLowerCase() == 'anburaj m' || 
									  userName.toLowerCase() == 'pravin kumar b'}">
									  
						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">view_list</i><span class="hide-menu">FPX
									Host Switch</span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/transaction/fpxHostList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">All Merchants</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/allActiveMerchantsList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Custom Merchants</span></a></li>
								</ul>
						  </div></li>
            </c:if>
            
            
						<!-- Transaction Approval, If payout amount (25k>) -->	
						<c:if test="${userName.toLowerCase() == 'kavin' || 
									  userName.toLowerCase() == 'ethan' || 
									  userName.toLowerCase() == 'rachel' || 
									  userName.toLowerCase() == 'jared chan' ||
									  userName.toLowerCase() == 'pcitest' }">
						<li>
							<a href="${pageContext.request.contextPath}/transaction/payout/list/exceeded-limit-approvals" onclick="loader()" class="collapsible-header">
								<i class="material-icons">view_list</i>
								<span class="hide-menu">Payout Transaction Approval</span>
							</a>
						</li>
						</c:if>

            
						<%
						if (isValidApproved) {
						%>
						<li><a
							href="${pageContext.request.contextPath}/submerchant/risk-compilence/intail"
							onclick="loader()" class="collapsible-header"><i
								class="approve_submerchant"></i> <span class="hide-menu">Approve
									Submerchant</span></a></li>



						<%
						}
						%>

						<%
						if (isValidOperationChild) {
						%>
						<li><a
							href="${pageContext.request.contextPath}/submerchant/operation-child/initial"
							onclick="loader()" class="collapsible-header"><i
								class="operation_child_submerchant"></i> <span class="hide-menu">Operation
									login</span></a></li>
						<%
						}
						%>

						<%
						if (isValidOperationParent) {
						%>
						<li><a
							href="${pageContext.request.contextPath}/submerchant/operation-parent/initial/submerchant"
							onclick="loader()" class="collapsible-header"> <i
								class="operation_parent_submerchant"></i><span class="hide-menu">Operation
									parent</span></a></li>
						<%
						}
						%>


						<!-- Host bank Switch  -->
						<!--
						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">view_list</i><span class="hide-menu">Host
									Bank Switch</span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/transaction/allMerchantList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">All Merchants</span></a></li>

									<li><a
										href="${pageContext.request.contextPath}/transaction/customMerchantList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Custom Merchants</span></a></li>

								</ul>
							</div></li>
						-->




						<li><a
							href="${pageContext.request.contextPath}/transaction/FinanceReport/1"
							onclick="loader()" class="collapsible-header"><i
								class="material-icons">view_list</i><span class="hide-menu">Transaction
									Report</span></a></li>


						<%-- 
						<%
						if (!isValidApproved) {
						%>
						<li><a
							href="${pageContext.request.contextPath}/submerchant/risk-compilence/intail"
							onclick="loader()" class="collapsible-header"><i
								class="material-icons">view_list</i><span class="hide-menu">Approve
									Submerchant</span></a></li>
						<%
						}
						%>



						<%
						if (!isValidOperationChild) {
						%>
						<li><a
							href="${pageContext.request.contextPath}/submerchant/operation-child/initial"
							onclick="loader()" class="collapsible-header"><i
								class="material-icons">view_list</i><span class="hide-menu">Operation
									login</span></a></li>

						<%
						}
						%>

						<%
						if (!isValidOperationParent) {
						%>
						<li><a
							href="${pageContext.request.contextPath}/submerchant/operation-parent/initial/submerchant"
							onclick="loader()" class="collapsible-header"><i
								class="material-icons">view_list</i><span class="hide-menu">Operation
									parent</span></a></li>

						<%
						}
						%>

 --%>


						<%-- <li><a
							href="${pageContext.request.contextPath}/transaction/Ipnconfig"
							onclick="loader()" class="collapsible-header"><i
								class="material-icons">view_list</i><span class="hide-menu">IPN
									Configuration </span></a></li> --%>

						<%-- <li><a
							href="${pageContext.request.contextPath}/transaction/ipnconfig"
							onclick="loader()" class="collapsible-header"><i
								class="material-icons">view_list</i><span class="hide-menu">IPN
									Configuration</span></a></li>
 --%>


						<li>
						  <a href="javascript: void(0);" class="collapsible-header has-arrow">
						    <i class="material-icons">account_balance</i>
						    <span class="hide-menu"> MDR </span>
						  </a>
						  <div class="collapsible-body">
						    <ul>
						      <li>
						        <a href="${pageContext.request.contextPath}/MDR/addMDRDetails" onclick="loader()">
						          <i class="material-icons">add_box</i>
						          <span class="hide-menu">Add MDR Details</span>
						        </a>
						      </li>
						      <%-- 
									<li>
										<a href="${pageContext.request.contextPath}/MDR/addMDRRates">
											<i class="material-icons">add_box</i>
											<span class="hide-menu">Add MDR Rates</span>
										</a>
									</li> --%><%--
									<li>
										<a href="${pageContext.request.contextPath}/MDR/updateMDRDetails">
											<i class="material-icons">update</i>
											<span class="hide-menu">Update MDR Details</span>
										</a>
									</li> 
								--%> 
							  <li>
						        <a href="${pageContext.request.contextPath}/MDR/updateMDRDetailsByRateId" onclick="loader()">
						          <i class="material-icons">update</i>
						          <span class="hide-menu">View MDR Details</span>
						        </a>
						      	</li>
						      <li>
						        <a href="${pageContext.request.contextPath}/editMdr/getAllMerchantsDetails" onclick="loader()" class="">
						          <i class="material-icons">update</i>
						          <span class="hide-menu"> Edit MDR Rates</span>
						        </a>
						      </li>
						      <li>
						        <a href="${pageContext.request.contextPath}/MDR/addProMDR" onclick="loader()">
						          <i class="material-icons">add_box</i>
						          <span class="hide-menu">Add Product MDR</span>
						        </a>
						      </li>
						      <li>
						        <a href="${pageContext.request.contextPath}/MDR/proMDRList" onclick="loader()">
						          <i class="material-icons">update</i>
						          <span class="hide-menu">Update Product MDR</span>
						        </a>
						      </li><%-- 
									<li>
										<a href="${pageContext.request.contextPath}/MDR/settelmentMDR">
											<i class="material-icons">photo_size_select_small</i>
											<span class="hide-menu">Settlement MDR</span>
										</a>
									</li>
									<li>
										<a href="${pageContext.request.contextPath}/MDR/mobiliteSettelmentMDR">
											<i class="material-icons">photo_size_select_small</i>
											<span class="hide-menu">Mobilite Settlement MDR</span>
										</a>
									</li>
									<li>
										<a href="${pageContext.request.contextPath}/MDR/bizappSettlement">
											<i class="material-icons">photo_size_select_small</i>
											<span class="hide-menu">Bizapp Settlement MDR</span>
										</a>
									</li>
									<li>
										<a href="${pageContext.request.contextPath}/MDR/fpxSettelmentMDR">
											<i class="material-icons">photo_size_select_small</i>
											<span class="hide-menu">FPX Settlement MDR</span>
										</a>
									</li> --%> <li>
						        <a href="${pageContext.request.contextPath}/MDR/chargeBack" onclick="loader()">
						          <i class="material-icons">photo_size_select_small</i>
						          <span class="hide-menu">Charge Back or Fee</span>
						        </a>
						      </li><%-- 
									<li>
										<a href="${pageContext.request.contextPath}/transaction/boost/1">
											<i class="material-icons">view_list</i>
											<span class="hide-menu">Boost Settlement Summary</span>
										</a>
									</li> --%> <li>
						        <a href="${pageContext.request.contextPath}/transaction/boostss/1" onclick="loader()">
						          <i class="material-icons">view_list</i>
						          <span class="hide-menu">Boost Summary</span>
						        </a>
						      </li><%-- 
									<li>
										<a href="${pageContext.request.contextPath}/transaction/settlement/1">
											<i class="material-icons">view_list</i>
											<span class="hide-menu">Settlement Summary</span>
										</a>
									</li> --%><%-- 
									<li>
										<a href="${pageContext.request.contextPath}/transaction/mobiliteSettlement/1">
											<i class="material-icons">view_list</i>
											<span class="hide-menu">Mobilite Settlement Summary</span>
										</a>
									</li>
									<li>
										<a href="${pageContext.request.contextPath}/transaction/bizappSettlement/1">
											<i class="material-icons">photo_size_select_small</i>
											<span class="hide-menu">Bizapp Settlement Summary</span>
										</a>
									</li>   --%> 
							  <li>
						        <a href="${pageContext.request.contextPath}/MDR/payLatMerchantSummary/1" onclick="loader()">
						          <i class="material-icons">view_list</i>
						          <span class="hide-menu">PayLater Merchant Summary</span>
						        </a>
						      </li><%--   
									<li>
										<a href="${pageContext.request.contextPath}/MDR/fileRegenerate">
											<i class="material-icons">equalizer</i>
											<span class="hide-menu">File Regeneration</span>
										</a>
									</li>--%><%--  
									<li>
										<a href="${pageContext.request.contextPath}/transaction/fpxTxnSummary/1">
											<i class="material-icons">view_list</i>
											<span class="hide-menu">FPX Transaction Summary</span>
										</a>
									</li> --%>
						    </ul>
						  </div>
						</li>

						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">account_balance_wallet</i><span
								class="hide-menu">Settlement</span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/MDR/settelmentMDR"
										onclick="loader()"><i class="material-icons">photo_size_select_small</i><span
											class="hide-menu">Settlement MDR</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/MDR/mobiliteSettelmentMDR"
										onclick="loader()"><i class="material-icons">photo_size_select_small</i><span
											class="hide-menu">Mobilite Settlement MDR</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/MDR/bizappSettlement"
										onclick="loader()"><i class="material-icons">photo_size_select_small</i><span
											class="hide-menu">Bizapp Settlement MDR</span></a></li>

									<li><a
										href="${pageContext.request.contextPath}/transaction/boost/1"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Boost Settlement Summary</span></a></li>
									<li><a
											href="${pageContext.request.contextPath}/transaction/settlementDetails"
											onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Settlement Details</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/settlement/1"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Settlement Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/mobiliteSettlement/1"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Mobilite Settlement Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/bizappSettlement/1"
										onclick="loader()"><i class="material-icons">photo_size_select_small</i><span
											class="hide-menu">Bizapp Settlement Summary</span></a></li>

								</ul>
							</div></li>


						<%-- 	<li>
							<ul>
								<li><a
									href="${pageContext.request.contextPath}/refund/fund"
									onclick="loader()" class="collapsible-header"><i
										class="material-icons">view_list</i><span class="hide-menu">Refund
											Intiate</span></a></li>

								<li><a
									href="${pageContext.request.contextPath}/refund/proccedToRefund/1"
									onclick="loader()" class="collapsible-header"><i
										class="material-icons">view_list</i><span class="hide-menu">Refund
											Request</span></a></li>
							</ul>
						</li> --%>


						<!-- working refund  -->

						<%-- 	<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">person</i><span class="hide-menu">
									Refund </span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/refund/fund"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Refund Intiate</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/refund/proccedToRefund/1"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Complete Refund </span></a></li>


								</ul>
							</div></li>   --%>


						<%--  <c:if test="${loginname.toLowerCase() == 'pcitest'}">  --%>
						<%-- 	<c:if test="${loginname.toLowerCase() == 'finance@gomobi.io'}">

							<li><a href="javascript: void(0);"
								class="collapsible-header has-arrow"><i
									class="material-icons">person</i><span class="hide-menu">
										Add Deposit </span></a>
								<div class="collapsible-body">
									<ul>
										<li><a
											href="${pageContext.request.contextPath}/transaction/merchantTopupList"><i
												class="material-icons">view_list</i><span class="hide-menu">Merchant
													Topup</span></a></li>

									</ul>
								</div></li>

						</c:if> --%>




						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">person</i><span class="hide-menu">
									Agent </span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/agent1/list"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Agent Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/agent1/addAgent"
										onclick="loader()"><i class="material-icons">add_box</i><span
											class="hide-menu">Add Agent</span></a></li>


								</ul>
							</div></li>

						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">person</i><span class="hide-menu">Sub
									Agent </span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/agent5/list"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Sub Agent Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/agent5/addSubAgent"
										onclick="loader()"><i class="material-icons">add_box</i><span
											class="hide-menu">Add SubAgent</span></a></li>


								</ul>
							</div></li>


						<%--  <li>
                <a href="javascript: void(0);" class="collapsible-header has-arrow"><i class="material-icons">person</i><span class="hide-menu">Master Merchant</span></a>
                <div class="collapsible-body">
                   <ul>
                        <li><a href="${pageContext.request.contextPath}/masterMerchant/list"><i class="material-icons">view_list</i><span class="hide-menu">Master Merchant Summary</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/masterMerchant/addMasterMerchant"><i class="material-icons">add_box</i><span class="hide-menu">Add Master Merchant</span></a></li>

                         
                    </ul>
                </div>
            </li> --%>

						<%--   <li>
                <a href="javascript: void(0);" class="collapsible-header has-arrow"><i class="material-icons">account_balance_wallet</i><span class="hide-menu">Transaction</span></a>
                <div class="collapsible-body">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/transaction/list"><i class="material-icons">view_list</i><span class="hide-menu">Transaction Summary</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/transaction/ezywireList/1"><i class="material-icons">view_list</i><span class="hide-menu">EZYWIRE Transaction Summary</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/transaction/ezywayList/1"><i class="material-icons">view_list</i><span class="hide-menu">EZYWAY Transaction Summary</span></a></li>
                         <li><a href="${pageContext.request.contextPath}/transaction/ezymotoList/1"><i class="material-icons">view_list</i><span class="hide-menu">EZYMOTO Transaction Summary</span></a></li>
                         <li><a href="${pageContext.request.contextPath}/transaction/ezyrecList/1"><i class="material-icons">view_list</i><span class="hide-menu">EZYREC Transaction Summary</span></a></li>
                         <li><a href="${pageContext.request.contextPath}/transaction/ezyrecplusList/1"><i class="material-icons">view_list</i><span class="hide-menu">EZYRECPLUS Transaction Summary</span></a></li>
                         <li><a href="${pageContext.request.contextPath}/transaction/ezypassList/1"><i class="material-icons">view_list</i><span class="hide-menu">EZYPASS Transaction Summary</span></a></li>
                         <li><a href="${pageContext.request.contextPath}/transaction/ezypodList/1"><i class="material-icons">view_list</i><span class="hide-menu">EZYPOD Transaction Summary</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/transaction/umEzywireList/1"><i class="material-icons">view_list</i><span class="hide-menu">UM_EZYWIRE Transaction Summary</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/transaction/umEzywayList/1"><i class="material-icons">view_list</i><span class="hide-menu">UM_EZYWAY Transaction Summary</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/transaction/UMenquiryTransaction/1"><i class="material-icons">view_list</i><span class="hide-menu">UM_EZYWAY Transaction Enquiry</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/transaction/umMotoList/1"><i class="material-icons">view_list</i><span class="hide-menu">UM_EZYMOTO Transaction Summary</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/transaction/UMMotoenquiryTransaction/1"><i class="material-icons">view_list</i><span class="hide-menu">UM_EZYMOTO Transaction Enquiry</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/transaction/umLinkList/1"><i class="material-icons">view_list</i><span class="hide-menu">UM_EZYLINK Transaction Summary</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/transaction/UMLinkenquiryTransaction/1"><i class="material-icons">view_list</i><span class="hide-menu">UM_EZYLINK Transaction Enquiry</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/transaction/umVccList/1"><i class="material-icons">view_list</i><span class="hide-menu">UM_EZYMOTO VCC Transaction Summary</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/transaction/UMVccenquiryTransaction/1"><i class="material-icons">view_list</i><span class="hide-menu">UM_EZYMOTO VCC Transaction Enquiry</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/transaction/umEzyauthList/1"><i class="material-icons">view_list</i><span class="hide-menu">UM_EZYAUTH Transaction Summary</span></a></li>                        
                        <li><a href="${pageContext.request.contextPath}/transaction1/list/1"><i class="material-icons">view_list</i><span class="hide-menu">All Transaction Summary</span></a></li>
                        <li><a href="${pageContext.request.contextPath}/transaction1/enquiryTransaction/1"><i class="material-icons">view_list</i><span class="hide-menu">Transaction Enquiry</span></a></li>
                        
						<c:if test="${fn:contains(accessibleUsersList,adminUserName) }">
							<li><a href="${pageContext.request.contextPath}/transaction1/merchantTransList/1"><i class="material-icons">view_list</i><span class="hide-menu">Merchant Transaction Summary</span></a></li>
						</c:if>
						 
                         <li><a href="${pageContext.request.contextPath}/transaction/ezypassList/1"><i class="material-icons">view_list</i><span class="hide-menu">EZYPASS Transaction Summary</span></a></li>
                    </ul>
                </div>
            </li> --%>

						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">account_balance_wallet</i><span
								class="hide-menu">Paydee Transactions</span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/transaction/list"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/ezywireList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYWIRE Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/ezywayList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYWAY Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/ezymotoList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYMOTO Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/ezylinkliteList/1"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYLINK Lite Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/ezyrecList/1"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYREC Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/ezyrecplusList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYRECPLUS Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/ezypodList/1"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYPOD Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/ezylinkList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYLINK Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/preauthtxn/list"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYAUTH Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction1/list"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">All Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction1/enquiryTransaction"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Transaction Enquiry</span></a></li>
								</ul>
							</div></li>

						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">account_balance_wallet</i><span
								class="hide-menu">U Mobile Transactions</span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/transaction/allUMlist/1"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">All Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/umEzywireList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYWIRE Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/umEzywireplusList/1"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYWIREPLUS Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/umEzywayList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYWAY Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/UMenquiryTransaction"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYWAY Transaction Enquiry</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/umMotoList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYMOTO Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/UMMotoenquiryTransaction"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYMOTO Transaction Enquiry</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/umLinkList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYLINK Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/UMLinkenquiryTransaction"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYLINK Transaction Enquiry</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/umVccList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYMOTO Details</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/UMVccenquiryTransaction"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYMOTO Transaction Enquiry</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/umEzyauthList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYAUTH Transaction Summary</span></a></li>

									<!-- 									rk added			 -->
									<li><a
										href="${pageContext.request.contextPath}/transaction/UMEzyauthenquiryTransaction"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYAUTH Transaction Enquiry</span></a></li>

									<!-- 												rk added -->



									<li><a
										href="${pageContext.request.contextPath}/transaction/umEzyrecList"
										onclick="loader()"> <i class="material-icons">view_list</i><span
											class="hide-menu">EZYREC Transaction Summary</span></a></li>

									<li><a
										href="${pageContext.request.contextPath}/transaction/umSplitList/1"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYSPLIT Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/UMEzyrecenquiryTransaction"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">EZYREC Transaction Enquiry</span></a></li>


								</ul>
							</div></li>



						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">view_list</i><span class="hide-menu">Wallet
									Summary</span></a>
							<div class="collapsible-body">
								<ul>
									<!--         <li><a href="${pageContext.request.contextPath}/transaction/listgrabpay"><i class="material-icons">view_list</i><span class="hide-menu">GRABPAY Transaction Summary</span></a></li> -->
									<li><a
										href="${pageContext.request.contextPath}/transaction/AdminGrabpayList"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">GRABPAY Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/walletBalance"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Boost Wallet Balance</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/listBoost"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Boost Transaction Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/m1PayTransaction"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">TNG/Shopee pay Summary</span></a></li>

										 <li><a
											href="${pageContext.request.contextPath}/duitnow/getAdminDuitnow"
											onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">DuitNow QR Transaction Summary</span></a></li> 

									<li><a
										href="${pageContext.request.contextPath}/transaction/bnplSummaryAdmin"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu"> BNPL Summary</span></a></li>

									<%-- rkboostqr  <li><a href="${pageContext.request.contextPath}/transaction/listBoostqr/1"><i class="material-icons">view_list</i><span class="hide-menu">Boost QR Transaction Summary</span></a></li>  
                     <li><a href="${pageContext.request.contextPath}/transaction/listBoostecom/1"><i class="material-icons">view_list</i><span class="hide-menu">Boost Ecom Transaction Summary</span></a></li>
                     <li><a href="${pageContext.request.contextPath}/transaction/listgrabpayqr"><i class="material-icons">view_list</i><span class="hide-menu">GRABPAY QR Transaction Summary</span></a></li> 
                     <li><a href="${pageContext.request.contextPath}/transaction/listgrabpayecom"><i class="material-icons">view_list</i><span class="hide-menu">GRABPAY Ecom Transaction Summary</span></a></li> 
 --%>


								</ul>
							</div></li>



						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">account_balance</i><span
								class="hide-menu">FPX</span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/MDR/fpxSettelmentMDR"
										onclick="loader()"><i class="material-icons">photo_size_select_small</i><span
											class="hide-menu">FPX Settlement MDR</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/fpxTxnSummary"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">FPX Transaction Summary</span></a></li>

									<li><a
										href="${pageContext.request.contextPath}/transaction/fpxSettlementSummary"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">FPX Settlement File </span></a></li>


								</ul>
							</div></li>

						<li><a
							href="${pageContext.request.contextPath}/transaction/EzySettleList/1"
							onclick="loader()" class="collapsible-header"><i
								class="material-icons">view_list</i><span class="hide-menu">EZYSETTLE
									Summary</span></a></li>


						<li><a
							href="${pageContext.request.contextPath}/transaction/Preauthfee/1"
							onclick="loader()" class="collapsible-header"><i
								class="material-icons">view_list</i><span class="hide-menu">PreAuthFee
									Summary</span></a></li>




						<!-- 		Withdraw and Deposit Details	 -->

						<li><a
							href="${pageContext.request.contextPath}/transaction/depositDetails"
							onclick="loader()" class="collapsible-header"><i
								class="material-icons">view_list</i><span class="hide-menu">Deposit
									Details </span></a></li>


						<li><a
							href="${pageContext.request.contextPath}/transaction/withdrawDetails"
							onclick="loader()" class="collapsible-header"><i
								class="material-icons">view_list</i><span class="hide-menu">Withdraw
									Details </span></a></li>



						<!-- 		Withdraw and Deposit Details	 -->



						<%--       <li>
                <a href="javascript: void(0);" class="collapsible-header has-arrow"><i class="material-icons">view_list</i><span class="hide-menu">EZYAUTH SUMMARY</span></a>
                <div class="collapsible-body">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/preauthtxn/list"><i class="material-icons">view_list</i><span class="hide-menu">EZYAUTH SUMMARY</span></a></li>

                    </ul>
                </div>
            </li> --%>

						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">view_list</i><span class="hide-menu">Merchant
									Report</span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/unusedmerchant/list"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">UnUsed Merchant Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transaction/UMunsedreport/1"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">UM_UnUsed Merchant Summary</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/monthlytxn/list"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Monthly Transaction Summary</span></a></li>

								</ul>
							</div></li>


						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"> <i
								class="material-icons">view_list</i> <span class="hide-menu">Monthly
									Report</span>
						</a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/transaction/fpxSettlementReport"
										onclick="loader()"><i class="material-icons">view_list</i>
											<span class="hide-menu">FPX Settlement Report </span> </a></li>

									<li><a
										href="${pageContext.request.contextPath}/transaction/boostSettlementReport"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Boost Settlement Report </span></a></li>
								</ul>
							</div></li>

						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">devices</i><span class="hide-menu">Device
									Testing</span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/mobileUser/devicemap"
										onclick="loader()"><i class="material-icons">devices</i><span
											class="hide-menu">Device Mapping</span></a></li>


								</ul>
							</div></li>

						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">mail_outline</i><span class="hide-menu">MQ
									Email Testing</span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/transaction1/testMQEmail"
										onclick="loader()"><i class="material-icons">mail</i><span
											class="hide-menu">Test Message Queue Email</span></a></li>



								</ul>
							</div></li>

						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="material-icons">devices</i><span class="hide-menu">Alerts</span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/transaction1/socketErrorAlert"
										onclick="loader()"><i class="material-icons">devices</i><span
											class="hide-menu">Socket Error</span></a></li>
								</ul>
							</div></li>



						<%--    <li>
                <a href="javascript: void(0);" class="collapsible-header has-arrow"><i class="material-icons">settings</i><span class="hide-menu">Settings</span></a>
                <div class="collapsible-body">
                    <ul>
                        <li><a href="${pageContext.request.contextPath}/admProf/changePassWordbyAdmin"><i class="material-icons">account_circle</i> Reset Password</a></li>
          				 <li><a href="#" id="link-logout"><i class="material-icons">power_settings_new</i> Logout</a></li>
 
                    </ul>
                </div>
            </li> --%>

						<li>
							<!--    <a href="javascript:void(0)" class="brand-logo nav-toggle">
              <span class="icon">
               <i _ngcontent-ffe-c19="" class="material-icons icon-image-preview">double_arrow</i>Collapse
              </span>
                
              
            </a>  -->
						</li>

					</ul>
				</li>
			</ul>
		</aside>



		<div class="page-wrapper">
			<c:set var="contentFileFull" scope="page"
				value="../${pageBean.contentFile}.jsp" />
			<c:choose>
				<c:when test="${not empty pageBean.sideMenuFile }">
					<%-- <div>
							<div class="col-md-3">
								<c:set var="contentSideMenuFile" scope="page" value="../${pageBean.sideMenuFile}.jsp" />
								<c:import url="${contentSideMenuFile}" />
							</div> --%>
					<div class="col-md-12">
						<c:import url="${contentFileFull}" />
					</div>
					<!-- </div> -->
				</c:when>
				<c:otherwise>
					<div>
						<c:import url="${contentFileFull}" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>

		<!-- <footer class="center-align m-b-30 m-l-15 m-r-15 ">All Rights Reserved  </footer> -->
	</div>


	<div class="chat-windows "></div>

	<script>
		jQuery(document).ready(function() {
			$('#link-logout').click(function() {

				$('#form-logout').submit();

			});
		});
	</script>


	<form
		action="${pageContext.request.contextPath}/j_spring_security_logout"
		method="post" id="form-logout">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>




	<script
		src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
	<%-- <script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script> --%>
	<%-- <script data-cfasync="false" src="${pageContext.request.contextPath}/resourcesNew1/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script> --%>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/jquery/dist/jquery.min.js"></script>
	<script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/dist/js/canva.js"></script>
	<script src="https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script>

	<script
		src="${pageContext.request.contextPath}/resourcesNew1/dist/js/materialize.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/perfect-scrollbar/dist/js/perfect-scrollbar.jquery.min.js"></script>

	<script
		src="${pageContext.request.contextPath}/resourcesNew1/dist/js/app.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/dist/js/app.init.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/dist/js/custom.min.js"></script>

	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist/dist/chartist.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.min.js"></script>
	<!--c3 JavaScript -->
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/c3/d3.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/c3/c3.min.js"></script>
<%--	<script src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chart.js/dist/Chart.min.js"></script>--%>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/dist/js/pages/dashboards/dashboard1.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/sparkline/sparkline.js"></script>

	<script
		src="${pageContext.request.contextPath}/resourcesNew1/dist/js/validate.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/compressed/picker.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/compressed/picker.date.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/compressed/picker.time.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/compressed/legacy.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/moment/moment.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/daterangepicker/daterangepicker.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/dist/js/pages/forms/datetimepicker/datetimepicker.init.js"></script>


	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resourcesNew1/dataTable/jquery.dataTables.min.js"></script>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resourcesNew1/dataTable/dataTables.material.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew/js/circle-progress.js"></script>
	<script
		src='${pageContext.request.contextPath}/resourcesNew1/select2/dist/js/select2.min.js'
		type='text/javascript'></script>
	<%-- <script src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script> --%>

	<%-- <script
				src="${pageContext.request.contextPath}/resourcesNew/js/plugins/sweetalert.min.js"></script> --%>




</body>
</html>