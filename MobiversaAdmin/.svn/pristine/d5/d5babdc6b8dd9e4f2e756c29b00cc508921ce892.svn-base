<%@page import="com.mobiversa.payment.controller.MerchantWebMobileController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
	<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
	<div>
	<div>Search for a MobileUser</div>
	<div>
		<form method="get" action="<%=MerchantWebMobileController.URL_BASE%>/search">
			<div class="input-group input-medium">
				<input type="text" class="form-control" placeholder="Search MobileUser" name="searchStr"> <span class="input-group-btn">
					<button class="btn blue" type="submit">Search</button>
				</span>
			</div>
		</form>
	</div>
</div>
<div class="portlet-body flip-scroll">
	<div>
		<div class="pull-right explore-page">
			<jsp:include page="../../commons/pagi.jsp" flush="true" />
		</div>
		<div class="clearfix"></div>
	</div>
	<c:choose>
			<c:when test="${mobileUser.username == null}">
	<table
		class="table table-bordered table-striped table-condensed flip-content">
		<thead class="flip-content">
		
			<tr>
				<th>Mobile user Id</th>
				<th>Full Name</th>
				<th>TID</th>
				<th>Status</th>
				<th>Activation Date/<br>Suspension Date
				</th>
				<th class="numeric">Quick Link</th>
			</tr>
		</thead>
		<tbody>
		<tr>
			<c:forEach items="${paginationBean.itemList}" var="dto" >
				<tr>
					<td>${dto.mobileUser.id}<br>
					<%-- <a href="/mobileUser/viewMobileUsers/details/${dto.mobileUser.merchant.id}">view all MobileUsers</a></td> --%>
					<td>${dto.mobileUser.username}</td>
					<td>${dto.tid.tid}</td>
					<td>${dto.mobileUser.status}</td>
					<%-- <td>${dto.mobileUser.merchant.businessName}</td> --%>
					<td><fmt:formatDate value="${dto.mobileUser.activateDate}" pattern="dd MMMM yyyy" /> /<br> <span><fmt:formatDate
								value="${dto.mobileUser.suspendDate }" pattern="dd MMMM yyyy" /></span></td>
					<td><a class="btn btn-primary" href="/mobileUserweb/details/${dto.mobileUser.merchant.id}">View</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:when>
			<c:otherwise>
				<p style="color:red;">No records found matching your search criteria!</p>
				<p style="color:red;"> Your are Currently viewing user: ${mobileUser.username }</p>
			</c:otherwise>
</c:choose>
	
	<div>
		<div class="pull-right explore-page">
			<jsp:include page="../../commons/pagi.jsp" flush="true" />
		</div>
		<div class="clearfix"></div>
	</div>
</div>