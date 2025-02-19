<%@page import="com.mobiversa.payment.controller.MerchantWebReaderController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
<div>
	<div>Search: Reader Serial No</div>
	<div>
		<form method="get" action="${pageContext.request.contextPath}<%=MerchantWebReaderController.URL_BASE%>/search">
			<div class="input-group input-medium">
				<input type="text" class="form-control" placeholder="Search Reader" name="serialNumber"> <span class="input-group-btn">
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
			<c:when test="${reader.serianlNumber == null}">
	<table
		class="table table-bordered table-striped table-condensed flip-content">
		<thead class="flip-content">
		
		<tr>
				<th scope="col" style="width: 450px !important">Reader Serial No</th>
				<th scope="col">Status</th>
				<th scope="col">Activation Date/<br>Suspension Date
				</th>
				<th scope="col" class="numeric">Quick Link</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paginationBean.itemList}" var="dto" >
				<tr>
					<td>${dto.serialNumber}</td>
					<td>${dto.status}</td>
					<td><fmt:formatDate value="${dto.activateDate }" pattern="dd MMMM yyyy" /> /<br> <span><fmt:formatDate
								value="${dto.suspendDate }" pattern="dd MMMM yyyy" /></span></td>
					<td><a class="btn btn-primary" href="${pageContext.request.contextPath}/readerweb/detail/${dto.id}">View</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:when>
			<c:otherwise>
				<p style="color:red;">No records found matching your search criteria!</p>
				<p style="color:red;"> Your are Currently viewing user: ${reader.serialNumber }</p>
			</c:otherwise>
</c:choose>
	
	<%-- <div>
		<div class="pull-right explore-page">
			<jsp:include page="../../commons/pagi.jsp" flush="true" />
		</div>
		<div class="clearfix"></div>
	</div> --%>
</div>