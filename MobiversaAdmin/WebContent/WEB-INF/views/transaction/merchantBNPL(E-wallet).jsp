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

// 

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

		
		if (e == null ||e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchBnplmerchantsummery?date='
					+ fromdateString + '&date1=' + todateString + '&currPage=' + PageNumber;
			form.submit;
			

		}
	}
	
	
	function loadSelectData1() {
		$("#overlay").show();
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

		
		if (e == null ||e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchBnplmerchantsummery?date='
					+ fromdateString + '&date1=' + todateString + '&currPage=' + PageNumber;
			form.submit;
			

		}
	}
	
	/* SEARCH IMPLEMENTATION */
	
	function loadSearch()
    {
  		$("#overlay").show();
  		var Value = document.getElementById("searchApi").value;
  		var TXNTYPE = 'BNPL1';
  		var type = document.getElementById("drop_val").value;
  		
  		document.location.href = '${pageContext.request.contextPath}/searchNew/findWallets?VALUE='
				+ Value + '&TXNTYPE='+ TXNTYPE +'&Type=' + type;
			form.submit;
  		
    }
	 
	function loadDropSearch(){
 	  var e = document.getElementById("drop_search");
     	var strUser = e.options[e.selectedIndex].value;
     	document.getElementById("drop_val").value = strUser;   
     	
     	/* For Dynamic Placeholder in SEARCH */
     	
     	if (strUser == "Ref") {
     		document.getElementsByName('search')[0].placeholder = 'Ex: Invoice Id';
     	  } else if (strUser == "Ap_Code") {
     		  document.getElementsByName('search')[0].placeholder = 'Ex: Bnpl Transaction Id';
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

		if (e == null ||e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			
		} else {
			
			document.getElementById("datevalex1").value = fromdateString;
			document.getElementById("datevalex2").value = todateString;
			
			
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/exportBnplmerchantsummery?fromDate=' +fromdateString
					+ '&toDate=' + todateString + '&export='+e2;
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
								<strong>BNPL Transaction Summary</strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
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
								<label for="from" style="margin: 0px;">From </label> <input
									type="hidden" name="date11" id="date11"
									<c:out value="${fromDate}"/>> <input type="text"
									id="from" name="fromDate" class="validate datepicker"
									onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
								<i class="material-icons prefix">date_range</i>


							</div>

							<div class="input-field col s12 m3 l3">

								<label for="to" style="margin: 0px;">To</label> <input
									type="hidden" name="date12" id="date12"
									<c:out value="${toDate}"/>> <input id="to" type="text"
									name="toDate" class="datepicker"
									onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
								<i class="material-icons prefix">date_range</i>
							</div>

<!-- 
							<div class="input-field col s12 m3 l3">

								<select name="txnType" id="txnType"
									onchange="return loadDropDate14();">
									<option selected value="">Choose</option>
									<option value="CARD">CARD</option>
									<option value="BOOST">BOOST</option>
									<option value="GRABPAY">GRABPAY</option>
									<option value="FPX">FPX</option>
									<option value="TNG">TNG AND SHOPEE PAY</option>
									
									<option value="BNPL">BNPL</option>
								</select> <label for="name">Payment Method</label> <input type="hidden"
									name="txnType1" id="txnType1">
							</div> -->





							<div class="input-field col s12 m3 l3">
								<input type="hidden" name="export1" id="export1"
									<c:out value="${status}"/>> <select name="export"
									id="export" onchange="return loadDropDate13();">
									<option selected value="">Choose</option>
									<option value="PDF">PDF</option>
									<option value="EXCEL">EXCEL</option>
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
      

		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">

						<div class="table-responsive m-b-20 m-t-15"  id="page-table">
							<table id="data_list_table1"
								class=" table-border-bottom table table-striped table-bordered">
								<thead>
									<tr>
										<th>Date</th>
										<th>Time</th>
										<th>MID</th>
										<th>TID</th>
										<th>Amount(RM)</th>
										<th>Name on Card</th>
										<th>Card Number</th>
										<th>Reference</th>
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
											<td>${dto.f268_CHNAME}</td>
											<td>${dto.PAN}</td>
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
												
												 <c:set var="fpx" value="${dto.cardType}" /> <c:if
													test="${fn:contains(fpx, 'FPX')}">
													<c:if
														test="${dto.STATUS =='SETTLED' || dto.STATUS =='NOT SETTLED' || dto.STATUS =='VOIDED' || dto.STATUS =='REFUNDED'}">
														<a href="javascript:void(0)" id="openFpxslip"
															onclick="javascript: openFpxslip('${dto.f011_AUTHIDRESP}')">
															<img class="w24"
															src='data:image/png;base64,<%=actionimg%> ' />
														</a>

													</c:if>
												</c:if>
												
												<c:if test="${dto.cardType == 'BNPL'}">
													<c:if
														test="${dto.STATUS =='SETTLED' || dto.STATUS =='NOT SETTLED'}">
														<a href="javascript:void(0)" id="openBnplslip"
															onclick="javascript: openBnplslip('${dto.f263_MRN}')">
															<img class="w24"
															src='data:image/png;base64,<%=actionimg%> ' />
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
												</c:if>
												
												<!-- BNPL VOID STARTS -->
												
												<c:if
													test="${dto.cardType == 'BNPL' }">
													<c:if test="${dto.STATUS =='NOT SETTLED' && dto.txnType !='AUTHSALE'}">
														<a
															href="${pageContext.request.contextPath}/transactionweb/cancelPaymentByMerchantBnpl/${dto.f263_MRN}">
															<img class="w24"
															src='data:image/png;base64,<%=voidimg%> ' />
														</a>
													</c:if>
												</c:if>
												
												
												
												</td>

											<td style="text-align: center;"><c:if
													test="${dto.cardType == 'MASTERCARD DEBIT' || dto.cardType == 'MASTERCARD CREDIT' || dto.cardType == 'VISA DEBIT' || dto.cardType == 'VISA CREDIT'}">
													<c:if test="${dto.STATUS =='SETTLED'}">
														<a
															href="${pageContext.request.contextPath}/transactionUmweb/refundEzywayPayment/${dto.f263_MRN}">
															<img class="w24"
															src='data:image/png;base64,<%=refundimg%> ' />
														</a>
													</c:if>
												</c:if></td>
												
												
										</tr>
									</c:forEach>
									
									<tr>
                                    <td colspan="21" style="text-align: center;">
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
      <script>
         document.getElementById("pop-bg-color").style.display ="none";
         var fromDateServer = document.getElementById("FromDate").value="${paginationBean.dateFromBackend}";
         var from1DateServer = document.getElementById("From1Date").value="${paginationBean.date1FromBackend}";
         
        // var TransactionType = document.getElementById("TXNType1").value="${paginationBean.TXNtype}";  /*  ${paginationBean.TXNtype}*/
         
       /*   if(${paginationBean.itemList.size()}==0){
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
         
         if(${paginationBean.itemList.size()}==0){
        		document.getElementById("no-data").innerText = "No data available";
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