<%@page
	import="com.mobiversa.payment.controller.MerchantWebUMTransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" import="java.util.*"%>
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
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/custom.css">
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>



<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,300;0,400;1,400&display=swap"
	rel="stylesheet">


<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">



<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/awesomplete/1.1.2/awesomplete.min.css" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/awesomplete/1.1.2/awesomplete.min.js"></script>

<script>
	var banks;
    document.addEventListener("DOMContentLoaded", function() {
        banks = [
            { name: "Affin Bank Berhad B2B", code: "ABB0235" },
            { name: "Alliance Bank Malaysia Berhad", code: "ABMB0213" },
            { name: "AmBank Malaysia Berhad", code: "AMBB0208" },
            { name: "Bank Islam Malaysia Berhad", code: "BIMB0340" },
            { name: "Bank Kerjasama Rakyat Malaysia Berhad", code: "BKRM0602" },
            { name: "Bank Muamalat Malaysia Berhad", code: "BMMB0342" },
            { name: "CIMB Bank Berhad", code: "BCBB0235" },
            { name: "CITIBANK BHD", code: "CIT0218" },
            { name: "Deutsche Bank Berhad", code: "DBB0199" },
            { name: "Hong Leong Bank Berhad", code: "HLB0224" },
            { name: "HSBC Bank Malaysia Berhad", code: "HSBC0223" },
            { name: "Kuwait Finance House (Malaysia) Berhad", code: "KFH0346" },
            { name: "Malayan Banking Berhad (M2E)", code: "MBB0228" },
            { name: "OCBC Bank Malaysia Berhad", code: "OCBC0229" },
            { name: "Public Bank Berhad", code: "PBB0233" },
            { name: "Public Bank Enterprise", code: "PBB0234" },
            { name: "RHB Bank Berhad", code: "RHB0218" },
            { name: "Standard Chartered Bank", code: "SCB0215" },
            { name: "United Overseas Bank B2B Regional", code: "UOB0228" },
            { name: "BNP Parbias Malaysia Berhad", code: "BNP003" },
            { name: "Bank Pertanian Malaysia Berhad(Agrobank)", code: "AGRO02" }
            /* { name: "SBI Bank A", code: "TEST0021" } */
        ];

        var input = document.getElementById("bankName");
        var bankCodeInput = document.getElementById("bankCode"); // Add this line

        new Awesomplete(input, {
            list: banks.map(function(bank) {
                return bank.name;
            }),
            minChars: 1,
            replace: function(suggestion) {
                var selectedBank = banks.find(function(bank) {
                    return bank.name === suggestion.value;
                });

                if (selectedBank) {
                    // Replace the input value with the selected bank name
                    input.value = selectedBank.name;

                    // Set the selected bank code to the hidden input field
                    bankCodeInput.value = selectedBank.code;
                }
            }
        });
    });
</script>



<style>
.awesomplete {
	display: block;
	position: relative;
}

.awesomplete>ul:before {
	display: none;
}

.awesomplete>ul {
	border-radius: .3em;
	margin: .2em 0 0;
	background: #fff;
	border: 1px solid rgba(0, 0, 0, .3);
	box-shadow: 0.05em 0.2em 0.6em rgba(0, 0, 0, .2);
	text-shadow: none;
	max-height: 90px;
	overflow-y: scroll;
}

/* Style each individual item in the dropdown */
.awesomplete li {
	padding: 8px;
	cursor: pointer;
	border-bottom: 1px solid #eee;
}

/* Style the selected item in the dropdown without highlighting */
.awesomplete li:hover, .awesomplete li:focus {
	background-color: #005baa;
	color: #333; /* Set the text color to override highlighting */
}

.awesomplete ul li[aria-selected="false"] mark {
	background-color: transparent !important;
	padding: 0;
}

.awesomplete ul li[aria-selected="true"] mark {
	background-color: transparent !important;
	padding: 0;
}

.awesomplete>ul>li[aria-selected=true] {
	background: #005baa;
	color: #fff;
}

.awesomplete>ul::-webkit-scrollbar {
	display: none;
}

/* Hide scrollbar for IE, Edge and Firefox */
.awesomplete>ul {
	-ms-overflow-style: none; /* IE and Edge */
	scrollbar-width: none; /* Firefox */
}

@media ( max-width : 576px) {
	#addDepositAmountPopup {
		width: 360px !important;
		height: 320px !important;
	}
	#depositmodalcontent {
		width: 360px !important;
		height: 320px !important;
	}
	#pop-headofdepositamount, #labelofdepositamount, #enteredAmount,
		#labelofselectbank, #bankName {
		font-size: 13px !important;
	}
	#closefordepositamount {
		font-size: 13px !important;
		height: 32px !important;
	}
	#depositamountbtn {
		font-size: 13px !important;
		height: 32px !important;
	}
	.awesomplete li {
		padding: 8px;
		cursor: pointer;
		border-bottom: 1px solid #eee;
		font-size: 13px !important;
	}
}

@media ( max-width : 380px) {
	#addDepositAmountPopup {
		width: 270px !important;
		height: 320px !important;
	}
	#depositmodalcontent {
		width: 270px !important;
		height: 320px !important;
	}
	#pop-headofdepositamount, #labelofdepositamount, #enteredAmount,
		#labelofselectbank, #bankName {
		font-size: 11px !important;
	}
	#closefordepositamount {
		font-size: 11px !important;
		height: 28px !important;
	}
	#depositamountbtn {
		font-size: 11px !important;
		height: 28px !important;
	}
	.awesomplete li {
		padding: 8px;
		cursor: pointer;
		border-bottom: 1px solid #eee;
		font-size: 11px !important;
	}
}
</style>

<style>
.pop-body {
	border-radius: 25px;
}

.mb-0 {
	padding-bottom: 0px !important;
}

#enteredAmount:not (.browser-default ) {
	border-bottom: 1.5px solid #ffa500 !important;
	color: rgb(158, 158, 158);
}

#enteredAmount::placeholder {
	color: rgb(158, 158, 158);
}

#bankName::placeholder {
	color: rgb(158, 158, 158);
}

input[type=text]:not (.browser-default ) {
	border-bottom: 1.5px solid #ffa500 !important;
	color: rgb(158, 158, 158);
}
</style>


<style>
.container ss {
	max-width: 100%;
	height: auto;
}

a {
	text-decoration: none !important;
	text-decoration: none;
	font-weight: 470 !important;
}

@media ( min-width : 767px) {
	#main-wrapper[data-layout="vertical"][data-sidebartype="mini-sidebar"] .page-wrapper
		{
		margin-left: 50px !important;
		margin-right: -15px;
	}
}

#merchantName:hover {
	color: 275ca8;
}

#agentName:hover {
	color: 275ca8;
}

.example_e1:focus {
	outline: none !important;
}

.table-border-bottom tr {
	border-bottom: 1px solid rgba(0, 0, 0, 0.12);
}

.key_hover:hover {
	cursor: pointer;
}

