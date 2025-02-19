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
      <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
   </head>
   <style>
      .container ss{
      max-width: 100%;
      height: auto;
      }
      a {
      text-decoration: none !important;
      text-decoration: none;
      font-weight: 470 !important;
      }
      @media (min-width: 767px){
      #main-wrapper[data-layout="vertical"][data-sidebartype="mini-sidebar"] .page-wrapper {
      margin-left: 50px !important;
      margin-right: -15px;
      }}
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
      input[type=number] {
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
      .temp-css{
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
      Â  outline: none;
      Â  border: 2px solid #005baa;
      Â  border-radius: 30px;
      Â  padding: 5px 10px;
      Â  margin-left: 160px;
      Â  color: white;
      Â  background-color: #005baa;
      Â  margin-top: 20px;
      Â  width: 160px;
      Â  height: 48px;
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
      #exampleModalCenter {
      z-index: 99;
      width: 25%;
      font-size: 24px;
      border-radius: 20px;
      font-weight: 400;
      font-family: 'Poppins', sans-serif;
      text-align: center;
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
   	.pad-0{
   	padding: 7px 0px ;
   	}
   	.pad-1{
   	padding: 0px 15px;
   	}
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
      .padding-card1{
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
      
      
      .centercard {
  margin: auto;
  width: 50%;
  border: 3px ;
  padding: 10px;
}
      
      /* new changes */
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
      function withdraw() {
      	var amount = document.getElementById("amount").value;
      	var merchantId = document.getElementById("merchantId").value;

      	//alert(merchantId);
      	if(amount!=""){
      	$("#overlay").show();
      	
     // debugger;
      	
      		document.location.href = '${pageContext.request.contextPath}/transactionUmweb/addbankamount?amount='
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
      
      	
   </script>
   <body class="" >
      <div id="overlay">
         <div id="overlay_text">
            <img class="img-fluid"
               src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
         </div>
      </div>
      <div class="container-fluid ">
         <div class="row mr-2 ml-2">
            <div class="col">
               <div class="card blue-bg text-white">
                  <div class="card-content">
                     <div class="d-flex align-items-center">
                        <h3 class="text-white">
                           <strong> Payout Bank Balance</strong>
                        </h3>
                     </div>
                  </div>
               </div>
            </div>
         </div>
         <style>
         .card {
      border-radius: 10px;
   }
         </style>
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
         
         
                
		<div class="row mr-2 ml-2">
    <!-- First Card -->
    <div class="col-md-4 mb-3">
        <div class="card card-mobi" style="border-radius: 0.9rem;">
            <div class="card-body">
                <div class="row pad-1">
                    <!-- Image Container -->
                    <div class="col-3 pad-0">
                        <img class="img-fluid" src="${pageContext.request.contextPath}/resourcesNew1/assets/Ampbank.svg">
                    </div>
                    <!-- Content Container -->
                    <div class="col-9 pad-0" >
                       <strong class="text-right " style="color: #72777B; font-size: 18px; font-weight: 600; white-space: nowrap;">AmBank Malaysia BerhadM2U</strong>
							<div class="table-responsive" style="margin-top: 12px !important; color: #72777B; font-size: 16px;">
										<tr>
										 <a >As of : ${settlementbankbalance.amBankUpdatedDate}</a> 
											
										</tr>
									<br>
									
							</div>
                    </div>
                </div>
                <div class="row pad-1" style="padding-top:24px !important;">
                <table class="table table-borderless pad-0" Style="margin-bottom: 0rem;">
								<tbody>
										<tr >				
											<td class=" pad-0" style="color:#1c1c1c; font-size: 20px; padding: 0px !important; font-weight: 600;"><span style="color: #72777B !important; font-weight: 600; font-size:16px;">Balance :  </span> RM ${settlementbankbalance.amBankBalance}</td>
									</tr>
								</tbody>
								</table>
                </div>
            </div>
        </div>
    </div>
     <!-- Second Card -->
    <div class="col-md-4 mb-3">
        <div class="card card-mobi" style="border-radius: 0.9rem;">
            <div class="card-body">
                <div class="row pad-1">
                    <!-- Image Container -->
                    <div class="col-3 pad-0">
                        <img class="img-fluid" src="${pageContext.request.contextPath}/resourcesNew1/assets/OCDC1.svg">
                    </div>
                    <!-- Content Container -->
                    <div class="col-9 pad-0" >
                       <strong class="text-right " style="color: #72777B; font-size: 18px; font-weight: 600;">OCBC Bank</strong>
							<div class="table-responsive" style="margin-top: 12px !important; color: #72777B; font-size: 16px;">
										<tr>
										 <a >As of : ${settlementbankbalance.ocbcBankUpdatedDate}</a> 
											
										</tr>
									<br>
									
							</div>
                    </div>
                </div>
                <div class="row pad-1" style="padding-top:24px !important;">
                <table class="table table-borderless pad-0" Style="margin-bottom: 0rem;">
								<tbody>
										<tr >				
											<td class=" pad-0" style="color:#1c1c1c; font-size: 20px; padding: 0px !important; font-weight: 600;"><span style="color: #72777B !important; font-weight: 600; font-size:16px;">Balance :  </span> RM ${settlementbankbalance.ocbcBankBalance}</td>
									</tr>
								</tbody>
								</table>
                </div>
            </div>
        </div>
    </div>
    <!-- Third Card -->
    <div class="col-md-4 mb-3">
        <div class="card card-mobi" style="border-radius: 0.9rem;">
            <div class="card-body">
                <div class="row pad-1">
                    <!-- Image Container -->
                    <div class="col-3 pad-0">
                        <img class="img-fluid" src="${pageContext.request.contextPath}/resourcesNew1/assets/total.svg">
                    </div>
                    <!-- Content Container -->
                    <div class="col-9 pad-0" >
                       <strong class="text-right " style="color: #72777B; font-size: 18px; font-weight: 600;">Total</strong>
							<div class="table-responsive" style="margin-top: 12px !important; color: #72777B; font-size: 16px;">
										<tr>
										 <a >As of : ${settlementbankbalance.totalBankBalanceUpdatedDate}</a> 
											
										</tr>
									<br>
									
							</div>
                    </div>
                </div>
                <div class="row pad-1" style="padding-top:24px !important;">
                <table class="table table-borderless pad-0" Style="margin-bottom: 0rem;">
								<tbody>
										<tr >				
											<td class=" pad-0" style="color:#1c1c1c; font-size: 20px; padding: 0px !important; font-weight: 600;"><span style="color: #72777B !important; font-weight: 600; font-size:16px;">Balance :  </span> RM ${settlementbankbalance.totalBankBalance}</td>
									</tr>
								</tbody>
								</table>
                </div>
            </div>
        </div>
    </div>



         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         
         	<!-- 	commented below -->
		<%-- <div class="row mr-2 ml-2">
			<div class="centercard col-md-4" >
				<div class="card card-mobi h-200 p-3 mobi-flex mobi-justify-content-center" style="border-radius: 0.9rem;">
					<div class="row-c mb-3 d-flex align-items-center justify-content-end">
					<div class="col-4 d-flex justify-content-end">
						<img class="img-fluid" src="${pageContext.request.contextPath}/resourcesNew1/assets/bankbalance.png">
					</div>
					</div>
					<div class="row-c " >
					<div class="col-12 ">
						<div class="text-right" >
						<br>
							<strong class="display-8 text-right " style="color: #2D2D2D;"> Bank Balance</strong>
							<div class="table-responsive">
								 <c:forEach items="${settlementbankbalance}" var="dto" varStatus="loop">
										<tr class="dto-${loop.index}">
										<a >Last Updated : ${dto.updatedDate}</a>
											
										</tr>
									</c:forEach> 
									<br>
									<table class="table table-borderless" Style="margin-bottom: 0rem;">
								<tbody>
									<c:forEach items="${settlementbankbalance}" var="dto" varStatus="loop">
										<tr class="dto-${loop.index}">
											<td class="text-right" style="color:#72777B; font-size: 26px;">RM ${dto.amount}</td>
										</tr>
									</c:forEach>
								</tbody>
								</table>
							</div>
						</div>
					</div>
					</div>
				</div>
			</div> --%>
		<!-- 	commented above -->
		
			<%-- 
			 <div class="col-md-4" >
				<div class="card card-mobi h-200 p-3 mobi-flex mobi-justify-content-center" style="border-radius: 0.9rem;">
					<div class="row-c mb-3 d-flex align-items-center justify-content-center">
					
					<div class="col-4 d-flex justify-content-center">
						 <img class="img-fluid img-responsive" style="max-width: 76.21px; max-height: 76.21px;" src="${pageContext.request.contextPath}/resourcesNew1/assets/withdraw.png">
                          
					</div>
					</div>
					<div class="row-c " >
					<div class="col-12 ">
						<div class="text-center">
							<strong class="display-8 text-right " >Add Bank Balance</strong>
							<div class="table-responsive">
								<table class="table table-borderless" Style="margin-bottom: 0rem;">
								<tbody>
								
								<c:forEach items="${settlementbankbalance}" var="dto" varStatus="loop">
										<tr class="dto-${loop.index}">
										<a >Last Updated : ${dto.updatedDate}</a>
											<td class="text-right" style="color:#72777B; font-size: 26px;">RM ${dto.amount}</td>
										</tr>
									</c:forEach>
									
									   
                           <div class="Withdraw">
                              <input type="number"
                                 placeholder="Enter Amount"
                                 id="amount"
                                 name="amount"
                                 path="amount"
                                 min="0"
                                 step="0.1"
                                 required
                                 style="width:80%"
                                 >
                                <!--  <br> -->
                              <button onclick="withdraw()" class="btn btn-primary custom-button mx-auto btn-lg btn-block" style="background-color: #005baa; height: 40px; width:220px">Update Balance</button>
                              <style>
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
                           </div>
								</tbody>
								</table>
							</div>
						</div>
					</div>
					</div> --%>
				</div>
			</div>
			
			
			
			
			
			
		</div>    
     
     
     		<input type="hidden" id="merchantId" value="${merchantid}" />
     		
     
     <%-- <c:choose>
     	<c:when test="${adminusername.toLowerCase()=='mobi'}"> --%>
     
           <%--  <div class="row justify-content-center">
               <div class="col-md-5">
                  <div class="card border-radius mx-auto" style="border-radius: 10px;">
                     <div class="card-content1 text-center pt-4">
                        <div>
                           <div class="d-flex justify-content-center mb-3">
                              <img class="img-fluid img-responsive" style="max-width: 76.21px; max-height: 76.21px;" src="${pageContext.request.contextPath}/resourcesNew1/assets/withdraw.png">
                           </div>
                           <strong class="mb-3">Withdraw Amount</strong>
                           <div class="Withdraw">
                              <input type="number"
                                 placeholder="Enter Amount"
                                 id="amount"
                                 name="amount"
                                 path="amount"
                                 min="0"
                                 step="0.1"
                                 required
                                 style="width:80%"
                                 >
                              <button onclick="withdraw()" class="btn btn-primary custom-button mx-auto btn-lg btn-block" style="background-color: #005baa; height: 50px;">Withdraw</button>
                              <style>
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
                           </div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
            
          --%>
           
           
           
         
         
      </div>
      <div id="overlay-popup"></div>
    
      <!-- 	</form> -->
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
   </body>
</html>