<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@page import="com.mobiversa.payment.controller.ForgotPasswordController"%>
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
<link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>


	 <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/styleLogin.css" rel="stylesheet">
	     <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/style.css" rel="stylesheet">
     
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/dashboard1.css" rel="stylesheet">
 <style>


/* .loginCard{
margin-top:8px !important;
} */
</style>

<script type="text/javascript">
window.history.replaceState({}, document.title, "/MobiversaAdmin/" + "forgotpwd/");

</script>
<script type="text/javascript">


function load()
{
	
 //$('#testing').click(function(){
	 
	
      	swal({
      		title: "Are you sure? you want to reset the password",
      		text: "it will be changed..!",
      		type: "warning",
      		showCancelButton: true,
      		confirmButtonText: "Yes, change it!",
      		cancelButtonText: "No, cancel!",
      		closeOnConfirm: false,
      		closeOnCancel: false,
      	  /*  confirmButtonClass: 'btn btn-success',
      	  cancelButtonClass: 'btn btn-danger', */
      		
      	}, function(isConfirm) {
      		if (isConfirm) {
      			
      			if (validateForm()) {
      			
       			alert("final submit")
       			$("#myform").submit();
      			}
      			
      		} else {
      			// swal("Cancelled", "Your Merchant Promotion details not added", "error"); 
      			 var url = "${pageContext.request.contextPath}/admmerchant/list/1"; 
      			$(location).attr('href',url);
      			//return true;
      		}
      	});
     // });
 
}
function emailCheck(str) {
		var at="@"
var dot="."
var lat=str.indexOf(at)
var lstr=str.length
var ldot=str.indexOf(dot)
// check if '@' is at the first position or at last position or absent in given email 
if (str.indexOf(at)==-1 || str.indexOf(at)==0 ||
        str.indexOf(at)==lstr){
   alert("Invalid E-mail ID")
   return false
}
   // check if '.' is at the first position or at last position or absent in given email
if (str.indexOf(dot)==-1 || str.indexOf(dot)==0 ||
        str.indexOf(dot)==lstr){
    alert("Invalid E-mail ID")
    return false
}
        // check if '@' is used more than one times in given email
if (str.indexOf(at,(lat+1))!=-1){
    alert("Invalid E-mail ID")
    return false
 }
   // check for the position of '.'
 if (str.substring(lat-1,lat)==dot || 
         str.substring(lat+1,lat+2)==dot){
    alert("Invalid E-mail ID")
    return false
 }
         // check if '.' is present after two characters from location of '@'
 if (str.indexOf(dot,(lat+2))==-1){
    alert("Invalid E-mail ID")
    return false
 }

		 // check for blank spaces in given email
 if (str.indexOf(" ")!=-1){
    alert("Invalid E-mail ID")
    return false
 }
  		 return true					
}
function validateForm(){ 
	
	alert("entering")
	var emailID = document.getElementById("email");
	
	alert("emailID" +emailID.value)
	if ((emailID.value==null)||(emailID.value=="")){
		alert("Please Enter your Email ID")
	
	// set cursor to the textbox provided for email entry
		emailID.focus()
		return false
    }
	if (emailCheck(emailID.value)==false){
		emailID.value=""
		emailID.focus()
		return false
	}else{
		return true;
	}
	
}
</script>
</head>
<body>
   
 <div class="container-fluid">     
    <div align="center">
    <div class="row">
  <div class="col s12">
      <div class="card border-radius">
        <div class="card-content padding-card">
          <div class="d-flex align-items-center">
        <h2>Reset Your Password</h2></div>
         <div class="d-flex align-items-center">
        <p>
        Please enter your login email, we'll send a new random password to your inbox:
        </p></div>
         
        <form method="post" id="myform" name="myform" action="forgotpwd/resetByPwdByMailId">
        
        <div class="row">
   			<div class="input-field col s12 m3 l3">
   			<!-- <label for="Business Name">Email</label> -->
   			<input type="text" placeholder="Email" name="email" id="email" size="20">
   			</div>
   				<div class="input-field col s12 m3 l3">
   				<button class="btn btn-primary"  onclick=" return load()" type="button" >Send new password</button>
   				</div>
   			</div>
        </form>
        </div></div></div></div></div>
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