.example_e1 {
	display: inline-block;
	margin-bottom: 0;
	font-weight: 600;
	text-align: left;
	vertical-align: middle;
	-ms-touch-action: manipulation;
	touch-action: manipulation;
	cursor: pointer;
	background-image: none;
	border: 0;
	color: rgb(39, 92, 168);
	letter-spacing: 1px;
	text-transform: uppercase;
	padding: 10px 15px;
	font-size: 13px;
	line-height: 1.428571429;
	border-radius: 25px;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
	transition: box-shadow 0.3s cubic-bezier(0.35, 0, 0.25, 1), transform
		0.2s cubic-bezier(0.35, 0, 0.25, 1), background-color 0.3s ease-in-out;
	font-style: Arial, Helvetica, sans-serif;
	border-radius: 15px;
}

.example_e1:hover {
	color: rgb(39, 92, 168);
	font-weight: 600 !important;
	-webkit-box-shadow: 0px 5px 40px -10px rgba(0, 0, 0, 0.57);
	-moz-box-shadow: 0px 5px 40px -10px rgba(0, 0, 0, 0.57);
	transition: all 0.3s ease 0s;
	border: 2px solid #cfcfd1;
	outline: 0 !important;
}

.hide_key {
	display: none;
}
/* Customize the input box */
input[type=number], input[type=decimal] {
	padding: 10px;
	border: none;
	border-bottom: 2px solid #4CAF50;
	font-size: 18px;
	margin-bottom: 20px;
	text-align: center;
	outline: none;
	width: 4rem;
	background-color: #f1f1f1;
	color: #011;
	height: 3rem;
	font-family: 'Poppins', sans-serif;
}

input::placeholder {
	color: #666666;
}

input:focus::placeholder {
	opacity: 0;
}
/* Style the button */
button {
	padding: 10px 20px;
	border: none;
	border-radius: 10px;
	background-color: #3A6EA5;
	color: white;
	font-size: 14px;
	cursor: pointer;
	transition: background-color 0.3s ease;
	font-family: 'Poppins', sans-serif;
	margin: 10px 30%;
}
/* Change button color on hover */
button:hover {
	background-color: #326195;
}
/* .center-div {
      margin-left: 33.3% !important;
      margin-top: 3.5% !important;
      } */
.temp-css {
	margin-left: 33%;
	width: 33%;
}

.submitBtn {
	padding: 8px 20px;
	border-radius: 10px;
	background-color: #54b74a;
	color: #fff;
	margin: auto;
	display: table;
}

.cbtn {
	Â outline: none;
	Â border: 2px solid #005baa;
	Â border-radius: 30px;
	Â padding: 5px 10px;
	Â margin-left: 160px;
	Â color: white;
	Â background-color: #005baa;
	Â margin-top: 20px;
	Â width: 160px;
	Â height: 48px;
}

.test {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 10;
	background-color: rgba(0, 0, 0, 0.5);
}

.modal {
	display: none;
	position: fixed;
	left: 0;
	right: 0;
	background-color: #fafafa;
	padding: 0;
	max-height: 70%;
	width: 55%;
	margin: auto;
	overflow-y: auto;
	border-radius: 2px;
	will-change: top, opacity;
}

#exampleModalCenter {
	z-index: 100;
	width: 25%;
	font-size: 24px;
	border-radius: 20px;
	font-weight: 400;
	font-family: 'Poppins', sans-serif;
	text-align: center;
}

#addDepositAmountPopup {
	z-index: 100;
	width: 25%;
	font-weight: 400;
	font-family: 'Poppins', sans-serif;
	text-align: center;
}

.depositscroll::-webkit-scrollbar {
	display: none;
}

/* Hide scrollbar for IE, Edge and Firefox */
.depositscroll {
	-ms-overflow-style: none; /* IE and Edge */
	scrollbar-width: none; /* Firefox */
}

#para {
	color: #000;
}

.test {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	z-index: 10;
	background-color: rgba(0, 0, 0, 0.5);
}

#close {
	color: #fff;
	background-color: #005baa;
}

.close1 {
	color: #fff;
	background-color: #005baa;
	outline: none;
}
</style>
<script type="text/javascript">






      history.pushState(null, null, "");
      window.addEventListener('popstate', function() {
      	history.pushState(null, null, "");
      
      });
   </script>
<style>
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

.card-content strong {
	color: #005baa;
}

.card-content1 strong {
	color: #005baa;
	font-size: 22px;
	white-space: nowrap;
}

.hello {
	color: black;
	text-align: center;
	width: 30%;
	padding-top: 10px;
	padding-bottom: 10px;
	white-space: nowrap;
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

.mobi-modal-body {
	position: relative;
	flex: 1 1 auto;
	padding: 1rem;
	font-size: 20px !important;
}

.fz-22 {
	font-size: 22px
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

.mobi-modal-title {
	margin-bottom: 0;
	line-height: 1.5;
}

.mobi-popup-btn-secondary {
	color: #fff !important;
	background-color: #A1A0A2 !important;
	border-color: #A1A0A2 !important;
}

.mobi-text-dark {
	padding: 14px;
	color: #212529;
}

.mobi-text-dark1 {
	color: #212529;
}

.c969696 {
	color: #080808;;
	font-size: 1.2rem !important;
}

.mobi-popup-btn {
	display: inline-block;
	font-weight: 400;
	line-height: 1.5;
	color: #212529;
	text-align: center;
	text-decoration: none;
	vertical-align: middle;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
	background-color: transparent;
	border: 1px solid transparent;
	padding: 0.375rem 0.75rem;
	font-size: 1rem;
	border-radius: 0.25rem;
	transition: color .15s ease-in-out, background-color .15s ease-in-out,
		border-color .15s ease-in-out, box-shadow .15s ease-in-out;
}

#mobi_modal_popup {
	display: none;
}

#overlay-popup {
	position: fixed;
	display: none;
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

#overlay_gifloader {
	position: fixed;
	display: none;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background: #000;
	opacity: 0.5;
	z-index: 100;
	cursor: pointer;
}

.mobi-text-right {
	text-align: right;
}

.date_circle {
	width: 80px;
	height: 80px;
	border-radius: 40px;
	color: #fff;
	font-weight: 600;
	display: flex;
	align-items: center !important;
	justify-content: center !important;
	font-size: 20px;
}

.gradient864cf5 {
	background: rgb(134, 76, 245);
	background: linear-gradient(156deg, rgba(134, 76, 245, 1) 39%,
		rgba(161, 115, 250, 1) 75%, rgba(159, 119, 238, 1) 100%);
}

.gradientf551c1 {
	background: rgb(245, 81, 193);
	background: linear-gradient(156deg, rgba(245, 81, 193, 1) 39%,
		rgba(251, 135, 215, 1) 75%, rgba(255, 161, 237, 1) 100%);
}

.gradient5b45f8 {
	background: rgb(91, 69, 248);
	background: linear-gradient(156deg, rgba(91, 69, 248, 1) 39%,
		rgba(119, 100, 252, 1) 75%, rgba(119, 100, 252, 1) 100%);
}

.gradient11ac4d {
	background: rgb(17, 172, 77);
	background: linear-gradient(156deg, rgba(17, 172, 77, 1) 39%,
		rgba(55, 200, 106, 1) 75%, rgba(67, 215, 116, 1) 100%);
}

