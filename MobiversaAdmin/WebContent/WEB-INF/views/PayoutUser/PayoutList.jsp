<%@page import="com.mobiversa.payment.controller.PayoutUserController"%>
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



<meta http-equiv="Content-Security-Policy"
	content="default-src 
    'self'">

<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/custom.css">

<!-- Script tag for Datepicker 	 -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
	
	 <script
            src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/FileSaver.js/2.0.5/FileSaver.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/dom-to-image/2.6.0/dom-to-image.min.js"></script>

</head>



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
</style>


<script type="text/javascript">

	var size ="${paginationBean.querySize}";
	console.log("total size of pagination from backend is : "+ size)


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

#decl {
	left: 40%;
	bottom: 40%;
	display: none;
	z-index: 199;
}

.decline-side-box {
	display: block;
	width: 25rem;
	height: 13rem;
	background-color: #FFFFFF;
	position: fixed;
	border-radius: 10px;
	border: 1px solid #FFDEAD;
}

.text-wrap-user {
	position: absolute;
	width: 22rem;
	height: 11rem;
	background-color: #FFFFFF;
	left: 20px;
	top: 10px;
	border-radius: 10px;
}

.text-style-decl {
	font-size: 1.6rem;
	font-weight: 500;
	font-family: sans-serif;
	color: #4377a2;
	padding: 15px 25px;
	margin: 5px 0;
}

#decline-reason {
	padding: 15px 0 0 0;
	font-size: 1.2rem;
	margin: 0 30px;
	width: 75%;
}

.bn632-hover {
	width: 80px;
	font-size: 16px;
	font-weight: 600;
	color: #fff;
	cursor: pointer;
	margin: 25px;
	height: 40px;
	text-align: center;
	border: none;
	background-size: 300% 100%;
	border-radius: 50px;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out;
	position: absolute;
	left: 35px;
	bottom: -20px;
}

.bn632-hover:hover {
	background-position: 100% 0;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out;
}

.bn632-hover:focus {
	outline: none;
}

.bn632-hover.bn26 {
	background-image: linear-gradient(to right, #25aae1, #4481eb, #04befe, #3f86ed);
	box-shadow: 0 4px 15px 0 rgba(65, 132, 234, 0.75);
}

.cancelbtn {
	width: 80px;
	font-size: 16px;
	font-weight: 600;
	color: #fff;
	cursor: pointer;
	margin: 20px;
	height: 40px;
	text-align: center;
	border: none;
	background-size: 300% 100%;
	border-radius: 50px;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out;
	position: absolute;
	left: 160px;
	bottom: -15px;
}

.cancelbtn:hover {
	background-position: 100% 0;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out;
}

.cancelbtn:focus {
	outline: none;
}

.cancelbtn.bn25 {
	/*   background-image: linear-gradient(
    to right,
    #29323c,
    #485563,
    #2b5876,
    #4e4376
  ); */
	background-color: #7D7D7D;
	/* box-shadow: 0 4px 15px 0 rgba(45, 54, 65, 0.75); */
}

#exampleModalCenter {
	z-index: 99;
	width: 25%;
	font-size: 24px;
	font-weight: 400;
	font-family: 'Poppins', sans-serif;
	text-align: center;
}
</style>


<script lang="JavaScript">



	function loadSelectData() {
    		$("#overlay").show();
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
    			$("#overlay").hide();
    		}
    		else {
    			document.getElementById("dateval1").value = fromdateString;
    			document.getElementById("dateval2").value = todateString;
    			/* document.getElementById("txnType").value = e2; */
    			<%--/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='--%>
    			// 		+ e + '&date1=' + e1 + '&txnType=' + e2; */

    			document.location.href = '${pageContext.request.contextPath}/payoutDataUser/searchPayout?date='
    					+ fromdateString + '&date1=' + todateString;


    			localStorage.setItem("fromDate", e);
    			localStorage.setItem("toDate", e1);
    			form.submit;

    		}
    	}




window.addEventListener('load', function () {
		var fromDate = localStorage.getItem("fromDate");
		var toDate = localStorage.getItem("toDate");

		if (fromDate && toDate) {

			document.getElementById("from").value = fromDate;
			document.getElementById("to").value = toDate;


			document.getElementById("datef").style.transform = "translateY(-14px) scale(0.8)";
			document.getElementById("datet").style.transform = "translateY(-14px) scale(0.8)";
		}

		localStorage.removeItem("fromDate");
		localStorage.removeItem("toDate");
		//localStorage.clear();
	});




