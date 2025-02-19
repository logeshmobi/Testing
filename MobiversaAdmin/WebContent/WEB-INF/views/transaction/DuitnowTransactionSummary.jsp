<%@page
        import="com.mobiversa.payment.controller.MerchantWebUMTransactionController"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec"
          uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.ResourceBundle"%>
<%
    ResourceBundle resource = ResourceBundle.getBundle("config");
    String actionimg = resource.getString("NEWACTION");
    String voidimage = resource.getString("VOIDIMAGE");
    String refundimage = resource.getString("REFUNDIMAGE");
    String eyeimg = resource.getString("NEWEYE");
%>

<html lang="en-US">
<head>

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>

    <link href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap" rel="stylesheet">

    <meta charset="UTF-8">
    <meta
            content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
            name="viewport">

    <!-- Script tag for Datepicker -->

    <script
            src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script
            src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>

</head>



<script type="text/javascript">
    history.pushState(null, null, "");
    window.addEventListener('popstate', function() {
        history.pushState(null, null, "");

    });

    window.onload = function checkResponse() {

        var fromDate = localStorage.getItem("fromDate");
        var toDate = localStorage.getItem("toDate");

        //console.log(fromDate + ":::"+toDate);
        if(fromDate && toDate){

            document.getElementById("from").value = fromDate;
            document.getElementById("to").value = toDate;

            document.getElementById("datef").style.transform = "translateY(-14px) scale(0.8)";
            document.getElementById("datet").style.transform = "translateY(-14px) scale(0.8)";
        }

        localStorage.removeItem("fromDate");
        localStorage.removeItem("toDate");
    }
</script>
<!-- <script type="text/javascript">
	jQuery(document).ready(function() {

		$('#export').select2();
		$('#txnType').select2();
	});
</script> -->

<style>
    .input-field{
        margin-bottom: 0;
    }
    .input-field .prefix {
        position: absolute;
        width: 3rem;
        font-size: 1.5rem !important;
        transition: color .2s;
        top: 0.5rem;
    }
    .export_btn{
        background-color: transparent !important;
        border: 2px solid #005baa !important;
        color: #005baa !important;
        font-weight: 500;
        border-radius: 50px;
        line-height: 0;
        margin-left: 3px;
    }

    .export_div .select-wrapper {
        width: 65%;
        float: left;
    }

    .datepicker, .select-wrapper {
        width: 80% !important;
    }

    .select-wrapper svg {
        display: block !important;
        fill: #918f8f !important;
    }

    .addUserBtn, .addUserBtn:hover {
        background-color: #fff;
        border: 1px solid #005baa;
        border-radius: 20px;
        color: #005baa;
        font-weight: 600;
    }

    .blue-btn {
        background-color: #005baa;
        color: #fff;
        border-radius: 20px;
    }

    .button-class {
        float: right;
    }

    .float-right {
        float: right;
    }
</style>
<style>

    .btn{
        font-size: 12px !important;
        height: 40px;
        padding: 0 20px;
    }

    strong{
        font-family: "Poppins", sans-serif !important;
        font-weight: 600 !important;
    }
    .container-fluid, button, input[type=text]:not(.browser-default){
        font-family: "Poppins", sans-serif !important;
    }

    input[type=text]:not(.browser-default){
        font-size: 14px !important;
    }
    #merchantName:hover {
        color: #275ca8;
    }

    .card-content{
        padding: 24px 35px !important;
    }

    .table_heading_row{
        white-space: nowrap;
    }

    .table_heading_row th{
        padding: 12px !important;
        font-weight: 600;
    }
    .table-border-bottom td {
        border-bottom: 1px solid rgba(0, 0, 0, 0.12) !important;
        padding: 9px 12px !important;
    }

    .table-border-bottom{
        font-size: 13px;
    }

    .w24 {
        width: 24px;
    }

    .hide_key {
        display: none;
    }

    #pagination {
        display: inline-block;
        vertical-align: middle;
        border-radius: 1px;
        font-weight: 600;
        padding: 1px 2px 4px 2px;
        border-top: 1px solid transparent;
        border-bottom: 1px solid transparent;
        background-color: transparent;
        /*float: right;*/
        /*margin-right: 15px;*/
        /*margin-bottom: 10px;*/
        /* background-image: -webkit-linear-gradient(top, #DBDBDB, #E2E2E2);
          background-image:    -moz-linear-gradient(top, #DBDBDB, #E2E2E2);
          background-image:     -ms-linear-gradient(top, #DBDBDB, #E2E2E2);
          background-image:      -o-linear-gradient(top, #DBDBDB, #E2E2E2);
          background-image:         linear-gradient(top, #DBDBDB, #E2E2E2); */
        /*  position:absolute;
          left:62rem;
          bottom:1rem; */
        font-family: 'Poppins', sans-serif;
        /* width:20%;
          height:6%; */
    }

    #pagination a, #pagination i {
        display: inline-block;
        vertical-align: middle;
        width: 2rem;
        /*  color: #7D7D7D; */
        text-align: center;
        font-size: 13px;
        padding: 2.5px;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        -o-user-select: none;
        user-select: none;
    }

    #pagination a {
        /* margin: 0 2px 0 2px; */
        margin: 0 2px;
        border-radius: 50px;
        /*border: 1px solid #005baa;*/
        cursor: pointer;
        /* box-shadow: inset 0 1px 0 0 #D7D7D7, 0 1px 2px #666; */
        /* text-shadow: 0 1px 1px #FFF; */
        /*background-color: white;*/
        color: #005baa;
        /*height: 2rem;*/
        vertical-align: middle;
        /*padding-top: 3px;*/
    }

    #pagination i {
        /*  margin: 0 3px 0 3px; */

    }

    #pagination a.current {
        border: 1px solid #005baa;
        box-shadow: 0 1px 1px #999;
        background-color: #005baa;
        color: white;
    }

    #exampleModalCenter {
        z-index: 99;
        width: 25%;
        font-size: 24px;
        font-weight: 400;
        font-family: 'Poppins', sans-serif;
        text-align: center;
    }

    .test {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        z-index: 10;
        background-color: rgba(0, 0, 0, 0.5);
    }

    #close {
        color: #fff;
        background-color: #005baa;
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

    .pop-body {
        border-radius: 25px;
    }

    .mb-0 {
        padding-bottom: 0px !important;
    }

    .key_hover:hover {
        cursor: pointer;
    }

    #agentName:hover {
        color: #275ca8;
    }

    .example_e1:focus {
        outline: none !important;
    }

    .example_e1 {
        display: inline-block;
        margin-bottom: 0;
        font-weight: 600;
        text-align: left;
        vertical-align: middle;
        -ms-touch-action: manipulation;
        touch-action: manipulation;
        cursor: pointer;
        background-image: none;
        border: 0;
        color: rgb(39, 92, 168);
        letter-spacing: 1px;
        text-transform: uppercase;
        padding: 10px 15px;
        font-size: 13px;
        line-height: 1.428571429;
        border-radius: 25px;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
        transition: box-shadow 0.3s cubic-bezier(0.35, 0, 0.25, 1), transform
        0.2s cubic-bezier(0.35, 0, 0.25, 1), background-color 0.3s ease-in-out;
        border-radius: 15px;
    }

    .example_e1:hover {
        color: rgb(39, 92, 168);
        font-weight: 600 !important;
        -webkit-box-shadow: 0px 5px 40px -10px rgba(0, 0, 0, 0.57);
        -moz-box-shadow: 0px 5px 40px -10px rgba(0, 0, 0, 0.57);
        transition: all 0.3s ease 0s;
        border: 2px solid #cfcfd1;
        outline: 0 !important;
    }
</style>

