<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<form action="###" method="post">
<div>
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">Your are Currently viewing ADID: ${bankUser.username }</div>
		<div class="pull-right">
			<a href="<%=AdminController.URL_BASE%>/user/detail/${bankUser.id}" class="btn btn-default">View Bank User Details</a>
			<div class="clearfix"></div>
		</div>
	</div>

<div class="col-xs-12 padding-10">
		<div class="h2">Suspended Bank User</div>
	</div>

	<div class="col-xs-12">
		<ul class="nav nav-pills nav-wizard">
			<li class="disabled"><a href="#" data-toggle="tab">Suspension Detail</a>
				<div class="nav-arrow"></div></li>
		<li class="active"><div class="nav-wedge"></div> <a href="#">All Done</a></li>
        </ul>
	</div>
   <div class="form-body>"> 
 <div class="form-control padding-top-10">
 <div class="pull-left">
 ALl Done</div></div>

<div class="pull-left">
Your suspension request has been successfully submitted</div><br>
<div class="bar-text-area col-md-12 padding-top-10">
		<div class="pull-left">
		Effective Date:${bankUserStatusHistory.createdDate}<br>
			Reason:${bankUserStatusHistory.reason}<br>
		    description:${bankUserStatusHistory.description}<br>
		</div>
 </div>
<div class="padding-10 col-xs-12">
        <div class="pull-left">
		<div class="h3">What you should do:</div>
		<div class="form-control">
		<input type="button" value="Email"></div>
		</div>
	</div>
	</div>
	</form>
    
    