.gradientfc496e {
	background: rgb(252, 73, 110);
	background: linear-gradient(156deg, rgba(252, 73, 110, 1) 39%,
		rgba(254, 73, 111, 1) 75%, rgba(254, 122, 149, 1) 100%);
}

.mobi-mr-10 {
	/*	margin-right: 10px; */
	margin-right: 43px;
}
</style>
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

.mobi-text-right {
	text-align: right;
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
<style>
td, th {
	padding: 7px 8px;
	color: #707070;
}

.table-border-bottom td {
	border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important;
}

thead th {
	border-bottom: 1px solid #ffa500;
	color: #4377a2;
}

img.refund_icon_6 {
	width: 40px;
	height: 40px;
	margin: 10px 9px 0 10px;
	object-fit: contain;
}

.w24 {
	width: 24px;
}
/* new changes */
.card-content1 strong {
	color: #2D2D2D;
	font-size: 22px;
	/* margin-right: 100px; */
	white-space: nowrap;
}

.card .padding-card {
	padding: 24px 130px;
}

.padding-card1 {
	padding: 24px 30px;
}

.card-content2 strong {
	color: #2D2D2D;
	font-size: 22px;
	/* margin-right: 100px; */
	white-space: nowrap;
}

.hello {
	color: #72777B;
	text-align: center;
	font-size: 24px;
	width: 30%;
	padding-top: 10px;
	padding-bottom: 10px;
	white-space: nowrap;
}

tr {
	border-bottom: none !important;
}

.custom-class {
	max-width: 1128px; /* Set a maximum width for the element */
	margin: 0 auto; /* Center the element horizontally */
}

.row-c {
	display: flex;
	justify-content: center;
	align-items: center;
}

.col-md-4 {
	flex: 1;
}

/* new changes */
.btn-block {
	display: block;
	width: 81%;
}

.btn.btn-primary {
	border-radius: 50px !important;
}

.navbar-nav .nav-link:hover {
	text-decoration: none;
}
</style>





<script type="text/javascript">


      function closepopup(){
             document.getElementById("exampleModalCenter").style.display ="none";
             document.getElementById("pop-bg-color").style.display ="none";
             // document.getElementById("pop-bg-color").style.display ="none";
             }
      jQuery(document).ready(function() {
      
      
      //$('#brand').select2();
      //$('#mid1').select2(); 
      //$('#merchantName').select2();
      });
   </script>
<script lang="JavaScript">


function submitDeposit() {
	var withdrawType = 'DepositAdjustment';
	var overDraftFee = null;
	var amountInput = document.getElementById("Depositamount");
	var amount = amountInput.value.replace(/[^\d.]/g, '');

	var merchantId = document.getElementById("merchantId").value;
	
	// Disable the button to prevent multiple clicks
    document.getElementById("depositbtn").disabled = true;
	
	document.location.href = '${pageContext.request.contextPath}/transactionUmweb/UpdatewithDraw?finalwithdrawamount=' + amount +
			'&merchantId=' + merchantId +
			'&withdrawType=' + withdrawType +
			'&overDraftFee=' + overDraftFee;
}

//saravana
function refilOverDraft(){
	  var overdraftAmount1=document.getElementById("overdraftAmount").innerText;
	  var depositAmount=document.getElementById("depositAmount").innerText;
	 /*  alert(overdraftAmount1);
	  alert(depositAmount); */
	  var merchantId = document.getElementById("merchantId").value;
	  
	  document.location.href = '${pageContext.request.contextPath}/transactionUmweb/refill?' +
		    'overdraftAmount1=' + encodeURIComponent(overdraftAmount1) +
		    '&depositAmount=' + encodeURIComponent(depositAmount) +
		    '&merchantId=' + encodeURIComponent(merchantId);;
		    window.location.href = url;
	  form.submit;
	  
}

//saravana
function refildeposit(){
	
	 
	  var depositAmount=document.getElementById("depositAmount").innerText;
	 /*  alert(overdraftAmount1);
	  alert(depositAmount); */
	  var merchantId = document.getElementById("merchantId").value;
	 
	  document.location.href = '${pageContext.request.contextPath}/transactionUmweb/refill1?' +
		    '&merchantId=' + encodeURIComponent(merchantId);;
		    window.location.href = url;
	  form.submit;
	  
}










      function withdraw() {
      	var amount = document.getElementById("amount").value;
      	var merchantId = document.getElementById("merchantId").value;

      	//alert(merchantId);
      	if(amount!=""){
      	$("#overlay").show();
      	
     // debugger;
      	
      		document.location.href = '${pageContext.request.contextPath}/transactionUmweb/UpdatewithDraw?amount='
      			+amount+'&merchantId='+merchantId;
      	}
      	// Do whatever you want with the amount here
      	//alert("Withdraw amount: " + amount);
      	
      	
      	
      form.submit;
      }
      
      
      
     
      
      $(document).ready(function() {
      	//alert(${id});
      	// $('#data_list_table').DataTable();
      });
      function closedata() {
      	
      	var currPage = "1";
      
      	
      	// Do whatever you want with the amount here
      	//alert("Withdraw amount: " + amount);
      	
      	
      	document.location.href = '${pageContext.request.contextPath}/transactionUmweb/payoutbalance?currPage='
      		+currPage;
      form.submit;
      }
      
      
      	function loadSelectData() {
      		//alert("test"+document.getElementById("txnType").value);
      		var e = document.getElementById("from").value;
      		var e1 = document.getElementById("to").value;
      
      		var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
      		var toDate = new Date(e1);//.toDateString("yyyy-MM-dd");	
      
      		var fromday = fromDate.getDate();
      		var frommon = fromDate.getMonth() + 1;
      		var fromyear = fromDate.getFullYear();
      
      		var today = toDate.getDate();
      		var tomon = toDate.getMonth() + 1;
      		var toyear = toDate.getFullYear();
      
      		var fromdateString = fromyear + '-'
      				+ (frommon <= 9 ? '0' + frommon : frommon) + '-'
      				+ (fromday <= 9 ? '0' + fromday : fromday);
      		var todateString = toyear + '-' + (tomon <= 9 ? '0' + tomon : tomon)
      				+ '-' + (today <= 9 ? '0' + today : today);
      
      		if (e == null || e == '' || e1 == null || e1 == '') {
      			alert("Please Select date(s)");
      
      		}
      
      		else {
      			document.getElementById("dateval1").value = fromdateString;
      			document.getElementById("dateval2").value = todateString;
      			/* document.getElementById("txnType").value = e2; */
      			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
      					+ e + '&date1=' + e1 + '&txnType=' + e2; */
      
      			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchPayout?date='
      					+ fromdateString + '&date1=' + todateString;
      
      			form.submit;
      
      		}
      	}
      	function loadExpData() {
      		//alert("test"+document.getElementById("txnType").value);
      		var e = document.getElementById("from").value;
      		var e1 = document.getElementById("to").value;
      		var e2 = document.getElementById("export1").value;
      
      		var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
      		var toDate = new Date(e1);//.toDateString("yyyy-MM-dd");	
      
      		var fromday = fromDate.getDate();
      		var frommon = fromDate.getMonth() + 1;
      		var fromyear = fromDate.getFullYear();
      
      		var today = toDate.getDate();
      		var tomon = toDate.getMonth() + 1;
      		var toyear = toDate.getFullYear();
      
      		var fromdateString = fromyear + '-'
      				+ (frommon <= 9 ? '0' + frommon : frommon) + '-'
      				+ (fromday <= 9 ? '0' + fromday : fromday);
      		var todateString = toyear + '-' + (tomon <= 9 ? '0' + tomon : tomon)
      				+ '-' + (today <= 9 ? '0' + today : today);
      
      		if (e == null || e == '' || e1 == null || e1 == '') {
      			alert("Please Select date(s)");
      
      		} else if (e2 == null || e2 == '') {
      
      			alert("Please Select Export Type");
      
      		} else {
      
      			document.getElementById("datevalex1").value = fromdateString;
      			document.getElementById("datevalex2").value = todateString;
      
      			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/exportPayout?date='
      					+ fromdateString + '&date1=' + todateString+ '&export=' + e2;
      			form.submit;
      
      		}
      	}
      
      	function loadDropDate13() {
      
      		var e = document.getElementById("export");
      
      		var strUser = e.options[e.selectedIndex].value;
      		document.getElementById("export1").value = strUser;
      
      	}
      
      	function loadDate(inputtxt, outputtxt) {
      
      		var field = inputtxt.value;
      
      		outputtxt.value = field;
      	}
      	
      	function openDialog(value) {
			if (value === 'settlementadjust') {
				document.getElementById('Settlement').style.display = 'block';
			}
			if (value === 'depositadjust') {
				document.getElementById('Deposit').style.display = 'block';
			}
			if (value === 'overdraftadjust') {
				document.getElementById('over-draft').style.display = 'block';
			}
		}

      
      	
   </script>

<style>
.card {
	border-radius: 10px;
}
</style>


</head>


<body class="">
	<div id="overlay">
		<div id="overlay_text">
			<img class="img-fluid"
				src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
		</div>
	</div>
	<div class="test" id="pop-bg-color"></div>
	<div class="container-fluid withdrawcontainer mb-0" id="pop-bg">
		<div class="row mr-2 ml-2">
			<div class="col">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>PAYOUT Balance </strong>
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>



		<div class="modal fade bd-example-modal-xl pop-body"
			style="width: 500px !important; height: 270px !important;"
			id="exampleModalCenter" tabindex="-1" role="dialog"
			aria-labelledby="mySmallModalLabel" aria-hidden="true"
			style="text-align:center;">
			<div class="modal-dialog modal-xl">
				<div class="modal-content "
					style="padding: 0 !important; margin: 0 !important;">
					<p class="pop-head"
						style="background-color: #f9f9f9; width: 100%; height: 60px; color: #005baa; padding-top: 12px; border-bottom: 2px solid #ffa500;">Information</p>
					<img id='img1'
						src="${pageContext.request.contextPath}/resourcesNew1/assets/success1.png"
						width="60px !important; height:60px !important;"> <img
						id='img2'
						src="${pageContext.request.contextPath}/resourcesNew1/assets/error.png"
						width="60px !important; height:60px !important;">
					<p id='para' style="font-size: 22px;"></p>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal" id="close" onclick="closedata()"
							style="width: 106px !important; height: 38px !important; font-size: 18px; border-radius: 50px !important; margin-right: 187px !important; letter-spacing: 0.8px; font-family: 'Poppins', sans-serif; font-weight: medium; transform: translateY(-10px);">Close</button>
					</div>
				</div>
			</div>
		</div>

		<!-- add deposit amount popup start  -->




		<!-- add deposit amount popup end  -->

		<div class="row mr-2 ml-2">
			

			<div class="col-md-4">
				<div
					class="card card-mobi h-200 p-3 mobi-flex mobi-justify-content-center"
					style="border-radius: 0.9rem;">
					<div
						class="row-c mb-3 d-flex align-items-center justify-content-end">
						<div class="col-4 d-flex justify-content-end">
							<img class="img-fluid"
								src="${pageContext.request.contextPath}/resourcesNew1/assets/Balance.png">
						</div>
					</div>
					<div class="row-c">
						<div class="col-12">
							<div class="text-right">
								<strong class="display-8 text-right" style="color: #2D2D2D;">Available 
									Balance</strong>
								<div class="table-responsive depositscroll">
									<table class="table table-borderless">
										<tbody>
										
										
										<td class="text-right" id="balanceNetAmt"
														style="color: #72777B; font-size: 26px;">RM ${availableBalance}</td>
										
											<%-- <c:forEach items="${paginationBean.itemList}" var="dto"
												varStatus="loop">
												<tr class="dto-${loop.index}">
													<td class="text-right" id="balanceNetAmt"
														style="color: #72777B; font-size: 26px;">RM
														${dto.balanceNetAmt}</td>
												</tr>
											</c:forEach> --%>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

<!-- 
			<div class="col-md-4 col-lg-4">
			<div class="card card-mobi  p-3 "
				style="border-radius: 0.9rem;">
				<div class="col-12">
					<h1
						style="font-size: 22px; font-weight: bold; margin-bottom: 1.5rem !important">Adjustments</h1>
						
				</div>
				<div class="row-c">

					<div class=" col-md-12 col-lg-12 adjustment">
						<div>
							<label style="margin-bottom: 0px !important;">Adjustment
								Type</label> 
								<select name="languages" id="adjustment"
								placeholder="hello" onchange="openDialog(this.value)">
								<option value="">Choose Type</option>
								<option value="settlementadjust">Settlement Adjustment</option>
								<option value="depositadjust">Deposit Adjustment</option>
								<option value="overdraftadjust">Overdraft Adjustment</option>
							</select>

						</div>
					</div>
				</div>
<br><br><br><br>
			</div>
		</div>
			 -->
		</div>


	<!-- HTML for Settlement dialog -->
	<div id="Settlement" class="dialog">
		<div class="dialog-content">
			<span class="close" onclick="closeDialog()">&times;</span>
			<h2>Settlement Adjustment</h2>
			<label for="amount"></label> <input type="text"
				style="text-align: center" placeholder="Enter Amount"
				inputmode="decimal" id="amount" name="amount">
			<button id="settlementbtn" onclick="submitSettlement()">Submit</button>
		</div>
	</div>

	<!-- HTML for Deposit dialog -->
	<div id="Deposit" class="dialog">
		<div class="dialog-content">
			<span class="close" onclick="closeDialog()">&times;</span>
			<h2>Deposit Adjustment</h2>
			<label for="Depositamount"></label> <input type="text"
				style="text-align: center" inputmode="decimal"
				placeholder="Enter Amount" id="Depositamount" name="amount">
			<button id="depositbtn" onclick="submitDeposit()">Submit</button>
		</div>
	</div>

	<!-- HTML for over-draft dialog -->
	<div id="over-draft" class="dialog">
		<div class="dialog-content">
			<span class="close" onclick="closeDialog()">&times;</span>
			<h2>Over-draft Adjustment</h2>
			<label for="amount"></label> <input type="text" name="amount"
				style="text-align: center" placeholder="Enter Amount"
				inputmode="decimal" id="OverDraftAmount"> <label for="fee"></label>
			<input type="text" name="overDraftFee" inputmode="decimal"
				style="text-align: center" id="overDraftFee" placeholder="Enter Fee">

			<button id="OverDraftbtn" onclick="submitOverDraft()">Submit</button>
		</div>
	</div>


<script>
/* 
  console.log(jQuery.fn.jquery); */

		function openDialog(value) {
			if (value === 'settlementadjust') {
				document.getElementById('Settlement').style.display = 'block';
			}
			if (value === 'depositadjust') {
				document.getElementById('Deposit').style.display = 'block';
			}
			if (value === 'overdraftadjust') {
				document.getElementById('over-draft').style.display = 'block';
			}
		}

  function closeDialog() {
	    document.getElementById('Settlement').style.display = 'none';
	    document.getElementById('Deposit').style.display = 'none';
	    document.getElementById('over-draft').style.display = 'none';

	    // add this line
	    document.getElementById('adjustment').value = '';


	    document.getElementById('amount').value = '';
	    document.getElementById('Depositamount').value = '';
	    document.getElementById('OverDraftAmount').value = '';
	    document.getElementById('overDraftFee').value = '';
	}


		<%--function submitSettlement(){--%>
		<%--	var withdrawType = 'SettlementAdjustment';--%>
		<%--	var overDraftFee = null;--%>
		<%--	// var amount = document.getElementById("amount").value;--%>
		<%--	var amount = amountInput.value.replace(/[^\d.]/g, ''); // Extract numeric value--%>
		<%--	var merchantId = document.getElementById("merchantId").value;--%>
		<%--	document.location.href = '${pageContext.request.contextPath}/transactionUmweb/withDraw?finalwithdrawamount=' + amount +--%>
		<%--			'&merchantId=' + merchantId +--%>
		<%--			'&withdrawType=' + withdrawType +--%>
		<%--			'&overDraftFee=' + overDraftFee;--%>

		<%--}--%>






		document.addEventListener('DOMContentLoaded', function() {
			var withdrawAmountInput3 = document.getElementById('amount');
			var withdrawButton3 = document.getElementById('settlementbtn');

			if (withdrawAmountInput3 && withdrawButton3) {
				withdrawAmountInput3.addEventListener('input', function() {
					const inputValue = this.value;
					const withdrawAmountValue = inputValue.trim() === 'RM' || inputValue.trim() === '' ? '0.00' : inputValue.replace(/[^0-9]/g, '');
					const withdrawAmount = (parseFloat(withdrawAmountValue) / 100).toFixed(2);
					const withdrawamountwithcomma = withdrawAmount.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

					withdrawAmountInput3.value = "RM " + withdrawamountwithcomma;

					// Check if the numeric value is greater than or equal to 10
					const numericValue = parseFloat(withdrawAmountInput3.value.replace(/[^0-9.]/g, ''));
					const isValidAmount = numericValue >= 10;

					withdrawButton3.disabled = !withdrawAmountInput3.value.includes("RM ") || !isValidAmount;
					withdrawButton3.style.opacity = withdrawButton3.disabled ? '0.5' : '1';
				});
			} else {
				console.error("One or both elements not found.");
			}
		});


		<%--function submitSettlement(){--%>
		<%--	var withdrawType = 'SettlementAdjustment';--%>
		<%--	var overDraftFee = null;--%>
		<%--	// var amount = document.getElementById("amount").value;--%>
		<%--	var amount = amountInput.value.replace(/[^\d.]/g, ''); // Extract numeric value--%>
		<%--	var merchantId = document.getElementById("merchantId").value;--%>
		<%--	document.location.href = '${pageContext.request.contextPath}/transactionUmweb/withDraw?finalwithdrawamount=' + amount +--%>
		<%--			'&merchantId=' + merchantId +--%>
		<%--			'&withdrawType=' + withdrawType +--%>
		<%--			'&overDraftFee=' + overDraftFee;--%>

		<%--}--%>
		function submitSettlement() {
			var withdrawType = 'SettlementAdjustment';
			var overDraftFee = null;
			var amountInput = document.getElementById("amount");
			var amount = amountInput.value.replace(/[^\d.]/g, ''); // Extract numeric value
			var merchantId = document.getElementById("merchantId").value;
			
			// Disable the button to prevent multiple clicks
	        document.getElementById("settlementbtn").disabled = true;
						
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/UpdatewithDraw?finalwithdrawamount=' + amount +
					'&merchantId=' + merchantId +
					'&withdrawType=' + withdrawType +
					'&overDraftFee=' + overDraftFee;
		}


		document.addEventListener('DOMContentLoaded', function() {
			var withdrawAmountInput4 = document.getElementById('Depositamount');
			var withdrawButton4 = document.getElementById('depositbtn');

			if (withdrawAmountInput4 && withdrawButton4) {
				withdrawAmountInput4.addEventListener('input', function() {
					const inputValue = this.value;
					const withdrawAmountValue = inputValue.trim() === 'RM' || inputValue.trim() === '' ? '0.00' : inputValue.replace(/[^0-9]/g, '');
					const withdrawAmount = (parseFloat(withdrawAmountValue) / 100).toFixed(2);
					const withdrawamountwithcomma = withdrawAmount.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

					withdrawAmountInput4.value = "RM " + withdrawamountwithcomma;

					// Check if the numeric value is greater than or equal to 10
					const numericValue = parseFloat(withdrawAmountInput4.value.replace(/[^0-9.]/g, ''));
					const isValidAmount = numericValue >= 10;

					withdrawButton4.disabled = !withdrawAmountInput4.value.includes("RM ") || !isValidAmount;
					withdrawButton4.style.opacity = withdrawButton4.disabled ? '0.5' : '1';
				});
			} else {
				console.error("One or both elements not found.");
			}
		});

		function submitDeposit() {
			var withdrawType = 'DepositAdjustment';
			var overDraftFee = null;
			var amountInput = document.getElementById("Depositamount");
			var amount = amountInput.value.replace(/[^\d.]/g, '');

			var merchantId = document.getElementById("merchantId").value;
			
			// Disable the button to prevent multiple clicks
	        document.getElementById("depositbtn").disabled = true;
			
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/UpdatewithDraw?finalwithdrawamount=' + amount +
					'&merchantId=' + merchantId +
					'&withdrawType=' + withdrawType +
					'&overDraftFee=' + overDraftFee;
		}


		document.addEventListener('DOMContentLoaded', function() {
			var withdrawAmountInput5 = document.getElementById('OverDraftAmount');
			var withdrawAmountInput6 = document.getElementById('overDraftFee');
			var withdrawButton5 = document.getElementById('OverDraftbtn');
			
			withdrawButton5.disabled = !withdrawAmountInput5.value.includes("RM ") || !withdrawAmountInput6.value.includes("RM ") || !isValidAmount;
			withdrawButton5.style.opacity = withdrawButton5.disabled ? '0.5' : '1';
			

			if (withdrawAmountInput5 && withdrawButton5) {
				withdrawAmountInput5.addEventListener('input', function() {
					const inputValue = this.value;
					const withdrawAmountValue = inputValue.trim() === 'RM' || inputValue.trim() === '' ? '0.00' : inputValue.replace(/[^0-9]/g, '');
					const withdrawAmount = (parseFloat(withdrawAmountValue) / 100).toFixed(2);
					const withdrawamountwithcomma = withdrawAmount.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

					withdrawAmountInput5.value = "RM " + withdrawamountwithcomma;

					// Check if the numeric value is greater than or equal to 10
					const numericValue = parseFloat(withdrawAmountInput5.value.replace(/[^0-9.]/g, ''));
					const isValidAmount = numericValue >= 10;

					withdrawButton5.disabled = !withdrawAmountInput5.value.includes("RM ") || !withdrawAmountInput6.value.includes("RM ") || !isValidAmount;
					withdrawButton5.style.opacity = withdrawButton5.disabled ? '0.5' : '1';
				});
			} 
			
			if(withdrawAmountInput6 && withdrawButton5){
				withdrawAmountInput6.addEventListener('input', function() {
					const inputValue = this.value;
					const withdrawAmountValue = inputValue.trim() === 'RM' || inputValue.trim() === '' ? '0.00' : inputValue.replace(/[^0-9]/g, '');
					const withdrawAmount = (parseFloat(withdrawAmountValue) / 100).toFixed(2);
					const withdrawamountwithcomma = withdrawAmount.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

					withdrawAmountInput6.value = "RM " + withdrawamountwithcomma;

					// Check if the numeric value is greater than or equal to 10
					const numericValue = parseFloat(withdrawAmountInput6.value.replace(/[^0-9.]/g, ''));
					const isValidAmount = numericValue >= 10;

					withdrawButton5.disabled = !withdrawAmountInput6.value.includes("RM ")|| !withdrawAmountInput5.value.includes("RM ") || !isValidAmount;
					withdrawButton5.style.opacity = withdrawButton5.disabled ? '0.5' : '1';
				});
			}
			
			
			/* else {
				console.error("One or both elements not found.");
			} */
		});

		<%--function submitOverDraft(){--%>
		<%--	var withdrawType = 'OverDraftAdjustment';--%>
		<%--	var amount = document.getElementById("OverDraftAmount").value;--%>
		<%--	var overDraftFee = document.getElementById("overDraftFee").value;--%>
		<%--	var merchantId = document.getElementById("merchantId").value;--%>
		<%--	document.location.href = '${pageContext.request.contextPath}/transactionUmweb/withDraw?finalwithdrawamount=' + amount +--%>
		<%--			'&merchantId=' + merchantId +--%>
		<%--			'&withdrawType=' + withdrawType +--%>
		<%--			'&overDraftFee=' + overDraftFee;--%>
		<%--}--%>
		function submitOverDraft() {
			var withdrawType = 'OverDraftAdjustment';
			var amountInput = document.getElementById("OverDraftAmount");

			// Extract numeric value without currency symbol and commas
			var amount = amountInput.value.replace(/[^\d.]/g, '');

			var overDraftFee = document.getElementById("overDraftFee").value;
			var merchantId = document.getElementById("merchantId").value;
			
			// Disable the button to prevent multiple clicks
	        document.getElementById("OverDraftbtn").disabled = true;

			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/UpdatewithDraw?finalwithdrawamount=' + amount +
					'&merchantId=' + merchantId +
					'&withdrawType=' + withdrawType +
					'&overDraftFee=' + overDraftFee;
		}



	</script>


	<script>
	
	var proceedToWithdrawBtn = document.getElementById("proceedToWithdraw");
	proceedToWithdrawBtn.disabled = true;
	proceedToWithdrawBtn.style.opacity = proceedToWithdrawBtn.disabled ? '0.5' : '1';
		
	var withdrawAmountInput = document.getElementById('withdrawamount');
	var withdrawButton = document.getElementById('withdrawBtn');
	
	var oneLackInNumber = parseFloat("100000.00");
	
	
	withdrawButton.disabled = !withdrawAmountInput.value.includes("RM ") || parseFloat(withdrawAmountInput.value.replace(/[^0-9]/g, '')) <= 0;
	withdrawButton.style.opacity = withdrawButton.disabled ? '0.5' : '1';
	
	
/* 	function openWithdrawPopupModal() {  
		
	    var elems = document.querySelector('#withdrawModal');
	    var instances = M.Modal.init(elems, {
	        dismissible: false,
	        in_duration: 0
	    });    
	    
	      validateWithdrawAmount();    
	    
	} */
	
var instances;
    
	function openWithdrawPopupModal() {
	    
        var payoutBalance ;
        
        
        
        proceedToWithdrawBtn = document.getElementById("proceedToWithdraw");
        
        var settlementBalanceWithCommas = document.getElementById('settlementBalanceForWithdraw').value;
        var totalBalanceWithCommas = document.getElementById('totalBalanceForWithdraw').value;
        var depositAmountWithCommas = document.getElementById('depositAmountForWithdraw').value;
        var withdrawAmountWithCommas = document.getElementById('withdrawamount').value;
        
        var payoutBalanceWithCommas = document.getElementById('payoutBalanceForWithdraw').value;
        

        var settlementBalance = parseFloat(settlementBalanceWithCommas.replace(/,/g, '')) || 0;
        var totalBalance = parseFloat(totalBalanceWithCommas.replace(/,/g, '')) || 0;
        var depositAmount = parseFloat(depositAmountWithCommas.replace(/,/g, '')) || 0;
        var withdrawAmount = parseFloat(withdrawAmountWithCommas.replace(/[^0-9.]/g, '')) || 0;
     
        var payoutBalance = parseFloat(payoutBalanceWithCommas.replace(/,/g, '')) || 0;

        
        //condition : 1 total amount should be greater than withdraw amount
        var totalAmount = settlementBalance + totalBalance + depositAmount;
                    
        //condition: 2  here eligible amount should be greater than one lack.
        var eligibleAmount = payoutBalance - withdrawAmount;
        
        
        
        
        
        console.log('Settlement Balance:', settlementBalance.toFixed(2));
	    console.log('Total Balance:', totalBalance.toFixed(2));
	    console.log('Deposit Amount:', depositAmount.toFixed(2));
	    console.log('Withdraw Amount:', withdrawAmount.toFixed(2));
	    console.log('payoutbalance Amount:', payoutBalance.toFixed(2));
	    
	    console.log("one lack in number : ",oneLackInNumber.toFixed(2));

	    console.log('Settlement Balance:', typeof(settlementBalance));
	    console.log('Total Balance:', typeof(totalBalance));
	    console.log('Deposit Amount:', typeof(depositAmount));
	    console.log('Withdraw Amount:', typeof(withdrawAmount)); 
	    console.log('payoutbalance Amount:', typeof(payoutBalance)); 
	    console.log("one lack in number : ",typeof(oneLackInNumber)); 
        
        
	      const notallowfiftyk = parseFloat("50000.00");
          const minAmount = parseFloat("10.00");
          const minAmountInFloat = minAmount.toFixed(2);
          const fiftyk = notallowfiftyk.toFixed(2);
    
          
		   var elems = document.querySelector('#withdrawModal');
				instances  = M.Modal.init(elems, {
                dismissible: false,
                in_duration: 0
           }); 
           
           if (withdrawAmount < minAmountInFloat ) {
        
               alert("Minimum Withdraw Amount is 10.00 RM");
                   instances.destroy(); 
           }   
           else if (withdrawAmount == fiftyk ) {
               
               alert("Please Enter The Amount that is greater than or less than 50,000.00 RM");
                   instances.destroy(); 
             
           }
          
 /*        else if(eligibleAmount <= oneLackInNumber){
               
               alert("The given amount is not eligible to Withdraw. ");
               instances.destroy(); 
           } */
           else if (withdrawAmount > totalAmount) {
               
               alert("Withdraw amount exceeds the total amount.");
               instances.destroy(); 
           }
           
           else{
                proceedToWithdrawBtn.disabled = false;
                proceedToWithdrawBtn.style.opacity = proceedToWithdrawBtn.disabled ? '0.5' : '1';
           }
    }
	
		withdrawAmountInput.addEventListener('input', function () {
		  		
			 const inputValue = this.value;
			 const withdrawAmountValue = inputValue.trim() === 'RM' || inputValue.trim() === '' ? '0.00' : inputValue.replace(/[^0-9]/g, '');
			 const withdrawAmount = (parseFloat(withdrawAmountValue) / 100).toFixed(2);
			 const withdrawamountwithcomma = withdrawAmount.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
				 
			/*  	console.log("comma values : ",withdrawamountwithcomma); */
			    withdrawAmountInput.value ="RM "+withdrawamountwithcomma;
			  /*   withdrawAmountInput.value = withdrawAmount; */
			    withdrawButton.disabled = !withdrawAmountInput.value.includes("RM ") || parseFloat(withdrawAmountInput.value.replace(/[^0-9]/g, '')) <= 0;
			    withdrawButton.style.opacity = withdrawButton.disabled ? '0.5' : '1';
		});

		
	
		
		
		function validateWithdrawAmount(){
			
			var payoutBalance ;
		    var proceedToWithdrawBtn = document.getElementById("proceedToWithdraw");
		    
		    var settlementBalanceWithCommas = document.getElementById('settlementBalanceForWithdraw').value;
		    var totalBalanceWithCommas = document.getElementById('totalBalanceForWithdraw').value;
		    var depositAmountWithCommas = document.getElementById('depositAmountForWithdraw').value;
		    var withdrawAmountWithCommas = document.getElementById('withdrawamount').value;
		    
		    var payoutBalanceWithCommas = document.getElementById('payoutBalanceForWithdraw').value;
		    

		    var settlementBalance = parseFloat(settlementBalanceWithCommas.replace(/,/g, '')) || 0;
		    var totalBalance = parseFloat(totalBalanceWithCommas.replace(/,/g, '')) || 0;
		    var depositAmount = parseFloat(depositAmountWithCommas.replace(/,/g, '')) || 0;
		 /*    var withdrawAmount = parseFloat(withdrawAmountWithCommas.replace(/,/g, '')) || 0; */
		    var withdrawAmount = parseFloat(withdrawAmountWithCommas.replace(/[^0-9.]/g, '')) || 0;
		 
		    var payoutBalance = parseFloat(payoutBalanceWithCommas.replace(/,/g, '')) || 0;

		     console.log('Settlement Balance:', settlementBalance.toFixed(2));
		    console.log('Total Balance:', totalBalance.toFixed(2));
		    console.log('Deposit Amount:', depositAmount.toFixed(2));
		    console.log('Withdraw Amount:', withdrawAmount.toFixed(2));
		    console.log('payoutbalance Amount:', payoutBalance.toFixed(2));
		    
		    console.log("one lack in number : ",oneLackInNumber.toFixed(2));

		    console.log('Settlement Balance:', typeof(settlementBalance));
		    console.log('Total Balance:', typeof(totalBalance));
		    console.log('Deposit Amount:', typeof(depositAmount));
		    console.log('Withdraw Amount:', typeof(withdrawAmount)); 
		    console.log('payoutbalance Amount:', typeof(payoutBalance)); 
		    console.log("one lack in number : ",typeof(oneLackInNumber)); 
		    
		    //condition : 1 total amount should be greater than withdraw amount
		    var totalAmount = settlementBalance + totalBalance + depositAmount;
		        	    
		    //condition: 2  here eligible amount should be greater than one lack.
		    var eligibleAmount = payoutBalance - withdrawAmount;
		    
	    	
			  /*   console.log("total amount : ",typeof(totalAmount));
			    console.log("eligible amount : ",typeof(eligibleAmount)); 
			    
			    console.log("total amount value : ",totalAmount);
			    console.log("eligible amount value : ",eligibleAmount); 
			    
			    console.log("condition 2 : ",eligibleAmount <= oneLackInNumber);
			    console.log("condition 1 : ",withdrawAmount >= totalAmount);

		       console.log("Total Amount:", totalAmount, "Withdraw Amount:", withdrawAmount); */

		    
		    if (withdrawAmount >= totalAmount) {
		    	
		    	proceedToWithdrawBtn.disabled = true;
		    	proceedToWithdrawBtn.style.opacity = proceedToWithdrawBtn.disabled ? '0.5' : '1';
		    	
		    }else if(eligibleAmount <= oneLackInNumber){
		    	
		    	proceedToWithdrawBtn.disabled = true;
		    	proceedToWithdrawBtn.style.opacity = proceedToWithdrawBtn.disabled ? '0.5' : '1';
		    }
		    else{
		    	proceedToWithdrawBtn.disabled = false;
		    	proceedToWithdrawBtn.style.opacity = proceedToWithdrawBtn.disabled ? '0.5' : '1';
		    	
		    	
		    }
		    
		   
		    
		}

		function submitWithdrawForm() {
		    var withdrawAmount = document.getElementById('withdrawamount').value;
		    var withdrawAmount = withdrawAmount.replace(/[^0-9.]/g, '') || 0;
			 
		    var finalWithdrawAmountInput = document.getElementById('finalwithdrawamount');
		    
		    finalWithdrawAmountInput.value = withdrawAmount;
		    
		    $('#withdrawModal').modal('close');
		    $("#overlay").show();
		}

	</script>


	<!--  withdraw amount code end -->


	<script>
	
         $(document).ready(function() {
        	 
        	 
          
          $('#data_list_table').DataTable({
           "bSort" : false
          });
         });   
         function openDialog(txnId, merchantId) {
         
          const txnid = document.getElementById("trxid")
          const merchantid = document.getElementById("merchantid")
         
          txnid.value = txnId
          merchantid.value = merchantId
         
          $("#mobi_modal_popup").show();
          $("#overlay-popup").show();   
         }	   
         function ProcessPaid() {
         
          var txnid = document.getElementById("trxid").value;
          var merchantId = document.getElementById("merchantid").value;
         
          document.getElementById("myForm").submit();
         }	   
         function closeDialog() {
          $("#mobi_modal_popup").hide();
          $("#overlay-popup").hide();
         }  
         $(document).ready(function() {
          if(${paginationBean.responseCode}=="0000"){	   
          $("#exampleModalCenter").show();
          document.getElementById('img2').style.display="none";		   
           document.getElementById("para").innerHTML = "Payment Done Successfully"; 
         document.getElementById("pop-bg-color").style.display ="block";
          }		 
          else if(${paginationBean.responseCode}=="0001"){		   
           $("#exampleModalCenter").show();
           document.getElementById('img1').style.display="none";
           document.getElementById("para").innerHTML = "Error in Processing The Payment";
           document.getElementById("pop-bg-color").style.display ="block";		   
          }	   
          });	   
         function closepopup(){
          document.getElementById("exampleModalCenter").style.display ="none";
         document.getElementById("pop-bg-color").style.display ="none";
          }	   
      </script>





<style>
.dialog {
    display: none;
    position: fixed;
    z-index: 1000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    overflow: auto;
    background-color: rgba(0, 0, 0, 0.4);
}

.dialog-content {
    position: relative;
    background-color: #fefefe;
    margin: 10% auto;
    padding: 20px;
    border-radius: 10px;
    width: 60%; /* Adjust width as needed */
    max-width: 400px; /* Limit maximum width */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    text-align: center; /* Center content horizontally */
}

.close {
    color: #aaaaaa;
    position: absolute;
    top: 10px;
    right: 15px;
    font-size: 25px;
    font-weight: bold;
}

.close:hover, .close:focus {
    color: #000;
    text-decoration: none;
    cursor: pointer;
}

.container1 {
    display: flex;
    justify-content: center;
    align-items: flex-start; /* Align to the top */
    margin-top: 100px;
    /* Add space between the top edge of the parent container and the .container1 */
}

.select-wrapper .dropdown-content {
    width: 238px;
    left: 0px;
    top: 0px !important;
    height: 200px;
    transform-origin: 0px 100%;
    opacity: 1;
    transform: scaleX(1) scaleY(1);
}

/*  .row {
          display: -ms-flexbox;
          display: flex;
          -ms-flex-wrap: wrap;
          margin-right: -15px;
          margin-left: -15px;
          align-content: center;
          margin-left: 393px;
       }

       .container-relative {
          position: relative;
          height: px;
          top: -194px;
          right: -240px;
       } */
.drop-down-card {
    position: absolute;
    top: 0;
    left: 0;
    transform: translateY(-50%);
    max-width: 600px;
    padding: 60px;
    margin-top: 30px;
}
</style>

	<script>
  $("#addDepositAmountPopup").hide(); 
  
  function validateAmount(input) {
	
	    var buttonOfPay = document.getElementById("depositamountbtn");

	    // Remove non-numeric characters from the input value
	    var numericValue = input.value.replace(/[^0-9]/g, '');
	    
	    

	    // Convert the numeric part to a float
	    var depositAmount = (parseFloat(numericValue) / 100).toFixed(2);


	    // Check if depositAmount is a valid number
	    if (!isNaN(depositAmount)) {
	        // Format the deposit amount with commas
	        depositAmount = depositAmount.replace(/\B(?=(\d{3})+(?!\d))/g, ',');

	        if (depositAmount == '0.00') {
	            buttonOfPay.disabled = true;
	            buttonOfPay.innerHTML = "Pay RM";
	        } else {
	            buttonOfPay.disabled = false;
	            buttonOfPay.innerHTML = "Pay " + depositAmount + " RM";
	        }

	        // Display the formatted amount in the input box
	        input.value = "RM " + depositAmount;
	       
	    } else {
	        // If not a valid number, set the input value to an empty string
	    	input.value = "";
	        buttonOfPay.disabled = true;
            buttonOfPay.innerHTML = "Pay RM";
	     
	    }
	}

  function addAmount(){
	  
	   $("#addDepositAmountPopup").show(); 
	  document.getElementById("overlay-popup").style.display = "block";
	  document.getElementById("enteredAmount").value="";
	  document.getElementById("bankName").value = "";
	  document.getElementById("depositamountbtn").disabled = true;
	  document.getElementById("depositamountbtn").innerHTML = "Pay RM";
	  
  }
  
  function closeDepositAmount(){
	   $("#addDepositAmountPopup").hide();
       $("#overlay-popup").hide();
  }
  


  function validateAmountOnSubmit() {
		
	    
	        var input = document.getElementById("enteredAmount");
	        var bankNameInput = document.getElementById("bankName");
	        var bankCodeInput = document.getElementById("bankCode");
	        var finalAmount = document.getElementById("transactionAmount");
	 

			//take numeric value only
	        var inputValue = input.value.replace(/[^0-9]/g, '');
			//convert into deimal format
	        var depositAmount = (parseFloat(inputValue) / 100).toFixed(2);
			
			//remove commas
	        depositAmount = depositAmount.replace(/\B(?=(\d{3})+(?!\d))/g, '');
			
			//compare amount
	        var minAmount = 2.00;
		
 
	        if (parseFloat(depositAmount) < minAmount) {
	      
	            alert("Minimum Deposit Amount is 2.00 RM ");
	            return false;
	        }
	        
	        var maxAmount = 800000.00;
	        
	        if (parseFloat(depositAmount) > maxAmount) {
	      
	            alert("Maximum Deposit Amount is 8,00,000 RM ");
	            return false;
	        }  
		    
		    input.value =  	input.value;
		 
// if bank name is empty throws alert
	          if (bankNameInput.value.trim() === "") {
	            alert("Please select your bank.");
	            return false;
	        }
	        
	    /*     var isValidBank = false;
	        var enteredBankName = bankNameInput.value.trim();

	        for (var i = 0; i < banks.length; i++) {
	        	console.log("compare : ",banks[i].name+" / "+enteredBankName)
	            if (banks[i].name === enteredBankName) {	
	                isValidBank = true;
	                break;
	            }
	        }

	        if (!isValidBank) {
	            alert("Bank Name Should be Valid.");
	            return false;
	        } */
	        
	        
// if bank name is not exist in that array, it throws as invalid bank name.

	        var selectedBank = banks.find(function(bank) {
	        	/* console.log("Selected Bank name:", banks.name +"/"+bankNameInput.value); */
	            return bank.name === bankNameInput.value.trim();
	        });

	        if (!selectedBank) {
	            alert("Bank Name Should be Valid.");
	            return false;
	        }
			
	          
	        finalAmount.value = depositAmount;

	        return true;
	}
  
  
  function validateBankName(event) {
	    var charCode = event.which || event.keyCode;
	    var charStr = String.fromCharCode(charCode);

	    if (/^[A-Za-z]+$/.test(charStr)) {
	    
	        return true;  
	    } else {
	        event.preventDefault();
	
	        return false;
	    }
	}
  
  
 
	 
  </script>
	<script>

  console.log(jQuery.fn.jquery);

  </script>
</body>
</html>