function loadSelectData2() {
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var fromDate = new Date(e);
		var toDate = new Date(e1);
		var PageNumber = document.getElementById("pgnum").value;
		//alert("Page Number: " + PageNumber);

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

		if (e === '' || e1 === '') { // Check for empty strings instead of null
			fromdateString = null;
			todateString = null;
			//alert("from: " + fromdateString + " to: " + fromdateString);
			document.location.href = '${pageContext.request.contextPath}/payoutDataUser/searchPayout?date='
					+ fromdateString + '&date1=' + todateString + '&currPage=' + PageNumber;
		} else {

			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;

			// alert("from: " + fromdateString + " to: " + todateString);
			document.location.href = '${pageContext.request.contextPath}/payoutDataUser/searchPayout?date='
					+ fromdateString + '&date1=' + todateString + '&currPage=' + PageNumber;

			console.log(fromdateString);
			console.log(todateString);
			console.log(PageNumber);

			/* Updated code... */
			localStorage.setItem("fromDate", e);
			localStorage.setItem("toDate", e1);

			form.submit(); // Added parentheses to actually call the function
		}
	}




	function loadExpData() {
	    var e = document.getElementById("from").value;
	    var e1 = document.getElementById("to").value;
	    var e2 = document.getElementById("export1").value;
	    

	    var fromDate = new Date(e);
	    var toDate = new Date(e1);

	    var timeDiff = Math.abs(toDate.getTime() - fromDate.getTime());
	    var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24));

	    if (e == null || e == '' || e1 == null || e1 == '') {
	        alert("Please Select date(s)");
	    } else if (e2 == null || e2 == '') {
	        alert("Please Select Export Type");
	    } else if (diffDays > 2) {
	    	 document.getElementById("email-modal-id").style.display = "block";
	    } else {
	        var fromday = fromDate.getDate();
	        var frommon = fromDate.getMonth() + 1;
	        var fromyear = fromDate.getFullYear();

	        var today = toDate.getDate();
	        var tomon = toDate.getMonth() + 1;
	        var toyear = toDate.getFullYear();

	        var fromdateString = fromyear + '-' + (frommon <= 9 ? '0' + frommon : frommon) + '-' + (fromday <= 9 ? '0' + fromday : fromday);
	        var todateString = toyear + '-' + (tomon <= 9 ? '0' + tomon : tomon) + '-' + (today <= 9 ? '0' + today : today);

	        document.getElementById("datevalex1").value = fromdateString;
	        document.getElementById("datevalex2").value = todateString;

	        document.location.href = '${pageContext.request.contextPath}/payoutDataUser/exportPayoutInPayoutLogin?date=' + fromdateString + '&date1=' + todateString + '&export=' + e2;

	        // form.submit();
	    }
	}

	function loadDropDate13() {

		var e = document.getElementById("export");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("export1").value = strUser;

	}

		function loadDate(inputtxt, outputtxt) {
    		var field = inputtxt.value.trim();
    		var regex = /^(?:Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\s\d{2},\s\d{4}$/;
    		if (!regex.test(field)) {
    			alert("Please enter a valid date format");
    			inputtxt.value = '';
    			outputtxt.value = '';
    			return;
    		}
    		outputtxt.value = field;
    	}



	function payoutIdSearch() {
    		$("#overlay").show();
    		var Value = document.getElementById('drop_option').value.trim();
    		var searchInput = document.getElementById('searchApi').value.trim();

    		var regex = /^[a-zA-Z0-9_\s]*$/;
    		if (searchInput === '' && Value === 'choose-type') {
    			alert('Please fill all the Data');
    			$("#overlay").hide();
    			return;
    		} else if (searchInput === '') {
    			alert("Search value must not be empty");
    			$("#overlay").hide();
    			return;
    		} else if (!regex.test(searchInput)) {
    			alert("Search value must not contain special symbols");
    			$("#overlay").hide();
    			return;
    		} else if (Value === '' || Value === 'choose-type') {
    			alert("Choose option must be selected");
    			$("#overlay").hide();
    			return;
    		}
    		if (searchInput.trim() === '' || Value.trim() === '') {
    			alert('Please enter a search value.');
    			$("#overlay").hide();
    			return;
    		}
    		document.location.href = '${pageContext.request.contextPath}/searchNew/searchPayoutAndTxnIdPayoutLogin?VALUE=' + searchInput + '&transaction_type='
    				+ Value;

    		form.submit;

    	}




	
	
</script>
<body class="">
	<div class="container-fluid">
		<div class="row">



			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>PAYOUT Summary</strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>

		<!-- no record found popup -->
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
					<img
						src="${pageContext.request.contextPath}/resourcesNew1/assets/NoRecordPNG.png"
						width="60px !important; height:60px !important;">
					<p id="innerText" style="font-size: 22px;"></p>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal" id="close" onclick="closepopup()"
							style="width: 106px !important; height: 38px !important; font-size: 18px; border-radius: 50px !important; margin-right: 187px !important; letter-spacing: 0.8px; font-family: 'Poppins', sans-serif; font-weight: medium; transform: translateY(-10px);">Close</button>
					</div>
				</div>
			</div>
		</div>

		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">

						<div class="row">
							<div class="input-field col s12 m3 l3">

								<label id="datef" for="from" style="margin: 0px;">From </label>
								<input type="hidden" name="date11" id="date11"
									<c:out value="${fromDate}"/>> <input type="text"
									id="from" name="fromDate" class="validate datepicker"
									onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
								<i class="material-icons prefix">date_range</i>
							</div>
							<div class="input-field col s12 m3 l3">

								<label for="to" id="datet" style="margin: 0px;">To</label> <input
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
									<%--									<option value="PDF">PDF</option>--%>
									<option value="EXCEL">EXCEL</option>
								</select> <label>Export Type</label>
							</div>

							<div class="input-field col s12 m3 l3">
								<div class="button-class" style="float: left !important;display: flex !important;">

									<input type="hidden" name="date1" id="dateval1"> <input
										type="hidden" name="date2" id="dateval2">
									<button class="btn btn-primary blue-btn three-btn-one"
										type="button" onclick="return loadSelectData();">Search</button>


									<input type="hidden" name="dateex1" id="datevalex1"> <input
										type="hidden" name="dateex2" id="datevalex2">
									<button
										class="export-btn waves-effect waves-light btn btn-round indigo three-btn-one"
										type="button" onclick="return loadExpData();">Export</button>
								</div>
							</div>

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
									style="font-family: 'Poppins', sans-serif; width: 20.5% !important;">
									<!--   <select name="drop_search"
                                         id="drop_search" onchange="return loadDropSearch();">
                                         <option selected value="" id="choose">Choose Type</option>
                                         <option value="Ref">Payout Id</option>

                                          </select> -->
									<input type="hidden" id="drop_val">

									<%--								<input type="text" value="PAYOUT ID" readonly>--%>
									<select class="payout-summary-drop-down" id="drop_option">
										<option value="choose-type">Choose type</option>
										<option value="payout-id">Payout ID</option>
										<option value="transaction-id">Transaction ID</option>
										<%--									<option value="payout-id">Transaction <ID></ID></option>--%>
									</select>





								</div>

								<div class="input-field col s12 m3 l3 offset-l1 offset-m1"
									style="width: 20.5% !important; margin-left: 4.33333% !important;">

									<input type="text" id="searchApi" name="search" class=""
										style="font-family: 'Poppins', sans-serif;"
										placeholder="Choose type to search" oninput="validateAmount()">

								</div>
								<div class="input-field col s12 m4 l4 offset-l1 offset-m1"
									style="width: 10%; margin-left: 03% !important;">

									<div class="button-class" style="float: left;">
										<button class="btn btn-primary blue-btn three-btn-one"
											type="button" onclick="payoutIdSearch()"
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





		<input type="hidden" id="curr-procces" value="0" />
		<div class="row">
			<div class="col s12">

				<div class="card border-radius">
					<div class="card-content padding-card">

						<div class="table-responsive m-b-20 m-t-15">
							<table class="table table-striped table-bordered">
								<thead>
									<tr>
										<th style="font-size: 14px">Date</th>
										<th style="font-size: 14px">Merchant Name</th>
										<th style="font-size: 14px">Transaction ID</th>
										<th style="font-size: 14px">Payout ID</th>
										<th style="font-size: 14px">Bank Name</th>
										<th style="font-size: 14px">Amount(RM)</th>
										<th style="font-size: 14px">Status</th>
										<th style="text-align: center; font-size: 14px">
											Reason</th>
										<th style="font-size: 14px">Host Transaction ID</th>
										<th style="font-size: 14px; text-align: center">Sales
											Slip</th>
										<th></th>
									</tr>
								</thead>

								<tbody>





									<c:forEach items="${paginationBean.itemList}" var="dto">


										<tr>


											<td style="text-align: left; font-size: 14px">${dto.payoutdate}
											<td style="text-align: left; font-size: 14px">${dto.createdby}</td>
											<td style="text-align: left; font-size: 14px">${dto.invoiceidproof}</td>
											<td style="text-align: left; font-size: 14px">${dto.payoutId}</td>
											<td style="text-align: left; font-size: 14px">${dto.payeebankname}</td>
											<td style="text-align: left; font-size: 14px">${dto.payoutamount}</td>
											<c:choose>


												<c:when test="${dto.payoutstatus == 'Declined'}">
													<td
														style="text-align: left; display: flex; align-items: center; justify-content: flex-start; font-size: 14px; margin-top: 5px">
														<img class=""
														src="${pageContext.request.contextPath}/resourcesNew1/assets/Declined.svg"
														width="20" height="20" style="margin-right: 5px;" /> <span
														style="color: red; font-weight: 450;">Declined</span>
													</td>
												</c:when>
												<c:when test="${dto.payoutstatus == 'Paid'}">
													<td
														style="text-align: left; display: flex; align-items: center; justify-content: flex-start; font-size: 14px; margin-top: 5px">
														<img class=""
														src="${pageContext.request.contextPath}/resourcesNew1/assets/paid.svg"
														width="20" height="20" style="margin-right: 5px;" /> <span
														style="color: #11bf36; font-weight: 500;">Paid</span>
													</td>
												</c:when>

												<c:when test="${dto.payoutstatus == 'Pending'}">
													<td
														style="text-align: left; display: flex; align-items: center; justify-content: flex-start; font-size: 14px; margin-top: 5px">
														<img class=""
														src="${pageContext.request.contextPath}/resourcesNew1/assets/pending.svg"
														width="20" height="20" style="margin-right: 5px;" /> <span
														style="color: #F9C84C; font-weight: 500;">Pending</span>
													</td>
												</c:when>
												<c:when test="${dto.payoutstatus == 'In Process'}">
													<td
														style="text-align: left; display: flex; align-items: center; justify-content: flex-start; font-size: 14px; margin-top: 5px">
														<img class=""
														src="${pageContext.request.contextPath}/resourcesNew1/assets/on_process.svg"
														width="20" height="20" style="margin-right: 5px;" /> <span
														style="color: #8A8A8A; font-weight: 500;">In
															Process</span>
													</td>
												</c:when>


												<c:otherwise>
													<td>${dto.payoutstatus}</td>
												</c:otherwise>
											</c:choose>
											<%-- <td style="text-align: center;"><c:if
                                                                        test="${dto.payoutstatus =='Processed'}">
                                                                        <a
                                                                            href="${pageContext.request.contextPath}/payoutDataUser/updatePayoutStatus/${dto.invoiceidproof}">
                                                                            <img class="w24"
                                                                            src='data:image/png;base64,<%=actionimg%> ' />

                                                                        </a>
                                                                    </c:if></td> --%>
											<%--									<p>${dto.createddate}<p/>--%>

											<td style="text-align: center;"><c:if
													test="${dto.payoutstatus == 'Declined' }">
													<a href="javascript:void(0)" id="openPayoutslip"
														<%-- onclick="javascript: openDeclinedReason('${dto.failurereason}')"> --%>
															  onclick="javascript: openDeclinedReason(
                                                               '${dto.failurereason == null || dto.failurereason.isEmpty() ? 'Payout transaction failed' :
                                                                (dto.failurereason.contains('ERROR (') || dto.failurereason.contains('Exception') || dto.failurereason.contains('exception') ? 'Host Side Error' :
                                                                (dto.failurereason.contains('Invalid payee details. Account Number / Proxy Type/Value cannot be found.') ? 'Invalid payee details' : dto.failurereason))}')">
														<div style="padding-top: 5px;">
															<img class="" width="20" height="20"
																src="${pageContext.request.contextPath}/resourcesNew1/assets/blureye.svg" />
														</div>
													</a>

												</c:if> <c:if test="${dto.payoutstatus == 'Pending' }">
													<a href="javascript:void(0)" id="openPayoutslip"
														onclick="javascript: openPendingReason('Please check by End of the day')">
														<div style="padding-top: 5px;">
															<img class="" width="20" height="20"
																src="${pageContext.request.contextPath}/resourcesNew1/assets/blureye.svg" />
														</div>
													</a>

												</c:if></td>
												
											  <td style="text-align: left; font-size: 14px">${dto.curlecRefNo}</td>	



											<td style="text-align: center; padding-top: 8px;"><c:if
													test="${dto.payoutstatus == 'Paid'}">
													<a href="javascript:void(0)" id="openPayoutslip "
														onclick="javascript: openPayoutslip(
                        													    '${dto.invoiceidproof}',
                        													   '${dto.payeename}',
                        													   '${dto.createdby}',
                        													   '${dto.payoutamount}',
                        													   '${dto.timeStamp}',
                        													   '${dto.srcrefno}',
                        													   '${dto.payoutstatus}',
                        													   '${dto.payouttype}',
                        													   '${dto.payoutdate}',
                        													   '${dto.paymentReference}',
                        													   '${dto.payoutId}',
                        													   '${dto.paidTime}',
                        													   '${dto.paidDate}'
                        													   )">
														<div style="padding-top: 5px;">
															<img class="w24"
																src="${pageContext.request.contextPath}/resourcesNew1/assets/salesSlip.svg" />
														</div>
													</a>

												</c:if></td>



											<td align="center;"><a href="javascript:void(0)"
												onclick="openPayoutslipMore(
                        																	   '${dto.payoutdate}',
                        																	   '${dto.createdby}',
                        																	   '${dto.payeename}',
                        																	   '${dto.payeebrn}',
                        																	   '${dto.payeeaccnumber}',
                        																	   '${dto.payeebankname}',
                        																	   '${dto.invoiceidproof}',
                        																	   '${dto.payoutId}',
                        																	   '${dto.payoutamount}',
                        																	   '${dto.payoutstatus}',
                        																	   '${dto.timeStamp}',
                        																	   '${dto.paidTime}',
                        																	   '${dto.paidDate}',
                        																	   '${dto.submerchantMid}',
                        																	   '${dto.mmId}',
                        																	   '${dto.failurereason}',
                        																	   '${dto.createdby}',
                        																	   '${dto.payouttype}',
                        																	   '${dto.srcrefno}',
                        																	   '${dto.paymentReference}'
                        																	   )">
													<div style="padding-top: 5px">
														<img class="w24"
															src="${pageContext.request.contextPath}/resourcesNew1/assets/more.svg" />
													</div>
											</a></td>
										</tr>
									</c:forEach>





									<td colspan="8"
										style="text-align: center; border-bottom: solid; border-bottom-color: white !important">
										<div id="no-data">
											<p></p>
										</div>
									</td>






								</tbody>
							</table>

						</div>
					</div>


				</div>
			</div>

		</div>
	</div>
	</div>




	<!-- mobi loading logo -->
	<div id="overlay" id="loading-gif">
		<div id="overlay_text">
			<img class="img-fluid"
				src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
		</div>
	</div>


	<div id="overlay-popup"></div>
	<div class="mobi-modal" id="mobi_modal_popup" tabindex="-1">
		<div class="mobi-modal-dialog">
			<div
				class="mobi-modal-content mobi-modal-content-center mobi-modal-content-mobile mobi-60-per">
				<div class="mobi-modal-header">
					<h5 class="mobi-modal-title mobi-text-dark"></h5>
					<img class="img-fluid mobi-close" onclick="closeDialog()"
						src="${pageContext.request.contextPath}/resourcesNew1/assets/close.png">

				</div>

				<div
					class="mobi-modal-body mobi-text-sucess mobi-100-per mobi-modal-content-center">
					<div class="mobi-float-left mobi-50-per">
						<p>
							<label><input type="radio" name="payout_radio"
								onClick="ProcessPaid()" id="check-paid" value=""><span
								class="c969696">Paid</span></label>
						</p>
					</div>
					<div class="mobi-float-left mobi-50-per">
						<p>
							<label><input type="radio" name="payout_radio"
								onclick="closeDialog()" id="check-Unpaid" value=""><span
								class="c969696">Unpaid</span></label>
						</p>
					</div>

					<!-- Sheik Edited For Decline Status - Start  -->
					<div class="mobi-float-left mobi-50-per">
						<p>
							<label><input type="radio" name="payout_radio"
								onClick="openDecline();" id="check-decline" value=""><span
								class="c969696" style="margin: 20px 0 0 20px;">Decline</span></label>
						</p>
					</div>

					<!-- Sheik Edited For Decline Status - End  -->

				</div>
				<div class="mobi-modal-footer"></div>

			</div>
		</div>
	</div>


	<div id="decl" class="decline-side-box">
		<div class="text-wrap-user">
			<label for="decline-reason" class="text-style-decl">Reason
				For Decline:</label> <input type="text" id="decline-reason"
				name="decline-reason" required maxlength="64"
				placeholder="Ex: Account Mismatch">
			<button class="bn632-hover bn26" onclick="confirmDecline();"
				id="confirmbtn">Confirm</button>
			<button class="cancelbtn bn25" onclick="closeDecline();"
				id="canceldecl">Cancel</button>
		</div>

	</div>

	<!-- modal to get email -->

	<!-- modal to get email -->

	<div id="email-modal-id" class="email-modal-class">

		<div class="email-content-class">


			<div
				style="text-align: center; display: inline-block; border-bottom: 1.5px solid #ffa500; width: 100%; vertical-align: middle; padding: 2%;">
				<!-- <p style="margin-bottom: 0px; font-size: 13px;">Note :</p> -->
				<p
					style="font-size: 16px; font-weight: 500 !important; margin-bottom: 1px; color: #005baa; margin-bottom: 1px;">Notification</p>

			</div>


			<div
				style="width: 100%; display: flex; flex-direction: column; justify-content: center; align-items: center; gap: 8px;">

				<div
					style="display: flex; align-items: center; justify-content: center; flex-direction: column; padding: 5px 0px; width: 80%; gap: 5px;">

					<img
						src="${pageContext.request.contextPath}/resourcesNew1/assets/mail_box1.svg"
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; width="45px !important;">
					<div style="width: 100%; display: flex; align-items: left;">
						<p
							style="margin-bottom: 1px; color: #586570; letter-spacing: 0.27px;">Please
							enter your email to receive the CSV file.</p>
					</div>

				</div>


				<div style="padding: 0 10%; width: 100% !important;">
					<input type="email" id="email-input" name="email"
						placeholder="username@gomobi.io"
						style="text-align: left; font-size: 13px; padding-bottom: 5px; height: 20px; color: #858585; margin-bottom: 25px; border-bottom: 1.5px solid #ffa500;">
					<p id="emailErrorMessage"
						style="color: red; font-size: 12px; text-align: center; margin: 0; margin-top: -15px; padding: 5px 0px; display: none;">Email
						must end with @gomobi.io</p>
				</div>

			</div>




			<div
				style="display: flex; flex-direction: row; gap: 10px; width: 100%; align-items: center; padding: 2%; justify-content: center; background-color: #b1dcfb30; border-bottom-right-radius: 9px; border-bottom-left-radius: 9px;">
				<button type="button" id="email-submit-btn-cancel"
					class="email-submit-class" onclick="closeNotify()"
					style="background: transparent; border: 2px solid #005baa; color: #005baa;">Cancel</button>
				<button type="button" id="email-submit-btn-submit"
					class="email-submit-class" onclick="submitEmail()">Submit</button>
			</div>
		</div>

	</div>




	<%-- <div id="slip-modal-id" class="slip-modal-class">
		<div id="slip-modal-content-id" class="slip-modal-content-class">
			<div style="padding: 0 10px;">
				<div
					style="text-align: center; padding: 19px 0px; border-bottom: 2px solid orange;">
									<h1 class="payment-slip-text" style="margin: 0"><b>Payment Slip</b></h1>
									<span style="float: right; cursor:pointer;font-weight: 500;font-size: 20px;margin-right: 17px;margin-top: -21px;" id="X-slip">X</span>
					<h1 class="payment-slip-text" style="margin: 0; padding: 0 10px;">
						<b style="text-align: center;">Payment Slip</b><span
							style="float: right; cursor: pointer; font-weight: 500; font-size: 20px;"
							id="X-slip"> <img class=""
							src="${pageContext.request.contextPath}/resourcesNew1/assets/xmark.svg"
							width="20" height="20";>
						</span>
					</h1>
				</div>

				<div class="content" style="padding: 20px;">
					<div style="height: auto;" class="slip-card">
						<div class="banner-slip"
							style="padding: 8px 15px; background-color: #005BAA; border-top-left-radius: 10px; border-top-right-radius: 10px;">
							<div class="logo"
								style="display: flex; flex-direction: row; align-items: center; justify-content: space-between; color: #fff;">
								<div style="font-size: 18px">Payout Transaction</div>
								<div style="font-size: 30px; font-weight: bold;">mobi</div>
							</div>
							<div class="amount-slip"
								style="display: flex; flex-direction: row; align-items: center; justify-content: space-between; color: #fff;">
								<div id="top-amount" style="font-size: 20px; font-weight: bold;"></div>
								<div id="top-date" style="font-size: 12px; font-weight: 300;"></div>
							</div>
						</div>
						<div class="content-slip"
							style="padding: 15px; border: 1px solid #efefef; box-shadow: 0 5px 16px 2px rgba(0, 0, 0, 0.3); border-bottom-left-radius: 10px; border-bottom-right-radius: 10px;">
							<div class="content-card"
								style="border: 1px solid orange;
    										 border-radius: 10px;
    										 padding: 10px;
    										 background-color: #EDFBFF;
    										 background-image: url('${pageContext.request.contextPath}/resourcesNew1/assets/mobi-watermark.svg');
    										 background-size: 100px 100px;
    										 background-position: center;
    										 background-repeat: no-repeat;">

								<div
									style="color: black; font-size: 12px; font-weight: 500; padding-bottom: 3px; margin-left: 5px;"
									id="payoutPayeeValue"></div>

								<div style="color: black; font-size: 13px; margin-left: 5px;"
									id="payoutMerchantName"></div>
								<div style="color: black">
									<span id="payoutAmountValue"
										style="color: #32d332; font-weight: 550; margin-left: 5px; font-size: 13px"
										aria-invalid="payout-date"></span> <span
										style="font-size: 13px" id="payout-date">Feb 2024</span>
								</div>



								<div style="margin: 10px;"></div>
								<div style="color: #97979D; margin-left: 5px; font-weight: 430;">Payment
									Description</div>
								<div style="color: black; font-size: 13px; margin-left: 5px;"
									id="payoutToId"></div>

								<div style="margin: 10px;"></div>

								<div
									style="color: #a9a6a6; color: #97979D; padding-bottom: 6px; margin-left: 5px; font-weight: 430;">Transaction
									Details</div>

								<table
									style="border-collapse: separate !important; border-spacing: 0;"
									class="slip-table">
									<tr>
										<td class="txn-details-option"
											style="font-size: 13px; color: #464649;">Src Ref No:</td>
										<td>:</td>
										<td id="srcrefnoValues"
											style="font-size: 13px; color: #464649"></td>
									</tr>

									<tr>
										<td class="txn-details-option"
											style="font-size: 13px; color: #464649;">Invoice Id</td>
										<td>:</td>
										<td id="invoice-id" style="font-size: 13px; color: #464649;"></td>
									</tr>

									<tr>
										<td class="txn-details-option"
											style="font-size: 13px; color: #464649;">Status</td>
										<td>:</td>
										<td id="payout-status"
											style="font-size: 13px; color: #464649;"></td>
									</tr>

									<tr>
										<td class="txn-details-option"
											style="font-size: 13px; color: #464649;">Payout Type</td>
										<td>:</td>
										<td id="payout-type"
											style="font-size: 13px; color: #464649; font-family: 'Poppins', sans-serif"></td>
									</tr>

									<tr>
										<td class="txn-details-option"
											style="font-size: 13px; color: #464649;">Payout Method</td>
										<td>:</td>
										<td><img class="w24"
											src="${pageContext.request.contextPath}/resourcesNew1/assets/Payout.svg" />
										</td>
									</tr>


								</table>

								<div class="email-link"
									style="display: flex; flex-direction: column; align-items: center; justify-content: center">
									<div style="color: #a9a6a6; font-size: 11px; padding-top: 4px">Contact
										for any questions regarding this payment</div>
									<div
										style="color: #005baa; font-size: 11px; font-family: 'Poppins', sans-serif">csmobi@gomobi.io</div>
								</div>


							</div>

						</div>
					</div>
				</div>
			</div>
			<div class="slipModal-close-btn"
				style="padding: 10px 0px; background: #005baa25;">
				<button type="button" id="slip-modal-close-btn-id"
					class="slip-modal-close-btn-class" onclick="closeSlipModal()">Close</button>
			</div>

		</div>
	</div> --%>

