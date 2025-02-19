<%@page import="org.springframework.web.context.request.RequestScope"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page import="com.mobiversa.payment.service.AdminService,com.mobiversa.common.bo.AuditTrail"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html lang="en"> 
<head>
 <link rel="icon" type="image/gif" sizes="16x16" href="${pageContext.request.contextPath}/resourcesNew/img/newMobiMIcon.png"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mobi</title>    
     <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
     <!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script> -->
     <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script> 
  
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="mobile-web-app-capable" content="yes">
<sec:csrfMetaTags />
<script src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
 <script type="text/javascript">
	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");
	});
</script>

	<link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist/dist/chartist.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/c3/c3.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/styleLogin.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/style.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/dashboard1.css" rel="stylesheet">
	  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.date.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.time.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/dataTable/dataTables.bootstrap4.min.css">	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resourcesNew1/dataTable/dataTables.material.min.css">
	<link href='${pageContext.request.contextPath}/resourcesNew1/select2/dist/css/select2.min.css' rel='stylesheet' type='text/css'>
	
	<link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>
		
 <style>


/* .loginCard{
margin-top:8px !important;
} */
</style>
<script type="text/javascript">

/* $(document).ready(function() {
	$("#myform").submit(function(event) { 
		alert("validate form");
		//alert(validateForm());

		if (validateForm()) {
		 
			
		 var r = confirm("Do You Want to reset the password..?");
		
			 if (r == true) {
				
				return true;
				
			}  else {
				return false;
			}  


			

		} else {
			return false;
		} 

	  });
});  
 */

function load()
{
	 $("#myform").submit();
}

</script>
</head>
<body  class="login-bg">
   
 <div class="container">     
  
    <div style="display: table;margin: auto !important;" class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
          <div class="d-flex align-items-center">
        <h2>Reset Your Password</h2></div>
         <div class="d-flex align-items-center">
        <p>
        Please enter your user name, we'll send a OTP to your registered Mobile Number / Email:
        </p></div>
         
        <form method="post" id="myform" name="myform" action="${pageContext.request.contextPath}/forgotpwd/forgetPwdByUserName">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
        <div class="row">
   			<div class="input-field col s12 m4 l6">
   			<!-- <label for="Business Name">Email</label> -->
   			<input type="text" placeholder="User Name" name="username" id="username" size="20">
   			</div>
   				<div class="input-field col s12 m4 l6">
   				<button class="btn btn-primary" type="button" onclick ="load()">Submit</button>
   				</div>
   			</div>
        </form>
        </div></div></div></div>

   </div>
     
<script src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
<%-- <script src="${pageContext.request.contextPath}/resourcesNew/assets/js/jquery-1.11.3.min.js"></script> --%>
<%-- <script data-cfasync="false" src="${pageContext.request.contextPath}/resourcesNew1/cdn-cgi/scripts/5c5dd728/cloudflare-static/email-decode.min.js"></script> --%>
<script src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/jquery/dist/jquery.min.js"></script>
 <script src="https://canvasjs.com/assets/script/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/dist/js/canva.js"></script>
<script src="https://canvasjs.com/assets/script/jquery.canvasjs.min.js"></script>

<script src="${pageContext.request.contextPath}/resourcesNew1/dist/js/materialize.min.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/perfect-scrollbar/dist/js/perfect-scrollbar.jquery.min.js"></script>
 
<script src="${pageContext.request.contextPath}/resourcesNew1/dist/js/app.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/dist/js/app.init.js"></script> 
<script src="${pageContext.request.contextPath}/resourcesNew1/dist/js/custom.min.js"></script>
 
<script src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist/dist/chartist.min.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.min.js"></script>
<!--c3 JavaScript -->
<script src="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/c3/d3.min.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/c3/c3.min.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chart.js/dist/Chart.min.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/dist/js/pages/dashboards/dashboard1.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/sparkline/sparkline.js"></script>


<script src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/compressed/picker.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/compressed/picker.date.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/compressed/picker.time.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/compressed/legacy.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/moment/moment.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/assets/libs/daterangepicker/daterangepicker.js"></script>
<script src="${pageContext.request.contextPath}/resourcesNew1/dist/js/pages/forms/datetimepicker/datetimepicker.init.js"></script>


<script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew1/dataTable/jquery.dataTables.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resourcesNew1/dataTable/dataTables.material.min.js"></script>
<script
				src="${pageContext.request.contextPath}/resourcesNew/js/circle-progress.js"></script>
<script src='${pageContext.request.contextPath}/resourcesNew1/select2/dist/js/select2.min.js' type='text/javascript'></script>
<%-- <script src="${pageContext.request.contextPath}/resourcesNew/js/plugins/select2.min.js"></script> --%>

			<%-- <script
				src="${pageContext.request.contextPath}/resourcesNew/js/plugins/sweetalert.min.js"></script> --%>


<script
		src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew/js/essential-plugins.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew/js/main.js"></script>


	<div class="visible-xs visible-sm extendedChecker"></div>
</body>
</html>