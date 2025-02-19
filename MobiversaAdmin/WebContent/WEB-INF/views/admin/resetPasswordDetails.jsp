<%@page import="org.springframework.web.context.request.RequestScope" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page import="com.mobiversa.payment.service.AdminService,com.mobiversa.common.bo.AuditTrail" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <%--    for tooltip start --%>

    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.bundle.min.js"></script>


    <%--    for tooltip end --%>


    <link rel="icon" type="image/gif" sizes="16x16"
          href="${pageContext.request.contextPath}/resourcesNew/img/newMobiMIcon.png"/>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Mobi</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <!-- <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script> -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>

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

    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist/dist/chartist.min.css"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/chartist-plugin-tooltips/dist/chartist-plugin-tooltip.css"
          rel="stylesheet">

    <link href="${pageContext.request.contextPath}/resourcesNew1/assets/extra-libs/c3/c3.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/styleLogin.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/style.css" rel="stylesheet">

    <link href="${pageContext.request.contextPath}/resourcesNew1/dist/css/pages/dashboard1.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.date.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resourcesNew1/assets/libs/pickadate/lib/themes/default.time.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resourcesNew1/dataTable/dataTables.bootstrap4.min.css">
    <link rel="stylesheet" type="text/css"
          href="${pageContext.request.contextPath}/resourcesNew1/dataTable/dataTables.material.min.css">
    <link href='${pageContext.request.contextPath}/resourcesNew1/select2/dist/css/select2.min.css' rel='stylesheet'
          type='text/css'>

    <link href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" rel="stylesheet"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script>

    <style>
        /* Style all input fields */
        input {
            width: 100%;
            padding: 12px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
            margin-top: 6px;
            margin-bottom: 16px;
        }


        /* The message box is shown when the user clicks on the password field */
        #message {
            display: none;
            background: #f1f1f1;
            color: #000;
            position: relative;
            padding: 20px;
            margin-top: 10px;
        }

        #message p {
            padding: 10px 35px;
            font-size: 18px;
        }


    </style>
    <script type="text/javascript">


        function load() {
            var npwd = document.getElementById("npassword").value;
            var cpwd = document.getElementById("cpassword").value;


            if (npwd == cpwd) {

                swal({
                    title: "Are you sure? Can we reset?",
                    text: "it will be reset..!",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonText: "Yes, reset !",
                    cancelButtonText: "No, cancel!",
                    closeOnConfirm: false,
                    closeOnCancel: false,
                    /*  confirmButtonClass: 'btn btn-success',
                    cancelButtonClass: 'btn btn-danger', */

                }, function (isConfirm) {
                    if (isConfirm) {

                        $("#myform").submit();


                    } else {
                        // swal("Cancelled", "Your Merchant Promotion details not added", "error");
                        var url = "${pageContext.request.contextPath}/admmerchant/list/1";
                        $(location).attr('href', url);
                        //return true;
                    }
                });

            } else {
                alert("New & Confrim Password Mis-Matched");
            }


        }

    </script>
</head>
<body class="login-bg">

<style>
    @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

    body {
        font-family: "Poppins", sans-serif !important;
    }

    .card .padding-card {
        padding: 25px 37px !important;
    }

    input[type=text]::placeholder {
        color: #9C9C9C;
        /*font-family: "Poppins", sans-serif;*/
    }

    input[type=text]::placeholder {
        color: #858585;
        /*font-family: "Poppins", sans-serif;*/
    }

    .rounded-button {
        background-color: #0056b3;
        color: white;
        border: none;
        padding: 1.2em 2em;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 1em;
        border-radius: 25px;
        cursor: pointer;
        transition: background-color 0.3s;
        width: 100%;
    }

    .rounded-button:focus, .rounded-button:active {
        background-color: #005baa;
    }
</style>

<style>

    @media only screen and (min-width: 993px) {
        .row .col.offset-l5 {
            margin-left: 57.66667% !important;
        }
    }

    @media only screen and (min-width: 993px) {
        .row .col.l5 {
            width: 37.66667%;
            margin-left: auto;
            left: auto;
            right: auto;
        }
    }

    .sample {
        display: none;
    }

</style>
<script>
    function enableOtpBtn() {
        const otpExpired = document.getElementById("otpExpired").style.display = "none";
        const invalidOtp = document.getElementById("invalidOtp").style.display = "none";
        const usernameInput = document.getElementById('otp');
        const otpButton = document.getElementById('otp-btn-otp');

        if (usernameInput.value.trim() !== '') {
            otpButton.disabled = false;
            otpButton.style.opacity = "1";
        } else {
            otpButton.disabled = true;
            otpButton.style.opacity = "0.5";
        }
    }


</script>


