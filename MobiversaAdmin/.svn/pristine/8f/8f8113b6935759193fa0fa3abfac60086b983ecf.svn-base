<%@page import="com.mobiversa.payment.controller.ReaderController"%>
<%@page import="com.mobiversa.common.bo.Merchant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<div>
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">Your are Currently viewing MID: ${merchant.mid.mid}</div>
		<div class="pull-right">
			<input name="search" type="button" value="View Reader Details" class="btn btn-default" />
			<div class="clearfix"></div>
		</div>
	</div>

<div class="col-xs-12 padding-10">
<div class="pull-left">	
Filter By Status:<select class="form-control">
      <option>Select One</option>
      <option>Suspend</option>
      <option>Active</option>
  </select>
</div>
</div>
<div>
	<div class="pull-right explore-page">
		<jsp:include page="../commons/pagi.jsp" flush="true" />
	</div>
	<div class="clearfix"></div>
</div>

<div class="table-scrollable">
	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th scope="col" style="width: 450px !important">Reader Serial No</th>
				<th scope="col">Status</th>
				<th scope="col">Activation Date/<br>Suspension Date
				</th>
				<th scope="col" class="numeric">Action</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paginationBean.itemList}" var="dto">
				<tr>
					<td>${dto.serialNumber}</td>
					<td>${dto.status}</td>
					<td><fmt:formatDate value="${dto.activateDate }" pattern="dd MMMM yyyy" /> /<br> <span><fmt:formatDate
								value="${dto.suspendDate }" pattern="dd MMMM yyyy" /></span></td>
					<td><a class="btn btn-primary" href="/reader/details/${dto.merchant.mid.id}">View</a></td>
					
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</div>
<div>
	<div class="pull-right explore-page">
		<jsp:include page="../commons/pagi.jsp" flush="true" />
	</div>
	<div class="clearfix"></div>
</div>
