<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
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

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/iconsdashboard.css">

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
<!-- Global site tag (gtag.js) - Google Analytics -->
<script async
	src="https://www.googletagmanager.com/gtag/js?id=UA-74242241-3"></script>

<script>
	window.dataLayer = window.dataLayer || [];

	function gtag() {
		dataLayer.push(arguments);
	}

	gtag('js', new Date());
	gtag('config', 'UA-74242241-3');
</script>

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

					<%--   <img src="${pageContext.request.contextPath}/resourcesNew1/assets/MobiNewWhite.png"
	id="mobiLogoCenterImg" alt="MobiLogo"> --%>

					<img
						src="${pageContext.request.contextPath}/resourcesNew1/assets/MobiBlueLogo.png"
						id="mobiLogoCenterImg" alt="MobiLogo">

					<ul class="right">

						<li><a class="dropdown-trigger" href="javascript: void(0);"
							data-target="user_dropdown"> <i _ngcontent-ffe-c19=""
								class="material-icons icon-image-preview">more_vert</i></a>
							<ul id="user_dropdown"
								class="mailbox dropdown-content dropdown-user">
								<!--     <li>
            <div class="dw-user-box" style="background-color:#005baa;">
              
              <div class="u-text" style="margin:auto;display:table;">
                <h4 style="color:blue;font-size:12px;text-align:center;"><span style="padding:5px;background-color:#fff;border-radius:5px"> Merchant </span></h4> <br/>
                
                 
              </div>
            </div>
          </li> -->
								<li role="separator" class="divider"></li>
								<c:if test="${localAdmin == 'No' || localAdmin == 'Yes'}">
									<%-- <li><a
										href="${pageContext.request.contextPath}/merchProf/detailsMerchProf"><i
											class="material-icons">account_circle</i> Change Password</a></li> --%>
								</c:if>
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
</style>

		</header>

		<aside class="left-sidebar">
			<ul id="slide-out" class="sidenav">
				<li>
					<ul class="collapsible">

						<li><a
							href="${pageContext.request.contextPath}/admmerchant/merdashBoard"
							class="collapsible-header"><i class="dashboard_menu"></i><span
								class="hide-menu"> Dashboard</span></a></li>

						<!--	<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="reader_menu"></i><span
								class="hide-menu">Readers</span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/readerweb/list/"><i
											class="material-icons">view_list</i><span class="hide-menu">
												Reader Summary</span></a></li>

								</ul>
							</div></li>  -->



						<!--
						<li><a
							href="${pageContext.request.contextPath}/searchNew/getMasterSearch"
							class="collapsible-header " onclick="loader()"><i
								class="masterSearch"></i><span class="hide-menu"> Master
									Search</span></a></li>
									-->

						<c:if test="${localAdmin == 'No'}">




							<%-- <c:if
								test="${merchant.mid.umEzywayMid == '000000000021591' || UmEzywayMid == '000000000021591' || merchant.mid.boostMid == '000000000055813' || boostMid == '000000000055813' || merchant.mid.boostMid == 'BST000000071615' || boostMid == 'BST000000071615'|| merchant.mid.boostMid == 'BST000000016841' || boostMid == 'BST000000016841' || merchant.mid.umEzywayMid == '000000000000006' ||  UmEzywayMid == '000000000000006' || merchant.mid.boostMid == 'BST000000012618' || boostMid == 'BST000000012618' || merchant.mid.boostMid == 'BST000000017413' || boostMid == 'BST000000017413' || merchant.mid.boostMid == 'BST000000077423' || boostMid == 'BST000000077423' || merchant.mid.boostMid == 'BST000000017573' || boostMid == 'BST000000017573' || merchant.mid.boostMid == 'BST000000013163' || boostMid == 'BST000000013163' || merchant.mid.boostMid == 'BST000000071835' || boostMid == 'BST000000071835' || merchant.mid.umEzywayMid == '000000000006501' || UmEzywayMid == '000000000006501' || merchant.mid.boostMid == 'BST000000083982' || boostMid == 'BST000000083982' || merchant.mid.boostMid == 'BST000000056341' || boostMid == 'BST000000056341' || merchant.mid.boostMid == 'BST000000097861' || boostMid == 'BST000000097861'|| merchant.mid.boostMid == 'BST000000094973' || boostMid == 'BST000000094973'}">
								 --%>
							<c:if
								test="${merchant.mid.umEzywayMid == '000000000021591' || UmEzywayMid == '000000000021591' || merchant.mid.boostMid == '000000000055813' || boostMid == '000000000055813' || merchant.mid.boostMid == 'BST000000071615' || boostMid == 'BST000000071615'|| merchant.mid.boostMid == 'BST000000016841' || boostMid == 'BST000000016841' || merchant.mid.umEzywayMid == '000000000000006' ||  UmEzywayMid == '000000000000006' || merchant.mid.boostMid == 'BST000000012618' || boostMid == 'BST000000012618' || merchant.mid.boostMid == 'BST000000017413' || boostMid == 'BST000000017413' || merchant.mid.boostMid == 'BST000000077423' || boostMid == 'BST000000077423' || merchant.mid.boostMid == 'BST000000017573' || boostMid == 'BST000000017573' || merchant.mid.boostMid == 'BST000000013163' || boostMid == 'BST000000013163' || merchant.mid.boostMid == 'BST000000071835' || boostMid == 'BST000000071835' || merchant.mid.umEzywayMid == '000000000006501' || UmEzywayMid == '000000000006501' || merchant.mid.boostMid == 'BST000000083982' || boostMid == 'BST000000083982' || merchant.mid.boostMid == 'BST000000056341' || boostMid == 'BST000000056341' || merchant.mid.boostMid == 'BST000000097861' || boostMid == 'BST000000097861'|| merchant.mid.boostMid == 'BST000000094973' || boostMid == 'BST000000094973' || merchant.mid.boostMid == 'BST000000088716' || boostMid == 'BST000000088716'}">

								<li><a href="javascript: void(0);"
									class="collapsible-header has-arrow"><i class="reader_menu"></i><span
										class="hide-menu"> Action </span></a>
									<div class="collapsible-body">
										<ul>


											<li><a
												href="${pageContext.request.contextPath}/admin/addSubMerchant"
												onclick="loader()"><i class="material-icons">person_add</i><span
													class="hide-menu">Add Merchant</span></a></li>
											<li><a
												href="${pageContext.request.contextPath}/admin/submerchantSum"
												onclick="loader()"><i class="material-icons">view_list</i><span
													class="hide-menu">Sub Merchant Summary</span></a></li>



										</ul>

									</div></li>

							</c:if>
						</c:if>


						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="fpxsummary_menu"></i><span class="hide-menu">FPX
									Summary</span></a>
							<div class="collapsible-body">
								<ul>
									<li><a
										href="${pageContext.request.contextPath}/transactionUmweb/fpxTxnSummary"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">FPX Transaction Summary</span></a></li>
								</ul>
							</div></li>

						<!-- Paydee txns -->
						<%--  <c:if test="${merchant.merchantType == null || merchant.merchantType == 'P'}"> --%>
						<c:if
							test="${merchant.mid.mid != null ||  merchant.mid.motoMid != null || merchant.mid.ezyrecMid != null || merchant.mid.ezywayMid != null || merchant.mid.ezypassMid != null}">
							<li><a href="javascript: void(0);"
								class="collapsible-header has-arrow"><i class="paydee_menu"></i><span
									class="hide-menu" id="tab">Paydee Summary</span></a>
								<div class="collapsible-body">
									<ul>
										<%--		<li><a
										href="${pageContext.request.contextPath}/transactionweb/list/1"><i
											class="material-icons">view_list</i><span class="hide-menu">All
												Transaction Summary</span></a></li>--%>
										<c:if test="${merchant.mid.mid != null}">
											<%-- <li><a
											href="${pageContext.request.contextPath}/transactionweb/listcash"><i
												class="material-icons">view_list</i><span class="hide-menu">EZYCASH
													Transaction Summary</span></a></li> --%>
											<li><a
												href="${pageContext.request.contextPath}/transactionweb/listcard"
												onclick="loader()"><i class="material-icons">view_list</i><span
													class="hide-menu">EZYWIRE Transaction Summary</span></a></li>
										</c:if>


										<c:if test="${merchant.mid.motoMid != null}">

											<c:if
												test="${merchant.auth3DS == null || merchant.auth3DS == 'NO' || merchant.auth3DS == 'No'}">
												<li><a
													href="${pageContext.request.contextPath}/transactionweb/motolist"
													onclick="loader()"><i class="material-icons">view_list</i><span
														class="hide-menu">EZYMOTO Transaction Summary</span></a></li>
											</c:if>

											<c:if
												test="${merchant.auth3DS == 'YES' || merchant.auth3DS == 'Yes'}">
												<li><a
													href="${pageContext.request.contextPath}/transactionweb/motoLinklist"
													onclick="loader()"><i class="material-icons">view_list</i><span
														class="hide-menu">EZYLINK Transaction Summary</span></a></li>
											</c:if>
										</c:if>
										<c:if test="${merchant.mid.ezywayMid != null}">
											<li><a
												href="${pageContext.request.contextPath}/transactionweb/ezywaylist"
												onclick="loader()"><i class="material-icons">view_list</i><span
													class="hide-menu">EZYWAY Transaction Summary</span></a></li>
										</c:if>
										<c:if test="${merchant.mid.ezyrecMid != null}">
											<li><a
												href="${pageContext.request.contextPath}/transactionweb/ezyreclist"
												onclick="loader()"><i class="material-icons">view_list</i><span
													class="hide-menu">EZYREC Transaction Summary</span></a></li>
											<li><a
												href="${pageContext.request.contextPath}/transactionweb/ezyrecpluslist"
												onclick="loader()"><i class="material-icons">view_list</i><span
													class="hide-menu">EZYRECPLUS Transaction Summary</span></a></li>
										</c:if>
										<%-- 		<c:if test="${merchant.mid.ezypassMid != null}">
										<li><a
											href="${pageContext.request.contextPath}/transactionweb/ezypasslist"><i
												class="material-icons">view_list</i><span class="hide-menu">EZYPASS
													Transaction Summary</span></a></li>
									</c:if> --%>
										<c:if test="${ezyPodCheck == 'Yes'}">
											<li><a
												href="${pageContext.request.contextPath}/transactionweb/ezypodlist"
												onclick="loader()"><i class="material-icons">view_list</i><span
													class="hide-menu">EZYPOD Transaction Summary</span></a></li>
										</c:if>

										<c:if test="${merchant.preAuth == 'Yes'}">
											<li><a
												href="${pageContext.request.contextPath}/merchantpreauth/PreAuth"
												onclick="loader()"><i class="material-icons">view_list</i><span
													class="hide-menu">EZYAUTH Transaction Summary</span></a></li>
										</c:if>





										<%--  <c:if test="${merchant.mid.gpayMid != null}">
                       <li><a href="${pageContext.request.contextPath}/transactionweb/listgrabpay"><i class="material-icons">view_list</i><span class="hide-menu">GRABPAY Transaction Summary</span></a></li>
                       </c:if> --%>

									</ul>
								</div></li>
						</c:if>
						<%--   </c:if> --%>

						<%-- <c:if test="${enableBoost == 'Yes' }">  
		  
        <li><a href="${pageContext.request.contextPath}/transactionweb/boostlist"><i class="material-icons">view_list</i><span class="hide-menu">BOOST Transaction Summary</span></a></li>
     
	  </c:if> --%>

						<!--	<c:if
							test="${enableBoost == 'Yes' || merchant.mid.gpayMid != null }">
							<li><a href="javascript: void(0);"
								class="collapsible-header has-arrow"><i
									class="wallet_menu"></i><span
									class="hide-menu">Wallet Summary</span></a>
								<div class="collapsible-body">
									<ul>
										<c:if test="${merchant.mid.gpayMid != null}">
											<li><a
												href="${pageContext.request.contextPath}/transactionweb/listgrabpay"><i
													class="material-icons">view_list</i><span class="hide-menu">GRABPAY
														Transaction Summary</span></a></li>
										</c:if>
										<c:if test="${enableBoost == 'Yes' }">
											<li><a
												href="${pageContext.request.contextPath}/transactionweb/boostlist"><i
													class="material-icons">view_list</i><span class="hide-menu">BOOST
														Transaction Summary</span></a></li>
										</c:if>
									</ul>
								</div></li>
						</c:if>  -->

						<!--     rksettlementsumm -->
						<!--       <li><a href="${pageContext.request.contextPath}/transactionUmweb/settlesum/1"><i class="material-icons">view_list</i><span class="hide-menu">Settlement Summary</span></a></li> -->



						<!-- umobile txns -->
						<%--  <c:if test="${merchant.merchantType != null && merchant.merchantType == 'U'}"> --%>
						<c:if
							test="${merchant.mid.umMotoMid != null || merchant.mid.umEzyrecMid != null || merchant.mid.umEzywayMid != null || merchant.mid.umEzypassMid != null || merchant.mid.umMid != null}">

							<c:if test="${merchant.mid.umEzywayMid != '000000000000000'}">


								<li id="hide-this-miTrade"><a href="javascript: void(0);"
									class="collapsible-header has-arrow"><i
										class="umobile_menu"></i><span class="hide-menu">U
											Mobile Summary</span></a>

									<div class="collapsible-body">
										<ul>
											<%--		<li><a
										href="${pageContext.request.contextPath}/transactionUmweb/allMerchantUMlist/1"><i
											class="material-icons">view_list</i><span class="hide-menu">All
												Transaction Summary</span></a></li> --%>
											<c:if test="${merchant.mid.umMid != null}">

												<li><a
													href="${pageContext.request.contextPath}/transactionUmweb/umEzywireList"
													onclick="loader()"><i class="material-icons">view_list</i><span
														class="hide-menu">EZYWIRE Transaction Summary</span></a></li>

												<%-- <li><a
											href="${pageContext.request.contextPath}/transactionweb/listcash"><i
												class="material-icons">view_list</i><span class="hide-menu">EZYCASH
													Transaction Summary</span></a></li> --%>
												<%-- 	<li><a
												href="${pageContext.request.contextPath}/transactionUmweb/alllist" onclick="loader()"><i
													class="material-icons">view_list</i><span class="hide-menu">EZYWIRE
														Transaction Summary</span></a></li> --%>
											</c:if>

											<c:if
												test="${merchant.mid.umEzywayMid != null ||  UmEzywayMid == '000000000021591' || UmEzywayMid == '000000111021591'}">

												<li><a
													href="${pageContext.request.contextPath}/transactionUmweb/umEzywayList"
													onclick="loader()"><i class="material-icons">view_list</i><span
														class="hide-menu">EZYWAY Transaction Summary</span></a></li>

												<!--  		<li><a
											href="${pageContext.request.contextPath}/transactionUmweb/UMwayenquiryTransaction/1"><i
												class="material-icons">view_list</i><span class="hide-menu">EZYWAY
													Transaction Enquiry</span></a></li> -->
											</c:if>

											<c:if test="${merchant.mid.umMotoMid != null}">
												<c:if
													test="${merchant.auth3DS == null || merchant.auth3DS == 'NO' || merchant.auth3DS == 'No'}">
													<%-- <li><a
												href="${pageContext.request.contextPath}/transactionweb/listcash"><i
													class="material-icons">view_list</i><span class="hide-menu">EZYCASH
														Transaction Summary</span></a></li> --%>
													<li><a
														href="${pageContext.request.contextPath}/transactionUmweb/umMotoList"
														onclick="loader()"><i class="material-icons">view_list</i><span
															class="hide-menu">EZYMOTO Transaction Summary</span></a></li>
												</c:if>
											</c:if>

											<%-- <c:if test="${merchant.mid.umMotoMid != null}">
                    <li><a href="${pageContext.request.contextPath}/transactionUmweb/UMMotoenquiryTransaction/1"><i class="material-icons">view_list</i><span class="hide-menu">UM_MOTO Transaction Enquiry</span></a></li>
                    </c:if> --%>

											<c:if test="${merchant.mid.umMotoMid != null}">
												<c:if
													test="${merchant.auth3DS == 'YES' || merchant.auth3DS == 'Yes'}">
													<%-- <li><a
												href="${pageContext.request.contextPath}/transactionweb/listcash"><i
													class="material-icons">view_list</i><span class="hide-menu">EZYCASH
														Transaction Summary</span></a></li> --%>
													<li><a
														href="${pageContext.request.contextPath}/transactionUmweb/umLinkList"
														onclick="loader()"><i class="material-icons">view_list</i><span
															class="hide-menu">EZYLINK Transaction Summary</span></a></li>
													<%-- <li><a href="${pageContext.request.contextPath}/transactionUmweb/UMLinkenquiryTransaction/1"><i class="material-icons">view_list</i><span class="hide-menu">UM_EZYLINK Transaction Enquiry</span></a></li> --%>
												</c:if>

												<%-- <c:if
											test="${merchant.ezyMotoVcc == 'YES' || merchant.ezyMotoVcc == 'Yes'}">
											<li><a
												href="${pageContext.request.contextPath}/transactionUmweb/umVccList"><i
													class="material-icons">view_list</i><span class="hide-menu">EZYMOTO
														VCC Details</span></a></li>
											<li><a href="${pageContext.request.contextPath}/transactionUmweb/UMVccenquiryTransaction/1"><i class="material-icons">view_list</i><span class="hide-menu">UM_EZYMOTO VCC Transaction Enquiry</span></a></li>
										</c:if> --%>
											</c:if>

											<c:if test="${merchant.mid.splitMid != null}">
												<li><a
													href="${pageContext.request.contextPath}/transactionUmweb/umSplitList"
													onclick="loader()"><i class="material-icons">view_list</i><span
														class="hide-menu">EZYSPLIT Transaction Summary</span></a></li>
											</c:if>


											<c:if test="${merchant.mid.umMotoMid != null}">
												<li><a
													href="${pageContext.request.contextPath}/transactionUmweb/umEzyauthList"
													onclick="loader()"><i class="material-icons">view_list</i><span
														class="hide-menu">EZYAUTH Transaction Summary</span></a></li>
											</c:if>

											<c:if test="${merchant.mid.umEzyrecMid != null}">
												<li><a
													href="${pageContext.request.contextPath}/transactionUmweb/umEzyrecList"
													onclick="loader()"><i class="material-icons">view_list</i><span
														class="hide-menu">EZYREC Transaction Summary</span></a></li>
											</c:if>




											<%--			<c:if test="${merchant.mid.umSsMotoMid != null}">
										<li><a
											href="${pageContext.request.contextPath}/transactionUmweb/ezyLinkSSList"><i
												class="material-icons">view_list</i><span class="hide-menu">EZYLINK_SS
													Transaction Summary</span></a></li>
									</c:if> --%>

										</ul>
									</div></li>
							</c:if>
						</c:if>


						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i class="ewallet_menu"></i><span
								class="hide-menu">eWallet Summary</span></a>
							<div class="collapsible-body">
								<ul>
									<!--         <li><a href="${pageContext.request.contextPath}/transaction/listgrabpay"><i class="material-icons">view_list</i><span class="hide-menu">GRABPAY Transaction Summary</span></a></li> -->
									<li><a
										href="${pageContext.request.contextPath}/transactionUmweb/MerchantGrab"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">GRABPAY Transaction Summary</span></a></li>

									<li><a
										href="${pageContext.request.contextPath}/transactionUmweb/merchantBoostlist"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Boost Transaction Summary</span></a></li>
									<%-- <li><a
										href="${pageContext.request.contextPath}/transactionUmweb/merchantm1PayTransaction" onclick="loader()"><i
											class="material-icons">view_list</i><span class="hide-menu">TNG/Shopee
												pay Summary</span></a></li> --%>
									<li><a
										href="${pageContext.request.contextPath}/transactionUmweb/merchantTNGTransaction"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">TNG Transaction Summary</span></a></li>



									<li><a
										href="${pageContext.request.contextPath}/transactionUmweb/merchantSHOPPYTransaction"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu">Shopee Transaction Summary</span></a></li>

									<%-- <li><a
										href="${pageContext.request.contextPath}/transactionUmweb/merchantbnplSummary" onclick="loader()"><i
											class="bnpl_summary_menu"></i><span class="hide-menu"> BNPL Summary</span></a></li> --%>




								</ul>
							</div></li>



						<c:if test="${merchant.username != 'martin.tan@mitrade.com'}">

							<li id="Hide-This-Tab-Bnpl"><a href="javascript: void(0);"
								class="collapsible-header has-arrow"><i
									class="bnpl_summary_menu"></i><span class="hide-menu">BNPL
										Summary</span></a>



								<div class="collapsible-body">

									<ul>

										<li><a
											href="${pageContext.request.contextPath}/transactionUmweb/merchantbnplSummary"
											onclick="loader()"><i class="material-icons">view_list</i><span
												class="hide-menu">Atome Summary</span></a></li>

									</ul>

								</div></li>

						</c:if>

						<%-- Start - Failed Transactions of Umobile--%>
						<c:if
							test="${merchant.mid.umMotoMid != null || merchant.mid.umEzyrecMid != null || merchant.mid.umEzywayMid != null || merchant.mid.umMid != null || merchant.mid.fpxMid != null}">
							<li><a href="javascript: void(0);"
								class="collapsible-header has-arrow"><i
									class="failedtr_menu"></i><span class="hide-menu">FPX/CARD
										Failed Summary</span></a>
								<div class="collapsible-body">
									<ul>
										<c:if test="${merchant.mid.umEzywayMid != null}">
											<li><a
												href="${pageContext.request.contextPath}/transactionUmweb/umEzywayFailList"
												onclick="loader()"><i class="material-icons">view_list</i><span
													class="hide-menu">EZYWAY Transaction </span></a></li>
										</c:if>


										<li><a
											href="${pageContext.request.contextPath}/transactionUmweb/fpxfailList"
											onclick="loader()"><i class="material-icons">view_list</i><span
												class="hide-menu">FPX Transaction </span></a></li>

										<c:if test="${merchant.mid.umMotoMid != null}">
											<c:if
												test="${merchant.auth3DS == 'YES' || merchant.auth3DS == 'Yes'}">
												<li><a
													href="${pageContext.request.contextPath}/transactionUmweb/umLinkFailList"
													onclick="loader()"><i class="material-icons">view_list</i><span
														class="hide-menu">EZYLINK Transaction</span></a></li>
											</c:if>
										</c:if>

										<!-- rk added -->

										<c:if
											test="${merchant.mid.umMotoMid != null || merchant.mid.umMid != null}">

											<li><a
												href="${pageContext.request.contextPath}/transactionUmweb/umEzyauthFailList"
												onclick="loader()"><i class="material-icons">view_list</i><span
													class="hide-menu">EZYAUTH Transaction</span></a></li>

										</c:if>




										<c:if test="${merchant.mid.umMotoMid != null}">
											<c:if
												test="${merchant.auth3DS == null || merchant.auth3DS == 'NO' || merchant.auth3DS == 'No'}">
												<li><a
													href="${pageContext.request.contextPath}/transactionUmweb/umMotoFailList"
													onclick="loader()"><i class="material-icons">view_list</i><span
														class="hide-menu">EZYMOTO Transaction</span></a></li>
											</c:if>
										</c:if>

										<c:if test="${merchant.mid.umEzyrecMid != null}">
											<li><a
												href="${pageContext.request.contextPath}/transactionUmweb/umEzyrecFailList"
												onclick="loader()"><i class="material-icons">view_list</i><span
													class="hide-menu">EZYREC Transaction</span></a></li>
										</c:if>

										<c:if test="${merchant.mid.umMid != null}">
											<li><a
												href="${pageContext.request.contextPath}/transactionUmweb/alllistFail"
												onclick="loader()"><i class="material-icons">view_list</i><span
													class="hide-menu">EZYWIRE Transaction</span></a></li>
										</c:if>

									</ul>
								</div></li>
						</c:if>

						<%-- End - Failed Transactions of Umobile--%>



						<li><a href="javascript: void(0);"
							class="collapsible-header has-arrow"><i
								class="settlement_menu"></i><span class="hide-menu">Settlement
									PDF</span></a>
							<div class="collapsible-body">
								<ul>

									<li><a
										href="${pageContext.request.contextPath}/DailyReport/Card"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu"> CARD DailyReport</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transactionweb/dailypdf"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu"> EWALLETS & FPX DailyReport</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/DailyReport/CardOld"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu"> CARD Before[19-01-2023]</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transactionweb/dailypdfOld"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu"> EWALLETS&FPX Before[19-01-2023]</span></a></li>
									<li><a
										href="${pageContext.request.contextPath}/transactionweb/pdf"
										onclick="loader()"><i class="material-icons">view_list</i><span
											class="hide-menu"> MONTHLY REPORT</span></a></li>

								</ul>
							</div></li>



						<c:if
							test="${not empty merchant.mid.motoMid || not empty merchant.mid.umMotoMid}">
							<c:if
								test="${merchant.ezyMotoVcc == 'YES' || merchant.ezyMotoVcc == 'Yes'}">
								<li><a href="javascript: void(0);"
									class="collapsible-header has-arrow"><i class="wallet_menu"></i>

										<span class="hide-menu">EZYMOTO </span> </a>
									<div class="collapsible-body">
										<ul>
											<c:if
												test="${merchant.ezyMotoVcc == 'YES' || merchant.ezyMotoVcc == 'Yes'}">
												<li><a
													href="${pageContext.request.contextPath}/transactionMoto/motovc"
													onclick="loader()"><i class="material-icons">account_balance_wallet</i><span
														class="hide-menu">Add EZYMOTO </span></a></li>
												<li><a
													href="${pageContext.request.contextPath}/transactionMoto/vcSummary"
													onclick="loader()"><i class="material-icons">account_balance_wallet</i><span
														class="hide-menu">EZYMOTO Details</span></a></li>
											</c:if>
										</ul>
									</div></li>
							</c:if>
						</c:if>

						<c:if test="${merchant.mid.ezyrecMid != null}">
							<li><a href="javascript: void(0);"
								class="collapsible-header has-arrow"><i class="reader_menu"></i><span
									class="hide-menu">EZYREC Summary</span></a>
								<div class="collapsible-body">
									<ul>
										<li><a
											href="${pageContext.request.contextPath}/transactionMoto/recurringList"
											onclick="loader()"><i class="material-icons">view_list</i><span
												class="hide-menu">Recurring Summary</span></a></li>
									</ul>
								</div></li>
						</c:if>

						<c:if test="${merchant.preAuth == 'Yes'}">
							<li><a href="javascript: void(0);"
								class="collapsible-header has-arrow"><i class="wallet_menu"></i><span
									class="hide-menu">PREAUTH Details</span></a>
								<div class="collapsible-body">
									<ul>

										<c:if
											test="${merchant.merchantType == null || merchant.merchantType == 'P'}">
											<li><a
												href="${pageContext.request.contextPath}/merchantpreauth/PreAuthList"
												onclick="loader()"><i class="material-icons">view_list</i>
													<span class="hide-menu">PREAUTH Transaction Summary</span>
											</a></li>

										</c:if>

										<c:if
											test="${merchant.merchantType != null && merchant.merchantType == 'U'}">
											<li><a
												href="${pageContext.request.contextPath}/merchantpreauth/PreAuthList1"
												onclick="loader()"><i class="material-icons">view_list</i><span
													class="hide-menu">PREAUTH Transaction Summary</span></a></li>

										</c:if>

									</ul>
								</div></li>
						</c:if>




						<%-- <c:if test="${merchant.mid.umEzywayMid != '000000000000000' || merchant.merchantType != 'P' || merchant.mid.umEzywayMid != '000000000001000'}">
						<li><a
							href="${pageContext.request.contextPath}/transactionUmweb/EzySettleList"
							class="collapsible-header" onclick="loader()"><i class="ezysettle_summary_menu"></i><span
								class="hide-menu">EZYSETTLE Summary</span></a></li>
					</c:if> --%>

						<c:if
							test="${merchant.username != 'martin.tan@mitrade.com' || merchant.username != 'maxim.p@monetix.pro'} ">
							<li><a
								href="${pageContext.request.contextPath}/transactionUmweb/EzySettleList"
								id="ezysettle" class="collapsible-header" onclick="loader()"><i
									class="ezysettle_summary_menu"></i><span class="hide-menu">EZYSETTLE
										Summary</span></a></li>

						</c:if>

						<%-- 	<c:if test="${merchant.mid.boostMid == '000000000055813'}"> --%>

						<c:if test="${merchant.enblPayout == 'Yes'}">

							<li><a
								href="${pageContext.request.contextPath}/transactionUmweb/payoutlist/1"
								class="collapsible-header" onclick="loader()"><i
									class="payout_summary_menu"></i><span cclass="hide-menu">PAYOUT
										Summary </span></a></li>

							<%-- 	<c:choose>
								<c:when test="${merchant.username == 'stellal@monetix.pro' || merchant.username == 'daria.ar@monetix.pro'}">
									
								</c:when>
								<c:otherwise>
									<li><a href="${pageContext.request.contextPath}/transactionUmweb/payoutbalance"
										                                class="collapsible-header"
										onclick="loader()"><i class="payout_summary_menu"></i><span
											                                    cclass="hide-menu">PAYOUT
												Balance </span></a></li>

								</c:otherwise>
							</c:choose> --%>



							<%-- <c:if
							test="${loginname.toLowerCase() == 'mobi' || loginname.toLowerCase() == 'test' || loginname.toLowerCase() == 'uat-mobi@gomobi.io' || loginname.toLowerCase() == 'pcitest' || loginname.toLowerCase() == 'rajiv@businessboosters.com.my' || loginname.toLowerCase() == 'Nishitha'|| loginname.toLowerCase() == 'Rachel' || loginname.toLowerCase() == 'philip.z@spayz.io' || loginname.toLowerCase() == 'v@valetax.com' || loginname.toLowerCase() == 'ethan2@mobiversa.com' || loginname.toLowerCase() == 'daria.ar@monetix.pro' || loginname.toLowerCase() == 'ai4wire@protonmail.com' || loginname.toLowerCase() == 'Winnie.loh@xpayasia.com' || loginname.toLowerCase() == 'alexandra.go@monetix.pro'  || loginname.toLowerCase() == 'partners@ecommpay.com' || loginname.toLowerCase() == 'operations@blenetltd.com' || loginname.toLowerCase() == 'payments@brivioplay.com' || loginname.toLowerCase() == 'skedia@dlocal.com' || loginname.toLowerCase() == 'farhan.khairol@products2u.my' || loginname.toLowerCase() == 'banking@zotapay.com'|| loginname.toLowerCase() == 'martin.tan@mitrade.com'  }">
							 --%>
							<c:if
								test="${loginname.toLowerCase() == 'mobi' || loginname.toLowerCase() == 'test' || loginname.toLowerCase() == 'uat-mobi@gomobi.io' || loginname.toLowerCase() == 'pcitest' || loginname.toLowerCase() == 'rajiv@businessboosters.com.my' || loginname.toLowerCase() == 'Nishitha'|| loginname.toLowerCase() == 'Rachel' || loginname.toLowerCase() == 'philip.z@spayz.io' || loginname.toLowerCase() == 'v@valetax.com' || loginname.toLowerCase() == 'ethan2@mobiversa.com' || loginname.toLowerCase() == 'daria.ar@monetix.pro' || loginname.toLowerCase() == 'ai4wire@protonmail.com' || loginname.toLowerCase() == 'phil.h@xpayasia.com' || loginname.toLowerCase() == 'alexandra.go@monetix.pro'  || loginname.toLowerCase() == 'partners@ecommpay.com' || loginname.toLowerCase() == 'operations@blenetltd.com' || loginname.toLowerCase() == 'payments@brivioplay.com' || loginname.toLowerCase() == 'skedia@dlocal.com' || loginname.toLowerCase() == 'farhan.khairol@products2u.my' || loginname.toLowerCase() == 'banking@zotapay.com'|| loginname.toLowerCase() == 'martin.tan@mitrade.com' || loginname.toLowerCase() == 'AI4wire@protonmail.com'  }">

								<%-- test="${loginname.toLowerCase() == 'mobi' || loginname.toLowerCase() == 'test' || loginname.toLowerCase() == 'uat-mobi@gomobi.io' || loginname.toLowerCase() == 'pcitest' || loginname.toLowerCase() == 'rajiv@businessboosters.com.my' || loginname.toLowerCase() == 'Nishitha'|| loginname.toLowerCase() == 'Rachel' || loginname.toLowerCase() == 'v@valetax.com' || loginname.toLowerCase() == 'ethan2@mobiversa.com' }">
 --%>

								<%-- <li><a
									href="${pageContext.request.contextPath}/transactionUmweb/payoutbalance"
									                                class="collapsible-header"
									onclick="loader()"><i class="payout_summary_menu"></i><span
										                                    cclass="hide-menu">PAYOUT
											Balance </span></a></li> --%>


								<li><a
									href="${pageContext.request.contextPath}/transactionUmweb/updatePayoutbalance"
									class="collapsible-header" onclick="loader()"><i
										class="payout_summary_menu"></i><span cclass="hide-menu">PAYOUT
											Balance</span></a></li>


								<%--  <li><a
									href="${pageContext.request.contextPath}/transactionUmweb/updatePayoutbalance"
									                                class="collapsible-header"
									onclick="loader()"><i class="payout_summary_menu"></i><span
										                                    cclass="hide-menu">Updated
											payout Balance </span></a></li>  --%>
							</c:if>

						</c:if>

						<%-- <c:if test="${loginname.toLowerCase() == 'mobi'}">
 --%>
						<%-- <li><a
								href="${pageContext.request.contextPath}/transactionUmweb/payoutbankbalance"
								class="collapsible-header" onclick="loader()"><i
									class="payout_summary_menu"></i><span cclass="hide-menu">
										Bank Balance </span></a></li> --%>
						<%-- 
						</c:if> --%>
						<%-- 			<c:if test="${merchant.enblPayout == 'Yes' && merchant.username == 'martin.tan@mitrade.com'}">
						<li><a
                                href="${pageContext.request.contextPath}/transactionUmweb/withDrawPage"
                                class="collapsible-header" onclick="loader()"><i class="payout_summary_menu"></i><span
                                    cclass="hide-menu">WithDraw Amount</span></a></li>
	</c:if>
 --%>
					</ul>
				</li>

			</ul>
		</aside>



		<div class="page-wrapper">
			<c:set var="contentFileFull" scope="page"
				value="../${pageBean.contentFile}.jsp" />
			<c:choose>
				<c:when test="${not empty pageBean.sideMenuFile }">

					<div class="col-md-12">
						<c:import url="${contentFileFull}" />
					</div>

				</c:when>
				<c:otherwise>
					<div>
						<c:import url="${contentFileFull}" />
					</div>
				</c:otherwise>
			</c:choose>
		</div>


	</div>


	<div class="chat-windows "></div>

	<script>
		jQuery(document).ready(function() {
			$('#link-logout').click(function() {

				$('#form-logout').submit();

			});
		});
	</script>

	<script>
	if(${merchant.username=='martin.tan@mitrade.com' || merchant.username == 'maxim.p@monetix.pro'}){
		document.getElementById("hide-this-miTrade").style.display = 'none';
	}
	
	if(${merchant.username=='martin.tan@mitrade.com' || merchant.username == 'maxim.p@monetix.pro'}){
		document.getElementById("Hide-This-Tab-Bnpl").style.display = 'none';
	}
	
	</script>


	<form
		action="${pageContext.request.contextPath}/j_spring_security_logout"
		method="post" id="form-logout">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>

	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>

	<%-- <script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script> --%>
	<%-- <script data-cfasync="false" src="${pageContext.request.contextPath}/resourcesNew1/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script> --%>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/jquery/dist/jquery.min.js"></script>
	<script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
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
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chart.js/dist/Chart.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/dist/js/pages/dashboards/dashboard1.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/sparkline/sparkline.js"></script>


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


	<script>
	
	$(document).ready(function() {
		  
		  if(${merchant.mid.umEzywayMid == '000000000000000' || merchant.merchantType == 'P'})
			  {
			  $("#ezysettle").hide();
			  }
		  else{
			  $("#ezysettle").show();
		  }
		 
		});
	
	
		$(document)
				.ready(
						function() {
							$('.payout-count')
									.click(
											function() {

												count_check = document
														.querySelectorAll('input[name="monday_check"]:checked').length;
												//	 count_check = num_0_prepend(count_check);
												$("#payout-check-count").html(
														count_check);

												if (count_check > 0) {
													$('#button-ctrl').prop(
															'disabled', false);
												} else {
													$('#button-ctrl').prop(
															'disabled', true);
												}

											})
						});

		function num_0_prepend(n) {
			return n > 9 ? "" + n : "0" + n;
		}

		$('#button-ctrl').prop('disabled', true);

		$("#disabled_div")
				.click(
						function() {

							var count = 1;

							$
									.ajax({
										async : false,
										type : "GET",
										url : "${pageContext.request.contextPath}/transactionweb/Addcountbypaydee",
										data : {
											"count" : count

										},
										success : function(result) {
										}
									});
						})

		//Check_Box Count Function

		$('.payout-count')
				.click(
						function() {

							if ($("input[type=checkbox]").is(":checked")) {

								var count = 1;

								$
										.ajax({
											async : false,
											type : "GET",
											url : "${pageContext.request.contextPath}/transactionweb/Addcountbycheckbox",
											data : {
												"count" : count

											},
											success : function(result) {
											}
										});

							}

						})

		//Withdraw_Count Function

		$("#button-ctrl")
				.click(
						function() {

							var count = 1;

							$
									.ajax({
										async : false,
										type : "GET",
										url : "${pageContext.request.contextPath}/transactionweb/Addcountbywithdraw",
										data : {
											"count" : count

										},
										success : function(result) {
										}
									});
						})
	</script>

</body>



</html>