<div class="container-fluid" style="background-color: transparent !important;">

    <div class="row otp_div" id="main_modal">
        <div class="col s12 m5 offset-m6 offset-l5 l5">
            <div class="card border-radius">
                <div class="card-content padding-card" id="new_old_pass">
                    <div class="center-container">
                        <div style="margin-left: -10px">
                            <img class="img-fluid" src="resourcesNew1/assets/logo1.svg" style="height: 50px !important">
                        </div>
                        <p style="color: black;font-weight: 600;font-size: 23px;margin-top: 10px">Enter OTP</p>
                        <p style="font-size:14px;color: #586570">Enter the OTP sent to your linked email
                            address. </p>
                        <div>
                            <label style="color: #848687;font-weight: 500;font-size: 16px;margin-bottom: 0px">OTP</label><br>
                            <form method="post" id="myform" name="myform"
                                  action="${pageContext.request.contextPath}/forgotpwd/resetPwdByUserName">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="text" name="otp" id="otp" placeholder="Enter OTP" oninput="enableOtpBtn()"
                                       style="border: none; border-bottom: 1px solid #ffa500; text-align: left; font-size: 13px; color: #858585;outline: none;max-width: 100%; box-sizing: border-box;">
                                <span id="invalidOtp" style="color: red;margin-bottom: 25px;">${invalidOtp}</span>
                                <span id="otpExpired" style="color: red;">${isExpired}</span>
                                <input type="hidden" id="username" name="username" value="${username}"/>
                                <input type="hidden" id="mobileNo" name="mobileNo" value="${mobileNo}"/>
                                <input type="hidden" id="email" name="email" value="${email}"/>
                                <input type="hidden" id="backend_otp" name="backend_otp" value="${otp}"/>
                                <input type="hidden" name="npassword" value="hardcodedPassword123"/>
                                <input type="hidden" name="cpassword" value="hardcodedPassword123"/>
                            </form>
                        </div>
                        <div class="button-container"
                             style="font-size: small; text-align-last: center; margin-top: 10px; text-align: -webkit-center; background-color: transparent;">
                            <button class="rounded-button" id="otp-btn-otp" disabled onclick="load1()"
                                    style="font-weight: 600;opacity: 0.5;margin-top:10px">Submit
                            </button>
                        </div>
                        <br>
                        <div style="display: flex;justify-content: space-between;color: #858585">
                            <div style="display: flex;gap: 15px;justify-content: center;align-items: center;">
                                <p id="sendOtpPage" style="color: #858585;font-size: 13px;display: none">
                                    Didn't Receive OTP ?</p>
                                <p id="resend_otp_new"
                                   style="color: #005BAA;cursor:pointer;font-weight: 500;display: none"
                                   onclick="resendOtp('${username}')">Resend
                                    OTP</p>
                            </div>
                            <div id="timer"></div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>


    <style>
        input[type="text"]:focus,
        input[type="password"]:focus {
            border-bottom: 1px solid #ffa500;
        }
    </style>

    <script>


        <%--        var timeLeft = 10;--%>
        <%--        var modal = document.getElementById('main_modal');--%>

        <%--        function formatTime(seconds) {--%>
        <%--            var minutes = Math.floor(seconds / 60);--%>
        <%--            var seconds = seconds % 60;--%>
        <%--            if (seconds < 10) {--%>
        <%--                seconds = '0' + seconds;--%>
        <%--            }--%>
        <%--            return minutes + ':' + seconds;--%>
        <%--        }--%>

        <%--        var countdown = setInterval(function () {--%>
        <%--            timeLeft--;--%>
        <%--            document.getElementById('timer').innerHTML = formatTime(timeLeft);--%>
        <%--            var resed_otp=document.getElementById('resend_otp_new');--%>
        <%--            var resend_msg=document.getElementById('sendOtpPage');--%>
        <%--            var isOtpExpired ="${isExpired}";--%>

        <%--            // Check if the modal is present and visible--%>
        <%--            if (modal && getComputedStyle(modal).display !== 'none') {--%>
        <%--                if (timeLeft <= 0 && (isOtpExpired.length > 0 || isOtpExpired != '')) {--%>
        <%--                    clearInterval(countdown);--%>
        <%--                    document.getElementById('timer').style.display = "none";--%>
        <%--                    resed_otp.style.display = "block";--%>
        <%--                    resend_msg.style.display = "block";--%>
        <%--                }--%>
        <%--            }--%>
        <%--            if (timeLeft <= 0) {--%>
        <%--                timeLeft = 0;--%>
        <%--            }--%>

        <%--        }, 1000);--%>


        var timeLeft = "${otpTime}";
        var modal = document.getElementById('main_modal');

        function formatTime(seconds) {
            var minutes = Math.floor(seconds / 60);
            var seconds = seconds % 60;
            if (seconds < 10) {
                seconds = '0' + seconds;
            }
            return minutes + ':' + seconds;
        }

        var timerElement = document.getElementById('timer');
        timerElement.innerHTML = formatTime(timeLeft);

        var countdown = setInterval(function () {
            var timerElement = document.getElementById('timer');
            var resed_otp = document.getElementById('resend_otp_new');
            var resend_msg = document.getElementById('sendOtpPage');
            var isOtpExpired = "${isExpired}";


            timeLeft--;


            if (isOtpExpired !== '') {
                timerElement.style.display = "none";
                resed_otp.style.display = "block";
                resend_msg.style.display = "block";
            }

            if (timeLeft <= 0) {
                timeLeft = 0;
                clearInterval(countdown);
                timerElement.style.display = "none";
                resed_otp.style.display = "block";
                resend_msg.style.display = "block";
            } else {
                timerElement.innerHTML = formatTime(timeLeft);
            }

            // Check if the modal is present and visible
            if (modal && getComputedStyle(modal).display !== 'none') {
                console.log(isOtpExpired)
                console.log(isOtpExpired !== '');
                if (timeLeft <= 0) {
                    clearInterval(countdown);
                    timerElement.style.display = "none";
                    resed_otp.style.display = "block";
                    resend_msg.style.display = "block";
                }

            }
        }, 1000);


        function resendOtp(username) {
            console.log(username);
            document.getElementById("resend_otp_form_id").value = username;
            $("#resend_otp_form").submit();


        }
    </script>

    <form id="resend_otp_form" action="${pageContext.request.contextPath}/forgotpwd/forgetPwdByUserName" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <input type="hidden" name="username" id="resend_otp_form_id">
    </form>

    <script>
        function togglePassword() {
            var passwordInput = document.getElementById("npswd1");
            var toggleButton = document.getElementById("togglepassword1");

            if (passwordInput.type === "password") {
                passwordInput.type = "text";
            } else {
                passwordInput.type = "password";
            }
        }


        function passwordVerify(event) {
            var npwd = document.getElementById("npswd1").value;
            var cpwd = document.getElementById("npswd2").value;
            if (npwd !== cpwd) {
                alert("Passwords do not match");
                event.preventDefault();
                return false;
            } else {
                alert("Passwords match");
            }
        }

    </script>


    <style>
        body {
            background-color: rgb(235, 234, 234);
            font-family: Arial, sans-serif;
        }

        #passwordInput {
            position: relative;
        }

        .tooltip {
            display: none;
            position: absolute;
            background-color: white;
            box-shadow: 0 14px 18px rgba(0, 0, 0, 0.2);
            border-radius: 5px;
            padding: 10px;
            z-index: 1;
            top: -23px;
            border: 1px solid #c5c5c5;
            opacity: 1 !important;
            left: -204px;
        }


        .tooltip::after {
            content: "";
            position: absolute;
            top: 40%;
            left: 100%;
            width: 0;
            height: 0;
            border-width: 10px;
            border-style: solid;
            border-color: transparent transparent transparent white;
            margin-left: -1px;
        }

        .tooltip::before {
            content: "";
            position: absolute;
            top: 40%;
            left: 100%;
            width: 0;
            height: 0;
            border-width: 10px;
            border-style: solid;
            border-color: transparent transparent transparent #c5c5c5;
        }


        .tooltip p {
            margin: 0;
        }

        .tooltip ul {
            list-style: none;
            padding-left: 0;
        }

        .tooltip ul li {
            display: flex;
            align-items: center;
            margin-bottom: 8px;
            /*color: red; */
        }

        .tooltip ul li.correct {
            /*color: green; */
        }

        .tooltip ul li .icon {
            margin-right: 8px;
        }

        .red::after {
            content: '\25EF';
            color: red;
            background-color: red;
            border-radius: 50%;
        }

        .green::after {
            content: '\25EF';
            color: green;
            background-color: green;
            border-radius: 50%;
        }

        .toolTipFont_size {
            font-size: 12px !important;
            color: rgb(133, 133, 133) !important;
        }
    </style>

    <style>


        [type="radio"]:not(:checked) + span:before, [type="radio"]:not(:checked) + span:after {

            border: none !important;
            display: none !important;
        }

        .requirement input[type="radio"] {
            appearance: none;
            width: 12px;
            height: 12px;
            border-radius: 50%;
            border: 2px solid #ddd;
            margin-right: 8px;
            opacity: 1 !important;
        }

        .requirement input[type="radio"].invalid {
            border-color: #ddd;
            background-color: red;
        }

        .requirement input[type="radio"].valid {
            border-color: #ddd;
            background-color: #2ECC71;
        }


        [type="radio"]:not(:checked) + span, [type="radio"]:checked + span {
            position: relative;
            padding-left: 20px !important;
            cursor: pointer;
            display: inline-block;
            height: 25px;
            line-height: 25px;
            font-size: 1rem;
            transition: .28s ease;
            user-select: none;
        }

    </style>