<!-- New Payslip design payout -->

	<div id="xPay_slip-modal-id" class="slip-modal-class">
		<section class="payout-slip-main-container poppins-regular"
			id="payout-slip-main-container-id">
			<div class="slip-card" id="slip-card-id">
				<div class="title-logo" style="position: relative">

					<%--      <img src="${pageContext.request.contextPath}/resourcesNew1/assets/mobi_logo.svg" width="55"
                        height="55"/> --%>

					<h3
						style="font-family: 'Poppins', sans-serif; color: #005baa; font-weight: 800; font-size: 25px !important; margin-bottom: 0 !important;">
						mobi</h3>
					<%-- <img style="position: absolute;
                     top: 3px;
                     right: 10px;
                     height: 20px ! important;
                     width: 20px !important;
                    cursor: pointer;" src="${pageContext.request.contextPath}/resourcesNew1/assets/xmark.svg" width="35"
                     height="35" onclick="closeXpayModal()"/> --%>

					<img id="cancel-download"
						style="position: absolute; top: 3px; right: 10px; height: 20px ! important; width: 20px !important; cursor: pointer;"
						src="${pageContext.request.contextPath}/resourcesNew1/assets/xmark.svg"
						width="35" height="35" onclick="closeXpayModal()" /> <img
						id="active-download"
						style="position: absolute; top: 3px; right: 40px; height: 20px !important; width: 20px !important; cursor: pointer;"
						src="${pageContext.request.contextPath}/resourcesNew1/assets/download.svg"
						width="35" height="35" onclick="downloadPayoutSlip()">
				</div>

				<hr class="horizontal-line">
				<!-- Second Part - Status and Time stamp -->
				<div class="main-status poppins-semibold">
					<!-- comment this below for Failure status -->
					<p class="status status-success">Successful</p>
					<!-- Uncomment this for Failure status -->
					<!-- <p class="status status-failure">Failed</p> -->
					<div class="status-container">
						<p class="sub-head">Transaction Summary</p>
						<p class="amount poppins-regular">
							MYR <span class="poppins-semibold amount-value"
								id="new_slip_amount"></span>
						</p>
							<p class="time-stamp poppins-semibold" id="">
								 <span class="poppins-semibold amount-value"
										id="paidDate"></span>
										 <span class="poppins-semibold amount-value"
										id="paidTime"></span>
								</p>
						<hr class="horizontal-default">
					</div>
				</div>
				<!-- Third Part - Transaction details area  -->
				<div class="transaction-details">
					<table>
						<tr class="no_border_bottom">
							<th class="poppins-regular xpay_slip_whiteSpace">From</th>
							<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak"
								style="text-transform: uppercase;" id="new_slip_merchant"></td>
						</tr>
						<tr class="no_border_bottom">
							<th class="poppins-regular xpay_slip_whiteSpace">To
								</th>
							<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak"
								id="new_slip_recipient"></td>
						</tr>
						
						 <tr class="no_border_bottom">
							<th class="poppins-regular xpay_slip_whiteSpace">Reference 
								No</th>
							<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak"
								id="new_slip_payoutId"></td>
						</tr>
					   <tr class="no_border_bottom">
							<th class="poppins-regular xpay_slip_whiteSpace">Transaction 
								ID</th>
							<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak"
								id="invoice-id"></td>
						</tr>

						<tr class="no_border_bottom">
							<th class="poppins-regular xpay_slip_whiteSpace">Payment
								Type</th>
							<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak"
								id="new_slip_paymentType"></td>
						</tr>
				
						<tr class="no_border_bottom">
							<th class="poppins-regular xpay_slip_whiteSpace">Payment
								Method</th>
							<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak">Payout</td>
						</tr>


						<tr class="no_border_bottom">
							<th class="poppins-regular xpay_slip_whiteSpace">Remarks
								</th>
							<td class="rightSide_color_code_slip poppins-medium xpay_slip_wordBreak"
								id="new_slip_paymentReference"></td>
						</tr>
					</table>
					<div class="bill-box-container">
						<div class="poppins-medium">Transfer Amount</div>
						<div class="poppins-semibold"
							style="font-size: 1rem; color: var(--value-color);"
							id="new_slip_transferAmt"></div>
					</div>
				</div>
				<hr class="horizontal-default">
				<div class="notes-section">
					<strong>Note</strong>
					<p class="notes" style= "font-size: 11px !important">
							This receipt is computer generated and no signature is required.
							For support, contact  <a href="mailto:csmobi@gomobi.io" style="color: #005BAA; text-decoration: underline; ">csmobi@gomobi.io</a>
						</p>
				</div>
			</div>
		</section>
	</div>


	<style>
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
	color: var(--title-color);
}

