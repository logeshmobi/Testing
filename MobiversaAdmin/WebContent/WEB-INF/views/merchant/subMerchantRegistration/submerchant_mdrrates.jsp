<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>sub merchant's MDR</title>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
          rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resourcesNew/css/niceCountryInput.css">
    <script src="${pageContext.request.contextPath}/resourcesNew/js/niceCountryInput.js"></script>

    <script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>


    <style>
        @import url('https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap');

        .container-fluid {
            padding: 20px 18px !important;
            font-family: "Poppins", sans-serif !important;
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
            z-index: 100;
            cursor: pointer;
        }


        .heading_text {
            font-family: "Poppins", sans-serif !important;
            font-weight: 600 !important;
            font-size: 20px !important;
        }


        .input-field.col label {
            font-size: 18px !important;
        }

        input[type="text"]:not(.browser-default) {
            border-bottom: 1.5px solid orange !important;
            font-size: 14px;
            color: #929292 !important;
            font-family: "Poppins", sans-serif !important;
        }

        input[type="text"]:not(.browser-default)::placeholder {
            color: #D0D0D0;
            font-family: "Poppins";
        }

        @media only screen and (min-width: 601px) {
            .row .col.m5 {
                width: 45%;
            }

            .mdr_card .input-field.col label {
                font-size: 14px !important;
            }
        }

        @media only screen and (min-width: 993px) {
            .row .col.l5 {
                width: 45% !important;
            }

            .mdr_card .input-field.col label {
                font-size: 17px !important;
            }
        }

        #country_label {
            transform: translateY(-30px) scale(0.8) !important;
            transform-origin: 0 0;
        }

        .input-field > label:not(.label-icon).active {
            transform: translateY(-18px) scale(0.8) !important;
            transform-origin: 0 0;
        }

        .mb-0 {
            margin-bottom: 0;
        }

        .mb-4 {
            margin-bottom: 16px;
        }

        .btn_row {
            text-align: center;
        }

        .row {
            margin-bottom: 15px;
        }

        .blue-btn, .blue-btn:hover, .blue-btn:focus {
            background-color: #005baa;
            border-radius: 50px;
            color: #fff;
            padding: 0 25px;
        }

        .card-inner {
            box-shadow:
                    5px 5px 10px -3px rgba(45, 45, 45, 0.05),  /* Right shadow */
                    -5px 5px 10px -3px rgba(45, 45, 45, 0.05), /* Left shadow */
                    0 5px 10px -3px rgba(45, 45, 45, 0.05) !important; /* Bottom shadow */
        }



        .blue-btn:hover, .blue-btn:focus{
            box-shadow:
                    5px 5px 10px -3px rgba(0, 90, 170, 0.2),  /* Right shadow */
                    -5px 5px 10px -3px rgba(0, 90, 170, 0.2), /* Left shadow */
                    0 5px 10px -3px rgba(0, 90, 170, 0.2) !important; /* Bottom shadow */
        }

        input:not([type]):focus:not([readonly]), input[type="text"]:not(.browser-default):focus:not([readonly]) {
            box-shadow: initial;
        }


    </style>

    <style>


        .popup_messages {
            color: #515151;
            font-size: 16px;
        }

        .confirmbtn {
            background-color: #005baa;
            border-radius: 50px;
            height: 33px !important;
            line-height: 33px !important;
            padding: 0 30px;
            font-size: 12px;
            margin: 0 5px;
            font-family: "Poppins";


        }

        .confirmbtn:hover, .confirmbtn:focus {
            background-color: #005baa !important;
            box-shadow:
                    5px 5px 10px -3px rgba(0, 90, 170, 0.2),  /* Right shadow */
                    -5px 5px 10px -3px rgba(0, 90, 170, 0.2), /* Left shadow */
                    0 5px 10px -3px rgba(0, 90, 170, 0.2) !important; /* Bottom shadow */
        }

        .cancelbtn {
            background-color: transparent;
            border-radius: 50px;
            height: 33px !important;
            line-height: 31px !important;
            padding: 0 25px;
            font-size: 12px;
            border: 1.5px solid #005baa;
            margin: 0 5px;
            color: #005baa;
            font-family: "Poppins";
        }

        .cancelbtn:hover, .cancelbtn:focus {
            background-color: transparent !important;
            color: #005baa !important;
            box-shadow:
                    5px 5px 10px -3px rgba(0, 90, 170, 0.2),  /* Right shadow */
                    -5px 5px 10px -3px rgba(0, 90, 170, 0.2), /* Left shadow */
                    0 5px 10px -3px rgba(0, 90, 170, 0.2) !important; /* Bottom shadow */
        }

        .confirm_overlay, .result_overlay {
            display: none;
            position: fixed;
            z-index: 100;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            /*background-color: rgb(0, 0, 0);*/
            background-color: rgba(0, 0, 0, 0.2);
            scrollbar-width: none;
        }

        .confirm_modal, .result_modal {
            background-color: #fff;
            border-radius: 10px !important;
            margin: 1% auto;
        }

        .update_popup-modal {
            background-color: #fff;
            border-radius: 10px !important;
            margin: 1% auto;
        }

        .modal-header {

            padding: 10px 6px;
            height: auto;
            width: 100%;
            text-align: center;
            border-bottom: 1.5px solid #F5A623;
            font-size: 16px;
        }

        .footer {
            background-color: #EFF8FF !important;
            display: flex;
            align-items: center;
            justify-content: center;
        }


        .align-center {
            text-align: center;
        }


        .modal_row {
            width: 100%;
            height: 100%;
            align-content: center;
        }

        .modal-header {
            color: #005BAA;
            text-align: center;
            padding: 10px;
            border-bottom: 1.5px solid orange;
            font-weight: 500;
            font-size: 16px;
        }

        .modal-content {
            padding: 15px 24px;
        }

        .modal-footer {
            background-color: #EFF8FF;
            border-bottom-left-radius: 10px;
            border-bottom-right-radius: 10px;
            padding: 10px 0;
        }

        .modal-content {
            padding: 10px 30px;
            font-family: "Poppins", sans-serif;
            color: #515151 !important;
        }

        .closebtn {
            background-color: #005baa;
            border-radius: 50px;
            height: 33px !important;
            line-height: 33px !important;
            padding: 0 30px;
            font-size: 12px;
            font-family: "Poppins";
        }

        .closebtn:hover, .closebtn:focus {
            background-color: #005baa !important;
            padding: 0 30px;
            box-shadow:
                    5px 5px 10px -3px rgba(0, 90, 170, 0.2),  /* Right shadow */
                    -5px 5px 10px -3px rgba(0, 90, 170, 0.2), /* Left shadow */
                    0 5px 10px -3px rgba(0, 90, 170, 0.2) !important; /* Bottom shadow */
        }

        .content_updatepopup {
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            padding: 7px 30px !important;

        }

        .businessdetails {
            opacity: 0.6 !important;
        }

        .businessdetails input {
            pointer-events: none;
        }

        .readonly-country-selector {
            pointer-events: none;
        }

        .blue-btn:hover, . blue-btn:focus{
            box-shadow:
                    5px 5px 10px -3px rgba(0, 90, 170, 0.2),  /* Right shadow */
                    -5px 5px 10px -3px rgba(0, 90, 170, 0.2), /* Left shadow */
                    0 5px 10px -3px rgba(0, 90, 170, 0.2) !important; /* Bottom shadow */
        }

        /*  mdr styles  */

        .mb-0 {
            margin-bottom: 0 !important;
        }

        #overlay {
            z-index: 100 !important;
        }

        .button-tabs, .mdr_button-tabs {
            color: #85858570;
            width: 100%;
            background: #fff;
            border: none;
            border-bottom: 4px solid #85858570;
            padding: 15px;
            font-weight: 600;
            font-family: "Poppins", sans-serif;
            cursor: pointer;
        }

        .p-0 {
            padding: 0 !important;
        }

        .button-tabs:focus, .mdr_button-tabs:focus {
            background-color: #fff !important;
        }

        .tab_active {
            color: #005baa;
            border-bottom: 4px solid #005baa;
            cursor: pointer;
        }


        .ws-nowrap {
            white-space: nowrap;
        }

        .mdr_card .input-field.col label {
            font-size: 17px;
        }

        .input-field.col input::placeholder {
            font-size: 14px !important;
        }


        input[type="text"]:not(.browser-default) {
            border-bottom: 1.5px solid orange !important;
            color: #707070;
            font-size: 13px !important;
            font-family: "Poppins", sans-serif;
        }


        input[type="text"]:not (.browser-default )::placeholder {
            color: #D0D0D0 !important;
        }

        input[type="text"]:not (.browser-default ):focus:not ([readonly] ) + label {
            color: #707070;
        }


        .mdr_button-tabs {
            white-space: nowrap;
        }

        .paymentmethod_text {
            color: #707070;
            font-weight: 600;
            font-size: 14px;
            text-align: left;
        }

        input[type=text]:not(.browser-default) {
            font-size: 13px !important;
            color: #707070 !important;
        }

        .disabled {
            opacity: 0.5;
            pointer-events: none;
        }

        .mx-0 {
            margin-left: 0 !important;
            margin-right: 0 !important;
        }

        input[type=text]:not(.browser-default ):focus:not([readonly] ) + label {
            color: #707070 !important;
        }


    </style>