<%--    <script>--%>
<%--        document.addEventListener('DOMContentLoaded', function () {--%>
<%--            const passwordInput1 = document.getElementById('npswd1');--%>
<%--            const tooltip1 = document.getElementById('tooltip1');--%>
<%--            const passwordInput2 = document.getElementById('npswd2');--%>
<%--            const tooltip2 = document.getElementById('tooltip2');--%>
<%--            const otpConfirmBtn = document.getElementById('otp_confirm_btn');--%>
<%--            const pass1CheckedImg = document.getElementById('pass1_checked');--%>
<%--            const pass2CheckedImg = document.getElementById('pass2_checked');--%>

<%--            function updateTooltip(inputElement, tooltipElement) {--%>
<%--                const password = inputElement.value;--%>

<%--                // Validate password requirements--%>
<%--                const hasMinimumChars = password.length >= 8;--%>
<%--                const hasUpperCase = /[A-Z]/.test(password);--%>
<%--                const hasNumber = /\d/.test(password);--%>
<%--                const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);--%>

<%--                // Update tooltip items--%>
<%--                const charRequirement = tooltipElement.querySelector('.eightchar');--%>
<%--                const upperCaseRequirement = tooltipElement.querySelector('.uppercase');--%>
<%--                const numberRequirement = tooltipElement.querySelector('.oneOrMore');--%>
<%--                const specialCharRequirement = tooltipElement.querySelector('.oneSpl');--%>

<%--                charRequirement.classList.toggle('valid', hasMinimumChars);--%>
<%--                charRequirement.classList.toggle('invalid', !hasMinimumChars);--%>

<%--                upperCaseRequirement.classList.toggle('valid', hasUpperCase);--%>
<%--                upperCaseRequirement.classList.toggle('invalid', !hasUpperCase);--%>

<%--                numberRequirement.classList.toggle('valid', hasNumber);--%>
<%--                numberRequirement.classList.toggle('invalid', !hasNumber);--%>

<%--                specialCharRequirement.classList.toggle('valid', hasSpecialChar);--%>
<%--                specialCharRequirement.classList.toggle('invalid', !hasSpecialChar);--%>

<%--                return hasMinimumChars && hasUpperCase && hasNumber && hasSpecialChar;--%>
<%--            }--%>

<%--            function updatePass1CheckedImg() {--%>
<%--                const password1Valid = updateTooltip(passwordInput1, tooltip1);--%>

<%--                if (password1Valid) {--%>
<%--                    pass1CheckedImg.style.display = 'inline';--%>
<%--                } else {--%>
<%--                    pass1CheckedImg.style.display = 'none';--%>
<%--                }--%>
<%--            }--%>

<%--            function updatePass2CheckedImg() {--%>
<%--                const password2Valid = updateTooltip(passwordInput2, tooltip2);--%>

<%--                if (password2Valid) {--%>
<%--                    pass2CheckedImg.style.display = 'inline';--%>
<%--                } else {--%>
<%--                    pass2CheckedImg.style.display = 'none';--%>
<%--                }--%>
<%--            }--%>

<%--            function arePasswordsEqual() {--%>
<%--                return passwordInput1.value === passwordInput2.value;--%>
<%--            }--%>

<%--            function updateButtonState() {--%>
<%--                const password1Valid = updateTooltip(passwordInput1, tooltip1);--%>
<%--                const password2Valid = updateTooltip(passwordInput2, tooltip2);--%>

<%--                if (password1Valid && password2Valid && arePasswordsEqual()) {--%>
<%--                    otpConfirmBtn.style.opacity = "1";--%>
<%--                    otpConfirmBtn.disabled = false;--%>
<%--                } else {--%>
<%--                    otpConfirmBtn.style.opacity = "0.5";--%>
<%--                    otpConfirmBtn.disabled = true;--%>

<%--                }--%>
<%--            }--%>

<%--            passwordInput1.addEventListener('input', function () {--%>
<%--                updatePass1CheckedImg();--%>
<%--                updateButtonState();--%>
<%--            });--%>

<%--            passwordInput2.addEventListener('input', function () {--%>
<%--                updateButtonState();--%>
<%--                updatePass2CheckedImg();--%>
<%--            });--%>

<%--            passwordInput1.addEventListener('focus', function () {--%>
<%--                tooltip1.style.display = 'block';--%>
<%--            });--%>

<%--            passwordInput2.addEventListener('focus', function () {--%>
<%--                tooltip2.style.display = 'block';--%>
<%--            });--%>

<%--            document.addEventListener('click', function (event) {--%>
<%--                if (!passwordInput1.contains(event.target) && !tooltip1.contains(event.target)) {--%>
<%--                    tooltip1.style.display = 'none';--%>
<%--                }--%>
<%--                if (!passwordInput2.contains(event.target) && !tooltip2.contains(event.target)) {--%>
<%--                    tooltip2.style.display = 'none';--%>
<%--                }--%>
<%--            });--%>
<%--        });--%>
<%--    </script>--%>


