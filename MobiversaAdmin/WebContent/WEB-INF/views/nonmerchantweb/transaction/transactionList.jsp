<%@page	import="com.mobiversa.payment.controller.NonMerchantWebTransactionController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


<html>
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
	
<!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script> -->
  
 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
<script type="text/javascript">
jQuery(document).ready(function() {
	
	
$('#tid').select2();

$('#devId').select2();

$('#status').select2();
$('#export').select2();

});
</script>

<script lang="JavaScript">
		function loadSelectData() {
			//alert("test"); 
			/* DateCheck(); */
			var e = document.getElementById("datepicker").value;
			var e1 = document.getElementById("datepicker1").value;
			//var e2= document.getElementById("tid1").value;
			//var e3 = document.getElementById("devid1").value;
			//var e4 =document.getElementById("status1").value; 
			
			//alert("e"+ e);
			//alert("e1"+ e1);
			 var eDate = new Date(e1);
			  var sDate = new Date(e);
			
		 
			
			if(e == '' && e1 == '' && e2 == '' && e3 == '' && e4 == ''){
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
		    else
		    {
			  /* if(e!= '' && e!= '' && sDate> eDate)
			    {
			    alert("Please ensure that the End Date is greater than or equal to the Start Date.");
			    return false;
			    } */
			/* var e2 = document.getElementById("tid1").value;
			 vare3=document.getElementById("devid1").value; 
			var e4 = document.getElementById("status1").value;
 */
	    	document.getElementById("date11").value = e;
			document.getElementById("date12").value = e1;

			/* document.getElementById("tid1").value = e2;
			 document.getElementById("devid1").value=e3;  
			document.getElementById("status1").value = e4; */

			/* document.location.href = '${pageContext.request.contextPath}/transactionweb/search?fromDate=' + e
					+ '&toDate=' + e1 + '&tid=' + e2 + '&devId=' + e3 + '&status=' + e4;
			form.submit; */
			
			document.getElementById("date11").value = e;
			document.getElementById("date12").value = e1;
			//alert("from date:"+ e);
			//alert("todate:"+ e1);
			document.form1.action="${pageContext.request.contextPath}/transaction/nonmerchantweb/search";
			//form.submit;
			document.form1.submit();
}
		}
		//alert(e + " " + e1);

		function loadDate(inputtxt, outputtxt) {
			var field = inputtxt.value;
			//var field1 = outputtxt.value;
			//alert(field+" : "+outputtxt.value);
			//document.getElementById("date11").value=field;
			outputtxt.value = field;
			//alert(outputtxt.value);
			// alert(document.getElementById("date11").value);
		}

		function loadDropDate() {
			// alert("strUser.value"); 
			var e = document.getElementById("tid");

			var strUser = e.options[e.selectedIndex].value;
			document.getElementById("tid1").value = strUser;
			//alert("data :" + strUser + " "+ document.getElementById("tid1").value);

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

		function loadDropDate12()
		{
			//alert("strUser.value");
			var e = document.getElementById("devId");
			
			var strUser = e.options[e.selectedIndex].value;
			document.getElementById("devid1").value = strUser;
			//alert("data :"+strUser+" "+document.getElementById("devid1").value);
			
		} 
		
		
		
		function loadData(num){
		var pnum= num;
		//alert("page :"+pnum);
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		/* var e2 = document.getElementById("tid1").value;
		var e3=document.getElementById("devid1").value;
		var e4= document.getElementById("status1").value;
		 */
		e= document.getElementById("date11").value;
		e1=document.getElementById("date12").value;
		/* e2 = document.getElementById("tid1").value;
		
		 e3=document.getElementById("devid1").value;
		 e4 = document.getElementById("status1").value; */
		//alert(e + '  '+ e1);
		
		/* if (e == null || e == '' ) {
			alert("Please Select FromDate");
			form.submit = false;
		} else if (e1 == null || e1 == ''){
			alert("Please Select ToDate");
			form.submit = false;
		} else */ if((e == null || e == '') && (e1 == null || e1 == '')
		 && (e2 == null || e2 == '') && (e3 == null || e3 == '') && (e4 == null || e4 == '')){
			//alert('both $$ ##');
			document.location.href = '${pageContext.request.contextPath}/list/'+pnum;
			form.submit;
		}else {
			//alert("else : "+e+" "+e1);
			document.location.href = '${pageContext.request.contextPath}/transaction/nonmerchantweb/search?fromDate=' + e
					+ '&toDate=' + e1+ '&currPage='+pnum;
			
			//document.forms["myform"].submit();
			form.submit;// = true; 
			

			}

		}
		
		function DateCheck()
		{
		  var StartDate= document.getElementById('datepicker').value;
		  var EndDate= document.getElementById('datepicker1').value;
		  var eDate = new Date(EndDate);
		  var sDate = new Date(StartDate);
		  if(StartDate!= '' && StartDate!= '' && sDate> eDate)
		    {
		    alert("Please ensure that the End Date is greater than or equal to the Start Date.");
		    return false;
		    }
		}
		
		
		
		function loaddata() {
		//alert("test data");
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		/* var e2= document.getElementById("tid1").value;
		var e3= document.getElementById("devid1").value;
		var e4= document.getElementById("status1").value;
		var e5= document.getElementById("export1").value; */
		//alert("e"+e);
		//alert("e1"+e1);
		//alert("e2"+e2);
		//alert("e3"+e3);
		//alert("e4"+e4);
		//alert("e5"+e5);
		
	/* 	if (e == null || e1 == null || e == '' || e1 == '') {
			//alert("picker :"+e + "  "+ e1);
			e = document.getElementById("dateval1").value;
			e1 = document.getElementById("dateval2").value;
			//alert("hidden : "+e + "  "+ e1); */
			if (e == null || e1 == null || e == '' || e1 == '') {
				alert("Please select date(s)");
			//}
		} else {

			/* document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			document.getElementById("dateval3").value = e2;
			document.getElementById("dateval4").value = e3;
			document.getElementById("dateval5").value = e4; */
			

			/*  e = document.getElementById("dateval").value; */
			//alert("test2: " + e + " " + e1);
			/* document.location.href = '${pageContext.request.contextPath}/transactionweb/export?fromDate=' + e
					+ '&toDate=' + e1 + '&tid=' + e2 + '&devId=' + e3 + '&status=' + e4 +'&export='+e5; */
			//alert(e);
					document.form1.action = "${pageContext.request.contextPath}/transaction/nonmerchantweb/export";
			//form.submit();
					document.form1.submit();
			
		}
	}
	
	
	
	
	function loaddata1() {
		//alert("test data");
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		/* var e2= document.getElementById("tid1").value;
		var e3= document.getElementById("devid1").value;
		var e4= document.getElementById("status1").value;
		var e5= document.getElementById("export1").value; */
		//alert("e"+e);
		//alert("e1"+e1);
		//alert("e2"+e2);
		//alert("e3"+e3);
		//alert("e4"+e4);
		//alert("e5"+e5);
		
		if (e == null || e1 == null || e == '' || e1 == '') {
			//alert("picker :"+e + "  "+ e1);
			e = document.getElementById("dateval1").value;
			e1 = document.getElementById("dateval2").value;
			//alert("hidden : "+e + "  "+ e1);
			if (e == null || e1 == null || e == '' || e1 == '') {
				alert("Please select date(s)");
			}
		} else {

			/* document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			document.getElementById("dateval3").value = e2;
			document.getElementById("dateval4").value = e3;
			document.getElementById("dateval5").value = e4; */
			

			/*  e = document.getElementById("dateval").value; */
			//alert("test2: " + e + " " + e1);
			document.location.href = '${pageContext.request.contextPath}/transaction/nonmerchantweb/pdf?fromDate=' + e
					+ '&toDate=' + e1 + '&tid=' + e2 + '&devId=' + e3 + '&status=' + e4 +'&export='+e5;
			//alert(e);
			form.submit();
			
		}
	}
	
	
	
	
	</script>


</head>

<body>


	<form method="post" name="form1"><!-- action="${pageContext.request.contextPath}/<%=NonMerchantWebTransactionController.URL_BASE%>/search">  onsubmit=" return loadSelectData()">    //onsubmit=" return loadSelectData()" -->
	<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" /> 
			<div style="overflow:auto;border:1px;width:100%">
	 <div class="content-wrapper">
        <h3 class="card-title">Non Merchant Transaction Summary</h3>        
        <div class="row">			
            <div class="col-md-12 formContianer">
            <div class="card">
           
              <div class="card-body">


	
			<div class="row">

				<div class="form-group col-md-4">
						<div class="form-group">
											<label for="From_Date">From Date</label><input type="hidden"
												name="date11" id="date11" <c:out value="${fromDate}"/>> <input
												type="text" class="form-control" id="datepicker"
												name="fromDate" style="width: 100%" placeholder="dd/mm/yyyy"
												onchange="loadDate(document.getElementById('datepicker'),document.getElementById('date11'))">

						</div>
				</div>

				<div class="form-group col-md-4">
					<div class="form-group">

						<label for="TO_Date">To Date</label><input type="hidden"
							name="date12" id="date12" <c:out value="${toDate}"/>> <input
							type="text" class="form-control" name="toDate"
							placeholder="dd/mm/yyyy" style="width: 100%" id="datepicker1" onchange="loadDate(document.getElementById('datepicker1'),document.getElementById('date12'))">

					</div>
				</div>
				
				
				

				<%-- <div class="form-group col-md-4">
					<div class="form-group">
						<label for="tid">TID </label> <input type="hidden"
							class="form-control" name="tid1" id="tid1" <c:out value="${tid}"/>>
							
						<select name="tid" id="tid" onchange="loadDropDate()"
							class="form-control" style="width: 100%">
							<option selected value=""><c:out value="TID" /></option>
							<c:forEach items="${tidList}" var="tid">

								<option <c:out value="${tid}"/>>${tid}</option>

							</c:forEach>
						</select>

					</div>
				</div>--%>
			</div> 

			<div class="row">
				<%-- <div class="form-group col-md-4 i">
					<div class="form-group">
						<label for="deviceId">Device Id</label> <input type="hidden"
							class="form-control" name="devid1" id="devid1" <c:out value="${devId}"/>>
						<select name="devId" id="devId" onchange="loadDropDate12()" style="width: 100%"
							class="form-control">

							<option selected value=""><c:out value="DevID" /></option>
							<c:forEach items="${devIdList}" var="devid">
								<option <c:out value="${devId}"/>>${devid}</option>
							</c:forEach>
						</select>
					</div>
				</div> --%> 
				<%-- <div class="form-group col-md-4 i">
					<div class="form-group">

						<label>Status</label> <input type="hidden" name="status1"
							id="status1" <c:out value="${status}"/>>
							 <select name="status"
							id="status" onchange="loadDropDate11()" class="form-control" style="width: 100%">


							<option selected value="">Choose</option>
							<option value="S">Settled</option>
							<option value="A">Pending</option>
							<option value="R">Reversal</option>
							<option value="C">Cancelled</option>
						</select>


					</div>
				</div> --%>
				
				<%-- <div class="form-group col-md-4 i">
					<div class="form-group">

						<label>Export Type</label> <input type="hidden" name="export1"
							id="export1" <c:out value="${status}"/>> <select name="export"
							id="export" onchange="loadDropDate13()" class="form-control" style="width: 100%">


							<option selected value="">Choose</option>
							<option value="PDF">PDF</option>
							<option value="EXCEL">EXCEL</option>
							
						</select>


					</div>
				</div> --%>

				<div class="form-group col-md-4 i">
					<div class="form-group">
					
							<button class="btn btn-primary" type="button"  onclick="return loadSelectData()">Search</button> 
							
	<!-- <button class="btn btn-primary"  type="button"  onclick="return loaddata()" target="_blank">Export</button> -->
					</div>
				</div>
			</div>

		</div>
		</div>
		</div>
		
	

		<div class="col-md-12 formContianer">
			<div class="card" style="width: 93.3rem;">
				<div class="card-body">
					<table class="table table-hover table-bordered" id="sampleTable">
				<thead>
					<tr>
						<th>Date</th>
						<th>Time</th>

						<th>Transaction Type</th>
						<th>Stan</th>
						<th>Amount(RM)</th>
						<!-- align="right" width="75px" -->

<!-- 						<th align="right" width="75px">DeviceHolder Name</th> -->
						<th>Location</th>

						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${paginationBean.itemList}" var="dto">
						<tr>
							<td>${dto.date}</td>
							<td>${dto.time}</td>

							<td>${dto.status}</td>
							<td>${dto.stan}</td>
							<td style="text-align:right;">${dto.amount}</td>
							<!-- style="padding: 14px;" align="right" class="separate 10px;" -->

<!-- 					<td> -->
<%-- 					<a style="color: #4bae4f" href="#" title="Tid:${dto.tid}" >${dto.merchantName}</a></td> --%>
							<td>${dto.location}</td>

							<td><a class="fa fa-pencil " aria-hidden="true"
								href="${pageContext.request.contextPath}/<%=NonMerchantWebTransactionController.URL_BASE%>/details/${dto.trxId}"
								target="_blank"><%-- <img
									src="${pageContext.request.contextPath}/resourcesNew/graphics/quicklink.png"> --%></a></td>

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

</form>


</body>
</html>






























