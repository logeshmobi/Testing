<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<form action="/admin/user/detail/${item.id}" method="post">

<div class="col-xs-12">
		<ul class="nav nav-pills nav-wizard">
			<li class=""><a href="#" data-toggle="tab">Bank User Detail</a>
				<div class="nav-arrow"></div></li>
			<li class=""><div class="nav-wedge"></div> <a href="#">Review And Confirm</a></li>
			<li class="active"><div class="nav-wedge"></div> <a href="#">All Done</a></li>
         </ul>
	</div>
	
	<div class="pull-left">
	<div>Your request to edit bank user for the following is successful</div><br>
	<!-- <div class="bar-switch-record col-xs-12 padding-top-10"> -->
	<div class="pull-left">BankUser Name:${bankUser.username}</div><br>
	<div class="pull-left">BankUser Role:${bankUser.role.value}</div>
</div>
	<div class="padding-10 col-xs-12">
        <div class="pull-left">
		<div class="h3">What you should do:</div>
		
		<a href="<%=AdminController.URL_BASE%>/bankUserDetails/${bankUser.id}" class="btn btn-primary">View BankUser Details</a>
		</div>
		</div>
	</form>
	
	