<%--    <script>--%>
<%--        document.addEventListener('DOMContentLoaded', function () {--%>
<%--            const passwordInput1 = document.getElementById('npswd1');--%>
<%--            const tooltip1 = document.getElementById('tooltip1');--%>
<%--            const passwordInput2 = document.getElementById('npswd2');--%>
<%--            const otpConfirmBtn = document.getElementById('otp_confirm_btn');--%>
<%--            const pass1CheckedImg = document.getElementById('pass1_checked');--%>
<%--            const pass2CheckedImg = document.getElementById('pass2_checked');--%>
<%--            const mismatchMessage = document.getElementById('mismatch_message'); // New element for mismatch message--%>

<%--            function updateTooltip(inputElement, tooltipElement) {--%>
<%--                const password = inputElement.value;--%>

<%--                // Validate password requirements--%>
<%--                const hasMinimumChars = password.length >= 8;--%>
<%--                const hasUpperCase = /[A-Z]/.test(password);--%>
<%--                const hasNumber = /\d/.test(password);--%>
<%--                const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);--%>

<%--                // Update tooltip items--%>
<%--                const charRequirement = tooltipElement.querySelector('.eightchar');--%>
<%--                const upperCaseRequirement = tooltipElement.querySelector('.uppercase');--%>
<%--                const numberRequirement = tooltipElement.querySelector('.oneOrMore');--%>
<%--                const specialCharRequirement = tooltipElement.querySelector('.oneSpl');--%>

<%--                charRequirement.classList.toggle('valid', hasMinimumChars);--%>
<%--                charRequirement.classList.toggle('invalid', !hasMinimumChars);--%>

<%--                upperCaseRequirement.classList.toggle('valid', hasUpperCase);--%>
<%--                upperCaseRequirement.classList.toggle('invalid', !hasUpperCase);--%>

<%--                numberRequirement.classList.toggle('valid', hasNumber);--%>
<%--                numberRequirement.classList.toggle('invalid', !hasNumber);--%>

<%--                specialCharRequirement.classList.toggle('valid', hasSpecialChar);--%>
<%--                specialCharRequirement.classList.toggle('invalid', !hasSpecialChar);--%>

<%--                return hasMinimumChars && hasUpperCase && hasNumber && hasSpecialChar;--%>
<%--            }--%>

<%--            function updatePass1CheckedImg() {--%>
<%--                const password1Valid = updateTooltip(passwordInput1, tooltip1);--%>

<%--                if (password1Valid) {--%>
<%--                    pass1CheckedImg.style.display = 'inline';--%>
<%--                } else {--%>
<%--                    pass1CheckedImg.style.display = 'none';--%>
<%--                }--%>
<%--            }--%>

<%--            function updatePass2CheckedImg() {--%>
<%--                const password2Valid = passwordInput2.value.length > 0; // Only check if password 2 has content--%>

<%--                if (password2Valid) {--%>
<%--                    pass2CheckedImg.style.display = 'inline';--%>
<%--                } else {--%>
<%--                    pass2CheckedImg.style.display = 'none';--%>
<%--                }--%>
<%--            }--%>

<%--            function arePasswordsEqual() {--%>
<%--                return passwordInput1.value === passwordInput2.value;--%>
<%--            }--%>

<%--            function updateButtonState() {--%>
<%--                const password1Valid = updateTooltip(passwordInput1, tooltip1);--%>
<%--                const password2Valid = passwordInput2.value.length > 0; // Check if password 2 has content--%>

<%--                if (password1Valid && password2Valid && arePasswordsEqual()) {--%>
<%--                    otpConfirmBtn.style.opacity = "1";--%>
<%--                    otpConfirmBtn.disabled = false;--%>
<%--                    mismatchMessage.style.display = 'none'; // Hide mismatch message if passwords match--%>
<%--                } else {--%>
<%--                    otpConfirmBtn.style.opacity = "0.5";--%>
<%--                    otpConfirmBtn.disabled = true;--%>

<%--                    console.log(!arePasswordsEqual())--%>
<%--                    if (!arePasswordsEqual()) {--%>
<%--                        mismatchMessage.innerText="Passwords don't match";--%>
<%--                        mismatchMessage.style.display = 'block';--%>
<%--                    } else {--%>
<%--                        mismatchMessage.style.display = 'none'; // Hide mismatch message for other conditions--%>
<%--                    }--%>
<%--                }--%>
<%--            }--%>

<%--            passwordInput1.addEventListener('input', function () {--%>
<%--                updatePass1CheckedImg();--%>
<%--                updateButtonState();--%>
<%--            });--%>

<%--            passwordInput2.addEventListener('input', function () {--%>
<%--                updateButtonState();--%>
<%--                updatePass2CheckedImg();--%>
<%--            });--%>

<%--            passwordInput1.addEventListener('focus', function () {--%>
<%--                tooltip1.style.display = 'block';--%>
<%--            });--%>

<%--            document.addEventListener('click', function (event) {--%>
<%--                if (!passwordInput1.contains(event.target) && !tooltip1.contains(event.target)) {--%>
<%--                    tooltip1.style.display = 'none';--%>
<%--                }--%>
<%--            });--%>
<%--        });--%>
<%--    </script>--%>

    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const passwordInput1 = document.getElementById('npswd1');
            const tooltip1 = document.getElementById('tooltip1');
            const passwordInput2 = document.getElementById('npswd2');
            const otpConfirmBtn = document.getElementById('otp_confirm_btn');
            const pass1CheckedImg = document.getElementById('pass1_checked');
            const pass2CheckedImg = document.getElementById('pass2_checked');
            const pass2UncheckedImg = document.getElementById('pass2_Unchecked');
            const mismatchMessage = document.getElementById('mismatch_message');

            let password2Typed = false;

            function updateTooltip(inputElement, tooltipElement) {
                const password = inputElement.value;

                const hasMinimumChars = password.length >= 8;
                const hasUpperCase = /[A-Z]/.test(password);
                const hasNumber = /\d/.test(password);
                const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

                const charRequirement = tooltipElement.querySelector('.eightchar');
                const upperCaseRequirement = tooltipElement.querySelector('.uppercase');
                const numberRequirement = tooltipElement.querySelector('.oneOrMore');
                const specialCharRequirement = tooltipElement.querySelector('.oneSpl');

                charRequirement.classList.toggle('valid', hasMinimumChars);
                charRequirement.classList.toggle('invalid', !hasMinimumChars);

                upperCaseRequirement.classList.toggle('valid', hasUpperCase);
                upperCaseRequirement.classList.toggle('invalid', !hasUpperCase);

                numberRequirement.classList.toggle('valid', hasNumber);
                numberRequirement.classList.toggle('invalid', !hasNumber);

                specialCharRequirement.classList.toggle('valid', hasSpecialChar);
                specialCharRequirement.classList.toggle('invalid', !hasSpecialChar);

                return hasMinimumChars && hasUpperCase && hasNumber && hasSpecialChar;
            }

            function updatePass1CheckedImg() {
                const password1Valid = updateTooltip(passwordInput1, tooltip1);

                if (password1Valid) {
                    pass1CheckedImg.style.display = 'inline';
                } else {
                    pass1CheckedImg.style.display = 'none';
                }
            }

            function arePasswordsEqual() {
                return passwordInput1.value === passwordInput2.value;
            }

            function updateButtonState() {
                const password1Valid = updateTooltip(passwordInput1, tooltip1);
                const password2Valid = passwordInput2.value.length > 0;

                if (password1Valid && password2Typed) {
                    if (arePasswordsEqual()) {
                        pass2CheckedImg.style.display = 'inline';
                        pass2UncheckedImg.style.display = 'none';
                        mismatchMessage.style.display = 'none';
                    } else {
                        pass2CheckedImg.style.display = 'none';
                        pass2UncheckedImg.style.display = 'inline';
                        mismatchMessage.innerText = "Passwords mismatched";
                        mismatchMessage.style.display = 'block';
                    }
                    otpConfirmBtn.style.opacity = arePasswordsEqual() ? "1" : "0.5";
                    otpConfirmBtn.disabled = !arePasswordsEqual();
                } else {
                    pass2CheckedImg.style.display = 'none';
                    pass2UncheckedImg.style.display = 'none';
                    mismatchMessage.style.display = 'none';
                    otpConfirmBtn.style.opacity = "0.5";
                    otpConfirmBtn.disabled = true;
                }
            }

            passwordInput1.addEventListener('input', function () {
                updatePass1CheckedImg();

                if (password2Typed) {
                    updateButtonState();
                }
            });

            passwordInput2.addEventListener('input', function () {
                password2Typed = true;
                updateButtonState();
            });

            passwordInput1.addEventListener('focus', function () {
                tooltip1.style.display = 'block';
            });

            document.addEventListener('click', function (event) {
                if (!passwordInput1.contains(event.target) && !tooltip1.contains(event.target)) {
                    tooltip1.style.display = 'none';
                }
            });
        });
    </script>



    <div class="row sample" id="mymodal">
        <div class="col s12 m5 offset-m6 offset-l5 l5">
            <div class="card border-radius" id="confimr_pass_modal">
                <div class="card-content padding-card">
                    <div style="margin-left: -10px">
                        <img class="img-fluid" src="resourcesNew1/assets/logo1.svg" style="height: 50px !important">
                    </div>
                    <form method="post" id="confirm_pass" name="confirm_pass"
                          action="${pageContext.request.contextPath}/forgotpwd/resetPwdByUserNameConfirmed">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <div id="passwordInput" class="password-container" style="margin-top: 15px;">
                            <label style="color: #848687;font-weight: 500;font-size: 16px;margin-bottom: 0px">New
                                Password</label><br>
                            <input type="password" name="npassword" id="npswd1" placeholder="Enter new password"
                                   style="border: none; border-bottom: 1px solid #ffa500; text-align: left; font-size: 13px; color: #858585; margin-bottom: 25px; outline: none;max-width: 100%; box-sizing: border-box;" oninput="resetError()">
                            <span class="toggle-password"
                                  onclick="togglePasswordVisibility('npswd1', this.querySelector('img'))">
                                 <img class="show-hide-pass"
                                      src="${pageContext.request.contextPath}/resourcesNew1/assets/show-password.svg"
                                      alt="Toggle Password Visibility">
                                <img id="pass1_checked"
                                     src="${pageContext.request.contextPath}/resourcesNew1/assets/paid.svg"
                                     style="display: none">
                                </span>
                            <div id="tooltip1" class="tooltip" style="display:none;">
                                <p>Your password must contain:</p>
                                <div id="charRequirement1" class="requirement">
                                    <input type="radio" class="eightchar invalid" disabled>
                                    <span class="toolTipFont_size">Minimum 8 characters</span>
                                </div>
                                <div id="upperCaseRequirement1" class="requirement">
                                    <input type="radio" class="uppercase invalid" disabled>
                                    <span class="toolTipFont_size">1 upper case letter</span>
                                </div>
                                <div id="numberRequirement1" class="requirement">
                                    <input type="radio" class="oneOrMore invalid" disabled>
                                    <span class="toolTipFont_size">1 or more numbers</span>
                                </div>
                                <div id="specialCharRequirement1" class="requirement">
                                    <input type="radio" class="oneSpl invalid" disabled>
                                    <span class="toolTipFont_size">1 or more special characters</span>
                                </div>
                            </div>
                        </div>
                        <div id="passwordInput2" class="password-container" style="position: relative;">
                            <label style="color: #848687;font-weight: 500;font-size: 16px;margin-bottom: 0px">Confirm
                                Password</label><br>
                            <input type="password" name="cpassword" id="npswd2" placeholder="Re-enter new password"
                                   style="border: none; border-bottom: 1px solid #ffa500; text-align: left; font-size: 13px; color: #858585;outline: none; max-width: 100%; box-sizing: border-box;" oninput="resetError()">
                            <span style="color: red;display: none" id="oldPassNotAllowed"></span>
                            <span style="color: red;display: none" id="mismatch_message"></span>
                            <span class="toggle-password"
                                  onclick="togglePasswordVisibility('npswd2', this.querySelector('img'))">
                                <img class="show-hide-pass"
                                     src="${pageContext.request.contextPath}/resourcesNew1/assets/show-password.svg"
                                     alt="Toggle Password Visibility">
                            <img id="pass2_checked"
                                 src="${pageContext.request.contextPath}/resourcesNew1/assets/paid.svg"
                                 style="display: none">
                                <img id="pass2_Unchecked"
                                     src="${pageContext.request.contextPath}/resourcesNew1/assets/Declined.svg"
                                     style="display: none">
                            </span>
                            <div id="tooltip2" class="tooltip" style="display:none;">
                                <p>Your password must contain:</p>
                                <div id="charRequirement2" class="requirement">
                                    <input type="radio" class="eightchar invalid" disabled>
                                    <span class="toolTipFont_size">Minimum 8 characters</span>
                                </div>
                                <div id="upperCaseRequirement2" class="requirement">
                                    <input type="radio" class="uppercase invalid" disabled>
                                    <span class="toolTipFont_size">1 upper case letter</span>
                                </div>
                                <div id="numberRequirement2" class="requirement">
                                    <input type="radio" class="oneOrMore invalid" disabled>
                                    <span class="toolTipFont_size">1 or more numbers</span>
                                </div>
                                <div id="specialCharRequirement2" class="requirement">
                                    <input type="radio" class="oneSpl invalid" disabled>
                                    <span class="toolTipFont_size">1 or more special characters</span>
                                </div>
                            </div>

                        </div>
                        <input type="hidden" id="username" name="username" value="${username}"/>
                        <input type="hidden" id="mobileNo" name="mobileNo" value="${mobileNo}"/>
                        <input type="hidden" id="email" name="email" value="${email}"/>
                        <%--                        <input type="hidden" id="backend_otp" name="backend_otp" value="${otp}"/>--%>
                        <input type="hidden" id="backend_otp" name="otp" value="${otp}"/>
                        <input type="hidden" name="requestState" value="second"/>

                    </form>
                    <div class="button-container"
                         style="font-size: small; text-align-last: center; margin-top: 10px; text-align: -webkit-center; background-color: transparent;">
                        <button class="rounded-button" disabled id="otp_confirm_btn" onclick="return confirmNewPass()"
                                style="font-weight: 600;opacity: 0.5;"
                        >Confirm
                        </button>
                    </div>
                    <br>
                    <a href="" id="forget_password_a" style="color: #005BAA;font-size: 13px">< Back to login page</a>
                </div>
            </div>
        </div>
    </div>

    <script>

        document.addEventListener('DOMContentLoaded', function () {
            document.getElementById('forget_password_a').addEventListener('click', function (event) {
                event.preventDefault();
                location.reload(true);
            });
        });
    </script>

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


    <style>
        .password-container {
            position: relative;
        }

        .toggle-password {
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            cursor: pointer;
            color: #ffa500;
        }

        button.rounded-button {
            background-color: #005BAA;
            color: white;
            border: none;
            border-radius: 25px;
            padding: 17px 20px;
            cursor: pointer;
            font-size: 14px;
            opacity: 1; /* Change opacity to 1 to enable the button by default */
        }

        button.rounded-button:disabled {
            opacity: 0.5;
            cursor: pointer;
        }

        input[type="password"] {
            padding-right: 40px; /* Add padding to make room for the toggle icon */
        }

    </style>


