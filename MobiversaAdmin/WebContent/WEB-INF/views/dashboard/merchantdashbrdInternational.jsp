<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@page import="com.mobiversa.payment.controller.HomeController"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<!--bootstarp new -->
<!--<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/resourcesNew1/dist/bootstrap/bootstrap.min.css">-->
<!--<script
			src="${pageContext.request.contextPath}/resourcesNew1/dist/bootstrap/bootstrap.min.js"></script>-->
<!--<script
			src="${pageContext.request.contextPath}/resourcesNew1/dist/bootstrap/jquery-3.2.1.slim.min.js"></script>-->
<!--<script
			src="${pageContext.request.contextPath}/resourcesNew1/dist/bootstrap/popper.min.js"></script>-->

<!--  <link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/resourcesNew/css/bootstrap.min.css"> -->
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/custom.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/fonts.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/font-awesome-master/css/font-awesome.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>


<style>

@import
	url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap')
	;


.container-fluid{
	font-family: "Poppins", sans-serif !important;
	background-color: #f6f6f6 !important;
}

.container {
	max-width: 1200px;
	margin: 0 auto;
	padding: 20px;
}

.text-center {
	text-align: center;
}

/* .mb-4 {
	margin-bottom: 1.5rem;
} */

.card {
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	background-color: white;
	padding: 12px;
	margin: 10px 14px;
	width : 100%;
}

.card-1 {
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	background-color: white;
	padding: 12px;
	margin: 10px;
}

.card-d {
	border-radius: 10px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
	background-color: white;
	padding: 25px;
	margin: 10px;
}

.card-title {
	display : flex;
	font-weight: 500 !important;
	color : #404040;
	font-size: 1.2rem !important;
	text-wrap: nowrap;
}
.card-title-1 {
	display : flex;
	font-weight: 600 !important;
	color : #404040;
	font-size: 1rem !important;
	text-wrap: nowrap;
}

.card-text {
	color: #656565;
	font-size: 0.9rem;
}
.card-text-1 {
	color: black;
	font-size: 0.8rem;
    font-weight: 600;
}

.btn {
	display: inline-block;
	padding: 10px 20px;
	text-decoration: none;
	color: white;
	background-color: #007bff;
	border-radius: 5px;
}

.btn-primary {
	background-color: #007bff;
}

.card-body {
	display: flex;
	flex-direction: column;
	gap: 15px;
	height: 100%;
}

.top-content {
	display: flex;
	flex-direction: row;
	align-items: center;
	justify-content: space-between;
	 
}

.bottom-content {
	margin-top: auto;
	font-size: 1.5em; /* Adjust size as needed */
	font-weight: bold;
}

.flex_content{
    margin-bottom: 10px;
    width: 80%;
    display: flex;
    justify-content: space-between;
    align-content: center;
}

.img-fluid {
	max-width: 100%;
	height: auto;
	width: 40px;
}

.amount {
	display: flex;
	font-size: 1.4rem;
	color: #404040;
	font-weight : 600;
	margin: 2px;
}

.row {
	display: flex;
	flex-direction: row;
	justify-content: space-between;
	padding: 0px 0px;
	width: 100%;
}

.col-10 {
	width: 75%;
}

.col-2 {
	width: 25%;
	display: flex;
}
.col-md-4{
	display: flex;
	width : 100%;
}
.blue-btn {
    background-color: #005baa !important;
    border-radius: 50px !important;
    font-size: 0.9rem !important;
    display: flex;
    padding: 0px 0px !important;
    width: 100%;
    text-align: center;
    align-items: center;
    justify-content: center;
}

.api-popup-class {
	display: none;
	position: fixed;
	z-index: 1;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: auto;
	background-color: rgba(0, 0, 0, 0.4);
}

.api-content-class {
	background-color: #fefefe;
	margin: 15% auto;
	border: 1px solid #888;
	max-width: 33%;
	height: auto;
	border-radius: 11px;
}

.email-close-class {
	background-color: #005baa;
	color: white;
	border-radius: 25px;
	border: none;
	padding: 10px 27px;
	font-size: 12px;
	height: 35px;
	outline: none;
	cursor: pointer;
}

.email-submit-class {
	background-color: #005baa;
	color: white;
	border-radius: 25px;
	border: none;
	padding: 10px 27px;
	font-size: 12px;
	height: 35px;
	outline: none;
	cursor: pointer;
}

