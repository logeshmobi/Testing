<%@page import="com.mobiversa.common.bo.Merchant"%>
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
	
	<!-- Script tag for Datepicker -->

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
     <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
	
	
 
</head>

<!-- <script type="text/javascript">
jQuery(document).ready(function() {
	
	
$('#export').select2();
$('#type').select2();
});
</script> -->
<script lang="JavaScript">
function loadSelectData() {
	//alert("test");
	var e = document.getElementById("from").value;
	var e1 = document.getElementById("to").value;
	//alert('Date :'+e+' '+e1);
	/* var ee = new Date(e);//.toDateString("yyyy-MM-dd");
	var ee1 = new Date(e1);//.toDateString("yyyy-MM-dd");				
	var d = ee.getDate();
	var m = ee.getMonth() + 1;
	var y = ee.getFullYear();
	var d1 = ee1.getDate();
	var m1 = ee1.getMonth() + 1;
	var y1 = ee1.getFullYear();
	var dateString = (d <= 9 ? '0' + d : d) + '/' + (m <= 9 ? '0' + m : m) + '/' + y;
	var dateString1 = (d1 <= 9 ? '0' + d1 : d1) + '/' + (m1 <= 9 ? '0' + m1 : m1) + '/' + y1; */
	
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
	//var dateString1 = y+'/'+ (m <= 9 ? '0' + m : m)+'/'+(d <= 9 ? '0' + d : d);
	
	

	
	//alert('Date :'+' : '+dateString+'  : '+dateString1);
	//var e2 = document.getElementById("type1").value;
	//alert("test"+e + " "+e1);
	if (e == null || e == '' || e1 == null || e1 == '') {
		alert("Please Select date(s)");
		//form.submit == false;
	} else {
		//alert("test1212"+e + " "+e1);
		document.getElementById("dateval1").value = e;
		document.getElementById("dateval2").value = e1;
		/* document.location.href = '${pageContext.request.contextPath}/transactionMoto/searchRecurringList?date='
				+ e + '&date1=' + e1 + '&type=' + e2; */
		document.location.href = '${pageContext.request.contextPath}/transactionMoto/searchRecurringList?date='
				+ fromdateString + '&date1=' + todateString;
		//alert("test1212 "+document.getElementById("dateval1").value);
		//alert("test1212 "+document.getElementById("dateval2").value);
		form.submit;
		document.getElementById("dateval1").value = e;
		document.getElementById("dateval2").value = e1;

	}
}

	
	function loadData(num) {
		var pnum = num;
		//alert("page :"+pnum);
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		//var e2 = document.getElementById("type1").value;
		//alert(document.getElementById("date11").value);
		//alert(document.getElementById("date12").value);
		e = document.getElementById("date11").value;
		e1 = document.getElementById("date12").value;

		//alert(e + '  '+ e1);

		/* if (e == null || e == '' ) {
			alert("Please Select FromDate");
			form.submit = false;
		} else if (e1 == null || e1 == ''){
			alert("Please Select ToDate");
			form.submit = false;
		} else */if ((e == null || e == '') && (e1 == null || e1 == '')) {
			//alert('both $$ ##');
			document.location.href = '${pageContext.request.contextPath}/transactionMoto/recurringList/'
					+ pnum;
			form.submit;
		} else {
			//alert("else : "+e+" "+e1);
			document.location.href = '${pageContext.request.contextPath}/transactionMoto/searchRecurringList?date='
					+ e + '&date1=' + e1+ '&type=' + e2 + '&currPage=' + pnum;

			//document.forms["myform"].submit();
			form.submit;// = true; 

		}

	}
	
	
	//export changes start
	function loadDropDate13() {
		//alert("loadDropDate13");
		var e = document.getElementById("export");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("export1").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}
function loadDropDatetype() {
		//alert("loadDropDate13");
		var e = document.getElementById("type");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("type1").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}
	
