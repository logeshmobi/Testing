
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
<%@page session="true"%>
<html class="no-js" lang="">
 <head>
 <link rel="icon" type="image/gif" sizes="16x16" href="${pageContext.request.contextPath}/resourcesNew/img/apple-icon-114x114.png"/>
 
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">		
				
	<meta name="description" content="The fastest growing online payment service in Malaysia. Easy to View Transaction Details">	
	<meta name="title" content="Mobiversa | Online Payment Service | Mobile Payment Malaysia">	
	<meta http-equiv="X-UA-Compatible" content="IE=edge">		
	<meta charset="UTF-8">		
	<title>Mobiversa</title>

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
 <!-- CSS -->
 <link rel="stylesheet"
	href="${pageContext.request.contextPath}/resourcesNew/css/main.css">
 <style>
#txtCompare1 {
	font: 25px verdana, arial, sans-serif;
	font-style: italic;
	color: #000000;
	background-color: #FFFFFF;
	padding: 4px; -moz-border-radius : 4px; -webkit-border-radius : 4px;
	border-radius : 4px; float : left;
	/* margin-top: 30px;
	margin-left: 4px; */
	cursor: pointer;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
	float: left;
}
#CaptchaDiv {
	font: 25px verdana, arial, sans-serif;
	font-style: italic;
	color: #000000;
	background-color: #FFFFFF;
	padding: 4px; -moz-border-radius : 4px; -webkit-border-radius : 4px;
	border-radius : 4px; float : left;
	/* margin-top: 30px;
	margin-left: 4px; */
	cursor: pointer;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
	float: left;
}
#txtCompare2 {
	font:25px verdana, arial, sans-serif;
	font-style: italic;
	color: #000000;
	background-color: #FFFFFF;
	padding: 4px; -moz-border-radius : 4px; -webkit-border-radius : 4px;
	border-radius : 4px;
	text-align: center;
	-moz-border-radius: 4px;
	-webkit-border-radius: 4px;
	border-radius: 4px;
}
</style>
 <script lang="JavaScript">
	$('CaptchaDiv').click(function() {

		GenerateCaptcha();
	});

	/* function GenerateCaptcha() {

		//alert("check captcha:");
		var alphanum = new Array('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
				'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
				'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
				'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', '1', '2', '3', '4', '5',
				'6', '7', '8', '9', '0');

		var a = alphanum[Math.floor(Math.random() * alphanum.length)] + '';
		var b = alphanum[Math.floor(Math.random() * alphanum.length)] + '';
		var c = alphanum[Math.floor(Math.random() * alphanum.length)] + '';
		var d = alphanum[Math.floor(Math.random() * alphanum.length)] + '';
		var e = alphanum[Math.floor(Math.random() * alphanum.length)] + '';
		var f = alphanum[Math.floor(Math.random() * alphanum.length)] + '';
		var g = alphanum[Math.floor(Math.random() * alphanum.length)] + '';

		var captchaCode = a + ' ' + b + ' ' + ' ' + c + ' ' + d + ' ' + e + ' '
				+ f + ' ' + g;

		//document.getElementById("txtCaptcha").value = captchaCode
		document.getElementById("CaptchaDiv").innerHTML = captchaCode
	} */

	function emptyCheck() {

		var str1 = removeSpaces(document.getElementById('txtCompare1').value);

		var str2 = removeSpaces(document.getElementById('txtCompare2').value);

		if (str2 == null && str2 == '') {
			alert("please enter Security Code");
			document.getElementById("txtCompare2").focus();
			return false;
		}
	}
	/* Validating Captcha Function */
	function ValidCaptcha() {
		//alert("captcha matches1212121212:");
		var str1 = removeSpaces(document.getElementById('txtCompare1').value);

		var str2 = removeSpaces(document.getElementById('txtCompare2').value);

		if (str2 == null && str2 == '') {
			alert("please enter Security Code");
			//document.getElementById("txtCompare2").focus();
			return false;
		}

		else if (str2 == null || str2 != str1) {
			alert("Wrong Security Code ");
			/* document.getElementById("txtCompare2").value = '';
			document.getElementById("txtCompare2").focus(); */
			return false;
		} else if (str1 == str2) {
			//alert("Captcha Matches:");
			return true;
		}
	}

	/* Remove spaces from Captcha Code */
	function removeSpaces(string) {
		return string.split(' ').join('');
	}

	function test() {
		$('#txtCompare1').bind("cut copy paste", function(e) {
			e.preventDefault();
			alert("cut,copy & paste not allowed");
		});
		
		$('#CaptchaDiv').bind("cut copy paste", function(e) {
			e.preventDefault();
			alert("cut,copy & paste not allowed");
		});

	}