<style>
    td, th {
        padding: 7px 8px;
        color: #707070;
    }

    thead th {
        border-bottom: 2px solid #ffa500;
        color: #4377a2;
    }
</style>
<style>
    .slip-modal-class, .modal-popup {
        display: none;
        position: fixed;
        z-index: 1000;
        left: 0;
        top: 0%;
        width: 100%;
        height: 100%;
        max-height: 100%;
        overflow: auto;
        scrollbar-width: none;
        background-color: rgba(0, 0, 0, 0.4);
    }

    * {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
    }

   :root {
        --success-title: #36B37E;
        --failure-title: #EC3E3E;
        --card-bg: #F6F6F6;
        --hr-line: #eeeeee;
        --title-color: #333739;
        --value-color: #2D2D2D;
        --bill-box: #ECECEC;
        --voided-color: #3c7599;
    }

    .no_border_bottom {
        border-bottom: none !important;
    }

    /* FONT STYLE CSS  */
    .poppins-light {
        font-family: "Poppins", sans-serif;
        font-weight: 300;
    }

    .poppins-regular {
        font-family: "Poppins", sans-serif;
        font-weight: 400;
    }

    .xpay_slip_wordBreak {
        word-break: break-all !important;
    }

    .xpay_slip_whiteSpace {
        white-space: nowrap !important;
        vertical-align: baseline !important;
    }

    .poppins-medium {
        font-family: "Poppins", sans-serif;
        font-weight: 500;
    }

    .poppins-semibold {
        font-family: "Poppins", sans-serif;
        font-weight: 600;
    }

    .poppins-bold {
        font-family: "Poppins", sans-serif;
        font-weight: 700;
    }

    /* Payout Slip Container main box  */
    .payout-slip-main-container {
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
    }

    .slip-card {
        width: 100%;
        max-width: 24rem;
        background-color: var(--card-bg);
        border-radius: 0.5rem;
        overflow: hidden;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    .title-logo {
        margin: 0.5rem auto 0.5rem auto;
        text-align: center;
    }

    .title-logo img {
        max-width: 100%;
        height: 1.625rem;
    }

    .horizontal-line {
        height: 0.3rem;
        background-color: var(--hr-line);
        border: none;
        margin: 0 auto 1rem auto;
    }

    .horizontal-default {
        width: 90%;
        height: 1px;
        background-color: var(--hr-line);
        color: var(--hr-line);
        margin: 0.5rem auto 0 auto;
        opacity: 0.5;
    }

    .main-status {
        text-align: center;
        color: var(--value-color);
    }

    .status {
        font-size: 1.3rem;
        margin-top: 0.25rem;
    }

    .status-success {
        color: var(--success-title);
    }
    .status-refunded {
        color: #D9AA00;
    }

    .status-failure {
        color: var(--failure-title);
    }

    .status-container {
        padding: 0 0.5rem;
    }

    .sub-head {
        font-size: 0.7rem;
        margin-top: 0.75rem;
    }

    .amount {
        font-size: 1.4rem;
        color: var(--title-color);
    }

    .amount-value {
        color: var(--value-color);
    }

    .time-stamp {
        font-size: 0.7rem;
        margin-top: 0.1rem;
    }

    .transaction-details {
        padding: 0 1.25rem;
    }

    .transaction-details table {
        width: 100%;
        border-collapse: collapse;
    }

    .transaction-details th {
        color: #333739;
    }

    .transaction-details th, .transaction-details td {
        padding: 0.85rem 0.3rem;
        text-align: left;
        font-size: 0.8rem;
    }

    .transaction-details td {
        text-align: right;
        word-wrap: break-word;
        font-weight: 600;
        color: #2D2D2D;
    }

    .bill-box-container {
        background-color: var(--bill-box);
        border-radius: 10px;
        font-size: 0.8rem;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0.7rem 1.5rem;
        margin-top: 0.5rem;
        color: var(--title-color);
    }

    .notes-section {
        padding: 0.5rem 1.5rem;
        font-size: 0.8rem;
        color: var(--title-color);
        margin-bottom: 0.5rem;
    }

    .notes {
        font-size: 0.75rem;
    }

    /* Media Queries for Responsive Design */
    @media ( max-width : 768px) {
        .slip-card {
            padding: 0;
        }
        .status {
            font-size: 1.1rem;
        }
        .amount {
            font-size: 1.2rem;
        }
    }

    @media ( max-width : 480px) {
        .slip-card {
            padding: 0;
        }
        .status {
            font-size: 1rem;
        }
        .amount {
            font-size: 1rem;
        }
    }
</style>

<!-- style for refund and void  -->

<style>
    .popup-body {
        border-radius: 20px;
    }

    #refundamount:not(.browser-default) {
        border-bottom: 1.5px solid #ffa500 !important;
    }

    #voidamount:not(.browser-default) {
        border-bottom: 1.5px solid #ffa500 !important;
    }
    #overlay-popup {
        position: fixed;
        display: none;
        width: 100%;
        height: 100%;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background: #000;
        opacity: 0.5;
        z-index: 99;
        cursor: pointer;
    }


    .datepicker-controls .select-wrapper .caret {
        display: none !important;
    }

</style>

<body class="">

<div id="overlay">
    <div id="overlay_text">
        <img class="img-fluid"
             src="${pageContext.request.contextPath}/resourcesNew1/assets/loader.gif">
    </div>
</div>



<!--  -->

