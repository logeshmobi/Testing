<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%-- <%@page import="com.mobiversa.common.bo.BankUser"%> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
	
	<div>
	<div>Search for a BankUser ID</div>
	<div>
		<form method="get" action="<%=AdminController.URL_BASE%>/search">
			<div class="input-group input-medium pull-left">
				<input type="text" class="form-control" placeholder="Search BankUser" name="username"> <span class="input-group-btn">
					<button class="btn blue" type="submit">Search</button>
				</span>
				
			</div>
		</form>
	</div>
</div>
<!-- <script type="text/javascript">
function validate_required(field, alerttext)
{
with(field
		{
	if (value==null||value="")
	{alert(alerttext);return false}
	else {return true}
}
}
function validate_form(thisform)
{
	with(thisform)
	{
		if(validate_required(field,"search box must be filled out!"==false))
			{field.focus();return false}
		}
}
	} -->
	
	
	<!-- } -->
<div class="portlet-body flip-scroll">
	<div>
		<div class="pull-right explore-page">
			<jsp:include page="../../commons/pagi.jsp" flush="true" />
		</div>
		<div class="clearfix"></div>
	</div>
	<c:choose>
			<c:when test="${bankUser.username == null}">
	<table
		class="table table-bordered table-striped table-condensed flip-content">
		<thead class="flip-content">
		
			<tr>
				<th>User ID</th>
				<th>Role</th>
				<th>Status</th>
				<th>Activation Date/<br>Suspension Date
				</th>
				<th class="numeric">Quick Link</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${paginationBean.itemList}" var="item">
				<tr>
					<td><c:out escapeXml='true' value='${item.username}'/></td>
					<td><c:out escapeXml='true' value='${item.role.value}'/></td>
					<td><c:out escapeXml='true' value='${item.status}'/></td>
					<td><fmt:formatDate value="${item.activateDate }" pattern="dd MMMM yyyy" /> /<br> <span><fmt:formatDate
								value="${item.suspendDate }" pattern="dd MMMM yyyy" /></span></td>
					<td><select class="form-control" id="my_selection">
							<option>select One</option>
							<option href="/admin/user/suspendbankuser/jsps/viewbankuser/">view</option>
							<option href="/admin/user/suspendbankuser/jsps/editbankuser/">Edit</option>
							<option href="/admin/user/suspendbankuser/jsps/suspendbankuser/">Suspend</option>
							<option href="/admin/user/suspendbankuser/jsps/unsuspendbankuser/">unsuspend</option>
							</select>
					<a  class="btn btn-primary" href="/admin/bankUserDetails/${item.id}" >Go</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:when>
			<c:otherwise>
				<p style="color:red;">No records found matching your search criteria!</p>
				<p style="color:red;"> Your are Currently viewing user: ${bankUser.username }</p>
			</c:otherwise>
</c:choose>
	
	<%-- <div>
		<div class="pull-right explore-page">
			<jsp:include page="../../commons/pagi.jsp" flush="true" />
		</div>
		<div class="clearfix"></div>
	</div> --%>
</div>