.email-submit-class:focus,
	.email-submit-class:active {
		background-color: #005baa; 
	}
}
.last-content{
	display: flex;
    flex-direction: column;
    gap: 27px;
}
.mobi-modal {
	position: fixed;
	top: 25%;
	left: 0;
	z-index: 1060;
	width: 100%;
	height: 100%;
	overflow-x: hidden;
	overflow-y: auto;
	outline: 0;
}

.mobi-modal-dialog {
	max-width: 500px;
	margin: 1.75rem auto;
}

.mobi-modal-content {
	position: relative;
	display: flex;
	flex-direction: column;
	width: 100%;
	pointer-events: auto;
	background-color: #fff;
	background-clip: padding-box;
	border: 1px solid rgba(0, 0, 0, .2);
	border-radius: 14px;
	outline: 0;
}

.mobi-popup-small {
	font-size: 13px;
	color: #808080;
}

.mobi-modal-header {
	display: flex;
	flex-shrink: 0;
	align-items: center;
	justify-content: space-between;
	padding: 1rem 1rem;
	/*	border-bottom: 1px solid #dee2e6; */
	border-top-left-radius: calc(0.3rem - 1px);
	border-top-right-radius: calc(0.3rem - 1px);
	font-size: 22px !important;
}
.mobi-modal-footer {
	display: flex;
	flex-wrap: wrap;
	flex-shrink: 0;
	align-items: center;
	justify-content: flex-end;
	padding: 0.75rem;
	/*	border-top: 1px solid #dee2e6; */
	border-bottom-right-radius: calc(0.3rem - 1px);
	border-bottom-left-radius: calc(0.3rem - 1px);
}

.mobi-modal-body {
	position: relative;
	flex: 1 1 auto;
	padding: 1rem;
	font-size: 20px !important;
}
.mobi-btxtb {
    color: #005baa;
    font-weight: bold;
}
#overlay-popup1 {
	position: fixed;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: #000;
	opacity: 0.5;
	z-index: 99;
	cursor: pointer;
}

 
 @media (max-width: 900px) {
     .row {
       	 display: flex;
       	 flex-direction : column;
       	 align-items: center;
            }
      .col-10, .col-2{
      width: 75%;
      }
     .api-content-class{
         max-width: 80%;
     }
       .card {
          width: 95%;
           margin-bottom: 0;
        }
        .card-1{
        width: 93%;
        }
        .mobi-modal-dialog{
        	max-width: 440px;
        }
  }

</style>

</head>

