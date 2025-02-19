<%@page import="com.mobiversa.payment.controller.AllTransactionController"%>
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
<body>

<!-- <script type="text/javascript">
jQuery(document).ready(function() {
	
	
$('#mid1').select2();
$('#export').select2();
$('#tid1').select2();

});
</script> -->



<style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
<script type="text/javascript">

function openNewWin(txnID){
	//alert(txnID);
	
	var url=window.location;
	//alert(url);
	var src = document.getElementById('popOutiFrame').src;
	 src=url+'transaction/details/'+txnID;
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
		//alert('search');
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("mid").value;
		var e3 = document.getElementById("tid").value;
		
		//alert("date picker date format:" + e+" "+e1+" "+e2+" "+e3);
		//alert("date picker date format1:" + e1);
		//loadDropDate11();
		//alert("test data");
		/* var eDate = new Date(e1);
		var sDate = new Date(e); */
		
		
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
		
		var day = fromdateString.split("/")[0];
        var month = fromdateString.split("/")[1];
        var year = fromdateString.split("/")[2];
        var day1 = todateString.split("/")[0];
        var month1 = todateString.split("/")[1];
        var year1 = todateString.split("/")[2];


       var startD =year+"/"+month+"/"+day;
       var endD =year1+"/"+month1+"/"+day1;

		
		var eDate = new Date(endD);
		var sDate = new Date(startD);
		
		if(e == '' && e1 == ''){
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
			//alert('test');
			 document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			//e= document.getElementById("date11").value;
			//e1=document.getElementById("date12").value; 
			//e3=document.getElementById("status1").value;
			//alert("from date:" + document.getElementById("dateval1").value);
			//alert("to date:" + document.getElementById("dateval2").value);
			 document.location.href = '${pageContext.request.contextPath}/transaction1/searchenquiryTransaction?fromDate=' + fromdateString
			+ '&toDate=' + todateString + '&mid=' + e2+ '&tid=' + e3; 

			//alert(document.location.href)
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
			/* document.location.href = '/transaction1/searchenquiryTransaction?date=' + e
					+ '&date1=' + e1 +'&currPage='+pnum; */
					 document.getElementById("dateval1").value = e;
					document.getElementById("dateval2").value = e1;
			 document.location.href = '${pageContext.request.contextPath}/transaction1/searchenquiryTransaction?date=' + e
			+ '&date1=' + e1 +'&currPage='+pnum; 
			
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
			
		/* loadDropDate11(); */
		/* loadDropDate13(); */
		var e = document.getElementById("from").value;
		var e1 = document.getElementById("to").value;
		var e2 = document.getElementById("mid").value;
		var e3 = document.getElementById("tid").value; 
		/* var e4 = document.getElementById("export1").value;   */
		var e4 = "EXCEL";  
		
		//loadDropDate11();
		
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
		
		var day = fromdateString.split("/")[0];
        var month = fromdateString.split("/")[1];
        var year = fromdateString.split("/")[2];
        var day1 = todateString.split("/")[0];
        var month1 = todateString.split("/")[1];
        var year1 = todateString.split("/")[2];


       var startD =year+"/"+month+"/"+day;
       var endD =year1+"/"+month1+"/"+day1;

		
		var eDate = new Date(endD);
		var sDate = new Date(startD);
		
		if(e == '' && e1 == ''){
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
			 //alert('test');
			 document.getElementById("dateval1").value = fromdateString;
				document.getElementById("dateval2").value = todateString;
			//e= document.getElementById("date11").value;
			//e1=document.getElementById("date12").value; 
			//e3=document.getElementById("status1").value;
			//alert("from date:" + document.getElementById("dateval1").value);
			//alert("to date:" + document.getElementById("dateval2").value);
			//alert("check export status:" + e3);
			document.location.href = '${pageContext.request.contextPath}/transaction1/export1?date=' + fromdateString
					+ '&date1=' + todateString + '&tid=' + e3+ '&mid=' + e2+ '&export=' + e4;
					
					/* document.location.href = '${pageContext.request.contextPath}/transaction1/export1?date=' + e
					+ '&date1=' + e1 + '&mid=' + e2+ '&tid=' + e3+ '&export=' + e4; */
			//alert(e);
			form.submit();
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
		}
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
           <h3 class="text-white">  <strong> Transaction Enquiry</strong></h3>
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
						<label for="from" style="margin:0px;"> From </label><input type="hidden"
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
									<select name="mid1"
										id="mid1"
										onchange="document.getElementById('mid').value=document.getElementById('mid1').value;">
										<!-- <optgroup label="MID" style="width:100%"> -->
										<option selected value=""><c:out value="MID" /></option>
										<c:forEach items="${midList}" var="mid">
											<option value="${mid}">${mid}</option>
										</c:forEach>
										</optgroup>
									</select>
									<label for="name">MID</label> <input type="hidden" name="mid" id="mid"
										value="${mid}"> 
								</div>
							

							<div class="input-field col s12 m3 l3">
									<select name="tid1"
										id="tid1"
										onchange="document.getElementById('tid').value=document.getElementById('tid1').value;">
										
										<option selected value=""><c:out value="TID" /></option>
										<c:forEach items="${tidList}" var="tid">
											<option <c:out value="${tid}"/>>${tid}</option>
										</c:forEach>
										</optgroup>
									</select>
									<label for="name">TID</label> <input type="hidden" name="tid" id="tid"
										<c:out value="${tid}"/>> 
								</div>
				
							
								<div class="row">
				<div class="input-field col s12 m3 l3" style="float:right !important;" >
					  <div class="button-class">
						<input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2">
						<button type="button" class="btn btn-primary blue-btn" onclick="loadSelectData()">search</button>
						<input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2"> 
									<button class="export-btn waves-effect waves-light btn btn-round indigo"  type="button"  onclick="return loaddata();">Export</button>
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
				<script>
/* 	$('.pickadate-clear-buttons').pickadate({
    close: 'Close Picker', 
	formatSubmit: 'MM/dd/yyyy',
	
   
}); */
	$('.datepicker').pickadate({
	    selectMonths: true, // Creates a dropdown to control month
	     format: 'yyyy-mm-dd' });

</script>
	
				 </div>
			
		</div></div>
	</div>
	
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
							<th>MID</th>
							<th>Business Name</th>
							<!-- <th>Transaction Type</th> -->
							<th>Card No</th>
							<th align="right" width="75px">Amount(RM)</th> <!-- align="right" width="75px" -->
							<th>Status</th>
							<th>Stan</th>
							
							<!-- <th>Batch No</th> -->
							<th>TID</th>
							<th>Approve Code</th>
							
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paginationBean.itemList}" var="dto">
							<tr>
								<td>${dto.transactionDate}</td>
								<td>${dto.transactionTime}</td>
								<td>${dto.mid}</td>
								<td>${dto.cardHolderName}</td>
								<%-- <td>${dto.mid}
								<a style="color: #4bae4f" href="${pageContext.request.contextPath}/transaction1/merchantdetails/${dto.mid}" target="_blank" title="Tid : ${dto.tid}">${dto.mid}</a></td> --%>
								<td>${dto.pan}</td>
								<td  align="right"  width="75px">${dto.amount}</td>
								
								<td>${dto.posConditionCode}</td>
								<td>${dto.stan}</td>
								<%-- <td>${dto.batchNo}</td> --%>
								<td>${dto.tid}</td>
								<td>${dto.aid}</td>
								
								
								
										<div class="form-group col-md-4" id="divviewer" style="display: none;">
					<div class="form-group">
					<div style="clear:both"> 
           <iframe id="popOutiFrame" frameborder="0" scrolling="no" width="800" height="600"></iframe>
           
        </div>
					
			</div></div>
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
		
		