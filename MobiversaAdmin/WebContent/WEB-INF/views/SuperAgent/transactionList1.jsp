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
<!-- <link href="/resourcesNew/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet"
	type="text/css" /> -->
	<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
	<style>
	
	
	</style>
</head>
<body>
	 <script lang="JavaScript">

	function loadSelectData() {
		//alert('test1');
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e3 = document.getElementById("status").value;
		loadDropDate11();
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
			e= document.getElementById("date11").value;
			e1=document.getElementById("date12").value; 
			e3=document.getElementById("status1").value;
			 document.location.href = '${pageContext.request.contextPath}/superagent/search1?date=' + e
			+ '&date1=' + e1+ '&status=' + e3; 

			form.submit;// = true; 
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
			document.location.href = '${pageContext.request.contextPath}/superagent/allTransactionSummary/'+pnum;
			form.submit;
		}else {
			//alert("Test Else condition");
			//alert("else : "+e+" "+e1);
			/* document.location.href = '/transaction1/search1?date=' + e
					+ '&date1=' + e1 +'&currPage='+pnum; */
			
			 document.location.href = '${pageContext.request.contextPath}/superagent/search1?date=' + e
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
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		var e2 = document.getElementById("export1").value;
		var e3 = document.getElementById("status").value;
		//alert("e2" + e2);
		
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
		 }else{
			 //alert('test');
			e= document.getElementById("date11").value;
			e1=document.getElementById("date12").value; 
			e3=document.getElementById("status1").value;
			document.location.href = '${pageContext.request.contextPath}/superagent/export1?date=' + e
					+ '&date1=' + e1 + '&export=' + e2+ '&status=' + e3;
			//alert(e);
			form.submit();
			
		}
	}
	

	//Script by Denis Gritcyuk: tspicker@yahoo.com
	//Submitted to JavaScript Kit (http://javascriptkit.com)
	//Visit http://javascriptkit.com for this script
</script> 

	
	<div class="pageContent ">
			<div class="container">
				<div class="boxHeader pageBoxHeader clearfix">
					<div class="pull-left">
						<h1 class="pageTitle">
							<a href="#" title="#">All Transaction Summary</a>
						</h1>


        </div>
        </div>
        </div>
        </div>
	

	<div>
		<form method="get" 	name="form1" action="#">	
		
		
		<div class="box box-without-bottom-padding">

				<div class="row">
				
				 <div class="col-xs-12 col-sm-4">
								<div class="form-group">
						<label for="From_Date">From Date</label><input
							type="hidden" name="date11" id="date11" <c:out value="${fromDate}"/>>
						<input type="text"  class="form-control" id="datepicker" name="date" style="width:100%"
							placeholder="dd/mm/yyyy"
							onchange="loadDate(document.form1.date,document.form1.date11)">
							
							</div>
							</div>
						
							 <div class="col-xs-12 col-sm-4">
								<div class="form-group">
								
						 <label for="TO_Date">To Date</label><input
							type="hidden" name="date12" id="date12" <c:out value="${toDate}"/>>
						<input type="text" class="form-control" name="date1" placeholder="dd/mm/yyyy" style="width:100%"
							id="datepicker1" onchange="loadDate(document.form1.date1,document.form1.date12)">

</div>
</div>
		<div class="col-xs-12 col-sm-4 i">
								<div class="form-group">
								
							<label>Status</label>		
						<input type="hidden" name="status1" id="status1" <c:out value="${status}"/>>
							<select name="status"  class="js-select" id="status" onchange="loadDropDate11()" >
					
							<option selected value="">CHOOSE</option>
							<option value="S">SETTLED</option>
							<option value="P">PENDING</option>
							<option value="R">REVERSAL</option>
							<option value="C">CANCELLED</option>
							<option value ="A">NOT SETTLED</option>
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
		
		
		
		
		<div class="col-xs-12 col-sm-4 i">
								<div class="form-group">	
						<button class="btn btn-primary" type="button" onclick="return loadSelectData()">Search</button>
						<button class="btn btn-primary"  type="button"  onclick="loaddata()">Export</button>
						</div>		
				</div>
				</div>			
						
				 </div>
		
					<div class="box box-without-bottom-padding" >
					<div class="tableWrap dataTable table-responsive js-select"  >
						<table class="table js-datatable" >
					<thead>
						<tr>
							<th >Date</th>
							<th>Time</th>
							<th>Mid</th>
							<th  >DeviceHolder Name</th>
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
										<td><a
											href="${pageContext.request.contextPath}<%=TransactionController.URL_BASE%>/details/${dto.trxId}"
											target="_blank"><img
												src="${pageContext.request.contextPath}/resourcesNew/graphics/quicklink.png"></a></td>
										</c:when> 
										<c:when test="${dto.status =='NOT SETTLED'}">
										<td><a
											href="${pageContext.request.contextPath}<%=TransactionController.URL_BASE%>/details/${dto.trxId}"
											target="_blank"><img
												src="${pageContext.request.contextPath}/resourcesNew/graphics/quicklink.png"></a></td>
										</c:when> 
										<c:when test="${dto.status =='CANCELLED'}">
										<td><a
											href="${pageContext.request.contextPath}<%=TransactionController.URL_BASE%>/details/${dto.trxId}"
											target="_blank"><img
												src="${pageContext.request.contextPath}/resourcesNew/graphics/quicklink.png"></a></td>
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
			
			</form>
		