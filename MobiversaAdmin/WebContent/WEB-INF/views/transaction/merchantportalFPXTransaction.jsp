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

<%
response.setHeader("Content-Security-Policy", "default-src 'self'");
%>



<html lang="en-US">
<head>

	  <link rel="preconnect" href="https://fonts.googleapis.com">
      <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
      <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400&display=swap" rel="stylesheet">
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
	
		<!-- Script tag for Datepicker -->

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
     <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
	
</head>


<style>
#merchantName:hover {
	color: 275ca8;
}

.table-border-bottom td {
	border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important;
}

.w24 {
	width: 24px;
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
      margin-bottom:10px;
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
      -webkit-user-select:none;
      -moz-user-select:none;
      -ms-user-select:none;
      -o-user-select:none;
      user-select:none;
      }
      #pagination a {
      /* margin: 0 2px 0 2px; */ 
      margin:0 2px;
      border-radius: 1px;
      border: 1px solid #005baa;
      cursor: pointer;
      /* box-shadow: inset 0 1px 0 0 #D7D7D7, 0 1px 2px #666; */
      /* text-shadow: 0 1px 1px #FFF; */
      background-color: white;
      color: #005baa;
      height:2.3rem;
      vertical-align: middle;
      padding-top:4px;
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
      #exampleModalCenter{
      z-index:99;
      width:25%;
      font-size:24px;
      
      font-weight:400;
      font-family:'Poppins',sans-serif;
      text-align:center;
      }
      .test{
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      z-index: 10;
      background-color: rgba(0,0,0,0.5);
      }
      #close{
      color:#fff;
      background-color:#005baa;
      }
     
      #overlay_text{
      position:absolute; 
      top:50%;
      left:50%;
      font-size:50px;
      color:#FFF;
      transform:translate(-50%,-50%);
      }
      #overlay_text .img-fluid{max-width:100%;}
      #overlay_text img{
      height:150px;}
      #overlay {
      position: fixed;
      display: none;
      width: 100%;
      height: 100%;
      top: 0;
      left: 0;
      right: 0;
      bottom: 0;
      background-color: rgba(0,0,0,0.5);
      z-index: 2;
      cursor: pointer;
      }
      
      .pop-body{
      border-radius:25px;
     
      }
      .mb-0{
      padding-bottom: 0px !important;
      }

.key_hover:hover {
	cursor: pointer;
}

#agentName:hover {
	color: 275ca8;
}

