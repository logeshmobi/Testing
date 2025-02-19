<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
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
    <title>Merchant Summary</title>   
    <!-- Script tag for Datepicker -->

	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
     <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
     
    
<!-- <script type="text/javascript">
jQuery(document).ready(function() {
	
$('#status').select2();
$('#export').select2();

});
</script> -->

<style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>

<script lang="JavaScript">

	function loadSelectData() {
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
		var f = document.getElementById("from").value.split("/");
		var f1 = document.getElementById("to").value.split("/");
		var e3 = document.getElementById("status").value;
		loadDropDate11();
		
		var sDate = new Date(f[2], f[1] - 1, f[0]);
		var eDate = new Date(f1[2], f1[1] - 1, f1[0]);
		
		if(e == '' && e1 == '' && e3 == ''){
			alert("Please select conditions.");
			    return false;
		}else if((e == '' && e1 != '') || (e != '' && e1 == '')){
			  alert("Please enter both Date's.");
			    return false;
		}else if(sDate > eDate)
		    {
		    alert("Please ensure that the End Date is greater than or equal to the Start Date.");
		    return false;
		 }else{
			 document.getElementById("dateval1").value = fromdateString;
			document.getElementById("dateval2").value = todateString;
			e3=document.getElementById("status1").value;
			 document.location.href = '${pageContext.request.contextPath}/device/notificationList?date=' + fromdateString
			+ '&date1=' + todateString + '&status=' + e3; 
			form.submit;// = true; 
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
		 }	

	}

	function loadDate(inputtxt,outputtxt)  
  	{  
  	 var field = inputtxt.value;
 	 outputtxt.value= field;
	}
	
	function loadDropDate11() 
	{
				var e = document.getElementById("status");
				var strUser = e.options[e.selectedIndex].value;
				document.getElementById("status1").value = strUser;

			} 
	
	//Script by Denis Gritcyuk: tspicker@yahoo.com
	//Submitted to JavaScript Kit (http://javascriptkit.com)
	//Visit http://javascriptkit.com for this script
</script>


  </head>
  <body>
<div class="container-fluid">    
  <div class="row">
  
 
  
  <div class="col s12">
      <div class="card blue-bg text-white">
        <div class="card-content">
          <div class="d-flex align-items-center">
           <h3 class="text-white">  <strong> Notification Summary  </strong></h3>
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
								 <input type="hidden" name="status1"
										id="status1" <c:out value="${status}"/>> <select name="status"
										 id="status"
										onchange="return loadDropDate11();">
										<option selected value="">CHOOSE</option>
										<option value="SUBMITTED">SUBMITTED</option>
										<option value="APPROVED">APPROVED</option>
										<option value="REJECTED">REJECTED</option>
										<option value="SENT">SENT</option>
									</select>
									<label>Status</label>
					</div>
					
					<div class="input-field col s12 m3 l3" style="float:right !important;">
					  <div class="button-class" >
										<input type="hidden" name="date1" id="dateval1"> <input
											type="hidden" name="date2" id="dateval2">
										<button class="btn btn-primary blue-btn" type="button"
											onclick="return loadSelectData();">Search</button>
									</div>
								</div>
					 </div> 
				</div>
				<style>
				
				.export-btn { float:left; width:35%;}
				.datepicker { width:80% !important;}				
				.select-wrapper .caret { fill: #005baa;}
				
				
				 .blue-btn { background-color:#005baa; color:#fff;border-radius:20px;}
				.button-class { float:right;}
				.float-right {float:right; }
				</style>
        </div>
      </div>
    </div>
   
	
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
									<th>Action Date</th>
									<th>Action Time</th>
									<th>Notification Title</th>
									<th>Status</th>
									<th>Action</th>
								</tr>
              </thead>
              <tbody>
              <c:forEach items="${paginationBean.itemList}" var="dto">
              	<tr>
										<td>${dto.actionDate}</td>
										<td>${dto.actionTime}</td>
										<td>${dto.msgTitle}</td>
										<td>${dto.status}</td>
											<td><a 
											href="${pageContext.request.contextPath}/device/details/${dto.id}?${_csrf.parameterName}=${_csrf.token}">
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
    	"bSort" : false
    } );
} );

</script>


</body> 
</html>