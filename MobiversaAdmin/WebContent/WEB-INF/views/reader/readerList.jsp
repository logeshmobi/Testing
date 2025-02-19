<%@page import="com.mobiversa.payment.controller.ReaderController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
 <script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
</head>
<body class="">
<div class="">
<!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            Reader Summary
          </h1>  
       </section>

<div>
<div>
	<div class="pull-right explore-page">
		<jsp:include page="../commons/pagi.jsp" flush="true" />
	</div>
	<div class="clearfix"></div>
</div>

<!-- Main content -->
        <section class="content" >
          <div class="col-xs-12 table-responsive">
              <table class="table table-striped">
		<thead>
			<tr>
				<th>Reader Serial No</th>
				<th>Status</th>
				<th>Activation Date</th>
				<th>Suspension Date</th>
				<th>MID</th>
				<!-- <th>Business Name </th> -->
				<th>Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paginationBean.itemList}" var="dto" >
				<tr>
					
					<td>${dto.deviceId}</td>
					<td>${dto.activeStatus}</td>
					<td><fmt:formatDate value="${dto.activatedDate }" pattern="dd-MMM-yyyy" /></td>
					<td><fmt:formatDate value="${dto.suspendedDate }" pattern="dd-MMM-yyyy" /></td>
					<td>${dto.merchantId}</td>
					<%-- <td>${dto.merchant.businessName}<br><a href="/reader/viewReaderList/${dto.merchant.id}">view all readers</a></td> --%>
					<td><a href="/reader/details/${dto.deviceId}"><img src="/resourcesNew/graphics/edit1.png"></a></td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
		</div>
		</section>
	</div>
</div>
<div>
	<div class="pull-right explore-page">
		<jsp:include page="../commons/pagi.jsp" flush="true" />
	</div>
	<div class="clearfix"></div>
</div>