<div class="test" id="pop-bg-color"></div>
<div id="overlay-popup"></div>
<div class="container-fluid mb-0" id="pop-bg">
    <div class="row">
        <div class="col s12">
            <div class="card blue-bg text-white">
                <div class="card-content">
                    <div class="d-flex align-items-center">
                        <h3 class="text-white" style="margin-bottom: 0;">
                            <strong style="margin-bottom: 0;font-size: 22px !important;">DuitNow QR Transaction Summary</strong>
                        </h3>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col s12">
            <div class="card border-radius">
                <div class="card-content">

                    <div class="row">
                        <div class="input-field col s12 m4 l3">
                            <label id = "datef" for="from" style="margin: 0px;">From </label> <input
                                type="hidden" name="date11" id="date11"
                        <c:out value=""/>> <input type="text"
                                                  id="from" name="fromDate" class="validate datepicker"
                                                  onchange="loadDate(document.getElementById('from'),document.getElementById('date11'))">
                            <i class="material-icons prefix">date_range</i>


                        </div>

                        <div class="input-field col s12 m4 l3">

                            <label id = "datet" for="to" style="margin: 0px;">To</label> <input
                                type="hidden" name="date12" id="date12"
                        <c:out value=""/>> <input id="to" type="text"
                                                  name="toDate" class="datepicker"
                                                  onchange="loadDate(document.getElementById('to'),document.getElementById('date12'))">
                            <i class="material-icons prefix">date_range</i>
                        </div>

                        <div class="input-field col s12 m4 l3">
                            <input type="hidden" name="export1" id="export1"
                            <c:out value=""/>> <select name="export"
                                                       id="export" onchange="return loadDropDate13();">
                            <option selected value="">Choose</option>
                            <option value="PDF">PDF</option>
                            <option value="EXCEL">CSV</option>
                        </select> <label class="control-label">Export Type</label>
                        </div>

                        <div class="input-field col s12 m4 l3">
                            <div class="button-class" style="float: left;">

                                <input type="hidden" name="date1" id="dateval1"> <input
                                    type="hidden" name="date2" id="dateval2">
                                <button class="btn btn-primary icon-btn" type="button"
                                        onclick="return loadSelectData();">Search</button>


                                <input type="hidden" name="dateex1" id="datevalex1"> <input
                                    type="hidden" name="dateex2" id="datevalex2">
                                <button class="btn export_btn icon-btn" type="button"
                                        onclick="return loadExpData();">Export</button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- MAIN DIV SEARCH TEST -->
    <div class="row" id="searchBoxDiv">
        <div class="col s12">
            <div class="card blue-bg text-white">
                <div class="card-content">

                    <!-- SEARCH TEST -->
                    <div class="row"
                         style="display: flex; align-items: center; justify-content: space-between; ">
                        <div class="col s12" style="padding: 0;">
                            <div class="input-field col s12 m3 l3"
                                 style="font-family: 'Poppins', sans-serif;">
                                <select name="drop_search" id="drop_search"
                                        onchange="return loadDropSearch();">
                                    <option selected value="" id="choose">Choose Type</option>
                                    <option value="INVOICE_ID">Invoice ID</option>
                                    <option value="TRANSACTION_ID">Transaction ID</option>
                                </select> <input type="hidden" id="drop_val">
                            </div>

                            <div class="input-field col s12 m3 l3"
                                 style="">
                                <input type="text" id="searchApi" name="search" class=""
                                       style="font-family: 'Poppins', sans-serif;"
                                       placeholder="Please select type to search">
                            </div>
                            <div class="input-field col s12 m3 l3"
                                 style="width: 10%; margin-left: 03%;">
                                <div class="button-class" style="float: left;">
                                    <button class="btn btn-primary blue-btn" type="button"
                                            onclick="loadSearch()"
                                            style="font-family: 'Poppins', sans-serif; width: 100%; font-size: 14px;">Search</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--  SEARCH TEST ENDS -->
                </div>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col s12">
            <div class="card border-radius">
                <div class="card-content" style="padding: 0px 20px !important;">

                    <div class="table-responsive m-b-20" id="page-table">
                        <table id="data_list_table1"
                               class=" table-border-bottom table table-striped table-bordered">
                            <thead>
                            <tr class="table_heading_row">
                                <th>Date</th>
                                <th>Time</th>
                                <th>Invoice ID</th>
                                <th>Transaction ID</th>
                                <th style="text-align: right">Amount(RM)</th>
                                <th>Status</th>
                                <th style="text-align: center;">Reason</th>
                                <th style="text-align: center;">Sales Slip</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>

                            <c:forEach items="${paginationBean.itemList}" var="dto"
                                       varStatus="id">
                                <tr>
                                    <td>${dto.createdDate}</td>
                                    <td>${dto.createdTime}</td>
                                    <td>${dto.invoiceId}</td>
                                    <td>${dto.transactionId}</td>
                                    <td style="text-align: right;">${dto.txnAmount}</td>
                                    <td>
                                        <div
                                                style="display: flex; align-items: center; justify-content: flex-start">

                                            <c:if test="${dto.status == 'SETTLED'}">
                                                <img
                                                        src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/paid.svg"
                                                        width="20" height="20" alt="">
                                                <span class="color-orange fw-500 ml-1;"
                                                      style="color: #28e528; text-transform: capitalize !important;">Settled</span>
                                            </c:if>

                                            <c:if test="${dto.status == 'NOT SETTLED'}">
                                                <img
                                                        src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg"
                                                        width="20" height="20" alt="">
                                                <span class="color-orange fw-500 ml-1;"
                                                      style="color: orange; text-transform: capitalize !important;">Not Settled</span>
                                            </c:if>
                                            <c:if test="${dto.status == 'PENDING'}">
                                                <img
                                                        src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/pending.svg"
                                                        width="20" height="20" alt="">
                                                <span class="color-orange fw-500 ml-1;"
                                                      style="color: orange; text-transform: capitalize !important;">Pending</span>
                                            </c:if>

                                            <c:if test="${dto.status == 'VOIDED'}">
                                                <img
                                                        src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Void.svg"
                                                        width="20" height="20" alt="">
                                                <span class="color-orange fw-500 ml-1;"
                                                      style="color: #3c7599; text-transform: capitalize !important;">Voided</span>
                                            </c:if>

                                            <c:if test="${dto.status == 'REFUNDED'}">
                                                <img
                                                        src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Refunded.svg"
                                                        width="20" height="20" alt="">
                                                <span class="color-orange fw-500 ml-1;"
                                                      style="color: #5d7d9a; text-transform: capitalize !important;">Refunded</span>
                                            </c:if>

                                            <c:if test="${dto.status == 'EZYSETTLED'}">
                                                <img
                                                        src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/ezysettle.svg"
                                                        width="20" height="20" alt="">
                                                <span class="color-orange fw-500 ml-1;"
                                                      style="color: #005BAA; text-transform: capitalize !important;">Ezysettled</span>
                                            </c:if>

                                            <c:if test="${dto.status == 'FAILED'}">
                                                <img
                                                        src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/Declined.svg"
                                                        width="20" height="20" alt="">
                                                <span class="color-orange fw-500 ml-1;"
                                                      style="color: red; text-transform: capitalize !important;">Failed</span>
                                            </c:if>


                                        </div>
                                    </td>
                                    <td style="text-align: center;">
                                        <c:if test="${dto.status =='FAILED'}">
                                            <button style="background-color: transparent;border: none; cursor: pointer;" class="declineReasonModalBtn" onclick="openReasonModal('${dto.declinedReason}')">
                                                <img src="${pageContext.request.contextPath}/resourcesNew1/assets/blureye.svg" width="22" height="22">
                                            </button>
                                        </c:if>
                                    </td>
                                    <!-- onclick="duitnowSlip('${dto.paidDate}','${dto.paidTime}','${dto.txnAmount}','${dto.invoiceId}','${dto.transactionId}','${dto.status}','${dto.merchantName}')" -->
                                    <td style="text-align: center;">
                                    <c:if test="${dto.status =='SETTLED'|| dto.status =='EZYSETTLED' || dto.status =='NOT SETTLED' || dto.status =='VOIDED'}">
                                            <a href="javascript:void(0)" id="openDuitnowSlip" onclick="duitnowSlip('${dto.createdDate}','${dto.createdTime}','${dto.merchantName}','${dto.txnAmount}','${dto.invoiceId}','${dto.transactionId}','${dto.status}','${dto.mdrAmount}','${dto.netAmount}','${dto.settlementDate}','${dto.subMerchantMid}','${dto.ezysettleAmount}','${dto.declinedReason}','${dto.paidDate}','${dto.paidTime}', false)" >
                                                <img class="w24"
                                                     src="${pageContext.request.contextPath}/resourcesNew1/assets/salesSlip.svg" />
                                            </a>
                                        </c:if>
                                    </td>

                                    <td class="align-center">
                                        <button class="" id="more_details_icon"
                                                onclick="openMoreInfoModal('${dto.createdDate}','${dto.createdTime}','${dto.merchantName}','${dto.txnAmount}','${dto.invoiceId}','${dto.transactionId}','${dto.status}','${dto.mdrAmount}','${dto.netAmount}','${dto.settlementDate}','${dto.subMerchantMid}','${dto.ezysettleAmount}','${dto.declinedReason}','${dto.paidDate}','${dto.paidTime}')"
                                                style="border: none; background-color: transparent; cursor: pointer;">
                                            <img
                                                    src="${pageContext.request.contextPath}/resourcesNew1/assets/mastersearch/more.svg"
                                                    width="20" height="20" alt=""
                                                    style="margin-top: 4.5px;">
                                        </button>


                                    </td>

                                </tr>

                            </c:forEach>

                            <c:if test="${paginationBean.itemList.size() eq 0}">
                                <tr>
                                    <td colspan="13" style="text-align: center;">
                                        <div id="no-data">
                                            <p style="margin-bottom: 0">No Data Available</p>
                                        </div>
                                    </td>
                                </tr>
                            </c:if>

                            </tbody>
                        </table>
                    </div>
                </div>

            </div>
        </div>
    </div>

 <c:if test="${paginationBean.itemList.size() ne 0}">
    <div class="row" id="pagination_row">
        <div class="col s12">
            <div class="card border-radius">
                <div class="card-content" style="padding: 5px !important; display: flex;justify-content: flex-end;">
                    <div id="pagination"></div>
                </div>
            </div>
        </div>
    </div>
  </c:if>





    <%--    SLIP CARD --%>



    <!--  NEW SLIP SPP STARTS -->

    <div id="xPay_slip-modal-id" class="slip-modal-class">
        <section class="payout-slip-main-container poppins-regular"
                 id="spp-slip-main-container-id">
            <div class="slip-card" id="slip-card-id">
                <div class="title-logo" style="position: relative">

                    <img
                            src="${pageContext.request.contextPath}/resourcesNew1/assets/mobi_logo.svg"
                            width="55" height="55" /> <img
                        style="position: absolute; top: 3px; right: 10px; height: 20px ! important; width: 20px !important; cursor: pointer;"
                        src="${pageContext.request.contextPath}/resourcesNew1/assets/xmark.svg"
                        width="35" height="35" onclick="closeXpayModal()" />
                </div>

                <hr class="horizontal-line">
                <!-- Second Part - Status and Time stamp -->
                <div class="main-status poppins-semibold">
                    <!-- comment this below for Failure status -->
                    <p id="spp_slip_Status" class="status status-success">${dto.STATUS}</p>

                    <!-- Uncomment this for Failure status -->
                    <!-- <p class="status status-failure">Failed</p> -->
                    <div class="status-container">
                        <p class="sub-head">Transaction Summary</p>
                        <p class="amount poppins-regular">
                            MYR <span class="poppins-semibold amount-value"
                                      id="spp_slip_amount"></span>
                        </p>
                        <p class="time-stamp poppins-semibold" id="spp_slip_date"></p>
                        <hr class="horizontal-default">
                    </div>
                </div>
                <!-- Third Part - Transaction details area  -->
                <div class="transaction-details">
                    <table>
                        <tr class="no_border_bottom">
                            <th class="poppins-regular xpay_slip_whiteSpace">Paid To</th>
                            <td class="poppins-medium xpay_slip_wordBreak"
                                style="text-transform: uppercase;" id="spp_slip_merchantname"></td>
                        </tr>
                        <tr class="no_border_bottom">
                            <th class="poppins-regular xpay_slip_whiteSpace">Invoice ID
                            </th>
                            <td class="poppins-medium xpay_slip_wordBreak"
                                id="spp_slip_reference"></td>
                        </tr>
                        <tr class="no_border_bottom">
                            <th class="poppins-regular xpay_slip_whiteSpace">Transaction ID
                            </th>
                            <td class="poppins-medium xpay_slip_wordBreak"
                                id="spp_slip_transactionId"></td>
                        </tr>

                        <tr class="no_border_bottom">
                            <th class="poppins-regular xpay_slip_whiteSpace">Payment Method</th>
                            <td class="poppins-medium xpay_slip_wordBreak">DuitNow QR</td>
                        </tr>

                    </table>
                    <div class="bill-box-container">
                        <div class="poppins-medium">Transfer Amount</div>
                        <div class="poppins-semibold"
                             style="font-size: 1rem; color: var(--value-color);"
                             id="spp_slip_amount_td"></div>
                    </div>
                </div>
                <hr class="horizontal-default">
                <div class="notes-section">
                    <strong>Note</strong>
                    <p class="notes">
                        This receipt is computer generated and no signature is required.
                        For support, contact  <a href="mailto:csmobi@gomobi.io" style="color: #005BAA; text-decoration: underline;">csmobi@gomobi.io</a>
                    </p>
                </div>
            </div>
        </section>
        <input type="hidden" name="merchantId" id="merchantId" value="${merchantId}">
    </div>

    <%--    more details modal --%>

    <div id="moreDetailsModal" class="modal-popup">
        <div class="moreinfo_modal_content">
            <div class="header-popup">
                <h2>More Information
                    <span style="float: right;margin-right: 10px;">
                        <button onclick="closeMoreInfoModal()" style="background-color: transparent;border: none;box-shadow: none;cursor: pointer;">
                            <img class="w24 " width="20" height="20" src="${pageContext.request.contextPath}/resourcesNew1/assets/xmark.svg" />
                        </button>
                    </span>
                </h2>
            </div>
            <div class=" more_info_content" >
                <div class="">
                    <h2 style=" text-align: left;color: #005baa; font-size: 17px !important;font-weight: 500;">Transaction Details</h2>
                </div>
                <table>
                    <tbody>
                    <tr>
                        <td class="left_info">Date</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="date_info"></td>
                    </tr>
                    <tr>
                        <td class="left_info">Time</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="time_info"></td>
                    </tr>
                    <tr>
                        <td class="left_info">Business Name</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="merchantName_info"></td>
                    </tr>
                    <tr>
                        <td class="left_info">Amount (RM)</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="txnAmount_info"></td>
                    </tr>
                    <tr>
                        <td class="left_info">Status</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="status_info"></td>
                    </tr>
                    <tr>
                        <td class="left_info">Invoice ID</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="invoiceId_info"></td>
                    </tr>
                    <tr>
                        <td class="left_info">Transaction ID</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="transactionId_info"></td>
                    </tr>
                    <tr>
                        <td class="left_info">MDR Amount (RM)</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="mdrAmount_info"></td>
                    </tr>
                    <tr>
                        <td class="left_info">Net Amount (RM)</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="netAmount_info"></td>
                    </tr>
                    <tr>
                        <td class="left_info">Payment Date</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="settledDate_info"></td>
                    </tr>
                    <tr style="display:none;">
                        <td class="left_info">Ezysettle Amount</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="ezysettleAmount_info"></td>
                    </tr>
                    <tr>
                        <td class="left_info">Submerchant MID</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="subMerchantMid_info"></td>
                    </tr>
                    <tr>
                        <td class="left_info">Sales Receipt</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="paymentReceipt_info">

                            <a href="javascript:void(0)" id="paymentReceiptLink"  style="display: flex;align-items: center;justify-content: flex-start;" >
                                <img class="w24" src="${pageContext.request.contextPath}/resourcesNew1/assets/salesSlip.svg" alt="Payment Receipt" />
                                <span style="margin-left: 5px; text-align: center;">View Receipt</span>
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td class="left_info">Reason</td>
                        <td class="hyphen">:</td>
                        <td class="right_info" id="declinedReason_info"></td>
                    </tr>
                    </tbody>
                </table>

            </div>
            <div class="modalFooter">
                <button class="btn closeBtn" id="closeMoreInfoModalBtn" onclick="closeMoreInfoModal()">Close</button>
            </div>
        </div>
    </div>




    <%--        decline reason modal --%>

    <div id="reasonModal" class="modal-popup">
        <div class="modal-content-popup">
            <div class="header-popup">
                <h2>Reason</h2>
            </div>
            <div class="reasonContent" >
                <img src="${pageContext.request.contextPath}/resourcesNew1/assets/Declined.svg" width="50" height="50">
                <p class="declinedReasonText" id="declinedReasonText"></p>
            </div>
            <div class="modalFooter">
                <button class="btn closeBtn" id="closeModalBtn" onclick="closeReasonModal()">Close</button>
            </div>
        </div>
    </div>


    <%--    result modal --%>

    <div id="resultModal" class="modal-popup">
        <div class="modal-content-popup">
            <div class="header-popup">
                <h2>Notification</h2>
            </div>
            <div class="reasonContent" >
                <img src="${pageContext.request.contextPath}/resourcesNew1/assets/clarity_alert-solid.svg"
                     width="50" height="50">
                <p class="result_text" id="result_text"></p>
            </div>
            <div class="modalFooter">
                <button class="btn closeBtn" id="closeResultBtn" onclick="closeResultModal()">Close</button>
            </div>
        </div>
    </div>



    <style>

        .moreinfo_modal_content {
            position: relative;
            margin: 5% auto;
            padding: 0px !important;
            width: 80%;
            max-width: 600px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
            text-align: center;
        }

        /* Ensure the table stays horizontal and adapts well to different screen sizes */
        .more_info_content {
            width: 100%;
            padding: 24px;
        }

        .more_info_content table {
            width: 100%;
            table-layout: auto;
            border-collapse: collapse;
        }

        .more_info_content tr {
            display: flex;
            justify-content: space-between;
            padding: 5px 0;
            border: none;
        }

        .more_info_content td {
            padding: 5px;
        }

        .left_info, .hyphen, .right_info {
            padding: 8px 10px;
            white-space: nowrap; /* Prevent text from wrapping */
        }

        .left_info {
            width: 40%; /* Define left column width */
        }

        .hyphen {
            width: 5%; /* Narrower space for the colon */
            text-align: center;
        }

        .right_info {
            width: 55%; /* Define right column width */
            text-align: left;
            word-wrap: break-word; /* Allow word-breaking if needed */
        }

        @media screen and (max-width: 600px) {
            .left_info {
                width: 45%; /* Adjust widths for smaller devices */
            }
            .hyphen{
                width: 5%;
            }

            .right_info {
                width: 50%;
            }

            .more_info_content tr {
                padding: 4px 0; /* Reduce padding for smaller screens */
            }
        }


        .fw-500{
            font-weight: 500;
        }

        .header-popup{
            border-bottom: 2px solid #e0a800;
            padding: 12px 0;
        }

        .header-popup h2{
            color: #005baa;
            font-size: 16px !important;
            font-weight: 500;
            text-align: center;
            margin-bottom: 0;
        }

        .reasonContent{
            padding: 12px;
        }

        .declinedReasonText, .result_text{
            color: #546570;
            font-size: 16px;
        }
        .modalFooter{
            background-color: #edf8ff;
            padding: 10px 0;
            border-bottom-left-radius: 10px;
            border-bottom-right-radius: 10px;
        }

        .closeBtn{
            background-color: #005baa !important;
            border: 2px solid #005baa !important;
            color: #fff !important;
            font-weight: 500;
            border-radius: 50px;
            line-height: 0;
            height: 33px;
            padding: 0 20px;
        }

        .cancelBtn{
            background-color: #fff !important;
            border: 2px solid #005baa !important;
            color: #005baa !important;
            font-weight: 500;
            border-radius: 50px;
            line-height: 0;
            height: 33px;
            padding: 0 20px;
        }

        .modal-content-popup {
            position: relative;
            margin: 15% auto;
            padding: 0px !important;
            width: 80%;
            max-width: 500px;
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.3);
            text-align: center;
        }

        .btn:hover, .btn:focus {
            background-color: #005baa;
            color: #fff;
            box-shadow: 5px 5px 10px -3px rgba(0, 90, 170, 0.2), /* Right shadow */
            -5px 5px 10px -3px rgba(0, 90, 170, 0.2),
                /* Left shadow */
            0 5px 10px -3px rgba(0, 90, 170, 0.2) !important;
            /* Bottom shadow */
        }


    </style>



    <script>



        function openReasonModal(reason) {
            const modal = document.getElementById('reasonModal');
            let reasonText = document.getElementById("declinedReasonText");
            reasonText.innerText = reason;
            modal.style.display = "block";
        }

        function openResultModal() {
            const modal = document.getElementById('resultModal');
            var resulttext = document.getElementById("result_text");
            resulttext.innerText = "Successfully voided..";
            modal.style.display = "block";
        }

        function closeResultModal() {
            const modal = document.getElementById('resultModal');
            modal.style.display = "none";
        }

        function closeReasonModal(){
            const reasonmodal = document.getElementById('reasonModal');
            const closeBtn = document.getElementById('closeModalBtn');
            reasonmodal.style.display = "none";
        }
        
        function setInfo(id, value) {
            const element = document.getElementById(id);
            const parentRow = element.parentNode; 
            if (value != null && value.trim() != "") {
                element.innerHTML = value;
                parentRow.style.display = ''; 
            } else {
               // parentRow.style.display = 'none';
                parentRow.style.display = ''; 
            	element.innerHTML = "N/A";
            }
        }
        

        function openMoreInfoModal(date, time, merchantName, txnAmount, invoiceId, transactionId, status, mdrAmount, netAmount, settledDate, subMerchantMid, ezysettleAmount,declinedReason,paidDate,paidTime) {
            const modal = document.getElementById('moreDetailsModal');

           /*  function setInfo(id, value) {
                document.getElementById(id).innerHTML = value ? value : "";
            } */
            

            setInfo('date_info', date);
            setInfo('time_info', time);
            setInfo('merchantName_info', merchantName);
            setInfo('txnAmount_info', txnAmount);
            setInfo('status_info', renderStatus(status));
            setInfo('invoiceId_info', invoiceId);
            setInfo('transactionId_info', transactionId);
            setInfo('mdrAmount_info', mdrAmount);
            setInfo('netAmount_info', netAmount);
            setInfo('subMerchantMid_info', subMerchantMid);
            //setInfo('ezysettleAmount_info', ezysettleAmount);


            // Conditionally display the declined reason row
            const declinedReasonRow = document.getElementById('declinedReason_info').parentNode; // Get the entire row
            const paymentReceiptInfo_row = document.getElementById('paymentReceipt_info').parentNode;
            const settledDate_info_row = document.getElementById('settledDate_info').parentNode;

            if (status === 'SETTLED' || status === 'NOT SETTLED' || status === 'VOIDED' || status === 'EZYSETTLED') {
                setInfo('settledDate_info', settledDate);
             //  settledDate_info_row.style.display = '';
            } else {
              //  settledDate_info_row.style.display = 'none';
                settledDate_info.innerText = 'N/A';

            }

            if (status === 'FAILED') {
               setInfo('declinedReason_info', declinedReason);
              //  declinedReasonRow.style.display = '';
            } else {
                //declinedReasonRow.style.display = 'none';
                declinedReason_info.innerText = 'N/A';

            }


            if (status === 'SETTLED' || status === 'NOT SETTLED' || status == 'VOIDED' || status == 'EZYSETTLED') {
                paymentReceiptInfo_row.style.display = '';
                const paymentReceiptLink = document.getElementById('paymentReceiptLink');
                paymentReceiptLink.onclick = function() {
                    //console.log('Link clicked!');
                    duitnowSlip(date, time, merchantName, txnAmount, invoiceId, transactionId, status, mdrAmount, netAmount, settledDate, subMerchantMid, ezysettleAmount,declinedReason,paidDate, paidTime,true);
                   // duitnowSlip(paidDate, paidTime, txnAmount, invoiceId, transactionId, status, merchantName);
                };
            } else {
                paymentReceiptInfo_row.style.display = 'none';
            }


            // Display the modal
            modal.style.display = "block";

            window.onclick = function (event) {
                if (event.target === modal) {
                    closeMoreInfoModal();
                }
            };
        }


        const contextPath = "${pageContext.request.contextPath}";
        //console.log("Context path:", contextPath);

        function renderStatus(status) {
            let icon, color, text;

            switch (status) {
                case 'SETTLED':
                    icon = contextPath + '/resourcesNew1/assets/mastersearch/paid.svg';
                    color = '#28e528';
                    text = 'Settled';
                    break;
                case 'NOT SETTLED':
                    icon = contextPath + '/resourcesNew1/assets/mastersearch/pending.svg';
                    color = 'orange';
                    text = 'Not Settled';
                    break;
                case 'PENDING':
                    icon = contextPath + '/resourcesNew1/assets/mastersearch/pending.svg';
                    color = 'orange';
                    text = 'Pending';
                    break;
                case 'VOIDED':
                    icon = contextPath + '/resourcesNew1/assets/mastersearch/Void.svg';
                    color = '#3c7599';
                    text = 'Voided';
                    break;
                case 'REFUNDED':
                    icon = contextPath + '/resourcesNew1/assets/mastersearch/Refunded.svg';
                    color = '#5d7d9a';
                    text = 'Refunded';
                    break;
                case 'EZYSETTLED':
                    icon = contextPath + '/resourcesNew1/assets/mastersearch/ezysettle.svg';
                    color = '#005BAA';
                    text = 'Ezysettled';
                    break;
                case 'FAILED':
                    icon = contextPath + '/resourcesNew1/assets/mastersearch/Declined.svg';
                    color = 'red';
                    text = 'Failed';
                    break;
                default:
                    // Default case for unknown or missing status
                    return `<span style="color: grey;">Status Not Available</span>`;
            }

            //console.log("Status:", status); // Debugging: Check if status is received correctly

            // Return the constructed HTML with icon and status
            return '<div style="display: flex; align-items: center; justify-content: flex-start;">' +
                '<img src="' + icon + '" width="20" height="20" alt="' + text + '" />' +
                '<span style="color: ' + color + '; text-transform: capitalize; margin-left: 8px;">' + text + '</span>' +
                '</div>';
        }




        function closeMoreInfoModal(){
            const reasonmodal = document.getElementById('moreDetailsModal');
            const closeBtn = document.getElementById('closeMoreInfoModalBtn');
            reasonmodal.style.display = "none";
        }




    </script>
    <script>

    let moreDetailsData = {};
    
        function formatTopDate(dateString) {
            const date = new Date(dateString);
            const options = { day: 'numeric', month: 'long', year: 'numeric' };
            return date.toLocaleDateString('en-GB', options);
        }


       // function duitnowSlip(date, time,txnamount,invoiceId,transactionId,status,merchantName)
      	 function duitnowSlip(date, time, merchantName, txnamount, invoiceId, transactionId, status, mdrAmount, netAmount, settledDate, subMerchantMid, ezysettleAmount,declinedReason,paidDate, paidTime,viaMoredetails)
        {
            //console.log("Received in duitnowSlip:");
            //console.log("Date:", date);
            //console.log("Time:", time);
            //console.log("Transaction Amount:", txnamount);
            //console.log("Invoice ID:", invoiceId);
            //console.log("Transaction ID:", transactionId);
            //console.log("Status:", status);
            //console.log("Merchant Name:", merchantName);

            var parts = paidDate.split('/');
            var formattedDate = parts[2] + '-' + parts[1] + '-' + parts[0];
            var dateTimeString = formatTopDate(formattedDate) + " " + paidTime;

            var parsedDate2 = new Date(formattedDate);
            var monthNames = ["January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
            ];
            var formattedDate2 = parsedDate2.getDate() + ' ' + monthNames[parsedDate2.getMonth()] + ' ' + parsedDate2.getFullYear();

            var modal = document.getElementById("xPay_slip-modal-id");
            var moreinfo_modal = document.getElementById("moreDetailsModal");
            modal.style.display = "block";
            moreinfo_modal.style.display = "none";
            var modal1 = document.getElementById("spp-slip-main-container-id");

            var amountWithCurrency = "MYR " + txnamount;

            document.getElementById("spp_slip_amount").innerText = txnamount;
            document.getElementById("spp_slip_amount_td").innerText = amountWithCurrency;
            document.getElementById("spp_slip_date").innerText = dateTimeString;
            // document.getElementById("spp_slip_mid").innerText = mid;
            document.getElementById("spp_slip_transactionId").innerText =  transactionId;
            document.getElementById("spp_slip_reference").innerText = invoiceId;
            document.getElementById("spp_slip_merchantname").innerText = merchantName;

            
            var statusElement = document.getElementById("spp_slip_Status");
            if (status === "SETTLED") {
                statusElement.innerText = "Successful";
                statusElement.style.color = "var(--success-title)";
            }else if (status === "VOIDED") {
                statusElement.innerText = "Voided";
                statusElement.style.color = "var(--voided-color)";
            }
            else {
                statusElement.innerText = "Successful";
                statusElement.style.color = "var(--success-title)";
            }


            window.onclick = function (event) {
                if (event.target === modal || event.target === modal1) {
                    closeXpayModal();
                    // console.log("closing xpay modal")
                    if(viaMoredetails){
                    	openMoreInfoModal(date, time, merchantName, txnamount, invoiceId, transactionId, status, mdrAmount, netAmount, settledDate, subMerchantMid, ezysettleAmount,declinedReason,paidDate, paidTime);
                    }
                }
                
            };
            
            if (viaMoredetails) {
                moreDetailsData = {
                    date,
                    time,
                    merchantName,
                    txnamount,
                    invoiceId,
                    transactionId,
                    status,
                    mdrAmount,
                    netAmount,
                    settledDate,
                    subMerchantMid,
                    ezysettleAmount,
                    declinedReason,
                    paidDate,
                    paidTime
                };
            } else {
                moreDetailsData = {}; 
            }

        };

        function closeXpayModal() {
            body.style.overflow = initialOverflow;
            var modal = document.getElementById("xPay_slip-modal-id");
            modal.style.display = "none";
            
            if (Object.keys(moreDetailsData).length > 0) {
                openMoreInfoModal(
                    moreDetailsData.date,
                    moreDetailsData.time,
                    moreDetailsData.merchantName,
                    moreDetailsData.txnamount,
                    moreDetailsData.invoiceId,
                    moreDetailsData.transactionId,
                    moreDetailsData.status,
                    moreDetailsData.mdrAmount,
                    moreDetailsData.netAmount,
                    moreDetailsData.settledDate,
                    moreDetailsData.subMerchantMid,
                    moreDetailsData.ezysettleAmount,
                    moreDetailsData.declinedReason,
                    moreDetailsData.paidDate,
                    moreDetailsData.paidTime
                );

                moreDetailsData = {};
            }

        }




        jQuery(function() {
            var date = new Date();
            var currentMonth = date.getMonth();
            var currentDate = date.getDate();
            var currentYear = date.getFullYear();

            $('.datepicker').datepicker({
                minDate: new Date(currentYear, currentMonth-2, currentDate),
                maxDate: new Date(currentYear, currentMonth, currentDate)
            });
        });

        $('.pickadate-clear-buttons').pickadate({
            close: 'Close Picker',
            formatSubmit: 'dd/mm/yyyy',
        });






        function show_key(id){

            document.getElementById('show_key_'+id).style.display ="block";
            document.getElementById('hide_key_'+id).style.display ="none";

        }

        function hide_key(id){

            document.getElementById('show_key_'+id).style.display ="none";
            document.getElementById('hide_key_'+id).style.display ="block";

        }

        function show_rrn(id){

            document.getElementById('show_rrn_'+id).style.display ="block";
            document.getElementById('hide_rrn_'+id).style.display ="none";

        }

        function hide_rrn(id){

            document.getElementById('show_rrn_'+id).style.display ="none";
            document.getElementById('hide_rrn_'+id).style.display ="block";

        }



        function loadDropSearch(){
            var e = document.getElementById("drop_search");
            var strUser = e.options[e.selectedIndex].value;
            document.getElementById("drop_val").value = strUser;

            if (strUser == "INVOICE_ID") {
                document.getElementsByName('search')[0].placeholder = 'Ex: Enter Invoice ID';
            } else if (strUser == "TRANSACTION_ID") {
                document.getElementsByName('search')[0].placeholder = 'Ex: Enter Transaction ID';
            }
            else if(strUser == ""){
                document.getElementsByName('search')[0].placeholder = 'Please select type to search ';
            }
        }

        function loadSearch()
        {
            var Value = document.getElementById("searchApi").value;
            var type = document.getElementById("drop_val").value;
            var merchantId = document.getElementById("merchantId").value;

            var e = document.getElementById("from").value;
            var e1 = document.getElementById("to").value;
            
            var validSearchIdRegex = /^[a-zA-Z0-9_]*$/;

            if(type.trim() === ''){
                alert("Please choose a type to search");
                $("#overlay").hide();
                return;
            }else if(Value.trim() === ''){
                alert("Please provide Id to search");
                $("#overlay").hide();
                return;
            }else if (!validSearchIdRegex.test(Value)) {
                alert("Search ID contains invalid characters. Only letters, numbers, and underscores ( _ ) are allowed.");
                $("#overlay").hide();
                return;
            }
            $("#overlay").show();
            document.location.href = '${pageContext.request.contextPath}/duitnow/searchData?searchId='
                + Value  +'&searchType=' + type;

            localStorage.setItem("fromDate", e);
            localStorage.setItem("toDate",e1);

            form.submit;

        }

        function loadDropDate13() {
            var e = document.getElementById("export");

            var strUser = e.options[e.selectedIndex].value;
            document.getElementById("export1").value = strUser;

        }

        function loadSelectData() {

            var e = document.getElementById("from").value;
            var e1 = document.getElementById("to").value;
            var PageNumber = document.getElementById("pgnum").value;

            //console.log("  selected date "+e+"::::"+e1);

            var fromDate = new Date(e);
            var toDate = new Date(e1);

            var fromday = fromDate.getDate();
            var frommon = fromDate.getMonth() + 1;
            var fromyear = fromDate.getFullYear();

            var today = toDate.getDate();
            var tomon = toDate.getMonth() + 1;
            var toyear = toDate.getFullYear();

            var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
            var todateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;

            if (e == null ||e == '' || e1 == null || e1 == '') {
                alert("Please Select date(s)");
                $("#overlay").hide();
            } else {

                document.getElementById("dateval1").value = fromdateString;
                document.getElementById("dateval2").value = todateString;

                //console.log("  selected date formatted  "+fromdateString+"::::"+todateString);

                $("#overlay").show();
                document.location.href = '${pageContext.request.contextPath}/duitnow/getAdminDuitnow?fromDate='
                    + fromdateString + '&toDate=' + todateString + '&currPage=' + PageNumber ;

                localStorage.setItem("fromDate", e);
                localStorage.setItem("toDate",e1);
                form.submit;

            }
        }

        function loadExpData() {
            //alert("test"+document.getElementById("txnType").value);
            var e = document.getElementById("from").value;
            var e1 = document.getElementById("to").value;
            var e2 = document.getElementById("export1").value;
            //var txnType = document.getElementById("txnType").value;

            var fromDate = new Date(e);
            var toDate = new Date(e1);

            var fromday = fromDate.getDate();
            var frommon = fromDate.getMonth() + 1;
            var fromyear = fromDate.getFullYear();

            var today = toDate.getDate();
            var tomon = toDate.getMonth() + 1;
            var toyear = toDate.getFullYear();

            var fromdateString = (fromday <= 9 ? '0' + fromday : fromday) + '/' + (frommon <= 9 ? '0' + frommon : frommon) + '/' + fromyear;
            var todateString = (today <= 9 ? '0' + today : today) + '/' + (tomon <= 9 ? '0' + tomon : tomon) + '/' + toyear;


            if (e == null ||e == '' || e1 == null || e1 == '') {
                alert("Please Select date(s)");
                return
            } else if(e2 == "") {
                alert("Please Select export type.");
                return
            }else {
                /* alert("inside else"); */
                //  $("#overlay").show();
                document.getElementById("datevalex1").value = fromdateString;
                document.getElementById("datevalex2").value = todateString;

                var merchantId = document.getElementById('merchantId').value;

                document.location.href = '${pageContext.request.contextPath}/duitnow/exportAllDuitnowTransactions?fromDate=' +fromdateString
                    + '&toDate=' + todateString + '&export='+e2;

                localStorage.setItem("fromDate", e);
                localStorage.setItem("toDate",e1);
                form.submit;

            }
        }


        function loadDate(inputtxt, outputtxt) {
            var field = inputtxt.value;
            outputtxt.value = field;
        }

        function loadSelectData2() {
            var fromdateString = document.getElementById("FromDate").value;
            var todateString = document.getElementById("From1Date").value;
          //  var PageNumber = document.getElementById("pgnum").value;

        	 var PageNumber = parseInt(document.getElementById("pgnum").value);
       		 var currPageNo = parseInt(${paginationBean.currPage});
          
            var e = document.getElementById("from").value;
            var e1 = document.getElementById("to").value;
            if(PageNumber === currPageNo){
            	return 
            }else{
            if (fromdateString == null ||fromdateString == '' || todateString == null || todateString == '') {
                document.getElementById("dateval1").value = fromdateString;
                document.getElementById("dateval2").value = todateString;
                $("#overlay").show();
                document.location.href = '${pageContext.request.contextPath}/duitnow/getAdminDuitnow?fromDate='
                    + fromdateString + '&toDate=' + todateString + '&currPage=' + PageNumber ;


                localStorage.setItem("fromDate", e);
                localStorage.setItem("toDate",e1);
                form.submit;
            } else {
                document.getElementById("dateval1").value = fromdateString;
                document.getElementById("dateval2").value = todateString;
                $("#overlay").show();
                //console.log("  selected date pagination formatted  "+fromdateString+"::::"+todateString);

                document.location.href = '${pageContext.request.contextPath}/duitnow/getAdminDuitnow?fromDate='
                    + fromdateString + '&toDate=' + todateString + '&currPage=' + PageNumber ;


                localStorage.setItem("fromDate", e);
                localStorage.setItem("toDate",e1);
                form.submit;
            }
           }
        }



    </script>


    <input type="hidden" id="pgnum" >
    <input type="hidden" id="FromDate" >
    <input type="hidden" id="From1Date" >

    <script>
        var fromDateServer = document.getElementById("FromDate").value="${paginationBean.dateFromBackend}";
        var from1DateServer = document.getElementById("From1Date").value="${paginationBean.date1FromBackend}";
        /* * * * * * * * * * * * * * * * *
         * Pagination
         * javascript page navigation
         * * * * * * * * * * * * * * * * */

        var PageNumber = document.getElementById("pgnum").value;
        var pageSize = 20;

        //Assuming you have a function to add a class to elements
        function addClass(element, className) {
            if (element.classList) {
                element.classList.add(className);
            } else {
                element.className += ' ' + className;
            }
        }


        function dynamic(pgNo){
            /* alert("Page Number:"+pgNo); */
            document.getElementById("pgnum").value=pgNo;
            loadSelectData2();

        }

        function previous(pgNo){
            /* alert("Page Number:"+pgNo); */
            pgNo--;
            document.getElementById("pgnum").value=pgNo;
            loadSelectData2();

        }

        function next(pgNo){
            /* alert("Page Number:"+pgNo); */
            pgNo++;
            document.getElementById("pgnum").value=pgNo;
            loadSelectData2();
        }


        var Pagination = {

            code: '',

            // --------------------
            // Utility
            // --------------------

            // converting initialize data
            Extend: function(data) {
                data = data || {};
                // Pagination.size = data.size || 300;
                ////console.log(Pagination.size);
                // Pagination.size = Math.ceil(${paginationBean.fullCount}/10) ||100;

                // old

                /* Pagination.size = ((${paginationBean.currPage})+4) ||100; */
                Pagination.size = Math.ceil(${paginationBean.querySize} / pageSize);

                /* Pagination.page = data.page || 1; */
                Pagination.page = ${paginationBean.currPage} || 1;
                Pagination.step = ((data.step)-4) || 3;
            },

            // add pages by number (from [s] to [f])
            Add: function(s, f) {
                for (var i = s; i < f; i++) {
                    Pagination.code += '<a onclick="dynamic('+i+')">' + i + '</a>';
                }
            },

            // add last page with separator
            /*   Last: function() {
                  Pagination.code += '<i>...</i>';
              },
               */

            /*    // old
            Last: function() {
                Pagination.code += '<a onclick="dynamic(((Pagination.page)+1))">'+ ((Pagination.page)+1)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+2))">'+ ((Pagination.page)+2)+ '</a>'+'<a onclick="dynamic(((Pagination.page)+3))">'+ ((Pagination.page)+3)+ '</a>'+'<i>...</i>';
            },
     */
            // old
            // add first page with separator
            /* 	First: function() {
                    if(Pagination.page==1){

                        Pagination.code += '<i>...</i>'+'<a onclick="dynamic(Pagination.page)" id="page1">'+Pagination.page+'</a>';

                    }
                    else{
                        Pagination.code += '<a>1</a>'+'<i>...</i>'+'<a  onclick="dynamic(((Pagination.page)-1))">'+((Pagination.page)-1)+'</a>'+'<a id="page2" onclick="dynamic(Pagination.page)">'+Pagination.page+'</a>';
                    }
                }, */


            // newer chnagess

            First: function() {
                Pagination.code += '<a onclick="dynamic(1)">1</a>';
                if (Pagination.page > 3) {
                    Pagination.code += '<i>...</i>';
                }
                for (var i = Math.max(2, Pagination.page - 1); i <= Pagination.page; i++) {
                    //directly goes to 1 instead of re-arranging
                    Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
                }
            },

            // arranges ...
            Last: function() {

                // total page
                var lastPage = Math.ceil(${paginationBean.querySize} / pageSize);
                // three pg no after 1st pg no
                if (lastPage > Pagination.page + 3) {
                    // generate <a> tag for 3 pg no
                    for (var i = Pagination.page + 1; i <= Pagination.page + 3; i++) {
                        Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
                    }
                    Pagination.code += '<i>...</i>';
//		         Pagination.code += '<a onclick="dynamic(' + lastPage + ')">' + lastPage + '</a>';
                } else {
                    // if less than 3 page generate <a> tag
                    for (var i = Pagination.page + 1; i <= lastPage; i++) {
                        Pagination.code += '<a onclick="dynamic(' + i + ')">' + i + '</a>';
                    }
                }
            },




            // --------------------
            // Handlers
            // --------------------

            // change page
            Click: function() {
                Pagination.page = +this.innerHTML;
                Pagination.Start();
                dynamic(page);
            },

            // previous page
            Prev: function() {

                Pagination.page--;
                if (Pagination.page < 1) {
                    Pagination.page = 1;
                }
                Pagination.Start();
                dynamic(page);
            },



            // next page


            Next: function() {
                Pagination.page++;
                if (Pagination.page > Pagination.size) {
                    Pagination.page = Pagination.size;
                }
                Pagination.Start();
                dynamic(page);
            },



            // --------------------
            // Script
            // --------------------

            // binding pages
            Bind: function() {
                var a = Pagination.e.getElementsByTagName('a');
                for (var i = 0; i < a.length; i++) {
                    if (+a[i].innerHTML === Pagination.page) a[i].className = 'current';
                    a[i].addEventListener('click', Pagination.Click, false);
                }
            },

            // write pagination
            Finish: function() {
                Pagination.e.innerHTML = Pagination.code;
                Pagination.code = '';
                Pagination.Bind();
            },

            // find pagination type
            Start: function() {
                if (Pagination.size < Pagination.step * 2 + 6) {
                    Pagination.Add(1, Pagination.size + 1);
                }
                else if (Pagination.page < Pagination.step * 2 + 1) {
                    Pagination.Add(1, Pagination.step * 2 + 4);
                    Pagination.Last();
                }
                else if (Pagination.page > Pagination.size - Pagination.step * 2) {
                    Pagination.First();
                    Pagination.Add(Pagination.size - Pagination.step * 2 - 2, Pagination.size + 1);
                }
                else {
                    Pagination.First();
                    Pagination.Add(Pagination.page - Pagination.step, Pagination.page + Pagination.step + 1);
                    Pagination.Last();
                }
                Pagination.Finish();
            },



            // --------------------
            // Initialization
            // --------------------

            // binding buttons
            Buttons: function(e) {
                var nav = e.getElementsByTagName('a');

                nav[0].addEventListener('click', Pagination.Prev, false);
                nav[1].addEventListener('click', Pagination.Next, false);
            },

            // create skeleton
            Create: function(e) {

                var html = [
                    '<a onclick="previous(${paginationBean.currPage})" style="width:7rem;font-weight:600;font-size:14px;height:2.3rem;padding-top:6px; " id="previous">&#60;&#60; Previous</a>', // previous button
                    '<span></span>',  // pagination container
                    '<a onclick="next(${paginationBean.currPage})" style="width:6.5rem;font-weight:600;font-size:14px;height:2.3rem;padding-top:6px;" id="nxt">Next &#62;&#62;</a>'  // next button
                ];

                e.innerHTML = html.join('');
                Pagination.e = e.getElementsByTagName('span')[0];
                Pagination.Buttons(e);


                // old
                /* if (${paginationBean.currPage} == 1) {
				var previousButton = document.getElementById("previous");
				previousButton.style.pointerEvents = "none";
				previousButton.style.opacity = "0.5";
			} */

                // newer chnages

                if (${paginationBean.currPage} == 1) {
                    var previousButton = document.getElementById("previous");
                    previousButton.style.pointerEvents = "none";
                    previousButton.style.opacity = "0.5";
                }
                // my chnages
                if (${paginationBean.currPage} == Pagination.size) {
                    var nextButton = document.getElementById("nxt");
                    nextButton.style.pointerEvents = "none";
                    nextButton.style.opacity = "0.5";
                }
                if (Pagination.size == 0) {
                    var nextButton = document.getElementById("nxt");
                    nextButton.style.pointerEvents = "none";
                    nextButton.style.opacity = "0.5";
                }
                if (Pagination.size == 0) {
                    var paginationContainer = document.getElementById("pagination_row");
                    paginationContainer.style.display = "none";
                }


            },

            // init
            Init: function(e, data) {
                Pagination.Extend(data);
                Pagination.Create(e);
                Pagination.Start();
            }
        };



        /* * * * * * * * * * * * * * * * *
        * Initialization
        * * * * * * * * * * * * * * * * */

        var init = function() {
            Pagination.Init(document.getElementById('pagination'), {
                size: 100, // pages size
                page: 1,  // selected page
                step: 3   // pages before and after current
            });
        };

        document.addEventListener('DOMContentLoaded', init, false);

    </script>

    <script>
        var body = document.body;
        var initialOverflow = body.style.overflow;

    </script>

</div>
</body>

</html>