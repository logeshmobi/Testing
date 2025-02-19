<%-- <%@page import="com.mobiversa.payment.controller.TransactionController"%> --%>
<%@page import="com.mobiversa.payment.controller.SuperAgentController"%>
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
   <script  type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
   <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>

<script type="text/javascript">
jQuery(document).ready(function() {
	
	
$('#export').select2();
$('#txnType').select2();
});
</script>



<script lang="JavaScript">
	function loadSelectData() {
		//alert("test");
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e2 = document.getElementById("txnType").value;
		if (e == null || e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			document.getElementById("txnType").value = e2;
			document.location.href = '${pageContext.request.contextPath}/superagent/search?date=' + e
					+ '&date1=' + e1+'&txnType='+e2;
			form.submit;
			//document.getElementById("dateval1").value = e;
			//document.getElementById("dateval2").value = e1;

		}
		//alert(e + " " + e1);
	}
	
	
	function loadDropDate13() {
			//alert("loadDropDate13");
			var e = document.getElementById("export");

			var strUser = e.options[e.selectedIndex].value;
			document.getElementById("export1").value = strUser;
			//alert("data :" + strUser + " "+ document.getElementById("status1").value);

		}
		
	function loadDropDate14() {
		//alert("loadDropDate13");
		var e = document.getElementById("txnType");

		var strUser = e.options[e.selectedIndex].value;
		document.getElementById("txnType1").value = strUser;
		//alert("data :" + strUser + " "+ document.getElementById("status1").value);

	}
	

	function loaddata() {
		loadDropDate13();
		loadDropDate14();
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e2 = document.getElementById("export1").value;
		var e3 = document.getElementById("txnType1").value;
		//alert("e2" + e2);
		
		if (e == null || e1 == null || e == '' || e1 == '') {
			//alert("picker :"+e + "  "+ e1);
			e = document.getElementById("dateval1").value;
			e1 = document.getElementById("dateval2").value;
			//alert("hidden : "+e + "  "+ e1);
			if (e == null || e1 == null || e == '' || e1 == '') {
				alert("Please select date(s)");
			}
		} else {

			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;

			/*  e = document.getElementById("dateval").value; */
			//alert("test2: " + e + " " + e1);
			document.location.href = '${pageContext.request.contextPath}/superagent/export?date=' + e
					+ '&date1=' + e1 + '&export=' + e2+'&txnType='+e3;
			//alert(e);
			form.submit();
			
		}
	}



 function loadDate(inputtxt,outputtxt)  
  	{  
  	 var field = inputtxt.value;
 	 //var field1 = outputtxt.value;
 	 //alert(field+" : "+outputtxt.value);
 	 //document.getElementById("date11").value=field;
 	 outputtxt.value= field;
 	 //alert(outputtxt.value);
 	// alert(document.getElementById("date11").value);
	} 
	
	
	function loadData(num){
		var pnum= num;
		//alert("page :"+pnum);
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		//alert(document.getElementById("date11").value);
		//alert(document.getElementById("date12").value);
		e= document.getElementById("date11").value;
		e1=document.getElementById("date12").value;
		
		//alert(e + '  '+ e1);
		
		/* if (e == null || e == '' ) {
			alert("Please Select FromDate");
			form.submit = false;
		} else if (e1 == null || e1 == ''){
			alert("Please Select ToDate");
			form.submit = false;
		} else */ if((e == null || e == '') && (e1 == null || e1 == '')){
			//alert('both $$ ##');
			document.location.href = '${pageContext.request.contextPath}/superagent/list/'+pnum;
			form.submit;
		}else {
			//alert("else : "+e+" "+e1);
			document.location.href = '${pageContext.request.contextPath}/superagent/search?date=' + e
					+ '&date1=' + e1+ '&currPage='+pnum;
			
			//document.forms["myform"].submit();
			form.submit;// = true; 
			

		} 
	
	} 
	
	//Script by Denis Gritcyuk: tspicker@yahoo.com
	//Submitted to JavaScript Kit (http://javascriptkit.com)
	//Visit http://javascriptkit.com for this script
</script>
<body class="">

