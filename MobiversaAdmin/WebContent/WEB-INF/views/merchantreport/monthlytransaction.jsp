<%@page import="com.mobiversa.payment.controller.UnuserMerchantController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
	
	

 <style>
	td, th { padding: 7px 8px;color:#707070; }
	thead th {  border-bottom: 1px solid #ffa500; color:#4377a2; }
	</style>
<!-- 
<script type="text/javascript">
jQuery(document).ready(function() {
	
	
$('#month').select2();
$('#year').select2();
$('#export').select2();


});
</script> -->


	<script lang="JavaScript">
/* var date;*/

	
	function loadSelectData() {
		//alert("test");
		var e = document.getElementById("month1").value;
		var e1 = document.getElementById("year1").value;
		if (e == null || e == '' ) {
			alert("Please Select Month");
			//form.submit == false;
		}else if (e1 == null || e1 == '' ) {
			alert("Please Select Year");
			//form.submit == false;
		} else {
			//alert("test search data:");
			//alert("chck data1:" +e);
			//alert("check data2:" + e1);
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			document.location.href = '${pageContext.request.contextPath}/monthlytxn/search?month=' + e+'&year='+ e1;
			form.submit;
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
		}
		//alert(e + " " + e1);
	}

	


	function loadDropDate11() {
			//alert("strUser.value");
			var e = document.getElementById("month");

			var strUser = e.options[e.selectedIndex].value;
			document.getElementById("month1").value = strUser;
			//alert("data :" + strUser + " "+ document.getElementById("status1").value);

		}

	function loadDropDate12() {
			//alert("strUser.value");
			var e = document.getElementById("year");

			var strUser = e.options[e.selectedIndex].value;
			document.getElementById("year1").value = strUser;
			//alert("data :" + strUser + " "+ document.getElementById("status1").value);

		}


	function loadDropDate13() {
			//alert("loadDropDate13");
			var e = document.getElementById("export");

			var strUser = e.options[e.selectedIndex].value;
			document.getElementById("export1").value = strUser;
			//alert("data :" + strUser + " "+ document.getElementById("status1").value);

		}
		
		
		
	function loaddata() {
		var e = document.getElementById("month1").value;
		var e1 = document.getElementById("year1").value;
		var e2 = document.getElementById("export1").value;
		if (e == null || e == '' ) {
			alert("Please Select Month");
			//form.submit == false;
		}else if (e1 == null || e1 == '' ) {
			alert("Please Select Year");
			//form.submit == false;
		} else {
			// alert('test');
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			document.location.href = '${pageContext.request.contextPath}/monthlytxn/export?month=' + e+'&year='+ e1
			+ '&export=' + e2;
			form.submit;
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			//alert(e);
			//form.submit();
			
		}
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
           <h3 class="text-white">  <strong>Monthly Transaction Summary</strong></h3>
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

						 <select name="month" class="form-control"
							id="month" onchange="loadDropDate11(document.getElementById('month'),document.getElementById('month1'))">  

<!-- onchange="loadDropDate11()" class="form-control">-->
							<option selected value="">Choose</option>
							<option value="1">JAN</option>
							<option value="2">FEB</option>
							<option value="3">MAR</option>
							<option value="4">APR</option>
							<option value="5">MAY</option>
							<option value="6">JUN</option>
							<option value="7">JUL</option>
							<option value="8">AUG</option>
							<option value="9">SEP</option>
							<option value="10">OCT</option>
							<option value="11">NOV</option>
							<option value="12">DEC</option>
						</select>
						<label for="name">Month</label> <input type="hidden" name="month1"
							id="month1" <c:out value="${status}"/>>

					</div>
				
				
				
					<div class="input-field col s12 m3 l3">

						 <select name="year" class="form-control"
							id="year" onchange="loadDropDate12(document.getElementById('year'),document.getElementById('year1'))   ">


							<option selected value="">Choose</option>
							<option value="2015">2015</option>
							<option value="2016">2016</option>
							<option value="2017">2017</option>
							<option value="2018">2018</option>
							
						</select>
						<label for="name">Year</label> <input type="hidden" name="year1"
							id="year1" <c:out value="${status}"/>>

					</div>
				
				
				<div class="input-field col s12 m3 l3">

						<select name="export"
							id="export" onchange="loadDropDate13()" class="form-control">


							<option selected value="">Choose</option>
							<option value="PDF">PDF</option>
							<option value="EXCEL">EXCEL</option>
							
						</select>
						<label for="name">Export Type</label> <input type="hidden" name="export1"
							id="export1" <c:out value="${status}"/>> 
						</div>
						
		
							
										
							
							<div class="input-field col s12 m3 l3" style="float:right;">	
							  <div class="button-class">
								<input type="hidden" name="date1" id ="dateval1">
						<input type="hidden" name="date2" id ="dateval2">	
						<button class="btn btn-primary blue-btn" type="button" onclick="loadSelectData()">Search</button>
						<button class="btn btn-primary blue-btn"  type="button"  onclick="loaddata()">Export</button>
						</div>				
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
						</div></div>
						
				<div class="row">
					  <div class="col s12">
					      <div class="card border-radius">
					        <div class="card-content padding-card">


					          <div class="table-responsive m-b-20 m-t-15">
					            <table id="data_list_table" class="table table-striped table-bordered">
			<thead>
				
				<tr>
					<th>Activate Date</th>
					<th>Merchant Name</th>
					<th>TID</th>
					<th>MID</th> 
					<th>Amount</th>
					<th>No of Transaction</th>	
					<!-- <th>AgentName</th> -->
					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${paginationBean.itemList}" var="dto">
					<tr>
						<td>${dto.date} </td> 
						<td>${dto.merchantName} </td>
						 <td>${dto.tid}</td>
						<td>${dto.mid} </td>
						<td>${dto.amount} </td> 
						<td>${dto.noofTxn} </td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
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
			
			