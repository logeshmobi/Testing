<%@page import="com.mobiversa.payment.controller.UnuserMerchantController"%>
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
<!-- <link href="/resourcesNew/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
 <script src="/resourcesNew/js/jQuery-2.1.4.min.js" type="text/javascript"></script>
<link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet"
	type="text/css" /> -->
	<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
	 <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 
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
			 //alert('test');
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			document.location.href = '${pageContext.request.contextPath}/monthlytxn/export?month=' + e+'&year='+ e1
			+ '&export=' + e2;
			form.submit;
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			//alert(e);
			form.submit();
			
		}
	}
	
	
	
	//Script by Denis Gritcyuk: tspicker@yahoo.com
	//Submitted to JavaScript Kit (http://javascriptkit.com)
	//Visit http://javascriptkit.com for this script
</script>
<body class="">
	<div class="pageWrap">

		<!-- Page content -->
		
		
		<div class="pageContent ">
			<div class="container">
				<div class="boxHeader pageBoxHeader clearfix">
					<div class="pull-left">
						<h1 class="pageTitle">
							<a href="#" title="#"> Monthly Transaction Summary </a>
						</h1>

					
	</div>
</div></div>
</div>
</div>
	<div class="box box-without-bottom-padding">

				<div class="row">
				
				
				<%-- <div class="col-xs-12 col-sm-4">
								<div class="form-group">
						<label for="From_Date">From Date</label><input
							type="hidden" name="date11" id="date11" value="${date}">
						<input type="text"  class="form-control" id="datepicker" name="date" 
							placeholder="dd/mm/yyyy" onchange="loadDate(document.form1.date,document.form1.date11)">
							
							</div>
							</div> --%>
							
				<div class="col-xs-12 col-sm-4 i">
					<div class="form-group">

						<label>Month</label> <input type="hidden" name="month1"
							id="month1" <c:out value="${status}"/>> <select name="month"
							id="month" onchange="loadDropDate11()" class="js-select">


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


					</div>
				</div>
				
				<div class="col-xs-12 col-sm-4 i">
					<div class="form-group">

						<label>Year</label> <input type="hidden" name="year1"
							id="year1" <c:out value="${status}"/>> <select name="year"
							id="year" onchange="loadDropDate12()" class="js-select">


							<option selected value="">Choose</option>
							<option value="2015">2015</option>
							<option value="2016">2016</option>
							<option value="2017">2017</option>
							
						</select>


					</div>
				</div>
				
				<div class="col-xs-12 col-sm-4 i">
					<div class="form-group">

						<label>Export Type</label> <input type="hidden" name="export1"
							id="export1" <c:out value="${status}"/>> <select name="export"
							id="export" onchange="loadDropDate13()" class="js-select">


							<option selected value="">Choose</option>
							<option value="PDF">PDF</option>
							<option value="EXCEL">EXCEL</option>
							
						</select>
						</div>
						</div>
		
							
							
							<!-- </div>
							<div class="row"> -->
				
				
							
							<div class="col-xs-12 col-sm-4 i">
								<div class="form-group">	
						<button class="btn btn-primary" type="button" onclick="loadSelectData()">Search</button>
						<button class="btn btn-primary"  type="button"  onclick="loaddata()">Export</button>
						<input type="hidden" name="date1" id ="dateval1">
						<input type="hidden" name="date2" id ="dateval2">					
						</div>
						</div>
						
						</div>	
						</div>
					</body>	
					
					
					
					
					
					
					
					
					
					
					
					
		<div class="box box-without-bottom-padding">
					<div class="tableWrap dataTable table-responsive js-select">
						<table class="table js-datatable">
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
			
			