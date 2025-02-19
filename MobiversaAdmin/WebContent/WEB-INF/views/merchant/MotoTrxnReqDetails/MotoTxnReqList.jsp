<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recurring Summary</title>    
  
<script type="text/javascript">

function openNewWin(data){
	//alert(data);
	if(data==null || data==''){
		data="No Updates."
	}
	
	document.getElementById('statusSpan').style.display='block';
	document.getElementById('reasonDiv').innerHTML=data;
		
}


function HideDiv(){
	document.getElementById('statusSpan').style.display='none';
}
    </script>
 		
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
		var e2 = document.getElementById("type").value;
		
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
		//alert("test"+e + " "+e1+" "+e2);
		if (e == null || e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			//alert("test1212"+e + " "+e1);
			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			/* document.location.href = '${pageContext.request.contextPath}/transactionMoto/searchRecurringList?date='
					+ e + '&date1=' + e1 + '&type=' + e2; */
			document.location.href = '${pageContext.request.contextPath}/admmerchant/searchmotoTxnReqList?fromDate='
					+ fromdateString + '&toDate=' + todateString +'&status='+e2;
			//alert("test1212 "+document.getElementById("dateval1").value);
			//alert("test1212 "+document.getElementById("dateval2").value);
			form.submit;
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			document.getElementById("type").value = e2;

		}
	}
	
	
	
	function loadData(num) {
		var pnum = num;
		//alert("page :"+pnum);
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
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
			document.location.href = '${pageContext.request.contextPath}/admmerchant/motoTxnReqList/'
					+ pnum;
			form.submit;
		} else {
			//alert("else : "+e+" "+e1);
			document.location.href = '${pageContext.request.contextPath}/admmerchant/searchmotoTxnReqList?date='
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
	//alert("loaddata();");
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("export1").value;
		var e3 = document.getElementById("type").value;
		
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
		//var e3 = document.getElementById("type1").value;
		//alert("e2" + e2);
		//alert("e1" + e1);
		//alert("e" + e);

		if (e == null || e == '' || e1 == null || e1 == '') {
			//alert("picker :"+e + "  "+ e1);
			e = document.getElementById("dateval1").value;
			e1 = document.getElementById("dateval2").value;
			//alert("hidden : "+e + "  "+ e1);
			if (e == null || e1 == null || e == '' || e1 == '') {
				alert("Please select date(s)");
			}
		} else {

			document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;

			/*  e = document.getElementById("dateval").value; */
			//alert("test2: " + e + " " + e1);
			/* document.location.href = '${pageContext.request.contextPath}/transactionMoto/exportRecurringList?date='
					+ e + '&date1=' + e1 + '&export=' + e2 + '&type=' + e3; */
					document.location.href = '${pageContext.request.contextPath}/admmerchant/exportMotoReqList?date='
					+ fromdateString + '&date1=' + todateString + '&export=' + e2+'&status='+e3;
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
	
		 <style>
	td, th { padding: 7px 8px; color:#707070;}
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>

  </head>
  <body>
<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong>EZYMOTO Transaction Request Details</strong></h3>
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
									<input  id="from" type="text" class="validate datepicker" 
									onchange="return loadDate(document.getElementById('from'),document.getElementById('date11'));">
						<i class="material-icons prefix">date_range</i>
					</div>
					<div class="input-field col s12 m3 l3">
						
						<label for="to" style="margin:0px;">To</label>
						<label for="From_Date">To Date</label><input type="hidden"
									name="date11" id="date11" <c:out value="${toDate}"/>>
						<input id="to" type="text" class="datepicker" 
						onchange="loadDate(document.getElementById('to'),document.getElementById('date11'))">
						<i class="material-icons prefix">date_range</i>
					</div>
					<div class="input-field col s12 m3 l3">
					

								
								<select name="type" id="type" onchange="loadDropDatetype()">
									<option selected value="">Choose</option>
									<option value="S">Success</option>
									<option value="F">Failed</option>
									<option value="E">Expired</option>
									<option value="R">Rejected by the Customer</option>
									<option value="P">Pending</option>
									<option value="I">Incomplete </option>
									
								</select>
								<label for="name">Status</label> <input
									type="hidden" name="type1" id="type1" <c:out value="${type}"/>>
							
					</div>
					<div class="input-field col s12 m3 l3 export_div">
						<select name="export" id="export" onchange="loadDropDate13()">
									<option selected value="">Choose</option>
									<option value="PDF">PDF</option>
									<option value="EXCEL">EXCEL</option>
						</select>
						<label for="name">Export Type</label>
						<input type="hidden" name="export1" id="export1" <c:out value="${status}"/>>
						
						
						

					</div>
					
						<div class="input-field col 112">
					  <div class="button-class" style="float:right !important;">
					  <input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2">
						<button type="button" class="btn btn-primary blue-btn" onclick="loadSelectData()">search</button> 
					  <input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2">
						<a class="export-btn waves-effect waves-light btn btn-round indigo" onclick="loaddata()">Export</a>
						
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
              <thead>
             <tr>
									<th>Date</th> 
									<th>Time</th> 
									<th>Business Name</th>
									<th>TID</th>
									<th>Amount</th>
									<th>Expired Date</th>
									
									<th>Status</th>
									<!-- <th>Edit</th> -->
									
									
								</tr>

              </thead>
              <tbody>
              <c:forEach items="${paginationBean.itemList}" var="motoReq">
               <tr>
										<td style="text-align:left;">${motoReq.reqDate }</td>
										<td style="text-align:left;">${motoReq.expectedDate }</td>
										<td style="text-align:left;">${motoReq.name }</td>
										<td style="text-align:right;">${motoReq.tid }</td>
										<td style="text-align:right;">${motoReq.amount}</td>
										<td style="text-align:left;">${motoReq.expDate}</td>
										<td style="text-align:left;">${motoReq.status }</td>
										
										<%-- <td align="center"><a class="fa fa-pencil "aria-hidden="true" title="${ezyrec.id}"
											href="${pageContext.request.contextPath}/admmerchant/editRecurring/${ezyrec.id}"></a></td>
									 --%>
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