</head>
<body>

<div class="test" id="pop-bg-color"></div>
<div id="overlay-popup"></div>


<div id="overlay">
    <div id="overlay_text">
        <img class="img-fluid"
             src="/MobiversaAdmin/resourcesNew1/assets/loader.gif">
    </div>
</div>

<div class="container-fluid mb-0" id="pop-bg">

    <div class="row">
        <div class="col s12">
            <div class="card blue-bg text-white">
                <div class="card-content">
                    <div class="d-flex align-items-center">
                        <h3 class="text-white mb-0 ">
                            <strong class="heading_text">Sub Merchant MDR Rates</strong>

                        </h3>
                    </div>
                </div>
            </div>
        </div>

    </div>


    <div class="row">
        <div class="col s12">
            <div class="card blue-bg text-white">
                <div class="card-content">
                    <div class="">
                        <h3 class="text-white mb-4">
                            <strong class="heading_text">Business Details</strong>
                        </h3>
<%--                        <c:forEach items="${merchantDetails}" var="merchant">--%>
                        <div class="businessdetails">
                            <div class="row">
                                <div class="input-field  col s12 m5 l5">
                                    <input type="text" id="businessName" name="businessName"
                                           placeholder="AZ EMPIRE BEAUTY & HEALTH CARE"  value = "${merchant.businessName}" readonly/>
                                    <label for="businessName">Business Name</label>
                                </div>
                                <div class="input-field col s12 m5 offset-m1 offset-l1 l5">
                                    <input type="text" id="email" name="email"
                                           placeholder="info.beauteegorgeous@gmail.com" value = "${merchant.email}"  readonly/>
                                    <label for="email">Email</label>
                                </div>

                            </div>

                            <div class="row">
                                <div class="input-field  col s12 m5 l5">
                                    <input type="text" id="website" name="website"
                                           placeholder="www.infobeauti.my" value = "${merchant.website}" readonly/>
                                    <label for="website">Website</label>
                                </div>
                                <div class="input-field col s12 m5 offset-m1 offset-l1 l5">
                                    <input type="text" id="industry" name="industry"
                                           placeholder="Cosmetic & Beauty" value = "${merchant.natureOfBusiness}" readonly/>
                                    <label for="industry">Industry</label>
                                </div>

                            </div>

                            <div class="row" style="margin-top: 25px;">
                                <div class="input-field  col s12 m5 l5">
                                    <label for="country" id="country_label">Country</label>
                                    <input type="hidden" id="country_hidden" name="country">
                                    <input type="hidden" id="country_name_hidden" name="country_name"  value = "${merchant.country}" >

                                    <div id="country" name="country"
                                         class="niceCountryInputSelector readonly-country-selector"
                                         data-selectedcountry="MY"
                                         data-showcontinentsonly="false" data-showflags="true"
                                         data-i18nall="All selected"
                                         data-i18nnofilter="No selection" data-i18nfilter="Filter"
                                         data-onchangecallback="onChangeCallback" data-showspecial="true"></div>


                                </div>
                            </div>
                        </div>
