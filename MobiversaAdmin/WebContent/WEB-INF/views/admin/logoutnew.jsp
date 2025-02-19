<%@ page import="net.tanesha.recaptcha.ReCaptcha"%>
<%@ page import="net.tanesha.recaptcha.ReCaptchaFactory"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js"
	type="text/javascript"></script>
<script
	src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"
	type="text/javascript"></script>
<%@page session="true"%>
<html class="no-js" lang="">


<head>
<link rel="icon" type="image/gif" sizes="16x16" href="${pageContext.request.contextPath}/resourcesNew/img/newMobiMIcon.png"/>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge">
<title>Mobi</title>
<meta name="description" content="...">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="mobile-web-app-capable" content="yes">
<sec:csrfMetaTags />
<script
	src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>







<script type="text/javascript">
	history.pushState(null, null, "");
	window.addEventListener('popstate', function() {
		history.pushState(null, null, "");
	});
</script>





<!-- CSS -->


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourcesNew/css/main.css">









<script type="text/javascript">
	function submitform() {
		document.formId.submit();
	}
</script>
<script language="javascript" type="text/javascript">
    function submitDetailsForm() {
    //alert("submit logout form");
       document.getElementById("formId").submit();
    }
</script>




<link
	href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700'
	rel='stylesheet' type='text/css'>

</head>



<body class="loginBg" onload="return submitDetailsForm();" id="body" name="body">
	

		<form
			action="${pageContext.request.contextPath}/j_spring_security_logout"
			method="post" id="formId" name="formId">
			
			 
		</form>

		

</body>
</html>