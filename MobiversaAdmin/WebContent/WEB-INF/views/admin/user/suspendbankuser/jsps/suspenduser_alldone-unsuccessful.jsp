<%@page import="com.mobiversa.payment.controller.AdminController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!-- form starts here -->
<form action="/addmerchantalldoneunsuccessful" method="post">
<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">Your are Currently viewing user: ${bankUser.username }</div>
		<div class="pull-right">
			<input name="search" type="button" value="Switch BankUser" class="btn btn-default" />
			<div class="clearfix"></div>
		</div>
	</div>
	<!-- Suspend bankuser block starts here -->
<div class="col-xs-12 padding-10">
		<div class="h2">Suspend BankUser</div>
	</div>
<div class="col-xs-12">
		<ul class="nav nav-pills nav-wizard">
			<li class="disabled"><a href="#" data-toggle="tab">Suspension Detail</a>
				<div class="nav-arrow"></div></li>
			<li class="active"><div class="nav-wedge"></div> <a href="#">All Done</a></li>
         </ul>
	</div>
	<div class="pull-left h2">Whoops...Smething is wrong!</div>
	<div>____________________________________________</div>
	<div class="pull-left">
	Your request is unsuccessful
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">ErrorCode:${bankUser.error}</b></div>
    </div>
	<div class="padding-10 col-xs-12">
        <div class="pull-left">
		<div class="h3">What you should do:</div>
		<div class="form-control">
		<input type="button" value="Try Again"></div>
		</div>
	</div>
	<!-- suspend bankuser block ends here -->
</div>
<!-- form ends here -->
</form>







