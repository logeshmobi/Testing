<%@page
	import="com.mobiversa.payment.controller.SettlementUserController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
</head>

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
<script type="text/javascript">

function openNewWin(mrn){
	//alert(txnID);
	
	var url=window.location;
	//alert(url);
	var src = document.getElementById('popOutiFrame').src;
	 src=url+'transaction/UMdetails/'+mrn;
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
    </script>


<script lang="JavaScript">
	function loadSelectData() {
		//alert("test");
		
			var e = document.getElementById("from").value;
			
			//alert("e"+e);
			
			if( document.getElementById("umFile").checked){
			
				var u = document.getElementById("umFile").value;
			
			}else{
				 var u = "No";
			}
			
			if( document.getElementById("mercFile").checked){
				
				var me = document.getElementById("mercFile").value;
			
			}else{
				 var me = "No";
			}
			
			if( document.getElementById("mdrFile").checked){
				
				var md = document.getElementById("mdrFile").value;
			
			}else{
				 var md = "No";
			}
			
			if( document.getElementById("dedFile").checked){
				
				var ded = document.getElementById("dedFile").value;
			
			}else{
				 var ded = "No";
			}
			if( document.getElementById("csvFile").checked){
				
				var csv = document.getElementById("csvFile").value;
			
			}else{
				 var csv = "No";
			}
			
			/* var me = document.getElementById("mercFile").value;
			alert("me"+me);
			var md = document.getElementById("mdrFile").value;
			alert("md"+md);
			var ded = document.getElementById("dedFile").value;
			alert("ded"+ded);
			var csv = document.getElementById("csvFile").value;
			alert("csv"+csv); */
			var st = document.getElementById("stPeriod").value;
			
			//alert("st"+st);
		
		
// 		
		var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
			

		var fromday = fromDate.getDate();
		var frommon = fromDate.getMonth() + 1;
		var fromyear = fromDate.getFullYear();

		
		/* var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear; */
		

		var fromdateString = fromyear+'-'+(frommon <= 9 ? '0' + frommon : frommon)+'-'+(fromday <= 9 ? '0' + fromday : fromday);
		

		/* var e2 = document.getElementById("txnType").value; */
		if (e == null || e == '' ) {
			alert("Please Select date");
			//form.submit == false;
		} else {
			
 			//alert("else");
			document.getElementById("dateval1").value = fromdateString;
			
			document.location.href = "${pageContext.request.contextPath}/settlementDataUser/generateFileCall?umFile="
					+u+"&mercFile="+me+"&mdrFile="+md+"&dedFile="+ded+"&csvFile="+csv+"&stPeriod="+st+"&date="+fromdateString;
					
			//alert("loc"+document.location.href);
			
		form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

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

		var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
		var todateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;

		/* alert("e"+e);
		alert("e1"+e1);
		alert("e2"+e2); */
		
		/* var e2 = document.getElementById("txnType").value; */
		if (e == null || e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			/* alert("inside else"); */
			document.getElementById("datevalex1").value = fromdateString;
			document.getElementById("datevalex2").value = todateString;
			
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/settlementDataUser/exportSettlementMDR?date=' + fromdateString
					+ '&date1=' + todateString +'&export='+e2; 
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

	function loadDropDate14() {
		//alert("loadDropDate13");
		var e = document.getElementById("txnType");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("txnType1").value = strUser;
		//alert("txntype: "+strUser);
		//document.getElementById("searchTxnType").value=strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}
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

		/* document.getElementById("dateval1").value = e;
		document.getElementById("dateval2").value = e1;
		document.getElementById("dateval3").value = e2;
		document.getElementById("dateval4").value = e3;
		document.getElementById("dateval5").value = e4; */

		/*  e = document.getElementById("dateval").value; */
		//alert("test2: " + e + " " + e1);
		 document.location.href = '${pageContext.request.contextPath}/transaction/umLinkExport?fromDate=' + e
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

	function loadDropDate11() {
		//alert("strUser.value");
		var e = document.getElementById("stPeriod1");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("stPeriod").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}


	//Script by Denis Gritcyuk: tspicker@yahoo.com
	//Submitted to JavaScript Kit (http://javascriptkit.com)
	//Visit http://javascriptkit.com for this script
</script>
<body class="">

	<%-- <form:form method="POST" id="form1" action="${pageContext.request.contextPath}/settlementDataUser/generateFileCall" name="form1" > --%>
	<div class="container-fluid">
		<div class="row">



			<div class="col s12">
				<div class="card blue-bg text-white">
					<div class="card-content">
						<div class="d-flex align-items-center">
							<h3 class="text-white">
								<strong>CARD File Generation</strong>
							</h3>
						</div>


					</div>
				</div>
			</div>
		</div>



		<div class="row">
			<div class="col s12">
				<div class="card border-radius">
					<div class="card-content padding-card">




						<div class="row">

							<div class="input-field col s12 m6 l6 ">
								UM GENERATE FILES :
								<p>
									<label><input type="radio" name="umFile" path="umFile"
										value="Yes" id="umFile" /><span>Yes</span></label> <label><input
										type="radio" checked="checked" name="umFile" value="No"
										id="umFile" path="umFile" /> <span>No</span></label>
								</p>

							</div>

							<div class="input-field col s12 m6 l6 ">
								MERCHANT FILE :
								<p>
									<label><input type="radio" name="mercFile"
										path="mercFile" value="Yes" id="mercFile" /><span>Yes</span></label>
									<label><input type="radio" checked="checked"
										name="mercFile" value="No" id="mercFile" path="mercFile" /> <span>No</span></label>
								</p>

							</div>

							<div class="input-field col s12 m6 l6 ">
								MDR FILE :
								<p>
									<label><input type="radio" name="mdrFile"
										path="mdrFile" value="Yes" id="mdrFile" /><span>Yes</span></label> <label><input
										type="radio" checked="checked" name="mdrFile" value="No"
										id="mdrFile" path="mdrFile" /> <span>No</span></label>
								</p>

							</div>

							<div class="input-field col s12 m6 l6 ">
								DEDUCTION FILE :
								<p>
									<label><input type="radio" name="dedFile"
										path="dedFile" value="Yes" id="dedFile" /><span>Yes</span></label> <label><input
										type="radio" checked="checked" name="dedFile" value="No"
										id="dedFile" path="dedFile" /> <span>No</span></label>
								</p>

							</div>


							<div class="input-field col s12 m6 l6 ">
								CSV FILE :
								<p>
									<label><input type="radio" name="csvFile"
										path="csvFile" value="Yes" id="csvFile" /><span>Yes</span></label> <label><input
										type="radio" checked="checked" name="csvFile" value="No"
										id="csvFile" path="csvFile" /> <span>No</span></label>
								</p>

							</div>
						</div>

						<div class="row">
							<div class="input-field col s12 m6 l6 ">
								<input type="hidden" name="stPeriod1" id="stPeriod1"
									value="${stPeriod}"> <select name="stPeriod"
									id="stPeriod" onchange="return loadDropDate11();">
									<option selected value="">Choose</option>
									<option value="1">1</option>
									<option value="3">3</option>
									<option value="5">5</option>
								</select> <label for="tid">Settlement Period</label>

							</div>


							<div class="input-field col s12 m6 l6">
								<label for="from" style="margin: 0px;"> Payment Date </label><input
									type="hidden" name="date11" id="date11"
									<c:out value="${fromDate}"/>> <input type="text"
									id="from" name="date1" class="validate datepicker"
									onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
								<i class="material-icons prefix">date_range</i>

							</div>

						</div>


						<div class="row">
							<div class="input-field col s12 m6 l6 ">
								<div class="button-class">
									<input type="hidden" name="date1" id="dateval1">
									<button class="submitBtn" type="button"
										onclick="loadSelectData()">Submit</button>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
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
		</div>


	</div>


	<%-- </form:form> --%>

</body>

</html>