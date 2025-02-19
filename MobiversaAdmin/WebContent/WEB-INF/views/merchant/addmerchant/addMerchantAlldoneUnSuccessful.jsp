<%@page import="com.mobiversa.payment.controller.MerchantWebController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script>
<script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "admmerchant/");

</script>
<body> 

<form action="/addmerchantalldoneunsuccessful" method="post">
<div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
<div class="col-xs-12 padding-10">
		<div class="h2">Add a Mobile User</div>
	</div>
<div class="col-xs-12">
		<ul class="nav nav-pills nav-wizard">
			<li class="disabled"><a href="#" data-toggle="tab">Mobile User Detail</a>
				<div class="nav-arrow"></div></li>
			<li class="disabled"><div class="nav-wedge"></div> <a href="#">Review & Confirm</a></li>
			<li class="active"><div class="nav-wedge"></div> <a href="#">All Done</a></li>
         </ul>
	</div>
	<div class="pull-left h2">Whoops...Something is wrong!</div>
	<div>____________________________________________</div>
	<div class="pull-left">
	<div>Your request is unsuccessful</div><br>
	<div class="bar-switch-record col-xs-12 padding-top-10">
		<div class="pull-left">ErrorCode:${merchant.error}</div>
    </div>
	<div class="padding-10 col-xs-12">
        <div class="pull-left">
		<div class="h3">What you should do:</div>
		<div class="form-control">
		<input type="button" value="Try Again"></div>
		</div>
	</div>
</div>
</div></div></div></div>
</form>
</body>