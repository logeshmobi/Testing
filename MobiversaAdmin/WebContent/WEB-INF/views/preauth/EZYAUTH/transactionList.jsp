<%@page import="com.mobiversa.payment.controller.PreAuthTxnController"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
   <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
	<script src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>

<script type="text/javascript">
        history.pushState(null, null, "");
window.addEventListener('popstate', function () {
    history.pushState(null, null, "");
   
    
});


</script>
	<script lang="JavaScript">

	function loadSelectData() {
		//alert("test");
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		if (e == null || e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			document.location.href = '${pageContext.request.contextPath}/preauthtxn/search?date=' + e
					+ '&date1=' + e1+'&txnType=EZYAUTH';
			form.submit;
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;

		}
		
	}

	function loaddata() {
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		
		if (e == null || e1 == null || e == '' || e1 == '') {
			
			e = document.getElementById("dateval1").value;
			e1 = document.getElementById("dateval2").value;
		
			if (e == null || e1 == null || e == '' || e1 == '') {
				alert("Please select date(s)");
			}
		} else {

			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;

			/*  e = document.getElementById("dateval").value; */
			//alert("test2: " + e + " " + e1);
			document.location.href = '${pageContext.request.contextPath}/preauthtxn/export?date=' + e
					+ '&date1=' + e1+'&txnType=EZYAUTH';
			//alert(e);
			form.submit();
			
		}
	}


	 function loadDate(inputtxt,outputtxt)  
	 {  
		 
		 
		// alert("test data123");
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
		 if((e == null || e == '') && (e1 == null || e1 == '')){
			//alert('both $$ ##');
			document.location.href = '${pageContext.request.contextPath}/preauthtxn/ezyauthlist/'+pnum;
			form.submit;
		}else {
			//alert("else : "+e+" "+e1);
			document.location.href = '${pageContext.request.contextPath}/preauthtxn/search?date=' + e
					+ '&date1=' + e1+'&txnType=EZYAUTH'+ '&currPage='+pnum;
			
			//document.forms["myform"].submit();
			form.submit;// = true; 
			

		} 
	
	} 
	
</script>
<body class="">
<div style="overflow:auto;border:1px;width:100%">
		 <div class="content-wrapper">
        <h3 class="card-title">EZYAUTH Summary</h3>        
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
</div></div>
							<div class="row">
							<div class="form-group col-md-4 i">
								<div class="form-group">	
						<button class="btn btn-primary icon-btn" type="button" onclick="loadSelectData()">Search</button>
						<input type="hidden" name="date1" id ="dateval1">
						<input type="hidden" name="date2" id ="dateval2">
						</div>
						</div>
						
						</div>	
						</div>
					</div>
					
					
					
					
					
					
					
					
					
					
					
					
	<div class="card" style="width: 90rem;">
			
					<table class="table table-hover table-bordered" id="sampleTable">
			<thead>
				
				<tr>
					<th ><!-- style="width: 25%" -->Date</th>
					
					<th>Business Name</th>
					<th>Amount(RM)</th>
					<th>State</th>	
					<!-- <th>AgentName</th> -->
					
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${paginationBean.itemList}" var="dto">
					<tr>
						<td>${dto.date}</td>
						
						<td> <form method="get"
				action="${pageContext.request.contextPath}<%=PreAuthTxnController.URL_BASE%>/merchant_ezyauthtxn"> 
				
				       <input
						type="hidden"  name="date" value="${dto.date}">
					  <!-- <input
						type="hidden" name="date1" value="1130"> --> 
						<input
						type="hidden" name="merchantName" value="${dto.merchantName}">
						<input
						type="hidden" name="city" value="${dto.location}">
						 
						
				<button class="btn blue" type="submit" style="color:275ca8;"><font color="275ca8">${dto.merchantName}</font></button>
				<%-- <%-- <a
							href="/transaction/merchantdetails/${dto.merchantName}~${dto.location}"></a> --%>			
							</form>
							</td>
						
						 <td style="text-align:right;">${dto.amount}</td> 
						<td>${dto.location}</td>
						<%-- <td><a href="/preauthtxn/agentdetails/${dto.agentName}">${dto.agentName}</a></td> --%>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
		</div>
	</div>		
			</div>
			</div>
			</body>
			</html>
			