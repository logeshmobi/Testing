<%@page import="com.mobiversa.payment.controller.AgentSubController"%>

<%@page import="com.mobiversa.common.bo.SubAgent"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
   <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <!-- <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
   <script src="/resourcesNew/js/jQuery-2.1.4.min.js" type="text/javascript"></script>  -->
   <script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
</head>
<body>
<%-- <div>
<!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            Sub Agent Summary
          </h1>  
       </section> --%>
       
       
       <div class="pageContent ">
			<div class="container">
				<div class="boxHeader pageBoxHeader clearfix">
					<div class="pull-left">
						<h1 class="pageTitle">
							<a href="#" title="#">Sub Agent Summary</a>
						</h1>


        </div>
        </div>
        </div>
        </div>
     
<%-- <div>
	<div class="pull-right explore-page">
		<jsp:include page="../commons/pagi.jsp" flush="true" />
	</div>
	<div class="clearfix"></div>
</div> --%>

<!-- Main content -->
      <%--   <section class="content">
          <div class="col-xs-12 table-responsive">
              <table class="table table-striped"> --%>
              
              <div class="box box-without-bottom-padding">
					<h2 class="boxHeadline">Sub Agent Summary </h2>					
					<div class="tableWrap dataTable table-responsive js-select">
						<table class="table js-datatable">
		<thead>
			<tr>
			
			     <th>Created Date</th>
				<th>SubAgent Name</th>
				<th>AgCode</th>
				<th>AgType</th>
				<!-- <th>Bank Name</th> -->
				<th>Status</th>
				
				<th>Edit</th>
				<!-- <th>Suspension Date</th>
				<th>Edit</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paginationBean.itemList}" var="dto">
				<tr>
				
				 <td><fmt:formatDate value="${dto.createdDate }" pattern="dd-MMM-yyyy" /> </td> 
					<td>${dto.name}</td>
					<td>${dto.code}</td>
					<td>${dto.type}</td>
					<%-- <td>${dto.bankName}</td> --%>
					<td>${dto.status}</td>
					
					<%-- <td><fmt:formatDate value="${dto.suspendDate }" pattern="dd-MMM-yyyy" /></td>  --%>
					<td><a href="${pageContext.request.contextPath}/agent5/detail/${dto.id}"><img src="${pageContext.request.contextPath}/resourcesNew/img/edit.png"></a></td>
               </tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</div>
<%-- </section>
</div>
<div>
	<div class="pull-right explore-page">
		<jsp:include page="../commons/pagi.jsp" flush="true" />
	</div>
	<div class="clearfix"></div>
</div> --%>