.example_e1:focus {
	outline: none !important;
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

thead th {
	border-bottom: 1px solid #ffa500;
	color: #4377a2;
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




//Fpx Sales Slip - Start

/* function openFpxslip(txnId){

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
	
} */

//Fpx Sales Slip - End

// New Fpx Sales Slip - Start

	  function formatTopDate(dateString) {
		    const date = new Date(dateString);
		    const options = { day: 'numeric', month: 'long', year: 'numeric' };
		    return date.toLocaleDateString('en-GB', options);
		}

function openFpxslip(txnamount,date,time,mid,authcode,rrn,txntype,businessName,status)
{

	var parts = date.split('/');
	var formattedDate = parts[2] + '-' + parts[1] + '-' + parts[0];
	var dateTimeString = formatTopDate(formattedDate) + " " + time;
	
	var parsedDate2 = new Date(formattedDate);
	var monthNames = ["January", "February", "March", "April", "May", "June",
		  "July", "August", "September", "October", "November", "December"
		];
	var formattedDate2 = parsedDate2.getDate() + ' ' + monthNames[parsedDate2.getMonth()] + ' ' + parsedDate2.getFullYear();
	
    var modal = document.getElementById("xPay_slip-modal-id");
    modal.style.display = "block";
    var modal1 = document.getElementById("payout-slip-main-container-id");
    
    var amountWithCurrency = "MYR " + txnamount;
    var bankName = txntype.split(" - ")[1];
    
    document.getElementById("fpx_slip_amount").innerText = txnamount;
    document.getElementById("fpx_slip_amount_td").innerText = amountWithCurrency;
    document.getElementById("fpx_slip_date").innerText = dateTimeString;
  //  document.getElementById("fpx_slip_date_td").innerText = formattedDate2;
    document.getElementById("fpx_slip_mid").innerText = mid;
    document.getElementById("fpx_slip_authcode").innerText = authcode;
    document.getElementById("fpx_slip_rrn").innerText = rrn;
    document.getElementById("fpx_slip_bankname").innerText = bankName;
    document.getElementById("fpx_slip_BusName").innerText = businessName;
    // Analyze status and set appropriate message and color
    var statusElement = document.getElementById("fpx_slip_Status");
    if (status === "SETTLED") {
        statusElement.innerText = "Successful";
        statusElement.style.color = "var(--success-title)"; // Use your success color variable here
    } else if (status === "VOIDED") {
        statusElement.innerText = "Voided";
        statusElement.style.color = "#D9AA00"; // Refunded status color
    }else if (status === "REFUNDED") {
        statusElement.innerText = "Voided";
        statusElement.style.color = "#D9AA00"; // Refunded status color
    } else {
        statusElement.innerText = "Successful";
        statusElement.style.color = "var(--success-title)"; // Default to success color for any other status
    }
    
    
    //close when clicked outside
    window.onclick = function (event) {
        if (event.target === modal || event.target === modal1) {
            closeXpayModal();
        }
    };

    };


    function closeXpayModal() {
        body.style.overflow = initialOverflow;
        var modal = document.getElementById("xPay_slip-modal-id");
        modal.style.display = "none";

    }

 // New Fpx Sales Slip - end

    </script>


<script lang="JavaScript">
	function loadSelectData() {
		$("#overlay").show();
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		//var e2 = document.getElementById("txnType").value;
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
			$("#overlay").hide();
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			//var TxnType = document.getElementById("txnType").value;
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchFpxTxnSummary?date='
					+ fromdateString + '&date1=' + todateString + '&currPage=' + PageNumber;
					
			/* Updated code...... */
			localStorage.setItem("fromDate", e);     
			localStorage.setItem("toDate",e1);
			
			form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

		}
	}
	
	/* Updated code..... */
	// event listeners
	  window.addEventListener('load', function() {     
		var fromDate = localStorage.getItem("fromDate");     
		var toDate = localStorage.getItem("toDate");    
		
		if(fromDate && toDate){
		
			document.getElementById("from").value = fromDate;
			document.getElementById("to").value = toDate;
	
	 		
	 		document.getElementById("datef").style.transform = "translateY(-14px) scale(0.8)";
			document.getElementById("datet").style.transform = "translateY(-14px) scale(0.8)";
	 	}
	 		
		localStorage.removeItem("fromDate");
		localStorage.removeItem("toDate");
		//localStorage.clear();
	});  
	
	function loadSelectData1() {
		$("#overlay").show();
		var date = document.getElementById("from").value;
		var date1 = document.getElementById("to").value;
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("FromDate").value;
		var e1 = document.getElementById("From1Date").value;
		//var e2 = document.getElementById("txnType").value;
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
		} else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			//var TxnType = document.getElementById("txnType").value;
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchFpxTxnSummary?date='
					+ fromdateString + '&date1=' + todateString + '&currPage=' + PageNumber;
			/* Updated code...... */
			localStorage.setItem("fromDate", date);     
			localStorage.setItem("toDate",date1);		
			form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

		}
	}
	
	 function loadSearch()
     {
   		$("#overlay").show();
   		var Value = document.getElementById("searchApi").value;
   		var TXNTYPE = 'FPX1';
   		var type = document.getElementById("drop_val").value;
   		
   		if(Value.trim() === '' || type.trim() === ''){
   	 		alert("Please choose a value before submitting");
   	 		$("#overlay").hide();
           return;
    	}
   		
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
      		document.getElementsByName('search')[0].placeholder = 'Ex: SellerOrderNo';
      	  } else if (strUser == "Ap_Code") {
      		  document.getElementsByName('search')[0].placeholder = 'Ex: FpxTxnID';
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
		if (e == null ||e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			/* alert("inside else"); */
			document.getElementById("datevalex1").value = fromdateString;
			document.getElementById("datevalex2").value = todateString;
			
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/exportFpxTxnSummary?fromDate=' +fromdateString
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
/* 
	function loadDropDate14() {
		//alert("loadDropDate13");
		var e = document.getElementById("txnType");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("txnType1").value = strUser;
		//alert("txntype: "+strUser);
		//document.getElementById("searchTxnType").value=strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	} */
/* function checkTxnType()
{
//alert("check"+document.getElementById("txnType1").value);
var txnType=document.getElementById("txnType1").value;
if(txnType=="Choose" || txnType=='')
{
alert("please select txnType field..");
return false;
}

} */

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

	

	//Script by Denis Gritcyuk: tspicker@yahoo.com
	//Submitted to JavaScript Kit (http://javascriptkit.com)
	//Visit http://javascriptkit.com for this script
	
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
</script>
<body class="">


	
      <div id="overlay">
         <div id="overlay_text"><img class="img-fluid" src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif"></div>
      </div>
      <div class="test" id="pop-bg-color"></div>
      <div class="container-fluid mb-0" id="pop-bg">
		<div class="row">



			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>FPX Transaction Summary</strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>


		  <div class="approve-modal-class" id="confirmation-modal-IPN-id">
			  <input type="hidden" id="invoice-for-approve">
			  <input type="hidden" id="mid-for-approve">
			  <input type="hidden" id="tid-for-approve">

<%--			  <div class="approve-modal-content">--%>
<%--				  <div>--%>
<%--					  <div style="border-bottom: 2.5px solid orange; padding: 10px 24px; text-align: center; font-size: 17px; color: #005baa; font-weight: 600">--%>
<%--						  Confirmation--%>
<%--					  </div>--%>

<%--					  <div class="reason-div" style="padding: 15px 30px 0px 30px; text-align: center;">--%>
<%--						  <img src="${pageContext.request.contextPath}/resourcesNew1/assets/confirmation.svg" width="40"--%>
<%--							   height="40"/>--%>
<%--						  &lt;%&ndash;                    <p id="reason-id" class="reason-class"></p>&ndash;%&gt;--%>
<%--					  </div>--%>
<%--					  <div style="padding: 0 30px; text-align: center;">--%>
<%--						  <p style="--%>
<%--                    display: flex;--%>
<%--                    justify-content: center;--%>
<%--                    align-items: center;--%>
<%--                    margin-bottom: 30px; font-size: 18px; color: #858585">Are You sure want to send IPN to this Transaction</p>--%>
<%--					  </div>--%>
<%--					  <div class="buttonsHorizontally">--%>

<%--					  </div>--%>

<%--					  <div class="approve-button"--%>
<%--						   style="padding: 10px; display: flex; justify-content: center; background-color: #EFF8FF;gap: 18px; border-bottom-left-radius: 15px; border-bottom-right-radius: 15px;">--%>
<%--						  <button type="button" class="close-Approve" id="approval_confirmation" onclick="close_aproval()"--%>
<%--								  style="background-color: transparent !important; font-family: 'Poppins';color: #005baa !important;border-style: solid !important; font-weight: 400;border-width: 1px !important;">--%>
<%--							  Cancel--%>
<%--						  </button>--%>
<%--						  <button--%>
<%--								  type="button"--%>
<%--								  class="close-Approve"--%>
<%--								  id="approval_cancel" style="font-family: 'Poppins'; font-weight: 400"--%>
<%--								  onclick="sendFpxIpn();">--%>
<%--							  Confirm--%>
<%--						  </button>--%>
<%--						  </button>--%>
<%--					  </div>--%>
<%--				  </div>--%>
<%--			  </div>--%>

		  </div>

  <div class="modal fade bd-example-modal-xl pop-body" style="width:500px !important;height:270px !important;" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" style="text-align:center;">
            <div class="modal-dialog modal-xl" >
               <div class="modal-content " style="padding:0 !important;margin:0 !important;" >
                  <p class="pop-head" style="background-color:#f9f9f9;width:100%;height:60px;color:#005baa;padding-top:12px;border-bottom: 2px solid #ffa500;">Information</p>
                  <img src="${pageContext.request.contextPath}/resourcesNew1/assets/NoRecordPNG.png" width="60px !important; height:60px !important;">
                  <p id="innerText" style="font-size:22px;"></p>
                  <div class="modal-footer"><button type="button" class="btn btn-secondary" data-dismiss="modal" id="close" onclick="closepopup()" style="width:106px !important; height:38px !important;font-size:18px;border-radius:50px !important;margin-right:187px !important;letter-spacing:0.8px;font-family:'Poppins',sans-serif;font-weight:medium;transform:translateY(-10px);">Close</button></div>
               </div>
            </div>
         </div>

		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">

						<div class="row">
							<div class="input-field col s12 m3 l3">
								<label id = "datef" for="from" style="margin: 0px;">From </label> <input
									type="hidden" name="date11" id="date11"
									<c:out value="${fromDate}"/>> <input type="text"
									id="from" name="fromDate" class="validate datepicker"
									onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
								<i class="material-icons prefix">date_range</i>


							</div>

							<div class="input-field col s12 m3 l3">

								<label id = "datet" for="to" style="margin: 0px;">To</label> <input
									type="hidden" name="date12" id="date12"
									<c:out value="${toDate}"/>> <input id="to" type="text"
									name="toDate" class="datepicker"
									onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
								<i class="material-icons prefix">date_range</i>
							</div>

							<div class="input-field col s12 m3 l3">
								<input type="hidden" name="export1" id="export1"
									<c:out value="${status}"/>> <select name="export"
									id="export" onchange="return loadDropDate13();">
									<option selected value="">Choose</option>
									<option value="PDF">PDF</option>
									<option value="EXCEL">CSV</option>
								</select> <label class="control-label">Export Type</label>
							</div>

							<div class="input-field col s12 m3 l3">
								<div class="button-class" style="float: left;">

									<input type="hidden" name="date1" id="dateval1"> <input
										type="hidden" name="date2" id="dateval2">
									<button class="btn btn-primary icon-btn" type="button"
										onclick="return loadSelectData();">Search</button>


									<input type="hidden" name="dateex1" id="datevalex1"> <input
										type="hidden" name="dateex2" id="datevalex2">
									<button class="btn btn-primary icon-btn" type="button"
										onclick="return loadExpData();">Export</button>
								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>

		<script>
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


</script>

<!-- MAIN DIV SEARCH TEST -->
      <div class="row" id="searchBoxDiv">
         <div class="col s12">
            <div class="card blue-bg text-white">
               <div class="card-content">
                  
                      <!-- SEARCH TEST -->
                        <div class="row" style="display:flex;align-items:center;justify-content:space-between;margin-left:01%;">
                           <div class="col s12">
                           <div class="input-field col s12 m3 l3" style="font-family:'Poppins',sans-serif;width:30%;">
                            <select name="drop_search"
                           id="drop_search" onchange="return loadDropSearch();">
                           <option selected value="" id="choose">Choose Type</option>
                            <option value="Ref">Reference No</option>
                           <option value="Ap_Code">Approval Code</option>
                            </select>
                            <input type="hidden" id="drop_val">
                            </div>
                        
                              <div class="input-field col s12 m3 l3" style="margin-left:07%;width:30%;">
                                 <input type="text" id="searchApi" name="search" class="" style="font-family:'Poppins',sans-serif;" placeholder="Please select type to search">
                              </div>
                              <div class="input-field col s12 m3 l3" style="width:10%;margin-left:07%;">
                                 <div class="button-class" style="float:left;" >
                                    <button class="btn btn-primary blue-btn" type="button" onclick="loadSearch()" style="font-family:'Poppins',sans-serif;width:100%;font-size:14px;">Search</button>
                                 </div>
                              </div>
                           </div>
                           </div>
                           <!--  SEARCH TEST ENDS -->   
               </div>
            </div>
         </div>
      </div>
      
       <!-- MAIN SEARCH TEST ENDS -->
      
	<!--  new slip fpx -->

		<div id="xPay_slip-modal-id" class="slip-modal-class">
			<section class="payout-slip-main-container poppins-regular"
				id="payout-slip-main-container-id">
				<div class="slip-card" id="slip-card-id">
					<div class="title-logo" style="position: relative">

						<img
							src="${pageContext.request.contextPath}/resourcesNew1/assets/mobi_logo.svg"
							width="55" height="55" /> <img
							style="position: absolute; top: 3px; right: 10px; height: 20px ! important; width: 20px !important; cursor: pointer;"
							src="${pageContext.request.contextPath}/resourcesNew1/assets/xmark.svg"
							width="35" height="35" onclick="closeXpayModal()" />
					</div>

					<hr class="horizontal-line">
					<!-- Second Part - Status and Time stamp -->
					<div class="main-status poppins-semibold">
						<!-- comment this below for Failure status -->
						<p id="fpx_slip_Status" class="status status-success">${dto.STATUS}</p>

						<!-- Uncomment this for Failure status -->
						<!-- <p class="status status-failure">Failed</p> -->
						<div class="status-container">
							<p class="sub-head">Transaction Summary</p>
							<p class="amount poppins-regular">
								MYR <span class="poppins-semibold amount-value"
									id="fpx_slip_amount"></span>
							</p>
							<p class="time-stamp poppins-semibold" id="fpx_slip_date"></p>
							<hr class="horizontal-default">
						</div>
					</div>
					<!-- Third Part - Transaction details area  -->
					<div class="transaction-details">
						<table>

							<tr class="no_border_bottom">
								<th class="poppins-regular xpay_slip_whiteSpace">Paid To</th>
								<td class="poppins-medium xpay_slip_wordBreak"
									style="text-transform: uppercase;" id="fpx_slip_BusName"></td>
							</tr>
							<tr class="no_border_bottom">
								<th class="poppins-regular xpay_slip_whiteSpace">Order ID</th>
								<td class="poppins-medium xpay_slip_wordBreak" id="fpx_slip_rrn"></td>
							</tr>
							<tr class="no_border_bottom">
								<th class="poppins-regular xpay_slip_whiteSpace">Transaction
									ID</th>
								<td class="poppins-medium xpay_slip_wordBreak"
									id="fpx_slip_authcode"></td>
							</tr>


							<tr class="no_border_bottom">
								<th class="poppins-regular xpay_slip_whiteSpace">MID</th>
								<td class="poppins-medium xpay_slip_wordBreak"
									style="text-transform: uppercase;" id="fpx_slip_mid"></td>
							</tr>
							<!-- <tr class="no_border_bottom">
								<th class="poppins-regular xpay_slip_whiteSpace">When</th>
								<td class="poppins-medium xpay_slip_wordBreak"
									id="fpx_slip_date_td"></td>
							</tr> -->
							<tr class="no_border_bottom">
								<th class="poppins-regular xpay_slip_whiteSpace">Payment
									Method</th>
								<td class="poppins-medium xpay_slip_wordBreak">FPX Bank
									Transfer</td>
							</tr>

							<tr class="no_border_bottom">
								<th class="poppins-regular xpay_slip_whiteSpace">Bank Name</th>
								<td class="poppins-medium xpay_slip_wordBreak" id="fpx_slip_bankname">
									</td>
							</tr>

						</table>
						<div class="bill-box-container">
							<div class="poppins-medium">Transfer Amount</div>
							<div class="poppins-semibold"
								style="font-size: 1rem; color: var(--value-color);"
								id="fpx_slip_amount_td"></div>
						</div>
					</div>
					<hr class="horizontal-default">
					<div class="notes-section">
						<strong>Note</strong>
						<p class="notes">
							This receipt is computer generated and no signature is required.
							For support, contact  <a href="mailto:csmobi@gomobi.io" style="color: #005BAA; text-decoration: underline;">csmobi@gomobi.io</a>
						</p>

					</div>
				</div>
			</section>
		</div>


		<style>

.slip-modal-class {
	display: none;
	position: fixed;
	z-index: 1000;
	left: 0;
	top: 0%;
	width: 100%;
	height: 100%;
	overflow: auto;
	scrollbar-width: none;
	background-color: rgba(0, 0, 0, 0.4);
}

@import
	url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap')
	;

* {
	box-sizing: border-box;
	margin: 0;
	padding: 0;
}

:root {
	--success-title: #36B37E;
	--failure-title: #EC3E3E;
	--card-bg: #F6F6F6;
	--hr-line: #eeeeee;
	--title-color: #333739;
	--value-color: #2D2D2D;
	--bill-box: #ECECEC;
}

.no_border_bottom {
	border-bottom: none !important;
}

/* FONT STYLE CSS  */
.poppins-light {
	font-family: "Poppins", sans-serif;
	font-weight: 300;
}

.poppins-regular {
	font-family: "Poppins", sans-serif;
	font-weight: 400;
}

.xpay_slip_wordBreak {
	word-break: break-all !important;
}

.xpay_slip_whiteSpace {
	white-space: nowrap !important;
	vertical-align: baseline !important;
}

.poppins-medium {
	font-family: "Poppins", sans-serif;
	font-weight: 500;
}

.poppins-semibold {
	font-family: "Poppins", sans-serif;
	font-weight: 600;
}

.poppins-bold {
	font-family: "Poppins", sans-serif;
	font-weight: 700;
}

/* Payout Slip Container main box  */
.payout-slip-main-container {
	display: flex;
	justify-content: center;
	align-items: center;
	min-height: 100vh;
}

.slip-card {
	width: 100%;
	max-width: 24rem;
	background-color: var(--card-bg);
	border-radius: 0.5rem;
	overflow: hidden;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.title-logo {
	margin: 0.5rem auto 0.5rem auto;
	text-align: center;
}

.title-logo img {
	max-width: 100%;
	height: 1.625rem;
}

.horizontal-line {
	height: 0.3rem;
	background-color: var(--hr-line);
	border: none;
	margin: 0 auto 1rem auto;
}

.horizontal-default {
	width: 90%;
	height: 1px;
	background-color: var(--hr-line);
	color: var(--hr-line);
	margin: 0.5rem auto 0 auto;
	opacity: 0.5;
}

.main-status {
	text-align: center;
	color: var(--value-color);
}

.status {
	font-size: 1.3rem;
	margin-top: 0.25rem;
}

.status-success {
	color: var(--success-title);
}

.status-refunded {
	color: #D9AA00;
}

.status-failure {
	color: var(--failure-title);
}

.status-container {
	padding: 0 0.5rem;
}

.sub-head {
	font-size: 0.7rem;
	margin-top: 0.75rem;
}

.amount {
	font-size: 1.4rem;
	color: var(--title-color);
}

.amount-value {
	color: var(--value-color);
}

.time-stamp {
	font-size: 0.7rem;
	margin-top: 0.1rem;
}

.transaction-details {
	padding: 0 1.25rem;
}

.transaction-details table {
	width: 100%;
	border-collapse: collapse;
}

.transaction-details th {
	color: #333739;
}

.transaction-details th, .transaction-details td {
	padding: 0.85rem 0.3rem;
	text-align: left;
	font-size: 0.8rem;
}

.transaction-details td {
	text-align: right;
	text-wrap: wrap;
	font-weight: 600;
	color: #2D2D2D;
}

.bill-box-container {
	background-color: var(--bill-box);
	border-radius: 10px;
	font-size: 0.8rem;
	display: flex;
	justify-content: space-between;
	align-items: center;
	padding: 0.7rem 1.5rem;
	margin-top: 0.5rem;
	color: var(--title-color);
}

.notes-section {
	padding: 0.5rem 1.5rem;
	font-size: 0.8rem;
	color: var(--title-color);
	margin-bottom: 0.5rem;
}

.notes {
	font-size: 0.75rem;
}

/* Media Queries for Responsive Design */
@media ( max-width : 768px) {
	.slip-card {
		padding: 0;
	}
	.status {
		font-size: 1.1rem;
	}
	.amount {
		font-size: 1.2rem;
	}
}

@media ( max-width : 480px) {
	.slip-card {
		padding: 0;
	}
	.status {
		font-size: 1rem;
	}
	.amount {
		font-size: 1rem;
	}
}
</style>



		<!--  new slip fpx end-->



		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">

						<div class="table-responsive m-b-20 m-t-15" id="page-table">
							<table id="data_list_table1"
								class=" table-border-bottom table table-striped table-bordered">
								<thead>
									<tr>
										<th>Date</th>
										<th>Time</th>
										<th>MID</th>
										<th>TID</th>
										<th>Amount(RM)</th>
										<th style="text-align:center;">PPID</th>
										<th>Reference No</th>
										<th>Approval Code</th>
										<th>RRN</th> 
										<th>Status</th>
										<th>Payment Method</th>
										<th>MDR Amount</th>
										<th>Net Amount</th>
										<th>Payment Date</th>
										<th>EZYSETTLE Amount</th>
										<th>PREAUTH Fee</th>
										<th>Sales Slip</th>
										<th>Sub Merchant MID</th>
										<th>Void</th>
										<th>Refund</th>
<%--										<c:if test="${loginname == 'pcitest'}">--%>
<%--											<th>IPN</th>--%>
<%--										</c:if>--%>

									</tr>
								</thead>
								<tbody>

									<c:forEach items="${paginationBean.itemList}" var="dto"
										varStatus="id">
										<tr>
											<td>${dto.date}</td>
											<td>${dto.time}</td>
											<td>${dto.f001_MID}</td>
											<td>${dto.f354_TID}</td>
											<td style="text-align: right;">${dto.f007_TXNAMT}</td>
											<td>${dto.ppid}</td>
											<td>${dto.f270_ORN}</td>
											<td class='key_hover'><span
												onclick="show_key('${id.index}')" id="hide_key_${id.index}"><img
													class="w24" src='data:image/png;base64,<%=eyeimg%> ' /> </span> <span
												onclick="hide_key('${id.index}')" id="show_key_${id.index}"
												class="hide_key">${dto.f011_AUTHIDRESP}</span></td>
											<td class='key_hover'><span
												onclick="show_rrn('${id.index}')" id="hide_rrn_${id.index}"><img
													class="w24" src='data:image/png;base64,<%=eyeimg%> ' /> </span> <span
												onclick="hide_rrn('${id.index}')" id="show_rrn_${id.index}"
												class="hide_key">${dto.f023_RRN}</span></td> 
											<td>${dto.STATUS}</td>
											<td>${dto.cardType}</td>
											<td style="text-align: right;">${dto.mdrAmt}</td>
											<td style="text-align: right;">${dto.netAmount}</td>
											<td>${dto.settlementDate}</td>
											<td>${dto.ezysettleAmt}</td>
											<td>${dto.preauthfee}</td>
											

											<td align="center"><c:if
													test="${dto.cardType == 'MASTERCARD DEBIT' || dto.cardType == 'MASTERCARD CREDIT' || dto.cardType == 'VISA DEBIT' || dto.cardType == 'VISA CREDIT' }">
													<c:if
														test="${dto.STATUS =='SETTLED' || dto.STATUS =='NOT SETTLED' || dto.STATUS =='VOIDED'}">
														<a href="javascript:void(0)" id="openNewWin"
															onclick="javascript: openNewWin('${dto.f263_MRN}')">
															<img class="w24"
															src='data:image/png;base64,<%=actionimg%> ' />
														</a>

													</c:if>
												</c:if> <c:if test="${dto.cardType == 'BOOST'}">
													<c:if
														test="${dto.STATUS =='SETTLED' || dto.STATUS =='NOT SETTLED' || dto.STATUS =='VOIDED'}">
														<a href="javascript:void(0)" id="openBoostslip"
															onclick="javascript: openBoostslip('${dto.f023_RRN}')">
															<img class="w24"
															src='data:image/png;base64,<%=actionimg%> ' />
														</a>

													</c:if>
												</c:if> <c:if test="${dto.cardType == 'GRABPAY'}">
													<c:if
														test="${dto.STATUS =='SETTLED' || dto.STATUS =='NOT SETTLED' || dto.STATUS =='VOIDED'}">
														<a href="javascript:void(0)" id="openGrabpayslip"
															onclick="javascript: openGrabpayslip('${dto.f023_RRN}')">
															<img class="w24"
															src='data:image/png;base64,<%=actionimg%> ' />
														</a>

													</c:if>
												</c:if>
												
												<c:if test="${dto.cardType == 'TNG'}">
													<c:if
														test="${dto.STATUS =='SETTLED' || dto.STATUS =='NOT SETTLED'}">
														<a href="javascript:void(0)" id="openTngslip"
															onclick="javascript: openTngpayslip('${dto.f263_MRN}')">
															<img class="w24"
															src='data:image/png;base64,<%=actionimg%> ' />
														</a>

													</c:if>
												</c:if>
												
												
												
												<c:if test="${dto.cardType == 'SPP'}">
													<c:if
														test="${dto.STATUS =='SETTLED' || dto.STATUS =='NOT SETTLED'}">
														<a href="javascript:void(0)" id="openShopeeslip"
															onclick="javascript: openShopeepayslip('${dto.f263_MRN}')">
															<img class="w24"
															src='data:image/png;base64,<%=actionimg%> ' />
														</a>

													</c:if>
												</c:if>
												
												 <c:set var="fpx" value="${dto.cardType}"/>
												<c:if
													test="${fn:contains(fpx, 'FPX')}">
													<c:if
														test="${dto.STATUS =='SETTLED' || dto.STATUS =='NOT SETTLED' || dto.STATUS =='VOIDED' || dto.STATUS =='REFUNDED'}">
														<a href="javascript:void(0)" id="openFpxslip"
															onclick="javascript: openFpxslip(
															'${dto.f007_TXNAMT}',
															'${dto.date}',
															'${dto.time}',
															'${dto.f001_MID}',
															'${dto.f011_AUTHIDRESP}',
															'${dto.f270_ORN}',
															'${dto.cardType}',
															'${merchantName}',
															'${dto.STATUS}'		
															
															)">
																<img class="w24"
															src="${pageContext.request.contextPath}/resourcesNew1/assets/salesSlip.svg" />
														</a>

													</c:if>
												</c:if>



												<div class="form-group col-md-4" id="divviewer"
													style="display: none;">
													<div class="form-group">
														<div style="clear: both">
															<iframe id="popOutiFrame" frameborder="0" scrolling="no"
																width="800" height="600"></iframe>

														</div>

													</div>
												</div></td>
												
												<td>${dto.submerchantmid}</td>

											<td style="text-align: center;"><c:if
													test="${dto.cardType == 'MASTERCARD DEBIT' || dto.cardType == 'MASTERCARD CREDIT' || dto.cardType == 'VISA DEBIT' || dto.cardType == 'VISA CREDIT' }">
													<c:if test="${dto.STATUS =='NOT SETTLED' && dto.txnType !='AUTHSALE'}">
														<a
															href="${pageContext.request.contextPath}/transactionUmweb/cancelPayment1/${dto.f263_MRN}">
															<img class="w24"
															src='data:image/png;base64,<%=voidimg%> ' />
														</a>
													</c:if>
												</c:if></td>


											<td style="text-align: center;">
												<c:if test="${dto.cardType == 'MASTERCARD DEBIT' || dto.cardType == 'MASTERCARD CREDIT' || dto.cardType == 'VISA DEBIT' || dto.cardType == 'VISA CREDIT'}">
													<c:if test="${dto.STATUS =='SETTLED'}">
													<a
															href="${pageContext.request.contextPath}/transactionUmweb/refundEzywayPayment/${dto.f263_MRN}">
														<img class="w24"
															 src='data:image/png;base64,<%=refundimg%> '/>
													</a>
													</c:if>
												</c:if>
											</td>
<%--											<c:if test="${loginname == 'pcitest'}">--%>
<%--											<td style="cursor: pointer">--%>
<%--												<img class="w24" onclick="openIpnApprovalModal('${dto.f001_MID}','${dto.f354_TID}','${dto.f270_ORN}')"--%>
<%--													 src="data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjAiIGhlaWdodD0iMjAiIHZpZXdCb3g9IjAgMCAyMCAyMCIgZmlsbD0ibm9uZSIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayI+CjxtYXNrIGlkPSJtYXNrMF8xMzE2OV84MTA0IiBzdHlsZT0ibWFzay10eXBlOmFscGhhIiBtYXNrVW5pdHM9InVzZXJTcGFjZU9uVXNlIiB4PSIyIiB5PSIyIiB3aWR0aD0iMTYiIGhlaWdodD0iMTYiPgo8cmVjdCB4PSIyIiB5PSIyIiB3aWR0aD0iMTYiIGhlaWdodD0iMTYiIGZpbGw9InVybCgjcGF0dGVybjBfMTMxNjlfODEwNCkiLz4KPC9tYXNrPgo8ZyBtYXNrPSJ1cmwoI21hc2swXzEzMTY5XzgxMDQpIj4KPHJlY3QgeD0iMiIgeT0iMiIgd2lkdGg9IjE2IiBoZWlnaHQ9IjE2IiBmaWxsPSIjMDA1QkFBIi8+CjwvZz4KPGRlZnM+CjxwYXR0ZXJuIGlkPSJwYXR0ZXJuMF8xMzE2OV84MTA0IiBwYXR0ZXJuQ29udGVudFVuaXRzPSJvYmplY3RCb3VuZGluZ0JveCIgd2lkdGg9IjEiIGhlaWdodD0iMSI+Cjx1c2UgeGxpbms6aHJlZj0iI2ltYWdlMF8xMzE2OV84MTA0IiB0cmFuc2Zvcm09InNjYWxlKDAuMDAxOTUzMTIpIi8+CjwvcGF0dGVybj4KPGltYWdlIGlkPSJpbWFnZTBfMTMxNjlfODEwNCIgd2lkdGg9IjUxMiIgaGVpZ2h0PSI1MTIiIHhsaW5rOmhyZWY9ImRhdGE6aW1hZ2UvcG5nO2Jhc2U2NCxpVkJPUncwS0dnb0FBQUFOU1VoRVVnQUFBZ0FBQUFJQUNBWUFBQUQwZU5UNkFBQUFDWEJJV1hNQUFBN0RBQUFPd3dISGI2aGtBQUFBR1hSRldIUlRiMlowZDJGeVpRQjNkM2N1YVc1cmMyTmhjR1V1YjNKbm0rNDhHZ0FBSUFCSlJFRlVlSnp0M1hlOFhWV1ovL0hQdlRlRlZGSWdvWVJFQ0Vrd0tMMUxGNlFJSWd4WVFYRkVCQVZ4TENPT2ZkUVp4K0duWUJ0UmJJeGpBVHRpQVFRQjZTQzk5NEJBQ0ttazNkeVMzeC9yWGtodWJqbGw3ZjJzOWV6disvVjZYdWtuejNuV1BtZXZ2ZmJhYTdVZ0lxa2JCbXphRTFPQkNjREVuaC9YalRIQTZKNGZSd0FiQTIwOWY3YXVzY0R3UHIvWEFTenY4M3RMZ0U1Z0diQUdXQUdzN1BseFNaOVkzUFBqZkdBQjhEelExZkE3RnBIQ3RWZ25JRkp4azRCcHdQU2VtTllUV3dKVGVtSlQ4dnVzcnVYbGpzQjg0Qm5nS2VEcG5oL245Zng4a1ZXQ0lsV1gyNWVLU0c1YUNDZjBiZnZFeko0Zng5aWxsb1Rsd0tQQUkzM2lVVUlIWWExZGFpSytxUU1nRXM4V3dGeGcrM1YrM0FFWVo1bFV4dG9KSFlGN2dmdlcrZkYrb05zd0x4RVgxQUVRcWQ4d1lBNndhMC9NQlhZaERPZEw4WllEZC9KeWgrQTI0RlpndFdWU0lybFJCMEJrY0MzQUxHRFBkZUxWd0VqTHBHUUQ3Y0Jkd00zQVRjQ053TU9tR1lra1RoMEFrZldOQnZZQzl1UGxFNzZ1N1BPMGtOQVp1QW00cHVmSFZhWVppU1JFSFFDcHVqSEEzc0Mrd0dzSUozNWQzZnZVU2JoMWNBVndIWEF0NGRGRmtVcFNCMENxWmhqaEN2L1FudGk5NS9la2Vqb0pvd0tYOThUTlBiOG5VZ25xQUVnVnpBUU9KNXp3RHdMRzI2WWppVm9LWEVYb0RQd1JlTncySFpGaXFRTWdIclVCT3dGSEEwY1JadXFMMU9zeDRQZkFKY0RWaE5VU1JkeFFCMEM4R0FNY0FieVJjTFUvMlRZZGNlWUZ3cWpBYjN0K1hHbWJqa2p6MUFHUW5JMEdYZ3VjQUJ4TFdPTmVwR2lyQ1JNSkx5WjBDSmJhcGlQU0dIVUFKRGZqQ0NmN053R0hvQm43WXF1ZE1HZmc1OEJ2MkhCREpaRmtxUU1nT1JnQkhJYXU5Q1Z0dlNNREZ4SkdCdGJZcGlNeU9IVUFKRlV0aE9meTN3RWNUOWorVmlRWGl3aTNDUDZYc09hQVNITFVBWkRVYkU0WTNuODNZY2xka2R3OUJQd1UrQUh3cEhFdUlpOVJCMEJTTUFKNEEvQXV3bEIvbTIwNklvWG9BdjVFNkFqOERqMVdLTWJVQVJCTFd3QW5BV2NBMDR4ekVTblRmT0NId0xlQkowd3prY3BTQjBESzFnb2NESnhLbU5DblpYaWx5cnFCSzRIdkFMOGlqQktJbEVJZEFDbkxlTUo5L1RPQWJZeHpFVW5SbzhDNWhGc0VLNHh6a1FwUUIwQ0t0ZzNoYXYrOXdBVGpYRVJ5c0l4d2UrREx3RDlzVXhFUnFkOEJoR2VodTRDMUNvV2k3bGdOWEFETVJVUWtjUzJFRFhpdXcvN0xVNkh3Rkg4amZMWTBhaXNpU1JsT1dMRG5YdXkvS0JVS3ozRTc0Yk0ySEJFUlF5T0E5d0ZQWWYvRnFGQlVLWjRFVGlOOEJrVkVTdE43eGY4STlsK0VDa1dWNDBuQ0pGczlUaXNpaFdvbGJNanpFUFpmZkFxRjR1VjRuTkFSMENxYUloSlY3K1MrTzdEL29sTW9GQVBIZllUUk9YVUVSS1JwaHdDM1lmL0ZwbEFvYW85N0NLTjFlbXBBUk9wMkpIQXI5bDlrQ29XaThiZ1pPQnlSZnFoM0tIM3RBdncvNEVEalBFUWtuaXVCZndIdXNrNUUwdEZxbllBa1kwdmdmTUlWdzRHMnFZaElaQWNUMWhDNEVKaGluSXNrUWhORlpCendhZUFud0Y2b1V5amlWUXV3STJGVHJ0V0V1VDNkcGhtSktkMENxTGFqZ1c4Q1cxa25JaUtsZXdqNEVIQ3BkU0ppUXgyQWF0b1IrRHF3bjNVaUltTHVDdUNEaEtXOHBVSTAzRnN0RTRIekNFTi9Pdm1MQ0lSSGZXOG5mRGRveSs0SzBRaEFOYlFRRmdjNUI5akVPQmNSU2RmendGbkF6NndUa2VLcEErRGZ0c0MzZ0VPdEV4R1JiUHdCT0IyWVo1MklGRWRQQWZnMUhQZ29jQkV3eHpnWEVjbkxMT0M5UUJkd0kyRlJJWEZHSXdBKzdRdGNnRTc4SXRLODY0RDNBUGRiSnlKeGFRVEFsMUhBRjREdkFKc2E1eUlpUGt3bjdEUTRCcmlXTUNvZ0RtZ0V3STk5Z2U4QnM2MFRFUkczN2dWT0lkd1drTXpwTWNEOGpTVk04cnNHbmZ4RnBGamJFMjRKbkVjWUVaQ01hUVFnYjdzRFAwWW5maEVwMytQQWljRDExb2xJWXpRSElFK3R3QWVBbjZLTlBVVEV4a1RnbllUenlMWG9TWUhzYUFRZ1A5TUpPM29kWUoySWlFaVBHd2lqQVk5Wkp5SzEweHlBdkJ4UFdMSlRKMzhSU2NuZXdOK0J0MXNuSXJWVEJ5QVA0NEh6Z1l1QlNjYTVpSWowWjJQQ25LU0wwSjRDV2RBdGdQVHRTZmhRYld1ZGlJaElqWjRBVGdMK1pweUhERUtUQU5NMURQZzA4RU8wZ1krSTVHVUNZUU15Q0k4TmFvSmdnalFDa0taTkNEUDhEN0ZPUkVTa1NWY0Rid2JtV3ljaTY5TWNnUFRzU1pqb3A1Ty9GTzNyd0FyckpNUzlBNENiZ2Qyc0U1SDE2UlpBV3Q0Qi9BS1liSjJJdUhjdmNCelFnVHFiVXJ5TkNXc0d6Q2M4TFNBaVBVWUMzeVhjSjFNb2lvNXU0Q0NDWWNDZENlU2txRTVjU05pNFRLVHlwdkh5ZnRzS1JSbnhROWEzTDZGVFlKMlhvanB4Ry9BS3hKUnVBZGc2RUxnTW1HT2NoMVRIWXVBWTFyLzNQdy9ZQnRqUkpDT3BvczBKS3dmZWdWWVBOS05KZ0RaYWdMT0F5NEdweHJsSXRmd2I4SHcvdi84aDRJV1NjNUZxMndUNEkvQXg5RVNhVk1RWTRKZllEOEVwcWhjM00zaW4vNzBKNUtpb1psd0VqRVpLcFY1WHVUWURMa0dQdzRpTi9SaDhaYlpXNEJaZ2wzTFNFVm5QemNBYjBIb0JwZEV0Z1BKc1Q1anNwNU8vV0xpSW9aZGw3UVkrVWtJdUl2M1pnN0NyNEN1dEU2a0tUUUlzeDhIQW53a2pBSktHYnNJS1pjOENXeG5uVXJRMWhHZitGOWZ3ZDU4QWRnZG1GNWxRQWw0RS9vWHdPTnAwZERHVWlvbkEyNENiQ01laVNOYmVDYlJqZjQ5TkVlSmU0TE9FV2UvRGdhY1N5S25vT0lmNmJFZm9ORmpuWFhUMHJsVS9HVGlWTUVLaXh5SFRpSGJDWmtJaVdXb2huR2owaFdJZjg0RHpnSjM3dE5GSkNlUldkQ3lpc1Mya3Y1bEE3a1hIWGYyODd4bUVKM1J1VHlDL3FrYzM0VHRVYzlVa0t5TUlxMTFaZjRDcUhFdUJid0Y3TS9BWHlHMEo1RmwwZkhDQTl6NlVUWUVsQ2VSZmRCd3dTQTEySm5TRWxpYVFaNVhqQjRUdlZKSGtUUVN1d3Y1RFU5VzRsM0FGTjNhSWRqb29nVnlMamtjSnkwdzM2bDhUZUE5Rng2OXJxTU5Hd0FtRWRUdXM4NjFxL0lXd3hiQklzbVlBOTJIL1lhbGFyQUMrVDloSnNWYS9UU0R2b3VPNE91clJuNUdFVmRxczMwZVIwVW1ZRDFLclhZRHpDWk1JclhPdld0eUQvd203a3FsWndKUFlmMGlxRlBjUnJ2YnJ2VEtZQlhRbGtIK1JjVU9kTlJuSTJ4TjRMMFhIdVEzVVpSeHdHdHBJcWV4NEhKaFpRL3VJbEdaNzRCbnNQeHhWaWN1QlEydHFtZjU5STRIM1VIUzhyb242ckt1TmNGdkYrdjBVR1V1QjhVM1U2RkRDbmg3Vzc2TXE4USswVm9Ba1loZGdBZllmQ3UvUlJWaEZzWjVoL3Y1TUJKWW44SDZLak91YXJGRmZiMHJnUFJVZC94S2hUanNRSnY5VzRSRks2MWhJV0RoSXhNdytoTVZWckQ4TW5tTTU4SFZnNnhyYlpDaFZtTmoyMmtpMTZ0V0svNkh1eDRpM01Ob000S3RvbmtEUnNSallxOFkyRVlucVlQUUJMektlQno1RDJERXNsbGI4VDJxN09scTExbmRzQXUrdDZEZzJXcldDaWJ5OCs2TDFlL01hTHhLZTZCRXB6ZXVCVmRnZi9CNWpJWEEyWWRmRTJJNU00UDBWSFFmR0tsWWZMZmhmTitHcWFOVmEzMWhDUjJCaEF1L1JZNndrZkxaRkN2Y0dZRFgyQjcyM1dBNThpV0tmOWYxOUF1K3p5UGhMdkZMMTYrZ0UzbVBSTVRkYXRUWTBGdmdZdW0xWVJMUUR4OWZlRkNMMWV4dlFnZjNCN2luYUNjOVZUNjJqSFJveG5mRE10L1g3TFRMMmoxYXRnZDFvK1A3S2lLL0VLOVdBSmhHV3VOVUtnM0dqRTNoWDdjMGdVcnQzNFAvWjhUS2puZkE0M2hiMU5FSVR2bGpDZTdLTXkrS1ZhbENIbC9pZUxHSWhZWWZBTWt3bHJFR2cyNG54b2d0dElpU1JIWS8vcThleW9odjRLZkNLZWhxZ1NTT0E1eUsraHhSajcyalZHdHAxQmI2UEZLSjNsOEN5ekFCK2hqWU9peFVkd0QvVjFRSWlBM2c5MnM0M1Z0d0s3RnRmK2FONFM0UDU1aEpsWGYzM2VsM0UzRk9NMk9zbzFHcGZ3bWZFK3YxN2lIWTBNVkNhZERBYW5vc1JDd2hMOXNaNnpycGUxOVNRWTg1eFNMeFMxZXp2VGVTYlErd1VyMVIxYVNGc1BLUmx4WnVQbGVnUlFXblFYdWc1LzJaakRYQWV6UzJ6MnF5NStCNWF2Uk9iL2RLOTd4SHdqWGlsYXNnWXdrUkJYWUEwRnl1QS9lb3J2VlRkTGxSakwvUWk0OWVrc1duSHVkalhvc2c0TVY2cDZqSU1lS0tHL0hLTnBReTlwWFFadGdaK2lYMDljbzdGMkkzb1NHYm00SC9DV0pIeERNMXZReHZMQ0h5dnd2WVVNRHhhdGVyM3dRSHk4aEtueEN0VjAxNlBiZ3MwRXdzb2RvMEhjV0Jid2s1VDFnZHJqdEZOZUo3ZmNyaS9yMy9Ddmk1RnhvZmlsYW9oWTRBWHNLOURVWEY3dkZKRk1ZYXdXSmFlU0dvc25pTmM0SWxzWURycVlUY2E5MUR1WTJpMXVnVDcyaFFWUzBpanMrVjlmWVZkNDVVcW1yMkJ1N0d2VFk3eE9MQlYvU1VYejhhakQxUWpzWVp3UlRLeS9wSVhiaXErVjIzOHIzaWxhc29Vd214cjYzb1VGVitMVjZxb2hoT1dGZFlrd2ZyalhvcGRjbHd5TW9Ld0NZajFRWmxiWEF1OHNvRjZsK1dqMk5lb3FHZ0hwc1VyVmRPK2czMU5pb29GaE8rSVZNM0IvMk91UmNRVjJNNmZrUVMwQUQvQy9tRE1LZFlRSGs5cXJiL2NwYm9QKzFvVkZUK01WNllvWnVON21ldzN4aXRWSVZvSTYyeG9rN0w2NHZ1TkZGdjgrQnoyQjJGT2NSK3djME9WTHRkZTJOZXF5RWp4a2FaZlkxK1hvdUxYRWV0VXBGY1Ixb1d3cmxkTzhhbUdLaTNaZXl1K0Y0aUpHYjB6L0VjM1ZPbnluWTk5ellxS1AwYXNVMHg3WUYrYm9tSU5zRW04VWhWcUk4SzhITThqTWpHam0vTDNmaEJqQjZQMS9XdU5ad2hyditkaUZMNFhjVG9zWHFtaTg3eFY4SmtSNjFTR3d3aWZYZXU2NVJDcmdRTWFLN1BrWmk1aFpTanJneTZIK0NVd3ViRXltM2tyOW5VcktoN0NadG5mV3AyRWZZMktpbHNpMXFrc2s5RXFnclhHSW1DN3hzb3N1ZGdNMzh1WHhvcFZ3SHNhSzdFNXo4LytmekJpbllvd0VwaVBmWjJLaXUzamxhcFVwNk1KZ3JYRW80VEhXc1doVWNBTjJCOWtxY2M4d3YzY0hFM0U3NjJkRlQzdkwzV2VGd1pLWmUyRlJ1d0NQSVo5RFZPUFd3Z3JMb29qTFdnb3JKYTRGSmpVWUkxVGNBcjJOU3dxdmhPeFRrV2FqdCtsYXYrQjNaYldNVXdtVENLMXJtUHE4WFBTdnRVbWRUb2IrNE1xNWVnQ1BrMzZ6L1lQNVFyc2ExbFU3Qml4VGtYN0ZmYjFLaXBTbm9SWmkxYmdNK2dwZ2FIaUk0MFdXTkp5Q0g2dlNHTEVDK1QvcFFaaGZvZlhkcjQyWXAzS2NBajJOU3NxZmhTeFRwWU9JNnh5YUYzUFZLTURPS2poNmtvU1pxQ0RmTEM0dWFkR0hweUpmVDJMaXBNaTFxa01MY0REMk5ldGlGaENtbnRmTkdJNjRUdkF1cWFweG56U1duSmI2ckFSWVVLSDlVR1VhbHhFbUJqcHhmWFkxN1NJV0VJK0N6Q3R5L050dHpkRXJKTzFrY0NQc2E5cHFuRVRmanA4bFhJQjlnZFBpdEZOV0NuTTB5U1g2ZmhkMWZFYkVldFVwcW1FRmZTczYxZEUvRGhpblZMUVF0amZ3K3RucU5rNHYrSEtpb25Uc0Q5b1VvelY1RGVjWEl1UFlWL2JvaUtIdlJjRzR2WEptMlg0R2ozcjlTWjhiKzNjVEx5N2licEtpZlpFaTE3MEZ5OEEremRSMTVUZGpuMTlpNGdjVjU5YjErSFkxN0NvT0M1aW5WS3lONzRYYzJvMFZnRzdOMUZYS2NFVTRDbnNENWJVNGw1ZzZ5YnFtckk1Mk5lM3FEZ3RZcDBzdE9KMzVjMmZ4U3RUY3JZaGZHZFkxemkxZUlKOE5vV3FuR0hBVmRnZkpLbkZuNEdObTZocjZqNk9mWTJMaUJYQStJaDFzdkpwN0d0WlJDd256OG1adFpvQVhJWjluVk9MeThsN01TaTNQb2Y5d1pGYVhFam9HSGwyRS9aMUxpSzhURFNiZ2Q5RlowNklXS2NValNDTWRGalhPYlg0WkRORmxmaGVnOTlGWUJxTmI1TC95bjVEMlJLL001ZHoyb0o1S0ZkaFg4OGk0dUtZUlVwVUMzQXU5clZPS1RxQXZab3Bxc1F6RnIrTGpqUWFYMnFxb3ZsNFAvYTFMaUp5WDNPK3IzZGhYOU1pWWhVK2J0UFV3dk9UTm8zRW8xU243WlAydjlnZkRLbEVOM0JXYytYTWl0ZDdsRG52T3RlZmNZUjc1dFoxTFNMZUVyRk9xZnNJZmtmY0dva2ZORmRPYWRieDJCOEVxVVFuMVhwV2RRSit0LzU5ZGNRNnBjTHJhbk0vaVZta0RKeEVHQUszcm5zcVVhVU9ZRkttQVl1d1B3QlNpSFpDWjZoS1RzUys3a1hFclRHTGxKQkRzYTl0RWJHRU1GbXVTbzRoM1A2d3JuMEtzWml3RXFtVXFBMjRCdnZHVHlGV0FxOXRycHhadWhqNzJoY1JYbS9odE9GM2pZNHFmdjRPUnd1dTljWlYrSjl3blpSUFlOL29LY1Fxd3BWVjFXd0V2SWg5L1dOSEoyRU5mYSsrakgyTmk0anpZaFlwSTYvSDcyMjRldVBzSm1zcE5kb1Z2NXVNMUJQdHdGRk4xakpYUjJOZi95TGk4cGhGU3RBdTJOZTRpSGdpWW8xeWN3UWFDVmhMbUJleFo1TzFsQ0hva2I4UVZUNzVnOStkSHQ4VHMwaUplaEQ3T2hjUkhpZHUxdW80ZEZHMmxuQnNqMm15bGpLSWIySGZ5TmF4aGpBSnA2cGE4YmxaeVJwZ2NzUTZwZXJmc2E5MUVmR0ptRVhLMEFubzZZQzFoRVdUcEFBSG9tZFFPOUZqSjN0ZzN3NUZ4S1V4aTVTdzdiQ3ZkUkZ4VTh3aVplcDQxQW5vQXZacHRwQ3l2akdFbFplc0c5Y3lPb0UzTjF0SUJ6NkZmVnNVRWUrTVdhVEUzWWw5dldOSEY3QjV6Q0psNmlUODd2MVFhOXhOOVI0TkxkVFhzRzlVeStpbVdvdjhET1k2N05zamRxekc5NDZOZlhuZHdmSFVtRVhLMkduWXQ0VjFmS3JwS2dvQWU2TWVwWGFmQ2liaWM0angxekdMbElHdDhYazc3NUtZUmNyY2YyRGZIcGF4R3BqYmRCVXJiaVR3QVBhTmFSbmZicnFLZnB5QWZYc1VFU2ZGTEZJbWJzVys3ckZqSldHTkNnbTdDUDRRK3pheGpMK2hCWUthOGxuc0c5RXlMZ0dHTlZ0RVI3NkhmWnZFampXRWtZMnE4YnFZMXlFeGk1UzVOdUEzMkxlSlpieXY2U3BXMUN5cXZkNzBqZWlaMHI3bVlkOHVzZVBQVVN1VWo3blkxNzZJOExhVFk3TkdBOWRqM3k1V3NSVFlxdWtxVmt3TGNBWDJqV2NWRHdHYk5sMUZYM2JBdmwyS2lOTmlGaWt6OTJOZi85aHhlOVFLK2JBSjFiNlZXNVZIZktONUovYU5aaFgvQUdZMFgwSjNQb3A5MjhTT0xtQ0xtRVhLak1lSll0MzQzcytoVWRzQXoyTGZQbGFoUjdock5BbFlnSDJEV2NSS3dsNEhzaUdQSTBMWFI2MVFmbmJIdmcyS2lCTmpGc21SdmFqdXZnSFBBUk9hTDZGLzM4UytzU3lpRzNocmhQcDVOQWFmWHh3ZmlWbWtETFhnYzE3SGoySVd5Wm1Uc0c4ZnE5QXl3VU40TlQ2Zjg2NGx2aGloZmw1NTNmMXZkc3dpWmNyaklsL1BFam8zMHIrdllOOUdGdEZCbU1zay9XZ2hQRGRwM1VnV2NRbDZYblF3NTJIZlJySGpucWdWeXRkcnNXK0xJcUxLdXdNT3BZM3c5SXQxRzFuRVZSSHE1MUpWaDRidXAxckx3RGJpTHV6YktYYm9jYkZnT0xBRSsvYUlIUitPV1NTSEpoS2Vkckp1SjR2UWhNQSt4Z0hQWU44d1pjZENZTnNJOWZOc01qNlhnajR3WW8xeTl3dnMyeU4yVkhWOWgzcThrdkNjdkhWYmxSMVBrY2dhTDZrTU81OU45WGJTNnQzYTl4SHJSQkszUCtrY3A3RXNKV3hxSk1FZnJCTW93SDVvV2VDaDNBKzhqVEFCdWtxbW9RbkFMOW1LOFBpYmRhK3M3UGhvak9KVndMbll0MVhzdUNocWhmSzNHVDQzQnpvd1lvMDgrd3oyYlZWMkxLZmFhNEM4NU1mWU4wYlo4UWMwUzdoV2QyRGZYckhqNUpnRmNzTGo1a0RhRXJZMnJjRGwyTGRYMmZIOUdNWEwyYzc0dkw4N1dEeE5XQnBUaGpZSmY4ZEhGK0dLVjliM09lemJKblpjRnJWQ3ZrMmxlaXNGZGxIeGhkLytnbjBqbE4zZ0IwZXBYRFc4RWZzMml4MDNSNjJRSDN0ZzN6YXhZem5oS1FlcHpVR0V1VkhXN1ZabVhCbWxjZzJ5bkZ4MU5OVTdHWDRhNHdiUHpBSFdDUlRBNDRTM0dHNEY1bHNuRWRrWVlCZnJKREp5RmRWN1BQWWc0QWpySk1yV2lzOTd1MFAxOU5waUZLOUNic2UrM1dMSFBsRXI1SXZIK1VDYTdGdWZOcW8zTW53bi9wNTBHbFRWRnYyWlQvVWVjMnlXeC92L3k5Q1E4R0JPeHI2TllzZnZZaGFvSXFaUnZRM2gzaFNsY2hrWURqeUtmY0hMaWk3Q2NxZFNuMk93Yjd2WThkdW9GZkpuUyt6YktIWXNvbUpYZDVHOEhwK1BoZzRVRHdMRG9sU3VEaFlINXFtRXZhR3I0anpDa0piVTV3RHJCQXB3aFhVQ2lmc0hZWEVZVHlhaWZRRWFjU253WGVza1NqUWJlSmQxRWtVYlRiV1cvSDBBR0JXbGN0VnpHL2J0Rnp0ZUdiVkNQbm5jK09uTXFCV3FqbkhBNDlpM1gxbnhGQ1d2SGxuMkNNQnBWT2RlZURkd0NyREtPcEVNamNiZnRwblA0Ty9xdGdnZVI4djJzMDRnVXk4UzVvdFZaYW5nYWNDN3JaTW95aWlxdGRCRDFSNW5pV2svN05zdmR2d3dab0VjR3dlc3diNjlZc2F6VVN0VVBWL0R2ZzNMaXFlQWtYSEtOclF5UndEZVMzVldRTHVmc0w2MU5HWXY2d1FLb1B2L3RYa1JmNHNsYlFhOHdqcUpqSjFOMkRxNENrb2RCU2lyQTdBUjFkbjlxQk40SjdEYU9wR003V21kUUdScjhUbTBYUlNQbmFVOXJCUEkyRXJDSTZKZHhubVU1V3hLR2dVb3F3TndLdUVSbnlyNFQrQVc2eVF5NSszTDhrRTBERndQajZ0bGVqdW15M1lEOEZYckpFcXlGZkRQMWtuRU1vS3dBWTcxdlpVeTRzNmU5eXVOMndMN2Rvd2Q1MGV0a0g4YkVTYlBXcmRiekxnbWFvV3FhU1BDazFYV2JWbEdQRUVKaTRhVk1RSndFdFc0K2w4TGZJQXdnVWthNS9IKy83WFdDV1JtTmY1RzBYYkZZS0VYWjFZVDVwS3R0VTZrQkRPQXR4VDlueFRkQVdnRlBsVHcvNUdLN3dOWFd5ZmhnTGY3LzZDcnYwWjRxOWxvWUs1MUVnNWNEZnpNT29tU2ZKek1WNUU4RHZ1aGxESmlJYkJwcEpwVjNWK3hiOCtZTVM5cWRhcmpNT3piTG5hY0VyVkMxYlVac0JqNzlpd2pqb3BVczM0VjNidW95c3ovc3dtYlYwaHoyZ2hEcFo1b1ZLZ3gxeEdlcVBGa2Qrc0VuSGlPNmp4bS9RbnJCQnAxSVBhOXB6TGlCaklmcGtuSUR0aTNaK3c0TldxRnF1VW03TnN2WnR3UnR6eVYxZ2I4SGZzMkxTUDJqVlN6RFJSNTR2cHdnYStkaWk3Z2ZWUm5xY3FpZVp3QTZPMWVkcG04VFo1OEZUREdPZ2tudWdnVEFxdnczZnN2UmIxd1VSMkFXY0NSQmIxMlNyNEIzRzZkaENQZUpnQXVJS3dCSUkzeDFnRm9BM2EyVHNLUlc0QUxySk1vd1RIQXpDSmV1S2pIVXM3RS83QjROMkg3VWczeHhuT1FkUUtSTFFEZVk1MUV4aVphSjFDQTA5RFRBREU5VGhnbWI3Rk9wRUJ0d1BzcDRJbTZJb28ybnJDaHdmZ0NYbHRFUktScVhpU3NFTGcwNW9zV2NaWCtIblR5RnhFUmlXVWM4SzdZTHhwN0JLQVZlQVRZT3ZMcmlvaUlWTm1qd0d3aVRueU1QUUp3SkRyNWk0aUl4RFlUZUYzTUY0emRBVGc5OHV1SmlJaEljRnJNRjR0NUMyQTY4QmhoeHFLSWlJakUxVVVZQ1hneXhvdkZIQUU0SFozOFJVUkVpdElHdkR2V2k4VWFBUmhCMlBSa2FxVFhFeEVSa1EwOVJ4aHg3MmoyaFdLTkFCeURUdjRpSWlKRjI0eEl1d1RHNmdEOGM2VFhFUkVSa2NGRldSTWd4aTJBTFFrVEVuVC9YMFJFcEhpZGhOc0F6emJ6SWpGR0FFNUdKMzhSRVpHeURBTk9hdlpGWW93QVBBRE1pZkE2SWlJaVVwdUhnTzBJbXlFMXBOa1JnUDNSeVY5RVJLUnNzNEc5bW5tQlpqc0FUUTlCaUlpSVNFUGUwY3cvYnVZV3dBakNCSVJKelNRZ0lpSWlEVmtFYkE2c2FlUWZOek1DOEhwMDhoY1JFYkV5aVNZMkNHcW1BL0MySnY2dGlJaUlOSy9oYzNHanR3REdFNVlqSE5Yb2Z5d2lJaUpOVzBsWWlYZDV2Zit3MFJHQTQ5REpYMFJFeE5wbzRJMk4vTU5HT3dCdmJ2RGZpWWlJU0Z4dmF1UWZOWElMWUFJd24vQVVnSWlJaU5ocUI2WUF5K3I1UjQyTUFCeU5UdjRpSWlLcEdBa2NXZTgvYXFRRDhFOE4vQnNSRVJFcFR0M241bnB2QVl3Rm5rY1RBRVZFUkZLeWtuQWJZRVd0LzZEZUVZQWowTWxmUkVRa05hT0J3K3I1Qi9WMkFCcDYxRUJFUkVRS2QwdzlmN21lV3dCdGhObi9rK3RLUjBSRVJNcXdrTEFvVUZjdGY3bWVFWUI5MGNsZlJFUWtWWk9CUFd2OXkvVjBBSTZxUHhjUkVSRXAwZXRyL1l2cUFJaUlpUGh4ZEsxL3NkWTVBRE9CUnhyTFJVUkVSRXEwTmZERVVIK3AxaEdBSTVwS1JVUkVSTXBTMCtPQXRYWUFEbTBpRVJFUkVTbFBUZWZzV200QkRDTThXakMrcVhSRVJFU2tERXVBVFJqaWNjQmFSZ0QyUWlkL0VSR1JYRXdBZGh2cUw5WFNBZER3djRpSVNGNWVOOVJmcUtVRE1PU0xpSWlJU0ZLR3ZIZ2ZhZzdBT0dBUllSNkFpSWlJNUtFRG1NZ2d1d01PTlFMd0duVHlGeEVSeWMxd3doeStBUTNWQVRnZ1hpNGlJaUpTb3YwSCs4T2hPZ0NEL21NUkVSRkoxcUFYOFlQTkFSZ05MQVpHUkUxSFJFUkV5dEJPZUNSd2RYOS9PTmdJd043bzVDOGlJcEtya2NEdUEvM2hZQjJBZmVQbklpSWlJaVhhYjZBL0dLd0RNT2pzUVJFUkVVbmVnT2Z5Z2VZQXRBQUxnTW1GcENNaUlpSmxXQUJNNmU4UEJob0JtSTFPL2lJaUlybmJGSmpaM3g4TTFBSFE4TCtJaUlnUC9aN1RCK29BN0ZsZ0lpSWlJbEtlZnMvcEF5M3pPK0JqQTVucUFPNnlUa0lZRDh5eVRpS2lleG5nK1ZvcHpXYkFsdFpKUk5JSjNHbWRoQUN3QTJFcFhTOXF2cWdmUWZoU1crc29ycW4xelV1aDNvVDlzUkF6K3AxWUk2VTZBL3ZqSUZhME0vUUdiVktPYTdFL0htTEdLdnJwMFBSM0MyQjd3dUlCbmx4dG5ZQUFZVEtLRjEzQVF1c2toT2VzRTRob0JHR1VUT3g1dTJqY0NKalQ5emY3NndEc1hId3VwZlBXbUxueTFBRllRT2dFaUszNTFnbEVwbEdsTkhnOFoyeHdicTlDQjZBVHVORTZDUUY4ZFFBOFhYbm16RnM3cUFPUWh1c0k1dzVQYXVvQTdGSkNJbVc2RlhqUk9na0JmSDI1ZWJ2eXpKVzNEb0NuVG5MT2xnTzNXeWNSMlpBZGdGYkM3RWRQUEE3bDVHb1Q2d1FpZXQ0NkFRRkM1MzZGZFJJUmVlb2s1ODdidVdNbitrd3k3ZHNCZUFVd3RxeHNTbktkZFFMeWtxbldDVVQwckhVQzhoSlBvekVhQVVqSDlkWUpSRGFCUG8vTTl1MEF6QzB2bDlMY1lwMkF2TVRUbDV1bmswN3VQTjBHOFBRWnlaM0h1V1Bici91THZoMkE3ZkhsU1hTbGxvbzJZSkoxRWhGNU91bmt6bE5iNkJaQU9wNEIvbUdkUkdTRGRnQzhqUURjWkoyQXZHUXlnMjgvblJ1TkFLVERVd2RBSXdCcHVkazZnY2pXTzhkNzd3QjRhN3ljZWJ1eThYVFN5WjJuenBpM3owbnV2SjFEQmh3QmFBRzJLemVYd25scnZKeDVlZ0lBOUJSQVNqeDF4alFDa0Jadm84aHpXZWRKZ0hVN0FOUHc5UVJBTi9CMzZ5VGtKWjYrMkRyUk1zQXBlY0U2Z1lnbVd5Y2c2N21Oc0phK0YrT0J6WHQvc1c0SHdOTXViUUFQNCt2NTROeE50RTRnb2lXRURxYWtZYkYxQWhHTkFFWmJKeUV2V1FZOFlaMUVaTnYyL3FTMXY5OTBRdHRxcG1XQ2RRSVJlVHJoZU9DdFBUeDlWanp3ZGk2WjJmdVQxdjUrMHdsdmpaYTdqYTBUaU1qYkNTZDNpNndUaUV3ZGdMUjRPNWYwMndId05nSndsM1VDc2g1MUFLUW8zdHBESFlDMGVEdVg2QmFBbE01VEIyQ0pkUUt5bmhlQkR1c2tJbElISUMzZXppWDlkZ0MyTVVpa0tFdUFwNnlUa1BXTXQwNGdJblVBMHVPcFRUeDFsajE0bkxBN29CY2JkQUFtNCtzUndBZXNFNUFOZUxxcThYYlAyUU5QdHdFOGZWWTg2Q1k4VmViRnh2UmNrUFYyQUxheXk2VVFEMWtuSUJ2dzlLWG02V1RqaGFjMjhmUlo4ZUpCNndRaW13N3FBRWg1UEExcmVocHU5c0pUQjhEVFo4VUxieDJBcmNCdkI4QmJZM25nNlV2TjA4bkdDMCszWlRRQ2tCNXY1eFRYSFFDTkFLU2xCUmhublVSRTZnQ2t4MU9icUFPUUh0Y2RnR21HaWNTMkZuakVPZ2xaejFpZ3pUcUppSFFMSUQyZTJrUWRnUFE4aEs4OUFkYWJBN0Q1SUg4eE44OEJLNjJUa1BWNGVnUVFZS2wxQXJJQlQ0OXBqYkZPUURhd0hGaGduVVFLcm9JU0FBQWdBRWxFUVZSRW04UExIWUROREJPSjdVbnJCR1FEM2pZMzBTWlQ2VmxsblVCRW82d1RrSDdOczA0Z29xbndjZ2RncW1FaXNha0RrQjV2SFFDTk1LWEhVNmRNSFlBMGVlb0FUSUhRQVJnR1RMTE5KU3AxQU5MajdRdE5IWUQwZUdvVGJ4MW1MenlkV3pZRjJscDdmdEk2eEYvT2lhZGVtaGVlT2dCcjhMWHV2QmZxQUVqUlBDMHYzd1pNYXNYWC9YOVFCeUJGbnI3UVBKMW9QUEYwQzhEVDU4VVRUeU1BQUZOYmdVMnNzNGpzYWVzRVpBTWJXU2NRa1RvQWFmTFVBZkEwWXVhSnB4RUFnRTFiOFhYL0g4SmpnSklXVDFjMDZnQ2t5ZE5UQUczQVNPc2taQVBQV3ljUTJjUldmQzA2c1JaNHdUb0oyWUNuRG9DbkswMVB2TFdMUmdIU293NUE0aGFqQ1ZvcDh2UmxwaEdBTkhsckYwK2RaaTlXQVM5YUp4SFJoRlo4YmRMaXJZZm1oVG9BVWpSdkl3RHFBS1RKMHpsbWdyY1JBRStONDRrNkFGSTBiKzJpRGtDYTVsc25FSkc3RG9EdS82ZEpIUUFwV2p2UVpaMUVSSjQrTTU1NDJnOWdZaXRocHpZdlBPMEk1b21ueHdCWFd5Y2dBK3EwVGlDaTRkWUpTTDg4bldQR3R1SnJxR21aZFFMU3IySFdDVVRrNlNUamphY1JBRS9iWjN2aWFSTGdxRlo4RFRXcEE1QW1UMTltbms0eTNuanFuSG42ekhqaTZSd3p5dHNJZ0tmZW1TZWV2c3c4bldTODhkUTU4L1NaOGNUVE9XYTB0eEVBVDQzamlhY3ZNMDhuR1c4OGRjNDgzVGJ6eE5NNVJpTUFVZ3BQWDJhZVRqTGVlT3FjZWVvMGUrTHBIRE82RlY4enRMMHRCdUtGcCsybVBaMWt2UEhVT2ZQVWFmWmt1WFVDRVkxcXhkZUJwbVdBMCtUcEdQTjBrdkhHVStkTUl3QnA4blNPR2FZT2dKVEIwNWVacDVPTU41NDZaNTQrTTU1NE9zZTB0ZUxyUVBQMEJlQ0pqakVwZzZmT21hZlBqQ2VlUHYvRDFBR1FNbmdhWmZKMGt2SEcwK2ZmMDJmR0U0MEFKTXpURjRBbm5vNHhkUURTNWFsdFBIMW1QUEhVQWRBSWdKUkN4NWlVd1ZQYmFBUWdUWjQ2QUcydHdGcnJMRVJFSXZDMFVaT25FNDBuTGRZSlJMUzJGZWkyemlJaTlaclRwS0ZaS1lPblo3UTlyVG52eVVqckJDSmEwNHF2WWJNUjFnbEl2OVFCa0RJc3RVNGdJay92eFJOUDJ6UjNhQVJBeXFBT2dKVGhVZXNFSXZMMFhqenhOQUxRM29xdkwyZFB2VE5QUEIxajZnQ2s2MkhyQkNKWkJUeGxuWVQweTFNSHdOMHRBSFVBMHVUcEdGTUhJRjIzV2ljUXlSMzRHcG4xeE5OdDVnNXZIUUJQamVPSlJnQ2tESGNEaTZ5VGlPQ3YxZ25JZ01aYkp4RFJtbGJDY0pNWFk2MFRrSDZwQXlCbDZBYitZcDFFQkpkYkp5QURtbUNkUUVTcnZYVUF4bGtuSVAxU0IwREs4alByQkpyMExIQ05kUkl5b0VuV0NVUzB1QlZZYVoxRlJKNkdaenp4ZEp0SkhZQzBYVXJldHdGK2lxOE9zemVlUmdDV2VSc0IwQzJBTkhuNlFsTUhJRzN0d1BuV1NUU29DL2lXZFJJeUtJMEFKRXdqQUdsU0IwREtkQzU1WHRqOEZEMy9uN3FKMWdsRTVHNEVRQjJBTkhtNkJhREZwdEwzUFBCVjZ5VHF0QnI0bkhVU01xUXRyQk9JYUVrcnZ0YlA5dFE3ODhSVEI4RFRRaUNlL1Fjd3p6cUpPcHdEUEdLZGhBeHBTK3NFSWxyYUNpeXh6aUtpVGEwVGtINTVHbVVhYloyQTFHUUY4Rjd5Mk8zMFhrS0hSZEkyR2w4WG1ZdGJnY1hXV1VRMHhUb0I2WmVuRHNBbzZ3U2tabjhpWEZtbmJEWHdkbng5UnJ6eWRQVVA4Rndydm5hZDBnaEFtanhOTk5VSVFGNCtRYm9MNjNRRDd3THV0RTVFYXVMcC9qL0E4OTV1QVl4Rlg5QXA4blIxb3hHQXZIUUFid1J1c0U2a0h4OGwvNFdMcW1TYWRRS1JQZWZ0RmdEQUp0WUp5QVkwQWlDV1ZnSkhBemRaSjlKakxmQXg0Q3ZXaVVoZFBJMEFyTVhoQ0FEQVZPc0VaQVBxQUlpMWhjQnJnVDhZNTdFR09CbjRzbkVlVWo5UGN3Q1dBdTJ0aEdkbVBkbktPZ0haZ0c0QlNBcFdBRWNCSHlUY0dpamJrOENCd0lVRy83YzB6OU10Z09jQVBIWUFwbHNuSUJ2UUNJQ2tZaTF3SHVGRWZGZEovMmMzOEcxZ1I5S2NpeUMxMmR3NmdZam1RK2dBTENBY29GNW9CQ0E5NmdCSWFxNEhkZ1hPb3VkcXFDQlhBWHNBcCtQcmlhc3E4alFINEdrSUhZQk93djB4TDlRQlNJK25Xd0FqMEhMQVhuUUNYd08yQnQ0SDNCUHBkZHVCWHdCN0F3Y0R0MFY2WGJIbGFiTzVsem9BMERNYzRJUnVBYVRIMHdnQWFCNkFONnVCL3dGZURleEVXSlh2QnVwYnd2b0Y0SmZBZXdoRHhTY0FOOFpOVTR4dFpKMUFSRS9EeTFjeTg0RlgyZVVTbFRvQTZmSFdBUmdOdkdpZGhCVGlUbDVlbUdjczhFcGdOakNEc05uWXhvUXIvT1dFay80andJTTlQK2F3N0xBMHp0T3Q4dlU2QU04YUpoTGJac0FZd294ZlNZTzNEc0RHK0JvMWsvNHRCMjdwQ1pIRitObHhkcjFiQUU4WkpoSmJDekRMT2dsWnp6SjhYUjFOc0U1QVJFcm5hYTZjMnc0QXFBT1FtazYwN2JTSTVPMGg2d1FpV1UzUDQvL3FBRWhaUEQwQ3BSRUFrZXA1d0RxQlNPNmtaejVEYndkZ25sMHVoVkFISUQyZWxweFdCMENrZWxMWlM2SlpOL2YreE9zSXdHenJCR1FER2dFUWtaeGRRM2dDSkhkWDl2Nmt0d093R0YvM2FMZXpUa0Eyb0JFQUVjblpTdUJxNnlTYXRCcTR2UGNYcmV2OHdhUGw1MUtZU1doRndOUjQ2Z0JvRXFCSU5mM1lPb0VtL1pGMUhwRmZ0d1B3U1BtNUZHb0g2d1JrUGJvRklDSzUreFhoc2VaYy9jKzZ2MUFIUU1yaWFRUkFIUUNSYWxvQmZNczZpUWJkRDF5eDdtK29BeUJsMFFpQWlIandWZkpjM2ZRejlGbVFUUjBBS1lzNkFDTGl3ZlBBZjFvblVhZGJDVHRVcnNkekIyQTJ2blp2eXAybld3Q1RyQk1RRVZQL0RUeHNuVVNOT29CVDZXYzU5blU3QVAvQTE2T0F3NENkclpPUWwzaGFSM3N5MEdhZGhJaVlhUWRPSXB4Y1UvY2Z3TzM5L2NHNkhZQzF3SDJscEZPZVBhMFRrSmNzc0U0Z29sWmdVK3NrUk1UVVRjRFoxa2tNNFRMZzh3UDlZV3VmWDN2ckFPeGhuWUM4eEZNSEFHQ3FkUUlpWXU2cndBWFdTUXpnSWVBdFFOZEFmOEY3QjBBakFPbDRBVjliQXFzRElDSnJnZE9BWDFzbjBzZGp3Q0dFVlg0SDVMMERzQTBhcWszRkdudzlDYUFPZ0loQXVNSitNM0NoZFNJOUhnQU9wb1k5ZnJ4M0FFQzNBVkxpNlRiQUZPc0VSQ1FaSGNESmhQdnQzWVo1L0FYWUIzaXlsci9jdHdQd0pQQmk3SXlNdmNZNkFYbUpwdzZBUmdCRVpGMXJnVThEUndEelMvNi9PM3IrNzhNWll0aC9YWDA3QU4zQUhSR1RTc0VCMWduSVM5UUJFQkh2TGdQbUF1ZFR6bWpBRFlUNWJwOEhPdXY1aDMwN0FEREE4NElaMncwWVk1MkVBT29BaUVnMUxDSk1EdHlWc0lGUUVSMkJlNEFUQ0tQY0RaMjNxOUFCR0FIc2JaMkVBT29BaUVpMTNBSDhFN0E5Y0E3d2JKT3Z0eEs0bURERC85V0U1WDJqUGwyMVk4OExlb29CRjBLUVVuMFErMk1oVmp3VHVUWWk0bDhic0Jmd1NlRFBoSm42ZzMzUExBT3VCNzRDSEF1TWpwbk1zSDUrN3o3Q01vY2pZLzVIeGpRUElBMmVSZ0EySll5Z1djNzRGWkc4ZEFFMzlrU3Y4WVFSeFluQVdNTGowcDNBMHhTOGhIckxBTDkvQytIZXVSY2R3RjNXU1FnVGdKbldTVVIwTy9sM0FEb0llNEFzQWg0bmJIQnlLM0EzK2I4M0VSbEVmeU1BRU5ZNDl0UUJHRTZZakNFU2srZk5waFlDVndJL0JmNUFHQlVVRVVmNm13UUlvUU1nSXRVMW1UREQrRmVFaVV0ZlJJc2ZpYmd5MEMyQVdZU05CRVJFZXEwaWJIN3lSY0pzWkJISjJFQWRnQmJDaEszSkplWWlJbm1ZQjd3WCtKTjFJaUxTdUlGdUFheEZ0d0ZFcEgvVENmTUN6aU9zc3lFaUdScW9Bd0RyUDZZZ0lyS3VGdUFEd084Smp5NkpTR1lHNndEOHJiUXNSQ1JYaHdKWG9kdUZJdGtaYUE0QXdDakNya0tlRmdRU2tXTGNRdGlEZkxsMUlpSlNtOEZHQUZZUlB0UWlJa1BaSGZnNWczK25pRWhDMm9iNDgyMkIvY3BJUkVTeU40dXdzdUMxMW9tSXlOQ0c2cTFmWFVvV0l1TEZaNEY5ckpNUWthRU5OZ2NBd3V6ZXhReThaTENJU0Y5M0VaYmU3clJPUkVRR050UXRnRFhBWVlUbmZrVkVhakdWc0plQTFoSVJTVmd0RTNhdUtEd0xFZkhtM3doUEVvbElvbXJwQUZ4V2VCWWk0czFVNEdUckpFUmtZRVBOQVlCdy8vOEZZT09DY3hFUlgrNER0cmRPSXFJMnd1T09od0t6Z2MxNmZuOGhjQ2R3QTNBTjBHMlNuVWhCZmszWUgwQ2hVQ2pxaVIzSTN5amdnOENURFAxKzV3SC9UaGdCRVVuYVVKTUFlMDBHWGw5a0lpTGkwaUxnU3Vza21yQS84RWZnYmRRMkNyb3hjQUJ3SmpBRGVJUXdnaXFTclJuWVgwa29GSXI4NG5yeWRUTGhTYWhtM244M2NEbHdkTG1waXd5dGxqa0F2ZTdCMS8wOEVTbGVCekNKL1BZSWVEZHdRZVRYdkIwNEYvZ0pXaU5CRWxEUHV0MlhGSmFGaUhnMUhIaWxkUkoxMmgvNGRnR3Z1elB3SStCQndpMkNNUVg4SHlJMXE2Y0RjR2xoV1lpSVo3T3RFNmpEU09COGlsMzlkQnZnYThBendIbkFsZ1grWHlJRHFxY0RjQU9hekNJaTlYdUZkUUoxZUIrd1hVbi8xM2pnQThCandJWG9GcXVVcko0T1FCZGhOcXlJU0QzR1dTZFFvemJDNDM1bEd3R2NSTmhENFhlRVd4QWloYXQzNys3ZkZwS0ZpSGlXU3dkZ2QyejNQV2tsUEMxd05mQjM0QjFvSXpZcFVMMGRnRDhDSzRwSVJFVGM2ckJPb0VhSFdDZXdEazBZbE1MVjJ3RllDZnlwaUVSRXhLMFhyUk9vMFJ6ckJQcWhDWU5TbUhvN0FBQy9qSjZGaUhpMnlEcUJHbTFobmNBZ2VpY01QZ3A4bC9JbUtvcGpqWFFBZmcrc2pwMklpTGoxc0hVQ05jcGhjWjZSd0NtRWpaYTB3cUEwcFpFT3dJdkFGYkVURVJHM0hyUk9vRWFMclJPb1F3dGh6c0x2Z0p1QUU2aDlieGNSb0xFT0FNQkZVYk1RRWE4V0VvYXRjM0NuZFFJTjJvUHduZnd3Y0JhYU1DZzFhclFEOEN2ME5JQ0lETzJ2aEExeGNwRHp4a1VBV3hQMkdwZ0hmQUZ0U1N4RGFMUURzQUx0RFNBaVE4dnBxYUZyZ2Flc2s0aGdFdkFKUWtkQUt3ektnSnE1WjlRQnZEVldJaUxpemhyQ2hMVlYxb25VYUMwd0VUakFPcEZJMm9BZGdkT0EzWUIvQUUrYVppUkpxV2M3NEw1R0VKNU5uUndwRnhIeDVSZUV5V2s1bVFJOEFZd3l6cU1vMnBKWVh0TE1DRUFYWVpHSzNTTGxJaUsrdkJ0NDJqcUpPcTBBWmdDN1dpZFNrTTJCWXdsN0R3RGNRejRyTlVwa3pZd0FBTHdHK0Z1TVJFVEVsYXVBZzYyVGFOQWN3blAyamM2Unlza3k0SWZBbHdtM0NLUkNtdTBBQU55UFZxVVNrWmQxQTNzRE4xc24wb1RmVWExRmR0WUFQd2YrQzdqWE9CY3BTWXdlN284aXZJYUkrUEVkOGo3NUE1eGpuVURKZXJja3ZodXRNRmdaTVVZQU5pTThPcU50SzBYa01XQVhZS2wxSWhIY0FPeGxuWVFoVFJoMExzYlNrY3NKSDVKWkVWNUxSUExWUWJoeXpHWGx2NkVzSTcrbkdHTFNoRUhuWWsxeStYNmsxeEdSUEswbFBQTi9vM1VpRWYwS1A1MlpadlN1TUtndGlaMkp0WG5FSTRRUC83aElyeWNpZVRrYitKWjFFcEd0SlV4b1BOSTZrVVNNQlBZRXpnQm1BdzhCQzB3emtxYkU2Z0IwQXhPQS9TTzlub2prWVMzaDVQOWw2MFFLY2cvd1htQzBkU0lKNlYxaDhIUmdYMkFKb1RNZ21ZbTVmZVNqd0Flb3hyT3pJZ0lyZ1hjQzUxc25VcUFPWUR5NnVPbFBDMkV4dUxjQ3h4Q1dmTDZYZkRaL3Fyd1lUd0dzcTJyUHpvcFUxUVBBbTRHN3JCTXBnZmZsZ1dONm5EQlA0QUswWTJ6eVlvNEFRQmdLZW52azF4U1JkS3dHdmdTY1NIVldqdk8rUEhCTUU0SERDZk1FTmllc0svQ2lhVVl5b05nakFLMkVlMEV6STcrdWlOanFCSDRHZkk0dzZiZHFxclE4Y0V4YVlUQmhzVWNBTkd0V3hKZG5nTzhTbmdYL1ByRElOaDB6Q3drYm44MnhUaVF6bWpDWXNOZ2pBQUJqZ0huQXBBSmVXMFNLdFJyNE8zQTFjRVhQajEybUdhVmpmMEk5cERsYVlUQVJSWFFBQUw0QWZLS2cxMDVGTitIeEp3OUxucWJpczRUN2hsNzhFZmlOZFJKRFdFTll6Zk1Gd2tTM2VXZ1c5MkNxdmp4d1RKb3c2TlFVd2lNaGE1M0hlYkVLSmdCY2pIMmJ4b3hMNDVaSEVuQUM5c2VWdDFpS1ZoaDA1d0xzRDZ5aW94UFlPVmJCaEgvRnZrMWp4aExpejdNUlcyMkVTWkRXeDViSGFBY3VCTGF2dVRVa1dYTUk5dzZ0RDZxaTQyWTBNemlXQTdGdno5aXhZOHdDU1JMT3dQNjQ4aHpkYUV0aUYzNlAvY0ZVUnB3YXEyQVZONDR3cW1MZG5qSGpqS2dWa2hTTUpxeUJiMzFzVlNIK0Ryd0RiVGVmcFlPd1A0REtpRVdFZVEvU3ZIdXdiOCtZOGZPNDVaRkVmQjc3WTZ0SzhSaHdGdUVwTThuSVRkZ2ZQR1dFdGtTTzR3Zll0MlhNZURadWVTUVJVd2g3SVZnZlgxVUxUUmpNek51d1AyaktpRzdDUFd4cHp2dXdiOHZZb1pVeGZmb085c2RXVmFOM3d1RGNJVnRKVEEwalBGOXNmY0NVRVhjVDlzeVd4dTJHZlR2R2pwTmpGa2lTVVpXSnppbUhKZ3htNEhUc0Q1U3k0ajhpMWF5cWh1TnZEWWtMb2xaSVV2STc3STh2UlFoTkdFelVDTUtxVDlZSFNCblJBZXdScDJ5VmRTUDI3Umd6cXJoNVRsWHNqLzN4cFZnL05HRXdRZitNL1lGUlZ0eVA5ZzV2eHRld2I4UFlzWFhVQ2tsS2JzRCsrRkpzR0pvd21KQmhoRjJnckErS3N1S2NPR1dycExkajMzNng0NVNvRlpLVWFIbmd0RU1UQmhOeEl2WUhRMW5SUlJnZWxQcE53Nzc5WXNkUG9sWklVcUxsZ2ZNSVRSZzAxZ3JjaGYyQlVGWThCb3lOVXJucWVRTDc5b3NaOHlsdTkwMnhwK1dCOHdwTkdEUlN0ZUd5cjhjcFcrWDhML1p0Rnp1MEw0QmZXaDQ0ejlDRXdaSzFBTGRqMy9CbFJUZHdXSlRLVmN0N3NHKzcyUEhocUJXUzFHaDU0SHhERXdaTGRBejJEVjVtUEE5c0VhVnkxYkVkOXUwV095Nk5XaUZKalpZSHpqODBZYkFrM3A3MUhpcitpdmFHcjBjTDRiNjVkYnZGak9Wb3BVanZ0RHl3ajlDRXdZSWRqbjBqbHgyZmpWRzRDdmtGOW0wV093NklXaUZKalpZSDloZHVKd3hhWHBFK0F1d05iR3VZUTluMkI2NG5URHlSb1cxTzZDaDZNaCs0d2pvSktjeEN3bjRXYzZ3VGtXZzJCNDRGVHVyNTlUMkVGVitsU1hPQk5kajM4TXFNNXdnSGxBeHRGK3piSzNiY0ZiVkNraUl0RCt3N05HRXdvbTlnMzZCbHg1Vm9Qa0F0Mm9ERjJMZFg3SmdSczBpU0pDMFA3RDgwWVRDQ3lZUmhNK3ZHTERzK0VhTjRGWEFwOW0wVk8wNkxXaUZKVWRYV082bHlhTUpnazg3RXZoSExqazdneUJqRmMrNmoyTGRWN1BodDFBcEppclE4Y0RYRDdZVEJJZzJqV2tzRTk4WXlZUHNJOWZOc0oremJLWFlzQnphS1dTUkprcFlIcm01b2hjRTZ2UmI3UnJPSUI0RUpFZXJuVlF2d0xQYnRGRHRlRjdOSWtpUXRENnhJZXNKZ1NoUFJIaWM4UGpQYk9wR1NUUVoyQlg1S3VKY2tHOW9GZUxWMUVwRXRBdjVrbllRVXFnTVlqM1lGcmJLUndKNkUwYURaaEF1K0JhWVpKV3cyWVdhbGRhL05JczZKVUQrdjNvbDkrOFNPUjZKV1NGS2w1WUVWNjRZbURBN2hIT3dieVNyZUhhRitIbTFCK09CWXQwL3M4RGFxSWYzVDhzQ0sva0lUQnZ1eE1XR3hIT3ZHc1loVndCN05sOUNsZTdCdm45ang2YWdWa2xScGVXREZZS0VKZzMyOEJmdEdzWXJuZ1ZuTmw5Q2RyMkxmTnJIampxZ1ZrcFQ5RHZ2alRaRjJ2RURZVW5veXdtK3dieENyZUFTWTJud0pYVGtTKzNZcElyYUpXU1JKbHBZSFZ0UWFpNEYvSmExSitxWGJBcC9Md05ZYXR3QmptNjZpSDZPQjFkaTNTK3o0VU13aVNkSnV3djU0VStRVFYxSHdoV0Jya1MvZXBHZUFUMW9uWVdnM3dpaklDT3RFRXJHU3NMNjZOOGRhSnlDbDBaTStVbzhEZ1d1bzhPWnhyY0MxMlBmRUxPTUhoTVZ3QkQ2T2ZYdkVqaTUwdTZjcTJvQkhzVC9tRkhuRmpjQndDcEQ2UFliZU4zOEsxWDFVWWlkQzQxOXBuVWdDbGdHbld5Y1JXUXZ3TUhDYmRTSlN1TFdFeDFtMUI0alVZeHJoZStJcTYwU3NmQXI3WHBoMXZML3BLdnJ3T1BadEVUc3VqMW9oU1ptV0IxWTBFcXVBcllnczlSR0FYdGNEYndBMnMwN0UwQkdFZVJGL3QwN0UyRXpDMHBxZXpBRE9CMVpZSnlLRjAvTEEwb2hod0JyZ0N1dEVyT3hCMkVMWHVpZG1HWjNBaWMwV01uT0hZdDhPUllSR2VLcGpLdUdLenZxWVUrUVZUMUh4K1dEbll0OEkxdEVKdkszWlFtWnNPRDRmRDcwbVpwRWtlVm9lV05GSXpLSENSZ1AzWTk4STF0RkpXQzJ4cW42T2ZSdkVqbTdDclFDcGh1M1E4c0NLK2lQcTkzN0s2d0QwWnlYd2RzSzlrQ3ByQXk0RWpyRk94TWdsMWdrVW9BVTQzam9KS2MwRHdLWFdTVWgydG9qNVlybDFBQ0JNZ3Z1TWRSSUpHQTVjUkRXM2xmd0RZUlRFbXpkYkp5Q2wwc0pBVXErTllyNVlqaDBBZ0M4RGY3Vk9JZ0VqZ0l1cDNuUEZpNERyckpNb3dHNkVweHlrR3E0QmJyWk9Rckt5UE9hTDVkb0I2Q2JNaGw5a25VZ0NSZ0svcG5wWGoxNXZBMVQ5S1krcTBTaUExT01SNndSU2NoejJrekpTaVM3ZzFPYkttWlZ0c2E5NUVmRVlGWC9VcDJLMFBMQ2kxdWdtOGh3QUQ3NlBmY09rRXQzQXg1b3JaMWJ1dzc3bVJjUytNWXNreVRzRCsyTk9rWDVFdjEyVTZ5MkFkWjBKUEdTZFJDSmFnQzhCNTFHTnE4aUxyQk1veUVuV0NVaXBmZ0FzdEU1Q2t2Y2o2d1JTdFJ2aDBVRHJIbHBLOFNQOGI2QzBQZloxTGlLV0FLTWkxa25TOTNuc2p6dEZ1dkVzK2s0WTFDZXhiNlRVNGlMQ2t3S2UzWXQ5bll1SXFrM3FyTG9waEhWT3JJODdSWnFoTlVLRzBBWmNobjFEcFJaL0JTWTFYdGJrZlJiN0doY1JXaVNtZXI2TC9YR25TQysraWRSa0UrQUo3QnNzdFhnSW1OVjRXWk0yRi92NkZoRWRhTVp2MVdoNVlFWGYrRC95MmJVM0NUdWhvYlQrWWlGd1VCTjFUZGs5Mk5lM2lQaEV6Q0pKRm42SC9YR25zSTlPNEZOVVl6SjNkQ2RoMzRBcFJqdHdjdU5sVGRabnNLOXRFZkVZUHA3VWtkcnRqLzF4cDdDTmF3a1hzdEtFYjJQZmtLbkdlZmc2c2N6QnZxWkZ4YUVSNnlSNXVBSDc0MDVSZnR4QU5mZDJLY1JJNEVic0d6WFZ1QWhmajVYY2pYMU5pMm9ucVpZVHNEL3VGT1dGVHZ3RjJSeDRCdnNHVGpWdXg4L21NNS9HdnA1RlJEdmhFVEdwampiQ211L1d4NTZpMk5DSnZ3UUhFbVpVV3pkMnFyRVlId2ZoZHRqWHNxajRhTVE2U1I2MFBMRGYwSW0vWkIvR3Z0RlRqbTdnaStUL3VNbXQyTmV5aUhnSXpRYXVtdEhBQXV5UFBVVzgwSW5mU0F2d00rd1BnTlRqQ21EVEJtdWNnck93cjJGUmNYakVPa2tldER5d2o5Q0pQd0dqZ091eFB4aFNqNmVCZlJxc3NiVXArTjBUNHBLSWRaSThhSG5ndkVNbi9zUk1RWHR2MXhMdHdBZkljOWpaNjBJcVhjQTJFZXNrZWZnTzlzZWVvcjdRaVQ5aE00SG5zVDlJY29qTHlHODVXcytQVVAxM3hEcEpIdWFnNVlGekNaMzRNN0Vmc0JyN0F5YUhlQjQ0cHJFeW05Z0lXSVI5M1lxSXhjQ1llS1dTVFB3VysyTlBNWERveEoraHR4Tm12MXNmUExuRStlUno4dkc4Q3VRcEVlc2tlZER5d0dtR1R2eVoreFQyQjFGTzhSandtb1lxWGE3WFlGK3JvdUp1OHB5YkljM1I4c0RwaEU3OGp2d1A5Z2RVVHRFQmZKYjAxd3g0RVB0YUZSV0hSYXlUNU1IejNKWmNRaWQraDRZVEpydFpIMXk1eGUzQXJnM1V1eXlmd2I1R1JjV2ZJOVpKOHFEbGdlMUNKMzdueGdOM1luK2c1UllkaEowRlU1d2I4QXA4ei9IWU1WcWxKQmRhSHJqYzBJbS9RcVlSN25GYkgzUTV4b1BBQWZXWHZIQlhZVitib3VJSEVlc2tlZER5d09XRVR2d1ZOUjE0QXZzRE1NZm9CaTRFSnRkYjlBSzlGZnU2RkJYdGhOMHVwVnEwUEhCeG9STy9zQjB3SC91RE1kZDRCamkrN3FvWFl5UytyNWorTTE2cEpCTmFIamgrNk1RdjY5a0JXSWo5Z1pselhBRzhxdDdDRitBYzdHdFJWQ3dGSnNRcmxXUkN5d1BIQ1ozNFpVQjdBc3V3UDBoempnN0NBa0tiMUZuN21HYmllekxneCtPVlNqS2g1WUdiQzUzNHBTYXZBWlpqZjhEbUhnc0pXL1VPcTYvODBWeFpRNDY1eG56QzVEQ3BGaTBQWEgvb3hDOTFPeFR0R3hBcjdzZG1YL3UzTkpodkxuRkd2RkpKSnJROGNPMmhFNzgwNVRqQ2NMYjFnZXdsZmduTXFxc0Ztak1DM3p0QVBrRlkwRXFxNVNic2o3MlU0M0hnalExWFYyUWRKNkw3YmpHakEvZ3U0ZEhMTW55NWhQZGtHYWZHSzVWa1Fzc0REeHcvQlVZMVhscVJEYjBGV0lQOXdlMHAyZ2tUQmJlb294MGE0WDB5NER6Q1k0OVNIVzNBbzlnZmU2bkZPV2pETENuSTBjQXE3QTl5YjdHQ3NLencxTnFib201L1NlQjlGaGxueGl1VlpFTExBNjhmRjZHVHZ4VHNjTFFZUjFHeGxMRGI0UGhhRzZNTzNvZE1uMEZQQkZUTkdPQUY3SSs5Rk9JSmRQeExTZllqbkt5c0QzcXZzWXd3SXJCbHJRMVNnMkhBa3dtOHR5TGpJOUdxSmJuUThzQWhqbTIya0NMMTJCWDF2b3VPZHNJZUE2K3NzVTJHOHJFRTNsT1JzUUFZRjZsV2tnY3REeHdlOVJNcDNmYUVvVmZyRDREMzZBSXVBZmFwclZrR05CSC9penY5VzVNMWt2eFVmWGxnaS9WRlJJQ3dnZERUMkg4SXFoTFhBRWNCcmJVMFRqKytuY0I3S0RJV29UMENxcWJLeXdQcjZsL016UUFleHY3RFVLVjRoRENrUDZXRzlsblhISHcvRXJnVytGeWROWkg4VlhWNVlGMzlTeEttQVhkai80R29XcXdHL28rd1BHcXQvcHhBM2tYR1VtQnlIZldRL0ZWeGVXQmQvVXRTeGdLWFl2L0JxR284UUJnVkdPcmtkMlFDdVJZZDV3NVJBL0huQnV5UHV6SkRWLytTbk9IQTk3RC9jRlE1bHZlMHdZSDBQMWVnQlhnd2dUeUxqRFdFMngxU0hkN1h1bGczZFBVdlNUdUw2azdNU1NtZUpxd3BzRytmOW5sL0Fya1ZIWmNnVmRKR21CdGpmZHlWRWJyNmwrUWRqNVlPVGludUphdzB1QzFoMWJDRkNlUlVkTHdPcVpJcUxBK3NxMy9KeHY1VTQwU1RVM1FEMXdMM0pKQkwwWEVuNGNwUXFtRTBZVUVvNitPdXlORFZ2MlJsSnY3dk9TdlNEVzBYWEMyZWx3ZlcxYjlrYVRKaEVSdnJENUNpZWpFZjJCaXBDcy9MQSt2cVB4SU5DNVpyRmZBendtakFxNHh6a1dvWlEzank0UXJyUktRVUt3aUxrKzFxblVoa053SWZ0MDVDcEZrZkFqcXc3MDBycWhPcmdWbElWWGhjSGxoWC8rTEdmc0N6Mkgrb0ZOV0pxd2dqQVZJTm5wWUgxcjEvY1dkTDRIcnNQMXlLNnNUYmtLcnd0RHl3cnY3RnBXSEFsN0QvZ0NtcUVjOFJ0a1dXYXZDd1BMQ3UvZ3VnU1lCcDZDWk16bnFVME1zZGJwdU9PRGVXc0Yzdzc2MFRrVklzSXl3Um5MUDNFRlk0RkhGdEorQXg3SHZjQ3QvUkJleURWRUh1eXdQcjZyOGdtZ3lVcHNuQVQ5QVNybEtzdTRGMzlmeThnN0NCVWw5TENTTlU5UmdOak96NWVSc3dudkFFd2dzOTBWVjNwdEtzTTRDdld5ZlJvQ09BUDFrbjRaRTZBT2xxSTJ4cisxbDBTMEQ4NkNiYzZyb1R1Qm40TTNDWGFVYlZNQnA0RXRqRU9wRTYzUWpzYloyRWlKVTlnSWV3SDRaVEtJcUtlY0FYZ0cyUUl1VzRQTEJtL2t2bGpTWHNiVy85WVZRb2lveHU0QmRvbGN5aTVMWThzTzc5aTZ6amVMU3JvTUovZEJFNnZKT1EyTDZEZmZ2V0dycjZGK2xqR25BbDloOU9oYUxvZUE0NEJva3BsK1dCZGZVdk1vQVc0Q3lnSGZzUHFrSlJaSFFENXhFV3k1STRjbGdlV0ZmL0lrUFlCYmdmK3crclFsRjAvSWFYSHkyVTVxUytQTEN1L2tWcU5CcjRLdENKL1FkWG9TZ3lMaU1jNzlLOGxKY0gxdFcvU0oxMkJtN0Qvc09yVUJRWmw2QWx6R000QWZ1MjdDOTA5Uy9Tb09IQUp3bXJybGwva0JXS291Sy9rV2FsdWp5d3J2NUZtclF0ZWxKQTRUZTZnYU9RWnAyQmZWdXVHN3I2RjRta0ZUaWRzSmE3OVFkYm9ZZ2R6d0diSXMwWURTekF2aTE3UTFmL0pkTzlOTC9XQXJjQ1B3SmVBY3cxelVZa3JySEF4bWhMNDJaMEVEWnEydDg2RWNLYS94KzNUa0xFcXpjUnJwcXNlL2tLUmF6b0JIWkFtcEhLOHNDNitqZWdFWURxdUJmNExtRVJvVDFRMjB2K1dnbTNBUzYyVGlSaks0QVp3SzZHT2VqcVg5dUdzQUlBQUFQMlNVUkJWS1JFY3dqN2ExdjMraFdLWnFNVG1JazB3M3A1WUYzOUcybTFUa0JNUEVqNDBMMFplTW80RjVGbXRBR25XQ2VSdVFleG0wdHhJK0ZpUkVRTWpBWStocDRXVU9RYmp4RnViVW5qckpZSDF0Vy9TQUkyQnk0Z2o1M0NGSXErc1F2U3JMS1hCOVp6LzhaMEMwQjZQVXNZU24wVjhBZmpYRVRxZFlCMUFnNThwZVQvNzNNbC8zOGlVcVBEME40Q2luemlGMGl6eWx3ZVdGZi9DZEFJZ0F6a3o4QnV3QnVBdTQxekVSbUsxZ05vWGhkd2Jrbi9sNjcrUlRJeERIZzNNQS83S3oyRm9yOW9SMnRieEZERzhzQzYraytFUmdDa0ZwM0E5NEJad1B2Um80T1NuaEdFaWF6U25KWEF0d3YrUDNUMUw1S3hFY0E3U0hNN1VVVjFZeFlTdzJSZ01jVzAwYlVsdmc4UktkQUk0RFRnY2V5Ly9CV0s3WkZZL3BYNDdkTUo3RlRtbXhDUjRnMERUZ1R1d3Y0a29LaHVhQVFnbmpiZ0t1SzJ6NmRLZlFjaVVycDlnVXV3UHhrb3FoY1RrWmltQXZjUnAyMytENjNXS0ZJWit4Q2V6ZTdFL3NTZzhCL3RhRkp6RWFZUzF1dHZwbTIraVo3UUVLbWtWd0JmQWhaaWY1SlErSTA3a2FJTUJ6NVBlRUtnbmpaNUZqak9JRjhSU2N3NDRBUEF3OWlmTEJUKzR2K1FvbTFKNk13L3hlQnRjUXZoY2VGUk5tbEtyWFJQUnNyV0Nod01uQW9jUzVoQUtOS3NNNEZ2V0NkUkVTM0FiTUlHVEpzUm5nWmFCVHdLM0E0OFk1ZWExRU1kQUxHME9XRTlnZmNCMDQxemtiek5Kb3d1aVVpTjFBR1FGQXdEamdMK0dUZ0NqUXBJZlI0bWRBQkVSQ1JqbXdGbkFYZGdmMTlaa1VmOEd5SlNONDBBU01yMkl0d2lPQUhZeERnWFNWTW5NQVBkZHhZUmNha05PQVM0RUZpRy9SV25JcDM0SGlMU0VJMEFTRzdHQU1jQWJ3SU9BemF5VFVjTWRRRGJBWTlaSnlJaUl1VWFCUnlOUmdhcUd2K05pRFJNSXdEaXhTamdjTUxvd0pIQXByYnBTTUdlQUY0RnJERE9ReVJiNmdDSVI2M0Fub1JIQzQ4Q2RyQk5SeUxyQkY0TFhHT2RpSWlJcEcwNlllWEJYd0NMc0IrNlZqUVhIMFpFUktST2JZVFJnVThDVndOcnNEK2hLV3FQODlISXBVZ1UraUJKMVkwRzlnYjJCdzRnZEE3MFpFR2FmZ3k4RStpMlRrVEVBM1VBUk5ZM0V0Z0QySSt3RU5HZXdCVFRqQVRnYThDSGdDN3JSRVM4VUFkQVpHZ3plYmt6c0NkaFVxRkdDY3JSVHJqbi8wM3JSRVM4VVFkQXBIN0RnRG5BWEdCN1lGZEN4MENQSHNaMUgzQWlZWXRaRVlsTUhRQ1JlQ1lTT2dSejEvbHhSOVF4cU5keTRQOEJYd0pXRytjaTRwWTZBQ0xGMndMWXRpZG05dm41eG9aNXBXWWhZWmIvZWNEenhybUl1S2NPZ0lpdDhjQldoQjN0cHZYOGZEcGhXK1ROQ2FNSG14SWVYL1NvSGJnY3VKaXdUc05LMjNSRXFrTWRBSkgwdFJBNkFWTjZmcHpZRXhONm92Zm5vd2dkaXBHRXh4dkhBQ01Jb3d5dDY3emVSajEvdHlndkVsYnI2MnNOTUkrd2pPL2R3UFhBVFlRaGZ4RXAyZjhIcjVZU01URHg2TXNBQUFBQVNVVk9SSzVDWUlJPSIvPgo8L2RlZnM+Cjwvc3ZnPgo=" />--%>
<%--											</td>--%>
<%--											</c:if>--%>
										</tr>
									</c:forEach>
									
									<tr>
                                    <td colspan="20" style="text-align: center;">
                                        <div id="no-data">
                                        <p></p>
                                        </div>
                                    </td>
                                </tr>

									
									
								</tbody>
							</table>

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
      <input type="hidden" id="pgnum" >
      <input type="hidden" id="FromDate" >
      <input type="hidden" id="From1Date" >
     <!--  <input type="hidden" id="TXNType1" > -->

	  <div id="exportData" data-export="${exportData}"></div>
      <script>
         document.getElementById("pop-bg-color").style.display ="none";
         var fromDateServer = document.getElementById("FromDate").value="${paginationBean.dateFromBackend}";
         var from1DateServer = document.getElementById("From1Date").value="${paginationBean.date1FromBackend}";
         
       //  var TransactionType = document.getElementById("TXNType1").value="${paginationBean.TXNtype}";  /*  ${paginationBean.TXNtype}*/
         
        /*  if(${paginationBean.itemList.size()}==0){
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
         } */
         
        <%-- if(${paginationBean.itemList.size()}==0){--%>
        <%--document.getElementById("no-data").innerText = "No data available";--%>
        <%--	}--%>

		 var exportData = document.getElementById("exportData").getAttribute("data-export");
		 if (${paginationBean.itemList.size()} == 0 && exportData === "noRecords") {

			 document.getElementById("exampleModalCenter").style.display = "block";
			 document.getElementById("pop-bg-color").style.display = "block";
			 document.getElementById("page-table").style.display = "none";
			 document.getElementById("innerText").innerHTML = "Sorry, No Records Found";
			 document.getElementById("innerText").style.fontWeight = "400";
			 document.getElementById("innerText").style.color = "#171717";
			 document.getElementById("nxt").style.cursor = "not-allowed";
			 document.getElementById("nxt").style.opacity = "0.6";
			 document.getElementById("nxt").disabled = "disabled";
		 } else if(${paginationBean.itemList.size()} == 0){
			 document.getElementById("no-data").innerText = "No data available";
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
          var size ="${paginationBean.querySize}";
          console.log("size "+size)

          
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
               
               Pagination.size = Math.ceil(${paginationBean.querySize} / 20);
               
               // Pagination.size = ((${paginationBean.currPage})+4) ||100;
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
         	/*  Last: function() {
                 Pagination.code += '<a onclick="dynamic(((Pagination.page)+1))">'+ ((Pagination.page)+1)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+2))">'+ ((Pagination.page)+2)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+3))">'+ ((Pagination.page)+3)+ '</a>'+'<i>...</i>';
             },  */
         
             // add first page with separator
           /*   First: function() {
             	if(Pagination.page==1){
             		 Pagination.code += '<i>...</i>'+'<a onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';
         			 
         		 }
             	else{
                 Pagination.code += '<a>1</a>'+'<i>...</i>'+'<a onclick="dynamic(((Pagination.page)-1))">'+((Pagination.page)-1)+'</a>'+'<a onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';
             	}
             }, */
         
             First: function() {
                 Pagination.code += '<a onclick="dynamic(1)">1</a>';
                 if (Pagination.page > 3) {
                     Pagination.code += '<i>...</i>';
                 }
                 for (var i = Math.max(2, Pagination.page - 1); i <= Pagination.page; i++) {
                    //directly goes to 1 instead of re-arranging
                     Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
                 }
             },

             // arranges ...
             Last: function() {

                 // total page
                 var lastPage = Math.ceil(${paginationBean.querySize} / 20);
                 // three pg no after 1st pg no
                 if (lastPage > Pagination.page + 3) {
                 // generate <a> tag for 3 pg no
                     for (var i = Pagination.page + 1; i <= Pagination.page + 3; i++) {
                         Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
                     }
                     Pagination.code += '<i>...</i>';
//                      Pagination.code += '<a onclick="dynamic(' + lastPage + ')">' + lastPage + '</a>';
                 } else {
                 // if less than 3 page generate <a> tag
                     for (var i = Pagination.page + 1; i <= lastPage; i++) {
                         Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
                     }
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
                	    '<a onclick="previous(${paginationBean.currPage})" style="width:7rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px; " id="previous">&#60;&#60; Previous</a>', // previous button
                	    '<span></span>',  // pagination container
                	    '<a onclick="next(${paginationBean.currPage})" style="width:6.5rem;font-weight:bold;font-size:14px;height:2.3rem;padding-top:6px;" id="nxt">Next &#62;&#62;</a>'  // next button
                	];

         
                 e.innerHTML = html.join('');
                 Pagination.e = e.getElementsByTagName('span')[0];
                 Pagination.Buttons(e);
                 
                 if (${paginationBean.currPage} == 1) {
                	 var previousButton = document.getElementById("previous");
                	 previousButton.style.pointerEvents = "none";
                	 previousButton.style.opacity = "0.5";
                	 }
                	 // my chnages
                	 if (${paginationBean.currPage} == Pagination.size) {
                	 var nextButton = document.getElementById("nxt");
                	 nextButton.style.pointerEvents = "none";
                	 nextButton.style.opacity = "0.5";
                	 }
                	 if (Pagination.size == 0) {
                	 var nextButton = document.getElementById("nxt");
                	 nextButton.style.pointerEvents = "none";
                	 nextButton.style.opacity = "0.5";
                	 }
                	 if (Pagination.size == 0) {
                	 var paginationContainer = document.getElementById("pagination");
                	 paginationContainer.style.display = "none";
                	 }

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
      
      <script>
         var body = document.body;
         var initialOverflow = body.style.overflow;

      </script>
<script>
	function close_aproval() {
		document.getElementById("confirmation-modal-IPN-id").style.display = "none";

	}
	<%--function sendFpxIpn()--%>
	<%--{--%>
	<%--	var sellerOrderNumber = document.getElementById("invoice-for-approve").value;--%>
	<%--	var mid = document.getElementById("mid-for-approve").value;--%>
	<%--	var tid = document.getElementById("tid-for-approve").value;--%>

	<%--	console.log('AJAX CALLED ',sellerOrderNumber,' mid : ',mid,' tid : ',tid);--%>

	<%--	document.getElementById("confirmation-modal-IPN-id").style.display = "none";--%>


	<%--	$("#overlay_gifloader").show();--%>

	<%--	$.ajax({--%>
	<%--		type: "GET",--%>
	<%--		url: "${pageContext.request.contextPath}/transactionUmweb/sendFpxIpn",--%>
	<%--		data: {--%>
	<%--			"sellerOrderNumber": sellerOrderNumber,--%>
	<%--			"mid": mid,--%>
	<%--			"tid": tid--%>
	<%--		},--%>
	<%--		success: function (result) {--%>
	<%--			if (result.responseCode === '200') {--%>

	<%--				$("#overlay_gifloader").hide();--%>

	<%--				document.getElementById("successmodalforrefund").style.display = "block";--%>
	<%--				document.getElementById("overlay-popup").style.display = "block";--%>
	<%--				document.getElementById("transactionIdOfSuccess").innerHTML = result.transactionId;--%>
	<%--				document.getElementById("amountOfSuccess").innerHTML = "RM "+ result.amount;--%>


	<%--			} else if (result.responseCode == '0001') {--%>

	<%--				$("#overlay_gifloader").hide();--%>

	<%--				document.getElementById("modalforfailedrefund").style.display = "block";--%>
	<%--				document.getElementById("overlay-popup").style.display = "block";--%>

	<%--			}else if(result.responseCode == '0002'){--%>

	<%--				$("#overlay_gifloader").hide();--%>

	<%--				document.getElementById("modalforfailedrefundcustomized").style.display = "block";--%>
	<%--				document.getElementById("overlay-popup").style.display = "block";--%>

	<%--			}--%>
	<%--		},--%>
	<%--		error: function (xhr, status, error) {--%>
	<%--			console.log("Error: " + error);--%>

	<%--			$("#overlay_gifloader").hide();--%>
	<%--			alert("An error occurred. Please try again later.");--%>
	<%--		}--%>
	<%--	});--%>

	<%--}--%>
	function sendFpxIpn() {
		const sellerOrderNumber = document.getElementById("invoice-for-approve").value;
		const mid = document.getElementById("mid-for-approve").value;
		const tid = document.getElementById("tid-for-approve").value;

		console.log('FETCH CALLED', sellerOrderNumber, 'mid:', mid, 'tid:', tid);

		document.getElementById("confirmation-modal-IPN-id").style.display = "none";
		// document.getElementById("overlay_gifloader").style.display = "block";

		const params = new URLSearchParams({
			sellerOrderNumber: sellerOrderNumber,
			mid: mid,
			tid: tid
		});
		console.log('---->',`${pageContext.request.contextPath}/transactionUmweb/sendFpxIpn?`,params.toString());

		const url = `${pageContext.request.contextPath}/transactionUmweb/sendFpxIpn?`+params.toString();

		console.log('url : ',url);


		fetch(url, {
			method: "GET"
		})
				.then(response => response.json())
				.then(result => {
					console.log('result : ',result);
					// document.getElementById("overlay_gifloader").style.display = "none";

					if (result.responseCode === '200') {
						console.log('inside success ',result.responseCode);
						document.getElementById("successmodalforrefund").style.display = "block";
						// document.getElementById("confirmation-modal-IPN-id").style.backgroundColor = "gray";
						console.log('--->',document.getElementById("successmodalforrefund").style.display);
						// document.getElementById("overlay-popup").style.display = "block";
						document.getElementById("transactionIdOfSuccess").innerHTML = result.ppId;
						// document.getElementById("ipnUrl").innerHTML = "RM " + result.url;

					} else if (result.responseCode === '0001') {
						console.log('inside failure ',result.responseCode);
						document.getElementById("modalforfailedrefund").style.display = "block";
						// document.getElementById("overlay-popup").style.display = "block";

					} else if (result.responseCode === '0002') {
						document.getElementById("modalforfailedrefundcustomized").style.display = "block";
						// document.getElementById("overlay-popup").style.display = "block";
					}
				})
				.catch(error => {
					console.error("Error:", error);
					// document.getElementById("overlay_gifloader").style.display = "none";
					// alert("An error occurred. Please try again later.");
				});
	}

	function openIpnApprovalModal(mid,tid,sellerOrderNumber)
	{
		var userName = '${loginname}';
		console.log('--->',userName);
		<%--console.log('user name ',${loginname});--%>
		console.log('Ipn Modal Called ',sellerOrderNumber,'  mid ',mid,' tid ',tid);
		document.getElementById("invoice-for-approve").value = sellerOrderNumber;
		document.getElementById("mid-for-approve").value = mid;
		document.getElementById("tid-for-approve").value = tid;
		document.getElementById("confirmation-modal-IPN-id").style.display = "block";

	}
	function closeForSuccessIPn()
	{
		document.getElementById("successmodalforrefund").style.display = "none";
	}
</script>


	  <div class="modal fade bd-example-modal-xl pop-body"
		   style="width: 500px !important; height: 370px !important; font-size: 18px;"
		   id="successmodalforrefund" tabindex="-1" role="dialog"
		   aria-labelledby="mySmallModalLabel" aria-hidden="true"
		   style="text-align:center;">
		  <div class="modal-dialog modal-xl">
			  <div class="modal-content "
				   style="padding: 0 !important; margin: 0 !important;">
				  <p class="pop-head"
					 style="background-color: #f9f9f9; width: 100%; height: 60px; color: #005baa; padding-top: 15px; font-weight: 600; border-bottom: 2px solid #ffa500; font-size: 18px; margin-bottom:12px;">Confirmation</p>
				  <img id="successicon"
					   src="${pageContext.request.contextPath}/resourcesNew1/assets/Successful.svg"
					   width="60px !important; height:60px !important;">
				  <p id="innerTextOfSuccess"
					 style="font-size: 18px; margin-bottom: 0;">The IPN has been
					  sent successfully</p>

				  <div id="detailsofsuccessrefund"
					   style="display: inline-block; border: 2px solid #005baa; border-radius: 10px; padding: 2% 3%; text-align: left; margin: 2% 0;">
					  <p
							  style="font-size: 15px; font-weight: 600; color: #005baa; margin-bottom: 7px; text-align: left;">Details</p>


					  <table>
						  <tbody>
						  <tr style="border-bottom: none;">
							  <td
									  style="font-size: 15px; text-align: left; margin-bottom: 2px; padding-left: 0; padding-bottom: 0;">
								  Transaction ID :</td>

							  <td style="width: 10px;"></td>
							  <td
									  style="font-size: 15px; display: flex; align-items: baseline; /* margin-bottom: 2px; */ padding-bottom: 0;">
								  <span id="transactionIdOfSuccess" style="padding: 0 0px;"></span>
							  </td>
						  </tr>
<%--						  <tr style="border-bottom: none;">--%>
<%--							  <td--%>
<%--									  style="font-size: 15px; text-align: left; margin-bottom: 2px; padding-left: 0; padding-bottom: 0; padding-top: 5px;">--%>
<%--								  Configured Ipn Url :</td>--%>

<%--							  <td style="width: 10px;"></td>--%>
<%--							  <td--%>
<%--									  style="font-size: 15px; text-align: left; margin-bottom: 2px; padding-top: 5px; padding-bottom: 0;">--%>
<%--								  <span id="ipnUrl" style="padding: 0 0px;"> </span>--%>
<%--							  </td>--%>
<%--						  </tr>--%>
						  </tbody>
					  </table>
				  </div>
			  </div>

			  <div class="modal-footer"
				   style="display: flex; align-items: center; justify-content: center;">
				  <button type="button" class="btn btn-secondary"
						  data-dismiss="modal" id="closeforsuccessrefund"
						  onclick="closeForSuccessIPn()"
						  style="width: 30%; height: 38px !important; background-color: #005baa; color: #fff; font-size: 16px; border-radius: 50px !important; font-family: 'Poppins', sans-serif;">Close</button>
			  </div>
		  </div>
	  </div>



	  <style>
	.approve-modal-class {
		display: none;
		position: fixed;
		z-index: 999;
		left: 0;
		top: 0;
		width: 100%;
		height: 100%;
		overflow: hidden;
		background-color: rgba(0, 0, 0, 0.4);
	}

	.approve-modal-content {
		background-color: #fefefe;
		margin: 15% auto;
		border: 1px solid #888;
		width: 92%;
		max-width: 460px;
		border-radius: 15px;
		height: auto;
	}

	.approve-modal-content {
		position: relative;
	}
	.close-Approve {
		background-color: #005baa;
		color: white;
		border-radius: 25px;
		border: none;
		/*padding: 10px 27px;*/
		padding: 10px 15px;
		font-size: 12px;
		height: 35px;
		outline: none;
		cursor: pointer;
	}

	.close-Approve:hover, .close-Approve:focus, .close-Approve:active {
		background-color: #005baa; /* Same color as default */
		box-shadow: 5px 5px 10px -3px rgba(0, 90, 170, 0.2), /* Right shadow */
		-5px 5px 10px -3px rgba(0, 90, 170, 0.2),
			/* Left shadow */
		0 5px 10px -3px rgba(0, 90, 170, 0.2) !important;
	}
	#successmodalforrefund{
		z-index: 99;
		width: 25%;
		font-weight: 400;
		font-family: 'Poppins', sans-serif;
		text-align: center;
		top: 0 !important;
	}

</style>

<%--<script>--%>
<%--	var loginName = '${loginname}';--%>
<%--	console.log('--->',loginName);--%>
<%--</script>--%>
      
      
</body>

</html>