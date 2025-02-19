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
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400&display=swap"
	rel="stylesheet">
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
</head>
<%-- <script type="text/javascript"
      src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
      <script type="text/javascript"
      src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
       
      <script
      src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script> --%>
<!-- Script tag for Datepicker 	 -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
<style>
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
	/* background-image: -webkit-linear-gradient(top, #DBDBDB, #E2E2E2);
      background-image:    -moz-linear-gradient(top, #DBDBDB, #E2E2E2);
      background-image:     -ms-linear-gradient(top, #DBDBDB, #E2E2E2);
      background-image:      -o-linear-gradient(top, #DBDBDB, #E2E2E2);
      background-image:         linear-gradient(top, #DBDBDB, #E2E2E2); */
	/*  position:absolute;
      left:62rem;
      bottom:1rem; */
	font-family: 'Poppins', sans-serif;
	/* width:20%;
      height:6%; */
}

#pagination a, #pagination i {
	display: inline-block;
	vertical-align: middle;
	width: 2.2rem;
	/*  color: #7D7D7D; */
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
	/* margin: 0 2px 0 2px; */
	margin: 0 2px;
	border-radius: 1px;
	border: 1px solid #005baa;
	cursor: pointer;
	/* box-shadow: inset 0 1px 0 0 #D7D7D7, 0 1px 2px #666; */
	/* text-shadow: 0 1px 1px #FFF; */
	background-color: white;
	color: #005baa;
	height: 2.3rem;
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

#exampleModalCenter {
	z-index: 99;
	width: 25%;
	font-size: 24px;
	font-weight: 400;
	font-family: 'Poppins', sans-serif;
	text-align: center;
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

.pop-body {
	border-radius: 25px;
}

.mb-0 {
	padding-bottom: 0px !important;
}
</style>
<script type="text/javascript">
      history.pushState(null, null, "");
      window.addEventListener('popstate', function() {
      	history.pushState(null, null, "");
      
      });
   </script>
<!-- <script type="text/javascript">
      jQuery(document).ready(function() {
      
      	$('#export').select2();
      	$('#txnType').select2();
      });
      </script> -->
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

.btn.btn-primary {
	background-color: #005baa !important;
	color: #fff !important;
	border-radius: 20px !important;
}

