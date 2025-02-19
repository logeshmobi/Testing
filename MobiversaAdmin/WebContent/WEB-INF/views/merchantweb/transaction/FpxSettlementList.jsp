<%@page import="com.mobiversa.payment.controller.MerchantWebTransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<html lang="en-US">
<head>
<meta charset="UTF-8">
<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">


</head>

<style>
	td, th { padding: 7px 8px; color:#707070;}
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
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

		if (e == null ||e == '' || e1 == null || e1 == '') {
			alert("Please Select Date");
			//form.submit == false;
		} else {
			/* document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			document.getElementById("txnType").value = e2; */
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			
			document.location.href = '${pageContext.request.contextPath}/transactionweb/searchFpxSettlementList?fromDate='
					+ fromdateString +'&toDate=' + todateString;
			//form.submit;
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
	
	function loadDropDate13() {
		//alert("loadDropDate13");
		var e = document.getElementById("export");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("export1").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

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

		
		/* var e2 = document.getElementById("txnType").value; */
		if (e == null ||e == '' || e1 == null || e1 == '') {
			alert("Please Select Date");
			//form.submit == false;
		} else {
			/* alert("inside else"); */
			document.getElementById("datevalex1").value = fromdateString;
			document.getElementById("datevalex2").value = todateString;
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/transactionweb/exportFpxSettlementList?fromDate='
				+ fromdateString +'&toDate=' + todateString +'&export='+e2; 
			//form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

		}
	}

</script>

<body>
<div class="container-fluid">    
  <div class="row">
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>FPX Settlement Summary</strong></h3>
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
						<label for="from" style="margin:0px;">From</label>
						<input type="hidden"
					name="date11" id="date11" <c:out value="${fromDate}"/>>
						<input type="text" id="from" name="date1"  class="validate datepicker"
						onchange="return loadDate(document.getElementById('from'),document.getElementById('date11'))">
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
						<select name="export" id="export" onchange="loadDropDate13()"
									class="form-control" style="width:100%">
									<option selected value="">Choose</option>
									<option value="PDF">PDF</option>
									<option value="EXCEL">EXCEL</option>
						</select>
						<label for="name">Export Type</label>
						<input type="hidden" name="export1" id="export1" <c:out value="${status}"/>>
						
						

					</div>
					
					<div class="input-field col s12 m3 l3" style="float:right;">
					  <div class="button-class">
					  
					  <input type="hidden" name="date1" id="dateval1"> <input
											type="hidden" name="date2" id="dateval2">
						<button type="button" class="btn btn-primary blue-btn" onclick="loadSelectData();">search</button> 
						<input type="hidden" name="dateex1" id="datevalex1"> <input
											type="hidden" name="dateex2" id="datevalex2">
						<a class="export-btn waves-effect waves-light btn btn-round indigo" onclick="loadExpData()">Export</a>
						
						
					 </div> 
					 </div> 
				</div>
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
        </div>
      </div>
    </div>
    </div>
    
    <script>
	$('.pickadate-clear-buttons').pickadate({
    close: 'Close Picker', 
	formatSubmit: 'MM/dd/yyyy',
   
});
$('.datepicker').pickadate();

</script>

	<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
  
           
		   <div class="table-responsive m-b-20 m-t-15">
            <table id="data_list_table" class="table table-striped table-bordered">
            <thead align="center">

							<tr>
							<th>Transaction Date</th>
							<th>Transaction Time</th>	
							<th>MID</th>										
							<th>Transaction Amount</th>	
							<th>Net Amount</th>	
							<th>MDR Amount</th>	
							
							<th>Reference</th>
							<th>Sub Merchant MID</th>
							
							
							
												
							
							
							
							
							<!-- <th>Action</th>  -->

						</tr>
					</thead>
					
					<tbody id="prodReportTable">
						<c:forEach items="${paginationBean.itemList}" var="dto">
							<tr>
							<%-- <fmt:parseDate value="${dto.settlementDate}" pattern="dd/MM/yyyy" var="myDate"/> --%>

									<%-- <td><fmt:formatDate pattern="dd-MMM-yyyy" value="${myDate}" /></td>  --%>	
									<td>${dto.txDate}</td>								
									<td>${dto.txTime}</td>
									<td>${dto.mid}</td>
									<%-- <td>${dto.makerName}</td> --%>
									<td style="text-align:right;"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${dto.txnAmount}"></fmt:formatNumber></td>
									
									<td style="text-align:right;"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${dto.payableAmt}"></fmt:formatNumber></td>
									<td style="text-align:right;"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${dto.mdrAmt}"></fmt:formatNumber></td>
									
									
									<td>${dto.sellerOrderNo}</td>
									<td>${dto.subMerchantMID}</td>
									
									
									
							</tr>
						</c:forEach>
					</tbody>
			</table>
			</div></div></div></div></div>

	</div>
	
<!--  <script type="text/javascript">
	jQuery(document).ready(function() {
		$('#merchantName').select2();
	/* 	$('#export').select2(); */
	});
</script>
 -->
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