<%--                        </c:forEach>--%>
                    </div>

                </div>
            </div>
        </div>


    </div>


    <div class="row">
        <div class="col s12">
            <div class="card blue-bg text-white">
                <div class="card-content">
                    <div class=" align-items-center">
                        <h3 class="text-white mb-0 ">
                            <strong class="heading_text">MDR Rates</strong>
                        </h3>


                        <%--                        mdr rates --%>

                        <div class="row" style="margin-top: 10px;">
                            <div class="col s12">
                                <div class="card card-inner blue-bg text-white">
                                    <div class="card-content mdr_card ">

                                        <div class="row mb-0 product_btn_row"
                                             style="padding: 0.50rem 0.75rem;">


                                            <div class="col s12 m3 l3 p-0" id="fpx_col">
                                                <button type="button" class="mdr_button-tabs"
                                                        id="fpx_mdr_btn">Internet Banking
                                                </button>
                                            </div>

                                            <div class="col s12 m3 l3 p-0" id="cards_col">
                                                <button type="button" class="mdr_button-tabs"
                                                        id="cards_mdr_btn">Card
                                                </button>
                                            </div>

                                            <div class="col s12 m3 l3 p-0" id="ewallet_col">
                                                <button type="button" class="mdr_button-tabs"
                                                        id="ewallet_mdr_btn">eWallets
                                                </button>
                                            </div>

                                            <div class="col s12 m3 l3 p-0" id="payout_col">
                                                <button type="button" class="mdr_button-tabs"
                                                        id="payout_mdr_btn">Payout
                                                </button>
                                            </div>

                                        </div>

                                        <div class="row mb-0">
                                            <div class="col s12 m12 l12">
													<span
                                                            style="color: red; font-size: 14px; text-align: center; padding: 5px 0;"
                                                            id="error_message"></span>
                                            </div>
                                        </div>


                                        <div class="row content" id="fpx_container"
                                             style="padding: 2rem 0; display: none;">
                                            <div class="col s12 m4 l4 input-field" style="">
                                                <p class="paymentmethod_text">FPX Internet Banking
                                                    MDR(%)</p>
                                            </div>
                                            <div class="col s12 m2 l2 input-field ">
                                                <input placeholder="0.00" name="fpx_merchantmdr"
                                                       id="fpx_merchantmdr" type="text" class=""
                                                       value=""> <label
                                                    for="fpx_merchantmdr" style="white-space: nowrap;"
                                                    inputmode="decimal">Merchant MDR</label>
                                            </div>
                                            <div class="col s12 m2 l2 input-field ">
                                                <input placeholder="0.00" name="fpx_hostmdr"
                                                       id="fpx_hostmdr" type="text" class=""
                                                       value="" > <label
                                                    for="fpx_hostmdr" style="white-space: nowrap;"
                                                    inputmode="decimal">Host MDR</label>
                                            </div>
                                            <div class="col s12 m2 l2 input-field disabled">
                                                <input placeholder="0.00" name="fpx_mobimdr"
                                                       id="fpx_mobimdr" type="text" class=""
                                                       value="" readonly> <label
                                                    for="fpx_mobimdr" style="white-space: nowrap;"
                                                    inputmode="decimal">Mobi MDR</label>
                                            </div>
                                            <div class="col s12 m2 l2 input-field ">
                                                <input placeholder="0.00" name="fpx_minimummdr"
                                                       id="fpx_minimummdr" type="text" class=""
                                                       value=""> <label
                                                    for="fpx_minimummdr" style="white-space: nowrap;"
                                                    inputmode="decimal">Minimum MDR</label>
                                            </div>
                                        </div>


                                        <%--                                cards mdr inputs    --%>


                                        <div id="cards_container" class="content"
                                             style="display: none;">

                                            <div class="row">
                                                <div class="col s12 m4 l4 input-field"></div>
                                                <div class="input-field col s12 m2 l1">
                                                    <img
                                                            src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/visacard.svg"
                                                            alt="visa" width="80" height="80">
                                                </div>
                                                <div
                                                        class="col s12 m2 l1 offset-l2 offset-m1 input-field ">
                                                    <img
                                                            src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/mastercard.svg"
                                                            alt="mastercard" width="80" height="80">
                                                </div>
                                                <div
                                                        class="col s12 m2 l1 offset-l2 offset-m1 input-field ">
                                                    <img
                                                            src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/unionpay.svg"
                                                            alt="unionpay" width="80" height="80">
                                                </div>
                                            </div>


                                            <%--                                    local debit card --%>
                                            <div class="row" style="padding: 0.5rem 0;">
                                                <div class="col s12 m4 l4 input-field" style="">
                                                    <p class="paymentmethod_text">MDR For Local Debit
                                                        Card(%)</p>
                                                </div>
                                                <div class="col s12 m2 l2  input-field ">
                                                    <%--                                                <img class="show-on-small" src="${pageContext.request.contextPath}/resourcesNew1/assets/visacard.svg" width="25" height="25">--%>
                                                    <input placeholder="0.00" name="localdebitvisamdr"
                                                           id="localdebitvisamdr" type="text" class=""
                                                           value=""
                                                           inputmode="decimal"> <label
                                                        for="localdebitvisamdr" style="white-space: nowrap;">MDR</label>
                                                </div>
                                                <div
                                                        class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
                                                    <input placeholder="0.00" name="localdebitmastermdr"
                                                           id="localdebitmastermdr" type="text" class=""
                                                           inputmode="decimal"
                                                           value=""> <label
                                                        for="localdebitmastermdr"
                                                        style="white-space: nowrap;">MDR</label>
                                                </div>
                                                <div
                                                        class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
                                                    <input placeholder="0.00" name="localdebitunionmdr"
                                                           id="localdebitunionmdr" type="text" class=""
                                                           inputmode="decimal"
                                                           value=""> <label
                                                        for="localdebitunionmdr"
                                                        style="white-space: nowrap;">MDR</label>
                                                </div>
                                            </div>

                                            <%--                                        local credit card --%>

                                            <div class="row" style="padding: 0.5rem 0;">
                                                <div class="col s12 m4 l4 input-field" style="">
                                                    <p class="paymentmethod_text">MDR For Local Credit
                                                        Card(%)</p>
                                                </div>
                                                <div class="col s12 m2 l2  input-field ">
                                                    <input placeholder="0.00" name="localcreditvisamdr"
                                                           id="localcreditvisamdr" type="text" class=""
                                                           inputmode="decimal"
                                                           value=""> <label
                                                        for="localcreditvisamdr"
                                                        style="white-space: nowrap;">MDR</label>
                                                </div>
                                                <div
                                                        class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
                                                    <input placeholder="0.00" name="localcreditmastermdr"
                                                           id="localcreditmastermdr" type="text" class=""
                                                           inputmode="decimal"
                                                           value=""> <label
                                                        for="localcreditmastermdr"
                                                        style="white-space: nowrap;">MDR</label>
                                                </div>
                                                <div
                                                        class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
                                                    <input placeholder="0.00" name="localcreditunionmdr"
                                                           id="localcreditunionmdr" type="text" class=""
                                                           inputmode="decimal"
                                                           value="">
                                                    <label for="localcreditunionmdr"
                                                           style="white-space: nowrap;">MDR</label>
                                                </div>
                                            </div>

                                            <%--                                        foreign debit card --%>

                                            <div class="row" style="padding: 0.5rem 0;">
                                                <div class="col s12 m4 l4 input-field" style="">
                                                    <p class="paymentmethod_text">MDR For Foreign Debit
                                                        Card(%)</p>
                                                </div>
                                                <div class="col s12 m2 l2  input-field ">
                                                    <input placeholder="0.00" name="foreigndebitvisamdr"
                                                           id="foreigndebitvisamdr" type="text" class=""
                                                           inputmode="decimal"
                                                           value=""> <label
                                                        for="foreigndebitvisamdr"
                                                        style="white-space: nowrap;">MDR</label>
                                                </div>
                                                <div
                                                        class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
                                                    <input placeholder="0.00" name="foreigndebitmastermdr"
                                                           id="foreigndebitmastermdr" type="text" class=""
                                                           inputmode="decimal"
                                                           value=""> <label
                                                        for="foreigndebitmastermdr"
                                                        style="white-space: nowrap;">MDR</label>
                                                </div>
                                                <div
                                                        class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
                                                    <input placeholder="0.00" name="foreigndebitunionmdr"
                                                           id="foreigndebitunionmdr" type="text" class=""
                                                           inputmode="decimal"
                                                           value="">
                                                    <label for="foreigndebitunionmdr"
                                                           style="white-space: nowrap;">MDR</label>
                                                </div>
                                            </div>

                                            <%--                                        foreign credit card --%>

                                            <div class="row" style="padding: 0.5rem 0;">
                                                <div class="col s12 m4 l4 input-field" style="">
                                                    <p class="paymentmethod_text">MDR For Foreign Credit
                                                        Card(%)</p>
                                                </div>
                                                <div class="col s12 m2 l2 input-field ">
                                                    <input placeholder="0.00" name="foreigncreditvisamdr"
                                                           id="foreigncreditvisamdr" type="text" class=""
                                                           inputmode="decimal"
                                                           value=""> <label
                                                        for="foreigncreditvisamdr"
                                                        style="white-space: nowrap;">MDR</label>
                                                </div>
                                                <div
                                                        class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
                                                    <input placeholder="0.00" name="foreigncreditmastermdr"
                                                           id="foreigncreditmastermdr" type="text" class=""
                                                           inputmode="decimal"
                                                           value="">
                                                    <label for="foreigncreditmastermdr"
                                                           style="white-space: nowrap;">MDR</label>
                                                </div>
                                                <div
                                                        class="col s12 m2 l2 offset-l1 offset-m1 input-field ">
                                                    <input placeholder="0.00" name="foreigncreditunionmdr"
                                                           id="foreigncreditunionmdr" type="text" class=""
                                                           inputmode="decimal"
                                                           value="">
                                                    <label for="foreigncreditunionmdr"
                                                           style="white-space: nowrap;">MDR</label>
                                                </div>
                                            </div>


                                        </div>


                                        <div class="row content" id="ewallet_container"
                                             style="padding: 2rem 0; display: none;">
                                            <%--                                       boost mdr --%>


                                            <div class="row mx-0">
                                                <div class="col s12 m4 l4 input-field" style="">
                                                    <p class="paymentmethod_text">MDR For Boost (%)</p>
                                                </div>
                                                <div class="col s12 m2 l2 input-field ">
                                                    <input placeholder="0.00" name="boost_merchantmdr"
                                                           id="boost_merchantmdr" type="text" class=""
                                                           inputmode="decimal"
                                                           value=""> <label
                                                        for="boost_merchantmdr" style="white-space: nowrap;">Merchant
                                                    MDR</label>
                                                </div>
                                                <div class="col s12 m2 l2 input-field ">
                                                    <input placeholder="0.00" name="boost_hostmdr"
                                                           id="boost_hostmdr" type="text" inputmode="decimal"
                                                           class="" value="" >
                                                    <label for="boost_hostmdr" style="white-space: nowrap;">Host
                                                        MDR</label>
                                                </div>
                                                <div class="col s12 m2 l2 input-field disabled">
                                                    <input placeholder="0.00" name="boost_mobimdr"
                                                           id="boost_mobimdr" type="text" inputmode="decimal"
                                                           class="" value="" readonly>
                                                    <label for="boost_mobimdr" style="white-space: nowrap;">Mobi
                                                        MDR</label>
                                                </div>
                                                <div class="col s12 m2 l2 input-field ">
                                                    <input placeholder="0.00" name="boost_minimummdr"
                                                           id="boost_minimummdr" type="text" inputmode="decimal"
                                                           class="" value=""> <label
                                                        for="boost_minimummdr" style="white-space: nowrap;">Minimum
                                                    MDR</label>
                                                </div>
                                            </div>

                                            <div class="row mx-0">
                                                <div class="col s12 m4 l4 input-field" style="">
                                                    <p class="paymentmethod_text">MDR For GrabPay (%)</p>
                                                </div>
                                                <div class="col s12 m2 l2 input-field ">
                                                    <input placeholder="0.00" name="grab_merchantmdr"
                                                           id="grab_merchantmdr" type="text" inputmode="decimal"
                                                           class="" value=""> <label
                                                        for="grab_merchantmdr" style="white-space: nowrap;">Merchant
                                                    MDR</label>
                                                </div>
                                                <div class="col s12 m2 l2 input-field ">
                                                    <input placeholder="0.00" name="grab_hostmdr"
                                                           id="grab_hostmdr" type="text" inputmode="decimal"
                                                           class="" value="" >
                                                    <label for="grab_hostmdr" style="white-space: nowrap;">Host
                                                        MDR</label>
                                                </div>
                                                <div class="col s12 m2 l2 input-field disabled">
                                                    <input placeholder="0.00" name="grab_mobimdr"
                                                           id="grab_mobimdr" type="text" inputmode="decimal"
                                                           class="" value="" readonly>
                                                    <label for="grab_mobimdr" style="white-space: nowrap;">Mobi
                                                        MDR</label>
                                                </div>
                                                <div class="col s12 m2 l2 input-field ">
                                                    <input placeholder="0.00" name="grab_minimummdr"
                                                           id="grab_minimummdr" type="text" inputmode="decimal"
                                                           class="" value=""> <label
                                                        for="grab_minimummdr" style="white-space: nowrap;">Minimum
                                                    MDR</label>
                                                </div>
                                            </div>

                                            <div class="row mx-0">
                                                <div class="col s12 m4 l4 input-field" style="">
                                                    <p class="paymentmethod_text">MDR For Touch'N Go (%)</p>
                                                </div>
                                                <div class="col s12 m2 l2 input-field ">
                                                    <input placeholder="0.00" name="tng_merchantmdr"
                                                           id="tng_merchantmdr" type="text" inputmode="decimal"
                                                           class="" value=""> <label
                                                        for="tng_merchantmdr" style="white-space: nowrap;">Merchant
                                                    MDR</label>
                                                </div>
                                                <div class="col s12 m2 l2 input-field ">
                                                    <input placeholder="0.00" name="tng_hostmdr"
                                                           id="tng_hostmdr" type="text" inputmode="decimal"
                                                           class="" value="" >
                                                    <label for="tng_hostmdr" style="white-space: nowrap;">Host
                                                        MDR</label>
                                                </div>
                                                <div class="col s12 m2 l2 input-field disabled">
                                                    <input placeholder="0.00" name="tng_mobimdr"
                                                           id="tng_mobimdr" type="text" inputmode="decimal"
                                                           class="" value="" readonly>
                                                    <label for="tng_mobimdr" style="white-space: nowrap;">Mobi
                                                        MDR</label>
                                                </div>
                                                <div class="col s12 m2 l2 input-field ">
                                                    <input placeholder="0.00" name="tng_minimummdr"
                                                           id="tng_minimummdr" type="text" inputmode="decimal"
                                                           class="" value=""> <label
                                                        for="tng_minimummdr" style="white-space: nowrap;">Minimum
                                                    MDR</label>
                                                </div>
                                            </div>

                                            <div class="row mx-0">
                                                <div class="col s12 m4 l4 input-field" style="">
                                                    <p class="paymentmethod_text">MDR For Shopee Pay (%)</p>
                                                </div>
                                                <div class="col s12 m2 l2 input-field ">
                                                    <input placeholder="0.00" name="spp_merchantmdr"
                                                           id="spp_merchantmdr" type="text" inputmode="decimal"
                                                           class="" value=""> <label
                                                        for="spp_merchantmdr" style="white-space: nowrap;">Merchant
                                                    MDR</label>
                                                </div>
                                                <div class="col s12 m2 l2 input-field ">
                                                    <input placeholder="0.00" name="spp_hostmdr"
                                                           id="spp_hostmdr" type="text" inputmode="decimal"
                                                           class="" value="" >
                                                    <label for="spp_hostmdr" style="white-space: nowrap;">Host
                                                        MDR</label>
                                                </div>
                                                <div class="col s12 m2 l2 input-field disabled">
                                                    <input placeholder="0.00" name="spp_mobimdr"
                                                           id="spp_mobimdr" type="text" inputmode="decimal"
                                                           class="" value="" readonly>
                                                    <label for="spp_mobimdr" style="white-space: nowrap;">Mobi
                                                        MDR</label>
                                                </div>
                                                <div class="col s12 m2 l2 input-field ">
                                                    <input placeholder="0.00" name="spp_minimummdr"
                                                           id="spp_minimummdr" type="text" inputmode="decimal"
                                                           class="" value=""> <label
                                                        for="spp_minimummdr" style="white-space: nowrap;">Minimum
                                                    MDR</label>
                                                </div>
                                            </div>

                                        </div>

                                        <%----%>

                                        <%--                                    payout content--%>

                                        <div class="row content" id="payout_container"
                                             style="padding: 2rem 0; display: none;">
                                            <div class="col s12 m4 l4 input-field" style="">
                                                <p class="paymentmethod_text">MDR For Payout (%)</p>
                                            </div>
                                            <div class="col s12 m2 l2 input-field ">
                                                <input placeholder="0.00" name="payout_merchantmdr"
                                                       id="payout_merchantmdr" type="text" inputmode="decimal"
                                                       class="" value="${response.payoutMerchantMDR}"> <label
                                                    for="payout_merchantmdr" style="white-space: nowrap;">Merchant
                                                MDR</label>
                                            </div>
                                            <div class="col s12 m2 l2 input-field ">
                                                <input placeholder="0.00" name="payout_hostmdr"
                                                       id="payout_hostmdr" type="text" inputmode="decimal"
                                                       class="" value="${response.payoutHostMDR}" >
                                                <label for="payout_hostmdr" style="white-space: nowrap;">Host
                                                    MDR</label>
                                            </div>
                                            <div class="col s12 m2 l2 input-field disabled">
                                                <input placeholder="0.00" name="payout_mobimdr"
                                                       id="payout_mobimdr" type="text" inputmode="decimal"
                                                       class="" value="${response.payoutMobiMDR}" readonly>
                                                <label for="payout_mobimdr" style="white-space: nowrap;">Mobi
                                                    MDR</label>
                                            </div>
                                            <div class="col s12 m2 l2 input-field ">
                                                <input placeholder="0.00" name="payout_minimummdr"
                                                       id="payout_minimummdr" type="text" inputmode="decimal"
                                                       class="" value="${response.payoutMinimumMDR}"> <label
                                                    for="payout_minimummdr" style="white-space: nowrap;">Minimum
                                                MDR</label>
                                            </div>
                                        </div>


                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="row btn_row">
                            <button type="button" id="submit_btn" class="btn  blue-btn">Submit</button>
                        </div>

                    </div>
                </div>
            </div>
        </div>

    </div>


    <div class="confirm_overlay" id="confirm_overlay">
        <div class="row modal_row">
            <div class="col offset-l4 offset-m3 s12 m6 l4">
                <div id="confirm-modal" class="confirm_modal">
                    <form action="${pageContext.request.contextPath}/submerchant/operation-child/request/mdrRates" method="post"
                          id="mdrDetailsForm">
                        <div class="modal-header">
                            <p class="mb-0">Confirmation</p>
                        </div>
                        <div class="modal-content ">
                            <div class="align-center">
                                <img src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Confirmation.svg"
                                     width="35" height="35">
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                            <input type="hidden" name="mid" id="midToUpdateMDR" value="${merchant.mid.subMerchantMID}">
                            <input type="hidden" name="mdrRates" id="updateMDRDetails" value="">
							<input type="hidden" name="businessName" id="subMerchantBusinessName" value="${merchant.businessName}">


                            <p class="align-center popup_messages">Would you like to proceed with these MDR rates?</p>
                        </div>
                        <div class="align-center modal-footer footer">
                            <button id="close_btn" class="btn cancelbtn" type="button" onclick="closePopupModal()"
                                    name="action">Cancel
                            </button>
                            <button id="confirm_btn" class="btn confirmbtn " type="button" onclick="submitDetails()"
                                    name="action">Confirm
                            </button>

                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="result_overlay" id="result_overlay">
        <div class="row modal_row">
            <div class="col offset-l4 offset-m3 s12 m6 l4">
                <div id="result-modal" class="result_modal">
                    <div class="modal-header">
                        <p class="mb-0">Notification</p>
                    </div>
                    <div class="modal-content ">
                        <div class="align-center">
                            <img id="result_popup_image"
                                 src="${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Successful.svg"
                                 width="40" height="40">
                        </div>
                        <p class="align-center popup_messages" id="result_popup_text"></p>
                    </div>
                    <div class="align-center modal-footer footer">
                        <button id="closebtn_result" class="btn blue-btn closebtn" type="button"
                                onclick="closeresultModal()" name="action">Close
                        </button>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <input type="hidden" id="status_update_result" value="${updateSubmerchantStatus}">
    <input type="hidden" id="countrynameFromBackend" value="${merchant.country}">

