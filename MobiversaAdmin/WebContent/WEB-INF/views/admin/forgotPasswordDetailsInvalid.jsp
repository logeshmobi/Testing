<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
<%@page session="true"%>
<html class="no-js" lang="">
 <head>
 <link rel="icon" type="image/gif" sizes="16x16" href="${pageContext.request.contextPath}/resourcesNew/img/newMobiMIcon.png"/>
 
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">		
				
	<meta name="description" content="The fastest growing online payment service in Malaysia. Easy to View Transaction Details">	
	<meta name="title" content="Mobiversa | Online Payment Service | Mobile Payment Malaysia">	
	<meta http-equiv="X-UA-Compatible" content="IE=edge">		
	<meta charset="UTF-8">		
	<title>Mobi</title>

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

	 <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/styleLogin.css" rel="stylesheet">
	     <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/style.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/dashboard1.css" rel="stylesheet">
 <style>


.backBtn{
    display: table !important;
    border-radius:10px !important;
    margin: auto !important;
} 
.btn.btn-primary{
 border-radius: 0px !important;
}
</style>

</head>
<body class="login-bg">
   
 <div class="container">     
  
    <div style="display: table;"  class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
        
         <div class="d-flex align-items-center">
        <h3>
        ${statusMsg}
        <h3></div>
        
        <div class="row">
			<div class="input-field col s12 m12 l12 ">
			<form action = "/MobiversaAdmin">
				<button type="submit" class="btn btn-primary backBtn">Back</button>
			</form>
			</div>
			</div>
         
        
        </div></div></div></div>
    </div>
     

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