.transaction-details th, .transaction-details td {
	padding: 0.85rem 0.3rem;
	text-align: left;
	font-size: 0.8rem;
}

.transaction-details td {
	text-align: right;
	text-wrap: wrap;
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

<!-- New Payslip design payout End -->

	<div id="payoutDetailsId" class="payoutDetailsClass">
		<div class="payoutDetailsClass-content"
			id="payoutDetailsClass-content-id">
			<div style="padding: 0 24px">
				<%--			<div>--%>
				<%--				<span id="closeModalBtn" class="close-payoutDetails">&times;</span>--%>
				<%--				<h1 class="payout-head"><b>Payout Details</b></h1>--%>
				<%--			</div>--%>

				<div
					style="border-bottom: 1px solid orange; padding: 15px 24px; text-align: center; font-size: 20px; color: #005baa; font-weight: 600">
					Payout Details<span
						style="float: right; font-weight: 500; font-size: 17px; cursor: pointer; margin-right: -22px; margin-top: 6px;"
						id="closeModalBtn"> <img class=""
						src="${pageContext.request.contextPath}/resourcesNew1/assets/xmark.svg"
						width="20" height="20";>
					</span>
				</div>
				<div id="table-content"></div>
			</div>
			<div class="payoutDetail-btn-class-div"
				style="padding: 15px; background-color: #005baa25;">
				<button type="button" id="payoutDetail-btn-id"
					class="payoutDetail-btn-class">Close</button>
			</div>
		</div>
	</div>



	<%-- 	<form method="get" id="myForm"
		action="${pageContext.request.contextPath}<%=PayoutUserController.URL_BASE%>/updatePayoutStatus">

		<input type="hidden" id="trxid" name="txnId" value="0"> <input
			type="hidden" id="merchantid" name="merchantID" value="0"> <input
			type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
	</form>
	
	<form method="get" id="myForm1"
        action="${pageContext.request.contextPath}<%=PayoutUserController.URL_BASE%>/declinePayoutStatus">
       <input type="hidden" id="trxnid" name="txnId1" value="0"> <input
            type="hidden" id="merchantsid" name="merchantID1" value="0"> <input
            type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
       <input type="hidden" id="cancelreason" name="cancelReason" value="0">
    </form> --%>
	<form method="get" id="myForm"
		action="${pageContext.request.contextPath}<%=PayoutUserController.URL_BASE%>/updatePayoutPaidStatus">

		<input type="hidden" id="trxid" name="txnId" value="0"> <input
			type="hidden" id="merchantid" name="merchantID" value="0"> <input
			type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
		<input type="hidden" id="payoutStatus" name="payoutStatus"
			value="PaymentPaid">
	</form>

	<form method="get" id="myForm1"
		action="${pageContext.request.contextPath}<%=PayoutUserController.URL_BASE%>/declinePayoutStatus1">
		<input type="hidden" id="trxnid" name="txnId1" value="0"> <input
			type="hidden" id="merchantsid" name="merchantID1" value="0">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}"> <input type="hidden"
			id="cancelreason" name="cancelReason" value="0"> <input
			type="hidden" id="payoutStatus1" name="payoutStatus1"
			value="PaymentDeclined">
	</form>




	<style>