</div>

<script>

    // document.getElementById("result_overlay").style.display = "block";
	
    function closePopupModal() {
        document.getElementById("confirm_overlay").style.display = "none";
    }

    function closeresultModal() {
        document.getElementById("result_overlay").style.display = "none";

        document.location.href = '${pageContext.request.contextPath}/submerchant/operation-child/initial';

    }


    //
    // function openConfirmPopup(){
    //     document.getElementById("confirm_overlay").style.display = "block";
    // }


    document.addEventListener("DOMContentLoaded", function (event) {
        document.querySelectorAll(".niceCountryInputSelector").forEach(element => new NiceCountryInput(element).init());

        var countrynameFromBackend = document.getElementById("countrynameFromBackend").value;
        selectCountryByName(countrynameFromBackend);

    });


    function onChangeCallback(ctr) {
        const countryName = document.getElementById('countryname');
       // console.log("country ; ", countryName.innerText);
        //console.log("The country was changed: " + ctr);
        document.getElementById("country_hidden").value = ctr;
        if (countryName)
            document.getElementById("country_name_hidden").value = countryName.innerText;

    }
/* 
    function selectCountryByName(countryName) {
        var dropdown = document.querySelector('.niceCountryInputMenuDropdownContent');
        var options = dropdown.querySelectorAll('a[data-countryname]');
      
        options.forEach(function(option) {


            if (option.getAttribute('data-countryname') === countryName) {

                option.click();
            }
        });
    }
 */
 
 function selectCountryByName(countryName) {
	    // Convert countryName to lowercase for case-insensitive comparison
	    var lowerCaseCountryName = countryName.toLowerCase();

	    // Find the country dropdown and its options
	    var dropdown = document.querySelector('.niceCountryInputMenuDropdownContent');
	    var options = dropdown.querySelectorAll('a[data-countryname]');

	    options.forEach(function(option) {
	        var optionCountryName = option.getAttribute('data-countryname');

	        // Convert optionCountryName to lowercase for case-insensitive comparison
	        var lowerCaseOptionCountryName = optionCountryName.toLowerCase();

	        if (lowerCaseOptionCountryName === lowerCaseCountryName) {
	            option.click();
	        }
	    });
	}
