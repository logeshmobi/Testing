<%@page import="org.springframework.web.context.request.RequestScope" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@page import="com.mobiversa.payment.service.AdminService,com.mobiversa.common.bo.AuditTrail" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">
<head>
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
    <%--    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">--%>

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

        function loadIt() {
            var userInputName = document.getElementById("email-input").value;
            if(validUserNames.includes(userInputName.toUpperCase())){
                $("#overlay").show();
                $("#myform").submit();
                return true;
            }else{
                document.getElementById("invalid_username").innerText="Invalid userName";
                document.getElementById("invalid_username").style.display="block";
                return false;
            }

        }

        function enableBtn() {
            document.getElementById("invalid_username").style.display="none";
            const usernameInput = document.getElementById('email-input');
            const otpButton = document.getElementById('otp-button');

            if (usernameInput.value.trim() !== '') {
                otpButton.disabled = false;
                otpButton.style.opacity="1";
            } else {
                otpButton.disabled = true;
                otpButton.style.opacity="0.5";
            }
        }

    </script>

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

    </style>


    <%--    poppins--%>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

    <link
            href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
            rel="stylesheet">

    <!-- Script tag for Datepicker   -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

        body {
            font-family: "Poppins", sans-serif !important;
        }

        .card .padding-card {
            padding: 25px 37px !important;
        }

        input[type=text]::placeholder {
            color: #D0D0D0;
        }

        .rounded-button {
            background-color: #0056b3;
            color: white;
            border: none;
            padding: 1em 2em;
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

</head>

<body class="login-bg">

<div class="container-fluid" style="background-color: transparent !important;">

    <div class="row">
        <div class="col s12 m5 offset-m6 offset-l5 l5">
            <div class="card border-radius">
                <div class="card-content padding-card">
                    <div class="center-container">
                        <div style="margin-left: -10px">
                            <img class="img-fluid" src="resourcesNew1/assets/logo1.svg" height="50px">
                        </div>
                        <p style="color: #586570;font-weight: 600;font-size: 23px !important;">Forgot Password?</p>
                        <p style="font-size:14px;color: #586570">Enter your username that is associated with your
                            account & we'll send OTP to reset your password</p>
                        <label style="color: #848687;font-weight: 500;font-size: 16px">User Name</label><br>
                        <form method="post" id="myform" name="myform"
                              action="${pageContext.request.contextPath}/forgotpwd/forgetPwdByUserName">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="text" id="email-input" name="username" placeholder="Enter username" oninput="enableBtn()"
                                   style="border: none; border-bottom: 1px solid #ffa500; text-align: left; font-size: 13px; color: #858585; margin-bottom: 25px; outline: none;max-width: 100%; box-sizing: border-box;">
                            <p id="invalid_username" style="color: red;margin-top: -18px;margin-bottom: 20px;"></p>
                            <div class="button-container"
                                 style="font-size: small; text-align-last: center; margin-top: 10px; text-align: -webkit-center; background-color: transparent;">
                                <button class="rounded-button" id="otp-button" onclick="return loadIt()" disabled style="font-weight: 800;opacity: 0.5">Send OTP
                                </button>
                            </div>
                            <br>
                            <a href="" id="forget_password_a" style="color: #005BAA;font-size: 13px;font-weight: 600"><
                                Back to login page</a>
                        </form>
                    </div>


                </div>
            </div>
        </div>
    </div>
</div>





<script>
    var statusMsg = "${statusMsg}";
</script>

<script>

    document.addEventListener('DOMContentLoaded', function () {
        document.getElementById('forget_password_a').addEventListener('click', function (event) {
            event.preventDefault();
            location.reload(true);
        });
    });
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


<div class="visible-xs visible-sm extendedChecker"></div>

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
        z-index: 2;
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

    var validUserNames = JSON.parse('${validUserNames}');
    console.log(validUserNames[1]);
</script>

</body>
</html>