</div>

<input type="hidden" id="isOtpValid">

<script>

    <%--function confirmNewPass() {--%>
    <%--    var username = document.getElementById("username").value;--%>
    <%--    var newPass = document.getElementById("npswd2").value;--%>
    <%--    var contextPath = '${pageContext.request.contextPath}';--%>
    <%--    var url = contextPath + '/forgotpwd/isOldPassMatches?' +--%>
    <%--        'username=' + encodeURIComponent(username) +--%>
    <%--        '&newPass=' + encodeURIComponent(newPass);--%>

    <%--    console.log("Request URL: " + url);--%>

    <%--    fetch(url)--%>
    <%--        .then(response => {--%>
    <%--            if (!response.ok) {--%>
    <%--                throw new Error('Network response was not ok');--%>
    <%--            }--%>
    <%--            return response.text();--%>
    <%--        })--%>
    <%--        .then(data => {--%>
    <%--            console.log("Response received: ", data);--%>
    <%--            if (data === "alreadyPresent") {--%>
    <%--                document.getElementById("oldPassNotAllowed").innerText="New password can't be the same as the current one";--%>
    <%--                document.getElementById("oldPassNotAllowed").style.display="block";--%>
    <%--            } else if (data === "new") {--%>
    <%--                var modal = document.getElementById("delclined-modal-id");--%>
    <%--                modal.style.display = "block";--%>
    <%--            }--%>
    <%--        })--%>
    <%--        .catch(error => {--%>
    <%--            console.error("Fetch error: ", error);--%>
    <%--        });--%>
    <%--}--%>




    function confirmNewPass() {
        var modal = document.getElementById("delclined-modal-id");
        var modal1 = document.getElementById("mymodal");
        modal1.style.display = "none";
        modal.style.display = "block";


    }



    function closeDeclined() {
        var modal = document.getElementById("delclined-modal-id");
        modal.style.display = "none";
        var modal1 = document.getElementById("mymodal");
        modal1.style.display = "block";
    }

    function yesConfirmed() {
        var modal1 = document.getElementById("confimr_pass_modal");
        modal1.style.display = "none";
        $("#overlay").show();
        var pass1 = document.getElementById("npswd1").value;
        var pass2 = document.getElementById("npswd2").value;
        var otp = document.getElementById("backend_otp").value;
        var username = document.getElementById("username").value;
        var mobileNo = document.getElementById("mobileNo").value;
        var email = document.getElementById("email").value;

        var message = "Password 1: " + pass1 + "\n" +
            "Password 2: " + pass2 + "\n" +
            "OTP: " + otp + "\n" +
            "Username: " + username + "\n" +
            "Mobile Number: " + mobileNo + "\n" +
            "Email: " + email;
        console.log("pass1 ", pass1)
        console.log("pass2 ", pass2)
        console.log("otp ", otp)
        console.log("username ", username)
        console.log("mobileNo ", mobileNo)
        console.log("email ", email)
        $("#confirm_pass").submit();
    }