function loaddata() {

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

	if (e == null || e1 == null || e == '' || e1 == '') {
		//alert("picker :"+e + "  "+ e1);
		e = document.getElementById("dateval1").value;
		e1 = document.getElementById("dateval2").value;
		//alert("hidden : "+e + "  "+ e1);
		if (e == null || e1 == null || e == '' || e1 == '') {
			alert("Please select date(s)");
		}
	} else {

		document.getElementById("dateval1").value = fromdateString
		document.getElementById("dateval2").value = todateString;

		/*  e = document.getElementById("dateval").value; */
		//alert("test2: " + e + " " + e1);
		/* document.location.href = '${pageContext.request.contextPath}/transactionMoto/exportRecurringList?date='
				+ e + '&date1=' + e1 + '&export=' + e2 + '&type=' + e3; */
				document.location.href = '${pageContext.request.contextPath}/transactionMoto/exportRecurringList?date='
				+ fromdateString + '&date1=' + todateString + '&export=' + e2;
		//alert(e);
		form.submit;

	}
}
	function loadDate(inputtxt,outputtxt)  
  	{  
  	 var field = inputtxt.value;
 	 //var field1 = outputtxt.value;
 	// alert(field+" : "+outputtxt.value);
 	 //document.getElementById("date11").value=field;
 	 outputtxt.value= field;
 	// alert(outputtxt.value);
 	// alert(document.getElementById("date11").value);
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
           <h3 class="text-white">  <strong> Recurring Summary  </strong></h3>
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
						<label for="from" style="margin:0px;"> From </label>
						<input type="hidden"
									name="date11" id="date11" <c:out value="${fromDate}"/>> 
						<input type="text" id="from" name="date1"  class="validate datepicker"
						onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
						<i class="material-icons prefix">date_range</i>
					</div>
					<div class="input-field col s12 m3 l3">
						
						<label for="to" style="margin:0px;">To</label>
						<input type="hidden"
									name="date11" id="date11" <c:out value="${toDate}"/>>
						<input id="to" type="text" class="datepicker" 
						onchange="loadDate(document.getElementById('to'),document.getElementById('date11'))">
						<i class="material-icons prefix">date_range</i>
					</div>
					
					<div class="input-field col s12 m3 l3 export_div">
						<select name="export" id="export" onchange="loadDropDate13()"
									style="width:100%">
									<option selected value="">Choose</option>
									<option value="PDF">PDF</option>
									<option value="EXCEL">EXCEL</option>
						</select>
						<label for="name">Export Type</label>
						<input type="hidden" name="export1" id="export1" <c:out value="${status}"/>>
						
						

					</div>
					
					<div class="input-field col s12 m3 l3">
					  <div class="button-class"  style="float:left;">
					  <input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2">
						<a class="export-btn waves-effect waves-light btn btn-round indigo" onclick="loaddata()">Export</a>
						<input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2">
						<button type="button" class="btn btn-primary blue-btn" onclick="loadSelectData()">search</button> 
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
	
	jQuery(function() {		
		var date = new Date();
		var currentMonth = date.getMonth();
		var currentDate = date.getDate();
		var currentYear = date.getFullYear();
		
		$('.datepicker').datepicker({
			minDate: new Date(currentYear, currentMonth-3, currentDate),
			maxDate: new Date(currentYear, currentMonth, currentDate+1)
		});
	});

	$('.pickadate-clear-buttons').pickadate({
    close: 'Close Picker', 
	formatSubmit: 'dd/mm/yyyy',
	
   
});


</script>
	
		<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
  
           
		   <div class="table-responsive m-b-20 m-t-15"> 
           	<table id="data_list_table" class="table table-striped table-bordered">

							<thead>
								<tr>

									
									<!-- <th>Merchant Name</th>
									<th>TID</th>-->
									<!-- <th>MID</th> --> 
									<th>TID</th>
									<th>Customer Name</th>
									<th>Amount</th>
									<th>Card No</th>
									<!-- <th>Expiry Date</th> -->
									<!-- <th>CardHolder Name</th> -->
									
									<th>Frequency</th><!-- <th>Period</th> -->
									<th>No Of Payments</th><!-- <th>Installment Count</th> -->
									
									<!-- <th>LastTrig Date</th> -->
									<th>NextTrig Date</th>
									<th>End Date</th>
									<th>Status</th>
									<th>Edit</th>
									
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${paginationBean.itemList}" var="merc">
									<tr>

										<%-- <td><fmt:formatDate value="${merc.activateDate}"
												pattern="dd-MMM-yyyy" /></td>
										<td>${merc.businessName}</td> --%>
										<td>${merc.tid }</td>
										<td>${merc.custName}</td>
										<td  align="right">${merc.amount}</td>
										<td  align="center">${merc.maskedPan }</td>

										<%-- <td>${merc.expDate}</td> --%>
										
										<td align="center">${merc.period}</td>
										<td align="center">${merc.installmentCount}</td>
										
										<%-- <td>${merc.lastTriggerDate}</td> --%>
										<td>${merc.nextTriggerDate}</td>
										<td>${merc.endDate}</td>
										<td>${merc.status}</td>
										<td align="center"><a 
											href="${pageContext.request.contextPath}/transactionMoto/editRecurringStatus/${merc.id}">
											<i class="material-icons">create</i></a></td>
	
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
</div>
	 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
	</div>
	
	<script>
$(document).ready(function() {
   // $('#data_list_table').DataTable();
} );

$(document).ready(function() {
    $('#data_list_table').DataTable( {
        columnDefs: [
            {
                targets: [ 0, 1, 2 ],
                className: 'mdl-data-table__cell--non-numeric'
            }
        ]
    } );
} );

</script>
	
</body>
</html>
