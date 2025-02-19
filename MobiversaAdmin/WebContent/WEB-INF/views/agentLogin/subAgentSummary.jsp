<%@page import="com.mobiversa.payment.controller.AgentSubMenuController"%>

<%@page import="com.mobiversa.common.bo.SubAgent"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<html lang="en-US">
<head>
<meta charset="UTF-8">
   <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
    <script type="text/javascript"  src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
</head>
<body>

<div style="overflow:auto;border:1px;width:100%">
<div class="content-wrapper">


		<div class="row">

			<div class="col-md-12 formContianer">
				<h3 class="card-title">
					SubAgent Summary<a class="btn btn-primary btn-flat pull-right"
						href="${pageContext.request.contextPath}/subagent/addSubAgent">Add
						New SubAgent <i class="fa fa-lg fa-plus"></i>
					</a>
				</h3>
				<div class="card" style="width: 90rem;">
					<div class="card-body">
						<table class="table table-hover table-bordered" id="sampleTable">

		<thead>
			<tr>
			
			<th>Created Date</th>
				<th>SubAgent Name</th>
				<th>AgCode</th>
				<th>AgType</th>
				
				<th>Status</th>
				
				<th>Edit</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paginationBean.itemList}" var="dto">
				<tr>
				
				 <td><fmt:formatDate value="${dto.createdDate }" pattern="dd-MMM-yyyy" /> </td> 
					<td>${dto.name}</td>
					<td>${dto.code}</td>
					<td>${dto.type}</td>
					
					<td>${dto.status}</td>
					
					
					<td>
					<%-- <a class="fa fa-pencil " aria-hidden="true" href="${pageContext.request.contextPath}/subagent/detail/${dto.id}">
					
					</a> --%>
					
					<form  method="post" id="form-edit" 
					action="${pageContext.request.contextPath}/subagent/detail/" > 
	 
	      <%--  <input type="hidden" name="status" value="${dto.status}" /> --%>
	       <input type="hidden" name="id" value="${dto.id}" />
	       
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
				<center><button type="submit"   style="border-style:none;color:blue;" > 
				<i class="fa fa-pencil" aria-hidden="true"></i></button></center>
				</form>
					
					</td>
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