</script>


<script>


    document.addEventListener("DOMContentLoaded", function () {
        var tabs = document.querySelectorAll('.mdr_button-tabs');

        var fpx = document.getElementById("fpx_mdr_btn");
        var fpx_content = document.getElementById("fpx_container");
        fpx.classList.add('tab_active');
        fpx_content.style.display = "block";

        tabs.forEach(function (tab) {
            tab.addEventListener('click', function () {
                var tabId = this.getAttribute('id');
                var contentId = tabId.replace('_mdr_btn', '_container');


                tabs.forEach(function (tab) {
                    tab.classList.remove('tab_active');
                });


                this.classList.add('tab_active');

                var contents = document.querySelectorAll('.content');
                contents.forEach(function (content) {
                    content.style.display = 'none';
                });

                document.getElementById(contentId).style.display = 'block';
            });
        });
    });


    function collectAndSubmitFormData() {
        var formData = {};

        formData.fpx = {
            merchantmdr: $('#fpx_merchantmdr').val(),
            hostmdr: $('#fpx_hostmdr').val(),
            mobimdr: $('#fpx_mobimdr').val(),
            minimummdr: $('#fpx_minimummdr').val(),

        };
        formData.cards = {
            visa: {
                localdebitmdr: $('#localdebitvisamdr').val(),
                localcreditmdr: $('#localcreditvisamdr').val(),
                foriegndebitmdr: $('#foreigndebitvisamdr').val(),
                foriegncreditmdr: $('#foreigncreditvisamdr').val()
            },
            master: {
                localdebitmdr: $('#localdebitmastermdr').val(),
                localcreditmdr: $('#localcreditmastermdr').val(),
                foriegndebitmdr: $('#foreigndebitmastermdr').val(),
                foriegncreditmdr: $('#foreigncreditmastermdr').val()
            },
            union: {
                localdebitmdr: $('#localdebitunionmdr').val(),
                localcreditmdr: $('#localcreditunionmdr').val(),
                foriegndebitmdr: $('#foreigndebitunionmdr').val(),
                foriegncreditmdr: $('#foreigncreditunionmdr').val()
            }
        };

        formData.ewallet = {
            boost: {
                merchantmdr: $('#boost_merchantmdr').val(),
                hostmdr: $('#boost_hostmdr').val(),
                mobimdr: $('#boost_mobimdr').val(),
                minimummdr: $('#boost_minimummdr').val()
            },
            grab: {
                merchantmdr: $('#grab_merchantmdr').val(),
                hostmdr: $('#grab_hostmdr').val(),
                mobimdr: $('#grab_mobimdr').val(),
                minimummdr: $('#grab_minimummdr').val()
            },
            tng: {
                merchantmdr: $('#tng_merchantmdr').val(),
                hostmdr: $('#tng_hostmdr').val(),
                mobimdr: $('#tng_mobimdr').val(),
                minimummdr: $('#tng_minimummdr').val()
            },
            spp: {
                merchantmdr: $('#spp_merchantmdr').val(),
                hostmdr: $('#spp_hostmdr').val(),
                mobimdr: $('#spp_mobimdr').val(),
                minimummdr: $('#spp_minimummdr').val()
            }
        };
        formData.payout = {
            merchantmdr: $('#payout_merchantmdr').val(),
            hostmdr: $('#payout_hostmdr').val(),
            mobimdr: $('#payout_mobimdr').val(),
            minimummdr: $('#payout_minimummdr').val(),

        };

        formData.merchantDetail = {
            mid: $('#midToUpdateMDR').val(),
            merchantName: $('#subMerchantBusinessName').val(),
        }

        $('#updateMDRDetails').val(JSON.stringify(formData));


        setTimeout(function() {
            $('#mdrDetailsForm').submit();
        }, 3000);


    }


    function openConfirmPopup() {
        document.getElementById("confirm_overlay").style.display = "block";

    }

    function submitDetails() {
        document.getElementById("confirm_overlay").style.display = "none";
        document.getElementById("overlay").style.display = "block";

        collectAndSubmitFormData();


    }


    var ids = [
        'fpx_merchantmdr',
        'fpx_hostmdr',
        'fpx_mobimdr',
        'fpx_minimummdr',
        'localdebitvisamdr',
        'localcreditvisamdr',
        'foreigndebitvisamdr',
        'foreigncreditvisamdr',
        'localdebitmastermdr',
        'localcreditmastermdr',
        'foreigndebitmastermdr',
        'foreigncreditmastermdr',
        'localdebitunionmdr',
        'localcreditunionmdr',
        'foreigndebitunionmdr',
        'foreigncreditunionmdr',
        'boost_merchantmdr',
        'boost_hostmdr',
        'boost_mobimdr',
        'boost_minimummdr',
        'grab_merchantmdr',
        'grab_hostmdr',
        'grab_mobimdr',
        'grab_minimummdr',
        'tng_merchantmdr',
        'tng_hostmdr',
        'tng_mobimdr',
        'tng_minimummdr',
        'spp_merchantmdr',
        'spp_hostmdr',
        'spp_mobimdr',
        'spp_minimummdr',
        'payout_merchantmdr',
        'payout_hostmdr',
        'payout_mobimdr',
        'payout_minimummdr'
    ];


    var idsMap = {
        'fpx_merchantmdr': 'FPX Merchant MDR',
        'fpx_hostmdr': 'FPX Host MDR',
        'fpx_mobimdr': 'FPX Mobi MDR',
        'fpx_minimummdr': 'FPX Minimum MDR',
        'localdebitvisamdr': 'Local Debit Visa MDR',
        'localcreditvisamdr': 'Local Credit Visa MDR',
        'foreigndebitvisamdr': 'Foreign Debit Visa MDR',
        'foreigncreditvisamdr': 'Foreign Credit Visa MDR',
        'localdebitmastermdr': 'Local Debit Master MDR',
        'localcreditmastermdr': 'Local Credit Master MDR',
        'foreigndebitmastermdr': 'Foreign Debit Master MDR',
        'foreigncreditmastermdr': 'Foreign Credit Master MDR',
        'localdebitunionmdr': 'Local Debit Union MDR',
        'localcreditunionmdr': 'Local Credit Union MDR',
        'foreigndebitunionmdr': 'Foreign Debit Union MDR',
        'foreigncreditunionmdr': 'Foreign Credit Union MDR',
        'boost_merchantmdr': 'Boost Merchant MDR',
        'boost_hostmdr': 'Boost Host MDR',
        'boost_mobimdr': 'Boost Mobi MDR',
        'boost_minimummdr': 'Boost Minimum MDR',
        'grab_merchantmdr': 'Grab Merchant MDR',
        'grab_hostmdr': 'Grab Host MDR',
        'grab_mobimdr': 'Grab Mobi MDR',
        'grab_minimummdr': 'Grab Minimum MDR',
        'tng_merchantmdr': 'TNG Merchant MDR',
        'tng_hostmdr': 'TNG Host MDR',
        'tng_mobimdr': 'TNG Mobi MDR',
        'tng_minimummdr': 'TNG Minimum MDR',
        'spp_merchantmdr': 'SPP Merchant MDR',
        'spp_hostmdr': 'SPP Host MDR',
        'spp_mobimdr': 'SPP Mobi MDR',
        'spp_minimummdr': 'SPP Minimum MDR',
        'payout_merchantmdr': 'Payout Merchant MDR',
        'payout_hostmdr': 'Payout Host MDR',
        'payout_mobimdr': 'Payout Mobi MDR',
        'payout_minimummdr': 'Payout Minimum MDR'
    };


    $('#submit_btn').click(function () {
        let showAlert = false;
        var error_msg = document.getElementById('error_message');
        let allFieldsEmpty = true;

        outerLoop:
            for (let i = 0; i < ids.length; i++) {
                const element = document.getElementById(ids[i]);
                if (element) {
                    const input_validate = parseFloat(element.value);
                    const max_mdr_value = 10.00;
                   // console.log("compare : " + ids[i] + (input_validate > max_mdr_value));

                    if (!isNaN(input_validate)) {
                        allFieldsEmpty = false;
                        if (input_validate > max_mdr_value) {
                            showAlert = true;
                            error_msg.style.color = "red";
                            error_msg.style.display = "block";
                            error_msg.innerHTML = "Maximum MDR Value is 10.00 RM. Please Enter the valid MDR in the " + idsMap[ids[i]].toUpperCase();
                            break outerLoop;
                        }
                    }

                }
            }

        if (allFieldsEmpty) {
            showAlert = true;
            error_msg.style.color = "red";
            error_msg.style.display = "block";
            error_msg.innerHTML = "Please fill the MDR details.";

        }

        if (!showAlert) {
            openConfirmPopup();
        }

    });


    ids.forEach(id => {
        const input = document.getElementById(id);
        var error_message = document.getElementById('error_message');
        if (input) {
            input.addEventListener('input', function (event) {
                error_message.style.display = "none";
                var value = event.target.value;
                const mdrValue = value.replace(/[^0-9]/g, '');
                var formatedvalue = (parseFloat(mdrValue) / 100).toFixed(2);
                var final_mdr = formatedvalue;
                console.log(isNaN(final_mdr));
                if (isNaN(final_mdr)) {
                    event.target.value = '0.00';
                } else {
                    event.target.value = final_mdr;
                }
                console.log("Final value : ", final_mdr);


                // If input is a merchant MDR input, calculate mobi MDR
                if (id.includes('merchantmdr')) {
                    // Extract the prefix to find the corresponding host MDR input
                    const prefix = id.replace('_merchantmdr', '');
                    const hostInputId = prefix + '_hostmdr';
                    const mobiInputId = prefix + '_mobimdr';

                    // Find the host MDR input
                    const hostInput = document.getElementById(hostInputId);
                    if (hostInput) {
                        // Get the values of merchant MDR and host MDR inputs
                        const hostValue = parseFloat(hostInput.value) || 0;
                        const merchantValue = parseFloat(final_mdr) || 0;

                        // Calculate mobi MDR by adding merchant and host MDR values
                        const mobiValue = (hostValue - merchantValue).toFixed(2);


                        // Remove the leading minus sign, if present
                        const mobimerchantmdr = mobiValue.toString().replace(/^(-)?/, '');

                        // Set the calculated mobi MDR value
                        const mobiInputMerchant = document.getElementById(mobiInputId);
                        if (mobiInputMerchant) {
                            mobiInputMerchant.value = mobimerchantmdr;
                        }
                    }
                }else  if (id.includes('hostmdr')) {
                    // Extract the prefix to find the corresponding host MDR input
                    const prefix = id.replace('_hostmdr', '');
                    const merchantInputId = prefix + '_merchantmdr';
                    const mobiInputId = prefix + '_mobimdr';

                    // Find the host MDR input
                    const merchantInput = document.getElementById(merchantInputId);
                    if (merchantInput) {
                        // Get the values of merchant MDR and host MDR inputs
                        const merchantValue = parseFloat(merchantInput.value) || 0;
                        const hostValue = parseFloat(final_mdr) || 0;

                        // Calculate mobi MDR by adding merchant and host MDR values
                        const mobiValue = (merchantValue - hostValue).toFixed(2);


                        // Remove the leading minus sign, if present
                        const mobimerchantmdr = mobiValue.toString().replace(/^(-)?/, '');

                        // Set the calculated mobi MDR value
                        const mobiInputMerchant = document.getElementById(mobiInputId);
                        if (mobiInputMerchant) {
                            mobiInputMerchant.value = mobimerchantmdr;
                        }
                    }
                }

            });
        }
    });


    function updateResultPopup(result) {

        document.getElementById("result_overlay").style.display = "block";

        const approveImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Successful.svg';
        const rejectImgPath = '${pageContext.request.contextPath}/resourcesNew1/assets/submerchantregister/Declined.svg';
        const result_popup_image = document.getElementById('result_popup_image');
        const result_popup_text = document.getElementById('result_popup_text');

        if (result === 'true') {
            result_popup_image.src = approveImgPath;
            result_popup_text.innerText = 'MDR rates successfully updated.';
        } else if (result === 'false') {
            result_popup_image.src = rejectImgPath;
            result_popup_text.innerText = 'Problem Encountered. Please Try again later.';
        }
    }

    document.addEventListener('DOMContentLoaded', () => {
        const result = document.getElementById("status_update_result");
        console.log(result.value)
        if (result && result.value) {
            updateResultPopup(result.value);
        }
    });

</script>


</body>
</html>