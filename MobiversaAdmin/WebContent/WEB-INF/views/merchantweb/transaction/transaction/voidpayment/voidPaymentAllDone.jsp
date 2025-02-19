<%@page import="com.mobiversa.payment.controller.TransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
 <script src="/resourcesNew/js/jQuery-2.1.4.min.js" type="text/javascript"></script>
<form action="" method="post">
<div>
	<div class="col-xs-12 padding-10">
		<div class="h2">Transaction Detail</div>
	</div>
<div class="col-xs-12">
		<ul class="nav nav-pills nav-wizard">
			<li class="disabled"><div class="nav-wedge"></div> <a href="#">Review & Confirm</a></li>
			<li class="active"><div class="nav-wedge"></div> <a href="#">All Done</a></li>
         </ul>
	</div>
<div class="pull-left h2">All Done</div>
    <div class="pull-left">
	Your request is successful
	<div class="bar-switch-record col-xs-12 padding-top-10">
	<div class="pull-left">Transaction ID:${transaction.transactionId}</div>
    </div>
</div>
	<div class="padding-10 col-xs-12">
        <div class="pull-left">
		<div class="h3">What you should do:</div>
		<div class="form-control">
		<input type="button" value="Email">
		</div>
	</div>
</div>
</div>
</form>