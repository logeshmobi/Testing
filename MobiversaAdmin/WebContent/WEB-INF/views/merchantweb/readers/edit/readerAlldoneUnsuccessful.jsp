<%@page import="com.mobiversa.payment.controller.MerchantWebReaderController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>


<div class="container-fluid">  
<div class="row">
<div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        
         <div class="d-flex align-items-center">
         <h3>Edit Reader Details</h3>
         </div>
<form action="" method="post">
<div class="col-xs-12">
		<ul class="nav nav-pills nav-wizard">
			<li class="disabled"><a href="#" data-toggle="tab">Reader Detail</a>
				<div class="nav-arrow"></div></li>
			<li class="disabled"><div class="nav-wedge"></div> <a href="#">Review & Confirm</a></li>
			<li class="active"><div class="nav-wedge"></div> <a href="#">All Done</a></li>
         </ul>
	</div>
	<div class="pull-left h2">Whoops...Smething is wrong!</div>
	<div class="pull-left">
	<div>Your request is unsuccessful</div><br>
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">Error Code: ${reader.error }</div>
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
</div></div></div></div></div>

