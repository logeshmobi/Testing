
<%@page import="com.mobiversa.payment.controller.TransactionController"%>
<%@page import="com.mobiversa.common.bo.ForSettlement"%>
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

      
</head>

<style>
	td, th { padding: 7px 8px; color:#707070;}
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
</style>
<body>

<!-- <script type="text/javascript">
jQuery(document).ready(function() {
	
	
$('#status').select2();
$('#export').select2();

});
</script> -->


	 <script lang="JavaScript">

	function loadSelectData() {
		//alert('test1');
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e3 = document.getElementById("status").value;
		
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

		
		//alert("date picker date format:" + e);
		//alert("date picker date format1:" + e1);
		loadDropDate11();
		//alert("test data");
		var eDate = new Date(todateString);
		var sDate = new Date(fromdateString);
		if (e == null || e == '' || e1 == null || e1 == ''|| e3 == null||e3 == '') {
			//alert("from date 1:" + e);
			alert("Please select conditions.");
			    return false;
		}else if((e == '' && e1 != '') || (e != '' && e1 == '')){
			  alert("Please enter both Date's.");
			    return false;
		}else if(sDate> eDate)
		    {
		    alert("Please ensure that the End Date is greater than or equal to the Start Date.");
		    return false;
		 }else{
			// alert('test');
			 document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			//e= document.getElementById("date11").value;
			//e1=document.getElementById("date12").value; 
			e3=document.getElementById("status1").value;
			//alert("from date:" + document.getElementById("dateval1").value);
			//alert("to date:" + document.getElementById("dateval2").value);
			 document.location.href = '${pageContext.request.contextPath}/transaction/allUmSearch1?date=' + fromdateString
			+ '&date1=' + todateString+ '&status=' + e3; 

			form.submit;// = true; 
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
		 }	

	}

	function loadDate(inputtxt,outputtxt)  
  	{  
  	 var field = inputtxt.value;
 	 //var field1 = outputtxt.value;
 	// alert(field+" : "+outputtxt.value);
 	 //document.getElementById("date11").value=field;
 	 outputtxt.value= field;
 	 //alert(outputtxt.value);
 	//alert(document.getElementById("date11").value);
	}
	
	function loadDropDate11() {
				//alert("strUser.value");
				var e = document.getElementById("status");

				var strUser = e.options[e.selectedIndex].value;
				document.getElementById("status1").value = strUser;
				//alert("data :" + strUser + " "+ document.getElementById("status1").value);

			} 
	
	
	function loadData(num){
		var pnum= num;
		//alert("page :"+pnum);
		/* var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		loadDropDate11();
		var e3 = document.getElementById("status").value;
		alert("page :"+e3); */
		//var e3 = e.options[e.selectedIndex].value;
		//var e3 = document.getElementById("status").value;
		//alert(document.getElementById("date11").value);
		//alert(document.getElementById("date12").value);
		e= document.getElementById("date11").value;
		e1=document.getElementById("date12").value;
		e3=document.getElementById("status1").value;
		//alert("page :"+e3+ e1+e);
		//alert('Teting :: '+pnum+' '+e + '  '+ e1+'  '+e3);
		
		/* if (e == null || e == '' ) {
			alert("Please Select FromDate");
			form.submit = false;
		} else if (e1 == null || e1 == ''){
			alert("Please Select ToDate");
			form.submit = false;
		} else */
		/*  if(e3 == null || e3 == ''){
				e3='F';
			}  */
		if((e == null || e == '') && (e1 == null || e1 == '') && (e3 == null || e3 == '')){
			//alert('both $$ ##');
			document.location.href = '${pageContext.request.contextPath}/transaction1/list/'+pnum;
			form.submit;
		}else {
			//alert("Test Else condition");
			//alert("else : "+e+" "+e1);
			/* document.location.href = '/transaction1/search1?date=' + e
					+ '&date1=' + e1 +'&currPage='+pnum; */
					 document.getElementById("dateval1").value = e;
					document.getElementById("dateval2").value = e1;
			 document.location.href = '${pageContext.request.contextPath}/transaction/allUmSearch1?date=' + e
			+ '&date1=' + e1 + '&status=' + e3 +'&currPage='+pnum; 
			
			//document.forms["myform"].submit();
			form.submit;// = true; 
			

		} 
	
	}
	
	
	function loadDropDate13() {
			//alert("loadDropDate13");
			var e = document.getElementById("export");

			var strUser = e.options[e.selectedIndex].value;
			document.getElementById("export1").value = strUser;
			//alert("data :" + strUser + " "+ document.getElementById("status1").value);

		}
		
	function loaddata() {
		loadDropDate11();
		loadDropDate13();
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("export1").value;
		var e3 = document.getElementById("status").value;
		//alert("e2" + e2);
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

		//loadDropDate11();
		var eDate = new Date(e1);
		var sDate = new Date(e);
		if(e == '' && e1 == '' && e3 == ''){
			alert("Please select conditions.");
			    return false;
		}else if((e == '' && e1 != '') || (e != '' && e1 == '')){
			  alert("Please enter both Date's.");
			    return false;
		}else if(sDate> eDate)
		    {
		    alert("Please ensure that the End Date is greater than or equal to the Start Date.");
		    return false;
		 }
		else{
			 //alert('test');
			 document.getElementById("dateval1").value = fromdateString;
				document.getElementById("dateval2").value = todateString;
			//e= document.getElementById("date11").value;
			//e1=document.getElementById("date12").value; 
			e3=document.getElementById("status1").value;
			//alert("from date:" + document.getElementById("dateval1").value);
			//alert("to date:" + document.getElementById("dateval2").value);
			//alert("check export status:" + e3);
			document.location.href = '${pageContext.request.contextPath}/transaction/allUmExport?date=' + fromdateString
					+ '&date1=' + todateString + '&export=' + e2+ '&status=' + e3;
			//alert(e);
			form.submit();
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
		}
	}
	
	
	
	
	
	function cal(ans,ID) {
		var status= ans;
		var id= ID;
		
	  document.location.href = '${pageContext.request.contextPath}/transaction1/update?txnID=' + id+ '&status=' + status;
      form.submit(); 

		return true;
		
	}
	
	

	//Script by Denis Gritcyuk: tspicker@yahoo.com
	//Submitted to JavaScript Kit (http://javascriptkit.com)
	//Visit http://javascriptkit.com for this script
</script> 


<body>


<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> All Transaction Summary</strong></h3>
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
					
					<div class="input-field col s12 m3 l3">
							
							<select name="status" id="status" onchange="return loadDropDate11();" >
					
							<option selected value="">CHOOSE</option>
							<option value="S">SETTLED</option>
							<option value="P">PENDING</option>
							<option value="C">CANCELLED</option>
							<option value ="A">NOT SETTLED</option>
							<option value ="NULL">FPX NOT SETTLED</option>
							<option value ="BPS">BOOST SETTLED</option>
							<option value ="BPA">BOOST NOT SETTLED</option>
							<option value ="BPC">BOOST CANCELLED</option>
							<option value ="GPS">GRAB SETTLED AND NOT SETTLED</option>
							<option value ="GRF">GRAB CANCELLED</option>
							
							
							
						</select>
						<label for="name">Status</label>		
						<input type="hidden" name="status1" id="status1" <c:out value="${status}"/>>
						
								
					</div>
						
		
			
			
					<div class="input-field col s12 m3 l3">
			  

						 <select name="export" 
							id="export" onchange="return loadDropDate13();" >
							<option selected value="">CHOOSE</option>
							<option value="PDF">PDF</option>
							<option value="EXCEL">EXCEL</option>
							
							
						</select>
						<label for="name">Export Type</label> 
						<input type="hidden" name="export1"
							id="export1" value="${export}">
						</div>
					</div>
					
				<div class="row">		
                      <div class="input-field col s12 m3 l3" style="float:right !important;">
                      <div class="button-class">
                      <input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2">
						<button type="button" class="btn btn-primary blue-btn" onclick="loadSelectData()">search</button> 
						<input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2">
						<a class="export-btn waves-effect waves-light btn btn-round indigo" onclick="loaddata()">Export</a>
						
						</div>		
				</div>
				</div>			
				
				 </div>
			
		</div></div></div>
		
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
											<!-- <th>Txn Type</th> -->
						<!-- dhinesh -->    <th>MID</th>
						<!-- dhinesh -->	<th>TID</th>
						                    <th>Amount(RM)</th>
						 <!-- dhinesh -->   <th>Name on Card</th>
						 <!-- dhinesh -->   <th>Card Number</th>
						                    <th>Reference</th>
						                    <th>AID Response</th>
						                    <th>RRN</th>
						                    <th>Stan</th>
						                    <th>Txn Type</th>
						                    <th>Status</th>
<!-- 						   dhinesh <th>Refund</th> -->
<!-- 						                    <th>Action</th> -->
<!-- 						dhinesh    <th>Void</th> -->
							
							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paginationBean.itemList}" var="dto">
							<tr>
								<td>${dto.date}</td>
								<td>${dto.time}</td>
								<td>${dto.mid}</td>	
								 <td>${dto.tid}</td>								
								<%-- <td>${dto.numOfSale}</td> --%>
								<td align="right"  width="75px">${dto.amount}</td>
								<td>${dto.numOfRefund}</td>
								<td>${dto.pan}</td>
								<td>${dto.invoiceId}</td>
								<td>${dto.aidResponse}</td>
								<td>${dto.rrn}</td>
								<td>${dto.stan}</td>
								<td>${dto.txnType}</td>
								<td>${dto.status}</td>
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
		
		