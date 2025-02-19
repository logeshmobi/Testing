<%@page import="com.mobiversa.payment.controller.MerchantWebTransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

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
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("from").value;
var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
		
		
		var fromday = fromDate.getDate();
		var frommon = fromDate.getMonth() + 1;
		var fromyear = fromDate.getFullYear();
		
		

		var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
		
		/* var e1 = document.getElementById("datepicker1").value; */
		/* var splitstring = e.split('/')
		
		var Day = splitstring[0];
		var Month = splitstring[1];
		var Year = splitstring[2];
		
		var Pharsed = Year+Month+Day; */
		
		/* var e2 = document.getElementById("txnType").value; */
		if (e == null || e == '') {
			alert("Please Select date");
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = fromdateString;
			/* document.getElementById("dateval2").value = e1; */
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/transactionweb/searchBizappSettlementSummary?settlementDate='
					+ fromdateString ;
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
	
	function loadExpData() {
		//alert("test"+document.getElementById("txnType").value);
		var e = document.getElementById("from").value;
		
		var e2 = document.getElementById("export1").value;
		
		var fromDate = new Date(e);//.toDateString("yyyy-MM-dd");
		

		var fromday = fromDate.getDate();
		var frommon = fromDate.getMonth() + 1;
		var fromyear = fromDate.getFullYear();
		
		var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
		
		
		/* var e2 = document.getElementById("txnType").value; */
		if (e == null ||e == '' ) {
			alert("Please Select Date");
			//form.submit == false;
		} else {
			/* alert("inside else"); */
			document.getElementById("datevalex1").value = fromdateString;
			/* document.getElementById("txnType").value = e2; */
			/* document.location.href = '${pageContext.request.contextPath}/transaction/searchUMEzyway?date='
					+ e + '&date1=' + e1 + '&txnType=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/transactionweb/exportBizappTxnSummary?settledDate='
				+ fromdateString +'&export='+e2; 
			//form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

		}
	}

</script>
<body class="">
<div class="container-fluid">  
<div class="row">

  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>Bizapp Settlement Summary </strong></h3>
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
						<label for="from" style="margin:0px;"> Date </label><input type="hidden"
											name="date11" id="date11" <c:out value="${fromDate}"/>> <input type="text" id="from" name="date1"  class="validate datepicker"
						onchange="return loadDate(document.getElementById('from'),document.getElementById('date11'))">
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
										
										<div class="input-field col s12 m3 l3">

										<input type="hidden" name="date1" id="dateval1"> <input
											type="hidden" name="date2" id="dateval2">
										<button class="btn btn-primary blue-btn" type="button"
											onclick="return loadSelectData();">Search</button>
											<input type="hidden" name="dateex1" id="datevalex1"> <input
											type="hidden" name="dateex2" id="datevalex2">
										<a class="export-btn waves-effect waves-light btn btn-round indigo" 
										onclick="loadExpData()">Export</a>


									</div>
								</div>


							</div>

						</div>
					</div>
<style>
				.export_div .select-wrapper { width:65%;float:left;}
				.datepicker { width:80% !important;}				
				.select-wrapper .caret { fill: #005baa;}
							
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
<script>
	$('.pickadate-clear-buttons').pickadate({
    close: 'Close Picker', 
	formatSubmit: 'dd/mm/yyyy',
	
   
});
/* $('.datepicker').pickadate(); */

</script>	</div>

<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
						<div class="table-responsive m-b-20 m-t-15">
		   
		   
          
           	<table id="data_list_table" class="table table-striped table-bordered">

							<thead>
								<tr>
									<th>Settlement Date</th>
									<th>Merchant Name</th>
									<th>Bank Name</th>
									<th>Email</th>
									<th>Host Amount</th>
									<th>MDR Amount</th>
									<th>Deduction Amount</th>
									<th>TXN Amount</th>
									<th>Net Amount</th>
									<th>Status</th>
									<!-- <th>Trade Name</th> -->
									<th>Account No</th>
									<th>Reference</th>
									<!-- <th>Settle Type</th> -->
									
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${paginationBean.itemList}" var="dto">
									<tr>
									<fmt:parseDate value="${dto.settlementDate}" pattern="dd/MM/yyyy" var="myDate"/> 
										<td><fmt:formatDate pattern="dd/MM/yyyy" value="${myDate}" /></td>  
										<td>${dto.merchantName}</td>
										<td>${dto.bankName}</td>
										<td>${dto.email}</td>
										<td>${dto.hostMdrAmt}</td>
										<td>${dto.mobiMdrAmt}</td>
										<td>${dto.detectionAmt}</td>
										<td>${dto.grossAmt}</td>
										<td>${dto.netAmt}</td>
										<td>${dto.status}</td>
										<%-- <td>${dto.tradeName}</td> --%>
										<td>${dto.accountNo}</td>
										<td>${dto.reference}</td>
										<%-- <c:choose>
                                            <c:when test="${not empty dto.subSettleType && dto.subSettleType != 'Normal'}">
                                            <td>T+${dto.subSettleType}</td>
                                              </c:when>
                                            <c:otherwise>
                                            <td>Normal</td>
                                              </c:otherwise>
                                        </c:choose> --%>
										</tr>
								</c:forEach>
							</tbody>
						</table>
						</div>
		 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
					
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