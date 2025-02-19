<%@page
	import="com.mobiversa.payment.controller.MerchantWebTransactionController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="java.util.ResourceBundle"%>
<%
	ResourceBundle resource = ResourceBundle.getBundle("config");
	String actionimg = resource.getString("NEWACTION");
	String voidimg = resource.getString("NEWVOID");
	String refundimg = resource.getString("NEWREFUND");
	String eyeimg = resource.getString("NEWEYE");
%>

<html>
<head>

	<!-- Script tag for Datepicker -->

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
     <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">


<script type="text/javascript">
	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");

	});
</script>
<script type="text/javascript">

function openNewWin(txnID){
	//alert(txnID);
	
	var url=window.location;
	//alert(url);
	var src = document.getElementById('popOutiFrame').src;
	 src=url+'transactionweb/details/'+txnID;
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



    </script>
<!-- <script type="text/javascript">
	jQuery(document).ready(function() {

		$('#tid').select2();

		$('#devId').select2();

		$('#status').select2();
		$('#export').select2();

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

.key_hover:hover {
	cursor: pointer;
}

.hide_key {
	display: none;
}

.table-border-bottom td {
	border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important;
}

.w24 {
	width: 24px;
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

<script lang="JavaScript">
	function loadSelectData() {
		//alert("test"); 
		/* DateCheck(); */
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("tid1").value;
		var e3 = document.getElementById("devid1").value;
		var e4 = document.getElementById("status1").value;
		var e5 = document.getElementById("txnType").value;
		
		
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
		
		/* var fromdateString = fromyear + '-' + (frommon <= 9 ? '0' + frommon : frommon) + '-' + (fromday <= 9 ? '0' + fromday : fromday);
		var todateString =toyear + '-' + (tomon <= 9 ? '0' + tomon : tomon) + '-' + (today <= 9 ? '0' + today : today); */
		//alert("e"+ e);
		//alert("e1"+ e1);
		var eDate = new Date(todateString);
		var sDate = new Date(fromdateString);

		if (e == '' && e1 == '' && e2 == '' && e3 == '' && e4 == '') {
			alert("Please select conditions.");
			return false;
		} else if ((e == '' && e1 != '') || (e!= '' && e1 == '')) {
			alert("Please enter both Date's.");
			return false;
		} else if (sDate > eDate) {
			alert("Please ensure that the End Date is greater than or equal to the Start Date.");
			return false;
		} else {
			/* if(e!= '' && e!= '' && sDate> eDate)
			  {
			  alert("Please ensure that the End Date is greater than or equal to the Start Date.");
			  return false;
			  } */
			/* var e2 = document.getElementById("tid1").value;
			 vare3=document.getElementById("devid1").value; 
			var e4 = document.getElementById("status1").value;
			 */
			 document.getElementById("date11").value = fromdateString;
				document.getElementById("date12").value = todateString;
			
 			document.getElementById("tid1").value = e2;
			document.getElementById("devid1").value = e3;
		document.getElementById("status1").value = e4;

			/* document.location.href = '${pageContext.request.contextPath}/transactionUmweb/search?fromDate=' + e
					+ '&toDate=' + e1 + '&tid=' + e2 + '&devId=' + e3 + '&status=' + e4;
			form.submit; */

			document.getElementById("date11").value = fromdateString;
			document.getElementById("date12").value = todateString;
			//alert("from date:"+ e);
			//alert("todate:"+ e1);
			document.getElementById('devId').disabled = false;
			document.getElementById('tid').disabled = false;
			alert("search");
			document.form1.action = "${pageContext.request.contextPath}/transactionUmweb/search";
			//form.submit;
			document.form1.submit();
		}
	}
	//alert(e + " " + e1);

	function loadDate(inputtxt, outputtxt) {
		var field = inputtxt.value;
		//var field1 = outputtxt.value;
		//alert(field+" : "+outputtxt.value);
		//document.getElementById("date11").value=field;
		outputtxt.value = field;
		//alert(outputtxt.value);
		// alert(document.getElementById("date11").value);
	}

	function loadDropDate() {
		// alert("strUser.value"); 
		var e = document.getElementById("tid");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("tid1").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("tid1").value);

	}

	function loadDropDate11() {
		//alert("strUser.value");
		var e = document.getElementById("status");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("status1").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}

	function loadDropDate13() {
		//alert("loadDropDate13");
		var e = document.getElementById("export");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("export1").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}

	function loadDropDate12() {
		//alert("strUser.value");
		var e = document.getElementById("devId");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("devid1").value = strUser;
		//alert("data :"+strUser+" "+document.getElementById("devid1").value);

	}

	function loadDropDate14() {
		//alert("loadDropDate13");
		var e = document.getElementById("txnType");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("txnType1").value = strUser;
		//alert("txntype: "+strUser);
		//document.getElementById("searchTxnType").value=strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}
	
	function loadData(num) {
		var pnum = num;
		//alert("page :"+pnum);
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e2 = document.getElementById("tid1").value;
		var e3 = document.getElementById("devid1").value;
		var e4 = document.getElementById("status1").value;

		e = document.getElementById("date11").value;
		e1 = document.getElementById("date12").value;
		e2 = document.getElementById("tid1").value;

		e3 = document.getElementById("devid1").value;
		e4 = document.getElementById("status1").value;
		//alert(e + '  '+ e1);

		/* if (e == null || e == '' ) {
			alert("Please Select FromDate");
			form.submit = false;
		} else if (e1 == null || e1 == ''){
			alert("Please Select ToDate");
			form.submit = false;
		} else */if ((e == null || e == '') && (e1 == null || e1 == '')
				&& (e2 == null || e2 == '') && (e3 == null || e3 == '')
				&& (e4 == null || e4 == '')) {
			//alert('both $$ ##');
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/list/'
					+ pnum;
			form.submit;
		} else {
			//alert("else : "+e+" "+e1);
			document.getElementById('devId').disabled = false;
			document.getElementById('tid').disabled = false;
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/search?fromDate='
					+ e + '&toDate=' + e1 + '&currPage=' + pnum;

			//document.forms["myform"].submit();
			form.submit;// = true; 

		}

	}

	function DateCheck() {
		var StartDate = document.getElementById('datepicker').value;
		var EndDate = document.getElementById('datepicker1').value;
		var eDate = new Date(EndDate);
		var sDate = new Date(StartDate);
		if (StartDate != '' && StartDate != '' && sDate > eDate) {
			alert("Please ensure that the End Date is greater than or equal to the Start Date.");
			return false;
		}
	}

	function loaddata() {
		//alert("test data");
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("tid1").value;
		var e3 = document.getElementById("devid1").value;
		var e4 = document.getElementById("status1").value;
		var e5 = document.getElementById("export1").value;
		
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
		
		/* var fromdateString = fromyear + '-' + (frommon <= 9 ? '0' + frommon : frommon) + '-' + (fromday <= 9 ? '0' + fromday : fromday);
		var todateString =toyear + '-' + (tomon <= 9 ? '0' + tomon : tomon) + '-' + (today <= 9 ? '0' + today : today); */
		//alert("e"+e);
		//alert("e1"+e1);
		//alert("e2"+e2);
		//alert("e3"+e3);
		//alert("e4"+e4);
		//alert("e5"+e5);

		/* 	if (e == null || e1 == null || e == '' || e1 == '') {
				//alert("picker :"+e + "  "+ e1);
				e = document.getElementById("dateval1").value;
				e1 = document.getElementById("dateval2").value;
				//alert("hidden : "+e + "  "+ e1); */
		if (e == null ||e == '' || e1 == null || e1 == '') {
			alert("Please select date(s)");
			//}
		} else {

			/* document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			document.getElementById("dateval3").value = e2;
			document.getElementById("dateval4").value = e3;
			document.getElementById("dateval5").value = e4; */

			/*  e = document.getElementById("dateval").value; */
			//alert("test2: " + e + " " + e1);
			/* document.location.href = '${pageContext.request.contextPath}/transactionUmweb/export?fromDate=' + e
					+ '&toDate=' + e1 + '&tid=' + e2 + '&devId=' + e3 + '&status=' + e4 +'&export='+e5; */
			//alert(e);
			document.getElementById('devId').disabled = false;
			document.getElementById('tid').disabled = false;
			//window.open("${pageContext.request.contextPath}/transactionUmweb/export", '_blank').focus();
			document.form1.action = "${pageContext.request.contextPath}/transactionUmweb/export";
			//form.submit();
			document.form1.submit();

		}
	}

	function loaddata1() {
		//alert("test data");
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e2 = document.getElementById("tid1").value;
		var e3 = document.getElementById("devid1").value;
		var e4 = document.getElementById("status1").value;
		var e5 = document.getElementById("export1").value;
		//alert("e"+e);
		//alert("e1"+e1);
		//alert("e2"+e2);
		//alert("e3"+e3);
		//alert("e4"+e4);
		//alert("e5"+e5);

		if (e == null || e1 == null || e == '' || e1 == '') {
			//alert("picker :"+e + "  "+ e1);
			e = document.getElementById("dateval1").value;
			e1 = document.getElementById("dateval2").value;
			//alert("hidden : "+e + "  "+ e1);
			if (e == null || e1 == null || e == '' || e1 == '') {
				alert("Please select date(s)");
			}
		} else {

			/* document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			document.getElementById("dateval3").value = e2;
			document.getElementById("dateval4").value = e3;
			document.getElementById("dateval5").value = e4; */

			/*  e = document.getElementById("dateval").value; */
			//alert("test2: " + e + " " + e1);
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/pdf?fromDate='
					+ e
					+ '&toDate='
					+ e1
					+ '&tid='
					+ e2
					+ '&devId='
					+ e3
					+ '&status=' + e4 + '&export=' + e5;
			//alert(e);
			form.submit();

		}
	}
</script>

<script type="text/javascript">
	/* $(document).ready(function(){
	 $('#status').on('change', function() {
	 if ( this.value == 'CT')
	 //.....................^.......
	 {
	 $("#devId").show();
	 $("#tid").show();
	 }
	 else
	 {
	 $("#tid").hide();
	 $("#tid").hide();
	 }
	 });
	 }); */
	function check() {
		if (document.getElementById('status').value == 'CT') {

			document.getElementById('devId').disabled = true;
			document.getElementById('tid').disabled = true;
		} else {
			document.getElementById('devId').disabled = false;
			document.getElementById('tid').disabled = false;
		}
	}
</script>


</head>

<body>

	<div class="container-fluid">
		<div class="row">



			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>EZYWIRE Transaction Summary</strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>


		<form method="post" name="form1">
			<!-- action="${pageContext.request.contextPath}/<%=MerchantWebTransactionController.URL_BASE%>/search">  onsubmit=" return loadSelectData()">    //onsubmit=" return loadSelectData()" -->
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
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
										class="datepicker" name="toDate"
										onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
									<i class="material-icons prefix">date_range</i>
								</div>









								<div class="input-field col s12 m3 l3">


									<select name="txnType" id="txnType"
										onchange="return loadDropDate14();">
										<option selected value="">Choose</option>
										<option value="CARD">CARD</option>
										<!-- <option value="BOOST">BOOST</option>
										<option value="GRABPAY">GRABPAY</option> -->



									</select> <label for="name">Payment Method</label> <input type="hidden"
										name="txnType1" id="txnType1">
								</div>




								<div class="input-field col s12 m3 l3">
									<input type="hidden" name="tid1" id="tid1"
										<c:out value="${tid}"/>> <select name="tid" id="tid"
										onchange="return loadDropDate();">
										<option selected value=""><c:out value="TID" /></option>
										<c:forEach items="${terminalDetailsList}" var="t">

											<option value="${t.tid}">${t.tid}</option>

										</c:forEach>
									</select> <label for="tid">TID </label>

								</div>



								<div class="input-field col s12 m3 l3">
									<input type="hidden" name="status1" id="status1"
										<c:out value="${status}"/>> <select name="status"
										id="status" onchange="return loadDropDate11();">




										<option selected value="">Choose</option>
										<c:if test="${enableBoost == 'Yes' }">
											<%-- <option value="#">${enableBoost}</option> --%>
											<option value="BPC">BOOST CANCELLED</option>
											<option value="BPS">BOOST SETTLED</option>
											<option value="BPA">BOOST PAYMENT</option>
											<%-- <option value="BP">BOOST PENDING</option> --%>
										</c:if>
										<%-- <option id="cashid" value="CT">CASH SALE</option> --%>
										<%-- <option id="cashid" value="CV">CASH CANCELLED</option> --%>
										<option value="S">SETTLED</option>
										<option value="A">NOT SETTLED</option>
										<%-- <option value="P">PENDING</option> --%>
										<%-- <option value="R">REVERSAL</option> --%>
										<option value="C">CANCELLED</option>
									</select> <label>Status</label>




								</div>

								<div class="input-field col s12 m3 l3">
									<input type="hidden" name="devid1" id="devid1"
										<c:out value="${devId}"/>> <select name="devId"
										id="devId" onchange="return loadDropDate12();"
										style="width: 100%" class="form-control">

										<option selected value=""><c:out value="DevID" /></option>
										<c:forEach items="${terminalDetailsList}" var="d">
											<option value="${d.deviceId}">${d.deviceId}</option>
										</c:forEach>
									</select> <label for="deviceId">Device Id</label>
								</div>

								<div class="input-field col s12 m3 l3">
									<input type="hidden" name="export1" id="export1"
										<c:out value="${status}"/>> <select name="export"
										id="export" onchange="return loadDropDate13();">


										<option selected value="">Choose</option>
										<option value="PDF">PDF</option>
										<option value="EXCEL">EXCEL</option>

									</select> <label>Export Type</label>


								</div>


								<div class="input-field col s12 m3 l3">
									<div class="button-class" style="float: left;">

										<button class="btn btn-primary blue-btn" type="button"
											onclick="return loadSelectData();">Search</button>

										<button
											class="export-btn waves-effect waves-light btn btn-round indigo"
											type="button" onclick="return loaddata();" target="_blank">Export</button>
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


			<div class="row">
				<div class="col s12">
					<div class="card border-radius">
						<div class="card-content padding-card">

							<div class="table-responsive m-b-20 m-t-15">
								<table id="data_list_table"
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
											<th>PREAUTH Fee</th>
											<th>Payment Date</th>
											<th>Sales Slip</th>
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
														class="w24" src='data:image/png;base64,<%=eyeimg%> ' /> </span>
													<span onclick="hide_key('${id.index}')"
													id="show_key_${id.index}" class="hide_key">${dto.f011_AUTHIDRESP}</span></td>
												<td class='key_hover'><span
													onclick="show_rrn('${id.index}')" id="hide_rrn_${id.index}"><img
														class="w24" src='data:image/png;base64,<%=eyeimg%> ' /> </span>
													<span onclick="hide_rrn('${id.index}')"
													id="show_rrn_${id.index}" class="hide_key">${dto.f023_RRN}</span></td>
												<td>${dto.STATUS}</td>
												<td>${dto.cardscheme}&nbsp;${dto.cardType}</td>
												<td style="text-align: right;">${dto.mdrAmt}</td>
												<td style="text-align: right;">${dto.netAmount}</td>
												<td>${dto.preauthfee}</td>
												<td>${dto.settlementDate}</td>
												<td style="text-align: center;"><c:if
														test="${dto.cardscheme != 'BOOST' && dto.cardscheme != 'GRABPAY'}">
														<c:if
															test="${dto.STATUS =='SETTLED' || dto.STATUS =='NOT SETTLED' || dto.STATUS =='VOIDED'}">
															<a href="javascript:void(0)" id="openNewWin"
																onclick="javascript: openNewWin('${dto.trxId}')"> <img
																class="w24" src='data:image/png;base64,<%=actionimg%> ' />
															</a>

														</c:if>
													</c:if> <c:if test="${dto.cardscheme == 'BOOST'}">
														<c:if
															test="${dto.STATUS =='SETTLED' || dto.STATUS =='NOT SETTLED' || dto.STATUS =='VOIDED'}">
															<a href="javascript:void(0)" id="openBoostslip"
																onclick="javascript: openBoostslip('${dto.f023_RRN}')">
																<img class="w24"
																src='data:image/png;base64,<%=actionimg%> ' />
															</a>

														</c:if>
													</c:if> <c:if test="${dto.cardscheme == 'GRABPAY'}">
														<c:if
															test="${dto.STATUS =='SETTLED' || dto.STATUS =='NOT SETTLED' || dto.STATUS =='VOIDED'}">
															<a href="javascript:void(0)" id="openGrabpayslip"
																onclick="javascript: openGrabpayslip('${dto.f023_RRN}')">
																<img class="w24"
																src='data:image/png;base64,<%=actionimg%> ' />
															</a>

														</c:if>
													</c:if> <c:set var="fpx" value="${dto.cardscheme}" /> <c:if
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



													<div class="form-group col-md-4" id="divviewer"
														style="display: none;">
														<div class="form-group">
															<div style="clear: both">
																<iframe id="popOutiFrame" frameborder="0" scrolling="no"
																	width="800" height="600"></iframe>

															</div>

														</div>
													</div></td>

												<td style="text-align: center;"><c:if
														test="${dto.cardscheme != 'BOOST' && dto.cardscheme != 'GRABPAY'}">
														<c:if test="${dto.STATUS =='NOT SETTLED' && dto.txnType != 'Yes'}">
															<a
																href="${pageContext.request.contextPath}/transactionUmweb/ezywireCancelPayment/${dto.trxId}">
																<img class="w24"
																src='data:image/png;base64,<%=voidimg%> ' />
															</a>
														</c:if>
													</c:if></td>

												<td style="text-align: center;"><c:if
														test="${dto.cardscheme != 'BOOST' && dto.cardscheme != 'GRABPAY'}">
														<c:if test="${dto.STATUS =='SETTLED'}">
															<a
																href="${pageContext.request.contextPath}/transactionUmweb/refundEzywirePayment/${dto.trxId}">
																<img class="w24"
																src='data:image/png;base64,<%=refundimg%> ' />
															</a>
														</c:if>
													</c:if></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>


					</div>
				</div>
			</div>
		</form>
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



</body>
</html>






