.custom-table {
	width: 100%;
	margin-top: 10px;
	font-size: 15px;
	border-collapse: collapse;
}

.custom-table tr {
	display: flex;
	border-bottom-color: #fff;
}

.custom-table  .summary_heading {
	font-size: 18px;
	color: #005baa;
}

.custom-table .popup-key {
	flex: 0.6 !important;
}

.custom-table .hyphen {
	flex: 0.3 !important;
}

.custom-table td {
	border-bottom: 1px solid white;
	padding: 8px;
	flex: 1;
}

.custom-table th {
	
}
</style>

	<div id="delclined-modal-id" class="declined-modal-class">
		<div class="declined-modal-content">
			<div>
				<div
					style="border-bottom: 1px solid orange; padding: 15px 24px; text-align: center; font-size: 16px; color: #005baa; font-weight: 500">
					Reason
					<%--<span style="float: right">X</span>--%>
				</div>

				<div class="reason-div"
					style="padding: 15px 30px; text-align: center;">

					<img
						src="${pageContext.request.contextPath}/resourcesNew1/assets/Declined.svg"
						width="50" height="50" />
					<p id="reason-id" class="reason-class"></p>

				</div>

				<div class="declined-button"
					style="padding: 10px; display: flex; justify-content: center; background-color: #005baa25">
					<button type="button" class="close-Declined" id="closeDeclined-id"
						onclick="closeDeclined()">Close</button>
				</div>
			</div>


		</div>

	</div>

	<!-- Added Pending popup-->
	<div id="pending-modal-id" class="declined-modal-class">
		<div class="declined-modal-content">
			<div>
				<div
					style="border-bottom: 1px solid orange; padding: 15px 24px; text-align: center; font-size: 16px; color: #005baa; font-weight: 500">
					Reason
					<%--<span style="float: right">X</span>--%>
				</div>

				<div class="reason-div"
					style="padding: 15px 30px; text-align: center;">

					<img
						src="${pageContext.request.contextPath}/resourcesNew1/assets/pending.svg"
						width="50" height="50" />
					<p id="pending-reason-id" class="reason-class"></p>

				</div>

				<div class="declined-button"
					style="padding: 10px; display: flex; justify-content: center; background-color: #005baa25">
					<button type="button" class="close-Declined" id="closeDeclined-id"
						onclick="closePending()">Close</button>
				</div>
			</div>


		</div>

	</div>






	<script>






	document.addEventListener('DOMContentLoaded', function () {
    			const closeModalBtn = document.getElementById('X-slip');
    			closeModalBtn.addEventListener('click', function () {
    				const modal = document.getElementById('slip-modal-id');
    				modal.style.display = 'none';
    			});
    		});
	
	   document.getElementById("email-close-btn").onclick = function() {
		    document.getElementById("email-modal-id").style.display = "none";
		    document.getElementById("emailErrorMessage").style.display = "none";
		    document.getElementById("email-input").value = "";
		}
	   
	   function submitEmail(){
			var email = document.getElementById("email-input").value.trim();
		    var fromDate = document.getElementById("from").value;
		    var toDate = document.getElementById("to").value;
		    var e2 = document.getElementById("export1").value;
		    var emailErrorMessage = document.getElementById("emailErrorMessage");
		    
		    if (email === "") {
		        alert("Please enter an email address");
		        return;
		    }
		    
		    var inputValue = email;
	        var isValid = inputValue.endsWith("@gomobi.io");
	      	    
	        if (!isValid) {
	            emailErrorMessage.style.display = "block";
	            return false;
	        } else {
	            emailErrorMessage.style.display = "none";
	        }
		    console.log("email is"+email);
		    console.log("date 1 is "+fromDate);
		    console.log("date 2 is "+toDate);
		    console.log("export is "+e2);
		    
		    document.location.href = '${pageContext.request.contextPath}/payoutDataUser/exportPayoutAsEmail?' +
		    '&date=' + fromDate + 
		    '&date1=' + toDate + 
		    '&emailId=' + email +
		    '&export=' + e2;

		    
		   
		}
	   
	   
		$(document).ready(function() {
			// $('#data_list_table').DataTable();
		});

		$(document).ready(
				function() {
					$('#data_list_table').DataTable({
						"bSort" : false,
						"stateSave" : true,
						"stateSaveParams" : function(settings, data) {
							data.length = settings._iDisplayLength;
						},
					});

					var savedLength = parseInt($('#data_list_table')
							.DataTable().state().length, 10);
					if (savedLength) {
						$('#data_list_table').DataTable().page.len(savedLength)
								.draw('page');
					}
				});

		function openDialog(txnId, merchantId, itemId) {

			const txnid = document.getElementById("trxid")
			const merchantid = document.getElementById("merchantid")

			const txnid1 = document.getElementById("trxnid")
			const merchantid1 = document.getElementById("merchantsid")
			//alert(txnId);
			txnid.value = txnId
			merchantid.value = merchantId

			txnid1.value = txnId
			merchantid1.value = merchantId

			document.getElementById("curr-procces").value = itemId

			$("#mobi_modal_popup").show();
			$("#overlay-popup").show();

		}

		function ProcessPaid() {

			var txnid = document.getElementById("trxid").value;
			var merchantId = document.getElementById("merchantid").value;
			var status = "pay";

			//alert(txnid" "status " "merchantId);
			// Send an AJAX request to the server
			$
					.ajax({
						url : "${pageContext.request.contextPath}/payoutDataUser/updatePayoutPaidStatus1",
						type : 'GET',
						data : {
							"merchantId" : merchantId,
							"txnid" : txnid,

							"status" : status
						},
						success : function(response) {

							if (response.responseCode == "0000") {
								//document.getElementById('response').innerHTML = response.responseCode;
								var currprocessIndx = document
										.getElementById("curr-procces").value;
								var trTag = document.querySelector('tr.dto-'
										+ currprocessIndx);
								var tdTag = trTag
										.querySelector('td:nth-of-type(8)');
								tdTag.innerText = 'Paid';
								var eyeIcon = trTag
										.querySelector('td:nth-of-type(15)');
								eyeIcon.style.display = 'none';

								statuschange();

								//�alert("Sucess");
							}
						},
						error : function(jqXHR, textStatus, errorThrown) {
							// Handle any errors that occur during the request
							alert("error");
							closeDialog()
							console.error(errorThrown);
						}
					});

		}
		function closeNotify(){
			document.getElementById("email-modal-id").style.display = "none";
			document.getElementById("emailErrorMessage").style.display = "none";
			document.getElementById('email-input').value = '';
		}

		//saravana

		function statuschange() {
			$("#mobi_modal_popup").hide();
			$("#overlay-popup").hide();
			const radioGroup = document.getElementsByName('payout_radio');
			for (let i = 0; i < radioGroup.length; i++) {
				radioGroup[i].checked = false;
			}

		}

		function ProcessDecline() {

			var txnid1 = document.getElementById("trxnid").value;
			var merchantId1 = document.getElementById("merchantsid").value;
			const hidDeclineBox = document.getElementById("cancelreason").value;

			document.getElementById("myForm1").submit();
		}

		function closeDialog() {
			$("#mobi_modal_popup").hide();
			$("#overlay-popup").hide();
		}

		function openDecline() {
			$("#decl").show();
			$("#overlay-popup").show();
			$("#mobi_modal_popup").hide();

		}

		function confirmDecline() {
			const declineReason = document.getElementById("decline-reason").value;
			const hidDeclineBox = document.getElementById("cancelreason").value = declineReason;
			/* alert(hidDeclineBox); */
			ProcessDecline();
		}

		function closeDecline() {
			$("#decl").hide();
			$("#overlay-popup").hide();

		}
		
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

	</script>


	<style>
