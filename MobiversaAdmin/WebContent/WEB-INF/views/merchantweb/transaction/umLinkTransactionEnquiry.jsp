<%@page import="com.mobiversa.payment.controller.MerchantWebUMTransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
	td, th { padding: 7px 8px;color:#707070; }
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
  
    if (window.focus) {
        newWindow.focus();
    }
		
}
    </script>


<script lang="JavaScript">
	function loadSelectData() {
		
		//alert("Please Select date(s)");
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

		if (e == null ||e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/searchUMLinkEnquiry?date='
					+ fromdateString + '&date1=' + todateString;
			form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

		}
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
		loadDropDate13();
		loadDropDate14();
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e2 = document.getElementById("export1").value;
		var e3 = document.getElementById("txnType1").value;

		//alert("e2" + e2);

		if (e == null || e1 == null || e == '' || e1 == '') {
			//alert("picker :"+e + "  "+ e1);
			e = document.getElementById("dateval1").value;
			e1 = document.getElementById("dateval2").value;
			//alert("hidden : "+e + "  "+ e1);
			if (e == null || e1 == null || e == '' || e1 == '') {
				alert("Please select date(s)");
			}
		} else {

			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;

			/*  e = document.getElementById("dateval").value; */
			//alert("test2: " + e + " " + e1);
			document.location.href = '${pageContext.request.contextPath}/transaction/export?date='
					+ e + '&date1=' + e1 + '&export=' + e2 + '&txnType=' + e3;
			//alert(e);
			form.submit();

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
			document.location.href = '${pageContext.request.contextPath}/transactionUmweb/umLinkEnqExport?fromDate=' + fromdateString
					+ '&toDate=' + todateString +'&export='+e2; 
			form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

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
           <h3 class="text-white">  <strong>EZYLINK Transaction Enquiry</strong></h3>
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
									<input type="text" id="from" name="date1"  class="validate datepicker"
									onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
									<i class="material-icons prefix">date_range</i>
				
										
									</div>

									<div class="input-field col s12 m3 l3">

											<label for="to" style="margin:0px;">To</label>
										<input type="hidden"
													name="date12" id="date12" <c:out value="${toDate}"/>>
										<input id="to" type="text" class="datepicker" 
										onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
										<i class="material-icons prefix">date_range</i>
									</div>


								

	 						<div class="input-field col s12 m3 l3">

										<input
											type="hidden" name="export1" id="export1" <c:out value="${status}"/>>
										<select name="export" id="export" onchange="return loadDropDate13();"
											style="width: 100%">
											<option selected value="">Choose</option>
											<option value="PDF">PDF</option>
											<option value="EXCEL">EXCEL</option>
										</select>
										<label class="control-label">Export Type</label> 
									</div>
								</div> 
							




							<div class="row">
								<div class="input-field col s12 m3 l3">
								<div class="button-class" style="float:left;">
										<input type="hidden" name="date1" id="dateval1"> <input
											type="hidden" name="date2" id="dateval2">
										<button class="btn btn-primary blue-btn" type="button"
											onclick="return loadSelectData();">Search</button>


										
										<input type="hidden" name="dateex1" id="datevalex1"> <input
											type="hidden" name="dateex2" id="datevalex2">
										<button class="export-btn waves-effect waves-light btn btn-round indigo" type="button"
											onclick="return loadExpData();">Export</button> 
									</div>
								</div>

							</div>
						</div>
					</div>
</div></div>
<script>
	$('.pickadate-clear-buttons').pickadate({
    close: 'Close Picker', 
	formatSubmit: 'dd/mm/yyyy',
	
   
});
$('.datepicker').pickadate();

</script>

				
					<div class="row">
					  <div class="col s12">
					      <div class="card border-radius">
					        <div class="card-content padding-card">

					          <div class="table-responsive m-b-20 m-t-15">
					            <table id="data_list_table" class="table table-striped table-bordered">
							<thead>
								<tr>
									<th>Date</th>
									<th>Time</th>
									<th>Txn Type</th>
									<th>Merchant Name</th>
									<!-- <th>F005_EXPDATE</th> -->
									<th>Amount</th>
									<th>TID</th>
									<th>Name on Card</th>
									<th>Masked Pan</th>
									<th>Reference</th>
									<!-- <th>Status</th> -->
									<!-- <th>F263_MRN</th> -->
									<th>AID Response</th>
									<th>Status</th>		
									<th>RRN</th>
																
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${paginationBean.itemList}" var="dto">
									<tr>
										<td>${dto.h003_TDT}</td>
										<td>${dto.time}</td>
										<td>${dto.txnType}</td>
										<td>${dto.merchantName}</td>
										<%-- <td>${dto.f005_EXPDATE}</td> --%>
										<td style="text-align:right;">${dto.f007_TXNAMT}</td>
										<td>${dto.f354_TID}</td>
										<td>${dto.f268_CHNAME}</td>
										<td>${dto.PAN}</td>
										<td>${dto.f270_ORN}</td>
										<%-- <td>${dto.STATUS}</td> --%>										
										<td>${dto.f011_AUTHIDRESP}</td>
										<td>${dto.respMessage}</td>
										<td>${dto.f023_RRN}</td>

									 </tr>
								</c:forEach>
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