</script>


<input type="hidden" id="isEmailTriggered">

<div id="delclined-modal-id" class="declined-modal-class">
    <div class="declined-modal-content">
        <div>
            <div
                    style="border-bottom: 1px solid orange; padding: 15px 24px; text-align: center; font-size: 16px; color: #005baa; font-weight: 500">
                Confirmation
            </div>

            <div class="reason-div"
                 style="padding: 15px 30px; text-align: center;">
                <img
                        src="${pageContext.request.contextPath}/resourcesNew1/assets/confirmation.svg"
                        width="50" height="50"/>
                <p>Are you sure yout want to confirm this as your new password?</p>
            </div>

            <div class="declined-button"
                 style="padding: 10px; display: flex; justify-content: center;gap: 15px; background-color: #EFF8FF;font-family: 'Poppins' !important;border-bottom-left-radius: 10px !important;border-bottom-right-radius: 10px  !important;">
                <button type="button" style="background-color: #EFF8FF !important;font-family: 'Poppins' !important;padding: 9px 15px 10px !important;" class="close-Declined" id="closeDeclined-id"
                        onclick="closeDeclined()">No, Cancel
                </button>
                <button type="button" style="font-family: 'Poppins' !important;padding: 12px 14px!important;" class="confirm-Declined" id="closeDeclined-id"
                        onclick="yesConfirmed()">Yes, Confirm Password
                </button>
            </div>
        </div>


    </div>