.email-modal-class {
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

.email-content-class {
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

.email-submit-class:focus, .email-submit-class:active {
	background-color: #005baa;
}
}
</style>

	<script>
		function validateAmount() {

			// Get the input element
			//var searchInput = document.getElementById('searchApi');

			// Remove non-numeric characters from the input value
			//var sanitizedValue = searchInput.value.replace(/[^0-9.]/g, '');

			// Set the sanitized value back to the input field
			//searchInput.value = sanitizedValue;
		}


		// payout Sales Slip - End
		function openDeclinedReason(failureReason) {

			console.log("reason : ", failureReason);
			var reason = failureReason;
			var reasonElement = document.getElementById('reason-id');
			reasonElement.textContent = reason;
			
			var modal = document.getElementById("delclined-modal-id");
			modal.style.display = "block";

		}
		
		//pending popup
		function openPendingReason(failureReason) {

			console.log("reason : ", failureReason);
			var reason = failureReason;
			var reasonElement = document.getElementById('pending-reason-id');
			reasonElement.textContent = reason;
			
			var modal = document.getElementById("pending-modal-id");
			modal.style.display = "block";

		}


		function closeDeclined() {
			var modal = document.getElementById("delclined-modal-id");
			modal.style.display = "none";
		}
		
		function closePending() {
			var modal = document.getElementById("pending-modal-id");
			modal.style.display = "none";
		}









		function openPayoutslipMore(payoutDate, merchantName, payeeName, payeeBrn, payeeAccNumber, payeeBankName, invoiceIdProof, payoutId, payoutamount, payoutStatus, timeStamp, paidTime, paidDate, submerchantMid, businessName, failureReason, createdby, payouttype, srcrefno,paymentReference) {
			var modal = document.getElementById("payoutDetailsId");
			var body = document.body;
			var initialOverflow = body.style.overflow;

			modal.style.display = "block";
			body.style.overflow = "hidden";
			var modalContent = modal.querySelector(".payoutDetailsClass-content");

			var table_content = document.getElementById("table-content");
			table_content.scrollTop = 0;
			var data = [
				["Date", payoutDate],
				["Merchant Name", merchantName],
				["Customer Name", payeeName],
				["BRN/IC", payeeBrn],
				["Account Number", payeeAccNumber],
				["Bank Name", payeeBankName],
				["Transaction ID", invoiceIdProof],
				["Payout ID", payoutId],
				["Amount (RM)", payoutamount],
				["Status", payoutStatus],
				["Timestamp", timeStamp.replace(".0", "")],
				["Paid/Declined Time", paidTime],
				["Paid/Declined Date", paidDate],
				["Sub Merchant MID", submerchantMid],
				["Sub Merchant Name", businessName],
				["Declined Reason", failureReason]
			];


			/* if (payoutStatus === 'Declined') {
				data[15][1] = failureReason;
			}else {
				data.splice(15, 1);
			} */
			
			if (payoutStatus === 'Declined') {
			    // Check if failureReason is null or empty
			    if (failureReason == null || failureReason.trim() === '') {
			        data[15][1] = 'Payout transaction failed';
			    } else if (failureReason.includes('Invalid payee details. Account Number / Proxy Type/Value cannot be found.')) {
			        data[15][1] = 'Invalid payee details';
			    } else if (failureReason.includes('ERROR (') || failureReason.includes('Exception') || failureReason.includes('exception')) {
			        data[15][1] = 'Host Side Error';
			    } else {
			        data[15][1] = failureReason; // Use the original failure reason if none of the conditions are met
			    }
			} else {
			    data.splice(15, 1); // Remove the failure reason if not Declined
			}


			if (payoutStatus === 'Paid') {
				data[9][1] = `<img class="" src="${pageContext.request.contextPath}/resourcesNew1/assets/paid.svg" width="20" height="20" style="margin-right: 5px;"/><span style="color: #11bf36;    font-weight: 500;">Paid</span>`;

				var salesSlipHtml = `<a href="javascript:void(0)" class="openPayoutslipInPopup"><img class="w24" src="${pageContext.request.contextPath}/resourcesNew1/assets/salesSlip.svg" width="20" height="20" style="margin-right: 5px;"'/><span style="color: cyan; position: relative; bottom: 5px;">View Receipt</span></a>`;
				var salesSlipRow = ["Sales Slip:", salesSlipHtml];
				data.push(salesSlipRow);
			} else if (payoutStatus === 'Declined') {
				data[9][1] = `<img class="" src="${pageContext.request.contextPath}/resourcesNew1/assets/Declined.svg" width="20" height="20" style="margin-right: 5px;"/><span style="color: red;    font-weight: 450;">Declined</span>`;
				//data[15][1] = failureReason;

				if (failureReason == null || failureReason.trim() === '') {
                  data[15][1] = 'Payout transaction failed';
                } else if (failureReason.includes('Invalid payee details. Account Number / Proxy Type/Value cannot be found.')) {
                  data[15][1] = 'Invalid payee details';
                } else if (failureReason.includes('ERROR (') || failureReason.includes('Exception') || failureReason.includes('exception')) {
                  data[15][1] = 'Host Side Error';
                } else {
                  data[15][1] = failureReason; // Use the original failure reason if none of the conditions are met
                }
			}
			else if (payoutStatus === 'Pending') {
				data[9][1] = `<img class="" src="${pageContext.request.contextPath}/resourcesNew1/assets/pending.svg" width="20" height="20" style="margin-right: 5px;"/><span style="color: #F9C84C;    font-weight: 450;">Pending</span>`;
				
			}else if (payoutStatus === 'In Process') {
			    console.log("coming");
			    data[9][1] = `<img class="" src="${pageContext.request.contextPath}/resourcesNew1/assets/on_process.svg" width="20" height="20" style="margin-right: 5px;"/><span style="color: #8A8A8A;    font-weight: 450;">In Process</span>`;
			}

			var table = document.createElement("table");
			table.classList.add("custom-table");
			var tbody = document.createElement("tbody");

			var head_row = document.createElement("tr");
			var popup_heading = document.createElement("th");
			popup_heading.classList.add("summary_heading");
			popup_heading.innerText = 'Transaction Details';
			popup_heading.style.fontWeight='600';
			popup_heading.style.fontSize='18px';

			tbody.appendChild(head_row);
			head_row.appendChild(popup_heading);

			for (var i = 0; i < data.length; i++) {
				var row = document.createElement("tr");
				var option = document.createElement("td");
				option.classList.add("popup-key");
				var hyphen = document.createElement("td");
				hyphen.classList.add("hyphen");
				hyphen.innerText = '-';

				var value = document.createElement("td");
				value.classList.add("popup-value");
				option.textContent = data[i][0];
				value.innerHTML = data[i][1];
				option.style.color = '#545a5f';
				value.style.color = '#837878';
				value.style.fontSize='15px';
				option.style.fontSize='15px';
				option.style.fontWeight='500';
				hyphen.style.fontSize='18px';
				hyphen.style.fontWeight='600';
				row.appendChild(option);
				row.appendChild(hyphen);
				row.appendChild(value);
				tbody.appendChild(row);
			}
			table.appendChild(tbody);
			table_content.appendChild(table);

			if (payoutStatus === 'Paid') {
				var salesSlipLink = table_content.querySelector(".openPayoutslipInPopup");
				salesSlipLink.addEventListener("click", function() {
					openPayoutslip(invoiceIdProof, payeeName, createdby, payoutamount, timeStamp.replace(".0", ""), srcrefno, payoutStatus, payouttype, payoutDate,paymentReference,payoutId,paidTime,paidDate);
				});
			}

			var closeButtonModal = document.getElementById("closeModalBtn");
			closeButtonModal.addEventListener("click", function() {
				modal.style.display = "none";
				body.style.overflow = initialOverflow;
				resetModal();
			});

			var closeButtonDetail = document.getElementById("payoutDetail-btn-id");
			closeButtonDetail.addEventListener("click", function() {
				while (table_content.firstChild) {
					table_content.removeChild(table_content.firstChild);
				}
				modal.style.display = "none";
				body.style.overflow = initialOverflow;
				resetModal();
			});

			function resetModal() {
				while (tbody.firstChild) {
					tbody.removeChild(tbody.firstChild);
				}
				modal.style.display = "none";
				body.style.overflow = initialOverflow;
			}
		}




		function formatDate(dateString) {
			console.log("Input dateString:", dateString);
			var parts = dateString.split(" ");
			var datePart = parts[0].split("/");
			var timePart = parts[1];
			var formattedDateString = datePart[2] + "-" + datePart[1] + "-" + datePart[0] + " " + timePart; // Added space before time
			var date = new Date(formattedDateString);
			console.log("Date object:", date);

			var monthNames = ["January", "February", "March", "April", "May", "June",
				"July", "August", "September", "October", "November", "December"];

			var day = date.getDate();
			var monthIndex = date.getMonth();
			console.log("Month index:", monthIndex);
			var year = date.getFullYear();
			var hours = date.getHours();
			var minutes = date.getMinutes();
			var seconds = date.getSeconds();
			hours = hours < 10 ? '0' + hours : hours;
			minutes = minutes < 10 ? '0' + minutes : minutes;
			seconds = seconds < 10 ? '0' + seconds : seconds;
			// var formattedDate = day+"-"+ monthNames[monthIndex].substring(0, 3) + "-" + year + ", " + " " + hours + ":" + minutes + ":" + seconds;
			var formattedDate = (day < 10 ? '0' + day : day) + "-" + monthNames[monthIndex].substring(0, 3) + "-" + year + ", " + " " + hours + ":" + minutes + ":" + seconds;
			console.log("Formatted date:", formattedDate);
			return formattedDate;
		}

		  function formatPayoutDate(dateString) {
			    const date = new Date(dateString);
			    const options = { day: 'numeric', month: 'long', year: 'numeric' };
			    return date.toLocaleDateString('en-GB', options);
			}


		function openPayoutslip(invoiceIdProof, payeeName, createdBy, payoutAmount, timeStamp, srcrefno, payoutStatus, payoutType,payoutdate,paymentReference,payoutId,paidTime,paidDate) {
	        body.style.overflow = "hidden";
			var modal = document.getElementById("xPay_slip-modal-id");
	        var modal1 = document.getElementById("payout-slip-main-container-id");
	        var modal2 = document.getElementById("slip-card-id");

	        globalTxnId = invoiceIdProof;
	        
			modal.style.display = "block";

	        document.getElementById("new_slip_amount").innerText = payoutAmount;
	        document.getElementById("new_slip_transferAmt").innerText = "MYR "+payoutAmount;
	        var formattedDate = timeStamp.replace(".0", "");
	        document.getElementById("paidTime").innerHTML = paidTime;
	        document.getElementById("paidDate").innerHTML = formatPayoutDate(paidDate);

	       // document.getElementById("new_slip_timestamp").innerText = formattedDate;
	        document.getElementById("invoice-id").innerText = invoiceIdProof;
	        document.getElementById("new_slip_merchant").innerText = createdBy;
	        document.getElementById("new_slip_recipient").innerText = payeeName;
	       // document.getElementById("new_slip_when").innerText = payoutDateNewSlip(payoutdate);
	       /*  console.log("sjhadkajdas "+payoutDateNewSlip(payoutdate)); */
	        document.getElementById("new_slip_paymentType").innerText = payoutType;
	        document.getElementById("new_slip_paymentReference").innerText = paymentReference;
	        document.getElementById("new_slip_payoutId").innerText = payoutId;
	       

	        /* document.getElementById("new_slip_paymentReference").innerText = paymentReference; */

	        window.onclick = function(event) {
	            if (event.target === modal || event.target === modal1) {
	                closeXpayModal();
	            }
	        };


	        

		}

		  function closeXpayModal() {
		        body.style.overflow = initialOverflow;
		        var modal = document.getElementById("xPay_slip-modal-id");
		        var modal1 = document.getElementById("payout-slip-main-container-id");
		        var modal2 = document.getElementById("slip-card-id");
		        modal.style.display = "none";

		    }

		function closeSlipModal() {
			var modal = document.getElementById("slip-modal-id");
			modal.style.display = "none";
		}


	</script>


	<div id="pagination"></div>
	<input type="hidden" id="pgnum">


	<script>




		//Assuming you have a function to add a class to elements
		function addClass(element, className) {
			if (element.classList) {
				element.classList.add(className);
			} else {
				element.className += ' ' + className;
			}
		}







		function dynamic(pgNo){
			/* alert("Page Number:"+pgNo); */
			document.getElementById("pgnum").value=pgNo;
			loadSelectData2();

		}

		function previous(pgNo){
			/* alert("Page Number:"+pgNo); */
			pgNo--;
			document.getElementById("pgnum").value=pgNo;
			loadSelectData2();

		}

		function next(pgNo){
			/* alert("Page Number:"+pgNo); */
			pgNo++;
			document.getElementById("pgnum").value=pgNo;
			loadSelectData2();
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
				<%--// Pagination.size = Math.ceil(${paginationBean.fullCount}/10) ||100;--%>

				Pagination.size = Math.ceil(${paginationBean.querySize} / 20);
				<%--Pagination.size = ((${paginationBean.currPage})+4) ||100;--%>
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


			// last function
			// Last: function() {
			// 	Pagination.code += '<a onclick="dynamic(((Pagination.page)+1))">'+ ((Pagination.page)+1)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+2))">'+ ((Pagination.page)+2)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+3))">'+ ((Pagination.page)+3)+ '</a>'+'<i>...</i>';
			// },


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
//         Pagination.code += '<a onclick="dynamic(' + lastPage + ')">' + lastPage + '</a>';
				} else {
					// if less than 3 page generate <a> tag
					for (var i = Pagination.page + 1; i <= lastPage; i++) {
						Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
					}
				}
			},



			// old code
			// add first page with separator
			// First: function() {
			// 	if(Pagination.page==1){
			//
			// 		Pagination.code += '<i>...</i>'+'<a onclick="dynamic(Pagination.page)" id="page1">'+Pagination.page+'</a>';
			//
			// 	}
			// 	else{
			// 		Pagination.code += '<a>1</a>'+'<i>...</i>'+'<a  onclick="dynamic(((Pagination.page)-1))">'+((Pagination.page)-1)+'</a>'+'<a id="page2" onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';
			// 	}
			// },


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



	<style>
