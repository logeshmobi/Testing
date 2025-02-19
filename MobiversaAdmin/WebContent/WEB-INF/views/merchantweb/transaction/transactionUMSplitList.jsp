
<%@page import="com.mobiversa.payment.controller.MerchantWebUMTransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
	<%@ page language="java" import="java.util.*" %> 
<%@ page import = "java.util.ResourceBundle" %>
<% ResourceBundle resource = ResourceBundle.getBundle("config");
  String actionimg=resource.getString("NEWACTION");
  String voidimg=resource.getString("NEWVOID");
  String refundimg=resource.getString("NEWREFUND");
  String eyeimg=resource.getString("NEWEYE");
  %>

<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
</head>


<style>

.table-border-bottom td {
border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important;
}

.w24
{
 width:24px;
}

.key_hover:hover{cursor:pointer;}

.hide_key {
display: none;
}
#merchantName:hover
{
   color:275ca8;
}
#agentName:hover
{
   color:275ca8;
}

.example_e1:focus {
    outline:none !important;
}

.example_e1  {
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
   color:rgb(39, 92, 168);
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
    transition: box-shadow 0.3s cubic-bezier(0.35, 0, 0.25, 1), transform 0.2s cubic-bezier(0.35, 0, 0.25, 1),
     background-color 0.3s ease-in-out;
  font-style:Arial, Helvetica, sans-serif;
  border-radius:15px;
  
   }

.example_e1:hover {
 color:rgb(39, 92, 168);
font-weight: 600 !important;

-webkit-box-shadow: 0px 5px 40px -10px rgba(0,0,0,0.57);
-moz-box-shadow: 0px 5px 40px -10px rgba(0,0,0,0.57);
transition: all 0.3s ease 0s;
border:2px solid #cfcfd1;
 outline:0 !important;
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
	.export_div .select-wrapper { width:65%;float:left;}
	.datepicker { width:80% !important;}				
	.select-wrapper .caret { fill: #005baa;}
	
	.addUserBtn,.addUserBtn:hover {
	background-color: #fff;border: 1px solid #005baa;border-radius: 20px;color: #005baa; font-weight:600;}
	 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
	.button-class { float:right;}
	.float-right {float:right; }
	</style>
	
	
	 <style>
	td, th { padding: 7px 8px; color:#707070;}
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
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
    </script>


<script lang="JavaScript">
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

		var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
		var todateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;

		/* var e2 = document.getElementById("txnType").value; */
		if (e == null ||e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchUMSplit?date='
					+ fromdateString + '&date1=' + todateString;
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
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/umSplitExport?fromDate=' +fromdateString
					+ '&toDate=' + todateString +'&export='+e2; 
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
		 document.location.href = '${pageContext.request.contextPath}/transactionUmweb/umSplitExport?fromDate=' + e
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

	

	//Script by Denis Gritcyuk: tspicker@yahoo.com
	//Submitted to JavaScript Kit (http://javascriptkit.com)
	//Visit http://javascriptkit.com for this script
</script>
<body class="">

<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>EZYSPLIT Transaction Summary</strong></h3>
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
								<div class="input-field col s12 m3 l3">
											<label for="from" style="margin:0px;">From </label>
											<input type="hidden"
												name="date11" id="date11" <c:out value="${fromDate}"/>> 
									<input type="text" id="from" name="fromDate"  class="validate datepicker"
									onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
									<i class="material-icons prefix">date_range</i>
				
										
									</div>

									<div class="input-field col s12 m3 l3">

											<label for="to" style="margin:0px;">To</label>
										<input type="hidden"
													name="date12" id="date12" <c:out value="${toDate}"/>>
										<input id="to" type="text" name="toDate" class="datepicker" 
										onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
										<i class="material-icons prefix">date_range</i>
									</div>





								

								 <div class="input-field col s12 m3 l3">
										 <input
											type="hidden" name="export1" id="export1" <c:out value="${status}"/>>
										<select name="export" id="export" onchange="return loadDropDate13();"
											>
											<option selected value="">Choose</option>
											<option value="PDF">PDF</option>
											<option value="EXCEL">EXCEL</option>
										</select>
										<label class="control-label">Export Type</label>
									</div>
								
								 <div class="input-field col s12 m3 l3">
								 <div class="button-class" style="float:left;">

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
	$('.pickadate-clear-buttons').pickadate({
    close: 'Close Picker', 
	formatSubmit: 'dd/mm/yyyy',
	
   
});
$('.datepicker').pickadate();


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
					            <table id="data_list_table" class=" table-border-bottom table table-striped table-bordered">
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
										<th>Sales Slip</th>
										<th>Void</th>
										<th>Refund</th>
								</tr>
							</thead>
								<tbody>

									<c:forEach items="${paginationBean.itemList}" var="dto" varStatus="id">
										<tr>
											 <td>${dto.date}</td>
											<td>${dto.time}</td>
										    <td>${dto.f001_MID}</td> 
										    <td>${dto.f354_TID}</td>
											<td style="text-align: right;">${dto.f007_TXNAMT}</td>
											<td>${dto.f268_CHNAME}</td>
											<td>${dto.PAN}</td>
											<td>${dto.f270_ORN}</td>
											<td class = 'key_hover'> <span onclick="show_key('${id.index}')" id="hide_key_${id.index}"><img class="w24" src='data:image/png;base64,<%=eyeimg%> '/> </span>
											<span onclick="hide_key('${id.index}')" id="show_key_${id.index}" class="hide_key">${dto.f011_AUTHIDRESP}</span></td>
											<td class = 'key_hover'> <span onclick="show_rrn('${id.index}')" id="hide_rrn_${id.index}"><img class="w24" src='data:image/png;base64,<%=eyeimg%> '/> </span>
											<span onclick="hide_rrn('${id.index}')" id="show_rrn_${id.index}" class="hide_key">${dto.f023_RRN}</span></td>
											<td>${dto.STATUS}</td>
											<td>${dto.cardType}</td>
											<td style="text-align: right;">${dto.mdrAmt}</td>
											<td style="text-align: right;">${dto.netAmount}</td>
											<td>${dto.settlementDate}</td>

											<td style="text-align:center;">
											<c:if test="${dto.STATUS =='SETTLED' || dto.STATUS =='NOT SETTLED' || dto.STATUS =='VOIDED'}">		
													<a href="javascript:void(0)" id="openNewWin"
														onclick="javascript: openNewWin('${dto.f263_MRN}')"> 
														<img class="w24" src='data:image/png;base64,<%=actionimg%> '/></a>

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

											<td style="text-align:center;">
												<c:if test="${dto.STATUS =='NOT SETTLED'}">
													<a
														href="${pageContext.request.contextPath}/transactionUmweb/cancelEzylinkPayment1/${dto.f263_MRN}">
														 <img class="w24" src='data:image/png;base64,<%=voidimg%> '/>
													</a>
												</c:if></td>
											<td style="text-align:center;"><c:if
													test="${dto.STATUS =='SETTLED'}">
													<a
														href="${pageContext.request.contextPath}/transactionUmweb/refundEzylinkPayment/${dto.f263_MRN}">
														<img class="w24" src='data:image/png;base64,<%=refundimg%> '/> 
													</a>
												</c:if></td>
										</tr>
									</c:forEach>
									
									<tr>
                                    <td colspan="18" style="text-align: center;">
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
	
</body>

</html>