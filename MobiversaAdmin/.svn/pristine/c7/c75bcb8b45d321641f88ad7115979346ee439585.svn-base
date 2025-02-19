<%@page	import="com.mobiversa.payment.controller.MerchantPreAuthController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script>
  <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
<html>
<head>
<meta charset="UTF-8">
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
	
	<script src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
	
	<script type="text/javascript">

        history.pushState(null, null, "");
window.addEventListener('popstate', function ()
 {
    history.pushState(null, null, "");
 
   
});
</script>
	<script type="text/javascript">
jQuery(document).ready(function() {

$('#tid').select2();
});
</script>

	<script lang="JavaScript">
		function loadSelectData() {
			//alert("test"); 
			/* DateCheck(); */
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
			 var eDate = new Date(e1);
			  var sDate = new Date(e);
			  if(e!= '' && e!= '' && sDate> eDate)
			    {
			    alert("Please ensure that the End Date is greater than or equal to the Start Date.");
			    return false;
			    }
			var e2 = document.getElementById("tid1").value;
			/*  vare3=document.getElementById("devid1").value; */
			//var e4 = document.getElementById("status1").value;

		document.getElementById("date11").value = fromdateString;
			document.getElementById("date12").value = todateString;

			document.getElementById("tid1").value = e2;
			/* document.getElementById("devid1").value=e3;  */
			//document.getElementById("status1").value = e4;

			document.location.href = '${pageContext.request.contextPath}/merchantpreauth/search?fromDate=' + fromdateString
					+ '&toDate=' + todateString + '&tid=' + e2+ '&txnType=PREAUTH'; /*  + '&status=' + e4 */
			//form.submit;
			document.getElementById("date11").value = fromdateString;
			document.getElementById("date12").value = todateString;
			form.submit;

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
			 //alert("strUser.value"); 
			var e = document.getElementById("tid");

			var strUser = e.options[e.selectedIndex].value;
			document.getElementById("tid1").value = strUser;
			//alert("data :" + strUser + " "+ document.getElementById("tid1").value);

		}

	
		function loadData(num) {
			var pnum = num;
			//alert("page :" + pnum);
			var e = document.getElementById("datepicker").value;
			var e1 = document.getElementById("datepicker1").value;
			var e2 = document.getElementById("tid").value;
			/* var e3=document.getElementById("devId").value; */
			//alert(document.getElementById("date11").value);
			//alert(document.getElementById("date12").value);
			e = document.getElementById("date11").value;
			e1 = document.getElementById("date12").value;
			e2 = document.getElementById("tid1").value;
			if ((e == null || e == '') && (e1 == null || e1 == '')
					&& (e2 == null || e2 == '') && (e4 == null || e4 == '')) {
				/* alert('both $$ ##'); */
				document.location.href = '${pageContext.request.contextPath}/merchantpreauth/list/' + pnum;
				form.submit;
			} else {
				//alert("else : " + e + " " + e1);
				document.location.href = '${pageContext.request.contextPath}/merchantpreauth/search?fromDate=' + e
						+ '&toDate=' + e1 + '&tid=' + e2+ '&txnType=PREAUTH' + '&currPage=' + pnum; /* +'&status=' + e4 */
						 

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
	</script>
	
	
</head>

<body>

				<form method="get" name="form1" action="#">
				<div style="overflow:auto;border:1px;width:100%">
				 <div class="content-wrapper">
        <h3 class="card-title"> Pre-Auth Transaction Summary</h3>        
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
							 placeholder="dd/mm/yyyy" style="width:100%"
							onchange="loadDate(document.getElementById('datepicker'),document.getElementById('date11'))">

					</div>
				</div>
				
				
				
				<div class="form-group col-md-4">
					<div class="form-group">

						<label for="TO_Date">To Date</label><input type="hidden"
							name="date12" id="date12" <c:out value="${toDate}"/>> <input
							type="text" class="form-control" id="datepicker1" name="date1"
							placeholder="dd/mm/yyyy" style="width:100%"
							onchange="loadDate(document.getElementById('datepicker1'),document.getElementById('date12'))">

					</div>
				</div>

						<div class="form-group col-md-4">
					<div class="form-group">
						<label for="tid">TID </label> <input type="hidden"
							class="form-control" name="tid1" id="tid1" <c:out value="${tid}"/>>
						<select name="tid" id="tid" onchange="loadDropDate()" style="width:100%"
							class="form-control">
							<option selected value=""><c:out value="TID" /></option>
							<c:forEach items="${tidList}" var="tid">

								<option <c:out value="${tid}"/>>${tid}</option>

							</c:forEach>
						</select>

					</div>
				</div>
			</div>
					
	<div class="row">
			
				
					<div class="form-group col-md-4 i">
					<div class="form-group">
						<button class="btn btn-primary icon-btn" type="button"
							onclick="loadSelectData()">Search</button>

					</div>
				</div>
			</div>

		</div></div>

	
	
		<div class="card" style="width: 100rem;">
			
					<table class="table table-hover table-bordered" id="sampleTable">
				<thead>
							<tr>
								<th>Date</th>
								<th>Time</th>
							
								<th>Status</th>
								<th>Amount(RM)</th>
							
								<th>TID</th>
								<th>Location</th>
								
								<th>Action</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${paginationBean.itemList}" var="dto">
								<tr>
									<td>${dto.date}</td>
									<td>${dto.time}</td>
									<%-- <td>${dto.trxId}</td> --%>
									<td>${dto.status}</td>
									<td style="text-align:right;">${dto.amount}</td>
									<%-- <td>${dto.approvalCode}</td> --%>
									<td>${dto.tid}</td>
									<td>${dto.location}</td>
									<%-- 	<td>${dto.latitude}</td> --%>
									<%-- <td><a class="fa fa-pencil "aria-hidden="true"
										href="${pageContext.request.contextPath}<%=MerchantPreAuthController.URL_BASE%>/details/${dto.trxId}"
										target="_blank"></a></td> --%>
							<c:choose>
													<c:when test="${dto.status =='PRE-AUTH CANCEL'}">
														<td><a class="fa fa-pencil "aria-hidden="true"
										href="${pageContext.request.contextPath}<%=MerchantPreAuthController.URL_BASE%>/details/${dto.trxId}"
										target="_blank"></a></td>
													</c:when>
													<c:when test="${dto.status =='PRE-AUTH SALE'}">
														<td><a class="fa fa-pencil "aria-hidden="true"
										href="${pageContext.request.contextPath}<%=MerchantPreAuthController.URL_BASE%>/details/${dto.trxId}"
										target="_blank"></a></td>
													</c:when>
													<c:when test="${dto.status =='PRE-AUTHORIZATION'}">
														<td><a class="fa fa-pencil "aria-hidden="true"
										href="${pageContext.request.contextPath}<%=MerchantPreAuthController.URL_BASE%>/details/${dto.trxId}"
										target="_blank"></a></td>
													</c:when>
													<c:when test="${dto.status =='EZY-AUTH CANCEL'}">
														<td><a class="fa fa-pencil "aria-hidden="true"
										href="${pageContext.request.contextPath}<%=MerchantPreAuthController.URL_BASE%>/details/${dto.trxId}"
										target="_blank"></a></td>
													</c:when>
													<c:when test="${dto.status =='EZY-AUTH SALE'}">
														<td><a class="fa fa-pencil "aria-hidden="true"
										href="${pageContext.request.contextPath}<%=MerchantPreAuthController.URL_BASE%>/details/${dto.trxId}"
										target="_blank"></a></td>
													</c:when>
													<c:when test="${dto.status =='EZY-AUTH'}">
														<td><a class="fa fa-pencil "aria-hidden="true"
										href="${pageContext.request.contextPath}<%=MerchantPreAuthController.URL_BASE%>/details/${dto.trxId}"
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
		</form>
</body>
</html>