.slip-table tr td {
	padding: 4px 8px;
}

.modal1 {
	display: none;
	position: fixed;
	z-index: 9999;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	overflow: hidden;
	background-color: rgba(0, 0, 0, 0.5);
}

.modal-content1 {
	background-color: #fefefe;
	margin: 5% auto;
	padding: 20px;
	border: 1px solid #888;
	width: 80%;
	max-width: 38%;
	border-radius: 11px;
	box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
	/*overflow-y: hidden;*/
	max-height: calc(106% - 172px);
}

.close1 {
	color: #aaa;
	float: right;
	font-size: 28px;
	font-weight: bold;
}

.close:hover, .close:focus {
	color: black;
	text-decoration: none;
	cursor: pointer;
}

.declined-modal-class {
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

/*.declined-modal-content {*/
/*	background-color: #fefefe;*/
/*	margin: 15% auto;*/
/*	padding: 24px;*/
/*	border: 1px solid #888;*/
/*	width: 92%;*/
/*	max-width: 460px;*/
/*	height: auto;*/
/*	border-radius: 15px;*/
/*}*/
.declined-modal-content {
	background-color: #fefefe;
	margin: 15% auto;
	/*padding: 24px;*/
	border: 1px solid #888;
	width: 92%;
	max-width: 460px;
	/* height: auto !important; */
	border-radius: 15px;
	height: auto;
}

.declined-modal-content {
	position: relative;
}

.yellow-line-declined {
	background-color: #f0c207;
	height: 0.9px;
	position: absolute;
	top: 51px;
	width: calc(100% - 1px);
	left: 1px;
}

.declined-reason-head {
	color: #005baa;
	font-size: 18px;
	display: flex;
	justify-content: center;
	align-items: center;
}

.close-Declined {
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

.close-Declined:focus, .close-Declined:active {
	background-color: #005baa; /* Same color as default */
}

.reason-class {
	color: black;
	font-size: 17px;
	display: flex;
	justify-content: center;
	align-items: center;
}

.declined-img {
	display: flex;
	justify-content: center;
	align-items: center;
}

.declin-img-img {
	height: 51px;
	position: relative;
	bottom: 6px;
}

.payoutDetailsClass {
	display: none;
	position: fixed;
	z-index: 999;
	left: 0;
	top: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.4);
}

/*.payoutDetailsClass-content {*/
/*	background-color: #fefefe;*/
/*	margin: 15% auto;*/
/*	border: 0px solid #888;*/
/*	max-width: 60%;*/
/*	border-radius: 7px;*/
/*	height: auto;*/
/*}*/

/*  for within scroll */
.payoutDetailsClass-content {
	background-color: #fefefe;
	margin: 2% auto;
	border: 0px solid #888;
	max-width: 60%;
	border-radius: 7px;
	height: auto;
	overflow-y: auto !important;
	height: 95%;
	scrollbar-width: none;
}

.payoutDetailsClass::-webkit-scrollbar {
	width: 0;
}

.yellow-line-morePayout {
	background-color: #f0c207;
	height: 1.9px;
	position: absolute;
	top: 260px;
	width: calc(84% - 359px);
	left: 21.5%;
}

.payout-head {
	font-size: 18px;
	color: #005baa;
	display: flex;
	justify-content: center;
	align-items: center;
}

.transaction-details {
	text-align: left;
	font-size: 20px;
	margin-top: 20px;
	font-weight: bold;
	color: #005baa;
}

.close-payoutDetails {
	font-size: 34px;
	position: absolute;
	bottom: 392px;
	left: 1036px;
	cursor: pointer;
	cursor: pointer;
}

.payoutDetail-btn-class {
	background-color: #005baa;
	color: white;
	border-radius: 25px;
	border: none;
	padding: 10px 27px;
	font-size: 12px;
	height: 35px;
	outline: none;
	cursor: pointer
}

.payoutDetail-btn-class:focus, .payoutDetail-btn-class:active {
	background-color: #005baa; /* Same color as default */
}

.payoutDetail-btn-class-div {
	display: flex;
	justify-content: center;
	margin-top: 24px;
}

.popup-value {
	display: flex;
}

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

.slip-modal-content-class {
	background-color: #fefefe;
	margin: 15% auto;
	/*	padding:  10px;*/
	border: 1px solid #888;
	width: 92%;
	max-width: 460px;
	border-radius: 15px;
	height: auto;
}

.slip-modal-close-btn-class {
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

.slip-modal-close-btn-class:focus, .slip-modal-close-btn-class:active {
	background-color: #005baa;
}

.slipModal-close-btn {
	display: flex;
	align-items: center;
	justify-content: center;
}

.payment-slip-text {
	color: #005baa;
	font-size: 18px;
}

.slip-yellow-line {
	background-color: #f0c207;
	height: 1.9px;
	position: absolute;
	top: 260px;
	width: calc(66% - 450px);
	left: 33.5%;
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

.editBtn {
	text-decoration: none;
	color: #039be5;
	-webkit-tap-highlight-color: transparent !important;
	border: none;
	cursor: pointer;
	background: none;
}

.three-btn-one {
	background-color: white !important;
	color: #005baa !important;
	border-style: solid;
	border-color: #005baa !important;
	border-width: 2px;
	font-weight: 530;
	height: 42px !important;
	line-height: 40px !important;
	padding: 0 30px !important;
	margin-left: 14px !important;
}

.three-btn-search {
	background-color: white !important;
	color: #005baa !important;
	border-style: solid;
	border-color: #005baa !important;
	border-width: 2px;
	font-weight: 530;
	height: 42px !important;
	line-height: 40px !important;
	padding: 0 30px !important;
	margin-left: 14px ! important;
}

/* empty modal pop up */
#exampleModalCenter {
	z-index: 99;
	width: 25%;
	font-size: 24px;
	font-weight: 400;
	font-family: 'Poppins', sans-serif;
	text-align: center;
}

.three-btn-search {
	background-color: white !important;
	color: #005baa !important;
	border-style: solid;
	border-color: #005baa !important;
	border-width: 2px;
	font-weight: 530;
	height: 42px !important;
	line-height: 40px !important;
	padding: 0 30px !important;
	margin-left: 14px ! important;
}
</style>



	<input type="hidden" id="txnType">
	<input type="hidden" id="pag">


	<script>

		var txnType = document.getElementById("txnType").value="${paginationBean.TXNtype}";
		if (txnType){
			document.getElementById("pagination").style.display="none";
		}

		if(${paginationBean.itemList.size()}==0){
			document.getElementById("no-data").innerText = "No data available";
			var hello = document.getElementById("no-data").value;
			console.log("size is : "+hello);

		}

		function closepopup(){
			document.getElementById("exampleModalCenter").style.display ="none";
			document.getElementById("pop-bg-color").style.display ="none";

		}

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

	<script>
    var body = document.body;
    var initialOverflow = body.style.overflow;

</script>


<script>
    function downloadPayoutSlip() {
        var modalContent = document.getElementById('xPay_slip-modal-id');
        // var hidingElements = document.getElementById('hiding_elements_div');

        if (!modalContent) {
            alert('Modal content not found');
            return;
        }

        // console.log('Hiding elements:', hidingElements);

        // Ensure text does not wrap
        var transferAmt = document.getElementById('new_slip_transferAmt');
        if (transferAmt) transferAmt.style.whiteSpace = 'nowrap';

        // Temporarily set a large enough size to capture all content
        var originalWidth = modalContent.style.width;
        var originalHeight = modalContent.style.height;
        modalContent.style.width = modalContent.scrollWidth + 'px';
        modalContent.style.height = modalContent.scrollHeight + 'px';
        // Hide specific elements
        // if (hidingElements) {
        //     hidingElements.style.display = 'none';
        //     console.log('Element hidden:', hidingElements.style.display);
        // }

        // Use html2canvas to capture the content as an image
        html2canvas(modalContent, {
            backgroundColor: null, // Transparent background
            scrollX: 0,
            scrollY: 0,
            useCORS: true,
            ignoreElements: (element) => element.id === 'cancel-download' || element.id === 'active-download',
            scale: 2,
        }).then(function (canvas) {
            // Convert canvas to image and initiate download


              var filename = globalTxnId ? globalTxnId + '.png' : 'pay_slip.png';
            var link = document.createElement('a');
            link.href = canvas.toDataURL('image/png');

            link.download = filename;
            // globalInvoiceIdProof//
            link.click();
        }).catch(function (error) {
            console.error('Error capturing the canvas:', error);
        }).finally(function () {
            // Restore original styles
            modalContent.style.width = originalWidth;
            modalContent.style.height = originalHeight;
        });
    }</script>

<style>
.rightSide_color_code_slip {
	color : #2D2D2D;
}
</style>

</body>

</html>