<!-- <form  name="form1" action="#"> -->
<div style="overflow:auto;border:1px;width:100%">
 <div class="content-wrapper">
 
 
        <h3 class="card-title"> Transaction Summary</h3>        
        <div class="row">			
            <div class="col-md-12 formContianer">
            <div class="card">
              <div class="card-body">


	
			<div class="row">
				
				<div class="form-group col-md-4">
					<div class="form-group">
						<label for="From_Date">From Date</label><input type="hidden"
							name="date11" id="date11" <c:out value="${fromDate}"/>> <input
							type="text" class="form-control" id="datepicker" name="date"
							 placeholder="dd/mm/yyyy" 
							onchange="loadDate(document.getElementById('datepicker'),document.getElementById('date11'))">

					</div>
				</div>
				
				
				
				<div class="form-group col-md-4">
					<div class="form-group">

						<label for="TO_Date">To Date</label><input type="hidden"
							name="date12" id="date12" <c:out value="${toDate}"/>> <input
							type="text" class="form-control" id="datepicker1" name="date1"
							placeholder="dd/mm/yyyy" onchange="loadDate(document.getElementById('datepicker1'),document.getElementById('date12'))">

					</div>
				</div>
				
				<div class="form-group col-md-4 i">
							<div class="form-group">

								<label class="control-label">Txn Type</label> <input
									type="hidden" name="txnType1" id="txnType1" <c:out value="${status}"/>>
								<select name="txnType" id="txnType" onchange="loadDropDate14()"
									class="form-control" style="width:100%">
									<option selected value="">Choose</option>
									<option value="ALL">ALL</option>
									<option value="CARD">CARD</option>
									<option value="CASH">CASH</option>
								</select>
							</div>
						</div>

	
						<div class="form-group col-md-4 i">
							<div class="form-group">

								<label class="control-label">Export Type</label> <input
									type="hidden" name="export1" id="export1" <c:out value="${status}"/>>
								<select name="export" id="export" onchange="loadDropDate13()"
									class="form-control" style="width:100%">
									<option selected value="">Choose</option>
									<option value="PDF">PDF</option>
									<option value="EXCEL">EXCEL</option>
								</select>
							</div>
						</div>
					</div>
					
		
	
	
					<div class="row">
						<div class="form-group col-md-4 i">
						<div class="form-group">
							
							<input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2">
								<button class="btn btn-primary icon-btn" type="button"
									onclick="loadSelectData()">Search</button>
								

<input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2">
								<button class="btn btn-primary icon-btn" type="button"
									onclick="loaddata()">Export</button>
							</div>
						</div>

					</div>
				</div>
			</div>
		
					<div class="card" style="width: 120rem;">
			
					<table class="table table-hover table-bordered" id="sampleTable">
			<thead>
				<tr>
				<th ><!-- style="width: 25%" -->Date</th>
				<th>AgentName</th>
				<th>Business Name</th>
				<th>Activation Date</th>
					
					
					
					<th>Amount(RM)</th>
					<th>Location</th>	
					
					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${paginationBean.itemList}" var="dto">
					<tr>
						<td>${dto.date}</td>
						<td><a style="color: #4bae4f" href="${pageContext.request.contextPath}/superagent/agentdetails/${dto.agentName}">${dto.agentName}</a></td>
						
						
						<%-- <td><fmt:formatDate value="$${dto.date}" pattern="dd-MMM-yyyy" /> </td> --%>
						<%-- <td>${dto.time}</td> --%>
						<%-- <td><a href="/transaction/agentdetails/${dto.agentName}">${dto.agentName}</a></td> --%>
						<td> <form method="get"
				action="${pageContext.request.contextPath}/<%=SuperAgentController.URL_BASE%>/merchantdetails"> 
				
				       <input
						type="hidden"  name="date" value="${dto.date}">
					  <!-- <input
						type="hidden" name="date1" value="1130"> --> 
						<input
						type="hidden" name="merchantName" value="${dto.merchantName}">
						<input
						type="hidden" name="city" value="${dto.location}">
						 
						
				<button class="btn blue" type="submit">${dto.merchantName}</button>
				<%-- <%-- <a
							href="/transaction/merchantdetails/${dto.merchantName}~${dto.location}"></a> --%>			
							</form>
							</td>
						<td>${dto.numOfRefund}</td>
						 <td style="text-align:right;">${dto.amount}</td>  <!-- style="padding: 14px; width: 45px" align="left" class="separate 20px;" -->
						<td>${dto.location}</td>
						
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
	
	