<body class="body-mobi">
	<div id="overlay" >
		<div id="overlay_text">
			<img class="img-fluid"
				src="${pageContext.request.contextPath}/resourcesNew/img/loader.gif">
		</div>
	</div>

	<!-- heading -->
	<div class="container-fluid">
		<div class="row-heading"  style="padding:15px 20px;">
		<div class="col-12">
			<div class="card-d shadow-sm radius-10">
				<div class="card-content">
					<h3 class="font-size-md" style="color: #005BAA; font-weight: 600;margin: 0px;">Dashboard
						- Upcoming settlements</h3>
				</div>
			</div>
		</div>
	</div>

	<div class="row"  style="padding:0px 20px;">
		<div class="col-10" >
			<div class="cards6">
				<!-- Cards in 3x3 grid -->
				<div class="row text-center">
					<!-- 1st card -->
					<div class="col-md-4">
						<div class="card">
							<div class="card-body">
								<div class="top-content">
									<div>
										<h5 class="card-title">Tommorrow</h5>
										<p class="card-text">Date: ${Fonedate}</p>
									</div>
									<div>
										<img class="img-fluid"
											src="${pageContext.request.contextPath}/resourcesNew1/assets/Tue.svg">
									</div>
								</div>
								<div class="bottom-content">
									<p class="card-text amount">RM
										${TotalsettledateoneNetAmount}</p>
								</div>
							</div>
						</div>
					</div>
					<!-- 2nd card -->
					<div class="col-md-4 ">
						<div class="card">
							<div class="card-body">
								<div class="top-content">
									<div>
										<h5 class="card-title">${settletwoweekNo}</h5>
										<p class="card-text">Date: ${Ftwodate}</p>
									</div>
									<div>
										<img class="img-fluid"
											src="${pageContext.request.contextPath}/resourcesNew1/assets/Wed.svg">
									</div>
								</div>
								<div class="bottom-content">
									<p class="card-text amount">RM
										${TotalsettledatetwoNetAmount}</p>
								</div>
							</div>
						</div>
					</div>
					<!-- 3rd card -->
					<div class="col-md-4">
						<div class="card">
							<div class="card-body">
								<div class="top-content">
									<div>
										<h5 class="card-title">${settlethreeweekNo}</h5>
										<p class="card-text">Date: ${Fthreedate}</p>
									</div>
									<div>
										<img class="img-fluid"
											src="${pageContext.request.contextPath}/resourcesNew1/assets/thu.svg">
									</div>
								</div>
								<div class="bottom-content">
									<p class="card-text amount">RM
										${TotalsettledatethreeNetAmount}</p>
								</div>
							</div>
						</div>
					</div>
				</div>

				<div class="row text-center">
					<!-- 4th card -->
					<div class="col-md-4">
						<div class="card">
							<div class="card-body">
								<div class="top-content">
									<div>
										<h5 class="card-title">${settlefourweekNo}</h5>
										<p class="card-text">Date: ${Ffourdate}</p>
									</div>
									<div>
										<img class="img-fluid"
											src="${pageContext.request.contextPath}/resourcesNew1/assets/fri.svg">
									</div>
								</div>
								<div class="bottom-content">
									<p class="card-text amount">RM
										${TotalsettledatefourNetAmount}</p>
								</div>
							</div>
						</div>
					</div>
					<!-- 5th card -->
					<div class="col-md-4">
						<div class="card">
							<div class="card-body">
								<div class="top-content">
									<div>
										<h5 class="card-title">${settlefiveweekNo}</h5>
										<p class="card-text">Date: ${Ffivedate}</p>
									</div>
									<div>
										<img class="img-fluid"
											src="${pageContext.request.contextPath}/resourcesNew1/assets/Mon.svg">
									</div>
								</div>
								<div class="bottom-content">
									<p class="card-text amount">RM
										${TotalsettledatefiveNetAmount}</p> 
										
								</div>
							</div>
						</div>
					</div>
					<!-- 6th card -->
					<div class="col-md-4">
						<div class="card">
							<div class="card-body">
								<div class="top-content">
									<div>
										<h5 class="card-title">Total</h5>
										<p class="card-text" style="font-size: 0.8rem;">Settlements
											Amount</p>
									</div>
									<div>
										<img class="img-fluid"
											src="${pageContext.request.contextPath}/resourcesNew1/assets/totalAmnt.svg">
									</div>
								</div>
								<div class="bottom-content">
									<p class="card-text amount">RM ${TotalSettlementAmount}</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-2" >
			<div class="apiPopup" style="width:100%">
			   <div class="text-center">
		        <div class="col-md-8 col-sm-12 mb-4 offset-md-2">
		            <div class="card-1">
		                <div class="card-body">
		                    <div class="last-content" style="display: flex;flex-direction: column;flex-wrap: wrap;">
		                     <div>
		                     	 <div style= "display: flex; justify-content: flex-start;margin	: 10px;">
                                <img class="img-fluid" src="${pageContext.request.contextPath}/resourcesNew1/assets/Profile2.svg">
                              </div>
                                <c:forEach items="${paginationBean2.itemList}" var="dtoo" varStatus="count">
                               <div>
		                        <h5 class="card-title-1" style ="margin-bottom:20px;">${dtoo.mobileUserName}</h5> 
		                       </div> 
		                     </div>
		                   
							    <div style="display: flex;flex-direction: column;align-items: flex-start;justify-content: center;">
							        <div class="flex_content">
							            <span class="card-text-1">TID </span> 
							            <span>: ${dtoo.tid}</span>
							        </div>
							       <div class="flex_content" style="width: 90%;">
							            <span class="card-text-1">Expiry date </span> 
							            <span>: ${dtoo.expiryDate}</span>
							        </div>
							          <div class="flex_content">
							            <span class="card-text-1">Ref No. </span> 
							            <span>: ${dtoo.refno}</span>
							        </div> 
							    </div>
							</c:forEach>

		                     	<div style="margin-top: 14px;">
		                     		 <button class="btn waves-effect waves-light blue-btn"  id="view-api-key-btn" type="submit">View API Key</button>
		                     	</div>
		                    </div>
		                </div>
		            </div>
		        </div>
			</div>
		</div>
		</div>
	</div>
	
	<!-- api key pop -->
		<div id="api-popup-id" class="api-popup-class">

		<div class="api-content-class">


			<div style="text-align: center;display : inline-block; border-bottom: 1.5px solid #ffa500; width: 100%; vertical-align: middle;
				padding: 2%;">
				<!-- <p style="margin-bottom: 0px; font-size: 13px;">Note :</p> -->
				<p style="font-size: 16px; font-weight: 500 !important; margin-bottom: 1px; color:#005baa; margin-bottom:1px;">API Key</p>

			</div>


			<div style="width: 100%; display: flex; flex-direction: column; justify-content: center; align-items: center; gap: 8px;">

				<div style = "display: flex; align-items: center;justify-content: center; flex-direction: column; padding:5px 0px; width:80%; gap:5px;">

					<img src="${pageContext.request.contextPath}/resourcesNew1/assets/key-fill.svg"
						 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; width="45px !important;">
					</div>
				<div>
					 <c:forEach items="${paginationBean2.itemList}" var="dtoo"
									varStatus="count">
						
								 <p id="api-key">${dtoo.motoapikey}</p>

								</c:forEach>
				</div>

			</div>

			<div style="display: flex; flex-direction: row; gap:10px; width:100%; align-items:center;padding: 2%; justify-content:center;background-color: #b1dcfb30;
				    border-bottom-right-radius: 9px; border-bottom-left-radius: 9px;">
				<button type="button" id="copy-btn" class="email-submit-class" onclick="copyToClipboard()"
						style="background: transparent;border: 2px solid #005baa; color: #005baa; ">Copy</button>
				<button type="button" id="email-submit-btn-submit" class="email-submit-class" onclick="closeNotify()">Close</button>
			</div>
		</div>

	</div>
	
	
		<!--  New Product Pop   -->
			<div id="overlay-popup1"></div>
			<div class="mobi-modal" id="mobi_modal_featurepopup"
				onclick="closefeature()" tabindex="-1">
				<div class="mobi-modal-dialog">
					<div class="mobi-modal-content">
						<div class="mobi-modal-header">
							<h5 class="mobi-modal-title mobi-text-dark"></h5>

						</div>

						<div class="mobi-modal-body mobi-text-sucess">
							<div class="mobi-text-dark"
								style="display: flex; flex-direction: row; align-items: center">
								<span class="mobi-btxtb">New product: Try out EZYSETTLE,
									to get your funds faster <span><img
										style="height: 30px; width: 30px"
										class="img-fluid mobi-sucess"
										src="${pageContext.request.contextPath}/resourcesNew1/assets/smile.jpg"></span>
								</span>
							</div>
						</div>
						<div class="mobi-modal-footer">
							<button type="button" id="mobi_popup_btn_close"
								class="mobi-popup-btn mobi-popup-btn-secondary"
								onclick="closefeature()">Close</button>
						</div>
					</div>
				</div>
			</div>
	</div>
	
	  <script>
        document.getElementById("view-api-key-btn").addEventListener("click", function() {
            document.getElementById("api-popup-id").style.display = "block";
            document.getElementById("modal-overlay").style.display = "block";
        });

        function closeNotify() {
            document.getElementById("api-popup-id").style.display = "none";
            document.getElementById("modal-overlay").style.display = "none";
        }
		
        window.onload = function() {
			$("#mobi_modal_featurepopup").show();
			$("#overlay-popup1").show();
		}
        function closefeature() {
			document.getElementById('mobi_modal_featurepopup').style.display = "none";
			document.getElementById('overlay-popup1').style.display = "none";
		}
        
        
        function copyToClipboard() {
            var apiKeyElement = document.getElementById('api-key');

            var apiKey = apiKeyElement.textContent;
            var tempInput = document.createElement('input');
            tempInput.value = apiKey;
            document.body.appendChild(tempInput);

            tempInput.select();
            document.execCommand('copy');

            document.body.removeChild(tempInput);

            var copyButton = document.getElementById('copy-btn');
            copyButton.textContent = 'Copied';

            setTimeout(function() {
                copyButton.textContent = 'Copy';
            }, 3000);
        }
    </script>
</body>


</html>