.btn.btn-primary1 {
	background-color: #005baa !important;
	color: #fff !important;
	border-radius: 20px !important;
	width: 71%;
}
</style>
<script type="text/javascript">
      function openNewWin(mrn){
      	//alert(txnID);
      	
      	var url=window.location;
      	//alert(url);
      	var src = document.getElementById('popOutiFrame').src;
      	 src=url+'transactionUmweb/UMdetails/'+mrn;
      	//    alert(src);
      	//src = pdffile_url;
      	//alert(src);
      	var h = 600;
      	var w = 1000;
      	var title = "Mobiversa Receipt";
      	
      	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
          var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;
      
          var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
          var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;
      
          var left = ((width / 2) - (w / 2)) + dualScreenLeft;
          var top = ((height / 2) - (h / 2)) + dualScreenTop;
         
         // divviewer.style.display='block';
          var newWindow = window.open(src, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
          
          //alert(src);
         // alert(newWindow);
          // Puts focus on the newWindow
          if (window.focus) {
              newWindow.focus();
          }
      		
      }
      
      
      
      //Boost Sales Slip - Start
      
      function openBoostslip(rrn){
      	
      	var url=window.location;
      	var src = document.getElementById('popOutiFrame').src;
      	 src=url+'transactionUmweb/BoostSlip/'+rrn;
      	var h = 600;
      	var w = 1000;
      	var title = "Mobiversa Receipt";
      	
      	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
          var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;
      
          var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
          var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;
      
          var left = ((width / 2) - (w / 2)) + dualScreenLeft;
          var top = ((height / 2) - (h / 2)) + dualScreenTop;
         
          var newWindow = window.open(src, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
          
          if (window.focus) {
              newWindow.focus();
          }
      		
      }
      
      //Boost Sales Slip - End
      
      
      
      //tng sale slip start
      function openTngpayslip(rrn){
      
      	var url=window.location;
      	var src = document.getElementById('popOutiFrame').src;
      	src=url+'transactionUmweb/TngpaySlip/'+rrn;
      	var h = 600;
      	var w = 1000;
      	var title = "Mobiversa Receipt";
      
      	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
      	var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;
      
      	var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
      	var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;
      
      	var left = ((width / 2) - (w / 2)) + dualScreenLeft;
      	var top = ((height / 2) - (h / 2)) + dualScreenTop;
      
      	var newWindow = window.open(src, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
      
      
      	if (window.focus) {
      	 newWindow.focus();
      	}
      		
      	}
      	//tng sale slip end
      	
      	
      	//shoppepay sales slip 
      	function openShopeepayslip(rrn){
      
      	var url=window.location;
      	var src = document.getElementById('popOutiFrame').src;
      	src=url+'transactionUmweb/Shopeepayslip/'+rrn;
      	var h = 600;
      	var w = 1000;
      	var title = "Mobiversa Receipt";
      
      	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
      	var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;
      
      	var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
      	var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;
      
      	var left = ((width / 2) - (w / 2)) + dualScreenLeft;
      	var top = ((height / 2) - (h / 2)) + dualScreenTop;
      
      	var newWindow = window.open(src, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
      
      
      	if (window.focus) {
      	 newWindow.focus();
      	}
      		
      	}
      	
      	
      	//shopeepay saales slip
      
      
      //Grabpay Sales Slip - Start
      
      function openGrabpayslip(rrn){
      
      	var url=window.location;
      	var src = document.getElementById('popOutiFrame').src;
      	 src=url+'transactionUmweb/GrabpaySlip/'+rrn;
      	var h = 600;
      	var w = 1000;
      	var title = "Mobiversa Receipt";
      	
      	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
          var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;
      
          var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
          var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;
      
          var left = ((width / 2) - (w / 2)) + dualScreenLeft;
          var top = ((height / 2) - (h / 2)) + dualScreenTop;
         
          var newWindow = window.open(src, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
          
       
          if (window.focus) {
              newWindow.focus();
          }
      		
      }
      
      //Grabpay Sales Slip - End
      
      //Fpx Sales Slip - Start
      
      function openFpxslip(txnId){
      
      	var url=window.location;
      	var src = document.getElementById('popOutiFrame').src;
      	 src=url+'transactionUmweb/FpxSlip/'+txnId;
      	var h = 600;
      	var w = 1000;
      	var title = "Mobiversa Receipt";
      	
      	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
          var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;
      
          var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
          var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;
      
          var left = ((width / 2) - (w / 2)) + dualScreenLeft;
          var top = ((height / 2) - (h / 2)) + dualScreenTop;
         
          var newWindow = window.open(src, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
          
       
          if (window.focus) {
              newWindow.focus();
          }
      		
      }
      
      //Fpx Sales Slip - End
      
      
      //BNPL SLIP STARTS
      
      function openBnplslip(rrn){
      
      	var url=window.location;
      	var src = document.getElementById('popOutiFrame').src;
      	src=url+'transactionUmweb/BnplPaySlip/'+rrn;
      	var h = 600;
      	var w = 1000;
      	var title = "Mobiversa Receipt";
      
      	var dualScreenLeft = window.screenLeft != undefined ? window.screenLeft : window.screenX;
      	var dualScreenTop = window.screenTop != undefined ? window.screenTop : window.screenY;
      
      	var width = window.innerWidth ? window.innerWidth : document.documentElement.clientWidth ? document.documentElement.clientWidth : screen.width;
      	var height = window.innerHeight ? window.innerHeight : document.documentElement.clientHeight ? document.documentElement.clientHeight : screen.height;
      
      	var left = ((width / 2) - (w / 2)) + dualScreenLeft;
      	var top = ((height / 2) - (h / 2)) + dualScreenTop;
      
      	var newWindow = window.open(src, title, 'scrollbars=yes, width=' + w + ', height=' + h + ', top=' + top + ', left=' + left);
      
      
      	if (window.focus) {
      	 newWindow.focus();
      	}
      		
      	}
      
      
          
   </script>
<script lang="JavaScript">


function searchTransaction(){
	var selectType = document.getElementById("drop_search").value;
	var inputText = document.getElementById("searchApi").value;
	
	alert(inputText);
	
	console.log(inputText);
	document.location.href = '${pageContext.request.contextPath}/refund/searchTransaction?selectType='
		+selectType +'&inputText='+inputText;
form.submit;
}


function openDialog(txnId, intiateDate,itemId){
	$("#overlay").show();
	 document.getElementById("txnId").value = txnId;
	 document.getElementById("intiateDate").value= intiateDate;
	
	document.getElementById("curr-procces").value = itemId
    
	var txnid = document.getElementById("txnId").value
	var intiatedate = document.getElementById("intiateDate").value
	var currentProces = document.getElementById("curr-procces").value
	
	var settlementDate = document.getElementById("settlementDate").value;
	var txnMid = document.getElementById("txnMid").value;
	var transactionAmount = document.getElementById("transactionAmount").value;
	var PageNumber = document.getElementById("pgnum").value;
	console.log(transactionAmount);
	
	alert(PageNumber);
	alert(txnid);
	alert(intiatedate);
	alert(currentProces);
	
	document.location.href = '${pageContext.request.contextPath}/refund/refundInprogress?txnid='
			+txnid + '&intiatedate=' + intiatedate + '&currentProces=' + currentProces+ '&currPage=' + PageNumber+'&settlementDate='+settlementDate+'&txnMid='+txnMid+'&transactionAmount='+transactionAmount+'&currPage=' + PageNumber;
	form.submit;
	
	
}

function pagination(){
	
	
	  var PageNumber = document.getElementById("pgnum").value;
	 // alert(PageNumber);
	  document.location.href = '${pageContext.request.contextPath}/refund/proccedToRefundpagination?currPage='
			+PageNumber;
	form.submit;
}

function openDialog1(txnId, intiateDate,itemId){
	$("#overlay").show();
	 document.getElementById("txnId").value = txnId;
	 document.getElementById("intiateDate").value= intiateDate;
	document.getElementById("curr-procces").value = itemId
    
	var txnid = document.getElementById("txnId").value
	var intiatedate = document.getElementById("intiateDate").value
	var currentProces = document.getElementById("curr-procces").value
	
	var settlementDate = document.getElementById("settlementDate").value;
	var txnMid = document.getElementById("txnMid").value;
	var transactionAmount = document.getElementById("transactionAmount").value;
	var PageNumber = document.getElementById("pgnum").value;
	
	console.log(transactionAmount);
	
	
	document.location.href = '${pageContext.request.contextPath}/refund/refundcompleted?txnid='
			+txnid + '&intiatedate=' + intiatedate + '&currentProces=' + currentProces+ '&currPage=' + PageNumber+'&settlementDate='+settlementDate+'&txnMid='+txnMid+'&transactionAmount='+transactionAmount;
	form.submit;
	
	
}









      function loadSelectData() {
      	$("#overlay").show();
      	//alert("test"+document.getElementById("txnType").value);
      	var e = document.getElementById("from").value;
      	var e1 = document.getElementById("to").value;
      /* 	var e2 = document.getElementById("txnType").value; */
        var e2 = "CARD";
      	var PageNumber = document.getElementById("pgnum").value;
      	
      	var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
      	var toDate = new Date(e1);//.toDateString("yyyy-MM-dd");	
      
      	var fromday = fromDate.getDate();
      	var frommon = fromDate.getMonth() + 1;
      	var fromyear = fromDate.getFullYear();
      
      	var today = toDate.getDate();
      	var tomon = toDate.getMonth() + 1;
      	var toyear = toDate.getFullYear();
      
      	var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
      	var todateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;
      
      	/* var e2 = document.getElementById("txnType").value; */
      	if (e == null ||e == '' || e1 == null || e1 == '') {
      		alert("Please Select date(s)");
      		//form.submit == false;
      	}
      	else if (e2 == null || e2 == '') {
      
      		alert("Please Select Payment Method");
      
      	} else if(e2 != null){
      		
      		document.getElementById("dateval1").value = fromdateString;
      		document.getElementById("dateval2").value = todateString;
      		var TxnType ="CARD";
      		document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchUMEzyway?date='
      				+ fromdateString + '&date1=' + todateString + '&txnType=' + TxnType+ '&currPage=' + PageNumber;
      		form.submit;
      		
      		
      	}
      	
      	else {
      		document.getElementById("dateval1").value = fromdateString;
      		document.getElementById("dateval2").value = todateString;
      		/* document.getElementById("txnType").value = e2; */
      		/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
      				+ e + '&date1=' + e1 + '&txnType=' + e2; */
      				var TxnType = "CARD";
      				document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchUMEzyway?date='
      						+ fromdateString + '&date1=' + todateString + '&txnType=' + TxnType+ '&currPage=' + PageNumber;
      		form.submit;
      		//document.getElementById("dateval1").value = e;
      		//document.getElementById("dateval2").value = e1;
      
      	}
      }
      
      
      
    
      
      
      function loadSelectData1() {
      	//alert("test"+document.getElementById("txnType").value);
      	
      	$("#overlay").show();
      	
      	var e = document.getElementById("FromDate").value;
      	var e1 = document.getElementById("From1Date").value;
      	/* var e2 = document.getElementById("TXNType1").value; */
      	var e2 = "CARD";
      	var PageNumber = document.getElementById("pgnum").value;
      	var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
      	var toDate = new Date(e1);//.toDateString("yyyy-MM-dd");	
      
      	var fromday = fromDate.getDate();
      	var frommon = fromDate.getMonth() + 1;
      	var fromyear = fromDate.getFullYear();
      
      	var today = toDate.getDate();
      	var tomon = toDate.getMonth() + 1;
      	var toyear = toDate.getFullYear();
      
      	var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
      	var todateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;
      
      	
      	/* var e2 = document.getElementById("txnType").value; */
      //	if (e == null ||e == '' || e1 == null || e1 == '') {
      		//alert("Please Select date(s)");
      		//form.submit == false;
      //	}
      	 if (e2 == null || e2 == '') {
      
      		alert("Please Select Payment Method");
      
      	} else if(e2 != null){
      		
      		document.getElementById("dateval1").value = fromdateString;
      		document.getElementById("dateval2").value = todateString;
      		/* var TxnType = document.getElementById("TXNType1").value; */
      		
      		var TxnType = "CARD";
      		
      		document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchUMEzyway?date='
      				+ fromdateString + '&date1=' + todateString + '&txnType=' + TxnType +  '&currPage=' + PageNumber;
      		form.submit;
      		
      		
      	}
      	
      	else {
      		document.getElementById("dateval1").value = fromdateString;
      		document.getElementById("dateval2").value = todateString;
      		/* document.getElementById("txnType").value = e2; */
      		/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
      				+ e + '&date1=' + e1 + '&txnType=' + e2; */
      				/* var TxnType = document.getElementById("TXNType1").value; */
      				 var TxnType = "CARD";
      				document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchUMEzyway?date='
      						+ fromdateString + '&date1=' + todateString + '&txnType=' + TxnType + '&currPage=' + PageNumber;
      		form.submit;
      		//document.getElementById("dateval1").value = e;
      		//document.getElementById("dateval2").value = e1;
      
      	}
      }
      
      /*  SEARCH FUNCTION HERE  */
      
      function loadSearch()
      {
    		$("#overlay").show();
    		var Value = document.getElementById("searchApi").value;
    		var TXNTYPE = 'UM_CARD1';
    		var type = document.getElementById("drop_val").value;
    		
    		document.location.href = '${pageContext.request.contextPath}/searchNew/find?VALUE='
  				+ Value + '&TXNTYPE='+ TXNTYPE +'&Type=' + type;
  			form.submit;
    		
      }
     	
      function loadDropSearch(){
    	  var e = document.getElementById("drop_search");
        	var strUser = e.options[e.selectedIndex].value;
        	document.getElementById("drop_val").value = strUser;   
        	
        	/* For Dynamic Placeholder in SEARCH */
        	
        	if (strUser == "Ref") {
        		  document.getElementsByName('search')[0].placeholder = 'Enter Reference';
        	  } else if (strUser == "Ap_Code") {
        		  document.getElementsByName('search')[0].placeholder = 'Enter Approval Code';
        	  }
        	  else if (strUser == "RRN") {
        		  document.getElementsByName('search')[0].placeholder = 'Enter RRN Number';
        	  }
        	  else if (strUser == "CARD_NO") {
        		  document.getElementsByName('search')[0].placeholder = 'Enter last four digits only';
        	  }
        	  else if(strUser == ""){
        		  document.getElementsByName('search')[0].placeholder = 'Please select type to search ';
        	  }
      }
     
      function loadExpData() {
      	//alert("test"+document.getElementById("txnType").value);
      	
      	var e = document.getElementById("from").value;
      	var e1 = document.getElementById("to").value;
      	var e2 = document.getElementById("export1").value;
      	//var txnType = document.getElementById("txnType").value;
      	
      	var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
      	var toDate = new Date(e1);//.toDateString("yyyy-MM-dd");	
      
      	var fromday = fromDate.getDate();
      	var frommon = fromDate.getMonth() + 1;
      	var fromyear = fromDate.getFullYear();
      
      	var today = toDate.getDate();
      	var tomon = toDate.getMonth() + 1;
      	var toyear = toDate.getFullYear();
      
      	var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
      	var todateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;
      
      	/* alert("e"+e);
      	alert("e1"+e1);
      	alert("e2"+e2); */
      	
      	/* var e2 = document.getElementById("txnType").value; */
      	if (e == null || e == '' || e1 == null || e1 == '') {
      		alert("Please Select date(s)");
      		//form.submit == false;
      	}
      	 else if (e2 == null || e2 == '') {
      
      			alert("Please Select Export Type");
      
      		}
      	
      	else {
      		/* alert("inside else"); */
      		
      		document.getElementById("datevalex1").value = fromdateString;
      		document.getElementById("datevalex2").value = todateString;
      		
      		/* document.getElementById("txnType").value = e2; */
      		/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
      				+ e + '&date1=' + e1 + '&txnType=' + e2; */
      				document.location.href = '${pageContext.request.contextPath}/transactionUmweb/umEzywayExport?fromDate=' + fromdateString
      				+ '&toDate=' + todateString + '&export='+e2; 
      				
      		form.submit;
      		//document.getElementById("dateval1").value = e;
      		//document.getElementById("dateval2").value = e1;
      
      	}
      }
      
      
      function loadDropDate13() {
      	//alert("loadDropDate13");
      	var e = document.getElementById("export");
      
      	var strUser = e.options[e.selectedIndex].value;
      	document.getElementById("export1").value = strUser;
      	//alert("data :" + strUser + " "+ document.getElementById("status1").value);
      
      }
      
      /* function loadDropDate14() {
      	//alert("loadDropDate13");
      	var e = document.getElementById("txnType");
      
      	var strUser = e.options[e.selectedIndex].value;
      	document.getElementById("txnType1").value = strUser;
      	//alert("txntype: "+strUser);
      	//document.getElementById("searchTxnType").value=strUser;
      	//alert("data :" + strUser + " "+ document.getElementById("status1").value);
      
      } */
      function checkTxnType()
      {
      //alert("check"+document.getElementById("txnType1").value);
      var txnType=document.getElementById("txnType1").value;
      if(txnType=="Choose" || txnType=='')
      {
      alert("please select txnType field..");
      return false;
      }
      
      }
      function loaddata() {
      alert("test data");
      var e = document.getElementById("datepicker").value;
      var e1 = document.getElementById("datepicker1").value;
      alert("test data1");
      /* var e2 = document.getElementById("tid1").value;
      var e3 = document.getElementById("devid1").value; */
      /* var e4 = document.getElementById("status1").value; */
      alert("test data2");
      var e5 = document.getElementById("export1").value;
      alert("e"+e);
      alert("e1"+e1);
      alert("e5"+e5);
      //alert("status"+e4);
      //alert("e5"+e5);
      
      /* 	if (e == null || e1 == null || e == '' || e1 == '') {
      		//alert("picker :"+e + "  "+ e1);
      		e = document.getElementById("dateval1").value;
      		e1 = document.getElementById("dateval2").value;
      		//alert("hidden : "+e + "  "+ e1); */
      if (e == null || e1 == null || e == '' || e1 == '') {
      	alert("Please select date(s)");
      	//}
      } else {
      	
      	alert("test data5");
      
      	//alert("test2: " + e + " " + e1);
      	 document.location.href = '${pageContext.request.contextPath}/transactionUmweb/umEzywayExport?fromDate=' + e
      			+ '&toDate=' + e1 +'&export='+e5; 
      	//alert(e);
      	/* document.form1.action = "${pageContext.request.contextPath}/transactionUmweb/umEzywayExport"; */
      	alert("test data6");
      	//form.submit();
      	//document.form1.submit();
      	
      	form.submit();
      
      }
      }
      
      function loadDate(inputtxt, outputtxt) {
      
      	// alert("test data123");
      	var field = inputtxt.value;
      	//var field1 = outputtxt.value;
      	//alert(field+" : "+outputtxt.value);
      	//document.getElementById("date11").value=field;
      	outputtxt.value = field;
      	//alert(outputtxt.value);
      	// alert(document.getElementById("date11").value);
      }
      
      function show_key(id){
      	
      	document.getElementById('show_key_'+id).style.display ="block";
      	document.getElementById('hide_key_'+id).style.display ="none";
      
      }
      
      function hide_key(id){
      	
      	document.getElementById('show_key_'+id).style.display ="none";
      	document.getElementById('hide_key_'+id).style.display ="block";
      
      }
      
      function show_rrn(id){
      
      document.getElementById('show_rrn_'+id).style.display ="block";
      document.getElementById('hide_rrn_'+id).style.display ="none";
      
      }
      
      function hide_rrn(id){
      
      document.getElementById('show_rrn_'+id).style.display ="none";
      document.getElementById('hide_rrn_'+id).style.display ="block";
      
      }
      
      
      //Script by Denis Gritcyuk: tspicker@yahoo.com
      //Submitted to JavaScript Kit (http://javascriptkit.com)
      //Visit http://javascriptkit.com for this script
   </script>
<body class="">
	<div id="overlay">
		<div id="overlay_text">
			<img class="img-fluid"
				src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
		</div>
	</div>
	<div class="test" id="pop-bg-color"></div>
	<div class="container-fluid mb-0" id="pop-bg">
		<div class="row">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>Refund Request</strong>
							</h3>
						</div>
					</div>
				</div>
			</div>
		</div>







		<div class="row" id="searchBoxDiv">
			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">

						<!-- SEARCH TEST -->
						<div class="row"
							style="display: flex; align-items: center; justify-content: space-between; margin-left: 01%;">
							<div class="col s12">
								<div class="input-field col s12 m3 l3"
									style="font-family: 'Poppins', sans-serif; width: 30%;">
									<select name="drop_search" id="drop_search">
										<option selected value="" id="choose">Choose Type</option>
										<option value="transactionId">Transaction Id</option>
										<option value="invoiceId">Invoice Id</option>

									</select> <input type="hidden" id="drop_val">
								</div>

								<div class="input-field col s12 m3 l3"
									style="margin-left: 07%; width: 30%;">
									<input type="text" id="searchApi" name="search" class=""
										style="font-family: 'Poppins', sans-serif;"
										placeholder="Please select type to search">
								</div>
								<div class="input-field col s12 m3 l3"
									style="width: 10%; margin-left: 07%;">
									<div class="button-class" style="float: left;">
										<button class="btn btn-primary blue-btn" type="button"
											onclick="searchTransaction()"
											style="font-family: 'Poppins', sans-serif; width: 100%; font-size: 14px;">Search</button>
									</div>
								</div>
							</div>
						</div>
						<!--  SEARCH TEST ENDS -->
					</div>
				</div>
			</div>
		</div>





		<input type="hidden" id="txnId" name="txnId" value="0"> <input
			type="hidden" id="intiateDate" name="intiateDate" value="0">

		<input type="hidden" id="curr-procces" name="curr-procces" value="0">

		<input type="hidden" id="settlementDate" name="settlementDate"
			value="${settlementDate}"> <input type="hidden" id="txnMid"
			name="txnMid" value="${txnMid}"> <input type="hidden"
			id="transactionAmount" name="transactionAmount"
			value="${transactionAmount}">



		<script>
            /* 3 MONTHS ONLY SHOW IN DATE PICKER */
            
            jQuery(function() {		
            	var date = new Date();
            	var currentMonth = date.getMonth();
            	var currentDate = date.getDate();
            	var currentYear = date.getFullYear();
            	
            	$('.datepicker').datepicker({
            		minDate: new Date(currentYear, currentMonth-2, currentDate),
            		maxDate: new Date(currentYear, currentMonth, currentDate+1)
            	});
            });
            
            $('.pickadate-clear-buttons').pickadate({
              close: 'Close Picker', 
            formatSubmit: 'dd/mm/yyyy',
            });
            
            /* $('.datepicker').pickadate(); */
         </script>


		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">
						<div class="table-responsive m-b-20 m-t-15" id="page-table">
							<table id="data_list_table1"
								class=" table-border-bottom table table-striped table-bordered">
								<thead>
									<tr>
										<th style="text-align: center;">Date</th>
										<th style="text-align: center;">Merchant Name</th>
										<th style="text-align: center;">Transaction Id</th>
										<th style="text-align: center;">Payment Type</th>
										<th style="text-align: right;">Request Refund(RM)</th>
										<th style="text-align: center;">Refund Status</th>
										<th style="text-align: center;">Action</th>

									</tr>


								</thead>
								<tbody>
									<c:forEach items="${paginationBean.itemList}" var="dto"
										varStatus="loop">
										<tr class="dto-${loop.index}">

											<td style="text-align: center;">${dto.initiateDate}</td>
											<td style="text-align: center;">${dto.merchantName}</td>
											<td style="text-align: center;">${dto.txnId}</td>
											<td style="text-align: center;">${dto.productType}</td>
											<td style="text-align: right;">${dto.requestRefundAmount}</td>
											<td style="text-align: center;"><c:if
													test="${dto.status == 'RIP'}">
													<img class="img-fluid"
														src="${pageContext.request.contextPath}/resourcesNew1/assets/proccessing.gif">
												</c:if> <c:if test="${dto.status == 'RS'}">
													<span style="color: #51CB49;"><b>Refunded</b></span>
												</c:if>  <c:if
													test="${dto.status == 'RI'}">
													<img class="img-fluid"
														src="${pageContext.request.contextPath}/resourcesNew1/assets/proccessing.gif">
												</c:if></td>




											<%-- 	<c:if test="${dto.status == 'RI'}">
												<td>
													<div class="input-field col s12 m6 l6 ">
														<div class="button-class">
															<button class="submitBtn"
																style="padding: 2px 10px; border-radius: 12px; border-color: #54b74a;"
																onclick="openDialog('${dto.txnId}','${dto.initiateDate}','${loop.index}')"
																type="submit">Hold</button>
														</div>
													</div>
												</td>
											</c:if>

											<c:if test="${dto.status == 'RIP'||dto.status == 'R'}">
												<td>
													<div class="input-field col s12 m6 l6 ">
														<div class="button-class">
															<button class="submitBtn"
																style="padding: 2px 10px; border-radius: 12px; border-color: #54b74a;"
																onclick="openDialog('${dto.txnId}','${dto.initiateDate}','${loop.index}')"
																type="submit" disabled>Hold</button>
														</div>
													</div>
												</td>
											</c:if> --%>

											<!-- <td>
												<div class="input-field col s12 m6 l6 ">
													<div class="button-class">
														<button class="submitBtn"
															style="padding: 2px 10px; border-radius: 12px; border-color: #54b74a;"
															type="submit">Status</button>
													</div>
												</div>
											</td> -->
											<td style="text-align: center;"><c:if
													test="${dto.status == 'RIP'}">

													<%-- 	<div class="input-field col s12 m6 l6">
														<div class="button-class" style="text-align: center;" >
															<button class="btn btn-primary blue-btn "
																style="width: 100%" type="submit"
																onclick="openDialog1('${dto.txnId}','${dto.initiateDate}','${loop.index}')">Update
																Status</button>
														</div>
													</div> --%>
													<button class="btn btn-primary  w-10" type="submit"
														onclick="openDialog1('${dto.txnId}','${dto.initiateDate}','${loop.index}')">Update
														Status</button>

												</c:if> <c:if test="${dto.status == 'RS'}">
													<button class="btn btn-primary1 w-6"
														style="opacity: 0.6; cursor: not-allowed; background-color: #80ADD4"
														!important type="submit" disabled>Updated</button>

												</c:if>
												
												<c:if test="${dto.status == 'RI'}">
													<button class="btn btn-primary1 w-6"
														style="opacity: 0.6; cursor: not-allowed; background-color: #80ADD4"
														!important type="submit" disabled>Updated</button>

												</c:if>
												</td>






										</tr>
									</c:forEach>
								</tbody>
							</table>
							<!-- Modal -->
						</div>

					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
         $(document).ready(function() {
            // $('#data_list_table').DataTable();
         } );
         
         $(document).ready(function() {
             $('#data_list_table').DataTable( {
             	"bSort" : false
             } );
         } );
         
      </script>
	<div id="pagination"></div>
	<input type="hidden" id="pgnum">
	<input type="hidden" id="FromDate">
	<input type="hidden" id="From1Date">


	<!-- <input type="hidden" id="TXNType1" > -->
	<script>
         document.getElementById("pop-bg-color").style.display ="none";
         var fromDateServer = document.getElementById("FromDate").value="${paginationBean.dateFromBackend}";
         var from1DateServer = document.getElementById("From1Date").value="${paginationBean.date1FromBackend}";
         
      //   var TransactionType = document.getElementById("TXNType1").value="${paginationBean.TXNtype}";  /*  ${paginationBean.TXNtype}*/
         
         if(${paginationBean.itemList.size()}==0){
         document.getElementById("exampleModalCenter").style.display ="block";
          document.getElementById("pop-bg-color").style.display ="block";
          document.getElementById("page-table").style.display ="none";
          document.getElementById("searchBoxDiv").style.display ="none";
                  document.getElementById("innerText").innerHTML = "Sorry, No Records Found";
                  document.getElementById("innerText").style.fontWeight ="400";
                  document.getElementById("innerText").style.color ="#171717";
                  document.getElementById("nxt").style.cursor ="not-allowed";
                  document.getElementById("nxt").style.opacity ="0.6";
                  document.getElementById("nxt").disabled ="disabled";
         }
         
         function closepopup(){
         document.getElementById("exampleModalCenter").style.display ="none";
          document.getElementById("pop-bg-color").style.display ="none";
         }
         
      </script>
	<script>
         /* * * * * * * * * * * * * * * * *
          * Pagination
          * javascript page navigation
          * * * * * * * * * * * * * * * * */
         
          
          function dynamic(pgNo){
         	/* alert("Page Number:"+pgNo); */
         	document.getElementById("pgnum").value=pgNo;
         	 loadSelectData1();
         	
         }
         
          function previous(pgNo){
         		/* alert("Page Number:"+pgNo); */
         		pgNo--;
         		document.getElementById("pgnum").value=pgNo;
         		 loadSelectData1();
         		
         	}
          
          function next(pgNo){
         		/* alert("Page Number:"+pgNo); */
         		pgNo++;
         		document.getElementById("pgnum").value=pgNo;
         		 loadSelectData1();
         	}
          
          
          
          
          
          function dynamic(pgNo){
           	/* alert("Page Number:"+pgNo); */
           	document.getElementById("pgnum").value=pgNo;
           pagination();
           	
           }
           
            function previous(pgNo){
           		/* alert("Page Number:"+pgNo); */
           		pgNo--;
           		document.getElementById("pgnum").value=pgNo;
           		pagination();
           		
           	}
            
            function next(pgNo){
           		/* alert("Page Number:"+pgNo); */
           		pgNo++;
           		document.getElementById("pgnum").value=pgNo;
           		pagination();
           	}
          
          
         var Pagination = {
         
             code: '',
         
             // --------------------
             // Utility
             // --------------------
         
             // converting initialize data
             Extend: function(data) {
                 data = data || {};
                // Pagination.size = data.size || 300; 
                 //console.log(Pagination.size);
               // Pagination.size = Math.ceil(${paginationBean.fullCount}/10) ||100;
               
                Pagination.size = ((${paginationBean.currPage})+4) ||100;
                 /* Pagination.page = data.page || 1; */
                 Pagination.page = ${paginationBean.currPage} || 1;
                 Pagination.step = ((data.step)-4) || 3;
             },
         
             // add pages by number (from [s] to [f])
             Add: function(s, f) {
                 for (var i = s; i < f; i++) {
                     Pagination.code += '<a onclick="dynamic('+i+')">' + i + '</a>';
                 }
             },
         
             // add last page with separator
           /*   Last: function() {
                 Pagination.code += '<i>...</i>';
             },
              */
         	 Last: function() {
                 Pagination.code += '<a onclick="dynamic(((Pagination.page)+1))">'+ ((Pagination.page)+1)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+2))">'+ ((Pagination.page)+2)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+3))">'+ ((Pagination.page)+3)+ '</a>'+'<i>...</i>';
             }, 
         
             // add first page with separator
             First: function() {
             	if(Pagination.page==1){
             		 Pagination.code += '<i>...</i>'+'<a onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';
         			 
         		 }
             	else{
                 Pagination.code += '<a>1</a>'+'<i>...</i>'+'<a onclick="dynamic(((Pagination.page)-1))">'+((Pagination.page)-1)+'</a>'+'<a onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';
             	}
             },
         
         
         
             // --------------------
             // Handlers
             // --------------------
         
             // change page
             Click: function() {
                 Pagination.page = +this.innerHTML;
                 Pagination.Start();
                 dynamic(page);
             },
         
             // previous page
             Prev: function() { 
             		
                 Pagination.page--;
                 if (Pagination.page < 1) {
                     Pagination.page = 1;
                 }
                 Pagination.Start();
                 dynamic(page);
              }, 
             
             
         
             // next page
             
             
             Next: function() {
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
             Bind: function() {
                 var a = Pagination.e.getElementsByTagName('a');
                 for (var i = 0; i < a.length; i++) {
                     if (+a[i].innerHTML === Pagination.page) a[i].className = 'current';
                     a[i].addEventListener('click', Pagination.Click, false);
                 }
             },
         
             // write pagination
             Finish: function() {
                 Pagination.e.innerHTML = Pagination.code;
                 Pagination.code = '';
                 Pagination.Bind();
             },
         
             // find pagination type
             Start: function() {
                 if (Pagination.size < Pagination.step * 2 + 6) {
                     Pagination.Add(1, Pagination.size + 1);
                 }
                 else if (Pagination.page < Pagination.step * 2 + 1) {
                     Pagination.Add(1, Pagination.step * 2 + 4);
                     Pagination.Last();
                 }
                 else if (Pagination.page > Pagination.size - Pagination.step * 2) {
                     Pagination.First();
                     Pagination.Add(Pagination.size - Pagination.step * 2 - 2, Pagination.size + 1);
                 }
                 else {
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
             Buttons: function(e) {
                 var nav = e.getElementsByTagName('a');
                 nav[0].addEventListener('click', Pagination.Prev, false);
                 nav[1].addEventListener('click', Pagination.Next, false);
             },
         
             // create skeleton
             Create: function(e) {
         
                 var html = [
                     '<a onclick="previous(${paginationBean.currPage})" style="width:7rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;">&#60;&#60; Previous</a>', // previous button
                     '<span></span>',  // pagination container
                     '<a onclick="next(${paginationBean.currPage})" style="width:6.5rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;" id="nxt">Next &#62;&#62;</a>'  // next button
                 ];
         
                 e.innerHTML = html.join('');
                 Pagination.e = e.getElementsByTagName('span')[0];
                 Pagination.Buttons(e);
             },
         
             // init
             Init: function(e, data) {
                 Pagination.Extend(data);
                 Pagination.Create(e);
                 Pagination.Start();
             }
         };
         
         
         
         /* * * * * * * * * * * * * * * * *
         * Initialization
         * * * * * * * * * * * * * * * * */
         
         var init = function() {
             Pagination.Init(document.getElementById('pagination'), {
                 size: 100, // pages size
                 page: 1,  // selected page
                 step: 3   // pages before and after current
             });
         };
         
         document.addEventListener('DOMContentLoaded', init, false);
      </script>
</body>
</html>