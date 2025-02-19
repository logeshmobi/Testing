<%@page import="com.mobiversa.payment.controller.MerchantWebReaderController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
<form action="###" method="post">
	<div>
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">Your are Currently viewing Reader Serial No: ${reader.serialNumber}</div>
	</div>

<div class="col-xs-12 padding-10">
		<div class="h2"> Reader Suspended</div>
	</div>

	<div class="col-xs-12">
		<ul class="nav nav-pills nav-wizard">
			<li class="disabled"><a href="#" data-toggle="tab">Details</a>
				<div class="nav-arrow"></div></li>
		<li class="active"><div class="nav-wedge"></div> <a href="#">All Done</a></li>
        </ul>
	</div>
    
 <div class=" padding-top-10">
 <!-- <div class="pull-left"> -->
 <b>Status:successful</b></div>
 
<div class="padding-top-10">
<!-- <div class="pull-left"> -->
Your request to suspend the Reader is successfull</div>
</div>
<div>
<div class="form-horizontal">

<div class="padding-top-10">
<div class="form-group">
	<div class="row static-info">
		<label class="col-lg-6 control-label">Reader Serial No:</label>
		<div class="col-md-4 value" >${reader.serialNumber }
		</div>
		</div>
		</div>
		<div class="form-group">
	<div class="row static-info">
		<label class="col-lg-6 control-label">Effective Date:</label>
		<div class="col-md-4 value" >${readerStatusHistory.createdDate}<br>
		</div>
		</div>
		</div>
		<div class="form-group">
	<div class="row static-info">
		<label class="col-lg-6 control-label">Reason:</label>
		<div class="col-md-4 value" >${readerStatusHistory.reason}<br>
		</div>
		</div>
		</div>
		<div class="form-group">
	<div class="row static-info">
		<label class="col-lg-6 control-label">Description:</label>
		<div class="col-md-4 value" >${readerStatusHistory.description}<br>
		</div>
		</div>
		</div>
		
 
 </div>
 </div>
 </div>
 
<div class="form-group"> 
<div style="float:right;">
 <input type="submit" class="btn btn-primary" value="Email"></div>
</div>
		
	</form>
    
    
