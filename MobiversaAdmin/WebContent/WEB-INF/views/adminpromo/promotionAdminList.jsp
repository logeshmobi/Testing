<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@page import="com.mobiversa.common.bo.MerchantDetails"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
   <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 
    <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 
    
   <script lang="JavaScript">
	function loadSelectData() {
		//alert("test");
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		//alert("test"+e + " "+e1);
		if (e == null || e == '' || e1 == null || e1 == '') {
			alert("Please Select date(s)");
			//form.submit == false;
		} else {
			//alert("test1212"+e + " "+e1);
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;
			document.location.href = '${pageContext.request.contextPath}/promotionAdmin/search?date='
					+ e + '&date1=' + e1;
			//alert("test1212 "+document.getElementById("dateval1").value);
			//alert("test1212 "+document.getElementById("dateval2").value);
			form.submit;
			document.getElementById("dateval1").value = e;
			document.getElementById("dateval2").value = e1;

		}
	}
	
	
	
	function loadData(num) {
		var pnum = num;
		//alert("page :"+pnum);
		var e = document.getElementById("datepicker").value;
		var e1 = document.getElementById("datepicker1").value;
		//alert(document.getElementById("date11").value);
		//alert(document.getElementById("date12").value);
		e = document.getElementById("date11").value;
		e1 = document.getElementById("date12").value;

		//alert(e + '  '+ e1);

		/* if (e == null || e == '' ) {
			alert("Please Select FromDate");
			form.submit = false;
		} else if (e1 == null || e1 == ''){
			alert("Please Select ToDate");
			form.submit = false;
		} else */if ((e == null || e == '') && (e1 == null || e1 == '')) {
			//alert('both $$ ##');
			document.location.href = '${pageContext.request.contextPath}/promotionAdmin/list/'
					+ pnum;
			form.submit;
		} else {
			//alert("else : "+e+" "+e1);
			document.location.href = '${pageContext.request.contextPath}/promotionAdmin/search?date='
					+ e + '&date1=' + e1 + '&currPage=' + pnum;

			//document.forms["myform"].submit();
			form.submit;// = true; 

		}

	}
	
	

	function loadDate(inputtxt,outputtxt)  
  	{  
  	 var field = inputtxt.value;
 	 //var field1 = outputtxt.value;
 	 alert(field+" : "+outputtxt.value);
 	 //document.getElementById("date11").value=field;
 	 outputtxt.value= field;
 	 alert(outputtxt.value);
 	// alert(document.getElementById("date11").value);
	}
	</script>
</head>
<body>
<div style="overflow:auto;border:1px;width:100%">
	<div class="content-wrapper">


		<div class="row">

			<div class="col-md-12 formContianer">
				<h3 class="card-title">
					Merchant EZYAds Summary
					<%-- <a class="btn btn-primary btn-flat pull-right"
						href="${pageContext.request.contextPath}/promotionAdmin/editMerchantPromotion">Promotion Edit
					 	
						
						 <i class="fa fa-lg fa-plus"></i>
					</a>--%>
				</h3>
				<!-- <div class="card" >
              <div class="card-body"> -->
              
            <%--  <div class="row">

						<div class="form-group col-md-4">
							<div class="form-group">
								<label for="From_Date">From Date</label><input type="hidden"
									name="date11" id="date11" value="${fromDate}"> <input
									type="text" class="form-control" id="datepicker" name="date"
									  placeholder="dd/mm/yyyy"
									onchange="loadDate(document.getElementById('datepicker'),document.getElementById('date11'))">

							</div>
						</div>
						<div class="form-group col-md-4">
							<div class="form-group">
								<label for="From_Date">To Date</label><input type="hidden"
									name="date11" id="date11" value="${toDate}"> <input
									type="text" class="form-control" id="datepicker1" name="date1"
									placeholder="dd/mm/yyyy"
									onchange="loadDate(document.getElementById('datepicker1'),document.getElementById('date11'))">

							</div>
						</div>
              <div class="row">
						<div class="form-group col-md-4 i">
							<div class="trans">
							<input type="hidden" name="date1" id="dateval1"> <input
									type="hidden" name="date2" id="dateval2">
								<button class="btn btn-primary icon-btn" type="button"
									onclick="loadSelectData()">Search</button>
              
              </div>
              </div></div></div></div>
              </div> --%>
              
              
              
              
					
				<div class="card" style="width: 140rem;">
					<div class="card-body">
						<table class="table table-hover table-bordered" id="sampleTable">
		<thead>
			<tr>
			
			 <!-- <th>MobiPromo_id</th> -->
			   <th>Activated Date</th>
		    	<th >Created Date</th>
		    	
				<th>Business Name</th>
				<th>MID</th> 
				<th style="width:100%">EZYAds Name</th>
				<th>Status</th>
				
				<th>EZYAds Code</th>
				<th>ValidFrom</th>
				<th>ValidTo</th>
				
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paginationBean.itemList}" var="merc">
				<tr>
				<%-- <td>${merc.id}</td> --%>
				
				<td><fmt:formatDate  <c:out value="${merc.activateDate}"/> pattern="dd-MMM-yyyy" /> </td>
				<td><fmt:formatDate <c:out value="${merc.createdDate}"/>  pattern="dd-MMM-yyyy" /> </td>
				
				<%-- <td>${merc.activateDate}</td>
				<td>${merc.createdDate}</td> --%>
					<td>${merc.merchantName}</td>
					<td>${merc.mid}</td> 
					<td>${merc.promoName}</td>
					<td>${merc.status }</td>
					<td>${merc.promoCode }</td>
					<%-- <td> <fmt:formatDate value="${merc.validityDate}" pattern="dd-MMM-yyyy" /></td> --%>
					
					
					<%-- <td><fmt:parseDate value="${merc.validityDate}" pattern="yyyy-MM-dd" var="myDate"/>
<fmt:formatDate value="${myDate}" pattern="dd-MMM-yyyy"/></td> --%>

<%-- <td>${merc.validFrom}</td>
<td>${merc.validTo}</td> --%>
<td><fmt:parseDate <c:out value="${merc.validFrom}"/>  pattern="dd/MM/yyyy" var="myDate"/>
<fmt:formatDate <c:out value="${myDate}"/>  pattern="dd-MMM-yyyy"/></td>

<td><fmt:parseDate <c:out value="${merc.validTo}"/>   pattern="dd/MM/yyyy" var="myDate1"/>
<fmt:formatDate <c:out value="${myDate1}"/>  pattern="dd-MMM-yyyy"/></td>

					
				<%-- <td><fmt:formatDate value="${merc.validityDate}" pattern="dd-MMM-yyyy" /></td> --%>
					<td><a class="fa fa-pencil " aria-hidden="true" href="${pageContext.request.contextPath}/promotionAdmin/detail/${merc.id}"></a></td>
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
</body></html>
