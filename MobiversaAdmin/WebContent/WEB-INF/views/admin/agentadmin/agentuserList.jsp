<%@page import="com.mobiversa.payment.controller.AdminAgentController"%>
<%@page import="com.mobiversa.common.bo.Agent"%>
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
</head>
<body class="">
<div class="">
<!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            Agent User Summary
          </h1>  
       </section>
       <div>
       <div>
 <%-- <div>
	<div>Search: MobileUser ID/Terminal ID</div>
	<div>
		<form method="get" action="<%=MobileUserController.URL_BASE%>/search">
			<div class="input-group input-medium">
				<input type="text" class="form-control" placeholder="Search MobileUser" name="username"> <span class="input-group-btn">
					<button class="btn blue" type="submit">Search</button>
				</span>
			</div>
		</form>
	</div>
</div> --%>
 
<div>
	<div class="pull-right explore-page">
		<%-- <jsp:include page="../commons/pagi.jsp" flush="true" /> --%>
	</div>
	<div class="clearfix"></div>
</div>

<!-- Main content -->
        <section class="content" >
          <div class="col-xs-12 table-responsive">
              <table class="table table-striped">
		<thead>
			<tr>
				<th>MID</th>
				<th>Business Name</th>
				<th>Mobile User</th>
				<!-- <th>TID</th>
				<th>Status</th>
				<th>Activation Date</th>
				<th>Suspension Date</th>
				<th>Edit</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paginationBean.itemList}" var="dto" >
				<tr>
					<%-- <td>${dto.tid.mid.mid}<br><a href="/mobileUser/viewMobileUsers/details/${dto.mobileUser.merchant.id}">view all MobileUsers</a></td>
					<td>${dto.mobileUser.merchant.businessName}</td> --%>
					<td>${dto.agent.username}</td>
					<td>${dto.agent.username}</td>
					<td>${dto.agent.status}</td>
					<%-- <td>${dto.mobileUser.merchant.businessName}</td> --%>
					<%-- <td><fmt:formatDate value="${dto.mobileUser.activateDate }" pattern="dd-MMM-yyyy" /></td>
					<td><fmt:formatDate value="${dto.mobileUser.suspendDate }" pattern="dd-MMM-yyyy" /></td>
					<td><a  href="/mobileUser/details/${dto.mobileUser.id}"><img src="/resourcesNew/graphics/edit1.png"></a></td> --%>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</section>
</div>
</div>
</div>
<div>
	<div class="pull-right explore-page">
		<%-- <jsp:include page="../commons/pagi.jsp" flush="true" /> --%>
	</div>
	<div class="clearfix"></div>
</div>
