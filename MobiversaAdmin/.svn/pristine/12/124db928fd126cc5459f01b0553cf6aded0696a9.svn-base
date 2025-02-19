<%@page import="com.mobiversa.payment.controller.AllTransactionController"%>
<%@page import="com.mobiversa.payment.controller.SuperAgentController"%>
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
	 <script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
   <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>


<script type="text/javascript">
jQuery(document).ready(function() {
$('#status').select2();
$('#export').select2();

});  
    </script>
 <script lang="JavaScript">

	function loadSelectData() {
		//alert('test1');
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e3 = document.getElementById("status").value;
		
		//alert("date picker date format:" + e);
		//alert("date picker date format1:" + e1);
		loadDropDate11();
		//alert("test data");
		var eDate = new Date(e1);
		var sDate = new Date(e);
		if(e == '' && e1 == '' && e3 == ''){
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
			 document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			//e= document.getElementById("date11").value;
			//e1=document.getElementById("date12").value; 
			e3=document.getElementById("status1").value;
			//alert("from date:" + document.getElementById("dateval1").value);
			//alert("to date:" + document.getElementById("dateval2").value);
			 document.location.href = '${pageContext.request.contextPath}/superagent/search1?date=' + e
			+ '&date1=' + e1+ '&status=' + e3; 

			form.submit;// = true; 
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
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
		
		if((e == null || e == '') && (e1 == null || e1 == '') && (e3 == null || e3 == '')){
			//alert('both $$ ##');
			document.location.href = '${pageContext.request.contextPath}/transaction1/allTransactionSummary/4'+pnum;
			form.submit;
		}else {
			
			 document.location.href = '${pageContext.request.contextPath}/superagent/search1?date=' + e
			+ '&date1=' + e1 + '&status=' + e3 +'&currPage='+pnum; 
			
			//document.forms["myform"].submit();
			form.submit;// = true; 
		}	

		 
	 
	}
	
	

	function loaddata() {
		loadDropDate11();
		loadDropDate13();
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e2 = document.getElementById("export1").value;
		var e3 = document.getElementById("status").value;
		//alert("e2" + e2);
		
		
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
		 }else{
			 //alert('test');
			 document.getElementById("dateval1").value = e;
				document.getElementById("dateval2").value = e1;
			//e= document.getElementById("date11").value;
			//e1=document.getElementById("date12").value; 
			e3=document.getElementById("status1").value;
			//alert("from date:" + document.getElementById("dateval1").value);
			//alert("to date:" + document.getElementById("dateval2").value);
			//alert("check export status:" + e3);
			document.location.href = '${pageContext.request.contextPath}/transaction1/export?date=' + e
					+ '&date1=' + e1 + '&export=' + e2+ '&status=' + e3;
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
	
	
 <div class="content-wrapper">
        <h3 class="card-title">All Transaction Summary</h3>        
        <div class="row">			
            <div class="col-md-12 formContianer">
            <div class="card">
              <div class="card-body">

	
				<div class="row">
				
				 <div class="form-group col-md-4">
								<div class="form-group">
						<label for="From_Date">From Date</label><input
							type="hidden" name="date11" id="date11" <c:out value="${fromDate}"/>>
						<input type="text"  class="form-control" id="datepicker" name="date" style="width:100%"
							placeholder="dd/mm/yyyy"
							onchange="loadDate(document.getElementById('datepicker'),document.getElementById('date11'))">
							
							</div>
							</div>
						
							 <div class="form-group col-md-4">
								<div class="form-group">
								
						 <label for="TO_Date">To Date</label><input
							type="hidden" name="date12" id="date12" <c:out value="${toDate}"/>>
						<input type="text" class="form-control" name="date1" placeholder="dd/mm/yyyy" style="width:100%"
							id="datepicker1" onchange="loadDate(document.getElementById('datepicker1'),document.getElementById('date12'))">

</div>
</div>
		<div class="form-group col-md-4 i">
								<div class="form-group">
								
							<label>Status</label>		
						<input type="hidden" name="status1" id="status1" <c:out value="${status}"/>>
							<select name="status"  style="width:100%" class="form-control" id="status" onchange="loadDropDate11()" >
					
							<option selected value="">CHOOSE</option>
							<option value="S">SETTLED</option>
							<option value="P">PENDING</option>
							<option value="R">REVERSAL</option>
							<option value="C">CANCELLED</option>
							<option value ="A">NOT SETTLED</option>
							<option value ="CT">CASH</option>
						</select>
						
								
					</div>
						</div>
		
			</div>
			
			 <!--   <div class="card-body"> -->
			   <div class="row">
					<div class="form-group col-md-4 i">
					<div class="form-group">

						<label>Export Type</label> <input type="hidden" name="export1"
							id="export1" value="${export}"> <select name="export" style="width:100%"
							id="export" onchange="loadDropDate13()" class="form-control">


							<option selected value="">CHOOSE</option>
							<option value="PDF">PDF</option>
							<option value="EXCEL">EXCEL</option>
							
						</select>
						</div>
						</div>
						
                       <!--  <div class="row"> -->
						<div class="form-group col-md-4 i">
						<div class="form-group">
						<input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2">
						<button class="btn btn-primary icon-btn" type="button" onclick="return loadSelectData()">Search</button>
						<input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2">
						<button class="btn btn-primary icon-btn"  type="button"  onclick="loaddata()">Export</button>
						</div>		
				</div>
				</div>			
				<!-- </div>		 -->
				 </div>
			
		</div>
	
		<!-- </div>
		 </div> -->
<div style="overflow:auto;border:1px;width:100%">
<div class="card" style="width: 120rem;">
			
					<table class="table table-hover table-bordered" id="sampleTable">
						<thead>
						<tr>
							<th>Date</th>
							<th>Time</th>
							<th>Mid</th>
							<th>DeviceHolder Name</th>
							<th>Status</th>
							<th>Location</th>
							<th >Amount(RM)</th> <!-- align="right" width="75px" -->
							
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paginationBean.itemList}" var="dto">
							<tr>
								<td>${dto.date}</td>
								<td>${dto.time}</td>
								<td><%-- ${dto.mid} --%>
								<a style="color: #4bae4f" href="${pageContext.request.contextPath}/transaction1/merchantdetails/${dto.mid}" target="_blank" title="Tid : ${dto.tid}">${dto.mid}</a></td>
								<td>${dto.merchantName}</td>
								<td>${dto.status}</td>
								<td>${dto.location}</td>
								<td >${dto.amount}</td> <!-- style="padding: 14px;" align="right" class="separate 10px;" -->
								
								
								<c:choose>
										<c:when test="${dto.status =='SETTLED'}">
										<td><a class="fa fa-pencil " aria-hidden="true"
											href="${pageContext.request.contextPath}<%=TransactionController.URL_BASE%>/details/${dto.trxId}"
											target="_blank"></a>
											</td>
										</c:when> 
										<c:when test="${dto.status =='NOT SETTLED'}">
										<td><a class="fa fa-pencil " aria-hidden="true"
											href="${pageContext.request.contextPath}<%=TransactionController.URL_BASE%>/details/${dto.trxId}"
											target="_blank"></a></td>
										</c:when> 
										<c:when test="${dto.status =='CANCELLED'}">
										<td><a class="fa fa-pencil " aria-hidden="true"
											href="${pageContext.request.contextPath}<%=TransactionController.URL_BASE%>/details/${dto.trxId}"
											target="_blank"></a></td>
										</c:when>  
										<c:when test="${dto.status =='CASH'}">
										<td><a class="fa fa-pencil " aria-hidden="true"
											href="${pageContext.request.contextPath}<%=TransactionController.URL_BASE%>/details/${dto.trxId}"
											target="_blank"></a></td>
										</c:when>  
										<c:otherwise>
										<td></td>
	    								</c:otherwise>
										</c:choose>
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
			</body>	
	
			</html>