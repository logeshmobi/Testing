<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<form action="" method="post"> 
<div>
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">Your are Currently viewing user: ${bankUser.username}</div>
		<div class="pull-right">
			<input name="search" type="button" value="Switch BankUser" class="btn btn-default" />
			<div class="clearfix"></div>
		</div>
	</div>
	<div class="col-xs-12 padding-10">
		<div class="pull-left h2">BankUser Details</div>
		<div class="pull-right">
			<a href="<%=AdminController.URL_BASE%>/edit/${bankUser.id}" class="btn btn-default">Edit Bank User Details</a>
		</div>
	</div>

	<div class="col-xs-12 bar-status-history padding-top-10">
		<div>
			<div class="pull-left">
				Registration Date :
				<fmt:formatDate value="${bankUser.activateDate}" pattern="dd MMMM yyyy" />
				<c:if test="${not empty bankUser.terminateDate }">
	    / Termination Date : <fmt:formatDate value="${bankUser.terminateDate}" pattern="dd MMMM yyyy" />
				</c:if>
			</div>
			<a href="#" class="btn btn-primary pull-right">Status History</a>
		</div>
	</div>
	<div class="form-body">
	<div class="padding-10 col-xs-12">
		<div class="h3">Bank User Information</div>

		<div class="row static-info">
			<div class="col-md-5 name">Full Name</div>
			<div class="col-md-7 value">${bankUser.firstName}</div>
		</div>
		<div class="row static-info">
			<div class="col-md-5 name">Role:</div>
			<div class="col-md-7 value">${bankUser.role.value}</div>
		</div>
		<div class="row static-info">
			<div class="col-md-5 name">Email:</div>
			<div class="col-md-7 value">${bankUser.email}</div>
		</div>
		
		<div class="form-group">
      <div class="col-sm-offset-2 col-sm-10">
         <button type="submit" class="btn btn-default">Suspend Bank User</button>
      </div>
   </div>
   </div>
   </div>
   </form>






















 