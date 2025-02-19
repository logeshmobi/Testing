<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js" type="text/javascript"></script>
<script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.js" type="text/javascript"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js" type="text/javascript"></script>
<%@page session="true" %>
<html class="no-js" lang="">
<head>
    <%-- <link rel="icon" type="image/gif" sizes="16x16" href="${pageContext.request.contextPath}/resourcesNew/img/newMobiMIcon.png"/> --%>

    <link rel="icon" type="image/gif" sizes="16x16"
          href="${pageContext.request.contextPath}/resourcesNew/img/newMobiMIcon.png"/>

    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <meta name="description"
          content="The fastest growing online payment service in Malaysia. Easy to View Transaction Details">
    <meta name="title" content="Mobiversa | Online Payment Service | Mobile Payment Malaysia">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta charset="UTF-8">
    <title>Mobi</title>
    <link rel="apple-touch-icon" sizes="57x57" href="assets/images/favicon/apple-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="60x60" href="assets/images/favicon/apple-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="72x72" href="assets/images/favicon/apple-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="76x76" href="assets/images/favicon/apple-icon-76x76.png">
    <link rel="apple-touch-icon" sizes="114x114" href="assets/images/favicon/apple-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="120x120" href="assets/images/favicon/apple-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="144x144" href="assets/images/favicon/apple-icon-144x144.png">
    <link rel="apple-touch-icon" sizes="152x152" href="assets/images/favicon/apple-icon-152x152.png">
    <link rel="apple-touch-icon" sizes="180x180" href="assets/images/favicon/apple-icon-180x180.png">
    <link rel="icon" type="image/png" sizes="192x192" href="assets/images/favicon/android-icon-192x192.png">
    <link rel="icon" type="image/png" sizes="32x32" href="assets/images/favicon/favicon-32x32.png">
    <link rel="icon" type="image/png" sizes="96x96" href="assets/images/favicon/favicon-96x96.png">
    <link rel="icon" type="image/png" sizes="16x16" href="assets/images/favicon/favicon-16x16.png">

    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/customloginn.css">

    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage" content="assets/images/favicon/ms-icon-144x144.png">
    <meta name="theme-color" content="#f1f5fa">

    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="mobile-web-app-capable" content="yes">
    <sec:csrfMetaTags/>
    <script src="${pageContext.request.contextPath}/resourcesNew/js/urlHide.js"></script>
    <script type="text/javascript">
        history.pushState(null, null, "");
        window.addEventListener('popstate', function () {
            history.pushState(null, null, "");
        });
    </script>
    <!--   rk -->
    <%-- 	 <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/styleLogin.css" rel="stylesheet"> --%>
    <%-- 	     <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/style.css" rel="stylesheet"> --%>


    <%--     <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/dashboard1.css" rel="stylesheet"> --%>


    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/bootstrap.min1.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resourcesNew1/dist/css/fonts/fonts.css">

    <!-- Global site tag (gtag.js) - Google Analytics -->
    <script async src="https://www.googletagmanager.com/gtag/js?id=UA-74242241-3"></script>

    <script>

        window.dataLayer = window.dataLayer || [];

        function gtag() {
            dataLayer.push(arguments);
        }

        gtag('js', new Date());
        gtag('config', 'UA-74242241-3');

    </script>
    <style>
        .login-btn {
            margin-top: 1rem !important;
        }
        .p-4 {
            padding: 1.5rem 1.5rem 1rem 1.5rem !important;
        }
        .form-control::placeholder {
            color: #858585 !important;
        }
        .form-control {
            color: #586570 !important;
            border: none;
        }


        /* .loginCard{
        margin-top:8px !important;
        } */
    </style>

    <!-- Captcha Style added on 03-02-22 -->

    <style>
		@import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');
		body {
			display: flex;
			justify-content: center;
			align-items: center;
			height: 100vh;
			margin: 0;
			background-color: #f9f9f9;
			font-family: "Poppins", sans-serif !important;
		}


        .pullee {
            width: 100%;
            appearance: none;
            border: 0px;
        }

        .pullee:active::-webkit-slider-thumb {
            appearance: none;
            transform: scale(1.1);
            cursor: -webkit-grabbing;
            cursor: -moz-grabbing;
        }

        .pullee:active::-moz-range-thumb {
            border: 0;
            transform: scale(1.1);
            cursor: -webkit-grabbing;
            cursor: -moz-grabbing;
        }

        .pullee:active::-ms-thumb {
            transform: scale(1.1);
            cursor: -webkit-grabbing;
            cursor: -moz-grabbing;
        }

        .pullee:focus {
            outline: none;
        }

        .pullee::-webkit-slider-thumb {
            margin-top: .2rem;
            padding: .8rem;
            appearance: none;
            display: block;
            width: 0.5rem;
            height: 0.5rem;
            border-radius: 50%;
            background-color: white;
            transform-origin: 50% 50%;
            transform: scale(1);
            transition: transform ease-out 100ms;
            cursor: -webkit-grab;
            cursor: -moz-grab;
        }

        .pullee::-moz-range-thumb {
            border: 0;
            display: block;
            width: 1.5rem;
            height: 1.5rem;
            border-radius: 50%;
            background: #fff;
            transform-origin: 50% 50%;
            transform: scale(1);
            transition: transform ease-out 100ms;
            cursor: -webkit-grab;
            cursor: -moz-grab;
        }

        .pullee::-ms-thumb {
            display: block;
            width: 1rem;
            height: 1rem;
            border-radius: 50%;
            background: #fff;
            transform-origin: 50% 50%;
            transform: scale(1);
            transition: transform ease-out 100ms;
            cursor: -webkit-grab;
            cursor: -moz-grab;
        }

        .pullee::-webkit-slider-runnable-track {
            height: 2rem;
            padding: 0.25rem;
            box-sizing: content-box;
            border-radius: 3rem;
            background-color: #2e5caa;
        }

        .pullee::-moz-range-track .pullee {
            width: 10rem;
            appearance: none;
        }

        .pullee::-moz-range-track .pullee:active::-webkit-slider-thumb {
            appearance: none;
            transform: scale(1.1);
            cursor: -webkit-grabbing;
            cursor: -moz-grabbing;
        }

        .pullee::-moz-range-track .pullee:active::-moz-range-thumb {
            border: 0;
            transform: scale(1.1);
            cursor: -webkit-grabbing;
            cursor: -moz-grabbing;
        }

        .pullee::-moz-range-track .pullee:active::-ms-thumb {
            transform: scale(1.1);
            cursor: -webkit-grabbing;
            cursor: -moz-grabbing;
        }

        .pullee::-moz-range-track .pullee:focus {
            outline: none;
        }

        .pullee::-moz-range-track .pullee::-webkit-slider-thumb {
            appearance: none;
            display: block;
            width: 1rem;
            height: 1rem;
            border-radius: 50%;
            background: #fff;
            transform-origin: 50% 50%;
            transform: scale(1);
            transition: transform ease-out 100ms;
            cursor: -webkit-grab;
            cursor: -moz-grab;
        }

        .pullee::-moz-range-track .pullee::-moz-range-thumb {
            border: 0;
            display: block;
            width: 1rem;
            height: 1rem;
            border-radius: 50%;
            background: #fff;
            transform-origin: 50% 50%;
            transform: scale(1);
            transition: transform ease-out 100ms;
            cursor: -webkit-grab;
            cursor: -moz-grab;
        }

        .pullee::-moz-range-track .pullee::-ms-thumb {
            display: block;
            width: 1rem;
            height: 1rem;
            border-radius: 50%;
            background: #fff;
            transform-origin: 50% 50%;
            transform: scale(1);
            transition: transform ease-out 100ms;
            cursor: -webkit-grab;
            cursor: -moz-grab;
        }

        .pullee::-moz-range-track .pullee::-webkit-slider-runnable-track {
            height: 1rem;
            padding: 0.25rem;
            box-sizing: content-box;
            border-radius: 1rem;
            background-color: #2e5caa;
        }

        .pullee::-moz-range-track {
            height: 1rem;
            padding: 0.5rem;
            box-sizing: content-box;
            border-radius: 1rem;
            background-color: #2e5caa;
        }

        .pullee::-moz-range-track .pullee::-moz-focus-outer {
            border: 0;
        }

        .pullee::-moz-range-track .pullee::-ms-track {
            border: 0;
            height: 1rem;
            padding: 0.25rem;
            box-sizing: content-box;
            border-radius: 1rem;
            background-color: #2e5caa;
            color: transparent;
        }

        .pullee::-moz-range-track .pullee::-ms-fill-lower, .pullee::-moz-range-track .pullee::-ms-fill-upper {
            background-color: transparent;
        }

        .pullee::-moz-range-track .pullee::-ms-tooltip {
            display: none;
        }

        .pullee::-moz-range-track html {
            font-size: 32px;
            text-align: center;
        }

        .pullee::-moz-range-track h1 {
            font-size: 0.8rem;
            text-transform: uppercase;
            letter-spacing: 1.25px;
        }

        .pullee::-moz-range-track .center-xy {
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }

        .pullee::-moz-focus-outer {
            border: 0;
        }

        .pullee::-ms-track {
            border: 0;
            height: 1rem;
            padding: 0.25rem;
            box-sizing: content-box;
            border-radius: 1rem;
            background-color: #2e5caa;
            color: transparent;
        }

        .pullee::-ms-fill-lower, .pullee::-ms-fill-upper {
            background-color: transparent;
        }

        .pullee::-ms-tooltip {
            display: none;
        }

        /* Extra small devices (phones, 600px and down) */
        @media only screen and (max-width: 600px) {
            .slider-text {
                position: absolute;
                font-size: small;
                width: 100%;
                /* max-width: 50%; */
                padding: 0.7rem;
                right: 0;
                color: white;
            }
        }

        /* Small devices (portrait tablets and large phones, 600px and up) */
        @media only screen and (min-width: 600px) {
            .slider-text {
                position: absolute;
                font-size: small;
                width: 50%;
                /* max-width: 50%; */
                padding: 0.7rem;
                right: 0;
                color: white;
            }
        }

        /* Medium devices (landscape tablets, 768px and up) */
        @media only screen and (min-width: 768px) {
        }

        /* Large devices (laptops/desktops, 992px and up) */
        @media only screen and (min-width: 992px) {
        }

        /* Extra large devices (large laptops and desktops, 1200px and up) */
        @media only screen and (min-width: 1200px) {
        }

        @-moz-document url-prefix() {

            .slide-text {

                margin-top: -12px;

            }

        }

        .pullee-readyonly::-webkit-slider-thumb {
            background-color: #d0d0d0;
        }

        .pullee-readyonly::-moz-range-thumb {
            background-color: #d0d0d0;
        }

        .pullee-slider-text {
            color: #d0d0d0 !important;
        }

        }
        .pullee-readyonly::-webkit-slider-thumb {
            background-color: #d0d0d0;
        }

        .pullee-readyonly::-moz-range-thumb {
            background-color: #d0d0d0;
        }

        .pullee-slider-text {
            color: #d0d0d0 !important;
        }
    </style>


    <style>
        html, body {
            margin: 0;
            height: 100%;
            overflow: hidden
        }


        .errormsg h9 {
            color: red !important;
            font-weight: 400 !important; /* Semi-bold */
            font-size: 15px !important; /* Font size 15px */
            text-align: center !important;

        }
        #user_input_form input{
            border: none;
            border-bottom: 1.5px solid orange !important;
            border-radius: 0 !important;
            padding: .3rem .2rem !important;
        }

        #user_input_form label{
            font-size: 1rem !important;
        }

        #user_input_form input::placeholder{
            font-size: 15px !important;
        }
        #user_input_form {
            margin-bottom: -20px !important;
        }

    </style>


    <script>
        // Initialize a variable to track the completion of reCAPTCHA
        var recaptchaCompleted = false;

        // Function to enable/disable the login button based on conditions
        function checkForm() {
            var recaptchaResponse = grecaptcha.getResponse();
            var username = document.getElementById("username").value;
            var password = document.getElementById("password").value;
            console.log("recaptchaResponse :", recaptchaResponse)
            if (recaptchaResponse.length > 0 && username !== "" && password !== "") {
                document.getElementById("btnValid").disabled = false;
            } else {
                document.getElementById("btnValid").disabled = true;
            }
        }

        // Callback function for reCAPTCHA completion
        function enableLogin() {
            recaptchaCompleted = true;
            checkForm();
        }

        /*

        //capta response
               function enableLogin() {
                   // Check if reCAPTCHA is successfully completed
                   var recaptchaResponse = grecaptcha.getResponse();
                   var username = document.getElementById("username").value;
                   var password = document.getElementById("password").value;
                   console.log("recaptchaResponse : ",recaptchaResponse);
                   console.log("username : ",username);
                   console.log("password : ",password);
                   if (recaptchaResponse.length > 0 && username !== "" && password !== "") {

                       document.getElementById("btnValid").disabled = false;
                   } else {
                       document.getElementById("btnValid").disabled = true;
                   }
               } */
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
                    type: "GET",

                    contentType: "application/json",
                    url: window.location + "generate/regenerateCaptcha",
                    //data : JSON.stringify(myJSON),
                    /* data : {
                        "txtCompare1" : $("#txtCompare1").val(),

                    }, */
                    data: data,
                    //contentType : "application/json; charset=utf-8",
                    ContentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    dataType: "json",
                    success: function (result) {
                        //alert("status of Captcha:...."+result.status);
                        if (result.status == "Done") {
                            //alert(result.data);
                            document.getElementById("txtCompare1").value = result.data;
                            document.getElementById("CaptchaDiv").innerHTML = result.data;
                            document.getElementById("txtCompare2").value = '';

                        } else if (result.status == "errorMsg") {

                            return false;

                        }
                    },
                    error: function (data, status, er) {
                        alert("error: " + data + " status: " + status + " er:"
                            + er);
                        window.location.href = '${pageContext.request.contextPath}/auth/loginerror';
                        form.submit;
                        return true;
                    }
                });
        }
    </script>


    <script lang="JavaScript">
        $('CaptchaDiv').click(function () {

            GenerateCaptcha();
        });


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
            } else if (str2 == null || str2 != str1) {
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
            $('#txtCompare1').bind("cut copy paste", function (e) {
                e.preventDefault();
                alert("cut,copy & paste not allowed");
            });

            $('#CaptchaDiv').bind("cut copy paste", function (e) {
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
                    type: "GET",

                    contentType: "application/json",
                    url: window.location + "generate/regenerateCaptcha",
                    //data : JSON.stringify(myJSON),
                    /* data : {
                        "txtCompare1" : $("#txtCompare1").val(),

                    }, */
                    data: data,
                    //contentType : "application/json; charset=utf-8",
                    ContentType: "application/x-www-form-urlencoded; charset=UTF-8",
                    dataType: "json",
                    success: function (result) {
                        //alert("status of Captcha:...."+result.status);
                        if (result.status == "Done") {
                            //alert(result.data);
                            document.getElementById("txtCompare1").value = result.data;
                            document.getElementById("CaptchaDiv").innerHTML = result.data;
                            document.getElementById("txtCompare2").value = '';

                        } else if (result.status == "errorMsg") {

                            return false;

                        }
                    },
                    error: function (data, status, er) {
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
                type: "GET",
                url: window.location + "generate/generateCaptcha",
                ContentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function (result) {
                    if (result.status == "Success") {
                        //alert(result.data);
                        document.getElementById("txtCompare1").value = result.data;
                        document.getElementById("CaptchaDiv").innerHTML = result.data;

                    } else {
                        $("#txtCompare1").html("<strong>Error</strong>");
                        console.log("Fail: ", result);
                    }
                },
                error: function (data, status, er) {
                    alert("error: " + data + " status: " + status + " er:" + er);
                    /* window.location.href ='
                    ${pageContext.request.contextPath}/auth/loginerror' ;
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
                type: "GET",
                contentType: "application/json",
                url: window.location + "generate/deleteValidatedCaptcha",
                //data : JSON.stringify(myJSON),
                data: {
                    "txtCompare1": $("#txtCompare1").val(),
                    //"txtCompare2" : today,

                },
                contentType: "application/json; charset=utf-8",

                dataType: "json",
                success: function (result) {
                    //alert("status of Captcha:...."+result.status);
                    if (result.status == "Success") {
                        //alert(result.data);
                        //document.getElementById("txtCompare1").value = result.data;
                        return true;
                    } else {

                        return false;

                    }
                },
                error: function (result, status, er) {
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
        /* $("#btnValid").on('click', function(){
        alert("jdkfjdkfj");
            ValidateCaptcha().done(function(data){
                 if(data.status == "Success")
                    return true;
                 else
                    return false;
            })
        }); */

    </script>
    <script language="javascript" type="text/javascript">

        function ValidatehiddenCaptcha() {

            return true;


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
            if (ttt) {
                //alert("submit logout form sss");
                return true;
            } else {
                //alert("submit logout form fff");
                return false;
            }
        }
    </script>


    <script type="text/javascript">


        function forgotPwd() {

            window.location = "${pageContext.request.contextPath}/forgotpwd/forgotPwdByUser";
            form.submit;
            return true;

        };
    </script>

    <script language="javascript" type="text/javascript">
        function testing1() {
//alert("status of Captcha:....testing1()");
//var t=true;
            var result = false;
            $.ajax({
                type: "GET",
                contentType: "application/json",
                url: window.location + "generate/validateCaptcha",
                //data : JSON.stringify(myJSON),
                data: {
                    "txtCompare1": removeSpaces(document.getElementById('txtCompare1').value),
                    "txtCompare2": $("#txtCompare2").val(),
                    "username": $("#username").val()
                },
                //data : myJSON,
                // data: { data: myJSON },

                /* data: JSON.stringify({
                txtCompare1 : $("#txtCompare1").val(),
                txtCompare2 : $("#txtCompare2").val()
                }), */
                //data: "txtCompare1=" + $("#txtCompare1").val() + "&txtCompare2=" + $("#txtCompare1").val(),
                contentType: "application/json; charset=utf-8",
                //mimeType : 'application/json',
                //data : formData,
                async: false,
                dataType: "json",
                success: function (result) {
                    //alert("status of Captcha:...."+result.status);
                    //var result = false;
                    if (result.status == "Success") {
                        //alert("check succes"+result.data);
                        //deletegeneratedCaptcha();
                        //result = true;
                        //t = true;
                        document.getElementById("hiddenCaptcha").value = true;
                        //alert("check succes : "+document.getElementById("hiddenCaptcha").value);
                        return true;

                    } else if (result.status == "errorMsg") {

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

                    } else if (result.status == "Fail") {

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
                error: function () {
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
            if (captcha2 == null && captcha2 == '') {
                alert("please enter Security Code");
                document.getElementById("txtCompare2").focus();
                return false;
            } else if (captcha2 == null || captcha1 != captcha2) {
                alert("Wrong Security Code ");
                document.getElementById("txtCompare2").value = '';
                document.getElementById("txtCompare2").focus();
                return false;
            }
                //alert(obj)
            //alert("url: "+window.location + "generate/generateCaptcha1");
            else if (captcha1 == captcha2) {
                //alert("capthca matched");
                //var t = true;

                var t = testing1();

                //alert("var r"+t);
                return t;
            }
        }


        function newlogin() {

            // alert("submit logout form");
            /*   var tt = ValidateCaptcha(); */
            return true;
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


    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

</head>


<!-- rk -->
<body class="loginBg login-bg" onload="return ajaxGet();" id="body" name="body" oncontextmenu="return false">

<div class="container-fluid ">
    <div class="row">


        <div class="col-md-4 offset-md-7  offset-lg-7 col-lg-4 col-12 mt-2 order-0">
            <div class="container p-4 bg-white rounded-3 mt-3">

				<img class="img-fluid" src="resourcesNew1/assets/login-logo.svg" style="height: 24px !important">
                <!-- 			<h1 class="c-092540 mt-2">Looks like you'r new to mobi. Welcome</h1> -->
                <h1 class=" mt-2"></h1>


                <form data-parsley-validate name='loginForm' class="login-form" id="user_input_form"
                      action="<c:url value='/j_spring_security_check' />?sdfweirukwioer=${_csrf.token}" method='POST'
                      id="login"
                      name="form1" onsubmit="return ValidatehiddenCaptcha();">

					<p style="color: #586570;font-weight: 500;font-size: 24px; margin-bottom: 0px !important;">Welcome
						!</p>
					<p style="font-size:13px;color: #858585; margin: 0px 0px 0px 0px!important" >Please enter your credentials to access your account.</p>

					<input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}">

					<div class="form-group" >
						<label class="text-dark mb-2" style="color: #586570 !important; margin-top: .7rem!important; margin-bottom: 3px!important">User Name</label> <br>
						<input name="username" type="text" class="form-control" style="border: none"
							   id="username" placeholder="Enter Username" onkeyup="checkForm()" oninput="resetError()">

					</div>

<%--					<div class="form-group" style="margin-top: 1rem!important;">--%>

<%--						<label class="text-dark  mb-2" style="color: #586570 !important;margin-top: .7rem!important; margin-bottom: 3px!important">Password</label>--%>
<%--						<input--%>
<%--								type="password" id="password" class="form-control"--%>
<%--								placeholder="Enter Password" name="password"--%>
<%--								onkeydown="return onclickTab(event);" onkeyup="checkForm()">--%>

<%--					</div>--%>
                    <div class="form-group" style="margin-top: 1rem!important;">
                        <label class="text-dark mb-2"
                               style="color: #586570 !important; margin-top: .7rem!important; margin-bottom: 3px!important;">Password</label>
                        <div style="position: relative;">
                            <input type="password" id="password" class="form-control" placeholder="Enter Password"
                                   name="password" onkeydown="return onclickTab(event);" onkeyup="checkForm()" oninput="resetError()">
                            <span class="toggle-password"
                                  onclick="togglePasswordVisibility('password', this.querySelector('img'))"
                                  style="position: absolute; top: 50%; right: 10px; transform: translateY(-50%); cursor: pointer;">
            <img class="show-hide-pass" src="${pageContext.request.contextPath}/resourcesNew1/assets/show-password.svg"
                 alt="Toggle Password Visibility">
        </span>
                        </div>
                    </div>


                    <!-- <div class="form-group mt-2">
                                <label class="text-dark  mt-2 mb-2">UserName <sup>*</sup></label>
                                <input  name="username" type="text" class="form-control" id="username" placeholder="Username">

                                </div>

                                    <div class="form-group mt-2">

                                        <label class="text-dark  mt-2 mb-2">Password</label>
                                        <input type="password" id="password" class="form-control"
                                            placeholder="Enter Password" name="password" onkeydown="return onclickTab(event);">

                                    </div> -->

                    <input type="hidden" align="center" id="txtCompare1"
                           name="txtCompare1" placeholder="" readonly onclick="return test();"/>

                    <!-- <div class="form-group mt-2">
                    <label class="text-dark  mt-2 mb-2">Enter Captcha</label>
                    <input  name="captcha" type="text" class="form-control " id="txtCompare2" placeholder="Enter Captcha">

                    </div> -->

                    <div class="form-group mt-2">
                        <a class="float-end small text-decoration-none;" href="#"
                           onclick="forgotPwd()" style="color: #005BAA">Forgot Password?</a>
                    </div>

                    <%-- 			<div class="form-group mt-5">
                        <div class="row">
                            <div class="col-6"><button  class="btn btn-captcha ls-5 c-005baa fw-bold" id="CaptchaDiv" onclick="return test();"> </button></div>
                            <div class="col-6"><button  class="btn btn-005baa fw-bold" onclick="return regenerateCaptcha();"><img width="12%" src="${pageContext.request.contextPath}/resourcesNew1/assets/regenerte.png"> Regenerate</button></div>
                        </div>
                    </div> --%>
                    <div class="errormsg">
                        <h9 id="error-message">Invalid Credential</h9>
                    </div>


                    <!-- <div class="alert alert-e0edff c-367fee small mt-3" role="alert" style="margin-top:2.5rem !important;">

         To prevent spam and automated extraction of data from websites
                            </div>

                                    <div id="verification-box" class="mt-4" disabled>
                                         <h6 style="margin: 0px !important;" >Verification required!</h6>

                                    <div
                                        style="font-size: small; text-align-last: center; margin-top: 10px; text-align: -webkit-center; background-color: transparent;">
                                        <p id="pswitch"
                                            class="slider-text slide-text pullee-slider-text">Slide to
                                            Login</p>
                                        <input id="swipeen" type="range" style="" value="0"
                                            class="pullee pullee-readyonly" placeholder="Swipe to submit"
                                            min="0" max="150" disabled>
                                    </div>
                                </div>
         -->

                    <br>
                    <div class="g-recaptcha"
                         data-sitekey="6LeoKlgoAAAAADyuPnoEheyl5akNRUg7fQzVUXHQ"
                         data-callback="enableLogin"></div>
                    <br>
                    <div class="form-group login-btn">
                        <button type="submit" name="submit" id="btnValid"
                                class="btn btn-005baa fw-bold w100-per" disabled
                                style="border-radius: 100px; padding: 10px 0px !important; font-weight: 600 !important;">
                            Login
                        </button>
                    </div>


                    <!-- 	<div class="form-group mt-2">
                            <button type="submit" name="submit" id="btnValid"
                                class="btn btn-005baa fw-bold w100-per" style="display:none"
                                onclick="return newlogin()" onclick="this.disabled = true">Login</button>
                            </div> -->
                    <br>


                    <p color="red" id="error" value=""></p>
                    <input type="hidden" name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>

                    <input type="hidden" name="hiddenCaptcha" id="hiddenCaptcha"
                           value="false"/>


                </form>


            </div>
        </div>
    </div>
</div>

<form
        action="${pageContext.request.contextPath}/j_spring_security_logout"
        method="post" id="formId" name="formId">


    <input type="hidden" name="link-logout" id="link-logout" value=""/>


    <input type="hidden" name="${_csrf.parameterName}"
           value="${_csrf.token}"/>

</form>


</section>

</div>


<!-- JS -->


<!-- 	<script -->
<%-- 		src="${pageContext.request.contextPath}/resourcesNew/js/jquery-2.1.4.min.js"></script> --%>
<script
        src="${pageContext.request.contextPath}/resourcesNew/js/essential-plugins.js"></script>
<!-- 	<script -->
<%-- 		src="${pageContext.request.contextPath}/resourcesNew/js/bootstrap.min.js"></script> --%>

<%-- <script
    src="${pageContext.request.contextPath}/resourcesNew/plugins/pace.min.js"></script>
--%>

<script
        src="${pageContext.request.contextPath}/resourcesNew/js/bootstrap.bundle.min.js"></script>
<script
        src="${pageContext.request.contextPath}/resourcesNew/js/jquery-3.5.1.min.js"></script>
<script
        src="${pageContext.request.contextPath}/resourcesNew/js/main.js"></script>


<div class="visible-xs visible-sm extendedChecker"></div>


</body>


<!-- old check capcha -->

<!--
<script>
    const username = document.getElementById("username")
    const password = document.getElementById("password")


 document.addEventListener('input', (evt) => {
        if (username.value !== '' && password.value !== '') {
           document.getElementById("verification-box").style.display = "block";
           document.getElementById("btnValid").style.display = "none";

        	$('#verification-box').attr(
					'disabled', false);
        	$('#swipeen').attr(
					'disabled', false);

        	document.getElementById('pswitch').classList.remove('pullee-slider-text');
        	document.getElementById('swipeen').classList.remove('pullee-readyonly');



        } else {
          //  document.getElementById("verification-box").style.display = "none"
           	document.getElementById("btnValid").style.display = "none";
          document.getElementById("verification-box").style.display = "block";

        	$('#verification-box').attr(
					'disabled', true);

        	$('#swipeen').attr(
					'disabled', true);


        	document.getElementById('pswitch').classList.add('pullee-slider-text');
        	document.getElementById('swipeen').classList.add('pullee-readyonly');



        }
    });
</script> -->

<script>
    function togglePasswordVisibility(id, imgElement) {
        var input = document.getElementById(id);
        if (input.type === "password") {
            input.type = "text";
            imgElement.src = "${pageContext.request.contextPath}/resourcesNew1/assets/hide-password.svg";
        } else {
            input.type = "password";
            imgElement.src = "${pageContext.request.contextPath}/resourcesNew1/assets/show-password.svg";
        }
    }
</script>


<script>
    var inputRange = document.getElementsByClassName('pullee')[0],
        maxValue = 150, // the higher the smoother when dragging
        speed = 12, // thanks to @pixelass for this
        currValue, rafID;

    // set min/max value
    inputRange.min = 0;
    inputRange.max = maxValue;

    // listen for unlock
    function unlockStartHandler() {
        // clear raf if trying again
        window.cancelAnimationFrame(rafID);

        // set to desired value
        currValue = +this.value;
    }

    function unlockEndHandler() {

        // store current value
        currValue = +this.value;

        // determine if we have reached success or not
        if (currValue >= maxValue) {
            successHandler();
        } else {
            rafID = window.requestAnimationFrame(animateHandler);
        }
    }

    // handle range animation
    function animateHandler() {

        // update input range
        inputRange.value = currValue;

        // determine if we need to continue
        if (currValue > -1) {
            window.requestAnimationFrame(animateHandler);
        }

        // decrement value
        currValue = currValue - speed;
    }

    // handle successful unlock
    function successHandler() {
        // alert('unlocked');
        document.getElementById("btnValid").style.display = "block"
        document.getElementById("verification-box").style.display = "none"

        // reset input range
        inputRange.value = 0;
    };

    // bind events
    inputRange.addEventListener('mousedown', unlockStartHandler, false);
    inputRange.addEventListener('mousestart', unlockStartHandler, false);
    inputRange.addEventListener('mouseup', unlockEndHandler, false);
    inputRange.addEventListener('touchend', unlockEndHandler, false);
</script>


<script src="https://www.google.com/recaptcha/api.js" async defer></script>

<script>
    document.addEventListener("DOMContentLoaded", function() {
        function resetError() {
            const errorMsg = document.querySelector(".errormsg");
            if (errorMsg.style.display !== "none") {
                errorMsg.style.display = "none";
            }
        }
        document.getElementById("username").addEventListener("input", resetError);
        document.getElementById("password").addEventListener("input", resetError);
    });
</script>

</html>