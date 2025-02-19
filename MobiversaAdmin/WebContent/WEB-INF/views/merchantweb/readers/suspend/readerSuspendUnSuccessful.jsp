<%@page import="com.mobiversa.payment.controller.MerchantWebReaderController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
<form action="###" method="post">
<div>
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">Your are Currently viewing terminal Serial no: ${reader.serialNumber }</div>
	</div>

<div class="col-xs-12 padding-10">
		<div class="h2">Reader Un-Suspended</div>
	</div>
<div class="col-xs-12">
		<ul class="nav nav-pills nav-wizard">
			<li class="disabled"><a href="#" data-toggle="tab">Details</a>
				<div class="nav-arrow"></div></li>
		<li class="active"><div class="nav-wedge"></div> <a href="#">All Done</a></li>
        </ul>
	</div>
    
 <div class="form-control padding-top-10">
 <div class="pull-left">
 Status:UnSuccessful</div></div>
 <div>_______________________________________________________________</div>
<div class="pull-left">
Your request is unsuccessful</div>
<div class="bar-text-area col-md-12 padding-top-10">
		<div class="pull-left">
		Error Code:${fail}
		</div>
 </div>
<div class="padding-10 col-xs-12">
        <div class="pull-left">
		<div class="h3">What you should do:</div>
		<div class="form-control">
		<input type="button" value="Try Again"></div>
		</div>
	</div>
	</div>
	</form>
    
    
