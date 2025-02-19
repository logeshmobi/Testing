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
    function enableOtpBtn(){
        const usernameInput = document.getElementById('otp');
        const otpButton = document.getElementById('otp-btn-otp');

        if (usernameInput.value.trim() !== '') {
            otpButton.disabled = false;
            otpButton.style.opacity="1";
        } else {
            otpButton.disabled = true;
            otpButton.style.opacity="0.5";
        }
    }



</script>


<div class="container-fluid" style="background-color: transparent !important;">

    <div class="row otp_div" id="main_modal">
        <div class="col s12 m5 offset-m6 offset-l5 l5">
            <div class="card border-radius">
                <div class="card-content padding-card" id="new_old_pass">

                </div>
            </div>
        </div>
    </div>
































</div>




<input type="hidden" id="isEmailTriggered">




<div id="successMailId" class="confirmation_email_class">
    <div class="confirmation_email_content_class">
        <div>
            <div style="border-bottom: 1px solid orange !important; padding: 15px 24px!important; text-align: center!important; font-size: 14px!important; color: #005baa!important; font-weight: 600 !important;"
            >
                Notification
            </div>
            <div class="confirmation_icon_div" style="display: flex;justify-content: center; margin-top: 13px !important;">
                <img class="confirmation_icon_img1" height="55px">
            </div>

            <div
                    style="padding: 0px 30px 10px 30px; text-align: center;">

                <p id="emailTrueOrFalse" style="color: #586570; font-size: 15px";></p>
            </div>
            <div
                    style="padding: 10px; display: flex; justify-content: center; background-color: #005baa25;">

                <button type="button" class="successMail_btn" id="closeSuccessEmail" style="font-family: 'Poppins', sans-serif; !important;">
                    Go to login
                </button>

            </div>
        </div>

    </div>

</div>


<script>
    document.addEventListener('DOMContentLoaded', function () {
        document.getElementById('closeSuccessEmail').addEventListener('click', function (event) {
            $("#overlay").show();
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


    .confirm-Declined{
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
                ptag.innerHTML = "You can now login with your new password.";
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



</body>
</html>