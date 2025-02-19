<%@page import="com.mobiversa.payment.controller.AdminController"%>
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
 <html lang="en-US">
<head>
<meta charset="UTF-8">
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
 <link href="/resourcesNew/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
 <link href="/resourcesNew/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
   
<body class="">


        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1 style="color:#0989af;">
            BankUser Summary
          </h1>  
       </section>
       
	<div>
<!-- 	<meta name="viewport" content="width=device-width, initial-scale=1.0"> -->
	<%-- <div>Search for a BankUser ID</div>
	<div>
		<form method="get" action="<%=AdminController.URL_BASE%>/search">
			<div class="input-group input-medium">
				<input type="text" class="form-control" placeholder="Search BankUser" name="username"> <span class="input-group-btn">
					<button class="btn blue" type="submit">Search</button>
				</span>
			</div>
		</form>
	</div>
</div> --%>

<div>
	<div>
		<div class="pull-right explore-page">
			<jsp:include page="../../commons/pagi.jsp" flush="true" />
		</div>
		<div class="clearfix"></div>
	</div>
	
	<section class="content">
          <div class="col-xs-12 table-responsive">
              <table class="table table-striped">
		<thead>
			<tr>
				<th>User ID</th>
				<th>Role</th>
				<th>Status</th>
				<th>Activation Date</th>
				<th>Suspension Date</th>
				<th>Edit</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paginationBean.itemList}" var="item">
				<tr>
					<td>${item.username}</td>
					<td>${item.role.value}</td>
					<td>${item.status}</td>
					<td><fmt:formatDate value="${item.activateDate }" pattern="dd-MMM-yyyy" /><td> 
					<td><fmt:formatDate value="${item.suspendDate }" pattern="dd-MMM-yyyy" /></td>
					<td><a href="/admin/bankUserDetails/${item.id}"> <img src="/resourcesNew/graphics/edit1.png"></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>
	</section>
	</div>
	
	<div>
		<div class="pull-right explore-page">
			<jsp:include page="../../commons/pagi.jsp" flush="true" />
		</div>
		<div class="clearfix"></div>
	</div>
</div>