</script>
<script type="text/javascript">
	// DO GET RegenerateCaptcha
	//${_csrf.parameterName}=${_csrf.token}

	/* var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
	var csrfHeader = $("meta[name='_csrf_header']").attr("content");
	var csrfToken = $("meta[name='_csrf']").attr("content"); */

	function regenerateCaptcha() {
		//alert("check captcha");
		
		var csrfParameter = $("meta[name='_csrf_parameter']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		var csrfToken = $("meta[name='_csrf']").attr("content");

		//var txtCompare1 = document.getElementById("txtCompare1").value;
		var txtCompare1 = removeSpaces(document.getElementById('txtCompare1').value);
		//alert("check authent: " + txtCompare1 + " " + csrfToken);
		var data = {};
		data[csrfParameter] = csrfToken;
		data[txtCompare1] = txtCompare1;
		//alert("check captcha");
		$
				.ajax({
					type : "GET",
					
					contentType : "application/json",
					url : window.location + "generate/regenerateCaptcha",
					//data : JSON.stringify(myJSON),
					/* data : {
						"txtCompare1" : $("#txtCompare1").val(),
						
					}, */
					data : data,
					//contentType : "application/json; charset=utf-8",
					ContentType: "application/x-www-form-urlencoded; charset=UTF-8",
					dataType : "json",
					success : function(result) {
						//alert("status of Captcha:...."+result.status);
						if (result.status == "Done") {
							//alert(result.data);
							document.getElementById("txtCompare1").value = result.data;
							document.getElementById("CaptchaDiv").innerHTML=result.data;
							document.getElementById("txtCompare2").value = '';

						} else if (result.status == "errorMsg") {

							return false;

						}
					},
					error : function(data, status, er) {
						alert("error: " + data + " status: " + status + " er:"
								+ er);
						window.location.href = '${pageContext.request.contextPath}/auth/loginerror';
						form.submit;
						return true;
					}
				});
	}
</script>
<script type="text/javascript">
	// DO GET
	function ajaxGet() {
	
		//alert("check captcha");
		$.ajax({
			type : "GET",
			url : window.location + "generate/generateCaptcha",
			ContentType: "application/x-www-form-urlencoded; charset=UTF-8",
			success : function(result) {
				if (result.status == "Success") {
					//alert(result.data);
					document.getElementById("txtCompare1").value = result.data;
					document.getElementById("CaptchaDiv").innerHTML=result.data;

				} else {
					$("#txtCompare1").html("<strong>Error</strong>");
					console.log("Fail: ", result);
				}
			},
			error : function(data, status, er) {
				alert("error: " + data + " status: " + status + " er:" + er);
				/* window.location.href ='${pageContext.request.contextPath}/auth/loginerror' ;
					form.submit; */
				return true;
			}
		});
	}
</script>
<script type="text/javascript">
	
    /* var d = new Date();
    var month = d.getMonth()+1;
    var year=d.getFullYear();
    var date=d.getDate()-1;
    
    var today=date+"/"+month+"/"+year;
    
    time=d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
    today=date+"/"+month+"/"+year+" "+time;
   alert(today); */
	
	

	function deletegeneratedCaptcha() {
		//alert("enter for delete captcha after submit")
		$.ajax({
					type : "GET",
					contentType : "application/json",
					url : window.location + "generate/deleteValidatedCaptcha",
					//data : JSON.stringify(myJSON),
					data : {
						"txtCompare1" : $("#txtCompare1").val(),
						//"txtCompare2" : today,

					},
					contentType : "application/json; charset=utf-8",

					dataType : "json",
					success : function(result) {
						//alert("status of Captcha:...."+result.status);
						if (result.status == "Success") {
							//alert(result.data);
							//document.getElementById("txtCompare1").value = result.data;
							return true;
						} else {

							return false;

						}
					},
					error : function(result, status, er) {
						alert("error: " + data + " status: " + status + " er:"
								+ er);
						window.location.href = '${pageContext.request.contextPath}/auth/loginerror';
						form.submit;
						return true;
					}
				});

	}
</script>
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

<script language="javascript" type="text/javascript">
$("#btnValid").on('click', function(){
alert("jdkfjdkfj");
    ValidateCaptcha().done(function(data){
         if(data.status == "Success")
            return true;
         else
			return false;
    })
});

</script>
<script language="javascript" type="text/javascript">

function ValidatehiddenCaptcha(){
//alert("ddsfddfdffdsdffsd");
var result = document.getElementById("hiddenCaptcha").value;
//alert("ddsfddfdffdsdffsd"+ result);

	if(result != "false"){
		//alert("true");
	 return true;
	}else{
			//alert("flase");
		return false;
	}

}
</script>
<script language="javascript" type="text/javascript">

    function testing() {
    
   // alert("submit logout form");
    /*   var tt = ValidateCaptcha(); */
    
    
    
    var tt = ValidateCaptcha();
      //alert("tt : "+tt);
     /*  if(tt){
      	alert("submit logout form ss");
      	return true;
      }else{
      	alert("submit logout form ff");
		return false;      
      } */
      var ttt = ValidatehiddenCaptcha();
     // alert("ttrrr : "+ttt);
      if(ttt){
      	//alert("submit logout form sss");
      	return true;
      }else{
      	//alert("submit logout form fff");
		return false;      
      }
    }
</script>


 <script type="text/javascript">


function forgotPwd()
{
	
	alert("clicking forgot password");


		window.location = "${pageContext.request.contextPath}/forgotpwd/forgotPwdBymerchant";
		form.submit;
		return true;
   
};
</script> 

<script language="javascript" type="text/javascript">
function testing1()
{
//alert("status of Captcha:....testing1()");
//var t=true;
var result = false;
$.ajax({
						type : "GET",
						contentType : "application/json",
						url : window.location + "generate/validateCaptcha",
						//data : JSON.stringify(myJSON),
						data : {
							"txtCompare1" : removeSpaces(document.getElementById('txtCompare1').value),
							 "txtCompare2" : $("#txtCompare2").val(), 
							 "username" : $("#username").val() 
						},
						//data : myJSON,
						// data: { data: myJSON },

						/* data: JSON.stringify({
						txtCompare1 : $("#txtCompare1").val(),
						txtCompare2 : $("#txtCompare2").val()
						}), */
						//data: "txtCompare1=" + $("#txtCompare1").val() + "&txtCompare2=" + $("#txtCompare1").val(),
						contentType : "application/json; charset=utf-8",
						//mimeType : 'application/json',
						//data : formData,
						async: false,
						dataType : "json",
						success : function(result)
						 {
							//alert("status of Captcha:...."+result.status);
							//var result = false;
							if (result.status == "Success") 
							{
								//alert("check succes"+result.data);
								//deletegeneratedCaptcha();
								//result = true;
								//t = true;
								document.getElementById("hiddenCaptcha").value = true;
								//alert("check succes : "+document.getElementById("hiddenCaptcha").value);
								return true;

							} 
							 else if (result.status == "errorMsg") 
							 {

								alert("Invalid Credentials...!");
								document.getElementById("hiddenCaptcha").value = false;
								var username = document.getElementById("useranme").value;
								var password = document.getElementById("password").value;
								window.location = '/MobiversaAdmin/auth/login/error';
								//"${pageContext.request.contextPath}/j_spring_security_logout
								//window.location = '/MobiversaAdmin/j_spring_security_logout';
								//submitDetailsForm();
								
								//window.location = "logoutnew.jsp";
								//result = false;
								//t = false;
								//alert("errorMsg  : "+document.getElementById("hiddenCaptcha").value);
								return false;

							}
							else if (result.status == "Fail") 
							 {

								alert("Empty Fields does not allow here...!");
								document.getElementById("hiddenCaptcha").value = false;
								window.location = '/MobiversaAdmin/auth/login/errorCaptcha';
								//"${pageContext.request.contextPath}/j_spring_security_logout
								//window.location = '/MobiversaAdmin/j_spring_security_logout';
								//submitDetailsForm();
								
								//window.location = "logoutnew.jsp";
								//result = false;
								//t = false;
								//alert("errorMsg  : "+document.getElementById("hiddenCaptcha").value);
								return false;

							}
							
							return result;
						},
						error : function() {
							//alert("Invalid Credentials");
							document.getElementById("hiddenCaptcha").value = false;
							//result = false;
							return false;
						}
					});
//alert("result 1234567890: "+document.getElementById("hiddenCaptcha").value);
return result;

}
</script>


<script type="text/javascript">
	// DO POST
	function ValidateCaptcha() {
		//alert("validating captcha");

		/* var obj = {
			txtCompare1 : $("#txtCompare1").val(),
			txtCompare2 : $("#txtCompare2").val()
		};
		var myJSON = JSON.stringify(obj); */

		/* alert("validate captcha " + $("#txtCompare1").val() + " "
					+ $("#txtCompare2").val()); */

		var captcha1 = removeSpaces(document.getElementById('txtCompare1').value);
		var captcha2 = document.getElementById("txtCompare2").value;

		//alert(captcha1 == captcha2);
		if (captcha2 == null && captcha2 == '') 
		{
			alert("please enter Security Code");
			document.getElementById("txtCompare2").focus();
			return false;
		} 
		else if (captcha2 == null || captcha1 != captcha2) 
		{
			alert("Wrong Security Code ");
			document.getElementById("txtCompare2").value = '';
			document.getElementById("txtCompare2").focus();
			return false;
		}
		//alert(obj)
		//alert("url: "+window.location + "generate/generateCaptcha1");
		else if (captcha1 == captcha2)
		 {
			//alert("capthca matched");
			//var t = true;
			
			var t = testing1();
			
			//alert("var r"+t);
			return t;
		}
	}
</script>
<script type="text/javascript">
 

        function onclickTab(e) {
            var KeyID = e.keyCode;
            if (KeyID == 9) {
               
                txtCompare2.focus();
                return false;
            }
        }
 
    </script>


<link
	href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:400,700'
	rel='stylesheet' type='text/css'>

</head>



<body class="loginBg" onload="return ajaxGet();" id="body" name="body"  oncontextmenu="return false">
	<div id="div1" name="div1"></div>
	<section class="login-content">

		<div class="login-box">


			<%--  <div align="center login-title"><a href="#"><img src="${pageContext.request.contextPath}/resourcesNew/img/mobiversalogo.png" align="top" draggable="false" width="210" height="56"></a></div> 
			<div class="login-form">	 --%>
			<%-- <form data-parsley-validate name='loginForm' class="login-form"
				action="<c:url value='/j_spring_security_check' />?sdfweirukwioer=${_csrf.token}" method='POST'
				name="form1" onsubmit="return ValidateCaptcha();"> --%>
				
				
				<form data-parsley-validate name='loginForm' class="login-form"
				action="<c:url value='/j_spring_security_check' />?sdfweirukwioer=${_csrf.token}" method='POST'
				name="form1" onsubmit="return ValidatehiddenCaptcha();">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}">
				<!-- onsubmit ="return checkPassword(this);" -->

				<!--  <h3 class="login-head">Sign in to Mobiversa</h3> -->

				<!-- <p>Enter your details below</p> -->

				<div class="login-head">
					<a href="https://www.mobiversa.com/">
					<%-- <img
						src="${pageContext.request.contextPath}/resourcesNew/img/mobiversalogo.png"
						align="top" style="width: 80%; margin-left: -20px;"></a> --%>
						<%-- <img
						src="${pageContext.request.contextPath}/resourcesNew/img/mobiLogo_v1.0.png"
						align="top" style="width: 80%; margin-left: -20px;"> --%>
						<img
						src="${pageContext.request.contextPath}/resourcesNew/img/mobiWhite.png"
						align="top" style="width: 79px;height:30px; margin-left: -20px;">
						
						
						</a>
						
						
				</div>
				<div class="input-group form-group">
					<span class="input-group-addon"><i class="fa fa-envelope-o"></i></span>
					<input type="text" class="form-control" id="username"
						placeholder="UserName" name="username"><span
						class="input-group-btn"> </span>
				</div>

				<div class="input-group form-group">
					<span class="input-group-addon"><i class="fa fa-lock fa-lg"></i></span>
					<input type="password" class="form-control" id="password"
						placeholder="Password" name="password" onkeydown="return onclickTab(event);"><span
						class="input-group-btn"> </span>
				</div>
				
                 <label for ="CaptchaDiv"  id="CaptchaDiv" class="form-control simple-form-control" 
                 onclick="return test();" style="font-weight:bold; font-family:Modern"></label>
              
				<input type="hidden" align="center"
					class="form-control simple-form-control" id="txtCompare1" 
					name="txtCompare1" placeholder="" readonly onclick="return test();"
					style="align: center; text-align: center; font:25px verdana, arial, sans-serif; font-style: italic;border-radius:10px;" />

				<input type="text" align="center" 
					class="form-control simple-form-control" id="txtCompare2" 
					name="txtCompare2" placeholder="Enter Captcha" required
					style="align: center; text-align: center; font: 25px verdana, arial, sans-serif; font-style: italic;"
					
					/>

				

				<div align="center">
					<a href="#">To prevent spam and automated <br>extraction
						of data from websites
					</a>
				</div>

				<!-- <div class="form-group btn-container">
							<button type="submit" name="submit" id="btnValid" class="btn btn-primary btn-block"  onclick="return(ValidCaptcha());" >Log In</button>	<br>
						</div> -->


				<div class="form-group btn-container">
					<button type="submit" name="submit" id="btnValid"
						class="btn btn-primary btn-block"
						onclick="return testing()">Log In</button>
					<br>
				</div>
		 	<!-- 	<div class="form-group btn-container">
					<button type="submit" id="btnforgotpwd" name="btnforgotpwd" 
						class="btn btn-default btn-block" value="forgotpwd"
						 onclick="return forgotPwd();"
							>Forgot Password</button>
					<br>
				</div> -->  

				<div class="form-group btn-container">
					<button type="button" id="btnrefresh"
						class="btn btn-default btn-block" value="recaptcha"
						name="txtCompare1" onclick="return regenerateCaptcha();"
						style="align: center">Regenerate Captcha</button>
				</div>

				<p color="red" id="error" value=""></p>
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
					
					<input type="hidden" name="hiddenCaptcha" id="hiddenCaptcha"
					value="false" />
	

			</form>
			
			
			<!-- <a href="/forgot-password">Forgot Password?</a>  -->
			
			
			<!-- <a href="/ForgotPasswordController.java">Forgot Password</a> -->




			<!-- forget password page design start -->


			<!-- <form class="forget-form" action="">
          <h3 class="login-head">Forgot Password ?</h3>
          <div class="input-group form-group">
          	<span class="input-group-addon"><i class="fa fa-envelope-o"></i></span>
            <input class="form-control" type="text" placeholder="Email"><span class="input-group-btn">
          </div>
          <div class="form-group btn-container">
            <button class="btn btn-primary btn-block">RESET <i class="fa fa-unlock fa-lg"></i></button>
          </div>
          <div class="form-group mt-20">
            <p class="semibold-text mb-0"><a id="noFlip" href="#"><i class="fa fa-angle-left fa-fw"></i> Back to Login</a></p>
          </div>
        </form>	   -->
			<!-- end -->
		</div>

		<form
			action="${pageContext.request.contextPath}/j_spring_security_logout"
			method="post" id="formId" name="formId">
			
			 <!-- <li class="dropdown"><a class="dropdown-toggle" href="#" data-toggle="dropdown" role="button" 
			 aria-haspopup="true" aria-expanded="false"><i class="fa fa-power-off logout" aria-hidden="true"
			  id="link-logout">LogOut</i></a></li> -->
			  
			  <input type="hidden" name="link-logout" id="link-logout" value=""/>
				 
             
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />

		</form>

		<div class="col-md-12 text-center loginFooter">
			<p>Trusted by the world's smartest companies</p>
			<img
				src="${pageContext.request.contextPath}/resourcesNew/images/Google_Play_logo.png">
			<img
				src="${pageContext.request.contextPath}/resourcesNew/images/appstore.png">
			<%-- <img src="${pageContext.request.contextPath}/resourcesNew/images/paypal.png"> --%>

		</div>
	</section>





	<!-- JS -->


	<script
		src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew/js/essential-plugins.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew/js/bootstrap.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resourcesNew/plugins/pace.min.js"></script>

	<script
		src="${pageContext.request.contextPath}/resourcesNew/js/main.js"></script>


	<div class="visible-xs visible-sm extendedChecker"></div>


</body>
</html>