</div>


<div id="successMailId" class="confirmation_email_class">
    <div class="confirmation_email_content_class">
        <div>
            <div
                    style="padding: 15px 24px; text-align: center; font-size: 16px; color: #005baa; font-weight: 500">
                Notification
            </div>
            <div class="confirmation_icon_div" style="display: flex;justify-content: center;">
                <img class="confirmation_icon_img1" height="40px">
            </div>

            <div
                    style="padding: 15px 30px; text-align: center;">

                <p id="emailTrueOrFalse" style="color: #586570;font-family: 'Poppins' !important"></p>
            </div>
            <div
                    style="padding: 10px; display: flex; justify-content: center; background-color: #EFF8FF;">
                <button type="button" class="successMail_btn" id="closeSuccessEmail">Back to login page</button>

            </div>
        </div>

    </div>

</div>


<script>
    document.addEventListener('DOMContentLoaded', function () {
        document.getElementById('closeSuccessEmail').addEventListener('click', function (event) {
            event.preventDefault();
            location.reload(true);
        });
    });
</script>


<style>


    #confirmation_email_id {
        display: none;
    }


    .declined-modal-class1 {
        display: none;
        position: fixed;
        z-index: 999;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: hidden;
        background-color: rgba(0, 0, 0, 0.4);
    }


    .confirmation_email_class {
        display: none;
        position: fixed;
        z-index: 999;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: hidden;
        background-color: rgba(0, 0, 0, 0.4);
    }

    /*.declined-modal-content {*/
    /*	background-color: #fefefe;*/
    /*	margin: 15% auto;*/
    /*	padding: 24px;*/
    /*	border: 1px solid #888;*/
    /*	width: 92%;*/
    /*	max-width: 460px;*/
    /*	height: auto;*/
    /*	border-radius: 15px;*/
    /*}*/
    .confirmation_email_content_class {
        background-color: #fefefe;
        margin: 8% auto;
        /*padding: 24px;*/
        border: 1px solid #888;
        width: 92%;
        max-width: 460px;
        /* height: auto !important; */
        border-radius: 15px;
        height: auto;
    }

    .confirmation_email_content_class {
        position: relative;
    }

    .yellow-line-declined {
        background-color: #f0c207;
        height: 0.9px;
        position: absolute;
        top: 51px;
        width: calc(100% - 1px);
        left: 1px;
    }

    .declined-reason-head {
        color: #005baa;
        font-size: 18px;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .confirmation_deposit_btn {
        background-color: #005baa;
        color: white;
        border-radius: 25px;
        border: none;
        padding: 10px 27px;
        font-size: 12px;
        height: 35px;
        outline: none;
        cursor: pointer;
        border-color: white;
        border-style: solid;
        border-width: 1px;
        font-weight: 600;
    }

    .confirmation_deposit_btn:focus, .confirmation_deposit_btn:active {
        background-color: white; /* Same color as default */
    }


    .successMail_btn {
        background-color: #005baa;
        color: white;
        border-radius: 25px;
        border: none;
        padding: 10px 27px;
        font-size: 12px;
        height: 35px;
        outline: none;
        cursor: pointer;
        border-color: #005baa;
        border-style: solid;
        border-width: 1px;
        font-weight: 600;
    }

    .successMail_btn:focus, .confirmation_deposit_btn:active {
        background-color: #005BAA; /* Same color as default */
    }


    .confirmation_cancel_btn {
        background-color: #EFF8FF;
        color: #005baa;
        border-radius: 25px;
        border: none;
        padding: 10px 27px;
        font-size: 12px;
        height: 35px;
        outline: none;
        cursor: pointer;
        border-color: #005baa;
        border-style: solid;
        border-width: 1px;
    }

    .confirmation_cancel_btn:focus, .confirmation_cancel_btn:active {
        background-color: white; /* Same color as default */
    }


    .select2-dropdown {
        border: 2px solid #2e5baa;
    }

    .select2-container--default .select2-selection--single {
        border: none;
    }

    .select-search .select-wrapper input {
        display: none !important;
    }

    /* .select2-container {
         background-color: #fff !important;
        padding: 6px !important;
        border: 2px solid #005baa;
        z-index: 999;
         border-radius:10px !important;
        width: 50% !important;
    } */
    .select-search .select-wrapper input {
        display: none !important;
    }

    .select2-results__options li {
        list-style-type: none;
        padding: 4px 0px 4px 4px !important;
        font-size: 18px !important;
    }

    ul.select2-results__options li {
        max-height: 250px;
        curser: pointer;
    }

    li ul .select2-results__option:hover {
        background-color: #005baa !important;
        color: #fff !important;
    }

    .select-search-hidden .select2-container {
        display: none !important;
    }
</style>


<style>

    .declined-modal-class {
        display: none;
        position: fixed;
        z-index: 999;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: hidden;
        background-color: rgba(0, 0, 0, 0.4);
    }


    .declined-modal-content {
        background-color: #fefefe;
        margin: 15% auto;
        /*padding: 24px;*/
        border: 1px solid #888;
        width: 92%;
        max-width: 460px;
        /* height: auto !important; */
        border-radius: 15px;
        height: auto;
    }

    .declined-modal-content {
        position: relative;
    }


    .confirm-Declined {
        background-color: #005baa;
        color: white;
        border-radius: 25px;
        border: none;
        padding: 10px 27px;
        font-size: 12px;
        height: 35px;
        outline: none;
        cursor: pointer;

    }

    .close-Declined {
        background-color: white;
        color: #005baa;
        border-radius: 25px;
        border-style: solid;
        border-color: #005baa;
        border-width: 2px;
        padding: 10px 27px;
        font-size: 12px;
        height: 35px;
        outline: none;
        cursor: pointer;

    }

    .close-Declined:focus, .close-Declined:active {
        background-color: white;
    }

    .confirm-Declined:focus, .confirm-Declined:active {
        background-color: #005baa;
    }


    .reason-class {
        color: black;
        font-size: 17px;
        display: flex;
        justify-content: center;
        align-items: center;
    }
</style>


<script>


    var otpVerified = document.getElementById("isOtpValid").value = "${isOtpValid}";
    if (otpVerified.toUpperCase() === 'TRUE') {
        console.log(otpVerified.toUpperCase() === 'TRUE');
        var main_modal = document.getElementById("main_modal");
        var passwordConfirmationModal = document.getElementById("mymodal");
        main_modal.style.display = "none";
        passwordConfirmationModal.style.display = "block";
    } else {
        console.log("modal not found")
    }


    function popup() {
        alert("hiii")
        var modal = document.getElementById("mymodal");
        modal.style.display = "block";
    }

    function load1() {
        $("#overlay").show();
        $("#myform").submit();

    }

    function confirmPass() {
        $("#overlay").show();
        $("#confirm_pass")
    }
</script>


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


<script type="text/javascript"
        src="${pageContext.request.contextPath}/resourcesNew1/dataTable/jquery.dataTables.min.js"></script>

<script type="text/javascript"
        src="${pageContext.request.contextPath}/resourcesNew1/dataTable/dataTables.material.min.js"></script>
<script
        src="${pageContext.request.contextPath}/resourcesNew/js/circle-progress.js"></script>
<script src='${pageContext.request.contextPath}/resourcesNew1/select2/dist/js/select2.min.js'
        type='text/javascript'></script>
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


<style>

    .otp_div {
        display: block;
    }

</style>


<script>


    console.log("pass::::: " +${IsPassChanged});
    var isEmailTriggered = "${IsPassChanged}";


    console.log("email :::: " + isEmailTriggered);

    if (isEmailTriggered === "true") {
        var modal = document.getElementById("successMailId");
        var modal1 = document.getElementById("main_modal");
        if (modal) {
            modal1.style.display = "none";
            modal.style.display = "block";
            var ptag = document.getElementById("emailTrueOrFalse");
            if (ptag) {
                ptag.innerHTML = "Your password has been successfully updated. ";
                ptag.style.fontFamily = "'Poppins'";
            }

            var img = document.querySelector(".confirmation_icon_img1");
            if (img) {
                img.src = "${pageContext.request.contextPath}/resourcesNew1/assets/paid.svg";
            }
        }
    } else if (isEmailTriggered === "false") {
        var modal = document.getElementById("successMailId");
        if (modal) {
            modal.style.display = "block";

            var ptag = document.getElementById("emailTrueOrFalse");
            if (ptag) {
                ptag.innerHTML = "Email failed to send";
            }

            var img = document.querySelector(".confirmation_icon_img1");
            if (img) {
                img.src = "${pageContext.request.contextPath}/resourcesNew1/assets/Declined.svg";
            }
        }
    }


</script>


<div id="overlay" id="loading-gif">
    <div id="overlay_text">
        <img class="img-fluid"
             src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
    </div>
</div>

<style>

    #overlay_text {
        position: absolute;
        top: 50%;
        left: 50%;
        font-size: 50px;
        color: #FFF;
        transform: translate(-50%, -50%);
    }

    #overlay_text .img-fluid {
        max-width: 100%;
    }

    #overlay_text img {
        height: 150px;
    }

    #overlay_text {
        position: absolute;
        top: 50%;
        left: 50%;
        font-size: 50px;
        color: #FFF;
        transform: translate(-50%, -50%);
    }

    #overlay_text .img-fluid {
        max-width: 100%;
    }

    #overlay_text img {
        height: 150px;
    }

    #overlay {
        position: fixed;
        display: none;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 10000;
        cursor: pointer;
    }


    .hidden {
        pointer-events: none;
        opacity: 0.5;
        cursor: not-allowed;
        color: #999;
    }
</style>

<script>
    function resetError() {
        console.log("......");
        const errorMsg = document.getElementById("oldPassNotAllowed");
        if (!errorMsg) {
            return;
        }

        const computedStyle = window.getComputedStyle(errorMsg);


        // Make sure to handle other possible values
        if (computedStyle.display === "inline" || computedStyle.display === "inline-block" || computedStyle.display==="block") {
            errorMsg.style.display = "none";

        } else {

        }
    }

    document.addEventListener("DOMContentLoaded", function() {
        resetError();
    });


</script>
</body>
</html>